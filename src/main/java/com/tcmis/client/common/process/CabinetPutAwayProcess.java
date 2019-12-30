package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.CabinetPutAwayBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for CabinetPutAwayProcess
 * @version 1.0
 * 
 *****************************************************************************/

public class CabinetPutAwayProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());

	public CabinetPutAwayProcess(String client) {
		super(client);
	}

	public CabinetPutAwayProcess(String client,String locale)  {
		super(client,locale);
	}
			
	public Collection getSearchData(WorkAreaSearchTemplateInputBean bean) throws BaseException, Exception {		
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CabinetPutAwayBean());
        
        StringBuilder query = new StringBuilder("SELECT * FROM table (pkg_confirm_put_away.fx_cabinet_put_away_search('");
		query.append(bean.getCompanyId()).append("', '");
		query.append(StringHandler.emptyIfNull(bean.getFacilityId())).append("','");
		query.append(StringHandler.emptyIfNull(bean.getReportingEntityId())).append("','");
        query.append(StringHandler.emptyIfNull(bean.getApplicationId())).append("','");
        query.append(StringHandler.emptyIfNull(bean.getAreaId())).append("','");
        query.append(StringHandler.emptyIfNull(bean.getBuildingId())).append("','");
        query.append(StringHandler.emptyIfNull(bean.getDeptId())).append("','");
        query.append(StringHandler.emptyIfNull(bean.getSearchArgument())).append("','");
		query.append(StringHandler.emptyIfNull(bean.getCriterion())).append("','");
		query.append(StringHandler.emptyIfNull(bean.getCriteria())).append("',");
		query.append(getSqlString(bean.getDateStart())).append(",");
		query.append(getSqlString(bean.getDateEnd())).append(",");
		query.append("'|'").append(",");
		query.append(getSqlString(bean.getPutAwayMethod()));
		query.append(")) ");
		query.append("ORDER BY application_desc, date_delivered, shipment_id");

		return factory.selectQuery(query.toString());
	}
	
	public Collection putAwayInventory(Collection<CabinetPutAwayBean> inputLines, PersonnelBean user) throws BaseException {
		Date timestamp = new Date();
		
		for (CabinetPutAwayBean inputBean : inputLines) {
			inputBean.setPutAwayBy(user.getPersonnelId());
			inputBean.setPutAwayDateTime(timestamp);
		}
		
		return putAwayInventory(inputLines, "Manual");
	}
	
	public Collection putAwayInventory(Collection<CabinetPutAwayBean> inputLines, String source) throws BaseException {
		Vector errorMessages = new Vector();
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory sqlFactory = new GenericSqlFactory(dbManager);
        Connection connection = dbManager.getConnection();
        Vector<String> processedCabShipments = new Vector();
		
		try{
			// get upload sequence to be used
			String query = "SELECT upload_sequence.nextval FROM dual";
			String uploadSequence = sqlFactory.selectSingleValue(query);
			String queryValues = "";
			
			int cabinetPutAwayCount = 0;
			int containerPutAwayCount = 0;
			int rowsInserted = 0;
	        StringBuilder errorMsg = new StringBuilder("");
			for (CabinetPutAwayBean inputBean : inputLines) {
                try {
                    if(inputBean.getContainerId() != null && !new BigDecimal(0).equals(inputBean.getContainerId())){
                        query = "INSERT INTO container_scan_put_away_stage (container_number, issue_id, receipt_id, data_source, upload_sequence ";
                        queryValues = ") VALUES(" + inputBean.getContainerId() + ", " + inputBean.getIssueId() + ", " + inputBean.getReceiptId() + ", '" + source + "', " + uploadSequence;

                        if(inputBean.hasPutAwayBy()){
                        	query += ", put_away_by";
                        	queryValues += ", " + inputBean.getPutAwayBy().toString();
                        }
                        
                        if(inputBean.hasPutAwayDateTime()){
                        	query += ", put_away_datetime";
                        	queryValues += ", " + DateHandler.getOracleToDateFunction(inputBean.getPutAwayDateTime());
                        }
                        
                        if(inputBean.hasPutAwayAcceptedBy()){
                        	query += ", put_away_accepted_by";
                        	queryValues += ", " + inputBean.getPutAwayAcceptedBy();
                        }
 
                        if (inputBean.hasCompanyId()){
                            query += ", company_id";
                            queryValues += ", '" + inputBean.getCompanyId() + "'";
                        }
                        
                        queryValues += ")";
                        query += queryValues;

                        rowsInserted = sqlFactory.deleteInsertUpdate(query,connection);

                        // only provide one error message since a generic message is used
                        if(errorMessages.size() < 1 && rowsInserted < 1)
                        {
                            errorMessages.add(library.getString("error.generic"));
                        }

                        containerPutAwayCount++;
                    }else{
                        // avoid duplicate uploads
                        if(!processedCabShipments.contains(inputBean.getShipmentId()+"-"+inputBean.getApplicationId())){
                            query = "INSERT INTO cabinet_scan_put_away_stage (shipment_id, application_id, data_source, upload_sequence ";
                            queryValues = ") VALUES(" + inputBean.getShipmentId() + ", " + inputBean.getApplicationId() + ", '" + source + "', " + uploadSequence;

                            if(inputBean.hasPutAwayBy()){
                            	query += ", put_away_by";
                            	queryValues += ", " + inputBean.getPutAwayBy().toString();
                            }
                            
                            if(inputBean.hasPutAwayDateTime()){
                            	query += ", put_away_datetime";
                            	queryValues += ", " + DateHandler.getOracleToDateFunction(inputBean.getPutAwayDateTime());
                            }
                            
                            if(inputBean.hasPutAwayAcceptedBy()){
                            	query += ", put_away_accepted_by";
                            	queryValues += ", " + inputBean.getPutAwayAcceptedBy();
                            }
     
                            if (inputBean.hasCompanyId()){
                                query += ", company_id";
                                queryValues += ", '" + inputBean.getCompanyId() + "'";
                            }
                            
                            queryValues += ")";
                            query += queryValues;

                            rowsInserted = sqlFactory.deleteInsertUpdate(query,connection);

                            // only provide one error message since a generic message is used
                            if(errorMessages.size() < 1 && rowsInserted < 1)
                            {
                                errorMessages.add(library.getString("error.generic"));
                            }

                            processedCabShipments.add(inputBean.getShipmentId()+"-"+inputBean.getApplicationId());
                            cabinetPutAwayCount++;
                        }
                    }
                }catch(Exception ee) {
                    errorMsg.append(query).append("\n");
                }
            }
			//if there any errors inserting data into table then send error email and don't process the data in this upload sequence
            if (errorMsg.length() > 0) {
                MailProcess.sendEmail("TCMIS.Support@HaasGroupIntl.com", null,"deverror@tcmis.com","Error inserting data into put away table(s)",errorMsg.toString());
            }else {
                // process records in staging table with the same upload sequence
                Collection inArgs = new Vector(1);
                inArgs.add(uploadSequence);

                Collection outArgs = new Vector(1);
                outArgs.add(new Integer(java.sql.Types.VARCHAR));

                Vector results = null;
                if(cabinetPutAwayCount > 0 || containerPutAwayCount > 0){
                    results = (Vector) sqlFactory.doProcedure(connection,"pkg_confirm_put_away.p_stage_and_process", inArgs, outArgs);
                    if(results.size()>0 && results.get(0) != null && !results.get(0).equals("OK")) {
                        MailProcess.sendEmail("TCMIS.Support@HaasGroupIntl.com", null,"deverror@tcmis.com","Error inserting data into put away table(s)",results.get(0).toString());
                        if(errorMessages.size() < 1)
                            errorMessages.add(library.getString("error.generic"));
                    }
                }
            }
        }catch(Exception e) {
			log.error("Error processing CabinetPutAwayProcess" + e.getMessage(), e);
			if(errorMessages.size() < 1)
				errorMessages.add(library.getString("error.generic"));
			//throw new BaseException(e);
		}finally{
            dbManager.returnConnection(connection);
            dbManager = null;
            sqlFactory = null;
        }
		
		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	
	public ExcelHandler getCabinetPutAwayExcelReport(WorkAreaSearchTemplateInputBean inputBean, Locale locale) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		getCabinetPutAwayExcelReport(pw,inputBean);
		return pw;
	}
	
	private void getCabinetPutAwayExcelReport(ExcelHandler pw, WorkAreaSearchTemplateInputBean inputBean) throws Exception {
		Collection<CabinetPutAwayBean> data = this.getSearchData(inputBean);
		pw.addTable();
		pw.addRow();

		int[] widths = {45, 0, 0};
		int[] types =  {0, 0, pw.TYPE_DATE};
		int[] aligns = {0, 0, 0};

		String[] headerkeys = {"label.workarea", "label.packinglist","label.datedelivered"};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		for (CabinetPutAwayBean member : data) {
			pw.addRow();

			pw.addCell(member.getApplicationDesc());
			pw.addCell(member.getShipmentId());
			pw.addCell(member.getDateDelivered());
		}
	}
	
	public String getPackingListFilterQuery(String paramStr, String putAwayMethod){
		StringBuffer shipmentQuery = new StringBuffer("");
		String countTypeQuery = "";
		
		try{
			String[] shipmentIds = paramStr.split("\\|");
			String[] applicationIds;
			String workAreaQuery;
			
			
			for(String shipmentId : shipmentIds){
				//subQuery = "(shipmentId = " + shipmentId + " and application_id in (";
				workAreaQuery = "";
				applicationIds = ((shipmentId.split("-"))[1]).split(",");			
				for(String applicationId : applicationIds){
					if(workAreaQuery.length() > 0)
						workAreaQuery += ",";
					workAreaQuery += applicationId;
				}
				
				if(shipmentQuery.length() > 0)
					shipmentQuery.append(" or ");
				shipmentQuery.append("(shipment_id = " + (shipmentId.split("-"))[0] + " and application_id in(" + workAreaQuery + "))");
			}
			
			if(putAwayMethod.equals("CONTAINER"))
				countTypeQuery = "count_type = 'CONTAINER' AND ";
			else if(putAwayMethod.equals("CABINET"))
				countTypeQuery = "count_type <> 'CONTAINER' AND ";
		} catch(Exception e){
			log.error("Error processing CabinetPutAwayProcess.getPackingListFilterQuery() " + e.getMessage(), e);
		}
		
		return countTypeQuery + "(" + shipmentQuery.toString() + ")";
	}
	
	/*public String getPackingListFilterQuery(String paramStr){
		StringBuffer query = new StringBuffer("");
		
		try{
			String[] subQueryParams = paramStr.split("\\|");
			String[] subQueryParam;
			String subQuery;

			for(String subQueryParamStr : subQueryParams){
				subQueryParam = subQueryParamStr.split("-");
				subQuery = "shipment_id = " + subQueryParam[0] + " and application_id = " + subQueryParam[1];
				
				if(subQueryParam[2].equals("CONTAINER"))
					subQuery += " and count_type = 'CONTAINER'";
				else
					subQuery += " and count_type <> 'CONTAINER'";
				
				if(query.length() > 0)
					query.append(" or ");
				
				query.append("(" + subQuery + ")");
			}
		} catch(Exception e){
			log.error("Error processing CabinetPutAwayProcess.getPackingListFilterQuery() " + e.getMessage(), e);
		}
		
		return query.toString();
	}*/
}
	
