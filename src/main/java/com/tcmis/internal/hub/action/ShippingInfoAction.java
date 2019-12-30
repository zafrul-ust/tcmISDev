package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.DotPartMaterialViewBean;
import com.tcmis.internal.hub.process.ExpertReviewItemProcess;
import com.tcmis.internal.hub.process.ShippingInfoProcess;


/******************************************************************************
 * Controller for shipping info
 * @version 1.0
     ******************************************************************************/
public class ShippingInfoAction
    extends TcmISBaseAction {

  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws
      BaseException, Exception {

	  PersonnelBean personnelBean = null;
	  if (!"JDE".equals(request.getParameter("source"))) {
		  if (!this.isLoggedIn(request,true)) {
				request.setAttribute("requestedPage", "shippinginfomain");
				request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));
				return (mapping.findForward("login"));
			}
		  
			personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		     if (!personnelBean.getPermissionBean().hasUserPagePermission("dotShippingInfo"))
		     {
		       return (mapping.findForward("nopermissions"));
		     }
	  }


		//	  main
		if( form == null )
			return (mapping.findForward("success"));
		//String action = request.getParameter("uAction");

		DotPartMaterialViewBean inputBean = new DotPartMaterialViewBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
		
		if( inputBean.getuAction() == null ) {
			request.setAttribute("blank","Y");
			return mapping.findForward("success");
		}
		//	  result
		
		StringBuffer requestURL = request.getRequestURL();
		ShippingInfoProcess process = new ShippingInfoProcess(this.getDbUser(request),getTcmISLocaleString(request));	    
		
		if ("search".equals(inputBean.getuAction()) ) {
			saveTcmISToken(request);
			
			request.setAttribute("dotPartMaterialColl",process.getDotPartMaterialViewColl(inputBean));
			
			ExpertReviewItemProcess expertReviewProc = new ExpertReviewItemProcess(this.getDbUser(request),getTcmISLocaleString(request));
			request.setAttribute("expertReviewList", expertReviewProc.listsByItem(inputBean.getItemId()));
//			request.setAttribute("hazardClassColl",process.getHazardClassColl());
		} else if ("update".equals(inputBean.getuAction()) && personnelBean != null) {
			BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
			checkToken(request);

			// Check if the user has permissions to update
			if (personnelBean.getPermissionBean().hasOpsEntityPermission("shippingInfo", null, null) ) {
				Collection updateBeanCollection = BeanHandler.getBeans((DynaBean) form, "dotPartMaterialBean", new DotPartMaterialViewBean(), getTcmISLocale(request));
				
				if(updateBeanCollection != null) {
					Collection errors = process.updateItem( personnelId, updateBeanCollection);
					if (errors != null)
						request.setAttribute("tcmISErrors",errors);
				}
			}
			else {
				request.setAttribute("tcmisError", getResourceBundleValue(request, "error.noaccesstopage"));
			}
			
			request.setAttribute("dotPartMaterialColl",process.getDotPartMaterialViewColl(inputBean));
//			request.setAttribute("hazardClassColl",process.getHazardClassColl());
		} else {/* set up search page data*/
			
		}
		
		assignStorageValidValueLists(request, process);
		return mapping.findForward("success");
	}
  
    public void assignStorageValidValueLists(HttpServletRequest request, ShippingInfoProcess process) throws BaseException {
    	request.setAttribute("acidBaseChoices", process.listAcidBaseChoices());
		request.setAttribute("corrosiveChoices", process.listCorrosiveChoices());
		request.setAttribute("aerosolChoices", process.listAerosolChoices());
		request.setAttribute("flammableChoices", process.listFlammableChoices());
		request.setAttribute("toxicChoices", process.listToxicChoices());
		request.setAttribute("oxidizerChoices", process.listOxidizerChoices());
		request.setAttribute("reactiveChoices", process.listReactiveChoices());
		request.setAttribute("waterReactiveChoices", process.listWaterReactiveChoices());
		request.setAttribute("organicPeroxideChoices", process.listOrganicPeroxideChoices());
		request.setAttribute("containerMaterialChoices", process.listContainerChoices());
    }
}
