package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleUtils;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.client.common.beans.MsdsDocumentInputBean;
import com.tcmis.client.common.process.MsdsDocumentProcess;


public class MsdsDocumentAction extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            BaseException, Exception {

        String uAction = request.getParameter("uAction");
        if ("login".equals(uAction) && !this.isLoggedIn(request, true)) {
            request.setAttribute("requestedPage", "showaddmrdocument");
            request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
            return (mapping.findForward("login"));
        }

        BigDecimal personnelId;
        // If you need access to who is logged in
        PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");

        if (user != null) {
            personnelId = new BigDecimal(user.getPersonnelId());
        } else {
            personnelId = new BigDecimal(-1);
        }

        MsdsDocumentInputBean msdsDocumentInputBean = new MsdsDocumentInputBean();
        MsdsDocumentProcess msdsDocumentProcess = new MsdsDocumentProcess(this.getDbUser(request), getTcmISLocaleString(request));
        if (form != null) {
            BeanHandler.copyAttributes(form, msdsDocumentInputBean, getTcmISLocale(request));
        }
        
        boolean fromKitManagement = "kitManagement".equalsIgnoreCase(msdsDocumentInputBean.getCalledFrom());

        if (!fromKitManagement && msdsDocumentInputBean.getMaterialId() == null) {
            return mapping.findForward("systemerrorpage");
        } else
        if (form != null && msdsDocumentInputBean.getUpdateDocuments() != null &&
                   msdsDocumentInputBean.getUpdateDocuments().length() > 0) {

            String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
            module = module.substring(1, module.length());
            if (user != null && !("haas".equalsIgnoreCase(module) || "catalog".equalsIgnoreCase(module) || "cv".equalsIgnoreCase(module))) {
                PermissionBean permissionBean = user.getPermissionBean();
                if (!permissionBean.hasFacilityPermission("msdsDocuments", null, null)) {
                    return mapping.findForward("nopermissions");
                }
            }

            DynaBean dynaForm = (DynaBean) form;
            List msdsDocumentViewBeans = (List) dynaForm.get("msdsDocumentViewBean");
            Iterator iterator = msdsDocumentViewBeans.iterator();
            int deleteCount = 0;

            checkToken(request);
            Collection msdsDocumentInputBeanCollection = new Vector();
            while (iterator.hasNext()) {
                org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();

                MsdsDocumentInputBean msdsDocumentUpdateInputBean = new MsdsDocumentInputBean();
                BeanHandler.copyAttributes(lazyBean, msdsDocumentUpdateInputBean, getTcmISLocale(request));
                if (msdsDocumentUpdateInputBean.getOk().equalsIgnoreCase("true")) {
                    deleteCount++;
                    msdsDocumentInputBeanCollection.add(msdsDocumentUpdateInputBean);
                }
            }

            if (deleteCount > 0) {
                int documentDeleteCount = fromKitManagement?msdsDocumentProcess.deleteKitDocument(msdsDocumentInputBeanCollection, personnelId,msdsDocumentInputBean.getCompanyId()):msdsDocumentProcess.deleteDocument(msdsDocumentInputBeanCollection, personnelId,msdsDocumentInputBean.getCompanyId());
                if (documentDeleteCount == -1)
                	request.setAttribute("tcmISError", getResourceBundleValue(request, "error.db.update"));
            }

            request.setAttribute("msdsDocumentViewBeanCollection",fromKitManagement?msdsDocumentProcess.getSearchKitResult(msdsDocumentInputBean):msdsDocumentProcess.getSearchResult(msdsDocumentInputBean));
            return (mapping.findForward("showMsdsDocuments"));
        } else if ("login".equals(uAction) || (msdsDocumentInputBean.getShowDocuments() != null && msdsDocumentInputBean.getShowDocuments().length() > 0)) {
        	request.setAttribute("msdsDocumentViewBeanCollection", fromKitManagement?msdsDocumentProcess.getSearchKitResult(msdsDocumentInputBean):msdsDocumentProcess.getSearchResult(msdsDocumentInputBean));
        	this.saveTcmISToken(request);
        	if (msdsDocumentInputBean.getShowDocuments().equalsIgnoreCase("includeDeleted")) {
        		request.setAttribute("includeDeleted", "Yes");
        	}
        	request.setAttribute("personnelId", personnelId);
        	return (mapping.findForward("showMsdsDocuments"));
        } else {//calls from showaddmsdsdocument.do
        	//forcing caller to give value to documentTypeSource parameter
        	if (!StringHandler.isBlankString(msdsDocumentInputBean.getDocumentTypeSource())) {
            	VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request), getTcmISLocale(request));
            	request.setAttribute("vvMsdsDocumentTypeBeanCollection", vvDataProcess.getVvDocumentType(msdsDocumentInputBean.getDocumentTypeSource()));
        	} else
        		return mapping.findForward("systemerrorpage");
        	
        	if (form != null && msdsDocumentInputBean.getSubmitSave() != null &&
                    msdsDocumentInputBean.getSubmitSave().length() > 0) {

                msdsDocumentInputBean.setEnteredBy(personnelId);
                String addNewDocumentUrl = "";
                if(!FileHandler.isValidUploadFile(msdsDocumentInputBean.getTheFile())) {
                	request.setAttribute("errorMessage", "Document type not allowed.");
                } else {
                	
                	if(StringHandler.isBlankString(msdsDocumentInputBean.getCompanyId()) && fromKitManagement)
                		msdsDocumentInputBean.setCompanyId(user.getCompanyId());
    	            addNewDocumentUrl = msdsDocumentProcess.addNewDocument(msdsDocumentInputBean,fromKitManagement);
    	            if (addNewDocumentUrl != null && addNewDocumentUrl.length() > 0) {
    	                request.setAttribute("showdocument", "Yes");
    	                request.setAttribute("newDocumentUrl", addNewDocumentUrl);
    	            } else {
    	                request.setAttribute("errorMessage", "Document not Added");
    	            }
                }

                return (mapping.findForward("success"));
            } else {
                String module = ModuleUtils.getInstance().getModuleConfig(request).getPrefix();
                module = module.substring(1, module.length());
                if (user != null && !("haas".equalsIgnoreCase(module) || "catalog".equalsIgnoreCase(module) || "cv".equalsIgnoreCase(module))) {
                    PermissionBean permissionBean = user.getPermissionBean();
                    if (!permissionBean.hasFacilityPermission("msdsDocuments", null, null)) {
                        return mapping.findForward("nopermissions");
                    }
                }

                return mapping.findForward("success");
            }
        }//end of method
    }
} //end of class