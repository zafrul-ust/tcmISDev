package com.tcmis.ws.scanner.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:48:59 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;


import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcmis.common.db.ConnectionPool;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.StringHandler;

import com.tcmis.ws.scanner.beans.PersonnelBean;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.CabinetListBean;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.Receipt;
//import com.tcmis.ws.scanner.beans.ScanDataList;


public class EchoProcess {
	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;

	public EchoProcess(String client) throws BaseException {
		library = new ResourceLibrary("scannerupload");
	}

	public String parsePersonnel(File file) throws BaseException {
//		  <EchoRequest xmlns="http://beans.scanner.ws.tcmis.com/xsd">
//		  <Value>Test</Value> 
//		  </EchoRequest>
		  PersonnelBean orderBean = null;
		    try {
		    	Digester digester = new Digester();
		        digester.addObjectCreate("EchoRequest", PersonnelBean.class);

		        digester.addCallMethod("EchoRequest/Value", "setLogonId", 0);
		        digester.parse(file);
		        orderBean = (PersonnelBean) digester.getRoot();
		        log.debug(orderBean);
		    }
		    catch (Exception e) {
		        System.out.println("Error:" + e.getMessage());
		      log.error("Error:" + e.getMessage());
		      e.printStackTrace(System.out);
		      BaseException be = new BaseException(e);
		      be.setMessageKey("");
		      be.setRootCause(e);
		      throw be;
		    }
		    return orderBean.getLogonId();
		  }

	public String processEcho(String xmlString) throws
		BaseException {
			try {
//				<?xml version="1.0" encoding="utf-8" ?> 
//				- <EchoResponse xmlns="http://beans.scanner.ws.tcmis.com/xsd">
//				  <Value>Test</Value> 
//				  </EchoResponse>
				String dirname = library.getString("upload.backupdir");
				//        	dirname = "c:\\GeneratedJava\\";
				File orderFile = FileHandler.saveTempFile(xmlString, "Echo_", ".xml", dirname);
				String personnel = parsePersonnel(orderFile);
				personnel = StringEscapeUtils.escapeXml(personnel);

				StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><EchoResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\"><Value>"+personnel+"</Value></EchoResponse>");
				return sw.toString();
		}
		catch(Exception e) {
			log.fatal("Error processing Echo", e);
			throw new BaseException(e.getCause());
		}
	}

}
