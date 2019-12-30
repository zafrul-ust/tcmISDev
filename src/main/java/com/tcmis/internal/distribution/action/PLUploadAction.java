package com.tcmis.internal.distribution.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.distribution.process.PLUploadProcess;

public class PLUploadAction
    extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "plupload");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
      
	FileUploadBean bean = new FileUploadBean();
    BeanHandler.copyAttributes(form, bean);
    //check if a file was uploaded
    if(bean.getTheFile() != null && (!bean.getTheFile().getName().endsWith("xlsx") && !bean.getTheFile().getName().endsWith("xls"))) {
    	request.setAttribute("errorMessage", "Invalid file.");
    } else if(bean.getTheFile() != null){
    	PLUploadProcess process = new PLUploadProcess(this);
    	String uAction = request.getParameter("pricelistAction");
    	String errorMessage = "";
    	if("update".equalsIgnoreCase(uAction))
    		errorMessage = process.uploadUpdateFile(bean, personnelBean);
    	else
    		errorMessage = process.uploadNewFile(bean, personnelBean);
		//if I got here everything went fine, add "ok" message
		if (StringHandler.isBlankString(errorMessage))
		{
			request.setAttribute("result", "ok");
		}
		else{
			request.setAttribute("errorMessage", errorMessage);
		}
	}    
    return mapping.findForward("showinput");
  } //end of method
} //end of class