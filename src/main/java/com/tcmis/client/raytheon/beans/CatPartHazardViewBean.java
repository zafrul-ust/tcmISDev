package com.tcmis.client.raytheon.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatPartHazardViewBean <br>
 * @version: 1.0, Oct 4, 2006 <br>
 *****************************************************************************/


public class CatPartHazardViewBean extends BaseDataBean {

	private String companyId;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String partShortName;
	private BigDecimal itemId;
	private BigDecimal partId;
	private BigDecimal materialId;
	private Date revisionDate;
	private String materialDesc;
	private String health;
	private String flammability;
	private String reactivity;
	private String specificHazard;
	private String targetOrgan;
	private String signalWord;
	private String hmisFlammability;
	private String hmisReactivity;
	private String hmisHealth;
	private String labelQty;
	private String ok;
  private String inventoryGroup;
  //private String paperSize;

	//constructor
	public CatPartHazardViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public void setFlammability(String flammability) {
		this.flammability = flammability;
	}
	public void setReactivity(String reactivity) {
		this.reactivity = reactivity;
	}
	public void setSpecificHazard(String specificHazard) {
		this.specificHazard = specificHazard;
	}
	public void setTargetOrgan(String targetOrgan) {
		this.targetOrgan = targetOrgan;
	}
	public void setSignalWord(String signalWord) {
		this.signalWord = signalWord;
	}
	public void setHmisFlammability(String hmisFlammability) {
		this.hmisFlammability = hmisFlammability;
	}
	public void setHmisReactivity(String hmisReactivity) {
		this.hmisReactivity = hmisReactivity;
	}
	public void setHmisHealth(String hmisHealth) {
		this.hmisHealth = hmisHealth;
	}
	public void setLabelQty(String labelQty) {
	 this.labelQty = labelQty;
  }
	public void setOk(String ok) {
	 this.ok = ok;
	}
	/*public void setPaperSize(String paperSize) {
	 if (paperSize != null && this.doTrim) {
		this.paperSize = paperSize.trim();
	 }
	 else {
		this.paperSize = paperSize;
	 }
	}*/
 public void setInventoryGroup(String inventoryGroup) {
  this.inventoryGroup = inventoryGroup;
 }

  //getters
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getHealth() {
		return health;
	}
	public String getFlammability() {
		return flammability;
	}
	public String getReactivity() {
		return reactivity;
	}
	public String getSpecificHazard() {
		return specificHazard;
	}
	public String getTargetOrgan() {
		return targetOrgan;
	}
	public String getSignalWord() {
		return signalWord;
	}
	public String getHmisFlammability() {
		return hmisFlammability;
	}
	public String getHmisReactivity() {
		return hmisReactivity;
	}
	public String getHmisHealth() {
		return hmisHealth;
	}
	public String getLabelQty() {
	 return labelQty;
	}
	public String getOk() {
	 return ok;
	}
	/*public String getPaperSize() {
	 return this.paperSize;
	}*/
  public String getInventoryGroup() {
   return inventoryGroup;
  }  
}