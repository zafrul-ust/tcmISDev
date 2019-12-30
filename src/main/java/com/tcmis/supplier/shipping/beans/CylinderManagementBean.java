package com.tcmis.supplier.shipping.beans;


import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Locale;

/******************************************************************************
 * CLASSNAME: CylinderManagementBean <br>
 * 
 * @version: 1.0, Feb 10, 2017 <br>
 *****************************************************************************/

public class CylinderManagementBean extends BaseDataBean {

    private String userAction;
    private String uAction;
    private String supplier;
    private String supplierName;
    private String companyId;
    private String companyName;
    private String locationId;
    private String locationDesc;
    private Collection locationList;
    private String searchMode;
    private String searchModeName;
    private String searchField;
    private String searchFieldName;
    private String searchArgument;
    private String includeInactive;

    private String cylinderConditionCode;
    private String cylinderStatus;
    private String refurbCategory;
    private String refurbSubcategory;

    private BigDecimal cylinderTrackingId;
    private String serialNumber;
    private String vendorPartNo;
    private String lotStatus;
    private Date dateReceived;
    private String receivedByName;
    private Date dateSentOut;
    private String cylinderTrackingStatus;
    private String status;
    private BigDecimal receivedBy;
    private String conversionGroup;
    private String onHand;
    private String returnFromLocation;
    private String manufacturerIdNo;
    private String manufacturerName;
    private String lastShippedDodaac;
    private Date lastUpdated;
    private BigDecimal lastUpdatedBy;
    private String lastUpdatedByName;
    private Date latestHydroTestDate;

    private String printBarcode;
    private String rowWithUpdate;
    private boolean selectedRow;
    private BigDecimal cylinderRefurbTransId;
    private Date dateServiced;
    private Date dateDisposed;
    private String dimensions;
    private String dotRating;
    private String gasType;
    private String correspondingNsn;
    private String capacity;
    private String documentNumber;
    private String notes;

    //constructor
    public CylinderManagementBean() {
    }

    public CylinderManagementBean(ActionForm form, Locale locale) throws Exception {
        try {
            BeanHandler.copyAttributes(form, this, locale);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isSearch() {
        return "search".equals(userAction) || "search".equals(uAction);
    }

    public boolean isCreateExcel() {
        return "createExcel".equals(userAction) || "createExcel".equals(uAction);
    }

    public boolean isIncludeInactive() {
        return "Y".equals(includeInactive);
    }

    public boolean isUpdate() {
        return "update".equals(userAction);
    }

    public boolean isShowNewEditCylinder() { return "showNewEditCylinder".equals(userAction);}

    public boolean isAddNewEditCylinder() { return "addNewEditCylinder".equals(userAction);}

    public boolean isShowEditCategory() { return "showEditCategory".equals(userAction);}

    public boolean isSubmitSubcategory() { return "submitSubcategory".equals(userAction);}

    public boolean isViewCylinderDetail() { return "viewCylinderDetails".equals(userAction);}

    public boolean isUpdateRow() {return "Y".equals(rowWithUpdate);}

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getuAction() {
        return uAction;
    }

    public void setuAction(String uAction) {
        this.uAction = uAction;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public Collection getLocationList() {
        return locationList;
    }

    public void setLocationList(Collection locationList) {
        this.locationList = locationList;
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

    public String getIncludeInactive() {
        return includeInactive;
    }

    public void setIncludeInactive(String includeInactive) {
        this.includeInactive = includeInactive;
    }

    public String getCylinderConditionCode() {
        return cylinderConditionCode;
    }

    public void setCylinderConditionCode(String cylinderConditionCode) {
        this.cylinderConditionCode = cylinderConditionCode;
    }

    public String getCylinderStatus() {
        return cylinderStatus;
    }

    public void setCylinderStatus(String cylinderStatus) {
        this.cylinderStatus = cylinderStatus;
    }

    public String getRefurbCategory() {
        return refurbCategory;
    }

    public void setRefurbCategory(String refurbCategory) {
        this.refurbCategory = refurbCategory;
    }

    public String getRefurbSubcategory() {
        return refurbSubcategory;
    }

    public void setRefurbSubcategory(String refurbSubcategory) {
        this.refurbSubcategory = refurbSubcategory;
    }

    public BigDecimal getCylinderTrackingId() {
        return cylinderTrackingId;
    }

    public void setCylinderTrackingId(BigDecimal cylinderTrackingId) {
        this.cylinderTrackingId = cylinderTrackingId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVendorPartNo() {
        return vendorPartNo;
    }

    public void setVendorPartNo(String vendorPartNo) {
        this.vendorPartNo = vendorPartNo;
    }

    public String getLotStatus() {
        return lotStatus;
    }

    public void setLotStatus(String lotStatus) {
        this.lotStatus = lotStatus;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getReceivedByName() {
        return receivedByName;
    }

    public void setReceivedByName(String receivedByName) {
        this.receivedByName = receivedByName;
    }

    public Date getDateSentOut() {
        return dateSentOut;
    }

    public void setDateSentOut(Date dateSentOut) {
        this.dateSentOut = dateSentOut;
    }

    public String getCylinderTrackingStatus() {
        return cylinderTrackingStatus;
    }

    public void setCylinderTrackingStatus(String cylinderTrackingStatus) {
        this.cylinderTrackingStatus = cylinderTrackingStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(BigDecimal receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getConversionGroup() {
        return conversionGroup;
    }

    public void setConversionGroup(String conversionGroup) {
        this.conversionGroup = conversionGroup;
    }

    public String getOnHand() {
        return onHand;
    }

    public void setOnHand(String onHand) {
        this.onHand = onHand;
    }

    public String getReturnFromLocation() {
        return returnFromLocation;
    }

    public void setReturnFromLocation(String returnFromLocation) {
        this.returnFromLocation = returnFromLocation;
    }

    public String getManufacturerIdNo() {
        return manufacturerIdNo;
    }

    public void setManufacturerIdNo(String manufacturerIdNo) {
        this.manufacturerIdNo = manufacturerIdNo;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getLastShippedDodaac() {
        return lastShippedDodaac;
    }

    public void setLastShippedDodaac(String lastShippedDodaac) {
        this.lastShippedDodaac = lastShippedDodaac;
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

    public Date getLatestHydroTestDate() {
        return latestHydroTestDate;
    }

    public void setLatestHydroTestDate(Date latestHydroTestDate) {
        this.latestHydroTestDate = latestHydroTestDate;
    }

    public String getPrintBarcode() {
        return printBarcode;
    }

    public void setPrintBarcode(String printBarcode) {
        this.printBarcode = printBarcode;
    }

    public String getRowWithUpdate() {
        return rowWithUpdate;
    }

    public void setRowWithUpdate(String rowWithUpdate) {
        this.rowWithUpdate = rowWithUpdate;
    }

    public boolean getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(boolean selectedRow) {
        this.selectedRow = selectedRow;
    }

    public BigDecimal getCylinderRefurbTransId() {
        return cylinderRefurbTransId;
    }

    public void setCylinderRefurbTransId(BigDecimal cylinderRefurbTransId) {
        this.cylinderRefurbTransId = cylinderRefurbTransId;
    }

    public Date getDateServiced() {
        return dateServiced;
    }

    public void setDateServiced(Date dateServiced) {
        this.dateServiced = dateServiced;
    }

    public Date getDateDisposed() {
        return dateDisposed;
    }

    public void setDateDisposed(Date dateDisposed) {
        this.dateDisposed = dateDisposed;
    }

    public boolean isSelectedRow() {
        return selectedRow;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDotRating() {
        return dotRating;
    }

    public void setDotRating(String dotRating) {
        this.dotRating = dotRating;
    }

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public String getCorrespondingNsn() {
        return correspondingNsn;
    }

    public void setCorrespondingNsn(String correspondingNsn) {
        this.correspondingNsn = correspondingNsn;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}