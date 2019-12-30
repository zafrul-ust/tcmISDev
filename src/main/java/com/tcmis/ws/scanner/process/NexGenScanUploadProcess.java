package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PageBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.GHSLabelReqsBean;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.internal.hub.beans.WorkAreaBinCountBean;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.Receipt;
import com.tcmis.ws.scanner.beans.UserCompanyFacilityBean;
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class NexGenScanUploadProcess extends BaseScannerSearchProcess implements Runnable {

	Log								log		= LogFactory.getLog(this.getClass());
	private static ResourceLibrary	library	= null;
	private JSONArray scans;
	private String emailUrl;
	private File backupFile;
	
	static {
		try {
			library = new ResourceLibrary("scannerupload");
		}
		catch (Exception ex) {
		}
	}

	public NexGenScanUploadProcess(String client) {
		super(client);
	}

	public void uploadScans(JSONArray scans, String emailUrl) throws BaseException {
		try {
			String dirname = library.getString("upload.backupdir");
			backupFile = FileHandler.saveTempFile(scans.toString(3), "ScannerUpload_" + scans.getJSONObject(0).getString("companyId") + "_", ".json", dirname);
			setEmailUrl(emailUrl);
			setScans(scans);
			new Thread(this).start();
		}
		catch (IOException e) {
			log.error("Error backing up scans", e);
			throw new BaseException(e);
		}
		catch (JSONException e) {
			log.error("Error backing up scans", e);
			throw new BaseException(e);
		}
	}

	public void uploadScans(BigDecimal personnelId, JSONArray scans, String emailUrl) throws BaseException, JSONException {

		Vector<WorkAreaBinCountBean> countByCorR = new Vector<WorkAreaBinCountBean>();
		Vector<CabinetInventoryCountStageBean> countByK = new Vector<CabinetInventoryCountStageBean>();
		Vector<CabinetItemInventoryCountBean> countByItem = new Vector<CabinetItemInventoryCountBean>();
		Vector<String> binIdsScanned = new Vector<String>();
		BigDecimal uploadSeq = getNextUpLoadSeq();
		for (int i = 0; i < scans.length(); i++) {
			JSONObject scan;
			scan = scans.getJSONObject(i);
			String countType = scan.has("countType") ? scan.getString("countType") : "R";
			if (!scan.has("binId")) {
				scan.put("binId", 0);
			}
			String binId = "" + scan.getInt("binId");
			if (!binIdsScanned.contains(binId)) {
				binIdsScanned.add(binId);
			}

			if (StringUtils.isBlank(countType)) {
				countType = "";
			}
			if (countType.startsWith("R") || countType.startsWith("C") || countType.startsWith("A") || countType.startsWith("D")) {
				countByCorR.add(new WorkAreaBinCountBean(uploadSeq, scan));
			}
			else if (countType.startsWith("K")) {
				countByK.add(new CabinetInventoryCountStageBean(uploadSeq, scan));
			}
			else { // I, P or T
				countByItem.add(new CabinetItemInventoryCountBean(uploadSeq, scan));
			}
		}
		
		CabinetCountProcess process = new CabinetCountProcess(client);
		process.process(binIdsScanned, new Vector(), countByItem, countByK, personnelId, emailUrl, uploadSeq, countByCorR);
	}

	private BigDecimal getNextUpLoadSeq() {
		BigDecimal b = null;
		Connection connection = null;
		try {
			connection = dbManager.getConnection();
			b = new BigDecimal("" + new SqlManager().getOracleSequence(connection, "upload_sequence"));
		}
		catch (Exception ex) {
			log.error("Error retrieveing upload_Sequence.nextVal ", ex);
		}
		finally {
			try {
				dbManager.returnConnection(connection);
			}
			catch (Exception ee) {
			}
			connection = null;
		}
		return b;
	}

	public void run() {
		try {
			int lastId = 0;
			JSONArray curScans = new JSONArray();

			for (int i = 0; i < scans.length(); i++) {
				JSONObject scan = scans.getJSONObject(i);
				int curId = scan.getInt("personnelId");
				if (curId != lastId) {
					if (curScans.length() > 0) {
						uploadScans(new BigDecimal(lastId), curScans, emailUrl);
					}
					curScans = new JSONArray();
					lastId = curId;
				}
				curScans.put(scan);
			}
			uploadScans(new BigDecimal(lastId), curScans, emailUrl);
		}
		catch (Exception e) {
			log.error("Error processing Scanner upload, see scan file: " + backupFile.getAbsolutePath(), e);
		}
	}

	public JSONArray getScans() {
		return scans;
	}

	public void setScans(JSONArray scans) {
		this.scans = scans;
	}

	public String getEmailUrl() {
		return emailUrl;
	}

	public void setEmailUrl(String emailUrl) {
		this.emailUrl = emailUrl;
	}
}
