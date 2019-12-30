package com.tcmis.internal.distribution.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.DbuyContractAddChargeInputBean;
import com.tcmis.internal.distribution.beans.DbuyContractAddChargeViewBean;
import com.tcmis.internal.distribution.process.DbuyAdditionalChargesProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

public class DbuyAdditionalChargesAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		//Login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "supplierpricelistmain");
			//	This is to save any parameters passed in the URL, so that they can be acccesed after the login
			request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}  
		DbuyContractAddChargeInputBean inputBean = new DbuyContractAddChargeInputBean();
		DbuyAdditionalChargesProcess dBuyChargesProcess = new DbuyAdditionalChargesProcess(this.getDbUser(request),getTcmISLocaleString(request));
		
		BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
		if(inputBean.getStartDate() == null) {
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
			
			Date startDate = (Date)formatter.parse(request.getParameter("startDateString")); 
			inputBean.setStartDate(startDate);
		}
		
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		/*Need to check if the user has permissions to view this page. if they do not have the permission
        we need to not show them the page.*/
		if (inputBean.getDbuyContractId() == null || inputBean.getStartDate() == null) {
			return (mapping.findForward("nopermissions"));
		}
		Vector errorMsgs = new Vector();
		 if (inputBean.getAction() !=null &&  "search".equals(inputBean.getAction()))
         {
             Collection dBuyAdditionalChargesColl = dBuyChargesProcess.getDbuyCharges(inputBean,personnelBean);
             request.setAttribute("dBuyAdditionalChargesColl", dBuyAdditionalChargesColl);
             this.saveTcmISToken(request);
             return (mapping.findForward("success"));

         }
         
        else if ("new".equals(inputBean.getAction()) )
		{
        	
        	Collection newdbuyaddtlcoll = dBuyChargesProcess.listCharges(inputBean);
        	request.setAttribute("newdbuyaddtlcoll", newdbuyaddtlcoll);
             this.saveTcmISToken(request);
        	 return (mapping.findForward("success"));
        	 
		}
       			//  Update
		else if ("update".equals(inputBean.getAction())) {

			checkToken(request);

			// get the data from grid
			Collection<DbuyContractAddChargeViewBean> beans = BeanHandler.getBeans((DynaBean) form, "dbuyAdditionalCharges", new DbuyContractAddChargeViewBean(), getTcmISLocale(request));

			String error = dBuyChargesProcess.update(beans, personnelBean,inputBean);
			if(!StringHandler.isBlankString(error))
				errorMsgs.add(error);
		      else
			  errorMsgs = null;
			
			 Collection dBuyAdditionalChargesColl = dBuyChargesProcess.getDbuyCharges(inputBean,personnelBean);
             request.setAttribute("dBuyAdditionalChargesColl", dBuyAdditionalChargesColl);
			request.setAttribute("tcmISErrors", errorMsgs);
			return (mapping.findForward("success"));

		}
		else if ("delete".equals(inputBean.getAction())) {

			checkToken(request);

			// get the data from grid
			Collection<DbuyContractAddChargeViewBean> beans = BeanHandler.getBeans((DynaBean) form, "dbuyAdditionalCharges", new DbuyContractAddChargeViewBean(), getTcmISLocale(request));

			String error = dBuyChargesProcess.delete(beans, personnelBean);
			if(!StringHandler.isBlankString(error))
					errorMsgs.add(error);
			else
				errorMsgs = null;
			Collection dBuyAdditionalChargesColl = dBuyChargesProcess.getDbuyCharges(inputBean,personnelBean);
            request.setAttribute("dBuyAdditionalChargesColl", dBuyAdditionalChargesColl);
			request.setAttribute("tcmISErrors", errorMsgs);
			return (mapping.findForward("success"));

		}
		return (mapping.findForward("success"));
	 }
}
	


