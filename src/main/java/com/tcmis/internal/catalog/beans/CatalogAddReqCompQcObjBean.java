package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.Date;

import oracle.sql.STRUCT;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CatalogAddReqCompQCObjBean <br>
 * @version: 1.0, Sep 14, 2010 <br>
 *****************************************************************************/

@SuppressWarnings("serial")
public class CatalogAddReqCompQcObjBean extends BaseDataBean implements SQLData {
	public static Log log = LogFactory.getLog(CatalogAddReqCompQcObjBean.class);
	public final static String SQLTypeName = "CATALOG_ADD_REQ_COMP_QC_OBJ";
	private String casNumber;
	private String chemicalId;
	private String dataSource;
	private String hazardous;
	private Date insertDate;
	private BigDecimal materialId;
	private String msdsChemicalName;
	private BigDecimal percent;
	private String percentAsCas;
	private BigDecimal percentCalc;
	private BigDecimal percentLower;
	private BigDecimal percentLowerCalc;
	private BigDecimal percentUpper;
	private BigDecimal percentUpperCalc;
	private String remark;
	private Date revisionDate;
	private boolean trace;
	private boolean tradeSecret;
	private BigDecimal sdsSectionId;

	//constructor
	public CatalogAddReqCompQcObjBean() {
	}

	public String getCasNumber() {
		return casNumber;
	}

	public String getChemicalId() {
		return chemicalId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getHazardous() {
		return hazardous;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	//getters
	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMsdsChemicalName() {
		return msdsChemicalName;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public String getPercentAsCas() {
		return percentAsCas;
	}

	public BigDecimal getPercentCalc() {
		return percentCalc;
	}

	public BigDecimal getPercentLower() {
		return percentLower;
	}

	public BigDecimal getPercentLowerCalc() {
		return percentLowerCalc;
	}

	public BigDecimal getPercentUpper() {
		return percentUpper;
	}

	public BigDecimal getPercentUpperCalc() {
		return percentUpperCalc;
	}

	public String getRemark() {
		return remark;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public String getSQLTypeName() {
		return SQLTypeName;
	}

	public boolean isTrace() {
		return trace;
	}

	public boolean isTradeSecret() {
		return tradeSecret;
	}

	public void readSQL(SQLInput stream, String type) throws SQLException {
		try {
			setMaterialId(stream.readBigDecimal());
			setRevisionDate(new Date(stream.readDate().getTime()));
			setPercent(stream.readBigDecimal());
			setChemicalId(stream.readString());
			setPercentAsCas(stream.readString());
			setPercentLower(stream.readBigDecimal());
			setPercentUpper(stream.readBigDecimal());
			setHazardous(stream.readString());
			setCasNumber(stream.readString());
			setTradeSecret("Y".equals(stream.readString()));
			setRemark(stream.readString());
			setMsdsChemicalName(stream.readString());
			setInsertDate(stream.readDate());
			setPercentLowerCalc(stream.readBigDecimal());
			setPercentCalc(stream.readBigDecimal());
			setPercentUpperCalc(stream.readBigDecimal());
			setDataSource(stream.readString());
			setTrace("Y".equals(stream.readString()));
			setSdsSectionId(stream.readBigDecimal());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			log.error(getClass().getName() + ".readSQL method failed", e);
		}
	}

	public void readObject(Object oo) throws SQLException{
		STRUCT b = (STRUCT)oo;
	   	Object[] attrs = b.getAttributes();
	   	int i = 0 ;
	   	   
	   	Date dd = null;
	   	
	   	this.setMaterialId((BigDecimal)attrs[i++]);
		dd = (Timestamp) attrs[i++];	if( dd != null ) this.setRevisionDate(new Date(dd.getTime()));
		this.setPercent((BigDecimal)attrs[i++]);
		this.setChemicalId((String)attrs[i++]);
		this.setPercentAsCas((String)attrs[i++]);
		this.setPercentLower((BigDecimal)attrs[i++]);
		this.setPercentUpper((BigDecimal)attrs[i++]);
		this.setHazardous((String)attrs[i++]);
		this.setCasNumber((String)attrs[i++]);
		this.setTradeSecret("Y".equals((String)attrs[i++]));
		this.setRemark((String)attrs[i++]);
		this.setMsdsChemicalName((String)attrs[i++]);
		dd = (Timestamp) attrs[i++];  if( dd != null ) this.setInsertDate(new Date(dd.getTime())); //this.setInsertDate(stream.readDate());
		this.setPercentLowerCalc((BigDecimal)attrs[i++]);
		this.setPercentCalc((BigDecimal)attrs[i++]);
		this.setPercentUpperCalc((BigDecimal)attrs[i++]);
		this.setDataSource((String)attrs[i++]);
		this.setTrace("Y".equals((String)attrs[i++]));
		this.setSdsSectionId((BigDecimal)attrs[i++]);
	}
	
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
		if (!StringHandler.isBlankString(casNumber) && casNumber.startsWith("TS")) {
			setTradeSecret(true);
		}
	}

	public void setChemicalId(String chemicalId) {
		this.chemicalId = chemicalId;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	//setters
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMsdsChemicalName(String msdsChemicalName) {
		this.msdsChemicalName = msdsChemicalName;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public void setPercentAsCas(String percentAsCas) {
		this.percentAsCas = percentAsCas;
	}

	public void setPercentCalc(BigDecimal percentCalc) {
		this.percentCalc = percentCalc;
	}

	public void setPercentLower(BigDecimal percentLower) {
		this.percentLower = percentLower;
	}

	public void setPercentLowerCalc(BigDecimal percentLowerCalc) {
		this.percentLowerCalc = percentLowerCalc;
	}

	public void setPercentUpper(BigDecimal percentUpper) {
		this.percentUpper = percentUpper;
	}

	public void setPercentUpperCalc(BigDecimal percentUpperCalc) {
		this.percentUpperCalc = percentUpperCalc;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public void setTrace(boolean trace) {
		this.trace = trace;
	}

	public void setTradeSecret(boolean tradeSecret) {
		this.tradeSecret = tradeSecret;
	}

	public BigDecimal getSdsSectionId() {
		return sdsSectionId;
	}

	public void setSdsSectionId(BigDecimal sdsSectionId) {
		this.sdsSectionId = sdsSectionId;
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeBigDecimal(getMaterialId());
			stream.writeDate(new java.sql.Date(getRevisionDate().getTime()));
			stream.writeBigDecimal(getPercent());
			stream.writeString(getChemicalId());
			stream.writeString(getPercentAsCas());
			stream.writeBigDecimal(getPercentLower());
			stream.writeBigDecimal(getPercentUpper());
			stream.writeString(getHazardous());
			stream.writeString(getCasNumber());
			stream.writeString(isTradeSecret() ? "Y" : "");
			stream.writeString(getRemark());
			stream.writeString(getMsdsChemicalName());
			stream.writeDate(new java.sql.Date(getInsertDate().getTime()));
			stream.writeBigDecimal(getPercentLowerCalc());
			stream.writeBigDecimal(getPercentCalc());
			stream.writeBigDecimal(getPercentUpperCalc());
			stream.writeString(getDataSource());
			stream.writeString(isTrace() ? "Y" : "");
			stream.writeBigDecimal(getSdsSectionId());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
		}
	}
}