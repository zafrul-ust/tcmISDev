package com.tcmis.internal.supply.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.supply.beans.InventoryGroupSelectionBean;

import radian.tcmis.common.util.SqlHandler;

public class SelectInvnGroupProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public SelectInvnGroupProcess(String client) {
		super(client);
	}

	public Collection<InventoryGroupSelectionBean> createInventoryGroupList(String locationId) {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new InventoryGroupSelectionBean());
		Collection<InventoryGroupSelectionBean> inventoryGroupcol = null;

		String query = "select * from location_to_ig_view where LOCATION_ID = " + SqlHandler.delimitString(locationId) + " order by inventory_group_name";

		try {
			inventoryGroupcol = factory.selectQuery(query);
		}
		catch (Exception e) {		
			log.error(e.getMessage());
		}
		return inventoryGroupcol;
	}
	
	public HashMap<String, String> getAddDetails(String locationId) {
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new InventoryGroupSelectionBean());
		HashMap<String, String> additionalData = new HashMap<String, String>();
		String opsEntityId = "";
		String currencyId = "";
		try {
			Collection<InventoryGroupSelectionBean> addInfo = factory.selectQuery("select distinct home_currency_id, ops_entity_id from location_to_ig_view where LOCATION_ID = " + SqlHandler.delimitString(locationId));
			Iterator it = addInfo.iterator();
			while(it.hasNext()) {
				InventoryGroupSelectionBean bean = (InventoryGroupSelectionBean)it.next();
				opsEntityId = bean.getOpsEntityId();
				currencyId = bean.getHomeCurrencyId();
			}
			if (opsEntityId != null && !opsEntityId.equals("") && currencyId != null && !currencyId.equals("")) {
				additionalData.put("currencyId", currencyId);
				additionalData.put("opsEntityId", opsEntityId);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return additionalData;
	}
	
	
}
