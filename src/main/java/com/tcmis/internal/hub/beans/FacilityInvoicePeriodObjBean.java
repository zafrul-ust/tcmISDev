package com.tcmis.internal.hub.beans;

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
 * CLASSNAME: FacilityObjBean <br>
 * @version: 1.0, Feb 07, 2006 <br>
 * This is the facility collection for company_fac_invoice_date_ov
 *****************************************************************************/

public class FacilityInvoicePeriodObjBean
    extends BaseDataBean
    implements SQLData {

  private String facilityId;
  private String facilityName;
	private Collection invoicePeriodVar ;
	private Array invoicePeriodVarArray;
	private Collection aniversaryDateVar ;
	private Array aniversaryDateVarArray;
	private Collection inventoryGroupVar ;
	private Array inventoryGroupVarArray;
  private String sqlType = "FAC_INVOICE_PERIOD_OBJ";

  //constructor
  public FacilityInvoicePeriodObjBean() {
  }

  //setters
  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }
  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }
	public void setInvoicePeriodVar(Collection coll) {
		this.invoicePeriodVar = coll;
	}

	 public void setInvoicePeriodVarArray(Array invoicePeriodVarArray) {
		if (invoicePeriodVarArray != null) {
		 List list = null;
		 try {
			list = Arrays.asList( (Object[]) invoicePeriodVarArray.getArray());
		 }
		 catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("ERROR SETTING CHILDREN FacilityInvoicePeriodObjBean:" +
			 sqle.getMessage());
		 }
		 this.setInvoicePeriodVar(list);
		}
	}

 public void setAniversaryDateVar(Collection coll) {
		this.aniversaryDateVar = coll;
	}

	 public void setAniversaryDateVarArray(Array aniversaryDateVarArray) {
		if (aniversaryDateVarArray != null) {
		 List list = null;
		 try {
			list = Arrays.asList( (Object[]) aniversaryDateVarArray.getArray());
		 }
		 catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("ERROR SETTING CHILDREN FacilityInvoicePeriodObjBean:" +
			 sqle.getMessage());
		 }
		 this.setAniversaryDateVar(list);
		}
	}

		 public void setInventoryGroupVar(Collection coll) {
			this.inventoryGroupVar = coll;
		 }

		 public void setInventoryGroupVarArray(Array inventoryGroupVarArray) {
			if (inventoryGroupVarArray != null) {
			 List list = null;
			 try {
				list = Arrays.asList( (Object[]) inventoryGroupVarArray.getArray());
			 }
			 catch (SQLException sqle) {
				sqle.printStackTrace();
				System.out.println("ERROR SETTING CHILDREN FacilityInvoicePeriodObjBean:" +
				 sqle.getMessage());
			 }
			 this.setInventoryGroupVar(list);
			}
		 }

  //getters
  public String getFacilityId() {
    return this.facilityId;
  }
  public String getFacilityName() {
    return this.facilityName;
  }
	public Collection getInvoicePeriodVar() {
		return this.invoicePeriodVar;
	}
	public Array getInvoicePeriodVarArray() {
	 return invoicePeriodVarArray;
	}

	public Collection getAniversaryDateVar() {
	 return this.aniversaryDateVar;
	}

	public Array getAniversaryDateVarArray() {
	 return aniversaryDateVarArray;
	}

	public Collection getInventoryGroupVar() {
	 return this.inventoryGroupVar;
	}

  public Array getInventoryGroupVarArray() {
	 return inventoryGroupVarArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setFacilityId(stream.readString());
      this.setFacilityName(stream.readString());
			this.setInvoicePeriodVarArray(stream.readArray());
			this.setAniversaryDateVarArray(stream.readArray());
			this.setInventoryGroupVarArray(stream.readArray());
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
      stream.writeString(this.getFacilityId());
      stream.writeString(this.getFacilityName());
			stream.writeArray(this.getInvoicePeriodVarArray());
			stream.writeArray(this.getAniversaryDateVarArray());
			stream.writeArray(this.getInventoryGroupVarArray());
    }
    catch (SQLException e) {
      throw (SQLException) e;
    }
    catch (Exception e) {
      new IllegalStateException(getClass().getName() +
                                ".writeSQL method failed").
          initCause(e);
    }
  }
}

