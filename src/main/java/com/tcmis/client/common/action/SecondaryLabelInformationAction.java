package com.tcmis.client.common.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import java.util.Collection;
import com.tcmis.client.common.process.SecondaryLabelInformationProcess;
import com.tcmis.client.common.beans.SecondaryLabelDataBean;
import com.tcmis.client.common.beans.SecLblIconsInLabelViewBean;

/**
 * ***************************************************************************
 * Controller for receiving
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class SecondaryLabelInformationAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "secondarylabelinformation");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		if (!this.isLoggedIn(request)) return mapping.findForward("nopermissions");

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		String uAction = request.getParameter("uAction");
		String facilityId = request.getParameter("facilityId");
		String materialId = request.getParameter("materialId");
		
//		CatalogProcess catalogProcess = new CatalogProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		
		SecondaryLabelInformationProcess process = new SecondaryLabelInformationProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
		PermissionBean permissionBean = personnelBean.getPermissionBean();
		if ("update".equals(uAction)) {
			if (!permissionBean.hasFacilityPermission("manageSecLblInfo", facilityId, "LOCKHEED") &&
					!permissionBean.hasFacilityPermission("secLblInfoAdmin", facilityId, "LOCKHEED")) {
				return mapping.findForward("nopermissions");
			}
			
			Collection<SecondaryLabelDataBean> beans = BeanHandler.getBeans((DynaBean)form,"secondaryLabelDataBean",new SecondaryLabelDataBean(),this.getTcmISLocale(request));
			Collection<SecLblIconsInLabelViewBean> iconBeans = BeanHandler.getBeans((DynaBean)form,"iconBean",new SecLblIconsInLabelViewBean(),this.getTcmISLocale(request));
			
			Collection errorMsgs = process.updateMaterialInfo(personnelId,materialId,facilityId,beans);
			Collection moreMsgs = process.updateIcons(personnelId,materialId,facilityId,iconBeans);
			
			if(errorMsgs != null && moreMsgs != null && moreMsgs.size() > 0)
				errorMsgs.addAll(moreMsgs);
			else if(errorMsgs == null && moreMsgs != null && moreMsgs.size() > 0)
				errorMsgs = moreMsgs;
		
			if(errorMsgs != null && errorMsgs.size() > 0)
				request.setAttribute("tcmISErrors", errorMsgs);
			
			request.setAttribute("MaterialBean", process.searchMaterialInfo(materialId,facilityId));
			request.setAttribute("typeNameColl", process.getTypeNameDropdowns(facilityId));
			request.setAttribute("secondaryLabelDataColl", process.getSecondaryLabelData(materialId,facilityId));
			Collection iconColl = process.getIcons(materialId,facilityId);
			if(iconColl.size() > 0)
				request.setAttribute("iconColl", iconColl);
			else {
				request.setAttribute("iconColl", process.getDefaultIcons());
			}
			
		}else {
			request.setAttribute("MaterialBean", process.searchMaterialInfo(materialId,facilityId));
			request.setAttribute("typeNameColl", process.getTypeNameDropdowns(facilityId));
			request.setAttribute("secondaryLabelDataColl", process.getSecondaryLabelData(materialId,facilityId));
			Collection iconColl = process.getIcons(materialId,facilityId);
			if(iconColl.size() > 0)
				request.setAttribute("iconColl", iconColl);
			else {
				request.setAttribute("iconColl", process.getDefaultIcons());
			}
		}
		return mapping.findForward("success");
	}
}
