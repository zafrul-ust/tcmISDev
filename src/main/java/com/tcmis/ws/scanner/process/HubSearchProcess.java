package com.tcmis.ws.scanner.process;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.ws.scanner.beans.Hub;

public class HubSearchProcess extends BaseScannerSearchProcess {
	public HubSearchProcess(String client) {
		super(client);
	}


	public JSONArray getResults(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Hub());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<Hub> beans = factory.selectQuery(getQuerySQL(input, false));
		for (Hub bean : beans) {
			results.put(BeanHandler.getJsonObject(bean, false));
		}

		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getQuerySQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT company_id, branch_plant hub, hub_name, home_company_id ";
		StringBuilder body = new StringBuilder(" FROM hub "); 
		
		if (input.has("companyId")) {
			String companyId = input.getString("companyId");
			if ("HAAS".equals(companyId.toUpperCase())) {
				companyId = "Radian";
			}
			body.append(" WHERE company_id = '").append(companyId).append("'");
		}
		
		return getWrappedSQL(select, body.toString(), "ORDER BY branch_plant", countOnly);
		
	}

}
