package com.tcmis.internal.catalog.process;

import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.hub.beans.EdiOrderTrackingBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.sql.Connection;
import java.util.*;

/******************************************************************************
 * Process used by CatalogAddReqCancellationProcess
 * @version 1.0
 *****************************************************************************/

public class CatalogAddReqCancellationProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());


	public CatalogAddReqCancellationProcess(String client) {
		super(client);
	}

	public void processData(String companyId, String catalogSource) throws Exception {
		try {
			if ("EDI".equals(catalogSource))
				cancelEdiCatalogAddReq(companyId);

		}catch (Exception e) {
			log.error(e);
		}
	}  //end of method

	public void cancelEdiCatalogAddReq(String companyId) throws Exception {
		try {
			StringBuilder query = new StringBuilder("update catalog_add_request_new carn set request_status = 7, approval_group_seq = 0,eval_rejected_by = -1, eval_rejected_comment = 'Rejected by EDI daily job'");
			query.append(" where company_id = '").append(companyId).append("' and request_status in (5,6,8)");
			query.append(" and exists (select null from customer_po_stage cpps");
			query.append(" where cpps.status = 'Processed' and cpps.company_id = carn.company_id and cpps.catalog_request_id = carn.request_id)");
			DbManager dbManager = new DbManager(getClient());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new EdiOrderTrackingBean());
			genericSqlFactory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
		}
	}  //end of method

} //end of class
