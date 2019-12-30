package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.internal.distribution.beans.*;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.process.AddChargeProcess;


  public class ShowOrdersAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws BaseException, Exception {
        String source = "showpreviousorders";
      	if (!this.checkLoggedIn( source ) ) return mapping.findForward("login");
      	request.setAttribute("source", source );
        /*Since there is no search section we consider this the start time. This should be done only for
        * pages that have no search section.*/
        request.setAttribute("startSearchTime", new Date().getTime() );
        String forward = "success";
        
        PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

        SalesSearchBean in = new SalesSearchBean();
        BeanHandler.copyAttributes(form, in);
//        if( in.getPrNumber() == null ) return mapping.findForward("nopermission");

        AddChargeProcess process = new AddChargeProcess(this);
//        process.getSearchResult("select * from table (pkg_rli_sales_order.fx_additional_charge({0}, {1}, {2}))", in,in.getCompanyId(),in.getPrNumber(),in.getLineItem());
//        select * from table (pkg_sales_search.FX_PREVIOUS_ORDER (
//                123457, -- item_id
//                '', -- spec_list
//                null, -- inventory_group
//                null, -- company_id
//                'US', -- region,
//                null -- limit_days
    //))

//    			java.util.Enumeration e = request.getParameterNames();
//    			while(e.hasMoreElements()) {
//    				String name = (String) e.nextElement();
//    				System.out.println("Name:"+name+":value:"+request.getParameter(name));
//    			}

        String action = request.getParameter("uAction");
        log.debug(action);
        if (action != null && "update".equals(action)) {
            this.checkToken(request);
            Collection<MrAddChargeViewBean> beans = BeanHandler.getBeans((DynaBean)form,"MrAddChargeView",new MrAddChargeViewBean(),this.getTcmISLocale());
            String ErrorMsg = "";
            for(MrAddChargeViewBean bean:beans) {
          	   if( "New".equals(bean.getChanged()) ) ErrorMsg += process.addLineCharge(bean);
         	   if( "Y".equals(bean.getChanged()) ) ErrorMsg += process.updateLine(bean);
         	   if( "Remove".equals(bean.getChanged()) ) ErrorMsg += process.removeLine(bean);
         	}
            request.setAttribute("tcmISError", ErrorMsg);
        }

//        request.setAttribute("beanColl",
//                process.getSearchResult(in,"mr_add_charge_view","prNumber",in.getPrNumber().toString()));
//        in.setItemId(new BigDecimal("123457"));
//        in.setSpecList("");
//       // in.setInventoryGroup(null);
//        in.setRegion("US");
//        in.setStartDate(null);
//        in.setEndDate(new Date());
//        in.setCustomerId(null);//customerId
	    if ( "No Specification".equals(in.getSpecList()))
	    {
	        in.setSpecList("");
	    }

		request.setAttribute("beanColl",process.getSearchResult("select * from table (pkg_sales_search.FX_PREVIOUS_ORDER ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7} ))", in,
				 in.getItemId(),
				 personnelBean.getCompanyId(),
				 new BigDecimal(personnelBean.getPersonnelId()),
				 in.getSpecList(),
				 in.getInventoryGroup(),
				 in.getCompanyId(),
				 in.getCustomerId(),
				 in.getRegion()));
        
//       request.setAttribute("beanColl",
//    		   process.getSearchResult("select * from table (pkg_sales_search.FX_PREVIOUS_ORDER ({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7} ))", in,in.getItemId(),in.getSpecList(),in.getInventoryGroup(),in.getCompanyId(),in.getCustomerId(),in.getRegion(),in.getStartDate(),in.getEndDate()));
//        request.setAttribute("feeDropDown",
//                process.getSearchResult(new VvAddChargeViewBean(),"vv_add_charge_view"));
//        request.setAttribute("addChargeRecurrenceDropDown",
//                process.getSearchResult(new VvChargeRecurrenceBean(),"vv_charge_recurrence","lineApplicable","Y"));
        
        
        this.saveTcmISToken(request);

        /*Since there is no search section we have to set end Time here as we cannot use the client side time.
        Client can be anywhere in the world.This should be done only for pages that have no search section.*/
        request.setAttribute("endSearchTime", new Date().getTime() );
        return (mapping.findForward(forward));
      }
 }