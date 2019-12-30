package com.tcmis.client.ups.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 20, 2008
 * Time: 2:44:53 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: AddressValidationResultBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class AddressValidationResultBean extends BaseDataBean {

	private BigDecimal rank;
    private BigDecimal quality;
    private String city;
    private String state;
    private String zipHighEnd;
    private String zipLowEnd;


    //constructor
	public AddressValidationResultBean() {
	}

	//setters
    public void setRank(BigDecimal b) {
		this.rank = b;
	}

    public void setQuality(BigDecimal b) {
		this.quality = b;
	}

    public void setCity(String s) {
		this.city = s;
	}

	public void setState(String s) {
		this.state = s;
	}

	public void setZipHighEnd(String s) {
		this.zipHighEnd = s;
	}

	public void setZipLowEnd(String s) {
		this.zipLowEnd = s;
	}

    //getters
    public BigDecimal getRank() {
		return this.rank;
	}

    public BigDecimal getQuality() {
		return this.quality;
	}

    public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public String getZipHighEnd() {
		return this.zipHighEnd;
	}

	public String getZipLowEnd() {
		return this.zipHighEnd;
	}
}
