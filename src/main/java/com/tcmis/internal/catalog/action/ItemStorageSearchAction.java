package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.ItemStorageBean;
import com.tcmis.internal.catalog.process.ItemStorageSearchProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.framework.TcmISBaseAction;

/******************************************************************************
 * Controller for ItemStorageSearchAction
 * @version 1.0
	******************************************************************************/
public class ItemStorageSearchAction extends TcmISBaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!isLoggedIn(request)) {
            request.setAttribute("requestedPage", "itemstoragesearchmain");
            request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
            return (mapping.findForward("login"));
        }

        if (form != null) {
            PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
            ItemStorageBean inputBean = new ItemStorageBean();
            BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
            ItemStorageSearchProcess process = new ItemStorageSearchProcess(this.getDbUser(request));
            if (inputBean.isSearch()) {
                request.setAttribute("itemPartCollection",process.getSearchData(inputBean));
            } else if (inputBean.isCreateExcel()) {
                this.setExcel(response, "Item Storage Search");
                process.createExcelReport(inputBean).write(response.getOutputStream());
                return noForward;
            }
        }
        return mapping.findForward("success");
    }
}
