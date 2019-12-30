package com.tcmis.client.usgov.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UsgovTcnToMsdsViewBean <br>
 * @version: 1.0, May 20, 2008 <br>
 *****************************************************************************/


public class UsgovTcnToMsdsViewBean extends BaseDataBean {

	private String usgovTcn;
	private BigDecimal materialId;
	private Date revisionDate;
	private String url;
    private String shipmentId;    
    private String mfgLiteratureContent;
    private BigDecimal prNumber;
    private String lineItem;

    public String getMfgLiteratureContent() {
        return mfgLiteratureContent;
    }

    public void setMfgLiteratureContent(String mfgLiteratureContent) {
        this.mfgLiteratureContent = mfgLiteratureContent;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    //constructor
	public UsgovTcnToMsdsViewBean() {
	}

	//setters
	public void setUsgovTcn(String usgovTcn) {
		this.usgovTcn = usgovTcn;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	//getters
	public String getUsgovTcn() {
		return usgovTcn;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public String getUrl() {
		return url;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
}