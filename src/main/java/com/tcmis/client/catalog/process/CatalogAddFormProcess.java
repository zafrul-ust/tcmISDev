package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogAddFormBean;
import com.tcmis.client.catalog.factory.CatalogAddFormBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

/******************************************************************************
 * Process used by CasNumberSearchAction
 * @version 1.0
 *****************************************************************************/

public class CatalogAddFormProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public CatalogAddFormProcess(String client) {
		super(client);
	}

	public CatalogAddFormProcess(String client, Locale locale) {
		super(client, locale);
	}

	@SuppressWarnings("unchecked")
	public CatalogAddFormBean getForm(String companyId, String requestId, String formId) throws BaseException, Exception {
		CatalogAddFormBeanFactory factory = new CatalogAddFormBeanFactory(new DbManager(getClient(), getLocale()));
		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
		criteria.addCriterion("requestId", SearchCriterion.EQUALS, requestId);
		criteria.addCriterion("formId", SearchCriterion.EQUALS, formId);

		Collection<CatalogAddFormBean> results = factory.select(criteria, null);
		return results.isEmpty() ? null : results.iterator().next();
	}


	/**
	 * Update process to update a particular comment for a given criteria.
	 * 
	 * @param bean
	 * @throws BaseException
	 * @throws Exception
	 */
	public void saveForm(CatalogAddFormBean bean) throws BaseException, Exception {
		CatalogAddFormBeanFactory factory = new CatalogAddFormBeanFactory(new DbManager(getClient(), getLocale()));
		/*
		if (bean.isExistingHmrf()) {
			factory.update(bean);
		}
		else {
			factory.insert(bean);
		}
		*/
		//first delete existing data
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, bean.getCompanyId());
		criteria.addCriterion("requestId", SearchCriterion.EQUALS, bean.getRequestId().toString());
		criteria.addCriterion("formId", SearchCriterion.EQUALS, bean.getFormId());
		factory.delete(criteria);
		//insert new data
		factory.insert(bean);
	}

}
