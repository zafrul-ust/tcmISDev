package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.CatalogVendorProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.internal.catalog.beans.SdsAuthoringSelectionInputBean;
import com.tcmis.internal.catalog.beans.SdsAuthoringSelectionViewBean;

public class SdsAuthoringSelectionProcess extends CatalogVendorProcess {

	public SdsAuthoringSelectionProcess(BigDecimal personnelId, String client) {
		super(personnelId, client);
	}
	
	public Collection<CatalogInputBean> getCompanyFacCatalogCollection() throws BaseException {
		String query = "SELECT c.company_id, c.company_name, f.facility_id, f.facility_name, cat.catalog_id, cat.catalog_desc, cf.catalog_company_id"
				+ " FROM company c,"
				+ " facility_view f,"
				+ " catalog cat,"
				+ " catalog_facility cf,"
				+ " feature_release fr"
				+ " WHERE f.company_id = c.company_id"
				+ " AND CAT.COMPANY_ID = cf.catalog_company_id"
				+ " AND cat.catalog_id = cf.catalog_id"
				+ " AND f.facility_id = cf.facility_id"
				+ " AND f.active = 'y'"
				+ " AND cf.company_id = c.company_id"
				+ " AND cf.catalog_add_allowed = 'Y'"
				+ " AND c.company_id = fr.company_id"
				+ " AND FR.FEATURE = 'SdsAuthoring'"
				+ " AND fr.active = 'Y'"
				+ " AND fr.scope = f.facility_id"
				+ " ORDER BY company_name, facility_name, catalog_desc";
		
		return (Collection<CatalogInputBean>) factory.setBean(new CatalogInputBean()).selectQuery(query);
	}

	public Collection<SdsAuthoringSelectionViewBean> selectLocales(SdsAuthoringSelectionInputBean input) throws BaseException {
		String query = "select v.*, nvl2(mlv.locale_code, 'N', 'Y') available, fl.company_id, fl.facility_id"
				+ " from vv_locale v, msds_locale_view mlv, facility_locale fl, feature_release fr"
				+ " where fl.locale_code = v.locale_code"
				+ " and fl.company_id = fr.company_id"
				+ " and fl.cat_add_sds_authoring = 'Y'"
				+ " AND FR.FEATURE = 'SdsAuthoring'"
				+ " AND fr.active = 'Y'"
				+ " AND fr.scope = fl.facility_id"
				+ " and mlv.material_id(+) = " + input.getMaterialId()
				+ " and trunc(mlv.revision_date(+)) = trunc(" + DateHandler.getOracleToDateFunction(input.getRevisionDate()) + ")"
				+ " and mlv.locale_code(+) = v.locale_code"
				+ " and v.allow_document_upload = 'Y'"
				+ " order by fl.company_id, fl.facility_id, fl.display_order";
		
		return factory.setBean(new SdsAuthoringSelectionViewBean()).selectQuery(query);
	}
}
