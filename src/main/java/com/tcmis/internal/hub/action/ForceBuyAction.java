package com.tcmis.internal.hub.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ForceBuyInputBean;
import com.tcmis.internal.hub.beans.ItemInventoryForceBuyViewBean;
import com.tcmis.internal.hub.beans.ItemCountInventoryViewBean;
import com.tcmis.internal.hub.process.ForceBuyProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;



public class ForceBuyAction extends TcmISBaseAction{
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "forcebuymain");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		/*Need to check if the user has permissions to view this page. if they do not have the permission
		we need to not show them the page.*/

	  if (!personnelBean.getPermissionBean().hasUserPagePermission("forceBuy"))
	  {
	    return (mapping.findForward("nopermissions"));
	  }
		
//		main
		if( form == null ) return mapping.findForward("success");
//		search
		String uAction = (String) ( (DynaBean)form).get("uAction");
		if( uAction == null ) return mapping.findForward("success");
//		result
	
//		String act = request.getParameter("uAction");
		ForceBuyProcess process = new ForceBuyProcess(this.getDbUser(request),getTcmISLocaleString(request));

		ForceBuyInputBean inputBean = new ForceBuyInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if (uAction.equals("search")) {
			this.saveTcmISToken(request);
			Collection itemInvDetailViewBeanColl = process.getSearchResult(inputBean);
			request.setAttribute("itemInvDetailViewBeanColl", itemInvDetailViewBeanColl);
			return (mapping.findForward("success"));
		}
		else if (uAction.equals("createExcel")) {
			
			Vector v = (Vector) process.getSearchResult(inputBean);
			this.setExcel(response,"ForceBuy");
			process.createExcelFile(v, (java.util.Locale)request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		else if( uAction.equals("update") ) {
	    	checkToken(request);
	    	
	    	 /*Need to check if the user has permissions to update this data. if they do not have the permission
	  	  we show a error message.*/
	      if (!personnelBean.getPermissionBean().hasInventoryGroupPermission("ForceBuy",null,null,null))
	      {
	            request.setAttribute("tcmISError",getResourceBundleValue(request,"error.noaccesstopage"));
	            request.setAttribute("itemInvDetailViewBeanCollection", process.getSearchResult(inputBean));
	            return (mapping.findForward("success"));
	      }
	      
			Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form,"itemInvDetailViewBean", new ItemCountInventoryViewBean(),getTcmISLocale(request),"okDoUpdate");
			Collection<String> errorMessageCollection = process.updateItemInvColl(updateBeanCollection, personnelBean);
		
			request.setAttribute("tcmISErrors", errorMessageCollection);
			
			Collection itemInvDetailViewBeanColl = process.getSearchResult(inputBean);
			request.setAttribute("itemInvDetailViewBeanColl", itemInvDetailViewBeanColl);
			return (mapping.findForward("success"));	
	    }
		else {
			return (mapping.findForward("success"));
		}
	}



}


