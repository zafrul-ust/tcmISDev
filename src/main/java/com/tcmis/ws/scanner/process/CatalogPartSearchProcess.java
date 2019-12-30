package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.Part;
import com.tcmis.ws.scanner.beans.CatalogPart;

public class CatalogPartSearchProcess extends BaseScannerSearchProcess {
	public CatalogPartSearchProcess(String client) {
		super(client);
	}

	public JSONArray getResults(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new CatalogPart());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<CatalogPart> beans = factory.selectQuery(getQuerySQL(input, false));
		for (CatalogPart bean : beans) {
			results.put(BeanHandler.getJsonObject(bean, true));
		}

		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getQuerySQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		StringBuilder select = new StringBuilder("SELECT cpig.*");
		if (!countOnly) {
			select.append(", cpig.last_updated_on last_updated");
		}

		StringBuilder body = new StringBuilder(" FROM customer.catalog_part_item_group cpig");
		boolean hasWhere = false;
		if (input.has("companyId")) {
			body.append(" WHERE cpig.company_id = ").append(SqlHandler.delimitString(input.getString("companyId")));
			hasWhere = true;
		}
		
		if (input.has("catalogId")) {
			body.append(hasWhere ? " AND" : " WHERE");
			body.append(" cpig.catalog_id = ").append(SqlHandler.delimitString(input.getString("catalogId")));
			hasWhere = true;
		}
		
		
		if (input.has("filterDate")) {
			body.append(hasWhere ? " AND" : " WHERE");
			body.append(" cpig.last_updated_on >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}

		return getWrappedSQL(select.toString(), body.toString(), "ORDER BY cpig.company_id, cpig.catalog_id, cpig.item_id", countOnly);

	}

}
