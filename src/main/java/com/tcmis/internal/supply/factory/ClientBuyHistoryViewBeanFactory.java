package com.tcmis.internal.supply.factory;

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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.ClientBuyHistoryViewBean;

public class ClientBuyHistoryViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_PO = "PO";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_PO_DATE = "PO_DATE";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	
	//table name
	public String TABLE = "CLIENT_BUY_HISTORY_VIEW";

	
	public ClientBuyHistoryViewBeanFactory(DbManager dbManager) {
		super(dbManager);		
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		} 
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		} 
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if (attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if (attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if (attributeName.equals("po")) {
			return ATTRIBUTE_PO;
		}
		else if (attributeName.equals("facPart_no")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if (attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if (attributeName.equals("manufacturer")) {
			return ATTRIBUTE_MANUFACTURER;
		}
		else if (attributeName.equals("mfgPart_no")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if (attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if (attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if (attributeName.equals("poDate")) {
			return ATTRIBUTE_PO_DATE;
		}
		else if (attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ClientBuyHistoryViewBean.class);
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

		 Collection clientBuyHistoryViewBeanCol = new Vector();

		 String query = "select * from " + TABLE + " " +
			 getWhereClause(criteria);
		 
		 if (sortCriteria != null) {
				query += getOrderByClause(sortCriteria);
		 }

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ClientBuyHistoryViewBean clientBuyHistoryViewBean = new ClientBuyHistoryViewBean();
			load(dataSetRow, clientBuyHistoryViewBean);
			clientBuyHistoryViewBeanCol.add(clientBuyHistoryViewBean);
		}

		return clientBuyHistoryViewBeanCol;
	}
}
