package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.client.common.beans.CabinetPutAwayBean;
import com.tcmis.client.common.process.CabinetPutAwayProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;

public class CabinetPutAwayScanProcess extends GenericProcess implements Runnable{
	 Log log = LogFactory.getLog(this.getClass());
	 private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	 private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());

	 Collection putAwayBeans;
		 
	 public CabinetPutAwayScanProcess(String client) {
		super(client);
	 }
	 
	 public CabinetPutAwayScanProcess(String client, Collection putAwayBeans) {
			super(client);
			this.putAwayBeans = putAwayBeans;
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
			 log.error("Error processing Cabinet Scan Put Away Scan token check " + e.getMessage(), e);
			 throw new BaseException(e);
		 }
		 
		 return false;	  
	 }
	 
	 public Collection getBeans(String jsonString) throws BaseException{
		 Collection beans = new Vector();
		 
		 try{
			 JSONObject jsonObject = new JSONObject(jsonString);
			 JSONArray deliveries = jsonObject.getJSONArray("deliveries");			 
			 
			 for(int i=0; i<deliveries.length(); i++){
				CabinetPutAwayBean bean = new CabinetPutAwayBean(deliveries.getJSONObject(i));
				beans.add(bean);
			 }
		 }
		 catch(Exception e){
			 throw new BaseException(e);
		 }
		 
		 
		 return beans;
	 }
	 
	 public String processPutAwayJson(String company, String jsonString){
		 String message = "OK";
		 
		 try{
    		if(isValidToken(company,jsonString)){
	    		Collection beans = getBeans(jsonString);
	    		
	    		if(beans != null && beans.size() > 0){
	    			// start new scan process in a separate thread to complete put away
	    			// return message to scanner after json string has been parsed
	    			CabinetPutAwayScanProcess putAwayProcess = new CabinetPutAwayScanProcess(client, beans);
	    			Thread thread = new Thread(putAwayProcess);
					thread.start();
	    		}
    		}
    		else
    			message = library.getString("tablet.sessionexpired");
    	}
    	catch(Exception e) {
             log.fatal("Error processing CabinetPutAwayScan " + e.getMessage(), e);
             message = library.getString("error.generic");
        }
		 
		 return message;
	 }
	 
	 public void run() {
		try {
			Thread.currentThread().sleep(10000);
			CabinetPutAwayProcess putAwayProcess = new CabinetPutAwayProcess(client);
			
			Collection msg = putAwayProcess.putAwayInventory(putAwayBeans,"Scanner");
		} catch(Exception e){
			log.fatal("Error processing CabinetPutAwayScan " + e.getMessage(), e);
		}
	 }
}
