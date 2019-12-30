package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.icu.util.Calendar;
import com.tcmis.client.catalog.beans.VvShelfLifeBasisBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.ItemAffectedNotification;
import com.tcmis.internal.catalog.beans.ItemConversionBean;
import com.tcmis.internal.catalog.beans.ItemConversionFactorBean;
import com.tcmis.internal.catalog.beans.MaterialAffectedNotification;
import com.tcmis.internal.catalog.beans.MfrAffectedNotification;
import com.tcmis.internal.catalog.beans.MfrNotificationCategoryBean;
import com.tcmis.internal.catalog.beans.MfrNotificationRequest;
import com.tcmis.internal.catalog.beans.MfrNotificationRequestWrapper;
import com.tcmis.internal.catalog.factory.MfrNotificationFactory;


public class MfrNotificationProcess extends GenericProcess {
	
	private static final String OUT_OF_BUSINESS_MSG = "OUT OF BUSINESS";
	private static final String OLD_FORMULATION_MSG = "Old Formulation";
	private static final String DISCONTINUED_MSG = "Discontinued";
	private static final String OBSOLETE_MSG = "OBSOLETE";
	private static final String OBSOLETE_SIZE_PKG_MSG = "OBSOLETE SIZE/PKG";

	public MfrNotificationProcess(String client) {
		super(client);
	}
	
	public BigDecimal insertNotificationRequest(MfrNotificationRequest input, PersonnelBean user) throws BaseException {
		BigDecimal notificationId = null;
		if (input.getNotificationId() == null) {
			notificationId = factory.getSequence("mfr_notification_seq");
		}
		else {
			notificationId = input.getNotificationId();
		}
		
		String insertStmt = "insert into mfr_notification_request"
				+ " (notification_id, inserted_by, date_inserted, status)"
				+ " values (" + notificationId
				+ ", " + user.getPersonnelIdBigDecimal()
				+ ", sysdate, 'Draft')";
		
		factory.deleteInsertUpdate(insertStmt);
		
		return notificationId;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MfrNotificationRequest> getNotificationRequest(BigDecimal notificationId) throws BaseException {
		String query = "select mnr.*, mnrc.mfr_req_category_id, mnrc.page_upload_code"
				+ " from mfr_notification_request mnr, mfr_notification_req_category mnrc"
				+ " where mnr.notification_id = " + notificationId.intValue()
				+ " and mnr.notification_id = mnrc.notification_id";
		return factory.setBean(new MfrNotificationRequest()).selectQuery(query);
	}
	
	public MfrNotificationRequest getNotificationRequest(BigDecimal notificationId, BigDecimal categoryId) throws BaseException {
		String query = "select mnr.*, mnrc.mfr_req_category_id from mfr_notification_request mnr, mfr_notification_req_category mnrc"
				+ " where mnrc.notification_id = " + notificationId.intValue()
				+ " and mnrc.mfr_req_category_id = " + categoryId.intValue()
				+ " and mnr.notification_id = mnrc.notification_id";
		
		@SuppressWarnings("unchecked")
		Collection<MfrNotificationRequest> notificationColl = factory.setBean(new MfrNotificationRequest()).selectQuery(query);
		Iterator<MfrNotificationRequest> notificationIterator = notificationColl.iterator();
		MfrNotificationRequest notification = null;
		while (notificationIterator.hasNext()) {
			notification = notificationIterator.next();
		}
		
		return notification;
	}
	
	public MfrNotificationCategoryBean getCategory(BigDecimal categoryId) throws BaseException {
		try {
			Connection connection = dbManager.getConnection();
			try {
				return getCategory(categoryId, connection);
			} finally {
				dbManager.returnConnection(connection);
			}
		}  catch(DbConnectionException e) {
			throw new BaseException(e);
		} catch(DbReturnConnectionException e) {
			throw new BaseException(e);
		}
	}
	
	private MfrNotificationCategoryBean getCategory(BigDecimal categoryId, Connection connection) throws BaseException {
		String query = "select mfr_req_category_id, mfr_req_category_desc"
				+ ", status, manufacturer_data, material_data, item_data, search_criteria"
				+ " from mfr_notification_category where mfr_req_category_id = "+categoryId;
		@SuppressWarnings("unchecked")
		Collection<MfrNotificationCategoryBean> categories = factory.setBean(new MfrNotificationCategoryBean()).selectQuery(query, connection);
		Iterator<MfrNotificationCategoryBean> notificationIterator = categories.iterator();
		MfrNotificationCategoryBean category = null;
		while (notificationIterator.hasNext()) {
			category = notificationIterator.next();
		}
		
		return category;
	}
	
	public void removeDeletedCategories(MfrNotificationRequest input) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			String selectedCategories = Arrays.stream(input.getSelectedCategories().split(",")).map(c -> c.substring("category".length())).collect(Collectors.joining(","));
			String baseStmt = " from mfr_notification_req_category where"
					+ " notification_id = " + input.getNotificationId()
					+ " and mfr_req_category_id not in (" + selectedCategories + ")";
			
			String deleteDetailStmt = "delete from mfr_notification_req_detail where"
					+ " notification_category_id in (select notification_category_id"
					+ baseStmt + ")";
			
			factory.deleteInsertUpdate(deleteDetailStmt, conn);
			
			String deleteStmt = "delete" + baseStmt;
			
			factory.deleteInsertUpdate(deleteStmt, conn);
		} catch(Exception e) {
			throw new BaseException(e.getMessage());
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public MfrNotificationRequestWrapper getRequestComponents(MfrNotificationRequest request) throws BaseException {
		try {
			Connection connection = dbManager.getConnection();
			try {
				return getRequestComponents(request, getCategory(request.getMfrReqCategoryId(), connection), connection);
			} finally {
				dbManager.returnConnection(connection);
			}
		}  catch(DbConnectionException e) {
			throw new BaseException(e);
		} catch(DbReturnConnectionException e) {
			throw new BaseException(e);
		}
	}
	
	public MfrNotificationRequestWrapper getRequestComponents(MfrNotificationRequest request, MfrNotificationCategoryBean category) throws BaseException {
		Connection connection = dbManager.getConnection();
		try {
			return getRequestComponents(request, category, connection);
		} finally {
			dbManager.returnConnection(connection);
		}
	}
	
	@SuppressWarnings("unchecked")
	private MfrNotificationRequestWrapper getRequestComponents(MfrNotificationRequest request, MfrNotificationCategoryBean category, Connection connection) throws BaseException {
		MfrNotificationFactory factory = new MfrNotificationFactory(dbManager);
		MfrNotificationRequestWrapper wrapper = new MfrNotificationRequestWrapper();
		// get current data
		try {
			if (request.getPageUploadCode() != null) {
				JSONObject object = new JSONObject(request.getPageUploadCode());
				if (object.has("mfr")) {
					MfrAffectedNotification mfr = (MfrAffectedNotification) BeanHandler.convertJsonToJavaObject(object.getJSONObject("mfr"), new MfrAffectedNotification(), Locale.getDefault(), "", false);
					wrapper.setMfr(mfr);
				}
				if (object.has("material")) {
					List<MaterialAffectedNotification> materials = new ArrayList<MaterialAffectedNotification>();
					JSONObject materialStore = object.getJSONObject("material");
					if (materialStore.has("category"+category.getMfrReqCategoryId().intValue()+"MaterialGrid")) {
						JSONArray materialArray = materialStore.getJSONObject("category"+category.getMfrReqCategoryId().intValue()+"MaterialGrid").getJSONArray("rows");
						for (int i = 0; i < materialArray.length(); i++) {
							if ( ! materialArray.isNull(i)) {
								materials.add((MaterialAffectedNotification) BeanHandler.convertJsonToJavaObject(materialArray.getJSONObject(i), new MaterialAffectedNotification(), Locale.getDefault(), "", false));
							}
						}
						wrapper.setMaterials(materials);
					}
				}
				if (object.has("item")) {
					List<ItemAffectedNotification> items = new ArrayList<ItemAffectedNotification>();
					JSONObject itemStore = object.getJSONObject("item");
					if (itemStore.has("category"+category.getMfrReqCategoryId().intValue()+"ItemGrid")) {
						JSONArray itemArray = itemStore.getJSONObject("category"+category.getMfrReqCategoryId().intValue()+"ItemGrid").getJSONArray("rows");
						for (int i = 0; i < itemArray.length(); i++) {
							if ( ! itemArray.isNull(i)) {
								items.add((ItemAffectedNotification) BeanHandler.convertJsonToJavaObject(itemArray.getJSONObject(i), new ItemAffectedNotification(), Locale.getDefault(), "", false));
							}
						}
						wrapper.setItems(items);
					}
				}
			}
			else if (request.isApproved()) {
				wrapper.setMfr(factory.getMfrAffectedNotification(request, connection));
			}
		} catch (JSONException e) {
			log.error(e);
		}
		
		// get stored revised data
		if (category.isManufacturerData()) {
			wrapper.setCurrentMfr(factory.getMfrAffectedNotification(request, connection));
			
			if (category.isMaterialData()) {
				if (category.isMfrAcquisition()) {
					BigDecimal acquiredMfr = null;
					if (wrapper.getMfr() != null && wrapper.getMfr().getAcquiredMfrId() != null) {
						acquiredMfr = wrapper.getMfr().getAcquiredMfrId();
					}
					else if (request.getAcquiredMfrId() != null) {
						acquiredMfr = request.getAcquiredMfrId();
					}
					
					if (request.isApproved()) {
						String detailMaterialQuery = "select m.material_id, m.material_desc, m.trade_name from"
								+ " mfr_notification_req_category c, mfr_notification_req_detail d, global.material m"
								+ " where c.notification_id = " + request.getNotificationId()
								+ " and c.mfr_req_category_id = " + category.getMfrReqCategoryId()
								+ " and c.notification_category_id = d.notification_category_id"
								+ " and m.acquired_mfr_id = c.acquired_mfr_id"
								+ " and d.mfg_id = m.mfg_id";
						Collection<MaterialAffectedNotification> materials = (Collection<MaterialAffectedNotification>)
								this.factory.setBean(new MaterialAffectedNotification()).selectQuery(detailMaterialQuery, connection);
						wrapper.setCurrentMaterials(materials);
					}
					else if (acquiredMfr != null) {
						wrapper.setCurrentMaterials(factory.getMaterialsByMfr(acquiredMfr, wrapper.getMaterials(), connection));
					}
				}
				else if (wrapper.getCurrentMfr() != null) {
					wrapper.setCurrentMaterials(factory.getMaterialsByMfr(wrapper.getCurrentMfr().getMfgId(), wrapper.getMaterials(), connection));
					if (category.isItemData() && request.getMaterialId() != null) {
						if (category.isMfrOutOfBusiness()) {
							String itemOnlyQuery = "select i.item_id, i.item_desc, fx_kit_packaging(i.item_id) packaging"
									+ ", p.material_id, m.mfg_id, fx_kit_packaging(i.item_id, p.material_id) pkg_style"
									+ " from global.item i, global.part p, global.material m"
									+ " where i.item_id = p.item_id"
									+ " and p.material_id = " + request.getMaterialId()
									+ " and p.material_id = m.material_id"
									+ " order by item_id";
							wrapper.setCurrentItems(this.factory.setBean(new ItemAffectedNotification()).selectQuery(itemOnlyQuery, connection));
						}
						else {
							wrapper.setCurrentItems(factory.getItemsByMaterial(request, category, wrapper.getItems(), connection));
						}
					}
				}
			}
		}
		else {
			if (request.isApproved()) {
				String commentsQuery = "select comments from mfr_notification_req_category mnrc"
						+ " where notification_id = " + request.getNotificationId()
						+ " and mfr_req_category_id = " + request.getMfrReqCategoryId();
				Optional<MfrAffectedNotification> mfr = this.factory.setBean(new MfrAffectedNotification()).selectQuery(commentsQuery,connection)
						.stream().findFirst();
				if (mfr.isPresent()) {
					if (wrapper.getMfr() == null) {
						wrapper.setMfr(mfr.get());
					}
					else {
						wrapper.getMfr().setComments(mfr.get().getComments());
					}
				}
			}
			
			if (category.isMaterialData()) {
				if (category.isMfrSearch()) {
					wrapper.setCurrentMaterials(factory.getMaterialsByMfr(request.getMfgId(), wrapper.getMaterials(), connection));
					Optional<BigDecimal> firstMaterial = wrapper.getCurrentMaterials().stream().map(m -> m.getMaterialId()).findFirst();
					if (firstMaterial.isPresent()) {
						request.setMaterialId(firstMaterial.get());
					}
				}
				else {
					wrapper.setCurrentMaterials(factory.getMaterialsByMaterialId(request, wrapper.getMaterials(), connection));
				}
				
				if (category.isItemData() && request.getMaterialId() != null) {
					wrapper.setCurrentItems(factory.getItemsByMaterial(request, category, wrapper.getItems(), connection));
				}
			}
			else if (category.isItemData()) {
				if (category.isMaterialSearch() && request.getMaterialId() != null) {
					wrapper.setCurrentItems(factory.getItemsByMaterial(request, category, wrapper.getItems(), connection));
				}
				else {
					wrapper.setCurrentItems(factory.getItemsByItemId(request, category, wrapper.getItems(), connection));
				}
			}
		}
		return wrapper;
	}
	
	public MfrNotificationRequestWrapper[] getRequestComponents(Collection<MfrNotificationRequest> input, Collection<MfrNotificationCategoryBean> categories) throws BaseException {
		MfrNotificationRequestWrapper[] components = new MfrNotificationRequestWrapper[categories.size()];
		int idx = 0;
		for (MfrNotificationCategoryBean category: categories) {
			if (category.isSelected()) {
				try {
					Connection connection = dbManager.getConnection();
					try {
						Optional<MfrNotificationRequest> notificationCategory = 
								input.stream().filter(n -> n.getMfrReqCategoryId().compareTo(category.getMfrReqCategoryId()) == 0).findFirst();
						if (notificationCategory.isPresent()) {
							MfrNotificationRequest request = notificationCategory.get();
							components[idx] = getRequestComponents(request, category, connection);
						}
					} finally {
						dbManager.returnConnection(connection);
					}
				} catch(DbConnectionException e) {
					throw new BaseException(e);
				} catch(DbReturnConnectionException e) {
					throw new BaseException(e);
				}
			}
			idx++;
		}
		return components;
	}
	
	public void submitRequest(MfrNotificationRequest input, Collection<MfrNotificationCategoryBean> categories, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			if ( ! isApproved(input, conn)) {
				for (MfrNotificationCategoryBean category : categories) {
					if (category.isObsolescence()) {
						checkItemConversionFactor(input, category, conn);
					}
				}
				if ( ! checkForDocuments(input, conn)) {
					ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
					throw new BaseException(library.getString("label.documentsrequired"));
				}
				saveRequest(input, user, conn);
				
				String submitStmt = "update mfr_notification_request"
						+ " set status = 'Pending Approval'"
						+ ", date_submitted = sysdate"
						+ ", internal_comments = " + SqlHandler.delimitString(input.getInternalComments())
						+ " where notification_id = " + input.getNotificationId();
				
				factory.deleteInsertUpdate(submitStmt, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private String getClob(String pageUploadCode) {
		StringBuilder clobBuilder = new StringBuilder();
		int start = 0;
		int end = 3000;
		while (end < pageUploadCode.length()) {
			clobBuilder.append("to_clob(").append(SqlHandler.delimitString(pageUploadCode.substring(start,end))).append(")||");
			start = end;
			end+=3000;
		}
		clobBuilder.append("to_clob(").append(SqlHandler.delimitString(pageUploadCode.substring(start, pageUploadCode.length()))).append(")");
		
		return clobBuilder.toString();
	}

	public void saveRequest(MfrNotificationRequest input, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			saveRequest(input, user, conn);
		} finally {
			dbManager.returnConnection(conn);
		}
	}

	@SuppressWarnings("rawtypes")
	public void saveRequest(MfrNotificationRequest input, PersonnelBean user, Connection conn) throws BaseException {
		try {
			if ( ! isApproved(input, conn)) {
				JSONObject jso = new JSONObject(input.getPageUploadCode());
				for (Iterator i=jso.keys();i.hasNext();) {
					String category = i.next().toString();
					int categoryId = new Integer(category.substring("category".length()));
					JSONObject categoryPageUploadCode = jso.getJSONObject(category);
					
					String externalComments = "";
					if (categoryPageUploadCode.has("mfr")) {
						JSONObject mfr = categoryPageUploadCode.optJSONObject("mfr");
						if (mfr.has("comments")) {
							externalComments = StringHandler.emptyIfNull(mfr.optString("comments"));
						}
					}
					
					String stmt = "update mfr_notification_req_category set page_upload_code="
							+ getClob(categoryPageUploadCode.toString())
							+ ", comments = " + SqlHandler.delimitString(externalComments)
							+ " where notification_id = " + input.getNotificationId()
							+ " and mfr_req_category_id = "+categoryId;
					
					int count = factory.deleteInsertUpdate(stmt, conn);
					
					if (count == 0) {
						stmt = "insert into mfr_notification_req_category ("
								+ "notification_category_id, notification_id, mfr_req_category_id, page_upload_code)"
								+ " values ("
								+ "mfr_notification_seq.nextval"
								+ ", " + input.getNotificationId()
								+ ", " + categoryId
								+ ", " + getClob(categoryPageUploadCode.toString()) + ")";
						
						factory.deleteInsertUpdate(stmt, conn);
					}
				}
				
				updateInternalComments(input, conn);
			}
		} catch(JSONException e) {
			throw new BaseException(e.getMessage());
		}
	}
	
	private String getNotificationCategoryId(MfrNotificationRequest input, MfrNotificationCategoryBean category, Connection conn) throws BaseException {
		String notificationCategoryIdQuery = "select notification_category_id from mfr_notification_req_category"
				+ " where notification_id = " + input.getNotificationId()
				+ " and mfr_req_category_id = " + category.getMfrReqCategoryId();
		
		return factory.selectSingleValue(notificationCategoryIdQuery, conn);
	}
	
	private void lockInMaterialUpdates(MaterialAffectedNotification material, MfrNotificationCategoryBean category, MfrNotificationRequest request, Connection conn) throws BaseException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String notificationCategoryId = getNotificationCategoryId(request, category, conn);
		String insertStmt = "insert into mfr_notification_req_detail"
				+ " (notification_detail_id, notification_category_id, material_id)"
				+ " select mfr_notification_seq.nextval"
				+ ", " + notificationCategoryId
				+ ", " + material.getMaterialId()
				+ " from dual where not exists("
				+ "select 1 from mfr_notification_req_detail"
				+ " where notification_category_id = " + notificationCategoryId
				+ " and material_id = " + material.getMaterialId() + ")";
		
		factory.deleteInsertUpdate(insertStmt, conn);
		
		if ( ! category.isRawMaterialChange()) {
			StringBuilder materialUpdate = new StringBuilder("update global.material set");
			if (category.isRebrandedProduct() || category.isMfrProductCodeChange() || category.isSpecChange()) {
				materialUpdate.append(" material_desc = ");
				if (StringHandler.isBlankString(material.getMaterialDesc())) {
					materialUpdate.append("material_desc");
				}
				else {
					materialUpdate.append(SqlHandler.delimitString(material.getMaterialDesc()));
				}
				if ( ! StringHandler.isBlankString(material.getTradeName())) {
					materialUpdate.append(", trade_name = ").append(SqlHandler.delimitString(material.getTradeName()));
				}
				if (category.isMfrProductCodeChange()) {
					if ( ! StringHandler.isBlankString(material.getProductCode()))
						materialUpdate.append(", product_code = ").append(SqlHandler.delimitString(material.getProductCode()));
				}
			}
			else {
				if ( ! StringHandler.isBlankString(material.getMaterialDesc())) {
					materialUpdate.append(" material_desc_extension = ").append(SqlHandler.delimitString(material.getMaterialDesc()));
				}
				else {
					materialUpdate.append(" material_desc_extension = ");
					if (category.isFormulationChange()) {
						materialUpdate.append("'(").append(OLD_FORMULATION_MSG).append(" ").append(year).append(")'");
					}
					else if (category.isMaterialDiscontinuation()) {
						materialUpdate.append("'(").append(DISCONTINUED_MSG).append(" ").append(year).append(")'");
					}
					else {
						materialUpdate.append("''");
					}
				}
				if ( ! StringHandler.isBlankString(material.getTradeName())) {
					materialUpdate.append(", trade_name_extension = ").append(SqlHandler.delimitString(material.getTradeName()));
				}
				else if (category.isFormulationChange() || category.isMaterialDiscontinuation()){
					materialUpdate.append(", trade_name_extension = ");
					if (category.isFormulationChange()) {
						materialUpdate.append("'(").append(OLD_FORMULATION_MSG).append(" ").append(year).append(")'");
					}
					else if (category.isMaterialDiscontinuation()) {
						materialUpdate.append("'(").append(DISCONTINUED_MSG).append(" ").append(year).append(")'");
					}
					else {
						materialUpdate.append("''");
					}
				}
			}
			materialUpdate.append(" where material_id = ").append(material.getMaterialId());
			
			factory.deleteInsertUpdate(materialUpdate.toString(), conn);
		}
	}
	
	private void lockInItemUpdates(ItemAffectedNotification item, MfrNotificationCategoryBean category, PersonnelBean user, MfrNotificationRequest request, Connection conn) throws BaseException {
		if ( ! (item.getItemId() == null || category.isManufacturerData() || category.isMaterialData())) {
			String notificationCategoryId = getNotificationCategoryId(request, category, conn);
			String insertStmt = "insert into mfr_notification_req_detail"
					+ " (notification_detail_id, notification_category_id, item_id)"
					+ " select mfr_notification_seq.nextval"
					+ ", " + notificationCategoryId
					+ ", " + item.getItemId()
					+ " from dual where not exists("
					+ "select 1 from mfr_notification_req_detail"
					+ " where notification_category_id = " + notificationCategoryId
					+ " and item_id = " + item.getItemId() + ")";
			
			factory.deleteInsertUpdate(insertStmt, conn);
		}
		
		if ( ! category.isProductFYINotice()) {
			boolean itemTypeUpdate = false;
			StringBuilder itemUpdate = new StringBuilder("update global.item set");
			if (item.getRevisionComments() != null && item.getRevisionComments().trim().length() > 0) {
				itemUpdate.append(" revision_comments = substr(rtrim(");
				itemUpdate.append("'").append(category.getMfrReqCategoryDesc()).append(" ").append(request.getNotificationId()).append(", ");
				itemUpdate.append(SqlHandler.validQuery((item.getRevisionComments())));
				DateFormat df = new SimpleDateFormat("ddMMMyyyy");
				
				itemUpdate.append(", ' || (select fx_personnel_id_to_name(inserted_by) from mfr_notification_request");
				itemUpdate.append(" where notification_id = ").append(request.getNotificationId()).append(") || ' ");
				itemUpdate.append(df.format(new Date())).append("; '");
				itemUpdate.append(" || revision_comments),1,400)");
			}
			else {
				itemUpdate.append(" revision_comments = revision_comments");
			}
			if (category.isItemDiscontinuation() || category.isMaterialDiscontinuation() || category.isFormulationChange()) {
				if ( ! StringHandler.isBlankString(item.getStockingType())) {
					itemUpdate.append(", stocking_type = ").append(SqlHandler.delimitString(item.getStockingType()));
				}
				else if (category.isFormulationChange() || category.isMaterialDiscontinuation() || category.isItemDiscontinuation()) {
					itemUpdate.append(", stocking_type = 'O'");
				}
				if ( ! StringHandler.isBlankString(item.getItemType())) {
					itemUpdate.append(", item_type = ").append(SqlHandler.delimitString(item.getItemType()));
					itemTypeUpdate = true;
				}
				else if (category.isFormulationChange() || category.isMaterialDiscontinuation() || category.isItemDiscontinuation()) {
					itemUpdate.append(", item_type = 'OB'");
					item.setItemType("OB");
					itemTypeUpdate = true;
				}
			}
			itemUpdate.append(" where");
			if (item.getItemId() != null) {
				itemUpdate.append(" item_id = ").append(item.getItemId());
			}
			else {
				itemUpdate.append(" item_id in (select item_id from global.part");
				itemUpdate.append(" where material_id = ").append(item.getMaterialId()).append(")");
			}

			factory.deleteInsertUpdate(itemUpdate.toString(), conn);
		}
	}
	
	private void lockInPartUpdates(ItemAffectedNotification item, MfrNotificationCategoryBean category, PersonnelBean user, Connection conn) throws BaseException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if ( ! category.isProductFYINotice()) {
			StringBuilder partUpdate = new StringBuilder("update global.part set");
			if (category.isMfrProductCodeChange() || category.isSpecChange()) {
				partUpdate.append(" mfg_part_no = ");
				if ( ! StringHandler.isBlankString(item.getMfgPartNo())) {
					partUpdate.append(SqlHandler.delimitString(item.getMfgPartNo()));
				}
				else {
					partUpdate.append("mfg_part_no");
				}
			}
			else {
				if ( ! StringHandler.isBlankString(item.getMfgPartNo())) {
					partUpdate.append(" mfg_part_no_extension = ").append(SqlHandler.delimitString(item.getMfgPartNo()));
				}
				else {
					partUpdate.append(" mfg_part_no_extension = ");
					if (category.isFormulationChange() || category.isMaterialDiscontinuation()) {
						partUpdate.append("'").append(OBSOLETE_MSG).append(" (").append(year).append(")'");
					}
					else if (category.isItemDiscontinuation()) {
						partUpdate.append("'").append(OBSOLETE_SIZE_PKG_MSG).append(" (").append(year).append(")'");
					}
					else {
						partUpdate.append("''");
					}
				}
			}
			if (category.isItemDiscontinuation() || category.isMaterialDiscontinuation() || category.isFormulationChange()) {
				if ( ! StringHandler.isBlankString(item.getStockingType())) {
					partUpdate.append(", stocking_type = ").append(SqlHandler.delimitString(item.getStockingType()));
				}
				else if (category.isFormulationChange() || category.isMaterialDiscontinuation() || category.isItemDiscontinuation()) {
					partUpdate.append(", stocking_type = 'O'");
				}
			}
			else if (category.isSpecChange()) {
				if ( ! StringHandler.isBlankString(item.getGrade()))
					partUpdate.append(", grade = ").append(SqlHandler.delimitString(item.getGrade()));
			}
			else if (category.isSLSTUpdate()) {
				if (item.getShelfLifeDays() != null) 
					partUpdate.append(", shelf_life_days = ").append(item.getShelfLifeDays());
				if ( ! StringHandler.isBlankString(item.getShelfLifeBasis()))
					partUpdate.append(", shelf_life_basis = ").append(SqlHandler.delimitString(item.getShelfLifeBasis()));
				
				
				boolean tempChanged = false;
				boolean tempUnitsChanged = false;
				if ( ! (item.getMinTemp() == null && item.getMaxTemp() == null)) {
					tempChanged = true;
					if (item.getMinTemp() != null)
						partUpdate.append(", min_temp = ").append(item.getMinTemp());
					else 
						partUpdate.append(", min_temp = null");
					
					if (item.getMaxTemp() != null) 
						partUpdate.append(", max_temp = ").append(item.getMaxTemp());
					else 
						partUpdate.append(", max_temp = null");
				}
				if ( ! StringHandler.isBlankString(item.getTempUnits())) {
					partUpdate.append(", temp_units = ").append(SqlHandler.delimitString(item.getTempUnits()));
					tempUnitsChanged = true;
				}
				
				if (tempChanged || tempUnitsChanged) {
					if ( ! tempChanged) {
						// if both min temp and max temp from the page are blank, don't update if min_temp and max_temp are NULL
						// in other words, don't verify temperature if there are no temperature values
						partUpdate.append(", temp_verified = nvl2(nvl(min_temp, max_temp), decode(temp_units, ").append(SqlHandler.delimitString(item.getTempUnits())).append(", temp_verified, 'Y'), temp_verified)");
						partUpdate.append(", temp_verified_by = nvl2(nvl(min_temp, max_temp), decode(temp_units, ").append(SqlHandler.delimitString(item.getTempUnits())).append(", temp_verified_by, ").append(user.getPersonnelId()).append("), temp_verified_by)");
						partUpdate.append(", date_temp_verified = nvl2(nvl(min_temp, max_temp), decode(temp_units, ").append(SqlHandler.delimitString(item.getTempUnits())).append(", date_temp_verified, sysdate), date_temp_verified)");
					}
					else {
						// don't update temp_verified, etc. if the values are exactly the same as they were before, which would be a non-update
						partUpdate.append(", temp_verified = ")
								.append("decode(min_temp, ").append(item.getMinTemp())
								.append(", decode(max_temp, ").append(item.getMaxTemp())
								.append(", decode(temp_units, ").append(SqlHandler.delimitString(item.getTempUnits()))
								.append(", temp_verified, 'Y'), 'Y'), 'Y')");
						partUpdate.append(", temp_verified_by = ")
								.append("decode(min_temp, ").append(item.getMinTemp())
								.append(", decode(max_temp, ").append(item.getMaxTemp())
								.append(", decode(temp_units, ").append(SqlHandler.delimitString(item.getTempUnits()))
								.append(", temp_verified_by, ").append(user.getPersonnelId()).append("), ")
								.append(user.getPersonnelId()).append("), ").append(user.getPersonnelId()).append(")");
						partUpdate.append(", date_temp_verified = ")
								.append("decode(min_temp, ").append(item.getMinTemp())
								.append(", decode(max_temp, ").append(item.getMaxTemp())
								.append(", decode(temp_units, ").append(SqlHandler.delimitString(item.getTempUnits()))
								.append(", date_temp_verified, sysdate), sysdate), sysdate)");
					}
				}
			}
			partUpdate.append(" where");
			if (item.getItemId() != null) {
				partUpdate.append(" item_id = ").append(item.getItemId()).append(" and");
			}
			partUpdate.append(" material_id = ").append(item.getMaterialId());
			
			factory.deleteInsertUpdate(partUpdate.toString(), conn);
		}
	}
	
	private void lockInPartLocaleUpdates(ItemAffectedNotification item,  
			MfrNotificationCategoryBean category, PersonnelBean user, Connection conn) throws BaseException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		StringBuilder partUpdate = new StringBuilder("update global.part_locale set");
		if (category.isMfrProductCodeChange() || category.isSpecChange()) {
			partUpdate.append(" mfg_part_no = ");
			if ( ! StringHandler.isBlankString(item.getMfgPartNo())) {
				partUpdate.append(SqlHandler.delimitString(item.getMfgPartNo()));
			}
			else {
				partUpdate.append("mfg_part_no");
			}
		}
		else {
			if ( ! StringHandler.isBlankString(item.getLocaleMfgPartNo())) {
				partUpdate.append(" mfg_part_no_extension = ").append(SqlHandler.delimitString(item.getLocaleMfgPartNo()));
			}
			else {
				partUpdate.append(" mfg_part_no_extension = ");
				if (category.isFormulationChange() || category.isMaterialDiscontinuation()) {
					partUpdate.append("'").append(OBSOLETE_MSG).append(" (").append(year).append(")'");
				}
				else if (category.isItemDiscontinuation()) {
					partUpdate.append("'").append(OBSOLETE_SIZE_PKG_MSG).append(" (").append(year).append(")'");
				}
				else {
					partUpdate.append("''");
				}
			}
		}
		if (category.isSpecChange()) {
			if ( ! StringHandler.isBlankString(item.getLocaleGrade()))
				partUpdate.append(", grade = ").append(SqlHandler.delimitString(item.getLocaleGrade()));
		}
		else if (category.isSLSTUpdate()) {
			boolean tempChanged = false;
			boolean tempUnitsChanged = false;
			if ( ! (item.getLocaleMinTemp() == null && item.getLocaleMaxTemp() == null)) {
				tempChanged = true;
				if (item.getLocaleMinTemp() != null)
					partUpdate.append(", min_temp = ").append(item.getLocaleMinTemp());
				else 
					partUpdate.append(", min_temp = null");
				
				if (item.getLocaleMaxTemp() != null) 
					partUpdate.append(", max_temp = ").append(item.getLocaleMaxTemp());
				else 
					partUpdate.append(", max_temp = null");
			}
			if ( ! StringHandler.isBlankString(item.getLocaleTempUnits())) {
				partUpdate.append(", temp_units = ").append(SqlHandler.delimitString(item.getLocaleTempUnits()));
				tempUnitsChanged = true;
			}
			
			if (tempChanged || tempUnitsChanged) {
				if ( ! tempChanged) {
					// if both min temp and max temp from the page are blank, don't update if min_temp and max_temp are NULL
					// in other words, don't verify temperature if there are no temperature values
					partUpdate.append(", temp_verified = nvl2(nvl(min_temp, max_temp), decode(temp_units, ").append(SqlHandler.delimitString(item.getLocaleTempUnits())).append(", temp_verified, 'Y'), temp_verified)");
					partUpdate.append(", temp_verified_by = nvl2(nvl(min_temp, max_temp), decode(temp_units, ").append(SqlHandler.delimitString(item.getLocaleTempUnits())).append(", temp_verified_by, ").append(user.getPersonnelId()).append("), temp_verified_by)");
					partUpdate.append(", date_temp_verified = nvl2(nvl(min_temp, max_temp), decode(temp_units, ").append(SqlHandler.delimitString(item.getLocaleTempUnits())).append(", date_temp_verified, sysdate), date_temp_verified)");
				}
				else {
					// don't update temp_verified, etc. if the values are exactly the same as they were before, which would be a non-update
					partUpdate.append(", temp_verified = ")
							.append("decode(min_temp, ").append(item.getLocaleMinTemp())
							.append(", decode(max_temp, ").append(item.getLocaleMaxTemp())
							.append(", decode(temp_units, ").append(SqlHandler.delimitString(item.getLocaleTempUnits()))
							.append(", temp_verified, 'Y'), 'Y'), 'Y')");
					partUpdate.append(", temp_verified_by = ")
							.append("decode(min_temp, ").append(item.getLocaleMinTemp())
							.append(", decode(max_temp, ").append(item.getLocaleMaxTemp())
							.append(", decode(temp_units, ").append(SqlHandler.delimitString(item.getLocaleTempUnits()))
							.append(", temp_verified_by, ").append(user.getPersonnelId()).append("), ")
							.append(user.getPersonnelId()).append("), ").append(user.getPersonnelId()).append(")");
					partUpdate.append(", date_temp_verified = ")
							.append("decode(min_temp, ").append(item.getLocaleMinTemp())
							.append(", decode(max_temp, ").append(item.getLocaleMaxTemp())
							.append(", decode(temp_units, ").append(SqlHandler.delimitString(item.getLocaleTempUnits()))
							.append(", date_temp_verified, sysdate), sysdate), sysdate)");
				}
			}
		}
		partUpdate.append(" where");
		if (item.getItemId() != null) {
			partUpdate.append(" item_id = ").append(item.getItemId());
			partUpdate.append(" and part_id = ").append(item.getPartId());
			partUpdate.append(" and locale_code = ").append(SqlHandler.delimitString(item.getLocaleCode()));
		}
		else {
			partUpdate.append(" (part_id, item_id) in (select part_id, item_id from global.part");
			partUpdate.append(" where material_id = ").append(item.getMaterialId()).append(")");
		}
		
		factory.deleteInsertUpdate(partUpdate.toString(), conn);
	}
	
	public void updateItemDataByMaterial(MfrNotificationCategoryBean category, MaterialAffectedNotification material, 
			MfrNotificationRequest input, PersonnelBean user, Connection conn) throws JSONException, BaseException {
		if ( ! category.isRebrandedProduct()) {
			if (StringHandler.isBlankString(material.getPageUploadCode())) {
				ItemAffectedNotification item = new ItemAffectedNotification();
				item.setMaterialId(material.getMaterialId());
				lockInPartLocaleUpdates(item, category, user, conn);
				lockInPartUpdates(item, category, user, conn);
				
				try {
					conn.commit();
				} catch(SQLException e) {
					throw new BaseException(e.getMessage());
				}
				
				lockInItemUpdates(item, category, user, input, conn);
			}
			else {
				JSONObject object = new JSONObject(material.getPageUploadCode());
				JSONObject itemStore = object.getJSONObject("item");
				if (itemStore.has("category"+category.getMfrReqCategoryId().intValue()+"ItemGrid")) {
					JSONArray itemArray = itemStore.getJSONObject("category"+category.getMfrReqCategoryId().intValue()+"ItemGrid").getJSONArray("rows");
					if (itemArray.length() > 0) {
						ItemAffectedNotification previousItem = null;
						for (int i = 0; i < itemArray.length(); i++) {
							ItemAffectedNotification item = (ItemAffectedNotification) BeanHandler.convertJsonToJavaObject(itemArray.getJSONObject(i), new ItemAffectedNotification(), Locale.getDefault(), "", false);
							if (previousItem != null) {
								if (item.getItemId().compareTo(previousItem.getItemId()) != 0) {
									lockInPartUpdates(previousItem, category, user, conn);
									
									try {
										conn.commit();
									} catch(SQLException e) {
										throw new BaseException(e.getMessage());
									}
									
									lockInItemUpdates(previousItem, category, user, input, conn);
								}
								else if (item.getMaterialId().compareTo(previousItem.getMaterialId()) != 0) {
									lockInPartUpdates(previousItem, category, user, conn);
								}
							}
							
							if ( ! StringHandler.isBlankString(item.getLocaleCode())) {
								lockInPartLocaleUpdates(item, category, user, conn);
							}
							
							previousItem = item;
						}
	
						lockInPartUpdates(previousItem, category, user, conn);
						
						try {
							conn.commit();
						} catch(SQLException e) {
							throw new BaseException(e.getMessage());
						}
						
						lockInItemUpdates(previousItem, category, user, input, conn);
					}
				}
			}
		}
	}
	
	private boolean checkForDocuments(MfrNotificationRequest input, Connection conn) throws BaseException {
		boolean hasDocuments = true;
		String query = "select count(doc.document_id) document_count"
				+ " from mfr_notification_req_detail d, mfr_notification_req_category c, document doc"
				+ " where c.notification_id = " + input.getNotificationId()
				+ " and c.notification_category_id = d.notification_category_id(+) and d.document_id(+) is not null"
				+ " and d.document_id = doc.document_id(+) and doc.status(+) <> 'I'";
		
		String lowestDocCount = factory.selectSingleValue(query, conn);
		if (lowestDocCount.equals("0")) {
			hasDocuments = false;
		}
		
		return hasDocuments;
	}
	
	private boolean isApproved(MfrNotificationRequest input, Connection conn) throws BaseException {
		String approveCheck = "select count(*) from mfr_notification_request where qc_date is not null and notification_id = "
				+ input.getNotificationId();
		
		String approved = factory.selectSingleValue(approveCheck, conn);
		return ! approved.equals("0");
	}
	
	public void approveRequest(MfrNotificationRequest input, PersonnelBean user,
			Collection<MfrNotificationCategoryBean> categories, 
			Function<String, MfrAffectedNotification> mfrSupplier,
			Function<String, Collection<MaterialAffectedNotification>> materialSupplier, 
			Function<String, Collection<ItemAffectedNotification>> itemSupplier) throws BaseException {
		
		Connection conn = dbManager.getConnection();
		try {
			if ( ! checkForDocuments(input, conn)) {
				ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
				throw new BaseException(library.getString("label.documentsrequired"));
			}
			
			if ( ! isApproved(input, conn)) {
				conn.setAutoCommit(false);
				for (MfrNotificationCategoryBean category : categories) {
					if (category.isSelected()) {
						if (category.isObsolescence()) {
							checkItemConversionFactor(input, category, conn);
						}
						String categoryName = "category"+category.getMfrReqCategoryId();
						
						this.approveRequest(mfrSupplier.apply(categoryName), 
								materialSupplier.apply(categoryName), 
								itemSupplier.apply(categoryName), 
								input, category, user, conn);
					}
				}
				
				String requestStmt = "update mfr_notification_request set"
						+ " status = 'Approved'"
						+ ", qc_user = " + user.getPersonnelIdBigDecimal()
						+ ", qc_date = sysdate"
						+ ", internal_comments = " + SqlHandler.delimitString(input.getInternalComments())
						+ " where notification_id = " + input.getNotificationId()
						+ " and date_submitted is not null";
			
				factory.deleteInsertUpdate(requestStmt, conn);
				conn.commit();
			}
		} catch(Exception e) {
			try {
				conn.rollback();
				throw new BaseException(e.getMessage());
			} catch (SQLException e1) {
				throw new BaseException(e1);
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new BaseException(e);
			} finally {
				dbManager.returnConnection(conn);
			}
		}
	}
	
	private  void approveRequest(MfrAffectedNotification mfr, Collection<MaterialAffectedNotification> materials, Collection<ItemAffectedNotification> items,
			MfrNotificationRequest input, MfrNotificationCategoryBean category, PersonnelBean user, Connection conn) throws BaseException, JSONException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (category.isManufacturerData()) {
			StringBuilder updateStmt = new StringBuilder("update global.manufacturer set");
			if ( ! StringHandler.isBlankString(mfr.getMfgDesc())) {
				updateStmt.append(" mfg_desc = rtrim(mfg_desc || ' ' || ").append(SqlHandler.delimitString(mfr.getMfgDesc())).append(")");
			}
			else {
				updateStmt.append(" mfg_desc = mfg_desc");
			}
			if ( ! StringHandler.isBlankString(mfr.getNotes())) {
				updateStmt.append(", notes = rtrim(").append(SqlHandler.delimitString(mfr.getNotes())).append(" || '; ' || notes)");
			}
			if (category.isMfrAcquisition() || category.isMfrLocationChange()) {
				if ( ! StringHandler.isBlankString(mfr.getMfgUrl())) 
					updateStmt.append(", mfg_url = ").append(SqlHandler.delimitString(mfr.getMfgUrl()));
				if ( ! StringHandler.isBlankString(mfr.getPhone()))
					updateStmt.append(", phone = ").append(SqlHandler.delimitString(mfr.getPhone()));
				if ( ! StringHandler.isBlankString(mfr.getContact()))
					updateStmt.append(", contact = ").append(SqlHandler.delimitString(mfr.getContact()));
				if ( ! StringHandler.isBlankString(mfr.getEmail()))
					updateStmt.append(", email = ").append(SqlHandler.delimitString(mfr.getEmail()));
				if ( ! StringHandler.isBlankString(mfr.getCageCode()))
					updateStmt.append(", cage_code = ").append(SqlHandler.delimitString(mfr.getCageCode()));
			
				if (category.isMfrLocationChange()) {
					String categoryUpdate = "update mfr_notification_req_category set equipment_change = "
							+ (mfr.isEquipmentChange()?"'Y'":"'N'")
							+ " where notification_id = " + input.getNotificationId()
							+ " and mfr_req_category_id = " + category.getMfrReqCategoryId();
					
					factory.deleteInsertUpdate(categoryUpdate, conn);
				}
			
				if (category.isMfrAcquisition()) {
					String categoryUpdate = "update mfr_notification_req_category set acquired_mfr_id = "
							+ mfr.getAcquiredMfrId()
							+ " where notification_id = " + input.getNotificationId()
							+ " and mfr_req_category_id = " + category.getMfrReqCategoryId();
					
					factory.deleteInsertUpdate(categoryUpdate, conn);
					
					boolean materialGrabbed = false;
					StringBuilder materialList = new StringBuilder();
					for (MaterialAffectedNotification material : materials) {
						if ( ! mfr.isDivisionAcquisition() || material.isGrab()) {
							materialGrabbed = true;
							
							if (materialList.length() > 0) {
								materialList.append(",");
							}
							materialList.append(material.getMaterialId());
						}
					}
					if (materialGrabbed) {
						String materialUpdate = "update global.material set mfg_id = " + mfr.getMfgId()
								+ ", acquired_mfr_id = " + mfr.getAcquiredMfrId()
								+ " where material_id in (" + materialList.toString() + ")";
						
						factory.deleteInsertUpdate(materialUpdate, conn);
					}
					
					if ( ! mfr.isDivisionAcquisition()) {
						String acquiredMfrUpdate = "update global.manufacturer"
								+ " set mfg_desc = mfg_desc || ' (Acquired Mfg - DO NOT USE)'"
								+ " where mfg_id = " + mfr.getAcquiredMfrId();
					
						factory.deleteInsertUpdate(acquiredMfrUpdate, conn);
					}
				}
			}
			else if (category.isMfrOutOfBusiness()) {
				String materialUpdate = "update global.material set material_desc_extension = "
						+ "'" + OUT_OF_BUSINESS_MSG + " (" + year + ")'"
						+ ", trade_name_extension = "
						+ "'" + OUT_OF_BUSINESS_MSG + " (" + year + ")'"
						+ " where mfg_id = " + mfr.getMfgId();
				
				factory.deleteInsertUpdate(materialUpdate, conn);
				
				String materialLocaleUpdate = "update global.material_locale set material_desc_extension = "
						+ "'" + OUT_OF_BUSINESS_MSG + " (" + year + ")'"
						+ ", trade_name_extension = "
						+ "'" + OUT_OF_BUSINESS_MSG + " (" + year + ")'"
						+ " where material_id in (select material_id from global.material"
						+ " where mfg_id = " + mfr.getMfgId() + ")";
				
				factory.deleteInsertUpdate(materialLocaleUpdate, conn);
				
				String partLocaleUpdate = "update global.part_locale set mfg_part_no_extension = "
						+ "'" + OBSOLETE_MSG + " (" + year + ")'"
						+ " where (part_id, item_id) in (select part_id, item_id from global.part"
						+ " where material_id in (select material_id from global.material"
						+ " where mfg_id = " + mfr.getMfgId() + "))";
				
				factory.deleteInsertUpdate(partLocaleUpdate, conn);
				
				String partUpdate = "update global.part set mfg_part_no_extension = "
						+ "'" + OBSOLETE_MSG + " (" + year + ")'"
						+ ", stocking_type = 'O'"
						+ " where material_id in (select material_id from global.material"
						+ " where mfg_id = " + mfr.getMfgId() + ")";
				
				factory.deleteInsertUpdate(partUpdate, conn);
				
				try {
					conn.commit();
				} catch(SQLException e) {
					throw new BaseException(e.getMessage());
				}
				
				String itemUpdate = "update global.item set stocking_type = 'O'"
						+ ", item_type = 'OB'"
						+ " where item_id in (select item_id from global.part"
						+ " where material_id in (select material_id from global.material"
						+ " where mfg_id = " + mfr.getMfgId() + "))";
				
				
				ItemAffectedNotification item = new ItemAffectedNotification();
				item.setItemType("OB");
				factory.deleteInsertUpdate(itemUpdate, conn);
			}
			updateStmt.append(" where mfg_id = ").append(mfr.getMfgId());
			
			factory.deleteInsertUpdate(updateStmt.toString(), conn);
		}
		else if (category.isMaterialData() && materials != null) {
			MaterialAffectedNotification previousMaterial = null;
			items = new ArrayList<ItemAffectedNotification>();
			for (MaterialAffectedNotification material : materials) {
				if ( ! (previousMaterial == null || material.getMaterialId().compareTo(previousMaterial.getMaterialId()) == 0)) {
					lockInMaterialUpdates(previousMaterial, category, input, conn);

					updateItemDataByMaterial(category, previousMaterial, input, user, conn);
				}
				
				if ( ! StringHandler.isBlankString(material.getLocaleCode())) {
					StringBuilder materialUpdate = new StringBuilder("update global.material_locale set");
					if (category.isRebrandedProduct() || category.isMfrProductCodeChange() || category.isSpecChange()) {
						materialUpdate.append(" material_desc = ");
						if (StringHandler.isBlankString(material.getLocaleMaterialDesc())) {
							materialUpdate.append("material_desc");
						}
						else {
							materialUpdate.append(SqlHandler.delimitString(material.getLocaleMaterialDesc()));
						}
						if ( ! StringHandler.isBlankString(material.getLocaleTradeName())) {
							materialUpdate.append(", trade_name = ").append(SqlHandler.delimitString(material.getLocaleTradeName()));
						}
					}
					else {
						if ( ! StringHandler.isBlankString(material.getLocaleMaterialDesc())) {
							materialUpdate.append(" material_desc_extension = ").append(SqlHandler.delimitString(material.getLocaleMaterialDesc()));
						}
						else {
							materialUpdate.append(" material_desc_extension = ");
							if (category.isFormulationChange()) {
								materialUpdate.append("'(").append(OLD_FORMULATION_MSG).append(" ").append(year).append(")'");
							}
							else if (category.isMaterialDiscontinuation()) {
								materialUpdate.append("'(").append(DISCONTINUED_MSG).append(" ").append(year).append(")'");
							}
							else {
								materialUpdate.append("''");
							}
						}
						if ( ! StringHandler.isBlankString(material.getLocaleTradeName())) {
							materialUpdate.append(", trade_name_extension = ").append(SqlHandler.delimitString(material.getLocaleTradeName()));
						}
						else if (category.isFormulationChange() || category.isMaterialDiscontinuation()){
							materialUpdate.append(", trade_name_extension = ");
							if (category.isFormulationChange()) {
								materialUpdate.append("'(").append(OLD_FORMULATION_MSG).append(" ").append(year).append(")'");
							}
							else if (category.isMaterialDiscontinuation()) {
								materialUpdate.append("'(").append(DISCONTINUED_MSG).append(" ").append(year).append(")'");
							}
							else {
								materialUpdate.append("''");
							}
						}
					}
					materialUpdate.append(" where material_id = ").append(material.getMaterialId());
					materialUpdate.append(" and locale_code = ").append(SqlHandler.delimitString(material.getLocaleCode()));
					
					factory.deleteInsertUpdate(materialUpdate.toString(), conn);
				}
				previousMaterial = material;
			}
			lockInMaterialUpdates(previousMaterial, category, input, conn);
			updateItemDataByMaterial(category, previousMaterial, input, user, conn);
		}
		else if (category.isItemData() && items != null) {
			if ( ! category.isProductFYINotice()) {
				ItemAffectedNotification previousItem = null;
				for (ItemAffectedNotification item : items) {
					if (previousItem != null) {
						if (item.getItemId().compareTo(previousItem.getItemId()) != 0) {
							lockInPartUpdates(previousItem, category, user, conn);
							
							try {
								conn.commit();
							} catch(SQLException e) {
								throw new BaseException(e.getMessage());
							}
							
							lockInItemUpdates(previousItem, category, user, input, conn);
						}
						else if (item.getMaterialId().compareTo(previousItem.getMaterialId()) != 0) {
							lockInPartUpdates(previousItem, category, user, conn);
						}
					}
					
					if ( ! StringHandler.isBlankString(item.getLocaleCode())) {
						lockInPartLocaleUpdates(item, category, user, conn);
					}
					previousItem = item;
				}
				lockInPartUpdates(previousItem, category, user, conn);
				
				try {
					conn.commit();
				} catch(SQLException e) {
					throw new BaseException(e.getMessage());
				}
				
				lockInItemUpdates(previousItem, category, user, input, conn);
			}
		}
		
		StringBuilder categoryUpdate = new StringBuilder("update mfr_notification_req_category set ");
		categoryUpdate.append("page_upload_code = to_clob('')");
		if (mfr.getComments() != null && mfr.getComments().trim().length() > 0) {
			categoryUpdate.append(", comments = ").append(SqlHandler.delimitString(mfr.getComments()));
		}
		categoryUpdate.append(" where notification_id = ").append(input.getNotificationId());
		categoryUpdate.append(" and mfr_req_category_id = ").append(category.getMfrReqCategoryId());
		
		factory.deleteInsertUpdate(categoryUpdate.toString(), conn);
	}
	
	public void rejectRequest(MfrNotificationRequest input, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			String updateStmt = "update mfr_notification_request set status = 'Rejected'"
					+ ", qc_date = sysdate"
					+ ", qc_user = " + user.getPersonnelId()
					+ ", internal_comments = " + SqlHandler.delimitString(input.getInternalComments())
					+ " where notification_id = " + input.getNotificationId();
			
			factory.deleteInsertUpdate(updateStmt, conn);
			
			String requesterEmailQuery = "select fx_personnel_id_to_email(inserted_by)"
					+ " from mfr_notification_request where notification_id = " + input.getNotificationId();
			
			String categoryNameQuery = "select c.mfr_req_category_desc"
					+ " from mfr_notification_category c, mfr_notification_req_category n"
					+ " where c.mfr_req_category_id = n.mfr_req_category_id"
					+ " and n.notification_id = " + input.getNotificationId();
			
			String emailAddr = factory.selectSingleValue(requesterEmailQuery, conn);
			StringBuilder subject = new StringBuilder("Rejected: Manufacturer Notification Request ")
					.append(input.getNotificationId()).append(" - ");
			
			@SuppressWarnings("unchecked")
			Collection<MfrNotificationCategoryBean> categoryColl = factory.setBean(new MfrNotificationCategoryBean()).selectQuery(categoryNameQuery, conn);
			for (MfrNotificationCategoryBean category : categoryColl) {
				subject.append(category.getMfrReqCategoryDesc()).append("; ");
			}
			
			String body = "Your request has been rejected by " + user.getFullName() + ". \n\n"
					+ input.getInternalComments();
			
			MailProcess.sendEmail(emailAddr, null,user.getEmail(),subject.toString(),body);
		} catch (Exception e) {
			log.error(e);
			throw new BaseException(e.getMessage());
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public void sendNotification(MfrNotificationRequest input) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			Collection<String> inParams = new Vector<>();
			inParams.add(input.getNotificationId().toPlainString());
			
			Collection<Integer> outParams = new Vector<>();
			outParams.add(new Integer(java.sql.Types.VARCHAR));
			@SuppressWarnings("unchecked")
			Collection<String> data = factory.doProcedure(conn, "pkg_manufacturer_notification.p_generate_mfr_notif_email", inParams, outParams);
			Iterator<String> i11 = data.iterator();
			String errorVal = "";
			while (i11.hasNext()) {
				errorVal = (String) i11.next();
			}
	
			if (!StringHandler.isBlankString(errorVal)) {
				if (!"OK".equalsIgnoreCase(errorVal)) {
					MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","pkg_manufacturer_notification.p_generate_mfr_notif_email (notification_id: " + input.getNotificationId() + ")","Error while executing p_send_mfg_notif_email: "+errorVal);
					input.setInternalComments(errorVal + " " + input.getInternalComments());
					updateInternalComments(input, conn);
				}
			}
		} catch(Exception e) {
			log.error(e);
			String errorMsg = "Error while executing p_generate_mfg_notif_email, see log for details.";
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","pkg_manufacturer_notification.p_generate_mfr_notif_email (notification_id: " + input.getNotificationId() + ")",errorMsg);
            input.setInternalComments(errorMsg + " " + input.getInternalComments());
			updateInternalComments(input, conn);
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public void loadManufacturer(MfrNotificationRequest input) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			String notificationCategoryId = null;
			String query = "select notification_category_id from mfr_notification_req_category"
					+ " where notification_id = " + input.getNotificationId()
					+ " and mfr_req_category_id = " + input.getMfrReqCategoryId();
			
			notificationCategoryId = factory.selectSingleValue(query, conn);
			if (StringHandler.isBlankString(notificationCategoryId)) {
				notificationCategoryId = factory.getSequence("mfr_notification_seq").toPlainString();
				
				String categoryInsert = "insert into mfr_notification_req_category"
						+ " (notification_category_id, notification_id, mfr_req_category_id)"
						+ " select " + notificationCategoryId
						+ ", " + input.getNotificationId()
						+ ", " + input.getMfrReqCategoryId()
						+ " from dual where not exists("
						+ "select 1 from mfr_notification_req_category"
						+ " where notification_id = " + input.getNotificationId()
						+ " and mfr_req_category_id = " + input.getMfrReqCategoryId() + ")";
				
				factory.deleteInsertUpdate(categoryInsert, conn);
			}
			
			String updateStmt = "update mfr_notification_req_detail set mfg_id = "
					+ input.getMfgId()
					+ " where notification_category_id = " + notificationCategoryId
					+ " and document_id is NULL";
			
			int rowsUpdated = factory.deleteInsertUpdate(updateStmt, conn);
			
			if (rowsUpdated == 0) {
				String insertStmt = "insert into mfr_notification_req_detail"
						+ " (notification_detail_id, notification_category_id, mfg_id)"
						+ " values (mfr_notification_seq.nextval"
						+ ", " + notificationCategoryId
						+ ", " + input.getMfgId() + ")";
				
				factory.deleteInsertUpdate(insertStmt, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public void addMaterials(MfrNotificationRequest input, Collection<BigDecimal> materials) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			String query = "select notification_category_id from mfr_notification_req_category"
					+ " where notification_id = " + input.getNotificationId()
					+ " and mfr_req_category_id = " + input.getMfrReqCategoryId();
			
			String categoryId = factory.selectSingleValue(query, conn);
			if (StringHandler.isBlankString(categoryId)) {
				categoryId = factory.getSequence("mfr_notification_seq").toString();
				String categoryInsert = "insert into mfr_notification_req_category"
						+ " (notification_category_id, notification_id, mfr_req_category_id)"
						+ " VALUES("
						+ categoryId
						+ ", " + input.getNotificationId()
						+ ", " + input.getMfrReqCategoryId() + ")";
				
				factory.deleteInsertUpdate(categoryInsert, conn);
			}
			
			for (BigDecimal materialId : materials) {
				String insertStmt = "insert into mfr_notification_req_detail"
						+ " (notification_detail_id, notification_category_id, material_id)"
						+ " select mfr_notification_seq.nextval"
						+ ", " + categoryId
						+ ", " + materialId
						+ " from dual where not exists("
						+ "select 1 from mfr_notification_req_detail"
						+ " where notification_category_id = " + categoryId
						+ " and material_id = " + materialId + ")";
				
				factory.deleteInsertUpdate(insertStmt, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public void deleteMaterials(MfrNotificationRequest input, Collection<MaterialAffectedNotification> materials) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			String query = "select notification_category_id from mfr_notification_req_category"
					+ " where notification_id = " + input.getNotificationId()
					+ " and mfr_req_category_id = " + input.getMfrReqCategoryId();
			
			String categoryId = factory.selectSingleValue(query, conn);
			
			for (MaterialAffectedNotification material : materials) {
				if (material.isGrab()) {
					String deleteStmt = "delete from mfr_notification_req_detail"
							+ " where notification_category_id = " + categoryId
							+ " and material_id = " + material.getMaterialId();
					
					factory.deleteInsertUpdate(deleteStmt, conn);
				}
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	public void addItems(MfrNotificationRequest input, Collection<BigDecimal> ids, boolean byMaterial) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			String query = "select notification_category_id from mfr_notification_req_category"
					+ " where notification_id = " + input.getNotificationId()
					+ " and mfr_req_category_id = " + input.getMfrReqCategoryId();
			
			String categoryId = factory.selectSingleValue(query, conn);
			if (StringHandler.isBlankString(categoryId)) {
				categoryId = factory.getSequence("mfr_notification_seq").toString();
				String categoryInsert = "insert into mfr_notification_req_category"
						+ " (notification_category_id, notification_id, mfr_req_category_id)"
						+ " VALUES("
						+ categoryId
						+ ", " + input.getNotificationId()
						+ ", " + input.getMfrReqCategoryId() + ")";
				
				factory.deleteInsertUpdate(categoryInsert, conn);
			}
			
			if (byMaterial) {
				for (BigDecimal materialId : ids) {
					String itemIdQuery = "select item_id from global.part where material_id = " + materialId;
					@SuppressWarnings("unchecked")
					Collection<MfrNotificationRequest> itemIds = factory.setBean(new MfrNotificationRequest()).selectQuery(itemIdQuery, conn);
					addItems(categoryId, itemIds.stream().map(r -> r.getItemId()).collect(Collectors.toList()), conn);
				}
			}
			else {
				addItems(categoryId, ids, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private void addItems(String categoryId, Collection<BigDecimal> itemIds, Connection conn) throws BaseException {
		if (itemIds != null) {
			for (BigDecimal itemId : itemIds) {
				
				String insertStmt = "insert into mfr_notification_req_detail"
						+ " (notification_detail_id, notification_category_id, item_id)"
						+ " select mfr_notification_seq.nextval"
						+ ", " + categoryId
						+ ", " + itemId
						+ " from dual where not exists("
						+ "select 1 from mfr_notification_req_detail"
						+ " where notification_category_id = " + categoryId
						+ " and item_id = " + itemId + ")";
				
				factory.deleteInsertUpdate(insertStmt, conn);
			}
		}
	}
	
	public void deleteItems(MfrNotificationRequest input, Collection<ItemAffectedNotification> items) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			String query = "select notification_category_id from mfr_notification_req_category"
					+ " where notification_id = " + input.getNotificationId()
					+ " and mfr_req_category_id = " + input.getMfrReqCategoryId();
			
			String categoryId = factory.selectSingleValue(query, conn);
			
			for (ItemAffectedNotification item : items) {
				if (item.isGrab()) {
					String deleteStmt = "delete from mfr_notification_req_detail"
							+ " where notification_category_id = " + categoryId
							+ " and item_id = " + item.getItemId();
					
					factory.deleteInsertUpdate(deleteStmt, conn);
				}
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private void checkItemConversionFactor(MfrNotificationRequest request, MfrNotificationCategoryBean category, Connection conn) throws BaseException {
		String query = "select d.material_id, d.item_id, count(icf.item_id) conversion_factor_count"
				+ " from mfr_notification_req_category c, mfr_notification_req_detail d, global.part p, global.item_conversion_factor icf"
				+ " where c.notification_id = " + request.getNotificationId()
				+ " and c.mfr_req_category_id = " + category.getMfrReqCategoryId()
				+ " and c.notification_category_id = d.notification_category_id"
				+ " and d.material_id is not null"
				+ " and d.material_id = p.material_id"
				+ " and (p.item_id = icf.item_id or p.item_id = icf.original_item_id)"
				+ " group by d.material_id, d.item_id"
				+ " union"
				+ " select d.material_id, d.item_id, count(icf.item_id) conversion_factor_count"
				+ " from mfr_notification_req_category c, mfr_notification_req_detail d, global.item i, global.item_conversion_factor icf"
				+ " where c.notification_id = " + request.getNotificationId()
				+ " and c.mfr_req_category_id = " + category.getMfrReqCategoryId()
				+ " and c.notification_category_id = d.notification_category_id"
				+ " and d.item_id is not null"
				+ " and d.item_id = i.item_id"
				+ " and i.item_type <> 'OB'"
				+ " and (d.item_id = icf.item_id or d.item_id = icf.original_item_id)"
				+ " group by d.item_id, d.material_id";
		
		@SuppressWarnings("unchecked")
		Collection<ItemConversionFactorBean> icfBeans = factory.setBean(new ItemConversionFactorBean()).selectQuery(query);
		
		if ( ! icfBeans.isEmpty()) {
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			StringBuilder icfFailureMsg = new StringBuilder("");
			icfBeans.stream().filter(b -> b.getConversionFactorCount().intValue() > 0)
					.filter(b -> b.getMaterialId() != null).forEach(b ->  icfFailureMsg.append(library.getString("label.material")).append(" ").append(b.getMaterialId()).append("<br/>"));
			
			icfBeans.stream().filter(b -> b.getConversionFactorCount().intValue() > 0)
					.filter(b -> b.getItemId() != null).forEach(b ->  icfFailureMsg.append(library.getString("label.item")).append(" ").append(b.getItemId()).append("<br/>"));
			
			String msg = library.format("label.mfrnotifyicffailure", request.getNotificationId().toPlainString(), icfFailureMsg.toString());
			throw new BaseException(msg);
		}
	}
	
	private void resolveItemConversionFactor(ItemAffectedNotification item, String itemUpdate, Connection conn) throws BaseException {
		resolveItemConversionFactor(item, null, itemUpdate, conn);
	}
	
	@SuppressWarnings("unchecked")
	private void resolveItemConversionFactor(ItemAffectedNotification item, BigDecimal mfrId, String itemUpdate, Connection conn) throws BaseException {
		Collection<ItemConversionFactorBean> conversionFactor = Collections.emptyList();
		Collection<ItemConversionBean> conversion = Collections.emptyList();
		
		String conversionFactorRemoval = "";
		String conversionRemoval = "";
		if (item.getMaterialId() == null) {
			if (mfrId != null) {
				// use Mfr ID
				String itemConversionFactorQuery = "select icf.* from global.item_conversion_factor icf, global.part p, global.material m"
						+ " where icf.item_id = p.item_id"
						+ " and p.material_id = m.material_id"
						+ " and m.mfg_id = " + mfrId;
				
				conversionFactor = factory.setBean(new ItemConversionFactorBean()).selectQuery(itemConversionFactorQuery, conn);
				
				String itemConversionQuery = "select ic.* from item_conversion ic, global.part p, global.material m"
						+ " where ic.item_id = p.item_id"
						+ " and p.material_id = m.material_id"
						+ " and m.mfg_id = " + mfrId;
				
				conversion = factory.setBean(new ItemConversionBean()).selectQuery(itemConversionQuery, conn);
				
				conversionFactorRemoval = "delete (" + itemConversionFactorQuery + ")";
				
				conversionRemoval = "delete (" + itemConversionQuery + ")";
			}
		}
		else if (item.getItemId() == null) {
			// use Material ID
			String itemConversionFactorQuery = "select icf.* from global.item_conversion_factor icf, global.part p"
					+ " where icf.item_id = p.item_id"
					+ " and p.material_id = " + item.getMaterialId();
			
			conversionFactor = factory.setBean(new ItemConversionFactorBean()).selectQuery(itemConversionFactorQuery, conn);
			
			String itemConversionQuery = "select ic.* from item_conversion ic, global.part p"
					+ " where ic.item_id  = p.item_id"
					+ " and p.material_id = " + item.getMaterialId();
			
			conversion = factory.setBean(new ItemConversionBean()).selectQuery(itemConversionQuery, conn);
			
			conversionFactorRemoval = "delete (" + itemConversionFactorQuery + ")";
			
			conversionRemoval = "delete (" + itemConversionQuery + ")";
		}
		else {
			// use Item ID
			String itemConversionFactorQuery = "select * from global.item_conversion_factor"
					+ " where original_item_id = " + item.getItemId();
			
			conversionFactor = factory.setBean(new ItemConversionFactorBean()).selectQuery(itemConversionFactorQuery, conn);
			
			String itemConversionQuery = "select * from item_conversion"
					+ " where original_item_id = " + item.getItemId();
			
			conversion = factory.setBean(new ItemConversionBean()).selectQuery(itemConversionQuery, conn);
			
			conversionFactorRemoval = "delete (" + itemConversionFactorQuery + ")";
			
			conversionRemoval = "delete (" + itemConversionQuery + ")";
		}
		
		if ( ! conversionFactor.isEmpty()) {
			factory.deleteInsertUpdate(conversionRemoval, conn);
			factory.deleteInsertUpdate(conversionFactorRemoval, conn);
			
			factory.deleteInsertUpdate(itemUpdate, conn);
			
			StringBuilder conversionFactorInsert = new StringBuilder("");
			for (ItemConversionFactorBean icf : conversionFactor) {
				if (conversionFactorInsert.length() == 0) {
					conversionFactorInsert.append("insert all");
				}
				else {
					conversionFactorInsert.append(" ");
				}
				conversionFactorInsert.append(" into global.item_conversion_factor values(");
				conversionFactorInsert.append(icf.getItemId());
				conversionFactorInsert.append(", ").append(icf.getOriginalItemId());
				conversionFactorInsert.append(", ").append(icf.getQuantityPerOriginalItem());
				conversionFactorInsert.append(", ").append(SqlHandler.delimitString(icf.getItemType()));
				conversionFactorInsert.append(", ").append(SqlHandler.delimitString(icf.getOriginalItemType()));
				conversionFactorInsert.append(", ").append(icf.getMaterialId());
				conversionFactorInsert.append(", ").append(icf.getPartId()).append(")");
			}
			conversionFactorInsert.append(" select * from dual");
			
			StringBuilder conversionInsert = new StringBuilder("");
			for (ItemConversionBean ic : conversion) {
				if (conversionInsert.length() == 0) {
					conversionInsert.append("insert all");
				}
				else {
					conversionInsert.append(" ");
				}
				conversionInsert.append(" into item_conversion values(");
				conversionInsert.append(ic.getItemId());
				conversionInsert.append(", ").append(ic.getInventoryGroup());
				conversionInsert.append(", ").append(ic.getBlanketMr());
				conversionInsert.append(", ").append(ic.getOriginalItemId());
				conversionInsert.append(", ").append(ic.getAutoTap());
				conversionInsert.append(", ").append(ic.getPriority()).append(")");
			}
			conversionInsert.append(" select * from dual");
			
			factory.deleteInsertUpdate(conversionFactorInsert.toString(), conn);
			factory.deleteInsertUpdate(conversionInsert.toString(), conn);
		}
		// the itemUpdate should always be run
		else {
			factory.deleteInsertUpdate(itemUpdate, conn);
		}
	}
	
	public void deleteRequest(MfrNotificationRequest input) throws BaseException {
		String detailRemoval = "delete from mfr_notification_req_detail where"
				+ " notification_category_id in (select notification_category_id from"
				+ " mfr_notification_req_category where notification_id = " + input.getNotificationId() + ")";
		
		String categoryRemoval = "delete from mfr_notification_req_category where"
				+ " notification_id = " + input.getNotificationId();
		
		String requestRemoval = "delete from mfr_notification_request where"
				+ " notification_id = " + input.getNotificationId();

		Connection conn = dbManager.getConnection();
		try {
			factory.deleteInsertUpdate(detailRemoval, conn);
			factory.deleteInsertUpdate(categoryRemoval, conn);
			factory.deleteInsertUpdate(requestRemoval, conn);
		} catch(Exception e) {
			throw new BaseException(e.getMessage());
		} finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private void updateInternalComments(MfrNotificationRequest input, Connection conn) throws BaseException {
		String internalCommentUpdate = "update mfr_notification_request"
				+ " set internal_comments = substr(" + SqlHandler.delimitString(input.getInternalComments()) + ", 0, 2000)"
				+ " where notification_id = " + input.getNotificationId();
		
		factory.deleteInsertUpdate(internalCommentUpdate, conn);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<VvShelfLifeBasisBean> getShelfLifeBasisColl() throws BaseException {
		String query = "select * from vv_shelf_life_basis where display_on_item_qc = 'Y' order by display_order";
		
		return factory.setBean(new VvShelfLifeBasisBean()).selectQuery(query);
	}
}
