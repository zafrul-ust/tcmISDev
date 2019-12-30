package com.tcmis.internal.hub.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PwcOrderLookupViewBean;
import com.tcmis.internal.hub.beans.PwcOrderNotifyErrorViewBean;
import com.tcmis.internal.hub.beans.PwcPoLookupViewBean;
import com.tcmis.internal.hub.beans.PwcResendResponseInputBean;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class PwcResendResponseProcess extends GenericProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PwcResendResponseProcess(String client,String locale) {
	    super(client,locale);
	  }

    public Collection<PwcPoLookupViewBean> getPoLookupResult(PwcResendResponseInputBean bean) throws BaseException {
    	//DbManager dbManager = new DbManager(getClient(),getLocale());
    	//GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PwcPoLookupViewBean());
    	factory = factory.setBean(new PwcPoLookupViewBean());

    	String viewName = "";
    	SearchCriteria criteria = new SearchCriteria();
    	SortCriteria sortCriteria = null; 
    	if ( ! StringHandler.isBlankString(bean.getPoNumberSearch())) {
    		viewName = "PWC_PO_LOOKUP_VIEW";
    		criteria.addCriterion("radianPo", SearchCriterion.EQUALS, bean.getPoNumberSearch());
    	}

	    Collection c=factory.select(criteria,sortCriteria,viewName);
	
	    return c;
    }
    
    public Collection<PwcOrderLookupViewBean> getOrderLookupResult(PwcResendResponseInputBean bean) throws BaseException {
    	/*DbManager dbManager = new DbManager(getClient(),getLocale());
    	GenericSqlFactory fac = new GenericSqlFactory(dbManager,new PwcOrderLookupViewBean());
*/
    	String viewName = "";
    	SearchCriteria criteria = new SearchCriteria();
    	SortCriteria sortCriteria = null; 
    	if (bean.getLookupAction().equals("orderLookup")) {
	    	if ( ! StringHandler.isBlankString(bean.getOrderNumberSearch())) {
	    		viewName = "PWC_ORDER_LOOKUP_VIEW";
	    		criteria.addCriterion("customerPoNo", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    	}
    	}
    	else if (bean.getLookupAction().equals("allocated")) {
    		viewName = "PWC_ALLOCATED_ORDERS_VIEW";
    		if ( ! StringHandler.isBlankString(bean.getOrderNumberSearch())) {
    			SearchCriteria custPoNoCriteria = new SearchCriteria();
	    		custPoNoCriteria.addCriterion("customerPoNo", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		SearchCriteria prNoCriteria = new SearchCriteria();
	    		prNoCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		criteria.addSubCriteria(custPoNoCriteria,prNoCriteria);
	    	}
    	}
    	else if (bean.getLookupAction().equals("pickedNotShipped")) {
    		viewName = "PWC_PICKED_ORDERS_VIEW";
    		if ( ! StringHandler.isBlankString(bean.getOrderNumberSearch())) {
	    		SearchCriteria custPoNoCriteria = new SearchCriteria();
	    		custPoNoCriteria.addCriterion("customerPoNo", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		SearchCriteria prNoCriteria = new SearchCriteria();
	    		prNoCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		criteria.addSubCriteria(custPoNoCriteria,prNoCriteria);
	    	}
    	}
    	else if (bean.getLookupAction().equals("shipped")) {
    		if ( ! StringHandler.isBlankString(bean.getOrderNumberSearch())) {
	    		viewName = "PWC_SHIPPED_BILLED_ORDERS_VIEW";
	    		SearchCriteria custPoNoCriteria = new SearchCriteria();
	    		custPoNoCriteria.addCriterion("customerPoNo", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		SearchCriteria prNoCriteria = new SearchCriteria();
	    		prNoCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		criteria.addSubCriteria(custPoNoCriteria,prNoCriteria);
	    		criteria.addCriterion("status", SearchCriterion.EQUALS, "SHIPPED");
	    	}
    	}
    	else if (bean.getLookupAction().equals("billed")) {
    		if ( ! StringHandler.isBlankString(bean.getOrderNumberSearch())) {
	    		viewName = "PWC_SHIPPED_BILLED_ORDERS_VIEW";
	    		SearchCriteria custPoNoCriteria = new SearchCriteria();
	    		custPoNoCriteria.addCriterion("customerPoNo", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		SearchCriteria prNoCriteria = new SearchCriteria();
	    		prNoCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
	    		criteria.addSubCriteria(custPoNoCriteria,prNoCriteria);
	    		criteria.addCriterion("status", SearchCriterion.EQUALS, "BILLED");
	    	}
    	}

    	Collection c = null;
    	if ( ! StringHandler.isBlankString(viewName)) {
    		c=factory.setBean(new PwcOrderLookupViewBean()).select(criteria,sortCriteria,viewName);
    	}
	
	    return c;
    }

    public Collection<PwcOrderNotifyErrorViewBean> getNotifyErrorResult(PwcResendResponseInputBean bean) throws BaseException {
    	//DbManager dbManager = new DbManager(getClient(),getLocale());
    	//GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PwcOrderNotifyErrorViewBean());

    	SearchCriteria criteria = new SearchCriteria();
    	if ( ! StringHandler.isBlankString(bean.getOrderNumberSearch())) {
	    	SearchCriteria custPoNoCriteria = new SearchCriteria();
			custPoNoCriteria.addCriterion("customerPoNo", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
			SearchCriteria prNoCriteria = new SearchCriteria();
			prNoCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, bean.getOrderNumberSearch());
			criteria.addSubCriteria(custPoNoCriteria,prNoCriteria);
    	}

    	SortCriteria scriteria = new SortCriteria();
    	//Collection<ResendXbuyViewBean> c = factory.select(criteria,scriteria);
    	Collection c=factory.setBean(new PwcOrderNotifyErrorViewBean()).select(criteria,null,"PWC_ORDER_NOTIFY_ERROR_VIEW");

    	return c;
    }
    
    public Collection<String> resendFullOrderAck(Collection<PwcOrderLookupViewBean> beans) throws BaseException {
    	PwcOrderLookupViewBean errorBean = new PwcOrderLookupViewBean();
    	Collection<String> errorMsgs = new ArrayList<String>();
		try {
			if (beans.size() == 0) {
				errorMsgs.add("Error: No rows selected. Unable to resend FOR");
			}
	    	for (PwcOrderLookupViewBean bean : beans) {
	    		if(!StringHandler.isBlankString(bean.getOk()) && Boolean.parseBoolean(bean.getOk())) {
		    		errorBean = bean;
					Collection inArgs = new Vector(3);
					inArgs.add(bean.getCustomerPoNo());
					inArgs.add(bean.getCustomerPoLineNo());
					inArgs.add(bean.getPrNumber());
					inArgs.add(bean.getLineItem());
		
					Collection outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					
					if(log.isDebugEnabled()) {
						log.debug("P_PWC_RESEND_FOR_RESPONSE:" + inArgs);
				    }
					
					Collection data = factory.doProcedure("P_PWC_RESEND_FOR_RESPONSE",inArgs,outArgs);
					Iterator i11 = data.iterator();
					String erroVal = "error";
					while (i11.hasNext()) {
						erroVal = (String) i11.next();
						if ( ! StringHandler.isBlankString(erroVal)) {
							errorMsgs.add(erroVal);							
						}
					}
					
				    if( ! errorMsgs.isEmpty()) {
				    	log.info("Error in Procedure P_PWC_RESEND_FOR_RESPONSE: "+bean.getCustomerPoNo()+"-"+bean.getCustomerPoLineNo()+"-"+bean.getPrNumber()+" Error Code "+erroVal+" ");
	                }
	    		}
	    	}
		}catch ( Exception dbe ) {
			log.info("Error in Procedure P_PWC_RESEND_FOR_RESPONSE: "+errorBean.getCustomerPoNo()+"-"+errorBean.getCustomerPoLineNo()+"-"+errorBean.getPrNumber());
			String errorMsg = "Error updating : "+dbe.toString();
			errorMsgs.add(errorMsg);
		}
		
		return errorMsgs;
    }

    public Collection  update(Collection <PwcOrderNotifyErrorViewBean> pwcOrderNotifyErrorViewBeanCollection) throws
  		BaseException, Exception {  
    	Collection inArgs = null;
    	Collection outArgs = null; 
    	String errorMsg = "";
    	String errorCode = null;
    	Vector errorMessages = new Vector();
    	//DbManager dbManager = new DbManager(getClient());
    	//GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

    	if((pwcOrderNotifyErrorViewBeanCollection!=null) && (!pwcOrderNotifyErrorViewBeanCollection.isEmpty()))
    	{
    		for(PwcOrderNotifyErrorViewBean pwcOrderNotifyErrorBean : pwcOrderNotifyErrorViewBeanCollection) {
    			try {
    				if(!StringHandler.isBlankString(pwcOrderNotifyErrorBean.getOk()) && 
    						Boolean.parseBoolean(pwcOrderNotifyErrorBean.getOk())) {
    					inArgs = new Vector(4);
					     inArgs.add(pwcOrderNotifyErrorBean.getCustomerPoNo());
					     inArgs.add(pwcOrderNotifyErrorBean.getCustomerPoLineNo());
					     inArgs.add(pwcOrderNotifyErrorBean.getPrNumber());
					     inArgs.add(pwcOrderNotifyErrorBean.getLineItem());
					     outArgs = new Vector(1);
					     outArgs.add(new Integer(java.sql.Types.VARCHAR));
					     
					     if(log.isDebugEnabled()) {
					         log.debug("P_PWC_RESET_NOTIFY:" + inArgs);
					     }
					     
					     Vector error = (Vector) factory.doProcedure("P_PWC_RESET_NOTIFY", inArgs, outArgs);
					     if(error.size()>0 && error.get(0) != null)
		                 { 	 errorCode = (String) error.get(0);
		                	 log.info("Error in Procedure P_PWC_RESET_NOTIFY: "+pwcOrderNotifyErrorBean.getCustomerPoNo()+"-"+pwcOrderNotifyErrorBean.getCustomerPoLineNo()+"-"+pwcOrderNotifyErrorBean.getPrNumber()+" Error Code "+errorCode+" ");
		                	 errorMessages.add(errorCode);                	 
		                 } 		     
					     
					 }
				} catch (Exception e) {
					errorMsg = "Error updating : "+e.toString();
					errorMessages.add(errorMsg);
				}
    		}
    	}  

    	return errorMessages;
    }

	public Collection resetSendResponse(Collection<PwcOrderNotifyErrorViewBean> pwcOrderNotifyErrorViewBeanCollection) throws BaseException, Exception {
		  
    	Collection inArgs = null;
    	Collection outArgs = null; 
    	String errorMsg = "";
    	String errorCode = null;
    	Vector errorMessages = new Vector();
    	
    	if((pwcOrderNotifyErrorViewBeanCollection!=null) && (!pwcOrderNotifyErrorViewBeanCollection.isEmpty()))
    	{
    		for(PwcOrderNotifyErrorViewBean pwcOrderNotifyErrorBean : pwcOrderNotifyErrorViewBeanCollection) {
    			try {
    				if(!StringHandler.isBlankString(pwcOrderNotifyErrorBean.getOk()) && 
    						Boolean.parseBoolean(pwcOrderNotifyErrorBean.getOk())) {
    					inArgs = new Vector(4);
					     inArgs.add(pwcOrderNotifyErrorBean.getCustomerPoNo());
					     inArgs.add(pwcOrderNotifyErrorBean.getCustomerPoLineNo());
					     inArgs.add(pwcOrderNotifyErrorBean.getPrNumber());
					     inArgs.add(pwcOrderNotifyErrorBean.getLineItem());
					     outArgs = new Vector(1);
					     outArgs.add(new Integer(java.sql.Types.VARCHAR));
					     
					     if(log.isDebugEnabled()) {
					         log.debug("P_PWC_RESET_SEND_FOR:" + inArgs);
					     }
					     
					     Vector error = (Vector) factory.doProcedure("P_PWC_RESET_SEND_FOR", inArgs, outArgs);
					     if(error.size()>0 && error.get(0) != null)
		                 { 	 errorCode = (String) error.get(0);
		                	 log.info("Error in Procedure P_PWC_RESET_SEND_FOR: "+pwcOrderNotifyErrorBean.getCustomerPoNo()+"-"+pwcOrderNotifyErrorBean.getCustomerPoLineNo()+"-"+pwcOrderNotifyErrorBean.getPrNumber()+" Error Code "+errorCode+" ");
		                	 errorMessages.add(errorCode);                	 
		                 } 		     
					     
					 }
				} catch (Exception e) {
					errorMsg = "Error updating : "+e.toString();
					errorMessages.add(errorMsg);
				}
    		}
    	}  

    	return errorMessages;    
	}
}