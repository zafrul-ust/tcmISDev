package com.tcmis.supplier.shipping.process;

import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelFormatFieldDefinitionBean;
import com.tcmis.internal.hub.beans.LabelFormatTemplateFileBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.supplier.shipping.beans.CatPartSearchViewBean;
import com.tcmis.supplier.shipping.beans.InventoryLevelLogBean;
import com.tcmis.supplier.shipping.beans.InventoryManagementInputBean;
import com.tcmis.supplier.shipping.beans.SupplyAndDemandViewBean;
import com.tcmis.supplier.shipping.beans.TransTypeStatusViewBean;
import com.tcmis.supplier.shipping.beans.TransactionMgmtViewBean;
import com.tcmis.supplier.shipping.beans.VvInventoryAdjCodeBean;

import radian.tcmis.common.util.SqlHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Types;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.BaseProcess;

public class InventoryManagementProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	protected DbManager dbManager = null;
	protected GenericSqlFactory factory = null;
	protected ResourceLibrary commonResourcesLibrary = null;
	
	public InventoryManagementProcess(String client, String locale) {
		super(client, locale);
		init();
	}
	
	private void init() {
		try {
			dbManager = new DbManager(client, this.getLocale());
			factory = new GenericSqlFactory(dbManager);
			commonResourcesLibrary = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		} catch (Exception ex) {
			log.error("Error initializing InventoryManagement", ex);
		}
	}
	
	public Collection<InventoryManagementInputBean> getLocationDropdownDataWithEditMinMaxPermission(PersonnelBean personnelBean) throws Exception {
		Vector<InventoryManagementInputBean> results = (Vector<InventoryManagementInputBean>) getInventoryManagementLocationDropdownData(personnelBean.getPersonnelIdBigDecimal());
		
		for (InventoryManagementInputBean bean : results)
			bean.setEditMinMaxPermission(personnelBean.getPermissionBean().hasSupplierLocationPermission("SetMMLevels", bean.getShipFromLocationId(), personnelBean.getCompanyId(), bean.getSupplier()));
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<InventoryManagementInputBean> getInventoryManagementLocationDropdownData(BigDecimal personnelId) throws Exception {
		StringBuilder query = new StringBuilder("SELECT s.supplier, s.supplier_name, l.location_id ship_from_location_id, l.location_desc");
		query.append(" FROM supplier s, user_supplier us, user_supplier_location usl, supplier_ship_from_location ctl, location l");
		query.append(" WHERE s.supplier = us.supplier AND us.personnel_id = ").append(personnelId);
		query.append(" AND s.supplier = ctl.supplier AND us.company_id = usl.company_id");
		query.append(" AND us.supplier = usl.supplier AND us.personnel_id = usl.personnel_id");
		query.append(" AND usl.company_id = ctl.company_id AND usl.location_id = ctl.ship_from_location_id");
		query.append(" AND l.company_id = ctl.company_id AND l.location_id = ctl.ship_from_location_id AND ctl.manage_inventory = 'Y'");
		query.append(" order by s.supplier_name,l.location_desc");
		return factory.setBean(new InventoryManagementInputBean()).selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TransTypeStatusViewBean> getTransactionTypeStatusData() throws Exception {
		StringBuilder query = new StringBuilder("SELECT transaction_type, status from trans_type_status_view order by transaction_type, status");
		return factory.setBean(new TransTypeStatusViewBean()).selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<TransactionMgmtViewBean> getSearchResult(InventoryManagementInputBean inputBean) throws Exception {
		StringBuilder query = new StringBuilder("select * from transaction_mgmt_view where");
		query.append(" supplier = ").append(SqlHandler.delimitString(inputBean.getSupplier()));
		if (!StringHandler.isBlankString(inputBean.getShipFromLocationId()))
			query.append(" and ship_from_location_id = ").append(SqlHandler.delimitString(inputBean.getShipFromLocationId()));
		if (inputBean.getTransactionFromDate() != null)
			query.append(" and transaction_date >= ").append(DateHandler.getOracleToDateFunction(inputBean.getTransactionFromDate()));
		//Date is defaulted to 00:00:00 time if not set, so to include everything within the given date, get everything with date less than the next date
		if (inputBean.getTransactionToDate() != null)
			query.append(" and transaction_date < ").append(DateHandler.getOracleToDateFunction(DateUtils.addDays(inputBean.getTransactionToDate(), 1)));;
		if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
			query.append(" and ").append(inputBean.getSearchField());
			switch (inputBean.getSearchMode()) {
				case "is":
					query.append(" = ").append(SqlHandler.delimitString(inputBean.getSearchArgument()));
					break;
				case "contains":
					query.append(" like ").append(SqlHandler.delimitString("%" + inputBean.getSearchArgument() + "%"));
					break;
				case "startsWith":
					query.append(" like ").append(SqlHandler.delimitString(inputBean.getSearchArgument() + "%"));
					break;
				case "endsWith":
					query.append(" like ").append(SqlHandler.delimitString("%" + inputBean.getSearchArgument()));
					break;
			}
		}
		if (!"Y".equalsIgnoreCase(inputBean.getIncludeHistory()))
			query.append(" and history = 'N'");
		
		if ("Y".equalsIgnoreCase(inputBean.getGfpPartsOnly()))
			query.append(" and gfp = 'Y'");
		
		if (!StringHandler.isBlankString(inputBean.getTransactionType()))
			query.append(" and transaction_type = ").append(SqlHandler.delimitString(inputBean.getTransactionType()));
		
		if (!StringHandler.isBlankString(inputBean.getStatus()))
			query.append(" and status = ").append(SqlHandler.delimitString(inputBean.getStatus()));
		
		query.append(" order by supplier, ship_from_location_id, inventory_id, sort_order, transaction_date");
		
		return factory.setBean(new TransactionMgmtViewBean()).selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<SupplyAndDemandViewBean> getSummarySearchResult(InventoryManagementInputBean inputBean) throws BaseException {
		StringBuilder query = new StringBuilder("select * from supply_and_demand_view");
		query.append(" where supplier = ").append(SqlHandler.delimitString(inputBean.getSupplier()));
		if ("Y".equalsIgnoreCase(inputBean.getGfpPartsOnly()))
			query.append(" and gfp = 'Y'");
		query.append(" order by location_desc, cat_part_no");
		
		return factory.setBean(new SupplyAndDemandViewBean()).selectQuery(query.toString());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addAdjustment(TransactionMgmtViewBean resultBean, BigDecimal personnelId) {
		Collection inArgs = new Vector();
		inArgs.add(resultBean.getInventoryId());
		inArgs.add(resultBean.getQuantity());
		inArgs.add(resultBean.getStatus());
		inArgs.add(personnelId);
		inArgs.add(resultBean.getNotes());
		Collection outArgs = new Vector();
		outArgs.add(new Integer(Types.VARCHAR));

		return callProcedure(inArgs, outArgs, "pkg_transaction_mgmt.p_create_adjustment");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addPartUpdateMinMax(TransactionMgmtViewBean resultBean, BigDecimal personnelId) {
		Collection inArgs = new Vector();
		inArgs.add(resultBean.getInventoryLevelId());
		inArgs.add(resultBean.getSupplier());
		inArgs.add(resultBean.getShipFromLocationId());
		inArgs.add(resultBean.getCatPartNo());
		inArgs.add(resultBean.getReorderPoint());
		inArgs.add(resultBean.getStockingLevel());
		inArgs.add(personnelId);
		Collection outArgs = new Vector();
		outArgs.add(new Integer(Types.VARCHAR));

		return callProcedure(inArgs, outArgs, "pkg_transaction_mgmt.p_upsert_inventory_level");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String updateInsertInventory(TransactionMgmtViewBean resultBean, BigDecimal personnelId) {
		Collection inArgs = new Vector();
		inArgs.add(resultBean.getInventoryId());
		inArgs.add(resultBean.getSupplier());
		inArgs.add(resultBean.getShipFromLocationId());
		inArgs.add(resultBean.getCatPartNo());
		inArgs.add(resultBean.getSupplierPoNumber().trim());
		inArgs.add(resultBean.getMfgLot());
		inArgs.add(resultBean.getDateOfManufacture());
		inArgs.add(resultBean.getExpireDate());
		inArgs.add(resultBean.getQuantity());
		inArgs.add(resultBean.getBolTrackingNum());
		inArgs.add(resultBean.getBin());
		inArgs.add(personnelId);
		inArgs.add("Y".equalsIgnoreCase(resultBean.getVmi()) ? "Y" : "N");
		inArgs.add(resultBean.getNotes());
		Collection outArgs = new Vector();
		outArgs.add(new Integer(Types.VARCHAR));

		return callProcedure(inArgs, outArgs, "pkg_transaction_mgmt.p_upsert_inventory");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String updateInsertPo(TransactionMgmtViewBean resultBean, BigDecimal personnelId) {
		Collection inArgs = new Vector();
		inArgs.add(resultBean.getTransactionId());
		inArgs.add(resultBean.getSupplier());
		inArgs.add(resultBean.getShipFromLocationId());
		inArgs.add(resultBean.getCatPartNo());
		inArgs.add(resultBean.getSupplierPoNumber().trim());
		inArgs.add("Y".equalsIgnoreCase(resultBean.getBlanketPo()) ? "Y" : "N");
		inArgs.add(resultBean.getQuantity());
		inArgs.add(personnelId);
		inArgs.add(resultBean.getNotes());
		Collection outArgs = new Vector();
		outArgs.add(new Integer(Types.VARCHAR));

		return callProcedure(inArgs, outArgs, "pkg_transaction_mgmt.p_upsert_po");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String updateInsertBin(TransactionMgmtViewBean resultBean) {
		Collection inArgs = new Vector();
		inArgs.add(resultBean.getSupplier());
		inArgs.add(resultBean.getShipFromLocationId());
		inArgs.add(resultBean.getBin().trim());
		inArgs.add(resultBean.getActive());
		Collection outArgs = new Vector();
		outArgs.add(new Integer(Types.VARCHAR));

		return callProcedure(inArgs, outArgs, "pkg_transaction_mgmt.p_upsert_bin");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String convertTransaction(TransactionMgmtViewBean resultBean, BigDecimal personnelId) {
		Collection inArgs = new Vector();
		inArgs.add(resultBean.getTransactionType());
		inArgs.add(resultBean.getTransactionId());
		inArgs.add(resultBean.getCatPartNo());
		inArgs.add(resultBean.getQuantity());
		inArgs.add(resultBean.getNotes());
		inArgs.add(personnelId);
		Collection outArgs = new Vector();
		outArgs.add(new Integer(Types.VARCHAR));

		return callProcedure(inArgs, outArgs, "pkg_transaction_mgmt.p_convert_part");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String callProcedure(Collection inArgs, Collection outArgs, String procedureName) {
		String msg;
		try {
			log.info(procedureName + " inArgs " + inArgs);
			Vector<String> result = (Vector) factory.doProcedure(procedureName, inArgs, outArgs);
			if (!result.isEmpty() && result.get(0) != null) {
				log.error(result.get(0));
				msg = result.get(0);
			} else
				msg = "OK";
		} catch (Exception e) {
			msg = commonResourcesLibrary.getString("generic.error");
		}

		return msg;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> getPart(AutocompleteInputBean autocompleteBean, String isInventoryLevelIdRequired) throws BaseException {
		StringBuilder query = new StringBuilder("select * from cat_part_search_view");
		query.append(" where supplier = ").append(SqlHandler.delimitString(autocompleteBean.getSupplier()));
		query.append(" and ship_from_location_id = ").append(SqlHandler.delimitString(autocompleteBean.getLocationId()));
		query.append(" and cat_part_no like ").append(SqlHandler.delimitString("%" + autocompleteBean.getSearchText() + "%"));
		if ("true".equalsIgnoreCase(isInventoryLevelIdRequired))
			query.append(" and inventory_level_id is not null");
		query.append(" and rownum <= 5 order by cat_part_no");
		
		Vector<CatPartSearchViewBean> resultColl = (Vector<CatPartSearchViewBean>) factory.setBean(new CatPartSearchViewBean()).selectQuery(query.toString());
		Vector<String> returnedColl = new Vector<String>(resultColl.size());
		String fieldSeparator = autocompleteBean.getFieldSeparator();
		for (CatPartSearchViewBean bean : resultColl)
			returnedColl.add(bean.getCatPartNo() + fieldSeparator + NumberHandler.emptyIfNull(bean.getInventoryLevelId()) + fieldSeparator + NumberHandler.emptyIfNull(bean.getReorderPoint()) + fieldSeparator + NumberHandler.emptyIfNull(bean.getStockingLevel()) + fieldSeparator + bean.getDomRequired() + fieldSeparator + StringHandler.emptyIfNull(bean.getGfp()));
		
		return returnedColl;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> getBin(AutocompleteInputBean autocompleteBean) throws BaseException {
		StringBuilder query = new StringBuilder("select * from supplier_loc_bin");
		query.append(" where supplier = ").append(SqlHandler.delimitString(autocompleteBean.getSupplier()));
		query.append(" and ship_from_location_id = ").append(SqlHandler.delimitString(autocompleteBean.getLocationId()));
		query.append(" and lower(bin) like lower(").append(SqlHandler.delimitString("%" + autocompleteBean.getSearchText() + "%")).append(")");
		if ("Y".equalsIgnoreCase(autocompleteBean.getActiveOnly()))
			query.append(" and active = 'Y'");
		query.append(" and rownum <= 5 order by bin");
		
		Vector<TransactionMgmtViewBean> resultColl = (Vector<TransactionMgmtViewBean>) factory.setBean(new TransactionMgmtViewBean()).selectQuery(query.toString());
		Vector<String> returnedColl = new Vector<String>(resultColl.size());
		String fieldSeparator = autocompleteBean.getFieldSeparator();
		for (TransactionMgmtViewBean bean : resultColl)
			returnedColl.add(bean.getBin() + fieldSeparator + StringHandler.emptyIfNull(bean.getActive()));
		
		return returnedColl;
	}

	@SuppressWarnings("unchecked")
	public Collection<TransactionMgmtViewBean> getConvertibleParts(TransactionMgmtViewBean resultBean) throws BaseException {
		StringBuilder query = new StringBuilder("select cat_part_no from cat_part_search_view");
		query.append(" where supplier = ").append(SqlHandler.delimitString(resultBean.getSupplier()));
		query.append(" and ship_from_location_id = ").append(SqlHandler.delimitString(resultBean.getShipFromLocationId()));
		query.append(" and conversion_group = ").append(SqlHandler.delimitString(resultBean.getConversionGroup()));
		query.append(" and cat_part_no <> ").append(SqlHandler.delimitString(resultBean.getCatPartNo()));
		query.append(" order by cat_part_no");
		
		return factory.setBean(new TransactionMgmtViewBean()).selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<InventoryLevelLogBean> getInventoryLevelMinMaxHistory(BigDecimal inventoryLevelId) throws BaseException {
		StringBuilder query = new StringBuilder("select old_reorder_point, new_reorder_point, old_stocking_level, new_stocking_level, updated_date, updated_by, fx_personnel_id_to_name(updated_by) AS updated_by_name from inventory_level_log");
		query.append(" where inventory_level_id = ").append(inventoryLevelId);
		query.append(" order by updated_date desc");
		
		return factory.setBean(new InventoryLevelLogBean()).selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<VvInventoryAdjCodeBean> getInventoryAdjustmentCodes(String supplier) throws BaseException {
		StringBuilder query = new StringBuilder("select * from vv_inventory_adj_code");
		query.append(" where supplier = ").append(SqlHandler.delimitString(supplier));
		query.append(" and active = 'Y' order by sort_order");
		
		return factory.setBean(new VvInventoryAdjCodeBean()).selectQuery(query.toString());
	}
	
	public String printInventoryLabel(TransactionMgmtViewBean resultBean, LabelInputBean labelInput, Vector<String> inventoryGroups) throws Exception {
		Connection conn = dbManager.getConnection();
		String filePath = null;
		try {
			ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());
			Collection<LabelFormatTemplateFileBean> templates = zplDataProcess.getLabelTemplate(inventoryGroups, labelInput.getLabelType(), labelInput.getPrinterPath(), conn);
			if (templates != null && !templates.isEmpty()) {
				LabelFormatTemplateFileBean template = templates.iterator().next();
				Collection<LabelFormatFieldDefinitionBean> fieldDefinitions = zplDataProcess.getAllLabelFormatFieldDefinition(template, template.getPrinterResolutionDpi().toPlainString(), conn);
				Vector<BaseDataBean> data = new Vector<BaseDataBean>();
				resultBean.setBarcode(resultBean.getInventoryId().toPlainString());
				data.add(resultBean);
				filePath = new ZplProcess(getClient()).printGenericLabel(new BigDecimal(labelInput.getLabelQuantity()), labelInput.getPrinterPath(), fieldDefinitions, template, data, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
		
		return filePath;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ExcelHandler createExcelReport(InventoryManagementInputBean inputBean) throws NoDataException, BaseException, Exception {
		ExcelHandler pw = new ExcelHandler(commonResourcesLibrary);
		pw.addTable();
		
		Vector<BaseDataBean> data;
		Vector<String> setupDataArr = new Vector<String>();
		//depending on whether users select "All" for shipFromLocation or not, show detail information or just the summary for the supplier
		if (!StringHandler.isBlankString(inputBean.getShipFromLocationId())) {
			data = (Vector) getSearchResult(inputBean);
			
			//write column headers
			pw.addRow();
			pw.addCellKeyBold("label.supplier");
			pw.addCell(inputBean.getSupplierName());
			pw.addRow();
			pw.addCellKeyBold("label.location");
			pw.addCell(inputBean.getLocationDesc());
			if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
				pw.addRow();
				pw.addCellKeyBold("label.search");
				pw.addCell(inputBean.getSearchFieldName() + " " + inputBean.getSearchModeName() + " "+ inputBean.getSearchArgument());
			}
			
			String dateCondition = "";
			if (inputBean.getTransactionFromDate() != null)
				dateCondition += commonResourcesLibrary.getString("label.from") + " " + DateHandler.formatDate(inputBean.getTransactionFromDate(), "MM/dd/yyyy") + " ";
			if (inputBean.getTransactionToDate() != null)
				dateCondition += commonResourcesLibrary.getString("label.to") + " " + DateHandler.formatDate(inputBean.getTransactionToDate(), "MM/dd/yyyy");
			if (!StringHandler.isBlankString(dateCondition)) {
				pw.addRow();
				pw.addCellKeyBold("label.transactiondate");
				pw.addCell(dateCondition);
			}
			
			if (!StringHandler.isBlankString(inputBean.getTransactionType())) {
				pw.addRow();
				pw.addCellKeyBold("label.type");
				pw.addCell(inputBean.getTransactionType());
			}
			
			if (!StringHandler.isBlankString(inputBean.getStatus())) {
				pw.addRow();
				pw.addCellKeyBold("label.status");
				pw.addCell(inputBean.getStatus());
			}
			
			if ("Y".equalsIgnoreCase(inputBean.getIncludeHistory())) {
				pw.addRow();
				pw.addThRegion("label.includehistory", 1, 2);
			}
			
			if ("Y".equalsIgnoreCase(inputBean.getGfpPartsOnly())) {
				pw.addRow();
				pw.addThRegion("label.gfppartsonly", 1, 2);
			}

			pw.addRow();
			pw.addRow();
			
			//headerKey + "|" + columnType + "|" + columnWidth + "|" + fieldName or database_column_name
			setupDataArr.add("label.location|||locationDesc");
			setupDataArr.add("label.partnumber|" + ExcelHandler.TYPE_PARAGRAPH + "||catPartNo");
			setupDataArr.add("label.conversiongroup|||conversionGroup");
			setupDataArr.add("label.gfp|||gfp");
			setupDataArr.add("label.reorderpoint|||reorderPoint");
			setupDataArr.add("label.stockinglevel|||stockingLevel");
			setupDataArr.add("label.purchaseqty|||purchaseQty");
			setupDataArr.add("label.partdescription|||partDescription");
			setupDataArr.add("label.uom|||uom");
			setupDataArr.add("label.type|||transactionType");
			setupDataArr.add("label.inventoryid|||inventoryId");
			setupDataArr.add("label.vendorpo|||supplierPoNumber");
			setupDataArr.add("label.blanketpo|||blanketPo");
			setupDataArr.add("label.vmi|||vmi");
			setupDataArr.add("label.boltrackingnumber|||bolTrackingNum");
			setupDataArr.add("label.orderquantity|||quantity");
			setupDataArr.add("label.lot|||mfgLot");
			setupDataArr.add("label.storagelocation|||bin");
			setupDataArr.add("label.expiredate|" + ExcelHandler.TYPE_CALENDAR + "||expireDate");
			setupDataArr.add("label.user|||transactionUserName");
			setupDataArr.add("label.transactiondate|" + ExcelHandler.TYPE_CALENDAR + "||transactionDate");
			setupDataArr.add("label.status|||status");
			setupDataArr.add("label.dono|||customerPoNo");
			setupDataArr.add("label.poline|||radianPoLine");
			setupDataArr.add("label.qty|||customerPoQty");
			setupDataArr.add("label.availableqty|||availableQty");
			setupDataArr.add("label.openqty|||openQty");
			setupDataArr.add("label.notes|||notes");
		} else {
			data = (Vector) getSummarySearchResult(inputBean);
			
			//write column headers
			pw.addRow();
			pw.addCellKeyBold("label.supplier");
			pw.addCell(inputBean.getSupplierName());
			
			if ("Y".equalsIgnoreCase(inputBean.getGfpPartsOnly())) {
				pw.addRow();
				pw.addThRegion("label.gfppartsonly", 1, 2);
			}
			pw.addRow();
			pw.addRow();
			
			//headerKey + "|" + columnType + "|" + columnWidth + "|" + fieldName or database_column_name
			setupDataArr.add("label.location|||locationDesc");
			setupDataArr.add("label.partnumber|" + ExcelHandler.TYPE_PARAGRAPH + "||catPartNo");
			setupDataArr.add("label.conversiongroup|||conversionGroup");
			setupDataArr.add("label.gfp|||gfp");
			setupDataArr.add("label.reorderpoint|||reorderPoint");
			setupDataArr.add("label.stockinglevel|||stockingLevel");
			setupDataArr.add("label.mr.qty|||totalMrQty");
			setupDataArr.add("label.availableqty|||totalAvailableQty");
			setupDataArr.add("label.openpoqty|||totalOpenPoQty");
			setupDataArr.add("label.purchaseqty|||pendingBuyQty");
		}
		pw.fill(setupDataArr, data);
		
		return pw;
	}
}