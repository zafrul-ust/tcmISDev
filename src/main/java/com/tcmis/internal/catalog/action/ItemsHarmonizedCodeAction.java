package com.tcmis.internal.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.ItemBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.catalog.beans.HtcLocalOvBean;
import com.tcmis.internal.catalog.process.ItemSearchProcess;

/******************************************************************************
 * Controller for logistics
 * @version 1.0
     ******************************************************************************/
public class ItemsHarmonizedCodeAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "itemsharmonizedcodemain");
			request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    PermissionBean permmissionBean = personnelBean.getPermissionBean();
    if ( !permmissionBean.hasUserPagePermission("itemHarmonizedCode"))
    {
      return (mapping.findForward("nopermissions"));
    }

    LogisticsInputBean inputBean = new LogisticsInputBean();

    if (form == null) {
    	return (mapping.findForward("success"));
    }

    ItemSearchProcess process = new ItemSearchProcess(this.getDbUser(request),this.getTcmISLocale(request));
	GenericProcess gprocess = new GenericProcess(this.getDbUser(request),this.getTcmISLocale(request));
	String headerQuery = "select * from global.vv_harmonized_trade_code order by harmonized_trade_code";
	String regionQuery = "select * from Global.vv_htc_customs_region order by description";
	
	BeanHandler.copyAttributes(form, inputBean, this.getTcmISLocale(request));
    String action =  (String)( (DynaBean) form).get("uAction");			//
    if ( "search".equals(action) ) {
		request.setAttribute("headerColl", gprocess.getSearchResultObjects(headerQuery ) );
		request.setAttribute("regionColl", gprocess.getSearchResultObjects(regionQuery ) );
    	request.setAttribute("beanCollection", process.getItemBeanCollection(inputBean));
    	request.setAttribute("beanOvCollection", process.getItemBeanOvCollection(inputBean));
		this.saveTcmISToken(request);
    }
    else if ( "update".equals(action) ) {
		this.checkToken(request);
// run update code here.    
        Collection<HtcLocalOvBean> coll = BeanHandler.getBeans((DynaBean)form, "ItemBean", new HtcLocalOvBean());
		if (personnelBean.getPermissionBean().hasOpsEntityPermission("itemHarmonizedCode", null, null)) {
// loop 1. do HTC update.
        HashMap<BigDecimal,HtcLocalOvBean> parentMap = new HashMap();
        for(HtcLocalOvBean bean:coll) {
        	BigDecimal itemId = bean.getItemId();
        	if( "Y".equals(bean.getIsParent())) {
        		parentMap.put(itemId, bean);
        		if( "true".equals( bean.getOk() ) )// grid beans.
        				process.updateHoronizedCode(bean);
        	}
        }
// loop 2. do extension update
        for(HtcLocalOvBean bean:coll) {
        	BigDecimal itemId = bean.getItemId();
        	HtcLocalOvBean pbean = parentMap.get(itemId);
    		if( "true".equals( pbean.getOk() ) )// grid beans.
				process.updateExtension(pbean,bean);
        }
		}
		request.setAttribute("headerColl", gprocess.getSearchResultObjects(headerQuery ) );
		request.setAttribute("regionColl", gprocess.getSearchResultObjects(regionQuery ) );
    	request.setAttribute("beanCollection", process.getItemBeanCollection(inputBean));
    	request.setAttribute("beanOvCollection", process.getItemBeanOvCollection(inputBean));
//		request.setAttribute("headerColl", gprocess.getSearchResultObjects(headerQuery ) );
//        request.setAttribute("beanCollection", process.getItemBeanCollection(inputBean));
    }
    else if ( "createExcel".equals(action) ) {
    	try {
			this.setExcel(response,"Items Without Harmonized Code");
			process.ItemsWithoutHoronizedCodeExcelReport(inputBean).write(response.getOutputStream());
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
