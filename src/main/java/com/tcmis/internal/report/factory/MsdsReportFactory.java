package com.tcmis.internal.report.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.internal.report.beans.MsdsReportBean;

/******************************************************************************
 * This is a specialized factory for creating reports.
 *****************************************************************************/
public class MsdsReportFactory
    extends BaseBeanFactory {
  Log log = LogFactory.getLog(this.getClass());

  //constructor
  public MsdsReportFactory(DbManager dbManager) {
    super(dbManager);
  }

  public DataSet getDataSet(MsdsReportBean bean) throws Exception {
    String query = "select * from MSDS_REVISION_VIEW where FACILITY = '" +
        bean.getFacility() + "'";
    return getDbManager().select(query);
  }
}