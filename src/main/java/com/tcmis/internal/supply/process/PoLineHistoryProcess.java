package com.tcmis.internal.supply.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.PoLineHistoryViewBean;
import com.tcmis.internal.supply.factory.PoLineHistoryViewBeanFactory;

public class PoLineHistoryProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PoLineHistoryProcess(String client) 
	{
		super(client);
	}  

	public Collection getPoLineHistoryCollection(PoLineHistoryViewBean inputBean) 
		throws BaseException, Exception {
		Collection c;
		DbManager dbManager = new DbManager(getClient());

		PoLineHistoryViewBeanFactory factory = new PoLineHistoryViewBeanFactory(dbManager);
	    SearchCriteria criteria = new SearchCriteria();	    
	    criteria.addCriterion("radianPo", SearchCriterion.EQUALS, inputBean.getRadianPo().toPlainString() );
	    criteria.addCriterion("poLine", SearchCriterion.EQUALS, inputBean.getPoLine().toPlainString() );
	    criteria.addCriterion("amendment", SearchCriterion.LESS_THAN, inputBean.getAmendment().toPlainString() );
	    
	    SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("amendment");
		sortCriteria.setSortAscending(false);
		c = factory.select(criteria, sortCriteria );
		return c;
	}
	
}
