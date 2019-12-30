package com.tcmis.internal.report.process;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.Region;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseExcelReportProcess;
import com.tcmis.common.util.DataSet;
import com.tcmis.internal.report.beans.AdhocWasteReportBean;
import com.tcmis.internal.report.factory.GenericReportFactory;

/******************************************************************************
 * Process to create Ad hoc waste excel report
 * @version 1.0
 *****************************************************************************/
public class AdhocWasteReportProcess
    extends BaseExcelReportProcess {
  Log log = LogFactory.getLog(this.getClass());

  public AdhocWasteReportProcess(String client) {
    super(client);
  }

      /*****************************************************************************
   * Process for creating adhoc usage reports
       ****************************************************************************/
/*
  public HSSFWorkbook getReport(AdhocWasteReportBean bean) throws BaseException,
      Exception {
    if (log.isInfoEnabled()) {
      log.info("calling adhoc usage report process");
    }
    //get data
    DbManager dbManager = new DbManager(getClient());
    GenericReportFactory factory = new GenericReportFactory(dbManager);
    DataSet dataSet = factory.getDataSetForAdhocWaste(bean);

    //create workbook
    HSSFWorkbook wb = new HSSFWorkbook();
    //first create input sheet with parameters
    HSSFSheet reportSheet = wb.createSheet("Input");
    //widen the columns
    reportSheet.setDefaultColumnWidth( (short) 20);
    //remove grid
    reportSheet.setDisplayGridlines(false);
    //header row
    HSSFRow row = reportSheet.createRow( (short) 0);
    row.createCell( (short) 0).setCellValue("Ad Hoc Waste Report");
    //merge report name header cells
    reportSheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 1));
    row.getCell( (short) 0).setCellStyle(getBoldBorderCellStyle(wb));
    row.createCell( (short) 1);
    row.getCell( (short) 1).setCellStyle(getBoldBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 1);
    //date row
    row = reportSheet.createRow( (short) 2);
    row.createCell( (short) 0).setCellValue("Date:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(new Date());
    row.getCell( (short) 1).setCellStyle(getLeftBorderDateCellStyle(wb,
        "m/d/yy h:mm"));
    //sub header row
    row = reportSheet.createRow( (short) 3);
    row.createCell( (short) 0).setCellValue("Input:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1);
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    //paramter rows starts from here
    row = reportSheet.createRow( (short) 4);
    row.createCell( (short) 0).setCellValue("Waste:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    if (bean.getType() != null && bean.getType().equalsIgnoreCase("All")) {
      row.createCell( (short) 1).setCellValue(bean.getType());
    }
    else if (bean.getType() != null &&
             bean.getType().equalsIgnoreCase("Profile")) {
      row.createCell( (short) 1).setCellValue(bean.getProfileId());
    }
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 5);
    row.createCell( (short) 0).setCellValue("Facility:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getFacilityID());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 6);
    row.createCell( (short) 0).setCellValue("Work Area:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getWorkAreaName());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 7);
    row.createCell( (short) 0).setCellValue("Accumulation Point:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getAccumePt());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 8);
    row.createCell( (short) 0).setCellValue("Vendor:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getVendor());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 9);
    row.createCell( (short) 0).setCellValue("Management Option:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getMgmtOption());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 10);
    row.createCell( (short) 0).setCellValue("Begin:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getBeginMonth() +
                                            "/" +
                                            bean.getBeginYear());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    row = reportSheet.createRow( (short) 11);
    row.createCell( (short) 0).setCellValue("End:");
    row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
    row.createCell( (short) 1).setCellValue(bean.getEndMonth() +
                                            "/" +
                                            bean.getEndYear());
    row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
    this.createOutputSheet(wb, dataSet);
    return wb;
  }
*/
}