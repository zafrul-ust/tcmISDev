package com.tcmis.internal.hub.action;

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
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.process.LabelProcess;
import com.tcmis.internal.hub.process.ZplProcess;

public class ReceiptLabelAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request,true)) {
	  request.setAttribute("requestedPage", "printcontainerlabels");
		request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	  return (mapping.findForward("login"));
	}

	LabelInputBean labelInputBean = new LabelInputBean();
	BeanHandler.copyAttributes(form, labelInputBean);
	//log.info("labelReceipts  "+labelInputBean.getLabelReceipts()+" printerPath  "+labelInputBean.getPrinterPath()+"");
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
	LabelProcess labelProcess = new LabelProcess(this.getDbUser(request),getTcmISLocaleString(request));

	if (labelInputBean.getLabelReceipts().length() > 0) {
	 Collection locationLabelPrinterCollection = new Vector();
	 if (personnelBean.getPrinterLocation() != null &&
		personnelBean.getPrinterLocation().length() > 0 &&
		!"811".equalsIgnoreCase(labelInputBean.getPaperSize())) {
		//ZplProcess zplProcess = new ZplProcess(this.getDbUser(request));
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getDbUser(request));
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
		if (locationLabelPrinterCollection!=null && locationLabelPrinterCollection.size() > 1)
		{
		 request.setAttribute("labelReceipts", labelInputBean.getLabelReceipts());
		 request.setAttribute("paperSize", labelInputBean.getPaperSize());
		 request.setAttribute("skipKitLabels", labelInputBean.getSkipKitLabels());
		 request.setAttribute("binLabels", labelInputBean.getBinLabels());
		 request.setAttribute("hubNumber", labelInputBean.getHubNumber());
		 if ("Yes".equalsIgnoreCase(labelInputBean.getBinLabels()))
		 {
			request.setAttribute("sourcePage", "printcontainerlabels");
		 }
		 else
		 {
			request.setAttribute("sourcePage", "printbinlabels");
		 }
		 request.setAttribute("locationLabelPrinterCollection",
			locationLabelPrinterCollection);
		 return mapping.findForward("printerchoice");
		}
	 }
	 else
	 {
		//Build PDF Labels here
		return mapping.findForward("pagenotavailable");
	 }

	 String labelUrl = labelProcess.buildReceiptLabels(personnelBean,labelInputBean,locationLabelPrinterCollection);
	 if (labelUrl.length() > 0)
	 {
			 //this.setSessionObject(request, labelUrl, "filePath");
       request.setAttribute("filePath", labelUrl);
       if (!(labelInputBean.getPaperSize() != null && labelInputBean.getPaperSize().equalsIgnoreCase("receiptDocument")))
       {
       //Update Receipt Table upon printing label with the last print date and pickable='y'
			 labelProcess.updateReceiptPickableStatus(labelInputBean.getLabelReceipts(),
				"Y", labelInputBean.getPaperSize());
       }
             request.setAttribute("doexcelpopup", "Yes");
			 return mapping.findForward("viewfile");
	 }
	 else
	 {
		return mapping.findForward("systemerrorpage");
	 }
	}
	else {
	 return mapping.findForward("systemerrorpage");
	}
 }
} //end of class