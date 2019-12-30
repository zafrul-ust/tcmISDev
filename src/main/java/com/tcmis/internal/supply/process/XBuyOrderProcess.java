package com.tcmis.internal.supply.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.internal.supply.beans.PoHeaderViewBean;
import com.tcmis.internal.supply.beans.PoLineDraftBean;
import com.tcmis.internal.supply.beans.XbuyViewBean;
import com.tcmis.internal.supply.factory.PoHeaderViewBeanFactory;
import com.tcmis.internal.supply.factory.PoLineDraftBeanFactory;
import com.tcmis.internal.supply.factory.XbuyViewBeanFactory;
import com.tcmis.internal.supply.factory.SupplierLocationPartOvBeanFactory;
import com.tcmis.supplier.xbuy.airgas.beans.OrderSubmitInputBean;
import com.tcmis.supplier.xbuy.airgas.beans.OrderSubmitResultViewBean;
import com.tcmis.supplier.xbuy.airgas.beans.StockQueryInputBean;
import com.tcmis.supplier.xbuy.airgas.beans.StockQueryResultViewBean;
import com.tcmis.supplier.xbuy.airgas.process.AirgasProcess;

import com.tcmis.internal.supply.beans.SupplierLocationPartOvBean;
import com.tcmis.internal.supply.beans.SupplierPartObjBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

/******************************************************************************
 * Process for buy orders
 * @version 1.0
 *****************************************************************************/
public class XBuyOrderProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  public XBuyOrderProcess(String client) {
    super(client);
  }


  public String submitStockQuery ()  throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    SupplierLocationPartOvBeanFactory factory = new SupplierLocationPartOvBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    Vector<SupplierLocationPartOvBean> result = (Vector<SupplierLocationPartOvBean>)factory.selectObject(criteria);
	AirgasProcess process = new AirgasProcess();

	factory.delete(null);
    
	for(SupplierLocationPartOvBean lBean:result ) {
		if( lBean.getSupplierRegionCode() == null || lBean.getSupplierRegionCode().trim().length() == 0 ) continue;
		//lBean.setSupplierPartList(result.get(0).getSupplierPartList());
    	String partNum = "";
    	String preFix = "";
    	int count = 0 ;
    	log.info("Region:"+lBean.getSupplierRegionCode()+":Location:"+lBean.getSupplierLocationCode());
    	log.info("Supplier:"+lBean.getSupplier()+":ShipFromLocationId:"+lBean.getShipFromLocationId());

      for(SupplierPartObjBean bean:(Collection<SupplierPartObjBean>)lBean.getSupplierPartList()) {
    		partNum += preFix + bean.getSupplierPartNo();
    		preFix = ",";
//    		 remove this later.
//    		if( ++count == 1) break;
    	}
  	Vector<StockQueryResultViewBean> v1 = new Vector();
    	if( !partNum.equals("")) {
    		StockQueryInputBean inputBean = new StockQueryInputBean();
    		// remove this line later.
    		try{
    		inputBean.setAccount(factory.selectSupplierAccountNumber(lBean.getSupplierRegionCode()));
    		} catch(Exception ex) {
    			BulkMailProcess bulkMailProcess = new
    			BulkMailProcess(this.getClient());
    			bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
    					 "Error getting airgas account number.",
    					 "query:select supplier_account_number from customer.supplier_location_mapping where SHIP_FROM_LOCATION_ID in ( select SHIP_FROM_LOCATION_ID from supplier_ship_from_location where supplier_region_code = '"+ lBean.getSupplierRegionCode() +"') and rownum < 2" 
    					 ,false);
    			continue;
    		}
    		log.info("Account:"+inputBean.getAccount());
    		inputBean.setRegion(lBean.getSupplierRegionCode());
    		inputBean.setInventoryLocation(lBean.getSupplierLocationCode());
    		inputBean.setPartNum(partNum);
    		v1 = process.StockQuery(inputBean);
    	}
    	for(StockQueryResultViewBean bean1:v1) {
    		if (!bean1.getQtyAvailable().equalsIgnoreCase("-1"))
    		{   
    			factory.insert(lBean,bean1.getAirgasPN(),bean1.getQtyAvailable());
    		}
    	}
    }
    // Query select * from supplier_location_part_ov to get the input parameters to pass to  AirgasProcess.StockQuery
    // delete from  supplier_inventory_snap where supplier = <supplier> and ship_from_location_id = <> and location_type = <>
    //insert into supplier_inventory_snap ( supplier, ship_from_location_id, location_type, supplier_part_no, quantity)

     return "OK";
   }

  public Collection getPurchaseOrderHeader(XbuyViewBean inputBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	PoHeaderViewBeanFactory factory = new PoHeaderViewBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("radianPo", SearchCriterion.EQUALS,  ""+inputBean.getRadianPo()+"" );

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("radianPo");

	return factory.select(criteria);
 }

  public Collection getSearchData(XbuyViewBean inputBean)  throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    XbuyViewBeanFactory factory = new XbuyViewBeanFactory(dbManager);

	  SearchCriteria criteria = new SearchCriteria();
	  SortCriteria sortCriteria = new SortCriteria();
	  criteria.addCriterion("radianPo", SearchCriterion.EQUALS, ""+inputBean.getRadianPo()+"" );
    criteria.addCriterion("poLine", SearchCriterion.EQUALS, ""+inputBean.getPoLine()+"" );
    sortCriteria.addCriterion("poLine");

		Collection c = factory.select(criteria, sortCriteria );

		return c;
  }

  public String submitAirgasOrder(XbuyViewBean inputBean)  throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    String errMsg ="ERROR";
    BulkMailProcess bulkMailProcess = new	BulkMailProcess(this.getClient());

    PoLineDraftBeanFactory poLineFactory = new PoLineDraftBeanFactory(dbManager);
    PoLineDraftBean poLineDraftBean = new PoLineDraftBean();
    poLineDraftBean.setRadianPo(inputBean.getRadianPo());
    poLineDraftBean.setPoLine(inputBean.getPoLine());

    if(inputBean != null && inputBean.getRadianPo() !=null && inputBean.getPoLine() != null) {
    /*Setting the stauts of the buy order to SendingXBuy so the cron will not pick this up when I am working on this.
    * Not sending out the order if the ship to address is Harrisburg PA Airgas location.*/
    poLineFactory.updateBuyOrderStatus(poLineDraftBean,"SendingXBuy");

    Collection orderHeaderCollection = getPurchaseOrderHeader(inputBean);
    Collection orderDataCollection = getSearchData(inputBean);
    OrderSubmitInputBean orderInputBean = new OrderSubmitInputBean();
    AirgasProcess process = new AirgasProcess();

    /*Order header ship to information*/
    for (Object obj : orderHeaderCollection) {
			if (obj == null)
				continue;
			PoHeaderViewBean poHeaderBean = (PoHeaderViewBean) obj;
      orderInputBean.setShipto_Name(poHeaderBean.getShiptoAddressLine1());
			orderInputBean.setShipto_Address1(poHeaderBean.getShiptoAddressLine2());
      orderInputBean.setShipto_Address2(poHeaderBean.getShiptoAddressLine3());
      orderInputBean.setShipto_City(poHeaderBean.getShiptoCity());
		  orderInputBean.setShipto_State(poHeaderBean.getShiptoStateAbbrev());
      orderInputBean.setShipto_Zip(poHeaderBean.getShiptoZip());
    }

		XbuyViewBean lineDetailbean = null;
		for (Object obj : orderDataCollection) {
			if (obj == null)
				continue;
			lineDetailbean = (XbuyViewBean) obj;
			if (lineDetailbean.getSupplierAccountNumber() != null && lineDetailbean.getSupplierAccountNumber().length() > 0
          && !lineDetailbean.getShipToLocationId().equalsIgnoreCase("10023092"))
      {
        orderInputBean.setHaaspo(lineDetailbean.getRadianPo()+"-"+lineDetailbean.getPoLine());
        orderInputBean.setAccount(lineDetailbean.getSupplierAccountNumber());
        orderInputBean.setOrder_priorityCode("IPG "+lineDetailbean.getPriorityRating());
        if (lineDetailbean.getUnitPrice().intValue() == 0)
        {
         orderInputBean.setInventoryLocation(lineDetailbean.getVmiLocationCode());
        }
        else
        {
         orderInputBean.setInventoryLocation(lineDetailbean.getSupplierLocationCode());
        }
        orderInputBean.setRegion(lineDetailbean.getSupplierRegionCode());
        orderInputBean.setPartNum(lineDetailbean.getSupplierPartNo());
        orderInputBean.setOrder_orderPrice(""+lineDetailbean.getUnitPrice().toString());
        /*Check to see what happens when we pass null*/
        orderInputBean.setOrder_uom(lineDetailbean.getUom());
        orderInputBean.setShipDate(DateHandler.formatDate(lineDetailbean.getVendorShipDate(), "MM/dd/yyyy") );
        orderInputBean.setOrder_orderQty(""+lineDetailbean.getQuantity());
        orderInputBean.setOrder_requiredDate(DateHandler.formatDate(lineDetailbean.getVendorShipDate(), "MM/dd/yyyy") );
        String comments = "";
        comments += "IPG "+lineDetailbean.getPriorityRating()
                 + ". Desired ship date "+DateHandler.formatDate(lineDetailbean.getVendorShipDate(), "MM/dd/yyyy")+".\n";
        if (lineDetailbean.getOriginalTransactionType().equalsIgnoreCase("850"))
        {
          comments += "Order Type: Commercial \n";
        }
        else if (lineDetailbean.getOriginalTransactionType().equalsIgnoreCase("940"))
        {
          comments += "Order Type: VMI \n";
        }
        
        if (lineDetailbean.getOriginInspectionRequired() !=null &&
            lineDetailbean.getOriginInspectionRequired().equalsIgnoreCase("y"))
        {
         comments +=" Origin Inspection Required. VMI must be used to fill this order unless NSN is depleted at all regions.\n";
        }
         if (lineDetailbean.getDeliveryComments() !=null)
         {
           comments += lineDetailbean.getDeliveryComments();
         }
        if (lineDetailbean.getNotes() !=null)
        {
          comments += "\n"+lineDetailbean.getNotes();
        }
        orderInputBean.setNotes(comments);

        orderInputBean.setSharesecret("airgas-haas-xml");
        orderInputBean.setOrderType("0");
        orderInputBean.setOrder_companyName("HAAS TCM");

        Vector orderResultColl = process.OrderSubmit(orderInputBean);
        String supplierOrderNo = ((OrderSubmitResultViewBean)orderResultColl.get(0)).getOrder_number();
        String airGasErrorMsg = ((OrderSubmitResultViewBean)orderResultColl.get(0)).getErrorMessage();
        log.info("Haas PO "+orderInputBean.getHaaspo()+" supplierOrderNo "+supplierOrderNo+" airGasErrorMsg "+airGasErrorMsg+" ");
        if (airGasErrorMsg.length() > 0)
        {
            bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
			      "Error sending Xbuy for PO " +inputBean.getRadianPo()+" supplierOrderNo  "+supplierOrderNo+" PART "+
                lineDetailbean.getSupplierPartNo()+" UOM "+lineDetailbean.getUom()+" ",
            "Error sending Xbuy for PO " +inputBean.getRadianPo()+" supplierOrderNo  "+supplierOrderNo+" PART "+
                lineDetailbean.getSupplierPartNo()+" UOM "+lineDetailbean.getUom()+" Account Number" + lineDetailbean.getSupplierAccountNumber()+
                " Region " + lineDetailbean.getSupplierRegionCode()+" Location Code " + lineDetailbean.getSupplierLocationCode()+
                " airGasErrorMsg "+airGasErrorMsg+" ", false);

          if (airGasErrorMsg.indexOf("deactivated") >= 0 || airGasErrorMsg.indexOf("No response") >= 0
                  || airGasErrorMsg.indexOf("not on file") >= 0 || airGasErrorMsg.indexOf("not assigned") >= 0 || airGasErrorMsg.indexOf("not defined") >= 0
                  || airGasErrorMsg.indexOf("Credit Hold") >= 0 || airGasErrorMsg.indexOf("block") >= 0  || airGasErrorMsg.indexOf("pricing") >= 0)  
          {
            bulkMailProcess.sendBulkEmail(new BigDecimal("15487"),
			      "Error sending Xbuy for PO " +inputBean.getRadianPo()+"",
            "Error sending Xbuy for PO " +inputBean.getRadianPo()+" Account Number" + lineDetailbean.getSupplierAccountNumber()+
                " Region " + lineDetailbean.getSupplierRegionCode()+" Location Code " + lineDetailbean.getSupplierLocationCode()+
                " airGasErrorMsg "+airGasErrorMsg+" ", false);
        }
        }

        if (supplierOrderNo !=null && !supplierOrderNo.equalsIgnoreCase("-1"))
        {
          errMsg = "OK";
          //Update po_line with supplier sales order number.
          poLineDraftBean.setRadianPo(lineDetailbean.getRadianPo());
          poLineDraftBean.setPoLine(lineDetailbean.getPoLine());
          poLineDraftBean.setSupplierSalesOrderNo(supplierOrderNo);

          try
          {
          poLineFactory.updateSuppSalesOrderNo(poLineDraftBean);
          }
          catch (Exception poEx) {
				    log.error("updateSuppSalesOrderNo error:" + poEx.getMessage());
            bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
			      "Error no updateSuppSalesOrderNo for PO " +inputBean.getRadianPo()+" supplierOrderNo  "+supplierOrderNo+"",
            "Error no updateSuppSalesOrderNo for PO " +inputBean.getRadianPo()+" supplierOrderNo  "+supplierOrderNo+"", false);
          }

          try
          {
          poLineFactory.confirmXbuyOrder(poLineDraftBean);
          }
          catch (Exception poEx) {
				    log.error("confirmXbuyOrder error:" + poEx.getMessage());
            bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
			      "Error confirmXbuyOrder for PO " +inputBean.getRadianPo()+"",
            "Error confirmXbuyOrder for PO " +inputBean.getRadianPo()+" "+poEx.getMessage()+"", false);
          }

          try
          {           
           poLineFactory.updateDateSent(poLineDraftBean);
          }
          catch (Exception poEx) {
				    log.error("updateDateSent error:" + poEx.getMessage());
            bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
			      "Error updateDateSent for PO " +inputBean.getRadianPo()+"",
            "Error updateDateSent for PO " +inputBean.getRadianPo()+"", false);
          }

          PoHeaderViewBeanFactory poHeaderViewBeanFactory = new PoHeaderViewBeanFactory(dbManager);
          PoHeaderViewBean poHeaderViewBean = new PoHeaderViewBean();
          poHeaderViewBean.setRadianPo(lineDetailbean.getRadianPo());
          poHeaderViewBeanFactory.allocatePOLine(poHeaderViewBean);
        }
        else
        {
          errMsg = "Error submitting order";
        }
      }
      else
      {
        bulkMailProcess.sendBulkEmail(new BigDecimal("86405"),
			  "Error no supplierAccountNumber for PO or shipping to Harrisburg " +inputBean.getRadianPo()+"",
        "Error no supplierAccountNumber for PO or shipping to Harrisburg " +inputBean.getRadianPo()+"", false);
      }
    }
    }

    if (!errMsg.equalsIgnoreCase("OK"))
    {
     poLineFactory.updateBuyOrderStatus(poLineDraftBean,"ProblemXBuy");
    }
    return errMsg;
  }
  public static void main(String[] args){
	  XBuyOrderProcess p = new XBuyOrderProcess("TCM_OPS");
	  try {
		  p.submitStockQuery();
	  }
	  catch(Exception e){}
  }
} //end of class