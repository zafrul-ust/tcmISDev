package com.tcmis.client.raytheon.beans;

import java.io.File;
import com.tcmis.common.framework.BaseDataBean;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PossUploadInputBean {
  private File theFile;
  private String fileName;
  private String uploadFile;

  public PossUploadInputBean() {
  }

  //setter
  public void setTheFile(File file) {
    this.theFile = file;
  }
  public void setFileName(String fileName) {
    if(fileName != null) {
      this.fileName = fileName.trim();
    }else {
      this.fileName = fileName;
    }
  }
  public void setUploadFile(String uploadFile) {
    this.uploadFile = uploadFile;
  }

  //getter
  public File getTheFile() {
    return this.theFile;
 }
 public String getFileName() {
   return this.fileName;
 }
 public String getUploadFile() {
   return this.uploadFile;
 }

} //end of class