package com.tcmis.internal.catalog.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ItemBean;
import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.HtcLocalOvBean;
import com.tcmis.internal.catalog.beans.ItemSearchBean;
import com.tcmis.internal.catalog.factory.HtcLocalOvBeanFactory;
import com.tcmis.internal.catalog.factory.ItemPartBeanFactory;
import com.tcmis.internal.hub.beans.LogisticsInputBean;

public class ItemSearchProcess  extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ItemSearchProcess(String client) {
		super(client);
	}

	public ItemSearchProcess(String client,Locale locale) {
		super(client,locale);
	}

	public Collection getItemBeanCollection (String manufacturerId, String searchArgument, String excludeItemIds) throws BaseException, Exception	{
		factory.setBean(new ItemSearchBean());
		
		StringBuilder query = new StringBuilder("select i.ITEM_ID, i.ITEM_DESC, p.MATERIAL_ID, m.MATERIAL_DESC, p.PKG_STYLE, p.GRADE, mfg.MFG_DESC, p.MFG_PART_NO, p.SHELF_LIFE_DAYS, p.SHELF_LIFE_BASIS, FX_STORAGE_TEMP_LABEL_FORMAT(p.STORAGE_TEMP) FORMATTED_STORAGE_TEMP");
		query.append(",p.mfg_part_no_extension");
		query.append(" from MANUFACTURER mfg, MATERIAL m, PART p, ITEM i");
		query.append(" where");
		query.append(" (lower(i.ITEM_ID) like lower('%").append(SqlHandler.validQuery(searchArgument)).append("%') or ").append("lower(i.ITEM_DESC) like lower('%").append(SqlHandler.validQuery(searchArgument)).append("%'))");
		if (!StringHandler.isBlankString(manufacturerId)) {
			query.append(" and m.MFG_ID = ");
			query.append(manufacturerId);
		}
		query.append(" and p.MATERIAL_ID = m.MATERIAL_ID");
		query.append(" and p.ITEM_ID = i.ITEM_ID");
		query.append(" and m.MFG_ID = mfg.MFG_ID");
		if (!StringHandler.isBlankString(excludeItemIds))
			query.append(" and i.item_id not in (").append(excludeItemIds).append(")");
		query.append(" order by ITEM_DESC asc");

		return factory.selectQuery(query.toString());
	}

	public Collection getItemPartCollection (String searchArgument) throws BaseException, Exception	{
		ItemPartBeanFactory factory = new ItemPartBeanFactory(dbManager);
		return factory.select ( searchArgument );
	}
	
	public Collection getItemPartCollection(AutocompleteInputBean inputBean) throws BaseException, Exception {
		ItemPartBeanFactory factory = new ItemPartBeanFactory(dbManager);
		return factory.select(inputBean.getSearchText());
	}
	
	public String updateExtension(HtcLocalOvBean pbean,HtcLocalOvBean bean) throws BaseException, Exception {
		//		Tcm_ops.P_UPDATE_HTC_LOCAL_EXTENSION(a_item_id  number,
		//                a_customs_region   global.htc_local_extension.customs_region%type,
		//                a_local_extension  global.htc_local_extension.local_extension%type,
		//                a_action   varchar2,
		//                a_error OUT    varchar2);
		if( !isBlank(bean.getLocalExtension())) {
			//			String pkgCall = "Tcm_ops.P_UPDATE_HTC_LOCAL_EXTENSION";
			//			String action = ( "New".equals(bean.getChanged()) )?"I":"U";
			//			Collection inArgs = ;
			return getProcError(buildProcedureInput(
					bean.getItemId(),
					bean.getCustomsRegion(),
					bean.getLocalExtension(),
					( "New".equals(bean.getChanged()) )?"I":"U" ),
					null,"Tcm_ops.P_UPDATE_HTC_LOCAL_EXTENSION");
		}
		return "";
	}
	public String updateHoronizedCode(HtcLocalOvBean bean) throws BaseException, Exception {
		String query = "update item set harmonized_trade_code = '"+
		bean.getHarmonizedTradeCode()+"' where item_id = " + bean.getItemId();
		factory.deleteInsertUpdate(query);
		return "";
	}
	public Collection updateHoronizedCode(Collection<ItemBean> beans) throws BaseException, Exception {
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemBean());
		for(ItemBean bean:beans) {
			//			if( isBlank(bean.getOk()) || !bean.getOk().equals("true") ) continue;
			String query = "update item set harmonized_trade_code = '"+
			bean.getHarmonizedTradeCode()+"' where item_id = " + bean.getItemId();
			factory.deleteInsertUpdate(query);
		}
		return null;
	}

	public Collection getItemBeanCollection(LogisticsInputBean inputBean) throws BaseException, Exception {
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ItemBean());
		SearchCriteria searchCriteria = new SearchCriteria();
		SortCriteria sortCriteria = new SortCriteria();

		sortCriteria.addCriterion("itemId");

		String s = inputBean.getSearchArgument();
		String mode = inputBean.getSearchMode();
		String field = inputBean.getSearchField();
		if( !isBlank( inputBean.getCheckbox() ) )
			searchCriteria.addCriterion("harmonizedTradeCode",SearchCriterion.IS,null);

		if( !isBlank(s) ) {
			if( mode.equals("is") )
				searchCriteria.addCriterion(field,SearchCriterion.EQUALS,s);
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
		return factory.select(searchCriteria, sortCriteria, "item");
	}

	public Collection getItemBeanOvCollection(LogisticsInputBean inputBean) throws BaseException, Exception {
		HtcLocalOvBeanFactory factory = new HtcLocalOvBeanFactory(dbManager);
		SearchCriteria searchCriteria = new SearchCriteria();
		SortCriteria sortCriteria = new SortCriteria();

		sortCriteria.addCriterion("itemId");

		String s = inputBean.getSearchArgument();
		String mode = inputBean.getSearchMode();
		String field = inputBean.getSearchField();
		if( !isBlank( inputBean.getCheckbox() ) )
			searchCriteria.addCriterion("harmonizedTradeCode",SearchCriterion.IS,null);

		if( !isBlank(s) ) {
			if( mode.equals("is") )
				searchCriteria.addCriterion(field,SearchCriterion.EQUALS,s);
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
		return factory.selectObject(searchCriteria, sortCriteria);
	}


	public ExcelHandler ItemsWithoutHoronizedCodeExcelReport(LogisticsInputBean inputBean) throws
	NoDataException, BaseException, Exception {

		Collection<ItemBean> data = getItemBeanCollection(inputBean);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		//		write column headers
		pw.addRow();

		String[] headerkeys = {"label.itemid","label.itemdescription","label.harmonizedtradecode"};
		int[] types = {	0,pw.TYPE_PARAGRAPH,0};
		int[] widths = {0,30,16};

		pw.applyColumnHeader(headerkeys, types, widths, null );

		for (ItemBean member : data) {
			pw.addRow();
			pw.addCell(member.getItemId());
			pw.addCell(member.getItemDesc());
			pw.addCell(member.getHarmonizedTradeCode());
		}
		return pw;
	}

}
