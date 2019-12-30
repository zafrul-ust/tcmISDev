package com.tcmis.internal.report.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.internal.report.beans.UsersSavedQueriesBean;
import com.tcmis.internal.report.process.DisplayViewDetailProcess;
import com.tcmis.internal.report.process.SaveQueryProcess;

/******************************************************************************
 *
 * @version 1.0
 ******************************************************************************/
public class AjaxAction
extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		//if (!this.isLoggedIn(request)) {
			//  return (mapping.findForward("login"));
			//}
		//if(!this.hasPermission(request, "Invoice", null)) {
		//  BaseException be = new BaseException("No permission");
		//  be.setMessageKey("error.permission.invalid");
		//  throw be;
		//}

		String tableName = request.getParameter("tableName");
		String queryName = request.getParameter("queryName");
		String queryNameToSave = request.getParameter("queryNameToSave");
		System.out.println("TABLE NAME:" + tableName);
		System.out.println("QUERY NAME:" + queryName);
		System.out.println("PERSONNEL ID:" + getPersonnelId(request));
		StringWriter stringWriter = new StringWriter();
		if(tableName != null && tableName.length() > 0) {
			DisplayViewDetailProcess process = new DisplayViewDetailProcess(this.
					getDbUser(request));
			Collection userObjectBeanDetailCollection = process.getViewDetail(this.
					getPersonnelId(request),
					tableName);

			try {
				BeanWriter beanWriter = new BeanWriter(stringWriter);
				// Configure betwixt
				// For more details see java docs or later in the main documentation
				beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
				beanWriter.getBindingConfiguration().setMapIDs(false);
				beanWriter.enablePrettyPrint();
				beanWriter.write("data", userObjectBeanDetailCollection);
			}
			catch (Exception e) {
				log.error("Error writing data", e);
			}
		}
		else if(queryNameToSave != null && queryNameToSave.length() > 0) {
			SaveQueryProcess process = new SaveQueryProcess(getDbUser(request));
			UsersSavedQueriesBean bean = new UsersSavedQueriesBean();
			//BeanHandler.copyAttributes(form, bean);
			bean.setQueryName(queryName);
			bean.setCompanyId(this.getCompanyId(request));
			bean.setPersonnelId(this.getPersonnelId(request));
			UsersSavedQueriesBean usersSavedQueriesBean = process.getUserQuery(bean);
			try {
				BeanWriter beanWriter = new BeanWriter(stringWriter);
				// Configure betwixt
				// For more details see java docs or later in the main documentation
				beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
				beanWriter.getBindingConfiguration().setMapIDs(false);
				beanWriter.enablePrettyPrint();
				beanWriter.write("data", usersSavedQueriesBean);
			}
			catch (Exception e) {
				log.error("Error writing data", e);
			}
		}
		else {
			SaveQueryProcess process = new SaveQueryProcess(getDbUser(request));
			UsersSavedQueriesBean bean = new UsersSavedQueriesBean();
			//BeanHandler.copyAttributes(form, bean);
			bean.setQueryName(queryName);
			bean.setCompanyId(this.getCompanyId(request));
			bean.setPersonnelId(this.getPersonnelId(request));
			UsersSavedQueriesBean usersSavedQueriesBean = process.getUserQuery(bean);
			try {
				BeanWriter beanWriter = new BeanWriter(stringWriter);
				// Configure betwixt
				// For more details see java docs or later in the main documentation
				beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
				beanWriter.getBindingConfiguration().setMapIDs(false);
				beanWriter.enablePrettyPrint();
				beanWriter.write("data", usersSavedQueriesBean);
			}
			catch (Exception e) {
				log.error("Error writing data", e);
			}
		}
		if(log.isDebugEnabled()) {
			log.debug(stringWriter.toString());
		}
		PrintWriter writer = response.getWriter();
		writer.write(stringWriter.toString());
		writer.close();

		return mapping.findForward("default");
	}
}