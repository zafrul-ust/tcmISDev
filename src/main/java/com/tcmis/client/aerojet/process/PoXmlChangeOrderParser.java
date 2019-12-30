package com.tcmis.client.aerojet.process;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.client.aerojet.beans.AddressBean;
import com.tcmis.client.aerojet.beans.ContactPersonBean;
import com.tcmis.client.aerojet.beans.DateTimeBean;
import com.tcmis.client.aerojet.beans.LineItemBean;
import com.tcmis.client.aerojet.beans.NoteToSupplier;
import com.tcmis.client.aerojet.beans.OperAmtBean;
import com.tcmis.client.aerojet.beans.PurchaseOrderBean;
import com.tcmis.client.aerojet.beans.QuantityBean;
import com.tcmis.client.aerojet.beans.ScheduleLineItemBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;


public class PoXmlChangeOrderParser extends BaseProcess {
  public PoXmlChangeOrderParser(String client) {
    super(client);
  }

  public PurchaseOrderBean parse(File file) throws BaseException {
	  Class[] bigDecimalClass = {BigDecimal.class};
	  Class[] dateClass = {Date.class};
	  PurchaseOrderBean purchaseOrderBean = null;
    
	  try {
		  log.debug("file path - " + file.getAbsolutePath());
		  Digester digester = new Digester();
		  digester.addObjectCreate("CHANGE_PO_006", PurchaseOrderBean.class);		  
		  //controlarea
		  digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/BSR/VERB", "setVerb", 0);
		  digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/BSR/NOUN", "setNoun", 0);
		  digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/BSR/REVISION", "setRevision", 0);
	      //sender
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/SENDER/COMPONENT", "setComponent", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/SENDER/TASK", "setTask", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/SENDER/REFERENCEID", "setReferenceId", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/SENDER/CONFIRMATION", "setConfirmation", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/SENDER/LANGUAGE", "setLanguage", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/SENDER/CODEPAGE", "setCodePage", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/SENDER/AUTHID", "setAuthId", 0);
	      //document creation datetime  //private String creationDate;
	      digester.addObjectCreate("CHANGE_PO_006/CNTROLAREA/DATETIME", DateTimeBean.class);
	      digester.addSetNext("CHANGE_PO_006/CNTROLAREA/DATETIME", "addDateTimeBean");
	      digester.addSetProperties("CHANGE_PO_006/CNTROLAREA/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("CHANGE_PO_006/CNTROLAREA/DATETIME/SECOND", "setSecond", 0);
	      
	      //header section
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME", DateTimeBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME", "addDateTimeBean");	      
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DATETIME/SECOND", "setSecond", 0);
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/OPERAMT", OperAmtBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/OPERAMT", "setOperAmtBean");
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/OPERAMT", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/OPERAMT/VALUE", "setValue", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/OPERAMT/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/OPERAMT/CURRENCY", "setCurrency", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/POID", "setPoId", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/POTYPE", "setPoType", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/DESCRIPTN", "setHeaderDescription", 0);	      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/USERAREA/FOB/DESCRIPTN", "setFobDescription", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/USERAREA/FOB/TERMID", "setFobId", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/USERAREA/FTTERM/DESCRIPTN", "setFreightTermDescription", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/USERAREA/FTTERM/TERMID", "setFreightTermId", 0);	      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/USERAREA/DOCSTYLE", "setDocStylePoType", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/POTERM/DESCRIPTN", "setPaymentTermsDesc", 0);	      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/POTERM/TERMID", "setPaymentTermsId", 0);
	      //supplier information	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER", AddressBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER", "addAddressBean");	
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/NAME", "setPartnerName", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/PARTNRID", "setPartnerId", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/PARTNRTYPE", "setPartnerType", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/ADDRESS/ADDRLINE", "addAddress", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/ADDRESS/CITY", "setCity", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/ADDRESS/COUNTRY", "setCountry", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/ADDRESS/COUNTY", "setRegion", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/ADDRESS/STATEPROVN", "setState", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/ADDRESS/POSTALCODE", "setZip", 0);
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/CONTACT", ContactPersonBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/CONTACT", "setContactPerson");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/CONTACT/NAME", "setName", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/CONTACT/EMAIL", "setEmail", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/CONTACT/FAX", "setFax", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POHEADER/PARTNER/CONTACT/TELEPHONE", "setTelephone", 0);
	      
	      //Line item starts	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE", LineItemBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE", "addLineItemBean");	      
	      //digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY/VALUE", "setLineOrderedQuantity", 0);
	      //digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY/UOM", "setUnitOfMeasure", 0);	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY", QuantityBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY", "setLineOrderedQuantityBean");
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY/VALUE", "setValue", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/QUANTITY/UOM", "setUom", 0);	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/OPERAMT", OperAmtBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/OPERAMT", "setLineItemAmtBean");
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/OPERAMT", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/OPERAMT/VALUE", "setValue", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/OPERAMT/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/OPERAMT/CURRENCY", "setCurrency", 0);		      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/POLINENUM", "setPoLineNumber", 0);
	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/NOTES", NoteToSupplier.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/NOTES", "addNoteToSupplier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/NOTES", "setNote", 0);	      
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/NOTES", "index", "index");
	      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/DESCRIPTN", "setItemDescription", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/ITEM", "setItemNumber", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/ITEMX", "setSupplierItemNumber", 0);
          digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/USERAREA/REQUESTOR", "setRequestorName", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/USERAREA/CATEGORYID", "setCategory", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/USERAREA/CONTRACTNUM", "setContractNumber", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/USERAREA/LINEORDERTYPE", "setLineOrderType", 0);
	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE", ScheduleLineItemBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE", "addScheduleLineItemBean");	      	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME", DateTimeBean.class); // need by date
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME", "addDateTimeBean");	      
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/DATETIME/SECOND", "setSecond", 0);		 
	      
	      //digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY/VALUE", "setScheduleQuantity", 0); // -- should this be a bean??
	      //digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY/UOM", "setScheduleUom", 0); // -- should this be a bean??
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/PSCLINENUM", "setScheduleLineNumber", 0);	
	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY", QuantityBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY", "setScheduleQuantityBean");
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY/VALUE", "setValue", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/QUANTITY/UOM", "setUom", 0);
	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME", DateTimeBean.class); // PromiseDate
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME", "addDateTimeBean");	      
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DATETIME/SECOND", "setSecond", 0);
	      
	      //digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT/VALUE", "setScheduleUnitPrice", 0);
	      //digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT/NUMOFDEC", "setScheduleUnitPrice", 0);
	      
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT", OperAmtBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT", "setScheduleUnitPriceBean");
	      digester.addSetProperties("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT", "qualifier", "qualifier");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT/VALUE", "setValue", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/OPERAMT/CURRENCY", "setCurrency", 0);
	      
	  	  //ship to information and deliver to information
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER", AddressBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER", "addAddressBean");	     
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/NAME", "setPartnerName", 0);	      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/PARTNRID", "setPartnerId", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/PARTNRTYPE", "setPartnerType", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/ADDRESS/ADDRLINE", "addAddress", 0);	      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/ADDRESS/CITY", "setCity", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/ADDRESS/COUNTRY", "setCountry", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/ADDRESS/COUNTY", "setRegion", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/ADDRESS/STATEPROVN", "setState", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/ADDRESS/POSTALCODE", "setZip", 0);
	      digester.addObjectCreate("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/CONTACT", ContactPersonBean.class);
	      digester.addSetNext("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/CONTACT", "addContactPerson");
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/CONTACT/NAME", "setName", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/PARTNER/CONTACT/EMAIL", "setEmail", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/CONTACT/FAX", "setFax", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/CONTACT/TELEPHONE", "setTelephone", 0);	      
	      //Requester and project information
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DISTPROJECT/REQUESTOR", "setProjectRequestorName", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DISTPROJECT/EMAIL", "setProjectRequestorEmail", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DISTPROJECT/PROJECTNUM", "setProjectNumber", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DISTPROJECT/PROJECTTYPE", "setProjectType", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DISTPROJECT/TASKNUM", "setProjectTask", 0);
	      
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DISTPROJECT/DFFDISTRIBUTN/ATTRIBUTE15", "setOwnerSegmentId", 0);
	      digester.addCallMethod("CHANGE_PO_006/DATAAREA/CHANGE_PO/POLINE/SCHEDULE/USERAREA/DISTPROJECT/DFFDISTRIBUTN/ATTRIBUTE16", "setApplication", 0);
	      
	      digester.parse(file);
	      purchaseOrderBean = (PurchaseOrderBean) digester.getRoot();
	      //purchaseOrderBean.setPayloadId(payloadId);
	      
    } catch (Exception e) {
      //log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("");
      be.setRootCause(e);
      throw be;
    }
    return purchaseOrderBean;
  }
  
}