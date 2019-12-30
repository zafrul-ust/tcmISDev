package com.tcmis.internal.invoice.factory;


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
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.invoice.beans.InvoiceFormatViewNrgBean;


/******************************************************************************
 * CLASSNAME: InvoiceFormatViewNrgBeanFactory <br>
 * @version: 1.0, Jul 31, 2008 <br>
 *****************************************************************************/


public class InvoiceFormatViewNrgBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
    public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
    public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
    public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
    public String ATTRIBUTE_CITY = "CITY";
    public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
    public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
    public String ATTRIBUTE_ZIP = "ZIP";
    public String ATTRIBUTE_ATTENTION = "ATTENTION";
    public String ATTRIBUTE_INVOICE_INQUIRIES = "INVOICE_INQUIRIES";
    public String ATTRIBUTE_CONTACT_PHONE = "CONTACT_PHONE";
    public String ATTRIBUTE_CONTACT_EMAIL = "CONTACT_EMAIL";
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_REGION = "REGION";
	public String ATTRIBUTE_TAX_RATE = "TAX_RATE";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_INVOICE_UNIT_PRICE = "INVOICE_UNIT_PRICE";
	public String ATTRIBUTE_TOTAL_ADD_CHARGE = "TOTAL_ADD_CHARGE";
	public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
	public String ATTRIBUTE_TOTAL_SALES_TAX = "TOTAL_SALES_TAX";
	public String ATTRIBUTE_NET_AMOUNT = "NET_AMOUNT";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";

	//table name
	public String TABLE = "INVOICE_FORMAT_VIEW_NRG";


	//constructor
	public InvoiceFormatViewNrgBeanFactory(DbManager dbManager) {
		super(dbManager);
	}
	public InvoiceFormatViewNrgBeanFactory(DbManager dbManager,String newTable) {
		super(dbManager);
		if( newTable != null && newTable.length() != 0 ) TABLE = newTable;
	}


	//get column names
	public String getColumnName(String attributeName) {
	   if (attributeName.equals("locationDesc")) {
	      return ATTRIBUTE_LOCATION_DESC;
	    }
	    else if (attributeName.equals("addressLine1")) {
	      return ATTRIBUTE_ADDRESS_LINE_1;
	    }
	    else if (attributeName.equals("addressLine2")) {
	      return ATTRIBUTE_ADDRESS_LINE_2;
	    }
	    else if (attributeName.equals("addressLine3")) {
	      return ATTRIBUTE_ADDRESS_LINE_3;
	    }
	    else if (attributeName.equals("city")) {
	      return ATTRIBUTE_CITY;
	    }
	    else if (attributeName.equals("stateAbbrev")) {
	        return ATTRIBUTE_STATE_ABBREV;
	      }
	    else if (attributeName.equals("countryAbbrev")) {
	        return ATTRIBUTE_COUNTRY_ABBREV;
	      }
	    else if (attributeName.equals("zip")) {
	        return ATTRIBUTE_ZIP;
	      }
	    else if (attributeName.equals("attention")) {
	        return ATTRIBUTE_ATTENTION;
	      }
	    else if (attributeName.equals("invoiceInquiries")) {
	        return ATTRIBUTE_INVOICE_INQUIRIES;
	      }
	    else if (attributeName.equals("contactPhone")) {
	        return ATTRIBUTE_CONTACT_PHONE;
	      }
	    else if (attributeName.equals("contactEmail")) {
	        return ATTRIBUTE_CONTACT_EMAIL;
	      }
		else if (attributeName.equals("invoice")) {
	      return ATTRIBUTE_INVOICE;
	    }
		else if(attributeName.equals("invoiceGroup")) {
			return ATTRIBUTE_INVOICE_GROUP;
		}
		else if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("region")) {
			return ATTRIBUTE_REGION;
		}
		else if(attributeName.equals("taxRate")) {
			return ATTRIBUTE_TAX_RATE;
		}
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("invoiceUnitPrice")) {
			return ATTRIBUTE_INVOICE_UNIT_PRICE;
		}
		else if(attributeName.equals("totalAddCharge")) {
			return ATTRIBUTE_TOTAL_ADD_CHARGE;
		}
		else if(attributeName.equals("serviceFee")) {
			return ATTRIBUTE_SERVICE_FEE;
		}
		else if(attributeName.equals("totalSalesTax")) {
			return ATTRIBUTE_TOTAL_SALES_TAX;
		}
		else if(attributeName.equals("netAmount")) {
			return ATTRIBUTE_NET_AMOUNT;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("accountNumber")) {
			return ATTRIBUTE_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InvoiceFormatViewNrgBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InvoiceFormatViewNrgBean invoiceFormatViewNrgBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
			"" + invoiceFormatViewNrgBean.getInvoice());

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


	public int delete(InvoiceFormatViewNrgBean invoiceFormatViewNrgBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
			"" + invoiceFormatViewNrgBean.getInvoice());

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
/*
	//insert
	public int insert(InvoiceFormatViewNrgBean invoiceFormatViewNrgBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(invoiceFormatViewNrgBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InvoiceFormatViewNrgBean invoiceFormatViewNrgBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVOICE + "," +
			ATTRIBUTE_INVOICE_GROUP + "," +
			ATTRIBUTE_INVOICE_PERIOD + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_END_DATE + "," +
			ATTRIBUTE_INVOICE_DATE + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_REGION + "," +
			ATTRIBUTE_TAX_RATE + "," +
			ATTRIBUTE_PART_NUMBER + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "," +
			ATTRIBUTE_TOTAL_ADD_CHARGE + "," +
			ATTRIBUTE_SERVICE_FEE + "," +
			ATTRIBUTE_TOTAL_SALES_TAX + "," +
			ATTRIBUTE_NET_AMOUNT + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_RADIAN_PO + ")" +
			" values (" +
			invoiceFormatViewNrgBean.getInvoice() + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getInvoiceGroup()) + "," +
			invoiceFormatViewNrgBean.getInvoicePeriod() + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getStartDate()) + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getEndDate()) + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getInvoiceDate()) + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getFacilityId()) + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getRegion()) + "," +
			invoiceFormatViewNrgBean.getTaxRate() + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getPartNumber()) + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getPartDescription()) + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getItemType()) + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getPackaging()) + "," +
			DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getDateDelivered()) + "," +
			invoiceFormatViewNrgBean.getQuantity() + "," +
			invoiceFormatViewNrgBean.getInvoiceUnitPrice() + "," +
			invoiceFormatViewNrgBean.getTotalAddCharge() + "," +
			invoiceFormatViewNrgBean.getServiceFee() + "," +
			invoiceFormatViewNrgBean.getTotalSalesTax() + "," +
			invoiceFormatViewNrgBean.getNetAmount() + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getCurrencyId()) + "," +
			invoiceFormatViewNrgBean.getPrNumber() + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getLineItem()) + "," +
			SqlHandler.delimitString(invoiceFormatViewNrgBean.getPoNumber()) + "," +
			invoiceFormatViewNrgBean.getRadianPo() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InvoiceFormatViewNrgBean invoiceFormatViewNrgBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(invoiceFormatViewNrgBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InvoiceFormatViewNrgBean invoiceFormatViewNrgBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVOICE + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getInvoice()) + "," +
			ATTRIBUTE_INVOICE_GROUP + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getInvoiceGroup()) + "," +
			ATTRIBUTE_INVOICE_PERIOD + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getInvoicePeriod()) + "," +
			ATTRIBUTE_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getStartDate()) + "," +
			ATTRIBUTE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getEndDate()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getInvoiceDate()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getFacilityId()) + "," +
			ATTRIBUTE_REGION + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getRegion()) + "," +
			ATTRIBUTE_TAX_RATE + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getTaxRate()) + "," +
			ATTRIBUTE_PART_NUMBER + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getPartNumber()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getPartDescription()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getItemType()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getPackaging()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				DateHandler.getOracleToDateFunction(invoiceFormatViewNrgBean.getDateDelivered()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getQuantity()) + "," +
			ATTRIBUTE_INVOICE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getInvoiceUnitPrice()) + "," +
			ATTRIBUTE_TOTAL_ADD_CHARGE + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getTotalAddCharge()) + "," +
			ATTRIBUTE_SERVICE_FEE + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getServiceFee()) + "," +
			ATTRIBUTE_TOTAL_SALES_TAX + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getTotalSalesTax()) + "," +
			ATTRIBUTE_NET_AMOUNT + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getNetAmount()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getCurrencyId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getLineItem()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(invoiceFormatViewNrgBean.getPoNumber()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(invoiceFormatViewNrgBean.getRadianPo()) + " " +
			"where " + ATTRIBUTE_INVOICE + "=" +
				invoiceFormatViewNrgBean.getInvoice();

		return new SqlManager().update(conn, query);
	}
*/

	//select
    public Collection select(SearchCriteria criteria, SortCriteria sortCriteria ,String client)
            throws BaseException {

            Connection connection = null;
            Collection c = null;
            try {
                connection = this.getDbManager().getConnection();
                c = select(criteria, sortCriteria, connection, client);
            }
            finally {
                this.getDbManager().returnConnection(connection);
            }
            return c;
        }

          public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
                               Connection conn, String client) throws BaseException {
        Collection invoiceColl = new Vector();
        String query = "";
        if(client.equalsIgnoreCase("NRG"))
        {
        	query= query + "select unique invoice, region from " + TABLE + " " ;
        }
        else
        {
        	query= query + "select unique invoice from " + TABLE + " " ;
        }
        query = query +  getWhereClause(criteria) + getOrderByClause(sortCriteria);
        
        if(log.isDebugEnabled()) {
          log.debug("QUERY:" + query);
        }
        sortCriteria.addCriterion("radianPo");
        DataSet baseDataSet = new SqlManager().select(conn, query);
        Iterator baseIter = baseDataSet.iterator();
        while (baseIter.hasNext()) {
          DataSetRow baseRow = (DataSetRow) baseIter.next();
          InvoiceFormatViewNrgBean bean = new
              InvoiceFormatViewNrgBean();
          load(baseRow, bean);
          //now I use the unique invoice # to query by invoice
          Collection invoiceFormatViewNrgBeanColl = new Vector();
          query = "select * from " + TABLE + " " +
              getWhereClause(new SearchCriteria("invoice", SearchCriterion.EQUALS, bean.getInvoice().toString())) + getOrderByClause(sortCriteria);
          if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
          }
          DataSet dataSet = new SqlManager().select(conn, query);
          Iterator dataIter = dataSet.iterator();
          int i = 1;
          while (dataIter.hasNext()) {
            DataSetRow dataSetRow = (DataSetRow) dataIter.next();
            InvoiceFormatViewNrgBean invoiceFormatViewNrgBean = new
                InvoiceFormatViewNrgBean();
            load(dataSetRow, invoiceFormatViewNrgBean);
            invoiceFormatViewNrgBean.setLineCount(new BigDecimal(i));
            invoiceFormatViewNrgBeanColl.add(invoiceFormatViewNrgBean);
            i++;
          }
          bean.setDetail(invoiceFormatViewNrgBeanColl);
          invoiceColl.add(bean);
        }
        return invoiceColl;
      }

/*
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

		Collection invoiceFormatViewNrgBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvoiceFormatViewNrgBean invoiceFormatViewNrgBean = new InvoiceFormatViewNrgBean();
			load(dataSetRow, invoiceFormatViewNrgBean);
			invoiceFormatViewNrgBeanColl.add(invoiceFormatViewNrgBean);
		}

		return invoiceFormatViewNrgBeanColl;
	}
*/
}