package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.ws.scanner.beans.InventoryGroup;

public class InventoryGroupSearchProcess extends BaseScannerSearchProcess {
	public InventoryGroupSearchProcess(String client) {
		super(client);
	}

	public JSONArray getResults(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new InventoryGroup());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<InventoryGroup> beans = factory.selectQuery(getQuerySQL(input, false));
		InventoryGroup currentIG = null;
		totalCount = 0;
		int pageStart = page > 1 ? ((page - 1) * pageSize) + 1 : 1;
		int pageEnd = page * pageSize;
		for (InventoryGroup bean : beans) {
			if (currentIG == null) {
				currentIG = bean;
			}
			else if (bean.isSameInventoryGroup(currentIG)) {
				currentIG.addCarrier(bean);
			}
			else {
				totalCount++;
				if (totalCount >= pageStart && totalCount <= pageEnd) {
					results.put(BeanHandler.getJsonObject(currentIG, false));
				}
				currentIG = bean;
			}
		}
		if (totalCount <= pageEnd) {
			totalCount++;
			results.put(BeanHandler.getJsonObject(currentIG, false));				
		}
		pageCount = results.length();

		return results;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT igd.hub, igd.inventory_group, igd.inventory_group_name, igd.manage_kits_as_single_unit, igd.packing_list_num_copies, igd.NONINTEGER_RECEIVING"
				+ ", igd.distributor_ops, igd.last_updated, ica.carrier_account_id, ica.account_company_id, ica.account_company_carrier_code, ica.last_updated carrier_last_updated";
		String body = " FROM  inventory_group_definition igd, ig_carrier_account ica, carrier_account ca"
				+ " WHERE igd.inventory_group != 'All' and igd.inventory_group = ica.inventory_group(+)"
				+ " AND ca.carrier_account_id(+) = ica.carrier_account_id AND ca.use_for_outbound(+) = 'YES'";
		if (input.has("filterDate")) {
			String filterDate = DateHandler.getOracleToDateFunction((Date) input.get("filterDate"));
			body += " and (igd.last_updated >= " + filterDate +
					" OR exists (select null from ig_carrier_account ica where igd.inventory_group = ica.inventory_group and last_updated >= " + filterDate + "))";		 
		}
		body += " UNION ALL" 
				+" SELECT 'All' hub, 'All' inventory_group, 'All' inventory_group_name, NULL manage_kits_as_single_unit, 0 packing_list_num_copies,"
				+ "null noninteger_receiving, null DISTRIBUTOR_OPS, sysdate last_updated," 
				+ "ica.carrier_account_id, ica.account_company_id, ica.account_company_carrier_code, ica.last_updated carrier_last_updated"
				+ " FROM  ig_carrier_account ica, carrier_account ca WHERE  ica.inventory_group = 'ALL'  AND ca.carrier_account_id = ICA.CARRIER_ACCOUNT_ID"
				+ " AND ca.use_for_outbound = 'YES'";
		if (input.has("filterDate")) {
			String filterDate = DateHandler.getOracleToDateFunction((Date) input.get("filterDate"));
			body += " AND exists (select null from ig_carrier_account where inventory_group = 'ALL' and last_updated >= " + filterDate + ")";		 
		}
		
		return select + body +  " ORDER BY hub, inventory_group, carrier_account_id";

	}

}
