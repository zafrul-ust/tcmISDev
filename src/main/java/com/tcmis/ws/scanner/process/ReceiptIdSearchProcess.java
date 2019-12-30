package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.ws.scanner.beans.Receipt;
import com.tcmis.ws.scanner.beans.ReceiptId;

public class ReceiptIdSearchProcess extends BaseScannerSearchProcess {
	protected int	page2		= 1;
	protected int	offsetRows	= 0;

	public ReceiptIdSearchProcess(String client) {
		super(client);
	}

	public JSONArray getResults(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new ReceiptId());
		setPagingConf(input);

		int firstCount = Integer.parseInt(factory.selectSingleValue(getQuerySQL(input, true)));
		int secondCount = Integer.parseInt(factory.selectSingleValue(getQuerySQL2(input, true)));
		totalCount = firstCount + secondCount;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);

		if (currentPageStart <= firstCount) {
			@SuppressWarnings("unchecked")
			Collection<ReceiptId> beans = factory.selectQuery(getQuerySQL(input, false));
			for (ReceiptId receiptId : beans) {
				// Code reproduced from  lines 759-776, com.tcmis.internal.hub.factory.ContainerLabelMasterViewBeanFactory, please keep logic in synch
				if(receiptId.isInseparableKit() || receiptId.isManageKitsAsSingleUnit()){
					receiptId.setBarcodeLot(receiptId.getLotNumber());
				}
				else{
					String query = "select r.receipt_id, r.item_id, p.part_id, NVL(fx_receipt_component_mfg_lot(r.receipt_id, p.item_id, p.part_id ), r.mfg_lot) lot_number"
								+ " from receipt r, part p where r.item_id = p.item_id and r.receipt_id = " + receiptId.getReceiptId() + " order by receipt_id, part_id";
					
					Collection<ReceiptId> components = factory.selectQuery(query); 
							
					// cycle through components and build concatenated mfg lot string
					StringBuffer componentMfgLots = new StringBuffer();					
					for (ReceiptId component : components){
						if(StringUtils.isNotBlank(component.getLotNumber())){
							if(componentMfgLots.length() > 0)
								componentMfgLots.append(";");
							componentMfgLots.append(component.getLotNumber());
						}
					}		
								
					receiptId.setBarcodeLot(componentMfgLots.toString());
				}
				results.put(BeanHandler.getJsonObject(receiptId, false));
				pageCount++;
			}
		}

		if (currentPageEnd > firstCount) {
			offsetRows = pageSize - (firstCount % pageSize);
			if (pageCount == 0) {
				page2 = page - (firstCount / pageSize);
			}
			@SuppressWarnings("unchecked")
			Collection<ReceiptId> beans = factory.selectQuery(getQuerySQL2(input, false));
			for (ReceiptId bean : beans) {
				results.put(BeanHandler.getJsonObject(bean, false));
				pageCount++;
			}
		}

		return results;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		StringBuilder select = new StringBuilder("SELECT r.receipt_id, ");
		if (!countOnly) {
			select.append(" r.receiving_status,");
			select.append(" r.item_id,");
			select.append(" r.bin,");
			select.append(" r.customer_receipt_id,");
			select.append(" (r.quantity_received - r.total_quantity_issued) quantity_on_hand,");
			select.append(" CASE");
			select.append(" WHEN r.receiving_status != 'Binned'");
			select.append(" THEN");
			select.append(" fx_ig_item_last_bin (r.inventory_group, r.item_id)");
			select.append(" ELSE");
			select.append(" r.bin");
			select.append(" END");
			select.append(" AS last_bin,");
			select.append(" R.INTERNAL_RECEIPT_NOTES,");
			select.append(" r.DATE_OF_RECEIPT,");
			select.append(" r.EXPIRE_DATE,");
			select.append(" r.INVENTORY_GROUP,");
			select.append(" r.LOT_STATUS,");
			select.append(" r.MFG_LOT LOT_NUMBER,");
			select.append(" r.original_receipt_id,");
			select.append(" fx_inseparable_item(r.item_id, r.inventory_group ) AS manage_kits_as_single_unit,");
			select.append(" i.inseparable_kit,");
		}
		select.append(" CASE");
		select.append("    WHEN ra.last_modified IS NULL THEN r.transaction_date");
		select.append("    ELSE ra.last_modified");
		select.append(" END");
		select.append("    AS last_updated");
		StringBuilder body = new StringBuilder(" FROM receipt r, (SELECT   receipt_id, MAX (time_stamp) last_modified FROM   receipt_audit GROUP BY   receipt_id) ra ");
		if (!countOnly) {
			body.append(", item i");
		}
		body.append(" WHERE r.receipt_id = ra.receipt_id(+)");
		if (!countOnly) {
			body.append("  AND r.item_id = i.item_id(+)");
		}
		if (input.has("filterDate")) {
			body.append(" AND (r.transaction_date >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
			body.append("  OR ra.last_modified >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate"))).append(")");
		}

		return getWrappedSQL(select.toString(), body.toString(), "ORDER BY r.receipt_id", countOnly);

	}

	protected String getQuerySQL2(JSONObject input, boolean countOnly) throws JSONException {
		StringBuilder select = new StringBuilder("SELECT d.receipt_id, d.deleted_receipt_id ");
		if (!countOnly) {
			select.append(", 'DELETED' receiving_status,");
			select.append(" d.item_id,");
			select.append(" d.bin,");
			select.append(" d.DATE_OF_RECEIPT,");
			select.append(" d.TIME_STAMP last_updated,");
			select.append(" d.lot_status, ");
			select.append(" d.mfg_lot lot_number");
		}
		StringBuilder body = new StringBuilder(" FROM deleted_receipt d");
		if (input.has("filterDate")) {
			body.append(" WHERE d.TIME_STAMP >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}

		return getWrappedSQL(select.toString(), body.toString(), "ORDER BY d.deleted_receipt_id", countOnly, page2, pageSize, offsetRows);
	}
}
