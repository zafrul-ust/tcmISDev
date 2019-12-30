package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.ws.scanner.beans.Part;

public class PartSearchProcess extends BaseScannerSearchProcess {
	public PartSearchProcess(String client) {
		super(client);
	}

	public JSONArray getResults(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Part());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<Part> beans = factory.selectQuery(getQuerySQL(input, false));
		for (Part bean : beans) {
			JSONObject part = BeanHandler.getJsonObject(bean, true);
			if (!bean.hasStorageTemp()) {
				part.put("storageTemp", bean.getAltStorageTemp());
			}
			part.remove("altStorageTemp");
			part.remove("minTemp");
			part.remove("maxTemp");
			part.remove("tempUnits");
			results.put(part);
		}

		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getQuerySQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		StringBuilder select = new StringBuilder("SELECT   p.item_id,");
		select.append("   p.part_id");
		if (!countOnly) {
			select.append(",   DECODE (gm.trade_name, NULL, gm.material_desc, gm.trade_name) name,");
			select.append("   gm.material_id,");
			select.append("   gm.material_desc,");
			select.append("   FX_HAZARD_CLASS(p.item_id) hazard_class,");
			select.append("   FX_PART_DOT( p.item_id, ';', p.material_id ) AS DOT,");
			select.append("   i.item_desc,");
			select.append("   i.case_qty,");
			select.append("   DECODE (i.item_type, 'MV', 'Y', 'N') mv_item,");
			select.append("   NVL(i.inseparable_kit, 'N'),");
			select.append("   MAN.MFG_ID,");
			select.append("   MAN.MFG_SHORT_NAME mfg_name,");
			select.append("   p.mfg_part_no,");
			select.append("   p.pkg_style,");
			select.append("   p.grade,");
			select.append("   p.dimension,");
			select.append("   p.storage_temp,");
			select.append("   p.min_temp,");
			select.append("   p.max_temp,");
			select.append("   p.temp_units,");
			select.append("   p.part_size,");
			select.append("   p.size_unit,");
			select.append("   p.shelf_life_basis,");
			select.append("   p.shelf_life_days,");
			select.append("   p.components_per_item,");
			select.append("   CASE");
			select.append("	  WHEN p.date_item_verified IS NULL");
			select.append("		   AND ia.item_last_modified IS NULL");
			select.append("	  THEN");
			select.append("		 TO_DATE ('01-Jan-2004', 'DD-MON-YYYY')");
			select.append("	  WHEN ia.item_last_modified IS NULL");
			select.append("	  THEN");
			select.append("		 p.date_item_verified");
			select.append("	  ELSE");
			select.append("		 ia.item_last_modified");
			select.append("   END");
			select.append("	  AS last_modified,");
			select.append("   PKG_HUB_AUTOMATION.FX_GET_LATEST_ITEM_IMAGE (p.ITEM_ID) image_file_name");
		}

		StringBuilder body = new StringBuilder(" FROM global.part p, global.material gm, item i, manufacturer man");
		body.append(", (SELECT   item_id, MAX (modified_datetime) item_last_modified FROM   global.item_audit GROUP BY   item_id) ia");
		body.append(" WHERE p.material_id = gm.material_id AND p.item_id = i.item_id AND gm.mfg_id = man.mfg_id  AND p.item_id = ia.item_id(+)");
		if (input.has("filterDate")) {
			body.append(" AND (ia.item_last_modified >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
			body.append(" OR p.date_item_verified >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate"))).append(")");
		}

		return getWrappedSQL(select.toString(), body.toString(), "ORDER BY p.item_id, p.part_id", countOnly);

	}

}
