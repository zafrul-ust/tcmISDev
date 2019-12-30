package com.tcmis.supplier.shipping.action;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;
import com.tcmis.supplier.shipping.process.ScannerUploadProcess;

public class ScannerUploadAction
    extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "scannerupload");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("scannerUpload"))
    {
      return (mapping.findForward("nopermissions"));
    }

    ScannerUploadInputBean bean = new ScannerUploadInputBean();
    BeanHandler.copyAttributes(form, bean);
    //check if a file was uploaded
    if(bean.getTheFile() != null) {
      String extension = FilenameUtils.getExtension(bean.getTheFile().getName());
      if (!"csv".equalsIgnoreCase(extension)) {
  		  request.setAttribute("errorMessage", "File type not allowed.");
  		  return mapping.findForward("showinput");
  	  }
    	
      ScannerUploadProcess process = new ScannerUploadProcess(this.getDbUser(request));
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