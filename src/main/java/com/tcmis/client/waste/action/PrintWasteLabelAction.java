package com.tcmis.client.waste.action;

import javax.servlet.http.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import org.apache.struts.action.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.waste.process.PrintWasteLabelProcess;

/******************************************************************************
 * Controller for print label
 * @version 1.0
 ******************************************************************************/
public class PrintWasteLabelAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    PrintWasteLabelProcess process = new PrintWasteLabelProcess(this.getDbUser(request));
    String pdfUrl = process.printLabel(request);
    if (pdfUrl.length() > 0) {
       request.setAttribute("filePath", pdfUrl);
       return mapping.findForward("viewfile");
     }else {
       return mapping.findForward("systemerrorpage");
     }
  }

}
