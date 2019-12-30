package com.tcmis.client.dla.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DlaIntOrderCurrentViewBean <br>
 * @version: 1.0, Aug 5, 2008 <br>
 *****************************************************************************/


public class DlaIntOrderCurrentViewBean extends BaseDataBean {

	private String contractNumber;
	private String callNo;
	private String shipToDodaac;
	private String shipToInfo;
	private String markForDodaac;
	private String catPartNo;
	private BigDecimal quantity;
	private String transportationControlNo;
	private String carrier;
	private String trackingNo;
	private String partShortName;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String supplier;
	private String supplierName;
	private String docNumWithSuffix;
	private String displayDetailStatus;
	private BigDecimal displayQuantityShipped;
	private String displayQuantityOpen;
	private String displayProjectedDateShipped;
	private Date displayActualShipDate;
	private String shipFrom;
	private Date reportDate;
	private String issuer;
	private Date edi856ToDlaEllis;
	private String shipFromLocationId;
	private String shipToLocationId;
	private String shipViaLocationId;
	private Date date997;
	private Date edi850ToAirgas;
	private Date airgas997Received;
	private Date poConfirmDate;
	private String comments;
	private BigDecimal prNumber;
	private String lineItem;
	private String shipToAddressCode;

  //constructor
	public DlaIntOrderCurrentViewBean() {
	}

	//setters
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setShipToInfo(String shipToInfo) {
		this.shipToInfo = shipToInfo;
	}
	public void setMarkForDodaac(String markForDodaac) {
		this.markForDodaac = markForDodaac;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setTransportationControlNo(String transportationControlNo) {
		this.transportationControlNo = transportationControlNo;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setDocNumWithSuffix(String docNumWithSuffix) {
		this.docNumWithSuffix = docNumWithSuffix;
	}
	public void setDisplayDetailStatus(String displayDetailStatus) {
		this.displayDetailStatus = displayDetailStatus;
	}
	public void setDisplayQuantityShipped(BigDecimal displayQuantityShipped) {
		this.displayQuantityShipped = displayQuantityShipped;
	}
	public void setDisplayQuantityOpen(String displayQuantityOpen) {
		this.displayQuantityOpen = displayQuantityOpen;
	}
	public void setDisplayProjectedDateShipped(String displayProjectedDateShipped) {
		this.displayProjectedDateShipped = displayProjectedDateShipped;
	}
	public void setDisplayActualShipDate(Date displayActualShipDate) {
		this.displayActualShipDate = displayActualShipDate;
	}
	public void setShipFrom(String shipFrom) {
		this.shipFrom = shipFrom;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public void setEdi856ToDlaEllis(Date edi856ToDlaEllis) {
		this.edi856ToDlaEllis = edi856ToDlaEllis;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipViaLocationId(String shipViaLocationId) {
		this.shipViaLocationId = shipViaLocationId;
	}
	public void setDate997(Date date997) {
		this.date997 = date997;
	}
	public void setEdi850ToAirgas(Date edi850ToAirgas) {
		this.edi850ToAirgas = edi850ToAirgas;
	}
	public void setAirgas997Received(Date airgas997Received) {
		this.airgas997Received = airgas997Received;
	}
	public void setPoConfirmDate(Date poConfirmDate) {
		this.poConfirmDate = poConfirmDate;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }
  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }
  public void setShipToAddressCode(String shipToAddressCode) {
    this.shipToAddressCode = shipToAddressCode;
  }

  //getters
	public String getContractNumber() {
		return contractNumber;
	}
	public String getCallNo() {
		return callNo;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getShipToInfo() {
		return shipToInfo;
	}
	public String getMarkForDodaac() {
		return markForDodaac;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getTransportationControlNo() {
		return transportationControlNo;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getDocNumWithSuffix() {
		return docNumWithSuffix;
	}
	public String getDisplayDetailStatus() {
		return displayDetailStatus;
	}
	public BigDecimal getDisplayQuantityShipped() {
		return displayQuantityShipped;
	}
	public String getDisplayQuantityOpen() {
		return displayQuantityOpen;
	}
	public String getDisplayProjectedDateShipped() {
		return displayProjectedDateShipped;
	}
	public Date getDisplayActualShipDate() {
		return displayActualShipDate;
	}
	public String getShipFrom() {
		return shipFrom;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public String getIssuer() {
		return issuer;
	}
	public Date getEdi856ToDlaEllis() {
		return edi856ToDlaEllis;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipViaLocationId() {
		return shipViaLocationId;
	}
	public Date getDate997() {
		return date997;
	}
	public Date getEdi850ToAirgas() {
		return edi850ToAirgas;
	}
	public Date getAirgas997Received() {
		return airgas997Received;
	}
	public Date getPoConfirmDate() {
		return poConfirmDate;
	}
	public String getComments() {
		return comments;
	}
  public BigDecimal getPrNumber() {
    return prNumber;
  }
  public String getLineItem() {
    return lineItem;
  }
  public String getShipToAddressCode() {
    return shipToAddressCode;
  }
}
