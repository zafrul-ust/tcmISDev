package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: RecipeInputBean <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/

public class RecipeInputBean
    extends BaseDataBean {

  private String submitSave;
  private String submitApprove;

  private BigDecimal personnelId;
  private BigDecimal recipeId;
  private String companyId;
  private String catalogId;
  private String recipeName;
  private String recipeDescription;
  private BigDecimal itemId;
  private String itemDesc;
  private BigDecimal yieldAmount;
  private String yieldAmountUnit;
  private Date inactivationDate;
  private String instructions;

  private String inactive;
  private String approved;

  private String partNumber;
  private BigDecimal partGroupNo;
  private String partDescription;
  private BigDecimal ingredientAmount;
  private String ingredientUnit;
  private String deleteIngredient;

  private String testParameter;
  private String testParameterUnit;
  private String deleteTestParameter;

  //constructor
  public RecipeInputBean() {
  }

  //setters
  public void setSubmitSave(String s) {
    this.submitSave = s;
  }

  public void setSubmitApprove(String s) {
    this.submitApprove = s;
  }

  public void setPersonnelId(BigDecimal b) {
    this.personnelId = b;
  }

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

  public void setInactivationDate(Date inactivationDate) {
    this.inactivationDate = inactivationDate;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public void setInactive(String inactive) {
    this.inactive = inactive;
  }

  public void setApproved(String s) {
    this.approved = s;
  }

  public void setPartNumber(String s) {
    this.partNumber = s;
  }
  public void setPartGroupNo(BigDecimal b) {
    this.partGroupNo = b;
  }
  public void setPartDescription(String s) {
    this.partDescription = s;
  }
  public void setIngredientAmount(BigDecimal b) {
    this.ingredientAmount = b;
  }
  public void setIngredientUnit(String s) {
    this.ingredientUnit = s;
  }
  public void setDeleteIngredient(String s) {
    this.deleteIngredient = s;
  }

  public void setTestParameter(String s) {
    this.testParameter = s;
  }
  public void setTestParameterUnit(String s) {
    this.testParameterUnit = s;
  }
  public void setDeleteTestParameter(String s) {
    this.deleteTestParameter = s;
  }
  //getters
  public String getSubmitSave() {
    return this.submitSave;
  }

  public String getSubmitApprove() {
    return this.submitApprove;
  }

  public BigDecimal getPersonnelId() {
    return this.personnelId;
  }

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

  public Date getInactivationDate() {
    return inactivationDate;
  }

  public String getInstructions() {
    return instructions;
  }

  public String getInactive() {
    return inactive;
  }

  public String getApproved() {
    return this.approved;
  }

  public String getPartNumber() {
    return this.partNumber;
  }
  public BigDecimal getPartGroupNo() {
    return this.partGroupNo;
  }
  public String getPartDescription() {
    return this.partDescription;
  }
  public String getIngredientUnit() {
    return this.ingredientUnit;
  }
  public BigDecimal getIngredientAmount() {
    return this.ingredientAmount;
  }
  public String getDeleteIngredient() {
    return this.deleteIngredient;
  }
  public String getTestParameter() {
    return this.testParameter;
  }
  public String getTestParameterUnit() {
    return this.testParameterUnit;
  }
  public String getDeleteTestParameter() {
    return this.deleteTestParameter;
  }
}