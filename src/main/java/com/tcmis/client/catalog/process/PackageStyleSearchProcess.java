package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvPkgStyleBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.admin.beans.AutocompleteInputBean;

/******************************************************************************
 * Process used by PackageStyleSearchProcess
 * @version 1.0
 *****************************************************************************/

public class PackageStyleSearchProcess  extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public PackageStyleSearchProcess(String client)
	{
		super(client);
	}

	public Collection getPackageStyleBeanCollection( String searchArgument) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VvPkgStyleBean());
		StringBuilder query = new StringBuilder("select * from vv_pkg_style");
		if (!StringHandler.isBlankString(searchArgument)) {
			query.append(" where ").append(doSearchLogic(searchArgument));
		}
		query.append(" order by pkg_style");
		return factory.selectQuery(query.toString());
	}
	
	public Collection getPackageStyleBeanCollection(AutocompleteInputBean inputBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VvPkgStyleBean()); 
	    
	    // This is for O'brien. We need to escape the ' in the name.
	    //String escapedSearchText = inputBean.getSearchText().replace("'", "''");
	    
	    StringBuilder query = new StringBuilder("select * from vv_pkg_style where ");
	    query.append(doSearchLogic(inputBean.getSearchText())).append(" and  rownum <= ").append(inputBean.getRowNum());
	  
	    return factory.selectQuery(query.toString());
	}

	public String doSearchLogic(String search) {
		Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			result = " lower(pkg_style) like lower('%" + SqlHandler.validQuery(search) + "%')";
			return result;
		}

		//contains operation in search text
		result += " lower(pkg_style) like lower('%" + SqlHandler.validQuery(likes.elementAt(0).toString().trim()) + "%') ";
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			result += " " + op + " lower(pkg_style) " + lk + " lower('%" + SqlHandler.validQuery(searchS) + "%') ";
		}

		return result;
	}

} //end of class
