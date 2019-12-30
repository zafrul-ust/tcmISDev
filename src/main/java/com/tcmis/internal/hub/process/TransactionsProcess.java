package com.tcmis.internal.hub.process;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.math.BigDecimal;
import java.math.MathContext;
import java.io.FileOutputStream;
import java.io.File;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.util.InventoryGroupHandler;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.hub.beans.TransactionTrackingViewBean;
import com.tcmis.internal.hub.beans.TransactionInputBean;
import com.tcmis.internal.hub.factory.TransactionTrackingViewBeanFactory;

import com.tcmis.common.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/******************************************************************************
 * Process for transactions
 * @version 1.0
 *****************************************************************************/
public class TransactionsProcess
    extends BaseProcess {

  Log log = LogFactory.getLog(this.getClass());

  private final long MILLISECONDS_PER_DAY = 86400000;

  public TransactionsProcess(String client) {
	    super(client);
	  }
  public TransactionsProcess(String client,String locale) {
	    super(client,locale);
	  }

  public Collection getTransactions(PersonnelBean personnelBean, TransactionInputBean inputBean, Collection invgrpColl) throws BaseException {
     Collection txnBeans = null;
     DbManager dbManager = new DbManager(this.getClient(),getLocale());
     TransactionTrackingViewBeanFactory factory = new TransactionTrackingViewBeanFactory(dbManager);
     //GenericSqlFactory factory = new GenericSqlFactory(dbManager,new TransactionTrackingViewBean());
     try {
        boolean isCollection = false;

        if (inputBean.getInventoryGroup() != null && inputBean.getInventoryGroup().length() > 0 && !inputBean.getInventoryGroup().equalsIgnoreCase("All")) {
           isCollection = InventoryGroupHandler.isCollection(invgrpColl,inputBean.getHub(), inputBean.getInventoryGroup());
        }

        SearchCriteria searchCriteria = new SearchCriteria();
        
        if (inputBean.getHub() != null && inputBean.getHub().trim().length() > 0 ) {
            searchCriteria.addCriterion("hub",SearchCriterion.EQUALS,inputBean.getHub());
        }
/*
        if (! isCollection && inputBean.getInventoryGroup() != null && inputBean.getInventoryGroup().length() > 0 && !inputBean.getInventoryGroup().equalsIgnoreCase("All")) {
           searchCriteria.addCriterion("inventoryGroup",SearchCriterion.EQUALS,inputBean.getInventoryGroup());
        }  */
        
        if(! isCollection && !StringHandler.isBlankString(inputBean.getInventoryGroup())) {
        	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
		}
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
			if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
				invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}

        if (inputBean.getItemId() != null && inputBean.getItemId().trim().length() > 0 ) {
           searchCriteria.addCriterion("itemId",SearchCriterion.EQUALS,inputBean.getItemId());
        }


        if (inputBean.getTransType() != null && inputBean.getTransType().equalsIgnoreCase("OV")) {
           searchCriteria.addCriterion("transactionType",SearchCriterion.IN,"'OV','IA','IT','LC'");
            searchCriteria.addCriterion("issueId",SearchCriterion.IS,null);
        }
        else if (inputBean.getTransType() != null && inputBean.getTransType().equalsIgnoreCase("XLC")) {
        	searchCriteria.addCriterion("transactionType",SearchCriterion.NOT_LIKE,"LC");   
        }	
        else if (inputBean.getTransType() != null && inputBean.getTransType().equalsIgnoreCase("RI")) {
           searchCriteria.addCriterion("transactionType",SearchCriterion.IN,"'RI','RMA'");
        }
        else if (inputBean.getTransType() != null && inputBean.getTransType().equalsIgnoreCase("LC")) {
           searchCriteria.addCriterion("transactionType",SearchCriterion.IN,"'LC'");
           searchCriteria.addCriterion("issueId",SearchCriterion.IS,null);
        }
        else if (inputBean.getTransType() != null && !inputBean.getTransType().equalsIgnoreCase("All")) {
           searchCriteria.addCriterion("transactionType",SearchCriterion.EQUALS,inputBean.getTransType());
        }

        if (inputBean.getReceiptId() != null && inputBean.getReceiptId().trim().length() > 0 ) {
           searchCriteria.addCriterion("receiptId",SearchCriterion.EQUALS,inputBean.getReceiptId());
        }

        if (inputBean.getMrNumber() != null && inputBean.getMrNumber().trim().length() > 0) {
           searchCriteria.addCriterion("prNumber",SearchCriterion.LIKE,inputBean.getMrNumber());
        }

        if (inputBean.getMfgLot() != null && inputBean.getMfgLot().trim().length() > 0) {
           searchCriteria.addCriterion("mfgLot",SearchCriterion.LIKE,inputBean.getMfgLot(),SearchCriterion.IGNORE_CASE);
        }

        if (inputBean.getRadianPo() != null && inputBean.getRadianPo().trim().length() > 0) {
           searchCriteria.addCriterion("radianPo",SearchCriterion.EQUALS,inputBean.getRadianPo());
        }
        if (inputBean.getIssueId() != null && inputBean.getIssueId().trim().length() > 0) {
            searchCriteria.addCriterion("issueId",SearchCriterion.EQUALS,inputBean.getIssueId());
         }
        
   
        if (inputBean.getTxnOnDate() != null ){
           searchCriteria.addCriterion("transactionDate",SearchCriterion.IS_DATE,inputBean.getTxnOnDate());
        }
        else if (inputBean.getDaysOld() != null && inputBean.getDaysOld().length()>0) {
            Date now = new Date();
            int numDays = Integer.parseInt(inputBean.getDaysOld());
            Date then = new Date( now.getTime() - (numDays * MILLISECONDS_PER_DAY));
            searchCriteria.addCriterion("transactionDate",SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatOracleDate(then));
         }
        else
        { 
        	if (inputBean.getTransactionFromDate() != null )
        		searchCriteria.addCriterion("transactionDate",SearchCriterion.FROM_DATE,inputBean.getTransactionFromDate());
        	if (inputBean.getTransactionToDate() != null) 
        		searchCriteria.addCriterion("transactionDate",SearchCriterion.TO_DATE,inputBean.getTransactionToDate());
        }  	
       
        if (inputBean.getTrackingNumber() != null &&  inputBean.getTrackingNumber().length() >0 ) {
            searchCriteria.addCriterion("trackingNumber",SearchCriterion.EQUALS,inputBean.getTrackingNumber(),SearchCriterion.IGNORE_CASE);
         }

     	SortCriteria sortCriteria = null;
        if (inputBean.getSortBy() != null) {
           sortCriteria =new SortCriteria();
	   sortCriteria.addCriterion(inputBean.getSortBy());
        }

        //txnBeans = factory.select(searchCriteria,sortCriteria);
        txnBeans = factory.select(searchCriteria,sortCriteria,isCollection,inputBean);

     } catch (BaseException be2) {
        log.error("Base Exception in getTransactions: " + be2);
     } finally {
        dbManager = null;
        factory = null;
     }

     return txnBeans;
  }

  public ExcelHandler getExcelReport(TransactionInputBean inputBean,Collection data, Locale locale) 
	  throws Exception
  {
 ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
      Iterator iterator = data.iterator();
      ExcelHandler pw = new ExcelHandler(library);
      pw.addTable();

//	  write column headers
      pw.addRow();
      pw.addCellKeyBold("label.hub");
      pw.addCell(inputBean.getHubName());
      pw.addCellKeyBold("label.receiptid");
      pw.addCell(inputBean.getReceiptId());
      pw.addCellKeyBold("label.mr");
      pw.addCell(inputBean.getMrNumber());
      pw.addRow();
      pw.addCellKeyBold("label.invgrp");
      pw.addCell(inputBean.getInventoryGroup());
      pw.addCellKeyBold("label.itemid");
      pw.addCell(inputBean.getItemId());
      pw.addCellKeyBold("label.mfglot");
      pw.addCell(inputBean.getMfgLot());
      pw.addCellKeyBold("label.po");
      pw.addCell(inputBean.getRadianPo());
      pw.addRow();
      pw.addCellKeyBold("transactions.transtype"); 
	  pw.addCell(inputBean.getTransType());
      pw.addCellBold(library.getString("transactions.ondate"));
      pw.addCell(inputBean.getTxnOnDate());
      pw.addCellKeyBold("label.within");
      pw.addCell(inputBean.getDaysOld() + library.getString("label.days"));
      pw.addRow();
      pw.addRow();
      String[] headerkeys = {
            "label.type","label.inventorygroup","label.receiptid","transactions.receivername","label.lotstatus","transactions.picklistid","label.issueid",
            "transactions.issuername", "label.itemid", "label.itemdesc", "label.quantity","label.currency",
            "label.cost","label.landedcost","label.mfglot","label.bin","receivedreceipts.label.dor","label.deliveryticket",
            "transactions.storagetemp","transactions.receivedpicked","transactions.source", "transactions.transferdestination","label.delivered","label.poline","label.supplier", "label.mrline",
            "label.customer","label.trackingnumber","transactions.receiptnotes"};
      int[] widths = {10,20,0,20,0,0,
                    0,0,0,0,0,0,
                    0,0,0,0,0,8,0,0,
                    0,18,10,10,15,0,
                    15,0,0};
      int [] types = {0,0,0,0,0,0,
                    0,0,0,pw.TYPE_PARAGRAPH,0,0,
                    0,0,0,0,pw.TYPE_CALENDAR,0,0,pw.TYPE_DATE,
                    0,0,pw.TYPE_CALENDAR,0,0,0,
                    0,0,pw.TYPE_PARAGRAPH};
      int[] aligns = {0,0,0,0,0,0,
                    0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,
                    0,0,0};

      pw.applyColumnHeader(headerkeys, types, widths, aligns);


	  while(iterator.hasNext())
	  {
		  TransactionTrackingViewBean batchProductionViewBean = (TransactionTrackingViewBean) iterator.next();
		  BigDecimal zero = new BigDecimal(0);
		  pw.addRow();
	      if(batchProductionViewBean.getTransactionType().equalsIgnoreCase("OV"))
			  pw.addCell(library.getString("label.receipt"));
		  else if(inputBean.getIssueId() != null && batchProductionViewBean.getTransactionType().equalsIgnoreCase("RI"))
			  pw.addCell(library.getString("label.issue"));
		  else if(inputBean.getIssueId() == null && batchProductionViewBean.getTransactionType().equalsIgnoreCase("IT"))
			  pw.addCell(library.getString("label.transferreceipt"));
		  else if(inputBean.getIssueId() != null && batchProductionViewBean.getTransactionType().equalsIgnoreCase("IT"))
			  pw.addCell(library.getString("label.transfer"));
		  else if(batchProductionViewBean.getTransactionType().equalsIgnoreCase("LC"))
			  pw.addCell(library.getString("label.lotchange"));
		  else if(batchProductionViewBean.getTransactionType().equalsIgnoreCase("IC"))
			  pw.addCell(library.getString("label.itemconversion"));
		  else if(batchProductionViewBean.getTransactionType().equalsIgnoreCase("IA"))
			  pw.addCell(library.getString("inventoryAdjustments"));
		  else if(batchProductionViewBean.getTransactionType().equalsIgnoreCase("WO"))
			  pw.addCell(library.getString("label.writeoff"));
		  else if(batchProductionViewBean.getTransactionType().equalsIgnoreCase("SF"))
			  pw.addCell(library.getString("label.servicefee"));
		  else
			  pw.addCell(batchProductionViewBean.getTransactionType());
		  pw.addCell(batchProductionViewBean.getInventoryGroupName());
		  pw.addCell(batchProductionViewBean.getReceiptId());
		  pw.addCell(batchProductionViewBean.getReceiverName());
		  pw.addCell(batchProductionViewBean.getLotStatus());
		  pw.addCell(
				  (batchProductionViewBean.getPicklistId() != null &&
				   batchProductionViewBean.getPicklistId().compareTo(zero) > 0 )?batchProductionViewBean.getPicklistId().toString():"");
		  pw.addCell(batchProductionViewBean.getIssueId());
		  pw.addCell(batchProductionViewBean.getIssuerName());
		  pw.addCell(batchProductionViewBean.getItemId());
		  pw.addCell(batchProductionViewBean.getItemDescription());
		  pw.addCell(batchProductionViewBean.getQuantity());
		  pw.addCell(batchProductionViewBean.getHomeCurrencyId());
		  if(batchProductionViewBean.getCost() != null && !batchProductionViewBean.getTransactionType().equalsIgnoreCase("RI"))
			  pw.addCell(batchProductionViewBean.getQuantity().multiply(batchProductionViewBean.getCost()).setScale(2, BigDecimal.ROUND_HALF_UP));
		  else
			  pw.addCell("");
		  if(batchProductionViewBean.getLandedCost() != null)
			  pw.addCell(batchProductionViewBean.getQuantity().multiply(batchProductionViewBean.getLandedCost()).setScale(2, BigDecimal.ROUND_HALF_UP));
		  else
			  pw.addCell("");  
		  pw.addCell(batchProductionViewBean.getMfgLot());
		  pw.addCell(batchProductionViewBean.getBin());
		  pw.addCell(batchProductionViewBean.getDateOfReceipt());
          pw.addCell(batchProductionViewBean.getDeliveryTicket());
		  pw.addCell(batchProductionViewBean.getLabelStorageTemp());
		  pw.addCellDateTime(batchProductionViewBean.getTransactionDate());
		  pw.addCell(batchProductionViewBean.getSourceInvGroupName());
		  pw.addCell(batchProductionViewBean.getDestinationInvGroupName());
		  pw.addCell(batchProductionViewBean.getDateDelivered());
		  pw.addCell((batchProductionViewBean.getRadianPo() != null )?batchProductionViewBean.getRadianPo().toString()+"-"+batchProductionViewBean.getPoLine():"");
		  pw.addCell(batchProductionViewBean.getSupplierName());
		  pw.addCell(
				  (batchProductionViewBean.getPrNumber() != null &&
						  batchProductionViewBean.getLineItem() != null &&
						  batchProductionViewBean.getLineItem().length() > 0 )?batchProductionViewBean.getPrNumber().toString()+"-"+batchProductionViewBean.getLineItem():""
		  );
		  pw.addCell(batchProductionViewBean.getCustomerName());
		  pw.addCell(batchProductionViewBean.getTrackingNumber());
		  pw.addCell(batchProductionViewBean.getNotes());
	  }
	  return pw;
}
}
