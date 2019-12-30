package com.tcmis.internal.distribution.process;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.VvCountryBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.BillToCustomerTaxExemptVwBean;
import com.tcmis.internal.distribution.beans.BillToCustomerTaxExemptionBean;
import com.tcmis.internal.distribution.factory.BillToCustomerTaxExemptionBeanFactory;

/******************************************************************************
 * Process tcreate the .
 * @version 1.0
 *****************************************************************************/
public class CustTaxExempCertProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CustTaxExempCertProcess(String client,String locale) {
	    super(client,locale);
	  } 

  public Collection<BillToCustomerTaxExemptVwBean> getSearchResult(BillToCustomerTaxExemptionBean bean) throws BaseException {
  DbManager dbManager = new DbManager(getClient(),getLocale());
  GenericSqlFactory fac = new GenericSqlFactory(dbManager,new BillToCustomerTaxExemptVwBean());

  SearchCriteria searchCriteria = new SearchCriteria(); 

  if(null!=bean.getCustomerId()) {
	  searchCriteria.addCriterion("customerId",  SearchCriterion.EQUALS,   bean.getCustomerId().toString());
    }

    Collection c=fac.select(searchCriteria,null,"BILL_TO_CUSTOMER_TAX_EXEMPT_VW");

    return c;
  }

  
  
	public Collection addCustomerTaxExemptionRecord( Collection<BillToCustomerTaxExemptionBean> beans, BillToCustomerTaxExemptionBean inputBean) throws BaseException, Exception {
		
		DbManager dbManager = new DbManager(getClient(), getLocale());
		
		BillToCustomerTaxExemptionBeanFactory factory = new BillToCustomerTaxExemptionBeanFactory(dbManager);
		
		
		String errorMsg = "";
		Vector errorMessages = new Vector();
		SearchCriteria criteria = new SearchCriteria();
		try {
			
			if (null != inputBean.getCustomerId()) {
				criteria.addCriterion("customerId",
						SearchCriterion.EQUALS, inputBean.getCustomerId().toString());
			}
			factory.delete(criteria);
			
			// this should have been handled in bean handler, where is the code??			
			ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			String indefinite = res.getString("label.indefinite");
			Date ind_date = new Date(1100,0,1);// year 01/01/3000
			SimpleDateFormat format = new SimpleDateFormat(res.getString("java.dateformat"),this.getLocaleObject());
			if (beans != null && !beans.isEmpty()) {	
				for (BillToCustomerTaxExemptionBean dataBean : beans) {
					String dateS = dataBean.getExpirationDateStr();
					if( dateS == null ) continue; // required.
					if( dateS.equals(indefinite)) {
						dataBean.setExpirationDate(ind_date);
					}
					else {
						dataBean.setExpirationDate( format.parse(dateS) );
					}
					factory.insert(dataBean);

				}
				
			}
		} catch (Exception e) {
			errorMsg = "Error Adding Customner Tax Exemption Record";
			errorMessages.add(errorMsg);
		}
		
		return errorMessages;
		
	}
	
	 public Collection getValidTaxCountry() throws BaseException {
		 DbManager dbManager = new DbManager(getClient(), getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		  SearchCriteria st = new SearchCriteria("vatExempt", SearchCriterion.EQUALS,"Y");
		  return factory.setBean( new VvCountryBean() ).select(st, new SortCriteria("countryAbbrev"),"global.vv_country");
	  }
	
	

}