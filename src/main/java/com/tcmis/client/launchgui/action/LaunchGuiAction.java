package com.tcmis.client.launchgui.action;

import javax.servlet.http.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import org.apache.struts.action.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.launchgui.process.LaunchGuiProcess;

/******************************************************************************
 * Controller for TDSF Report
 * @version 1.0
 ******************************************************************************/
public class LaunchGuiAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
    /*Ignoring company so that GUI can be launched from the Haas application for all clients*/
    if (!this.isLoggedIn(request,true)) {
      request.setAttribute("requestedPage", "launchguimain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    //populate drop downs
    //If you need access to who is logged in
    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		LaunchGuiProcess process = new LaunchGuiProcess(this.getDbUser(request));

    if (form != null) {
		 if ( ( (DynaBean) form).get("launchGui") != null &&
			( (String) ( (DynaBean) form).get("launchGui")).length() > 0) {
			/*if (!this.isTcmISTokenValid(request, true)) {
			 BaseException be = new BaseException("Duplicate form submission");
			 be.setMessageKey("error.submit.duplicate");
			 throw be;
			}
			this.saveTcmISToken(request);*/

		  String ecomerce = "N";
      String payLoadId = "0";
			if (((DynaBean) form).get("ecomerce") != null) {
			 ecomerce = ((DynaBean) form).get("ecomerce").toString();
			}
			if (((DynaBean) form).get("payLoadId") != null) {
			 payLoadId = ((DynaBean) form).get("payLoadId").toString();
			}

			String launchFile = process.launchGui(personnelBean, ecomerce, payLoadId);
			request.setAttribute("filePath", launchFile);
			return mapping.findForward("starttcmis");
		 } //end of else
		 else {
		//first get header dropdown data
		request.setAttribute("companyDropDownCollection", process.getCompanyDropDown(personnelBean.getPersonnelId()));
		request.setAttribute("hostUrl", process.getHostUrl());
		this.saveTcmISToken(request);

		return (mapping.findForward("success"));
	 }
	}
	return (mapping.findForward("success"));
 } //end of method
} //end of class
