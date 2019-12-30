package com.tcmis.ws.scanner.process;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.SupplierLocation;

public class SupplierLocationSearchProcess extends BaseScannerSearchProcess {
	public SupplierLocationSearchProcess(String client) {
		super(client);
	}

	public JSONArray getLocations(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new SupplierLocation());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<SupplierLocation> beans = factory.selectQuery(getSQL(input, false));
		for (SupplierLocation bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}
		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getSQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getSQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "select ssfl.supplier, l.location_id , ssfl.status, L.LOCATION_DESC ";
		String body = " from supplier_ship_from_location ssfl, location l where ssfl.supplier = ";
		body += SqlHandler.delimitString(input.getString("supplier"));
		body += " AND l.location_id = ssfl.ship_from_location_id";
		body += " AND EXISTS (select null from customer.user_supplier_location us where us.supplier = ssfl.SUPPLIER)";
		return getWrappedSQL(select, body, "ORDER BY ssfl.supplier, l.location_id", countOnly);
	}

}
