package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.process.*;

/******************************************************************************
 * Controller for cabinet labels
 * @version 1.0
     ******************************************************************************/
public class MinMaxLevelAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {
    if (!this.isLoggedIn(request,true)) {
      request.setAttribute("requestedPage", "minmaxlevelmain");
      request.setAttribute("requestedURLWithParameters",
                           this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    if ( !personnelBean.getPermissionBean().hasUserPagePermission("minMaxLevels") )
    {
      return (mapping.findForward("nopermissions"));
    }

    MinMaxLevelInputBean bean = new MinMaxLevelInputBean();
    MinMaxLevelProcess process = new MinMaxLevelProcess(this.getDbUser(request),this.getTcmISLocaleString(request));

    if (form == null) {
        return (mapping.findForward("success"));
    }

    BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
    String action = (String) ((DynaBean) form).get("uAction");
    if (action == null) {    	 
        request.setAttribute("authorizedUsersForUsergroup", process.getAuthorizedUsersForUsergroup(personnelBean, "uploadPartLevel"));
    	return (mapping.findForward("success"));
    }
    
    if ( action.equals("showlevels")) {
        this.saveTcmISToken(request);
        request.setAttribute("startSearchTime", new Date().getTime() );
        request.setAttribute("currentMinMaxLevelViewBeanCollection",
                process.getSearchData(personnelBean, bean));
        request.setAttribute("endSearchTime", new Date().getTime() );
        return (mapping.findForward("showlevels"));
    }
    //if form is not null we have to perform some action
    if ( action.equals("search")) {
      request.setAttribute("currentMinMaxLevelViewBeanCollection",
                           process.getSearchData(personnelBean, bean));
      //request.setAttribute("minMaxLevelLogViewBeanCollection",
      //                     process.getHistoryData(bean));
      this.saveTcmISToken(request);
    }
    else if ( "createExcel".equals(action)){
   	   this.setExcel(response, "MinMaxLevelGenerateExcel");
        process.createExcelFile(process.getSearchData(personnelBean, bean), (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        return noForward;
   }else if ( "createTemplateData".equals(action)){
   	   this.setExcel(response, "MinMaxLevelGenerateExcel");
        process.createTemplateData(personnelBean, bean, (java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
        return noForward;
   }

    if ( action.equals("history")) {
      request.setAttribute("minMaxLevelLogViewBeanCollection",
                           process.getHistoryData(personnelBean, bean));
      this.saveTcmISToken(request);
    }
    else if ( action.equals("update")) {
      this.checkToken(request);
      if("showlevels".equals(request.getParameter("showlevels"))) {
          request.setAttribute("startSearchTime", new Date().getTime() );
      }
//		java.util.Enumeration<String> e = request.getParameterNames();
//
//		Vector<String> v = new Vector();
//		while(e.hasMoreElements()) 
//			v.add(e.nextElement());
//		Collections.sort(v);
//		for(String ss:v) {
//			System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
//		}
      
      Collection c = new Vector();
      DynaBean dynaBean = (DynaBean) form;
			Collection beans = BeanHandler.getBeans(dynaBean,
					"minMaxLevelInputBean", new MinMaxLevelInputBean(), getTcmISLocale(request));
      Collection errorCollection = process.update(beans,personnelBean);
      if (errorCollection.size() > 0) {
        request.setAttribute("tcmISErrors", errorCollection);
      }
      request.setAttribute("currentMinMaxLevelViewBeanCollection",
                           process.getSearchData(personnelBean, bean));
      if("showlevels".equals(request.getParameter("showlevels"))) {
          request.setAttribute("endSearchTime", new Date().getTime() );
          return (mapping.findForward("showlevels"));
      }
      
//      request.setAttribute("minMaxLevelLogViewBeanCollection",
//                           process.getHistoryData(bean));
    }
    return (mapping.findForward("success"));
  }
}