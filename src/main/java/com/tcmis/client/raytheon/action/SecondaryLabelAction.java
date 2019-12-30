package com.tcmis.client.raytheon.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.raytheon.beans.CatPartHazardViewBean;
import com.tcmis.client.raytheon.process.SecondaryLabelProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;

public class SecondaryLabelAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {
	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "label");
	 //This is to save any parameters passed in the URL, so that they
	 //can be acccesed after the login
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if (form != null) {
	 DynaBean dynaForm = (DynaBean) form;
	 List catPartHazardViewBeans = (List) dynaForm.get(
		"catPartHazardViewBean");
	 Iterator iterator = catPartHazardViewBeans.iterator();
	 int deleteCount = 0;
   String inventoryGroup = "";
   Collection catPartHazardViewBeanCollection = new Vector();
	 while (iterator.hasNext()) {
		org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.
		 commons.beanutils.LazyDynaBean) iterator.next();
		CatPartHazardViewBean catPartHazardViewBean = new
		 CatPartHazardViewBean();
		BeanHandler.copyAttributes(lazyBean, catPartHazardViewBean);
		if (catPartHazardViewBean.getOk() != null) {
		 //log.debug("rowid     " + catPartHazardViewBean.getOk() + "");
		 deleteCount++;
		 catPartHazardViewBeanCollection.add(catPartHazardViewBean);
     inventoryGroup = catPartHazardViewBean.getInventoryGroup();
    }
	 }

	 SecondaryLabelProcess secondaryLabelProcess = new SecondaryLabelProcess(this.
		getDbUser(request));
	 if ( ( (DynaBean) form).get("action") != null &&
		( (String) ( (DynaBean) form).get("action")).equalsIgnoreCase("printLabels")) {
		request.setAttribute("catPartHazardViewBeanCollection",
		 secondaryLabelProcess.getLabelData(catPartHazardViewBeanCollection));
    request.setAttribute("inventoryGroup",inventoryGroup);     
    return (mapping.findForward("success"));
	 }
	 else if ( ( (DynaBean) form).get("buttonPrint") != null &&
		( (String) ( (DynaBean) form).get("buttonPrint")).length() > 0) {

		//log.info("Here Print the labels to ZPL");
		LabelInputBean labelInputBean = new LabelInputBean();
		BeanHandler.copyAttributes(form, labelInputBean);
		//log.info("PaperSize  " + labelInputBean.getPaperSize() + " printerPath  " +		 labelInputBean.getPrinterPath() + "");

		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
		 "personnelBean");

		if (personnelBean.getPrinterLocation() != null &&
		 personnelBean.getPrinterLocation().length() > 0) {

		 String labelUrl = secondaryLabelProcess.buildSecondaryLabels(personnelBean,
			labelInputBean, catPartHazardViewBeanCollection);
		 if (labelUrl.length() > 0) {
			request.setAttribute("filePath", labelUrl);
			//Update Receipt Table upon printing label with the last print date and pickable='y'
			return mapping.findForward("printlabels");
		 }
		 else {
			return mapping.findForward("systemerrorpage");
		 }
		}
		else {
		 //No ZPL printer to print to
		 return mapping.findForward("pagenotavailable");
		}
}
}
	else {
	 return mapping.findForward("success");
}
	return mapping.findForward("success");
 } //end of method
} //end of class