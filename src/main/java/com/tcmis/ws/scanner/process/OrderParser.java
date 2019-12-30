package com.tcmis.ws.scanner.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:44:36 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.PersonnelBean;


public class OrderParser extends BaseProcess {
  public OrderParser(String client) {
    super(client);
  }

  public PersonnelBean parseCabinetReceiptItemRequest(File file) throws BaseException {
    Class[] bigDecimalClass = {BigDecimal.class};
    Class[] dateClass = {Date.class};
    PersonnelBean orderBean = null;
    try {
    	Digester digester = new Digester();
        digester.addObjectCreate("CabinetReceiptItemRequest", PersonnelBean.class);

        digester.addCallMethod("CabinetReceiptItemRequest/PersonnelId", "setPersonnelIdStr", 0);
        digester.addCallMethod("CabinetReceiptItemRequest/Token", "setToken", 0);
        digester.addCallMethod("CabinetReceiptItemRequest/Version", "setVersion", 0);
        digester.addCallMethod("CabinetReceiptItemRequest/TimeLastLoaded", "setTimeLastLoaded", 0);
        digester.addObjectCreate("CabinetReceiptItemRequest/Facilities/Facility", Facility.class);
        digester.addSetNext("CabinetReceiptItemRequest/Facilities/Facility", "addFacilityBean");
        digester.addCallMethod("CabinetReceiptItemRequest/Facilities/Facility/Id", "setFacilityId", 0);
        digester.addCallMethod("CabinetReceiptItemRequest/Facilities/Facility/Name", "setFacilityName", 0);
        digester.parse(file);
        orderBean = (PersonnelBean) digester.getRoot();
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
  public PersonnelBean parsePersonnel(File file) throws BaseException {
	    Class[] bigDecimalClass = {
	        BigDecimal.class};
	    Class[] dateClass = {
	        Date.class};
	    PersonnelBean orderBean = null;
	    try {
	    	Digester digester = new Digester();
	        digester.addObjectCreate("PersonnelRequest", PersonnelBean.class);

	        digester.addCallMethod("PersonnelRequest/LoginId", "setLogonId", 0);
	        digester.addCallMethod("PersonnelRequest/Password", "setPassword", 0);
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

  public static void main(String[] args) {
	  String outPath     = "c:\\GeneratedJava\\";
	  File f = new File(outPath+"test.xml");
	  OrderParser p = new OrderParser("");	  // TCM_OPS
	  try {
//		  p.parse(f);
	  } catch(Exception ex){
		  
	  }
  }
}
