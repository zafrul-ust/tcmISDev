package com.tcmis.client.purchasing.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.purchasing.beans.BuyerCompanyPoViewBean;
import com.tcmis.client.purchasing.beans.InventoryToReceiveViewBean;
import com.tcmis.client.purchasing.factory.BuyerCompanyPoViewBeanFactory;
import com.tcmis.client.purchasing.factory.BuyerInventoryGroupViewBeanFactory;
import com.tcmis.client.purchasing.factory.InventoryToReceiveViewBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;

/******************************************************************************
 * Process for customer buy orders
 * @version 1.0
 *****************************************************************************/
public class CustomerBuyerProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public CustomerBuyerProcess(String client) {
		super(client);
	}

	public Collection getMyInventoryGroup(PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		BuyerInventoryGroupViewBeanFactory factory = new BuyerInventoryGroupViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS, (new BigDecimal(personnelBean.getPersonnelId())).toString());
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("inventoryGroup");
		return factory.select(criteria,sortCriteria);
	}

	public void updateCustomerToReceiveData(Collection buyOrdersInputBeanCollection, PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Iterator iterator = buyOrdersInputBeanCollection.iterator();
		while(iterator.hasNext()) {
			BuyOrdersInputBean bean = (BuyOrdersInputBean)iterator.next();
			if (bean.getOk() != null) {
				Collection inArgs = new Vector(5);
				inArgs.add(bean.getDocNum());
				inArgs.add(bean.getOrderQuantity());
				inArgs.add(bean.getLastSupplier());
				inArgs.add(DateHandler.getTimestampFromString("MM/dd/yyyy",DateHandler.formatDate(bean.getPromisedDate(),"MM/dd/yyyy")));
				inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));
				log.debug(inArgs);
				Vector outArgs = new Vector(1);
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				Collection coll = procFactory.doProcedure("p_update_buyer_company_po", inArgs,outArgs);
				if (log.isDebugEnabled()) {
					if( coll.size() == 1 ) {
						if (((Vector)coll).get(0) != null) {
							log.debug(((Vector)coll).get(0).toString());
						}
					}
				}
			}
		}
	}

	public Collection getPurchaseOrderData(BuyOrdersInputBean input, PersonnelBean user)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		InventoryToReceiveViewBeanFactory factory = new InventoryToReceiveViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		
		criteria.addCriterion("userId", SearchCriterion.EQUALS, "" + user.getPersonnelId());
		if (input.hasBuyerId()) {
			criteria.addCriterion("buyerId", SearchCriterion.EQUALS, input.getBuyerId().toString());
		}
		if (input.hasInventoryGroup()) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, input.getInventoryGroup());
		}
		if (input.hasSearchText()) {
			if ("CONTAINS".equals(input.getSearchType())) {
				criteria.addCriterion(input.getSearchWhat(), SearchCriterion.LIKE, input.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("IS".equals(input.getSearchType())) {
				criteria.addCriterion(input.getSearchWhat(), SearchCriterion.EQUALS, input.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("START_WITH".equals(input.getSearchType())) {
				criteria.addCriterion(input.getSearchWhat(), SearchCriterion.STARTS_WITH, input.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("END_WITH".equals(input.getSearchType())) {
				criteria.addCriterion(input.getSearchWhat(), SearchCriterion.ENDS_WITH, input.getSearchText(),SearchCriterion.IGNORE_CASE);
			}
		}
		
		if (input.isShowOnlyOpenRequests()) {
			criteria.addCriterion("status", SearchCriterion.EQUALS, "Open");
		}

        SortCriteria sortCriteria = new SortCriteria();
		return factory.select(criteria,sortCriteria);
	}

	public Boolean canEditPurchaseOrder(BuyOrdersInputBean bean, PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		InventoryToReceiveViewBeanFactory factory = new InventoryToReceiveViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("userId", SearchCriterion.EQUALS, (new BigDecimal(personnelBean.getPersonnelId())).toString());
		if (bean.getBuyerId() != null) {
			criteria.addCriterion("buyerId", SearchCriterion.EQUALS, bean.getBuyerId().toString());
		}
		if (!StringHandler.isBlankString(bean.getInventoryGroup())) {
			if (!"All".equals(bean.getInventoryGroup())) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			}
		}
		if (!StringHandler.isBlankString(bean.getSearchText())) {
			if ("CONTAINS".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.LIKE, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("IS".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.EQUALS, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("START_WITH".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.STARTS_WITH, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("END_WITH".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.ENDS_WITH, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}
		}
		return factory.hasPermission(criteria);
	}

	public void updatePurchaseRequestData(Collection buyOrdersInputBeanCollection, PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Iterator iterator = buyOrdersInputBeanCollection.iterator();
		while(iterator.hasNext()) {
			BuyOrdersInputBean bean = (BuyOrdersInputBean)iterator.next();
			if (bean.getOk() != null) {
				Collection inArgs = new Vector(6);
				inArgs.add(bean.getCustomerPoNumber());
				inArgs.add(bean.getOrderQuantity());
				inArgs.add(bean.getLastSupplier());
				inArgs.add(DateHandler.getTimestampFromString("MM/dd/yyyy",DateHandler.formatDate(bean.getPromisedDate(),"MM/dd/yyyy")));
				inArgs.add(bean.getPrNumber());
				inArgs.add(new BigDecimal(personnelBean.getPersonnelId()));
				Vector outArgs = new Vector(1);
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				Collection coll = procFactory.doProcedure("p_create_buyer_company_po", inArgs,outArgs);
				if (log.isDebugEnabled()) {
					if( coll.size() == 1 ) {
						if (((Vector)coll).get(0) != null) {
							log.debug(((Vector)coll).get(0).toString());
						}
					}
				}
			}
		}
	}


	public void convertPurchaseRequestData(Collection buyOrdersInputBeanCollection, PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Iterator iterator = buyOrdersInputBeanCollection.iterator();
		while(iterator.hasNext()) {
			BuyOrdersInputBean inputRow = (BuyOrdersInputBean)iterator.next();
			if (inputRow.getOk() != null) {
				Collection inArgs = new Vector(6);
				inArgs.add(inputRow.getPrNumber());
				inArgs.add(null);
				inArgs.add(inputRow.getStatus());
				inArgs.add("Radian");
				inArgs.add(inputRow.getDefaultBuyerId());
				inArgs.add("Purchaser");
				Vector outArgs = new Vector(1);
				outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				Collection coll = procFactory.doProcedure("PKG_BUY_ORDER.P_UPDATE_BUY_ORDER", inArgs,outArgs);
				if (log.isDebugEnabled()) {
					if( coll.size() == 1 ) {
						if (((Vector)coll).get(0) != null) {
							log.debug(((Vector)coll).get(0).toString());
						}
					}
				}
			}
		}
	}
	
	public Collection getPurchaseRequestData(BuyOrdersInputBean bean, PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		BuyerCompanyPoViewBeanFactory factory = new BuyerCompanyPoViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("userId", SearchCriterion.EQUALS, (new BigDecimal(personnelBean.getPersonnelId())).toString());
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
		if (!StringHandler.isBlankString(bean.getInventoryGroup())) {
			if (!"All".equals(bean.getInventoryGroup())) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			}
		}
		if (!StringHandler.isBlankString(bean.getSearchText())) {
			if ("CONTAINS".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.LIKE, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("IS".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.EQUALS, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("START_WITH".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.STARTS_WITH, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("END_WITH".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.ENDS_WITH, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}
		}
		if (bean.getShowOnlyOpenBuys() != null) {
			criteria.addCriterion("readOnly", SearchCriterion.EQUALS,"N");
		}
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion(bean.getSortBy());
		return factory.select(criteria,sortCriteria);
	}

	public Boolean canEditPurchaseRequest(BuyOrdersInputBean bean, PersonnelBean personnelBean)  throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		BuyerCompanyPoViewBeanFactory factory = new BuyerCompanyPoViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("userId", SearchCriterion.EQUALS, (new BigDecimal(personnelBean.getPersonnelId())).toString());
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, personnelBean.getCompanyId());
		if (!StringHandler.isBlankString(bean.getInventoryGroup())) {
			if (!"All".equals(bean.getInventoryGroup())) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			}
		}
		if (!StringHandler.isBlankString(bean.getSearchText())) {
			if ("CONTAINS".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.LIKE, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("IS".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.EQUALS, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("START_WITH".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.STARTS_WITH, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}else if ("END_WITH".equals(bean.getSearchType())) {
				criteria.addCriterion(bean.getSearchWhat(), SearchCriterion.ENDS_WITH, bean.getSearchText(),SearchCriterion.IGNORE_CASE);
			}
		}
		return factory.hasPermission(criteria);
	}

	public ExcelHandler  getPurchaseOrderExcelReport(BuyOrdersInputBean bean, PersonnelBean personnelBean, Locale locale) throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection data = this.getPurchaseOrderData(bean,personnelBean);

		Iterator iterator = data.iterator();
		ExcelHandler pw = new ExcelHandler(library);

		//	  write column headers

		/*
   //header table
   pw.println("<table border=\"1\">");
	 pw.println("<tr>");
	 pw.print("<td><b>" + library.getString("label.inventorygroup") + ":</b></td>");
	 pw.print("<td>" + bean.getInventoryGroup()+"</td>");
	 pw.print("<td><b>" + library.getString("label.search") + ":</b></td>");
	 pw.print("<td>" + bean.getSearchWhat()+" "+bean.getSearchType()+" "+bean.getSearchText()+"</td>");

   //blank row
   pw.println("<tr>");
   pw.print("<td>&nbsp</td>");

   pw.println("</table>");
		 */
		//result table
		pw.addTable();
		//write column headers
		pw.addRow();
		pw.addCellKeyBold("label.inventorygroup");
		pw.addCellKeyBold("label.buyer");
		pw.addCellKeyBold("label.catpartno");
		pw.addCellKeyBold("label.pkg");
		pw.addCellKeyBold("label.itemid");
		pw.addCellKeyBold("label.itemdesc");
		pw.addCellKeyBold("label.po");
		pw.addCellKeyBold("label.qty");
		pw.addCellKeyBold("label.promiseddate");
		pw.addCellKeyBold("label.supplier");

		//now write data
		while (iterator.hasNext()) {
			InventoryToReceiveViewBean inventoryToReceiveViewBean = (InventoryToReceiveViewBean) iterator.next(); ;
			pw.addRow();
			pw.addCell(inventoryToReceiveViewBean.getInventoryGroupName());
			pw.addCell(inventoryToReceiveViewBean.getBuyerName());
			pw.addCell(inventoryToReceiveViewBean.getCatPartNo());
			pw.addCell(inventoryToReceiveViewBean.getPackaging());
			pw.addCell(inventoryToReceiveViewBean.getItemId());
			pw.addCell(inventoryToReceiveViewBean.getItemDesc());
			pw.addCell(inventoryToReceiveViewBean.getPoNumber());
			pw.addCell(inventoryToReceiveViewBean.getQuantityToReceive());
			pw.addCell(DateHandler.formatDate(inventoryToReceiveViewBean.getExpectedDeliveryDate(),"MM/dd/yyyy"));
			pw.addCell(inventoryToReceiveViewBean.getSupplierName());

		}
		return pw;
	} //end of method

	public ExcelHandler  getPurchaseRequestExcelReport(BuyOrdersInputBean bean, PersonnelBean personnelBean, Locale locale) throws NoDataException, BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection data = this.getPurchaseRequestData(bean,personnelBean);

		Iterator iterator = data.iterator();
		ExcelHandler pw = new ExcelHandler(library);
		/*
   //header table
   pw.println("<table border=\"1\">");
	 pw.println("<tr>");
	 pw.print("<td><b>" + library.getString("label.inventorygroup") + ":</b></td>");
	 pw.print("<td>" + bean.getInventoryGroup()+"</td>");
	 pw.print("<td><b>" + library.getString("label.search") + ":</b></td>");
	 pw.print("<td>" + bean.getSearchWhat()+" "+bean.getSearchType()+" "+bean.getSearchText()+"</td>");

	 pw.println("<tr>");
	 pw.print("<td><b>" + library.getString("purchaserequests.label.showonlyopenbuys") + ":</b></td>");
	 pw.print("<td>" + bean.getShowOnlyOpenBuys()+"</td>");
	 pw.print("<td><b>" + library.getString("label.sortby") + ":</b></td>");
	 pw.print("<td>" + bean.getSortBy()+"</td>");

   //blank row
   pw.println("<tr>");
   pw.print("<td>&nbsp</td>");

   pw.println("</table>");
		 */
		//result table
		pw.addTable();

		//	  write column headers
		pw.addRow();
		pw.addCellKeyBold("label.inventorygroup");
		pw.addCellKeyBold("label.created");
		pw.addCellKeyBold("label.requestno");
		pw.addCellKeyBold("label.catpartno");
		pw.addCellKeyBold("label.itemid");
		pw.addCellKeyBold("label.itemdesc");
		pw.addCellKeyBold("label.requestedqty");
		pw.addCellKeyBold("label.pkg");
		pw.addCellKeyBold("label.po");
		pw.addCellKeyBold("label.qty");
		pw.addCellKeyBold("label.promiseddate");
		pw.addCellKeyBold("label.supplier");


		//now write data
		while (iterator.hasNext()) {
			BuyerCompanyPoViewBean buyerCompanyPoViewBean = (BuyerCompanyPoViewBean) iterator.next(); ;
			pw.addRow();
			pw.addCell(buyerCompanyPoViewBean.getInventoryGroup());
			pw.addCell(DateHandler.formatDate(buyerCompanyPoViewBean.getDateIssued(),"MM/dd/yyyy hh:mm"));
			pw.addCell(buyerCompanyPoViewBean.getPrNumber());
			pw.addCell(buyerCompanyPoViewBean.getCatPartNo());
			pw.addCell(buyerCompanyPoViewBean.getItemId());
			pw.addCell(buyerCompanyPoViewBean.getItemDesc());
			pw.addCell(buyerCompanyPoViewBean.getOrderQuantity());
			pw.addCell(buyerCompanyPoViewBean.getPackaging());
			pw.addCell(buyerCompanyPoViewBean.getPoNumber());
			pw.addCell(buyerCompanyPoViewBean.getQuantityToReceive());
			pw.addCell(DateHandler.formatDate(buyerCompanyPoViewBean.getExpectedDeliveryDate(),"MM/dd/yyyy"));
			pw.addCell(buyerCompanyPoViewBean.getSupplierName());

		}
		return pw;
	} //end of method

} //end of class