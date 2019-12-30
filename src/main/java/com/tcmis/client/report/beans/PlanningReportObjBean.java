package com.tcmis.client.report.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierLocationOvBean <br>
 * @version: 1.0, Oct 24, 2007 <br>
 *****************************************************************************/


public class PlanningReportObjBean extends BaseDataBean implements SQLData {

	  private String county;
	  private String reportId;
	  private String reportName;
	  private BigDecimal solidThreshold;
	  private String solidThresholdUnit;
	  private BigDecimal liquidThreshold;
	  private String liquidThresholdUnit;
	  private BigDecimal gasThreshold;
	  private String gasThresholdUnit;
	  private BigDecimal pureThreshold;
	  private Collection areaList;
	  private Array areaListArray;
	  private String sqlType = "CUSTOMER.PLANNING_REPORT_OBJ";


	//constructor
	public PlanningReportObjBean() {
	}
	
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public BigDecimal getSolidThreshold() {
		return solidThreshold;
	}

	public void setSolidThreshold(BigDecimal solidThreshold) {
		this.solidThreshold = solidThreshold;
	}

	public String getSolidThresholdUnit() {
		return solidThresholdUnit;
	}

	public void setSolidThresholdUnit(String solidThresholdUnit) {
		this.solidThresholdUnit = solidThresholdUnit;
	}

	public BigDecimal getLiquidThreshold() {
		return liquidThreshold;
	}

	public void setLiquidThreshold(BigDecimal liquidThreshold) {
		this.liquidThreshold = liquidThreshold;
	}

	public String getLiquidThresholdUnit() {
		return liquidThresholdUnit;
	}

	public void setLiquidThresholdUnit(String liquidThresholdUnit) {
		this.liquidThresholdUnit = liquidThresholdUnit;
	}

	public BigDecimal getGasThreshold() {
		return gasThreshold;
	}

	public void setGasThreshold(BigDecimal gasThreshold) {
		this.gasThreshold = gasThreshold;
	}

	public String getGasThresholdUnit() {
		return gasThresholdUnit;
	}

	public void setGasThresholdUnit(String gasThresholdUnit) {
		this.gasThresholdUnit = gasThresholdUnit;
	}

	public BigDecimal getPureThreshold() {
		return pureThreshold;
	}

	public void setPureThreshold(BigDecimal pureThreshold) {
		this.pureThreshold = pureThreshold;
	}

	public Collection getAreaList() {
		return this.areaList;
	}

	public void setAreaList(Collection coll) {
		this.areaList = coll;
	}

	public Array getAreaListArray() {
		return areaListArray;
	}

	
	public void setAreaListArray(Array areaListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) areaListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setAreaList(list);
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setReportId(stream.readString());
			this.setReportName(stream.readString());
			this.setCounty(stream.readString());
			this.setSolidThreshold(stream.readBigDecimal());
			this.setSolidThresholdUnit(stream.readString());
			this.setLiquidThreshold(stream.readBigDecimal());
			this.setLiquidThresholdUnit(stream.readString());
			this.setGasThreshold(stream.readBigDecimal());
			this.setGasThresholdUnit(stream.readString());
			this.setPureThreshold(stream.readBigDecimal());
			this.setAreaListArray(stream.readArray());
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
			stream.writeString(this.getReportId());
			stream.writeString(this.getReportName());
			stream.writeString(this.getCounty());
			stream.writeBigDecimal(this.getSolidThreshold());
			stream.writeString(this.getSolidThresholdUnit());
			stream.writeBigDecimal(this.getLiquidThreshold());
			stream.writeString(this.getLiquidThresholdUnit());
			stream.writeBigDecimal(this.getGasThreshold());
			stream.writeString(this.getGasThresholdUnit());
			stream.writeBigDecimal(this.getPureThreshold());
			stream.writeArray(this.getAreaListArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}
}