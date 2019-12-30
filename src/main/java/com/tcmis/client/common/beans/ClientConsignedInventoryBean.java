package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ClientConsignedInventoryBean <br>
 * @version: 1.0, Dec 29, 2010 <br>
 *****************************************************************************/


public class ClientConsignedInventoryBean extends BaseDataBean {

	private String clientPartNos;
	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal receiptId;
	private BigDecimal quantity;
	private String mfgLot;
	private Date expireDate;
	private BigDecimal returnPrNumber;
	private String ownerCompanyId;
	private String inventoryGroup;
	private String inventoryGroupName;
	private Date qcDate;
	private String hub;
	private String accountNumber;
	private String accountNumber2;
	private String accountNumber3;
	private String accountNumber4;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String partDescription;
	private String currencyId;
	private BigDecimal establishedUnitPrice;
	private BigDecimal extPrice;
	private String lotStatus;
	private String ownerSegmentId;
    private BigDecimal uosQuantity;
    private String unitOfSale;
    private String customerReceiptId;


    //constructor
	public ClientConsignedInventoryBean() {
	}

	//setters
	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setClientPartNos(String clientPartNos) {
		this.clientPartNos = clientPartNos;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setReturnPrNumber(BigDecimal returnPrNumber) {
		this.returnPrNumber = returnPrNumber;
	}
	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}
	public void setAccountNumber3(String accountNumber3) {
		this.accountNumber3 = accountNumber3;
	}
	public void setAccountNumber4(String accountNumber4) {
		this.accountNumber4 = accountNumber4;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}
	//getters
	public String getCustomerReceiptId() {
		return customerReceiptId;
	}
	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}
	public String getLotStatus() {
		return lotStatus;
	 }
	public String getClientPartNos() {
		return clientPartNos;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getReturnPrNumber() {
		return returnPrNumber;
	}
	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public String getHub() {
		return hub;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getAccountNumber2() {
		return accountNumber2;
	}
	public String getAccountNumber3() {
		return accountNumber3;
	}
	public String getAccountNumber4() {
		return accountNumber4;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getPartDescription() {
		return partDescription;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getExtPrice() {
		return extPrice;
	}

	public void setExtPrice(BigDecimal extPrice) {
		this.extPrice = extPrice;
	}

	public BigDecimal getEstablishedUnitPrice() {
		return establishedUnitPrice;
	}

	public void setEstablishedUnitPrice(BigDecimal establishedUnitPrice) {
		this.establishedUnitPrice = establishedUnitPrice;
	}

    public BigDecimal getUosQuantity() {
        return uosQuantity;
    }

    public void setUosQuantity(BigDecimal uosQuantity) {
        this.uosQuantity = uosQuantity;
    }

    public String getUnitOfSale() {
        return unitOfSale;
    }

    public void setUnitOfSale(String unitOfSale) {
        this.unitOfSale = unitOfSale;
    }
}