package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.beans.CustomerContactBean;
import com.tcmis.internal.distribution.beans.CustomerLookupSearchViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;

/******************************************************************************
 * Process for CustomerAddressSearchView
 * @version 1.0
 *****************************************************************************/
public class CustomerLookupSearchProcess
extends GenericProcess {

	public CustomerLookupSearchProcess(TcmISBaseAction act) throws BaseException{
		super(act);
	}

	public CustomerLookupSearchProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getCompanyDropDown() throws BaseException {
		return factory.setBean( new com.tcmis.common.admin.beans.CompanyBean() ).select(
				new SearchCriteria("customer",SearchCriterion.EQUALS,"Y"), null,"COMPANY");
	}

	public Collection getCompanyData(LogisticsInputBean bean) throws BaseException {
		return factory.setBean(new com.tcmis.common.admin.beans.CompanyBean()).
		select(new SearchCriteria("customer",SearchCriterion.EQUALS,"Y").addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument() ),null,"COMPANY");
	}

	public Collection getSearchResult(CustomerLookupSearchViewBean ib) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();

		if ( !isBlank(ib.getSearchNameArgument() ) )
			criteria.addCriterionWithMode("customerName", ib.getSearchNameMode(), ib.getSearchNameArgument());

		if ( !isBlank(ib.getSearchCustomerIdArgument()) )
			criteria.addCriterion("customerId", SearchCriterion.EQUALS, ib.getSearchCustomerIdArgument().toString());

		if ( !isBlank(ib.getSearchBillToArgument()) )
			criteria.addCriterionWithMode("billToAddress", ib.getSearchBillToMode(), ib.getSearchBillToArgument());

		if ( !isBlank(ib.getSearchShipToArgument()) )
			criteria.addCriterionWithMode("shipToAddressSearch", ib.getSearchShipToMode(), ib.getSearchShipToArgument());

		if ( !isBlank(ib.getCustomerId()) )
			criteria.addCriterion("customerId", SearchCriterion.EQUALS, ib.getCustomerId().toString());

		if ( !isBlank(ib.getSearchSynonymArgument() ) )
			criteria.addCriterionWithMode("legacyCustomerId", ib.getSearchSynonymMode(), ib.getSearchSynonymArgument());

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerAddressSearchViewBean());
		Collection c = null;
		StringBuilder tmpQry = new StringBuilder("select distinct customer_id,customer_name,customer_origin,bill_to_address,notes,legacy_customer_id,credit_status, abc_classification from customer_address_search_view ");
		tmpQry.append(factory.getWhereClause(criteria));

		c = factory.selectQuery(tmpQry.toString());
		//log.debug("customer_address_search_view"+criteria);
		return c;
	}

	public Collection searchNewCustomer(String customerId) throws BaseException {

		//  	SearchCriteria criteria = new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId);
		//criteria.addCriterion("locationType", SearchCriterion.EQUALS,"Bill To");

		return factory.setBean(new CustomerAddRequestViewBean()).select( new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId),null, "customer_details_view");
	}

	public Collection searchNewCustomerContact(String customerId) throws BaseException {

		// 	SearchCriteria criteria = new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId);
		//criteria.addCriterion("locationType", SearchCriterion.EQUALS,"Bill To");

		return factory.setBean(new CustomerContactBean()).select( new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId),null, "CUSTOMER_CONTACT");
	}

	public Collection searchShipToBeanColl(String customerId) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerAddressSearchViewBean());
		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("customerId", SearchCriterion.EQUALS,	customerId);
		//criteria.addCriterion("locationType", SearchCriterion.NOT_EQUAL,"Bill To");

		SortCriteria sort = new SortCriteria();

		return factory.setBean(new CustomerAddressSearchViewBean()).
		select(criteria,sort, "customer_address_search_view");
	}

	public Object[] showResult(CustomerLookupSearchViewBean inputBean) throws BaseException {
		Vector bv = (Vector) this.getSearchResult(inputBean);
		HashMap m1 = new HashMap();
		Integer i1 = null;
		BigDecimal customerId = null;

		CustomerAddressSearchViewBean bean = null;
		for (int i = 0; i < bv.size(); i++) {
			bean = (CustomerAddressSearchViewBean) bv.get(i);
			customerId = bean.getCustomerId();

			if (m1.get(customerId) == null) {
				i1 = new Integer(0);
				m1.put(customerId, i1);
			}
			i1 = (Integer) m1.get(customerId);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(customerId, i1);
		}
		log.debug(m1);
		Object[] objs = {bv,m1};
		return objs;
	}
	
  public ExcelHandler getExcelReport(Collection<CustomerAddressSearchViewBean> data, Locale locale) throws
     NoDataException, BaseException, Exception {
   
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
//write column headers
	pw.addRow();
/*Pass the header keys for the Excel.*/
	String[] headerkeys = {
	  "label.customerid","label.customer","label.customerorigin","label.billtoaddress","label.creditstatus",
	  "label.notes","label.synonym"};
	/*This array defines the type of the excel column.
	0 means default depending on the data type. 
	pw.TYPE_PARAGRAPH defaults to 40 characters.
	pw.TYPE_CALENDAR set up the date with no time.
	pw.TYPE_DATE set up the date with time.*/
	int[] types = {
	  pw.TYPE_NUMBER,0,0,0,
	  pw.TYPE_PARAGRAPH,0};
	
	/*This array defines the default width of the column when the Excel file opens.
	0 means the width will be default depending on the data type.*/
	int[] widths = {
	  10,15,10,40,20,0,
	  25,10};
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	int[] horizAligns = {
	  0,0,0,0,0,
	  0,0};
	  
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
	// format the numbers to the special columns
	pw.setColumnDigit(6, 2);
	pw.setColumnDigit(7, 2);

	for (CustomerAddressSearchViewBean member : data) {
	  
     pw.addRow();
     pw.addCell(member.getCustomerId());
     pw.addCell(member.getCustomerName());
     pw.addCell(member.getCustomerOrigin());
     pw.addCell(member.getBillToAddress());
     pw.addCell(member.getCreditStatus());
     pw.addCell(member.getNotes());
     pw.addCell(member.getLegacyCustomerId());
   }
   return pw;
 }


}
