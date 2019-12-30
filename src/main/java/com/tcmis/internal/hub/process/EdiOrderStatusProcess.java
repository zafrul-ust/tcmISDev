package com.tcmis.internal.hub.process;

import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.factory.CatalogPartUnitOfSaleBeanFactory;
import com.tcmis.client.order.factory.CustomerPoStageBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.BeanSorter;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.EdiOrderErrorViewBean;
import com.tcmis.internal.hub.factory.EdiOrderCheckRuleBeanFactory;
import com.tcmis.internal.hub.factory.EdiOrderErrorViewBeanFactory;
import com.tcmis.internal.hub.factory.EdiShiptoMappingViewBeanFactory;
import com.tcmis.internal.hub.factory.InternalPartListBeanFactory;

/******************************************************************************

 * Process methods for EDI Order error status page

 * @version 2.0

 *****************************************************************************/

public class EdiOrderStatusProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public EdiOrderStatusProcess(String client) {
		super(client);
	}

	/*
	 * Create a Collection of EDI_ORDER_ERROR_VIEW
	 */
	public Collection getStatusCollection(String companyId) throws BaseException {
		log.debug("executing EDI status qry");
		DbManager dbManager = new DbManager(this.getClient());
		EdiOrderErrorViewBeanFactory beanFactory = new EdiOrderErrorViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("currentOrderStatus",SearchCriterion.EQUALS,"ERROR");
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
		Collection errorStatusBeans = beanFactory.select(searchCriteria);
		dbManager = null;
		beanFactory = null;
		return errorStatusBeans;
	}

	/*
	 * Create a Collection of all valid ship-to's for the given company_id
	 */
	public Collection getShiptoList(String companyId) throws BaseException {
		Collection shiptoList = null;
		log.debug("executing EDI shipto list query");
		DbManager dbManager = new DbManager(this.getClient());
		EdiShiptoMappingViewBeanFactory beanFactory = new EdiShiptoMappingViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
		shiptoList = beanFactory.select(searchCriteria);
		dbManager = null;
		beanFactory = null;
		return shiptoList;
	}

	/*
	 * Call one of the three possible reset procedures: pkg_dbuy_from_customer.P_CREATE_PRE_860,
	 * pkg_dbuy_from_customer.CUSTOMER_PO_SHIPTO_UPDATE, pkg_dbuy_from_customer.CUSTOMER_PO_RESET
	 */
	public void resetLineStatus(Collection errorStatusBeans, String companyId, String poNum, String lineNum, String txnType, String lineSeq, String catPartNo,
			String chgOrder, String shipto, String loadId, String loadLine, String chgQty, String chgUom, String chgCurrency, String chgPrice,
			int personnelId,  int rowid) throws BaseException {
		DbManager dbManager = new DbManager(this.getClient());
		BigDecimal dLineSeq;
		BigDecimal dChgOrdSeq;
		BigDecimal dLoadId;
		BigDecimal dLoadLine;
		BigDecimal dPrice;
		BigDecimal dPersonnelId=null;
		Double  dQuantity;
		try {
			dPersonnelId = new BigDecimal(personnelId);
			dLineSeq = new BigDecimal(lineSeq);
			dChgOrdSeq = new BigDecimal(chgOrder);
			dLoadId = new BigDecimal(loadId);
			dLoadLine = new BigDecimal(loadLine);
			dQuantity = new Double(chgQty);
		} catch (NumberFormatException nfe) {
			dLineSeq = new BigDecimal(0);
			dChgOrdSeq = new BigDecimal(0);
			dLoadId = new BigDecimal(0);
			dLoadLine = new BigDecimal(0);
			dQuantity = new Double(0);
			if (dPersonnelId==null) dPersonnelId = new BigDecimal(0);
			log.error("Invalid number for resetLineStatus()");
			return;
		}

		try {
			dPrice = new BigDecimal(chgPrice);
		} catch (NumberFormatException nfe2) {
			dPrice = new BigDecimal(0);
			log.info("Error converting price. set price to 0.0");
		}

		String oldShipto=null;
		BigDecimal oldQty=null;
		String oldUom=null;
		String oldCatPart=null;
		BigDecimal oldPrice=null;
		String comments = "From web page.";

		try {
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Vector params = new Vector(6);

			oldShipto = (String) getData(errorStatusBeans, "shipto_party_id",companyId, poNum, lineNum, txnType, lineSeq);
			oldQty = (BigDecimal) getData(errorStatusBeans, "ordered_qty", companyId, poNum, lineNum, txnType, lineSeq);
			oldUom = (String) getData(errorStatusBeans, "ordered_uom", companyId, poNum, lineNum, txnType, lineSeq);
			oldCatPart = (String) getData(errorStatusBeans, "cat_part_no_on_order", companyId, poNum, lineNum, txnType, lineSeq);
			oldPrice = (BigDecimal) getData(errorStatusBeans, "unit_price_orig", companyId, poNum, lineNum, txnType, lineSeq);
			if (oldPrice==null) oldPrice = new BigDecimal(0);

			if ( (oldQty.doubleValue()!=dQuantity.doubleValue()) || !chgUom.equalsIgnoreCase(oldUom) || oldPrice.doubleValue()!=dPrice.doubleValue()) { // create pre_860
				params.add(dLoadId);
				params.add(dLoadLine);
				params.add(dLineSeq);
				params.add(dQuantity);
				params.add(chgUom);
				params.add(shipto);
				params.add(dPersonnelId);
				params.add(comments);
				params.add(chgCurrency);
				params.add(dPrice);
				String newStatus = "";
				String errorDetail = "";
				String newLoadId = "";
				String newLoadLine = "";
				String newLineSeq = "";
				params.add(newStatus);
				params.add(errorDetail);
				params.add(newLoadId);
				params.add(newLoadLine);
				params.add(newLineSeq);
				procFactory.doProcedure("pkg_dbuy_from_customer.P_CREATE_PRE_860",params);
				log.debug("GenericProcedureFactory::called pkg_dbuy_from_customer.P_CREATE_PRE_860");
			} else if (! shipto.equalsIgnoreCase(oldShipto) || ! catPartNo.equalsIgnoreCase(oldCatPart)) {
				if (! shipto.equalsIgnoreCase(oldShipto)) {
					// reset the ship to
					params.add(companyId);
					params.add(poNum);
					params.add(lineNum);
					params.add(txnType);
					params.add(dLineSeq);
					params.add(dChgOrdSeq);
					params.add(shipto);
					params.add(dPersonnelId);
					procFactory.doProcedure("pkg_dbuy_from_customer.CUSTOMER_PO_SHIPTO_UPDATE",params);
					log.debug("GenericProcedureFactory::called pkg_dbuy_from_customer.CUSTOMER_PO_SHIPTO_UPDATE");
				}
				params.removeAllElements();
				if (! catPartNo.equalsIgnoreCase(oldCatPart)) {
					// reset the cat part
					params.add(companyId);
					params.add(poNum);
					params.add(lineNum);
					params.add(txnType);
					params.add(dLineSeq);
					params.add(dChgOrdSeq);
					params.add(catPartNo);
					params.add(dPersonnelId);
					procFactory.doProcedure("pkg_dbuy_from_customer.CUSTOMER_PO_PART_UPDATE",params);
					log.debug("GenericProcedureFactory::called pkg_dbuy_from_customer.CUSTOMER_PO_PART_UPDATE");
				}
			} else {                                                                               // reset the error status
				params.add(companyId);
				params.add(poNum);
				params.add(lineNum);
				params.add(txnType);
				params.add(dLineSeq);
				params.add(dChgOrdSeq);
				procFactory.doProcedure("pkg_dbuy_from_customer.CUSTOMER_PO_RESET", params);
				log.debug("GenericProcedureFactory::called pkg_dbuy_from_customer.CUSTOMER_PO_RESET");
			}
		} catch (BaseException be) {
			log.error("Base Exception calling reset status for EDI: (ProcedureFactory failed.) " + be);
			log.trace(be);
		}
	}

	public void releaseLine(String companyId, String poNum, String lineNum, String txnType,
			String lineSeq, String chgOrder, int personnelId) throws BaseException {

		DbManager dbManager = new DbManager(this.getClient());
		BigDecimal dLineSeq;
		BigDecimal dChgOrdSeq;
		BigDecimal dPersonnelId=null;
		try {
			dPersonnelId = new BigDecimal(personnelId);
			dLineSeq = new BigDecimal(lineSeq);
			dChgOrdSeq = new BigDecimal(chgOrder);
		} catch (NumberFormatException nfe) {
			dLineSeq = new BigDecimal(0);
			dChgOrdSeq = new BigDecimal(0);
			if (dPersonnelId==null) dPersonnelId = new BigDecimal(0);
			log.error("Invalid number for releaseLine()");
			return;
		}

		try {
			GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			Vector params = new Vector(7);
			params.add(companyId);
			params.add(poNum);
			params.add(lineNum);
			params.add(txnType);
			params.add(dLineSeq);
			params.add(dChgOrdSeq);
			params.add(dPersonnelId);
			procFactory.doProcedure("pkg_dbuy_from_customer.customer_po_release", params);
			if (log.isDebugEnabled()) {
				log.debug("GenericProcedureFactory::called pkg_dbuy_from_customer.customer_po_release("+poNum+","+lineNum+")");
			}
		} catch (BaseException be) {
			log.error("Base Exception calling release line for EDI: (ProcedureFactory failed.) " + be);
			log.trace(be);
		}
	}

	public int setToIgnoreStatus(String companyId, String poNum, String lineNum, String loadId,
			String loadLine, String logonId) throws BaseException  {
		DbManager dbManager = new DbManager(this.getClient());
		CustomerPoStageBeanFactory factory = new CustomerPoStageBeanFactory(dbManager);
		Date now = new Date();
		String newComments = "Set to IGNORE " + DateHandler.formatOracleDate(now) + " by " + logonId;
		return factory.updateStatusIgnore(companyId, poNum, lineNum, loadId, loadLine, newComments);
	}
	/*
	 * Sort the Collection based on the column specified by sortColumn
	 */
	public Collection sortOrders(Collection orders, String sortColumn) throws BaseException {
		BeanSorter beanSorter = new BeanSorter(new EdiOrderErrorViewBean());
		String sortFieldGetter = getFieldGetterName(sortColumn);
		return beanSorter.sort(orders,sortFieldGetter);
	}

	/*
	 * Search EDI_ORDER_ERROR_VIEW and return the resulting Collection
	 */
	public Collection getSearchCollection(String companyId, String searchField, String operator, String value) throws BaseException {
		Collection errorStatusBeans = null;
		DbManager dbManager = new DbManager(this.getClient());
		EdiOrderErrorViewBeanFactory beanFactory = new EdiOrderErrorViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion(searchField,getCriterion(operator),formatValue(operator,value));
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
		errorStatusBeans = beanFactory.select(searchCriteria);
		dbManager = null;
		beanFactory = null;
		return errorStatusBeans;
	}

	/*
	 * get all valid UOS for the given company
	 */
	public Collection getValidUnitOfSale(String companyId) throws BaseException {
		Collection validUOS = null;
		DbManager dbManager = new DbManager(this.getClient());
		// FacItemBeanFactory beanFactory = new FacItemBeanFactory(dbManager);
		CatalogPartUnitOfSaleBeanFactory beanFactory = new CatalogPartUnitOfSaleBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
		validUOS = beanFactory.selectDistinctUOS(searchCriteria);
		dbManager = null;
		beanFactory = null;
		return validUOS;
	}

	public Collection getPartList(String orderedQty, String companyId, String facilityId, String catPartNo) throws BaseException {
		Collection partList = null;

		String query = "";
		query += "select fi.fac_part_no cat_part_no, ";
		query += "       fi.part_description, ";
		query += "       fi.unit_of_sale catalog_uos, ";
		query += "       fi.unit_of_sale_quantity_per_each uos_per_packaging, ";
		query += "       ROUND(" + orderedQty + " / fi.unit_of_sale_quantity_per_each, 4) mr_qty, ";
		query += "       i.item_id, ";
		query += "       fx_kit_packaging(i.item_id) tcm_packaging, ";
		query += "       i.item_desc ";
		query += "  from customer.fac_item fi, ";
		query += "       customer.catalog_part_item_group cpig, ";
		query += "       item i ";
		query += " where fi.company_id = '"+ companyId + "' ";
		query += "   and fi.facility_id = '" + facilityId + "' ";
		query += "   and nvl(fi.label_spec,fi.fac_part_no) = '" + catPartNo + "' ";
		query += "   and fi.company_id=cpig.company_id ";
		query += "   and fi.fac_part_no=cpig.cat_part_no ";
		query += "   and fi.facility_id=cpig.catalog_id ";
		query += "   and cpig.item_id=i.item_id ";
		query += "   and cpig.status in ('A','D')";

		DbManager dbManager = new DbManager(this.getClient());
		InternalPartListBeanFactory beanFactory = new InternalPartListBeanFactory(dbManager);

		partList = beanFactory.select(query);

		dbManager = null;
		beanFactory = null;

		return partList;
	}

	public String getReleaseStatus(String companyId) throws BaseException {
		String releaseStatus = "NO";
		String query = "select count(*) from edi_order_check_rule x where rule_id='MANUAL RELEASE REQUIRED' and company_id='" + companyId + "'";
		DbManager dbManager = new DbManager(this.getClient());
		EdiOrderCheckRuleBeanFactory beanFactory = new EdiOrderCheckRuleBeanFactory(dbManager);

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
		searchCriteria.addCriterion("",SearchCriterion.EQUALS,"MANUAL RELEASE REQUIRED");

		Collection status = beanFactory.select(searchCriteria);

		if (status.size()>0) {
			releaseStatus = "RELEASE";
		}
		dbManager = null;
		beanFactory = null;

		return releaseStatus;
	}

	public void getExcelReport(Collection data, Writer writer, Locale locale) throws
	NoDataException, BaseException, Exception {
		if(log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);

		//log.debug("data:" + data);
		Iterator iterator = data.iterator();
		PrintWriter pw = new PrintWriter(writer);
		pw.println("<html>");
		pw.println("<table border=\"1\">");
		//write column headers
		pw.println("<tr>");
		pw.print("<th>" + library.getString("label.facility") + "</th>");
		pw.print("<th>" + library.getString("label.workarea") + "</th>");
		pw.print("<th>" + library.getString("label.requestor") + "</th>");
		pw.print("<th>" + library.getString("label.mrline") + "</th>");
		pw.print("<th>" + library.getString("label.releasedate") + "</th>");
		pw.print("<th>" + library.getString("label.stockingtype") + "</th>");
		pw.print("<th>" + library.getString("label.partnumber") + "</th>");
		pw.print("<th>" + library.getString("label.partdescription") + "</th>");
		pw.print("<th>" + library.getString("label.quantityopen") + "</th>");
		pw.print("<th>" + library.getString("label.totalquantity") + "</th>");

		pw.print("<th>" + library.getString("label.needed") + "</th>");
		pw.print("<th>" + library.getString("label.allocationtype") + "</th>");
		pw.print("<th>" + library.getString("label.ref") + "</th>");
		pw.print("<th>" + library.getString("label.refline") + "</th>");
		pw.print("<th>" + library.getString("label.supplier") + "</th>");
		pw.print("<th>" + library.getString("label.mfglot") + "</th>");
		pw.print("<th>" + library.getString("label.itemid") + "</th>");
		pw.print("<th>" + library.getString("label.inventorygroup") + "</th>");
		pw.print("<th>" + library.getString("label.quantity") + "</th>");
		pw.print("<th>" + library.getString("label.quantityonhand") + "</th>");

		pw.print("<th>" + library.getString("label.quantityavailable") + "</th>");
		pw.print("<th>" + library.getString("label.status") + "</th>");
		pw.print("<th>" + library.getString("label.date") + "</th>");
		//pw.print("<th>" + library.getString("label.datenotes") + "</th>");
		pw.print("<th>" + library.getString("label.systemrequireddateontimedate") + "</th>");
		pw.print("<th>" + library.getString("label.notes") + "</th>");
		pw.print("<th>" + library.getString("label.critical") + "</th>");
		pw.print("<th>" + library.getString("label.inventorygrouponhand") + "</th>");
		pw.print("<th>" + library.getString("label.inventorygroupavailable") + "</th>");
		pw.println("<th>" + library.getString("label.lotstatusage") + "</th>");
		pw.println("<th>" + library.getString("label.deliverytype") + "</th>");

		pw.println("<th>" + library.getString("label.lookaheaddays") + "</th>");

		pw.println("</tr>");
		//now write data
		while(iterator.hasNext()) {
			EdiOrderErrorViewBean errorBean = (EdiOrderErrorViewBean)iterator.next();
			Date lookAheadDate = new Date();

			String rowColor = "bgcolor=\"#535353\" ";
			String fontColor = "<font color=\"#FFFFFF\">";
			String fontClose = "</font>";

			pw.println("<tr>");
			/*
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getFacilityId()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getApplication()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getRequestorLastName()) + ", " + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getRequestorFirstName()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getPrNumber()) + "-" + prOpenOrderBean.getLineItem() + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getReleaseDate()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getItemType()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getFacPartNo()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getPartDescription()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getOpenQuantity()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(errorBean.getOrderQuantity()) + fontClose + "</td>");

      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getRequiredDatetime()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getAllocationType()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getRefNo()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getRefLine()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getSupplier()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getMfgLot()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getItemId()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getInventoryGroup()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getAllocQuantity()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getQtyOnHand()) + fontClose + "</td>");

      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getQtyAvailable()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getRefStatus()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getRefDate()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getSystemRequiredDatetime()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getNotes()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getCritical()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getIgQtyOnHand()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getIgQtyAvailable()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getLotStatusAge()) + fontClose + "</td>");
      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getDeliveryType()) + fontClose + "</td>");

      pw.println("<td " + rowColor + ">" + fontColor + StringHandler.noBreakSpaceIfNull(prOpenOrderBean.getLookAheadDays()) + fontClose + "</td>");
      pw.println("</tr>");
			 */
		}
		pw.println("</table>");
		pw.println("</html>");
		pw.flush();
		pw.close();
	}


	/*
	 * get the value in the collection for the given field, given the po, line, load_id
	 */
	private Object getData(Collection orders, String field, String companyId, String po, String line, String txnType, String lineSeq) {
		Object oldValue = null;
		String getterName = getFieldGetterName(field);
		Method getterMethod;
		EdiOrderErrorViewBean bean = null;
		Iterator iter = orders.iterator();
		while (iter.hasNext()) {
			bean = (EdiOrderErrorViewBean) iter.next();
			if (companyId.equalsIgnoreCase(bean.getCompanyId()) &&
					po.equalsIgnoreCase(bean.getCustomerPoNo()) &&
					line.equalsIgnoreCase(bean.getCustomerPoLineNo()) &&
					txnType.equalsIgnoreCase(bean.getTransactionType()) &&
					lineSeq.equalsIgnoreCase(bean.getLineSequence().toString())) {
				try {
					getterMethod = bean.getClass().getMethod(getterName, (java.lang.Class[]) null);
					oldValue = getterMethod.invoke(bean, (java.lang.Object[]) null);
					return oldValue;
				} catch (NoSuchMethodException nsme) {
					log.error("No Such Method Exception getting data for field " + field + " : " + nsme);
				} catch (IllegalAccessException iae) {
					log.error("Illegal Access Exception getting data for field " + field + " : " + iae);
				} catch (InvocationTargetException ite) {
					log.error("Invocation Exception getting data for field " + field + " : " + ite);
				}
			}
		}
		return oldValue;
	}

	/*
	 * return the SearchCriterion for the page search operators
	 */
	private String getCriterion(String operator) {
		String criterion = "";
		if (operator.equalsIgnoreCase("CONTAINS")) {
			criterion = SearchCriterion.LIKE;
		} else if (operator.equalsIgnoreCase("IS")) {
			criterion = SearchCriterion.EQUALS;
		} else if (operator.equalsIgnoreCase("IS NOT")) {
			criterion = SearchCriterion.NOT_EQUAL;
		}
		return criterion;
	}

	/*
	 * return a prepped search value string
	 */
	private String formatValue(String operator, String value) {
		String newVal = value; // .toUpperCase();                 // mn: removed uppercase on 10/28/08
		if (operator.equalsIgnoreCase("CONTAINS")) {
			newVal = "%" + newVal + "%";
		}
		return newVal;
	}

	/*
	 * create the name of the getter for the given database field name
	 */
	private String getFieldGetterName(String col) {
		String fieldName = getFieldHandlerName(col);
		String getterName = "get" + fieldName.replaceFirst(fieldName.substring(0,1), fieldName.substring(0,1).toUpperCase());
		return getterName;
	}

	private String getFieldHandlerName(String columnName) {
		String[] sgCna = columnName.split("_");
		String sgColumnName = "";
		for (int j = 0; j < sgCna.length; ++j) {
			sgCna[j] = sgCna[j].replaceFirst(sgCna[j].substring(0, 1),
					sgCna[j].substring(0, 1).toUpperCase());
			sgColumnName = sgColumnName.concat(sgCna[j]);
		}
		return sgColumnName;
	}

}
