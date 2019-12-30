package com.tcmis.internal.supply.process;

import java.util.Collection;
import org.apache.commons.logging.*;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.supply.beans.PoNotesViewBean;
import com.tcmis.internal.supply.factory.PoNotesViewBeanFactory;
import com.tcmis.internal.supply.factory.PoNotesBeanFactory;

/******************************************************************************
* Process used by POTextRNAction
* @version 1.0
*****************************************************************************/

public class PoNotesProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public PoNotesProcess(String client) 
	{
		super(client);
	}  

	public Collection getPOTextBeanCollection(String pONumber) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		Collection c;

		PoNotesViewBeanFactory factory = new PoNotesViewBeanFactory(dbManager);
	    SearchCriteria criteria = new SearchCriteria();
	    SortCriteria sortCriteria = new SortCriteria();
	    criteria.addCriterion("radianPo", SearchCriterion.EQUALS, pONumber );			
		sortCriteria.addCriterion("noteDate");
		sortCriteria.setSortAscending(false);
		c = factory.select(criteria, sortCriteria );
		//log.debug("POText collection size: [" + c.size() + "]; "); 
  
		return c;
	}
	
	public void insertPoNotes( PoNotesViewBean  poNotesBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		PoNotesBeanFactory factory = new PoNotesBeanFactory(dbManager);
		factory.insert(poNotesBean);  
		return;
	}	
}


 

