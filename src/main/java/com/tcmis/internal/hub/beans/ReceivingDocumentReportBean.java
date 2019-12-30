package com.tcmis.internal.hub.beans;


import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Locale;

/******************************************************************************
 * CLASSNAME: ReceivingDocumentReportBean <br>
 * 
 * @version: 1.0, Sep 8, 2016 <br>
 *****************************************************************************/

public class ReceivingDocumentReportBean extends BaseDataBean {

    private String userAction;
    private String opsEntityId;
    private String opsEntityName;
    private String sourceHub;
    private String sourceHubName;
    private String inventoryGroup;
    private String inventoryGroupName;
    private Date beginDateJsp;
    private Date endDateJsp;

    private BigDecimal receiptId;
    private String mfgLot;
    private Date qcDate;
    private String qcUserName;
    private BigDecimal documentId;
    private String documentType;
    private String documentUrl;
    private BigDecimal numberOfDays;
    private String enteredByName;
    private String documentName;
    private String companyId;

    //constructor
    public ReceivingDocumentReportBean() {
    }

    public ReceivingDocumentReportBean(ActionForm form, Locale locale) throws Exception {
        try {
            BeanHandler.copyAttributes(form, this, locale);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getOpsEntityId() {
        return opsEntityId;
    }

    public void setOpsEntityId(String opsEntityId) {
        this.opsEntityId = opsEntityId;
    }

    public String getSourceHub() {
        return sourceHub;
    }

    public void setSourceHub(String sourceHub) {
        this.sourceHub = sourceHub;
    }

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public Date getBeginDateJsp() {
        return beginDateJsp;
    }

    public void setBeginDateJsp(Date beginDateJsp) {
        this.beginDateJsp = beginDateJsp;
    }

    public Date getEndDateJsp() {
        return endDateJsp;
    }

    public void setEndDateJsp(Date endDateJsp) {
        this.endDateJsp = endDateJsp;
    }

    public BigDecimal getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(BigDecimal receiptId) {
        this.receiptId = receiptId;
    }

    public String getMfgLot() {
        return mfgLot;
    }

    public void setMfgLot(String mfgLot) {
        this.mfgLot = mfgLot;
    }

    public Date getQcDate() {
        return qcDate;
    }

    public void setQcDate(Date qcDate) {
        this.qcDate = qcDate;
    }

    public String getQcUserName() {
        return qcUserName;
    }

    public void setQcUserName(String qcUserName) {
        this.qcUserName = qcUserName;
    }

    public BigDecimal getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigDecimal documentId) {
        this.documentId = documentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public BigDecimal getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(BigDecimal numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getEnteredByName() {
        return enteredByName;
    }

    public void setEnteredByName(String enteredByName) {
        this.enteredByName = enteredByName;
    }

    public boolean isSearch() {
        return "search".equals(userAction);
    }

    public boolean isCreateExcel() {
        return "createExcel".equals(userAction);
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getOpsEntityName() {
        return opsEntityName;
    }

    public void setOpsEntityName(String opsEntityName) {
        this.opsEntityName = opsEntityName;
    }

    public String getSourceHubName() {
        return sourceHubName;
    }

    public void setSourceHubName(String sourceHubName) {
        this.sourceHubName = sourceHubName;
    }

    public String getInventoryGroupName() {
        return inventoryGroupName;
    }

    public void setInventoryGroupName(String inventoryGroupName) {
        this.inventoryGroupName = inventoryGroupName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean hasSourceHub() {
        return StringUtils.isNotBlank(sourceHub);
    }

    public boolean hasInventoryGroup() {
        return StringUtils.isNotBlank(inventoryGroup);
    }

    public boolean hasBeginDateJsp() {
        return beginDateJsp != null;
    }

    public boolean hasEndDateJsp() {
        return endDateJsp != null;
    }
}