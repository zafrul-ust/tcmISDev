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

/******************************************************************************
 * CLASSNAME: InvoicePrintOvBean <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoicePrintOvBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal invoice;
  private String companyId;
  private String facilityId;
  private String accountSysId;
  private BigDecimal invoiceAmount;
  private Date invoiceDate;
  private Date transactionDate;
  private BigDecimal invoicePeriod;
  private String accountSysLabel;
  private String locationLabel;
  private String invoiceGroup;
  private String poNumber;
  private String commodity;
  private BigDecimal originalInvoice;
  private BigDecimal creditInvoice;
  private BigDecimal creditCardNumber;
  private Date creditCardExpirationDate;
  private Collection invoiceLines;
  private Array invoiceLinesArray;
  private String sqlType = "invoice_print_obj";

  //constructor
  public InvoicePrintOvBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setAccountSysId(String accountSysId) {
    this.accountSysId = accountSysId;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setAccountSysLabel(String accountSysLabel) {
    this.accountSysLabel = accountSysLabel;
  }

  public void setLocationLabel(String locationLabel) {
    this.locationLabel = locationLabel;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setCommodity(String commodity) {
    this.commodity = commodity;
  }

  public void setOriginalInvoice(BigDecimal originalInvoice) {
    this.originalInvoice = originalInvoice;
  }

  public void setCreditInvoice(BigDecimal creditInvoice) {
    this.creditInvoice = creditInvoice;
  }

  public void setCreditCardNumber(BigDecimal creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
    this.creditCardExpirationDate = creditCardExpirationDate;
  }

  public void setInvoiceLines(Collection coll) {
    this.invoiceLines = coll;
  }

  public void setInvoiceLinesArray(Array invoiceLinesArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) invoiceLinesArray.getArray());
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

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getAccountSysId() {
    return accountSysId;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getAccountSysLabel() {
    return accountSysLabel;
  }

  public String getLocationLabel() {
    return locationLabel;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getCommodity() {
    return commodity;
  }

  public BigDecimal getOriginalInvoice() {
    return originalInvoice;
  }

  public BigDecimal getCreditInvoice() {
    return creditInvoice;
  }

  public BigDecimal getCreditCardNumber() {
    return creditCardNumber;
  }

  public Date getCreditCardExpirationDate() {
    return creditCardExpirationDate;
  }

  public Collection getInvoiceLines() {
    return this.invoiceLines;
  }

  public Array getInvoiceLinesArray() {
    return invoiceLinesArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInvoice(stream.readBigDecimal());
      this.setCompanyId(stream.readString());
      this.setFacilityId(stream.readString());
      this.setAccountSysId(stream.readString());
      this.setInvoiceAmount(stream.readBigDecimal());
      this.setInvoiceDate(new java.util.Date(stream.readDate().getTime()));
      this.setTransactionDate(new java.util.Date(stream.readDate().getTime()));
      this.setInvoicePeriod(stream.readBigDecimal());
      this.setAccountSysLabel(stream.readString());
      this.setLocationLabel(stream.readString());
      this.setInvoiceGroup(stream.readString());
      this.setPoNumber(stream.readString());
      this.setCommodity(stream.readString());
      this.setOriginalInvoice(stream.readBigDecimal());
      this.setCreditInvoice(stream.readBigDecimal());
      this.setCreditCardNumber(stream.readBigDecimal());
      this.setCreditCardExpirationDate(new java.util.Date(stream.readDate().
          getTime()));
      this.setInvoiceLinesArray(stream.readArray());
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
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getFacilityId());
      stream.writeString(this.getAccountSysId());
      stream.writeBigDecimal(this.getInvoiceAmount());
      stream.writeDate(new java.sql.Date(this.getInvoiceDate().getTime()));
      stream.writeDate(new java.sql.Date(this.getTransactionDate().getTime()));
      stream.writeBigDecimal(this.getInvoicePeriod());
      stream.writeString(this.getAccountSysLabel());
      stream.writeString(this.getLocationLabel());
      stream.writeString(this.getInvoiceGroup());
      stream.writeString(this.getPoNumber());
      stream.writeString(this.getCommodity());
      stream.writeBigDecimal(this.getOriginalInvoice());
      stream.writeBigDecimal(this.getCreditInvoice());
      stream.writeBigDecimal(this.getCreditCardNumber());
      stream.writeDate(new java.sql.Date(this.getCreditCardExpirationDate().
                                         getTime()));
      stream.writeArray(this.getInvoiceLinesArray());
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