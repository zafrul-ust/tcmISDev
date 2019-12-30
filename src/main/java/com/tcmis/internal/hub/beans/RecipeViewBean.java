package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: Customer.recipeViewBean <br>
 * @version: 1.0, Sep 25, 2007 <br>
 *****************************************************************************/

public class RecipeViewBean
    extends BaseDataBean {

  private BigDecimal recipeId;
  private String companyId;
  private String catalogId;
  private String recipeName;
  private String recipeDescription;
  private BigDecimal itemId;
  private String itemDesc;
  private BigDecimal yieldAmount;
  private String yieldAmountUnit;
  private Date recipeQcDate;
  private BigDecimal recipeQcUserId;
  private String recipeQcUsername;
  private Date inactivationDate;
  private String instructions;

  //constructor
  public RecipeViewBean() {
  }

  //setters
  public void setRecipeId(BigDecimal recipeId) {
    this.recipeId = recipeId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }

  public void setRecipeDescription(String recipeDescription) {
    this.recipeDescription = recipeDescription;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setItemDesc(String itemDesc) {
    this.itemDesc = itemDesc;
  }

  public void setYieldAmount(BigDecimal yieldAmount) {
    this.yieldAmount = yieldAmount;
  }

  public void setYieldAmountUnit(String yieldAmountUnit) {
    this.yieldAmountUnit = yieldAmountUnit;
  }

  public void setRecipeQcDate(Date recipeQcDate) {
    this.recipeQcDate = recipeQcDate;
  }

  public void setRecipeQcUserId(BigDecimal recipeQcUserId) {
    this.recipeQcUserId = recipeQcUserId;
  }

  public void setRecipeQcUsername(String recipeQcUsername) {
    this.recipeQcUsername = recipeQcUsername;
  }

  public void setInactivationDate(Date inactivationDate) {
    this.inactivationDate = inactivationDate;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  //getters
  public BigDecimal getRecipeId() {
    return recipeId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getRecipeName() {
    return recipeName;
  }

  public String getRecipeDescription() {
    return recipeDescription;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getItemDesc() {
    return itemDesc;
  }

  public BigDecimal getYieldAmount() {
    return yieldAmount;
  }

  public String getYieldAmountUnit() {
    return yieldAmountUnit;
  }

  public Date getRecipeQcDate() {
    return recipeQcDate;
  }

  public BigDecimal getRecipeQcUserId() {
    return recipeQcUserId;
  }

  public String getRecipeQcUsername() {
    return recipeQcUsername;
  }

  public Date getInactivationDate() {
    return inactivationDate;
  }

  public String getInstructions() {
    return instructions;
  }
}