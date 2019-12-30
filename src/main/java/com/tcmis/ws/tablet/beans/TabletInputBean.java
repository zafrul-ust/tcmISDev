package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
import org.json.JSONException;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.StringHandler;

public class TabletInputBean extends BaseTabletBean {
	private static String						INDEFINITE				= "01/01/3000";
	public static Log							log						= LogFactory.getLog(TabletInputBean.class);
	private String								accountNumber;
	private String								accountNumber2;
	private String								accountNumber3;
	private String								accountNumber4;
	private String								bin;
	private String								catalogCompanyId;
	private String								catalogId;
	private String								catPartNo;
	private String								citrPo;
	private String								componentId;
	private String								components;
	private String								countryOfOrigin;
	private String								customerReceiptId;
	private String								dateOfManufacture;
	private String								dateOfReceipt;
	private String								dateOfShipment;
	private String								definedShelfLifeItem;
	private String								docNum;
	private String								docType;
	private String								expireDate;
	private String								facilityId;
	private String								hazComLabelFlag;
	protected String							hub;
	private String								hubId;
	private String								imageData;
	private String								imageType;
	private String								inboundShipmentDetailId;
	private String								inboundShipmentId;
	private String								initialScanDate;
	private String								interCompanyPo;
	private String								interCompanyPoLine;
	private String								internalReceiptNotes;
	private String								inventoryGroup;
	private String								itemId;
	private String								lot;
	private boolean								manageKitsAsSingleUnit;
	private String								mfgLabelExpireDate;
	private String								newMsdsRevReceivedFlag;
	private String								notes;
	protected String							opsEntityId;
	private String								originalReceiptId;
	private String								ownerCompanyId;
	private String								ownerSegmentId;
	private String								partGroupNo;
	private String								po;
	private String								poLine;
	private String								poNumber;
	private String								qaStatement;
	private String								qualityTrackingNumber;
	private String								quantity;
	private Collection<ReceiptComponentBean>	receiptComponents;
	private String								receiptGroup;
	private String								receiptId;
	private String								receivedPurchasingUnits;
	private String								receiverId;
	private String								receivingStatus;
	private String								returnReceiptId;
	private String								returnValue;
	private String								rma;
	private String								trackingId;
	private String								transferReceiptId;
	private String								transferRequest;
	private String								unitLabelPrinted;
	private String								vendorShipDate;
	
	// GHS Label Sections
	private boolean								productName;
	private boolean								supplierInfo;
	private boolean								signalWord;
	private boolean								pictogram;
	private boolean								precautionaryStatement;
	private boolean								hazardStatement;

	public TabletInputBean(ActionForm form, Locale tcmISLocale) throws BaseException {
		super(form, tcmISLocale);
		if (receiptComponents == null) {
			setReceiptComponents(Collections.<ReceiptComponentBean> emptyList());
		}
		if (StringHandler.isBlankString((String)((DynaBean)form).get("manageKitsAsSingleUnit"))) {
			setManageKitsAsSingleUnit(true);
		}
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountNumber2() {
		return accountNumber2;
	}

	public String getAccountNumber3() {
		return accountNumber3;
	}

	public String getAccountNumber4() {
		return accountNumber4;
	}

	public String getBin() {
		return bin;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCitrPo() {
		return citrPo;
	}

	public String getComponentId() {
		return componentId;
	}

	public String getComponents() {
		return components;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public String getCustomerReceiptId() {
		return customerReceiptId;
	}

	public String getDateOfManufacture() {
		return dateOfManufacture;
	}

	public String getDateOfReceipt() {
		return dateOfReceipt;
	}

	public String getDateOfShipment() {
		return dateOfShipment;
	}

	public String getDefinedShelfLifeItem() {
		return definedShelfLifeItem;
	}

	public String getDocNum() {
		return docNum;
	}

	public String getDocType() {
		return docType;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getHazComLabelFlag() {
		return hazComLabelFlag;
	}

	public String getHub() {
		return hub;
	}

	public String getHubId() {
		return hubId;
	}

	public String getImageData() {
		return imageData;
	}

	public String getImageType() {
		return imageType;
	}

	public String getInboundShipmentDetailId() {
		return inboundShipmentDetailId;
	}

	public String getInboundShipmentId() {
		return inboundShipmentId;
	}

	public String getInitialScanDate() {
		return initialScanDate;
	}

	public String getInterCompanyPo() {
		return interCompanyPo;
	}

	public String getInterCompanyPoLine() {
		return interCompanyPoLine;
	}

	public String getInternalReceiptNotes() {
		return internalReceiptNotes;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getItemId() {
		return itemId;
	}

	public String getLot() {
		return lot;
	}

	public String getMfgLabelExpireDate() {
		return mfgLabelExpireDate;
	}

	public String getNewMsdsRevReceivedFlag() {
		return newMsdsRevReceivedFlag;
	}

	public String getNotes() {
		return notes;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getOriginalReceiptId() {
		return originalReceiptId;
	}

	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}

	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}

	public String getPartGroupNo() {
		return partGroupNo;
	}

	public String getPo() {
		return po;
	}

	public String getPoLine() {
		return poLine;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public String getQaStatement() {
		return qaStatement;
	}

	public String getQualityTrackingNumber() {
		return qualityTrackingNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	public Collection<ReceiptComponentBean> getReceiptComponents() {
		return receiptComponents;
	}

	public String getReceiptGroup() {
		return receiptGroup;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public String getReceivedPurchasingUnits() {
		return receivedPurchasingUnits;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public String getReceivingStatus() {
		return receivingStatus;
	}

	public String getReturnReceiptId() {
		return returnReceiptId;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public String getRma() {
		return rma;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public String getTransferReceiptId() {
		return transferReceiptId;
	}

	public String getTransferRequest() {
		return transferRequest;
	}

	public String getUnitLabelPrinted() {
		return unitLabelPrinted;
	}

	public String getVendorShipDate() {
		return vendorShipDate;
	}

	public boolean hasBin() {
		return !StringHandler.isBlankString(bin);
	}

	public boolean hasCitrPo() {
		return !StringHandler.isBlankString(citrPo);
	}

	public boolean hasComponentId() {
		return !StringHandler.isBlankString(componentId);
	}

	public boolean hasComponents() {
		return !receiptComponents.isEmpty();
	}

	public boolean hasDateOfReceipt() {
		return !StringHandler.isBlankString(dateOfReceipt);
	}

	public boolean hasDocType() {
		return !StringHandler.isBlankString(docType);
	}

	public boolean hasExpireDate() {
		return !StringHandler.isBlankString(expireDate) && !expireDate.equals(INDEFINITE);
	}

	public boolean hasHub() {
		return !StringHandler.isBlankString(getHub());
	}

	public boolean hasHubId() {
		return !StringHandler.isBlankString(hubId);
	}

	public boolean hasImageData() {
		return !StringHandler.isBlankString(imageData);
	}

	public boolean hasImageType() {
		return !StringHandler.isBlankString(imageType);
	}

	public boolean hasInboundShipmentDetailId() {
		return !StringHandler.isBlankString(inboundShipmentDetailId);
	}

	public boolean hasInboundShipmentId() {
		return !StringHandler.isBlankString(inboundShipmentId);
	}

	public boolean hasInventoryGroup() {
		return !StringHandler.isBlankString(getInventoryGroup());
	}

	public boolean hasItemId() {
		return !StringHandler.isBlankString(itemId);
	}

	public boolean hasLot() {
		return !StringHandler.isBlankString(lot);
	}

	public boolean hasOpsEntityId() {
		return !StringHandler.isBlankString(getOpsEntityId());
	}

	public boolean hasPO() {
		return !StringHandler.isBlankString(po);
	}

	public boolean hasPoLine() {
		return !StringHandler.isBlankString(poLine);
	}

	public boolean hasQuantity() {
		return !StringHandler.isBlankString(quantity);
	}

	public boolean hasReceiptId() {
		return !StringHandler.isBlankString(receiptId);
	}

	public boolean hasRma() {
		return !StringHandler.isBlankString(rma);
	}

	public boolean hasTransferRequest() {
		return !StringHandler.isBlankString(transferRequest);
	}

	public boolean isManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}

	public boolean isProductName() {
		return productName;
	}

	public boolean isSupplierInfo() {
		return supplierInfo;
	}

	public boolean isSignalWord() {
		return signalWord;
	}

	public boolean isPictogram() {
		return pictogram;
	}

	public boolean isPrecautionaryStatement() {
		return precautionaryStatement;
	}

	public boolean isHazardStatement() {
		return hazardStatement;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}

	public void setAccountNumber3(String accountNumber3) {
		this.accountNumber3 = accountNumber3;
	}

	public void setAccountNumber4(String accountNumber4) {
		this.accountNumber4 = accountNumber4;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCitrPo(String docNum) {
		this.citrPo = docNum;
	}

	public void setComponentId(String partId) {
		this.componentId = partId;
	}

	public void setComponents(String components) {
		this.components = components;
		if (!StringHandler.isBlankString(components)) {
			Collection<ReceiptComponentBean> beans = new ArrayList<ReceiptComponentBean>();
			try {
				JSONArray jsonComponents = new JSONArray(components);
				for (int i = 0; i < jsonComponents.length(); i++) {
					ReceiptComponentBean component = new ReceiptComponentBean(jsonComponents.getJSONObject(i));
					if (!component.hasExpireDate()) {
						component.setExpireDateString(getExpireDate());
					}
					if (hasReceiptId()) {
						component.setReceiptId(new BigDecimal(getReceiptId()));
					}
					if (i == 0) {
						if (!hasItemId()) {
							setItemId("" + component.getItemId());
						}
						if (!hasBin()) {
							setBin(component.getBin());
						}
						if (!hasQuantity()) {
							setQuantity(component.getQuantity());
						}
						if (!hasLot()) {
							setLot(component.getMfgLot());
						}
						if (!hasExpireDate()) {
							setExpireDate(component.getExpireDateString());
						}
					}
					beans.add(component);
				}
			}
			catch (JSONException e) {
				log.error("Error creating ReceiptComponents", e);
			}
			setReceiptComponents(beans);
		}
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}

	public void setDateOfManufacture(String dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public void setDateOfReceipt(String dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public void setDateOfShipment(String dateOfShipment) {
		this.dateOfShipment = dateOfShipment;
	}

	public void setDefinedShelfLifeItem(String definedShelfLifeItem) {
		this.definedShelfLifeItem = definedShelfLifeItem;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	
	public void setHazComLabelFlag(String hazcomLabelFlag) {
		this.hazComLabelFlag = hazcomLabelFlag;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubId(String hubId) {
		this.hubId = hubId;
	}

	public void setImageData(String imageData) {
		if (imageData != null) this.imageData = imageData.replaceFirst("data:image/png;base64,", "");
		// this.imageData = imageData;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public void setInboundShipmentDetailId(String inboundShipmentDetailId) {
		this.inboundShipmentDetailId = inboundShipmentDetailId;
	}

	public void setInboundShipmentId(String inboundShipmentId) {
		this.inboundShipmentId = inboundShipmentId;
	}

	public void setInitialScanDate(String initialScanDate) {
		this.initialScanDate = initialScanDate;
	}

	public void setInterCompanyPo(String interCompanyPo) {
		this.interCompanyPo = interCompanyPo;
	}

	public void setInterCompanyPoLine(String interCompanyPoLine) {
		this.interCompanyPoLine = interCompanyPoLine;
	}

	public void setInternalReceiptNotes(String internalReceiptNotes) {
		this.internalReceiptNotes = internalReceiptNotes;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public void setManageKitsAsSingleUnit(boolean manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}

	public void setMfgLabelExpireDate(String mfgLabelExpireDate) {
		this.mfgLabelExpireDate = mfgLabelExpireDate;
	}

	public void setNewMsdsRevReceivedFlag(String newMsdsRevReceivedFlag) {
		this.newMsdsRevReceivedFlag = newMsdsRevReceivedFlag;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setOriginalReceiptId(String originalReceiptId) {
		this.originalReceiptId = originalReceiptId;
	}

	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}

	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}

	public void setPartGroupNo(String partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public void setPoLine(String poLine) {
		this.poLine = poLine;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setQaStatement(String qaStatement) {
		this.qaStatement = qaStatement;
	}

	public void setQualityTrackingNumber(String qualityTrackingNumber) {
		this.qualityTrackingNumber = qualityTrackingNumber;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setReceiptComponents(Collection<ReceiptComponentBean> receiptComponents) {
		this.receiptComponents = receiptComponents;
	}

	public void setReceiptGroup(String receiptGroup) {
		this.receiptGroup = receiptGroup;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;

		if (!StringUtils.isBlank(receiptId)) {
			this.receiptId = receiptId.replace("RCPT", "");
			if (receiptComponents != null && !receiptComponents.isEmpty()) {
				for (ReceiptComponentBean component : receiptComponents) {
					if (!component.hasReceiptId()) {
						component.setReceiptId(new BigDecimal(this.receiptId));
					}
				}
			}
		}
	}

	public void setReceivedPurchasingUnits(String receivedPurchasingUnits) {
		this.receivedPurchasingUnits = receivedPurchasingUnits;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public void setReceivingStatus(String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}

	public void setReturnReceiptId(String returnReceiptId) {
		this.returnReceiptId = returnReceiptId;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public void setRma(String rmaId) {
		this.rma = rmaId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public void setTransferReceiptId(String transferReceiptId) {
		this.transferReceiptId = transferReceiptId;
	}

	public void setTransferRequest(String transferRequest) {
		this.transferRequest = transferRequest;
	}

	public void setUnitLabelPrinted(String unitLabelPrinted) {
		this.unitLabelPrinted = unitLabelPrinted;
	}

	public void setVendorShipDate(String vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}

	public void setProductName(boolean productName) {
		this.productName = productName;
	}

	public void setSupplierInfo(boolean supplierInfo) {
		this.supplierInfo = supplierInfo;
	}

	public void setSignalWord(boolean signalWord) {
		this.signalWord = signalWord;
	}

	public void setPictogram(boolean pictogram) {
		this.pictogram = pictogram;
	}

	public void setPrecautionaryStatement(boolean precautionaryStatement) {
		this.precautionaryStatement = precautionaryStatement;
	}

	public void setHazardStatement(boolean hazardStatement) {
		this.hazardStatement = hazardStatement;
	}

}
