package com.tcmis.internal.hub.process;

//import java.io.*;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.RecipeIngredientSearchViewBean;
import com.tcmis.internal.hub.factory.RecipeIngredientSearchViewBeanFactory;

/******************************************************************************
 * Process used by RecipeIngredientAction
 * @version 1.0
 *****************************************************************************/

public class RecipeIngredientProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public RecipeIngredientProcess(String client) {
	super(client);
 }

 public Collection getRecipeIngredientSearchViewBeanCollection(
	RecipeIngredientSearchViewBean bean) throws BaseException, Exception {
	DbManager dbManager = new DbManager(getClient());
	RecipeIngredientSearchViewBeanFactory factory = new	 RecipeIngredientSearchViewBeanFactory(dbManager);
	SearchCriteria searchCriteria = new SearchCriteria();

	 searchCriteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS,
		bean.getCatalogCompanyId());
	 searchCriteria.addCriterion("catalogId", SearchCriterion.EQUALS,
		bean.getCatalogId());
	 if (bean.getSearchString() != null && bean.getSearchString().length() != 0) {
	 searchCriteria.addCriterion("searchString", SearchCriterion.LIKE,
		bean.getSearchString(), "Y");
	 }

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("itemId");
	Collection c = factory.select(searchCriteria, sortCriteria);
	return c;
 }
}