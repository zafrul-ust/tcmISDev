package com.tcmis.internal.invoice.beans;

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
 * CLASSNAME: InvoiceViewL3OvBean <br>
 * @version: 1.0, May 16, 2005 <br>
 *****************************************************************************/

public class InvoiceViewL3OvBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal invoice;
  private String accountNumber;
  private BigDecimal invoicePeriod;
  private Collection detail;
  private Array detailArray;
  private String sqlType = "INVOICE_VIEW_L3_OBJ";

  //constructor
  public InvoiceViewL3OvBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setDetail(Collection coll) {
    this.detail = coll;
  }

  public void setDetailArray(Array detailArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) detailArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setDetail(list);
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public Collection getDetail() {
    return this.detail;
  }

  public Array getDetailArray() {
    return detailArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInvoice(stream.readBigDecimal());
      this.setAccountNumber(stream.readString());
      this.setInvoicePeriod(stream.readBigDecimal());
      this.setDetailArray(stream.readArray());
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
      stream.writeBigDecimal(this.getInvoice());
      stream.writeString(this.getAccountNumber());
      stream.writeBigDecimal(this.getInvoicePeriod());
      stream.writeArray(this.getDetailArray());
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