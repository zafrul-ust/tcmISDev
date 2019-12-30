package com.tcmis.client.het.action;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.het.beans.HetContainerEntryViewBean;
import com.tcmis.client.het.beans.PrintNhpInputBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

public class PrintNhpAction extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

		Collection<HetContainerEntryViewBean> beans = BeanHandler.getBeans((DynaBean) form, "nhpBean", new HetContainerEntryViewBean(), getTcmISLocale(request));
		request.setAttribute("insertColl", beans);

		String action = request.getParameter("action");
		if ("print".equals(action)) {
			response.setHeader("Content-Disposition", "inline; filename=labels.txt");
			response.setContentType("text/plain");
			ServletOutputStream out = response.getOutputStream();
			out.print("^XA\n");
			out.print("^DFLMLabel^FS\n");
			out.print("^LH10,10\n");
			out.print("^FO10,10^A0N,50^FN1^FS\n");
			out.print("^FO10,70^A0N,25^FDPart:^FS\n");
			out.print("^FO65,70^A0N,25^FN2^FS\n");
			out.print("^FO10,100^A0N,25^FDTrade Name:^FS\n");
			out.print("^FO150,100^A0N,25^FN3^FS\n");
			out.print("^FO10,125^A0N,25^FDManufacturer:^FS\n");
			out.print("^FO160,125^A0N,25^FN4^FS\n");
			out.print("^FO400,10^A0N,25^FDMSDS:^FS\n");
			out.print("^FO470,10^A0N,25^FN5^FS\n");
			out.print("^FO400,35^A0N,25^FDExp Date:^FS\n");
			out.print("^FO500,35^A0N,25^FN6^FS\n");
			out.print("^FO400,60^A0N,25^FDOTR Flag:^FS\n");
			out.print("^FO510,60^A0N,25^FN7^FS\n");
			out.print("^XZ\n");
			out.print("\n");
			for (HetContainerEntryViewBean bean : beans) {
				out.print("^XA\n");
				out.print("^XFLMLabel^FS\n");
				out.print("^FN1^FD" + bean.getContainerId() + "^FS\n");
				out.print("^FN2^FD" + bean.getCatPartNo() + "^FS\n");
				out.print("^FN3^FD" + bean.getTradeName() + "^FS\n");
				out.print("^FN4^FD" + bean.getManufacturer() + "^FS\n");
				out.print("^FN5^FD" + bean.getCustMsdsNo() + "^FS\n");
				out.print("^FN6^FD" + bean.getFormattedExpirationDate() + "^FS\n");
				out.print("^FN7^FD" + (bean.isUsageLoggingRequired() ? "N" : "Y") + "^FS\n");
				out.print("^PQ1\n");
				out.print("^XZ\n");
				out.print("\n");
			}
			out.close();
			return noForward;
		}
		else {
			return (mapping.findForward("success"));
		}
	}

}
