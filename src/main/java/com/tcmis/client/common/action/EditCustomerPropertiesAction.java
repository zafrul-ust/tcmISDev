package com.tcmis.client.common.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.process.EditCustomerPropertiesProcess;
import com.tcmis.client.common.process.MsdsIndexingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.catalog.beans.MsdsBean;

/******************************************************************************
 * Controller for Add/Edit Customer Properties page
 * @version 1.0
 ******************************************************************************/

public class EditCustomerPropertiesAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		
		if (!this.isLoggedIn(request)) {
        	request.setAttribute("requestedPage", "editcustomerproperties");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
    	}
		
		String uAction = request.getParameter("uAction");
		String materialId = request.getParameter("materialId");
		String revisionDate = request.getParameter("revisionDate");
		
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
		
		EditCustomerPropertiesProcess eCProcess = new EditCustomerPropertiesProcess(this.getDbUser(request),getTcmISLocaleString(request));
        if ("update".equals(uAction)) {
        	
        	Vector<MsdsBean> beans = (Vector<MsdsBean>)BeanHandler.getBeans((DynaBean) form, "msds", new MsdsBean(), getTcmISLocale());
        	MsdsBean msdsBean = beans.get(0);
        	msdsBean.setMaterialId(new BigDecimal(materialId));
        	
        	DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
        	Date date = (Date)formatter.parse(revisionDate);  
        	msdsBean.setRevisionDate(date);
        	
        	eCProcess.updateCOForCatalog(msdsBean, personnelBean.getCompanyId(), personnelBean.getPersonnelId());
           
            request.setAttribute("done","Y");
        }else {
            //load initial data
        	MsdsIndexingProcess process = new MsdsIndexingProcess(personnelBean.getPersonnelIdBigDecimal(), getDbUser());
			request.setAttribute("vvFlashPointMethodCollection",process.setFlashPointMethod());
			
         	request.setAttribute("item", eCProcess.getCompanyMsdsInfo(materialId, revisionDate));

        }
		return (mapping.findForward("success"));
	}
}   //end of class