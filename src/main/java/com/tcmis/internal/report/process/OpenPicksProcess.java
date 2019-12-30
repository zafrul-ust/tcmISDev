package com.tcmis.internal.report.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReprintPicklistIdViewBean;
import com.tcmis.internal.report.beans.OpenPicksInputBean;
import com.tcmis.internal.report.beans.OpenPicksViewBean;

/**
 * ****************************************************************** Process for the OpenPicksProcess Section
 * 
 * @version 1.0 *****************************************************************
 */

public class OpenPicksProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public OpenPicksProcess(String client, String locale) {
		super(client, locale);
	}

	public OpenPicksProcess(String client) {
		super(client);
	}

	public Collection<OpenPicksViewBean> getSearchResult(OpenPicksInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory openPicksFactory = new GenericSqlFactory(dbManager, new OpenPicksViewBean());

		SearchCriteria searchCriteria = new SearchCriteria();
		SortCriteria sortCcriteria = new SortCriteria();
		sortCcriteria.setSortAscending(true);
		sortCcriteria.addCriterion("opsEntityId,inventoryGroup");

		if (!StringHandler.isBlankString(inputBean.getOpsEntityId()))
			searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		if (!StringHandler.isBlankString(inputBean.getHub()))
			searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
		if (!StringHandler.isBlankString(inputBean.getInventoryGroup()))
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
		if ((!StringHandler.isBlankString(inputBean.getPicklistId())) && (!("All").equalsIgnoreCase(inputBean.getPicklistId())))
			searchCriteria.addCriterion("picklistId", SearchCriterion.EQUALS, inputBean.getPicklistId());

		Collection<OpenPicksViewBean> c = openPicksFactory.select(searchCriteria, sortCcriteria, OPEN_PICKS_TABLE_NAME);

		return c;

	}

	public ExcelHandler getExcelReport(OpenPicksInputBean bean, PersonnelBean personnelBean, Locale locale) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

		Collection<OpenPicksViewBean> data = getSearchResult(bean);

		ExcelHandler pw = new ExcelHandler(library);
		BigDecimal total = new BigDecimal("0");

		pw.addTable();
		/*		pw.addRow();
		pw.addTdEmpty(13);
		pw.addTh("label.total");
		for (OpenPicksViewBean member : data) {
			if(null!=member.getExtPrice())
			{
				total = total.add(member.getExtPrice());
			}

		}
		pw.addCellBold(total);    */

		pw.addRow();

		String[] headerkeys = { "label.operatingentity", "label.hub", "label.inventorygroup", "label.picklistid", "label.customer", "label.shipto", "label.customerpo", "label.salesorder", "label.partnum", "label.description", "label.quantity",
				"label.currencyid", "label.priceeach", "label.dateprinted", "label.value", "label.daysold", "label.supplierdateaccepted", "label.neededbyoron", "label.needdate" };
		int[] widths = { 12, 12, 12, 12, 12, 20, 12, 12, 12, 12, 12, 12, 12, 12, 12, 8, 14 };
		int[] types = { pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_NUMBER, pw.TYPE_STRING, pw.TYPE_STRING, pw.TYPE_NUMBER, pw.TYPE_STRING, pw.TYPE_STRING, ExcelHandler.TYPE_PARAGRAPH, pw.TYPE_NUMBER, pw.TYPE_STRING, pw.TYPE_NUMBER,
				pw.TYPE_CALENDAR, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, pw.TYPE_STRING, pw.TYPE_CALENDAR };

		int[] aligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		pw.setColumnDigit(12, 4);
		pw.setColumnDigit(14, 2);

		for (OpenPicksViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getOperatingEntityName());
			pw.addCell(member.getHubName());
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getPicklistId());
			pw.addCell(member.getCustomerName());
			pw.addCell(member.getShipToLocationDesc());
			pw.addCell(member.getCustomerPoLine());
			pw.addCell(member.getSalesOrder());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getPartDescription());
			pw.addCell(member.getPickQty());
			if (member.getUnitPrice() == null) {
				pw.addCell("");
			}
			else {
				pw.addCell(member.getCurrencyId());
			}
			pw.addCell(member.getUnitPrice());
			pw.addCell(member.getPicklistPrintDate());
			pw.addCell(member.getExtPrice());
			pw.addCell(member.getDaysOld());
			pw.addCell(member.getSupplierDateAccepted());
			pw.addCell(member.getNeedByPrefix());
			pw.addCell(member.getNeedDate());
			
			if (member.getExtPrice() !=null)
			    total = total.add(member.getExtPrice());

		}
		
		pw.addRow();
		pw.addTdEmpty(13);
		pw.addTh("label.total");
		pw.addCellBold(total);
		return pw;

	}

	public Collection<ReprintPicklistIdViewBean> getPickListId(OpenPicksInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		GenericSqlFactory openPicksFactory = new GenericSqlFactory(dbManager, new ReprintPicklistIdViewBean());

		SearchCriteria searchCriteria = new SearchCriteria();

		StringBuilder queryStr = new StringBuilder("select distinct PICKLIST_ID,PICKLIST_PRINT_DATE from REPRINT_PICKLIST_ID_VIEW ");

		if (!StringHandler.isBlankString(inputBean.getHub())) {
			queryStr.append(" where HUB = ").append(inputBean.getHub()).append(" and PACKING_GROUP_ID IS NOT NULL order by PICKLIST_PRINT_DATE desc ");
		}
		Collection<ReprintPicklistIdViewBean> c = openPicksFactory.selectQuery(queryStr.toString());

		return c;

	}

	private static final String OPEN_PICKS_TABLE_NAME = "OPEN_PICKS_VIEW";
	private static final String PICK_LIST_TABLE_NAME = "REPRINT_PICKLIST_ID_VIEW ";
}