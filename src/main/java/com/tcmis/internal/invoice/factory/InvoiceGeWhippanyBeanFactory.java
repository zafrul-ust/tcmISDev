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
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.invoice.beans.InvoiceFormatVerasunViewBean;
import com.tcmis.internal.invoice.beans.InvoiceGeWhippanyBean;

public class InvoiceGeWhippanyBeanFactory extends BaseBeanFactory
{

	Log log = LogFactory.getLog(this.getClass());

	// column names
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
	public String ATTRIBUTE_LINE_AMOUNT = "LINE_AMOUNT";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";

	// table name
	public String TABLE = "INVOICE_PRINT_GEWHIP_VIEW";

	// constructor
	public InvoiceGeWhippanyBeanFactory(DbManager dbManager)
	{
		super(dbManager);
	}

	// get column names
	public String getColumnName(String attributeName)
	{

		if (attributeName.equals("invoice"))
		{
			return ATTRIBUTE_INVOICE;
		}
		else
			if (attributeName.equals("poNumber"))
			{
				return ATTRIBUTE_PO_NUMBER;
			}
			else
				if (attributeName.equals("invoiceDate"))
				{
					return ATTRIBUTE_INVOICE_DATE;
				}
				else
					if (attributeName.equals("invoiceAmount"))
					{
						return ATTRIBUTE_INVOICE_AMOUNT;
					}
					else
						if (attributeName.equals("invoicePeriod"))
						{
							return ATTRIBUTE_INVOICE_PERIOD;
						}
						else
							if (attributeName.equals("invoiceGroup"))
							{
								return ATTRIBUTE_INVOICE_GROUP;
							}
							else
								if (attributeName.equals("lineAmount"))
								{
									return ATTRIBUTE_LINE_AMOUNT;
								}
								else
									if (attributeName.equals("accountNumber"))
									{
										return ATTRIBUTE_ACCOUNT_NUMBER;
									}
									else
										if (attributeName.equals("lineItem"))
										{
											return ATTRIBUTE_LINE_ITEM;
										}
										else
										{
											return super
													.getColumnName(attributeName);
										}
	}

	// get column types
	public int getType(String attributeName)
	{
		return super.getType(attributeName, InvoiceGeWhippanyBean.class);
	}

	// select
	@SuppressWarnings("rawtypes")
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
			throws BaseException
	{
		Connection connection = null;
		Collection c = null;
		try
		{
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection select(SearchCriteria criteria,
			SortCriteria sortCriteria, Connection conn) throws BaseException
	{
		Collection invoicesCollection = new Vector();
		String query = "select unique invoice from " + TABLE + " "
				+ getWhereClause(criteria) + getOrderByClause(sortCriteria);
		DataSet baseDataSet = new SqlManager().select(conn, query);
		Iterator baseDataIter = baseDataSet.iterator();
		while (baseDataIter.hasNext())
		{
			DataSetRow dataRow = (DataSetRow) baseDataIter.next();
			InvoiceGeWhippanyBean invoiceBean = new InvoiceGeWhippanyBean();
			load(dataRow, invoiceBean);
			// Now, query by invoice number
			Collection invoiceGeWhippanyBeanCollection = new Vector();
			query = "select * from "
					+ TABLE
					+ " "
					+ getWhereClause(new SearchCriteria("invoice",
							SearchCriterion.EQUALS, invoiceBean.getInvoice()
									.toString()))
					+ getOrderByClause(sortCriteria);
			if (log.isDebugEnabled())
			{
				log.debug("QUERY:" + query);
			}
			DataSet dataSet = new SqlManager().select(conn, query);
			Iterator dataIter = dataSet.iterator();
			while (dataIter.hasNext())
			{
				DataSetRow dataSetRow = (DataSetRow) dataIter.next();
				InvoiceGeWhippanyBean invoiceGEWhippanyViewBean = new InvoiceGeWhippanyBean();
				load(dataSetRow, invoiceGEWhippanyViewBean);
				invoiceGeWhippanyBeanCollection
						.add(invoiceGEWhippanyViewBean);
			}
			invoicesCollection.add(invoiceGeWhippanyBeanCollection);
		}
		return invoicesCollection;
	}
}
