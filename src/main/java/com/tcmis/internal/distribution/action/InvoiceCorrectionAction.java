package com.tcmis.internal.distribution.action;

import java.util.Collection;
import java.util.Vector;

import java.math.BigDecimal;

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
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.distribution.beans.CatalogItemAddChargeBean;
import com.tcmis.internal.distribution.beans.InvoiceCorrPrintHcViewBean;
import com.tcmis.internal.distribution.beans.InvoiceCorrPrintLcViewBean;
import com.tcmis.internal.distribution.beans.InvoiceCorrPrintLineViewBean;
import com.tcmis.internal.distribution.beans.InvoiceCorrPrintViewBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestInputBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRcptShpViewBean;
import com.tcmis.internal.distribution.beans.ItemUomBean;
import com.tcmis.internal.distribution.beans.MrAddChargeViewBean;
import com.tcmis.internal.distribution.beans.MrReturnChargeViewBean;
import com.tcmis.internal.distribution.beans.NewFeesItemsViewBean;
import com.tcmis.internal.distribution.beans.VvReturnReasonBean;
import com.tcmis.internal.distribution.process.InvoiceCorrectionProcess;

public class InvoiceCorrectionAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//Login
		String source = "invoicecorrection";
      	if (!this.checkLoggedIn( source ) ) return mapping.findForward("login");

		//Process search
		PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");

//		Not in page table....
//		if (!personnelBean.getPermissionBean().hasUserPagePermission("invoiceCorrection")) {
//	        return (mapping.findForward("nopermissions"));
//	    }

		InvoiceCorrectionProcess process = new InvoiceCorrectionProcess(this);
		//InvoiceCorrectionViewBean
		String action = request.getParameter("uAction");
		InvoiceCorrPrintViewBean bean = new InvoiceCorrPrintViewBean();
		BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));

		if ( "search".equals(action) ) {
//          If already been run once. can call roll back.			
			String err = process.initInvoiceCorr(bean,personnelBean);
			if( err == null || err.length() != 0 );
				request.setAttribute("err",err);
//			request.setAttribute("invoice",process.getSearchResult("select * from tcm_ops.invoice_corr_print_view where invoice = {0}", bean,bean.getInvoice() ) );
//			Collection lines = 
//				process.getSearchResult("select * from tcm_ops.invoice_corr_print_line_view where invoice = {0}", new InvoiceCorrPrintLineViewBean(),bean.getInvoice() );
//			request.setAttribute("invoiceColl", lines);
//			for(InvoiceCorrPrintLineViewBean b:(Vector<InvoiceCorrPrintLineViewBean>) lines){
//				b.setCharges(process.getSearchResult("select * from tcm_ops.invoice_corr_print_lc_view where invoice = {0} and line_item = {1}", new InvoiceCorrPrintLcViewBean(),bean.getInvoice(), b.getLineItem()) );
//			}
//			request.setAttribute("chargeColl",process.getSearchResult("select * from invoice_corr_print_hc_view where invoice = {0}",new InvoiceCorrPrintHcViewBean(), bean.getInvoice() ) );
		}
		if ( "approve".equals(action) ) {
//          If already been run once. can call roll back.	
//			public String setPrintDate(BigDecimal invoiceNum,PersonnelBean personnelBean,String justprint) throws Exception {
//			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			process.setPrintDate(bean.getInvoice(),personnelBean);
//			String err = process.initInvoiceCorr(bean,personnelBean);
//			request.setAttribute("invoice",process.getSearchResult("select * from tcm_ops.invoice_corr_print_view where invoice = {0}", bean,bean.getInvoice() ) );
//			Collection lines = 
//				process.getSearchResult("select * from tcm_ops.invoice_corr_print_line_view where invoice = {0}", new InvoiceCorrPrintLineViewBean(),bean.getInvoice() );
//			request.setAttribute("invoiceColl", lines);
//			for(InvoiceCorrPrintLineViewBean b:(Vector<InvoiceCorrPrintLineViewBean>) lines){
//				b.setCharges(process.getSearchResult("select * from tcm_ops.invoice_corr_print_lc_view where invoice = {0} and line_item = {1}", new InvoiceCorrPrintLcViewBean(),bean.getInvoice(), b.getLineItem()) );
//			}
//			request.setAttribute("chargeColl",process.getSearchResult("select * from invoice_corr_print_hc_view where invoice = {0}",new InvoiceCorrPrintHcViewBean(), bean.getInvoice() ) );
//			request.setAttribute("approved", "Y");
			request.setAttribute("done", "Y");
		}
		if ( "save".equals(action) ) {
// save is run from the main page.			
//          If already been run once. can call roll back.			
			//BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			Collection<InvoiceCorrPrintLineViewBean> invoiceBeans = BeanHandler.getBeans((DynaBean) form, "InvoiceCorrPrintLineViewBean", new InvoiceCorrPrintLineViewBean(), this.getTcmISLocale());
//			Collection<InvoiceCorrPrintHcViewBean> chargeBeans = BeanHandler.getBeans((DynaBean) form, "InvoiceCorrPrintHcViewBean", new InvoiceCorrPrintHcViewBean(), this.getTcmISLocale());
			for (InvoiceCorrPrintLineViewBean ib : invoiceBeans) {
				if (ib.getOrderedQuantity() != null ){
					process.updateMaterialLine(ib,bean);
				}
//				else {
//					process.updateLine(ib);
//				}
			}
			request.setAttribute("invoice",process.getSearchResult("select * from tcm_ops.invoice_corr_print_view where invoice = {0}", bean,bean.getInvoice() ) );
			Collection lines = 
				process.getSearchResult("select * from tcm_ops.invoice_corr_print_line_view where invoice = {0}", new InvoiceCorrPrintLineViewBean(),bean.getInvoice() );
			request.setAttribute("invoiceColl", lines);
			for(InvoiceCorrPrintLineViewBean b:(Vector<InvoiceCorrPrintLineViewBean>) lines){
				b.setCharges(process.getSearchResult("select * from tcm_ops.invoice_corr_print_lc_view where invoice = {0} and line_item = {1}", new InvoiceCorrPrintLcViewBean(),bean.getInvoice(), b.getLineItem()) );
			}
			request.setAttribute("chargeColl",process.getSearchResult("select * from invoice_corr_print_hc_view where invoice = {0}",new InvoiceCorrPrintHcViewBean(), bean.getInvoice() ) );
//			for (InvoiceCorrPrintHcViewBean cb : chargeBeans) {
//				if (cb.getDeleteCharge()==null){
//					process.removeHeader(cb);
//				}
//				else {
//					process.updateHeader(cb);
//				}
//			}
//			String err = process.submitInvoiceCorr(bean,personnelBean);
//			request.setAttribute("done", "Y");
		}
		if ( "submit".equals(action) ) {
//          If already been run once. can call roll back.			
//			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			Collection<InvoiceCorrPrintLineViewBean> invoiceBeans = BeanHandler.getBeans((DynaBean) form, "InvoiceCorrPrintLineViewBean", new InvoiceCorrPrintLineViewBean(), this.getTcmISLocale());
//			Collection<InvoiceCorrPrintHcViewBean> chargeBeans = BeanHandler.getBeans((DynaBean) form, "InvoiceCorrPrintHcViewBean", new InvoiceCorrPrintHcViewBean(), this.getTcmISLocale());
			for (InvoiceCorrPrintLineViewBean ib : invoiceBeans) {
				if (ib.getOrderedQuantity() != null ){
					process.updateMaterialLine(ib,bean);
				}
//				else {
//					process.updateLine(ib);
//				}
			}			
//			Collection<InvoiceCorrPrintLineViewBean> invoiceBeans = BeanHandler.getBeans((DynaBean) form, "InvoiceCorrPrintLineViewBean", new InvoiceCorrPrintLineViewBean(), this.getTcmISLocale());
//			Collection<InvoiceCorrPrintHcViewBean> chargeBeans = BeanHandler.getBeans((DynaBean) form, "InvoiceCorrPrintHcViewBean", new InvoiceCorrPrintHcViewBean(), this.getTcmISLocale());
//			for (InvoiceCorrPrintLineViewBean ib : invoiceBeans) {
//				if (ib.getDeleteCharge()==null){
//					process.updateMaterialLine(ib);
//				}
//				else {
//					process.updateLine(ib);
//				}
//			}
			String err = process.submitInvoiceCorr(bean,personnelBean);
//			request.setAttribute("done", "Y");
		}
		if ( "rollback".equals(action) ) {
//          If already been run once. can call roll back.			
			String err = process.invoiceRollback(bean,personnelBean);
//			if( err == null || err.length() != 0 );
				request.setAttribute("rollbackmsg","Rollback Done!");
//			request.setAttribute("invoice",process.getSearchResult("select * from tcm_ops.invoice_corr_print_view where invoice = {0}", bean,bean.getInvoice() ) );
//			Collection lines = 
//				process.getSearchResult("select * from tcm_ops.invoice_corr_print_line_view where invoice = {0}", new InvoiceCorrPrintLineViewBean(),bean.getInvoice() );
//			request.setAttribute("invoiceColl", lines);
//			for(InvoiceCorrPrintLineViewBean b:(Vector<InvoiceCorrPrintLineViewBean>) lines){
//				b.setCharges(process.getSearchResult("select * from tcm_ops.invoice_corr_print_lc_view where invoice = {0} and line_item = {1}", new InvoiceCorrPrintLcViewBean(),bean.getInvoice(), b.getLineItem()) );
//			}
//			request.setAttribute("chargeColl",process.getSearchResult("select * from invoice_corr_print_hc_view where invoice = {0}",new InvoiceCorrPrintHcViewBean(), bean.getInvoice() ) );
		}
		else if ( "linecharge".equals(action) ) {
			InvoiceCorrPrintLcViewBean lbean = new InvoiceCorrPrintLcViewBean();
			BeanHandler.copyAttributes(form, lbean, getTcmISLocale(request));
			request.setAttribute("feeDropDown", process.getSearchResult( "select * from table (pkg_catalog_item_add_charge.fx_cat_item_add_charge_data({0}, {1})) where Line_Charge={2} ORDER BY ITEM_DESCRIPTION", new CatalogItemAddChargeBean(), bean.getOpsEntityId(),bean.getCurrencyId(), "Y"));
			request.setAttribute("beanColl", process.getSearchResult("select * from tcm_ops.invoice_corr_print_lc_view where invoice = {0} and line_item = {1}", new InvoiceCorrPrintLcViewBean(),lbean.getInvoice(), lbean.getLineItem()));
			return mapping.findForward("linecharge");
		}
		else if ( "headercharge".equals(action) ) {
			request.setAttribute("source","addchargeheader");
//			InvoiceCorrPrintHcViewBean hbean = new InvoiceCorrPrintHcViewBean();
//			BeanHandler.copyAttributes(form, hbean, getTcmISLocale(request));
//			BeanHandler.copyAttributes(form, bean, getTcmISLocale(request));
			request.setAttribute("feeDropDown", process.getSearchResult( "select * from table (pkg_catalog_item_add_charge.fx_cat_item_add_charge_data({0}, {1})) where Header_Charge={2} ORDER BY ITEM_DESCRIPTION", new CatalogItemAddChargeBean(), bean.getOpsEntityId(),bean.getCurrencyId(), "Y"));
			request.setAttribute("beanColl",process.getSearchResult("select * from invoice_corr_print_hc_view where invoice = {0}",new InvoiceCorrPrintHcViewBean(), bean.getInvoice() ) );
			return mapping.findForward("headercharge");
		}
		if ( "update".equals(action) ) {
			source = request.getParameter("source");
			String ErrorMsg = "";
			// header charge
			if( "addchargeheader".equals(source) ) {
			InvoiceCorrPrintLineViewBean invoiceBean = new InvoiceCorrPrintLineViewBean();
			BeanHandler.copyAttributes(form, invoiceBean, getTcmISLocale(request));
			Collection<InvoiceCorrPrintHcViewBean> chargeBeans = BeanHandler.getBeans((DynaBean) form, "MrAddChargeView", new InvoiceCorrPrintHcViewBean(), this.getTcmISLocale());
			for (InvoiceCorrPrintHcViewBean cb : chargeBeans) {
				if ("New".equals(cb.getChanged()))
					ErrorMsg += process.addHeaderCharge(cb,invoiceBean);
				else if (cb.getDeleteCharge()==null){
					process.removeHeader(cb);
				}
				else {
					process.updateHeader(cb);
				}
			}
//			InvoiceCorrPrintViewBean inputBean = new InvoiceCorrPrintViewBean();
//			BeanHandler.copyAttributes(form, inputBean, getTcmISLocale(request));
			request.setAttribute("feeDropDown", process.getSearchResult( "select * from table (pkg_catalog_item_add_charge.fx_cat_item_add_charge_data({0}, {1})) where Header_Charge={2} ORDER BY ITEM_DESCRIPTION", new CatalogItemAddChargeBean(), bean.getOpsEntityId(),bean.getCurrencyId(), "Y"));
			request.setAttribute("beanColl",process.getSearchResult("select * from invoice_corr_print_hc_view where invoice = {0}",new InvoiceCorrPrintHcViewBean(), bean.getInvoice() ) );
			request.setAttribute("source","addchargeheader");
			return mapping.findForward("headercharge");
			}
// line charage.			
			else  {
				Collection<InvoiceCorrPrintLineViewBean> invoiceBeans = BeanHandler.getBeans((DynaBean) form, "MrAddChargeView", new InvoiceCorrPrintLineViewBean(), this.getTcmISLocale());
				for (InvoiceCorrPrintLineViewBean ib : invoiceBeans) {
					if ("New".equals(ib.getChanged()))
						ErrorMsg += process.addLineCharge(ib);
					else if (ib.getDeleteCharge()==null){
//shouldn't never be here.						
//						process.updateMaterialLine(ib);
					}
					else {
						process.updateLine(ib);
					}
				}
				InvoiceCorrPrintLcViewBean lbean = new InvoiceCorrPrintLcViewBean();
				BeanHandler.copyAttributes(form, lbean, getTcmISLocale(request));
				request.setAttribute("feeDropDown", process.getSearchResult( "select * from table (pkg_catalog_item_add_charge.fx_cat_item_add_charge_data({0}, {1})) where Line_Charge={2} ORDER BY ITEM_DESCRIPTION", new CatalogItemAddChargeBean(), bean.getOpsEntityId(),bean.getCurrencyId(), "Y"));
				request.setAttribute("beanColl", process.getSearchResult("select * from tcm_ops.invoice_corr_print_lc_view where invoice = {0} and line_item = {1}", new InvoiceCorrPrintLcViewBean(),lbean.getInvoice(), lbean.getLineItem()));
				return mapping.findForward("linecharge");
			}
		}
		request.setAttribute("invoice",process.getSearchResult("select * from tcm_ops.invoice_corr_print_view where invoice = {0}", bean,bean.getInvoice() ) );
		Collection lines = 
			process.getSearchResult("select * from tcm_ops.invoice_corr_print_line_view where invoice = {0}", new InvoiceCorrPrintLineViewBean(),bean.getInvoice() );
		request.setAttribute("invoiceColl", lines);
		for(InvoiceCorrPrintLineViewBean b:(Vector<InvoiceCorrPrintLineViewBean>) lines){
			b.setCharges(process.getSearchResult("select * from tcm_ops.invoice_corr_print_lc_view where invoice = {0} and line_item = {1}", new InvoiceCorrPrintLcViewBean(),bean.getInvoice(), b.getLineItem()) );
		}
		request.setAttribute("chargeColl",process.getSearchResult("select * from invoice_corr_print_hc_view where invoice = {0}",new InvoiceCorrPrintHcViewBean(), bean.getInvoice() ) );
		
		return mapping.findForward("success");
	}

}
