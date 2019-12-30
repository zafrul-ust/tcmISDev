package com.tcmis.supplier.shipping.factory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.supplier.shipping.beans.SupplierLocPicklistOvBean;


/******************************************************************************
 * CLASSNAME: SupplierLocPicklistOvBeanFactory <br>
 * @version: 1.0, Oct 25, 2007 <br>
 *****************************************************************************/


public class SupplierLocPicklistOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SUPPLIER_LOC_PICKLISTS = "SUPPLIER_LOC_PICKLISTS";

	//table name
	public String TABLE = "SUPPLIER_LOC_PICKLIST_OV";


	//constructor
	public SupplierLocPicklistOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("supplierLocPicklists")) {
			return ATTRIBUTE_SUPPLIER_LOC_PICKLISTS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria, null,false) ;
	}
	
	public Collection selectDLAObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria, null,true) ;
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria,boolean isDLASupplierList) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("CUSTOMER.PICKLIST_OBJ",
					Class.forName(
					"com.tcmis.supplier.shipping.beans.PicklistObjBean"));
			map.put("CUSTOMER.LOCATION_PICKLIST_OBJ",
					Class.forName(
					"com.tcmis.supplier.shipping.beans.LocationPicklistObjBean"));
			map.put("CUSTOMER.SUPPLIER_LOC_PICKLIST_OBJ",
					Class.forName(
					"com.tcmis.supplier.shipping.beans.SupplierLocPicklistOvBean"));
			c = selectObject(criteria, sortCriteria,connection,isDLASupplierList);
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

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria,Connection conn,boolean isDLASupplierList) throws
	BaseException {
		Collection supplierLocPicklistOvBeanColl = new Vector();

		String sortBy = " ";
		StringBuilder query = new StringBuilder("select value(p) from ");
		query.append(TABLE);
		query.append(" p ");
		if(isDLASupplierList)
			query.append(", dla_gas_supplier dgs ");
		query.append(getWhereClause(criteria));
		if(isDLASupplierList)
			query.append(" and DGS.SUPPLIER = P.SUPPLIER");
		query.append(getOrderByClause(sortCriteria));
		if(log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()) {
				SupplierLocPicklistOvBean b = (SupplierLocPicklistOvBean) rs.getObject(1);
				supplierLocPicklistOvBeanColl.add(b);
			}
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		return supplierLocPicklistOvBeanColl;
	}
}