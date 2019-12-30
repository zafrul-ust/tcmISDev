package com.tcmis.client.aerojet.beans;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class LineItemBean extends BaseDataBean {

	private QuantityBean lineOrderedQuantityBean;
	private OperAmtBean lineItemAmtBean; 
	private String poLineNumber;
	private Collection noteToSupplierCollection = new Vector();	
	private String itemDescription;
	private String itemNumber;
	private String supplierItemNumber;
	private String requestorName;
	private String category;
	private String contractNumber;
	private String lineOrderType;
	private Collection scheduleLineItemBeanCollection = new Vector();	
	
	/*Combine all notes */
	public String getFullNotetoSupplier() {
		Iterator iterate = this.noteToSupplierCollection.iterator();
		StringBuilder sb = new StringBuilder();
		while (iterate.hasNext()) {
			NoteToSupplier note = (NoteToSupplier) iterate.next();		
			String temp = note.getNote();
			if(!StringHandler.isBlankString(temp)) {
				sb.append(temp).append(";");
			}
		}
		return sb.toString();
	}
	
	/*Combine only notes tag with Quality as the index*/
	public String getFullQualityNotetoSupplier() {
		Iterator iterate = this.noteToSupplierCollection.iterator();
		StringBuilder sb = new StringBuilder();
		while (iterate.hasNext()) {
			NoteToSupplier note = (NoteToSupplier) iterate.next();
			if (note.getIndex() != null && note.getIndex().contains("QUALITY")) {	
				if(!StringHandler.isBlankString(note.getNote())) {
					sb.append(note.getNote()).append(";");
				}
			}
		}
		return sb.toString();
	}
	
	public void addNoteToSupplier (NoteToSupplier noteToSupplier) {
		this.noteToSupplierCollection.add(noteToSupplier);
	}

	public void addScheduleLineItemBean (ScheduleLineItemBean scheduleLineItemBean) {
		this.scheduleLineItemBeanCollection.add(scheduleLineItemBean);
	}

	public OperAmtBean getLineItemAmtBean() {
		return lineItemAmtBean;
	}

	public void setLineItemAmtBean(OperAmtBean lineItemAmtBean) {
		this.lineItemAmtBean = lineItemAmtBean;
	}
	public String getPoLineNumber() {
		return poLineNumber;
	}
	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}	
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getSupplierItemNumber() {
		return supplierItemNumber;
	}
	public void setSupplierItemNumber(String supplierItemNumber) {
		this.supplierItemNumber = supplierItemNumber;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getLineOrderType() {
		return lineOrderType;
	}
	public void setLineOrderType(String lineOrderType) {
		this.lineOrderType = lineOrderType;
	}

	public Collection getScheduleLineItemBeanCollection() {
		return scheduleLineItemBeanCollection;
	}

	public void setScheduleLineItemBeanCollection(
			Collection scheduleLineItemBeanCollection) {
		this.scheduleLineItemBeanCollection = scheduleLineItemBeanCollection;
	}

	public Collection getNoteToSupplierCollection() {
		return noteToSupplierCollection;
	}

	public void setNoteToSupplierCollection(Collection noteToSupplierCollection) {
		this.noteToSupplierCollection = noteToSupplierCollection;
	}

	public QuantityBean getLineOrderedQuantityBean() {
		return lineOrderedQuantityBean;
	}

	public void setLineOrderedQuantityBean(QuantityBean lineOrderedQuantityBean) {
		this.lineOrderedQuantityBean = lineOrderedQuantityBean;
	}

	
}
