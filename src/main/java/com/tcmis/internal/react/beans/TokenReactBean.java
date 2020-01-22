package com.tcmis.internal.react.beans;

import com.tcmis.common.framework.BaseDataBean;

public class TokenReactBean extends BaseDataBean {

    private int userId;
    private String module;
    private String companyId;
    private String schema;
    private int timeout;
    private boolean valid;
    private String message;

    public TokenReactBean() {
    }

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
    }

    public String getModule() {
	return module;
    }

    public void setModule(String module) {
	this.module = module;
    }

    public String getCompanyId() {
	return companyId;
    }

    public void setCompanyId(String companyId) {
	this.companyId = companyId;
    }

    public String getSchema() {
	return schema;
    }

    public void setSchema(String schema) {
	this.schema = schema;
    }

    public int getTimeout() {
	return timeout;
    }

    public void setTimeout(int timeout) {
	this.timeout = timeout;
    }

    public boolean isValid() {
	return valid;
    }

    public void setValid(boolean valid) {
	this.valid = valid;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

}