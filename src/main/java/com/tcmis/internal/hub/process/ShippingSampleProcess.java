package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ShipConfirmViewBean;
import com.tcmis.internal.hub.beans.ShippingSampleInputBean;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for shipping sample
 * 
 * @version 1.0
 *****************************************************************************/
public class ShippingSampleProcess extends GenericProcess {
	Log	log	= LogFactory.getLog(this.getClass());

	public ShippingSampleProcess(String client, Locale locale) {
		super(client, locale);
	}
	
	public BigDecimal insertSampleData(ShippingSampleInputBean inputBean) throws BaseException {
		BigDecimal newPickingUnitId = factory.getSequence("picking_unit_id_seq");
		
		if (newPickingUnitId != null) {
			StringBuilder query = new StringBuilder("insert into picking_unit (picking_unit_id, hub, pick_date, picking_state) values");
			query.append(" (").append(newPickingUnitId);
			query.append(", ").append(inputBean.getHub());
			query.append(", sysdate, 'PRE_SHIP')");
			factory.deleteInsertUpdate(query.toString());
			
			query = new StringBuilder("insert into picking_unit_sample (picking_unit_id, receipt_id, company_id, facility_id, ship_to_location_id, quantity) values");
			query.append(" (").append(newPickingUnitId);
			query.append(", ").append(inputBean.getReceiptId());
			query.append(", ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(", ").append(SqlHandler.delimitString(inputBean.getFacilityId()));
			query.append(", ").append(SqlHandler.delimitString(inputBean.getShipToLocationId()));
			query.append(", ").append(inputBean.getQuantity()).append(")");
			factory.deleteInsertUpdate(query.toString());
		}
		
		return newPickingUnitId;
	}
	
	@SuppressWarnings("unchecked")
	public Vector<ShipConfirmViewBean> getCompanyDropDownData(BigDecimal receiptId) throws BaseException {
		Vector<ShipConfirmViewBean> result = null;
		
		StringBuilder query = new StringBuilder("select facility_id, owner_company_id company_id from receipt");
		query.append(" where receipt_id = ").append(receiptId);
		factory.setBean(new FacilityBean());
		Collection<FacilityBean> coll = factory.selectQuery(query.toString());
		if (!coll.isEmpty()) {
			String receiptOwnerCompanyId = coll.iterator().next().getCompanyId();
			String facilityId = coll.iterator().next().getFacilityId();

			query = new StringBuilder("select * from (select fig.company_id, nvl(c.company_name, fig.company_id) company_name, fig.facility_id, nvl(fx_facility_name(fig.facility_id, fig.company_id), fig.facility_id) facility_name from facility_inventory_group fig, customer.company c");
			query.append(" where fig.company_id = c.company_id");
			if (StringHandler.isBlankString(receiptOwnerCompanyId)) {
				query.append(" and fig.inventory_group in (select distinct inventory_group from receipt where receipt_id = ").append(receiptId).append(")");
				query.append(" and fig.company_id not in ('HAAS', 'Radian')");
			} else {
				query.append(" where c.company_id = ").append(SqlHandler.delimitString(receiptOwnerCompanyId));
				query.append(" and fig.facility_id = ").append(SqlHandler.delimitString(facilityId));
			}
			query.append(") x group by company_id, facility_id, company_name, facility_name order by company_name, facility_name");
			factory.setBean(new ShipConfirmViewBean());
			result = (Vector<ShipConfirmViewBean>) factory.selectQuery(query.toString());
		}
		
		return result;
	}
}