package com.tcmis.client.waste.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: WstContCaHazLabelViewBean <br>
 * @version: 1.0, Feb 14, 2007 <br>
 *****************************************************************************/


public class WstContCaHazLabelViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal containerId;
	private String lpadContainerId;
	private BigDecimal wasteItemId;
	private String epaId;
	private String manifest;
	private Date accumulationStartDate;
	private String genName;
	private String genAddress;
	private String genPhone;
	private String genCity;
	private String genState;
	private String genZip;
	private String epaWasteNo;
	private String caWasteNo;
	private String contentsComposition;
	private String physicalStateSolid;
	private String physicalStateLiquid;
	private String flammable;
	private String toxic;
	private String corrosive;
	private String reactive;
	private String hazardOther;
	private String hazardOtherDetail;
	private String dot;


	//constructor
	public WstContCaHazLabelViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setContainerId(BigDecimal containerId) {
		this.containerId = containerId;
	}
	public void setLpadContainerId(String lpadContainerId) {
		this.lpadContainerId = lpadContainerId;
	}
	public void setWasteItemId(BigDecimal wasteItemId) {
		this.wasteItemId = wasteItemId;
	}
	public void setEpaId(String epaId) {
		this.epaId = epaId;
	}
	public void setManifest(String manifest) {
		this.manifest = manifest;
	}
	public void setAccumulationStartDate(Date accumulationStartDate) {
		this.accumulationStartDate = accumulationStartDate;
	}
	public void setGenName(String genName) {
		this.genName = genName;
	}
	public void setGenAddress(String genAddress) {
		this.genAddress = genAddress;
	}
	public void setGenPhone(String genPhone) {
		this.genPhone = genPhone;
	}
	public void setGenCity(String genCity) {
		this.genCity = genCity;
	}
	public void setGenState(String genState) {
		this.genState = genState;
	}
	public void setGenZip(String genZip) {
		this.genZip = genZip;
	}
	public void setEpaWasteNo(String epaWasteNo) {
		this.epaWasteNo = epaWasteNo;
	}
	public void setCaWasteNo(String caWasteNo) {
		this.caWasteNo = caWasteNo;
	}
	public void setContentsComposition(String contentsComposition) {
		this.contentsComposition = contentsComposition;
	}
	public void setPhysicalStateSolid(String physicalStateSolid) {
		this.physicalStateSolid = physicalStateSolid;
	}
	public void setPhysicalStateLiquid(String physicalStateLiquid) {
		this.physicalStateLiquid = physicalStateLiquid;
	}
	public void setFlammable(String flammable) {
		this.flammable = flammable;
	}
	public void setToxic(String toxic) {
		this.toxic = toxic;
	}
	public void setCorrosive(String corrosive) {
		this.corrosive = corrosive;
	}
	public void setReactive(String reactive) {
		this.reactive = reactive;
	}
	public void setHazardOther(String hazardOther) {
		this.hazardOther = hazardOther;
	}
	public void setHazardOtherDetail(String hazardOtherDetail) {
		this.hazardOtherDetail = hazardOtherDetail;
	}
	public void setDot(String dot) {
		this.dot = dot;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getContainerId() {
		return containerId;
	}
	public String getLpadContainerId() {
		return lpadContainerId;
	}
	public BigDecimal getWasteItemId() {
		return wasteItemId;
	}
	public String getEpaId() {
		return epaId;
	}
	public String getManifest() {
		return manifest;
	}
	public Date getAccumulationStartDate() {
		return accumulationStartDate;
	}
	public String getGenName() {
		return genName;
	}
	public String getGenAddress() {
		return genAddress;
	}
	public String getGenPhone() {
		return genPhone;
	}
	public String getGenCity() {
		return genCity;
	}
	public String getGenState() {
		return genState;
	}
	public String getGenZip() {
		return genZip;
	}
	public String getEpaWasteNo() {
		return epaWasteNo;
	}
	public String getCaWasteNo() {
		return caWasteNo;
	}
	public String getContentsComposition() {
		return contentsComposition;
	}
	public String getPhysicalStateSolid() {
		return physicalStateSolid;
	}
	public String getPhysicalStateLiquid() {
		return physicalStateLiquid;
	}
	public String getFlammable() {
		return flammable;
	}
	public String getToxic() {
		return toxic;
	}
	public String getCorrosive() {
		return corrosive;
	}
	public String getReactive() {
		return reactive;
	}
	public String getHazardOther() {
		return hazardOther;
	}
	public String getHazardOtherDetail() {
		return hazardOtherDetail;
	}
	public String getDot() {
		return dot;
	}
}