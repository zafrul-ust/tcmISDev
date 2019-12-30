package com.tcmis.internal.hub.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.VvDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.AllocationInputBean;
import com.tcmis.internal.hub.beans.SplitReceiptBean;
import com.tcmis.internal.hub.beans.VvHubBinsBean;
import com.tcmis.internal.hub.beans.VvHubRoomBean;
import com.tcmis.internal.hub.process.BinLabelsProcess;
import com.tcmis.internal.hub.process.BinsToScanProcess;
import com.tcmis.internal.hub.process.ReceivingProcess;
import com.tcmis.internal.hub.process.SplitReceiptProcess;

public class SplitReceiptAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request,true)) {
			request.setAttribute("requestedPage", "splitreceipt");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		String userAction = request.getParameter("uAction");
		//String action = (String) ( (DynaBean)form).get("action");
		//String sourceHubName = (String) ( (DynaBean)form).get("sourceHubName");
		PersonnelBean pBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

//		if (!personnelBean.getPermissionBean().hasUserPagePermission("binLabels") )
//		{
//			return (mapping.findForward("nopermissions"));
//		}

		SplitReceiptBean in = new SplitReceiptBean();
        BeanHandler.copyAttributes(form, in,this.getTcmISLocale());
        SplitReceiptProcess process = new SplitReceiptProcess(this);
        if (userAction == null)
			userAction = "";
		if ("split".equalsIgnoreCase((String)((DynaBean)form).get("uAction"))) {
				Object[] objs = process.splitReceipt(in, pBean);
// hard coding for test.				
//				objs[0] = "1126389";
//				objs[1] = "";
				request.setAttribute("done", "done");
				request.setAttribute("receiptId", objs[0]);
				request.setAttribute("errmsg", objs[1]);
		}
		else {
			request.setAttribute("coll", process.search(in));
			request.setAttribute("bins", process.searchbins(in));
		}
		return mapping.findForward("success");
	}
} //end of class