package com.tcmis.internal.hub.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:08:29 PM
 * To change this template use File | Settings | File Templates.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.factory.VvFreightAdviceStatusBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.FreightAdviceInputBean;
import com.tcmis.internal.hub.beans.FreightAdviceViewBean;
import com.tcmis.internal.hub.factory.FreightAdviceViewBeanFactory;

/******************************************************************************
 * Process for ItemManagement
 * @version 1.0
 *****************************************************************************/

public class FreightAdviceProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public FreightAdviceProcess(String client) {
		super(client);
	}

	public FreightAdviceProcess(String client,String locale) {
		super(client,locale);
	}

	public Collection getConsolidationNumber(FreightAdviceInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FreightAdviceViewBean());
		StringBuilder query = new StringBuilder("select distinct consolidation_number,transportation_mode from freight_advice_view where").append(
		" date_shipped is null and hub = ").append(inputBean.getHub()).append(
		" and scheduled_ship_date = to_date('").append(DateHandler.formatDate(inputBean.getShipDate(),"MM/dd/yyyy")).append("','mm/dd/yyyy')");
		if ("LTL".equalsIgnoreCase(inputBean.getTransportationMode())) {
			query.append(" and transportation_mode in ('LTL','TL')");
		}else if ("TL".equalsIgnoreCase(inputBean.getTransportationMode())) {
			query.append(" and transportation_mode = 'TL'");
		}
		query.append(" order by consolidation_number");
		return factory.selectQuery(query.toString());
	}

	public Collection getShipStatusCollection() throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		VvFreightAdviceStatusBeanFactory factory = new VvFreightAdviceStatusBeanFactory(dbManager);
		return factory.select(new SearchCriteria(), null);
	}

	public Collection<FreightAdviceViewBean> getSearchData(FreightAdviceInputBean inputBean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());

		FreightAdviceViewBeanFactory factory = new FreightAdviceViewBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();

		if(!StringHandler.isBlankString(inputBean.getHub())) {
			searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, inputBean.getHub());
		}

		if(!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
			searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inputBean.getInventoryGroup());
		}
		if(inputBean.getShipDate() != null) {
			searchCriteria.addCriterion("scheduledShipDate", SearchCriterion.EQUALS, DateHandler.formatOracleDate(inputBean.getShipDate()));
		}
		if(!StringHandler.isBlankString(inputBean.getShipStatus())) {
			searchCriteria.addCriterion("status", SearchCriterion.EQUALS, inputBean.getShipStatus());
		}
		if(NumberHandler.zeroBigDecimalIfNull(inputBean.getPackingGroupId()).intValue() >0) {
			searchCriteria.addCriterion("packingGroupId", SearchCriterion.EQUALS, ""+inputBean.getPackingGroupId()+"");
		}
		String s = null;
		s = inputBean.getSearchArgument();
		if ( s != null && !s.equals("") ) {
			String mode = inputBean.getSearchMode();
			String field = inputBean.getSearchField();

			if( mode.equals("is") )
			{
				if( field.equalsIgnoreCase("packingGroupId") || field.equalsIgnoreCase("prNumber"))
				{
					searchCriteria.addCriterion(field,SearchCriterion.EQUALS,s);
				}
				else
				{
					searchCriteria.addCriterion(field,SearchCriterion.EQUALS,
							s,SearchCriterion.IGNORE_CASE);
				}
			}
			if( mode.equals("contains"))
				searchCriteria.addCriterion(field,
						SearchCriterion.LIKE,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("startsWith"))
				searchCriteria.addCriterion(field,
						SearchCriterion.STARTS_WITH,
						s,SearchCriterion.IGNORE_CASE);
			if( mode.equals("endsWith"))
				searchCriteria.addCriterion(field,
						SearchCriterion.ENDS_WITH,
						s,SearchCriterion.IGNORE_CASE);
		}
		return factory.select(searchCriteria, null);
	}

	public void cancel(Collection<FreightAdviceInputBean> freightAdviceInputBeanCollection) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());


		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		Collection resultCollection = null;

		for(FreightAdviceInputBean bean : freightAdviceInputBeanCollection) {
			if(!StringHandler.isBlankString(bean.getCancel())) {
				if (log.isDebugEnabled()) {
					log.debug("cancel:" + bean.getPackingGroupId());
				}
				Collection inArgs = new Vector(1);
				inArgs.add(bean.getPackingGroupId());
				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				resultCollection = procFactory.doProcedure("p_freight_advice_cancel", inArgs, outArgs);
			}
		}
	}

	public String changeFreightAdvice(FreightAdviceViewBean bean, BigDecimal personnelId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		String errorMessage = "";
		Collection inArgs = new Vector(9);
		inArgs.add(bean.getPackingGroupId());
		inArgs.add(new Timestamp(bean.getDateShipped().getTime()));
		inArgs.add(bean.getTransportationMode());
		inArgs.add(bean.getPrNumber());
		inArgs.add(bean.getLineItem());
		inArgs.add(personnelId);
		if (!StringHandler.isBlankString(bean.getNotes())) {
			inArgs.add(bean.getNotes());
		}else {
			inArgs.add("");
		}
		inArgs.add(bean.getHub());
		if (!StringHandler.isBlankString(bean.getConsolidationNumber())) {
			inArgs.add(bean.getConsolidationNumber());
		}else {
			inArgs.add("");
		}
		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		Vector v = (Vector) procFactory.doProcedure("pkg_freight_advice.P_FREIGHT_ADVICE_REQUEST", inArgs, outArgs);
		errorMessage = (String) v.get(0);
		return errorMessage;
	}

	public ExcelHandler getExcelReport(FreightAdviceInputBean bean,Locale locale) throws
	Exception {
		if(log.isDebugEnabled()) {
			log.debug("locale:" + locale);
			log.debug("country:" + locale.getCountry());
			log.debug("language:" + locale.getLanguage());
		}
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<FreightAdviceViewBean> data = this.getSearchData(bean);
		//log.debug("data:" + data);
		//Iterator iterator = data.iterator();
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//	  write column headers
		pw.addRow();

		String[] headerkeys =   {
				"label.shiptoaddress","label.markforaddress","label.packinggroupid","label.ultimatedodaac",
				"label.inventorygroup","label.consolidationno","label.carrier",
				"label.mode","label.trackingnumber","label.trailer","label.stop","label.mrline",
				"label.milstrip","label.partnumber","label.shortname",
				"label.quantity","label.weight", "label.cube",
				"label.status","label.notes", "label.projectedshipdate", "label.actualshipdate"} ;
		int [] widths = {30,30,0,0,14,15,16,0,20,10,10,0,15,14,16,0,0,0,0,0,0,0};
		int [] types =  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,pw.TYPE_CALENDAR,pw.TYPE_CALENDAR};
		int [] aligns = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

		pw.applyColumnHeader(headerkeys, types, widths, aligns);
		//now write data

		for(FreightAdviceViewBean freightAdviceViewBean : data) {
			pw.addRow();
			pw.addCell(freightAdviceViewBean.getShipVia());
			pw.addCell(freightAdviceViewBean.getUltimateShipTo());
			pw.addCell(freightAdviceViewBean.getUltimateDodaac());
			pw.addCell(freightAdviceViewBean.getPackingGroupId());
			pw.addCell(freightAdviceViewBean.getInventoryGroup());
			pw.addCell(freightAdviceViewBean.getConsolidationNumber());
			pw.addCell(freightAdviceViewBean.getCarrierName());
			pw.addCell(freightAdviceViewBean.getTransportationMode());
			pw.addCell(freightAdviceViewBean.getCarrierTrackingNumber());
			pw.addCell(freightAdviceViewBean.getTrailerNumber());
			pw.addCell(freightAdviceViewBean.getStopNumber());
			pw.addCell(freightAdviceViewBean.getMrLine());
			pw.addCell(freightAdviceViewBean.getMilstripCode());
			pw.addCell(freightAdviceViewBean.getFacPartNo());
			pw.addCell(freightAdviceViewBean.getPartShortName());
			pw.addCell(freightAdviceViewBean.getQuantity());
			pw.addCell(freightAdviceViewBean.getWeight());
			pw.addCell(freightAdviceViewBean.getCube());
			pw.addCell(freightAdviceViewBean.getStatus());
			pw.addCell(freightAdviceViewBean.getNotes());
			pw.addCell(freightAdviceViewBean.getScheduledShipDate());
			pw.addCell(freightAdviceViewBean.getDateShipped());

		}
		return pw;
	}
}
