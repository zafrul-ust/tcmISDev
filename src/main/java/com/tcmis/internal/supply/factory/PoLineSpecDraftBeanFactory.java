package com.tcmis.internal.supply.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.SQLException;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.supply.beans.PoLineSpecDraftBean;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;


/******************************************************************************
 * CLASSNAME: PoLineSpecDraftBeanFactory <br>
 * @version: 1.0, Aug 11, 2008 <br>
 *****************************************************************************/


public class PoLineSpecDraftBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SPEC_ID = "SPEC_ID";
	public String ATTRIBUTE_SPEC_LIBRARY = "SPEC_LIBRARY";
	public String ATTRIBUTE_DETAIL = "DETAIL";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_AMENDMENT = "AMENDMENT";
	public String ATTRIBUTE_COC = "COC";
	public String ATTRIBUTE_COA = "COA";
	public String ATTRIBUTE_COC_REQ = "COC_REQ";
	public String ATTRIBUTE_COA_REQ = "COA_REQ";
	public String ATTRIBUTE_COLOR = "COLOR";
	public String ATTRIBUTE_CURRENT_COC_REQUIREMENT = "CURRENT_COC_REQUIREMENT";
	public String ATTRIBUTE_CURRENT_COA_REQUIREMENT = "CURRENT_COA_REQUIREMENT";

	//table name
	public String TABLE = "PO_LINE_SPEC_DRAFT";


	//constructor
	public PoLineSpecDraftBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("specId")) {
			return ATTRIBUTE_SPEC_ID;
		}
		else if(attributeName.equals("specLibrary")) {
			return ATTRIBUTE_SPEC_LIBRARY;
		}
		else if(attributeName.equals("detail")) {
			return ATTRIBUTE_DETAIL;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("amendment")) {
			return ATTRIBUTE_AMENDMENT;
		}
		else if(attributeName.equals("coc")) {
			return ATTRIBUTE_COC;
		}
		else if(attributeName.equals("coa")) {
			return ATTRIBUTE_COA;
		}
		else if(attributeName.equals("cocReq")) {
			return ATTRIBUTE_COC_REQ;
		}
		else if(attributeName.equals("coaReq")) {
			return ATTRIBUTE_COA_REQ;
		}
		else if(attributeName.equals("color")) {
			return ATTRIBUTE_COLOR;
		}
		else if(attributeName.equals("currentCocRequirement")) {
			return ATTRIBUTE_CURRENT_COC_REQUIREMENT;
		}
		else if(attributeName.equals("currentCoaRequirement")) {
			return ATTRIBUTE_CURRENT_COA_REQUIREMENT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoLineSpecDraftBean.class);
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

	public int insert(String sql)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(sql, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(String sql, Connection conn)
		throws BaseException {

		return new SqlManager().update(conn, sql);
	}	

  public void saveInitialSpec(BuyOrdersInputBean bean, BigDecimal poNumber) throws BaseException {
    Connection connection = null;
    try {
      connection = this.getDbManager().getConnection();
      connection.setAutoCommit(false);
      saveInitialSpec(bean, poNumber, connection);
      connection.setAutoCommit(true);
    }
    catch(SQLException e) {
      log.error("Error:" + e.getMessage(),e);
      throw new BaseException(e);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
  }

  public void saveInitialSpec(BuyOrdersInputBean bean, BigDecimal poNumber, Connection conn) throws BaseException, SQLException {
    Collection inArgs = new Vector(7);
    inArgs.add(bean.getShipToLocationId());
	 inArgs.add(bean.getShipToCompanyId());
	 inArgs.add(bean.getItemId());
	 inArgs.add(poNumber);
	 inArgs.add(new BigDecimal(1000));
	 inArgs.add(new BigDecimal(0));
	 inArgs.add(bean.getInventoryGroup());

	 Collection outArgs = new Vector(1);
    outArgs.add(new Integer(java.sql.Types.VARCHAR));
    GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
    Collection resultCollection = procFactory.doProcedure(conn, "pkg_po.Po_spec", inArgs, outArgs);
    Iterator iterator = resultCollection.iterator();
    String query = null;
    while(iterator.hasNext()) {
      query = (String)iterator.next();
    }
	 DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PoLineSpecDraftBean poLineSpecDraftBean = new PoLineSpecDraftBean();
      load(dataSetRow, poLineSpecDraftBean);
		if ( ( "1".equalsIgnoreCase(NumberHandler.emptyIfNull(poLineSpecDraftBean.getColor()) ) ||
			    "2".equalsIgnoreCase(NumberHandler.emptyIfNull(poLineSpecDraftBean.getColor()) ) ) &&
			 ("Y".equalsIgnoreCase(poLineSpecDraftBean.getCurrentCocRequirement()) || "Y".equalsIgnoreCase(poLineSpecDraftBean.getCurrentCoaRequirement())) ) {
			 insert("insert into po_line_spec_draft (SPEC_ID,SPEC_LIBRARY,RADIAN_PO,PO_LINE,COC,COA,AMENDMENT,DETAIL,COA_REQ,COC_REQ,COLOR) values "+
			 		  "('"+poLineSpecDraftBean.getSpecId()+"','"+poLineSpecDraftBean.getSpecLibrary()+"',"+
				         poNumber.toString()+ ",1000,'"+poLineSpecDraftBean.getCurrentCocRequirement()+"','"+
				         poLineSpecDraftBean.getCurrentCoaRequirement()+"',0,'"+StringHandler.emptyIfNull(poLineSpecDraftBean.getDetail())+"','"+poLineSpecDraftBean.getCurrentCoaRequirement()+"','"+poLineSpecDraftBean.getCurrentCocRequirement()+"',"+poLineSpecDraftBean.getColor().toString()+")");
		 }
	 }
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

		Collection poLineSpecDraftBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoLineSpecDraftBean poLineSpecDraftBean = new PoLineSpecDraftBean();
			load(dataSetRow, poLineSpecDraftBean);
			poLineSpecDraftBeanColl.add(poLineSpecDraftBean);
		}

		return poLineSpecDraftBeanColl;
	}

}  //end of class