package com.tcmis.internal.hub.process;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Types;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvCountryBean;
import com.tcmis.common.admin.factory.VvReceiptDocumentTypeBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ContainerLabelViewBean;
import com.tcmis.internal.hub.beans.GHSLabelReqsBean;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.ReceivingQcCheckListBean;
import com.tcmis.internal.hub.factory.ReceivingQcCheckListDataMapper;
import com.tcmis.internal.hub.factory.ReceivingQcCheckListDataMapperImpl;

import radian.tcmis.common.util.DateHandler;

/******************************************************************************
 * Process for receiving qc
 * 
 * @version 1.0
 *****************************************************************************/
public class ReceivingQcCheckListProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	private ReceivingQcCheckListDataMapper dataMapper;
	private GenericSqlFactory factory;

	public ReceivingQcCheckListProcess(String client, Locale locale) {
		super(client, locale);
		factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
	}

	public ReceivingQcCheckListProcess(String client) {
		super(client);
		factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
	}

	public Collection buildDocs(ReceiptDescriptionViewBean inputBean) throws BaseException {
		factory.setBean(new ReceiptDocumentInputBean());
		try {
			return factory.selectQuery("select * from table (PKG_HUB_AUTOMATION.FX_GET_INBOUND_SHIPMENT_DOCS(" + inputBean.getInboundShipmentDetailId() + "))");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}
	
	public ReceivingQcCheckListDataMapper getDataMapper() {
		if (dataMapper == null) {
			dataMapper = new ReceivingQcCheckListDataMapperImpl(new DbManager(client, this.getLocale()));
		}
		return dataMapper;
	}

	@SuppressWarnings("unchecked")
	public Collection<ReceiptDocumentViewBean> getAlreadyAssociatedDocs(String documentUrl) {
		factory.setBean(new ReceiptDocumentViewBean());
		try {
			return factory.selectQuery("select receipt_id, document_type, document_url from receipt_document where DOCUMENT_URL = '" + documentUrl + "'");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return Collections.EMPTY_LIST;
	}

	public Collection getCatalogResults(ReceiptDescriptionViewBean inputBean) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory.selectQuery("select distinct c.company_id,c.catalog_id,c.cat_part_no,c.stocked,c.shelf_life_days,c.storage_temp,c.shelf_life_basis,"
					+ "m.reorder_point,m.stocking_level,c.inventory_group,ig.inventory_group_name,fx_spec_list(c.company_id,c.catalog_id,c.cat_part_no) spec_list,"
					+ " decode(p.catalog_item_id,null,'N','Y') pwa300,tcm_ops.fx_get_min_rt_out_time(c.item_id,c.cat_part_no,c.catalog_id,"
					+ "c.company_id,c.company_id) min_rt_out_time,m.reorder_quantity,cy.company_short_name, c.catalog_company_id, c.part_group_no,"
					+ "c.incoming_testing,c.recert_instructions,c.total_recerts_allowed,c.max_recert_number,c.customer_part_revision "
					+ ",nvl(fx_catalog_desc(c.company_id,c.catalog_id),c.catalog_id) catalog_desc " + "from cat_part_all_app_view c,minmax_item_part_view m,"
					+ "inventory_group_definition ig,company cy, utc.pwa_300 p where c.item_id = " + inputBean.getItemId() + " and c.company_id = m.company_id (+)"
					+ " and c.catalog_id = m.catalog_id (+) and c.cat_part_no = m.cat_part_no (+) and c.item_id = m.item_id (+) and c.inventory_group = ig.inventory_group"
					+ " and ig.hub = " + inputBean.getHub() + " and m.item_id (+) = " + inputBean.getItemId() + " and c.inventory_group = m.inventory_group (+)"
					+ " and c.catalog_item_id = p.catalog_item_id (+) and nvl(ig.distributor_ops,'N') = 'N' and c.catalog_id != 'Demo' and c.company_id = cy.company_id (+) AND ig.inventory_group = '"
					+ inputBean.getInventoryGroup() + "'");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public Collection getPoNotes(ReceiptDescriptionViewBean inputBean) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory
					.selectQuery("select distinct to_char(note_date,'mm/dd/yyyy') po_note_date,p.note po_note,to_char(note_date,'yyyymmdd') from po_notes p where radian_po = "
							+ inputBean.getRadianPo() + " and note is not null order by to_char(note_date,'yyyymmdd')");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public Collection getCustomerOrderInternalOrderNotes(ReceiptDescriptionViewBean inputBean) throws BaseException {
		try {
			factory.setBean(new ReceivingQcCheckListBean());
			return factory.selectQuery("select rli.notes customer_order_note, rli.internal_note from mr_allocation mra, request_line_item rli, purchase_request pr"
					+ " where mra.company_id = pr.company_Id and mra.pr_number = pr.pr_number and mra.company_id = rli.company_Id and mra.pr_number = rli.pr_number and mra.line_item = rli.line_item and mra.doc_type = 'PO'"
					+ " and mra.doc_num = " + inputBean.getRadianPo() + " and mra.doc_line = " + inputBean.getPoLine() + " and mra.item_id = " + inputBean.getItemId());
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public Collection getHubSpecificNotes(BigDecimal itemId, String branchPlant) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory.selectQuery(
					"select SHELF_LIFE, REQ_TESTING, STORAGE_REQS, NOTES from RECEIVE_INFO where item_id = " + itemId + " and warehouse = fx_hub_name(" + branchPlant + ")");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public Collection getHeaderInfo(String receiptId) throws BaseException {
		factory.setBean(new ReceiptDescriptionViewBean());
		try {
			return factory.selectQuery(" SELECT * FROM TABLE (PKG_HUB_AUTOMATION.FX_GET_RECEIPT_CHECKLIST_DATA(" + receiptId + "))");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public Collection getMsdsInfo(BigDecimal itemId) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			String query = "select * from (select icv.DESCRIPTION, icv.MANUFACTURER, icv.CONTENT, TO_DATE( REVISION_DATE, 'DD/MON/YYYY HH:MI:SS' ) AS REVISION_DATE, msds_locale  from item_component_view icv where item_id = ";
			query += itemId;
			query += " union select icmlv.DESCRIPTION, icmlv.MANUFACTURER, icmlv.CONTENT, TO_DATE(icmlv.REVISION_DATE, 'DD/MON/YYYY' ) AS REVISION_DATE, locale_code msds_locale from item_component_msds_loc_view icmlv where item_id = ";
			query += itemId;
			query += ") where msds_locale = '" + getLocale() + "'";
			return factory.selectQuery(query);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public Collection getVvReceiptDocumentType() throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		VvReceiptDocumentTypeBeanFactory vvReceiptDocumentTypeBeanFactory = new VvReceiptDocumentTypeBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("documentType", SearchCriterion.NOT_LIKE, "Picture%");
		try {
			return vvReceiptDocumentTypeBeanFactory.select(criteria);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public void printChecklist(ReceivingInputBean inputBean, ReceiptDescriptionViewBean viewBean, PersonnelBean personnelBean) throws BaseException {
		try {
			StringBuilder printUrl = new StringBuilder("http://localhost/HaasReports/report/printReceivingQcChecklistReport.do?searchType=is&searchWhat=Receipt%20Id&search="
					+ viewBean.getReceiptId() + "&sourceHub=" + inputBean.getSourceHub() + "&sort=" + inputBean.getSort() + "&receiptId=" + viewBean.getReceiptId()
					+ "&opsEntityId=" + inputBean.getOpsEntityId() + "&personnelId=" + personnelBean.getPersonnelId());

			printUrl.append("&companyId=" + (StringHandler.isBlankString(viewBean.getCompanyId()) == false ? viewBean.getCompanyId() : "Radian"));
			printUrl.append(viewBean.getShipToCompanyId() != null && viewBean.getShipToCompanyId().length() > 0 ? "&shipToCompanyId=" + viewBean.getShipToCompanyId() : "");
			printUrl.append(viewBean.getItemId() != null && viewBean.getItemId().toString().length() > 0 ? "&itemId=" + viewBean.getItemId() : "");
			printUrl.append(viewBean.getRadianPo() != null && viewBean.getRadianPo().toString().length() > 0 ? "&radianPo=" + viewBean.getRadianPo() : "");
			printUrl.append(viewBean.getPoLine() != null && viewBean.getPoLine().toString().length() > 0 ? "&poLine=" + viewBean.getPoLine() : "");
			printUrl.append(viewBean.getAmendment() != null && viewBean.getAmendment().toString().length() > 0 ? "&amendment=" + viewBean.getAmendment() : "");
			printUrl.append(viewBean.getInventoryGroup() != null && viewBean.getInventoryGroup().length() > 0
					? "&inventoryGroup=" + URLEncoder.encode(viewBean.getInventoryGroup(), "UTF-8") : "");
			printUrl.append(inputBean.getSourceHubName() != null && inputBean.getSourceHubName().length() > 0 ? "&hub=" + URLEncoder.encode(inputBean.getSourceHubName()) : "");

			String reportUrl = "";
			String actualUrl = printUrl.toString();
			if (log.isDebugEnabled()) {
				log.debug("Retrieving: " + actualUrl);
			}
			reportUrl = NetHandler.getHttpPostResponse(actualUrl, null, null, "");
			if (log.isDebugEnabled()) {
				log.debug("Received: " + reportUrl);
			}

			// insert pdf into Receipt Document
			File f = new File(reportUrl);
			ReceiptDocumentInputBean bean = new ReceiptDocumentInputBean();
			bean.setDocumentId(new BigDecimal(1));
			bean.setReceiptId(viewBean.getReceiptId());
			bean.setDocumentName("QVR for " + viewBean.getReceiptId());
			bean.setDocumentType("QVR");
			bean.setDocumentDate(new Date());
			bean.setEnteredBy(personnelBean.getPersonnelIdBigDecimal());
			bean.setCompanyId(StringHandler.isBlankString(viewBean.getCompanyId()) == false ? viewBean.getCompanyId() : "Radian");
			bean.setTheFile(f);

			ReceiptDocumentProcess process = new ReceiptDocumentProcess(getClient(), getLocale());
			process.addNewDocument(bean);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public Integer revertStatus(ReceiptDescriptionViewBean viewBean) {
		factory.setBean(new ReceiptDocumentInputBean());
		try {
			return factory.deleteInsertUpdate("update receipt set RECEIVING_STATUS = 'Re-Image', RECEIVING_STATUS_DATE = sysdate, INTERNAL_RECEIPT_NOTES = "
					+ SqlHandler.delimitString(viewBean.getInternalReceiptNotes()) + " where receipt_id = " + viewBean.getReceiptId());
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public Collection getEngineeringEval(ReceiptDescriptionViewBean inputBean) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory.selectQuery(
					"select pr.pr_number, rli.line_item,pr.company_id,pr.facility_id,rli.fac_part_no,fx_personnel_Id_to_name(pr.requestor) requestor from purchase_request pr, request_line_item rli, buy_order bo where"
							+ " bo.mr_number = pr.pr_number and" + " bo.mr_number = rli.pr_number and" + " bo.mr_line_item = rli.line_item and " + " bo.radian_po = "
							+ inputBean.getRadianPo() + " and" + " bo.item_id = " + inputBean.getItemId() + " and" + " ENGINEERING_EVALUATION = 'Y'");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Collection getAssociatePrs(ReceiptDescriptionViewBean inputBean) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory.selectQuery("select pr_number,mr_number,mr_line_item,part_id,spec_list,item_type,lpp,notes,purchasing_note,"
					+ "comments,requestor_last_name, requestor_first_name,phone,email,csr_name, mr_quantity,order_quantity,size_unit,need_date,facility_id,delivery_point_desc ship_to_delivery_point"
					+ " from associate_pr_view a where  (a.status not in ('Closed','Cancel')) " + " and a.SUPPLY_PATH  in ('Purchaser','Dbuy','Wbuy','Ibuy') " + " and a.ITEM_ID ="
					+ inputBean.getItemId() + " and a.INVENTORY_GROUP = '" + inputBean.getInventoryGroup() + "'" + " and a.RADIAN_PO = " + inputBean.getRadianPo()
					+ "  order by a.ITEM_ID");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public String associateShipmentDocument(ReceiptDocumentInputBean bean, BigDecimal user) throws BaseException {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		try {
			inArgs = new Vector(4);
			inArgs.add(bean.getInboundShipmentDocumentId());
			inArgs.add(bean.getInboundShipmentId());
			inArgs.add(user);
			inArgs.add(null);
			outArgs = new Vector(1);
			outArgs.add(new Integer(Types.VARCHAR));
			log.debug("Update arguments: " + inArgs);

			Vector error = (Vector) factory.doProcedure("PKG_HUB_AUTOMATION.P_UPSERT_INBND_SHPMNT_DOCUMENT", inArgs, outArgs);

			if (error.size() > 0 && error.get(0) != null && !((String) error.get(0)).equalsIgnoreCase("ok")) {
				errorMsg = (String) error.get(0);
				log.info("Error adding doc " + bean.getInboundShipmentDocumentId() + " to : " + bean.getInboundShipmentId());

			}
		}
		catch (Exception e) {
			errorMsg = "Error adding doc " + bean.getInboundShipmentDocumentId() + " to : " + bean.getInboundShipmentId();
		}

		return errorMsg;
	}

	public String getSaicPOClauses(ReceiptDescriptionViewBean bean) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory.selectSingleValue(
					"select CLAUSES_SET_1||CLAUSES_SET_2||CLAUSES_SET_3||CLAUSES_SET_4||CLAUSES_SET_5 PO_CLAUSES from  customer_inventory_to_receive where OWNER_COMPANY_ID ='"
							+ bean.getOwnerCompanyId() + "' and PO_NUMBER ='" + bean.getPoNumber() + "'");

		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Collection getSaicPONotes(String saicPOClauses) throws BaseException {
		if (saicPOClauses == null)
			saicPOClauses = "";
		String[] poClausesArray = saicPOClauses.split("(?<=\\G....)"); /// add a
																		/// not
																		/// null
																		/// clause
																		/// before
																		/// this
																		/// like
																		/// ->
																		/// if
																		/// (saicPOClauses
																		/// ==
																		/// null
																		/// )
																		/// saicPOClauses
																		/// =
																		/// "";
		String poClauseWhereClause = "";
		for (int i = 0; i < poClausesArray.length; i++) {
			if (i == poClausesArray.length - 1)
				poClauseWhereClause += "'" + poClausesArray[i] + "'";
			else
				poClauseWhereClause += "'" + poClausesArray[i] + "'" + ",";

		}

		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory.selectQuery("select * from customer.saic_polchem_po_clauses where clause_number in (" + poClauseWhereClause + ") and print_on_jacket = 'Y'");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Collection getSaicPONotesVerified(String catPartNo) throws BaseException {
		factory.setBean(new ReceivingQcCheckListBean());
		try {
			return factory.selectQuery("Select VERIFIED_BY, VERIFIED_DATE from polchem_nsn_verification where nsn = '" + catPartNo + "'");

		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public Collection getContainerLabelResult(String receiptId) throws BaseException {
		factory.setBean(new ContainerLabelViewBean());
		try {
			return factory.selectQuery("select * from container_label_view  where receipt_id = " + receiptId + " order by container_label_id");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public void updateExpireDate(ReceiptDescriptionViewBean input) throws BaseException {
		if (input.hasComponentId()) {
			StringBuilder sql = new StringBuilder("update receipt_component set EXPIRE_DATE = ").append(DateHandler.getOracleToDateFunction(input.getExpireDate())).append(" where receipt_id = ").append(input.getReceiptId());
			sql.append(" and part_id = ").append(input.getComponentId());
			factory.deleteInsertUpdate(sql.toString());
		}
		else {
			StringBuilder sql = new StringBuilder("update receipt set EXPIRE_DATE = ").append(DateHandler.getOracleToDateFunction(input.getExpireDate())).append(" where receipt_id = ").append(input.getReceiptId());
			factory.deleteInsertUpdate(sql.toString());			
		}
	}	

	public void updateAssignedIfNull(PersonnelBean user, BigDecimal receiptId) throws BaseException {
		StringBuilder sql = new StringBuilder("update receipt set ASSIGNED_TO = ").append(user.getPersonnelId()).append(" where receipt_id = ").append(receiptId)
				.append(" and ASSIGNED_TO is null");
		factory.deleteInsertUpdate(sql.toString());
	}

	public String getDefinedShelfLifeItem(BigDecimal receiptId) throws BaseException {
		String definedShelfLifeItem = (String) getDataMapper().definedShelfLifeItem(receiptId);
		return (StringHandler.isBlankString(definedShelfLifeItem) || "0".equals(definedShelfLifeItem))?"N":"Y";
	}

	public Collection getGHSLabelReqs(String receiptId) throws BaseException {
		factory.setBean(new GHSLabelReqsBean());
		try {
			StringBuilder query = new StringBuilder(
					"select  PRODUCT_NAME, PICTOGRAM, SIGNAL_WORD, HAZARD_STATEMENT, PRECAUTIONARY_STATEMENT, SUPPLIER_INFO from receipt_ghs_label_reqs where receipt_id = "
							+ receiptId);

			return factory.selectQuery(query.toString());
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public String getLabTestComplete(BigDecimal receiptId) throws BaseException {
		StringBuilder sql = new StringBuilder("select pkg_lab_testing.fx_receipt_lab_test_complete(").append(receiptId).append(") from dual ");
		return factory.selectSingleValue(sql.toString());
	}

	public Map<String, String> getInventoryGroupDetails(String inventoryGroup) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		Connection conn = dbManager.getConnection();

		Map<String, String> igDetails = new HashMap<String, String>();
		try {
			String toCalculate = "N";

			StringBuilder query = new StringBuilder();
			query.append("select calculate_expire_date from inventory_group_definition where inventory_group = ");
			query.append(SqlHandler.delimitString(inventoryGroup));
			try {
				toCalculate = factory.selectSingleValue(query.toString(), conn);
			}
			catch (Exception e) {
				// Temporary ignore
			}

			String toEdit = "N";
			query = new StringBuilder();
			query.append("select edit_shelf_life from inventory_group_definition where inventory_group = ");
			query.append(SqlHandler.delimitString(inventoryGroup));
			try {
				toEdit = factory.selectSingleValue(query.toString(), conn);
			}
			catch (Exception e) {
				// Temporary ignore
			}

			igDetails.put("calculateQvrExpDate", toCalculate);
			igDetails.put("editShelfLife", toEdit);
		}
		catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return igDetails;
	}

	public String recalculateMinExpDate(ReceiptDocumentInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select to_char(minimum_expire_date, 'DD-Mon-YYYY') FROM TABLE ");
		query.append("(PKG_HUB_AUTOMATION.FX_GET_RECEIPT_CHECKLIST_DATA(");
		query.append(input.getReceiptId()).append("))");

		return factory.selectSingleValue(query.toString());
	}
	
	// Get the first 5 countries whose abbrev starts with the input text
	@SuppressWarnings("unchecked")
	public Collection<VvCountryBean> getSimilarCountries(AutocompleteInputBean bean) throws BaseException {
		StringBuilder query = new StringBuilder("select country, country_abbrev from vv_country");
		query.append(" where lower(country_abbrev) like").append(" lower('").append(StringHandler.getTrimmed(bean.getSearchText())).append("%')");
		query.append(" and rownum < 5");
		query.append(" order by country, country_abbrev");
		
		return factory.setBean(new VvCountryBean()).selectQuery(query.toString());
	}
	
	public boolean isWmsInventoryGroup(String inventoryGroup) throws BaseException {
		StringBuilder query = new StringBuilder("select count(*) from inventory_group_definition igd, hub h");
		query.append(" where igd.inventory_group = ").append(SqlHandler.delimitString(inventoryGroup));
		query.append(" and igd.inventory_group_type = 'WAREHOUSE'");
		query.append(" and igd.hub = h.branch_plant");
		query.append(" and h.automated_putaway = 'Y'");
		
		return !factory.selectSingleValue(query.toString()).equals("0");
	}
	
	public void setWmsSynchronized(boolean synched, ReceivingInputBean input) throws BaseException {
		String query = "update receipt set wms_synchronized = "
				+ (synched?"'Y'":"'N'")
				+ " where receipt_id = " + input.getReceiptId();
		
		factory.deleteInsertUpdate(query);
	}
}