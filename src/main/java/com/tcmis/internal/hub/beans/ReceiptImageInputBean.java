package com.tcmis.internal.hub.beans;

import java.io.File;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ReceiptDocumentViewBean <br>
 * 
 * @version: 1.0, Oct 19, 2005 <br>
 *****************************************************************************/

public class ReceiptImageInputBean extends BaseInputBean {

	private String	fileName;
	private String	itemId;
	private String	itemImageId;
	private String	receiptId;
	private String	uAction;

	// constructor
	public ReceiptImageInputBean() {
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getItemId() {
		return itemId;
	}

	public String getItemImageId() {
		return itemImageId;
	}

	public String getReceiptId() {
		return receiptId;
	}
	
	public boolean hasItemImageId () {
		return !StringHandler.isBlankString(itemImageId);
	}

	public void setFileName(String fileName) {
		if (fileName != null && this.doTrim) {
			this.fileName = fileName.trim();
		}
		else {
			this.fileName = fileName;
		}
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setItemImageId(String itemImageId) {
		this.itemImageId = itemImageId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

}