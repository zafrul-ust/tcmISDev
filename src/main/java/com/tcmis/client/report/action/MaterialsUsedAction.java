package com.tcmis.client.report.action;

import java.io.File;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

import java.util.Date;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.process.UseApprovalStatusProcess;
import com.tcmis.client.report.beans.MaterialsUsedInputBean;
import com.tcmis.client.report.process.MaterialsUsedProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

public class MaterialsUsedAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

// no login
	 if( form == null ) 
			return (mapping.findForward("success"));
	//if form is not null we have to perform some action
	 MaterialsUsedInputBean inputBean = new	MaterialsUsedInputBean();
	 BeanHandler.copyAttributes(form, inputBean);

	 if (inputBean.getSubmitSearch() != null &&
		inputBean.getSubmitSearch().trim().length() > 0) {

		MaterialsUsedProcess materialsUsedProcess = new
		MaterialsUsedProcess(this.getDbUser(request));

		Collection c = materialsUsedProcess.getSearchResult(inputBean);
		c = materialsUsedProcess.createRelationalObject(c);

		request.setAttribute("materialsUsedViewBeanCollection", c);
		return (mapping.findForward("success"));
	 }
     if (( (DynaBean) form).get("userAction") != null &&
			   ((String)( (DynaBean) form).get("userAction")).trim().equalsIgnoreCase("buttonCreateExcel")) {

		ResourceLibrary resource = new ResourceLibrary("report");

		MaterialsUsedProcess materialsUsedProcess = new MaterialsUsedProcess(this.getDbUser(request));
		Collection c = materialsUsedProcess.getSearchResult(inputBean);
		c = materialsUsedProcess.createRelationalObject(c);
		request.setAttribute("materialsUsedViewBeanCollection", c);

		File dir = new File(resource.getString("excel.report.serverpath"));
		File file = File.createTempFile("excel", ".xls", dir);
		materialsUsedProcess.writeExcelFile(inputBean,c,file.getAbsolutePath());
		this.setSessionObject(request,
				resource.getString("report.hosturl") +
				resource.getString("excel.report.urlpath") +
				file.getName(), "filePath");

		request.setAttribute("doexcelpopup", "Yes");
		return (mapping.findForward("viewfile"));
	 }
// serach
     else {
    	UseApprovalStatusProcess useApprovalStatusProcess = new
    	UseApprovalStatusProcess(this.getDbUser(request));
    	request.setAttribute("facAppUserGrpOvBeanColl",	useApprovalStatusProcess.getFacilityData());

 		Date defaultEndDate = new Date();
 		Calendar calendar = Calendar.getInstance();
 		calendar.set(Calendar.MONTH, 0);
 		calendar.set(Calendar.DATE, 1);
 		Date defaultStart = calendar.getTime();
 		request.setAttribute("defaultEndDate", defaultEndDate);
 		request.setAttribute("defaultStart", defaultStart);
 		return mapping.findForward("success");
 	 }
 	} //end of method
} //end of class