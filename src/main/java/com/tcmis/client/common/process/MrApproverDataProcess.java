package com.tcmis.client.common.process;

import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.client.common.beans.RequestLineItemApproverBean;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.math.BigDecimal;

/******************************************************************************
 * Process for MaterialRequestProcess
 * @version 1.0
 *****************************************************************************/
public class MrApproverDataProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());


	public MrApproverDataProcess(String client) {
	  	super(client);
	   }

	 public MrApproverDataProcess(String client,String locale)  {
	    super(client,locale);
	}
	
	public Collection getApprover(BigDecimal prNumber, String lineItem) throws
		BaseException {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RequestLineItemApproverBean());
			
			String query = "select a.use_approval_comment,fx_personnel_id_to_phone(a.use_approver) phone,fx_personnel_id_to_email(a.use_approver) email,fx_personnel_id_to_name(a.use_approver) full_name " +
							"from request_line_item a " +
							"where a.pr_number = " + prNumber.toString() + " and a.line_item = '" + lineItem + "'" +
							" order by fx_personnel_id_to_name(a.use_approver)";
			
			return factory.selectQuery(query);
	}
	
	
	public Collection getPurchaseRequestApprover(BigDecimal prNumber) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RequestLineItemApproverBean());
		
		String query = "select fx_personnel_id_to_phone(a.requested_finance_approver) phone,fx_personnel_id_to_email(a.requested_finance_approver) email,fx_personnel_id_to_name(a.requested_finance_approver) full_name,nvl(a.rejection_reason,'') rejection_reason " +
					 	"from purchase_request a " + 
					 	"where a.pr_number = " + prNumber.toString();
		
		return factory.selectQuery(query);
	}



	 
	
} //end of class