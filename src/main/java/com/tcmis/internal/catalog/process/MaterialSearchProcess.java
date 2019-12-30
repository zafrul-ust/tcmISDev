package com.tcmis.internal.catalog.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.internal.catalog.beans.MaterialBean;
import com.tcmis.internal.catalog.factory.MaterialBeanFactory;

/******************************************************************************
 * Process used by MaterialSearchAction
 * @version 1.0
 *****************************************************************************/

public class MaterialSearchProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public MaterialSearchProcess(String client)
	{
		super(client);
	}

	public Collection getMaterialBeanCollection(String manufacturer, String searchArgument, String excludeMaterialIds) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MaterialBeanFactory factory = new MaterialBeanFactory(dbManager);
		Collection c = factory.select(manufacturer, searchArgument, excludeMaterialIds);
		//log.debug("MaterialBean collection size: [" + c.size() + "]; ");

		return c;
	}
	
	public Collection getMaterialBeanCollection(AutocompleteInputBean inputBean) throws BaseException, Exception
	{
		return getMaterialBeanCollection("", inputBean.getSearchText(),null);
	}
	
	public BigDecimal getNextMaterialId() throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MaterialBeanFactory factory = new MaterialBeanFactory(dbManager);
		BigDecimal materialId = factory.getNextMaterialId();
		// log.debug("materialId.toString() = [" + materialId.toString() + "]; ");

		return materialId;
	}


	public int insertNewMaterial(MaterialBean materialBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MaterialBeanFactory factory = new MaterialBeanFactory(dbManager);
		int result = factory.insert(materialBean );
		if (log.isDebugEnabled()) {
			log.debug("new Integer( result ).toString() = [" + new Integer( result ).toString() + "]; ");
		}

		return result;
	}

}
