package com.tcmis.client.het.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.AreaBean;
import com.tcmis.client.common.beans.BuildingBean;
import com.tcmis.client.common.factory.AreaBeanFactory;
import com.tcmis.client.common.factory.BuildingBeanFactory;
import com.tcmis.client.het.beans.HetBuildingApplicationViewBean;
import com.tcmis.client.het.beans.HetPermitAreaBldgAppBean;
import com.tcmis.client.het.beans.HetPermitBean;
import com.tcmis.client.het.beans.HetPermitIdForMgmtBean;
import com.tcmis.client.het.beans.HetPermitMgmtInputBean;
import com.tcmis.client.het.beans.PermitManagementInputBean;
import com.tcmis.client.het.factory.HetPermitAreaBldgAppBeanFactory;
import com.tcmis.client.het.factory.HetPermitBeanFactory;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.FacilityBeanFactory;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

public class PermitManagementProcess extends GenericProcess {

	/**
	 * ******************************************************************
	 * Process for the PoExpediting Section
	 * 
	 * @version 1.0
	 *          *************************************************************
	 *          ****
	 */

	Log								log	= LogFactory.getLog(this.getClass());
	private HetPermitBeanFactory	permitFactory;

	public PermitManagementProcess(String client, Locale locale) {
		super(client, locale);
		permitFactory = new HetPermitBeanFactory(dbManager);
	}

	public PermitManagementProcess(String client) {
		super(client);
		permitFactory = new HetPermitBeanFactory(dbManager);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetPermitBean> getActivePermits(String companyId, String facilityId, BigDecimal workArea) throws BaseException {
		factory.setBeanObject(new HetPermitBean());
		StringBuilder query = new StringBuilder("select * from HET_PERMIT ");
		query.append("where STATUS = 'A' ");
		query.append("and COMPANY_ID = '").append(companyId).append("' ");
		query.append("and FACILITY_ID = '").append(facilityId).append("' ");
		if (workArea != null) {
			query.append("and BUILDING_ID in ( ");
			query.append("select B.BUILDING_ID from BUILDING B, Floor F, Room R, FAC_LOC_APP FLA ");
			query.append("where ");
			query.append("FLA.FACILITY_ID = '").append(facilityId).append("' and FLA.APPLICATION_id = ").append(workArea).append(" ");
			query.append("and R.ROOM_ID = FLA.ROOM_ID ");
			query.append("and F.FLOOR_ID = R.FLOOR_ID ");
			query.append("and B.BUILDING_ID = F.BUILDING_ID)");
		}
		query.append(" order by building_id, permit_name");
		return factory.selectQuery(query.toString());
	}

	public String getBuildingIdForWorkArea(String companyId, String facilityId, BigDecimal workArea) throws BaseException {
		StringBuilder query = new StringBuilder("select B.BUILDING_ID from BUILDING B, Floor F, Room R, FAC_LOC_APP FLA ");
		query.append("where ");
		query.append("FLA.FACILITY_ID = '").append(facilityId).append("' and FLA.APPLICATION_id = ").append(workArea).append(" ");
		query.append("and R.ROOM_ID = FLA.ROOM_ID ");
		query.append("and F.FLOOR_ID = R.FLOOR_ID ");
		query.append("and B.BUILDING_ID = F.BUILDING_ID");
		return factory.selectSingleValue(query.toString());
	}

	public String getAreaIdForBuilding(String companyId, String facilityId, String buildingId) throws BaseException {
		StringBuilder query = new StringBuilder("select AREA_ID from BUILDING where BUILDING_ID = ").append(buildingId);
		return factory.selectSingleValue(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<FacilityBean> getFacilities(PersonnelBean user) throws BaseException {
		factory.setBeanObject(new FacilityBean());
		StringBuilder query = new StringBuilder("select F.* from facility F, user_facility UF where upper(F.active) = 'Y' ");
		query.append(" and UF.COMPANY_ID = '").append(user.getCompanyId()).append("' ");
		query.append(" and UF.personnel_id = ").append(user.getPersonnelId());
		query.append(" and F.facility_id = UF.facility_id");
		return factory.selectQuery(query.toString());
	}

	public Collection<AreaBean> getAreas(PersonnelBean user, String facilityId) throws BaseException {
		AreaBeanFactory areaFactory = new AreaBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, user.getCompanyId());
		if (!StringHandler.isBlankString(facilityId)) {
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
		}
		else {
			StringBuilder nestedSelectSubstring = new StringBuilder("select F.facility_id from facility F, user_facility UF where upper(F.active) = 'Y' ");
			nestedSelectSubstring.append(" and UF.COMPANY_ID = '").append(user.getCompanyId()).append("' ");
			nestedSelectSubstring.append(" and UF.personnel_id = ").append(user.getPersonnelId());
			nestedSelectSubstring.append(" and F.facility_id = UF.facility_id");
			criteria.addNestedQuery("facilityId", nestedSelectSubstring.toString(), null);
		}
		SortCriteria sort = new SortCriteria("facilityId");
		sort.addCriterion("areaName");
		return areaFactory.select(criteria, sort);
	}

	public Collection<BuildingBean> getBuildings(String companyId, String facilityId, String areaId) throws BaseException {
		BuildingBeanFactory buildingFactory = new BuildingBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);

		if (!StringHandler.isBlankString(areaId)) {
			criteria.addCriterion("areaId", SearchCriterion.EQUALS, areaId);
		}
		else if (!StringHandler.isBlankString(facilityId)) {
			String nestedSelectSubstring = "select distinct area_id from area";
			SearchCriteria nestedCriteria = new SearchCriteria("facilityId", SearchCriterion.EQUALS, facilityId);
			nestedCriteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
			criteria.addNestedQuery("areaId", nestedSelectSubstring, nestedCriteria);
		}
		SortCriteria sort = new SortCriteria("areaId");
		sort.addCriterion("buildingName");
		return buildingFactory.select(criteria, sort);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetPermitBean> getSearchResult(PermitManagementInputBean input, PersonnelBean user) throws BaseException {
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, user.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());

		if (input.hasBuildingId()) {
			criteria.addCriterion("buildingId", SearchCriterion.EQUALS, input.getBuildingId());
		}
		else if (input.hasAreaId()) {
			String nestedSelectSubstring = "select distinct building_id from building";
			SearchCriteria nestedCriteria = new SearchCriteria("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
			nestedCriteria.addCriterion("areaId", SearchCriterion.EQUALS, input.getAreaId());
			criteria.addNestedQuery("buildingId", nestedSelectSubstring, nestedCriteria);
		}

		return permitFactory.select(criteria, null);
	}

	public String updatePermits(Collection<HetPermitBean> permits, PersonnelBean user) throws BaseException {
		String errMsg = null;
		HetPermitBeanFactory permitBeanFactory = new HetPermitBeanFactory(dbManager);

		try {
			for (HetPermitBean permit : permits) {
				if (permit.isNewPermit()) {
					permitBeanFactory.insert(permit);
				}
				else {
					permitBeanFactory.update(permit);
				}
			}
		}
		catch (Exception e) {
			log.error("Error updating permits", e);
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errMsg = library.getString("generic.error");
		}
		return errMsg;
	}

	public String deletePermits(Collection<HetPermitBean> permits, PersonnelBean user) throws BaseException {
		String errMsg = null;
		HetPermitBeanFactory permitBeanFactory = new HetPermitBeanFactory(dbManager);

		try {
			for (HetPermitBean permit : permits) {
				permitBeanFactory.delete(permit);
			}
		}
		catch (Exception e) {
			log.error("Error updating permits", e);
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errMsg = library.getString("generic.error");
		}
		return errMsg;
	}

	public ExcelHandler getExcelReport(Collection<HetPermitBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		// write column headers
		pw.addRow();
		/* Pass the header keys for the Excel. */
		String[] headerkeys = { "label.area", "label.building", "label.permit", "label.controldevice",  "label.active", "label.description" };
		/*
		 * This array defines the type of the excel column. 0 means default
		 * depending on the data type. pw.TYPE_PARAGRAPH defaults to 40
		 * characters. pw.TYPE_CALENDAR set up the date with no time.
		 * pw.TYPE_DATE set up the date with time.
		 */
		int[] types = { 0, 0, 0, 0, 0, 0 };

		/*
		 * This array defines the default width of the column when the Excel
		 * file opens. 0 means the width will be default depending on the data
		 * type.
		 */
		int[] widths = { 15, 20, 20, 20, 10, 30 };
		/*
		 * This array defines the horizontal alignment of the data in a cell. 0
		 * means excel defaults the horizontal alignemnt by the data type.
		 */
		int[] horizAligns = { 0, 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		/*
		 * pw.setColumnDigit(6, 2); pw.setColumnDigit(7, 2);
		 */

		for (HetPermitBean member : data) {
			pw.addRow();
			pw.addCell(member.getAreaName());
			pw.addCell(member.getBuildingName());
			pw.addCell(member.getPermitName());
			pw.addCell(member.getControlDevice());
			pw.addCell(member.getStatus());
			pw.addCell(member.getDescription());
		}
		return pw;
	}
}
