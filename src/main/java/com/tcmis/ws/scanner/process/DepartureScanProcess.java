package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ShipConfirmInputBean;
import com.tcmis.internal.hub.process.ShipConfirmProcess;
import com.tcmis.ws.scanner.beans.DepartureShipment;
import com.tcmis.ws.scanner.beans.PickingUnit;
import com.tcmis.ws.scanner.beans.TabletShipment;

public class DepartureScanProcess extends BaseScannerPickProcess {
	public DepartureScanProcess(String client) {
		super(client);
	}

	public JSONArray prepareShipment(DepartureShipment shipment, JSONArray errors) throws BaseException {
		Connection conn = dbManager.getConnection();
		JSONObject currentError = null;
		try {
			conn.setAutoCommit(false);
			currentError = updateShipment(shipment, conn);
			if (currentError != null) {
				errors.put(currentError);
				log.debug("*** ROLLING BACK CHANGES FOR prepareShipment ***");
				conn.rollback();
			}
			else {
				conn.commit();
			}
		}
		catch (SQLException e) {
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				log.error("Error rolling back in prepareShipment", e1);
			}
			throw new BaseException("Error doing prepareShipment", e);
		}
		finally {
			try {
				conn.setAutoCommit(true);
			}
			catch (SQLException e) {
				log.error("Error committing prepareShipment", e);
			}
			dbManager.returnConnection(conn);
		}
		return errors;
	}

	public JSONArray updateShipment(DepartureShipment shipment, JSONArray errors) throws BaseException {
		Connection conn = dbManager.getConnection();
		JSONObject currentError = null;
		try {
			conn.setAutoCommit(false);
			currentError = updateShipmentData(shipment, conn);
			if (currentError != null) {
				errors.put(currentError);
				log.debug("*** ROLLING BACK CHANGES FOR updateShipment ***");
				conn.rollback();
			}
			else {
				conn.commit();
			}
		}
		catch (SQLException e) {
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				log.error("Error rolling back in updateShipment", e1);
			}
			throw new BaseException("Error doing updateShipment", e);
		}
		finally {
			try {
				conn.setAutoCommit(true);
			}
			catch (SQLException e) {
				log.error("Error committing updateShipment", e);
			}
			dbManager.returnConnection(conn);
		}
		return errors;
	}

	public JSONArray confirmShipment(DepartureShipment shipment, JSONArray errors) throws BaseException {
		JSONObject currentError = callShipConfirm(shipment);
		if (currentError != null) {
			errors.put(currentError);
		}
		else {
			Connection conn = dbManager.getConnection();
			try {
				currentError = updateShipmentData(shipment, conn);
			}
			catch (Exception e) {
				currentError = getError(e, "Error updating Shipment Table", shipment.getId(), "");
			}
			finally {
				dbManager.returnConnection(conn);
			}
			if (currentError != null) {
				errors.put(currentError);
			}
		}
		return errors;
	}

	public JSONArray addPicksToShipment(DepartureShipment shipment, JSONArray errors) throws BaseException {
		Connection conn = dbManager.getConnection();
		JSONObject currentError = null;
		try {
			conn.setAutoCommit(false);
			currentError = updateShipment(shipment, conn);
			if (currentError != null) {
				errors.put(currentError);
				log.debug("*** ROLLING BACK CHANGES FOR addPickToShipment ***");
				conn.rollback();
			}
			else {
				conn.commit();
			}
		}
		catch (SQLException e) {
			try {
				conn.rollback();
			}
			catch (SQLException e1) {
				log.error("Error rolling back in addPickToShipment", e1);
			}
			throw new BaseException("Error doing addPickToShipment", e);
		}
		finally {
			try {
				conn.setAutoCommit(true);
			}
			catch (SQLException e) {
				log.error("Error committing addPickToShipment", e);
			}
			dbManager.returnConnection(conn);
		}
		return errors;
	}

	protected Collection<TabletShipment> getShipmentCrossReference(String tabletShipmentId, Connection conn) throws BaseException {
		factory.setBean(new TabletShipment());
		@SuppressWarnings("unchecked")
		Collection<TabletShipment> beans = factory.selectQuery("select t.* from tablet_shipment t where t.tablet_shipment_id = " + SqlHandler.delimitString(tabletShipmentId),
				conn);
		return beans;
	}

	private PickingUnit getPick(PickingUnit pick, Connection conn) throws BaseException {
		factory.setBean(new PickingUnit());
		@SuppressWarnings("unchecked")
		Collection<PickingUnit> beans = factory.selectQuery("select * from picking_unit p where p.picking_unit_id = " + pick.getPickingUnitId(), conn);
		return beans.isEmpty() ? null : beans.iterator().next();
	}

	private JSONObject updatePick(PickingUnit pick, Connection conn) throws BaseException {
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("UPDATE picking_unit SET tablet_shipment_id = ").append(SqlHandler.delimitString(pick.getTabletShipmentId()));
			if (pick.hasTrackingNumbers()) {
				sql.append(", tracking_numbers = ").append(SqlHandler.delimitString(pick.getTrackingNumbers()));
			}
			if (pick.hasPersonnelId()) {
				sql.append(", last_updated_by = ").append(pick.getPersonnelId());
			}
			sql.append(", picking_state = 'SHIP_READY'");
			sql.append(" WHERE picking_unit_id = ").append(pick.getPickingUnitId());
			factory.deleteInsertUpdate(sql.toString(), conn);
		}
		catch (Exception e) {
			return getError(e, "Error updating picking_unit:", "", sql.toString());
		}

		return null;

	}

	private JSONObject updateShipmentData(DepartureShipment shipment, Connection conn) throws BaseException {
		StringBuilder sql = new StringBuilder();
		try {
			Collection<TabletShipment> shipmentCrossRef = getShipmentCrossReference(shipment.getTabletShipmentId(), conn);

			for (TabletShipment currentShipment : shipmentCrossRef) {
				sql.setLength(0);
				boolean firstUpdateColAdded = false;
				sql.append("UPDATE shipment SET");
				if (shipment.isErgCheckedSet()) {
					firstUpdateColAdded = true;
					sql.append(" erg_checked = ").append(shipment.isErgChecked() ? "'Y'" : "'N'");
				}
				if (shipment.isPlacardsRequiredSet()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" placards_required = ").append(shipment.isPlacardsRequired() ? "'Y'" : "'N'");
					sql.append(", placards_supplied = ").append(shipment.isPlacardsSupplied() ? "'Y'" : "'N'");
				}
				if (shipment.hasTrackingNumber()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" tracking_number = ").append(SqlHandler.delimitString(shipment.getTrackingNumber()));
				}
				if (shipment.hasPalletTrackingNumbers()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" pallet_tracking_number = ").append(SqlHandler.delimitString(shipment.getPalletTrackingNumbers()));
				}
				if (shipment.hasCarrierName()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" carrier_code = ").append(SqlHandler.delimitString(shipment.getCarrierName()));
				}
				if (shipment.hasAlternateCarrier()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" alternate_carrier = ").append(SqlHandler.delimitString(shipment.getAlternateCarrier()));
				}
				if (shipment.hasFreightChargePaymentSource()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" freight_charge_payment_source = ").append(SqlHandler.delimitString(shipment.getFreightChargePaymentSource()));
				}
				if (shipment.hasGrossWeight()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" gross_weight = ").append(shipment.getGrossWeight());
				}
				if (shipment.hasDryIceWeight()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" dry_ice_weight_lbs = ").append(shipment.getDryIceWeight());
				}
				if (shipment.hasNumberOfPallets()) {
					if (firstUpdateColAdded) {
						sql.append(",");
					}
					else {
						firstUpdateColAdded = true;
					}
					sql.append(" number_of_pallets = ").append(shipment.getNumberOfPallets());
				}
				sql.append(" WHERE shipment_id = ").append(currentShipment.getShipmentId());
				if (firstUpdateColAdded) {
					factory.deleteInsertUpdate(sql.toString(), conn);
				}
			}
		}
		catch (Exception e) {
			return getError(e, "Error updating shipment:", "", sql.toString());
		}

		return null;

	}

	private void updateSampleData(DepartureShipment shipment) throws BaseException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE picking_unit pu SET picking_state = 'SHIP_CONFIRMED'");
		sql.append(" WHERE tablet_shipment_id = ").append(SqlHandler.delimitString(shipment.getTabletShipmentId()));
		sql.append(" AND EXISTS (SELECT null FROM picking_unit_sample pus WHERE pus.picking_unit_id = pu.picking_unit_id)");
		factory.deleteInsertUpdate(sql.toString());
	}

	private Timestamp getTimestamp(Date date) {
		return date != null ? new Timestamp(date.getTime()) : null;
	}

	private boolean responseIsOK(Vector results) {
		String response = (String) results.firstElement();
		log.debug("Procedure call returned: " + response);
		return StringHandler.isBlankString(response) || "0".equals(response);
	}

	private JSONObject updateShipment(DepartureShipment shipment, Connection conn) throws BaseException {
		StringBuilder sql = new StringBuilder();

		try {
			BigDecimal shipmentId = null;
			HashMap<String, BigDecimal> shipmentIds = new HashMap<String, BigDecimal>();
			ArrayList<BigDecimal> newShipmentIds = new ArrayList<BigDecimal>();

			// Collect all existing shipmentIds for this tabletShipment, one per
			// inventoryGroup
			Collection<TabletShipment> shipmentCrossRefs = getShipmentCrossReference(shipment.getTabletShipmentId(), conn);
			for (TabletShipment shipmentCrossRef : shipmentCrossRefs) {
				String inventoryGroup = factory.selectSingleValue("select inventory_group from shipment where shipment_id = " + shipmentCrossRef.getShipmentId(), conn);
				shipmentIds.put(inventoryGroup, shipmentCrossRef.getShipmentId());
			}

			for (PickingUnit pick : shipment.getPickingUnits()) {
				String inventoryGroup = factory.selectSingleValue(
						"select i.inventory_group from picking_unit_issue pui, issue i where i.issue_id = pui.issue_id and pui.picking_unit_id = " + pick.getPickingUnitId(), conn);
				if (shipmentIds.containsKey(inventoryGroup)) {
					// There is already a shipment for this tabletShipment with
					// this inventoryGroup
					shipmentId = shipmentIds.get(inventoryGroup);
				}
				else {
					// NO existing shipment for this tabletShipment with this
					// inventoryGroup
					shipmentId = factory.getSequence("shipment_seq");
					shipmentIds.put(inventoryGroup, shipmentId);
					newShipmentIds.add(shipmentId);
				}
				pick.setInventoryGroup(inventoryGroup);
			}

			for (String inventoryGroup : shipmentIds.keySet()) {
				shipmentId = shipmentIds.get(inventoryGroup);
				factory.deleteInsertUpdate("delete from SHIPMENT_CREATION_STAGE", conn);

				for (PickingUnit pick : shipment.getPickingUnits()) {
					if (pick.getInventoryGroup().equals(inventoryGroup)) {
						// Put the pick into the SHIPMENT_CREATION_STAGE table
						sql.setLength(0);
						pick = getPick(pick, conn);
						sql.append("insert into SHIPMENT_CREATION_STAGE (PR_NUMBER,LINE_ITEM,PICKLIST_ID,SHIPMENT_ID,ACTION,DATE_INSERTED) values (");
						sql.append(pick.getPrNumber()).append(",");
						sql.append(SqlHandler.delimitString(pick.getLineItem())).append(",");
						sql.append(pick.getPicklistId()).append(",");
						sql.append(shipmentId).append(",");
						// Create if new shipmentID, update otherwise
						sql.append(newShipmentIds.contains(shipmentId) ? "'CREATE'," : "'UPDATE',");
						sql.append("sysdate)");
						factory.deleteInsertUpdate(sql.toString(), conn);
					}
				}

				// Create/update shipment(s)
				Collection inArgs = new Vector();
				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				Vector results = (Vector) factory.doProcedure(conn, "P_CREATE_SHIPMENT", inArgs, outArgs);
				if (!responseIsOK(results)) {
					return getError(null, "Error calling P_CREATE_SHIPMENT:", shipment.getId(), "P_CREATE_SHIPMENT " + results.firstElement());
				}
			}
			// Save any new tabletShipmentId <> shipmentId cross-references
			for (BigDecimal currentShipmentId : newShipmentIds) {
				sql.setLength(0);
				sql.append("insert into tablet_shipment (SHIPMENT_ID,tablet_shipment_id");
				if (shipment.hasShipDate()) {
					sql.append(", planned_ship_date");
				}
				sql.append(") values (");
				sql.append(currentShipmentId).append(",");
				sql.append(SqlHandler.delimitString(shipment.getTabletShipmentId()));
				if (shipment.hasShipDate()) {
					sql.append(",").append(DateHandler.getOracleToDateFunction(shipment.getShipDate()));
				}
				sql.append(")");
				factory.deleteInsertUpdate(sql.toString(), conn);
			}

			// Update the picking_unit table
			for (PickingUnit pick : shipment.getPickingUnits()) {
				pick.setTabletShipmentId(shipment.getTabletShipmentId());
				pick.setPersonnelId(shipment.getPersonnelId());
				if (shipment.hasShipDate()) {
					pick.setShipDate(shipment.getShipDate());
				}
				JSONObject error = updatePick(pick, conn);
				if (error != null) {
					error.put("id", shipment.getId());
					return error;
				}
			}

			// Update the picking_unit table for any added samples
			for (PickingUnit pick : shipment.getSamples()) {
				pick.setTabletShipmentId(shipment.getTabletShipmentId());
				pick.setPersonnelId(shipment.getPersonnelId());
				if (shipment.hasShipDate()) {
					pick.setShipDate(shipment.getShipDate());
				}
				JSONObject error = updatePick(pick, conn);
				if (error != null) {
					error.put("id", shipment.getId());
					return error;
				}
			}

			// Add any data for the BOL to the shipment table
			updateShipmentData(shipment, conn);
		}
		catch (Exception e) {
			return getError(e, "Error creating shipment:", shipment.getId(), sql.toString());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private JSONObject callShipConfirm(DepartureShipment shipment) throws BaseException {
		try {
			ShipConfirmProcess confirmProcess = new ShipConfirmProcess(getClient(), getLocale());
			PersonnelBean user = new PersonnelBean();
			user.setPersonnelId(shipment.getPersonnelId().intValue());
			user.setFromTablet(true);
			Collection<TabletShipment> shipmentCrossRef = Collections.EMPTY_LIST;

			Connection conn = dbManager.getConnection();
			try {
				shipmentCrossRef = getShipmentCrossReference(shipment.getTabletShipmentId(), conn);
			}
			finally {
				dbManager.returnConnection(conn);
			}
			
			ShipConfirmInputBean shipmentInput = shipmentCrossRef.stream().findFirst().map(crossRef -> {
					ShipConfirmInputBean bean = new ShipConfirmInputBean();
					bean.setDeliveredDate(shipment.hasShipDate()?shipment.getShipDate():crossRef.getPlannedShipDate());
					return bean;
				}).get();
			
			List<ShipConfirmInputBean> shipmentCollection = new ArrayList<>();
			
			// Ship confirm all shipments related to this tabletShimpentId
			for (TabletShipment currentCrossRef : shipmentCrossRef) {
				String query = "select "
						+ currentCrossRef.getShipmentId() + " shipment_id, "
						+ DateHandler.getOracleToDateFunction(shipmentInput.getDeliveredDate()) + " delivered_date, "
						+ SqlHandler.delimitString(shipment.getTrackingNumber()) + " tracking_number, "
						+ SqlHandler.delimitString(shipment.getCarrierName()) + " carrier_code, "
						+ shipment.getFreightCharge() + " freight_charge, "
						+ shipment.getPersonnelId() + " personnel_id, "
						+ "inventory_group, branch_plant "
						+ "from shipment where shipment_id = " + currentCrossRef.getShipmentId();
				
				factory.setBean(new ShipConfirmInputBean()).selectQuery(query).stream()
						.findFirst().ifPresent(bean -> shipmentCollection.add((ShipConfirmInputBean)bean));
			}
			if ( ! shipmentCollection.isEmpty()) {
				confirmProcess.confirmNotAutoShipment(user, shipmentInput, shipmentCollection);
			}

			// Ship Confirm any added Samples
			updateSampleData(shipment);
		}
		catch (Exception e) {
			return getError(e, "Error confirming shipment:", shipment.getId(), "");
		}
		return null;
	}

}
