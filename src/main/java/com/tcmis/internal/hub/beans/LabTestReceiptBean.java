package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class LabTestReceiptBean extends BaseDataBean implements SQLData {
	public static Log log = LogFactory.getLog(TestResultBean.class);
	public final static String SQLTypeName = "CUSTOMER.LAB_TEST_RECEIPT_OBJ";

	private BigDecimal itemId;
    private BigDecimal receiptId;
    private String mfgLot;
    private Date expireDate;
    private BigDecimal hub;
    private String inventoryGroup;
    private BigDecimal quantityReceived;
    private Date dateOfManufacture;
    private Date dateOfShipment;
    private Date dateOfReceipt;
    private String lotStatus;
    private BigDecimal radianPo;
    private BigDecimal poLine;

    // Constructor
	public LabTestReceiptBean(){
	}

    public BigDecimal getItemId() {
        return itemId;
    }

    public void setItemId(BigDecimal itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(BigDecimal receiptId) {
        this.receiptId = receiptId;
    }

    public String getMfgLot() {
        return mfgLot;
    }

    public void setMfgLot(String mfgLot) {
        this.mfgLot = mfgLot;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public BigDecimal getHub() {
        return hub;
    }

    public void setHub(BigDecimal hub) {
        this.hub = hub;
    }

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public BigDecimal getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(BigDecimal quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public Date getDateOfShipment() {
        return dateOfShipment;
    }

    public void setDateOfShipment(Date dateOfShipment) {
        this.dateOfShipment = dateOfShipment;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public String getLotStatus() {
        return lotStatus;
    }

    public void setLotStatus(String lotStatus) {
        this.lotStatus = lotStatus;
    }

    public BigDecimal getRadianPo() {
        return radianPo;
    }

    public void setRadianPo(BigDecimal radianPo) {
        this.radianPo = radianPo;
    }

    public BigDecimal getPoLine() {
        return poLine;
    }

    public void setPoLine(BigDecimal poLine) {
        this.poLine = poLine;
    }

    public String getSQLTypeName() throws SQLException {
		return SQLTypeName;
	}

	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		try{
			setItemId(stream.readBigDecimal());
			setReceiptId(stream.readBigDecimal());
			setMfgLot(stream.readString());
			setExpireDate(stream.readDate());
			setHub(stream.readBigDecimal());
			setInventoryGroup(stream.readString());
			setQuantityReceived(stream.readBigDecimal());
			setDateOfManufacture(stream.readDate());
            setDateOfShipment(stream.readDate());
            setDateOfReceipt(stream.readDate());
            setLotStatus(stream.readString());
            setRadianPo(stream.readBigDecimal());
            setPoLine(stream.readBigDecimal());
        }
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			log.error(getClass().getName() + ".readSQL method failed", e);
		}
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		try{
			stream.writeBigDecimal(getItemId());
			stream.writeBigDecimal(getReceiptId());
			stream.writeString(getMfgLot());
			stream.writeBigDecimal(getHub());
			stream.writeString(getInventoryGroup());
			stream.writeBigDecimal(getQuantityReceived());
			stream.writeDate(getDateOfManufacture());
			stream.writeDate(getDateOfShipment());
            stream.writeDate(getDateOfReceipt());
            stream.writeString(getLotStatus());
            stream.writeBigDecimal(getRadianPo());
            stream.writeBigDecimal(getPoLine());
        }
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
		}
	}

}
