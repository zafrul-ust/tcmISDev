package com.tcmis.internal.supply.process;

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
import com.tcmis.internal.supply.beans.OpenScheduleBean;

/******************************************************************************
* Process used by ShowScheduleTcmBuysAction
* @version 1.0
*****************************************************************************/

public class ShowScheduleTcmBuysProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
	private static final String TABLE_NAME ="open_schedule";
	
	public ShowScheduleTcmBuysProcess(String client) 
	{
		super(client);
	}  

	public Collection<OpenScheduleBean> getShowScheduleCollection (OpenScheduleBean inputBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());		
		SearchCriteria searchCriteria = new SearchCriteria();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new OpenScheduleBean());  
		
		if( null != inputBean.getPrNumber()) {
		     searchCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, inputBean.getPrNumber().toString() );	
		}
		
		if(null != inputBean.getLineItem()) {
			 searchCriteria.addCriterion("lineItem", SearchCriterion.EQUALS, inputBean.getLineItem());	
		}
        
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "dateToDeliver" );
		sortCriteria.setSortAscending( true );
		Collection<OpenScheduleBean> c = factory.select(searchCriteria, sortCriteria, TABLE_NAME);
		
		return c;	
	}
	
	
	 
	
}
