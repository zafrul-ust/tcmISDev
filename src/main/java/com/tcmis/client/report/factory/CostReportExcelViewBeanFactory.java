package com.tcmis.client.report.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.client.report.beans.CostReportInputBean;
import com.tcmis.client.report.beans.CostReportExcelViewBean;
import com.tcmis.client.report.beans.CostReportTotalPerCurrencyBean;


/******************************************************************************
 * CLASSNAME: CostReportExcelViewBeanFactory <br>
 * @version: 1.0, Feb 10, 2009 <br>
 *****************************************************************************/


public class CostReportExcelViewBeanFactory extends BaseBeanFactory {

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
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_END_DATE = "END_DATE";
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
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH = "UNIT_OF_SALE_QUANTITY_PER_EACH";
	public String ATTRIBUTE_ITEM_UOM = "ITEM_UOM";

	//table name
	public String TABLE = "COST_REPORT_VIEW";


	//constructor
	public CostReportExcelViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("unitOfSaleQuantityPerEach")) {
			return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
		}
		else if(attributeName.equals("itemUom")) {
			return ATTRIBUTE_ITEM_UOM;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CostReportExcelViewBean.class);
	}

	//select
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

		Collection costReportExcelViewBeanColl = new Vector();
		Collection costReportTotalPerCurrency = new Vector();
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportExcelViewBean costReportExcelViewBean = new CostReportExcelViewBean();
			load(dataSetRow, costReportExcelViewBean);
			costReportExcelViewBeanColl.add(costReportExcelViewBean);

			//summing up the total per currency
			if (inputBean.getSqlFields().contains("total_add_charge") || inputBean.getSqlFields().contains("total_sales_tax") ||
				 inputBean.getSqlFields().contains("service_fee") || inputBean.getSqlFields().contains("pei_amount") ||
				 inputBean.getSqlFields().contains("net_amount") || inputBean.getSqlFields().contains("total_rebate")) {
				getTotalPerCurrency(costReportTotalPerCurrency,costReportExcelViewBean);
			}
		}
		//add costReportTotalPerCurrency to input bean
		inputBean.setTotalPerCurrency(costReportTotalPerCurrency);
		return costReportExcelViewBeanColl;
	}

	//summing up the total per currency
	//for: total_add_charge, total_sales_tax, service_fee, pei_amount, net_amount, total_rebate (material_savings)
	private void getTotalPerCurrency (Collection costReportTotalPerCurrency, CostReportExcelViewBean bean) {
		if (costReportTotalPerCurrency.size() == 0) {
			//first time
			CostReportTotalPerCurrencyBean totalBean = new CostReportTotalPerCurrencyBean();
			totalBean.setCurrencyId(bean.getCurrencyId());
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
}