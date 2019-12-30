package radian.tcmis.internal.admin.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.framework.BaseBeanFactory;
import radian.tcmis.common.db.SqlManager;
import radian.tcmis.common.util.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.internal.admin.beans.SicBean;

/******************************************************************************
 * CLASSNAME: SicBeanFactory <br>
 * @version: 1.0, May 4, 2004 <br>
 *****************************************************************************/

public class SicBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_SIC_CODE = "SIC_CODE";
  public String ATTRIBUTE_INDUSTRY_TITLE = "INDUSTRY_TITLE";

  //sequence and table names
  public String SEQUENCE = "SIC_CODE";
  public String TABLE = "SIC";

  //constructor
  public SicBeanFactory(String client) {
    super(client);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("sicCode")) {
      return ATTRIBUTE_SIC_CODE;
    }
    if (attributeName.equals("industryTitle")) {
      return ATTRIBUTE_INDUSTRY_TITLE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, SicBean.class);
  }

  //delete
  public int delete(SicBean sicBean, TcmISDBModel dbModel) throws BaseException {

    SearchCriteria criteria = new SearchCriteria("sicCode",
                                                 SearchCriterion.EQUALS,
                                                 sicBean.getSicCode());

    return delete(criteria, dbModel.getConnection());
  }

  public int delete(SicBean sicBean, Connection conn) throws BaseException {

    SearchCriteria criteria = new SearchCriteria("sicCode",
                                                 SearchCriterion.EQUALS,
                                                 sicBean.getSicCode());

    return delete(criteria, conn);
  }

  public int delete(SearchCriteria criteria, TcmISDBModel dbModel) throws
      BaseException {

    return delete(criteria, dbModel.getConnection());
  }

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {

    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

  //insert
  public int insert(SicBean sicBean, TcmISDBModel dbModel) throws BaseException {

    return insert(sicBean, dbModel.getConnection());
  }

  public int insert(SicBean sicBean, Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_SIC_CODE + "," +
        ATTRIBUTE_INDUSTRY_TITLE + ") VALUES (" +
        SqlHandler.delimitString(sicBean.getSicCode()) + "," +
        SqlHandler.delimitString(sicBean.getIndustryTitle()) + ")";

    return sqlManager.update(conn, query);
  }

  //update
  public int update(SicBean sicBean, TcmISDBModel dbModel) throws BaseException {

    return update(sicBean, dbModel.getConnection());
  }

  public int update(SicBean sicBean, Connection conn) throws BaseException {

    String query = "update " + TABLE + " set " +
        ATTRIBUTE_SIC_CODE + "=" +
        SqlHandler.delimitString(sicBean.getSicCode()) + "," +
        ATTRIBUTE_INDUSTRY_TITLE + "=" +
        SqlHandler.delimitString(sicBean.getIndustryTitle()) + " " +
        "where " + ATTRIBUTE_SIC_CODE + "=" +
        SqlHandler.delimitString(sicBean.getSicCode());

    return new SqlManager().update(conn, query);
  }

  //select
  public Collection select(SearchCriteria criteria, TcmISDBModel dbModel) throws
      BaseException {

    return select(criteria, dbModel.getConnection());
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection sicBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      SicBean sicBean = new SicBean();
      load(dataSetRow, sicBean);
      sicBeanColl.add(sicBean);
    }

    return sicBeanColl;
  }
}