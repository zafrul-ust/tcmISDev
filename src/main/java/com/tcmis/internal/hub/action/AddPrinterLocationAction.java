package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserPageSelectViewBean;
import com.tcmis.common.admin.process.UserPageSelectViewProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LocationLabelPrinterBean;
import com.tcmis.internal.hub.beans.PrinterLocationBean;
import com.tcmis.internal.hub.process.AddPrinterLocationProcess;


public class AddPrinterLocationAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		
		if (!this.isLoggedIn(request)) {
		      request.setAttribute("requestedPage", "addnewprinterloc");
		      request.setAttribute("requestedURLWithParameters",
		                           this.getRequestedURLWithParameters(request));
		      return (mapping.findForward("login"));
		    }
		String forward = "success";

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

		if (!(personnelBean.getPermissionBean().hasOpsEntityPermission("addprinterloc", null, null))) {

	            return (mapping.findForward("nopermissions"));
			}
		
		PrinterLocationBean inputBean = new PrinterLocationBean();
		BeanHandler.copyAttributes(form, inputBean);
		
		AddPrinterLocationProcess process = new AddPrinterLocationProcess(this.getDbUser(request));

		 String action = inputBean.getuAction();
		
		if (action != null && "new".equals(action)){
			UserPageSelectViewProcess companyProcess = new UserPageSelectViewProcess(this.getDbUser(request),getTcmISLocaleString(request));
        	DynaBean dynaBean = (DynaBean) form;
        	UserPageSelectViewBean bean = new UserPageSelectViewBean();
        	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
        	bean.setPersonnelId(personnelId);
        	bean.setUserId(personnelId);
        	Vector v = (Vector) companyProcess.getSearchResult(bean);
            request.setAttribute("UserPageSelectViewCollection",v);
            Collection c = process.getResult("");
            
            request.setAttribute("locationLabelPrinterBeanCollection",c);
		}
		
		if (action != null && "insert".equals(action)) {
			
			String ErrorMsg = "";
			ErrorMsg += process.insertPrinterLocation(inputBean);
			if(ErrorMsg.length() == 0){
				Collection<LocationLabelPrinterBean> beans = BeanHandler.getBeans((DynaBean) form, "locationlabelPrinterBean", new LocationLabelPrinterBean(), this.getTcmISLocale());
				for (LocationLabelPrinterBean bean : beans) {
					
						ErrorMsg += process.insertPrinterPath(bean,inputBean);
									
			   }
			 }
			
				UserPageSelectViewProcess companyProcess = new UserPageSelectViewProcess(this.getDbUser(request),getTcmISLocaleString(request));
	        	DynaBean dynaBean = (DynaBean) form;
	        	UserPageSelectViewBean bean = new UserPageSelectViewBean();
	        	BeanHandler.copyAttributes(dynaBean, bean, getTcmISLocale(request));
	        	bean.setPersonnelId(personnelId);
	        	bean.setUserId(personnelId);
	        	Vector v = (Vector) companyProcess.getSearchResult(bean);
	            request.setAttribute("UserPageSelectViewCollection",v);
	            Collection c = process.getResult("");
	            
	            request.setAttribute("locationLabelPrinterBeanCollection",c);
	            if(ErrorMsg.length() != 0){
					request.setAttribute("tcmISError", ErrorMsg);}
			else{
				request.setAttribute("done","Y");
			     }
			
		}
		
		
		return (mapping.findForward(forward));
	}
}