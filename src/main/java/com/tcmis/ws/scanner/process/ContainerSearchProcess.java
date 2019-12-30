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

public class ContainerSearchProcess extends BaseScannerSearchProcess {
	public ContainerSearchProcess(String client) {
		super(client);
	}


	public JSONArray getContainers(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Receipt());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<Receipt> beans = factory.selectQuery(getContainerSQL(input, false));
		for (Receipt bean : beans) {
			results.put(BeanHandler.getJsonObject(bean, false));
		}

		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getContainerSQL(input, true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getContainerSQL(JSONObject input, boolean countOnly) throws JSONException {
		String select = "SELECT receipt_id, container_number, min(issue_id)";
		StringBuilder body = new StringBuilder(" FROM   customer.container_scan_put_away cspa"); 
		body.append(" WHERE   cspa.processing_status = 'PROCESSED'");
		body.append("  AND cspa.company_id = '").append(input.getString("companyId")).append("'");
		body.append("  AND cspa.data_source != 'P_AUTO_TRANSFER_CONTAINER'");
		if (input.has("filterDate")) {
			body.append(" AND cspa.put_away_datetime > ").append(DateHandler.getOracleToDateFunction((Date) input.get("filterDate")));
		}
		body.append("  AND (EXISTS");
		body.append("   (SELECT   NULL");
		body.append("    FROM   issue s, request_line_item rli");
		body.append("    WHERE cspa.issue_id = s.issue_id");
		body.append("     AND s.company_id = rli.company_id");
		body.append("     AND s.pr_number = rli.pr_number");
		body.append("     AND s.line_item = rli.line_item");
		body.append("     AND rli.company_id = '").append(input.getString("companyId")).append("'");
		body.append("     AND rli.facility_id = '").append(input.getString("facilityId")).append("')");
		body.append("   OR EXISTS");
		body.append("    (SELECT   NULL");
		body.append("     FROM   issue s,");
		body.append("      inventory_transfer_request itr,");
		body.append("      fac_loc_app fla");
		body.append("     WHERE cspa.issue_id = s.issue_id");
		body.append("      AND s.pr_number = itr.transfer_request_id");
		body.append("      AND itr.destination_inventory_group = fla.stocking_inventory_group");
		body.append("      AND fla.company_id = '").append(input.getString("companyId")).append("'");
		body.append("      AND fla.facility_id = '").append(input.getString("facilityId")).append("'))");
		body.append(" GROUP BY receipt_id, container_number");
		
		return getWrappedSQL(select, body.toString(), "ORDER BY cspa.receipt_id, cspa.container_number", countOnly);
		
	}

}
