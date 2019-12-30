package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ScaqmdRule109ViewBean <br>
 * @version: 1.0, Dec 16, 2005 <br>
 *****************************************************************************/


public class ScaqmdRule109ViewBean extends BaseDataBean {

	private BigDecimal keyId;
	private String permitNo;
	private BigDecimal itemId;
	private Date shipmentDate;
	private String facilityGroupId;
	private String catPartNo;
        private String partDescription;
	private String forUseBy;
	private BigDecimal materialId;
	private Date revisionDate;
	private String amountUsed;
	private String units;
	private String vocCoatingLbPerGal;
	private String vocMatlLbPerGal;
	private String vocEmissionsLb;
	private String mixingRatio;
	private String vocCompVaporPressureMmhg;


	//constructor
	public ScaqmdRule109ViewBean() {
	}

	//setters
	public void setKeyId(BigDecimal keyId) {
		this.keyId = keyId;
	}
	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
        public void setPartDescription(String partDescription) {
                this.partDescription = partDescription;
        }
	public void setForUseBy(String forUseBy) {
		this.forUseBy = forUseBy;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setAmountUsed(String amountUsed) {
		this.amountUsed = amountUsed;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public void setVocCoatingLbPerGal(String vocCoatingLbPerGal) {
		this.vocCoatingLbPerGal = vocCoatingLbPerGal;
	}
	public void setVocMatlLbPerGal(String vocMatlLbPerGal) {
		this.vocMatlLbPerGal = vocMatlLbPerGal;
	}
	public void setVocEmissionsLb(String vocEmissionsLb) {
		this.vocEmissionsLb = vocEmissionsLb;
	}
	public void setMixingRatio(String mixingRatio) {
		this.mixingRatio = mixingRatio;
	}
	public void setVocCompVaporPressureMmhg(String vocCompVaporPressureMmhg) {
		this.vocCompVaporPressureMmhg = vocCompVaporPressureMmhg;
	}


	//getters
	public BigDecimal getKeyId() {
		return keyId;
	}
	public String getPermitNo() {
		return permitNo;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public Date getShipmentDate() {
		return shipmentDate;
	}
	public String getFacilityGroupId() {
		return facilityGroupId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
        public String getPartDescription() {
                return partDescription;
        }
	public String getForUseBy() {
		return forUseBy;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public String getAmountUsed() {
		return amountUsed;
	}
	public String getUnits() {
		return units;
	}
	public String getVocCoatingLbPerGal() {
		return vocCoatingLbPerGal;
	}
	public String getVocMatlLbPerGal() {
		return vocMatlLbPerGal;
	}
	public String getVocEmissionsLb() {
		return vocEmissionsLb;
	}
	public String getMixingRatio() {
		return mixingRatio;
	}
	public String getVocCompVaporPressureMmhg() {
		return vocCompVaporPressureMmhg;
	}
}