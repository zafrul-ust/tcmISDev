package com.tcmis.client.report.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.client.report.process.ListManagementProcess;
import com.tcmis.client.report.beans.GlobalAndCustomerListViewBean;
import com.tcmis.client.report.beans.ListManagementInputBean;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;


/**
 * Controller for list Management.
 */

public class ListManagementAction extends TcmISBaseAction {
	
	private Collection doSearch(ListManagementProcess process, ListManagementInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		Collection<GlobalAndCustomerListViewBean> results = process.getListOfListChemicals(inputBean,personnelBean);
		//input.setTotalLines(results.size());
		// Stick the results into the session
		request.setAttribute("listChemicalColl",results);
		// save the token if update actions can be performed later.
		saveTcmISToken(request);
		return results;
	}
	

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "listmanagementmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
		 if ( !personnelBean.getPermissionBean().hasUserPagePermission("listManagement") )
		    {
		      return (mapping.findForward("nopermissions"));
		    }

		//if form is not null we have to perform some action
		if (form != null) {
			//copy date from dyna form to the data form
			ListManagementInputBean inputBean = new ListManagementInputBean();
			BeanHandler.copyAttributes(form, inputBean);

			ListManagementProcess process = new ListManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
			if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction())){
				doSearch(process,inputBean,personnelBean);
	            return (mapping.findForward("success"));
	        }else if (inputBean.getuAction() !=null &&  "updateListofLists".equals(inputBean.getuAction())){
                if (!personnelBean.getPermissionBean().hasPermission("editCustomerList", null)){
                     return (mapping.findForward("nopermissions"));
                }
                GlobalAndCustomerListViewBean bean = new GlobalAndCustomerListViewBean();
                Collection<GlobalAndCustomerListViewBean> beans;
                beans = BeanHandler.getBeans((DynaBean) form, "listManagementViewBean", bean,getTcmISLocale(request));
                Vector errorMsgs = (Vector)process.updateListofLists(beans,personnelBean);
				 
                if(errorMsgs.get(1) != null &&  ((String)errorMsgs.get(1)).length() != 0) {
                    request.setAttribute("tcmISError", errorMsgs.get(1));
                    request.setAttribute("closeNewListWin", "N");				
                }
                else
                {
                	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale(request));
	        	 	request.setAttribute("tcmISError",library.getString("label.updatewarning"));
	            }
                doSearch(process,inputBean,personnelBean);
                return mapping.findForward("success");
            }else if (inputBean.getuAction() !=null &&  "deleteList".equals(inputBean.getuAction())){
                String errorMsg = process.deleteList(inputBean);
                if (!"OK".equals(errorMsg)) {
                    request.setAttribute("listManagementCallBack", true);
                    request.setAttribute("callBackError", errorMsg);
                }
                else
                {
                    request.setAttribute("listManagementCallBack", true);
                    request.setAttribute("callBackError", "");
                }
                //doSearch(process,inputBean,personnelBean);
	            return (mapping.findForward("deletelist"));
            }else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction())){
	             this.setExcel(response, "ChemicalListsExcel");
	             process.getExcelReport(doSearch(process,inputBean,personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
	             return noForward;
	        }else if (inputBean.getuAction() !=null &&  "showUploadList".equals(inputBean.getuAction())) {
                 if (!personnelBean.getPermissionBean().hasPermission("editCustomerList", null)){
                     return (mapping.findForward("nopermissions"));
                 }
                 return mapping.findForward("showupdatelist");
	        }else if ("processUpload".equals(request.getParameter("uAction"))){
				
			    ScannerUploadInputBean bean = new ScannerUploadInputBean();
			    BeanHandler.copyAttributes(form, bean);

			    Vector errorMsgs =  process.uploadListFile(bean,personnelBean);        	
			    
				if(errorMsgs.get(1) != null &&  ((String)errorMsgs.get(1)).length() != 0) {
					request.setAttribute("tcmISError", errorMsgs.get(1));
					request.setAttribute("closeNewListWin", "N");				
				}else
					{
						request.setAttribute("closeNewListWin", "Y");
	                    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getTcmISLocale(request));
		        	 	request.setAttribute("tcmISError",library.getString("label.updatewarning"));
	                }
				return mapping.findForward("showupdatelist");
		    }

        }
		return mapping.findForward("success");
	}
}