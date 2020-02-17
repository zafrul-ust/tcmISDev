package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.CabinetBinItemViewBeanFactory;
import com.tcmis.internal.hub.factory.HubCabinetViewBeanFactory;
import com.tcmis.ws.scanner.process.CabinetCountProcess;

/******************************************************************************
 * Process for ManualCabinetCount
 * 
 * @version 1.0
 *****************************************************************************/

public class ManualCabinetCountProcess extends BaseProcess {
	Log				log					= LogFactory.getLog(this.getClass());
	private boolean	calledFromNewPage	= false;

	public ManualCabinetCountProcess(String client) {
		super(client);
	}

	public void setCalledFromNewPage(boolean value) {
		calledFromNewPage = value;
	}

	public String insertCabinetItemInventoryCounts(Collection<CabinetItemInventoryCountBean> cabinetBinItemViewBeanCollection, BigDecimal personnelId) throws BaseException, Exception {
		return insertCabinetItemInventoryCounts(cabinetBinItemViewBeanCollection, personnelId, null);
	}

	public String insertCabinetItemInventoryCounts(Collection<CabinetItemInventoryCountBean> cabinetBinItemViewBeanCollection, BigDecimal personnelId, String clientTrackingURL) throws BaseException, Exception {
		ResourceLibrary resource = new ResourceLibrary("scannerupload");
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
		BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
		String mrsCreated = "";
		String errorMessage = "";
		Collection cabinetItemInventoryCountColl = new Vector();
		BigDecimal uploadSeq = getNextUpLoadSeq();
		Date countDateTime = Calendar.getInstance().getTime();

		Vector<CabinetInventoryCountStageBean> kanbanBeans = new Vector();
		Vector<WorkAreaBinCountBean> receiptCountBeans = new Vector();
		Vector<WorkAreaBinCountBean> workAreaBinCountBeans = new Vector();
		boolean hasDisbursement = false;

		String companyId = "";
		Vector receiptData = new Vector();
		Vector processedBinIds = new Vector();
		for (CabinetItemInventoryCountBean inputBean : cabinetBinItemViewBeanCollection) {
			if (processedBinIds.contains(inputBean.getBinId())) continue;
			if (inputBean.isCountByReceipt() && inputBean.hasReceiptId()) {
				if (StringHandler.isBlankString(companyId)) companyId = inputBean.getCompanyId();
				String[] receiptIds = inputBean.getReceiptId().split("\\|");
				String[] receiptQtys = inputBean.getReceiptQty().split("\\|");
				for (int i = 0; i < receiptIds.length; i++) {
					if (Integer.parseInt(receiptQtys[i]) >= 0) {
						WorkAreaBinCountBean countBean = new WorkAreaBinCountBean();
						countBean.setBinId(inputBean.getBinId());
						countBean.setCompanyId(companyId);
						countBean.setCountDatetime(countDateTime);
						countBean.setCountQuantity(new BigDecimal(receiptQtys[i]));
						countBean.setCountSource("MANUAL");
						countBean.setCountType("RECEIPT_ID");
						countBean.setPersonnelId(personnelId);
						countBean.setReceiptId(new BigDecimal(receiptIds[i]));
						countBean.setContainerNumber(new BigDecimal(0));
						countBean.setUploadSequence(uploadSeq);
						receiptCountBeans.add(countBean);
						// keeping track of bin ids that already processed
					}
				}
				processedBinIds.addElement(inputBean.getBinId());
			}
			if (inputBean.isAdvancedReceipt() && inputBean.hasReceiptId()) {
				if (StringHandler.isBlankString(companyId)) companyId = inputBean.getCompanyId();
				String[] receiptIds = inputBean.getReceiptId().split("\\|");
				String[] newQtys = StringUtils.splitPreserveAllTokens(inputBean.getReceiptQty(), "\\|");
				String[] expiredQtys = StringUtils.splitPreserveAllTokens(inputBean.getReceiptExpiredQty(), "\\|");
				String[] damagedQtys = StringUtils.splitPreserveAllTokens(inputBean.getReceiptDamagedQty(), "\\|");
				for (int i = 0; i < receiptIds.length; i++) {
					if (StringUtils.isNotBlank(newQtys[i]) && Integer.parseInt(newQtys[i]) > 0) {
						WorkAreaBinCountBean countBean = new WorkAreaBinCountBean();
						countBean.setBinId(inputBean.getBinId());
						countBean.setCompanyId(companyId);
						countBean.setCountDatetime(countDateTime);
						countBean.setCountQuantity(new BigDecimal(newQtys[i]));
						countBean.setCountSource("MANUAL");
						countBean.setCountType(inputBean.getCountType());
						countBean.setPersonnelId(personnelId);
						countBean.setReceiptId(new BigDecimal(receiptIds[i]));
						countBean.setContainerNumber(new BigDecimal(0));
						countBean.setUploadSequence(uploadSeq);
						countBean.setContainerOpen("N");
						countBean.setContainerExpired("N");
						countBean.setContainerCondition(null);
						receiptCountBeans.add(countBean);
					}
					if (StringUtils.isNotBlank(damagedQtys[i]) && Integer.parseInt(damagedQtys[i]) > 0) {
						WorkAreaBinCountBean countBean = new WorkAreaBinCountBean();
						countBean.setBinId(inputBean.getBinId());
						countBean.setCompanyId(companyId);
						countBean.setCountDatetime(countDateTime);
						countBean.setCountQuantity(new BigDecimal(damagedQtys[i]));
						countBean.setCountSource("MANUAL");
						countBean.setCountType(inputBean.getCountType());
						countBean.setPersonnelId(personnelId);
						countBean.setReceiptId(new BigDecimal(receiptIds[i]));
						countBean.setContainerNumber(new BigDecimal(0));
						countBean.setUploadSequence(uploadSeq);
						countBean.setContainerOpen("N");
						countBean.setContainerExpired("N");
						countBean.setContainerCondition("Damaged, Customer");
						receiptCountBeans.add(countBean);
					}
					if (StringUtils.isNotBlank(expiredQtys[i]) && Integer.parseInt(expiredQtys[i]) > 0) {
						WorkAreaBinCountBean countBean = new WorkAreaBinCountBean();
						countBean.setBinId(inputBean.getBinId());
						countBean.setCompanyId(companyId);
						countBean.setCountDatetime(countDateTime);
						countBean.setCountQuantity(new BigDecimal(expiredQtys[i]));
						countBean.setCountSource("MANUAL");
						countBean.setCountType(inputBean.getCountType());
						countBean.setPersonnelId(personnelId);
						countBean.setReceiptId(new BigDecimal(receiptIds[i]));
						countBean.setContainerNumber(new BigDecimal(0));
						countBean.setUploadSequence(uploadSeq);
						countBean.setContainerOpen("N");
						countBean.setContainerExpired("Y");
						countBean.setContainerCondition(null);
						receiptCountBeans.add(countBean);
					}
				}
				processedBinIds.addElement(inputBean.getBinId());
			}
			else if ((inputBean.isCountByItemId() || inputBean.isCountByPart()) && inputBean.hasCountQuantity()) {
				cabinetItemInventoryCountColl.add(inputBean);
				// keeping track of bin ids that already processed
				processedBinIds.addElement(inputBean.getBinId());
			}
			else if (inputBean.isKanBanCount() && inputBean.hasKanbanQuantity()) {
				CabinetInventoryCountStageBean kbBean = new CabinetInventoryCountStageBean();
				kbBean.setBinId(inputBean.getBinId());
				kbBean.setCountDatetime(countDateTime);
				kbBean.setCountQuantity(inputBean.getKanbanBinScanQty());
				kbBean.setCompanyId(inputBean.getCompanyId());
				kbBean.setPersonnelId(personnelId);
				kbBean.setUploadSequence(uploadSeq);
				kanbanBeans.add(kbBean);
				// keeping track of bin ids that already processed
				processedBinIds.addElement(inputBean.getBinId());
			}
			else if ((inputBean.isAutomaticRefill() || inputBean.isDisbursementCount()) && inputBean.hasCountQuantity()) {
				WorkAreaBinCountBean autoBean = new WorkAreaBinCountBean();
				autoBean.setBinId(inputBean.getBinId());
				autoBean.setCompanyId(inputBean.getCompanyId());
				if (inputBean.hasCountDateTime()) {
					autoBean.setCountDatetime(inputBean.getCountDatetime());
				}
				else {
					autoBean.setCountDatetime(countDateTime);
				}
				autoBean.setCountQuantity(inputBean.getCountQuantity());
				autoBean.setCountSource("MANUAL");
				autoBean.setCountType(inputBean.getCountType());
				autoBean.setPersonnelId(personnelId);
				autoBean.setUploadSequence(uploadSeq);
				if (inputBean.isDisbursementCount()) {
					hasDisbursement = true;
					autoBean.setUnitOfMeasurement(inputBean.getDisbursedUnitOfMeasure());
					autoBean.setContainerNumber(new BigDecimal(0));
				}
				workAreaBinCountBeans.add(autoBean);
			}
		}

		// processing data
		DbManager dbManager = new DbManager(getClient());
		Connection connection = null;
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new CabinetItemInventoryCountBean());
		try {
			connection = dbManager.getConnection();
			// Process receipt
			if (receiptCountBeans.size() > 0) {
				CabinetCountProcess cabinetCountProcess = new CabinetCountProcess(getClient(), getLocaleObject());
				cabinetCountProcess.setConnection(connection);
				mrsCreated += cabinetCountProcess.processWorkAreaBinCount(personnelId, receiptCountBeans, uploadSeq);
			}

			// Process KanBan
			if (kanbanBeans.size() > 0) {
				CabinetCountProcess cabinetCountProcess = new CabinetCountProcess(getClient(), getLocaleObject());
				cabinetCountProcess.setConnection(connection);
				mrsCreated += cabinetCountProcess.processKban(kanbanBeans);
			}

			// Process the item
			if (cabinetItemInventoryCountColl.size() > 0) {
				com.tcmis.ws.scanner.process.ManualCabinetCountProcess manualProcess = new com.tcmis.ws.scanner.process.ManualCabinetCountProcess(getClient());
				manualProcess.setConnection(connection);
				mrsCreated += manualProcess.doCabinetItemInventoryCountInserts(cabinetItemInventoryCountColl, personnelId, uploadSeq);
			}

			// process AutoFill or Disbursement
			if (workAreaBinCountBeans.size() > 0) {
				//if disbursement
				if (hasDisbursement) {
					CabinetCountProcess cabinetCountProcess = new CabinetCountProcess(getClient(), getLocaleObject());
					cabinetCountProcess.setConnection(connection);
					cabinetCountProcess.processWorkAreaBinCount(personnelId,workAreaBinCountBeans,uploadSeq);
				}else {
					processWorkAreaBinCount(workAreaBinCountBeans, genericSqlFactory, connection);
					Vector inArgs = new Vector(1);
					inArgs.add(uploadSeq);
					Vector outArgs = new Vector(1);
					outArgs.add(new Integer(java.sql.Types.VARCHAR));
					Vector error = (Vector) genericSqlFactory.doProcedure(connection, "pkg_work_area_bin_count.P_PROCESS_COUNT", inArgs, outArgs);
					if (error.size() > 1 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
						MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "ManualCabinetCountProcess Error encountered while calling pkg_work_area_bin_count.P_PROCESS_COUNT:" + uploadSeq, (String) error.get(0));
					}
				}
			}

		}
		catch (Exception e) {
			log.error(e);
		}
		finally {
			dbManager.returnConnection(connection);
			connection = null;
		}

		if (mrsCreated.length() > 0) {
			String message;
			String title;
			if (StringHandler.isBlankString(clientTrackingURL)) {
				String wwwHome = resource.getString("upload.hosturl");
				String resultUrl = wwwHome + resource.getString("upload.cabinetcountresult");
				message = library.getString("letter.cabinetscannerupload") + resultUrl + "UserAction=Search&mrslist=" + mrsCreated + "\n\n\n" + library.getString("label.personnelid") + " : " + personnelId;
				title = library.getString("cabinetcountupload.title.label");
			}
			else {
				message = library.getString("letter.itemconsignmentresults") + clientTrackingURL + "?mrslist=" + mrsCreated + "\n\n\n" + library.getString("label.personnelid") + " : " + personnelId;
				;
				title = library.getString("cabinetcountupload.title.label");
			}
			bulkMailProcess.sendBulkEmail(personnelId, title, message, true);

			return "";
		}
		else {
			return errorMessage;
		}
	} // end of method

	private void processWorkAreaBinCount(Collection workAreaBinCountBeans, GenericSqlFactory genericSqlFactory, Connection connection) throws BaseException {
		StringBuilder query = null;
		Iterator iter = workAreaBinCountBeans.iterator();
		while (iter.hasNext()) {
			WorkAreaBinCountBean workAreaBinCountBean = (WorkAreaBinCountBean) iter.next();
			query = new StringBuilder("insert into work_area_bin_count (upload_sequence,bin_id,count_datetime,company_id,count_type,personnel_id,count_quantity,count_source,processing_status,reporting_item_id)");
			query.append(" values (").append(workAreaBinCountBean.getUploadSequence()).append(",").append(workAreaBinCountBean.getBinId()).append(",");
			query.append(DateHandler.getOracleToDateFunction(workAreaBinCountBean.getCountDatetime())).append(",");
			query.append("'").append(workAreaBinCountBean.getCompanyId()).append("',");
			query.append("'").append(workAreaBinCountBean.getCountType()).append("',").append(workAreaBinCountBean.getPersonnelId()).append(",");
			query.append(workAreaBinCountBean.getCountQuantity());
			if (StringHandler.isBlankString(workAreaBinCountBean.getCountSource())) {
				query.append(",'SCANNER'");
			}
			else {
				query.append(",'").append(workAreaBinCountBean.getCountSource()).append("'");
			}
			query.append(",'NEW'");
			String reporting_item_id = ",pkg_work_area_bin_count.fx_bin_best_item_id ("+workAreaBinCountBean.getBinId()+")";
			query.append(reporting_item_id);
			query.append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
		}
	} // end of method

	// ----------
	public Collection getCabinetsCollection(ManualCabinetCountInputBean bean) throws BaseException, Exception {
		return getCabinetsList(getSearchData(bean));
	}

	public Object[] showResult(ManualCabinetCountInputBean bean) throws BaseException {
		Collection flatData = getSearchData(bean);
		HashMap m1 = new HashMap();
		Integer i1 = null;
		HashMap map = null;
		/*
		 * old splitting cell code CabinetBinItemViewBean pbean = null; Iterator
		 * flatDataIter = flatData.iterator(); String cabinetname = null; int
		 * row = 0; while (flatDataIter.hasNext()) { pbean =
		 * (CabinetBinItemViewBean) flatDataIter.next(); cabinetname =
		 * pbean.getCabinetName(); pbean.setCabinetRowspan(row); row++; if
		 * (m1.get(cabinetname) == null) { i1 = new Integer(0);
		 * m1.put(cabinetname, i1); } i1 = (Integer) m1.get(cabinetname); i1 =
		 * new Integer(i1.intValue() + 1); m1.put(cabinetname, i1);
		 * 
		 * }
		 */
		Object[] objs = { flatData, m1 };
		return objs;
	}

	private Collection getCabinetsList(Collection flatData) {
		CabinetBinItemViewBean flatBean = null;
		CabinetBinItemViewBean cabinetBean = null;
		CabinetBinItemViewBean itemBean = null;
		String lastCabinet = "";
		ArrayList cabinetsList = new ArrayList();
		ArrayList itemsList = null;
		Iterator flatDataIter = flatData.iterator();

		try {
			while (flatDataIter.hasNext()) {
				flatBean = (CabinetBinItemViewBean) flatDataIter.next();

				if (!flatBean.getCabinetName().equals(lastCabinet)) {
					// new cabinet
					if (cabinetBean != null) {
						cabinetBean.setItemsList((ArrayList) itemsList.clone());
						cabinetBean.setCabinetRowspan(itemsList.size());
						cabinetsList.add(cabinetBean.clone());
					}
					cabinetBean = new CabinetBinItemViewBean();
					copyCabinetData(flatBean, cabinetBean);
					itemsList = new ArrayList();
					lastCabinet = cabinetBean.getCabinetName();
					itemBean = null;
				}
				itemBean = new CabinetBinItemViewBean();
				copyBinData(flatBean, itemBean);
				itemsList.add(itemBean.clone());
			}
			if (cabinetBean != null) {
				cabinetBean.setItemsList((ArrayList) itemsList.clone());
				cabinetBean.setCabinetRowspan(itemsList.size());
				cabinetsList.add(cabinetBean.clone());
			}
		}
		catch (Exception e) {
			log.error("Exception in getCabinetsList: " + e);
		}

		return cabinetsList;
	}

	private void copyBinData(CabinetBinItemViewBean source, CabinetBinItemViewBean dest) {
		dest.setCompanyId(source.getCompanyId());
		dest.setBinName(source.getBinName());
		dest.setBinId(source.getBinId());
		dest.setPackaging(source.getPackaging());
		dest.setDescription(source.getDescription());
		dest.setItemId(source.getItemId());
		dest.setCabinetName(source.getCabinetName());
		dest.setCabinetId(source.getCabinetId());

	}

	private void copyCabinetData(CabinetBinItemViewBean source, CabinetBinItemViewBean dest) {
		dest.setCabinetName(source.getCabinetName());
		dest.setCabinetId(source.getCabinetId());
	}

	public Collection getSearchData(ManualCabinetCountInputBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		CabinetBinItemViewBeanFactory factory = new CabinetBinItemViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (!StringHandler.isBlankString(input.getCompanyId())) {
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, input.getCompanyId());
		}

		if (!StringHandler.isBlankString(input.getFacilityId())) {
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		}

		if (!StringHandler.isBlankString(input.getAreaId())) {
			criteria.addCriterion("areaId", SearchCriterion.IN, "'" + input.getAreaId().replace("|", "','") + "'");
		}

		if (!StringHandler.isBlankString(input.getBuildingId())) {
			criteria.addCriterion("buildingId", SearchCriterion.IN, "'" + input.getBuildingId().replace("|", "','") + "'");
		}

		if (!StringHandler.isBlankString(input.getDeptId())) {
			criteria.addCriterion("deptId", SearchCriterion.IN, "'" + input.getDeptId().replace("|", "','") + "'");
		}

		if (input.getCabinetIdArray() != null && input.getCabinetIdArray().length > 0) {
			criteria.addCriterionArray("cabinetId", SearchCriterion.IN, input.getCabinetIdArray());
		}

		criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
		criteria.addCriterion("cpigStatus", SearchCriterion.CUSTOM, "<> 'O'");

		if (!StringHandler.isBlankString(input.getSearchArgument())) {
			String mode = input.getSearchMode();
			String field = input.getSearchField();
			if (mode.equals("is")) {
				criteria.addCriterion(field, SearchCriterion.EQUALS, input.getSearchArgument(), SearchCriterion.IGNORE_CASE);
			}

			if (mode.equals("contains")) {
				criteria.addCriterion(field, SearchCriterion.LIKE, input.getSearchArgument(), SearchCriterion.IGNORE_CASE);

			}
		}
		criteria.addCriterion("countType", SearchCriterion.NOT_EQUAL, "N");

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("applicationDesc");
		sortCriteria.addCriterion("catPartNo");
		sortCriteria.addCriterion("binId");
		sortCriteria.addCriterion("itemId");

		return factory.select(criteria, sortCriteria);
	}

	public Collection getAllLabelData(PersonnelBean personnelBean) throws BaseException, Exception {
		Collection criteriaColl = new Vector();
		Iterator iterator = personnelBean.getHubInventoryGroupOvBeanCollection().iterator();
		while (iterator.hasNext()) {
			HubInventoryGroupOvBean ovBean = (HubInventoryGroupOvBean) iterator.next();
			criteriaColl.add(ovBean.getBranchPlant());
		}
		DbManager dbManager = new DbManager(getClient());
		HubCabinetViewBeanFactory factory = new HubCabinetViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("branchPlant", SearchCriterion.EQUALS, criteriaColl);

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("hubName");
		sortCriteria.addCriterion("facilityId");
		sortCriteria.addCriterion("application");
		sortCriteria.addCriterion("cabinetName");
		Collection flatCollection = factory.select(criteria, sortCriteria);
		return this.getNormalizedHubCollection(flatCollection);
	}

	private Collection getSortedCabinetBeanForAllWorkAreasCollection(String branchPlant, String facilityId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		HubCabinetViewBeanFactory factory = new HubCabinetViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("branchPlant", SearchCriterion.EQUALS, branchPlant);
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("cabinetName");
		return factory.select(criteria, sortCriteria);
	}

	public Collection update(Collection manualCabinetCountInputBeanCollection, ManualCabinetCountInputBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		Iterator iterator = manualCabinetCountInputBeanCollection.iterator();
		while (iterator.hasNext()) {
			ManualCabinetCountInputBean updateBean = (ManualCabinetCountInputBean) iterator.next();
		}
		return this.getSearchData(bean);
	}

	public ExcelHandler getExcelReport(ManualCabinetCountInputBean bean, Locale locale) {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		Collection data;
		try {
			data = this.getSearchData(bean);
		}
		catch (BaseException e) {
			log.debug("caught BaseException; " + e.toString());
			return pw;
		}
		Iterator iterator = data.iterator();
		pw.addTable();

		// write column headers
		pw.addRow();

		pw.addCellKeyBold("label.workarea");
		pw.addCellKeyBold("label.catalog");
		pw.addCellKeyBold("label.partno");
		pw.addCellKeyBold("label.item");
		pw.addCellKeyBold("label.status");
		pw.addCellKeyBold("label.packaging");
		pw.addCellKeyBold("label.description");
		pw.addCellKeyBold("label.binname");

		while (iterator.hasNext()) {
			CabinetBinItemViewBean cabinetBinItemViewBean = (CabinetBinItemViewBean) iterator.next();
			pw.addRow();
			pw.addCell(cabinetBinItemViewBean.getApplicationDesc());
			pw.addCell(cabinetBinItemViewBean.getCatalogDesc());
			pw.addCell(cabinetBinItemViewBean.getCatPartNo());
			pw.addCell(cabinetBinItemViewBean.getItemId());
			if ("A".equalsIgnoreCase(cabinetBinItemViewBean.getCpigStatus())) pw.addCell(library.getString("label.active"));
			else if ("D".equalsIgnoreCase(cabinetBinItemViewBean.getCpigStatus())) pw.addCell(library.getString("label.drawdown"));
			else if ("O".equalsIgnoreCase(cabinetBinItemViewBean.getCpigStatus())) pw.addCell(library.getString("label.obsolete"));
			else
				pw.addCell("");
			pw.addCell(cabinetBinItemViewBean.getPackaging());
			pw.addCell(cabinetBinItemViewBean.getDescription());
			pw.addCell(cabinetBinItemViewBean.getBinName());
		}
		return pw;
	}

	private String getPropertyValue(String propertyName, ResourceLibrary library) {
		String propertyValue = propertyName; // just in case it is not
		// found in library;
		try {
			propertyValue = library.getString(propertyName);
		}
		catch (Exception e) {
			log.debug("caught exception resolving property: [" + propertyName + "]; " + e.toString());
		}
		return propertyValue;
	}

	private Collection getNormalizedHubCollection(Collection c) throws Exception {

		Collection normalizedCollection = new Vector(c.size());
		Iterator flatIterator = c.iterator();
		String previousHub = "";
		String previousFacility = "";
		String previousApplication = "";
		HubCabinetViewBean hubBean = null;
		HubCabinetViewBean facilityBean = null;
		HubCabinetViewBean applicationBean = null;
		HubCabinetViewBean cabinetBean = null;
		while (flatIterator.hasNext()) {

			HubCabinetViewBean flatBean = (HubCabinetViewBean) flatIterator.next();
			if (flatBean != null) {

				if (previousHub != null && !previousHub.equalsIgnoreCase(flatBean.getBranchPlant())) {
					// new bean
					if (previousHub.length() == 0) {
						// first time in loop
						hubBean = new HubCabinetViewBean();
						facilityBean = new HubCabinetViewBean();
						applicationBean = new HubCabinetViewBean();
						cabinetBean = new HubCabinetViewBean();
						this.copyCabinetData(flatBean, cabinetBean);
						this.copyApplicationData(flatBean, applicationBean);
						this.copyFacilityData(flatBean, facilityBean);
						this.copyHubData(flatBean, hubBean);
					}
					else {
						// different hub
						applicationBean.addCabinetBean((HubCabinetViewBean) cabinetBean.clone());
						facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
						hubBean.addFacilityBean((HubCabinetViewBean) facilityBean.clone());
						normalizedCollection.add(hubBean.clone());
						hubBean = new HubCabinetViewBean();
						facilityBean = new HubCabinetViewBean();
						applicationBean = new HubCabinetViewBean();
						cabinetBean = new HubCabinetViewBean();
						this.copyCabinetData(flatBean, cabinetBean);
						this.copyApplicationData(flatBean, applicationBean);
						this.copyFacilityData(flatBean, facilityBean);
						this.copyHubData(flatBean, hubBean);
					}
				}
				else {
					// same hub
					if (previousFacility != null && !previousFacility.equalsIgnoreCase(flatBean.getFacilityId())) {
						// new facility
						applicationBean.addCabinetBean((HubCabinetViewBean) cabinetBean.clone());
						facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
						hubBean.addFacilityBean((HubCabinetViewBean) facilityBean.clone());
						facilityBean = new HubCabinetViewBean();
						applicationBean = new HubCabinetViewBean();
						cabinetBean = new HubCabinetViewBean();
						this.copyCabinetData(flatBean, cabinetBean);
						this.copyApplicationData(flatBean, applicationBean);
						this.copyFacilityData(flatBean, facilityBean);
					}
					else {
						// same dock, check for new
						// application
						if (previousApplication != null && !previousApplication.equalsIgnoreCase(flatBean.getApplication())) {
							applicationBean.addCabinetBean((HubCabinetViewBean) cabinetBean.clone());
							facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
							applicationBean = new HubCabinetViewBean();
							cabinetBean = new HubCabinetViewBean();
							this.copyCabinetData(flatBean, cabinetBean);
							this.copyApplicationData(flatBean, applicationBean);
						}
						else {
							// same application, add
							// cabinet
							applicationBean.addCabinetBean((HubCabinetViewBean) cabinetBean.clone());
							cabinetBean = new HubCabinetViewBean();
							this.copyCabinetData(flatBean, cabinetBean);
						}
					}
				}
				previousHub = flatBean.getBranchPlant();
				previousFacility = flatBean.getFacilityId();
				previousApplication = flatBean.getApplication();
			}
		}
		applicationBean.addCabinetBean((HubCabinetViewBean) cabinetBean.clone());
		facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
		hubBean.addFacilityBean((HubCabinetViewBean) facilityBean.clone());
		normalizedCollection.add(hubBean.clone());
		return normalizedCollection;
	}

	private void copyHubData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
		// toBean.setPreferredWarehouse(fromBean.getPreferredWarehouse());
		toBean.setBranchPlant(fromBean.getBranchPlant());
		toBean.setHubName(fromBean.getHubName());
	}

	private void copyFacilityData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) throws BaseException, Exception {
		toBean.setFacilityId(fromBean.getFacilityId());
		toBean.setFacilityName(fromBean.getFacilityName());
		toBean.setSortedCabinetBeanForAllWorkAreasCollection(this.getSortedCabinetBeanForAllWorkAreasCollection(fromBean.getBranchPlant(), fromBean.getFacilityId()));
	}

	private void copyApplicationData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
		toBean.setApplication(fromBean.getApplication());
		toBean.setApplicationDesc(fromBean.getApplicationDesc());
	}

	private void copyCabinetData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
		toBean.setCabinetId(fromBean.getCabinetId());
		toBean.setCabinetName(fromBean.getCabinetName());
	}

	public BigDecimal getNextUpLoadSeq() throws BaseException {
		BigDecimal b = null;
		Connection connection = null;
		DbManager dbManager1 = new DbManager(getClient(), this.getLocale());
		try {
			connection = dbManager1.getConnection();
			b = new BigDecimal("" + new SqlManager().getOracleSequence(connection, "upload_sequence"));
		}
		finally {
			dbManager1.returnConnection(connection);
			dbManager1 = null;
			connection = null;
		}
		return b;
	}

	public Collection getReceipts(String binId, String receiptId, String receiptQty, String receiptDamaged, String receiptExpired) throws BaseException {
		Collection<CabinetInventoryCountStageBean> receipts = getReceipts(binId);

		// populate with receipt quantities
		String[] receiptIds = receiptId.split("\\|");
		String[] receiptQtys = receiptQty.split("\\|");
		String[] receiptExpireds = ("" + receiptExpired).split("\\|");
		String[] receiptDamageds = ("" + receiptDamaged).split("\\|");
		Vector matchingReceiptId = new Vector();

		for (CabinetInventoryCountStageBean receipt : receipts) {
			for (int i = 0; i < receiptIds.length; i++) {
				if (receipt.getReceiptId().toString().equals(receiptIds[i])) {
					receipt.setCountQuantity(new BigDecimal(receiptQtys[i]));
					if (receiptDamageds.length >= i && StringUtils.isNotBlank(receiptDamageds[i])) {
						receipt.setReceiptDamagedQty(receiptDamageds[i]);
					}
					if (receiptExpireds.length >= i && StringUtils.isNotBlank(receiptExpireds[i])) {
						receipt.setReceiptExpiredQty(receiptExpireds[i]);
					}
					matchingReceiptId.add(new BigDecimal(i));
				}
			}
			receipt.setEditableReceipt(false);
		}

		// add new receipts to return
		for (int i = 0; i < receiptIds.length; i++) {
			if (matchingReceiptId.contains(new BigDecimal(i))) continue;
			// only adding records if it's not previously matched
			if (!StringHandler.isBlankString(receiptIds[i]) && !StringHandler.isBlankString(receiptQtys[i])) {
				CabinetInventoryCountStageBean receipt = new CabinetInventoryCountStageBean();
				receipt.setReceiptId(new BigDecimal(receiptIds[i]));
				receipt.setCountQuantity(new BigDecimal(receiptQtys[i]));
				receipt.setEditableReceipt(true);
				if (receiptDamageds.length >= i && StringUtils.isNotBlank(receiptDamageds[i])) {
					receipt.setReceiptDamagedQty(receiptQtys[i]);
				}
				if (receiptExpireds.length >= i && StringUtils.isNotBlank(receiptExpireds[i])) {
					receipt.setReceiptExpiredQty(receiptExpireds[i]);
				}
				receipts.add(receipt);
			}
		}
		return receipts;
	}

	public Collection getReceipts(String binId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory sqlFactory = new GenericSqlFactory(dbManager, new CabinetInventoryCountStageBean());

		Collection results;

		try {
			StringBuilder query = new StringBuilder("select * from table (pkg_work_area_management.fx_bin_expected_receipt_pipe ('");
			query.append(binId).append("'))");
			query.append(" order by receipt_id");
			results = sqlFactory.selectQuery(query.toString());
		}
		catch (Exception e) {
			log.error("Error processing ManualCabinetCountProcess" + e.getMessage(), e);
			throw new BaseException(e);
		}
		finally {
			// factory = null;
			dbManager = null;
		}

		return results;
	}

	public String validateNewReceipts(Collection<CabinetInventoryCountStageBean> receipts, String itemId) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory sqlFactory = new GenericSqlFactory(dbManager, new CabinetInventoryCountStageBean());

		Collection results;
		String invalidReceipts = "";

		try {
			for (CabinetInventoryCountStageBean receipt : receipts) {
				// only check manually added receipts
				if (receipt.isEditableReceipt() && receipt.getCountQuantity() != null && receipt.getCountQuantity().intValue() >= 0) {
					String query = "SELECT receipt_id FROM receipt WHERE receipt_id = " + receipt.getReceiptId() + " AND item_id = " + itemId;
					log.debug(query);

					results = sqlFactory.selectQuery(query);

					if (results.size() < 1) invalidReceipts += receipt.getReceiptId() + "<br>";
				}
			}
		}
		catch (Exception e) {
			log.error("Error processing ManualCabinetCountProcess" + e.getMessage(), e);
			throw new BaseException(e);
		}
		finally {
			// factory = null;
			dbManager = null;
		}

		if (invalidReceipts.length() > 0) invalidReceipts = "Invalid receipt ids: <br>" + invalidReceipts;

		return invalidReceipts;
	}

	public Object[] getDataRowSpan(Collection dataColl) throws Exception {
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		Boolean containAutomaticRefillData = new Boolean(false);
		Boolean containDisbursementData = new Boolean(false);
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		StringBuilder firstLevelKey;
		// looping thru line data
		Iterator iter = dataColl.iterator();
		while (iter.hasNext()) {
			CabinetBinItemViewBean tmpBean = (CabinetBinItemViewBean) iter.next();
			// check to see any record has count_type = 'AutomaticRefill'
			if ("AutomaticRefill".equals(tmpBean.getCountType()) && !containAutomaticRefillData.booleanValue()) containAutomaticRefillData = new Boolean(true);
			// check to see any record has count_type = 'DISBURSEMENT'
			if ("DISBURSEMENT".equals(tmpBean.getCountType()) && !containDisbursementData.booleanValue()) containDisbursementData = new Boolean(true);
			// do row span logic
			firstLevelKey = new StringBuilder(tmpBean.getCabinetId().toString()).append(tmpBean.getBinId().toString()).append(tmpBean.getCatPartNo());
			StringBuilder secondLevelKey = new StringBuilder("secondLevel").append(firstLevelKey).append(tmpBean.getItemId());

			if (m1.get(firstLevelKey.toString()) == null) {
				i1 = new Integer(0);
				m1.put(firstLevelKey.toString(), i1);
				map = new HashMap();
				m2.put(firstLevelKey.toString(), map);
			}
			i1 = (Integer) m1.get(firstLevelKey.toString());
			i1 = new Integer(i1.intValue() + 1);
			m1.put(firstLevelKey.toString(), i1);

			// second level
			if (map.get(secondLevelKey.toString()) == null) {
				i2 = new Integer(0);
				map.put(secondLevelKey.toString(), i2);
			}
			i2 = (Integer) map.get(secondLevelKey.toString());
			i2 = new Integer(i2.intValue() + 1);
			map.put(secondLevelKey.toString(), i2);

		}
		Object[] objs = { m1, m2, containAutomaticRefillData, containDisbursementData };
		return objs;
	}
}