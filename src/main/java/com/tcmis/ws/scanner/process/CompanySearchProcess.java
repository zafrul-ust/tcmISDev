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

public class CompanySearchProcess extends BaseScannerSearchProcess {
	public CompanySearchProcess(String client) {
		super(client);
	}

	public JSONArray getCompanies(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new CompanyBean());
		setPagingConf(input);

		Collection<CompanyBean> beans = factory.selectQuery(getCompanySQL(false));
		for (CompanyBean bean : beans) {
			JSONObject company = new JSONObject();
			company.put("companyId", bean.getCompanyId());
			company.put("companyName", bean.getCompanyName());
			results.put(company);
		}

		totalCount = pageCount = beans.size();
		if (pageCount >= pageSize || page != 1) {
			String fullCount = factory.selectSingleValue(getCompanySQL(true));
			totalCount = Integer.parseInt(fullCount);
		}

		return results;
	}

	protected String getCompanySQL(boolean countOnly) throws JSONException {
		String coreSQL = "FROM ( ";
		coreSQL += " SELECT";
		coreSQL += "  c.company_Id,";
		coreSQL += "  c.company_Name";
		coreSQL += " FROM";
		coreSQL += "  company c";
		coreSQL += " WHERE";
		coreSQL += "  EXISTS ";
		coreSQL += "   (SELECT";
		coreSQL += "     NULL";
		coreSQL += "    FROM";
		coreSQL += "     fac_loc_app fla";
		coreSQL += "    WHERE";
		coreSQL += "     fla.company_id = c.company_id";
		coreSQL += "     AND fla.allow_stocking = 'Y'";
		coreSQL += "     AND fla.status = 'A')";
		coreSQL += " UNION";
		coreSQL += " SELECT";
		coreSQL += "  'TCMIS_ASSET_TRACKING' company_id,";
		coreSQL += "  'TCMIS_ASSET_TRACKING' company_name";
		coreSQL += " FROM";
		coreSQL += "  DUAL";
		coreSQL += " ) b";
		return getWrappedSQL("select b.*", coreSQL, "ORDER BY b.company_id", countOnly);
	}

}
