package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.tcmis.internal.distribution.beans.MrAddChargeViewBean;
import com.tcmis.internal.distribution.beans.PriceListInputBean;
import com.tcmis.internal.distribution.beans.PriceListOvBean;
import com.tcmis.internal.distribution.beans.UserOpsEntityPgViewBean;
import com.tcmis.internal.distribution.process.CustomerRequestProcess;
import com.tcmis.internal.distribution.process.PriceListProcess;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.process.LogisticsProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
     ******************************************************************************/
public class PriceListAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

	if (!this.checkLoggedIn( "pricelistmain") ) return mapping.findForward("login");
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    PermissionBean permmissionBean = personnelBean.getPermissionBean();
    if ( !permmissionBean.hasUserPagePermission("priceList") )
    {
      return (mapping.findForward("nopermissions"));
    }

    //populate drop downs
    PriceListInputBean inputBean = new PriceListInputBean();
    
    //PriceListOvBean inputBean = new PriceListOvBean();
    
    PriceListProcess process = new PriceListProcess(this);

    if (form == null) {
//    	VvDataProcess vvDataProcess = new VvDataProcess(this.getDbUser(request),getTcmISLocale(request));
//    	request.setAttribute("vvLotStatusBeanCollection", vvDataProcess.getActiveVvLotStatus());
//      get ops->pricelist connected drop down.
//    	CustomerRequestProcess p = new CustomerRequestProcess(this);
    	//request.setAttribute("priceList", p.getPriceListDropDown());
    	//request.setAttribute("opsPrice", p.getOpsPrice());
    	request.setAttribute("opsPgColl",
    	process.getSearchResult("select * from user_ops_entity_pg_view where personnel_id = {0} and company_id = {1} order by ops_entity_id,price_group_name",
    			new UserOpsEntityPgViewBean(),personnelBean.getPersonnelId(),personnelBean.getCompanyId()));
    	return (mapping.findForward("success"));
    }

    BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
// hard coded data.
//    inputBean.setCompanyId("MILLER");
//    inputBean.setSearchArgument("401674");

//    inputBean.setLotStatus(request.getParameterValues("lotStatus"));
    String action =  (String)( (DynaBean) form).get("uAction");			//
    if ( "search".equals(action) ) {
    	request.setAttribute("beanCollection", process.getPriceList(inputBean));
        this.saveTcmISToken(request);
    }
    else if ( action.equals("createXSL")) {
		try {
			this.setExcel(response,"Price List");
	    	process.getExcelReport(inputBean,process.getPriceList(inputBean), 
					 personnelBean.getLocale()).write(response.getOutputStream());
		}
		catch (Exception ex) {
		 ex.printStackTrace();
		 return mapping.findForward("genericerrorpage");
		}
		return noForward;
    } else
    if ( "update".equals(action) ) {
			java.util.Enumeration<String> e = request.getParameterNames();
		
		Vector<String> v = new Vector();
		while(e.hasMoreElements()) 
			v.add(e.nextElement());
		Collections.sort(v);
		for(String ss:v) {
			System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
		}
//        this.checkToken(request);
        String ErrorMsg = "";
//        if(    personnelBean.getPermissionBean().hasInventoryGroupPermission("GenerateOrders", request.getParameter("inventoryGroup"), null, null) 
//        	|| personnelBean.getPermissionBean().hasInventoryGroupPermission("shipConfirm", request.getParameter("inventoryGroup"), null, null)	
//        ) {
        Collection<PriceListOvBean> beans = BeanHandler.getBeans((DynaBean)form,"PriceListViewBean",new PriceListOvBean(),this.getTcmISLocale());
        HashMap<String,HashMap<Date,Vector<PriceListOvBean>>> partmap = new HashMap();
        Vector<PriceListOvBean> deleteStartDatemap = new Vector();
        for(PriceListOvBean bean:beans) {
        	//String partname = bean.getPartName();
        	String catpartno = bean.getCatPartNo();
        	//HashMap<Date,Vector<PriceListOvBean>> parts = partmap.get(partname);
        	HashMap<Date,Vector<PriceListOvBean>> parts = partmap.get(catpartno);
        	if( parts == null ) {
        		parts = new HashMap();
        		//partmap.put(partname, parts);
        		partmap.put(catpartno, parts);
        	}
        	Date startdate = bean.getStartDate();
        	if( "deleteStartDate".equals(bean.getChanged())){
        		Date oldStartDate = bean.getOldStartDate();
        		if( oldStartDate != null && "Y".equals(bean.getIsParent()))
        			deleteStartDatemap.add(bean);
        		continue;
        	}
//        	if( "markasdelete".equals(bean.getChanged())) continue;
        	if( startdate == null ) continue;
        	Vector<PriceListOvBean> partdates = parts.get(startdate);
        	if( partdates == null ) {
        		partdates = new Vector();
        		parts.put(startdate, partdates);
        	}
        	BigDecimal qty = bean.getBreakQuantity();
        	BigDecimal price = bean.getCatalogPrice();
        	if( qty ==  null || price == null ) continue;
        	if( "Y".equals(bean.getIsParent()) )
        		partdates.insertElementAt(bean, 0);
        	else {
        		partdates.add(bean);
        	}
        }
        for(String catpartno: partmap.keySet()) {
        	HashMap<Date,Vector<PriceListOvBean>> parts = partmap.get(catpartno);
        	Vector<Date> sort = new Vector();
        	for(Date startdate: parts.keySet() ) {
        		sort.add(startdate);
        	}
        	Collections.sort(sort);
        	for(Date startdate: sort ) {
        		Vector<PriceListOvBean> partdates = parts.get(startdate);
        		boolean changed = false;
        		for(PriceListOvBean bean:partdates) {
        			if( "Y".equals(bean.getIsParent()) ) {
        				if( !bean.getStartDate().equals(bean.getOldStartDate())) {
        					changed = true; break;
        				}
        			}
        			if( !bean.getBreakQuantity().equals(bean.getOldBreakQuantity()) ) {
        				changed = true; break;
        			}
        			if( !bean.getCatalogPrice().equals(bean.getOldCatalogPrice()) ) {
        				changed = true; break;
        			}
                	if( "markasdelete".equals(bean.getChanged())) {
                		changed = true; break;
                	}
        		}
        		if( !changed )continue;
        		String qty1 = "1";      
        		String price1 = ""; 
        		String qty2 = "";      
        		String price2 = ""; 
        		PriceListOvBean bean0 = null;
        		for(PriceListOvBean bean:partdates) {
        			if( "Y".equals(bean.getIsParent())) {
        				price1 = bean.getCatalogPrice().toString();
        				bean0 = bean;
        				continue;
        			}
                	if( "markasdelete".equals(bean.getChanged())) continue;
        			qty2 += "," + bean.getBreakQuantity();
        			price2 += "," + bean.getCatalogPrice();
        		}
        		qty1 += qty2;
        		price1 += price2;
        		if( bean0 != null )
        			process.ModifyPriceList(bean0,qty1,price1);
        	}
        	//        	if( "Y".equals(bean.getChanged()) ) ErrorMsg += process.ModifyPriceList(bean);
        	//     	   if( "Y".equals(bean.getChanged()) ) ErrorMsg += process.updateLine(bean);
        	//     	   if( "Remove".equals(bean.getChanged()) ) ErrorMsg += process.RemovePriceList(bean);
        }
        for(PriceListOvBean db: deleteStartDatemap) {
        	process.DeletePriceList(db);
        }
        request.setAttribute("tcmISError", ErrorMsg);
    	request.setAttribute("beanCollection", process.getPriceList(inputBean));
    	System.out.println("back from query");
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
