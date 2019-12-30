package com.tcmis.client.aerojet.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class DateTimeBean extends BaseDataBean {

	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 1L;
	private String day;
	private String month;
	private String year;
	private String hour;
	private String minute;
	private String second;
	private String qualifier;
	//private SimpleDateFormat formaterDateTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	//private SimpleDateFormat formaterDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public Date getDateTime(SimpleDateFormat format) {
		Date dt = null;
		try {				
			String dateInString = this.month + "/" + this.day + "/" + this.year + " " +
				this.hour + ":" + this.minute + ":" + this.second;
			if(dateInString.trim().length() > 5 ) {
				dt = format.parse(dateInString);
			}
		} catch (Exception e ) {
			dt = null;
		}
		return dt;
	}
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	
}
