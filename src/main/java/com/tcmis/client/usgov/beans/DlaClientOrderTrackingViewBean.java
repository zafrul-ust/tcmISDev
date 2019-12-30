package com.tcmis.client.usgov.beans;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DlaClientOrderTrackingViewBean <br>
 * @version: 1.0, Aug 8, 2008 <br>
 *****************************************************************************/


public class DlaClientOrderTrackingViewBean extends BaseDataBean {

	private String docNum;
	private String contractNumber;
	private String callNo;
	private String shipToDodaac;
	private String markForDodaac;
	private String transactionType;
	private String catPartNo;
	private Date date997;
	private Date shipToOkDate;
	private BigDecimal quantity;
	private String uom;
	private String status;
	private String transportationControlNo;
	private String usgovShipmentNumber;
	private BigDecimal qtyShipped;
	private Date actualShipDate;
	private Date projectedDateShipped;
	private Date arrivalDate;
	private String carrier;
	private String trackingNo;
	private String comments;
	private String openOrder;
	private BigDecimal addressChangeRequestId;
	private String partShortName;
	private String shipToLocationId;
	private String shipViaLocationId;
	private String priorityRating;
	private String displayStatus;
	private String poSuffix;
	private String origPriorityRating;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal shipmentId;
	private String open;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String projectCode;
	private String projectName;
	private String verifiedForAsn;
	private String shipFromLocationId;
	private String supplierSalesOrderNo;
	private String shipFromLocationDesc;
	private Date expediteDate;
    private String mfgLiteratureContent;
    private String orderStatus;
	private Date orderDate;

    public String getMfgLiteratureContent() {
        return mfgLiteratureContent;
    }

    public void setMfgLiteratureContent(String mfgLiteratureContent) {
        this.mfgLiteratureContent = mfgLiteratureContent;
    }
    
    private Collection<DlaClientOrderTrackingViewBean> detailCollection = new Vector();
    
    //constructor
	public DlaClientOrderTrackingViewBean() {
	}

	//setters
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setMarkForDodaac(String markForDodaac) {
		this.markForDodaac = markForDodaac;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setDate997(Date date997) {
		this.date997 = date997;
	}
	public void setShipToOkDate(Date shipToOkDate) {
		this.shipToOkDate = shipToOkDate;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setTransportationControlNo(String transportationControlNo) {
		this.transportationControlNo = transportationControlNo;
	}
	public void setUsgovShipmentNumber(String usgovShipmentNumber) {
		this.usgovShipmentNumber = usgovShipmentNumber;
	}
	public void setQtyShipped(BigDecimal qtyShipped) {
		this.qtyShipped = qtyShipped;
	}
	public void setActualShipDate(Date actualShipDate) {
		this.actualShipDate = actualShipDate;
	}
	public void setProjectedDateShipped(Date projectedDateShipped) {
		this.projectedDateShipped = projectedDateShipped;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setOpenOrder(String openOrder) {
		this.openOrder = openOrder;
	}
	public void setAddressChangeRequestId(BigDecimal addressChangeRequestId) {
		this.addressChangeRequestId = addressChangeRequestId;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipViaLocationId(String shipViaLocationId) {
		this.shipViaLocationId = shipViaLocationId;
	}
	public void setPriorityRating(String priorityRating) {
		this.priorityRating = priorityRating;
	}
	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}
	public void setPoSuffix(String poSuffix) {
		this.poSuffix = poSuffix;
	}
	public void setOrigPriorityRating(String origPriorityRating) {
		this.origPriorityRating = origPriorityRating;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setVerifiedForAsn(String verifiedForAsn) {
		this.verifiedForAsn = verifiedForAsn;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
	public void setShipFromLocationDesc(String shipFromLocationDesc) {
		this.shipFromLocationDesc = shipFromLocationDesc;
	}
    public void setDetailCollection(Collection<DlaClientOrderTrackingViewBean> c) {
		this.detailCollection = c;
	}
	public void addDetailBean(DlaClientOrderTrackingViewBean bean) {
		this.detailCollection.add(bean);
	}
    public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}    
    public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	//getters
	public Date getOrderDate() {
		return orderDate;
	}
	public String getDocNum() {
		return docNum;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public String getCallNo() {
		return callNo;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getMarkForDodaac() {
		return markForDodaac;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public Date getDate997() {
		return date997;
	}
	public Date getShipToOkDate() {
		return shipToOkDate;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getUom() {
		return uom;
	}
	public String getStatus() {
		return status;
	}
	public String getTransportationControlNo() {
		return transportationControlNo;
	}
	public String getUsgovShipmentNumber() {
		return usgovShipmentNumber;
	}
	public BigDecimal getQtyShipped() {
		return qtyShipped;
	}
	public Date getActualShipDate() {
		return actualShipDate;
	}
	public Date getProjectedDateShipped() {
		return projectedDateShipped;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public String getComments() {
		return comments;
	}
	public String getOpenOrder() {
		return openOrder;
	}
	public BigDecimal getAddressChangeRequestId() {
		return addressChangeRequestId;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipViaLocationId() {
		return shipViaLocationId;
	}
	public String getPriorityRating() {
		return priorityRating;
	}
	public String getDisplayStatus() {
		return displayStatus;
	}
	public String getPoSuffix() {
		return poSuffix;
	}
	public String getOrigPriorityRating() {
		return origPriorityRating;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getOpen() {
		return open;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getVerifiedForAsn() {
		return verifiedForAsn;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	public String getShipFromLocationDesc() {
		return shipFromLocationDesc;
	}
    public Collection<DlaClientOrderTrackingViewBean> getDetailCollection() {
        return detailCollection;
    }

	public Date getExpediteDate() {
		return expediteDate;
	}

	public void setExpediteDate(Date expediteDate) {
		this.expediteDate = expediteDate;
	}
}