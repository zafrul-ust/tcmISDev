package com.tcmis.client.common.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for MSDS viewer Search page
 * @version 1.0
 ******************************************************************************/

public class StorageLocationsAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
/*		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "msdsviewermain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
*/
		String materialId = request.getParameter("materialId");
		String facilityId = request.getParameter("facilityId");
		String application = request.getParameter("application");
        MsdsViewerProcess process = new MsdsViewerProcess(this.getDbUser(request),getTcmISLocaleString(request));
      
        if (materialId != null) {
            request.setAttribute("workAreaColl", process.getStorageLocations(materialId, facilityId, application));
        }
        
		return (mapping.findForward("success"));
	}
}   //end of class
