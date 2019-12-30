package com.tcmis.client.aerojet.beans;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class AddressBean extends BaseDataBean {
	
	private String partnerName;
	private String partnerId;
	private String partnerType;
	private Collection address = new Vector();	
	private String city;
	private String state;
	private String zip;
	private String country;
	private String region;
	private String address1;
	private String address2;
	private String address3;
	private ContactPersonBean contactPerson;

	public String getAddress1() {
		if (!StringHandler.isBlankString(address1)) {
			if (address1.length() > 60) {
				address1 = address1.substring(0, 59);
			}
		}
		return address1;
	}

	public String getAddress2() {
		if (!StringHandler.isBlankString(address2)) {
			if (address2.length() > 60) {
				address2 = address2.substring(0, 59);
			}
		}
		return address2;
	}

	public String getAddress3() {
		if (!StringHandler.isBlankString(address3)) {
			if (address3.length() > 60) {
				address3 = address3.substring(0, 59);
			}
		}
		return address3;
	}

	public void addAddress(String address) {
		this.address.add(address);		
		if (this.address.size() == 1) {			
			this.address1 = (String)this.address.toArray()[0]; 
		} else if (this.address.size() == 2) {
			this.address2 = (String)this.address.toArray()[1];
		} else if (this.address.size() == 3) {
			this.address3 = (String)this.address.toArray()[2];
		}
	}
	
	public Collection getAddress() {
		return address;
	}
	public void setAddress(Collection address) {
		this.address = address;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) { 
		this.partnerType = partnerType;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public ContactPersonBean getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(ContactPersonBean contactPerson) {
		this.contactPerson = contactPerson;
	}
	public void addContactPerson(ContactPersonBean contactPerson) {
		this.contactPerson = contactPerson;
	}
}
