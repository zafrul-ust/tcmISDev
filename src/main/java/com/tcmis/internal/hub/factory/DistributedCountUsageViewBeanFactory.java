package com.tcmis.internal.hub.factory;


import java.math.BigDecimal;
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
import com.tcmis.internal.hub.beans.DistributedCountUsageInputBean;
import com.tcmis.internal.hub.beans.DistributedCountUsageViewBean;
import com.tcmis.internal.hub.beans.DistributedCountUsageViewItemBean;
import com.tcmis.internal.hub.beans.DistributedCountUsageViewWorkAreaBean;
/******************************************************************************
 * CLASSNAME: DistributedCountUsageViewBeanFactory <br>
 * @version: 1.0, Aug 25, 2006 <br>
 *****************************************************************************/


public class DistributedCountUsageViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_COUNT_ID = "COUNT_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_USAGE = "USAGE";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_DISTRIBUTED_USAGE = "DISTRIBUTED_USAGE";
	public String ATTRIBUTE_UOM_DISTRIBUTED_USAGE = "UOM_DISTRIBUTED_USAGE";
	public String ATTRIBUTE_DATE_PROCESSED = "DATE_PROCESSED";
	public String ATTRIBUTE_COUNT_STATUS = "COUNT_STATUS";
	public String ATTRIBUTE_COUNT_TYPE = "COUNT_TYPE";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

	//table name
	public String TABLE = "DISTRIBUTED_COUNT_USAGE_VIEW";


	//constructor
	public DistributedCountUsageViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("countId")) {
			return ATTRIBUTE_COUNT_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("usage")) {
			return ATTRIBUTE_USAGE;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("distributedUsage")) {
			return ATTRIBUTE_DISTRIBUTED_USAGE;
		}
		else if(attributeName.equals("uomDistributedUsage")) {
			return ATTRIBUTE_UOM_DISTRIBUTED_USAGE;
		}
		else if(attributeName.equals("dateProcessed")) {
			return ATTRIBUTE_DATE_PROCESSED;
		}
		else if(attributeName.equals("countStatus")) {
			return ATTRIBUTE_COUNT_STATUS;
		}
		else if(attributeName.equals("countType")) {
			return ATTRIBUTE_COUNT_TYPE;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, DistributedCountUsageViewBean.class);
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
	public Collection select(SearchCriteria criteria)
	throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,null,null,false,connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	//call P_UPDATE_COUNT_DISTRIBUTION
	public Collection updateCountDistribution(DistributedCountUsageViewBean partBean,
			DistributedCountUsageViewWorkAreaBean waUsageBean,
			BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (partBean.getCountId() != null) {
			cin.add(partBean.getCountId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCompanyId() != null && partBean.getCompanyId().length() > 0) {
			cin.add(partBean.getCompanyId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCatalogId() != null && partBean.getCatalogId().length() > 0) {
			cin.add(partBean.getCatalogId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCatPartNo() != null && partBean.getCatPartNo().length() > 0) {
			cin.add(partBean.getCatPartNo());
		}
		else {
			cin.add("");
		}

		if (partBean.getPartGroupNo() != null) {
			cin.add(partBean.getPartGroupNo());
		}
		else {
			cin.add("");
		}

		if (waUsageBean.getItemId() != null && waUsageBean.getItemId().length() > 0) {
			cin.add(waUsageBean.getItemId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCountType() != null && partBean.getCountType().length() > 0) {
			cin.add(partBean.getCountType());
		}
		else {
			cin.add("");
		}

		if (waUsageBean.getFacilityId() != null &&
				waUsageBean.getFacilityId().length() > 0) {
			cin.add(waUsageBean.getFacilityId());
		}
		else {
			cin.add("");
		}

		if (waUsageBean.getApplication() != null &&
				waUsageBean.getApplication().length() > 0) {
			cin.add(waUsageBean.getApplication());
		}
		else {
			cin.add("");
		}

		if (waUsageBean.getUomDistributedUsage() != null) {
			cin.add(waUsageBean.getUomDistributedUsage());
		}
		else {
			cin.add("");
		}

		if (log.isDebugEnabled()) {
			log.debug("CatalogId  " + partBean.getCatalogId() + "");
			log.debug("catPartNo  " + partBean.getCatPartNo() + "");
			log.debug("countId  " + partBean.getCountId() + "");
			log.debug("FacilityId  " + waUsageBean.getFacilityId() + "");
			log.debug("Application  " + waUsageBean.getApplication() + "");
			log.debug("uomDistributedUsage  " + waUsageBean.getUomDistributedUsage() + "");
		}
		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.INTEGER));
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection cOptionalIn = new Vector();
		cOptionalIn.add("");

		if (partBean.getCatalogCompanyId() != null) {
			cOptionalIn.add(partBean.getCatalogCompanyId());
		}
		else {
			cOptionalIn.add("");
		}

		Collection result = this.getDbManager().doProcedure("P_UPDATE_COUNT_DISTRIBUTION", cin,cout,cOptionalIn);
		this.getDbManager().returnConnection(connection);
		return result;
	}


	//call P_PROCESS_COUNT_DISTRIBUTION
	public Collection processCountDistribution(DistributedCountUsageViewBean partBean,
			DistributedCountUsageViewItemBean itemUsageBean,
			BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (partBean.getCountId() != null) {
			cin.add(partBean.getCountId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCompanyId() != null && partBean.getCompanyId().length() > 0) {
			cin.add(partBean.getCompanyId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCatalogId() != null && partBean.getCatalogId().length() > 0) {
			cin.add(partBean.getCatalogId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCatPartNo() != null && partBean.getCatPartNo().length() > 0) {
			cin.add(partBean.getCatPartNo());
		}
		else {
			cin.add("");
		}

		if (partBean.getPartGroupNo() != null) {
			cin.add(partBean.getPartGroupNo());
		}
		else {
			cin.add("");
		}

		if (itemUsageBean.getItemId() != null && itemUsageBean.getItemId().length() > 0) {
			cin.add(itemUsageBean.getItemId());
		}
		else {
			cin.add("");
		}

		if (partBean.getCountType() != null && partBean.getCountType().length() > 0) {
			cin.add(partBean.getCountType());
		}
		else {
			cin.add("");
		}
		cin.add(personnelId);

		if (log.isDebugEnabled()) {
			log.debug("CatalogId  " + partBean.getCatalogId() + "");
			log.debug("catPartNo  " + partBean.getCatPartNo() + "");
			log.debug("countId  " + partBean.getCountId() + "");
		}
		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.INTEGER));
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection cOptionalIn = new Vector();
		cOptionalIn.add("");

		if (partBean.getCatalogCompanyId() != null) {
			cOptionalIn.add(partBean.getCatalogCompanyId());
		}
		else {
			cOptionalIn.add("");
		}

		Collection result = this.getDbManager().doProcedure("P_PROCESS_COUNT_DISTRIBUTION", cin,cout,cOptionalIn);
		this.getDbManager().returnConnection(connection);
		return result;
	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
			DistributedCountUsageInputBean bean,
			boolean iscollection) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, bean, iscollection, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
			DistributedCountUsageInputBean bean, boolean iscollection,
			Connection conn) throws BaseException {
		Collection distributedCountUsageViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " ";

		String whereClause = getWhereClause(criteria);
		if (iscollection) {
			if (whereClause.trim().length() > 0) {
				whereClause += "and ";
			}

			whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " +
			ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION +
			" where " + ATTRIBUTE_HUB + " = '" + bean.getSourceHub() + "' and " +
			ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() +
			"') ";
		}

		query += whereClause + " ";
		if (sortCriteria != null) {
			query += getOrderByClause(sortCriteria);
		}

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			DistributedCountUsageViewBean distributedCountUsageViewBean = new
			DistributedCountUsageViewBean();
			load(dataSetRow, distributedCountUsageViewBean);
			distributedCountUsageViewBeanColl.add(distributedCountUsageViewBean);
		}

		return distributedCountUsageViewBeanColl;
	}
}