package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceLineBean <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceLineBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String companyId;
  private String accountNumber;
  private String accountNumber2;
  private String poNumber;
  private BigDecimal extendedPrice;
  private BigDecimal rebate;
  private BigDecimal serviceFee;
  private BigDecimal totalAmount;
  private BigDecimal additionalCharges;
  private String chargeType;
  private BigDecimal salesTax;
  private String currencyId;
  private String sqlType = "INVOICE_LINE_PRINT_OBJ";

  //constructor
  public InvoiceLineBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setAccountNumber2(String accountNumber2) {
    this.accountNumber2 = accountNumber2;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setExtendedPrice(BigDecimal extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public void setRebate(BigDecimal rebate) {
    this.rebate = rebate;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public void setAdditionalCharges(BigDecimal additionalCharges) {
    this.additionalCharges = additionalCharges;
  }

  public void setChargeType(String chargeType) {
    this.chargeType = chargeType;
  }

  public void setSalesTax(BigDecimal salesTax) {
    this.salesTax = salesTax;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAccountNumber2() {
    return accountNumber2;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public BigDecimal getExtendedPrice() {
    return extendedPrice;
  }

  public BigDecimal getRebate() {
    return rebate;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public BigDecimal getAdditionalCharges() {
    return additionalCharges;
  }

  public String getChargeType() {
    return chargeType;
  }

  public BigDecimal getSalesTax() {
    return salesTax;
  }

  public String getCurrencyId() {
    return currencyId;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInvoice(stream.readBigDecimal());
      this.setInvoiceLine(stream.readBigDecimal());
      this.setCompanyId(stream.readString());
      this.setAccountNumber(stream.readString());
      this.setAccountNumber2(stream.readString());
      this.setPoNumber(stream.readString());
      this.setExtendedPrice(stream.readBigDecimal());
      this.setRebate(stream.readBigDecimal());
      this.setServiceFee(stream.readBigDecimal());
      this.setTotalAmount(stream.readBigDecimal());
      this.setAdditionalCharges(stream.readBigDecimal());
      this.setChargeType(stream.readString());
      this.setSalesTax(stream.readBigDecimal());
      this.setCurrencyId(stream.readString());
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
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getAccountNumber());
      stream.writeString(this.getAccountNumber2());
      stream.writeString(this.getPoNumber());
      stream.writeBigDecimal(this.getExtendedPrice());
      stream.writeBigDecimal(this.getRebate());
      stream.writeBigDecimal(this.getServiceFee());
      stream.writeBigDecimal(this.getTotalAmount());
      stream.writeBigDecimal(this.getAdditionalCharges());
      stream.writeString(this.getChargeType());
      stream.writeBigDecimal(this.getSalesTax());
      stream.writeString(this.getCurrencyId());
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