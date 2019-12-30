package com.tcmis.client.common.beans;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ApprovedMaterialInputBean <br>
 * @version: 1.0, Jul 31, 2014 <br>
 *****************************************************************************/

public class ApprovedMaterialInputBean extends BaseDataBean {

	private BigDecimal materialId;
    private Date revisionDate;
    private String localeCode;
    private BigDecimal batchId;
    private BigDecimal creatorId;
    private String manual = "";
    private String userAction = "";
    private String msdsId = "";
    private String docId = "";
    private BigDecimal revisionDateTimeStamp;

    public ApprovedMaterialInputBean() {
        super();
    }

    public BigDecimal getMaterialId() {
        return materialId;
    }

    public void setMaterialId(BigDecimal materialId) {
        this.materialId = materialId;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public BigDecimal getBatchId() {
        return batchId;
    }

    public void setBatchId(BigDecimal batchId) {
        this.batchId = batchId;
    }

    public BigDecimal getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(BigDecimal creatorId) {
        this.creatorId = creatorId;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getMsdsId() {
        return msdsId;
    }

    public void setMsdsId(String msdsId) {
        this.msdsId = msdsId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public boolean isGetImage() {
        return "getImage".equals(userAction);
    }

    public BigDecimal getRevisionDateTimeStamp() {
        return revisionDateTimeStamp;
    }

    public void setRevisionDateTimeStamp(BigDecimal revisionDateTimeStamp) {
        this.revisionDateTimeStamp = revisionDateTimeStamp;
    }

    public boolean isManualDataEntryComplete() {
        return "Y".equals(manual);
    }

    public boolean isReleasingCatalogAddRequest() {
        return "releaseCatAdd".equals(userAction);
    }
}