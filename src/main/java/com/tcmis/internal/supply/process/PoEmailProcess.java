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
import com.tcmis.internal.supply.beans.PoEmailViewBean;
import com.tcmis.internal.supply.factory.PoEmailBeanFactory;
import com.tcmis.internal.supply.beans.PoEmailViewBean;
import com.tcmis.internal.supply.factory.PoEmailViewBeanFactory;

/******************************************************************************
* Process used by POTextREAction
* @version 1.0
*****************************************************************************/

public class PoEmailProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public PoEmailProcess(String client) 
	{
		super(client);
	}  

	public Collection getPOTextBeanCollection(String pONumber) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		Collection c;

		PoEmailViewBeanFactory factory = new PoEmailViewBeanFactory(dbManager);
	    SearchCriteria criteria = new SearchCriteria();
	    SortCriteria sortCriteria = new SortCriteria();
	    criteria.addCriterion("radianPo", SearchCriterion.EQUALS, pONumber );			
		sortCriteria.addCriterion("emailDate");
		sortCriteria.setSortAscending(false);
		c = factory.select(criteria, sortCriteria );
		//log.debug("POText collection size: [" + c.size() + "]; "); 
  
		return c;
	}
	
	public void insertPoEmail( PoEmailViewBean  poEmailBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		Date systemDate = new Date();
		PoEmailBeanFactory factory = new PoEmailBeanFactory(dbManager);
		factory.insert(poEmailBean);  
		return;
	}	
}


 

