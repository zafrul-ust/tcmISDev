package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class Pick extends BaseDataBean {

	private String					bin;
	private BigDecimal				boxCount;
	private String					carrierName;
	private String					dotOverride;
	private BigDecimal				dryIceWeight;
	private String					hub;
	private String					id;
	private boolean					overPackSet	= false;
	private String					packageType;
	private Date					packDate;
	private BigDecimal				pdfPrinterId;
	// private BigDecimal pdoc;
	private Collection<BigDecimal>	pdocs;
	private Date					pickDate;
	private String					pickingState;
	private BigDecimal				pickingUnitId;
	private String					pickNotes;
	private BigDecimal				pickPersonnelId;
	private Collection<PickReceipt>	pickReceipts;
	private PickValidation			pickValidation;
	private Date					qcDate;
	private String					qcNotes;
	private BigDecimal				qcPersonnelId;
	private PickValidation			qcValidation;
	private BigDecimal				sharedPickingUnitId;
	private Date					shipDate;
	private String					tabletShipmentId;
	private BigDecimal				totalWeight;
	private String					trackingNumbers;
	private boolean					useOverpackBox;

	public String getBin() {
		return this.bin;
	}

	public BigDecimal getBoxCount() {
		return this.boxCount;
	}

	public String getCarrierName() {
		return this.carrierName;
	}

	public String getDotOverride() {
		return this.dotOverride;
	}

	public BigDecimal getDryIceWeight() {
		return this.dryIceWeight;
	}

	public String getId() {
		return this.id;
	}

	public String getInvalidMessage() {
		return "Invalid pickingUnit " + getPickingUnitId()
				+ ": Must have id, pickingUnitId, pickPersonnelId, pickDate, pickingState, pickValidation, qcPersonnelId, qcDate, qcValidation and at least one pickReceipt";
	}

	public String getInvalidUpdateMessage() {
		return "Invalid pickingUnit " + getPickingUnitId() + ": Must have id, pickingUnitId, pickPersonnelId";
	}

	public String getPackageType() {
		return this.packageType;
	}

	public Date getPackDate() {
		return this.packDate;
	}

	public BigDecimal getPdfPrinterId() {
		return this.pdfPrinterId;
	}

	public Collection<BigDecimal> getPdocs() {
		return this.pdocs;
	}

	public Date getPickDate() {
		return this.pickDate;
	}

	public String getPickingState() {
		return this.pickingState;
	}

	public BigDecimal getPickingUnitId() {
		return this.pickingUnitId;
	}

	public String getPickNotes() {
		return this.pickNotes;
	}

	public BigDecimal getPickPersonnelId() {
		return this.pickPersonnelId;
	}

	public Collection<PickReceipt> getPickReceipts() {
		return this.pickReceipts;
	}

	public PickValidation getPickValidation() {
		return this.pickValidation;
	}

	public Date getQcDate() {
		return this.qcDate;
	}

	public String getQcNotes() {
		return this.qcNotes;
	}

	public BigDecimal getQcPersonnelId() {
		return this.qcPersonnelId;
	}

	public PickValidation getQcValidation() {
		return this.qcValidation;
	}

	public BigDecimal getSharedPickingUnitId() {
		return this.sharedPickingUnitId;
	}

	public Date getShipDate() {
		return this.shipDate;
	}

	public String getTabletShipmentId() {
		return this.tabletShipmentId;
	}

	public BigDecimal getTotalWeight() {
		return this.totalWeight;
	}

	public String getTrackingNumbers() {
		return this.trackingNumbers;
	}

	public boolean hasBin() {
		return StringUtils.isNotBlank(bin);
	}

	public boolean hasBoxCount() {
		return boxCount != null;
	}

	public boolean hasCarrierName() {
		return StringUtils.isNotBlank(carrierName);
	}

	public boolean hasDotOverride() {
		return StringUtils.isNotBlank(dotOverride);
	}

	public boolean hasDryIceWeight() {
		return dryIceWeight != null;
	}

	public boolean hasHub() {
		return StringUtils.isNotBlank(hub);
	}
	
	public boolean hasId() {
		return StringUtils.isNotBlank(id);
	}

	public boolean hasPackageType() {
		return StringUtils.isNotBlank(packageType);
	}

	public boolean hasPackDate() {
		return packDate != null;
	}

	public boolean hasPdfPrinterId() {
		return pdfPrinterId != null;
	}

	public boolean hasPdocs() {
		return pdocs != null && !pdocs.isEmpty();
	}

	public boolean hasPickDate() {
		return pickDate != null;
	}

	public boolean hasPickingUnitId() {
		return pickingUnitId != null;
	}

	public boolean hasPickNotes() {
		return StringUtils.isNotBlank(pickNotes);
	}

	public boolean hasPickPersonnelId() {
		return pickPersonnelId != null;
	}

	public boolean hasPickReceipts() {
		return pickReceipts != null && !pickReceipts.isEmpty();
	}

	public boolean hasPickState() {
		return StringUtils.isNotBlank(pickingState);
	}

	public boolean hasPickValidation() {
		return pickValidation != null;
	}

	public boolean hasQcDate() {
		return qcDate != null;
	}

	public boolean hasQcNotes() {
		return StringUtils.isNotBlank(qcNotes);
	}

	public boolean hasQcPersonnelId() {
		return qcPersonnelId != null;
	}

	public boolean hasQcValidation() {
		return qcValidation != null;
	}

	public boolean hasSharedPickingUnitId() {
		return sharedPickingUnitId != null;
	}

	public boolean hasShipDate() {
		return shipDate != null;
	}

	public boolean hasTabletShipmentId() {
		return StringUtils.isNotBlank(tabletShipmentId);
	}

	public boolean hasTotalWeight() {
		return totalWeight != null;
	}

	public boolean hasTrackingNumbers() {
		return StringUtils.isNotBlank(trackingNumbers);
	}

	public boolean isUseOverpackBox() {
		return this.useOverpackBox;
	}

	public boolean isUseOverpackSet() {
		return overPackSet;
	}

	public boolean isValid() {
		return hasId() && hasPickingUnitId() && hasPickValidation() && hasPickDate() && hasPickPersonnelId() && hasPickState() && hasQcPersonnelId() && hasQcValidation()
				&& hasQcDate() && hasPickReceipts();
	}

	public boolean isValidForUpdate() {
		return hasId() && hasPickingUnitId() && hasPickPersonnelId();
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setBoxCount(BigDecimal boxCount) {
		this.boxCount = boxCount;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setDotOverride(String dotOverride) {
		this.dotOverride = dotOverride;
	}

	public void setDryIceWeight(BigDecimal dryIceWeight) {
		this.dryIceWeight = dryIceWeight;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}

	public void setPdfPrinterId(BigDecimal printerId) {
		this.pdfPrinterId = printerId;
	}

	public void setPdocs(Collection<BigDecimal> pdocs) {
		this.pdocs = pdocs;
	}

	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
	}

	public void setPickingState(String pickState) {
		this.pickingState = pickState;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public void setPickNotes(String pickNotes) {
		this.pickNotes = pickNotes;
	}

	public void setPickPersonnelId(BigDecimal pickPersonnelId) {
		this.pickPersonnelId = pickPersonnelId;
	}

	public void setPickReceipts(Collection<PickReceipt> receipts) {
		this.pickReceipts = receipts;
	}

	public void setPickValidation(PickValidation pickValidation) {
		this.pickValidation = pickValidation;
	}

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public void setQcNotes(String qcNotes) {
		this.qcNotes = qcNotes;
	}

	public void setQcPersonnelId(BigDecimal qcPersonnelId) {
		this.qcPersonnelId = qcPersonnelId;
	}

	public void setQcValidation(PickValidation qcValidation) {
		this.qcValidation = qcValidation;
	}

	public void setSharedPickingUnitId(BigDecimal sharedPickingUnitId) {
		this.sharedPickingUnitId = sharedPickingUnitId;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public void setTabletShipmentId(String tabletShipmentId) {
		this.tabletShipmentId = tabletShipmentId;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public void setTrackingNumbers(String trackingNumbers) {
		this.trackingNumbers = trackingNumbers;
	}

	public void setUseOverpackBox(boolean useOverpackBox) {
		overPackSet = true;
		this.useOverpackBox = useOverpackBox;
	}

	public String getHub() {
		return this.hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

}