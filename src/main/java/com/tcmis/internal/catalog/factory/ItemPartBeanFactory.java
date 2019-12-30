package com.tcmis.internal.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.catalog.beans.ItemPartBean;

/******************************************************************************
 * CLASSNAME: ItemPartBeanFactory <br>
 * @version: 1.0, 1/22/2008 <br>
 *****************************************************************************/

public class ItemPartBeanFactory
extends BaseBeanFactory
{

	Log log = LogFactory.getLog(this.getClass());

	// fields from Item table:

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

	// fields from Part table:

	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	//public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_GRADE = "GRADE";
	public String ATTRIBUTE_PKG_STYLE = "PKG_STYLE";
	public String ATTRIBUTE_PART_SIZE = "PART_SIZE";
	public String ATTRIBUTE_SIZE_UNIT = "SIZE_UNIT";
	public String ATTRIBUTE_TECH_SPEC = "TECH_SPEC";
	public String ATTRIBUTE_REMARK = "REMARK";
	public String ATTRIBUTE_AIRCRAFT = "AIRCRAFT";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_PACKING_INSTR_CODE = "PACKING_INSTR_CODE";
	//public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
	public String ATTRIBUTE_PART_DESC = "PART_DESC";
	public String ATTRIBUTE_NET_WT_UNIT = "NET_WT_UNIT";
	public String ATTRIBUTE_NET_WT = "NET_WT";
	//public String ATTRIBUTE_CASE_QTY = "CASE_QTY";
	public String ATTRIBUTE_DIMENSION = "DIMENSION";
	public String ATTRIBUTE_RECERT = "RECERT";
	//public String ATTRIBUTE_STOCKING_TYPE = "STOCKING_TYPE";
	public String ATTRIBUTE_SIZE_VERIFIED = "SIZE_VERIFIED";
	public String ATTRIBUTE_COMPONENTS_PER_ITEM = "COMPONENTS_PER_ITEM";
	public String ATTRIBUTE_SIZE_VARIES = "SIZE_VARIES";
	public String ATTRIBUTE_ITEM_VERIFIED = "ITEM_VERIFIED";
	public String ATTRIBUTE_ITEM_VERIFIED_BY = "ITEM_VERIFIED_BY";
	public String ATTRIBUTE_DATE_ITEM_VERIFIED = "DATE_ITEM_VERIFIED";
	//public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
	//public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
	public String ATTRIBUTE_ORM_D = "ORM_D";
	public String ATTRIBUTE_PKG_GT_RQ = "PKG_GT_RQ";
	public String ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT = "BULK_PKG_MARINE_POLLUTANT";
	public String ATTRIBUTE_SHIP_CHANGE_ID = "SHIP_CHANGE_ID";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_MIN_TEMP = "MIN_TEMP";
	public String ATTRIBUTE_MAX_TEMP = "MAX_TEMP";
	public String ATTRIBUTE_TEMP_UNITS = "TEMP_UNITS";
	public String ATTRIBUTE_COMPONENT_ITEM_ID = "COMPONENT_ITEM_ID";
	public String ATTRIBUTE_TEMP_VERIFIED = "TEMP_VERIFIED";
	public String ATTRIBUTE_TEMP_VERIFIED_BY = "TEMP_VERIFIED_BY";
	public String ATTRIBUTE_DATE_TEMP_VERIFIED = "DATE_TEMP_VERIFIED";

	//table name
	public String TABLE = "ITEM";

	//constructor
	public ItemPartBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
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

		// fields from Part table:

		else if(attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		/* else if(attributeName.equals("itemId")) {
		return ATTRIBUTE_ITEM_ID;
	} */
		else if(attributeName.equals("grade")) {
			return ATTRIBUTE_GRADE;
		}
		else if(attributeName.equals("pkgStyle")) {
			return ATTRIBUTE_PKG_STYLE;
		}
		else if(attributeName.equals("partSize")) {
			return ATTRIBUTE_PART_SIZE;
		}
		else if(attributeName.equals("sizeUnit")) {
			return ATTRIBUTE_SIZE_UNIT;
		}
		else if(attributeName.equals("techSpec")) {
			return ATTRIBUTE_TECH_SPEC;
		}
		else if(attributeName.equals("remark")) {
			return ATTRIBUTE_REMARK;
		}
		else if(attributeName.equals("aircraft")) {
			return ATTRIBUTE_AIRCRAFT;
		}
		else if(attributeName.equals("mfgPartNo")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if(attributeName.equals("packingInstrCode")) {
			return ATTRIBUTE_PACKING_INSTR_CODE;
		}
		/* else if(attributeName.equals("storageTemp")) {
		return ATTRIBUTE_STORAGE_TEMP;
	} */
		else if(attributeName.equals("partDesc")) {
			return ATTRIBUTE_PART_DESC;
		}
		else if(attributeName.equals("netWtUnit")) {
			return ATTRIBUTE_NET_WT_UNIT;
		}
		else if(attributeName.equals("netWt")) {
			return ATTRIBUTE_NET_WT;
		}
		/* else if(attributeName.equals("caseQty")) {
		return ATTRIBUTE_CASE_QTY;
	} */
		else if(attributeName.equals("dimension")) {
			return ATTRIBUTE_DIMENSION;
		}
		else if(attributeName.equals("recert")) {
			return ATTRIBUTE_RECERT;
		}
		/* else if(attributeName.equals("stockingType")) {
		return ATTRIBUTE_STOCKING_TYPE;
	} */
		else if(attributeName.equals("sizeVerified")) {
			return ATTRIBUTE_SIZE_VERIFIED;
		}
		else if(attributeName.equals("componentsPerItem")) {
			return ATTRIBUTE_COMPONENTS_PER_ITEM;
		}
		else if(attributeName.equals("sizeVaries")) {
			return ATTRIBUTE_SIZE_VARIES;
		}
		else if(attributeName.equals("itemVerified")) {
			return ATTRIBUTE_ITEM_VERIFIED;
		}
		else if(attributeName.equals("itemVerifiedBy")) {
			return ATTRIBUTE_ITEM_VERIFIED_BY;
		}
		else if(attributeName.equals("dateItemVerified")) {
			return ATTRIBUTE_DATE_ITEM_VERIFIED;
		}
		/*
	else if(attributeName.equals("shelfLifeDays")) {
		return ATTRIBUTE_SHELF_LIFE_DAYS;
	}
	else if(attributeName.equals("shelfLifeBasis")) {
		return ATTRIBUTE_SHELF_LIFE_BASIS;
	}
		 */
		else if(attributeName.equals("ormDd")) {
			return ATTRIBUTE_ORM_D;
		}
		else if(attributeName.equals("pkgGtRq")) {
			return ATTRIBUTE_PKG_GT_RQ;
		}
		else if(attributeName.equals("bulkPkgMarinePollutant")) {
			return ATTRIBUTE_BULK_PKG_MARINE_POLLUTANT;
		}
		else if(attributeName.equals("shipChangeId")) {
			return ATTRIBUTE_SHIP_CHANGE_ID;
		}
		else if(attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if(attributeName.equals("minTemp")) {
			return ATTRIBUTE_MIN_TEMP;
		}
		else if(attributeName.equals("maxTemp")) {
			return ATTRIBUTE_MAX_TEMP;
		}
		else if(attributeName.equals("tempUnits")) {
			return ATTRIBUTE_TEMP_UNITS;
		}
		else if(attributeName.equals("componentItemId")) {
			return ATTRIBUTE_COMPONENT_ITEM_ID;
		}
		else if(attributeName.equals("tempVerified")) {
			return ATTRIBUTE_TEMP_VERIFIED;
		}
		else if(attributeName.equals("tempVerifiedBy")) {
			return ATTRIBUTE_TEMP_VERIFIED_BY;
		}
		else if(attributeName.equals("dateTempVerified")) {
			return ATTRIBUTE_DATE_TEMP_VERIFIED;
		}

		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ItemPartBean.class);
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

	public Collection getItemPartBeanCollection ( String materialId )
	throws BaseException
	{
		Connection connection = null;
		Collection	itemPartBeanCollection = null;
		try
		{
			connection = this.getDbManager().getConnection();
			itemPartBeanCollection = getItemPartBeanCollection ( materialId, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return itemPartBeanCollection;
	}

	public Collection getItemPartBeanCollection ( String materialId, Connection connection )
	throws BaseException
	{
		Collection	itemPartBeanCollection = new Vector();
		// orig StringBuilder queryBuffer = new StringBuilder( "select distinct p.SIZE_VERIFIED, p.STOCKING_TYPE, p.ITEM_VERIFIED, p.item_id item, p.part_id part, p.material_id material, p.GRADE Grade, p.components_per_item ||' x'||' '|| p.PART_SIZE ||' '||p.SIZE_UNIT||' '||p.PKG_STYLE||' '||p.DIMENSION Packaging,   p.SHELF_LIFE_DAYS, p.SHELF_LIFE_BASIS, p.STORAGE_TEMP, p.RECERT, p.SIZE_VARIES, p.MFG_PART_NO MPN, p.NET_WT||' '||p.NET_WT_UNIT Weight, it.CASE_QTY, it.ITEM_TYPE, it.REVISION_COMMENTS ");

		StringBuilder queryBuffer = new StringBuilder( "select distinct p.SIZE_VERIFIED, p.STOCKING_TYPE, p.ITEM_VERIFIED, p.item_id, p.part_id, p.material_id, p.GRADE, p.components_per_item ||' x'||' '|| p.PART_SIZE ||' '||p.SIZE_UNIT||' '||p.PKG_STYLE||' '||p.DIMENSION,   p.SHELF_LIFE_DAYS, p.SHELF_LIFE_BASIS, p.STORAGE_TEMP, p.RECERT, p.SIZE_VARIES, p.MFG_PART_NO, p.NET_WT||' '||p.NET_WT_UNIT, it.CASE_QTY, it.ITEM_TYPE, it.REVISION_COMMENTS ");
		queryBuffer.append( " from global.part p,(select x.item_id from part x ,(select item_id  from part group by item_id  having count(*)=1) y  where x.item_id = y.item_id   and  x.material_id in (");
		queryBuffer.append( materialId );
		queryBuffer.append( ")) z, global.item it where p.item_id = z.item_id and z.item_id = it.item_id and it.item_type in ('MA','OB','MS','MV') and p.STOCKING_TYPE = 'S' order by p.item_id, p.material_id ");

		//queryBuffer.append( requestId );

		DataSet dataSet = new SqlManager().select(connection, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemPartBean itemPartBean = new ItemPartBean();
			load(dataSetRow, itemPartBean);
			itemPartBeanCollection.add( itemPartBean );
		}

		return itemPartBeanCollection;
	}

	public Collection select( String searchArgument )	throws BaseException
	{
		Connection connection = null;
		Collection c = null;
		try
		{
			connection = this.getDbManager().getConnection();
			c = select( searchArgument, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select( String searchArgument, Connection conn)	throws BaseException
	{
		Collection itemPartBeanColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder("select * from item ");
		queryBuffer.append(" where lower( ITEM_DESC ) like lower('%");
		queryBuffer.append(searchArgument);
		queryBuffer.append("%')");
		queryBuffer.append(" or ITEM_ID like '");
		queryBuffer.append(searchArgument);
		queryBuffer.append("' order by ITEM_ID, ITEM_DESC asc ");

		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ItemPartBean itemPartBean = new ItemPartBean();
			load(dataSetRow, itemPartBean);
			itemPartBeanColl.add(itemPartBean);
		}
		return itemPartBeanColl;
	}

}