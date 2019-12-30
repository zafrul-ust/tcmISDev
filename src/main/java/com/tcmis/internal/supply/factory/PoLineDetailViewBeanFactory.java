package com.tcmis.internal.supply.factory;


import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.DateHandler;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Timestamp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * CLASSNAME: PoLineDetailViewBeanFactory <br>
 * @version: 1.0, Aug 7, 2008 <br>
 *****************************************************************************/


public class PoLineDetailViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_AMENDMENT = "AMENDMENT";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CURRENCY_DISPLAY = "CURRENCY_DISPLAY";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_ALLOWED_PRICE_VARIANCE = "ALLOWED_PRICE_VARIANCE";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_DPAS_RATING = "DPAS_RATING";
	public String ATTRIBUTE_QUALITY_FLOW_DOWNS = "QUALITY_FLOW_DOWNS";
	public String ATTRIBUTE_PACKAGING_FLOW_DOWNS = "PACKAGING_FLOW_DOWNS";
	public String ATTRIBUTE_PO_LINE_NOTE = "PO_LINE_NOTE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_SECONDARY_SUPPLIER_ON_PO = "SECONDARY_SUPPLIER_ON_PO";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_PO_LINE_STATUS = "PO_LINE_STATUS";
	public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
	public String ATTRIBUTE_QUANTITY_VOUCHERED = "QUANTITY_VOUCHERED";
	public String ATTRIBUTE_QUANTITY_RETURNED = "QUANTITY_RETURNED";
	public String ATTRIBUTE_SUPPLIER_QTY = "SUPPLIER_QTY";
	public String ATTRIBUTE_SUPPLIER_PKG = "SUPPLIER_PKG";
	public String ATTRIBUTE_SUPPLIER_UNIT_PRICE = "SUPPLIER_UNIT_PRICE";
	public String ATTRIBUTE_EVER_CONFIRMED = "EVER_CONFIRMED";
	public String ATTRIBUTE_MSDS_REQUESTED_DATE = "MSDS_REQUESTED_DATE";
	public String ATTRIBUTE_PR_SHIP_TO = "PR_SHIP_TO";
	public String ATTRIBUTE_PR_COMPANY_ID = "PR_COMPANY_ID";
	public String ATTRIBUTE_GENERIC_COC = "GENERIC_COC";
	public String ATTRIBUTE_GENERIC_COA = "GENERIC_COA";
	public String ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT = "REMAINING_SHELF_LIFE_PERCENT";
	public String ATTRIBUTE_DATE_CONFIRMED = "DATE_CONFIRMED";
	public String ATTRIBUTE_DELIVERY_COMMENTS = "DELIVERY_COMMENTS";
	public String ATTRIBUTE_ARCHIVED = "ARCHIVED";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_ITEM_VERIFIED_BY = "ITEM_VERIFIED_BY";
	public String ATTRIBUTE_DATE_ITEM_VERIFIED = "DATE_ITEM_VERIFIED";
	public String ATTRIBUTE_DBUY_LOCK_STATUS = "DBUY_LOCK_STATUS";
	public String ATTRIBUTE_MAX_PRICE_LIMIT = "MAX_PRICE_LIMIT";
	public String ATTRIBUTE_MAX_VENDOR_SHIP_DATE = "MAX_VENDOR_SHIP_DATE";
	public String ATTRIBUTE_NBO_COMPANY_ID = "NBO_COMPANY_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SECONDARY_SUPPLIER_ADDRESS = "SECONDARY_SUPPLIER_ADDRESS";
	public String ATTRIBUTE_DIFFERENT_SUPPLIER_ON_LINE = "DIFFERENT_SUPPLIER_ON_LINE";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM = "PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_BUYER_COMPANY_ID = "BUYER_COMPANY_ID";
	public String ATTRIBUTE_CHANGE_SUPPLIER = "CHANGE_SUPPLIER";
	public String ATTRIBUTE_LAST_CONFIRMED = "LAST_CONFIRMED";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
	public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
	public String ATTRIBUTE_DBUY_CONTRACT_PRICE = "DBUY_CONTRACT_PRICE";
	public String ATTRIBUTE_EXPEDITE_COMMENTS = "EXPEDITE_COMMENTS";
  public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_NAME = "SHIP_FROM_LOCATION_NAME";
	public String ATTRIBUTE_EXPEDITE_DATE = "EXPEDITE_DATE";


  //table name
	public String TABLE = "PO_LINE_DETAIL_VIEW";


	//constructor
	public PoLineDetailViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("amendment")) {
			return ATTRIBUTE_AMENDMENT;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("currencyDisplay")) {
			return ATTRIBUTE_CURRENCY_DISPLAY;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("extendedPrice")) {
			return ATTRIBUTE_EXTENDED_PRICE;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("allowedPriceVariance")) {
			return ATTRIBUTE_ALLOWED_PRICE_VARIANCE;
		}
		else if(attributeName.equals("mfgPartNo")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if(attributeName.equals("supplierPartNo")) {
			return ATTRIBUTE_SUPPLIER_PART_NO;
		}
		else if(attributeName.equals("dpasRating")) {
			return ATTRIBUTE_DPAS_RATING;
		}
		else if(attributeName.equals("qualityFlowDowns")) {
			return ATTRIBUTE_QUALITY_FLOW_DOWNS;
		}
		else if(attributeName.equals("packagingFlowDowns")) {
			return ATTRIBUTE_PACKAGING_FLOW_DOWNS;
		}
		else if(attributeName.equals("poLineNote")) {
			return ATTRIBUTE_PO_LINE_NOTE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("secondarySupplierOnPo")) {
			return ATTRIBUTE_SECONDARY_SUPPLIER_ON_PO;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("poLineStatus")) {
			return ATTRIBUTE_PO_LINE_STATUS;
		}
		else if(attributeName.equals("quantityReceived")) {
			return ATTRIBUTE_QUANTITY_RECEIVED;
		}
		else if(attributeName.equals("quantityVouchered")) {
			return ATTRIBUTE_QUANTITY_VOUCHERED;
		}
		else if(attributeName.equals("quantityReturned")) {
			return ATTRIBUTE_QUANTITY_RETURNED;
		}
		else if(attributeName.equals("supplierQty")) {
			return ATTRIBUTE_SUPPLIER_QTY;
		}
		else if(attributeName.equals("supplierPkg")) {
			return ATTRIBUTE_SUPPLIER_PKG;
		}
		else if(attributeName.equals("supplierUnitPrice")) {
			return ATTRIBUTE_SUPPLIER_UNIT_PRICE;
		}
		else if(attributeName.equals("everConfirmed")) {
			return ATTRIBUTE_EVER_CONFIRMED;
		}
		else if(attributeName.equals("msdsRequestedDate")) {
			return ATTRIBUTE_MSDS_REQUESTED_DATE;
		}
		else if(attributeName.equals("prShipTo")) {
			return ATTRIBUTE_PR_SHIP_TO;
		}
		else if(attributeName.equals("prCompanyId")) {
			return ATTRIBUTE_PR_COMPANY_ID;
		}
		else if(attributeName.equals("genericCoc")) {
			return ATTRIBUTE_GENERIC_COC;
		}
		else if(attributeName.equals("genericCoa")) {
			return ATTRIBUTE_GENERIC_COA;
		}
		else if(attributeName.equals("remainingShelfLifePercent")) {
			return ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT;
		}
		else if(attributeName.equals("dateConfirmed")) {
			return ATTRIBUTE_DATE_CONFIRMED;
		}
		else if(attributeName.equals("deliveryComments")) {
			return ATTRIBUTE_DELIVERY_COMMENTS;
		}
		else if(attributeName.equals("archived")) {
			return ATTRIBUTE_ARCHIVED;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("itemVerifiedBy")) {
			return ATTRIBUTE_ITEM_VERIFIED_BY;
		}
		else if(attributeName.equals("dateItemVerified")) {
			return ATTRIBUTE_DATE_ITEM_VERIFIED;
		}
		else if(attributeName.equals("dbuyLockStatus")) {
			return ATTRIBUTE_DBUY_LOCK_STATUS;
		}
		else if(attributeName.equals("maxPriceLimit")) {
			return ATTRIBUTE_MAX_PRICE_LIMIT;
		}
		else if(attributeName.equals("maxVendorShipDate")) {
			return ATTRIBUTE_MAX_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("nboCompanyId")) {
			return ATTRIBUTE_NBO_COMPANY_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("secondarySupplierAddress")) {
			return ATTRIBUTE_SECONDARY_SUPPLIER_ADDRESS;
		}
		else if(attributeName.equals("differentSupplierOnLine")) {
			return ATTRIBUTE_DIFFERENT_SUPPLIER_ON_LINE;
		}
		else if(attributeName.equals("purchasingUnitsPerItem")) {
			return ATTRIBUTE_PURCHASING_UNITS_PER_ITEM;
		}
		else if(attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		}
		else if(attributeName.equals("buyerCompanyId")) {
			return ATTRIBUTE_BUYER_COMPANY_ID;
		}
		else if(attributeName.equals("changeSupplier")) {
			return ATTRIBUTE_CHANGE_SUPPLIER;
		}
		else if(attributeName.equals("lastConfirmed")) {
			return ATTRIBUTE_LAST_CONFIRMED;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("shelfLifeBasis")) {
			return ATTRIBUTE_SHELF_LIFE_BASIS;
		}
		else if(attributeName.equals("shelfLifeDays")) {
			return ATTRIBUTE_SHELF_LIFE_DAYS;
		}
		else if(attributeName.equals("dbuyContractPrice")) {
			return ATTRIBUTE_DBUY_CONTRACT_PRICE;
		}
		else if(attributeName.equals("expediteComments")) {
			return ATTRIBUTE_EXPEDITE_COMMENTS;
		}
		else if(attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else if(attributeName.equals("shipFromLocationName")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_NAME;
		}  
		else if(attributeName.equals("expediteDate")) {
			 return ATTRIBUTE_EXPEDITE_DATE;
	    }
    else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoLineDetailViewBean.class);
	}

  	//update po_line projected delivery date - this updates the projected delivery date without an ammendment to the PO
	public int updateProjDeliveryDate(PoLineDetailViewBean inputBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateProjDeliveryDate(inputBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updateProjDeliveryDate(PoLineDetailViewBean inputBean, Connection conn)
		throws BaseException {

		String query  = "update PO_LINE set " +			
			ATTRIBUTE_PROMISED_DATE + "=" +
				DateHandler.getOracleToDateFunction(inputBean.getPromisedDate()) + 
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				inputBean.getRadianPo() + " and "+ATTRIBUTE_PO_LINE+ "="+inputBean.getPoLine();

		return new SqlManager().update(conn, query);
	}

 //update call pkg_po.DELETE_PO_LINE
 public Collection deletePoLine(PoLineDetailViewBean inputBean) throws Exception {
	Connection connection = this.getDbManager().getConnection();
	Collection cin = new Vector();

	cin.add(inputBean.getRadianPo());
	cin.add(inputBean.getPoLine());
	cin.add(inputBean.getAmendment());

	Collection cout = new Vector();
	cout.add(new Integer(java.sql.Types.VARCHAR));

	Collection result = this.getDbManager().doProcedure("pkg_po.DELETE_PO_LINE",
	 cin, cout);
	this.getDbManager().returnConnection(connection);
	return result;
 }

	//update call pkg_po.SAVE_PO_LINE
	public Collection updatePoLine(PoLineDetailViewBean inputBean,
	 BigDecimal personnelId, String updateAction) throws Exception {
	 Connection connection = this.getDbManager().getConnection();
	 Collection cin = new Vector();

	 cin.add(inputBean.getRadianPo());
	 cin.add(inputBean.getPoLine());
	 cin.add(inputBean.getAmendment());

	 if (inputBean.getItemId() != null) {
		cin.add(inputBean.getItemId());
	 }
	 else {
		cin.add("");
	 }

	 if ("confirm".equalsIgnoreCase(updateAction)) {
		cin.add("Y");
	 }
	 else {
		cin.add("");
	 }

	 if (inputBean.getQuantity() != null) {
		cin.add(inputBean.getQuantity());
	 }
	 else {
		cin.add("");
	 }

	 if (inputBean.getUnitPrice() != null) {
		cin.add(inputBean.getUnitPrice());
	 }
	 else {
		cin.add("");
	 }

	 if (inputBean.getNeedDate() != null) {
		try {
     cin.add(new Timestamp(inputBean.getNeedDate().getTime()));
		}
		catch (Exception ex) {
		}
	 }
	 else {
		cin.add("");
	 }

	 if (inputBean.getPromisedDate() != null) {
		try {
      cin.add(new Timestamp(inputBean.getPromisedDate().getTime()));
		}
		catch (Exception ex) {
		}
	 }
	 else {
		cin.add("");
	 }

	 cin.add("");

	 if (inputBean.getMfgPartNo() != null &&
		inputBean.getMfgPartNo().trim().length() > 0) {
		cin.add(inputBean.getMfgPartNo().trim());
	 }
	 else {
		cin.add("");
	 }

	 if (inputBean.getSupplierPartNo() != null &&
		inputBean.getSupplierPartNo().trim().length() > 0) {
		cin.add(inputBean.getSupplierPartNo().trim());
	 }
	 else {
		cin.add("");
	 }

	 if (inputBean.getDpasRating() != null &&
		inputBean.getDpasRating().trim().length() > 0) {
		cin.add(inputBean.getDpasRating().trim());
	 }
	 else {
		cin.add("");
	 }

	 cin.add("");
	 cin.add("");

	 if (inputBean.getPoLineNote() != null &&
		inputBean.getPoLineNote().trim().length() > 0) {
		cin.add(inputBean.getPoLineNote().trim());
	 }
	 else {
		cin.add("");
	 }

	 if ("confirm".equalsIgnoreCase(updateAction)) {
		cin.add("");
	 }
	 else {
		if (inputBean.getAmmendmentcomments() != null &&
		 inputBean.getAmmendmentcomments().trim().length() > 0) {
		 cin.add(inputBean.getAmmendmentcomments().trim());
		}
		else {
		 cin.add("");
		}
	}
	cin.add(personnelId);

	if (inputBean.getSupplierQty() != null) {
	 cin.add(inputBean.getSupplierQty());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getSupplierPkg() != null &&
	 inputBean.getSupplierPkg().trim().length() > 0) {
	 cin.add(inputBean.getSupplierPkg().trim());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getSupplierUnitPrice() != null) {
	 cin.add(inputBean.getSupplierUnitPrice());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getGenericCoc() != null &&
	 "Yes".equalsIgnoreCase(inputBean.getGenericCoc())) {
	 cin.add("Y");
	}
	else {
	 cin.add("N");
	}

	if (inputBean.getGenericCoa() != null &&
	 "Yes".equalsIgnoreCase(inputBean.getGenericCoa())) {
	 cin.add("Y");
	}
	else {
	 cin.add("N");
	}

	if (inputBean.getMsdsRequestedDate() != null &&
	 "Yes".equalsIgnoreCase(inputBean.getMsdsRequestedDate())) {
	 cin.add("Y");
	}
	else {
	 cin.add("");
	}

	if (inputBean.getRemainingShelfLifePercent() != null) {
	 cin.add(inputBean.getRemainingShelfLifePercent());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getDeliveryComments() != null &&
	 inputBean.getDeliveryComments().trim().length() > 0) {
	 cin.add(inputBean.getDeliveryComments().trim());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getVendorShipDate() != null) {
	 try {
    cin.add(new Timestamp(inputBean.getVendorShipDate().getTime()));
	 }
	 catch (Exception ex) {
	 }
	}
	else {
	 cin.add("");
	}

	if (inputBean.getCurrencyId() != null) {
	 cin.add(inputBean.getCurrencyId());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getSupplier() != null &&
	 inputBean.getSupplier().trim().length() > 0) {
	 cin.add(inputBean.getSupplier().trim());
	}
	else {
	 cin.add("");
	}

	Collection cout = new Vector();
	cout.add(new Integer(java.sql.Types.VARCHAR));
    cout.add(new Integer(java.sql.Types.VARCHAR));
        
  Collection cOptionalIn = new Vector();
  if (inputBean.getShipFromLocationId() != null &&
	 inputBean.getShipFromLocationId().trim().length() > 0) {
	 cOptionalIn.add(inputBean.getShipFromLocationId().trim());
	}
	else {
	 cOptionalIn.add("");
	}

  if (inputBean.getSupplierSalesOrderNo() != null &&
	 inputBean.getSupplierSalesOrderNo().trim().length() > 0) {
	 cOptionalIn.add(inputBean.getSupplierSalesOrderNo().trim());
	}
	else {
	 cOptionalIn.add("");
	}
        
  Collection result = this.getDbManager().doProcedure("pkg_po.SAVE_PO_LINE",cin,cout,cOptionalIn);
	this.getDbManager().returnConnection(connection);
	return result;
 }

 //select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,null, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

 public Collection select(SearchCriteria criteria,SortCriteria sortCriteria)
	throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
		connection = this.getDbManager().getConnection();
		c = select(criteria,sortCriteria, connection);
	}
	finally {
		this.getDbManager().returnConnection(connection);
	}
	return c;
 }

	public Collection select(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection poLineDetailViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);
		if (sortCriteria !=null)
		{
			 query += getOrderByClause(sortCriteria);
		}

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoLineDetailViewBean poLineDetailViewBean = new PoLineDetailViewBean();
			load(dataSetRow, poLineDetailViewBean);
			poLineDetailViewBeanColl.add(poLineDetailViewBean);
		}

		return poLineDetailViewBeanColl;
	}
}