package com.tcmis.ws.tablet.beans;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

/******************************************************************************
 * CLASSNAME: InboundShipmentBean <br>
 * 
 * @version: 1.0, Mar 5, 2013 <br>
 *****************************************************************************/

public class TimestampBean extends BaseTabletBean {

	private Date	date;

	// constructor
	public TimestampBean() {
	}

	public TimestampBean(ActionForm form, Locale tcmISLocale) {
		super(form, tcmISLocale);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}