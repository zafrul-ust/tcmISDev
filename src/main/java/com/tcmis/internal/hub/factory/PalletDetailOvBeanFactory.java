package com.tcmis.internal.hub.factory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.PalletDetailOvBean;
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
 * CLASSNAME: PalletDetailOvBeanFactory <br>
 * @version: 1.0, Jan 12, 2008 <br>
 *****************************************************************************/


public class PalletDetailOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_BOX_LABEL_ID = "BOX_LABEL_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UOS_QUANTITY = "UOS_QUANTITY";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_BOX_ID = "BOX_ID";
	public String ATTRIBUTE_PALLET_ID = "PALLET_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";

	//table name
	public String TABLE = "PALLET_DETAIL_OV";


	//constructor
	public PalletDetailOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("sourceHub")) {
			return ATTRIBUTE_SOURCE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("boxLabelId")) {
			return ATTRIBUTE_BOX_LABEL_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("uosQuantity")) {
			return ATTRIBUTE_UOS_QUANTITY;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("boxId")) {
			return ATTRIBUTE_BOX_ID;
		}
		else if(attributeName.equals("palletId")) {
			return ATTRIBUTE_PALLET_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PalletDetailOvBean.class);
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

		Collection palletDetailOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PalletDetailOvBean palletDetailOvBean = new PalletDetailOvBean();
			load(dataSetRow, palletDetailOvBean);
			palletDetailOvBeanColl.add(palletDetailOvBean);
		}

		return palletDetailOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
		 connection = this.getDbManager().getConnection();
		 java.util.Map map = connection.getTypeMap();
		 map.put("TCM_OPS.PALLET_DETAIL_OBJ",Class.forName("com.tcmis.internal.hub.beans.PalletDetailOvBean"));
		 map.put("TCM_OPS.PALLET_OBJ",Class.forName("com.tcmis.internal.hub.beans.PalletObjBean"));
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
		Collection palletDetailOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " + getWhereClause(criteria);
		 log.debug(query);
		 try {
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(query);
			 while (rs.next()) {
				 PalletDetailOvBean bean = (PalletDetailOvBean) rs.getObject(1);
				 palletDetailOvBeanColl.add(bean);
			 }
		 }catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
			return palletDetailOvBeanColl;
		}

	//select
	public String validateUserInput(String sql)
		throws BaseException {

		Connection connection = null;
		String c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = validateUserInput(sql, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public String validateUserInput(String sql, Connection conn)
		throws BaseException {

		String result = "";
		DataSet dataSet = new SqlManager().select(conn, sql);

		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			result = dataSetRow.getString("temp_val");
		}
		return result;
	}

}