package com.tcmis.client.openCustomer.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.openCustomer.beans.UserShiptoAdminBean;
import com.tcmis.client.openCustomer.factory.OpenUserShiptoSelOvBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process for CustomerPagesAction
 * @version 1.0
 *****************************************************************************/
public class ShipToPermissionProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ShipToPermissionProcess(String client) {
		super(client);
	}

	public ShipToPermissionProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getCustomerShiptoDropdowns(BigDecimal userId, BigDecimal personnelId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		OpenUserShiptoSelOvBeanFactory factory = new OpenUserShiptoSelOvBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (null != userId) {
			criteria.addCriterion("userId", SearchCriterion.EQUALS, userId.toString());
		}

		if (null != personnelId) {
			criteria.addCriterion("personnelId", SearchCriterion.EQUALS, personnelId.toString());
		}

		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("customerName");
		return factory.selectObject(criteria, sort);

	}

	public Collection getSearchResult(UserShiptoAdminBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserShiptoAdminBean());

		StringBuilder query = new StringBuilder("select * from table (PKG_OPEN_CUSTOMER.FX_GET_USER_SHIPTO_ADMIN_DATA(");
		query.append(input.getUserId()).append(",").append(input.getPersonnelId()).append(",").append(input.getCustomerId()).append(",'");
		query.append(input.getFacilityId()).append("')) order by facility_name");

		return factory.selectQuery(query.toString());
	}

	public ExcelHandler getExcelReport(Collection<UserShiptoAdminBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		pw.addRow();

		String[] headerkeys = { "label.shipto", "label.access" };

		int[] types = { 0, 0 };

		int[] widths = { 35, 15 };

		int[] horizAligns = { 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for (UserShiptoAdminBean member : data) {

			pw.addRow();
			pw.addCell(member.getFacilityName());
			pw.addCell(member.isFacilityAccess() ? "Y" : "N");
		}
		return pw;
	}

	public String updateAccess(UserShiptoAdminBean input) throws BaseException {
		Collection inArgs = new Vector();

		inArgs.add(blankIfNull(input.getCustomerId()));
		inArgs.add(blankIfNull(input.getFacilityId()));
		inArgs.add(blankIfNull(input.getPersonnelId()));
		inArgs.add(input.isFacilityAccess() ? "Y" : "N");
		inArgs.add(input.isInvoiceGroupMember() ? "Y" : "N");
		inArgs.add(blankIfNull(input.getUserId()));

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Collection coll = procFactory.doProcedure("PKG_OPEN_CUSTOMER.P_SET_USER_FACILITY", inArgs, outArgs);

		String errorMsg = (String) ((Vector) coll).get(0);
		if (errorMsg != null && errorMsg.length() > 0 && !errorMsg.equalsIgnoreCase("ok"))
			return errorMsg;
		return "";
	}

	private Object blankIfNull(Object value) {
		return value == null ? "" : value;
	}
}