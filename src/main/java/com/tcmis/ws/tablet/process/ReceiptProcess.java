package com.tcmis.ws.tablet.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.GHSLabelReqsBean;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class ReceiptProcess extends GenericProcess {

	public ReceiptProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public JSONArray getReceipts(TabletInputBean input, String dateFormat) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new ReceiptBean());
		SearchCriteria criteria = new SearchCriteria();

		if (input.hasPO()) {
			criteria.addCriterion("radianPo", SearchCriterion.EQUALS, input.getPo());
		}
		if (input.hasItemId()) {
			criteria.addCriterion("itemId", SearchCriterion.EQUALS, input.getItemId());
		}
		if (input.hasInboundShipmentId()) {
			String nestedSelectSubstring = "select inbound_shipment_detail_id from inbound_shipment_detail";
			SearchCriteria nestedCriteria = new SearchCriteria("inboundShipmentId", SearchCriterion.EQUALS, input.getInboundShipmentId());
			criteria.addNestedQuery("inboundShipmentDetailId", nestedSelectSubstring, nestedCriteria);
		}
		if (input.hasReceiptId()) {
			criteria.addCriterion("receiptId", SearchCriterion.EQUALS, input.getReceiptId());
		}
		SortCriteria sort = new SortCriteria("itemId");

		Collection<ReceiptBean> beans = factory.select(criteria, sort, "RECEIPT");
		for (ReceiptBean bean : beans) {
			JSONObject jsonBean = BeanHandler.getJsonObject(bean, dateFormat);
			JSONArray components = getReceiptComponents("" + bean.getReceiptId(), dateFormat);
			if (components.length() > 0) {
				jsonBean.put("components", components);
			}
			JSONObject ghsLabelReqs = getGHSLabelReqs("" + bean.getReceiptId());
			jsonBean.put("ghsLabelReqs", ghsLabelReqs);
			results.put(jsonBean);
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getReceiptsById(TabletInputBean input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new ReceiptBean());

		StringBuilder query = new StringBuilder(
				"select RECEIPT_ID, MFG_LOT, DATE_OF_MANUFACTURE, DECODE( CUSTOMER_WARRANTY_DAYS, NULL, EXPIRE_DATE, NULL ) AS EXPIRE_DATE, DATE_OF_SHIPMENT, VENDOR_SHIP_DATE, HAZCOM_LABEL_FLAG from receipt where receipt_id = " + input.getReceiptId());

		Collection<ReceiptBean> beans = factory.selectQuery(query.toString());
		for (ReceiptBean bean : beans) {
			JSONObject jsonBean = BeanHandler.getJsonObject(bean);
			JSONObject ghsLabelReqs = getGHSLabelReqs("" + bean.getReceiptId());
			jsonBean.put("ghsLabelReqs", ghsLabelReqs);
			results.put(jsonBean);
		}

		return results;
	}

	private JSONArray getReceiptComponents(String receiptId, String dateFormat) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new ReceiptComponentBean());
		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("receiptId", SearchCriterion.EQUALS, receiptId);
		SortCriteria sort = new SortCriteria("partId");

		Collection<ReceiptComponentBean> beans = factory.select(criteria, sort, "RECEIPT_COMPONENT");
		for (ReceiptComponentBean bean : beans) {
			JSONObject jsonBean = BeanHandler.getJsonObject(bean, dateFormat);
			results.put(jsonBean);
		}

		return results;
	}

	public JSONArray getReceiptImages(TabletInputBean input) throws BaseException {
		JSONArray results = new JSONArray();
		factory.setBean(new ReceiptDocumentViewBean());

		SearchCriteria criteria = new SearchCriteria("receiptId", SearchCriterion.EQUALS, input.getReceiptId());
		criteria.addCriterion("documentType", SearchCriterion.LIKE, "Picture-");

		SortCriteria sort = new SortCriteria("documentType");

		Collection<ReceiptDocumentViewBean> beans = factory.select(criteria, sort, "RECEIPT_DOCUMENT_VIEW");
		for (ReceiptDocumentViewBean bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}

		return results;
	}

	public String saveReceiptImage(TabletInputBean input, PersonnelBean user) throws BaseException {
		String message = "";
		ResourceLibrary library = new ResourceLibrary("tabletConfig");
		String savePath = library.getString("receiptImageDirectory");
		if (!savePath.endsWith(File.separator)) {
			savePath += File.separator;
		}
		String urlPath = library.getString("receiptImageServerURL");
		if (!urlPath.endsWith(File.separator)) {
			urlPath += File.separator;
		}
		String fileName = getFilename(input, savePath, 1);

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			FileOutputStream fos = new FileOutputStream(savePath + fileName);
			fos.write(decoder.decodeBuffer(input.getImageData()));
			fos.flush();
			fos.close();
		}
		catch (IOException e) {
			message = "Error saving image to disk: " + e;
			log.error(message, e);
		}

		try {
			if (StringHandler.isBlankString(message)) {
				StringBuilder insert = new StringBuilder("Insert into RECEIPT_DOCUMENT (DOCUMENT_ID, RECEIPT_ID, DOCUMENT_NAME, DOCUMENT_TYPE, INSERT_DATE, DOCUMENT_DATE, DOCUMENT_URL, ENTERED_BY, COMPANY_ID) values (");
				insert.append("null,");
				insert.append(input.getReceiptId()).append(",");
				insert.append("'").append(fileName).append("',");
				insert.append("'").append(input.getImageType()).append("',");
				insert.append("sysdate,");
				insert.append("sysdate,");
				insert.append("'").append(urlPath).append(fileName).append("',");
				insert.append(user.getPersonnelId()).append(",");
				insert.append("'").append(user.getCompanyId()).append("'");
				insert.append(")");
				factory.deleteInsertUpdate(insert.toString());
				input.setReturnValue(urlPath + fileName);
			}
		}
		catch (Exception e) {
			message = "Error saving image record: " + e;
			log.error(message, e);
		}

		return message;
	}

	private String getFilename(TabletInputBean input, String savePath, int cntr) {
		String fileName = new StringBuilder(input.getReceiptId()).append("-").append(input.getImageType()).append("_").append(cntr).append(".jpg").toString();
		if (new File(savePath + fileName).exists()) {
			return getFilename(input, savePath, cntr + 1);
		}
		return fileName;
	}

	@SuppressWarnings("unchecked")
	public String upsertReceipts(TabletInputBean input, PersonnelBean user) throws Exception {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", user.getLocale());
		SimpleDateFormat formatter = new SimpleDateFormat(library.getString("java.tabletdateformat"));
		SimpleDateFormat formatterWithTime = new SimpleDateFormat(library.getString("java.tabletdatetimeformat"));

		inArgs = new Vector();
		inArgs.add(input.getInboundShipmentDetailId());
		inArgs.add(input.getReceiptId());
		inArgs.add(input.getReceivingStatus());
		inArgs.add(input.getPoLine());
		inArgs.add(input.getItemId());
		inArgs.add(input.getLot());
		inArgs.add(input.getHubId());
		inArgs.add(input.getInventoryGroup());
		inArgs.add(input.getBin());
		inArgs.add(input.getQuantity());
		inArgs.add(getTimestamp(formatter, input.getDateOfReceipt()));
		inArgs.add(getTimestamp(formatter, input.getDateOfManufacture()));
		inArgs.add(getTimestamp(formatter, input.getDateOfShipment()));
		inArgs.add(getTimestamp(formatter, !StringHandler.isBlankString(input.getExpireDate()) ? input.getExpireDate() : input.getMfgLabelExpireDate()));
		inArgs.add(getTimestamp(formatter, input.getVendorShipDate()));
		inArgs.add(user.getPersonnelId());
		inArgs.add(input.getDocType());
		inArgs.add(input.getNotes());
		inArgs.add(input.getReceivedPurchasingUnits());
		inArgs.add(input.getReceiptGroup());
		inArgs.add(input.getPoNumber());
		inArgs.add(input.getOwnerCompanyId());
		inArgs.add(input.getCatalogCompanyId());
		inArgs.add(input.getCatalogId());
		inArgs.add(input.getCatPartNo());
		inArgs.add(input.getPartGroupNo());
		inArgs.add(input.getOriginalReceiptId());
		inArgs.add(input.getTransferReceiptId());
		inArgs.add(input.getReturnReceiptId());
		inArgs.add(input.getQaStatement());
		inArgs.add(input.getInterCompanyPo());
		inArgs.add(input.getInterCompanyPoLine());
		inArgs.add(input.getCountryOfOrigin());
		inArgs.add(input.getOwnerSegmentId());
		inArgs.add(input.getAccountNumber());
		inArgs.add(input.getAccountNumber2());
		inArgs.add(input.getAccountNumber3());
		inArgs.add(input.getAccountNumber4());
		inArgs.add(input.getCustomerReceiptId());
		inArgs.add(input.getQualityTrackingNumber());
		inArgs.add(input.getHazComLabelFlag());
		inArgs.add(input.getUnitLabelPrinted());
		inArgs.add(input.getDefinedShelfLifeItem());
		inArgs.add(getTimestamp(formatterWithTime, input.getInitialScanDate()));
		inArgs.add(input.getInternalReceiptNotes());
		inArgs.add(getTimestamp(formatter, !StringHandler.isBlankString(input.getMfgLabelExpireDate()) ? input.getMfgLabelExpireDate() : input.getExpireDate()));
		inArgs.add(input.getNewMsdsRevReceivedFlag());

		outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Vector results = (Vector) factory.doProcedure("PKG_HUB_AUTOMATION.P_UPSERT_RECEIPT", inArgs, outArgs);
		String result = (String) results.firstElement();
		if ((StringHandler.isBlankString(result) || "OK".equals(result)) && input.hasComponents() && !input.isManageKitsAsSingleUnit()) {
			for (ReceiptComponentBean bean : input.getReceiptComponents()) {
				upsertReceiptComponent(bean, input.getReceiptId());
			}
		}
		else if (result.trim().endsWith("MISSING OR INVALID INPUT VALUES:")) {
			result += validateInput(input);
		}
		
		upsertGHSLabelReqs(input);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public String markReceiptBinned(TabletInputBean input, PersonnelBean user) throws Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		inArgs = new Vector();
		inArgs.add(null);
		inArgs.add(input.getReceiptId());
		inArgs.add("Binned");
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(input.getBin());
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(user.getPersonnelId());
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);

		outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Vector results = (Vector) factory.doProcedure("PKG_HUB_AUTOMATION.P_UPSERT_RECEIPT", inArgs, outArgs);

		return (String) results.firstElement();

	}

	@SuppressWarnings("unchecked")
	public String binReceipt(TabletInputBean input, PersonnelBean user) throws Exception {
		String result = validateInputForBinning(input);
		if (StringHandler.isBlankString(result)) {
			if (input.isManageKitsAsSingleUnit()) {
				result = markReceiptBinned(input, user);
			}
			else {
				StringBuilder update = new StringBuilder("update RECEIPT_COMPONENT set BIN = '").append(input.getBin()).append("'");
				update.append(" where RECEIPT_ID = '").append(input.getReceiptId()).append("' and PART_ID = '").append(input.getComponentId()).append("'");
				factory.deleteInsertUpdate(update.toString());
				if (allReceiptComponentsBinned(input.getReceiptId())) {
					result = markReceiptBinned(input, user);
				}
			}
		}
		return result;
	}

	public void reImageComplete(TabletInputBean input, PersonnelBean user) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
		String updateDate = DateHandler.formatDate(new Date(), library.getString("java.datetimeformat"));
		StringBuilder update = new StringBuilder("update receipt set INTERNAL_RECEIPT_NOTES = INTERNAL_RECEIPT_NOTES || '  (Images retaken ");
		update.append(updateDate).append(")', RECEIVING_STATUS = 'QC Ready', RECEIVING_STATUS_DATE = sysdate");
		update.append(" where RECEIPT_ID = ").append(input.getReceiptId());
		factory.deleteInsertUpdate(update.toString());
	}

	private boolean allReceiptComponentsBinned(String receiptId) throws BaseException {
		return !"Y".equals(factory.selectSingleValue("Select 'Y' from dual where exists (select r.receipt_id from receipt r, receipt_component rc, vv_hub_bins vhb where r.receipt_id = " + receiptId
				+ " and rc.receipt_id = r.receipt_id and vhb.branch_plant = r.hub  and vhb.bin = rc.bin AND vhb.bin_type = 'Receiving')"));
	}

	private Timestamp getTimestamp(SimpleDateFormat formatter, String date) throws BaseException {
		if (StringHandler.isBlankString(date)) {
			return null;
		}
		try {
			return getTimestamp(formatter.parse(date));
		}
		catch (Exception e) {
			throw new BaseException("Error parsing date with format " + formatter.toPattern() + " : " + e.getMessage(), e);
		}
	}

	private Timestamp getTimestamp(Date date) {
		return date != null ? new Timestamp(date.getTime()) : null;
	}

	// update call p_receipt_component
	@SuppressWarnings("unchecked")
	public void upsertReceiptComponent(ReceiptComponentBean component, String receiptId) throws Exception {
		Collection cin = new Vector();

		cin.add(receiptId);
		cin.add(component.getItemId() != null ? component.getItemId() : "");
		cin.add(component.getPartId() != null ? component.getPartId() : "");
		cin.add(StringHandler.isBlankString(component.getMfgLot()) ? "" : component.getMfgLot());
		cin.add(StringHandler.isBlankString(component.getBin()) ? "" : component.getBin());
		cin.add(getTimestamp(component.getExpireDate()));
		cin.add(getTimestamp(component.getMfgLabelExpireDate()));

		factory.doProcedure("P_RECEIPT_COMPONENT", cin);
	}

	public String validateInput(TabletInputBean input) {
		String message = "";
		if (!input.hasReceiptId()) {
			message += "receiptId is required for insert. ";
		}
		if (!input.hasInboundShipmentDetailId()) {
			message += "inboundShipmentDetailId is required for insert. ";
		}
		if (input.hasPO() && !input.hasPoLine()) {
			message += "poLine is required for insert of PO inbound shipments. ";
		}
		if (!input.hasItemId()) {
			message += "itemId is required for insert. ";
		}
		if (!input.hasInventoryGroup()) {
			message += "inventoryGroup is required for insert. ";
		}
		if (!input.hasHubId()) {
			message += "hubId is required for insert. ";
		}
		if (!input.hasLot()) {
			message += "lot is required for insert. ";
		}
		if (!input.hasBin()) {
			message += "bin is required for insert. ";
		}
		if (!input.hasQuantity()) {
			message += "quantity is required for insert. ";
		}
		if (!input.hasDateOfReceipt()) {
			message += "dateOfReceipt is required for insert. ";
		}
		if (!input.hasExpireDate()) {
			message += "expireDate is required for insert. ";
		}
		if (!input.hasDocType()) {
			message += "docType is required for insert. ";
		}
		return message;
	}

	public String validateInputForBinning(TabletInputBean input) {
		String message = "";
		if (!input.hasReceiptId()) {
			message += "receiptId is required for binning. ";
		}
		if (!input.hasBin()) {
			message += "bin is required for binning. ";
		}
		if (!input.isManageKitsAsSingleUnit()) {
			if (!input.hasComponentId()) {
				message += "componentId is required for binning when manageKitsAsSingleUnit = 'N'. ";
			}
		}
		return message;
	}

	public JSONArray getBinnableReceipts(TabletInputBean input, PersonnelBean user, String dateFormat) throws BaseException {
		return getReceiptsByStatus("QC Complete", input, user, dateFormat);
	}

	public JSONArray getRevertedReceipts(TabletInputBean input, PersonnelBean user, String dateFormat) throws BaseException {
		return getReceiptsByStatus("Re-Image", input, user, dateFormat);
	}

	public JSONArray getReceiptsByStatus(String status, TabletInputBean input, PersonnelBean user, String dateFormat) throws BaseException {
		factory.setBean(new ReceivingStatusViewBean());

		StringBuilder query = new StringBuilder("SELECT BIN.*, RD.DOCUMENT_URL IMAGE_URL, ICC.SALES_VELOCITY FROM TABLE(PKG_HUB_AUTOMATION.FX_GET_RECEIPT_BINNING_DATA(");
		query.append("'").append(status).append("',");
		if (StringHandler.isBlankString(input.getReceiptId())) {
			query.append("'',");
		} else {
			query.append("'").append(input.getReceiptId()).append("',");
		}
		query.append("'',");
		query.append(input.hasHub() ? "'" + input.getHub() + "'," : "'',");
		query.append(input.hasInventoryGroup() ? "'" + input.getInventoryGroup() + "'," : "'',");
		query.append(input.hasOpsEntityId() ? "'" + input.getOpsEntityId() + "'" : "''");
		query.append(")) BIN, RECEIPT_DOCUMENT RD, ITEM_CYCLE_COUNT ICC WHERE BIN.receipt_id = RD.receipt_id(+) AND RD.DOCUMENT_NAME(+) LIKE '%Picture-Item_1%' AND BIN.item_id = icc.item_id (+) ORDER BY CRITICAL, INBOUND_SHIPMENT_ID, BIN.receipt_id");

		@SuppressWarnings("unchecked")
		Collection<ReceivingStatusViewBean> beans = factory.selectQuery(query.toString());

		JSONArray results = new JSONArray();

		for (ReceivingStatusViewBean bean : beans) {
			results.put(BeanHandler.getJsonObject(bean, dateFormat));
		}

		return results;

	}

	public JSONArray getReceiptDataById(TabletInputBean input, PersonnelBean user, String dateFormat) throws BaseException {
		return getReceiptsByStatus("", input, user, dateFormat);
	}

	public Collection<ReceivingStatusViewBean> getReceiptDataByIdStatus(TabletInputBean input, PersonnelBean user, String dateFormat) throws BaseException {
		factory.setBean(new ReceivingStatusViewBean());

		StringBuilder query = new StringBuilder("SELECT BIN.*, RD.DOCUMENT_URL IMAGE_URL, R.QC_DATE FROM TABLE(PKG_HUB_AUTOMATION.FX_GET_RECEIPT_BINNING_DATA(");
		query.append("'',");
		if (StringHandler.isBlankString(input.getReceiptId())) {
			query.append("'',");
		} else {
			query.append("'").append(input.getReceiptId()).append("',");
		}
		query.append("'',");
		query.append(input.hasHub() ? "'" + input.getHub() + "'," : "'',");
		query.append(input.hasInventoryGroup() ? "'" + input.getInventoryGroup() + "'," : "'',");
		query.append(input.hasOpsEntityId() ? "'" + input.getOpsEntityId() + "'" : "''");
		query.append(")) BIN, RECEIPT_DOCUMENT RD, RECEIPT R  WHERE BIN.receipt_id = RD.receipt_id(+) AND RD.DOCUMENT_NAME(+) LIKE '%Picture-Item_1%' ");
		query.append(" AND BIN.RECEIPT_ID = R.RECEIPT_ID  ORDER BY CRITICAL, INBOUND_SHIPMENT_ID, BIN.receipt_id");

		@SuppressWarnings("unchecked")
		Collection<ReceivingStatusViewBean> beans = factory.selectQuery(query.toString());
		
		return beans;
	}
	
	// update call p_receipt_component
	@SuppressWarnings("unchecked")
	public void upsertGHSLabelReqs(TabletInputBean input) throws Exception {
		Collection cin = new Vector();
		
		StringBuilder query = new StringBuilder("insert into RECEIPT_GHS_LABEL_REQS ");
		query.append("(RECEIPT_ID, PRODUCT_NAME, SIGNAL_WORD, PICTOGRAM, HAZARD_STATEMENT, PRECAUTIONARY_STATEMENT, SUPPLIER_INFO) VALUES (");
		query.append(input.getReceiptId()).append(", ");
		query.append("'").append(input.isProductName() ? "Y" : "N").append("', ");
		query.append("'").append(input.isSignalWord() ? "Y" : "N").append("', ");
		query.append("'").append(input.isPictogram() ? "Y" : "N").append("', ");
		query.append("'").append(input.isHazardStatement() ? "Y" : "N").append("', ");
		query.append("'").append(input.isPrecautionaryStatement() ? "Y" : "N").append("', ");
		query.append("'").append(input.isSupplierInfo() ? "Y" : "N").append("'");
		query.append(")");
		factory.deleteInsertUpdate(query.toString());
	}
		
	public JSONObject getGHSLabelReqs(String receiptId) throws BaseException, JSONException {
		factory.setBean(new GHSLabelReqsBean());

		StringBuilder query = new StringBuilder("select  PRODUCT_NAME, PICTOGRAM, SIGNAL_WORD, HAZARD_STATEMENT, PRECAUTIONARY_STATEMENT, SUPPLIER_INFO from receipt_ghs_label_reqs where receipt_id = " + receiptId);

		Collection<GHSLabelReqsBean> beans = factory.selectQuery(query.toString());
		if (beans == null || beans.isEmpty()) {
			return null;
		}
		JSONObject jsonBean = BeanHandler.getJsonObject(beans.iterator().next());

		return jsonBean;
	}
	
	public Collection<ReceivingStatusViewBean> verifyReceiptId(TabletInputBean input) throws BaseException {
		factory.setBean(new ReceivingStatusViewBean());
		StringBuilder query = new StringBuilder("SELECT RECEIVING_STATUS, QC_DATE, RECEIPT_ID, BIN FROM RECEIPT WHERE (RECEIVING_STATUS = 'Binned' OR RECEIVING_STATUS IS NULL) AND BIN IS NOT NULL ");		
		if (!StringHandler.isBlankString(input.getReceiptId())) {			
			query.append(" AND RECEIPT_ID = '").append(input.getReceiptId()).append("'");
		}
		if (!StringHandler.isBlankString(input.getHub())) {
			query.append(" AND HUB = '").append(input.getHub()).append("'");
		}
		query.append(" AND QC_DATE IS NOT NULL AND BIN IS NOT NULL ORDER BY RECEIPT_ID ");
		@SuppressWarnings("unchecked")
		Collection<ReceivingStatusViewBean> beans = factory.selectQuery(query.toString());		
		return beans;
	}
	
	public Collection<ReceivingStatusViewBean> getReceiptDataByIdHub(TabletInputBean input) throws BaseException {
		factory.setBean(new ReceivingStatusViewBean());
		StringBuilder query = new StringBuilder("SELECT  R.RECEIPT_ID, R.DOC_NUM, R.ITEM_ID, R.QC_DATE, FX_ITEM_DESC( R.ITEM_ID ) AS ITEM_DESC, R.QUANTITY_RECEIVED, R.RECEIVING_STATUS, "); 
		query.append(" R.NOTES AS RECEIVING_NOTES, R.RECEIVING_STATUS_DATE, R.INVENTORY_GROUP, IGD.INVENTORY_GROUP_NAME, IGD.OPS_ENTITY_ID, "); 
		query.append(" FX_OPS_ENTITY_NAME( IGD.OPS_ENTITY_ID ) AS OPS_ENTITY_NAME, NVL2( RC.RECEIPT_ID, RC.BIN, R.BIN ) AS BIN, FX_PART_DOT( R.ITEM_ID ) AS DOT, "); 
		query.append(" FX_RECEIPT_STORAGE_TEMP( R.RECEIPT_ID ) AS STORAGE_TEMP, NVL2( RC.RECEIPT_ID, 'N', 'Y') AS MANAGE_KITS_AS_SINGLE_UNIT, RC.PART_ID AS COMPONENT_ID, ");
		query.append(" (SELECT COUNT(1) FROM RECEIPT_COMPONENT WHERE RECEIPT_ID = R.RECEIPT_ID ) AS NUMBER_OF_KITS, RD.DOCUMENT_URL AS IMAGE_URL ");
		query.append(" FROM RECEIPT R, RECEIPT_COMPONENT RC, INVENTORY_GROUP_DEFINITION IGD, RECEIPT_DOCUMENT RD ");    
		query.append(" WHERE RC.RECEIPT_ID(+) = R.RECEIPT_ID AND ");
		query.append(" IGD.INVENTORY_GROUP = R.INVENTORY_GROUP AND ");
		query.append(" R.RECEIPT_ID = RD.RECEIPT_ID(+) AND "); 
		query.append(" RD.DOCUMENT_NAME(+) LIKE '%Picture-Item_1%' ");
		if (!StringHandler.isBlankString(input.getReceiptId())) {			
			query.append(" AND R.RECEIPT_ID = '").append(input.getReceiptId()).append("'");
		} else {
			query.append(" AND R.RECEIPT_ID = ''");
		}
		if (!StringHandler.isBlankString(input.getHub())) {
			query.append(" AND R.HUB = '").append(input.getHub()).append("'");
		} else {
			query.append(" AND R.HUB = ''");
		}
		@SuppressWarnings("unchecked")
		Collection<ReceivingStatusViewBean> beans = factory.selectQuery(query.toString());		
		return beans;
	}
	
	@SuppressWarnings("unchecked")
	public String rebinReceipt(TabletInputBean input, PersonnelBean user) throws Exception {
		String result = validateInputForBinning(input);
		if (StringHandler.isBlankString(result)) {
			updateReceiptForReBinning(input, user);
			if (!input.isManageKitsAsSingleUnit()) {
				StringBuilder update = new StringBuilder("update RECEIPT_COMPONENT set BIN = '").append(input.getBin()).append("'");
				update.append(" where RECEIPT_ID = '").append(input.getReceiptId()).append("' and PART_ID = '").append(input.getComponentId()).append("'");
				factory.deleteInsertUpdate(update.toString());
			}
		}
		return result;
	}
	
	public int updateReceiptForReBinning(TabletInputBean input, PersonnelBean user) throws Exception {
		//UPDATE RECEIPT X SET 
		//RECEIVING_STATUS = V_RECEIVING_STATUS, 
		//LAST_MODIFIED_BY  = A_RECEIVER_ID, 
		//RECEIVING_STATUS_DATE = SYSDATE, 
		//BIN = A_BIN 
		//WHERE RECEIPT_ID = A_RECEIPT_ID
		StringBuilder update = new StringBuilder("UPDATE RECEIPT ");
		update.append(" SET RECEIVING_STATUS = '").append(input.getReceivingStatus()).append("',");
		update.append(" BIN = '").append(input.getBin()).append("',");
		update.append(" LAST_MODIFIED_BY = ").append(user.getPersonnelId()).append(",");
		update.append(" RECEIVING_STATUS_DATE = SYSDATE ");
		update.append(" WHERE RECEIPT_ID = '").append(input.getReceiptId()).append("'");
		return factory.deleteInsertUpdate(update.toString());
	}
	

	
}
