package com.tcmis.internal.distribution.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.internal.supply.beans.SupplierAddressViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for Find Supplier
 * @version 1.0
 *****************************************************************************/
public class FindSupplierProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FindSupplierProcess(String client) {
    super(client);
  }
	
  public FindSupplierProcess(String client, String locale) {
    super(client,locale);
  }
  
  public Collection getSupplierColl(AutocompleteInputBean inputBean) throws BaseException, Exception {
	  DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SupplierAddressViewBean());
	    
	    // This is for O'brien. We need to escape the ' in the name.
	    String escapedSearchText = inputBean.getSearchText().replace("'", "''");
	    
	    StringBuilder query = new StringBuilder("SELECT distinct supplier, supplier_name, active_payment_terms, city, country_abbrev ");
	    query.append(" FROM SUPPLIER_ADDRESS_VIEW WHERE lower(SUPPLIER||SUPPLIER_NAME) like lower('%").append(escapedSearchText).append("%') ");
	  
	    if("true".equals(inputBean.getActiveOnly()))
	    	query.append(" and active_payment_terms = 'Active'");
	    
	    if (inputBean.getRowNum() != null) 
	    	query.append(" and rownum <= ").append(inputBean.getRowNum());
	    
	    query.append(" order by supplier_name");
	    return factory.selectQuery(query.toString());
	}


}
