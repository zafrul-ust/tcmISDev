package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ACCOUNT_SYS_HEADERS_VIEW <br>
 * @version: 1.0, March 25, 2011 <br>
 *****************************************************************************/


public class WorkAreaCountBean extends BaseDataBean {

	private String countType;
	private String countTypeDesc;
	private String jspLabel;
	private BigDecimal cabinetStartPredateBlock;

	//constructor
	public WorkAreaCountBean() {
	}

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public String getCountTypeDesc() {
        return countTypeDesc;
    }

    public void setCountTypeDesc(String countTypeDesc) {
        this.countTypeDesc = countTypeDesc;
    }

    public String getJspLabel() {
        return jspLabel;
    }

    public void setJspLabel(String jspLabel) {
        this.jspLabel = jspLabel;
    }

	public BigDecimal getCabinetStartPredateBlock() {
		return cabinetStartPredateBlock;
	}

	public void setCabinetStartPredateBlock(BigDecimal cabinetStartPredateBlock) {
		this.cabinetStartPredateBlock = cabinetStartPredateBlock;
	}
}