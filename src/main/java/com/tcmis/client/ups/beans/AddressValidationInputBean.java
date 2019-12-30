package com.tcmis.client.ups.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 20, 2008
 * Time: 3:01:38 PM
 * To change this template use File | Settings | File Templates.
 */


public class AddressValidationInputBean extends BaseDataBean {

    private String city;
    private String state;
    private String zip;


    //constructor
	public AddressValidationInputBean() {
	}

	//setters
    public void setCity(String s) {
		this.city = s;
	}

	public void setState(String s) {
		this.state = s;
	}

	public void setZip(String s) {
		this.zip = s;
	}

    //getters
    public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public String getZip() {
		return this.zip;
	}
}
