package com.tcmis.internal.hub.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.hub.beans.BatchPicklistViewBean;


/******************************************************************************
 * CLASSNAME: BatchPicklistViewBeanFactory <br>
 * @version: 1.0, Oct 5, 2007 <br>
 *****************************************************************************/


public class BatchPicklistViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_BATCH_ID = "BATCH_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_PICKLIST_QUANTITY = "PICKLIST_QUANTITY";
	public String ATTRIBUTE_PICKLIST_AMOUNT = "PICKLIST_AMOUNT";
	public String ATTRIBUTE_QUANTITY_PICKED = "QUANTITY_PICKED";
	public String ATTRIBUTE_PICKED_AMOUNT = "PICKED_AMOUNT";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_INVENTORY_QUANTITY = "INVENTORY_QUANTITY";
	public String ATTRIBUTE_INVENTORY_AMOUNT = "INVENTORY_AMOUNT";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_RECIPE_AMOUNT = "RECIPE_AMOUNT";
	public String ATTRIBUTE_AMOUNT_UNIT = "AMOUNT_UNIT";
	public String ATTRIBUTE_PICKABLE = "PICKABLE";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_PRODUCTION_DATE = "PRODUCTION_DATE";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	//table name
	public String TABLE = "BATCH_PICKLIST_VIEW";


	//constructor
	public BatchPicklistViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("batchId")) {
			return ATTRIBUTE_BATCH_ID;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("lotStatus")) {
			return ATTRIBUTE_LOT_STATUS;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("picklistQuantity")) {
			return ATTRIBUTE_PICKLIST_QUANTITY;
		}
		else if(attributeName.equals("picklistAmount")) {
			return ATTRIBUTE_PICKLIST_AMOUNT;
		}
		else if(attributeName.equals("quantityPicked")) {
			return ATTRIBUTE_QUANTITY_PICKED;
		}
		else if(attributeName.equals("pickedAmount")) {
			return ATTRIBUTE_PICKED_AMOUNT;
		}
		else if(attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("inventoryQuantity")) {
			return ATTRIBUTE_INVENTORY_QUANTITY;
		}
		else if(attributeName.equals("inventoryAmount")) {
			return ATTRIBUTE_INVENTORY_AMOUNT;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("recipeAmount")) {
			return ATTRIBUTE_RECIPE_AMOUNT;
		}
		else if(attributeName.equals("amountUnit")) {
			return ATTRIBUTE_AMOUNT_UNIT;
		}
		else if(attributeName.equals("pickable")) {
			return ATTRIBUTE_PICKABLE;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("productionDate")) {
			return ATTRIBUTE_PRODUCTION_DATE;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, BatchPicklistViewBean.class);
	}


	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {
		return select(criteria, null);
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

		  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
		      BaseException {
		    Collection batchPicklistViewBeanColl = new Vector();
		    String query = "select * from " + TABLE + " " +
		        getWhereClause(criteria) + getOrderByClause(sortCriteria);
		    DataSet dataSet = new SqlManager().select(conn, query);
		    Iterator dataIter = dataSet.iterator();
		    while (dataIter.hasNext()) {
		      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
		      BatchPicklistViewBean batchPicklistViewBean = new BatchPicklistViewBean();
		      load(dataSetRow, batchPicklistViewBean);
		      batchPicklistViewBeanColl.add(batchPicklistViewBean);
		    }
		    return batchPicklistViewBeanColl;
		  }
}