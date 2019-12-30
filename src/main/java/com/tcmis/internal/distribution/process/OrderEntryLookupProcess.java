package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.OrderEntryLookupSearchBean;
import com.tcmis.internal.distribution.beans.SalesOrderViewBean;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;

public class OrderEntryLookupProcess extends GenericProcess {

	public OrderEntryLookupProcess(String client) {
		super(client);
	}

	public OrderEntryLookupProcess(String client, String locale) {
		super(client, locale);
	}

	@SuppressWarnings("unchecked")
	public Collection<SalesQuoteViewBean> getSalesOrder(OrderEntryLookupSearchBean inputSearchBean, PersonnelBean personnelBean)
	throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new SalesQuoteViewBean());
		//SearchCriteria criteria = new SearchCriteria();
		StringBuffer orderStatus = new StringBuffer();
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		Collection<SalesQuoteViewBean> c = null;
		String singleStatus = null;
		String mode = "";
		String field = "";
		try {
			/*		    a_personnel_id  global.personnel.personnel_id%type,
		    a_ops_entity_id operating_entity.ops_entity_id%type default null,
		    a_hub hub.branch_plant%type default null,
		    a_inventory_group tcm_ops.inventory_group_definition.inventory_group%type default null,
		    a_order_date1    date,
		    a_order_date2   date,
		    a_entered_by    customer.purchase_request.submitted_by%type default null,
		    a_customer_id  bill_to_customer.customer_id%type default null,
		    a_order_status customer.purchase_request.pr_status%type default null,
		    a_order_type_MR   varchar2 default 'N',
		    a_order_type_QT   varchar2 default 'N',
		    a_order_type_SP   varchar2 default 'N',
		    a_search_column   varchar2,
		    a_search_filter  varchar2,
		    a_search_text   varchar2,
		    a_debug         varchar2 default 'N',
		    a_mr_query out   varchar2,
		    a_sq_query out  varchar2,
		    a_sp_query out  varchar2,
		    a_error_status out varchar2
			 */



			if ((null!=inputSearchBean.getOrderStatus()) && ( inputSearchBean.getOrderStatus().length>0)) {

				if (inputSearchBean.getOrderStatus().length == 1) {
					if (!inputSearchBean.getOrderStatus()[0].toString().equalsIgnoreCase("All"))
						singleStatus =	inputSearchBean.getOrderStatus()[0].toString();
				}
				else
				{
					for (int i = 0; i < inputSearchBean.getOrderStatus().length; i++) {
						if (inputSearchBean.getOrderStatus()[i].length() > 0) {

							if (!inputSearchBean.getOrderStatus()[i].toString().equalsIgnoreCase("All"))
							{
								orderStatus.append("'"+ inputSearchBean.getOrderStatus()[i].toString() + "'");
							}
							if ((i + 1) < inputSearchBean.getOrderStatus().length)
								orderStatus.append(",");
						}

					}
				}
			}


			if(!StringHandler.isBlankString(inputSearchBean.getSearchArgument())) {
				mode = inputSearchBean.getSearchMode();
				field = inputSearchBean.getSearchField();
			}


			Collection inArgs =
				buildProcedureInput(

						personnelId,
						inputSearchBean.getOperatingEntityId(),
						inputSearchBean.getHub(),
						inputSearchBean.getInventoryGroup(),
						inputSearchBean.getOrderFromDate(),
						inputSearchBean.getOrderToDate(),
						inputSearchBean.getPersonnelId(),
						inputSearchBean.getCustomerId(),
						(!StringHandler.isBlankString(singleStatus)?singleStatus:orderStatus.toString()),
						inputSearchBean.getMr(),
						inputSearchBean.getQuote(),
						inputSearchBean.getScratchPad(),
						field,
						mode,
						inputSearchBean.getSearchArgument(),
						"N",
						inputSearchBean.getBlanket()
				);

			Vector outArgs = new Vector();
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );
			outArgs.add( new Integer(java.sql.Types.VARCHAR) );

			if (log.isDebugEnabled()) {
				log.debug("calling TCM_OPS.P_SALES_LOOKUP inArgs:"+inArgs);
			}

			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Vector result = null;
			result = (Vector) procFactory.doProcedure( "TCM_OPS.P_SALES_LOOKUP", inArgs, outArgs);



			//Vector error = new Vector();
			String query = null;
			StringBuffer queryBuilder = new StringBuffer("");
			if( (null!= result) && (result.size() !=0) )
			{
				if(null!=result.get(0))
					queryBuilder.append( ((Vector<String>)result).get(0));
				if(null!=result.get(1))
				{
					if(null!=result.get(0))
						queryBuilder.append(" UNION ALL ");

					queryBuilder.append( ((Vector<String>)result).get(1));
				}
				if(null!=result.get(2))
				{
					if(null!=result.get(0) || null!=result.get(1))
						queryBuilder.append(" UNION ALL ");

					queryBuilder.append( ((Vector<String>)result).get(2));
				}
				query = queryBuilder.toString();
			}
			else
			{
				query= null;
			}

			if((null!=query) && (query.length()>0))
			{
				c  = factory.selectQuery(query);
			}

			return c ;
		}  finally {

			dbManager = null;
			factory = null;
		}
	}
	
	 public ExcelHandler getExcelReport(Collection<SalesQuoteViewBean> data, Locale locale) throws
     NoDataException, BaseException, Exception {
   
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable();
//write column headers
	pw.addRow();
/*Pass the header keys for the Excel.*/
	String[] headerkeys = {
	  "label.ourreferrence","label.date","label.type","label.customer",
	  "label.customerreference","label.shipto","label.enteredby","label.status",
	  "label.contact","label.numberoflines","label.operatingentity","label.hub","label.inventorygroup"};
	/*This array defines the type of the excel column.
	0 means default depending on the data type. 
	pw.TYPE_PARAGRAPH defaults to 40 characters.
	pw.TYPE_CALENDAR set up the date with no time.
	pw.TYPE_DATE set up the date with time.*/
	int[] types = {
	  pw.TYPE_NUMBER, pw.TYPE_CALENDAR, 0, 0,
	  0, 0, 0, 0,
	  0, pw.TYPE_NUMBER,0, 0, 0};
	
	/*This array defines the default width of the column when the Excel file opens.
	0 means the width will be default depending on the data type.*/
	int[] widths = {
	  10,8,5,12,
	  10,12,12,8,
	  10,5,12,10,12};
	/*This array defines the horizontal alignment of the data in a cell.
	0 means excel defaults the horizontal alignemnt by the data type.*/
	int[] horizAligns = {
	  0,0,0,0,
	  0,0,0,0,
	  0,0,0,0,0};
	  
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
	// format the numbers to the special columns
	pw.setColumnDigit(6, 2);
	pw.setColumnDigit(7, 2);

	for (SalesQuoteViewBean member : data) {
	  
     pw.addRow();
         
     pw.addCell(member.getPrNumber());
     pw.addCell(member.getSubmittedDate());
     pw.addCell(member.getOrderType());
     pw.addCell(member.getCustomerName());
     pw.addCell(member.getPoNumber());
     pw.addCell(member.getShipToAddressLine1()+" "+
				member.getShipToAddressLine2()+" "+
				member.getShipToAddressLine3()+" "+
				member.getShipToAddressLine4()+" "+
				member.getShipToAddressLine5());
     pw.addCell(member.getSubmittedByName());
     pw.addCell(member.getOrderStatus());
     pw.addCell(member.getRequestorName());
     pw.addCell(member.getNumberOfLines());
     pw.addCell(member.getOperatingEntityName());
     pw.addCell(member.getHubName());
     pw.addCell(member.getInventoryGroupName());
     
    
   }
   return pw;
 }

}
