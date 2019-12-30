package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.accountspayable.beans.VoucherReportInputBean;
import com.tcmis.internal.accountspayable.process.VoucherReportProcess;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;

public class GetSupplierInvoicesProcess extends BaseProcess implements Callable<String> {
	HttpServletRequest request = null;
	PersonnelBean personnelBean = null;
	QuickQuoteInputBean inputBean = null;
	
	public GetSupplierInvoicesProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean){
		super(client, locale);
		this.request = request;
		this.inputBean = inputBean;
	}
	
	public String call() throws BeanCopyException {
		Collection results = null;
		
		try {
			results = searchSupplierInvoice();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		request.setAttribute("voucherReportColl",results);
		return "done";
	}
	
	public Collection searchSupplierInvoice() throws BaseException {
		VoucherReportProcess process = new VoucherReportProcess(this.getClient(), this.getLocale());
		
		VoucherReportInputBean inputBean3 = new VoucherReportInputBean();
		BeanHandler.copyAttributes(inputBean, inputBean3);
	    
		inputBean3.setBranchPlant(inputBean.getHub());
		inputBean3.setVoucherAge(new BigDecimal(inputBean.getDays()));	
		
		Collection results = null;
		
		try {
			results = process.getSupplierInvoices(inputBean3);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return results;
	}
}
