/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.tcmis.ws.scanner.beans;

import com.tcmis.common.util.DateHandler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Vector;
import java.util.TimeZone;

public class PersonnelBean {
	String status;
	String reason;
	String logonId;
	String password;
	String token; 
	String tokenValidUntil; 
	String companyId;
	BigDecimal personnelId=null;
    Vector<Facility> facilityInfo= new Vector();
    String version;
    Date timeLastLoaded;

    public Vector<Facility> getFacilityInfo() {
		return facilityInfo;
	}
	public String getLogonId() {
		return logonId;
	}
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setPersonnelIdStr(String s) {
		if( s != null && s.length() !=0 )
			this.personnelId = new BigDecimal(s);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void addFacilityBean(Facility orderLineBean) {
        this.facilityInfo.add(orderLineBean);
    }
	public void setFacilityInfo(Vector<Facility> facilityInfo) {
		this.facilityInfo = facilityInfo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenValidUntil() {
		return tokenValidUntil;
	}
	public void setTokenValidUntil(String tokenValidUntil) {
		this.tokenValidUntil = tokenValidUntil;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getTimeLastLoaded() {
        return timeLastLoaded;
    }

    public void setTimeLastLoaded(String timeLastLoadedStr) {
        try {
            this.timeLastLoaded = DateHandler.getDateFromString("yyyy-MM-dd'T'HH:mm:ss",timeLastLoadedStr, TimeZone.getTimeZone("GMT"));
        }catch(Exception ex){ex.printStackTrace();}
    }
}