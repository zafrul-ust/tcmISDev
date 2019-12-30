package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.CylinderLocation;

public class CylinderLocationSearchProcess extends BaseScannerSearchProcess {
	public CylinderLocationSearchProcess(String client) {
		super(client);
	}

	public JSONArray getCylinderLocations(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new CylinderLocation());
		setPagingConf(input);

		StringBuilder query = new StringBuilder(getSQL(input));

		@SuppressWarnings("unchecked")
		Collection<CylinderLocation> beans = factory.selectQuery(query.toString());
		
		JSONObject supplier = null;
		JSONArray locations = null;
		String currentSupplier = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);

		for (CylinderLocation bean : beans) {
			if (!bean.getSupplier().equals(currentSupplier)) {
				currentSupplier = bean.getSupplier();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}				
				supplier = new JSONObject();
				supplier.put("supplier", bean.getSupplier());
				supplier.put("supplierName", bean.getSupplierName());
				locations = new JSONArray();
				supplier.put("locations", locations);
				results.put(supplier);
			}
			if (totalCount < currentPageStart || totalCount > currentPageEnd) {
				continue;
			}
			bean.setSupplierName(null);
			bean.setSupplier(null);
			locations.put(BeanHandler.getJsonObject(bean, false));

		}

		return results;
	}

	protected String getSQL(JSONObject input) throws JSONException {
		StringBuilder query = new StringBuilder("SELECT s.supplier, s.supplier_name , l.location_id, l.location_desc, c.status, NVL(c.last_updated, c.date_inserted) last_updated");
		query.append(" FROM supplier s, cylinder_tracking_location c, customer.location l");
		query.append(" WHERE s.supplier = c.supplier AND l.company_id = c.company_id AND l.location_id = c.location_id");
		if (input.has("supplier")) {
			query.append(" AND c.supplier = ").append(SqlHandler.delimitString(input.getString("supplier")));
		}
		if (input.has("filterDate")) {
			query.append(" AND NVL(c.last_updated, c.date_inserted) >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}
		query.append(" ORDER BY supplier, location_id");
		return query.toString();

	}

}
