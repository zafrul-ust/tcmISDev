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
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class FacilitySearchProcess extends BaseScannerSearchProcess {
	public FacilitySearchProcess(String client) {
		super(client);
	}

	public boolean isFacilityActiveForScanning(JSONObject input) throws BaseException, JSONException {
		factory.setBean(new Facility());

		@SuppressWarnings("unchecked")
		Collection<Facility> beans = factory.selectQuery("select facility_Id from fac_loc_app fla where fla.company_id = '" +
				input.getString("companyId") + "' AND facility_id = '" + input.getString("facilityId") + "'" +
				" and status = 'A' and allow_stocking = 'Y' and rownum = 1");

		return beans.isEmpty() ? false : true;
	}

	public JSONArray getFacilities(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Facility());
		setPagingConf(input);

		if ("TCMIS_ASSET_TRACKING".equals(input.get("companyId"))) {
			JSONObject facility = new JSONObject();
			facility.put("companyId", input.get("companyId"));
			facility.put("facilityId", "CYLINDER");
			facility.put("facilityName", "CYLINDER");
			results.put(facility);
			totalCount = pageCount = 1;
		}
		else {
		@SuppressWarnings("unchecked")
		Collection<Facility> beans = factory.selectQuery(getSQL(input, false));
		for (Facility bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}
		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getSQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}
		}
		return results;
	}

	protected String getSQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT f.company_id, f.facility_id, fx_facility_name (f.facility_id, f.company_id) facility_name ";
		StringBuilder body = new StringBuilder("FROM customer.facility f WHERE EXISTS ");
		body.append(" (select null from fac_loc_app fla where fla.company_id = '");
		body.append(input.get("companyId"));
		body.append("' and f.facility_id = fla.facility_id and f.company_id = fla.company_id AND fla.allow_stocking = 'Y' AND fla.status = 'A')");
		return getWrappedSQL(select, body.toString(), "ORDER BY f.facility_id", countOnly);

	}

}
