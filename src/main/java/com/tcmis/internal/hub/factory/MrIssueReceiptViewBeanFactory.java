package com.tcmis.internal.hub.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.hub.beans.MrIssueReceiptViewBean;


/******************************************************************************
 * CLASSNAME: MrIssueReceiptViewBeanFactory <br>
 * @version: 1.0, Nov 14, 2006 <br>
 *****************************************************************************/


public class MrIssueReceiptViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_TOTAL_SHIPPED = "TOTAL_SHIPPED";
	public String ATTRIBUTE_QUANTITY_RETURNED = "QUANTITY_RETURNED";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_RECERT_NUMBER = "RECERT_NUMBER";
	public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CREDIT_RETURN = "CREDIT_RETURN";
//	private BigDecimal shipmentId;
//	private Date dateShipped;
//	private BigDecimal issudId;
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	
	//table name
	public String TABLE = "MR_ISSUE_RECEIPT_VIEW";


	//constructor
	public MrIssueReceiptViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("sourceHub")) {
			return ATTRIBUTE_SOURCE_HUB;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("totalShipped")) {
			return ATTRIBUTE_TOTAL_SHIPPED;
		}
		else if(attributeName.equals("quantityReturned")) {
			return ATTRIBUTE_QUANTITY_RETURNED;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("recertNumber")) {
			return ATTRIBUTE_RECERT_NUMBER;
		}
		else if(attributeName.equals("lotStatus")) {
			return ATTRIBUTE_LOT_STATUS;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("creditReturn")) {
			return ATTRIBUTE_CREDIT_RETURN;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MrIssueReceiptViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(MrIssueReceiptViewBean mrIssueReceiptViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + mrIssueReceiptViewBean.getCompanyId());

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


	public int delete(MrIssueReceiptViewBean mrIssueReceiptViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + mrIssueReceiptViewBean.getCompanyId());

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
	public int insert(MrIssueReceiptViewBean mrIssueReceiptViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(mrIssueReceiptViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(MrIssueReceiptViewBean mrIssueReceiptViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_SOURCE_HUB + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_TOTAL_SHIPPED + "," +
			ATTRIBUTE_QUANTITY_RETURNED + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_RECERT_NUMBER + "," +
			ATTRIBUTE_LOT_STATUS + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_CREDIT_RETURN + ")" +
 values (
			SqlHandler.delimitString(mrIssueReceiptViewBean.getCompanyId()) + "," +
			StringHandler.nullIfZero(mrIssueReceiptViewBean.getPrNumber()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getSourceHub()) + "," +
			StringHandler.nullIfZero(mrIssueReceiptViewBean.getItemId()) + "," +
			StringHandler.nullIfZero(mrIssueReceiptViewBean.getReceiptId()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getCatPartNo()) + "," +
			StringHandler.nullIfZero(mrIssueReceiptViewBean.getTotalShipped()) + "," +
			StringHandler.nullIfZero(mrIssueReceiptViewBean.getQuantityReturned()) + "," +
			StringHandler.nullIfZero(mrIssueReceiptViewBean.getQuantity()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getMfgLot()) + "," +
			DateHandler.getOracleToDateFunction(mrIssueReceiptViewBean.getExpireDate()) + "," +
			StringHandler.nullIfZero(mrIssueReceiptViewBean.getRecertNumber()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getLotStatus()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(mrIssueReceiptViewBean.getCreditReturn()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(MrIssueReceiptViewBean mrIssueReceiptViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(mrIssueReceiptViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(MrIssueReceiptViewBean mrIssueReceiptViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getCompanyId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getLineItem()) + "," +
			ATTRIBUTE_SOURCE_HUB + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getSourceHub()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getItemId()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getReceiptId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getFacilityId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_TOTAL_SHIPPED + "=" + 
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getTotalShipped()) + "," +
			ATTRIBUTE_QUANTITY_RETURNED + "=" + 
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getQuantityReturned()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getQuantity()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getMfgLot()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(mrIssueReceiptViewBean.getExpireDate()) + "," +
			ATTRIBUTE_RECERT_NUMBER + "=" + 
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getRecertNumber()) + "," +
			ATTRIBUTE_LOT_STATUS + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getLotStatus()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_CREDIT_RETURN + "=" + 
				SqlHandler.delimitString(mrIssueReceiptViewBean.getCreditReturn()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				StringHandler.nullIfZero(mrIssueReceiptViewBean.getCompanyId());

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
        
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,boolean forCredit,boolean getbins)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection,forCredit,getbins);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
        
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection mrIssueReceiptViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MrIssueReceiptViewBean mrIssueReceiptViewBean = new MrIssueReceiptViewBean();
			load(dataSetRow, mrIssueReceiptViewBean);
			mrIssueReceiptViewBeanColl.add(mrIssueReceiptViewBean);
		}

		return mrIssueReceiptViewBeanColl;
	}
        
   public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn,boolean forCredit,boolean getbins)
   throws BaseException {
      
      Collection mrIssueReceiptViewBeanColl = new Vector();
      String table = TABLE;
      if( forCredit ) table  = "return_mr_for_credit_view";
      String query = "select * from " + table + " " + getWhereClause(criteria);
      
      if (sortCriteria != null) {
         query += " " + getOrderByClause(sortCriteria);
      }
      
      DataSet dataSet = new SqlManager().select(conn, query);
      
      Iterator dataIter = dataSet.iterator();
      
      ReceiptItemPriorBinViewBeanFactory factory = new ReceiptItemPriorBinViewBeanFactory(getDbManager());
      SearchCriteria childCriteria = null;
      Collection priorBins = null;
      String key = null;
      Hashtable itemhubHash = new Hashtable();
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow)dataIter.next();
         MrIssueReceiptViewBean mrIssueReceiptViewBean = new MrIssueReceiptViewBean();
         load(dataSetRow, mrIssueReceiptViewBean);
         if( getbins && !forCredit) {                  
         key = mrIssueReceiptViewBean.getItemId().intValue() + mrIssueReceiptViewBean.getSourceHub();
         if (itemhubHash.containsKey(key)) {
            priorBins = (Collection) itemhubHash.get(key);
            mrIssueReceiptViewBean.setReceiptItemPriorBinViewBeanCollection(priorBins);
         } else {
         
            //get collection of ReceiptItemPriorViewBeans
            childCriteria = new SearchCriteria();
            childCriteria.addCriterion("itemId", SearchCriterion.EQUALS,
                                       "" + mrIssueReceiptViewBean.getItemId().intValue() + "");
            childCriteria.addCriterion("hub", SearchCriterion.EQUALS,
                                       mrIssueReceiptViewBean.getSourceHub());
            childCriteria.addCriterion("status", SearchCriterion.EQUALS, "A");
            childCriteria.addCriterion("onSite", SearchCriterion.EQUALS, "Y");
            priorBins = factory.select(childCriteria);
            mrIssueReceiptViewBean.setReceiptItemPriorBinViewBeanCollection(priorBins);            
            itemhubHash.put(key, priorBins);
         }
         }
         mrIssueReceiptViewBeanColl.add(mrIssueReceiptViewBean);
      }
      factory = null;
      itemhubHash = null;
      dataSet = null;      
      
      return mrIssueReceiptViewBeanColl;
   }
    
   public Collection receive(String companyId, BigDecimal receiptId, BigDecimal prNumber, String lineItem, BigDecimal quantity,
                             String bin, Date dor, String receiverId) throws BaseException {
       Connection connection = null;
       Collection results = null;
       try {
               connection = this.getDbManager().getConnection();
               results = receive(companyId, receiptId, prNumber, lineItem, quantity, bin, dor, receiverId, connection);
       }
       finally {
               this.getDbManager().returnConnection(connection);
       }
       return results;                                
   }
   
  private Collection receive(String companyId, BigDecimal receiptId, BigDecimal prNumber, String lineItem, BigDecimal quantity,
                             String bin, Date dor, String receiverId, Connection conn) throws BaseException {
                             
       Collection inArgs = new Vector(8);
       inArgs.add(companyId);       
       inArgs.add(receiptId);
       inArgs.add(prNumber);
       inArgs.add(lineItem);
       inArgs.add(quantity);       
       inArgs.add(bin);              
       inArgs.add(new Timestamp(dor.getTime()));            
       inArgs.add(receiverId);
     
       Collection outArgs = new Vector(2);
       outArgs.add(new Integer(java.sql.Types.NUMERIC));
       outArgs.add(new Integer(java.sql.Types.VARCHAR));
       GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
       return procFactory.doProcedure(conn, "p_customer_owned_receipt", inArgs, outArgs);       
   }
  public Collection credit(String replacementFlag, BigDecimal issueId, BigDecimal rev_quantity, BigDecimal quantity, String companyId, BigDecimal prNumber, String lineItem, 
		  String comment, BigDecimal orderQuantity,BigDecimal personnelId ) throws BaseException {
	  Connection connection = null;
	  Collection results = null;
	  try {
		  connection = this.getDbManager().getConnection();
		  results = credit(replacementFlag, issueId, rev_quantity, quantity, companyId, prNumber, lineItem, comment,orderQuantity,personnelId, connection);
	  }
	  finally {
		  this.getDbManager().returnConnection(connection);
	  }
	  return results;                                
  }
  private Collection credit(String replacementFlag, BigDecimal issueId, BigDecimal rev_quantity, BigDecimal quantity, String companyId, BigDecimal prNumber, String lineItem, 
		  String comment, BigDecimal orderQuantity,BigDecimal personnelId, Connection conn) throws BaseException {

	  Collection inArgs = new Vector(10);
	  if( "true".equalsIgnoreCase(replacementFlag) || "Y".equalsIgnoreCase(replacementFlag) )
		  replacementFlag = "Y";
	  else 
		  replacementFlag = "N";
	  inArgs.add(replacementFlag);       
	  inArgs.add(issueId);
	  inArgs.add(null);
	  inArgs.add(rev_quantity);
	  inArgs.add(quantity);
	  inArgs.add(companyId);
	  inArgs.add(prNumber);
	  inArgs.add(lineItem);
	  inArgs.add(comment);
	  inArgs.add(orderQuantity);
      inArgs.add(personnelId);


      Collection outArgs = new Vector(1);
      outArgs.add(new Integer(java.sql.Types.VARCHAR));
      
	  GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
	  return procFactory.doProcedure(conn, "P_RETURN_MR_FOR_CREDIT", inArgs, outArgs);       
  }  
}