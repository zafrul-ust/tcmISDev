package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.client.catalog.beans.UseApprovalStatusInputBean;
import com.tcmis.client.catalog.beans.UseApprovalStatusViewBean;
import com.tcmis.client.catalog.process.UseApprovalStatusProcess;
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
public class UseApprovalStatusAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws BaseException, Exception {

        if (!isLoggedIn(request)) {
            request.setAttribute("requestedPage", "useapprovalmain");
            request.setAttribute("requestedURLWithParameters",
                    getRequestedURLWithParameters(request));
            return (mapping.findForward("login"));
        }

        UseApprovalStatusProcess useApprovalStatusProcess = new UseApprovalStatusProcess(this.getDbUser(request));

        PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
        if (!personnelBean.getPermissionBean().hasUserPagePermission("workAreaManagement"))
        {
            return (mapping.findForward("nopermissions"));
        }

        // if form is not null we have to perform some action
        if (form != null) {
            // copy date from dyna form to the data form
            UseApprovalStatusInputBean inputBean = new UseApprovalStatusInputBean();
            BeanHandler.copyAttributes(form, inputBean);

            FacLocAppBean facLocAppInputBean = new FacLocAppBean();
            BeanHandler.copyAttributes(form, facLocAppInputBean);

            if (inputBean.isSearch()) {
                Collection c = useApprovalStatusProcess.getSearchResult(inputBean);
                request.setAttribute("useApprovalStatusViewBeanCollection", c);
                saveTcmISToken(request);

            } else if (inputBean.isUpdate()) {
                checkToken(request);
                request.setAttribute("startSearchTime", new Date().getTime());
                DynaBean dynaBean = (DynaBean) form;
                Collection useApprovalStatusViewBeans = BeanHandler.getBeans(dynaBean, "useApprovalStatusViewBean", new UseApprovalStatusViewBean());
                String errorMessage = "";
                errorMessage = useApprovalStatusProcess.updateSelected(inputBean, useApprovalStatusViewBeans, personnelBean.getPersonnelIdBigDecimal());
                facLocAppInputBean.setUseApprovalLimitsOption("Y");
                useApprovalStatusProcess.updateUseApprovalLimitsOption(facLocAppInputBean);
                request.setAttribute("errorMessage", errorMessage);
                Collection c = useApprovalStatusProcess.getSearchResult(inputBean);
                request.setAttribute("useApprovalStatusViewBeanCollection", c);

            } else if (inputBean.getuAction() != null
                    && inputBean.getuAction().equalsIgnoreCase("showUseApprovers")) {
                Collection c = useApprovalStatusProcess.getUseApprovers(facLocAppInputBean);
                request.setAttribute("overLimitUseApproverViewBeanColl", c);
                return (mapping.findForward("showUseApprovers"));
            } else if (inputBean.getuAction() != null
                    && inputBean.getuAction().equalsIgnoreCase("UpdateManagedUseApproval")) {
                useApprovalStatusProcess.updateManagedUseApproval(facLocAppInputBean, personnelBean.getPersonnelIdBigDecimal());
                facLocAppInputBean.setUseApprovalLimitsOption("Y");
                useApprovalStatusProcess.updateUseApprovalLimitsOption(facLocAppInputBean);
                Collection c = useApprovalStatusProcess.getSearchResult(inputBean);
                request.setAttribute("useApprovalStatusViewBeanCollection", c);

            } else if (inputBean.isCreateExcel()) {
                setExcel(response, "WorkAreaManagementExcel");
                useApprovalStatusProcess.writeExcelFile(
                        useApprovalStatusProcess.getSearchResult(inputBean), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(
                        response.getOutputStream());
                return noForward;
            }

            if (facLocAppInputBean.getApplication() != null
                    && facLocAppInputBean.getFacilityId() != null) {
                Collection facLocAppBeanCollection = useApprovalStatusProcess
                        .getApplicationProperties(facLocAppInputBean);
                String managedWorkArea = "";
                if (!facLocAppBeanCollection.isEmpty()) {
                    managedWorkArea = ((FacLocAppBean) facLocAppBeanCollection.iterator().next()).getManagedUseApproval();
                }

                request.setAttribute("managedWorkArea", managedWorkArea);
                // log.debug("managedWorkArea : " + managedWorkArea + "");
            }

            request.setAttribute("facAppUserGrpOvBeanColl",
                    useApprovalStatusProcess.getFacilityData());
            request.setAttribute("vvUseApprovalOrderQtyRuleBeanColl",
                    useApprovalStatusProcess.getVvOrderQtyRule());

        }
        return mapping.findForward("success");
    }
}