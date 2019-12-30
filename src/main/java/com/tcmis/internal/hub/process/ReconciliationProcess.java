package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.HubCountDateBean;
import com.tcmis.internal.hub.beans.HubInventoryCountViewBean;
import com.tcmis.internal.hub.beans.ReconciliationInputBean;
import com.tcmis.internal.hub.beans.ReconciliationViewBean;

/******************************************************************************
 * Process for reconcialiation
 * 
 * @version 1.0
 *****************************************************************************/
public class ReconciliationProcess extends GenericProcess {
	Log	log	= LogFactory.getLog(this.getClass());

	public ReconciliationProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection<HubCountDateBean> getCountDateDropDown(ReconciliationInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new HubCountDateBean());
		String query = "select *  from hub_count_date_view where hub = '" + inputBean.getHub().toString() + "' and COUNT_STATUS  <> 'Closed' order by COUNT_DATETIME desc";
		Collection<HubCountDateBean> coll = factory.selectQuery(query);
		return coll;
	}

	public Object[] startNewCount(ReconciliationInputBean bean, int personnelId) throws BaseException, Exception {

		String errorMsg = "";
		Vector error = new Vector();
		Vector errorMessages = new Vector();
		BigDecimal newCountId = null;
		String errorCode = "OK";
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if (bean != null) {

			try {
				if (!StringHandler.isBlankString(bean.getHub())) {
					Collection inArgs = null;
					Collection inArgs2 = null;

					inArgs = buildProcedureInput(bean.getCountType(), bean.getHub());

					if (null != bean.getItemId()) {
						inArgs2 = buildProcedureInput(null, bean.getItemId(), personnelId);
					}
					else {
						inArgs2 = buildProcedureInput(null, null, personnelId);
					}
					Vector outArgs = new Vector();
					outArgs.add(new Integer(java.sql.Types.NUMERIC));
					outArgs.add(new Integer(java.sql.Types.VARCHAR));

					if (log.isDebugEnabled()) {
						log.debug("Input Args for pkg_inventory_count.p_start_count  :" + inArgs);
					}
					error = (Vector) factory.doProcedure("pkg_inventory_count.p_start_count", inArgs, outArgs, inArgs2);
					// Vector error = new Vector();

					if (error.size() > 0 && error.get(1) != null) {
						errorCode = error.get(1).toString();
					}
					newCountId = (BigDecimal) error.get(0);
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				errorCode = "Error Adding Count Date: " + bean.getHub() + " " + e.toString();
			}
		}

		Object[] objs = { errorCode, newCountId };
		return objs;
	}

	public String setCountDateClose(ReconciliationInputBean inputBean) throws BaseException {
		String result = "OK";

		DbManager dbManager = new DbManager(getClient(), this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new HubCountDateBean());

		try {
			String query = "Update HUB_COUNT_DATE Set COUNT_STATUS = 'Closed' " + "where HUB = " + inputBean.getHub() + " and COUNT_ID = " + inputBean.getCountId();
			factory.deleteInsertUpdate(query);
		}
		catch (Exception e) {
			e.printStackTrace();
			result = "Error: Updating";
		}
		return result;
	}

	public Collection getData(PersonnelBean user, ReconciliationInputBean input) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new HubInventoryCountViewBean());

		Collection<HubInventoryCountViewBean> inventoryCountColl = null;
		
		String[] inventoryGroups;
		if ((input.getInventoryGroup() != null) && (!StringHandler.isBlankString(input.getInventoryGroup()[0].toString()))) {
			inventoryGroups = input.getInventoryGroup();
		}
		else {
			inventoryGroups = user.getHub(input.getHub()).getInventoryGroupsForSearch().toArray(new String[0]);
		}
		
		boolean containsAll = false;
		String[] lotStatusColl = input.getLotStatus();
		if (lotStatusColl != null) for (int i = 0; i < lotStatusColl.length; i++) {
			if ("".equals(lotStatusColl[i])) {
				containsAll = true;
				break;
			}
		}
		
		String skipAlreadyCounted = (input.isSkipCountedCheckbox() && input.getCountType().equals("Cycle Count"))?"'Y'":"'N'";			
		String partNumbers = input.isIncludePartNumbers()?"'Y'":"'N'";
		String lotStatusString = (lotStatusColl==null||containsAll)?"'All'":SqlHandler.validQuery(Arrays.toString(lotStatusColl)).replace("[", "'").replace("]", "'");
		String lotStatusInclusive = "Inclusive".equals(input.getRadiobox())?"'I'":"'E'";
		String problemReceiptsOnly = StringHandler.isBlankString(input.getCheckbox())?"'N'":"'Y'";
		String room = StringHandler.isBlankString(input.getRoom())?"'All'":SqlHandler.delimitString(input.getRoom());
		String searchField = StringHandler.convertBeanPropertyToDatabaseColumn(input.getSearchField());
		String searchMode = input.getSearchMode();
		String searchText = input.getSearchArgument();
		String pipelinedTable = "select * from TABLE(" +
				"pkg_inventory_reconciliation.fx_hub_inventory_count(" +
				SqlHandler.delimitString(input.getHub()) + "," +
				SqlHandler.validQuery(Arrays.toString(inventoryGroups)).replace("[", "'").replace("]", "'") + "," +
				SqlHandler.validQuery(input.getCountId()) + "," +
				partNumbers + "," +
				lotStatusString + "," +
				lotStatusInclusive + "," +
				problemReceiptsOnly + "," +
				room + "," +
				skipAlreadyCounted + "," +
				SqlHandler.delimitString(StringHandler.emptyIfNull(input.getBinFrom())) + "," +
				SqlHandler.delimitString(StringHandler.emptyIfNull(input.getBinTo())) + "," +
				SqlHandler.delimitString(searchField) + "," +
				SqlHandler.delimitString(searchMode) + ", " +
				SqlHandler.delimitString(searchText) + "))";
		
		StringBuilder query = new StringBuilder(pipelinedTable);
		if (searchText != null && !(searchText.trim().length() == 0)) {
			if (searchField.equalsIgnoreCase("cat_part_no")) {
				if (searchMode.equals("is")) query.append(" where lower("+searchField+") = lower("+SqlHandler.delimitString(searchText)+")");
				else if (searchMode.equals("contains")) query.append(" where lower("+searchField+") like lower("+SqlHandler.delimitString("%"+searchText+"%")+")");
				else if (searchMode.equals("startsWith")) query.append(" where lower("+searchField+") like lower("+SqlHandler.delimitString(searchText+"%")+")");
				else if (searchMode.equals("endsWith")) query.append(" where lower("+searchField+") like lower("+SqlHandler.delimitString("%"+searchText)+")");
			}
		}
		
		inventoryCountColl = factory.selectQuery(query.toString());
		return inventoryCountColl;
	}

	public ExcelHandler getReconciliationExcel(PersonnelBean personnelBean, ReconciliationInputBean inputBean, Locale locale) throws Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<HubInventoryCountViewBean> data = getData(personnelBean, inputBean);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();

		SimpleDateFormat dFormat = new SimpleDateFormat("MMM dd yyyy HH:mm a");
		pw.addCellKeyBold("label.date");
		pw.addCellBold(dFormat.format(Calendar.getInstance().getTime()));
		pw.addRow();
		pw.addCellKeyBold("label.branchplant");
		pw.addCellBold(inputBean.getHubName());
		pw.addRow();
		pw.addCellKeyBold("label.countdate");
		pw.addCellBold(inputBean.getInputDate());
		pw.addRow();
		pw.addCellKeyBold("label.counttype");
		pw.addCellBold(inputBean.getCountType());
		pw.addRow();
		pw.addCellKeyBold("label.countstartedby");
		pw.addCellBold(inputBean.getCountStartedByName());
		pw.addRow();
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.inventorygroup", "label.item", "label.description", "label.packaging", "label.unitcost", "label.mfglot", "label.room", "label.bin", "label.lotstatus", "label.expiredate", "label.receiptid", "label.legacyreceiptid",
				"label.partnumber", "label.recordedonhand", "label.actualonhand", "label.ownercompanyid" };
		// , "label.replenishqty","label.needdatemm/dd/yyyy"};
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type.
		 */
		int[] types = { 0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, 0 };
		// pw.TYPE_NUMBER,pw.TYPE_CALENDAR};
		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 15, 20, 0, 0, 8, 0, 0, 15, 12, 0, 0, 0, 15, 10, 10, 10 };
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		pw.setColumnDigit(10, 1);

		// now write data
		int i = 1;
		for (HubInventoryCountViewBean member : data) {
			i++;
			pw.addRow();

			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getUnitCost());
			pw.addCell(member.getMfgLot());
			pw.addCell(member.getRoom());
			pw.addCell(member.getBin());
			pw.addCell(member.getLotStatus());
			if (member.getExpireDate() != null) pw.addCell(dFormat.format(member.getExpireDate()));
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getCustomerReceiptId());
			if (member.getCatPartNo() != null) pw.addCell(member.getCatPartNo());
			else if (member.getCatPartNos() != null) pw.addCell(member.getCatPartNos());
			else
				pw.addCell("");
			if (!inputBean.getPrintOnHandCheckbox())
				pw.addCell(member.getExpectedQuantity());
			else   
				pw.addCell("");
			pw.addCell(member.getActualQuantity());
			pw.addCell(member.getOwnerCompanyId());
		}
		return pw;
	}

	public Vector update(PersonnelBean personnelBean, ReconciliationInputBean inputBean, Collection<ReconciliationViewBean> beanColl) throws BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		// String errorCode = null;
		Vector errorMessages = new Vector();
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		PermissionBean permissionBean = personnelBean.getPermissionBean();

		if ((beanColl != null) && (!beanColl.isEmpty())) {
			for (ReconciliationViewBean reconciliationBean : beanColl) {

				if (!permissionBean.hasInventoryGroupPermission("inventoryReconciliation", reconciliationBean.getInventoryGroup(), inputBean.getHubName(), null)
						&& !personnelBean.getPermissionBean().hasInventoryGroupPermission("approveReconciliation", null, inputBean.getHubName(), null)) continue;

				if (((null != reconciliationBean.getActualQuantity()) && (!(reconciliationBean.getActualQuantity().equals(reconciliationBean.getOldActualQuantity()))))
						|| ((null == reconciliationBean.getActualQuantity()) && (null != reconciliationBean.getOldActualQuantity()))) {
					try {
						// if(!StringHandler.isBlankString(pwcOrderNotifyErrorBean.getOk()))
						// {
						inArgs = buildProcedureInput(inputBean.getCountId(), reconciliationBean.getReceiptId(), personnelId, reconciliationBean.getActualQuantity(), null, null, null);
						outArgs = new Vector(1);
						outArgs.add(new Integer(java.sql.Types.VARCHAR));

						if (log.isDebugEnabled()) {
							log.debug("Input Args for pkg_inventory_count.p_update_inventory_count  :" + inArgs);
						}
						Vector error = (Vector) factory.doProcedure("pkg_inventory_count.p_update_inventory_count", inArgs, outArgs);
						if (error.get(0) != null && !((String) error.get(0)).equalsIgnoreCase("")) {
							errorMessages.add(error.get(0));
						}
						// }
					}
					catch (Exception e) {
						errorMsg = "Error updating : " + e.toString();
						errorMessages.add(errorMsg);
					}

				}
			}
		}

		return errorMessages;
	}

	public Vector approveRecon(BigDecimal personnelId, ReconciliationInputBean inputBean) throws BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		// String errorCode = null;
		Vector errorMessages = new Vector();
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		try {
			// if(!StringHandler.isBlankString(pwcOrderNotifyErrorBean.getOk()))
			// {
			inArgs = buildProcedureInput(inputBean.getCountId(), personnelId);
			outArgs = new Vector(1);

			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			if (log.isDebugEnabled()) {
				log.debug("Input Args for pkg_inventory_count.p_approve_inventory_count  :" + inArgs);
			}
			Vector error = (Vector) factory.doProcedure("pkg_inventory_count.p_approve_inventory_count", inArgs, outArgs);
			if (error.get(0) != null && !((String) error.get(0)).equalsIgnoreCase("")) {
				errorMessages.add(error.get(0));
			}

		}
		catch (Exception e) {
			errorMsg = "Error approving : " + e.toString();
			errorMessages.add(errorMsg);
		}

		return errorMessages;
	}

}
