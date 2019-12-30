package com.tcmis.client.report.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.*;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.Hashtable;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.report.beans.CostReportViewBean;
import com.tcmis.client.report.beans.CostReportInputBean;
import com.tcmis.client.report.beans.CostReportTotalPerCurrencyBean;
import com.tcmis.client.report.beans.CostReportExcelViewBean;


/******************************************************************************
 * CLASSNAME: CostReportViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CostReportViewBeanFactory extends BaseBeanFactory {

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
	public String ATTRIBUTE_WASTE_ORDER_NO = "WASTE_ORDER_NO";
	public String ATTRIBUTE_WASTE_MANIFEST_ID = "WASTE_MANIFEST_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
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
	public String ATTRIBUTE_REFERENCE_NUMBER = "REFERENCE_NUMBER";
	public String ATTRIBUTE_FINANCE_APPROVER_NAME = "FINANCE_APPROVER_NAME";
	public String ATTRIBUTE_TOTAL_REBATE = "TOTAL_REBATE";
	public String ATTRIBUTE_VOUCHER_URL = "VOUCHER_URL";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_TOTAL_FREIGHT_CHARGE = "TOTAL_FREIGHT_CHARGE";
	public String ATTRIBUTE_ACCOUNT_NUMBER3 = "ACCOUNT_NUMBER3";
	public String ATTRIBUTE_ACCOUNT_NUMBER4 = "ACCOUNT_NUMBER4";
	public String ATTRIBUTE_CUSTOMER_INVOICE_NO = "CUSTOMER_INVOICE_NO";
	public String ATTRIBUTE_CUSTOMER_PART_NO = "CUSTOMER_PART_NO";
	public String ATTRIBUTE_SHIPPING_REFERENCE = "SHIPPING_REFERENCE";
	public String ATTRIBUTE_CAT_PART_ATTRIBUTE = "CAT_PART_ATTRIBUTE";
	public String ATTRIBUTE_QUALITY_ID = "QUALITY_ID";

	//table name
	public String TABLE = "COST_REPORT_VIEW";


	//constructor
	public CostReportViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("wasteOrderNo")) {
			return ATTRIBUTE_WASTE_ORDER_NO;
		}
		else if(attributeName.equals("wasteManifestId")) {
			return ATTRIBUTE_WASTE_MANIFEST_ID;
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
		else if(attributeName.equals("referenceNumber")) {
			return ATTRIBUTE_REFERENCE_NUMBER;
		}
		else if(attributeName.equals("financeApproverName")) {
			return ATTRIBUTE_FINANCE_APPROVER_NAME;
		}
		else if(attributeName.equals("totalRebate")) {
			return ATTRIBUTE_TOTAL_REBATE;
		}
		else if(attributeName.equals("voucherUrl")) {
			return ATTRIBUTE_VOUCHER_URL;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else if(attributeName.equals("totalFreightCharge")) {
			return ATTRIBUTE_TOTAL_FREIGHT_CHARGE;
		}
		else if(attributeName.equals("accountNumber3")) {
			return ATTRIBUTE_ACCOUNT_NUMBER3;
		}
		else if(attributeName.equals("accountNumber4")) {
			return ATTRIBUTE_ACCOUNT_NUMBER4;
		}
		else if(attributeName.equals("customerInvoiceNo")) {
			return ATTRIBUTE_CUSTOMER_INVOICE_NO;
		}
		else if(attributeName.equals("customerPartNo")) {
			return ATTRIBUTE_CUSTOMER_PART_NO;
		}
		else if(attributeName.equals("shippingReference")) {
			return ATTRIBUTE_SHIPPING_REFERENCE;
		}
		else if(attributeName.equals("catPartAttribute")) {
			return ATTRIBUTE_CAT_PART_ATTRIBUTE;
		}
		else if(attributeName.equals("qualityId")) {
			return ATTRIBUTE_QUALITY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CostReportViewBean.class);
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

		Collection costReportViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportViewBean costReportViewBean = new CostReportViewBean();
			load(dataSetRow, costReportViewBean);
			costReportViewBeanColl.add(costReportViewBean);
		}

		return costReportViewBeanColl;
	}

	public Collection selectExcel(String query, CostReportInputBean inputBean)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectExcel(query,inputBean,connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectExcel(String query, CostReportInputBean inputBean, Connection conn)
		throws BaseException {
		//log.debug("start");
		Collection costReportViewBeanColl = new Vector();
		Collection costReportTotalPerCurrency = new Vector();
		try {
			Statement sqlStatement = conn.createStatement();
      	ResultSet sqlResult = sqlStatement.executeQuery(query);
			
      	if (log.isDebugEnabled()) {
      		log.debug("query done:"+query);
		}
      	
			//get resultset metadata
      	ResultSetMetaData rsmd = sqlResult.getMetaData();
			int[] columnType = new int[rsmd.getColumnCount()];
			Method[] writerMethodArray = new Method[rsmd.getColumnCount()];
			CostReportViewBean costReportViewBean = new CostReportViewBean();
			BeanInfo beanInfo = Introspector.getBeanInfo(costReportViewBean.getClass());
      	PropertyDescriptor[] pdesc = beanInfo.getPropertyDescriptors();
			int count = 0;
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				columnType[count] = rsmd.getColumnType(i);
				int beanColumnIndex = -1;
				for (int j = 0; j < pdesc.length; j++) {
					if (rsmd.getColumnName(i).equalsIgnoreCase(getColumnName(pdesc[j].getName()))) {
						beanColumnIndex = j;
						break;
					}
				}
				if (beanColumnIndex == -1) {
					//column in view but not in bean
					writerMethodArray[count] = null;
				}else {
					writerMethodArray[count] = pdesc[beanColumnIndex].getWriteMethod();
				}
				count++;
			}
			//log.debug("metadata done");
			while (sqlResult.next()) {
				CostReportViewBean bean = new CostReportViewBean();
				loadNew(sqlResult,bean,columnType,writerMethodArray);
				costReportViewBeanColl.add(bean);

				//summing up the total per currency
				if (inputBean.getSqlFields().contains("total_freight_charge") || inputBean.getSqlFields().contains("total_add_charge") || inputBean.getSqlFields().contains("total_sales_tax") ||
					 inputBean.getSqlFields().contains("service_fee") || inputBean.getSqlFields().contains("pei_amount") ||
					 inputBean.getSqlFields().contains("net_amount") || inputBean.getSqlFields().contains("total_rebate")) {
					getTotalPerCurrency(costReportTotalPerCurrency,bean);
				}
			}

			//log.debug("done");
		}catch (Exception e) {
			e.printStackTrace();
		}

		//add costReportTotalPerCurrency to input bean
		inputBean.setTotalPerCurrency(costReportTotalPerCurrency);
		//log.debug("end:"+rowCount);
		return costReportViewBeanColl;
	}

	public Collection select(String query, CostReportInputBean inputBean)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(query,inputBean,connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(String query, CostReportInputBean inputBean, Connection conn)
		throws BaseException {
		if (log.isDebugEnabled()) {
			 log.debug("start query:"+query);
		}
		
		Collection costReportViewBeanColl = new Vector();
		Collection costReportTotalPerCurrency = new Vector();
		int rowCount = 0;
		try {
			Statement sqlStatement = conn.createStatement();
      	ResultSet sqlResult = sqlStatement.executeQuery(query);
      		
      	if (log.isDebugEnabled()) {
      		log.debug("done");
		}
			//get resultset metadata
      	ResultSetMetaData rsmd = sqlResult.getMetaData();
			int[] columnType = new int[rsmd.getColumnCount()];
			Method[] writerMethodArray = new Method[rsmd.getColumnCount()];
			CostReportViewBean costReportViewBean = new CostReportViewBean();
			BeanInfo beanInfo = Introspector.getBeanInfo(costReportViewBean.getClass());
      	PropertyDescriptor[] pdesc = beanInfo.getPropertyDescriptors();
			int count = 0;
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				columnType[count] = rsmd.getColumnType(i);
				int beanColumnIndex = -1;
				for (int j = 0; j < pdesc.length; j++) {
					if (rsmd.getColumnName(i).equalsIgnoreCase(getColumnName(pdesc[j].getName()))) {
						beanColumnIndex = j;
						break;
					}
				}
				if (beanColumnIndex == -1) {
					//column in view but not in bean
					writerMethodArray[count] = null;
				}else {
					writerMethodArray[count] = pdesc[beanColumnIndex].getWriteMethod();
				}
				count++;
			}
			//log.debug("metadata done");
			Vector tmpV = new Vector(inputBean.getReportFields());
			int additionalWhereIndex = tmpV.indexOf("whereClauseForLink");
			int additionalFieldSize = inputBean.getAdditionalFields().size();
			while (sqlResult.next()) {
				CostReportViewBean bean = new CostReportViewBean();
				Vector columnDataColl = new Vector();
				loadNew(sqlResult,bean,columnType,writerMethodArray,columnDataColl);
				//adding where clause for drill down
				if (additionalWhereIndex > -1) {
					columnDataColl.insertElementAt(buildingWhereClauseForLink(inputBean,bean),additionalWhereIndex);
				}
				//removing additional fields
				for (int ii = 0; ii < additionalFieldSize; ii++) {
					columnDataColl.removeElementAt(columnDataColl.size()-1);
				}

				costReportViewBeanColl.add(columnDataColl);

				//summing up the total per currency
				if (inputBean.getSqlFields().contains("total_freight_charge") || inputBean.getSqlFields().contains("total_add_charge") || inputBean.getSqlFields().contains("total_sales_tax") ||
					 inputBean.getSqlFields().contains("service_fee") || inputBean.getSqlFields().contains("pei_amount") ||
					 inputBean.getSqlFields().contains("net_amount") || inputBean.getSqlFields().contains("total_rebate")) {
					getTotalPerCurrency(costReportTotalPerCurrency,bean);
				}
				rowCount++;
			}

			//log.debug("done");
		}catch (Exception e) {
			e.printStackTrace();
		}

		//add costReportTotalPerCurrency to input bean
		inputBean.setTotalPerCurrency(costReportTotalPerCurrency);
		//log.debug("end:"+rowCount);
		return costReportViewBeanColl;
	}

	private void getColumnsType(Connection conn,String query, CostReportInputBean inputBean) {
		//log.debug("get type start");
		try {
			Statement sqlStatement = conn.createStatement();
			//get resultset
			ResultSet sqlResult = sqlStatement.executeQuery(query);
			//get resultset metadata
			ResultSetMetaData rsmd = sqlResult.getMetaData();
			Collection columnTypes = new Vector();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			  switch (rsmd.getColumnType(i)) {
				 case Types.VARCHAR:
					columnTypes.add("String");
					break;
				 case Types.NUMERIC:
					columnTypes.add("BigDecimal");
				 	break;
				 case Types.DATE:
					columnTypes.add("Date");
					break;
				 case Types.ARRAY:
					columnTypes.add("Array");
					break;
				 default:
					columnTypes.add("String");
					;
			  }
			}
			inputBean.setColumnTypes(columnTypes);
		}catch (SQLException sqlE) {
		 	sqlE.printStackTrace();
		}
		//log.debug("get type end");
	}

	private String buildingWhereClauseForLink(CostReportInputBean inputBean, CostReportViewBean costReportViewBean) {
		String result = "";
		Object[] columnNames = inputBean.getSqlFields().toArray();
		for (int i = 0; i < columnNames.length; i++) {
			String[] tmpName = columnNames[i].toString().split(" ");	
			String column = tmpName[tmpName.length-1];
			if ("invoice".equalsIgnoreCase(column) || "customer_invoice_no".equalsIgnoreCase(column) || "invoice_type".equalsIgnoreCase(column) || "invoice_date".equalsIgnoreCase(column) ||
				 "invoice_period".equalsIgnoreCase(column) ||
				 "invoice_line".equalsIgnoreCase(column) || "account_sys_id".equalsIgnoreCase(column) || "account_number".equalsIgnoreCase(column) ||
			 	 "account_number2".equalsIgnoreCase(column) || "po_number".equalsIgnoreCase(column) || "mr_line".equalsIgnoreCase(column) ||
			 	 "waste_order_no".equalsIgnoreCase(column) || "waste_manifest_id".equalsIgnoreCase(column) || "cat_part_no".equalsIgnoreCase(column) ||
			 	 "type_desc".equalsIgnoreCase(column) || "item_id".equalsIgnoreCase(column) || "mfg_id".equalsIgnoreCase(column) || "radian_po".equalsIgnoreCase(column) ||
			 	 "reference_number".equalsIgnoreCase(column) || "receipt_id".equalsIgnoreCase(column) ||
			 	 "customer_part_no".equalsIgnoreCase(column) || "shipping_reference".equalsIgnoreCase(column) ) {
				try {
					if(!StringHandler.isBlankString(BeanHandler.getField(costReportViewBean,column))) {
						if ("invoice_date".equalsIgnoreCase(column)) {
							result += column+"= to_date('"+BeanHandler.getField(costReportViewBean,column)+"','mm/dd/yyyy') and ";
						}else {
							result += column+"= '"+BeanHandler.getField(costReportViewBean,column)+"' and ";
						}
					}
				}catch (Exception e) {

				}
			}
		}
		//remove last "and ";
		if (result.length() > 4) {
			result = result.substring(0,result.length()-4);
		}
		return result;
	}

	//summing up the total per currency
	//for: total_add_charge, total_sales_tax, service_fee, pei_amount, net_amount, total_rebate (material_savings)
	private void getTotalPerCurrency (Collection costReportTotalPerCurrency, CostReportViewBean bean) {
		if (costReportTotalPerCurrency.size() == 0) {
			//first time
			CostReportTotalPerCurrencyBean totalBean = new CostReportTotalPerCurrencyBean();
			totalBean.setCurrencyId(bean.getCurrencyId());
			if(bean.getTotalFreightCharge() != null) {
				totalBean.setTotalFreightCharge(bean.getTotalFreightCharge());
			}
			if(bean.getTotalAddCharge() != null) {
				totalBean.setTotalAddCharge(bean.getTotalAddCharge());
			}
			if(bean.getTotalSalesTax() != null) {
				totalBean.setTotalSalesTax(bean.getTotalSalesTax());
			}
			if(bean.getServiceFee() != null) {
				totalBean.setTotalServiceFee(bean.getServiceFee());
			}
			if(bean.getPeiAmount() != null) {
				totalBean.setTotalPeiAmount(bean.getPeiAmount());
			}
			if(bean.getNetAmount() != null) {
				totalBean.setTotalNetAmount(bean.getNetAmount());
			}
			if(bean.getTotalRebate() != null) {
				totalBean.setTotalMaterialSaving(bean.getTotalRebate());
			}
			costReportTotalPerCurrency.add(totalBean);
		}else {
			Iterator iter = costReportTotalPerCurrency.iterator();
			boolean currencyExist = false;
			while (iter.hasNext()) {
				CostReportTotalPerCurrencyBean totalBean = (CostReportTotalPerCurrencyBean)iter.next();
				//data existed
				if (bean.getCurrencyId().equals(totalBean.getCurrencyId())) {
					BigDecimal tmpTotal;
					if (bean.getTotalFreightCharge() != null) {
						tmpTotal = totalBean.getTotalFreightCharge();
						if (tmpTotal == null) {
							totalBean.setTotalFreightCharge(bean.getTotalFreightCharge());
						}else {
							totalBean.setTotalFreightCharge(tmpTotal.add(bean.getTotalFreightCharge()));
						}
					}
					if (bean.getTotalAddCharge() != null) {
						tmpTotal = totalBean.getTotalAddCharge();
						if (tmpTotal == null) {
							totalBean.setTotalAddCharge(bean.getTotalAddCharge());
						}else {
							totalBean.setTotalAddCharge(tmpTotal.add(bean.getTotalAddCharge()));
						}
					}
					if (bean.getTotalSalesTax() != null) {
						tmpTotal = totalBean.getTotalSalesTax();
						if (tmpTotal == null) {
							totalBean.setTotalSalesTax(bean.getTotalSalesTax());
						}else {
							totalBean.setTotalSalesTax(tmpTotal.add(bean.getTotalSalesTax()));
						}
					}
					if (bean.getServiceFee() != null) {
						tmpTotal = totalBean.getTotalServiceFee();
						if (tmpTotal == null) {
							totalBean.setTotalServiceFee(bean.getServiceFee());
						}else {
							totalBean.setTotalServiceFee(tmpTotal.add(bean.getServiceFee()));
						}
					}
					if (bean.getPeiAmount() != null) {
						tmpTotal = totalBean.getTotalPeiAmount();
						if (tmpTotal == null) {
							totalBean.setTotalPeiAmount(bean.getPeiAmount());
						}else {
							totalBean.setTotalPeiAmount(tmpTotal.add(bean.getPeiAmount()));
						}
					}
					if (bean.getNetAmount() != null) {
						tmpTotal = totalBean.getTotalNetAmount();
						if (tmpTotal == null) {
							totalBean.setTotalNetAmount(bean.getNetAmount());
						}else {
							totalBean.setTotalNetAmount(tmpTotal.add(bean.getNetAmount()));
						}
					}
					if (bean.getTotalRebate() != null) {
						tmpTotal = totalBean.getTotalMaterialSaving();
						if (tmpTotal == null) {
							totalBean.setTotalMaterialSaving(bean.getTotalRebate());
						}else {
							totalBean.setTotalMaterialSaving(tmpTotal.add(bean.getTotalRebate()));
						}
					}
					currencyExist = true;
				} //end of data existed
			} //end of while loop
			
			//currency id does not exist in data
			if (!currencyExist) {
				CostReportTotalPerCurrencyBean totalBean = new CostReportTotalPerCurrencyBean();
				totalBean.setCurrencyId(bean.getCurrencyId());
				if(bean.getTotalFreightCharge() != null) {
					totalBean.setTotalFreightCharge(bean.getTotalFreightCharge());
				}
				if(bean.getTotalAddCharge() != null) {
					totalBean.setTotalAddCharge(bean.getTotalAddCharge());
				}
				if(bean.getTotalSalesTax() != null) {
					totalBean.setTotalSalesTax(bean.getTotalSalesTax());
				}
				if(bean.getServiceFee() != null) {
					totalBean.setTotalServiceFee(bean.getServiceFee());
				}
				if(bean.getPeiAmount() != null) {
					totalBean.setTotalPeiAmount(bean.getPeiAmount());
				}
				if(bean.getNetAmount() != null) {
					totalBean.setTotalNetAmount(bean.getNetAmount());
				}
				if(bean.getTotalRebate() != null) {
					totalBean.setTotalMaterialSaving(bean.getTotalRebate());
				}
				costReportTotalPerCurrency.add(totalBean);
			}
		} //end of not first time

	} //end of method

} //end of class