package com.tcmis.client.catalog.process;


import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ClientInventoryCommentsViewBean;
import com.tcmis.client.catalog.factory.ClientInventoryCommentsViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process used by CasNumberSearchAction
 * @version 1.0
 *****************************************************************************/

public class CatalogCommentProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());

	public CatalogCommentProcess(String client) 
	{
		super(client);
	}  

	public CatalogCommentProcess(String client,Locale locale) 
	{
		super(client,locale);
	}  

	/**
	 * Searches the CLIENT_INVENTORY_COMMENTS for a particular comment
	 * @param clientCommentsBean
	 * @param action
	 * @return Collection
	 * @throws BaseException
	 * @throws Exception
	 */
	public Collection search(ClientInventoryCommentsViewBean clientCommentsBean, String action) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient(),this.getLocale());		
		ClientInventoryCommentsViewBeanFactory factory = new ClientInventoryCommentsViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();


		criteria.addCriterion("catalogCompanyId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatalogCompanyId());

		criteria.addCriterion("catalogId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatalogId());		

		criteria.addCriterion("catPartNo",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatPartNo());

		criteria.addCriterion("partGroupNo",
				SearchCriterion.EQUALS,
				clientCommentsBean.getPartGroupNo().toString());

		criteria.addCriterion("facilityId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getFacilityId());

/*
 * 
 		criteria.addCriterion("comments",
				SearchCriterion.LIKE,
				clientCommentsBean.getComments());			
*/


		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("comments");

		return factory.select(criteria,scriteria );
	}

	/**
	 * Inserts a new comment with given values.
	 * 
	 * @param clientCommentsBean
	 * @return 
	 * @throws BaseException
	 * @throws Exception
	 */
	public void  insertNewComments(ClientInventoryCommentsViewBean clientCommentsBean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		ClientInventoryCommentsViewBeanFactory factory = new ClientInventoryCommentsViewBeanFactory(dbManager);
		factory.insert(clientCommentsBean);	
	}

	/**
	 * Update process to update a particular comment for a given criteria.
	 * 
	 * @param clientCommentsBean
	 * @throws BaseException
	 * @throws Exception
	 */
	public void updateClientComments (ClientInventoryCommentsViewBean clientCommentsBean) throws BaseException, Exception 
	{

		DbManager dbManager = new DbManager(getClient(),this.getLocale());		
		ClientInventoryCommentsViewBeanFactory factory = new ClientInventoryCommentsViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("catalogCompanyId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatalogCompanyId());

		criteria.addCriterion("catalogId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatalogId());		

		criteria.addCriterion("catPartNo",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatPartNo());

		criteria.addCriterion("partGroupNo",
				SearchCriterion.EQUALS,
				clientCommentsBean.getPartGroupNo().toString());

		criteria.addCriterion("facilityId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getFacilityId());		


		try {
			factory.update(clientCommentsBean, criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void deleteComments(ClientInventoryCommentsViewBean clientCommentsBean) throws BaseException, Exception 
	{

		DbManager dbManager = new DbManager(getClient(),this.getLocale());		
		ClientInventoryCommentsViewBeanFactory factory = new ClientInventoryCommentsViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("catalogCompanyId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatalogCompanyId());

		criteria.addCriterion("catalogId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatalogId());		

		criteria.addCriterion("catPartNo",
				SearchCriterion.EQUALS,
				clientCommentsBean.getCatPartNo());

		criteria.addCriterion("partGroupNo",
				SearchCriterion.EQUALS,
				clientCommentsBean.getPartGroupNo().toString());

		criteria.addCriterion("facilityId",
				SearchCriterion.EQUALS,
				clientCommentsBean.getFacilityId());		


		try {
			factory.delete(criteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
