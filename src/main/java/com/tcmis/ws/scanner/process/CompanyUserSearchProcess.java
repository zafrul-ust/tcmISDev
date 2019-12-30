package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.ws.scanner.beans.UserCompanyFacilityBean;

public class CompanyUserSearchProcess extends BaseScannerSearchProcess {

	private JSONObject	user					= null;
	private JSONArray	companies				= null;
	private JSONArray	facilities				= null;
	private JSONArray	inventoryGroups			= null;
	private String		currentUser				= null;
	private String		currentCompanyId		= null;
	private JSONObject	currentCompany			= null;
	private String		currentFacilityId		= null;
	private JSONObject	currentFacility			= null;
	private JSONArray	facilityPermissions		= null;
	private String		currentInventoryGroupId	= null;
	private JSONObject	currentInventoryGroup	= null;
	private JSONArray	igPermissions			= null;

	public CompanyUserSearchProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	private Collection<UserCompanyFacilityBean> getUsers(Collection<UserCompanyFacilityBean> personnelIds) throws BaseException, JSONException {
		Vector<UserCompanyFacilityBean> users = new Vector<UserCompanyFacilityBean>();
		int count = 0;
		StringBuilder ids = new StringBuilder();
		for (UserCompanyFacilityBean user : personnelIds) {
			count++;
			if (count > 1) {
				ids.append(",");
			}
			ids.append(user.getPersonnelId());
			if (count == 100) {
				users.addAll(factory.selectQuery(getSQL(ids.toString())));
				count = 0;
				ids.setLength(0);
			}
		}
		if (count > 0) {
			users.addAll(factory.selectQuery(getSQL(ids.toString())));
		}

		return users;
	}

	public JSONArray getCompanyUsers(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new UserCompanyFacilityBean());
		setPagingConf(input);

		Collection<UserCompanyFacilityBean> personnelIds = factory.selectQuery(getCoreSQL(input, false));

		@SuppressWarnings("unchecked")
		Collection<UserCompanyFacilityBean> beans = getUsers(personnelIds);

		for (UserCompanyFacilityBean bean : beans) {
			if (!bean.getLogonId().equals(currentUser)) {
				currentUser = bean.getLogonId();
				companies = new JSONArray();

				doNewCompany(bean);

				bean.setFacilityId(null);
				bean.setHub(null);
				bean.setInventoryGroup(null);
				bean.setPermission(null);
				bean.setCompanyId(null);
				user = BeanHandler.getJsonObject(bean, false);
				user.put("companies", companies);
				results.put(user);
			}
			else if (!bean.getCompanyId().equals(currentCompanyId)) {
				doNewCompany(bean);
			}
			else if (!bean.getFacilityId().equals(currentFacilityId)) {
				doNewFacility(bean);
			}
			else if (!bean.getInventoryGroup().equals(currentInventoryGroupId)) {
				doNewInventoryGroup(bean);
			}
			else {
				addPermission(bean);
			}
		}

		totalCount = pageCount = results.length();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getCoreSQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	private void doNewCompany(UserCompanyFacilityBean bean) throws JSONException {
		currentCompanyId = bean.getCompanyId();
		currentCompany = new JSONObject();
		currentCompany.put("companyId", currentCompanyId);
		companies.put(currentCompany);
		facilities = new JSONArray();
		currentCompany.put("facilities", facilities);
		doNewFacility(bean);
	}

	private void doNewFacility(UserCompanyFacilityBean bean) throws JSONException {
		currentFacilityId = bean.getFacilityId();
		currentFacility = new JSONObject();
		currentFacility.put("facilityId", currentFacilityId);
		currentFacility.put("hub", bean.getHub());
		facilities.put(currentFacility);
		facilityPermissions = null;
		inventoryGroups = null;
		doNewInventoryGroup(bean);
	}

	private void doNewInventoryGroup(UserCompanyFacilityBean bean) throws JSONException {
		currentInventoryGroupId = bean.getInventoryGroup();
		if (!"NONE".equals(currentInventoryGroupId)) {
			currentInventoryGroup = new JSONObject();
			currentInventoryGroup.put("inventoryGroupId", currentInventoryGroupId);
			if (inventoryGroups == null) {
				inventoryGroups = new JSONArray();
				currentFacility.put("inventoryGroups", inventoryGroups);
			}
			inventoryGroups.put(currentInventoryGroup);
		}
		igPermissions = null;
		addPermission(bean);
	}

	private void addPermission(UserCompanyFacilityBean bean) throws JSONException {
		if ("NONE".equals(currentInventoryGroupId)) {
			if (facilityPermissions == null) {
				facilityPermissions = new JSONArray();
				currentFacility.put("permissions", facilityPermissions);
			}
			facilityPermissions.put(bean.getPermission());
		}
		else {
			if (igPermissions == null) {
				igPermissions = new JSONArray();
				currentInventoryGroup.put("permissions", igPermissions);
			}
			igPermissions.put(bean.getPermission());
		}
	}

	protected String getSQL(String personnelIdSQL) throws JSONException {

		StringBuilder body = new StringBuilder("SELECT distinct * FROM   (");
		body.append(" SELECT gp.logon_id,");
		body.append("        gp.personnel_id,");
		body.append("        gp.last_name,");
		body.append("        gp.first_name,");
		body.append("        gp.password AS credentials,");
		body.append("        gp.password_expire_date AS credential_expiration,");
		body.append("        igd.company_id,");
		body.append("        f.facility_id,");
		body.append("        igd.hub,");
		body.append("        ugmi.inventory_group,");
		body.append("        UGMI.USER_GROUP_ID AS permission");
		body.append(" FROM   user_group_member_ig ugmi,");
		body.append("        global.personnel gp,");
		body.append("        inventory_group_definition igd,");
		body.append("        user_company uc,");
		body.append("        facility f");
		body.append(" WHERE  gp.personnel_id IN (").append(personnelIdSQL).append(")");
		body.append("        AND ugmi.personnel_id = GP.PERSONNEL_ID");
		body.append("        AND ugmi.user_group_id IN ('Receiving', 'ReceivingQC')");
		body.append("        AND uc.personnel_id = ugmi.personnel_id");
		body.append("        AND UC.COMPANY_ID = UGMI.COMPANY_ID");
		body.append("        AND UC.STATUS = 'A'");
		body.append("        AND igd.inventory_group = ugmi.inventory_group");
		body.append("        AND f.branch_plant = igd.hub(+)");
		body.append(" UNION ALL");
		body.append(" SELECT gp.logon_id,");
		body.append("        gp.personnel_id,");
		body.append("        gp.last_name,");
		body.append("        gp.first_name,");
		body.append("        gp.password AS credentials,");
		body.append("        gp.password_expire_date AS credential_expiration,");
		body.append("        CASE ugm.company_id WHEN 'Radian' THEN 'HAAS' ELSE ugm.company_id END COMPANY_ID,");
		body.append("        ugm.facility_id,");
		body.append("        f.branch_plant hub,");
		body.append("        'NONE' AS inventory_group,");
		body.append("        UGM.USER_GROUP_ID AS permission");
		body.append(" FROM   user_group_member ugm,");
		body.append("        global.personnel gp,");
		body.append("        user_company uc,");
		body.append("        facility f");
		body.append(" WHERE  gp.personnel_id IN (").append(personnelIdSQL).append(")");
		body.append("        AND uc.personnel_id = ugm.personnel_id");
		body.append("        AND UC.STATUS = 'A'");
		body.append("        AND UC.COMPANY_ID = UGM.COMPANY_ID");
		body.append("        AND ugm.user_group_id IN ('Picking', 'Picking QC')");
		body.append("        AND ugm.personnel_id = GP.PERSONNEL_ID");
		body.append("        AND f.facility_id = ugm.facility_id");
		body.append(" UNION ALL");
		body.append(" SELECT  gp.logon_id,");
		body.append("        gp.personnel_id,");
		body.append("        gp.last_name,");
		body.append("        gp.first_name,");
		body.append("        gp.password AS credentials,");
		body.append("        gp.password_expire_date AS credential_expiration,");
		body.append("        CASE ugm.company_id WHEN 'Radian' THEN 'HAAS' ELSE ugm.company_id END COMPANY_ID,");
		body.append("        ugm.facility_id,");
		body.append("        f.branch_plant hub,");
		body.append("        'NONE' inventory_group,");
		body.append("        'Scan User' AS permission");
		body.append(" FROM   user_group_member ugm,");
		body.append("        global.personnel gp,");
		body.append("        customer.facility f");
		body.append(" WHERE  gp.personnel_id IN (").append(personnelIdSQL).append(")");
		body.append("        AND ugm.user_group_id = 'Scanner Fucntions'");
		body.append("        AND gp.personnel_id = ugm.personnel_id");
		body.append("        AND EXISTS (SELECT NULL");
		body.append("					FROM   fac_loc_app fla");
		body.append("			        WHERE  fla.facility_id = ugm.facility_id");
		body.append("					       AND fla.company_id = ugm.company_id");
		body.append("					       AND fla.allow_stocking = 'Y'");
		body.append("					       AND fla.status = 'A')");
		body.append("        AND f.facility_id = ugm.facility_id");
		body.append("        AND f.company_id = ugm.company_id");
		body.append(" UNION ALL");
		body.append(" SELECT gp.logon_id,");
		body.append("        gp.personnel_id,");
		body.append("        gp.last_name,");
		body.append("        gp.first_name,");
		body.append("        gp.password AS credentials,");
		body.append("        gp.password_expire_date AS credential_expiration,");
		body.append("        'TCMIS_ASSET_TRACKING' AS company_id,");
		body.append("        'CYLINDER' AS facility_id,");
		body.append("        NULL AS hub,");
		body.append("        'NONE' AS inventory_group,");
		body.append("        'CylinderTracking' AS permission");
		body.append(" FROM   user_supplier_location us,");
		body.append("        global.personnel gp");
		body.append(" WHERE  gp.personnel_id IN (").append(personnelIdSQL).append(")");
		body.append("        AND us.personnel_id = gp.personnel_id");
		body.append(") ORDER BY");
		body.append("       personnel_id,");
		body.append("	   company_id,");
		body.append("	   facility_id,");
		body.append("	   inventory_group");
		return body.toString();
	}

	protected String getCoreSQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT gp.personnel_id ";
		StringBuilder body = new StringBuilder("FROM   global.personnel gp ");
		body.append(" WHERE   gp.password IS NOT NULL");
		body.append("   AND(EXISTS (SELECT   NULL"); // Receiving Users
		body.append("          FROM   user_group_member_ig ugmi,");
		body.append("            user_company uc");
		body.append("         WHERE       ugmi.personnel_id = gp.personnel_id");
		body.append("            AND ugmi.user_group_id IN ('Receiving', 'ReceivingQC')");
		body.append("            AND uc.personnel_id = ugmi.personnel_id");
		body.append("            AND UC.COMPANY_ID = UGMI.COMPANY_ID");
		body.append("            AND UC.STATUS = 'A')");
		body.append("       OR EXISTS (SELECT   NULL"); // CMS Scanner users
		body.append("          FROM   user_group_member ugm");
		body.append("         WHERE       ugm.personnel_id = gp.personnel_id");
		body.append("            AND ugm.user_group_id = 'Scanner Fucntions'");
		body.append("                AND EXISTS (SELECT   *");
		body.append("                    FROM   fac_loc_app fla");
		body.append("                   WHERE       fla.facility_id = ugm.facility_id");
		body.append("                      AND fla.company_id = ugm.company_id");
		body.append("                      AND fla.allow_stocking = 'Y'");
		body.append("                      AND fla.status = 'A') )");
		body.append("       OR EXISTS (SELECT   NULL"); // Hub Picking users
		body.append("          FROM   user_group_member ugm,");
		body.append("            user_company uc");
		body.append("         WHERE       ugm.personnel_id = gp.personnel_id");
		body.append("            AND ugm.user_group_id IN ('Picking', 'Picking QC')");
		body.append("            AND uc.personnel_id = ugm.personnel_id");
		body.append("            AND UC.STATUS = 'A'");
		body.append("            AND UC.COMPANY_ID = UGM.COMPANY_ID)");
		body.append("       OR EXISTS (SELECT   NULL"); // Cylinder Asset tracking users 
		body.append("          FROM   user_supplier_location us");
		body.append("         WHERE   us.personnel_id = gp.personnel_id) )");
		return getWrappedSQL(select, body.toString(), "ORDER BY gp.personnel_id", countOnly);

	}

}
