package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.scanner.beans.HubRoom;

import radian.tcmis.common.util.SqlHandler;

public class HubBinSearchProcess extends BaseScannerSearchProcess {
	public HubBinSearchProcess(String client) {
		super(client);
	}

	public JSONArray getResults(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new HubRoom());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<HubRoom> beans = factory.selectQuery(getQuerySQL(input, false));
		HubRoom currentRoom = null;
		totalCount = 0;
		int pageStart = page > 1 ? ((page - 1) * pageSize) + 1 : 1;
		int pageEnd = page * pageSize;
		if ( ! beans.isEmpty()) {
			for (HubRoom bean : beans) {
				if (currentRoom == null) {
					currentRoom = bean;
				}
				else if (bean.isSameRoom(currentRoom)) {
					currentRoom.addBin(bean);
				}
				else {
					totalCount++;
					if (totalCount >= pageStart && totalCount <= pageEnd) {
						results.put(BeanHandler.getJsonObject(currentRoom, false));
					}
					currentRoom = bean;
				}
			}
			totalCount++;
			if (totalCount <= pageEnd) {
				results.put(BeanHandler.getJsonObject(currentRoom, false));
			}
		}
		pageCount = results.length();

		return results;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		String select = "SELECT hb.branch_plant hub, "
				+ "nvl(hb.room, " + SqlHandler.delimitString(library.getString("label.general")) + ") AS room"
				+ ", hb.bin, hb.bin_desc, hb.status, hb.on_site, "
				+ "hb.last_updated_date last_modified, nvl(hb.bin_type,'Normal') bin_type, "
				+ "hr.route_order, hr.room_description, hb.route_order AS bin_route_order";
		StringBuilder body = new StringBuilder(" FROM vv_hub_bins hb, vv_hub_room hr WHERE")
				.append(" hr.room(+) = hb.room AND hr.hub(+) = hb.branch_plant AND nvl(bin_type, 'Normal') <> 'Customer OS'");

		if (input.has("hub")) {
			body.append(" AND hb.branch_plant = ").append(SqlHandler.delimitString(input.getString("hub")));
		}
		if (input.has("filterDate")) {
			body.append(" AND hb.last_updated_date >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		} 

		return select + body.toString() + " ORDER BY hb.branch_plant, hb.room, hb.bin";

	}

}
