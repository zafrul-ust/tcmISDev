package com.tcmis.internal.distribution.process;

import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerReturnTrackingInputBean;
import com.tcmis.internal.distribution.beans.CustomerReturnRequestViewBean;

public class CustomerReturnTrackingProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public CustomerReturnTrackingProcess(String client, Locale locale) {
		super(client, locale);
	}

	@SuppressWarnings("unchecked")
	public Collection<CustomerReturnRequestViewBean> getSearchResult(CustomerReturnTrackingInputBean inputBean, PersonnelBean personnelBean, boolean isLimitSearch) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT v.*");
		query.append(", crr.return_type");

		query.append(", decode(");
		query.append("(SELECT count(*) FROM PURCHASE_REQUEST WHERE pr_number = v.pr_number AND account_sys_id not in ('DIST', 'Haas'))");
		query.append(", 0, 'Y'");
		query.append(", 'N') is_distribution");

		query.append(" FROM CUSTOMER_RETURN_REQUEST_VIEW v, CUSTOMER_RETURN_REQUEST crr");

		query.append(" WHERE v.customer_rma_id = crr.customer_rma_id(+)");

		if(!StringHandler.isBlankString(inputBean.getOpsEntityId())) {
			query.append(" AND v.ops_entity_id = ").append(SqlHandler.delimitString(inputBean.getOpsEntityId()));
		}

		if(!StringHandler.isBlankString(inputBean.getHub())) {
			query.append(" AND v.hub = ").append(SqlHandler.delimitString(inputBean.getHub()));
		}

		if(!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
			query.append(" AND v.inventory_group = ").append(SqlHandler.delimitString(inputBean.getInventoryGroup()));
		} else if (isLimitSearch) {
			StringBuilder subQuery = new StringBuilder("SELECT DISTINCT inventory_group");
			subQuery.append(" FROM user_inventory_group");
			subQuery.append(" WHERE personnel_id = ").append(personnelBean.getPersonnelId());
			if (!StringHandler.isBlankString(personnelBean.getCompanyId())) {
				subQuery.append(" AND company_id = ").append(SqlHandler.delimitString(personnelBean.getCompanyId()));
			}

			query.append(" AND v.inventory_group in (").append(subQuery).append(")");
		}

		if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
			query.append(" AND v.").append(StringHandler.convertBeanPropertyToDatabaseColumn(inputBean.getSearchField()));

			switch (inputBean.getSearchMode()) {
				case "is":
					query.append(" = ").append(SqlHandler.delimitString(inputBean.getSearchArgument()));
					break;
				case "contains":
					query.append(" like ").append(SqlHandler.delimitString("%" + inputBean.getSearchArgument() + "%"));
					break;
				case "startsWith":
					query.append(" like ").append(SqlHandler.delimitString(inputBean.getSearchArgument() + "%"));
					break;
				case "endsWith":
					query.append(" like ").append(SqlHandler.delimitString("%" + inputBean.getSearchArgument()));
					break;
			}
		}

		if(!StringHandler.isBlankString(inputBean.getRmaStatus())) {
			query.append(" AND v.rma_status = ").append(SqlHandler.delimitString(inputBean.getRmaStatus()));
		}

		if ("2".equals(inputBean.getSearchOption()) && inputBean.getDays() != null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, (inputBean.getDays().intValue()) * (-1));

			query.append(" AND v.request_start_date >= ").append(DateHandler.getOracleToDateFunction(cal.getTime()));
		} else if ("1".equals(inputBean.getSearchOption())) {
			query.append(" AND v.rma_status NOT IN ('Complete', 'Draft')");
		}

	return new GenericSqlFactory(new DbManager(getClient(),getLocale())).setBean(new CustomerReturnRequestViewBean()).selectQuery(query.toString());
 }

public ExcelHandler getExcelReport(CustomerReturnTrackingInputBean inputBean, PersonnelBean personnelBean) throws
  NoDataException, BaseException, Exception {
	Collection<CustomerReturnRequestViewBean> data = getSearchResult(inputBean, personnelBean, true);
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

	ExcelHandler pw = new ExcelHandler(library);

pw.addTable();
//write column headers
pw.addRow();
/*Pass the header keys for the Excel.*/

String[] headerkeys = {
"label.rma","label.csr","label.operatingentity","label.inventorygroup","label.customer","label.customerpoline", "label.mrline","label.customerpn",
"label.ourpn","label.description","label.returnquantityrequested","label.returnquantityauthorized", "label.currencyid","label.extprice",
"label.status","label.reason","label.comments"};
/*This array defines the type of the excel column.
0 means default depending on the data type. */
int[] types = {
0,0,0,0,0,0,0,
0,0,0,0,0,0,0,
0,0,0};

int[] widths = {
	      0,20,20,20,20,18,0,10,
	      0,18,12,12,0,0,
	      15,20,20};

/*This array defines the horizontal alignment of the data in a cell.
0 means excel defaults the horizontal alignemnt by the data type.*/

pw.applyColumnHeader(headerkeys, types, widths, null );

pw.setColumnDigit(13,2);
// now write data
//int i = 1;
for (CustomerReturnRequestViewBean member : data) {

  pw.addRow();

  pw.addCell(member.getCustomerRmaId());
  pw.addCell(member.getCsrName());
  pw.addCell(member.getOpsEntityName());
  pw.addCell(member.getInventoryGroupName());
  pw.addCell(member.getCustomerName());
  if (member.getPoNumber() == null) member.setPoNumber("");
  if (member.getReleaseNumber() == null) member.setReleaseNumber("");
  pw.addCell(member.getPoNumber()+"-"+member.getReleaseNumber());
  pw.addCell(member.getPrNumber()+"-"+member.getLineItem());
  pw.addCell(member.getFacPartNo());
  pw.addCell(member.getHaasPartNo());
  pw.addCell(member.getPartDescription());
  pw.addCell(member.getQuantityReturnRequested());
  pw.addCell(member.getQuantityReturnAuthorized());
  pw.addCell(member.getCurrencyId());
  if(member.getQuantityReturnAuthorized() == null || member.getUnitPrice() == null)
	  pw.addCell("");
  else
	  pw.addCell(member.getQuantityReturnAuthorized().multiply(member.getUnitPrice()));
  pw.addCell(member.getRmaStatus());
  pw.addCell(member.getReasonDescription());
  pw.addCell(member.getReturnNotes());
}
return pw;
}



}
