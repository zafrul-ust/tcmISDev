package com.tcmis.internal.hub.process;

import java.math.BigDecimal;

import java.text.MessageFormat;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.beans.CustomerReturnInputBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.beans.MrIssueReceiptViewBean;
import com.tcmis.internal.hub.factory.InventoryGroupDefinitionBeanFactory;
import com.tcmis.internal.hub.factory.MrIssueReceiptViewBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptItemPriorBinViewBeanFactory;

/******************************************************************************
 * Process for customer returns
 * @version 1.0
 *****************************************************************************/
public class CustomerReturnProcess
extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());
   
   public CustomerReturnProcess(String client) {
      super(client);
   }
   
   public CustomerReturnProcess(String client,String locale) {
	    super(client,locale);
  }
      
   public Collection getReturns(CustomerReturnInputBean inputBean,boolean getbins) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      MrIssueReceiptViewBeanFactory factory = new MrIssueReceiptViewBeanFactory(dbManager);
      SearchCriteria searchCriteria = new SearchCriteria();
      Collection issueReceipts = null;
      
      try {
         if (inputBean.getHub() != null && inputBean.getHub().trim().length() > 0 ) {
            searchCriteria.addCriterion("sourceHub",SearchCriterion.EQUALS,inputBean.getHub());
         }
         
         if (inputBean.getFacilityId().length() > 0 && !inputBean.getFacilityId().equalsIgnoreCase("All")) {
            searchCriteria.addCriterion("facilityId",SearchCriterion.EQUALS,inputBean.getFacilityId());
         }
         
         if (inputBean.getInventoryGroup() != null && inputBean.getInventoryGroup().trim().length() > 0 && !inputBean.getInventoryGroup().equalsIgnoreCase("All")) {
             searchCriteria.addCriterion("inventoryGroup",SearchCriterion.EQUALS,inputBean.getInventoryGroup());
          }

         if (inputBean.getReceiptId() != null && inputBean.getReceiptId().trim().length() > 0 ) {
            searchCriteria.addCriterion("receiptId",SearchCriterion.EQUALS,inputBean.getReceiptId());
         }
         
         if (inputBean.getItemId() != null && inputBean.getItemId().trim().length() > 0 ) {
            searchCriteria.addCriterion("itemId",SearchCriterion.EQUALS,inputBean.getItemId());
         }
         
         if (inputBean.getCatPartNo() != null && inputBean.getCatPartNo().trim().length() > 0) {
            searchCriteria.addCriterion("catPartNo",SearchCriterion.LIKE,inputBean.getCatPartNo(),SearchCriterion.IGNORE_CASE);
         }
         
         if (inputBean.getMrNumber() != null && inputBean.getMrNumber().trim().length() > 0) {
            searchCriteria.addCriterion("prNumber",SearchCriterion.EQUALS,inputBean.getMrNumber());
         }
         searchCriteria.addCriterion("quantity",SearchCriterion.GREATER_THAN,"0");
         
         SortCriteria sortCriteria = null;
         if (inputBean.getSortBy() != null) {
            sortCriteria =new SortCriteria();
            sortCriteria.addCriterion(inputBean.getSortBy());
         }
         boolean forCredit = false;
         if( "Y".equals(inputBean.getReturnForCredit()))
        	 forCredit = true;
         issueReceipts = factory.select(searchCriteria,sortCriteria,forCredit,getbins);
         
      } catch (BaseException be2) {
         log.error("Base Exception in getReturns: " + be2);
      } finally {
         dbManager = null;
         factory = null;
      }
      
      return issueReceipts;
   }
   
   /*
   // do a P_CUSTOMER_OWNED_RECEIPT() call for each return selected
   // @return a Collection of two Collections, first is receipt_id's, second is errors
   */
   public Collection receiveSelected(Collection returnsBeanCollection, PersonnelBean pbean, String hubName,boolean forCredit) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      MrIssueReceiptViewBeanFactory factory = new MrIssueReceiptViewBeanFactory(dbManager);
      Iterator iter = returnsBeanCollection.iterator();
      Collection allResults = new Vector(2);
      Collection custReceiptIds = new Vector();
      Collection errors = new Vector();
      ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
      String err = res.getString("error.db.procedure");
      BigDecimal personnelId = pbean.getPersonnelIdBigDecimal();
      try {
        BigDecimal custOwnedReceiptId;        
        String errorMessage;
        Collection results;
        Iterator resultsIter;
      
        while (iter.hasNext()) {
           MrIssueReceiptViewBean returnBean = (MrIssueReceiptViewBean) iter.next();
         
           if (returnBean.getOk() != null && returnBean.getOk().length()>0) {

              custOwnedReceiptId = new BigDecimal(0);
              errorMessage = null;
// This check is already on client side. For for financial, its on server side too in case of someone hack it.              
//              <c:set var="ugid" value='customerReturns'/>
//              	<c:set var="ugid" value='returnForCredit'/>
              PermissionBean permissionBean = pbean.getPermissionBean();
              if( ! forCredit ) {
//            		<tcmis:inventoryGroupPermission indicator="true" userGroupId="${ugid}" companyId="${personnelBean.companyId}" facilityId="${receipt.facilityId}" inventoryGroup="${receipt.inventoryGroup}">
            	  
            	  if( permissionBean.hasInventoryGroupPermission("customerReturns",returnBean.getInventoryGroup(), hubName, pbean.getCompanyId()) ) {
	            	  log.info("Calling P_CUSTOMER_OWNED_RECEIPT(" + returnBean.getCompanyId()+","+returnBean.getReceiptId()+","+
	            			  returnBean.getPrNumber()+","+returnBean.getLineItem()+","+returnBean.getQuantityReturned()+","+
	            			  returnBean.getBin()+","+returnBean.getDor()+","+personnelId.toString()+")");
	            	  results = factory.receive(returnBean.getCompanyId(), returnBean.getReceiptId(), returnBean.getPrNumber(),
	            			  returnBean.getLineItem(), returnBean.getQuantityReturned(), returnBean.getBin(),
	            			  returnBean.getDor(), personnelId.toString());       
	            	  resultsIter = results.iterator();
	            	  if (resultsIter.hasNext()) {
	            		  custOwnedReceiptId = (BigDecimal) resultsIter.next();
	            	  }
	            	  if (resultsIter.hasNext()) {
	            		  errorMessage = (String) resultsIter.next();
	            	  }
	            	  log.info("P_CUSTOMER_OWNED_RECEIPT returned: " + custOwnedReceiptId.toString() +"," + errorMessage);
            	  }
            	  else
            		  errorMessage = res.getString("nopermissions.title")+ " \n"+ res.getString("label.returnreceiptid") +": "+ returnBean.getReceiptId()+"\n "+MessageFormat.format(res.getString("label.invgrouppermsfor"),"customerReturns:"+returnBean.getInventoryGroup()) +"\n\n";
              }
              else { // for credit
            	  if( permissionBean.hasInventoryGroupPermission("returnForCredit",returnBean.getInventoryGroup(), hubName, pbean.getCompanyId()) ) {
	            	  log.info("Calling P_RETURN_MR_FOR_CREDIT(" + returnBean.getCompanyId()+","+returnBean.getReceiptId()+","+
	            			  returnBean.getPrNumber()+","+returnBean.getLineItem()+","+returnBean.getQuantityReturned()+","+
	            			  returnBean.getBin()+","+returnBean.getDor()+","+personnelId.toString()+")");
	            	  results = factory.credit(returnBean.getReplacementMaterial(), 
	            			  				   returnBean.getIssueId(),
	            			  				   returnBean.getQuantityReturned(),
	            			  				   returnBean.getQuantity(),
	            			  				   returnBean.getCompanyId(),
	            			  				   returnBean.getPrNumber(),
	            			  				   returnBean.getLineItem(),
	            			  				   null,
	            			  				   returnBean.getOrderQuantity(),
	                                           personnelId);//comment
	            	  resultsIter = results.iterator();
	            	  if (resultsIter.hasNext()) {
	            		  errorMessage = (String) resultsIter.next();
	            	  }
	            	  log.info("P_RETURN_MR_FOR_CREDIT returned: " + custOwnedReceiptId.toString() +"," + errorMessage);
            	  }
//label.invgrouppermsfor=Inventory Group Permissions for {0}
//            	  nopermissions.title
            	  else
            		  errorMessage = res.getString("nopermissions.title")+ " \n"+ res.getString("label.returnreceiptid") +": "+ returnBean.getReceiptId()+"\n "+MessageFormat.format(res.getString("label.invgrouppermsfor"),"returnForCredit:"+returnBean.getInventoryGroup()) +"\n\n";
              }
              if ( !isBlank(errorMessage) && !"OK".equalsIgnoreCase(errorMessage)) { 
            	  errors.add(err + " " + returnBean.getIssueId() );    
            	  MailProcess.sendEmail("deverror@haasgroupintl.com",
            			  	"",// cc"edierror@haasgroupintl.com","deverror@haasgroupintl.com",
            			  	"deverror@haasgroupintl.com",
            			  	"Customer Return For CustomerOwned/Credit Error",
            			  	"IssueId:" + returnBean.getIssueId() +"\n" +
            			  	"Returning Quantity:" + returnBean.getQuantityReturned() +"\n" +
            			  	"Replacement Flag:" + returnBean.getReplacementMaterial() +"\n" +
            			  	"Pr Number:" + returnBean.getPrNumber() +"\n" +
            			  	"Line:" + returnBean.getLineItem() + "\n" +
            			  	"personnelId:" + personnelId + "\n" +
            			  	errorMessage
            			  	);
              }
           }
        }
        
        allResults.add(custReceiptIds);
        allResults.add(errors);
        
      } catch (BaseException be ){
         log.error("BaseException in calling P_CUSTOMER_OWNED_RECEIPT");         
      }
      return allResults;
   }
 
   public ExcelHandler getExcelReport(CustomerReturnInputBean inputBean, DropDownNamesInputBean dBean) throws NoDataException, BaseException, Exception {
	   
		Vector<MrIssueReceiptViewBean> data = (Vector<MrIssueReceiptViewBean>)this.getReturns(inputBean,false);

		ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(res);
		pw.addTable();

		//	String branch_plant1, String search_String, String expireswithin, String personnelID, Vector data, ResourceLibrary resource
		//StringBuffer Msg=new StringBuffer();
//		String url = "";
//		String file = "";
	//
//		Random rand = new Random();
//		int tmpReq = rand.nextInt();
//		Integer tmpReqNum = new Integer(tmpReq);
	//
//		String writefilepath = radian.web.tcmisResourceLoader.getsaveltempreportpath();

		try {
			Hashtable summary = new Hashtable();
//			summary = (Hashtable) data.elementAt(0);
//			int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();

			pw.addCellBold(res.getString("label.operatingentity"));
			pw.addCell(dBean.getOpsEntityName());
			pw.addCellBold(res.getString("label.receiptid"));
			pw.addCell(inputBean.getReceiptId());
			pw.addRow();
			pw.addCellBold(res.getString("label.hub"));
			pw.addCell(dBean.getHubName());
			pw.addCellBold(res.getString("label.mr"));
			pw.addCell(inputBean.getMrNumber());
			pw.addRow();
			pw.addCellBold(res.getString("label.inventorygroup"));
			pw.addCell(dBean.getInventoryGroupName());
			pw.addCellBold(res.getString("label.part"));
			pw.addCell(inputBean.getCatPartNo());
			pw.addRow();
			pw.addCellBold(res.getString("label.facility"));
			pw.addCell(dBean.getFacilityName());
			pw.addCellBold(res.getString("label.item"));
			pw.addCell(inputBean.getItemId());
			pw.addRow();
			boolean forCredit = false;
			if( "Y".equals(inputBean.getReturnForCredit() ) )
					forCredit = true;
			pw.addCellBold(res.getString("label.returnfor"));
				if(forCredit)
					pw.addCell(res.getString("label.credit"));
				else
					pw.addCell(res.getString("label.customerowned"));
			pw.addCellBold(res.getString("label.sortby"));
			pw.addCell(dBean.getSortByName());
			pw.addRow();
			pw.addRow();
			if (data.size() == 0) {
				StringBuffer Msg = new StringBuffer();
				Msg.append(res.getString("label.norecordfound"));
				pw.addCell(Msg.toString());
				return pw;
			}
//			  write column headers
			  pw.addThRegion("label.company", 2,1);
			  pw.addThRegion("label.facility", 2,1);
			  pw.addThRegion("label.mrline", 2,1);
			  pw.addThRegion("label.item", 2,1);
			  pw.addThRegion("label.part", 2,1);
			  pw.addThRegion("label.receiptid", 2,1);
			  pw.addThRegion("label.mfglot", 2,1);
			  pw.addThRegion("label.qty", 2,1);
			  if(forCredit)
				  pw.addThRegion("label.returninfo", 1,3);
			  pw.addRow();
			  pw.addTdEmpty();
			  pw.addTdEmpty();
			  pw.addTdEmpty();
			  pw.addTdEmpty();
			  pw.addTdEmpty();
			  pw.addTdEmpty();
			  pw.addTdEmpty();
			  pw.addTdEmpty();
//			  pw.addCellKeyBold("label.bin");
//			  pw.addCellKeyBold("receivedreceipts.label.dor");
			  if(forCredit) {
				  pw.addCellKeyBold("label.shipmentid");
				  pw.addCellKeyBold("label.dateshipped");
				  pw.addCellKeyBold("label.issueid");
			  }
		    int[] types_credit = {
					0,0,0,0,0,
					0,0,0,0,pw.TYPE_CALENDAR,
					0
					};
//		    int[] types_cusowned = {
//					0,0,0,0,0,
//					0,0,0,0
//					};
		    if(forCredit) 
		    	pw.setColumnHeader(null,types_credit);
//			int[] widths = {
//					0,0,0,0,0,
//					0,0,0,0,0,
//					0,0,0
//					};
			

	        int i = 0; //used for detail lines
			for (MrIssueReceiptViewBean bean:data) {
				i++;

				pw.addRow();
				pw.addCell(bean.getCompanyId());
				pw.addCell(bean.getFacilityId());
				pw.addCell(bean.getPrNumber()+"-"+bean.getLineItem());
				pw.addCell(bean.getItemId());
				pw.addCell(bean.getCatPartNo());
				pw.addCell(bean.getReceiptId());
				pw.addCell(bean.getMfgLot());
				pw.addCell(bean.getQuantity());
			    if(forCredit) {
	////			    	pw.addCell(bean.getBin());
	////				pw.addCell(bean.getDor());
					pw.addCell(bean.getShipmentId());
					pw.addCell(bean.getShipConfirmDate());
					pw.addCell(bean.getIssueId());
			    }
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return pw;
	}
   
}
