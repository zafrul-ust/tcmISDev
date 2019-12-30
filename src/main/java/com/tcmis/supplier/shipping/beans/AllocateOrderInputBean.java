package com.tcmis.supplier.shipping.beans;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

public class AllocateOrderInputBean extends BaseInputBean {
	private String branchPlant;
	private String catPartNo;
	private String city;
	private String customerPo;
	private BigDecimal itemId;
	private String mfgDateRequired;
	private BigDecimal mrNumber;
	private String mrLineItem;
	private String oconus;
	private String originInspectionRequired;
	private BigDecimal partsPerBox;
	private BigDecimal poLine;
	private BigDecimal qtyToPick;
	private BigDecimal radianPo;
	private Date receiptDate;
	private String shipFromLocationId;
	private String shipFromLocationName;
	private String shiptoAddress;
	private String supplier;
	private String supplierSalesOrderNo;
	private Date vendorShipDate;
	private String vmi;
	
	public AllocateOrderInputBean() {
	}

	public AllocateOrderInputBean(ActionForm form, Locale locale) throws Exception {
		try {
			BeanHandler.copyAttributes(form, this, locale);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("branchPlant");
		addHiddenFormField("catPartNo");
		addHiddenFormField("itemId");
		addHiddenFormField("mfgDateRequired");
		addHiddenFormField("mrNumber");
		addHiddenFormField("mrLineItem");
		addHiddenFormField("originInspectionRequired");
		addHiddenFormField("poLine");
		addHiddenFormField("qtyToPick");
		addHiddenFormField("radianPo");
		addHiddenFormField("receiptDate");
		addHiddenFormField("shipFromLocationId");
		addHiddenFormField("supplier");
		addHiddenFormField("vendorShipDate");
		addHiddenFormField("partsPerBox");
		addHiddenFormField("city");
		addHiddenFormField("shipFromLocationName");
		addHiddenFormField("customerPo");
		addHiddenFormField("shiptoAddress");
		addHiddenFormField("oconus");
		addHiddenFormField("vmi");
	}

	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}

	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getMfgDateRequired() {
		return mfgDateRequired;
	}

	public void setMfgDateRequired(String mfgDateRequired) {
		this.mfgDateRequired = mfgDateRequired;
	}

	public BigDecimal getQtyToPick() {
		return qtyToPick;
	}

	public void setQtyToPick(BigDecimal qtyToPick) {
		this.qtyToPick = qtyToPick;
	}

	public String getShipFromLocationId() {
		return shipFromLocationId;
	}

	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getMrNumber() {
		return mrNumber;
	}

	public void setMrNumber(BigDecimal mrNumber) {
		this.mrNumber = mrNumber;
	}

	public String getMrLineItem() {
		return mrLineItem;
	}

	public void setMrLineItem(String mrLineItem) {
		this.mrLineItem = mrLineItem;
	}

	public BigDecimal getPoLine() {
		return poLine;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public Date getVendorShipDate() {
		return vendorShipDate;
	}

	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}

	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}

	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}

	public BigDecimal getPartsPerBox() {
		return partsPerBox;
	}

	public void setPartsPerBox(BigDecimal partsPerBox) {
		this.partsPerBox = partsPerBox;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShipFromLocationName() {
		return shipFromLocationName;
	}

	public void setShipFromLocationName(String shipFromLocationName) {
		this.shipFromLocationName = shipFromLocationName;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	
	public boolean isConfirmAllocation() {
		return "confirmAllocation".equalsIgnoreCase(uAction);
	}

	public String getShiptoAddress() {
		return shiptoAddress;
	}

	public void setShiptoAddress(String shiptoAddress) {
		this.shiptoAddress = shiptoAddress;
	}

	public String getOconus() {
		return oconus;
	}

	public void setOconus(String oconus) {
		this.oconus = oconus;
	}

	public String getVmi() {
		return vmi;
	}

	public void setVmi(String vmi) {
		this.vmi = vmi;
	}
}