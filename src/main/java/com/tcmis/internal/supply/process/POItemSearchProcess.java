package com.tcmis.internal.supply.process;

import java.io.PrintWriter;

import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Iterator;

import java.util.Locale;

import org.apache.commons.logging.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.supply.beans.POItemSearchInputBean;

import com.tcmis.internal.supply.beans.POItemBean;
import com.tcmis.internal.supply.factory.POItemBeanFactory;

/******************************************************************************
* Process used by POItemSearchAction
* @version 1.0
*****************************************************************************/

public class POItemSearchProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public POItemSearchProcess(String client) 
	{
		super(client);
	}  

	public Collection getPOItemBeanCollection (POItemSearchInputBean inputBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient());
		POItemBeanFactory factory = new POItemBeanFactory(dbManager);
  
		Collection c = factory.getPOItemBeanCollection (inputBean );
		//log.debug("pOItemBean collection size: [" + c.size() + "]; "); 
  
		return c;
	}
	
}
