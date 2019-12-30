package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

public class KitIndexingQueueViewBean {

	private boolean ok;
	private String customerMixtureNumber;
	private String mixtureDesc;
	private BigDecimal mixtureVoc;
	private String mixtureVocDetect;
	private BigDecimal mixtureVocLower;
	private BigDecimal mixtureVocUpper;
	private String mixtureVocUnit;
	private String mixtureVocSource;
	private BigDecimal mixtureVocLwes;
	private String mixtureVocLwesDetect;
	private BigDecimal mixtureVocLwesLower;
	private BigDecimal mixtureVocLwesUpper;
	private String mixtureVocLwesUnit;
	private String mixtureVocLwesSource;
	private String mixturePhysicalState;
	private String mixturePhysicalStateSource;
	private BigDecimal materialId;
	private String materialDesc;
	private Date componentRevDate;
	private Date newRevisionDate;
	private boolean asMixed;
	private String customerMsdsDb;
	private String mfgDesc;
	private String tradeName;
	private String companyName;
    private BigDecimal mixtureId;

    public boolean isOk() {
		return ok;
	}
	
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}
	
	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		this.customerMixtureNumber = customerMixtureNumber;
	}
	
	public String getMixtureDesc() {
		return mixtureDesc;
	}
	
	public void setMixtureDesc(String mixtureDesc) {
		this.mixtureDesc = mixtureDesc;
	}
	
	public BigDecimal getMixtureVoc() {
		return mixtureVoc;
	}
	
	public void setMixtureVoc(BigDecimal mixtureVoc) {
		this.mixtureVoc = mixtureVoc;
	}
	
	public String getMixtureVocDetect() {
		return mixtureVocDetect;
	}
	
	public void setMixtureVocDetect(String mixtureVocDetect) {
		this.mixtureVocDetect = mixtureVocDetect;
	}
	
	public BigDecimal getMixtureVocLower() {
		return mixtureVocLower;
	}
	
	public void setMixtureVocLower(BigDecimal mixtureVocLower) {
		this.mixtureVocLower = mixtureVocLower;
	}
	
	public BigDecimal getMixtureVocUpper() {
		return mixtureVocUpper;
	}
	
	public void setMixtureVocUpper(BigDecimal mixtureVocUpper) {
		this.mixtureVocUpper = mixtureVocUpper;
	}
	
	public String getMixtureVocUnit() {
		return mixtureVocUnit;
	}
	
	public void setMixtureVocUnit(String mixtureVocUnit) {
		this.mixtureVocUnit = mixtureVocUnit;
	}
	
	public String getMixtureVocSource() {
		return mixtureVocSource;
	}
	
	public void setMixtureVocSource(String mixtureVocSource) {
		this.mixtureVocSource = mixtureVocSource;
	}
	
	public BigDecimal getMixtureVocLwes() {
		return mixtureVocLwes;
	}
	
	public void setMixtureVocLwes(BigDecimal mixtureVocLwes) {
		this.mixtureVocLwes = mixtureVocLwes;
	}
	
	public String getMixtureVocLwesDetect() {
		return mixtureVocLwesDetect;
	}
	
	public void setMixtureVocLwesDetect(String mixtureVocLwesDetect) {
		this.mixtureVocLwesDetect = mixtureVocLwesDetect;
	}
	
	public BigDecimal getMixtureVocLwesLower() {
		return mixtureVocLwesLower;
	}
	
	public void setMixtureVocLwesLower(BigDecimal mixtureVocLwesLower) {
		this.mixtureVocLwesLower = mixtureVocLwesLower;
	}
	
	public BigDecimal getMixtureVocLwesUpper() {
		return mixtureVocLwesUpper;
	}
	
	public void setMixtureVocLwesUpper(BigDecimal mixtureVocLwesUpper) {
		this.mixtureVocLwesUpper = mixtureVocLwesUpper;
	}
	
	public String getMixtureVocLwesUnit() {
		return mixtureVocLwesUnit;
	}
	
	public void setMixtureVocLwesUnit(String mixtureVocLwesUnit) {
		this.mixtureVocLwesUnit = mixtureVocLwesUnit;
	}
	
	public String getMixtureVocLwesSource() {
		return mixtureVocLwesSource;
	}
	
	public void setMixtureVocLwesSource(String mixtureVocLwesSource) {
		this.mixtureVocLwesSource = mixtureVocLwesSource;
	}
	
	public String getMixturePhysicalState() {
		return mixturePhysicalState;
	}
	
	public void setMixturePhysicalState(String mixturePhysicalState) {
		this.mixturePhysicalState = mixturePhysicalState;
	}
	
	public String getMixturePhysicalStateSource() {
		return mixturePhysicalStateSource;
	}
	
	public void setMixturePhysicalStateSource(String mixturePhysicalStateSource) {
		this.mixturePhysicalStateSource = mixturePhysicalStateSource;
	}
	
	public BigDecimal getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	
	public String getMaterialDesc() {
		return materialDesc;
	}
	
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	
	public Date getComponentRevDate() {
		return componentRevDate;
	}
	
	public void setComponentRevDate(Date componentRevDate) {
		this.componentRevDate = componentRevDate;
	}

	public Date getNewRevisionDate() {
		return newRevisionDate;
	}

	public void setNewRevisionDate(Date newRevisionDate) {
		this.newRevisionDate = newRevisionDate;
	}

	public boolean isAsMixed() {
		return asMixed;
	}

	public void setAsMixed(boolean asMixed) {
		this.asMixed = asMixed;
	}

	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}

	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

    public BigDecimal getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(BigDecimal mixtureId) {
        this.mixtureId = mixtureId;
    }
}
