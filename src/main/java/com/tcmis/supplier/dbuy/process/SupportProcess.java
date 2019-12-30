package com.tcmis.supplier.dbuy.process;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.dla.factory.DlaDailyShipStatusBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.supplier.dbuy.factory.DbuyAcknowledgementBeanFactory;
import com.tcmis.supplier.dbuy.factory.DbuyMatchingDisplayViewBeanFactory;
import com.tcmis.supplier.dbuy.factory.DlaGasOpenOrdersBeanFactory;
import com.tcmis.supplier.dbuy.factory.EbuyDailyConfirmedOrdersBeanFactory;

/******************************************************************************
 * Process for dbuy misatch
 * @version 1.0
 *****************************************************************************/
public class SupportProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public SupportProcess(String client) {
		super(client);
	}

	public SupportProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getAcknowledgements(String po) throws BaseException {
		Collection ackBeans = null;
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		DbuyAcknowledgementBeanFactory ackFactory = new DbuyAcknowledgementBeanFactory(dbManager);
		try {
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("radianPo",SearchCriterion.EQUALS,po);
			ackBeans = ackFactory.selectDistinct(searchCriteria);
		} catch (BaseException be2) {
			log.error("Base Exception in getAcknowledgements: " + be2);
		} finally {
			dbManager = null;
			ackFactory = null;
		}
		return ackBeans;
	}

	public Collection getSent(String po) throws BaseException {
		Collection sentBeans = null;
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		DbuyMatchingDisplayViewBeanFactory dbuyViewFactory = new DbuyMatchingDisplayViewBeanFactory(dbManager);
		try {
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("radianPo",SearchCriterion.EQUALS,po);
			sentBeans = dbuyViewFactory.select(searchCriteria);
		} catch (BaseException be2) {
			log.error("Base Exception in getSent: " + be2);
		} finally {
			dbManager = null;
			dbuyViewFactory = null;
		}
		return sentBeans;
	}

	public Collection getDailyConfirmedEbuys() throws BaseException {
		Collection ebuys = null;
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		EbuyDailyConfirmedOrdersBeanFactory factory = new EbuyDailyConfirmedOrdersBeanFactory(dbManager);
		try {
			ebuys = factory.selectEbuys();
		} catch (BaseException be2) {
			log.error("Base Exception in getSent: " + be2);
		} finally {
			dbManager = null;
			factory = null;
		}
		return ebuys;

	}

	public Collection getWeeklyConfirmedEbuys(String weeks) throws BaseException {
		Collection ebuys = null;
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		EbuyDailyConfirmedOrdersBeanFactory factory = new EbuyDailyConfirmedOrdersBeanFactory(dbManager);
		try {
			ebuys = factory.selectEbuysTime(weeks);
		} catch (BaseException be2) {
			log.error("Base Exception in getSent: " + be2);
		} finally {
			dbManager = null;
			factory = null;
		}
		return ebuys;

	}

	public Collection getOpenGasOrders() throws BaseException {
		Collection dbuys = null;
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		DlaGasOpenOrdersBeanFactory factory = new DlaGasOpenOrdersBeanFactory(dbManager);
		try {
			dbuys = factory.select();
		} catch (BaseException be2) {
			log.error("Base Exception in getOpenGasOrders: " + be2);
		} finally {
			dbManager = null;
			factory = null;
		}
		return dbuys;
	}

	public Collection getShipStatusTenDays() throws BaseException {
		Collection allTenDays = new Vector(10);
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		DlaDailyShipStatusBeanFactory factory = new DlaDailyShipStatusBeanFactory(dbManager);
		String day = "";
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		for (int i=-1; i<9; i++) {
			Date searchDay = new Date( now.getTime() - (1000 * 60 * 60 * 24 * i) );
			day = sdf.format(searchDay);
			if(log.isDebugEnabled()) {
				log.debug("Searching for ship status for day: " + day);
			}
			try {
				Collection oneDay = factory.select(day);
				allTenDays.add(oneDay);
			} catch (BaseException be3) {
				log.error("Base Exception in getOpenGasOrders: " + be3);
			} finally {
				dbManager = null;

			}
		}
		return allTenDays;
	}

	public Collection getShipStatusXDays(int days) throws BaseException {
		Collection allDays = new Vector(days);
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		DlaDailyShipStatusBeanFactory factory = new DlaDailyShipStatusBeanFactory(dbManager);
		String day = "";
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		for (int i=-1; i<days-1; i++) {
			Date searchDay = new Date( now.getTime() - (1000 * 60 * 60 * 24 * i) );
			day = sdf.format(searchDay);
			if(log.isDebugEnabled()) {
				log.debug("Searching for ship status for day: " + day);
			}
			try {
				Collection oneDay = factory.select(day);
				allDays.add(oneDay);
			} catch (BaseException be3) {
				log.error("Base Exception in getOpenGasOrders: " + be3);
			} finally {
				dbManager = null;

			}
		}
		return allDays;
	}

	public int getExpectedOrderCount() throws BaseException {
		int orderCount = 0;
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		String txtMonth = month + "";
		if (month<10) txtMonth = "0" + month;
		String  qry = "";
		qry += "select count(rlie.desired_ship_date) order_num ";
		qry += "from buy_order bo, customer.request_line_item_extension rlie ";
		qry += "where bo.mr_number in ";
		qry += "( select pr_number from customer.customer_po_stage where company_id='USGOV' and trading_partner='dla' and transaction_type='850' and status not in ('TEST','IGNORE', 'CANCELLED') ) ";
		qry += "and bo.mr_number = rlie.pr_number ";
		qry += "and trunc(rlie.desired_ship_date) between to_date('" + txtMonth + "-01-2008','MM-DD-YYYY') and trunc(SYSDATE-1)";

		ResultSet rs = doQuery(qry);
		try {
			if (rs!=null) {
				rs.next();
				orderCount = rs.getInt("order_num");
			}
		} catch (SQLException sqle) {
			log.error("SQL Exception getting expected order count");
		}
		return orderCount;

	}

	public int getOnTimeOrderCount() throws BaseException {
		int ontimeCount = 0;
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		String txtMonth = month + "";
		if (month<10) txtMonth = "0" + month;
		String qry = "";
		qry += "select count(issue.DATE_SHIPPED) on_time_count ";
		qry += "from buy_order bo, customer.request_line_item_extension rlie, customer.issue ";
		qry += "where bo.mr_number in ";
		qry += "( select pr_number from customer.customer_po_stage x where company_id='USGOV' and trading_partner='dla' and transaction_type='850' and status not in ('TEST','IGNORE', 'CANCELLED') ) ";
		qry += "and bo.mr_number = rlie.pr_number ";
		qry += "and trunc(rlie.desired_ship_date) between to_date('" + txtMonth + "-01-2008','MM-DD-YYYY') and trunc(SYSDATE-1) ";
		qry += "and bo.MR_NUMBER = issue.PR_NUMBER ";
		qry += "and issue.ship_confirm_date is not null ";
		qry += "and trunc(issue.DATE_SHIPPED) <= rlie.desired_ship_date";

		ResultSet rs = doQuery(qry);
		try {
			if (rs!=null) {
				rs.next();
				ontimeCount = rs.getInt("on_time_count");
			}
		} catch (SQLException sqle) {
			log.error("SQL Exception getting on time order count");
		}
		return ontimeCount;

	}

	private ResultSet doQuery(String query) throws BaseException {
		ResultSet rs = null;
		DbManager dbManager = new DbManager(this.getClient(),getLocale());
		Connection conn = null;
		try {
			conn = dbManager.getConnection();
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(query);
		} catch (DbConnectionException dbce) {
			log.error("Db Connection Exception executing internal order process query: " + dbce);
		} catch (SQLException sqle) {
			log.error("SQL Exception executing internal order process query: " + sqle);
		} finally {
			// dbManager.returnConnection(conn);
		}


		return rs;
	}

	public String doAckMatch(String batchId) throws BaseException {
		String result=null;
		Connection connect1=null;
		CallableStatement cs=null;
		DbManager dbManager = new DbManager(this.getClient(),getLocale());

		/*
         procedure pkg_dbuy.dbuy_acknowledgement_match(BATCH_ID number) )
		 */

		connect1=dbManager.getConnection();
		try {
			cs=connect1.prepareCall( "{call pkg_dbuy.dbuy_acknowledgement_match(?)}" );
			cs.setString(1,batchId);
			cs.execute();
			result = "OK";
		} catch (SQLException sqle) {
			log.error("SQL Exception in doAckMatch: " + sqle);
		} finally {
			dbManager.returnConnection(connect1);
		}


		return result;
	}

}
