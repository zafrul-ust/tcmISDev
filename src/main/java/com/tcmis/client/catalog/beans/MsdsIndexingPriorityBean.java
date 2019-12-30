package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MsdsIndexingPriorityBean <br>
 * @version: 1.0, Dec 5, 2006 <br>
 *****************************************************************************/

public class MsdsIndexingPriorityBean extends BaseDataBean {

    private BigDecimal msdsIndexingPriorityId;
    private String msdsIndexingPriorityDesc;
    private BigDecimal catalogAddOrder;
    private BigDecimal startupOrder;
    private BigDecimal newRevisionOrder;

    //constructor
    public MsdsIndexingPriorityBean() {
    }

    public BigDecimal getMsdsIndexingPriorityId() {
        return msdsIndexingPriorityId;
    }

    public void setMsdsIndexingPriorityId(BigDecimal msdsIndexingPriorityId) {
        this.msdsIndexingPriorityId = msdsIndexingPriorityId;
    }

    public String getMsdsIndexingPriorityDesc() {
        return msdsIndexingPriorityDesc;
    }

    public void setMsdsIndexingPriorityDesc(String msdsIndexingPriorityDesc) {
        this.msdsIndexingPriorityDesc = msdsIndexingPriorityDesc;
    }

    public BigDecimal getCatalogAddOrder() {
        return catalogAddOrder;
    }

    public void setCatalogAddOrder(BigDecimal catalogAddOrder) {
        this.catalogAddOrder = catalogAddOrder;
    }

    public BigDecimal getStartupOrder() {
        return startupOrder;
    }

    public void setStartupOrder(BigDecimal startupOrder) {
        this.startupOrder = startupOrder;
    }

    public BigDecimal getNewRevisionOrder() {
        return newRevisionOrder;
    }

    public void setNewRevisionOrder(BigDecimal newRevisionOrder) {
        this.newRevisionOrder = newRevisionOrder;
    }
}