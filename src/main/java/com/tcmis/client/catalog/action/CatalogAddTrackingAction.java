package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Vector;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatalogAddTrackingInputBean;
import com.tcmis.client.catalog.beans.NewChemTrackingViewBean;
import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.process.CatalogAddTrackingProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for catalog add tracking
 * @version 1.0
 ******************************************************************************/
public class CatalogAddTrackingAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws BaseException, Exception {
      if (!this.isLoggedIn(request)) {
           request.setAttribute("requestedPage", "catalogaddtrackingmain");
           request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
           return (mapping.findForward("login"));
      }

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		
	     if (!personnelBean.getPermissionBean().hasUserPagePermission("catalogAddTracking"))
	     {
	       return (mapping.findForward("nopermissions"));
	     }
	     
		if( form == null )
			return (mapping.findForward("success"));
		
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		String forward = "success";      

		CatalogAddTrackingInputBean inputBean = new CatalogAddTrackingInputBean();
		BeanHandler.copyAttributes(form, inputBean,getTcmISLocale(request));

		CatalogAddTrackingProcess process = new CatalogAddTrackingProcess(this.getDbUser(request),getTcmISLocaleString(request));

        // Search
		if (null!=inputBean.getAction() &&	"search".equals(inputBean.getAction()))  {
			Vector<NewChemTrackingViewBean> c = (Vector<NewChemTrackingViewBean>) process.getSearchResult(inputBean,personnelBean);
			Object[] results = process.createRelationalObjects(c);
			request.setAttribute("newChemTrackingCollection", results[0]);
			request.setAttribute("rowCountRequest", results[1]);
			request.setAttribute("rowCountLineItem", results[2]);
			request.setAttribute("qualityIdLabelColumnHeader", results[3]);
			request.setAttribute("catPartAttrColumnHeader", results[4]);
        }else if (null!=inputBean.getAction() && "resubmitRejectedRequest".equals(inputBean.getAction())) {
            StringBuffer requestURL = request.getRequestURL();
            CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
            CatAddHeaderViewBean tmpBean = new CatAddHeaderViewBean();
            tmpBean.setCalledFrom("catalogAddTracking");
            tmpBean.setCompanyId(inputBean.getCompanyId());
            tmpBean.setRequestId(inputBean.getRequestId());
            if ("OK".equals(catalogAddProcess.copyRequest(tmpBean,personnelBean))) {
                request.setAttribute("requestId",tmpBean.getRequestId());
                return mapping.findForward("resubmitRejectedRequest");
            }else {
                return noForward;
            }
        }else if (null!=inputBean.getAction() && "createExcel".equals(inputBean.getAction())) {
			this.setExcel(response, "New_Chem_Tracking_List");
			process.getExcelReport(inputBean,personnelBean,
					(java.util.Locale) request.getSession().getAttribute("tcmISLocale")).write(response.getOutputStream());
			return noForward;
		}else{
			//this.saveTcmISToken(request);
			//load initial data
			OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			request.setAttribute("myWorkareaFacilityViewBeanCollection", orderTrackingProcess.getMyWorkareaFacilityViewBean(personnelId));
            //user facility approval roles
            request.setAttribute("userFacilityApprovalRoleColl",process.getUserFacilityApprovalRoles(personnelBean));
            //company use msds
            CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
            request.setAttribute("isCompanyUsesCustomerMSDS",personnelBean.isFeatureReleased("ShowKitMaterialMsds","ALL",personnelBean.getCompanyId())?"Y":"N");
        }
		return (mapping.findForward(forward));
	}
	
}
