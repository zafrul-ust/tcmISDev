package radian.tcmis.common.creator;

import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CodeCreator {
  public CodeCreator() {
  }

/*****************************************************************************
   * Creator class for Beans.  Queries given table and creates getters and
   * setters for all columns.
   *
   * @param packageName the package name (com.tcmis.common.creator, com.tcmis.client.gm.beans, etc)
   * @param tableName the table the bean is created for
   * @param outPath directory output path where file will be created
   * @param conn database connection used to query given table
****************************************************************************/
  public static void createBean(String packageName,
                         String tableName,
                         String outPath,
                         Connection conn) {
    Date myDate = new Date();
    String date = DateFormat.getDateInstance().format(myDate);
    String beanName = "";
    String columnName = null;
    String sgColumnName = null;
    String columnType = null;
    String dot = ".";
    String slash = "\\";
    String outStr = null;
    String[] tableNameArray = tableName.toLowerCase().split("_");
    for (int x = 0; x < tableNameArray.length; ++x) {
      tableNameArray[x] = tableNameArray[x].replaceFirst(
          tableNameArray[x].substring(0, 1),
          tableNameArray[x].substring(0, 1).toUpperCase());
      beanName = beanName.concat(tableNameArray[x]);
    }
    beanName = beanName.concat("Bean");
    try {
      PrintWriter printWriter = new PrintWriter(new FileWriter(outPath +
          beanName + ".java"), true);
      //query statement
      Statement sqlStatement = conn.createStatement();
      //query just for column names
      String query = "select * from " + tableName + " where rownum < 1";
      //get resultset
      ResultSet sqlResult = sqlStatement.executeQuery(query);
      //get resultset metadata
      ResultSetMetaData rsmd = sqlResult.getMetaData();
      outStr = "package " + packageName + ".beans;\n\n" +
          "import java.util.Date;\n" +
          "import java.math.BigDecimal;\n\n" +
          "import com.tcmis.common.framework.BaseDataBean;\n\n\n" +
          "/******************************************************************************\n" +
          " * CLASSNAME: " + beanName + " <br>\n" +
          " * @version: 1.0, " + date + " <br>\n" +
          " *****************************************************************************/\n\n\n" +
          "public class " + beanName + " extends BaseDataBean {\n\n";
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        String s = rsmd.getColumnName(i).toLowerCase();
        switch (rsmd.getColumnType(i)) {
          case Types.VARCHAR:
            columnType = "String";
            break;
          case Types.NUMERIC:
            if (rsmd.getScale(i) > 0) {
              columnType = "BigDecimal";
            }
            else {
              columnType = "int";
            }
            break;
          case Types.DATE:
            columnType = "Date";
            break;
          default:
            columnType = "String";
            ;
        }
        String[] cna = s.split("_");
        columnName = cna[0];
        for (int j = 0; j < cna.length; ++j) {
          if (j > 0) {
            cna[j] = cna[j].replaceFirst(cna[j].substring(0, 1),
                                         cna[j].substring(0, 1).toUpperCase());
            columnName = columnName.concat(cna[j]);
          }
        }
        outStr += "\tprivate " + columnType + " " + columnName + ";\n";
      } //end for
      outStr += "\n\n\t//constructor\n" +
          "\tpublic " + beanName + "() {\n" +
          "\t}\n\n";
      outStr += "\t//setters\n";
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        String s = rsmd.getColumnName(i).toLowerCase();
        switch (rsmd.getColumnType(i)) {
          case Types.VARCHAR:
            columnType = "String";
            break;
          case Types.NUMERIC:
            if (rsmd.getScale(i) > 0) {
              columnType = "BigDecimal";
            }
            else {
              columnType = "int";
            }
            break;
          case Types.DATE:
            columnType = "Date";
            break;
          default:
            columnType = "String";
            ;
        }
        String[] cna = s.split("_");
        columnName = cna[0];
        for (int j = 0; j < cna.length; ++j) {
          if (j > 0) {
            cna[j] = cna[j].replaceFirst(cna[j].substring(0, 1),
                                         cna[j].substring(0, 1).toUpperCase());
            columnName = columnName.concat(cna[j]);
          }
        }
        String[] sgCna = s.split("_");
        sgColumnName = "";
        for (int j = 0; j < sgCna.length; ++j) {
          sgCna[j] = sgCna[j].replaceFirst(sgCna[j].substring(0, 1),
                                           sgCna[j].substring(0, 1).toUpperCase());
          sgColumnName = sgColumnName.concat(sgCna[j]);
        }
        outStr += "\tpublic void set" + sgColumnName + "(" +
            columnType + " " + columnName + ") {\n" +
            "\t\tthis." + columnName + " = " + columnName +
            ";\n" + "\t}\n";
      } //end for
      outStr += "\n\n\t//getters\n";
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        String s = rsmd.getColumnName(i).toLowerCase();
        switch (rsmd.getColumnType(i)) {
          case Types.VARCHAR:
            columnType = "String";
            break;
          case Types.NUMERIC:
            if (rsmd.getScale(i) > 0) {
              columnType = "BigDecimal";
            }
            else {
              columnType = "int";
            }
            break;
          case Types.DATE:
            columnType = "Date";
            break;
          default:
            columnType = "String";
            ;
        }
        String[] cna = s.split("_");
        columnName = cna[0];
        for (int j = 0; j < cna.length; ++j) {
          if (j > 0) {
            cna[j] = cna[j].replaceFirst(cna[j].substring(0, 1),
                                         cna[j].substring(0, 1).toUpperCase());
            columnName = columnName.concat(cna[j]);
          }
        }
        String[] sgCna = s.split("_");
        sgColumnName = "";
        for (int j = 0; j < sgCna.length; ++j) {
          sgCna[j] = sgCna[j].replaceFirst(sgCna[j].substring(0, 1),
                                           sgCna[j].substring(0, 1).toUpperCase());
          sgColumnName = sgColumnName.concat(sgCna[j]);
        }
        outStr += "\tpublic " + columnType + " get" + sgColumnName +
            "() {\n" +
            "\t\treturn " + columnName + ";\n" +
            "\t}\n";
      } //end for
      outStr += "}";
      printWriter.print(outStr);
      printWriter.close();
      sqlResult.close();
      sqlStatement.close();
    }
    catch (IOException ioe) {
      System.out.println("IO ERROR" + ioe.getMessage());
    }
    catch (SQLException e) {
      System.out.print("SQL ERROR" + e.getMessage());
    }
  }

/*****************************************************************************
 * Creator class for Factories.  Queries given table and creates getters and
 * setters for all columns.
 * <b>NOTE: This method assumes that the first column is the primary key column
 * and that it is a numeric column. If this is not the case you will have to
 * make modifications to the insert and update methods.</b>
 *
 * @param packageName the package name (com.tcmis.common.creator, com.tcmis.client.gm.beans, etc)
 * @param tableName the table the bean is created for
 * @param outPath directory output path where file will be created
 * @param conn database connection used to query given table
  ****************************************************************************/
  public static void createFactory(String packageName,
                            String tableName,
                            String outPath,
                            Connection conn) {

    Date myDate = new Date();
    String date = DateFormat.getDateInstance().format(myDate);
    String beanFactoryName = "";
    String dot = ".";
    String slash = "\\";
    String prepend = "";
    String append = "";
    String outStr = "";
    String[] tableNameArray = tableName.toLowerCase().split("_");
    for (int x = 0; x < tableNameArray.length; ++x) {
      tableNameArray[x] = tableNameArray[x].replaceFirst(tableNameArray[x].
          substring(0, 1),
          tableNameArray[x].substring(0, 1).toUpperCase());
      beanFactoryName = beanFactoryName.concat(tableNameArray[x]);
    }
    beanFactoryName = beanFactoryName.concat("BeanFactory");
    String beanName = beanFactoryName.replaceAll("Factory", "");
    String beanInstance = beanName.replaceFirst(beanName.substring(0, 1),
                                                beanName.substring(0, 1).
                                                toLowerCase());

    try {
      PrintWriter printWriter = new PrintWriter(new FileWriter(outPath +
          beanFactoryName + ".java"), true);

      //query statement
      Statement sqlStatement = conn.createStatement();
      //query just for column names
      String query = "select * from " + tableName + " where rownum < 1";
      //get resultset
      ResultSet sqlResult = sqlStatement.executeQuery(query);
      //get resultset metadata
      ResultSetMetaData rsmd = sqlResult.getMetaData();
      String[] columnName = new String[rsmd.getColumnCount()];
      String[] columnType = new String[rsmd.getColumnCount()];
      String[] attributeName = new String[rsmd.getColumnCount()];
      String[] setAttributeName = new String[rsmd.getColumnCount()];
      String[] getAttributeName = new String[rsmd.getColumnCount()];

      int x = 0;
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        switch (rsmd.getColumnType(i)) {
          case Types.VARCHAR:
            columnType[x] = "String";
            break;
          case Types.NUMERIC:
            if (rsmd.getScale(i) > 0) {
              columnType[x] = "BigDecimal";
            }
            else {
              columnType[x] = "int";
            }
            break;
          case Types.DATE:
            columnType[x] = "Date";
            break;
          default:
            columnType[x] = "String";
            ;
        }
        columnName[x] = rsmd.getColumnName(i).toUpperCase();
        String[] cna = columnName[x].toLowerCase().split("_");
        attributeName[x] = cna[0];
        for (int j = 0; j < cna.length; ++j) {
          if (j > 0) {
            cna[j] = cna[j].replaceFirst(cna[j].substring(0, 1),
                                         cna[j].substring(0, 1).toUpperCase());
            attributeName[x] = attributeName[x].concat(cna[j]);
          }
        }
        String[] sgCna = columnName[x].toLowerCase().split("_");
        getAttributeName[x] = "get";
        setAttributeName[x] = "set";
        for (int j = 0; j < sgCna.length; ++j) {
          sgCna[j] = sgCna[j].replaceFirst(sgCna[j].substring(0,
              1), sgCna[j].substring(0, 1).toUpperCase());
          setAttributeName[x] = setAttributeName[x].concat(sgCna[
              j]);
          getAttributeName[x] = getAttributeName[x].concat(sgCna[
              j]);
        }
        ++x;
      }
      String primId = attributeName[0];

      outStr =
          "package " + packageName + ".factory;\n\n\n" +
          "import org.apache.commons.logging.Log;\n" +
          "import org.apache.commons.logging.LogFactory;\n" +
          "import java.beans.BeanInfo;\n" +
          "import java.beans.IntrospectionException;\n" +
          "import java.beans.Introspector;\n" +
          "import java.beans.PropertyDescriptor;\n" +
          "\n" +
          "import java.lang.reflect.Method;\n" +
          "\n" +
          "import java.sql.Connection;\n" +
          "\n" +
          "import java.text.ParseException;\n" +
          "\n" +
          "import java.util.Collection;\n" +
          "import java.util.Iterator;\n" +
          "import java.util.Vector;\n" +
          "\n" +
          "import com.tcmis.common.framework.BaseDataBean;\n" +
          "import com.tcmis.common.exceptions.BaseException;\n" +
          "import com.tcmis.common.exceptions.DataBeanCreationException;\n" +
          "import com.tcmis.common.framework.BaseBeanFactory;\n" +
          "import com.tcmis.common.db.SqlManager;\n" +
          "import com.tcmis.common.util.*;\n" +
          "import com.tcmis.server7.share.dbObjs.TcmISDBModel;\n" +
          "import " + packageName + ".beans." + beanName +
          ";\n\n\n" +
          "/******************************************************************************\n" +
          " * CLASSNAME: " + beanFactoryName + " <br>\n" +
          " * @version: 1.0, " + date + " <br>\n" +
          " *****************************************************************************/\n\n\n" +
          "public class " + beanFactoryName + " extends BaseBeanFactory {\n\n" +
          "\tLog log = LogFactory.getLog(this.getClass());\n\n" +
          "\t//column names\n";
      for (int i = 0; i < attributeName.length; ++i) {
        outStr += "\tpublic String ATTRIBUTE_" +
            columnName[i] + " = \"" + columnName[i] + "\";\n";
      } //end for
      outStr +=
          "\n\t//table name\n" +
          "\tpublic String TABLE = \"" + tableName.toUpperCase() +
          "\";\n\n\n" +

          "\t//constructor\n" +
          "\tpublic " + beanFactoryName +
          "(String client) {\n" +
          "\t\tsuper(client);\n" +
          "\t}\n\n\n" +

          "\t//get column names\n" +
          "\tpublic String getColumnName(String attributeName) {\n";
      for (int i = 0; i < attributeName.length; ++i) {
        if(i == 0) {
          outStr += "\t\tif(attributeName.equals(\"" + attributeName[i] +
              "\")) {\n" +
              "\t\t\treturn ATTRIBUTE_" +
              columnName[i] + ";\n" +
              "\t\t}\n";
        }
        else {
          outStr += "\t\telse if(attributeName.equals(\"" + attributeName[i] +
              "\")) {\n" +
              "\t\t\treturn ATTRIBUTE_" +
              columnName[i] + ";\n" +
              "\t\t}\n";

        }
      }
      outStr +=
          "\t\telse {\n" +
          "\t\t\treturn super.getColumnName(attributeName);\n" +
          "\t\t}\n" +
          "\t}\n\n\n" +

          "\t//get column types\n" +
          "\tpublic int getType(String attributeName) {\n" +
          "\t\treturn super.getType(attributeName, " + beanName +
          ".class);\n" +
          "\t}\n\n\n" +

          "//you need to verify the primary key(s) before uncommenting this\n" +
          "/*\n" +
          "\t//delete\n" +
          "\tpublic int delete(" + beanName + " " + beanInstance + ", TcmISDBModel dbModel)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\tSearchCriteria criteria = new SearchCriteria(\"" + primId +
          "\", \"=\",\n" +
          "\t\t\t\"\" + " + beanInstance + "." + getAttributeName[0] +
          "());\n\n" +
          "\t\treturn delete(criteria, dbModel.getConnection());\n" +
          "\t}\n\n\n" +

          "\tpublic int delete(" + beanName + " " + beanInstance +
          ", Connection conn)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\tSearchCriteria criteria = new SearchCriteria(\"" + primId +
          "\", \"=\",\n" +
          "\t\t\t\"\" + " + beanInstance + "." + getAttributeName[0] +
          "());\n\n" +
          "\t\treturn delete(criteria, conn);\n" +
          "\t}\n" +
          "*/\n\n" +

          "\tpublic int delete(SearchCriteria criteria, TcmISDBModel dbModel)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\treturn delete(criteria, dbModel.getConnection());\n" +
          "\t}\n\n\n" +

          "\tpublic int delete(SearchCriteria criteria, Connection conn)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\tString sqlQuery = \" delete from \" + TABLE + \" \" +\n" +
          "\t\t\tgetWhereClause(criteria);\n\n" +
          "\t\treturn new SqlManager().update(conn, sqlQuery);\n" +
          "\t}\n\n\n" +

          "//you need to verify the primary key(s) before uncommenting this\n" +
          "/*\n" +
          "\t//insert\n" +
          "\tpublic int insert(" + beanName + " " + beanInstance + ", TcmISDBModel dbModel)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\treturn insert(" + beanInstance + ", dbModel.getConnection());\n" +
          "\t}\n\n\n" +

          "\tpublic int insert(" + beanName + " " + beanInstance +
          ", Connection conn)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\tSqlManager sqlManager = new SqlManager();\n\n" +

          "\t\tString query  = \n" +
          "\t\t\t\"insert into \" + TABLE + \" (\" +\n";
      for (int i = 0; i < columnName.length; ++i) {
        if (i == (columnName.length - 1)) {
          outStr +=
              "\t\t\tATTRIBUTE_" + columnName[i] + " + \")\" +\n";
        }
        else {
          outStr +=
              "\t\t\tATTRIBUTE_" + columnName[i] + " + \",\" +\n";
        }
      }

      for (int i = 0; i < columnName.length; ++i) {
        if (columnType[i].equals("String")) {
          //prepend = "\t\t\t\"'\" + ";
          prepend = "\t\t\tSqlHandler.delimitString(";
          if (i == (columnName.length - 1)) {
            append = ") + \")\";\n\n";
          }
          else {
            append = ") + \",\" +\n";
          }
        }
        else if (columnType[i].equals("Date")) {
          prepend = "\t\t\tDateHandler.getOracleToDateFunction(";
          if (i == (columnName.length - 1)) {
            append = ") + \")\";\n\n";
          }
          else {
            append = ") + \",\" +\n";
          }
        }
        else {
          prepend = "\t\t\tStringHandler.nullIfZero(";
          if (i == (columnName.length - 1)) {
            append = ") + \")\";\n\n";
          }
          else {
            append = ") + \",\" +\n";
          }
        }
        outStr +=
            prepend + beanInstance + "." + getAttributeName[i] + "()" +
            append;
      }
      outStr +=
          "\t\treturn sqlManager.update(conn, query);\n" +
          "\t}\n\n\n" +

          "\t//update\n" +
          "\tpublic int update(" + beanName + " " + beanInstance + ", TcmISDBModel dbModel)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\treturn update(" + beanInstance + ", dbModel.getConnection());\n" +
          "\t}\n\n\n" +

          "\tpublic int update(" + beanName + " " + beanInstance +
          ", Connection conn)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\tString query  = \"update \" + TABLE + \" set \" +\n";
      for (int i = 0; i < columnName.length; ++i) {
        if (columnType[i].equals("String")) {
          prepend = "SqlHandler.delimitString(";
          if (i == (columnName.length - 1)) {
            append = ") + \" \" +\n";
          }
          else {
            append = ") + \",\" +\n";
          }
        }
        else if (columnType[i].equals("Date")) {
          prepend = "DateHandler.getOracleToDateFunction(";
          if (i == (columnName.length - 1)) {
            append = ") + \" \" +\n";
          }
          else {
            append = ") + \",\" +\n";
          }
        }
        else {
          prepend = "StringHandler.nullIfZero(";
          if (i == (columnName.length - 1)) {
            append = ") + \" \" +\n";
          }
          else {
            append = ") + \",\" +\n";
          }
        }
        outStr +=
            "\t\t\tATTRIBUTE_" + columnName[i] + " + \"=\" + \n" +
            "\t\t\t\t" + prepend + beanInstance + "." +
            getAttributeName[i] + "()" + append;
      }
      outStr +=
          "\t\t\t\"where \" + ATTRIBUTE_" + columnName[0] +
          " + \"=\" +\n" +
          "\t\t\t\tStringHandler.nullIfZero(" + beanInstance + "." +
          getAttributeName[0] + "());\n\n" +
          "\t\treturn new SqlManager().update(conn, query);\n" +
          "\t}\n" +
          "*/\n\n" +

          "\t//select\n" +
          "\tpublic Collection select(SearchCriteria criteria, TcmISDBModel dbModel)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\treturn select(criteria, dbModel.getConnection());\n" +
          "\t}\n" +

          "\tpublic Collection select(SearchCriteria criteria, Connection conn)\n" +
          "\t\tthrows BaseException {\n\n" +
          "\t\tCollection " + beanInstance + "Coll = new Vector();\n\n" +
          "\t\tString query = \"select * from \" + TABLE + \" \" +\n" +
          "\t\t\tgetWhereClause(criteria);\n\n" +
          "\t\tDataSet dataSet = new SqlManager().select(conn, query);\n\n" +
          "\t\tIterator dataIter = dataSet.iterator();\n\n" +
          "\t\twhile (dataIter.hasNext()) {\n" +
          "\t\t\tDataSetRow dataSetRow = (DataSetRow)dataIter.next();\n" +
          "\t\t\t" + beanName + " " + beanInstance + " = new " + beanName +
          "();\n" +
          "\t\t\tload(dataSetRow, " + beanInstance + ");\n" +
          "\t\t\t" + beanInstance + "Coll.add(" + beanInstance + ");\n" +
          "\t\t}\n\n" +
          "\t\treturn " + beanInstance + "Coll;\n" +
          "\t}\n" +
          "}";

      printWriter.print(outStr);
      printWriter.close();
      sqlResult.close();
      sqlStatement.close();
    }
    catch (IOException ioe) {
      System.out.println("IO ERROR:" + ioe.getMessage());
    }
    catch (SQLException e) {
      System.out.println("SQL ERROR:" + e.getMessage());
    }
  }
}
