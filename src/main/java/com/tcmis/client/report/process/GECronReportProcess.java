package com.tcmis.client.report.process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.tcmis.client.report.beans.ReceiptDocumentViewerInputBean;
import com.tcmis.client.report.beans.ShippedPartsReportBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GECronReportProcess
    extends BaseProcess {


  public GECronReportProcess(String client) {
    super(client);
  }

  public Collection getCronSearch(String companyId, String facilityId, Date beginConfirmDate, Date endConfirmDate) throws Exception {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		StringBuilder query = new StringBuilder("select * from table (pkg_report_shipped_parts.fx_shipped_parts_report('");
		query.append(StringHandler.emptyIfNull(companyId));
		query.append("','");
		query.append(StringHandler.emptyIfNull(facilityId));
		query.append("',TO_DATE('").append(dateFormatter.format(beginConfirmDate)).append("', 'MM/DD/RRRR hh24:mi:ss')");
		query.append(",TO_DATE('").append(dateFormatter.format(endConfirmDate)).append("', 'MM/DD/RRRR hh24:mi:ss')");
		query.append("))");
		
		genericSqlFactory.setBeanObject(new ShippedPartsReportBean());
		return genericSqlFactory.selectQuery(query.toString());
	} //end of method

	
	public void getCronReport(String companyId, String facilityId) throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		
		ReceiptDocumentViewerInputBean inputBean = new ReceiptDocumentViewerInputBean();
		inputBean.setFacilityId(facilityId);
		inputBean.setDeliveryFromDate(null);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, 30);        
		cal.set(Calendar.SECOND, 0); 
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		// This cron job will be run at 11:30AM EST and 4:30PM EST.
		String filename = "haasgedeliveries";
		if(cal.get(Calendar.HOUR_OF_DAY) == 10) {
	//		filename = "DeliveryReport"+dateFormat.format(cal.getTime())+"AM";
			
			inputBean.setDeliveryToDate(cal.getTime());
			cal.add(Calendar.HOUR, -19);
			inputBean.setDeliveryFromDate(cal.getTime());
		}
		else {
	//		filename = "DeliveryReport"+dateFormat.format(cal.getTime())+"PM";
			
			inputBean.setDeliveryToDate(cal.getTime());
			cal.add(Calendar.HOUR, -5);
			inputBean.setDeliveryFromDate(cal.getTime());
		}
		
		Collection<ShippedPartsReportBean> data = getCronSearch(companyId, facilityId, inputBean.getDeliveryFromDate(), inputBean.getDeliveryToDate());
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//write column headers
		pw.addRow();
		
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.date", "label.cabinetlocation", "label.partnumber", "label.quantitydelivered", "label.unitcost"};
		
		/*This array defines the type of the excel column.
		0 means default depending on the data type. */
		int[] types = { pw.TYPE_CALENDAR, 0, 0, 0, 0};
					
		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 0, 18, 12, 18, 10};
					
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, pw.ALIGN_RIGHT, pw.ALIGN_RIGHT};
	
		pw.setColumnDigit(5, 2);
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		for (ShippedPartsReportBean member : data) {
			pw.addRow();
			pw.addCell(member.getDateShipped());
			pw.addCell(member.getCabinetName());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getQuantityDelivered());
			pw.addCell(member.getUnitCost());
		}
		
		ResourceLibrary resourceLibrary = new ResourceLibrary("GE");
	    File file = new File(resourceLibrary.getString("savedeliveryreportpath")+filename+".xls");
		
	    log.debug(resourceLibrary.getString("savedeliveryreportpath")+filename+".xls");

/*		for (int i=0; file.exists(); i++) {
			filename = filename+i;
			file = new File("c:\\workspace\\"+filename+".xls");
		}   */
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		pw.write(bos);
	}

}