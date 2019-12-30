package com.tcmis.ws.scanner.process;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.PickingUnit;
import com.tcmis.ws.scanner.beans.PickingUnitIssue;
import com.tcmis.ws.scanner.beans.ScannerPicklistViewBean;

public class PickableUnitsSearchProcess extends BaseScannerSearchProcess {

	private final String	ISSUE_ARRAY_NAME	= "receipts";
	private ResourceLibrary	library;

	public PickableUnitsSearchProcess(String client) {
		super(client);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	@SuppressWarnings("unchecked")
	private Collection<PickingUnitIssue> getPickableUnitIssues(JSONObject input, Collection<PickingUnitIssue> pickingUnitIds) throws BaseException, JSONException {
		Vector<PickingUnitIssue> puiColl = new Vector<PickingUnitIssue>();
		factory.setBean(new PickingUnitIssue());
		int count = 0;
		StringBuilder ids = new StringBuilder();
		for (PickingUnitIssue pickingUnit : pickingUnitIds) {
			count++;
			if (count > 1) {
				ids.append(",");
			}
			ids.append(pickingUnit.getPickingUnitId());
			if (count == 100) {
				puiColl.addAll(factory.selectQuery(getPUISQL(input, ids.toString())));
				count = 0;
				ids.setLength(0);
			}
		}
		if (count > 0) {
			puiColl.addAll(factory.selectQuery(getPUISQL(input, ids.toString())));
		}

		return puiColl;
	}

	public JSONArray getPickableUnits(JSONObject input) throws BaseException {
		JSONArray pickUnitArray = new JSONArray();
		Connection conn = dbManager.getConnection();
		try {
			setPagingConf(input);

			@SuppressWarnings("unchecked")
			Collection<PickingUnitIssue> pickingUnitIds = factory.setBean(new PickingUnitIssue()).selectQuery(getQuerySQL(input, false), conn);
			Collection<PickingUnitIssue> pickingUnits = Collections.emptyList();

			if (!pickingUnitIds.isEmpty()) {
				pickingUnits = getPickableUnitIssues(input, pickingUnitIds);
			}

			int pickingUnitId = 0;
			boolean optionalInvPickShown = false;
			int arrayIndex = -1;
			for (PickingUnitIssue pickingUnit : pickingUnits) {
				int puid = pickingUnit.getPickingUnitId().intValue();
				JSONObject jsonPickingUnit = null;
				if (puid == pickingUnitId) {
					jsonPickingUnit = pickUnitArray.getJSONObject(arrayIndex);
				}
				else {
					pickingUnitId = puid;
					optionalInvPickShown = false;
					arrayIndex++;
					PickingUnit pub = new PickingUnit();
					BeanHandler.copyAttributes(pickingUnit, pub);
					jsonPickingUnit = BeanHandler.getJsonObject(pub, false);

					JSONArray issueRay = new JSONArray();
					jsonPickingUnit.put(ISSUE_ARRAY_NAME, issueRay);

					pickUnitArray.put(jsonPickingUnit);
				}

				JSONArray receiptArray = null;
				if (jsonPickingUnit.has(ISSUE_ARRAY_NAME)) {
					receiptArray = jsonPickingUnit.getJSONArray(ISSUE_ARRAY_NAME);
				}
				else {
					receiptArray = new JSONArray();
				}
				ScannerPicklistViewBean picklistBean = new ScannerPicklistViewBean();
				BeanHandler.copyAttributes(pickingUnit, picklistBean);
				receiptArray.put(BeanHandler.getJsonObject(picklistBean));
				if (pickingUnit.isShowOptionalInvPick() && !optionalInvPickShown) {
					optionalInvPickShown = true;
					String optionalInvPickQuery = getOptionalInvPickQuery(pickingUnit);

					@SuppressWarnings("unchecked")
					Collection<ScannerPicklistViewBean> optionalPicks = factory.setBean(new ScannerPicklistViewBean()).selectQuery(optionalInvPickQuery);
					JSONArray optionals = new JSONArray();
					for (ScannerPicklistViewBean optionalPick : optionalPicks) {
						optionals.put(optionalPick.getReceiptId());
					}
					for (int i = 0; i < receiptArray.length(); i++) {
						receiptArray.getJSONObject(i).put("optional", optionals);
					}
				}
			}

			totalCount = pageCount = arrayIndex + 1;
			if (pageCount >= pageSize || page != 1) {
				String fullCount = factory.selectSingleValue(getQuerySQL(input, true), conn);
				totalCount = Integer.parseInt(fullCount);
			}
		}
		catch (DbSelectException e) {
			BaseException ex = new BaseException(library.getString(e.getMessageKey()));
			throw ex;
		}
		catch (JSONException e) {
			throw new BaseException(e);
		}
		finally {
			dbManager.returnConnection(conn);
		}

		return pickUnitArray;
	}

	protected String getOptionalInvPickQuery(PickingUnitIssue pickingUnit) {
		String query = "select receipt_id, 0 quantity, issue_id" + " from table(pkg_pick.fx_picklist_by_pu(" + pickingUnit.getPickingUnitId() + "))";

		return query;
	}

	protected String getPUISQL(JSONObject input, String puids) throws JSONException {
		String query = "SELECT pu.*, i.need_date, i.item_id, i.quantity, i.receipt_id, i.issue_id, i.company_id, " 
				+ "nvl(itr.item_type, rli.item_type) AS item_type, "
				+ "nvl(itr.catalog_id, rli.catalog_id) AS catalog_id, " 
				+ "rli.po_number, rli.fac_part_no, rli.delivery_point, " 
				+ "rli.notes AS rli_notes, "
				+ "nvl(fla.facility_id, rli.facility_id) AS facility_id, " 
				+ "rli.allocate_by_mfg_lot, "
				+ "NVL(trigd.location_id, rli.ship_to_location_id) AS ship_to_location_id, "
				+ "nvl(fla.application, rli.application) AS application, " 
				+ "nvl(itr.scrap, rli.scrap) AS scrap, " 
				+ "itr.comments AS transfer_comment, "
				+ "nvl(itr.cat_part_no, rli.fac_part_no) AS customer_part_number, " 
				+ "pr.end_user, pr.requestor, pr.Special_Instructions, pr.Internal_Note, PR.ORDER_SHIPTO_NOTE, "
				+ "fx_personnel_id_to_name(pr.requestor) requestor_name, " 
				+ "igd.show_optional_inv_pick, " 
				+ "pu.hub AS customer_facility, "
				+ "NVL(trigd.location_id, igd.location_id) AS ship_to, "
				+ "DECODE( rlie.rdd, '999', 'RDD: 999', NULL ) rdd_comment, "
				+ "customer.fx_fac_loc_app_part_comment( rli.company_id, rli.catalog_id, rli.fac_part_no, pr.facility_id, rli.application, rli.part_group_no, CHR( 10 ) ) fac_loc_app_part_comment, "
				+ "customer.fx_cat_part_comment( rli.company_id, rli.catalog_id, rli.fac_part_no, CHR( 10 ) ) cat_part_comment, "
				+ "fx_get_pu_customer_id (i.company_id,i.inventory_group, i.ops_entity_id,i.ops_company_id,rli.facility_id,pr.account_sys_id) customer_id "
				+ "FROM picking_unit pu, issue i, picking_unit_issue pui, customer.request_line_item rli, "
				+ "customer.purchase_request pr, inventory_group_definition igd, inventory_transfer_request itr, "
				+ "customer.request_line_item_extension rlie, customer.FAC_LOC_APP FLA, inventory_group_definition trigd "
				+ "WHERE pu.picking_unit_id = pui.picking_unit_id and i.issue_id(+) = pui.issue_id "
				+ "AND pu.pr_number = rli.pr_number(+) " 
				+ "AND pu.line_item = rli.line_item(+) " 
				+ "AND rli.pr_number = rlie.pr_number(+) "
				+ "AND rli.line_item = rlie.line_item(+) " 
				+ "AND rli.company_id = rlie.company_id(+) " 
				+ "AND pu.pr_number = pr.pr_number(+) and pu.picking_unit_id in (" + puids + ") " 
				+ "AND pu.pr_number = ITR.TRANSFER_REQUEST_ID(+) " 
				+ "AND ITR.DESTINATION_INVENTORY_GROUP = FLA.STOCKING_INVENTORY_GROUP(+) "
				+ "AND itr.CATALOG_COMPANY_ID = fla.company_id(+) " 
				+ "AND i.inventory_group = igd.inventory_group(+) " 
				+ "AND itr.destination_inventory_group = trigd.inventory_group(+) "
				+ "ORDER BY pu.picking_unit_id, i.receipt_id";
		return query;
	}

	protected String getQuerySQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "";
		String orderBy = "";
		if (!countOnly) {
			select = "select pu.picking_unit_id";
			orderBy = " order by pu.picking_unit_id";
		}
		else {
			select = "select pu.*";
		}

		String body = " from picking_unit pu";
		String where = " where";

		if (input.has("hub")) {
			body += where + " pu.hub = " + SqlHandler.delimitString(input.getString("hub"));
			where = " and";
		}

		if (input.has("filterDate")) {
			body += where + " pu.last_updated" + " >= " + DateHandler.getOracleToDateFunction((Date) input.get("filterDate"));
			// filterDate inserted in Action class by isFilterValid method
			where = " and";
		}
		
		// Don't return Samples
		body += where + " not exists (select null from picking_unit_sample pus where pus.picking_unit_id = pu.picking_unit_id)";
		return getWrappedSQL(select, body, orderBy, countOnly);
	}
}
