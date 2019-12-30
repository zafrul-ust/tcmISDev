package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class InventoryInputBean {

 private String searchText;
 private BigDecimal expiresWithin;
 private BigDecimal expiresAfter;
 private BigDecimal arrivesWithin;
 private String onHand;
 private String onOrder;
 private String noOor;
 private String facilityId;
 private String inventory;
 private String collection;
 private String companyId;

 //constructor
 public InventoryInputBean() {
 }

 //setters
 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setInventory(String inventory) {
	this.inventory = inventory;
 }

 public void setSearchText(String searchText) {
	this.searchText = searchText;
 }

 public void setOnHand(String onHand) {
	this.onHand = onHand;
 }

 public void setOnOrder(String onOrder) {
	this.onOrder = onOrder;
 }

 public void setNoOor(String noOor) {
	this.noOor = noOor;
 }

 public void setExpiresWithin(BigDecimal expiresWithin) {
	this.expiresWithin = expiresWithin;
 }

 public void setExpiresAfter(BigDecimal expiresAfter) {
	this.expiresAfter = expiresAfter;
 }

 public void setArrivesWithin(BigDecimal arrivesWithin) {
	this.arrivesWithin = arrivesWithin;
 }

 public void setCollection(String collection) {
	this.collection = collection;
 }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    //getters
 public String getFacilityId() {
	return this.facilityId;
 }

 public String getInventory() {
	return this.inventory;
 }

 public String getSearchText() {
	return this.searchText;
 }

 public String getOnHand() {
	return this.onHand;
 }

 public String getOnOrder() {
	return this.onOrder;
 }

 public String getNoOor() {
	return this.noOor;
 }

 public BigDecimal getExpiresWithin() {
	return this.expiresWithin;
 }

 public BigDecimal getExpiresAfter() {
	return this.expiresAfter;
 }

 public BigDecimal getArrivesWithin() {
	return this.arrivesWithin;
 }

 public String getCollection() {
	return collection;
 }

    public String getCompanyId() {
        return companyId;
    }
}