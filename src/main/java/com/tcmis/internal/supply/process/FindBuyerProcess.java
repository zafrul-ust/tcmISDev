package com.tcmis.internal.supply.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelUserGroupViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for Find Supplier
 * @version 1.0
 *****************************************************************************/
public class FindBuyerProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FindBuyerProcess(String client) {
    super(client);
  }
	
  public FindBuyerProcess(String client, String locale) {
    super(client,locale);
  }
  
  public Collection getBuyerColl(AutocompleteInputBean inputBean) throws BaseException, Exception {
	  DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PersonnelUserGroupViewBean());
	    
	    // This is for O'brien. We need to escape the ' in the name.
	    String escapedSearchText = inputBean.getSearchText().replace("'", "''");
	    
	    StringBuilder query = new StringBuilder("SELECT distinct Personnel_id, name ");
	    query.append(" FROM PERSONNEL_USER_GROUP_VIEW WHERE user_group_id = 'BuyOrder' and lower(personnel_id||name) like lower('%").append(escapedSearchText).append("%') ");
	    if (inputBean.getRowNum() != null) 
	    	query.append(" and rownum <= ").append(inputBean.getRowNum());
	    
	    query.append(" order by name");
	    return factory.selectQuery(query.toString());
	}

}
