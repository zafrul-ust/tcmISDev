package com.tcmis.internal.distribution.process;

import java.util.Collection;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.distribution.beans.CustomerUpdateInputAddressBean;

public class CustomerUpdateInputAddressProcess  extends BaseProcess {

	public CustomerUpdateInputAddressProcess(String client)
	{
		super(client);
	}
	
	public Collection getAddressInfo(String companyId, String locationId) throws BaseException, Exception
	{
		DbManager dbManager;
		GenericSqlFactory factory;
		try{
			 dbManager = new DbManager(getClient(),this.getLocale());
			 factory = new GenericSqlFactory(dbManager,new CustomerUpdateInputAddressBean());
			 String query = "select * from location where company_id =  '" + companyId + "' and location_id='" + locationId + "'";
		return  factory.selectQuery(query);
		}finally{
			dbManager = null;
			factory = null;
		}
	
	}
	
	public int updateAddress(CustomerUpdateInputAddressBean customerUpdateInputAddressBean) throws BaseException, Exception
	{
		DbManager dbManager;
		GenericSqlFactory factory;
		try{
			 dbManager = new DbManager(getClient(),this.getLocale());
			 factory = new GenericSqlFactory(dbManager,new CustomerUpdateInputAddressBean());
		String query = "update location set zip = " + SqlHandler.delimitString(customerUpdateInputAddressBean.getZip()) 
					 + ", address_Line_1_display=" + SqlHandler.delimitString(customerUpdateInputAddressBean.getAddressLine1Display()) 
					 + ", address_Line_2_display=" + SqlHandler.delimitString(customerUpdateInputAddressBean.getAddressLine2Display()) 
					 + ", address_Line_3_display=" + SqlHandler.delimitString(customerUpdateInputAddressBean.getAddressLine3Display()) 
					 + ", address_Line_4_display=" + SqlHandler.delimitString(customerUpdateInputAddressBean.getAddressLine4Display()) 
					 + ", address_Line_5_display=" + SqlHandler.delimitString(customerUpdateInputAddressBean.getAddressLine5Display()) 
					 + ", city=" + SqlHandler.delimitString(customerUpdateInputAddressBean.getCity()) 
					 + ", state_abbrev='" + customerUpdateInputAddressBean.getStateAbbrev() 
					 + "', country_abbrev='" + customerUpdateInputAddressBean.getCountryAbbrev() 
					 + "' where company_id =  '" + customerUpdateInputAddressBean.getBillToCompanyId() + "'" 
					 + " and location_id =  '" + customerUpdateInputAddressBean.getLocationId() + "'";
	    return factory.deleteInsertUpdate(query);
		}finally{
			dbManager = null;
			factory = null;
		}
	}

}
