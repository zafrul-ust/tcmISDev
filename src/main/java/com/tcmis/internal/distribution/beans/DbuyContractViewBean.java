package  com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractViewBean <br>
 * @version: 1.0, Jan 30, 2013 <br>
 *****************************************************************************/


public class DbuyContractViewBean extends BaseDataBean {

	private String currencyId;
	private BigDecimal currentPrice;
	private Date currentPriceDt;
	private BigDecimal dbuyContractId;
	private String hub;
	private String inventoryGroup;
	private String itemDesc;
	private BigDecimal itemId;
	private String opsEntityId;
	private String opsEntity;
	private String shipToLocationId;
	private String supplier;
	private String supplierName;
	private String supplierPartNo;
	private BigDecimal leadTimeDays;
	private BigDecimal minBuyQuantity;
	private BigDecimal minBuyValue;
	private BigDecimal breakQuantity;
	private BigDecimal unitPrice;
	private String inventoryGroupName;
	
	//constructor
	public DbuyContractViewBean() {
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public Date getCurrentPriceDt() {
		return currentPriceDt;
	}

	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}

	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}
	
	public String getOpsEntity() {
		return opsEntity;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getSupplier() {
		return supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}
	
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	
	public void setCurrencyId(String curencyId) {
		this.currencyId = curencyId;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public void setCurrentPriceDt(Date currentPriceDt) {
		this.currentPriceDt = currentPriceDt;
	}

	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setOpsEntity(String opsEntity) {
		this.opsEntity = opsEntity;
	}
	
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}

	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}

	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	public BigDecimal getMinBuyQuantity() {
		return minBuyQuantity;
	}

	public void setMinBuyQuantity(BigDecimal minBuyQuantity) {
		this.minBuyQuantity = minBuyQuantity;
	}

	public BigDecimal getMinBuyValue() {
		return minBuyValue;
	}

	public void setMinBuyValue(BigDecimal minBuyValue) {
		this.minBuyValue = minBuyValue;
	}

	public BigDecimal getBreakQuantity() {
		return breakQuantity;
	}

	public void setBreakQuantity(BigDecimal breakQuantity) {
		this.breakQuantity = breakQuantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
}