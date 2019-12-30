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
//import com.tcmis.internal.supply.beans.POTextInputBean;
import com.tcmis.internal.supply.factory.PoCannedCommentsBeanFactory;
import com.tcmis.internal.supply.beans.PoCannedCommentsBean;

/******************************************************************************
* Process used by POCannedCommentsAction
* @version 1.0
*****************************************************************************/

public class POCannedCommentsProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public POCannedCommentsProcess(String client) 
	{
		super(client);
	}  

	public Collection getPOCannedCommentsBeanCollection() throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		Collection c;

		PoCannedCommentsBeanFactory factory = new PoCannedCommentsBeanFactory(dbManager);
	    SearchCriteria criteria = new SearchCriteria();
	    SortCriteria sortCriteria = new SortCriteria();
	    //criteria.addCriterion("whatever", SearchCriterion.EQUALS, whatever );			
		sortCriteria.addCriterion("comments");
		sortCriteria.setSortAscending(true);
		c = factory.select(criteria, sortCriteria );
		//log.debug("POText collection size: [" + c.size() + "]; "); 
  
		return c;
	}
}


 

