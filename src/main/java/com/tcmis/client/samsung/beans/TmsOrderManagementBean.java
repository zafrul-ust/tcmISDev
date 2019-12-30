package com.tcmis.client.samsung.beans;


import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Locale;

/******************************************************************************
 * CLASSNAME: TmsOrderManagementBean <br>
 * 
 * @version: 1.0, May 05, 2017 <br>
 *****************************************************************************/

public class TmsOrderManagementBean extends BaseDataBean {

    private String userAction;
    private String companyId;
    private String facilityId;
    private String facilityName;
    private Date orderFromDate;
    private Date orderToDate;
    private String searchMode;
    private String searchModeName;
    private String searchField;
    private String searchFieldName;
    private String searchArgument;
    private String displayOnlyErrorLine;
    private String customerRequisitionNumber;
    private BigDecimal prNumber;
    private String lineItem;
    private Date requiredDatetime;
    private Date submittedDate;
    private String status;
    private Date lastUpdated;
    private BigDecimal lastUpdatedBy;
    private String lastUpdatedByName;
    private String rowWithUpdate;
    private String catPartNo;
    private String packaging;
    private BigDecimal quantity;
    private String allocateByMfgLot;
    private String internalNote;
    private String sourceFileName;
    private String requestLineStatus;
    private String application;
    private String applicationDesc;
    private Date requestDate;
    private boolean onlyOpenRequest;
    private String notes;
    private Date allocateAfter;

    //constructor
    public TmsOrderManagementBean() {
    }

    public TmsOrderManagementBean(ActionForm form, Locale locale) throws Exception {
        try {
            BeanHandler.copyAttributes(form, this, locale);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isSearch() { return "search".equals(userAction);}

    public boolean isCreateExcel() {
        return "createExcel".equals(userAction);
    }

    public boolean isUpdate() {
        return "update".equals(userAction);
    }

    public boolean isUpdateAndReprocess() {
        return "updateAndReprocess".equals(userAction);
    }

    public boolean isCancelReservation() {
        return "cancelReservation".equals(userAction);
    }

    public boolean isCancelLineReservation() {
        return "cancelLineReservation".equals(userAction);
    }

    public boolean isUpdateRow() {return "true".equals(rowWithUpdate);}

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Date getOrderFromDate() {
        return orderFromDate;
    }

    public void setOrderFromDate(Date orderFromDate) {
        this.orderFromDate = orderFromDate;
    }

    public Date getOrderToDate() {
        return orderToDate;
    }

    public void setOrderToDate(Date orderToDate) {
        this.orderToDate = orderToDate;
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getSearchModeName() {
        return searchModeName;
    }

    public void setSearchModeName(String searchModeName) {
        this.searchModeName = searchModeName;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchFieldName() {
        return searchFieldName;
    }

    public void setSearchFieldName(String searchFieldName) {
        this.searchFieldName = searchFieldName;
    }

    public String getSearchArgument() {
        return searchArgument;
    }

    public void setSearchArgument(String searchArgument) {
        this.searchArgument = searchArgument;
    }

    public String getDisplayOnlyErrorLine() {
        return displayOnlyErrorLine;
    }

    public void setDisplayOnlyErrorLine(String displayOnlyErrorLine) {
        this.displayOnlyErrorLine = displayOnlyErrorLine;
    }

    public String getCustomerRequisitionNumber() {
        return customerRequisitionNumber;
    }

    public void setCustomerRequisitionNumber(String customerRequisitionNumber) {
        this.customerRequisitionNumber = customerRequisitionNumber;
    }

    public BigDecimal getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(BigDecimal prNumber) {
        this.prNumber = prNumber;
    }

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public Date getRequiredDatetime() {
        return requiredDatetime;
    }

    public void setRequiredDatetime(Date requiredDatetime) {
        this.requiredDatetime = requiredDatetime;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedByName() {
        return lastUpdatedByName;
    }

    public void setLastUpdatedByName(String lastUpdatedByName) {
        this.lastUpdatedByName = lastUpdatedByName;
    }

    public String getRowWithUpdate() {
        return rowWithUpdate;
    }

    public void setRowWithUpdate(String rowWithUpdate) {
        this.rowWithUpdate = rowWithUpdate;
    }

    public String getCatPartNo() {
        return catPartNo;
    }

    public void setCatPartNo(String catPartNo) {
        this.catPartNo = catPartNo;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getAllocateByMfgLot() {
        return allocateByMfgLot;
    }

    public void setAllocateByMfgLot(String allocateByMfgLot) {
        this.allocateByMfgLot = allocateByMfgLot;
    }

    public String getInternalNote() {
        return internalNote;
    }

    public void setInternalNote(String internalNote) {
        this.internalNote = internalNote;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getRequestLineStatus() {
        return requestLineStatus;
    }

    public void setRequestLineStatus(String requestLineStatus) {
        this.requestLineStatus = requestLineStatus;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationDesc() {
        return applicationDesc;
    }

    public void setApplicationDesc(String applicationDesc) {
        this.applicationDesc = applicationDesc;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public boolean getOnlyOpenRequest() {
        return onlyOpenRequest;
    }

    public void setOnlyOpenRequest(boolean onlyOpenRequest) {
        this.onlyOpenRequest = onlyOpenRequest;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getAllocateAfter() {
        return allocateAfter;
    }

    public void setAllocateAfter(Date allocateAfter) {
        this.allocateAfter = allocateAfter;
    }
}