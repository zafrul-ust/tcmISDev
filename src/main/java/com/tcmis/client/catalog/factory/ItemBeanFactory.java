package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.ItemBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: ItemBeanFactory <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class ItemBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
 public String ATTRIBUTE_CATEGORY_ID = "CATEGORY_ID";
 public String ATTRIBUTE_REVISION_COMMENTS = "REVISION_COMMENTS";
 public String ATTRIBUTE_REPLACEMENT_ITEM = "REPLACEMENT_ITEM";
 public String ATTRIBUTE_STOCKING_TYPE = "STOCKING_TYPE";
 public String ATTRIBUTE_MSRP = "MSRP";
 public String ATTRIBUTE_PRICE_UNIT = "PRICE_UNIT";
 public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
 public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
 public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
 public String ATTRIBUTE_SAMPLE_ONLY = "SAMPLE_ONLY";
 public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
 public String ATTRIBUTE_CASE_QTY = "CASE_QTY";
 public String ATTRIBUTE_CT_PETROLEUM_TAX = "CT_PETROLEUM_TAX";
 public String ATTRIBUTE_INSEPARABLE_KIT = "INSEPARABLE_KIT";

 //table name
 public String TABLE = "ITEM";

 //constructor
 public ItemBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("itemDesc")) {
	 return ATTRIBUTE_ITEM_DESC;
	}
	else if (attributeName.equals("categoryId")) {
	 return ATTRIBUTE_CATEGORY_ID;
	}
	else if (attributeName.equals("revisionComments")) {
	 return ATTRIBUTE_REVISION_COMMENTS;
	}
	else if (attributeName.equals("replacementItem")) {
	 return ATTRIBUTE_REPLACEMENT_ITEM;
	}
	else if (attributeName.equals("stockingType")) {
	 return ATTRIBUTE_STOCKING_TYPE;
	}
	else if (attributeName.equals("msrp")) {
	 return ATTRIBUTE_MSRP;
	}
	else if (attributeName.equals("priceUnit")) {
	 return ATTRIBUTE_PRICE_UNIT;
	}
	else if (attributeName.equals("storageTemp")) {
	 return ATTRIBUTE_STORAGE_TEMP;
	}
	else if (attributeName.equals("shelfLifeDays")) {
	 return ATTRIBUTE_SHELF_LIFE_DAYS;
	}
	else if (attributeName.equals("shelfLifeBasis")) {
	 return ATTRIBUTE_SHELF_LIFE_BASIS;
	}
	else if (attributeName.equals("sampleOnly")) {
	 return ATTRIBUTE_SAMPLE_ONLY;
	}
	else if (attributeName.equals("itemType")) {
	 return ATTRIBUTE_ITEM_TYPE;
	}
	else if (attributeName.equals("caseQty")) {
	 return ATTRIBUTE_CASE_QTY;
	}
	else if (attributeName.equals("ctPetroleumTax")) {
	 return ATTRIBUTE_CT_PETROLEUM_TAX;
	}
	else if (attributeName.equals("inseparableKit")) {
	 return ATTRIBUTE_INSEPARABLE_KIT;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, ItemBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(ItemBean itemBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
		"" + itemBean.getItemId());
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
	public int delete(ItemBean itemBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
		"" + itemBean.getItemId());
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
	public int insert(ItemBean itemBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(itemBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(ItemBean itemBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_ITEM_DESC + "," +
		ATTRIBUTE_CATEGORY_ID + "," +
		ATTRIBUTE_REVISION_COMMENTS + "," +
		ATTRIBUTE_REPLACEMENT_ITEM + "," +
		ATTRIBUTE_STOCKING_TYPE + "," +
		ATTRIBUTE_MSRP + "," +
		ATTRIBUTE_PRICE_UNIT + "," +
		ATTRIBUTE_STORAGE_TEMP + "," +
		ATTRIBUTE_SHELF_LIFE_DAYS + "," +
		ATTRIBUTE_SHELF_LIFE_BASIS + "," +
		ATTRIBUTE_SAMPLE_ONLY + "," +
		ATTRIBUTE_ITEM_TYPE + "," +
		ATTRIBUTE_CASE_QTY + "," +
		ATTRIBUTE_CT_PETROLEUM_TAX + "," +
		ATTRIBUTE_INSEPARABLE_KIT + ")" +
		" values (" +
		itemBean.getItemId() + "," +
		SqlHandler.delimitString(itemBean.getItemDesc()) + "," +
		itemBean.getCategoryId() + "," +
		SqlHandler.delimitString(itemBean.getRevisionComments()) + "," +
		itemBean.getReplacementItem() + "," +
		SqlHandler.delimitString(itemBean.getStockingType()) + "," +
		itemBean.getMsrp() + "," +
		SqlHandler.delimitString(itemBean.getPriceUnit()) + "," +
		SqlHandler.delimitString(itemBean.getStorageTemp()) + "," +
		itemBean.getShelfLifeDays() + "," +
		SqlHandler.delimitString(itemBean.getShelfLifeBasis()) + "," +
		SqlHandler.delimitString(itemBean.getSampleOnly()) + "," +
		SqlHandler.delimitString(itemBean.getItemType()) + "," +
		itemBean.getCaseQty() + "," +
		SqlHandler.delimitString(itemBean.getCtPetroleumTax()) + "," +
		SqlHandler.delimitString(itemBean.getInseparableKit()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(ItemBean itemBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(itemBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(ItemBean itemBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_ITEM_ID + "=" +
		 StringHandler.nullIfZero(itemBean.getItemId()) + "," +
		ATTRIBUTE_ITEM_DESC + "=" +
		 SqlHandler.delimitString(itemBean.getItemDesc()) + "," +
		ATTRIBUTE_CATEGORY_ID + "=" +
		 StringHandler.nullIfZero(itemBean.getCategoryId()) + "," +
		ATTRIBUTE_REVISION_COMMENTS + "=" +
		 SqlHandler.delimitString(itemBean.getRevisionComments()) + "," +
		ATTRIBUTE_REPLACEMENT_ITEM + "=" +
		 StringHandler.nullIfZero(itemBean.getReplacementItem()) + "," +
		ATTRIBUTE_STOCKING_TYPE + "=" +
		 SqlHandler.delimitString(itemBean.getStockingType()) + "," +
		ATTRIBUTE_MSRP + "=" +
		 StringHandler.nullIfZero(itemBean.getMsrp()) + "," +
		ATTRIBUTE_PRICE_UNIT + "=" +
		 SqlHandler.delimitString(itemBean.getPriceUnit()) + "," +
		ATTRIBUTE_STORAGE_TEMP + "=" +
		 SqlHandler.delimitString(itemBean.getStorageTemp()) + "," +
		ATTRIBUTE_SHELF_LIFE_DAYS + "=" +
		 StringHandler.nullIfZero(itemBean.getShelfLifeDays()) + "," +
		ATTRIBUTE_SHELF_LIFE_BASIS + "=" +
		 SqlHandler.delimitString(itemBean.getShelfLifeBasis()) + "," +
		ATTRIBUTE_SAMPLE_ONLY + "=" +
		 SqlHandler.delimitString(itemBean.getSampleOnly()) + "," +
		ATTRIBUTE_ITEM_TYPE + "=" +
		 SqlHandler.delimitString(itemBean.getItemType()) + "," +
		ATTRIBUTE_CASE_QTY + "=" +
		 StringHandler.nullIfZero(itemBean.getCaseQty()) + "," +
		ATTRIBUTE_CT_PETROLEUM_TAX + "=" +
		 SqlHandler.delimitString(itemBean.getCtPetroleumTax()) + "," +
		ATTRIBUTE_INSEPARABLE_KIT + "=" +
		 SqlHandler.delimitString(itemBean.getInseparableKit()) + " " +
		"where " + ATTRIBUTE_ITEM_ID + "=" +
		 itemBean.getItemId();
	 return new SqlManager().update(conn, query);
	}
	*/

 //select
 public Collection select(SearchCriteria criteria) throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = select(criteria, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection itemBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ItemBean itemBean = new ItemBean();
	 load(dataSetRow, itemBean);
	 itemBeanColl.add(itemBean);
	}

	return itemBeanColl;
 }

 public Collection select(String query, Connection conn) throws BaseException {

	Collection itemBeanColl = new Vector();

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ItemBean itemBean = new ItemBean();
	 load(dataSetRow, itemBean);
	 itemBeanColl.add(itemBean);
	}

        this.getDbManager().returnConnection(conn);
        
	return itemBeanColl;
 }
 
 public Collection select(String query) throws BaseException {

		Collection itemBeanColl = new Vector();
		Connection connection = null;
		
		try {
	        connection = this.getDbManager().getConnection();

			DataSet dataSet = new SqlManager().select(connection, query);
	
			Iterator dataIter = dataSet.iterator();
	
			while (dataIter.hasNext()) {
				 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
				 ItemBean itemBean = new ItemBean();
				 load(dataSetRow, itemBean);
				 itemBeanColl.add(itemBean);
			}
		} finally {
			this.getDbManager().returnConnection(connection);
		}
	        
		return itemBeanColl;
 }

}