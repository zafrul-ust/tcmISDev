package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.CabinetMrViewBean;
import com.tcmis.client.common.beans.CustomerMrTrackingInputBean;
import com.tcmis.client.common.beans.LineAllocationViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/*******************************************************************
 * 
 * @version 1.0
 * 
 * 
 ********************************************************************/

public class CustomerMrTrackingProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public CustomerMrTrackingProcess(String client, Locale locale) {
		super(client, locale);
	}

	@SuppressWarnings("unchecked")
	public Collection<CabinetMrViewBean> getMrResults(CustomerMrTrackingInputBean input) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CabinetMrViewBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterionArray("prNumber", SearchCriterion.IN, input.getPrNumbers());

		SortCriteria sort = new SortCriteria();
		sort.addCriterion("prNumber");
		sort.addCriterion("lineItem");

		try {
			return factory.select(criteria, sort, "CABINET_MR_VIEW");
		}
		finally {
			dbManager = null;
			factory = null;
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<LineAllocationViewBean> getAllocationResults(CustomerMrTrackingInputBean input) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new LineAllocationViewBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterionArray("prNumber", SearchCriterion.IN, input.getPrNumbers());

		SortCriteria sort = new SortCriteria();
		sort.addCriterion("prNumber");
		sort.addCriterion("lineItem");

		try {
			return factory.select(criteria, sort, "LINE_ALLOCATION_VIEW");
		}
		finally {
			dbManager = null;
			factory = null;
		}
	}

}
