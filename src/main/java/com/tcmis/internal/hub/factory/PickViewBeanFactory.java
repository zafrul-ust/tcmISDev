package com.tcmis.internal.hub.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.hub.beans.PickViewBean;


/******************************************************************************
 * CLASSNAME: PickViewBeanFactory <br>
 * @version: 1.0, Mar 15, 2007 <br>
 *****************************************************************************/


public class PickViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_MR_LINE = "MR_LINE";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_TOTAL_QTY = "TOTAL_QTY";
	public String ATTRIBUTE_PICK_QTY = "PICK_QTY";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_CAT_PART_DESCRIPTION = "CAT_PART_DESCRIPTION";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_INVENTORY_QUANTITY = "INVENTORY_QUANTITY";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
	public String ATTRIBUTE_CONSIGNED_FLAG = "CONSIGNED_FLAG";
	public String ATTRIBUTE_CERTIFICATION_DATE = "CERTIFICATION_DATE";
	public String ATTRIBUTE_PICKABLE = "PICKABLE";
	public String ATTRIBUTE_CERTIFIED_BY = "CERTIFIED_BY";
	public String ATTRIBUTE_CERTIFICATION_NUMBER = "CERTIFICATION_NUMBER";
	public String ATTRIBUTE_QUALITY_CONTROL_ITEM = "QUALITY_CONTROL_ITEM";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";

	//table name
	public String TABLE = "PICK_VIEW";


	//constructor
	public PickViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("totalQty")) {
			return ATTRIBUTE_TOTAL_QTY;
		}
		else if(attributeName.equals("pickQty")) {
			return ATTRIBUTE_PICK_QTY;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("catPartDescription")) {
			return ATTRIBUTE_CAT_PART_DESCRIPTION;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("inventoryQuantity")) {
			return ATTRIBUTE_INVENTORY_QUANTITY;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if(attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if(attributeName.equals("transferRequestId")) {
			return ATTRIBUTE_TRANSFER_REQUEST_ID;
		}
		else if(attributeName.equals("consignedFlag")) {
			return ATTRIBUTE_CONSIGNED_FLAG;
		}
		else if(attributeName.equals("certificationDate")) {
			return ATTRIBUTE_CERTIFICATION_DATE;
		}
		else if(attributeName.equals("pickable")) {
			return ATTRIBUTE_PICKABLE;
		}
		else if(attributeName.equals("certifiedBy")) {
			return ATTRIBUTE_CERTIFIED_BY;
		}
		else if(attributeName.equals("certificationNumber")) {
			return ATTRIBUTE_CERTIFICATION_NUMBER;
		}
		else if(attributeName.equals("qualityControlItem")) {
			return ATTRIBUTE_QUALITY_CONTROL_ITEM;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PickViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PickViewBean pickViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + pickViewBean.getHub());

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


	public int delete(PickViewBean pickViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + pickViewBean.getHub());

		return delete(criteria, conn);
	}
*/

	public int delete(SearchCriteria criteria)
		throws BaseException {

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


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//insert
	public int insert(PickViewBean pickViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(pickViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PickViewBean pickViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_MR_LINE + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_BIN + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_TOTAL_QTY + "," +
			ATTRIBUTE_PICK_QTY + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_CAT_PART_DESCRIPTION + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_INVENTORY_QUANTITY + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_FAC_PART_NO + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_DELIVERY_POINT + "," +
			ATTRIBUTE_REQUESTOR + "," +
			ATTRIBUTE_TRANSFER_REQUEST_ID + "," +
			ATTRIBUTE_CONSIGNED_FLAG + "," +
			ATTRIBUTE_CERTIFICATION_DATE + "," +
			ATTRIBUTE_PICKABLE + "," +
			ATTRIBUTE_CERTIFIED_BY + "," +
			ATTRIBUTE_CERTIFICATION_NUMBER + "," +
			ATTRIBUTE_QUALITY_CONTROL_ITEM + "," +
			ATTRIBUTE_INVENTORY_GROUP + ")" +
 values (
			SqlHandler.delimitString(pickViewBean.getHub()) + "," +
			StringHandler.nullIfZero(pickViewBean.getPrNumber()) + "," +
			SqlHandler.delimitString(pickViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(pickViewBean.getMrLine()) + "," +
			StringHandler.nullIfZero(pickViewBean.getReceiptId()) + "," +
			StringHandler.nullIfZero(pickViewBean.getItemId()) + "," +
			SqlHandler.delimitString(pickViewBean.getBin()) + "," +
			SqlHandler.delimitString(pickViewBean.getMfgLot()) + "," +
			DateHandler.getOracleToDateFunction(pickViewBean.getExpireDate()) + "," +
			StringHandler.nullIfZero(pickViewBean.getTotalQty()) + "," +
			StringHandler.nullIfZero(pickViewBean.getPickQty()) + "," +
			SqlHandler.delimitString(pickViewBean.getApplication()) + "," +
			SqlHandler.delimitString(pickViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(pickViewBean.getItemType()) + "," +
			SqlHandler.delimitString(pickViewBean.getPartDescription()) + "," +
			SqlHandler.delimitString(pickViewBean.getCatPartDescription()) + "," +
			SqlHandler.delimitString(pickViewBean.getPackaging()) + "," +
			StringHandler.nullIfZero(pickViewBean.getInventoryQuantity()) + "," +
			SqlHandler.delimitString(pickViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(pickViewBean.getFacPartNo()) + "," +
			StringHandler.nullIfZero(pickViewBean.getPartGroupNo()) + "," +
			SqlHandler.delimitString(pickViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(pickViewBean.getDeliveryPoint()) + "," +
			SqlHandler.delimitString(pickViewBean.getRequestor()) + "," +
			StringHandler.nullIfZero(pickViewBean.getTransferRequestId()) + "," +
			SqlHandler.delimitString(pickViewBean.getConsignedFlag()) + "," +
			DateHandler.getOracleToDateFunction(pickViewBean.getCertificationDate()) + "," +
			SqlHandler.delimitString(pickViewBean.getPickable()) + "," +
			StringHandler.nullIfZero(pickViewBean.getCertifiedBy()) + "," +
			SqlHandler.delimitString(pickViewBean.getCertificationNumber()) + "," +
			SqlHandler.delimitString(pickViewBean.getQualityControlItem()) + "," +
			SqlHandler.delimitString(pickViewBean.getInventoryGroup()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PickViewBean pickViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(pickViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PickViewBean pickViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_HUB + "=" +
				SqlHandler.delimitString(pickViewBean.getHub()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" +
				StringHandler.nullIfZero(pickViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" +
				SqlHandler.delimitString(pickViewBean.getLineItem()) + "," +
			ATTRIBUTE_MR_LINE + "=" +
				SqlHandler.delimitString(pickViewBean.getMrLine()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" +
				StringHandler.nullIfZero(pickViewBean.getReceiptId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(pickViewBean.getItemId()) + "," +
			ATTRIBUTE_BIN + "=" +
				SqlHandler.delimitString(pickViewBean.getBin()) + "," +
			ATTRIBUTE_MFG_LOT + "=" +
				SqlHandler.delimitString(pickViewBean.getMfgLot()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" +
				DateHandler.getOracleToDateFunction(pickViewBean.getExpireDate()) + "," +
			ATTRIBUTE_TOTAL_QTY + "=" +
				StringHandler.nullIfZero(pickViewBean.getTotalQty()) + "," +
			ATTRIBUTE_PICK_QTY + "=" +
				StringHandler.nullIfZero(pickViewBean.getPickQty()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(pickViewBean.getApplication()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(pickViewBean.getFacilityId()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" +
				SqlHandler.delimitString(pickViewBean.getItemType()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" +
				SqlHandler.delimitString(pickViewBean.getPartDescription()) + "," +
			ATTRIBUTE_CAT_PART_DESCRIPTION + "=" +
				SqlHandler.delimitString(pickViewBean.getCatPartDescription()) + "," +
			ATTRIBUTE_PACKAGING + "=" +
				SqlHandler.delimitString(pickViewBean.getPackaging()) + "," +
			ATTRIBUTE_INVENTORY_QUANTITY + "=" +
				StringHandler.nullIfZero(pickViewBean.getInventoryQuantity()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(pickViewBean.getCatalogId()) + "," +
			ATTRIBUTE_FAC_PART_NO + "=" +
				SqlHandler.delimitString(pickViewBean.getFacPartNo()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" +
				StringHandler.nullIfZero(pickViewBean.getPartGroupNo()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(pickViewBean.getCompanyId()) + "," +
			ATTRIBUTE_DELIVERY_POINT + "=" +
				SqlHandler.delimitString(pickViewBean.getDeliveryPoint()) + "," +
			ATTRIBUTE_REQUESTOR + "=" +
				SqlHandler.delimitString(pickViewBean.getRequestor()) + "," +
			ATTRIBUTE_TRANSFER_REQUEST_ID + "=" +
				StringHandler.nullIfZero(pickViewBean.getTransferRequestId()) + "," +
			ATTRIBUTE_CONSIGNED_FLAG + "=" +
				SqlHandler.delimitString(pickViewBean.getConsignedFlag()) + "," +
			ATTRIBUTE_CERTIFICATION_DATE + "=" +
				DateHandler.getOracleToDateFunction(pickViewBean.getCertificationDate()) + "," +
			ATTRIBUTE_PICKABLE + "=" +
				SqlHandler.delimitString(pickViewBean.getPickable()) + "," +
			ATTRIBUTE_CERTIFIED_BY + "=" +
				StringHandler.nullIfZero(pickViewBean.getCertifiedBy()) + "," +
			ATTRIBUTE_CERTIFICATION_NUMBER + "=" +
				SqlHandler.delimitString(pickViewBean.getCertificationNumber()) + "," +
			ATTRIBUTE_QUALITY_CONTROL_ITEM + "=" +
				SqlHandler.delimitString(pickViewBean.getQualityControlItem()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(pickViewBean.getInventoryGroup()) + " " +
			"where " + ATTRIBUTE_HUB + "=" +
				StringHandler.nullIfZero(pickViewBean.getHub());

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection pickViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PickViewBean pickViewBean = new PickViewBean();
			load(dataSetRow, pickViewBean);
			pickViewBeanColl.add(pickViewBean);
		}

		return pickViewBeanColl;
	}

   public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
      Connection connection = null;
      Collection c = null;
      try {
         connection = this.getDbManager().getConnection();
         c = select(criteria, sortCriteria, connection);
      }
      finally {
         this.getDbManager().returnConnection(connection);
      }
      return c;
   }

   public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {
      Collection pickViewBeanColl = new Vector();
      String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);
      DataSet dataSet = new SqlManager().select(conn, query);
      Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow) dataIter.next();
         PickViewBean pickViewBean = new PickViewBean();
         load(dataSetRow, pickViewBean);
         pickViewBeanColl.add(pickViewBean);
      }
      return pickViewBeanColl;
   }

   public Collection issueInsert(Collection inArgs) throws BaseException {
     Collection outArgs = new Vector(2);
     outArgs.add(new Integer(java.sql.Types.NUMERIC));
     outArgs.add(new Integer(java.sql.Types.VARCHAR));
     GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
     Connection conn = this.getDbManager().getConnection();
     return procFactory.doProcedure(conn, "p_issue_insert", inArgs, outArgs);
   }

   public Collection lineItemAllocate(Collection inArgs) throws BaseException {
     GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
     Connection conn = this.getDbManager().getConnection();
     procFactory.doProcedure(conn, "p_line_item_allocate", inArgs);
     this.getDbManager().returnConnection(conn);
     return new Vector();
   }
}