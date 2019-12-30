package com.tcmis.internal.distribution.process;

import java.util.Calendar;
import java.util.Collection;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;
import com.tcmis.internal.distribution.beans.DbuyInputBean;
import com.tcmis.internal.distribution.process.DbuyProcess;

public class GetSupplierPriceListCollProcess extends BaseProcess implements Callable<String> {
	HttpServletRequest request = null;
	PersonnelBean personnelBean = null;
	QuickQuoteInputBean inputBean = null;
	
	public GetSupplierPriceListCollProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean, PersonnelBean personnelBean){
		super(client, locale);
		this.request = request;
		this.inputBean = inputBean;
		this.personnelBean = personnelBean;
	}
	
	public String call() throws BeanCopyException {
		Collection results = null;
		
		try {
			results = searchSupplierPriceList();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		request.setAttribute("priceListColl",results);
		return "done";
	}
	
	public Collection searchSupplierPriceList() throws BaseException {
		DbuyProcess process = new DbuyProcess(this.getClient(), this.getLocale());
		
		DbuyInputBean inputBean3 = new DbuyInputBean();
		BeanHandler.copyAttributes(inputBean, inputBean3);
	    
//		inputBean3.setShowExpired("A");	
//		inputBean3.setSearchField("supplier");
//		inputBean3.setSearchMode("is");
//		inputBean3.setSearchArgument(inputBean.getSupplier());
		
		
		Collection results = null;
		
		try {
			results = process.getDbuy(inputBean3, personnelBean.getPersonnelIdBigDecimal());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return results;
	}
}
