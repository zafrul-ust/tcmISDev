package com.tcmis.client.catalog.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PkgInventoryDetailWebPrInventoryDetailBean <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class PkgInventoryDetailWebPrInventoryDetailBean
 extends BaseDataBean {

 private Collection itemDescription;
 private Collection partsForItem;
 private Collection onHandMaterial;
 private Collection inSupplyChain;

 //constructor
 public PkgInventoryDetailWebPrInventoryDetailBean() {
 }

 public void setItemDescription(Collection coll) {
	this.itemDescription = coll;
 }

 public void setPartsForItem(Collection coll) {
	this.partsForItem = coll;
 }

 public void setOnHandMaterial(Collection coll) {
	this.onHandMaterial = coll;
 }

 public void setInSupplyChain(Collection coll) {
	this.inSupplyChain = coll;
 }

 public Collection getItemDescription() {
	return this.itemDescription;
 }

 public Collection getPartsForItem() {
	return this.partsForItem;
 }

 public Collection getOnHandMaterial() {
	return this.onHandMaterial;
 }

 public Collection getInSupplyChain() {
	return this.inSupplyChain;
 }

}