package com.tcmis.internal.print.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TcnPalletViewBean <br>
 * @version: 1.0, Mar 4, 2008 <br>
 *****************************************************************************/


public class TcnPalletViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String palletId;
	private BigDecimal palletNumber;
	private BigDecimal numberOfPallets;
	private String shipDate;
	private BigDecimal grossWeight;
	private BigDecimal cubicFeet;
	private BigDecimal quantity;
	private String nsn;
	private String unitOfIssue;
	private String milstripCode;
	private String tcn;
	private String transportationPriority;
	private String projectCode;
	private String fmsCaseNum;
	private String portOfEmbarkation;
	private String portOfDebarkation;
	private String tac;
	private String ric;
	private String rdd;
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
	private String palletRfid;
	private String carrierCode;
	private String consolidationNumber;


	//constructor
	public TcnPalletViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}
	public void setPalletNumber(BigDecimal palletNumber) {
		this.palletNumber = palletNumber;
	}
	public void setNumberOfPallets(BigDecimal numberOfPallets) {
		this.numberOfPallets = numberOfPallets;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}
	public void setCubicFeet(BigDecimal cubicFeet) {
		this.cubicFeet = cubicFeet;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}
	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
	}
	public void setTcn(String tcn) {
		this.tcn = tcn;
	}
	public void setTransportationPriority(String transportationPriority) {
		this.transportationPriority = transportationPriority;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public void setFmsCaseNum(String fmsCaseNum) {
		this.fmsCaseNum = fmsCaseNum;
	}
	public void setPortOfEmbarkation(String portOfEmbarkation) {
		this.portOfEmbarkation = portOfEmbarkation;
	}
	public void setPortOfDebarkation(String portOfDebarkation) {
		this.portOfDebarkation = portOfDebarkation;
	}
	public void setTac(String tac) {
		this.tac = tac;
	}
	public void setRic(String ric) {
		this.ric = ric;
	}
	public void setRdd(String rdd) {
		this.rdd = rdd;
	}
	public void setShipFromDodaac(String shipFromDodaac) {
		this.shipFromDodaac = shipFromDodaac;
	}
	public void setShipFromCompanyId(String shipFromCompanyId) {
		this.shipFromCompanyId = shipFromCompanyId;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setIgdCompanyId(String igdCompanyId) {
		this.igdCompanyId = igdCompanyId;
	}
	public void setIgdLocationId(String igdLocationId) {
		this.igdLocationId = igdLocationId;
	}
	public void setShipFromLine1(String shipFromLine1) {
		this.shipFromLine1 = shipFromLine1;
	}
	public void setShipFromLine2(String shipFromLine2) {
		this.shipFromLine2 = shipFromLine2;
	}
	public void setShipFromLine3(String shipFromLine3) {
		this.shipFromLine3 = shipFromLine3;
	}
	public void setShipFromLine4(String shipFromLine4) {
		this.shipFromLine4 = shipFromLine4;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipToLine1(String shipToLine1) {
		this.shipToLine1 = shipToLine1;
	}
	public void setShipToLine2(String shipToLine2) {
		this.shipToLine2 = shipToLine2;
	}
	public void setShipToLine3(String shipToLine3) {
		this.shipToLine3 = shipToLine3;
	}
	public void setShipToLine4(String shipToLine4) {
		this.shipToLine4 = shipToLine4;
	}
	public void setShipViaDodaac(String shipViaDodaac) {
		this.shipViaDodaac = shipViaDodaac;
	}
	public void setShipViaCompanyId(String shipViaCompanyId) {
		this.shipViaCompanyId = shipViaCompanyId;
	}
	public void setShipViaLocationId(String shipViaLocationId) {
		this.shipViaLocationId = shipViaLocationId;
	}
	public void setShipViaLine1(String shipViaLine1) {
		this.shipViaLine1 = shipViaLine1;
	}
	public void setShipViaLine2(String shipViaLine2) {
		this.shipViaLine2 = shipViaLine2;
	}
	public void setShipViaLine3(String shipViaLine3) {
		this.shipViaLine3 = shipViaLine3;
	}
	public void setShipViaLine4(String shipViaLine4) {
		this.shipViaLine4 = shipViaLine4;
	}
	public void setPalletRfid(String palletRfid) {
		this.palletRfid = palletRfid;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getPalletId() {
		return palletId;
	}
	public BigDecimal getPalletNumber() {
		return palletNumber;
	}
	public BigDecimal getNumberOfPallets() {
		return numberOfPallets;
	}
	public String getShipDate() {
		return shipDate;
	}
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}
	public BigDecimal getCubicFeet() {
		return cubicFeet;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getNsn() {
		return nsn;
	}
	public String getUnitOfIssue() {
		return unitOfIssue;
	}
	public String getMilstripCode() {
		return milstripCode;
	}
	public String getTcn() {
		return tcn;
	}
	public String getTransportationPriority() {
		return transportationPriority;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public String getFmsCaseNum() {
		return fmsCaseNum;
	}
	public String getPortOfEmbarkation() {
		return portOfEmbarkation;
	}
	public String getPortOfDebarkation() {
		return portOfDebarkation;
	}
	public String getTac() {
		return tac;
	}
	public String getRic() {
		return ric;
	}
	public String getRdd() {
		return rdd;
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
	public String getPalletRfid() {
		return palletRfid;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getConsolidationNumber() {
		return consolidationNumber;
	}
}