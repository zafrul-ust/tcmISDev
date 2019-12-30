package com.tcmis.internal.report.beans;

import org.apache.commons.lang.StringUtils;
import com.tcmis.common.framework.BaseDataBean;

public class Address extends BaseDataBean {
	private String	line1;
	private String	line2;
	private String	line3;
	private String	line4;
	private String	line5;
	private String	location;

	public String getAddress() {
		StringBuilder address = new StringBuilder(line1);
		if (StringUtils.isNotBlank(line2)) {
			address.append("<BR>\n").append(line2);
		}
		if (StringUtils.isNotBlank(line3)) {
			address.append("<BR>\n").append(line3);
		}
		if (StringUtils.isNotBlank(line4)) {
			address.append("<BR>\n").append(line4);
		}
		if (StringUtils.isNotBlank(line5)) {
			address.append("<BR>\n").append(line5);
		}
		return address.toString();
	}
	public String getLine1() {
		return line1;
	}

	public String getLine2() {
		return line2;
	}

	public String getLine3() {
		return line3;
	}

	public String getLine4() {
		return line4;
	}

	public String getLine5() {
		return line5;
	}

	public String getLocation() {
		return location;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public void setLine4(String line4) {
		this.line4 = line4;
	}

	public void setLine5(String line5) {
		this.line5 = line5;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
