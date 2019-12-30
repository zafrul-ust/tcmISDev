package com.tcmis.client.common.action;

import javax.servlet.http.*;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.client.common.process.FtpRowDataProcess;

/******************************************************************************
 * Controller for FtpRowDataAction Report
 * @version 1.0
 ******************************************************************************/
public class FtpRowDataAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
    if (form != null) {
		  String dataKey = null;
			if (((DynaBean) form).get("communicationId") != null) {
			 dataKey = ((DynaBean) form).get("communicationId").toString();
			}
			FtpRowDataProcess process = new FtpRowDataProcess(this.getDbUser(request));
			if (process.createRowDataFile(dataKey)) {
				return mapping.findForward("success");
			}else {
				return (mapping.findForward("error"));
			}
		 }else {
			return (mapping.findForward("error"));
    }
 } //end of method
} //end of class
