package com.tcmis.client.report.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvMaterialSubcategoryBean <br>
 * @version: 1.0, Mar 5, 2013 <br>
 *****************************************************************************/


public class VvMaterialCategoryBean extends BaseDataBean {

	private String companyId;
	private String catalogCompanyId;
	private String catalogId;
	private String materialCategoryName;
	private BigDecimal materialCategoryId;
	private String solvent;
	private String diluent;
	private String jspLabel;

	//constructor
	public VvMaterialCategoryBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setMaterialCategoryName(String materialCategoryName) {
		this.materialCategoryName = materialCategoryName;
	}

	public void setMaterialCategoryId(BigDecimal materialCategoryId) {
		this.materialCategoryId = materialCategoryId;
	}
	
	public void setSolvent(String solvent) {
		this.solvent = solvent;
	}
	public void setDiluent(String diluent) {
		this.diluent = diluent;
	}
	public void setJspLabel(String jspLabel) {
		this.jspLabel = jspLabel;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getMaterialCategoryName() {
		return materialCategoryName;
	}
	public BigDecimal getMaterialCategoryId() {
		return materialCategoryId;
	}
	
	public String getSolvent() {
		return solvent;
	}

	public String getDiluent() {
		return diluent;
	}

	public String getJspLabel() {
		return jspLabel;
	}

}