package com.tcmis.internal.report.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.framework.BaseProcess;

/******************************************************************************
 * Process to create AdhocUsage excel report
 * @version 1.0
 *****************************************************************************/
public class AdhocUsageReportProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public AdhocUsageReportProcess(String client) {
    super(client);
  }

      /*****************************************************************************
   * Process for creating adhoc usage reports
       ****************************************************************************/
  /*
       public HSSFWorkbook getReport(AdhocUsageReportBean bean) throws BaseException,
        Exception {
      if (log.isInfoEnabled()) {
        log.info("calling adhoc usage report process");
      }
      //get data
      GenericReportFactory factory = new GenericReportFactory(getClient());
      DataSet dataSet = factory.getDataSetForAdhocUsage(getDbModel(), bean);
      closeDbModel();
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
      //set row height to twice default height
      //row.setHeightInPoints((float)40);
      row.createCell( (short) 0).setCellValue("Ad Hoc Usage Report");
      //merge report name header cells
      reportSheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
      row.getCell( (short) 0).setCellStyle(getBoldBorderCellStyle(wb));
      row.createCell( (short) 1);
      row.getCell( (short) 1).setCellStyle(getBoldBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 1);
      //date row
      row = reportSheet.createRow( (short) 2);
      row.createCell( (short) 0).setCellValue("Date:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(new Date());
      row.getCell( (short) 1).setCellStyle(getLeftBorderDateCellStyle(wb, "m/d/yy h:mm"));
      //sub header row
      row = reportSheet.createRow( (short) 3);
      row.createCell( (short) 0).setCellValue("Input:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1);
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      //paramter rows starts from here
      row = reportSheet.createRow( (short) 4);
      row.createCell( (short) 0).setCellValue("Chemicals:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      if (bean.getChemType() != null &&
          bean.getChemType().equalsIgnoreCase("List")) {
        row.createCell( (short) 1).setCellValue(bean.getListID());
      }
      else if (bean.getChemType() != null &&
               bean.getChemType().equalsIgnoreCase("Single")) {
        row.createCell( (short) 1).setCellValue(bean.getCASListID());
      }
      else {
        row.createCell( (short) 1).setCellValue("All");
      }
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 5);
      row.createCell( (short) 0).setCellValue("Facility:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getFacilityID());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 6);
      row.createCell( (short) 0).setCellValue("Word Area:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getApplication());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 7);
      row.createCell( (short) 0).setCellValue("Process:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getProcess());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 8);
      row.createCell( (short) 0).setCellValue("Unit Op:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getUnitOp());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 9);
      row.createCell( (short) 0).setCellValue("Emission Point:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getEmissionPt());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 10);
      row.createCell( (short) 0).setCellValue("Part No:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getPartNo());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 11);
      row.createCell( (short) 0).setCellValue("Category:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getCategoryID());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 12);
      row.createCell( (short) 0).setCellValue("Manufacturer:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getManufacturer());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 13);
      row.createCell( (short) 0).setCellValue("Begin:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getBeginMonth() + "/" +
                                              bean.getBeginDay() + "/" +
                                              bean.getBeginYear());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      row = reportSheet.createRow( (short) 14);
      row.createCell( (short) 0).setCellValue("End:");
       row.getCell( (short) 0).setCellStyle(getRightBorderBoldFontCellStyle(wb));
      row.createCell( (short) 1).setCellValue(bean.getEndMonth() + "/" +
                                              bean.getEndDay() + "/" +
                                              bean.getEndYear());
      row.getCell( (short) 1).setCellStyle(getBorderCellStyle(wb));
      this.createOutputSheet(wb, dataSet);
      return wb;
    }
   */
}