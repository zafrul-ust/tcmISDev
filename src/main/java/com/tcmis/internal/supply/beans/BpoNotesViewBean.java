package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class BpoNotesViewBean extends BaseDataBean  
{

	private BigDecimal 	bpo;
	private BigDecimal 	userId;
	private Date 		noteDate;
	private String 		note;
	private String 		notesUserName;

	//constructor
	public BpoNotesViewBean() {
	}

	public void setBpo(BigDecimal bpo) {
		this.bpo = bpo;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}
	public void setNote(String text) {
		this.note = text;
	}
	public void setNotesUserName(String notesUserName) {
		this.notesUserName = notesUserName;
	}

	public BigDecimal getBpo() {
		return bpo;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public Date getNoteDate() {
		return noteDate;
	}
	public String getNote() {
		return note;
	}
	public String getNotesUserName() {
		return notesUserName;
	}
}