package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewSyracuseDetailBean <br>
 * @version: 1.0, Mar 16, 2005 <br>
 *****************************************************************************/

public class InvoiceViewSyracuseDetailBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private String partNumber;
  private BigDecimal quantity;
  private BigDecimal extPrice;
  private BigDecimal netLineAmount;
  private BigDecimal additionalCharges;
  private BigDecimal serviceFee;
  private String mrNumber;
  private Date dateDelivered;
  private BigDecimal invoiceUnitPrice;
  private String accountNumber;
  private BigDecimal salesTax;
  private BigDecimal percentSplitCharge;
  private String partDescription;
  private BigDecimal lineCount;
  private String sqlType = "INVOICE_VIEW_SYRACUSE_DET_OBJ";

  //constructor
  public InvoiceViewSyracuseDetailBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setExtPrice(BigDecimal extPrice) {
    this.extPrice = extPrice;
  }

  public void setNetLineAmount(BigDecimal netLineAmount) {
    this.netLineAmount = netLineAmount;
  }

  public void setAdditionalCharges(BigDecimal additionalCharges) {
    this.additionalCharges = additionalCharges;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setMrNumber(String mrNumber) {
    this.mrNumber = mrNumber;
  }

  public void setDateDelivered(Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setSalesTax(BigDecimal salesTax) {
    this.salesTax = salesTax;
  }

  public void setPercentSplitCharge(BigDecimal b) {
    this.percentSplitCharge = b;
  }

  public void setPartDescription(String s) {
    this.partDescription = s;
  }

  public void setLineCount(BigDecimal b) {
    this.lineCount = b;
  }
  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getPartNumber() {
    return partNumber;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getExtPrice() {
    return extPrice;
  }

  public BigDecimal getNetLineAmount() {
    return netLineAmount;
  }

  public BigDecimal getAdditionalCharges() {
    return additionalCharges;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public String getMrNumber() {
    return mrNumber;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getSalesTax() {
    return salesTax;
  }

  public BigDecimal getPercentSplitCharge() {
    return percentSplitCharge;
  }

  public String getPartDescription() {
    return this.partDescription;
  }

  public BigDecimal getLineCount() {
    return this.lineCount;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInvoice(stream.readBigDecimal());
      this.setInvoiceLine(stream.readBigDecimal());
      this.setPoNumber(stream.readString());
      this.setPartNumber(stream.readString());
      this.setQuantity(stream.readBigDecimal());
      this.setExtPrice(stream.readBigDecimal());
      this.setNetLineAmount(stream.readBigDecimal());
      this.setAdditionalCharges(stream.readBigDecimal());
      this.setServiceFee(stream.readBigDecimal());
      this.setMrNumber(stream.readString());
      this.setDateDelivered(new Date(stream.readDate().getTime()));
      this.setInvoiceUnitPrice(stream.readBigDecimal());
      this.setAccountNumber(stream.readString());
      this.setSalesTax(stream.readBigDecimal());
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
      stream.writeBigDecimal(this.getInvoiceLine());
      stream.writeString(this.getPoNumber());
      stream.writeString(this.getPartNumber());
      stream.writeBigDecimal(this.getQuantity());
      stream.writeBigDecimal(this.getExtPrice());
      stream.writeBigDecimal(this.getNetLineAmount());
      stream.writeBigDecimal(this.getAdditionalCharges());
      stream.writeBigDecimal(this.getServiceFee());
      stream.writeString(this.getMrNumber());
      stream.writeDate(new java.sql.Date(this.getDateDelivered().getTime()));
      stream.writeBigDecimal(this.getInvoiceUnitPrice());
      stream.writeString(this.getAccountNumber());
      stream.writeBigDecimal(this.getSalesTax());
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