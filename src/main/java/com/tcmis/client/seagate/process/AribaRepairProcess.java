package com.tcmis.client.seagate.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.beans.MrNeedingApprovalViewBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;


/**
 * ******************************************************************
 * Process for the PoExpediting Section
 * 
 * @version 1.0
 * *****************************************************************
 */

public class AribaRepairProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	
    DbManager dbManager = null;
    GenericSqlFactory genericSqlFactory = null;
    Connection connection = null;

	public AribaRepairProcess(String client, Locale locale) {
		super(client, locale);
		dbManager = new DbManager(getClient(),this.getLocale());
		genericSqlFactory = new GenericSqlFactory(dbManager);
	}

	public AribaRepairProcess(String client) {
		super(client);
		dbManager = new DbManager(getClient(),this.getLocale());
		genericSqlFactory = new GenericSqlFactory(dbManager);
	}

	@SuppressWarnings("unchecked")
	public Collection<MrNeedingApprovalViewBean> getSearchResult(MrNeedingApprovalViewBean input) throws BaseException {
		genericSqlFactory.setBean(new MrNeedingApprovalViewBean());

		String query ;
		StringBuilder criteria = new StringBuilder();
		
		/* Searching for order FROM date */
		if (input.hasConfirmFromDate()) {		
			criteria.append("release_date >= " +DateHandler.getOracleToDateFunction(input.getConfirmFromDate()));
		}
		
		/* Searching for order TO date */
		if (input.hasConfirmToDate()) {		
			if(criteria.length() > 0)
				criteria.append(" AND ");
			criteria.append("release_date < " + DateHandler.getOracleToDateFunction(input.getConfirmToDate()));
		}	
		
		if (input.hasFacility()){			
			if(criteria.length() > 0)
				criteria.append(" AND ");
			criteria.append("facility_id = '" + input.getFacilityId() + "'");
		}
		
		if (input.hasApplication()){		
			if(criteria.length() > 0)
				criteria.append(" AND ");
			criteria.append("application = '" + input.getApplication() + "'");
		}

		if (input.hasCompanyId()){		
			if(criteria.length() > 0)
				criteria.append(" AND ");
			criteria.append("company_id = '" + input.getCompanyId() + "'");
		}
		
		if (input.hasCabinetReplenishment() && input.getCabinetReplenishment().equals("Y")){	
			if(criteria.length() > 0)
				criteria.append(" AND ");
			criteria.append("cabinet_replenishment = 'Y'" );
		} else {
			if(criteria.length() > 0)
				criteria.append(" AND ");
			criteria.append("cabinet_replenishment != 'Y'" );
		}
		criteria.append(" ORDER BY pr_number, line_item");

		query = "SELECT /*+ INDEX(V.RLI REQUEST_LINE_ITEM_N4) */ * FROM mr_needing_approval_view v WHERE ";
		return genericSqlFactory.selectQuery(query + criteria.toString());
	}

	public Collection update(String inputLines, String customerReturnId) throws BaseException {
		Vector errorMessages = new Vector();
		String errorMsg = "";
		String[] MRs = inputLines.split("!!!!!");
		for (String MR : MRs) {

				String[] mrLine = MR.split("-");
				{
					try {
						connection = dbManager.getConnection();
						errorMessages = (Vector)resubmitRequests(mrLine[0],mrLine[1],customerReturnId);
						}
					catch (Exception e) {
						errorMsg = "Error : " ;
						errorMessages.add(errorMsg);
					}
					finally
					{
						if(connection != null)
							dbManager.returnConnection(connection);
					}
				}
		}

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	
	   public Collection resubmitRequests(String prNumber,String lineItem, String customerReturnId) throws Exception {
			  Vector errorMessages = new Vector();
			  String errorMsg = "";
		      String query = "update request_line_item set payload_id = '"+customerReturnId+"' where pr_number = "+prNumber+" and line_item = "+ lineItem;
		      try {
		    	  genericSqlFactory.deleteInsertUpdate(query,connection);

		      }catch (BaseException e) {
					errorMsg = "Error : " ;
					errorMessages.add(errorMsg);
		      }
		      return errorMessages;
		   }

}