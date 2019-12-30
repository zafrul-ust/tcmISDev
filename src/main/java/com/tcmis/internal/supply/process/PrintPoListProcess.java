package com.tcmis.internal.supply.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.factory.BpoImageViewBeanFactory;
import com.tcmis.internal.supply.factory.PoImageViewBeanFactory;


/******************************************************************************
 * Process to build a web page for user to view PO/BO.
 * @version 1.0
 *****************************************************************************/
public class PrintPoListProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PrintPoListProcess(String client) {
    super(client);
  }


  public Collection getSearchResult(String type, String searchText) throws Exception {
    DbManager dbManager = new DbManager(getClient());
    Collection c = new Vector();
    if ("PO".equalsIgnoreCase(type)) {
      PoImageViewBeanFactory factory = new PoImageViewBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("radianPo", SearchCriterion.EQUALS, searchText);
      SortCriteria sortCriteria = new SortCriteria();
      sortCriteria.setSortAscending(false);
      sortCriteria.addCriterion("datePrinted");
      c = factory.select(criteria,sortCriteria);
    } else {
      BpoImageViewBeanFactory factory = new BpoImageViewBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("bpo", SearchCriterion.EQUALS, searchText);
      SortCriteria sortCriteria = new SortCriteria();
      sortCriteria.setSortAscending(false);
      sortCriteria.addCriterion("datePrinted");
      c = factory.select(criteria,sortCriteria);
    }

    return c;
  } //end of method

} //end of class
