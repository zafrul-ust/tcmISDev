package com.tcmis.client.report.factory;


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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.report.beans.ApplicationObjBean;
import com.tcmis.client.report.beans.DivFacGrpFacReAppOvBean;
import com.tcmis.client.report.beans.FacGrpFacReAppObjBean;
import com.tcmis.client.report.beans.FacReAppObjBean;
import com.tcmis.client.report.beans.ReAppObjBean;


import java.sql.SQLException;import com.tcmis.common.exceptions.DbSelectException;import java.sql.Statement;import java.sql.ResultSet;


/******************************************************************************
 * CLASSNAME: DivFacGrpFacReAppOvBeanFactory <br>
 * @version: 1.0, Apr 11, 2011 <br>
 *****************************************************************************/


public class DivFacGrpFacReAppOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_DIVISION_ID = "DIVISION_ID";
	public String ATTRIBUTE_DIVISION_DESCRIPTION = "DIVISION_DESCRIPTION";
	public String ATTRIBUTE_FACILITY_GROUP_LIST = "FACILITY_GROUP_LIST";

	//table name
	public String TABLE = "DIV_FAC_GRP_FAC_RE_APP_OV";


	//constructor
	public DivFacGrpFacReAppOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("divisionId")) {
			return ATTRIBUTE_DIVISION_ID;
		}
		else if(attributeName.equals("divisionDescription")) {
			return ATTRIBUTE_DIVISION_DESCRIPTION;
		}
		else if(attributeName.equals("facilityGroupList")) {
			return ATTRIBUTE_FACILITY_GROUP_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DivFacGrpFacReAppOvBean.class);
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
	 return selectObject(criteria,null);
	 }

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
		 connection = this.getDbManager().getConnection();
		 java.util.Map map = connection.getTypeMap();
		 map.put("CUSTOMER.DIV_FAC_GRP_FAC_RE_APP_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.DivFacGrpFacReAppOvBean"));
		 map.put("CUSTOMER.RE_APP_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.ReAppObjBean"));
		 map.put("CUSTOMER.FAC_GRP_FAC_RE_APP_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.FacGrpFacReAppObjBean"));
		 map.put("CUSTOMER.FAC_RE_APP_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.FacReAppObjBean"));
		 map.put("CUSTOMER.APPLICATION_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.ApplicationObjBean"));

		 c = selectObject(criteria, sortCriteria,connection);
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
	
	static Comparator<ApplicationObjBean> appComparator = new Comparator<ApplicationObjBean>(){
		public int compare( ApplicationObjBean a, ApplicationObjBean b ){
			if( a.getApplicationDesc() == null ) return -1;
			return a.getApplicationDesc().compareToIgnoreCase(b.getApplicationDesc());
		}
	};
	static Comparator<ReAppObjBean> replistComparator = new Comparator<ReAppObjBean>(){
		public int compare( ReAppObjBean a, ReAppObjBean b ){
			if( a.getReportingEntityDescription() == null ) return -1;
			return a.getReportingEntityDescription().compareToIgnoreCase(b.getReportingEntityDescription());
		}
	};
	static Comparator<FacReAppObjBean> facComparator = new Comparator<FacReAppObjBean>(){
		public int compare( FacReAppObjBean a, FacReAppObjBean b ){
			if( a.getFacilityName() == null ) return -1;
			return a.getFacilityName().compareToIgnoreCase(b.getFacilityName());
		}
	};
	static Comparator<FacGrpFacReAppObjBean> facgroupComparator = new Comparator<FacGrpFacReAppObjBean>(){
		public int compare( FacGrpFacReAppObjBean a, FacGrpFacReAppObjBean b ){
			if( a.getFacilityGroupDescription() == null ) return -1;
			return a.getFacilityGroupDescription().compareToIgnoreCase(b.getFacilityGroupDescription());
		}
	};
	static Comparator<DivFacGrpFacReAppOvBean> divComparator = new Comparator<DivFacGrpFacReAppOvBean>(){
		public int compare( DivFacGrpFacReAppOvBean a, DivFacGrpFacReAppOvBean b ){
			if( a.getDivisionDescription() == null ) return -1;
			return a.getDivisionDescription().compareToIgnoreCase(b.getDivisionDescription());
		}
	};
//divisionDescription
	 public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
		BaseException {
		Collection<DivFacGrpFacReAppOvBean> divFacGrpFacReAppOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + getOrderByClause(sortCriteria);
		 log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					DivFacGrpFacReAppOvBean b = (DivFacGrpFacReAppOvBean) rs.getObject(1);
					divFacGrpFacReAppOvBeanColl.add(b);
				}
				rs.close();
				st.close();
				Collections.sort((List)divFacGrpFacReAppOvBeanColl,divComparator);
				for(DivFacGrpFacReAppOvBean l1:divFacGrpFacReAppOvBeanColl){
					//	    	System.out.println("line:"+(i++));
					Collections.sort((List)l1.getFacilityGroupList(),facgroupComparator);
					for(FacGrpFacReAppObjBean l2: (Collection<FacGrpFacReAppObjBean>)l1.getFacilityGroupList() ) {
						Collections.sort((List)l2.getFacilityList(),facComparator);
						for(FacReAppObjBean l3: (Collection<FacReAppObjBean>)l2.getFacilityList() ) {
							Collections.sort((List)l3.getReportingEntityList(),replistComparator);
							for(ReAppObjBean l4: (Collection<ReAppObjBean>)l3.getReportingEntityList() ) {
								Collections.sort((List)l4.getApplicationList(),appComparator);
							}
						}
					}
				}
			}
			catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
			return divFacGrpFacReAppOvBeanColl;
		}
//	  static Comparator<ApplicationObjBean> appComparator = new Comparator<ApplicationObjBean>(){
//			public int compare( ApplicationObjBean a, ApplicationObjBean b ){
//				if( a.getApplicationDesc() == null ) return -1;
//				return a.getApplicationDesc().compareToIgnoreCase(b.getApplicationDesc());
//			}
//	  };
//	  divisionDescription
//	  public Collection selectObject(SearchCriteria criteria, Connection conn) throws BaseException, Exception {
//
//	    Vector<OtrackDropdownOvBean> otrackDropdownOvBeanColl = new Vector();
//
//	    String query = "select value(p) from " + TABLE + " p " + getWhereClause(criteria);
//
//	    if (log.isDebugEnabled()) {
//	      log.debug("QUERY:" + query);
//	    }
//	    Statement st = conn.createStatement();
//	    ResultSet rs = st.executeQuery(query);
//	    while (rs.next()) {
//	      OtrackDropdownOvBean b = (OtrackDropdownOvBean) rs.getObject(1);
//	      otrackDropdownOvBeanColl.add(b);
//	    }
//	    // sorting, get some anomous
////	    int i = 0 ;
//	    
//	    for(OtrackDropdownOvBean l1:otrackDropdownOvBeanColl){
////	    	System.out.println("line:"+(i++));
//	    	Collections.sort((List)l1.getFacilityList(),facComparator);
//	    	for(FacilityObjBean l2: l1.getFacilityList() ) {
//	        	Collections.sort((List)l2.getApplicationList(),appComparator);
//	    	}
//	    }
	 

}