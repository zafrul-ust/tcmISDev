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
import com.tcmis.internal.hub.beans.Cfr49HazardousMaterialViewBean;
import com.tcmis.internal.hub.beans.DotShippingNameInputBean;
import com.tcmis.internal.hub.factory.Cfr49HazardousMaterialViewBeanFactory;

/******************************************************************************
 * Process used by DotShippingNameAction
 * @version 1.0
 *****************************************************************************/

public class DotShippingNameProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public DotShippingNameProcess(String client)
	{
		super(client);
	}

	public Collection getHazMatBeanCollection(DotShippingNameInputBean bean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		Cfr49HazardousMaterialViewBeanFactory factory = new Cfr49HazardousMaterialViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		String searchArgument = "";
		if (bean.getSearchArgument() != null )
		{
			searchArgument = bean.getSearchArgument();
		}

		if (log.isDebugEnabled()) {
			log.debug(" preparing search to find materialDescription like the Search Argument: [" +   searchArgument + "]; ");
		}
		searchCriteria.addCriterion("hazardousMaterialDescription", SearchCriterion.LIKE, searchArgument, "Y" );
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "hazmatId" );

		Collection c = factory.select(searchCriteria, sortCriteria);

		//log.debug("Cfr49HazardousMaterial collection size: [" + c.size() + "]; ");

		return c;
	}
	
	public Collection checkDupShippingName(String id) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new Cfr49HazardousMaterialViewBean());
		String query = "select * from CFR_49_proper_shipping_name where hazmat_id =  " + id;
		return  factory.selectQuery(query);
	}
	
}


