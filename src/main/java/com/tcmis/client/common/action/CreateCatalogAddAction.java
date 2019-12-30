package com.tcmis.client.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.ApprovedMaterialProcess;
import com.tcmis.client.common.process.CreateCatalogAddProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for creating new catalog add requests for PO data
 * @version 1.0
 ******************************************************************************/

public class CreateCatalogAddAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

        String loadId = request.getParameter("loadId");
        StringBuffer requestURL = request.getRequestURL();
        CreateCatalogAddProcess process = new CreateCatalogAddProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
        if (loadId != null) {
            process.processData(loadId);
        }else {
            process.processData("-1");
        }
        response.getOutputStream().print("OK");
        return noForward;
	}
}   //end of class

