package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Vector;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.*;
import com.tcmis.client.common.process.CabinetDefinitionManagementProcess;
import com.tcmis.client.common.process.CountTypeProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;

/**
 * ***************************************************************************
 * Controller for cabinet labels
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class ClientCabinetBinAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "clientcabinetbinaddpart");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (form != null) {
			String uAction = (String) ((DynaBean) form).get("uAction");
			CabinetBinInputBean bean = new CabinetBinInputBean();
			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			BeanHandler.copyAttributes(form, bean, "dateformat",personnelBean.getLocale());
			if ("add".equalsIgnoreCase(uAction)) {
				bean.setPersonnelId(new BigDecimal(((PersonnelBean) this.getSessionObject(request, "personnelBean")).getPersonnelId()));
				CabinetBinProcess cabinetBinProcess = new CabinetBinProcess(this.getDbUser(request), getTcmISLocaleString(request));
				String errorMsg = cabinetBinProcess.addPart(bean,personnelBean);
				if (!StringHandler.isBlankString(errorMsg)) {
					request.setAttribute("tcmISError",errorMsg);
					//catalog dropdown
					CabinetDefinitionManagementProcess process = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
					request.setAttribute("catalogFacilityBeanCollection", process.getCatalogs(bean));
					// Create the drop-down bean for the COUNT_TYPE
					getCountType(bean);
                    request.setAttribute("vvTierIITemperatureCodeBeanColl",process.getVvTierIITemperatureCodeBeanColl());
	        		request.setAttribute("authorizedUserWorkAreasColl",process.getAuthorizedUserWorkAreasForManangedMatl(personnelBean,request.getParameter("facilityId"),request.getParameter("companyId")));
				}else {
					request.setAttribute("catalogFacilityBeanCollection", new Vector(0));
					request.setAttribute("countTypeDropDownList", new Vector(0));
                    request.setAttribute("defaultCountType","");
                }
			}else {
				CabinetDefinitionManagementProcess process = new CabinetDefinitionManagementProcess(this.getDbUser(request), getTcmISLocaleString(request));
				//catalog dropdown
				request.setAttribute("catalogFacilityBeanCollection", process.getCatalogs(bean));
                // Create the drop-down bean for the COUNT_TYPE
				getCountType(bean);
                request.setAttribute("authorizedUserWorkAreasColl",process.getAuthorizedUserWorkAreasForManangedMatl(personnelBean,request.getParameter("facilityId"),request.getParameter("companyId")));
				request.setAttribute("vvTierIITemperatureCodeBeanColl",process.getVvTierIITemperatureCodeBeanColl());
            }
		}

		return (mapping.findForward("success"));
	} //end of method

    private void getCountType(CabinetBinInputBean bean) {
        try {
            CountTypeProcess countTypeProcess = new CountTypeProcess(this.getDbUser(request), getTcmISLocaleString(request));
            request.setAttribute("countTypeDropDownList", countTypeProcess.getCountType("ClientCabinetBinAction"));
            //default_count_type
            request.setAttribute("defaultCountType",countTypeProcess.getFacilityDefaultCountType(bean));
        }catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("countTypeDropDownList",  new DropDownListBean[0]);
        }
    }

} //end of class