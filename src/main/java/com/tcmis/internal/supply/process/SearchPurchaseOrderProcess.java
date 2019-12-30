package com.tcmis.internal.supply.process;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.PoSearchResultBean;
import com.tcmis.internal.supply.beans.SearchPoInputBean;

/******************************************************************************
 * Process for dbuy misatch
 * @version 1.0
 *****************************************************************************/
public class SearchPurchaseOrderProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public SearchPurchaseOrderProcess(String client) {
    super(client);
  }
  
  public SearchPurchaseOrderProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getBuyerDropDown()  throws BaseException
  {
    DbManager dbManager = new DbManager(getClient());
    PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
    return factory.selectBuyOrdersBuyer();
  }
  
  public Collection<PoSearchResultBean> getPOs(SearchPoInputBean inputBean, PersonnelBean personnelBean) throws
	BaseException{
	  DbManager dbManager = new DbManager(getClient());
	  GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoSearchResultBean());
		
	  SearchCriteria searchCriteria = new SearchCriteria();
		
//	    if(!StringHandler.isBlankString(inputBean.getBranchPlant())) 
	    	searchCriteria.addCriterionArray("branchPlant", SearchCriterion.IN, inputBean.getBranchPlant());
    if(!StringHandler.isBlankString(inputBean.getInventoryGroup()))
    	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
    else {
    	String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
//    	if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
//    		invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
    	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
    }
	    
	    if(!StringHandler.isBlankString(inputBean.getSupplierId())) {
	        searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS, inputBean.getSupplierId());
	    }
	   	     
	    if ( !isBlank(inputBean.getSearchString()) ) 
	    	searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchString());

	    if ( !isBlank(inputBean.getSearchString2()) ) 
	    	searchCriteria.addCriterionWithMode(inputBean.getSearchField2(), inputBean.getSearchMode2(), inputBean.getSearchString2());

	    if(inputBean.getBuyer() != null && !inputBean.getBuyer().equals("")) {
	    	searchCriteria.addCriterion("buyer", SearchCriterion.EQUALS, inputBean.getBuyer().toString());
	    }

	    if(inputBean.getPromisedFromDate() != null) {
	    	searchCriteria.addCriterion("promisedDate", SearchCriterion.FROM_DATE, inputBean.getPromisedFromDate());
	    }

	    if(inputBean.getPromisedToDate() != null) {
	    	searchCriteria.addCriterion("promisedDate", SearchCriterion.TO_DATE, inputBean.getPromisedToDate());
	    }

	    if(inputBean.getConfirmedFromDate() != null) {
	    	searchCriteria.addCriterion("dateConfirmed", SearchCriterion.FROM_DATE, inputBean.getConfirmedFromDate());
	    }

	    if(inputBean.getConfirmedToDate() != null) {
	    	searchCriteria.addCriterion("dateConfirmed", SearchCriterion.TO_DATE, inputBean.getConfirmedToDate());
	    }
	    if(!isBlank(inputBean.getSupplyPath())) {
	    	searchCriteria.addCriterion("supplyPath", SearchCriterion.EQUALS, inputBean.getSupplyPath());
	    }

	 return factory.select(searchCriteria, null,"PURCHASING_HISTORY_VIEW");
}
  
 /*
  public Collection getPOs(SearchPoInputBean inputBean, Collection higbColl) throws BaseException {

     Collection poBeans = null;
     DbManager dbManager = new DbManager(this.getClient(),getLocale());
     
     String emptyString = new String("");
     
     PoSearchResultBeanFactory poSearchFactory = new PoSearchResultBeanFactory(dbManager);
     try {
        Collection params = new Vector();       
        // 1 : po number
        params.add(emptyString);
        
        // 2: item id
        if (inputBean.getBrokenPrLink() != null && inputBean.getBrokenPrLink().equalsIgnoreCase("Yes")) {        
           params.add(emptyString);
        } else {
           if ( inputBean.getSearchField1() != null && inputBean.getSearchField1().equalsIgnoreCase("ITEM_ID") &&
                inputBean.getSearchString1() != null && inputBean.getSearchString1().trim().length()>0 ) {
              BigDecimal itemID = new BigDecimal(inputBean.getSearchString1());             
              params.add(itemID);
           } else if ( inputBean.getSearchField2() != null && inputBean.getSearchField2().equalsIgnoreCase("ITEM_ID") &&
                       inputBean.getSearchString2() != null && inputBean.getSearchString2().trim().length()>0) {
              BigDecimal itemID = new BigDecimal(inputBean.getSearchString2());             
              params.add(itemID);
           } else {
              params.add(emptyString);
           }
        }

        // 3 : item description
        if (inputBean.getBrokenPrLink() != null && inputBean.getBrokenPrLink().equalsIgnoreCase("Yes")) {        
           params.add(emptyString);
        } else {
           if ( inputBean.getSearchField1() != null && inputBean.getSearchField1().equalsIgnoreCase("ITEM_DESC") &&
                inputBean.getSearchString1() != null && inputBean.getSearchString1().trim().length()>0 ) {                
              params.add(inputBean.getSearchString1());
           } else if ( inputBean.getSearchField2() != null && inputBean.getSearchField2().equalsIgnoreCase("ITEM_DESC") &&
                       inputBean.getSearchString2() != null && inputBean.getSearchString2().trim().length()>0 ) {       
              params.add(inputBean.getSearchString2());
           } else {
              params.add(emptyString);
           }
        }
        
        // 4 : buyer name
        params.add(emptyString);
        
        // 5 : supplier name
        if ( inputBean.getSearchField1() != null && inputBean.getSearchField1().equalsIgnoreCase("SUPPLIER") &&
             inputBean.getSearchString1() != null && inputBean.getSearchString1().trim().length()>0 ) {                
           params.add(inputBean.getSearchString1());
        } else if ( inputBean.getSearchField2() != null && inputBean.getSearchField2().equalsIgnoreCase("SUPPLIER") &&
                    inputBean.getSearchString2() != null && inputBean.getSearchString2().trim().length()>0 ) {       
           params.add(inputBean.getSearchString2());
        } else {
           params.add(emptyString);
        }
        
        // 6 ; hub name
        if (inputBean.getBranchPlant() != null && inputBean.getBranchPlant().length > 0 ) {
           StringBuffer hubs= new StringBuffer();
           for(int i=0; i <inputBean.getBranchPlant().length; i++){
             if (i==0) 
            	 hubs.append(""+inputBean.getBranchPlant()[0]);                       
             else 
            	 hubs.append(","+inputBean.getBranchPlant()[i]);           
           }	   
           params.add(hubs.toString());
        } else {
           params.add(emptyString);
        }
        
        // 7 : unconfirmed
        if (inputBean.getUnconfirmedOrders() != null && inputBean.getUnconfirmedOrders().equalsIgnoreCase("Yes")) {
           params.add(new String("Y"));           
        } else {
           params.add(emptyString);
        }
        
        // 8 : broken pr
        if (inputBean.getBrokenPrLink() != null && inputBean.getBrokenPrLink().equalsIgnoreCase("Yes")) {
           params.add(new String("Y"));           
        } else {
           params.add(emptyString);
        }
        
        // 9 : all buyers
        params.add(emptyString);
        
        // 10: buyer id
        if (inputBean.getBuyer() != null) {
           params.add(inputBean.getBuyer());
        } else {
           params.add(emptyString);
        }
        
        // 11: open po
        if (inputBean.getOpenOrders() != null && inputBean.getOpenOrders().equalsIgnoreCase("Yes")) {
           params.add((String)"Y");           
        } else {
           params.add(emptyString);
        }
        
        // 12: inventory group
        if (inputBean.getInventoryGroup() != null && !inputBean.getInventoryGroup().equalsIgnoreCase("All")) {
           params.add(inputBean.getInventoryGroup());
        } else {
           params.add(emptyString);
        }
        
        // 13: inventory group collection
        params.add(emptyString);
//	HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(this.getClient());
//	boolean iscollection = hubInventoryGroupProcess.isCollection(higbColl,inputBean.getBranchPlant(), inputBean.getInventoryGroup());
//        if (iscollection) {
//           params.add(new String("y"));
//  	} else { // if (inputBean.getInventoryGroup().length() > 0) {
//	   params.add(emptyString);
//	}
        
//		 14: START_DATE1
		 if (inputBean.getConfirmedFromDate() != null ) {
			 params.add(new Timestamp(inputBean.getConfirmedFromDate().getTime()));
		 } else {
			 params.add(emptyString);
		 }
		 log.debug("inputBean.getConfirmedFromDate()"+inputBean.getConfirmedFromDate());
//		 15: END_DATE1
		 if (inputBean.getConfirmedToDate() != null ) {
			 params.add(new Timestamp(inputBean.getConfirmedToDate().getTime()));
		 } else {
			 params.add(emptyString);
		 }
log.debug("inputBean.getConfirmedToDate()"+inputBean.getConfirmedToDate());
//		 16: originalPromisedStart
		 if (inputBean.getPromisedFromDate() != null ) {
			 params.add(new Timestamp(inputBean.getPromisedFromDate().getTime()));
		 } else {
			 params.add(emptyString);
		 }

//		 17: originalPromisedEnd
		 if (inputBean.getPromisedToDate() != null ) {
			 params.add(new Timestamp(inputBean.getPromisedToDate().getTime()));
		 } else {
			 params.add(emptyString);
		 }
		 
//		 18: suppliedId
		 if (inputBean.getSupplierId() != null ) {
			 params.add(inputBean.getSupplierId());
		 } else {
			 params.add(emptyString);
		 }
        
        poBeans = poSearchFactory.search(params);              
        
     } catch (BaseException be2) {
        log.error("Base Exception in getPOs: " + be2);
     } finally {
        dbManager = null;
        poSearchFactory = null;
     }

     return poBeans;
  }
*/  
  
  public void getExcelReport(Collection data, ServletOutputStream out, Locale locale) throws
  	BaseException, Exception
  {
  	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
  	  ExcelHandler pw = new ExcelHandler(library);
  	  
  	  pw.addTable();
//  	  write column headers
  	  pw.addRow();

       String[] headerkeys = {"label.po","label.supplier","label.buyer","label.hub","label.inventorygroup",
    		                  "label.dateconfirmed","label.itemid","label.itemdescription","label.supplypath","label.needdate","label.promiseddate",
    		                  "label.quantity","label.currency","label.unitprice",
                              "label.extprice","label.qtyreceived","label.paymentterms","label.customerpo","label.partnumber",
                              "label.mfgpartno","label.manufacturername","label.supplierpartnum","label.shiptoaddress",
                              "label.vendorshipdate","label.revisedpromisedate"};
      int[] widths = {0,30,14,15,17,
                    12,0,0,0,0,0,
                    0,0,0,
                    0,0,0,10,12,
                    15,30,15,40,
                    12,12};
      int [] types = {0,0,0,0,0,
    		          pw.TYPE_CALENDAR,0, pw.TYPE_PARAGRAPH,0,pw.TYPE_CALENDAR,pw.TYPE_CALENDAR,
    		          0,0,0,
                      0,0,0,0,0,
                      0,0,0,0,
                      pw.TYPE_CALENDAR,pw.TYPE_CALENDAR};
      int[] aligns = {0,0,0,0,0,
                    0,0,0,0,0,0,
                    0,0,0,
                    0,0,0,0,0,
                    0,0,0,0,
                    0,0};
      pw.applyColumnHeader(headerkeys, types, widths, aligns);
      
      pw.setColumnDigit(13, 4);
      pw.setColumnDigit(14, 2);
      for(PoSearchResultBean bean:(Collection<PoSearchResultBean>) data)
  	  {
  		  pw.addRow();
  		  pw.addCell(bean.getRadianPo());
  		  pw.addCell(bean.getSupplierName());
  		  pw.addCell(bean.getBuyerName());
  		  pw.addCell(bean.getHubName());
  		  pw.addCell(bean.getInventoryGroupName());
  		  pw.addCell(bean.getDateConfirmed());
  		  pw.addCell(bean.getItemId());
  		  pw.addCell(bean.getItemDesc());
  		  pw.addCell(bean.getSupplyPath());
  		  pw.addCell(bean.getNeedDate());
  		  pw.addCell(bean.getPromisedDate());
  		  pw.addCell(bean.getQuantity());
  		  if(bean.getUnitPrice() == null) 
  			  pw.addCell("");
  		  else
  			  pw.addCell(bean.getCurrencyId());
  		  pw.addCell(bean.getUnitPrice());
  		  pw.addCell(bean.getExtPrice());
  		  pw.addCell(bean.getQtyReceived());
  		  pw.addCell(bean.getPaymentTerms());
  		  pw.addCell(bean.getCustomerPo());
  		  pw.addCell(bean.getPartNumber());
  		  pw.addCell(bean.getMfgPartNo());
  		  pw.addCell(bean.getManufacturer());
  		  pw.addCell(bean.getSupplierPartNo());
  		  pw.addCell(bean.getShipToAddress());
  		  pw.addCell(bean.getVendorShipDate());
  		  pw.addCell(bean.getRevisedPromisedDate());
      }
  	  pw.write(out);
  	  out.close();
  }
}
