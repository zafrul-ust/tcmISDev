package com.tcmis.client.dla.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:08:29 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.dla.beans.PolchemOrderStatusViewBean;
import com.tcmis.client.dla.beans.PolchemOrderTrackingInputBean;
import com.tcmis.client.dla.factory.PolchemOrderStatusViewBeanFactory;
import com.tcmis.client.dla.factory.VvCurrentOrderStatusBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for ItemManagement
 * @version 1.0
 *****************************************************************************/

public class PolchemOrderTrackingProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public PolchemOrderTrackingProcess(String client) {
		super(client);
	}

	public PolchemOrderTrackingProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getStatusCollection() throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		VvCurrentOrderStatusBeanFactory factory = new VvCurrentOrderStatusBeanFactory(dbManager);
		return factory.select(new SearchCriteria(), null);
	}

	public Collection<PolchemOrderStatusViewBean> getSearchData(PolchemOrderTrackingInputBean bean) throws
	BaseException {

		if (log.isDebugEnabled()) {
			log.debug("bean:" + bean);
		}
		DbManager dbManager = new DbManager(getClient(),getLocale());

		PolchemOrderStatusViewBeanFactory factory = new PolchemOrderStatusViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		//		search by
		String s = null;
		s = bean.getSearchArgument();
		if ( s != null && !s.equals("") ) {
			String mode = bean.getSearchType();
			String field = bean.getSearchField();

			if( mode.equals("is") )
				criteria.addCriterion(field,SearchCriterion.EQUALS,s);
			if( mode.equals("contains"))
				criteria.addCriterion(field,
						SearchCriterion.LIKE,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("startsWith"))
				criteria.addCriterion(field,
						SearchCriterion.STARTS_WITH,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("endsWith"))
				criteria.addCriterion(field,
						SearchCriterion.ENDS_WITH,
						s,SearchCriterion.IGNORE_CASE);
		}

		//		Status
		if(!StringHandler.isBlankString(bean.getCurrentStatus())) {
			criteria.addCriterion("currentStatus", SearchCriterion.EQUALS, bean.getCurrentStatus());
		}

		//		date
		if(bean.getEndDate() != null) {
			criteria.addCriterion("orderReceivedDate",
					SearchCriterion.LESS_THAN_OR_EQUAL_TO,
					DateHandler.formatOracleDate(bean.getEndDate()));
		}
		if(bean.getBeginDate() != null) {
			criteria.addCriterion("orderReceivedDate",
					SearchCriterion.GREATER_THAN_OR_EQUAL_TO,
					DateHandler.formatOracleDate(bean.getBeginDate()));
		}


		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("poNumber");

		return factory.select(criteria,scriteria);
	}

	public Object[] showResult(PolchemOrderTrackingInputBean inputBean) throws BaseException {
		Vector bv = (Vector) this.getSearchData(inputBean);
		HashMap m1 = new HashMap();
		Integer i1 = null;
		String partNum = null;

		PolchemOrderStatusViewBean pbean = null;
		for (int i = 0; i < bv.size(); i++) {
			pbean = (PolchemOrderStatusViewBean) bv.get(i);
			partNum = StringHandler.emptyIfNull(pbean.getPoNumber());

			if (m1.get(partNum) == null) {
				i1 = new Integer(0);
				m1.put(partNum, i1);
			}
			i1 = (Integer) m1.get(partNum);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(partNum, i1);
		}

		Object[] objs = {bv,m1};
		return objs;
	}


	public ExcelHandler getExcelReport(Collection bean,Locale locale) throws
	Exception {
		if(log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}

		ResourceLibrary library = new ResourceLibrary(
				"com.tcmis.common.resources.CommonResources", locale);
		Collection<PolchemOrderStatusViewBean> data = bean;
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.milstrip","label.nsn","label.ownercompanyid", "label.orderreceived","label.haasordercreated",
				"label.dpmsstatus","label.dpmsreleasedate","label.inventorygroup", "label.haasordernumber", "label.status",
				"label.qty","label.allocateddate","label.backordereddate","label.freightadvicesent","label.freightadvicereceived",
				"label.picklistprintdate","label.packeddate","label.dateshipped","label.shipconfirmsent"};
		/*This array defines the type of the excel column.
    0 means default depending on the data type. */
		int[] types = {
				0,0,0,pw.TYPE_DATE,pw.TYPE_DATE,
				0,pw.TYPE_DATE,0,0,0,
				pw.TYPE_NUMBER,pw.TYPE_DATE,pw.TYPE_DATE,pw.TYPE_DATE,pw.TYPE_DATE,
				pw.TYPE_DATE,pw.TYPE_DATE,pw.TYPE_CALENDAR,pw.TYPE_DATE};
		/*This array defines the default width of the column when the Excel file opens.
    0 means the width will be default depending on the data type.*/
		int[] widths = {
				15,15,12,0,0,
				12,0,20,0,12,
				0,0,0,0,0,
				0,0,0,0};
		/*This array defines the horizontal alignment of the data in a cell.
    0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = {
				pw.ALIGN_CENTER,pw.ALIGN_CENTER,pw.ALIGN_CENTER,0,0,
				0,0,0,0,0,
				0,0,0,0,0,
				0,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// now write data
		//		int i = 1;
		for (PolchemOrderStatusViewBean member : data) {
			//			i++;
			pw.addRow();

			pw.addCell(member.getPoNumber());
			pw.addCell(member.getFacPartNo());
			pw.addCell(member.getOwnerCompanyId());
			pw.addCell(member.getOrderReceivedDate());
			pw.addCell(member.getOrderCreatedDate());
			pw.addCell(member.getOrderStatus());
			pw.addCell(member.getReleasedDate());
			pw.addCell(member.getInventoryGroup());
			pw.addCell(member.getPrNumber()+"-"+member.getLineItem());
			pw.addCell(member.getCurrentStatus());
			pw.addCell(member.getQuanty());
			pw.addCell(member.getAllocatedDate());
			pw.addCell(member.getBackorderedDate());
			pw.addCell(member.getFaSentDate());
			pw.addCell(member.getFaReceivedDate());
			pw.addCell(member.getPicklistPrintDate());
			pw.addCell(member.getPackedDate());
			pw.addCell(member.getShippedDate());
			pw.addCell(member.getScSentDate());
		}
		return pw;
	}
}
