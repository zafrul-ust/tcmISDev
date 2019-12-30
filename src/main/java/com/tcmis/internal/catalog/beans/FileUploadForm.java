package com.tcmis.internal.catalog.beans;


import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/******************************************************************************
 * CLASSNAME: FileUploadBean <br>
 * @version: 1.0, Oct 19, 2005 <br>
 *****************************************************************************/

public class FileUploadForm
    extends ActionForm {
  private String filename;
  private String location;
  private FormFile theFile;

  public FileUploadForm() {
  }

public FormFile getTheFile() {
	return theFile;
}

public void setTheFile(FormFile theFile) {
	this.theFile = theFile;
}

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
}