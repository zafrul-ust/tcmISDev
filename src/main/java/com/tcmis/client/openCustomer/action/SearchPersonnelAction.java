package com.tcmis.client.openCustomer.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.SearchPersonnelInputBean;
import com.tcmis.client.openCustomer.process.SearchPersonnelProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

public class SearchPersonnelAction extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "searchpersonnelmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    
   //if form is not null we have to perform some action
   if (form != null) {
      this.saveTcmISToken(request);
     //copy date from dyna form to the data form
     SearchPersonnelInputBean bean = new SearchPersonnelInputBean();
     BeanHandler.copyAttributes(form, bean);
     //check what button was pressed and determine where to go
     if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
       bean.setSearchUserId(personnelId);
       
       SearchPersonnelProcess process = new SearchPersonnelProcess(this.getDbUser(request));
       request.setAttribute("personnelBeanCollection", process.getSearchResult(bean));   
       if (bean.getStatus() == null || bean.getStatus().trim().length() == 0) {
    	   request.setAttribute("showLegend", "true");
       }
       return (mapping.findForward("showresults"));
     }else {
       //log initial data for dropdown
	     bean.setSearchUserId(personnelId);
	     SearchPersonnelProcess process = new SearchPersonnelProcess(this.getDbUser(request));
	     Collection dropDownDataColl = process.getDropDownData(personnelId);
		 request.setAttribute("dropDownDataCollection", dropDownDataColl);
	     return (mapping.findForward("success"));
     }
   }else {
     request.setAttribute("displayElementId", request.getParameter("displayElementId"));
     request.setAttribute("valueElementId", request.getParameter("valueElementId"));
     request.setAttribute("searchText", StringHandler.emptyIfNull(request.getParameter("searchText")));
   }
   return mapping.findForward("success");
  } //end of method
} //end of class