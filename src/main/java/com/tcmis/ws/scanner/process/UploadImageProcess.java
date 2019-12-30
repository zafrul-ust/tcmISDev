package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;

import radian.tcmis.common.util.SqlHandler;

public class UploadImageProcess extends GenericProcess {

	public UploadImageProcess(String client) {
		super(client);
	}
	
	public String saveImage(JSONObject input, FormFile file) throws BaseException, JSONException {
		ResourceLibrary library = new ResourceLibrary("tabletConfig");
		StringBuilder savePath = new StringBuilder(library.getString("receiptImageDirectory"));
		if (!savePath.toString().endsWith(File.separator)) {
			savePath.append(File.separator);
		}
		Calendar today = Calendar.getInstance();
		StringBuilder dateDir = new StringBuilder();
		dateDir.append(today.get(Calendar.YEAR)).append(File.separator);
		dateDir.append(StringUtils.leftPad(Integer.toString(today.get(Calendar.MONTH)+1),2,"0")).append(File.separator);
		
		StringBuilder urlPath = new StringBuilder(library.getString("receiptImageServerURL"));
		if (!urlPath.toString().endsWith(File.separator)) {
			urlPath.append(File.separator);
		}
		savePath.append(dateDir);
		urlPath.append(dateDir);
		String fileName = getFilename(input, savePath.toString(), 1);

		String errorMsg = "";
		try {
			File dir = new File(savePath.toString());
			if ( ! dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(savePath.append(fileName).toString());
			fos.write(file.getFileData());
			fos.flush();
			fos.close();
		}
		catch (IOException e) {
			errorMsg = "Error saving receipt image to disk";
			log.error("Error saving receipt image to disk: ", e);
		}

		int i = 0;
		try {
			JSONArray rids = new JSONArray();
			if (input.has("rids")) {
				rids = input.getJSONArray("rids");
			}
			else {
				rids.put(input.getInt("receiptId"));
			}
			for (; i < rids.length(); i++) {
				StringBuilder insertStmt = new StringBuilder();
				insertStmt.append("insert into RECEIPT_DOCUMENT (DOCUMENT_ID, RECEIPT_ID, DOCUMENT_NAME, DOCUMENT_TYPE, INSERT_DATE, DOCUMENT_DATE, DOCUMENT_URL, ENTERED_BY, COMPANY_ID) values (");
				insertStmt.append("(select nvl(max(document_id),0)+1 from receipt_document where receipt_id = ");
				insertStmt.append(rids.getInt(i)).append("),");
				insertStmt.append(rids.getInt(i)).append(",");
				insertStmt.append("'").append(fileName).append("',");
				insertStmt.append(SqlHandler.delimitString(input.getString("imageType"))).append(",");
				insertStmt.append("sysdate,");
				insertStmt.append("sysdate,");
				insertStmt.append("'").append(urlPath).append(fileName).append("',");
				insertStmt.append(input.getInt("personnelId")).append(",");
				insertStmt.append("'Radian'");
				insertStmt.append(")");
				factory.deleteInsertUpdate(insertStmt.toString());
	
				if (input.optBoolean("reImageComplete")) {
					reImageComplete(rids.getInt(i));
				}
			}
		}
		catch (Exception e) {
			int receiptId = input.getInt("receiptId");
			if (i > 0) {
				receiptId = input.getJSONArray("rids").getInt(i);
			}
			errorMsg = "Error saving image record: Receipt ID " + receiptId + " or Image Type "+input.getString("imageType")+" not valid.";
			log.error("Error saving image record: ", e);
		}
		return errorMsg;
	}
	
	private String getFilename(JSONObject input, String savePath, int cntr) throws JSONException {
		String fileName = new StringBuilder("" + input.getInt("receiptId")).append("-").append(input.getString("imageType")).append("_").append(cntr).append(".jpg").toString();
		if (new File(savePath + fileName).exists()) {
			return getFilename(input, savePath, cntr + 1);
		}
		return fileName;
	}
	
	public void reImageComplete(int receiptId) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
		String updateDate = DateHandler.formatDate(new Date(), library.getString("java.datetimeformat"));
		StringBuilder update = new StringBuilder("update receipt set INTERNAL_RECEIPT_NOTES = INTERNAL_RECEIPT_NOTES || '  (Images retaken ");
		update.append(updateDate).append(")', RECEIVING_STATUS = 'QC Ready', RECEIVING_STATUS_DATE = sysdate");
		update.append(" where RECEIPT_ID = ").append(receiptId);
		factory.deleteInsertUpdate(update.toString());
	}
}
