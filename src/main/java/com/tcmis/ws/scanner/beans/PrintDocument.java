package com.tcmis.ws.scanner.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class PrintDocument extends BaseDataBean {
	private String documentUrl;
	private String documentType;
	
	private static Pattern urlPattern = Pattern.compile("(https?://[^/]+)?/(.*)");

	// constructor
	public PrintDocument() {
	}

	public String getDocumentUrl() {
		return this.documentUrl;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getFilename() {
		if (StringUtils.isBlank(this.documentUrl)) {
			return "";
		}
		Matcher matcher = urlPattern.matcher(this.documentUrl);
		if (matcher.find()) {
			return "/webdata/html/" + matcher.group(2);
		}
		else {
			return "/webdata/html/" + this.documentUrl;
		}
	}

}