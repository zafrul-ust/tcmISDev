package com.tcmis.internal.distribution.action;

import java.util.Collection;
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
import com.tcmis.internal.distribution.beans.InvoiceSearchBean;
import com.tcmis.internal.distribution.process.PrintInvoiceProcess;
import com.tcmis.internal.hub.beans.ShipConfirmInputBean;
import com.tcmis.internal.hub.process.ShipConfirmProcess;


  public class PrintInvoiceAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws BaseException, Exception {
    String source = "printinvoice";
  	if (!this.checkLoggedIn( source ) ) return mapping.findForward("login");
  	request.setAttribute("source", source );
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request,"personnelBean");
	if (!personnelBean.getPermissionBean().hasUserPagePermission("invoicePrint"))
	   return (mapping.findForward("nopermissions"));

	String forward = "success";
	//if form is not null we have to perform some action
	String action = null;
	if (form == null || (action = (String)((DynaBean) form).get("uAction")) == null ) {
		return (mapping.findForward(forward));
	}
	//if form is not null we have to perform some action
	ShipConfirmInputBean ib = new ShipConfirmInputBean();
	BeanHandler.copyAttributes(form, ib, getTcmISLocale(request));
	ShipConfirmProcess process = new ShipConfirmProcess(this.getDbUser(request),getTcmISLocaleString(request));
	PrintInvoiceProcess piProcess = new PrintInvoiceProcess(this.getDbUser(request),getTcmISLocaleString(request));
	String documentType = request.getParameter("documentType");
	String idField = request.getParameter("idField");

	if (action.equalsIgnoreCase("search")) {
		request.setAttribute("beanColl",piProcess.getSearchResults(personnelBean, documentType, idField, ib));
		
        this.saveTcmISToken(request);
	} else if (action.equalsIgnoreCase("createExcel")) {
		this.setExcel(response,"PrintInvoice");
		piProcess.getExcelReport(piProcess.getSearchResults(personnelBean, documentType, idField, ib)).write(response.getOutputStream());  	
        
		return noForward;
	} else if (action.equalsIgnoreCase("confirmNotAutoShipment")) {
		checkToken(request);
/*			java.util.Enumeration<String> e = request.getParameterNames();
		
		Vector<String> v = new Vector();
		while(e.hasMoreElements()) 
			v.add(e.nextElement());
		Collections.sort(v);
		for(String ss:v) {
			System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
		}
		String confirmedShipments = process.confirmNotAutoShipment(bean, BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected"));
		request.setAttribute("shipmentBeanCollection",process.getShipment(bean,confirmedShipments));
		request.setAttribute("confirmed","confirmed");
		forward = "confirmshipment";
			
*/	} else if (action.equalsIgnoreCase("printInvoice")) {
        checkToken(request);

//        String confirmedShipments = process.generateInvoice(BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected"));
	//	request.setAttribute("shipmentBeanCollection",process.getShipment(bean,confirmedShipments));
		forward = "confirmshipment";
  //      request.setAttribute("confirmedShipments",confirmedShipments);           
		return (mapping.findForward("confirmshipment"));
	} else if (action.equalsIgnoreCase("printBolLong")
            || action.equalsIgnoreCase("printBolShort")
            || action.equalsIgnoreCase("printBoxLabels")) {
    //  Collection printColl = BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected");
      //this.setSessionObject(request, process.bolPrintData(printColl),"PRINTDATA");
      this.setSessionObject(request, "","PICKLIST_ID");
      //this.setSessionObject(request, picklistIdParam,"PICKLIST_ID");
      request.setAttribute("action", action);
      request.setAttribute("source", "shipconfirm");
      forward = "printbol";
	} 
    else if (action.equalsIgnoreCase("printPackingList")) {
		log.debug("print packing list");
	} 
    else if(action.equalsIgnoreCase("regenEInvoice"))
    {
		checkToken(request);
    	Collection<InvoiceSearchBean> beans =  BeanHandler.getBeans((DynaBean) form, "InvoiceSearchBean", new InvoiceSearchBean(), getTcmISLocale(),"selected");
		request.setAttribute("tcmISErrors", piProcess.regenEInvoice(beans));
		request.setAttribute("beanColl",piProcess.getSearchResults(personnelBean, documentType, idField, ib));
		
        this.saveTcmISToken(request);
    }
    else if(action.equalsIgnoreCase("showEInvoiceHistory"))
    {
		request.setAttribute("beanColl",piProcess.getEInvoiceHistory(request.getParameter("invoice")));
		this.saveTcmISToken(request);
		forward = "showEHistory";
    }
	return (mapping.findForward(forward));
  }
}