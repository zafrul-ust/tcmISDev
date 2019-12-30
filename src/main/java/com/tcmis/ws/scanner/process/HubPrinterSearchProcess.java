package com.tcmis.ws.scanner.process;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.HubPrinter;

public class HubPrinterSearchProcess extends BaseScannerSearchProcess {

	private final String SUPPORT_ARRAY_NAME = "supports";
	private ResourceLibrary library;
	
	public HubPrinterSearchProcess(String client) {
		super(client);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}
	
	@SuppressWarnings("unchecked")
	private Collection<HubPrinter> getPrinterColl(JSONObject input, Collection<HubPrinter> pidColl) throws BaseException, JSONException {
		Vector<HubPrinter> printerColl = new Vector<HubPrinter>();
		int count = 0;
		StringBuilder ids = new StringBuilder();
		for (HubPrinter printer : pidColl) {
			count++;
			if (count > 1) {
				ids.append(",");
			}
			ids.append(printer.getPrinterId());
			if (count == 100) {
				printerColl.addAll(factory.selectQuery(getPrinterSql(input, ids.toString())));
				count = 0;
				ids.setLength(0);
			}
		}
		if (count > 0) {
			printerColl.addAll(factory.selectQuery(getPrinterSql(input, ids.toString())));
		}

		return printerColl;
	}

	public JSONArray getHubPrinters(JSONObject input) throws BaseException {
		JSONArray hubPrinterArray = new JSONArray();
		Connection conn = dbManager.getConnection();
		try {
			setPagingConf(input);
			
			Collection<HubPrinter> pidColl = factory.setBean(new HubPrinter()).selectQuery(getQuerySQL(input, false), conn);
			
			Collection<HubPrinter> printerColl = Collections.emptyList();
			if ( ! pidColl.isEmpty()) {
				printerColl = getPrinterColl(input, pidColl);
			}
			
			int printerId = 0;
			int arrayIndex = -1;
			for (HubPrinter printer : printerColl) {
				int pid = printer.getPrinterId().intValue();
				JSONObject hubPrinter = null;
				if (pid == printerId) {
					hubPrinter = hubPrinterArray.getJSONObject(arrayIndex);
				}
				else {
					printerId = pid;
					arrayIndex++;
					String labelStock = printer.getLabelStock();
					// prevent labelStock from showing up in the JSON Object
					printer.setLabelStock(null);
					hubPrinter = BeanHandler.getJsonObject(printer, false);
					printer.setLabelStock(labelStock);
					
					JSONArray supportArray = new JSONArray();
					hubPrinter.put(SUPPORT_ARRAY_NAME, supportArray);
					
					hubPrinterArray.put(hubPrinter);
				}

				JSONArray supportArray = null;
				if (hubPrinter.has(SUPPORT_ARRAY_NAME)) {
					supportArray = hubPrinter.getJSONArray(SUPPORT_ARRAY_NAME);
				}
				else {
					supportArray = new JSONArray();
				}
				supportArray.put(printer.getLabelStock());
			}
			
			totalCount = pageCount = arrayIndex+1;
			if (pageCount >= pageSize || page != 1) {
				String fullCount = factory.selectSingleValue(getQuerySQL(input, true),conn);
				totalCount = Integer.parseInt(fullCount);
			}
		}
		catch(DbSelectException e) {
			BaseException ex = new BaseException(library.getString(e.getMessageKey()));
			throw ex;
		}
		catch(JSONException e) {
			throw new BaseException(e);
		}
		finally {
			dbManager.returnConnection(conn);
		}
		
		return hubPrinterArray;
	}
	
	protected String getPrinterSql(JSONObject input, String printerIds) throws JSONException {
		String query = "select p.printer_id, llp.printer_location, llp.label_stock, p.printer_path, p.hub"
				+ " from printer p, location_label_printer llp"
				+ " where p.printer_id in (" + printerIds +") and p.printer_path = llp.printer_path"
				+ " and p.hub = llp.hub and p.cups_name is not null";
		if (input.has("hub")) {
			query += " and p.hub = "+SqlHandler.delimitString(input.getString("hub"));
		}
		
		if (input.has("filterDate")) {
			query += " and p.last_updated"
					+ " >= "
					+ DateHandler.getOracleToDateFunction((Date)input.get("filterDate"));
			// filterDate inserted in Action class by isFilterValid method
		}
		query += " order by p.printer_path";
		return query;
	}
	
	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "";
		String orderBy = "";
		if (!countOnly) {
			select = "select distinct p.printer_id";
			orderBy = " order by p.printer_id";
		}
		else {
			select = "select printer_path";
			orderBy = " order by p.printer_path";
		}

		String body = " from printer p where p.cups_name is not null";
		
		if (input.has("hub")) {
			body += " and p.hub = "+SqlHandler.delimitString(input.getString("hub"));
		}
		
		if (input.has("filterDate")) {
			body += " and p.last_updated"
					+ " >= "
					+ DateHandler.getOracleToDateFunction((Date) input.get("filterDate"));
			// filterDate inserted in Action class by isFilterValid method
		}
		
		return getWrappedSQL(select, body, orderBy, countOnly);
	}
}
