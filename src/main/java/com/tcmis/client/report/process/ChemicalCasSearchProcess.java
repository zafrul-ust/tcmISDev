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
import com.tcmis.client.report.factory.ChemSynonymViewBeanFactory;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class ChemicalCasSearchProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ChemicalCasSearchProcess(String client) {
    super(client);
  }

  public Collection getChemicalList(String searchText) throws BaseException, Exception {

    Collection casCollection = new Vector();

    DbManager dbManager = new DbManager(getClient());
    ChemSynonymViewBeanFactory chemSynonymViewBeanFactory = new ChemSynonymViewBeanFactory(dbManager);
    if (searchText != null) {
      if (searchText.length() > 0) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("alternateName", SearchCriterion.LIKE, searchText);
        casCollection = chemSynonymViewBeanFactory.selectSortByName(criteria);
      }else {
        casCollection = chemSynonymViewBeanFactory.selectSortByName(null);
      }
    }

    return casCollection;
  }

} //end of class