package com.tcmis.internal.catalog.factory;

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
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.MaterialBean;

/******************************************************************************
 * CLASSNAME: MaterialBeanFactory <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/

public class MaterialBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_MATERIAL_CATEGORY = "MATERIAL_CATEGORY";
	public String ATTRIBUTE_MFG_ID = "MFG_ID";
	public String ATTRIBUTE_HAZARD_CLASS = "HAZARD_CLASS";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	public String ATTRIBUTE_PACKING_GROUP = "PACKING_GROUP";
	public String ATTRIBUTE_UN_NUMBER = "UN_NUMBER";
	public String ATTRIBUTE_NA_NUMBER = "NA_NUMBER";
	public String ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS = "SUBSIDIARY_HAZARD_CLASS";
	public String ATTRIBUTE_GROUND_SHIPPING_NAME = "GROUND_SHIPPING_NAME";
	public String ATTRIBUTE_AIR_SHIPPING_NAME = "AIR_SHIPPING_NAME";
	public String ATTRIBUTE_EMERGENCY_RESPONSE_GUIDE_NO = "EMERGENCY_RESPONSE_GUIDE_NO";
	public String ATTRIBUTE_REVIEW = "REVIEW";
	public String ATTRIBUTE_DRY_ICE = "DRY_ICE";
	public String ATTRIBUTE_ERG = "ERG";
	public String ATTRIBUTE_MSDS_ON_LINE = "MSDS_ON_LINE";
	public String ATTRIBUTE_PRODUCT_CODE = "PRODUCT_CODE";
	public String ATTRIBUTE_CUSTOMER_ONLY_MSDS = "CUSTOMER_ONLY_MSDS";
	public String ATTRIBUTE_MFG_DESC = "MFG_DESC";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";

	//table name
	public String TABLE = "MATERIAL";
	public String MFG_TABLE = "MANUFACTURER";

	//constructor
	public MaterialBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if (attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if (attributeName.equals("materialCategory")) {
			return ATTRIBUTE_MATERIAL_CATEGORY;
		}
		else if (attributeName.equals("mfgId")) {
			return ATTRIBUTE_MFG_ID;
		}
		else if (attributeName.equals("hazardClass")) {
			return ATTRIBUTE_HAZARD_CLASS;
		}
		else if (attributeName.equals("tradeName")) {
			return ATTRIBUTE_TRADE_NAME;
		}
		else if (attributeName.equals("packingGroup")) {
			return ATTRIBUTE_PACKING_GROUP;
		}
		else if (attributeName.equals("unNumber")) {
			return ATTRIBUTE_UN_NUMBER;
		}
		else if (attributeName.equals("naNumber")) {
			return ATTRIBUTE_NA_NUMBER;
		}
		else if (attributeName.equals("subsidiaryHazardClass")) {
			return ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS;
		}
		else if (attributeName.equals("groundShippingName")) {
			return ATTRIBUTE_GROUND_SHIPPING_NAME;
		}
		else if (attributeName.equals("airShippingName")) {
			return ATTRIBUTE_AIR_SHIPPING_NAME;
		}
		else if (attributeName.equals("emergencyResponseGuideNo")) {
			return ATTRIBUTE_EMERGENCY_RESPONSE_GUIDE_NO;
		}
		else if (attributeName.equals("review")) {
			return ATTRIBUTE_REVIEW;
		}
		else if (attributeName.equals("dryIce")) {
			return ATTRIBUTE_DRY_ICE;
		}
		else if (attributeName.equals("erg")) {
			return ATTRIBUTE_ERG;
		}
		else if (attributeName.equals("msdsOnLine")) {
			return ATTRIBUTE_MSDS_ON_LINE;
		}
		else if (attributeName.equals("productCode")) {
			return ATTRIBUTE_PRODUCT_CODE;
		}
		else if (attributeName.equals("customerOnlyMsds")) {
			return ATTRIBUTE_CUSTOMER_ONLY_MSDS;
		}
		else if (attributeName.equals("mfgDesc")) {
			return ATTRIBUTE_MFG_DESC;
		}
		else if (attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, MaterialBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(MaterialBean materialBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + materialBean.getCompanyId());

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


	public int delete(MaterialBean materialBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + materialBean.getCompanyId());

		return delete(criteria, conn);
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
	 */

	public BigDecimal getNextMaterialId() throws BaseException {
		Connection connection = null;
		int result = 0;
		BigDecimal mfgId;
		try {
			connection = getDbManager().getConnection();
			mfgId = getNextMaterialId(connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return mfgId;
	}

	public BigDecimal getNextMaterialId(Connection conn) throws BaseException {
		String query = "select global.material_seq.nextval from dual ";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
		BigDecimal nextMaterialId = new BigDecimal(0);
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			if (log.isDebugEnabled()) {
				log.debug("dataSetRow.toString() = [" + dataSetRow.toString() + "]; ");
			}
			nextMaterialId = new BigDecimal(dataSetRow.getInt("NEXTVAL"));
			if (log.isDebugEnabled()) {
				log.debug("nextMaterialId.toString() = [" + nextMaterialId.toString() + "]; ");
			}
		}
		return nextMaterialId;
	}

	public int insert(MaterialBean materialBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(materialBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(MaterialBean materialBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into global." + TABLE + " (" + ATTRIBUTE_MATERIAL_ID + ", " + ATTRIBUTE_MATERIAL_DESC + ", " + ATTRIBUTE_TRADE_NAME + ", " + ATTRIBUTE_MFG_ID + ")" +
		/*
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_MATERIAL_ID + "," +
		ATTRIBUTE_MATERIAL_DESC + "," +
		ATTRIBUTE_MATERIAL_CATEGORY + "," +
		ATTRIBUTE_MFG_ID + "," +
		ATTRIBUTE_HAZARD_CLASS + "," +
		ATTRIBUTE_TRADE_NAME + ")" + // "," +
		ATTRIBUTE_PACKING_GROUP + "," +
		ATTRIBUTE_UN_NUMBER + "," +
		ATTRIBUTE_NA_NUMBER + "," +
		ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS + "," +
		ATTRIBUTE_GROUND_SHIPPING_NAME + "," +
		ATTRIBUTE_AIR_SHIPPING_NAME + "," +
		ATTRIBUTE_EMERGENCY_RESPONSE_GUIDE_NO + "," +
		ATTRIBUTE_REVIEW + "," +
		ATTRIBUTE_DRY_ICE + "," +
		ATTRIBUTE_ERG + "," +
		ATTRIBUTE_MSDS_ON_LINE + ")" +
		 */
				" values (" + materialBean.getMaterialId() + "," + SqlHandler.delimitString(materialBean.getMaterialDesc()) + "," + SqlHandler.delimitString(materialBean.getTradeName()) + "," + materialBean.getMfgId() + ")";

		/*
			SqlHandler.delimitString(materialBean.getCompanyId()) + "," +
			materialBean.getMaterialId() + "," +
			SqlHandler.delimitString(materialBean.getMaterialDesc()) + "," +
			SqlHandler.delimitString(materialBean.getMaterialCategory()) + "," +
			materialBean.getMfgId() + "," +
			SqlHandler.delimitString(materialBean.getHazardClass()) + "," +
			SqlHandler.delimitString(materialBean.getTradeName()) + "," +
			SqlHandler.delimitString(materialBean.getPackingGroup()) + "," +
			SqlHandler.delimitString(materialBean.getUnNumber()) + "," +
			SqlHandler.delimitString(materialBean.getNaNumber()) + "," +
			SqlHandler.delimitString(materialBean.getSubsidiaryHazardClass()) + "," +
			SqlHandler.delimitString(materialBean.getGroundShippingName()) + "," +
			SqlHandler.delimitString(materialBean.getAirShippingName()) + "," +
			SqlHandler.delimitString(materialBean.getEmergencyResponseGuideNo()) + "," +
			SqlHandler.delimitString(materialBean.getReview()) + "," +
			SqlHandler.delimitString(materialBean.getDryIce()) + "," +
			materialBean.getErg() + "," +
			SqlHandler.delimitString(materialBean.getMsdsOnLine()) + ")";
		 */
		return sqlManager.update(conn, query);
	}

	/*
	//update
	public int update(MaterialBean materialBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(materialBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(MaterialBean materialBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(materialBean.getCompanyId()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" +
				StringHandler.nullIfZero(materialBean.getMaterialId()) + "," +
			ATTRIBUTE_MATERIAL_DESC + "=" +
				SqlHandler.delimitString(materialBean.getMaterialDesc()) + "," +
			ATTRIBUTE_MATERIAL_CATEGORY + "=" +
				SqlHandler.delimitString(materialBean.getMaterialCategory()) + "," +
			ATTRIBUTE_MFG_ID + "=" +
				StringHandler.nullIfZero(materialBean.getMfgId()) + "," +
			ATTRIBUTE_HAZARD_CLASS + "=" +
				SqlHandler.delimitString(materialBean.getHazardClass()) + "," +
			ATTRIBUTE_TRADE_NAME + "=" +
				SqlHandler.delimitString(materialBean.getTradeName()) + "," +
			ATTRIBUTE_PACKING_GROUP + "=" +
				SqlHandler.delimitString(materialBean.getPackingGroup()) + "," +
			ATTRIBUTE_UN_NUMBER + "=" +
				SqlHandler.delimitString(materialBean.getUnNumber()) + "," +
			ATTRIBUTE_NA_NUMBER + "=" +
				SqlHandler.delimitString(materialBean.getNaNumber()) + "," +
			ATTRIBUTE_SUBSIDIARY_HAZARD_CLASS + "=" +
				SqlHandler.delimitString(materialBean.getSubsidiaryHazardClass()) + "," +
			ATTRIBUTE_GROUND_SHIPPING_NAME + "=" +
				SqlHandler.delimitString(materialBean.getGroundShippingName()) + "," +
			ATTRIBUTE_AIR_SHIPPING_NAME + "=" +
				SqlHandler.delimitString(materialBean.getAirShippingName()) + "," +
			ATTRIBUTE_EMERGENCY_RESPONSE_GUIDE_NO + "=" +
				SqlHandler.delimitString(materialBean.getEmergencyResponseGuideNo()) + "," +
			ATTRIBUTE_REVIEW + "=" +
				SqlHandler.delimitString(materialBean.getReview()) + "," +
			ATTRIBUTE_DRY_ICE + "=" +
				SqlHandler.delimitString(materialBean.getDryIce()) + "," +
			ATTRIBUTE_ERG + "=" +
				StringHandler.nullIfZero(materialBean.getErg()) + "," +
			ATTRIBUTE_MSDS_ON_LINE + "=" +
				SqlHandler.delimitString(materialBean.getMsdsOnLine()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				materialBean.getCompanyId();

		return new SqlManager().update(conn, query);
	}
	 */
	public Collection select(String manufacturer, String searchArgument, String excludeMaterialIds) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(manufacturer, searchArgument, excludeMaterialIds, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(String manufacturer, String searchArgument, String excludeMaterialIds, Connection conn) throws BaseException {
		Collection materialBeanColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder("select m.*, mfg.MFG_DESC, mfg.COUNTRY_ABBREV from "); // + TABLE + " " +
		queryBuffer.append(TABLE).append(" m, ").append(MFG_TABLE).append(" mfg");
		queryBuffer.append(" where  lower(m.MATERIAL_DESC || m.MATERIAL_ID || m.TRADE_NAME) like lower('%");
		queryBuffer.append(SqlHandler.validQuery(searchArgument));
		queryBuffer.append("%')");
		if (!StringHandler.isBlankString(manufacturer)) {
			queryBuffer.append(" and m.MFG_ID = ");
			queryBuffer.append(manufacturer);
		}
		queryBuffer.append(" and mfg.MFG_ID=m.MFG_ID");
		if (!StringHandler.isBlankString(excludeMaterialIds))
			queryBuffer.append(" and m.material_id not in (").append(excludeMaterialIds).append(")");
		queryBuffer.append("  order by MATERIAL_DESC asc");

		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString());

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MaterialBean materialBean = new MaterialBean();
			load(dataSetRow, materialBean);
			materialBeanColl.add(materialBean);
			//   log.debug("materialBean.toString() = [" + materialBean.toString() + "]; ");
		}
		return materialBeanColl;
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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection materialBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MaterialBean materialBean = new MaterialBean();
			load(dataSetRow, materialBean);
			materialBeanColl.add(materialBean);
		}
		return materialBeanColl;
	}
}