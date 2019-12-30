package com.tcmis.supplier.wbuy.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;


/******************************************************************************
 * CLASSNAME: InternalSpecsBean <br>
 * @version: 1.0, Sep 9, 2005 <br>
 *****************************************************************************/


public class InternalSpecsBean extends BaseDataBean {

	private String specId;
	private String specIdDisplay;
	private String detail;
	//private String companyId;
	private String specLibraryDesc;
	private String content;
	private String onLine;
        private String currentCocRequirement;
        private String currentCoaRequirement;
	private String savedCoc;
	private String savedCoa;
        private String cocReqAtSave;
        private String coaReqAtSave;
	//private String attach;
	//private String ok;
	//private BigDecimal itemId;
	private String specLibrary;
	private BigDecimal color;
        private BigDecimal colorAtSave;
	//private String certRequiredCurrent;
	//private String certRequiredAtSave;
	//private Date dateLastChanged;
	//private String status;

    public void setSavedCoc(String savedCoc) {
        this.savedCoc = savedCoc;
    }

    public void setSavedCoa(String savedCoa) {
        this.savedCoa = savedCoa;
    }

    //constructor
	public InternalSpecsBean() {
	}

	//setters
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	public void setSpecIdDisplay(String specIdDisplay) {
		this.specIdDisplay = specIdDisplay;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
//	public void setCompanyId(String companyId) {
//		this.companyId = companyId;
//	}
	public void setSpecLibraryDesc(String specLibraryDesc) {
		this.specLibraryDesc = specLibraryDesc;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
        
	public void setCurrentCocRequirement(String currentCocRequirement) {
		this.currentCocRequirement = currentCocRequirement;
	}
	public void setCurrentCoaRequirement(String currentCoaRequirement) {
		this.currentCoaRequirement = currentCoaRequirement;
	}
        
	public void setCoc(String savedCoc) {
		this.savedCoc = savedCoc;
	}
	public void setCoa(String savedCoa) {
		this.savedCoa = savedCoa;
	}
        
	public void setCocReqAtSave(String cocReqAtSave) {
		this.cocReqAtSave = cocReqAtSave;
	}
	public void setCoaReqAtSave(String coaReqAtSave) {
		this.coaReqAtSave = coaReqAtSave;
	}
        
//	public void setAttach(String attach) {
//		this.attach = attach;
//	}
//	public void setOk(String ok) {
//		this.ok = ok;
//	}
//	public void setItemId(BigDecimal itemId) {
//		this.itemId = itemId;
//	}
	public void setSpecLibrary(String specLibrary) {
		this.specLibrary = specLibrary;
	}
	public void setColor(BigDecimal color) {
		this.color = color;
	}
	public void setColorAtSave(BigDecimal colorAtSave) {
		this.colorAtSave = colorAtSave;
	}
        
//	public void setCertRequiredCurrent(String certRequiredCurrent) {
//		this.certRequiredCurrent = certRequiredCurrent;
//	}
//	public void setCertRequiredAtSave(String certRequiredAtSave) {
//		this.certRequiredAtSave = certRequiredAtSave;
//	}
//	public void setDateLastChanged(Date dateLastChanged) {
//		this.dateLastChanged = dateLastChanged;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}


	//getters
	public String getSpecId() {
		return specId;
	}
	public String getSpecIdDisplay() {
		return specIdDisplay;
	}
	public String getDetail() {
		return detail;
	}
//	public String getCompanyId() {
//		return companyId;
//	}
	public String getSpecLibraryDesc() {
		return specLibraryDesc;
	}
	public String getContent() {
		return content;
	}
	public String getOnLine() {
		return onLine;
	}                
	public String getCurrentCocRequirement() {
		return currentCocRequirement;
	}
	public String getCurrentCoaRequirement() {
		return currentCoaRequirement;
	}
	public String getSavedCoc() {
		return savedCoc;
	}
	public String getSavedCoa() {
		return savedCoa;
	}
  
	public String getCocReqAtSave() {
		return cocReqAtSave;
	}
	public String getCoaReqAtSave() {
		return coaReqAtSave;
	}

//	public String getAttach() {
//		return attach;
//	}
//	public String getOk() {
//		return ok;
//	}
//	public BigDecimal getItemId() {
//		return itemId;
//	}
	public String getSpecLibrary() {
		return specLibrary;
	}
	public BigDecimal getColor() {
		return color;
	}
	public BigDecimal getColorAtSave() {
		return colorAtSave;
	}
//	public String getCertRequiredCurrent() {
//		return certRequiredCurrent;
//	}
//	public String getCertRequiredAtSave() {
//		return certRequiredAtSave;
//	}
//	public Date getDateLastChanged() {
//		return dateLastChanged;
//	}
//	public String getStatus() {
//		return status;
//	}
}