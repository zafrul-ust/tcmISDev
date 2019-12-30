package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.InboundShipmentTrackingViewBean;
import com.tcmis.internal.hub.beans.InboundShipmentTrackingInputBean;


/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class InboundShipmentTrackingProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public InboundShipmentTrackingProcess(String client) {
		super(client);
	}

	public InboundShipmentTrackingProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection<InboundShipmentTrackingViewBean> getSearchResults(InboundShipmentTrackingInputBean input, PersonnelBean user) throws BaseException {
		factory.setBean(new InboundShipmentTrackingViewBean());
		
		StringBuilder query = new StringBuilder("SELECT * FROM TABLE(PKG_HUB_AUTOMATION.FX_GET_INBOUND_SHIPMENT_TRACK(");
		query.append("'"+input.getOpsEntityId()+"',");
		query.append("'"+input.getHub()+"',");
		query.append("'"+input.getInventoryGroup()+"',");
		query.append(input.hasEstimatedDeliveryFromDate() ?  DateHandler.getOracleToDateFunction(input.getEstimatedDeliveryFromDate(), "yyyyMMdd", "YYYYMMDD") : "''").append(",");
		query.append(input.hasEstimatedDeliveryToDate() ? DateHandler.getOracleToDateFunction(input.getEstimatedDeliveryToDate(), "yyyyMMdd", "YYYYMMDD") : "''").append(",");;
		query.append("'"+input.getSearchField()+"',");
		query.append("'"+input.getSearchMode()+"',");
		query.append("'"+input.getSearchArgument()+"'");
		query.append("))");
		
		if (input.hasActualDate() || input.isOnlyNotDelivered()) {
			int cntr = 0;
			query.append(" where ");
			if (input.hasActualDeliveryFromDate()) {
				query.append(" ARRIVAL_SCAN_DATE >= " + DateHandler.getOracleToDateFunction(input.getActualDeliveryFromDate()));
				cntr++;
			}
			if (input.hasActualDeliveryToDate()) {
				if (cntr > 0) {
					query.append(" AND");
				}
				query.append(" ARRIVAL_SCAN_DATE <= (" + DateHandler.getOracleToDateFunction(input.getActualDeliveryToDate()) + " + 1)");
				cntr++;
			}
			if (input.isOnlyNotDelivered()) {
				if (cntr > 0) {
					query.append(" AND");
				}
				query.append(" SHIPMENT_STATUS is in ('In Transit','Manual Entry')");
			}
		}
		if (input.hasActualDeliveryToDate() || input.hasActualDeliveryFromDate()) {
			query.append(" order by arrival_scan_date");
		}
		else {
			query.append(" order by estimated_delivery_date");
			
		}
		
		return factory.selectQuery(query.toString());
	}

	public ExcelHandler getExcelReport(Collection<InboundShipmentTrackingViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		
				
		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.operatingentity", "label.hub", "label.inventorygroup", "label.carrier", "label.trackingnumber", 
								"label.transactionnumber", "label.estimatedeliverydate", "label.arrivalscanusername", "label.arrivalscandate", 
								"label.receivingstatus", "label.receivingpriority" };
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0, 0, 
						0, pw.TYPE_CALENDAR, 0, pw.TYPE_CALENDAR, 
						0, 0};

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 15, 15, 15, 20, 20, 
						 20, 0, 15, 0,
						 10, 10};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {0, 0, 0, 0, 0, 
							 0, 0, 0, 0,
							 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);


		for (InboundShipmentTrackingViewBean member : data) {

			pw.addRow();
			pw.addCell(member.getOperatingEntityName());
			pw.addCell(member.getHubName());
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getCarrierParentShortName());
			pw.addCell(member.getTrackingNumber());
			pw.addCell(member.getTransactionNumber());
			pw.addCell(member.getEstimatedDeliveryDate());
			pw.addCell(member.getArrivalScanUser());
			pw.addCell(member.getArrivalScanDate());
			pw.addCell(member.getReceivingStatus());
			pw.addCell(member.getReceivingPriority());
		}
		return pw;
	}
}
