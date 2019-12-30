package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: RecipeTestParameterViewBean <br>
 * @version: 1.0, Sep 27, 2007 <br>
 *****************************************************************************/

public class RecipeTestParameterViewBean
    extends BaseDataBean {

  private String companyId;
  private BigDecimal recipeId;
  private String testParameter;
  private String testParameterUnit;

  //constructor
  public RecipeTestParameterViewBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setRecipeId(BigDecimal recipeId) {
    this.recipeId = recipeId;
  }

  public void setTestParameter(String testParameter) {
    this.testParameter = testParameter;
  }

  public void setTestParameterUnit(String testParameterUnit) {
    this.testParameterUnit = testParameterUnit;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public BigDecimal getRecipeId() {
    return recipeId;
  }

  public String getTestParameter() {
    return testParameter;
  }

  public String getTestParameterUnit() {
    return testParameterUnit;
  }
}