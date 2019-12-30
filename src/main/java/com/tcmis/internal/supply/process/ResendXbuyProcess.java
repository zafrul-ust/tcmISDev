package com.tcmis.internal.supply.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.ResendXBuyInputBean;
import com.tcmis.internal.supply.beans.ResendXbuyViewBean;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class ResendXbuyProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ResendXbuyProcess(String client,String locale) {
	    super(client,locale);
	  } 

  public Collection<ResendXbuyViewBean> getSearchResult(ResendXBuyInputBean bean) throws BaseException {
  DbManager dbManager = new DbManager(getClient(),getLocale());
  GenericSqlFactory fac = new GenericSqlFactory(dbManager,new ResendXbuyViewBean());

  SearchCriteria criteria = new SearchCriteria();

  /*if(bean.getSearchArgument() != null && bean.getSearchArgument().length() > 0) {
     criteria.addCriterion("RADIAN_PO",
                            SearchCriterion.LIKE,
                           bean.getSearchArgument());
    }*/

  SortCriteria scriteria = new SortCriteria();
  //Collection<ResendXbuyViewBean> c = factory.select(criteria,scriteria);
    Collection c=fac.select(null,null,"RESEND_XBUY_VIEW");

    return c;
  }

  
  
  public Collection update(Collection <ResendXbuyViewBean> resendViewBeanCollection) throws
	BaseException, Exception {
Collection inArgs = null;
Collection outArgs = null;
String errorMsg = "";
String errorCode = null;
//Collection resultCollection = null;
Vector errorMessages = new Vector();

DbManager dbManager = new DbManager(getClient());
GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

if((resendViewBeanCollection!=null) && (!resendViewBeanCollection.isEmpty()))
{
	 for(ResendXbuyViewBean rsendBean : resendViewBeanCollection) {
	     try {
			if(!StringHandler.isBlankString(rsendBean.getOk()) && !StringHandler.isBlankString(rsendBean.getShipFromLocationId())
					 &&  !StringHandler.isBlankString(rsendBean.getSupplierAccountNumber()) ) {
			     inArgs = new Vector(4);
			     inArgs.add(rsendBean.getRadianPo());
			     inArgs.add(rsendBean.getPoLine());
			     inArgs.add(rsendBean.getShipFromLocationId());
			     inArgs.add(rsendBean.getSupplierAccountNumber());
			     outArgs = new Vector(1);
			     outArgs.add(new Integer(java.sql.Types.VARCHAR));
			     if(log.isDebugEnabled()) {
			         log.debug("P_RESEND_XBUY_TO_AIRGAS:" + inArgs);
			     }			   
			     Vector error = (Vector) factory.doProcedure("P_RESEND_XBUY_TO_AIRGAS", inArgs, outArgs);
			     if(error.size()>0 && error.get(0) != null)
                 {
                	 errorCode = (String) error.get(0);
                	 log.info("Error in Procedure P_RESEND_XBUY_TO_AIRGAS: "+rsendBean.getRadianPo()+"-"+rsendBean.getShipFromLocationId()+"-"+rsendBean.getSupplierAccountNumber()+" Error Code "+errorCode+" ");
                	 errorMessages.add(errorCode);
                	 
                 }			     
			     
			 }			
		} catch (Exception e) {
			errorMsg = "Error updating PO: "+ rsendBean.getRadianPo()+"-"+rsendBean.getPoLine()+ "";
			errorMessages.add(errorMsg);
		}
	 }
	 
}  


 return errorMessages;


}

  public Collection confirm(String radianPo,String airgasCu ) throws  BaseException, Exception {
	  Collection inArgs = null;
	  Collection outArgs = null;
	  String errorMsg = "";
	  String errorCode = null;
	  //Collection resultCollection = null;
	  Vector errorMessages = new Vector();

	  DbManager dbManager = new DbManager(getClient());
	  GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
	  try {
		  inArgs = new Vector(2);
		  inArgs.add(radianPo);
		  inArgs.add(airgasCu);
		  outArgs = new Vector();
		  //			     outArgs.add(new Integer(java.sql.Types.VARCHAR));
		  if(log.isDebugEnabled()) {
			  log.debug("p_manual_xbuy_confirm:" + inArgs);
		  }			   
		  Vector error = (Vector) factory.doProcedure("p_manual_xbuy_confirm", inArgs, outArgs);
		  if(error.size()>0 && error.get(0) != null) {
			  errorCode = (String) error.get(0);
			  log.info("Error in Procedure p_manual_xbuy_confirm: "+radianPo+" Error Code "+errorCode+" ");
			  errorMessages.add(errorCode);

		  }			     
	  } catch (Exception e) {
		  errorMsg ="Error in Procedure p_manual_xbuy_confirm: "+radianPo+" Error Code "+errorCode+" ";
		  errorMessages.add(errorMsg);
	  }
	  return errorMessages;
  }

}