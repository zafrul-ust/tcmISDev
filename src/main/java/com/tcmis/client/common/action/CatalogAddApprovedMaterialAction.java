package com.tcmis.client.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.client.common.beans.ApprovedMaterialInputBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.ApprovedMaterialProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for Processing Catalog Add Approved Material from Maxcom
 * @version 1.0
 ******************************************************************************/

public class CatalogAddApprovedMaterialAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
        ApprovedMaterialInputBean inputBean = new ApprovedMaterialInputBean();
        BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
        StringBuffer requestURL = request.getRequestURL();
        ApprovedMaterialProcess process = new ApprovedMaterialProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")),inputBean);
        process.processRequest();
		return (mapping.findForward("success"));
	}
}   //end of class
