package com.tcmis.client.seagate.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.seagate.process.CraProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

public class CraAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException {
		try {
	      CraProcess process = new CraProcess("SEAGATE");
	      //now process new_chem_approval

	      //process "Active" requests
	      process.processSeagateNewChemApproval();
	      //process "Inactive" requests
	      process.processSeagateInactiveRequest();
	      //process "Reactivated" requests
	      process.processSeagateReactivatedRequest();

	    }
	    catch (Exception e) {
	    	log.error("CRA processing", e);
	        MailProcess.sendEmail("deverror@haastcm.com", null,
	                              "deverror@haastcm.com",
	                              "SEAGATE - CRA processing error",
	                              "CRA processing.\n" + e.getMessage());
	    }
		
		return noForward;
	}
}
