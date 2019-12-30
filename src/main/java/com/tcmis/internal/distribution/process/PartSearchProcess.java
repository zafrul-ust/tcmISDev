package com.tcmis.internal.distribution.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.distribution.beans.PartSearchBean;
import com.tcmis.internal.distribution.beans.PartSearchInputBean;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class PartSearchProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public PartSearchProcess(String client) {
  	super(client);
   }
   
   public PartSearchProcess(String client,String locale) {
	    super(client,locale);
  }

   public Collection getPartSearchResult(PersonnelBean personnelBean, PartSearchInputBean inputBean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PartSearchBean());
//TODO: Add part desccription criteria to this
		String query="select * from table (pkg_sales_search.FX_PART_SEARCH('" + StringHandler.emptyIfNull(inputBean.getCompanyId()) +"','" +
									StringHandler.emptyIfNull(inputBean.getCustomerId()) +"','" +
									StringHandler.emptyIfNull(inputBean.getShipToLocationId()) +"','" +
									StringHandler.emptyIfNull(inputBean.getInventoryGroup()) +"'," +
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(inputBean.getPartNumber())) +",'" + inputBean.getSearchPartNumberMode() +"'," + 
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(inputBean.getSpecification())) +",'" + inputBean.getSearchSpecificationMode() +"'," +
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(inputBean.getCustomerPartNumber())) +",'" + inputBean.getSearchCustomerPartNumberMode() +"'," +
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(inputBean.getText())) +",'" + inputBean.getSearchTextMode() +"'," +
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(inputBean.getAlternate())) +",'" + inputBean.getSearchAlternateMode() +"'," +
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(inputBean.getPartDesc())) +",'" + inputBean.getSearchPartDescMode() +"','" +
                                    StringHandler.emptyIfNull(inputBean.getCurrencyId()) +"','" +
                                    personnelBean.getPersonnelId() +"','" +
                                    StringHandler.emptyIfNull(inputBean.getPriceGroupId()) + "'))";
	
		return factory.selectQuery(query);
	}
 
}
