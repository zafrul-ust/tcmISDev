package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.FlowDown;
import com.tcmis.ws.scanner.beans.InboundShipment;
import com.tcmis.ws.scanner.beans.KitComponent;
import com.tcmis.ws.scanner.beans.ReceivingReceipt;
import com.tcmis.ws.scanner.beans.UserCompanyFacilityBean;

public class InboundShipmentSearchProcess extends BaseScannerSearchProcess {
	public InboundShipmentSearchProcess(String client) {
		super(client);
	}

	public JSONArray getPOs(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundShipment());
		setPagingConf(input);

		StringBuilder query = new StringBuilder("select * FROM table (tcm_ops.pkg_ios_scanner.fx_open_po ('" + input.getString("hub") + "', 'OPEN PO'");
		if (input.has("filterDate")) {
			query.append(", ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}
		query.append("))");
		query.append(" order by radian_po, line_item, item_id, part_id");

		@SuppressWarnings("unchecked")
		Collection<InboundShipment> shipments = factory.selectQuery(query.toString());
		Collection<FlowDown> flowDowns = getPOFlowDowns(shipments);
		flowDowns.addAll(getInventoryGroupFlowDowns(shipments));

		JSONObject po = null;
		JSONArray lines = null;
		JSONObject line = null;
		JSONArray items = null;
		BigDecimal currentPO = null;
		BigDecimal currentLine = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);
		for (InboundShipment shipment : shipments) {
			if (!shipment.getRadianPo().equals(currentPO)) {
				currentPO = shipment.getRadianPo();
				currentLine = shipment.getLineItem();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				pageCount++;
				po = new JSONObject();
				lines = new JSONArray();
				line = new JSONObject();
				items = new JSONArray();
				po.put("po", shipment.getRadianPo());
				po.put("hub", shipment.getHub());
				po.put("inventoryGroup", shipment.getInventoryGroup());
				po.put("lines", lines);
				line.put("lineItem", shipment.getLineItem());
				line.put("items", items);
				JSONArray currentFlowDowns = new JSONArray();
				for (FlowDown flowdown : flowDowns) {
					if (flowdown.isForShipment(shipment)) {
						currentFlowDowns.put(flowdown.getJSON());
					}
				}
				line.put("flowdowns", currentFlowDowns);
				lines.put(line);
				results.put(po);
			}
			else if (!shipment.getLineItem().equals(currentLine)) {
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				currentLine = shipment.getLineItem();
				line = new JSONObject();
				items = new JSONArray();
				line.put("lineItem", shipment.getLineItem());
				line.put("items", items);
				JSONArray currentFlowDowns = new JSONArray();
				for (FlowDown flowdown : flowDowns) {
					if (flowdown.isForShipment(shipment)) {
						currentFlowDowns.put(flowdown.getJSON());
					}
				}
				line.put("flowdowns", currentFlowDowns);
				lines.put(line);
			}
			if (totalCount < currentPageStart || totalCount > currentPageEnd) {
				continue;
			}
			shipment.setRadianPo(null);
			shipment.setLineItem(null);
			shipment.setHub(null);
			shipment.setInventoryGroup(null);
			items.put(BeanHandler.getJsonObject(shipment, false));
		}

		return results;
	}


	public JSONArray getTransfers(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundShipment());
		setPagingConf(input);

		StringBuilder query = new StringBuilder("select * FROM table (tcm_ops.pkg_ios_scanner.fx_open_po ('" + input.getString("hub") + "', 'TRANSFER'");
		if (input.has("filterDate")) {
			query.append(", ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}
		query.append("))");

		query.append(" order by transfer_request_id, original_receipt_id, item_id, part_id");

		@SuppressWarnings("unchecked")
		Collection<InboundShipment> shipments = factory.selectQuery(query.toString());
		Collection<FlowDown> flowDowns = getPOFlowDowns(shipments);
		flowDowns.addAll(getInventoryGroupFlowDowns(shipments));

		JSONObject transfer = null;
		JSONArray items = null;
		BigDecimal currentTransfer = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);
		for (InboundShipment shipment : shipments) {
			if (!shipment.getTransferRequestId().equals(currentTransfer)) {
				currentTransfer = shipment.getTransferRequestId();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				pageCount++;
				transfer = new JSONObject();
				items = new JSONArray();
				transfer.put("transferRequestId", shipment.getTransferRequestId());
				transfer.put("hub", shipment.getHub());
				transfer.put("inventoryGroup", shipment.getInventoryGroup());
				transfer.put("items", items);
				JSONArray currentFlowDowns = new JSONArray();
				for (FlowDown flowdown : flowDowns) {
					if (flowdown.isForShipment(shipment)) {
						currentFlowDowns.put(flowdown.getJSON());
					}
				}
				transfer.put("flowdowns", currentFlowDowns);
				results.put(transfer);
			}
			if (totalCount < currentPageStart || totalCount > currentPageEnd) {
				continue;
			}
			shipment.setTransferRequestId(null);
			shipment.setHub(null);
			shipment.setInventoryGroup(null);
			JSONObject item = BeanHandler.getJsonObject(shipment, false);
			if (shipment.hasOriginalReceiptId()) {
				item.put("originalReceipt", getOriginalReceipt(shipment.getOriginalReceiptId()));
			}

			items.put(item);
		}

		return results;
	}

	public JSONArray getCITRs(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundShipment());
		setPagingConf(input);

		StringBuilder query = new StringBuilder("select * FROM table (tcm_ops.pkg_ios_scanner.fx_open_po ('" + input.getString("hub") + "', 'CITR'");
		if (input.has("filterDate")) {
			query.append(", ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}
		query.append("))");

		query.append(" order by doc_num, item_id, part_id");

		@SuppressWarnings("unchecked")
		Collection<InboundShipment> shipments = factory.selectQuery(query.toString());
		Collection<FlowDown> flowDowns = getCITRFlowDowns(shipments);
		flowDowns.addAll(getInventoryGroupFlowDowns(shipments));
		JSONObject CITR = null;
		JSONArray items = null;
		BigDecimal currentDocNum = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);
		for (InboundShipment shipment : shipments) {
			if (!shipment.getDocNum().equals(currentDocNum)) {
				currentDocNum = shipment.getDocNum();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				pageCount++;
				CITR = new JSONObject();
				items = new JSONArray();
				CITR.put("customerPo", shipment.getCustomerPo());
				CITR.put("docNum", shipment.getDocNum());
				CITR.put("ownerCompanyId", shipment.getOwnerCompanyId());
				CITR.put("hub", shipment.getHub());
				CITR.put("customerReceiptId", shipment.getCustomerReceiptId());
				CITR.put("ownerCompanyId", shipment.getOwnerCompanyId());
				CITR.put("ownerSegmentId", shipment.getOwnerSegmentId());
				CITR.put("catalogCompanyId", shipment.getCatalogCompanyId());
				CITR.put("catalogId", shipment.getCatalogId());
				CITR.put("catPartNo", shipment.getCatPartNo());
				CITR.put("accountNumber", shipment.getAccountNumber());
				CITR.put("accountNumber2", shipment.getAccountNumber2());
				CITR.put("accountNumber3", shipment.getAccountNumber3());
				CITR.put("accountNumber4", shipment.getAccountNumber4());
				CITR.put("inventoryGroup", shipment.getInventoryGroup());
				CITR.put("items", items);
				JSONArray currentFlowDowns = new JSONArray();
				for (FlowDown flowdown : flowDowns) {
					if (flowdown.isForShipment(shipment)) {
						currentFlowDowns.put(flowdown.getJSON());
					}
				}
				CITR.put("flowdowns", currentFlowDowns);
				results.put(CITR);
			}
			if (totalCount < currentPageStart || totalCount > currentPageEnd) {
				continue;
			}
			shipment.setCustomerPo(null);
			shipment.setDocNum(null);
			shipment.setOwnerCompanyId(null);
			shipment.setHub(null);
			shipment.setInventoryGroup(null);
			items.put(BeanHandler.getJsonObject(shipment, false));
		}

		return results;
	}

	public JSONArray getRMAs(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new InboundShipment());
		setPagingConf(input);

		StringBuilder query = new StringBuilder("select * FROM table (tcm_ops.pkg_ios_scanner.fx_open_po ('" + input.getString("hub") + "', 'CUSTOMER RETURN'");
		if (input.has("filterDate")) {
			query.append(", ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}
		query.append("))");

		query.append(" order by CUSTOMER_RMA_ID, ORIGINAL_RECEIPT_ID, ITEM_ID, PART_ID");

		@SuppressWarnings("unchecked")
		Collection<InboundShipment> beans = factory.selectQuery(query.toString());
		JSONObject rma = null;
		JSONArray originalReceipts = null;
		JSONObject originalReceipt = null;
		JSONArray items = null;
		BigDecimal currentRMA = null;
		BigDecimal currentOriginalReceipt = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);
		for (InboundShipment bean : beans) {
			if (!bean.getCustomerRmaId().equals(currentRMA)) {
				currentRMA = bean.getCustomerRmaId();
				currentOriginalReceipt = bean.getOriginalReceiptId();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				pageCount++;
				rma = new JSONObject();
				originalReceipts = new JSONArray();
				originalReceipt = new JSONObject();
				items = new JSONArray();
				rma.put("customerRmaId", bean.getCustomerRmaId());
				rma.put("hub", bean.getHub());
				rma.put("inventoryGroup", bean.getInventoryGroup());
				rma.put("originalReceipts", originalReceipts);
				// originalReceipt.put("originalReceiptId",
				// bean.getOriginalReceiptId());
				originalReceipt.put("items", items);
				originalReceipts.put(originalReceipt);
				results.put(rma);
			}
			else if (!bean.getOriginalReceiptId().equals(currentOriginalReceipt)) {
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				currentOriginalReceipt = bean.getOriginalReceiptId();
				originalReceipt = new JSONObject();
				items = new JSONArray();
				// originalReceipt.put("originalReceiptId",
				// bean.getOriginalReceiptId());
				originalReceipt.put("items", items);
				originalReceipts.put(originalReceipt);
			}
			if (totalCount < currentPageStart || totalCount > currentPageEnd) {
				continue;
			}
			bean.setCustomerRmaId(null);
			// bean.setOriginalReceiptId(null);
			bean.setHub(null);
			bean.setInventoryGroup(null);
			JSONObject item = BeanHandler.getJsonObject(bean, false);
			if (bean.hasOriginalReceiptId()) {
				item.put("originalReceipt", getOriginalReceipt(bean.getOriginalReceiptId()));
			}

			items.put(item);
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getOriginalReceipt(BigDecimal originalReceiptId) throws BaseException {
		factory.setBean(new ReceivingReceipt());

		String query = "SELECT r.*, r.mfg_lot lot FROM RECEIPT r WHERE RECEIPT_ID = " + originalReceiptId;

		Collection<ReceivingReceipt> beans = factory.selectQuery(query);

		factory.setBean(new KitComponent());
		for (ReceivingReceipt bean : beans) {
			if (bean.isSeparableKit()) {
				query = "SELECT rc.*,mfg_lot lot FROM RECEIPT_COMPONENT rc WHERE RECEIPT_ID = " + originalReceiptId
						+ " ORDER BY RECEIPT_ID, PART_ID";
				bean.setKitComponents(factory.selectQuery(query));
			}
			return BeanHandler.getJsonObject(bean, false);
		}

		return null;
	}

	private Collection<FlowDown> getCITRFlowDowns(Collection<InboundShipment> shipments) throws BaseException {
		int chunkSize = 200;
		Vector<String> docNums = new Vector<String>();
		for (InboundShipment shipment : shipments) {
			docNums.add("" + shipment.getDocNum());
		}

		factory.setBean(new FlowDown());
		Vector<FlowDown> flowDowns = new Vector<FlowDown>();
		int count = 0;
		StringBuilder currentDocNums = new StringBuilder();
		for (String docNum : docNums) {
			count++;
			if (count > 1) {
				currentDocNums.append(",");
			}
			currentDocNums.append(docNum);
			if (count == chunkSize) {
				flowDowns.addAll(factory.selectQuery(getCITRFlowDownSQL(currentDocNums.toString())));
				count = 0;
				currentDocNums.setLength(0);
			}
		}
		if (count > 0) {
			flowDowns.addAll(factory.selectQuery(getCITRFlowDownSQL(currentDocNums.toString())));
		}

		return flowDowns;
	}
	
	private Collection<FlowDown> getPOFlowDowns(Collection<InboundShipment> shipments) throws BaseException {
		int chunkSize = 200;
		Vector<String> radianPos = new Vector<String>();
		for (InboundShipment shipment : shipments) {
			radianPos.add("" + shipment.getRadianPo());
		}

		factory.setBean(new FlowDown());
		Vector<FlowDown> flowDowns = new Vector<FlowDown>();
		int count = 0;
		StringBuilder currentDocNums = new StringBuilder();
		for (String docNum : radianPos) {
			count++;
			if (count > 1) {
				currentDocNums.append(",");
			}
			currentDocNums.append(docNum);
			if (count == chunkSize) {
				flowDowns.addAll(factory.selectQuery(getPoFlowdownSQL(currentDocNums.toString())));
				count = 0;
				currentDocNums.setLength(0);
			}
		}
		if (count > 0) {
			flowDowns.addAll(factory.selectQuery(getPoFlowdownSQL(currentDocNums.toString())));
		}

		return flowDowns;
	}

	private Collection<FlowDown> getInventoryGroupFlowDowns(Collection<InboundShipment> shipments) throws BaseException {
		int chunkSize = 200;
		HashMap<String, String> inventoryGroups = new HashMap<String, String>();
		for (InboundShipment shipment : shipments) {
			inventoryGroups.put(shipment.getInventoryGroup(), "Y");
		}

		factory.setBean(new FlowDown());
		Vector<FlowDown> flowDowns = new Vector<FlowDown>();
		int count = 0;
		StringBuilder currentInventoryGroups = new StringBuilder();
		for (String inventoryGroup : inventoryGroups.keySet()) {
			count++;
			if (count > 1) {
				currentInventoryGroups.append(",");
			}
			currentInventoryGroups.append(SqlHandler.delimitString(inventoryGroup));
			if (count == chunkSize) {
				flowDowns.addAll(factory.selectQuery(getInventoryGroupFlowDownSQL(currentInventoryGroups.toString())));
				count = 0;
				currentInventoryGroups.setLength(0);
			}
		}
		if (count > 0) {
			flowDowns.addAll(factory.selectQuery(getInventoryGroupFlowDownSQL(currentInventoryGroups.toString())));
		}

		return flowDowns;
	}

	private String getInventoryGroupFlowDownSQL(String inventoryGroups) {
		String query = " SELECT   null FLOW_DOWN,";
		query += "         qual_stmnt flow_down_desc,";
		query += "         'Vendor Qualification' flow_down_type,";
		query += "         NULL REVISION_DATE,";
		query += "         NULL content,";
		query += "         NULL CURRENT_VERSION,";
		query += "         NULL CATALOG_ID,";
		query += "         'HAAS' COMPANY_ID,";
		query += "         inventory_group,";
		query += "         NULL item_id,";
		query += "         NULL part_id";
		query += "  FROM   ig_vendor_qual_stmnt";
		query += " WHERE   inventory_group in (" + inventoryGroups + ")";
		query += " UNION";
		query += " SELECT   ";
		query += "        null FLOW_DOWN,";
		query += "         c.QUALITY_CONTROL_ITEM_I18N_LBL flow_down_desc,";
		query += "         'Quality Control' flow_down_type,";
		query += "         NULL REVISION_DATE,";
		query += "         NULL content,";
		query += "         NULL CURRENT_VERSION,";
		query += "         C.CATALOG_ID,";
		query += "         C.COMPANY_ID,";
		query += "         cpi.inventory_group,";
		query += "         cpic.item_id,";
		query += "         cpic.part_id";
		query += "  FROM   catalog_part_inventory cpi,";
		query += "         catalog_part_item_component cpic,";
		query += "         customer.catalog c";
		query += " WHERE       c.QUALITY_CONTROL_ITEM_I18N_LBL IS NOT NULL";
		query += "         AND cpi.CATALOG_COMPANY_ID = c.company_id";
		query += "         AND Cpi.CATALOG_ID = c.catalog_id";
		query += "         AND cpi.inventory_group in (" + inventoryGroups + ")";
		query += "         AND cpi.quality_control = 'Y'";
		query += "         AND cpic.company_id = cpi.company_id";
		query += "         AND cpic.catalog_company_id = cpi.catalog_company_id";
		query += "         AND cpic.catalog_id = cpi.catalog_id";
		query += "         AND cpic.cat_part_no = cpi.cat_part_no";
		query += "         AND cpic.part_group_no = cpi.part_group_no";
		return query;
	}

	private String getCITRFlowDownSQL(String docNums) {
		String query = " SELECT";
		query += "         f.flow_down,";
		query += "         f.flow_down_desc,";
		query += "         f.flow_down_type,";
		query += "         f.revision_date,";
		query += "         f.content,";
		query += "         f.current_version,";
		query += "         f.catalog_id,";
		query += "         f.company_id,";
		query += "         citr.doc_num";
		query += " FROM";
		query += "         vv_flow_down f,";
		query += "         fac_item_flow_down fifd,";
		query += "         catalog_company cc,";
		query += "         customer_inventory_to_receive citr";
		query += " WHERE";
		query += "         f.company_id = fifd.company_id";
		query += "         AND f.catalog_id = fifd.catalog_id";
		query += "         AND f.flow_down = fifd.flow_down";
		query += "         AND fifd.company_id = citr.catalog_company_id";
		query += "         AND fifd.catalog_id = citr.catalog_id";
		query += "         AND fifd.fac_part_no = citr.cat_part_no";
		query += "         AND cc.cat_part_flow_down = 'Y'";
		query += "         AND cc.company_id = citr.owner_company_id";
		query += "         AND cc.catalog_company_id = citr.catalog_company_id";
		query += "         AND cc.catalog_id = citr.catalog_id";
		query += "         AND citr.doc_num in (" + docNums + ")";
		return query;
	}

	private String getPoFlowdownSQL(String pos) {
		String query = "SELECT   L.FLOW_DOWN,";
		query += "         F.FLOW_DOWN_DESC,";
		query += "         F.FLOW_DOWN_TYPE,";
		query += "         F.REVISION_DATE,";
		query += "         f.content,";
		query += "         F.CURRENT_VERSION,";
		query += "         L.CATALOG_ID,";
		query += "         L.radian_po,";
		query += "         L.po_line,";
		query += "         L.COMPANY_ID";
		query += "  FROM   po_line_flow_down l, vv_flow_down f";
		query += " WHERE       l.radian_po in (" + pos + ")";
		query += "         AND l.flow_down = f.flow_down";
		query += "         AND l.catalog_id = f.catalog_id";
		query += "         AND l.company_id = f.company_id";
		return query;
	}
}
