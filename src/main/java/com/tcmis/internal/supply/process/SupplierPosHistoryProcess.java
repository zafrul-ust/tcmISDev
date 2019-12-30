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
import com.tcmis.internal.supply.beans.SupplierPurchaseHistoryViewBean;

/******************************************************************************
* Process used by TcmBuyHistoryAction
* @version 1.0
*****************************************************************************/

public class SupplierPosHistoryProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public SupplierPosHistoryProcess(String client) 
	{
		super(client);
	}  

	public Collection getSupplierPurchaseHistoryViewBeanCollection (SupplierPurchaseHistoryViewBean inputBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());		
		SearchCriteria searchCriteria = new SearchCriteria();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SupplierPurchaseHistoryViewBean());  
		
		if(null!=inputBean.getSupplier())
		{
			 searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS, inputBean.getSupplier() );	
		}
		
		SortCriteria sortCriteria = new SortCriteria();
//		sortCriteria.addCriterion( "radianPo" );
//		sortCriteria.setSortAscending( false );
		Collection<SupplierPurchaseHistoryViewBean> c = factory.select(searchCriteria, sortCriteria, TABLE_NAME);
		
		return c;	
	}
	
	
	 private static final String TABLE_NAME ="SUPPLIER_PURCHASE_HISTORY_VIEW";
	
}
