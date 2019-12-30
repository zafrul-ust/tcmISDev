package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.distribution.beans.CustomerContactViewBean;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;

public class GetContactCollProcess extends GenericProcess implements Callable<String> {
	HttpServletRequest request = null;
	QuickQuoteInputBean inputBean = null;
	
	public GetContactCollProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean){
		super(client, locale);
		this.request = request;
		this.inputBean = inputBean;
	}
	
	public String call() {
		Collection results = null;
		try {
			results = searchNewCustomerContact();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		request.setAttribute("contactColl",results);
		return "done";
	}
	
	public Collection searchNewCustomerContact() throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerId", SearchCriterion.EQUALS,	inputBean.getCustomerId().toString());
		criteria.addCriterion("status", SearchCriterion.EQUALS, "ACTIVE");

		return factory.setBean(new CustomerContactViewBean()).select( criteria, null, "CUSTOMER_CONTACT_VIEW");
	}
}
