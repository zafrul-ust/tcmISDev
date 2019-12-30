package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoLineExpediteDateBean <br>
 * @version: 1.0, Aug 13, 2008 <br>
 *****************************************************************************/


public class PoLineExpediteDateBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private Date dateEntered;
	private Date revisedPromisedDate;
	private String comments;
    private Date oldRevisedDate;
    private String oldComments;
    private String ok;
    private String inventoryGroup;
    private BigDecimal enteredBy;
    private String creditHold;
    
    public String getCreditHold() {
		return creditHold;
	}

	public void setCreditHold(String creditHold) {
		this.creditHold = creditHold;
	}

    //constructor
	public PoLineExpediteDateBean() {
	}

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public Date getOldRevisedDate() {
        return oldRevisedDate;
    }

    public void setOldRevisedDate(Date oldRevisedDate) {
        this.oldRevisedDate = oldRevisedDate;
    }

    public String getOldComments() {
        return oldComments;
    }

    public void setOldComments(String oldComments) {
        this.oldComments = oldComments;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
    //setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}
	public void setRevisedPromisedDate(Date revisedPromisedDate) {
		this.revisedPromisedDate = revisedPromisedDate;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public Date getDateEntered() {
		return dateEntered;
	}
	public Date getRevisedPromisedDate() {
		return revisedPromisedDate;
	}
	public String getComments() {
		return comments;
	}

	/**
	 * @param enteredBy the enteredBy to set
	 */
	public void setEnteredBy(BigDecimal enteredBy) {
		this.enteredBy = enteredBy;
	}

	/**
	 * @return the enteredBy
	 */
	public BigDecimal getEnteredBy() {
		return enteredBy;
	}
}