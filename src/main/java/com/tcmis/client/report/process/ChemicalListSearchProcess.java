package com.tcmis.client.report.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.client.report.factory.ListBeanFactory;
import com.tcmis.client.report.factory.ReportListSnapBeanFactory;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class ChemicalListSearchProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ChemicalListSearchProcess(String client) {
    super(client);
  }

  public Collection getCASForList(String searchText) throws BaseException, Exception {

    Collection listCollection = new Vector();

    DbManager dbManager = new DbManager(getClient());
    ReportListSnapBeanFactory reportListSnapBeanFactory = new ReportListSnapBeanFactory(dbManager);
    if (searchText != null) {
      if (searchText.length() > 0) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("listId", SearchCriterion.EQUALS, searchText);
        listCollection = reportListSnapBeanFactory.selectDistinctCASForList(criteria);
      }else {
        listCollection = reportListSnapBeanFactory.selectDistinctCASForList(null);
      }
    }

    return listCollection;
  }

  public Collection getChemicalList(String searchText) throws BaseException, Exception {

    Collection listCollection = new Vector();

    DbManager dbManager = new DbManager(getClient());
    ListBeanFactory listBeanFactory = new ListBeanFactory(dbManager);
    if (searchText != null) {
      if (searchText.length() > 0) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("listName", SearchCriterion.LIKE, searchText,SearchCriterion.IGNORE_CASE);
        listCollection = listBeanFactory.selectSortByName(criteria);
      }else {
        listCollection = listBeanFactory.selectSortByName(null);
      }
    }

    return listCollection;
  }

} //end of class