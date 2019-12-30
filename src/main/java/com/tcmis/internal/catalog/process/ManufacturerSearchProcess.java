package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.factory.ManufacturerBeanFactory;

/******************************************************************************
 * Process used by ManufacturerSearchAction
 * @version 1.0
 *****************************************************************************/

public class ManufacturerSearchProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public ManufacturerSearchProcess(String client)
	{
		super(client);
	}

	public Collection getManufacturerBeanCollection( String searchArgument) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		ManufacturerBeanFactory factory = new ManufacturerBeanFactory(dbManager);
		return factory.select( searchArgument );
	}

	public Collection getManufacturerBeanCollection(AutocompleteInputBean inputBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ManufacturerBean());

		// This is for O'brien. We need to escape the ' in the name.
		String escapedSearchText = inputBean.getSearchText().replace("'", "''");

		StringBuilder queryBuffer = new StringBuilder("select * from manufacturer ");
		queryBuffer.append(" where lower( MFG_DESC || MFG_ID ) like lower('%");
		queryBuffer.append(escapedSearchText);
		queryBuffer.append("%') and  rownum <= ").append(inputBean.getRowNum()).append(" order by MFG_ID, MFG_DESC asc ");
		return factory.selectQuery(queryBuffer.toString());
	}

	public BigDecimal getNextMfgId() throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		ManufacturerBeanFactory factory = new ManufacturerBeanFactory(dbManager);
		return factory.getNextMfgId();
	}


	public int insertNewManufacturer(ManufacturerBean manufacturerBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		ManufacturerBeanFactory factory = new ManufacturerBeanFactory(dbManager);
		int result = factory.insert(manufacturerBean );
		if (log.isDebugEnabled()) {
			log.debug("new Integer( result ).toString() = [" + new Integer( result ).toString() + "]; ");
		}

		return result;
	}


}
