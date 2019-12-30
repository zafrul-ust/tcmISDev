package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: CatalogAddSpecBean <br>
 * @version: 1.0, Jun 23, 2010 <br>
 *****************************************************************************/

public class CatalogAddSpecBean extends BaseDataBean {

	private String specId;
 	private BigDecimal requestId;
 	private String specName;
	private String specTitle;
	private String specVersion;
	private String specAmendment;
	private Date specDate;
	private String content;
	private String onLine;
	private String companyId;
	private String specLibrary;
	private String coc;
	private String coa;
	private String dataSource;
	private String calledFrom;
    private String displayStatus;
    private BigDecimal lineItem;
    private String oldSpecId;
    //the reason for this column is because I messed up
    //should of asked to be called data_source
    //note: dataSource and specSource are the same
    private String specSource;
    private String itar;

     //constructor
	public CatalogAddSpecBean() {
 	}

 	//setters
	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public void setSpecTitle(String specTitle) {
		this.specTitle = specTitle;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	public void setSpecAmendment(String specAmendment) {
		this.specAmendment = specAmendment;
	}

	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}

	public void setCoc(String coc) {
		this.coc = coc;
	}

	public void setCoa(String coa) {
		this.coa = coa;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setCalledFrom(String calledFrom) {
		this.calledFrom = calledFrom;
	}

	//getters
	public String getSpecId() {
		return specId;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public String getSpecName() {
		return specName;
	}

	public String getSpecTitle() {
		return specTitle;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public String getSpecAmendment() {
		return specAmendment;
	}

	public Date getSpecDate() {
		return specDate;
	}

	public String getContent() {
		return content;
	}

	public String getOnLine() {
		return onLine;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getSpecLibrary() {
		return specLibrary;
	}

	public String getCoc() {
		return coc;
	}

	public String getCoa() {
		return coa;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getCalledFrom() {
		return calledFrom;
	}

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public BigDecimal getLineItem() {
        return lineItem;
    }

    public void setLineItem(BigDecimal lineItem) {
        this.lineItem = lineItem;
    }

    public String getOldSpecId() {
        return oldSpecId;
    }

    public void setOldSpecId(String oldSpecId) {
        this.oldSpecId = oldSpecId;
    }

    public String getSpecSource() {
        return specSource;
    }

    public void setSpecSource(String specSource) {
        this.specSource = specSource;
    }

    public String getItar() {
        return itar;
    }

    public void setItar(String itar) {
        this.itar = itar;
    }
}