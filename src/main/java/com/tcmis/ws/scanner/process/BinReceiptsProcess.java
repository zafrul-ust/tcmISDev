package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.icu.text.SimpleDateFormat;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.internal.hub.beans.SplitReceiptBean;

import radian.tcmis.common.util.SqlHandler;
import radian.tcmis.common.util.StringHandler;

public class BinReceiptsProcess extends GenericProcess {

	public BinReceiptsProcess(String client) {
		super(client);
	}
	
	public String binReceipts(JSONObject input) throws JSONException, Exception {
		BigDecimal personnelId = new BigDecimal(input.getInt("personnelId"));
		String hubId = input.getString("hub");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date binDate = sdf.parse(input.getString("binDate"));
		JSONArray receipts = input.getJSONArray("receipts");
		String errorMsg = "";
		for (int i = 0; i < receipts.length();i++) {
			JSONObject receipt = receipts.getJSONObject(i);
			errorMsg = binReceipt(personnelId,hubId,binDate, receipt);
			if ( ! StringHandler.isBlankString(errorMsg)) {
				break;
			}
		}
		return errorMsg;
	}
	
	private String binReceipt(BigDecimal personnelId, String hubId, Date binDate, JSONObject receipt) throws JSONException, Exception {
		String errorMsg = "";
		JSONArray kitComponents = null;
		BigDecimal newReceiptId = null;
		try {
			kitComponents = receipt.getJSONArray("kitComponents");
		}
		catch(JSONException e) { /* do nothing */ }
		try {
			newReceiptId = new BigDecimal(receipt.getInt("newReceiptId"));
		}
		catch(JSONException e) { /* do nothing */ }

		String newBin = receipt.getString("newBin");
		BigDecimal rid = new BigDecimal(receipt.getInt("originalReceiptId"));
		if (newReceiptId != null) {
			BigDecimal quantity = new BigDecimal(receipt.getInt("quantity"));
			errorMsg = splitReceipt(personnelId, hubId, newBin, binDate, quantity, rid, newReceiptId);
		}
		else {
			if (kitComponents != null) {
				for (int i = 0; i < kitComponents.length();i++) {
					updateReceiptComponents(rid, kitComponents.getJSONObject(i));
				}
			}
			
			errorMsg = upsertReceipts(personnelId, rid, newBin);
			if ("OK".equals(errorMsg)) {
				errorMsg = "";
			}
		}
		return errorMsg;
	}
	
	private void updateReceiptComponents(BigDecimal receiptId, JSONObject component) throws JSONException, BaseException {
		BigDecimal partId = new BigDecimal(component.getInt("partId"));
		String newBin = component.getString("newBin");
		if (receiptId != null) {
			StringBuilder update = new StringBuilder("");
			update.append("update RECEIPT_COMPONENT set BIN = ").append(SqlHandler.delimitString(newBin));
			update.append(" where RECEIPT_ID = ").append(receiptId);
			update.append(" and PART_ID = ").append(partId);
			System.out.println(update.toString());
			factory.deleteInsertUpdate(update.toString());
		}
	}
	
	private String splitReceipt(BigDecimal personnelId, String hub, String bin, Date binDate, BigDecimal quantity, BigDecimal oldRid, BigDecimal newRid) throws JSONException, Exception {
		Connection connect1 = dbManager.getConnection();
		
		Collection<SplitReceiptBean> beans = factory.setBean(new SplitReceiptBean()).selectQuery(
				"select inventory_group, lot_status from receipt where receipt_id = "+oldRid, connect1);
		Iterator<SplitReceiptBean> receiptIterator = beans.iterator();
		SplitReceiptBean oldReceipt = null;
		if (receiptIterator.hasNext()) {
			oldReceipt = receiptIterator.next();
		}
		String lotStatus = oldReceipt==null?null:oldReceipt.getLotStatus();
		String inventoryGroup = oldReceipt==null?null:oldReceipt.getInventoryGroup();
		
		CallableStatement cstmt = connect1.prepareCall("{call p_receipt_split(?,?,?,?,?,?,?,?,?,?,?,?)}");
		String queryS = "call p_receipt_split(";
		Timestamp binDateTimeStamp = new Timestamp(binDate.getTime());

		cstmt.setString(1, hub); queryS += GenericProcess.getSqlString(hub);
		cstmt.setString(2, bin); queryS += ","+GenericProcess.getSqlString(bin);
		cstmt.setTimestamp(3, binDateTimeStamp); queryS += ","+GenericProcess.getSqlString(binDateTimeStamp);
		cstmt.setBigDecimal(4, quantity); queryS += ","+GenericProcess.getSqlString(quantity);
		cstmt.setString(5, lotStatus); queryS += ","+GenericProcess.getSqlString(lotStatus);
		cstmt.setBigDecimal(6, personnelId); queryS += ","+GenericProcess.getSqlString(personnelId);
		cstmt.setBigDecimal(7, oldRid); queryS += ","+GenericProcess.getSqlString(oldRid);
		cstmt.setString(8, inventoryGroup); queryS += ","+GenericProcess.getSqlString(inventoryGroup);
		cstmt.setNull(9,java.sql.Types.VARCHAR); queryS += ",??certUpdatePersonnelId??";
		cstmt.setBigDecimal(10, newRid);

		cstmt.registerOutParameter(10, Types.INTEGER); queryS += ","+GenericProcess.getSqlString(newRid);
		cstmt.registerOutParameter(11, Types.VARCHAR); queryS += ",<errmsg>";

		cstmt.setString(12, "N"); queryS += ",N";

		log.debug("query:"+queryS);
		cstmt.executeQuery();

		Integer receiptId = cstmt.getInt(10);
		String errmsg = cstmt.getString(11);

		log.debug("New Split Receipt Id: "+receiptId);
		log.debug("Error Message: "+errmsg);

		connect1.commit();
		cstmt.close();
		dbManager.returnConnection(connect1);
		
		return errmsg;
	}
	
	private boolean isQcComplete(BigDecimal receiptId, Connection conn) throws BaseException {
		return "Y".equals(factory.selectSingleValue("Select 'Y' from dual where exists (select r.receipt_id from receipt r where r.receipt_id = " + receiptId
				+ " and ( r.receiving_status = 'QC Complete' OR r.receiving_status is null ))",conn));
	}
	
	private String upsertReceipts(BigDecimal personnelId, BigDecimal receiptId, String bin) throws BaseException {
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection conn = dbManager.getConnection();
		try {
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
	
			inArgs = new Vector();
			inArgs.add(null);
			inArgs.add(receiptId);
			inArgs.add(isQcComplete(receiptId,conn)?"Binned":null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(bin);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(personnelId);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
			inArgs.add(null);
	
			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
	
			Vector results = (Vector) factory.doProcedure("PKG_HUB_AUTOMATION.P_UPSERT_RECEIPT", inArgs, outArgs);
			errorMsg = (String)results.firstElement();
		}
		finally {
			dbManager.returnConnection(conn);
		}

		return errorMsg;
	}
}
