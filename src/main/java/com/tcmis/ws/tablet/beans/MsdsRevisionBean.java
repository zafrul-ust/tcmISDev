package com.tcmis.ws.tablet.beans;

import java.util.Date;

public class MsdsRevisionBean extends BaseTabletBean {

	private String	description;
	private Date	revisionDate;

	public String getDescription() {
		return description;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;

	}
}