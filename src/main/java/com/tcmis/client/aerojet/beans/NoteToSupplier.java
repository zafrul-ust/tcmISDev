package com.tcmis.client.aerojet.beans;

import com.tcmis.common.framework.BaseDataBean;

public class NoteToSupplier extends BaseDataBean {

	private String index;
	private String note;

	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
