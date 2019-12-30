package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.exceptions.DbReturnConnectionException;
import com.tcmis.common.ui.web.taglib.Environment;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.IssueBean;
import com.tcmis.ws.scanner.beans.Pick;
import com.tcmis.ws.scanner.beans.PickReceipt;

public class PickCompleteProcess extends BaseScannerPickProcess {
	public PickCompleteProcess(String client) {
		super(client);
	}

	public JSONArray completePick(Pick pick, JSONArray errors) throws BaseException {
		int originalNumberOfErrors = errors.length();

		Connection conn = dbManager.getConnection();
		JSONObject currentError = null;
		try {
			conn.setAutoCommit(false);
			currentError = savePick(pick, pick.hasPickState() ? pick.getPickingState() : "PRE_SHIP", conn);
			if (currentError != null) {
				errors.put(currentError);
				log.debug("*** ROLLING BACK CHANGES FOR PICK ***");
				conn.rollback();
			}
			else {
				IssueBean issue = getIssue(pick, conn);

				for (PickReceipt receipt : pick.getPickReceipts()) {
					currentError = saveReceiptValidation(pick, receipt, conn);
					if (currentError != null) {
						errors.put(currentError);
						break;
					}
					// else {
					// saveReceipt(pick, receipt, issue, conn, errors);
					// }
					// if (errors.length() > originalNumberOfErrors) {
					// break;
					// }
				}

				if (errors.length() == originalNumberOfErrors) {
					synchPickIssuesWithReceipts(pick, conn, errors);
				}

				if (errors.length() == originalNumberOfErrors) {
					finishPick(pick, issue, conn, errors);
				}

				if (errors.length() > originalNumberOfErrors) {
					log.debug("*** ROLLING BACK CHANGES FOR PICK ***");
					conn.rollback();
				}
				else {
					conn.commit();

					class OneShotTask implements Runnable {
						Pick		thePick;
						DbManager	dbmanager;

						OneShotTask(Pick pick, DbManager dbManager) {
							thePick = pick;
							dbmanager = dbManager;
						}

						public void run() {
							threadableGeneratePVR(thePick, dbmanager);
						}
					}
					new Thread(new OneShotTask(pick, dbManager)).start();
				}
			}
		}
		catch (Exception e) {
			try {
				log.debug("*** ROLLING BACK CHANGES FOR PICK ***");
				conn.rollback();
			}
			catch (SQLException e1) {
				log.error("Error rolling back in PickCompleteProcess", e1);
			}
			throw new BaseException("Error saving pick", e);
		}
		finally {
			try {
				conn.setAutoCommit(true);
			}
			catch (SQLException e) {
				log.error("Error committing PickCompleteProcess", e);
			}
			dbManager.returnConnection(conn);
		}
		return errors;
	}

	public JSONArray updatePick(Pick pick, JSONArray errors) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			conn.setAutoCommit(false);
			JSONObject currentError = savePick(pick, null, conn);
			if (currentError != null) {
				errors.put(currentError);
				log.debug("*** ROLLING BACK UPDATE FOR PICK ***");
				conn.rollback();
			}
		}
		catch (Exception e) {
			try {
				log.debug("*** ROLLING BACK UPDATE FOR PICK ***");
				conn.rollback();
			}
			catch (SQLException e1) {
				log.error("Error rolling back in PickCompleteProcess.update", e1);
			}
			throw new BaseException("Error updating pick", e);
		}
		finally {
			try {
				conn.setAutoCommit(true);
			}
			catch (SQLException e) {
				log.error("Error committing PickCompleteProcess", e);
			}
			dbManager.returnConnection(conn);
		}
		return errors;
	}

	private IssueBean getIssue(Pick pick, Connection conn) throws BaseException {
		factory.setBean(new IssueBean());
		@SuppressWarnings("unchecked")
		Collection<IssueBean> beans = factory
				.selectQuery("select i.* from picking_unit_issue p, issue i where p.picking_unit_id = " + pick.getPickingUnitId() + " and I.ISSUE_ID = p.issue_id", conn);
		return beans.iterator().next();
	}

	private Timestamp getTimestamp(Date date) {
		return date != null ? new Timestamp(date.getTime()) : null;
	}

	private boolean responseIsOK(Vector results) {
		String response = "" + results.firstElement();
		return StringHandler.isBlankString(response) || "0".equals(response);
	}

	private JSONObject savePick(Pick pick, String pickingState, Connection conn) throws BaseException {
		StringBuilder sql = new StringBuilder("update PICKING_UNIT set ");
		sql.append(" LAST_UPDATED_BY = ").append(pick.getPickPersonnelId());
		if (StringUtils.isNotBlank(pickingState)) {
			sql.append(", PICKING_STATE = ").append(SqlHandler.delimitString(pickingState));
		}
		if (pick.hasPickDate()) {
			sql.append(", PICK_DATE = ").append(DateHandler.getOracleToDateFunction(pick.getPickDate()));
		}
		if (pick.hasPackDate()) {
			sql.append(", PACK_DATE = ").append(DateHandler.getOracleToDateFunction(pick.getPackDate()));
		}
		if (pick.hasQcDate()) {
			sql.append(", QC_DATE = ").append(DateHandler.getOracleToDateFunction(pick.getQcDate()));
			if (!pick.hasPackDate()) {
				sql.append(", PACK_DATE = ").append(DateHandler.getOracleToDateFunction(pick.getQcDate()));
			}
		}
		if (pick.hasShipDate()) {
			sql.append(", SHIP_DATE = ").append(DateHandler.getOracleToDateFunction(pick.getShipDate()));
		}
		if (pick.hasBin()) {
			sql.append(", BIN = ").append(SqlHandler.delimitString(pick.getBin()));
		}
		if (pick.hasHub()) {
			sql.append(", HUB = ").append(SqlHandler.delimitString(pick.getHub()));
		}
		if (pick.hasBoxCount()) {
			sql.append(", BOX_COUNT = ").append(pick.getBoxCount());
		}
		if (pick.hasPackageType()) {
			sql.append(", PACKAGE_TYPE = ").append(SqlHandler.delimitString(pick.getPackageType()));
		}
		// if (pick.hasPickNotes()) {
		// sql.append(", PICK_NOTES =
		// ").append(SqlHandler.delimitString(pick.getPickNotes()));
		// }
		if (pick.hasTotalWeight()) {
			sql.append(", TOTAL_WEIGHT = ").append(pick.getTotalWeight());
		}
		if (pick.hasTrackingNumbers()) {
			sql.append(", TRACKING_NUMBERS = ").append(SqlHandler.delimitString(pick.getTrackingNumbers()));
		}
		if (pick.hasCarrierName()) {
			sql.append(", CARRIER_NAME = ").append(SqlHandler.delimitString(pick.getCarrierName()));
		}
		if (pick.hasDotOverride()) {
			sql.append(", DOT_OVERRIDE = ").append(SqlHandler.delimitString(pick.getDotOverride()));
		}
		if (pick.hasTabletShipmentId()) {
			sql.append(", TABLET_SHIPMENT_ID = ").append(SqlHandler.delimitString(pick.getTabletShipmentId()));
		}
		if (pick.isUseOverpackSet()) {
			sql.append(", USE_OVERPACK_BOX = ").append(SqlHandler.delimitString(pick.isUseOverpackBox() ? "Y" : "N"));
		}
		if (pick.hasDryIceWeight()) {
			sql.append(", DRY_ICE_WEIGHT_LBS = ").append(pick.getDryIceWeight());
		}
		if (pick.hasSharedPickingUnitId()) {
			sql.append(", SHARED_PICKING_UNIT_ID = ").append(pick.getSharedPickingUnitId());
		}

		sql.append(" where PICKING_UNIT_ID = ").append(pick.getPickingUnitId());
		try {
			factory.deleteInsertUpdate(sql.toString(), conn);
		}
		catch (Exception e) {
			return getError(e, "Error updating picking_unit: ", pick.getId(), sql.toString());
		}

		if (pick.hasPdocs()) {
			String pdocSQL = "DELETE FROM picking_unit_pdoc WHERE picking_unit_id=" + pick.getPickingUnitId();
			try {
				factory.deleteInsertUpdate(pdocSQL, conn);
				for (BigDecimal pdoc : pick.getPdocs()) {
					pdocSQL = "INSERT INTO picking_unit_pdoc (picking_unit_id, pdoc) values (" + pick.getPickingUnitId() + ", " + pdoc + ")";
					factory.deleteInsertUpdate(pdocSQL, conn);
				}
			}
			catch (Exception e) {
				return getError(e, "Error updating picking_unit_pdoc: ", pick.getId(), pdocSQL);
			}
		}

		if (pick.hasPickValidation()) {
			return savePickValidation(pick, conn);
		}
		else {
			return null;
		}
	}

	private JSONObject savePickValidation(Pick pick, Connection conn) throws BaseException {
		StringBuilder sql = new StringBuilder("insert into PICKING_UNIT_VALIDATION");
		sql.append(" (PICKING_UNIT_ID, PICK_PERSONNEL_ID, PICK_ITEM_ID_VALIDATED, PICK_MATERIAL_VALIDATED, PICK_PACKAGE_VALIDATED, PICK_CUST_PART_NUM_VALIDATED, PICK_NOTES");
		sql.append(", QC_PERSONNEL_ID, QC_ITEM_ID_VALIDATED, QC_MATERIAL_VALIDATED, QC_PACKAGE_VALIDATED, QC_CUST_PART_NUM_VALIDATED, QC_NOTES) VALUES (");
		sql.append(pick.getPickingUnitId()).append(",");
		sql.append(pick.getPickPersonnelId()).append(",");
		sql.append(pick.getPickValidation().isItemIdValidated() ? "'Y'" : "'N'").append(",");
		sql.append(pick.getPickValidation().isMaterialValidated() ? "'Y'" : "'N'").append(",");
		sql.append(pick.getPickValidation().isPackageValidated() ? "'Y'" : "'N'").append(",");
		sql.append(pick.getPickValidation().isCustomerPartNumberValidated() ? "'Y'" : "'N'").append(",");
		sql.append(SqlHandler.delimitString(pick.getPickNotes())).append(", ");
		sql.append(pick.getQcPersonnelId()).append(",");
		sql.append(pick.getQcValidation().isItemIdValidated() ? "'Y'" : "'N'").append(",");
		sql.append(pick.getQcValidation().isMaterialValidated() ? "'Y'" : "'N'").append(",");
		sql.append(pick.getQcValidation().isPackageValidated() ? "'Y'" : "'N'").append(",");
		sql.append(pick.getQcValidation().isCustomerPartNumberValidated() ? "'Y'" : "'N'").append(",");
		sql.append(SqlHandler.delimitString(pick.getQcNotes()));
		sql.append(")");
		try {
			factory.deleteInsertUpdate(sql.toString(), conn);
		}
		catch (Exception e) {
			return getError(e, "Error inserting into PICKING_UNIT_VALIDATION: ", pick.getId(), sql.toString());
		}
		return null;
	}

	// @SuppressWarnings({"rawtypes", "unchecked"})
	// private void saveReceipt(Pick pick, PickReceipt receipt, IssueBean issue,
	// Connection conn, JSONArray errors) throws BaseException {
	// Collection inArgs = null;
	// Collection outArgs = null;
	//
	// inArgs = new Vector();
	// inArgs.add(issue.getPicklistId());
	// inArgs.add(issue.getCompanyId());
	// inArgs.add(issue.getPrNumber());
	// inArgs.add(issue.getLineItem());
	// inArgs.add(getTimestamp(issue.getNeedDate()));
	// inArgs.add(issue.getSourceHub());
	// inArgs.add(receipt.getReceiptId());
	// inArgs.add(getTimestamp(pick.getPickDate()));
	// inArgs.add(receipt.getQcValidation().getQcQuantity());
	// inArgs.add(pick.getPickPersonnelId());
	//
	// outArgs = new Vector(2);
	// outArgs.add(new Integer(java.sql.Types.NUMERIC));
	// outArgs.add(new Integer(java.sql.Types.VARCHAR));
	//
	// try {
	// Vector results = (Vector) factory.doProcedure(conn, "P_ENTER_PICK",
	// inArgs, outArgs);
	// if (!responseIsOK(results)) {
	// errors.put(getError(null, "Error calling: P_ENTER_PICK: " +
	// results.get(1), pick.getId(), "args: " + inArgs));
	// }
	// }
	// catch (BaseException b) {
	// errors.put(getError(b, "Error calling: P_ENTER_PICK: ", pick.getId(),
	// "args: " + inArgs));
	// return;
	// }
	// }

	private void synchPickIssuesWithReceipts(Pick pick, Connection conn, JSONArray errors) {
		Collection inArgs = null;

		inArgs = new Vector();
		inArgs.add(pick.getPickingUnitId());

		try {
			factory.doProcedure(conn, "pkg_pu_receipt_update.p_validate_pu_pick", inArgs);
		}
		catch (BaseException b) {
			errors.put(getError(b, "Error calling: pkg_pu_receipt_update.p_validate_pu_pick: ", pick.getId(), "args: " + inArgs));
			return;
		}
	}

	private void threadableGeneratePVR(Pick pick, DbManager dbManager) {
		Connection conn = null;

		try {
			conn = dbManager.getConnection();
			generatePVR(pick, true, dbManager.getConnection(), new JSONArray());
		}
		catch (DbConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				dbManager.returnConnection(conn);
			}
			catch (DbReturnConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected String generatePVR(Pick pick, boolean savePVR, Connection conn, JSONArray errors) {
		String pdfUrl = "";
		try {
			String url = "http://localhost/HaasReports/report/printConfigurableReport.do?pr_pickingUnitId=" + pick.getPickingUnitId()
					+ "&rpt_ReportBeanId=PickValidationRecordReportDefinition&pr_urlOnly=y";
			if (!Environment.isProduction()) {
				log.debug("Generating PVR via " + url);
			}
			pdfUrl = NetHandler.getHttpPostResponse(url, null, null, "");
		}
		catch (Exception e) {
			errors.put(getError(e, "Error generating PVR: ", pick.getId(), ""));
			return "";
		}
		try {
			String file = getFullFilenameFromUrl(pdfUrl);
			if (savePVR) {
				if (!Environment.isProduction()) {
					log.debug("Saving PVR");
				}
				addDocumentToPickingDocument(pick.getQcPersonnelId(), "" + pick.getPickingUnitId(), file, "PVR", conn);
			}
			if (pick.hasPdfPrinterId()) {
				String printerCupsName = getPrinterCupsName(pick.getPdfPrinterId());
				if (!Environment.isProduction()) {
					log.debug("Printing file " + file + " to printer " + printerCupsName);
				}
				PrintHandler.print(printerCupsName, file);
			}
		}
		catch (Exception e) {
			errors.put(getError(e, "Error saving PVR: ", pick.getId(), ""));
		}
		if (!Environment.isProduction()) {
			log.debug("Done generating PVR");
		}
		return pdfUrl;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void finishPick(Pick pick, IssueBean issue, Connection conn, JSONArray errors) throws BaseException {
		Collection inArgs = new Vector(5);
		int curErrors = errors.length();

		try {
			inArgs.add(issue.getCompanyId());
			inArgs.add(issue.getPrNumber());
			inArgs.add(issue.getLineItem());
			inArgs.add("N");
			inArgs.add(getTimestamp(issue.getNeedDate()));
			factory.doProcedure(conn, "P_LINE_ITEM_ALLOCATE", inArgs);
		}
		catch (Exception e) {
			errors.put(getError(e, "Error calling: P_LINE_ITEM_ALLOCATE: ", pick.getId(), "args: " + inArgs));
			return;
		}

		if (errors.length() == curErrors) {
			inArgs = new Vector(2);
			inArgs.add(issue.getPicklistId());
			inArgs.add(pick.getQcPersonnelId());
			try {
				factory.doProcedure(conn, "P_QC_PICKLIST", inArgs);
			}
			catch (BaseException b) {
				errors.put(getError(b, "Error calling: P_QC_PICKLIST: ", pick.getId(), "args: " + inArgs));
			}
		}

		// Ensure that the picking_unit.picking_state was not overwritten by the
		// preceding DB procedure calls.
		if (errors.length() == curErrors) {
			if (pick.hasPickState()) {
				StringBuilder sql = new StringBuilder("update PICKING_UNIT set PICKING_STATE = ").append(SqlHandler.delimitString(pick.getPickingState()));
				sql.append(" where PICKING_UNIT_ID = ").append(pick.getPickingUnitId());
				try {
					factory.deleteInsertUpdate(sql.toString(), conn);
				}
				catch (Exception e) {
					errors.put(getError(e, "Error updating picking_unit: ", pick.getId(), sql.toString()));
				}
			}
		}
	}

	private JSONObject saveReceiptValidation(Pick pick, PickReceipt receipt, Connection conn) throws BaseException {
		if (!receipt.isValid()) {
			JSONObject rowError = new JSONObject();
			try {
				rowError.put("pickingUnitId", pick.getPickingUnitId());
				rowError.put("errorMessage", receipt.getInvalidMessage());
			}
			catch (JSONException e2) {
				e2.printStackTrace();
			}
			return getError(null, receipt.getInvalidMessage(), pick.getId(), "");
		}
		String stmt = "update PICKING_UNIT_RCPT_VALIDATION" + " set pick_quantity = " + receipt.getPickQuantity() + ", pick_bin_scanned = "
				+ (receipt.getPickValidation().isBinScanned() ? "'Y'" : "'N'") + ", pick_receipt_scanned = " + (receipt.getPickValidation().isReceiptScanned() ? "'Y'" : "'N'")
				+ ", pick_lot_number_validated = " + (receipt.getPickValidation().isLotNumberValidated() ? "'Y'" : "'N'") + ", pick_lot_status_validated = "
				+ (receipt.getPickValidation().isLotStatusValidated() ? "'Y'" : "'N'") + ", pick_expiration_validated = "
				+ (receipt.getPickValidation().isExpirationValidated() ? "'Y'" : "'N'") + ", qc_quantity = " + receipt.getQcValidation().getQcQuantity() + ", qc_bin_scanned = "
				+ (receipt.getQcValidation().isBinScanned() ? "'Y'" : "'N'") + ", qc_receipt_scanned = " + (receipt.getQcValidation().isReceiptScanned() ? "'Y'" : "'N'")
				+ ", qc_lot_number_validated = " + (receipt.getQcValidation().isLotNumberValidated() ? "'Y'" : "'N'") + ", qc_lot_status_validated = "
				+ (receipt.getQcValidation().isLotStatusValidated() ? "'Y'" : "'N'") + ", qc_expiration_validated = "
				+ (receipt.getQcValidation().isExpirationValidated() ? "'Y'" : "'N'") + ", qc_container_barcode_validated = "
				+ (receipt.getQcValidation().isContainerBarcodeValidated() ? "'Y'" : "'N'") + ", qc_container_barcode_applied = "
				+ (receipt.getQcValidation().isContainerBarcodeApplied() ? "'Y'" : "'N'") + " where picking_unit_id = " + pick.getPickingUnitId() + " and receipt_id = "
				+ receipt.getReceiptId();

		try {
			int rowsUpdated = factory.deleteInsertUpdate(stmt.toString(), conn);
			if (rowsUpdated == 0) {
				StringBuilder insertStmt = new StringBuilder("insert into PICKING_UNIT_RCPT_VALIDATION");
				insertStmt.append(
						" (PICKING_UNIT_ID, RECEIPT_ID,  PICK_QUANTITY, PICK_BIN_SCANNED, PICK_RECEIPT_SCANNED, PICK_LOT_NUMBER_VALIDATED, PICK_LOT_STATUS_VALIDATED, PICK_EXPIRATION_VALIDATED");
				insertStmt.append(", QC_QUANTITY, QC_BIN_SCANNED, QC_RECEIPT_SCANNED, QC_LOT_NUMBER_VALIDATED, QC_LOT_STATUS_VALIDATED, QC_EXPIRATION_VALIDATED");
				insertStmt.append(", QC_CONTAINER_BARCODE_VALIDATED, QC_CONTAINER_BARCODE_APPLIED) VALUES (");
				insertStmt.append(pick.getPickingUnitId()).append(",");
				insertStmt.append(receipt.getReceiptId()).append(",");
				insertStmt.append(receipt.getPickQuantity()).append(",");
				insertStmt.append(receipt.getPickValidation().isBinScanned() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getPickValidation().isReceiptScanned() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getPickValidation().isLotNumberValidated() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getPickValidation().isLotStatusValidated() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getPickValidation().isExpirationValidated() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getQcValidation().getQcQuantity()).append(",");
				insertStmt.append(receipt.getQcValidation().isBinScanned() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getQcValidation().isReceiptScanned() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getQcValidation().isLotNumberValidated() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getQcValidation().isLotStatusValidated() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getQcValidation().isExpirationValidated() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getQcValidation().isContainerBarcodeValidated() ? "'Y'" : "'N'").append(",");
				insertStmt.append(receipt.getQcValidation().isContainerBarcodeApplied() ? "'Y'" : "'N'");
				insertStmt.append(")");
				factory.deleteInsertUpdate(insertStmt.toString(), conn);
			}
		}
		catch (Exception e) {
			return getError(e, "Error updating PICKING_UNIT_RCPT_VALIDATION: ", pick.getId(), stmt.toString());
		}

		return null;
	}

}
