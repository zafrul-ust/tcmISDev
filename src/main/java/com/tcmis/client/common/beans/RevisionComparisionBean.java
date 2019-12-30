package com.tcmis.client.common.beans;

import java.util.Date;
import com.tcmis.common.framework.BaseDataBean;

public class RevisionComparisionBean extends BaseDataBean {
	
	private Date revisionDate;
    private String localeDisplay;

    //constructor
	public RevisionComparisionBean() {
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

    public String getLocaleDisplay() {
        return localeDisplay;
    }

    public void setLocaleDisplay(String localeDisplay) {
        this.localeDisplay = localeDisplay;
    }
}
