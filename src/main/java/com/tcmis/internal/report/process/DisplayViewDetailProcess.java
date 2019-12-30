package com.tcmis.internal.report.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.report.factory.UserObjectBeanFactory;

/******************************************************************************
 * Process to create MSDS excel report
 * @version 1.0
 *****************************************************************************/
public class DisplayViewDetailProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public DisplayViewDetailProcess(String client) {
    super(client);
  }

  public Collection getViews(int personnelId) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    UserObjectBeanFactory factory = new UserObjectBeanFactory(dbManager);
    Collection c = null;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("personnelId",
                          SearchCriterion.EQUALS,
                          new Integer(personnelId).toString());
    return factory.selectTableOnly(criteria);

  }

  public Collection getViewDetail(int personnelId) throws BaseException {
    DbManager dbManager = new DbManager(getClient());
    UserObjectBeanFactory factory = new UserObjectBeanFactory(dbManager);
    Collection c = null;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("personnelId", SearchCriterion.EQUALS,
                          new Integer(personnelId).toString());
    return factory.select(criteria);

  }

  public Collection getViewDetail(int personnelId, String viewName) throws
      BaseException {
    DbManager dbManager = new DbManager(getClient());
    UserObjectBeanFactory factory = new UserObjectBeanFactory(dbManager);
    Collection c = null;
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("tableName", SearchCriterion.EQUALS, viewName);
    criteria.addCriterion("personnelId", SearchCriterion.EQUALS,
                          new Integer(personnelId).toString());
    return factory.select(criteria);
  }
}
