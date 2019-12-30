package com.tcmis.client.raytheon.action;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.raytheon.beans.UpdateGoletaPoViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.client.raytheon.process.UpdateApexPOProcess;


  public class UpdateApexPOAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws BaseException, Exception {

  	if (!this.isLoggedIn(request)) {
  		  request.setAttribute("requestedPage", "updateapexpo".toLowerCase());
  		  request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
  		  return (mapping.findForward("login"));
      }

    /*Since there is no search section we consider this the start time. This should be done only for
    * pages that have no search section.*/
    request.setAttribute("startSearchTime", new Date().getTime() );
    String forward = "success";
    
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

    UpdateGoletaPoViewBean inputBean = new UpdateGoletaPoViewBean();
    BeanHandler.copyAttributes(form, inputBean);
    if( inputBean.getPrNumber() == null ) return mapping.findForward("nopermission");

    UpdateApexPOProcess process = new UpdateApexPOProcess(this.getDbUser(request),getTcmISLocaleString(request));

    String action = request.getParameter("uAction");
    log.debug(action);
    if (action != null && "update".equals(action)) {
        this.checkToken(request);
        Collection<UpdateGoletaPoViewBean> beans = BeanHandler.getBeans((DynaBean)form,"beanCollDiv",new UpdateGoletaPoViewBean(),this.getTcmISLocale(request));
          Object[] result = process.update(beans,personnelBean);
        request.setAttribute("tcmISErrors", result[1]);
    }
    String[] searchCriteriaNames = { "prNumber" };
    String[] searchCriteriaValues = { inputBean.getPrNumber().toString() };

    request.setAttribute("beanColl",
                process.getSearchResult(inputBean,searchCriteriaNames,searchCriteriaValues));
    this.saveTcmISToken(request);

    /*Since there is no search section we have to set end Time here as we cannot use the client side time.
    Client can be anywhere in the world.This should be done only for pages that have no search section.*/
    request.setAttribute("endSearchTime", new Date().getTime() );
    return (mapping.findForward(forward));
  }
}