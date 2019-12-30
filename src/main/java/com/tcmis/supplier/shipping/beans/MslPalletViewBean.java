package com.tcmis.supplier.shipping.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: MslPalletViewBean <br>
 * @version: 1.0, Dec 1, 2007 <br>
 *****************************************************************************/


public class MslPalletViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String palletId;
	private BigDecimal palletNumber;
	private BigDecimal numberOfPallets;
	private String shipDate;
	private BigDecimal grossWeight;
	private BigDecimal cubicFeet;
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
  private String flashpointInfo;
	private String palletProjectCode;
	private String carrierProNumber;

  //constructor
	public MslPalletViewBean() {
	}

	//setters
  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = StringHandler.emptyIfNull(inventoryGroup);
  }
  public void setPalletId(String palletId) {
    this.palletId = StringHandler.emptyIfNull(palletId);
  }
  public void setPalletNumber(BigDecimal palletNumber) {
    this.palletNumber = palletNumber;
  }
  public void setNumberOfPallets(BigDecimal numberOfPallets) {
    this.numberOfPallets = numberOfPallets;
  }
  public void setShipDate(String shipDate) {
    this.shipDate = StringHandler.emptyIfNull(shipDate);
  }
  public void setGrossWeight(BigDecimal grossWeight) {
    this.grossWeight = grossWeight;
  }
  public void setCubicFeet(BigDecimal cubicFeet) {
    this.cubicFeet = cubicFeet;
  }
  public void setMilstripCode(String milstripCode) {
    this.milstripCode = StringHandler.emptyIfNull(milstripCode);
  }
  public void setTcn(String tcn) {
    this.tcn = StringHandler.emptyIfNull(tcn);
  }
  public void setTransportationPriority(String transportationPriority) {
    this.transportationPriority = StringHandler.emptyIfNull(transportationPriority);
  }
  public void setProjectCode(String projectCode) {
    this.projectCode = StringHandler.emptyIfNull(projectCode);
  }
  public void setFmsCaseNum(String fmsCaseNum) {
    this.fmsCaseNum = StringHandler.emptyIfNull(fmsCaseNum);
  }
  public void setPortOfEmbarkation(String portOfEmbarkation) {
    this.portOfEmbarkation = StringHandler.emptyIfNull(portOfEmbarkation);
  }
  public void setPortOfDebarkation(String portOfDebarkation) {
    this.portOfDebarkation = StringHandler.emptyIfNull(portOfDebarkation);
  }
  public void setTac(String tac) {
    this.tac = StringHandler.emptyIfNull(tac);
  }
  public void setRic(String ric) {
    this.ric = StringHandler.emptyIfNull(ric);
  }
  public void setRdd(String rdd) {
    this.rdd = StringHandler.emptyIfNull(rdd);
  }
  public void setShipFromDodaac(String shipFromDodaac) {
    this.shipFromDodaac = StringHandler.emptyIfNull(shipFromDodaac);
  }
  public void setShipFromCompanyId(String shipFromCompanyId) {
    this.shipFromCompanyId = StringHandler.emptyIfNull(shipFromCompanyId);
  }
  public void setShipFromLocationId(String shipFromLocationId) {
    this.shipFromLocationId = StringHandler.emptyIfNull(shipFromLocationId);
  }
  public void setIgdCompanyId(String igdCompanyId) {
    this.igdCompanyId = StringHandler.emptyIfNull(igdCompanyId);
  }
  public void setIgdLocationId(String igdLocationId) {
    this.igdLocationId = StringHandler.emptyIfNull(igdLocationId);
  }
  public void setShipFromLine1(String shipFromLine1) {
    this.shipFromLine1 = StringHandler.emptyIfNull(shipFromLine1);
  }
  public void setShipFromLine2(String shipFromLine2) {
    this.shipFromLine2 = StringHandler.emptyIfNull(shipFromLine2);
  }
  public void setShipFromLine3(String shipFromLine3) {
    this.shipFromLine3 = StringHandler.emptyIfNull(shipFromLine3);
  }
  public void setShipFromLine4(String shipFromLine4) {
    this.shipFromLine4 = StringHandler.emptyIfNull(shipFromLine4);
  }
  public void setShipToDodaac(String shipToDodaac) {
    this.shipToDodaac = StringHandler.emptyIfNull(shipToDodaac);
  }
  public void setShipToCompanyId(String shipToCompanyId) {
    this.shipToCompanyId = StringHandler.emptyIfNull(shipToCompanyId);
  }
  public void setShipToLocationId(String shipToLocationId) {
    this.shipToLocationId = StringHandler.emptyIfNull(shipToLocationId);
  }
  public void setShipToLine1(String shipToLine1) {
    this.shipToLine1 = StringHandler.emptyIfNull(shipToLine1);
  }
  public void setShipToLine2(String shipToLine2) {
    this.shipToLine2 = StringHandler.emptyIfNull(shipToLine2);
  }
  public void setShipToLine3(String shipToLine3) {
    this.shipToLine3 = StringHandler.emptyIfNull(shipToLine3);
  }
  public void setShipToLine4(String shipToLine4) {
    this.shipToLine4 = StringHandler.emptyIfNull(shipToLine4);
  }
  public void setShipViaDodaac(String shipViaDodaac) {
    this.shipViaDodaac = StringHandler.emptyIfNull(shipViaDodaac);
  }
  public void setShipViaCompanyId(String shipViaCompanyId) {
    this.shipViaCompanyId = StringHandler.emptyIfNull(shipViaCompanyId);
  }
  public void setShipViaLocationId(String shipViaLocationId) {
    this.shipViaLocationId = StringHandler.emptyIfNull(shipViaLocationId);
  }
  public void setShipViaLine1(String shipViaLine1) {
    this.shipViaLine1 = StringHandler.emptyIfNull(shipViaLine1);
  }
  public void setShipViaLine2(String shipViaLine2) {
    this.shipViaLine2 = StringHandler.emptyIfNull(shipViaLine2);
  }
  public void setShipViaLine3(String shipViaLine3) {
    this.shipViaLine3 = StringHandler.emptyIfNull(shipViaLine3);
  }
  public void setShipViaLine4(String shipViaLine4) {
    this.shipViaLine4 = StringHandler.emptyIfNull(shipViaLine4);
  }
	public void setPalletRfid(String palletRfid) {
		this.palletRfid = StringHandler.emptyIfNull(palletRfid);
	}
  public void setCarrierCode(String carrierCode) {
   this.carrierCode = StringHandler.emptyIfNull(carrierCode);
  }
  public void setConsolidationNumber(String consolidationNumber) {
   this.consolidationNumber = StringHandler.emptyIfNull(consolidationNumber);
  }
	public void setFlashpointInfo(String flashpointInfo) {
		this.flashpointInfo = flashpointInfo;
	}
	public void setPalletProjectCode(String palletProjectCode) {
		this.palletProjectCode = palletProjectCode;
	}
	public void setCarrierProNumber(String carrierProNumber) {
		this.carrierProNumber = carrierProNumber;
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
	public String getFlashpointInfo() {
		return flashpointInfo;
	}
	public String getPalletProjectCode() {
		return palletProjectCode;
	}
	public String getCarrierProNumber() {
		return carrierProNumber;
	}  
}