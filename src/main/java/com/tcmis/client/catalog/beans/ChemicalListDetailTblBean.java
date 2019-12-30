package com.tcmis.client.catalog.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ChemicalListDetailTblBean <br>
 * @version: 1.0, May 17, 2011 <br>
 *****************************************************************************/


public class ChemicalListDetailTblBean extends BaseDataBean {

	private String approvalRole;
	private BigDecimal itemId;
	private BigDecimal lineItem;
	private String listId;
	private String listName;
	private String chemicalName;
	private BigDecimal percentage;


	//constructor
	public ChemicalListDetailTblBean() {
	}


	public String getApprovalRole() {
		return approvalRole;
	}


	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}


	public String getChemicalName() {
		return chemicalName;
	}


	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}


	public BigDecimal getItemId() {
		return itemId;
	}


	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	public BigDecimal getLineItem() {
		return lineItem;
	}


	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}


	public String getListId() {
		return listId;
	}


	public void setListId(String listId) {
		this.listId = listId;
	}


	public String getListName() {
		return listName;
	}


	public void setListName(String listName) {
		this.listName = listName;
	}


	public BigDecimal getPercentage() {
		return percentage;
	}


	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	
}