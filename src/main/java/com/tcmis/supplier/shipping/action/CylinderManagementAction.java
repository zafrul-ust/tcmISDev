package com.tcmis.supplier.shipping.action;

import java.util.Collection;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.CylinderManagementBean;
import com.tcmis.supplier.shipping.process.CylinderManagementProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/**
 * ***************************************************************************
 * Controller for Cylinder Management
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CylinderManagementAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "cylindermanagementmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		if (!personnelBean.getPermissionBean().hasUserPagePermission("cylinderManagement"))
			return (mapping.findForward("nopermissions"));

		//if form is not null we have to perform some action
		if (form != null) {
			CylinderManagementProcess process = new CylinderManagementProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			//copy date from dyna form to the data form
			CylinderManagementBean inputBean = new CylinderManagementBean(form,getTcmISLocale(request));
			if (inputBean.isSearch()) {
				this.saveTcmISToken(request);
				request.setAttribute("cylinderManagementCollection",process.getSearchResult(inputBean));
			} else if (inputBean.isCreateExcel()) {
				this.setExcel(response, "EDI Order Tracking");
				process.createExcelReport(inputBean).write(response.getOutputStream());
				return noForward;
			}else if (inputBean.isShowNewEditCylinder()) {
				Hashtable vvCylinderData = process.getVvCylinderData(inputBean,personnelBean);
				request.setAttribute("cylinderLocationColl",vvCylinderData.get(process.LOCATION_COLL));
				request.setAttribute("cylinderConditionCodeColl",vvCylinderData.get(process.CONDITION_CODE_COLL));
				request.setAttribute("cylinderStatusColl",vvCylinderData.get(process.CYLINDER_STATUS_COLL));
				return mapping.findForward("addNewEditCylinder");
			}else if (inputBean.isAddNewEditCylinder()) {
				String errorMsg = process.addNewEditCylinder(inputBean,personnelBean);
				if (!StringHandler.isBlankString(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				Hashtable vvCylinderData = process.getVvCylinderData(inputBean,personnelBean);
				request.setAttribute("cylinderLocationColl",vvCylinderData.get(process.LOCATION_COLL));
				request.setAttribute("cylinderConditionCodeColl",vvCylinderData.get(process.CONDITION_CODE_COLL));
				request.setAttribute("cylinderStatusColl",vvCylinderData.get(process.CYLINDER_STATUS_COLL));
				return mapping.findForward("addNewEditCylinder");
			}else if (inputBean.isShowEditCategory()) {
				request.setAttribute("subcategoryColl",process.getEditCategory(inputBean));
				return mapping.findForward("editSubcategory");
			}else if (inputBean.isSubmitSubcategory()) {
				Collection beans = BeanHandler.getBeans((DynaBean) form, "cylinderSubcategoryDiv", new CylinderManagementBean(), this.getTcmISLocale(request));
				String errorMsg = process.submitSubcategory(inputBean,beans,personnelBean);
				if (!StringHandler.isBlankString(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				request.setAttribute("subcategoryColl",process.getEditCategory(inputBean));
				return mapping.findForward("editSubcategory");
			}else if (inputBean.isViewCylinderDetail()) {
				request.setAttribute("cylinderDetailColl",process.getCylinderDetail(inputBean));
				return mapping.findForward("cylinderDetails");
			}else {
				//load initial drop down data
				inputBean.setSupplier("All");
				Hashtable vvCylinderData = process.getCylinderStatus(inputBean);
				request.setAttribute("cylinderStatusColl",vvCylinderData.get(process.CYLINDER_STATUS_COLL));
				request.setAttribute("supplierLocationCollection",process.buildSupplierLocationObj(personnelBean));
			}
		}
		return mapping.findForward("success");
	}
}