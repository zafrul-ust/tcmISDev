package com.tcmis.supplier.shipping.action;

import java.util.Collection;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.CylinderDocumentManagerBean;
import com.tcmis.supplier.shipping.beans.CylinderManagementBean;
import com.tcmis.supplier.shipping.process.CylinderDocumentManagerProcess;
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
 * Controller for Cylinder Document Manager
 *
 * @version 1.0
 *          ****************************************************************************
 */
public class CylinderDocumentManagerAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "cylinderdocumentmanager");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//if form is not null we have to perform some action
		if (form != null) {
			PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
			CylinderDocumentManagerProcess process = new CylinderDocumentManagerProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
			//copy date from dyna form to the data form
			CylinderDocumentManagerBean inputBean = new CylinderDocumentManagerBean(form,getTcmISLocale(request));
			if (inputBean.isViewCylinderDocumentManager()) {
				request.setAttribute("cylinderDocumentBeanCollection",process.getCylinderDocuments(inputBean));
			}else if (inputBean.isDeleteDocument()) {
				Collection beans = BeanHandler.getBeans((DynaBean) form, "cylinderDocumentBean", new CylinderDocumentManagerBean(), this.getTcmISLocale(request));
				String errorMsg = process.deleteDocument(beans,personnelBean);
				if (!StringHandler.isBlankString(errorMsg)) {
					request.setAttribute("tcmISError", errorMsg);
				}
				request.setAttribute("cylinderDocumentBeanCollection",process.getCylinderDocuments(inputBean));
			}else if (inputBean.isShowAddCylinderDocument()) {
				request.setAttribute("cylinderDocumentTypeCollection",process.getCylinderDocumentTypes(inputBean));
				return mapping.findForward("addCylinderDocument");
			}else if (inputBean.isAddCylinderDocument()) {
				process.addNewDocument(inputBean,personnelBean);
				return mapping.findForward("addCylinderDocument");
			}
		}
		return mapping.findForward("success");
	}
}
