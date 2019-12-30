package com.tcmis.internal.print.action;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ContainerLabelDetailViewBean;
import com.tcmis.internal.print.process.UnitLabelPartNumberProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnitLabelPartNumberAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	  request.setAttribute("requestedPage", "unitlabelpartnumber");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}

  ContainerLabelDetailViewBean inputBean = new ContainerLabelDetailViewBean();
  BeanHandler.copyAttributes(form, inputBean);

  UnitLabelPartNumberProcess process = new UnitLabelPartNumberProcess(this.getDbUser(request));
  request.setAttribute("labelDataCollection",process.getSearchData(inputBean));
    
  return mapping.findForward("sucess");
 }
} //end of class