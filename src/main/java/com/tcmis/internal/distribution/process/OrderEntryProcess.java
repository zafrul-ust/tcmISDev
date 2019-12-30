package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.OrderEntryInputBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class OrderEntryProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public OrderEntryProcess(String client,String locale) {
		super(client,locale);
	} 

	public Collection<SalesQuoteViewBean> getSearchResult(OrderEntryInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SalesQuoteViewBean());

		SearchCriteria searchCriteria = new SearchCriteria();

		
		searchCriteria.addCriterion("status", SearchCriterion.IN, "'ScratchPad','Draft'");		
		
		searchCriteria.addCriterion("submittedBy", SearchCriterion.EQUALS,  String.valueOf(personnelBean.getPersonnelId()));

		Collection<SalesQuoteViewBean>  c = factory.select(searchCriteria,null,"SCRATCH_PAD_VIEW");

		return c;
	}





	
  public Collection delete(Collection <SalesQuoteViewBean> salesQuoteViewBeanCollection,  PersonnelBean personnelBean) throws
		BaseException, Exception {
	Collection inArgs = null;
	Collection outArgs = null;
	String errorMsg = "";
	String errorCode = null;
	//Collection resultCollection = null;
	Vector errorMessages = new Vector();

	DbManager dbManager = new DbManager(getClient());
	GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

	if((salesQuoteViewBeanCollection!=null) && (!salesQuoteViewBeanCollection.isEmpty()))
	{
		 for(SalesQuoteViewBean salesQuoteBean : salesQuoteViewBeanCollection) {
		     try {
				if( (!StringHandler.isBlankString(salesQuoteBean.getOk())) && (null!=salesQuoteBean.getPrNumber()))
						  {
				     inArgs = new Vector(2);				    
				     inArgs.add(personnelBean.getPersonnelId());
                     inArgs.add(salesQuoteBean.getPrNumber());
                     outArgs = new Vector(1);
				     outArgs.add(new Integer(java.sql.Types.VARCHAR));
				     if(log.isDebugEnabled()) {
				         log.debug("Input Args for PKG_SALES_QUOTE.P_DELETE_SALES_QUOTE  :" + inArgs);
				     }			   
				     Vector error = (Vector)factory.doProcedure("PKG_SALES_QUOTE.P_DELETE_SALES_QUOTE", inArgs, outArgs);
				     if( error.size()>0 && error.get(0) != null && !"ok".equalsIgnoreCase((String)error.get(0)))
	                 {
	                	 errorCode = (String) error.get(0);
	                	 log.info("Error in Procedure PKG_SALES_QUOTE.P_DELETE_SALES_QUOTE: "+salesQuoteBean.getPrNumber()+" Error Code "+errorCode+" ");
	                	 errorMessages.add(errorCode);
	                	 
	                 }			     
				     
				 }			
			} catch (Exception e) {
				errorMsg = "Error deleting record with Pr Number: "+ salesQuoteBean.getPrNumber();
				errorMessages.add(errorMsg);
			}
		 }
		 
	}  


	 return errorMessages;


	}
}