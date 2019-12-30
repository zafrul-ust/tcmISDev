package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.CustLabTestRequiredViewBean;
import com.tcmis.client.common.beans.TestDefinitionInputBean;
import com.tcmis.client.common.process.TestDefinitionProcess;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class TestDefinitionAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "testdefinition");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (!this.isLoggedIn(request)) return mapping.findForward("nopermissions");

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		
		TestDefinitionInputBean inputBean = new TestDefinitionInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		
		TestDefinitionProcess process = new TestDefinitionProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		String activeTestsOnly = inputBean.getActiveTestsOnly();

		request.setAttribute("activeTestsOnly", activeTestsOnly);
		boolean blnActiveTestsOnly = true;
		if (activeTestsOnly != null && !activeTestsOnly.equalsIgnoreCase("") && activeTestsOnly.equalsIgnoreCase("Y")) 
			blnActiveTestsOnly = true;
		else
			blnActiveTestsOnly = false;
		
		if ("update".equals(inputBean.getuAction())) {
			if (!permissionBean.hasFacilityPermission("testDefinition", inputBean.getFacilityId(), inputBean.getCompanyId())) {
				return mapping.findForward("nopermissions");
			}  
			
			Collection<CustLabTestRequiredViewBean> beans = BeanHandler.getBeans((DynaBean)form,"labTestBean",new CustLabTestRequiredViewBean(),this.getTcmISLocale(request));
			
			Collection errorMsgs = process.updateLabTests(personnelId,inputBean,beans);
			
			if(errorMsgs != null && errorMsgs.size() > 0)
				request.setAttribute("tcmISErrors", errorMsgs);
			
			request.setAttribute("custLabTestColl", process.getCusLabTestRequiredColl(inputBean, blnActiveTestsOnly));
			request.setAttribute("testColl", process.getVvTestColl(inputBean,true));
            request.setAttribute("frequencyTypeColl", process.getFrequencyTypeColl());
            request.setAttribute("frequencyUnitColl", process.getFrequencyUnitColl());
        }else if ("applyDefaultTests".equals(inputBean.getuAction())) {
            process.applyDefaultTests(inputBean,personnelId);
			request.setAttribute("custLabTestColl", process.getCusLabTestRequiredColl(inputBean, blnActiveTestsOnly));
			request.setAttribute("testColl", process.getVvTestColl(inputBean,true));
            request.setAttribute("frequencyTypeColl", process.getFrequencyTypeColl());
            request.setAttribute("frequencyUnitColl", process.getFrequencyUnitColl());
        } else {
			request.setAttribute("custLabTestColl", process.getCusLabTestRequiredColl(inputBean, blnActiveTestsOnly));
			request.setAttribute("testColl", process.getVvTestColl(inputBean,true));
            request.setAttribute("frequencyTypeColl", process.getFrequencyTypeColl());
            request.setAttribute("frequencyUnitColl", process.getFrequencyUnitColl());
        }
		return mapping.findForward("success");
	}
}
