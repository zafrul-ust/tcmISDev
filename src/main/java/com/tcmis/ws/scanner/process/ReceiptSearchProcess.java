package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.ws.scanner.beans.Receipt;

public class ReceiptSearchProcess extends BaseScannerSearchProcess {
	public ReceiptSearchProcess(String client) {
		super(client);
	}

	public JSONArray getReceipts(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Receipt());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<Receipt> beans = factory.selectQuery(getSQL(input, false));
		for (Receipt bean : beans) {
			results.put(BeanHandler.getJsonObject(bean, false));
		}

		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getSQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getSQL(JSONObject input, boolean countOnly) throws JSONException {
		StringBuilder select = new StringBuilder();

		select.append("SELECT");
		select.append(" r.receipt_id,");
		select.append(" r.customer_receipt_id");
		if (!countOnly) {
			select.append(", r.item_id,");
			select.append(" r.EXPIRE_DATE,");
			select.append(" r.MFG_LOT,");
			select.append(" CASE");
			select.append("  WHEN ra.last_modified IS NULL THEN r.transaction_date");
			select.append("  ELSE ra.last_modified");
			select.append(" END");
			select.append(" AS last_updated");
		}

		StringBuilder body = new StringBuilder();
		body.append(" FROM   receipt r, facility_inventory_group fig");
		body.append(", (SELECT   receipt_id, MAX (time_stamp) last_modified FROM receipt_audit GROUP BY receipt_id) ra");
		body.append(" WHERE       fig.company_id = '").append(input.get("companyId")).append("'");
		body.append("  AND fig.facility_id = '").append(input.get("facilityId")).append("'");
		body.append("  AND r.inventory_group = fig.inventory_group");
		body.append("  AND r.receipt_id = ra.receipt_id(+)");
		if (input.has("filterDate")) {
			body.append(" AND (r.transaction_date >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
			body.append("   OR ra.last_modified >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate"))).append(")");
		}
		return getWrappedSQL(select.toString(), body.toString(), "order by r.customer_receipt_id, r.receipt_id asc", countOnly);

	}

}
