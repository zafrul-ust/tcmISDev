package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.beans.MsdsAuthorBean;
import com.tcmis.internal.catalog.factory.MsdsAuthorBeanFactory;

/******************************************************************************
 * Process used by NewRevisionDateAction
 * @version 1.0
 *****************************************************************************/

public class MsdsAuthorSearchProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public MsdsAuthorSearchProcess(String client)
	{
		super(client);
	}

	public Collection getMsdsAuthorBeanCollection( String searchArgument) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MsdsAuthorBeanFactory factory = new MsdsAuthorBeanFactory(dbManager);
		return factory.select( searchArgument );
	}
	
	public Collection getMsdsAuthorBeanCollection(AutocompleteInputBean inputBean) throws BaseException, Exception {		
		DbManager dbManager = new DbManager(getClient());
		MsdsAuthorBeanFactory factory = new MsdsAuthorBeanFactory(dbManager);
		return factory.select(inputBean.getSearchText());
	}

	public BigDecimal getNextMsdsAuthorId() throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MsdsAuthorBeanFactory factory = new MsdsAuthorBeanFactory(dbManager);
		return factory.getNextMsdsAuthorId();
	}

	public int insertNewMsdsAuthor(MsdsAuthorBean msdsAuthorBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MsdsAuthorBeanFactory factory = new MsdsAuthorBeanFactory(dbManager);
		int result = factory.insert(msdsAuthorBean );
		if (log.isDebugEnabled()) {
			log.debug("new Integer( result ).toString() = [" + new Integer( result ).toString() + "]; ");
		}

		return result;
	}
	
	
	public int insertNewMsdsAuthor(ManufacturerBean manufacturerBean) throws BaseException, Exception
	{
		MsdsAuthorBean msdsAuthorBean = new MsdsAuthorBean();

		msdsAuthorBean.setMsdsAuthorId(manufacturerBean.getMfgId());
		msdsAuthorBean.setMsdsAuthorDesc(manufacturerBean.getMfgDesc());
		msdsAuthorBean.setContact(manufacturerBean.getContact());
		msdsAuthorBean.setPhone(manufacturerBean.getPhone());
		msdsAuthorBean.setEmail(manufacturerBean.getEmail());
		msdsAuthorBean.setUrl(manufacturerBean.getMfgUrl());
		msdsAuthorBean.setCountryAbbrev(manufacturerBean.getCountryAbbrev());
		msdsAuthorBean.setNotes(manufacturerBean.getNotes());

		return insertNewMsdsAuthor(msdsAuthorBean);
	}

	public MsdsAuthorBean getMsdsAuthor(BigDecimal msdsAuthorId) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		MsdsAuthorBeanFactory factory = new MsdsAuthorBeanFactory(dbManager);
		MsdsAuthorBean msdsAuthorBean = factory.select(msdsAuthorId);

		return msdsAuthorBean;
	}
}
