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
import com.tcmis.internal.invoice.beans.InvoiceViewZfBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewSaicTfBeanFactory <br>
 * 
 * @version: 1.0, Sep 1st, 2010 <br>
 *****************************************************************************/

public class InvoiceViewZfBeanFactory extends BaseBeanFactory
{

	Log log = LogFactory.getLog(this.getClass());

	// column names
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_PERIOD = "PERIOD";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_AMOUNT = "AMOUNT";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_PLANT_CODE = "PLANT_CODE";

	// table name
	public String TABLE = "INVOICE_PRINT_MARYSVILLE_VIEW";

	// constructor
	public InvoiceViewZfBeanFactory(DbManager dbManager)
	{
		super(dbManager);
	}

	// get column names
	@Override
	public String getColumnName(String attributeName)
	{
		if (attributeName.equals("invoice"))
		{
			return ATTRIBUTE_INVOICE;
		}
		else
			if (attributeName.equals("invoiceDate"))
			{
				return ATTRIBUTE_INVOICE_DATE;
			}
			else
				if (attributeName.equals("invoicePeriod"))
				{
					return ATTRIBUTE_INVOICE_PERIOD;
				}
				else
					if (attributeName.equals("invoiceAmount"))
					{
						return ATTRIBUTE_INVOICE_AMOUNT;
					}
					else
						if (attributeName.equals("poNumber"))
						{
							return ATTRIBUTE_PO_NUMBER;
						}
						else
							if (attributeName.equals("itemType"))
							{
								return ATTRIBUTE_ITEM_TYPE;
							}
							else
								if (attributeName.equals("period"))
								{
									return ATTRIBUTE_PERIOD;
								}
								else
									if (attributeName.equals("partDescription"))
									{
										return ATTRIBUTE_PART_DESCRIPTION;
									}
									else
										if (attributeName.equals("quantity"))
										{
											return ATTRIBUTE_QUANTITY;
										}
										else
											if (attributeName
													.equals("unitPrice"))
											{
												return ATTRIBUTE_UNIT_PRICE;
											}
											else
												if (attributeName
														.equals("amount"))
												{
													return ATTRIBUTE_AMOUNT;
												}
												else
													if (attributeName
															.equals("currencyId"))
													{
														return ATTRIBUTE_CURRENCY_ID;
													}
													else if (attributeName.equals("plantCode")){
														return ATTRIBUTE_PLANT_CODE;
													}
													else
													{
														return super
																.getColumnName(attributeName);
													}
	}

	// get column types
	@Override
	public int getType(String attributeName)
	{
		return super.getType(attributeName, InvoiceViewZfBean.class);
	}

	// select
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
		catch (Exception e)
		{
			log.fatal("Error querying DB ", e);
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
		Collection invoicesZFCollection = new Vector();
		String query = "select unique invoice from " + TABLE + " "
				+ getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled())
		{
			log.debug("QUERY:" + query);
		}
		DataSet baseDataSet = new SqlManager().select(conn, query);
		Iterator baseDataIter = baseDataSet.iterator();
		while (baseDataIter.hasNext())
		{
			DataSetRow dataRow = (DataSetRow) baseDataIter.next();
			InvoiceViewZfBean invoiceBean = new InvoiceViewZfBean();
			load(dataRow, invoiceBean);
			// now add child records
			Collection invoiceZFBeanCollection = new Vector();
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
				InvoiceViewZfBean invoiceViewZfBean = new InvoiceViewZfBean();
				load(dataSetRow, invoiceViewZfBean);
				invoiceZFBeanCollection.add(invoiceViewZfBean);
			}
			invoicesZFCollection.add(invoiceZFBeanCollection);
		}
		return invoicesZFCollection;
	}

}
