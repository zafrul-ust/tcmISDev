package com.tcmis.client.raytheon.beans;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * This class is a placeholder for form values.  In a multipart request, files are represented by
 * set and get methods that use the class org.apache.struts.upload.FormFile, an interface with
 * basic methods to retrieve file information.  The actual structure of the FormFile is dependant
 * on the underlying impelementation of multipart request handling.  The default implementation
 * that struts uses is org.apache.struts.upload.CommonsMultipartRequestHandler.
 *
 * @version $Revision: 1.1 $ $Date: 2007/10/22 20:12:16 $
 */
public class PossUploadInputForm extends ActionForm {

  protected FormFile theFile;
  protected String fileName;
  protected String uploadFile;

  //constructor
  public PossUploadInputForm() {
  }

  //setters
  public void setTheFile(FormFile file) {
    this.theFile = file;
  }

  public void setFileName(String fileName) {
    if (fileName != null) {
      this.fileName = fileName.trim();
    } else {
      this.fileName = fileName;
    }
  }

  public void setUploadFile(String uploadFile) {
    this.uploadFile = uploadFile;
  }

  //getters
  public FormFile getTheFile() {
    return this.theFile;
  }

  public String getFileName() {
    return this.fileName;
  }

  public String getUploadFile() {
    return this.uploadFile;
  }
}