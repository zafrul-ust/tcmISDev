package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.client.common.beans.CompanyMsdsLogBean;

/******************************************************************************
 * Process for PropertyLogProcess
 * @version 1.0
 *****************************************************************************/
public class PropertyLogProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public PropertyLogProcess(String client,String locale)  {
		super(client,locale);
	}
	
	public Collection getSearchResult(String materialId, String revisionDate) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CompanyMsdsLogBean());
		StringBuilder query = new StringBuilder("select * from company_msds_log_view where material_id = ");
        query.append(materialId).append(" and revision_date = '").append(revisionDate).append("'");
        query.append(" order by updated_on desc, updated_by_name, property_Name");
        
        return factory.selectQuery(query.toString());
	}
	

} //end of class