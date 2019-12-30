package com.tcmis.internal.hub.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:08:29 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.InventoryReceiveInputBean;
import com.tcmis.internal.hub.beans.CustomerInventoryToReceiveBean;
import com.tcmis.internal.hub.beans.UserIgCompanyOwnerSegmentBean;
import com.tcmis.internal.hub.factory.CustomerInventoryToReceiveBeanFactory;
import com.tcmis.client.common.beans.OwnerSegmentBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Connection;

import javax.servlet.ServletOutputStream;

/******************************************************************************
 * Process for ItemManagement
 * @version 1.0
 *****************************************************************************/

public class InventoryReceiveProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public InventoryReceiveProcess(String client) {
	super(client);
 }
 
 public InventoryReceiveProcess(String client,String locale) {
	    super(client,locale);
 }

public Collection<CustomerInventoryToReceiveBean> getSearchData(InventoryReceiveInputBean inputBean,PersonnelBean personnelBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());

	CustomerInventoryToReceiveBeanFactory factory = new CustomerInventoryToReceiveBeanFactory(dbManager);
	SearchCriteria searchCriteria = new SearchCriteria();
	if (!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
		searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
	} else {
		String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
		if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0)
			invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' ";
		if (inputBean.getHub() != null && inputBean.getHub().length() != 0)
			invQuery += " and hub = '" + inputBean.getHub() + "' ";
		if( inputBean.getOpsEntityId()!=null && inputBean.getOpsEntityId().length()>0 )
			invQuery +=  " and ops_entity_id = '" + inputBean.getOpsEntityId() +"' ";
		searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
	}
    
    if("Y".equals(inputBean.getShowAvailable()))
    {
    	searchCriteria.addCriterion("quantityToReceive", SearchCriterion.NOT_EQUAL, "TOTAL_QUANTITY_RECEIVED");
    }
    
    if(inputBean.getExpDeliveryFromDate() != null)
    {
    	searchCriteria.addCriterion("expectedDeliveryDate", SearchCriterion.FROM_DATE, inputBean.getExpDeliveryFromDate());
    }
    
    if(inputBean.getExpDeliveryToDate() != null)
    {
    	searchCriteria.addCriterion("expectedDeliveryDate", SearchCriterion.TO_DATE, inputBean.getExpDeliveryToDate());
    }
    
    searchCriteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument());
    return factory.select(searchCriteria, null);
 }

public Collection<CompanyBean> getCompanyDropDown(BigDecimal personnelId, String inventoryGroup, String hub, String userGroupId) throws BaseException {
    Vector result = new Vector(0);
    try {
        DbManager dbManager = new DbManager(getClient(),this.getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserIgCompanyOwnerSegmentBean());
        SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("personnelId", SearchCriterion.EQUALS, personnelId.toString());
        searchCriteria.addCriterion("userGroupId", SearchCriterion.EQUALS, userGroupId);
        searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, hub);
        if (!StringHandler.isBlankString(inventoryGroup)) {
            searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
        }
        SortCriteria sort = new SortCriteria();
        sort.addCriterion("inventoryGroupName");
        sort.addCriterion("companyName");
        sort.addCriterion("ownerSegmentDesc");
        //create object beans
        Iterator iter = factory.select(searchCriteria,sort,"user_ig_company_owner_view").iterator();
        String lastInventoryGroup = "";
        String lastIgCompany = "";
        while (iter.hasNext()) {
            UserIgCompanyOwnerSegmentBean bean = (UserIgCompanyOwnerSegmentBean)iter.next();
            if (!lastInventoryGroup.equals(bean.getInventoryGroup())) {
                UserIgCompanyOwnerSegmentBean igBean = new UserIgCompanyOwnerSegmentBean();
                //inventory group
                igBean.setInventoryGroup(bean.getInventoryGroup());
                igBean.setInventoryGroupName(bean.getInventoryGroupName());
                //company
                Collection companyColl = new Vector();
                UserIgCompanyOwnerSegmentBean cBean = new UserIgCompanyOwnerSegmentBean();
                cBean.setCompanyId(bean.getCompanyId());
                cBean.setCompanyName(bean.getCompanyName());
                companyColl.add(cBean);
                //owner segment
                Collection ownerSegmentColl = new Vector();
                UserIgCompanyOwnerSegmentBean oBean = new UserIgCompanyOwnerSegmentBean();
                oBean.setOwnerSegmentId(bean.getOwnerSegmentId());
                oBean.setOwnerSegmentDesc(bean.getOwnerSegmentDesc());
                ownerSegmentColl.add(oBean);

                cBean.setOwnerSegmentColl(ownerSegmentColl);
                igBean.setCompanyColl(companyColl);
                result.addElement(igBean);
            }else {
                UserIgCompanyOwnerSegmentBean igBean = (UserIgCompanyOwnerSegmentBean)result.lastElement();
                //company
                Vector companyColl = (Vector)igBean.getCompanyColl();
                if (!lastIgCompany.equals(bean.getInventoryGroup()+bean.getCompanyId())) {
                    UserIgCompanyOwnerSegmentBean cBean = new UserIgCompanyOwnerSegmentBean();
                    cBean.setCompanyId(bean.getCompanyId());
                    cBean.setCompanyName(bean.getCompanyName());
                    companyColl.add(cBean);
                    //owner segment
                    Collection ownerSegmentColl = new Vector();
                    UserIgCompanyOwnerSegmentBean oBean = new UserIgCompanyOwnerSegmentBean();
                    oBean.setOwnerSegmentId(bean.getOwnerSegmentId());
                    oBean.setOwnerSegmentDesc(bean.getOwnerSegmentDesc());
                    ownerSegmentColl.add(oBean);
                    cBean.setOwnerSegmentColl(ownerSegmentColl);
                }else {
                    UserIgCompanyOwnerSegmentBean cBean = (UserIgCompanyOwnerSegmentBean)companyColl.lastElement();
                    //owner segment
                    Collection ownerSegmentColl = (Collection)cBean.getOwnerSegmentColl();
                    UserIgCompanyOwnerSegmentBean oBean = new UserIgCompanyOwnerSegmentBean();
                    oBean.setOwnerSegmentId(bean.getOwnerSegmentId());
                    oBean.setOwnerSegmentDesc(bean.getOwnerSegmentDesc());
                    ownerSegmentColl.add(oBean);
                }
            }
            lastInventoryGroup = bean.getInventoryGroup();
            lastIgCompany = bean.getInventoryGroup()+bean.getCompanyId();
        }
    }catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}

public void getExcelReport(InventoryReceiveInputBean inputBean, PersonnelBean personnelBean,ServletOutputStream out, Locale locale) throws
	Exception
	{
		Collection data = getSearchData(inputBean,personnelBean);
	
		  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		  ExcelHandler pw = new ExcelHandler(library);
		  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
	Iterator iterator = data.iterator();
	  pw.addSheet();
	  pw.addRow();
//	now write data
        String[] headerkeys = {"label.ponumberpoline","label.company","label.catalog","label.partnum","label.itemid","label.itemdesc",
        		"label.packaging","label.supplier","label.inventorygroup","label.expecteddeliverydate","label.qty","label.ownersegmentid","label.chargenumber",
        		"label.chargenumber2","label.chargenumber3","label.chargenumber4","label.customerreceiptid","label.qualityTrackingNumber","label.quantityreceived",
        		"label.unitofsale","label.notes","label.enteredby","label.entereddate","label.updatedby","label.updateddate"};

        int  [] widths = {20, 10, 12, 12, 10, 20, 25, 12, 15, 0, 5,
                          0,0,0,0,0,12,0,10, 5, 20, 10 , 0, 10, 0} ;

        int [] types =   {0,0,0,0,0,0,0,0,0,
                ExcelHandler.TYPE_CALENDAR,0,0,0,0,0,ExcelHandler.TYPE_CALENDAR,0,ExcelHandler.TYPE_CALENDAR};

        int[] aligns = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

       pw.applyColumnHeader(headerkeys, types, widths, aligns);

    while(iterator.hasNext())
	{
		CustomerInventoryToReceiveBean bean = (CustomerInventoryToReceiveBean) iterator.next();
		pw.addRow();
		pw.addCell(bean.getPoNumber());
		pw.addCell(bean.getOwnerCompanyId());
		pw.addCell(bean.getCatalogId());
		pw.addCell(bean.getCatPartNo());
		pw.addCell(bean.getItemId());
		pw.addCell(bean.getItemDesc());
		pw.addCell(bean.getPackaging());
		pw.addCell(bean.getSupplierName());
		pw.addCell(bean.getInventoryGroup());
		pw.addCell(bean.getExpectedDeliveryDate());
        pw.addCell(bean.getQuantityToReceive());
        pw.addCell(bean.getOwnerSegmentId());
        pw.addCell(bean.getAccountNumber());
        pw.addCell(bean.getAccountNumber2());
        pw.addCell(bean.getAccountNumber3());
        pw.addCell(bean.getAccountNumber4());
        pw.addCell(bean.getCustomerReceiptId());
        pw.addCell(bean.getQualityTrackingNumber());
        pw.addCell(bean.getTotalQuantityReceived());
        pw.addCell(bean.getUnitOfSale());
        pw.addCell(bean.getNotes());
        pw.addCell(bean.getEnteredByName());
        if(bean.getDateInserted() != null)
		 {
		   pw.addCell(sdf.format(bean.getDateInserted()));
		 }
		 else
		 {
			 pw.addCell(bean.getDateInserted());	 
		 }
        pw.addCell(bean.getUpdatedByName());
        if(bean.getDateLastUpdated() != null)
		 {
		   pw.addCell(sdf.format(bean.getDateLastUpdated()));
		 }
		 else
		 {
			 pw.addCell(bean.getDateLastUpdated());	 
		 }
       
    }
	  pw.write(out);
}

 public String update(Collection beans,Locale locale,PersonnelBean personnelBean) throws
	BaseException {
	String errMsg = "";
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.dbmsg", locale);
	DbManager dbManager = new DbManager(getClient(),getLocale());
	PermissionBean permissionBean = personnelBean.getPermissionBean();
	
	CustomerInventoryToReceiveBeanFactory factory = new CustomerInventoryToReceiveBeanFactory(dbManager);
	
	for(CustomerInventoryToReceiveBean bean: (Collection<CustomerInventoryToReceiveBean>) beans) {
		
		if (!permissionBean.hasInventoryGroupPermission("custInvReceiving", bean.getInventoryGroup(), null, personnelBean.getCompanyId()))
	    {
	      errMsg = "No Permission to Update"+bean.getInventoryGroup();
	      return errMsg;
	    }
		if((bean.getTotalQuantityReceived()!=null) && (bean.getQuantityToReceive()!=null)){
		  if( bean.getQuantityToReceive().compareTo(bean.getTotalQuantityReceived()) >= 0 ){
	     		    factory.update(bean,personnelBean);
	        }
		  else
			{
			  
			  errMsg += library.format( "1001002",
						bean.getCustomerPoNo(),
						bean.getTotalQuantityReceived(),
						bean.getQuantityToReceive());
			}
		}
		else
		{
		  
		  errMsg += library.format( "1001002",
					bean.getCustomerPoNo(),
					bean.getTotalQuantityReceived(),
					bean.getQuantityToReceive());
		}
		
	}
	return errMsg;
 }
 
 public String addnewitem(CustomerInventoryToReceiveBean beans, PersonnelBean personnelBean ) throws
	BaseException {
	String errMsg = "";
	DbManager dbManager = new DbManager(getClient(),getLocale());
	PermissionBean permissionBean = personnelBean.getPermissionBean();
	if (!permissionBean.hasInventoryGroupPermission("custInvReceiving", beans.getInventoryGroup(), null, personnelBean.getCompanyId()))
    {
      errMsg = "No Permission to Add"+beans.getInventoryGroup();
      return errMsg;
    }
	
try {
	Collection inArgs = new Vector(13);
    if(beans.getInventoryGroup() != null) {
      inArgs.add(beans.getInventoryGroup());
    }
    else {
      inArgs.add("");
    }
    if(beans.getOwnerCompanyId() != null) {
      inArgs.add(beans.getOwnerCompanyId());
    }
    else {
      inArgs.add("");
    }
    if(beans.getCatalogCompanyId() != null) {
      inArgs.add(beans.getCatalogCompanyId());
    }
    else {
      inArgs.add("");
    }
    if(beans.getCatalogId() != null) {
      inArgs.add(beans.getCatalogId());
    }
    else {
      inArgs.add("");
    }
    if(beans.getCatPartNo() != null) {
      inArgs.add(beans.getCatPartNo());
    }
    else {
      inArgs.add("");
    }
    if(beans.getPartGroupNo() != null) {
      inArgs.add(beans.getPartGroupNo());
    }
    else {
      inArgs.add("1");
    }
    if(beans.getCustomerPoNo() != null) {
      inArgs.add(beans.getCustomerPoNo());
    }
    else {
      inArgs.add("");
    }
    if(beans.getCustomerPoLine() != null) {
      inArgs.add(beans.getCustomerPoLine());
    }
    else {
      inArgs.add("");
    }
    if(beans.getExpectedDeliveryDate() != null) {
    	Format formatter = new SimpleDateFormat("yyyy-MM-dd");

    	inArgs.add(formatter.format(beans.getExpectedDeliveryDate()));
    }
    else {
      inArgs.add("");
    }
    if(beans.getQuantityToReceive() != null) {
      inArgs.add(beans.getQuantityToReceive());
    }
    else {
      inArgs.add("");
    }
    if(beans.getSupplierName() != null) {
      inArgs.add(beans.getSupplierName());
    }
    else {
      inArgs.add("");
    }
    if(beans.getNotes() != null) {
      inArgs.add(beans.getNotes());
    }
    else {
      inArgs.add("");
    }
     
     inArgs.add(personnelBean.getPersonnelId());
     
     if(beans.getItemId() != null) {
         inArgs.add(beans.getItemId());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getPoNumber() != null) {
         inArgs.add(beans.getPoNumber());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getOwnerSegmentId() != null) {
         inArgs.add(beans.getOwnerSegmentId());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getAccountNumber() != null) {
         inArgs.add(beans.getAccountNumber());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getAccountNumber2() != null) {
         inArgs.add(beans.getAccountNumber2());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getAccountNumber3() != null) {
         inArgs.add(beans.getAccountNumber3());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getAccountNumber4() != null) {
         inArgs.add(beans.getAccountNumber4());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getCustomerReceiptId() != null) {
         inArgs.add(beans.getCustomerReceiptId());
       }
       else {
         inArgs.add("");
       }
     
     if(beans.getQualityTrackingNumber() != null) {
         inArgs.add(beans.getQualityTrackingNumber());
       }
       else {
         inArgs.add("");
       }
     
     log.info("P_CITR_INSERT  " + inArgs);
     GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
     procFactory.doProcedure("P_CITR_INSERT", inArgs);

  
	} catch (Exception e) {
		e.printStackTrace();
		errMsg = "Error adding new Item";
		
	}

	return errMsg;
  }
 }


