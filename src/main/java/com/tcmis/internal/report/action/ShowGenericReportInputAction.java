package com.tcmis.internal.report.action;

import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.report.beans.*;
import com.tcmis.internal.report.process.*;

/******************************************************************************
 * Controller for inventory reports
 * @version 1.0
     ******************************************************************************/
public class ShowGenericReportInputAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException {
    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "showgenericreportinput");
      return (mapping.findForward("login"));
    }
    DisplayViewDetailProcess process = new DisplayViewDetailProcess(this.
        getDbUser(request));
    Collection userObjectBeanCollection = process.getViews(this.getPersonnelId(
        request));
    request.setAttribute("userObjectBeanCollection",
                         userObjectBeanCollection);
    //we get to this action both with and without a form
    //if there is no form we'll default the table info
    //to the first table in the table collection
    UserObjectBean bean = new UserObjectBean();
    BeanHandler.copyAttributes(form, bean);
    if (bean == null ||
        bean.getTableName() == null ||
        bean.getTableName().length() < 1) {
      if (log.isDebugEnabled()) {
        log.debug("no bean");
      }
      Iterator iterator = userObjectBeanCollection.iterator();
      if (iterator.hasNext()) {
        bean = (UserObjectBean) iterator.next();
      }
    }
    if (bean != null && bean.getTableName() != null) {
      Collection userObjectBeanDetailCollection = process.getViewDetail(this.
          getPersonnelId(request),
          bean.getTableName());
      request.setAttribute("userObjectBeanDetailCollection",
                           userObjectBeanDetailCollection);
    }
      SaveQueryProcess saveQueryprocess = new SaveQueryProcess(getDbUser(request));
      UsersSavedQueriesBean queryBean = new UsersSavedQueriesBean();
      queryBean.setCompanyId(this.getCompanyId(request));
      queryBean.setPersonnelId(this.getPersonnelId(request));
      request.setAttribute("usersSavedQueriesBeanCollection", saveQueryprocess.getUserQueries(queryBean));
    return (mapping.findForward("genericreportinput"));
  }
}
