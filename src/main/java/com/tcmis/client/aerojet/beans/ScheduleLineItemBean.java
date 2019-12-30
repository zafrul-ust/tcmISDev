package com.tcmis.client.aerojet.beans;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class ScheduleLineItemBean extends BaseDataBean {
	
	private Collection dateTimeCollectionBean = new Vector(); //needByDate, promiseDate
	private QuantityBean scheduleQuantityBean;	//Qty
	private String scheduleLineNumber;
	private OperAmtBean scheduleUnitPriceBean;//price
	private Collection addressBeanCollection = new Vector(); //ship to information and deliver to information	
	private String projectRequestorName;	
	private String projectRequestorEmail;
	private String projectRequestorPhoneNumber;	
	private String projectNumber;
	private String projectType;
	private String projectTask;
	private String application;
	private String ownerSegmentId;
	
	public void addAddressBean(AddressBean bean) {
		this.addressBeanCollection.add(bean);
	}
	
	public void addDateTimeBean(DateTimeBean dateTimeBean) {
		this.dateTimeCollectionBean.add(dateTimeBean);
	}
	
	public Collection getDateTimeCollectionBean() {
		return dateTimeCollectionBean;
	}

	public void setDateTimeCollectionBean(Collection dateTimeCollectionBean) {
		this.dateTimeCollectionBean = dateTimeCollectionBean;
	}
	public String getScheduleLineNumber() {
		return scheduleLineNumber;
	}
	public void setScheduleLineNumber(String scheduleLineNumber) {
		this.scheduleLineNumber = scheduleLineNumber;
	}	
	public Collection getAddressBeanCollection() {
		return addressBeanCollection;
	}
	public void setAddressBeanCollection(Collection addressBeanCollection) {
		this.addressBeanCollection = addressBeanCollection;
	}
	public String getProjectRequestorName() {
		return projectRequestorName;
	}
	public void setProjectRequestorName(String projectRequestorName) {
		this.projectRequestorName = projectRequestorName;
	}	
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectTask() {
		return projectTask;
	}
	public void setProjectTask(String projectTask) {
		this.projectTask = projectTask;
	}

	public String getProjectRequestorEmail() {
		return projectRequestorEmail;
	}

	public void setProjectRequestorEmail(String projectRequestorEmail) {
		this.projectRequestorEmail = projectRequestorEmail;
	}

	public String getProjectRequestorPhoneNumber() {
		return projectRequestorPhoneNumber;
	}

	public void setProjectRequestorPhoneNumber(String projectRequestorPhoneNumber) {
		this.projectRequestorPhoneNumber = projectRequestorPhoneNumber;
	}

	public Date getDateTime(String qualifier, SimpleDateFormat format) {
		Date dt = null;
		Iterator loop = this.dateTimeCollectionBean.iterator();
		while(loop.hasNext()){
		    DateTimeBean localBean = (DateTimeBean) loop.next();		
			if (!StringHandler.isBlankString(localBean.getQualifier()) 
					&& localBean.getQualifier().equalsIgnoreCase(qualifier)) {
				dt = localBean.getDateTime(format);
				break;
			}
		}
		return dt;
	} 
	
	public AddressBean getAddressBean(String qualifier) {
		AddressBean addressbean = null;
		Iterator loop = this.addressBeanCollection.iterator();
		while(loop.hasNext()){
			AddressBean localBean = (AddressBean) loop.next();		
			if (!StringHandler.isBlankString(localBean.getPartnerType()) 
					&& localBean.getPartnerType().equalsIgnoreCase(qualifier)) {				
				addressbean = localBean;
				break;
			}
		}
		return addressbean;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}

	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}

	public QuantityBean getScheduleQuantityBean() {
		return scheduleQuantityBean;
	}

	public void setScheduleQuantityBean(QuantityBean scheduleQuantityBean) {
		this.scheduleQuantityBean = scheduleQuantityBean;
	}

	public OperAmtBean getScheduleUnitPriceBean() {
		return scheduleUnitPriceBean;
	}

	public void setScheduleUnitPriceBean(OperAmtBean scheduleUnitPriceBean) {
		this.scheduleUnitPriceBean = scheduleUnitPriceBean;
	}

	
}
