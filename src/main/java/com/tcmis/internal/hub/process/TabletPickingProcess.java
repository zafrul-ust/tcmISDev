package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.PicklistReprintViewBean;
import com.tcmis.internal.hub.beans.PicklistSelectionViewBean;
import com.tcmis.internal.hub.beans.TabletPickableUnitViewBean;
import com.tcmis.internal.hub.factory.IssueBeanFactory;
import com.tcmis.internal.hub.factory.PicklistSelectionViewBeanFactory;
import com.tcmis.internal.hub.factory.ReprintPicklistIdViewBeanFactory;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for cabinet definition
 * 
 * @version 1.0
 *****************************************************************************/
public class TabletPickingProcess extends GenericProcess {
	Log					log						= LogFactory.getLog(this.getClass());

	private final long	MILLISECONDS_PER_DAY	= 86400000;

	public TabletPickingProcess(String client) {
		super(client);
	}

	public TabletPickingProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getSearchResult(PicklistSelectionViewBean bean, PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());

		StringBuilder searchCriteria = new StringBuilder(" where");

		/*
		 * This is to avoid people from picking MRs that are waiting for freight
		 * advice
		 */
		if (bean.getPackingGroupId() != null) {
			searchCriteria.append(" v.packing_group_id IS NOT NULL AND ");
			searchCriteria.append(" v.consolidation_number IS NOT NULL");
		}
		else {
			searchCriteria.append(" v.packing_group_id IS NULL AND ");
			searchCriteria.append(" v.consolidation_number IS NULL");
		}

		if (bean.getHub() != null && bean.getHub().length() > 0) {
			searchCriteria.append(" and v.hub = ").append(SqlHandler.delimitString(bean.getHub()));
		}

		if (!StringHandler.isBlankString(bean.getInventoryGroup())) {
			searchCriteria.append(" and v.inventory_group = ").append(SqlHandler.delimitString(bean.getInventoryGroup()));
		}
		else {
			String invQuery = " select inventory_group from user_inventory_group uig where uig.personnel_id = " + personnelBean.getPersonnelId();
			if (personnelBean.getCompanyId() != null && personnelBean.getCompanyId().length() != 0)
				invQuery += " and uig.company_id = '" + personnelBean.getCompanyId() + "' ";
			if (bean.getHub() != null && bean.getHub().length() > 0)
				invQuery += " and uig.hub = '" + bean.getHub() + "' ";
			if (bean.getOpsEntityId() != null && bean.getOpsEntityId().length() > 0)
				invQuery += " and uig.ops_entity_id = '" + bean.getOpsEntityId() + "' ";
			searchCriteria.append(" and v.inventory_group in (").append(invQuery).append(")");
		}

		if ((null != bean.getTransportationMode()) && (bean.getTransportationMode().length() != 0)) {
			if (bean.getTransportationMode().equalsIgnoreCase("parcel")) {
				searchCriteria.append(" and LOWER(v.transportation_mode) = LOWER(");
				searchCriteria.append(SqlHandler.delimitString(bean.getTransportationMode())).append(")");
			}
			else if (bean.getTransportationMode().equalsIgnoreCase("ltltl")) {
				searchCriteria.append(" and LOWER(v.transportation_mode) <> 'parcel'");
			}
		}
		/*
		 * if (bean.getTransportationMode()!=null &&
		 * bean.getTransportationMode().length()>0) {
		 * criteria.addCriterion("transportationMode",
		 * SearchCriterion.EQUALS,bean.getTransportationMode()); }
		 * 
		 * if (bean.getCarrierCode()!=null && bean.getCarrierCode().length()>0)
		 * { criteria.addCriterion("carrierCode",
		 * SearchCriterion.EQUALS,bean.getCarrierCode()); }
		 */
		if (bean.getFacilityId() != null && bean.getFacilityId().length() > 0) {
			searchCriteria.append(" and v.facility_id = ").append(SqlHandler.delimitString(bean.getFacilityId()));
		}

		if (bean.getShowOCONUSonly() != null && bean.getShowOCONUSonly().length() > 0) {
			searchCriteria.append(" and v.oconus_flag = 'Y'");
		}

		if (bean.getShowHazardousOnly() != null && bean.getShowHazardousOnly().length() > 0) {
			searchCriteria.append(" and v.hazardous = 'Y'");
		}

		if (null != bean.getCustomerId()) {
			searchCriteria.append(" and v.customer_id = ").append(bean.getCustomerId());
		}

		if (null != bean.getCustomerServiceRepId()) {
			searchCriteria.append(" and v.customer_service_rep_id = ").append(bean.getCustomerServiceRepId());
		}

		String s = bean.getSearchArgument();
		if (s != null && !(s.trim().length() == 0)) {
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
			if (mode.equals("is"))
				searchCriteria.append(" and ").append(SqlHandler.validQuery(field)).append(" = ").append(SqlHandler.delimitString(s));
			if (mode.equals("contains"))
				searchCriteria.append(" and ").append("LOWER(").append(SqlHandler.validQuery(field)).append(") like LOWER('%").append(SqlHandler.validQuery(s)).append("%')");
			if (mode.equals("startsWith"))
				searchCriteria.append(" and ").append("LOWER(").append(SqlHandler.validQuery(field)).append(") like LOWER('").append(SqlHandler.validQuery(s)).append("%')");
			if (mode.equals("endsWith"))
				searchCriteria.append(" and ").append("LOWER(").append(SqlHandler.validQuery(field)).append(") like LOWER('%").append(SqlHandler.validQuery(s)).append("')");
		}

		if (bean.getExpireDays() != null && bean.getExpireDays().length() > 0 && !("pr_number".equals(bean.getSearchField()) && bean.getSearchArgument().length() > 0)) {
			searchCriteria.append(" and (v.expire_date > SYSDATE + ").append(bean.getExpireDays()).append(" OR v.scrap = 'y' OR v.expire_date IS NULL) ");
		}
		searchCriteria.append(" AND v.bin = b.bin(+) AND v.hub = b.branch_plant(+) AND b.branch_plant = r.hub(+) AND b.room = r.room(+)");

		StringBuilder sortCriteria = new StringBuilder(" order by ");
		if (bean.getSortBy() != null) {
			String order = "";
			if (bean.getSortBy().contains(",")) {
				String[] sorting = bean.getSortBy().split(",");
				for (String o : sorting) {
					if (!order.isEmpty()) {
						order += ",";
					}
					order += "v." + o;
				}
			}
			else {
				order = "v." + bean.getSortBy();
			}
			sortCriteria.append(order);
		}

		String selectFields = "v.UNIT_GROSS_WEIGHT_LB, v.DOT, v.OCONUS_FLAG, MIN( v.HAZMAT_ID_MISSING ) HAZMAT_ID_MISSING, SUM( v.PICK_QTY ) PICK_QTY, v.INVENTORY_GROUP, v.HUB, v.PR_NUMBER,"
				+ " v.LINE_ITEM, v.STOCKING_METHOD, v.DELIVERY_TYPE, v.RELEASE_DATE, v.NEED_DATE_PREFIX, v.NEED_DATE, v.APPLICATION, v.APPLICATION_DESC, v.FACILITY_ID, v.PART_DESCRIPTION,"
				+ "SUBSTR( v.PACKAGING, 1, 200 ) PACKAGING, v.CATALOG_ID, v.CAT_PART_NO, v.PART_GROUP_NO, v.COMPANY_ID, v.DELIVERY_POINT, v.SHIP_TO_LOCATION_ID, v.REQUESTOR, v.SCRAP, "
				+ "fx_mr_notes( v.COMPANY_ID, v.PR_NUMBER, v.LINE_ITEM, v.SHIPPED_AS_SINGLE, v.SEAVAN ) MR_NOTES, v.CRITICAL, v.ITEM_ID, v.CARRIER_CODE, v.PICKUP_TIME, v.STOP_NUMBER, "
				+ "v.TRAILER_NUMBER, v.PACKING_GROUP_ID, v.CARRIER_NAME, v.TRANSPORTATION_MODE, v.SHIP_TO_LOCATION_DESC, v.SHIP_TO_CITY, v.SHIP_TO_STATE_ABBREV, v.SHIP_TO_ZIP, "
				+ "v.MILSTRIP_CODE, v.SHIP_TO_DODAAC, v.REQUIRES_OVERPACK, v.SHIPPED_AS_SINGLE, v.CONSOLIDATION_NUMBER, v.MR_COUNT, v.TRANSPORTATION_PRIORITY, v.HAZARDOUS, v.SPLIT_TCN,"
				+ "v.ACK_SENT, v.AIR_GROUND_INDICATOR, v.CUSTOMER_SERVICE_REP_ID, v.CUSTOMER_SERVICE_REP_NAME, v.OPS_ENTITY_ID, v.OPERATING_ENTITY_NAME, v.CUSTOMER_ID, v.CUSTOMER_NAME,"
				+ "v.FACILITY_NAME, v.INVENTORY_GROUP_NAME, v.MATERIAL_REQUEST_ORIGIN, v.ADDRESS_LINE_1_DISPLAY, v.ADDRESS_LINE_2_DISPLAY, v.ADDRESS_LINE_3_DISPLAY, v.ADDRESS_LINE_4_DISPLAY,"
				+ "v.ADDRESS_LINE_5_DISPLAY, v.CMS, v.DISTRIBUTION, v.SHIPPING_REFERENCE, v.ADR_MISSING, v.DEST_INVENTORY_GROUP_NAME, v.ABC_CLASSIFICATION, v.DELIVERY_POINT_DESC,"
				+ "v.CATALOG_PRICE, v.CURRENCY_ID, r.room, r.room_description";

		String groupBy = "GROUP BY v.UNIT_GROSS_WEIGHT_LB, v.DOT, v.OCONUS_FLAG, v.INVENTORY_GROUP, v.HUB, v.PR_NUMBER, v.LINE_ITEM, v.STOCKING_METHOD, v.DELIVERY_TYPE, v.RELEASE_DATE, "
				+ "v.NEED_DATE_PREFIX, v.NEED_DATE, v.APPLICATION, v.APPLICATION_DESC, v.FACILITY_ID, v.PART_DESCRIPTION, SUBSTR( PACKAGING, 1, 200 ), v.CATALOG_ID, v.CAT_PART_NO, "
				+ "v.PART_GROUP_NO, v.COMPANY_ID, v.DELIVERY_POINT, v.SHIP_TO_LOCATION_ID, v.REQUESTOR, v.SCRAP, v.SEAVAN, v.CRITICAL, v.ITEM_ID, v.CARRIER_CODE, v.PICKUP_TIME, v.STOP_NUMBER, "
				+ "v.TRAILER_NUMBER, v.PACKING_GROUP_ID, v.CARRIER_NAME, v.TRANSPORTATION_MODE, v.SHIP_TO_LOCATION_DESC, v.SHIP_TO_CITY, v.SHIP_TO_STATE_ABBREV, v.SHIP_TO_ZIP, v.MILSTRIP_CODE, "
				+ "v.SHIP_TO_DODAAC, v.REQUIRES_OVERPACK, v.SHIPPED_AS_SINGLE, v.CONSOLIDATION_NUMBER, v.MR_COUNT, v.TRANSPORTATION_PRIORITY, v.HAZARDOUS, v.SPLIT_TCN, v.ACK_SENT, "
				+ "v.AIR_GROUND_INDICATOR, v.CUSTOMER_SERVICE_REP_ID, v.CUSTOMER_SERVICE_REP_NAME, v.OPS_ENTITY_ID, v.OPERATING_ENTITY_NAME, v.CUSTOMER_ID, v.CUSTOMER_NAME, v.FACILITY_NAME, "
				+ "v.INVENTORY_GROUP_NAME, v.MATERIAL_REQUEST_ORIGIN, v.ADDRESS_LINE_1_DISPLAY, v.ADDRESS_LINE_2_DISPLAY, v.ADDRESS_LINE_3_DISPLAY, v.ADDRESS_LINE_4_DISPLAY, "
				+ "v.ADDRESS_LINE_5_DISPLAY, v.CMS, v.DISTRIBUTION, v.SHIPPING_REFERENCE, v.ADR_MISSING, v.DEST_INVENTORY_GROUP_NAME, v.ABC_CLASSIFICATION, v.DELIVERY_POINT_DESC, "
				+ "v.CATALOG_PRICE, v.CURRENCY_ID , r.room, r.room_description";
		String selection = "select " + selectFields + " from PICKLIST_SELECTION_VIEW v, vv_hub_bins b, vv_hub_room r" + searchCriteria.toString() + " " + groupBy + " "
				+ sortCriteria.toString();

		Collection<PicklistSelectionViewBean> results = factory.setBean(new PicklistSelectionViewBean()).selectQuery(selection);

		Collection<PicklistSelectionViewBean> picks = new Vector<PicklistSelectionViewBean>();
		PicklistSelectionViewBean lastPick = new PicklistSelectionViewBean();
		for (PicklistSelectionViewBean pick : results) {
			if (lastPick.matches(pick)) {
				lastPick.addPick(pick);
			}
			else {
				picks.add(pick);
				lastPick = pick;
			}
		}
		
		return picks;
	}

	@SuppressWarnings("unchecked")
	public Object[] createPicklist(PicklistSelectionViewBean input, Collection<PicklistSelectionViewBean> inputBeans, PersonnelBean user, Locale locale) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		IssueBeanFactory issueFactory = new IssueBeanFactory(dbManager);
		BigDecimal picklistId = issueFactory.selectBatchNumber();
		Timestamp pickListTime = new Timestamp((new Date()).getTime());
		PicklistSelectionViewBeanFactory factory = new PicklistSelectionViewBeanFactory(dbManager);
		BigDecimal personnelId = new BigDecimal(user.getPersonnelId());
		PermissionBean userPermission = user.getPermissionBean();

		Vector errorCodes = new Vector();

		for (PicklistSelectionViewBean bean : inputBeans) {
			if (userPermission.hasInventoryGroupPermission("Picking", bean.getInventoryGroup(), null, null)) {
				Collection inArgs = new Vector(10);
				inArgs.add(picklistId);
				inArgs.add(bean.getCompanyId());
				inArgs.add(bean.getPrNumber());
				inArgs.add(bean.getLineItem());
				inArgs.add(bean.getHub());
				if (bean.getNeedDate() != null) {
					inArgs.add(new Timestamp(bean.getNeedDate().getTime()));
				}
				else {
					inArgs.add(new Timestamp(new Date().getTime()));
				}
				inArgs.add(personnelId);
				inArgs.add(pickListTime);

				Collection cout = new Vector();
				cout.add(new Integer(java.sql.Types.VARCHAR));

				Collection optionalInArgs = new Vector(3);
				optionalInArgs.add((!StringHandler.isBlankString(input.getExpireDays()) ? (new BigDecimal(input.getExpireDays())) : (new Integer(java.sql.Types.NULL))));
				if (input.getFromPickingPicklist() != null && !input.getFromPickingPicklist().equalsIgnoreCase("Y")) {
					optionalInArgs.add((null != bean.getPackingGroupId() ? bean.getPackingGroupId() : (new Integer(java.sql.Types.NULL))));
				}
				else {
					optionalInArgs.add(null);
				}
				optionalInArgs.add("Y");
				if (log.isDebugEnabled()) {
					log.debug("input Args for the Procedure: " + inArgs + cout + optionalInArgs);
				}
				Vector result = (Vector) factory.createPicklist(inArgs, cout, optionalInArgs, locale);
				if (result.size() > 0 && result.get(0) != null) {

					errorCodes.add((result).get(0));
					if (log.isDebugEnabled()) {
						log.debug("result for " + bean.getPrNumber() + "-" + bean.getLineItem() + ": " + (result).get(0).toString());
					}
				}
			}
		}
		Object[] objs = {picklistId, errorCodes};
		return objs;
	}

	public Collection<TabletPickableUnitViewBean> getPickableUnits(PicklistReprintViewBean input) throws BaseException {
		Collection<TabletPickableUnitViewBean> pickingUnits = null;
		String query = "select pu.*, ps.picking_state_desc, FX_PU_SHIPPING_LOCATION(pu.picking_unit_id) ship_to_location_desc from picking_unit pu, vv_picking_state ps where ";
		String psPortion = " and pu.picking_state = ps.picking_state";
		if (input.getPicklistId() != null) {
			query += "picklist_id = " + input.getPicklistId() + psPortion;
		}
		else if (input.getSearchArgument() != null && input.getSearchArgument().trim().length() > 0) {
			String mode = input.getSearchMode();
			String field = input.getSearchField();
			String where = " ";
			if (mode.equals("is")) {
				where += field + " = " + SqlHandler.delimitString(input.getSearchArgument());
			}
			else if (mode.equals("contains")) {
				where += field + " like " + SqlHandler.delimitString("%" + input.getSearchArgument() + "%");
			}
			else if (mode.equals("startsWith")) {
				where += field + " like " + SqlHandler.delimitString(input.getSearchArgument() + "%");
			}
			else if (mode.equals("endsWith")) {
				where += field + " like " + SqlHandler.delimitString("%" + input.getSearchArgument());
			}
			query += where + psPortion;
		}
		pickingUnits = factory.setBean(new TabletPickableUnitViewBean()).selectQuery(query);

		String roomQuery = "SELECT p.picking_unit_id, vhr.room, r.hub, r.bin FROM picking_unit_issue p, issue i, vv_hub_bins vhb, vv_hub_room vhr, receipt r "
				+ "WHERE p.picking_unit_id IN (SELECT picking_unit_id FROM picking_unit WHERE picklist_id = " + input.getPicklistId() + ") "
				+ "AND i.issue_id = p.issue_id AND r.receipt_id = i.receipt_id AND vhb.branch_plant(+) = r.hub AND vhb.bin(+) = r.bin  AND vhr.hub(+) = vhb.branch_plant AND vhr.room(+) = vhb.room";
		Collection<TabletPickableUnitViewBean> rooms = factory.selectQuery(roomQuery);
		for (TabletPickableUnitViewBean room : rooms) {
			for (TabletPickableUnitViewBean pickingUnit : pickingUnits) {
				if (pickingUnit.matches(room)) {
					pickingUnit.setRoom(room.getRoom());
					pickingUnit.setBin(room.getBin());
				}
			}
		}
		return pickingUnits;
	}

	public Collection<TabletPickableUnitViewBean> getPickingGroups(PicklistReprintViewBean input) throws BaseException {
		String query = "select picking_group_id, picking_group_name from picking_group where " + "hub = " + SqlHandler.delimitString(input.getHub()) + " and status = 'A'"
		// + " and company_id = " +
		// SqlHandler.delimitString(input.getCompanyId())
				+ " order by picking_group_name";
		Collection<TabletPickableUnitViewBean> pickingGroups = factory.setBean(new TabletPickableUnitViewBean()).selectQuery(query);

		return pickingGroups;
	}

	public void assignPickingGroups(Collection<TabletPickableUnitViewBean> beans, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			for (TabletPickableUnitViewBean bean : beans) {
				if (bean.isUpdate()) {
					String stmt = "update picking_unit set picking_group_id = " + bean.getPickingGroupId()
							+ ", picking_state = nvl((select auto_state from picking_group where picking_group_id = " + bean.getPickingGroupId() + "),picking_state)"
							+ ", last_updated_by = " + user.getPersonnelId() + " where picking_unit_id = " + bean.getPickingUnitId();

					factory.deleteInsertUpdate(stmt, conn);
				}
			}
		}
		catch (Exception e) {
			throw new BaseException(e);
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	public Collection getOpenPicklist(PicklistSelectionViewBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		ReprintPicklistIdViewBeanFactory factory = new ReprintPicklistIdViewBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
		/*
		 * This is to avoid people from picking MRs that are waiting for freight
		 * advice
		 */
		if (bean.getFromPickingPicklist() != null && bean.getFromPickingPicklist().equalsIgnoreCase("Y")) {
			criteria.addCriterion("packingGroupId", SearchCriterion.IS, "null");
		}
		else {
			criteria.addCriterion("packingGroupId", SearchCriterion.IS_NOT, "null");
		}

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("picklistPrintDate");
		sortCriteria.setSortAscending(false);

		return factory.selectDistinctPicklist(criteria, sortCriteria);
	}

	public ExcelHandler getExcelReport(PicklistSelectionViewBean bean, PersonnelBean personnelBean, Locale locale) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PicklistSelectionViewBean> data = getSearchResult(bean, personnelBean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		if (bean.getPackingGroupId() != null) {
			String[] headerkeys = {"label.inventorygroup", "label.facility", "label.carrier", "label.trailer", "label.stop", "label.pickuptime", "label.mode",
					"label.consolidationno", "label.ordercount", "label.shipto", "label.transportationpriority", "label.hazardous", "label.oconus", "label.bin", "label.mrline",
					"label.quantity", "label.type", "label.needed", "label.releasedate", "label.partnumber", "label.itemid", "label.type", "label.partdescription",
					"label.packaging", "label.notes", "label.dot"};

			int[] widths = {12, 12, 17, 0, 0, 18, 14, 15, 0, 0, 15, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15};
			int[] types = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_DATE, 0, 0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH,
					pw.TYPE_PARAGRAPH};
			int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);

			for (PicklistSelectionViewBean member : data) {
				pw.addRow();
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getFacilityName());
				pw.addCell(member.getCarrierName());
				pw.addCell(member.getTrailerNumber());
				pw.addCell(member.getStopNumber());
				pw.addCell(member.getPickupTime());
				pw.addCell(member.getTransportationMode());
				pw.addCell(member.getConsolidationNumber());
				pw.addCell(member.getMrCount());
				pw.addCell(StringHandler.emptyIfNull(member.getShipToLocationDesc()) + member.getShipToCity() + member.getShipToStateAbbrev());
				pw.addCell(member.getTransportationPriority());
				pw.addCell(member.getHazardous());
				pw.addCell(member.getOconusFlag());
				pw.addCell(member.getBin());
				pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
				pw.addCell(member.getPickQty());
				pw.addCell(member.getNeedDatePrefix());
				pw.addCell(member.getNeedDate());
				pw.addCell(member.getReleaseDate());
				pw.addCell(member.getCatPartNo());
				pw.addCell(member.getItemId());
				pw.addCell(member.getDeliveryType());
				pw.addCell(member.getPartDescription());
				pw.addCell(member.getPackaging());
				pw.addCell(member.getMrNotes());
				pw.addCell(member.getDot());
			}

		}
		else {
			String[] headerkeys = {"label.inventorygroup", "label.facility", "label.shipto", "label.requestor", "label.csr", "label.customer", "label.mrline", "label.releasedate",
					"label.partnumber", "label.type", "label.quantity", "label.type", "label.needed", "label.partdescription", "label.packaging", "label.notes", "label.workarea",
					"label.deliverypoint", "label.dot"};

			int[] widths = {12, 12, 12, 12, 12, 12, 12, 10, 12, 6, 9, 6, 12, 12, 22, 12, 12, 12, 15};
			int[] types = {0, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0};
			int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);

			for (PicklistSelectionViewBean member : data) {
				if ("USGOV".equals(member.getCompanyId()) && (!"0".equals(member.getLineItem()))) {

				}
				else {
					pw.addRow();
					pw.addCell(member.getInventoryGroupName());
					pw.addCell(member.getFacilityName());
					if ("Y".equals(member.getDistribution()))
						pw.addCell(StringHandler.emptyIfNull(member.getAddressLine1Display()) + " " + StringHandler.emptyIfNull(member.getAddressLine2Display()) + " "
								+ StringHandler.emptyIfNull(member.getAddressLine3Display()) + " " + StringHandler.emptyIfNull(member.getAddressLine4Display()) + " "
								+ StringHandler.emptyIfNull(member.getAddressLine5Display()));
					else if (member.getLineItem().equals("0"))
						pw.addCell(member.getDestInventoryGroupName() + "-" + member.getApplication());
					else
						pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getRequestor());
					pw.addCell(member.getCustomerServiceRepName());
					pw.addCell(member.getCustomerName());
					pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
					pw.addCell(member.getReleaseDate());
					pw.addCell(member.getCatPartNo());
					pw.addCell(member.getStockingMethod());
					pw.addCell(member.getPickQty());
					pw.addCell(member.getNeedDatePrefix());
					pw.addCell(member.getNeedDate());
					pw.addCell(member.getPartDescription());
					pw.addCell(member.getPackaging());
					pw.addCell(member.getMrNotes());
					if (member.getLineItem().equals("0"))
						pw.addCell(member.getApplication());
					else
						pw.addCell(member.getApplicationDesc());
					pw.addCell(member.getDeliveryPointDesc());
					pw.addCell(member.getDot());
				}
			}
		}
		return pw;
	}

	public ExcelHandler getPickableUnitViewExcelReport(PicklistReprintViewBean bean, Locale locale) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<TabletPickableUnitViewBean> data = getPickableUnits(bean);
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();
		pw.addCellKeyBold("label.picklistid");
		if (!data.isEmpty()) {
			pw.addCell(data.stream().findFirst().get().getPicklistId());
		}

		pw.addRow();
		pw.addRow();

		String[] headerkeys = new String[] {"label.pickingunit", "label.hub", "label.mrline", "label.pickinggroup", "label.pickingstate"};

		int[] widths = new int[] {18, 18, 18, 18, 18};

		int[] types = new int[] {0, 0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH};

		int[] aligns = new int[] {0, 0, 0, 0, 0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);
		for (TabletPickableUnitViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getPickingUnitId());
			pw.addCell(member.getHub());
			pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
			pw.addCell(member.getPickingGroupId());
			pw.addCell(member.getPickingState());
		}
		return pw;
	}
}
