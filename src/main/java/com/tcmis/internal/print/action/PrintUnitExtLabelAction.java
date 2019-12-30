package com.tcmis.internal.print.action;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.process.UnitExtLabelProcess;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: nawaz.shaik
 * Date: Feb 11, 2009
 * This class is used to print unit and ext labels for polchem project.
 * this URL is called only after the complate packing group has been scanned on the scanner.
 * I need to check which label need to be printed and print them.
 */
public class PrintUnitExtLabelAction
    extends TcmISBaseAction{

  public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
          BaseException, Exception {
/*
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showallocationanalysis");
      //This is to save any parameters passed in the URL, so that they
      //can be acccesed after the login
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
*/
    String result = "OK";

    LabelInputBean inputBean = new LabelInputBean();
    BeanHandler.copyAttributes(form, inputBean);
    if(log.isDebugEnabled()) {
        log.debug("got bean:" + inputBean);
    }
    try {
        if(inputBean.getLabelType().equalsIgnoreCase("UNITEXT")) {
          UnitExtLabelProcess labelProcess = new UnitExtLabelProcess(this.getDbUser(request));
          labelProcess.buildUnitExtLabels(inputBean);
        }
        else {
            throw new BaseException("Invalid label type:" + inputBean.getLabelType());
        }
    }
    catch(Exception e) {
        result = e.getMessage();
        log.info("Error getting label for:"+e.getMessage());
    }
    PrintWriter writer = response.getWriter();
    writer.write(result);
    writer.close();

    HttpSession session = request.getSession(false);
    if (session != null) {
      try {        
        session.invalidate();
      }
      catch (Exception e) {
        //ignore
      }
    }
    return noForward;
  }
}
