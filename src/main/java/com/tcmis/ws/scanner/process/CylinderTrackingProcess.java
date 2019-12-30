package com.tcmis.ws.scanner.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.ws.scanner.beans.Cylinder;

public class CylinderTrackingProcess extends BaseScannerPickProcess {
	public CylinderTrackingProcess(String client) {
		super(client);
	}

	public JSONArray trackCylinder(Cylinder cylinder, JSONArray errors) {
		try {
			Cylinder existingCylinder = getCylinderTrackingId(cylinder);
			saveCylinderTracking(cylinder, existingCylinder, errors);
		}
		catch (BaseException e) {
			errors.put(getError(e, "Error tracking cylinder: ", cylinder.getId(), null));
		}

		return errors;
	}

	private Cylinder getCylinderTrackingId(Cylinder refurb) throws BaseException {
		StringBuilder query = new StringBuilder("select * from cylinder_tracking where cylinder_tracking_id in (SELECT MAX( cylinder_tracking_id ) FROM cylinder_tracking");
		query.append(" WHERE SUPPLIER = ").append(SqlHandler.delimitString(refurb.getSupplier()));
		query.append(" AND manufacturer_id_no = ").append(SqlHandler.delimitString(refurb.getManufacturerIdNo()));
		query.append(" AND serial_number = ").append(SqlHandler.delimitString(refurb.getSerialNumber()));
		query.append(")");
		factory.setBean(new Cylinder());
		Collection<Cylinder> results =  factory.selectQuery(query.toString());
		if (results != null && !results.isEmpty()){
			return results.iterator().next();
		}
		return new Cylinder();
	}

	private boolean responseIsOK(Vector results) {
		String response = "" + results.firstElement();
		return StringHandler.isBlankString(response) || "OK".equals(response);
	}

	private void saveCylinderTracking(Cylinder cylinder, Cylinder existingCylinder, JSONArray errors) {
		Vector cin = new Vector();
		cin.addElement((!existingCylinder.hasCylinderTrackingId() || existingCylinder.isOut()) ? null : existingCylinder.getCylinderTrackingId());
		cin.addElement(cylinder.getSupplier());
		cin.addElement("SUPPLIER");
		cin.addElement(cylinder.getSerialNumber());
		cin.addElement(cylinder.getManufacturerIdNo());
		cin.addElement(cylinder.getVendorPartNo());
		cin.addElement(cylinder.hasCorrespondingNsn() ? cylinder.getCorrespondingNsn() : existingCylinder.getCorrespondingNsn());//
		cin.addElement(cylinder.getRefurbCategory());
		cin.addElement(cylinder.getConversionGroup());
		cin.addElement(cylinder.getLatestHydroTestDate());
		cin.addElement(cylinder.hasStatus() ? cylinder.getStatus() : existingCylinder.getStatus());//
		cin.addElement(cylinder.getLocationId());
		cin.addElement(cylinder.getCylinderConditionCode());
		cin.addElement(cylinder.getCylinderStatus());
		cin.addElement(cylinder.getReturnFromLocation());
		cin.addElement(cylinder.getLastShippedDodaac());
		cin.addElement(cylinder.getPersonnelId());
		cin.addElement(cylinder.hasDocumentNumber() ? cylinder.getDocumentNumber() : existingCylinder.getDocumentNumber());//
		cin.addElement(cylinder.getNotes());
		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));

		try {
			Vector results = (Vector) factory.doProcedure("pkg_cylinder_tracking.p_cylinder_tracking_upsert", cin, cout);
			if (!responseIsOK(results)) {
				errors.put(getError("Error calling: pkg_cylinder_tracking.p_cylinder_tracking_upsert: " + results.firstElement(), cylinder.getId()));
			}
		}
		catch (BaseException e) {
			errors.put(getError(e, "Error calling: pkg_cylinder_tracking.p_cylinder_tracking_upsert ", cylinder.getId(), null));
		}

	}
}
