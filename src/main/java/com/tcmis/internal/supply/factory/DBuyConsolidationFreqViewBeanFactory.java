package com.tcmis.internal.supply.factory;

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


import com.tcmis.internal.supply.beans.DBuyConsolidationFreqViewBean;

public class DBuyConsolidationFreqViewBeanFactory  extends BaseBeanFactory {

	  Log log = LogFactory.getLog(this.getClass());

	  //column names
	  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	  public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	  public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
	  public String ATTRIBUTE_RUN_TIME = "RUN_TIME";
	  public String ATTRIBUTE_RUN_DAY = "RUN_DAY";
	  public String ATTRIBUTE_ENTERED_BY = "ENTERED_BY";
	  public String ATTRIBUTE_STATUS = "STATUS";
	  public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	  public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	  public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	  public String ATTRIBUTE_IGD_NEED_BY_TOLERANCE = "IGD_NEED_BY_TOLERANCE";
	  
	  //table name
	  public String TABLE = "CONSOLIDATE_BUY_FOR_IG_VIEW";

	  //constructor
	  public DBuyConsolidationFreqViewBeanFactory(DbManager dbManager) {
	    super(dbManager);
	  }

	  //get column names
	  public String getColumnName(String attributeName) {
	    if (attributeName.equals("inventoryGroup")) {
	      return ATTRIBUTE_INVENTORY_GROUP;
	    }
	    else if (attributeName.equals("inventoryGroupName")) {
		      return ATTRIBUTE_INVENTORY_GROUP_NAME;
		    }
	    else if (attributeName.equals("supplyPath")) {
	      return ATTRIBUTE_SUPPLY_PATH;
	    }
	    else if (attributeName.equals("runTime")) {
	      return ATTRIBUTE_RUN_TIME;
	    }
	    else if (attributeName.equals("runDay")) {
	      return ATTRIBUTE_RUN_DAY;
	    }
	    else if (attributeName.equals("enteredBy")) {
	      return ATTRIBUTE_ENTERED_BY;
	    }
	    else if (attributeName.equals("status")) {
	      return ATTRIBUTE_STATUS;
	    }
	    else if (attributeName.equals("transactionDate")) {
	      return ATTRIBUTE_TRANSACTION_DATE;
	    }
	    else if (attributeName.equals("opsEntityId")) {
	      return ATTRIBUTE_OPS_ENTITY_ID;
	    }
	    else if (attributeName.equals("opsCompanyId")) {
	      return ATTRIBUTE_OPS_COMPANY_ID;
	    }
	    else if (attributeName.equals("igdNeedByTolerance")) {
		      return ATTRIBUTE_IGD_NEED_BY_TOLERANCE;
		    }
	    else {
	      return super.getColumnName(attributeName);
	    }
	  }

	  //get column types
	  public int getType(String attributeName) {
	    return super.getType(attributeName, DBuyConsolidationFreqViewBean.class);
	  }


	  //select
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
	    Collection minMaxLevelLogViewBeanColl = new Vector();
	    String query = "select * from " + TABLE + " " +
	        getWhereClause(criteria) + getOrderByClause(sortCriteria);
	    if(log.isDebugEnabled()) {
	      log.debug("QUERY:" + query);
	    }
	    DataSet dataSet = new SqlManager().select(conn, query);
	    Iterator dataIter = dataSet.iterator();
	    while (dataIter.hasNext()) {
	      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	      DBuyConsolidationFreqViewBean dBuyConsolidationFreqViewBean = new  DBuyConsolidationFreqViewBean();
	      load(dataSetRow, dBuyConsolidationFreqViewBean);
	      minMaxLevelLogViewBeanColl.add(dBuyConsolidationFreqViewBean);
	    }
	    return minMaxLevelLogViewBeanColl;
	  }
	}
