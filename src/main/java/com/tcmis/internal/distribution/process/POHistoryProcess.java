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
import com.tcmis.internal.distribution.beans.POHistoryViewBean;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class POHistoryProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public POHistoryProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection<POHistoryViewBean> getSearchResult(POHistoryViewBean inputBean, PersonnelBean personnelBean) throws BaseException {

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new POHistoryViewBean());
		DbManager dbManager = new DbManager(getClient(),getLocale());
		Collection<POHistoryViewBean> c = null;
		//TODO: Add part desccription criteria to this
		StringBuilder query= new StringBuilder("select * from table (pkg_sales_search.FX_PREVIOUS_po_line(");
		if ( "No Specification".equals(inputBean.getSpecList()))
		{
			inputBean.setSpecList("");
		}

		query.append( StringHandler.emptyIfNull(inputBean.getItemId()) ).append(",'" ).append(
				StringHandler.emptyIfNull(personnelBean.getCompanyId())).append("','" ).append(
						personnelBean.getPersonnelId() ).append("','" ).append(
								StringHandler.emptyIfNull(inputBean.getSpecList()) ).append("','" ).append(
										StringHandler.emptyIfNull(inputBean.getInventoryGroup()) ).append("','" ).append(
												inputBean.getRegion() ).append("'," ).append(	// Ops Region
												"365").append("," ).append( 	// Limit Days, set to one year back
												"null" ).append(",'" ).append(		// row limit
														StringHandler.emptyIfNull(inputBean.getSearchKey())).append( "')) ");	// Scope
		c = factory.selectQuery(query.toString());

		return c;
	}




}