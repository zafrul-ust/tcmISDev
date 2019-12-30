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

public class BinItemSearchProcess extends BaseScannerSearchProcess {
	public BinItemSearchProcess(String client) {
		super(client);
	}

	public JSONArray getBinItems(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new BinItem());
		setPagingConf(input);

		StringBuilder query = new StringBuilder(getSQL(input, false));

		Collection<BinItem> beans = factory.selectQuery(query.toString());
		for (BinItem bean : beans) {
			results.put(BeanHandler.getJsonObject(bean));
		}
		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getSQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getSQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT CABINET_BIN_SCAN_DETAIL_VIEW.*, CABINET_BIN_SCAN_DETAIL_VIEW.date_modified last_modified";
		StringBuilder body = new StringBuilder("FROM CABINET_BIN_SCAN_DETAIL_VIEW");
		body.append(" WHERE company_id = '");
		body.append(input.get("companyId"));
		body.append("' AND facility_id = '");
		body.append(input.get("facilityId"));
		body.append("'");
		if (input.has("filterDate")) {
			body.append(" AND date_modified >= ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}

		return getWrappedSQL(select, body.toString(), "ORDER BY application_id, bin_id", countOnly);

	}
	
//	protected String getSQL(JSONObject input, boolean countOnly) throws JSONException {
//		String select = "SELECT fla.company_id, fla.application_id, b.bin_id, b.bin_name, cpig.item_id, b.status," 
//				+ " (CASE b.count_type WHEN 'PART' THEN 'I' WHEN 'AutomaticRefill' THEN 'I' ELSE SUBSTR (b.count_type, 1, 1) END) count_type,"
//				+ " fla.pull_within_days_to_expiration, fla.include_expired_material, fla.application_desc, i.item_desc, b.size_unit default_size_unit";
//		StringBuilder body = new StringBuilder("FROM customer.catalog_part_item_group cpig, cabinet_part_inventory b, fac_loc_app fla, item i");
//		body.append(" WHERE i.item_id  = cpig.item_id");
//		body.append(" AND cpig.status != 'M'");
//		body.append(" AND cpig.company_id = b.catalog_company_id");
//		body.append(" AND cpig.catalog_id = b.catalog_id");
//		body.append(" AND cpig.cat_part_no = b.cat_part_no");
//		body.append(" AND cpig.part_group_no = b.part_group_no");
//		body.append(" AND b.company_id = fla.company_id");
//		body.append(" AND b.facility_id = fla.facility_id");
//		body.append(" AND b.application = fla.application");
//		body.append(" AND fla.status = 'A'");
//		body.append(" AND fla.allow_stocking = 'Y'");
//		body.append(" AND fla.company_id = '");
//		body.append(input.get("companyId"));
//		body.append("' AND fla.facility_id = '");
//		body.append(input.get("facilityId"));
//		body.append("'");
//		return getWrappedSQL(select, body.toString(), "ORDER BY fla.application_id, b.bin_id", countOnly);
//
//	}

}
