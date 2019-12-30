package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetSetupViewBean <br>
 * @version: 1.0, Nov 10, 2010 <br>
 *****************************************************************************/

public class CabinetSetupInputBean extends BaseDataBean {

	private static final BigDecimal NEW = new BigDecimal("-1");
	private boolean active = true;
	private String building;
	private BigDecimal cabinetId;
	private String cabinetName;
	private String companyId;
	private String contactEmail;
	private String contactName;
	private String contactPhone;
	private String customerCabinetId;
	private String deliveryPoint;
	private String inventoryGroup;
	private String location;
	private String locationId;
	private String orderingApplication;
	private String room;
	private String status;
	private boolean touched = false;

	//constructor
	public CabinetSetupInputBean() {
	}

	public String getBuilding() {
		return building;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getContactName() {
		return contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getCustomerCabinetId() {
		return customerCabinetId;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getLocation() {
		return location;
	}

	public String getLocationId() {
		return locationId;
	}

	public String getOrderingApplication() {
		return orderingApplication;
	}

	public String getRoom() {
		return room;
	}

	public String getStatus() {
		return status;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isNew() {
		return NEW.equals(this.cabinetId);
	}

	public boolean isTouched() {
		return touched;
	}

	public void setActive(boolean active) {
		this.active = active;
		this.status = active ? "A" : "I";
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setCustomerCabinetId(String customerCabinetId) {
		this.customerCabinetId = customerCabinetId;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setOrderingApplication(String orderingApplication) {
		this.orderingApplication = orderingApplication;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setStatus(String status) {
		this.status = status;
		this.active = "A".equals(status) ? true : false;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
}