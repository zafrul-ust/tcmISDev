package com.tcmis.internal.supply.process;

import java.sql.Connection;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.supply.beans.SuggestedSupplierBean;

/******************************************************************************
* Process used by ShowScheduleTcmBuysAction
* @version 1.0
*****************************************************************************/

public class ShowSuggestedSupplierProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
	
	public ShowSuggestedSupplierProcess(String client) 
	{
		super(client);
	}  

	public Collection<SuggestedSupplierBean> getSuggestedSupplierCol (SuggestedSupplierBean inputBean) throws BaseException, Exception 
	{
		String query = null;		
		DbManager dbManager = new DbManager(this.getClient());
		Connection conn = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new SuggestedSupplierBean());
		Collection<SuggestedSupplierBean> suggestedSupplierBeansCol = null;
		try 
		{	
			if (inputBean.getRequestId() != null && inputBean.getRequestId().equals("")) {
				query = " Select cai.SUGGESTED_VENDOR, cai.VENDOR_CONTACT_EMAIL, cai.VENDOR_CONTACT_FAX, cai.VENDOR_CONTACT_NAME, cai.VENDOR_CONTACT_PHONE, carn.VENDOR_PART_NO " +
					" from customer.catalog_add_request_new carn,catalog_add_item cai " +
					" where carn.REQUEST_ID = " + inputBean.getRequestId() + 
					" and carn.request_id = cai.request_id " +
					" and cai.line_item = 1 " +
					" and cai.part_id = 1 " +
					" order by LAST_UPDATED desc ";	
			} else {
				query = " Select cai.SUGGESTED_VENDOR, cai.VENDOR_CONTACT_EMAIL, cai.VENDOR_CONTACT_FAX, cai.VENDOR_CONTACT_NAME, cai.VENDOR_CONTACT_PHONE, carn.VENDOR_PART_NO " +
					" from customer.catalog_add_request_new carn, catalog_add_item cai " +
					" where CATALOG_ID = " + SqlHandler.delimitString(inputBean.getCatalogId()) +
					" and CAT_PART_NO = " + SqlHandler.delimitString(inputBean.getCatPartNo()) +
					" and LAST_UPDATED is not null " +
					" and carn.request_id = cai.request_id " +
					" and cai.line_item = 1 " +
					" and cai.part_id = 1 " +
					" order by LAST_UPDATED desc ";	
			}
			
			suggestedSupplierBeansCol = genericSqlFactory.selectQuery(query, conn);
			
		}
		catch (Exception sqle) {
			log.error("SQL Exception in getFlowdownSQL: " + sqle);
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return suggestedSupplierBeansCol;		
	
	}
	
}
