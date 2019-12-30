package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SalesQuoteLineObjBean <br>
 * @version: 1.0, Mar 3, 2010 <br>
 *****************************************************************************/


public class SalesQuoteLineObjBean extends BaseDataBean implements SQLData {

	private BigDecimal prNumber;
	private String lineItem;
	private String rcptQualityHoldShelfLife;
	private String rcptQualityHoldSpec;
	private Date rcptQualHoldSlSetDate;
	private Date rcptQualHoldSpecSetDate;
	private BigDecimal rcptQualityHoldReleaser;
	private Date rcptQualityHoldReleaseDate;
	private String rcptQualityHoldNote;
	private Date promiseDate;
	private Date requiredDatetime;
	private BigDecimal orderQuantity;
	private String partDescription;
	private BigDecimal itemId;
	private String expertReviewHold;
	private String expertReviewDesc;
	private String forceHold;
	private String forceHoldComment;
	private String sqlType = "SALES_QUOTE_LINE_OBJ";


	//constructor
	public SalesQuoteLineObjBean() {
	}

	//setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setRcptQualityHoldShelfLife(String rcptQualityHoldShelfLife) {
		this.rcptQualityHoldShelfLife = rcptQualityHoldShelfLife;
	}
	public void setRcptQualityHoldSpec(String rcptQualityHoldSpec) {
		this.rcptQualityHoldSpec = rcptQualityHoldSpec;
	}
	public void setRcptQualHoldSlSetDate(Date rcptQualHoldSlSetDate) {
		this.rcptQualHoldSlSetDate = rcptQualHoldSlSetDate;
	}
	public void setRcptQualHoldSpecSetDate(Date rcptQualHoldSpecSetDate) {
		this.rcptQualHoldSpecSetDate = rcptQualHoldSpecSetDate;
	}
	public void setRcptQualityHoldReleaser(BigDecimal rcptQualityHoldReleaser) {
		this.rcptQualityHoldReleaser = rcptQualityHoldReleaser;
	}
	public void setRcptQualityHoldReleaseDate(Date rcptQualityHoldReleaseDate) {
		this.rcptQualityHoldReleaseDate = rcptQualityHoldReleaseDate;
	}
	public void setRcptQualityHoldNote(String rcptQualityHoldNote) {
		this.rcptQualityHoldNote = rcptQualityHoldNote;
	}


	//getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getRcptQualityHoldShelfLife() {
		return rcptQualityHoldShelfLife;
	}
	public String getRcptQualityHoldSpec() {
		return rcptQualityHoldSpec;
	}
	public Date getRcptQualHoldSlSetDate() {
		return rcptQualHoldSlSetDate;
	}
	public Date getRcptQualHoldSpecSetDate() {
		return rcptQualHoldSpecSetDate;
	}
	public BigDecimal getRcptQualityHoldReleaser() {
		return rcptQualityHoldReleaser;
	}
	public Date getRcptQualityHoldReleaseDate() {
		return rcptQualityHoldReleaseDate;
	}
	public String getRcptQualityHoldNote() {
		return rcptQualityHoldNote;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setPrNumber(stream.readBigDecimal());
			this.setLineItem(stream.readString());
			this.setRcptQualityHoldShelfLife(stream.readString());
			this.setRcptQualityHoldSpec(stream.readString());
			 dd = stream.readDate();			if( dd != null ) this.setRcptQualHoldSlSetDate(new Date(dd.getTime()));
			 dd = stream.readDate();			if( dd != null ) this.setRcptQualHoldSpecSetDate(new Date(dd.getTime()));
			this.setRcptQualityHoldReleaser(stream.readBigDecimal());
			 dd = stream.readDate();			if( dd != null ) this.setRcptQualityHoldReleaseDate(new Date(dd.getTime()));
			this.setRcptQualityHoldNote(stream.readString());
			 dd = stream.readDate();			if( dd != null ) this.setPromiseDate(new Date(dd.getTime()));
			 dd = stream.readDate();			if( dd != null ) this.setRequiredDatetime(new Date(dd.getTime()));
			 this.setOrderQuantity(stream.readBigDecimal());
			 this.setPartDescription(stream.readString());
			 this.setItemId(stream.readBigDecimal());
			 this.setExpertReviewHold(stream.readString());
			 this.setExpertReviewDesc(stream.readString());
			 this.setForceHold(stream.readString());
			 this.setForceHoldComment(stream.readString());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").
			initCause(e);
		}
	}
	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeBigDecimal(this.getPrNumber());
			stream.writeString(this.getLineItem());
			stream.writeString(this.getRcptQualityHoldShelfLife());
			stream.writeString(this.getRcptQualityHoldSpec());
			stream.writeDate(new java.sql.Date(this.getRcptQualHoldSlSetDate().getTime()));
			stream.writeDate(new java.sql.Date(this.getRcptQualHoldSpecSetDate().getTime()));
			stream.writeBigDecimal(this.getRcptQualityHoldReleaser());
			stream.writeDate(new java.sql.Date(this.getRcptQualityHoldReleaseDate().getTime()));
			stream.writeString(this.getRcptQualityHoldNote());
			stream.writeDate(new java.sql.Date(this.getPromiseDate().getTime()));
			stream.writeDate(new java.sql.Date(this.getRequiredDatetime().getTime()));
			stream.writeBigDecimal(this.getOrderQuantity());
			stream.writeString(this.getPartDescription());
			stream.writeBigDecimal(this.getItemId());
			stream.writeString(this.getExpertReviewHold());
			stream.writeString(this.getExpertReviewDesc());
			stream.writeString(this.getForceHold());
			stream.writeString(this.getForceHoldComment());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public Date getPromiseDate() {
		return promiseDate;
	}

	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}

	public Date getRequiredDatetime() {
		return requiredDatetime;
	}

	public void setRequiredDatetime(Date requiredDatetime) {
		this.requiredDatetime = requiredDatetime;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getExpertReviewDesc() {
		return expertReviewDesc;
	}

	public void setExpertReviewDesc(String expertReviewDesc) {
		this.expertReviewDesc = expertReviewDesc;
	}

	public String getExpertReviewHold() {
		return expertReviewHold;
	}

	public void setExpertReviewHold(String expertReviewHold) {
		this.expertReviewHold = expertReviewHold;
	}

	public String getForceHold() {
		return forceHold;
	}

	public void setForceHold(String forceHold) {
		this.forceHold = forceHold;
	}

	public String getForceHoldComment() {
		return forceHoldComment;
	}

	public void setForceHoldComment(String forceHoldComment) {
		this.forceHoldComment = forceHoldComment;
	}
}