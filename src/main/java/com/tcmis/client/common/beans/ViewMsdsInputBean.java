package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.io.File;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ViewMsdsInputBean <br>
 * @version: 1.0, July 19, 2011 <br>
 *****************************************************************************/

public class ViewMsdsInputBean extends BaseDataBean {

  private BigDecimal materialId;
  private BigDecimal itemId;
  private String facility;
  private String client;
  private String localeCode;
  private String revisionDate;
  private String name;
  private String phone;
  private String email;
  private String comments;
  private File theFile;
  private String fileName;
  private String revisionDateFormat;
  private String revisionDateTimeStamp;
  private String content;
  private BigDecimal documentId;

  //constructor
  public ViewMsdsInputBean() {
  }
  
  public String getRevisionDateTimeStamp() {
		return revisionDateTimeStamp;
	}


	public void setRevisionDateTimeStamp(String revisionDateTimeStamp) {
		this.revisionDateTimeStamp = revisionDateTimeStamp;
	}

  public void setFileName(String fileName) {
	    if (fileName != null && this.doTrim) {
	      this.fileName = fileName.trim();
	    }
	    else {
	      this.fileName = fileName;
	    }
  }
  
  public String getFileName() {
	    return this.fileName;
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


public BigDecimal getItemId() {
	return itemId;
}

public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
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


public File getTheFile() {
	return theFile;
}


public void setTheFile(File theFile) {
	this.theFile = theFile;
}

public String getClient() {
	return client;
}

public void setClient(String client) {
	this.client = client;
}

    public String getRevisionDateFormat() {
        return revisionDateFormat;
    }

    public void setRevisionDateFormat(String revisionDateFormat) {
        this.revisionDateFormat = revisionDateFormat;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getDocumentId() {
        return documentId;
    }

    public void setDocumentId(BigDecimal documentId) {
        this.documentId = documentId;
    }
}