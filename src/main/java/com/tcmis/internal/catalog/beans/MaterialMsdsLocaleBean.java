package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import oracle.sql.STRUCT;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: MaterialMsdsLocaleViewBean <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/

public class MaterialMsdsLocaleBean extends BaseDataBean {

	private BigDecimal materialId;
	private Date revisionDate;
	private String content;
	private String onLine;
	private Date insertDate;
	private String localeCode;
	private String materialDesc;
	private String tradeName;
	
	//constructor
	public MaterialMsdsLocaleBean() {
	}

	//getters
	public BigDecimal getMaterialId() {
		return materialId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public String getContent() {
		return content;
	}

	public String getOnLine() {
		return onLine;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public String getTradeName() {
		return tradeName;
	}
		
	//setters
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	public void readObject(Object oo) throws SQLException{
		STRUCT b = (STRUCT)oo;
	   	Object[] attrs = b.getAttributes();
	   	int i = 0 ;
	   	   
	   	Date dd = null;
	   	
	   	this.setMaterialId((BigDecimal)attrs[i++]);
		dd = (Timestamp) attrs[i++];	if( dd != null ) this.setRevisionDate(new Date(dd.getTime()));
		this.setContent((String)attrs[i++]);
		this.setOnLine((String)attrs[i++]);
		dd = (Timestamp) attrs[i++];  if( dd != null ) this.setInsertDate(new Date(dd.getTime())); //this.setInsertDate(stream.readDate());
		this.setLocaleCode((String)attrs[i++]);
		this.setMaterialDesc((String)attrs[i++]);
		this.setTradeName((String)attrs[i++]);
	}
}
