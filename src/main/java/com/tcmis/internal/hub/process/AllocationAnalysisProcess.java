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
 * Process for allocation analysis
 * 
 * @version 1.0
 *****************************************************************************/
public class AllocationAnalysisProcess extends BaseProcess {
	Log	log	= LogFactory.getLog(this.getClass());

	public AllocationAnalysisProcess(String client) {
		super(client);
	}

	public AllocationAnalysisProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getHubDropDownData(BigDecimal personnelId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		HubInventoryGroupFacOvBeanFactory factory = new HubInventoryGroupFacOvBeanFactory(dbManager);
		return factory.selectObject(new SearchCriteria("personnelId", SearchCriterion.EQUALS, personnelId.toString()), new SortCriteria("priority,facilityId"));
	}

	public Collection<PrOpenOrderBean> getSearchData(AllocationAnalysisInputBean input) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		PrOpenOrderBeanFactory factory = new PrOpenOrderBeanFactory(dbManager);

		return factory.select(input);
	}

	public ExcelHandler getExcelReport(Collection bean, Locale locale) throws NoDataException, BaseException, Exception {
		if (log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PrOpenOrderBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.customer", "label.facility", "label.workarea", "label.requestor", "label.csr", "label.mrline", "label.status", "label.releasedate", "label.stockingtype", "label.ownerinventorygroup",
							    "label.partnumber","label.partdescription", "label.customerpartnumber", "label.quantityopen", "label.totalopenvalue", "label.currency", "label.totalquantity", "label.deliverytype", "label.needed", "label.allocationtype",
								"label.ref","label.refline", "label.supplier", "label.mfglot", "label.itemid","label.sourceinventorygroup", "label.quantity", "label.quantityonhand", "label.quantityavailable", "label.status",
								"label.date","label.systemrequireddateontimedate", "label.notes", "label.critical", "label.inventorygrouponhand","label.inventorygroupavailable", "label.lotstatusage", "label.lookaheaddays", "label.oconus" };
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type.
		 */
		int[] types = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, pw.TYPE_PARAGRAPH, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0, pw.TYPE_NUMBER, 0, pw.TYPE_CALENDAR, 0,
						pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0, 0, pw.TYPE_NUMBER, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0,
						pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0, 0 };

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 15, 19, 23, 25, 25, 0, 15, 18, 0, 0,
						 26, 0, 0, 0, 0, 0, 0, 0, 15, 0,
						 0, 0, 22, 12, 0, 15, 0, 10, 11, 12,
						 0, 0, 0, 0, 12, 12, 0, 0, 0 };
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				              0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
							  0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		// int i = 1;
		for (PrOpenOrderBean member : data) {
			// i++;
			String requestor = "";
			if (member.getRequestor() != null) {
				requestor = member.getRequestorLastName() + ", " + member.getRequestorFirstName();
			}

			pw.addRow();
			pw.addCell(member.getCustomerName());
			pw.addCell(member.getFacilityName());
			pw.addCell(member.getApplicationDesc());
			pw.addCell(requestor);
			pw.addCell(member.getCustomerServiceRepName());
			pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
			pw.addCell(member.getReleaseStatus());
			pw.addCell(member.getReleaseDate());
			pw.addCell(member.getItemType());
			pw.addCell(member.getOwnerInventoryGroupName());
			if (member.getGlobalCatalog() != null && member.getGlobalCatalog().equalsIgnoreCase("Y")) {
				pw.addCell(member.getAlternateName());
				pw.addCell(member.getCatalogItemDescription());
			}
			else {
				pw.addCell(member.getFacPartNo());
				pw.addCell(member.getPartDescription());
			}
			pw.addCell(member.getDistCustomerPartList());
			pw.addCell(member.getOpenQuantity());
			if (member.getTotalOpenValue() != null) {
				pw.addCell(member.getTotalOpenValue());
				pw.addCell(member.getCurrencyId());
			}else {
				pw.addCell("");
				pw.addCell("");
			}
			pw.addCell(member.getOrderQuantity());
			pw.addCell(member.getDeliveryType());
			pw.addCell(member.getRequiredDatetime());
			pw.addCell(member.getAllocationType());
			pw.addCell(member.getRefNo());
			pw.addCell(member.getRefLine());
			pw.addCell(member.getSupplier());
			pw.addCell(member.getMfgLot());
			pw.addCell(member.getItemId());
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getAllocQuantity());
			pw.addCell(member.getQtyOnHand());
			pw.addCell(member.getQtyAvailable());
			pw.addCell(member.getRefStatus());
			pw.addCell(member.getRefDate());
			pw.addCell(member.getSystemRequiredDatetime());
			pw.addCell(member.getNotes());
			pw.addCell(member.getCritical());
			pw.addCell(member.getIgQtyOnHand());
			pw.addCell(member.getIgQtyAvailable());
			pw.addCell(member.getLotStatusAge());
			pw.addCell(member.getLookAheadDays());
			pw.addCell(member.getOconus());
		}
		return pw;
	}
}
