package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: RecipeIngredientSearchViewBean <br>
 * @version: 1.0, Sep 25, 2007 <br>
 *****************************************************************************/

public class RecipeIngredientSearchViewBean
    extends BaseDataBean {

  private String catPartNo;
  private BigDecimal itemId;
  private String materialDesc;
  private String grade;
  private String mfgDesc;
  private String searchString;
  private String companyId;
  private String catalogId;
  private String catalogCompanyId;
  private BigDecimal partGroupNo;

  //constructor
  public RecipeIngredientSearchViewBean() {
  }

  //setters
  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setMaterialDesc(String materialDesc) {
    this.materialDesc = materialDesc;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public void setMfgDesc(String mfgDesc) {
    this.mfgDesc = mfgDesc;
  }

  public void setSearchString(String searchString) {
    this.searchString = searchString;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCatalogCompanyId(String catalogCompanyId) {
    this.catalogCompanyId = catalogCompanyId;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }
  //getters
  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getMaterialDesc() {
    return materialDesc;
  }

  public String getGrade() {
    return grade;
  }

  public String getMfgDesc() {
    return mfgDesc;
  }

  public String getSearchString() {
    return searchString;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getCatalogCompanyId() {
    return catalogCompanyId;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }
}