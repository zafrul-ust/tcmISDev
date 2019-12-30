package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ListBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/


public class ListBean extends BaseDataBean {

	private String listId;
	private String listName;
	private String listDescription;
	private String listChemicalChanged;
	private String hasThreshold;
	private String hasAmountThreshold;
	private String responsibleParty;
	private String maintenanceSchedule;

	//constructor
	public ListBean() {
	}

	//setters
	public void setListId(String listId) {
		this.listId = listId;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}

	public void setListDescription(String listDescription) {
		this.listDescription = listDescription;
	}

	//getters
	public String getListDescription() {
		return listDescription;
	}
	public String getListId() {
		return listId;
	}
	public String getListName() {
		return listName;
	}

	public String getListChemicalChanged() {
		return listChemicalChanged;
	}

	public void setListChemicalChanged(String listChemicalChanged) {
		this.listChemicalChanged = listChemicalChanged;
	}

	public String getHasThreshold() {
		return hasThreshold;
	}

	public void setHasThreshold(String hasThreshold) {
		this.hasThreshold = hasThreshold;
	}

	public String getHasAmountThreshold() {
		return hasAmountThreshold;
	}

	public void setHasAmountThreshold(String hasAmountThreshold) {
		this.hasAmountThreshold = hasAmountThreshold;
	}

	public String getResponsibleParty() {
		return responsibleParty;
	}

	public void setResponsibleParty(String responsibleParty) {
		this.responsibleParty = responsibleParty;
	}

	public String getMaintenanceSchedule() {
		return maintenanceSchedule;
	}

	public void setMaintenanceSchedule(String maintenanceSchedule) {
		this.maintenanceSchedule = maintenanceSchedule;
	}
}