package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
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

/**
 * @author kelly.hatcher
 *
 */

public class BaseScannerSearchProcess extends GenericProcess {
	protected int	pageSize	= 10000;
	protected int	page		= 1;
	protected int	pageCount	= 0;
	protected int	totalCount	= 0;

	public BaseScannerSearchProcess(String client) {
		super(client);
	}

	protected void setPagingConf(JSONObject input) throws JSONException {
		if (input.has("pageSize")) {
			pageSize = input.getInt("pageSize");
		}
		if (input.has("page")) {
			page = input.getInt("page");
		}
	}

	public JSONObject getPageData() throws JSONException {
		JSONObject pageData = new JSONObject();
		pageData.put("page", page);
		pageData.put("pageSize", pageSize);
		pageData.put("pageCount", pageCount);

		int totalPages = totalCount / pageSize;
		if (totalCount % pageSize != 0) {
			totalPages++;
		}
		pageData.put("totalCount", totalCount);
		pageData.put("totalPages", totalPages);

		return pageData;
	}

	/**
	 * Generate SQL that enables paging by adding a "ROW_NUMBER() OVER (Order BY
	 * ...) ROW_NUM" then wrapping the entire query in an additional query that
	 * takes the page & pagesize to determine a WHERE clause using the ROW_NUM
	 * 
	 * @param select - The select statement for the query
	 * @param where - The Where clause
	 * @param orderBy - The ORDER BY statement
	 * @param countOnly - Return only a count and not a page of data
	 * @return
	 */
	protected String getWrappedSQL(String select, String where, String orderBy, boolean countOnly) {
		return getWrappedSQL(select, "", where, orderBy, countOnly, page, pageSize, 0);
	}

	/**
	 * Adds the ability pass in an additional select that will be added to the paged select
	 * This allows function selects to be run _only_ on the paged data and not the entire sub select 
	 * 
	 * @param select
	 * @param externalSelect
	 * @param where
	 * @param orderBy
	 * @param countOnly
	 * @return
	 */
	protected String getWrappedSQL(String select, String externalSelect, String where, String orderBy, boolean countOnly) {
		return getWrappedSQL(select, externalSelect, where, orderBy, countOnly, page, pageSize, 0);
	}

	protected String getWrappedSQL(String select, String where, String orderBy, boolean countOnly, int pageNum, int rowsPerPage, int offsetRowCount) {
		return getWrappedSQL(select, "", where, orderBy, countOnly, pageNum, rowsPerPage, offsetRowCount);
	}

	protected String getWrappedSQL(String select, String externalSelect, String where, String orderBy, boolean countOnly, int pageNum, int rowsPerPage, int offsetRowCount) {
		StringBuilder wrappedQuery = new StringBuilder();
		if (countOnly) {
			wrappedQuery.append("SELECT count(*) FROM ( ");
			wrappedQuery.append(select);
			wrappedQuery.append(" ");
			wrappedQuery.append(where);
			wrappedQuery.append(" )");
		}
		else {
			wrappedQuery.append("SELECT a.*");
			if (StringUtils.isNotBlank(externalSelect)) {
				wrappedQuery.append(", ").append(externalSelect);
			}
			wrappedQuery.append(" FROM (");
			wrappedQuery.append(select);
			wrappedQuery.append(", ROW_NUMBER () OVER (");
			if (!orderBy.trim().toUpperCase().startsWith("ORDER")) {
				wrappedQuery.append("ORDER BY ");
			}
			wrappedQuery.append(orderBy);
			wrappedQuery.append(") row_num ");
			wrappedQuery.append(where);
			wrappedQuery.append(") a");

			if (page > 1) {
				wrappedQuery.append(" WHERE row_num BETWEEN  ");
				if (offsetRowCount > 0) {
					wrappedQuery.append(((pageNum - 2) * rowsPerPage) + 1 + offsetRowCount);
					wrappedQuery.append(" AND ");
					wrappedQuery.append(((pageNum - 1) * rowsPerPage) + offsetRowCount);
				}
				else {
					wrappedQuery.append(((pageNum - 1) * rowsPerPage) + 1);
					wrappedQuery.append(" AND ");
					wrappedQuery.append((pageNum * rowsPerPage));
				}
			}
			else if (offsetRowCount > 0) {
				wrappedQuery.append(" WHERE row_num < " + (offsetRowCount + 1));
			}
			else {
				wrappedQuery.append(" WHERE row_num < " + ((pageNum * rowsPerPage) + 1));
			}
			wrappedQuery.append(" ORDER BY row_num");
		}

		return wrappedQuery.toString();
	}

}
