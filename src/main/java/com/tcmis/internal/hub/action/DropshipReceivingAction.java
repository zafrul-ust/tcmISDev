package com.tcmis.internal.hub.action;

import com.tcmis.client.operations.beans.DropShipReceivingInputBean;
import com.tcmis.client.operations.process.DropshipReceivingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.process.ShipmentHistoryProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
 ******************************************************************************/
public class DropshipReceivingAction extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "dropshipreceivingmain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
     
    if (!personnelBean.getPermissionBean().hasUserPagePermission("dropshipReceiving"))
    {
      return (mapping.findForward("nopermissions"));
    }
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

    if (form == null) {
       DropshipReceivingProcess process = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));
       personnelBean.getPermissionBean().setUserGroupMemberLocationBeanCollection(process.getLocationPermissions(personnelId));
       this.setSessionObject(request, null, "dropshipReceivingViewBeanCollection");
       return mapping.findForward("success");
    }

    ShipmentHistoryProcess igdprocess = new ShipmentHistoryProcess(this.getDbUser(request),getTcmISLocaleString(request));
    String action = (String)( (DynaBean) form).get("action");
    //search
    if( action == null || action.equals("")) {
    	request.setAttribute("hubPrefViewOvBeanCollection",igdprocess.getDropDownData(personnelId.intValue()));
    	request.setAttribute("searchWhat", "PO");
        return mapping.findForward("success");
    }

    //if form is not null we have to perform some action
    if (form != null) {
      //copy date from dyna form to the data form
      DropShipReceivingInputBean inputBean = new DropShipReceivingInputBean();
      inputBean.setDoTrim(true);
      BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

      //check what button was pressed and determine where to go
      if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        this.saveTcmISToken(request);
        //check if it's chemical or non-chemical
        DropshipReceivingProcess process = new DropshipReceivingProcess(this.getDbUser(request),getTcmISLocaleString(request));

        //get search result
        Collection c = process.getResult(inputBean, personnelBean.getPermissionBean().hasLocationPermission("DropshipReceiving", null, inputBean.getFacilityId(), inputBean.getCompanyId()),personnelBean);
        //add result and pass it to the jsp page
        request.setAttribute("companyId",inputBean.getCompanyId());
        request.setAttribute("facilityId",inputBean.getFacilityId());
        this.setSessionObject(request, c, "dropshipReceivingViewBeanCollection");
        request.setAttribute("receivingViewRelationBeanCollection", process.createRelationalObject(c));
        return (mapping.findForward("showresults"));
      }
    }
    return mapping.findForward("success");
  }
 }