package com.tcmis.internal.distribution.action;

import java.util.Collections;
import java.util.Vector;

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
import com.tcmis.internal.distribution.beans.PriceListInputBean;
import com.tcmis.internal.distribution.beans.PriceListOvBean;
import com.tcmis.internal.distribution.process.CustomerRequestProcess;
import com.tcmis.internal.distribution.process.PriceListProcess;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.process.LogisticsProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
     ******************************************************************************/
public class NewPriceListAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

	if (!this.checkLoggedIn( "newpricelist") ) return mapping.findForward("login");
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    PermissionBean permmissionBean = personnelBean.getPermissionBean();
//    if ( !permmissionBean.hasUserPagePermission("priceList") )
//    {
//      return (mapping.findForward("nopermissions"));
//    }

    //populate drop downs
    PriceListInputBean inputBean = new PriceListInputBean();
    
    //PriceListOvBean inputBean = new PriceListOvBean();
    
    PriceListProcess process = new PriceListProcess(this);

    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
// hard coded data.
//    inputBean.setCompanyId("MILLER");
//    inputBean.setSearchArgument("401674");

//    inputBean.setLotStatus(request.getParameterValues("lotStatus"));
    String action =  (String)( (DynaBean) form).get("uAction");			//
    if ( "new".equals(action) ) {
    	
		java.util.Enumeration<String> e = request.getParameterNames();
		
		Vector<String> v = new Vector();
		while(e.hasMoreElements()) 
			v.add(e.nextElement());
		Collections.sort(v);
		for(String ss:v) {
			System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
		}
		String companyId = request.getParameter("companyId");
		String name = request.getParameter("priceGroupName");
		String desc = request.getParameter("priceGroupDesc");
		String ops = request.getParameter("opsEntityId");
		String currencyId = request.getParameter("currencyId");		
//		String baselineresetdate = request.getParameter("baselineresetdate");		
		
		request.setAttribute("tcmISError", process.newPriceGroup(companyId,name,desc,ops,currencyId,inputBean.getExpireDate()));
    	request.setAttribute("done", "Y");
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
    return (mapping.findForward("success"));
  }
}
