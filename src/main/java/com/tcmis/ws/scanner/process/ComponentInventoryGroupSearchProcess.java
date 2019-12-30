package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.ws.scanner.beans.ComponentInventoryGroup;

public class ComponentInventoryGroupSearchProcess extends BaseScannerSearchProcess {
	protected int	page2		= 1;
	protected int	offsetRows	= 0;

	public ComponentInventoryGroupSearchProcess(String client) {
		super(client);
	}

	public JSONArray getComponentInventoryGroups(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new ComponentInventoryGroup());
		setPagingConf(input);

		int firstCount = Integer.parseInt(factory.selectSingleValue(getSQL(input, true)));
		int secondCount = Integer.parseInt(factory.selectSingleValue(getSQL2(input, true)));
		totalCount = firstCount + secondCount;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);

		if (currentPageStart <= firstCount) {
			@SuppressWarnings("unchecked")
			Collection<ComponentInventoryGroup> beans = factory.selectQuery(getSQL(input, false));
			for (ComponentInventoryGroup bean : beans) {
				results.put(BeanHandler.getJsonObject(bean));
				pageCount++;
			}
		}
		if (currentPageEnd > firstCount) {
			offsetRows = pageSize - (firstCount % pageSize);
			if (pageCount == 0) {
				page2 = page - (firstCount / pageSize);
			}
			@SuppressWarnings("unchecked")
			Collection<ComponentInventoryGroup> beans = factory.selectQuery(getSQL2(input, false));
			for (ComponentInventoryGroup bean : beans) {
				results.put(BeanHandler.getJsonObject(bean));
				pageCount++;
			}
		}

		return results;
	}

	protected String getSQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT cig.*";
		String externalSelect = "";
		if (!countOnly) {
			if (input.has("filterDate")) {
				select += ", cig.last_updated_on AS last_modified";
			}
			else {
				select += ", decode(cig.last_updated_on, null, TO_DATE('2000/01/01','yyyy/mm/dd'), cig.last_updated_on) AS last_modified";
			}
			externalSelect = "fx_quality_control_item_id (a.item_id, a.inventory_group) AS quality_control_item";
		}
		StringBuilder body = new StringBuilder("FROM COMPONENT_INVENTORY_GROUP cig WHERE cig.storage_temp is not null");
		if (input.has("filterDate")) {
			body.append(" AND cig.last_updated_on >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}

		return getWrappedSQL(select, externalSelect, body.toString(), "ORDER BY inventory_group, item_id", countOnly);

	}

	protected String getSQL2(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT	DISTINCT cpi.inventory_group, cpig.item_id, cpi.quality_control quality_control_item";
		if (!countOnly) {
			select += ", 1 part_id, 'CUSTOMER' source, NULL storage_temp";
			if (input.has("filterDate")) {
				select += ", cpi.last_updated AS last_modified";
			}
			else {
				select += ", DECODE(cpi.last_updated, NULL, TO_DATE('2000/01/01', 'yyyy/mm/dd' ), cpi.last_updated ) AS last_modified";
			}
		}
		StringBuilder body = new StringBuilder("FROM customer.catalog_part_inventory cpi, customer.catalog_part_item_group cpig");
		body.append(" WHERE cpi.cat_part_no = cpig.cat_part_no");
		body.append(" AND cpi.catalog_company_id = cpig.company_id");
		body.append(" AND cpi.catalog_id = cpig.catalog_id");
		body.append(" AND cpi.quality_control = 'Y'");
		if (input.has("filterDate")) {
			body.append(" AND cpi.last_updated >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}
		body.append(" AND NOT EXISTS (SELECT	 null");
		body.append("  FROM	 COMPONENT_INVENTORY_GROUP cig");
		body.append("  WHERE		  cig.inventory_group = cpi.inventory_group");
		body.append("  AND cig.item_id = cpig.item_id");
		body.append("  AND cig.storage_temp IS NOT NULL)");

		if (input.has("filterDate")) {
			body.append(" AND cpi.last_updated >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}

		return getWrappedSQL(select, body.toString(), "ORDER BY inventory_group, item_id", countOnly, page2, pageSize, offsetRows);

	}
}
