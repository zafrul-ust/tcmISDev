package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.InventoryInputBean;
import com.tcmis.client.catalog.beans.PkgInventoryDetailWebPrInventoryBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/**
 * ***************************************************************************
 * CLASSNAME: PkgInvDetInventoryViewBeanFactory <br>
 *
 * @version: 1.0, May 13, 2005 <br>
 * ***************************************************************************
 */

public class PkgInventoryDetailWebPrInventoryBeanFactory
		extends BaseBeanFactory {

Log log = LogFactory.getLog(this.getClass());

//column names
public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
public String ATTRIBUTE_STOCKING_METHOD = "STOCKING_METHOD";
public String ATTRIBUTE_SET_POINTS = "SET_POINTS";
public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
public String ATTRIBUTE_ITEMS_PER_PART = "ITEMS_PER_PART";
public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
public String ATTRIBUTE_QTY_AVAILABLE = "QTY_AVAILABLE";
public String ATTRIBUTE_QTY_HELD = "QTY_HELD";
public String ATTRIBUTE_QTY_ON_ORDER = "QTY_ON_ORDER";
public String ATTRIBUTE_QTY_IN_PURCHASING = "QTY_IN_PURCHASING";
public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
public String ATTRIBUTE_PACKAGING = "PACKAGING";
public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
public String ATTRIBUTE_MSDS_ON_LINE = "MSDS_ON_LINE";
public String ATTRIBUTE_ITEM_PACKAGING = "ITEM_PACKAGING";
public String ATTRIBUTE_STOCKING_METHOD_ORIG = "STOCKING_METHOD_ORIG";
public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
public String ATTRIBUTE_REORDER_QUANTITY = "REORDER_QUANTITY";
public String ATTRIBUTE_LAST_COUNT_DATE = "LAST_COUNT_DATE";
public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

//table name
public String TABLE = "PKG_INV_DET_INVENTORY_VIEW";

//constructor
public PkgInventoryDetailWebPrInventoryBeanFactory(DbManager dbManager) {
	super(dbManager);
}

//get column names
public String getColumnName(String attributeName) {
	if (attributeName.equals("catalogId")) {
		return ATTRIBUTE_CATALOG_ID;
	} else if (attributeName.equals("catPartNo")) {
		return ATTRIBUTE_CAT_PART_NO;
	} else if (attributeName.equals("partGroupNo")) {
		return ATTRIBUTE_PART_GROUP_NO;
	} else if (attributeName.equals("partDescription")) {
		return ATTRIBUTE_PART_DESCRIPTION;
	} else if (attributeName.equals("stockingMethod")) {
		return ATTRIBUTE_STOCKING_METHOD;
	} else if (attributeName.equals("setPoints")) {
		return ATTRIBUTE_SET_POINTS;
	} else if (attributeName.equals("inventoryGroup")) {
		return ATTRIBUTE_INVENTORY_GROUP;
	} else if (attributeName.equals("inventoryGroupName")) {
		return ATTRIBUTE_INVENTORY_GROUP_NAME;
	} else if (attributeName.equals("itemsPerPart")) {
		return ATTRIBUTE_ITEMS_PER_PART;
	} else if (attributeName.equals("itemId")) {
		return ATTRIBUTE_ITEM_ID;
	} else if (attributeName.equals("qtyAvailable")) {
		return ATTRIBUTE_QTY_AVAILABLE;
	} else if (attributeName.equals("qtyHeld")) {
		return ATTRIBUTE_QTY_HELD;
	} else if (attributeName.equals("qtyOnOrder")) {
		return ATTRIBUTE_QTY_ON_ORDER;
	} else if (attributeName.equals("qtyInPurchasing")) {
		return ATTRIBUTE_QTY_IN_PURCHASING;
	} else if (attributeName.equals("materialId")) {
		return ATTRIBUTE_MATERIAL_ID;
	} else if (attributeName.equals("materialDesc")) {
		return ATTRIBUTE_MATERIAL_DESC;
	} else if (attributeName.equals("mfgDesc")) {
		return ATTRIBUTE_MFG_DESC;
	} else if (attributeName.equals("packaging")) {
		return ATTRIBUTE_PACKAGING;
	} else if (attributeName.equals("mfgPartNo")) {
		return ATTRIBUTE_MFG_PART_NO;
	} else if (attributeName.equals("msdsOnLine")) {
		return ATTRIBUTE_MSDS_ON_LINE;
	} else if (attributeName.equals("itemPackaging")) {
		return ATTRIBUTE_ITEM_PACKAGING;
	} else if (attributeName.equals("stockingMethodOrig")) {
		return ATTRIBUTE_STOCKING_METHOD_ORIG;
	} else if (attributeName.equals("reorderPoint")) {
		return ATTRIBUTE_REORDER_POINT;
	} else if (attributeName.equals("stockingLevel")) {
		return ATTRIBUTE_STOCKING_LEVEL;
	} else if (attributeName.equals("reorderQuantity")) {
		return ATTRIBUTE_REORDER_QUANTITY;
	} else if (attributeName.equals("lastCountDate")) {
		return ATTRIBUTE_LAST_COUNT_DATE;
	} else if (attributeName.equals("issueGeneration")) {
		return ATTRIBUTE_ISSUE_GENERATION;
	} else if (attributeName.equals("catalogCompanyId")) {
		return ATTRIBUTE_CATALOG_COMPANY_ID;
	} else {
		return super.getColumnName(attributeName);
	}
}

//get column types
public int getType(String attributeName) {
	return super.getType(attributeName, PkgInventoryDetailWebPrInventoryBean.class);
}

//you need to verify the primary key(s) before uncommenting this
/*
//delete
 public int delete(PkgInvDetInventoryViewBean pkgInvDetInventoryViewBean)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
	 "" + pkgInvDetInventoryViewBean.getCatalogId());
	Connection connection = null;
	int result = 0;
	try {
	 connection = this.getDbManager().getConnection();
	 result = this.delete(criteria, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
 }
 public int delete(PkgInvDetInventoryViewBean pkgInvDetInventoryViewBean, Connection conn)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
	 "" + pkgInvDetInventoryViewBean.getCatalogId());
	return delete(criteria, conn);
 }
 */

public int delete(SearchCriteria criteria) throws BaseException {

	Connection connection = null;
	int result = 0;
	try {
		connection = getDbManager().getConnection();
		result = delete(criteria, connection);
	}
	finally {
		this.getDbManager().returnConnection(connection);
	}
	return result;
}

public int delete(SearchCriteria criteria,
                  Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
}

//you need to verify the primary key(s) before uncommenting this
/*
//insert
 public int insert(PkgInvDetInventoryViewBean pkgInvDetInventoryViewBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = insert(pkgInvDetInventoryViewBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
 }
 public int insert(PkgInvDetInventoryViewBean pkgInvDetInventoryViewBean, Connection conn)
	throws BaseException {
	SqlManager sqlManager = new SqlManager();
	String query  =
	 "insert into " + TABLE + " (" +
	 ATTRIBUTE_CATALOG_ID + "," +
	 ATTRIBUTE_CAT_PART_NO + "," +
	 ATTRIBUTE_PART_GROUP_NO + "," +
	 ATTRIBUTE_PART_DESCRIPTION + "," +
	 ATTRIBUTE_STOCKING_METHOD + "," +
	 ATTRIBUTE_SET_POINTS + "," +
	 ATTRIBUTE_INVENTORY_GROUP + "," +
	 ATTRIBUTE_ITEMS_PER_PART + "," +
	 ATTRIBUTE_ITEM_ID + "," +
	 ATTRIBUTE_QTY_AVAILABLE + "," +
	 ATTRIBUTE_QTY_HELD + "," +
	 ATTRIBUTE_QTY_ON_ORDER + "," +
	 ATTRIBUTE_QTY_IN_PURCHASING + "," +
	 ATTRIBUTE_MATERIAL_ID + "," +
	 ATTRIBUTE_MATERIAL_DESC + "," +
	 ATTRIBUTE_MFG_DESC + "," +
	 ATTRIBUTE_PACKAGING + "," +
	 ATTRIBUTE_MFG_PART_NO + "," +
	 ATTRIBUTE_MSDS_ON_LINE + ")" +
	 " values (" +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getCatalogId()) + "," +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getCatPartNo()) + "," +
	 pkgInvDetInventoryViewBean.getPartGroupNo() + "," +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getPartDescription()) + "," +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getStockingMethod()) + "," +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getSetPoints()) + "," +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getInventoryGroup()) + "," +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getItemsPerPart()) + "," +
	 pkgInvDetInventoryViewBean.getItemId() + "," +
	 pkgInvDetInventoryViewBean.getQtyAvailable() + "," +
	 pkgInvDetInventoryViewBean.getQtyHeld() + "," +
	 pkgInvDetInventoryViewBean.getQtyOnOrder() + "," +
	 pkgInvDetInventoryViewBean.getQtyInPurchasing() + "," +
	 pkgInvDetInventoryViewBean.getMaterialId() + "," +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMaterialDesc()) + "," +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMfgDesc()) + "," +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getPackaging()) + "," +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMfgPartNo()) + "," +
	 SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMsdsOnLine()) + ")";
	return sqlManager.update(conn, query);
 }
//update
 public int update(PkgInvDetInventoryViewBean pkgInvDetInventoryViewBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = update(pkgInvDetInventoryViewBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
 }
 public int update(PkgInvDetInventoryViewBean pkgInvDetInventoryViewBean, Connection conn)
	throws BaseException {
	String query  = "update " + TABLE + " set " +
	 ATTRIBUTE_CATALOG_ID + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getCatalogId()) + "," +
	 ATTRIBUTE_CAT_PART_NO + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getCatPartNo()) + "," +
	 ATTRIBUTE_PART_GROUP_NO + "=" +
	StringHandler.nullIfZero(pkgInvDetInventoryViewBean.getPartGroupNo()) + "," +
	 ATTRIBUTE_PART_DESCRIPTION + "=" +
		SqlHandler.delimitString(pkgInvDetInventoryViewBean.getPartDescription()) + "," +
	 ATTRIBUTE_STOCKING_METHOD + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getStockingMethod()) + "," +
	 ATTRIBUTE_SET_POINTS + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getSetPoints()) + "," +
	 ATTRIBUTE_INVENTORY_GROUP + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getInventoryGroup()) + "," +
	 ATTRIBUTE_ITEMS_PER_PART + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getItemsPerPart()) + "," +
	 ATTRIBUTE_ITEM_ID + "=" +
		StringHandler.nullIfZero(pkgInvDetInventoryViewBean.getItemId()) + "," +
	 ATTRIBUTE_QTY_AVAILABLE + "=" +
	StringHandler.nullIfZero(pkgInvDetInventoryViewBean.getQtyAvailable()) + "," +
	 ATTRIBUTE_QTY_HELD + "=" +
		StringHandler.nullIfZero(pkgInvDetInventoryViewBean.getQtyHeld()) + "," +
	 ATTRIBUTE_QTY_ON_ORDER + "=" +
	StringHandler.nullIfZero(pkgInvDetInventoryViewBean.getQtyOnOrder()) + "," +
	 ATTRIBUTE_QTY_IN_PURCHASING + "=" +
		StringHandler.nullIfZero(pkgInvDetInventoryViewBean.getQtyInPurchasing()) + "," +
	 ATTRIBUTE_MATERIAL_ID + "=" +
	StringHandler.nullIfZero(pkgInvDetInventoryViewBean.getMaterialId()) + "," +
	 ATTRIBUTE_MATERIAL_DESC + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMaterialDesc()) + "," +
	 ATTRIBUTE_MFG_DESC + "=" +
		SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMfgDesc()) + "," +
	 ATTRIBUTE_PACKAGING + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getPackaging()) + "," +
	 ATTRIBUTE_MFG_PART_NO + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMfgPartNo()) + "," +
	 ATTRIBUTE_MSDS_ON_LINE + "=" +
	SqlHandler.delimitString(pkgInvDetInventoryViewBean.getMsdsOnLine()) + " " +
	 "where " + ATTRIBUTE_CATALOG_ID + "=" +
		pkgInvDetInventoryViewBean.getCatalogId();
	return new SqlManager().update(conn, query);
 }
 */

public Collection select(InventoryInputBean bean) throws BaseException {
	Connection connection = this.getDbManager().getConnection();
	log.info("Search getSearchText " + bean.getSearchText() + "  getFacility " +
			bean.getFacilityId() + "  getInventory " + bean.getInventory() +
			"  getExpiresWithin " + bean.getExpiresWithin() + "  getExpiresAfter " +
			bean.getExpiresAfter() + "");
	Collection cin = new Vector();
	if (bean.getSearchText() != null && bean.getSearchText().length() > 0) {
		cin.add(new String(bean.getSearchText().trim()));
	} else {
		cin.add("");
	}

	if (bean.getFacilityId() != null && bean.getFacilityId().trim().length() > 0) {
		cin.add(new String(bean.getFacilityId()));
	} else {
		cin.add("");
	}

	if (bean.getInventory() != null && bean.getInventory().trim().length() > 0 &&
			!"All".equalsIgnoreCase(bean.getInventory())) {
		cin.add(new String(bean.getInventory()));
	} else {
		cin.add(new String("ALL"));
	}

	if (bean.getCollection() != null && bean.getCollection().trim().length() > 0) {
		cin.add(new String(bean.getCollection()));
	} else {
		cin.add("");
	}

	/*if (bean.getOnHand() != null &&
	 bean.getOnHand().trim().length() > 0)*/
	{
		if (bean.getExpiresAfter() != null) {
			cin.add(bean.getExpiresAfter());
		} else {
			cin.add("");
		}

		if (bean.getExpiresWithin() != null) {
			cin.add(bean.getExpiresWithin());
		} else {
			cin.add("");
		}
	}
	/*else
		{
		cin.add("");
		cin.add("");
		}*/

	/*if (bean.getOnOrder() != null &&
	 bean.getOnOrder().trim().length() > 0)*/
	{
		if (bean.getArrivesWithin() != null) {
			cin.add(bean.getArrivesWithin());
		} else {
			cin.add("");
		}
	}
	/*else {
		cin.add("");
		}*/

	Collection cout = new Vector();
	cout.add(new Integer(java.sql.Types.VARCHAR));
	Collection result = this.getDbManager().doProcedure(
			"PKG_INVENTORY_DETAIL_WEB.PR_INVENTORY", cin, cout);

	Iterator i11 = result.iterator();
	String searchQuery = "";
	while (i11.hasNext()) {
		searchQuery = (String) i11.next();
		;
	}

	Collection c = select(searchQuery, connection);
	this.getDbManager().returnConnection(connection);
	return c;
}

public Collection select(String query, Connection conn) throws BaseException {

	Collection pkgOrderTrackPrOrderTrackBeanColl = new Vector();

	/*String query = "select * from " + TABLE + " " +
	 getWhereClause(criteria);*/

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
		DataSetRow dataSetRow = (DataSetRow) dataIter.next();
		PkgInventoryDetailWebPrInventoryBean pkgInventoryDetailWebPrInventoryBean = new
				PkgInventoryDetailWebPrInventoryBean();
		load(dataSetRow, pkgInventoryDetailWebPrInventoryBean);
		pkgOrderTrackPrOrderTrackBeanColl.add(pkgInventoryDetailWebPrInventoryBean);
	}

	return pkgOrderTrackPrOrderTrackBeanColl;
}

}