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
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.GHSLabelReqsBean;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.Receipt;
import com.tcmis.ws.scanner.beans.Supplier;
import com.tcmis.ws.scanner.beans.UserCompanyFacilityBean;
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class SupplierSearchProcess extends BaseScannerSearchProcess {
	public SupplierSearchProcess(String client) {
		super(client);
	}

	public JSONArray getSuppliers(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new Supplier());
		setPagingConf(input);

		@SuppressWarnings("unchecked")
		Collection<Supplier> beans = factory.selectQuery(getSQL(input, false));
		for (Supplier bean : beans) {
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
		String select = "select  s.supplier, s.supplier_name ";
		String body = "from supplier s where exists (select null from customer.user_supplier_location us where us.supplier = S.SUPPLIER)";
		return getWrappedSQL(select, body, "ORDER BY s.supplier", countOnly);
	}

}
