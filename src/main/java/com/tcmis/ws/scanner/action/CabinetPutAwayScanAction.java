package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.client.common.beans.CabinetPutAwayBean;
import com.tcmis.client.common.process.CabinetPutAwayProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.JsonRequestResponseAction;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.scanner.process.CabinetPutAwayScanProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/******************************************************************************
 *
 * @version 1.0
 ******************************************************************************/
public class CabinetPutAwayScanAction
    extends JsonRequestResponseAction {

	private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocale());
	
	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
	  String message;
	  
	  if(jsonString != null && jsonString.length() > 0) {
		log.debug("JSON String received: " + jsonString);  
		  
    	CabinetPutAwayScanProcess scanProcess = new CabinetPutAwayScanProcess(this.getDbUser(request));
    	message = scanProcess.processPutAwayJson(getPathCompany(request), jsonString);
	  }
	  else
		  message = "No data received";
	  
	  try{
		  PrintWriter pw = response.getWriter();
		  pw.write(message);
		  pw.close();
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  
	  return this.noForward;
  }
}