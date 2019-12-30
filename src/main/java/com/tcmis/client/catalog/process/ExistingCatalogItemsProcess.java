package com.tcmis.client.catalog.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.factory.ExistingCatalogViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class ExistingCatalogItemsProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public ExistingCatalogItemsProcess(String client) {
	super(client);
 }

 public Collection getsearchResult(String catPartNo, String catalogId,
	String companyId) throws BaseException {
	DbManager dbManager = new DbManager(getClient());

	ExistingCatalogViewBeanFactory factory = new ExistingCatalogViewBeanFactory(
	 dbManager);

	SearchCriteria criteria = new SearchCriteria();

	if (catPartNo != null && catPartNo.length() > 0) {
	 criteria.addCriterion("catPartNo", SearchCriterion.LIKE, catPartNo);
	}

	if (catalogId != null && catalogId.length() > 0) {
	 criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
	}

	if (companyId != null && companyId.length() > 0) {
	 criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
	}

	return factory.select(criteria);
 }
}