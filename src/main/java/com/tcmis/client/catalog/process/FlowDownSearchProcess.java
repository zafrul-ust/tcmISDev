package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.VvPkgStyleBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.catalog.beans.CatalogAddFlowDownBean;

/******************************************************************************
 * Process used by FlowDownSearchProcess
 * @version 1.0
 *****************************************************************************/

public class FlowDownSearchProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public FlowDownSearchProcess(String client)
	{
		super(client);
	}

	public Collection getFlowDownBeanCollection( String searchArgument, String catalogId, String companyId) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatalogAddFlowDownBean());
		StringBuilder query = new StringBuilder("select * from vv_flow_down");
		query.append(" where catalog_id = '").append(catalogId).append("'");
		query.append(" and company_id = '").append(companyId).append("'");
		if (!StringHandler.isBlankString(searchArgument)) {
			query.append(" and (").append(doSearchLogic(searchArgument)).append(")");
		}

		query.append(" order by flow_down_desc");
		return factory.selectQuery(query.toString());
	}

	public String doSearchLogic(String search) {
		Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " lower(flow_down_desc) like lower('%" + SqlHandler.validQuery(search) + "%')";
			return result;
		}

		//contains operation in search text
		result += " lower(flow_down_desc) like lower('%" + SqlHandler.validQuery(likes.elementAt(0).toString().trim()) + "%') ";
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			result += " " + op + " lower(flow_down_desc) " + lk + " lower('%" + SqlHandler.validQuery(searchS) + "%') ";
		}

		return result;
	}

} //end of class
