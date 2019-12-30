package com.tcmis.client.raytheon.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.raytheon.beans.PossUploadInputBean;
import com.tcmis.client.raytheon.process.PossUploadProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.FileHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class PossUploadAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
    //if form is not null we have to perform some action
    if (form != null) {
      //copy date from dyna form to the data form
      PossUploadInputBean inputBean = new PossUploadInputBean();
      BeanHandler.copyAttributes(form, inputBean);
      if (inputBean.getTheFile() != null) {
    	if(!FileHandler.isValidUploadFile(inputBean.getTheFile())){
    		request.setAttribute("result","Not OK");
            return (mapping.findForward("success"));
    	}
    		
        //check what button was pressed and determine where to go
        PossUploadProcess process = new PossUploadProcess(this.getDbUser(request));
        process.load(inputBean);
        //get search result
        request.setAttribute("result","OK");
        return (mapping.findForward("success"));
      }else {
        return mapping.findForward("success");
      }
    } else {
      return mapping.findForward("success");
    }
  }
}