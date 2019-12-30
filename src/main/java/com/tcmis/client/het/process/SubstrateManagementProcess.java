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
import com.tcmis.client.het.beans.HetSubstrateAreaBldgAppBean;
import com.tcmis.client.het.beans.HetSubstrateIdForMgmtBean;
import com.tcmis.client.het.beans.PermitManagementInputBean;
import com.tcmis.client.het.beans.VvHetApplicationMethodBean;
import com.tcmis.client.het.beans.VvHetSubstrateBean;
import com.tcmis.client.het.factory.HetSubstrateAreaBldgAppBeanFactory;
import com.tcmis.client.het.factory.VvHetSubstrateBeanFactory;
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

public class SubstrateManagementProcess extends GenericProcess {

	/**
	 * ******************************************************************
	 * Process for the Substrate Management
	 * 
	 * @version 1.0
	 * *****************************************************************
	 */

	Log log = LogFactory.getLog(this.getClass());

	public SubstrateManagementProcess(String client, Locale locale) {
		super(client, locale);
	}

	public SubstrateManagementProcess(String client) {
		super(client);
	}


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


	@SuppressWarnings("unchecked")
	public Collection<BuildingBean> getBuildingsForArea(String companyId,  String areaId) throws BaseException {
		BuildingBeanFactory buildingFactory = new BuildingBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		if (!StringHandler.isBlankString(areaId)) {
			criteria.addCriterion("areaId", SearchCriterion.EQUALS, areaId);
		}
		SortCriteria sort = new SortCriteria("areaId");
		sort.addCriterion("buildingName");
		return buildingFactory.select(criteria, sort);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetBuildingApplicationViewBean> getWorkAreasForBuilding(String companyId,  String facilityId, String buildingId) throws BaseException {
		factory.setBean(new HetBuildingApplicationViewBean());
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		if (!StringHandler.isBlankString( facilityId)) {
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
		}
		if (!StringHandler.isBlankString( buildingId)) {
			criteria.addCriterion("buildingId", SearchCriterion.EQUALS, buildingId);
		}
		SortCriteria sort = new SortCriteria("facilityId");
		sort.addCriterion("buildingId");
		sort.addCriterion("application");
		return factory.select(criteria, sort, "HET_BUILDING_APPLICATION_VIEW");
	}

	@SuppressWarnings("unchecked")
	public Collection<HetSubstrateIdForMgmtBean> getSearchResult(PermitManagementInputBean input, PersonnelBean user) throws BaseException {

		factory.setBean(new HetSubstrateIdForMgmtBean());

		StringBuilder query = new StringBuilder("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.FX_SUBSTRATE_ID_MGMT_SEARCH ('");
		query.append(user.getCompanyId()).append("','");
		query.append(input.getFacilityId()).append("',");
		query.append(StringHandler.nullIfEmpty(input.getAreaId())).append(",");
		query.append(StringHandler.nullIfEmpty(input.getBuildingId())).append(",");
		query.append(StringHandler.nullIfEmpty(input.getWorkArea()));

		query.append(")) order by substrate, area_name, building_name, application_desc");

		return factory.selectQuery(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<VvHetSubstrateBean> getSubstratesForFacility(PermitManagementInputBean input, PersonnelBean user) throws BaseException {

		factory.setBean(new VvHetSubstrateBean());

		StringBuilder query = new StringBuilder("select * from vv_het_substrate where substrate_id in (select substrate_id from het_method_area_bldg_app where company_id = '");
		query.append(user.getCompanyId()).append("' and facility_id = '").append(input.getFacilityId()).append("')");

		return factory.selectQuery(query.toString());
	}
	
	public String updateSubstrate(Collection<HetSubstrateIdForMgmtBean> substrates, PersonnelBean user) throws BaseException {
		String errMsg = null;
		VvHetSubstrateBeanFactory substrateBeanFactory = new VvHetSubstrateBeanFactory(dbManager);
		HetSubstrateAreaBldgAppBeanFactory substrateAreaRelationshipFactory = new HetSubstrateAreaBldgAppBeanFactory(dbManager);

		try {
			BigDecimal lastSubstrateId = null;
			HetSubstrateIdForMgmtBean lastSubstrate = null;
			for (HetSubstrateIdForMgmtBean inputSubstrate : substrates) {
				if (inputSubstrate.isNewSubstrate()) {
					VvHetSubstrateBean substrate = createSubstrateBean(inputSubstrate, user);
					substrateBeanFactory.insert(substrate);
					lastSubstrateId = substrate.getSubstrateId();
				}
				else if (inputSubstrate.isSubstrateModified() && inputSubstrate.getSubstrateId() != null && !inputSubstrate.getSubstrateId().equals(lastSubstrateId)) {
					substrateBeanFactory.update(createSubstrateBean(inputSubstrate, user));
				}

				if (inputSubstrate.getSubstrateId() == null) {
					inputSubstrate.setSubstrateId(lastSubstrateId);
				}
				else {
					lastSubstrateId = inputSubstrate.getSubstrateId();
				}

				if (inputSubstrate.isNewSubstrateRow() && !inputSubstrate.isDeleted()) {
					substrateAreaRelationshipFactory.insert(createHetSubstrateAreaBldgAppBean(inputSubstrate, user));
				}
				else if (inputSubstrate.isDeleted() && !inputSubstrate.isNewSubstrateRow()) {
					substrateAreaRelationshipFactory.delete(createHetSubstrateAreaBldgAppBean(inputSubstrate, user));
				}
				lastSubstrate = inputSubstrate;
			}
			
			// Delete orphaned substrates
			for (HetSubstrateIdForMgmtBean inputSubstrate : substrates) {
					if (inputSubstrate.isDeleted() && !inputSubstrate.isNewSubstrateRow() && !substrateAreaRelationshipFactory.substrateHasLocations(inputSubstrate.getSubstrateId())) {
						substrateBeanFactory.delete(createSubstrateBean(inputSubstrate, user));
					}
			}

		}
		catch (Exception e) {
			log.error("Error updating substrates", e);
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errMsg = library.getString("generic.error");
		}
		return errMsg;
	}

	private VvHetSubstrateBean createSubstrateBean(HetSubstrateIdForMgmtBean inputBean, PersonnelBean user) throws BeanCopyException {
		VvHetSubstrateBean substrate = new VvHetSubstrateBean();
		BeanHandler.copyAttributes(inputBean, substrate);
		return substrate;
	}

	private HetSubstrateAreaBldgAppBean createHetSubstrateAreaBldgAppBean(HetSubstrateIdForMgmtBean inputBean, PersonnelBean user) throws BeanCopyException {
		HetSubstrateAreaBldgAppBean substrate = new HetSubstrateAreaBldgAppBean();
		BeanHandler.copyAttributes(inputBean, substrate);
		return substrate;
	}


	public ExcelHandler getExcelReport(Collection<HetSubstrateIdForMgmtBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.code", "label.substrate", "label.area", "label.building", "label.workarea" };
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0, 0 };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 10, 30, 15, 15 , 35  };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		/*pw.setColumnDigit(6, 2);
		pw.setColumnDigit(7, 2);*/

		for (HetSubstrateIdForMgmtBean member : data) {
			pw.addRow();
			pw.addCell(member.getSubstrateCode());
			pw.addCell(member.getSubstrate());
			pw.addCell(member.getAreaId().intValue() == -1 ? "All" : member.getAreaName());			
			pw.addCell(member.getBuildingId().intValue() == -1 ? "All" : member.getBuildingName());			
			pw.addCell(member.getApplicationId().intValue() == -1 ? "All" : member.getApplicationDesc());			
		}
		return pw;
	}
}
