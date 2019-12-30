package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TransferRequestReportViewBean <br>
 * @version: 1.0, Jun 17, 2009 <br>
 *****************************************************************************/


public class TransfersInputBean extends BaseDataBean {
	
	private String searchField;
	private String searchMode;
	private String searchArgument;
	private String status;
	private Date fromDate;
	private Date toDate;
	private String sourceEntities;
	private String sourceOpsEntityId;
	private String sourceHub;
	private String sourceHubName;
	private String sourceInventoryGroup;
	private String destinationEntities;
	private String destinationOpsEntityId;
	private String destinationHub;
	private String destinationHubName;
	private String destinationInventoryGroup;

	//constructor
	public TransfersInputBean() {
	}

	public String getDestinationHub() {
		return destinationHub;
	}

	public void setDestinationHub(String destinationHub) {
		this.destinationHub = destinationHub;
	}

	public String getDestinationHubName() {
		return destinationHubName;
	}

	public void setDestinationHubName(String destinationHubName) {
		this.destinationHubName = destinationHubName;
	}

	public String getDestinationInventoryGroup() {
		return destinationInventoryGroup;
	}

	public void setDestinationInventoryGroup(String destinationInventoryGroup) {
		this.destinationInventoryGroup = destinationInventoryGroup;
	}

	public String getDestinationOpsEntityId() {
		return destinationOpsEntityId;
	}

	public void setDestinationOpsEntityId(String destinationOpsEntityId) {
		this.destinationOpsEntityId = destinationOpsEntityId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getSourceHub() {
		return sourceHub;
	}

	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public String getSourceHubName() {
		return sourceHubName;
	}

	public void setSourceHubName(String sourceHubName) {
		this.sourceHubName = sourceHubName;
	}

	public String getSourceInventoryGroup() {
		return sourceInventoryGroup;
	}

	public void setSourceInventoryGroup(String sourceInventoryGroup) {
		this.sourceInventoryGroup = sourceInventoryGroup;
	}

	public String getSourceOpsEntityId() {
		return sourceOpsEntityId;
	}

	public void setSourceOpsEntityId(String sourceOpsEntityId) {
		this.sourceOpsEntityId = sourceOpsEntityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	public String getDestinationEntities() {
		return destinationEntities;
	}

	public void setDestinationEntities(String destinationEntities) {
		this.destinationEntities = destinationEntities;
	}

	public String getSourceEntities() {
		return sourceEntities;
	}

	public void setSourceEntities(String sourceEntities) {
		this.sourceEntities = sourceEntities;
	}


}