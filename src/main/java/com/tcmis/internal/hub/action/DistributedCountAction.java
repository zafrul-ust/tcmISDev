package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.internal.hub.beans.DistributedCountUsageInputBean;
import com.tcmis.internal.hub.beans.DistributedCountUsageViewBean;
import com.tcmis.internal.hub.process.DistributedCountProcess;
import com.tcmis.internal.hub.process.ShipmentHistoryProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for receiving
 * @version 1.0
	******************************************************************************/
public class DistributedCountAction
 extends TcmISBaseAction {

 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "distributedcountmain");
	 request.setAttribute("requestedURLWithParameters",
		this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}
	
	PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,
	 "personnelBean");
	
    if (!personnelBean.getPermissionBean().hasUserPagePermission("distributedCount"))
    {
      return (mapping.findForward("nopermissions"));
    }


	if (form != null) {
	 //copy date from dyna form to the data form
	 DistributedCountUsageInputBean inputBean = new DistributedCountUsageInputBean();
	 BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));

	 if (inputBean.getSubmitUpdate() != null || inputBean.getSubmitSave() != null) {
		if (!this.isTcmISTokenValid(request, true)) {
		 BaseException be = new BaseException("Duplicate form submission");
		 be.setMessageKey("error.submit.duplicate");
		 this.saveTcmISToken(request);
		 throw be;
		}
		this.saveTcmISToken(request);
	 }

 //populate drop downs

	Collection hubInventoryGroupOvBeanCollection = personnelBean.getHubInventoryGroupOvBeanCollection();
	BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

	DistributedCountProcess distributedCountProcess = new
	 DistributedCountProcess(this.getDbUser(request),getTcmISLocaleString(request));

	 /*else if (inputBean.getButtonCreateExcel() != null &&
		inputBean.getButtonCreateExcel().trim().length() > 0) {
		Collection savedSearchCollection = (Collection)this.getSessionObject(
		 request, "distributedCountUsageViewBeanCollection");

		String url = distributedCountProcess.writeExcelFile(savedSearchCollection);
		this.setSessionObject(request, url, "filePath");
		return mapping.findForward("viewfile");
	 }*/
	 if ((inputBean.getSubmitUpdate() != null &&
		inputBean.getSubmitUpdate().trim().length() > 0) || (inputBean.getSubmitSave() != null &&
		inputBean.getSubmitSave().trim().length() > 0) ) {

		Vector savedBeanVector = new Vector( (Collection)this.getSessionObject(
		 request, "distributedCountUsageViewBeanCollection"));

		// Cast form to DynaBean
		DynaBean dynaForm = (DynaBean) form;
		List distributedCountUsageViewBeans = (List) dynaForm.get(
		 "distributedCountUsageViewBean");
		Collection distributedCountUsageViewBeanInputCollection = new Vector();
		if (distributedCountUsageViewBeans !=null)
		{
		 Iterator iterator = distributedCountUsageViewBeans.iterator();

		 while (iterator.hasNext()) {
			org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.
			 beanutils.LazyDynaBean) iterator.next();

			DistributedCountUsageViewBean distributedCountUsageViewBean = new
			 DistributedCountUsageViewBean();
			BeanHandler.copyAttributes(lazyBean, distributedCountUsageViewBean, getTcmISLocale(request));
			distributedCountUsageViewBeanInputCollection.add(distributedCountUsageViewBean);
		 }
		}

		distributedCountUsageViewBeanInputCollection = distributedCountProcess.
		 copyAttributes(distributedCountUsageViewBeanInputCollection, savedBeanVector);

		Collection c = new Vector();
		c = distributedCountProcess.createRelationalObject( distributedCountUsageViewBeanInputCollection );

		//now I can pass the collection of true data beans to the process
		String errorMessage = "";
		errorMessage = distributedCountProcess.updateCount(inputBean,c, personnelId);

		request.setAttribute("errorMessage", errorMessage);
		Collection searchCollection = distributedCountProcess.getsearchResult(inputBean,hubInventoryGroupOvBeanCollection);

		this.setSessionObject(request, searchCollection, "distributedCountUsageViewBeanCollection");
		request.setAttribute("distributedCountUsageViewRelationBeanCollection",
		 distributedCountProcess.createRelationalObject(searchCollection));

		return (mapping.findForward("showresults"));
	 }
	 else if (inputBean.getSubmitSearch() != null &&
		inputBean.getSubmitSearch().trim().length() > 0) {
		Collection c = distributedCountProcess.getsearchResult(inputBean,hubInventoryGroupOvBeanCollection);

		this.setSessionObject(request, c, "distributedCountUsageViewBeanCollection");
		request.setAttribute("distributedCountUsageViewRelationBeanCollection",
		 distributedCountProcess.createRelationalObject(c));
		this.saveTcmISToken(request);
		return (mapping.findForward("showresults"));
	 }

	 this.setSessionObject(request, null, "distributedCountUsageViewBeanCollection");
	 return (mapping.findForward("success"));
	}

	return mapping.findForward("success");
 }
}