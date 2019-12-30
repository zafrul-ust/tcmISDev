package com.tcmis.internal.hub.beans;


import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Locale;

/******************************************************************************
 * CLASSNAME: EdiOrderTrackingBean <br>
 * 
 * @version: 1.0, Sep 8, 2016 <br>
 *****************************************************************************/

public class EdiOrderTrackingBean extends BaseDataBean {

    private String userAction;
    private String companyId;
    private String companyName;
    private String facilityId;
    private String facilityName;
    private Collection facilityList;
    private String searchMode;
    private String searchModeName;
    private String searchField;
    private String searchFieldName;
    private String searchArgument;
    private String updateRow;
    private String onlyDisplayCatalogAdd;
    private String onlyDisplayEdiError;
    private String application;
    private String catalogCompanyId;
    private String catalogId;
    private String catalogDesc;
    private BigDecimal catalogAddRequestId;
    private String catPartNo;
    private BigDecimal partGroupNo;
    private BigDecimal changeOrderSequence;
    private String customerPoNo;
    private String customerPoLineNo;
    private String customerPoLineNote;
    private Date dateOrderReceived;
    private String errorDetail;
    private BigDecimal lineSequence;
    private String orderPartUom;
    private String partShortDesc;
    private BigDecimal quantity;
    private Date requestedDeliveryDate;
    private String shiptoCity;
    private String shiptoPartyId;
    private String status;
    private String statusDetail;
    private String supplierCode;
    private String unspsc;

    //constructor
    public EdiOrderTrackingBean() {
    }

    public EdiOrderTrackingBean(ActionForm form, Locale locale) throws Exception {
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Collection getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(Collection facilityList) {
        this.facilityList = facilityList;
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

    public boolean isSearch() {
        return "search".equals(userAction);
    }

    public boolean isCreateExcel() {
        return "createExcel".equals(userAction);
    }

    public boolean isUpdate() {
        return "update".equals(userAction);
    }

    public boolean isOpenEditEdiError() { return "openEditEdiError".equals(userAction);}

    public boolean isEditEdiError() { return "editEdiError".equals(userAction);}

    public String getUpdateRow() {
        return updateRow;
    }

    public void setUpdateRow(String updateRow) {
        this.updateRow = updateRow;
    }

    public String getOnlyDisplayCatalogAdd() {
        return onlyDisplayCatalogAdd;
    }

    public void setOnlyDisplayCatalogAdd(String onlyDisplayCatalogAdd) {
        this.onlyDisplayCatalogAdd = onlyDisplayCatalogAdd;
    }

    public boolean isDisplayCatalogAddOnly() {
        return "Y".equals(onlyDisplayCatalogAdd);
    }

    public boolean isDisplayEdiErrorOnly() {
        return "Y".equals(onlyDisplayEdiError);
    }

    public String getOnlyDisplayEdiError() {
        return onlyDisplayEdiError;
    }

    public void setOnlyDisplayEdiError(String onlyDisplayEdiError) {
        this.onlyDisplayEdiError = onlyDisplayEdiError;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getCatalogCompanyId() {
        return catalogCompanyId;
    }

    public void setCatalogCompanyId(String catalogCompanyId) {
        this.catalogCompanyId = catalogCompanyId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogDesc() {
        return catalogDesc;
    }

    public void setCatalogDesc(String catalogDesc) {
        this.catalogDesc = catalogDesc;
    }

    public BigDecimal getCatalogAddRequestId() {
        return catalogAddRequestId;
    }

    public void setCatalogAddRequestId(BigDecimal catalogAddRequestId) {
        this.catalogAddRequestId = catalogAddRequestId;
    }

    public String getCatPartNo() {
        return catPartNo;
    }

    public void setCatPartNo(String catPartNo) {
        this.catPartNo = catPartNo;
    }

    public BigDecimal getPartGroupNo() {
        return partGroupNo;
    }

    public void setPartGroupNo(BigDecimal partGroupNo) {
        this.partGroupNo = partGroupNo;
    }

    public BigDecimal getChangeOrderSequence() {
        return changeOrderSequence;
    }

    public void setChangeOrderSequence(BigDecimal changeOrderSequence) {
        this.changeOrderSequence = changeOrderSequence;
    }

    public String getCustomerPoNo() {
        return customerPoNo;
    }

    public void setCustomerPoNo(String customerPoNo) {
        this.customerPoNo = customerPoNo;
    }

    public String getCustomerPoLineNo() {
        return customerPoLineNo;
    }

    public void setCustomerPoLineNo(String customerPoLineNo) {
        this.customerPoLineNo = customerPoLineNo;
    }

    public String getCustomerPoLineNote() {
        return customerPoLineNote;
    }

    public void setCustomerPoLineNote(String customerPoLineNote) {
        this.customerPoLineNote = customerPoLineNote;
    }

    public Date getDateOrderReceived() {
        return dateOrderReceived;
    }

    public void setDateOrderReceived(Date dateOrderReceived) {
        this.dateOrderReceived = dateOrderReceived;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public BigDecimal getLineSequence() {
        return lineSequence;
    }

    public void setLineSequence(BigDecimal lineSequence) {
        this.lineSequence = lineSequence;
    }

    public String getOrderPartUom() {
        return orderPartUom;
    }

    public void setOrderPartUom(String orderPartUom) {
        this.orderPartUom = orderPartUom;
    }

    public String getPartShortDesc() {
        return partShortDesc;
    }

    public void setPartShortDesc(String partShortDesc) {
        this.partShortDesc = partShortDesc;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Date getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    public String getShiptoCity() {
        return shiptoCity;
    }

    public void setShiptoCity(String shiptoCity) {
        this.shiptoCity = shiptoCity;
    }

    public String getShiptoPartyId() {
        return shiptoPartyId;
    }

    public void setShiptoPartyId(String shiptoPartyId) {
        this.shiptoPartyId = shiptoPartyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getUnspsc() {
        return unspsc;
    }

    public void setUnspsc(String unspsc) {
        this.unspsc = unspsc;
    }

    public boolean isCancelCatalogAddRequest() {
        return "cancelCatalogAddRequest".equals(userAction);
    }
}