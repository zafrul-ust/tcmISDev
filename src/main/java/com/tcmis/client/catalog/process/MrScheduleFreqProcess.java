package com.tcmis.client.catalog.process;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatPartCommentBean;
import com.tcmis.client.catalog.beans.MrScheduleFreqInputBean;
import com.tcmis.client.catalog.factory.CatPartCommentBeanFactory;
import com.tcmis.client.catalog.factory.CatPartCommentsViewBeanFactory;
import com.tcmis.client.common.beans.LineItemViewBean;
import com.tcmis.client.order.factory.RequestLineItemBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.supply.beans.SupplierLocationPartOvBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class MrScheduleFreqProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public MrScheduleFreqProcess(String client,Locale locale) {
	super(client,locale);
 }

 public Vector<LineItemViewBean> getSearchResult(MrScheduleFreqInputBean bean) throws BaseException {
	DbManager dbManager = new DbManager(getClient());

	GenericSqlFactory factory = new GenericSqlFactory(dbManager,new LineItemViewBean());

	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("prNumber",  SearchCriterion.EQUALS, ""+bean.getPrNumber());
	criteria.addCriterion("lineItem",  SearchCriterion.EQUALS, bean.getLineItem());

	return (Vector<LineItemViewBean>)factory.select(criteria,null,"line_item_ii_view");
 }

 private String runQuery(DbManager dbManager,String query,Boolean selectS) {
	Connection connection = null;
	String s = null;
	try {
		connection = dbManager.getConnection();
		Statement st = connection.createStatement();
		log.debug(query);
		if( selectS ) {
			st.execute(query);
			ResultSet rs = st.getResultSet();
			rs.next();
			s = rs.getString(1);
		}
		else 
			st.executeUpdate(query);
	}
	catch (Exception e) {
        e.printStackTrace();
        DbSelectException ex = new DbSelectException("error.db.insert");
		ex.setRootCause(e);        
//		System.out.println(ex.getMessage());
	}
	try {
		if( dbManager != null && connection !=  null ) 
			dbManager.returnConnection(connection);
	}
	catch (Exception e) {}
	return s;
 }
 
 public void update(MrScheduleFreqInputBean bean) throws
	BaseException {
	 DbManager dbManager = new DbManager(getClient());

	 GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance(this.getLocaleObject());
	 calendar.setTime(bean.getStartingDate());
	 
	 int q = bean.getQuantity().intValue();
	 int t = bean.getTotal().intValue();
	 int c = (t+q-1)/q;
	 int r = t-q*(c-1);
	 int field = calendar.MONTH;
	 int delta = 1;
	 boolean dateQ = false;
	 if( bean.getFrequency().equals("week") ) {
		 field = calendar.DATE;
		 delta = 7*bean.getWeek().intValue();
	 }
	 else if( bean.getFrequency().equals("day") ) {
		 dateQ = true;
		 field = calendar.DATE;
		 delta = bean.getDay().intValue();
	 }
	 else { // month
		 delta = bean.getMonth().intValue();
	 }
	 String query = "delete from DELIVERY_SCHEDULE where company_id = '"+bean.getCompanyId()+"' and pr_number = "+bean.getPrNumber()+" and line_item = '"+bean.getLineItem()+"'";

	 runQuery(dbManager,query,false);
	 
	 if( !dateQ )
		 while (c-- > 0) {

			 int qty = bean.getQuantity().intValue();
			 if( c==0 ) qty = r;
			 //String query = "insert into DELIVERY_SCHEDULE select COMPANY_ID, PR_NUMBER, LINE_ITEM, FX_PREVIOUS_BUSINESS_DATE(DATE_TO_DELIVER), QTY from dual";
			 query = "insert into DELIVERY_SCHEDULE (COMPANY_ID, PR_NUMBER, LINE_ITEM, DATE_TO_DELIVER,QTY ) values("+
				 "'"+bean.getCompanyId()+"'," +
				 bean.getPrNumber()+"," +
				 "'"+bean.getLineItem()+"'," +
				 "trunc(fx_schedule_date('"+bean.getCompanyId()+"',"+bean.getPrNumber()+"," +"'"+bean.getLineItem()+"',"+ DateHandler.getOracleToDateFunction(calendar.getTime()) +"))," +
				 "'"+qty+"')";

			 runQuery(dbManager,query,false);
			 
			 calendar.add(field,delta);
		 }
	 else {//if( !dateQ )
		 String dateS = null;
		 String prevDateS = null;
		 int prevQuantity = 0 ;
		 String prevS = null;
		 
		 while (c-- > 0) {
			 int qty = bean.getQuantity().intValue();
			 if( c==0 ) qty = r;
			 
			 String currS = DateHandler.getOracleToDateFunction(calendar.getTime());
			 String dateDelivery = "select fx_schedule_date('"+bean.getCompanyId()+"',"+bean.getPrNumber()+"," +"'"+bean.getLineItem()+"',"+ currS +") from dual";
			 dateS = runQuery(dbManager,dateDelivery,true);
			 if( prevDateS != null && !dateS.equals(prevDateS)) {
             //String query = "insert into DELIVERY_SCHEDULE select COMPANY_ID, PR_NUMBER, LINE_ITEM, FX_PREVIOUS_BUSINESS_DATE(DATE_TO_DELIVER), QTY from dual";
			 query = "insert into DELIVERY_SCHEDULE (COMPANY_ID, PR_NUMBER, LINE_ITEM, DATE_TO_DELIVER,QTY ) values("+
				 "'"+bean.getCompanyId()+"'," +
				 bean.getPrNumber()+"," +
				 "'"+bean.getLineItem()+"'," +
				 "TO_DATE('"+prevDateS.substring(0,prevDateS.length()-2)+"', 'RRRR/MM/DD hh24:mi:ss')," +
				 "'"+prevQuantity+"')";

			 runQuery(dbManager,query,false);
			 prevQuantity = 0;
			 }
			 prevQuantity += qty;
             prevDateS = dateS;
			 prevS = currS;
			 calendar.add(field,delta);
		 }

		 query = "insert into DELIVERY_SCHEDULE (COMPANY_ID, PR_NUMBER, LINE_ITEM, DATE_TO_DELIVER,QTY ) values("+
		 "'"+bean.getCompanyId()+"'," +
		 bean.getPrNumber()+"," +
		 "'"+bean.getLineItem()+"'," +
		 "TO_DATE('"+dateS.substring(0,dateS.length()-2)+"', 'RRRR/MM/DD hh24:mi:ss')," +  
		 "'"+prevQuantity+"')";

		 runQuery(dbManager,query,false);
	 }

/*	 delete from DELIVERY_SCHEDULE where pr_number = xxx and line_item = yy

	 

	 insert into DELIVERY_SCHEDULE

	 select COMPANY_ID, PR_NUMBER, LINE_ITEM, FX_PREVIOUS_BUSINESS_DATE(DATE_TO_DELIVER), QTY from dual
*/
   RequestLineItemBeanFactory rlibf = new RequestLineItemBeanFactory(dbManager);
   rlibf.updateQuantity(bean.getTotal(), bean.getCompanyId(), bean.getPrNumber(), bean.getLineItem());
 } 
}