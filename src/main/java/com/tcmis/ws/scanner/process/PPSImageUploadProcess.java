package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.ws.scanner.beans.PrintInput;
import com.tcmis.ws.scanner.beans.TabletShipment;

import radian.tcmis.common.util.SqlHandler;
import sun.misc.BASE64Decoder;

public class PPSImageUploadProcess extends GenericProcess {

	public PPSImageUploadProcess(String client) {
		super(client);
	}

	public void uploadPPSImage(JSONObject input, FormFile f) throws BaseException, JSONException {
		ResourceLibrary library = new ResourceLibrary("tabletConfig");
		String savePath = library.getString("pickingDocDirectory");
		if (!savePath.endsWith(File.separator)) {
			savePath += File.separator;
		}
		Calendar today = Calendar.getInstance();
		String dateYear = Integer.toString(today.get(Calendar.YEAR));
		String dateMonth = StringUtils.leftPad(Integer.toString(today.get(Calendar.MONTH) + 1), 2, "0");

		String urlPath = library.getString("relativePickingDocDirectory");
		if (!(urlPath.startsWith("/"))) {
			urlPath = "/" + urlPath;
		}
		if (!urlPath.endsWith("/")) {
			urlPath += "/";
		}
		savePath += dateYear + File.separator + dateMonth + File.separator;
		String fileName = getFilename(input.getInt("pickingUnitId") + "-" + input.getString("documentType"), savePath);
		urlPath += dateYear + "/" + dateMonth + "/" + fileName;

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			File dir = new File(savePath.toString());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(savePath + fileName);
			fos.write(f.getFileData());
			fos.flush();
			fos.close();
		}
		catch (IOException e) {
			log.error("Error saving receipt image to disk: ", e);
			throw new BaseException(e);
		}

		String stmt = "insert into picking_unit_document " + "(picking_unit_id, document_path, document_type, receipt_id, last_updated_by) " + "values ("
				+ input.getInt("pickingUnitId") + ", " + SqlHandler.delimitString(urlPath) + ", " + SqlHandler.delimitString(input.getString("documentType")) + ", "
				+ input.getInt("receiptId") + ", " + input.getInt("personnelId") + ")";

		factory.deleteInsertUpdate(stmt);
	}

	public void uploadBOLSignature(JSONObject input, FormFile f) throws BaseException, JSONException {
		ResourceLibrary library = new ResourceLibrary("shipmentdocument");
		String savePath = library.getString("shipment.documentfile.path");
		if (!savePath.endsWith(File.separator)) {
			savePath += File.separator;
		}
		Calendar today = Calendar.getInstance();
		String dateYear = Integer.toString(today.get(Calendar.YEAR));
		String dateMonth = StringUtils.leftPad("" + (today.get(Calendar.MONTH) + 1), 2, "0");
		String dateDay = StringUtils.leftPad("" + today.get(Calendar.DAY_OF_MONTH), 2, "0");

		String urlPath = library.getString("shipment.documentfile.urlpath");
		if (!(urlPath.startsWith("/"))) {
			urlPath = "/" + urlPath;
		}
		if (!urlPath.endsWith("/")) {
			urlPath += "/";
		}
		savePath += dateYear + File.separator + dateMonth + File.separator;
		String fileName = getFilename(input.getString("documentType") + "_" + dateYear + dateMonth + dateDay, savePath);
		urlPath += dateYear + "/" + dateMonth + "/" + fileName;

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			File dir = new File(savePath.toString());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(savePath + fileName);
			fos.write(f.getFileData());
			fos.flush();
			fos.close();
		}
		catch (IOException e) {
			log.error("Error saving BOL Signature to disk: ", e);
			throw new BaseException(e);
		}

		String escapedSignatureType = SqlHandler.delimitString(input.getString("documentType"));
		Connection conn = null;
		try {
			conn = dbManager.getConnection();

			Collection<TabletShipment> crossRefs = new DepartureScanProcess(getClient()).getShipmentCrossReference(input.getString("tabletShipmentId"), conn);
			for (TabletShipment crossRef : crossRefs) {

				String stmt = "insert into shipment_document (shipment_id, document_name, document_url, document_type, document_date, insert_date, entered_by) values ("
						+ crossRef.getShipmentId() + ", (select document_type_desc from vv_shipment_document_type where document_type = " + escapedSignatureType + "), "
						+ SqlHandler.delimitString(urlPath) + ", " + escapedSignatureType + ", " + "sysdate, sysdate, " + input.getInt("personnelId") + ")";

				factory.deleteInsertUpdate(stmt);
			}
		}
		catch (Exception e1) {
			throw new BaseException("Error saving Straight BOL: " + e1.getMessage());
		}
		finally {
			try {
				dbManager.returnConnection(conn);
			}
			catch (DbReturnConnectionException e) {
				e.printStackTrace();
			}
		}

		if (input.has("pdfPrinterId") || input.has("printerId")) {
			PrintInput printInput = new PrintInput();
			BeanHandler.getJsonBeans(input, printInput);
			if (input.has("pdfPrinterId")) {
				printInput.setPrinterId(input.getInt("pdfPrinterId"));
			}
			JSONArray errors = new JSONArray();
			new PrintProcess(getClient()).printStraightBOL(printInput, errors);
			if (errors.length() > 0) {
				throw new BaseException(errors.get(0).toString());
			}
		}
	}

	private String getFilename(String fileName, String parentDir) {
		return getFilename(fileName, parentDir, 1);
	}

	private String getFilename(String coreFileName, String parentDir, int cntr) {
		String actualFileName = new StringBuilder(coreFileName).append("_").append(cntr).append(".jpg").toString();
		if (new File(parentDir + actualFileName).exists()) {
			return getFilename(coreFileName, parentDir, cntr + 1);
		}
		return actualFileName;
	}
}
