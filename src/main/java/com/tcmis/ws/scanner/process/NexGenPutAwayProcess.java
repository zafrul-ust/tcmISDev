package com.tcmis.ws.scanner.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
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
import com.tcmis.ws.scanner.beans.PutAwayBean;
import com.tcmis.ws.scanner.beans.Receipt;
import com.tcmis.ws.scanner.beans.UserCompanyFacilityBean;
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class NexGenPutAwayProcess extends BaseScannerSearchProcess {

	String	containerSQL	= "INSERT INTO container_scan_put_away_stage (container_number, issue_id, receipt_id, data_source, upload_sequence, put_away_by, put_away_datetime, put_away_accepted_by, company_id) VALUES (?, ?, ?, 'Scanner', UPLOADSEQUENCE, ?, ?, ?, ?) ";
	// INSERT INTO container_scan_put_away_stage (container_number,
	// issue_id,
	// receipt_id,
	// data_source,
	// upload_sequence,
	// put_away_by,
	// put_away_datetime,
	// put_away_accepted_by,
	// company_id)
	// VALUES (?,?, ?, "Scanner", UPLOADSEQUENCE, ?, ? ?, ?)

	String	cabinetSQL		= "INSERT INTO cabinet_scan_put_away_stage (shipment_id, application_id, data_source, upload_sequence, put_away_by, put_away_datetime, put_away_accepted_by, company_id) VALUES (?, ?, 'Scanner', UPLOADSEQUENCE, ?, ?, ?, ?)";

	// INSERT INTO cabinet_scan_put_away_stage (shipment_id,
	// application_id,
	// data_source,
	// upload_sequence,
	// put_away_by,
	// put_away_datetime,
	// put_away_accepted_by,
	// company_id)
	// VALUES (?,?,"Scanner",UPLOADSEQUENCE,?,??,?)

	public NexGenPutAwayProcess(String client) {
		super(client);
	}

	public JSONArray putAwayDeliveries(JSONArray deliveries) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		PreparedStatement cabinetPS = null;
		PreparedStatement containerPS = null;
		int containerCount = 0;
		int cabinetCount = 0;
		int i = 0;
		JSONArray errors = new JSONArray();

		try {
			String uploadSequence = factory.selectSingleValue("SELECT upload_sequence.nextval FROM dual");
			cabinetPS = connection.prepareStatement(cabinetSQL.replace("UPLOADSEQUENCE", uploadSequence));
			containerPS = connection.prepareStatement(containerSQL.replace("UPLOADSEQUENCE", uploadSequence));

			for (i = 0; i < deliveries.length(); i++) {
				JSONObject delivery = deliveries.getJSONObject(i);
				if (delivery.has("containerId") && delivery.getInt("containerId") != 0) {
					try {
						containerPS.setInt(1, delivery.getInt("containerId"));
						containerPS.setInt(2, delivery.getInt("issueId"));
						containerPS.setInt(3, delivery.getInt("receiptId"));
						containerPS.setInt(4, delivery.getInt("personnelId"));
						if (delivery.has("deliveryTimestamp")) {
							containerPS.setTimestamp(5, new java.sql.Timestamp(DateHandler.getDateFromIso8601String(delivery.getString("deliveryTimestamp")).getTime()));
						}
						else {
							containerPS.setTimestamp(5, null);
						}
						containerPS.setString(6, delivery.has("putAwayAcceptedBy") ? delivery.getString("putAwayAcceptedBy") : null);
						containerPS.setString(7, delivery.has("companyId") ? delivery.getString("companyId") : null);
						containerPS.executeUpdate();
						containerCount++;
					}
					catch (JSONException je) {
						try {
							JSONObject rowError = new JSONObject();
							rowError.put("id", delivery.has("id") ? delivery.getString("id") : "UNKNOWN");
							rowError.put("errorMessage", "Invalid JSON");
							rowError.put("errorSQL", "N/A");
							errors.put(rowError);
						}
						catch (JSONException e2) {
							e2.printStackTrace();
						}
					}
					catch (SQLException e) {
						JSONObject rowError = new JSONObject();
						try {
							rowError.put("id", delivery.has("id") ? delivery.getString("id") : "UNKNOWN");
							rowError.put("errorMessage", "Error inserting into container_scan_put_away_stage");
							String errorSQL = containerSQL.replace("UPLOADSEQUENCE", uploadSequence) + " values " + delivery.getInt("containerId") + ", "
									+ delivery.getInt("issueId") + ", " + delivery.getInt("receiptId") + delivery.getInt("personnelId");
							errorSQL += delivery.has("deliveryTimestamp") ? delivery.getString("deliveryTimestamp") : "null";
							errorSQL += ", ";
							errorSQL += SqlHandler.delimitString(delivery.getString("putAwayAcceptedBy"));
							errorSQL += ", ";
							errorSQL += SqlHandler.delimitString(delivery.getString("companyId"));

							rowError.put("errorSQL", errorSQL.toString());
							errors.put(rowError);
						}
						catch (JSONException e2) {
							e2.printStackTrace();
						}

					}
				}
				else {
					try {

						cabinetPS.setInt(1, delivery.getInt("shipmentId"));
						cabinetPS.setInt(2, delivery.getInt("cabinetId"));
						cabinetPS.setInt(3, delivery.getInt("personnelId"));
						if (delivery.has("deliveryTimestamp")) {
							cabinetPS.setTimestamp(4, new java.sql.Timestamp(DateHandler.getDateFromIso8601String(delivery.getString("deliveryTimestamp")).getTime()));
						}
						else {
							cabinetPS.setTimestamp(4, null);
						}
						cabinetPS.setString(5, delivery.has("putAwayAcceptedBy") ? delivery.getString("putAwayAcceptedBy") : null);
						cabinetPS.setString(6, delivery.has("companyId") ? delivery.getString("companyId") : null);

						cabinetPS.executeUpdate();
						cabinetCount++;
					}
					catch (JSONException je) {
						try {
							JSONObject rowError = new JSONObject();
							rowError.put("id", delivery.has("id") ? delivery.getString("id") : "UNKNOWN");
							rowError.put("errorMessage", "Invalid JSON");
							rowError.put("errorSQL", "N/A");
							errors.put(rowError);
						}
						catch (JSONException e2) {
							e2.printStackTrace();
						}
					}
					catch (SQLException e) {
						JSONObject rowError = new JSONObject();
						try {
							rowError.put("id", delivery.has("id") ? delivery.getString("id") : "UNKNOWN");
							rowError.put("errorMessage", "Error inserting into cabinet_scan_put_away_stage");
							String errorSQL = cabinetSQL.replace("UPLOADSEQUENCE", uploadSequence) + " values " + delivery.getInt("shipmentId") + ", "
									+ delivery.getInt("cabinetId") + ", " + delivery.getInt("personnelId");
							errorSQL += delivery.has("deliveryTimestamp") ? delivery.getString("deliveryTimestamp") : "null";
							errorSQL += ", ";
							errorSQL += SqlHandler.delimitString(delivery.getString("putAwayAcceptedBy"));
							errorSQL += ", ";
							errorSQL += SqlHandler.delimitString(delivery.getString("companyId"));

							rowError.put("errorSQL", errorSQL.toString());
							errors.put(rowError);
						}
						catch (JSONException e2) {
							e2.printStackTrace();
						}

					}

				}
			}

			if (cabinetCount > 0 || containerCount > 0) {
				Collection inArgs = new Vector(1);
				inArgs.add(uploadSequence);

				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				Vector results = null;
				results = (Vector) factory.doProcedure(connection, "pkg_confirm_put_away.p_stage_and_process", inArgs, outArgs);
				if (results.size() > 0 && (!"OK".equals(results.get(0)) && StringUtils.isNotBlank((String) results.get(0)))) {
					MailProcess.sendEmail("TCMIS.Support@HaasGroupIntl.com", null, "deverror@tcmis.com", "Error in pkg_confirm_put_away.p_stage_and_process ", "" + results.get(0));
					getErrors(uploadSequence, deliveries, errors);
				}
			}

		}
		catch (Exception e) {
			try {
				connection.rollback();
			}
			catch (SQLException e1) {
				log.error("Error rolling back insert to delivery_confirmation " + e1.getMessage(), e1);
			}
			if (!(e instanceof BaseException)) {
				String msg = "Error inserting data into container/cabinet_scan_put_away_stage, putaway record " + i + ": " + e.getMessage();
				log.error(msg, e);
				throw new BaseException(msg, e);
			}
			else {
				throw (BaseException) e;
			}
		}
		finally {
			try {
				if (cabinetPS != null) {
					cabinetPS.close();
				}
				if (containerPS != null) {
					containerPS.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			dbManager.returnConnection(connection);
			dbManager = null;
		}
		return errors;
	}

	private JSONArray getErrors(String uploadSequence, JSONArray deliveries, JSONArray errors) throws BaseException {
		errors = getTableError("cabinet_scan_put_away_stage", uploadSequence, deliveries, errors);
		errors = getTableError("cabinet_scan_put_away", uploadSequence, deliveries, errors);
		errors = getTableError("container_scan_put_away_stage", uploadSequence, deliveries, errors);
		errors = getTableError("container_scan_put_away", uploadSequence, deliveries, errors);
		return errors;
	}

	private JSONArray getTableError(String table, String uploadSequence, JSONArray deliveries, JSONArray errors) throws BaseException {
		StringBuilder errorMsg = new StringBuilder();
		factory.setBean(new PutAwayBean());
		Collection<PutAwayBean> tableErrors = factory
				.selectQuery("select * from " + table + " where upload_sequence = " + uploadSequence + " and processing_status != 'PROCESSED'");
		for (PutAwayBean error : tableErrors) {
			errors.put(getRowError(error, deliveries, uploadSequence));
		}
		return errors;
	}

	private JSONObject getRowError(PutAwayBean error, JSONArray deliveries, String uploadSequence) {
		JSONObject rowError = new JSONObject();
		try {
			rowError.put("id", getCabinetId(error, deliveries));
			rowError.put("errorMessage", error.getError());
			rowError.put("errorSQL", "pkg_confirm_put_away.p_stage_and_process(" + uploadSequence + ", out1)");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return rowError;
	}

	private String getCabinetId(PutAwayBean error, JSONArray deliveries) {
		try {
			for (int i = 0; i < deliveries.length(); i++) {
				JSONObject delivery;
				delivery = deliveries.getJSONObject(i);
				if (error.matches(delivery)) {
					return delivery.has("id") ? delivery.getString("id") : "UNKNOWN";
				}
			}
		}
		catch (JSONException e) {
		}
		return "UNKNOWN";
	}
}
