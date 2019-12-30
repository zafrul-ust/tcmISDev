package com.tcmis.internal.supply.process;

import java.util.Collection;

import org.apache.commons.logging.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.supply.beans.POShipToInputBean;

import com.tcmis.internal.supply.beans.LocationToIgViewBean;
import com.tcmis.internal.supply.factory.ShipToLocationViewBeanFactory;

/******************************************************************************
* Process used by POSupplierAction
* @version 1.0
*****************************************************************************/

public class POShipToProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public POShipToProcess(String client) 
	{
		super(client);
	}  

	public Collection getShipToLocationViewBeanCollection(POShipToInputBean bean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		ShipToLocationViewBeanFactory factory = new ShipToLocationViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		String searchArgument = "";
		if (bean.getSearchArgument() != null ) 
		{  
			searchArgument = bean.getSearchArgument();
		}
		
		searchCriteria.addCriterion("search", SearchCriterion.LIKE, searchArgument, "Y" );
		if (bean.hasCompanyId())
			searchCriteria.addCriterion("companyId", SearchCriterion.EQUALS, bean.getCompanyId());
		
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion( "locationId" );
   
		Collection c = factory.selectDistinct(searchCriteria, sortCriteria);
		//log.debug("shipToLocation collection size: [" + c.size() + "]; "); 
  
		return c;
	}
        
        public Collection getLocationToIgViewBeanCollection(String locationId) throws BaseException, Exception {
            DbManager dbManager = new DbManager(getClient(),getLocale());
            GenericSqlFactory factory = new GenericSqlFactory(dbManager, new LocationToIgViewBean());      
            SearchCriteria criteria = new SearchCriteria();      
            criteria.addCriterion("locationId", SearchCriterion.EQUALS, locationId);

            SortCriteria scriteria = new SortCriteria();      
            return factory.select(criteria,scriteria, "location_to_ig_view");
           
        }
}
