package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.CatAddStatusViewBean;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * 
 * @version 1.0
 ******************************************************************************/
public class EngEvalAction extends TcmISBaseAction {

	private static final String CAT_ADD_HEADER_VIEW_BEAN		      = "catAddHeaderViewBean";
	private static final String TCMIS_ERROR								= "tcmISError";
	private static final String MATERIAL_REQUEST_DATA  		      = "materialRequestData";

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "engeval");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//	  main
		if (form == null)
			return (mapping.findForward("success"));
		String action = request.getParameter("uAction");
		if (action == null)
			return mapping.findForward("success");
		//	  result
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		StringBuffer requestURL = request.getRequestURL();
		EngEvalProcess process = new EngEvalProcess(this.getDbUser(request), getTcmISLocaleString(request), requestURL.substring(0, requestURL.lastIndexOf("/")));
		//
		CatAddHeaderViewBean inputBean = new CatAddHeaderViewBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

		if (action.equals("new")) {
			this.saveTcmISToken(request);

			Collection detailData = new Vector();
			detailData.add("");
			detailData.add("");
			detailData.add("");
			request.setAttribute("catalogAddItemBeanColl", detailData);

			String facilityId = request.getParameter("facilityId").toString();
			String application = request.getParameter("application").toString();
			BigDecimal itemId = new BigDecimal(request.getParameter("itemId").toString());
			String accountSysId = request.getParameter("accountSysId").toString();

			Vector infos = (Vector) process.getInfo(facilityId, application, itemId, accountSysId, personnelId);

		}
		else if (action.equals("view")) {
			BigDecimal requestId = inputBean.getRequestId();
			if (requestId != null) {
				CatAddHeaderViewBean catAddHeaderViewBean = process.getEngEvalData(requestId, personnelBean);
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN, catAddHeaderViewBean);

				MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
				mrBean.setPrNumber(catAddHeaderViewBean.getPrNumber());
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request), requestURL.substring(0, requestURL.lastIndexOf("/")));
				mrProcess.setCallFromEval(true);
				mrProcess.setCatAddHeaderViewBean(catAddHeaderViewBean);
				request.setAttribute(MATERIAL_REQUEST_DATA, mrProcess.getMrData(mrBean, personnelBean));
			}
		}
		else if (action.equals("submit")) {
			if (log.isDebugEnabled()) {
				log.debug("here in submit:" + inputBean.getRequestId());
			}
			Collection tabBeans = BeanHandler.getBeans((DynaBean) form, "catalogAddItemBean", new CatalogAddItemBean(), getTcmISLocale(request));
			String errorMsg = process.submitEval(inputBean, tabBeans, personnelBean);
			log.debug(errorMsg);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}
			BigDecimal requestId = inputBean.getRequestId();
			if (requestId != null) {
				CatAddHeaderViewBean catAddHeaderViewBean = process.getEngEvalData(requestId, personnelBean);
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN, catAddHeaderViewBean);

				MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
				mrBean.setPrNumber(catAddHeaderViewBean.getPrNumber());
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request), requestURL.substring(0, requestURL.lastIndexOf("/")));
				mrProcess.setCallFromEval(true);
				mrProcess.setCatAddHeaderViewBean(catAddHeaderViewBean);
				request.setAttribute(MATERIAL_REQUEST_DATA, mrProcess.getMrData(mrBean, personnelBean));
			}
		}
		else if (action.equals("save")) {
			if (log.isDebugEnabled()) {
				log.debug("here in save:" + inputBean.getRequestId() + "-" + inputBean.getNotes() + "-" + request.getParameter("notes"));
			}
			Collection tabBeans = BeanHandler.getBeans((DynaBean) form, "catalogAddItemBean", new CatalogAddItemBean(), getTcmISLocale(request));
			process.saveEval(inputBean, tabBeans, personnelBean);
			BigDecimal requestId = inputBean.getRequestId();
			if (requestId != null) {
				CatAddHeaderViewBean catAddHeaderViewBean = process.getEngEvalData(requestId, personnelBean);
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN, catAddHeaderViewBean);

				MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
				mrBean.setPrNumber(catAddHeaderViewBean.getPrNumber());
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request), requestURL.substring(0, requestURL.lastIndexOf("/")));
				mrProcess.setCallFromEval(true);
				mrProcess.setCatAddHeaderViewBean(catAddHeaderViewBean);
				request.setAttribute(MATERIAL_REQUEST_DATA, mrProcess.getMrData(mrBean, personnelBean));
			}
		}
		else if (action.equals("delete")) {
			if (log.isDebugEnabled()) {
				log.debug("here in delete:" + inputBean.getRequestId());
			}
			CatAddHeaderViewBean catAddHeaderViewBean = new CatAddHeaderViewBean();
			catAddHeaderViewBean.setSizeUnitViewColl(new Vector(0));
			request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN, catAddHeaderViewBean);
			if (!process.deleteRequest(inputBean)) {
				return mapping.findForward("genericerrorpage");
			}
		}
		else if (action.equals("approve")) {
			if (log.isDebugEnabled()) {
				log.debug("here in approve:" + inputBean.getRequestId());
			} //first save data
			Collection tabBeans = BeanHandler.getBeans((DynaBean) form, "catalogAddItemBean", new CatalogAddItemBean(), getTcmISLocale(request));
			process.saveEval(inputBean, tabBeans, personnelBean);
			//approval roles
			Collection approvalRoleBeans = BeanHandler.getBeans((DynaBean) form, "approvalRoleDataInnerDiv", new CatAddStatusViewBean(), this.getTcmISLocale(request));
			String errorMsg = process.approvalRequest(inputBean, approvalRoleBeans, personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}

			BigDecimal requestId = inputBean.getRequestId();
			if (requestId != null) {
				CatAddHeaderViewBean catAddHeaderViewBean = process.getEngEvalData(requestId, personnelBean);
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN, catAddHeaderViewBean);

				MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
				mrBean.setPrNumber(catAddHeaderViewBean.getPrNumber());
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request), requestURL.substring(0, requestURL.lastIndexOf("/")));
				mrProcess.setCallFromEval(true);
				mrProcess.setCatAddHeaderViewBean(catAddHeaderViewBean);
				request.setAttribute(MATERIAL_REQUEST_DATA, mrProcess.getMrData(mrBean, personnelBean));
			}
		}
		else if (action.equals("reject")) {
			if (log.isDebugEnabled()) {
				log.debug("here in reject:" + inputBean.getRequestId());
			} //approval roles
			Collection approvalRoleBeans = BeanHandler.getBeans((DynaBean) form, "approvalRoleDataInnerDiv", new CatAddStatusViewBean(), this.getTcmISLocale(request));
			String errorMsg = process.rejectRequest(inputBean, approvalRoleBeans, personnelBean);
			if (!"OK".equalsIgnoreCase(errorMsg)) {
				request.setAttribute(TCMIS_ERROR, errorMsg);
			}

			BigDecimal requestId = inputBean.getRequestId();
			if (requestId != null) {
				CatAddHeaderViewBean catAddHeaderViewBean = process.getEngEvalData(requestId, personnelBean);
				request.setAttribute(CAT_ADD_HEADER_VIEW_BEAN, catAddHeaderViewBean);

				MaterialRequestInputBean mrBean = new MaterialRequestInputBean();
				mrBean.setPrNumber(catAddHeaderViewBean.getPrNumber());
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getDbUser(request), getTcmISLocaleString(request), requestURL.substring(0, requestURL.lastIndexOf("/")));
				mrProcess.setCallFromEval(true);
				mrProcess.setCatAddHeaderViewBean(catAddHeaderViewBean);
				request.setAttribute(MATERIAL_REQUEST_DATA, mrProcess.getMrData(mrBean, personnelBean));
			}
		}
		else if (action.equals("getPreviousOrderEngEval")) {
			if (log.isDebugEnabled()) {
				log.debug("here in getPreviousOrderEngEval:" + inputBean.getItemId() + "-" + inputBean.getEngEvalFacilityId() + "+" + inputBean.getEngEvalWorkArea());
			}
			request.setAttribute("beanCollection", process.getPreviousOrderedEval(inputBean, personnelId));
		}
		else if (action.equals("completedPreviousOrderEngEval")) {
			if (log.isDebugEnabled()) {
				log.debug("here in completedPreviousOrderEngEval:" + inputBean.getItemId() + "-" + inputBean.getEngEvalFacilityId() + "+" + inputBean.getEngEvalWorkArea());
			}
			process.completedPreviousOrderedEval(inputBean);
			request.setAttribute("beanCollection", process.getPreviousOrderedEval(inputBean, personnelId));
		}
		else {/* set up search page data*/

		}
		return mapping.findForward("success");
	}
}
