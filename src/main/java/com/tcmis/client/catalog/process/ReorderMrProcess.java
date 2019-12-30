package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.MyWorkareaFacilityViewBean;
import com.tcmis.client.catalog.beans.ReorderMrInputBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;

import radian.tcmis.common.util.SqlHandler;

public class ReorderMrProcess extends GenericProcess {
	protected DbManager dbManager = null;
	protected GenericSqlFactory factory = null;
	protected ResourceLibrary commonResourcesLibrary = null;
	
	public ReorderMrProcess(String client) {
		super(client);
		init();
	}
	
	public ReorderMrProcess(String client, String locale) {
		super(client,locale);
		init();
	}
	
	public ReorderMrProcess(String client, Locale locale) {
		super(client,locale);
		init();
	}
	
	private void init() {
		try {
			dbManager = new DbManager(client, this.getLocale());
			factory = new GenericSqlFactory(dbManager);
			commonResourcesLibrary = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		} catch (Exception ex) {
			log.error("Error initializing InventoryManagement", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ShoppingCartBean> getMrData(ReorderMrInputBean inputBean) throws Exception {
		StringBuilder query = new StringBuilder("select company_id, facility_id, fac_part_no AS cat_part_no, part_group_no, quantity, catalog_price, currency_id");
		if (StringHandler.isBlankString(inputBean.getApplicationId()))
            query.append(", application");
		else
            query.append(", ").append(SqlHandler.delimitString(inputBean.getApplicationId())).append(" AS application");
		query.append(", application AS original_application, fx_application_desc(facility_id,application,company_id) AS application_display");
		query.append(", catalog_company_id, catalog_id, 0 projected_lead_time, baseline_price, item_type AS stocking_method, item_type, inventory_group,");
		query.append(" FX_CAT_PART_DESC(catalog_company_id, catalog_id, fac_part_no) part_description,");
		query.append(" fx_kit_packaging(fx_best_item(catalog_id, fac_part_no, catalog_company_id, part_group_no)) example_packaging,");
		query.append(" fx_cat_part_use_approved(catalog_company_id, company_id, catalog_id, ");
		if ("Y".equalsIgnoreCase(inputBean.getIsFirstLoad()))
			query.append("facility_id, application");
		else
			query.append(SqlHandler.delimitString(inputBean.getFacilityId())).append(", ").append(SqlHandler.delimitString(StringHandler.isBlankString(inputBean.getApplicationId()) ? "XXXXXXX" : inputBean.getApplicationId()));
		query.append(", fac_part_no, part_group_no) part_approved");
		query.append(" from request_line_item where pr_number = ").append(inputBean.getPrNumber());
		query.append(" order by line_item");
		
		return factory.setBean(new ShoppingCartBean()).selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MyWorkareaFacilityViewBean> getDropdownCollection(BigDecimal personnelId) throws Exception {
		Vector<MyWorkareaFacilityViewBean> dropdownColl = (Vector<MyWorkareaFacilityViewBean>) new OrderTrackingProcess(getClient(), getLocale()).getMyWorkareaFacilityViewBean(personnelId,"ReorderMR");
		
		CatalogInputBean catalogInputBean = new CatalogInputBean();
		CatalogProcess catalogProcess = new CatalogProcess(getClient(), getLocale());
		for (MyWorkareaFacilityViewBean bean : dropdownColl) {
			catalogInputBean.setFacilityId(bean.getFacilityId());
			bean.setPrRulesViewBeanCollection(catalogProcess.getPrRulesForFacility(catalogInputBean));
		}
		
		return dropdownColl;
	}
}