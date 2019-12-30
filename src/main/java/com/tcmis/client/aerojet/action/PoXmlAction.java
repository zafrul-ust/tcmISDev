package com.tcmis.client.aerojet.action;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.aerojet.process.PoXmlProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ResourceLibrary;

public class PoXmlAction extends TcmISBaseAction {

	static String urlhost = "https://www.tcmis.com/" ; //"http://haas-4djz3x1/";//

	static { 
	    try {
		ResourceLibrary lconfig = new ResourceLibrary("tcmISWebResource");
		if( lconfig != null ) {
			String hostname = lconfig.getString("hosturl");
			if( hostname != null && hostname.length() != 0 )
				urlhost = hostname;
		}
	    }catch(Exception ex){}
	}
	
	
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {
		
		String xmlString = request.getParameter("PAYLOAD");

		//for ( String key: (java.util.Set<String>) request.getParameterMap().keySet() ) {
		//	log.debug(key+":"+request.getParameter(key));
		//}
		
		try {
			String address = "deverror@tcmis.com";
			//log.debug("xmlString Before:" + xmlString);
			xmlString  = URLDecoder.decode(xmlString, "UTF-8");
			log.debug("xmlString length:" + xmlString.length());
			//MailProcess.sendEmail(address, "", "deverror@haastcm.com", "Received Aerojet PO XML doc ", "DOC:\n" + xmlString);
		} catch (Exception ex) {
			log.error("error sending mail:" + ex.getMessage());
		}
	    
	    if(xmlString != null && xmlString.length() > 0) {
	    	try {
	        	//StringBuffer requestURL = request.getRequestURL();
	        	//String emailUrl = requestURL.substring(0,requestURL.lastIndexOf("/")) + "/poxml.do?";
	        	//emailUrl = emailUrl.replaceFirst("^.*//[^/]+/", urlhost);//"https://www.tcmis.com/");
	        	
	        	//logging incoming URI
	        	log.debug(request.getRequestURI()+((request.getQueryString()==null)?"":"?"+request.getQueryString()));
	        	
	        	PoXmlProcess process = new PoXmlProcess(this.getDbUser(request));

	            String result = process.processPoXml(getPathCompany(request),xmlString);
	            
	            //TODO: Comment out after testing
	        	log.debug(result);
				//TODO: Comment out after testing
				
	            PrintWriter writer = response.getWriter();
	            writer.write(result);
	            writer.close();
	        }
	        catch(Exception e) {
	            e.printStackTrace();
	            log.fatal("Error processing PoXml" + e.getMessage(), e);
	        }
	    }
	    return this.noForward; 
	  }
	
}
