package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.internal.catalog.beans.ItemAffectedNotification;
import com.tcmis.internal.catalog.beans.MaterialAffectedNotification;
import com.tcmis.internal.catalog.beans.MfrAffectedNotification;
import com.tcmis.internal.catalog.beans.MfrNotificationCategoryBean;
import com.tcmis.internal.catalog.beans.MfrNotificationRequest;

public class MfrNotificationFactory extends BaseBeanFactory {

	public String ATTRIBUTE_MFG_ID = "MFG_ID";
	public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
	public String ATTRIBUTE_MFG_URL = "MFG_URL";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_CONTACT = "CONTACT";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_CAGE_CODE = "CAGE_CODE";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_EQUIPMENT_CHANGE = "EQUIPMENT_CHANGE";
	public String ATTRIBUTE_ACQUIRED_MFR_ID = "ACQUIRED_MFR_ID";
	public String ATTRIBUTE_ACQUIRED_MFR_DESC = "ACQUIRED_MFR_DESC";
	public String ATTRIBUTE_ACQUISITION_TYPE = "ACQUISITION_TYPE";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	public String ATTRIBUTE_PRODUCT_CODE = "PRODUCT_CODE";
	public String ATTRIBUTE_LOCALE_MATERIAL_DESC = "LOCALE_MATERIAL_DESC";
	public String ATTRIBUTE_LOCALE_TRADE_NAME = "LOCALE_TRADE_NAME";
	public String ATTRIBUTE_MATERIAL_DESC_EXTENSION = "MATERIAL_DESC_EXTENSION";
	public String ATTRIBUTE_TRADE_NAME_EXTENSION = "TRADE_NAME_EXTENSION";
	public String ATTRIBUTE_LOCALE_MATERIAL_DESC_EXTENSION = "LOCALE_MATERIAL_DESC_EXTENSION";
	public String ATTRIBUTE_LOCALE_TRADE_NAME_EXTENSION = "LOCALE_TRADE_NAME_EXTENSION";
	
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_PKG_STYLE = "PKG_STYLE";
	public String ATTRIBUTE_REVISION_COMMENTS = "REVISION_COMMENTS";
	public String ATTRIBUTE_STOCKING_TYPE = "STOCKING_TYPE";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_LOCALE_MFG_PART_NO = "LOCALE_MFG_PART_NO";
	public String ATTRIBUTE_GRADE = "GRADE";
	public String ATTRIBUTE_LOCALE_GRADE = "LOCALE_GRADE";
	public String ATTRIBUTE_MIN_TEMP = "MIN_TEMP";
	public String ATTRIBUTE_MAX_TEMP = "MAX_TEMP";
	public String ATTRIBUTE_TEMP_UNITS = "TEMP_UNITS";
	public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
	public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
	public String ATTRIBUTE_SHELF_LIFE_BASIS_DISP_LABEL = "SHELF_LIFE_BASIS_DISP_LABEL";
	public String ATTRIBUTE_LOCALE_MIN_TEMP = "LOCALE_MIN_TEMP";
	public String ATTRIBUTE_LOCALE_MAX_TEMP = "LOCALE_MAX_TEMP";
	public String ATTRIBUTE_LOCALE_TEMP_UNITS = "LOCALE_TEMP_UNITS";
	public String ATTRIBUTE_MFG_PART_NO_EXTENSION = "MFG_PART_NO_EXTENSION";
	public String ATTRIBUTE_LOCALE_MFG_PART_NO_EXTENSION = "LOCALE_MFG_PART_NO_EXTENSION";
	
	public String ATTRIBUTE_LOCALE_CODE = "LOCALE_CODE";
	public String ATTRIBUTE_LOCALE_DISPLAY = "LOCALE_DISPLAY";
	
	public String MFR_TABLE = "global.manufacturer";
	public String MATERIAL_TABLE = "global.material";
	public String MATERIAL_LOCALE_TABLE = "global.material_locale";
	public String ITEM_TABLE = "global.item";
	public String PART_TABLE = "global.part";
	public String PART_LOCALE_TABLE = "global.part_locale";
	public String LOCALE_TABLE = "vv_locale";
	public String MFR_REQUEST_NOT_TABLE = "mfr_notification_request";
	public String MFR_REQUEST_CAT_TABLE = "mfr_notification_req_category";
	public String MFR_REQUEST_DETAIL_TABLE = "mfr_notification_req_detail";
	
	private Class<? extends BaseDataBean> beanType;
	
	public MfrNotificationFactory(DbManager dbManager) {
		super(dbManager);
	}
	
	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("mfgId")) {
			return ATTRIBUTE_MFG_ID;
		}
		else if (attributeName.equals("mfgDesc")) {
			return ATTRIBUTE_MFG_DESC;
		}
		else if (attributeName.equals("mfgUrl")) {
			return ATTRIBUTE_MFG_URL;
		}
		else if (attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if (attributeName.equals("contact")) {
			return ATTRIBUTE_CONTACT;
		}
		else if (attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if (attributeName.equals("cageCode")) {
			return ATTRIBUTE_CAGE_CODE;
		}
		else if (attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if (attributeName.equals("equipmentChange")) {
			return ATTRIBUTE_EQUIPMENT_CHANGE;
		}
		else if (attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if (attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if (attributeName.equals("tradeName")) {
			return ATTRIBUTE_TRADE_NAME;
		}
		else if (attributeName.equals("productCode")) {
			return ATTRIBUTE_PRODUCT_CODE;
		}
		else if (attributeName.equals("localeMaterialDesc")) {
			return ATTRIBUTE_LOCALE_MATERIAL_DESC;
		}
		else if (attributeName.equals("localeTradeName")) {
			return ATTRIBUTE_LOCALE_TRADE_NAME;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if (attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if (attributeName.equals("pkgStyle")) {
			return ATTRIBUTE_PKG_STYLE;
		}
		else if (attributeName.equals("revisionComments")) {
			return ATTRIBUTE_REVISION_COMMENTS;
		}
		else if (attributeName.equals("stockingType")) {
			return ATTRIBUTE_STOCKING_TYPE;
		}
		else if (attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if (attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if (attributeName.equals("mfgPartNo")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if (attributeName.equals("localeMfgPartNo")) {
			return ATTRIBUTE_LOCALE_MFG_PART_NO;
		}
		else if (attributeName.equals("grade")) {
			return ATTRIBUTE_GRADE;
		}
		else if (attributeName.equals("localeGrade")) {
			return ATTRIBUTE_LOCALE_GRADE;
		}
		else if (attributeName.equals("minTemp")) {
			return ATTRIBUTE_MIN_TEMP;
		}
		else if (attributeName.equals("maxTemp")) {
			return ATTRIBUTE_MAX_TEMP;
		}
		else if (attributeName.equals("tempUnits")) {
			return ATTRIBUTE_TEMP_UNITS;
		}
		else if (attributeName.equals("shelfLifeDays")) {
			return ATTRIBUTE_SHELF_LIFE_DAYS;
		}
		else if (attributeName.equals("shelfLifeBasis")) {
			return ATTRIBUTE_SHELF_LIFE_BASIS;
		}
		else if (attributeName.equals("shelfLifeBasisDispLabel")) {
			return ATTRIBUTE_SHELF_LIFE_BASIS_DISP_LABEL;
		}
		else if (attributeName.equals("localeMinTemp")) {
			return ATTRIBUTE_LOCALE_MIN_TEMP;
		}
		else if (attributeName.equals("localeMaxTemp")) {
			return ATTRIBUTE_LOCALE_MAX_TEMP;
		}
		else if (attributeName.equals("localeTempUnits")) {
			return ATTRIBUTE_LOCALE_TEMP_UNITS;
		}
		else if (attributeName.equals("localeCode")) {
			return ATTRIBUTE_LOCALE_CODE;
		}
		else if (attributeName.equals("localeDisplay")) {
			return ATTRIBUTE_LOCALE_DISPLAY;
		}
		else if (attributeName.equals("acquiredMfrId")) {
			return ATTRIBUTE_ACQUIRED_MFR_ID;
		}
		else if (attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if (attributeName.equals("materialDescExtension")) {
			return ATTRIBUTE_MATERIAL_DESC_EXTENSION;
		}
		else if (attributeName.equals("tradeNameExtension")) {
			return ATTRIBUTE_TRADE_NAME_EXTENSION;
		}
		else if (attributeName.equals("localeMaterialDescExtension")) {
			return ATTRIBUTE_LOCALE_MATERIAL_DESC_EXTENSION;
		}
		else if (attributeName.equals("localeTradeNameExtension")) {
			return ATTRIBUTE_LOCALE_TRADE_NAME_EXTENSION;
		}
		else if (attributeName.equals("mfgPartNoExtension")) {
			return ATTRIBUTE_MFG_PART_NO_EXTENSION;
		}
		else if (attributeName.equals("localeMfgPartNoExtension")) {
			return ATTRIBUTE_LOCALE_MFG_PART_NO_EXTENSION;
		}
		else if (attributeName.equals("acquiredMfrDesc")){
			return ATTRIBUTE_ACQUIRED_MFR_DESC;
		}
		else if (attributeName.equals("acquisitionType")) {
			return ATTRIBUTE_ACQUISITION_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}
	
	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, beanType);
	}

	public MfrAffectedNotification getMfrAffectedNotification(MfrNotificationRequest request, Connection conn) throws BaseException {
		String additionalColumn = "";
		String additionalFilter = "";
		String additionalTable = "";
		if (request.isApproved()) {
			additionalColumn = ", mrc."+ATTRIBUTE_ACQUIRED_MFR_ID
					+ ", mrc."+ATTRIBUTE_COMMENTS
					+ ", mfra."+ATTRIBUTE_MFG_DESC+" "+ATTRIBUTE_ACQUIRED_MFR_DESC
					+ ", nvl2(m."+ATTRIBUTE_MATERIAL_ID+",'DIVISION','WHOLE_COMPANY') "+ATTRIBUTE_ACQUISITION_TYPE;
			additionalTable = ", "+MFR_TABLE+" mfra, "+MATERIAL_TABLE+" m";
			additionalFilter = " and mfra."+ATTRIBUTE_MFG_ID+"(+) = mrc."+ATTRIBUTE_ACQUIRED_MFR_ID
					+ " and m."+ATTRIBUTE_MFG_ID+"(+) = mfra."+ATTRIBUTE_MFG_ID;
		}
		String query = "select "
				+ "mfr."+ATTRIBUTE_MFG_ID
				+ ", mfr."+ATTRIBUTE_MFG_DESC
				+ ", mfr."+ATTRIBUTE_MFG_URL
				+ ", mfr."+ATTRIBUTE_PHONE
				+ ", mfr."+ATTRIBUTE_CONTACT
				+ ", mfr."+ATTRIBUTE_EMAIL
				+ ", mfr."+ATTRIBUTE_NOTES
				+ ", mfr."+ATTRIBUTE_CAGE_CODE
				+ ", mrc."+ATTRIBUTE_EQUIPMENT_CHANGE
				+ additionalColumn
				+ " from "+MFR_TABLE+" mfr, "+MFR_REQUEST_CAT_TABLE+" mrc, "+MFR_REQUEST_DETAIL_TABLE+" d"
				+ additionalTable
				+ " where "
				+ "mfr."+ATTRIBUTE_MFG_ID+"(+) = d."+ATTRIBUTE_MFG_ID
				+ " and d.notification_category_id = mrc.notification_category_id"
				+ " and mrc.notification_id = " + request.getNotificationId()
				+ " and mrc.mfr_req_category_id = " + request.getMfrReqCategoryId()
				+ " and d."+ATTRIBUTE_MFG_ID+" is not null"
				+ additionalFilter;
		
		MfrAffectedNotification mfrBean = null;
		beanType = MfrAffectedNotification.class;
		
		DataSet dataSet = new SqlManager().select(conn, query.toString());
		if (dataSet.isEmpty()) {
			query = "select "
					+ "mfr."+ATTRIBUTE_MFG_ID
					+ ", mfr."+ATTRIBUTE_MFG_DESC
					+ ", mfr."+ATTRIBUTE_MFG_URL
					+ ", mfr."+ATTRIBUTE_PHONE
					+ ", mfr."+ATTRIBUTE_CONTACT
					+ ", mfr."+ATTRIBUTE_EMAIL
					+ ", mfr."+ATTRIBUTE_NOTES
					+ ", mfr."+ATTRIBUTE_CAGE_CODE
					+ " from "+MFR_TABLE+" mfr"
					+ " where "
					+ "mfr."+ATTRIBUTE_MFG_ID+" = " + request.getMfgId();
			
			dataSet = new SqlManager().select(conn, query.toString());
		}

		@SuppressWarnings("unchecked")
		Iterator<DataSetRow> dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = dataIter.next();
			mfrBean = new MfrAffectedNotification();
			load(dataSetRow, mfrBean);
			break;
		}
		
		return mfrBean;
	}
	
	public BigDecimal getMfgIdForRequest(MfrNotificationRequest request, Connection conn) throws BaseException {
		String query = "select "
				+ "d."+ATTRIBUTE_MFG_ID
				+ " from "+MFR_REQUEST_DETAIL_TABLE+" d"
				+ ", "+MFR_REQUEST_CAT_TABLE+" mrc"
				+ " where mrc.notification_id=" + request.getNotificationId()
				+ " and mrc.mfr_req_category_id=" + request.getMfrReqCategoryId()
				+ " and d.notification_category_id = mrc.notification_category_id";
		
		BigDecimal mfgId = null;
		DataSet dataSet = new SqlManager().select(conn, query);
		
		@SuppressWarnings("unchecked")
		Iterator<DataSetRow> dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = dataIter.next();
			mfgId = dataSetRow.getBigDecimal(ATTRIBUTE_MFG_ID);
			break;
		}
		
		return mfgId;
	}
	
	public Collection<MaterialAffectedNotification> getMaterialsByMfr(BigDecimal mfrId, List<MaterialAffectedNotification> newMaterials, Connection conn) throws BaseException {
		if (newMaterials == null) {
			newMaterials = new ArrayList<MaterialAffectedNotification>();
		}
		
		String query = "select "
				+ ATTRIBUTE_MATERIAL_ID
				+ ", " + ATTRIBUTE_MATERIAL_DESC
				+ ", " + ATTRIBUTE_MATERIAL_DESC_EXTENSION
				+ ", " + ATTRIBUTE_TRADE_NAME
				+ ", " + ATTRIBUTE_TRADE_NAME_EXTENSION
				+ ", " + ATTRIBUTE_MFG_ID
				+ " from " + MATERIAL_TABLE  
				+ " where "+ATTRIBUTE_MFG_ID+" = " + mfrId
				+ " order by "+ATTRIBUTE_MATERIAL_ID;
		
		Collection<MaterialAffectedNotification> materialCollection = new Vector<MaterialAffectedNotification>();
		beanType = MaterialAffectedNotification.class;
		
		DataSet dataSet = new SqlManager().select(conn, query);

		@SuppressWarnings("unchecked")
		Iterator<DataSetRow> dataIter = dataSet.iterator();

		int idx = 0;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = dataIter.next();
			MaterialAffectedNotification materialBean = new MaterialAffectedNotification();
			load(dataSetRow, materialBean);
			materialCollection.add(materialBean);
			if (newMaterials.size()<= idx || newMaterials.get(idx).getMaterialId() == null || 
					materialBean.getMaterialId().compareTo(newMaterials.get(idx).getMaterialId()) != 0) {
				newMaterials.add(idx, new MaterialAffectedNotification());
			}
			idx++;
		}
		
		return materialCollection;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MaterialAffectedNotification> getMaterialsByMaterialId(MfrNotificationRequest request, List<MaterialAffectedNotification> newMaterials, Connection conn) throws BaseException {
		if (newMaterials == null) {
			newMaterials = new ArrayList<MaterialAffectedNotification>();
		}
		
		String dynamicFilter = "";
		String tables = MATERIAL_TABLE+" m, "+MATERIAL_LOCALE_TABLE+" ml, "+LOCALE_TABLE+" l";
		if (request.getMaterialId() == null) {
			tables = tables + ", "+MFR_REQUEST_DETAIL_TABLE+" d, "+MFR_REQUEST_CAT_TABLE+" c";
			dynamicFilter = " and m."+ATTRIBUTE_MATERIAL_ID+" = d."+ATTRIBUTE_MATERIAL_ID
				+ " and c.notification_category_id = d.notification_category_id"
				+ " and c.mfr_req_category_id = "+request.getMfrReqCategoryId()
				+ " and c.notification_id = "+request.getNotificationId();
		}
		else {
			dynamicFilter = " and m."+ATTRIBUTE_MATERIAL_ID+" = "+request.getMaterialId();
		}

		Collection<MaterialAffectedNotification> materialCollection = new Vector<MaterialAffectedNotification>();
		Iterator<DataSetRow> dataIter = null;
		String query = "select "
				+ "m."+ATTRIBUTE_MATERIAL_ID
				+ ", m."+ATTRIBUTE_MATERIAL_DESC
				+ ", m."+ATTRIBUTE_MATERIAL_DESC_EXTENSION
				+ ", m."+ATTRIBUTE_TRADE_NAME
				+ ", m."+ATTRIBUTE_TRADE_NAME_EXTENSION
				+ ", m."+ATTRIBUTE_PRODUCT_CODE
				+ ", m."+ATTRIBUTE_MFG_ID
				+ ", ml."+ATTRIBUTE_LOCALE_CODE
				+ ", l."+ATTRIBUTE_LOCALE_DISPLAY
				+ ", ml."+ATTRIBUTE_MATERIAL_DESC+" "+ATTRIBUTE_LOCALE_MATERIAL_DESC
				+ ", ml."+ATTRIBUTE_MATERIAL_DESC_EXTENSION + " " + ATTRIBUTE_LOCALE_MATERIAL_DESC_EXTENSION
				+ ", ml."+ATTRIBUTE_TRADE_NAME+" "+ATTRIBUTE_LOCALE_TRADE_NAME
				+ ", ml."+ATTRIBUTE_TRADE_NAME_EXTENSION + " " + ATTRIBUTE_LOCALE_TRADE_NAME_EXTENSION
				+ " from " + tables
				+ " where "
				+ "m."+ATTRIBUTE_MATERIAL_ID+" = ml."+ATTRIBUTE_MATERIAL_ID+"(+)"
				+ " and l."+ATTRIBUTE_LOCALE_CODE+"(+) = ml."+ATTRIBUTE_LOCALE_CODE
				+ dynamicFilter
				+ " order by m."+ATTRIBUTE_MATERIAL_ID+", l."+ATTRIBUTE_LOCALE_DISPLAY;
		
		beanType = MaterialAffectedNotification.class;
		
		DataSet dataSet = new SqlManager().select(conn, query);
		
		dataIter = dataSet.iterator();

		int idx = 0;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = dataIter.next();
			MaterialAffectedNotification materialBean = new MaterialAffectedNotification();
			load(dataSetRow, materialBean);
			materialCollection.add(materialBean);
			if (newMaterials.size()<= idx || newMaterials.get(idx).getMaterialId() == null ||
					materialBean.getMaterialId().compareTo(newMaterials.get(idx).getMaterialId()) != 0) {
				newMaterials.add(idx, new MaterialAffectedNotification());
			}
			/*if (idx == 0) {
				request.setMaterialId(materialBean.getMaterialId());
			}*/
			idx++;
		}
		
		if (materialCollection.size() < newMaterials.size()) {
			Iterator<MaterialAffectedNotification> newMaterialIterator = newMaterials.iterator();
			Iterator<MaterialAffectedNotification> currentMaterialIterator = materialCollection.iterator();
			MaterialAffectedNotification currentMaterial = null;
			while (newMaterialIterator.hasNext()) {
				MaterialAffectedNotification newMaterial = newMaterialIterator.next();
				if (currentMaterial == null && currentMaterialIterator.hasNext()) {
					currentMaterial = currentMaterialIterator.next();
				}
				if (newMaterial.getMaterialId() != null && currentMaterial != null && newMaterial.getMaterialId().compareTo(currentMaterial.getMaterialId()) == 0) {
					currentMaterial = null;
				}
				else {
					newMaterialIterator.remove();
				}
			}
		}
		
		return materialCollection;
	}
	
	private Collection<ItemAffectedNotification> getItemsQuery(String dynamicFilter, String additionalTables, List<ItemAffectedNotification> newItems, Connection conn) throws BaseException {
		if (newItems == null) {
			newItems = new ArrayList<ItemAffectedNotification>();
		}
		
		String query = "select "
				+ "i."+ATTRIBUTE_ITEM_ID
				+ ", i."+ATTRIBUTE_ITEM_DESC
				+ ", fx_kit_packaging(i."+ATTRIBUTE_ITEM_ID+") "+ATTRIBUTE_PACKAGING
				+ ", i."+ATTRIBUTE_REVISION_COMMENTS
				+ ", i."+ATTRIBUTE_STOCKING_TYPE
				+ ", i."+ATTRIBUTE_ITEM_TYPE
				+ ", p."+ATTRIBUTE_PART_ID
				+ ", p."+ATTRIBUTE_MATERIAL_ID
				+ ", p."+ATTRIBUTE_MFG_PART_NO
				+ ", p."+ATTRIBUTE_GRADE
				+ ", p."+ATTRIBUTE_MIN_TEMP
				+ ", p."+ATTRIBUTE_MAX_TEMP
				+ ", p."+ATTRIBUTE_TEMP_UNITS
				+ ", p."+ATTRIBUTE_SHELF_LIFE_DAYS
				+ ", p."+ATTRIBUTE_SHELF_LIFE_BASIS
				+ ", p."+ATTRIBUTE_MFG_PART_NO_EXTENSION
				+ ", slb.jsp_label "+ATTRIBUTE_SHELF_LIFE_BASIS_DISP_LABEL
				+ ", fx_kit_packaging(i."+ATTRIBUTE_ITEM_ID+", p."+ATTRIBUTE_MATERIAL_ID+") "+ATTRIBUTE_PKG_STYLE
				+ ", pl."+ATTRIBUTE_LOCALE_CODE
				+ ", l."+ATTRIBUTE_LOCALE_DISPLAY
				+ ", pl."+ATTRIBUTE_MFG_PART_NO+" "+ATTRIBUTE_LOCALE_MFG_PART_NO
				+ ", pl."+ATTRIBUTE_GRADE+" "+ATTRIBUTE_LOCALE_GRADE
				+ ", pl."+ATTRIBUTE_MIN_TEMP+" "+ATTRIBUTE_LOCALE_MIN_TEMP
				+ ", pl."+ATTRIBUTE_MAX_TEMP+" "+ATTRIBUTE_LOCALE_MAX_TEMP
				+ ", pl."+ATTRIBUTE_TEMP_UNITS+" "+ATTRIBUTE_LOCALE_TEMP_UNITS
				+ ", pl."+ATTRIBUTE_MFG_PART_NO_EXTENSION+" "+ATTRIBUTE_LOCALE_MFG_PART_NO_EXTENSION
				+ ", m."+ATTRIBUTE_MFG_ID
				+ " from "
				+ ITEM_TABLE+" i"
				+ ", "+PART_TABLE+" p"
				+ ", "+PART_LOCALE_TABLE+" pl"
				+ ", "+LOCALE_TABLE+" l"
				+ ", "+MATERIAL_TABLE+" m"
				+ ", vv_shelf_life_basis slb"
				+ additionalTables
				+ " where"
				+ " i."+ATTRIBUTE_ITEM_ID+" = p."+ATTRIBUTE_ITEM_ID
				+ " and p."+ATTRIBUTE_ITEM_ID+" = pl."+ATTRIBUTE_ITEM_ID+"(+)"
				+ " and p."+ATTRIBUTE_PART_ID+" = pl."+ATTRIBUTE_PART_ID+"(+)"
				+ " and l."+ATTRIBUTE_LOCALE_CODE+"(+) = pl."+ATTRIBUTE_LOCALE_CODE
				+ " and p."+ATTRIBUTE_SHELF_LIFE_BASIS+"=slb."+ATTRIBUTE_SHELF_LIFE_BASIS+"(+)"
				+ " and p."+ATTRIBUTE_MATERIAL_ID+"=m."+ATTRIBUTE_MATERIAL_ID
				+ " and " + dynamicFilter
				+ " order by i."+ATTRIBUTE_ITEM_ID+", p."+ATTRIBUTE_PART_ID+", l."+ATTRIBUTE_LOCALE_DISPLAY;
		
		Collection<ItemAffectedNotification> itemCollection = new Vector<ItemAffectedNotification>();
		beanType = ItemAffectedNotification.class;
		
		DataSet dataSet = new SqlManager().select(conn, query);

		@SuppressWarnings("unchecked")
		Iterator<DataSetRow> dataIter = dataSet.iterator();

		int idx = 0;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = dataIter.next();
			ItemAffectedNotification itemBean = new ItemAffectedNotification();
			load(dataSetRow, itemBean);
			itemCollection.add(itemBean);
			if (newItems.size()<= idx || newItems.get(idx).getItemId() == null
					|| itemBean.getItemId().compareTo(newItems.get(idx).getItemId()) != 0) {
				ItemAffectedNotification newItem = new ItemAffectedNotification();
				newItem.setMinTemp(itemBean.getMinTemp());
				newItem.setMaxTemp(itemBean.getMaxTemp());
				newItem.setLocaleMinTemp(itemBean.getLocaleMinTemp());
				newItem.setLocaleMaxTemp(itemBean.getLocaleMaxTemp());
				newItems.add(idx, newItem);
			}
			idx++;
		}
		
		if (itemCollection.size() < newItems.size()) {
			Iterator<ItemAffectedNotification> newItemIterator = newItems.iterator();
			Iterator<ItemAffectedNotification> currentItemIterator = itemCollection.iterator();
			ItemAffectedNotification currentItem = null;
			while (newItemIterator.hasNext()) {
				ItemAffectedNotification newItem = newItemIterator.next();
				if (currentItem == null && currentItemIterator.hasNext()) {
					currentItem = currentItemIterator.next();
				}
				if (newItem.getItemId() != null && currentItem != null && newItem.getItemId().compareTo(currentItem.getItemId()) == 0) {
					currentItem = null;
				}
				else {
					newItemIterator.remove();
				}
			}
		}
		
		return itemCollection;
		
	}
	
	public Collection<ItemAffectedNotification> getItemsByMaterial(MfrNotificationRequest request, MfrNotificationCategoryBean category, List<ItemAffectedNotification> newItems, Connection conn) throws BaseException {
		return getItemsByMaterial(request.getMaterialId(), category, request.isApproved(), newItems, conn);
	}
	
	public Collection<ItemAffectedNotification> getItemsByMaterial(BigDecimal materialId, MfrNotificationCategoryBean category, boolean requestApproved, List<ItemAffectedNotification> newItems, Connection conn) throws BaseException {
		Collection<ItemAffectedNotification> items = null;
		
		String queryFilter = "pp."+ATTRIBUTE_MATERIAL_ID+" = "+materialId
				+ " and p."+ATTRIBUTE_ITEM_ID+" = pp."+ATTRIBUTE_ITEM_ID;
		

		
		if (requestApproved 
				&& (category.isMfrOutOfBusiness()
						|| category.isFormulationChange()
						|| category.isMaterialDiscontinuation()
						|| category.isItemDiscontinuation())) {
			queryFilter += " and i.item_type not in ('DU', 'OA')";
		}
		else {
			queryFilter += " and i.item_type not in ('DU', 'OA', 'OB')";
		}
		
		items = getItemsQuery(queryFilter, ", "+PART_TABLE+" pp", newItems, conn);
		
		return items;
	}
	
	public Collection<ItemAffectedNotification> getItemsByItemId(MfrNotificationRequest request, MfrNotificationCategoryBean category, List<ItemAffectedNotification> newItems, Connection conn) throws BaseException {
		Collection<ItemAffectedNotification> itemList = null;
		
		String additionalTables = "";
		String queryFilter = "";
		if (request.getItemId() == null) {
			additionalTables = additionalTables + ", "+MFR_REQUEST_DETAIL_TABLE+" d, "+MFR_REQUEST_CAT_TABLE+" c";
			queryFilter = "i."+ATTRIBUTE_ITEM_ID+" = d."+ATTRIBUTE_ITEM_ID
				+ " and c.notification_category_id = d.notification_category_id"
				+ " and c.mfr_req_category_id = "+request.getMfrReqCategoryId()
				+ " and c.notification_id = "+request.getNotificationId();
		}
		else {
			queryFilter = "i."+ATTRIBUTE_ITEM_ID+" = "+request.getItemId();
		}
		
		if (request.isApproved() 
				&& (category.isMfrOutOfBusiness()
						|| category.isFormulationChange()
						|| category.isMaterialDiscontinuation()
						|| category.isItemDiscontinuation())) {
			queryFilter += " and i.item_type not in ('DU', 'OA')";
		}
		else {
			queryFilter += " and i.item_type not in ('DU', 'OA', 'OB')";
		}
		itemList = getItemsQuery(queryFilter, additionalTables, newItems, conn);
		
		return itemList;
	}
}
