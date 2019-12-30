package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.ws.scanner.process.CabinetCountProcess;
import com.tcmis.ws.scanner.process.ManualCabinetCountProcess;

/******************************************************************************
 * Process for Receipt Document
 * 
 * @version 1.0
 *****************************************************************************/

public class ForceCabinetCountProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ForceCabinetCountProcess(String client, Locale locale) {
		super(client, locale);
	}

	public String doUpload(String binIdsScannedList, BigDecimal personnelId,BigDecimal upLoadSeq) throws BaseException {
        if (upLoadSeq != null) {
            DbManager dbManager = new DbManager(getClient());
            Connection connection = null;
            String errorMessage = "";
            ResourceLibrary messages = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
            ResourceLibrary resource = new ResourceLibrary("scannerupload");
            BulkMailProcess bulkMailProcess = new BulkMailProcess(getClient());
            try {
                connection = dbManager.getConnection();
                GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
                HashMap createMrResult = new HashMap();
                String mrsCreated = "";
                Boolean createMrSuccess = new Boolean(false);
                try {
                    String query = "select distinct company_id from cabinet_inventory_count where date_processed is null and upload_sequence = "+upLoadSeq;
                    String companyId = genericSqlFactory.selectSingleValue(query,connection);
                    if (!StringHandler.isBlankString(companyId)) {
                        com.tcmis.ws.scanner.process.CabinetCountProcess countProcess = new CabinetCountProcess(getClient());
                        countProcess.setConnection(connection);
                        createMrResult = countProcess.createMaterialRequestForReceiptCount(upLoadSeq,companyId, personnelId);
                        createMrSuccess = (Boolean) createMrResult.get("SUCESS");
                        if (!createMrSuccess.booleanValue()) {
                            errorMessage += (String) createMrResult.get("ERROR");
                        }
                        mrsCreated = (String) createMrResult.get("CREATEDMRLIST");
                    }
                }catch (Exception ex2) {
                    errorMessage += ex2.getMessage();
                }

                //Create Item Count MRs.
                try{
                    String query = "select distinct company_id from (select company_id from cabinet_item_inventory_count where date_processed is null and upload_sequence = "+upLoadSeq+
                                   " union all select company_id from work_area_bin_count where date_processed is null and upload_sequence = "+upLoadSeq+")";
                    String companyId = genericSqlFactory.selectSingleValue(query,connection);
                    if (!StringHandler.isBlankString(companyId)) {
                        com.tcmis.ws.scanner.process.ManualCabinetCountProcess countByItemProcess = new ManualCabinetCountProcess(getClient());
                        countByItemProcess.setConnection(connection);
                        createMrResult = countByItemProcess.createMaterialRequestForItemCount(companyId, personnelId,upLoadSeq);
                        createMrSuccess = (Boolean) createMrResult.get("SUCESS");
                        if (!createMrSuccess.booleanValue()) {
                            errorMessage += (String) createMrResult.get("ERROR");
                        }
                        mrsCreated += (String) createMrResult.get("CREATEDMRLIST");
                    }
                }catch (Exception ex2) {
                    errorMessage += ex2.getMessage();
                }

                String wwwHome = resource.getString("upload.hosturl");
                String resultUrl = wwwHome + resource.getString("upload.cabinetcountresult");
                String message = messages.getString("letter.cabinetscannerupload") + resultUrl + "UserAction=Search&mrslist=" + mrsCreated + "\n\n\n" +  messages.getString("label.uploadsequence") + " : " + upLoadSeq + "\n\n\n" +  messages.getString("label.personnelid") + " : " + personnelId;
                bulkMailProcess.sendBulkEmail(personnelId, messages.getString("letter.cabinetscanneruploadtitle") , message, true);
                bulkMailProcess.sendBulkEmail(new BigDecimal("86405"), messages.getString("letter.cabinetscanneruploadtitle"), message + "\n\n" +  messages.getString("label.scannedbins")  + binIdsScannedList.toString(), true);

                //update date_processed
                Vector inArgs = new Vector(1);
                inArgs.add(upLoadSeq);
                Vector outArgs = new Vector(1);
                outArgs.add(new Integer(java.sql.Types.VARCHAR));

                Vector error = (Vector) genericSqlFactory.doProcedure(connection,"pkg_work_area_bin_count.P_PROCESS_COUNT", inArgs, outArgs);
                if (error.size() > 1 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
                    MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","ManualCabinetCountProcess Error encountered while calling pkg_work_area_bin_count.P_PROCESS_COUNT:"+upLoadSeq,(String)error.get(0));
                }
                error = (Vector) genericSqlFactory.doProcedure(connection,"pkg_cabinet_count.p_process_cabinet_rcpt_inv_ct", inArgs, outArgs);
                if (error.size() > 1 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
                    MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","ManualCabinetCountProcess Error encountered while calling pkg_cabinet_count.p_process_cabinet_rcpt_inv_ct:"+upLoadSeq,(String)error.get(0));
                }
            }catch (Exception e) {
                errorMessage = e.getMessage();
            }finally {
                dbManager.returnConnection(connection);
            }

            return errorMessage;
		}else {
			return "No Bins to process";
		}
	} //end of method
} //end of class
