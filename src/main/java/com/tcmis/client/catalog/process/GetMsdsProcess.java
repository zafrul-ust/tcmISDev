package com.tcmis.client.catalog.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CustomerMaterialNumberBean;
import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for Msds AutoComplete
 * @version 1.0
 *****************************************************************************/
public class GetMsdsProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public GetMsdsProcess(String client) {
    super(client);
  }
	
  public GetMsdsProcess(String client, String locale) {
    super(client,locale);
  }
  
  public Collection getMSDSColl(AutocompleteInputBean inputBean) throws BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerMaterialNumberBean()); 
	    
	    // This is for O'brien. We need to escape the ' in the name.
	    String escapedSearchText = inputBean.getSearchText().replace("'", "''");
	    StringBuilder query = new StringBuilder("select * from table(pkg_search_msds_data.fx_search_msds_data(");
	    query.append("'").append((inputBean.getCompanyId()==null||inputBean.getCompanyId().length()==0?"":inputBean.getCompanyId())).append("',");
	    query.append("'").append((inputBean.getCustomerMsdsDb()==null||inputBean.getCustomerMsdsDb().length()==0?"":inputBean.getCustomerMsdsDb())).append("',");
	    query.append("'").append((inputBean.getSearchText()==null||inputBean.getSearchText().length()==0?"":escapedSearchText)).append("',");
	    query.append("'").append((inputBean.getRowNum()==null||inputBean.getRowNum().length()==0?"20":inputBean.getRowNum())).append("'))");
	    
	    return factory.selectQuery(query.toString());
	}


}
