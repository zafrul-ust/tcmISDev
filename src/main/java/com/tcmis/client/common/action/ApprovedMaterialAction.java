package com.tcmis.client.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.common.beans.ApprovedMaterialInputBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.ApprovedMaterialProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

import java.math.BigDecimal;
import java.util.Calendar;

/******************************************************************************
 * Controller for Processing Approved Material from Maxcom
 * @version 1.0
 ******************************************************************************/

public class ApprovedMaterialAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		String materialId = request.getParameter("materialId");
        StringBuffer requestURL = request.getRequestURL();
        ApprovedMaterialProcess process = new ApprovedMaterialProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")),new ApprovedMaterialInputBean());
        if (materialId != null) {
            process.processData(new BigDecimal(materialId),new BigDecimal(Calendar.getInstance().getTimeInMillis()),"");
        }
        
		return (mapping.findForward("success"));
	}
}   //end of class

