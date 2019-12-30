package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TempShipmentHistoryViewBean <br>
 * 
 * @version: 1.0, Apr 13, 2005 <br>
 *****************************************************************************/

public class ReconciliationInputBean extends BaseDataBean
{

	private static final long serialVersionUID = -695823314323053263L;
	private String binFrom;
	private String binTo;
	private String checkbox;
	private Date countDate;
	private String countDateString;
	private String countId;
	private String countType;
	private String hub;
	private String hubName;
	private String[] inventoryGroup;
	private BigDecimal itemId;
	private String opsEntityId;
	private boolean printOnHandCheckbox;
	private String room;
	private String[] lotStatus;
	private String radiobox;
	private String searchArgument;

	private String searchField;
	private String searchMode;
	private String sortBy;
	private String inputDate;
	private String countStartedByName;
	private boolean skipCountedCheckbox;
	private boolean includePartNumbers;

	// constructor
	public ReconciliationInputBean()
	{
	}

	public String getBinFrom()
	{
		return binFrom;
	}

	public String getBinTo()
	{
		return binTo;
	}

	public String getCheckbox()
	{
		return checkbox;
	}

	public Date getCountDate()
	{
		return countDate;
	}

	public String getCountDateString()
	{
		return countDateString;
	}

	public String getCountId()
	{
		return countId;
	}

	public String getCountType()
	{
		return countType;
	}

	public String getHub()
	{
		return hub;
	}

	public String getHubName()
	{
		return hubName;
	}

	public String[] getInventoryGroup()
	{
		return inventoryGroup;
	}

	public BigDecimal getItemId()
	{
		return itemId;
	}

	public String getOpsEntityId()
	{
		return opsEntityId;
	}

	/**
	 * @return the printOnHandCheckbox
	 */
	public boolean getPrintOnHandCheckbox()
	{
		return printOnHandCheckbox;
	}

	public String getRoom()
	{
		return room;
	}

	public String getSearchArgument()
	{
		return searchArgument;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public String getSearchMode()
	{
		return searchMode;
	}

	public String getSortBy()
	{
		return sortBy;
	}
	
	public String getCountStartedByName() {
		return countStartedByName;
	}
	
	public void setCountStartedByName(String countStartedByName) {
		this.countStartedByName = countStartedByName;
	}

	public void setBinFrom(String binFrom)
	{
		this.binFrom = binFrom;
	}

	public void setBinTo(String binTo)
	{
		this.binTo = binTo;
	}

	public void setCheckbox(String checkbox)
	{
		this.checkbox = checkbox;
	}

	public void setCountDate(Date countDate)
	{
		this.countDate = countDate;
	}

	public void setCountDateString(String countDateString)
	{
		this.countDateString = countDateString;
	}

	public void setCountId(String countId)
	{
		this.countId = countId;
	}

	public void setCountType(String countType)
	{
		this.countType = countType;
	}

	public void setHub(String hub)
	{
		this.hub = hub;
	}

	public void setHubName(String hubName)
	{
		this.hubName = hubName;
	}

	public void setInventoryGroup(String[] inventoryGroup)
	{
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId)
	{
		this.itemId = itemId;
	}

	public void setOpsEntityId(String opsEntityId)
	{
		this.opsEntityId = opsEntityId;
	}

	/**
	 * @param printOnHandCheckbox
	 *            the printOnHandCheckbox to set
	 */
	public void setPrintOnHandCheckbox(boolean printOnHandCheckbox)
	{
		this.printOnHandCheckbox = printOnHandCheckbox;
	}

	public void setRoom(String room)
	{
		this.room = room;
	}

	public void setSearchArgument(String searchArgument)
	{
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public void setSearchMode(String searchMode)
	{
		this.searchMode = searchMode;
	}

	public void setSortBy(String sortBy)
	{
		this.sortBy = sortBy;
	}

	
	public String getRadiobox() {
		return radiobox;
	}

	public void setRadiobox(String radiobox) {
		this.radiobox = radiobox;
	}

	public String[] getLotStatus() {
		return lotStatus;
	}

	public void setLotStatus(String[] lotStatus) {
		this.lotStatus = lotStatus;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	public void setSkipCountedCheckbox(boolean skipCountedCheckbox) {
		this.skipCountedCheckbox = skipCountedCheckbox;
	}
	
	public boolean isSkipCountedCheckbox() {
		return skipCountedCheckbox;
	}
	
	public boolean isCycleCount() {
		return countType.equals("Cycle Count");
	}

	public boolean isIncludePartNumbers() {
		return includePartNumbers;
	}

	public void setIncludePartNumbers(boolean includePartNumbers) {
		this.includePartNumbers = includePartNumbers;
	}
}
