package com.tcmis.internal.hub.action;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.ShipConfirmInputBean;
import com.tcmis.internal.hub.process.ShipConfirmProcess;
import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * ***************************************************************************
 * Test Case: Boston Hub, Dallas, Miami Hub have different functions. 
 * Make sure to test them all.
 * 
 * ****************************************************************************
 */
public class ShipConfirmAction extends TcmISBaseAction {

	public ActionForward executeAction(ActionMapping mapping,
																		 ActionForm form,
																		 HttpServletRequest request,
																		 HttpServletResponse response) throws
			BaseException, Exception {
		if (!this.checkLoggedIn("shipconfirmmain") ) 
			return (mapping.findForward("login"));

	    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request,"personnelBean");
	    
		if (!personnelBean.getPermissionBean().hasUserPagePermission("shipConfirm"))
		   return (mapping.findForward("nopermissions"));

		String forward = "success";
		//if form is not null we have to perform some action
		String action = null;
		if (form == null || (action = (String)((DynaBean) form).get("uAction")) == null ) {
			return (mapping.findForward(forward));
		}
		//if form is not null we have to perform some action
		ShipConfirmInputBean bean = new ShipConfirmInputBean();
        BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
        bean.setPersonnelId(new BigDecimal(""+personnelBean.getPersonnelId()+""));
        ShipConfirmProcess process = new ShipConfirmProcess(this.getDbUser(request),getTcmISLocaleString(request));

		if (action.equalsIgnoreCase("search")) {
			Object[] results = process.search(personnelBean, bean);
			request.setAttribute("beanColl",results[0]);
			request.setAttribute("shipmentIdColl",results[1]);
			request.setAttribute("prNumberColl",results[2]);
			request.setAttribute("showProForma", results[3]);
			
			this.saveTcmISToken(request);
		} else if (action.equalsIgnoreCase("createExcel")) {
			this.setExcel(response,"ShipConfirm");
			process.getExcelReport(personnelBean, bean, request.getLocale()).write(response.getOutputStream());
			return noForward;
		} else if (action.equalsIgnoreCase("confirmShipment")) {
			checkToken(request);
			Collection c = BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected");
			process.udpateProposedExportDate(c);
			process.confirmShipment(personnelBean,bean, c);
			
			Object[] results = process.search(personnelBean, bean);
			request.setAttribute("beanColl",results[0]);
			request.setAttribute("shipmentIdColl",results[1]);
			request.setAttribute("prNumberColl",results[2]);
			request.setAttribute("showProForma", results[3]);
			request.setAttribute("motIncotermCollection",process.getIncotermForModeOfTransport());
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
*/			String confirmedShipments = process.confirmNotAutoShipment(personnelBean,bean, BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected"));
			request.setAttribute("shipmentBeanCollection",process.getShipment(bean,confirmedShipments,personnelBean));
			request.setAttribute("motIncotermCollection",process.getIncotermForModeOfTransport());
			request.setAttribute("confirmed","confirmed");
			forward = "confirmshipment";
		} else if (action.equalsIgnoreCase("createShipment")) {
			checkToken(request);
			process.createShipment(BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected"));
			
			Object[] results = process.search(personnelBean, bean);
			request.setAttribute("beanColl",results[0]);
			request.setAttribute("shipmentIdColl",results[1]);
			request.setAttribute("prNumberColl",results[2]);
			request.setAttribute("showProForma", results[3]);
			
		} else if (action.equalsIgnoreCase("updateShipment")) {
			checkToken(request);
			process.updateShipment(BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected"));
			
			Object[] results = process.search(personnelBean, bean);
			request.setAttribute("beanColl",results[0]);
			request.setAttribute("shipmentIdColl",results[1]);
			request.setAttribute("prNumberColl",results[2]);
			request.setAttribute("showProForma", results[3]);
			
		} else if (action.equalsIgnoreCase("showconfirmshipment")) {
			log.debug("show confirm shipment");
/*			java.util.Enumeration<String> e = request.getParameterNames();
			
			Vector<String> v = new Vector();
			while(e.hasMoreElements()) 
				v.add(e.nextElement());
			Collections.sort(v);
			for(String ss:v) {
				System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
			}
*/			request.setAttribute("shipmentBeanCollection",process.getShipment(bean,"",personnelBean));
			request.setAttribute("motIncotermCollection",process.getIncotermForModeOfTransport());
			forward = "confirmshipment";
			this.saveTcmISToken(request);
		} else if (action.equalsIgnoreCase("printInvoice")) {
            checkToken(request);

            String confirmedShipments = process.generateInvoice(BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected"));
            String cmsConfirmedShipments = process.generateCmsInvoice(BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected"));
            
			request.setAttribute("shipmentBeanCollection",process.getShipment(bean,confirmedShipments,personnelBean));
			forward = "confirmshipment";
            request.setAttribute("confirmedShipments",confirmedShipments);
            request.setAttribute("cmsConfirmedShipments",cmsConfirmedShipments);
            request.setAttribute("motIncotermCollection",process.getIncotermForModeOfTransport());
			return (mapping.findForward("confirmshipment"));
		} else if (action.equalsIgnoreCase("printBolLong")
                || action.equalsIgnoreCase("printBolShort")
                || action.equalsIgnoreCase("printBoxLabels")) {
          Collection printColl = BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected");
          this.setSessionObject(request, process.bolPrintData(printColl),"PRINTDATA");
          this.setSessionObject(request, "","PICKLIST_ID");
          //this.setSessionObject(request, picklistIdParam,"PICKLIST_ID");
          request.setAttribute("action", action);
          request.setAttribute("source", "shipconfirm");
          forward = "printbol";
		} 
        else if (action.equalsIgnoreCase("showprintPL")) {		
        	request.setAttribute("printPLCollection",process.getPackingList(bean.getBranchPlant()));
        	forward = "printpl";
		} 
        else if (action.equalsIgnoreCase("showprintproforma")) {
			request.setAttribute("shipmentBeanCollection",process.getShipment(bean,"",personnelBean,false));
			request.setAttribute("motIncotermCollection",process.getIncotermForModeOfTransport());
			forward = "confirmshipment";
			this.saveTcmISToken(request);
		} else if (action.equalsIgnoreCase("printProForma")) {
            checkToken(request);

			Collection c = BeanHandler.getGridBeans((DynaBean)form, "shipConfirmInputBean", bean, getTcmISLocale(request),"selected");
			process.udpateShipmentBy(c);
            process.udpateProposedExportDate(c);           
            String selectedShipments = process.generateProForma(c);
           
            request.setAttribute("selectedShipments",selectedShipments);
			request.setAttribute("shipmentBeanCollection",process.getShipment(bean,selectedShipments,personnelBean,false));
            request.setAttribute("motIncotermCollection",process.getIncotermForModeOfTransport());
			forward = "confirmshipment";
		}
		return (mapping.findForward(forward));
	}
}
