package com.tcmis.client.common.beans;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.tcmis.common.util.StringHandler;

public class UploadFileForm extends ActionForm {
	  private FormFile theFile;
	  private BigDecimal materialId;
	  private String facility;
	  private String client;
	  private String localeCode;
	  private String revisionDate;
	  private String name;
	  private String phone;
	  private String email;
	  private String comments;
	  private String fileName;
	
	  //constructor
	  public UploadFileForm() {
	  }
	
	  public void setTheFile(FormFile file) {
	    this.theFile = file;
	  }
	
	  public FormFile getTheFile() {
	    return this.theFile;
	  }

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

} //end of class