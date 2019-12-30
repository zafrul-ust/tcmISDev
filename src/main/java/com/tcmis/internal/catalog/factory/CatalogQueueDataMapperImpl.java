package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;

public class CatalogQueueDataMapperImpl extends GenericSqlFactory implements CatalogQueueDataMapper {

	public CatalogQueueDataMapperImpl(DbManager dbManager) {
		super(dbManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<VvLocaleBean> possibleAlternateLocales(CatalogQueueBean workQueueItem) throws BaseException {
		Objects.requireNonNull(workQueueItem);
		return (Collection<VvLocaleBean>)
				this.setBean(new VvLocaleBean()).selectQuery(
						"select l.locale_display, l.locale_code from vv_locale l, facility_locale fl"
								+ ", catalog_add_request_new carn, catalog_queue q"
								+ " where carn.request_id = q.catalog_add_request_id"
								+ " and carn.company_id = fl.company_id"
								+ " and carn.eng_eval_facility_id = fl.facility_id"
								+ " and l.locale_code = fl.locale_code"
								+ " and q.q_id = " + workQueueItem.getqId()
								+ " order by fl.display_order");
	}

	@Override
	public void changeQueueLocale(BigDecimal qId, String task, String localeOverride) throws BaseException {
		deleteInsertUpdate("update catalog_queue q "
				+ "set (q.locale_code, q.catalog_vendor_assignment_id, q.unit_price, q.currency_id) = "
				+ "(select cqi.locale_code"
				+ ", nvl(cva.catalog_vendor_assignment_id, q.catalog_vendor_assignment_id) catalog_vendor_assignment_id" 
				+ ", nvl(nvl(cva.unit_price, cva.sourcing_unit_price), q.unit_price) unit_price"
				+ ",nvl(cva.currency_id, q.currency_id) currency_id"
				+ " from catalog_vendor_assignment cva, catalog_queue_item cqi"
				+ " where cqi.locale_code = " + SqlHandler.delimitString(localeOverride)
				+ " and cqi.task = " + SqlHandler.delimitString(task)
				+ " and cqi.catalog_queue_item_id = cva.catalog_queue_item_id(+)"
				+ " and cva.status(+) = 'A'"
				+ " and cva.supplier(+) = nvl(q.alt_supplier, q.supplier)"
				+ " and cva.supplier(+) not in (" 
				+ CatalogQueueBean.WESCO_SUPPLIER_ID 
				+ ", " + CatalogQueueBean.WESCO_ALT_SUPPLIER_ID + "))"
				+ " where q.q_id = " + qId);
	}

	@Override
	public void changeItemLocaleByQId(BigDecimal qId, String localeOverride) throws BaseException {
		deleteInsertUpdate("update catalog_add_item_locale set to_locale_code = " 
				+ SqlHandler.delimitString(localeOverride)
				+ " where catalog_add_item_id = "
				+ "(select catalog_add_item_id from catalog_queue where q_id = " + qId + ")");
	}
}
