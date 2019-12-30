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
import com.tcmis.ws.scanner.beans.CylinderManufacturer;

public class CylinderManufacturerSearchProcess extends BaseScannerSearchProcess {
	public CylinderManufacturerSearchProcess(String client) {
		super(client);
	}

	public JSONArray getCylinders(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new CylinderManufacturer());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<CylinderManufacturer> beans = factory.selectQuery("SELECT * FROM vv_cylinder_manufacturer ORDER BY manufacturer_id_no");
		for (CylinderManufacturer bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}
		totalCount = pageCount = beans.size();
		return results;
	}

}
