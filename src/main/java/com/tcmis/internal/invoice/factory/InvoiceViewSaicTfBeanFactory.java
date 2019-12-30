package com.tcmis.internal.invoice.factory;


import java.sql.Connection;
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
import com.tcmis.internal.invoice.beans.InvoiceViewSiacBean;


/******************************************************************************
 * CLASSNAME: InvoiceViewSaicTfBeanFactory <br>
 * @version: 1.0, Aug 23, 2010 <br>
 *****************************************************************************/


public class InvoiceViewSaicTfBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
	public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_LAB_COST = "LAB_COST";
	public String ATTRIBUTE_MARK_UP = "MARK_UP";
	public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
	

	//table name
	public String TABLE = "INVOICE_FORMAT_SAIC_TEST_FEE";


	//constructor
	public InvoiceViewSaicTfBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("invoice")) {
			return ATTRIBUTE_INVOICE;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else if(attributeName.equals("invoiceGroup")) {
			return ATTRIBUTE_INVOICE_GROUP;
		}
		else if(attributeName.equals("invoiceAmount")) {
			return ATTRIBUTE_INVOICE_AMOUNT;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("labCost")) {
			return ATTRIBUTE_LAB_COST;
		}
		else if(attributeName.equals("markUp")) {
			return ATTRIBUTE_MARK_UP;
		}
		else if(attributeName.equals("netAmount")) {
			return ATTRIBUTE_NET_AMOUNT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, InvoiceViewSiacBean.class);
	}


	
	//select
	public Collection select(SearchCriteria criteria)
	throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,  connection);
		}
		catch (Exception e) {
			log.fatal("Error querying DB ", e);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	
	
	public Collection select(SearchCriteria criteria,  Connection conn)
	throws BaseException {

		Collection invoiceViewSiacBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) ;

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceViewSiacBean invoiceViewSiacBean = new InvoiceViewSiacBean();
			load(dataSetRow, invoiceViewSiacBean);
			invoiceViewSiacBeanColl.add(invoiceViewSiacBean);
		}

		return invoiceViewSiacBeanColl;
	}

}