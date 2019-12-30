package com.tcmis.client.common.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ContactLogBean;
import com.tcmis.internal.catalog.process.ContactLogProcess;

public class ViewContactLogsAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		
		ContactLogProcess process = new ContactLogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		ContactLogBean clb = new ContactLogBean();
		BeanHandler.copyAttributes(form, clb);
		
		String uAction = request.getParameter("uAction");
		if ("logHistory".equals(uAction) && clb.getCurrentMaterialId() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(process.getDateFormat()); 
			clb.setLogHistoryDataColl(process.retrieveLogHistory(clb));
			process.createLogHistoryJasonObject(clb, response, dateFormat);
			
			return noForward;
		}
		
		return mapping.findForward("success");
		
	}

}
