package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class HubCycleCountInputBean extends BaseInputBean {

	private static final long serialVersionUID = 1L;
	private BigDecimal binId;
	private Date countDatetime;
	private BigDecimal receiptId;
	private BigDecimal countId;
	private BigDecimal personnelId;
	private BigDecimal expectedQuantity;
	private BigDecimal actualQuantity;
	private Date dateProcessed;
	private String opsEntityId;
	private String hub;
	private String sortBy;
	private String counted;
	private BigDecimal countMonth; 
	private String showUncountedOnly;
	
	public HubCycleCountInputBean() {
		
	}
	
	public HubCycleCountInputBean(HttpServletRequest request, ActionForm inputForm, Locale locale) {
		super(inputForm, locale);		
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		addHiddenFormField("sortBy");
		addHiddenFormField("hub");
		addHiddenFormField("opsEntityId");		
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public Date getCountDatetime() {
		return countDatetime;
	}

	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public Date getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public boolean isStartNewCount() {
		return "startCount".equals(getuAction());
	}	

	public boolean isCloseCount() {
		return "closeCount".equals(getuAction());
	}

	public boolean isAllRowsCounted() {
		return "allRowsCounted".equals(getuAction());
	}
	
	public boolean isValidCreateNewCountId() {
		return "isValidCreateNewCountId".equals(getuAction());
	}
	
	public boolean isNextMonthCountStarted() {
		return "isNextMonthCountStarted".equals(getuAction());
	}
	
	public boolean isRowCounted() {
		return "true".equals(counted) && StringHandler.emptyIfNull(actualQuantity).length() > 0;
	}

	public BigDecimal getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(BigDecimal actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public String getCounted() {
		return counted;
	}

	public void setCounted(String counted) {
		this.counted = counted;
	}

	public BigDecimal getCountId() {
		return countId;
	}

	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}

	public BigDecimal getExpectedQuantity() {
		return expectedQuantity;
	}

	public void setExpectedQuantity(BigDecimal expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public BigDecimal getCountMonth() {
		return countMonth;
	}

	public void setCountMonth(BigDecimal countMonth) {
		this.countMonth = countMonth;
	}

	public String getShowUncountedOnly() {
		return showUncountedOnly;
	}

	public void setShowUncountedOnly(String showUncountedOnly) {
		this.showUncountedOnly = showUncountedOnly;
	}
	
}
