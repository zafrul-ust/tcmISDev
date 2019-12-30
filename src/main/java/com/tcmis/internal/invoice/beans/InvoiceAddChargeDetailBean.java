package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceAddChargeDetailBean <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceAddChargeDetailBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private BigDecimal issueId;
  private String companyId;
  private String itemType;
  private BigDecimal prNumber;
  private String lineItem;
  private String additionalChargeDesc;
  private BigDecimal additionalChargeAmount;
  private BigDecimal additionalChargeItemId;
  private BigDecimal issueCostRevision;
  private String salesTaxApplied;
  private String sqlType = "INVOICE_ADD_CHARGE_DETAIL_OBJ";

  //constructor
  public InvoiceAddChargeDetailBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setAdditionalChargeDesc(String additionalChargeDesc) {
    this.additionalChargeDesc = additionalChargeDesc;
  }

  public void setAdditionalChargeAmount(BigDecimal additionalChargeAmount) {
    this.additionalChargeAmount = additionalChargeAmount;
  }

  public void setAdditionalChargeItemId(BigDecimal additionalChargeItemId) {
    this.additionalChargeItemId = additionalChargeItemId;
  }

  public void setIssueCostRevision(BigDecimal issueCostRevision) {
    this.issueCostRevision = issueCostRevision;
  }

  public void setSalesTaxApplied(String salesTaxApplied) {
    this.salesTaxApplied = salesTaxApplied;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public BigDecimal getIssueId() {
    return issueId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getItemType() {
    return itemType;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public String getAdditionalChargeDesc() {
    return additionalChargeDesc;
  }

  public BigDecimal getAdditionalChargeAmount() {
    return additionalChargeAmount;
  }

  public BigDecimal getAdditionalChargeItemId() {
    return additionalChargeItemId;
  }

  public BigDecimal getIssueCostRevision() {
    return issueCostRevision;
  }

  public String getSalesTaxApplied() {
    return salesTaxApplied;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInvoice(stream.readBigDecimal());
      this.setInvoiceLine(stream.readBigDecimal());
      this.setIssueId(stream.readBigDecimal());
      this.setCompanyId(stream.readString());
      this.setItemType(stream.readString());
      this.setPrNumber(stream.readBigDecimal());
      this.setLineItem(stream.readString());
      this.setAdditionalChargeDesc(stream.readString());
      this.setAdditionalChargeAmount(stream.readBigDecimal());
      this.setAdditionalChargeItemId(stream.readBigDecimal());
      this.setIssueCostRevision(stream.readBigDecimal());
      this.setSalesTaxApplied(stream.readString());
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
      stream.writeBigDecimal(this.getIssueId());
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getItemType());
      stream.writeBigDecimal(this.getPrNumber());
      stream.writeString(this.getLineItem());
      stream.writeString(this.getAdditionalChargeDesc());
      stream.writeBigDecimal(this.getAdditionalChargeAmount());
      stream.writeBigDecimal(this.getAdditionalChargeItemId());
      stream.writeBigDecimal(this.getIssueCostRevision());
      stream.writeString(this.getSalesTaxApplied());
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