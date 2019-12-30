package com.tcmis.client.aerojet.process;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.actuate.ereport.engine.expreval.functions.DateTime;
import com.tcmis.client.aerojet.beans.AddressBean;
import com.tcmis.client.aerojet.beans.DateTimeBean;
import com.tcmis.client.aerojet.beans.LineItemBean;
import com.tcmis.client.aerojet.beans.PurchaseOrderBean;
import com.tcmis.client.aerojet.beans.ScheduleLineItemBean;
import com.tcmis.client.order.beans.CustomerPoPreStageBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailReferenceBean;
import com.tcmis.client.raytheon.beans.XcblOrderDetailScheduleBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericWebServiceProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.scanner.beans.ScanDataList;
import com.tcmis.ws.scanner.process.CabinetCountWorkerProcess;

public class PoXmlProcess extends GenericWebServiceProcess {
    Log log = LogFactory.getLog(this.getClass());
    private ResourceLibrary library = null;
    //private String url = null;
    static boolean ignoreTokenError = false; // should load this from localconfig.
    
    public PoXmlProcess(String client) {
        super(client);
        library = new ResourceLibrary("aerojet");
	}

    static {
        try {
            ResourceLibrary lconfig = new ResourceLibrary("localconfig");
            if (lconfig != null) {
                if ("IgnoreTokenError".equals(lconfig.getString("IgnoreTokenError")))
                    ignoreTokenError = true;
            }
        } catch (Exception ex) {
        }
    }
    
    public String processPoXml(String Company, String xmlString) throws BaseException {
        boolean validOrder = true;
    	String status = "Failed";
        String reason = "System failed to save backup file";        
        try {
        	//save the file on disk
            String dirname = library.getString("upload.backupdir");
            File poOrderFile = FileHandler.saveTempFile(xmlString, "PoXml_" + getClient() + "_", ".xml", dirname);            
            reason = "Failed to parse order po Data.";
            
            PurchaseOrderBean poBean = null;
            //check if new order or change order parsing 
            if ( xmlString != null && xmlString.indexOf("<PROCESS_PO_007>") > -1) {
            	log.debug("valid po found - processing");
            	validOrder = true;
                //parse data            
                PoXmlParser poXmlParser = new PoXmlParser(getClient());
                poBean = poXmlParser.parse(poOrderFile);                
            } else if (xmlString != null && xmlString.indexOf("<CHANGE_PO_006>") > -1) {
            	log.debug("valid change po found - processing");
            	validOrder = true;
                //parse change po data
                PoXmlChangeOrderParser poXmlChangeOrderParser = new PoXmlChangeOrderParser(getClient());
                poBean = poXmlChangeOrderParser.parse(poOrderFile);                
            } else {
                log.error("Invalid PO:" + xmlString);
                sendErrorEmail("Invalid po xml", xmlString);
                validOrder = false;
                reason = "Invalid PO XML received";
            }
            
            if (validOrder) {
                //invoke a thread to flaten the bean and perform the backend process
                PoXmlSubmitWorkerProcess p = new PoXmlSubmitWorkerProcess(poBean, getClient());
                Thread thread = new Thread(p);
                thread.start();            	
            }

        } catch (Exception ex) {
        	log.error("Error processing the request:" + xmlString, ex);
        	validOrder = false;        	
        }
        if (validOrder) {
        	status = "200";  
        	reason = "OK";
        }
        StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><PoXmlResponse xmlns=\"http://www.tcmis.com/tcmIS/aerojet/xsd\"><Status><Result>" + status + "</Result><Message>" + reason + "</Message></Status></PoXmlResponse>");
        return sw.toString();
    }

    public static void sendErrorEmail(String subject, String msg) {
        MailProcess.sendEmail("deverror@haasgroupintl.com", "", "deverror@haasgroupintl.com", subject, msg);
    }
    
}
