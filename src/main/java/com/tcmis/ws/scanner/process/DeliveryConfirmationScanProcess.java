package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.util.Date;
import java.sql.Connection;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.client.common.beans.CabinetPutAwayBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.ws.scanner.beans.DeliveryConfirmationBean;

public class DeliveryConfirmationScanProcess extends GenericProcess implements Runnable{
	 Log log = LogFactory.getLog(this.getClass());
	 private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	 private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());

	 Collection inputBeans;
		 
	 public DeliveryConfirmationScanProcess(String client) {
		super(client);
	 }
	 
	 public DeliveryConfirmationScanProcess(String client, Collection inputBeans) {
			super(client);
			this.inputBeans = inputBeans;
		 }

	 public boolean isValidToken(String company, String jsonString) throws BaseException{
		 String loginId;
		 String token;
		 
		 try{
			 JSONObject jsonObject = new JSONObject(jsonString);
			 loginId = jsonObject.getString("loginId");
			 token = jsonObject.getString("token");
			 
			 String checkToken = this.factory.getFunctionValue("Pkg_token.fx_verify_tcmis_token",loginId, company, token);

			 if("OK".equals(checkToken))
					return true;					
		 }
		 catch(Exception e){
			 log.error("Error processing Delivery Confirmation Scan token check " + e.getMessage(), e);
			 throw new BaseException(e);
		 }
		 
		 return false;	  
	 }
	 
	 public Collection getBeans(String jsonString) throws BaseException{
		 Collection beans = new Vector();
		 try{
			 JSONObject jsonObject = new JSONObject(jsonString);
			 JSONArray deliveries = jsonObject.getJSONArray("Confirmations");
			 
			 for(int i=0; i<deliveries.length(); i++){
				DeliveryConfirmationBean bean = new DeliveryConfirmationBean(deliveries.getJSONObject(i));
				beans.add(bean);
			 }
         }catch(Exception e){
			 throw new BaseException(e);
		 }

		 return beans;
	 }

    public void confirmDelivery(Collection inputBeans) throws BaseException{
        DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager);
        Connection connection = dbManager.getConnection();
        StringBuilder okShipments = new StringBuilder("");
        StringBuilder errorShipments = new StringBuilder("");
        BigDecimal scannerPersonnelId = new BigDecimal(-1);
        try {
            Iterator iter = inputBeans.iterator();
            while (iter.hasNext()) {
                DeliveryConfirmationBean bean = (DeliveryConfirmationBean)iter.next();
                scannerPersonnelId = bean.getPersonnelId();
                StringBuilder query = new StringBuilder("insert into delivery_confirmation(company_id,pr_number,line_item,shipment_id,date_shipped,quantity_shipped,comments,confirmed_by,date_confirmed) ");
                query.append(" select company_id,pr_number,line_item,shipment_id,date_delivered date_shipped,sum(quantity) quantity_shipped,'Confirmation scan' comments,");
                query.append(bean.getPersonnelId()).append(" confirmed_by,").append(DateHandler.getOracleToDateFunction(bean.getConfirmationDate())).append(" date_confirmed ");
                query.append(" from issue i where i.shipment_id = ").append(bean.getShipmentId());
                query.append(" and not exists (select shipment_id from delivery_confirmation dc where i.company_id = dc.company_id and i.pr_number = dc.pr_number");
                query.append(" and i.line_item = dc.line_item and i.shipment_id = dc.shipment_id)");
                query.append(" group by company_id,pr_number,line_item,shipment_id,date_delivered");
                //continue to process the non-error data
                try {
                    factory.deleteInsertUpdate(query.toString(),connection);
                    if (StringHandler.isBlankString(okShipments.toString()))
                        okShipments.append(bean.getShipmentId());
                    else
                        okShipments.append(", ").append(bean.getShipmentId());
                }catch(Exception ee){
			        log.error("Error inserting data into delivery_confirmation " + ee.getMessage(), ee);
                    if (StringHandler.isBlankString(errorShipments.toString()))
                        errorShipments.append(bean.getShipmentId());
                    else
                        errorShipments.append(", ").append(bean.getShipmentId());
                }
            } //end of while

            //sending out email notification
            StringBuilder msg = new StringBuilder("");
            if (!StringHandler.isBlankString(okShipments.toString())) {
                msg.append("We successfully processed the following shipments: ").append(okShipments).append("\n\n");
            }

            //add errors shipments
            if (!StringHandler.isBlankString(errorShipments.toString())) {
                msg.append("Your delivery confirmation scan encountered an error for the following shipments: ");
                msg.append(errorShipments).append("\n\n").append("Please rescan the above shipments and upload again.  If the problem persists, contact your tcmIS CSR.");
            }

            if (!StringHandler.isBlankString(okShipments.toString()) || !StringHandler.isBlankString(errorShipments.toString())) {
                StringBuilder tmpQ = new StringBuilder("select nvl(fx_personnel_id_to_email(").append(scannerPersonnelId).append("),'deverror@tcmis.com') email_address from dual");
                String scannerEmail = factory.selectSingleValue(tmpQ.toString(),connection);
                MailProcess.sendEmail(scannerEmail,null,"deverror@tcmis.com","Delivery Confirmation Scan",msg.toString());
            }

        }catch(Exception e){
			 log.error("Unexpected error " + e.getMessage(), e);
		}finally{
            dbManager.returnConnection(connection);
            dbManager = null;
            factory = null;
        }
    }

    public void testRun() {
        try {
            Collection t = new Vector();
            DeliveryConfirmationBean bean = new DeliveryConfirmationBean();
            bean.setPersonnelId(new BigDecimal(86030));
            bean.setShipmentId(new BigDecimal(1694344));
            bean.setConfirmationDate(new Date());
            t.add(bean);
            bean = new DeliveryConfirmationBean();
            bean.setPersonnelId(new BigDecimal(86030));
            bean.setShipmentId(new BigDecimal(1943657));
            bean.setConfirmationDate(new Date());
            t.add(bean);
            this.confirmDelivery(t);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String processData(String company, String jsonString){
		 String message = "OK";
		 
		 try{
    		if(isValidToken(company,jsonString)){
                //JSON String received: {"loginId":"sskidmore","token":"0139EFA919F1681DE05400144F80B9CE","Confirmations":[{"ShipmentID":"2840632","UserID":"4023","ConfirmationDate":"22AUG2014T01:26PM"}]}
                Collection beans = getBeans(jsonString);
	    		
	    		if(beans != null && beans.size() > 0){
	    			// start new scan process in a separate thread to complete put away
	    			// return message to scanner after json string has been parsed
	    			DeliveryConfirmationScanProcess process = new DeliveryConfirmationScanProcess(client, beans);
	    			Thread thread = new Thread(process);
					thread.start();
	    		}
    		}
    		else
    			message = library.getString("tablet.sessionexpired");
    	}
    	catch(Exception e) {
             log.fatal("Error processing Delivery Confirmation " + e.getMessage(), e);
             message = library.getString("error.generic");
        }
		 
		 return message;
	 }
	 
	 public void run() {
		try {
			Thread.currentThread().sleep(1000);
			DeliveryConfirmationScanProcess myProcess = new DeliveryConfirmationScanProcess(client);
			myProcess.confirmDelivery(inputBeans);
		} catch(Exception e){
			log.fatal("Error processing Delivery Confirmation " + e.getMessage(), e);
		}
	 }
}
