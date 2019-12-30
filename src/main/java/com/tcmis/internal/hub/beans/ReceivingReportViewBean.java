package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReceivingReportViewBean <br>
 * @version: 1.0, Oct 20, 2008 <br>
 *****************************************************************************/


public class ReceivingReportViewBean extends BaseDataBean {

	private String hub;
	private String inventoryGroup;
	private Date dateOfReceipt;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private String packaging;
	private String itemDesc;
	private String companyId;
	private String facilityId;
	private String customerPoNo;
	private String releaseNo;
	private String catPartNo;
	private String partDescription;
	private Collection qtyDorCollection;
	private String inventoryGroupName;
	private String itemUom;
	private BigDecimal quantityReceivedItemUom;
	private BigDecimal quantityReturnedItemUom;
	private BigDecimal poLineQuantityItemUom;
	private String uos;
	private BigDecimal quantityReceivedUos;
	private BigDecimal quantityReturnedUos;
	private BigDecimal poLineQuantityUos;
	private BigDecimal quantityReceived;
	private BigDecimal poLineQuantity;
	private String uom;
	private String rowWithSameMonth;

	//constructor
	public ReceivingReportViewBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setReleaseNo(String releaseNo) {
		this.releaseNo = releaseNo;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setQtyDorCollection(Collection qtyDorCollection) {
		this.qtyDorCollection = qtyDorCollection;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}

	public void setQuantityReceivedItemUom(BigDecimal quantityReceivedItemUom) {
		this.quantityReceivedItemUom = quantityReceivedItemUom;
	}

	public void setPoLineQuantityItemUom(BigDecimal poLineQuantityItemUom) {
		this.poLineQuantityItemUom = poLineQuantityItemUom;
	}

	public void setUos(String uos) {
		this.uos = uos;
	}

	public void setQuantityReceivedUos(BigDecimal quantityReceivedUos) {
		this.quantityReceivedUos = quantityReceivedUos;
	}

	public void setPoLineQuantityUos(BigDecimal poLineQuantityUos) {
		this.poLineQuantityUos = poLineQuantityUos;
	}

	public void setQuantityReturnedItemUom(BigDecimal quantityReturnedItemUom) {
		this.quantityReturnedItemUom = quantityReturnedItemUom;
	}

	public void setQuantityReturnedUos(BigDecimal quantityReturnedUos) {
		this.quantityReturnedUos = quantityReturnedUos;
	}

	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public void setPoLineQuantity(BigDecimal poLineQuantity) {
		this.poLineQuantity = poLineQuantity;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public void setRowWithSameMonth(String rowWithSameMonth) {
		this.rowWithSameMonth = rowWithSameMonth;
	}

	//getters
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getPackaging() {
		return packaging;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getReleaseNo() {
		return releaseNo;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}

	public Collection getQtyDorCollection() {
		return qtyDorCollection;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getItemUom() {
		return itemUom;
	}

	public BigDecimal getQuantityReceivedItemUom() {
		return quantityReceivedItemUom;
	}

	public BigDecimal getQuantityReturnedItemUom() {
		return quantityReturnedItemUom;
	}

	public BigDecimal getPoLineQuantityItemUom() {
		return poLineQuantityItemUom;
	}

	public String getUos() {
		return uos;
	}

	public BigDecimal getQuantityReceivedUos() {
		return quantityReceivedUos;
	}

	public BigDecimal getQuantityReturnedUos() {
		return quantityReturnedUos;
	}

	public BigDecimal getPoLineQuantityUos() {
		return poLineQuantityUos;
	}

	public String getUom() {
		return uom;
	}

	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}

	public BigDecimal getPoLineQuantity() {
		return poLineQuantity;
	}

	public String getRowWithSameMonth() {
		return rowWithSameMonth;
	}
}