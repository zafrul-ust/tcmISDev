package com.tcmis.client.raytheon.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.Timestamp;
import java.sql.Connection;
import java.util.Calendar;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RegexMatcher;
import org.apache.commons.digester.RegexRules;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.SimpleRegexMatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.client.raytheon.beans.PossUploadInputBean;
import com.tcmis.client.catalog.factory.PossAddRequestBeanFactory;
import com.tcmis.client.catalog.factory.CatalogAddRequestNewBeanFactory;
import com.tcmis.client.catalog.factory.CatalogAddItemBeanFactory;
import com.tcmis.client.catalog.beans.*;
import com.tcmis.client.catalog.factory.CatalogAddUserGroupBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.factory.CatalogPartItemGroupBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;

/******************************************************************************
 * Process to load order data from POSS.
 * @version 1.0
 *****************************************************************************/
public class PossUploadProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  private DbManager dbManager;
  private Connection connection = null;
  private GenericSqlFactory genericSqlFactory;

  private static String[] file_fields =
      {
      "part number:",
      "trade name:",
      "description:",
      "manufacturer:",
      "container size:",
      "mfg part no:",
      "dwg or spec number:",
      "testing requirements doc:",
      "quality/packaging flowdown reqs:",
      "duplicate part no:",
      "new poss:",
      "replace poss:",
      "replaced part no:",
      "storage conditions:",
      "age:",
      "approved facility:",
      "approved source code:",
      "approved users:",
      "msds number:",
      "suggested vendor:",
      "suggested vendor part no:",
      "proper shipping name:",
      "hazard class:",
      "un number:",
      "na number:",
      "packing group:",
      "npfa-hmis numbers:",
      "requestor last name:",
      "requestor first name:",
      "requestor mi:",
      "mfg address:",
      "mfg tele:",
      "distrib address:",
      "distrib tele:",
      "additional specs:",
      "special notes:"
      };

  public PossUploadProcess(String client) {
    super(client);
  }

  public void load(PossUploadInputBean bean) throws BaseException {
	 String fullFileName = "";
	 try {
		dbManager = new DbManager(getClient());
		connection = dbManager.getConnection();
		genericSqlFactory = new GenericSqlFactory(dbManager);
		if (bean.getTheFile() != null) {
        //copy file to server
        File f = bean.getTheFile();
        ResourceLibrary resource = new ResourceLibrary("possUpload");
        String path = resource.getString("file.path");
        String fileName = resource.getString("file.name");
        Calendar cal = Calendar.getInstance();
        String tmpFileName = ""+cal.get(Calendar.YEAR)+cal.get(Calendar.MONTH)+cal.get(Calendar.DAY_OF_MONTH)+cal.get(Calendar.HOUR)+cal.get(Calendar.MINUTE)+cal.get(Calendar.SECOND);
        fullFileName = path+tmpFileName+fileName;
        File file = new File(fullFileName);
        //move file to the right place
        FileHandler.move(bean.getTheFile(), file);
        //process file
        PossAddRequestBean possAddRequestBean = readDataFromFile(fullFileName);
        if (possAddRequestBean != null) {
		  StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
		  BigDecimal requestId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
          //insert data into poss_add_request
          PossAddRequestBeanFactory factory = new PossAddRequestBeanFactory(dbManager);
          possAddRequestBean.setRequestId(requestId);
          factory.insert(possAddRequestBean);

          //find out whether this is new POSS or POSSAR
          boolean possar = false;
          CatalogPartItemGroupBeanFactory cFactory = new CatalogPartItemGroupBeanFactory(dbManager);
          SearchCriteria criteria = new SearchCriteria();
          criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,possAddRequestBean.getPossNo());
          criteria.addCriterion("catalogId", SearchCriterion.EQUALS,"Tucson");
          criteria.addCriterion("companyId", SearchCriterion.EQUALS,"RAYTHEON");
          Collection cColl = cFactory.select(criteria);
          if (cColl != null) {
            if (cColl.size() > 0) {
              possar = true;
            }
          }

          //insert data into catalog_add_request_new
          boolean ok = insertIntoCatalogAddRequestNew(fullFileName,possAddRequestBean,requestId,possar);
          //insert data into catalog_add_item
          if (ok) {
            ok = insertIntoCatalogAddItem(fullFileName,possAddRequestBean,requestId,possar);
          }
          //insert data into catalog_add_user_group
          if (ok) {
            ok = insertIntoCatalogAddUserGroup(fullFileName,possAddRequestBean,requestId);
          }
          //insert data into catalog_add_spec
          if (ok) {
            ok = insertIntoCatalogAddSpec(fullFileName,possAddRequestBean,requestId);
          }
          //auto approve approval roles that are not needed
          if (ok) {
            autoApprovedApprovalRoles(requestId);
          }
          //send out notification email
          if (ok) {
            ok = sendPOSSEmail(fullFileName,possAddRequestBean,requestId);
          }
        }
      }
    } catch (Exception e) {
      BaseException be = new BaseException("Can't put file on server:" + e.getMessage());
      be.setRootCause(e);
		MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Unable to load POSS file: "+fullFileName,"See log file.");
		throw be;
    }finally {
		dbManager.returnConnection(connection);
		dbManager = null;
		connection = null;
		genericSqlFactory = null;
	 }
  } //end of method

  private void autoApprovedApprovalRoles(BigDecimal requestId) throws Exception {
		try {
            //getting all the Raytheon approval roles and auto approve it for POSS
            StringBuilder query = new StringBuilder("select distinct approval_role from vv_chemical_approval_role where company_id = 'RAYTHEON'");
            query.append(" and catalog_company_id = 'RAYTHEON' and catalog_id = 'Tucson' and facility_id = 'Tucson Airport' and active = 'Y'");
            query.append(" and approval_role not in ('Material QC','Item QC,'TCM Spec')");
            genericSqlFactory.setBeanObject(new FacilityApprovalRolesBean());
            Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
            while (iter.hasNext()) {
                FacilityApprovalRolesBean bean = (FacilityApprovalRolesBean)iter.next();
                query = new StringBuilder("insert into catalog_add_approval(company_id,request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id,start_time)");
                query.append(" select distinct carn.company_id,carn.request_id,-1,sysdate,'Approved','Auto approved','").append(bean.getApprovalRole()).append("',carn.eng_eval_facility_id,carn.catalog_id,carn.catalog_company_id,sysdate from");
                query.append(" catalog_add_request_new carn where carn.request_id = ").append(requestId);
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
  } //end of method

  private boolean insertIntoCatalogAddSpec(String fileName, PossAddRequestBean possAddBean,BigDecimal requestId) {
    boolean result = true;
    StringBuilder query = new StringBuilder("");
    try {
      //catalog_add_spec
      query = new StringBuilder("insert into catalog_add_spec (spec_id,spec_name,spec_title,request_id,line_item,spec_source)");
      query.append(" values ('").append(possAddBean.getPossNo()).append("','").append(possAddBean.getPossNo()).append("','").append(possAddBean.getPossNo()).append("'");
      query.append(",").append(requestId).append(",1,'catalog_add_spec')");
      genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
      //catalog_add_spec_qc
      query = new StringBuilder("insert into catalog_add_spec_qc (spec_id,spec_name,spec_title,company_id,request_id,line_item,spec_source)");
      query.append(" values ('").append(possAddBean.getPossNo()).append("','").append(possAddBean.getPossNo()).append("','").append(possAddBean.getPossNo()).append("'");
      query.append(",'RAYTHEON',").append(requestId).append(",1,'catalog_add_spec_qc')");
      genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
    }catch (Exception e) {
      result = false;
      e.printStackTrace();
      MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error occurred while insertIntoCatalogAddSpec POSS file: "+fileName,query.toString());
    }
    return result;
  }

  private boolean sendPOSSEmail(String fileName, PossAddRequestBean possAddBean, BigDecimal requestId) {
    boolean result = true;
    try {
      String esub = new String("Request " + requestId.intValue() + " was CREATED by POSS.");
      String emsg = new String("Status     : APPROVED by POSS\n");
      emsg += "Request Id : " + requestId.intValue() + "\n";
      emsg += "Requestor  : " + possAddBean.getRequestorLast()+" "+possAddBean.getRequestorFirst() + "\n";
      emsg += "POSS number: " + possAddBean.getPossNo() + "\n";
      DbManager dbManager = new DbManager(getClient());
      PersonnelBeanFactory pFactory = new PersonnelBeanFactory(dbManager);
      Collection pColl = pFactory.selectUsersByGroup("Phoenix Hub","POSSEmail");
      Iterator iter = pColl.iterator();
      String[] toEmailAddress = new String[pColl.size()];
      int count = 0;
      while (iter.hasNext()) {
        PersonnelBean pBean = (PersonnelBean)iter.next();
        if (pBean.getEmail() != null) {
          toEmailAddress[count] = pBean.getEmail();
          count++;
        }
      }
		if (count > 0) {
            MailProcess.sendEmail(toEmailAddress,null,esub,emsg,false);
        }
	 }catch (Exception e) {
      result = false;
      e.printStackTrace();
      MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Unable to send notification email POSS file: "+fileName,"See log file.");
    }
    return result;
  }

  private boolean insertIntoCatalogAddUserGroup(String fileName,PossAddRequestBean possAddBean,BigDecimal requestId) {
    boolean result = true;
    try {
      CatalogAddUserGroupBean catAddUserGroupBean = new CatalogAddUserGroupBean();
      catAddUserGroupBean.setRequestId(requestId);
      catAddUserGroupBean.setCompanyId("RAYTHEON");
      catAddUserGroupBean.setFacilityId("Tucson Airport");
      catAddUserGroupBean.setWorkArea("All");
      catAddUserGroupBean.setUserGroupId("All");
      catAddUserGroupBean.setProcessDesc("POSS material");
      catAddUserGroupBean.setQuantity1(new BigDecimal(0));
      catAddUserGroupBean.setPer1(new BigDecimal(0));
      catAddUserGroupBean.setPeriod1("days");
      catAddUserGroupBean.setQuantity2(new BigDecimal(0));
      catAddUserGroupBean.setPer2(new BigDecimal(0));
      catAddUserGroupBean.setPeriod2("days");
      CatalogAddUserGroupBeanFactory cFactory = new CatalogAddUserGroupBeanFactory(dbManager);
      cFactory.insert(catAddUserGroupBean,connection);
    }catch (Exception e) {
      result = false;
      e.printStackTrace();
      MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error occurred while insertIntoCatalogAddUserGroup POSS file: "+fileName,"See log file.");
    }
    return result;
  }

  private boolean insertIntoCatalogAddItem(String fileName,PossAddRequestBean possAddBean,BigDecimal requestId, boolean possar) {
    boolean result = true;
    try {
      CatalogAddItemBean catAddItemBean = new CatalogAddItemBean();
      catAddItemBean.setRequestId(requestId);
      catAddItemBean.setCompanyId("RAYTHEON");
		catAddItemBean.setLineItem(new BigDecimal(1));
		catAddItemBean.setMaterialDesc(possAddBean.getDescription());
      catAddItemBean.setManufacturer(possAddBean.getManufacturer());
      catAddItemBean.setMfgCatalogId(possAddBean.getManufacturersPart());
      catAddItemBean.setMfgTradeName(possAddBean.getTradename());
      catAddItemBean.setPossSize(possAddBean.getPossSize());
      catAddItemBean.setPossSpecialNote(possAddBean.getSpecialNotes());
      catAddItemBean.setPartId(new BigDecimal(1));
		catAddItemBean.setVendorContactPhone(possAddBean.getDistributorPhone());
      catAddItemBean.setSuggestedVendor(possAddBean.getSuggestedVendor());
		catAddItemBean.setStartingView(new BigDecimal(0));

		String tmpComment = "NEW POSS";
      if (possar) {
        tmpComment = "POSSAR";
      }
      tmpComment += " - "+possAddBean.getPossNo();
      CatalogAddItemBeanFactory cFactory = new CatalogAddItemBeanFactory(dbManager);
      cFactory.insert(catAddItemBean,connection);
      //save user data
      cFactory.createOrig(requestId,connection);
      //insert data for QC
      cFactory.createQc(requestId,tmpComment,connection);
    }catch (Exception e) {
      result = false;
      e.printStackTrace();
      MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error occurred while insertIntoCatalogAddItem POSS file: "+fileName,"See log file.");
    }
    return result;
  }

  private boolean insertIntoCatalogAddRequestNew(String fileName,PossAddRequestBean possAddBean,BigDecimal requestId, boolean possar) {
    boolean result = true;
    try {
      //get personnel_id from name
      BigDecimal requestorId = new BigDecimal(-1);
      PersonnelBeanFactory pFactory = new PersonnelBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("lastName", SearchCriterion.EQUALS,possAddBean.getRequestorLast(),SearchCriterion.IGNORE_CASE);
      criteria.addCriterion("firstName", SearchCriterion.EQUALS,possAddBean.getRequestorFirst(),SearchCriterion.IGNORE_CASE);
		if (!StringHandler.isBlankString(possAddBean.getRequestorMiddle())) {
			criteria.addCriterion("midInitial", SearchCriterion.EQUALS,possAddBean.getRequestorMiddle(),SearchCriterion.IGNORE_CASE);
		}
		Collection pColl = pFactory.select(criteria,connection);
      if (pColl != null) {
        if (pColl.size() > 0 ) {
          Iterator iter = pColl.iterator();
          while (iter.hasNext()) {
            PersonnelBean pBean = (PersonnelBean)iter.next();
            requestorId = new BigDecimal(pBean.getPersonnelId());
            break;
          }
        }
      }
		StringBuilder query = new StringBuilder("insert into catalog_add_request_new (request_id,request_date,company_id,catalog_company_id,catalog_id,cat_part_no,replaces_part_no");
		query.append(",part_group_no,stocked,starting_view,engineering_evaluation,shelf_life_source,requestor,poss_est_mon_usage,poss_store,part_description");
		query.append(",submit_date,request_status,approval_group_seq,eng_eval_facility_id,poss,qc_status)");
		query.append(" values (").append(requestId).append(",sysdate,'RAYTHEON','RAYTHEON','Tucson',").append(SqlHandler.delimitString(possAddBean.getPossNo())).append(",");
		query.append(SqlHandler.delimitString(possAddBean.getReplPartno())).append(",1,'OOR',0,'N','Mfg',").append(requestorId).append(",").append(SqlHandler.delimitString(possAddBean.getEstMonUsage()));
		query.append(",").append(SqlHandler.delimitString(possAddBean.getStore())).append(",").append(SqlHandler.delimitString(possAddBean.getDescription()));
		query.append(",sysdate,6,1,'Tucson Airport'");
        if (possar) {
            query.append(",'R','Pending Item'");
        }else {
            query.append(",'Y','Pending Material'");
        }
        query.append(")");
        genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
	 }catch (Exception e) {
      result = false;
      e.printStackTrace();
      MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error occurred while insertIntoCatalogAddRequestNew POSS file: "+fileName,"See log file.");
    }
    return result;
  }

  //returns data loaded in bean if everything is okay
  //otherwise returns bean = null
  private PossAddRequestBean readDataFromFile(String fileName) {
    PossAddRequestBean bean = new PossAddRequestBean();
    try{
      //getting data from file
      BufferedReader in = new BufferedReader(new FileReader(fileName));
      int rowNum = 0;
      String tmp = null;
      while ((tmp = in.readLine()) != null) {
        if (tmp.trim().length() > 0) {
			  tmp = tmp.trim();
			 //check to make sure that data in line is correct
          boolean validDataInLine = false;
          for (int i = 0; i < file_fields.length; i++) {
            if (tmp.indexOf(file_fields[i]) > -1) {
				  validDataInLine = true;
              break;
            }
          }
          if (!validDataInLine) {
            rowNum--;
          }
          if (rowNum == 0) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getPossNo() == null) {
                bean.setPossNo(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setPossNo(bean.getPossNo()+" "+tmp);
              }
            }
          } else if (rowNum == 1) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getTradename() == null) {
                 bean.setTradename(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setTradename(bean.getTradename()+" "+tmp);
              }
            }
          } else if (rowNum == 2) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getDescription() == null) {
                bean.setDescription(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setDescription(bean.getDescription()+" "+tmp);
              }
            }
          } else if (rowNum == 3) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getManufacturer() == null) {
                 bean.setManufacturer(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setManufacturer(bean.getManufacturer()+" "+tmp);
              }
            }
          } else if (rowNum == 4) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getPossSize() == null) {
                bean.setPossSize(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setPossSize(bean.getPossSize()+" "+tmp);
              }
            }
          } else if (rowNum == 5) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getManufacturersPart() == null) {
                bean.setManufacturersPart(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setManufacturersPart(bean.getManufacturersPart()+" "+tmp);
              }
            }
          } else if (rowNum == 6) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getDwgOrSpecNumber() == null) {
                bean.setDwgOrSpecNumber(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setDwgOrSpecNumber(bean.getDwgOrSpecNumber()+" "+tmp);
              }
            }
          } else if (rowNum == 7) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getTestingReqsDocument() == null) {
                bean.setTestingReqsDocument(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setTestingReqsDocument(bean.getTestingReqsDocument()+" "+tmp);
              }
            }
          } else if (rowNum == 8) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getQaAttPkgAtt() == null) {
                bean.setQaAttPkgAtt(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setQaAttPkgAtt(bean.getQaAttPkgAtt()+" "+tmp);
              }
            }
          } else if (rowNum == 9) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getDupPartno() == null) {
                bean.setDupPartno(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setDupPartno(bean.getDupPartno()+" "+tmp);
              }
            }
          } else if (rowNum == 10) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getNewPoss() == null) {
                bean.setNewPoss(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setNewPoss(bean.getNewPoss()+" "+tmp);
              }
            }
          } else if (rowNum == 11) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getReplacePoss() == null) {
                bean.setReplacePoss(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setReplacePoss(bean.getReplacePoss()+" "+tmp);
              }
            }
          } else if (rowNum == 12) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getReplPartno() == null) {
                bean.setReplPartno(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setReplPartno(bean.getReplPartno()+" "+tmp);
              }
            }
          } else if (rowNum == 13) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getStore() == null) {
                bean.setStore(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setStore(bean.getStore()+" "+tmp);
              }
            }
          } else if (rowNum == 14) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getAge() == null) {
                bean.setAge(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setAge(bean.getAge()+" "+tmp);
              }
            }
          } else if (rowNum == 15) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getOrdFacility() == null) {
                bean.setOrdFacility(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setOrdFacility(bean.getOrdFacility()+" "+tmp);
              }
            }
          } else if (rowNum == 16) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getOrdSourceCd() == null) {
                bean.setOrdSourceCd(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setOrdSourceCd(bean.getOrdSourceCd()+" "+tmp);
              }
            }
          } else if (rowNum == 17) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getApprovedUsers() == null) {
                bean.setApprovedUsers(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setApprovedUsers(bean.getApprovedUsers()+" "+tmp);
              }
            }
          } else if (rowNum == 18) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getMsdsNumber() == null) {
                bean.setMsdsNumber(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setMsdsNumber(bean.getMsdsNumber()+" "+tmp);
              }
            }
          } else if (rowNum == 19) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getSuggestedVendor() == null) {
                bean.setSuggestedVendor(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setSuggestedVendor(bean.getSuggestedVendor()+" "+tmp);
              }
            }
          } else if (rowNum == 20) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getMaxSuggestedVendorPartNo() == null) {
                bean.setMaxSuggestedVendorPartNo(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setMaxSuggestedVendorPartNo(bean.getMaxSuggestedVendorPartNo()+" "+tmp);
              }
            }
          } else if (rowNum == 21) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getProperShippingName() == null) {
                bean.setProperShippingName(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setProperShippingName(bean.getProperShippingName()+" "+tmp);
              }
            }
          } else if (rowNum == 22) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getHazardClass() == null) {
                bean.setHazardClass(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setHazardClass(bean.getHazardClass()+" "+tmp);
              }
            }
          } else if (rowNum == 23) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getUnNumber() == null) {
                bean.setUnNumber(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setUnNumber(bean.getUnNumber()+" "+tmp);
              }
            }
          } else if (rowNum == 24) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getNaNumber() == null) {
                bean.setNaNumber(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setNaNumber(bean.getNaNumber()+" "+tmp);
              }
            }
          } else if (rowNum == 25) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getPackingGroup() == null) {
                bean.setPackingGroup(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setPackingGroup(bean.getPackingGroup()+" "+tmp);
              }
            }
          } else if (rowNum == 26) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getNfpaHmisNumbers() == null) {
                bean.setNfpaHmisNumbers(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setNfpaHmisNumbers(bean.getNfpaHmisNumbers()+" "+tmp);
              }
            }
          } else if (rowNum == 27) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getRequestorLast() == null) {
                bean.setRequestorLast(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setRequestorLast(bean.getRequestorLast()+" "+tmp);
              }
            }
          } else if (rowNum == 28) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getRequestorFirst() == null) {
                bean.setRequestorFirst(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setRequestorFirst(bean.getRequestorFirst()+" "+tmp);
              }
            }
          } else if (rowNum == 29) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getRequestorMiddle() == null) {
                bean.setRequestorMiddle(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setRequestorMiddle(bean.getRequestorMiddle()+" "+tmp);
              }
            }
          } else if (rowNum == 30) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getMfgAddress() == null) {
                bean.setMfgAddress(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setMfgAddress(bean.getMfgAddress()+" "+tmp);
              }
            }
          } else if (rowNum == 31) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getMfgPhone() == null) {
                bean.setMfgPhone(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setMfgPhone(bean.getMfgPhone()+" "+tmp);
              }
            }
          } else if (rowNum == 32) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getDistributorAddress() == null) {
                bean.setDistributorAddress(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setDistributorAddress(bean.getDistributorAddress()+" "+tmp);
              }
            }
          } else if (rowNum == 33) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getDistributorPhone() == null) {
                bean.setDistributorPhone(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setDistributorPhone(bean.getDistributorPhone()+" "+tmp);
              }
            }
          } else if (rowNum == 34) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getAdditionalSpecs() == null) {
                bean.setAdditionalSpecs(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setAdditionalSpecs(bean.getAdditionalSpecs()+" "+tmp);
              }
            }
          } else if (rowNum == 35) {
            if (tmp.length() > file_fields[rowNum].length()) {
              if (bean.getSpecialNotes() == null) {
                bean.setSpecialNotes(tmp.substring(file_fields[rowNum].length()));
              }else {
                bean.setSpecialNotes(bean.getSpecialNotes()+" "+tmp);
              }
            }
          }
          rowNum++;
        } //end of if line is empty
      } //end while loop
      in.close();

      if (rowNum != 36) {
        MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error occurred while reading POSS file: "+fileName,"File contains less/more lines than expected.");
        bean = null;
      }
    }catch (Exception ee) {
      ee.printStackTrace();
      bean = null;
      MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error occurred while reading POSS file: "+fileName,"See log file.");
    }
    return bean;
  } //end of method

} //end of class
