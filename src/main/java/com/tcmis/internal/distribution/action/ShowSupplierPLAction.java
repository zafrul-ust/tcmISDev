package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.DbuyInputBean;
import com.tcmis.internal.distribution.beans.PriceListOvBean;
import com.tcmis.internal.distribution.process.CustomerRequestProcess;
import com.tcmis.internal.distribution.process.DbuyProcess;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.process.LogisticsProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
     ******************************************************************************/
public class ShowSupplierPLAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

	if (!this.checkLoggedIn( "showsupplierpl") ) return mapping.findForward("login");
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    PermissionBean permmissionBean = personnelBean.getPermissionBean();
//    if ( !permmissionBean.hasUserPagePermission("priceList") )
//    {
//      return (mapping.findForward("nopermissions"));
//    }

    //populate drop downs
    DbuyInputBean inputBean = new DbuyInputBean();
    
    //PriceListOvBean inputBean = new PriceListOvBean();
    
    DbuyProcess process = new DbuyProcess(this);

    if (form == null) {
//    	VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
//    	request.setAttribute("vvLotStatusBeanCollection", vvDataProcess.getActiveVvLotStatus());
//      get ops->pricelist connected drop down.
//    	CustomerRequestProcess p = new CustomerRequestProcess(this);
    	//request.setAttribute("priceList", p.getPriceListDropDown());
//    	request.setAttribute("opsPrice", p.getOpsPrice());
    	return (mapping.findForward("success"));
    }
    request.setAttribute("startSearchTime", new Date().getTime() );
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
// hard coded data.
//    inputBean.setCompanyId("MILLER");
//    inputBean.setSearchArgument("401674");

//    inputBean.setLotStatus(request.getParameterValues("lotStatus"));
    String action =  (String)( (DynaBean) form).get("uAction");			//
    action ="search";
    inputBean.setSearchArgument(request.getParameter("itemId"));
    inputBean.setSearchMode("is");//.setSearchArgument(request.getParameter("itemId"));
    inputBean.setSearchField("itemId");//.setSearchMode("is");//.setSearchArgument(request.getParameter("itemId"));
    if ( "search".equals(action) ) {
    	request.setAttribute("beanCollection", process.getDbuy(inputBean,personnelId));
    }
    else if ( "createExcel".equals(action) ) {
    	try {
			this.setExcel(response,"pricelist");
//			process.getpricelistExcel(inputBean,personnelBean).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    }
    else if ( "loaddata".equals(action) ) {
//    	request.setAttribute("vvLotStatusLegend", process.getLotStatusLegend());
        return (mapping.findForward("loaddata"));
    }
    request.setAttribute("endSearchTime", new Date().getTime() );
    return (mapping.findForward("success"));
  }
}
