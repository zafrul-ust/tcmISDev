package com.tcmis.internal.hub.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.ReceivingHistoryViewBean;

/******************************************************************************
 * Process for receiving
 * 
 * @version 1.0
 *****************************************************************************/
public class ReceivingHistoryProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public ReceivingHistoryProcess(String client) {
		super(client);
	}

	public ReceivingHistoryProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getReceivingHistory(ReceivingHistoryViewBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ReceivingHistoryViewBean());

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());

		if (bean.getInventoryGroup() != null && !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
			criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
		}

		if (bean.getItemId() != null) {
			criteria.addCriterion("itemId", SearchCriterion.EQUALS, "" + bean.getItemId() + "");
		}

		if (bean.getApproved() != null) {
			criteria.addCriterion("approved", SearchCriterion.EQUALS, bean.getApproved());
		}

		if (bean.getRadianPo() != null) {
			criteria.addCriterion("radianPo", SearchCriterion.EQUALS, "" + bean.getRadianPo() + "");
		}

		if (bean.getPoLine() != null) {
			criteria.addCriterion("poLine", SearchCriterion.EQUALS, "" + bean.getPoLine() + "");
		}
		
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("dateOfReceipt", "desc");
		
		return factory.select( criteria,  sort, "RECEIVING_HISTORY_VIEW");
	}
}