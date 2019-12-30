package com.tcmis.internal.distribution.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ItemBean;
import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for Find Item
 * @version 1.0
 *****************************************************************************/
public class FindItemProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FindItemProcess(String client) {
    super(client);
  }
	
  public FindItemProcess(String client, String locale) {
    super(client,locale);
  }
  
  public Collection getBuyerColl(AutocompleteInputBean inputBean) throws BaseException, Exception {
	  DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemBean());
	    
	    // This is for O'brien. We need to escape the ' in the name.
	    String escapedSearchText = inputBean.getSearchText().replace("'", "''");
	    
	    StringBuilder query = new StringBuilder("SELECT distinct item_id, item_desc ");
	    query.append(" FROM item WHERE lower(item_id||item_desc) like lower('%").append(escapedSearchText).append("%') ");
	    if (inputBean.getRowNum() != null) 
	    	query.append(" and rownum <= ").append(inputBean.getRowNum());
	    
	    query.append(" order by item_id");
	    return factory.selectQuery(query.toString());
	}

}
