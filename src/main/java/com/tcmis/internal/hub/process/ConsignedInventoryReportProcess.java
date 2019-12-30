package com.tcmis.internal.hub.process;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for
 * @version 1.0
 *****************************************************************************/
public class ConsignedInventoryReportProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ConsignedInventoryReportProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getHubDropDownData(BigDecimal personnelId)  throws BaseException {
		Collection c = null;
		DbManager dbManager = new DbManager(getClient(),getLocale());
		HubInventoryGroupFacOvBeanFactory factory = new HubInventoryGroupFacOvBeanFactory(dbManager);
		return factory.selectObject(new SearchCriteria("personnelId", SearchCriterion.EQUALS, personnelId.toString()), new SortCriteria("priority,facilityId"));
	}

	public Collection getReportDataOld(ConsignedInventoryReportInputBean inputBean)  throws BaseException {
		Collection c = null;
		DbManager dbManager = new DbManager(getClient(),getLocale());
		ConsignedInventoryViewBeanFactory factory = new ConsignedInventoryViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, inputBean.getHub());

		if(inputBean.getBeginDate() != null) {
			criteria.addCriterion("shipConfirmDate", SearchCriterion.FROM_DATE, inputBean.getBeginDate());
		}
		if(inputBean.getEndDate() != null) {
			criteria.addCriterion("shipConfirmDate", SearchCriterion.TO_DATE, inputBean.getEndDate());
		}
		if(inputBean.getItemId() != null) {
			criteria.addCriterion("itemId", SearchCriterion.EQUALS, inputBean.getItemId().toString());
		}
		if(!StringHandler.isBlankString(inputBean.getPartNumber())) {
			criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, inputBean.getPartNumber());
		}
		SortCriteria sortCriteria = new SortCriteria("hubName");
		sortCriteria.addCriterion("receiptId");
		sortCriteria.addCriterion("shipConfirmDate");
		return factory.select(criteria, sortCriteria);
	}

	public Collection getReportData(ConsignedInventoryReportInputBean inputBean)  throws BaseException {
		Collection c = null;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ConsignedInventoryViewBeanFactory factory = new ConsignedInventoryViewBeanFactory(dbManager);

		/*   Processed in Factory. 
		 *   if(inputBean.getEndDate() != null) {
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.setTime(inputBean.getEndDate());
	    calendar.add(GregorianCalendar.DATE, 1);
		inputBean.setEndDate(calendar.getTime());
    }
		 */
		return factory.select(inputBean.getHub(), inputBean.getInventoryGroup(),inputBean.getItemId(), inputBean.getBeginDate(), inputBean.getEndDate(), inputBean.getPartNumber());
	}


	public ExcelHandler getExcelReport(ConsignedInventoryReportInputBean inputBean, Locale locale)
	throws Exception
	{
		Collection<ConsignedInventoryViewBean> data = this.getReportData(inputBean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

//		write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.hubname","label.inventorygroup","label.suppliername", "label.haaspo","label.itemid",
				"label.itemdescription","label.quantityissued","label.unitprice"};
		/*This array defines the type of the excel column.
		    0 means default depending on the data type. */
		int[] types = {
				0,0,0,0,0,
				pw.TYPE_PARAGRAPH,0, pw.TYPE_NUMBER};
		/*This array defines the default width of the column when the Excel file opens.
		    0 means the width will be default depending on the data type.*/
		int[] widths = {
				22,22,35,12,0,
				0,12,0};
		/*This array defines the horizontal alignment of the data in a cell.
		    0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				0,0,0,0,0,
				0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		
		//now write data
		for(ConsignedInventoryViewBean bean : data) {
			pw.addRow();
			pw.addCell(bean.getHubName());
			pw.addCell(bean.getInventoryGroup());
			pw.addCell(bean.getSupplierName());
			pw.addCell(bean.getPo());
			pw.addCell(bean.getItemId());
			pw.addCell(bean.getItemDesc());
			pw.addCell(bean.getIssued());
			pw.addCell(bean.getUnitPrice());
		}
		return pw;
	}
	/*
  public void getExcelReport(ConsignedInventoryReportInputBean inputBean, Writer writer, Locale locale) throws
      NoDataException, BaseException, Exception {
    if(log.isDebugEnabled()) {
      log.debug("locale:" + locale);
      log.debug("country:" + locale.getCountry());
      log.debug("language:" + locale.getLanguage());
    }
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
    Collection<ConsignedInventoryViewBean> data = this.getReportData(inputBean);
    PrintWriter pw = new PrintWriter(writer);
    pw.println("<html>");
    pw.println("<table border=\"1\">");
    //write column headers
    pw.println("<tr>");
    pw.addCellKeyBold("label.inventorygroup");
    pw.addCellKeyBold("label.supplierid");
    pw.addCellKeyBold("label.suppliername");
    pw.addCellKeyBold("label.hubid");
    pw.addCellKeyBold("label.hubname");
    pw.addCellKeyBold("label.itemid");
    pw.addCellKeyBold("label.partnumber");
    pw.addCellKeyBold("label.haaspo");
    pw.addCellKeyBold("label.unitprice");
    pw.addCellKeyBold("label.receiptid");
    pw.addCellKeyBold("label.lotnumber");
    pw.addCellKeyBold("label.dateofreceipt");
    pw.addCellKeyBold("label.quantityissued");
    pw.addCellKeyBold("label.shipconfirmdate");
    pw.addCellKeyBold("label.itemdescription");
    pw.println("</tr>");
    //now write data
    for(ConsignedInventoryViewBean bean : data) {
        pw.println("<tr>");
        pw.addCell(bean.getInventoryGroup());
        pw.addCell(bean.getSupplier());
        pw.addCell(bean.getSupplierName());
        pw.addCell(bean.getBranchPlant());
        pw.addCell(bean.getHubName());
        pw.addCell(bean.getItemId());
        pw.addCell(bean.getCatPartNo());
        pw.addCell(bean.getPo());
        pw.addCell(bean.getUnitPrice());
        pw.addCell(bean.getReceiptId());
        pw.addCell(bean.getMfgLot());
        pw.addCell(bean.getDateOfReceipt());
        pw.addCell(bean.getIssued());
        pw.addCell(bean.getShipConfirmDate());
        pw.addCell(bean.getItemDesc());
        pw.println("</tr>");
    }

    pw.println("</table>");
    pw.println("</html>");
    pw.flush();
    pw.close();
  }
	 */
}
