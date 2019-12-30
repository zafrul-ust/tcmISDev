package radian.tcmis.internal.admin.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Collection;
import java.util.Vector;

import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.exceptions.DataBeanCreationException;
import radian.tcmis.common.framework.BaseBeanFactory;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

import radian.tcmis.internal.admin.beans.TableBean;
import radian.tcmis.internal.admin.beans.ColumnBean;

/******************************************************************************
 * Factory for meta data. Populates a <code>TableBean</code> with the metadata
 * for the given table.
 * @version: 1.0, Mar 16, 2004 <br>
 *****************************************************************************/
public class TableMetaDataFactory extends BaseBeanFactory {
  Log log = LogFactory.getLog(this.getClass());

  //constructor
  public TableMetaDataFactory(String client) {
    super(client);
  }

  public TableBean getMetaData(String tableName, TcmISDBModel dbModel)
      throws BaseException {
    TableBean bean = new TableBean();
    try {
      Statement statement = dbModel.getConnection().createStatement();
      ResultSet rs = statement.executeQuery("select * from " + tableName +
                                            " where 1=2");
      ResultSetMetaData rsmd = rs.getMetaData();
      bean.setName(tableName);
      bean.setColumnCount(rsmd.getColumnCount());
      Collection columns = new Vector(rsmd.getColumnCount());
      for(int i=1; i<=rsmd.getColumnCount(); i++) {
        ColumnBean columnBean = new ColumnBean();
        columnBean.setCatalogName(rsmd.getCatalogName(i));
        columnBean.setClassName(rsmd.getColumnClassName(i));
        columnBean.setIsAutoIncrement(rsmd.isAutoIncrement(i));
        columnBean.setIsCaseSensitive(rsmd.isCaseSensitive(i));
        columnBean.setIsCurrency(rsmd.isCurrency(i));
        columnBean.setIsDefinitelyWritable(rsmd.isDefinitelyWritable(i));
        columnBean.setIsNullable(rsmd.isNullable(i));
        columnBean.setIsReadOnly(rsmd.isReadOnly(i));
        columnBean.setIsSearchable(rsmd.isSearchable(i));
        columnBean.setIsSigned(rsmd.isSigned(i));
        columnBean.setIsWritable(rsmd.isWritable(i));
        columnBean.setLabel(rsmd.getColumnLabel(i));
        columnBean.setName(rsmd.getColumnName(i));
        columnBean.setPrecision(rsmd.getPrecision(i));
        columnBean.setScale(rsmd.getScale(i));
        columnBean.setSchemaName(rsmd.getSchemaName(i));
        columnBean.setSize(rsmd.getColumnDisplaySize(i));
        columnBean.setTableName(rsmd.getTableName(i));
        columnBean.setType(rsmd.getColumnType(i));
        columnBean.setTypeName(rsmd.getColumnTypeName(i));
        columns.add(columnBean);
      }
      bean.setColumnBeanCollection(columns);
    }
    catch(Exception e) {
      log.error("ERROR creating TableBean for " + tableName);
      log.error("ERROR:" + e.getMessage());
      throw new DataBeanCreationException("Error creating bean for " + tableName);
    }
    return bean;
  }
}