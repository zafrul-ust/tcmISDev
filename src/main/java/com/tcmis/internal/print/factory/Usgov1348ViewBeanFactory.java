package com.tcmis.internal.print.factory;


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
import com.tcmis.internal.print.beans.Usgov1348ViewBean;


/******************************************************************************
 * CLASSNAME: Usgov1348ViewBeanFactory <br>
 * @version: 1.0, Feb 13, 2008 <br>
 *****************************************************************************/


public class Usgov1348ViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_BOX_10 = "BOX_10";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_BOX_2 = "BOX_2";
	public String ATTRIBUTE_BOX_4 = "BOX_4";
	public String ATTRIBUTE_BOX_3 = "BOX_3";
	public String ATTRIBUTE_BOX_27_LINE_1 = "BOX_27_LINE_1";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
	public String ATTRIBUTE_FIRST_ROW_1348 = "FIRST_ROW_1348";
	public String ATTRIBUTE_BOX_9 = "BOX_9";
	public String ATTRIBUTE_BOX_27_LINE_3 = "BOX_27_LINE_3";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_BOX_15 = "BOX_15";
	public String ATTRIBUTE_BOX_17 = "BOX_17";
	public String ATTRIBUTE_BOX_26_LINE_1 = "BOX_26_LINE_1";
	public String ATTRIBUTE_BOX_26_LINE_2 = "BOX_26_LINE_2";
	public String ATTRIBUTE_BOX_26_LINE_3 = "BOX_26_LINE_3";
	public String ATTRIBUTE_BOX_26_LINE_4 = "BOX_26_LINE_4";
	public String ATTRIBUTE_BOX_26_LINE_5 = "BOX_26_LINE_5";
	public String ATTRIBUTE_BOX_26_LINE_6 = "BOX_26_LINE_6";
	public String ATTRIBUTE_BOX_25 = "BOX_25";
	public String ATTRIBUTE_BOX_24 = "BOX_24";
	public String ATTRIBUTE_REQUIRED_DELIVERY_DATE = "REQUIRED_DELIVERY_DATE";
	public String ATTRIBUTE_BOX_12 = "BOX_12";
	public String ATTRIBUTE_BOX_13 = "BOX_13";
	public String ATTRIBUTE_PRINTED_FIRST_ROW = "PRINTED_FIRST_ROW";
	public String ATTRIBUTE_SUPPLEMENTARY_ADDRESS = "SUPPLEMENTARY_ADDRESS";
	public String ATTRIBUTE_DISTRIBUTION_CODE = "DISTRIBUTION_CODE";
	public String ATTRIBUTE_BOX_1_DOLLARS = "BOX_1_DOLLARS";
	public String ATTRIBUTE_BOX_1_CENTS = "BOX_1_CENTS";
	public String ATTRIBUTE_BOX_5 = "BOX_5";
	public String ATTRIBUTE_FMS = "FMS";
	public String ATTRIBUTE_UNIT_OF_ISSUE = "UNIT_OF_ISSUE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_TCN = "TCN";
	public String ATTRIBUTE_CAGE_CODE = "CAGE_CODE";
  public String ATTRIBUTE_PROJECT_CODE = "PROJECT_CODE";
  public String ATTRIBUTE_BOX_28 = "BOX_28";
	public String ATTRIBUTE_BOX_29 = "BOX_29";
	public String ATTRIBUTE_TRANSPORTATION_PRIORITY = "TRANSPORTATION_PRIORITY";
    public String ATTRIBUTE_FMS_CASE_NUM = "FMS_CASE_NUM";
    public String ATTRIBUTE_RELEASE_NUM = "RELEASE_NUM";

  //table name
	public String TABLE = "USGOV_1348_VIEW";


	//constructor
	public Usgov1348ViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("box10")) {
			return ATTRIBUTE_BOX_10;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("box2")) {
			return ATTRIBUTE_BOX_2;
		}
		else if(attributeName.equals("box4")) {
			return ATTRIBUTE_BOX_4;
		}
		else if(attributeName.equals("box3")) {
			return ATTRIBUTE_BOX_3;
		}
		else if(attributeName.equals("box27Line1")) {
			return ATTRIBUTE_BOX_27_LINE_1;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
		}
		else if(attributeName.equals("firstRow1348")) {
			return ATTRIBUTE_FIRST_ROW_1348;
		}
		else if(attributeName.equals("box9")) {
			return ATTRIBUTE_BOX_9;
		}
		else if(attributeName.equals("box27Line3")) {
			return ATTRIBUTE_BOX_27_LINE_3;
		}
		else if(attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("box15")) {
			return ATTRIBUTE_BOX_15;
		}
		else if(attributeName.equals("box17")) {
			return ATTRIBUTE_BOX_17;
		}
		else if(attributeName.equals("box26Line1")) {
			return ATTRIBUTE_BOX_26_LINE_1;
		}
		else if(attributeName.equals("box26Line2")) {
			return ATTRIBUTE_BOX_26_LINE_2;
		}
		else if(attributeName.equals("box26Line3")) {
			return ATTRIBUTE_BOX_26_LINE_3;
		}
		else if(attributeName.equals("box26Line4")) {
			return ATTRIBUTE_BOX_26_LINE_4;
		}
		else if(attributeName.equals("box26Line5")) {
			return ATTRIBUTE_BOX_26_LINE_5;
		}
		else if(attributeName.equals("box26Line6")) {
			return ATTRIBUTE_BOX_26_LINE_6;
		}
		else if(attributeName.equals("box25")) {
			return ATTRIBUTE_BOX_25;
		}
		else if(attributeName.equals("box24")) {
			return ATTRIBUTE_BOX_24;
		}
		else if(attributeName.equals("requiredDeliveryDate")) {
			return ATTRIBUTE_REQUIRED_DELIVERY_DATE;
		}
		else if(attributeName.equals("box12")) {
			return ATTRIBUTE_BOX_12;
		}
		else if(attributeName.equals("box13")) {
			return ATTRIBUTE_BOX_13;
		}
		else if(attributeName.equals("printedFirstRow")) {
			return ATTRIBUTE_PRINTED_FIRST_ROW;
		}
		else if(attributeName.equals("box1Dollars")) {
			return ATTRIBUTE_BOX_1_DOLLARS;
		}
		else if(attributeName.equals("box1Cents")) {
			return ATTRIBUTE_BOX_1_CENTS;
		}
		else if(attributeName.equals("box5")) {
			return ATTRIBUTE_BOX_5;
		}
		else if(attributeName.equals("fms")) {
			return ATTRIBUTE_FMS;
		}
		else if(attributeName.equals("unitOfIssue")) {
			return ATTRIBUTE_UNIT_OF_ISSUE;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("tcn")) {
			return ATTRIBUTE_TCN;
		}
		else if(attributeName.equals("cageCode")) {
			return ATTRIBUTE_CAGE_CODE;
		}
		else if(attributeName.equals("projectCode")) {
			return ATTRIBUTE_PROJECT_CODE;
		}
		else if(attributeName.equals("box28")) {
			return ATTRIBUTE_BOX_28;
		}
		else if(attributeName.equals("box29")) {
			return ATTRIBUTE_BOX_29;
		}
		else if(attributeName.equals("transportationPriority")) {
			return ATTRIBUTE_TRANSPORTATION_PRIORITY;
		}
		else if(attributeName.equals("fmsCaseNum")) {
			return ATTRIBUTE_FMS_CASE_NUM;
		}
        else if(attributeName.equals("releaseNum")) {
			return ATTRIBUTE_RELEASE_NUM;
		}
        else if(attributeName.equals("distributionCode")) {
			return ATTRIBUTE_DISTRIBUTION_CODE;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, Usgov1348ViewBean.class);
	}

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

		Collection usgov1348ViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			Usgov1348ViewBean usgov1348ViewBean = new Usgov1348ViewBean();
			load(dataSetRow, usgov1348ViewBean);
			usgov1348ViewBeanColl.add(usgov1348ViewBean);
		}

		return usgov1348ViewBeanColl;
	}
}