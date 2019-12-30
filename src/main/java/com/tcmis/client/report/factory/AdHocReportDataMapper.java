package com.tcmis.client.report.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.tcmis.client.report.beans.BaseFieldViewBean;
import com.tcmis.client.report.beans.PublishTemplateInputBean;
import com.tcmis.client.report.beans.UsageReportTemplateBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.sql.builder.CoalesceDecorator;
import com.tcmis.common.util.sql.builder.CountDecorator;
import com.tcmis.common.util.sql.builder.FunctionDecorator;
import com.tcmis.common.util.sql.builder.QueryBuilder;
import com.tcmis.common.util.sql.builder.QueryBuilderFactory;
import com.tcmis.common.util.sql.builder.QueryTable;
import com.tcmis.common.util.sql.builder.StandardColumn;
import com.tcmis.common.util.sql.builder.ValueColumn;

public class AdHocReportDataMapper extends GenericSqlFactory implements IAdHocReportDataMapper {
	
	private static final String separator = "|";
	private static final String NO_RESTRICTION = "NO_RESTRICTION";
	private static final String RESTRICTED = "RESTRICTED";
	private static final String UNKNOWN = "UNKNOWN";
	
	private static final String THRESHOLD = "THRESHOLD";
	private static final String YES = "YES";
	private static final String LBS = "LBS";
	
	private static final String START = "START";
	private static final String STOP = "STOP";

	public AdHocReportDataMapper(DbManager dbManager) {
		super(dbManager);
	}
	
	// query found in customer.pkg_ad_hoc_report.p_save_template
	private BigDecimal nextTemplateId(Connection conn) throws BaseException, SQLException {
		String query = QueryBuilderFactory.generateBuilder(conn)
				.select(new StandardColumn("ad_hoc_template_id_seq.nextval"))
				.from(QueryTable.defaultTable())
				.toString();
		return new BigDecimal(this.selectSingleValue(query, conn));
	}
	
	// cursor customer.pkg_ad_hoc_report.p_save_template.c_locale
	private Optional<BigDecimal> selectLocalizedTemplate(String localeCode, BigDecimal templateId, Connection conn) throws BaseException, SQLException {
		QueryTable table = new QueryTable("ad_hoc_template_locale");
		String query = QueryBuilderFactory.generateBuilder(conn)
				.select(new StandardColumn("template_id"))
				.from(table)
				.whereStringEquals("locale_code", localeCode)
				.whereNumberEquals("template_id", templateId)
				.toString();
				
		String sqlResult = selectSingleValue(query, conn);
		BigDecimal returnValue = StringHandler.isBlankString(sqlResult)?null:new BigDecimal(sqlResult);
		return Optional.ofNullable(returnValue);
	}
	
	// security.p_security
	private boolean isAllowed(String companyId, Connection conn) throws BaseException, SQLException {
		String query = QueryBuilderFactory.generateBuilder(conn)
				.select(new FunctionDecorator("SYS_CONTEXT", "SYS_CONTEXT", 
						new ValueColumn("CTX_COMPANY"), new ValueColumn("CTX_CUSTOMER_COMPANY")))
				.from(QueryTable.defaultTable())
				.toString();
		
		// Will be one of:
		// NO_RESTRICTION
		// RESTRICTED
		// UNKNOWN
		// = '<company_id>'
		String restriction = selectSingleValue(query, conn);
		String condition = SqlHandler.delimitString(NO_RESTRICTION) + " = ";
		switch(restriction) {
		case NO_RESTRICTION: 
		case RESTRICTED:
		case UNKNOWN:
			condition += SqlHandler.delimitString(restriction);
			break;
		default:
			condition = SqlHandler.delimitString(companyId) + restriction;
		}
		
		// cannot completely use QueryBuilder here because of the way `condition` is built
		QueryBuilder builder = QueryBuilderFactory.generateBuilder(conn);
		String countQuery = builder
				.selectAll(new CountDecorator())
				.from(QueryTable.defaultTable())
				.toString() + " WHERE " + condition;

		return !"0".equals(selectSingleValue(countQuery, conn));
	}

	private void upsertAdHocTemplate(UsageReportTemplateBean bean, Connection conn) throws SQLException, BaseException {
		String[] reportFields = bean.getBaseFieldId() != null
				? StringHandler.stripLast(bean.getBaseFieldId(), separator).split("\\"+separator)
				: bean.getReportFieldList() != null ? bean.getReportFieldList() : new String[0];
		bean.setReportFieldList(reportFields);
		
		QueryBuilder upsertBuilder = QueryBuilderFactory.generateBuilder(conn)
				.update(new QueryTable("ad_hoc_template_view")).setString("company_id", bean.getCompanyId())
				.setString("report_type", bean.getReportType()).setNumber("personnel_id", bean.getPersonnelId())
				.setString("template_name", bean.getTemplateName())
				.setString("list", Arrays.stream(bean.getReportFieldList()).collect(Collectors.joining(separator)))
				.setString("sep", separator).setDate("start_date", bean.getBeginDateJsp())
				.setDate("stop_date", bean.getEndDateJsp()).setString("list_id", bean.getListId())
				.setNull("addl_constraint").setNull("addl_from").setString("cas_number", bean.getCasNumber())
				.setString("facility_id", bean.getFacilityId()).setString("application", bean.getApplication())
				.setString("cat_part_no", bean.getPartNumber())
				.setString("cat_part_no_search_type", bean.getPartNumberCriteria())
				.when(!"All Docks".equalsIgnoreCase(bean.getDockId()),
						query -> query.setString("dock_location_id", bean.getDockId()))
				.when(!"All Delivery Points".equalsIgnoreCase(bean.getDeliveryPoint()),
						query -> query.setString("delivery_point", bean.getDeliveryPoint()))
				.setString("category_id", bean.getMaterialCategory()).setString("manufacturer", bean.getManufacturer())
				.setString("manufacturer_search_type", bean.getManufacturerCriteria())
				.setString("include_column_alias", "N").setString("part_location", bean.getLocation())
				.setString("part_category", bean.getCategory()).setNull("accumulation_point").setNull("vendor_id")
				.setNull("vendor_profile_id").setNull("management_option_list").setNull("exclude_hub_waste")
				.setString("query_type", bean.getQueryType())
				.setString("chemical_list", StringHandler.stripLast(bean.getChemicalFieldListId(), separator))
				.setString("chemical_list_format", StringHandler.stripLast(bean.getListFormat(), separator))
				.setString("output_mode", bean.getReportGenerationType()).setString("report_name", bean.getReportName())
				.setNull("management_option_desc").setNull("waste_profile_desc")
				.setString("reporting_entity_id", bean.getReportingEntityId())
				.setString("user_group_id", bean.getUserGroupId()).setString("owner", bean.getOwner())
				.setString("status", bean.getStatus()).setNumber("last_modified_by", bean.getLastModifiedBy())
				.setDate("last_modified_on", bean.getLastModifiedOn(), true)
				.setString("template_description", bean.getTemplateDescription())
				.setString("url_page_arg", bean.getUrlPageArg()).setNull("account_sys_id").setNull("charge_type")
				.setNull("charge_number_1").setNull("charge_number_2").setNull("search_by").setNull("search_text")
				.setNull("invoice").setNull("invoice_period").setNull("report_field").setNull("invoice_start_date")
				.setNull("invoice_end_date").setNull("requestor").setNull("requestor_name").setNull("commodity")
				.setNull("date_delivered_group_by").setNull("source_hub").setNull("item_type").setNull("search_type")
				.setNull("output_file_type").setNull("uom").setNull("charge_number_3").setNull("charge_number_4")
				.setNull("po_number").setString("area_id", bean.getAreaId())
				.setString("building_id", bean.getBuildingId()).setString("floor_id", bean.getFloorId())
				.setString("room_id", bean.getRoomId()).setString("facility_group_id", bean.getFacilityGroupId())
				.setNull("division_id").setNull("customer_part_no").setNull("shipping_reference")
				.setNull("customer_invoice_no").setNull("invoice_number").setNull("search_parameter")
				.setNull("search_value").setNull("msds_match_type").setNull("mfg_id").setNull("physical_state")
				.setNull("msds_ph").setNull("msds_ph_compare").setNull("msds_flash_pt").setNull("msds_fp_compare")
				.setNull("msds_fp_unit").setNull("vapor_pressure").setNull("msds_vp_compare").setNull("msds_vp_unit")
				.setNull("voc").setNull("voc_compare").setNull("voc_unit").setNull("solids_percent")
				.setNull("msds_sp_compare").setNull("nfpa_health").setNull("nfpa_health_comp").setNull("nfpa_flam")
				.setNull("nfpa_flam_comp").setNull("nfpa_reactivity").setNull("nfpa_react_comp")
				.setNull("specific_hazard").setNull("hmis_health").setNull("hmis_health_comp").setNull("hmis_flam")
				.setNull("hmis_flam_comp").setNull("hmis_reactivity").setNull("hmis_react_comp")
				.setNull("personal_prot").setNull("constraint_seperator").setNull("full_db_search")
				.setNull("approved_search").setNull("kit_only").setNull("stocked").setNull("voc_lwes")
				.setNull("voc_lwes_compare").setNull("voc_lwes_unit").setString("dept_id", bean.getDeptId())
				.setString("material_category_id", bean.getMaterialCategoryId())
				.setString("material_subcategory_id", bean.getMaterialSubcategoryId())
				.setString("catalog_company_id", bean.getCatalogCompanyId())
				.setString("catalog_id", bean.getCatalogId()).setString("header", bean.getHeader())
				.setString("footer", bean.getFooter()).setNull("composition_percent_operator")
				.setNull("composition_percent_limit").setNull("amount_limit_operator").setNull("amount_limit")
				.setNull("amount_limit_unit").setNull("trace").setNull("trade_secret")
				.when("true".equalsIgnoreCase(bean.getIncludeOpenOrders()) || "Y".equals(bean.getIncludeOpenOrders()),
						query -> query.setString("include_open_orders", "Y"),
						query -> query.setString("include_open_orders", "N"))
				.when("true".equalsIgnoreCase(bean.getGateKeeping()) || "Y".contentEquals(bean.getGateKeeping()),
						query -> query.setString("gatekeeping", "Y"), query -> query.setString("gatekeeping", "N"))
				.setString("report_period_type", bean.getReportPeriodType())
				.setString("report_period_day", bean.getReportPeriodDay())
				.setString("email_subject", bean.getEmailSubject()).setString("email_message", bean.getEmailMessage())
				.setString("email_user_group_id", bean.getEmailUserGroupId())
				.setString("email_subject_neg", bean.getEmailSubjectNeg())
				.setString("email_message_neg", bean.getEmailMessageNeg())
				.setString("email_user_group_id_neg", bean.getEmailUserGroupIdNeg())
				.setString("over_flam_ctrl_zn_limit", bean.getOverFlamCtrlZnLimit())
				.setString("over_flam_ctrl_zn_lmt_percent", bean.getOverFlamCtrlZnLmtPercent())
				.setString("flammability_control_zone_id", bean.getFlammabilityControlZoneId())
				.setString("voc_zone_id", bean.getVocZoneId()).setString("flammable", bean.getFlammable())
				.setNull("fp_test_detect").setNull("fp_test").setNull("fp_test_unit").setNull("nfpa_test_detect")
				.setNull("nfpa_test").setNull("hmis_test_detect").setNull("hmis_test").setNull("positive_output")
				.setNull("negative_output").setNull("null_output")
				.whereStringEquals("template_id", bean.getTemplateId());

		if (this.deleteInsertUpdate(upsertBuilder.toString(), conn) == 0) {
			upsertBuilder.setNumber("template_id", nextTemplateId(conn));
			this.deleteInsertUpdate(upsertBuilder.toInsertString(), conn);
			bean.setTemplateId(upsertBuilder.getUpsertValue("template_id"));
		}
	}
	
	// customer.pkg_ad_hoc_report.p_save_template
	@Override
	public String saveTemplate(UsageReportTemplateBean bean, PersonnelBean personnelBean, String client) throws BaseException, SQLException {
		Connection conn = getDbManager().getConnection();

		try {
			if (StringHandler.isBlankString(bean.getCompanyId())) {
				bean.setCompanyId(personnelBean.getCompanyId());
			}

			if ( ! isAllowed(bean.getCompanyId(), conn)) {
				throw new BaseException("Company security violation");
			}
			conn.setAutoCommit(false);
			bean.setPersonnelId(personnelBean.getPersonnelIdBigDecimal());

			if ("list".equalsIgnoreCase(bean.getGridType())) {
				bean.setListId(bean.getGridSubmit());
			} else if ("cas".equalsIgnoreCase(bean.getGridType())) {
				bean.setCasNumber(bean.getGridSubmit());
			}

			String gridTypeReportOption = (!StringHandler.isBlankString(bean.getGridSubmit()))
					? "cas".equalsIgnoreCase(bean.getGridType()) ? "singleChemical" : "all"
					: "all";

			bean.setQueryType(gridTypeReportOption);

			if ("SWA".equals(client)) {
				bean.setPartCategory(gridTypeReportOption);
			} else {
				bean.setPartCategory("");
				bean.setLocation("");
			}

			bean.setLastModifiedBy(personnelBean.getPersonnelIdBigDecimal());
			bean.setLastModifiedOn(null);

			Map<String, String> reportPeriodDates = new HashMap<>();
			reportPeriodDates.put("specificdates", null);
			reportPeriodDates.put("numberofdays", bean.getNumOfDays());
			reportPeriodDates.put("dayofweek", bean.getSelDayOfWeek());
			reportPeriodDates.put("dayofmonth", bean.getSelDayOfMonth());
			reportPeriodDates.put("dayofyear", bean.getSelDayOfYear());

			bean.setReportPeriodDay(reportPeriodDates.getOrDefault(
					StringHandler.emptyIfNull(bean.getReportPeriodType()).toLowerCase(), bean.getReportPeriodDay()));

			if (!bean.getIsMatCatFX()) {
				bean.setMaterialCategoryId("");
				bean.setMaterialSubcategoryId("");
				bean.setCatalogCompanyId("");
				bean.setCatalogId("");
			}

			upsertAdHocTemplate(bean, conn);

			String localeCode = personnelBean.getLocale().toString();
			if ( ! "en_US".equals(localeCode)) {
				Optional<BigDecimal> localTemplateId = selectLocalizedTemplate(localeCode, new BigDecimal(bean.getTemplateId()), conn);
				
				if (localTemplateId.isPresent()) {
					QueryBuilder localeBuilder = QueryBuilderFactory.generateBuilder(conn)
							.update(new QueryTable("ad_hoc_template_locale"))
							.setString("template_name", bean.getTemplateName())
							.setString("report_name", bean.getReportName())
							.setString("template_description", bean.getTemplateDescription())
							.whereStringEquals("locale_code", localeCode)
							.whereNumberEquals("template_id", new BigDecimal(bean.getTemplateId()));
					
					if (this.deleteInsertUpdate(localeBuilder.toString(), conn) == 0) {
						localeBuilder.setString("template_id", bean.getTemplateId())
								.setString("locale_code", localeCode);
						this.deleteInsertUpdate(localeBuilder.toInsertString(), conn);
					}
				}
			}
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			throw new BaseException(e);
		} finally {
			conn.setAutoCommit(true);
			getDbManager().returnConnection(conn);
		}

		return "OK";
	}

	// customer.pkg_ad_hoc_report.p_share_template.c_user_company
	private String getCompanyUser(String companyId, BigDecimal personnelId, Connection conn)
			throws BaseException, SQLException {
		String query = QueryBuilderFactory.generateBuilder(conn).select(new StandardColumn("personnel_id"))
				.from(new QueryTable("user_company")).whereStringEquals("company_id", companyId, false)
				.whereNumberEquals("personnel_id", personnelId).toString();

		return selectSingleValue(query, conn);
	}

	// customer.pkg_ad_hoc_report.p_share_template.c_ad_hoc_template
	private Optional<UsageReportTemplateBean> getTemplate(BigDecimal templateId, Connection conn)
			throws BaseException, SQLException {
		QueryTable ad_hoc_template = new QueryTable("ad_hoc_template");
		String query = QueryBuilderFactory.generateBuilder(conn)
				.select(ad_hoc_template.col("template_id", "template_id_numeric"))
				.select(ad_hoc_template.col("owner"))
				.select(ad_hoc_template.col("company_id")).from(ad_hoc_template)
				.whereNumberEquals("template_id", templateId).toString();
		return this.setBean(new UsageReportTemplateBean()).selectQuery(query, conn).stream().findFirst();
	}

	// customer.pkg_ad_hoc_report.p_share_template.c_ad_hoc_template_personnel
	private String getTemplatePersonnel(BigDecimal templateId, String companyId, BigDecimal personnelId,
			Connection conn) throws BaseException, SQLException {
		String query = QueryBuilderFactory.generateBuilder(conn)
				.select(new StandardColumn("template_id"))
				.from(new QueryTable("ad_hoc_template_personnel"))
				.whereNumberEquals("template_id", templateId)
				.whereNumberEquals("personnel_id", personnelId)
				.whereStringEquals("company_id", companyId, false)
				.toString();

		return selectSingleValue(query, conn);
	}

	// customer.pkg_ad_hoc_report.p_share_template
	@Override
	public String shareTemplate(BigDecimal templateId, String companyId, BigDecimal personnelId)
			throws BaseException, SQLException {
		String result = "Ok";

		Connection conn = getDbManager().getConnection();

		try {
			if (!isAllowed(companyId, conn)) {
				throw new BaseException("Company security violation");
			}
			Optional<UsageReportTemplateBean> bean = getTemplate(templateId, conn);
			if (!bean.isPresent()) {
				throw new BaseException(templateId + " is not a valid template ID.");
			} else if (!StringUtils.equals(bean.map(b -> b.getCompanyId()).orElse(""), companyId)) {
				throw new BaseException("The template company ID (" + bean.map(b -> b.getCompanyId()).get()
						+ ") does not match the requested company ID (" + companyId + ").");
			} else if (!StringUtils.equals(bean.map(b -> b.getOwner()).orElse(""), "PERSONNEL_ID")) {
				throw new BaseException("Only templates that are owned by individuals can be shared with other users.");
			}

			if (StringHandler.isBlankString(getCompanyUser(companyId, personnelId, conn))) {
				throw new BaseException(
						"Personnel ID " + personnelId.toString() + " is not a user for company " + companyId + ".");
			}

			if (!StringHandler.isBlankString(getTemplatePersonnel(templateId, companyId, personnelId, conn))) {
				throw new BaseException("This user already has access to this template.");
			}

			String insertStmt = QueryBuilderFactory.generateBuilder(conn).insert()
					.into(new QueryTable("ad_hoc_template_personnel"))
					.setNumber("template_id", templateId)
					.setString("company_id", companyId)
					.setNumber("personnel_id", personnelId)
					.setString("status", "A")
					.toString();

			deleteInsertUpdate(insertStmt, conn);
		} finally {
			getDbManager().returnConnection(conn);
		}

		return result;
	}

	// customer.pkg_ad_hoc_report.p_publish_template.c_ad_hoc_template
	private Optional<UsageReportTemplateBean> getFullTemplate(BigDecimal templateId, Connection conn)
			throws SQLException, BaseException {
		QueryTable t = new QueryTable("ad_hoc_template", "t");
		String query = QueryBuilderFactory.generateBuilder(conn).select(t.col("account_sys_id")).select(t.col("template_id","report_template_id"))
				.select(t.col("accumulation_point")).select(t.col("addl_constraint")).select(t.col("addl_from"))
				.select(t.col("application")).select(t.col("cas_number"))
				.select(t.col("category_id", "material_category")).select(t.col("cat_part_no", "part_number"))
				.select(t.col("cat_part_no_search_type", "part_number_criteria")).select(t.col("charge_number_1"))
				.select(t.col("charge_number_2")).select(t.col("charge_type"))
				.select(t.col("chemical_list", "chemical_field_list_id"))
				.select(t.col("chemical_list_format", "list_format")).select(t.col("commodity"))
				.select(t.col("company_id")).select(t.col("date_delivered_group_by")).select(t.col("delivery_point"))
				.select(t.col("dock_location_id")).select(t.col("exclude_hub_waste")).select(t.col("facility_id"))
				.select(t.col("include_column_alias")).select(t.col("invoice")).select(t.col("invoice_start_date"))
				.select(t.col("invoice_end_date")).select(t.col("invoice_period")).select(t.col("item_type"))
				.select(t.col("last_modified_by")).select(t.col("last_modified_on"))
				.select(t.col("list", "base_field_id")).select(t.col("list_id")).select(t.col("management_option_desc"))
				.select(t.col("management_option_list")).select(t.col("manufacturer"))
				.select(t.col("manufacturer_search_type", "manufacturer_criteria")).select(t.col("output_file_type"))
				.select(t.col("output_mode", "report_generation_type")).select(t.col("owner"))
				.select(t.col("part_category")).select(t.col("part_location")).select(t.col("personnel_id"))
				.select(t.col("query_type")).select(t.col("reporting_entity_id")).select(t.col("report_field"))
				.select(t.col("report_name")).select(t.col("report_type")).select(t.col("requestor"))
				.select(t.col("requestor_name")).select(t.col("search_by")).select(t.col("search_text"))
				.select(t.col("search_type")).select(t.col("sep")).select(t.col("source_hub"))
				.select(t.col("start_date", "beginDateJsp")).select(t.col("status"))
				.select(t.col("stop_date", "end_date_jsp")).select(t.col("template_description"))
				.select(t.col("template_name")).select(t.col("uom")).select(t.col("url_page_arg"))
				.select(t.col("user_group_id")).select(t.col("vendor_id")).select(t.col("vendor_profile_id"))
				.select(t.col("waste_profile_desc")).select(t.col("charge_number_3")).select(t.col("charge_number_4"))
				.select(t.col("po_number")).select(t.col("area_id")).select(t.col("building_id"))
				.select(t.col("room_id")).select(t.col("floor_id")).select(t.col("facility_group_id"))
				.select(t.col("division_id")).select(t.col("customer_part_no")).select(t.col("shipping_reference"))
				.select(t.col("customer_invoice_no")).select(t.col("invoice_number")).select(t.col("search_parameter"))
				.select(t.col("search_value")).select(t.col("msds_match_type")).select(t.col("mfg_id"))
				.select(t.col("physical_state")).select(t.col("msds_ph")).select(t.col("msds_ph_compare"))
				.select(t.col("msds_flash_pt")).select(t.col("msds_fp_compare")).select(t.col("msds_fp_unit"))
				.select(t.col("vapor_pressure")).select(t.col("msds_vp_compare")).select(t.col("msds_vp_unit"))
				.select(t.col("voc")).select(t.col("voc_compare")).select(t.col("voc_unit"))
				.select(t.col("solids_percent")).select(t.col("msds_sp_compare")).select(t.col("nfpa_health"))
				.select(t.col("nfpa_health_comp")).select(t.col("nfpa_flam")).select(t.col("nfpa_flam_comp"))
				.select(t.col("nfpa_reactivity")).select(t.col("nfpa_react_comp")).select(t.col("specific_hazard"))
				.select(t.col("hmis_health")).select(t.col("hmis_health_comp")).select(t.col("hmis_flam"))
				.select(t.col("hmis_flam_comp")).select(t.col("hmis_reactivity")).select(t.col("hmis_react_comp"))
				.select(t.col("personal_prot")).select(t.col("constraint_seperator")).select(t.col("full_db_search"))
				.select(t.col("approved_search")).select(t.col("kit_only")).select(t.col("stocked"))
				.select(t.col("voc_lwes")).select(t.col("voc_lwes_compare")).select(t.col("voc_lwes_unit"))
				.select(t.col("dept_id")).select(t.col("material_category_id")).select(t.col("material_subcategory_id"))
				.select(t.col("composition_percent_operator")).select(t.col("composition_percent_limit"))
				.select(t.col("amount_limit_operator")).select(t.col("amount_limit")).select(t.col("amount_limit_unit"))
				.select(t.col("trace")).select(t.col("trade_secret")).select(t.col("include_open_orders"))
				.select(t.col("gatekeeping")).select(t.col("report_period_type")).select(t.col("report_period_day"))
				.select(t.col("email_subject")).select(t.col("email_message")).select(t.col("email_user_group_id"))
				.select(t.col("email_subject_neg")).select(t.col("email_message_neg"))
				.select(t.col("email_user_group_id_neg")).select(t.col("over_flam_ctrl_zn_limit"))
				.select(t.col("over_flam_ctrl_zn_lmt_percent")).select(t.col("flammability_control_zone_id"))
				.select(t.col("voc_zone_id")).select(t.col("catalog_company_id")).select(t.col("catalog_id"))
				.select(t.col("header")).select(t.col("footer")).select(t.col("flammable"))
				.select(t.col("fp_test_detect")).select(t.col("fp_test")).select(t.col("fp_test_unit"))
				.select(t.col("nfpa_test_detect")).select(t.col("nfpa_test")).select(t.col("hmis_test_detect"))
				.select(t.col("hmis_test")).select(t.col("positive_output")).select(t.col("negative_output"))
				.select(t.col("null_output")).from(t).whereNumberEquals("template_id", templateId).toString();

		return this.setBean(new UsageReportTemplateBean()).selectQuery(query, conn).stream().findFirst();
	}

	// customer.pkg_ad_hoc_report.p_publish_template
	@Override
	public String publishTemplateToUserGroupAndCompany(PublishTemplateInputBean inputBean, String owner,
			String userGroupId, PersonnelBean personnelBean, String client) throws BaseException, SQLException {
		String result = "OK";

		Connection conn = getDbManager().getConnection();

		try {
			result = getFullTemplate(inputBean.getTemplateId(), conn)
				.map(template -> {
					template.setOwner(owner);
					template.setUserGroupId(userGroupId);
					template.setTemplateId(template.getReportTemplateId().toString());
					try {
						upsertAdHocTemplate(template, conn);
						return "OK";
					} catch (Exception e) {
						return e.getMessage();
					}
				}).orElse("Template ID "+inputBean.getTemplateId().toString()+" is not valid");
		} finally {
			getDbManager().returnConnection(conn);
		}
		return result;
	}

	@Override
	public String inactivateTemplate(BigDecimal templateId, String companyId) throws BaseException, SQLException {
		Connection conn = getDbManager().getConnection();

		try {
			if ( ! isAllowed(companyId, conn)) {
				throw new BaseException("Company security violation");
			}
			
			String query = QueryBuilderFactory.generateBuilder(conn)
					.update(new QueryTable("ad_hoc_template_view"))
					.setString("status", "I")
					.whereNumberEquals("template_id", templateId)
					.toString();
			
			deleteInsertUpdate(query, conn);
		} finally {
			getDbManager().returnConnection(conn);
		}
		
		return "Ok";
	}

	@Override
	public String unshareTemplate(BigDecimal templateId, String companyId, BigDecimal userId) throws BaseException, SQLException {
		Connection conn = getDbManager().getConnection();

		try {
			String query = QueryBuilderFactory.generateBuilder(conn)
					.delete().from(new QueryTable("ad_hoc_template_personnel"))
					.whereNumberEquals("template_id", templateId)
					.whereNumberEquals("personnel_id", userId)
					.whereStringEquals("company_id", companyId, false)
					.toString();
			
			deleteInsertUpdate(query, conn);
		} finally {
			getDbManager().returnConnection(conn);
		}
		return "Ok";
	}

	@Override
	public String deleteTemplate(BigDecimal templateId, String companyId) throws BaseException, SQLException {
		Connection conn = getDbManager().getConnection();
		try {
			if ( ! isAllowed(companyId, conn)) {
				throw new BaseException("Company security violation");
			}
			
			String query = QueryBuilderFactory.generateBuilder(conn)
					.delete().from(new QueryTable("ad_hoc_template_personnel"))
					.whereNumberEquals("template_id", templateId)
					.toString();
			
			deleteInsertUpdate(query, conn);
			
			query = QueryBuilderFactory.generateBuilder(conn)
					.delete().from(new QueryTable("ad_hoc_template_view"))
					.whereNumberEquals("template_id", templateId)
					.toString();
			
			deleteInsertUpdate(query, conn);
		} finally {
			getDbManager().returnConnection(conn);
		}
		return "Ok";
	}
	
	// customer.pkg_ad_hoc_report.FX_COLUMN_PARAM
	@Override
	public List<BaseFieldViewBean> columnParams(BigDecimal[] reportFieldList, String[] chemList, String[] chemListFormat, String reportType, String companyId, BigDecimal displayLength, Connection conn) throws BaseException, IllegalStateException, SQLException {
		List<BaseFieldViewBean> baseColumns = new ArrayList<>();
		
		baseColumns.addAll(selectBaseFields(reportType, reportFieldList, companyId, displayLength, conn));
		baseColumns.addAll(chemicalListColumn(chemList, chemListFormat, companyId, displayLength, conn));
		
		return baseColumns;
	}
	
	// customer.pkg_ad_hoc_report.FX_COLUMN_PARAM.c_data
	private Collection<BaseFieldViewBean> selectBaseFields(String reportType, BigDecimal[] baseFieldIds, String companyId, BigDecimal displayLength, Connection conn) throws BaseException, IllegalStateException, SQLException {
		final QueryTable bfv = new QueryTable("base_field_view", "bfv");
		String baseFieldQuery = QueryBuilderFactory.generateBuilder(conn)
				.select(bfv.col("base_field_id"))
				.select(bfv.col("name"))
				.select(new CoalesceDecorator("display_length", bfv.col("display_length"), new ValueColumn(displayLength)))
				.select(bfv.col("hyperlink"))
				.from(bfv)
				.when(companyId != null, q -> q.whereStringEquals("company_id", companyId, false))
				.whereStringEquals("report_type", reportType, false)
				.whereNumberIn("base_field_id", Arrays.stream(baseFieldIds))
				.toString();
		
		return setBean(new BaseFieldViewBean()).selectQuery(baseFieldQuery, conn);
	}
	
	// customer.pkg_ad_hoc_report.FX_COLUMN_PARAM - Helper
	private Collection<BaseFieldViewBean> chemicalListColumn(String[] listIds, String[] listFormats, String companyId, BigDecimal displayLength, Connection conn) throws BaseException, SQLException {
		Collection<BaseFieldViewBean> baseFields = listNames(companyId, listIds, listFormats, conn);
		
		baseFields.forEach(bean -> {
			bean.setDisplayLength(displayLength);
			bean.setHyperlink("N");
			bean.setBaseFieldId(new BigDecimal(-1));
		});
		return baseFields;
	}
	
	// customer.pkg_ad_hoc_report.fx_list_name
	private Collection<BaseFieldViewBean> listNames(String companyId, String[] listIds, String[] listFormats, Connection conn) throws BaseException, SQLException {
		QueryTable c = new QueryTable("co_list", "c");
		QueryBuilder unionQuery = null;

		Function<String, String> formatIdToDisplay = id -> {
			String displayVal = "";
			switch(id) {
				case THRESHOLD: displayVal = "Threshold"; break;
				case YES: displayVal = "Yes"; break;
				case LBS: displayVal = "Pounds"; break;
				default: displayVal = "";
			}
			return displayVal;
		};
		
		for (int i = 0; i < listIds.length && i < listFormats.length; i++) {
			String listId = listIds[i];
			String listFormat = listFormats[i];
			
			QueryBuilder query = QueryBuilderFactory.generateBuilder(conn)
					.select(q -> q.concatenation("name", c.col("list_name"), 
							new ValueColumn(" ("+formatIdToDisplay.apply(listFormat)+")")))
					.from(c)
					.when(companyId != null, q -> q.whereStringEquals("company_id", companyId, false))
					.whereStringEquals("list_id", listId, false);

			unionQuery = query.unionAll(unionQuery);
		}
		
		
		return setBean(new BaseFieldViewBean()).selectQuery(unionQuery.toString(), conn);
	}

	@Override
	public void logMessage(String process, String startStop, String source, String error, BigDecimal fileSize, Connection connection) throws BaseException, SQLException {
		Connection conn = getDbManager().getConnection();

		try {
			QueryBuilder builder = QueryBuilderFactory.generateBuilder(conn)
					.update(new QueryTable("ad_hoc_report_log"));
			
			boolean start = START.equals(startStop);
			boolean stop = STOP.equals(startStop);
			switch(process) {
			case STAGE_PROCESS:
				if (start) {
					builder.setCurrentDateTime("stage_start")
							.setString("stage_source", source);
				}
				else if (stop) {
					builder.setCurrentDateTime("stage_stop")
							.setString("stage_error", error);
				}
				break;
			case QUERY_PROCESS:
				if (start) {
					builder.setCurrentDateTime("query_start")
							.setString("query_source", source);
				}
				else if (stop) {
					builder.setCurrentDateTime("query_stop")
							.setString("query_error", error);
				}
				break;
			case REPORT_PREP_PROCESS:
				if (start) {
					builder.setCurrentDateTime("report_prep_start")
							.setString("report_prep_source", source);
				}
				else if (stop) {
					builder.setCurrentDateTime("report_prep_stop")
							.setString("report_prep_error", error)
							.setNumber("file_size_bytes", fileSize);
				}
				break;
			default:
				builder = null;
			}

			if (builder != null) {
				builder.whereNumberEquals("ad_hoc_report_log_id", currentReportLogId(connection));
				deleteInsertUpdate(builder.toString(), conn);
			}
		} finally {
			getDbManager().returnConnection(conn);
		}
		return;
	}
	

	private BigDecimal currentReportLogId(Connection conn) throws BaseException, SQLException {
		String query = QueryBuilderFactory.generateBuilder(conn)
				.select(new FunctionDecorator("pkg_ad_hoc_report.fx_current_report_log_id", "report_log_id")) 
				.from(QueryTable.defaultTable())
				.toString();
		BigDecimal id = null;
		try {
			id = new BigDecimal(selectSingleValue(query, conn));
		} catch(NumberFormatException e) {
			throw new BaseException(e);
		}
		return id;
	}
}
