package com.tcmis.client.seagate.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
import com.tcmis.client.catalog.beans.CatalogAddApprovalBean;
import com.tcmis.client.catalog.beans.NewChemUrlViewBean;
import com.tcmis.client.seagate.beans.CraProcessBean;
import com.tcmis.client.seagate.beans.SeagateChemApprovalStageBean;
import com.tcmis.common.admin.beans.*;
import com.tcmis.common.admin.process.*;
import com.tcmis.common.db.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.process.EngEvalProcess;

@SuppressWarnings("all")
public class CraProcess
    extends GenericProcess {

  private final static String TO_EMAIL = "deverror@haastcm.com";
  private final static String FROM_EMAIL = "seagate_cra@haastcm.com";
  private String payloadId;
  private ResourceLibrary library = null;
  GenericSqlFactory genericSqlFactory = null;
  DbManager dbManager = null;
  Connection connection = null;

  public CraProcess(String client) {
    super(client);
    library = new ResourceLibrary("seagate");
    dbManager = new DbManager(getClient(),this.getLocale());
    genericSqlFactory = new GenericSqlFactory(dbManager);
  }
  
  
  public void processSeagateReactivatedRequest() throws Exception {

	       String requestIds = "";
	   try {
	       //if sql returns something other than 0 (zero) for request_id, it means that this request was once approved/rejected from CRA
	       String query = "select carn.eng_eval_facility_id facility_id,carn.catalog_id,carn.catalog_company_id,carn.request_status,scas.radian_request_id,nvl(caa.request_id,'0') request_id"+
	                      " from seagate_chem_approval_stage scas,catalog_add_approval caa,catalog_add_request_new carn"+
	                      " where processed_status is null and radian_request_id is not null and lower(cra_status) = 'reactivated' and"+
	                      " scas.radian_request_id = carn.request_id and scas.radian_request_id = caa.request_id(+) and"+
	                      " caa.approval_role(+) = 'CRA'";
	       connection = dbManager.getConnection();
	       genericSqlFactory.setBean(new SeagateChemApprovalStageBean());

	       Vector<SeagateChemApprovalStageBean> seagateChemApprovalStageBeans = (Vector<SeagateChemApprovalStageBean>)genericSqlFactory.selectQuery(query,connection);
	   	
			 for (SeagateChemApprovalStageBean seagateChemApprovalStageBean : seagateChemApprovalStageBeans) {
	         String requestId = seagateChemApprovalStageBean.getRadianRequestId().toPlainString();
	         //if there is no record in catalog_add_approval and the carn.request_status is not 7 (rejected)
	         //then mark request for "Reactivated"
	         if ("0".equalsIgnoreCase(seagateChemApprovalStageBean.getRequestId().toPlainString()) && !"7".equalsIgnoreCase(seagateChemApprovalStageBean.getRequestStatus().toPlainString())) {
	           markCRARequestsAsProcessed(requestId,"Reactivated");
	         }else if ("7".equalsIgnoreCase(seagateChemApprovalStageBean.getRequestStatus().toPlainString())) {
	           //delete previously approved/rejected record from CRA
	        	genericSqlFactory.deleteInsertUpdate("delete from catalog_add_approval where approval_role = 'CRA' and request_id = "+requestId,connection);
	           //reset catalog_add_request_new.request_status = 5 (pending CRA)
	        	genericSqlFactory.deleteInsertUpdate("update catalog_add_request_new set request_status = 5,approval_group_seq=1 where request_id = "+requestId,connection);
	           markCRARequestsAsProcessed(requestId,"Reactivated");
	         }else {
	           //holding requests so I can approved parts from use_approval
	           requestIds += requestId + ",";
	         }
	       }
	       if (!StringHandler.isBlankString(requestIds)) {
	         //remove last "," commas
	         requestIds = requestIds.substring(0,requestIds.length()-1);
	         //approve parts from use_approval
	         setApprovalStatusForParts(requestIds,"approved");
	         //mark requests as processed
	         markCRARequestsAsProcessed(requestIds,"Processed");
	       }
	     }catch (Exception e) {
	       if (!StringHandler.isBlankString(requestIds)) {
	         //remove last "," commas
	         requestIds = requestIds.substring(0,requestIds.length()-1);
	         //mark requests as processed
	         markCRARequestsAsProcessed(requestIds,"Error");
	         MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,"JRun Scheduler","Error occurred while scheduler processing 'Reactivated' request: "+requestIds);
	       }
		   log.error("Error :" + e.getMessage());
	       throw e;
	     }finally {
	    	 dbManager.returnConnection(connection);
	    }
	   } //end of method
  
  
  public void processSeagateInactiveRequest() throws Exception {
	     String requestIds = "";
		try {
	       //if sql returns something other than 0 (zero) for request_id, it means that this request was once approved/rejected from CRA
	       //therefore, I'm not inserting a record into catalog_add_approval for it
	       String query = "select carn.eng_eval_facility_id facility_id,carn.catalog_id,carn.catalog_company_id,carn.request_status,scas.radian_request_id,nvl(caa.request_id,'0') request_id"+
	                      " from seagate_chem_approval_stage scas,catalog_add_approval caa,catalog_add_request_new carn"+
	                      " where processed_status is null and radian_request_id is not null and lower(cra_status) = 'inactive' and"+
	                      " scas.radian_request_id = carn.request_id and scas.radian_request_id = caa.request_id(+) and"+
	                      " caa.approval_role(+) = 'CRA'";
	       
	       connection = dbManager.getConnection();
	       genericSqlFactory.setBean(new SeagateChemApprovalStageBean());

	       Vector<SeagateChemApprovalStageBean> seagateChemApprovalStageBeans = (Vector<SeagateChemApprovalStageBean>)genericSqlFactory.selectQuery(query,connection);
	   	
			 for (SeagateChemApprovalStageBean seagateChemApprovalStageBean :seagateChemApprovalStageBeans) {
			 String radianRequestId = seagateChemApprovalStageBean.getRadianRequestId().toPlainString();
	         if ("0".equalsIgnoreCase(seagateChemApprovalStageBean.getRequestId().toPlainString())) {
	           //if 0 then add rejected request
	           rejectCRARequest(seagateChemApprovalStageBean.getCatalogId(),seagateChemApprovalStageBean.getCatalogCompanyId(),seagateChemApprovalStageBean.getFacilityId(),radianRequestId,"insert");
	         }else {
	           //if request is not at Ready to Order (9) status
	           if (!"9".equalsIgnoreCase(seagateChemApprovalStageBean.getRequestStatus().toPlainString())) {
	             //otherwise, set record as rejected from CRA
	             rejectCRARequest(seagateChemApprovalStageBean.getCatalogId(),seagateChemApprovalStageBean.getCatalogCompanyId(),seagateChemApprovalStageBean.getFacilityId(),radianRequestId, "update");
	           }
	         }
	         //holding requests so I can unapproved parts from use_approval
	         requestIds += radianRequestId+",";
	       }
	       if (!StringHandler.isBlankString(requestIds)) {
	         //remove last "," commas
	         requestIds = requestIds.substring(0,requestIds.length()-1);
	         //unapprove parts from use_approval
	         setApprovalStatusForParts(requestIds,"unapproved");
	         //mark requests as processed
	         markCRARequestsAsProcessed(requestIds,"Processed");
	       }
	     }catch (Exception e) {
	       if (!StringHandler.isBlankString(requestIds)) {
	         //remove last "," commas
	         requestIds = requestIds.substring(0,requestIds.length()-1);
	         //mark requests as processed
	         markCRARequestsAsProcessed(requestIds,"Error");
	         MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,"JRun Scheduler","Error occurred while scheduler processing 'Inactive' request: "+requestIds);
	       }

		   log.error("Error :" + e.getMessage());
	       throw e;
	     }finally {
		     dbManager.returnConnection(connection);
	    }
	   } //end of method
  
  void markCRARequestsAsProcessed(String requestIds,String processStatus) throws Exception {
	     try {
	    	 genericSqlFactory.deleteInsertUpdate("update seagate_chem_approval_stage set processed_date = sysdate,processed_status = '"+processStatus+"'"+
	                   " where radian_request_id in ("+requestIds+") and processed_status is null",connection);
	     }catch (Exception e) {
		   log.error("Error :" + e.getMessage());
	       throw e;
	     }
	   } //end of method
  
  void setApprovalStatusForParts(String requestIds, String status) throws Exception {
	     try {
	    	 String craStatus = "";
	    	 if ("approved".equals(status)) {
	    		 craStatus = "Reactivated";
	    	 }
	    	 else {
	    		 craStatus = "Inactive";
	    	 }
	    	 genericSqlFactory.deleteInsertUpdate("update use_approval set approval_status = '"+status+"' where (company_id,catalog_id,catalog_company_id,facility_id,application,fac_part_no)"+
	                   " in (select carn.company_id,carn.catalog_id,carn.catalog_company_id,carn.eng_eval_facility_id,caug.work_area,carn.cat_part_no from catalog_add_request_new carn, catalog_add_user_group caug"+
	                   " where carn.company_id = caug.company_id and carn.request_id = caug.request_id and carn.cat_part_no is not null and carn.request_id in ("+requestIds+") and" +
	                   " caug.work_area in (select scas.building_id || '/' || scas.location_id from seagate_chem_approval_stage scas where scas.cra_status = '"+craStatus+"' and caug.request_id = scas.radian_request_id))",connection);
	     }catch (Exception e) {
	       log.error("Error :" + e.getMessage());
	       throw e;
	     }
	   } //end of method
  
  void rejectCRARequest(String catalogId,String catalogCompanyId,String facilityId, String requestId, String type) throws Exception {
	     try {
	       //first set catalog_add_request_new.request_status to rejected
	    	 genericSqlFactory.deleteInsertUpdate("update catalog_add_request_new set request_status = 7,approval_group_seq = 0 where request_id = " + requestId,connection);
	       if ("insert".equalsIgnoreCase(type)) {
	         //next insert rejected record in catalog_add_approval
	    	  genericSqlFactory.deleteInsertUpdate("insert into catalog_add_approval(request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id)" +
	                     " values(" + requestId + ",-1,sysdate,'Rejected',null,'CRA','" + facilityId + "','" + catalogId + "','"+catalogCompanyId+"')",connection);
	       }else {
	    	  genericSqlFactory.deleteInsertUpdate("update catalog_add_approval set status = 'Rejected',approval_date = sysdate,reason = null where approval_role = 'CRA' and request_id = "+requestId,connection);
	       }
	     }catch (Exception e) {
	    	log.error("Error :" + e.getMessage());
	       throw e;
	     }
	   } //end of method
  
  //processing "Active" record from seagate_chem_approval_stage (i.e. every 30 min. - JRun scheduler)
public void processSeagateNewChemApproval() throws Exception {
   //first get all unprocess request
	 try {
		   String query = "select x.REQUESTOR_ID,decode(x.FACILITY_ID,'NORMANDALE','NRM','SHAKOPEE','SHAK','PITTSBURGH','WATERFRONT','FREMONT',x.building_id,'MILPITAS',x.building_id,'FREMONT RESEARC','FRC-01',x.facility_id) facility_id,x.FACILITY_DESC,x.BUILDING_ID,x.BUILDING_DESC,"+
		                  "x.PROCESS_DESC,x.REQUESTER_DEPT_ID,x.CAT_PART_NO,x.CHEMICAL_NAME,x.SEAGATE_MSDS_NUMBER,"+
		                  "x.MSDS_TRADE_NAME,x.CONTAINER_TYPE,x.UNIT_OF_MEASURE,"+
		                  "x.CONTAINER_SIZE,x.KIT_FLAG,x.RADIAN_REQUEST_ID,x.SEAGATE_REQUEST_ID,x.LOCATION_ID,"+
		                  "x.LOCATION_DESC,x.PROCESSED_DATE,x.REQUESTOR_ID,x.SEAGATE_REQUEST_ID "+
		                  "from seagate_chem_approval_stage x "+
		                  "where ((processed_status is null and lower(cra_status) = 'active') or (processed_status = 'Reactivated' and lower(cra_status) = 'reactivated'))"+
		                  " and radian_request_id is not null order by radian_request_id";
		   		  connection = dbManager.getConnection();
		   	    genericSqlFactory.setBean(new SeagateChemApprovalStageBean());
				   Vector<SeagateChemApprovalStageBean> seagateChemApprovalStageBeans = (Vector<SeagateChemApprovalStageBean>)genericSqlFactory.selectQuery(query,connection);
				   //next check and update data
				   String facID = "";
				   Vector applicationV = new Vector();
				   String processDesc = "";
				   String catPartNo = "";
				   String chemicalName = "";
				   String seagateMSDSNumber = "";
				   String containerType = "";
				   String containerSize = "";
				   String unitOfMeasure = "";
				   String startingView = "";
				    Hashtable ftpData = new Hashtable();
				   //for each request compare requested data with ftp data
					for (SeagateChemApprovalStageBean seagateChemApprovalStageBean :seagateChemApprovalStageBeans) {
						  String currentRequestId = seagateChemApprovalStageBean.getRadianRequestId().toPlainString();
						  if (ftpData.containsKey(currentRequestId)) {
							  Vector requestDataV = (Vector)ftpData.get(currentRequestId);
							  Hashtable h = new Hashtable();
							  h.put("REQUEST_ID",StringHandler.emptyIfNull(currentRequestId));
							  h.put("PROCESS_DESC",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getProcessDesc()));
							  h.put("CHEMICAL_NAME",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getChemicalName()));
							  h.put("SEAGATE_MSDS_NUMBER",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getSeagateMsdsNumber()));
							  h.put("CAT_PART_NUMBER",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getCatPartNo()));
							  h.put("FACILITY_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getFacilityId()));
							  h.put("BUILDING_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getBuildingId()));
							  h.put("LOCATION_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getLocationId()));
							  h.put("CONTAINER_TYPE",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getContainerType()));
							  h.put("CONTAINER_SIZE",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getContainerSize()));
							  h.put("UNIT_OF_MEASURE",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getUnitOfMeasure()));
							  h.put("SEAGATE_REQUESTOR_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getRequestorId()));
							  h.put("SEAGATE_REQUEST_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getSeagateRequestId()));
							  requestDataV.addElement(h);
						  }else {
							  Vector requestDataV = new Vector();
							  Hashtable h = new Hashtable();
							  h.put("REQUEST_ID",StringHandler.emptyIfNull(currentRequestId));
							  h.put("PROCESS_DESC",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getProcessDesc()));
							  h.put("CHEMICAL_NAME",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getChemicalName()));
							  h.put("SEAGATE_MSDS_NUMBER",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getSeagateMsdsNumber()));
							  h.put("CAT_PART_NUMBER",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getCatPartNo()));
							  h.put("FACILITY_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getFacilityId()));
							  h.put("BUILDING_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getBuildingId()));
							  h.put("LOCATION_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getLocationId()));
							  h.put("CONTAINER_TYPE",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getContainerType()));
							  h.put("CONTAINER_SIZE",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getContainerSize()));
							  h.put("UNIT_OF_MEASURE",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getUnitOfMeasure()));
							  h.put("SEAGATE_REQUESTOR_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getRequestorId()));
							  h.put("SEAGATE_REQUEST_ID",StringHandler.emptyIfNull(seagateChemApprovalStageBean.getSeagateRequestId()));
							  requestDataV.addElement(h);
							  ftpData.put(currentRequestId,requestDataV);
						  }
					}
					
			
				  Enumeration enu = ftpData.keys();
				   while (enu.hasMoreElements()) {
						String reqID = (String)enu.nextElement();
						BigDecimal bdReqID = new BigDecimal(reqID);
				    try {
					     boolean approvedByQC = Integer.parseInt(genericSqlFactory.selectSingleValue("select count(*) from catalog_add_approval where approval_role = 'QC' and request_id = "+reqID,connection)) > 0;
				 
					       //set the process date to sysdate
					       query = "update seagate_chem_approval_stage set processed_date = sysdate where processed_date is null and radian_request_id = "+reqID;
					       genericSqlFactory.deleteInsertUpdate(query, connection);
					
					       query = "select * from new_chem_url_view where request_id = "+reqID;
					       genericSqlFactory.setBean(new NewChemUrlViewBean());
					 	  Vector<NewChemUrlViewBean> newChemUrlViewBeans = (Vector<NewChemUrlViewBean>)genericSqlFactory.selectQuery(query,connection);
					
							  for(NewChemUrlViewBean newChemUrlViewBean : newChemUrlViewBeans){
								 //the reason for this is that the work area is different
								 if (applicationV.size() == 0) {
									 facID = newChemUrlViewBean.getTcmFacilityId();
									 processDesc = newChemUrlViewBean.getProcessDesc();
									 catPartNo = newChemUrlViewBean.getCatPartNo();
									 chemicalName = newChemUrlViewBean.getMfgTradeName();
									 seagateMSDSNumber = newChemUrlViewBean.getCustomerMsdsNumber();
									 containerSize = newChemUrlViewBean.getPartSize();
									 unitOfMeasure = newChemUrlViewBean.getSizeUnit();
									 containerType = newChemUrlViewBean.getPkgStyle();
									 startingView = newChemUrlViewBean.getStartingView().toPlainString();
								 }
								 applicationV.addElement(newChemUrlViewBean.getApplication());
							  }
					       //compare the data
							  String ftpFacID = "";
					       String ftpCatPartNo = "";
					       String ftpChemicalName = "";
					       String ftpSeagateMSDSNumber = "";
					       String ftpContainerType = "";
					       String ftpContainerSize = "";
					       String ftpUnitOfMeasure = "";
					       String ftpSeagateRequestID = "";
							  Vector tmpRequestDataV = (Vector)ftpData.get(reqID);
							  Vector ftpApplicationV = new Vector(tmpRequestDataV.size());
							  for (int kk = 0; kk < tmpRequestDataV.size(); kk++) {
								  //the reason for this is because all these data are the same, only work area and process desc (possibly) are different
								  Hashtable h = (Hashtable)tmpRequestDataV.elementAt(kk);
								  if (kk == 0) {
									  ftpFacID = (String)h.get("FACILITY_ID");
									  ftpCatPartNo = (String)h.get("CAT_PART_NUMBER");
									  ftpChemicalName = (String)h.get("CHEMICAL_NAME");
									  ftpSeagateMSDSNumber = (String)h.get("SEAGATE_MSDS_NUMBER");
									  ftpContainerType = (String)h.get("CONTAINER_TYPE");
									  ftpContainerSize = (String)h.get("CONTAINER_SIZE");
									  ftpUnitOfMeasure = (String)h.get("UNIT_OF_MEASURE");
									  ftpSeagateRequestID = (String)h.get("SEAGATE_REQUEST_ID");
								  }
								  String ftpProcessDesc = (String)h.get("PROCESS_DESC");
								  String ftpApplication = (String)h.get("BUILDING_ID")+"/"+(String)h.get("LOCATION_ID");
					
								  if (!ftpFacID.equalsIgnoreCase(facID) || !applicationV.contains(ftpApplication)) {
									 int count =  Integer.parseInt(genericSqlFactory.selectSingleValue("select count(*) from fac_loc_app where facility_id = '"+ftpFacID+"' and application = '"+ftpApplication+"'",connection));
									 if (count == 0) {
										query = "update seagate_chem_approval_stage set processed_status = 'Error' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
										genericSqlFactory.deleteInsertUpdate(query,connection);
										MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,"New Chemical add for Seagate (request_id: "+reqID+" - work area: "+ftpApplication+") Unknown Facility/Work Area","User requested a new chemical add to an unknown facility/work area"); 
										continue;
									 }else {
									 	//create a new record for work areas that was added from CRA
									   query = "insert into catalog_add_user_group (request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2,approval_status)";
									   query += " select request_id,'"+ftpFacID+"','"+ftpApplication+"',user_group_id,'"+ftpProcessDesc+"',";
									   query += "quantity_1,per_1,period_1,quantity_2,per_2,period_2,'approved' from catalog_add_user_group where request_id = "+reqID+" and rownum < 2";
										genericSqlFactory.deleteInsertUpdate(query,connection);
									 }
								  }else {
									 //if user change process_desc
									 if (!ftpProcessDesc.equalsIgnoreCase(processDesc)) {
										query = "update catalog_add_user_group set process_desc = '"+ftpProcessDesc+"' where facility_id = '"+ftpFacID+"' and work_area = '"+ftpApplication+"' and request_id = "+reqID;
										genericSqlFactory.deleteInsertUpdate(query,connection);
									 }
								  }
								  ftpApplicationV.addElement(ftpApplication);
							  }
							  //now delete work areas that was deleted from CRA
							  for (int jj = 0; jj < applicationV.size(); jj++) {
								  String tmpApp = (String)applicationV.elementAt(jj);
								  if (!ftpApplicationV.contains(tmpApp)) {
								  	query = "delete from catalog_add_user_group where request_id = "+reqID+" and facility_id = '"+facID+"' and work_area = '"+tmpApp+"'";
								  	genericSqlFactory.deleteInsertUpdate(query,connection);
								  }
							  }
					
							  //if user change cat_part_number - catalog_add_request_new
					       if (!ftpCatPartNo.equalsIgnoreCase(catPartNo) && !StringHandler.isBlankString(ftpCatPartNo)) {
					         if (StringHandler.isBlankString(catPartNo)) {
					           query = "update catalog_add_request_new set cat_part_no = " + "'" + ftpCatPartNo + "' where request_id = " + reqID;
					           genericSqlFactory.deleteInsertUpdate(query,connection);   
					         }else {
									if (!catPartNo.equalsIgnoreCase(ftpCatPartNo)) {
										//set the process error date to sysdate so we can tell whether or not we process this request or not
					           	query = "update seagate_chem_approval_stage set processed_status = 'Error' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
					            genericSqlFactory.deleteInsertUpdate(query,connection);
					            MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,"New Chemical add for Seagate (request_id: "+reqID+")","cat_part_no changed in CRA");  //During test I go ahead and send this to me (later on Ronnie)
									}
									return;
					         }
					       }
					
					       //if user change other field in catalog_add_item
					       String message = "";
					
					       if (!ftpSeagateMSDSNumber.equalsIgnoreCase(seagateMSDSNumber)) {
					        query = "update catalog_add_item set customer_msds_number = '" + ftpSeagateMSDSNumber + "' where line_item = 1 and request_id = " + reqID + " and part_id = 1";
					        genericSqlFactory.deleteInsertUpdate(query,connection);   
					       }
					       if (!ftpContainerSize.equalsIgnoreCase(containerSize)) {
					         if (!approvedByQC) {
					           query = "update catalog_add_item set part_size = "  + ftpContainerSize + " where line_item = 1 and request_id = " + reqID + " and part_id = 1";
					           genericSqlFactory.deleteInsertUpdate(query,connection);
					
					         }else {
					           message += "\nOur part_size = "+containerSize+"\nCRA part_size = "+ftpContainerSize;
					         }
					       }
					       if (!ftpUnitOfMeasure.equalsIgnoreCase(unitOfMeasure)) {
					         query = "select count(*) from cont_size_size_unit_link where unit_code = "+ftpUnitOfMeasure;
					         if (Integer.parseInt(genericSqlFactory.selectSingleValue(query,connection)) > 0) {
					           if (("0".equalsIgnoreCase(startingView) || "1".equalsIgnoreCase(startingView)) && !approvedByQC ) {
					        	   genericSqlFactory.deleteInsertUpdate("update catalog_add_item set size_unit = (select VV_SIZE_UNIT from cont_size_size_unit_link where preferred = 'y' and unit_code = "+ftpUnitOfMeasure+") where request_id = "+reqID,connection);
					           }else {
					             message += "\nOur size_unit: "+unitOfMeasure+"\nCRA size_unit = "+ftpUnitOfMeasure;
					           }
					         }else {
					           message += "\nOur size_unit: "+unitOfMeasure+"\nCRA size_unit = "+ftpUnitOfMeasure;
					         }
					       }
					       if (!ftpContainerType.equalsIgnoreCase(containerType)) {
					         query = "select count(*) from cont_type_pkg_style_link where vv_pkg_style = '"+ftpContainerType+"'";
					         if (Integer.parseInt(genericSqlFactory.selectSingleValue(query,connection)) > 0) {
					           if (("0".equalsIgnoreCase(startingView) || "1".equalsIgnoreCase(startingView)) && !approvedByQC) {
					             query = "update catalog_add_item set pkg_style = '" + ftpContainerType + "' where line_item = 1 and request_id = " + reqID + " and part_id = 1";
					             genericSqlFactory.deleteInsertUpdate(query,connection);        
					           }else {
					             message += "\nOur pkg_style: "+containerType+"\nCRA pkg_style = "+ftpContainerType;
					           }
					         }else {
					           message += "\nOur pkg_style: "+containerType+"\nCRA pkg_style = "+ftpContainerType;
					         }
					       }
					       //12-10-01 ignore CRA changes for this field, 4-15-02 need to update this field
					       if (!ftpChemicalName.equalsIgnoreCase(chemicalName)) {
					         if (!approvedByQC) {
					           query = "update catalog_add_item set mfg_trade_name = '" + ftpChemicalName + "' where line_item = 1 and request_id = " + reqID + " and part_id = 1";
					           genericSqlFactory.deleteInsertUpdate(query,connection);   
					         }else {
					           message += "\nOur mfg_trade_name: "+chemicalName+"\nCRA mfg_trade_name = "+ftpChemicalName;
					         }
					       }
					
					       //now set the processed status to okay
					       query = "update seagate_chem_approval_stage set processed_status = 'Processed' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
					       genericSqlFactory.deleteInsertUpdate(query,connection);
					
					       //add approval record for CRA
					       query = "insert into catalog_add_approval (company_id,request_id,facility_id,catalog_id,catalog_company_id,approval_role,chemical_approver,status,approval_date,reason)"+
					             " select company_id,request_id,'"+ftpFacID+"',catalog_id,catalog_company_id,'CRA',-1,'Approved',sysdate,'Auto approved by tcmIS' from catalog_add_request_new where request_id = "+reqID+
					             " and not exists(select * from catalog_add_approval where request_id = "+reqID+" and approval_role='CRA')";
					       genericSqlFactory.deleteInsertUpdate(query,connection);
					
					       //set seagate request id
					       if (!StringHandler.isBlankString(ftpSeagateRequestID)) {
					    	   genericSqlFactory.deleteInsertUpdate("update catalog_add_request_new set customer_request_id = " + ftpSeagateRequestID + " where request_id = " + reqID,connection);
					       }
					
					       //send email to if data is different from cra
					       if (message.length() > 0) {
					         query = "select distinct email from personnel where personnel_id in (select personnel_id from user_group_member where user_group_id = 'CRANofication' and facility_id = 'All')";
					         genericSqlFactory.setBean(new PersonnelBean());
					         Vector<PersonnelBean> personnelBeans = (Vector<PersonnelBean>)genericSqlFactory.selectQuery(query,connection);
					         for(PersonnelBean personnelBean:personnelBeans)
					        	 if(!StringHandler.isBlankString(personnelBean.getEmail()))
					        			 MailProcess.sendEmail(personnelBean.getEmail(), "", FROM_EMAIL,"CRA data is different for request: "+reqID,message);
					       }
					
					       //set next status and send email
					       //NewChemApproval nca = new NewChemApproval(db);
					       EngEvalProcess engEvalProcess = new EngEvalProcess("SEAGATE", "EN-US","https://www.tcmis.com/tcmIS/"+getClient());
					       engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
					       String[] result = engEvalProcess.setNextStatus(bdReqID);
					       String done = result[0];
					       String eval = result[1];
					       String nextGroup = result[2];
					       //send email to user if the end of approval process is reach.  Otherwise, continue to the next approval group
					       if ("Done".equalsIgnoreCase(done)) {
					         //first check to see if tcmIS need to generate a part_no
					         //if request doesn't contains a part_no
					         if (Integer.parseInt(genericSqlFactory.selectSingleValue("select count(*) from catalog_add_request_new where (cat_part_no is null or vsize(cat_part_no) < 2 or ascii(cat_part_no) = 160 or cat_part_no like '%?%') and request_id = "+reqID,connection)) > 0) {
					           Calendar cal = Calendar.getInstance();
					           Integer temp = new Integer(cal.get(cal.YEAR));
					           String partNo = "";
					           if ("FEC".equalsIgnoreCase(getClient())) {
					             partNo = temp.toString().substring(3)+getClient().toUpperCase()+"-";
					           }else {
					             partNo = temp.toString().substring(2)+getClient().toUpperCase()+"-";
					           }
					           Integer partSeq = new Integer(genericSqlFactory.selectSingleValue("select part_seq.nextval from dual",connection));
					           partNo += partSeq.toString();
					           genericSqlFactory.deleteInsertUpdate("update catalog_add_request_new set cat_part_no = '"+partNo+"' where request_id = "+reqID,connection);
					         }
					         //add part to catalog
					         try {
					           //call store procedure to add part to catalog
					           Vector in = new Vector();
					           in.addElement(reqID);
					           Vector out = new Vector(1);
						   	   out.add(new Integer(java.sql.Types.VARCHAR));
						   	   String val = getProcError(connection,in, null,"p_add_cat_part_request");  

					           if (!StringHandler.isBlankString(val) && !val.startsWith("OK")) {
					             String esub = "Error while calling procedure to add part to catalog (request #"+reqID+")";
					             String emsg = "Procedure: p_add_cat_part has an error.\n";
					             emsg += val;
					        	 MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,esub,emsg);
					           }
					         }catch (Exception ee) {
					        	log.error("Error :" + ee.getMessage());
					           String esub = "Error while processing CRA request (request #"+reqID+")";
					           String emsg = "Procedure: p_add_cat_part throw an exception.\n";
					           MailProcess.sendEmail(TO_EMAIL, "", FROM_EMAIL,esub,emsg);
					         }
					         engEvalProcess.sendUserConfirmedEmail(bdReqID);
					       }else {
					         //sending email to the next group/group seq
					         if (nextGroup.equalsIgnoreCase("New group")) {
					        	 engEvalProcess.sendApproversEmail(bdReqID,"",new Vector(0),new Vector(0));
				
					         }
					       }
					   }catch (Exception ee) {
						    log.error("Error :" + ee.getMessage());
					        //set the process error date to sysdate so we can tell whether or not we process this request or not
					        query = "update seagate_chem_approval_stage set processed_status = 'Error' where (processed_status is null or processed_status = 'Reactivated') and radian_request_id = "+reqID;
					        genericSqlFactory.deleteInsertUpdate(query,connection);
					        ee.printStackTrace();
					        throw ee;
					      }
					 }	
		}catch (Exception ee) {
			log.error("Error :" + ee.getMessage());
		    throw ee;
		  }
		finally
		{
			dbManager.returnConnection(connection);
		}
		 
}


}