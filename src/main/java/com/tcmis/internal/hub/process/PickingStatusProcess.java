package com.tcmis.internal.hub.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.ZplHandler;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LabelFormatTemplateFileBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.PickingGroupBean;
import com.tcmis.internal.hub.beans.PickingStateViewBean;
import com.tcmis.internal.hub.beans.PickingStatusInputBean;
import com.tcmis.internal.hub.beans.PickingStatusViewBean;
import com.tcmis.internal.hub.beans.PickingUnitDocument;
import com.tcmis.internal.hub.factory.LabelFormatFieldDefinitionBeanFactory;
import com.tcmis.ws.scanner.beans.IataDotLabel;
import com.tcmis.ws.scanner.beans.Pick;
import com.tcmis.ws.scanner.beans.ScannerPicklistViewBean;
import com.tcmis.ws.scanner.beans.ShippingSampleDeliveryLabel;
import com.tcmis.ws.scanner.process.PickRejectProcess;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for cabinet definition
 * 
 * @version 1.0
 *****************************************************************************/
public class PickingStatusProcess extends GenericProcess {
	
	private static String LINE_FEED = "";
	static {
		LINE_FEED += (char) (13);
		LINE_FEED += (char) (10);
	}
	Log log = LogFactory.getLog(this.getClass());

	public PickingStatusProcess(String client) {
		super(client);
	}

	public PickingStatusProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection<PickingStateViewBean> getPickingStateColl() throws BaseException {
		String query = "select * from vv_picking_state order by sort_order";

		Collection<PickingStateViewBean> pickingStateColl = factory.setBean(new PickingStateViewBean()).selectQuery(query);
		return pickingStateColl;
	}

	public Collection<PickingStatusViewBean> getSearchResult(PickingStatusInputBean input) throws BaseException {
		String opsId = input.getOpsEntityId();
		String query = "select pu.*, i.inventory_group, i.date_delivered, i.need_date, i.item_id, i.quantity, i.receipt_id, i.issue_id, pu.tablet_shipment_id, i.picklist_print_date AS pick_creation_date, "
				+ "DECODE (rli.scrap, 'y', 'SCRAP', rli.item_type) AS item_type, rli.catalog_id, rli.po_number, rli.fac_part_no, rli.delivery_point, rli.notes, rli.facility_id, "
				+ "rli.allocate_by_mfg_lot, rli.ship_to_location_id, rli.application, "
				+ "pr.end_user, pg.picking_group_name, ps.picking_state_desc, ps.picking_status_pg_assignable, pr.requestor, "
				+ "igd.show_optional_inv_pick, ps.override_option, puv.qc_notes, puv.pick_notes, pud.pdoc, " 
				+ "fx_catalog_desc(rli.company_id, rli.catalog_id) AS catalog_desc, "
				+ "fx_application_desc(rli.facility_id, rli.application, rli.company_id) AS application_desc, " 
				+ "fx_personnel_id_to_name(pr.requestor) AS requestor_name, "
				+ "fx_facility_name(rli.facility_id, rli.company_id) AS facility_name, "
				+ "fx_personnel_id_to_name(puv.pick_personnel_id) AS picker_name, "
				+ "fx_personnel_id_to_name(puv.qc_personnel_id) AS packer_name "
				+ "FROM picking_unit pu, issue i, picking_unit_issue pui, request_line_item rli, purchase_request pr, "
				+ "inventory_group_definition igd, picking_group pg, vv_picking_state ps, picking_unit_validation puv, " 
				+ "picking_unit_pdoc pud "
				+ "WHERE pu.picking_unit_id = pui.picking_unit_id " 
				+ "and i.issue_id(+) = pui.issue_id " 
				+ "and puv.picking_unit_id(+) = pu.picking_unit_id "
				+ "and pud.picking_unit_id(+) = pu.picking_unit_id "
				+ "and pu.line_item = rli.line_item(+) " 
				+ "and pu.pr_number = rli.pr_number(+) " 
				+ "and pu.pr_number = pr.pr_number(+) "
				+ "and pu.picking_group_id = pg.picking_group_id " 
				+ "and pu.picking_state = ps.picking_state " 
				+ "and i.inventory_group = igd.inventory_group(+)"
				+ (this.isBlank(opsId)?"":" and i.ops_entity_id = " + this.getSqlString(opsId));
		query = filterQuery(query, input);
		query += " order by pu.picking_unit_id,"; // always order by picking_unit.
		if (input.isPickingStateOrder()) {
			query += "pu.picking_state";
		}
		else if (input.isFacilityOrder()) {
			query += "facility_name, application_desc";
		}
		else if (input.isCustomerPartNoOrder()) {
			query += "rli.fac_part_no";
		}
		else if (input.isDeliveryPointOrder()) {
			query += "rli.delivery_point";
		}
		else if (input.isItemOrder()) {
			query += "i.item_id";
		}
		else if (input.isNeedDateOrder()) {
			query += "i.need_date";
		}
		else if (input.isPrNumberOrder()) {
			query += "pu.pr_number DESC, pu.line_item";
		}
		else if (input.isShipToLocationOrder()) {
			query += "rli.ship_to_location_id";
		}
		else if (input.isPicklistOrder()) {
			query += "pu.picklist_id, i.receipt_id";
		}
		else {
			query += "i.receipt_id";
		}
		
		@SuppressWarnings("unchecked")
		Collection<PickingStatusViewBean> pickingColl = factory.setBean(new PickingStatusViewBean()).selectQuery(query);
		List<PickingStatusViewBean> resultList = new ArrayList<PickingStatusViewBean>();

		PickingStatusViewBean currentPick = null;
		int pickingUnitId = 0;
		HashSet<BigDecimal> docSet = new HashSet();
		String beanKey ="";
		HashMap<String,PickingStatusViewBean> pickBeanMap = null;// new HashMap();
		for (PickingStatusViewBean pick : pickingColl) {
			if (currentPick == null || !currentPick.matches(pick)) {
				pickBeanMap = new HashMap();
				if (currentPick != null) {
					String pdocS = "";
					for (BigDecimal ss : docSet)
						if (ss != null) {
							if (pdocS.length() == 0)
								pdocS += ss;
							else
								pdocS += "," + ss;
						}
					currentPick.setPdocs(pdocS);
					docSet = new HashSet();
				}
				currentPick = pick;
				docSet.add(pick.getPdoc());

// Larry Note: Daniel made a comment this was there for specific reason, and we don't know it should be merged/grouped or not.				
				if (pick.isShowOptionalInvPick() && pick.getPickingUnitId().intValue() != pickingUnitId) {
					pickingUnitId = pick.getPickingUnitId().intValue();
					String optionalInvPickQuery = "select receipt_id, 0 quantity from table(pkg_pick.fx_picklist_by_pu("
							+ pick.getPickingUnitId() + "))";

					Collection<ScannerPicklistViewBean> oipColl = factory.setBean(new ScannerPicklistViewBean())
							.selectQuery(optionalInvPickQuery);
					for (ScannerPicklistViewBean oip : oipColl) {
						PickingStatusViewBean statusBean = new PickingStatusViewBean();
						BeanHandler.copyAttributes(pick, statusBean);
						statusBean.setReceiptId(oip.getReceiptId());
						statusBean.setQuantity(oip.getQuantity());
						resultList.add(statusBean);
					}
				}
			} else 
				docSet.add(pick.getPdoc());
			beanKey = pick.getReceiptId()+"=="+pick.getQuantity();
			if(pickBeanMap.get(beanKey) == null ) {
				pickBeanMap.put(beanKey, pick);
				resultList.add(pick);
			}
		}
		// The last row.
		if (currentPick != null) {
			String pdocS = "";
			for (BigDecimal ss : docSet)
				if (ss != null) {
					if (pdocS.length() == 0)
						pdocS += ss;
					else
						pdocS += "," + ss;
				}
			currentPick.setPdocs(pdocS);
		}
		resultList.addAll(getSampleSearchResult(input));
		
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<PickingStatusViewBean> getSampleSearchResult(PickingStatusInputBean input) throws BaseException {
		StringBuilder searchCriteriaQuery = new StringBuilder("");
		if (input.hasPickCreationFromDate())
			searchCriteriaQuery.append(" and pu.pick_date >= ").append(DateHandler.getOracleToDateFunction(input.getPickCreationFromDate()));
		if (input.hasPickCreationToDate())
			searchCriteriaQuery.append(" and pu.pick_date <= ").append(DateHandler.getOracleToDateFunction(input.getPickCreationToDate()));
		if (input.hasPickingState() && input.isPickingStateShipping())
			searchCriteriaQuery.append(" and pu.picking_state = 'PRE_SHIP'");
		if (input.hasSearchText() && (input.isReceiptSearch() || input.isItemSearch() || input.isPickingUnitIdSearch())) {
			searchCriteriaQuery.append(" and LOWER(");
			if (input.isReceiptSearch())
				searchCriteriaQuery.append("r.receipt_id");
			else if (input.isItemSearch())
				searchCriteriaQuery.append("r.item_id");
			else if (input.isPickingUnitIdSearch())
				searchCriteriaQuery.append("pus.picking_unit_id");
			searchCriteriaQuery.append(") ").append(input.getSearchComparisonAndValue());
		}
		if (searchCriteriaQuery.length() == 0)
			return new ArrayList<PickingStatusViewBean>();

		StringBuilder query = new StringBuilder("select pus.*, pu.pick_date pick_creation_date, pu.picking_state, pu.tablet_shipment_id, r.item_id, 'Sample' item_type, r.inventory_group, fx_facility_name(pus.facility_id, pus.company_id) facility_name from picking_unit pu, picking_unit_sample pus, receipt r");
		query.append(" where pus.picking_unit_id = pu.picking_unit_id");
		query.append(" and pus.receipt_id = r.receipt_id");
		if (input.hasSourceHub())
			query.append(" and pu.hub = ").append(SqlHandler.delimitString(input.getSourceHub()));
		query.append(searchCriteriaQuery);
		
		query.append(" order by pus.picking_unit_id"); // always order by picking_unit.
		if (input.isFacilityOrder())
			query.append(", facility_name");
		else if (input.isItemOrder())
			query.append(", r.item_id");
		else if (input.isShipToLocationOrder())
			query.append(", pus.ship_to_location_id");
		else
			query.append(", r.receipt_id");
		
		factory.setBean(new PickingStatusViewBean());
		return factory.selectQuery(query.toString());
	}

	public Collection<PickingGroupBean> getPickingGroups(PickingStatusInputBean input) throws BaseException {
		String query = "select picking_group_id, picking_group_name, hub from picking_group";
		query += " where status != 'I'";
		if (!StringHandler.isBlankString(input.getSourceHub())) {
			query += " and hub = " + SqlHandler.delimitString(input.getSourceHub());
		}
		query += " order by hub, picking_group_name";
		Collection<PickingGroupBean> pickingGroups = factory.setBean(new PickingGroupBean()).selectQuery(query);

		return pickingGroups;
	}

	public void updatePickingStatus(PickingStatusInputBean input, PersonnelBean user, Collection<PickingStatusViewBean> pickingStatusColl) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			for (PickingStatusViewBean bean : pickingStatusColl) {
				if (bean.isOk()) {
					if (bean.isPickingStateRejected()) {
						PickRejectProcess rejectProcess = new PickRejectProcess(getClient());
						Pick pick = new Pick();
						BeanHandler.copyAttributes(bean, pick);
						pick.setPickPersonnelId(user.getPersonnelIdBigDecimal());
						JSONArray results = rejectProcess.rejectPick(pick, new JSONArray());
						if (results.length() > 0) {
							throw new BaseException(results.getJSONObject(0).getString("errorMessage"));
						}
						else {
							String query = "update picking_unit set rejection_comment = " + SqlHandler.delimitString(bean.getRejectionComment())
									+ " where picking_unit_id = " + bean.getPickingUnitId();
							factory.deleteInsertUpdate(query, conn);
						}
					}
					else {
						String query = "update picking_unit set picking_state = " + SqlHandler.delimitString(bean.getPickingState()) + ", picking_group_id = "
								+ bean.getPickingGroupId() + ", last_updated_by = " + user.getPersonnelId() + ", rejection_comment = " + SqlHandler.delimitString(bean.getRejectionComment()) 
								+ " where picking_unit_id = " + bean.getPickingUnitId();
						factory.deleteInsertUpdate(query, conn);
					}
				}
			}
		}
		catch (JSONException e) {
			// Ignore, we generated the JSON automatically there should never be
			// an error.
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	public void updatePickingStatusShipmentOverride(PickingStatusInputBean input, PersonnelBean user) throws BaseException {
		String query = "update picking_unit set dot_override = " + SqlHandler.delimitString(input.getDotOverride()) 
				+ ", shipping_override_apprvl_date = sysdate"
				+ ", shipping_override_approver = " + user.getPersonnelId();
		if (!StringHandler.isBlankString(input.getDotOverride()))
			query += ", hazardous_override = 'N'";
		else
			query += ", hazardous_override = 'Y'";
		query += " where picking_unit_id = " + input.getPickingUnitId();
		factory.deleteInsertUpdate(query);
	}
	
	public Collection<PickingUnitDocument> getPickingDocuments(PickingStatusInputBean input) throws BaseException {
		String query = "SELECT * from picking_unit_document " + "WHERE picking_unit_id in ("
				+ " SELECT pu.picking_unit_id from picking_unit pu, issue i, picking_unit_issue pui, request_line_item rli " + " WHERE "
				+ " pui.picking_unit_id = pu.picking_unit_id and pui.issue_id = i.issue_id(+)" + " and pu.line_item = rli.line_item(+) and pu.pr_number = rli.pr_number(+) ";
		query = filterQuery(query, input) + ")";

		Collection<PickingUnitDocument> pickingDocColl = factory.setBean(new PickingUnitDocument()).selectQuery(query);

		return pickingDocColl;
	}

	private String filterQuery(String query, PickingStatusInputBean input) {
		if (input.hasSourceHub()) {
			query += " and pu.hub = " + SqlHandler.delimitString(input.getSourceHub());
		}
		if (input.hasFilterDate()) {
			query += " and i.need_date" + " <= " + DateHandler.getOracleToDateFunction(input.getFilterDate());
		}
		if (input.hasSearchText()) {
			if (input.isPdocSearch()) {
				query += " and pu.picking_unit_id in (select picking_unit_id from picking_unit_pdoc where pdoc " + input.getSearchComparisonAndValue() + ")";
			}
			else {
				query += " and LOWER(" + input.getSearchField() + ") " + input.getSearchComparisonAndValue();
			}
		}
		if (input.hasPickingGroupId()) {
			query += " and pu.picking_group_id = " + input.getPickingGroupId();
		}
		if (input.hasPickCreationFromDate()) {
			query += " and i.picklist_print_date >= " + DateHandler.getOracleToDateFunction(input.getPickCreationFromDate());
		}
		if (input.hasPickCreationToDate()) {
			query += " and i.picklist_print_date <= " + DateHandler.getOracleToDateFunction(input.getPickCreationToDate());
		}
		if (input.hasPickingState()) {
			if (input.isPickingStateOpen()) {
				query += " and pu.picking_state not in ('SHIP_CONFIRMED', 'REJECTED')";
			}
			else if (input.isPickingStateHold()) {
				query += " and pu.picking_state in ('PICK_HOLD', 'QC_HOLD')";
			}
			else if (input.isPickingStatePicking()) {
				query += " and pu.picking_state in ('PICK_READY', 'PICKING')";
			}
			else if (input.isPickingStatePacking()) {
				query += " and pu.picking_state in ('PACK_READY', 'PACK_PROGRESS')";
			}
			else if (input.isPickingStateQC()) {
				query += " and pu.picking_state in ('QC_READY', 'QC_PROGRESS')";
			}
			else if (input.isPickingStateShipping()) {
				query += " and pu.picking_state in ('PRE_SHIP', 'DEPARTURE_READY', 'SHIP_READY')";
			}
			else {
				query += " and pu.picking_state = " + SqlHandler.delimitString(input.getPickingState());
			}
		}
		return query;
	}
	
	private void addLabelZPL(StringBuilder zpl, PickingStatusInputBean input, LabelFormatTemplateFileBean template, Collection<LabelFormatFieldDefinitionBean> fieldDefinitions,
			BaseDataBean bean) throws BaseException {
		// Tell the ZPL printer what template we are using
		zpl.append("^XA").append(LINE_FEED);
		zpl.append("^XF").append(template.getLabelFormat()).append("^FS").append(LINE_FEED);
		zpl.append(LINE_FEED);
		// Add the data to the label
		for (LabelFormatFieldDefinitionBean field : fieldDefinitions) {
			zpl.append("^").append(field.getLabelFieldId()).append("^FD").append(BeanHandler.getField(bean, field.getLabelFieldContent().toLowerCase())).append("^FS")
					.append(LINE_FEED);
		}
		// Add the print quantity and finish the label
		zpl.append("^PQ").append(input.getLabelPrintQty()).append(LINE_FEED);
		zpl.append("^XZ").append(LINE_FEED);
	}
	
	private LabelFormatTemplateFileBean getLabelTemplate(PickingStatusInputBean input, LabelInputBean labelInput, Connection conn) throws BaseException {
		String query = "SELECT y.*, z.label_template" + " FROM label_format_template_file y, label_format_template z" + "  WHERE EXISTS ("
				+ " 	SELECT label_format FROM inventory_group_label_format x" + " 	WHERE x.INVENTORY_GROUP IN ("
				+ " 		SELECT r.inventory_group FROM receipt r, issue i, picking_unit_issue pui WHERE r.receipt_id = i.receipt_id AND I.ISSUE_ID = PUI.ISSUE_ID AND pui.picking_unit_id = "
				+ input.getPickingUnitId() + " 	)" + " 	AND x.label_type = " + SqlHandler.delimitString(input.getLabelPrintType()) + " 	AND x.LABEL_FORMAT = y.LABEL_FORMAT)"
				+ " 	AND y.printer_resolution_dpi IN ("
				+ " 		SELECT	llp.printer_resolution_dpi FROM	printer p, location_label_printer llp WHERE llp.hub = p.hub AND llp.printer_path = P.PRINTER_PATH AND p.printer_path = "
				+ SqlHandler.delimitString(labelInput.getPrinterPath()) + " 	)" + " 	AND y.label_template_filename = z.label_template_filename";
		
		@SuppressWarnings("unchecked")
		Collection<LabelFormatTemplateFileBean> labels = factory.setBean(new LabelFormatTemplateFileBean()).selectQuery(query);
		if ( ! labels.isEmpty()) {
			return labels.iterator().next();
		}
		log.error("Error retrieving label template");
		return null;
	}
	
	private Collection<BaseDataBean> getDotIataData(PickingStatusInputBean input, Connection conn) throws BaseException {
		String query = "SELECT distinct i.item_id, fx_part_iata (i.item_id,null,null,null,null,null,'N','N','N') iata, fx_part_dot (i.item_id) dot"
				+ " FROM picking_unit_issue pui, issue i WHERE i.issue_id = pui.issue_id AND pui.picking_unit_id = "
				+ input.getPickingUnitId();
		
		return factory.setBean(new IataDotLabel()).selectQuery(query, conn);
	}
	
	@SuppressWarnings("unchecked")
	public String printLabel(PickingStatusInputBean input, LabelInputBean labelInput, LabelFormatTemplateFileBean template, Collection<BaseDataBean> data, Connection conn) throws Exception {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("labelFormat", SearchCriterion.EQUALS, template.getLabelFormat());
		criteria.addCriterion("printerResolutionDpi", SearchCriterion.EQUALS, template.getPrinterResolutionDpi().toString());
		Collection<LabelFormatFieldDefinitionBean> fieldDefinitions = new LabelFormatFieldDefinitionBeanFactory(dbManager).select(criteria, conn);

		StringBuilder zpl = new StringBuilder();
		zpl.append(template.getLabelTemplate()).append(LINE_FEED);
		
		// Add the ZPL for each label
		for (BaseDataBean label : data)
			addLabelZPL(zpl, input, template, fieldDefinitions, label);

		String printer = factory.selectSingleValue("select cups_name from printer where printer_path = " + SqlHandler.delimitString(labelInput.getPrinterPath()));
		try {
			PrintHandler.printString(printer, zpl.toString());
			return "";
		} catch (BaseException e) {}
		
		ResourceLibrary resource = new ResourceLibrary("label");
		File tempDir = new File(resource.getString("label.serverpath"));
		File jnlp = File.createTempFile(template.getLabelFormat() + "jnlp", ".jnlp", tempDir);
		File testFile = File.createTempFile(template.getLabelFormat(), ".txt", tempDir);
		
		PrintWriter pw = new PrintWriter(new FileOutputStream(testFile.getAbsolutePath()));
		pw.println(zpl.toString());
		pw.close();
		
		ZplHandler.writeJnlpFileToDisk(jnlp.getAbsolutePath(), testFile.getName(), labelInput.getPrinterPath(), zpl.toString());
		
		return resource.getString("label.hosturl") + resource.getString("label.urlpath") + jnlp.getName();
	}
	
	public String printIataDotLabel(PickingStatusInputBean input, LabelInputBean labelInput) throws BaseException {
		Connection conn = dbManager.getConnection();
		String filePath = null;
		try {
			LabelFormatTemplateFileBean template = getLabelTemplate(input, labelInput, conn);
			if (template != null) {
				Collection<BaseDataBean> data = getDotIataData(input, conn);
				filePath = printLabel(input, labelInput, template, data, conn);
			}
		} catch(Exception e) {
			throw new BaseException(e);
		} finally {
			dbManager.returnConnection(conn);
		}
		
		return filePath;
	}

	@SuppressWarnings("unchecked")
	private LabelFormatTemplateFileBean getSampleLabelTemplate(PickingStatusInputBean input, LabelInputBean labelInput, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT y.*, z.label_template FROM label_format_template_file y, label_format_template z WHERE EXISTS (");
		query.append(" SELECT label_format FROM inventory_group_label_format x WHERE x.INVENTORY_GROUP IN ( SELECT r.inventory_group FROM receipt r, picking_unit_sample pus WHERE r.receipt_id = pus.receipt_id");
		query.append(" AND pus.picking_unit_id = ").append(input.getPickingUnitId()).append(")");
		query.append(" AND x.label_type = ").append(SqlHandler.delimitString(input.getLabelPrintType()));
		query.append(" AND x.LABEL_FORMAT = y.LABEL_FORMAT)");
		query.append(" AND y.printer_resolution_dpi IN (SELECT llp.printer_resolution_dpi FROM printer p, location_label_printer llp WHERE llp.hub = p.hub AND llp.printer_path = P.PRINTER_PATH");
		query.append(" AND p.printer_path = ").append(SqlHandler.delimitString(labelInput.getPrinterPath())).append(")");
		query.append("	AND y.label_template_filename = z.label_template_filename");
		
		Collection<LabelFormatTemplateFileBean> labels = null;
		if (conn != null)
			labels = factory.setBean(new LabelFormatTemplateFileBean()).selectQuery(query.toString(), conn);
		else
			labels = factory.setBean(new LabelFormatTemplateFileBean()).selectQuery(query.toString());
		try {
			return labels.iterator().next();
		} catch (Exception e) {
			log.error("Error retrieving label template");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<BaseDataBean> getShippingSampleData(PickingStatusInputBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("select pus.picking_unit_id, pus.ship_to_location_id, nvl(fx_location_name(pus.ship_to_location_id), pus.ship_to_location_id) delivery_point_desc, sysdate date_printed");
		query.append(", pu.hub, fx_hub_name(pu.hub) hub_name, nvl(fx_facility_name(pus.facility_id, pus.company_id), pus.facility_id) facility_name, pus.receipt_id, pus.quantity");
		query.append(" from picking_unit_sample pus, picking_unit pu where pus.picking_unit_id = pu.picking_unit_id");
		query.append(" and pus.picking_unit_id = ").append(input.getPickingUnitId());
		Collection coll = factory.setBean(new ShippingSampleDeliveryLabel()).selectQuery(query.toString(), conn);
		Iterator<ShippingSampleDeliveryLabel> itr = coll.iterator();
		while (itr.hasNext()) {
			ShippingSampleDeliveryLabel label = itr.next();
			label.setBarcode("SA|PUI" + label.getPickingUnitId().toPlainString() + "|" + label.getReceiptId().toPlainString()  + "|" + label.getQuantity().toPlainString() + "|" + label.getShipToLocationId() + "|" + label.getHub());
		}
		
		return coll;
	}
	
	public String printSampleDeliveryLabel(PickingStatusInputBean input, LabelInputBean labelInput) throws Exception {
		Connection conn = dbManager.getConnection();
		String filePath = null;
		try {
			LabelFormatTemplateFileBean template = getSampleLabelTemplate(input, labelInput, conn);
			if (template != null) {
				Collection<BaseDataBean> data = getShippingSampleData(input, conn);
				filePath = printLabel(input, labelInput, template, data, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
		
		return filePath;
	}

	public ExcelHandler getExcelReport(PickingStatusInputBean bean, PersonnelBean personnelBean, Locale locale) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<PickingStatusViewBean> data = getSearchResult(bean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();

		String[] headerkeys = {"label.pickingunit", "label.pickinggroup", "label.picklist", "label.pdoc", "label.pickcreationdate", "label.pickingstate", "label.mrline", "label.needed", "label.item", "label.receipt",
				"label.qty", "label.itemtype", "label.catalog", "label.ponumber", "label.customerpartnumber", "label.deliverypoint", "label.notes", "label.holdcomments", "label.facility", "label.inventorygroup",
				"label.lotcontrolled", "label.shipto", "label.workarea", "label.enduser", "label.requestor", "label.packeroverride", "label.labeloverride", "label.qtyoverride", "label.dotoverride",
				"label.picker", "label.datepicked", "label.packer","label.packeddate","label.datedelivered"};

		int[] widths = {0, 0, 0, 0, 10, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 10, 10};
		int[] types = {0, 0, 0, 0, pw.TYPE_DATE, 0, 0, pw.TYPE_DATE, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,pw.TYPE_DATE, 0, pw.TYPE_DATE, pw.TYPE_DATE};
		int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.ALIGN_CENTER,0, pw.ALIGN_CENTER, 0,  0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (PickingStatusViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getPickingUnitId());
			pw.addCell(member.getPickingGroupName());
			pw.addCell(member.getPicklistId());
			pw.addCell(member.getPdocs());
			pw.addCell(member.getPickCreationDate());
			pw.addCell(member.getPickingStateDesc());
			pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
			pw.addCell(member.getNeedDate());
			pw.addCell(member.getItemId());
			pw.addCell(member.getReceiptId());
			pw.addCell(member.getQuantity());
			pw.addCell(member.getItemType());
			pw.addCell(member.getCatalogId());
			pw.addCell(member.getPoNumber());
			pw.addCell(member.getFacPartNo());
			pw.addCell(member.getDeliveryPoint());
			pw.addCell(member.getNotes());
			pw.addCell(member.getHoldComments());
			pw.addCell(member.getFacilityId());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getAllocateByMfgLot());
			pw.addCell(member.getShipToLocationId());
			pw.addCell(member.getApplicationDesc());
			pw.addCell(member.getEndUser());
			pw.addCell(member.getRequestorName());
			pw.addCell(member.isPackerOverride() ? "Y" : "N");
			pw.addCell(member.isLabelOverride() ? "Y" : "N");
			pw.addCell(member.isQtyOverride() ? "Y" : "N");
			pw.addCell(member.getDotOverride());
			pw.addCell(member.getPickerName());
			pw.addCell(member.getPickDate());
			pw.addCell(member.getPackerName());
			pw.addCell(member.getPackDate());
			pw.addCell(member.getDateDelivered());
		}

		return pw;
	}
}
