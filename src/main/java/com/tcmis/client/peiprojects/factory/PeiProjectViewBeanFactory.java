package com.tcmis.client.peiprojects.factory;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.peiprojects.beans.PeiProjectInputBean;
import com.tcmis.client.peiprojects.beans.PeiProjectViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
/******************************************************************************
 * CLASSNAME: PeiProjectViewBeanFactory <br>
 * @version: 1.0, Dec 14, 2005 <br>
 *****************************************************************************/


public class PeiProjectViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PROJECT_ID = "PROJECT_ID";
	public String ATTRIBUTE_PROJECT_NAME = "PROJECT_NAME";
	public String ATTRIBUTE_PROJECT_DESC = "PROJECT_DESC";
	public String ATTRIBUTE_PROJECT_CATEGORY = "PROJECT_CATEGORY";
	public String ATTRIBUTE_PROJECT_MANAGER_ID = "PROJECT_MANAGER_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPROVED_DATE = "APPROVED_DATE";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_PROJECTED_FINISTED_DATE = "PROJECTED_FINISTED_DATE";
	public String ATTRIBUTE_POJECTED_COST = "POJECTED_COST";
	public String ATTRIBUTE_ACTUAL_COST = "ACTUAL_COST";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_BEST_PRACTICE = "BEST_PRACTICE";
	public String ATTRIBUTE_PROJECT_STATUS = "PROJECT_STATUS";
	public String ATTRIBUTE_ACTUAL_FINISH_DATE = "ACTUAL_FINISH_DATE";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_PROJECT_MANAGER = "PROJECT_MANAGER";
	public String ATTRIBUTE_PROJECT_MANAGER_EMAIL = "PROJECT_MANAGER_EMAIL";
	public String ATTRIBUTE_PROJECT_MANAGER_PHONE = "PROJECT_MANAGER_PHONE";
	public String ATTRIBUTE_CUSTOMER_CONTACT_ID = "CUSTOMER_CONTACT_ID";
	public String ATTRIBUTE_CUSTOMER_CONTACT_MANAGER = "CUSTOMER_CONTACT_MANAGER";
	public String ATTRIBUTE_CUSTOMER_CONTACT_EMAIL = "CUSTOMER_CONTACT_EMAIL";
	public String ATTRIBUTE_CUSTOMER_CONTACT_PHONE = "CUSTOMER_CONTACT_PHONE";
	public String ATTRIBUTE_SEARCH = "SEARCH";
	public String ATTRIBUTE_TOTAL_PROJECTED_PERIOD_SAVINGS = "TOTAL_PROJECTED_PERIOD_SAVINGS";
	public String ATTRIBUTE_TOTAL_ACTUAL_PERIOD_SAVINGS = "TOTAL_ACTUAL_PERIOD_SAVINGS";
	public String ATTRIBUTE_KEYWORD_ID = "KEYWORD_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_GAIN_SHARE_DURATION = "GAIN_SHARE_DURATION";
	public String ATTRIBUTE_PROPOSED_DATE_TO_CLIENT = "PROPOSED_DATE_TO_CLIENT";

	//table name
	public String TABLE = "PEI_PROJECT_VIEW";
	public String UPDATE_TABLE = "PEI_PROJECT";
	public String PEI_PROJECT_ID_SEQ_TABLE = "PEI_PROJECT_ID_SEQ";
	public String PEI_PROJECT_KEYWORD_TABLE = "PEI_PROJECT_KEYWORD";

	//constructor
	public PeiProjectViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("projectId")) {
			return ATTRIBUTE_PROJECT_ID;
		}
		else if(attributeName.equals("projectName")) {
			return ATTRIBUTE_PROJECT_NAME;
		}
		else if(attributeName.equals("projectDesc")) {
			return ATTRIBUTE_PROJECT_DESC;
		}
		else if(attributeName.equals("projectCategory")) {
			return ATTRIBUTE_PROJECT_CATEGORY;
		}
		else if(attributeName.equals("projectManagerId")) {
			return ATTRIBUTE_PROJECT_MANAGER_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("approvedDate")) {
			return ATTRIBUTE_APPROVED_DATE;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("projectedFinistedDate")) {
			return ATTRIBUTE_PROJECTED_FINISTED_DATE;
		}
		else if(attributeName.equals("pojectedCost")) {
			return ATTRIBUTE_POJECTED_COST;
		}
		else if(attributeName.equals("actualCost")) {
			return ATTRIBUTE_ACTUAL_COST;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("bestPractice")) {
			return ATTRIBUTE_BEST_PRACTICE;
		}
		else if(attributeName.equals("projectStatus")) {
			return ATTRIBUTE_PROJECT_STATUS;
		}
		else if(attributeName.equals("actualFinishDate")) {
			return ATTRIBUTE_ACTUAL_FINISH_DATE;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("projectManager")) {
			return ATTRIBUTE_PROJECT_MANAGER;
		}
		else if(attributeName.equals("projectManagerEmail")) {
			return ATTRIBUTE_PROJECT_MANAGER_EMAIL;
		}
		else if(attributeName.equals("projectManagerPhone")) {
			return ATTRIBUTE_PROJECT_MANAGER_PHONE;
		}
		else if(attributeName.equals("customerContactId")) {
			return ATTRIBUTE_CUSTOMER_CONTACT_ID;
		}
		else if(attributeName.equals("customerContactManager")) {
			return ATTRIBUTE_CUSTOMER_CONTACT_MANAGER;
		}
		else if(attributeName.equals("customerContactEmail")) {
			return ATTRIBUTE_CUSTOMER_CONTACT_EMAIL;
		}
		else if(attributeName.equals("customerContactPhone")) {
			return ATTRIBUTE_CUSTOMER_CONTACT_PHONE;
		}
		else if(attributeName.equals("search")) {
			return ATTRIBUTE_SEARCH;
		}
		else if(attributeName.equals("totalProjectedPeriodSavings")) {
			return ATTRIBUTE_TOTAL_PROJECTED_PERIOD_SAVINGS;
		}
		else if(attributeName.equals("totalActualPeriodSavings")) {
			return ATTRIBUTE_TOTAL_ACTUAL_PERIOD_SAVINGS;
		}
		else if(attributeName.equals("companyId")) {
		 return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("gainShareDuration")) {
		 return ATTRIBUTE_GAIN_SHARE_DURATION;
		}
		else if(attributeName.equals("proposedDateToClient")) {
		 return ATTRIBUTE_PROPOSED_DATE_TO_CLIENT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PeiProjectViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PeiProjectViewBean peiProjectViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectViewBean.getProjectId());

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


	public int delete(PeiProjectViewBean peiProjectViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectViewBean.getProjectId());

		return delete(criteria, conn);
	}
*/

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


//you need to verify the primary key(s) before uncommenting this

	//insert
	public int insert(PeiProjectViewBean peiProjectViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(peiProjectViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PeiProjectViewBean peiProjectViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + UPDATE_TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PROJECT_ID + "," +
			ATTRIBUTE_PROJECT_NAME + "," +
			ATTRIBUTE_PROJECT_DESC + "," +
			ATTRIBUTE_PROJECT_CATEGORY + "," +
			ATTRIBUTE_PROJECT_MANAGER_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPROVED_DATE + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_PROJECTED_FINISTED_DATE + "," +
			ATTRIBUTE_POJECTED_COST + "," +
			ATTRIBUTE_ACTUAL_COST + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_BEST_PRACTICE + "," +
			ATTRIBUTE_PROJECT_STATUS + "," +
			ATTRIBUTE_ACTUAL_FINISH_DATE + "," +
			ATTRIBUTE_PRIORITY + "," +
			ATTRIBUTE_GAIN_SHARE_DURATION + "," +
			ATTRIBUTE_PROPOSED_DATE_TO_CLIENT + "," +
			/*ATTRIBUTE_PROJECT_MANAGER + "," +
			ATTRIBUTE_PROJECT_MANAGER_EMAIL + "," +
			ATTRIBUTE_PROJECT_MANAGER_PHONE + "," +*/
			ATTRIBUTE_CUSTOMER_CONTACT_ID + ")" +
			/*ATTRIBUTE_CUSTOMER_CONTACT_MANAGER + "," +
			ATTRIBUTE_CUSTOMER_CONTACT_EMAIL + "," +
			ATTRIBUTE_CUSTOMER_CONTACT_PHONE + "," +
			ATTRIBUTE_SEARCH + "," +
			ATTRIBUTE_TOTAL_PROJECTED_PERIOD_SAVINGS + "," +
			ATTRIBUTE_TOTAL_ACTUAL_PERIOD_SAVINGS + ")" +*/
			" values (" +
			SqlHandler.delimitString(peiProjectViewBean.getCompanyId()) + "," +
			peiProjectViewBean.getProjectId() + "," +
			SqlHandler.delimitString(peiProjectViewBean.getProjectName()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getProjectDesc()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getProjectCategory()) + "," +
			StringHandler.nullIfZero(peiProjectViewBean.getProjectManagerId()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getFacilityId()) + "," +
			DateHandler.getOracleToDateFunction(peiProjectViewBean.getApprovedDate()) + "," +
			DateHandler.getOracleToDateFunction(peiProjectViewBean.getStartDate()) + "," +
			DateHandler.getOracleToDateFunction(peiProjectViewBean.getProjectedFinistedDate()) + "," +
			peiProjectViewBean.getPojectedCost() + "," +
			peiProjectViewBean.getActualCost() + "," +
			SqlHandler.delimitString(peiProjectViewBean.getComments()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getBestPractice()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getProjectStatus()) + "," +
			DateHandler.getOracleToDateFunction(peiProjectViewBean.getActualFinishDate()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getPriority()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getGainShareDuration()) + "," +
			DateHandler.getOracleToDateFunction(peiProjectViewBean.getProposedDateToClient()) + "," +
			/*SqlHandler.delimitString(peiProjectViewBean.getProjectManager()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getProjectManagerEmail()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getProjectManagerPhone()) + "," +*/
			StringHandler.nullIfZero(peiProjectViewBean.getCustomerContactId()) + ")" ;
			/*SqlHandler.delimitString(peiProjectViewBean.getCustomerContactManager()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getCustomerContactEmail()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getCustomerContactPhone()) + "," +
			SqlHandler.delimitString(peiProjectViewBean.getSearch()) + "," +
			peiProjectViewBean.getTotalProjectedPeriodSavings() + "," +
			peiProjectViewBean.getTotalActualPeriodSavings() + ")";*/

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PeiProjectViewBean peiProjectViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(peiProjectViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PeiProjectViewBean peiProjectViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + UPDATE_TABLE + " set " +
			/*ATTRIBUTE_PROJECT_ID + "=" +
				StringHandler.nullIfZero(peiProjectViewBean.getProjectId()) + "," +*/
			ATTRIBUTE_PROJECT_NAME + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getProjectName()) + "," +
			ATTRIBUTE_PROJECT_DESC + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getProjectDesc()) + "," +
			ATTRIBUTE_PROJECT_CATEGORY + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getProjectCategory()) + "," +
			ATTRIBUTE_PROJECT_MANAGER_ID + "=" +
				StringHandler.nullIfZero(peiProjectViewBean.getProjectManagerId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getFacilityId()) + "," +
			ATTRIBUTE_APPROVED_DATE + "=" +
				DateHandler.getOracleToDateFunction(peiProjectViewBean.getApprovedDate()) + "," +
			ATTRIBUTE_START_DATE + "=" +
				DateHandler.getOracleToDateFunction(peiProjectViewBean.getStartDate()) + "," +
			ATTRIBUTE_PROJECTED_FINISTED_DATE + "=" +
				DateHandler.getOracleToDateFunction(peiProjectViewBean.getProjectedFinistedDate()) + "," +
			ATTRIBUTE_POJECTED_COST + "=" +
				StringHandler.nullIfZero(peiProjectViewBean.getPojectedCost()) + "," +
			ATTRIBUTE_ACTUAL_COST + "=" +
				StringHandler.nullIfZero(peiProjectViewBean.getActualCost()) + "," +
			ATTRIBUTE_COMMENTS + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getComments()) + "," +
			ATTRIBUTE_BEST_PRACTICE + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getBestPractice()) + "," +
			ATTRIBUTE_PROJECT_STATUS + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getProjectStatus()) + "," +
			ATTRIBUTE_ACTUAL_FINISH_DATE + "=" +
			DateHandler.getOracleToDateFunction(peiProjectViewBean.getActualFinishDate()) + "," +
			ATTRIBUTE_GAIN_SHARE_DURATION + "=" +
			SqlHandler.delimitString(peiProjectViewBean.getGainShareDuration()) + "," +
			ATTRIBUTE_PROPOSED_DATE_TO_CLIENT + "=" +
			DateHandler.getOracleToDateFunction(peiProjectViewBean.getProposedDateToClient()) + "," +
			ATTRIBUTE_PRIORITY + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getPriority()) + "," +
			/*ATTRIBUTE_PROJECT_MANAGER + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getProjectManager()) + "," +
			ATTRIBUTE_PROJECT_MANAGER_EMAIL + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getProjectManagerEmail()) + "," +
			ATTRIBUTE_PROJECT_MANAGER_PHONE + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getProjectManagerPhone()) + "," +*/
			ATTRIBUTE_CUSTOMER_CONTACT_ID + "=" +
				StringHandler.nullIfZero(peiProjectViewBean.getCustomerContactId()) + " " +
			/*ATTRIBUTE_CUSTOMER_CONTACT_MANAGER + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getCustomerContactManager()) + "," +
			ATTRIBUTE_CUSTOMER_CONTACT_EMAIL + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getCustomerContactEmail()) + "," +
			ATTRIBUTE_CUSTOMER_CONTACT_PHONE + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getCustomerContactPhone()) + "," +
			ATTRIBUTE_SEARCH + "=" +
				SqlHandler.delimitString(peiProjectViewBean.getSearch()) + "," +
			ATTRIBUTE_TOTAL_PROJECTED_PERIOD_SAVINGS + "=" +
				StringHandler.nullIfZero(peiProjectViewBean.getTotalProjectedPeriodSavings()) + "," +
			ATTRIBUTE_TOTAL_ACTUAL_PERIOD_SAVINGS + "=" +
				StringHandler.nullIfZero(peiProjectViewBean.getTotalActualPeriodSavings()) + " " +*/
			"where " + ATTRIBUTE_PROJECT_ID + "=" +
				peiProjectViewBean.getProjectId();

		return new SqlManager().update(conn, query);
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

		Collection peiProjectViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PeiProjectViewBean peiProjectViewBean = new PeiProjectViewBean();
			load(dataSetRow, peiProjectViewBean);
			peiProjectViewBeanColl.add(peiProjectViewBean);
		}

		return peiProjectViewBeanColl;
	}

	 /****************************************************************************
		* Returns a collection of PeiProjectViewBean for the criteria selected
		* on the web page or for a particular project. Had to create a new method
		* because the search criteria can not handel joining two tabels in the query
		*
		* @param peiProjectInputBean the <code>PeiProjectInputBean</code> has
		*        the input parameters submited in the from
		* @throws BaseException if there are problems communicating with the
		*          database
		****************************************************************************/
	 //select
		public Collection selectForProjectsList(SearchCriteria criteria,PeiProjectInputBean peiProjectInputBean)
			throws BaseException {

			Connection connection = null;
			Collection c = null;
			try {
				connection = this.getDbManager().getConnection();
				c = selectForProjectsList(criteria,peiProjectInputBean,connection);
			}
			finally {
				this.getDbManager().returnConnection(connection);
			}
			return c;
		}

		public Collection selectForProjectsList(SearchCriteria criteria,
		 PeiProjectInputBean peiProjectInputBean,
		 Connection conn) throws BaseException {
		 Collection peiProjectViewBeanColl = new Vector();
		 String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		 String[] keywordsCollectionSelect = peiProjectInputBean.
			getKeywordsCollectionSelect();
		 String[] statusCollectionSelect = peiProjectInputBean.
			getStatusCollectionSelect();
		 String[] categoryCollectionSelect = peiProjectInputBean.
			getCategoryCollectionSelect();

		 boolean falgforand = false;
		 if (getWhereClause(criteria).length() > 0) {
			falgforand = true;
		 }
		 else if (keywordsCollectionSelect.length > 0 ||
			statusCollectionSelect.length > 0 || categoryCollectionSelect.length > 0) {
			query += " where ";
		 }

		 if (keywordsCollectionSelect.length > 0) {
			String keywordsSelectedString = "";
			if (keywordsCollectionSelect != null) {
			 for (int loop = 0; loop < keywordsCollectionSelect.length; loop++) {
				if (loop > 0) {
				 keywordsSelectedString += ",";
				}
				keywordsSelectedString += "'" + keywordsCollectionSelect[loop] + "'";
			 }
			}

			if (keywordsCollectionSelect != null &&
			 "or".equalsIgnoreCase(peiProjectInputBean.getKeywordAndOrOr()) &&
			 !"'All'".equalsIgnoreCase(keywordsSelectedString)) {
			 if (falgforand)
				query += " and " + ATTRIBUTE_PROJECT_ID + " in (select " +
				 ATTRIBUTE_PROJECT_ID + " from " + PEI_PROJECT_KEYWORD_TABLE + " where " +
				 ATTRIBUTE_KEYWORD_ID + " in (" +
				 keywordsSelectedString + ")) ";
			 else {
				query += " " + ATTRIBUTE_PROJECT_ID + " in (select " +
				 ATTRIBUTE_PROJECT_ID + " from " + PEI_PROJECT_KEYWORD_TABLE + " where " +
				 ATTRIBUTE_KEYWORD_ID + " in (" +
				 keywordsSelectedString + ")) ";
				falgforand = true;
			 }
			}
			else if (keywordsCollectionSelect != null &&
			 "and".equalsIgnoreCase(peiProjectInputBean.getKeywordAndOrOr()) &&
			 !"'All'".equalsIgnoreCase(keywordsSelectedString)) {
			 String keyWordQuery = "";
			 keyWordQuery += "(";
			 int serchCount = 0;
			 for (int loop = 0; loop < keywordsCollectionSelect.length; loop++) {
				if (serchCount > 0) {
				 keyWordQuery += " and ";
				}
				String selectedKeyword = (String) keywordsCollectionSelect[loop];
				if (!"All".equalsIgnoreCase(selectedKeyword)) {
				 keyWordQuery += " " + ATTRIBUTE_KEYWORD_ID + " = '" + selectedKeyword +
					"' ";
				 serchCount++;
				}
			 }
			 keyWordQuery += ")";
			 if (!"()".equalsIgnoreCase(keyWordQuery)) {
				if (falgforand) {
				 query +=
					" and " + ATTRIBUTE_PROJECT_ID + " in (select " + ATTRIBUTE_PROJECT_ID +
					" from " + PEI_PROJECT_KEYWORD_TABLE + " where " +
					keyWordQuery + " )";
				}
				else {
				 query +=
					" " + ATTRIBUTE_PROJECT_ID + " in (select " + ATTRIBUTE_PROJECT_ID +
					" from " + PEI_PROJECT_KEYWORD_TABLE + " where " +
					keyWordQuery + " )";
				 falgforand = true;
				}
			 }
			}
		 }

		 if (statusCollectionSelect.length > 0) {
			String statusSelectedString = "";
			if (statusCollectionSelect != null) {
			 for (int loop = 0; loop < statusCollectionSelect.length; loop++) {
				if (loop > 0) {
				 statusSelectedString += ",";
				}
				statusSelectedString += "'" + statusCollectionSelect[loop] + "'";
			 }
			}

			if (statusCollectionSelect != null &&
			 !"'All'".equalsIgnoreCase(statusSelectedString)) {
			 if (falgforand)
				query += " and " + ATTRIBUTE_PROJECT_STATUS + " in (" +
				 statusSelectedString + ") ";
			 else {
				query += " " + ATTRIBUTE_PROJECT_STATUS + " in (" + statusSelectedString +
				 ") ";
				falgforand = true;
			 }
			}
			else if (statusCollectionSelect != null &&
			 "'All'".equalsIgnoreCase(statusSelectedString)) {

			 if (falgforand)
				query += " and nvl("+ATTRIBUTE_PROJECT_STATUS+",0) not in ('8') ";
			 else {
				query += " nvl("+ATTRIBUTE_PROJECT_STATUS+",0) not in ('8') ";
				falgforand = true;
			 }
			}
		 }
		 else if (peiProjectInputBean.getProjectId() == null) {
			if (keywordsCollectionSelect.length == 0 && categoryCollectionSelect.length == 0 && getWhereClause(criteria).length() ==0) {
				query += " where ";
			 }

			if (falgforand)
			 query += " and nvl("+ATTRIBUTE_PROJECT_STATUS+",0) not in ('8') ";
			else {
			 query += " nvl("+ATTRIBUTE_PROJECT_STATUS+",0) not in ('8') ";
			 falgforand = true;
			}
		 }

		 if (categoryCollectionSelect.length > 0) {
			String categorySelectedString = "";
			if (categoryCollectionSelect != null) {
			 for (int loop = 0; loop < categoryCollectionSelect.length; loop++) {
				if (loop > 0) {
				 categorySelectedString += ",";
				}
				categorySelectedString += "'" + categoryCollectionSelect[loop] + "'";
			 }
			}

			if (categoryCollectionSelect != null &&
			 !"'All'".equalsIgnoreCase(categorySelectedString)) {
			 if (falgforand)
				query += " and " + ATTRIBUTE_PROJECT_CATEGORY + " in (" +
				 categorySelectedString + ") ";
			 else {
				query += " " + ATTRIBUTE_PROJECT_CATEGORY + " in (" + categorySelectedString +
				 ") ";
				falgforand = true;
			 }
			}
		 }

		 String sortBy = "";
		 if (peiProjectInputBean.getSortBy() !=null && peiProjectInputBean.getSortBy().length() > 0)
		 {
				sortBy = " order by " + peiProjectInputBean.getSortBy();
		 }
		 query += sortBy;

		 DataSet dataSet = new SqlManager().select(conn, query);
		 Iterator dataIter = dataSet.iterator();

		 while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			PeiProjectViewBean peiProjectViewBean = new PeiProjectViewBean();
			load(dataSetRow, peiProjectViewBean);

			if ( (peiProjectInputBean.getProjectId() != null &&
			 peiProjectInputBean.getProjectId().intValue() > 0) ||
			 (peiProjectInputBean.getSubmitCreateReport() != null &&
			 peiProjectInputBean.getSubmitCreateReport().trim().length() > 0) ||
			 (peiProjectInputBean.getSubmitPrint() != null &&
			 peiProjectInputBean.getSubmitPrint().trim().length() > 0) ){

			 PeiProjectKeywordBeanFactory keywordFactory = new
				PeiProjectKeywordBeanFactory(getDbManager());
			 SearchCriteria childCriteria = new SearchCriteria();
			 childCriteria.addCriterion("projectId", SearchCriterion.EQUALS,
				"" + peiProjectViewBean.getProjectId().intValue() + "");
			 peiProjectViewBean.setKeywordsCollection(keywordFactory.select(
				childCriteria));

			 VvPeiProjectKeywordBeanFactory baseKeywordFactory = new
				VvPeiProjectKeywordBeanFactory(getDbManager());
			 peiProjectViewBean.setBaseKeywordsCollection(baseKeywordFactory.
				selectKeywordsNotInProject(
				"" + peiProjectViewBean.getProjectId().intValue() + ""));

			 PeiProjectDocumentBeanFactory documentFactory = new
				PeiProjectDocumentBeanFactory(getDbManager());
			 childCriteria = new SearchCriteria();
			 childCriteria.addCriterion("projectId", SearchCriterion.EQUALS,
				"" + peiProjectViewBean.getProjectId().intValue() + "");
			 peiProjectViewBean.setDocumentsCollection(documentFactory.select(
				childCriteria));

			 PeiProjectSavingsBeanFactory savingsFactory = new
				PeiProjectSavingsBeanFactory(getDbManager());
			 childCriteria = new SearchCriteria();
			 childCriteria.addCriterion("projectId", SearchCriterion.EQUALS,
				"" + peiProjectViewBean.getProjectId().intValue() + "");
			 peiProjectViewBean.setSavingsCollection(savingsFactory.select(
				childCriteria));
			}
			peiProjectViewBeanColl.add(peiProjectViewBean);
	 }
		 return peiProjectViewBeanColl;
		}

		public BigDecimal selectProjectId() throws BaseException {
		 BigDecimal projectId = null;
		 String query = "select " + PEI_PROJECT_ID_SEQ_TABLE + ".nextval " +
			ATTRIBUTE_PROJECT_ID +
			" from dual";

		 Connection connection = getDbManager().getConnection();
		 DataSet dataSet = new SqlManager().select(connection, query);
		 getDbManager().returnConnection(connection);

		 Iterator dataIter = dataSet.iterator();
		 while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			PeiProjectViewBean peiProjectViewBean = new PeiProjectViewBean();
			load(dataSetRow, peiProjectViewBean);

			projectId = peiProjectViewBean.getProjectId();
		 }

	return projectId;
 }
}