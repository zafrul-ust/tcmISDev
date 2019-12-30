package com.tcmis.client.waste.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.waste.beans.TsdfReceivingViewBean;
import com.tcmis.client.waste.beans.TsdfWasteReceivingInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: TsdfReceivingViewBeanFactory <br>
 * @version: 1.0, Dec 19, 2006 <br>
 *****************************************************************************/

public class TsdfReceivingViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_GENERATOR_COMPANY_ID = "GENERATOR_COMPANY_ID";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_ACTUAL_SHIP_DATE = "ACTUAL_SHIP_DATE";
	public String ATTRIBUTE_GENERATOR_FACILITY_ID = "GENERATOR_FACILITY_ID";
	public String ATTRIBUTE_ORDER_NO = "ORDER_NO";
	public String ATTRIBUTE_CONTAINER_ID = "CONTAINER_ID";
	public String ATTRIBUTE_TSDF_COMPANY_ID = "TSDF_COMPANY_ID";
	public String ATTRIBUTE_TSDF_FACILITY_ID = "TSDF_FACILITY_ID";
	public String ATTRIBUTE_TSDF_FACILITY_NAME = "TSDF_FACILITY_NAME";
	public String ATTRIBUTE_WASTE_ITEM_ID = "WASTE_ITEM_ID";
	public String ATTRIBUTE_VENDOR_ID = "VENDOR_ID";
	public String ATTRIBUTE_VENDOR_PROFILE_ID = "VENDOR_PROFILE_ID";
	public String ATTRIBUTE_PROFILE_DESCRIPTION = "PROFILE_DESCRIPTION";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_STATE_WASTE_CODES = "STATE_WASTE_CODES";
	public String ATTRIBUTE_EPA_WASTE_CODES = "EPA_WASTE_CODES";
	public String ATTRIBUTE_GENERATOR_WASTE_LOCATION_ID = "GENERATOR_WASTE_LOCATION_ID";
	public String ATTRIBUTE_TSDF_FACILITY_ID_FOR_GENERATOR = "TSDF_FACILITY_ID_FOR_GENERATOR";
	public String ATTRIBUTE_MANIFEST_ID = "MANIFEST_ID";
	public String ATTRIBUTE_MANIFEST_STATE = "MANIFEST_STATE";
	public String ATTRIBUTE_MANIFEST_COUNTRY = "MANIFEST_COUNTRY";

	//table name
	public String TABLE = "TSDF_RECEIVING_VIEW";

	//constructor
	public TsdfReceivingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("generatorCompanyId")) {
			return ATTRIBUTE_GENERATOR_COMPANY_ID;
		} else if (attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		} else if (attributeName.equals("actualShipDate")) {
			return ATTRIBUTE_ACTUAL_SHIP_DATE;
		} else if (attributeName.equals("generatorFacilityId")) {
			return ATTRIBUTE_GENERATOR_FACILITY_ID;
		} else if (attributeName.equals("orderNo")) {
			return ATTRIBUTE_ORDER_NO;
		} else if (attributeName.equals("containerId")) {
			return ATTRIBUTE_CONTAINER_ID;
		} else if (attributeName.equals("tsdfCompanyId")) {
			return ATTRIBUTE_TSDF_COMPANY_ID;
		} else if (attributeName.equals("tsdfFacilityId")) {
			return ATTRIBUTE_TSDF_FACILITY_ID;
		} else if (attributeName.equals("tsdfFacilityName")) {
			return ATTRIBUTE_TSDF_FACILITY_NAME;
		} else if (attributeName.equals("wasteItemId")) {
			return ATTRIBUTE_WASTE_ITEM_ID;
		} else if (attributeName.equals("vendorId")) {
			return ATTRIBUTE_VENDOR_ID;
		} else if (attributeName.equals("vendorProfileId")) {
			return ATTRIBUTE_VENDOR_PROFILE_ID;
		} else if (attributeName.equals("profileDescription")) {
			return ATTRIBUTE_PROFILE_DESCRIPTION;
		} else if (attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		} else if (attributeName.equals("stateWasteCodes")) {
			return ATTRIBUTE_STATE_WASTE_CODES;
		} else if (attributeName.equals("epaWasteCodes")) {
			return ATTRIBUTE_EPA_WASTE_CODES;
		} else if (attributeName.equals("generatorWasteLocationId")) {
			return ATTRIBUTE_GENERATOR_WASTE_LOCATION_ID;
		} else if (attributeName.equals("tsdfFacilityIdForGenerator")) {
			return ATTRIBUTE_TSDF_FACILITY_ID_FOR_GENERATOR;
		} else if (attributeName.equals("manifestId")) {
			return ATTRIBUTE_MANIFEST_ID;
		} else if (attributeName.equals("manifestState")) {
			return ATTRIBUTE_MANIFEST_STATE;
		} else if (attributeName.equals("manifestCountry")) {
			return ATTRIBUTE_MANIFEST_COUNTRY;
		} else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, TsdfReceivingViewBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
//delete
   public int delete(TsdfReceivingViewBean tsdfReceivingViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("generatorCompanyId", "SearchCriterion.EQUALS",
     "" + tsdfReceivingViewBean.getGeneratorCompanyId());
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
   public int delete(TsdfReceivingViewBean tsdfReceivingViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("generatorCompanyId", "SearchCriterion.EQUALS",
     "" + tsdfReceivingViewBean.getGeneratorCompanyId());
    return delete(criteria, conn);
   }
	 */

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
//insert
   public int insert(TsdfReceivingViewBean tsdfReceivingViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(tsdfReceivingViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(TsdfReceivingViewBean tsdfReceivingViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_GENERATOR_COMPANY_ID + "," +
     ATTRIBUTE_SHIPMENT_ID + "," +
     ATTRIBUTE_ACTUAL_SHIP_DATE + "," +
     ATTRIBUTE_GENERATOR_FACILITY_ID + "," +
     ATTRIBUTE_ORDER_NO + "," +
     ATTRIBUTE_CONTAINER_ID + "," +
     ATTRIBUTE_TSDF_COMPANY_ID + "," +
     ATTRIBUTE_TSDF_FACILITY_ID + "," +
     ATTRIBUTE_TSDF_FACILITY_NAME + "," +
     ATTRIBUTE_WASTE_ITEM_ID + "," +
     ATTRIBUTE_VENDOR_PROFILE_ID + "," +
     ATTRIBUTE_PROFILE_DESCRIPTION + "," +
     ATTRIBUTE_PACKAGING + "," +
     ATTRIBUTE_STATE_WASTE_CODES + "," +
     ATTRIBUTE_EPA_WASTE_CODES + ")" +
     " values (" +
     SqlHandler.delimitString(tsdfReceivingViewBean.getGeneratorCompanyId()) + "," +
     tsdfReceivingViewBean.getShipmentId() + "," +
     DateHandler.getOracleToDateFunction(tsdfReceivingViewBean.getActualShipDate()) + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getGeneratorFacilityId()) + "," +
     tsdfReceivingViewBean.getOrderNo() + "," +
     tsdfReceivingViewBean.getContainerId() + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getTsdfCompanyId()) + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getTsdfFacilityId()) + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getTsdfFacilityName()) + "," +
     tsdfReceivingViewBean.getWasteItemId() + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getVendorProfileId()) + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getProfileDescription()) + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getPackaging()) + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getStateWasteCodes()) + "," +
     SqlHandler.delimitString(tsdfReceivingViewBean.getEpaWasteCodes()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(TsdfReceivingViewBean tsdfReceivingViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(tsdfReceivingViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(TsdfReceivingViewBean tsdfReceivingViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_GENERATOR_COMPANY_ID + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getGeneratorCompanyId()) + "," +
     ATTRIBUTE_SHIPMENT_ID + "=" +
      StringHandler.nullIfZero(tsdfReceivingViewBean.getShipmentId()) + "," +
     ATTRIBUTE_ACTUAL_SHIP_DATE + "=" +
      DateHandler.getOracleToDateFunction(tsdfReceivingViewBean.getActualShipDate()) + "," +
     ATTRIBUTE_GENERATOR_FACILITY_ID + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getGeneratorFacilityId()) + "," +
     ATTRIBUTE_ORDER_NO + "=" +
      StringHandler.nullIfZero(tsdfReceivingViewBean.getOrderNo()) + "," +
     ATTRIBUTE_CONTAINER_ID + "=" +
      StringHandler.nullIfZero(tsdfReceivingViewBean.getContainerId()) + "," +
     ATTRIBUTE_TSDF_COMPANY_ID + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getTsdfCompanyId()) + "," +
     ATTRIBUTE_TSDF_FACILITY_ID + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getTsdfFacilityId()) + "," +
     ATTRIBUTE_TSDF_FACILITY_NAME + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getTsdfFacilityName()) + "," +
     ATTRIBUTE_WASTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(tsdfReceivingViewBean.getWasteItemId()) + "," +
     ATTRIBUTE_VENDOR_PROFILE_ID + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getVendorProfileId()) + "," +
     ATTRIBUTE_PROFILE_DESCRIPTION + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getProfileDescription()) + "," +
     ATTRIBUTE_PACKAGING + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getPackaging()) + "," +
     ATTRIBUTE_STATE_WASTE_CODES + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getStateWasteCodes()) + "," +
     ATTRIBUTE_EPA_WASTE_CODES + "=" +
      SqlHandler.delimitString(tsdfReceivingViewBean.getEpaWasteCodes()) + " " +
     "where " + ATTRIBUTE_GENERATOR_COMPANY_ID + "=" +
      tsdfReceivingViewBean.getGeneratorCompanyId();
    return new SqlManager().update(conn, query);
   }
	 */

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

		Collection tsdfReceivingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			TsdfReceivingViewBean tsdfReceivingViewBean = new TsdfReceivingViewBean();
			load(dataSetRow, tsdfReceivingViewBean);
			tsdfReceivingViewBeanColl.add(tsdfReceivingViewBean);
		}

		return tsdfReceivingViewBeanColl;
	}

	public Collection selectOV(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectOV(criteria, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectOV(SearchCriteria criteria, Connection conn) throws BaseException {

		Collection tsdfReceivingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		log.debug(query);
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			TsdfReceivingViewBean tsdfReceivingViewBean = new TsdfReceivingViewBean();
			load(dataSetRow, tsdfReceivingViewBean);
			tsdfReceivingViewBeanColl.add(tsdfReceivingViewBean);
			//get tsdf profile for waste item
			WasteItemConvertViewBeanFactory factory = new WasteItemConvertViewBeanFactory(this.getDbManager());
			SearchCriteria criteria2 = new SearchCriteria();
			criteria2.addCriterion("fromWasteItemId", SearchCriterion.EQUALS, tsdfReceivingViewBean.getWasteItemId().toString());
			criteria2.addCriterion("fromFacilityId", SearchCriterion.EQUALS, tsdfReceivingViewBean.getGeneratorFacilityId().toString());
			tsdfReceivingViewBean.setWasteItemConvertViewBean(factory.select(criteria2));
			//get tsdf storage location for facility
			WasteLocationBeanFactory wlFactory = new WasteLocationBeanFactory(this.getDbManager());
			SearchCriteria criteria3 = new SearchCriteria();
			criteria3.addCriterion("facilityId", SearchCriterion.EQUALS, tsdfReceivingViewBean.getTsdfFacilityId().toString());
			criteria3.addCriterion("locationType", SearchCriterion.EQUALS, "storage");
			tsdfReceivingViewBean.setWasteLocationBean(wlFactory.select(criteria3));
		}

		return tsdfReceivingViewBeanColl;
	} //end of method

	public String createTsdfWasteRequest(TsdfWasteReceivingInputBean inputBean, int receiverId) throws BaseException {
		String error = "";
		Connection connection = this.getDbManager().getConnection();
		/*
    try {
      connection.setAutoCommit(false);
    } catch (SQLException ex) {
    }*/

		//input to procedure
		Collection cin = new Vector();
		//requestor
		cin.add(new Integer(receiverId));
		//facility_id
		cin.add(inputBean.getTsdfFacilityId());
		//account sys
		cin.add("");
		//requested releaser
		cin.add("");

		//output from procedure
		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));

		//optional parameters
		Collection optionalIn = new Vector(1);
		//set status = TSDF
		optionalIn.add("TSDF");

		Collection result = null;
		SqlManager sqlManager = new SqlManager();
		try {
			result = sqlManager.doProcedure(connection, "P_WASTE_MGMT_REQUEST", cin, cout, optionalIn);
		} catch (Exception e) {
			error = "Error while running procedure P_WASTE_MGMT_REQUEST";
		}
		if (error.length() < 1) {
			Iterator i11 = result.iterator();
			String wasteRequestId = "";
			String messageFromProcedure = "";
			int count = 0;
			while (i11.hasNext()) {
				if (count == 0) {
					wasteRequestId = (String) i11.next();
				} else {
					messageFromProcedure = (String) i11.next();
				}
				count++;
			}
			//new tsdf waste mgmt request
			if (log.isDebugEnabled()) {
				log.debug("waste mgmt request: "+wasteRequestId);
				log.debug("Message from P_WASTE_MGMT_REQUEST: "+messageFromProcedure);
			}
			//if no error from p_waste_mgmt_request then continue
			if (StringHandler.isBlankString(messageFromProcedure)) {
				if (log.isDebugEnabled()) {
					log.debug(createTsdfWasteContainer(inputBean, wasteRequestId));
				}
			}else {
				error = messageFromProcedure;
			}
		}

		/*
    try {
      connection.setAutoCommit(true);
    } catch (SQLException ex) {
    }
		 */
		this.getDbManager().returnConnection(connection);
		return error;
	} //end of method

	public String createTsdfWasteContainer(TsdfWasteReceivingInputBean inputBean, String wasteRequestId) throws BaseException {
		String error = "";
		Connection connection = this.getDbManager().getConnection();

		//input to procedure
		Collection cin = new Vector();
		//waste request id
		cin.add(new BigDecimal(wasteRequestId));
		//old container Id
		cin.add(new BigDecimal(inputBean.getContainerId()));
		//tsdf waste item Id
		cin.add(new BigDecimal(inputBean.getTsdfWasteItemId()));
		//generation point - same as tsdf location
		cin.add(inputBean.getTsdfLocation());
		//tsdf facility Id
		cin.add(inputBean.getTsdfFacilityId());
		//tsdf location
		cin.add(inputBean.getTsdfLocation());
		//date of receipt
		cin.add(DateHandler.getTimestampFromString("MM/dd/yyyy",inputBean.getDateOfReceipt()));

		//output from procedure
		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection result = null;
		SqlManager sqlManager = new SqlManager();
		try {
			result = sqlManager.doProcedure(connection, "P_TSDF_CONTAINER_RECEIVE", cin, cout);
		} catch (Exception e) {
			error = "Error while running procedure P_TSDF_CONTAINER_RECEIVE";
		}
		if (error.length() < 1) {
			Iterator i11 = result.iterator();
			String lineItem = "";
			String containerId = "";
			String messageFromProcedure = "";
			int count = 0;
			while (i11.hasNext()) {
				if (count == 0) {
					lineItem = (String) i11.next();
				}else if (count == 1) {
					containerId = (String) i11.next();
				} else {
					messageFromProcedure = (String) i11.next();
				}
				count++;
			}
			//new tsdf waste mgmt request
			if (log.isDebugEnabled()) {
				log.debug("waste mgmt request line: "+lineItem);
				log.debug("waste mgmt request container: "+containerId);
				log.debug("Message from P_TSDF_CONTAINER_RECEIVE: "+messageFromProcedure);
			}
		}

		this.getDbManager().returnConnection(connection);
		return error;
	} //end of method


} //end of class