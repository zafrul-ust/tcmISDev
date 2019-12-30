package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: RowDataBean <br>
 * @version: 1.0, Nov 14, 2007 <br>
 *****************************************************************************/


public class RowDataBean extends BaseDataBean {

	private String rowData;
	private String ftpServer;
	private String ftpUserName;
	private String ftpPassword;
	private String fileName;
	private String path;
	private String encryptionRequired;
	private String encryptionMethod;
	private String encryptionPublicKey;
	private String ftpType;
	private String dosFormat;

	//constructor
	public RowDataBean() {
	}

	//setters
	public void setRowData(String rowData) {
		this.rowData = rowData;
	}
	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setPath(String path) {
		this.path = path;
	}

	//getters
	public String getRowData() {
		return rowData;
	}
	public String getFtpServer() {
		return ftpServer;
	}
	public String getFtpUserName() {
		return ftpUserName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public String getFileName() {
		return fileName;
	}
	public String getPath() {
		return path;
	}

	public String getEncryptionRequired() {
		return encryptionRequired;
	}

	public void setEncryptionRequired(String encryptionRequired) {
		this.encryptionRequired = encryptionRequired;
	}

	public String getEncryptionMethod() {
		return encryptionMethod;
	}

	public void setEncryptionMethod(String encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
	}

	public String getEncryptionPublicKey() {
		return encryptionPublicKey;
	}

	public void setEncryptionPublicKey(String encryptionPublicKey) {
		this.encryptionPublicKey = encryptionPublicKey;
	}

	public String getFtpType() {
		return ftpType;
	}

	public void setFtpType(String ftpType) {
		this.ftpType = ftpType;
	}

	public String getDosFormat() {
		return dosFormat;
	}

	public void setDosFormat(String dosFormat) {
		this.dosFormat = dosFormat;
	}

	
}