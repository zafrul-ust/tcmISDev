package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PackingListDetailViewBean <br>
 * @version: 1.0, Apr 20, 2005 <br>
 *****************************************************************************/


public class PackingListDetailViewBean extends BaseDataBean {

	private BigDecimal shipmentId;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal radianPo;
	private String poNumber;
	private BigDecimal receiptId;
	private String catPartNo;
	private String partShortName;
	private BigDecimal quantityDelivered;
	private String unitOfSale;
	private String deliveryPoint;
	private String deliveryPointDesc;
        private String packaging;
        private String mfgLot;
        private Date expireDate;
        private BigDecimal tcmRefNo;
	private BigDecimal qaStatement;
	private String inboundCertificate;
	private BigDecimal usageFactor;
    private String partDescription;

    //constructor
	public PackingListDetailViewBean() {
	}

	//setters
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setQuantityDelivered(BigDecimal quantityDelivered) {
		this.quantityDelivered = quantityDelivered;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}
        public void setPackaging(String packaging) {
                this.packaging = packaging;
        }
        public void setMfgLot(String mfgLot) {
                this.mfgLot = mfgLot;
        }
        public void setExpireDate(Date expireDate) {
                this.expireDate = expireDate;
        }
        public void setTcmRefNo(BigDecimal tcmRefNo) {
                this.tcmRefNo = tcmRefNo;
        }

	public void setQaStatement(BigDecimal qaStatement) {
		this.qaStatement = qaStatement;
	}

	public void setInboundCertificate(String inboundCertificate) {
		this.inboundCertificate = inboundCertificate;
	}
    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    //getters
	public String getPartDescription() {
        return partDescription;
    }
    public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public BigDecimal getQuantityDelivered() {
		return quantityDelivered;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}
        public String getPackaging() {
                return packaging;
        }
        public String getMfgLot() {
                return mfgLot;
        }
        public Date getExpireDate() {
                return expireDate;
        }
        public BigDecimal getTcmRefNo() {
                return tcmRefNo;
        }

	public BigDecimal getQaStatement() {
		return qaStatement;
	}

	public String getInboundCertificate() {
		return inboundCertificate;
	}

	public BigDecimal getUsageFactor() {
		return usageFactor;
	}

	public void setUsageFactor(BigDecimal usageFactor) {
		this.usageFactor = usageFactor;
	}
	
	
}