package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

import antlr.StringUtils;
import radian.tcmis.common.util.StringHandler;

@SuppressWarnings("serial")
public class MaterialAffectedNotification extends BaseDataBean {

	private boolean grab;
	private BigDecimal materialId;
	private String materialDesc;
	private String materialDescExtension;
	private String tradeName;
	private String tradeNameExtension;
	private String productCode;
	private String localeCode;
	private String localeDisplay;
	private String localeMaterialDesc;
	private String localeMaterialDescExtension;
	private String localeTradeName;
	private String localeTradeNameExtension;
	private String pageUploadCode;
	private BigDecimal mfgId;

	public boolean isGrab() {
		return grab;
	}
	public void setGrab(boolean grab) {
		this.grab = grab;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public String getMaterialDesc() {
		return getExtendedString(materialDesc, materialDescExtension);
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getTradeName() {
		return getExtendedString(tradeName, tradeNameExtension);
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getLocaleDisplay() {
		return localeDisplay;
	}
	public void setLocaleDisplay(String localeDisplay) {
		this.localeDisplay = localeDisplay;
	}
	public String getLocaleMaterialDesc() {
		return getExtendedString(localeMaterialDesc, localeMaterialDescExtension);
	}
	public void setLocaleMaterialDesc(String localeMaterialDesc) {
		this.localeMaterialDesc = localeMaterialDesc;
	}
	public String getLocaleTradeName() {
		return getExtendedString(localeTradeName, localeTradeNameExtension);
	}
	public void setLocaleTradeName(String localeTradeName) {
		this.localeTradeName = localeTradeName;
	}
	public String getPageUploadCode() {
		return pageUploadCode;
	}
	public void setPageUploadCode(String pageUploadCode) {
		this.pageUploadCode = pageUploadCode;
	}
	public String getMaterialDescExtension() {
		return materialDescExtension;
	}
	public void setMaterialDescExtension(String materialDescExtension) {
		this.materialDescExtension = materialDescExtension;
	}
	public String getTradeNameExtension() {
		return tradeNameExtension;
	}
	public void setTradeNameExtension(String tradeNameExtension) {
		this.tradeNameExtension = tradeNameExtension;
	}
	public String getLocaleMaterialDescExtension() {
		return localeMaterialDescExtension;
	}
	public void setLocaleMaterialDescExtension(String localeMaterialDescExtension) {
		this.localeMaterialDescExtension = localeMaterialDescExtension;
	}
	public String getLocaleTradeNameExtension() {
		return localeTradeNameExtension;
	}
	public void setLocaleTradeNameExtension(String localeTradeNameExtension) {
		this.localeTradeNameExtension = localeTradeNameExtension;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	private String getExtendedString(String orig, String ext) {
		StringBuilder builder = new StringBuilder();
		if ( ! StringHandler.isBlankString(ext)) {
			if ( ! StringHandler.isBlankString(orig)) {
				builder.append(orig).append(" ").append(ext);
			}
			else {
				builder.append(ext);
			}
		}
		else if ( ! StringHandler.isBlankString(orig)) {
			builder.append(orig);
		}
		return builder.toString();
	}
}
