package com.tcmis.client.dla.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:07:00 PM
 * To change this template use File | Settings | File Templates.
 */

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Date;

public class PolchemOrderTrackingInputBean
extends BaseDataBean
{
	private String searchField;
	private String searchType;
	private String searchArgument;

	private String currentStatus;
	private BigDecimal prNumber;
	private Date beginDate;
	private Date endDate;


	public PolchemOrderTrackingInputBean()
	{
	}


	public Date getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}


	public String getCurrentStatus() {
		return currentStatus;
	}


	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getSearchArgument() {
		return searchArgument;
	}


	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}


	public String getSearchField() {
		return searchField;
	}


	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}


	public String getSearchType() {
		return searchType;
	}


	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public BigDecimal getPrNumber() {
		return prNumber;
	}


	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}


}

