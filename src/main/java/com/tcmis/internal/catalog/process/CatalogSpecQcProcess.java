package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.client.catalog.beans.*;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.internal.catalog.beans.CatalogAddSpecViewBean;

/******************************************************************************
 * Process for catalog spec qc
 * @version 1.0
 *****************************************************************************/
public class CatalogSpecQcProcess extends BaseProcess {

    Log log = LogFactory.getLog(this.getClass());
    private DbManager dbManager;
	private Connection connection = null;
	private GenericSqlFactory genericSqlFactory;
    private String URL = "";
    public static final int HEADER_DATA = 0;
    public static final int SPEC_DATA = 1;
    public static final int SPEC_LIBRARIES = 2;
    
    public CatalogSpecQcProcess(String client) {
    	super(client);
    }
  
    public CatalogSpecQcProcess(String client, String locale, String URL) {
		super(client, locale);
        this.URL = URL;
    }

    public void deleteSpec(CatalogAddSpecBean inputBean) throws BaseException {
		try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            //catalog_add_spec_detail
            StringBuilder query = new StringBuilder("delete from catalog_add_spec_detail where request_id = ").append(inputBean.getRequestId());
            query.append(" and company_id = '").append(inputBean.getCompanyId()).append("' and spec_id = '").append(inputBean.getSpecId()).append("'");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            //catalog_add_spec_qc
            query = new StringBuilder("delete from catalog_add_spec_qc where request_id = ").append(inputBean.getRequestId());
            query.append(" and company_id = '").append(inputBean.getCompanyId()).append("' and spec_id = '").append(inputBean.getSpecId()).append("'");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}  //end of method

    public void addNewSpec(PersonnelBean personnelBean, CatalogAddSpecBean inputBean, Collection beans) throws BaseException {
		try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            //save screen data
            saveExistingData(inputBean,beans);

            //add new spec to request
            CatalogAddRequestProcess catAddReqProcess = new CatalogAddRequestProcess(this.getClient(),this.getLocale(),"");
            catAddReqProcess.setFactoryConnection(genericSqlFactory,connection);
            //delete spec if exist for request
            if (!StringHandler.isBlankString(inputBean.getSpecId())) {
                catAddReqProcess.deleteSelectedSpec(inputBean,"catalog_add_spec_qc");
            }
            //insert new data and it doesn't matter whether user is adding new spec or updating existing spec on request
            //get max line_item from catalog_add_spec for request
            StringBuilder query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_spec_qc where request_id = ").append(inputBean.getRequestId());
            query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
            String maxLineItem = genericSqlFactory.selectSingleValue(query.toString(),connection);
            catAddReqProcess.submitNewSpec(personnelBean,inputBean,maxLineItem,"catalog_add_spec_qc");
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}  //end of method

    public void addExistingSpec(CatalogAddSpecBean inputBean, Collection beans) throws BaseException {
		try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            //save screen data
            saveExistingData(inputBean,beans);

            //add existing spec to request
            CatalogAddRequestProcess catAddReqProcess = new CatalogAddRequestProcess(this.getClient(),this.getLocale(),"");
            catAddReqProcess.setFactoryConnection(genericSqlFactory,connection);
            //delete spec if exist for request
            if (!StringHandler.isBlankString(inputBean.getSpecId())) {
                //if user is replacing a spec with existing spec
                if (!StringHandler.isBlankString(inputBean.getOldSpecId())) {
                    //saving this so we can put it back because the deleteSelectedSpec method can only handle
                    //value in spec_id
                    String newSpecId = inputBean.getSpecId();
                    inputBean.setSpecId(inputBean.getOldSpecId());
                    catAddReqProcess.deleteSelectedSpec(inputBean,"catalog_add_spec_qc");
                    //put new spec_id back
                    inputBean.setSpecId(newSpecId);
                }
                //just in case delete data from catalog_add_spec_qc where spec_id is the new spec
                catAddReqProcess.deleteSelectedSpec(inputBean,"catalog_add_spec_qc");
                //inserting new data
                String maxLineItem = "";
                //set maxLineItem to equal to replaced line so data will match with catalog_add_spec
                if (!StringHandler.isBlankString(inputBean.getOldSpecId())) {
                    if (inputBean.getLineItem() != null) 
                        maxLineItem = inputBean.getLineItem().toString();
                }
                //if maxLineItem is not set then get it
                if (StringHandler.isBlankString(maxLineItem)){
                    //insert new data and it doesn't matter whether user is adding new spec or updating existing spec on request
                    //get max line_item from catalog_add_spec for request
                    StringBuilder query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_spec_qc where request_id = ").append(inputBean.getRequestId());
                    query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
                    maxLineItem = genericSqlFactory.selectSingleValue(query.toString(),connection);
                }
                catAddReqProcess.addingExistingSpecToRequest(inputBean,maxLineItem,"catalog_add_spec_qc");
                //delete exiting spec detail for spec_id
                StringBuilder query = new StringBuilder("delete from catalog_add_spec_detail where spec_id = ").append(SqlHandler.delimitString(inputBean.getSpecId()));
                query.append(" and spec_library = ").append(SqlHandler.delimitString(inputBean.getSpecLibrary()));
                query.append(" and request_id = ").append(inputBean.getRequestId());
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                //catAddReqProcess.copyExistingSpecDetailToRequest(inputBean,"catalog_add_spec_detail");
            }
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}  //end of method

    public void saveData(CatalogAddSpecBean inputBean, Collection beans) throws BaseException {
		try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
		    saveExistingData(inputBean,beans);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}  //end of method

    private void saveExistingData(CatalogAddSpecBean inputBean, Collection beans) {
        try {
            Iterator iter = beans.iterator();
            while (iter.hasNext()) {
                CatalogAddSpecBean tmpBean = (CatalogAddSpecBean)iter.next();
                //skip if data from catalog_add_spec, the reason this check is here is because
                //we want to show QC group the data that user entered
                if ( ! "QC".equals(tmpBean.getDataSource())) continue;

				// collapse all consecutive spaces into one, like a web browser would do
                tmpBean.setSpecName(StringUtils.join(StringUtils.split(tmpBean.getSpecName(), " "), ' '));
                tmpBean.setSpecTitle(StringUtils.join(StringUtils.split(tmpBean.getSpecTitle(), " "), ' '));
                tmpBean.setSpecVersion(StringUtils.join(StringUtils.split(tmpBean.getSpecVersion(), " "), ' '));
        		tmpBean.setSpecAmendment(StringUtils.join(StringUtils.split(tmpBean.getSpecAmendment(), " "), ' '));
        		tmpBean.setSpecId(StringUtils.join(StringUtils.split(tmpBean.getSpecId(), " "), ' '));
        		tmpBean.setOldSpecId(StringUtils.join(StringUtils.split(tmpBean.getOldSpecId(), " "), ' '));
                
                StringBuilder query = new StringBuilder("update catalog_add_spec_qc set spec_library = ");
                if (!StringHandler.isBlankString(tmpBean.getSpecLibrary())) {
                    query.append(SqlHandler.delimitString(tmpBean.getSpecLibrary()));
                }else {
                    query.append("''");
                }
                //spec_id
                StringBuilder specId = new StringBuilder(tmpBean.getSpecName());
                if (!StringHandler.isBlankString(tmpBean.getSpecVersion()))
                    specId = new StringBuilder(specId).append("_").append(tmpBean.getSpecVersion());
                if (!StringHandler.isBlankString(tmpBean.getSpecAmendment()))
                    specId = new StringBuilder(specId).append("_").append(tmpBean.getSpecAmendment());
                if (!specId.equals(tmpBean.getOldSpecId()))
                    query.append(",spec_id = ").append(SqlHandler.delimitString(specId.toString()));
                //spec_name
                query.append(",spec_name = ").append(SqlHandler.delimitString(tmpBean.getSpecName()));
                //spec_title
                if (!StringHandler.isBlankString(tmpBean.getSpecName())) {
                    query.append(",spec_title = ").append(SqlHandler.delimitString(tmpBean.getSpecTitle()));
                }else {
                    query.append(",spec_title = ''");
                }
                if (!StringHandler.isBlankString(tmpBean.getSpecVersion())) {
                    query.append(",spec_version = ").append(SqlHandler.delimitString(tmpBean.getSpecVersion()));
                }else {
                    query.append(",spec_version = ''");
                }
                if (!StringHandler.isBlankString(tmpBean.getSpecAmendment())) {
                    query.append(",spec_amendment = ").append(SqlHandler.delimitString(tmpBean.getSpecAmendment()));
                }else {
                    query.append(",spec_amendment = ''");
                }
                if ("disabled|true".equals(tmpBean.getCoc()) || "true".equals(tmpBean.getCoc())) {
                    query.append(",coc = 'Y'");
                }else {
                    query.append(",coc = null");
                }
                if ("disabled|true".equals(tmpBean.getCoa()) || "true".equals(tmpBean.getCoa())) {
                    query.append(",coa = 'Y'");
                }else {
                    query.append(",coa = null");
                }
                if (tmpBean.getSpecDate() != null) {
                    query.append(",spec_date = ").append(DateHandler.getOracleToDateFunction(tmpBean.getSpecDate()));
                }else {
                    query.append(",spec_date = ''");
                }

                query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and request_id = ").append(inputBean.getRequestId());
                query.append(" and line_item = ").append(tmpBean.getLineItem());
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    } //end of method

    public Object[] getRequestData(CatalogAddSpecBean inputBean, PersonnelBean personnelBean) throws BaseException {
        Object[] result = new Object[3];
        try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            CatAddHeaderViewBean catAddHeaderViewBean = getRequestHeader(inputBean, personnelBean);
            result[HEADER_DATA] = catAddHeaderViewBean;
            result[SPEC_DATA] = getRequestLines(catAddHeaderViewBean,inputBean);
            result[SPEC_LIBRARIES] = getSpecLibraryCollection(inputBean);
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
        return result;
    }  //end of method

    private CatAddHeaderViewBean getRequestHeader(CatalogAddSpecBean inputBean, PersonnelBean personnelBean) throws BaseException {
		CatAddHeaderViewBean catAddHeaderViewBean = null;
		try {
          CatalogAddRequestProcess process = new CatalogAddRequestProcess(this.getClient(),this.getLocale(),"");
          process.setFactoryConnection(genericSqlFactory,connection);
          //get catalog_add_request_new data
          Vector<CatAddHeaderViewBean> headerColl = (Vector)process.getCatAddHeaderView(inputBean.getRequestId());
          catAddHeaderViewBean = headerColl.get(0);
          
          //if request is pending approval then get all approvers who can approve this request
		  process.getApproverForRequest(catAddHeaderViewBean,catAddHeaderViewBean.getRequestId(),personnelBean);
          //set view level
          process.calculateViewLevel(catAddHeaderViewBean,personnelBean,"TCM Spec");
        }catch (Exception e) {
			e.printStackTrace();
		}
		return catAddHeaderViewBean;
	}
    
    private Collection<CatalogAddSpecViewBean> getRequestLines(CatAddHeaderViewBean catAddHeaderViewBean, CatalogAddSpecBean inputBean) throws BaseException {
		genericSqlFactory.setBeanObject(new CatalogAddSpecViewBean());
		SearchCriteria searchCriteria = new SearchCriteria();
		SortCriteria sortCriteria = new SortCriteria();
		searchCriteria.addCriterion("requestId", SearchCriterion.EQUALS, ""+inputBean.getRequestId());
		searchCriteria.addCriterion("companyId", SearchCriterion.EQUALS, ""+inputBean.getCompanyId());
        if (!"approver".equals(catAddHeaderViewBean.getViewLevel()))
            searchCriteria.addCriterion("dataSource", SearchCriterion.EQUALS, "QC");
        sortCriteria.addCriterion("lineItem", "asc");
		sortCriteria.addCriterion("dataSource", "asc");
    	return genericSqlFactory.select(searchCriteria, sortCriteria,connection,"CATALOG_ADD_SPEC_VW");
	}
    
    private Collection getSpecLibraryCollection(CatalogAddSpecBean inputBean) throws BaseException {
    	genericSqlFactory.setBeanObject(new CatalogAddSpecViewBean());
		return genericSqlFactory.selectQuery("select distinct spec_library from spec where company_id = '"+inputBean.getCompanyId()+"' order by spec_library",connection);
	}

    public void copyIntoCatalogAddSpec(String companyId,BigDecimal requestId) throws Exception {
        //first remove record
        genericSqlFactory.deleteInsertUpdate("delete from catalog_add_spec where request_id = "+requestId+" and company_id = '"+companyId+"'",connection);

        //catalog_add_spec
        StringBuilder query = new StringBuilder("insert into catalog_add_spec (company_id,request_id,spec_id,spec_name,spec_title");
        query.append(",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,spec_source)");
        query.append(" select company_id,request_id,spec_id,spec_name,spec_title");
        query.append(",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,");
        query.append("decode(spec_source,null,'catalog_add_spec','catalog_add_spec_qc','catalog_add_spec',spec_source)");
        query.append(" from catalog_add_spec_qc where request_id = ").append(requestId).append(" and company_id = '").append(companyId).append("'");
        genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        //todo need to revist this for China project
        /*update locale if needed
        if (!"en_US".equalsIgnoreCase(this.getLocale())) {
            //catalog_add_spec_qc_locale
            genericSqlFactory.deleteInsertUpdate("delete from catalog_add_spec_locale where request_id = "+requestId+" and company_id = '"+companyId+"'",connection);
            query = new StringBuilder("insert into catalog_add_spec_locale");
            query.append(" select * from catalog_add_spec_qc_locale");
            query.append(" where request_id = ").append(requestId).append(" and company_id = '").append(companyId).append("'");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }
        */
    } //end of method

    public void approveSpec(CatalogAddSpecBean inputBean, PersonnelBean user) throws BaseException {
        try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
            query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
            genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
            Vector<CatAddHeaderViewBean> dataC = (Vector)genericSqlFactory.selectQuery(query.toString());
            CatAddHeaderViewBean bean = dataC.get(0);

            //copy data back into catalog_add_spec
            copyIntoCatalogAddSpec(inputBean.getCompanyId(),inputBean.getRequestId());

            CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),calculateClientUrl(inputBean.getCompanyId()));
            process.setFactoryConnection(genericSqlFactory,connection);
            process.approvalRequestFromSpecificPage(bean,"TCM Spec",user);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
    } //end of method
    
    public void rejectSpec(CatalogAddSpecBean inputBean, PersonnelBean user) throws BaseException {
        try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
            genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
            Vector<CatAddHeaderViewBean> dataC = (Vector)genericSqlFactory.selectQuery(query.toString());
            CatAddHeaderViewBean bean = dataC.get(0);
            CatAddStatusViewBean statusBean = new CatAddStatusViewBean();
            statusBean.setApprovalRole("TCM Spec");
            statusBean.setStatus("Pending Spec");
            Collection approvalRoleBeans = new Vector();
            approvalRoleBeans.add(statusBean);

            CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),calculateClientUrl(inputBean.getCompanyId()));
            process.setFactoryConnection(genericSqlFactory,connection);
            process.callRejectRequest(bean, approvalRoleBeans, user);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
    } //end of method

    public void revertRequest(CatalogAddSpecBean inputBean) throws BaseException {
        try {
            dbManager = new DbManager(getClient(), getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            EngEvalProcess process = new EngEvalProcess(getClient(),getLocale(),calculateClientUrl(inputBean.getCompanyId()));
            process.setFactoryConnection(genericSqlFactory,connection);
            CatAddHeaderViewBean bean = new CatAddHeaderViewBean();
            bean.setCompanyId(inputBean.getCompanyId());
            bean.setRequestId(inputBean.getRequestId());
            process.revertRequestToPreviouslyApprovedSpecApproval(bean);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
    }

    private String calculateClientUrl(String companyId) {
        String result = URL;
        try {
            StringBuilder query = new StringBuilder("select web_application_path from");
            if ("TCM_OPS".equals(getClient()))
                query.append(" customer.connection_pool_company");
            else
                query.append(" connection_pool_company");
            query.append(" where company_id = '").append(companyId).append("'");
            
            String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
            if (!StringHandler.isBlankString(tmpVal)) {
                //removing current module and replace it with web_application_path from database
                result = URL.substring(0,URL.indexOf("/tcmIS"))+tmpVal;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }  //end of method

} //end of class