package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.SalesOrderSearchBean;
import com.tcmis.internal.distribution.beans.SalesOrderTotalsBean;
import com.tcmis.internal.distribution.beans.SalesOrderViewBean;

public class SalesOrderSearchProcess extends BaseProcess {

	public SalesOrderSearchProcess(String client) {
		super(client);
	}

	public SalesOrderSearchProcess(String client, String locale) {
		super(client, locale);
	}
	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	
	@SuppressWarnings("unchecked")
	public Collection<SalesOrderViewBean> getSalesOrder(SalesOrderSearchBean inputSearchBean, PersonnelBean personnelBean) 
		throws BaseException {
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SalesOrderViewBean());
		
		String showOnlyLateOrders = "N";
		if("Y".equals(inputSearchBean.getShowOnlyLateOrders()))
			showOnlyLateOrders = "Y";
		
		String showGrossMarginExceptions = "N";
		if("Y".equals(inputSearchBean.getShowGrossMarginExceptions()))
			showGrossMarginExceptions = "Y";
		
		String backOrdersOnly = "N";
		if("Y".equals(inputSearchBean.getBackOrdersOnly()))
			backOrdersOnly = "Y";
	
/*		
		if (("prNumber").equals(inputSearchBean.getSearchField()) && !StringHandler.isBlankString(inputSearchBean.getSearchArgument()))
			extraWhereClause = " where pr_number = " + inputSearchBean.getSearchArgument();
		
		if (("catalogItem").equals(inputSearchBean.getSearchField()) && !StringHandler.isBlankString(inputSearchBean.getSearchArgument()))
			extraWhereClause = " where alternate_name = '" + inputSearchBean.getSearchArgument() + "'";
		
		if (("customerPO").equals(inputSearchBean.getSearchField()) && !StringHandler.isBlankString(inputSearchBean.getSearchArgument()))
			extraWhereClause = " where po_number = '" + inputSearchBean.getSearchArgument() + "'";
		
      if (showGrossMarginExceptions.equalsIgnoreCase("Y")) {
			if (StringHandler.isBlankString(extraWhereClause)) {
				extraWhereClause = " where ( margin < minimum_gross_margin or margin > maximum_gross_margin )";
			}else {
				extraWhereClause = " and ( margin < minimum_gross_margin or margin > maximum_gross_margin )";
			}
		}
*/
		String prNumber = null;
		String catalogItem = null;
		String customerPO = null;
		
		if (("prNumber").equals(inputSearchBean.getSearchField()) && !StringHandler.isBlankString(inputSearchBean.getSearchArgument()))
			prNumber = inputSearchBean.getSearchArgument();
		
		if (("catalogItem").equals(inputSearchBean.getSearchField()) && !StringHandler.isBlankString(inputSearchBean.getSearchArgument()))
			catalogItem = inputSearchBean.getSearchArgument();
		
		if (("customerPO").equals(inputSearchBean.getSearchField()) && !StringHandler.isBlankString(inputSearchBean.getSearchArgument()))
			customerPO = inputSearchBean.getSearchArgument();
		
		String extraWhereClause = "";
		if (showGrossMarginExceptions.equalsIgnoreCase("Y"))
			extraWhereClause = " where ( margin < minimum_gross_margin or margin > maximum_gross_margin )";
		
		  String query="select * from table (Pkg_sales_search.fx_sales_order_line_search(" +
									personnelBean.getPersonnelId() +",'" +
									StringHandler.emptyIfNull(personnelBean.getCompanyId()) +"','" +
									StringHandler.emptyIfNull(inputSearchBean.getOperatingEntityId()) +"','" +
									StringHandler.emptyIfNull(inputSearchBean.getHub()) +"','" +
									StringHandler.emptyIfNull(inputSearchBean.getInventoryGroup()) +"','" +
									StringHandler.emptyIfNull(inputSearchBean.getCustomerId()) +"'," +  
									(null!=inputSearchBean.getOrderFromDate()?"TO_DATE('" + dateFormatter.format( inputSearchBean.getOrderFromDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''") +"," +
									(null!=inputSearchBean.getOrderToDate()?"TO_DATE('" + dateFormatter.format( inputSearchBean.getOrderToDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''")+"," +  
									(null!=inputSearchBean.getPromiseShipFromDate()?"TO_DATE('" + dateFormatter.format( inputSearchBean.getPromiseShipFromDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''")+"," +
									(null!=inputSearchBean.getPromiseShipToDate()?"TO_DATE('" + dateFormatter.format( inputSearchBean.getPromiseShipToDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''")+",'"+
									showOnlyLateOrders+"','"+showGrossMarginExceptions+"','"+backOrdersOnly+"','"+inputSearchBean.getOrderStatusList()+"','"+
									StringHandler.emptyIfNull(prNumber) +"',"+
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(catalogItem)) +","+
									SqlHandler.escSingleQteFuncall(StringHandler.emptyIfNull(customerPO)) +",'" +
									StringHandler.emptyIfNull(inputSearchBean.getPersonnelId()) +"'))" + extraWhereClause;

		return factory.selectQuery(query);
	}

	@SuppressWarnings("static-access")
	public ExcelHandler getExcelReport(SalesOrderSearchBean inputBean,
			Collection<SalesOrderViewBean> salesOrderCollection,
			Locale locale) {
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	  BigDecimal sumQuantityOpened = new BigDecimal("0");
	  BigDecimal sumExtPrice = new BigDecimal("0");
	  BigDecimal openedExtPrice  = new BigDecimal("0");
	  BigDecimal extPrice  = new BigDecimal("0");
	 // SalesOrderTotalsBean salesOrderTotalsBean;
	  Collection currencyColl = new Vector();
	  Hashtable salesOrderTotals = new Hashtable();
	  
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();

		// write column headers
		pw.addRow();		
		pw.addCellKeyBold("label.operatingentity");
		pw.addCell(inputBean.getOperatingEntityId());
		pw.addCellKeyBold("label.customer");
		pw.addCell(inputBean.getCustomerName());
		pw.addRow();
		pw.addCellKeyBold("label.hub");
		if (!StringHandler.isBlankString(inputBean.getHub()) && (!salesOrderCollection.isEmpty())) {
			SalesOrderViewBean [] beans = salesOrderCollection.toArray(new SalesOrderViewBean[0]);
			pw.addCell(beans[0].getHubName());
		} else {
			pw.addCell("");
		}
		pw.addCellKeyBold("label.orderdate");
		pw.addCellKeyBold("label.from");
		pw.setColumnCalendarType(4);
		pw.addCell(inputBean.getOrderFromDate());
		pw.addCellKeyBold("label.to");
		pw.setColumnCalendarType(6);
		pw.addCell(inputBean.getOrderToDate());	
		pw.addRow();
		pw.addCellKeyBold("label.invgrp");
		pw.addCell(inputBean.getInventoryGroup());
		pw.addCellKeyBold("label.promised.ship.date");
		pw.addCellKeyBold("label.from");
		pw.addCell(inputBean.getPromiseShipFromDate());
		pw.addCellKeyBold("label.to");
		pw.addCell(inputBean.getPromiseShipToDate());			
		pw.addRow();	
		pw.addTdEmpty(2);
		pw.addCellKeyBold("label.daysLate");
		pw.addCell(inputBean.getShowOnlyLateOrders());	
		pw.addRow();
		pw.addRow();
		String[] headerkeys = { "label.operatingentity", "label.hub",
				"label.inventorygroup", "label.customer",
				"label.customerid", "label.poline",
				"label.mr","label.submittedby", "label.requestor",
				"label.part", "label.partdesc", "label.quantityordered","picking", 
				"label.shipped","label.inprogress","label.backordered",
				"label.currencyid","label.price","label.extprice","label.qtyOpened","label.openedExtPrice","label.margin",
				"label.needed", "label.promised.ship.date",
				"label.status", "label.orderdate",
				"label.daysLate", "label.externallinenote", "label.internallinenote", 
				"label.orderinternalnote", "label.orderexternal", "label.shiptoaddress", "label.customerpart" };
	
		int[] widths = { 10, 10, 12, 18, 10, 10, 10,12, 15, 10, 20, 12, 10, 10,10, 10, 10,10, 9, 10,8, 10, 10, 10, 10,10,
				6, 32, 20, 20, 20, 120, 25};
		int[] types = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER,pw.TYPE_NUMBER,pw.TYPE_NUMBER,
				pw.TYPE_NUMBER,pw.TYPE_NUMBER, pw.TYPE_NUMBER, 0,pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER,
				pw.TYPE_CALENDAR,
				pw.TYPE_CALENDAR, 0, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0, 0};
		int[] aligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, pw.ALIGN_RIGHT, pw.ALIGN_RIGHT, pw.ALIGN_RIGHT, 
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, aligns);
		
		pw.setColumnDigit(20,1);

		for (SalesOrderViewBean salesOrderBean : salesOrderCollection) {
			
			pw.addRow();
			pw.addCell(salesOrderBean.getOperatingEntityName());
			pw.addCell(salesOrderBean.getHubName());
			pw.addCell(salesOrderBean.getInventoryGroupName());
			pw.addCell(salesOrderBean.getCustomerName());
			pw.addCell(salesOrderBean.getCustomerId());
			if(salesOrderBean.getCustomerPoLine() == null)
				pw.addCell(salesOrderBean.getPoNumber());
			else
				pw.addCell(salesOrderBean.getPoNumber()+"-"+salesOrderBean.getCustomerPoLine());
			pw.addCell(salesOrderBean.getPrNumber()+"-"+salesOrderBean.getLineItem());
			pw.addCell(salesOrderBean.getSubmitterName());
			pw.addCell(salesOrderBean.getRequestorName());
			pw.addCell(salesOrderBean.getFacPartNo());
			pw.addCell(salesOrderBean.getPartDescription());
			pw.addCell(salesOrderBean.getQuantity());
			pw.addCell(salesOrderBean.getQtyPicked());
			pw.addCell(salesOrderBean.getQtyShipped());			
			pw.addCell(salesOrderBean.getQtyAllocated());
            pw.addCell(salesOrderBean.getQtyInPurchasing());
            if(salesOrderBean.getCatalogPrice() == null) {
				pw.addCell("");
				pw.addCell("");
				pw.addCell("");
				
			}
			else {
				pw.addCell(salesOrderBean.getCurrencyId());
				pw.addCell(salesOrderBean.getCatalogPrice().setScale(4,BigDecimal.ROUND_HALF_UP));
				if(salesOrderBean.getQuantity() != null) {
					extPrice = salesOrderBean.getCatalogPrice().multiply(salesOrderBean.getQuantity());
					pw.addCell(extPrice.setScale(4,BigDecimal.ROUND_HALF_UP));
				}
				else
					pw.addCell("");
			}
            BigDecimal qtyOpened;
            if(salesOrderBean.getQuantity() != null && salesOrderBean.getQtyShipped() != null)
            {
            	qtyOpened = salesOrderBean.getQuantity().subtract(salesOrderBean.getQtyShipped());
            }
            else
            {
            	qtyOpened = salesOrderBean.getQuantity();
            }
            pw.addCell(qtyOpened);
            if(salesOrderBean.getCatalogPrice() == null || qtyOpened == null) {
				pw.addCell("");
            }
            else
            {
              openedExtPrice = qtyOpened.multiply(salesOrderBean.getCatalogPrice());
              pw.addCell(openedExtPrice.setScale(4,BigDecimal.ROUND_HALF_UP));
            }          	
			if(salesOrderBean.getMargin() == null)
				pw.addCell("");
			else
				pw.addCell(salesOrderBean.getMargin().setScale(1,BigDecimal.ROUND_HALF_UP)+"%");
			    pw.addCell(salesOrderBean.getNeedDate());
			   pw.addCell(salesOrderBean.getPromiseDate());
			   pw.addCell(salesOrderBean.getOrderStatus());
			   pw.addCell(salesOrderBean.getSubmittedDate());
			if(salesOrderBean.getDaysLate() != null && salesOrderBean.getDaysLate().compareTo(BigDecimal.ZERO) > 0)
				pw.addCell(salesOrderBean.getDaysLate());
			else
				pw.addCell("");
			pw.addCell(salesOrderBean.getComments());
			pw.addCell(salesOrderBean.getInternalNote());
			pw.addCell(salesOrderBean.getOrderInternalNote());
			pw.addCell(salesOrderBean.getSpecialInstructions());
			pw.addCell(salesOrderBean.getAddressLine1Display()+" "+salesOrderBean.getAddressLine2Display()+" "+salesOrderBean.getAddressLine3Display()+" "+salesOrderBean.getAddressLine4Display()+" "+salesOrderBean.getAddressLine5Display());
			pw.addCell(salesOrderBean.getDistCustomerPartList());
			
			 String currencyId = salesOrderBean.getCurrencyId() == null ? "" : salesOrderBean.getCurrencyId();

			  if (!currencyColl.contains(currencyId)) {
				  SalesOrderTotalsBean salesOrderTotalsBean2 = new SalesOrderTotalsBean();
				  salesOrderTotalsBean2.setCurrencyId(currencyId);
				  			  

				salesOrderTotals.put(currencyId,salesOrderTotalsBean2);
				currencyColl.add(currencyId);
			  }

			  SalesOrderTotalsBean salesOrderTotalsBean = (SalesOrderTotalsBean)salesOrderTotals.get(currencyId);


			  salesOrderTotalsBean.setExtPrice(extPrice);
			  salesOrderTotalsBean.setOpenedExtPrice(openedExtPrice);
			  			 
		}
		
		try {
			 Enumeration E;
			 for (E = salesOrderTotals.keys(); E.hasMoreElements(); ) {
			   String key = (String) E.nextElement();
			   SalesOrderTotalsBean salesOrderTotalsBean = (SalesOrderTotalsBean)salesOrderTotals.get(key);

		       pw.addRow();
		       pw.addTdEmpty(15);
		       pw.addTh("label.total");
		       pw.addCellBold(salesOrderTotalsBean.getCurrencyId() );
		       pw.addCell("");
		       pw.addCellBold(salesOrderTotalsBean.getExtPrice().setScale(4,BigDecimal.ROUND_HALF_UP));
		       pw.addCell("");
		       pw.addCellBold(salesOrderTotalsBean.getOpenedExtPrice().setScale(4,BigDecimal.ROUND_HALF_UP));
			 }
		}
		catch (Exception e) {

		  }
				
		return pw;
	}
	
	
	public Collection update(Collection<SalesOrderViewBean> salesOrderViewBeanCollection) throws
	BaseException, Exception {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		String errorCode = null;
		//Collection resultCollection = null;
		Vector errorMessages = new Vector();

		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		if((salesOrderViewBeanCollection!=null) && (!salesOrderViewBeanCollection.isEmpty()))
		{
			for(SalesOrderViewBean salesOrderBean : salesOrderViewBeanCollection) {
				try {
					if((!StringHandler.isBlankString(salesOrderBean.getOk()) && (null!=salesOrderBean.getPrNumber())) &&
							(!StringHandler.isBlankString(salesOrderBean.getLineItem()) ) )   {
						inArgs = new Vector(2);
						inArgs.add(salesOrderBean.getPrNumber());
						inArgs.add(salesOrderBean.getLineItem());						
						outArgs = new Vector(1);
						outArgs.add(new Integer(java.sql.Types.VARCHAR));
						if(log.isDebugEnabled()) {
							log.debug("Sales Order Search Proc:" + inArgs);
						}			   
						Vector error = null; 
						//(Vector) factory.doProcedure("PROC_NAME", inArgs, outArgs);
						if(error.size()>0 && error.get(0) != null)
						{
							errorCode = (String) error.get(0);
							log.info("Error in Procedure For Sales Order Search Section: "+salesOrderBean.getSalesOrder()+" "+" Error Code "+errorCode+" ");
							errorMessages.add(errorCode);

						}			     

					}			
				} catch (Exception e) {
					errorMsg = "Error updating Sales Order: "+ salesOrderBean.getSalesOrder()+ "";
					errorMessages.add(errorMsg);
				}
			}

		} 

		return errorMessages;
	}
	
}
