package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.DailyInventoryItemViewBean;
import com.tcmis.internal.hub.beans.DailyInventoryItemViewTotalsBean;
import com.tcmis.internal.hub.beans.DailyInventoryReportInputBean;
import com.tcmis.internal.hub.beans.InventoryGroupCollectionBean;
import com.tcmis.internal.hub.factory.DailyInventoryItemViewBeanFactory;
import com.tcmis.internal.hub.factory.InventoryGroupCollectionBeanFactory;

/******************************************************************************
 * Process for receiving
 * 
 * @version 1.0
 *****************************************************************************/
public class DailyInventoryReportProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public DailyInventoryReportProcess(String client) {
		super(client);
	}

	public DailyInventoryReportProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getSearchResult(DailyInventoryReportInputBean bean,
			Collection hubInventoryGroupOvBeanColl) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		DailyInventoryItemViewBeanFactory factory = new DailyInventoryItemViewBeanFactory(dbManager);
		
		String sortBy = null;
		SearchCriteria criteria = new SearchCriteria();
		// add inventory group to criteria if not "All"
		HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(
				this.getClient());
		boolean iscollection = hubInventoryGroupProcess.isCollection(
				hubInventoryGroupOvBeanColl, bean.getHub(),
				bean.getInventoryGroup());

		if (bean.getInventoryGroup() != null
				&& !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

			if (iscollection) {
				// get inventory groups for that collection
				SearchCriteria invCriteria = new SearchCriteria();
				invCriteria.addCriterion("hub", SearchCriterion.EQUALS,
						bean.getHub());
				invCriteria.addCriterion("inventoryGroupCollection",
						SearchCriterion.EQUALS, bean.getInventoryGroup());
				InventoryGroupCollectionBeanFactory invFactory = new InventoryGroupCollectionBeanFactory(
						dbManager);
				Iterator iterator = invFactory.select(invCriteria).iterator();
				int count = 0;
				while (iterator.hasNext()) {
					InventoryGroupCollectionBean inventoryGroupCollectionBean = (InventoryGroupCollectionBean) iterator
							.next();
					if (count == 0) {
						criteria.addCriterion("inventoryGroup",
								SearchCriterion.EQUALS,
								inventoryGroupCollectionBean
										.getInventoryGroup());
					} else {
						criteria.addValueToCriterion("inventoryGroup",
								inventoryGroupCollectionBean
										.getInventoryGroup());
					}
					count++;
				}
			} else if (bean.getInventoryGroup().length() > 0) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
						bean.getInventoryGroup());
			}
		}

		/*
		 * if (bean.getDailyDate() != null ) {
		 * criteria.addCriterion("dailyDate", SearchCriterion.FROM_DATE,
		 * bean.getDailyDate()); criteria.addCriterion("dailyDate",
		 * SearchCriterion.TO_DATE, bean.getDailyDate()); }
		 */
		if (bean.getDailyDate() != null) {
			criteria.addCriterion("dailyDate", SearchCriterion.GREATER_THAN,
					DateHandler.formatDate(bean.getDailyDate(), "MM/dd/yyyy",
							this.getLocaleObject()));
		}
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			// Date dailyDate =
			// com.tcmis.common.util.DateHandler.getDateFromString("MM/dd/yyyy",
			// bean.getDailyDate());
			Date dailyDate = bean.getDailyDate();
			calendar.setTime(dailyDate);
			calendar.add(calendar.DATE, 1);
			dailyDate = calendar.getTime();
			if (bean.getDailyDate() != null) {
				criteria.addCriterion(
						"dailyDate",
						SearchCriterion.LESS_THAN_OR_EQUAL_TO,
						DateHandler.formatDate(dailyDate, "MM/dd/yyyy",
								this.getLocaleObject()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return factory.select(criteria);
	}

	public DailyInventoryItemViewTotalsBean getTotalBean(Collection results) {
		DailyInventoryItemViewTotalsBean dailyInventoryItemViewTotalsBean = new DailyInventoryItemViewTotalsBean();
		for (DailyInventoryItemViewBean dailyInventoryItemViewBean : (Vector<DailyInventoryItemViewBean>) results) {
			dailyInventoryItemViewTotalsBean.setHaasMaterialValue(dailyInventoryItemViewBean.getHaasMaterialValue());
			dailyInventoryItemViewTotalsBean.setCustMaterialValue(dailyInventoryItemViewBean.getCustMaterialValue());
			dailyInventoryItemViewTotalsBean.setConsMaterialValue(dailyInventoryItemViewBean.getConsMaterialValue());
			dailyInventoryItemViewTotalsBean.setSumMaterialValue(dailyInventoryItemViewBean.getSumMaterialValue());
			dailyInventoryItemViewTotalsBean.setHaasFullValue(dailyInventoryItemViewBean.getHaasFullValue());
			dailyInventoryItemViewTotalsBean.setCustomerFullValue(dailyInventoryItemViewBean.getCustomerFullValue());
			dailyInventoryItemViewTotalsBean.setConsignedFullValue(dailyInventoryItemViewBean.getConsignedFullValue());
			dailyInventoryItemViewTotalsBean.setSumFullValue(dailyInventoryItemViewBean.getSumFullValue());
			dailyInventoryItemViewTotalsBean.setCurrencyId(dailyInventoryItemViewBean.getCurrencyId());
		}

		return dailyInventoryItemViewTotalsBean;
	}

	public ExcelHandler writeExcelFile(Collection searchCollection,
			DailyInventoryItemViewTotalsBean dailyInventoryItemViewTotalsBean,
			Locale locale) throws Exception {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();

		// write column headers
		pw.addRow();
		pw.addThRegion("label.partnumber", 2, 1);
		pw.addThRegion("label.itemid", 2, 1);
		pw.addThRegion("label.description", 2, 1);
		pw.addThRegion("label.quantity", 1, 4);
		pw.addThRegion("label.pkg", 2, 1);
		pw.addThRegion("label.currencyid", 2, 1);
		pw.addThRegion("dailyInventoryreport.label.materialvalue", 1, 4);
		pw.addThRegion("dailyInventoryreport.label.fullvalue", 1, 4);

		pw.addRow();
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");

		pw.addTh("label.haas");
		pw.addTh("label.consigned");
		pw.addTh("label.customer");
		pw.addTh("label.total");

		pw.addCell("");
		pw.addCell("");

		pw.addTh("label.haas");
		pw.addTh("label.consigned");
		pw.addTh("label.customer");
		pw.addTh("label.total");

		pw.addTh("label.haas");
		pw.addTh("label.consigned");
		pw.addTh("label.customer");
		pw.addTh("label.total");

		// DecimalFormat valuesFormat = new DecimalFormat("########,##0.00##");
		// valuesFormat.setMinimumFractionDigits(2);
		String[] headerkeys = { "label.partnumber", "label.itemid",
				"label.description", "label.haas", "label.consigned",
				"label.customer", "label.total", "label.pkg",
				"label.currencyid", "label.haas", "label.consigned",
				"label.customer", "label.total", "label.haas",
				"label.consigned", "label.customer", "label.total" };
		int[] types = { 0, 0, ExcelHandler.TYPE_PARAGRAPH, 0, 0, 00, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };

		int[] widths = { 16, 12, 0, 0, 12, 12, 12, 30, 0, 0, 12, 12, 12, 0, 12,
				12, 12 };

		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.setColumnHeader(headerkeys, types, widths, horizAligns);
		pw.applyColumnWidth();

		pw.setColumnDigit(3, 2);
		for (int i = 5; i < 8; i++)
			pw.setColumnDigit(i, 2);
		pw.setColumnDigit(8, 4);
		for (int i = 9; i < 12; i++)
			pw.setColumnDigit(i, 2);
		pw.setColumnDigit(12, 4);

		// print rows
		Iterator i11 = searchCollection.iterator();
		while (i11.hasNext()) {
			pw.addRow();
			DailyInventoryItemViewBean dailyInventoryItemViewBean = (DailyInventoryItemViewBean) i11.next();

			pw.addCell(dailyInventoryItemViewBean.getCatPartNo());
			pw.addCell(dailyInventoryItemViewBean.getItemId());
			pw.addCell(dailyInventoryItemViewBean.getItemDesc());
			pw.addCell((dailyInventoryItemViewBean.getHaasOwenedQty() != null) ? new BigDecimal(dailyInventoryItemViewBean.getHaasOwenedQty()) : new BigDecimal(0));
			pw.addCell((dailyInventoryItemViewBean.getConsignedQty() != null) ? new BigDecimal(dailyInventoryItemViewBean.getConsignedQty()) : new BigDecimal(0));
			pw.addCell((dailyInventoryItemViewBean.getCustomerOwenedQty() != null) ? new BigDecimal(dailyInventoryItemViewBean.getCustomerOwenedQty()) : new BigDecimal(0));
			pw.addCell(dailyInventoryItemViewBean.getQuantity());
			pw.addCell(dailyInventoryItemViewBean.getPackaging());
			pw.addCell(dailyInventoryItemViewBean.getCurrencyId());
			pw.addCell(dailyInventoryItemViewBean.getHaasMaterialValue());
			pw.addCell(dailyInventoryItemViewBean.getConsMaterialValue());
			pw.addCell(dailyInventoryItemViewBean.getCustMaterialValue());
			pw.addCell(dailyInventoryItemViewBean.getSumMaterialValue());
			pw.addCell(dailyInventoryItemViewBean.getHaasFullValue());
			pw.addCell(dailyInventoryItemViewBean.getConsignedFullValue());
			pw.addCell(dailyInventoryItemViewBean.getCustomerFullValue());
			pw.addCell(dailyInventoryItemViewBean.getSumFullValue());
		}

		pw.setColumnDigit(3, 2);
		for (int i = 5; i < 8; i++)
			pw.setColumnDigit(i, 2);
		pw.setColumnDigit(8, 4);
		for (int i = 9; i < 12; i++)
			pw.setColumnDigit(i, 2);
		pw.setColumnDigit(12, 4);

		pw.addRow();
		pw.addRow();
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");
		pw.addCell("");
		pw.addCellKeyBold("label.total");
		pw.addCell(dailyInventoryItemViewTotalsBean.getCurrencyId());

		pw.addCell(dailyInventoryItemViewTotalsBean.getHaasMaterialValue());

		pw.addCell(dailyInventoryItemViewTotalsBean.getConsMaterialValue());

		pw.addCell(dailyInventoryItemViewTotalsBean.getCustMaterialValue());

		pw.addCell(dailyInventoryItemViewTotalsBean.getSumMaterialValue());

		pw.addCell(dailyInventoryItemViewTotalsBean.getHaasFullValue());

		pw.addCell(dailyInventoryItemViewTotalsBean.getConsignedFullValue());

		pw.addCell(dailyInventoryItemViewTotalsBean.getCustomerFullValue());

		pw.addCell(dailyInventoryItemViewTotalsBean.getSumFullValue());

		return pw;
	}

}
