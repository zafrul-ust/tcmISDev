package com.tcmis.client.common.factory;


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
import com.tcmis.client.common.beans.FacilityInventoryGroupBean;


/******************************************************************************
 * CLASSNAME: facilityInventoryGroupBeanFactory <br>
 * @version: 1.0, Aug 20, 2008 <br>
 *****************************************************************************/


public class FacilityInventoryGroupBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_STATUS = "STATUS";

	//table name
	public String TABLE = "customer.FACILITY_INVENTORY_GROUP";


	//constructor
	public FacilityInventoryGroupBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FacilityInventoryGroupBean.class);
	}

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		return select(criteria,null);

	}
	public Collection selectFix()
	throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId",SearchCriterion.EQUALS,"USGOV");
		criteria.addCriterion("facilityId",SearchCriterion.EQUALS,"DLA Gases");
		criteria.addCriterion("status",SearchCriterion.EQUALS,"ACTIVE");
		return select(criteria,null);
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
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
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection facilityInventoryGroupBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FacilityInventoryGroupBean facilityInventoryGroupBean = new FacilityInventoryGroupBean();
			load(dataSetRow, facilityInventoryGroupBean);
			facilityInventoryGroupBeanColl.add(facilityInventoryGroupBean);
		}

		return facilityInventoryGroupBeanColl;
	}
}