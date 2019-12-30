package com.tcmis.internal.supply.process;

//import java.io.*;
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
import com.tcmis.internal.supply.beans.CarrierInfoBean;
import com.tcmis.internal.supply.beans.POCarrierInputBean;

/******************************************************************************
* Process used by POCarrierAction
* @version 1.0
*****************************************************************************/

public class POCarrierProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public POCarrierProcess(String client) 
	{
		super(client);
	}  
	
	public POCarrierProcess(String client,String locale) {
	    super(client,locale);
    }

	public Collection getCarrierInfoBeanCollection(POCarrierInputBean inputBean, String inventoryGroup, String source) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CarrierInfoBean());
		SearchCriteria searchCriteria = new SearchCriteria();
		
		if ( inputBean.getSearchArgument() != null && !inputBean.getSearchArgument().equals("") ) {
	    	String mode = inputBean.getSearchMode();
	    	String field = inputBean.getSearchField();
	    	
	    	if( mode.equals("is") )
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.EQUALS,
	    				inputBean.getSearchArgument());
	    	if( mode.equals("contains"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.LIKE,
	    				inputBean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
	    	if( mode.equals("startsWith"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.STARTS_WITH,
	    				inputBean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
	    	if( mode.equals("endsWith"))
	    		searchCriteria.addCriterion(field,
	    				SearchCriterion.ENDS_WITH,
	    				inputBean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
	    }
		
		searchCriteria.addCriterion("status",
				SearchCriterion.EQUALS,
				"ACTIVE",SearchCriterion.IGNORE_CASE);
		
		searchCriteria.addSubCriteria(new SearchCriteria("inventoryGroup", SearchCriterion.EQUALS,inventoryGroup), new SearchCriteria("inventoryGroup", SearchCriterion.EQUALS,"ALL"));
		
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("companyId");
		sortCriteria.addCriterion("shipToLocId");
		sortCriteria.addCriterion("carrierName");
		sortCriteria.addCriterion("carrierCode");
  
		return factory.select(searchCriteria, sortCriteria, "carrier_info");
	}
}
