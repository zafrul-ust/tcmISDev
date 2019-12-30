package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TempShipmentHistoryViewBean <br>
 * @version: 1.0, Apr 13, 2005 <br>
 *****************************************************************************/

public class HubCountDateBean
    extends BaseDataBean {


	
	private static final long serialVersionUID = -5533428308243174740L;
	private String hub;
	private Date countDatetime;
	private BigDecimal countId;
	private String countStatus;
	private String countType;
	private String countStartedByName;
	private String room;
	

  //constructor
  public HubCountDateBean() {
  }

  
public String getRoom() {
	return room;
}

public void setRoom(String room) {
	this.room = room;
}

public Date getCountDatetime() {
	return countDatetime;
}


public void setCountDatetime(Date countDatetime) {
	this.countDatetime = countDatetime;
}


public String getHub() {
	return hub;
}


public void setCountStartedByName(String countStartedByName) {
	this.countStartedByName = countStartedByName;
}

public String getCountStartedByName() {
	return countStartedByName;
}


public void setHub(String hub) {
	this.hub = hub;
}



/**
 * @return the countId
 */
public BigDecimal getCountId() {
	return countId;
}


/**
 * @param countId the countId to set
 */
public void setCountId(BigDecimal countId) {
	this.countId = countId;
}


/**
 * @return the countStatus
 */
public String getCountStatus() {
	return countStatus;
}


/**
 * @param countStatus the countStatus to set
 */
public void setCountStatus(String countStatus) {
	this.countStatus = countStatus;
}


/**
 * @return the countType
 */
public String getCountType() {
	return countType;
}


/**
 * @param countType the countType to set
 */
public void setCountType(String countType) {
	this.countType = countType;
}


}
