package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.PickingUnit;
import com.tcmis.ws.scanner.beans.PrintInput;

/**
 * @author kelly.hatcher
 *
 */

public class BaseScannerPickProcess extends BaseScannerSearchProcess {
	protected static String		fileGenerationPath	= "";
	protected static Pattern	filenamePattern		= Pattern.compile("(https?://[^/]+)?.*/([^/]*)");
	protected static Pattern	relativeUrlPattern	= Pattern.compile("(https?://[^/]+)?/(.*)");
	protected static String		savePath			= "";
	protected static String		webroot				= "";

	static {
		ResourceLibrary library = new ResourceLibrary("tabletConfig");
		savePath = library.getString("pickingDocDirectory");
		if (!savePath.endsWith(File.separator)) {
			savePath += File.separator;
		}
		ResourceLibrary tcmISresource = new ResourceLibrary("tcmISWebResource");
		fileGenerationPath = tcmISresource.getString("savelabelpath");
		if (!fileGenerationPath.endsWith(File.separator)) {
			fileGenerationPath += File.separator;
		}
		webroot = StringUtils.getCommonPrefix(new String[] {savePath, fileGenerationPath});
	}

	public BaseScannerPickProcess(String client) {
		super(client);
	}

	protected void addDocumentToPickingDocument(BigDecimal personnelId, String pickingUnitId, String fileName, String docType) throws IOException, BaseException {
		addDocumentToPickingDocument(personnelId, pickingUnitId, fileName, docType, dbManager.getConnection());
	}

	protected void addDocumentToPickingDocument(BigDecimal personnelId, String pickingUnitId, String fileName, String docType, Connection conn)
			throws IOException, BaseException {
		File originalFile = new File(fileName);
		String date = new SimpleDateFormat("yyyy/MM/").format(new Date());
		String destFilename = savePath + date + originalFile.getName();
		File destFile = new File(destFilename);
		destFile.getParentFile().mkdirs();
		FileUtils.copyFile(originalFile, destFile);
		String insert = "insert into picking_Unit_document (picking_unit_id, document_path, document_type, last_updated_by)" + "values (" + pickingUnitId + ","
				+ SqlHandler.delimitString(getRelativeUrlFromFilename(destFilename)) + "," + SqlHandler.delimitString(docType) + "," + personnelId + ")";
		factory.deleteInsertUpdate(insert, conn);
	}

	protected String getFilenameFromUrl(String documentUrl) {
		if (StringUtils.isBlank(documentUrl)) {
			return "";
		}
		Matcher matcher = filenamePattern.matcher(documentUrl);
		if (matcher.find()) {
			return matcher.group(2);
		}
		else {
			return documentUrl;
		}
	}

	protected String getFullFilenameFromUrl(String documentUrl) {
		if (StringUtils.isBlank(documentUrl)) {
			return "";
		}
		Matcher matcher = relativeUrlPattern.matcher(documentUrl);
		if (matcher.find()) {
			return webroot + matcher.group(2);
		}
		else {
			return documentUrl;
		}
	}

	protected String getRelativeUrlFromFilename(String fileName) {
		return fileName.replaceFirst("/webdata/html/", "/").replaceAll(webroot, "/");
	}

	public JSONObject getError(Exception e, String msg, JSONObject input, String sql) {
		String id = "";
		try {
			id = input != null && input.has("id") ? input.getString("id") : "";
		}
		catch (JSONException e1) {
			// Should NOT be able to actually get here
			e1.printStackTrace();
		}
		return getError(e, msg, id, sql);
	}

	public JSONObject getError(String msg, String id) {
		return getError(null, msg, id, null);
	}

	public JSONObject getError(Exception e, String msg, String id, String sql) {
		JSONObject rowError = new JSONObject();

		try {
			if (StringUtils.isBlank(msg)) {
				msg = "";
			}
			else if (e == null) {
				rowError.put("errorMessage", msg);
			}

			if (StringUtils.isNotBlank(id)) {
				rowError.put("id", id);
			}

			if (StringUtils.isNotBlank(sql)) {
				rowError.put("errorSQL", sql);
			}
			if (e != null) {
				rowError.put("rootCauseErrorMessage", msg + ExceptionUtils.getRootCauseMessage(e));
				rowError.put("errorMessage", msg + ExceptionUtils.getMessage(e));
				rowError.put("stackTrace", ExceptionUtils.getFullStackTrace(e));
				log.error( msg + ": " + ExceptionUtils.getRootCauseMessage(e), e);
			}
		}
		catch (JSONException e1) {
			e1.printStackTrace();
		}
		return rowError;
	}

	protected String getPrinterCupsName(BigDecimal printerId) throws BaseException {
		return getPrinterCupsName("" + printerId);
	}

	protected String getPrinterCupsName(String printerId) throws BaseException {
		return factory.selectSingleValue("select cups_name from printer where printer_id = " + printerId);
	}

}
