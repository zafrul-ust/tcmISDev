package com.tcmis.supplier.shipping.factory;


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
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.supplier.shipping.beans.SupplierUsgovLabelViewBean;
import com.tcmis.internal.hub.beans.LabelInputBean;


/******************************************************************************
 * CLASSNAME: SupplierUsgovLabelViewBeanFactory <br>
 * @version: 1.0, Dec 3, 2008 <br>
 *****************************************************************************/


public class SupplierUsgovLabelViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BOX_LABEL_ID = "BOX_LABEL_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_NSN = "NSN";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_CAGE_CODE = "CAGE_CODE";
	public String ATTRIBUTE_FORMATTED_NSN = "FORMATTED_NSN";
	public String ATTRIBUTE_PID_PART_NO = "PID_PART_NO";
	public String ATTRIBUTE_FORMATTED_CUSTOMER_PO = "FORMATTED_CUSTOMER_PO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE = "QUANTITY_IN_UNIT_OF_SALE";
	public String ATTRIBUTE_QUANTITY_IN_UNIT_OF_ISSUE = "QUANTITY_IN_UNIT_OF_ISSUE";
	public String ATTRIBUTE_UNIT_OF_ISSUE = "UNIT_OF_ISSUE";
	public String ATTRIBUTE_GROSS_WEIGHT = "GROSS_WEIGHT";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME = "UN_NUMBER_AND_PROPER_SHIP_NAME";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED = "ORIGIN_INSPECTION_REQUIRED";
	public String ATTRIBUTE_MHM_DATE = "MHM_DATE";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";

  //table name
	public String TABLE = "SUPPLIER_USGOV_LABEL_VIEW";


	//constructor
	public SupplierUsgovLabelViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("boxLabelId")) {
			return ATTRIBUTE_BOX_LABEL_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("nsn")) {
			return ATTRIBUTE_NSN;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("cageCode")) {
			return ATTRIBUTE_CAGE_CODE;
		}
		else if(attributeName.equals("formattedNsn")) {
			return ATTRIBUTE_FORMATTED_NSN;
		}
		else if(attributeName.equals("pidPartNo")) {
			return ATTRIBUTE_PID_PART_NO;
		}
		else if(attributeName.equals("formattedCustomerPo")) {
			return ATTRIBUTE_FORMATTED_CUSTOMER_PO;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("quantityInUnitOfSale")) {
			return ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE;
		}
		else if(attributeName.equals("quantityInUnitOfIssue")) {
			return ATTRIBUTE_QUANTITY_IN_UNIT_OF_ISSUE;
		}
		else if(attributeName.equals("unitOfIssue")) {
			return ATTRIBUTE_UNIT_OF_ISSUE;
		}
		else if(attributeName.equals("grossWeight")) {
			return ATTRIBUTE_GROSS_WEIGHT;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("unNumberAndProperShipName")) {
			return ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("dateOfManufacture")) {
			return ATTRIBUTE_DATE_OF_MANUFACTURE;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("shippedAsSingle")) {
			return ATTRIBUTE_SHIPPED_AS_SINGLE;
		}
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
		else if(attributeName.equals("mhmDate")) {
			return ATTRIBUTE_MHM_DATE;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}    
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierUsgovLabelViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(SupplierUsgovLabelViewBean supplierUsgovLabelViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("boxLabelId", "SearchCriterion.EQUALS",
			"" + supplierUsgovLabelViewBean.getBoxLabelId());

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


	public int delete(SupplierUsgovLabelViewBean supplierUsgovLabelViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("boxLabelId", "SearchCriterion.EQUALS",
			"" + supplierUsgovLabelViewBean.getBoxLabelId());

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
	public int insert(SupplierUsgovLabelViewBean supplierUsgovLabelViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(supplierUsgovLabelViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SupplierUsgovLabelViewBean supplierUsgovLabelViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BOX_LABEL_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_ISSUE_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_PICKLIST_ID + "," +
			ATTRIBUTE_NSN + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_CAGE_CODE + "," +
			ATTRIBUTE_FORMATTED_NSN + "," +
			ATTRIBUTE_PID_PART_NO + "," +
			ATTRIBUTE_FORMATTED_CUSTOMER_PO + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_OF_ISSUE + "," +
			ATTRIBUTE_UNIT_OF_ISSUE + "," +
			ATTRIBUTE_GROSS_WEIGHT + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_DATE_SHIPPED + "," +
			ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED + "," +
			ATTRIBUTE_MHM_DATE + ")" +
			" values (" +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getBoxLabelId()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getShipFromLocationId()) + "," +
			supplierUsgovLabelViewBean.getIssueId() + "," +
			supplierUsgovLabelViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getLineItem()) + "," +
			supplierUsgovLabelViewBean.getPicklistId() + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getNsn()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getCageCode()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getFormattedNsn()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getPidPartNo()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getFormattedCustomerPo()) + "," +
			supplierUsgovLabelViewBean.getQuantity() + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getQuantityInUnitOfSale()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getQuantityInUnitOfIssue()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getUnitOfIssue()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getGrossWeight()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getDateDelivered()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getDateShipped()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getUnNumberAndProperShipName()) + "," +
			supplierUsgovLabelViewBean.getRadianPo() + "," +
			supplierUsgovLabelViewBean.getPoLine() + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getDateOfManufacture()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getExpireDate()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getPartShortName()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getShippedAsSingle()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getRequiresOverpack()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getInventoryGroup()) + "," +
			supplierUsgovLabelViewBean.getReceiptId() + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getOriginInspectionRequired()) + "," +
			SqlHandler.delimitString(supplierUsgovLabelViewBean.getMhmDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SupplierUsgovLabelViewBean supplierUsgovLabelViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(supplierUsgovLabelViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SupplierUsgovLabelViewBean supplierUsgovLabelViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BOX_LABEL_ID + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getBoxLabelId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getCompanyId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getSupplier()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(supplierUsgovLabelViewBean.getIssueId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(supplierUsgovLabelViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getLineItem()) + "," +
			ATTRIBUTE_PICKLIST_ID + "=" + 
				StringHandler.nullIfZero(supplierUsgovLabelViewBean.getPicklistId()) + "," +
			ATTRIBUTE_NSN + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getNsn()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_CAGE_CODE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getCageCode()) + "," +
			ATTRIBUTE_FORMATTED_NSN + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getFormattedNsn()) + "," +
			ATTRIBUTE_PID_PART_NO + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getPidPartNo()) + "," +
			ATTRIBUTE_FORMATTED_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getFormattedCustomerPo()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(supplierUsgovLabelViewBean.getQuantity()) + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_OF_SALE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getQuantityInUnitOfSale()) + "," +
			ATTRIBUTE_QUANTITY_IN_UNIT_OF_ISSUE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getQuantityInUnitOfIssue()) + "," +
			ATTRIBUTE_UNIT_OF_ISSUE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getUnitOfIssue()) + "," +
			ATTRIBUTE_GROSS_WEIGHT + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getGrossWeight()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getDateShipped()) + "," +
			ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getUnNumberAndProperShipName()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(supplierUsgovLabelViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(supplierUsgovLabelViewBean.getPoLine()) + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getDateOfManufacture()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getExpireDate()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getPartShortName()) + "," +
			ATTRIBUTE_SHIPPED_AS_SINGLE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getShippedAsSingle()) + "," +
			ATTRIBUTE_REQUIRES_OVERPACK + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getRequiresOverpack()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(supplierUsgovLabelViewBean.getReceiptId()) + "," +
			ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getOriginInspectionRequired()) + "," +
			ATTRIBUTE_MHM_DATE + "=" + 
				SqlHandler.delimitString(supplierUsgovLabelViewBean.getMhmDate()) + " " +
			"where " + ATTRIBUTE_BOX_LABEL_ID + "=" +
				supplierUsgovLabelViewBean.getBoxLabelId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection supplierUsgovLabelViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierUsgovLabelViewBean supplierUsgovLabelViewBean = new SupplierUsgovLabelViewBean();
			load(dataSetRow, supplierUsgovLabelViewBean);
			supplierUsgovLabelViewBeanColl.add(supplierUsgovLabelViewBean);
		}

		return supplierUsgovLabelViewBeanColl;
	}

  //select placard data
	public Collection selectPlacardData(SearchCriteria criteria, SortCriteria sortCriteria, LabelInputBean labelInputBean)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectPlacardData(criteria,labelInputBean, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectPlacardData(SearchCriteria criteria,LabelInputBean labelInputBean, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection supplierUsgovLabelViewBeanColl = new Vector();

		String query = "select distinct (select sum(QUANTITY) from SUPPLIER_PACKING_VIEW where SUPPLIER = '"+labelInputBean.getSupplier()+"' and PALLET_ID = '"+labelInputBean.getPalletId()+"') "+ATTRIBUTE_QUANTITY+","+ATTRIBUTE_UNIT_OF_SALE+","+ATTRIBUTE_INVENTORY_GROUP+","+ATTRIBUTE_NSN+","+ATTRIBUTE_CUSTOMER_PO+","+ATTRIBUTE_CAGE_CODE+","+ATTRIBUTE_FORMATTED_NSN+
        ","+ATTRIBUTE_FORMATTED_CUSTOMER_PO+","+ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME+
        ","+ATTRIBUTE_PART_SHORT_NAME+","+ATTRIBUTE_MHM_DATE+","+ATTRIBUTE_MFG_LOT+" from " + TABLE + " " +
			getWhereClause(criteria) + "group by "+ATTRIBUTE_QUANTITY+","+ATTRIBUTE_UNIT_OF_SALE+","+ATTRIBUTE_INVENTORY_GROUP+","+ATTRIBUTE_NSN+","+ATTRIBUTE_CUSTOMER_PO+","+ATTRIBUTE_CAGE_CODE+","+ATTRIBUTE_FORMATTED_NSN+
        ","+ATTRIBUTE_FORMATTED_CUSTOMER_PO+","+ATTRIBUTE_UN_NUMBER_AND_PROPER_SHIP_NAME+
        ","+ATTRIBUTE_PART_SHORT_NAME+","+ATTRIBUTE_MHM_DATE+","+ATTRIBUTE_MFG_LOT+ getOrderByClause(sortCriteria);

    log.info(query);
    DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierUsgovLabelViewBean supplierUsgovLabelViewBean = new SupplierUsgovLabelViewBean();
			load(dataSetRow, supplierUsgovLabelViewBean);
			supplierUsgovLabelViewBeanColl.add(supplierUsgovLabelViewBean);
		}

		return supplierUsgovLabelViewBeanColl;
	}
}