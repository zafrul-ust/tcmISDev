package com.tcmis.internal.distribution.process;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;
import com.tcmis.internal.supply.beans.PoSearchResultBean;

public class GetPoCollProcess extends BaseProcess implements Callable<String> {
	HttpServletRequest request = null;
	PersonnelBean personnelBean = null;
	QuickQuoteInputBean inputBean = null;
	
	public GetPoCollProcess(String client,String locale, HttpServletRequest request, QuickQuoteInputBean inputBean, PersonnelBean personnelBean){
		super(client,locale);
		this.request = request;
		this.inputBean = inputBean;
		this.personnelBean = personnelBean;
	}
	
	public String call() throws Exception {
		
		Collection results = null;
		try {
			results = searchPos();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		request.setAttribute("poHistoryColl",results);
		return "done";
	}
	
	public Collection searchPos() throws BaseException {

		  	DbManager dbManager = new DbManager(getClient());
		  	GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoSearchResultBean());
		  
		  	SearchCriteria searchCriteria = new SearchCriteria();
			
		  	if(inputBean.getOpsEntityId() != null && !StringHandler.isBlankString(inputBean.getOpsEntityId())) 
		    	searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		  	
		    if(inputBean.getHub() != null && !StringHandler.isBlankString(inputBean.getHub())) 
		    	searchCriteria.addCriterion("branchPlant", SearchCriterion.EQUALS, inputBean.getHub());
		    	
		    if(inputBean.getInventoryGroup() != null && !StringHandler.isBlankString(inputBean.getInventoryGroup()))
		    	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
	/*	    else {
		    	String invQuery = " select distinct inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId() ;
	//	    	if( personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0 )
	//	    		invQuery +=  " and company_id = '" + personnelBean.getCompanyId() +"' ";
		    	searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		    }
	*/	    
		    if(inputBean.getSupplier() != null && !StringHandler.isBlankString(inputBean.getSupplier())) {
		        searchCriteria.addCriterion("supplier", SearchCriterion.EQUALS, inputBean.getSupplier());
		    }
		    
		    // set date
		    Calendar c = Calendar.getInstance();
		    c.setTime(inputBean.getToday());
		    c.add(Calendar.DATE, Integer.parseInt((inputBean.getDays().trim()))*-1);

		    if(inputBean.getDays() != null) {
		    	searchCriteria.addCriterion("dateConfirmed", SearchCriterion.FROM_DATE, c.getTime());
		    }

		    return factory.select( searchCriteria, null, "PURCHASING_HISTORY_VIEW");
	}
}
