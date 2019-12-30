package com.tcmis.client.catalog.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatPartCommentBean;
import com.tcmis.client.catalog.beans.CatalogCartBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.ClientLabelBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.process.UserFacilityUgAdminOvProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserFacilityAdminViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class CatalogCommonAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	 PersonnelBean user = null;
	 String uAction = request.getParameter("uAction");

	 if (!this.isLoggedIn(request)) {
			 request.setAttribute("requestedPage", "showcatalog");
			 request.setAttribute("requestedURLWithParameters",
												 this.getRequestedURLWithParameters(request));
			 return (mapping.findForward("login"));
	 }

	 
	 if (form == null) {
/*		 try {
		 personnelBean = new PersonnelBean();
		 personnelBean.setCompanyId(this.getClient(request));
		 personnelBean.setLogonId(request.getParameter("userid"));
		 personnelBean.setClearTextPassword(request.getParameter("passwd"));
		 LoginProcess loginProcess = new LoginProcess(this.getDbUser(request)); 
		 personnelBean = loginProcess.loginWeb(personnelBean,false);
		 personnelBean.setLocale(com.tcmis.common.admin.action.LoginAction.setLocale(request, request.getSession(),request.getParameter("locale")));

		 this.setSessionObject(request,personnelBean,"personnelBean");
		 } catch(Exception ex) { return mapping.findForward("nopermissions");}
*/
		 return mapping.findForward("success");
	}
	 
	if( !this.isLoggedIn(request)) return mapping.findForward("nopermissions");
	
	user = (PersonnelBean)this.getSessionObject(request,"personnelBean");
	BigDecimal personnelId = new BigDecimal(user.getPersonnelId());

	OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.
	 getDbUser(request),this.getTcmISLocaleString(request));

	//if form is not null we have to perform some action
	if (form != null) {
	 //copy date from dyna form to the data form
	 CatalogInputBean bean = new CatalogInputBean();
	 
	 BeanHandler.copyAttributes(form, bean,this.getTcmISLocale(request));

//	 for( String key: (java.util.Set<String>) request.getParameterMap().keySet() ) {
//		 System.out.println(key+":"+request.getParameter(key));
//	 }
	 CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request),this.getTcmISLocaleString(request));
	 CatalogProcess catalogProcess2 = new CatalogProcess(this.getDbUser(request),this.getTcmISLocale(request));

	 if ( uAction != null && uAction.equals("showCart") ) {
	        Collection<CatalogCartBean> beans  = BeanHandler.getBeans((DynaBean)form, "CatalogCartBean",new CatalogCartBean(),this.getLocale(request));      
	        request.setAttribute("CatalogCartBeanCollection", beans);
	    	return mapping.findForward("showcart");
	 } else if ( "search".equals(uAction) ) {
				Vector<PrCatalogScreenSearchBean> c = (Vector<PrCatalogScreenSearchBean>)catalogProcess.getSearchResult(bean, user);
				Object[] results = catalogProcess.createRelationalObjects(c);		
				request.setAttribute("prCatalogScreenSearchBeanCollection", results[0]);
				request.setAttribute("rowCountPart", results[1]);
				request.setAttribute("rowCountItem", results[2]);
				this.saveTcmISToken(request);
//				request.setAttribute("prCatalogScreenSearchBeanCollection",
//				catalogProcess.createRelationalObject(c));
	 }	 
	 else if ( "loaddata".equals(uAction) ) {
		 PrCatalogScreenSearchBean pbean = new PrCatalogScreenSearchBean();
		 BeanHandler.copyAttributes(form, pbean,this.getTcmISLocale(request));
		 
		 request.setAttribute("specColl",catalogProcess.getSpecMenu(pbean) );
		 request.setAttribute("partInvColl",catalogProcess.getInventoryMenu(pbean) );
		 request.setAttribute("stockingReorder",catalogProcess.getStockingReorder(pbean) );
		 request.setAttribute("ImgLit",catalogProcess.getImgLit(pbean) );
		 
		 if( "jsondata".equals(request.getParameter("jsondata")))
			 return mapping.findForward("jsondata");
		 else
			 return mapping.findForward("success");
	 }
//	 search frame
	 else {
			request.setAttribute("myWorkareaFacilityViewBeanCollection",
					 orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
	 }
	 return mapping.findForward("success");
	}
	return mapping.findForward("success");
 }
}
