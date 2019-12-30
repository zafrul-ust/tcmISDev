package com.tcmis.client.catalog.action;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tcmis.client.catalog.beans.ClientLabelBean;
import com.tcmis.client.catalog.beans.ClientLabelInputBean;
import com.tcmis.client.catalog.process.ClientLabelProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;

public class ClientLabelAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws BaseException, Exception {

	if (!this.isLoggedIn(request)) {
	 request.setAttribute("requestedPage", "showlabel");
	 request.setAttribute("requestedURLWithParameters",
										 this.getRequestedURLWithParameters(request));
	 return (mapping.findForward("login"));
	}

	if ( ( (DynaBean) form).get("buttonPrint") != null &&
	 ( (String) ( (DynaBean) form).get("buttonPrint")).length() > 0) {
	 //log.info("Here Print the labels to ZPL");
	 ClientLabelInputBean clientLabelInputBean = new ClientLabelInputBean();
	 BeanHandler.copyAttributes(form, clientLabelInputBean);

	 String[] catPartNoArray = clientLabelInputBean.getCatPartNo();
	 String[] partDescriptionArray = clientLabelInputBean.getPartDescription();
	 String[] shelfLifeArray = clientLabelInputBean.getShelfLife();
	 String[] customerPoArray = clientLabelInputBean.getCustomerPo();
	 String[] expirationDateArray = clientLabelInputBean.getExpirationDate();
	 String[] recertExpDateArray = clientLabelInputBean.getRecertExpDate();
	 String[] employeeNumArray = clientLabelInputBean.getEmployeeNum();
	 String[] labelQtyArray = clientLabelInputBean.getLabelQty();

	 Vector v = new Vector(catPartNoArray.length);
	 //log.info("Here in print Label " + catPartNoArray.length + " Array Length  " + catPartNoArray.length + "");
	 for (int i = 0; i < catPartNoArray.length; i++) {
		if (labelQtyArray[i].length() > 0) {
		 ClientLabelBean clientLabelBean = new ClientLabelBean();
		 clientLabelBean.setCatPartNo(catPartNoArray[i]);
		 clientLabelBean.setPartDescription(partDescriptionArray[i]);
		 clientLabelBean.setShelfLife(shelfLifeArray[i]);
		 clientLabelBean.setCustomerPo(customerPoArray[i]);
		 clientLabelBean.setExpirationDate(expirationDateArray[i]);
		 clientLabelBean.setRecertExpDate(recertExpDateArray[i]);
		 clientLabelBean.setEmployeeNum(employeeNumArray[i]);
		 clientLabelBean.setLabelQty(labelQtyArray[i]);

		 v.add(clientLabelBean);
		}
	 }

	 ClientLabelProcess clientLabelProcess = new ClientLabelProcess(this.
		getDbUser(request));
	 ResourceLibrary resource = new ResourceLibrary("report");

	 /*File dir = new File(resource.getString("excel.report.serverpath"));
			File file = File.createTempFile("zpl", ".txt", dir);
			clientLabelProcess.writeZplFile(clientLabelInputBean,
						file.getAbsolutePath());
			File jnlpfile = File.createTempFile("jnlp", ".jnlp", dir);
		 clientLabelProcess.writejnlpfile(jnlpfile.getAbsolutePath(),file.getName());
			this.setSessionObject(request,
					resource.getString("report.hosturl") +
					resource.getString("excel.report.urlpath") +
					jnlpfile.getName(), "filePath");
			request.setAttribute("doexcelpopup", "Yes");
			return mapping.findForward("success");*/String url = clientLabelProcess.
		load(v);

	 request.setAttribute("filePath",url);
	 //this.setSessionObject(request, url, "filePath");
	 return mapping.findForward("viewfile");
	}
	else {
	 return mapping.findForward("success");
	}
 } //end of method
} //end of class