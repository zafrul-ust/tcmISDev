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
import com.tcmis.internal.invoice.beans.DrsPalmbayInvoiceDtlViewBean;
import com.tcmis.internal.invoice.beans.InvoiceViewZfBean;

/******************************************************************************
 * CLASSNAME: DrsPalmbayInvoiceDtlViewBeanFactory <br>
 * 
 * @version: 1.0, May 8, 2010 <br>
 *****************************************************************************/

public class DrsPalmbayInvoiceDtlViewBeanFactory extends BaseBeanFactory
{

	public String ATTRIBUTE_CYLINDER_RENTAL_COST = "CYLINDER_RENTAL_COST";

	public String ATTRIBUTE_GAS_COST = "GAS_COST";
	// column names
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_MATERIAL_COST = "MATERIAL_COST";
	public String ATTRIBUTE_MATERIAL_REBATE = "MATERIAL_REBATE";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
	public String ATTRIBUTE_TOTAL_DUE = "TOTAL_DUE";
	public String ATTRIBUTE_WASTE_COST = "WASTE_COST";
	public String ATTRIBUTE_WASTE_REBATE = "WASTE_REBATE";
	Log log = LogFactory.getLog(this.getClass());

	// JIRA - TCMISDEV-157
	// boolean to indicate if this is the invoice for fees
	private boolean printFeeInvoice = false;

	// table name
	public String TABLE = "DRS_PALMBAY_INVOICE_DTL_VIEW";

	// constructor
	public DrsPalmbayInvoiceDtlViewBeanFactory(DbManager dbManager,
			boolean printFees)
	{
		super(dbManager);
		this.setPrintFeeInvoice(printFees);
	}

	public int delete(SearchCriteria criteria) throws BaseException
	{
		Connection connection = null;
		int result = 0;
		try
		{
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn)
			throws BaseException
	{
		String sqlQuery = " delete from " + TABLE + " "
				+ getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	// get column names
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
					if (attributeName.equals("poNumber"))
					{
						return ATTRIBUTE_PO_NUMBER;
					}
					else
						if (attributeName.equals("materialCost"))
						{
							return ATTRIBUTE_MATERIAL_COST;
						}
						else
							if (attributeName.equals("materialRebate"))
							{
								return ATTRIBUTE_MATERIAL_REBATE;
							}
							else
								if (attributeName.equals("wasteCost"))
								{
									return ATTRIBUTE_WASTE_COST;
								}
								else
									if (attributeName.equals("wasteRebate"))
									{
										return ATTRIBUTE_WASTE_REBATE;
									}
									else
										if (attributeName.equals("gasCost"))
										{
											return ATTRIBUTE_GAS_COST;
										}
										else
											if (attributeName
													.equals("cylinderRentalCost"))
											{
												return ATTRIBUTE_CYLINDER_RENTAL_COST;
											}
											else
												if (attributeName
														.equals("serviceFee"))
												{
													return ATTRIBUTE_SERVICE_FEE;
												}
												else
													if (attributeName
															.equals("totalDue"))
													{
														return ATTRIBUTE_TOTAL_DUE;
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
		return super.getType(attributeName, DrsPalmbayInvoiceDtlViewBean.class);
	}

	/**
	 * @return the printFeeInvoice
	 */
	public boolean isPrintFeeInvoice()
	{
		return printFeeInvoice;
	}

	// select
	@SuppressWarnings("rawtypes")
	public Collection select(SearchCriteria criteria) throws BaseException
	{
		return select(criteria, null);
	}

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
		Collection drsPalmbayInvoicesCollection = new Vector();
		StringBuilder sb = new StringBuilder().append("select unique invoice from ");
		sb.append(TABLE).append(" ").append(getWhereClause(criteria));
		if (this.isPrintFeeInvoice())
		{
			sb.append("AND SERVICE_FEE > 0 ");
		}
		else
		{
			sb.append("AND SERVICE_FEE = 0 ");
		}
		sb.append(getOrderByClause(sortCriteria));
		String query = sb.toString();
		DataSet invoicesDataSet = new SqlManager().select(conn, query);
		Iterator invoicesIter = invoicesDataSet.iterator();
		while (invoicesIter.hasNext())
		{
			DataSetRow invoiceRow = (DataSetRow) invoicesIter.next();
			DrsPalmbayInvoiceDtlViewBean invoiceBean = new DrsPalmbayInvoiceDtlViewBean();
			load(invoiceRow, invoiceBean);
			// get details for invoice
			Collection drsPalmBayinvoiceDetailsCollection = new Vector();
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
				DrsPalmbayInvoiceDtlViewBean invoiceDetailsBean = new DrsPalmbayInvoiceDtlViewBean();
				load(dataSetRow, invoiceDetailsBean);
				drsPalmBayinvoiceDetailsCollection.add(invoiceDetailsBean);
			}
			drsPalmbayInvoicesCollection.add(drsPalmBayinvoiceDetailsCollection);
		}
		return drsPalmbayInvoicesCollection;
	}

	/**
	 * @param printFeeInvoice
	 *            the printFeeInvoice to set
	 */
	public void setPrintFeeInvoice(boolean printFeeInvoice)
	{
		this.printFeeInvoice = printFeeInvoice;
	}
}