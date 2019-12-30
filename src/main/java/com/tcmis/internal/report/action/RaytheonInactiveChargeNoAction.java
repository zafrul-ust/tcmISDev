package com.tcmis.internal.report.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
import com.tcmis.internal.distribution.process.SpecListProcess;
import com.tcmis.internal.hub.beans.LogisticsInputBean;

/******************************************************************************
 * Controller for CustomerAddressSearch
 * @version 1.0
 ******************************************************************************/
public class RaytheonInactiveChargeNoAction
extends TcmISBaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws
			BaseException, Exception {

		//  login
		if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "raytheoninactivechargeno");
			request.setAttribute("requestedURLWithParameters",
					this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}

		//  main
		if( form == null )
			      	return (mapping.findForward("success"));
		String uAction = (String) ( (DynaBean)form).get("uAction");

		//  result
		//		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		boolean inactiveChargeNumber = true;
		String inaParm = request.getParameter("inactiveChargeNumber");
		if( !"Y".equals(inaParm ) ) {
//			if( "Y".equals(request.getParameter("invalidDirectedCharge") ) )
//				return mapping.findForward("success");
			inactiveChargeNumber = false; //means invalidDirectedCharge
		}
		GenericProcess process = new GenericProcess(this);
		if ( "createExcel".equals(uAction)) {
			try {
				String title = "Raytheon Inactive Charge Number Report";
				if( !inactiveChargeNumber )
					title = "Raytheon Inactive Charge Number Report";
				setExcel(response, "Raytheon Inactive Charge Number Report");
				getExcel(process,inactiveChargeNumber).write(response.getOutputStream());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return mapping.findForward("genericerrorpage");
			}
			return noForward;
		}
		{
//			Object[] dataAll = process.getSearchResultObjects("select * from customer.raytheon_inactive_charge_no");
//			request.setAttribute("startSearchTime", new Date().getTime() );
			request.setAttribute("dataColl", getData(process,inactiveChargeNumber));
//			request.setAttribute("endSearchTime", new Date().getTime() ); 
			//			return mapping.findForward("showDetails");
		}
		return mapping.findForward("success");
	}
	Object getData(GenericProcess process,boolean inactiveChargeNumber) throws Exception{
		//Raytheon_invalid_dir_chg 
		String qry = "select * from raytheon_inactive_charge_no";//Raytheon_invalid_dir_chg 
		if( !inactiveChargeNumber )
			qry = "select a.CHARGE_NUMBER_1, a.TRADING_PARTNER, a.FACILITY_ID,a.ACCOUNT_SYS_ID," +
					"a.CHARGE_TYPE,b.APPLICATION_DESC as application,a.BANNED,a.PERCENT,a.CHARGE_NUMBER_2," +
					"a.COMMODITY_TYPE,a.CAT_PART_NO,a.PART_GROUP_NO from invalid_Directed_Charge a," +
					"(select distinct facility_id,application, application_desc from fac_loc_app where company_id = 'RAYTHEON') b" +
					" where a.application = b.application and a.facility_id = b.facility_id";
		Object[] dataAll = process.getSearchResultObjects(qry);
		return dataAll[2];
	}
	public ExcelHandler getExcel(GenericProcess process,boolean inactiveChargeNumber) throws
	NoDataException, BaseException, Exception {

		Vector<Object[]> data = (Vector<Object[]>)getData(process,inactiveChargeNumber);
		ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getTcmISLocale());
		ExcelHandler pw = new ExcelHandler(res);
		pw.addTable();
		String[] headerkeys = {
				"label.inactivedate","label.chargeid","label.tradingpartner","label.chargenumber","label.ponumber",
				"label.poline","label.radianpo","label.mrnumber","label.lineitem","label.status","label.facilityid","label.deliverytype",
				"label.releasedate"};
//			  columnName:'<fmt:message key="label.chargenumber1"/> <fmt:message key="transactions.source"/>'
//			  columnName:'<fmt:message key="label.chargenumber2"/> <fmt:message key="transactions.source"/>'
		String[] headerkeys2 = {
				"label.chargenumber1","label.tradingpartner","label.facilityid","label.accountsysid","label.chargetype",
				"label.application","label.banned","label.percent","label.chargenumber2","label.commodityType",
				"label.catpartno","label.partgroupnumber"};
		if( !inactiveChargeNumber )
			headerkeys = headerkeys2;
		int[] types = {
				 pw.TYPE_CALENDAR,0,0,0,0,
				 0,0,0,0,0,
				 0,0,pw.TYPE_CALENDAR
			};
		int[] types2 = {
				 0,0,0,0,0,
				 0,0,0,0,0,
				 0,0
			};
		if( !inactiveChargeNumber )
			types = types2;
				
		pw.setColumnHeader(headerkeys,types);
//		if( !inactiveChargeNumber ) {
//			// Larry note: I didn't want to create extra resource in common resource.
//			pw.setColumnHeader(9,res.getString("label.chargenumber1")+" "+res.getString("transactions.source"));
//			pw.setColumnHeader(10,res.getString("label.chargenumber2")+" "+res.getString("transactions.source"));
//		}
		pw.applyColumnHeader();

		for (Object[] member : data) {
			pw.addRow();
			for(Object o:member)
				pw.addCell(o);
		}
		return pw;
	}
}
