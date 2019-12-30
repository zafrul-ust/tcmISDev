package com.tcmis.ws.scanner.process;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.ws.scanner.beans.CylinderRefurbCategory;

public class CylinderRefurbCategorySearchProcess extends BaseScannerSearchProcess {
	public CylinderRefurbCategorySearchProcess(String client) {
		super(client);
	}

	public JSONArray getCylinderRefurbCategorys(JSONObject input) throws BaseException, JSONException {
		JSONArray results = new JSONArray();
		factory.setBean(new CylinderRefurbCategory());
		setPagingConf(input);

		String query = "SELECT   r.supplier, r.refurb_category, r.display_order, r.status, RS.REFURB_SUBCATEGORY, RS.DISPLAY_ORDER sub_display_order,rs.status sub_status"
				+ " FROM   vv_cylinder_refurb_category r, vv_cylinder_refurb_subcategory rs" + " WHERE   rs.supplier = r.supplier AND RS.REFURB_CATEGORY = r.refurb_category"
				+ " ORDER BY   supplier, display_order, sub_display_order";

		@SuppressWarnings("unchecked")
		Collection<CylinderRefurbCategory> beans = factory.selectQuery(query.toString());

		JSONObject supplier = null;
		JSONObject refurbCategory = null;
		JSONArray refurbCategories = null;
		JSONArray refurbSubcategories = null;
		String currentSupplier = null;
		String currentCategory = null;
		totalCount = 0;
		pageCount = 0;
		int currentPageStart = page == 1 ? 1 : (((page - 1) * pageSize) + 1);
		int currentPageEnd = page == 1 ? pageSize : (page * pageSize);

		for (CylinderRefurbCategory bean : beans) {
			if (!bean.getSupplier().equals(currentSupplier)) {
				currentSupplier = bean.getSupplier();
				totalCount++;
				if (totalCount < currentPageStart || totalCount > currentPageEnd) {
					continue;
				}
				supplier = new JSONObject();
				supplier.put("supplier", bean.getSupplier());
				results.put(supplier);

				refurbCategories = new JSONArray();
				supplier.put("refurbCategories", refurbCategories);

			}
			if (!bean.getRefurbCategory().equals(currentCategory)) {
				currentCategory = bean.getRefurbCategory();

				refurbCategory = new JSONObject();
				refurbCategory.put("refurbCategory", bean.getRefurbCategory());
				refurbCategory.put("displayOrder", bean.getDisplayOrder());
				refurbCategory.put("status", bean.getStatus());
				refurbCategories.put(refurbCategory);

				refurbSubcategories = new JSONArray();
				refurbCategory.put("refurbSubcategories", refurbSubcategories);
			}
			JSONObject subcategory = new JSONObject();
			subcategory.put("refurbSubcategory", bean.getRefurbSubcategory());
			subcategory.put("displayOrder", bean.getSubDisplayOrder());
			subcategory.put("status", bean.getSubStatus());
			refurbSubcategories.put(subcategory);

		}

		return results;
	}

}
