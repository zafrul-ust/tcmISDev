package com.tcmis.internal.distribution.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.ContactLookUpInputBean;
import com.tcmis.internal.distribution.beans.CustomerContactSearchViewBean;
/******************************************************************************
 * Process for CustomerAddressSearchView
 * @version 1.0
 *****************************************************************************/
public class ContactLookUpProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	public ContactLookUpProcess(String client) {
		super(client);
	}

	public ContactLookUpProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getSearchResult(ContactLookUpInputBean bean) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerContactSearchViewBean());
		SearchCriteria criteria = new SearchCriteria();

		if ( !isBlank(bean.getSearchContactArgument()) )
			criteria.addCriterionWithMode("contactName", bean.getSearchContactMode(), bean.getSearchContactArgument());

		if ( !isBlank(bean.getSearchNameArgument()) )
			criteria.addCriterionWithMode("customerName", bean.getSearchNameMode(), bean.getSearchNameArgument());

		if ( !isBlank(bean.getSearchPhoneArgument()) )
			criteria.addCriterionWithMode("phone", bean.getSearchPhoneMode(), bean.getSearchPhoneArgument());


		if ( !isBlank(bean.getSearchCustomerIdArgument()) )
			criteria.addCriterion("customerId", SearchCriterion.EQUALS,	bean.getSearchCustomerIdArgument());
		//		criteria.addCriterionWithMode("customerId", bean.getSearchCustomerIdMode(), bean.getSearchCustomerIdArgument());

		if ( !isBlank(bean.getSearchEmailArgument()) )
			criteria.addCriterionWithMode("email", bean.getSearchEmailMode(), bean.getSearchEmailArgument());

		if ( !isBlank(bean.getSearchSynonymArgument() ) )
			criteria.addCriterionWithMode("legacyCustomerId", bean.getSearchSynonymMode(), bean.getSearchSynonymArgument());

		if ( !("Y").equals(bean.getShowinactive()))
			criteria.addCriterion("status", SearchCriterion.EQUALS, "ACTIVE", SearchCriterion.IGNORE_CASE );

		SortCriteria sort = new SortCriteria();

		return factory.select(criteria,sort, "customer_contact_search_view");
	}

	/*
   public Object[] showResult(ContactLookUpInputBean inputBean) throws BaseException {
		Vector bv = (Vector) this.getSearchResult(inputBean);
		HashMap m1 = new HashMap();
		Integer i1 = null;
		BigDecimal customerId = null;

		CustomerContactSearchViewBean bean = null;
		for (int i = 0; i < bv.size(); i++) {
			bean = (CustomerContactSearchViewBean) bv.get(i);
			customerId = bean.getCustomerId();

			if (m1.get(customerId) == null) {
				i1 = new Integer(0);
				m1.put(customerId, i1);
			}
			i1 = (Integer) m1.get(customerId);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(customerId, i1);
		}

		Object[] objs = {bv,m1};
		return objs;
	}
	 */
	public ExcelHandler  getExcelReport(Collection bean, Locale locale) throws NoDataException, BaseException {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<CustomerContactSearchViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.customername","label.customerid","label.abcclassification","label.name","label.phone","label.email",
				"label.contacttype","label.otherjobfunctions","label.billtoaddress","label.synonym"};
		/*This array defines the type of the excel column.
   0 means default depending on the data type. */
		int[] types = {
				0,0,0,0,0,0,
				0,0,pw.TYPE_PARAGRAPH,0};
		/*This array defines the default width of the column when the Excel file opens.
   0 means the width will be default depending on the data type.*/
		int[] widths = {
				35,12,15,20,14,20,
				14,20,0,12};
		/*This array defines the horizontal alignment of the data in a cell.
   0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				0,0,0,0,0,0,
				0,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		int i = 1;
		for (CustomerContactSearchViewBean member : data) {
			i++;
			pw.addRow();
			String otherJobFunctions = "";
			if (log.isDebugEnabled()) {
				log.debug(otherJobFunctions.length());
			}
			if (("Y").equals(member.getPurchasing())) {
				if (otherJobFunctions.length() == 0)
					otherJobFunctions = library.getString("label.purchasing");
				else
					otherJobFunctions = otherJobFunctions + "," + library.getString("label.purchasing");
			}

			if (("Y").equals(member.getAccountsPayable())) {
				if (otherJobFunctions.length() == 0)
					otherJobFunctions = library.getString("label.accountspayable");
				else
					otherJobFunctions = otherJobFunctions + "," + library.getString("label.accountspayable");
			}

			if (("Y").equals(member.getReceiving())) {
				if (otherJobFunctions.length() == 0)
					otherJobFunctions = library.getString("label.receiving");
				else
					otherJobFunctions = otherJobFunctions + "," + library.getString("label.receiving");
			}

			if (("Y").equals(member.getQualityAssurance())) {
				if (otherJobFunctions.length() == 0)
					otherJobFunctions = library.getString("label.qualityassurance");
				else
					otherJobFunctions = otherJobFunctions + "," + library.getString("label.qualityassurance");
			}

			if (("Y").equals(member.getManagement())) {
				if (otherJobFunctions.length() == 0)
					otherJobFunctions = library.getString("label.management");
				else
					otherJobFunctions = otherJobFunctions + "," + library.getString("label.management");
			}

			pw.addCell(member.getCustomerName());
			pw.addCell(member.getCustomerId());
			pw.addCell(member.getAbcClassification() == null?"":member.getAbcClassification());
			pw.addCell(member.getLastName()+","+member.getFirstName());
			pw.addCell(member.getPhone());
			pw.addCell(member.getEmail());
			pw.addCell(member.getContactType());
			pw.addCell(otherJobFunctions);
			pw.addCell(member.getAddressLine1Display()+" "+member.getAddressLine2Display()+" "+member.getAddressLine3Display()+" "+member.getAddressLine4Display());
			pw.addCell(member.getLegacyCustomerId());
		}
		return pw;
	}

}
