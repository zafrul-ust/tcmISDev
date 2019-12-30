package com.tcmis.internal.hub.factory;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LessThanNWksInvViewBean;
import com.tcmis.internal.hub.beans.NWeekInventoryInputBean;


/******************************************************************************
 * CLASSNAME: LessThanNWksInvViewBeanFactory <br>
 * @version: 1.0, Jul 30, 2009 <br>
 *****************************************************************************/


public class LessThanNWksInvViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PART_NO = "PART_NO";
	public String ATTRIBUTE_AVERAGE_COST = "AVERAGE_COST";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_ON_HAND = "ON_HAND";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_SPECS = "SPECS";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_AVERAGE_WEEKLY_USAGE = "AVERAGE_WEEKLY_USAGE";
	public String ATTRIBUTE_WEEKS_LEFT = "WEEKS_LEFT";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_DAYS_TO_STOCKOUT_QTR1 = "DAYS_TO_STOCKOUT_QTR1";
	public String ATTRIBUTE_DAYS_TO_STOCKOUT_QTR2 = "DAYS_TO_STOCKOUT_QTR2";
	public String ATTRIBUTE_DAYS_TO_STOCKOUT_QTR3 = "DAYS_TO_STOCKOUT_QTR3";
	public String ATTRIBUTE_DAYS_TO_STOCKOUT_QTR4 = "DAYS_TO_STOCKOUT_QTR4";

	//table name
	public String TABLE = "LESS_THAN_N_WKS_INV_VIEW";


	//constructor
	public LessThanNWksInvViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("partNo")) {
			return ATTRIBUTE_PART_NO;
		}
		else if(attributeName.equals("averageCost")) {
			return ATTRIBUTE_AVERAGE_COST;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("onHand")) {
			return ATTRIBUTE_ON_HAND;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("specs")) {
			return ATTRIBUTE_SPECS;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("averageWeeklyUsage")) {
			return ATTRIBUTE_AVERAGE_WEEKLY_USAGE;
		}
		else if(attributeName.equals("weeksLeft")) {
			return ATTRIBUTE_WEEKS_LEFT;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("daysToStockoutQtr1")) {
			return ATTRIBUTE_DAYS_TO_STOCKOUT_QTR1;
		}
		else if(attributeName.equals("daysToStockoutQtr2")) {
			return ATTRIBUTE_DAYS_TO_STOCKOUT_QTR2;
		}
		else if(attributeName.equals("daysToStockoutQtr3")) {
			return ATTRIBUTE_DAYS_TO_STOCKOUT_QTR3;
		}
		else if(attributeName.equals("daysToStockoutQtr4")) {
			return ATTRIBUTE_DAYS_TO_STOCKOUT_QTR4;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, LessThanNWksInvViewBean.class);
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

		Collection lessThanNWksInvViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			LessThanNWksInvViewBean lessThanNWksInvViewBean = new LessThanNWksInvViewBean();
			load(dataSetRow, lessThanNWksInvViewBean);
			lessThanNWksInvViewBeanColl.add(lessThanNWksInvViewBean);
		}

		return lessThanNWksInvViewBeanColl;
	}
	public Collection selectUsingProc(NWeekInventoryInputBean inputBean, PersonnelBean personnelBean)
	throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection c = null;
		connection.setAutoCommit(false);
		Collection inArgs = new Vector(5);
		if (StringHandler.isBlankString(inputBean.getOpsEntityId()))
		{
			inArgs.add("");
		}
		else
		{
			inArgs.add(inputBean.getOpsEntityId());
		}

		if (StringHandler.isBlankString(inputBean.getInventoryGroup()))
		{
			inArgs.add("My Inventory Groups");
		}
		else
		{
			inArgs.add(inputBean.getInventoryGroup());
		}

		if (StringHandler.isBlankString(inputBean.getHub()))
		{
			inArgs.add("");
		}
		else
		{
			inArgs.add(inputBean.getHub());
		}

		inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));

		if(!StringHandler.isBlankString(inputBean.getSearchArgument()))
		{
			inArgs.add(new BigDecimal(inputBean.getSearchArgument()));
		}
		else
		{
			inArgs.add(new BigDecimal("30"));
		}

		Vector outArgs = new Vector();

		outArgs.add( new Integer(java.sql.Types.VARCHAR) );

		if (log.isDebugEnabled()) {
			log.debug("inArgs for p_low_inventory:"+inArgs);
		}
		SqlManager sqlManager = new SqlManager();
		Vector result = (Vector)sqlManager.doProcedure(connection, "p_low_inventory", inArgs, outArgs);
		String query = null;

		if( result.size() !=0 )
			query = ((Vector<String>)result).get(0);
		else
			query= null;

		if(null!=query)
		{
			c = selectUsingQuery(query, connection);

		}
		connection.setAutoCommit(true);
		this.getDbManager().returnConnection(connection);
		return c;
	}

	public Collection selectUsingQuery(String query, Connection conn) throws BaseException {

		Collection lessThanNWeekInventoryBeanColl = new Vector();

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			LessThanNWksInvViewBean lessThanNWksInvViewBean = new LessThanNWksInvViewBean();
			load(dataSetRow, lessThanNWksInvViewBean);
			lessThanNWeekInventoryBeanColl.add(lessThanNWksInvViewBean);
		}

		return lessThanNWeekInventoryBeanColl;
	}
}