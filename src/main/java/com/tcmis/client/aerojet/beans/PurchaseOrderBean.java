package com.tcmis.client.aerojet.beans;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class PurchaseOrderBean extends BaseDataBean {
	
	private String companyId;
	//controlarea
	private String verb;
	private String noun;
	private String revision;
	//sender
	private String component;
	private String task;
	private String referenceId;
	private String confirmation;
	private String language;
	private String codePage;
	private String authId;
	//header
	private String poId; //ponumber
	private String poType;
	private String headerDescription;
	private String fobDescription;
	private String fobId;
	private String freightTermDescription;
	private String freightTermId;
	private String docStylePoType;	
	//payment Terms
	private String paymentTermsDesc;
	private String paymentTermsId;	
	
	private Collection lineItemBeanCollection = new Vector();	
	private Collection addressBeanCollection = new Vector();   //ship via carrier name/details
	private Collection dateTimeBeanCollection = new Vector();  //document creation datetime
	private OperAmtBean operAmtBean;
	
	public Date getDateTime(String qualifier, SimpleDateFormat format) { 
		Date dt = null;
			Iterator loop = this.dateTimeBeanCollection.iterator();
			while(loop.hasNext()) {
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
	
	public void setOperAmtBean(OperAmtBean operAmtBean) {
		this.operAmtBean = operAmtBean;
	}

	public OperAmtBean getOperAmtBean() {
		return operAmtBean;
	}

	public void addLineItemBean(LineItemBean bean) {
		this.lineItemBeanCollection.add(bean);		
	}
	
	public void addAddressBean(AddressBean bean) {
		this.addressBeanCollection.add(bean);
	}
	
	public void addDateTimeBean(DateTimeBean bean) {
		this.dateTimeBeanCollection.add(bean);
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getNoun() {
		return noun;
	}

	public void setNoun(String noun) {
		this.noun = noun;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCodePage() {
		return codePage;
	}

	public void setCodePage(String codePage) {
		this.codePage = codePage;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getPoType() {
		return poType;
	}

	public void setPoType(String poType) {
		this.poType = poType;
	}

	public String getHeaderDescription() {
		return headerDescription;
	}

	public void setHeaderDescription(String headerDescription) {
		this.headerDescription = headerDescription;
	}

	public String getFobDescription() {
		return fobDescription;
	}

	public void setFobDescription(String fobDescription) {
		this.fobDescription = fobDescription;
	}

	public String getFobId() {
		return fobId;
	}

	public void setFobId(String fobId) {
		this.fobId = fobId;
	}

	public String getFreightTermDescription() {
		return freightTermDescription;
	}

	public void setFreightTermDescription(String freightTermDescription) {
		this.freightTermDescription = freightTermDescription;
	}

	public String getFreightTermId() {
		return freightTermId;
	}

	public void setFreightTermId(String freightTermId) {
		this.freightTermId = freightTermId;
	}

	public String getDocStylePoType() {
		return docStylePoType;
	}

	public void setDocStylePoType(String docStylePoType) {
		this.docStylePoType = docStylePoType;
	}

	public String getPaymentTermsDesc() {
		return paymentTermsDesc;
	}

	public void setPaymentTermsDesc(String paymentTermsDesc) {
		this.paymentTermsDesc = paymentTermsDesc;
	}

	public String getPaymentTermsId() {
		return paymentTermsId;
	}

	public void setPaymentTermsId(String paymentTermsId) {
		this.paymentTermsId = paymentTermsId;
	}

	public Collection getLineItemBeanCollection() {
		return lineItemBeanCollection;
	}

	public void setLineItemBeanCollection(Collection lineItemBeanCollection) {
		this.lineItemBeanCollection = lineItemBeanCollection;
	}

	public Collection getAddressBeanCollection() {
		return addressBeanCollection;
	}

	public void setAddressBeanCollection(Collection addressBeanCollection) {
		this.addressBeanCollection = addressBeanCollection;
	}

	public Collection getDateTimeBeanCollection() {
		return dateTimeBeanCollection;
	}

	public void setDateTimeBeanCollection(Collection dateTimeBeanCollection) {
		this.dateTimeBeanCollection = dateTimeBeanCollection;
	}
	
	
}
