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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringEscapeUtils;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlCalendar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.PasswordProcess;
import com.tcmis.common.db.ConnectionPool;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.InvalidPasswordException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

import com.tcmis.ws.scanner.beans.PersonnelBean;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.CabinetListBean;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.Receipt;
//import com.tcmis.ws.scanner.beans.ScanDataList;


public class ValidateTokenProcess
extends GenericProcess{
	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;

	public ValidateTokenProcess(String client) throws BaseException {
		super(client);
		library = new ResourceLibrary("scannerupload");
	}

	public PersonnelBean getPersonnel(PersonnelBean personnel){
		Connection conn = null;

		personnel.setStatus("TokenFailure");
		personnel.setReason("Invalid token. Please sign in again.");

		try { try {
			String logonId = this.factory.selectSingleValue("select logon_id from personnel where personnel_id = " + personnel.getPersonnelId());
			String query = null;//PersonnelProcess.getVerifyTokenStr(personnel.getCompanyId(),logonId,personnel.getToken());
//			String s = this.factory.selectSingleValue(query);
//			if( this.isBlank(s) ) throw new Exception("Valid token required.");
		    String s = this.factory.getFunctionValue("Pkg_token.fx_verify_tcmis_token",
							   logonId,
							   personnel.getCompanyId(),
							   personnel.getToken()
							   );

			if( !"OK".equals(s) ) return personnel;

			personnel.setStatus("Success");
			personnel.setReason("Token is valid.");
		}
		finally{ if( conn != null ) conn.close(); }
		} catch(Exception ex){} 

		return personnel;
	}


	public String processOrder(String company,String xmlString) throws
	BaseException {
		try {
			String dirname = library.getString("upload.backupdir");
			//        	dirname = "c:\\GeneratedJava\\";
			File orderFile = FileHandler.saveTempFile(xmlString, "ValidateToken_", ".xml", dirname);
			PersonnelBean personnel = parsePersonnel(orderFile);
			personnel.setCompanyId(company);
			PersonnelBean list = getPersonnel(personnel);
			return getResponseXml(list);
			
		}
		catch(Exception e) {
			log.fatal("Error processing Personnel", e);
			throw new BaseException(e.getCause());
		}
	}


	public String getResponseXml(PersonnelBean list) throws BaseException {
		StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><ValidateTokenResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\"><Result>"+list.getStatus()+"</Result><Message>"+list.getReason()+"</Message></ValidateTokenResponse>");
		return sw.toString();
	}

	public PersonnelBean parsePersonnel(File file) throws BaseException {
		Class[] bigDecimalClass = {
				BigDecimal.class};
		Class[] dateClass = {
				Date.class};
		PersonnelBean orderBean = null;
		try {
			Digester digester = new Digester();
			digester.addObjectCreate("ValidateTokenRequest", PersonnelBean.class);

			digester.addCallMethod("ValidateTokenRequest/PersonnelId", "setPersonnelIdStr", 0);
			digester.addCallMethod("ValidateTokenRequest/Token", "setToken", 0);
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
		return orderBean;
	}
}
