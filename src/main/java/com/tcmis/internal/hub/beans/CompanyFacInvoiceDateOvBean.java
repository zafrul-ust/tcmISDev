package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CompanyFacInvoiceDateOvBean <br>
 * @version: 1.0, Feb 7, 2006 <br>
 *****************************************************************************/

public class CompanyFacInvoiceDateOvBean extends BaseDataBean implements SQLData {
	private BigDecimal personnelId;
	private String companyId;
	private String companyName;
	private Collection facInvoicePeriodVar;
	private Array facInvoicePeriodVarArray;
	private String sqlType = "COMP_FAC_INVOICE_PERIOD_OBJ";

	//constructor
	public CompanyFacInvoiceDateOvBean() {
	}

	//setters
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setFacInvoicePeriodVar(Collection coll) {
		this.facInvoicePeriodVar = coll;
	}
	public void setFacInvoicePeriodVarArray(Array facInvoicePeriodVarArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) facInvoicePeriodVarArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN CompanyFacInvoiceDateOvBean :" + sqle.getMessage());
		}
		this.setFacInvoicePeriodVar(list);
	}


	//getters
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public Collection getFacInvoicePeriodVar() {
		return this.facInvoicePeriodVar;
	}
	public Array getFacInvoicePeriodVarArray() {
		return facInvoicePeriodVarArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPersonnelId(stream.readBigDecimal());
			this.setCompanyId(stream.readString());
			this.setCompanyName(stream.readString());
			this.setFacInvoicePeriodVarArray(stream.readArray());
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
			stream.writeBigDecimal(this.getPersonnelId());
			stream.writeString(this.getCompanyId());
			stream.writeString(this.getCompanyName());
			stream.writeArray(this.getFacInvoicePeriodVarArray());
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