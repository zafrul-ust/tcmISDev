package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.internal.creditcard.beans.*;

/******************************************************************************
 * CLASSNAME: InvoiceFormatDetailViewBean <br>
 * @version: 1.0, Mar 29, 2005 <br>
 *****************************************************************************/

public class InvoiceFormatDetailViewBean
    extends BaseDataBean
    implements SQLData, CreditCardLineBean {

  //private BigDecimal invoice;
  private BigDecimal issueId;
  private BigDecimal quantity;
  private BigDecimal invoiceUnitPrice;
  private String poNumber;
  private String sqlType = "TCM_OPS.INVOICE_FORMAT_DETAI_OBJ";

  //constructor
  public InvoiceFormatDetailViewBean() {
  }

  //setters
//  public void setInvoice(BigDecimal invoice) {
//    this.invoice = invoice;
//  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  //getters
//  public BigDecimal getInvoice() {
//    return invoice;
//  }

  public BigDecimal getIssueId() {
    return issueId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      //this.setInvoice(stream.readBigDecimal());
      this.setIssueId(stream.readBigDecimal());
      this.setQuantity(stream.readBigDecimal());
      this.setInvoiceUnitPrice(stream.readBigDecimal());
      this.setPoNumber(stream.readString());
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
      //stream.writeBigDecimal(this.getInvoice());
      stream.writeBigDecimal(this.getIssueId());
      stream.writeBigDecimal(this.getQuantity());
      stream.writeBigDecimal(this.getInvoiceUnitPrice());
      stream.writeString(this.getPoNumber());
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