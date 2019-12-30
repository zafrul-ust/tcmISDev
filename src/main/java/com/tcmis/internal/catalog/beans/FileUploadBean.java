package com.tcmis.internal.catalog.beans;

import java.io.File;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ItemBean <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class FileUploadBean
 extends BaseDataBean {

 private String filename;
 private String location;
 private File theFile;
 
 public String getFilename() {
	return filename;
}

public void setFilename(String filename) {
	this.filename = filename;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

 public FileUploadBean() {
 }

public File getTheFile() {
	return theFile;
}

public void setTheFile(File theFile) {
	this.theFile = theFile;
}

}