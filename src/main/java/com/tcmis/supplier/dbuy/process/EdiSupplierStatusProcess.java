package com.tcmis.supplier.dbuy.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.DbuyInputBean;
import com.tcmis.supplier.dbuy.beans.DbuyAcknowledgementBean;
import com.tcmis.supplier.dbuy.factory.DbuyAcknowledgementBeanFactory;

/******************************************************************************
 * Process for allocation analysis
 * 
 * @version 1.0
 *****************************************************************************/
public class EdiSupplierStatusProcess extends GenericProcess {
	Log		log	= LogFactory.getLog(this.getClass());
	Long	l	= new Long(1);

	public EdiSupplierStatusProcess(String client) {
		super(client);
	}

	public EdiSupplierStatusProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection<DbuyAcknowledgementBean> getSearchResult(DbuyInputBean input, PersonnelBean user) throws BaseException {
		DbuyAcknowledgementBeanFactory dbuyAcknowledgementBeanFactory = new DbuyAcknowledgementBeanFactory(new DbManager(getClient(), getLocale()));

		SearchCriteria criteria = new SearchCriteria();

		if (!StringHandler.isBlankString(input.getCompanyId()))
			criteria.addCriterion("supplierName", SearchCriterion.EQUALS, input.getCompanyId());

		if (!StringHandler.isBlankString(input.getSearchArgument())) {
			criteria.addCriterionWithMode(input.getSearchField(), input.getSearchMode(), input.getSearchArgument());
		}

		if (input.getDateInsertedFromDate() != null) {
			criteria.addCriterion("dateInserted", SearchCriterion.FROM_DATE, input.getDateInsertedFromDate());
		}

		if (input.getDateInsertedToDate() != null) {
			criteria.addCriterion("dateInserted", SearchCriterion.TO_DATE, input.getDateInsertedToDate());
		}

		if (!"Y".equalsIgnoreCase(input.getShowConfirmedPOs())) {
			criteria.addCriterion("done", SearchCriterion.EQUALS, "N");
			criteria.addCriterion("matched", SearchCriterion.EQUALS, "N");
		}

		return dbuyAcknowledgementBeanFactory.select(criteria);
	}

	public String resendCorrectedPo(BigDecimal resendPoNo, PersonnelBean user) throws BaseException, SQLException {

		Connection conn = null;
		String errorMsg = null;
		String newPO = null;
		try {

			if (dbManager == null)
				dbManager = new DbManager(getClient(), getLocale());
			if (factory == null)
				factory = new GenericSqlFactory(dbManager);
			conn = dbManager.getConnection();
			conn.setAutoCommit(false);

			newPO = factory.selectSingleValue("select po_seq.NEXTVAL from dual", conn);
			StringBuilder query = new StringBuilder(
					"INSERT INTO po (BPO,BRANCH_PLANT,BUYER,BUYER_COMPANY_ID,CARRIER,CARRIER_SERVICE_TYPE,CONSIGNED_PO,CRITICAL,CUSTOMER_PO,DATE_ACCEPTED,DATE_ACKNOWLEDGEMENT,");
			query.append(
					"DATE_CREATED,DATE_SENT,DBUY_CONTRACT_ID,DBUY_STATUS,DBUY_STATUS_SET_DATE,DBUY_USER_ID,DELIVERY_INSTRUCTION,EDI_DOCUMENT_CONTROL_NUMBER,EDI_INTERCHANGE_CONTROL_NUMBER,EDI_TRADING_PARTNER_ID,");
			query.append(
					"END_USER,FINANCIAL_APPROVAL_REQUIRED,FINANCIAL_APPROVAL_STATUS,FINANCIAL_REVIEWER,FINANCIAL_REVIEWER_DATE,FINANCIAL_REVIEWER_EMAIL_SENT,FOLLOWUP_EMAILS,FREIGHT_ON_BOARD,INVENTORY_GROUP,");
			query.append(
					"PAYMENT_TERMS,QUALIFICATION_LEVEL,RADIAN_PO,SHIP_TO_COMPANY_ID,SHIP_TO_DELIVERY_POINT,SHIP_TO_FACILITY_ID,SHIP_TO_LOCATION_ID,SUPPLIER,SUPPLIER_CONTACT_ID,TERMS_AND_CONDITIONS) ");
			query.append(
					"SELECT p.BPO,p.BRANCH_PLANT,p.BUYER,p.BUYER_COMPANY_ID,p.CARRIER,p.CARRIER_SERVICE_TYPE,p.CONSIGNED_PO,p.CRITICAL,p.CUSTOMER_PO,p.DATE_ACCEPTED,p.DATE_ACKNOWLEDGEMENT,p.DATE_CREATED,");
			query.append(
					"p.DATE_SENT,p.DBUY_CONTRACT_ID,'NEW',p.DBUY_STATUS_SET_DATE,p.DBUY_USER_ID,p.DELIVERY_INSTRUCTION,p.EDI_DOCUMENT_CONTROL_NUMBER,p.EDI_INTERCHANGE_CONTROL_NUMBER,p.EDI_TRADING_PARTNER_ID,");
			query.append(
					"p.END_USER,p.FINANCIAL_APPROVAL_REQUIRED,p.FINANCIAL_APPROVAL_STATUS,p.FINANCIAL_REVIEWER,p.FINANCIAL_REVIEWER_DATE,p.FINANCIAL_REVIEWER_EMAIL_SENT,p.FOLLOWUP_EMAILS,p.FREIGHT_ON_BOARD,p.INVENTORY_GROUP");
			query.append(",p.PAYMENT_TERMS,p.QUALIFICATION_LEVEL,").append(newPO)
					.append(",p.SHIP_TO_COMPANY_ID,p.SHIP_TO_DELIVERY_POINT,p.SHIP_TO_FACILITY_ID,p.SHIP_TO_LOCATION_ID,p.SUPPLIER,p.SUPPLIER_CONTACT_ID,p.TERMS_AND_CONDITIONS FROM po p WHERE p.radian_po = ")
					.append(resendPoNo);
			String supplier = factory.selectSingleValue(new StringBuilder("select supplier from po where radian_po=").append(resendPoNo).toString(), conn);
			factory.deleteInsertUpdate(query.toString(), conn);
			factory.deleteInsertUpdate(new StringBuilder("update PO_LINE_SPEC_DRAFT set radian_po = ").append(newPO).append(" where radian_po = ").append(resendPoNo).toString(),
					conn);
			factory.deleteInsertUpdate(new StringBuilder("update PO_LINE_FLOW_DOWN_DRAFT set radian_po = ").append(newPO).append(" where radian_po = ").append(resendPoNo).toString(),
					conn);
			factory.deleteInsertUpdate(new StringBuilder("update PO_LINE_DRAFT set radian_po = ").append(newPO).append(" where radian_po = ").append(resendPoNo).toString(), conn);
			factory.deleteInsertUpdate(new StringBuilder("update po set dbuy_status = 'REJECTED' where radian_po = ").append(resendPoNo).toString(), conn);
			factory.deleteInsertUpdate(new StringBuilder("UPDATE buy_order SET BUYER_COMPANY_ID = 'Radian',RADIAN_PO = ").append(newPO).append(",PO_DETACH_BY = ")
					.append(user.getPersonnelIdBigDecimal()).append(",PO_IN_JDE = 'n',DATE_CHANGED = SYSDATE, status='ProblemDBuy', COMMENTS=COMMENTS || ' This BO was reassigned by ")
					.append(user.getFullName()).append(" to PO ").append(newPO).append(" and sent via Dbuy Supply path to Supplier ").append(supplier)
					.append(" '  WHERE radian_po = ").append(resendPoNo).toString(), conn);
			factory.deleteInsertUpdate(new StringBuilder("update dbuy_acknowledgement set done = 'Y' where RADIAN_PO = ").append(resendPoNo).toString(), conn);
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch (Exception e) {
			errorMsg = "Error attempting to Resend Dbuy, original PO: " + resendPoNo;
			conn.rollback();
			conn.setAutoCommit(true);
		}
		finally {
			if (dbManager != null)
				dbManager.returnConnection(conn);
			factory = null;
			dbManager = null;
		}

		return (errorMsg != null ? errorMsg : newPO);
	}

	public ExcelHandler getExcelReport(Collection<DbuyAcknowledgementBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		String[] headerkeys = {"label.supplier", "label.itemid", "label.po", "label.poline", "label.polineconfirmeddate", "label.shiptoaddress", "label.suppliershiptocode",
				"label.ediacknowledgementcode", "label.acceptededidatecode", "label.acceptedquantity", "label.quantityordered", "label.acceptedunitprice", "label.transactiondate",
				"label.promiseddate", "label.dock.date", "deliveryschedule.label.reviewer", "label.reviewcomments", "label.mismatchcomments", "label.supplierpartnum",
				"label.supplierpartdesc", "label.suppliersalesorderno", "label.suppliersalesorderdate", "label.podate", "label.ponotes", "label.polinenotes"};

		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
		 * characters. pw.TYPE_CALENDAR set up the date with no time.
		 * pw.TYPE_DATE set up the date with time.
		 */
		int[] types = {0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR,
				pw.TYPE_CALENDAR, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, pw.TYPE_PARAGRAPH, 0, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH};

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = {0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 30, 0, 30, 0, 7, 7, 30, 30};
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		pw.setColumnDigit(11, 2);

		for (DbuyAcknowledgementBean member : data) {

			pw.addRow();
			pw.addCell(member.getSupplierName());
			pw.addCell(member.getItemId());
			pw.addCell(member.getRadianPo());
			pw.addCell(member.getPoLine());
			pw.addCell(member.getPoLineConfirmedDate());
			StringBuilder address = new StringBuilder(StringHandler.emptyIfNull(member.getShiptoAddressLine1())).append(" ")
					.append(StringHandler.emptyIfNull(member.getShiptoAddressLine2())).append(" ").append(StringHandler.emptyIfNull(member.getShiptoAddressLine3())).append(" ")
					.append(StringHandler.emptyIfNull(member.getShiptoCity())).append(" ").append(StringHandler.emptyIfNull(member.getShiptoStateAbbrev())).append(" ")
					.append(StringHandler.emptyIfNull(member.getShiptoZip())).append(" ").append(StringHandler.emptyIfNull(member.getShiptoCountryAbbrev()));
			pw.addCell(address.toString());
			pw.addCell(member.getSupplierShipToCode());
			pw.addCell(member.getEdiAcknowledgementCode());
			pw.addCell(member.getAcceptedEdiDateCode());
			pw.addCell(member.getAcceptedQuantity() + " " + member.getAcceptedUom());
			pw.addCell(member.getOrderQuantity() + " " + member.getEdiUom());
			pw.addCell(member.getAcceptedUnitPrice());
			pw.addCell(member.getTransactionDate());
			pw.addCell(member.getPromisedDate());
			pw.addCell(member.getDockDate());
			pw.addCell(member.getReviewerId());
			pw.addCell(member.getReviewComments());
			pw.addCell(member.getMismatchComments());
			pw.addCell(member.getSupplierPartNo());
			pw.addCell(member.getSupplierPartDesc());
			pw.addCell(member.getSupplierSalesOrderNo());
			pw.addCell(member.getSupplierSalesOrderDate());
			pw.addCell(member.getDatePoCreated());
			pw.addCell(member.getPoNotes());
			pw.addCell(member.getPoLineNotes());
		}
		return pw;
	}
}
