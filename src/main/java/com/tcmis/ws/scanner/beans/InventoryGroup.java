package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InventoryGroupDefinitionBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class InventoryGroup extends BaseDataBean {

	private String								accountCompanyCarrierCode;
	private String								accountCompanyId;
	private String								carrierAccountId;
	private Collection<InventoryGroupCarrier>	carrierAccounts;
	private boolean								distributorOps;
	private String								hub;
	private String								inventoryGroup;
	private BigDecimal							inventoryGroupId;
	private String								inventoryGroupName;
	private boolean								manageKitsAsSingleUnit;
	private boolean								nonintegerReceiving;
	private BigDecimal							packingListNumCopies;
	private Date								lastUpdated;
	private Date								carrierLastUpdated;

	// constructor
	public InventoryGroup() {
	}

	public void addCarrier(InventoryGroup child) {
		if (carrierAccounts == null) {
			setCarrierAccounts(new Vector<InventoryGroupCarrier>());
		}
		carrierAccounts.add(child.getFirstCarrier());
	}

	public Collection<InventoryGroupCarrier> getCarrierAccounts() {
		return this.carrierAccounts;
	}

	protected synchronized InventoryGroupCarrier getFirstCarrier() {
		InventoryGroupCarrier firstCarrier = null;
		if (carrierAccounts == null || carrierAccounts.isEmpty()) {
			setCarrierAccounts(new Vector<InventoryGroupCarrier>());
			firstCarrier = new InventoryGroupCarrier();
			carrierAccounts.add(firstCarrier);
		}
		else {
			firstCarrier = ((Vector<InventoryGroupCarrier>) carrierAccounts).get(0);
		}
		return firstCarrier;
	}

	public String getHub() {
		return hub;
	}

	// getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getInventoryGroupId() {
		return inventoryGroupId;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public BigDecimal getPackingListNumCopies() {
		return packingListNumCopies;
	}

	public boolean isDistributorOps() {
		return this.distributorOps;
	}

	public boolean isManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}

	public boolean isNonintegerReceiving() {
		return nonintegerReceiving;
	}

	public boolean isSameInventoryGroup(InventoryGroup other) {
		return StringUtils.isNotBlank(hub) && StringUtils.isNotBlank(inventoryGroup) && hub.equals(other.getHub()) && inventoryGroup.equals(other.getInventoryGroup());
	}

	public void setAccountCompanyCarrierCode(String accountCompanyCarrierCode) {
		getFirstCarrier().setAccountCompanyCarrierCode(accountCompanyCarrierCode);
	}

	public void setAccountCompanyId(String accountCompanyId) {
		getFirstCarrier().setAccountCompanyId(accountCompanyId);
	}

	public void setCarrierAccountId(String carrierAccountId) {
		getFirstCarrier().setCarrierAccountId(carrierAccountId);
	}

	private void setCarrierAccounts(Collection<InventoryGroupCarrier> carrierAccounts) {
		this.carrierAccounts = carrierAccounts;
	}

	public void setDistributorOps(boolean distributorOps) {
		this.distributorOps = distributorOps;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupId(BigDecimal inventoryGroupId) {
		this.inventoryGroupId = inventoryGroupId;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setManageKitsAsSingleUnit(boolean manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}

	public void setNonintegerReceiving(boolean nonintegerReceiving) {
		this.nonintegerReceiving = nonintegerReceiving;
	}

	public void setPackingListNumCopies(BigDecimal packingListNumCopies) {
		this.packingListNumCopies = packingListNumCopies;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setCarrierLastUpdated(Date carrierLastUpdated) {
		getFirstCarrier().setLastUpdated(carrierLastUpdated);
	}
}