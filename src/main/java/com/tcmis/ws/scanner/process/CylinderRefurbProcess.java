package com.tcmis.ws.scanner.process;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.ws.scanner.beans.CylinderRefurb;

public class CylinderRefurbProcess extends BaseScannerPickProcess {
	public CylinderRefurbProcess(String client) {
		super(client);
	}

	public JSONArray refurbCylinder(CylinderRefurb cylinder, JSONArray errors) {
		try {
			String currentTrackingId = getCylinderTrackingId(cylinder);

			if (StringUtils.isBlank(currentTrackingId)) {
				errors.put(getError("No Cylinder found to refurbish", cylinder.getId()));
			}
			else {
				String refurbPriceId = getRefurbPriceId(cylinder);
				if (StringUtils.isBlank(refurbPriceId)) {
					errors.put(getError("No cylinder_refurb_price found for '" + cylinder.getRefurbCategory() + "', '" + cylinder.getRefurbSubcategory() + "'", cylinder.getId()));
				}
				else {
					saveCylinderRefurb(cylinder, currentTrackingId, refurbPriceId, errors);
				}
			}
		}
		catch (BaseException e) {
			errors.put(getError(e, "Error refurbing cylinder: ", cylinder.getId(), null));
		}

		return errors;
	}

	private String getCylinderTrackingId(CylinderRefurb refurb) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT MAX( cylinder_tracking_id ) FROM cylinder_tracking");
		query.append(" WHERE SUPPLIER = ").append(SqlHandler.delimitString(refurb.getSupplier()));
		query.append(" AND manufacturer_id_no = ").append(SqlHandler.delimitString(refurb.getManufacturerIdNo()));
		query.append(" AND serial_number = ").append(SqlHandler.delimitString(refurb.getSerialNumber()));
		return factory.selectSingleValue(query.toString());
	}

	private String getRefurbPriceId(CylinderRefurb refurb) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT	a.cylinder_refurb_price_id");
		query.append(" FROM (");
		query.append("   SELECT   crp.cylinder_refurb_price_id, ROW_NUMBER( ) OVER (ORDER BY crp.start_date DESC) row_num");
		query.append("   FROM cylinder_refurb_price crp");
		query.append("   WHERE crp.SUPPLIER = ").append(SqlHandler.delimitString(refurb.getSupplier()));
		query.append("   AND crp.refurb_category = ").append(SqlHandler.delimitString(refurb.getRefurbCategory()));
		query.append("   AND crp.refurb_subcategory = ").append(SqlHandler.delimitString(refurb.getRefurbSubcategory()));
		query.append("   AND crp.start_date <= sysdate");
		query.append("   )");
		query.append(" a where a.row_num = 1");
		return factory.selectSingleValue(query.toString());
	}

	private void saveCylinderRefurb(CylinderRefurb cylinder, String cylinderTrackingId, String cylinderRefurbPriceId, JSONArray errors) {
		StringBuilder insert = new StringBuilder();
		insert.append(
				"insert into cylinder_refurb_trans (cylinder_tracking_id, cylinder_refurb_price_id, refurb_category, refurb_subcategory, date_serviced, inserted_by, date_inserted)");
		insert.append(" values(");
		insert.append(cylinderTrackingId).append(", ");
		insert.append(cylinderRefurbPriceId).append(", ");
		insert.append(SqlHandler.delimitString(cylinder.getRefurbCategory())).append(", ");
		insert.append(SqlHandler.delimitString(cylinder.getRefurbSubcategory())).append(", ");
		insert.append(DateHandler.getOracleToDateFunction(cylinder.getDateServiced())).append(", ");
		insert.append(cylinder.getPersonnelId()).append(", ");
		insert.append("sysdate)");
		try {
			factory.deleteInsertUpdate(insert.toString());
		}
		catch (Exception e) {
			errors.put(getError(e, "Error inserting into cylinder_refurb_trans: ", cylinder.getId(), insert.toString()));
		}
	}
}
