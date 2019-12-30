package com.tcmis.internal.supply.process;

import java.io.PrintWriter;

import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.supply.beans.POTextInputBean;
import com.tcmis.internal.supply.beans.BpoNotesViewBean;
import com.tcmis.internal.supply.factory.BpoNotesBeanFactory;
import com.tcmis.internal.supply.beans.BpoNotesViewBean;
import com.tcmis.internal.supply.factory.BpoNotesViewBeanFactory;

/******************************************************************************
* Process used by POTextBNAction
* @version 1.0
*****************************************************************************/

public class BlanketNotesProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public BlanketNotesProcess(String client) 
	{
		super(client);
	}  

	public Collection getPOTextBeanCollection(String pONumber) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		Collection c;

		BpoNotesViewBeanFactory factory = new BpoNotesViewBeanFactory(dbManager);
	    SearchCriteria criteria = new SearchCriteria();
	    SortCriteria sortCriteria = new SortCriteria();
	    criteria.addCriterion("bpo", SearchCriterion.EQUALS, pONumber );			
		sortCriteria.addCriterion("noteDate");
		sortCriteria.setSortAscending(false);
		c = factory.select(criteria, sortCriteria );
		//log.debug("POText collection size: [" + c.size() + "]; "); 
		return c;
	}
	
	public void insertPoNotes( BpoNotesViewBean  poNotesBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		Date systemDate = new Date();
		BpoNotesBeanFactory factory = new BpoNotesBeanFactory(dbManager);
		factory.insert(poNotesBean);  		  
		return;
	}	
}


 

