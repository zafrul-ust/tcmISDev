package com.tcmis.internal.catalog.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.catalog.process.FileUploadProcess;

public class FileUploadAction
    extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

	  if (!this.isLoggedIn(request)) {
		  request.setAttribute("requestedPage", "fileupload");
		  request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
		  return (mapping.findForward("login"));
	  }

	  PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
	  if ( !(personnelBean.getPermissionBean().hasUserPagePermission("catalogFileUpLoad") ) )
	  {
		  return (mapping.findForward("nopermissions"));
	  }

	  FileUploadBean bean = new FileUploadBean();
	  BeanHandler.copyAttributes(form, bean);
    //check if a file was uploaded
	  if(bean.getTheFile() != null && !FileHandler.isValidUploadFile(bean.getTheFile())) {
	    	request.setAttribute("errorMessage", "Invalid file.");
	  } else if(bean.getTheFile() != null) {
     	
		  FileUploadProcess process = new FileUploadProcess(this.getDbUser(request),this.getTcmISLocale(request));
		  String errorMessage = process.uploadFile(bean);
      //if I got here everything went fine, add "ok" message
		  if (errorMessage.length() == 0)
		  {
			  request.setAttribute("result", "ok");
		  }
		  else
		  {
			  request.setAttribute("errorMessage", errorMessage);
		  }
	  }    
	  return mapping.findForward("showinput");
  } //end of method
} //end of class