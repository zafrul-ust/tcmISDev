package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.internal.distribution.process.ConsignmentCountUploadProcess;
import com.tcmis.supplier.shipping.beans.ScannerUploadInputBean;
import com.tcmis.supplier.xbuy.airgas.process.AirgasUploadProcess;

public class ConsignmentCountUploadAction
    extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "consignmentcountupload");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

	PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

    // Verify view permissions
    if (!personnelBean.getPermissionBean().hasUserPagePermission("consignmentProcessingUpload")) {
        return (mapping.findForward("nopermissions"));
    }
      
    ScannerUploadInputBean bean = new ScannerUploadInputBean();
    BeanHandler.copyAttributes(form, bean);
    //check if a file was uploaded
    bean.setEnteredBy(personnelBean.getPersonnelIdBigDecimal());
    if(bean.getTheFile() != null) {
    	if(!FileHandler.isValidUploadFile(bean.getTheFile()) ) {
			request.setAttribute("errorMessage", "File type not allowed.");
			return mapping.findForward("showinput");
    	}	
    	
      ConsignmentCountUploadProcess process = new ConsignmentCountUploadProcess(this);
      String errorMessage = "";
      if( "BAE".equalsIgnoreCase(bean.getCompanyId()) )
    	  errorMessage = process.uploadBAEFile(bean,personnelBean);
      else if( "Hamilton-Autocrib".equalsIgnoreCase(bean.getCompanyId()) )
    	  errorMessage = process.uploadAutoCribData(bean);
      else if( "SAS_TECH".equalsIgnoreCase(bean.getCompanyId()) )
    	  errorMessage = process.processSAS(bean,personnelBean);
      
//      else if( "Hamilton-Autocrib-Issue".equalsIgnoreCase(bean.getCompanyId()) )
//    	  errorMessage = process.uploadAutoCribIssueReport(bean);
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