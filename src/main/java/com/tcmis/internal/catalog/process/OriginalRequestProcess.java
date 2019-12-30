package com.tcmis.internal.catalog.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.catalog.beans.OriginalRequestHeaderBean;
import com.tcmis.internal.catalog.factory.CatalogAddItemBeanFactory;
import com.tcmis.internal.catalog.factory.OriginalRequestHeaderBeanFactory;

/******************************************************************************
 * Process used by OriginalRequestAction
 * @version 1.0
 *****************************************************************************/

public class OriginalRequestProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public OriginalRequestProcess(String client)
	{
		super(client);
	}

	public OriginalRequestHeaderBean getHeaderBean (String requestId) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		OriginalRequestHeaderBeanFactory factory = new OriginalRequestHeaderBeanFactory(dbManager);
		OriginalRequestHeaderBean headerBean = null;
		headerBean = factory.getOriginalRequestHeaderBean ( requestId );

		return headerBean;
	}

	public Collection getCatalogAddItemBeanCollection (String requestId) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		CatalogAddItemBeanFactory factory = new CatalogAddItemBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();

		searchCriteria.addCriterion("requestId", SearchCriterion.EQUALS, requestId );

		//SortCriteria sortCriteria = new SortCriteria();
		//sortCriteria.addCriterion( "radianPo" );
		//sortCriteria.setSortAscending( false );
		Collection c = factory.select(searchCriteria,null); //, sortCriteria);

		if (log.isDebugEnabled()) {
			log.debug("CatalogAddItemBean collection size: [" + c.size() + "]; ");
		}

		return c;
	}

}
