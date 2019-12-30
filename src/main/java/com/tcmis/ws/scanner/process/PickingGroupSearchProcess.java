package com.tcmis.ws.scanner.process;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.PickingGroup;

public class PickingGroupSearchProcess extends BaseScannerSearchProcess {

	private ResourceLibrary library;
	
	public PickingGroupSearchProcess(String client) {
		super(client);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public JSONArray getPickingGroups(JSONObject input) throws BaseException {
		Connection conn = dbManager.getConnection();
		JSONArray pickingGroupArray = new JSONArray();
		try {
			setPagingConf(input);
			
			@SuppressWarnings("unchecked")
			Collection<PickingGroup> beans = factory.setBean(new PickingGroup()).selectQuery(getQuerySQL(input, false),conn);
			for (PickingGroup bean : beans) {
				pickingGroupArray.put(BeanHandler.getJsonObject(bean, false));
			}
			
			totalCount = pageCount = beans.size();
			if (pageCount >= pageSize || page != 1) {
				String fullCount = factory.selectSingleValue(getQuerySQL(input, true),conn);
				totalCount = Integer.parseInt(fullCount);
			}
			
		}
		catch(DbSelectException e) {
			BaseException ex = new BaseException(library.getString(e.getMessageKey()));
			throw ex;
		}
		catch(JSONException e) {
			throw new BaseException(e);
		}
		finally {
			dbManager.returnConnection(conn);
		}
		
		return pickingGroupArray;
	}
	
	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "select pg.*";
				
		String body = " from picking_group pg";
		String where = " where";
		if (input.has("hub")) {
			body += where + " hub = "+SqlHandler.delimitString(input.getString("hub"));
			where = " and";
		}
		
		if (input.has("filterDate")) {
			body += where+" pg.last_updated "
					+ " >= "
					+ DateHandler.getOracleToDateFunction((Date) input.get("filterDate"));
			// filterDate inserted in Action class by isFilterValid method
		}
		String orderBy = "";
		if ( ! countOnly) {
			orderBy = " order by pg.picking_group_id";
		}
		
		return getWrappedSQL(select, body, orderBy, countOnly);
	}
}
