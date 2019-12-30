package com.tcmis.client.aerojet.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.aerojet.beans.AddressBean;
import com.tcmis.client.aerojet.beans.LineItemBean;
import com.tcmis.client.aerojet.beans.PurchaseOrderBean;
import com.tcmis.client.aerojet.beans.ScheduleLineItemBean;
import com.tcmis.client.order.beans.CustomerPoPreStageBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

public class PoXmlSubmitWorkerProcess implements Runnable {
    Log log = LogFactory.getLog(this.getClass());

	PurchaseOrderBean bean;
	BigDecimal personnelId;
	BigDecimal nextupLoadSeq;
	String client = null;
	//String url = null;
	
	public PoXmlSubmitWorkerProcess(PurchaseOrderBean bean, String client){
		this.bean = bean;
		this.client = client;
	}
	
	public void run() {
		Collection customerPoPreStageBeanCol;
		PoXmlSubmitProcess process = new PoXmlSubmitProcess(client);
		try {
			BigDecimal loadId = process.getNextLoadIdSeq();
			log.debug("Load id for customerPoPreStage :" + loadId);
			String errorMessage = "";			
			//flatten the bean to insert into the customer_po_pre_stage table
			customerPoPreStageBeanCol = generateCustomerPoPreStageBean(bean, loadId, process);
			if (log.isDebugEnabled()) 
	            log.debug("Flat collection size:" + customerPoPreStageBeanCol.size());

			if ( bean.getVerb().equalsIgnoreCase("PROCESS") && bean.getRevision().equalsIgnoreCase("007")) 
				errorMessage = process.processData(customerPoPreStageBeanCol, loadId);		        
			else if (bean.getVerb().equalsIgnoreCase("CHANGE") && bean.getRevision().equalsIgnoreCase("006")) 
		    	errorMessage = process.processChangeData(customerPoPreStageBeanCol, loadId);		    	
			
	        log.debug("errorMessage from processData - " + errorMessage);
	        
		} catch(Exception ex){
			ex.printStackTrace();
			log.error(ex);
		}
	}
	
	
	private Collection generateCustomerPoPreStageBean(PurchaseOrderBean poBean, BigDecimal loadId, PoXmlSubmitProcess process) throws BaseException {
	    //DbManager dbManager = new DbManager(this.client);
	    //BigDecimal loadId = new BigDecimal(dbManager.getOracleSequence("CUSTOMER_PO_LOAD_SEQ"));
		SimpleDateFormat formatDateTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
	    Collection collection = new Vector();
	    
	    //since I need to "flatten" this bean into multiple "flat" beans I'll add the beans
	    //to the collection in the iteraton of the schedule at the bottom of this method
	    CustomerPoPreStageBean customerPoPreStageBean = new CustomerPoPreStageBean();
	    //set the data related to new order (850) and change order (860)
	    if (poBean.getVerb().equalsIgnoreCase("PROCESS") && poBean.getRevision().equalsIgnoreCase("007")) {
		    customerPoPreStageBean.setTransactionType("850");
		    customerPoPreStageBean.setStatus("NEW");
	    } else if (poBean.getVerb().equalsIgnoreCase("CHANGE") && poBean.getRevision().equalsIgnoreCase("006")) {
	    	customerPoPreStageBean.setTransactionType("860");
	    	customerPoPreStageBean.setStatus("IGNORE");
	    	customerPoPreStageBean.setProcessingComments("XML UPLOAD - We do not process 860-change orders for Aerojet.");
	    }
	    
	    //first copy the "header" bean
	    customerPoPreStageBean.setCustomerPoNo(poBean.getPoId());
	    customerPoPreStageBean.setDateIssued(poBean.getDateTime("CREATION", formatDate));	    
	    //customerPoPreStageBean.setPoTypeCode(poBean.getPoType());
	    customerPoPreStageBean.setCurrencyId(poBean.getOperAmtBean().getCurrency());
	    //soldto
	    AddressBean buyer = poBean.getAddressBean("SoldTo");
	    customerPoPreStageBean.setBuyerPartyName(buyer.getPartnerName());
	    customerPoPreStageBean.setBuyerNameOnPo(process.getApplicationLogonId(buyer.getContactPerson().getName()));
	    //customerPoPreStageBean.setBuyerNameOnPo(buyer.getPartnerName());
	    customerPoPreStageBean.setBuyerAddress1(buyer.getAddress1());
	    customerPoPreStageBean.setBuyerAddress2(buyer.getAddress2());
	    customerPoPreStageBean.setBuyerAddress3(buyer.getAddress3());
	    customerPoPreStageBean.setBuyerZip(buyer.getZip());
	    customerPoPreStageBean.setBuyerCity(buyer.getCity());
	    customerPoPreStageBean.setBuyerState(buyer.getState());
	    customerPoPreStageBean.setBuyerRegion(buyer.getRegion());
	    customerPoPreStageBean.setBuyerCountry(buyer.getCountry());
	    
	    AddressBean seller = poBean.getAddressBean("Supplier");
	    //customerPoPreStageBean.setCustomerHaasContractId(poBean.getSellerIdent());
        customerPoPreStageBean.setCustomerHaasAccountNo(seller.getPartnerId());
        customerPoPreStageBean.setSellerIdOnPo(seller.getPartnerId());
	    customerPoPreStageBean.setSellerPartyName(seller.getPartnerName());
	    customerPoPreStageBean.setSellerNameOnPo(seller.getPartnerName());
	    customerPoPreStageBean.setSellerAddress1(seller.getAddress1());
	    customerPoPreStageBean.setSellerAddress2(seller.getAddress2());
	    customerPoPreStageBean.setSellerZip(seller.getZip());
	    customerPoPreStageBean.setSellerCity(seller.getCity());
	    customerPoPreStageBean.setSellerState(seller.getState());
	    customerPoPreStageBean.setSellerRegion(seller.getRegion());
	    customerPoPreStageBean.setSellerCountry(seller.getCountry());
	    	    
	    //shipto only at schedule line level and not at the header level
	    //billto
	    AddressBean billto = poBean.getAddressBean("BillTo");
	    customerPoPreStageBean.setBilltoPartyId(billto.getPartnerId());
	    customerPoPreStageBean.setBilltoParty(billto.getPartnerName());
	    customerPoPreStageBean.setBilltoAddress1(billto.getAddress1());
	    customerPoPreStageBean.setBilltoAddress2(billto.getAddress2());
	    customerPoPreStageBean.setBilltoAddress3(billto.getAddress3());
	    customerPoPreStageBean.setBilltoZip(billto.getZip());
	    customerPoPreStageBean.setBilltoCity(billto.getCity());
	    customerPoPreStageBean.setBilltoState(billto.getState());
	    customerPoPreStageBean.setBilltoRegion(billto.getRegion());
	    customerPoPreStageBean.setBilltoCountry(billto.getCountry());

	    //customerPoPreStageBean.setFreightOnBoardNotes(poBean.getFobDescription());
	    //customerPoPreStageBean.setFreightOnBoard(poBean.getFobId());
	    //customerPoPreStageBean.setPaymentTerms(poBean.getPaymentTermsId());
	    //customerPoPreStageBean.setPaymentTermsNotes(poBean.getPaymentTermsDesc());
	    customerPoPreStageBean.setCustomerPoNote(poBean.getHeaderDescription());
	    
	    customerPoPreStageBean.setTotalAmountOnPo(poBean.getOperAmtBean().getDecimalValue());
	    customerPoPreStageBean.setLoadId(loadId);
	    customerPoPreStageBean.setDateInserted(new Date());
	    //this is for a constraint on the customer_po_pre_stage table
	    customerPoPreStageBean.setTransport("XML");  
	    customerPoPreStageBean.setTransporterAccount("Aerojet");
	    customerPoPreStageBean.setTradingPartner("Aerojet");
	    customerPoPreStageBean.setTradingPartnerId("Aerojet");
	    customerPoPreStageBean.setPre860("N");
	    customerPoPreStageBean.setCompanyId("AEROJET");
	    customerPoPreStageBean.setChangeOrderSequence(new BigDecimal("0"));
	    //customerPoPreStageBean.setOrderDate(poBean.getDateTime("DOCUMENT", formatDate));
	    //copy detail
	    int loadLine = 1;
	    //int lineSequence = 1;
	    Iterator detailIterator = poBean.getLineItemBeanCollection().iterator();
	    while (detailIterator.hasNext()) {
	      LineItemBean detailBean = (LineItemBean) detailIterator.next();
	      customerPoPreStageBean.setCustomerPoLineNo(detailBean.getPoLineNumber());
	      customerPoPreStageBean.setManufacturerPartNum(detailBean.getSupplierItemNumber());
	      customerPoPreStageBean.setHaasItemNo(detailBean.getSupplierItemNumber());
	      customerPoPreStageBean.setItemDescription(detailBean.getItemDescription());
	      customerPoPreStageBean.setQuantity(detailBean.getLineOrderedQuantityBean().getDecimalValue());
	      customerPoPreStageBean.setUom(detailBean.getLineOrderedQuantityBean().getUom());
	      customerPoPreStageBean.setUnitPrice(detailBean.getLineItemAmtBean().getDecimalValue());
	      customerPoPreStageBean.setCurrencyId(detailBean.getLineItemAmtBean().getCurrency());
	      customerPoPreStageBean.setCustomerPoLineNote(detailBean.getFullNotetoSupplier());
	      customerPoPreStageBean.setLoadLine(new BigDecimal(loadLine));
	      //customerPoPreStageBean.setLineSequence(new BigDecimal(lineSequence));
	      //customerPoPreStageBean.setCustomerHaasContractId(detailBean.getContractNumber());
	      customerPoPreStageBean.setCatPartNo(detailBean.getItemNumber());
	      
	      //copy schedule
	      int lineSequence = 1;
	      //int loadLine = 1;
	      Iterator scheduleIterator = detailBean.getScheduleLineItemBeanCollection().iterator();
	      while (scheduleIterator.hasNext()) {
	        //this is the last iterator so I need to copy the attributes to a different bean
	        //and add that bean to the collection
	        CustomerPoPreStageBean flatBean = new CustomerPoPreStageBean();
	        BeanHandler.copyAttributes(customerPoPreStageBean, flatBean);
	        ScheduleLineItemBean scheduleBean = (ScheduleLineItemBean)scheduleIterator.next();
	        flatBean.setLineSequence(new BigDecimal(lineSequence));
	        //flatBean.setLoadLine(new BigDecimal(loadLine));
	        flatBean.setScheduleQuantity(scheduleBean.getScheduleQuantityBean().getDecimalValue());
	        flatBean.setScheduleUom(scheduleBean.getScheduleQuantityBean().getUom());	        
	        flatBean.setRequestedDeliveryDate(scheduleBean.getDateTime("NEEDDELV", formatDate)); //--populating the need date in both the columns
	        flatBean.setDesiredDeliveryDate(scheduleBean.getDateTime("NEEDDELV", formatDateTime));
	        flatBean.setDesiredShipDate(scheduleBean.getDateTime("PROMSHIP", formatDateTime));	        
	        AddressBean shipTo = scheduleBean.getAddressBean("ShipTo");
			flatBean.setShiptoPartyId(shipTo.getZip());
			flatBean.setShiptoPartyName(shipTo.getPartnerName());
			flatBean.setShiptoAddress1(shipTo.getAddress1());
			flatBean.setShiptoAddress2(shipTo.getAddress2());
			flatBean.setShiptoAddress3(shipTo.getAddress3());	          
			flatBean.setShiptoZip(shipTo.getZip());
			flatBean.setShiptoCity(shipTo.getCity());
			flatBean.setShiptoState(shipTo.getState());
			flatBean.setShiptoRegion(shipTo.getRegion());
			flatBean.setShiptoCountry(shipTo.getCountry());
			//flatBean.setShiptoContactName(shipTo.getContactPerson().getName());
			//flatBean.setChargeNumber1(scheduleBean.getProjectNumber());
			//flatBean.setChargeNumber2(scheduleBean.getProjectType());
            flatBean.setChargeNumber1("PO XML");
            flatBean.setChargeNumber2(scheduleBean.getProjectNumber());
			flatBean.setChargeNumber3(scheduleBean.getProjectTask());
			if (StringHandler.isBlankString(scheduleBean.getApplication()))
				flatBean.setApplication("WAREHOUSE");
			else 
				flatBean.setApplication(scheduleBean.getApplication());			
			flatBean.setOwnerSegmentId(scheduleBean.getOwnerSegmentId());
			flatBean.setRequestorName(scheduleBean.getProjectRequestorName());
			flatBean.setBuyerEmail(scheduleBean.getProjectRequestorEmail());
	        collection.add(flatBean);
	        lineSequence++;
	        //loadLine++;
	      }
	      loadLine++;
	      //lineSequence++;
	    }
	    //In case there are no line items or schedule line items in the po received from aerojet
	    if(collection.size() == 0) {
	      collection.add(customerPoPreStageBean);
	    }
	    return collection;
	}
	
}
