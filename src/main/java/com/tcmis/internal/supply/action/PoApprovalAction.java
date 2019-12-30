package com.tcmis.internal.supply.action;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PoApprovalChainViewBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.supply.beans.PoApprovalActionInputBean;
import com.tcmis.internal.supply.beans.PoApprovalInputBean;
import com.tcmis.internal.supply.beans.PoApprovalViewBean;
import com.tcmis.internal.supply.process.PoApprovalProcess;

public class PoApprovalAction extends TcmISBaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward forward = mapping.findForward("success");
		
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "poapprovalmain");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
		PoApprovalProcess process = new PoApprovalProcess(this.getDbUser(request));
		request.setAttribute("buyerBeanCollection", process.getBuyerDropDown());
		PoApprovalInputBean input = new PoApprovalInputBean();
		BeanHandler.copyAttributes((DynaBean) form, input, getTcmISLocale(request));
		
		try {
			if (input.isSearch()) {
				request.setAttribute("poApprovalColl", process.getSearchResult(input, personnelBean));
			}
			else if (input.isShowApprovalChain()) {
				PoApprovalViewBean bean = new PoApprovalViewBean();
				BeanHandler.copyAttributes((DynaBean) form, bean, getTcmISLocale(request));
				Collection<PoApprovalChainViewBean> approvalChain = process.getPoApprovalChain(bean);
				JSONObject results = new JSONObject();
				JSONArray resultArray = new JSONArray();
				for (PoApprovalChainViewBean approvalStep : approvalChain) {
					JSONObject resultStep = new JSONObject();
					resultStep.put("approverLimit", approvalStep.isApproverLimitUnlimited()?"Unlimited":NumberFormat.getInstance(getTcmISLocale()).format(approvalStep.getApproverLimit().intValue()));
					resultStep.put("approverName", approvalStep.getApproverName());
					resultStep.put("approvalAction", approvalStep.getActionTaken());
					resultArray.put(resultStep);
				}
				results.put("approvalChain", resultArray);
				
				PrintWriter out = response.getWriter();
				out.write(results.toString(3));
				out.close();
				
				forward = noForward;
			}
			else if (input.isSelectRejectionCode()) {
				request.setAttribute("poApprovalCodes", process.getPoApprovalCodes(input));
			}
			else if (input.isApproveAllPos()) {
				Collection<PoApprovalViewBean> approvals = BeanHandler.getBeans((DynaBean) form, "poApprovalData", new PoApprovalViewBean(), getTcmISLocale(request), "grab");
				process.approveAll(approvals, personnelBean);
				request.setAttribute("approvalSuccess", "Y");
				request.setAttribute("poApprovalColl", process.getSearchResult(input, personnelBean));
			}
			else if (input.isRejectPo()) {
				PoApprovalActionInputBean bean = new PoApprovalActionInputBean();
				BeanHandler.copyAttributes((DynaBean) form, bean, getTcmISLocale(request));
				process.rejectPo(bean, personnelBean);
				request.setAttribute("rejectionSuccess", "Y");
			}
			else if (input.isCreateExcel()) {
				this.setExcel(response, "PoApproval");
				process.getExcelReport(input, personnelBean, getTcmISLocale(request)).write(response.getOutputStream());
				log.debug("CREATE EXCEL COMPLETED");
				forward = noForward;
			}
		}
		catch (BaseException e) {
			request.setAttribute("tcmisError", e.getMessage());
            log.error(e);
		}
		
		return forward;
	}
}