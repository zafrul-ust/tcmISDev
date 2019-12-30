package com.tcmis.client.operations.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.operations.beans.EdiOrderStatusInputBean;
import com.tcmis.internal.hub.beans.EdiOrderErrorViewBean;
import com.tcmis.client.operations.process.EdiOrderStatusProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.SearchPersonnelInputBean;
import com.tcmis.common.admin.process.SearchPersonnelProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

/**
 * ***************************************************************************
 * Test Case: IAI, USGOV, MILLER have different right-click functions. 
 * 
 * 
 * ****************************************************************************
 */
public class EdiOrderStatusAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "ediorderstatusmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		HttpSession session = request.getSession();

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
/*		if (!personnelBean.getPermissionBean().hasUserPagePermission("orderEntry")) {
			return (mapping.findForward("nopermissions"));
		}  */
		SearchPersonnelInputBean bean = new SearchPersonnelInputBean();
		bean.setSearchUserId(personnelId);
		SearchPersonnelProcess personnelProcess = new SearchPersonnelProcess(this.getDbUser(request));
		Collection userCompanyColl = personnelProcess.getDropDownData(bean);
		request.setAttribute("userCompanyColl", userCompanyColl);
		
		if (form == null) return (mapping.findForward("success"));
		
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getTcmISLocale());
		String uAction = (String) ((DynaBean) form).get("uAction");

		String forward = "success";      

		EdiOrderStatusInputBean inputBean = new EdiOrderStatusInputBean();
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));
		EdiOrderStatusProcess process = new EdiOrderStatusProcess(this.getDbUser(request),getTcmISLocaleString(request));
	
		// Search
		if ("search".equals(uAction))  {
			request.setAttribute("ediOrderErrorViewBeanColl", process.getSearchResult(inputBean));
			request.setAttribute("ShipToListBean",process.getShiptoList(inputBean.getCompanyId()));   
			request.setAttribute("validUOSBean",process.getValidUnitOfSale(inputBean.getCompanyId()));
			this.saveTcmISToken(request);
			return (mapping.findForward(forward));
		}
		//  Create Excel
		else if ("createExcel".equals(uAction)) {
			this.setExcel(response, "EdiOrderStatus");
			process.getExcelReport(inputBean,
					(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}
		// Reset Status
		else if ("resetStatus".equals(uAction)) {
			checkToken(request);
			
			try {
				Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "ediOrderErrorViewBean", new EdiOrderErrorViewBean(), getTcmISLocale(request));
				process.resetStatus(personnelId, inputBean,updateBeanCollection);
			}
			catch (Exception ex) {
				ex.printStackTrace();
				request.setAttribute("tcmISError",library.getString("generic.error"));
			} 
			request.setAttribute("ediOrderErrorViewBeanColl", process.getSearchResult(inputBean));
			request.setAttribute("ShipToListBean",process.getShiptoList(inputBean.getCompanyId()));   
			request.setAttribute("validUOSBean",process.getValidUnitOfSale(inputBean.getCompanyId()));
			
			return (mapping.findForward(forward));
		}
		// Ignore the line
		else if ("ignoreLine".equals(uAction)) {
			checkToken(request);
			
//			 get parameters
			 String companyId = (String) request.getParameter("companyId");
			 String poNum = (String) request.getParameter("selectedCustomerPoNo");
			 String lineNum = (String) request.getParameter("selectedCustomerPoLineNo");
			 String loadLine = (String) request.getParameter("selectedLoadLine");            
			 String loadId = (String) request.getParameter("selectedLoadId");  
			 
		      try {
		    	 process.setToIgnoreStatus(companyId, poNum, lineNum, loadId, loadLine, personnelBean.getLogonId());                             
		      } catch (BaseException ex) {
		    	  ex.printStackTrace();
				  request.setAttribute("tcmISError",library.getString("generic.error"));
		      }            

		    request.setAttribute("ediOrderErrorViewBeanColl", process.getSearchResult(inputBean));
			request.setAttribute("ShipToListBean",process.getShiptoList(inputBean.getCompanyId()));   
			request.setAttribute("validUOSBean",process.getValidUnitOfSale(inputBean.getCompanyId()));
		
			return (mapping.findForward(forward));
		}		

	else{/*
		Collection<EdiOrderErrorViewBean> hubPreferredWareHouseCollection = process.getHubPreferredWareHouseList();
		request.setAttribute("preferredHubCollection",hubPreferredWareHouseCollection );

        ClientCabinetManagementProcess clientCabmgtProcess = new ClientCabinetManagementProcess(this.getDbUser(request),getTcmISLocaleString(request));
		request.setAttribute("hubCabinetViewBeanCollection", clientCabmgtProcess.getAllLabelData( hubPreferredWareHouseCollection));
		
		this.saveTcmISToken(request);		 */
	}

	return (mapping.findForward(forward));
}
}
