package com.tcmis.ws.scanner.action;

import java.io.PrintWriter;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.JsonRequestResponseAction;
import com.tcmis.ws.scanner.process.DeliveryConfirmationScanProcess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/******************************************************************************
 *
 * @version 1.0
 ******************************************************************************/
public class DeliveryConfirmationScanAction extends JsonRequestResponseAction {

	public ActionForward executeAction(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response, String jsonString) throws BaseException {
	  String message;
	  
	  if(jsonString != null && jsonString.length() > 0) {
		log.debug("JSON String received: " + jsonString);  
    	DeliveryConfirmationScanProcess scanProcess = new DeliveryConfirmationScanProcess(this.getDbUser(request));
    	message = scanProcess.processData(getPathCompany(request), jsonString);
	  }else
		  message = "No data received";
	  
	  try{
		  PrintWriter pw = response.getWriter();
		  pw.write(message);
		  pw.close();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	  
	  return this.noForward;
  }
}