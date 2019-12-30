package com.tcmis.internal.distribution.process;

//import java.io.*;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.VvCarrierBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerCarrierInfoBean;
import com.tcmis.internal.distribution.beans.VvCarrierServiceTypeBean;

/******************************************************************************
 * Process used by POSupplierAction
 * @version 1.0
 *****************************************************************************/

public class CustomerCarrierProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());

	public CustomerCarrierProcess(String client) 
	{
		super(client);
	}  

	public CustomerCarrierProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection <CustomerCarrierInfoBean> getCarrierInfoViewBeanCollection(CustomerCarrierInfoBean bean) throws BaseException, Exception 
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerCarrierInfoBean());

		Collection <CustomerCarrierInfoBean> c = null;

		SearchCriteria searchCriteria = new SearchCriteria();

		if(!StringHandler.isBlankString(bean.getSearchArgument())) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();

			if (mode.equals("is"))
				searchCriteria.addCriterion(field, SearchCriterion.EQUALS, bean.getSearchArgument());
			if (mode.equals("like"))
				searchCriteria.addCriterion(field, SearchCriterion.LIKE, bean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
			if (mode.equals("startsWith"))
				searchCriteria.addCriterion(field, SearchCriterion.STARTS_WITH, bean.getSearchArgument(),SearchCriterion.IGNORE_CASE);
			if (mode.equals("endsWith"))
				searchCriteria.addCriterion(field, SearchCriterion.ENDS_WITH, bean.getSearchArgument(),	SearchCriterion.IGNORE_CASE);
		}

		searchCriteria.addCriterion("status",	SearchCriterion.EQUALS,	"ACTIVE",SearchCriterion.IGNORE_CASE);	

		if(!StringHandler.isBlankString(bean.getInventoryGroup())) {
			searchCriteria.addCriterion("inventoryGroup",	SearchCriterion.IN,	"'All','ALL','"+bean.getInventoryGroup()+"'");	
		}
       
		if(("Yes".equals(bean.getShowCustomerCarriersOnly())) && (null!=bean.getCustomerId()))
		{
			searchCriteria.addCriterion("customerId",	SearchCriterion.EQUALS,	bean.getCustomerId().toString());	
		}
		else
		{
//			searchCriteria.addCriterion("customerId",	SearchCriterion.IS,	"");	
		}

		c = factory.select(searchCriteria, null, TABLE_NAME);

		return c;
	}


	public Collection createNewCarrier(CustomerCarrierInfoBean bean) throws
	BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;		
		Vector errorMessages = new Vector();


		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if(bean!=null )
		{

			try {
				if( ((!StringHandler.isBlankString(bean.getCarrierName())) && (!StringHandler.isBlankString(bean.getTransportationMode())))
						&& 
						 (!StringHandler.isBlankString(bean.getCarrierMethod())) )
				{
					inArgs = new Vector(4);				    
					inArgs.add(bean.getCarrierName().trim());
					inArgs.add(bean.getTransportationMode());
					inArgs.add(bean.getCarrierMethod());
					inArgs.add("");						
					outArgs = new Vector(2);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					if(log.isDebugEnabled()) {
						log.debug("Input Args for P_CREATE_NEW_CARRIER  :" + inArgs);
					}			   
					Vector error = (Vector)factory.doProcedure("P_CREATE_NEW_CARRIER", inArgs, outArgs);
					if( error.size()>0 && error.get(0) != null && !"ok".equalsIgnoreCase((String)error.get(0)))
					{
						errorCode = (String) error.get(0);
						log.info("Error in Procedure P_CREATE_NEW_CARRIER: "+bean.getCarrierName()+" Error Code "+errorCode+" ");
						errorMessages.add(errorCode);

					}			     

				}			
			} catch (Exception e) {
				errorMsg = "Error Adding New Record for  Carrier: "+ bean.getCarrierName();
				errorMessages.add(errorMsg);
			}


		}  


		return errorMessages;

	}

	public Object[] createNewCarrierAccount(CustomerCarrierInfoBean bean) throws
	BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;		
		Vector errorMessages = new Vector();
		boolean isValid = false;
		Vector error = null;
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if(bean!=null )
		{

			try {
				if( ((!StringHandler.isBlankString(bean.getCarrierName())) && (!StringHandler.isBlankString(bean.getAccount())))
						&& 
						( (null!=bean.getCustomerId()) && (!StringHandler.isBlankString(bean.getInventoryGroup()))))
				{
					isValid = true;
					inArgs = new Vector(4);				    
					inArgs.add(bean.getCarrierName());
					inArgs.add(bean.getAccount().trim());
					inArgs.add(bean.getCustomerId());
					inArgs.add(bean.getInventoryGroup());
					inArgs.add(bean.getNotes());						
					outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
                    outArgs.add(new Integer(java.sql.Types.NUMERIC));
                    if(log.isDebugEnabled()) {
						log.debug("Input Args for P_CREATE_NEW_CARRIER_ACCOUNT  :" + inArgs);
					}			   
                    error = (Vector)factory.doProcedure("P_CREATE_NEW_CARRIER_ACCOUNT", inArgs, outArgs);
					if( error.size()>0 && error.get(0) != null && !"ok".equalsIgnoreCase((String)error.get(0)))
					{
						errorCode = (String) error.get(0);
						log.info("Error in Procedure P_CREATE_NEW_CARRIER_ACCOUNT: "+bean.getCarrierName()+" Error Code "+errorCode+" ");
						errorMessages.add(errorCode);

					}			     

				}			
			} catch (Exception e) {
				errorMsg = "Error Adding New Account for  Carrier: "+ bean.getCarrierName();
				errorMessages.add(errorMsg);
			}


		}  

		Object[] objs = {errorMessages,isValid,error.get(1)};
		return objs;



	}


	public Collection getVvCarrier() throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VvCarrierBean());

		SearchCriteria searchCriteria = new SearchCriteria();

		SortCriteria sortCriteria = new SortCriteria();

		searchCriteria.addCriterion("availableToPublic",SearchCriterion.EQUALS,"y");				

		sortCriteria.setSortAscending(true);

		sortCriteria.addCriterion("carrierName");

		return factory.select(searchCriteria, sortCriteria, "VV_CARRIER");
	}





	private static final String TABLE_NAME ="CUSTOMER_CARRIER_INFO";	
}




