package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvStateBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CustomerCreditBean;
import com.tcmis.internal.distribution.beans.DbuyContractViewBean;
import com.tcmis.internal.catalog.beans.CatalogAddQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogQueueItemBean;
import com.tcmis.internal.catalog.beans.CatalogVendorAssignmentBean;
import com.tcmis.internal.hub.beans.CustomerInventoryToReceiveBean;
import com.tcmis.internal.hub.factory.CustomerInventoryToReceiveBeanFactory;
import com.tcmis.internal.supply.beans.DbuyContractPriceBreakObjBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;
import com.tcmis.internal.supply.factory.DbuyContractPriceOvBeanFactory;

/******************************************************************************
 * Process for DbuyProcess
 * @version 1.0
 *****************************************************************************/
public class CatalogSupplierProcess extends GenericProcess { 

	public CatalogSupplierProcess(TcmISBaseAction act) throws BaseException {
		super(act);
	}
	
	public CatalogSupplierProcess(String client, String locale) {
	    super(client,locale);
	}

	public Collection getCatalogVendorAssignment(CatalogVendorAssignmentBean bean) throws BaseException {
		String query = "select * from catalog_vendor_assignment_view ";
		String whereClause = "";
		if( !isBlank(bean.getSupplier())) {
			if( !isBlank(whereClause))
				whereClause += " and";
			whereClause += " supplier = " + getSqlString(bean.getSupplier());
		}
		if( !isBlank(bean.getTask()) ) {
			if( !isBlank(whereClause))
				whereClause += " and";
			whereClause += " task  = " + getSqlString(bean.getTask());
		}
		if (!bean.showInactiveChecked()) {
			if( !isBlank(whereClause))
				whereClause += " and";
			whereClause += " status  = 'A'";
		}
		if( !isBlank(whereClause)) {
			query += " where"+whereClause;
		}
		query += " order by to_number(item_id),locale_code";
		return factory.setBean(new CatalogVendorAssignmentBean()).selectQuery(query);
	}

	public void addCatalogVendorAssignment(Collection<CatalogVendorAssignmentBean> beans, PersonnelBean personnelBean) throws BaseException {
		StringBuilder cvaStmt = new StringBuilder();
		
		beans.forEach(bean -> {
			if (cvaStmt.length() > 0) {
				cvaStmt.append(" union ");
			}
			cvaStmt.append(" select ")
					.append(SqlHandler.delimitString(bean.getSupplier()))
					.append(", cqi.catalog_queue_item_id")
					.append(", 'A', 0, sysdate, 'USD'")
					.append(", ").append(personnelBean.getPersonnelId())
					.append(", sysdate")
					.append(" from catalog_queue_item cqi where ")
					.append(" cqi.locale_code = ").append(SqlHandler.delimitString(bean.getLocaleCode()))
					.append(" and cqi.task = ").append(SqlHandler.delimitString(bean.getTask()));
		});
		
		String query = "insert into catalog_vendor_assignment"
				+ " (supplier, catalog_queue_item_id, status, percentage, start_date, currency_id, inserted_by, inserted_date)"
				+ cvaStmt.toString();
		/*String query = "insert into catalog_vendor_assignment ( supplier , catalog_Queue_Item_Id, status,percentage,start_date, currency_id,inserted_by,inserted_date ) values ( " +
				this.getSqlString(bean.getSupplier()) +"," +
				this.getSqlString(bean.getCatalogQueueItemId()) +",'A',0,sysdate, 'USD',"+
				personnelBean.getPersonnelId()+",sysdate)";*/
		factory.deleteInsertUpdate(query);
	}
	public Collection updateCatalogVendorAssignment(CatalogVendorAssignmentBean input,Collection<CatalogVendorAssignmentBean> beans, PersonnelBean personnelBean) throws BaseException {
		Connection connection = dbManager.getConnection();
		try {
			for (CatalogVendorAssignmentBean bean : beans) {
				String updateQuery = "update catalog_vendor_assignment set " +
						"  PERCENTAGE =" + getSqlString(bean.getPercentage()) + "," +
						"  STATUS =" + getSqlString(bean.getStatus1()) + "," +
						"  START_DATE =" + getSqlString(bean.getStartDate()) + "," +
						"  STOP_DATE =" + getSqlString(bean.getStopDate()) + "," +
						"  ADDL_INGRED_BASE_QTY =" + getSqlString(bean.getAddlIngredBaseQty()) + "," +
						"  ADDL_INGRED_BUNDLE_QTY =" + getSqlString(bean.getAddlIngredBundleQty()) + "," +
						"  CURRENCY_ID =" + getSqlString(bean.getCurrencyId()) + "," +
						"  SOURCING_UNIT_PRICE =" + getSqlString(bean.getSourcingUnitPrice()) + "," +
						"  OPS_ENTITY_ID =" + getSqlString(bean.getOpsEntityId()) + "," +
						"  UNIT_PRICE =" + getSqlString(bean.getUnitPrice()) + "," +
						"  ADDL_INGRED_UNIT_PRICE =" + getSqlString(bean.getAddlIngredUnitPrice()) + "," +
						"  BILLED_BY_COMPONENT =" + getSqlString(bean.getBilledByComponent()) + "," +
						"  LAST_UPDATED_BY = "+personnelBean.getPersonnelId() +","+
						"  LAST_UPDATED = sysdate"+
						" where CATALOG_VENDOR_ASSIGNMENT_ID =" + getSqlString(bean.getCatalogVendorAssignmentId());

				factory.deleteInsertUpdate(updateQuery,connection);
			}
		}catch(Exception e) {
			throw e;
		}finally {
			dbManager.returnConnection(connection);
		}

		return getCatalogVendorAssignment(input);
	}
	
	public Collection getCatalogVendor() throws BaseException {
		return factory.setBean(new CatalogVendorAssignmentBean()).selectQuery("select distinct supplier, supplier_name from catalog_vendor_assignment_view order by supplier_name");
	}
	
	public Collection getQueueItem(CatalogVendorAssignmentBean inputBean) throws BaseException {
		String query = "select cqi.catalog_queue_item_id, cqi.task, cqi.item_id, cqi.sourcing_item_id"
				+ ", cqi.addl_ingred_item_id, cqi.cas_sourcing_item_id, nvl(cqi.locale_code, l.locale_code) locale_code"
				+ ", l.locale_display, nvl2(cva.supplier, 'Y', 'N') assigned_to_supplier"
				+ " from catalog_queue_item cqi, catalog_vendor_assignment cva, vv_locale l"
				+ " where cqi.catalog_queue_item_id = cva.catalog_queue_item_id(+)"
				+ " and cqi.locale_code(+) = l.locale_code"
				+ " and cva.supplier(+) = " + SqlHandler.delimitString(inputBean.getSupplier());
		if( !isBlank(inputBean.getTask()) ) {
			query += " and cqi.task(+) = " +getSqlString(inputBean.getTask());
		}
		return factory.setBean(new CatalogQueueItemBean()).selectQuery(query);
	}
	
	public void addLocalesToTask(Collection<CatalogQueueItemBean> taskLocaleColl) throws BaseException {
		StringBuilder cqiStmt = new StringBuilder();
		taskLocaleColl.forEach(taskLocale -> {
			if (cqiStmt.length() > 0) {
				cqiStmt.append(" union ");
			}
			cqiStmt.append(" select ")
					.append(taskLocale.getItemId())
					.append(", ").append(taskLocale.getAddlIngredItemId())
					.append(", ").append(taskLocale.getSourcingItemId())
					.append(", ").append(SqlHandler.delimitString(taskLocale.getLocaleCode()))
					.append(", ").append(SqlHandler.delimitString(taskLocale.getTask()))
					.append(" from dual where not exists")
					.append(" (select catalog_queue_item_id from catalog_queue_item")
					.append(" where locale_code = ").append(SqlHandler.delimitString(taskLocale.getLocaleCode()))
					.append(" and task = ").append(SqlHandler.delimitString(taskLocale.getTask())).append(")");
			
		});
		
		if (cqiStmt.length() > 0) {
			String insertStmt = "insert into catalog_queue_item"
					+ "(item_id, addl_ingred_item_id, sourcing_item_id, locale_code, task)"
					+ cqiStmt.toString();
			
			factory.deleteInsertUpdate(insertStmt);
		}
	}

	public ExcelHandler getExcelReport(CatalogVendorAssignmentBean ibean, PersonnelBean pbean) throws BaseException {

		BigDecimal personnelId = new BigDecimal(pbean.getPersonnelId());
		Locale locale = pbean.getLocale();
		Collection<CatalogVendorAssignmentBean> data = getCatalogVendorAssignment(ibean);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//  write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headervals = { "Supplier", "Task", "Locale", "Item ID", "Item Desc", "Percentage",
				"Status","Start Date","Stop Date","Addl Ingred Base Qty","Addl Ingred Bundle Qty", "Unit Price","Addl Ingred Unit Price",
				"Currency ID","Sourcing Unit Price","Operating Entity","Billed By Component"};
		/*This array defines the type of the excel column.
		  0 means default depending on the data type. */
		int[] types = { 0, 0, 0, 0, 0, 0, pw.TYPE_NUMBER, 0,pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER,  0, pw.TYPE_NUMBER, 0, pw.TYPE_NUMBER, 0, 0 };
		int[] widths = { 25, 15, 0, 0, 20, 0, 0, 0, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		pw.applyColumnHeaderVal(headervals, types, widths, null);

		BigDecimal one = new BigDecimal("1");
		for (CatalogVendorAssignmentBean bean : data) {
			pw.addRow();
			pw.addCell(bean.getSupplier()+"-"+bean.getSupplierName());
			pw.addCell(bean.getTask());
			pw.addCell(bean.getLocaleCode());
			pw.addCell(bean.getItemId());
			pw.addCell(bean.getItemDesc());
			pw.addCell(bean.getPercentage());
			pw.addCell(bean.getStatus());
			pw.addCell(bean.getStartDate());
			pw.addCell(bean.getStopDate());
			pw.addCell(bean.getAddlIngredBaseQty());
			pw.addCell(bean.getAddlIngredBundleQty());
			pw.addCell(bean.getUnitPrice());
			pw.addCell(bean.getAddlIngredUnitPrice());
			pw.addCell(bean.getCurrencyId());
			pw.addCell(bean.getSourcingUnitPrice());
			pw.addCell(bean.getOperatingEntityName());
			pw.addCell(bean.getBilledByComponent());
		}
		return pw;
	}
}
