package com.tcmis.client.dla.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DlaGasRoadmapViewBean <br>
 * @version: 1.0, Jul 5, 2011 <br>
 *****************************************************************************/


public class DlaGasRoadmapViewBean extends BaseDataBean {

	private String nsn;
	private String contractNumber;
	private String manufacturer;
	private String manufacturerCage;
	private String subcontractor;
	private String subcontractorCage;
	private String tradeName;
	private String msdsId;
	private Date latestRevDate;
	private Date shipConfirmDate;


	//constructor
	public DlaGasRoadmapViewBean() {
	}

	//setters
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setManufacturerCage(String manufacturerCage) {
		this.manufacturerCage = manufacturerCage;
	}
	public void setSubcontractor(String subcontractor) {
		this.subcontractor = subcontractor;
	}
	public void setSubcontractorCage(String subcontractorCage) {
		this.subcontractorCage = subcontractorCage;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public void setMsdsId(String msdsId) {
		this.msdsId = msdsId;
	}
	public void setLatestRevDate(Date latestRevDate) {
		this.latestRevDate = latestRevDate;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}


	//getters
	public String getNsn() {
		return nsn;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getManufacturerCage() {
		return manufacturerCage;
	}
	public String getSubcontractor() {
		return subcontractor;
	}
	public String getSubcontractorCage() {
		return subcontractorCage;
	}
	public String getTradeName() {
		return tradeName;
	}
	public String getMsdsId() {
		return msdsId;
	}
	public Date getLatestRevDate() {
		return latestRevDate;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}

}