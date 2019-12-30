package com.tcmis.internal.msds.beans;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

/**
 * This class is a placeholder for form values.  In a multipart request, files are represented by
 * set and get methods that use the class org.apache.struts.upload.FormFile, an interface with
 * basic methods to retrieve file information.  The actual structure of the FormFile is dependant
 * on the underlying impelementation of multipart request handling.  The default implementation
 * that struts uses is org.apache.struts.upload.CommonsMultipartRequestHandler.
 *
 * @version $Revision: 1.6 $ $Date: 2009/01/08 16:45:38 $
 */
public class MsdsInputForm extends ActionForm {


    protected String msds;
    protected String status;
    protected String tradeName;
    protected String manufacturerName;
    protected String revisionDate;
    protected FormFile theFile;
    protected String fileName;
    protected String facilityId;
    protected String building;
    protected String floor;
    protected String department;
    protected String onLine;
    protected String hazardClassification;
    protected String submitNew;
    protected String submitSearch;
    protected String submitAdd;
    protected String submitUpdate;
    protected String submitDelete;

    public String getMsds() {
        return this.msds;
    }

    public void setMsds(String msds) {
      if(msds != null)
        this.msds = msds.trim();
      else
        this.msds = msds;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
      if(status != null)
        this.status = status.trim();
      else
        this.status = status;
    }

    public String getTradeName() {
        return this.tradeName;
    }

    public void setTradeName(String tradeName) {
      if(tradeName != null)
        this.tradeName = tradeName.trim();
      else
        this.tradeName = tradeName;
    }

    public String getManufacturerName() {
        return this.manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
      if(manufacturerName != null)
        this.manufacturerName = manufacturerName.trim();
      else
        this.manufacturerName = manufacturerName;
    }

    public String getRevisionDate() {
        return this.revisionDate;
    }

    public void setRevisionDate(String revisionDate) {
      if(revisionDate != null)
        this.revisionDate = revisionDate.trim();
      this.revisionDate = revisionDate;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
      if(fileName != null)
        this.fileName = fileName.trim();
      else
        this.fileName = fileName;
    }


    public FormFile getTheFile() {
        return this.theFile;
    }

    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }

    public String getFacilityId() {
        return this.facilityId;
    }

    public void setFacilityId(String facilityId) {
      if(facilityId != null)
        this.facilityId = facilityId.trim();
      else
        this.facilityId = facilityId;
    }

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String building) {
      if(building != null) {
        this.building = building.trim();
      }
      else {
        this.building = building;
      }
    }

    public String getFloor() {
        return this.floor;
    }

    public void setFloor(String floor) {
      if(floor != null)
        this.floor = floor.trim();
      else
        this.floor = floor;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
      if(department != null)
        this.department = department.trim();
      else
        this.department = department;
    }

    public String getOnLine() {
        return this.onLine;
    }

    public void setOnLine(String onLine) {
      if(onLine != null)
        this.onLine = onLine.trim();
      else
        this.onLine = onLine;
    }

    public String getSubmitNew() {
        return this.submitNew;
    }

    public void setSubmitNew(String submitNew) {
        this.submitNew = submitNew;
    }

    public String getSubmitSearch() {
        return this.submitSearch;
    }

    public void setSubmitSearch(String submitSearch) {
        this.submitSearch = submitSearch;
    }

    public String getSubmitAdd() {
        return this.submitAdd;
    }

    public void setSubmitAdd(String submitAdd) {
        this.submitAdd = submitAdd;
    }

    public String getSubmitUpdate() {
        return this.submitUpdate;
    }

    public void setSubmitUpdate(String submitUpdate) {
        this.submitUpdate = submitUpdate;
    }

    public String getSubmitDelete() {
        return this.submitDelete;
    }

    public void setSubmitDelete(String submitDelete) {
        this.submitDelete = submitDelete;
    }

    public String getHazardClassification() {
        return this.hazardClassification;
    }

    public void setHazardClassification(String s) {
      if(s != null)
        this.hazardClassification = s.trim();
      else
        this.hazardClassification = s;
    }

}
