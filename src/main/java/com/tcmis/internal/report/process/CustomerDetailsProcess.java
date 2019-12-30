package com.tcmis.internal.report.process;

import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.report.beans.Address;
import com.tcmis.internal.report.beans.CatalogOverview;
import com.tcmis.internal.report.beans.CompanyFacility;
import com.tcmis.internal.report.beans.CustomerDetailsInput;
import com.tcmis.internal.report.beans.InventoryGroupOverview;
import com.tcmis.internal.report.beans.InvoiceGroupOverview;
import com.tcmis.internal.report.beans.InvoicingHeader;
import com.tcmis.internal.report.beans.OperationalHeader;
import com.tcmis.internal.report.beans.ServiceFeeMarkupRule;
import com.tcmis.internal.report.beans.ServiceFeeOverview;

public class CustomerDetailsProcess extends GenericProcess {

	public CustomerDetailsProcess(String client) {
		super(client);
	}

	public JSONObject getBillingAddressesForGrid(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"SELECT btc.customer_name AS location," +
				"       NVL (loc.address_line_1_display, loc.address_line_1) AS line_1," +
				"       NVL (loc.address_line_2_display, loc.address_line_2) AS line_2," +
				"       NVL (loc.address_line_3_display, loc.address_line_3) AS line_3," +
				"       NVL (loc.address_line_4_display, (loc.city || ', ' || loc.state_abbrev || ' ' || loc.zip)) AS line_4," +
				"       NVL (loc.address_line_5_display, loc.country_abbrev) AS line_5" +
				"  FROM customer.bill_to_customer btc, customer.location loc" +
				" WHERE btc.bill_to_company_id =  " + SqlHandler.delimitString(input.getCompanyId()) +
				"   AND btc.customer_id IN (SELECT customer_id" +
				"                             FROM customer.invoice_group_facility igf" +
				"                            WHERE igf.company_id =  " + SqlHandler.delimitString(input.getCompanyId()) +
				"                              AND igf.facility_id = " + SqlHandler.delimitString(input.getFacilityId()) + ")" +
				"   AND btc.bill_to_location_id = loc.location_id";

		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		results.put("rows", rows);

		factory.setBean(new Address());
		@SuppressWarnings("unchecked")
		Collection<Address> beans = factory.selectQuery(SQL);

		if (beans.isEmpty()) {
			JSONArray data = new JSONArray();
			data.put("No Records Found");
			data.put("");
			JSONObject row = new JSONObject();
			row.put("id", 1);
			row.put("data", data);
			rows.put(row);
		}
		else {
			int id = 1;
			for (Address bean : beans) {
				JSONArray data = new JSONArray();
				data.put(bean.getLocation());
				data.put(bean.getAddress());
				JSONObject row = new JSONObject();
				row.put("id", id++);
				row.put("data", data);
				rows.put(row);
			}
		}

		return results;
	}

	public JSONObject getCatalogsForGrid(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"SELECT cf.catalog_id," +
				"       CASE" +
				"          WHEN EXISTS" +
				"                  (SELECT NULL" +
				"                     FROM customer.cabinet_part_inventory cpi" +
				"                    WHERE cpi.company_id = cf.company_id" +
				"                      AND cpi.facility_id = cf.facility_id" +
				"                      AND cpi.catalog_id = cf.catalog_id" +
				"                      AND receipt_processing_method = 'Home Company Owned') THEN" +
				"             'Y'" +
				"          ELSE" +
				"             'N'" +
				"       END" +
				"          AS wesco_owned_cabinets," +
				"       CASE" +
				"          WHEN EXISTS" +
				"                  (SELECT NULL" +
				"                     FROM customer.fac_loc_app fla" +
				"                    WHERE fla.company_id = cf.company_id" +
				"                      AND fla.facility_id = cf.facility_id" +
				"                      AND fla.application NOT IN ('All', 'SF', 'TEST', 'Facility Wide')) " + 
				"            OR EXISTS" + 
				"                  (SELECT *" + 
				"                     FROM customer.cabinet_part_inventory cbi" + 
				"                    WHERE cbi.company_id = cf.company_id" + 
				"                      AND cbi.facility_id = cf.facility_id" + 
				"                      AND cbi.application NOT IN ('All', 'SF', 'TEST', 'Facility Wide')) THEN" +
				"             'Y'" +
				"          ELSE" +
				"             'N'" +
				"       END" +
				"          AS use_cabinets," +
				"       f.put_away_required" +
				"  FROM catalog_facility cf, customer.facility f" +
				" WHERE cf.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"   AND cf.facility_id = " + SqlHandler.delimitString(input.getFacilityId()) +
				"   AND f.company_id = cf.company_id" +
				"   AND f.facility_id = cf.facility_id" +
				"   AND cf.catalog_id != 'TCM- SALEM'";

		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		results.put("rows", rows);

		factory.setBean(new CatalogOverview());
		@SuppressWarnings("unchecked")
		Collection<CatalogOverview> beans = factory.selectQuery(SQL);

		if (beans.isEmpty()) {
			JSONArray data = new JSONArray();
			data.put("No Records Found");
			data.put("");
			data.put("");
			data.put("");
			JSONObject row = new JSONObject();
			row.put("id", 1);
			row.put("data", data);
			rows.put(row);
		}
		else {
			int id = 1;
			for (CatalogOverview bean : beans) {
				JSONArray data = new JSONArray();
				data.put(bean.getCatalogId());
				data.put(bean.isWescoOwnedCabinets());
				data.put(bean.isUseCabinets());
				data.put(bean.isPutawayRequired());

				JSONObject row = new JSONObject();
				row.put("id", id++);
				row.put("data", data);
				rows.put(row);
			}
		}

		return results;
	}

	public JSONObject getCompanies() throws BaseException, JSONException {
		String SQL = 
				"  SELECT f.facility_name," +
				"         f.facility_id," +
				"         f.company_id," +
				"         c.company_name" +
				"    FROM customer.facility f, customer.company c" +
				"   WHERE c.company_id = f.company_id" +
				"     AND f.active = 'y' " +
				"ORDER BY company_name, facility_name ASC";

		JSONObject results = new JSONObject();
		JSONArray companies = new JSONArray();
		JSONObject facilities = new JSONObject();
		JSONArray companyFacilities = new JSONArray();
		results.put("companies", companies);
		results.put("facilities", facilities);

		factory.setBean(new CompanyFacility());
		String currentCompanyId = null;
		@SuppressWarnings("unchecked")
		Collection<CompanyFacility> beans = factory.selectQuery(SQL);
		for (CompanyFacility bean : beans) {
			if (!bean.getCompanyId().equals(currentCompanyId)) {
				currentCompanyId = bean.getCompanyId();
				JSONObject company = new JSONObject();
				company.put("companyId", bean.getCompanyId());
				company.put("companyName", bean.getCompanyName());
				companies.put(company);
				companyFacilities = new JSONArray();
				facilities.put(bean.getCompanyId(), companyFacilities);
			}
			JSONObject facility = new JSONObject();
			facility.put("facilityId", bean.getFacilityId());
			facility.put("facilityName", bean.getFacilityName());
			companyFacilities.put(facility);
		}

		return results;
	}

	public JSONObject getInventoryGroupsForGrid(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"SELECT igd.inventory_group_id," +
				"       igd.inventory_group_name," +
				"       fig.status," +
				"       igd.hub," +
				"       hub.hub_name," +
				"       DECODE (igd.issue_generation, 'Item Counting', 'Y', 'N') AS use_item_counting," +
				"       CASE" +
				"          WHEN EXISTS" +
				"                  (SELECT NULL" +
				"                     FROM inventory_group_label_format label" +
				"                    WHERE label.inventory_group = igd.inventory_group_name) THEN" +
				"             'Y'" +
				"          ELSE" +
				"             'N'" +
				"       END" +
				"          AS use_container_labels" +
				"  FROM customer.facility_inventory_group fig, inventory_group_definition igd, hub" +
				" WHERE fig.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"   AND fig.facility_id = " + SqlHandler.delimitString(input.getFacilityId()) +
				"   AND igd.inventory_group(+) = fig.inventory_group" +
				"   AND hub.branch_plant(+) = igd.hub";

		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		results.put("rows", rows);

		factory.setBean(new InventoryGroupOverview());
		@SuppressWarnings("unchecked")
		Collection<InventoryGroupOverview> beans = factory.selectQuery(SQL);

		if (beans.isEmpty()) {
			JSONArray data = new JSONArray();
			data.put("No Records Found");
			data.put("");
			data.put("");
			data.put("");
			JSONObject row = new JSONObject();
			row.put("id", 1);
			row.put("data", data);
			rows.put(row);
		}
		else {
			int id = 1;
			for (InventoryGroupOverview bean : beans) {
				JSONArray data = new JSONArray();
				data.put(bean.getInventoryGroupName());
				data.put(bean.getHub() + " / " + bean.getHubName());
				data.put(bean.isUseItemCounting());
				data.put(bean.isUseContainerLabels());
				JSONObject row = new JSONObject();
				row.put("id", id++);
				row.put("data", data);
				rows.put(row);
			}
		}

		return results;
	}

	public JSONObject getInvoiceGroupsForGrid(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"SELECT igf.invoice_group," +
				"       btc.payment_terms," +
				"       vas.account_sys_desc," +
				"       DECODE (ig.invoice_unit_price_method," +
				"               'unit_cost', 'Unit Cost'," +
				"               'baseline_price', 'Baseline Price'," +
				"               'buy_type_price', 'Buy Type Price'," +
				"               'catalog_price', 'Catalog Price'," +
				"               ig.invoice_unit_price_method)" +
				"          AS pricing_method" +
				"  FROM customer.invoice_group_facility igf," +
				"       customer.invoice_group ig," +
				"       customer.bill_to_customer btc," +
				"       vv_account_sys vas" +
				" WHERE igf.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"   AND igf.facility_id = " + SqlHandler.delimitString(input.getFacilityId()) +
				"   AND ig.invoice_group(+) = igf.invoice_group" +
				"   AND btc.customer_id(+) = igf.customer_id" +
				"   AND vas.account_sys_id(+) = igf.account_sys_id" +
				"   AND vas.company_id(+) = igf.company_id";

		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		results.put("rows", rows);

		factory.setBean(new InvoiceGroupOverview());
		@SuppressWarnings("unchecked")
		Collection<InvoiceGroupOverview> beans = factory.selectQuery(SQL);

		if (beans.isEmpty()) {
			JSONArray data = new JSONArray();
			data.put("No Records Found");
			data.put("");
			data.put("");
			data.put("");
			JSONObject row = new JSONObject();
			row.put("id", 1);
			row.put("data", data);
			rows.put(row);
		}
		else {
			int id = 1;
			for (InvoiceGroupOverview bean : beans) {
				JSONArray data = new JSONArray();
				data.put(bean.getInvoiceGroup());
				data.put(bean.getAccountSysDesc());
				data.put(bean.getPaymentTerms());
				data.put(bean.getPricingMethod());
				JSONObject row = new JSONObject();
				row.put("id", id++);
				row.put("data", data);
				rows.put(row);
			}
		}
		return results;
	}

	public JSONObject getInvoicingHeader(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"  SELECT f.facility_name," +
				"         c.company_name," +
				"         oe.operating_entity_name AS billing_entity," +
				"         NVL(pg.price_group_name, pg.price_group_id) AS price_group," +
				"         pg.currency_id" +
				"    FROM customer.facility f," +
				"         customer.company c," +
				"         ops_entity_facility oef," +
				"         operating_entity oe," +
				"         customer.price_group pg" +
				"   WHERE f.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"     AND f.facility_id = " + SqlHandler.delimitString(input.getFacilityId()) +
				"     AND c.company_id(+) = f.company_id" +
				"     AND oef.company_id(+) = f.company_id" +
				"     AND oef.facility_id(+) = f.facility_id" +
				"     AND oe.ops_entity_id(+) = oef.ops_entity_id" +
				"     AND pg.price_group_id(+) = f.price_group_id " +
				"ORDER BY company_name, facility_name DESC";

		JSONObject results = new JSONObject();

		factory.setBean(new InvoicingHeader());
		@SuppressWarnings("unchecked")
		Collection<InvoicingHeader> beans = factory.selectQuery(SQL);

		for (InvoicingHeader bean : beans) {
			results = BeanHandler.getJsonObject(bean);
		}

		return results;
	}

	public JSONObject getOperationalHeader(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"  SELECT f.facility_id," +
				"         f.company_id," +
				"         oe.ops_entity_id," +
				"         oe.operating_entity_name" +
				"    FROM customer.facility f, ops_entity_facility oef, operating_entity oe" +
				"   WHERE f.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"     AND f.facility_id = " + SqlHandler.delimitString(input.getFacilityId()) +
				"     AND oef.company_id(+) = f.company_id" +
				"     AND oef.facility_id(+) = f.facility_id" +
				"     AND oe.ops_entity_id(+) = oef.ops_entity_id " +
				"ORDER BY facility_name DESC";

		JSONObject results = new JSONObject();

		factory.setBean(new OperationalHeader());
		@SuppressWarnings("unchecked")
		Collection<OperationalHeader> beans = factory.selectQuery(SQL);

		for (OperationalHeader bean : beans) {
			results = BeanHandler.getJsonObject(bean);
		}

		return results;
	}

	public JSONObject getServiceFeeMarkupRulesForGrid(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"SELECT sfmp.item_type," +
				"       sfmp.buy_type," +
				"       sfmp.order_type," +
				"       sfmp.formula_id AS cost," +
				"       sfmp.service_fee_percent AS percent" +
				"  FROM customer.service_fee_markup_rule sfmp" +
				" WHERE sfmp.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"   AND sfmp.facility_id =  " + SqlHandler.delimitString(input.getFacilityId());
		
		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		results.put("rows", rows);

		factory.setBean(new ServiceFeeMarkupRule());
		@SuppressWarnings("unchecked")
		Collection<ServiceFeeMarkupRule> beans = factory.selectQuery(SQL);

		if (beans.isEmpty()) {
			JSONArray data = new JSONArray();
			data.put("No Records Found");
			data.put("");
			data.put("");
			data.put("");
			data.put("");
			JSONObject row = new JSONObject();
			row.put("id", 1);
			row.put("data", data);
			rows.put(row);
		}
		else {
			int id = 1;
			for (ServiceFeeMarkupRule bean : beans) {
				JSONArray data = new JSONArray();
				data.put(bean.getItemType());
				data.put(bean.getBuyType());
				data.put(bean.getOrderType());
				data.put(bean.getCost());
				data.put(bean.getPercent());
				JSONObject row = new JSONObject();
				row.put("id", id++);
				row.put("data", data);
				rows.put(row);
			}
		}

		return results;
	}

	public JSONObject getServiceFeesForGrid(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"SELECT nbpo.radian_po AS po," +
				"       nbpo.po_line," +
				"       nbpo.cat_part_no AS part," +
				"       fi.part_description," +
				"       cpp.catalog_price AS price" +
				"  FROM customer.no_buy_order_po nbpo, customer.fac_item fi, customer.catalog_part_pricing cpp" +
				" WHERE nbpo.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"   AND nbpo.use_facility_id = " + SqlHandler.delimitString(input.getFacilityId()) +
				"   AND nbpo.end_date > SYSDATE" +
				"   AND fi.company_id(+) = nbpo.company_id" +
				"   AND fi.fac_part_no(+) = nbpo.cat_part_no" +
				"   AND cpp.catalog_company_id(+) = nbpo.company_id" + 
				"   AND cpp.cat_part_no(+) = nbpo.cat_part_no" + 
				"   AND cpp.end_date >= SYSDATE";
		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		results.put("rows", rows);

		factory.setBean(new ServiceFeeOverview());
		@SuppressWarnings("unchecked")
		Collection<ServiceFeeOverview> beans = factory.selectQuery(SQL);

		if (beans.isEmpty()) {
			JSONArray data = new JSONArray();
			data.put("No Records Found");
			data.put("");
			data.put("");
			data.put("");
			data.put("");
			JSONObject row = new JSONObject();
			row.put("id", 1);
			row.put("data", data);
			rows.put(row);
		}
		else {
			int id = 1;
			for (ServiceFeeOverview bean : beans) {
				JSONArray data = new JSONArray();
				data.put(bean.getPo());
				data.put(bean.getPoLine());
				data.put(bean.getPart());
				data.put(bean.getPartDescription());
				data.put(bean.getPrice().toString());
				JSONObject row = new JSONObject();
				row.put("id", id++);
				row.put("data", data);
				rows.put(row);
			}
		}

		return results;
	}

	public JSONObject getShippingAddressesForGrid(CustomerDetailsInput input) throws BaseException, JSONException {
		String SQL = 
				"SELECT loc.location_desc AS location," +
				"       NVL (loc.address_line_1_display, loc.address_line_1) AS line_1," +
				"       NVL (loc.address_line_2_display, loc.address_line_2) AS line_2," +
				"       NVL (loc.address_line_3_display, loc.address_line_3) AS line_3," +
				"       NVL (loc.address_line_4_display, (loc.city || ', ' || loc.state_abbrev || ' ' || loc.zip)) AS line_4," +
				"       NVL (loc.address_line_5_display, loc.country_abbrev) AS line_5" +
				"  FROM customer.facility fac, customer.location loc" +
				" WHERE fac.company_id = " + SqlHandler.delimitString(input.getCompanyId()) +
				"   AND fac.facility_id = " + SqlHandler.delimitString(input.getFacilityId()) +
				"   AND loc.company_id = fac.company_id" +
				"   AND loc.location_id = fac.shipping_location";

		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		results.put("rows", rows);

		factory.setBean(new Address());
		@SuppressWarnings("unchecked")
		Collection<Address> beans = factory.selectQuery(SQL);

		if (beans.isEmpty()) {
			JSONArray data = new JSONArray();
			data.put("No Records Found");
			data.put("");
			JSONObject row = new JSONObject();
			row.put("id", 1);
			row.put("data", data);
			rows.put(row);
		}
		else {
			int id = 1;
			for (Address bean : beans) {
				JSONArray data = new JSONArray();
				data.put(bean.getLocation());
				data.put(bean.getAddress());
				JSONObject row = new JSONObject();
				row.put("id", id++);
				row.put("data", data);
				rows.put(row);
			}
		}
		return results;

	}
}
