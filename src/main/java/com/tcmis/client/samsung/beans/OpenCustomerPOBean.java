package com.tcmis.client.samsung.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.tcmis.common.framework.BaseDataBean;

public class OpenCustomerPOBean extends BaseDataBean {
	private Date			fdDate;
	private BigDecimal	grQuantity;
	private String			item;
	private String			material;
	private String			materialDescription;
	private BigDecimal	open;
	private String			plant;
	private String			purchaseDoc;
	private BigDecimal	quantity;
	private String			vendor;

	// constructor
	public OpenCustomerPOBean() {
	}

	public OpenCustomerPOBean(List<String> csvRow) {
		int i = 0;
		this.material = csvRow.get(i++);
		this.materialDescription = csvRow.get(i++);
		this.plant = csvRow.get(i++);
		this.purchaseDoc = csvRow.get(i++);
		this.item = csvRow.get(i++);
		if (csvRow.size() == 11) {
			i++; // Skip a line, the file still has "Vend. Mat" column
		}
		this.vendor = csvRow.get(i++);
		this.quantity = new BigDecimal(csvRow.get(i++));
		this.grQuantity = new BigDecimal(csvRow.get(i++));
		this.open = new BigDecimal(csvRow.get(i++));
		try {
			this.fdDate = new SimpleDateFormat("yyyy.dd.MM").parse(csvRow.get(i++));
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	public String getDescription() {
		return "Material: " + material + ",  PurchDoc: " + purchaseDoc + ", Item: " + item + ", Vendor: " + vendor;
	}

	public Date getFdDate() {
		return fdDate;
	}

	public String getGeneratedPoNumber() {
		return purchaseDoc + "-" + vendor + "-" + item;
	}

	public BigDecimal getGrQuantity() {
		return grQuantity;
	}

	public String getItem() {
		return item;
	}

	public String getMaterial() {
		return material;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public String getPlant() {
		return plant;
	}

	public String getPurchaseDoc() {
		return purchaseDoc;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getVendor() {
		return vendor;
	}

	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
	}

	public void setGrQuantity(BigDecimal grQuantity) {
		this.grQuantity = grQuantity;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public void setPurchaseDoc(String purchaseDoc) {
		this.purchaseDoc = purchaseDoc;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
}
