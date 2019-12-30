package com.tcmis.client.usgov.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.LocationBean;
import com.tcmis.common.admin.factory.UserPageBeanFactory;
import com.tcmis.common.admin.factory.LocationBeanFactory;
import com.tcmis.client.usgov.beans.OrderTrackingInputBean;
import com.tcmis.client.usgov.beans.DlaClientOrderTrackingViewBean;
import com.tcmis.client.usgov.beans.UsgovTcnToMsdsViewBean;
import com.tcmis.client.usgov.factory.DlaClientOrderTrackingViewBeanFactory;
import com.tcmis.client.usgov.factory.UsgovTcnToMsdsViewBeanFactory;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OrderTrackingProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public OrderTrackingProcess(String client) {
		super(client);
	}

	public OrderTrackingProcess(String client, String locale) {
		super(client, locale);
	}

	public boolean hasPagePermission(PersonnelBean bean) throws BaseException {
		boolean hasPermission = false;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("personnelId", SearchCriterion.EQUALS, String.valueOf(bean.getPersonnelId()));
		searchCriteria.addCriterion("pageId", SearchCriterion.EQUALS, "orderTracking");
		UserPageBeanFactory factory = new UserPageBeanFactory(dbManager);
		if (factory.select(searchCriteria, null).size() > 0) {
			hasPermission = true;
		}
		return hasPermission;
	}

	public Collection<UsgovTcnToMsdsViewBean> getLocation(OrderTrackingInputBean bean) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		SearchCriteria searchCriteria = new SearchCriteria("usgovTcn", SearchCriterion.EQUALS, bean.getTcn());
		searchCriteria.addCriterion("shipmentId", SearchCriterion.EQUALS, bean.getShipmentId());
		searchCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, bean.getPrNumber().toString());
		searchCriteria.addCriterion("lineItem", SearchCriterion.EQUALS, bean.getLineItem());

		UsgovTcnToMsdsViewBeanFactory factory = new UsgovTcnToMsdsViewBeanFactory(dbManager);
		return factory.select(searchCriteria, null);
	}

	public LocationBean getAddress(OrderTrackingInputBean bean) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		SearchCriteria searchCriteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, "USGOV");
		searchCriteria.addCriterion("locationId", SearchCriterion.EQUALS, bean.getLocationId());
		LocationBeanFactory factory = new LocationBeanFactory(dbManager);
		LocationBean myBean = null;
		for (LocationBean locationBean : factory.select(searchCriteria, null)) {
			myBean = locationBean;
		}
		return myBean;
	}

	public Collection<DlaClientOrderTrackingViewBean> getSearchData(OrderTrackingInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		SearchCriteria searchCriteria = new SearchCriteria();
		SortCriteria sortCriteria = new SortCriteria();

		if (bean.hasSearchArgument()) {
			if ("callNo".equalsIgnoreCase(bean.getSearchField()) || "deliveryNo".equalsIgnoreCase(bean.getSearchField())) {
				searchCriteria.addCriterion("contractNumber", SearchCriterion.EQUALS, bean.getContractNumber() + "-" + bean.getSearchArgument());
			} else {
				searchCriteria.addCriterion("contractNumber", SearchCriterion.STARTS_WITH, bean.getContractNumber());
				if ("docNumber".equalsIgnoreCase(bean.getSearchField()) || "milstripCode".equalsIgnoreCase(bean.getSearchField()))
					searchCriteria.addCriterion("docNum", SearchCriterion.EQUALS, bean.getSearchArgument());
				else
					searchCriteria.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument());
			}
		} else
			searchCriteria.addCriterion("contractNumber", SearchCriterion.STARTS_WITH, bean.getContractNumber());

		if (!"All".equalsIgnoreCase(bean.getStatus()))
			searchCriteria.addCriterion("orderStatus", SearchCriterion.EQUALS, bean.getStatus(), SearchCriterion.IGNORE_CASE);

		/* Searching for order FROM date */
		if (bean.hasDateFrom()) {
			searchCriteria.addCriterion(bean.getDateOpt(), SearchCriterion.FROM_DATE, bean.getDateFrom());
		}
		/* Searching for order TO date */
		if (bean.hasDateTo()) {
			searchCriteria.addCriterion(bean.getDateOpt(), SearchCriterion.TO_DATE, bean.getDateTo());
		}

		sortCriteria.addCriterion("date997");
		sortCriteria.addCriterion("contractNumber");
		sortCriteria.addCriterion("docNum");
		sortCriteria.addCriterion("arrivalDate");

		DlaClientOrderTrackingViewBeanFactory factory = new DlaClientOrderTrackingViewBeanFactory(dbManager);
		return factory.select(searchCriteria, sortCriteria);
	}

	public ExcelHandler getExcelReportNoSplit(Collection bean, Locale locale) throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<DlaClientOrderTrackingViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.docnumbermillstrip", "label.priority", "label.shiptododaac",
				"label.ultimatedodaac", "label.ordernumber", "label.nsn", "label.nomenclature", "label.usgovorderdate",
				"label.dlametricdate", "label.quantityordered", "label.uom", "label.status",
				"label.transactionquantity", "label.tcn", "label.estimatedshipdate", "label.actualshipdate",
				"label.projectedarrivaldate", "label.carrier", "label.trackingnumber", "label.comments" };

		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type.
		 */
		int[] types = { 0, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_NUMBER, 0, 0, pw.TYPE_NUMBER,
				0, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH };

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 16, 0, 0, 0, 20, 15, 20, 0, 0, 0, 0, 18, 0, 20, 0, 0, 12, 12, 12, 25 };

		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { pw.ALIGN_CENTER, pw.ALIGN_CENTER, pw.ALIGN_CENTER, pw.ALIGN_CENTER, 0, 0, 0, 0, 0, 0, 0,
				pw.ALIGN_CENTER, 0, 0, 0, 0, pw.ALIGN_CENTER, pw.ALIGN_CENTER, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		for (DlaClientOrderTrackingViewBean member : data) {
			pw.addRow();

			pw.addCell(member.getDocNum());
			pw.addCell(member.getPriorityRating());
			pw.addCell(member.getShipToDodaac());
			pw.addCell(member.getMarkForDodaac());
			pw.addCell(member.getContractNumber());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getPartShortName());
			pw.addCell(member.getOrderDate());
			pw.addCell(member.getShipToOkDate());
			pw.addCell(member.getQuantity() != null ? member.getQuantity().toPlainString() : null);
			pw.addCell(member.getUom());
			pw.addCell(member.getOrderStatus());
			pw.addCell(member.getQtyShipped() != null ? member.getQtyShipped().toPlainString() : null);
			pw.addCell(member.getTransportationControlNo());
			if (member.getExpediteDate() != null) {
				pw.addCell(member.getExpediteDate());
			} else {
				pw.addCell(member.getProjectedDateShipped());
			}
			pw.addCell(member.getActualShipDate());
			pw.addCell(member.getArrivalDate());
			pw.addCell(member.getCarrier());
			pw.addCell(member.getTrackingNo());
			pw.addCell(member.getComments());
		}
		return pw;
	}
}