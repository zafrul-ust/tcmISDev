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


public class PoXmlParser extends BaseProcess {
  public PoXmlParser(String client) {
    super(client);
  }

  public PurchaseOrderBean parse(File file) throws BaseException {
	  Class[] bigDecimalClass = {BigDecimal.class};
	  Class[] dateClass = {Date.class};
	  PurchaseOrderBean purchaseOrderBean = null;
    
	  try {
		  log.debug("file path - " + file.getAbsolutePath());
		  Digester digester = new Digester();
		  digester.addObjectCreate("PROCESS_PO_007", PurchaseOrderBean.class);		  
		  //controlarea
		  digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/BSR/VERB", "setVerb", 0);
		  digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/BSR/NOUN", "setNoun", 0);
		  digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/BSR/REVISION", "setRevision", 0);
	      //sender
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/SENDER/COMPONENT", "setComponent", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/SENDER/TASK", "setTask", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/SENDER/REFERENCEID", "setReferenceId", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/SENDER/CONFIRMATION", "setConfirmation", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/SENDER/LANGUAGE", "setLanguage", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/SENDER/CODEPAGE", "setCodePage", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/SENDER/AUTHID", "setAuthId", 0);
	      //document creation datetime  //private String creationDate;
	      digester.addObjectCreate("PROCESS_PO_007/CNTROLAREA/DATETIME", DateTimeBean.class);
	      digester.addSetNext("PROCESS_PO_007/CNTROLAREA/DATETIME", "addDateTimeBean");
	      digester.addSetProperties("PROCESS_PO_007/CNTROLAREA/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("PROCESS_PO_007/CNTROLAREA/DATETIME/SECOND", "setSecond", 0);
	      
	      //header section
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME", DateTimeBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME", "addDateTimeBean");	      
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DATETIME/SECOND", "setSecond", 0);
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/OPERAMT", OperAmtBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/OPERAMT", "setOperAmtBean");
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/OPERAMT", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/OPERAMT/VALUE", "setValue", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/OPERAMT/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/OPERAMT/CURRENCY", "setCurrency", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/POID", "setPoId", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/POTYPE", "setPoType", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/DESCRIPTN", "setHeaderDescription", 0);	      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/USERAREA/FOB/DESCRIPTN", "setFobDescription", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/USERAREA/FOB/TERMID", "setFobId", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/USERAREA/FTTERM/DESCRIPTN", "setFreightTermDescription", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/USERAREA/FTTERM/TERMID", "setFreightTermId", 0);	      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/USERAREA/DOCSTYLE", "setDocStylePoType", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/POTERM/DESCRIPTN", "setPaymentTermsDesc", 0);	      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/POTERM/TERMID", "setPaymentTermsId", 0);
	      //supplier information	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER", AddressBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER", "addAddressBean");	
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/NAME", "setPartnerName", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/PARTNRID", "setPartnerId", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/PARTNRTYPE", "setPartnerType", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/ADDRESS/ADDRLINE", "addAddress", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/ADDRESS/CITY", "setCity", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/ADDRESS/COUNTRY", "setCountry", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/ADDRESS/COUNTY", "setRegion", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/ADDRESS/STATEPROVN", "setState", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/ADDRESS/POSTALCODE", "setZip", 0);
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/CONTACT", ContactPersonBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/CONTACT", "setContactPerson");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/CONTACT/NAME", "setName", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/CONTACT/EMAIL", "setEmail", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/CONTACT/FAX", "setFax", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERHDR/PARTNER/CONTACT/TELEPHONE", "setTelephone", 0);
	      
	      //Line item starts	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN", LineItemBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN", "addLineItemBean");	      
	      //digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY/VALUE", "setLineOrderedQuantity", 0);
	      //digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY/UOM", "setUnitOfMeasure", 0);	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY", QuantityBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY", "setLineOrderedQuantityBean");
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY/VALUE", "setValue", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/QUANTITY/UOM", "setUom", 0);	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/OPERAMT", OperAmtBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/OPERAMT", "setLineItemAmtBean");
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/OPERAMT", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/OPERAMT/VALUE", "setValue", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/OPERAMT/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/OPERAMT/CURRENCY", "setCurrency", 0);		      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINENUM", "setPoLineNumber", 0);
	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/NOTES", NoteToSupplier.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/NOTES", "addNoteToSupplier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/NOTES", "setNote", 0);	      
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/NOTES", "index", "index");
	      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/DESCRIPTN", "setItemDescription", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/ITEM", "setItemNumber", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/ITEMX", "setSupplierItemNumber", 0);
          digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/USERAREA/REQUESTOR", "setRequestorName", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/USERAREA/CATEGORYID", "setCategory", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/USERAREA/CONTRACTNUM", "setContractNumber", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/USERAREA/LINEORDERTYPE", "setLineOrderType", 0);
	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD", ScheduleLineItemBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD", "addScheduleLineItemBean");	      	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME", DateTimeBean.class); // need by date
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME", "addDateTimeBean");	      
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/DATETIME/SECOND", "setSecond", 0);		 
	      
	      //digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY/VALUE", "setScheduleQuantity", 0); // -- should this be a bean??
	      //digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY/UOM", "setScheduleUom", 0); // -- should this be a bean??
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/PSCLINENUM", "setScheduleLineNumber", 0);	
	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY", QuantityBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY", "setScheduleQuantityBean");
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY/VALUE", "setValue", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/QUANTITY/UOM", "setUom", 0);
	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME", DateTimeBean.class); // PromiseDate
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME", "addDateTimeBean");	      
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME/YEAR", "setYear", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME/MONTH", "setMonth", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME/DAY", "setDay", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME/HOUR", "setHour", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME/MINUTE", "setMinute", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DATETIME/SECOND", "setSecond", 0);
	      
	      //digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT/VALUE", "setScheduleUnitPrice", 0);
	      //digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT/NUMOFDEC", "setScheduleUnitPrice", 0);
	      
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT", OperAmtBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT", "setScheduleUnitPriceBean");
	      digester.addSetProperties("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT", "qualifier", "qualifier");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT/VALUE", "setValue", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT/NUMOFDEC", "setNumOfDec", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/OPERAMT/CURRENCY", "setCurrency", 0);
	      
	  	  //ship to information and deliver to information
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER", AddressBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER", "addAddressBean");	     
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/NAME", "setPartnerName", 0);	      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/PARTNRID", "setPartnerId", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/PARTNRTYPE", "setPartnerType", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/ADDRESS/ADDRLINE", "addAddress", 0);	      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/ADDRESS/CITY", "setCity", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/ADDRESS/COUNTRY", "setCountry", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/ADDRESS/COUNTY", "setRegion", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/ADDRESS/STATEPROVN", "setState", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/ADDRESS/POSTALCODE", "setZip", 0);
	      digester.addObjectCreate("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/CONTACT", ContactPersonBean.class);
	      digester.addSetNext("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/CONTACT", "addContactPerson");
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/CONTACT/NAME", "setName", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/PARTNER/CONTACT/EMAIL", "setEmail", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/CONTACT/FAX", "setFax", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/CONTACT/TELEPHONE", "setTelephone", 0);	      
	      //Requester and project information
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DISTPROJECT/REQUESTOR", "setProjectRequestorName", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DISTPROJECT/EMAIL", "setProjectRequestorEmail", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DISTPROJECT/PROJECTNUM", "setProjectNumber", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DISTPROJECT/PROJECTTYPE", "setProjectType", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DISTPROJECT/TASKNUM", "setProjectTask", 0);
	      
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DISTPROJECT/DFFDISTRIBUTN/ATTRIBUTE15", "setOwnerSegmentId", 0);
	      digester.addCallMethod("PROCESS_PO_007/DATAAREA/PROCESS_PO/POORDERLIN/POLINESCHD/USERAREA/DISTPROJECT/DFFDISTRIBUTN/ATTRIBUTE16", "setApplication", 0);
	      
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