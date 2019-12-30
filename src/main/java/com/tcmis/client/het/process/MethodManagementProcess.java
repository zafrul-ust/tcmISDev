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
import com.tcmis.client.het.beans.FxMethodIdMgmtSearchBean;
import com.tcmis.client.het.beans.HetBuildingApplicationViewBean;
import com.tcmis.client.het.beans.HetMethodAreaBldgAppBean;
import com.tcmis.client.het.beans.PermitManagementInputBean;
import com.tcmis.client.het.beans.VvHetApplicationMethodBean;
import com.tcmis.client.het.factory.HetMethodAreaBldgAppBeanFactory;
import com.tcmis.client.het.factory.VvHetApplicationMethodBeanFactory;
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

public class MethodManagementProcess extends GenericProcess {

	/**
	 * ******************************************************************
	 * Process for the Method Management
	 * 
	 * @version 1.0
	 * *****************************************************************
	 */

	Log log = LogFactory.getLog(this.getClass());

	public MethodManagementProcess(String client, Locale locale) {
		super(client, locale);
	}

	public MethodManagementProcess(String client) {
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
	public Collection<FxMethodIdMgmtSearchBean> getSearchResult(PermitManagementInputBean input, PersonnelBean user) throws BaseException {

		factory.setBean(new FxMethodIdMgmtSearchBean());

		StringBuilder query = new StringBuilder("select * from table (PKG_HAZARDOUS_EMISSIONS_TRACK.FX_METHOD_ID_MGMT_SEARCH ('");
		query.append(user.getCompanyId()).append("','");
		query.append(input.getFacilityId()).append("',");
		query.append(StringHandler.nullIfEmpty(input.getAreaId())).append(",");
		query.append(StringHandler.nullIfEmpty(input.getBuildingId())).append(",");
		query.append(StringHandler.nullIfEmpty(input.getWorkArea()));

		query.append(")) order by method, area_name, building_name, application_desc");

		return factory.selectQuery(query.toString());
	}


	@SuppressWarnings("unchecked")
	public Collection<VvHetApplicationMethodBean> getMethodsForFacility(PermitManagementInputBean input, PersonnelBean user) throws BaseException {

		factory.setBean(new VvHetApplicationMethodBean());

		StringBuilder query = new StringBuilder("select * from vv_het_application_method where method_id in (select method_id from het_method_area_bldg_app where company_id = '");
		query.append(user.getCompanyId()).append("' and facility_id = '").append(input.getFacilityId()).append("')");

		return factory.selectQuery(query.toString());
	}
	
	public String updateMethod(Collection<FxMethodIdMgmtSearchBean> methods, PersonnelBean user) throws BaseException {
		String errMsg = null;
		VvHetApplicationMethodBeanFactory methodBeanFactory = new VvHetApplicationMethodBeanFactory(dbManager);
		HetMethodAreaBldgAppBeanFactory otherFactory = new HetMethodAreaBldgAppBeanFactory(dbManager);

		try {
			BigDecimal lastMethodId = null;
			FxMethodIdMgmtSearchBean lastMethod = null;
			for (FxMethodIdMgmtSearchBean inputBean : methods) {
				if (inputBean.isNewMethod()) {
					VvHetApplicationMethodBean method = createMethodBean(inputBean, user);
					methodBeanFactory.insert(method);
					lastMethodId = method.getMethodId();
				}
				else if (inputBean.isMethodModified() && inputBean.getMethodId() != null && !inputBean.getMethodId().equals(lastMethodId)) {
					methodBeanFactory.update(createMethodBean(inputBean, user));
				}

				if (inputBean.getMethodId() == null) {
					inputBean.setMethodId(lastMethodId);
				}
				else {
					lastMethodId = inputBean.getMethodId();
				}

				if (inputBean.isNewMethodRow() && !inputBean.isDeleted()) {
					otherFactory.insert(createHetMethodAreaBldgAppBean(inputBean, user));
				}
				else if (inputBean.isDeleted() && !inputBean.isNewMethodRow()) {
					otherFactory.delete(createHetMethodAreaBldgAppBean(inputBean, user));
				}
				// AFTER processing the last modification to a permit, check to see if it needs deleted.
				if (lastMethodId != null && !inputBean.getMethodId().equals(lastMethodId)) {
					if (!otherFactory.methodHasLocations(lastMethodId)) {
						methodBeanFactory.delete(createMethodBean(lastMethod, user));
					}
				}
				lastMethod = inputBean;
			}
			if (!otherFactory.methodHasLocations(lastMethodId)) {
				methodBeanFactory.delete(createMethodBean(lastMethod, user));
			}
		}
		catch (Exception e) {
			log.error("Error updating methods", e);
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errMsg = library.getString("generic.error");
		}
		return errMsg;
	}


	private VvHetApplicationMethodBean createMethodBean(FxMethodIdMgmtSearchBean inputBean, PersonnelBean user) throws BeanCopyException {
		VvHetApplicationMethodBean method = new VvHetApplicationMethodBean();
		BeanHandler.copyAttributes(inputBean, method);
		return method;
	}

	private HetMethodAreaBldgAppBean createHetMethodAreaBldgAppBean(FxMethodIdMgmtSearchBean inputBean, PersonnelBean user) throws BeanCopyException {
		HetMethodAreaBldgAppBean method = new HetMethodAreaBldgAppBean();
		BeanHandler.copyAttributes(inputBean, method);
		return method;
	}


	public ExcelHandler getExcelReport(Collection<FxMethodIdMgmtSearchBean> data, Locale locale) throws NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();
		//write column headers
		pw.addRow();
		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.code", "label.method", "label.area", "label.building", "label.workarea" };
		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, 0, 0, 0, 0 };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 10, 30, 20, 30, 30 };
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0 };

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		// format the numbers to the special columns
		/*pw.setColumnDigit(6, 2);
		pw.setColumnDigit(7, 2);*/

		for (FxMethodIdMgmtSearchBean member : data) {
			pw.addRow();
			pw.addCell(member.getMethodCode());
			pw.addCell(member.getMethod());
			pw.addCell(member.getAreaId().intValue() == -1 ? "All" : member.getAreaName());			
			pw.addCell(member.getBuildingId().intValue() == -1 ? "All" : member.getBuildingName());			
			pw.addCell(member.getApplicationId().intValue() == -1 ? "All" : member.getApplicationDesc());			
		}
		return pw;
	}
}
