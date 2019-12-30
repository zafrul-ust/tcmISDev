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
import com.tcmis.ws.scanner.beans.Cylinder;
import com.tcmis.ws.scanner.beans.CylinderLocation;

public class CylinderSearchProcess extends BaseScannerSearchProcess {
	public CylinderSearchProcess(String client) {
		super(client);
	}

	public JSONArray getCylinders(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Cylinder());
		setPagingConf(input);

		StringBuilder query = new StringBuilder(getSQL(input, false));

		@SuppressWarnings("unchecked")
		Collection<Cylinder> beans = factory.selectQuery(query.toString());
		for (Cylinder bean : beans) {
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
		String select = "SELECT c.*, M.MANUFACTURER_NAME";
		StringBuilder body = new StringBuilder("FROM cylinder_tracking c, vv_cylinder_manufacturer m");
		body.append(" WHERE M.MANUFACTURER_ID_NO = C.MANUFACTURER_ID_NO");
		body.append(" AND (c.cylinder_tracking_id,c.serial_number,c.manufacturer_id_no)");
		body.append("  IN (select max(cylinder_tracking_id),serial_number,manufacturer_id_no from cylinder_tracking group by serial_number,manufacturer_id_no)");
		if (input.has("supplier")) {
			body.append(" AND c.supplier = ").append(SqlHandler.delimitString(input.getString("supplier")));
		}
		if (input.has("filterDate")) {
			body.append(" AND NVL(c.last_updated, c.date_received) >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}

		return getWrappedSQL(select, body.toString(), "ORDER BY c.manufacturer_id_no, c.serial_number", countOnly);

	}

}
