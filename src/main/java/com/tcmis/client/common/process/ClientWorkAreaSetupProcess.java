package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.client.catalog.factory.FacLocAppBeanFactory;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.common.beans.FacLocAppViewBean;
import com.tcmis.client.common.beans.FacilityDockBean;
import com.tcmis.client.common.beans.PrRulesViewBean;
import com.tcmis.client.common.beans.VvCompassPointBean;
import com.tcmis.client.common.beans.WorkAreaSearchTemplateInputBean;
import com.tcmis.client.common.beans.WorkAreaSetupInputBean;
import com.tcmis.client.common.factory.PersonnelFacAreaBldgRmOvBeanFactory;
import com.tcmis.common.admin.beans.InventoryGroupDefinitionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/*******************************************************************
 * 
 * @version 1.0
 * 
 * 
 ********************************************************************/

public class ClientWorkAreaSetupProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public ClientWorkAreaSetupProcess(String client, Locale locale) {
		super(client, locale);
	}

	public Collection getPrRulesForFacility(WorkAreaSearchTemplateInputBean bean) throws BaseException {
		factory.setBean(new PrRulesViewBean());
		String query = "select distinct prv.account_sys_id,prv.account_sys_label from pr_rules_view prv, invoice_group_facility igf where prv.facility_id = '" + bean.getFacilityId() + "'"
		+ " and prv.status = 'A' and prv.company_id = igf.company_id and prv.facility_id = igf.facility_id and prv.account_sys_id = igf.account_sys_id order by account_sys_id";
		return factory.selectQuery(query);
	}

	@SuppressWarnings("unchecked")
	public Collection<FacLocAppBean> getWorkAreaSearchResults(WorkAreaSearchTemplateInputBean input) throws BaseException {
		FacLocAppBeanFactory flaFactory = new FacLocAppBeanFactory(dbManager);

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, input.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		if (input.hasReportingEntityId()) {
			criteria.addCriterion("reportingEntityId", SearchCriterion.EQUALS, input.getReportingEntityId());
		}
		if (input.hasAreaId()) {
			criteria.addCriterion("areaId", SearchCriterion.IN, "'" + input.getAreaId().replace("|", "','") + "'");
		}

		if (input.hasBuildingId()) {
			criteria.addCriterion("buildingId", SearchCriterion.IN, "'" + input.getBuildingId().replace("|", "','") + "'");
		}

		if (input.hasDeptId()) {
			criteria.addCriterion("deptId", SearchCriterion.IN, "'" + input.getDeptId().replace("|", "','") + "'");
		}
		
		if (input.hasApplicationId()) {
			criteria.addCriterion("applicationId", SearchCriterion.IN, "'" + input.getApplicationId().replace("|", "','") + "'");
		}

		if (!"Y".equals(input.getShowInactive()) ) {
			criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
		}

		Vector<FacLocAppBean> sortUseCodeData = (Vector<FacLocAppBean>) flaFactory.select(criteria, null);

		for (FacLocAppBean b : sortUseCodeData) {
			if (b.getUseCodeData() != null && !b.getUseCodeData().equalsIgnoreCase("")) {
				String[] data = b.getUseCodeData().split(";");
				StringBuilder id = new StringBuilder();
				StringBuilder name = new StringBuilder();

				for (int i = 0; i < data.length;) {
					id.append(data[i++]);
					name.append(data[i++]);
					if (data.length > 2 && i != data.length) {
						id.append(",");
						name.append(", ");
					}
				}
				b.setUseCodeIdString(id.toString());
				b.setUseCodeName(name.toString());
			}
		}

		return sortUseCodeData;
	}

	public Collection<VvCompassPointBean> getCompassPoints() throws BaseException {
		factory.setBean(new VvCompassPointBean());
		return factory.select(null, null, "VV_COMPASS_POINT");
	}

	@SuppressWarnings("unchecked")
	public Collection<InventoryGroupDefinitionBean> getInventoryGroupsForFacility(WorkAreaSearchTemplateInputBean input) throws BaseException {
		String nestedSelectQuery = "select distinct inventory_group from facility_inventory_group";
		SearchCriteria nestedCriteria = new SearchCriteria("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addNestedQuery("inventoryGroup", nestedSelectQuery, nestedCriteria);

		return factory.setBean(new InventoryGroupDefinitionBean()).select(criteria, null, "INVENTORY_GROUP_DEFINITION");
	}

	private String getDefaultInventoryGroupForFacility(WorkAreaSearchTemplateInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT inventory_group_default from facility");
		query.append(" WHERE company_id = '").append(input.getCompanyId()).append("'");
		query.append(" AND facility_id = '").append(input.getFacilityId()).append("'");
		return factory.selectSingleValue(query.toString());
	}

	@SuppressWarnings("unchecked")
	public Collection<FacilityDockBean> getDocksForFacility(WorkAreaSearchTemplateInputBean input) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, input.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
		return factory.setBean(new FacilityDockBean()).select(criteria, null, "FACILITY_DOCK");
	}

	public String updateWorkAreaSetup(WorkAreaSearchTemplateInputBean input, Collection<WorkAreaSetupInputBean> updatedWorkAreas, PersonnelBean user) throws BaseException, Exception {
		String errMsg = null;
		FacLocAppBeanFactory flaFactory = new FacLocAppBeanFactory(dbManager);
		try {
			for (WorkAreaSetupInputBean workArea : updatedWorkAreas) {
				if (workArea.isNew()) {
					// Don't create new entry if they just made a new row and didn't enter anything
					if (workArea.hasDescription()) {
						// Get the work Area Id
						BigDecimal newApplicationId = new BigDecimal(dbManager.getOracleSequence("application_id_seq"));

						// Create a record in FacLocApp
						FacLocAppBean fla = new FacLocAppBean();
						// Set the fields from the GUI
						BeanHandler.copyAttributes(workArea, fla);
						fla.setCompanyId(input.getCompanyId());
						fla.setFacilityId(input.getFacilityId());
						fla.setApplication("" + newApplicationId.intValue());
                        //setting workArea.application to use later in application_use_group_member setup
                        workArea.setApplication("" + newApplicationId.intValue());
                        fla.setApplicationId(newApplicationId);

						// set the fields from the GUI that are not direct copies
						setBeanValuesBasedOnInput(workArea, fla);

						// If they don't have permissions, use the facilities default inventory group
						if (!user.getPermissionBean().hasFacilityPermission("InventoryGroupMgmt", input.getFacilityId(), input.getCompanyId())) {
							fla.setInventoryGroup(getDefaultInventoryGroupForFacility(input));
						}

						// Default values
						fla.setUseApprovalLimitsOption("N");
						fla.setDockDeliverToSelectionRule("Select Both");
						fla.setAllocateByDistance("N");
						if (fla.getPullWithinDaysToExpiration() == null) {
							fla.setPullWithinDaysToExpiration(new BigDecimal(0));
						}
						fla.setUpdatedBy(user.getPersonnelIdBigDecimal());
						flaFactory.insert(fla);
						String subject = "New Work Area: " + workArea.getApplicationDesc();
						StringBuilder message = new StringBuilder("New Work Area, ").append(workArea.getApplicationDesc());
						message.append(", was added by ").append(user.getFirstName()).append(" ").append(user.getLastName());
						BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
						bulkMailProcess.sendBulkEmail("NewWorkAreaNotification", input.getFacilityId(), null, input.getCompanyId(),subject, message.toString(), false);
					}
				}
				else {
					// Do the WorkArea  update, first get the existing cabinet
					SearchCriteria criteria = new SearchCriteria();
					criteria.addCriterion("companyId", SearchCriterion.EQUALS, input.getCompanyId());
					criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
					criteria.addCriterion("applicationId", SearchCriterion.EQUALS, workArea.getApplicationId().toPlainString());
					FacLocAppBean original = flaFactory.selectBean(criteria);

					// overwrite the fields from the GUI
					BeanHandler.copyAttributes(workArea, original);
					// set the fields from the GUI that are not direct copies
					setBeanValuesBasedOnInput(workArea, original);
					//default
					if (original.getPullWithinDaysToExpiration() == null) {
						original.setPullWithinDaysToExpiration(new BigDecimal(0));
					}
					original.setUpdatedBy(user.getPersonnelIdBigDecimal());
					flaFactory.update(original);

				}
				factory.deleteInsertUpdate("delete from application_use_group_member where COMPANY_ID ='" + workArea.getCompanyId() + "' and FACILITY_ID='" + input.getFacilityId() + "' and APPLICATION ='"
						+ workArea.getApplication() + "'");
				if (!StringHandler.isBlankString(workArea.getUseCodeIdString())) {
					String useCodeId = "'" + workArea.getUseCodeIdString().replace(",", "','") + "'";
					factory.deleteInsertUpdate("insert into application_use_group_member select company_id,facility_id,application_use_group_id,'" + workArea.getApplication()
							+ "' from application_use_group where company_id = '" + workArea.getCompanyId() + "' and facility_id = '"+input.getFacilityId()+"' and application_use_group_id in (" + useCodeId + ")");
				}

			}

		}
		catch (Exception e) {
			e.printStackTrace();
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errMsg = library.getString("generic.error");
		}
		return errMsg;
	}

	private void setBeanValuesBasedOnInput(WorkAreaSetupInputBean workArea, FacLocAppBean fla) {
		if (workArea.isDockFixed()) {
			fla.setDockSelectionRule("FIXED");
		}
		else if (!StringHandler.isBlankString(workArea.getLocationId())) {
			fla.setDockSelectionRule("DEFAULT");
		}
		else {
			fla.setDockSelectionRule("NO RULE");
		}
		if (workArea.isDeliveryPointFixed()) {
			fla.setDeliveryPointSelectionRule("FIXED");
		}
		else if (!StringHandler.isBlankString(workArea.getDeliveryPoint())) {
			fla.setDeliveryPointSelectionRule("DEFAULT");
		}
		else {
			fla.setDeliveryPointSelectionRule("NO RULE");
		}

		fla.setAllowStocking(workArea.isOrderingStockLevel() ? "Y" : "N");
		fla.setManualMrCreation(workArea.isOrderingManual() ? "Y" : "N");
		fla.setDropShip(workArea.isDropShip());
		fla.setOffsite(workArea.isOffsite());
	}

	public ExcelHandler getExcelReport(WorkAreaSearchTemplateInputBean inputBean, Locale locale, PersonnelBean personnelBean) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		Collection<FacLocAppBean> data = getWorkAreaSearchResults(inputBean);
		CatalogProcess catalogProcess = new CatalogProcess(getClient(), getLocale());
		boolean showHmrbFeatures = personnelBean.isFeatureReleased("HmrbTab", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showDefaultChargeType = personnelBean.isFeatureReleased("ShowChargeTypeDefault", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showIncludeExpired = personnelBean.isFeatureReleased("IncludeExpired", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showLocSection = personnelBean.isFeatureReleased("ShowLocSection", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showEmissionPoint = personnelBean.isFeatureReleased("ShowEmissionPoint", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showReportUsage = personnelBean.isFeatureReleased("ShowReportUsage", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showControlledColInWADefiniton = personnelBean.isFeatureReleased("ShowControlledColInWADefiniton", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showAlternateContact = personnelBean.isFeatureReleased("ShowAlternateContact", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showVocZone = personnelBean.isFeatureReleased("ShowVocZone", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showFlammabilityControlZone = personnelBean.isFeatureReleased("ShowFlammabilityControlZone", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean showReportInventory = personnelBean.isFeatureReleased("ShowReportInventory", inputBean.getFacilityId(),inputBean.getCompanyId());
		boolean allowSplitKit = catalogProcess.isAllowSplitKits(inputBean.getFacilityId());
		boolean useCodeRequired = false;
		if (!data.isEmpty())
			useCodeRequired = "Y".equals(((FacLocAppBean) ((Vector) data).firstElement()).isUseCodeRequired());
		pw.addTable();
		pw.addRow();

		ArrayList headerkeys = new ArrayList<String>();
		ArrayList widths = new ArrayList<Integer>();
		headerkeys.add("label.active");
		widths.add(0);
		headerkeys.add("label.workarea");
		widths.add(30);
		headerkeys.add("label.department");
		widths.add(15);
		headerkeys.add("label.dropship");
		widths.add(0);
		headerkeys.add("label.offsite");
		widths.add(0);
		headerkeys.add("label.workareagroup");
		widths.add(15);
		headerkeys.add("label.orderingmethodforstockedparts");
		widths.add(15);
		headerkeys.add("label.pulledwithindays");
		widths.add(0);
		headerkeys.add("label.daysbetweenscan");
		widths.add(0);
		headerkeys.add("label.area");
		widths.add(0);
		headerkeys.add("label.building");
		widths.add(0);
		headerkeys.add("label.floor");
		widths.add(0);
		headerkeys.add("label.room");
		widths.add(0);
		headerkeys.add("label.interiorexterior");
		widths.add(0);
		if (showHmrbFeatures) {
			headerkeys.add("label.compasspoint");
			widths.add(0);
			headerkeys.add("label.column");
			widths.add(0);
		}
		if (showLocSection) {
			headerkeys.add("label.section");
			widths.add(0);
		}
		if (showEmissionPoint) {
			headerkeys.add("label.emissionpoint");
			widths.add(0);
		}
		if (showReportUsage) {
			headerkeys.add("label.reportusage");
			widths.add(0);
		}
		if (showControlledColInWADefiniton) {
			headerkeys.add("label.controlled");
			widths.add(0);
		}
		if (showReportInventory) {
			headerkeys.add("label.reportinventory");
			widths.add(0);
		}
		headerkeys.add("label.inventorygroup");
		widths.add(30);
		headerkeys.add("label.accountsys");
		widths.add(0);
		if (showDefaultChargeType) {
			headerkeys.add("label.defaultchargetype");
			widths.add(0);
		}
		headerkeys.add("label.dockname");
		widths.add(0);
		headerkeys.add("label.dockfixed");
		widths.add(0);
		headerkeys.add("label.deliverypointname");
		widths.add(0);
		headerkeys.add("label.deliverypointfixed");
		widths.add(0);
		if (useCodeRequired) {
			headerkeys.add("label.approvalcode");
			widths.add(0);
		}
		if (allowSplitKit) {
			headerkeys.add("label.splitkits");
			widths.add(0);
		}
		if (showHmrbFeatures) {
			headerkeys.add("label.dailyusagelogging");
			widths.add(0);
			headerkeys.add("label.allowseparablemixture");
			widths.add(0);
		}
		if (showIncludeExpired) {
			headerkeys.add("label.includeexpiredmaterial");
			widths.add(0);
		}
		headerkeys.add("label.description");
		widths.add(0);
		headerkeys.add("label.contactname");
		widths.add(0);
		headerkeys.add("label.phone");
		widths.add(0);
		headerkeys.add("label.email");
		widths.add(0);
		if (showAlternateContact) {
			headerkeys.add("label.secondarycontactname");
			widths.add(0);
			headerkeys.add("label.secondaryphone");
			widths.add(0);
			headerkeys.add("label.secondaryemail");
			widths.add(0);
		}
		headerkeys.add("label.location");
		widths.add(0);
		headerkeys.add("label.customworkareaid");
		widths.add(10);
		if (showFlammabilityControlZone) {
			headerkeys.add("label.flammabilitycontrolzone");
			widths.add(0);
		}
		if (showVocZone) {
			headerkeys.add("label.voczone");
			widths.add(0);
		}

		String headerKeysArray[] = (String[]) headerkeys.toArray(new String[headerkeys.size()]);
		int widthsArray[] = new int[widths.size()];
		for (int i = 0; i < widths.size(); i++) {
			widthsArray[i] = (Integer) widths.get(i);
		}

		pw.applyColumnHeader(headerKeysArray, widthsArray, null, null);

		// now write data
		for (FacLocAppBean member : data) {
			pw.addRow();
			pw.addCell(member.getStatus());
			pw.addCell(member.getApplicationDesc());
			pw.addCell(member.getDeptName());
			pw.addCell(member.isDropShip() ? "Y" : "N");
			pw.addCell(member.isOffsite() ? "Y" : "N");
			pw.addCell(member.getWorkAreaGroupDesc());
			if (member.getAllowStocking().equalsIgnoreCase("Y"))
				if (member.getManualMrCreation().equalsIgnoreCase("Y"))
					pw.addCell("MANUAL & STOCK LEVEL");
				else
					pw.addCell("STOCK LEVEL");
			else
				pw.addCell("MANUAL ORDERING");
			pw.addCell(member.getPullWithinDaysToExpiration());
			pw.addCell(member.getDaysBetweenScan());
			pw.addCell(member.getAreaName());
			pw.addCell(member.getBuildingName());
			pw.addCell(member.getFloor());
			pw.addCell(member.getRoomName());
			if (member.getFloorId() == null) {
				pw.addCell("");
			}
			else if (member.isInterior())
				pw.addCell("I");
			else
				pw.addCell("E");
			if (showHmrbFeatures) {
				pw.addCell(member.getCompassPoint());
				pw.addCell(member.getLocColumn());
			}
			if (showLocSection) {
				pw.addCell(member.getLocSection());
			}
			if (showEmissionPoint) {
				pw.addCell(member.getEmissionPoint());
			}
			if (showReportUsage) {
				pw.addCell((member.isReportUsage()) ? "Y" : "N");
			}
			if (showControlledColInWADefiniton) {
				pw.addCell((member.isSpecificUseApprovalRequired()) ? "Y" : "N");
			}
			if (showReportInventory) {
				pw.addCell((member.isReportInventory()) ? "Y" : "N");
			}
			pw.addCell(member.getInventoryGroupName());
			pw.addCell(member.getStockingAccountSysId());
			if (showDefaultChargeType) {
				if ("d".equals(member.getChargeTypeDefault()))
					pw.addCell(library.getString("label.direct"));
				else if ("i".equals(member.getChargeTypeDefault()))
					pw.addCell(library.getString("label.indirect"));
				else
					pw.addCell("");
			}

			pw.addCell(member.getLocationId());
			pw.addCell((member.isDockSelectionFixed()) ? "Y" : "N");
			pw.addCell(member.getDeliveryPointDesc());
			pw.addCell((member.isDeliveryPointSelectionFixed()) ? "Y" : "N");
			if (useCodeRequired)
				pw.addCell(member.getUseCodeName());
			if (allowSplitKit)
				pw.addCell(member.getUseCodeName());
			if (showHmrbFeatures) {
				pw.addCell((member.isOverrideUsageLogging() ? "Y" : "N"));
				pw.addCell((member.isAllowSplitKits() ? "Y" : "N"));
			}

			if (showIncludeExpired) {
				pw.addCell((member.isIncludeExpiredMaterial() ? "Y" : "N"));
			}
			pw.addCell(member.getLocationDetail());
			pw.addCell(member.getContactName());
			pw.addCell(member.getContactPhone());
			pw.addCell(member.getContactEmail());
			if (showAlternateContact) {
				pw.addCell(member.getContact2Name());
				pw.addCell(member.getContact2Phone());
				pw.addCell(member.getContact2Email());
			}
			pw.addCell(member.getLocationDetail());
			pw.addCell(member.getCustomerCabinetId());
			if (showFlammabilityControlZone) 
				pw.addCell(member.getFlammabilityControlZoneDesc());
			if (showVocZone) 
				pw.addCell(member.getVocZoneDescription());
		}
		return pw;
	} //end of method

	public Collection getFacilityAreaBldgRm(BigDecimal personnelId) throws BaseException {
        PersonnelFacAreaBldgRmVwProcess process = new PersonnelFacAreaBldgRmVwProcess(getClient(),locale.toString());
        return process.getFacilityAreaBldgRm(personnelId,"");
    }

	public Collection getDept(String facId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FacLocAppBean());
		String query = "select distinct d.dept_id, d.dept_name from DEPT d, FAC_LOC_APP fla where d.dept_id = fla.dept_id and fla.facility_id = '"+facId+"'";
		return factory.selectQuery(query);
	}

	public Collection getSearchHistory(WorkAreaSearchTemplateInputBean input,PersonnelBean user) throws BaseException {
		factory.setBeanObject(new FacLocAppViewBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, user.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		criteria.addCriterion("applicationId", SearchCriterion.EQUALS, input.getApplicationId());

		SortCriteria sort = new SortCriteria();
		sort.addCriterion("updatedOn", "desc nulls last");

		Vector<FacLocAppViewBean>sortUseCodeData = (Vector) factory.select(criteria, sort, "fac_loc_app_view");

		for (FacLocAppViewBean b : sortUseCodeData) {
			if (b.getUseCodeData() != null && !b.getUseCodeData().equalsIgnoreCase("")) {
				String[] data = b.getUseCodeData().split(";");
				StringBuilder id = new StringBuilder();
				StringBuilder name = new StringBuilder();

				for (int i = 0; i < data.length;) {
					id.append(data[i++]);
					name.append(data[i++]);
					if (data.length > 2 && i != data.length) {
						id.append(",");
						name.append(", ");
					}
				}
				b.setUseCodeIdString(id.toString());
				b.setUseCodeName(name.toString());
			}
		}
		return sortUseCodeData;
	}

}
