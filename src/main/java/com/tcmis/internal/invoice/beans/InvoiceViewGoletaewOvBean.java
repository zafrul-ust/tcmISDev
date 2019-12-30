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
 * CLASSNAME: InvoiceViewGoletaewOvBean <br>
 * @version: 1.0, Mar 4, 2005 <br>
 *****************************************************************************/

public class InvoiceViewGoletaewOvBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private BigDecimal invoicePeriod;
  private String catPartNo;
  private BigDecimal invoiceQuantity;
  private BigDecimal requestedQuantity;
  private BigDecimal invoiceUnitPrice;
  private BigDecimal unitRebate;
  private BigDecimal extendedPrice;
  private BigDecimal rebate;
  private BigDecimal netAmount;
  private BigDecimal totalAddCharge;
  private BigDecimal serviceFee;
  private String partDescription;
  private String requestorName;
  private String requestNumber;
  private BigDecimal netExtPrice;
  private BigDecimal finalExtPrice;
  private BigDecimal invoiceAmount;
  private String mfgDesc;
  private BigDecimal serviceFeePercent;
  private String invoiceSupplier;
  private java.util.Date invoiceDate;
  private Collection detail;
  private Array detailArray;
  private String sqlType = "INVOICE_VIEW_GOLETAEW_DET_OBJ";

  //constructor
  public InvoiceViewGoletaewOvBean() {
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

  public void setRequestedQuantity(BigDecimal requestedQuantity) {
    this.requestedQuantity = requestedQuantity;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setUnitRebate(BigDecimal unitRebate) {
    this.unitRebate = unitRebate;
  }

  public void setExtendedPrice(BigDecimal extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public void setRebate(BigDecimal rebate) {
    this.rebate = rebate;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public void setTotalAddCharge(BigDecimal totalAddCharge) {
    this.totalAddCharge = totalAddCharge;
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

  public void setRequestNumber(String requestNumber) {
    this.requestNumber = requestNumber;
  }

  public void setNetExtPrice(BigDecimal netExtPrice) {
    this.netExtPrice = netExtPrice;
  }

  public void setFinalExtPrice(BigDecimal finalExtPrice) {
    this.finalExtPrice = finalExtPrice;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setMfgDesc(String mfgDesc) {
    this.mfgDesc = mfgDesc;
  }

  public void setServiceFeePercent(BigDecimal serviceFeePercent) {
    this.serviceFeePercent = serviceFeePercent;
  }

  public void setInvoiceSupplier(String invoiceSupplier) {
    this.invoiceSupplier = invoiceSupplier;
  }

  public void setInvoiceDate(java.util.Date invoiceDate) {
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

  public BigDecimal getRequestedQuantity() {
    return requestedQuantity;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public BigDecimal getUnitRebate() {
    return unitRebate;
  }

  public BigDecimal getExtendedPrice() {
    return extendedPrice;
  }

  public BigDecimal getRebate() {
    return rebate;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public BigDecimal getTotalAddCharge() {
    return totalAddCharge;
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

  public String getRequestNumber() {
    return requestNumber;
  }

  public BigDecimal getNetExtPrice() {
    return netExtPrice;
  }

  public BigDecimal getFinalExtPrice() {
    return finalExtPrice;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getMfgDesc() {
    return mfgDesc;
  }

  public BigDecimal getServiceFeePercent() {
    return serviceFeePercent;
  }

  public String getInvoiceSupplier() {
    return invoiceSupplier;
  }

  public java.util.Date getInvoiceDate() {
    return invoiceDate;
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
      this.setInvoiceLine(stream.readBigDecimal());
      this.setPoNumber(stream.readString());
      this.setInvoicePeriod(stream.readBigDecimal());
      this.setCatPartNo(stream.readString());
      this.setInvoiceQuantity(stream.readBigDecimal());
      this.setRequestedQuantity(stream.readBigDecimal());
      this.setInvoiceUnitPrice(stream.readBigDecimal());
      this.setUnitRebate(stream.readBigDecimal());
      this.setExtendedPrice(stream.readBigDecimal());
      this.setRebate(stream.readBigDecimal());
      this.setNetAmount(stream.readBigDecimal());
      this.setTotalAddCharge(stream.readBigDecimal());
      this.setServiceFee(stream.readBigDecimal());
      this.setPartDescription(stream.readString());
      this.setRequestorName(stream.readString());
      this.setRequestNumber(stream.readString());
      this.setNetExtPrice(stream.readBigDecimal());
      this.setFinalExtPrice(stream.readBigDecimal());
      this.setInvoiceAmount(stream.readBigDecimal());
      this.setMfgDesc(stream.readString());
      this.setServiceFeePercent(stream.readBigDecimal());
      this.setInvoiceSupplier(stream.readString());
      this.setInvoiceDate(stream.readDate());
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
      stream.writeBigDecimal(this.getRequestedQuantity());
      stream.writeBigDecimal(this.getInvoiceUnitPrice());
      stream.writeBigDecimal(this.getUnitRebate());
      stream.writeBigDecimal(this.getExtendedPrice());
      stream.writeBigDecimal(this.getRebate());
      stream.writeBigDecimal(this.getNetAmount());
      stream.writeBigDecimal(this.getTotalAddCharge());
      stream.writeBigDecimal(this.getServiceFee());
      stream.writeString(this.getPartDescription());
      stream.writeString(this.getRequestorName());
      stream.writeString(this.getRequestNumber());
      stream.writeBigDecimal(this.getNetExtPrice());
      stream.writeBigDecimal(this.getFinalExtPrice());
      stream.writeBigDecimal(this.getInvoiceAmount());
      stream.writeString(this.getMfgDesc());
      stream.writeBigDecimal(this.getServiceFeePercent());
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