package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoNotesViewBean <br>
 * @version: 1.0, Nov 19, 2007 <br>
 *****************************************************************************/


public class PoNotesViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal userId;
	private Date noteDate;
	private String note;
	private String notesUserName;


	//constructor
	public PoNotesViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setNotesUserName(String notesUserName) {
		this.notesUserName = notesUserName;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
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