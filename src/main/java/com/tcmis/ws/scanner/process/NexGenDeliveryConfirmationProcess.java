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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PageBean;
import com.tcmis.common.admin.beans.PersonnelBean;
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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.GHSLabelReqsBean;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.beans.ReceiptDocumentViewBean;
import com.tcmis.internal.hub.beans.ReceivingStatusViewBean;
import com.tcmis.ws.scanner.beans.BinItem;
import com.tcmis.ws.scanner.beans.Facility;
import com.tcmis.ws.scanner.beans.Receipt;
import com.tcmis.ws.scanner.beans.UserCompanyFacilityBean;
import com.tcmis.ws.tablet.beans.ReceiptComponentBean;
import com.tcmis.ws.tablet.beans.TabletInputBean;

public class NexGenDeliveryConfirmationProcess extends BaseScannerSearchProcess {

	String SQL = "insert into delivery_confirmation (company_id,pr_number,line_item,shipment_id,date_shipped,quantity_shipped,comments,confirmed_by,date_confirmed) select company_id, pr_number, line_item, shipment_id, date_delivered date_shipped, sum(quantity) quantity_shipped, 'Confirmation scan' comments, ? confirmed_by, ? date_confirmed from issue i where i.shipment_id = ? and not exists (select shipment_id from delivery_confirmation dc where i.company_id = dc.company_id and i.pr_number = dc.pr_number and i.line_item = dc.line_item and i.shipment_id = dc.shipment_id) GROUP BY company_id,pr_number,line_item,shipment_id, date_delivered";

	// insert into delivery_confirmation
	// (company_id,pr_number,line_item,shipment_id,date_shipped,quantity_shipped,comments,confirmed_by,date_confirmed)
	// select company_id,
	// pr_number,
	// line_item,
	// shipment_id,
	// date_delivered date_shipped,
	// sum(quantity) quantity_shipped,
	// 'Confirmation scan' comments,
	// ? confirmed_by,
	// ? date_confirmed
	// from issue i
	// where
	// i.shipment_id = ?
	// and not exists
	// (select shipment_id
	// from delivery_confirmation dc
	// where i.company_id = dc.company_id
	// and i.pr_number = dc.pr_number
	// and i.line_item = dc.line_item
	// and i.shipment_id = dc.shipment_id)
	// GROUP BY company_id,
	// pr_number,
	// line_item,
	// shipMent_id,
	// date_delivered

	public NexGenDeliveryConfirmationProcess(String client) {
		super(client);
	}

	public JSONArray confirmDelivery(JSONArray confirmations) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		PreparedStatement preparedStatement = null;
		int i = 0;
		JSONArray errors = new JSONArray();
		
		try {
			preparedStatement = connection.prepareStatement(SQL);
			JSONObject confirmation = null;
			for (i = 0; i < confirmations.length(); i++) {
				try {
					confirmation = confirmations.getJSONObject(i);
					preparedStatement.setInt(1, confirmation.getInt("personnelId"));
					preparedStatement.setTimestamp(2, new java.sql.Timestamp(DateHandler.getDateFromIso8601String(confirmation.getString("confirmationDate")).getTime()));
					preparedStatement.setInt(3, confirmation.getInt("shipmentId"));
					preparedStatement.executeUpdate();
				}
				catch (JSONException je) {
					try {
						JSONObject rowError = new JSONObject();
						rowError.put("id", confirmation.has("id") ? confirmation.getString("id") : "UNKNOWN");
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
						rowError.put("id", confirmation.has("id") ? confirmation.getString("id") : "UNKNOWN");
						rowError.put("errorMessage", "Error inserting into delivery_confirmation");
						rowError.put("errorSQL", SQL + " values " + confirmation.getInt("personnelId") + ", " + confirmation.getString("confirmationDate") +", " +confirmation.getInt("shipmentId"));
						errors.put(rowError);
					}
					catch (JSONException e2) {
						e2.printStackTrace();
					}

				}
			}
		}
		catch (SQLException e2) {
			String msg = "Error inserting data into delivery_confirmation + e.getMessage()";
			throw new BaseException(msg, e2);
		}
		finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			}
			catch (SQLException e) {
				log.error("Error returning connection", e);
			}
			dbManager.returnConnection(connection);
			dbManager = null;
		}
		return errors;
	}
}
