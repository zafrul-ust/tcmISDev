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
import com.tcmis.internal.hub.beans.AdrViewBean;
import com.tcmis.internal.hub.beans.DotShippingNameInputBean;

/******************************************************************************
 * Process used by DotShippingNameAction
 * @version 1.0
 *****************************************************************************/

public class AdrShippingNameProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public AdrShippingNameProcess(String client)
	{
		super(client);
	}

	public Collection getAdrBeanCollection(DotShippingNameInputBean bean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new AdrViewBean());
		SearchCriteria searchCriteria = new SearchCriteria();
		String searchArgument = "";
		if (bean.getSearchArgument() != null )
		{
			searchArgument = bean.getSearchArgument();
		}

		if (log.isDebugEnabled()) {
			log.debug(" preparing search to find materialDescription like the Search Argument: [" +   searchArgument + "]; ");
		}
		searchCriteria.addCriterion("nameAndDescription", SearchCriterion.LIKE, searchArgument, "Y" );
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "adrId" );
		Collection c = factory.select(searchCriteria, sortCriteria,"ADR_VIEW");

		//log.debug("Cfr49HazardousMaterial collection size: [" + c.size() + "]; ");

		return c;
	}
	
	public Collection checkDupShippingName(String id) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new AdrViewBean());
		String query = "select * from adr_proper_shipping_name  where adr_id =  " + id;
		return  factory.selectQuery(query);
	}
	
}


