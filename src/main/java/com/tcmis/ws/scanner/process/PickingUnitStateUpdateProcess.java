package com.tcmis.ws.scanner.process;

import java.sql.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;

import radian.tcmis.common.util.SqlHandler;

public class PickingUnitStateUpdateProcess extends GenericProcess {

	public PickingUnitStateUpdateProcess(String client) {
		super(client);
	}

	public void updatePickingUnitState(JSONObject input) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			updatePickingUnit(input, conn);
			updatePickingUnitValidation(input, conn);
			if (input.has("pdocs")) {
				updatePdoc(input.getInt("pickingUnitId"), input.getJSONArray("pdocs"), conn);
			}
		}
		catch (JSONException e) {
			throw new BaseException(e);
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	private void updatePdoc(int pickingUnitId, JSONArray pdocs, Connection conn) throws BaseException, JSONException {
		factory.deleteInsertUpdate("DELETE FROM picking_unit_pdoc WHERE picking_unit_id=" + pickingUnitId, conn);
		for (int i = 0; i < pdocs.length(); i++) {
			factory.deleteInsertUpdate("INSERT INTO picking_unit_pdoc (picking_unit_id, pdoc) values (" + pickingUnitId + ", " + pdocs.getInt(i) + ")", conn);
		}
	}

	private void updatePickingUnit(JSONObject input, Connection conn) throws BaseException, JSONException {

		String stmt = "update picking_unit set last_updated_by=" + input.getInt("personnelId");

		if (input.has("pickingState")) {
			stmt += ", picking_state=" + SqlHandler.delimitString(input.getString("pickingState"));
		}

		if (input.has("bin")) {
			stmt += ", bin=" + SqlHandler.delimitString(input.getString("bin"));
		}

		if (input.has("pickingGroupId")) {
			stmt += ", picking_group_id=" + input.getInt("pickingGroupId");
		}

		if (input.has("boxCount")) {
			stmt += ", box_count=" + input.getInt("boxCount");
		}

		if (input.has("totalWeight")) {
			stmt += ", total_weight =" + input.getString("totalWeight");
		}

		if (input.has("tabletShipmentId")) {
			stmt += ", tablet_shipment_id =" + SqlHandler.delimitString(input.getString("tabletShipmentId"));
		}

		if (input.has("packageType")) {
			stmt += ", package_type =" + SqlHandler.delimitString(input.getString("packageType"));
		}

		if (input.has("hub")) {
			stmt += ", hub =" + SqlHandler.delimitString(input.getString("hub"));
		}

		stmt += " where picking_unit_id = " + input.getInt("pickingUnitId");

		factory.deleteInsertUpdate(stmt, conn);

	}

	private void updatePickingUnitValidation(JSONObject input, Connection conn) throws BaseException, JSONException {
		int pickingUnitId = input.getInt("pickingUnitId");
		String qcNotes = input.optString("qcNotes");
		String pickNotes = input.optString("pickNotes");
		String holdNote = "";

		if (!(qcNotes == null || qcNotes.isEmpty() || input.isNull("qcNotes"))) {
			holdNote = qcNotes + "; ";
		}
		if (!(pickNotes == null || pickNotes.isEmpty() || input.isNull("pickNotes"))) {
			holdNote += pickNotes + "; ";
		}
		if (!holdNote.isEmpty()) {
			String stmt = "update picking_unit set hold_note = to_char(sysdate) || ' - ' || rtrim(" + SqlHandler.delimitString(holdNote) + " || hold_note)"
					+ " where picking_unit_id = " + pickingUnitId;
			factory.deleteInsertUpdate(stmt, conn);
		}
	}
}
