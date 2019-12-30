package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

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

import com.tcmis.internal.hub.beans.BatchTestResultViewBean;
import com.tcmis.internal.hub.process.BatchTestResultViewProcess;

/******************************************************************************
 * Controller for BatchTestResult
 * @version 1.0
 ******************************************************************************/
public class BatchTestResultAction extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception {

		//  login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "BatchTestResultmain"
					.toLowerCase());
			request.setAttribute("requestedURLWithParameters", this
					.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		String action = (String) ((DynaBean) form).get("_action");
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(
				request, "personnelBean");
		BatchTestResultViewProcess process = new BatchTestResultViewProcess(
				this.getDbUser(request));
		//  main
		if (action == null || action.equals("test")) {
			request.setAttribute("batchId", request.getParameter("batchId"));
			request.setAttribute("batchCloseDate", request
					.getParameter("batchCloseDate"));
			request.setAttribute("inventoryGroup", request
					.getParameter("inventoryGroup"));
		}
		//  result
		else if (action != null && action.equals("display")) {
			BatchTestResultViewBean bean = new BatchTestResultViewBean();
			BeanHandler.copyAttributes(form, bean);
			boolean readonly = (personnelBean.getPermissionBean()
					.hasInventoryGroupPermission("BatchTesting", request
							.getParameter("inventoryGroup"), null, null));
			Vector v = (Vector) process.getSearchResult(bean, readonly);
			request.setAttribute("BatchTestResultViewCollection", v);
			v = (Vector) process.getNameList(bean);
			request.setAttribute("nameLists", v);
			request.setAttribute("inventoryGroup", request
					.getParameter("inventoryGroup"));
			request.setAttribute("readonly", request.getParameter("readonly"));
		}
		//  update
		else if (action != null && action.equals("update")) {
			Collection c = new Vector();

			java.util.Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()) {
				String name = (String) e.nextElement();
				System.out.println("Name:"+name+":value:"+request.getParameter(name));
			}
			DynaBean dynaBean = (DynaBean) form;
			BatchTestResultViewBean[] beans = null; 
			Vector v = (Vector) BeanHandler
					.getBeans(dynaBean, "batchTestBean",
							new BatchTestResultViewBean());
			Object[] objs = v.toArray();
			BatchTestResultViewBean bean0 = null, bean = null;
			for (int ii = 0; ii < objs.length; ii++) {
				bean = (BatchTestResultViewBean ) objs[ii];
				if (bean.getModified().equalsIgnoreCase("Y")) {
					process.updateValue(bean, new BigDecimal(personnelBean
							.getPersonnelId()));
					if (bean0 == null)
						bean0 = bean;
				}
			}
			request.setAttribute("BatchTestResultViewCollection", process
					.getSearchResult(bean0, true));
			request.setAttribute("nameLists", process.getNameList(bean0));
			request.setAttribute("readonly", request.getParameter("readonly"));
		}
		return mapping.findForward("success");
	}
}