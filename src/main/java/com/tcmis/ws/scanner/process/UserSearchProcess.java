package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PageBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.GHSLabelReqsBean;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.Receipt;
import com.tcmis.ws.scanner.beans.UserCompanyFacilityBean;
import com.tcmis.ws.scanner.beans.UserHubInventoryGroup;
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class UserSearchProcess extends BaseScannerSearchProcess {
	public UserSearchProcess(String client) {
		super(client);
	}

	public JSONArray getUsers(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new UserCompanyFacilityBean());
		setPagingConf(input);

		StringBuilder query = new StringBuilder("select gp.logon_id, gp.personnel_id, gp.last_name, gp.first_name, gp.password AS credentials, gp.password_expire_date AS credential_expiration, ugm.*");
		query.append(" FROM user_group_member ugm, global.personnel gp");
		query.append(" WHERE ugm.user_group_id = 'Scanner Fucntions'");
		query.append(" and gp.personnel_id = ugm.personnel_id");
		query.append(" AND exists (select null from fac_loc_app fla");
		query.append("   where fla.facility_id = ugm.facility_id");
		query.append("   AND fla.company_id = ugm.company_id");
		query.append("   AND fla.allow_stocking = 'Y'");
		query.append("   AND fla.status = 'A')");
		query.append(" order by ugm.personnel_id, ugm.company_id, ugm.facility_id");

		Collection<UserCompanyFacilityBean> beans = factory.selectQuery(query.toString());
		JSONObject user = null;
		JSONObject companies = null;
		JSONArray facilities = null;
		String currentUser = null;
		String currentCompany = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);
		for (UserCompanyFacilityBean bean : beans) {
			if (!bean.getLogonId().equals(currentUser)) {
				currentUser = bean.getLogonId();
				currentCompany = bean.getCompanyId();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				pageCount++;
				user = BeanHandler.getJsonObject(bean);
				companies = new JSONObject();
				facilities = new JSONArray();
				user.put("companies", companies);
				companies.put(currentCompany, facilities);
				results.put(user);
			}
			else if (!bean.getCompanyId().equals(currentCompany)) {
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				currentCompany = bean.getCompanyId();
				facilities = new JSONArray();
				companies.put(currentCompany, facilities);
			}
			if (totalCount < currentPageStart || totalCount > currentPageEnd) {
				continue;
			}
			facilities.put(bean.getFacilityId());
		}

		return results;
	}

	public JSONArray getReceivingUsers(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new UserHubInventoryGroup());
		setPagingConf(input);

		StringBuilder query = new StringBuilder("SELECT gp.logon_id, gp.personnel_id, gp.last_name, gp.first_name, gp.password AS credentials, gp.password_expire_date AS credential_expiration, igd.hub, ugmi.inventory_group");
		query.append(" FROM user_group_member_ig ugmi, global.personnel gp, inventory_group_definition igd");
		query.append(" WHERE   ugmi.user_group_id IN ('Receiving', 'ReceivingQC')");
		query.append(" AND gp.personnel_id = ugmi.personnel_id");
		query.append(" AND igd.inventory_group = ugmi.inventory_group");
		query.append(" GROUP BY gp.logon_id, gp.personnel_id, gp.last_name, gp.password, gp.password_expire_date, gp.first_name, igd.hub, ugmi.inventory_group");
		query.append(" ORDER BY gp.personnel_id, igd.hub, ugmi.inventory_group");

		Collection<UserHubInventoryGroup> beans = factory.selectQuery(query.toString());
		JSONObject user = null;
		JSONArray hubs = null;
		JSONObject hub = null;
		JSONArray inventoryGroups = null;
		String currentUser = null;
		String currentHub = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);
		for (UserHubInventoryGroup bean : beans) {
			if (!bean.getLogonId().equals(currentUser)) {
				currentUser = bean.getLogonId();
				currentHub = bean.getHub();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				pageCount++;
				user = BeanHandler.getJsonObject(bean);
				hubs = new JSONArray();
				hub = new JSONObject();
				inventoryGroups = new JSONArray();
				user.put("hubs", hubs);
				currentHub = bean.getHub();
				hub.put("hub", Integer.parseInt(currentHub));
				hub.put("inventoryGroups", inventoryGroups);
				hubs.put(hub);
				results.put(user);
			}
			else if (!bean.getHub().equals(currentHub)) {
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				hub = new JSONObject();
				currentHub = bean.getHub();
				hub.put("hub", Integer.parseInt(currentHub));
				inventoryGroups = new JSONArray();
				hub.put("inventoryGroups", inventoryGroups);
				hubs.put(hub);
			}
			if (totalCount < currentPageStart || totalCount > currentPageEnd) {
				continue;
			}
			inventoryGroups.put(bean.getInventoryGroup());
		}

		return results;
	}
}
