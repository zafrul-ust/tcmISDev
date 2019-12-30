package com.tcmis.internal.catalog.process;

import java.util.Collection;
import java.util.Locale;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.HmirsMgmtInputBean;
import com.tcmis.internal.catalog.beans.HmirsMgmtViewBean;

/******************************************************************************
 * Process used by MaterialSearchAction
 * @version 1.0
 *****************************************************************************/

public class HmirsMgmtProcess extends GenericProcess {

	public HmirsMgmtProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<HmirsMgmtViewBean> getSearchResults(HmirsMgmtInputBean input) throws BaseException, Exception {
		StringBuilder query = new StringBuilder();
		query.append("select hrm.*, fx_personnel_id_to_name(last_updated_by) last_updated_by_name");
		query.append(" from usgov.hmirs_road_map hrm where ");
		boolean appendAnd = false;
		if (input.hasLoadDateFrom()) {
			query.append("load_date >= " + DateHandler.getOracleToDateFunction(input.getLoadDateFrom()));
			appendAnd = true;
		}
		if (input.hasLoadDateTo()) {
			if (appendAnd) {
				query.append(" and");
			}
			query.append(" load_date <= " + DateHandler.getOracleToDateFunction(input.getLoadDateTo()));
			appendAnd = true;
		}
		if ( ! input.isItemSearch() && input.isDisplayOnlyUnmappedNsns()) {
			if (appendAnd) {
				query.append(" and");
			}
			query.append(" item_id is null");
			appendAnd = true;
		}
		if (input.hasSearchText()) {
			String searchWhat = "";
			if (input.isNsnSearch()) {
				searchWhat = "nsn";
			}
			else if (input.isItemSearch()) {
				searchWhat = "item_id";
			}
			else if (input.isMfgNameSearch()) {
				searchWhat = "manufacturer_name";
			}
			
			if (appendAnd) {
				query.append(" and ");
			}
			
			String searchType = input.getSearchType();
			if (searchType.equals("is"))
				query.append(searchWhat).append(" = ").append(SqlHandler.delimitString(input.getSearchText()));
			if (searchType.equals("contains"))
				query.append(" lower(").append(searchWhat).append(") like LOWER('%").append(SqlHandler.validQuery(input.getSearchText())).append("%')");
			if (searchType.equals("startsWith"))
				query.append(" lower(").append(searchWhat).append(") like LOWER('").append(SqlHandler.validQuery(input.getSearchText())).append("%')");
			if (searchType.equals("endsWith"))
				query.append(" lower(").append(searchWhat).append(") like LOWER('%").append(SqlHandler.validQuery(input.getSearchText())).append("')");
		}
		else if ( ! (input.hasLoadDateFrom() || input.hasLoadDateTo())){
			throw new BaseException("Must have one of load date from, load date to, or search text.");
		}
		query.append(" order by nsn");
		return factory.setBean(new HmirsMgmtViewBean()).selectQuery(query.toString());
	}

	public void updateHmirsRoadMap(Collection<HmirsMgmtViewBean> mappings, PersonnelBean user) throws BaseException {
		for (HmirsMgmtViewBean mapping : mappings) {
			if ( ! mapping.isMappingEstablished()) {
				String stmt = "update usgov.hmirs_road_map set item_id = " + mapping.getItemId()
						+ ", last_updated_date = sysdate, last_updated_by = " + user.getPersonnelId()
						+ " where hmirs_road_map_id = " + mapping.getHmirsRoadMapId();
				
				factory.deleteInsertUpdate(stmt);
			}
		}
	}
	
	public ExcelHandler getExcelReport(HmirsMgmtInputBean bean, PersonnelBean personnelBean, Locale locale) throws BaseException, Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		Collection<HmirsMgmtViewBean> data = getSearchResults(bean);

		ExcelHandler pw = new ExcelHandler(library);
		pw.addTable();
		// write column headers
		pw.addRow();
		pw.addCellKeyBold("label.loaddate");
		pw.addCellKeyBold("label.from");
		pw.addCell(bean.getLoadDateFrom());
		pw.addCellKeyBold("label.to");
		pw.addCell(bean.getLoadDateTo());
		
		pw.addRow();
		pw.addCellBold(bean.getSearchWhatDesc() + " " + bean.getSearchTypeDesc());
		pw.addCell(bean.getSearchText());
		
		if (bean.isDisplayOnlyUnmappedNsns()) {
			pw.addRow();
			pw.addTdRegionBold(library.getString("label.displayunmappednsn"),1,3);
		}
		
		pw.addRow();
		pw.addRow();
		
		String[] headerkeys = {"label.item", "label.nsn", "label.productdesc", "label.manufacturer", "label.cagecode", "label.saicsupplier", 
				"label.cagecode", "label.code", "label.msds", "label.loaddate", "label.lastupdated", "label.lastupdatedby"};

		int[] widths = {20, 12, 20, 20, 0, 20, 0, 0, 0, 0, 0, 0};
		int[] types = {0, 0, pw.TYPE_PARAGRAPH, pw.TYPE_PARAGRAPH, 0, pw.TYPE_PARAGRAPH, 0, 0, 0, pw.TYPE_DATE, pw.TYPE_DATE, 0};
		int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		for (HmirsMgmtViewBean member : data) {
			pw.addRow();
			pw.addCell(member.getItemId());
			pw.addCell(member.getNsn());
			pw.addCell(member.getProductDesc());
			pw.addCell(member.getManufacturerName());
			pw.addCell(member.getManufacturerCageCode());
			pw.addCell(member.getSaicSupplierName());
			pw.addCell(member.getSupplierCageCode());
			pw.addCell(member.getSaicSupplierCode());
			pw.addCell(member.getSaicMsdsId());
			pw.addCell(member.getLoadDate());
			pw.addCell(member.getLastUpdatedDate());
			pw.addCell(member.getLastUpdatedByName());
		}

		return pw;
	}
}
