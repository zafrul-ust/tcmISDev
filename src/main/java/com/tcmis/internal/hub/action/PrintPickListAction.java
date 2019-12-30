package com.tcmis.internal.hub.action;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.beans.PicklistViewBean;
import com.tcmis.internal.hub.process.PrintPickListProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class PrintPickListAction extends TcmISBaseAction {

   public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
     throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "carrierpickingmain");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    PicklistViewBean inputBean = new PicklistViewBean();
	  BeanHandler.copyAttributes(form, inputBean);

    if (inputBean.getPicklistId() == null) {
      return mapping.findForward("systemerrorpage");
    } else {
      //call the process here
      PrintPickListProcess process = new PrintPickListProcess(this.getDbUser(request));
      String url = process.createPrintablePickList(inputBean,personnelId);      
      request.setAttribute("filePath", url);
      return mapping.findForward("viewfile");
    }
  } //end of method
} //end of class