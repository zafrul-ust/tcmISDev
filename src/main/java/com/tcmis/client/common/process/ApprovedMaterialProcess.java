package com.tcmis.client.common.process;

import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.internal.catalog.beans.CatalogAddReqMsdsInputBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.admin.process.FetchMsdsProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.common.beans.ApprovedMaterialInputBean;
import com.tcmis.client.common.beans.MsdsDocumentInputBean;
import com.tcmis.client.common.beans.ViewMsdsInputBean;
import com.tcmis.client.common.factory.MsdsDocumentViewBeanFactory;
import com.tcmis.internal.catalog.beans.MsdsBean;
import com.tcmis.internal.catalog.factory.MsdsBeanFactory;

import java.sql.Connection;
import java.util.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/******************************************************************************
 * Process for ApprovedMaterialProcess
 * @version 1.0
 *****************************************************************************/
public class ApprovedMaterialProcess extends BaseProcess implements Runnable{

	Log log = LogFactory.getLog(this.getClass());
    private DbManager dbManager;
	private Connection connection = null;
	private GenericSqlFactory genericSqlFactory;
    private String URL = "";
    private ApprovedMaterialInputBean inputBean;

    public ApprovedMaterialProcess(String client,String locale, String url, ApprovedMaterialInputBean inputBean)  {
		super(client,locale);
        this.URL = url;
        this.inputBean = inputBean;
    }

    public void processRequest() throws BaseException {
        try {
            Thread thread = new Thread(this);
            thread.start();
        }catch (Exception ee) {
            StringBuilder esub = new StringBuilder("Error while processing data from Maxcom/CatAdmin call");
            String emsg = ee.getMessage();
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
        }
    }

    public void run() {
        try {
            ApprovedMaterialProcess process = new ApprovedMaterialProcess(getClient(),getLocale(),URL,inputBean);
            if (inputBean.isGetImage()) {
                process.getImageOnly(inputBean.getMsdsId(),inputBean.getDocId());
            }else if (inputBean.getBatchId() != null) {
                process.processData(inputBean.getBatchId());
            }else if (inputBean.getMaterialId() != null) {
                process.processData(inputBean.getMaterialId(),inputBean.getRevisionDateTimeStamp(),inputBean.getLocaleCode());
            }else if (inputBean.isManualDataEntryComplete()) {
                process.manualSetDataEntryComplete();
            }else if (inputBean.isReleasingCatalogAddRequest()) {
                process.releasingCatalogAddRequests();
            }
        } catch(Exception e){
            log.fatal("Error processing CabinetPutAwayScan " + e.getMessage(), e);
        }
    }

    private void convertDateIntoLong() {
        try {
            Date tmpDate = DateHandler.getDateFromString("MM/dd/yyyy","7/11/2014");
            log.debug("date:"+tmpDate.getTime()+"*");
            tmpDate = DateHandler.getDateFromString("MM/dd/yyyy","2/19/2013");
            log.debug("date:"+tmpDate.getTime());
            tmpDate = DateHandler.getDateFromString("MM/dd/yyyy HH:mm:ss a","01/06/2012 12:01:00 AM");
            log.debug("date:"+tmpDate.getTime());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMsdsImage(BigDecimal materialId, Date revisionDate, String localeCode, String docId, String msdsId) throws Exception {
        try {
            String source = "msdsAdmin";
            StringBuilder query;
            ViewMsdsInputBean bean = new ViewMsdsInputBean();
            if (StringHandler.isBlankString(docId)) {
                //check to see if we need to pull image from Maxcom
                query = new StringBuilder("select content,document_id from global.msds where material_id = ").append(materialId);
                query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate,"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                query.append(" and (content is null or lower(content) = 'additional')");
                genericSqlFactory.setBeanObject(new ViewMsdsInputBean());
                Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
                while (iter.hasNext()) {
                    bean = (ViewMsdsInputBean)iter.next();
                    break;
                }
            }else {
                source = "catAdmin";
                bean.setDocumentId(new BigDecimal(docId));
            }
            if (bean.getDocumentId() != null) {
                //get image from Maxcom
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMddyy mm");
                String tmpDate2 = dateFormatter.format(revisionDate);
                String[] tmpFormatDate = tmpDate2.split(" ");
                StringBuilder fileName = new StringBuilder(materialId.toString()).append("_").append(tmpFormatDate[0]).append("_00").append(tmpFormatDate[1]);
                if (!StringHandler.isBlankString(localeCode) && !"en_US".equals(localeCode))
                    fileName.append("_").append(localeCode.toLowerCase());
                fileName.append(".pdf");
                //set whether this is MSDS or additional document
                String fileType = "MSDS";
                if ("additional".equals(bean.getContent()))
                    fileType = "additionalDocument";
                //now fetch file
                FetchMsdsProcess process = new FetchMsdsProcess(getClient());
                process.fetchMsds(fileName.toString(),bean.getDocumentId().toString(),source,fileType);
                //update content
                String tmpUrl = URL.substring(0,URL.indexOf("/tcmIS"));
                if (URL.startsWith("http://ws1.tcmis.com"))
                    tmpUrl = tmpUrl.replace("http://ws1.tcmis.com","https://www.tcmis.com");
                else if (URL.startsWith("http://www.tcmis.com"))
                    tmpUrl = tmpUrl.replace("http:","https:");

                ResourceLibrary resource = new ResourceLibrary("maxcom");
                //update database tables
                if ("additional".equals(bean.getContent())) {
                    //update content to no required ex. https://www.tcmis.com/MSDS/not_required.html
                    query = new StringBuilder("update global.msds set content = '").append(tmpUrl).append(resource.getLabel("tcmis.msds.dir")).append("not_required.html").append("' where material_id = ").append(materialId);
                    query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate,"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                    query.append(" and content = 'additional'");
                    genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                    //insert data into material_document
                    MsdsDocumentInputBean insertBean = new MsdsDocumentInputBean();
                    insertBean.setMaterialId(materialId);
                    insertBean.setDocumentName("Technical Data Sheet");
                    insertBean.setDocumentType("Technical Data Sheet");
                    insertBean.setDocumentSource("Catalog");
                    insertBean.setDocumentUrl(tmpUrl + resource.getLabel("tcmis.additional.doc.dir") + fileName);
                    insertBean.setEnteredBy(new BigDecimal(-1));
                    new MsdsDocumentViewBeanFactory(genericSqlFactory.getDbManager()).insert(insertBean, connection);
                }else {
                    query = new StringBuilder("update global.msds set content = '").append(tmpUrl).append(resource.getLabel("tcmis.msds.dir")).append(fileName).append("'");
                    if (!StringHandler.isBlankString(localeCode) && !"en_US".equals(localeCode))
                        query.append(",on_line = 'N'");
                    else
                        query.append(",on_line = 'Y'");
                    query.append(" where material_id = ").append(materialId);
                    query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate,"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                    genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                }
                //handle locale code
                if (!StringHandler.isBlankString(localeCode) && !"en_US".equals(localeCode)) {
                    MsdsBeanFactory msdsFactory = new MsdsBeanFactory(dbManager);
			        MsdsBean msdsBean = msdsFactory.select(materialId, revisionDate, connection);
                    msdsBean.setImageLocaleCode(localeCode);
                    msdsFactory.setMsdsLocale(msdsBean,connection);
                }
                //tell Maxcom that tcmIS got the image from catAdmin
                if (!StringHandler.isBlankString(msdsId) && !"additionalDocument".equals(fileType) && "catAdmin".equals(source))
                    process.fetchMsdsCatAdminCompleted(msdsId);
            }
        }catch (Exception e) {
            log.error(e);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            String tmpDate = dateFormatter.format(revisionDate);
            StringBuilder msg = new StringBuilder("material_id: ").append(materialId);
            msg.append("\n").append("revision_date: ").append(revisionDate).append(" (").append(tmpDate).append(")");
            msg.append("\n").append("locale_code: ").append(localeCode);
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error occurred while get MSDS image from Maxcom",msg.toString());
            throw e;
        }
    } //end of method

    private String setDataGlobalEntryComplete(BigDecimal materialId, Date revisionDate, BigDecimal creatorId) {
        String result = "";
        try {
            StringBuilder query = new StringBuilder("select pkg_data_entry_complete.FX_DATA_ENTRY_COMPLETE(");
            query.append(materialId).append(",").append(DateHandler.getOracleToDateFunction(revisionDate)).append(") from dual");
            result = genericSqlFactory.selectSingleValue(query.toString(),connection);
            if ("Y".equals(result)) {
                //if revision required QC
                query = new StringBuilder("select count(*) from msds_qc where material_id = ").append(materialId);
                query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate,"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                query.append(" and submit_date is null and approve_date is null");
                if (!dataCountIsZero(query.toString())) {
                    //then submit for QC
                    query = new StringBuilder("update msds_qc set source = 'Maxcom', submit_date = sysdate");
                    if (creatorId != null)
                        query.append(", submit_by = ").append(creatorId);
                    else
                        query.append(", submit_by = -1");
                    query.append(" where material_id = ").append(materialId);
                    query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate,"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                    genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                }else {
                    //set DEC = Y
                    // if no data in msds_qc
                    //or msds_qc is approved
                    //revision does not required qc
                    boolean revisionDoesNotRequiredQc = false;
                    query = new StringBuilder("select count(*) from msds_qc where material_id = ").append(materialId);
                    query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate,"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                    if (dataCountIsZero(query.toString())) {
                        revisionDoesNotRequiredQc = true;
                    }
                    //revision QC approved
                    boolean revisionQcApproved = false;
                    query = new StringBuilder("select count(*) from msds_qc where material_id = ").append(materialId);
                    query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate,"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                    query.append(" and submit_date is not null and approve_date is not null");
                    if (!dataCountIsZero(query.toString())) {
                        revisionQcApproved = true;
                    }
                    //set data_entry_complete of revision does not required qc or qc approved
                    if (revisionDoesNotRequiredQc || revisionQcApproved) {
                        //otherwise set DEC = Y
                        Collection inArgs = new Vector(3);
                        inArgs.add(materialId);
                        inArgs.add(revisionDate);
                        inArgs.add("N");
                        inArgs.add("-1");
                        Collection outArgs = new Vector(2);
                        outArgs.add(new Integer(java.sql.Types.VARCHAR));
                        outArgs.add(new Integer(java.sql.Types.VARCHAR));
                        Collection data = genericSqlFactory.doProcedure(connection, "global.pkg_data_entry_complete.P_SET_DATA_ENTRY_COMPLETE", inArgs, outArgs);
                        Iterator i11 = data.iterator();
                        String erroVal = "";
                        int i = 0;
                        while (i11.hasNext()) {
                            if (i++ == 0)
                                result = (String) i11.next();
                            else
                                erroVal = (String) i11.next();
                        }
                        if (!StringHandler.isBlankString(erroVal)) {
                            if (!"OK".equalsIgnoreCase(erroVal.trim())) {
                                MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "setDataGlobalEntryComplete : pkg_data_entry_complete.P_SET_DATA_ENTRY_COMPLETE (material_id: " + materialId + " revision_date: " + revisionDate + ")", "Error while executing P_SET_DATA_ENTRY_COMPLETE: " + erroVal);
                            }
                        }
                    }
                }

                //if global data entry complete then call procedure to create company msds data if needed
                //TCMISDEV-2619
                //if DEC still Y after procedure call
                if(!StringHandler.isBlankString(result) && "Y".equals(result.trim())) {
                    Collection inArgs = new Vector(2);
                    inArgs.add(materialId);
                    inArgs.add(revisionDate);
                    genericSqlFactory.doProcedure(connection, "pkg_data_entry_complete.p_insert_company_msds", inArgs);
                }
            } //end if DEC = Y
        }catch ( Exception dbe ) {
            result = "N";
            log.error(dbe);
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","setDataGlobalEntryComplete : pkg_data_entry_complete.P_SET_DATA_ENTRY_COMPLETE (material_id: " + materialId + " revision_date: " + revisionDate+ ")","Error while executing P_SET_DATA_ENTRY_COMPLETE, see log for details");
        }
        if (StringHandler.isBlankString(result))
            result = "N";
        return result.trim();

    } //end of method

    //global.msds.id_only
    // Y - pending for Maxcom to pull record into their system
    // C, N, null - completed
    // E - error
    // A - approval from Maxcom pending tcmIS process to get MSDS (if missing) and set global.msds.data_entry_complete
    public void processData(BigDecimal batchId) throws BaseException {
        try {
            StringBuilder esub = new StringBuilder("Global Data Entry Complete is not complete for the following materials");
            StringBuilder emsg = new StringBuilder("");
            dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
            StringBuilder query = null;
            if (batchId.equals(new BigDecimal(1))) {
                query = new StringBuilder("select global.msds_batch_id_seq.nextval from dual");
                batchId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
                query = new StringBuilder("update global.msds set msds_batch_id = ").append(batchId).append(" where id_only = 'A' and msds_batch_id is null");
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
            query = new StringBuilder("select m.material_id,m.revision_date,nvl(ml.locale_code,m.locale_code) locale_code");
            query.append(", nvl(m.inserted_by,m.verified_by) creator_id");
            query.append(" from global.msds m, global.msds_locale ml where m.id_only = 'A'");
            query.append(" and msds_batch_id = ").append(batchId);
            query.append(" and m.material_id = ml.material_id(+) and m.revision_date = ml.revision_date(+)");
            genericSqlFactory.setBeanObject(new ApprovedMaterialInputBean());
            Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
            while (iter.hasNext()) {
                ApprovedMaterialInputBean bean = (ApprovedMaterialInputBean)iter.next();
                //get MSDS image from Maxcom and update content
                getMsdsImage(bean.getMaterialId(),bean.getRevisionDate(),bean.getLocaleCode(),"","");
                //set global data entry complete
                String globalDataEntryComplete = setDataGlobalEntryComplete(bean.getMaterialId(),bean.getRevisionDate(),bean.getCreatorId());
                query = new StringBuilder("update global.msds set ");
                if ("N".equals(globalDataEntryComplete)) {
                    query.append("id_only = 'E'");
                    emsg.append("material: ").append(bean.getMaterialId());
                    emsg.append("revision_date: ").append(bean.getRevisionDate());
                }else {
                    query.append("id_only = 'C'");
                }
                query.append(" where material_id = ").append(bean.getMaterialId());
                query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(bean.getRevisionDate(),"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                //process catalog adds
                processCatalogAddForMaterial(bean.getMaterialId());
                //handle "not required" pictogram
                query = new StringBuilder("delete from msds_ghs_pictogram where material_id = ").append(bean.getMaterialId());
                query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(bean.getRevisionDate(),"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                query.append(" and pictogram_id = 'not required'");
                query.append(" and exists (select null from msds_ghs_pictogram where material_id = ").append(bean.getMaterialId());
                query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(bean.getRevisionDate(),"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss am"));
                query.append(" and pictogram_id <> 'not required')");
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
            //sending out email
            if (!StringHandler.isBlankString(emsg.toString())) {
                MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
            }
        }catch (Exception ee) {
			StringBuilder esub = new StringBuilder("Error while processData for approved batch MSDS: ");
			String emsg = ee.getMessage();
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
    }

    public void processData(BigDecimal materialId, BigDecimal revisionDateTimeStamp, String localeCode) throws BaseException {
		try {
            dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
            //get MSDS image from Maxcom and update content
            Date revDate = new Date(revisionDateTimeStamp.longValueExact());
            getMsdsImage(materialId,revDate,localeCode,null,"");
            //set global data entry complete
            String globalDataEntryComplete = setDataGlobalEntryComplete(materialId,revDate,null);
            if ("N".equals(globalDataEntryComplete)) {
                StringBuilder esub = new StringBuilder("Global Data Entry Complete is not complete for material: ");
                esub.append(materialId);
                MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),esub.toString());
            }
            //process catalog adds
            processCatalogAddForMaterial(materialId);
        }catch (Exception ee) {
			StringBuilder esub = new StringBuilder("Error while processData for approved material: ");
			esub.append(materialId);
			String emsg = ee.getMessage();
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
    }

    public void getImageOnly(String msdsId, String docId) throws BaseException {
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("select m.material_id,m.revision_date,m.locale_code from global.msds m");
            query.append(" where msds_id = ").append(msdsId);
            genericSqlFactory.setBeanObject(new ApprovedMaterialInputBean());
            Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
            while (iter.hasNext()) {
                ApprovedMaterialInputBean bean = (ApprovedMaterialInputBean)iter.next();
                //get MSDS image from Maxcom and update content
                getMsdsImage(bean.getMaterialId(),bean.getRevisionDate(),bean.getLocaleCode(),docId,msdsId);
            }
        }catch (Exception ee) {
			StringBuilder esub = new StringBuilder("Error while processData for approved batch MSDS: ");
			String emsg = ee.getMessage();
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
    } //end of method

    public void manualSetDataEntryComplete() throws BaseException {
		try {
            dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("select material_id,revision_date,nvl(m.inserted_by,m.verified_by) creator_id from global.msds where document_id is not null and data_entry_complete = 'N'");
            genericSqlFactory.setBeanObject(new ApprovedMaterialInputBean());
            Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
            while (iter.hasNext()) {
                ApprovedMaterialInputBean bean = (ApprovedMaterialInputBean)iter.next();
                //set global data entry complete
                String globalDataEntryComplete = setDataGlobalEntryComplete(bean.getMaterialId(),bean.getRevisionDate(),bean.getCreatorId());
                if ("N".equals(globalDataEntryComplete)) {
                    StringBuilder esub = new StringBuilder("Global Data Entry Complete is not complete for material: ");
                    esub.append(bean.getMaterialId());
                    MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),esub.toString());
                }
            }
        }catch (Exception ee) {
			StringBuilder esub = new StringBuilder("Error while processData for approved material");
			String emsg = ee.getMessage();
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
    }

    private boolean dataCountIsZero(String query) {
		boolean result = false;
		try {
			String tmpVal = genericSqlFactory.selectSingleValue(query,connection);
			if ("0".equalsIgnoreCase(tmpVal)) {
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
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

    private void advanceCatalogQueue(MsdsIndexingProcess msdsIndexingProcess, CatalogAddItemBean catalogAddItemBean) throws Exception {
        StringBuilder query = new StringBuilder("select * from catalog_queue");
        query.append(" where catalog_add_request_id = ").append(catalogAddItemBean.getRequestId()).append(" and catalog_add_item_id = ").append(catalogAddItemBean.getCatalogAddItemId());
        query.append(" and task = 'SDS Indexing'");
        genericSqlFactory.setBeanObject(new CatalogQueueBean());
        Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
        while(iter.hasNext()) {
            CatalogQueueBean catalogQueueBean = (CatalogQueueBean)iter.next();
            CatalogAddReqMsdsInputBean catalogAddReqMsdsInputBean = new CatalogAddReqMsdsInputBean();
            catalogAddReqMsdsInputBean.setMaterialId(catalogQueueBean.getMaterialId());
            catalogAddReqMsdsInputBean.setRevisionDate(catalogQueueBean.getRevisionDate());
            catalogAddReqMsdsInputBean.setqId(catalogQueueBean.getqId());
            msdsIndexingProcess.advanceAssignedToApproved(catalogAddReqMsdsInputBean,new BigDecimal(-1),connection);
        }
    }   //end of method

    private void processCatalogAddForMaterial(BigDecimal materialId) throws Exception {
        MsdsIndexingProcess msdsIndexingProcess = new MsdsIndexingProcess(new BigDecimal(-1),getClient());
        //get all catalog add requests that currently pending indexing data
        StringBuilder query = new StringBuilder("select carn.company_id,carn.request_id,car.approval_role,cai.catalog_add_item_id");
        query.append(" from catalog_add_request_new carn, catalog_add_item cai, vv_catalog_add_request_status cars, vv_chemical_approval_role car");
        query.append(" where carn.company_id = cai.company_id and carn.request_id = cai.request_id");
        query.append(" and cai.material_id = ").append(materialId).append(" and carn.msds_indexing_status = 'Pending'");
        query.append(" and cai.msds_indexing_status = 'Pending'");
        query.append(" and carn.company_id = cars.company_id and carn.request_status = cars.request_status");
        query.append(" and carn.company_id = car.company_id and carn.catalog_company_id = car.catalog_company_id");
        query.append(" and carn.catalog_id = car.catalog_id and carn.eng_eval_facility_id = car.facility_id");
        query.append(" and car.approval_group = cars.approval_group and carn.approval_group_seq = car.approval_group_seq");
        query.append(" and car.role_function like 'Verify SDS Indexing%' and car.active = 'Y'");
        genericSqlFactory.setBeanObject(new CatalogAddItemBean());
        Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
        while(iter.hasNext()) {
            CatalogAddItemBean bean = (CatalogAddItemBean)iter.next();
            //update cai.msds_indexing_status for material_id
            query = new StringBuilder("update catalog_add_item set msds_indexing_status = 'Approved'");
            query.append(" where company_id = '").append(bean.getCompanyId()).append("' and request_id = ").append(bean.getRequestId());
            query.append(" and material_id = ").append(materialId);
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            //approve catalog_queue
            advanceCatalogQueue(msdsIndexingProcess,bean);
            //check to see if this request has any material that is still pending MSDS Indexing
            query = new StringBuilder("select count(*) from catalog_add_item where msds_indexing_status = 'Pending'");
            query.append(" and company_id = '").append(bean.getCompanyId()).append("' and request_id = ").append(bean.getRequestId());
            //if this is the last material approved by MSDS Indexing then approve this request
            if (dataCountIsZero(query.toString())) {
                query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(bean.getRequestId());
                query.append(" and company_id = '").append(bean.getCompanyId()).append("'");
                genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
                Vector<CatAddHeaderViewBean> dataC = (Vector)genericSqlFactory.selectQuery(query.toString());
                CatAddHeaderViewBean catAddHeaderBean = dataC.get(0);
                CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),calculateClientUrl(bean.getCompanyId()));
                process.setFactoryConnection(genericSqlFactory,connection);
                PersonnelBean personnelBean = new PersonnelBean();
                personnelBean.setPersonnelId(-1);
                process.approvalRequestFromSpecificPage(catAddHeaderBean,"MSDS Indexing",personnelBean);
                //
                query = new StringBuilder("update catalog_add_request_new set msds_indexing_status = 'Approved'");
                query.append(" where company_id = '").append(bean.getCompanyId()).append("' and request_id = ").append(bean.getRequestId());
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
        }
    } //end of method

    //this method this created to manually run can release all catalog adds that have DEC = Y and ID_ONLY = C
    public void releasingCatalogAddRequests() {
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            //get all catalog add requests that currently pending indexing data
            StringBuilder query = new StringBuilder("select carn.company_id,carn.request_id,car.approval_role, cai.material_id");
            query.append(" from catalog_add_request_new carn, catalog_add_item cai, vv_catalog_add_request_status cars, vv_chemical_approval_role car, global.msds m");
            query.append(" where carn.company_id = cai.company_id and carn.request_id = cai.request_id");
            query.append(" and carn.msds_indexing_status = 'Pending'");
            query.append(" and cai.msds_indexing_status = 'Pending'");
            query.append(" and carn.company_id = cars.company_id and carn.request_status = cars.request_status");
            query.append(" and carn.company_id = car.company_id and carn.catalog_company_id = car.catalog_company_id");
            query.append(" and carn.catalog_id = car.catalog_id and carn.eng_eval_facility_id = car.facility_id");
            query.append(" and car.approval_group = cars.approval_group and carn.approval_group_seq = car.approval_group_seq");
            query.append(" and car.role_function like 'Verify SDS Indexing%' and car.active = 'Y'");
            query.append(" and cai.material_id = m.material_id and m.data_entry_complete = 'Y' and m.id_only = 'C'");
            query.append(" and m.revision_date = fx_msds_most_recent_date (m.material_id)");
            //query.append(" and cai.material_id = 510835");
            query.append(" order by request_id");
            genericSqlFactory.setBeanObject(new CatalogAddItemBean());
            Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
            while(iter.hasNext()) {
                CatalogAddItemBean bean = (CatalogAddItemBean)iter.next();
                //update cai.msds_indexing_status for material_id
                query = new StringBuilder("update catalog_add_item set msds_indexing_status = 'Approved'");
                query.append(" where company_id = '").append(bean.getCompanyId()).append("' and request_id = ").append(bean.getRequestId());
                query.append(" and material_id = ").append(bean.getMaterialId());
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                //check to see if this request has any material that is still pending MSDS Indexing
                query = new StringBuilder("select count(*) from catalog_add_item where msds_indexing_status = 'Pending'");
                query.append(" and company_id = '").append(bean.getCompanyId()).append("' and request_id = ").append(bean.getRequestId());
                //if this is the last material approved by MSDS Indexing then approve this request
                if (dataCountIsZero(query.toString())) {
                    query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(bean.getRequestId());
                    query.append(" and company_id = '").append(bean.getCompanyId()).append("'");
                    genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
                    Vector<CatAddHeaderViewBean> dataC = (Vector)genericSqlFactory.selectQuery(query.toString());
                    CatAddHeaderViewBean catAddHeaderBean = dataC.get(0);
                    CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),calculateClientUrl(bean.getCompanyId()));
                    process.setFactoryConnection(genericSqlFactory,connection);
                    PersonnelBean personnelBean = new PersonnelBean();
                    personnelBean.setPersonnelId(-1);
                    process.approvalRequestFromSpecificPage(catAddHeaderBean,"MSDS Indexing",personnelBean);
                    //
                    query = new StringBuilder("update catalog_add_request_new set msds_indexing_status = 'Approved'");
                    query.append(" where company_id = '").append(bean.getCompanyId()).append("' and request_id = ").append(bean.getRequestId());
                    genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dbManager.returnConnection(connection);
            }catch (Exception ee){
                ee.printStackTrace();
            }
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
    } //end of method
} //end of class