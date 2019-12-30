package com.tcmis.internal.supply.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Mar 14, 2008
 * Time: 3:32:15 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Date;

public class ChangeDlaShipToInputBean
    extends BaseDataBean
{
    private String 	 lineItem;
    private BigDecimal 	 prNumber;
    private String 	 dodaac; //shipViaDodaac
	private String 	 locationId;
    private String   dodaacType;
    private String 	 mfDodaac; //shipToDodaac
	private String 	 mfLocationId;
    private String   mfDodaacType;
    private String 	 notes;
    private String 	 portOfDebarkation;
    private String 	 portOfEmbarkation;

    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String city;
    private String stateAbbrev;
    private String zip;
    private String countryAbbrev;

    private String milstripCode;
    private String oldMilstripCode;
    private Date dpmsReplyDate;
    private String searchText;
    private String locationType;
	private String shipToLocationId;	
	private String shipViaLocationId;
	private BigDecimal radianPO;
    private Date desiredShipDate;
   
   public ChangeDlaShipToInputBean()
	{
	}

  public Date getDesiredShipDate() {
        return desiredShipDate;
    }

  public void setDesiredShipDate(Date desiredShipDate) {
       this.desiredShipDate = desiredShipDate;
  }

  public String getLocationType() {
    return locationType;
  }

  public void setLocationType(String locationType) {
    this.locationType = locationType;
  }
  
  public String getCity() {
		return city;
	}

	public void setCity(String s) {
		this.city = s;
	}

    public String getStateAbbrev() {
		return stateAbbrev;
	}

	public void setStateAbbrev(String s) {
		this.stateAbbrev = s;
	}

    public String getZip() {
		return zip;
	}

	public void setZip(String s) {
		this.zip = s;
	}

    public String getCountryAbbrev() {
		return countryAbbrev;
	}

	public void setCountryAbbrev(String s) {
		this.countryAbbrev = s;
	}

    public String getAddress1() {
		return address1;
	}

	public void setAddress1(String s) {
		this.address1 = s;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String s) {
		this.address2 = s;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String s) {
		this.address3 = s;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String s) {
		this.address4 = s;
	}

    public String getPortOfDebarkation() {
        return portOfDebarkation;
    }

    public void setPortOfDebarkation(String s) {
        this.portOfDebarkation = s;
    }

    public String getPortOfEmbarkation() {
        return portOfEmbarkation;
    }

    public void setPortOfEmbarkation(String s) {
        this.portOfEmbarkation = s;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String s) {
      if (s != null)
      {
        s =s.trim();
      }
      this.notes = s;
    }

	public String getDodaac() {
		return dodaac;
	}

	public void setDodaac(String s) {
		this.dodaac = s;
	}

	public String getMfDodaac() {
		return mfDodaac;
	}

	public void setMfDodaac(String s) {
		this.mfDodaac = s;
	}

    public String getDodaacType() {
		return dodaacType;
	}

	public void setDodaacType(String s) {
		this.dodaacType = s;
	}

    public String getMfDodaacType() {
		return mfDodaacType;
	}

	public void setMfDodaacType(String s) {
		this.mfDodaacType = s;
	}

    public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String s) {
		this.locationId = s;
	}

    public String getMfLocationId() {
		return mfLocationId;
	}

	public void setMfLocationId(String s) {
		this.mfLocationId = s;
	}

    public String getMilstripCode() {
		return milstripCode;
	}

	public void setMilstripCode(String s) {
		this.milstripCode = s;
	}

    public String getOldMilstripCode() {
		return oldMilstripCode;
	}

	public void setOldMilstripCode(String s) {
		this.oldMilstripCode = s;
	}

    public Date getDpmsReplyDate() {
		return dpmsReplyDate;
	}

	public void setDpmsReplyDate(Date d) {
		this.dpmsReplyDate = d;
	}

    public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String s) {
		this.searchText = s;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public String getShipViaLocationId() {
		return shipViaLocationId;
	}

	public void setShipViaLocationId(String shipViaLocationId) {
		this.shipViaLocationId = shipViaLocationId;
	}

	public BigDecimal getRadianPO() {
		return radianPO;
	}

	public void setRadianPO(BigDecimal radianPo) {
		this.radianPO = radianPo;
	}

}
