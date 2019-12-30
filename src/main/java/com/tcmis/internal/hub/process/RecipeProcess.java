package com.tcmis.internal.hub.process;


import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;
import com.tcmis.common.admin.factory.VvRecipeTestParameterBeanFactory;
import com.tcmis.common.admin.factory.VvMeasurementUnitBeanFactory;
import com.tcmis.common.admin.factory.VvSizeUnitBeanFactory;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class RecipeProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public RecipeProcess(String client) {
    super(client);
  }

  public Collection getSearchData(RecipeInputBean inputBean)  throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    RecipeViewBeanFactory factory = new RecipeViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    if(inputBean.getRecipeId() != null) {
      criteria.addCriterion("recipeId", SearchCriterion.EQUALS, inputBean.getRecipeId().toString());
    }
    if(!StringHandler.isBlankString(inputBean.getRecipeName())) {
      criteria.addCriterion("recipeName", SearchCriterion.LIKE, inputBean.getRecipeName(), "Y");
    }
    if(!StringHandler.isBlankString(inputBean.getRecipeDescription())) {
      criteria.addCriterion("recipeDescription", SearchCriterion.LIKE, inputBean.getRecipeDescription(), "Y");
    }
    if(!StringHandler.isBlankString(inputBean.getCompanyId())) {
      criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
    }
    if(!StringHandler.isBlankString(inputBean.getCatalogId())) {
      criteria.addCriterion("catalogId", SearchCriterion.EQUALS, inputBean.getCatalogId());
    }
    if(!StringHandler.isBlankString(inputBean.getInactive())) {
      criteria.addCriterion("inactiveDate", SearchCriterion.IS_NOT, "NULL");
    }
    if(!StringHandler.isBlankString(inputBean.getApproved())) {
      criteria.addCriterion("recipeQcDate", SearchCriterion.IS_NOT, "NULL");
    }
    return factory.select(criteria, new SortCriteria());
  }

  public Collection getOwnerCatalogDropdown(BigDecimal personnelId)  throws BaseException {
    Collection c = null;
    try {
      DbManager dbManager = new DbManager(getClient());
      RecipeUserGroupOvBeanFactory factory = new RecipeUserGroupOvBeanFactory(
          dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("personnelId", SearchCriterion.EQUALS,
                            personnelId.toString());
      c = factory.selectObject(criteria, new SortCriteria());
    }
    catch(Exception e) {
      log.error("Error getting ownercatalogdropdown:" + e.getMessage(), e);
      throw new BaseException(e);
    }
    return c;
  }

  public Collection getVvSizeUnitDropDown()  throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    VvSizeUnitBeanFactory factory = new VvSizeUnitBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("dispensable", SearchCriterion.EQUALS, "Y");
    criteria.addCriterion("unitType", SearchCriterion.IN, "'volume m','weight e','volume e','weight m'");
    return factory.select(criteria, new SortCriteria("sizeUnit"));
  }

  public Collection getVvRecipeTestParameterDropDown()  throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    VvRecipeTestParameterBeanFactory factory = new VvRecipeTestParameterBeanFactory(dbManager);
    return factory.select(new SearchCriteria(), new SortCriteria());
  }

  public Collection getVvMeasurementUnitDefault()  throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    VvMeasurementUnitBeanFactory factory = new VvMeasurementUnitBeanFactory(dbManager);
    return factory.select(new SearchCriteria(), new SortCriteria());
  }

  public Collection getIngredientDetail(RecipeInputBean inputBean)  throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    RecipeIngredientViewBeanFactory factory = new RecipeIngredientViewBeanFactory(dbManager);
    return factory.select(new SearchCriteria("recipeId", SearchCriterion.EQUALS, inputBean.getRecipeId().toString()), new SortCriteria());
  }

  public Collection getTestParameterDetail(RecipeInputBean inputBean)  throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    RecipeTestParameterViewBeanFactory factory = new RecipeTestParameterViewBeanFactory(dbManager);
    return factory.select(new SearchCriteria("recipeId", SearchCriterion.EQUALS, inputBean.getRecipeId().toString()), new SortCriteria());
  }

  public void saveRecipe(RecipeInputBean inputBean, 
                         Collection ingredientCollection, 
                         Collection testParameterCollection) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
    Collection inArgs = new Vector(10);
    if(log.isDebugEnabled()) {
      log.debug("bean:" + inputBean);
    }
    inArgs.add(inputBean.getCompanyId());
    inArgs.add(inputBean.getPersonnelId());
    inArgs.add(inputBean.getCatalogId());
    inArgs.add(inputBean.getRecipeName());
    inArgs.add(inputBean.getRecipeDescription());
    if(inputBean.getYieldAmount() == null) {
      inArgs.add("");
    }
    else {
      inArgs.add(inputBean.getYieldAmount());
    }
    inArgs.add(inputBean.getYieldAmountUnit());
    inArgs.add(inputBean.getInstructions());
    if(StringHandler.emptyIfNull(inputBean.getSubmitApprove()).length() != 0) {
      inArgs.add("Y"); //approve flag
    }
    else {
      inArgs.add("N"); //approve flag      
    }
    inArgs.add(new BigDecimal("1"));//version number
    if(log.isDebugEnabled()) {
      log.debug("p_recipe_create args:" + inArgs);
    }
    Collection outArgs = new Vector(3);
    outArgs.add(new Integer(java.sql.Types.NUMERIC));
    outArgs.add(new Integer(java.sql.Types.NUMERIC));
    outArgs.add(new Integer(java.sql.Types.VARCHAR));
    Collection resultArgs = procFactory.doProcedure("p_recipe_create", inArgs, outArgs);
    Iterator resultIterator = resultArgs.iterator();
    BigDecimal resultRecipeId = null;
    BigDecimal resultItemId = null;
    String resultError = null;
    for(int i=0; resultIterator.hasNext(); i++) {
      if(i == 0) { //recipe id
        resultRecipeId = (BigDecimal)resultIterator.next();
        if(log.isDebugEnabled()) {
          log.debug("recipe id:" + resultRecipeId);
        }
      }
      else if(i == 1) {//yield item id
        resultItemId = (BigDecimal)resultIterator.next();
        if(log.isDebugEnabled()) {
          log.debug("item id:" + resultItemId);
        }
      }
      else { //any error
        resultError = (String)resultIterator.next();
        if(log.isDebugEnabled()) {
          log.debug("error:" + resultError);
        }
      }
    }
    //now do ingredients
    if(ingredientCollection != null) {
      Iterator ingredientIterator = ingredientCollection.iterator();
      while(ingredientIterator.hasNext()) {
        RecipeInputBean ingredientBean = (RecipeInputBean)ingredientIterator.next();
        inArgs = new Vector(9);
        inArgs.add(inputBean.getCompanyId());
        inArgs.add(inputBean.getPersonnelId());
        inArgs.add(inputBean.getRecipeId());
        inArgs.add(inputBean.getCatalogId());
        inArgs.add(ingredientBean.getPartNumber());
        if(ingredientBean.getPartGroupNo() == null) {
          inArgs.add("");
        }
        else {
          inArgs.add(ingredientBean.getPartGroupNo());
        }
        if(ingredientBean.getIngredientAmount() == null) {
          inArgs.add("");
        }
        else {
          inArgs.add(ingredientBean.getIngredientAmount());
        }
        inArgs.add(ingredientBean.getIngredientUnit());
        if(ingredientBean.getDeleteIngredient() != null) {
          inArgs.add("delete");
        }
        else {
          inArgs.add("update");
        }
        if(log.isDebugEnabled()) {
          log.debug("p_recipe_ingredient args:" + inArgs);
        }
        outArgs = new Vector(1);
        outArgs.add(new Integer(java.sql.Types.VARCHAR));
        resultArgs = procFactory.doProcedure("p_recipe_ingredient", inArgs, outArgs);
        resultIterator = resultArgs.iterator();
        for(int i=0; resultIterator.hasNext(); i++) {
          resultError = (String)resultIterator.next();
          if(log.isDebugEnabled()) {
            log.debug("p_recipe_ingredient error:" + resultError);
          }
        }
      }
    }
    //now do test parameters
    if(testParameterCollection != null) {
      Iterator testParameterIterator = testParameterCollection.iterator();
      while(testParameterIterator.hasNext()) {
        RecipeInputBean testParameterBean = (RecipeInputBean)testParameterIterator.next();
        inArgs = new Vector(3);
        inArgs.add(inputBean.getRecipeId());
        inArgs.add(testParameterBean.getTestParameter());
        inArgs.add(testParameterBean.getTestParameterUnit());
        outArgs = new Vector(1);
        outArgs.add(new Integer(java.sql.Types.VARCHAR));
        if(testParameterBean.getDeleteTestParameter() != null) {
          Collection optionalInArgs = new Vector(1);
          optionalInArgs.add("Y");
          resultArgs = procFactory.doProcedure("p_recipe_test_parameter", inArgs, outArgs, optionalInArgs);
          if (log.isDebugEnabled()) {
            log.debug("p_recipe_test_parameter args:" + inArgs + ":" + optionalInArgs);
          }
        }
        else {
          resultArgs = procFactory.doProcedure("p_recipe_test_parameter", inArgs, outArgs);
          if (log.isDebugEnabled()) {
            log.debug("p_recipe_test_parameter args:" + inArgs);
          }
        }
        resultIterator = resultArgs.iterator();
        for(int i=0; resultIterator.hasNext(); i++) {
          resultError = (String)resultIterator.next();
          if(log.isDebugEnabled()) {
            log.debug("p_recipe_ingredient error:" + resultError);
          }
        }
      }
    }
  }
}