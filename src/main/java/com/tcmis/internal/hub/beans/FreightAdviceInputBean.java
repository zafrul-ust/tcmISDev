package com.tcmis.internal.hub.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:07:00 PM
 * To change this template use File | Settings | File Templates.
 */

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class FreightAdviceInputBean
    extends BaseDataBean
{
	private String 	 hub;
    private String 	 inventoryGroup;
	private Date 	 shipDate;
	private String   shipStatus;
    private String cancel;
    private String mrLine;
    private BigDecimal packingGroupId;
    private String searchField;
    private String searchMode;
    private String searchArgument;
	 private String transportationMode;


	 public FreightAdviceInputBean()
	{
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date d) {
		this.shipDate = d;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String s) {
		this.shipStatus = s;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String s) {
		this.cancel = s;
	}

	public String getMrLine() {
		return mrLine;
	}

	public void setMrLine(String s) {
		this.mrLine = s;
	}

	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}

	public void setPackingGroupId(BigDecimal b) {
		this.packingGroupId = b;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getTransportationMode() {
		return transportationMode;
	}

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
}

