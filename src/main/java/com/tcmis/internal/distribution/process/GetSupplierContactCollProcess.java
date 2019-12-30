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
import com.tcmis.internal.supply.process.POSupplierContactProcess;
import com.tcmis.internal.supply.beans.POSupplierContactInputBean;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;

public class GetSupplierContactCollProcess extends BaseProcess implements Callable<String> {
	HttpServletRequest request = null;
	PersonnelBean personnelBean = null;
	QuickQuoteInputBean inputBean = null;
	
	public GetSupplierContactCollProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean){
		super(client, locale);
		this.request = request;
		this.inputBean = inputBean;
	}
	
	public String call() throws BeanCopyException {
		Collection results = null;
		
		try {
			results = searchSupplierContact();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		request.setAttribute("pOSupplierContactBeanCollection",results);
		return "done";
	}
	
	public Collection searchSupplierContact() throws BaseException {
		POSupplierContactProcess process = new POSupplierContactProcess(this.getClient());
		
		POSupplierContactInputBean inputBean3 = new POSupplierContactInputBean();
		BeanHandler.copyAttributes(inputBean, inputBean3);
	    
		inputBean3.setAction("search");
		
		Collection results = null;
		
		try {
			results = process.getSupplierContactBeanCollection(inputBean3);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return results;
	}
}
