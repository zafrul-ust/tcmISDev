package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PossAddRequestBean <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class PossAddRequestBean extends BaseDataBean {

	private String specId;
	private String msdsNumber;
	private String suggestedVendor;
	private String maxSuggestedVendorPartNo;
	private String properShippingName;
	private String hazardClass;
	private String unNumber;
	private String naNumber;
	private String packingGroup;
	private String nfpaHmisNumbers;
	private BigDecimal requestId;
	private String possNo;
	private String tradename;
	private String description;
	private String manufacturer;
	private String possSize;
	private String manufacturersPart;
	private String dwgOrSpecNumber;
	private String testingReqsDocument;
	private String qaAttPkgAtt;
	private String dupInstock;
	private String dupPartno;
	private String newPoss;
	private String replacePoss;
	private String replPartno;
	private String issTozeroDelete;
	private String delUponRecv;
	private String dispOther;
	private String dispOtherText;
	private String estMonUsage;
	private String store;
	private String slInitRetest;
	private String age;
	private String ordFacility;
	private String ordSourceCd;
	private String approvedUsers;
	private String requestorLast;
	private String requestorFirst;
	private String requestorMiddle;
	private String mfgAddress;
	private String mfgPhone;
	private String distributorAddress;
	private String distributorPhone;
	private String additionalSpecs;
	private String specialNotes;


	//constructor
	public PossAddRequestBean() {
	}

	//setters
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	public void setMsdsNumber(String msdsNumber) {
		this.msdsNumber = msdsNumber;
	}
	public void setSuggestedVendor(String suggestedVendor) {
		this.suggestedVendor = suggestedVendor;
	}
	public void setMaxSuggestedVendorPartNo(String maxSuggestedVendorPartNo) {
		this.maxSuggestedVendorPartNo = maxSuggestedVendorPartNo;
	}
	public void setProperShippingName(String properShippingName) {
		this.properShippingName = properShippingName;
	}
	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}
	public void setUnNumber(String unNumber) {
		this.unNumber = unNumber;
	}
	public void setNaNumber(String naNumber) {
		this.naNumber = naNumber;
	}
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}
	public void setNfpaHmisNumbers(String nfpaHmisNumbers) {
		this.nfpaHmisNumbers = nfpaHmisNumbers;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setPossNo(String possNo) {
		this.possNo = possNo;
	}
	public void setTradename(String tradename) {
		this.tradename = tradename;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setPossSize(String possSize) {
		this.possSize = possSize;
	}
	public void setManufacturersPart(String manufacturersPart) {
		this.manufacturersPart = manufacturersPart;
	}
	public void setDwgOrSpecNumber(String dwgOrSpecNumber) {
		this.dwgOrSpecNumber = dwgOrSpecNumber;
	}
	public void setTestingReqsDocument(String testingReqsDocument) {
		this.testingReqsDocument = testingReqsDocument;
	}
	public void setQaAttPkgAtt(String qaAttPkgAtt) {
		this.qaAttPkgAtt = qaAttPkgAtt;
	}
	public void setDupInstock(String dupInstock) {
		this.dupInstock = dupInstock;
	}
	public void setDupPartno(String dupPartno) {
		this.dupPartno = dupPartno;
	}
	public void setNewPoss(String newPoss) {
		this.newPoss = newPoss;
	}
	public void setReplacePoss(String replacePoss) {
		this.replacePoss = replacePoss;
	}
	public void setReplPartno(String replPartno) {
		this.replPartno = replPartno;
	}
	public void setIssTozeroDelete(String issTozeroDelete) {
		this.issTozeroDelete = issTozeroDelete;
	}
	public void setDelUponRecv(String delUponRecv) {
		this.delUponRecv = delUponRecv;
	}
	public void setDispOther(String dispOther) {
		this.dispOther = dispOther;
	}
	public void setDispOtherText(String dispOtherText) {
		this.dispOtherText = dispOtherText;
	}
	public void setEstMonUsage(String estMonUsage) {
		this.estMonUsage = estMonUsage;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public void setSlInitRetest(String slInitRetest) {
		this.slInitRetest = slInitRetest;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setOrdFacility(String ordFacility) {
		this.ordFacility = ordFacility;
	}
	public void setOrdSourceCd(String ordSourceCd) {
		this.ordSourceCd = ordSourceCd;
	}
	public void setApprovedUsers(String approvedUsers) {
		this.approvedUsers = approvedUsers;
	}
	public void setRequestorLast(String requestorLast) {
		this.requestorLast = requestorLast;
	}
	public void setRequestorFirst(String requestorFirst) {
		this.requestorFirst = requestorFirst;
	}
	public void setRequestorMiddle(String requestorMiddle) {
		this.requestorMiddle = requestorMiddle;
	}
	public void setMfgAddress(String mfgAddress) {
		this.mfgAddress = mfgAddress;
	}
	public void setMfgPhone(String mfgPhone) {
		this.mfgPhone = mfgPhone;
	}
	public void setDistributorAddress(String distributorAddress) {
		this.distributorAddress = distributorAddress;
	}
	public void setDistributorPhone(String distributorPhone) {
		this.distributorPhone = distributorPhone;
	}
	public void setAdditionalSpecs(String additionalSpecs) {
		this.additionalSpecs = additionalSpecs;
	}
	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes;
	}


	//getters
	public String getSpecId() {
		return specId;
	}
	public String getMsdsNumber() {
		return msdsNumber;
	}
	public String getSuggestedVendor() {
		return suggestedVendor;
	}
	public String getMaxSuggestedVendorPartNo() {
		return maxSuggestedVendorPartNo;
	}
	public String getProperShippingName() {
		return properShippingName;
	}
	public String getHazardClass() {
		return hazardClass;
	}
	public String getUnNumber() {
		return unNumber;
	}
	public String getNaNumber() {
		return naNumber;
	}
	public String getPackingGroup() {
		return packingGroup;
	}
	public String getNfpaHmisNumbers() {
		return nfpaHmisNumbers;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public String getPossNo() {
		return possNo;
	}
	public String getTradename() {
		return tradename;
	}
	public String getDescription() {
		return description;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getPossSize() {
		return possSize;
	}
	public String getManufacturersPart() {
		return manufacturersPart;
	}
	public String getDwgOrSpecNumber() {
		return dwgOrSpecNumber;
	}
	public String getTestingReqsDocument() {
		return testingReqsDocument;
	}
	public String getQaAttPkgAtt() {
		return qaAttPkgAtt;
	}
	public String getDupInstock() {
		return dupInstock;
	}
	public String getDupPartno() {
		return dupPartno;
	}
	public String getNewPoss() {
		return newPoss;
	}
	public String getReplacePoss() {
		return replacePoss;
	}
	public String getReplPartno() {
		return replPartno;
	}
	public String getIssTozeroDelete() {
		return issTozeroDelete;
	}
	public String getDelUponRecv() {
		return delUponRecv;
	}
	public String getDispOther() {
		return dispOther;
	}
	public String getDispOtherText() {
		return dispOtherText;
	}
	public String getEstMonUsage() {
		return estMonUsage;
	}
	public String getStore() {
		return store;
	}
	public String getSlInitRetest() {
		return slInitRetest;
	}
	public String getAge() {
		return age;
	}
	public String getOrdFacility() {
		return ordFacility;
	}
	public String getOrdSourceCd() {
		return ordSourceCd;
	}
	public String getApprovedUsers() {
		return approvedUsers;
	}
	public String getRequestorLast() {
		return requestorLast;
	}
	public String getRequestorFirst() {
		return requestorFirst;
	}
	public String getRequestorMiddle() {
		return requestorMiddle;
	}
	public String getMfgAddress() {
		return mfgAddress;
	}
	public String getMfgPhone() {
		return mfgPhone;
	}
	public String getDistributorAddress() {
		return distributorAddress;
	}
	public String getDistributorPhone() {
		return distributorPhone;
	}
	public String getAdditionalSpecs() {
		return additionalSpecs;
	}
	public String getSpecialNotes() {
		return specialNotes;
	}
}