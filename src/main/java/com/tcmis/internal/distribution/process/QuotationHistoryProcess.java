package com.tcmis.internal.distribution.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.QuotationHistoryViewBean;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class QuotationHistoryProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public QuotationHistoryProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection<QuotationHistoryViewBean> getSearchResult(QuotationHistoryViewBean inputBean, PersonnelBean personnelBean) throws BaseException {

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new QuotationHistoryViewBean());
		DbManager dbManager = new DbManager(getClient(),getLocale());
		Collection<QuotationHistoryViewBean> c = null;
		//TODO: Add part desccription criteria to this
		StringBuilder query= new StringBuilder("select * from table (pkg_sales_search.FX_PREVIOUS_quote(");

		if ( "No Specification".equals(inputBean.getSpecList()))
		{
			inputBean.setSpecList("");
		}
		query.append( inputBean.getItemId() ).append(",'" ).append(
				StringHandler.emptyIfNull(personnelBean.getCompanyId()) ).append("'," ).append(
						personnelBean.getPersonnelId() ).append( ",'" ).append(
								StringHandler.emptyIfNull(inputBean.getSpecList()) ).append("','" ).append(
										StringHandler.emptyIfNull(inputBean.getInventoryGroup()) ).append("','" ).append(
												StringHandler.emptyIfNull(inputBean.getCompanyId())).append("'," ).append(
														(null!=inputBean.getCustomerId()?"'"+inputBean.getCustomerId()+"'":null) ).append(",'" ).append(
																StringHandler.emptyIfNull(inputBean.getRegion()) ).append("'," ).append(	// Ops Region
																"null" ).append("," ).append(				// start Date, null defaults to one year ago
																"null" ).append("," ).append(				// stop date
																"null" ).append(",'" ).append(				// row limit
																		StringHandler.emptyIfNull(inputBean.getSearchKey())).append( "')) ");	// Scope

		c = factory.selectQuery(query.toString());

		return c;
	}




}