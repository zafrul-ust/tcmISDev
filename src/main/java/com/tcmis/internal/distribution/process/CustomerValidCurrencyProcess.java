package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.CustomerValidCurrencyInputBean;
import com.tcmis.internal.distribution.beans.OpsEntCustomerCurrencyViewBean;

/*
 *
 * 
 */

public class CustomerValidCurrencyProcess extends GenericProcess {
    Log log = LogFactory.getLog(this.getClass());

    public CustomerValidCurrencyProcess(TcmISBaseAction act) throws BaseException {
	super(act);
    }

    public CustomerValidCurrencyProcess(String client,Locale locale) {
	super(client,locale);
    }
    @SuppressWarnings("unchecked")
	public Collection<OpsEntCustomerCurrencyViewBean> getSearchResult(CustomerValidCurrencyInputBean inputBean, PersonnelBean user) throws BaseException {
	SearchCriteria criteria = new SearchCriteria();
	    
	if( !isBlank(inputBean.getCustomerId()) )
	    criteria.addCriterion("customerId",SearchCriterion.EQUALS,inputBean.getCustomerId());
	    
	if( !isBlank(inputBean.getOpsEntityId()) )
	    criteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId() );
	  
	return factory.setBean(new OpsEntCustomerCurrencyViewBean())
	    .select(criteria, new SortCriteria("currencyDisplay"),"ops_ent_customer_currency_view");
    }

    public Collection update(CustomerValidCurrencyInputBean inputBean, Collection<OpsEntCustomerCurrencyViewBean> beans, PersonnelBean user) throws BaseException {
	Vector errorMessages = new Vector();
	Collection inArgs = null;
	Collection outArgs = null;
	String errorMsg = "";
	DbManager dbManager = new DbManager(getClient(), getLocale());
	GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
	BigDecimal personnelId = user.getPersonnelIdBigDecimal();

	PermissionBean permissionBean = user.getPermissionBean();
	for (OpsEntCustomerCurrencyViewBean bean : beans) {

	    if (!user.getPermissionBean().hasOpsEntityPermission("CustomerDetailUpdate", null, null) && 
		!user.getPermissionBean().hasOpsEntityPermission("CustomerDetailAdmin", null, null)) continue;

	    try {
		if ("true".equals(bean.getDeleteCurrency())) {
		    inArgs = new Vector(3);
		    inArgs.add(inputBean.getCustomerId());
		    inArgs.add(inputBean.getOpsEntityId());
		    inArgs.add(bean.getCurrencyId());
		    outArgs = new Vector(1);
		    outArgs.add(new Integer(java.sql.Types.VARCHAR));

		    Vector error = (Vector) factory.doProcedure("pkg_customer_setup.p_delete_ops_ent_currency", inArgs, outArgs);

		    if(error.size()>0 && !"OK".equals(error.get(0)))
			{
			    String errorCode = (String) error.get(0);
			    errorMessages.add(errorCode);
			}
		    continue;
		}
		if (bean.getDeleteCurrency().contains("disable")) {
		    inArgs = new Vector(6);
		    inArgs.add(inputBean.getCustomerId());
		    inArgs.add(inputBean.getOpsEntityId());
		    inArgs.add(bean.getCurrencyId());
		    inArgs.add("");
		    inArgs.add(new BigDecimal(user.getPersonnelId()));
		    inArgs.add(bean.getRemittanceId());
		    outArgs = new Vector(1);
		    outArgs.add(new Integer(java.sql.Types.VARCHAR));

		    Vector error = (Vector) factory.doProcedure("pkg_customer_setup.p_insert_ops_ent_currency", inArgs, outArgs);

		    if(error.size()>0 && !"OK".equals(error.get(0)))
			{
			    String errorCode = (String) error.get(0);
			    errorMessages.add(errorCode);
			} 
		}
		if ("true".equals(bean.getDefaultCurrency())) {
		    inArgs = new Vector(3);
		    inArgs.add(inputBean.getCustomerId());
		    inArgs.add(inputBean.getOpsEntityId());
		    inArgs.add(bean.getCurrencyId());
		    outArgs = new Vector(1);
		    outArgs.add(new Integer(java.sql.Types.VARCHAR));

		    Vector error = (Vector) factory.doProcedure("pkg_customer_setup.p_update_default_currency", inArgs, outArgs);

		    if(error.size()>0 && !"OK".equals(error.get(0)))
			{
			    String errorCode = (String) error.get(0);
			    errorMessages.add(errorCode);
			}
		}
		//procedure p_update_remittance_id(a_customer_id       in  ops_entity_customer_currency.customer_id%type
		//                                ,a_ops_entity_id     in  ops_entity_customer_currency.ops_entity_id%type
		//                                ,a_currency_id     in  ops_entity_customer_currency.currency_id%type
		//                                ,a_remittance_id     in  ops_entity_customer_currency.remittance_id%type
		//                                ,a_error             out varchar2) IS
		
		inArgs = new Vector(4);
		inArgs.add(inputBean.getCustomerId());
		inArgs.add(inputBean.getOpsEntityId());
		inArgs.add(bean.getCurrencyId());
		inArgs.add(bean.getRemittanceId());
		outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Vector error = (Vector) factory.doProcedure("pkg_customer_setup.p_update_remittance_id", inArgs, outArgs);
		if(error.size()>0 && !"OK".equals(error.get(0)))
		    {
			String errorCode = (String) error.get(0);
			errorMessages.add(errorCode);
		    } 
	    }
	    catch (Exception e) {
		errorMsg = "Error updating Currency: " + bean.getCurrencyId();
		errorMessages.add(errorMsg);
	    }
	}

	factory = null;
	dbManager = null;

	return (errorMessages.size() > 0 ? errorMessages : null);
    }

}
