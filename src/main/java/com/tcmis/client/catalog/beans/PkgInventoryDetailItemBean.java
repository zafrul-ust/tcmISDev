package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PkgInvDetInventoryViewBean <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class PkgInventoryDetailItemBean
 extends BaseDataBean {

 /*private String catalogId;
		private String catPartNo;
		private BigDecimal partGroupNo;
		private String partDescription;
		private String stockingMethod;
		private String setPoints;
		private String inventoryGroup;*/
 private String itemsPerPart;
 private BigDecimal itemId;
 private BigDecimal qtyAvailable;
 private BigDecimal qtyHeld;
 private BigDecimal qtyOnOrder;
 private BigDecimal qtyInPurchasing;
 /*private BigDecimal materialId;
		private String materialDesc;
		private String mfgDesc;
		private String packaging;
		private String mfgPartNo;
		private String msdsOnLine;*/
 private String itemPackaging;
 private Date lastCountDate;
 private Collection componentCollection;

 //constructor
 public PkgInventoryDetailItemBean() {
 }

 //setters
 /*public void setCatalogId(String catalogId) {
	 this.catalogId = catalogId;
		}
		public void setCatPartNo(String catPartNo) {
	 this.catPartNo = catPartNo;
		}
		public void setPartGroupNo(BigDecimal partGroupNo) {
	 this.partGroupNo = partGroupNo;
		}
		public void setPartDescription(String partDescription) {
	 this.partDescription = partDescription;
		}
		public void setStockingMethod(String stockingMethod) {
	 this.stockingMethod = stockingMethod;
		}
		public void setSetPoints(String setPoints) {
	 this.setPoints = setPoints;
		}
		public void setInventoryGroup(String inventoryGroup) {
	 this.inventoryGroup = inventoryGroup;
		}*/

 public void setItemsPerPart(String itemsPerPart) {
	this.itemsPerPart = itemsPerPart;
 }

 public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
 }

 public void setQtyAvailable(BigDecimal qtyAvailable) {
	this.qtyAvailable = qtyAvailable;
 }

 public void setQtyHeld(BigDecimal qtyHeld) {
	this.qtyHeld = qtyHeld;
 }

 public void setQtyOnOrder(BigDecimal qtyOnOrder) {
	this.qtyOnOrder = qtyOnOrder;
 }

 public void setQtyInPurchasing(BigDecimal qtyInPurchasing) {
	this.qtyInPurchasing = qtyInPurchasing;
 }

 /*public void setMaterialId(BigDecimal materialId) {
	 this.materialId = materialId;
		}
		public void setMaterialDesc(String materialDesc) {
	 this.materialDesc = materialDesc;
		}
		public void setMfgDesc(String mfgDesc) {
	 this.mfgDesc = mfgDesc;
		}
		public void setPackaging(String packaging) {
	 this.packaging = packaging;
		}
		public void setMfgPartNo(String mfgPartNo) {
	 this.mfgPartNo = mfgPartNo;
		}
		public void setMsdsOnLine(String msdsOnLine) {
	 this.msdsOnLine = msdsOnLine;
		}*/

 public void setItemPackaging(String itemPackaging) {
	this.itemPackaging = itemPackaging;
 }

 public void setLastCountDate(Date lastCountDate) {
	this.lastCountDate = lastCountDate;
 }

 public void setComponentCollection(Collection componentCollection) {
	this.componentCollection = componentCollection;
 }

 //getters
 /*public String getCatalogId() {
	 return catalogId;
		}
		public String getCatPartNo() {
	 return catPartNo;
		}
		public BigDecimal getPartGroupNo() {
	 return partGroupNo;
		}
		public String getPartDescription() {
	 return partDescription;
		}
		public String getStockingMethod() {
	 return stockingMethod;
		}
		public String getSetPoints() {
	 return setPoints;
		}
		public String getInventoryGroup() {
	 return inventoryGroup;
		}*/

 public String getItemsPerPart() {
	return itemsPerPart;
 }

 public BigDecimal getItemId() {
	return itemId;
 }

 public BigDecimal getQtyAvailable() {
	return qtyAvailable;
 }

 public BigDecimal getQtyHeld() {
	return qtyHeld;
 }

 public BigDecimal getQtyOnOrder() {
	return qtyOnOrder;
 }

 public BigDecimal getQtyInPurchasing() {
	return qtyInPurchasing;
 }

 /*public BigDecimal getMaterialId() {
	 return materialId;
		}
		public String getMaterialDesc() {
	 return materialDesc;
		}
		public String getMfgDesc() {
	 return mfgDesc;
		}
		public String getPackaging() {
	 return packaging;
		}
		public String getMfgPartNo() {
	 return mfgPartNo;
		}
		public String getMsdsOnLine() {
	 return msdsOnLine;
		}*/

 public String getItemPackaging() {
	return itemPackaging;
 }

 public Date getLastCountDate() {
	return lastCountDate;
 }

 public Collection getComponentCollection() {
	return componentCollection;
 }

}