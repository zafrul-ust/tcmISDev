package com.tcmis.client.report.beans;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadFileForm extends ActionForm {
	protected FormFile theFile;
	protected String fileName;
	protected String uAction;

	//constructor
	public UploadFileForm() {
	}

	public void setTheFile(FormFile file) {
	 this.theFile = file;
	}
	public void setFileName(String fileName) {
		if(fileName != null) {
			this.fileName = fileName.trim();
		}
		else {
			this.fileName = fileName;
		}
	}

	//getters

	public FormFile getTheFile() {
	 return this.theFile;
	}
	public String getFileName() {
			return this.fileName;
	}
	
	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

} //end of class