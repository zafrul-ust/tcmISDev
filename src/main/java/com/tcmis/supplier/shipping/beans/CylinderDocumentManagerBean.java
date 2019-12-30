package com.tcmis.supplier.shipping.beans;


import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.io.File;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Locale;

/******************************************************************************
 * CLASSNAME: CylinderDocumentManagerBean <br>
 * 
 * @version: 1.0, May 19, 2017 <br>
 *****************************************************************************/

public class CylinderDocumentManagerBean extends BaseDataBean {

    private String userAction;
    private String supplier;
    private String manufacturerName;
    private BigDecimal cylinderTrackingId;
    private String serialNumber;
    private BigDecimal cylinderDocumentId;
    private String documentName;
    private String documentType;
    private String documentUrl;
    private Date dateInserted;
    private BigDecimal insertedBy;
    private String insertedByName;
    private Date lastUpdatedDate;
    private BigDecimal lastUpdatedBy;
    private String lastUpdatedByName;
    private String includeDeleted;
    private String ok;
    private File theFile;
    private String documentSource;

    //constructor
    public CylinderDocumentManagerBean() {
    }

    public CylinderDocumentManagerBean(ActionForm form, Locale locale) throws Exception {
        try {
            BeanHandler.copyAttributes(form, this, locale);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isAddCylinderDocument() { return "addCylinderDocument".equals(userAction);}

    public boolean isShowAddCylinderDocument() { return "showAddCylinderDocument".equals(userAction);}

    public boolean isViewCylinderDocumentManager() { return "viewCylinderDocumentManager".equals(userAction);}

    public boolean isDeleteDocument() { return "deleteDocument".equals(userAction);}

    public boolean isIncludeDeted() { return "Yes".equals(includeDeleted);}

    public boolean isDeleteRow() { return "true".equals(ok);}

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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

    public BigDecimal getCylinderDocumentId() {
        return cylinderDocumentId;
    }

    public void setCylinderDocumentId(BigDecimal cylinderDocumentId) {
        this.cylinderDocumentId = cylinderDocumentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
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

    public Date getDateInserted() {
        return dateInserted;
    }

    public void setDateInserted(Date dateInserted) {
        this.dateInserted = dateInserted;
    }

    public BigDecimal getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(BigDecimal insertedBy) {
        this.insertedBy = insertedBy;
    }

    public String getInsertedByName() {
        return insertedByName;
    }

    public void setInsertedByName(String insertedByName) {
        this.insertedByName = insertedByName;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
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

    public String getIncludeDeleted() {
        return includeDeleted;
    }

    public void setIncludeDeleted(String includeDeleted) {
        this.includeDeleted = includeDeleted;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public File getTheFile() {
        return theFile;
    }

    public void setTheFile(File theFile) {
        this.theFile = theFile;
    }

    public String getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(String documentSource) {
        this.documentSource = documentSource;
    }
}