package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;

import radian.tcmis.common.util.SqlHandler;
import sun.misc.BASE64Decoder;

public class CylinderImageUploadProcess extends BaseScannerPickProcess {
	private static String cylinderDocDirectory;

	static {
		ResourceLibrary library = new ResourceLibrary("tabletConfig");
		cylinderDocDirectory = library.getString("cylinderDocDirectory");
		if (!cylinderDocDirectory.endsWith(File.separator)) {
			cylinderDocDirectory += File.separator;
		}
	}

	public CylinderImageUploadProcess(String client) {
		super(client);
	}

	public JSONObject uploadImage(JSONObject input, FormFile f)  {
		String insertStmt = "";
		try {
			String saveDirectory = cylinderDocDirectory + new SimpleDateFormat("yyyy/MM/").format(new Date());
			File dir = new File(saveDirectory);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileName = getFilename(input, saveDirectory, 1);
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(new BASE64Decoder().decodeBuffer(f.toString()));
			fos.flush();
			fos.close();
			insertStmt = "insert  into cylinder_document (cylinder_tracking_id, supplier, document_type, document_name, inserted_by, document_date, document_url, last_updated_by, last_updated_date)"
					+ " select max(c.cylinder_tracking_id), c.supplier" + ", 'Picture'" + ", "
					+ (input.has("documentName") ? SqlHandler.delimitString(input.getString("documentName")) : "'Cylinder'") + ", " + input.getInt("personnelId") + ", sysdate"
					+ ", " + SqlHandler.delimitString(getRelativeUrlFromFilename(fileName))	+ ", " + input.getInt("personnelId") + ", sysdate" 
					+ " from cylinder_tracking c where c.serial_number = "
					+ SqlHandler.delimitString(input.getString("serialNumber")) + " and c.manufacturer_id_no = " + SqlHandler.delimitString(input.getString("manufacturerIdNo"))
					+ " GROUP BY c.supplier, c.serial_number, c.manufacturer_id_no";

			factory.deleteInsertUpdate(insertStmt);
		}
		catch (JSONException e) {
			return getError(e, "Invalid JSON: ", input, "");
		}
		catch (IOException e) {
			log.error("Error saving cylinder image to disk: ", e);
			return getError(e, "Error saving cylinder image to disk: ", input, "");
		}
		catch (BaseException e) {
			log.error("Error updating DB: ", e);
			return getError(e, "Error updating DB: ", input, insertStmt);
		}		return null;
	}

	private String getFilename(JSONObject input, String savePath, int cntr) throws JSONException {
		String fileName = new StringBuilder(savePath).append(input.getString("manufacturerIdNo")).append("-").append(input.getString("serialNumber")).append("_").append(cntr)
				.append(".jpg").toString();
		if (new File(fileName).exists()) {
			return getFilename(input, savePath, cntr + 1);
		}
		return fileName;
	}
}
