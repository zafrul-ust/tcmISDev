package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;
import com.tcmis.common.util.StringHandler;

public class ReceivingStatusInputBean extends HubBaseInputBean {
	private String	assignedTo;
	private String	inventoryGroupList;
	private String	receiptId;
	private String	receivingStatus;
	private boolean	updated	= false;

	public ReceivingStatusInputBean() {
		super();
	}

	public ReceivingStatusInputBean(ActionForm inputForm) {
		super(inputForm);
	}

	public ReceivingStatusInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public String getInventoryGroupList() {
		return inventoryGroupList;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public String getReceivingStatus() {
		return receivingStatus;
	}

	public boolean hasAssignedTo() {
		return !StringHandler.isBlankString(assignedTo);
	}

	public boolean hasInventoryGroupList() {
		return !StringHandler.isBlankString(inventoryGroupList);
	}

	public boolean hasReceivingStatus() {
		return !StringHandler.isBlankString(receivingStatus);
	}
	
	public boolean isAssignedToUnassigned() {
		return "-1".equals(assignedTo);
	}

	public boolean isUpdated() {
		return updated;
	}

	public boolean isUserSearch() {
		return "userSearch".equals(getuAction());
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("hub");
		addHiddenFormField("receivingStatus");
		addHiddenFormField("inventoryGroupList");
		addHiddenFormField("assignedTo");
	}

	public void setInventoryGroupList(String inventoryGroupList) {
		this.inventoryGroupList = inventoryGroupList;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public void setReceivingStatus(String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
}
