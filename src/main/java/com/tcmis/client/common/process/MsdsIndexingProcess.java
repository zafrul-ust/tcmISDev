package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.Vector;

import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.MsdsIndexingPriorityBean;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.client.common.beans.MsdsIndexingBean;
import com.tcmis.client.common.beans.MsdsIndexingKitBean;
import com.tcmis.client.common.beans.MsdsIndexingQcBean;
import com.tcmis.client.common.beans.MsdsIndexingQcDetailBean;
import com.tcmis.client.common.beans.MsdsIndexingRequestBean;
import com.tcmis.client.common.beans.MsdsLocaleViewBean;
import com.tcmis.client.common.factory.MsdsIndexingBeanFactory;
import com.tcmis.client.common.factory.MsdsIndexingQcBeanFactory;
import com.tcmis.common.admin.beans.CompanyBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserCompanyBean;
import com.tcmis.common.admin.beans.UserCompanyViewBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.CatalogVendorProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddReqMsdsInputBean;
import com.tcmis.internal.catalog.beans.CatalogAddReqMsdsQcOvBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestOvBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestVendorBean;
import com.tcmis.internal.catalog.beans.ChemicalBean;
import com.tcmis.internal.catalog.beans.CompositionBean;
import com.tcmis.internal.catalog.beans.GHSFieldsInputBean;
import com.tcmis.internal.catalog.beans.GHSStatementsViewBean;
import com.tcmis.internal.catalog.beans.ManufacturerBean;
import com.tcmis.internal.catalog.beans.MaterialBean;
import com.tcmis.internal.catalog.beans.MsdsBean;
import com.tcmis.internal.catalog.beans.PictogramBean;
import com.tcmis.internal.catalog.beans.QualityCheckOriginalHeaderViewBean;
import com.tcmis.internal.catalog.beans.QualityCheckOriginalViewBean;
import com.tcmis.internal.catalog.factory.CatalogAddRequestOvBeanFactory;
import com.tcmis.internal.catalog.factory.CatalogQueueDataMapper;
import com.tcmis.internal.catalog.factory.CatalogQueueDataMapperImpl;
import com.tcmis.internal.catalog.factory.ChemicalBeanFactory;
import com.tcmis.internal.catalog.factory.CompositionBeanFactory;
import com.tcmis.internal.catalog.factory.ManufacturerBeanFactory;

public class MsdsIndexingProcess extends CatalogVendorProcess {

	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
	private String globalDataEntryComplete = "";
	private String companyDataEntryComplete = "";
	private String URL = "";
	private CatalogQueueDataMapper workQueueItem;

	public MsdsIndexingProcess(BigDecimal personnelId, String client) {
		this(personnelId, client,"");
	}

	public MsdsIndexingProcess(BigDecimal personnelId, String client, String URL) {
		super(personnelId, client);
		this.URL = URL;
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
		initDataMapper();
	}
	
	private void initDataMapper() {
		workQueueItem = new CatalogQueueDataMapperImpl(dbManager);
	}

	//this method return Y if request allow Master Data Team to select Id_only on page
	//otherwise return N
	public String getIdOnlyDisplayValue(BigDecimal requestId) throws BaseException {
		StringBuilder query = new StringBuilder("select count(*) from vv_chemical_approval_role car, catalog_add_request_new carn, vv_catalog_add_request_status cars, vv_approval_role_function arf");
		query.append(" where car.company_id = carn.company_id and car.facility_id = carn.eng_eval_facility_id and car.catalog_company_id = carn.catalog_company_id");
		query.append(" and car.catalog_id = carn.catalog_id and carn.company_id = cars.company_id and carn.request_status = cars.request_status");
		query.append(" and car.approval_group = cars.approval_group and car.approval_group_seq = carn.approval_group_seq");
		query.append(" and car.role_function = arf.role_function");
		query.append(" and arf.display_id_only = 'Y'");
		query.append(" and carn.request_id = ").append(requestId);
		BigDecimal tmpVal = new BigDecimal(factory.selectSingleValue(query.toString()));
		return (tmpVal.intValue()==0?"N":"Y");
	} //end of method

	public ManufacturerBean getManufacturer(String mfgId) throws BaseException {
		ManufacturerBeanFactory mbFactory = new ManufacturerBeanFactory(dbManager);
		return mbFactory.selectManufacturer(mfgId);
	}

	public Collection<ManufacturerBean> getManufacturerById(CatalogAddReqMsdsInputBean input) throws BaseException {
		factory.setBean(new ManufacturerBean());

		SearchCriteria criteria = new SearchCriteria("mfgId",SearchCriterion.EQUALS,input.getMfgId());

		return factory.select(criteria, null, "global.manufacturer");
	}

	public Collection<ManufacturerBean> getManufacturerByMaterialId(CatalogAddReqMsdsInputBean input) throws BaseException {
		Collection<ManufacturerBean> mfgColl = null;
		if (input.getMaterialId() != null) {
			factory.setBean(new ManufacturerBean());

			StringBuilder query = new StringBuilder("select * from manufacturer mfg, material mat where ");
			query.append("mfg.mfg_id = mat.mfg_id and mat.material_id = ").append(input.getMaterialId());

			mfgColl = factory.selectQuery(query.toString());
		}
		return mfgColl;
	}

	public Collection<MaterialBean> getMaterialById(CatalogAddReqMsdsInputBean input) throws BaseException {
		Collection<MaterialBean> materialColl = null;
		if (input.getMaterialId() != null) {
			factory.setBean(new MaterialBean());

			SearchCriteria criteria = new SearchCriteria("materialId",SearchCriterion.EQUALS,input.getMaterialId().toString());

			materialColl = factory.select(criteria, null, "global.material");
		}
		return materialColl;
	}

	public Collection<MaterialBean> getLocalizedMaterialById(CatalogAddReqMsdsInputBean input, String localeCode) throws BaseException {
		Collection<MaterialBean> materialColl = null;
		if (input.getMaterialId() != null) {
			factory.setBean(new MaterialBean());

			SearchCriteria criteria = new SearchCriteria("materialId",SearchCriterion.EQUALS,input.getMaterialId().toString());
			criteria.addCriterion("localeCode", SearchCriterion.EQUALS, localeCode);

			materialColl = factory.select(criteria, null, "material_locale");
		}
		return materialColl;
	}

	public Collection<MsdsLocaleViewBean> getAvailableRevDates(CatalogAddReqMsdsInputBean input) throws BaseException {
		return getAvailableRevDates(input, false);
	}

	public Collection<MsdsLocaleViewBean> getAvailableRevDates(CatalogAddReqMsdsInputBean input, boolean localeSpecific) throws BaseException {
		factory.setBean(new MsdsLocaleViewBean());

		Collection<MsdsLocaleViewBean> beanColl = null;
		if (input.getMaterialId() != null) {
			StringBuilder query = new StringBuilder("select m.material_id, m.revision_date, ");
			query.append("trunc(m.revision_date) || ' - ' || v.locale_display revision_date_display, ");
			query.append("trunc(m.revision_date) revision_date_display_short, ");
			query.append("v.locale_code, v.locale_display ");
			query.append("from global.msds m, vv_locale v where m.material_id = ").append(input.getMaterialId());
			query.append(" and m.locale_code = v.locale_code");
			if (localeSpecific && ! StringHandler.isBlankString(input.getqLocaleCode())) {
				query.append(" and m.locale_code = ").append(SqlHandler.delimitString(input.getqLocaleCode()));
			}
			query.append(" order by m.material_id, m.revision_date desc");

			beanColl = factory.selectQuery(query.toString());

		}

		return beanColl;
	}

	public BigDecimal getDataEntryStandard(String companyId) throws BaseException {
		factory.setBean(new MsdsLocaleViewBean());

		StringBuilder query = null;
		if (companyId != null) {
			query = new StringBuilder("select nvl(msds_data_entry_standard, '0') from company where company_id = '");
			query.append(companyId).append("'");
		}
		else {
			query = new StringBuilder("select current_global_standard from vv_msds_data_entry_standard where rownum = 1");
		}

		BigDecimal standard = new BigDecimal(0);
		if (query != null) {
			standard = new BigDecimal(factory.selectSingleValue(query.toString()));
		}

		return standard;
	}

	public Collection<MsdsIndexingBean> getMsdsByMaterialRevision(CatalogAddReqMsdsInputBean input, BigDecimal component) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		StringBuilder query = new StringBuilder("select sds.*, ");
		query.append("pkg_item_consolidation.fx_msds_author_desc (sds.msds_author_id) msds_author_desc, ");
		query.append("pkg_item_consolidation.fx_msds_author_country_abv (sds.msds_author_id) msds_author_country_abbrev, ");
		query.append("pkg_item_consolidation.fx_msds_author_notes (sds.msds_author_id) msds_author_notes, ");
		query.append("nvl2(mlv.revision_date,(trunc(mlv.revision_date) || ' - ' || mlv.locale_display),'') revision_date_display, mlv.locale_display");
		query.append(" from global.msds sds, msds_locale_view mlv where ");
		query.append("sds.material_id = ").append(StringHandler.zeroIfNull(input.getMaterialId()));
		query.append(" and sds.material_id = mlv.material_id(+) and sds.revision_date = mlv.revision_date(+)");
		query.append(" and sds.revision_date = ").append(DateHandler.getOracleToDateFunction(input.getRevisionDate()));
		query.append(" order by sds.revision_date desc");

		return factory.selectQuery(query.toString());
	}

	public BigDecimal getNumComponents(CatalogAddReqMsdsInputBean input) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		StringBuilder query = new StringBuilder("select max(part_id) from catalog_add_item_qc where request_id=");
		query.append(input.getRequestId()).append(" and line_item=").append(input.getLineItem()==null?"1":input.getLineItem());

		return new BigDecimal(factory.selectSingleValue(query.toString()));
	}

	public Collection<MsdsIndexingBean> getComponentData(CatalogAddReqMsdsInputBean input) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		StringBuilder query = new StringBuilder("select msds.material_id, msds.revision_date from global.msds, ");
		query.append("(select msds.material_id, max(revision_date) revision_date from global.msds, catalog_add_item_qc ");
		query.append("where msds.material_id = catalog_add_item_qc.material_id ");
		query.append("and catalog_add_item_qc.request_id = ").append(StringHandler.zeroIfNull(input.getRequestId()));
		query.append(" group by msds.material_id) msds2 ");
		query.append("where msds.material_id = msds2.material_id ");
		query.append("and msds.revision_date = msds2.revision_date");

		return factory.selectQuery(query.toString());
	}

	public Optional<CatalogQueueBean> getCatalogQueueData(CatalogAddReqMsdsInputBean input) throws BaseException {
		factory.setBean(new CatalogQueueBean());

		StringBuilder query = new StringBuilder();
		if (input.hasqId()) {
			query.append("select q.*, v.locale_display, caiq.material_id_sourced, caiq.company_id from catalog_queue q, vv_locale v, catalog_add_item_qc caiq")
					.append(" where q.q_id = ").append(input.getqId())
					.append(" and caiq.request_id = q.catalog_add_request_id")
					.append(" and caiq.catalog_add_item_id = q.catalog_add_item_id")
					.append(" and v.locale_code(+) = q.locale_code");
		}

		Collection<CatalogQueueBean> queue = query.length() == 0?Collections.emptyList():factory.selectQuery(query.toString());

		Optional<CatalogQueueBean> optional = queue.stream().findFirst();
		optional.filter(b -> b.hasAltSupplier() && ! this.getClient().equals("TCM_OPS"))
				.ifPresent(b -> b.setStatus(CatalogAddReqMsdsInputBean.STATUS_CLOSED));
		return optional;
	}

	public Collection<MsdsIndexingBean> getMsdsByRequest(CatalogAddReqMsdsInputBean input, BigDecimal component) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		// must select company_msds first so that when the data is put into the bean,
		// it can immediately tell whether there is a company msds or not
		StringBuilder query = new StringBuilder("select p.company_msds")
				.append(", nvl2(p.revision_date,(trunc(p.revision_date) || ' - ' || v.locale_display),'') revision_date_display")
				.append(", v.locale_code, m.sds_required")
				.append(", p.* from catalog_add_req_msds_qc_ov p, global.msds m, vv_locale v")
				.append(" where p.request_id = ").append(input.getRequestId())
				.append(" and m.material_id(+) = p.material_id and m.revision_date(+) = p.revision_date");
		if (input.hasqId()) {
			query.append(" and p.catalog_add_item_id = ").append(input.getCatalogAddItemId());
			query.append(" and v.locale_code = ").append(SqlHandler.delimitString(input.getqLocaleCode()));
			query.append(" and nvl(p.locale_code, v.locale_code) = v.locale_code");
		}
		else {
			query.append(" and p.line_item = ").append(input.getLineItem());
			query.append(" and p.part_id = ").append(component.add(new BigDecimal(1)));
			query.append(" and nvl(p.locale_code, v.locale_code) = v.locale_code");
		}
		query.append(" order by p.part_id, p.revision_date desc");

		Collection<MsdsIndexingBean> msdsColl = factory.selectQuery(query.toString());
		if (msdsColl.isEmpty() && input.hasqId()) {
			query = new StringBuilder("select qc.request_id, qc.line_item, qc.part_id, qc.material_id")
					.append(", qc.comments, qc.catalog_add_item_id, qc.customer_msds_number component_msds")
					.append(", ").append(SqlHandler.delimitString(input.getqLocaleCode()))
					.append(" from customer.catalog_add_item_qc qc")
					.append(" where qc.request_id = ").append(input.getRequestId())
					.append(" and qc.catalog_add_item_id = ").append(input.getCatalogAddItemId());

			msdsColl = factory.selectQuery(query.toString());
		}

		return msdsColl;
	}

	public Collection<MsdsIndexingBean> getMsdsByAuthoringRequest(CatalogAddReqMsdsInputBean input, CatalogQueueBean workQueueItem) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		StringBuilder query = new StringBuilder("select sds.content, sds.msds_author_id")
				.append(", pkg_item_consolidation.fx_msds_author_desc (sds.msds_author_id) msds_author_desc")
				.append(", pkg_item_consolidation.fx_msds_author_country_abv (sds.msds_author_id) msds_author_country_abbrev")
				.append(", pkg_item_consolidation.fx_msds_author_notes (sds.msds_author_id) msds_author_notes")
				.append(", r.* from global.msds sds")
				.append(", (select caiq.material_id, q.revision_date, q.locale_code, l.locale_display")
				.append(", decode(q.item_id, cqi.item_id, 'Authoring', 'Sourcing') authoring_type")
				.append(", src.revision_date source_revision_date, src.content source_content, src.locale_code source_locale_code, sl.locale_display source_locale_display")
				.append(", nvl2(q.revision_date,(trunc(q.revision_date) || ' - ' || l.locale_display),'(Please Select) - ' || l.locale_display) revision_date_display")
				.append(", nvl2(src.revision_date,(trunc(src.revision_date) || ' - ' || sl.locale_display),'') source_revision_date_display")
				.append(" from customer.catalog_add_item_qc caiq, catalog_queue q, catalog_queue_item cqi, global.msds src, vv_locale l, vv_locale sl")
				.append(" where caiq.request_id = q.catalog_add_request_id and caiq.catalog_add_item_id = q.catalog_add_item_id")
				.append(" and src.material_id = caiq.material_id and src.revision_date = caiq.source_revision_date")
				.append(" and l.locale_code = q.locale_code and sl.locale_code = src.locale_code")
				.append(" and cqi.task = q.task and cqi.locale_code = q.locale_code")
				.append(" and q.q_id = ").append(workQueueItem.getqId()).append(") r")
				.append(" where sds.material_id(+) = r.material_id and sds.revision_date(+) = r.revision_date");

		return factory.selectQuery(query.toString());
	}

	public Collection<CompanyBean> getCustomerOverrideCompanies(BigDecimal materialId) throws BaseException {
		factory.setBean(new CompanyBean());

		StringBuilder query = new StringBuilder("select * from table(PKG_COMPANY_MSDS.FX_GET_COMPANY('");
		query.append(StringHandler.emptyIfNull(materialId)).append("')) order by company_name");

		return factory.selectQuery(query.toString());
	}

	public Collection<MsdsIndexingBean> getCustomerOverrideMsds(CatalogAddReqMsdsInputBean input) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		Collection<MsdsIndexingBean> coMsdsColl = null;

		if (input.getMaterialId() != null && input.getRevisionDate() != null && ! StringHandler.isBlankString(input.getCompanyId())) {
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("materialId", SearchCriterion.EQUALS, "" + input.getMaterialId().toString());
			criteria.addCriterion("revisionDate", SearchCriterion.EQUALS, DateHandler.getOracleToDateFunction(input.getRevisionDate()));
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + input.getCompanyId());

			coMsdsColl = factory.select(criteria, null, "customer.company_msds");
		}

		return coMsdsColl;
	}

	public Collection getMsdsIndexingPriority(String priorityType) throws BaseException {
		StringBuilder query = new StringBuilder("select * from msds_indexing_priority");
		if ("catalogAddOrder".equals(priorityType)) {
			query.append(" where catalog_add_order is not null");
			query.append(" order by catalog_add_order");
		}else if ("startupOrder".equals(priorityType)) {
			query.append(" where startup_order is not null");
			query.append(" order by startup_order");
		}else if ("newRevisionOrder".equals(priorityType)) {
			query.append(" where new_revision_order is not null");
			query.append(" order by new_revision_order");
		}
		factory.setBeanObject(new MsdsIndexingPriorityBean());
		return factory.selectQuery(query.toString());
	} //end of method

	public String getCatalogFacilityMsdsIndexingPriority(String requestId) throws BaseException {
		String result = "";
		if ( ! StringHandler.isBlankString(requestId)) {
			StringBuilder query = new StringBuilder("select nvl(msds_indexing_priority_id,1) from ");
			if ("TCM_OPS".equals(this.getClient()))
				query.append("customer.catalog_facility cf");
			else
				query.append("catalog_facility cf");
			query.append(",catalog_add_request_new carn");
			query.append(" where carn.company_id = cf.company_id and carn.catalog_company_id = cf.catalog_company_id and carn.catalog_id = cf.catalog_id");
			query.append(" and carn.eng_eval_facility_id = cf.facility_id and carn.request_id = ").append(requestId);
			result = factory.selectSingleValue(query.toString());
		}
		return result;
	} //end of method

	public Collection<GHSStatementsViewBean> getStatements(GHSFieldsInputBean inputBean) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select gs.id, gs.code, gs.statement, nvl(mgs.is_from_msds,1) is_from_msds, mgs.msds_id ");
		query.append("from msds_admin.ghs_statement gs left join msds_admin.msds_ghs_statement mgs ");
		query.append("on gs.id = mgs.ghs_statement_id and mgs.msds_id = ").append(inputBean.getMsdsId()).append(" where");
		if (inputBean.getCodeAbbrev().equals("H")) {
			query.append(" (gs.code like 'H%' or gs.code like 'EUH%') and gs.code not like 'HM%'");
		}
		else {
			query.append(" gs.code like '").append(inputBean.getCodeAbbrev()).append("%'");
		}

		query.append(" order by gs.code");
		Collection<GHSStatementsViewBean> statements = factory.setBean(new GHSStatementsViewBean()).selectQuery(query.toString());

		return statements;
	}

	public Collection<PictogramBean> getPictograms() throws Exception {
		StringBuilder query = new StringBuilder("select * from vv_ghs_pictogram order by default_order");

		Collection<PictogramBean> pictogramColl = factory.setBean(new PictogramBean()).selectQuery(query.toString());

		return pictogramColl;
	}

	public Collection<MsdsIndexingQcDetailBean> getQcErrorTypes() throws BaseException {
		String query = "select * from vv_msds_qc_error_type";

		return factory.setBean(new MsdsIndexingQcDetailBean()).selectQuery(query);
	}

	public Collection<PictogramBean> getGhsPictograms(BigDecimal materialId, Date revisionDate) throws BaseException {
		StringBuilder ghsPictograms = new StringBuilder();

		StringBuilder query = new StringBuilder();
		query.append("select g.pictogram_id, g.pictogram_description, g.file_name, NVL(m.on_sds,'Y') on_sds, m.material_id ");
		query.append("from vv_ghs_pictogram g, msds_ghs_pictogram m ");
		query.append("where m.pictogram_id(+) = g.pictogram_id");
		query.append(" and m.material_id(+) = ").append(materialId);
		query.append(" and m.revision_date(+) = ").append(DateHandler.getOracleToDateFunction(revisionDate));
		query.append(" order by g.default_order desc");

		Collection<PictogramBean> pictogramColl = factory.setBean(new PictogramBean()).selectQuery(query.toString());
		//inputBean.setPictograms(pictogramColl);

		return pictogramColl;
	}

	public CatalogAddRequestOvBean getRequest(CatalogAddReqMsdsInputBean input) throws BaseException {
		CatalogAddRequestOvBeanFactory addRequestOvfactory = new CatalogAddRequestOvBeanFactory(dbManager);
		Collection<CatalogAddRequestOvBean> results = addRequestOvfactory.select(new SearchCriteria("requestId", SearchCriterion.EQUALS, input.getRequestId()), null);
		//if returned is a new bean instead of null, jsp will incorrectly flag masterData as Y, since returned is not empty
		return results != null && !results.isEmpty() ? results.toArray(new CatalogAddRequestOvBean[1])[0] : null;
	}

	public boolean isRequestForCompanyWithMSDS(String requestId) throws BaseException {
		String companyMsds = factory.selectSingleValue("select company_msds from company c, catalog_add_item_qc qc where qc.request_id = '" + requestId + "' and c.company_id = qc.company_id");
		return "Y".equals(companyMsds);
	}

	public MsdsIndexingKitBean getKitData(CatalogAddReqMsdsInputBean input) throws BaseException {
		MsdsIndexingKitBean kitData = new MsdsIndexingKitBean();
		Connection conn = dbManager.getConnection();
		try {
			boolean result = false;
			StringBuilder query = new StringBuilder("select f.facility_id from catalog_add_request_new carn");
			if ("TCM_OPS".equals(this.getClient()))
				query.append(",customer.facility f");
			else
				query.append(",facility f");
			query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id");
			query.append(" and f.allow_mixture = 'Y' and carn.request_id = ").append(input.getRequestId());
			String tmpVal = factory.selectSingleValue(query.toString(),conn);
			if (!StringHandler.isBlankString(tmpVal))
				result = true;
			else
				tmpVal = "ABXYZAB";

			kitData.setAllowMixture(result);
			kitData.setFacilityId(tmpVal);

			if(result) {
				factory.setBean(new CatalogAddReqMsdsQcOvBean());
				query = new StringBuilder("SELECT caic.*, m.mfg_desc mixture_manufacturer FROM catalog_add_item_qc caic, manufacturer m WHERE caic.REQUEST_ID =").append(input.getRequestId());
				query.append(" AND line_item = ").append(input.getLineItem()).append(" AND CAIC.MIXTURE_MFG_ID = M.MFG_ID(+)");
				Vector<CatalogAddReqMsdsQcOvBean> res = (Vector<CatalogAddReqMsdsQcOvBean>)factory.selectQuery(query.toString(),conn);
				if (res.size() > 1) {
					CatalogAddReqMsdsQcOvBean resBean = res.firstElement();
					kitData.setMixtureDesc(resBean.getMixtureDesc());
					kitData.setMixtureManufacturer(resBean.getMixtureManufacturer());
					kitData.setMixtureMfgId(resBean.getMixtureMfgId());
					kitData.setMixtureProductCode(resBean.getMixtureProductCode());
					kitData.setMixturePhysicalState(resBean.getMixturePhysicalState());
					kitData.setMixturePhysicalStateSource(resBean.getPhysicalStateSource());
					kitData.setMixtureVoc(resBean.getMixtureVoc());
					kitData.setMixtureVocLower(resBean.getMixtureVocLower());
					kitData.setMixtureVocUpper(resBean.getMixtureVocUpper());
					kitData.setMixtureVocUnit(resBean.getMixtureVocUnit());
					kitData.setMixtureVocSource(resBean.getVocSource());
					kitData.setMixtureVocLwes(resBean.getMixtureVocLwes());
					kitData.setMixtureVocLwesLower(resBean.getMixtureVocLwesLower());
					kitData.setMixtureVocLwesUpper(resBean.getMixtureVocLwesUpper());
					kitData.setMixtureVocLwesUnit(resBean.getMixtureVocLwesUnit());
					kitData.setMixtureVocLwesSource(resBean.getMixtureVocLwesSource());
					kitData.setKitSize(new BigDecimal(res.size()));
					kitData.setCustomerMixtureNumber(resBean.getCustomerMixtureNumber());
				}
				else {
					// fake allow mixture = false because we don't need kit data for a single component
					kitData.setAllowMixture(false);
				}
			}
		}
		finally {
			dbManager.returnConnection(conn);
		}

		return kitData;
	}

	public Collection setFlashPointMethod() throws BaseException {
		factory.setBean(new MsdsBean());
		return factory.selectQuery("select * from vv_flash_point_method");
	}

	public Collection<CompositionBean> getCompositionBeans(BigDecimal materialId, Date revisionDate) throws BaseException {
		CompositionBeanFactory compositionFactory = new CompositionBeanFactory(dbManager);
		SearchCriteria query = new SearchCriteria("materialId", SearchCriterion.EQUALS, materialId.toPlainString());
		query.addCriterion("revisionDate", SearchCriterion.EQUALS, DateHandler.getOracleToDateFunction(revisionDate));
		SortCriteria sorter = new SortCriteria("sdsSectionId");
		sorter.addCriterion("casNumber");
		return compositionFactory.select(query, sorter);
	}

	public Optional<BigDecimal> lockedInMaterialId(CatalogAddReqMsdsInputBean input) throws BaseException {
		String query = "select material_id from catalog_add_item_qc"
				+ " where material_id_sourced in ('Y', 'X')"
				+ " and (request_id, catalog_add_item_id) = ("
				+ "select catalog_add_request_id, catalog_add_item_id from catalog_queue"
				+ " where q_id = " + input.getqId() + ")";

		Optional<BigDecimal> lockedInMaterial = Optional.empty();
		try {
			String materialId = factory.selectSingleValue(query);
			if ( ! StringHandler.isBlankString(materialId)) {
				lockedInMaterial = Optional.of(new BigDecimal(materialId));
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}

		return lockedInMaterial;
	}

	public void updateWorkQueueItem(MsdsIndexingBean msds, CatalogQueueBean workQueueItem) throws BaseException {
		String query = "update catalog_queue set material_id = " + msds.getMaterialId()
				+ ", revision_date = " + DateHandler.getOracleToDateFunction(msds.getRevisionDate())
				+ " where q_id = " + workQueueItem.getqId();

		factory.deleteInsertUpdate(query);
	}

	public void updateLineItemMSDS(MsdsIndexingBean msds, MsdsIndexingRequestBean request, CatalogQueueBean queueRow) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			// 1. update catalog_add_item_qc
			StringBuilder query = new StringBuilder("update catalog_add_item_qc set");
			query.append(" MATERIAL_DESC=").append(SqlHandler.delimitString(msds.getMaterial().getMaterialDesc())).append(",");
			query.append(" MANUFACTURER=").append(SqlHandler.delimitString(msds.getMfg().getMfgDesc())).append(",");
			query.append(" MATERIAL_ID=").append(msds.getMaterialId()).append(",");
			query.append(" MFG_ID=").append(msds.getMfg().getMfgId()).append(",");
			query.append(" MFG_TRADE_NAME=").append(SqlHandler.delimitString(msds.getMaterial().getTradeName())).append(",");
			query.append(" COMMENTS=").append(SqlHandler.delimitString(msds.getComments()));
			if (!StringHandler.isBlankString(msds.getComponentMsds()))
				query.append(",").append(" CUSTOMER_MSDS_NUMBER=").append(SqlHandler.delimitString(msds.getComponentMsds()));
			query.append(" where REQUEST_ID = ").append(request.getRequestId());
			query.append(" and LINE_ITEM = ").append(request.getLineItem());
			if (queueRow.hasqId()) {
				query.append(" and CATALOG_ADD_ITEM_ID = ").append(queueRow.getCatalogAddItemId());
			}
			else {
				query.append(" and PART_ID = ").append(queueRow.getCatalogAddItemId());
			}
			//System.out.println(query.toString());
			factory.deleteInsertUpdate(query.toString(), conn);

			// 2. update catlaog_add_item
			query = new StringBuilder("update catalog_add_item set");
			query.append(" MATERIAL_DESC=").append(SqlHandler.delimitString(msds.getMaterial().getMaterialDesc())).append(",");
			query.append(" MANUFACTURER=").append(SqlHandler.delimitString(msds.getMfg().getMfgDesc())).append(",");
			query.append(" MATERIAL_ID=").append(msds.getMaterialId()).append(",");
			query.append(" MFG_ID=").append(msds.getMfg().getMfgId()).append(",");
			query.append(" MFG_TRADE_NAME=").append(SqlHandler.delimitString(msds.getMaterial().getTradeName()));
			if (!StringHandler.isBlankString(msds.getComponentMsds()))
				query.append(",").append(" CUSTOMER_MSDS_NUMBER=").append(SqlHandler.delimitString(msds.getComponentMsds()));
			query.append(" where REQUEST_ID = ").append(request.getRequestId());
			query.append(" and LINE_ITEM = ").append(request.getLineItem());
			if (queueRow.hasqId()) {
				query.append(" and CATALOG_ADD_ITEM_ID = ").append(queueRow.getCatalogAddItemId());
			}
			else {
				query.append(" and PART_ID = ").append(queueRow.getCatalogAddItemId());
			}
			//System.out.println(query.toString());
			factory.deleteInsertUpdate(query.toString(), conn);
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	public void updateLineItemMSDS(MsdsIndexingBean msds, MsdsIndexingRequestBean request, BigDecimal partId) throws BaseException {
		CatalogQueueBean queueRow = new CatalogQueueBean();
		queueRow.setCatalogAddItemId(partId);
		updateLineItemMSDS(msds, request, queueRow);
	}

	public void updateKitData(MsdsIndexingKitBean kitData, MsdsIndexingRequestBean requestData) throws BaseException {
		Connection conn = dbManager.getConnection();

		try {
			StringBuilder query = new StringBuilder("update catalog_add_item_qc set");
			query.append(" MIXTURE_DESC=").append(SqlHandler.delimitString(kitData.getMixtureDesc())).append(",");
			query.append(" MIXTURE_MFG_ID=").append(kitData.getMixtureMfgId()).append(",");
			query.append(" MIXTURE_PRODUCT_CODE=").append(SqlHandler.delimitString(kitData.getMixtureProductCode())).append(",");
			query.append(" MIXTURE_PHYSICAL_STATE='").append(kitData.getMixturePhysicalState()).append("',");
			query.append(" MIXTURE_PHYSICAL_STATE_SOURCE='").append(kitData.getMixturePhysicalStateSource()).append("',");
			query.append(" MIXTURE_VOC_DETECT='").append(calculateDetect(kitData.getMixtureVoc(),kitData.getMixtureVocLower(),kitData.getMixtureVocUpper())).append("',");
			query.append(" MIXTURE_VOC=").append(kitData.getMixtureVoc()).append(",");
			query.append(" MIXTURE_VOC_LOWER=").append(kitData.getMixtureVocLower()).append(",");
			query.append(" MIXTURE_VOC_UPPER=").append(kitData.getMixtureVocUpper()).append(",");
			query.append(" MIXTURE_VOC_UNIT='").append(kitData.getMixtureVocUnit()).append("',");
			query.append(" MIXTURE_VOC_SOURCE='").append(kitData.getMixtureVocSource()).append("',");
			query.append(" MIXTURE_VOC_LWES_DETECT ='").append(calculateDetect(kitData.getMixtureVocLwes(),kitData.getMixtureVocLwesLower(),kitData.getMixtureVocLwesUpper())).append("',");
			query.append(" MIXTURE_VOC_LWES =").append(kitData.getMixtureVocLwes()).append(",");
			query.append(" MIXTURE_VOC_LWES_LOWER =").append(kitData.getMixtureVocLwesLower()).append(",");
			query.append(" MIXTURE_VOC_LWES_UPPER =").append(kitData.getMixtureVocLwesUpper()).append(",");
			query.append(" MIXTURE_VOC_LWES_UNIT ='").append(kitData.getMixtureVocLwesUnit()).append("',");
			query.append(" MIXTURE_VOC_LWES_SOURCE ='").append(kitData.getMixtureVocLwesSource()).append("',");
			query.append(" CUSTOMER_MIXTURE_NUMBER=").append(SqlHandler.delimitString(kitData.getCustomerMixtureNumber()));
			query.append(" where REQUEST_ID = ").append(requestData.getRequestId());
			query.append(" and LINE_ITEM = ").append(requestData.getLineItem());
			//System.out.println(query.toString());
			factory.deleteInsertUpdate(query.toString(), conn);
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	private String calculateDetect(BigDecimal value, BigDecimal lower, BigDecimal upper) {
		String result = "=";
		if (value != null)
			result = "=";
		else if (lower != null && upper != null)
			result = "=";
		else if (lower != null)
			result = ">=";
		else if (upper != null)
			result = "<=";

		return result;
	}

	public void updatePartDescription(MsdsIndexingRequestBean requestBean) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			StringBuilder query = new StringBuilder("update catalog_add_request_new set part_description = ");
			query.append(SqlHandler.delimitString(requestBean.getPartDescription()));
			query.append(" where request_id = ").append(requestBean.getRequestId());
			//System.out.println(query.toString());
			factory.deleteInsertUpdate(query.toString(), conn);
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	public void updateMSDS(CatalogAddReqMsdsInputBean input, MsdsIndexingBean msds, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			// 1. update global.manufacturer and global.material table if there is a mfgId/materialId
			if (msds.getMfg().getMfgId() != null) {
				StringBuilder mfgupdate = new StringBuilder("update global.manufacturer set");
				mfgupdate.append(" mfg_desc = ").append(SqlHandler.delimitString(msds.getMfg().getMfgDesc()));
				mfgupdate.append(", mfg_short_name = ").append(SqlHandler.delimitString(msds.getMfg().getMfgShortName()));
				mfgupdate.append(", mfg_url = ").append(SqlHandler.delimitString(msds.getMfg().getMfgUrl()));
				mfgupdate.append(" where mfg_id = ").append(msds.getMfg().getMfgId());

				factory.deleteInsertUpdate(mfgupdate.toString(), conn);
			}
			if (msds.getMaterialId() != null) {
				StringBuilder materialupdate = new StringBuilder("update global.material set");
				materialupdate.append(" MATERIAL_DESC=").append(SqlHandler.delimitString(msds.getMaterial().getMaterialDesc())).append(",");
				if (msds.getMfg().getMfgId() != null) {
					materialupdate.append(" MFG_ID=").append(msds.getMfg().getMfgId()).append(",");
				}
				if (msds.getMaterial().getProductCode() != null) {
					materialupdate.append(" PRODUCT_CODE=").append(SqlHandler.delimitString(msds.getMaterial().getProductCode())).append(",");
				}
				materialupdate.append(" TRADE_NAME=").append(SqlHandler.delimitString(msds.getMaterial().getTradeName()));
				if(msds.getMaterial().getCustomerOnlyMsds() == null)
				{
					materialupdate.append(", CUSTOMER_ONLY_MSDS='N'");
				}

				materialupdate.append(" where MATERIAL_ID = ").append(msds.getMaterialId());
				//System.out.println(materialupdate.toString());
				factory.deleteInsertUpdate(materialupdate.toString(), conn);

				String localeCode = StringHandler.isBlankString(input.getqLocaleCode())?
						SqlHandler.delimitString(msds.getImageLocaleCode()):
						SqlHandler.delimitString(input.getqLocaleCode());

				if ( ! "'en_US'".equals(localeCode)) {
					String localizedMaterialUpdate = "update material_locale"
							+ " set material_desc = " + SqlHandler.delimitString(msds.getLocalizedMaterial().getMaterialDesc())
							+ ", trade_name = " + SqlHandler.delimitString(msds.getLocalizedMaterial().getTradeName())
							+ " where material_id = " + msds.getMaterialId()
							+ " and locale_code = " + localeCode;

					int rowsUpdated = factory.deleteInsertUpdate(localizedMaterialUpdate, conn);
					if (rowsUpdated == 0) {
						localizedMaterialUpdate = "insert into material_locale(material_id, material_desc, trade_name, locale_code) values ("
								+ msds.getMaterialId()
								+ ", " + SqlHandler.delimitString(msds.getLocalizedMaterial().getMaterialDesc())
								+ ", " + SqlHandler.delimitString(msds.getLocalizedMaterial().getTradeName())
								+ ", " + localeCode + ")";

						factory.deleteInsertUpdate(localizedMaterialUpdate, conn);
					}
				}
			}

			// 2. Update global.msds IF there is a revision Date
			if (msds.getMaterialId() != null && msds.getRevisionDate() != null) {
				// Update the verified data if the verified box is checked
				//MsdsBeanFactory msdsFactory = new MsdsBeanFactory(dbManager);
				MsdsIndexingBeanFactory msdsFactory = new MsdsIndexingBeanFactory(dbManager);
				MsdsIndexingBean msdsTestBean = msdsFactory.select(msds.getMaterialId(), msds.getRevisionDate(), conn);
				if (msdsTestBean == null) {
					// New MSDS revision
					if (!StringHandler.isBlankString(msds.getContent()) &&
							("en_US".equalsIgnoreCase(msds.getImageLocaleCode()) || StringHandler.isBlankString(msds.getImageLocaleCode())))
						msds.setOnLine("Y");
					else
						msds.setOnLine("N");
					msds.setReviewedBy(user.getPersonnelId() + "");
					msds.setVerifiedBy(user.getPersonnelIdBigDecimal());
					msds.setVerifiedOn(Calendar.getInstance().getTime());
					if (msds.getMsdsId() == null) {
						msds.setMsdsId(msdsFactory.nextMaxcomId(conn));
					}
					msdsFactory.insert(msds, conn);
				}else {
					// Update to existing MSDS revision
					if (!StringHandler.isBlankString(msds.getImageLocaleCode())) {
						if (!StringHandler.isBlankString(msds.getContent()) && "en_US".equalsIgnoreCase(msds.getImageLocaleCode()))
							msds.setOnLine("Y");
						else
							msds.setOnLine("N");
					}else {
						msds.setOnLine(msdsTestBean.getOnLine());
					}
					msds.setReviewedBy(user.getPersonnelId() + "");
					msds.setVerifiedBy(user.getPersonnelIdBigDecimal());
					msds.setVerifiedOn(Calendar.getInstance().getTime());
					if (msds.getMsdsId() == null) {
						msds.setMsdsId(msdsFactory.nextMaxcomId(conn));
					}
					msdsFactory.update(msds, input.isSourcing(), conn);
				}
				msds.setMsdsId(msds.getMsdsId());

				//handling global.msds_locale here
				//if user upload a foreign MSDS then insert/update global.msds_locale
				if (!StringHandler.isBlankString(msds.getImageLocaleCode())) {
					msdsFactory.removeMsdsLocale(msds,conn);
					// we always want to remove the old record, but don't add another one if it is en_US
					if (!"en_US".equalsIgnoreCase(msds.getImageLocaleCode())) {
						msdsFactory.setMsdsLocale(msds,conn);
					}
				}

				if ((input.isIndexing() || ! input.hasqId())) {
					if(msds.isSaveCustomerOverride()) {
						updateCOForCatalog(msds, user.getPersonnelId(),conn);
					}

					// 3. Update GHS Statements and Pictograms
					msdsFactory.updateStatements(msds, user, conn);
					msdsFactory.updatePictograms(msds, conn);

					// 4. Update QC Details
					MsdsIndexingQcBean qcBean = null;
					MsdsIndexingQcBean coQcBean = null;
					if ( ! input.hasqId()) {
						MsdsIndexingQcBeanFactory msdsQcFactory = new MsdsIndexingQcBeanFactory(dbManager);
						qcBean = msdsQcFactory.getMsdsIndexingQcBean(msds.getMaterialId(), msds.getRevisionDate(), null, conn);
						if (msds.getCompanyId() != null) {
							coQcBean = msdsQcFactory.getMsdsIndexingQcBean(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), conn);
						}

						if ((qcBean == null) || ((qcBean.getApproveDate() == null) && (qcBean.getSubmitDate() != null) &&
								( ! user.getPersonnelIdBigDecimal().equals(qcBean.getSubmitBy())))) {
							msdsQcFactory.updateQcDetails(msds, user, null, conn);
						}

						if(msds.isSaveCustomerOverride() && (coQcBean != null) && (coQcBean.getApproveDate() == null) &&
								(coQcBean.getSubmitDate() != null) && ( ! user.getPersonnelIdBigDecimal().equals(coQcBean.getSubmitBy()))) {
							msdsQcFactory.updateQcDetails(msds, user, msds.getCompanyId(), conn);
						}
					}

					// 5. Update Composition Data IF there is a materialId AND a revisionDate AND 1 or more rows of Composition Data
					//	  Update Locale Data IF there is BOTH a materialId AND a revisionDate
					if (msds.getCompositionData().size() > 0) {
						CompositionBeanFactory compositionFactory = new CompositionBeanFactory(dbManager);
						SearchCriteria deleteExisting = new SearchCriteria("materialId", SearchCriterion.EQUALS, msds.getMaterialId().toPlainString());
						deleteExisting.addCriterion("revisionDate", SearchCriterion.EQUALS, DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
						compositionFactory.delete(deleteExisting, conn);

						ChemicalBeanFactory chemFactory = new ChemicalBeanFactory(dbManager);

						for (CompositionBean composition : msds.getCompositionData()) {
							if (!StringHandler.isBlankString(composition.getCasNumber()) && !StringHandler.isBlankString(composition.getMsdsChemicalName())) {
								if (!chemFactory.exists(composition.getCasNumber()) ) {
									if (!chemFactory.isValidCASNumber(composition.getCasNumber())) {
										break;
									}
									ChemicalBean cas = new ChemicalBean();
									cas.setCasNumber(composition.getCasNumber());
									cas.setPreferredName(composition.getMsdsChemicalName());
									chemFactory.insert(cas, conn);
								}
								boolean trace = Boolean.parseBoolean(composition.getTrace());
								boolean tradeSecret = Boolean.parseBoolean(composition.getTradeSecret());
								composition.setTrace(trace?"Y":"N");
								composition.setTradeSecret(tradeSecret?"Y":"N");
								composition.setMaterialId(msds.getMaterialId());
								composition.setRevisionDate(msds.getRevisionDate());
								composition.setChemicalId(composition.getMsdsChemicalName());
								//System.out.println("compositionFactory.insert(composition, conn)");
								compositionFactory.insert(composition, conn);
							}
						}
					}

					//set/check data_entry_complete
					//if we are not sending data to Maxcom then check to make sure that global data entry is complete
					if (input.hasqId() || ! sendToMaxcom(msds.getIdOnly(),conn)) {
						//call procedure to set global data entry complete and standard
						//if it hasn't been called

						checkSetDataEntryComplete(input, msds, user, qcBean, coQcBean, conn);
						if (input.isSubmitForQc()) {
							// the method checks for data entry complete
							submitForQc(msds,user);
						}

						//if global data entry complete then call procedure to create company msds data if needed
						//TCMISDEV-2619
						if ("Y".equalsIgnoreCase(globalDataEntryComplete)) {
							Collection inArgs = new Vector(4);
							inArgs.add(msds.getMaterialId());
							inArgs.add(msds.getRevisionDate());
							inArgs.add(null);
							inArgs.add("Y");
							factory.doProcedure(conn,"pkg_data_entry_complete.p_insert_company_msds",inArgs);
						}

						if ("Y".equalsIgnoreCase(companyDataEntryComplete) && input.isApprove()) {
							// if we are about to successfully approve the customer record,
							// then approve the global record with source 'Customer QC,' if it exists
							StringBuilder updateStmt = new StringBuilder("update global.msds_qc ");
							updateStmt.append("set approve_by=").append(user.getPersonnelIdBigDecimal()).append(", ");
							updateStmt.append("approve_date=sysdate where ");
							updateStmt.append("material_id = ").append(msds.getMaterialId());
							updateStmt.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
							updateStmt.append(" and source = 'Customer QC'");

							factory.deleteInsertUpdate(updateStmt.toString(), conn);
						}
					}
				}
				else if (input.isAuthoring()) {
					determineAuthoringType(input, msds, conn);
				}

				doJobQueue(msds,conn);
			}
		}
		catch(BaseException e) {
			e.printStackTrace();
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}

	public void insertCompanyMsdsForCatalogAdd(MsdsIndexingBean msds, MsdsIndexingRequestBean request) throws Exception {
		Connection conn = dbManager.getConnection();
		try {
			//insert record into company_msds if company of catalog add has company override requirements
			StringBuilder query = new StringBuilder("select carn.company_id from catalog_add_request_new carn, company c");
			query.append(" where carn.request_id = ").append(request.getRequestId()).append(" and carn.company_id = c.company_id and c.company_msds = 'Y'");
			String companyId = factory.selectSingleValue(query.toString(),conn);
			if (!StringHandler.isBlankString(companyId) && msds.getMaterial() != null && msds.getRevisionDate() != null) {
				Collection inArgs = new Vector(4);
				inArgs.add(msds.getMaterialId());
				inArgs.add(msds.getRevisionDate());
				inArgs.add(companyId);
				inArgs.add("Y");
				factory.doProcedure(conn, "pkg_data_entry_complete.p_insert_company_msds", inArgs);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(conn);
		}
	}

	public Collection updateCOForCatalog(MsdsIndexingBean msds, int personnelId, Connection conn) throws BaseException {
		Vector<Object> inArgs = new Vector();
		MsdsIndexingBean co = msds.getCoData();
		inArgs.add(msds.getCompanyId());// request company id
		inArgs.add(msds.getMaterialId());//using global
		inArgs.add(msds.getRevisionDate());//using global
		inArgs.add(co.getContent());
		inArgs.add(co.getHealth());
		inArgs.add(co.getFlammability());
		inArgs.add(co.getReactivity());
		inArgs.add(co.getSpecificHazard());
		inArgs.add(co.getNfpaSource());
		inArgs.add(co.getHmisHealth());
		inArgs.add(co.getHmisFlammability());
		inArgs.add(co.getHmisReactivity());
		inArgs.add(co.getPersonalProtection());
		inArgs.add(co.getHmisChronic());
		inArgs.add(co.getHmisSource());
		inArgs.add(co.getSpecificGravityDetect());
		inArgs.add(co.getSpecificGravityLower());
		inArgs.add(co.getSpecificGravityUpper());
		inArgs.add(co.getSpecificGravityBasis());
		inArgs.add(co.getSpecificGravitySource());
		inArgs.add(co.getDensityDetect());
		inArgs.add(co.getDensity());
		inArgs.add(co.getDensityUpper());
		inArgs.add(co.getDensityUnit());
		inArgs.add(co.getDensitySource());
		inArgs.add(co.getFlashPointDetect());
		inArgs.add(co.getFlashPointLower());
		inArgs.add(co.getFlashPointUpper());
		inArgs.add(co.getFlashPointUnit());
		inArgs.add(co.getFlashPointMethod());
		inArgs.add(co.getFlashPointSource());
		inArgs.add(co.getBoilingPointDetect());
		inArgs.add(co.getBoilingPointLower());
		inArgs.add(co.getBoilingPointUpper());
		inArgs.add(co.getBoilingPointUnit());
		inArgs.add(co.getBoilingPointSource());
		inArgs.add(co.getBoilingPointDetail());
		inArgs.add(co.getPhDetect());
		inArgs.add(co.getPh());
		inArgs.add(co.getPhUpper());
		inArgs.add(co.getPhDetail());
		inArgs.add(co.getPhSource());
		inArgs.add(co.getVocLower());
		inArgs.add(co.getVoc());
		inArgs.add(co.getVocUpper());
		inArgs.add(co.getVocUnit());
		inArgs.add(co.getVocSource());
		inArgs.add(co.getVocLessH2oExemptLower());
		inArgs.add(co.getVocLessH2oExempt());
		inArgs.add(co.getVocLessH2oExemptUpper());
		inArgs.add(co.getVocLessH2oExemptUnit());
		inArgs.add(co.getVocLessH2oExemptSource());
		inArgs.add(co.getSolidsLower());
		inArgs.add(co.getSolids());
		inArgs.add(co.getSolidsUpper());
		inArgs.add(co.getSolidsUnit());
		inArgs.add(co.getSolidsSource());
		inArgs.add(co.getVaporPressureDetect());
		inArgs.add(co.getVaporPressure());
		inArgs.add(co.getVaporPressure());
		inArgs.add(co.getVaporPressureUpper());
		inArgs.add(co.getVaporPressureUnit());
		inArgs.add(co.getVaporPressureTemp());
		inArgs.add(co.getVaporPressureTempUnit());
		inArgs.add(co.getVaporPressureSource());
		inArgs.add(co.getCarcinogen());
		inArgs.add(co.getChronic());
		inArgs.add(co.getCorrosive());
		inArgs.add(co.getEvaporationRate());
		inArgs.add(co.getFireConditionsToAvoid());
		inArgs.add(co.getIncompatible());
		inArgs.add(co.getOxidizer());
		inArgs.add(co.getPolymerize());
		inArgs.add(co.getStable());
		inArgs.add(co.getVaporDensity());
		inArgs.add(co.getWaterReactive());
		inArgs.add(co.getOzoneDepletingCompound());
		inArgs.add(co.getLowVolumeExempt());
		inArgs.add(co.getPhysicalState());
		inArgs.add(co.getAlloy());
		inArgs.add(co.getDetonable());
		inArgs.add(co.getMiscible());
		inArgs.add(co.getPyrophoric());
		inArgs.add(co.getSpontaneouslyCombustible());
		inArgs.add(co.getTscaStatement());
		inArgs.add(co.getPhysicalStateSource());
		inArgs.add(co.getSara311312Acute());
		inArgs.add(co.getSara311312Chronic());
		inArgs.add(co.getSara311312Fire());
		inArgs.add(co.getSara311312Pressure());
		inArgs.add(co.getSara311312Reactivity());
		inArgs.add(personnelId);
		inArgs.add(null);
		inArgs.add(co.getAsMixed());
		inArgs.add(co.getHealthEffects());

		return factory.doProcedure(conn,"pkg_company_msds.p_upsert_company_msds", inArgs, new Vector());
	}

	//p_set_data_entry_complete will set global/company data_entry_complete = Y, msds_data_entry_standard = current standard, and on_line = Y if required data are satisfied
	//and return what it set data_entry_complete to Y/N
	public String setDataEntryComplete(BigDecimal materialId, Date revisionDate, String companyId, PersonnelBean user, boolean includeQc, Connection conn) {
		String result = "";
		String pkg = "pkg_data_entry_complete";
		try {
			Collection inArgs = new Vector(3);
			inArgs.add(materialId);
			inArgs.add(revisionDate);
			inArgs.add(companyId);
			inArgs.add(includeQc?"Y":"N");
			inArgs.add(user.getPersonnelId());

			Collection outArgs = new Vector(2);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			Collection data = factory.doProcedure(conn,pkg+".P_SET_DATA_ENTRY_COMPLETE",inArgs,outArgs);
			Iterator i11 = data.iterator();
			String erroVal = "";
			int i = 0;
			while (i11.hasNext()) {
				if (i++ == 0)
					result = (String) i11.next();
				else
					erroVal = (String) i11.next();
			}

			if (!StringHandler.isBlankString(erroVal)) {
				if (!"OK".equalsIgnoreCase(erroVal)) {
					MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",pkg+".P_SET_DATA_ENTRY_COMPLETE (material_id: " + materialId + " revision_date: " + revisionDate+ " company_id: "+companyId +")","Error while executing P_SET_DATA_ENTRY_COMPLETE: "+erroVal);
				}
			}
		}catch ( Exception dbe ) {
			result = "N";
			log.error(dbe);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",pkg+".P_SET_DATA_ENTRY_COMPLETE (material_id: " + materialId + " revision_date: " + revisionDate+ " company_id: "+companyId +")","Error while executing P_SET_DATA_ENTRY_COMPLETE, see log for details");
		}
		if (StringHandler.isBlankString(result))
			result = "N";
		return result.trim();
	}

	public String checkDataEntryComplete(BigDecimal materialId, Date revisionDate, String companyId, boolean includeQc, Connection conn) {
		String result = "";
		try {
			StringBuilder query = new StringBuilder("select pkg_data_entry_complete.FX_DATA_ENTRY_COMPLETE(");
			query.append(materialId).append(", ").append(DateHandler.getOracleToDateFunction(revisionDate)).append(", ");
			if ( ! StringHandler.isBlankString(companyId)) {
				query.append("'"+companyId+"', ");
			}
			else {
				query.append("null, ");
			}
			query.append((includeQc?"'Y'":"'N'")+") from dual");

			result = factory.selectSingleValue(query.toString(),conn);
		}catch ( Exception dbe ) {
			result = "N";
			log.error(dbe);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","pkg_data_entry_complete.FX_DATA_ENTRY_COMPLETE (material_id: " + materialId + " revision_date: " + revisionDate+ " company_id: "+companyId +" include_qc: N)","Error while executing P_SET_DATA_ENTRY_COMPLETE, see log for details");
		}
		if (StringHandler.isBlankString(result))
			result = "N";
		return result.trim();
	}

	private boolean sendToMaxcom(String idOnly, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("select count(*) from global.vv_id_only where id_only = '");
		query.append(idOnly).append("' and send_to_maxcom = 'N'");
		BigDecimal send = new BigDecimal(factory.selectSingleValue(query.toString(),conn));
		return (send.intValue() == 0);
	}

	public void doJobQueue(MsdsIndexingBean msds,Connection conn) throws BaseException {
		Vector inArgs = new Vector();
		inArgs.add(msds.getMaterialId());
		inArgs.add(msds.getRevisionDate());
		//System.out.println("pkg_kit_job_queue.p_create_queue("+msds.getMaterialId()+","+msds.getRevisionDate()+")");
		factory.doProcedure(conn,"pkg_kit_job_queue.p_create_queue", inArgs, new Vector());
	}

	public void upsertReportSnap(BigDecimal materialId, Date revisionDate) throws BaseException {
		Connection conn = dbManager.getConnection();

		Vector inArgs = new Vector();
		inArgs.add(materialId);
		inArgs.add(revisionDate);
		//inArgs.add(companyId);

		try {
			//System.out.println("pkg_report_snap.p_upsert_report_snap("+materialId+","+revisionDate+")");
			factory.doProcedure(conn, "pkg_report_snap.p_upsert_report_snap", inArgs);
		}
		catch(Exception e){
			log.error(e);
		} finally {
			dbManager.returnConnection(conn);
		}
	}

	public boolean isValidCasNumber(String casNumber) throws BaseException {
		return new ChemicalBeanFactory(dbManager).isValidCASNumber(casNumber);
	}

	public String globalDataEntryCompleteRequired() {
		String result = "";
		if ("N".equals(globalDataEntryComplete))
			result = library.getString("label.globaldataentrycompleterequired");
		return result;
	}

	public String companyDataEntryCompleteRequired() {
		String result = "";
		if ("N".equals(companyDataEntryComplete))
			result = library.getString("label.companydataentrycompleterequired");
		return result;
	}

	public void approveRequest(CatalogAddReqMsdsInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			approveRequest(inputBean,personnelBean,conn);
		}catch(Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(conn);
		}
	}

	public void approveRequest(CatalogAddReqMsdsInputBean inputBean, PersonnelBean personnelBean, Connection conn) throws BaseException {
		try {
			if ("Material QC".equals(inputBean.getApprovalRole())) {
				approveMaterialQC(inputBean,personnelBean,conn);
			}else if ("MSDS Indexing".equals(inputBean.getApprovalRole())) {
				approveMSDSIndexing(inputBean,personnelBean,conn);
			}else if ("Custom Indexing".equals(inputBean.getApprovalRole())) {
				approveCompanyMSDS(inputBean,personnelBean,conn);
			}else if ("SDS QC".equals(inputBean.getApprovalRole())) {
				approveMSDSQC(inputBean,personnelBean,conn);
			}
		}catch(Exception e) {
			log.error(e);
		}
	}

	private void approveMSDSQC(CatalogAddReqMsdsInputBean inputBean, PersonnelBean user, Connection conn) throws BaseException {
		try {
			StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
			factory.setBeanObject(new CatAddHeaderViewBean());
			Vector<CatAddHeaderViewBean> dataC = (Vector)factory.selectQuery(query.toString(),conn);
			CatAddHeaderViewBean bean = dataC.get(0);

			query = new StringBuilder(" set msds_qc_status = 'Approved' where request_id = ");
			query.append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem()).append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			////update catalog_add_item.custom_indexing_status
			StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);
			////update catalog_add_item_qc.custom_indexing_status
			tmpQ = new StringBuilder("update catalog_add_item_qc");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);

			query = new StringBuilder("select count(*) from catalog_add_item_qc where company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" and request_id = ").append(inputBean.getRequestId()).append(" and msds_qc_status = 'Pending'");
			String tmpVal = factory.selectSingleValue(query.toString(),conn);
			if ("0".equals(tmpVal)) {
				//check to see if approver approved all the line then send request to next queue
				bean.setHasHmrbTab(user.isFeatureReleased("HmrbTab",bean.getEngEvalFacilityId(),bean.getCompanyId())?"Y":"N");
				CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),"");
				tmpVal = process.approvalRequestFromCatAddReqMsdsProcess(bean,inputBean.getApprovalRole(),user);
				if (!"OK".equalsIgnoreCase(tmpVal)) {
					MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveMSDSQC failed: "+inputBean.getRequestId(),tmpVal);
				}
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveMSDSQC failed: "+inputBean.getRequestId(),"approveMSDSQC failed: "+inputBean.getRequestId());
		}
	}

	private void approveMaterialQC(CatalogAddReqMsdsInputBean inputBean, PersonnelBean user, Connection conn) throws BaseException {
		try {
			StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
			factory.setBeanObject(new CatAddHeaderViewBean());
			Vector<CatAddHeaderViewBean> dataC = (Vector)factory.selectQuery(query.toString(),conn);
			CatAddHeaderViewBean bean = dataC.get(0);
			if(StringHandler.isBlankString(bean.getLineItem()))
				bean.setLineItem(inputBean.getLineItem());

			query = new StringBuilder(" set material_qc_status = 'Approved' where request_id = ");
			query.append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem()).append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			////update catalog_add_item.material_qc_status
			StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);
			////update catalog_add_item_qc.material_qc_status
			tmpQ = new StringBuilder("update catalog_add_item_qc");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);

			//if MSDS approval ONLY
			if ((new BigDecimal(6)).equals(bean.getStartingView()) || (new BigDecimal(7)).equals(bean.getStartingView())) {
				query = new StringBuilder("update catalog_add_request_new set qc_status = 'Passed QC' where request_id = ").append(inputBean.getRequestId());
				factory.deleteInsertUpdate(query.toString(),conn);
				//
				query = new StringBuilder("update catalog_add_item cai set (material_id,customer_msds_number) =");
				query.append(" (select material_id,customer_msds_number from catalog_add_item_qc caiq where cai.company_id = caiq.company_id");
				query.append(" and cai.request_id = caiq.request_id and cai.line_item = caiq.line_item and cai.part_id = caiq.part_id)");
				query.append(" where cai.company_id = '").append(inputBean.getCompanyId()).append("' and cai.request_id = ").append(inputBean.getRequestId()).append(" and cai.line_item = ").append(inputBean.getLineItem());
				factory.deleteInsertUpdate(query.toString(),conn);

				bean.setHasHmrbTab(user.isFeatureReleased("HmrbTab",bean.getEngEvalFacilityId(),bean.getCompanyId())?"Y":"N");
				CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),"");
				String tmpVal = process.approvalRequestFromCatAddReqMsdsProcess(bean,inputBean.getApprovalRole(),user);
				if (!"OK".equalsIgnoreCase(tmpVal)) {
					MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveMaterialQC failed: "+inputBean.getRequestId(),tmpVal);
				}

			}else {
				//the reason that I am not updating catalog_add_item data from catalog_add_item_qc here is because this data will be updated in the Item QC process
				//if request has any line that is still pending Material QC than don't approve request
				query = new StringBuilder("select count(*) from catalog_add_item_qc where company_id = '").append(inputBean.getCompanyId()).append("'");
				query.append(" and request_id = ").append(inputBean.getRequestId()).append(" and material_qc_status = 'Pending'");
				String tmpVal = factory.selectSingleValue(query.toString(),conn);
				if ("0".equals(tmpVal)) {
					//check to see if approver approved all the line then send request to next queue
					bean.setHasHmrbTab(user.isFeatureReleased("HmrbTab",bean.getEngEvalFacilityId(),bean.getCompanyId())?"Y":"N");
					CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),URL);
					tmpVal = process.approvalRequestFromCatAddReqMsdsProcess(bean,inputBean.getApprovalRole(),user);
					if (!"OK".equalsIgnoreCase(tmpVal)) {
						MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveMaterialQC failed: "+inputBean.getRequestId(),tmpVal);
					}
				}
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveMaterialQC failed: "+inputBean.getRequestId(),"approveMaterialQC failed: "+inputBean.getRequestId());
		}
	}

	private void approveMSDSIndexing(CatalogAddReqMsdsInputBean inputBean, PersonnelBean user, Connection conn) throws BaseException {
		try {
			StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
			factory.setBeanObject(new CatAddHeaderViewBean());
			Vector<CatAddHeaderViewBean> dataC = (Vector)factory.selectQuery(query.toString(),conn);
			CatAddHeaderViewBean bean = dataC.get(0);

			query = new StringBuilder(" set msds_indexing_status = 'Approved' where request_id = ");
			query.append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem()).append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			////update catalog_add_item.msds_indexing_status
			StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);
			////update catalog_add_item_qc.msds_indexing_status
			tmpQ = new StringBuilder("update catalog_add_item_qc");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);

			query = new StringBuilder("select count(*) from catalog_add_item_qc where company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" and request_id = ").append(inputBean.getRequestId()).append(" and msds_indexing_status = 'Pending'");
			String tmpVal = factory.selectSingleValue(query.toString(),conn);
			if ("0".equals(tmpVal)) {
				//check to see if approver approved all the line then send request to next queue
				bean.setHasHmrbTab(user.isFeatureReleased("HmrbTab",bean.getEngEvalFacilityId(),bean.getCompanyId())?"Y":"N");
				CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),"");
				tmpVal = process.approvalRequestFromCatAddReqMsdsProcess(bean,inputBean.getApprovalRole(),user);
				//
				query = new StringBuilder("update catalog_add_request_new set msds_indexing_status = 'Approved'");
				query.append(" where company_id = '").append(bean.getCompanyId()).append("' and request_id = ").append(bean.getRequestId());
				factory.deleteInsertUpdate(query.toString(),conn);

				if (!"OK".equalsIgnoreCase(tmpVal)) {
					MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveMSDSIndexing failed: "+inputBean.getRequestId(),tmpVal);
				}
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveMSDSIndexing failed: "+inputBean.getRequestId(),"approveMSDSIndexing failed: "+inputBean.getRequestId());
		}
	}

	private void approveCompanyMSDS(CatalogAddReqMsdsInputBean inputBean, PersonnelBean user, Connection conn) throws BaseException {
		try {
			StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
			factory.setBeanObject(new CatAddHeaderViewBean());
			Vector<CatAddHeaderViewBean> dataC = (Vector)factory.selectQuery(query.toString(),conn);
			CatAddHeaderViewBean bean = dataC.get(0);

			query = new StringBuilder(" set custom_indexing_status = 'Approved' where request_id = ");
			query.append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem()).append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			////update catalog_add_item.custom_indexing_status
			StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);
			////update catalog_add_item_qc.custom_indexing_status
			tmpQ = new StringBuilder("update catalog_add_item_qc");
			tmpQ.append(query);
			factory.deleteInsertUpdate(tmpQ.toString(),conn);

			query = new StringBuilder("select count(*) from catalog_add_item_qc where company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" and request_id = ").append(inputBean.getRequestId()).append(" and custom_indexing_status = 'Pending'");
			String tmpVal = factory.selectSingleValue(query.toString(),conn);
			if ("0".equals(tmpVal)) {
				//check to see if approver approved all the line then send request to next queue
				bean.setHasHmrbTab(user.isFeatureReleased("HmrbTab",bean.getEngEvalFacilityId(),bean.getCompanyId())?"Y":"N");
				CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),"");
				tmpVal = process.approvalRequestFromCatAddReqMsdsProcess(bean,inputBean.getApprovalRole(),user);
				if (!"OK".equalsIgnoreCase(tmpVal)) {
					MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveCompanyMSDS failed: "+inputBean.getRequestId(),tmpVal);
				}
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","approveCompanyMSDS failed: "+inputBean.getRequestId(),"approveCompanyMSDS failed: "+inputBean.getRequestId());
		}
	}

	public String getKitDocs(CatalogAddReqMsdsInputBean input) throws Exception{

		Connection conn = dbManager.getConnection();
		StringBuffer result = new StringBuffer("");

		try {
			ResourceLibrary resource = new ResourceLibrary("uploadFile");
			factory.setBean(new CatalogAddReqMsdsQcOvBean());

			StringBuilder query = new StringBuilder("SELECT * from catalog_add_document where company_id = '").append(input.getCompanyId()).append("' and REQUEST_ID =").append(input.getRequestId());
			query.append(" and line_item = ").append(input.getLineItem());
			Collection<CatalogAddReqMsdsQcOvBean> res = factory.selectQuery(query.toString(),conn);
			for (CatalogAddReqMsdsQcOvBean bean:res) {
				result.append("/../../").append(bean.getDocumentUrl()).append("\n");
			}
		}
		finally {
			dbManager.returnConnection(conn);
		}

		return result.toString();
	}

	public void fillRequestData(CatalogAddReqMsdsInputBean input, MsdsIndexingBean msds, BigDecimal component) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		StringBuilder caicQuery = new StringBuilder("select caic.comments, caic.customer_msds_number component_msds ");
		caicQuery.append("from catalog_add_item_qc caic");
		caicQuery.append(" where caic.request_id = ").append(input.getRequestId());
		caicQuery.append(" and caic.line_item = ").append(StringHandler.nullIfEmpty(input.getLineItem()));
		caicQuery.append(" and caic.part_id = ").append(component.add(new BigDecimal(1)));
		caicQuery.append(" and caic.catalog_add_item_id = ").append(input.getCatalogAddItemId());
		Collection<MsdsIndexingBean> beanColl = factory.selectQuery(caicQuery.toString());
		Iterator<MsdsIndexingBean> it = beanColl.iterator();
		if (it.hasNext()) {
			MsdsIndexingBean bean = it.next();
			msds.setComments(bean.getComments());
			msds.setComponentMsds(bean.getComponentMsds());
		}
	}

	public void fillInQcData(MsdsIndexingBean msds, boolean customer) throws BaseException {
		factory.setBean(new MsdsIndexingQcBean());

		String qcTable = customer?"company_msds_qc":"msds_qc";
		StringBuilder query = new StringBuilder("select * from "+qcTable);
		query.append(" where material_id = ").append(msds.getMaterialId());
		query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
		if (customer) {
			query.append(" and company_id = '").append(msds.getCompanyId()).append("'");
		}

		Collection<MsdsIndexingQcBean> qcColl = factory.selectQuery(query.toString());
		if (qcColl.size() > 0) {
			factory.setBean(new MsdsIndexingQcDetailBean());
			msds.setQcData(qcColl.iterator().next());

			query = new StringBuilder("select qc.*, err.qc_error_type from "+qcTable+"_detail qc, vv_msds_qc_error_type err");
			query.append(" where qc.material_id = ").append(msds.getMaterialId());
			query.append(" and qc.revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
			if (customer) {
				query.append(" and company_id = '").append(msds.getCompanyId()).append("'");
			}
			query.append(" and qc.qc_error_type_id = err.qc_error_type_id");

			Collection<MsdsIndexingQcDetailBean> detailColl = factory.selectQuery(query.toString());
			if (detailColl != null) {
				for (Iterator<MsdsIndexingQcDetailBean> it = detailColl.iterator();it.hasNext();) {
					MsdsIndexingQcDetailBean detailBean = it.next();
					String colName = detailBean.getColumnName();
					/*if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_ALLOY)) {
						msds.getQcData().setAlloyQcErrorType(detailBean.getQcErrorTypeId());
					}*/
					if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_AUTHOR)) {
						msds.getQcData().setMsdsAuthorQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_BOILING_POINT)) {
						msds.getQcData().setBoilingPointQcErrorType(detailBean.getQcErrorTypeId());
					}
					/*else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_CARCINOGEN)) {
						msds.getQcData().setCarcinogenQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_CHRONIC)) {
						msds.getQcData().setChronicQcErrorType(detailBean.getQcErrorTypeId());
					}*/
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_COMPOSITION)) {
						msds.getQcData().setCompositionQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_CORROSIVE)) {
						msds.getQcData().setCorrosiveQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_DENSITY)) {
						msds.getQcData().setDensityQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_DESCRIPTION)) {
						msds.getQcData().setMaterialDescriptionQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_DETONABLE)) {
						msds.getQcData().setDetonableQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_EVAPORATION_RATE)) {
						msds.getQcData().setEvaporationRateQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_FIRE)) {
						msds.getQcData().setFireConditionsToAvoidQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_FLASHPOINT)) {
						msds.getQcData().setFlashPointQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_GHS_ADDRESS)) {
						msds.getQcData().setLabelAddressQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_HAZARD)) {
						msds.getQcData().setHazardStatementsQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_HEALTH_EFFECTS)) {
						msds.getQcData().setHealthEffectsQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_HMIS)) {
						msds.getQcData().setHmisQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_IMAGE)) {
						msds.getQcData().setContentQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_INCOMPATIBLE)) {
						msds.getQcData().setIncompatibleQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_LOCALIZED_MATERIAL_DESC)) {
						msds.getQcData().setLocalizedMaterialDescQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_LOCALIZED_TRADE_NAME)) {
						msds.getQcData().setLocalizedTradeNameQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_MANUFACTURER)) {
						msds.getQcData().setManufacturerQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_NFPA)) {
						msds.getQcData().setNfpaQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_OXIDIZER)) {
						msds.getQcData().setOxidizerQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PH)) {
						msds.getQcData().setPhQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PHYSICAL_STATE)) {
						msds.getQcData().setPhysicalStateQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PICTOGRAMS)) {
						msds.getQcData().setGhsPictogramsQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_POLYMERIZE)) {
						msds.getQcData().setPolymerizeQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PRECAUTIONARY)) {
						msds.getQcData().setPrecautionaryStatementsQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PRODUCT_CODE)) {
						msds.getQcData().setProductCodeQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_REVISION_DATE)) {
						msds.getQcData().setRevisionDateQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SARA)) {
						msds.getQcData().setSaraQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SIGNAL_WORD)) {
						msds.getQcData().setSignalWordQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SOLIDS)) {
						msds.getQcData().setSolidsQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SPECIFIC_GRAVITY)) {
						msds.getQcData().setSpecificGravityQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_STABLE)) {
						msds.getQcData().setStableQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_TRADE_NAME)) {
						msds.getQcData().setTradeNameQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_TSCA)) {
						msds.getQcData().setTscaQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VAPOR_DENSITY)) {
						msds.getQcData().setVaporDensityQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VAPOR_PRESSURE)) {
						msds.getQcData().setVaporPressureQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VOC)) {
						msds.getQcData().setVocQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VOC_LWES)) {
						msds.getQcData().setVocLessH2oExemptQcErrorType(detailBean.getQcErrorTypeId());
					}
					else if (colName.equals(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_WATER)) {
						msds.getQcData().setWaterReactiveQcErrorType(detailBean.getQcErrorTypeId());
					}
				}
			}
		}
	}

	public void submitForQc(MsdsIndexingBean msds, PersonnelBean user) throws BaseException {
		factory.setBean(new MsdsIndexingQcBean());

		String globalDataEntryCompleteRequired = globalDataEntryCompleteRequired();
		String companyDataEntryCompleteRequired = companyDataEntryCompleteRequired();
		if (StringHandler.isBlankString(globalDataEntryCompleteRequired)) {
			StringBuilder stmt = new StringBuilder("update msds_qc");
			stmt.append(" set submit_date=sysdate, submit_by=").append(user.getPersonnelId());
			stmt.append(" where material_id = ").append(msds.getMaterialId());
			stmt.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
			stmt.append(" and submit_date is null");

			factory.deleteInsertUpdate(stmt.toString());

			if (StringHandler.isBlankString(companyDataEntryCompleteRequired) && !StringHandler.isBlankString(msds.getCompanyId())) {
				stmt = new StringBuilder("update company_msds_qc");
				stmt.append(" set submit_date=sysdate, submit_by=").append(user.getPersonnelId());
				stmt.append(" where material_id = ").append(msds.getMaterialId());
				stmt.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
				stmt.append(" and company_id = '").append(msds.getCompanyId()).append("'");
				stmt.append(" and submit_date is null");

				factory.deleteInsertUpdate(stmt.toString());
			}
		}
	}

	public Collection<UserCompanyViewBean> getUserQcCompaines(PersonnelBean user) throws BaseException {
		factory.setBean(new UserCompanyViewBean());

		StringBuilder query = new StringBuilder("select company_id from ");
		if ("TCM_OPS".equals(this.getClient()))
			query.append("customer.user_company");
		else
			query.append("user_company");
		query.append(" where personnel_id = ").append(user.getPersonnelId());
		query.append(" and msds_qc_role = 'Y'");

		return factory.selectQuery(query.toString());
	}

	public boolean userIsApprover(PersonnelBean user, String companyId) throws BaseException {
		Connection conn = dbManager.getConnection();
		boolean approver = false;
		try {
			approver = userIsApprover(user, companyId, conn);
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return approver;
	}

	protected boolean userIsApprover(PersonnelBean user, String companyId, Connection conn) throws BaseException {
		factory.setBean(new UserCompanyBean());

		if (companyId == null) {
			companyId = "Radian";
		}
		StringBuilder query = new StringBuilder("select * from customer.user_company");
		query.append(" where personnel_id = ").append(user.getPersonnelIdBigDecimal());
		query.append(" and company_id = '").append(companyId).append("'");
		query.append(" and msds_qc_role = 'Y'");

		Collection dataSet = factory.selectQuery(query.toString(), conn);
		Iterator dataIter = dataSet.iterator();

		boolean hasRole = false;
		if (dataIter.hasNext()) {
			hasRole = true;
		}

		return hasRole;
	}

	private void queryDEC(MsdsIndexingBean msds, Connection conn) throws BaseException {
		factory.setBean(new MsdsIndexingBean());

		StringBuilder query = new StringBuilder("select data_entry_complete from global.msds");
		query.append(" where material_id = ").append(msds.getMaterialId());
		query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));

		String globalDec = factory.selectSingleValue(query.toString(), conn);
		if (StringHandler.isBlankString(globalDec)) {
			msds.setDataEntryComplete(false);
		}
		else {
			msds.setDataEntryComplete("Y".equals(globalDec));
		}

		if (msds.isSaveCustomerOverride() && msds.getCoData() != null) {
			query = new StringBuilder("select data_entry_complete from company_msds");
			query.append(" where material_id = ").append(msds.getMaterialId());
			query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
			query.append(" and company_id = '").append(msds.getCompanyId()).append("'");

			String customerDec = factory.selectSingleValue(query.toString(), conn);
			if (StringHandler.isBlankString(customerDec)) {
				msds.getCoData().setDataEntryComplete(false);
			}
			else {
				msds.getCoData().setDataEntryComplete("Y".equals(customerDec));
			}
		}
	}

	public void checkSetDataEntryComplete(CatalogAddRequestVendorBean carvBean, PersonnelBean user, Connection conn) throws BaseException {
		CatalogAddReqMsdsInputBean input = new CatalogAddReqMsdsInputBean();
		input.setqId(carvBean.getqId());
		input.setApprove(true);

		String query = "select material_id, revision_date"
				+ " from global.msds, catalog_queue q"
				+ " where msds.material_id = q.material_id and msds.revision_date = q.revision_date"
				+ " and q.q_id = " + carvBean.getqId();


		Optional<MsdsIndexingBean> selection = factory.setBean(new MsdsIndexingBean()).selectQuery(query, conn).stream().findAny();
		if (selection.isPresent()) {
			MsdsIndexingBean sds = selection.get();
			MsdsIndexingQcBeanFactory msdsQcFactory = new MsdsIndexingQcBeanFactory(dbManager);
			MsdsIndexingQcBean qcBean = msdsQcFactory.getMsdsIndexingQcBean(sds.getMaterialId(), sds.getRevisionDate(), null, conn);

			checkSetDataEntryComplete(input, sds, user, qcBean, null, conn);
		}
	}

	public void checkSetDataEntryComplete(CatalogAddReqMsdsInputBean input, MsdsIndexingBean msds, PersonnelBean user,
										  MsdsIndexingQcBean qcBean, MsdsIndexingQcBean coQcBean, Connection conn) throws BaseException {
		String globalDec = "N";
		String customerDec = "N";

		if (input.isSubmitForQc()) {
			if (qcBean != null) {
				globalDec = checkDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, false, conn);
			}
			else {
				globalDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, user, false, conn);
			}

			// if company ID is null, then any customer call will be a repeat of the global call
			if (msds.getCompanyId() != null) {
				if (coQcBean != null) {
					customerDec = checkDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), false, conn);
				}
				else {
					customerDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), user, false, conn);
				}
			}
			else {
				customerDec = "Y";
			}
		}
		else if (input.isApprove()) {
			if (qcBean != null && input.hasqId()) {
				boolean userIsApprover = userIsApprover(user,null,conn);
				if (qcBean.getSubmitDate() != null && qcBean.getApproveDate() == null &&
						! user.getPersonnelIdBigDecimal().equals(qcBean.getSubmitBy()) && userIsApprover) {
					globalDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, user, true, conn);
				}
				else {
					globalDec = checkDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, true, conn);
				}
			}
			else {
				globalDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, user, true, conn);
			}

			// if company ID is null, then any customer call will be a repeat of the global call
			if (msds.getCompanyId() != null) {
				if (coQcBean != null) {
					boolean userIsApprover = userIsApprover(user,msds.getCompanyId(),conn);
					if (coQcBean.getSubmitDate() != null && coQcBean.getApproveDate() == null &&
							! user.getPersonnelIdBigDecimal().equals(coQcBean.getSubmitBy()) && userIsApprover) {
						customerDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), user, true, conn);
						// if global has already been approved, or does not require approval it will not be set yet
						// but global DEC must be true for customer DEC to be true
						// so set global DEC to "Y" so that no error message shows up on the page
						if (customerDec.equals("Y")) {
							globalDec = customerDec;
						}
					}
					else {
						customerDec = checkDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), true, conn);
						if (customerDec.equals("Y")) {
							globalDec = customerDec;
						}
					}
				}
				else {
					customerDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), user, true, conn);
					// if global has already been approved, or does not require approval it will not be set yet
					// but global DEC must be true for customer DEC to be true
					// so set global DEC to "Y" so that no error message shows up on the page
					if (customerDec.equals("Y")) {
						globalDec = customerDec;
					}
				}
			}
			else {
				customerDec = "Y";
			}
		}
		else {
			queryDEC(msds,conn);
			if (msds.isDataEntryComplete()) {
				globalDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, user, false, conn);
				if (msds.getCoData() != null && msds.getCoData().isDataEntryComplete()) {
					customerDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), user, false, conn);
				}
				else {
					customerDec = "Y";
				}
			}
			else {
				if (qcBean != null && qcBean.getSubmitDate() != null && qcBean.getApproveDate() == null) {
					globalDec = checkDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, false, conn);
				}
				else {
					globalDec = "Y";
					// globalDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), null, user, false, conn);
				}

				if (msds.getCompanyId() != null && msds.isSaveCustomerOverride()) {
					if (coQcBean != null && coQcBean.getSubmitDate() != null && coQcBean.getApproveDate() == null) {
						customerDec = checkDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), false, conn);
					}
					else {
						customerDec = "Y";
						// customerDec = setDataEntryComplete(msds.getMaterialId(), msds.getRevisionDate(), msds.getCompanyId(), user, false, conn);
					}
				}
				else {
					customerDec = "Y";
				}
			}
		}

		globalDataEntryComplete = globalDataEntryComplete=="N"?globalDataEntryComplete:globalDec;
		companyDataEntryComplete = companyDataEntryComplete=="N"?companyDataEntryComplete:customerDec;
	}

	public String getApprovalRoleBefore(BigDecimal requestId, String approvalRole) throws BaseException {
		String beforeAfter = "";
		StringBuilder query = new StringBuilder();

		query.append("select distinct pkg_company_msds.fx_is_approval_role_before(car.company_id,car.facility_id,car.approval_role,car.catalog_company_id,car.catalog_id,car.application,'SDS QC') before_sds_qc ");
		query.append("from vv_chemical_approval_role car, catalog_add_request_new carn,vv_catalog_add_request_status ars ");
		query.append("where carn.company_id = car.company_id and carn.eng_eval_facility_id = car.facility_id ");
		query.append("and carn.catalog_id = car.catalog_id and carn.approval_group_seq = car.approval_group_seq ");
		query.append("and carn.company_id = ars.company_id and carn.request_status = ars.request_status ");
		query.append("and ars.company_id = car.company_id and ars.approval_group = car.approval_group ");
		query.append("and carn.request_id = ").append(requestId);

		beforeAfter = factory.selectSingleValue(query.toString());
		return beforeAfter;
	}

	public String getRejectOutOfScopeFeature(BigDecimal requestId) throws BaseException {
		String hasOutOfScopeFeature = "N";
		StringBuilder query = new StringBuilder("select count(*) from vv_catalog_add_request_status cars, catalog_add_request_new carn");
		query.append(" where cars.company_id = carn.company_id and cars.out_of_scope = 'Y'");
		query.append(" and carn.request_id = ").append(requestId);
		if (!"0".equals(factory.selectSingleValue(query.toString())))
			hasOutOfScopeFeature = "Y";
		return hasOutOfScopeFeature;
	}

	public void rejectOutOfScope(CatalogAddReqMsdsInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		rejectRequest("Reject - Out of Scope", new BigDecimal(18), inputBean, personnelBean);
	}

	public void rejectCannotFulfill(CatalogAddReqMsdsInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		rejectRequest(inputBean.getRejectionComments(), new BigDecimal(7), inputBean, personnelBean);
	}

	private void rejectRequest(String reason, BigDecimal rejectStatus, CatalogAddReqMsdsInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		Connection connection = dbManager.getConnection();
		try {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),"");
			engEvalProcess.setFactoryConnection(factory,connection);
			//insert approval role into catalog_add_approval
			if (engEvalProcess.insertApprovalData(new BigDecimal(inputBean.getRequestId()),inputBean.getApprovalRole(),"Rejected",reason,new BigDecimal(personnelBean.getPersonnelId()),"N")) {
				//reject request
				StringBuilder query = new StringBuilder();
				query.append("update catalog_add_request_new set request_status = ").append(rejectStatus)
						.append(",approval_group_seq = 0,request_rejected = 'Y'")
						.append(",eval_status = 'Rejected',last_updated = sysdate,approval_group_seq_start_time = null where request_id = ")
						.append(inputBean.getRequestId());
				factory.deleteInsertUpdate(query.toString(),connection);

				query = new StringBuilder("update catalog_queue set status = ").append(SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_CLOSED));
				query.append(" where catalog_add_request_id = ").append(inputBean.getRequestId());
				factory.deleteInsertUpdate(query.toString(),connection);

				//send user email
				engEvalProcess.sendUserConfirmedEmail(new BigDecimal(inputBean.getRequestId()));
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
		}
	}  //end of method

	public void advanceAssignedToApproved(CatalogAddReqMsdsInputBean input, BigDecimal personnelId, Connection conn) throws BaseException {
        String updateStmt = "update catalog_queue set"
                + " task_complete_date = sysdate"
                + ", material_id = " + input.getMaterialId()
                + ", revision_date = " + DateHandler.getOracleToDateFunction(input.getRevisionDate())
                + ", supplier_approved_date = sysdate"
                + ", supplier_approved_by = " + personnelId
                + ", approved_date = sysdate"
                + ", approved_by = " + personnelId
                + ", status = 'Approved'"
                + " where q_id = " + input.getqId();
        factory.deleteInsertUpdate(updateStmt, conn);
        determineSourcingItemId(input, conn);
        updateMaterialSourcingFlag(input, conn);

	}	//end of method

	public void advanceCatalogQueue(CatalogAddReqMsdsInputBean input, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			advanceCatalogQueue(input, user, conn);
			if (input.isAssigned() && input.isExpressApproval()) {
				input.setCatalogQueueRowStatus(CatalogAddReqMsdsInputBean.STATUS_PENDING_QC);
				advanceCatalogQueue(input, user, conn);
				input.setCatalogQueueRowStatus(CatalogAddReqMsdsInputBean.STATUS_PENDING_APPROVAL);
				advanceCatalogQueue(input, user, conn);
			}
		} finally {
			dbManager.returnConnection(conn);
		}
	}

	public void advanceCatalogQueue(CatalogAddReqMsdsInputBean input, PersonnelBean user, Connection conn) throws BaseException {
		if (input.isAssigned()) {
			String updateStmt = "update catalog_queue set"
					+ " task_complete_date = sysdate"
					+ ", status = 'Pending QC'"
					+ ", material_id = " + input.getMaterialId()
					+ ", revision_date = " + DateHandler.getOracleToDateFunction(input.getRevisionDate())
					+ " where q_id = " + input.getqId()
					+ " and status = "+SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_ASSIGNED);

			factory.deleteInsertUpdate(updateStmt, conn);
			determineSourcingItemId(input, conn);
			updateMaterialSourcingFlag(input, conn);
		}
		else if (input.isPendingVendorQc()) {
			String updateStmt = "update catalog_queue set"
					+ " supplier_approved_date = sysdate"
					+ ", supplier_approved_by = " + user.getPersonnelIdBigDecimal()
					+ ", status = 'Pending Approval'"
					+ " where q_id = " + input.getqId()
					+ " and status = "+SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_PENDING_QC);

			factory.deleteInsertUpdate(updateStmt, conn);
			updateMaterialSourcingFlag(input, conn);
			notifyMasterDataTeam(input, conn);
		}
		else if (input.isPendingApproval()) {
			String updateStmt = "update catalog_queue set"
					+ " approved_date = sysdate"
					+ ", approved_by = " + user.getPersonnelIdBigDecimal()
					+ ", status = 'Approved'"
					+ " where q_id = " + input.getqId()
					+ " and status = "+SqlHandler.delimitString(CatalogAddReqMsdsInputBean.STATUS_PENDING_APPROVAL);

			factory.deleteInsertUpdate(updateStmt, conn);
		}
	}

	public void notifyMasterDataTeam(CatalogAddReqMsdsInputBean input, Connection conn) throws BaseException {
		try {
			String query = "select count(*) from catalog_queue where catalog_add_request_id = "
					+ input.getRequestId()
					+ " and task = " + SqlHandler.delimitString(input.getCatalogQueueRowTask())
					+ " and status <> 'Pending Approval'";

			String count = factory.selectSingleValue(query, conn);

			if (Integer.parseInt(count) == 0) {
				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(), getLocale(), "");
				engEvalProcess.setFactoryConnection(factory, conn);
				engEvalProcess.sendApproversEmail(new BigDecimal(input.getRequestId()), "", Collections.emptyList(), Collections.emptyList(),false);
			}
		} catch(Exception e) {
			throw new BaseException(e);
		}
	}

	private void determineSourcingItemId(CatalogAddReqMsdsInputBean input, Connection conn) throws BaseException {
		if (input.isSourcing()) {
			String new_material = "q.insert_date < m.insert_date and m.verified = 'N'";
			String matl_created_item = "cqi.item_id";
			String matl_found_in_system_item = "nvl(cqi.sourcing_item_id, cqi.item_id)";
			String sourcing_item = "(case when " + new_material + " then " + matl_created_item + " else " + matl_found_in_system_item + " end)";

			String matl_created_price = "cva.unit_price";
			String matl_found_in_system_price = "nvl(cva.sourcing_unit_price, cva.unit_price)";
			String sourcing_price = "(case when " + new_material + " then " + matl_created_price + " else " + matl_found_in_system_price + " end)";

			String query = "update catalog_queue set (item_id, unit_price) = (select " + sourcing_item + " item, " + sourcing_price + " price"
					+ " from global.material m, catalog_add_item_qc caiq, catalog_queue q, catalog_vendor_assignment cva, catalog_queue_item cqi"
					+ " where q.q_id = " + input.getqId()
					+ " and q.catalog_add_request_id = caiq.request_id"
					+ " and q.catalog_add_item_id = caiq.catalog_add_item_id"
					+ " and q.catalog_vendor_assignment_id = cva.catalog_vendor_assignment_id"
					+ " and cva.catalog_queue_item_id = cqi.catalog_queue_item_id"
					+ " and caiq.material_id = m.material_id)"
					+ " where q_id = " + input.getqId();

			factory.deleteInsertUpdate(query, conn);
		}
	}

	private void updateMaterialSourcingFlag(CatalogAddReqMsdsInputBean input, Connection conn) throws BaseException {
		String query = "update catalog_add_item_qc caiq"
				+ " set material_id_sourced = decode(caiq.material_id_sourced, 'N', 'Y', 'X', 'X', 'Y', 'Y', 'N')"
				+ " where (caiq.request_id, catalog_add_item_id) = ("
				+ "select cq.catalog_add_request_id, cq.catalog_add_item_id"
				+ " from catalog_queue cq where q_id = " + input.getqId() + ")";

		factory.deleteInsertUpdate(query, conn);
	}

	private void determineAuthoringType(CatalogAddReqMsdsInputBean input, MsdsIndexingBean msds, Connection conn) throws BaseException {
		if (input.isAuthoring()) {
			String authoringItem = "cqi.item_id";
			String authoringUnitPrice = "cva.unit_price";
			if ( ! msds.isAuthoringTypeAuthoring()) {
				authoringItem = "nvl(cqi.sourcing_item_id, cqi.item_id)";
				authoringUnitPrice = "nvl(cva.sourcing_unit_price, cva.unit_price)";
			}

			String query = "update catalog_queue set (item_id, unit_price) = (select " + authoringItem + " item, " + authoringUnitPrice + " price"
					+ " from catalog_queue q, catalog_vendor_assignment cva, catalog_queue_item cqi"
					+ " where q.q_id = " + input.getqId()
					+ " and q.catalog_vendor_assignment_id = cva.catalog_vendor_assignment_id"
					+ " and cva.catalog_queue_item_id = cqi.catalog_queue_item_id)"
					+ " where q_id = " + input.getqId();

			factory.deleteInsertUpdate(query, conn);
		}
	}

	public Date getNewRevisionDate(String materialId, Date revisionDate, String authorId) throws BaseException {
		Date newDate = revisionDate;
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		Connection conn = dbManager.getConnection();
		try {
			newDate = getNewRevisionDate(materialId,revisionDate,authorId,factory,conn);
		} catch(Exception e){
			log.error(e);
		} finally {
			dbManager.returnConnection(conn);
		}
		return newDate;
	}

	public Date getNewRevisionDate(String materialId, Date revisionDate, String authorId, GenericProcedureFactory factory, Connection conn) throws BaseException{
		Date newDate = revisionDate;
		if (revisionDate == null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.AM_PM, Calendar.AM);
			revisionDate = cal.getTime();
		}

		Vector inArgs = new Vector();
		inArgs.add(materialId);
		inArgs.add(authorId);
		//inArgs.add(new Timestamp(revisionDate.getTime()));
		inArgs.add(revisionDate);

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		Vector v = (Vector) factory.doProcedure(conn,"pkg_item_consolidation.p_get_rev_date", inArgs, outArgs);
		try{
			newDate = DateHandler.getDateFromString("MM-dd-yy HH:mm:ss a", (String) v.get(0));
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return newDate;
	}

	public QualityCheckOriginalHeaderViewBean getQcOriginalHeader(CatalogAddReqMsdsInputBean input) throws BaseException {
		StringBuilder detailquery = new StringBuilder("");
		detailquery.append("select car.MESSAGE_TO_APPROVERS,car.PART_DESCRIPTION,car.request_id,car.CAT_PART_NO,");
		detailquery.append("cai.VENDOR_CONTACT_NAME,cai.VENDOR_CONTACT_EMAIL,cai.VENDOR_CONTACT_PHONE,cai.VENDOR_CONTACT_FAX,cai.SUGGESTED_VENDOR, ");
		detailquery.append("car.company_id, car.catalog_id, car.SUBMIT_DATE");
		detailquery.append(",fx_personnel_id_to_name(car.requestor) requestor_full_name,fx_personnel_id_to_phone(car.requestor) requestor_phone");
		detailquery.append(",fx_personnel_id_to_email(car.requestor) requestor_email,car.engineering_evaluation,fx_company_name(car.company_id) company_name");
		detailquery.append(",fx_catalog_desc(car.catalog_company_id,car.catalog_id) catalog_name");
		detailquery.append(" from customer.catalog_add_request_new car,customer.catalog_add_item cai");
		if (input.hasqId()) {
			detailquery.append(", catalog_queue q")
					.append(" where car.request_id = cai.request_id")
					.append(" and cai.request_id = q.catalog_add_request_id")
					.append(" and cai.line_item = q.line_item")
					.append(" and cai.part_id = 1")
					.append(" and q.q_id = ").append(input.getqId());
		}
		else {
			detailquery.append(" where car.request_id = cai.request_id and cai.line_item = ")
					.append(input.getLineItem()).append(" and cai.part_id = 1 and ")
					.append("car.request_id = ").append(input.getRequestId());
		}

		QualityCheckOriginalHeaderViewBean bean = null;
		try {
			if ("TCM_OPS".equals(this.getClient())) {
				Collection<QualityCheckOriginalHeaderViewBean> beanColl = factory.setBean(new QualityCheckOriginalHeaderViewBean()).selectQuery(detailquery.toString());
				Iterator<QualityCheckOriginalHeaderViewBean> it = beanColl.iterator();
				while (it.hasNext()) {
					bean = it.next();
					break;
				}
			}
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		return bean;
	}

	public Collection<QualityCheckOriginalViewBean> getQcOriginalInfo(CatalogAddReqMsdsInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select distinct i.REQUEST_ID,i.MANUFACTURER,i.MATERIAL_ID,i.PART_SIZE,i.SIZE_UNIT,i.PKG_STYLE,");
		query.append("i.CASE_QTY,i.NETWT,i.NETWT_UNIT,i.COMPONENTS_PER_ITEM,i.SAMPLE_ONLY,i.LABEL_COLOR,i.PART_ID");
		//if Wesco master data team then display user's original data, otherwise, display cleaned up data
		if ("TCM_OPS".equals(this.getClient()))
			query.append(",i.MATERIAL_DESC,i.MFG_CATALOG_ID,i.DIMENSION,i.GRADE,i.MFG_TRADE_NAME");
		else
			query.append(",i.postscreen_material_desc MATERIAL_DESC,i.postscreen_mfg_catalog_id MFG_CATALOG_ID,i.postscreen_dimension DIMENSION,i.postscreen_grade GRADE,i.postscreen_mfg_trade_name MFG_TRADE_NAME");
		query.append(" from customer.catalog_add_item_orig i");
		if (input.hasqId()) {
			query.append(", catalog_queue q")
					.append(" where i.request_id = q.catalog_add_request_id")
					.append(" and i.catalog_add_item_id = q.catalog_add_item_id")
					.append(" and i.line_item = q.line_item")
					.append(" and q.q_id = ").append(input.getqId());
		}
		else {
			query.append(" where i.request_id = ").append(input.getRequestId())
					.append(" and i.line_item = ").append(input.getLineItem());
		}
		query.append(" order by i.part_id");

		return factory.setBean(new QualityCheckOriginalViewBean()).selectQuery(query.toString());
	}

	public String getNotRequiredContent(String localeCode, String sdsRequired) throws BaseException {
		String content = "";
		if ("N".equals(sdsRequired)) {
			String query = "select sds_not_required_path from vv_locale where locale_code = "
					+ SqlHandler.delimitString(localeCode);

			content = factory.selectSingleValue(query);
		}
		return content;
	}

	public boolean isRequestHasUnapprovedQueue(CatalogAddReqMsdsInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("select count(*) from catalog_queue");
		query.append(" where catalog_add_request_id = ").append(input.getRequestId());
		query.append(" and task = ").append(SqlHandler.delimitString(input.getCatalogQueueRowTask()));
		query.append(" and status <> 'Approved'");

		String count = factory.selectSingleValue(query.toString());

		if (StringHandler.isBlankString(count) || Integer.parseInt(count) == 0)
			return false;
		else
			return true;
	}
	
	public Collection<VvLocaleBean> getFacilityLocales(CatalogQueueBean input) throws BaseException {
		try {
			return workQueueItem.possibleAlternateLocales(input);
		} catch(NullPointerException e) {
			return Collections.emptyList();
		}
	}
	
	public void overrideWorkQueueItemLocale(CatalogAddReqMsdsInputBean input) throws BaseException {
		workQueueItem.changeQueueLocale(input.getqId(), input.getCatalogQueueRowTask(), input.getLocaleOverride());
		workQueueItem.changeItemLocaleByQId(input.getqId(), input.getLocaleOverride());
	}

} //end of class