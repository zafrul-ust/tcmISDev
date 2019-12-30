package com.tcmis.internal.report.process;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.report.beans.UsersSavedQueriesBean;
import com.tcmis.internal.report.factory.UsersSavedQueriesBeanFactory;

/******************************************************************************
 * Process to create save a query for the internal report application.
 * @version 1.0
 *****************************************************************************/
public class SaveQueryProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public SaveQueryProcess(String client) {
    super(client);
  }

  /******************************************************************************
   * Saves the query entered if it does not exist and updates it if it does 
   * already exist.<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  public void saveOrUpdateQuery(UsersSavedQueriesBean bean) throws BaseException {
    if(!this.doesQueryExist(bean)) {
      this.saveQuery(bean);
    }
    else {
      bean.setQueryNameOld(bean.getQueryName());
      this.updateQuery(bean);
    }
  }

  /******************************************************************************
   * Saves the query entered.<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws BaseException If an <code>SQLException</code> is thrown or if 
   * there is not sufficient data provided.
   ****************************************************************************/
  public void saveQuery(UsersSavedQueriesBean bean) throws BaseException {
    //check input
    if (!this.validateInput(bean)) {
      if (log.isErrorEnabled()) {
        log.error("There is data missing");
      }
      throw new BaseException("There is data missing");
    }
    DbManager dbManager = new DbManager(getClient());
    UsersSavedQueriesBeanFactory factory = new UsersSavedQueriesBeanFactory(
        dbManager);
    bean.setDateSaved(new Date());
    int result = 0;

    result = factory.insert(bean);
  }

  /******************************************************************************
   * Deletes the query chosen.<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  public void deleteQuery(UsersSavedQueriesBean bean) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    UsersSavedQueriesBeanFactory factory = new UsersSavedQueriesBeanFactory(
        dbManager);
    int result = 0;
    result = factory.delete(bean);
  }

  /******************************************************************************
   * Updates the query entered if it does exist and save it if it does not
   * exist.<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  public void updateQuery(UsersSavedQueriesBean bean) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    UsersSavedQueriesBeanFactory factory = new UsersSavedQueriesBeanFactory(
        dbManager);
    int result = 0;

    //if it's the same name update otherwise insert a new query
    if (bean.getQueryName().equalsIgnoreCase(bean.getQueryNameOld())) {
      result = factory.update(bean);
    }
    else {
      result = factory.insert(bean);
    }
  }

  /******************************************************************************
   * Gets the saved queries for this user.<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  public Collection getUserQueries(UsersSavedQueriesBean bean) throws
      BaseException {
    DbManager dbManager = new DbManager(getClient());
    UsersSavedQueriesBeanFactory factory = new UsersSavedQueriesBeanFactory(
        dbManager);
    Collection c = null;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("companyId",
                          SearchCriterion.EQUALS,
                          bean.getCompanyId());
    criteria.addCriterion("personnelId",
                          SearchCriterion.EQUALS,
                          new Integer(bean.getPersonnelId()).toString());

    c = factory.select(criteria);
    return c;
  }

  /******************************************************************************
   * Gets a user query based on query name<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  public UsersSavedQueriesBean getUserQuery(UsersSavedQueriesBean bean) throws
      BaseException {
    DbManager dbManager = new DbManager(getClient());
    UsersSavedQueriesBeanFactory factory = new UsersSavedQueriesBeanFactory(
        dbManager);
    Collection c = null;
    UsersSavedQueriesBean newBean = new UsersSavedQueriesBean();
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("companyId",
                          SearchCriterion.EQUALS,
                          bean.getCompanyId());
    criteria.addCriterion("personnelId",
                          SearchCriterion.EQUALS,
                          new Integer(bean.getPersonnelId()).toString());
    criteria.addCriterion("queryName",
                          SearchCriterion.EQUALS,
                          bean.getQueryName());

    c = factory.select(criteria);

    //set old query name
    Iterator iterator = c.iterator();
    while (iterator.hasNext()) {
      newBean = ( (UsersSavedQueriesBean) iterator.next());
      newBean.setQueryNameOld(bean.getQueryName());
    }
    return newBean;
  }

  /******************************************************************************
   * Checks if a query by that name already exists for that user.<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws BaseException If an <code>SQLException</code> is thrown
   ****************************************************************************/
  public boolean doesQueryExist(UsersSavedQueriesBean bean) throws
      BaseException {
    boolean queryExist = false;
    DbManager dbManager = new DbManager(getClient());
    UsersSavedQueriesBeanFactory factory = new UsersSavedQueriesBeanFactory(
        dbManager);
    Collection c = null;
    UsersSavedQueriesBean newBean = new UsersSavedQueriesBean();
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("companyId",
                          SearchCriterion.EQUALS,
                          bean.getCompanyId());
    criteria.addCriterion("personnelId",
                          SearchCriterion.EQUALS,
                          new Integer(bean.getPersonnelId()).toString());
    criteria.addCriterion("queryName",
                          SearchCriterion.EQUALS,
                          bean.getQueryName());

    c = factory.select(criteria);
    if(c.size() > 0) {
      queryExist = true;
    }
    return queryExist;
  }

  private boolean validateInput(UsersSavedQueriesBean bean) {
    if (bean == null ||
        //bean.getCompanyId() == null || bean.getCompanyId().trim().length() < 1 ||
        bean.getPersonnelId() == 0 ||
        bean.getQueryName() == null || bean.getQueryName().trim().length() < 1 ||
        bean.getQuery() == null || bean.getQuery().trim().length() < 1) {
      return false;
    }
    else {
      return true;
    }

  }

}
