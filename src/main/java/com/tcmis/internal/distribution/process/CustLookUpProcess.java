package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
import com.tcmis.internal.distribution.beans.CustomerAddressSearchViewBean;
import com.tcmis.internal.distribution.beans.CustomerCarrierInfoBean;
import com.tcmis.internal.distribution.beans.CustomerCarrierInfoViewBean;
import com.tcmis.internal.distribution.beans.CustomerContactViewBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;

/******************************************************************************
 * Process for CustomerAddressSearchView
 * @version 1.0
 *****************************************************************************/
public class CustLookUpProcess
extends GenericProcess {

	public CustLookUpProcess(TcmISBaseAction act) throws BaseException{
		super(act);
	}

	public CustLookUpProcess(String client,String locale) {
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

	public Collection getSearchResult(CustomerAddressSearchViewBean ib, PersonnelBean personnelBean) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerAddressSearchViewBean());        
		StringBuilder query = new StringBuilder("select * from table (Pkg_sales_search.FX_CUSTOMER_ADDRESS_SEARCH('").append(NumberHandler.emptyIfNull(ib.getSearchCustomerIdArgument())).append("',");
		query.append(SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(ib.getSearchNameArgument()))).append(",'").append(StringHandler.emptyIfNull(ib.getSearchNameMode())).append("',");
		query.append(SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(ib.getSearchSynonymArgument()))).append(",'").append(StringHandler.emptyIfNull(ib.getSearchSynonymMode())).append("',");
		query.append(SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(ib.getSearchBillToArgument()))).append(",'").append(StringHandler.emptyIfNull(ib.getSearchBillToMode())).append("',");
		query.append(SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(ib.getSearchShipToArgument()))).append(",'").append(StringHandler.emptyIfNull(ib.getSearchShipToMode())).append("',");
		query.append(personnelBean.getPersonnelId()).append(")) where status = 'A' order by customer_id");
		
        return factory.selectQuery(query.toString());
          
	}

	public Collection searchNewCustomer(String customerId) throws BaseException {

		//SearchCriteria criteria = new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId);
		//criteria.addCriterion("locationType", SearchCriterion.EQUALS,"Bill To");

		return factory.setBean(new CustomerAddRequestViewBean()).select( new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId),null, "customer_details_view");
	}

	public Collection searchNewCustomerContact(String customerId) throws BaseException {

		// 	SearchCriteria criteria = new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId);
		//criteria.addCriterion("locationType", SearchCriterion.EQUALS,"Bill To");

		return factory.setBean(new CustomerContactViewBean()).select( new SearchCriteria("customerId", SearchCriterion.EQUALS,	customerId),null, "CUSTOMER_CONTACT_VIEW");
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

	public Object[] showResult(CustomerAddressSearchViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
		Vector bv = (Vector) this.getSearchResult(inputBean, personnelBean);
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

	public ExcelHandler  getExcelReport(Collection bean, Locale locale) throws BaseException {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<CustomerAddressSearchViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.id","label.customer","label.customerorigin","label.abcclassification","label.billto","label.facility","label.shiptoname",
				"label.shipto","label.synonym"};
		/*This array defines the type of the excel column.
   0 means default depending on the data type. */
		int[] types = {
				
				0,0,0,0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,0};
		/*This array defines the default width of the column when the Excel file opens.
   0 means the width will be default depending on the data type.*/
		int[] widths = {
				7,25,15,7,40,20,40,12};
		/*This array defines the horizontal alignment of the data in a cell.
   0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				pw.ALIGN_CENTER,0,0,pw.ALIGN_CENTER,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		int i = 1;
		for (CustomerAddressSearchViewBean member : data) {
			i++;
			pw.addRow();

			pw.addCell(member.getCustomerId());
			pw.addCell(member.getCustomerName());
			pw.addCell(member.getCustomerOrigin());
			pw.addCell(member.getAbcClassification() == null?"":member.getAbcClassification());
			pw.addCell(member.getBillToAddress());
			pw.addCell(member.getLocationDesc());
			pw.addCell(member.getFacilityName());
			pw.addCell(member.getShipToAddressLine1Display()+" "+
					member.getShipToAddressLine2Display()+" "+
					member.getShipToAddressLine3Display()+" "+
					member.getShipToAddressLine4Display()+" "+
					member.getShipToAddressLine5Display());
			pw.addCell(member.getLegacyCustomerId());
		}
		return pw;
	}

	public Collection <CustomerCarrierInfoBean> getCarrierInfoViewBeanCollection(String customerId) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(),getLocale());

		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerCarrierInfoViewBean());

		Collection c = null;

		StringBuilder tmpQry = new StringBuilder("select  distinct carrier_code, account, notes, carrier_name, inventory_group from CUSTOMER_CARRIER_INFO where lower(STATUS) = lower('ACTIVE') " );

		if(!StringHandler.isBlankString(customerId))
		{
			tmpQry.append("and customer_id =").append(customerId);
		}
		else
		{
			tmpQry.append("and customer_id IS NULL");
		}

		c = factory.selectQuery(tmpQry.toString());

		return c;
	}




}
