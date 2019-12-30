package com.tcmis.ws.tablet.beans;

import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InboundShipmentDetailBean <br>
 * 
 * @version: 1.0, Mar 5, 2013 <br>
 *****************************************************************************/

public class InboundShipmentDetailBean extends BaseTabletBean {

	private BigDecimal	customerRmaId;
	private Date		dateInserted;
	private Date		dateUpdated;
	private BigDecimal	docNum;
	private BigDecimal	inboundShipmentDetailId;
	private BigDecimal	inboundShipmentId;
	private String		inventoryGroup;
	private BigDecimal	radianPo;
	private String		receivingPriority;
	private BigDecimal	transferRequestId;

	// constructor
	public InboundShipmentDetailBean() {
	}

	public InboundShipmentDetailBean(ActionForm form, Locale tcmISLocale) {
		super(form, tcmISLocale);
	}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public Date getDateInserted() {
		return dateInserted;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	// getters
	public BigDecimal getInboundShipmentDetailId() {
		return inboundShipmentDetailId;
	}

	public BigDecimal getInboundShipmentId() {
		return inboundShipmentId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public String getReceivingPriority() {
		return receivingPriority;
	}

	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}

	public boolean hasCustomerRmaId() {
		return customerRmaId != null;
	}

	public boolean hasRadianPo() {
		return radianPo != null;
	}

	public boolean hasTransferRequestId() {
		return transferRequestId != null;
	}
	
	public boolean hasDocNum() {
		return docNum != null;
	}

	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}

	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	// setters
	public void setInboundShipmentDetailId(BigDecimal inboundShipmentDetailId) {
		this.inboundShipmentDetailId = inboundShipmentDetailId;
	}

	public void setInboundShipmentId(BigDecimal inboundShipmentId) {
		this.inboundShipmentId = inboundShipmentId;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setReceivingPriority(String receivingPriority) {
		this.receivingPriority = receivingPriority;
	}

	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}

}