package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.accountspayable.beans.VoucherReportViewBean;
import com.tcmis.internal.distribution.beans.CustomerContactViewBean;
import com.tcmis.internal.distribution.beans.InvoiceSearchBean;
import com.tcmis.internal.distribution.beans.POHistoryViewBean;
import com.tcmis.internal.distribution.beans.QuickQuoteInputBean;
import com.tcmis.internal.distribution.beans.QuotationHistoryViewBean;
import com.tcmis.internal.distribution.beans.SalesSearchBean;
import com.tcmis.internal.hub.beans.LogisticsViewBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceBreakObjBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;
import com.tcmis.internal.supply.beans.PoSearchResultBean;
import com.tcmis.internal.supply.beans.SupplierContactBean;

   /******************************************************************************
   * Process for Quick Item View and Quick Customer View pages
   * @version 1.0
   *****************************************************************************/
  public class QuickQuoteProcess
   extends GenericProcess {
   Log log = LogFactory.getLog(this.getClass());
   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

   public QuickQuoteProcess(String client, Locale locale) {
		super(client, locale);
   }

   public QuickQuoteProcess(String client) {
		super(client);
   }
	
   public Collection getMRColl(QuickQuoteInputBean inputBean, PersonnelBean personnelBean) throws
	BaseException {
		
	    StringBuilder query = new StringBuilder("select igd.inventory_group_name, rli.pr_number, rli.line_item,rli.quantity, rli.release_date,");
		query.append(" rli.po_number customer_po_number, rli.currency_id, rli.catalog_price unit_price, "); 
		query.append("(SELECT NVL (quantity_delivered, 0) FROM  issue_key i1 WHERE rli.pr_number = i1.pr_number(+) AND rli.line_item = i1.line_item(+) AND rli.company_id = i1.company_id(+)) quantity_shipped, ");
		query.append("rli.sales_order ");

		query.append("from vv_ops_region r, inventory_group_definition igd, customer.bill_to_customer btc, catalog_item ci, catalog_item_spec cis, purchase_request pr, request_line_item rli ");
		query.append("where pr.pr_status in ('posubmit', 'compchk2') and r.ops_company_id (+) = igd.ops_company_id and r.ops_region (+) = igd.ops_region and igd.inventory_group = rli.inventory_group and ");
		query.append("exists (select personnel_id from customer.user_group_member_ops_entity where user_group_id = 'ViewSalesOrders' and personnel_id = ").append(personnelBean.getPersonnelId()).append(" and ops_entity_id =  pr.ops_entity_id) and ");
		query.append("nvl(rli.cancel_status, 'OK') <> 'canceled' and pr.customer_id = btc.customer_id (+) and ci.catalog_company_id = cis.catalog_company_id and ci.catalog_id = cis.catalog_id and ci.item_id = cis.item_id and ");
		query.append("cis.catalog_company_id = rli.catalog_company_id and cis.catalog_id = rli.catalog_id and cis.cat_part_no = rli.fac_part_no and pr.company_id = rli.company_id and pr.pr_number = rli.pr_number and ");
		query.append("pr.customer_id = ").append(inputBean.getCustomerId());
		query.append(" and rli.inventory_group in (select inventory_group from table (pkg_inventory_group.fx_personnel_inventory_group('").append(personnelBean.getCompanyId()).append("', ").append(personnelBean.getPersonnelId()).append(", null,null))) and ");
		query.append("rli.catalog_company_id = 'HAAS' and rli.catalog_id = 'Global' "); 
		query.append(" and rli.release_date < sysdate and rli.release_date >= sysdate - ").append(inputBean.getDays());  
		query.append(" order by rli.release_date desc");
		
		Collection results = factory.setBean(new SalesSearchBean()).selectQuery(query.toString());
		
		return results;
   }
   
   public Collection getQuoteColl(QuickQuoteInputBean inputBean, PersonnelBean personnelBean) throws
	BaseException {
	    StringBuilder query = new StringBuilder("select igd.inventory_group_name, sqln.pr_number, sqln.line_item, sq.submitted_date, sqln.quantity, ");
		query.append("sqln.cat_part_no, sqln.currency_id, sqln.customer_part_no, sqln.catalog_price unit_price,"); 
		query.append("pkg_sales_search.fx_orig_sales_quote_count(sq.pr_number) orig_sales_quote_count ");

		query.append("from vv_ops_region r, inventory_group_definition igd, customer.bill_to_customer btc, catalog_item ci, catalog_item_spec cis, sales_quote sq,sales_quote_line sqln ");
		query.append("where sq.status = 'Confirmed' and r.ops_company_id (+) = igd.ops_company_id and r.ops_region (+) = igd.ops_region and igd.inventory_group = sqln.inventory_group and ");
		query.append("exists (select personnel_id from customer.user_group_member_ops_entity where user_group_id = 'ViewQuotes' and personnel_id = ").append(personnelBean.getPersonnelId()).append(" and ops_entity_id = sq.ops_entity_id) and ");
		
		query.append("sq.status <> 'ScratchPad' and sq.customer_id = btc.customer_id (+) and ci.catalog_company_id = cis.catalog_company_id and ci.catalog_id = cis.catalog_id and ");
		query.append("ci.item_id = cis.item_id and cis.catalog_company_id = sqln.catalog_company_id and cis.catalog_id = sqln.catalog_id and cis.cat_part_no = sqln.cat_part_no and sq.pr_number = sqln.pr_number and ");
		
		query.append("sq.customer_id = ").append(inputBean.getCustomerId());
		query.append(" and sq.inventory_group in (select inventory_group from table (pkg_inventory_group.fx_personnel_inventory_group('").append(personnelBean.getCompanyId()).append("', ").append(personnelBean.getPersonnelId()).append(", null,null))) and ");
		query.append("sqln.catalog_company_id = 'HAAS' and sqln.catalog_id = 'Global' "); 
		query.append("and sq.submitted_date < sysdate and sq.submitted_date >= sysdate - ").append(inputBean.getDays());  
		query.append(" order by sq.submitted_date desc");

		Collection results = factory.setBean(new QuotationHistoryViewBean()).selectQuery(query.toString());
		
		return results;
   }
   
   public Collection<LogisticsViewBean> getInventorySearchResult(QuickQuoteInputBean input) throws
	BaseException {
	   factory.setBean(new LogisticsViewBean());
		String baseQuery = "select qv.ops_entity_id,  nvl(qv.distributor_Ops,'N') DISTRIBUTOR_OPS, qv.INVENTORY_GROUP, qv.INVENTORY_GROUP_NAME, qv.INVENTORY_GROUP_TYPE, qv.UNIT_COST,";
		baseQuery += " qv.CURRENCY_ID, qv.MFG_LOT, qv.EXPIRE_DATE, qv.SPECS, sum(qv.QUANTITY) qty_on_hand, PO.CURRENCY_ID PURCHASE_CURRENCY_ID, ROUND(PO.UNIT_PRICE,2) PURCHASE_UNIT_COST";
		baseQuery += "  from QUICK_ITEM_INVENTORY_VIEW qv, PO_LINE po where qv.QUANTITY <> 0 and qv.item_id = " + input.getItemId();
		String baseQuery2 = " and qv.EXPIRE_DATE > sysdate + 1 AND QV.RADIAN_PO = PO.RADIAN_PO(+) AND QV.PO_LINE = PO.PO_LINE(+)";
		baseQuery2 +=  " group by qv.ops_entity_id, qv.distributor_ops, qv.INVENTORY_GROUP, qv.INVENTORY_GROUP_NAME,qv.INVENTORY_GROUP_TYPE";
		baseQuery2 += " , qv.UNIT_COST, qv.CURRENCY_ID, qv.MFG_LOT, qv.EXPIRE_DATE, qv.SPECS, PO.CURRENCY_ID, PO.UNIT_PRICE";
		baseQuery2 += " order by qv.ops_entity_id, qv.distributor_ops, qty_on_hand desc, qv.INVENTORY_GROUP_TYPE desc";

		String query1 = " and qv.ops_entity_id = '" +input.getOpsEntityId() + "'";
		if(StringUtils.isNotBlank(input.getInventoryGroup())) {
			query1 += " and qv.inventory_group = '" + input.getInventoryGroup() + "'";
		}
		
		@SuppressWarnings("unchecked")
		Collection<LogisticsViewBean> results = factory.selectQuery(baseQuery + query1 + baseQuery2);
		
		if(StringUtils.isNotBlank(input.getInventoryGroup())) {
			String query2 = " and qv.ops_entity_id = '" + input.getOpsEntityId() + "'";
			query2 += " and qv.inventory_group <> '" + input.getInventoryGroup() + "'";
			results.addAll(factory.selectQuery(baseQuery + query2 + baseQuery2));
		}
		
		String query3 = " and qv.ops_entity_id <> '" +input.getOpsEntityId() + "'";
		results.addAll(factory.selectQuery(baseQuery + query3 + baseQuery2));
				
		return results;
   }
   
   public String getSuggestedSellPrice(QuickQuoteInputBean inputBean) throws
	BaseException {
		
		StringBuilder query = new StringBuilder("select fx_suggested_price('");
		query.append(inputBean.getInventoryGroup());
		query.append("',").append(inputBean.getItemId());
		query.append(",'").append(inputBean.getCurrencyId());
		query.append("', ").append(inputBean.getCustomerId());
		query.append(") from dual ");
		
//log.debug(query);	
		return factory.selectSingleValue(query.toString());
	}
   
   public String getItemDesc(QuickQuoteInputBean inputBean) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(), getLocale());
	   GenericSqlFactory factory = new GenericSqlFactory(dbManager);
	   StringBuilder query = new StringBuilder("select part_description from catalog_item where item_id = ").append(inputBean.getItemId());
		
	   return factory.selectSingleValue(query.toString());
   }
   
   public Object[] doCreateMRFromMR(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem) throws BaseException {
	    Object[] obj = new Object[2];

		Collection inArgs = new Vector();
		inArgs.add(oldPrNumber);
		inArgs.add(lineItem);
		inArgs.add(personnelId);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		try {
				if (log.isDebugEnabled()) {
					log.debug("PKG_RLI_SALES_ORDER.P_COPY_MULTI_MR_LINE"+inArgs);
				}
				Vector v = (Vector) factory.doProcedure("PKG_RLI_SALES_ORDER.P_COPY_MULTI_MR_LINE", inArgs, outArgs);
				obj[0] = v.get(0);
				if (log.isDebugEnabled()) {
					log.debug("error"+v.get(2));
				}
			
				return obj;
		} catch (Exception ex) {
				ex.printStackTrace();
				obj[1] = library.getString("generic.error");
				return obj;
		}
	}
   
   	public Vector callNewSalesQuote(BigDecimal personnelId, BigDecimal oldPrNumber) throws BaseException {
	    
   		Collection inArgs = new Vector();
		inArgs.add(personnelId);

		if (!isBlank(oldPrNumber))
			inArgs.add(oldPrNumber);
		else
			inArgs.add("");
		
		inArgs.add("Quote"); // This is for Quote

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		if (log.isDebugEnabled()) {
			log.debug("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE"+inArgs);
		}
		Vector v = (Vector) factory.doProcedure("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE", inArgs, outArgs);
		return v;
	}
   	
   	public String callNewSalesQuoteLine(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem, BigDecimal newPrNumber) throws BaseException {
	    
	    String errorMessages = null;
		try {
				Collection inArgs = new Vector();
				inArgs.add(personnelId);
				inArgs.add(oldPrNumber);
				inArgs.add(lineItem);
				if (!isBlank(newPrNumber)) inArgs.add(newPrNumber);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE_LINE"+inArgs);
				}
				Vector v = (Vector) factory.doProcedure("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE_LINE", inArgs, outArgs);
				
				if (v.get(1) != null && !((String) v.get(1)).equalsIgnoreCase("OK") && !((String) v.get(1)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+v.get(1));
					}
					errorMessages = v.get(1).toString();
				}
			return errorMessages;
		} catch (Exception ex) {
			ex.printStackTrace();
			return errorMessages;
		}

	}
   	
   	public Vector callNewMRforNewCustomerFromMR(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem, BigDecimal newCustomerId, String newCompanyId, String newShipToLocId) throws BaseException {
	    
   		Vector v = new Vector();
		try {
				Collection inArgs = new Vector();
				inArgs.add(oldPrNumber);
				inArgs.add(lineItem);
				inArgs.add(personnelId);
				inArgs.add(newCompanyId);
				inArgs.add(newCustomerId);
				inArgs.add(newShipToLocId);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_RLI_SALES_order.P_CREATE_MR_NEW_CUSTOMER"+inArgs);
				}
				v = (Vector) factory.doProcedure("PKG_RLI_SALES_order.P_CREATE_MR_NEW_CUSTOMER", inArgs, outArgs);
				
			return v;
		} catch (Exception ex) {
			ex.printStackTrace();
			return v;
		}
	}
   	
   	public Vector callNewMRforNewCustomerFromQuote(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem, BigDecimal newCustomerId, String newCompanyId, String newShipToLocId) throws BaseException {
	    
   		Vector v = new Vector();
		try {
				Collection inArgs = new Vector();
				inArgs.add(oldPrNumber);
				inArgs.add(lineItem);
				inArgs.add(personnelId);
				inArgs.add(newCompanyId);
				inArgs.add(newCustomerId);
				inArgs.add(newShipToLocId);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_RLI_SALES_ORDER.P_CREATE_MR_FROM_QTE_NEW_CUST"+inArgs);
				}
				v = (Vector) factory.doProcedure("PKG_RLI_SALES_ORDER.P_CREATE_MR_FROM_QTE_NEW_CUST", inArgs, outArgs);
				
			return v;
		} catch (Exception ex) {
			ex.printStackTrace();
			return v;
		}
	}
   	
   	public Vector callNewQuoteforNewCustomerFromQuote(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem, BigDecimal newCustomerId, String newCompanyId, String newShipToLocId) throws BaseException {
	    
   		Vector v = new Vector();
		try {
				Collection inArgs = new Vector();
				inArgs.add(personnelId);
				inArgs.add(oldPrNumber);
				inArgs.add(lineItem);
				inArgs.add("Quote");
				inArgs.add(newCompanyId);
				inArgs.add(newCustomerId);
				inArgs.add(newShipToLocId);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_SALES_QUOTE.P_SALES_QUOTE_NEW_CUSTOMER"+inArgs);
				}
				v = (Vector) factory.doProcedure("PKG_SALES_QUOTE.P_SALES_QUOTE_NEW_CUSTOMER", inArgs, outArgs);
				
			return v;
		} catch (Exception ex) {
			ex.printStackTrace();
			return v;
		}
	}
   	
   	public Vector callNewMRforNewCustomer(BigDecimal personnelId, BigDecimal newCustomerId, String newCompanyId, String newShipToLocId) throws BaseException {
	    
   		Vector v = new Vector();
		try {
				Collection inArgs = new Vector();
				inArgs.add(personnelId);
				inArgs.add(newCompanyId);
				inArgs.add(newCustomerId);
				inArgs.add(newShipToLocId);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_RLI_SALES_order.p_new_mr_new_customer"+inArgs);
				}
				v = (Vector) factory.doProcedure("PKG_RLI_SALES_order.p_new_mr_new_customer", inArgs, outArgs);
				
			return v;
		} catch (Exception ex) {
			ex.printStackTrace();
			return v;
		}
	}
   	
   	public Vector callNewQuoteforNewCustomer(BigDecimal personnelId, BigDecimal newCustomerId, String newCompanyId, String newShipToLocId) throws BaseException {
	    
   		Vector v = new Vector();
		try {
				Collection inArgs = new Vector();
				inArgs.add(personnelId);
				inArgs.add(newCompanyId);
				inArgs.add(newCustomerId);
				inArgs.add(newShipToLocId);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE_NEW_CUST"+inArgs);
				}
				v = (Vector) factory.doProcedure("PKG_SALES_QUOTE.P_NEW_SALES_QUOTE_NEW_CUST", inArgs, outArgs);
				
			return v;
		} catch (Exception ex) {
			ex.printStackTrace();
			return v;
		}
	}
   	
   	public String callNewMRLineFromMR(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem, BigDecimal newPrNumber) throws BaseException {
	    
   		String errorMessages = null;
		try {
				Collection inArgs = new Vector();
				inArgs.add(oldPrNumber);
				inArgs.add(lineItem);
				inArgs.add(newPrNumber);
				inArgs.add(personnelId);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_RLI_SALES_ORDER.P_COPY_MR_LINE_NEW_CUSTOMER"+inArgs);
				}
				Vector v = (Vector) factory.doProcedure("PKG_RLI_SALES_ORDER.P_COPY_MR_LINE_NEW_CUSTOMER", inArgs, outArgs);
				
				if (v.get(1) != null && !((String) v.get(1)).equalsIgnoreCase("OK") && !((String) v.get(1)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+v.get(1));
					}
					errorMessages = v.get(1).toString();
					
				}
			return errorMessages;
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}

	}
   	
   	public String callNewMRLineFromQuote(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem, BigDecimal newPrNumber) throws BaseException {
	    
   		String errorMessages = null;
		try {
				Collection inArgs = new Vector();
				inArgs.add(oldPrNumber);
				inArgs.add(lineItem);
				inArgs.add(personnelId);
				inArgs.add(newPrNumber);

				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_RLI_SALES_ORDER.P_ADD_QTE_LINE_MR_FOR_NEW_CUST"+inArgs);
				}
				Vector v = (Vector) factory.doProcedure("PKG_RLI_SALES_ORDER.P_ADD_QTE_LINE_MR_FOR_NEW_CUST", inArgs, outArgs);
				
				if (v.get(1) != null && !((String) v.get(1)).equalsIgnoreCase("OK") && !((String) v.get(1)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+v.get(1));
					}
					errorMessages = v.get(1).toString();
					
				}
			return errorMessages;
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}

	}

   	public String callNewQuoteLine(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem, BigDecimal newPrNumber) throws BaseException {
    
		String errorMessages = null;
		try {
				Collection inArgs = new Vector();
				inArgs.add(personnelId);
				inArgs.add(oldPrNumber);
				inArgs.add(lineItem);
				inArgs.add(newPrNumber);
	
				Vector outArgs = new Vector();
				outArgs.add(new Integer(java.sql.Types.NUMERIC));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				
				if (log.isDebugEnabled()) {
						log.debug("PKG_SALES_QUOTE.P_SALES_QUOTE_LINE_NEW_CUST"+inArgs);
				}
				Vector v = (Vector) factory.doProcedure("PKG_SALES_QUOTE.P_SALES_QUOTE_LINE_NEW_CUST", inArgs, outArgs);
				
				if (v.get(1) != null && !((String) v.get(1)).equalsIgnoreCase("OK") && !((String) v.get(1)).equalsIgnoreCase("")) {
					if (log.isDebugEnabled()) {
						log.debug("error:"+v.get(1));
					}
					errorMessages = v.get(1).toString();
					
				}
			return errorMessages;
		} catch (Exception ex) {
			ex.printStackTrace();
			return library.getString("generic.error");
		}
	
	}
   	
   	public Vector callCreateMrFromQuoteLine(BigDecimal personnelId, BigDecimal oldPrNumber, String lineItem) throws BaseException {
	    
		Collection inArgs = new Vector();
		inArgs.add(oldPrNumber);
		inArgs.add(lineItem);
		inArgs.add(personnelId);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		if (log.isDebugEnabled()) {
			log.debug("PKG_RLI_SALES_ORDER.P_CREATE_MR_FROM_QUOTE"+inArgs);
		}
		Vector v = (Vector) factory.doProcedure("PKG_RLI_SALES_ORDER.P_CREATE_MR_FROM_QUOTE", inArgs, outArgs);
		
		if (log.isDebugEnabled()) 
			log.debug("error"+v.get(1));

		return v;
	}

   	public ExcelHandler getItemMRHistoryExcelReport(Collection<SalesSearchBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.orderdate", "label.customer", "label.unitprice", "label.margin", "label.qty", "label.customerpo", "label.qtyshipped", "label.lastshippeddate", "label.mrline", "label.globalpart", "label.customerpart", "label.specification" };
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { pw.TYPE_CALENDAR, 0, 0, 0, pw.TYPE_NUMBER, 0, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, 0, 0, 0, 0};
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 20, 0, 0, 0, 12, 0, 0, 0, 0, 15, 15 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(3, 2);
			//pw.setColumnDigit(8, 2);
	
			for (SalesSearchBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getReleaseDate());
				pw.addCell(member.getCustomerName());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitPrice());
				String margin = "";
				if(member.getMargin() != null)
					margin = member.getMargin().setScale(1,BigDecimal.ROUND_HALF_UP) + "%";
				pw.addCell(margin);
				pw.addCell(member.getQuantity());
				pw.addCell(member.getCustomerPoNumber());
				pw.addCell(member.getQuantityShipped());
				pw.addCell(member.getLastShipDate());
				pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
				pw.addCell(member.getCatPartNo());
				pw.addCell(member.getCustomerPartNo());
				pw.addCell(member.getSpecList());
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getItemPOHistoryExcelReport(Collection<POHistoryViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.dateconfirmed", "label.supplier", "label.unitprice", "label.qty", "label.qtyreceived", "label.lastreceiveddate", "label.po"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { pw.TYPE_CALENDAR, 0, 0, pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 15, 20, 10, 0, 0, 15, 15 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0,0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(4, 2);
			//pw.setColumnDigit(5, 2);
	
			for (POHistoryViewBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getDateConfirmed());
				pw.addCell(member.getSupplierName());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitPrice());
				pw.addCell(member.getQuantity());
				pw.addCell(member.getTotalQuantityReceived());
				pw.addCell(member.getDateLastReceived());
				pw.addCell(member.getRadianPo() + " - " + member.getPoLine());				
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getItemPriceQuoteHistoryExcelReport(Collection<QuotationHistoryViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.quotedate", "label.customer", "label.qty", "label.quotedprice", "label.quote",
									"label.ordered", "label.globalpart", "label.customerpart", "label.specification"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { pw.TYPE_CALENDAR, 0, pw.TYPE_NUMBER, 0, 0, 
							pw.TYPE_NUMBER, 0, 0, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 20, 0, 0, 0,
							 0, 0, 15, 18 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0,
									0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(5, 2);
			//pw.setColumnDigit(8, 2);
	
			for (QuotationHistoryViewBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getSubmittedDate());
				pw.addCell(member.getCustomerName());
				pw.addCell(member.getQuantity());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitPrice());
				pw.addCell(member.getPrNumber());
				pw.addCell(member.getOrigSalesQuoteCount());
				pw.addCell(member.getCatPartNo());
				pw.addCell(member.getCustomerPartNo());
				pw.addCell(member.getSpecList());	
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getCustomerMRHistoryExcelReport(Collection<SalesSearchBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.orderdate", "label.inventorygroup", "label.unitprice", "label.margin", "label.qty", "label.customerpo", "label.qtyshipped", "label.lastshippeddate", "label.mrline", "label.globalpart", "label.customerpart", "label.desc", "label.specification"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { pw.TYPE_CALENDAR, 0, 0, 0, pw.TYPE_NUMBER, 0, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0 };
			
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 15, 20, 18, 0 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(3, 2);
			//pw.setColumnDigit(8, 2);
	
			for (SalesSearchBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getReleaseDate());
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitPrice());
				String margin = "";
				if(member.getMargin() != null)
					margin = member.getMargin().setScale(1,BigDecimal.ROUND_HALF_UP) + "%";
				pw.addCell(margin);
				pw.addCell(member.getQuantity());
				pw.addCell(member.getCustomerPoNumber());
				pw.addCell(member.getQuantityShipped());
				pw.addCell(member.getLastShipDate());
				pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
				pw.addCell(member.getCatPartNo());
				pw.addCell(member.getCustomerPartNo());
				pw.addCell(member.getPartDescription());
				pw.addCell(member.getSpecList());
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getCustomerPriceQuoteHistoryExcelReport(Collection<QuotationHistoryViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.quotedate", "label.inventorygroup", "label.qty", "label.quotedprice", "label.quote", "label.ordered", "label.globalpart", "label.customerpart", "label.desc", "label.specification"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { pw.TYPE_CALENDAR, 0, pw.TYPE_NUMBER, 0, 0, pw.TYPE_NUMBER, 0, 0, 0, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 15, 0, 0, 0, 0, 0, 15, 20, 18  };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0,0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(5, 2);
			//pw.setColumnDigit(8, 2);
	
			for (QuotationHistoryViewBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getSubmittedDate());
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getQuantity());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitPrice());
				pw.addCell(member.getPrNumber()+ "-" + member.getLineItem());
				pw.addCell(member.getOrigSalesQuoteCount());
				pw.addCell(member.getCatPartNo());
				pw.addCell(member.getCustomerPartNo());
				pw.addCell(member.getPartDescription());
				pw.addCell(member.getSpecList());
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getInventoryExcelReport(Collection<LogisticsViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.qtyonhand", "label.cost", "label.purchasecost", "label.inventorygroup", "label.expiredate", "label.specs", "label.mfglot", "label.consigned"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { pw.TYPE_NUMBER, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 9, 9, 25, 0, 18, 18, 12 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0,0,  0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(1, 2);
	
			for (LogisticsViewBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getQtyOnHand());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitCost());
				pw.addCell(member.getPurchaseCurrencyId() + " " + member.getPurchaseUnitCost());
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getExpireDate());
				pw.addCell(member.getSpecs());
				pw.addCell(member.getMfgLot());
				String consigned = "";
				if(member.getInventoryGroupType() != null && member.getInventoryGroupType().equals("CONSIGNMENT"))
					consigned = "Y";
				pw.addCell(consigned);
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getInvoiceExcelReport(Collection<InvoiceSearchBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.customerpo", "label.invoice", "label.customerinvoice", "label.shipconfirmdate", "label.goodsvalue", "label.total", "label.materialmargin"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_NUMBER };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 12, 0, 12, 0, 0, 0, 0 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(1, 2);
	
			for (InvoiceSearchBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getPoNumber());
				pw.addCell(member.getInvoice());
				pw.addCell(member.getCustomerInvoice());
				pw.addCell(member.getDateConfirmed());
				String totalGoods = "";
				if(member.getTotalGoods() != null)
					totalGoods = member.getCurrencyId() + " " + member.getTotalGoods();
				pw.addCell(totalGoods);
				String total = "";
				if(member.getTotal() != null)
					total = member.getCurrencyId() + member.getTotal();
				pw.addCell(total);
				if(member.getMargin() != null)
					pw.addCell(member.getMargin().setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getContactExcelReport(Collection<CustomerContactViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.name", "label.nickname", "label.jobfunction", "label.phone", "label.mobile", "label.fax", "label.email", "label.otherjobfunctions", "label.active", "label.defaultcontact"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 15, 0, 15, 0, 0, 10, 15, 15, 0, 0 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(1, 2);
	
			for (CustomerContactViewBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getLastName() + ", " + member.getFirstName());
				String nickname ="";
				if (member.getNickname() != null)
					nickname = member.getNickname();
				pw.addCell(nickname);
				pw.addCell(member.getContactType());
				pw.addCell(member.getPhone());
				pw.addCell(member.getAltPhone());
				pw.addCell(member.getFax());
				pw.addCell(member.getEmail());
				String other = "";
				if(member.getPurchasing() != null && member.getPurchasing().equals("Y"))
					other += "," + "Purchasing";
				if(member.getAccountsPayable() != null && member.getAccountsPayable().equals("Y"))
					other += "," + "Accounts Payable";
				if(member.getReceiving() != null && member.getReceiving().equals("Y"))
					other += "," + "Receiving";
				if(member.getQualityAssurance() != null && member.getQualityAssurance().equals("Y"))
					other += "," + "Quality Assurance";
				if(member.getManagement() != null && member.getManagement().equals("Y"))
					other += "," + "Management";
				if(other.length() > 0)
					other = other.substring(1);
				pw.addCell(other);
				pw.addCell(member.getStatus());
				pw.addCell(member.getDefaultContact());
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getSupplierPOHistoryExcelReport(Collection<PoSearchResultBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.inventorygroup", "label.item", "label.description", "label.specification", "label.dateconfirmed", "label.qty", "label.unitprice", "label.qtyreceived", "label.lastreceiveddate", "label.qtyvouchered", "label.po" };
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { 0, 0, 0, 0, pw.TYPE_CALENDAR, pw.TYPE_NUMBER, 0, pw.TYPE_NUMBER, pw.TYPE_CALENDAR, pw.TYPE_NUMBER, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 15, 0, 20, 18, 0, 0, 0, 0, 0, 12, 0 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(1, 2);
	
			for (PoSearchResultBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getItemId());
				pw.addCell(member.getItemDesc());
				pw.addCell(member.getSpecList());
				pw.addCell(member.getDateConfirmed());
				pw.addCell(member.getQuantity());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitPrice());
				pw.addCell(member.getQtyReceived());
				pw.addCell(member.getDateLastReceived());
				pw.addCell(member.getQuantityVouchered());
				pw.addCell(member.getRadianPo()+"-"+member.getPoLine());
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getSupplierPriceListExcelReport(Collection<DbuyContractPriceOvBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.item", "label.description", "label.shipto", "label.inventorygroup", "label.priority",
									"label.startDate", "label.unitprice", "label.qty", "label.supplierpartnum", "label.minimumbuyqty",
									"label.minimumordervalue" };
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { 0, 0, 0, 0, pw.TYPE_NUMBER, 
							pw.TYPE_CALENDAR, 0, pw.TYPE_NUMBER, 0, pw.TYPE_NUMBER,
							0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 20, 0, 15, 0,
							 0, 0, 0, 20, 10,
							 10 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0,
								  0, 0, 0, 0, 0,
								  0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(1, 2);
	
			for (DbuyContractPriceOvBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getItemId());
				pw.addCell(member.getItemDesc());
				pw.addCell(member.getShipToLocationId());
				pw.addCell(member.getInventoryGroupName());
				pw.addCell(member.getPriority());
				pw.addCell(member.getStartDate());
				pw.addCell(member.getCurrencyId() + " " + member.getUnitPrice());
				pw.addCell(new BigDecimal(1));
				pw.addCell(member.getSupplierPartNo());
				pw.addCell(member.getMinBuyQuantity());
				pw.addCell(member.getMinBuyValue());
				
				for (DbuyContractPriceBreakObjBean priceBreanBean : (Collection<DbuyContractPriceBreakObjBean>) member.getPriceBreakCollection()) {
					pw.addRow();
					pw.addCell(member.getItemId());
					pw.addCell(member.getItemDesc());
					pw.addCell(member.getShipToLocationId());
					pw.addCell(member.getInventoryGroupName());
					pw.addCell(member.getPriority());
					pw.addCell(member.getStartDate());
					pw.addCell(member.getCurrencyId() + " " + priceBreanBean.getUnitPrice());
					pw.addCell(priceBreanBean.getBreakQuantity());
					pw.addCell(member.getSupplierPartNo());
					pw.addCell(member.getMinBuyQuantity());
					pw.addCell(member.getMinBuyValue());
					
				}
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getSupplierInvoiceHistoryExcelReport(Collection<VoucherReportViewBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.po", "label.buyer", "label.phone", "label.invoice", "label.invoicedate", "label.amount", "label.status", "label.approvedby", "label.approveddate", "label.comment"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { 0, 0, 0, 0, pw.TYPE_CALENDAR, 0, 0, 0, pw.TYPE_CALENDAR, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 0, 15, 0, 0, 0, 0, 0, 10, 0, 20 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(1, 2);
	
			for (VoucherReportViewBean member : data) {
	
				pw.addRow();
				pw.addCell(member.getRadianPo());
				pw.addCell(member.getBuyerName());
				pw.addCell(member.getBuyerPhone());
				pw.addCell(member.getSupplierInvoiceId());
				pw.addCell(member.getInvoiceDate());
				pw.addCell(member.getCurrencyId() + " " + member.getNetInvoiceAmount());
				pw.addCell(member.getStatus());
				pw.addCell(member.getApApproverName());
				pw.addCell(member.getApprovedDate());
				pw.addCell(member.getApNote());
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
   	public ExcelHandler getSupplierContactExcelReport(Collection<SupplierContactBean> data, Locale locale) throws NoDataException, BaseException, Exception {
		log.info("Generating Excel Report");
		try{
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);
	
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.contactname", "label.nickname", "label.contacttype", "label.phone", "label.fax", "label.email"};
			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = { 0, 0, 0, 0, 0, 0 };
	
			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 15, 0, 15, 10, 10, 25 };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, };
	
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	
			// format the numbers to the special columns
			//pw.setColumnDigit(1, 2);
	
			for (SupplierContactBean member : data) {
	
				pw.addRow();
				String name = "";
				if(member.getLastName() != null)
					name += member.getLastName();
				if(member.getFirstName() != null){
					if(name.length() > 0)
						name += ", ";
					
					name += member.getFirstName();
				}
				pw.addCell(name);
				String nickname ="";
				if (member.getNickname() != null)
					nickname = member.getNickname();
				pw.addCell(nickname);
				pw.addCell(member.getContactType());
				if(member.getPhoneExtension() != null)
					pw.addCell(member.getPhone()+" "+ member.getPhoneExtension());
				else
					pw.addCell(member.getPhone());
				pw.addCell(member.getFax());
				pw.addCell(member.getEmail());
			}
			return pw;
		}
		catch(Exception e){
			log.error(e.toString());
			throw e;
		}
	}
   	
}
