package com.tcmis.client.report.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.report.beans.CostReportAddChargeViewBean;


/******************************************************************************
 * CLASSNAME: CostReportAddChargeViewBeanFactory <br>
 * @version: 1.0, Jul 18, 2008 <br>
 *****************************************************************************/


public class CostReportAddChargeViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_COMMODITY = "COMMODITY";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_FACILITY_DISPLAY = "FACILITY_DISPLAY";
	public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_INVOICE_PERIOD_DESC = "INVOICE_PERIOD_DESC";
	public String ATTRIBUTE_INVOICE_LINE = "INVOICE_LINE";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_UNIT_REBATE = "UNIT_REBATE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
	public String ATTRIBUTE_TOTAL_SALES_TAX = "TOTAL_SALES_TAX";
	public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
	public String ATTRIBUTE_PERCENT_SPLIT_CHARGE = "PERCENT_SPLIT_CHARGE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_MR_LINE = "MR_LINE";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_WASTE_ORDER_NO = "WASTE_ORDER_NO";
	public String ATTRIBUTE_WASTE_MANIFEST_ID = "WASTE_MANIFEST_ID";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_PEI_AMOUNT = "PEI_AMOUNT";
	public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CATEGORY_ID = "CATEGORY_ID";
	public String ATTRIBUTE_MFG_ID = "MFG_ID";
	public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_CATEGORY_DESC = "CATEGORY_DESC";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_TYPE_DESC = "TYPE_DESC";
	public String ATTRIBUTE_ADDITIONAL_CHARGE_DESC = "ADDITIONAL_CHARGE_DESC";
	public String ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT = "ADDITIONAL_CHARGE_AMOUNT";
	public String ATTRIBUTE_REFERENCE_NUMBER = "REFERENCE_NUMBER";
	public String ATTRIBUTE_FINANCE_APPROVER_NAME = "FINANCE_APPROVER_NAME";

	//table name
	public String TABLE = "COST_REPORT_ADD_CHARGE_VIEW";


	//constructor
	public CostReportAddChargeViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("invoice")) {
			return ATTRIBUTE_INVOICE;
		}
		else if(attributeName.equals("commodity")) {
			return ATTRIBUTE_COMMODITY;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("facilityDisplay")) {
			return ATTRIBUTE_FACILITY_DISPLAY;
		}
		else if(attributeName.equals("accountSysId")) {
			return ATTRIBUTE_ACCOUNT_SYS_ID;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else if(attributeName.equals("invoicePeriodDesc")) {
			return ATTRIBUTE_INVOICE_PERIOD_DESC;
		}
		else if(attributeName.equals("invoiceLine")) {
			return ATTRIBUTE_INVOICE_LINE;
		}
		else if(attributeName.equals("accountNumber")) {
			return ATTRIBUTE_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("accountNumber2")) {
			return ATTRIBUTE_ACCOUNT_NUMBER2;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("unitRebate")) {
			return ATTRIBUTE_UNIT_REBATE;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("totalAddCharge")) {
			return ATTRIBUTE_TOTAL_ADD_CHARGE;
		}
		else if(attributeName.equals("totalSalesTax")) {
			return ATTRIBUTE_TOTAL_SALES_TAX;
		}
		else if(attributeName.equals("serviceFee")) {
			return ATTRIBUTE_SERVICE_FEE;
		}
		else if(attributeName.equals("percentSplitCharge")) {
			return ATTRIBUTE_PERCENT_SPLIT_CHARGE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("wasteOrderNo")) {
			return ATTRIBUTE_WASTE_ORDER_NO;
		}
		else if(attributeName.equals("wasteManifestId")) {
			return ATTRIBUTE_WASTE_MANIFEST_ID;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("peiAmount")) {
			return ATTRIBUTE_PEI_AMOUNT;
		}
		else if(attributeName.equals("netAmount")) {
			return ATTRIBUTE_NET_AMOUNT;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("invoiceUnitPrice")) {
			return ATTRIBUTE_INVOICE_UNIT_PRICE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("categoryId")) {
			return ATTRIBUTE_CATEGORY_ID;
		}
		else if(attributeName.equals("mfgId")) {
			return ATTRIBUTE_MFG_ID;
		}
		else if(attributeName.equals("mfgDesc")) {
			return ATTRIBUTE_MFG_DESC;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("sourceHub")) {
			return ATTRIBUTE_SOURCE_HUB;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("categoryDesc")) {
			return ATTRIBUTE_CATEGORY_DESC;
		}
		else if(attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("typeDesc")) {
			return ATTRIBUTE_TYPE_DESC;
		}
		else if(attributeName.equals("additionalChargeDesc")) {
			return ATTRIBUTE_ADDITIONAL_CHARGE_DESC;
		}
		else if(attributeName.equals("additionalChargeAmount")) {
			return ATTRIBUTE_ADDITIONAL_CHARGE_AMOUNT;
		}
		else if(attributeName.equals("referenceNumber")) {
			return ATTRIBUTE_REFERENCE_NUMBER;
		}
		else if(attributeName.equals("financeApproverName")) {
			return ATTRIBUTE_FINANCE_APPROVER_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CostReportAddChargeViewBean.class);
	}

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection costReportAddChargeViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportAddChargeViewBean costReportAddChargeViewBean = new CostReportAddChargeViewBean();
			load(dataSetRow, costReportAddChargeViewBean);
			costReportAddChargeViewBeanColl.add(costReportAddChargeViewBean);
		}

		return costReportAddChargeViewBeanColl;
	}

	public Collection select(String query)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(query, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(String query, Connection conn)
		throws BaseException {

		Collection costReportAddChargeViewBeanColl = new Vector();

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportAddChargeViewBean costReportAddChargeViewBean = new CostReportAddChargeViewBean();
			load(dataSetRow, costReportAddChargeViewBean);
			costReportAddChargeViewBeanColl.add(costReportAddChargeViewBean);
		}

		return costReportAddChargeViewBeanColl;
	}
}