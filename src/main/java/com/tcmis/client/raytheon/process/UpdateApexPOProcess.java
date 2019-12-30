package com.tcmis.client.raytheon.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.raytheon.beans.UpdateGoletaPoViewBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class UpdateApexPOProcess
   extends BaseProcess {
   static Log log = LogFactory.getLog(UpdateApexPOProcess.class);

   public UpdateApexPOProcess(String client) {
  	super(client);
   }
   
   public UpdateApexPOProcess(String client,String locale) {
	    super(client,locale);
   }

   public UpdateApexPOProcess(String client,Locale locale) {
	    super(client,locale);
   }

   static public Collection getSearchResult(Locale locale,String client,Object inputBean,String tableName,String... cs) throws BaseException {

	  	DbManager dbManager = new DbManager( client , locale.toString() );
	  	GenericSqlFactory factory = new GenericSqlFactory(dbManager,inputBean);
	  	SearchCriteria criteria = new SearchCriteria();
	  	for(int i=0; i< cs.length; i++) {
	  	  	criteria.addCriterion(cs[i], SearchCriterion.EQUALS,cs[i+1]);
	  	  	i++;
	  	}
	  	return factory.select(criteria,null,tableName);
   }

   public Collection getSearchResult(Object inputBean,String[] searchCriteriaNames,String[] searchCriteriaValues) throws BaseException {
	    String tableName = "update_goleta_po_view";
	  	DbManager dbManager = new DbManager(getClient(),getLocale());
	  	GenericSqlFactory factory = new GenericSqlFactory(dbManager,inputBean);
	  	SearchCriteria criteria = new SearchCriteria();
	  	for(int i=0; i< searchCriteriaNames.length; i++) 
	  	  	criteria.addCriterion(searchCriteriaNames[i], SearchCriterion.EQUALS,searchCriteriaValues[i]);
	  	return factory.select(criteria,null,tableName);
  }

   public Object[] update(Collection<UpdateGoletaPoViewBean> beans,PersonnelBean personnelBean)  throws BaseException {
	    Collection c = new Vector();
	    Collection cc = new Vector();
	    String     error  = null;
	    PermissionBean permission = personnelBean.getPermissionBean();
	    if(beans != null) {
	      DbManager dbManager = new DbManager(getClient(),getLocale());
	      GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
	      Iterator iterator = beans.iterator();
	      while(iterator.hasNext()) {
	    	  UpdateGoletaPoViewBean bean = (UpdateGoletaPoViewBean)iterator.next();
	        if(bean.getOk() == null || bean.getOk().length() == 0 ) continue; 
	        if(	!permission.hasApplicationPermission("GenerateOrders", bean.getApplication(), bean.getFacilityId(), null) ) 
	        {
	        	// Internationlize this when database error messages is internationalized.
	        	//cc.add("No Workarea Permission. Workarea: " + bean.getApplication() );
	        	continue;
	        }
//	        CREATE OR REPLACE PROCEDURE p_update_customer_po(
//	        		   a_pr_number              request_line_item.pr_number%TYPE,
//	        		   a_line_item              request_line_item.line_item%TYPE,
//	        		   a_customer_po            request_line_item.po_number%TYPE,
//	        		   a_release_number         request_line_item.release_number%TYPE,
//	        		   a_personnel_id           request_line_item.last_updated_by%TYPE,
//	        		   a_error_code       OUT   VARCHAR
//	    ) IS
            Vector inArgs = new Vector();
            inArgs.add(bean.getPrNumber());
            inArgs.add(bean.getLineItem());
            inArgs.add(bean.getPoNumber());
            inArgs.add(bean.getReleaseNumber());
            inArgs.add(personnelBean.getPersonnelId()); //cannot be null, autoboxing the int.
            Collection outArgs = new Vector(1);
	        
	        outArgs.add(new Integer(java.sql.Types.VARCHAR));

	        Collection resultCollection = procFactory.doProcedure(
	                "P_UPDATE_CUSTOMER_PO", inArgs, outArgs);

            Iterator it = resultCollection.iterator();
            while (it.hasNext()) {
//	              result = (BigDecimal) it.next();
            	error  = (String) it.next();
	            if( error != null && error.length() !=0 )cc.add(error);
	        }
	      }
	    }
	    Object[] objs ={c,cc};
	    return objs;
	  }
}
