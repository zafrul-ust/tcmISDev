package com.tcmis.ws.tablet.process;

import java.util.Collection;
import org.json.JSONArray;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.tablet.beans.CycleCountingInputBean;
import com.tcmis.ws.tablet.beans.HubCycleCountViewBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class CycleCountingProcess extends GenericProcess {

	public CycleCountingProcess(String client) {
		super(client);
	}

	public JSONArray getHubCycleCount(TabletInputBean input, PersonnelBean user, String dateFormat) throws BaseException {		
		factory.setBean(new HubCycleCountViewBean());

		StringBuilder query = new StringBuilder("SELECT COUNT_ID, ROOM, BIN, RECEIPT_ID, EXPECTED_QUANTITY, ACTUAL_QUANTITY, ITEM_DESC ");
		query.append(" FROM HUB_CYCLE_COUNT_VIEW ");
		query.append(" WHERE HUB = ");
		query.append(input.getHub());
		//query.append(" AND ROWNUM <= 10");
		query.append(" AND COUNT_ID IN");
		query.append(" (SELECT COUNT_ID FROM HUB_COUNT_DATE ");
		query.append(" WHERE HUB = ");
		query.append(input.getHub());
		query.append(" AND COUNT_STATUS = 'Open' AND COUNT_TYPE = 'Cycle Count')");
		@SuppressWarnings("unchecked")
		
		Collection<HubCycleCountViewBean> beans = factory.selectQuery(query.toString());

		JSONArray results = new JSONArray();

		for (HubCycleCountViewBean bean : beans) {
			results.put(BeanHandler.getJsonObject(bean, dateFormat));
		}

		return results;
	}
	
	public String updateCycleCount(CycleCountingInputBean input) throws Exception {
		String message = "";
		
		try {
			StringBuilder query = new StringBuilder("UPDATE INVENTORY_COUNT");
			query.append(" SET ACTUAL_QUANTITY = " + input.getActualQuantity().toString());
			query.append(" WHERE COUNT_ID = " + input.getCountId().toString());
			query.append(" AND RECEIPT_ID = " + input.getReceiptId().toString());
			
			factory.deleteInsertUpdate(query.toString());
		}
		catch (Exception e) {
			message = "Error updating count: " + e;
			log.error(message, e);
		}

		return message;
	}
}
