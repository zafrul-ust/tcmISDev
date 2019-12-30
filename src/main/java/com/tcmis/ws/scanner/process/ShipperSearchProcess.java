package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.ws.scanner.beans.Shipper;

public class ShipperSearchProcess extends BaseScannerSearchProcess {
	public ShipperSearchProcess(String client) {
		super(client);
	}

	public JSONArray getResults(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Shipper());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<Shipper> beans = factory.selectQuery(getQuerySQL(input, false));
		Shipper currentShipper = null;
		totalCount = 0;
		int pageStart = page > 1 ? ((page - 1) * pageSize) + 1 : 1;
		int pageEnd = page * pageSize;
		for (Shipper bean : beans) {
			if (currentShipper == null) {
				currentShipper = bean;
			}
			else if (bean.isSameCarrier(currentShipper)) {
				currentShipper.addAccount(bean);
			}
			else {
				totalCount++;
				if (totalCount >= pageStart && totalCount <= pageEnd) {
					results.put(BeanHandler.getJsonObject(currentShipper, false));
				}
				currentShipper = bean;
			}
		}
		if (totalCount <= pageEnd) {
			totalCount++;
			results.put(BeanHandler.getJsonObject(currentShipper, false));				
		}
		pageCount = results.length();

		return results;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT vc.*, ca.account_company_id, ca.carrier_account, ca.carrier_account_id, ca.status account_status, ca.last_updated account_last_updated, ca.customer_id account_customer_id, ca.notes account_notes";
		String body = " FROM vv_carrier vc, carrier_account ca"
				+ " WHERE ca.carrier_name(+) = vc.carrier_name AND ca.use_for_outbound = 'YES'";
		if (input.has("filterDate")) {
			String filterDate = DateHandler.getOracleToDateFunction((Date) input.get("filterDate"));
			body += " and (vc.last_updated >= " + filterDate +
					" OR exists (SELECT null FROM carrier_account ca WHERE ca.carrier_name = vc.carrier_name and ca.last_updated >= " + filterDate + "))";		 
		}

		return select + body +  " ORDER BY vc.carrier_name, CA.CARRIER_ACCOUNT_ID";

	}

}
