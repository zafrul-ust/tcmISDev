package com.tcmis.supplier.shipping.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.common.admin.factory.UserSupplierLocationBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ExcelHandlerXlsx;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.FileUploadBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentInputBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;
import com.tcmis.supplier.shipping.beans.SupplierShippingInputBean;
import com.tcmis.supplier.shipping.factory.SupplierCarrierBeanFactory;
import com.tcmis.supplier.shipping.factory.SupplierPackingViewBeanFactory;

/*******************************************************************************
 * Process for SupplierPackingView
 * 
 * @version 1.0
 ******************************************************************************/
public class PackProcess extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());

	public PackProcess(String client)
	{
		super(client);
	}

	public PackProcess(String client, String locale)
	{
		super(client, locale);
	}

	public String printTable(Collection supplierPackingViewBeanCollection)
	{
		String pdfUrl = "";
		PackConfirmPrintTableProcess printPdfProcess = new PackConfirmPrintTableProcess(
				this.getClient());
		try
		{
			pdfUrl = printPdfProcess
					.buildPdf(supplierPackingViewBeanCollection);
		}
		catch (Exception ex1)
		{
			ex1.printStackTrace();
			pdfUrl = "";
		}
		return pdfUrl;
	}

	public Collection getSearchResult(SupplierShippingInputBean bean)
			throws BaseException
	{

		DbManager dbManager = new DbManager(getClient(), getLocale());
		SupplierPackingViewBeanFactory factory = new SupplierPackingViewBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS,
					bean.getSupplier());
		if (bean.getPicklistId() != null && !bean.getPicklistId().equals(""))
			criteria.addCriterion("picklistId", SearchCriterion.EQUALS, bean
					.getPicklistId().toString());
		if (bean.getSuppLocationIdArray() != null
				&& bean.getSuppLocationIdArray().length == 1
				&& bean.getSuppLocationIdArray()[0].length() == 0)
		{
			/*
			 * All supplier locations means all supplier locations the user has
			 * privilages to
			 */
			UserSupplierLocationBeanFactory userSuppLocfactory = new UserSupplierLocationBeanFactory(
					dbManager);
			SearchCriteria suppLoccriteria = new SearchCriteria();
			suppLoccriteria.addCriterion("supplier", SearchCriterion.EQUALS,
					bean.getSupplier());
			suppLoccriteria.addCriterion("personnelId", SearchCriterion.EQUALS,
					bean.getPersonnelId().toString());
			Collection userSuppLocCollection = userSuppLocfactory
					.selectDistinctSuppLoc(suppLoccriteria, null);
			criteria.addCriterion("shipFromLocationId", SearchCriterion.EQUALS,
					userSuppLocCollection, "");
		}
		else
			if (bean.getSuppLocationIdArray() != null
					&& bean.getSuppLocationIdArray().length > 0)
			{
				boolean initFlag = true;
				for (int i = 0; i < bean.getSuppLocationIdArray().length; i++)
				{
					if (bean.getSuppLocationIdArray()[i].length() > 0)
					{
						if (initFlag)
						{
							criteria.addCriterion("shipFromLocationId",
									SearchCriterion.EQUALS,
									bean.getSuppLocationIdArray()[i].toString());
						}
						else
						{
							criteria.addValueToCriterion("shipFromLocationId",
									bean.getSuppLocationIdArray()[i].toString());
						}
						initFlag = false;
					}
				}
			}

		String s = null;
		s = bean.getSearchArgument();
		if (s != null && !s.equals(""))
		{
			String mode = bean.getSearchMode();
			String field = bean.getSearchField();
			if (field.equals("cityCommaState"))
				s = s.replaceAll("\\s", ""); // remove all white spaces
			if (mode.equals("is"))
				criteria.addCriterion(field, SearchCriterion.EQUALS, s);
			if (mode.equals("contains"))
				criteria.addCriterion(field, SearchCriterion.LIKE, s,
						SearchCriterion.IGNORE_CASE);
			if (mode.equals("startsWith"))
				criteria.addCriterion(field, SearchCriterion.STARTS_WITH, s,
						SearchCriterion.IGNORE_CASE);
			if (mode.equals("endsWith"))
				criteria.addCriterion(field, SearchCriterion.ENDS_WITH, s,
						SearchCriterion.IGNORE_CASE);
		}

		if (bean.getStatus() == null)
		{
			criteria.addCriterion("shipConfirmDate", SearchCriterion.IS, null);
		}
	
		SortCriteria scriteria = new SortCriteria();
		// SHIP_TO_LOCATION_ID,ULTIMATE_SHIP_TO_DODAAC,PR_NUMBER,LINE_ITEM
		//
		scriteria.addCriterion("picklistId");
		scriteria.addCriterion("shipToLocationId");
		scriteria.addCriterion("ultimateShipToDodaac");
		scriteria.addCriterion("prNumber");
		scriteria.addCriterion("lineItem");
		scriteria.addCriterion("serialNumber");
		return factory.select(criteria, scriteria);
	}

	public Collection getValidCarriers(SupplierShippingInputBean bean)
			throws BaseException
	{

		DbManager dbManager = new DbManager(getClient(), getLocale());
		SupplierCarrierBeanFactory factory = new SupplierCarrierBeanFactory(
				dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (bean.getSupplier() != null && !bean.getSupplier().equals(""))
			criteria.addCriterion("supplier", SearchCriterion.EQUALS,
					bean.getSupplier());

		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("carrierName");
		return factory.select(criteria, scriteria);
	}

	public ExcelHandler getExcelReportNoSplit(Collection bean, Locale locale)
			throws NoDataException, BaseException
	{

		if (log.isDebugEnabled())
		{
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}
		Collection<SupplierPackingViewBean> data = bean;
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		// write column headers
		pw.addRow();
		String[] headerkeys = { "label.supplierlocation", "label.shipto",
				"label.ultimatedodaac", "label.mrline", "label.haaspoline",
				"label.deliveryticket", "label.partno", "label.shortname",
				"label.packing", "label.qty", "label.expdate",
				"label.externalid", "label.caseid", "label.palletid","label.serialnumber",
				"label.trackingno", "label.carrier" };

		int[] widths = { 16, 30, 0, 0, 12, 12, 13, 20, 25, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] types = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				ExcelHandler.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0 };
		int[] aligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		String rowColor = "";
		String fontColor = "";
		String fontClose = "";
		Iterator iterator = bean.iterator();
		// now write data
		while (iterator.hasNext())
		{
			SupplierPackingViewBean member = (SupplierPackingViewBean) iterator
					.next();
			pw.addRow();
			pw.addCell(member.getShipFromLocationName());
			pw.addCell(member.getShipToCityCommaState() + " "
					+ member.getShipToZipcode());
			pw.addCell(member.getUltimateShipToDodaac());
			pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
			pw.addCell(member.getRadianPo() + "-" + member.getPoLine());
			pw.addCell(member.getSupplierSalesOrderNo());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getPartShortName());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getQuantity());
			pw.addCell(member.getExpireDate());
			pw.addCell(member.getBoxLabelId());
			pw.addCell(member.getBoxId());
			pw.addCell(member.getPalletId());
			pw.addCell(member.getSerialNumber());
			pw.addCell(member.getTrackingNumber());
			pw.addCell(member.getCarrierName());
		}
		return pw;
	}

	public String shipConfirms(Collection beans1) throws BaseException
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		BigDecimal batchNumber = new BigDecimal(
				dbManager.getOracleSequence("shipping_batch_seq"));
		int shipConfirmCount = 0;
		String err = "";
		// log.info("batchNumber: "+batchNumber+"");
		SupplierPackingViewBean bean = null;
		for (Object obj : beans1)
		{
			if (obj == null)
				continue;
			bean = (SupplierPackingViewBean) obj;
			String id = bean.getOk();
			String caseId = bean.getBoxId();
			if ((id != null && id.length() > 0)
					&& (caseId != null && caseId.length() > 0))
			{
				err = (String) ((Vector) update(bean)).get(0);
				if (err != null && !err.equalsIgnoreCase("ok"))
					err += err;
				shipConfirm(bean, batchNumber);
				shipConfirmCount++;
			}
		}
		// Call this if you called shipConfirm atleast once
		Vector result = null;
		if (shipConfirmCount > 0)
		{
			result = (Vector) shipConfirmBatch(batchNumber);
		}
		if (result == null)
			return null;
		String errorCode = (String) result.get(0);
		log.info("p_packing_ship_confirm errorCode- " + errorCode);
		if (errorCode != null && !errorCode.equalsIgnoreCase("ok"))
		{
			return err + errorCode;
		}
		return null;
	}

	public Object[] shipConfirmsTicket(Collection beans1) throws BaseException
	{
		SupplierPackingViewBean bean = null;
		Vector result = new Vector();
		HashMap mrLineMap = new HashMap();
		HashMap ticketQtyMap = new HashMap();
		HashMap m1 = new HashMap();
		for (Object obj : beans1)
		{
			if (obj == null)
				continue;
			bean = (SupplierPackingViewBean) obj;
			String ok = bean.getOk();
			BigDecimal prNumber = bean.getPrNumber();
			String line = bean.getLineItem();
			String ticket = bean.getDeliveryTicket();
			BigDecimal qty = bean.getQuantity();
			if ((ok != null && ok.length() > 0)
					&& (line != null && line.length() > 0)
					&& (ticket != null && ticket.length() > 0)
					&& qty.intValue() > 0)
			{
				String mrline = prNumber.toString() + "-" + line;
				HashMap ticketMap = (HashMap) mrLineMap.get(mrline);
				if (ticketMap == null)
				{
					ticketMap = new HashMap();
					mrLineMap.put(mrline, ticketMap);
					m1.put(mrline, new Integer(0));
				}
				SupplierPackingViewBean newBean = (SupplierPackingViewBean) ticketMap
						.get(ticket);
				if (newBean == null)
				{
					newBean = new SupplierPackingViewBean();
					newBean.setPrNumber(bean.getPrNumber());
					newBean.setLineItem(bean.getLineItem());
					newBean.setPackaging(bean.getPackaging());
					newBean.setDeliveryTicket(bean.getDeliveryTicket());
					newBean.setDateShipped(bean.getDateShipped());
					newBean.setTrackingNumber(bean.getTrackingNumber());
					newBean.setCarrierName(bean.getCarrierName());
					newBean.setQuantity(new BigDecimal(0));
					ticketMap.put(ticket, newBean);
					result.add(newBean);
					Integer i1 = (Integer) m1.get(mrline);
					i1 = new Integer(i1.intValue() + 1);
					m1.put(mrline, i1);
				}
				newBean.setQuantity(newBean.getQuantity().add(
						bean.getQuantity()));
			}
		}
		// Call this if you called shipConfirm atleast once
		Object[] objs = { result, m1 };
		return objs;
	}

	public void shipConfirm(SupplierPackingViewBean bean, BigDecimal batchNumber)
			throws BaseException
	{
		Collection inArgs = new Vector();
		inArgs.add(batchNumber);
		inArgs.add(bean.getBoxId());
		log.info("p_shipping_batch  " + inArgs);
		DbManager dbManager = new DbManager(getClient(), getLocale());
		dbManager.doProcedure("p_shipping_batch", inArgs);
	}

	public Collection shipConfirmBatch(BigDecimal batchNumber)
			throws BaseException
	{
		Collection inArgs = new Vector();

		inArgs.add(batchNumber);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		return procFactory.doProcedure("p_packing_ship_confirm", inArgs,
				outArgs);
	}

	public String update(Collection beans1) throws BaseException
	{
		SupplierPackingViewBean bean = null;
		String errorCode = "";
		for (Object obj : beans1)
		{
			if (obj == null)
				continue;
			bean = (SupplierPackingViewBean) obj;
			String id = bean.getOk();
			if (id != null && !id.equals(""))
			{
				String err = (String) ((Vector) update(bean)).get(0);
				if (err != null && !err.equalsIgnoreCase("ok"))
					errorCode += err;
			}
		}
		return errorCode;
	}

	public Collection update(SupplierPackingViewBean bean) throws BaseException
	{
		Collection inArgs = new Vector();
        Collection c = null;

        if (bean.getBoxId() != null)
		{
        inArgs.add(bean.getBoxLabelId());
		inArgs.add(bean.getBoxId());

		if (bean.getPalletId() != null)
		{
			inArgs.add(bean.getPalletId());
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getTrackingNumber() != null)
		{
			inArgs.add(bean.getTrackingNumber());
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getCarrierName() != null)
		{
			inArgs.add(bean.getCarrierName());
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getDeliveryTicket() != null)
		{
			inArgs.add(bean.getDeliveryTicket());
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getDateDelivered() != null)
		{
			inArgs.add(new Timestamp(bean.getDateDelivered().getTime()));
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getDateShipped() != null)
		{
			inArgs.add(new Timestamp(bean.getDateShipped().getTime()));
		}
		else
		{
			inArgs.add("");
		}	
		if (bean.getSerialNumber() != null)
		{
			inArgs.add(bean.getSerialNumber());
		}
		else
		{
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		log.info("p_packing_update  " + inArgs);
		c = procFactory.doProcedure("p_packing_update", inArgs, outArgs);
        }
        return c;
    }

	public Object[] showResult(SupplierShippingInputBean inputBean)
			throws BaseException
	{
		Vector bv = (Vector) this.getSearchResult(inputBean);
		// calculate m1 and m2
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		HashMap<String, BigDecimal> orderQuantities = new HashMap<String, BigDecimal>();
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient()));
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		String partNum = null;

		SupplierPackingViewBean pbean = null;
		for (int i = 0; i < bv.size(); i++)
		{
			pbean = (SupplierPackingViewBean) bv.get(i);
			partNum = pbean.getShipToLocationId();

			if (m1.get(partNum) == null)
			{
				i1 = new Integer(0);
				m1.put(partNum, i1);
				map = new HashMap();
				m2.put(partNum, map);
			}
			i1 = (Integer) m1.get(partNum);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(partNum, i1);

			String itemId = pbean.getUltimateShipToDodaac();
			if (itemId == null)
				itemId = "";
			itemId = "itemId" + itemId;

			if (map.get(itemId) == null)
			{
				i2 = new Integer(0);
				map.put(itemId, i2);
			}
			i2 = (Integer) map.get(itemId);
			i2 = new Integer(i2.intValue() + 1);
			map.put(itemId, i2);
			
			String key = pbean.getPrNumber() + pbean.getLineItem();
			if (orderQuantities.containsKey(key))
				pbean.setOrderQuantity(orderQuantities.get(key));
			else {
				StringBuilder query = new StringBuilder("select sum(picked_qty) from supplier.open_pick_view");
				query.append(" where pr_number = ").append(pbean.getPrNumber());
				query.append(" and line_item = ").append(SqlHandler.delimitString(pbean.getLineItem()));
				BigDecimal orderQuantity;
				try {
					orderQuantity = new BigDecimal(factory.selectSingleValue(query.toString()));
				} catch (Exception e) {
					orderQuantity = new BigDecimal("0");
				}
				pbean.setOrderQuantity(orderQuantity);
				orderQuantities.put(key, orderQuantity);
			}
		}
		Object[] objs = { bv, m1, m2 };
		return objs;
	}

	public String generateOriginAsn(Collection beans) throws BaseException
	{
		Vector<BigDecimal> packingGroupId = new Vector();
		SupplierPackingViewBean bean = null;
		String errorCode = "";
		for (Object obj : beans)
		{
			if (obj == null)
				continue;
			bean = (SupplierPackingViewBean) obj;

			String id = bean.getOk();
			if (id != null
					&& !id.equals("")
					&& (bean.getOriginInspectionRequired()
							.equalsIgnoreCase("Y")))
			{
				if (bean.getPackingGroupId() != null
						&& bean.getUsgovTcn() != null
						&& !packingGroupId.contains(bean.getPackingGroupId()))
				{
					packingGroupId.add(bean.getPackingGroupId());
					generateAsn(bean);
				}
			}
		}
		return errorCode;
	}

	public void generateAsn(SupplierPackingViewBean bean) throws BaseException
	{
		Collection inArgs = new Vector();

		if (bean.getPrNumber() != null)
		{
			inArgs.add(bean.getPrNumber());
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getLineItem() != null)
		{
			inArgs.add(bean.getLineItem());
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getPackingGroupId() != null)
		{
			inArgs.add(bean.getPackingGroupId());
		}
		else
		{
			inArgs.add("");
		}
		if (bean.getShipFromLocationId() != null)
		{
			inArgs.add(bean.getShipFromLocationId());
		}
		else
		{
			inArgs.add("");
		}

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(
				dbManager);
		if (log.isDebugEnabled())
		{
			log.debug("p_generate_origin_insp_asn  " + inArgs);
		}
		procFactory.doProcedure("p_generate_origin_insp_asn", inArgs);
	}

	// TCMISDEV-264: Convert the Pack & Confirm Report
	/**
	 * Returns a comma separated list of shipfromlocationIds
	 * 
	 * @param bean
	 * @throws BaseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getShipFromLocationIds(SupplierShippingInputBean bean)
			throws BaseException
	{
		StringBuilder locationsIdsStr = new StringBuilder();
		DbManager dbManager = new DbManager(getClient(), getLocale());
		UserSupplierLocationBeanFactory userSuppLocfactory = new UserSupplierLocationBeanFactory(
				dbManager);
		SearchCriteria suppLoccriteria = new SearchCriteria();
		suppLoccriteria.addCriterion("supplier", SearchCriterion.EQUALS,
				bean.getSupplier());
		suppLoccriteria.addCriterion("personnelId", SearchCriterion.EQUALS,
				bean.getPersonnelId().toString());
		Collection userSuppLocCollection = userSuppLocfactory
				.selectDistinctSuppLoc(suppLoccriteria, null);
		Iterator userSuppLocIterator = userSuppLocCollection.iterator();
		while (userSuppLocIterator.hasNext())
		{
			String currentId = (String) userSuppLocIterator.next();
			locationsIdsStr.append(currentId);
			locationsIdsStr.append(",");
		}
		return locationsIdsStr.toString();
	}

	/**
	 * Build a filter query string that can be used by the report template to
	 * filter data in a generic way.
	 * 
	 * @param inputBean
	 * @return
	 * @throws BaseException
	 */
	public String getFilterCondition(SupplierShippingInputBean inputBean)
			throws BaseException
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		SupplierPackingViewBeanFactory factory = new SupplierPackingViewBeanFactory(
				dbManager);
		StringBuilder query = new StringBuilder();
		query.append("1=1");

		SearchCriteria searchCriteria = new SearchCriteria();
		String searchArgument = inputBean.getSearchArgument();
		if (searchArgument != null && !searchArgument.equals(""))
		{
			String mode = inputBean.getSearchMode();
			String field = inputBean.getSearchField();
			if (field.equals("cityCommaState"))
				searchArgument = searchArgument.replaceAll("\\s", "");
			if (mode.equals("is"))
				searchCriteria.addCriterion(field, SearchCriterion.EQUALS,
						searchArgument);
			if (mode.equals("contains"))
				searchCriteria.addCriterion(field, SearchCriterion.LIKE,
						searchArgument, SearchCriterion.IGNORE_CASE);
			if (mode.equals("startsWith"))
				searchCriteria.addCriterion(field, SearchCriterion.STARTS_WITH,
						searchArgument, SearchCriterion.IGNORE_CASE);
			if (mode.equals("endsWith"))
				searchCriteria.addCriterion(field, SearchCriterion.ENDS_WITH,
						searchArgument, SearchCriterion.IGNORE_CASE);
		}
		if (inputBean.getPicklistId() != null
				&& !inputBean.getPicklistId().equals(""))
			searchCriteria.addCriterion("picklistId", SearchCriterion.EQUALS,
					inputBean.getPicklistId().toString());
		if (inputBean.getStatus() == null)
		{
			searchCriteria.addCriterion("shipConfirmDate", SearchCriterion.IS, null);
		}
		String whereClause = factory.getWhereClause(searchCriteria);
		if (whereClause != null && whereClause.length() > 0)
		{
			String whereQuery = null;
			query.append(" AND ");
			if (whereClause.startsWith("where"))
				whereQuery = new String(whereClause.substring(6));
			else
				whereQuery = new String(whereClause);
			query.append(whereQuery);
		}
		return query.toString();
	}
	
	
	public Collection getSerialNumsByDelTick(SupplierShippingInputBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		factory.setBean(new SupplierPackingViewBean());
		StringBuilder query = new StringBuilder("select * from SUPPLIER_PACKING_VIEW where SUPPLIER = '").append(input.getSupplier());
		query.append("' and SHIP_FROM_LOCATION_ID = '").append(input.getSelShipFromLocationId());
		query.append("' and SUPPLIER_SALES_ORDER_NO = '").append(input.getSupplierSalesOrderNo());
		query.append("' and SHIP_CONFIRM_DATE IS NULL order by PICKLIST_ID,SHIP_TO_LOCATION_ID,ULTIMATE_SHIP_TO_DODAAC,PR_NUMBER,LINE_ITEM asc");
		return factory.selectQuery(query.toString());
	}
	
	/**
	 * TCMISDEV-3644
	 * Create Excel captured data per Delivery Ticket
	 * Intended to assist users for large orders so they can copy/paste and upload Serial Numbers
	 **/
	public ExcelHandler getSerialNumExcel(Collection bean, Locale locale)
			throws NoDataException, BaseException
	{

		if (log.isDebugEnabled())
		{
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}
		Collection<SupplierPackingViewBean> data = bean;
		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		// write column headers
		pw.addRow();
		String[] headerkeys = {"label.pleasedonotedit","label.thiscolumnisignored","label.deliveryticket","label.caseid", "label.palletid","label.serialnumber"};


		int[] widths = {20,20,20,40,40,40};
		int[] types = {0,0, 0, 0, 0, 0 };
		int[] aligns = {0,0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		String rowColor = "";
		String fontColor = "";
		String fontClose = "";
		Iterator iterator = bean.iterator();
		// now write data
		while (iterator.hasNext())
		{
			SupplierPackingViewBean member = (SupplierPackingViewBean) iterator
					.next();
			pw.addRow();
			pw.addCell(member.getBoxLabelId());
			pw.addCell("");
			pw.addCell(member.getDeliveryTicket());
			pw.addCell(member.getBoxId());
			pw.addCell(member.getPalletId());
			pw.addCell(member.getSerialNumber());
		}
		return pw;
	}
	/**
	 * TCMISDEV-3644
	 * Upload Excel captured data per Delivery Ticket
	 * Intended to assist users for large orders so they can copy/paste and upload Serial Numbers
	 **/
	public Collection uploadSerialNumExcel(FileUploadBean fileBean,SupplierShippingInputBean inputBean, Locale locale) throws BaseException,IOException {
		DbManager dbManager = null;
		Connection conn = null;
		GenericProcedureFactory procFactory = null;
		GenericSqlFactory sqlFactory = null;

		try{
			if (fileBean.getTheFile() != null) {
					if (log.isDebugEnabled()) {
						log.debug(fileBean.getTheFile().getName());
					}
				Vector errorMessages = new Vector();
				Collection inArgs = null;
				Collection outArgs = null;
				String errorMsg = "";
				String errorCode = null;
				dbManager = new DbManager(this.getClient(), getLocale());
				conn = dbManager.getConnection();
				sqlFactory = new GenericSqlFactory(dbManager);
				
				Vector<Vector<String>> sv = null;
				String fileName = fileBean.getTheFile().getCanonicalPath();
				if(fileName.indexOf(".xlsx") != -1)
					sv = ExcelHandlerXlsx.read(fileName);
				else if(fileName.indexOf(".xls") != -1)
					sv = ExcelHandler.read(fileName);			
				else
				{
					errorMessages.add(new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale).getString("label.filetypenotallowed"));
					return errorMessages;
				}
				int row = 0;
						
				for(Vector<String> v : sv) {
					row++;
					if( row == 1 ) continue;// line 1 is header.
					String boxLabelId = v.get(0);
					if( StringHandler.isBlankString(boxLabelId) )  break;
						try {
							
							 inArgs = new Vector(9);
					         inArgs.add(new BigDecimal(boxLabelId));
					         String boxId = v.get(3);
					         if(boxId.length() > 9)
					        	 boxId = boxId.substring(15);
					         inArgs.add(boxId);
					         String palletId = v.get(4);
					         if(palletId.length() > 9)
					        	 palletId = palletId.substring(15);
					         inArgs.add(palletId);
					         inArgs.add(null);
					         inArgs.add(null);
					         inArgs.add(inputBean.getSupplierSalesOrderNo());
					         inArgs.add(null);
					         inArgs.add(inputBean.getDesiredShipDateEnd());
					         inArgs.add(v.get(5)); 
					         outArgs = new Vector(1);
					         outArgs.add(new Integer(java.sql.Types.VARCHAR));
		                     if(log.isDebugEnabled()) {
		            		     log.debug("p_packing_update:" + inArgs);
				             }
		                     Vector<String> error = (Vector<String>) sqlFactory.doProcedure(conn,"p_packing_update", inArgs, outArgs);
		                     
		                     if(error.size()>0 && !"OK".equalsIgnoreCase(error.get(0)))
		                     {
		                    	 errorCode = (String) error.get(0);
		                    	 log.info("Error calling Procedure p_packing_update:Delivery Ticket "+inputBean.getSupplierSalesOrderNo()+", Case Label Id:"+boxLabelId+" Error Code "+errorCode+" ");
		                    	 if(errorMessages.isEmpty())
		                    		 errorMessages.add(new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale).getString("error.serialnumberuploaderror")+"<br/>");
		                    	 errorMessages.add(errorCode);
		                    	 
		                     }
						} catch (Exception e) {
				         	 if(errorMessages.isEmpty())
	                    		 errorMessages.add(new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale).getString("error.serialnumberuploaderror")+"<br/>");
							errorMsg = "Error calling Procedure p_packing_update:Delivery Ticket "+inputBean.getSupplierSalesOrderNo();
							errorMessages.add(errorMsg);
						}
				}
				
				String delieveryTicketCount = sqlFactory.selectSingleValue(new StringBuilder("select count(*) from SUPPLIER_PACKING_VIEW where SUPPLIER = '").append(inputBean.getSupplier()).append("' and SUPPLIER_SALES_ORDER_NO = '").append(inputBean.getSupplierSalesOrderNo()).append("' and SHIP_CONFIRM_DATE IS NULL").toString(),conn);
				int dtc = Integer.parseInt(delieveryTicketCount);
				if(dtc != (row-1))
					errorMessages.add(new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale).getString("error.serialnumberexcelnotcomplete"));
				
				return (errorMessages.size()>0?errorMessages:null);
			}
		}
		finally
		{
			   if(dbManager != null)
				   dbManager.returnConnection(conn);		
			   	procFactory = null;
			   	sqlFactory = null;
				dbManager = null;
		}
		return null;
	}
	/**
	 * TCMISDEV-3644
	 * Create Excel template
	 * Intended to assist users for large orders so they can copy/paste and upload Serial Numbers
	 **/
	public ExcelHandler getSerialNumExcelTemplate(Locale locale)
			throws NoDataException, BaseException
	{

		if (log.isDebugEnabled())
		{
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}

		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		// write column headers
		pw.addRow();
		String[] headerkeys = {"label.pleasedonotedit","label.thiscolumnisignored","label.deliveryticket","label.caseid", "label.palletid","label.serialnumber"};


		int[] widths = {20,20,20,40,40,40};
		int[] types = {0,0, 0, 0, 0, 0 };
		int[] aligns = {0,0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		String rowColor = "";
		String fontColor = "";
		String fontClose = "";

		for(int i = 0; i < 2;i++)
		{
			pw.addRow();
			pw.addCell(library.getString("label.tcmiddata"));
			pw.addCell("");
			pw.addCell(library.getString("label.adeliveryticket"));
			pw.addCell(library.getString("label.acaseid"));
			pw.addCell(library.getString("label.apalletid"));
			pw.addCell(library.getString("label.apalletid"));
		}

		return pw;
	}
}