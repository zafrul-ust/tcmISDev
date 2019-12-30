package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogAddSpecBean <br>
 *****************************************************************************/

public class CatalogAddSpecViewBean extends BaseDataBean {

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
	private String itar;
	private BigDecimal lineItem;
    //this column contains where the spec is coming from
    //catalog_add_spec, catalog_add_spec_qc, spec
    private String specSource;
    //this is for spec_library
    private String specLibraryDesc;
    private String specLibraryPath;
    private String global;
    private String specDetail;
    //this column contains which table data is coming from in
    //CATALOG_ADD_SPEC_VW
    //catalog_add_spec or catalog_add_spec_qc
    private String dataSource;

    //constructor
	public CatalogAddSpecViewBean() {
	}

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

	public String getItar() {
		return itar;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public String getSpecSource() {
		return specSource;
	}

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

	public void setItar(String itar) {
		this.itar = itar;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setSpecSource(String specSource) {
		this.specSource = specSource;
	}

    public String getSpecLibraryDesc() {
        return specLibraryDesc;
    }

    public void setSpecLibraryDesc(String specLibraryDesc) {
        this.specLibraryDesc = specLibraryDesc;
    }

    public String getSpecLibraryPath() {
        return specLibraryPath;
    }

    public void setSpecLibraryPath(String specLibraryPath) {
        this.specLibraryPath = specLibraryPath;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getOnLine() {
        return onLine;
    }

    public void setOnLine(String onLine) {
        this.onLine = onLine;
    }

    public String getSpecDetail() {
        return specDetail;
    }

    public void setSpecDetail(String specDetail) {
        this.specDetail = specDetail;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}