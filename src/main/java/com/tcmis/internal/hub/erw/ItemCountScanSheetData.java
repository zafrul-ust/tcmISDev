package com.tcmis.internal.hub.erw;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CountScanSetupViewBean;

public class ItemCountScanSheetData {
 private String inventoryGroup;
 private String companyId;
 private String catalogId;
 private String catPartNo;
 private String partGroupNo;
 private String countUom;
 private String itemId;
 private String description;
 private String hub;
 private String stockingMethod;
 private String countType;
 private String itemIdPartBarcode;
 private String inventoryGroupBarcode;

 public ItemCountScanSheetData(CountScanSetupViewBean countScanSetupViewBean) {
	inventoryGroup = StringHandler.emptyIfNull(countScanSetupViewBean.
	 getInventoryGroup());
	companyId = StringHandler.emptyIfNull(countScanSetupViewBean.getCompanyId());
	catalogId = StringHandler.emptyIfNull(countScanSetupViewBean.getCatalogId());
	catPartNo = StringHandler.emptyIfNull(countScanSetupViewBean.getCatPartNo());
	partGroupNo = NumberHandler.emptyIfNull(countScanSetupViewBean.getPartGroupNo());
	countUom = StringHandler.emptyIfNull(countScanSetupViewBean.getCountUom());
	itemId = StringHandler.emptyIfNull(countScanSetupViewBean.getItemId());
	description = StringHandler.emptyIfNull(countScanSetupViewBean.getDescription());
	hub = StringHandler.emptyIfNull(countScanSetupViewBean.getHub());
	stockingMethod = StringHandler.emptyIfNull(countScanSetupViewBean.
	 getStockingMethod());
	countType = StringHandler.emptyIfNull(countScanSetupViewBean.getCountType());

	if ("ITEM".equalsIgnoreCase(countType))
	{
	 try {
		itemIdPartBarcode = com.tcmis.common.util.BarCodeHandler.Code128b("I" +
		 itemId + "");
	 }
	 catch (Exception ex) {
	 }
	}
  else if ("PART".equalsIgnoreCase(countType))
	{
	 try {
		itemIdPartBarcode = com.tcmis.common.util.BarCodeHandler.Code128b("P"+catPartNo.toUpperCase()+"");
	 }
	 catch (Exception ex1) {
	 }
	}

	try {
	 inventoryGroupBarcode = com.tcmis.common.util.BarCodeHandler.Code128b("INV" +
		inventoryGroup.toUpperCase() + "");
	}
	catch (Exception ex2) {
	}

 }

 //getters
 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public String getCompanyId() {
	return companyId;
 }

 public String getCatalogId() {
	return catalogId;
 }

 public String getCatPartNo() {
	return catPartNo;
 }

 public String getPartGroupNo() {
	return partGroupNo;
 }

 public String getCountUom() {
	return countUom;
 }

 public String getItemId() {
	return itemId;
 }

 public String getDescription() {
	return description;
 }

 public String getHub() {
	return hub;
 }

 public String getStockingMethod() {
	return stockingMethod;
 }

 public String getCountType() {
	return countType;
 }

 public String getItemIdPartBarcode() {
	return itemIdPartBarcode;
 }

 public String getInventoryGroupBarcode() {
	return inventoryGroupBarcode;
 }

 public static Vector getFieldVector() {
	Vector v = new Vector(11);
	v.addElement("inventoryGroup = getInventoryGroup");
	v.addElement("companyId = getCompanyId");
	v.addElement("catalogId = getCatalogId");
	v.addElement("catPartNo = getCatPartNo");
	v.addElement("partGroupNo = getPartGroupNo");
	v.addElement("countUom = getCountUom");
	v.addElement("itemId = getItemId");
	v.addElement("description = getDescription");
	v.addElement("hub = getHub");
	v.addElement("stockingMethod = getStockingMethod");
	v.addElement("countType = getCountType");
	v.addElement("itemIdPartBarcode = getItemIdPartBarcode");
	v.addElement("inventoryGroupBarcode = getInventoryGroupBarcode");

	return v;
 }

 public static Vector getVector(Collection in) {
	Vector out = new Vector();
	Iterator iterator = in.iterator();
	while (iterator.hasNext()) {
	 out.addElement(new ItemCountScanSheetData( (CountScanSetupViewBean) iterator.
		next()));
}
	return out;
 }
}