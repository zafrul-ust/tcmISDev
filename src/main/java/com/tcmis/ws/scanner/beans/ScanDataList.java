package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Vector;

import com.tcmis.ws.scanner.beans.ScanDataBean;

public class ScanDataList {
	Vector<ScanDataBean> scanData = new Vector();
	BigDecimal personnelId = null;
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void addScanDataBean(ScanDataBean orderLineBean) {
        this.scanData.add(orderLineBean);
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
	public Vector<ScanDataBean> getScanData() {
		return scanData;
	}

	public void setScanData(Vector<ScanDataBean> scanData) {
		this.scanData = scanData;
	}


}
