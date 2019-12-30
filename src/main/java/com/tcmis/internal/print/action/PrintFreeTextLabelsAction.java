package com.tcmis.internal.print.action;

import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.process.HubLabelProcess;

public class PrintFreeTextLabelsAction
extends TcmISBaseAction {
	@Override
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "printfreetextlabels");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		LabelInputBean labelInputBean = new LabelInputBean();
		BeanHandler.copyAttributes(form, labelInputBean);
		if(log.isDebugEnabled()) {
			log.debug(labelInputBean);
		}
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
		HubLabelProcess hubLabelProcess = new HubLabelProcess(this.
				getDbUser(request));

		if (!StringHandler.isBlankString(labelInputBean.getLabelFreeText())) {
			Collection locationLabelPrinterCollection = new Vector();
			if (personnelBean.getPrinterLocation() != null &&
					personnelBean.getPrinterLocation().length() > 0 &&
					!"811".equalsIgnoreCase(labelInputBean.getPaperSize())) {
				//ZplProcess zplProcess = new ZplProcess(this.getDbUser(request));
				ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
				locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
				if (locationLabelPrinterCollection!=null && locationLabelPrinterCollection.size() > 1)
				{
					request.setAttribute("labelFreeText", labelInputBean.getLabelFreeText());
					request.setAttribute("paperSize", labelInputBean.getPaperSize());
					request.setAttribute("textFontSize", labelInputBean.getTextFontSize());

					request.setAttribute("locationLabelPrinterCollection",locationLabelPrinterCollection);
					return mapping.findForward("printerchoice");
				}
			}
			else
			{
				//Build PDF Labels here
				return mapping.findForward("pagenotavailable");
			}

			String labelUrl="";
			//String labelUrl = hubLabelProcess.buildReceiptLabels(personnelBean,labelInputBean,locationLabelPrinterCollection);
			if (labelUrl.length() > 0)
			{
				//this.setSessionObject(request, labelUrl, "filePath");
				request.setAttribute("filePath", labelUrl);
				return mapping.findForward("viewfile");
			}
			else
			{
				return mapping.findForward("systemerrorpage");
			}
		}

		return mapping.findForward("showinput");
	}
} //end of class