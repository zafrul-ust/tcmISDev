package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class FindCustomerProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FindCustomerProcess(String client) {
    super(client);
  }
	
  public FindCustomerProcess(String client, String locale) {
    super(client,locale);
  }
  
  public Collection getCustomerColl(AutocompleteInputBean inputBean) throws BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerAddressSearchViewBean()); 
	    
	    // This is for O'brien. We need to escape the ' in the name.
	    String escapedSearchText = inputBean.getSearchText().replace("'", "''");
	    
	    StringBuilder query = new StringBuilder("select customer_id, customer_name, abc_classification, bill_to_company_id ");
	    query.append(" FROM quick_customer_view WHERE (lower(CUSTOMER_NAME) LIKE lower('%").append(escapedSearchText);
	    query.append("%') or CUSTOMER_id LIKE '").append(escapedSearchText).append("%') and  rownum <= ").append(inputBean.getRowNum());
	    query.append(" order by customer_name");
	    
	    return factory.selectQuery(query.toString());
	}


}
