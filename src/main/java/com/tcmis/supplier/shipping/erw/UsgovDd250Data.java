package com.tcmis.supplier.shipping.erw;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.UsgovDd250ViewBean;


/******************************************************************************
 * CLASSNAME: UsgovDd250ViewBean <br>
 * @version: 1.0, Nov 26, 2007 <br>
 *****************************************************************************/


public class UsgovDd250Data {

  private String inventoryGroup;
	private String prNumber;
	private String lineItem;
	private String itemNo;
	private String catPartNo;
	private String partShortName;
	private String shippingContainers;
	private String quantity;
	private String unitOfSale;
	private String unitPrice;
	private String amount;
	private String acceptancePoint;
	private String shipDate;
	private String shipmentNumber;
	private String carrierName;
	private String trackingNumber;
	private String tcn;
	private String primeContractor;
	private String primeContractorDodaac;
	private String primeContractorLine1;
	private String primeContractorLine2;
	private String primeContractorLine3;
	private String contractNumber;
	private String orderNumber;
	private String page;
	private String numberOfPages;
	private String shipFromDodaac;
	private String shipFromCompanyId;
	private String shipFromLocationId;
	private String igdCompanyId;
	private String igdLocationId;
	private String shipFromLine1;
	private String shipFromLine2;
	private String shipFromLine3;
	private String shipFromLine4;
	private String shipToDodaac;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String shipToLine1;
	private String shipToLine2;
	private String shipToLine3;
	private String shipToLine4;
	private String shipViaDodaac;
	private String shipViaCompanyId;
	private String shipViaLocationId;
	private String shipViaLine1;
	private String shipViaLine2;
	private String shipViaLine3;
	private String shipViaLine4;
	private String adminDodaac;
	private String adminLine1;
	private String adminLine2;
	private String adminLine3;
	private String payerDodaac;
	private String payerLine1;
	private String payerLine2;
	private String payerLine3;
	private String deliveryTicket;
	private String originInspectionRequired;

  public UsgovDd250Data(UsgovDd250ViewBean usgovDd250ViewBean) {
	prNumber= NumberHandler.emptyIfNull(usgovDd250ViewBean.getPrNumber());
	lineItem= StringHandler.emptyIfNull(usgovDd250ViewBean.getLineItem());
	catPartNo= StringHandler.emptyIfNull(usgovDd250ViewBean.getCatPartNo());
	quantity= NumberHandler.emptyIfNull(usgovDd250ViewBean.getQuantity());
	unitOfSale= StringHandler.emptyIfNull(usgovDd250ViewBean.getUnitOfSale());
	unitPrice= NumberHandler.emptyIfNull(usgovDd250ViewBean.getUnitPrice());
	amount= NumberHandler.emptyIfNull(usgovDd250ViewBean.getAmount());
	shipDate= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipDate());
	shipmentNumber= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipmentNumber());
	carrierName= StringHandler.emptyIfNull(usgovDd250ViewBean.getCarrierName());
	trackingNumber= StringHandler.emptyIfNull(usgovDd250ViewBean.getTrackingNumber());
	tcn= StringHandler.emptyIfNull(usgovDd250ViewBean.getTcn());
	primeContractor= StringHandler.emptyIfNull(usgovDd250ViewBean.getPrimeContractor());
	contractNumber= StringHandler.emptyIfNull(usgovDd250ViewBean.getContractNumber());
	orderNumber= StringHandler.emptyIfNull(usgovDd250ViewBean.getOrderNumber());
	page= StringHandler.emptyIfNull(usgovDd250ViewBean.getPage());
	numberOfPages= StringHandler.emptyIfNull(usgovDd250ViewBean.getNumberOfPages());
	shipFromDodaac= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipFromDodaac());
	shipFromCompanyId= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipFromCompanyId());
	shipFromLocationId= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipFromLocationId());
	igdCompanyId= StringHandler.emptyIfNull(usgovDd250ViewBean.getIgdCompanyId());
	igdLocationId= StringHandler.emptyIfNull(usgovDd250ViewBean.getIgdLocationId());
	shipFromLine1= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipFromLine1());
	shipFromLine2= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipFromLine2());
	shipFromLine3= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipFromLine3());
	shipFromLine4= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipFromLine4());
	shipToDodaac= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipToDodaac());
	shipToCompanyId= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipToCompanyId());
	shipToLocationId= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipToLocationId());
	shipToLine1= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipToLine1());
	shipToLine2= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipToLine2());
	shipToLine3= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipToLine3());
	shipToLine4= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipToLine4());
	shipViaDodaac= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipViaDodaac());
	shipViaCompanyId= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipViaCompanyId());
	shipViaLocationId= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipViaLocationId());
	shipViaLine1= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipViaLine1());
	shipViaLine2= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipViaLine2());
	shipViaLine3= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipViaLine3());
	shipViaLine4= StringHandler.emptyIfNull(usgovDd250ViewBean.getShipViaLine4());
	partShortName= StringHandler.emptyIfNull(usgovDd250ViewBean.getPartShortName())+" ("+StringHandler.emptyIfNull(usgovDd250ViewBean.getShippingContainers())+")";
	primeContractorDodaac= StringHandler.emptyIfNull(usgovDd250ViewBean.getPrimeContractorDodaac());
	primeContractorLine1= StringHandler.emptyIfNull(usgovDd250ViewBean.getPrimeContractorLine1());
	primeContractorLine2= StringHandler.emptyIfNull(usgovDd250ViewBean.getPrimeContractorLine2());
	primeContractorLine3= StringHandler.emptyIfNull(usgovDd250ViewBean.getPrimeContractorLine3());
  itemNo= StringHandler.emptyIfNull(usgovDd250ViewBean.getItemNo());
  shippingContainers= StringHandler.emptyIfNull(usgovDd250ViewBean.getShippingContainers());
  acceptancePoint= StringHandler.emptyIfNull(usgovDd250ViewBean.getAcceptancePoint());
  adminDodaac= StringHandler.emptyIfNull(usgovDd250ViewBean.getAdminDodaac());
	adminLine1= StringHandler.emptyIfNull(usgovDd250ViewBean.getAdminLine1());
	adminLine2= StringHandler.emptyIfNull(usgovDd250ViewBean.getAdminLine2());
	adminLine3= StringHandler.emptyIfNull(usgovDd250ViewBean.getAdminLine3());
	payerDodaac= StringHandler.emptyIfNull(usgovDd250ViewBean.getPayerDodaac());
	payerLine1= StringHandler.emptyIfNull(usgovDd250ViewBean.getPayerLine1());
	payerLine2= StringHandler.emptyIfNull(usgovDd250ViewBean.getPayerLine2());
	payerLine3= StringHandler.emptyIfNull(usgovDd250ViewBean.getPayerLine3());
  deliveryTicket= "Delivery Ticket: " +StringHandler.emptyIfNull(usgovDd250ViewBean.getDeliveryTicket());
  deliveryTicket += " "+ StringHandler.emptyIfNull(usgovDd250ViewBean.getNotes());
  originInspectionRequired =StringHandler.emptyIfNull(usgovDd250ViewBean.getOriginInspectionRequired());
  if (originInspectionRequired.equalsIgnoreCase("Y") && StringHandler.emptyIfNull(usgovDd250ViewBean.getOriginalTransactionType()).equalsIgnoreCase("850") )
  {
    originInspectionRequired = "See WAWF";
  }
  else
  {
    originInspectionRequired = "";
  }   
  }

	public static Vector getFieldVector() {
	Vector v = new Vector(60);
	v.addElement("prNumber=getPrNumber");
	v.addElement("lineItem= getLineItem");
	v.addElement("catPartNo= getCatPartNo");
	v.addElement("quantity= getQuantity");
	v.addElement("unitOfSale= getUnitOfSale");
	v.addElement("unitPrice= getUnitPrice");
	v.addElement("amount= getAmount");
	v.addElement("shipDate= getShipDate");
	v.addElement("shipmentNumber= getShipmentNumber");
	v.addElement("carrierName= getCarrierName");
	v.addElement("trackingNumber= getTrackingNumber");
	v.addElement("tcn= getTcn");
	v.addElement("primeContractor= getPrimeContractor");
	v.addElement("contractNumber= getContractNumber");
	v.addElement("orderNumber= getOrderNumber");
	v.addElement("page= getPage");
	v.addElement("numberOfPages= getNumberOfPages");
	v.addElement("shipFromDodaac= getShipFromDodaac");
	v.addElement("shipFromCompanyId= getShipFromCompanyId");
	v.addElement("shipFromLocationId= getShipFromLocationId");
	v.addElement("igdCompanyId= getIgdCompanyId");
	v.addElement("igdLocationId= getIgdLocationId");
	v.addElement("shipFromLine1= getShipFromLine1");
	v.addElement("shipFromLine2= getShipFromLine2");
	v.addElement("shipFromLine3= getShipFromLine3");
	v.addElement("shipFromLine4= getShipFromLine4");
	v.addElement("shipToDodaac= getShipToDodaac");
	v.addElement("shipToCompanyId= getShipToCompanyId");
	v.addElement("shipToLocationId= getShipToLocationId");
	v.addElement("shipToLine1= getShipToLine1");
	v.addElement("shipToLine2= getShipToLine2");
	v.addElement("shipToLine3= getShipToLine3");
	v.addElement("shipToLine4= getShipToLine4");
	v.addElement("shipViaDodaac= getShipViaDodaac");
	v.addElement("shipViaCompanyId= getShipViaCompanyId");
	v.addElement("shipViaLocationId= getShipViaLocationId");
	v.addElement("shipViaLine1= getShipViaLine1");
	v.addElement("shipViaLine2= getShipViaLine2");
	v.addElement("shipViaLine3= getShipViaLine3");
	v.addElement("shipViaLine4= getShipViaLine4");
	v.addElement("partShortName= getPartShortName");
	v.addElement("primeContractorDodaac= getPrimeContractorDodaac");
	v.addElement("primeContractorLine1= getPrimeContractorLine1");
	v.addElement("primeContractorLine2= getPrimeContractorLine2");
	v.addElement("primeContractorLine3= getPrimeContractorLine3");  
  v.addElement("itemNo= getItemNo");
  v.addElement("shippingContainers= getShippingContainers");
  v.addElement("acceptancePoint= getAcceptancePoint");
  v.addElement("adminDodaac= getAdminDodaac");
	v.addElement("adminLine1= getAdminLine1");
	v.addElement("adminLine2= getAdminLine2");
	v.addElement("adminLine3= getAdminLine3");
	v.addElement("payerDodaac= getPayerDodaac");
	v.addElement("payerLine1= getPayerLine1");
	v.addElement("payerLine2= getPayerLine2");
	v.addElement("payerLine3= getPayerLine3");
  v.addElement("deliveryTicket= getDeliveryTicket");
  v.addElement("originInspectionRequired= getOriginInspectionRequired");    
  return v;
	}

	public static Vector getVector(Collection in) {
	 Vector out = new Vector();
	 Iterator iterator = in.iterator();
	 while (iterator.hasNext()) {
		 out.addElement(new UsgovDd250Data( (UsgovDd250ViewBean) iterator.next()));
	 }
	 return out;
	 }

	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getItemNo() {
		return itemNo;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getShippingContainers() {
		return shippingContainers;
	}
	public String getQuantity() {
		return quantity;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public String getAmount() {
		return amount;
	}
	public String getAcceptancePoint() {
		return acceptancePoint;
	}
	public String getShipDate() {
		return shipDate;
	}
	public String getShipmentNumber() {
		return shipmentNumber;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getTcn() {
		return tcn;
	}
	public String getPrimeContractor() {
		return primeContractor;
	}
	public String getPrimeContractorDodaac() {
		return primeContractorDodaac;
	}
	public String getPrimeContractorLine1() {
		return primeContractorLine1;
	}
	public String getPrimeContractorLine2() {
		return primeContractorLine2;
	}
	public String getPrimeContractorLine3() {
		return primeContractorLine3;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public String getPage() {
		return page;
	}
	public String getNumberOfPages() {
		return numberOfPages;
	}
	public String getShipFromDodaac() {
		return shipFromDodaac;
	}
	public String getShipFromCompanyId() {
		return shipFromCompanyId;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getIgdCompanyId() {
		return igdCompanyId;
	}
	public String getIgdLocationId() {
		return igdLocationId;
	}
	public String getShipFromLine1() {
		return shipFromLine1;
	}
	public String getShipFromLine2() {
		return shipFromLine2;
	}
	public String getShipFromLine3() {
		return shipFromLine3;
	}
	public String getShipFromLine4() {
		return shipFromLine4;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipToLine1() {
		return shipToLine1;
	}
	public String getShipToLine2() {
		return shipToLine2;
	}
	public String getShipToLine3() {
		return shipToLine3;
	}
	public String getShipToLine4() {
		return shipToLine4;
	}
	public String getShipViaDodaac() {
		return shipViaDodaac;
	}
	public String getShipViaCompanyId() {
		return shipViaCompanyId;
	}
	public String getShipViaLocationId() {
		return shipViaLocationId;
	}
	public String getShipViaLine1() {
		return shipViaLine1;
	}
	public String getShipViaLine2() {
		return shipViaLine2;
	}
	public String getShipViaLine3() {
		return shipViaLine3;
	}
	public String getShipViaLine4() {
		return shipViaLine4;
	}
	public String getAdminDodaac() {
		return adminDodaac;
	}
	public String getAdminLine1() {
		return adminLine1;
	}
	public String getAdminLine2() {
		return adminLine2;
	}
	public String getAdminLine3() {
		return adminLine3;
	}
	public String getPayerDodaac() {
		return payerDodaac;
	}
	public String getPayerLine1() {
		return payerLine1;
	}
	public String getPayerLine2() {
		return payerLine2;
	}
	public String getPayerLine3() {
		return payerLine3;
	}
  public String getDeliveryTicket() {
		return deliveryTicket;
	}
	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}  
}