package com.tcmis.internal.hub.factory;

import com.tcmis.internal.hub.beans.LtlTlPalletOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: ParcelOvBeanFactory <br>
 * @version: 1.0, Jan 12, 2008 <br>
 *****************************************************************************/


public class LtlTlPalletOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
	public String ATTRIBUTE_TRANSPORTATION_MODE = "TRANSPORTATION_MODE";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
  public String ATTRIBUTE_TRAILER_NUMBER = "TRAILER_NUMBER";
	public String ATTRIBUTE_STOP_NUMBER = "STOP_NUMBER";
	public String ATTRIBUTE_PALLET_ID = "PALLET_ID";
	public String ATTRIBUTE_CONSOLIDATION_NUMBER = "CONSOLIDATION_NUMBER";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED = "ORIGIN_INSPECTION_REQUIRED";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_TRACK_SERIAL_NUMBER = "TRACK_SERIAL_NUMBER";
	public String ATTRIBUTE_MISSING_SERIAL_NUMBER = "MISSING_SERIAL_NUMBER";

	//table name
	public String TABLE = "LTL_TL_PALLET_OV";


	//constructor
	public LtlTlPalletOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("sourceHub")) {
			return ATTRIBUTE_SOURCE_HUB;
		}
		else if(attributeName.equals("transportationMode")) {
			return ATTRIBUTE_TRANSPORTATION_MODE;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("trailerNumber")) {
			return ATTRIBUTE_TRAILER_NUMBER;
		}
		else if(attributeName.equals("stopNumber")) {
			return ATTRIBUTE_STOP_NUMBER;
		}
		else if(attributeName.equals("palletId")) {
			return ATTRIBUTE_PALLET_ID;
		}
		else if(attributeName.equals("consolidationNumber")) {
			return ATTRIBUTE_CONSOLIDATION_NUMBER;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}else if(attributeName.equals("trackSerialNumber")) {
			return ATTRIBUTE_TRACK_SERIAL_NUMBER;
		}else if(attributeName.equals("missingSerialNumber")) {
			return ATTRIBUTE_MISSING_SERIAL_NUMBER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, LtlTlPalletOvBean.class);
	}

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection ltlTlPalletOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			LtlTlPalletOvBean ltlTlPalletOvBean = new LtlTlPalletOvBean();
			load(dataSetRow, ltlTlPalletOvBean);
			ltlTlPalletOvBeanColl.add(ltlTlPalletOvBean);
		}

		return ltlTlPalletOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
		 connection = this.getDbManager().getConnection();
		 java.util.Map map = connection.getTypeMap();
		 map.put("TCM_OPS.LTL_TL_PALLET_OBJ",Class.forName("com.tcmis.internal.hub.beans.LtlTlPalletOvBean"));
		 map.put("TCM_OPS.CARRIER_OBJ",Class.forName("com.tcmis.internal.hub.beans.CarrierObjBean"));
		 c = selectObject(criteria, connection);
		}
		catch (Exception e) {
		 log.error("selectObject error:" + e.getMessage());
		 DbSelectException ex = new DbSelectException("error.db.select");
		 ex.setRootCause(e);
		 throw ex;
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return c;
	 }

	 public Collection selectObject(SearchCriteria criteria, Connection conn) throws BaseException {
		Collection ltlTlPalletOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " + getWhereClause(criteria);
		 //query += " and transportation_mode in ('LTL','TL')";
		 log.debug(query);
		 try {
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(query);
			 while (rs.next()) {
				 LtlTlPalletOvBean bean = (LtlTlPalletOvBean) rs.getObject(1);
				 ltlTlPalletOvBeanColl.add(bean);
			 }
		 }catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
			return ltlTlPalletOvBeanColl;
		}

	//select
	public Collection selectDistinct(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectDistinct(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectDistinct(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection ltlTlPalletOvBeanColl = new Vector();

		String query = "select distinct facility_id,carrier_code,carrier_name,tracking_number,track_serial_number,missing_serial_number from " + TABLE + " " +getWhereClause(criteria);
		query += " and transportation_mode in ('LTL','TL')";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			LtlTlPalletOvBean ltlTlPalletOvBean = new LtlTlPalletOvBean();
			load(dataSetRow, ltlTlPalletOvBean);
			ltlTlPalletOvBeanColl.add(ltlTlPalletOvBean);
		}

		return ltlTlPalletOvBeanColl;
	}

}