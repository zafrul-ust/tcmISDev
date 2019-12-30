package com.tcmis.supplier.wbuy.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.wbuy.beans.OrderListInputBean;
import com.tcmis.supplier.wbuy.beans.ProblemRejectionWbuyViewBean;
import com.tcmis.supplier.wbuy.beans.WbuyStatusViewBean;
import com.tcmis.supplier.wbuy.factory.ProblemRejectionWbuyViewBeanFactory;
import com.tcmis.supplier.wbuy.factory.UserSupplierViewBeanFactory;
import com.tcmis.supplier.wbuy.factory.WbuyStatusViewBeanFactory;

/******************************************************************************
 * Process for the order list section
 * @version 1.0
 *****************************************************************************/

/**
 * Change History -------------- 03/23/09 - Shahzad Butt - Recreated the process to bring search results and also for populating excel spreadsheet with search results.
 */
public class OrderListProcess extends BaseProcess {
	private static final String Collection = null;

	public OrderListProcess(String client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	Log log = LogFactory.getLog(this.getClass());

	/**
	 * Gets the search results from the DB for a given search criteria.
	 * 
	 * @param inputBeanObj
	 * @return Collection<WbuyStatusViewBean>
	 * @throws BaseException
	 */
	public Collection<WbuyStatusViewBean> getSearchResultsUsingSuppliers(OrderListInputBean inputBeanObj) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		WbuyStatusViewBeanFactory wBuyStatusFactoryObj = new WbuyStatusViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		StringBuilder supplierList = new StringBuilder();
		StringBuilder statusList = new StringBuilder();
		boolean isConfirmedChecked = false;
		if (inputBeanObj.getSupplierIdArray() != null && inputBeanObj.getSupplierIdArray().length > 0) {

			if (inputBeanObj.getSupplierIdArray().length == 1) {
				criteria.addCriterion("supplier", SearchCriterion.EQUALS, inputBeanObj.getSupplierIdArray()[0].toString());
			}
			else {
				for (int i = 0; i < inputBeanObj.getSupplierIdArray().length; i++) {
					if (inputBeanObj.getSupplierIdArray()[i].length() > 0) {
						if (!inputBeanObj.getSupplierIdArray()[i].toString().equalsIgnoreCase("All"))
							supplierList.append("'").append(inputBeanObj.getSupplierIdArray()[i].toString()).append("'");
						if ((i + 1) < inputBeanObj.getSupplierIdArray().length)
							supplierList.append(",");
					}

				}
				criteria.addCriterion("supplier", SearchCriterion.IN, supplierList.toString().toLowerCase(), SearchCriterion.IGNORE_CASE);
			}

		}

		if ("bystatus".equalsIgnoreCase(inputBeanObj.getShowPOs())) {
			if (inputBeanObj.getPoStatusChkbxArray() != null && inputBeanObj.getPoStatusChkbxArray().length > 0) {

				if (inputBeanObj.getPoStatusChkbxArray().length == 1) {
					criteria.addCriterion("dbuyStatus", SearchCriterion.EQUALS, inputBeanObj.getPoStatusChkbxArray()[0].toString());
				}
				else {
					for (int i = 0; i < inputBeanObj.getPoStatusChkbxArray().length; i++) {
						if (inputBeanObj.getPoStatusChkbxArray()[i].length() > 0) {
							statusList.append("'").append(inputBeanObj.getPoStatusChkbxArray()[i].toString()).append("'");
							if ((i + 1) < inputBeanObj.getPoStatusChkbxArray().length)
								statusList.append(",");
							if ("confirmed".equalsIgnoreCase(inputBeanObj.getPoStatusChkbxArray()[i].toString())) {
								isConfirmedChecked = true;
							}
						}

					}
					criteria.addCriterion("dbuyStatus", SearchCriterion.IN, statusList.toString().toLowerCase(), SearchCriterion.IGNORE_CASE);
				}

			}
		}
		else if ("bypo".equalsIgnoreCase(inputBeanObj.getShowPOs())) {
			criteria.addCriterion("radianPo", SearchCriterion.EQUALS, inputBeanObj.getPoNumber());
		}

		if (isConfirmedChecked) {
			/* Searching for FROM date */
			if (inputBeanObj.getConfirmedFromDate() != null) {
				criteria.addCriterion("dateConfirmed", SearchCriterion.FROM_DATE, inputBeanObj.getConfirmedFromDate());
			}
			/* Searching for TO date */
			if (inputBeanObj.getConfirmedToDate() != null) {
				criteria.addCriterion("dateConfirmed", SearchCriterion.TO_DATE, inputBeanObj.getConfirmedToDate());
			}
		}

		Collection<WbuyStatusViewBean> c = wBuyStatusFactoryObj.select(criteria, null);

		return c;
	}

	/**
	 * Generates the Excel spreadsheet for a given criteria by searching the DB and then populating the spreadsheet with the resultset.
	 * 
	 * @param inputBeanObj
	 * @param locale
	 * @return ExcelHandler
	 * @throws NoDataException
	 * @throws BaseException
	 */
	public ExcelHandler getExcelReport(OrderListInputBean inputBeanObj, Locale locale) throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<WbuyStatusViewBean> data = getSearchResultsUsingSuppliers(inputBeanObj);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.po.num", "label.company", "label.shipto", "label.date.created", "label.critical", "label.status", "label.first.viewed", "label.confirmed", "label.promised.ship.date", "label.est.dock.date", "label.comments" };

		/*This array defines the type of the excel column.
		0 means default depending on the data type. */
		int[] types = { pw.TYPE_NUMBER, pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_CALENDAR, pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_CALENDAR, pw.TYPE_STRING, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH,

		};
		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 0, 10, 0, 0, 0, 0, 0, 12, 0, 0, 30 };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER, 0, pw.ALIGN_CENTER, 0 };

		int[] aligns = null;

		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		int i = 1;
		for (WbuyStatusViewBean member : data) {
			i++;
			pw.addRow();

			pw.addCell(member.getRadianPo());
			pw.addCell(member.getOperatingEntityName());
			pw.addCell(member.getShipToLocationId());
			pw.addCell(member.getDateCreated());
			pw.addCell(member.getCritical());
			pw.addCell(member.getDbuyStatus());
			pw.addCell(member.getDateAcknowledgement());
			pw.addCell(member.getDateConfirmed());
			pw.addCell(member.getVendorShipDate());
			pw.addCell(member.getPromisedDate());
			pw.addCell(member.getComments());

		}
		return pw;
	}

	public Collection<WbuyStatusViewBean> searchOrder(String poNum) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		WbuyStatusViewBeanFactory wBuyStatusFactoryObj = new WbuyStatusViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(poNum)) {
			criteria.addCriterion("radian_po", SearchCriterion.EQUALS, poNum);
		}
		Collection<WbuyStatusViewBean> c = wBuyStatusFactoryObj.select(criteria, null);

		return c;
	}

	/**
	 * For a given radianPo, returns the Collection<ProblemRejectionWbuyViewBean> containing details for problems, rejections etc.
	 * 
	 * @param radianPo
	 * @return Collection<ProblemRejectionWbuyViewBean>
	 */
	public Collection<ProblemRejectionWbuyViewBean> getProblemsRejects(ProblemRejectionWbuyViewBean inputBean) {

		ProblemRejectionWbuyViewBeanFactory prwvbFactory = null;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		prwvbFactory = new ProblemRejectionWbuyViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();

		if (inputBean.getRadianPo() != null) {

			criteria.addCriterion("radianPo", SearchCriterion.EQUALS, "" + inputBean.getRadianPo() + "");
		}

		Collection<ProblemRejectionWbuyViewBean> c = null;
		try {
			c = prwvbFactory.select(criteria);
		}
		catch (BaseException e) {
			// TODO Auto-generated catch block
			log.error("exception in getProblemsRejects: " + e);
		}

		return c;

	}

	public Collection getSearchDropDown(BigDecimal personnelId) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		UserSupplierViewBeanFactory factory = new UserSupplierViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, personnelId.toString());
		return factory.select(criteria, null);
	}
}