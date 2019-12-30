package com.tcmis.internal.hub.erw;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PicklistViewBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class PickListData
{
	private String picklistId;
	private String hub;
	private String inventoryGroup;
	private String prNumber;
	private String lineItem;
	private String mrLine;
	private String receiptId;
	private String itemId;
	private String bin;
	private String mfgLot;
	private String lotStatus;
	private String expireDate;
	private String stockingMethod;
	private String deliveryType;
	private String needDate;
	private String picklistQuantity;
	private String quantityPicked;
	private String qcDate;
	private String application;
	private String facilityId;
	private String partDescription;
	private String packaging;
	private String inventoryQuantity;
	private String catalogId;
	private String catPartNo;
	private String partGroupNo;
	private String companyId;
	private String catalogCompanyId;
	private String deliveryPoint;
	private String shipToLocationId;
	private String requestor;
	private String endUser;
	private String transferRequestId;
	private String mrNotes;
	private String critical;
	private String certificationDate;
	private String pickable;
	private String certifiedBy;
	private String certificationNumber;
	private String qualityControlItem;
	private String facLocAppPartComment;
	private String catPartComment;
	private String cabinetReplenishment;
	private String hazmatIdMissing;
	private String receiptDocumentAvailable;
	private String nonintegerReceiving;
	private String recertNumber;
	private String carrierCode;
	private String pickupTime;
	private String stopNumber;
	private String trailerNumber;
	private String packingGroupId;
	private String carrierName;
	private String transportationMode;
	private String requiresOverpack;
	private String shippedAsSingle;
	private String shipToLocationDesc;
	private String shipToCity;
	private String shipToStateAbbrev;
	private String shipToZip;
	private String transfer;
    private String packingGroupIdBarcode;
	private String shipToDodaac;
	private String packagedAs;
	private String maxUnitOfIssuePerBox;
	private String maxUnitOfIssuePerPallet;
	private String consolidationNumber;
	private String mrCount;
	private String oconusFlag;
	private String milstripCode;
	private String trackingNumber;
	private String dot;
	private String transportationPriority;
	private String hazardous;
	private String rddComment;
	private String poNumber;
	private String splitTcn;
	private String airGroundIndicator;
	private String customerServiceRepId;
	private String customerServiceRepName;
	private String opsEntityId;
	private String operatingEntityName;
	private String customerId;
	private String customerName;
	private String facilityName;
	private String inventoryGroupName;
	private String customerReceiptId;
	private String requestorName;
	private String requestorPhone;
	private String requestorFax;
	private String requestorEmail;
	private String paymentTerms;
	private String specialInstructions;
	private String carrierAccountId;
	private String carrierContact;
	private String carrierServiceType;
	private String incoTerms;
	private String flashPoint;
	private String addressLine1Display;
	private String addressLine2Display;
	private String addressLine3Display;
	private String addressLine4Display;
	private String addressLine5Display;
	private String materialRequestOrigin;
	private String submittedBy;
	private String submittedByName;
	private String hubBinRoom;
	private String logoImageUrl;
	private String chargeFreight;

  public PickListData(PicklistViewBean bean) {
    splitTcn=NumberHandler.emptyIfNull(bean.getSplitTcn());
    if (splitTcn.equalsIgnoreCase("1"))
    {
      splitTcn = "Y";
    }
    else if (splitTcn.equalsIgnoreCase("0"))
    {
      splitTcn = "N";
    }
    picklistId=NumberHandler.emptyIfNull(bean.getPicklistId());
    hub=StringHandler.emptyIfNull(bean.getHub());
    inventoryGroup=StringHandler.emptyIfNull(bean.getInventoryGroup());
    prNumber=NumberHandler.emptyIfNull(bean.getPrNumber());
    lineItem=StringHandler.emptyIfNull(bean.getLineItem());
    mrLine=StringHandler.emptyIfNull(bean.getMrLine());
    receiptId=NumberHandler.emptyIfNull(bean.getReceiptId());
    itemId=NumberHandler.emptyIfNull(bean.getItemId());
    bin=StringHandler.emptyIfNull(bean.getBin());
    mfgLot=StringHandler.emptyIfNull(bean.getMfgLot());
    lotStatus=StringHandler.emptyIfNull(bean.getLotStatus());
    expireDate=DateHandler.formatDate(bean.getExpireDate(),"MM/dd/yyyy");
    if (expireDate !=null && expireDate.equalsIgnoreCase("01/01/3000"))
    {
     expireDate = "INDEFINITE";
    }
    stockingMethod=StringHandler.emptyIfNull(bean.getStockingMethod());
    deliveryType=StringHandler.emptyIfNull(bean.getDeliveryType());
    needDate=DateHandler.formatDate(bean.getNeedDate(),"MM/dd/yyyy");
    picklistQuantity=NumberHandler.emptyIfNull(bean.getPicklistQuantity());
    quantityPicked=NumberHandler.emptyIfNull(bean.getQuantityPicked());
    qcDate=DateHandler.formatDate(bean.getQcDate(),"MM/dd/yyyy");
    application=StringHandler.emptyIfNull(bean.getApplication());
    facilityId=StringHandler.emptyIfNull(bean.getFacilityId());
    partDescription=StringHandler.emptyIfNull(bean.getPartDescription());
    packaging=StringHandler.emptyIfNull(bean.getPackaging());
    inventoryQuantity=NumberHandler.emptyIfNull(bean.getInventoryQuantity());
    catalogId=StringHandler.emptyIfNull(bean.getCatalogId());
    catPartNo=StringHandler.emptyIfNull(bean.getCatPartNo());
    partGroupNo= NumberHandler.emptyIfNull(bean.getPartGroupNo());
    companyId=StringHandler.emptyIfNull(bean.getCompanyId());
    deliveryPoint=StringHandler.emptyIfNull(bean.getDeliveryPoint());
    shipToLocationId=StringHandler.emptyIfNull(bean.getShipToLocationId());
    requestor=StringHandler.emptyIfNull(bean.getRequestor());
    endUser=StringHandler.emptyIfNull(bean.getEndUser());
    transferRequestId=NumberHandler.emptyIfNull(bean.getTransferRequestId());
    mrNotes="MR Notes: "+StringHandler.emptyIfNull(bean.getMrNotes())+" "+
        StringHandler.emptyIfNull(bean.getFacLocAppPartComment())+" "+
        StringHandler.emptyIfNull(bean.getCatPartComment())+" "+
        StringHandler.emptyIfNull(bean.getRddComment());
    critical=StringHandler.emptyIfNull(bean.getCritical());
    certificationDate= DateHandler.formatDate(bean.getCertificationDate(),"MM/dd/yyyy");
    pickable=StringHandler.emptyIfNull(bean.getPickable());
    certifiedBy=NumberHandler.emptyIfNull(bean.getCertifiedBy());
    certificationNumber=StringHandler.emptyIfNull(bean.getCertificationNumber());
    qualityControlItem=StringHandler.emptyIfNull(bean.getQualityControlItem());
    facLocAppPartComment=StringHandler.emptyIfNull(bean.getFacLocAppPartComment());
    catPartComment=StringHandler.emptyIfNull(bean.getCatPartComment());
    cabinetReplenishment= StringHandler.emptyIfNull(bean.getCabinetReplenishment());
    hazmatIdMissing=StringHandler.emptyIfNull(bean.getHazmatIdMissing());
    receiptDocumentAvailable=StringHandler.emptyIfNull(bean.getReceiptDocumentAvailable());
    nonintegerReceiving=StringHandler.emptyIfNull(bean.getNonintegerReceiving());
    recertNumber=NumberHandler.emptyIfNull(bean.getRecertNumber());
    carrierCode=StringHandler.emptyIfNull(bean.getCarrierCode());
    pickupTime=DateHandler.formatDate(bean.getPickupTime(),"MM/dd/yyyy HH:mm a zzz");
    stopNumber=NumberHandler.emptyIfNull(bean.getStopNumber());
    trailerNumber=StringHandler.emptyIfNull(bean.getTrailerNumber());
    packingGroupId=NumberHandler.emptyIfNull(bean.getPackingGroupId());
    carrierName=StringHandler.emptyIfNull(bean.getCarrierName());
    transportationMode=StringHandler.emptyIfNull(bean.getTransportationMode());
    requiresOverpack=StringHandler.emptyIfNull(bean.getRequiresOverpack());
    shippedAsSingle=StringHandler.emptyIfNull(bean.getShippedAsSingle());
    shipToLocationDesc=StringHandler.emptyIfNull(bean.getShipToLocationDesc());
    shipToCity=StringHandler.emptyIfNull(bean.getShipToCity());
    shipToStateAbbrev=StringHandler.emptyIfNull(bean.getShipToStateAbbrev());
    shipToZip=StringHandler.emptyIfNull(bean.getShipToZip());
    shipToDodaac=StringHandler.emptyIfNull(bean.getShipToDodaac());
    try {
		packingGroupIdBarcode = com.tcmis.common.util.BarCodeHandler.Code128c("" +packingGroupId + "");
   }
	 catch (Exception ex) {
    ex.printStackTrace();    
   }

    transfer="";
    BigDecimal transferRequestId = bean.getTransferRequestId();
		if (transferRequestId != null) {
			if (transferRequestId.toString().trim().length() > 0) {
				transfer = "(Transfer Request)";
			}
		}
    
    if (!StringHandler.isBlankString(bean.getOconusFlag()) && bean.getOconusFlag().equalsIgnoreCase("Y"))
    {
      transfer = "(OCONUS)";
    }

    recertNumber="";
    if (recertNumber.toString().trim().length() > 0) {
				recertNumber = "(RECERT)";
		}

	  consolidationNumber = StringHandler.emptyIfNull(bean.getConsolidationNumber());
    if (bean.getMrCount().intValue() >1)
    {
      consolidationNumber += "(MULTI-"+bean.getMrCount()+" Orders)";
    }
    maxUnitOfIssuePerBox = NumberHandler.emptyIfNull(bean.getMaxUnitOfIssuePerBox());
	  maxUnitOfIssuePerPallet = NumberHandler.emptyIfNull(bean.getMaxUnitOfIssuePerPallet());
	  packagedAs = StringHandler.emptyIfNull(bean.getPackagedAs());
    milstripCode=StringHandler.emptyIfNull(bean.getMilstripCode());
    trackingNumber=StringHandler.emptyIfNull(bean.getTrackingNumber());
    dot="DOT: " +StringHandler.emptyIfNull(bean.getDot());
    dot = StringHandler.replace(dot,"\n"," ");
    transportationPriority=StringHandler.emptyIfNull(bean.getTransportationPriority());
    hazardous=StringHandler.emptyIfNull(bean.getHazardous());
    rddComment=StringHandler.emptyIfNull(bean.getRddComment());
    poNumber=StringHandler.emptyIfNull(bean.getPoNumber());
    splitTcn=NumberHandler.emptyIfNull(bean.getSplitTcn());      
    airGroundIndicator = StringHandler.emptyIfNull(bean.getAirGroundIndicator());
    customerServiceRepId=NumberHandler.emptyIfNull(bean.getCustomerServiceRepId());
    customerServiceRepName=StringHandler.emptyIfNull(bean.getCustomerServiceRepName());
    opsEntityId=StringHandler.emptyIfNull(bean.getOpsEntityId());
    operatingEntityName=StringHandler.emptyIfNull(bean.getOperatingEntityName());
    customerId=NumberHandler.emptyIfNull(bean.getCustomerId());
    customerName=StringHandler.emptyIfNull(bean.getCustomerName());
    facilityName=StringHandler.emptyIfNull(bean.getFacilityName());
    inventoryGroupName=StringHandler.emptyIfNull(bean.getInventoryGroupName());
    customerReceiptId=StringHandler.emptyIfNull(bean.getCustomerReceiptId());
    requestorName=StringHandler.emptyIfNull(bean.getRequestorName());
    requestorPhone=StringHandler.emptyIfNull(bean.getRequestorPhone());
    requestorFax=StringHandler.emptyIfNull(bean.getRequestorFax());
    requestorEmail=StringHandler.emptyIfNull(bean.getRequestorEmail());
    paymentTerms=StringHandler.emptyIfNull(bean.getPaymentTerms());
    specialInstructions=StringHandler.emptyIfNull(bean.getSpecialInstructions());
    carrierAccountId=StringHandler.emptyIfNull(bean.getCarrierAccountId());
    carrierContact=StringHandler.emptyIfNull(bean.getCarrierContact());
    carrierServiceType=StringHandler.emptyIfNull(bean.getCarrierServiceType());
    incoTerms=StringHandler.emptyIfNull(bean.getIncoTerms());
    flashPoint=StringHandler.emptyIfNull(bean.getFlashPoint());
    addressLine1Display=StringHandler.emptyIfNull(bean.getAddressLine1Display());
    addressLine2Display=StringHandler.emptyIfNull(bean.getAddressLine2Display());
    addressLine3Display=StringHandler.emptyIfNull(bean.getAddressLine3Display());
    addressLine4Display=StringHandler.emptyIfNull(bean.getAddressLine4Display());
    addressLine5Display=StringHandler.emptyIfNull(bean.getAddressLine5Display());
    materialRequestOrigin=StringHandler.emptyIfNull(bean.getMaterialRequestOrigin());
    submittedBy=NumberHandler.emptyIfNull(bean.getSubmittedBy());
    submittedByName=StringHandler.emptyIfNull(bean.getSubmittedByName());
    hubBinRoom=StringHandler.emptyIfNull(bean.getHubBinRoom());
    logoImageUrl=StringHandler.emptyIfNull(bean.getLogoImageUrl());
    chargeFreight=StringHandler.emptyIfNull(bean.getChargeFreight());
  } //end of method

	//getters
	public String getPicklistId() {
		return picklistId;
	}
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getMrLine() {
		return mrLine;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public String getItemId() {
		return itemId;
	}
	public String getBin() {
		return bin;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public String getStockingMethod() {
		return stockingMethod;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public String getNeedDate() {
		return needDate;
	}
	public String getPicklistQuantity() {
		return picklistQuantity;
	}
	public String getQuantityPicked() {
		return quantityPicked;
	}
	public String getQcDate() {
		return qcDate;
	}
	public String getApplication() {
		return application;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getInventoryQuantity() {
		return inventoryQuantity;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartGroupNo() {
		return partGroupNo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getRequestor() {
		return requestor;
	}
	public String getEndUser() {
		return endUser;
	}
	public String getTransferRequestId() {
		return transferRequestId;
	}
	public String getMrNotes() {
		return mrNotes;
	}
	public String getCritical() {
		return critical;
	}
	public String getCertificationDate() {
		return certificationDate;
	}
	public String getPickable() {
		return pickable;
	}
	public String getCertifiedBy() {
		return certifiedBy;
	}
	public String getCertificationNumber() {
		return certificationNumber;
	}
	public String getQualityControlItem() {
		return qualityControlItem;
	}
	public String getFacLocAppPartComment() {
		return facLocAppPartComment;
	}
	public String getCatPartComment() {
		return catPartComment;
	}
	public String getCabinetReplenishment() {
		return cabinetReplenishment;
	}
	public String getHazmatIdMissing() {
		return hazmatIdMissing;
	}
	public String getReceiptDocumentAvailable() {
		return receiptDocumentAvailable;
	}
	public String getNonintegerReceiving() {
		return nonintegerReceiving;
	}
	public String getRecertNumber() {
		return recertNumber;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public String getStopNumber() {
		return stopNumber;
	}
	public String getTrailerNumber() {
		return trailerNumber;
	}
	public String getPackingGroupId() {
		return packingGroupId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getShipToLocationDesc() {
		return shipToLocationDesc;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToStateAbbrev() {
		return shipToStateAbbrev;
	}
	public String getShipToZip() {
		return shipToZip;
	}
	public String getTransfer() {
		return transfer;
	}
	public String getPackingGroupIdBarcode() {
		return packingGroupIdBarcode;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getPackagedAs() {
		return packagedAs;
	}
	public String getMaxUnitOfIssuePerBox() {
		return maxUnitOfIssuePerBox;
	}
	public String getMaxUnitOfIssuePerPallet() {
		return maxUnitOfIssuePerPallet;
	}
	public String getConsolidationNumber() {
		return consolidationNumber;
	}
	public String getMrCount() {
		return mrCount;
	}
	public String getOconusFlag() {
		return oconusFlag;
	}
	public String getMilstripCode() {
		return milstripCode;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getDot() {
		return dot;
	}
	public String getTransportationPriority() {
		return transportationPriority;
	}
	public String getHazardous() {
		return hazardous;
	}
	public String getRddComment() {
		return rddComment;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getSplitTcn() {
		return splitTcn;
	}
	public String getAirGroundIndicator() {
		return airGroundIndicator;
	}
	public String getCustomerServiceRepId() {
		return customerServiceRepId;
	}
	public String getCustomerServiceRepName() {
		return customerServiceRepName;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getCustomerReceiptId() {
		return customerReceiptId;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getRequestorPhone() {
		return requestorPhone;
	}
	public String getRequestorFax() {
		return requestorFax;
	}
	public String getRequestorEmail() {
		return requestorEmail;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public String getCarrierAccountId() {
		return carrierAccountId;
	}
	public String getCarrierContact() {
		return carrierContact;
	}
	public String getCarrierServiceType() {
		return carrierServiceType;
	}
	public String getIncoTerms() {
		return incoTerms;
	}
	public String getFlashPoint() {
		return flashPoint;
	}
	public String getAddressLine1Display() {
		return addressLine1Display;
	}
	public String getAddressLine2Display() {
		return addressLine2Display;
	}
	public String getAddressLine3Display() {
		return addressLine3Display;
	}
	public String getAddressLine4Display() {
		return addressLine4Display;
	}
	public String getAddressLine5Display() {
		return addressLine5Display;
	}
	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public String getSubmittedByName() {
		return submittedByName;
	}
	public String getHubBinRoom() {
		return hubBinRoom;
	}
    public String getLogoImageUrl() {
    	return logoImageUrl;
    }
    public String getChargeFreight() {
    	return chargeFreight;
    }
  public static Vector getFieldVector() {
  Vector v = new Vector();
  v.addElement("splitTcn= getSplitTcn");
  v.addElement("picklistId= getPicklistId");
  v.addElement("hub= getHub");
  v.addElement("inventoryGroup= getInventoryGroup");
  v.addElement("prNumber= getPrNumber");
  v.addElement("lineItem= getLineItem");
  v.addElement("mrLine= getMrLine");
  v.addElement("receiptId= getReceiptId");
  v.addElement("itemId= getItemId");
  v.addElement("bin= getBin");
  v.addElement("mfgLot= getMfgLot");
  v.addElement("lotStatus= getLotStatus");
  v.addElement("expireDate= getExpireDate");
  v.addElement("stockingMethod= getStockingMethod");
  v.addElement("deliveryType= getDeliveryType");
  v.addElement("needDate= getNeedDate");
  v.addElement("picklistQuantity= getPicklistQuantity");
  v.addElement("quantityPicked= getQuantityPicked");
  v.addElement("qcDate= getQcDate");
  v.addElement("application= getApplication");
  v.addElement("facilityId= getFacilityId");
  v.addElement("partDescription= getPartDescription");
  v.addElement("packaging= getPackaging");
  v.addElement("inventoryQuantity= getInventoryQuantity");
  v.addElement("catalogId= getCatalogId");
  v.addElement("catPartNo= getCatPartNo");
  v.addElement("partGroupNo= getPartGroupNo");
  v.addElement("companyId= getCompanyId");
  v.addElement("deliveryPoint= getDeliveryPoint");
  v.addElement("shipToLocationId= getShipToLocationId");
  v.addElement("requestor= getRequestor");
  v.addElement("endUser= getEndUser");
  v.addElement("transferRequestId= getTransferRequestId");
  v.addElement("mrNotes= getMrNotes");
  v.addElement("critical= getCritical");
  v.addElement("certificationDate= getCertificationDate");
  v.addElement("pickable= getPickable");
  v.addElement("certifiedBy= getCertifiedBy");
  v.addElement("certificationNumber= getCertificationNumber");
  v.addElement("qualityControlItem= getQualityControlItem");
  v.addElement("facLocAppPartComment= getFacLocAppPartComment");
  v.addElement("catPartComment= getCatPartComment");
  v.addElement("cabinetReplenishment= getCabinetReplenishment");
  v.addElement("hazmatIdMissing= getHazmatIdMissing");
  v.addElement("receiptDocumentAvailable= getReceiptDocumentAvailable");
  v.addElement("nonintegerReceiving= getNonintegerReceiving");
  v.addElement("recertNumber= getRecertNumber");
  v.addElement("carrierCode= getCarrierCode");
  v.addElement("pickupTime= getPickupTime");
  v.addElement("stopNumber= getStopNumber");
  v.addElement("trailerNumber= getTrailerNumber");
  v.addElement("packingGroupId= getPackingGroupId");
  v.addElement("carrierName= getCarrierName");
  v.addElement("transportationMode= getTransportationMode");
  v.addElement("requiresOverpack= getRequiresOverpack");
  v.addElement("shippedAsSingle= getShippedAsSingle");
  v.addElement("shipToLocationDesc= getShipToLocationDesc");
  v.addElement("shipToCity= getShipToCity");
  v.addElement("shipToStateAbbrev= getShipToStateAbbrev");
  v.addElement("shipToZip= getShipToZip");
  v.addElement("transfer= getTransfer");
  v.addElement("packingGroupIdBarcode= getPackingGroupIdBarcode");
  v.addElement("shipToDodaac= getShipToDodaac");
  v.addElement("consolidationNumber= getConsolidationNumber");
  v.addElement("maxUnitOfIssuePerBox= getMaxUnitOfIssuePerBox");
  v.addElement("maxUnitOfIssuePerPallet= getMaxUnitOfIssuePerPallet");
  v.addElement("packagedAs= getPackagedAs");
  v.addElement("milstripCode= getMilstripCode");
  v.addElement("trackingNumber= getTrackingNumber");
  v.addElement("dot= getDot");
  v.addElement("transportationPriority= getTransportationPriority");
  v.addElement("hazardous= getHazardous");
  v.addElement("rddComment= getRddComment");
  v.addElement("poNumber= getPoNumber");
  v.addElement("splitTcn= getSplitTcn");
  v.addElement("airGroundIndicator= getAirGroundIndicator");
  v.addElement("customerServiceRepId= getCustomerServiceRepId");
  v.addElement("customerServiceRepName= getCustomerServiceRepName");
  v.addElement("opsEntityId= getOpsEntityId");
  v.addElement("operatingEntityName= getOperatingEntityName");
  v.addElement("customerId= getCustomerId");
  v.addElement("customerName= getCustomerName");
  v.addElement("facilityName= getFacilityName");
  v.addElement("inventoryGroupName= getInventoryGroupName");
  v.addElement("customerReceiptId= getCustomerReceiptId");
  v.addElement("requestorName= getRequestorName");
  v.addElement("requestorPhone= getRequestorPhone");
  v.addElement("requestorFax= getRequestorFax");
  v.addElement("requestorEmail= getRequestorEmail");
  v.addElement("paymentTerms= getPaymentTerms");
  v.addElement("specialInstructions= getSpecialInstructions");
  v.addElement("carrierAccountId= getCarrierAccountId");
  v.addElement("carrierContact= getCarrierContact");
  v.addElement("carrierServiceType= getCarrierServiceType");
  v.addElement("incoTerms= getIncoTerms");
  v.addElement("flashPoint= getFlashPoint");
  v.addElement("addressLine1Display= getAddressLine1Display");
  v.addElement("addressLine2Display= getAddressLine2Display");
  v.addElement("addressLine3Display= getAddressLine3Display");
  v.addElement("addressLine4Display= getAddressLine4Display");
  v.addElement("addressLine5Display= getAddressLine5Display");
  v.addElement("materialRequestOrigin= getMaterialRequestOrigin");
  v.addElement("submittedBy= getSubmittedBy");
  v.addElement("submittedByName= getSubmittedByName");
  v.addElement("hubBinRoom= getHubBinRoom");
  v.addElement("logoImageUrl= getLogoImageUrl");
  v.addElement("chargeFreight= getChargeFreight");
  return v;
 }

  public static Vector getVector(Collection in) {
    Vector out = new Vector();
    try {
			Iterator iterator = in.iterator();
			while (iterator.hasNext()) {
				PicklistViewBean picklistViewBean = (PicklistViewBean)iterator.next();
				out.addElement(new PickListData(picklistViewBean));
			}
    } catch (Exception e11) {
      e11.printStackTrace();
    }
    return out;
  }
}
