package com.tcmis.internal.react.beans;

import com.tcmis.common.framework.BaseDataBean;

public class LoginReactBean extends BaseDataBean {

	private String logonId;
	private String password;
	private String langSetting;
	private String search;
	private String home;
	private String requestedPage;

	public String getLogonId() {
		return logonId;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLangSetting() {
		return langSetting;
	}

	public void setLangSetting(String langSetting) {
		this.langSetting = langSetting;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getRequestedPage() {
		return requestedPage;
	}

	public void setRequestedPage(String requestedPage) {
		this.requestedPage = requestedPage;
	}

}