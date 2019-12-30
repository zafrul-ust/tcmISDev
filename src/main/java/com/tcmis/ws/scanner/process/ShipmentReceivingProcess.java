package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.scanner.beans.CitrReceipt;
import com.tcmis.ws.scanner.beans.DocumentId;
import com.tcmis.ws.scanner.beans.KitComponent;
import com.tcmis.ws.scanner.beans.PoReceipt;
import com.tcmis.ws.scanner.beans.ReceivingReceipt;
import com.tcmis.ws.scanner.beans.RmaReceipt;
import com.tcmis.ws.scanner.beans.Shipment;
import com.tcmis.ws.scanner.beans.TransferReceipt;

public class ShipmentReceivingProcess extends BaseScannerSearchProcess {
	public ShipmentReceivingProcess(String client) {
		super(client);
	}

	public JSONArray receiveShipment(Shipment shipment, JSONArray errors) throws BaseException {
		int originalNumberOfErrors = errors.length();

		Connection conn = dbManager.getConnection();
		JSONObject currentError = null;
		try {
			conn.setAutoCommit(false);
			currentError = saveInboundShipment(shipment, conn);
			if (currentError != null) {
				errors.put(currentError);
			}
			else {
				if (shipment.hasDocumentIds()) {
					for (DocumentId docId : shipment.getDocumentIds()) {
						saveDocId(shipment, docId, conn, errors);
					}
				}
				if (errors.length() == originalNumberOfErrors && shipment.hasPoReceipts()) {
					for (PoReceipt receipt : shipment.getPoReceipts()) {
						if (receipt.IsValidReceipt()) {
							currentError = saveInboundShipmentDetail(shipment, receipt, conn);
							if (currentError != null) {
								errors.put(currentError);
							}
							else {
								saveReceipt(shipment, receipt, conn, errors);
							}
						}
						else {
							JSONObject rowError = new JSONObject();
							try {
								rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
								rowError.put("errorMessage", "Invalid PO RECEIPT " + receipt.getReceiptId() + " requires PO and PO_LINE");
								errors.put(rowError);
							}
							catch (JSONException e2) {
								e2.printStackTrace();
							}
						}
					}
				}
				if (errors.length() == originalNumberOfErrors && shipment.hasTransferReceipts()) {
					for (TransferReceipt receipt : shipment.getTransferReceipts()) {
						if (receipt.IsValidReceipt()) {
							currentError = saveInboundShipmentDetail(shipment, receipt, conn);
							if (currentError != null) {
								errors.put(currentError);
							}
							else {
								saveReceipt(shipment, receipt, conn, errors);
							}
						}
						else {
							JSONObject rowError = new JSONObject();
							try {
								rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
								rowError.put("errorMessage", "Invalid TRANSFER RECEIPT " + receipt.getReceiptId() + " requires TRANSFER_RECEIPT_ID and ORIGINAL_RECEIPT_ID");
								errors.put(rowError);
							}
							catch (JSONException e2) {
								e2.printStackTrace();
							}
						}
					}
				}
				if (errors.length() == originalNumberOfErrors && shipment.hasRmaReceipts()) {
					for (RmaReceipt receipt : shipment.getRmaReceipts()) {
						if (receipt.IsValidReceipt()) {
							currentError = saveInboundShipmentDetail(shipment, receipt, conn);
							if (currentError != null) {
								errors.put(currentError);
							}
							else {
								saveReceipt(shipment, receipt, conn, errors);
							}
						}
						else {
							JSONObject rowError = new JSONObject();
							try {
								rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
								rowError.put("errorMessage", "Invalid RMA RECEIPT " + receipt.getReceiptId() + " requires CUSTOMER_RMA_ID and ORIGINAL_RECEIPT_ID");
								errors.put(rowError);
							}
							catch (JSONException e2) {
								e2.printStackTrace();
							}
						}
					}
				}
				if (errors.length() == originalNumberOfErrors && shipment.hasCitrReceipts()) {
					for (CitrReceipt receipt : shipment.getCitrReceipts()) {
						if (receipt.IsValidReceipt()) {
							currentError = saveInboundShipmentDetail(shipment, receipt, conn);
							if (currentError != null) {
								errors.put(currentError);
							}
							else {
								saveReceipt(shipment, receipt, conn, errors);
							}
						}
						else {
							JSONObject rowError = new JSONObject();
							try {
								rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
								rowError.put("errorMessage", "Invalid CITR RECEIPT " + receipt.getReceiptId() + " requires CUSTOMER_PO and DOC_NUM");
								errors.put(rowError);
							}
							catch (JSONException e2) {
								e2.printStackTrace();
							}
						}
					}
				}
			}

			if (errors.length() > originalNumberOfErrors) {
				conn.rollback();
			}
			else {
				conn.commit();
			}
		}
		catch (SQLException e) {
			throw new BaseException("Error saving shipment", e);
		}
		finally {
			try {
				conn.setAutoCommit(true);
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbManager.returnConnection(conn);
		}
		return errors;
	}

	private void saveDocId(Shipment shipment, DocumentId docId, Connection conn, JSONArray errors) throws BaseException {
		StringBuilder sql = new StringBuilder("insert into INBOUND_SHIPMENT_DOCUMENT");
		sql.append(" (INBOUND_SHIPMENT_ID, INBOUND_SHIPMENT_DOCUMENT_ID, INITIAL_SCAN_USER) VALUES (");
		sql.append(SqlHandler.delimitString(shipment.getShipmentId())).append(", ").append(docId.getDocId()).append(", ").append(shipment.getPersonnelId());
		sql.append(")");
		try {
			factory.deleteInsertUpdate(sql.toString(), conn);
		}
		catch (Exception e) {
			JSONObject rowError = new JSONObject();
			try {
				rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
				rowError.put("errorMessage", "Error inserting into INBOUND_SHIPMENT_DOCUMENT: " + e.getMessage());
				rowError.put("errorSQL", sql.toString());
				errors.put(rowError);
			}
			catch (JSONException e2) {
				e2.printStackTrace();
			}
		}
	}

	private JSONObject saveInboundShipment(Shipment shipment, Connection conn) throws BaseException {
		int shipmentId = dbManager.getOracleSequence("INBOUND_SHIPMENT_ID_SEQ");
		shipment.setShipmentId("" + shipmentId);

		StringBuilder sql = new StringBuilder("insert into inbound_shipment");
		sql.append(
				" (INBOUND_SHIPMENT_ID, HUB, TRACKING_NUMBER, CARRIER_PARENT_SHORT_NAME, DATE_OF_RECEIPT, COUNT_AND_CONDITION_NOTES, SHIPMENT_STATUS, ARRIVAL_SCAN_USER, ARRIVAL_SCAN_DATE, UPDATE_USER)");
		sql.append("VALUES (");
		sql.append(shipment.getShipmentId()).append(", ");
		sql.append(SqlHandler.delimitString(shipment.getHub())).append(", ");
		sql.append(SqlHandler.delimitString(shipment.getTrackingNumber())).append(", ");
		sql.append(SqlHandler.delimitString(shipment.getCarrierParentShortName())).append(", ");
		sql.append(DateHandler.getOracleToDateFunction(shipment.getArrivalScanDate())).append(", ");
		sql.append(SqlHandler.delimitString(shipment.getCountAndConditionNotes())).append(", ");
		sql.append("'Incoming', ");
		sql.append(shipment.getPersonnelId()).append(", ");
		sql.append(DateHandler.getOracleToDateFunction(getTimestamp(shipment.getArrivalScanDate()))).append(", ");
		sql.append(shipment.getPersonnelId());
		sql.append(")");
		try {
			factory.deleteInsertUpdate(sql.toString(), conn);
		}
		catch (Exception e) {
			JSONObject rowError = new JSONObject();
			try {
				rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
				rowError.put("errorMessage", "Error inserting into inbound_shipment: " + e.getMessage());
				rowError.put("errorSQL", sql.toString());
			}
			catch (JSONException e2) {
				e2.printStackTrace();
			}
			return rowError;
		}
		return null;
	}

	private Timestamp getTimestamp(Date date) {
		return date != null ? new Timestamp(date.getTime()) : null;
	}

	private void saveReceipt(Shipment shipment, ReceivingReceipt receipt, Connection conn, JSONArray errors) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		
		BigDecimal quantityReceived = isMvItem(receipt.getItemId(), conn)?
						receipt.getQuantity():
						(receipt.getQuantity().multiply(receipt.getReceivedPurchasingUnits()));
						
		if(receipt.getDefinedShelfLifeItem() == null) {
			receipt.setDefinedShelfLifeItem(getDefinedShelfLifeItem(receipt.getPo(), receipt.getPoLine(), conn));
		}

		inArgs = new Vector();
		inArgs.add(receipt.getShipmentDetailId());
		inArgs.add(receipt.getReceiptId());
		inArgs.add(receipt.getReceivingStatus());
		inArgs.add(receipt.getPoLine());
		inArgs.add(receipt.getItemId());
		inArgs.add(receipt.getLot());
		inArgs.add(shipment.getHub());
		inArgs.add(receipt.getInventoryGroup());
		inArgs.add(receipt.getBin());
		inArgs.add(quantityReceived.setScale(0, RoundingMode.HALF_UP));
		inArgs.add(getTimestamp(receipt.getDateOfReceipt()));
		inArgs.add(getTimestamp(receipt.getDateOfManufacture()));
		inArgs.add(getTimestamp(receipt.getDateOfShipment()));
		inArgs.add(getTimestamp(receipt.getExpireDate()));
		inArgs.add(getTimestamp(receipt.getVendorShipDate()));
		inArgs.add(shipment.getPersonnelId());
		inArgs.add(receipt.getDocType());
		inArgs.add(receipt.getNotes());
		inArgs.add(receipt.getReceivedPurchasingUnits());
		inArgs.add(receipt.getReceiptGroup());
		inArgs.add(receipt.isCITR() ? receipt.getCustomerPo() : receipt.getPo());
		inArgs.add(receipt.getOwnerCompanyId());
		inArgs.add(receipt.getCatalogCompanyId());
		inArgs.add(receipt.getCatalogId());
		inArgs.add(receipt.getCatPartNo());
		inArgs.add(receipt.getPartGroupNo());
		inArgs.add(receipt.getOriginalReceiptId());
		inArgs.add(receipt.getOriginalReceiptId());
		inArgs.add(receipt.getOriginalReceiptId());
		inArgs.add(receipt.getQaStatement());
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(null);
		inArgs.add(receipt.getOwnerSegmentId());
		inArgs.add(receipt.getAccountNumber());
		inArgs.add(receipt.getAccountNumber2());
		inArgs.add(receipt.getAccountNumber3());
		inArgs.add(receipt.getAccountNumber4());
		inArgs.add(receipt.getCustomerReceiptId());
		inArgs.add(null);
		inArgs.add(receipt.getHazcomLabelFlag());
		inArgs.add(null);
		inArgs.add(receipt.getDefinedShelfLifeItem());
		inArgs.add(getTimestamp(shipment.getArrivalScanDate()));
		inArgs.add(receipt.getInternalReceiptNotes());
		inArgs.add(getTimestamp(receipt.getExpireDate()));
		inArgs.add(null);
		
		outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Vector results = (Vector) factory.doProcedure(conn, "PKG_HUB_AUTOMATION.P_UPSERT_RECEIPT", inArgs, outArgs);
		String result = (String) results.firstElement();
		if (responseIsOK(result)) {
			if (receipt.hasKitComponents() && receipt.isSeparableKit()) {
				for (KitComponent bean : receipt.getKitComponents()) {
					saveReceiptComponent(shipment, bean, receipt.getReceiptId(), receipt.getItemId(), conn, errors);
				}
			}
			saveGHSLabelReqs(shipment, receipt, conn, errors);
		}
		else {
			JSONObject rowError = new JSONObject();
			try {
				rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
				rowError.put("errorMessage", "Error calling: PKG_HUB_AUTOMATION.P_UPSERT_RECEIPT: " + result);
				rowError.put("errorSQL", "args: " + inArgs);
				errors.put(rowError);
			}
			catch (JSONException e2) {
				e2.printStackTrace();
			}
		}

	}

	private void saveGHSLabelReqs(Shipment shipment, ReceivingReceipt receipt, Connection conn, JSONArray errors) {
		StringBuilder insert = new StringBuilder("insert into RECEIPT_GHS_LABEL_REQS ");
		insert.append("(RECEIPT_ID, PRODUCT_NAME, SIGNAL_WORD, PICTOGRAM, HAZARD_STATEMENT, PRECAUTIONARY_STATEMENT, SUPPLIER_INFO) VALUES (");
		insert.append(receipt.getReceiptId()).append(", ");
		insert.append("'").append(receipt.isGhsProductName() ? "Y" : "N").append("', ");
		insert.append("'").append(receipt.isGhsSignalWord() ? "Y" : "N").append("', ");
		insert.append("'").append(receipt.isGhsPictogram() ? "Y" : "N").append("', ");
		insert.append("'").append(receipt.isGhsHazardStatement() ? "Y" : "N").append("', ");
		insert.append("'").append(receipt.isGhsPrecautionaryStatement() ? "Y" : "N").append("', ");
		insert.append("'").append(receipt.isGhsSupplierInfo() ? "Y" : "N").append("'");
		insert.append(")");
		try {
			factory.deleteInsertUpdate(insert.toString(), conn);
		}
		catch (Exception e) {
			JSONObject rowError = new JSONObject();
			try {
				rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
				rowError.put("errorMessage", "Error inserting into RECEIPT_GHS_LABEL_REQS: " + e.getMessage());
				rowError.put("errorSQL", insert.toString());
			}
			catch (JSONException e2) {
				e2.printStackTrace();
			}
			errors.put(rowError);
		}

	}

	private void saveReceiptComponent(Shipment shipment, KitComponent component, BigDecimal receiptId, BigDecimal itemId, Connection conn, JSONArray errors) throws BaseException {
		Collection cin = new Vector();

		cin.add(receiptId);
		cin.add(itemId);
		cin.add(component.getPartId() != null ? component.getPartId() : "");
		cin.add(StringHandler.isBlankString(component.getLot()) ? "" : component.getLot());
		cin.add(StringHandler.isBlankString(component.getBin()) ? "" : component.getBin());
		cin.add(getTimestamp(component.getExpireDate()));
		cin.add(getTimestamp(component.getExpireDate()));

		try {
			factory.doProcedure(conn, "P_RECEIPT_COMPONENT", cin);
		}
		catch (Exception e) {
			JSONObject rowError = new JSONObject();
			try {
				rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
				rowError.put("errorMessage", "Error calling P_RECEIPT_COMPONENT: " + e.getMessage());
				rowError.put("errorSQL", "args: " + cin);
			}
			catch (JSONException e2) {
				e2.printStackTrace();
			}
			errors.put(rowError);
		}
	}

	private JSONObject saveInboundShipmentDetail(Shipment shipment, ReceivingReceipt receipt, Connection conn) throws BaseException {
		String shipmentDetailId = "" + dbManager.getOracleSequence("INBOUND_SHIPMENT_DETAIL_ID_SEQ");

		StringBuilder sql = new StringBuilder("INSERT INTO INBOUND_SHIPMENT_DETAIL");
		sql.append("( INBOUND_SHIPMENT_ID, INBOUND_SHIPMENT_DETAIL_ID, RECEIVING_PRIORITY, RADIAN_PO, TRANSFER_REQUEST_ID, CUSTOMER_RMA_ID, DOC_NUM ) VALUES (");
		sql.append(shipment.getShipmentId()).append(", ");
		sql.append(shipmentDetailId).append(", ");
		sql.append("'Normal', ");
		sql.append(receipt.hasPo() ? receipt.getPo() : "null").append(", ");
		sql.append(receipt.hasTransferRequestId() ? receipt.getTransferRequestId() : "null").append(", ");
		sql.append(receipt.hasCustomerRmaId() ? receipt.getCustomerRmaId() : "null").append(", ");
		sql.append(receipt.hasDocNum() ? receipt.getDocNum() : "null");
		sql.append(")");
		try {
			factory.deleteInsertUpdate(sql.toString(), conn);
			receipt.setShipmentDetailId(shipmentDetailId);
		}
		catch (Exception e) {
			JSONObject rowError = new JSONObject();
			try {
				rowError.put("id", shipment.hasId() ? shipment.getId() : "UNKNOWN");
				rowError.put("errorMessage", "Error inserting into INBOUND_SHIPMENT_DETAIL: " + e.getMessage());
				rowError.put("errorSQL", sql.toString());
			}
			catch (JSONException e2) {
				e2.printStackTrace();
			}
			return rowError;
		}
		return null;

	}
	
	private boolean isMvItem(BigDecimal itemId, Connection conn) throws BaseException {
		String query = "select item_type from item where item_id = " + itemId;
		
		String itemType = factory.selectSingleValue(query, conn);
		
		return "MV".equals(itemType);
	}

	private boolean responseIsOK(String response) {
		return StringHandler.isBlankString(response) || "OK".equals(response);
	}
	
	private String getDefinedShelfLifeItem(BigDecimal po, BigDecimal poLine, Connection conn)  throws BaseException {
		String query = "select pkg_defined_shelf_life.fx_defined_shelf_life_item (" + po + ", " + poLine + ") from dual";
		
		return factory.selectSingleValue(query, conn);
	}

}
