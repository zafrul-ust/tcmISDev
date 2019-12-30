package com.tcmis.internal.supply.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.SupplierItemNotesInputBean;
import com.tcmis.internal.supply.beans.SuppEntityItemNotesViewBean;
import com.tcmis.internal.supply.process.SupplierItemNotesProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Vector;

public class SupplierItemNotesAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "supplieritemnotesmain");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}  
		Vector errorMsgs = new Vector();
		SupplierItemNotesProcess notesProcess = new SupplierItemNotesProcess(this.getDbUser(request),getTcmISLocaleString(request));
		// copy date from dyna form to the data bean
		SupplierItemNotesInputBean inputBean = new SupplierItemNotesInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/*Need to check if the user has permissions to view this page. if they do not have the permission
        we need to not show them the page.*/
		if (!personnelBean.getPermissionBean().hasUserPagePermission("supplierItemNotes")) {
			return (mapping.findForward("nopermissions"));
		}
		
		 if (inputBean.getuAction() !=null &&  "search".equals(inputBean.getuAction()))
         {
             Collection<SuppEntityItemNotesViewBean> itemNotesColl = notesProcess.getItemNotes(inputBean,personnelBean);
             request.setAttribute("itemNotesViewBeanColl", itemNotesColl);
             this.saveTcmISToken(request);
             return (mapping.findForward("success"));

         }
         else if (inputBean.getuAction() !=null &&  "createExcel".equals(inputBean.getuAction()))
         {
             this.setExcel(response, "ItemNotesExcel");
             notesProcess.getExcelReport(notesProcess.getItemNotes(inputBean, personnelBean),(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
             return noForward;
        }
        else if ("new".equals(inputBean.getuAction()) )
		{
        	 if (!personnelBean.getPermissionBean().hasOpsEntityPermission("supplierItemNotes", inputBean.getOpsEntityId(), null)) {
     			return (mapping.findForward("nopermissions"));
     		}
        	             
             this.saveTcmISToken(request);
        	 return (mapping.findForward("success"));
        	 
		}
        else if ("submit".equals(inputBean.getuAction()))
        {
        	 if (!personnelBean.getPermissionBean().hasOpsEntityPermission("supplierItemNotes", inputBean.getOpsEntityId(), null)) {
     			return (mapping.findForward("nopermissions"));
     		}

             SuppEntityItemNotesViewBean suppEntityItemNotesViewBean =new SuppEntityItemNotesViewBean();
		     BeanHandler.copyAttributes(form, suppEntityItemNotesViewBean, getTcmISLocale(request));

             notesProcess.addNotes(suppEntityItemNotesViewBean, personnelBean);        		 
        	 request.setAttribute("done", "Y");
        }
		 
			//  Update
		else if ("update".equals(inputBean.getuAction())) {

			/*Need to check if the user has permissions to update this data. if they do not have the permission
			we show a error message.*/
       	 if (!personnelBean.getPermissionBean().hasOpsEntityPermission("supplierItemNotes", inputBean.getOpsEntityId(), null)) {
  			return (mapping.findForward("nopermissions"));
  		}

			// If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);

			// get the data from grid
			Collection<SuppEntityItemNotesViewBean> beans = BeanHandler.getBeans((DynaBean) form, "supplierItemNotesBean", new SuppEntityItemNotesViewBean(), getTcmISLocale(request));

			String error = notesProcess.update(beans, personnelBean);
			if(!StringHandler.isBlankString(error))
					errorMsgs.add(error);
			else
				errorMsgs = null;
	        Collection<SuppEntityItemNotesViewBean> itemNotesColl = notesProcess.getItemNotes(inputBean,personnelBean);
            request.setAttribute("itemNotesViewBeanColl", itemNotesColl);
			request.setAttribute("tcmISErrors", errorMsgs);
			return (mapping.findForward("success"));

		}
		else if ("delete".equals(inputBean.getuAction())) {

			/*Need to check if the user has permissions to update this data. if they do not have the permission
			we show a error message.*/
       	 if (!personnelBean.getPermissionBean().hasOpsEntityPermission("supplierItemNotes", inputBean.getOpsEntityId(), null)) {
  			return (mapping.findForward("nopermissions"));
  		}

			// If the page is being updated I check for a valid token.
			//checkToken will aslo save token for you to avoid duplicate form submissions.
			checkToken(request);

			// get the data from grid
			Collection<SuppEntityItemNotesViewBean> beans = BeanHandler.getBeans((DynaBean) form, "supplierItemNotesBean", new SuppEntityItemNotesViewBean(), getTcmISLocale(request));

			String error = notesProcess.delete(beans, personnelBean);
			if(!StringHandler.isBlankString(error))
					errorMsgs.add(error);
			else
				errorMsgs = null;
	        Collection<SuppEntityItemNotesViewBean> itemNotesColl = notesProcess.getItemNotes(inputBean,personnelBean);
            request.setAttribute("itemNotesViewBeanColl", itemNotesColl);
			request.setAttribute("tcmISErrors", errorMsgs);
			return (mapping.findForward("success"));

		}
		return (mapping.findForward("success"));
	 }
}
	


