package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.internal.creditcard.beans.*;

/******************************************************************************
 * CLASSNAME: InvoiceFormatSeagateOvBean <br>
 * @version: 1.0, Mar 28, 2005 <br>
 *****************************************************************************/

public class InvoiceFormatVerisignOvBean
    extends BaseDataBean
    implements SQLData, CreditCardBean {

  private BigDecimal invoice;
  private BigDecimal invoicePeriod;
  private String zip;
  private BigDecimal invoiceAmount;
  private BigDecimal creditCardNumber;
  private Date creditCardExpirationDate;
  private String txref;
  private String resultCode;
  private String companyId;
  private String invoiceGroup;
  private Collection invoiceLines;
  private Array detailArray;
  private String sqlType = "TCM_OPS.INVOICE_FORMAT_VERISIGN_OBJ";

  //constructor
  public InvoiceFormatVerisignOvBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setCreditCardNumber(BigDecimal creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
    this.creditCardExpirationDate = creditCardExpirationDate;
  }

  public void setTxref(String txref) {
    this.txref = txref;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public void setCompanyId(String s) {
    this.companyId = s;
  }

  public void setInvoiceGroup(String s) {
    this.invoiceGroup = s;
  }

  public void setInvoiceLines(Collection coll) {
    this.invoiceLines = coll;
  }

  public void setDetailArray(Array detailArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) detailArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setInvoiceLines(list);
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getZip() {
    return zip;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public BigDecimal getCreditCardNumber() {
    return creditCardNumber;
  }

  public Date getCreditCardExpirationDate() {
    return creditCardExpirationDate;
  }

  public String getTxref() {
    return txref;
  }

  public String getResultCode() {
    return resultCode;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public Collection getInvoiceLines() {
    return this.invoiceLines;
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
      this.setCompanyId(stream.readString());
      this.setInvoiceGroup(stream.readString());
      this.setInvoice(stream.readBigDecimal());
      this.setInvoicePeriod(stream.readBigDecimal());
      this.setZip(stream.readString());
      this.setInvoiceAmount(stream.readBigDecimal());
      this.setCreditCardNumber(stream.readBigDecimal());
      this.setCreditCardExpirationDate(new Date(stream.readDate().getTime()));
      this.setTxref(stream.readString());
      this.setResultCode(stream.readString());
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
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getInvoiceGroup());
      stream.writeBigDecimal(this.getInvoice());
      stream.writeBigDecimal(this.getInvoicePeriod());
      stream.writeString(this.getZip());
      stream.writeBigDecimal(this.getInvoiceAmount());
      stream.writeBigDecimal(this.getCreditCardNumber());
      stream.writeDate(new java.sql.Date(this.getCreditCardExpirationDate().
                                         getTime()));
      stream.writeString(this.getTxref());
      stream.writeString(this.getResultCode());
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