package com.tcmis.internal.report.process;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import oracle.sql.CLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbUpdateException;
import com.tcmis.common.exceptions.GeneralException;
import com.tcmis.common.framework.BaseExcelReportProcess;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.report.beans.ExcelReportBean;
import com.tcmis.internal.report.factory.ExcelReportBeanFactory;

/******************************************************************************
 * Process to create "free hand" excel report
 * @version 1.0
 *****************************************************************************/
public class ExcelReportProcess
    extends BaseExcelReportProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ExcelReportProcess(String client) {
    super(client);
  }

      /******************************************************************************
   * Takes the query entered and if it's valid created a html table and save it
   * to the specified path.<BR>
   *
   * @param personnelId  the personnel id of the user running the query
   * @param filePath  path to file where resulting html table will be saved.
   * @param bean  bean holding the input entered by the user
   * @throws DbUpdateException If an non-select query is entered
   * @throws GeneralException If the "pr_query_check" procedure does not return a "Y"
   * @throws BaseException If an <code>SQLException</code> is thrown
   * @throws Exception If an unhandled error is thrown
       ****************************************************************************/
  public void writeExcelFile(int personnelId,
                             String filePath,
                             ExcelReportBean bean) throws
      DbUpdateException, GeneralException, BaseException, Exception {
		if (log.isDebugEnabled()) {
			log.debug("calling excel report process");
		}
		// validate query
		if (!this.validateInput(bean)) {
			if (log.isDebugEnabled()) {
				log.debug("invalid bean");
			}
			throw new DbUpdateException("Only select statements are permitted");
		}
		// get data
		DbManager dbManager = new DbManager(getClient());
		ExcelReportBeanFactory factory = new ExcelReportBeanFactory(dbManager);
		DataSet dataSet = null;
		String message = factory.validateQuery(personnelId, bean.getQuery());
		if (log.isDebugEnabled()) {
			log.debug("VALIDATION:" + message);
		}
		// if procedure doesn't return a 'Y' then throw error
		if (message == null || !message.equalsIgnoreCase("y")) {
			throw new GeneralException(message);
		}
		// System.out.println("total:" + Runtime.getRuntime().totalMemory());
		// System.out.println("free:" + Runtime.getRuntime().freeMemory());
		Locale lcl = getLocaleObject();
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", lcl);
		ExcelHandler pw = new ExcelHandler(library);
		Connection connection = dbManager.getConnection();
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(bean.getQuery());
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			pw.addTable();
			// write column headers
			pw.addRow();
			/* Pass the header keys for the Excel. */
			List<String> headerKeysList = new ArrayList<String>();
			List<Integer> headerTypeList = new ArrayList<Integer>();
			List<Integer> hWidthList = new ArrayList<Integer>();
			for (int i = 1; i <= columnCount; i++) {
				headerKeysList.add(resultSetMetaData.getColumnName(i));
				int width = getColumnType(i, resultSetMetaData);
				headerTypeList.add(width);
				hWidthList.add(getColumnWidth(width));
			}
			String[] headerkeys = new String[headerKeysList.size()];
			headerKeysList.toArray(headerkeys);
			/* This array defines the type of the excel column. 0 means default
			 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
			 * characters. pw.TYPE_CALENDAR set up the date with no time.
			 * pw.TYPE_DATE set up the date with time.*/
			// int[] types = { 0, pw.TYPE_PARAGRAPH };
			int[] types = new int[headerKeysList.size()];
			/* This array defines the default width of the column when the Excel
			 * file opens. 0 means the width will be default depending on the data type.*/
			int[] widths = new int[headerKeysList.size()];
			/* This array defines the horizontal alignment of the data in a
			 * cell. 0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = new int[headerKeysList.size()];
			for (int j = 0; j < headerKeysList.size(); j++) {
				types[j] = headerTypeList.get(j);// 0;//headerTypeList.get(j);
				widths[j] = 0;
				horizAligns[j] = 0;
			}	
			pw.applyColumnHeaderVal(headerkeys, types, widths, horizAligns);
			OutputStream os = new FileOutputStream(filePath);
			while (rs.next()) {
				pw.addRow();
				for (int i = 1; i <= columnCount; i++) {
					pw.addCell(this.emptyIfNull(this.getResultSetValue(rs, i,
							resultSetMetaData)));
				}				
			}
			pw.applyColumnWidth();
			pw.write(os);
		} catch (Exception e) {
			try {
				dbManager.returnConnection(connection);
			} catch (Exception ex) {
				// ignore
			}
			throw e;
		}
		dbManager.returnConnection(connection);
		// System.out.println("total3:" + Runtime.getRuntime().totalMemory());
		// System.out.println("free3:" + Runtime.getRuntime().freeMemory());
		if (log.isDebugEnabled()) {
			log.debug("Done with report");
		}
	}


      /******************************************************************************
   * THIS METHOD IS NOT BEING USED!!!!
   * Takes the query entered and if it's valid created a excel file using the
   * apache poi package.<BR>
   *
   * @param bean  bean holding the input entered by the user
   * @throws DbUpdateException If an non-select query is entered
   * @throws BaseException If an <code>SQLException</code> is thrown
   * @throws Exception If an unhandled error is thrown
       ****************************************************************************/
/*
  public HSSFWorkbook getExcelReport(ExcelReportBean bean) throws
      DbUpdateException, BaseException, Exception {

    if (log.isDebugEnabled()) {
      log.debug("calling excel report process");
    }
    //validate query
    if (!this.validateInput(bean)) {
      if (log.isDebugEnabled()) {
        log.debug("invalid bean");
      }
      throw new DbUpdateException("Only select statements are permitted");
    }
    //get data
    DbManager dbManager = new DbManager(getClient());
    ExcelReportBeanFactory factory = new ExcelReportBeanFactory(dbManager);
    DataSet dataSet = null;

    dataSet = factory.getDataSet(bean.getQuery());

    if (log.isDebugEnabled()) {
      log.debug("DATASET:" + dataSet);
    }
    //create workbook
    HSSFWorkbook wb = new HSSFWorkbook();
    //create output sheet
    this.createOutputSheet(wb, dataSet);
    //create comment sheet
    HSSFSheet reportSheet = wb.createSheet("Comment");
    //widen the columns
    reportSheet.setDefaultColumnWidth( (short) 50);
    //remove grid
    reportSheet.setDisplayGridlines(false);
    //header row
    HSSFRow row = reportSheet.createRow( (short) 0);
    //heighten the row
    row.setHeightInPoints( (float) 50);
    row.createCell( (short) 0).setCellValue(bean.getComment());

    return wb;
  }
*/
  private boolean validateInput(ExcelReportBean bean) {
    if (bean.getQuery().trim().toLowerCase().startsWith("select ")) {
      return true;
    }
    else {
      return false;
    }
  }

  private Object getResultSetValue(ResultSet resultSet,
                                   int columnNumber,
                                   ResultSetMetaData resultSetMetaData) throws
      SQLException {
    Object returnObject;
    switch (resultSetMetaData.getColumnType(columnNumber)) {
      case Types.CHAR:
        returnObject = resultSet.getString(columnNumber);
        break;
      case Types.VARCHAR:
        returnObject = resultSet.getString(columnNumber);
        break;
      case Types.LONGVARCHAR:
        returnObject = resultSet.getAsciiStream(columnNumber);
        break;
      case Types.INTEGER:
        returnObject = new Long(resultSet.getLong(columnNumber));
        break;
      case Types.NUMERIC:
        returnObject = (java.math.BigDecimal)
            resultSet.getBigDecimal(columnNumber);
/*
        if (returnObject != null) {
          //ran into an issue querying views, added this as fix
          //add decimal point if default doesn't have one
//System.out.println("scale:" + resultSetMetaData.getScale(columnNumber));
          if (resultSetMetaData.getScale(columnNumber) < 1) {
            returnObject = resultSet.getBigDecimal(columnNumber).
                setScale(
                this.getScale(returnObject.toString()));
          }
          else {
            returnObject = resultSet.getBigDecimal(columnNumber).
                setScale(
                resultSetMetaData.getScale(columnNumber));
          }

//System.out.println("actual scale:" + ((java.math.BigDecimal)returnObject).scale());
        }
*/
        break;
      case Types.FLOAT:
        returnObject =
            new Float(resultSet.getFloat(columnNumber));
        break;
      case Types.DOUBLE:
        returnObject =
            new Double(resultSet.getDouble(columnNumber));
        break;
      case Types.DATE:
        returnObject = resultSet.getTimestamp(columnNumber);
        break;
      case Types.TIMESTAMP:
        returnObject = resultSet.getTimestamp(columnNumber);
        break;
      case Types.CLOB:
    	CLOB clob= (CLOB) resultSet.getClob(columnNumber);  
    	if(clob != null)
    		returnObject = clob.getSubString((long)1,(int)clob.length());
    	else 
    		returnObject = null;
        break;
      default:
        returnObject = resultSet.getString(columnNumber);
        ;
    }
    return returnObject;
  } // end of getResultSetValue()

      /******************************************************************************
   * Substitute method for <code>resultSetMetaData.getScale</code> which for
   * some reason doesn't return the right value on some views.
   *
   * @param s the string to look for a . character
   * @return an int representing the scale of the string
       *****************************************************************************/
  public int getScale(String s) {
    int scale = 0;
    if (s.indexOf(".") != -1) {
      scale = s.substring(s.indexOf(".")).length();
    }
    return scale;
  }
  
  /******************************************************************************
   * Substitute method for <code>resultSetMetaData.getScale</code> which for
   * some reason doesn't return the right value on some views.
   *
   * @param s the string to look for a . character
   * @return an int representing the scale of the string
   *****************************************************************************/
  private int getColumnType( int columnNumber, ResultSetMetaData resultSetMetaData) throws 
  	SQLException {
		int returnType;
		//System.out.println(resultSetMetaData.getColumnName(columnNumber) + " - " + resultSetMetaData.getColumnType(columnNumber) + " - " + resultSetMetaData.getColumnDisplaySize(columnNumber));
		int size = resultSetMetaData.getColumnDisplaySize(columnNumber);
		switch (resultSetMetaData.getColumnType(columnNumber)) {
			case Types.CHAR:
				returnType = ExcelHandler.TYPE_STRING;
				break;
			case Types.VARCHAR:
				if (size > 50)
					returnType = ExcelHandler.TYPE_PARAGRAPH;
				else 
					returnType = ExcelHandler.TYPE_STRING;
				break;
			case Types.LONGVARCHAR:
				returnType = ExcelHandler.TYPE_PARAGRAPH;
				break;
			case Types.INTEGER:				
			case Types.NUMERIC:
			case Types.FLOAT:
			case Types.DOUBLE:
				returnType = ExcelHandler.TYPE_NUMBER;
				break;			
			case Types.DATE:
				returnType = ExcelHandler.TYPE_DATE;
				break;
			case Types.TIMESTAMP:
				returnType = ExcelHandler.TYPE_CALENDAR;
				break;
			case Types.CLOB:
				returnType = ExcelHandler.TYPE_NONE;
				break;
			default:
				returnType = ExcelHandler.TYPE_NONE;
		}
		return returnType;
	} // end of getResultSetValue()

  /******************************************************************************
   * Substitute method for <code>resultSetMetaData.getScale</code> which for
   * some reason doesn't return the right value on some views.
   *
   * @param s the string to look for a . character
   * @return an int representing the scale of the string
   *****************************************************************************/
  private int getColumnWidth(int columnType) throws 
  	SQLException {
		int returnWidth;
		switch (columnType) {
			case 1: //ExcelHandler.TYPE_STRING
				returnWidth = 15;
				break;
			case 6: //ExcelHandler.TYPE_PARAGRAPH
				returnWidth = 25;
				break;
			case 2: //ExcelHandler.TYPE_NUMBER
				returnWidth = 10;
				break;
			case 3: //ExcelHandler.TYPE_DATE
				returnWidth = 15;
				break;
			case 4: //ExcelHandler.TYPE_CALENDAR
				returnWidth = 15;
				break;
			case 0: //ExcelHandler.TYPE_NONE
				returnWidth = 0;
				break;			
			default:
				returnWidth = 0;
		}
		return returnWidth;
	} // end of getResultSetValue()

   
}