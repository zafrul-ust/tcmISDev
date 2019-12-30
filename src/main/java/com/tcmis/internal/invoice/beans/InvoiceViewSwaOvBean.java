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
 * CLASSNAME: InvoiceViewSwaOvBean <br>
 * @version: 1.0, Mar 22, 2005 <br>
 *****************************************************************************/

public class InvoiceViewSwaOvBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private BigDecimal invoicePeriod;
  private String catPartNo;
  private BigDecimal invoiceQuantity;
  private BigDecimal quantityOrdered;
  private BigDecimal invoiceUnitPrice;
  private BigDecimal unitDiscount;
  private BigDecimal extendedPrice;
  private BigDecimal totalDiscount;
  private BigDecimal netAmount;
  private BigDecimal totalAdditionalCharge;
  private BigDecimal serviceFee;
  private String partDescription;
  private String requestorName;
  private String haasRequestNumber;
  private BigDecimal finalExtendedPrice;
  private BigDecimal totalAmount;
  private BigDecimal invoiceAmount;
  private String manufacturerDesc;
  private String invoiceSupplier;
  private Date invoiceDate;
  private Collection detail;
  private Array detailArray;
  private String sqlType = "INVOICE_VIEW_SWA_OBJ";

  private BigDecimal issueId;

  //constructor
  public InvoiceViewSwaOvBean() {
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

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setInvoiceQuantity(BigDecimal invoiceQuantity) {
    this.invoiceQuantity = invoiceQuantity;
  }

  public void setQuantityOrdered(BigDecimal quantityOrdered) {
    this.quantityOrdered = quantityOrdered;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setUnitDiscount(BigDecimal unitDiscount) {
    this.unitDiscount = unitDiscount;
  }

  public void setExtendedPrice(BigDecimal extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public void setTotalDiscount(BigDecimal totalDiscount) {
    this.totalDiscount = totalDiscount;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public void setTotalAdditionalCharge(BigDecimal totalAdditionalCharge) {
    this.totalAdditionalCharge = totalAdditionalCharge;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setRequestorName(String requestorName) {
    this.requestorName = requestorName;
  }

  public void setHaasRequestNumber(String haasRequestNumber) {
    this.haasRequestNumber = haasRequestNumber;
  }

  public void setFinalExtendedPrice(BigDecimal finalExtendedPrice) {
    this.finalExtendedPrice = finalExtendedPrice;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setManufacturerDesc(String manufacturerDesc) {
    this.manufacturerDesc = manufacturerDesc;
  }

  public void setInvoiceSupplier(String invoiceSupplier) {
    this.invoiceSupplier = invoiceSupplier;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
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

  public void setIssueId(BigDecimal b) {
    this.issueId = b;
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

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getInvoiceQuantity() {
    return invoiceQuantity;
  }

  public BigDecimal getQuantityOrdered() {
    return quantityOrdered;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public BigDecimal getUnitDiscount() {
    return unitDiscount;
  }

  public BigDecimal getExtendedPrice() {
    return extendedPrice;
  }

  public BigDecimal getTotalDiscount() {
    return totalDiscount;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public BigDecimal getTotalAdditionalCharge() {
    return totalAdditionalCharge;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public String getRequestorName() {
    return requestorName;
  }

  public String getHaasRequestNumber() {
    return haasRequestNumber;
  }

  public BigDecimal getFinalExtendedPrice() {
    return finalExtendedPrice;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getManufacturerDesc() {
    return manufacturerDesc;
  }

  public String getInvoiceSupplier() {
    return invoiceSupplier;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Collection getDetail() {
    return this.detail;
  }

  public Array getDetailArray() {
    return detailArray;
  }

  public BigDecimal getIssueId() {
    return this.issueId;
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
      this.setInvoicePeriod(stream.readBigDecimal());
      this.setCatPartNo(stream.readString());
      this.setInvoiceQuantity(stream.readBigDecimal());
      this.setQuantityOrdered(stream.readBigDecimal());
      this.setInvoiceUnitPrice(stream.readBigDecimal());
      this.setUnitDiscount(stream.readBigDecimal());
      this.setExtendedPrice(stream.readBigDecimal());
      this.setTotalDiscount(stream.readBigDecimal());
      this.setNetAmount(stream.readBigDecimal());
      this.setTotalAdditionalCharge(stream.readBigDecimal());
      this.setServiceFee(stream.readBigDecimal());
      this.setPartDescription(stream.readString());
      this.setRequestorName(stream.readString());
      this.setHaasRequestNumber(stream.readString());
      this.setFinalExtendedPrice(stream.readBigDecimal());
      this.setTotalAmount(stream.readBigDecimal());
      this.setInvoiceAmount(stream.readBigDecimal());
      this.setManufacturerDesc(stream.readString());
      this.setInvoiceSupplier(stream.readString());
      this.setInvoiceDate(new Date(stream.readDate().getTime()));
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
      stream.writeBigDecimal(this.getInvoiceLine());
      stream.writeString(this.getPoNumber());
      stream.writeBigDecimal(this.getInvoicePeriod());
      stream.writeString(this.getCatPartNo());
      stream.writeBigDecimal(this.getInvoiceQuantity());
      stream.writeBigDecimal(this.getQuantityOrdered());
      stream.writeBigDecimal(this.getInvoiceUnitPrice());
      stream.writeBigDecimal(this.getUnitDiscount());
      stream.writeBigDecimal(this.getExtendedPrice());
      stream.writeBigDecimal(this.getTotalDiscount());
      stream.writeBigDecimal(this.getNetAmount());
      stream.writeBigDecimal(this.getTotalAdditionalCharge());
      stream.writeBigDecimal(this.getServiceFee());
      stream.writeString(this.getPartDescription());
      stream.writeString(this.getRequestorName());
      stream.writeString(this.getHaasRequestNumber());
      stream.writeBigDecimal(this.getFinalExtendedPrice());
      stream.writeBigDecimal(this.getTotalAmount());
      stream.writeBigDecimal(this.getInvoiceAmount());
      stream.writeString(this.getManufacturerDesc());
      stream.writeString(this.getInvoiceSupplier());
      stream.writeDate(new java.sql.Date(this.getInvoiceDate().getTime()));
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