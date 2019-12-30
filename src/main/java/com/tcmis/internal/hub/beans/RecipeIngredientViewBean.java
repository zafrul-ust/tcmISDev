package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: RecipeIngredientViewBean <br>
 * @version: 1.0, Sep 27, 2007 <br>
 *****************************************************************************/

public class RecipeIngredientViewBean
    extends BaseDataBean {

  private String companyId;
  private BigDecimal recipeId;
  private String catPartNo;
  private BigDecimal amount;
  private String amountUnit;
  private String partDescription;
  private String catalogId;
  private String catalogCompanyId;
  private BigDecimal partGroupNo;

  //constructor
  public RecipeIngredientViewBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setRecipeId(BigDecimal recipeId) {
    this.recipeId = recipeId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setAmountUnit(String amountUnit) {
    this.amountUnit = amountUnit;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
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
  public String getCompanyId() {
    return companyId;
  }

  public BigDecimal getRecipeId() {
    return recipeId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getAmountUnit() {
    return amountUnit;
  }

  public String getPartDescription() {
    return partDescription;
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