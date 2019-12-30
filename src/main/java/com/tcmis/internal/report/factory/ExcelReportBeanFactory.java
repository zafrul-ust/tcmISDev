package com.tcmis.internal.report.factory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;

/******************************************************************************
 * CLASSNAME: BulkWasteBeanFactory <br>
 * @version: 1.0, Mar 16, 2004 <br>
 *****************************************************************************/

public class ExcelReportBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //constructor
  public ExcelReportBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  public DataSet getDataSet(String query) throws Exception {
    if (log.isInfoEnabled()) {
      log.info("QUERY:" + query);
    }
    return getDbManager().select(query);
  }

  public String validateQuery(int personnelId,
                              String query) throws Exception {
    String foo = "";
    try {
      if (log.isInfoEnabled()) {
        log.info("PROCEDURE QUERY:" + query);
      }
      Vector in = new Vector(2);
      in.addElement(new Integer(personnelId).toString());
      in.addElement(query);
      in.trimToSize();
      Vector out = new Vector(1);
      out.add(new Integer(java.sql.Types.VARCHAR));
      out.trimToSize();
      Collection result = getDbManager().doProcedure("pr_query_check", in, out);
      Iterator iterator = result.iterator();
      while (iterator.hasNext()) {
        foo = (String) iterator.next();
      }
    }
    catch (BaseException sqle) {
      log.debug("SQLERROR for Excel report:" + sqle.getMessage());
    }
    catch (Exception e) {
      log.debug("ERROR:" + e.getMessage());
      e.printStackTrace();
    }
    return foo;
  }

}