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
import com.tcmis.internal.supply.beans.PoViewBean;

/******************************************************************************
* Process used by TcmBuyHistoryAction
* @version 1.0
*****************************************************************************/

public class TcmBuyHistoryProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public TcmBuyHistoryProcess(String client) 
	{
		super(client);
	}  

	public Collection getPoViewBeanCollection (PoViewBean inputBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());		
		SearchCriteria searchCriteria = new SearchCriteria();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoViewBean());  
		
		if(null!=inputBean.getItemId())
		{
		     searchCriteria.addCriterion("itemId", SearchCriterion.EQUALS, inputBean.getItemId().toString() );	
		}
		if(null!=inputBean.getSupplier())
		{
			 searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS, inputBean.getSupplier() );	
		}
        if(null!=inputBean.getOpsEntityId())
		{
			 searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId() );
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "radianPo" );
		sortCriteria.setSortAscending( false );
		Collection<PoViewBean> c = factory.select(searchCriteria, sortCriteria, TABLE_NAME);
		
		return c;	
	}
	
	
	 private static final String TABLE_NAME ="PO_VIEW";
	
}
