package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.VvLotStatusBean;
import com.tcmis.internal.hub.process.LogisticsProcess;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

/******************************************************************************
 * Controller for logistics
 * 
 * @version 1.0
 ******************************************************************************/
public class LogisticsAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "logisticsmain");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		PersonnelBean personnelBean = (PersonnelBean) getSessionObject(request, "personnelBean");
		
		// populate drop downs
		LogisticsProcess process = new LogisticsProcess(getDbUser(request), getTcmISLocale(request));
		
		if (form == null) {
			// needs lotstatus for both search and results.
			request.setAttribute("lotStatusColl", process.getLotStatusColl(personnelBean));
			return (mapping.findForward("success"));
		}

		String logonId = "" + personnelBean.getPersonnelId();

		LogisticsInputBean inputBean = new LogisticsInputBean();
		BeanHandler.copyAttributes(form, inputBean, getTcmISLocale());
		String[] lotStatus = request.getParameterValues("lotStatus");
		inputBean.setLotStatus(lotStatus);
		String action = (String) ((DynaBean) form).get("uAction"); //
		if ("loaddata".equals(action)) {
			request.setAttribute("bins", process.loadData(inputBean));
			return (mapping.findForward("loaddata"));
		}

		if ("update".equals(action)) {
			// Object[] objs = process.UpdateLine(inputBean);
			Vector hasError = new Vector();
			Vector<LogisticsViewBean> beans = (Vector<LogisticsViewBean>) BeanHandler.getGridBeans((DynaBean) form, "LogisticsBean", new LogisticsViewBean(), getTcmISLocale(request), "ok");
			BigDecimal prevReceipt = null;
			for (LogisticsViewBean bean : beans) {
				BigDecimal currReceipt = bean.getReceiptId();
				boolean result = process.UpdateLine(bean, logonId);
				if (!result) hasError.add(bean.getReceiptId());

				if ((prevReceipt == null || prevReceipt.compareTo(currReceipt) != 0)
						&& !StringHandler.isBlankString(bean.getReceivingStatus())
						&& ((!bean.getOldLotStatus().equalsIgnoreCase("available") && bean.getLotStatus().equalsIgnoreCase("available")) || (!bean.getOldLotStatus().equalsIgnoreCase("Client Review") && bean.getLotStatus().equalsIgnoreCase("Client Review")))) {
					ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(getDbUser(), getTcmISLocale());
					ReceivingInputBean receivingInputBean = new ReceivingInputBean();

					BeanHandler.copyAttributes(bean, receivingInputBean);
					receivingInputBean.setSourceHub(bean.getHub());
					receivingInputBean.setSort("Bin");
					receivingInputBean.setSourceHubName(bean.getHubName());
					receivingInputBean.setSearch(bean.getReceiptId().toPlainString());

					LogisticsQVRThread logisticsQVRThread = new LogisticsQVRThread(receivingQcCheckListProcess, receivingInputBean, personnelBean);
					logisticsQVRThread.start();
				}
				prevReceipt = bean.getReceiptId();
			}
			request.setAttribute("hasError", hasError);
		}
		else if (action.equals("createExcel")) {
			try {
				setExcel(response, "Logistics Excel Report");
				process.getExcelReport(inputBean, personnelBean).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		// if ( "search".equals(action) )
		{
			Object[] objs = process.getSearchResult(inputBean, personnelBean);
			request.setAttribute("pageNameViewBeanCollection", objs[0]);
			request.setAttribute("rowSpan", objs[1]);
			request.setAttribute("hasRowSpan", objs[2]);
			// Just need to be recompiled.
			if (null == getSessionObject(request, "qcInv")) {
				setSessionAttribute("qcInv", process.getQcInv(personnelBean));
			}

			// needs lotstatus for both search and results.
			request.setAttribute("lotStatusColl", process.getLotStatusColl(personnelBean));
		}

		return (mapping.findForward("success"));
	}
}
