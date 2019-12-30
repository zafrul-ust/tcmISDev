package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerOrderTrackingInputBean;
import com.tcmis.internal.distribution.beans.SalesOrderViewBean;

public class CustomerOrderTrackingProcess extends GenericProcess {

	public CustomerOrderTrackingProcess(String client) {
		super(client);
	}

	public CustomerOrderTrackingProcess(String client, String locale) {
		super(client, locale);
	}

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	@SuppressWarnings("unchecked")
	public Collection<SalesOrderViewBean> getSalesOrder(CustomerOrderTrackingInputBean input, PersonnelBean user)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SalesOrderViewBean());

		String where ="";
		
		where = user.getPersonnelId() +",'"+
		input.getCustomerIdList()+"',";	
		
		String query="select * from table (pkg_open_customer_order_track.FX_CUSTOMER_MR_LINE_SEARCH(" +
		where  +		
        (null!=input.getOrderFromDate()?"TO_DATE('" + dateFormatter.format( input.getOrderFromDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''") +"," +
		(null!=input.getOrderToDate()?"TO_DATE('" + dateFormatter.format( input.getOrderToDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''")+"," +
		(null!=input.getPromiseShipFromDate()?"TO_DATE('" + dateFormatter.format( input.getPromiseShipFromDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''")+"," +
		(null!=input.getPromiseShipToDate()?"TO_DATE('" + dateFormatter.format( input.getPromiseShipToDate()) + "', 'MM/DD/RRRR hh24:mi:ss')":"''")+
		",'"+input.getOrderStatusList()+"','"+
		input.getSearchField()+"','"+
		input.getSearchMode()+"',"+
		SqlHandler.escSingleQteFuncall(input.getSearchArgument())+"))";

		return factory.selectQuery(query);
	}

	@SuppressWarnings("static-access")
	public ExcelHandler getExcelReport( CustomerOrderTrackingInputBean inputBean,
			Collection<SalesOrderViewBean> salesOrderCollection,
			Locale locale) {
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();

		// write column headers
		pw.addRow();
		
		String[] headerkeys = {
				"label.operatingentity", "label.podaskline", "label.mrline", "label.requestor", 
				"label.catalogitem", "label.description", "label.specifications", "label.quantityordered","label.shipped",
				"label.inprogress","label.backordered", "label.currencyid","label.price","label.extprice",
				"label.needed", "label.promised.ship.date", "label.status", "label.orderdate","label.comments" };
		int[] widths = {
				10,10, 10, 12, 
				10, 30, 15, 10, 10,
				10, 10, 10, 10, 9,
				10, 10, 10, 10, 30};
		int[] types = {
				0, 0, 0, 0, 
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				pw.TYPE_CALENDAR, pw.TYPE_CALENDAR, 0,pw.TYPE_CALENDAR, 0};
		int[] aligns = {
				0, 0, 0, 0, 
				0, 0, 0, 0, 0,
				0, 0, 0, pw.ALIGN_RIGHT, pw.ALIGN_RIGHT,
				0, 0, 0, 0, 0};

		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		pw.setColumnDigit(19,4);

		for (SalesOrderViewBean salesOrderBean : salesOrderCollection) {

			pw.addRow();
			pw.addCell(salesOrderBean.getOperatingEntityName());
			//			pw.addCell(salesOrderBean.getInventoryGroupName());
			if(salesOrderBean.getCustomerPoLine() == null)
				pw.addCell(salesOrderBean.getPoNumber());
			else
				pw.addCell(salesOrderBean.getPoNumber()+"-"+salesOrderBean.getCustomerPoLine());
			pw.addCell(salesOrderBean.getPrNumber()+"-"+salesOrderBean.getLineItem());
			pw.addCell(salesOrderBean.getRequestorName());
			pw.addCell(salesOrderBean.getFacPartNo());
			pw.addCell(salesOrderBean.getPartDescription());
			pw.addCell(salesOrderBean.getSpecification());
			pw.addCell(salesOrderBean.getQuantity());
			pw.addCell(salesOrderBean.getQtyShipped());
			pw.addCell(salesOrderBean.getQtyAllocated());
			pw.addCell(salesOrderBean.getQtyInPurchasing());
			if(salesOrderBean.getCatalogPrice() == null) {
				pw.addCell("");
				pw.addCell("");
			}
			else {
				pw.addCell(salesOrderBean.getCurrencyId());
				pw.addCell(salesOrderBean.getCatalogPrice().setScale(4,BigDecimal.ROUND_HALF_UP).toString());
			}
			if(salesOrderBean.getCatalogPrice() == null || salesOrderBean.getQuantity() == null)
				pw.addCell("");
			else {
				BigDecimal extPrice = salesOrderBean.getQuantity().multiply(salesOrderBean.getCatalogPrice());
				pw.addCell(extPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			}
			pw.addCell(salesOrderBean.getNeedDate());
			pw.addCell(salesOrderBean.getPromiseDate());
			pw.addCell(salesOrderBean.getRequestLineStatus());
			pw.addCell(salesOrderBean.getSubmittedDate());
			pw.addCell(salesOrderBean.getComments());
		}
		return pw;
	}
	

}
