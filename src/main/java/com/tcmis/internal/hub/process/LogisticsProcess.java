package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ExpiredInventoryViewBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.hub.beans.VvLotStatusBean;

/******************************************************************************
 * Process for logistics
 * 
 * @version 1.0
 *****************************************************************************/
public class LogisticsProcess extends GenericProcess {
	Log	log	= LogFactory.getLog(this.getClass());

	public LogisticsProcess(String client, Locale locale) {
		super(client, locale, new LogisticsViewBean());
	}

	public Vector<LogisticsViewBean> getSearchResultData(LogisticsInputBean input, PersonnelBean user) throws BaseException {

		SearchCriteria searchCriteria = new SearchCriteria();
		if (input.hasHub()) {
			searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, input.getHub());
		}
		if (input.hasInventoryGroup()) {
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, input.getInventoryGroup());
		}
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + user.getPersonnelId();
			if (user.hasCompanyId()) {
				invQuery += " and company_id = '" + user.getCompanyId() + "' ";
			}
			if (input.hasHub()) {
				invQuery += " and hub = " + getSqlString(input.getHub());
			}
			else if (input.hasOpsEntityId()) {
				invQuery += " and ops_entity_id = " + getSqlString(input.getOpsEntityId());
			}
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}

		if (input.hasReceiverId()) {
			searchCriteria.addCriterion("receiverId", SearchCriterion.EQUALS, "" + input.getReceiverId());
		}

		if (input.hasTransactionDate()) {
			searchCriteria.addCriterion("transactionDate", SearchCriterion.IS_DATE, input.getTransactionDate());
		}

		if (input.hasNumDaysOld()) {
			// allow either positive and negative number
			searchCriteria.addCriterion("receiptExpireDate", SearchCriterion.CUSTOM, " < sysdate + (" + input.getNumDaysOld() + ")");
		}

		if (input.isShowIssuedReceiptsChecked()) {
			searchCriteria.addCriterion("", SearchCriterion.CUSTOM, " nvl(PICKABLE,'N') <> 'Y' ");
		}

		if (!input.isIncludeHistoryChecked()) {
			searchCriteria.addCriterion("quantity", SearchCriterion.NOT_EQUAL, "0");
		}

		if (input.hasSearchArgument()) {
			if (input.isSearchFieldCatPartNo()) {
				StringBuffer catPartNoSearch = new StringBuffer("(item_id, inventory_group) IN (SELECT   cpig_cpi.item_id, cpig_cpi.inventory_group FROM cpig_cpi_view cpig_cpi, inventory_group_definition igd");
				catPartNoSearch.append(" WHERE   cpig_cpi.inventory_group = igd.inventory_group AND igd.hub = ").append(getSqlString(input.getHub()));
				catPartNoSearch.append(" AND cpig_cpi.cat_part_no ");
				if (input.isModeIs()) {
					catPartNoSearch.append(" = ").append(getSqlString(input.getSearchArgument()));
				}
				else if (input.isModeContains()) {
					catPartNoSearch.append(" LIKE ").append(getSqlString("%" + input.getSearchArgument() + "%"));
				}
				else if (input.isModeStartsWith()) {
					catPartNoSearch.append(" LIKE ").append(getSqlString(input.getSearchArgument() + "%"));
				}
				else if (input.isModeEndsWith()) {
					catPartNoSearch.append(" LIKE ").append(getSqlString("%" + input.getSearchArgument()));
				}
				
				if (!input.isIncludeObsoletePartsChecked()) {
					catPartNoSearch.append(" AND cpig_cpi.cpig_status in ('A','D')");					
				}
				catPartNoSearch.append(")");
				searchCriteria.addCriterion("", SearchCriterion.CUSTOM, catPartNoSearch.toString());
			}
			else {
				if (input.isModeIs()) {
					if (input.isSearchFieldCaseSensitive()) {
						searchCriteria.addCriterion(input.getSearchField(), SearchCriterion.EQUALS, input.getSearchArgument());
					}
					else {
						searchCriteria.addCriterion(input.getSearchField(), SearchCriterion.EQUALS, input.getSearchArgument(), SearchCriterion.IGNORE_CASE);
					}
				}
				else if (input.isModeContains()) {
					searchCriteria.addCriterion(input.getSearchField(), SearchCriterion.LIKE, input.getSearchArgument(), SearchCriterion.IGNORE_CASE);
				}
				else if (input.isModeStartsWith()) {
					searchCriteria.addCriterion(input.getSearchField(), SearchCriterion.STARTS_WITH, input.getSearchArgument(), SearchCriterion.IGNORE_CASE);
				}
				else if (input.isModeEndsWith()) {
					searchCriteria.addCriterion(input.getSearchField(), SearchCriterion.ENDS_WITH, input.getSearchArgument(), SearchCriterion.IGNORE_CASE);
				}
			}
		}

		boolean containsAll = false;
		String[] values = input.getLotStatus();
		if (values != null) {
			for (int i = 0; i < values.length; i++)
				if ("".equals(values[i])) {
					containsAll = true;
					break;
				}
		}
		else {
			containsAll = true;
		}

		if (!containsAll) {
			searchCriteria.addCriterionArray("lotStatus", SearchCriterion.IN, input.getLotStatus());
		}

		SortCriteria sortCriteria = null;
		if (input.hasSortBy()) {
			sortCriteria = new SortCriteria();
			for (String cr : input.getSortBy().split(",")) {
				sortCriteria.addCriterion(cr.replaceAll("[|]", " "));
			}
		}

		return (Vector<LogisticsViewBean>) factory.select(searchCriteria, sortCriteria, "LOGISTICS_VIEW");
	}

	public Object[] getSearchResult(LogisticsInputBean inputBean, PersonnelBean pbean) throws BaseException {
		return getRowSpan(getSearchResultData(inputBean, pbean));
	}

	public Vector getQcInv(PersonnelBean bean) throws BaseException {
		Object[] c = factory.selectIntoObjectArray("select distinct inventory_group from tcm_ops.quality_certification_number where personnel_id = " + bean.getPersonnelId());
		Vector cc = null;
		try {
			cc = ((Vector) (c)[2]);// .get(0);
			// very wield, I cannot use exception to capture it....
		}
		catch (Exception ex) {
		}
		return cc;
	}

	public Object[] getRowSpan(Vector<LogisticsViewBean> bv) {

		Vector<Integer> m1 = new Vector();
		Integer i1 = null;
		String partNum = null;
		String prePart = "";
		boolean flag = false; // no rowspan.
		int current = 0;
		int count = 0;
		for (LogisticsViewBean pbean : bv) {
			partNum = pbean.getReceiptId().toString();

			if (partNum.equals(prePart)) {
				Integer receiptCount = m1.get(current);
				receiptCount++;
				m1.set(current, receiptCount);
				m1.add(receiptCount);
				flag = true;
			}
			else {
				m1.add(new Integer(1));
				current = count;
			}
			count++;
			prePart = partNum;
		}
		Object[] objs = { bv, m1, flag };
		return objs;
	}

	public Vector<LogisticsViewBean> getBins(Vector<LogisticsViewBean> v) throws BaseException {
		HashMap map = new HashMap();
		for (LogisticsViewBean bean : v) {

			String hashValue = bean.getItemId() + "||" + bean.getHub();
			Vector bins = (Vector) map.get(hashValue);
			if (bins == null) {
				SearchCriteria criteria = new SearchCriteria();
				// select BIN from receipt_item_prior_bin_view where status =
				// 'A' and item_id = 157833 and hub = '2101'
				criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
				criteria.addCriterion("itemId", SearchCriterion.EQUALS, bean.getItemId().toString());
				criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
				bins = (Vector) factory.select(criteria, null, "receipt_item_prior_bin_view");
				map.put(hashValue, bins);
			}
			bean.setBins(bins);
		}
		return v;
	}

	public Collection loadData(LogisticsInputBean inputBean) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
		criteria.addCriterion("itemId", SearchCriterion.EQUALS, inputBean.getItemId().toString());
		criteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
		return (Vector) factory.select(criteria, null, "receipt_item_prior_bin_view");

	}

	public Vector getExpiredInventory(LogisticsInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		factory.setBean(new ExpiredInventoryViewBean());

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("quantity", SearchCriterion.NOT_EQUAL, "0");
		if (!StringHandler.isBlankString(inputBean.getOpsEntityId())) searchCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS, inputBean.getOpsEntityId());
		if (!StringHandler.isBlankString(inputBean.getHub())) searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
		if (!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
		}
		else {
			String invQuery = " select inventory_group from user_inventory_group where personnel_id = " + personnelBean.getPersonnelId();
			if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0) invQuery += " and company_id = '" + personnelBean.getCompanyId() + "' ";
			if (!StringHandler.isBlankString(inputBean.getHub())) {
				invQuery += " and hub = '" + inputBean.getHub() + "' ";
			}
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.IN, invQuery);
		}
		boolean containsAll = false;
		String[] values = inputBean.getLotStatus();
		if (values != null) for (int i = 0; i < values.length; i++)
			if ("".equals(values[i])) {
				containsAll = true;
				break;
			}
		if ("Inclusive".equals(inputBean.getCheckbox())) {
			if (!containsAll) {
				searchCriteria.addCriterionArray("lotStatus", SearchCriterion.IN, inputBean.getLotStatus());
			}
		}
		else {
			searchCriteria.addCriterionArray("lotStatus", SearchCriterion.NOT_IN, inputBean.getLotStatus());
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("expireDate");
		return (Vector) factory.select(searchCriteria, sortCriteria, "expired_inventory_view");// ;,"LOGISTICS_VIEW");

	}

	public ExcelHandler getShelfLifeExpReportExcel(LogisticsInputBean inputBean, PersonnelBean personnelBean) throws NoDataException, BaseException, Exception {

		Vector<ExpiredInventoryViewBean> data = this.getExpiredInventory(inputBean, personnelBean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

		ExcelHandler pw = new ExcelHandler(library);

		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance(this.getLocaleObject());
		calendar.setTime(new Date());

		SimpleDateFormat sdf = new SimpleDateFormat("MMM", this.getLocaleObject());
		SimpleDateFormat sdfyy = new SimpleDateFormat("yy", this.getLocaleObject());

		pw.addTable();
		// write column headers

		pw.addRow();
		pw.addTdRegionBold(" ", 1, 14);

		// pw.addRow();
		// pw.addTdEmpty(15);
		pw.addTh("label.total");

		// now write data
		// int i = 1;
		BigDecimal expired = new BigDecimal("0");
		BigDecimal month1 = new BigDecimal("0");
		BigDecimal month2 = new BigDecimal("0");
		BigDecimal month3 = new BigDecimal("0");
		BigDecimal month4 = new BigDecimal("0");
		BigDecimal month5 = new BigDecimal("0");
		BigDecimal month6 = new BigDecimal("0");

		for (ExpiredInventoryViewBean member : data) {
			if (member.getExpired() != null) expired = expired.add(member.getExpired());
			if (member.getMonth1() != null) month1 = month1.add(member.getMonth1());
			if (member.getMonth2() != null) month2 = month2.add(member.getMonth2());
			if (member.getMonth3() != null) month3 = month3.add(member.getMonth3());
			if (member.getMonth4() != null) month4 = month4.add(member.getMonth4());
			if (member.getMonth5() != null) month5 = month5.add(member.getMonth5());
			if (member.getMonth6() != null) month6 = month6.add(member.getMonth6());
		}

		pw.addCellBold(expired.setScale(2, BigDecimal.ROUND_HALF_UP));
		pw.addCellBold(month1.setScale(2, BigDecimal.ROUND_HALF_UP));
		pw.addCellBold(month2.setScale(2, BigDecimal.ROUND_HALF_UP));
		pw.addCellBold(month3.setScale(2, BigDecimal.ROUND_HALF_UP));
		pw.addCellBold(month4.setScale(2, BigDecimal.ROUND_HALF_UP));
		pw.addCellBold(month5.setScale(2, BigDecimal.ROUND_HALF_UP));
		pw.addCellBold(month6.setScale(2, BigDecimal.ROUND_HALF_UP));
		/* Pass the header keys for the Excel. */
		pw.addRow();
		pw.addTh("label.operatingentity");
		pw.addTh("label.hub");
		pw.addTh("label.inventorygroup");
		pw.addTh("label.item");
		pw.addTh("label.partno");
		pw.addTh("label.description");
		pw.addTh("label.specs");
		pw.addTh("label.receiptid");
		pw.addTh("label.lotstatus");
		pw.addTh("label.totallife");
		pw.addTh("label.bin");
		pw.addTh("label.expiredate");
		pw.addTh("label.onhand");
		pw.addTh("label.currencyid");
		pw.addTh("label.cost");
		pw.addCellKeyBold("label.expired");

		int monthCount = 0;
		while (monthCount < 6) {
			monthCount++;
			String monthName = sdf.format(calendar.getTime());
			String year = sdfyy.format(calendar.getTime());

			pw.addCellBold(monthName + " " + year);

			calendar.add(2, 1);
		}

		String[] headerkeys = { "label.operatingentity", "label.hub", "label.inventorygroup", "label.item", "label.partno", "label.description", "label.specs", "label.receiptid", "label.lotstatus", "label.bin", "label.totallife", "label.expiredate",
				"label.onhand", "label.currencyid", "label.cost", "label.expired", "label.jun", "label.jul", "label.aug", "label.sep", "label.oct", "label.nov" };

		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type.
		 */
		int[] types = { 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_NUMBER, 0, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER };

		int[] widths = { 15, 12, 15, 0, 0, 0, 0, 0, 0, 0, 0 };

		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */

		pw.setColumnHeader(headerkeys, types, widths, null);
		BigDecimal minus1 = new BigDecimal("-1");
		String shelfLifeDefault = library.getString("label.indefinite");

		pw.setColumnDigit(14, 4);
		for (int i = 15; i <= 21; i++)
			pw.setColumnDigit(i, 2);

		for (ExpiredInventoryViewBean member : data) {
			String shelfLife = shelfLifeDefault;
			if (isBlank(member.getShelfLifeDays())) shelfLife = "";
			else if (!member.getShelfLifeDays().equals(minus1)) shelfLife = library.format("format.totalshelfdaysfrom", member.getShelfLifeDays(), member.getShelfLifeBasis());

			pw.addRow();

			pw.addCell(member.getOperatingEntityName());
			pw.addCell(member.getHubName());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getItemId());
			pw.addCell(member.getClientPartNos());
			pw.addCell(member.getPartShortName());
			pw.addCell(member.getSpecs());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getLotStatus());
			pw.addCell(shelfLife);
			pw.addCell(member.getBin());
			pw.addCell(member.getExpireDate());
			pw.addCell(member.getQuantity());
			if (member.getUnitCost() == null) pw.addCell("");
			else
				pw.addCell(member.getCurrencyId());
			pw.addCell(member.getUnitCost());
			pw.addCell(member.getExpired());
			pw.addCell(member.getMonth1());
			pw.addCell(member.getMonth2());
			pw.addCell(member.getMonth3());
			pw.addCell(member.getMonth4());
			pw.addCell(member.getMonth5());
			pw.addCell(member.getMonth6());
		}
		return pw;
	}
	// for receipts has write off operations in this update. receipt id was a bigdecimal but used as a string in the proeceure.
	private HashSet<String> writeOffReceipts = new HashSet<String>();
	public boolean UpdateLine(LogisticsViewBean bean, String loginID) throws Exception {
		String lotupdate = bean.getLotStatus().equals(bean.getOldLotStatus()) ? "no" : "yes";

		Vector<String> errorMsgs = new Vector();
		boolean result = false;
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		String indefinite = library.getString("label.indefinite");
		SimpleDateFormat outformat = new SimpleDateFormat("MM/dd/yyyy", this.getLocaleObject());
		SimpleDateFormat informat = new SimpleDateFormat("dd-MMM-yyyy", this.getLocaleObject());
		// SqlHandler.delimitString() + "," +

		// DateHandler.getOracleToDateFunction(dbuyContractPriceOvBean.getUpdatedDate())
		// + "," +
		String Sel_status = StringHandler.emptyIfNull(bean.getLotStatus());// ==null?" ":bean.getLotStatus().trim());
		String notes = StringHandler.emptyIfNull(bean.getNotes());// .getLotStatus();//==null?" ":bean.getLotStatus().trim());

		String rootcause = StringHandler.emptyIfNull(bean.getLotStatusRootCause());// hD.get("TERMINAL_ROOT_CAUSE")==null?" ":hD.get("TERMINAL_ROOT_CAUSE").toString();
		String rootcausenotes = SqlHandler.validQuery(StringHandler.emptyIfNull(bean.getLotStatusRootCauseNotes()));// notes;//bean.gettgetcBothHelpObjs.changeSingleQuotetoTwoSingleQuote(rootcausenotes);
		String rootcausecompany = StringHandler.emptyIfNull(bean.getResponsibleCompanyId());// hD.get("TERMINAL_COMPANY")==null?" ":hD.get("TERMINAL_COMPANY").toString();
		String deliveryTicket = StringHandler.emptyIfNull(bean.getDeliveryTicket());// hD.get(
																					// "DELIVERY_TICKET"
																					// )==null?"":hD.get(
																					// "DELIVERY_TICKET"
																					// ).toString().trim();
		String bin_name1 = StringHandler.emptyIfNull(bean.getBin());// ==null?" ":bean.getBin().toString());
		String mfglot = SqlHandler.validQuery(StringHandler.emptyIfNull(bean.getMfgLot()));// BothHelpObjs.changeSingleQuotetoTwoSingleQuote(hD.get("MFG_LOT")==null?" ":hD.get("MFG_LOT").toString());
		String dateOfManufacture = "";
		;// hD.get( "DATE_OF_MANUFACTURE" )==null?"":hD.get(
			// "DATE_OF_MANUFACTURE" ).toString().trim();
		if (bean.getDateOfManufacture() != null) {
			dateOfManufacture = outformat.format(bean.getDateOfManufacture());
		}
		String dateOfShipment = "";// (hD.get("DATE_OF_SHIPMENT")==null?"":hD.get("DATE_OF_SHIPMENT").toString());
		if (bean.getDateOfShipment() != null) {
			dateOfShipment = outformat.format(bean.getDateOfShipment());
		}
		String dateOfRepackaging = "";
		;// hD.get( "DATE_OF_MANUFACTURE" )==null?"":hD.get(
			// "DATE_OF_MANUFACTURE" ).toString().trim();
		if (bean.getDateOfRepackaging() != null) {
			dateOfRepackaging = outformat.format(bean.getDateOfRepackaging());
		}
		String recertnum = StringHandler.emptyIfNull(bean.getRecertNumber());// ==null?" ":bean.getRecertNumber().toString());
		String qualitycntitem = StringHandler.emptyIfNull(bean.getQualityControlItem());// (
																						// hD.get(
																						// "QUALITY_CONTROL_ITEM"
																						// )
																						// ==
																						// null
																						// ?
																						// " "
																						// :
																						// hD.get(
																						// "QUALITY_CONTROL_ITEM"
																						// ).toString()
																						// );
		String certupdate = StringHandler.emptyIfNull(bean.getCertupdate());// .getcgetc(hD.get("CERTUPDATE")==null?" ":hD.get("CERTUPDATE").toString());
		String unitLabelCatPartNo = StringHandler.emptyIfNull(bean.getCatPartNo());// hD.get("UNIT_LABEL_CAT_PART_NO")==null?"":hD.get("UNIT_LABEL_CAT_PART_NO").toString();
		String oldLotStatus = StringHandler.emptyIfNull(bean.getOldLotStatus());
		String expiredateUpdate = StringHandler.emptyIfNull(bean.getExpireDateUpdate());// .getex(hD.get("EXPIREDATEUPDATE")==null?" ":hD.get("EXPIREDATEUPDATE").toString());

		String invQuantity = StringHandler.emptyIfNull(bean.getQuantity());// (hD.get("QUANTITY")==null?"":hD.get("QUANTITY").toString());
		String Oreceipt_id = StringHandler.emptyIfNull(bean.getReceiptId());// ==null?" ":bean.getReceiptId().toString());
		String expdate = StringHandler.emptyIfNull(bean.getExpireDateStr());
		String internalReceiptNotes = StringHandler.emptyIfNull(bean.getInternalReceiptNotes());
		String supplierCageCode = StringHandler.emptyIfNull(bean.getSupplierCageCode());
		String dateOfRepackagingStr = "";
		if (bean.getDateOfRepackaging() != null) dateOfRepackagingStr = outformat.format(bean.getDateOfRepackaging());

		String unitLabelPrinted = isBlank(bean.getUnitLabelPrinted()) ? "N" : bean.getUnitLabelPrinted();// hD.get("UNIT_LABEL_CAT_PART_NO")==null?"":hD.get("UNIT_LABEL_CAT_PART_NO").toString();
		String polchemIg = isBlank(bean.getPolchemIg()) ? "N" : bean.getPolchemIg();
		String doNumberRequired = isBlank(bean.getDoNumberRequired()) ? "N" : bean.getDoNumberRequired();
		String qaStatement = bean.getQualityTrackingNumber();// .getQaStatement();
		String customerReceiptId = bean.getCustomerReceiptId();

		int recertintnum = 0;
		boolean updaterecert = false;

		try {
			String recertStr = "" + bean.getRecertNumber();
			recertintnum = Integer.parseInt(recertStr);
			updaterecert = true;
		}
		catch (RuntimeException ee) {
			recertintnum = 0;
		}

		Date expDate = null;
		if (indefinite.equalsIgnoreCase(expdate)) {
			expdate = "01/01/3000";
		}
		else {
			expDate = informat.parse(expdate);
			expdate = outformat.format(expDate);
		}
		// logisticsLog.info( "Doing Update Satus From Logistics Page : " +
		// Sel_status + " Receipt Id" + Oreceipt_id + " Bin Name " + bin_name1 +
		// "  recertnum" + recertnum );
		CallableStatement cs = null;
		String updquery = "";
		try {
			if ("Customer Purchase".equalsIgnoreCase(Sel_status) || "Write Off Requested".equalsIgnoreCase(Sel_status) || "3PL Purchase".equalsIgnoreCase(Sel_status)) {
				if (isBlank(rootcause) || isBlank(rootcausecompany)) {
					result = false;
					return result;
				}
			}

			updquery = "update receipt set UNIT_LABEL_PRINTED='" + unitLabelPrinted + "',DELIVERY_TICKET='" + deliveryTicket + "',BIN='" + bin_name1 + "',EXPIRE_DATE=to_date('" + expdate + "'" + "," + "'MM/DD/YYYY'),MFG_LOT='" + mfglot
					+ "',LAST_MODIFIED_BY = '" + loginID + "'" + ",QUALITY_TRACKING_NUMBER = " + getSqlString(qaStatement) + ",INTERNAL_RECEIPT_NOTES = " + getSqlString(internalReceiptNotes) + ",MFG_LABEL_EXPIRE_DATE="
					+ (bean.getMfgLabelExpireDate() != null ? "to_date('" + outformat.format(bean.getMfgLabelExpireDate()) + "','MM/DD/YYYY')" : "''") + ",Customer_Receipt_Id = " + getSqlString(customerReceiptId) + "" + ",SUPPLIER_CAGE_CODE = '"
					+ supplierCageCode + "'";
			if (!"Incoming".equalsIgnoreCase(Sel_status)) {
				updquery += ",LOT_STATUS='" + Sel_status + "'";
			}

			if (dateOfManufacture.length() > 0) {
				updquery += ",DATE_OF_MANUFACTURE=to_date('" + dateOfManufacture + "'" + "," + "'MM/DD/YYYY')";
			}
			else {
				updquery += ",DATE_OF_MANUFACTURE=null";
			}

			if (dateOfShipment.length() > 0) {
				updquery += ",DATE_OF_SHIPMENT=to_date('" + dateOfShipment + "'" + "," + "'MM/DD/YYYY')";
			}
			else {
				updquery += ",DATE_OF_SHIPMENT=null";
			}

			if (dateOfRepackaging.length() > 0) {
				updquery += ",DATE_OF_REPACKAGING=to_date('" + dateOfRepackaging + "'" + "," + "'MM/DD/YYYY')";
			}
			else {
				updquery += ",DATE_OF_REPACKAGING=null";
			}

			if ("Customer Purchase".equalsIgnoreCase(Sel_status) || "Write Off Requested".equalsIgnoreCase(Sel_status) || "3PL Purchase".equalsIgnoreCase(Sel_status)) {
				updquery += ",LOT_STATUS_ROOT_CAUSE = '" + rootcause + "',RESPONSIBLE_COMPANY_ID = '" + rootcausecompany + "',LOT_STATUS_ROOT_CAUSE_NOTES= '" + rootcausenotes + "'";
			}

			if (updaterecert) {
				updquery += ",RECERT_NUMBER=" + recertnum + "";
			}

			if ("Y".equalsIgnoreCase(qualitycntitem) && "Yes".equalsIgnoreCase(certupdate)) {
				updquery += ",CERTIFICATION_DATE=SYSDATE, CERTIFIED_BY=" + loginID + "";
			}

			updquery += " where RECEIPT_ID = " + Oreceipt_id + " ";

			String insDelUnitLabelPart = "";
			if (unitLabelPrinted.equalsIgnoreCase("Y") && unitLabelCatPartNo.trim().length() > 0) {
				insDelUnitLabelPart = "insert into receipt_label_info(receipt_id, cat_part_no) values(" + "" + Oreceipt_id + "," + unitLabelCatPartNo + "" + ")";
			}
			else {
				insDelUnitLabelPart = "delete from receipt_label_info where receipt_id = " + Oreceipt_id + "";
			}

			try {
				java.sql.Connection connect1 = this.dbManager.getConnection();// db.getConnection();
				log.debug(updquery);
				cs = connect1.prepareCall(updquery);
				cs.execute();
				connect1.commit();

				if (polchemIg.equalsIgnoreCase("Y") && doNumberRequired.equalsIgnoreCase("N")) {
					log.debug(insDelUnitLabelPart);
					cs = connect1.prepareCall(insDelUnitLabelPart);
					cs.execute();
					connect1.commit();
				}
			}
			catch (Exception e) {
				// e.printStackTrace();
				new BulkMailProcess(this.getClient()).sendBulkEmail(new BigDecimal("86405"), "Error in Logistics Page Update", "This Update Statement gave error " + updquery + " \n\n " + e.getMessage() + "", false);
				throw e;
			}
			finally {
				if (cs != null) {
					try {
						cs.close();
					}
					catch (Exception se) {
						// se.printStackTrace();
					}
				}
			}

			try {
				if ("Yes".equalsIgnoreCase(expiredateUpdate)) {
					Connection connect1 = this.dbManager.getConnection();
					cs = connect1.prepareCall("{call PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset(?)}");
					cs.setInt(1, Integer.parseInt(Oreceipt_id));
					cs.execute();
					log.debug("call PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset " + Oreceipt_id);
				}
			}
			catch (Exception e) {
				// logisticsLog.info(
				// "\n\n--------- Erros in Receiving P_RECEIPT_ALLOCATE Procedure 1 \n\n"
				// );
				// e.printStackTrace();
				errorMsgs.addElement("Error Calling PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset for Receipt ID :" + Oreceipt_id + "");
				new BulkMailProcess(this.getClient()).sendBulkEmail(new BigDecimal("86405"), "Error calling Procedure PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset in HubInvenLabelsTables",
						"Error occured while running p_rec_full_sl_date_reset from logistics\n" + e.getMessage() + "\nFor Input Parameters Receipt Id: " + Oreceipt_id + "", false);
				// radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure PKG_DEFINED_SHELF_LIFE.p_rec_full_sl_date_reset in HubInvenLabelsTables","Error occured while running p_rec_full_sl_date_reset from logistics\n"
				// + e.getMessage() +"\nFor Input Parameters Receipt Id: " +
				// Oreceipt_id + "" );
				throw e;
			}
			finally {
				if (cs != null) {
					try {
						cs.close();
					}
					catch (Exception se) {
						// se.printStackTrace();
					}
				}
			}

			if ("yes".equalsIgnoreCase(lotupdate) || "Yes".equalsIgnoreCase(expiredateUpdate)) {
				log.info("Doing P_RECEIPT_ALLOCATE...for " + Oreceipt_id);
				try {
					Connection connect1 = dbManager.getConnection();// db.getConnection();
					// Connection connect1=db.getConnection();
					cs = connect1.prepareCall("{call P_RECEIPT_ALLOCATE(?, ?)}");
					cs.setInt(1, Integer.parseInt(Oreceipt_id));
					cs.setString(2, "A");
					cs.execute();
				}
				catch (Exception e) {
					// logisticsLog.info(
					// "\n\n--------- Erros in Receiving P_RECEIPT_ALLOCATE Procedure 1 \n\n"
					// );
					// e.printStackTrace();
					errorMsgs.addElement("Error Calling P_RECEIPT_ALLOCATE for Receipt ID :" + Oreceipt_id + "");
					// I don't know the database email yet.
					new BulkMailProcess(this.getClient()).sendBulkEmail(new BigDecimal("86405"), "Error calling Procedure P_RECEIPT_ALLOCATE in HubInvenLabelsTables", "Error occured while running P_RECEIPT_ALLOCATE from logistics\n" + e.getMessage()
							+ "\nFor Input Parameters Receipt Id: " + Oreceipt_id + "", false);
					// radian.web.emailHelpObj.senddatabaseHelpemails("Error calling Procedure P_RECEIPT_ALLOCATE in HubInvenLabelsTables","Error occured while running P_RECEIPT_ALLOCATE from logistics\n"
					// + e.getMessage() +"\nFor Input Parameters Receipt Id: " +
					// Oreceipt_id + "" );
					throw e;
				}
				finally {
					if (cs != null) {
						try {
							cs.close();
						}
						catch (Exception se) {
							// se.printStackTrace();
						}
					}
				}
			}

			if ("Write Off Requested".equalsIgnoreCase(Sel_status) && !oldLotStatus.equalsIgnoreCase("Write Off Requested")) {
				// write it off if not already been write off on this receipt.
				if(!writeOffReceipts.contains(Oreceipt_id)) {
					result = writeOffRequest(Oreceipt_id, invQuantity, "" + rootcause + " " + rootcausenotes + "", loginID);
					// disregarding the previous 'result', put into the hashset.
					writeOffReceipts.add(Oreceipt_id);
				}
			}

			if (!"Write Off Requested".equalsIgnoreCase(Sel_status) && oldLotStatus.equalsIgnoreCase("Write Off Requested")) {
				if(!writeOffReceipts.contains(Oreceipt_id)) {
					result = cancelWriteOffRequest(Oreceipt_id, loginID);
					writeOffReceipts.add(Oreceipt_id);
				}
			}
			if ("N".equalsIgnoreCase(bean.getManageKitsAsSingleUnit())) {
				log.info("Calling p_receipt_component fromLogistics for COMPONENT_ID " + bean.getComponentId() + " RECEIPT_ID " + bean.getReceiptId() + " Personnel ID  " + loginID + "");
				// /////////////////
				String errorcode = "";
				Connection connect1;
				// CallableStatement cs = null;

				String Item_id = bean.getItemId() == null ? " " : bean.getItemId().toString();
				String compID = bean.getComponentId() == null ? " " : bean.getComponentId().toString();
				String Mfg_lot = bean.getMfgLot() == null ? " " : bean.getMfgLot().toString();
				String Sel_bin = bean.getBin() == null ? " " : bean.getBin().toString();
				String Expiry_Date = expdate;

				// String invengrp= hD.get( "INVENTORY_GROUP" ) == null ? " " :
				// hD.get( "INVENTORY_GROUP" ).toString();

				try {
					connect1 = this.dbManager.getConnection();// db.getConnection();
					try {
						cs = connect1.prepareCall("{call p_receipt_component(?,?,?,?,?,?,?,?)}");
						cs.setInt(1, Integer.parseInt(Oreceipt_id));
						cs.setInt(2, Integer.parseInt(Item_id));
						cs.setInt(3, Integer.parseInt(compID));
						if (Mfg_lot.trim().length() > 0) {
							cs.setString(4, Mfg_lot);
						}
						else {
							cs.setNull(4, java.sql.Types.VARCHAR);
						}
						if (Sel_bin.trim().length() > 0) {
							cs.setString(5, Sel_bin);
						}
						else {
							cs.setNull(5, java.sql.Types.VARCHAR);
						}
						if (Expiry_Date.length() > 1) {
							if (expDate != null) cs.setTimestamp(6, new java.sql.Timestamp(expDate.getTime()));
							else
								cs.setTimestamp(6, new java.sql.Timestamp(outformat.parse(Expiry_Date).getTime()));
						}
						else {
							cs.setNull(6, java.sql.Types.DATE);
						}

						if (bean.getMfgLabelExpireDate() != null) {
							cs.setTimestamp(7, new java.sql.Timestamp(bean.getMfgLabelExpireDate().getTime()));
						}
						else {
							cs.setNull(7, java.sql.Types.DATE);
						}

						if (bean.getDateOfRepackaging() != null) {
							cs.setTimestamp(8, new java.sql.Timestamp(outformat.parse(dateOfRepackagingStr).getTime()));
						}
						else {
							cs.setNull(8, java.sql.Types.DATE);
						}

						// cs.registerOutParameter(21,java.sql.Types.VARCHAR);
						cs.execute();

						// errorcode =
						// BothHelpObjs.makeBlankFromNull(cs.getString(21));
						result = true;
					}
					catch (Exception e) {
						e.printStackTrace();
						result = false;
						new BulkMailProcess(this.getClient()).sendBulkEmail(new BigDecimal("86405"), "Error Calling Procedure call p_receipt_component in HubReceiving", "Error occured while running call p_receipt_component \nError Msg:\n" + e.getMessage()
								+ "\n\n Parameters passed P_receipt_INSERT receiptID " + Oreceipt_id + "\n Item_id " + Item_id + "\n Mfg_lot " + Mfg_lot + "\n Bin " + Sel_bin + "\n Expiry_Date " + Expiry_Date + "  personnel Id " + loginID + "\n", false);
						throw e;
					}
					finally {
						connect1.commit();
						if (cs != null) {
							try {
								cs.close();
							}
							catch (Exception se) {
								se.printStackTrace();
							}
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					result = false;
				}

				// result = radian.web.HTMLHelpObj.insreceiptcomp(db, hD,
				// receipt_id, logonid);
				// ////////////////////////
			}
			result = true;
		}
		catch (Exception e) {
			// e.printStackTrace();
			errorMsgs.addElement("Error for Receipt ID :" + Oreceipt_id + "");
			result = false;
		}

		return result;
	}

	public boolean writeOffRequest(String receiptId, String quantity, String writeOffComments, String PersonnelId) {
		Connection connect1;
		CallableStatement cs = null;
		boolean result = false;
		try {
			connect1 = this.dbManager.getConnection();// db.getConnection();
			// connect1 = db.getConnection();
			cs = connect1.prepareCall("{call PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request(?,SYSDATE,?,?,?,?)}");
			cs.setInt(1, Integer.parseInt(receiptId));
			// cs.setNull(2,java.sql.Types.DATE);
			cs.setInt(2, Integer.parseInt(PersonnelId));
			cs.setInt(3, Integer.parseInt(quantity));
			cs.setString(4, writeOffComments);
			cs.registerOutParameter(5, Types.VARCHAR);

			log.info("calling Procedure PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request: " + receiptId + " quantity " + quantity + " ");
			cs.execute();

			String errorMsg = cs.getString(5);

			if (errorMsg != null && !"ok".equalsIgnoreCase(errorMsg)) {
				log.info("Error in Procedure PKG_INVENTORY_ADJUSTMENT.p_create_inv_adj_request: " + receiptId + " Error Code " + errorMsg + " ");
			}

			connect1.commit();
			cs.close();
			result = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean cancelWriteOffRequest(String receiptId, String PersonnelId) {
		Connection connect1;
		CallableStatement cs = null;
		boolean result = false;
		try {
			connect1 = this.dbManager.getConnection();// db.getConnection();
			// connect1 = db.getConnection();
			cs = connect1.prepareCall("{call PKG_INVENTORY_ADJUSTMENT.p_cancel_write_off_request(?)}");
			cs.setInt(1, Integer.parseInt(receiptId));
			// cs.registerOutParameter(3, Types.VARCHAR);
			log.info("calling Procedure PKG_INVENTORY_ADJUSTMENT.p_cancel_write_off_request: " + receiptId + " ");
			cs.execute();

			/*
			 * String errorMsg = cs.getString(3);
			 * 
			 * if( errorMsg != null && !"ok".equalsIgnoreCase(errorMsg)) {
			 * logisticsLog.info(
			 * "Error in Procedure PKG_INVENTORY_ADJUSTMENT.p_cancel_write_off_request: "
			 * +receiptId+" Error Code "+errorMsg+" "); }
			 */

			connect1.commit();
			cs.close();
			result = true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Vector<BaseDataBean> getLotStatusColl(PersonnelBean pbean) throws BaseException {
		String query = "select * from vv_lot_status where ACTIVE='Y' and LOT_STATUS <> 'Incoming'";
		
		boolean disabledWriteOff = pbean.isFeatureReleased("DisabledWriteOff","All", pbean.getCompanyId());
		
		if(disabledWriteOff)
			query += " and LOT_STATUS NOT LIKE 'Write Off%'";

		query +=  " order by lot_status";
				
		return getSearchResult(query, new VvLotStatusBean());
	}

	public ExcelHandler getExcelReport(LogisticsInputBean inputBean, PersonnelBean pbean) throws NoDataException, BaseException, Exception {

		Vector<LogisticsViewBean> data = getSearchResultData(inputBean, pbean);

		ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(res);
		pw.addTable();

		try {
			Hashtable summary = new Hashtable();
			if (data.size() == 0) {
				StringBuffer Msg = new StringBuffer();
				Msg.append(res.getString("label.norecordfound"));
				pw.addCell(Msg.toString());
				return pw;
			}
			
			boolean disabledUnitCost = pbean.isFeatureReleased("DisabledUnitCost","All", pbean.getCompanyId());
			String[] headerkeys;
			int[] types;
			int[] widths;
			
			if(disabledUnitCost){
				headerkeys = new String[] { 
						"label.item", "label.receiptid", "label.description", "label.inventorygroup", "label.status",												//1
						"label.lotstatusage", "label.expdate", "label.bin", "label.lot", "label.receiptspecs", 														//2
						"label.quantity", "label.qtyreceived", "label.#labels", "label.ownercompanyid", 											//3
						"label.qualitynote","label.notes", "label.internalreceiptnotes", "receiving.label.deliveryticket", "receivedreceipts.label.manufacturerdos",//4
						"label.dom", "label.dateofrepackaging", "label.dor", "label.minexpdate", "label.originalreceiptid", 										//5
						"label.legacyreceiptid","label.po", "label.line", "label.rootcause",  "label.customerpo", "label.recertNo", 								//6
						"label.pendingadjustment", "label.lastprintdate", "label.storagetemp", "label.unitlabeledper129p", "label.pkg", 						//7
						"label.incomingtesting", "label.labelexpiredate", "label.cagecode", "label.dot", "label.partnumber"									//8
				};
				types = new int[] { 
						0, 0, pw.TYPE_PARAGRAPH, 16, 0,													//1
						0, pw.TYPE_CALENDAR, 0, 0, 0,													//2 
						0, 0, 0, 0, 																	//3						
						pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, pw.TYPE_CALENDAR, 	//4
						pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, 0,  	//5
						0, 0, 0, 0, 0, 0,																	//6
						0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH,									//7
						0, 0, 0, 0, 0																	//8
				};
				widths = new int[] {
						0, 0, 0, 0, 0, //1
						0, 0, 0, 0, 0, //2
						0, 0, 0, 0,  //3
						0, 0, 0, 0, 0, //4
						0, 0, 0, 0, 0, //5
						0, 0, 0, 0, 0, 0, //6
						0, 0, 0, 0, 0, //7
						0, 0 ,0, 0, 0			// 8
				};
			}else{
				headerkeys = new String[] { 
						"label.item", "label.receiptid", "label.description", "label.inventorygroup", "label.status",												//1
						"label.lotstatusage", "label.expdate", "label.bin", "label.lot", "label.receiptspecs", 														//2
						"label.quantity", "label.qtyreceived", "label.#labels", "label.ownercompanyid", "label.unitcost", "label.currency",							//3
						"label.qualitynote","label.notes", "label.internalreceiptnotes", "receiving.label.deliveryticket", "receivedreceipts.label.manufacturerdos",//4
						"label.dom", "label.dateofrepackaging", "label.dor", "label.minexpdate", "label.originalreceiptid", 										//5
						"label.legacyreceiptid","label.po", "label.line", "label.rootcause",  "label.customerpo", "label.recertNo", 								//6
						"label.pendingadjustment", "label.lastprintdate", "label.storagetemp", "label.unitlabeledper129p", "label.pkg", 						//7
						"label.incomingtesting", "label.labelexpiredate", "label.cagecode", "label.dot", "label.partnumber"									//8
				};
				types = new int[] { 
						0, 0, pw.TYPE_PARAGRAPH, 16, 0,													//1
						0, pw.TYPE_CALENDAR, 0, 0, 0,													//2 
						0, 0, 0, 0, 0, 0,																	//3
						pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, pw.TYPE_CALENDAR, 	//4
						pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, 0,  	//5
						0, 0, 0, 0, 0, 0,																	//6
						0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH,									//7
						0, 0, 0, 0, 0																	//8
				};
				widths = new int[] {
						0, 0, 0, 0, 0, //1
						0, 0, 0, 0, 0, //2
						0, 0, 0, 0, 0, 0, //3
						0, 0, 0, 0, 0, //4
						0, 0, 0, 0, 0, //5
						0, 0, 0, 0, 0, 0, //6
						0, 0, 0, 0, 0, //7
						0, 0 ,0, 0, 0	// 8
				};
			}

			pw.addRow();
			pw.addCellBold(res.getString("label.branchplant"));
			pw.addCell(inputBean.getHubName());
			pw.addRow();
			pw.addCellBold(res.getString("label.searchstring"));
			pw.addCell(StringHandler.emptyIfNull(inputBean.getSearchArgument()));
			pw.addRow();
			pw.addCellBold(res.getString("inventory.label.expireswithin"));
			pw.addCell(StringHandler.emptyIfNull(inputBean.getNumDaysOld()));
			pw.addRow();
			pw.applyColumnHeader(headerkeys, types, widths);

			int i = 0; // used for detail lines
			for (LogisticsViewBean bean : data) {
				i++;

				pw.addRow();
				// Group 1
				pw.addCell(bean.getItemId());
				pw.addCell(bean.getReceiptId());
				pw.addCell(bean.getItemDesc());
				pw.addCell(bean.getInventoryGroupName());
				pw.addCell(bean.getLotStatus());
				
				// Group 2
				pw.addCell(bean.getLotStatusAge());
				pw.addCell(bean.getExpireDate());
				pw.addCell(bean.getBin());
				pw.addCell(bean.getMfgLot());
				pw.addCell(bean.getSpecs());

				// Group 3
				pw.addCell(bean.getQuantity());
				pw.addCell(bean.getQuantityReceived());
				pw.addCell(bean.getNoOfLabels());
				if (StringUtils.isBlank(bean.getOwnerCompanyId()) || "Radian".equals(bean.getOwnerCompanyId()) ) {
					pw.addCell("Haas");
				}
				else {		
					pw.addCell(bean.getOwnerCompanyId());
				}
				if(!disabledUnitCost) {
					pw.addCell(bean.getUnitCost());
					pw.addCell(bean.getCurrencyId());
				}
				pw.addCell(bean.getQualityTrackingNumber());

				// Group 4
				pw.addCell(bean.getNotes());
				pw.addCell(bean.getInternalReceiptNotes());
				pw.addCell(bean.getDeliveryTicket());
				pw.addCell(bean.getDateOfShipment());
				pw.addCell(bean.getDateOfManufacture());
				
				// Group 5
				pw.addCell(bean.getDateOfRepackaging());
				pw.addCell(bean.getDateOfReceipt());
				pw.addCell(bean.getMinimumExpireDate());
				pw.addCell(bean.getOriginalReceiptId());
				pw.addCell(bean.getCustomerReceiptId());
				
				// Group 6
				pw.addCell(bean.getRadianPo());
				pw.addCell(bean.getPoLine());
				pw.addCell(bean.getLotStatusRootCause());
				pw.addCell(bean.getPoNumber());				
				pw.addCell(bean.getRecertNumber());
				
				// Group 7
				BigDecimal netPendingAdj = bean.getNetPendingAdj();
				if (netPendingAdj != null) {
					netPendingAdj = netPendingAdj.multiply(new BigDecimal("-1"));
				}
				pw.addCell(netPendingAdj);
				pw.addCell(bean.getLastPrintDate());
				pw.addCell(bean.getStorageTemp());
				pw.addCell(bean.getUnitLabelPrinted());
				pw.addCell(bean.getReceiptPackaging());

				// Group 8
				pw.addCell(bean.getIncomingTesting());
				if (bean.getMfgLabelExpireDate() != null && bean.getMfgLabelExpireDate().getTime() == 32503701600000l) {
					pw.addCell(res.getLabel("label.indefinite"));
				}
				else {
					pw.addCell(bean.getMfgLabelExpireDate());
				}
				pw.addCell(bean.getSupplierCageCode());
				pw.addCell(bean.getDot());
				pw.addCell(bean.getClientPartNos());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return pw;
	}
}
