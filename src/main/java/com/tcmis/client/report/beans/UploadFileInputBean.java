package com.tcmis.client.report.beans;

import java.io.File;

import com.tcmis.common.framework.BaseDataBean;


public class UploadFileInputBean extends BaseDataBean {

    private File theFile;
	private String fileName;
	private String uAction;

	//constructor
	public UploadFileInputBean() {
	}

	//setters
		
	public void setTheFile(File file) {
		this.theFile = file;
	}
	
	 public void setFileName(String fileName) {
		if(fileName != null && this.doTrim) {
			this.fileName = fileName.trim();
		}
		else {
			this.fileName = fileName;
		}
	}
	 
	//getters
		
	public File getTheFile() {
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
	
	
}