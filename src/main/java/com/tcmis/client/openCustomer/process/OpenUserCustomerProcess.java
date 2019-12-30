package com.tcmis.client.openCustomer.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.openCustomer.factory.OpenUserCustomerOvBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class OpenUserCustomerProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public OpenUserCustomerProcess(String client) {
		super(client);
	}

	public Collection getSearchResult(PersonnelBean user) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		OpenUserCustomerOvBeanFactory factory = new OpenUserCustomerOvBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, "" + user.getPersonnelId());

		SortCriteria sort = new SortCriteria("customerName");

		return factory.select(criteria, sort);
	}
}