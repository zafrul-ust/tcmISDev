package com.tcmis.internal.hub.factory;

import java.sql.Connection;
import java.sql.Timestamp;
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
import com.tcmis.internal.hub.beans.UsageTrendViewBean;
import com.tcmis.internal.hub.beans.UsageTrendViewInputBean;

/******************************************************************************
 * CLASSNAME: UsageTrendViewBeanFactory <br>
 * @version: 1.0, Feb 1, 2005 <br>
 *****************************************************************************/

public class UsageTrendViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
 public String ATTRIBUTE_PACKAGING = "PACKAGING";
 public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_COUNT_UOM = "COUNT_UOM";
 public String ATTRIBUTE_BASELINE_ANNUAL_USAGE = "BASELINE_ANNUAL_USAGE";
 public String ATTRIBUTE_BASELINE_ANNUAL_USAGE_YTD =
	"BASELINE_ANNUAL_USAGE_YTD";
 public String ATTRIBUTE_CONTRACT_START_DATE = "CONTRACT_START_DATE";
 public String ATTRIBUTE_LATEST_ANIVERSARY_DATE = "LATEST_ANIVERSARY_DATE";
 public String ATTRIBUTE_PRIOR_YEAR_ANIVERSARY_DATE =
	"PRIOR_YEAR_ANIVERSARY_DATE";
 public String ATTRIBUTE_MONTH0 = "MONTH0";
 public String ATTRIBUTE_MONTH1 = "MONTH1";
 public String ATTRIBUTE_MONTH2 = "MONTH2";
 public String ATTRIBUTE_MONTH3 = "MONTH3";
 public String ATTRIBUTE_MONTH4 = "MONTH4";
 public String ATTRIBUTE_MONTH5 = "MONTH5";
 public String ATTRIBUTE_MONTH6 = "MONTH6";
 public String ATTRIBUTE_MONTH7 = "MONTH7";
 public String ATTRIBUTE_MONTH8 = "MONTH8";
 public String ATTRIBUTE_MONTH9 = "MONTH9";
 public String ATTRIBUTE_MONTH10 = "MONTH10";
 public String ATTRIBUTE_MONTH11 = "MONTH11";
 public String ATTRIBUTE_PRIOR_YEAR_USAGE = "PRIOR_YEAR_USAGE";
 public String ATTRIBUTE_PRIOR_YEAR_USAGE_YTD = "PRIOR_YEAR_USAGE_YTD";
 public String ATTRIBUTE_CURRENT_YEAR_YTD = "CURRENT_YEAR_YTD";

 //table name
 //public String TABLE = "USAGE_TREND_VIEW";

 //constructor
 public UsageTrendViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 public String getColumnName(String attributeName) {
	if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("itemDesc")) {
	 return ATTRIBUTE_ITEM_DESC;
	}
	else if (attributeName.equals("packaging")) {
	 return ATTRIBUTE_PACKAGING;
	}
	else if (attributeName.equals("manufacturer")) {
	 return ATTRIBUTE_MANUFACTURER;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("countUom")) {
	 return ATTRIBUTE_COUNT_UOM;
	}
	else if (attributeName.equals("baselineAnnualUsage")) {
	 return ATTRIBUTE_BASELINE_ANNUAL_USAGE;
	}
	else if (attributeName.equals("baselineAnnualUsageYtd")) {
	 return ATTRIBUTE_BASELINE_ANNUAL_USAGE_YTD;
	}
	else if (attributeName.equals("contractStartDate")) {
	 return ATTRIBUTE_CONTRACT_START_DATE;
	}
	else if (attributeName.equals("latestAniversaryDate")) {
	 return ATTRIBUTE_LATEST_ANIVERSARY_DATE;
	}
	else if (attributeName.equals("priorYearAniversaryDate")) {
	 return ATTRIBUTE_PRIOR_YEAR_ANIVERSARY_DATE;
	}
	else if (attributeName.equals("month0")) {
	 return ATTRIBUTE_MONTH0;
	}
	else if (attributeName.equals("month1")) {
	 return ATTRIBUTE_MONTH1;
	}
	else if (attributeName.equals("month2")) {
	 return ATTRIBUTE_MONTH2;
	}
	else if (attributeName.equals("month3")) {
	 return ATTRIBUTE_MONTH3;
	}
	else if (attributeName.equals("month4")) {
	 return ATTRIBUTE_MONTH4;
	}
	else if (attributeName.equals("month5")) {
	 return ATTRIBUTE_MONTH5;
	}
	else if (attributeName.equals("month6")) {
	 return ATTRIBUTE_MONTH6;
	}
	else if (attributeName.equals("month7")) {
	 return ATTRIBUTE_MONTH7;
	}
	else if (attributeName.equals("month8")) {
	 return ATTRIBUTE_MONTH8;
	}
	else if (attributeName.equals("month9")) {
	 return ATTRIBUTE_MONTH9;
	}
	else if (attributeName.equals("month10")) {
	 return ATTRIBUTE_MONTH10;
	}
	else if (attributeName.equals("month11")) {
	 return ATTRIBUTE_MONTH11;
	}
	else if (attributeName.equals("priorYearUsage")) {
	 return ATTRIBUTE_PRIOR_YEAR_USAGE;
	}
	else if (attributeName.equals("priorYearUsageYtd")) {
	 return ATTRIBUTE_PRIOR_YEAR_USAGE_YTD;
	}
	else if (attributeName.equals("currentYearYtd")) {
	 return ATTRIBUTE_CURRENT_YEAR_YTD;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, UsageTrendViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(UsageTrendViewBean usageTrendViewBean)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("itemId", "=",
	"" + usageTrendViewBean.getItemId());
	Connection connection = this.getDbManager().getConnection();
	int result = this.delete(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
	 }
	 public int delete(UsageTrendViewBean usageTrendViewBean, Connection conn)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("itemId", "=",
	"" + usageTrendViewBean.getItemId());
	return delete(criteria, conn);
	 }
	*/

 public int delete(SearchCriteria criteria) throws BaseException {

	Connection connection = getDbManager().getConnection();
	int result = delete(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
 }

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	//String sqlQuery = " delete from " + TABLE + " " +
	String sqlQuery = " delete from " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	 public int insert(UsageTrendViewBean usageTrendViewBean)
	throws BaseException {
	Connection connection = getDbManager().getConnection();
	int result = insert(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
	 }
	 public int insert(UsageTrendViewBean usageTrendViewBean, Connection conn)
	throws BaseException {
	SqlManager sqlManager = new SqlManager();
	String query  =
	"insert into " + TABLE + " (" +
	ATTRIBUTE_ITEM_ID + "," +
	ATTRIBUTE_ITEM_DESC + "," +
	ATTRIBUTE_MANUFACTURER + "," +
	ATTRIBUTE_SUPPLIER + "," +
	ATTRIBUTE_PACKAGING + "," +
	ATTRIBUTE_COUNT_UOM + "," +
	ATTRIBUTE_ANNUAL_BASELINE + "," +
	ATTRIBUTE_PRIOR_YEAR + "," +
	ATTRIBUTE_BASE_LINE_YTD + "," +
	ATTRIBUTE_PREVIOUS_YEAR_YTD + "," +
	ATTRIBUTE_CURRENT_YEAR_YTD + "," +
	ATTRIBUTE_USAGE_JAN + "," +
	ATTRIBUTE_USAGE_FEB + "," +
	ATTRIBUTE_USAGE_MAR + "," +
	ATTRIBUTE_USAGE_APR + "," +
	ATTRIBUTE_USAGE_MAY + "," +
	ATTRIBUTE_USAGE_JUN + "," +
	ATTRIBUTE_USAGE_JUL + "," +
	ATTRIBUTE_USAGE_AUG + "," +
	ATTRIBUTE_USAGE_SEP + "," +
	ATTRIBUTE_USAGE_OCT + "," +
	ATTRIBUTE_USAGE_NOV + "," +
	ATTRIBUTE_USAGE_DEC + ")" +
	SqlHandler.delimitString(usageTrendViewBean.getItemId()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getItemDesc()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getManufacturer()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getSupplier()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getPackaging()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getCountUom()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getAnnualBaseline()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getPriorYear()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getBaseLineYtd()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getPreviousYearYtd()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getCurrentYearYtd()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageJan()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageFeb()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageMar()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageApr()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageMay()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageJun()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageJul()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageAug()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageSep()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageOct()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageNov()) + "," +
	SqlHandler.delimitString(usageTrendViewBean.getUsageDec()) + ")";
	return sqlManager.update(conn, query);
	 }
//update
	 public int update(UsageTrendViewBean usageTrendViewBean)
	throws BaseException {
	Connection connection = getDbManager().getConnection();
	int result = update(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
	 }
	 public int update(UsageTrendViewBean usageTrendViewBean, Connection conn)
	throws BaseException {
	String query  = "update " + TABLE + " set " +
	ATTRIBUTE_ITEM_ID + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getItemId()) + "," +
	ATTRIBUTE_ITEM_DESC + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getItemDesc()) + "," +
	ATTRIBUTE_MANUFACTURER + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getManufacturer()) + "," +
	ATTRIBUTE_SUPPLIER + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getSupplier()) + "," +
	ATTRIBUTE_PACKAGING + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getPackaging()) + "," +
	ATTRIBUTE_COUNT_UOM + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getCountUom()) + "," +
	ATTRIBUTE_ANNUAL_BASELINE + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getAnnualBaseline()) + "," +
	ATTRIBUTE_PRIOR_YEAR + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getPriorYear()) + "," +
	ATTRIBUTE_BASE_LINE_YTD + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getBaseLineYtd()) + "," +
	ATTRIBUTE_PREVIOUS_YEAR_YTD + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getPreviousYearYtd()) + "," +
	ATTRIBUTE_CURRENT_YEAR_YTD + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getCurrentYearYtd()) + "," +
	ATTRIBUTE_USAGE_JAN + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageJan()) + "," +
	ATTRIBUTE_USAGE_FEB + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageFeb()) + "," +
	ATTRIBUTE_USAGE_MAR + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageMar()) + "," +
	ATTRIBUTE_USAGE_APR + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageApr()) + "," +
	ATTRIBUTE_USAGE_MAY + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageMay()) + "," +
	ATTRIBUTE_USAGE_JUN + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageJun()) + "," +
	ATTRIBUTE_USAGE_JUL + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageJul()) + "," +
	ATTRIBUTE_USAGE_AUG + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageAug()) + "," +
	ATTRIBUTE_USAGE_SEP + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageSep()) + "," +
	ATTRIBUTE_USAGE_OCT + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageOct()) + "," +
	ATTRIBUTE_USAGE_NOV + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageNov()) + "," +
	ATTRIBUTE_USAGE_DEC + "=" +
	 SqlHandler.delimitString(usageTrendViewBean.getUsageDec()) + " " +
	"where " + ATTRIBUTE_ITEM_ID + "=" +
	 StringHandler.nullIfZero(usageTrendViewBean.getItemId());
	return new SqlManager().update(conn, query);
	 }
	*/

 //select
 public Collection select(UsageTrendViewInputBean bean) throws BaseException {
	Connection connection = this.getDbManager().getConnection();
  /*log.info(bean.getCompanyId());
	log.info(bean.getInventoryGroup());
	log.info(bean.getfacilityId());
	log.info(bean.getAniversaryDate());
	log.info(bean.getUnitsOrDollars());*/

	Collection cin = new Vector();
  if (bean.getCompanyId() != null && bean.getCompanyId().length() > 0) {
	 cin.add(new String(bean.getCompanyId()));
	}
	else {
	 cin.add("");
	}

	if (bean.getInventoryGroup() != null && bean.getInventoryGroup().length() > 0 ) {
	 cin.add(new String(bean.getInventoryGroup()));
	}
	else {
	 cin.add("");
	}

	if (bean.getfacilityId() != null && bean.getfacilityId().trim().length() > 0 &&
	 !"All".equalsIgnoreCase(bean.getfacilityId())) {
	 cin.add(new String(bean.getfacilityId()));
	}
	else {
	 cin.add("");
	}

	if (bean.getAniversaryDate() != null) {
	 try {
		 cin.add(new Timestamp(bean.getAniversaryDate().getTime()));
	//	cin.add(com.tcmis.common.util.DateHandler.getTimestampFromString("MM/dd/yyyy",bean.getAniversaryDate()));
	 }
	 catch (Exception ex) {
		ex.printStackTrace();
	 }
	}
	else {
	 cin.add("");
	}

	if (bean.getUnitsOrDollars() != null &&
	 bean.getUnitsOrDollars().trim().length() > 0) {
	 cin.add(new String(bean.getUnitsOrDollars()));
	}
	else {
	 cin.add("");
	}

	Collection cout = new Vector();
	cout.add(new Integer(java.sql.Types.VARCHAR));
	Collection result = this.getDbManager().doProcedure("P_USAGE_TREND", cin,
	 cout);

	Iterator i11 = result.iterator();
	String searchQuery = "";
	while (i11.hasNext()) {
	 searchQuery = (String) i11.next(); ;
	}

	Collection c = select(searchQuery, connection);
	this.getDbManager().returnConnection(connection);
	return c;
 }

 public Collection select(String query, Connection conn) throws BaseException {

	Collection usageTrendViewBeanColl = new Vector();

	/*String query = "select * from " + TABLE + " " +
	 getWhereClause(criteria);*/

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 UsageTrendViewBean usageTrendViewBean = new UsageTrendViewBean();
	 load(dataSetRow, usageTrendViewBean);
	 usageTrendViewBeanColl.add(usageTrendViewBean);
	}

	return usageTrendViewBeanColl;
 }
}