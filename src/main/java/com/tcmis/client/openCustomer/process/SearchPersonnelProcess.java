package com.tcmis.client.openCustomer.process;

import com.tcmis.common.admin.beans.SearchPersonnelInputBean;
import com.tcmis.common.admin.beans.UserCompanyViewBean;
import com.tcmis.common.admin.factory.FacilityViewBeanFactory;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.client.openCustomer.beans.OpenUserShiptoSelOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class SearchPersonnelProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public SearchPersonnelProcess(String client) {
    super(client);
  }

	public Collection getDropDownData(BigDecimal userId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new OpenUserShiptoSelOvBean());
		
		StringBuilder query = new StringBuilder("SELECT * FROM TABLE (PKG_OPEN_CUSTOMER.FX_GET_USER_CUST_ADMIN_DATA(");
		query.append(userId).append(", NULL)) WHERE LOWER(ADMIN_ROLE) LIKE '%admin%'");
		
		return factory.selectQuery(query.toString());
	}
	
	
    public Collection getSearchResult(SearchPersonnelInputBean bean) throws BaseException {
	    Collection personnelBeanCollection = new Vector();

	    DbManager dbManager = new DbManager(getClient());
	    PersonnelBeanFactory personnelBeanFactory = new PersonnelBeanFactory(dbManager);
	    personnelBeanCollection = personnelBeanFactory.selectUserSearchUnderOpenCustomer(bean);
	    return personnelBeanCollection;
    }

}