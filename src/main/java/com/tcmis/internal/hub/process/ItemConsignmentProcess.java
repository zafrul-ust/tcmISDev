package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ItemConsignmentInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;

/*******************************************************************
 * 
 * @version 1.0
 * 
 * 
 ********************************************************************/

public class ItemConsignmentProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;

	public ItemConsignmentProcess(String client, Locale locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}

	@SuppressWarnings("unchecked")
	public Collection<LogisticsViewBean> getSearchResult(ItemConsignmentInputBean input, PersonnelBean user) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new LogisticsViewBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("hub", SearchCriterion.EQUALS, input.getHub());
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, input.getInventoryGroup());

		if (input.hasFieldSearch()) {
			if (input.isSearchFieldReceiptId()) {
				SearchCriteria receiptIdCriteria = new SearchCriteria();
				receiptIdCriteria.addCriterionWithMode(input.getSearchField() , input.getSearchMode(), input.getSearchText());
				SearchCriteria origReceiptIdCriteria = new SearchCriteria();
				origReceiptIdCriteria.addCriterionWithMode("transferReceiptId", input.getSearchMode(), input.getSearchText());
				criteria.addSubCriteria(receiptIdCriteria, origReceiptIdCriteria);
			}
			else {
				criteria.addCriterionWithMode(input.getSearchField(), input.getSearchMode(), input.getSearchText());
			}
		}

		criteria.addCriterion("quantity", SearchCriterion.GREATER_THAN, "0");
        criteria.addCriterion("qcDate", SearchCriterion.IS_NOT, null);

        SortCriteria sort = new SortCriteria();
		sort.addCriterion("inventoryGroup");
		sort.addCriterion("itemId");

		try {
			if (input.isCountByItem()) {
				String query = "SELECT  SUM(quantity) as quantity, inventory_group, inventory_group_name, item_id, item_desc,dist_customer_part_list,catalog_price,currency_id FROM CONSIGNMENT_PROCESSING_VIEW " + factory.getWhereClause(criteria)
				+ " GROUP BY  inventory_group, inventory_group_name, item_id, item_desc,dist_customer_part_list,catalog_price,currency_id " + factory.getOrderByClause(sort);
				return factory.selectQuery(query);
			}
			else {
				sort.addCriterion("receiptId");
				return factory.select(criteria, sort, "CONSIGNMENT_PROCESSING_VIEW");
			}
		}
		finally {
			dbManager = null;
			factory = null;
		}
	}

	public ExcelHandler getExcelReport(ItemConsignmentInputBean input, PersonnelBean user) throws NoDataException, BaseException, Exception {

		Collection<LogisticsViewBean> data = getSearchResult(input, user);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
		ExcelHandler excel = new ExcelHandler(library);


		/* Column headers for  Excel. */
		String[] headerkeys;

		//Column types, 0 means default depending on the data type.
		int[] types;

		//Column width for Excel, 0 means the width will be default depending on the data type.
		int[] widths;

		if (input.isCountByReceipt()) {
			headerkeys =new String[]{ "label.inventorygroup", "label.item", "label.description", "label.receiptid", "label.originalreceiptid", "label.legacyreceiptid", "label.partnumber", "label.expirationdate","label.quantityonhand", "label.usedquantity", "label.useddate", "label.customerreference", "label.price"};
			types = new int[]{ 0, 0, excel.TYPE_PARAGRAPH, 0, 0, 0, 0, excel.TYPE_DATE, excel.TYPE_NUMBER, excel.TYPE_DATE, 0,excel.TYPE_NUMBER};
			widths = new int[] { 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0 };
		}
		else {
			headerkeys =new String[]{ "label.inventorygroup", "label.item", "label.description", "label.partnumber", "label.quantityonhand", "label.usedquantity", "label.useddate", "label.customerreference", "label.price"};
			types = new int[]{ 0, 0, excel.TYPE_PARAGRAPH, 0, excel.TYPE_NUMBER, 0, excel.TYPE_DATE, 0,excel.TYPE_NUMBER,};
			widths = new int[] { 20, 0, 0, 0, 0, 0, 0, 20, 0};
		}

		// Cell horizontal alignment
		int[] horizAligns = null;

		// Create the table
		excel.addTable();

		// Create the column header row
		excel.addRow();
		excel.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// Set date format for appropriate rows
		if (input.isCountByReceipt()) {
			excel.setColumnDateFormat(7 , library.getString("java.exceldateformat"));
		}


		// now write rows for the data
		for (LogisticsViewBean bean : data) {
			excel.addRow();
			excel.addCell(bean.getInventoryGroupName());
			excel.addCell(bean.getItemId());
			excel.addCell(bean.getItemDesc());
			if (input.isCountByReceipt()) {
				excel.addCell(bean.getReceiptId());
				excel.addCell(bean.getTransferReceiptId());
				excel.addCell(bean.getCustomerReceiptId());
				excel.addCell(bean.getDistCustomerPartList());
				excel.addCell(bean.getExpireDate());
			}
			else {
				excel.addCell(bean.getDistCustomerPartList());
			}
			excel.addCell(bean.getQuantity());
			excel.addCell("");
			excel.addCell("");
			excel.addCell("");
			excel.addCell(bean.getCatalogPrice());
		}
		return excel;
	}

	@SuppressWarnings("unchecked")
	public Collection<String> update(ItemConsignmentInputBean input, Collection<ItemConsignmentInputBean> rows, PersonnelBean user) throws BaseException, Exception {

		ResourceLibrary messages = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());

		Collection<String> errorMessages = new Vector<String>();
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();

		String mrsCreated = "";
		String procedure = input.isCountByItem() ? "p_issue_insert_item_count" : "p_issue_insert_receipt_count";
		try {
			for (ItemConsignmentInputBean row : rows) {
				
				if(row.isNewCatalogPrice())
				{
					Collection cip = new Vector(); 
					cip.add(row.getItemId());
					cip.add(row.getInventoryGroup());
					cip.add(row.getCatalogPrice());
					cip.add(row.getCurrencyId());
					Collection<String> err = dbManager.doProcedure("PKG_CONSIGNED_ITEM.P_ADD_CATALOG_ITEM_PRICE", cip,  new Vector());
					if (err != null && err.size() > 0)
						for(String er : err)
							errorMessages.add(er);
						
				}
				
				Collection cin = new Vector();
				cin.add( input.isCountByItem() ? row.getItemId() : row.getReceiptId()); // 1, a_item_id OR a_receipt_id receipt.item_id%type,
				cin.add(today); // 2, a_date_picked issue.date_picked%type,
				cin.add(row.getUsedQuantity()); // 3, a_quantity issue.quantity%type,
				cin.add(new java.sql.Date(row.getDateDelivered().getTime())); // 4, a_date_delivered issue.date_delivered%type,
				cin.add(row.getInventoryGroup()); // 5, a_inventory_group receipt.inventory_group%type,
				cin.add(user.getPersonnelIdBigDecimal()); // 6, a_issuer_id issue.issuer_id%type,

				Collection cout = new Vector();
				cout.add(new Integer(java.sql.Types.NUMERIC)); // 7, a_pr_number in out issue.pr_number%type
				cout.add(new Integer(java.sql.Types.VARCHAR)); // 8, a_line_item out issue.line_item%type
				cout.add(new Integer(java.sql.Types.VARCHAR)); // 9, a_error_code out varchar

				Collection cOptionalIn = new Vector();
				cOptionalIn.add(""); // 10, a_facility_id request_line_item.facility_id%type default null,
				cOptionalIn.add(""); // 11, a_application request_line_item.application%type default null,
				cOptionalIn.add(""); // 12, a_count_id request_line_item.count_id%type default null,
				cOptionalIn.add(""); // 13, a_cost_center request_line_item.cost_center%type default null,
				cOptionalIn.add(""); // 14, a_cost_center_classification request_line_item.cost_center_classification%type default null,
				cOptionalIn.add(""); // 15, a_process request_line_item.process%type default null,
				cOptionalIn.add(""); // 16, a_chemical_category request_line_item.chemical_category%type default null,
				cOptionalIn.add("Customer Consignment"); // 17, a_material_request_origin purchase_request.material_request_origin%type default 'Item Count',
				cOptionalIn.add("Y"); // 18, a_invoice char default 'N'
				cOptionalIn.add("Y"); // 19, a_allow_over_use char default 'N'
				cOptionalIn.add(row.getShippingReference()); // 20, a_shipping_reference request_line_item.shipping_reference%type default null
				cOptionalIn.add(input.getPoNumber());
				
				if (log.isDebugEnabled()) {
					log.debug(procedure + " -> " + cin + cout + cOptionalIn);
				}

				Collection result = dbManager.doProcedure(procedure, cin, cout, cOptionalIn);

				if (result != null) {
					Iterator resultIterator = result.iterator();
					int resultCount = 0;
					BigDecimal prNumber = null;
					String lineItem = "";
					String error = "";
					while (resultIterator.hasNext()) {
						if (resultCount == 0) {
							prNumber = (BigDecimal) resultIterator.next();
						}
						else if (resultCount == 1) {
							lineItem = (String) resultIterator.next();
						}
						else if (resultCount == 2) {
							error = (String) resultIterator.next();
						}
						resultCount++;
					}

					if (!StringHandler.isBlankString(error)) {
						log.info("errorCode :" + error );
						errorMessages.add(messages.getString("error.db.procedure"));
						errorMessages.add( error);
					}
					else if (prNumber == null) {
						errorMessages.add(messages.getString("error.db.procedure"));
					}
					else {
						if (!StringHandler.isBlankString(mrsCreated)) {
							mrsCreated += ",";
						}

						mrsCreated += prNumber;
						log.info("MR is created " + prNumber + "-" + lineItem + "");
					}
				}

			}
		}
		catch (Exception e) {

		}
		finally {
			dbManager.returnConnection(connection);
		}

		if (!StringHandler.isBlankString(mrsCreated)) {
			ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");
			String wwwHome = resource.getString("hosturl");
			String resultUrl = wwwHome + resource.getString("cabupconfirmurl");


			BulkMailProcess bulkMailProcess = new BulkMailProcess(getClient());

			String message = messages.getString("letter.itemconsignmentresults") + resultUrl + "UserAction=Search&mrslist=" + mrsCreated + "\n\n\n" + messages.getString("label.personnelid") + " : " + user.getPersonnelId();
			bulkMailProcess.sendBulkEmail(user.getPersonnelIdBigDecimal(), messages.getString("letter.itemconsignmentresultstitle"), message, true);
			if (log.isDebugEnabled()) {
				log.debug(message);
			}
		}
		return errorMessages;
	}
}
