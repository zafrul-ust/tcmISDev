package radian.web.barcode;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.util.Hashtable;
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
    private String applicationDesc;
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
    private String allocateByMfgLot;

  public PickListData(Hashtable h, int size) {
     picklistId = "";
	 hub = "";
	 inventoryGroup = "";
	 prNumber = "";
	 lineItem = "";
	 mrLine = "";
	 receiptId = "";
	 itemId = "";
	 bin = "";
	 mfgLot = "";
	 lotStatus = "";
	 expireDate = "";
	 stockingMethod = "";
	 deliveryType = "";
	 needDate = "";
	 picklistQuantity = "";
	 quantityPicked = "";
	 qcDate = "";
	 application = "";
	 facilityId = "";
	 partDescription = "";
	 packaging = "";
	 inventoryQuantity = "";
	 catalogId = "";
	 catPartNo = "";
	 partGroupNo = "";
	 companyId = "";
	 deliveryPoint = "";
	 shipToLocationId = "";
	 requestor = "";
	 endUser = "";
	 transferRequestId = "";
	 mrNotes = "";
	 critical = "";
	 certificationDate = "";
	 pickable = "";
	 certifiedBy = "";
	 certificationNumber = "";
	 qualityControlItem = "";
	 facLocAppPartComment = "";
	 catPartComment = "";
	 cabinetReplenishment = "";
	 hazmatIdMissing = "";
	 receiptDocumentAvailable = "";
	 nonintegerReceiving = "";
	 recertNumber = "";
	 carrierCode = "";
	 pickupTime = "";
	 stopNumber = "";
	 trailerNumber = "";
	 packingGroupId = "";
	 carrierName = "";
	 transportationMode = "";
	 requiresOverpack = "";
	 shippedAsSingle = "";
	 shipToLocationDesc = "";
	 shipToCity = "";
	 shipToStateAbbrev = "";
	 shipToZip = "";
	 transfer = "";
     packingGroupIdBarcode = "";
	 shipToDodaac = "";
	 consolidationNumber = "";
	 maxUnitOfIssuePerBox = "";
	 maxUnitOfIssuePerPallet = "";
	 packagedAs = "";
	 milstripCode = "";
	 trackingNumber = "";
	 dot = "";
	 transportationPriority = "";
	 hazardous = "";
	 rddComment = "";
	 poNumber = "";
     splitTcn = "";
	 airGroundIndicator = "";
	 customerServiceRepId = "";
	 customerServiceRepName = "";
	 opsEntityId = "";
	 operatingEntityName = "";
	 customerId = "";
	 customerName = "";
	 facilityName = "";
	 inventoryGroupName = "";
	 customerReceiptId = "";
	 requestorName = "";
	 requestorPhone = "";
	 requestorFax = "";
	 requestorEmail = "";
	 paymentTerms = "";
	 specialInstructions = "";
	 carrierAccountId = "";
	 carrierContact = "";
	 carrierServiceType = "";
	 incoTerms = "";
     transfer = "";
	 flashPoint = "";
	 addressLine1Display = "";
	 addressLine2Display = "";
	 addressLine3Display = "";
	 addressLine4Display = "";
	 addressLine5Display = "";
	 materialRequestOrigin = "";
	 submittedBy = "";
	 submittedByName = "";
	 hubBinRoom = "";
     logoImageUrl="";
     chargeFreight = "";
     allocateByMfgLot = "";

    for (int i = 0; i < size; i++) {
      switch (i) {
        case 0:
          this.picklistId = h.get("PICKLIST_ID").toString();
          break;
        case 1:
          this.mrLine = h.get("MR_LINE").toString();
          break;
        case 2:
          this.facilityId = h.get("FACILITY_ID").toString();
          break;
        case 3:
          String transferRequestId = h.get("TRANSFER_REQUEST_ID").toString();
          if (transferRequestId.trim().length() > 0) {
              this.application = h.get("APPLICATION").toString();
          }
          else
          {
              this.application = h.get("APPLICATION_DESC").toString();
          }                       
          break;
        case 4:
          this.requestor = h.get("REQUESTOR").toString();
          break;
        case 5:
          this.catalogId = h.get("CATALOG_ID").toString();
          break;
        case 6:
          this.catPartNo = h.get("CAT_PART_NO").toString();
          break;
        case 7:
          this.partDescription = "Description: "+ h.get("PART_DESCRIPTION").toString();
          break;
        case 8:
          this.itemId = h.get("ITEM_ID").toString();
          break;
        case 9:
          this.lotStatus = h.get("LOT_STATUS").toString();
          break;
        case 10:
          this.expireDate = h.get("EXPIRE_DATE").toString();
          break;
        case 11:
          this.mfgLot = h.get("MFG_LOT").toString();
          break;
        case 12:
          customerReceiptId = h.get("CUSTOMER_RECEIPT_ID").toString();
          if (customerReceiptId.trim().length() > 0)
          {
              this.bin = h.get("BIN").toString()+ "\n("+ customerReceiptId+ ")";
          }
          else
          {
              this.bin = h.get("BIN").toString();
          }
          break;
        case 13:
          this.receiptId = h.get("RECEIPT_ID").toString();
          break;
        case 14:
          this.inventoryQuantity = h.get("INVENTORY_QUANTITY").toString();
          break;
        case 15:
          this.picklistQuantity = h.get("PICKLIST_QUANTITY").toString();
          break;
        case 16:
          this.quantityPicked = h.get("QUANTITY_PICKED").toString();
          break;
        case 17:
            String tmpPrNumber = h.get("PR_NUMBER").toString();
		    this.prNumber = tmpPrNumber.replace(".","12345");
          break;
        case 18:
          this.lineItem = h.get("LINE_ITEM").toString();
          break;
        case 19:
          this.deliveryPoint = h.get("DELIVERY_POINT").toString();
          break;
        case 20:
          this.shipToLocationId = h.get("SHIP_TO_LOCATION_ID").toString();
          break;
        case 21:
          this.stockingMethod = h.get("STOCKING_METHOD").toString();
          break;
        case 22:
          this.packaging = "Packaging: "+h.get("PACKAGING").toString();
          break;
        case 23:
          this.endUser = h.get("END_USER").toString();
          break;
        case 24:
          String multipleCatPartNo=h.get( "MULTIPLE_CAT_PART_NO" ).toString();
          String inventoryGroup = h.get("INVENTORY_GROUP").toString();
          if ("Y".equalsIgnoreCase(multipleCatPartNo)  && ("Bell Amarillo".equalsIgnoreCase(inventoryGroup)
                    || "Dallas Bell".equalsIgnoreCase(inventoryGroup) || "Dallas Bell FSL".equalsIgnoreCase(inventoryGroup) || "Dallas Bell AVC700".equalsIgnoreCase(inventoryGroup)) )
          {
             mrNotes = "Verify part number on label. \n"; 
          }
          String chargeDescription = h.get("CHARGE_DESCRIPTION").toString();
          if (chargeDescription.length() > 0)
          {
             mrNotes = mrNotes + chargeDescription;  
          }
          this.mrNotes = mrNotes + h.get("MR_NOTES").toString()+" "+ h.get("SPECIAL_INSTRUCTIONS").toString();
          break;
        case 25:
          transferRequestId = h.get("TRANSFER_REQUEST_ID").toString();
          if (transferRequestId.trim().length() > 0) {
            this.transfer = "(Transfer Request)";
          }
          break;
        case 26:
          String recertNumber = h.get("RECERT_NUMBER").toString();
          if (recertNumber.trim().length() > 0) {
            this.recertNumber = "(RECERT)";
          }
          break;
        case 27:
          this.poNumber = h.get("PO_NUMBER").toString();
          break;
        case 28:
          this.shipToLocationDesc = h.get("SHIP_TO_LOCATION_DESC").toString();
          break;
        case 29:
          this.carrierName = h.get("CARRIER_NAME").toString();
          break;
        case 30:
          this.packagedAs = h.get("PACKAGED_AS").toString();
          break;
        case 31:
          this.dot = "Transportation Info: "+ h.get("DOT").toString();
          break;
        case 32:
          this.customerServiceRepName = h.get("CUSTOMER_SERVICE_REP_NAME").toString();
          break;
        case 33:
          this.customerName = h.get("CUSTOMER_NAME").toString();
          break;
        case 34:
          this.facilityName = h.get("FACILITY_NAME").toString();
          break;
        case 35:
          this.customerReceiptId = h.get("CUSTOMER_RECEIPT_ID").toString();
          break;
        case 36:
          this.requestorName = h.get("REQUESTOR_NAME").toString();
          break;
        case 37:
          this.requestorPhone = h.get("REQUESTOR_PHONE").toString();
          break;
        case 38:
          this.requestorEmail = h.get("REQUESTOR_EMAIL").toString();
          break;
        case 39:
          this.paymentTerms = h.get("PAYMENT_TERMS").toString();
          break;
        case 40:
          this.specialInstructions = h.get("SPECIAL_INSTRUCTIONS").toString();
          break;
        case 41:
          this.carrierAccountId = h.get("CARRIER_ACCOUNT_ID").toString();
          break;
        case 42:
          this.carrierContact = h.get("CARRIER_CONTACT").toString();
          break;
        case 43:
          this.carrierServiceType = h.get("CARRIER_SERVICE_TYPE").toString();
          break;
        case 44:
          this.incoTerms = h.get("INCO_TERMS").toString();
          break;
        case 45:
          this.flashPoint = h.get("FLASH_POINT").toString();
          break;
        case 46:
          this.addressLine1Display = h.get("ADDRESS_LINE_1_DISPLAY").toString();
          break;
        case 47:
          this.addressLine2Display = h.get("ADDRESS_LINE_2_DISPLAY").toString();
          break;
        case 48:
          this.addressLine3Display = h.get("ADDRESS_LINE_3_DISPLAY").toString();
          break;
        case 49:
          this.addressLine4Display = h.get("ADDRESS_LINE_4_DISPLAY").toString();
          break;
        case 50:
          this.addressLine5Display = h.get("ADDRESS_LINE_5_DISPLAY").toString();
          break;
        case 51:
          this.submittedByName = h.get("SUBMITTED_BY_NAME").toString();
          break;
        case 55:
          this.hubBinRoom = h.get("HUB_BIN_ROOM").toString();
          break;
        case 56:
          this.logoImageUrl = h.get("LOGO_IMAGE_URL").toString();
          break;
        case 57:
          this.needDate = h.get("DELIVERY_TYPE").toString() + " "+ h.get("NEED_DATE").toString()  ;
          break;
        case 58:
            this.allocateByMfgLot = h.get("ALLOCATE_BY_MFG_LOT").toString();
            break;
      }
    }
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
    public String getApplicationDesc() {
        return applicationDesc;
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

	public String getConsolidationNumber() {
		return consolidationNumber;
	}

	public String getMaxUnitOfIssuePerBox() {
		return maxUnitOfIssuePerBox;
	}

	public String getMaxUnitOfIssuePerPallet() {
		return maxUnitOfIssuePerPallet;
	}

	public String getPackagedAs() {
		return packagedAs;
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

    public String getCatalogCompanyId() {
        return catalogCompanyId;
    }

    public String getMrCount() {
        return mrCount;
    }

    public String getOconusFlag() {
        return oconusFlag;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public String getChargeFreight() {
        return chargeFreight;
    }
    
    public String getAllocateByMfgLot() {
        return allocateByMfgLot;
    }
    
    

    public static Vector getFieldVector() {
  Vector v = new Vector();
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
  v.addElement("allocateByMfgLot= getAllocateByMfgLot");
  
  return v;
 }

  public static Vector getVector(Vector in) {
    Vector out = new Vector();
    try {
      for (int i = 0; i < in.size(); i++) {
        Hashtable h = (Hashtable) in.elementAt(i);
        out.addElement(new PickListData(h, h.size()));
      }
    } catch (Exception e11) {
      e11.printStackTrace();
    }
    return out;
  }
}
