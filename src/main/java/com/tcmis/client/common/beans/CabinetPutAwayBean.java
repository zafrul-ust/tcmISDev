package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CabinetPutAwayBean <br>
 * 
 * @version: 1.0, Aug 27, 2013 <br>
 * 
 *****************************************************************************/

public class CabinetPutAwayBean extends BaseDataBean {
	private BigDecimal	applicationId;
	private String		application;
	private String		applicationDesc;
	private String		companyId;
	private Date		dateDelivered;
	private BigDecimal	shipmentId;
	private BigDecimal	issueId;
	private BigDecimal	receiptId;
	private BigDecimal	containerId;

	private Integer		putAwayBy;
	private Date		putAwayDateTime;
	private String		putAwayAcceptedBy;

	// constructor
	public CabinetPutAwayBean() {
	}

	public CabinetPutAwayBean(JSONObject jsonData) throws BaseException {
		try {
			String shipmentId = jsonData.getString("shipmentId");
			String applicationId = jsonData.getString("cabinetId");
			String putAwayBy = jsonData.getString("personnelId");
			String putAwayDateTime = jsonData.getString("deliveryTimestamp");
			String putAwayAcceptedBy = jsonData.getString("putAwayAcceptedBy");
			String issueId = jsonData.getString("issueId");
			String receiptId = jsonData.getString("receiptId");
			String containerId = jsonData.getString("containerId");
			
			this.setShipmentId(shipmentId.length() > 0 ? new BigDecimal(shipmentId) : null);
			this.setApplicationId(applicationId.length() > 0 ? new BigDecimal(applicationId) : null);
			this.setPutAwayBy(putAwayBy.length() > 0 ? new Integer(putAwayBy) : null);
			this.setPutAwayDateTime(putAwayDateTime.length() > 0 ?  DateHandler.getDateFromString("ddMMMyyyy'T'hh:mmaaa",putAwayDateTime,TimeZone.getTimeZone("GMT")) : null);
			this.setPutAwayAcceptedBy(putAwayAcceptedBy.length() > 0 ? putAwayAcceptedBy : null);
			this.setIssueId(issueId.length() > 0 ? new BigDecimal(issueId) : null);
			this.setReceiptId(receiptId.length() > 0 ? new BigDecimal(receiptId) : null);
			this.setContainerId(containerId.length() > 0 ? new BigDecimal(containerId) : null);
			
		}
		catch (Exception e) {
			throw new BaseException(e);
		}
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getCompanyId() {
		return companyId;
	}

	public Date getDateDelivered() {
		return dateDelivered;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public Integer getPutAwayBy() {
		return putAwayBy;
	}

	public Date getPutAwayDateTime() {
		return putAwayDateTime;
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public BigDecimal getContainerId() {
		return containerId;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setCompanyName(String cabinetName) {
		this.applicationDesc = cabinetName;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public void setPutAwayBy(Integer putAwayBy) {
		this.putAwayBy = putAwayBy;
	}

	public void setPutAwayDateTime(Date putAwayDateTime) {
		this.putAwayDateTime = putAwayDateTime;
	}

	public void setContainerId(BigDecimal containerId) {
		this.containerId = containerId;
	}
	
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public String getPutAwayAcceptedBy() {
		return putAwayAcceptedBy;
	}

	public void setPutAwayAcceptedBy(String putAwayAcceptedBy) {
		this.putAwayAcceptedBy = putAwayAcceptedBy;
	}
	
	public boolean hasCompanyId(){
		if(!StringHandler.isBlankString(this.getCompanyId()))
			return true;
		return false;
	}
	
	public boolean hasPutAwayBy(){
		if(this.putAwayBy != null && this.putAwayBy.intValue() > 0)
			return true;
		return false;
	}
	
	public boolean hasPutAwayDateTime(){
		if(this.putAwayDateTime != null)
			return true;
		return false;
	}
	
	public boolean hasPutAwayAcceptedBy(){
		if(!StringHandler.isBlankString(this.putAwayAcceptedBy))
			return true;
		return false;
	}
}
