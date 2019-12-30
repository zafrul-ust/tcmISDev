package com.tcmis.client.catalog.process;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ApplicationUseGroupBean;
import com.tcmis.client.catalog.beans.ApprovalUserGroupBean;
import com.tcmis.client.catalog.beans.CaProcessEmissionPointBean;
import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.CatAddStatusViewBean;
import com.tcmis.client.catalog.beans.CatPartQplViewBean;
import com.tcmis.client.catalog.beans.CatalogAddFlowDownBean;
import com.tcmis.client.catalog.beans.CatalogAddHmrbBean;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.beans.CatalogAddSpecBean;
import com.tcmis.client.catalog.beans.CatalogAddStorageBean;
import com.tcmis.client.catalog.beans.CatalogAddUserGroupBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.CatalogStorageTempBean;
import com.tcmis.client.catalog.beans.CustomerMaterialNumberBean;
import com.tcmis.client.catalog.beans.FacAppEmissionPointBean;
import com.tcmis.client.catalog.beans.FacItemBean;
import com.tcmis.client.catalog.beans.MaterialCategorySubcatViewBean;
import com.tcmis.client.catalog.beans.NewChemUrlViewBean;
import com.tcmis.client.catalog.beans.VvShelfLifeBasisBean;
import com.tcmis.client.common.beans.ChemicalApproverBean;
import com.tcmis.client.common.beans.MsdsDocumentInputBean;
import com.tcmis.client.common.process.CreateCatalogAddProcess;
import com.tcmis.client.lmco.beans.VocetCoatCategoryBean;
import com.tcmis.client.lmco.beans.VocetStatusBean;
import com.tcmis.common.admin.beans.AutocompleteInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.SdsAuthoringSelectionViewBean;
import com.tcmis.internal.hub.beans.InventoryGroupDefinitionBean;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process for CatalogAddRequestProcess
 * @version 1.0
 *****************************************************************************/
public class CatalogAddRequestProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	private DbManager dbManager;
	private Connection connection = null;
	private GenericSqlFactory genericSqlFactory;
	private ResourceLibrary library = null;
	private String URL = "";
	private boolean showObsolete = false;
	private final String emissionPointsIdSeparators = "-|#^%*!$@";

	//starting views
	public static final int NEW_MATERIAL = 0;
	public static final int NEW_SIZE_PACKAGING = 1;
	public static final int NEW_PART = 2;
	public static final int NEW_WORK_AREA_APPROVAL = 3;
	public static final int MODIFY_QPL = 4;
	public static final int FADEOUT_FROM_QPL = 5;
	public static final int NEW_MSDS = 6;
	public static final int NEW_APPROVAL_CODE = 7;
	public static final int NEW_PART_APPROVAL_CODE = 8;
	public static final int SDS_AUTHORING = 9;

	public CatalogAddRequestProcess(String client) {
		super(client);
	}

	public CatalogAddRequestProcess(String client, String locale, String URL) {
		super(client, locale);
		this.URL = URL;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
		this.connection = connection;
		this.genericSqlFactory = genericSqlFactory;
	}

	public void setShowObsolete(boolean val) {
		this.showObsolete = val;
	}

	public BigDecimal sdsAuthoring(CatalogInputBean bean, Date revisionDate, PersonnelBean personnelBean, Collection<SdsAuthoringSelectionViewBean> beans) throws Exception{
		BigDecimal requestId = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
			requestId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			String tmpStartingPoint = "sdsAuthoring";

			//create new catalog_add_request_new
			bean.setCatalogAddCatalogId(bean.getCatalogId());
			bean.setCatalogAddCatalogCompanyId(bean.getCatalogCompanyId());
			createNewCatalogAddRequestNew(bean,personnelBean,requestId,tmpStartingPoint);
			//create catalog_add_item
			query = copyCatalogAddItemByMaterial(requestId,new BigDecimal(1),new BigDecimal(bean.getMaterialId()),new BigDecimal(SDS_AUTHORING),new BigDecimal(1),null);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			query = new StringBuilder("update catalog_add_item set source_revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate)).append(" where request_id = ").append(requestId);
			query.append(" and line_item = 1 and part_id = 1");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//create catalog_add_item_locale
			StringBuilder whereClause = new StringBuilder("");
			Iterator iter = beans.iterator();
			while (iter.hasNext()) {
				SdsAuthoringSelectionViewBean tmpBean = (SdsAuthoringSelectionViewBean) iter.next();
				if (whereClause.length() > 0)
					whereClause.append(",");
				whereClause.append("'").append(tmpBean.getLocaleCode()).append("'");
			}
			query = new StringBuilder("insert into catalog_add_item_locale (catalog_add_item_id,to_locale_code,sds_indexing_status,sds_sourcing_status,company_id)");
			query.append(" select cai.catalog_add_item_id, l.locale_code,null,null,cai.company_id from catalog_add_item cai, vv_locale l where cai.request_id = ").append(requestId);
			query.append(" and cai.line_item = 1 and cai.part_id = 1 and l.locale_code in (").append(whereClause).append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//submit request
			query = new StringBuilder("update catalog_add_request_new set submit_date = sysdate where request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//set next status
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			//update catalog_add_item_qc and catalog_add_item_orig
			engEvalProcess.updateCatalogAddItemQcNOrig(requestId);
			engEvalProcess.setPersonnelBean(personnelBean);
			engEvalProcess.setNextStatusAction("Submit");
			Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(requestId);
			CatAddHeaderViewBean catAddHeaderViewBean = headerColl.get(0);
			setNextStatus(catAddHeaderViewBean,engEvalProcess,personnelBean.getPersonnelId());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return requestId;
	} //end of method


	public Collection getVocetStatus(CatAddHeaderViewBean inputBean) throws BaseException {
		genericSqlFactory.setBeanObject(new VocetStatusBean());
		StringBuilder query = new StringBuilder("select * from vocet_status where");
		query.append(" company_id = '").append(inputBean.getCompanyId()).append("'");
		query.append(" and facility_id = '").append(inputBean.getEngEvalFacilityId()).append("'");
		query.append(" order by vocet_status_desc");
		return genericSqlFactory.selectQuery(query.toString());
	}  //end of method

	public Collection getVocetCoatCategory(CatAddHeaderViewBean inputBean) throws BaseException {
		genericSqlFactory.setBeanObject(new VocetCoatCategoryBean());
		StringBuilder query = new StringBuilder("select * from vocet_coat_category where");
		query.append(" company_id = '").append(inputBean.getCompanyId()).append("'");
		query.append(" and facility_id = '").append(inputBean.getEngEvalFacilityId()).append("'");
		query.append(" order by vocet_coat_category_desc");
		return genericSqlFactory.selectQuery(query.toString());
	}  //end of method

	public String copyRequest(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			Collection cin = new Vector(4);
			cin.add(inputBean.getCompanyId());
			cin.add(inputBean.getRequestId());
			cin.add(personnelBean.getPersonnelIdBigDecimal());
			//this is called when user wants to copy request from rejected request
			if ("catalogAddTracking".equals(inputBean.getCalledFrom())) {
				cin.add("Y");
			}else {
				cin.add("N");
			}
			Collection cout = new Vector(2);
			cout.add(new Integer(java.sql.Types.VARCHAR));
			cout.add(new Integer(java.sql.Types.VARCHAR));
			Vector error = (Vector)genericSqlFactory.doProcedure(connection,"p_add_cat_request_copy", cin, cout);
			if (error.get(1) != null && !"OK".equals(error.get(1))) {
				result = error.get(1).toString();
				StringBuilder esub = new StringBuilder("Error while calling procedure to copy request (request #");
				esub.append(inputBean.getRequestId());
				esub.append(")");
				StringBuilder emsg = new StringBuilder("Procedure: p_add_cat_request_copy has an error.\n");
				emsg.append(result);
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
			}else {
				try {
					BigDecimal newRequestId = new BigDecimal((String)error.get(0));
					StringBuilder query = new StringBuilder("");
					if ("catalogAddTracking".equals(inputBean.getCalledFrom())) {
						//if called from catalog add order tracking set original request to Resubmitted status (16) and mark request as rejected
						query = new StringBuilder("update catalog_add_request_new set request_status = 16,request_rejected = 'Y',last_updated = sysdate,last_changed_by = ").append(personnelBean.getPersonnelId());
						query.append(" where request_id = ").append(inputBean.getRequestId());
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					}else {
						//else called from catalog add/msds screen for Edit and Resubmit
						// set original request to Resubmitted status (16)
						query = new StringBuilder("update catalog_add_request_new set request_status = 16,last_updated = sysdate,last_changed_by = ").append(personnelBean.getPersonnelId());
						query.append(" where request_id = ").append(inputBean.getRequestId());
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						//set record in catalog_add_approval
						query = new StringBuilder("select min(approval_role) from vv_chemical_approval_role car where company_id = '").append(inputBean.getCompanyId());
						query.append("' and facility_id = '").append(inputBean.getEngEvalFacilityId()).append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId());
						query.append("' and catalog_id = '").append(inputBean.getCatalogId()).append("' and active = 'Y' and resubmit_request = 'Y'");
						query.append(" and approval_role not in (").append("select approval_role from catalog_add_approval where request_id =").append(inputBean.getRequestId());
						query.append(")");
						String tmpApprovalRole = genericSqlFactory.selectSingleValue(query.toString(),connection);
						//insert approval role into catalog_add_approval
						EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
						engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
						if (!engEvalProcess.insertApprovalData(inputBean.getRequestId(),tmpApprovalRole,"Resubmitted","",new BigDecimal(personnelBean.getPersonnelId()),"N")) {
							result = library.getString("error.db.update");
						}
						//notify original requestor
						engEvalProcess.sendUserConfirmedEmail(inputBean.getRequestId());
					}
					//reset new request
					query = new StringBuilder("update catalog_add_request_new set request_status = 17,last_updated = sysdate, submit_date = null,");
					query.append("request_date = sysdate,approval_group_seq = null,approval_group_seq_start_time = null");
					query.append(" where request_id = ").append(newRequestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//setting request accordingly
					inputBean.setOriginalRequestId(inputBean.getRequestId());
					inputBean.setRequestId(newRequestId);
				}catch(Exception ee) {
					log.error(ee);
				}
			}
		}catch (Exception ee) {
			result = library.getString("error.db.update");
			log.error(ee);
			StringBuilder esub = new StringBuilder("Error while copying request (request #");
			esub.append(inputBean.getRequestId());
			esub.append(")");
			String emsg = "Procedure: p_add_cat_request_copy throw an exception.\n";
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public String hasKeywordListApproval(BigDecimal requestId) {
		String result = "N";
		StringBuilder query = new StringBuilder("");
		try {
			query.append("select count(*) from vv_chemical_approval_role$ vcar, catalog_add_request_new carn");
			query.append(" where vcar.company_id = carn.company_id and vcar.catalog_company_id = carn.catalog_company_id");
			query.append(" and vcar.catalog_id = carn.catalog_id and vcar.facility_id = carn.eng_eval_facility_id");
			query.append(" and vcar.role_function in ('Verify Approval Review','Verify List') and vcar.active = 'Y' and carn.request_id = ").append(requestId);
			query.append(" and (");
			query.append("(carn.approval_path = 'MSDS+Part' and (vcar.msds_approval = 'Y' or vcar.part_approval = 'Y'))");
			query.append(" or (carn.approval_path = 'MSDS' and vcar.msds_approval = 'Y')");
			query.append(" or (carn.approval_path = 'Part' and vcar.part_approval = 'Y')");
			query.append(")");
			String tmpVal = genericSqlFactory.selectSingleValue(query.toString());
			if (!"0".equalsIgnoreCase(tmpVal)) {
				result = "Y";
			}
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","hasKeywordListApproval failed",query.toString());
		}
		return result;
	}	//end of method

	private Collection getShelfLifeBasis() throws BaseException {
		genericSqlFactory.setBeanObject(new VvShelfLifeBasisBean());
		StringBuilder query = new StringBuilder("select * from vv_shelf_life_basis");
		query.append(" order by display_order");
		return genericSqlFactory.selectQuery(query.toString());
	}

	private Collection getCatalogStorageTemp(CatAddHeaderViewBean inputBean) throws BaseException {
		genericSqlFactory.setBeanObject(new CatalogStorageTempBean());
		StringBuilder query = new StringBuilder("select * from catalog_storage_temp");
		query.append(" where catalog_company_id = '").append(inputBean.getCatalogCompanyId());
		query.append("' and catalog_id = '").append(inputBean.getCatalogId());
		query.append("' order by to_number(display_order)");
		return genericSqlFactory.selectQuery(query.toString());
	}

	private Collection getFacilityInventoryGroup(CatAddHeaderViewBean inputBean) throws BaseException {
		genericSqlFactory.setBeanObject(new InventoryGroupDefinitionBean());
		StringBuilder query = new StringBuilder("select igd.inventory_group,igd.inventory_group_name");
		query.append(" from facility_inventory_group fig, inventory_group_definition igd");
		query.append(" where fig.inventory_group = igd.inventory_group");
		if (!StringHandler.isBlankString(inputBean.getCompanyId())) {
			query.append(" and fig.company_id = '").append(inputBean.getCompanyId()).append("'");
		}
		if (!StringHandler.isBlankString(inputBean.getEngEvalFacilityId())) {
			query.append(" and fig.facility_id = '").append(inputBean.getEngEvalFacilityId()).append("'");
		}
		query.append(" and fig.allow_catalog_add = 'Y' order by igd.inventory_group_name");
		return genericSqlFactory.selectQuery(query.toString());
	}

	public String saveSpecData(CatAddHeaderViewBean inputBean, Collection beans) throws BaseException {
		String result = "OK";
		StringBuilder query = new StringBuilder("");
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			Iterator iter = beans.iterator();
			while (iter.hasNext()) {
				CatalogAddSpecBean tmpBean = (CatalogAddSpecBean)iter.next();
				if ("new".equalsIgnoreCase(tmpBean.getDataSource())) {
					query = new StringBuilder("update catalog_add_spec set itar = '").append(tmpBean.getItar()).append("'");
					query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and spec_id = '").append(tmpBean.getSpecId()).append("'");
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch(Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Save Spec data failed",query.toString());
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}  //end of method

	public String saveUseApprovalData(CatAddHeaderViewBean inputBean, Collection beans) throws BaseException {
		String result = "OK";
		StringBuilder query = new StringBuilder("");
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			Iterator iter = beans.iterator();
			while (iter.hasNext()) {
				CatalogAddUserGroupBean tmpBean = (CatalogAddUserGroupBean)iter.next();
				if ("new".equalsIgnoreCase(tmpBean.getDataSource())) {
					query = new StringBuilder("update catalog_add_user_group set approval_status = '");
					query.append(tmpBean.getApprovalStatus()).append("', inventory_group = '").append(tmpBean.getInventoryGroup()).append("'");
					query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and work_area = '").append(tmpBean.getWorkArea()).append("'");
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Save Use Approval data failed",query.toString());
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public String saveQplData(CatAddHeaderViewBean inputBean, Collection beans) throws BaseException {
		String result = "OK";
		StringBuilder query = new StringBuilder("");
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			Iterator iter = beans.iterator();
			while (iter.hasNext()) {
				CatPartQplViewBean tmpBean = (CatPartQplViewBean)iter.next();
				if (tmpBean.getLineItem() == null) continue;
				if (tmpBean.getStartingView() == null) continue;
				//update shelf life data
				query = new StringBuilder("update catalog_add_item set ");

				if("Y".equalsIgnoreCase(inputBean.getTimeTempSensitiveYes()))
				{
					query.append("customer_temperature_id = '").append(tmpBean.getCustomerTemperatureId()).append("'");
					if ("Indefinite".equalsIgnoreCase(tmpBean.getShelfLifeBasis())) {
						query.append(",shelf_life_days = '-1'");
						query.append(",shelf_life_basis = ''");
						query.append(",mfg_recommended_shelf_life = 'N'");
					}else if ("MfgRecommended".equalsIgnoreCase(tmpBean.getShelfLifeBasis())) {
						query.append(",shelf_life_days = ''");
						query.append(",shelf_life_basis = ''");
						query.append(",mfg_recommended_shelf_life = 'Y'");
					}else if ("E".equalsIgnoreCase(tmpBean.getShelfLifeBasis()) || "X".equalsIgnoreCase(tmpBean.getShelfLifeBasis())) {
						query.append(",shelf_life_days = '-2'");
						query.append(",shelf_life_basis = '").append(tmpBean.getShelfLifeBasis()).append("'");
						query.append(",mfg_recommended_shelf_life = 'N'");
					}else {
						query.append(",shelf_life_days = ").append(tmpBean.getShelfLifeDays());
						query.append(",shelf_life_basis = '").append(tmpBean.getShelfLifeBasis()).append("'");
						query.append(",mfg_recommended_shelf_life = 'N'");
					}
				}
				else
				{
					query.append("customer_temperature_id = ''");
					query.append(",shelf_life_days = ''");
					query.append(",shelf_life_basis = ''");
					query.append(",mfg_recommended_shelf_life = 'N'");
				}
				if (!StringHandler.isBlankString(tmpBean.getCustomerMixtureNumber())) {
					query.append(",customer_mixture_number = ").append(SqlHandler.delimitString(tmpBean.getCustomerMixtureNumber()));
				}else {
					query.append(",customer_mixture_number = ''");
				}
				if ("Y".equalsIgnoreCase(inputBean.getRoomTempOutTimeYes()) && tmpBean.getRoomTempOutTime() != null) {
					query.append(",room_temp_out_time = ").append(tmpBean.getRoomTempOutTime());
				}else {
					query.append(",room_temp_out_time = ''");
				}
				if (!StringHandler.isBlankString(tmpBean.getCustomerMsdsNumber())) {
					query.append(",customer_msds_number = ").append(SqlHandler.delimitString(tmpBean.getCustomerMsdsNumber()));
				}else {
					query.append(",customer_msds_number = ''");
				}
				if (!StringHandler.isBlankString(tmpBean.getReplacesMsds())) {
					query.append(",replaces_msds = '").append(tmpBean.getReplacesMsds()).append("'");
				}else {
					query.append(",replaces_msds = ''");
				}
				if ("Y".equals(inputBean.getAllowEditHetUsageRecording())) {
					query.append(",het_usage_recording = '").append(tmpBean.getHetUsageRecording()).append("'");
				}
				//label color
				if ("Y".equals(inputBean.getLabelColorRequired())) {
					if (!StringHandler.isBlankString(tmpBean.getLabelColor())) {
						query.append(",label_color = '").append(tmpBean.getLabelColor()).append("'");
					}else {
						query.append(",label_color = ''");
					}
				}
				if (!StringHandler.isBlankString(tmpBean.getMixtureDesc())) {
					query.append(",mixture_desc = ").append(SqlHandler.delimitString(tmpBean.getMixtureDesc()));
				}else {
					query.append(",mixture_desc = ''");
				}
				if ("Y".equalsIgnoreCase(inputBean.getRoomTempOutTimeYes()) && !StringHandler.isBlankString(tmpBean.getRoomTempOutTimeUnit())) {
					query.append(",room_temp_out_time_unit = '").append(tmpBean.getRoomTempOutTimeUnit()).append("'");
				}else {
					query.append(",room_temp_out_time_unit = ''");
				}
				if (!StringHandler.isBlankString(tmpBean.getVocetStatusId())) {
					query.append(",vocet_status_id = '").append(tmpBean.getVocetStatusId()).append("'");
				}else {
					query.append(",vocet_status_id = ''");
				}
				if (!StringHandler.isBlankString(tmpBean.getVocetCoatCategoryId())) {
					query.append(",vocet_coat_category_id = '").append(tmpBean.getVocetCoatCategoryId()).append("'");
				}else {
					query.append(",vocet_coat_category_id = ''");
				}
				if ("true".equals(tmpBean.getNanomaterial()) || "Y".equals(tmpBean.getNanomaterial())) {
					query.append(",nanomaterial = 'Y'");
				}
				if ("false".equals(tmpBean.getNanomaterial()) || "N".equals(tmpBean.getNanomaterial())) {
					query.append(",nanomaterial = null");
				}
				if ("true".equals(tmpBean.getRadioactive()) || "Y".equals(tmpBean.getRadioactive())) {
					query.append(",radioactive = 'Y'");
				}
				if ("false".equals(tmpBean.getRadioactive()) || "N".equals(tmpBean.getRadioactive())) {
					query.append(",radioactive = null");
				}

				if (tmpBean.getMixRatioAmount() == null) {
					query.append(",mix_ratio_size_unit = null");
					query.append(",mix_ratio_amount = null");
				}else {
					query.append(",mix_ratio_size_unit = '").append(StringHandler.emptyIfNull(tmpBean.getMixRatioSizeUnit())).append("'");
					query.append(",mix_ratio_amount = ").append(tmpBean.getMixRatioAmount()==null?"null":tmpBean.getMixRatioAmount());
					query.append(",mixture_physical_state = 'liquid'");
				}
				if (!StringHandler.isBlankString(tmpBean.getCustomerMfgId()))
					query.append(",customer_mfg_id = ").append(SqlHandler.delimitString(tmpBean.getCustomerMfgId()));
				else
					query.append(",customer_mfg_id = ''");

				query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(tmpBean.getLineItem());
				query.append(" and part_id = ").append(tmpBean.getPartId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
			//update catalog_add_request_new.time_temp_sensitive

			if (!StringHandler.isBlankString(inputBean.getTimeTempSensitive())) {
				query = new StringBuilder("update catalog_add_request_new set time_temp_sensitive = '").append(inputBean.getTimeTempSensitive());
				query.append("' where request_id = ").append(inputBean.getRequestId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Save Qpl data failed",query.toString());
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public CatalogAddSpecBean getSpecInfo(CatalogAddSpecBean inputBean, boolean newStandardMfgCert) throws BaseException {
		CatalogAddSpecBean bean = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			if ("newSpec".equalsIgnoreCase(inputBean.getCalledFrom())) {
				bean = new CatalogAddSpecBean();
				bean.setCompanyId(inputBean.getCompanyId());
				bean.setRequestId(inputBean.getRequestId());
				if (newStandardMfgCert) {
					bean.setSpecId("Std Mfg Cert");
				}
			}else {
				bean = getEditSpecData(inputBean);
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return bean;
	}

	private CatalogAddSpecBean getEditSpecData(CatalogAddSpecBean inputBean) throws Exception {
		CatalogAddSpecBean bean = null;
		try {
			genericSqlFactory.setBeanObject(new CatalogAddSpecBean());
			StringBuilder query = new StringBuilder("select * from catalog_add_spec where request_id = ").append(inputBean.getRequestId());
			query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" and spec_id = ").append(SqlHandler.delimitString(inputBean.getSpecId()));
			Collection data = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = data.iterator();
			while (iter.hasNext()) {
				bean = (CatalogAddSpecBean)iter.next();
				break;
			}
			if (bean == null) {
				bean = new CatalogAddSpecBean();
				bean.setRequestId(inputBean.getRequestId());
				bean.setSpecId(inputBean.getSpecId());
			}
		}catch (Exception e) {
			log.error(e);
		}
		return bean;
	}

	public String submitSpec(PersonnelBean personnelBean, CatalogAddSpecBean inputBean) throws Exception {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			//delete spec if exist for request
			if (!StringHandler.isBlankString(inputBean.getSpecId())) {
				deleteSelectedSpec(inputBean,"catalog_add_spec");
			}
			//insert new data and it doesn't matter whether user is adding new spec or updating existing spec on request
			//get max line_item from catalog_add_spec for request
			StringBuilder query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_spec where request_id = ").append(inputBean.getRequestId());
			query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			String maxLineItem = genericSqlFactory.selectSingleValue(query.toString(),connection);
			result = submitNewSpec(personnelBean,inputBean,maxLineItem,"catalog_add_spec");
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public String copyExistingSpecDetailToRequest(CatalogAddSpecBean inputBean, String tableName) {
		String result = "OK";
		StringBuilder query = new StringBuilder("insert into ");
		query.append(tableName).append(" (company_id,request_id,spec_id,spec_library,detail,spec_detail_type,");
		query.append("spec_detail_class,spec_detail_form,spec_detail_group,spec_detail_grade,spec_detail_style,");
		query.append("spec_detail_finish,spec_detail_size,spec_detail_color,spec_detail_other)");
		try {
			query.append(" select distinct '").append(inputBean.getCompanyId()).append("',").append(inputBean.getRequestId());
			query.append(",spec_id,spec_library,detail,spec_detail_type,spec_detail_class,spec_detail_form,spec_detail_group,spec_detail_grade,spec_detail_style,");
			query.append("spec_detail_finish,spec_detail_size,spec_detail_color,spec_detail_other").append(" from spec_detail where spec_id = ").append(SqlHandler.delimitString(inputBean.getSpecId()));
			query.append(" and spec_library = ").append(SqlHandler.delimitString(inputBean.getSpecLibrary()));
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit Add SPEC Detail failed",query.toString());
		}
		return result;
	}

	public String addingExistingSpecToRequest(CatalogAddSpecBean inputBean, String lineItem, String tableName) {
		String result = "OK";
		StringBuilder query = new StringBuilder("insert into ");
		query.append(tableName).append(" (company_id,request_id,line_item,spec_id,spec_name,spec_title,spec_version,");
		query.append("spec_amendment,spec_date,content,on_line,spec_library,spec_source)");
		try {
			query.append(" select distinct '").append(inputBean.getCompanyId()).append("',").append(inputBean.getRequestId()).append(",");
			query.append(lineItem).append(",spec_id,spec_name,spec_title,spec_version,spec_amendment,spec_date,content,on_line,spec_library");
			query.append(",'spec'").append(" from spec where spec_id = ").append(SqlHandler.delimitString(inputBean.getSpecId()));
			query.append(" and spec_library = ").append(SqlHandler.delimitString(inputBean.getSpecLibrary()));
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit Add SPEC failed",query.toString());
		}
		return result;
	}

	public String submitNewSpec(PersonnelBean personnelBean, CatalogAddSpecBean inputBean, String lineItem, String tableName) {
		String result = "OK";
		StringBuilder query = new StringBuilder("insert into ");
		query.append(tableName).append(" (spec_id,request_id,spec_name,spec_title,spec_version,spec_amendment,spec_date,");
		query.append("content,on_line,company_id,spec_library,coc,coa,line_item,spec_source,itar) values (");
		try {
			//determine itarFlag here
			String itarFlag = "N";
			StringBuilder tmpQ = new StringBuilder("select eng_eval_facility_id from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
			String tmpFacilityId =  genericSqlFactory.selectSingleValue(tmpQ.toString(),connection);
			if (personnelBean != null && personnelBean.isFeatureReleased("ItarControl",StringHandler.emptyIfNull(tmpFacilityId),inputBean.getCompanyId()))
				itarFlag = "Y";

			// collapse all consecutive spaces into one, like a web browser would do
			inputBean.setSpecName(StringUtils.join(StringUtils.split(inputBean.getSpecName(), " "), ' '));
			inputBean.setSpecTitle(StringUtils.join(StringUtils.split(inputBean.getSpecTitle(), " "), ' '));
			inputBean.setSpecVersion(StringUtils.join(StringUtils.split(inputBean.getSpecVersion(), " "), ' '));
			inputBean.setSpecAmendment(StringUtils.join(StringUtils.split(inputBean.getSpecAmendment(), " "), ' '));

			StringBuilder specId = new StringBuilder(inputBean.getSpecId());
			String specName = inputBean.getSpecName();
			String specTitle = inputBean.getSpecTitle();
			String specLibrary = inputBean.getSpecLibrary();
			if ("No Specification".equalsIgnoreCase(specId.toString())) {
				specTitle = "No Specification";
				specName = "No Specification";
				specLibrary = "global";
			}else if ("Std Mfg Cert".equalsIgnoreCase(specId.toString())) {
				specTitle = "Std Mfg Cert";
				specName = "Std Mfg Cert";
				specLibrary = "global";
			}else {
				specId = new StringBuilder(specName);
				if (inputBean.getSpecVersion().length() > 0)
					specId = new StringBuilder(specId).append("_").append(inputBean.getSpecVersion());
				if (inputBean.getSpecAmendment().length() > 0)
					specId = new StringBuilder(specId).append("_").append(inputBean.getSpecAmendment());
			}

			query.append(SqlHandler.delimitString(specId.toString()));
			query.append(",").append(inputBean.getRequestId());
			if (!StringHandler.isBlankString(specName)) {
				query.append(",").append(SqlHandler.delimitString(specName));
			}else {
				query.append(",''");
			}
			query.append(",").append(SqlHandler.delimitString(specTitle));
			if (!StringHandler.isBlankString(inputBean.getSpecVersion())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getSpecVersion()));
			}else {
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getSpecAmendment())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getSpecAmendment()));
			}else {
				query.append(",''");
			}
			//spec date
			query.append(",''");
			//content
			query.append(",''");
			//on_line
			query.append(",'N'");
			query.append(",").append(SqlHandler.delimitString(inputBean.getCompanyId()));

			if (!StringHandler.isBlankString(specLibrary)) {
				query.append(",").append(SqlHandler.delimitString(specLibrary));
			}else {
				query.append(",''");
			}

			if (!StringHandler.isBlankString(inputBean.getCoc())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getCoc()));
			}else {
				query.append(",'N'");
			}
			if (!StringHandler.isBlankString(inputBean.getCoa())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getCoa()));
			}else {
				query.append(",'N'");
			}

			query.append(",").append(lineItem);
			//spec_source
			query.append(",'").append(tableName).append("'");
			//itar
			query.append(",'").append(itarFlag).append("'");
			query.append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit Add SPEC failed",query.toString());
		}
		return result;
	}

	public void addNoSpec(CatAddHeaderViewBean inputBean) {
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			//get max line_item from catalog_add_spec for request
			StringBuilder query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_spec where request_id = ").append(inputBean.getRequestId());
			query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
			String maxLineItem = factory.selectSingleValue(query.toString());

			query = new StringBuilder("insert into catalog_add_spec (spec_id,spec_name,spec_title,spec_library,request_id,company_id,line_item,spec_source)");
			query.append(" values ('No Specification','No Specification','No Specification','global',");
			query.append(inputBean.getRequestId()).append(",").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(",").append(maxLineItem);
			query.append(",'catalog_add_spec'");
			query.append(")");
			factory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public void deleteSpec(CatalogAddSpecBean inputBean) throws Exception{
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			deleteSelectedSpec(inputBean,"catalog_add_spec");
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	} //end of method

	public void deleteSelectedSpec(CatalogAddSpecBean inputBean, String tableName) {
		try {
			StringBuilder query = new StringBuilder("delete from ");
			query.append(tableName).append(" where request_id = ").append(inputBean.getRequestId());
			query.append(" and company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(" and spec_id like ").append(SqlHandler.delimitString(inputBean.getSpecId().replaceAll("\\s+",  "%")));
			genericSqlFactory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public void reloadSpecData(CatAddHeaderViewBean bean) throws Exception{
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			getSpecData(bean);
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	} //end of method

	public CatalogAddFlowDownBean getFlowdownInfo(CatalogAddFlowDownBean inputBean) throws BaseException {
		CatalogAddFlowDownBean bean = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			if ("newFlowdown".equalsIgnoreCase(inputBean.getCalledFrom())) {
				bean = new CatalogAddFlowDownBean();
				bean.setRequestId(inputBean.getRequestId());
			}else {
				bean = getEditFlowdownData(inputBean);
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return bean;
	}

	private CatalogAddFlowDownBean getEditFlowdownData(CatalogAddFlowDownBean inputBean) throws Exception {
		CatalogAddFlowDownBean bean = null;
		try {
			genericSqlFactory.setBeanObject(new CatalogAddSpecBean());
			StringBuilder query = new StringBuilder("select * from catalog_add_flow_down where request_id = ").append(inputBean.getRequestId());
			query.append(" and company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(" and flow_down = ").append(SqlHandler.delimitString(inputBean.getFlowDown()));
			Collection data = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = data.iterator();
			while (iter.hasNext()) {
				bean = (CatalogAddFlowDownBean)iter.next();
				break;
			}
			if (bean == null) {
				bean = new CatalogAddFlowDownBean();
				bean.setRequestId(inputBean.getRequestId());
				bean.setFlowDown(inputBean.getFlowDown());
			}
		}catch (Exception e) {
			log.error(e);
		}
		return bean;
	}

	public String submitFlowdown(CatalogAddFlowDownBean inputBean) throws Exception {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select count(*) from catalog_add_flow_down where request_id = ").append(inputBean.getRequestId());
			query.append(" and company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(" and catalog_id = ").append(SqlHandler.delimitString(inputBean.getCatalogId()));
			query.append(" and flow_down = ").append(SqlHandler.delimitString(inputBean.getFlowDown()));
			if(dataCountIsZero(query.toString())) {
				result = submitNewFlowdown(inputBean);
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	private String submitNewFlowdown(CatalogAddFlowDownBean inputBean) {
		String result = "OK";
		StringBuilder query = new StringBuilder("insert into catalog_add_flow_down (request_id,flow_down,catalog_id,company_id) values (");
		try {
			query.append(inputBean.getRequestId());
			query.append(",").append(SqlHandler.delimitString(inputBean.getFlowDown()));
			query.append(",").append(SqlHandler.delimitString(inputBean.getCatalogId()));
			query.append(",").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit Add flow down failed",query.toString());
		}
		return result;
	}


	public void deleteFlowdown(CatalogAddFlowDownBean inputBean) {
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("delete from catalog_add_flow_down where request_id = ").append(inputBean.getRequestId());
			query.append(" and company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
			query.append(" and flow_down = ").append(SqlHandler.delimitString(inputBean.getFlowDown()));
			factory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public void reloadFlowdownData(CatAddHeaderViewBean bean) throws Exception{
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			getFlowdownData(bean);
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	} //end of method

	private void setCatalogAddItemRestricted(CatAddHeaderViewBean inputBean) {
		try {
			//by default all requests are restricted = Y
			//update to restricted = N if material/msds previously approved without restriction
			StringBuilder query = new StringBuilder("update catalog_add_item set restricted = 'N' where request_id = ").append(inputBean.getRequestId());
			query.append(" and (line_item,part_id) in (select cai.line_item, cai.part_id");
			query.append(" from catalog_add_item cai, catalog_add_planned_use capu, catalog_add_request_new carn, catalog_company cc,customer_msds_xref cmx, customer_msds_or_mixture_use mxac");
			query.append(" where cai.company_id = capu.company_id and cai.request_id = capu.request_id");
			query.append(" and cai.company_id = carn.company_id and cai.request_id = carn.request_id");
			query.append(" and cai.company_id = cc.company_id and carn.catalog_id = cc.catalog_id");
			query.append(" and cai.company_id = cmx.company_id and cai.customer_msds_number = cmx.customer_msds_number");
			query.append(" and cai.material_id = cmx.material_id and cc.customer_msds_db = cmx.customer_msds_db");
			query.append(" and cmx.company_id = mxac.company_id and cmx.customer_msds_db = mxac.customer_msds_db and cmx.customer_msds_number = mxac.customer_msds_or_mixture_no");
			query.append(" and capu.usage_subcategory_id = mxac.usage_subcategory_id and capu.material_subcategory_id = mxac.material_subcategory_id");
			query.append(" and mxac.restricted = 'N' and cai.request_id = ").append(inputBean.getRequestId()).append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","setCatalogAddItemRestricted request data failed","See log files");
		}
	} //end of method

	public String submitRequestData(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		StringBuilder query = new StringBuilder("");
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			result = saveHeaderData(inputBean,personnelBean);
			if ("OK".equalsIgnoreCase(result)) {
				//set submit date
				query.append("update catalog_add_request_new set submit_date = sysdate where request_id = ").append(inputBean.getRequestId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//set restricted flag in catalog_add_item
				if ("Y".equals(inputBean.getHasHmrbTab())) {

					if(inputBean.getQplContainsKit())
						CatalogAddHmrbProcess.removeThinnerHmrbData(inputBean.getRequestId(), inputBean.getCompanyId(), genericSqlFactory, connection);

					setCatalogAddItemRestricted(inputBean);
				}

				if("SEAGATE".equalsIgnoreCase(inputBean.getCompanyId()))
				{
					String CRA = getCRA(inputBean.getRequestId());
					if ("CRA".equalsIgnoreCase(CRA)) {
						String queryString = buildURLString(inputBean.getRequestId().toPlainString());
						inputBean.setSeagateURL(new ResourceLibrary( "tcmISWebResource" ).getString("seagateCRAURL") + queryString);
					}
				}

				//company like Aerojet would like for us to generate a part number for request if it's empty
				query = new StringBuilder("select fx_feature_released('AutoGenerateCatPartNo',carn.eng_eval_facility_id,carn.company_id");
				query.append(") released from catalog_add_request_new carn where request_id = ").append(inputBean.getRequestId());
				if ("Y".equals(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
					createPartNoForRequest(inputBean);
				}

				//set next status
				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
				engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
				//populate catalog_add_item_locale for request
				engEvalProcess.populateCatalogAddItemLocale(inputBean.getRequestId(),inputBean.getStartingView());
				//update catalog_add_item_qc and catalog_add_item_orig
				engEvalProcess.updateCatalogAddItemQcNOrig(inputBean.getRequestId());
				//update catalog_add_spec_qc
				engEvalProcess.updateCatalogAddSpecQc(inputBean.getRequestId());
				engEvalProcess.setNextStatusAction("Submit");
				result = setNextStatus(inputBean,engEvalProcess,personnelBean.getPersonnelId());
				if ("OK".equalsIgnoreCase(result)) {
					//check to see if request needs MSDS
					query = new StringBuilder("select count(*) from catalog_add_item where starting_view in (0,6) and nvl(line_status,'X') <> 'Rejected' and request_id = ").append(inputBean.getRequestId());
					if (!dataCountIsZero(query.toString())) {
						String tmpVal = engEvalProcess.getUserUploadedMsdsForRequest(inputBean.getRequestId());
						if (tmpVal.length() == 0) {
							engEvalProcess.sendRequestorMsdsEmail(inputBean.getRequestId());
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit request data failed",query.toString());
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public String rejectRequest(CatAddHeaderViewBean inputBean, Collection approvalRoleBeans, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			result = callRejectRequest(inputBean,approvalRoleBeans,personnelBean);
			if ("OK".equals(result) || "Rejected".equals(result)) {
				result = "OK";
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("error.db.update");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	//the reason that I need this method is because which ever this method is called from the connection already established
	public String callRejectRequest(CatAddHeaderViewBean inputBean, Collection approvalRoleBeans, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		try {
			StringBuilder approvalRoles = new StringBuilder("");
			Iterator iter = approvalRoleBeans.iterator();
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			//this variable keep track if approval role already approved
			StringBuilder approvalRoleAlreadyApproved = new StringBuilder("");
			while (iter.hasNext()) {
				boolean canReject = true;
				CatAddStatusViewBean bean = (CatAddStatusViewBean)iter.next();
				if (!StringHandler.isBlankString(bean.getStatus())) {
					if(!"0".equals(checkApprovalCount(inputBean, bean))) {
						approvalRoleAlreadyApproved.append(library.getString("label.approvalcodewasapprovedorrejected").replace("{0}", bean.getApprovalRole())).append(" ");
						canReject = false;
					}

					if (canReject) {
						//keeping tracking of approval roles so I can determine whether to continue on reject or not
						if (approvalRoles.length() == 0) {
							approvalRoles.append("'").append(bean.getApprovalRole()).append("'");
						}else {
							approvalRoles.append(",'").append(bean.getApprovalRole()).append("'");
						}
						//insert approval role into catalog_add_approval
						if (!engEvalProcess.insertApprovalData(inputBean.getRequestId(),bean.getApprovalRole(),"Rejected",bean.getReason(),new BigDecimal(personnelBean.getPersonnelId()),"N")) {
							result += library.getString("error.db.update");
							break;
						}
					}
				}
			} //end of loop
			//should only set rejected or continue if there is at least one approval role rejected this request
			if ("OK".equalsIgnoreCase(result) && approvalRoles.length() > 0) {
				if (engEvalProcess.continueOnRejection(inputBean,approvalRoles)) {
					engEvalProcess.setNextStatusAction("Rejected");
					result = setNextStatus(inputBean,engEvalProcess,personnelBean.getPersonnelId());
				}else {
					//freeze review test results if request has list or review rules
					if ("Y".equals(hasKeywordListApproval(inputBean.getRequestId()))) {
						freezeApprovalResult(inputBean);
					}
					//reject request
					StringBuilder query = new StringBuilder("update catalog_add_request_new set request_status = 7,approval_group_seq = 0,request_rejected = 'Y'");
					query.append(",eval_status = 'Rejected',last_updated = sysdate,approval_group_seq_start_time = null where request_id = ").append(inputBean.getRequestId());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//send user email
					engEvalProcess.sendUserConfirmedEmail(inputBean.getRequestId());
					result = "Rejected";
				}
			}
			//pass already approved message to client
			if (approvalRoleAlreadyApproved.length() > 0) {
				if ("OK".equals(result) || "Rejected".equals(result) ) {
					result = approvalRoleAlreadyApproved.toString();
				}else {
					result += " "+approvalRoleAlreadyApproved.toString();
				}
			}

		}catch (Exception e) {
			log.error(e);
			result = library.getString("error.db.update");
		}
		return result;
	}

	private void freezeApprovalResult(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			//call store procedure to freeze approval results
			Collection cin = new Vector(8);
			cin.add(inputBean.getCompanyId());
			cin.add(inputBean.getRequestId());
			cin.add("");    //approval_role
			cin.add("");    //stop_on_failure
			cin.add("");    //rule_id
			cin.add("");    //arguement_1
			cin.add("");    //arguement_2
			cin.add("Y");    //provide_line_list_detail
			Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.VARCHAR));
			if (log.isDebugEnabled()) {
				log.debug(cin);
			}
			Collection procedureData = genericSqlFactory.doProcedure(connection,"PKG_APPROVAL_REVIEW.P_ARCHIVE_CAT_ADD_REVIEW", cin, cout);
			Iterator i11 = procedureData.iterator();
			String result = "";
			while (i11.hasNext()) {
				result = (String) i11.next();
			}
			if (!StringHandler.isBlankString(result) && !result.startsWith("OK")) {
				StringBuilder esub = new StringBuilder("Error while calling procedure to freeze approval results (request #");
				esub.append(inputBean.getRequestId());
				esub.append(")");
				StringBuilder emsg = new StringBuilder("Procedure: PKG_APPROVAL_REVIEW.P_ARCHIVE_CAT_ADD_REVIEW has an error.\n");
				emsg.append(result);
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
			}
		}catch (Exception ee) {
			log.error(ee);
			StringBuilder esub = new StringBuilder("Error while processing request (request #");
			esub.append(inputBean.getRequestId());
			esub.append(")");
			String emsg = "Procedure: PKG_APPROVAL_REVIEW.P_ARCHIVE_CAT_ADD_REVIEW throw an exception.\n";
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
			throw ee;
		}
	} //end of method

	private String checkApprovalCount(CatAddHeaderViewBean inputBean, CatAddStatusViewBean bean)throws Exception {
		String result = "0";
		try {
			StringBuilder query = new StringBuilder("select count(*) from catalog_add_approval");
			query.append(" where facility_id = '").append(inputBean.getEngEvalFacilityId());
			query.append("' and approval_role = '").append(bean.getApprovalRole());
			query.append("' and request_id = ").append(inputBean.getRequestId());
			return genericSqlFactory.selectSingleValue(query.toString(), connection);
		}catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	//this method is to be call by specific pages approving catalog add request
	//for example Translation page and catalog Spec Qc
	//and genericSqlFactory and connection are declared and passed in from those processes
	public String approvalRequestFromSpecificPage(CatAddHeaderViewBean inputBean, String approvalRole, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		try {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			if (!engEvalProcess.insertApprovalData(inputBean.getRequestId(),approvalRole,"Approved","",personnelBean.getPersonnelIdBigDecimal(),"N")) {
				result = library.getString("error.db.update");
			}
			if ("OK".equalsIgnoreCase(result)) {
				engEvalProcess.setNextStatusAction("Approved");
				result = setNextStatus(inputBean,engEvalProcess,personnelBean.getPersonnelId());
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("error.db.update");
		}
		return result;
	} //end of method


	//this method is for new material/msds approval only
	public String approvalRequestFromCatAddReqMsdsProcess(CatAddHeaderViewBean inputBean, String approvalRole, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			if (!engEvalProcess.insertApprovalData(inputBean.getRequestId(),approvalRole,"Approved","",personnelBean.getPersonnelIdBigDecimal(),"N")) {
				result = library.getString("error.db.update");
			}
			if ("OK".equalsIgnoreCase(result)) {
				//set mixture VOC, VOC LWES, Density and Specific Gravity
				calculateMixtureData(inputBean);
				//copy mixture data from catalog_add_item_qc to catalog_add_item
				copyMixtureData(inputBean);
				//update customer_mixture_number and customer_msds_number
				setMixtureAndCustomerMsds(inputBean);
				engEvalProcess.setNextStatusAction("Approved");
				result = setNextStatus(inputBean,engEvalProcess,personnelBean.getPersonnelId());
				//mix ratio calculation because Item QC might be auto approved
				//update calculated mix_ratio data if facility allow kit
				if (isFacilityAllowKit(inputBean.getRequestId()))
					setMixRatioCalculation(inputBean.getRequestId());
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("error.db.update");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		if (StringHandler.isBlankString(result))
			result = "OK";
		return result;
	} //end of method

	public String approvalRequest(CatAddHeaderViewBean inputBean, Collection approvalRoleBeans, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			result = saveHeaderData(inputBean,personnelBean);
			//this variable keep track if approval role already approved
			StringBuilder approvalRoleAlreadyApproved = new StringBuilder("");
			if ("OK".equalsIgnoreCase(result)) {
				Iterator iter = approvalRoleBeans.iterator();
				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
				engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
				//insert record into catalog_add_approval
				while (iter.hasNext()) {
					boolean canApprove = true;
					CatAddStatusViewBean bean = (CatAddStatusViewBean)iter.next();
					if (!StringHandler.isBlankString(bean.getStatus())) {
						if(!"0".equals(checkApprovalCount(inputBean, bean))) {
							approvalRoleAlreadyApproved.append(library.getString("label.approvalcodewasapprovedorrejected").replace("{0}", bean.getApprovalRole())).append(" ");
							canApprove = false;
						}

						if (canApprove) {
							if (!engEvalProcess.insertApprovalData(inputBean.getRequestId(),bean.getApprovalRole(),"Approved",bean.getReason(),new BigDecimal(personnelBean.getPersonnelId()),inputBean.getApprovedWithRestriction())) {
								result = library.getString("error.db.update");
								break;
							}else {
								//successfully approved for role
								//if approval role is before TCM QC then copy data from catalog_add_item to catalog_add_item_qc
								if ("BEFORE".equalsIgnoreCase(bean.getBeforeTcmQc()))
									engEvalProcess.createCatalogAddItemQc(inputBean.getRequestId());
								//if approval role is before TCM SPEC then copy data from catalog_add_spec to catalog_add_spec_qc
								if ("BEFORE".equalsIgnoreCase(bean.getBeforeTcmSpec()))
									engEvalProcess.createCatalogAddSpecQc(inputBean.getRequestId());
								engEvalProcess.sendAdditionalNotificationWhenApproved(inputBean.getRequestId(),bean.getApprovalRole());
							}
						}
					}
				} //end of loop
				if ("OK".equalsIgnoreCase(result)) {
					engEvalProcess.setNextStatusAction("Approved");
					result = setNextStatus(inputBean,engEvalProcess,personnelBean.getPersonnelId());
				}
			}
			//pass already approved message to client
			if (approvalRoleAlreadyApproved.length() > 0) {
				if ("OK".equals(result)) {
					result = approvalRoleAlreadyApproved.toString();
				}else {
					result += " "+approvalRoleAlreadyApproved.toString();
				}
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("error.db.update");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	//this method is build so that when catalog approved a request then route it to this method
	//so we can handle the new approval logic
	public String approveRequestFromNChemObj(BigDecimal requestId, BigDecimal personnelId) throws Exception{
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(requestId);
			CatAddHeaderViewBean catAddHeaderViewBean = headerColl.get(0);
			//the reason for this is because it's approved from the OLD TCM QC page and it does not
			//have a URL
			if (StringHandler.isBlankString(URL)) {
				ResourceLibrary companyLibrary = new ResourceLibrary("dbuser");
				Enumeration enur = companyLibrary.getKeys();
				String tmpClientUrl = "";
				while (enur.hasMoreElements()) {
					String key = (String)enur.nextElement();
					String tmpData = companyLibrary.getString(key);
					if (this.getClient().equalsIgnoreCase(tmpData)) {
						tmpClientUrl = key;
						break;
					}
				}

				ResourceLibrary resource = new ResourceLibrary("label");
				String hostUrl = resource.getString("label.hosturl");

				if (tmpClientUrl.length() > 0) {
					URL = hostUrl+"/tcmIS"+tmpClientUrl;
				}else {
					URL = hostUrl+"/tcmIS/"+this.getClient().toLowerCase();
				}
			}

			//update calculated mix_ratio data if facility allow kit
			if (isFacilityAllowKit(requestId))
				setMixRatioCalculation(requestId);

			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			//set catalog_add_item.het_usage_recording
			engEvalProcess.setCatalogAddItemHetUsageRecording(requestId);
			engEvalProcess.setNextStatusAction("Approved");
			result = setNextStatus(catAddHeaderViewBean,engEvalProcess,personnelId.intValue());
		}catch (Exception e) {
			log.error(e);
			result = "Error";
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	private void setMixRatioCalculation(BigDecimal requestId) {
		try {
			StringBuilder query = new StringBuilder("select line_item from catalog_add_item where request_id = ").append(requestId);
			query.append(" group by line_item having count(*) > 1");
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean bean = (CatalogAddItemBean) iter.next();
				query = new StringBuilder("update catalog_add_item cai set calc_mix_ratio_amount =");
				query.append(" round(fx_item_weight(cai.item_id, cai.material_id) / fx_item_weight(cai.item_id) * 100,2)");
				query.append(", calc_mix_ratio_size_unit = '%(w/w)'");
				query.append(" where cai.request_id = ").append(requestId).append(" and cai.line_item = ").append(bean.getLineItem());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}

		}catch(Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Unable to set catalog_add_item.calc_mix_ratio_amount","Unable to set catalog_add_item.calc_mix_ratio_amount for request: "+requestId);
		}
	} //end of method

	private void updatePossFacility(BigDecimal requestId) {
		try {
			//update catalog_add_user_group with other POSS facilities
			String tmp = "insert into catalog_add_user_group (company_id,request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)"+
					" select caug.company_id,caug.request_id,fr.scope facility_id,'All' work_area,'All' user_group_id,caug.process_desc"+
					",caug.quantity_1,caug.per_1,caug.period_1,caug.quantity_2,caug.per_2,caug.period_2"+
					" from catalog_add_user_group caug, feature_release fr"+
					" where caug.facility_id = 'Tucson Airport' and caug.request_id = "+requestId+
					" and caug.company_id = fr.company_id and fr.feature = 'PossFacility'"+
					" and fr.scope not in (select facility_id from catalog_add_user_group where request_id = "+requestId+")";
			genericSqlFactory.deleteInsertUpdate(tmp,connection);
		}catch (Exception e) {

		}

	}  //end of method

	public String setNextStatus(CatAddHeaderViewBean inputBean, EngEvalProcess engEvalProcess, int personnelId) {
		String result = "OK";
		try {
			//set carn.msds_approval
			engEvalProcess.setApprovalPath(inputBean.getRequestId());
			//set carn.cat_add_part_needs_review flag
			engEvalProcess.setCatPartNeedsReview(inputBean.getRequestId(),personnelId);
			String[] resultData = engEvalProcess.setNextStatus(inputBean.getRequestId());
			String done = resultData[EngEvalProcess.DONE_INDEX];
			//send email to user if the end of approval process is reach.  Otherwise, continue to the next approval group
			if ("Done".equalsIgnoreCase(done)) {
				//freeze review test results if request has list or review rules
				if ("Y".equals(hasKeywordListApproval(inputBean.getRequestId()))) {
					freezeApprovalResult(inputBean);
				}

				String startingView = resultData[EngEvalProcess.STARTING_VIEW_INDEX];
				//if MSDS approval only
				if ("6".equals(startingView) || "7".equals(startingView)) {
					//update customer_mixture_number and customer_msds_number
					setMixtureAndCustomerMsds(inputBean);
					moveKitDocsToItDirectory(inputBean);
					//call procedure to put data into customer_msds_xref
					result = addMsdsApproval(inputBean);
					if (StringHandler.isBlankString(result) || "OK".equalsIgnoreCase(result)) {
						engEvalProcess.sendUserConfirmedEmail(inputBean.getRequestId());
						result = "OK";
					}
				}else if ( ! "9".equals(startingView)){
					//POSS request
					if ("Y".equalsIgnoreCase(resultData[EngEvalProcess.POSS_REQUEST_INDEX])) {
						updatePossFacility(inputBean.getRequestId());
					}
					//create part if part is empty
					if (createPartNoForRequest(inputBean)) {
						//handle PWA TEC catalog adds
						pwaTecCatalogAddsExtraProcess(inputBean);

						//put part into catalog
						NewCatalogItemProcess newCatalogItemProcess = new NewCatalogItemProcess(this.getClient(),this.getLocale(),URL);
						newCatalogItemProcess.setFactoryConnection(genericSqlFactory,connection);
						if ("Y".equalsIgnoreCase(inputBean.getCreateTemporaryItem())) {
							newCatalogItemProcess.updateTemporaryItem(inputBean);
						}else {
							//mark catalog_add_approval.restricted = Y if request has HMRB and only one work area
							//was requested for this request and that work area is not "All"
							setRestrictedFlag(inputBean);
							//update customer_mixture_number and customer_msds_number
							setMixtureAndCustomerMsds(inputBean);
							//move spec into it correct directory
							moveKitDocsToItDirectory(inputBean);
							moveSpecsToItDirectory(inputBean);
							//call procedure to put data into catalog
							//the reason I doing this test is because this call be called from TCM SPEC QC
							boolean includeCompany = false;
							if ("TCM_OPS".equals(this.getClient()))
								includeCompany = true;
							result = newCatalogItemProcess.putPartInCatalog(inputBean,includeCompany);
							if (StringHandler.isBlankString(result) || "OK".equalsIgnoreCase(result)) {
								engEvalProcess.sendUserConfirmedEmail(inputBean.getRequestId());
								result = "OK";
							}
						}
					}
				}
				//insert data into company_manufacturer if applicable
				setManufacturerIdMapping(inputBean);

				engEvalProcess.sendApproversNotifyOnlyEmail(inputBean.getRequestId());
				//if MERCK than update customer_po_stage status
				//todo need to find a cleaner way to handle this, but for now it's okay: tngo July 11, 2014
				if ("MERCK".equals(inputBean.getCompanyId())) {
					CreateCatalogAddProcess cProcess = new CreateCatalogAddProcess(this.getClient(),this.getLocale(),URL);
					cProcess.setFactoryConnection(genericSqlFactory,connection);
					cProcess.updateCustomerPoStageApprovedCatalogAdd(inputBean);
				}
			}else if ("Rejected".equalsIgnoreCase(done)) {
				Collection tmpApprovalRoles = new Vector(1);
				CatAddStatusViewBean tmpBean = new CatAddStatusViewBean();
				tmpBean.setApprovalRole(resultData[EngEvalProcess.REJECT_ON_FAILURE_APPROVAL_ROLE_INDEX]);
				tmpBean.setReason(resultData[EngEvalProcess.REJECT_ON_FAILURE_STATEMENT_INDEX]);
				tmpBean.setStatus("Reject");
				tmpApprovalRoles.add(tmpBean);

				PersonnelBean tmpPBean = new PersonnelBean();
				tmpPBean.setPersonnelId(-1);

				result = callRejectRequest(inputBean,tmpApprovalRoles,tmpPBean);
				if ("".equals(result) || "OK".equals(result) || "Rejected".equals(result)) {
					//sending email to the next group/group seq
					if ("New group".equalsIgnoreCase(resultData[EngEvalProcess.NEXT_GROUP_INDEX])) {
						engEvalProcess.sendApproversEmail(inputBean.getRequestId(),resultData[EngEvalProcess.MESSAGE_TO_APPROVER_INDEX],engEvalProcess.getOtherApprovalRoleEmail(inputBean.getRequestId()),engEvalProcess.getApproversNotifyOnlyEmail(inputBean.getRequestId()));
					}
					result = "OK";
				}
				//if MERCK than update customer_po_stage status
				//todo need to find a cleaner way to handle this, but for now it's okay: tngo July 11, 2014
				if ("MERCK".equals(inputBean.getCompanyId())) {
					CreateCatalogAddProcess cProcess = new CreateCatalogAddProcess(this.getClient(),this.getLocale(),URL);
					cProcess.setFactoryConnection(genericSqlFactory,connection);
					cProcess.updateCustomerPoStageRejectedCatalogAdd(inputBean);
				}
			}else {
				//sending email to the next group/group seq
				if ("New group".equalsIgnoreCase(resultData[EngEvalProcess.NEXT_GROUP_INDEX])) {
					engEvalProcess.sendApproversEmail(inputBean.getRequestId(),resultData[EngEvalProcess.MESSAGE_TO_APPROVER_INDEX],engEvalProcess.getOtherApprovalRoleEmail(inputBean.getRequestId()),engEvalProcess.getApproversNotifyOnlyEmail(inputBean.getRequestId()));
				}
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("error.db.update");
		}
		return result;
	} //end of method

	private void setManufacturerIdMapping(CatAddHeaderViewBean inputBean) {
		try {
			StringBuilder query = new StringBuilder("insert into company_manufacturer ");
			query.append("select company_id,mfg_id,customer_mfg_id,'' customer_mfg_desc");
			query.append(" from catalog_add_item where mfg_id is not null and customer_mfg_id is not null");
			query.append(" and request_id = ").append(inputBean.getRequestId());
			query.append(" minus");
			query.append(" select company_id,mfg_id,customer_mfg_id,'' customer_mfg_desc from company_manufacturer");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Update customer manufacturer data from catalog_add_item to company_manufacturer failed ("+inputBean.getRequestId()+")","See log for details.\n"+e.getMessage());
		}
	}  //end of method

	public String getCustomerMfg(String companyId,BigDecimal mfgId) {
		try {
			StringBuilder query = new StringBuilder("select customer_mfg_id from company_manufacturer where company_id = '").append(companyId).append("'");
			query.append(" and mfg_id = ").append(mfgId);
			query.append(" order by customer_mfg_id");
			genericSqlFactory.setBeanObject(new CatPartQplViewBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(), connection).iterator();
			StringBuilder results = new StringBuilder("");
			while (iter.hasNext()) {
				CatPartQplViewBean catPartQplViewBean = (CatPartQplViewBean) iter.next();
				if (!StringHandler.isBlankString(results.toString()))
					results.append("; ");
				results.append(catPartQplViewBean.getCustomerMfgId());
			}
			return results.toString();
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Getting customer manufacturer data from company_manufacturer failed ("+companyId+","+mfgId+")","See log for details.\n"+e.getMessage());
			return "";
		}
	} //end of method

	void moveSpecsToItDirectory(CatAddHeaderViewBean inputBean) {
		try {
			//final documents path
			ResourceLibrary tmpRl = new ResourceLibrary("tcmISWebResource");
			String docBasePath = tmpRl.getString("docViewerPath");
			if (!docBasePath.endsWith(File.separator)) {
				docBasePath += File.separator;
			}
			docBasePath += "spec"+File.separator;

			//catalog add uploaded documents path
			tmpRl = new ResourceLibrary("uploadFile");
			String savedPath = tmpRl.getString("file.path");
			if (!savedPath.endsWith(File.separator)) {
				savedPath += File.separator;
			}
			savedPath += "catalogAddSpec"+File.separator;

			//move spec image to correct directory logic here
			StringBuilder query = new StringBuilder("select * from catalog_add_spec where company_id = '").append(inputBean.getCompanyId());
			query.append("' and request_id = ").append(inputBean.getRequestId()).append(" and on_line = 'Y' and content is not null and spec_source <> 'spec'");
			genericSqlFactory.setBeanObject(new CatalogAddSpecBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				CatalogAddSpecBean bean = (CatalogAddSpecBean)iter.next();
				//final image fileName is spec_id.file_extension
				int terminator = bean.getContent().indexOf(".");
				String destinationFileName = docBasePath+bean.getSpecId();
				//the reason for this if is to handle fileName without extension
				if (terminator > 0)
					destinationFileName += bean.getContent().substring(terminator);
				File source = new File(savedPath+bean.getContent());
				File destination = new File(destinationFileName);
				//the reason for copy is because we still want users to be able to view SPECs even after it was approved
				FileHandler.copy(source,destination);
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Moving Spec images to correct directory failed ("+inputBean.getRequestId()+")","See log for details.\n"+e.getMessage());
		}
	}  //end of method


	void moveKitDocsToItDirectory(CatAddHeaderViewBean inputBean) {
		try {
			//final documents path
			ResourceLibrary resourceLibrary = new ResourceLibrary("uploadFile");
			String sourcePath = resourceLibrary.getString("file.path");
			resourceLibrary = new ResourceLibrary("msdsdocument");
			String destinationPath = resourceLibrary.getString("kit.documentfile.path");

			StringBuilder query = new StringBuilder("select distinct cad.*, cai.customer_mixture_number FROM catalog_add_document cad, catalog_add_item cai where cad.request_id = cai.request_id and cad.company_id = '").append(inputBean.getCompanyId());
			query.append("' and cad.request_id = ").append(inputBean.getRequestId());
			genericSqlFactory.setBeanObject(new MsdsDocumentInputBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				MsdsDocumentInputBean bean = (MsdsDocumentInputBean)iter.next();
				int terminator = bean.getDocumentName().indexOf(".");
				StringBuilder destinationFileName = new StringBuilder(destinationPath).append(bean.getCustomerMixtureNumber()).append("_").append(bean.getDocumentId());
				//the reason for this if is to handle fileName without extension
				if (terminator > 0)
					destinationFileName.append(bean.getDocumentName().substring(terminator));
				File source = new File(new StringBuilder(sourcePath).append(bean.getDocumentUrl()).toString());
				File destination = new File(destinationFileName.toString());
				FileHandler.move(source, destination);
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Moving Kit images to correct directory failed ("+inputBean.getRequestId()+")","See log for details.\n"+e.getMessage());
		}
	}  //end of method

	private void calculateMixtureData(CatAddHeaderViewBean inputBean) {
		try {
			//catalog add request is for Mixture and facility allows mixture
			if (isFacilityAllowKit(inputBean.getRequestId())) {
				//calculate density and specific gravity
				StringBuilder query = new StringBuilder("update catalog_add_item_qc set (");
				query.append("mixture_density,mixture_density_upper,mixture_density_unit,mixture_density_detect,mixture_density_source");
				query.append(") = (select value,upper,unit,detect,nvl(source,'calculation') source from table(pkg_mixture_property.fx_cat_add_density_tb(").append(inputBean.getRequestId()).append(",").append(inputBean.getLineItem()).append(")))");
				query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem());
				query.append(" and mixture_density is null");
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//specific gravity
				query = new StringBuilder("update catalog_add_item_qc set (");
				query.append("mixture_specific_gravity,mixture_specific_gravity_upper,mixture_specific_gravity_basis,mixture_specificgravity_detect,mixture_specificgravity_source");
				query.append(") = (select value,upper,basis,detect,nvl(source,'calculation') source from table(pkg_mixture_property.fx_cat_add_specific_gravity_tb(").append(inputBean.getRequestId()).append(",").append(inputBean.getLineItem()).append(")))");
				query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem());
				query.append(" and mixture_specific_gravity is null");
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//VOC
				query = new StringBuilder("update catalog_add_item_qc set (mixture_voc,mixture_voc_upper,mixture_voc_unit,mixture_voc_detect,mixture_voc_source)");
				query.append(" = (select value,upper,unit,detect,nvl(source,'calculation') source from table(pkg_mixture_property.fx_cat_add_voc_tb(").append(inputBean.getRequestId()).append(",").append(inputBean.getLineItem()).append(")))");
				query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem());
				query.append(" and mixture_voc is null");
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//VOC LWES
				query = new StringBuilder("update catalog_add_item_qc set (mixture_voc_lwes,mixture_voc_lwes_upper,mixture_voc_lwes_unit,mixture_voc_lwes_detect,mixture_voc_lwes_source)");
				query.append(" = (select value,upper,unit,detect,nvl(source,'calculation') source from table(pkg_mixture_property.fx_cat_add_voc_lwes_tb(").append(inputBean.getRequestId()).append(",").append(inputBean.getLineItem()).append(")))");
				query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem());
				query.append(" and mixture_voc_lwes is null");
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Update mixture calculated data in catalog_add_item_qc failed ("+inputBean.getRequestId()+")","See log for details.\n"+e.getMessage());
		}
	}  //end of method

	private void copyMixtureData(CatAddHeaderViewBean inputBean) {
		try {
			StringBuilder query = new StringBuilder("update catalog_add_item cai set (mixture_voc,mixture_voc_lower,mixture_voc_upper,mixture_voc_unit,mixture_voc_detect,mixture_voc_source");
			query.append(",mixture_voc_lwes,mixture_voc_lwes_lower,mixture_voc_lwes_upper,mixture_voc_lwes_unit,mixture_voc_lwes_source,mixture_voc_lwes_detect");
			query.append(",mixture_product_code,mixture_physical_state_source,mixture_physical_state,mixture_mfg_id,mixture_desc");
			query.append(",mixture_density,mixture_density_upper,mixture_density_unit,mixture_density_detect,mixture_density_source");
			query.append(",mixture_specific_gravity,mixture_specific_gravity_lower,mixture_specific_gravity_upper,mixture_specific_gravity_basis,mixture_specificgravity_detect,mixture_specificgravity_source");
			query.append(",customer_mixture_number");
			query.append(") = (select mixture_voc,mixture_voc_lower,mixture_voc_upper,mixture_voc_unit,mixture_voc_detect,mixture_voc_source");
			query.append(",mixture_voc_lwes,mixture_voc_lwes_lower,mixture_voc_lwes_upper,mixture_voc_lwes_unit,mixture_voc_lwes_source,mixture_voc_lwes_detect");
			query.append(",mixture_product_code,mixture_physical_state_source,mixture_physical_state,mixture_mfg_id,mixture_desc");
			query.append(",mixture_density,mixture_density_upper,mixture_density_unit,mixture_density_detect,mixture_density_source");
			query.append(",mixture_specific_gravity,mixture_specific_gravity_lower,mixture_specific_gravity_upper,mixture_specific_gravity_basis,mixture_specificgravity_detect,mixture_specificgravity_source");
			query.append(",customer_mixture_number");
			query.append(" from catalog_add_item_qc caiq where cai.company_id = caiq.company_id");
			query.append(" and cai.request_id = caiq.request_id and cai.line_item = caiq.line_item and cai.part_id = caiq.part_id");
			query.append(" and caiq.company_id = '").append(inputBean.getCompanyId()).append("' and caiq.request_id = ").append(inputBean.getRequestId()).append(" and caiq.line_item = ").append(inputBean.getLineItem()).append(") where cai.company_id = '").append(inputBean.getCompanyId()).append("' and cai.request_id = ").append(inputBean.getRequestId()).append(" and cai.line_item = ").append(inputBean.getLineItem());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Update mixture data from catalog_add_item_qc to catalog_add_item failed ("+inputBean.getRequestId()+")","See log for details.\n"+e.getMessage());
		}
	}  //end of method

	void setMixtureAndCustomerMsds(CatAddHeaderViewBean inputBean) {
		try {
			StringBuilder query = new StringBuilder("select fx_feature_released('");
			if ("Y".equals(inputBean.getNewMaterial()))
				query.append("AutoGenCustMsdsForMaterial");
			else
				query.append("AutoGenCustMsdsForPart");
			query.append("',carn.eng_eval_facility_id,carn.company_id) released from catalog_add_request_new carn where request_id = ").append(inputBean.getRequestId());
			if ("Y".equals(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
				//update customer_msds_number is null
				query = new StringBuilder("update catalog_add_item set customer_msds_number = 'M'||material_id where customer_msds_number is null and material_id is not null and request_id = ").append(inputBean.getRequestId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

				//need to determine whether line is kit
				query = new StringBuilder("select line_item,part_id,item_id from catalog_add_item where request_id = ").append(inputBean.getRequestId());
				query.append(" order by line_item,part_id");
				genericSqlFactory.setBeanObject(new CatalogAddItemBean());
				Collection dataC = genericSqlFactory.selectQuery(query.toString(),connection);
				Vector kitLines = new Vector(dataC.size());
				Vector lineHasItemId = new Vector(dataC.size());
				if (dataC.size() > 1) {
					Iterator iter = dataC.iterator();
					while (iter.hasNext()) {
						CatalogAddItemBean bean = (CatalogAddItemBean)iter.next();
						if (bean.getPartId().intValue() > 1) {
							if (!kitLines.contains(bean.getLineItem())) {
								kitLines.addElement(bean.getLineItem());
								if (bean.getItemId() != null) {
									lineHasItemId.addElement(new Boolean(true));
								}else {
									lineHasItemId.addElement(new Boolean(false));
								}
							}
						}
					}
				}
				//check to see if allow kit
				if (isFacilityAllowKit(inputBean.getRequestId())) {
					for (int i = 0; i < kitLines.size(); i++) {
						query = new StringBuilder("select count(*) from catalog_add_item");
						query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(kitLines.elementAt(i).toString());
						BigDecimal tempCount = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
						if (tempCount.intValue() > 1) {
							//update customer_mixture_number to sequence TCMISDEV-2286
							query = new StringBuilder("select CUSTOMER_KIT_MSDS_SEQ.nextval from dual");
							BigDecimal tmpVal = genericSqlFactory.getSequence("CUSTOMER_KIT_MSDS_SEQ");
							query = new StringBuilder("update catalog_add_item set customer_mixture_number = '").append(tmpVal).append("' where customer_mixture_number is null and request_id = ").append(inputBean.getRequestId());
							query.append(" and line_item = ").append(kitLines.elementAt(i).toString());
							genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						}
					}
				}
			}  //end of if auto generate customer msds number
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Set mixture and customer MSDS flag failed ("+inputBean.getRequestId()+")","See log for details.\n"+e.getMessage());
		}
	}

	private boolean isFacilityAllowKit(BigDecimal requestId) {
		boolean result = false;
		StringBuilder query = new StringBuilder("select count(*) from catalog_add_request_new carn");
		if ("TCM_OPS".equals(this.getClient()))
			query.append(",customer.facility f");
		else
			query.append(",facility f");
		query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id");
		query.append(" and f.allow_mixture = 'Y' and carn.request_id = ").append(requestId);
		if (!dataCountIsZero(query.toString())) {
			result = true;
		}
		return result;
	} //end of method

	void setRestrictedFlag(CatAddHeaderViewBean inputBean) {
		try {
			if ("Y".equals(inputBean.getHasHmrbTab())) {
				StringBuilder query = new StringBuilder("select count(*) from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
				query.append(" and work_area <> 'All'");
				int dataCount = (new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection))).intValue();
				if (dataCount == 1) {
					query = new StringBuilder("update catalog_add_approval set restricted = 'Y' where approval_role = 'Item QC' and request_id = ").append(inputBean.getRequestId());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Set restricted flag failed ("+inputBean.getRequestId()+")","See log for details.\n"+e.getMessage());
		}
	}

	public String addMsdsApproval(CatAddHeaderViewBean inputBean) throws Exception {
		String result = "OK";
		try {
			Collection cin = new Vector(3);
			cin.add(inputBean.getRequestId());
			cin.add(inputBean.getCompanyId());
			cin.add(inputBean.getEngEvalFacilityId());
			Collection cout = new Vector(1);
			cout.add(new Integer(java.sql.Types.VARCHAR));
			if (log.isDebugEnabled()) {
				log.debug(cin);
			}
			Collection opt = new Vector(2);
			//this is for adding material_id into noncatalog_material
			opt.add("Y");
			//this is so that the procedure can update customer_msds_or_mixture_use start and end date if different
			opt.add("Y");
			Collection procedureData = genericSqlFactory.doProcedure(connection,"Pkg_catalog_planned_add.p_cust_msds_app_insert", cin, cout,opt);
			Iterator i11 = procedureData.iterator();
			while (i11.hasNext()) {
				result = (String) i11.next();
			}
			if (result != null && !"OK".equals(result)) {
				StringBuilder esub = new StringBuilder("Error while calling procedure to add MSDS approval (request #");
				esub.append(inputBean.getRequestId());
				esub.append(")");
				StringBuilder emsg = new StringBuilder("Procedure: Pkg_catalog_planned_add.p_cust_msds_app_insert has an error.\n");
				emsg.append(result);
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
			}
		}catch (Exception ee) {
			result = library.getString("error.db.update");
			log.error(ee);
			StringBuilder esub = new StringBuilder("Error while processing request (request #");
			esub.append(inputBean.getRequestId());
			esub.append(")");
			String emsg = "Procedure: Pkg_catalog_planned_add.p_cust_msds_app_insert throw an exception.\n";
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
			throw ee;
		}
		return result;
	}

	public void pwaTecCatalogAddsExtraProcess(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			StringBuilder query = new StringBuilder("select count(*) from catalog_add_request_new carn where carn.company_id = 'UTC' and carn.catalog_id = 'PWA AM' and");
			query.append(" carn.eng_eval_facility_id = 'PWA- TEC' and carn.request_id = ").append(inputBean.getRequestId());
			if (!dataCountIsZero(query.toString())) {
				//determine whether this is a MRO or chemical request
				query = new StringBuilder("select cat_part_no from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
				String tmpPartNo = genericSqlFactory.selectSingleValue(query.toString(),connection);
				String tmp2 = tmpPartNo.substring(0,8);  // I am getting ABC 1234 from part no
				String[] tmp3 = tmp2.split(" ");
				if (tmp3.length == 2) {
					if ("TEC".equalsIgnoreCase(tmp3[0])) {
						try {
							BigDecimal number2 = new BigDecimal(tmp3[1]);
							if (number2.intValue() >= 5000) {
								//MRO part add it to exception table so that the feed will not change it
								query = new StringBuilder("insert into pwa_catalog_item_id_exception (catalog_item_id,comments) values ('").append(tmpPartNo).append("','Non-MSDS material thru catalog adds')");
								genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
							}else {
								//NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
								query = new StringBuilder("delete from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
								genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
							}
						}catch (Exception ee) {
							//NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
							query = new StringBuilder("delete from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
							genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						}
					}else {
						//NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
						query = new StringBuilder("delete from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					}
				}else {
					//NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
					query = new StringBuilder("delete from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch (Exception e) {
			throw e;
		}
	}

	public boolean createPartNoForRequest(CatAddHeaderViewBean inputBean) throws Exception {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("select count(*) from catalog_add_request_new where (cat_part_no is null or vsize(cat_part_no) < 2 or ascii(cat_part_no) = 160 or cat_part_no like '%?%') and request_id = ").append(inputBean.getRequestId());
			//if cat_part_no is empty
			if (!dataCountIsZero(query.toString())) {
				boolean partSeqRequired = true;
				query = new StringBuilder("select count(*) from catalog_facility cf, catalog_add_request_new carn");
				query.append(" where cf.company_id = carn.company_id and cf.catalog_company_id = carn.catalog_company_id and");
				query.append(" cf.cat_part_add_auto_gen_null_pn = 'N' and cf.catalog_id = carn.catalog_id and cf.facility_id = carn.eng_eval_facility_id and carn.request_id = ").append(inputBean.getRequestId());
				if (!dataCountIsZero(query.toString())) {
					MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Can't automatically create catalog part no","catalog_facility.cat_part_add_auto_gen_null_pn = N for request Id:"+inputBean.getRequestId());
					result = false;
				}else {
					Calendar cal = Calendar.getInstance();
					Integer temp = new Integer(cal.get(cal.YEAR));
					StringBuilder partNo = new StringBuilder("");
					if ("FEC".equalsIgnoreCase(inputBean.getCompanyId())) {
						partNo.append(temp.toString().substring(3)).append("TCM-");
					}else if ("RAYTHEON".equalsIgnoreCase(inputBean.getCompanyId())) {
						partNo.append(temp.toString().substring(2)).append("RAY-");
					}else {
						query = new StringBuilder("select cat_add_part_no_prefix from catalog_facility cf, catalog_add_request_new carn");
						query.append(" where cf.company_id = carn.company_id and cf.catalog_company_id = carn.catalog_company_id and");
						query.append(" cf.catalog_id = carn.catalog_id and cf.facility_id = carn.eng_eval_facility_id and carn.request_id = ").append(inputBean.getRequestId());
						String catAddPartNoPrefix = genericSqlFactory.selectSingleValue(query.toString(),connection);
						//no format or prefix required
						if (StringHandler.isBlankString(catAddPartNoPrefix)) {
							partNo.append(temp.toString().substring(2)).append("TCM-");
						}else {
							//if PWR then it need the following format PWR-XXXXX-XXXX
							if ("PWR".equals(catAddPartNoPrefix)) {
								partNo.append(catAddPartNoPrefix).append("-");
								BigDecimal tmp = new BigDecimal(cal.getTimeInMillis());
								//add XXXXX-XXXX
								partNo.append(tmp.toString().substring(4,9)).append("-").append(tmp.toString().substring(9));
								partSeqRequired = false;
							}else {
								partNo.append(catAddPartNoPrefix).append("-");
							}
						}
					}
					if (partSeqRequired) {
						query = new StringBuilder("select part_seq.nextval from dual");
						BigDecimal partSeq = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
						partNo.append(partSeq.toString());
					}
					query = new StringBuilder("update catalog_add_request_new set cat_part_no = '").append(partNo.toString()).append("', part_group_no = 1 where request_id = ").append(inputBean.getRequestId());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					inputBean.setCatPartNo(partNo.toString());
				}
			}else {
				query = new StringBuilder("update catalog_add_request_new set part_group_no = 1 where part_group_no is null and request_id = ").append(inputBean.getRequestId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
		}catch (Exception e) {
			result = false;
			throw e;
		}
		return result;
	}

	public String deleteRequest(CatAddHeaderViewBean inputBean) throws Exception{
		String result = "OK";
		try {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			if (!engEvalProcess.deleteRequest(inputBean)) {
				result = library.getString("msg.tcmiserror");
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Delete request failed","request Id:"+inputBean.getRequestId());
		}
		return result;
	} //end of method

	public String saveRequestData(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		StringBuilder query = new StringBuilder("");
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			saveHeaderData(inputBean,personnelBean);
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Save request data failed",query.toString());
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	private String saveHeaderData(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) {
		String result = "OK";
		StringBuilder query = new StringBuilder("");
		try {
			query.append("update catalog_add_request_new set last_updated = sysdate,last_changed_by = ").append(personnelBean.getPersonnelId());
			if (!StringHandler.isBlankString(inputBean.getCatPartNo())) {
				query.append(",cat_part_no = ").append(SqlHandler.delimitString(inputBean.getCatPartNo()));
			}else {
				query.append(",cat_part_no = ''");
			}
			query.append(",stocked = ").append(SqlHandler.delimitString(inputBean.getStocked()));
			if (!StringHandler.isBlankString(inputBean.getReplacesPartNo())) {
				query.append(",replaces_part_no = ").append(SqlHandler.delimitString(inputBean.getReplacesPartNo()));
			}else {
				query.append(",replaces_part_no = ''");
			}
			if (!StringHandler.isBlankString(inputBean.getPartDescription())) {
				query.append(",part_description = ").append(SqlHandler.delimitString(inputBean.getPartDescription()));
			}else {
				query.append(",part_description = ''");
			}
			if (!StringHandler.isBlankString(inputBean.getMessageToApprovers())) {
				query.append(",message_to_approvers = ").append(SqlHandler.delimitString(inputBean.getMessageToApprovers()));
			}else {
				query.append(",message_to_approvers = ''");
			}
			if ("Y".equalsIgnoreCase(inputBean.getTimeTempSensitiveYes())) {
				query.append(",time_temp_sensitive = 'Y'");
			}else {
				query.append(",time_temp_sensitive = 'N'");
			}
			if (!StringHandler.isBlankString(inputBean.getRecertInstructions())) {
				query.append(",recert_instructions = ").append(SqlHandler.delimitString(inputBean.getRecertInstructions()));
			}else {
				query.append(",recert_instructions = ''");
			}
			if (inputBean.getMaxRecertNumber() != null) {
				query.append(",max_recert_number = ").append(inputBean.getMaxRecertNumber());
			}else {
				query.append(",max_recert_number = ''");
			}
			if ("Y".equalsIgnoreCase(inputBean.getIncomingTesting())) {
				query.append(",incoming_testing = 'Y'");
			}else {
				query.append(",incoming_testing = 'N'");
			}


			//todo changing data here
			String tmpCatPartAttribute = "";
			String tmpProductionMaterial = "";
			if (!StringHandler.isBlankString(inputBean.getCatPartAttribute1())) {
				tmpCatPartAttribute = inputBean.getCatPartAttribute1();
				if (!StringHandler.isBlankString(inputBean.getLabel1SetCarnProdMatlVal()))
					tmpProductionMaterial = inputBean.getLabel1SetCarnProdMatlVal();
			}
			if (!StringHandler.isBlankString(inputBean.getCatPartAttribute2())) {
				tmpCatPartAttribute = inputBean.getCatPartAttribute2();
				if (!StringHandler.isBlankString(inputBean.getLabel2SetCarnProdMatlVal()))
					tmpProductionMaterial = inputBean.getLabel2SetCarnProdMatlVal();
			}
			query.append(",cat_part_attribute = '").append(tmpCatPartAttribute).append("'");
			query.append(",production_material = '").append(tmpProductionMaterial).append("'");

			if (!StringHandler.isBlankString(inputBean.getQualityId())) {
				query.append(",quality_id = ").append(SqlHandler.delimitString(inputBean.getQualityId()));
			}else {
				query.append(",quality_id = ''");
			}
			//todo end of changing data


			if (!StringHandler.isBlankString(inputBean.getCustomerPartRevision())) {
				query.append(",customer_part_revision = ").append(SqlHandler.delimitString(inputBean.getCustomerPartRevision()));
			}else {
				query.append(",customer_part_revision = ''");
			}
			//saving data in catalog_add_request_new
			query.append(" where request_id = ").append(inputBean.getRequestId());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//for those facilities that uses material category
			saveMaterialCategorySubcategoryData(inputBean,personnelBean);
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Save header data failed",query.toString());
		}
		return result;
	} //end of method

	private void saveMaterialCategorySubcategoryData(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) {
		StringBuilder query = new StringBuilder("");
		try {
			//save data if it's not New Work Area Approval and Modify QPL
			if ("Y".equals(inputBean.getHasMaterialCategoryOption()) && inputBean.getStartingView().intValue() != 3 &&
					inputBean.getStartingView().intValue() != 4) {
				//first delete current data if exist
				query.append("delete from catalog_add_planned_use where request_id = ").append(inputBean.getRequestId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//now insert data back
				if (!StringHandler.isBlankString(inputBean.getMaterialSubcategoryId())) {
					//use_seq.nextval
					query = new StringBuilder("insert into catalog_add_planned_use (planned_id,company_id,request_id,material_subcategory_id");
					query.append(",fly_away_with_aircraft,thinned_when_used,shift_1,shift_2,shift_3,saturday,sunday,gt_54_gal)");
					query.append(" select use_seq.nextval,'").append(inputBean.getCompanyId()).append("',").append(inputBean.getRequestId());
					query.append(",").append(inputBean.getMaterialSubcategoryId());
					query.append(",null,null,null,null,null,null,null,null from dual");
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch(Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Save material category and subcategory data failed",query.toString());
		}
	}  //end of method

	public void reloadUseApprovalData(CatAddHeaderViewBean bean, PersonnelBean personnelBean) throws Exception{
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(bean.getRequestId());
			CatAddHeaderViewBean catAddHeaderViewBean = headerColl.get(0);
			BeanHandler.copyAttributes(catAddHeaderViewBean, bean);
			//if request is pending approval then get all approvers who can approve this request
			getApproverForRequest(bean,catAddHeaderViewBean.getRequestId(),personnelBean);
			//set view level
			calculateViewLevel(bean,personnelBean);
			//can edit use approval
			canEditUseApproval(bean,personnelBean);
			//specific_use_approval_required
			specificUseApprovalRequired(bean);

			getUseApprovalData(bean, personnelBean);
			//get list of inventory group for facility
			bean.setInventoryGroupColl(getFacilityInventoryGroup(bean));
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	} //end of method

	public void deleteUseApprovalLine(CatAddHeaderViewBean inputBean) {
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("delete from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
			query.append(" and work_area = ").append(SqlHandler.delimitString(inputBean.getEngEvalWorkArea()));
			factory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public CatalogAddUserGroupBean getUseApproval(CatAddHeaderViewBean inputBean) throws BaseException {
		CatalogAddUserGroupBean bean = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			Collection<FacAppEmissionPointBean> epOptionColl = getEmissionPointColl(inputBean.getEngEvalFacilityId());
			if ("newWorkArea".equalsIgnoreCase(inputBean.getCalledFrom())) {
				bean = new CatalogAddUserGroupBean();
				bean.setRequestId(inputBean.getRequestId());
				bean.setCompanyId(inputBean.getCompanyId());
				bean.setFacilityId(inputBean.getEngEvalFacilityId());
				bean.setHasApplicationUseGroup(inputBean.getHasApplicationUseGroup());
				bean.setWorkAreaColl(getWorkAreaForFacility(inputBean,bean.getHasApplicationUseGroup()));
				bean.setUserGroupColl(getApprovalUserGroupData(inputBean));
				bean.setInventoryGroupColl(getFacilityInventoryGroup(inputBean));

				boolean isUnique = true;
				for (int i = 0; i < emissionPointsIdSeparators.length(); i++) {
					String curSeparator = emissionPointsIdSeparators.substring(i, i + 1);
					for (FacAppEmissionPointBean epBean : epOptionColl)
						if (epBean.getAppEmissionPoint().contains(curSeparator) || epBean.getFacEmissionPoint().contains(curSeparator)) {
							isUnique = false;
							break;
						}
					
					if (isUnique) {
						bean.setEmissionPointIdSeparator(curSeparator);
						break;
					}
				}
				if (bean.getEmissionPointIdSeparator() == null)
					bean.setEmissionPointIdSeparator(emissionPointsIdSeparators);
			} else {
				bean = getEditUseApproval(inputBean);
			}

			bean.setEmissionPointColl(epOptionColl);
			
			//specific_use_approval_required
			specificUseApprovalRequired(inputBean);
			bean.setSpecificUseApprovalRequired(inputBean.getSpecificUseApprovalRequired());
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return bean;
	}

	private CatalogAddUserGroupBean getEditUseApproval(CatAddHeaderViewBean inputBean) throws Exception {
		CatalogAddUserGroupBean bean = null;
		try {
			genericSqlFactory.setBeanObject(new CatalogAddUserGroupBean());
			StringBuilder query = new StringBuilder("select * from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
			query.append(" and work_area = '").append(inputBean.getEngEvalWorkArea()).append("'");
			Collection data = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = data.iterator();
			while (iter.hasNext()) {
				bean = (CatalogAddUserGroupBean)iter.next();
				break;
			}
			if (bean == null) {
				bean = new CatalogAddUserGroupBean();
				bean.setRequestId(inputBean.getRequestId());
				bean.setHasApplicationUseGroup("N");
				bean.setWorkAreaColl(new Vector(0));
				bean.setUserGroupColl(new Vector(0));
				bean.setInventoryGroupColl(new Vector(0));
			}else {
				inputBean.setCompanyId(bean.getCompanyId());
				inputBean.setEngEvalFacilityId(bean.getFacilityId());
				bean.setHasApplicationUseGroup(inputBean.getHasApplicationUseGroup());
				bean.setWorkAreaColl(getWorkAreaForFacility(inputBean,bean.getHasApplicationUseGroup()));
				bean.setUserGroupColl(getApprovalUserGroupData(inputBean));
				bean.setInventoryGroupColl(getFacilityInventoryGroup(inputBean));
			}
			
			bean.setEmissionPoints(inputBean.getEmissionPoints());
			bean.setEmissionPointIdSeparator(inputBean.getEmissionPointIdSeparator());
		}catch (Exception e) {
			log.error(e);
		}
		return bean;
	}

	private void hasApplicationUseGroup(CatAddHeaderViewBean bean) {
		try {
			StringBuilder countQuery = new StringBuilder("select count(*) from facility where company_id = '").append(bean.getCompanyId()).append("'");
			countQuery.append(" and facility_id = '").append(bean.getEngEvalFacilityId()).append("' and use_code_required = 'Y'");
			if (!dataCountIsZero(countQuery.toString())) {
				bean.setHasApplicationUseGroup("Y");
			}else {
				bean.setHasApplicationUseGroup("N");
			}
		}catch (Exception e) {
			bean.setHasApplicationUseGroup("N");
			log.error(e);
		}
	}

	private Collection getUseGroupData(CatAddHeaderViewBean bean) {
		Collection result = new Vector();
		try {
			StringBuilder query = new StringBuilder("select * from application_use_group where (company_id,facility_id,application_use_group_id) in (");
			query.append(" select vus.company_id,vus.facility_id,application_use_group_id from catalog_add_planned_use capu,vv_usage_subcategory vus");
			query.append(" where capu.company_id = vus.company_id and capu.usage_subcategory_id = vus.usage_subcategory_id and capu.request_id = ").append(bean.getRequestId());
			query.append(" union all ");
			query.append("select vus.company_id,vus.facility_id,application_use_group_id from catalog_add_request_new carn, fac_part_use_code fpuc, vv_usage_subcategory vus");
			query.append(" where carn.request_id = ").append(bean.getRequestId());
			query.append(" and carn.company_id = fpuc.company_id and carn.catalog_company_id = fpuc.catalog_company_id");
			query.append(" and carn.catalog_id = fpuc.catalog_id and carn.eng_eval_facility_id = fpuc.facility_id");
			query.append(" and carn.cat_part_no = fpuc.cat_part_no and carn.part_group_no = fpuc.part_group_no");
			query.append(" and carn.company_id = vus.company_id and carn.eng_eval_facility_id = vus.facility_id");
			query.append(" and fpuc.usage_subcategory_id = vus.usage_subcategory_id) order by application_use_group_id");
			genericSqlFactory.setBeanObject(new ApplicationUseGroupBean());
			result = genericSqlFactory.selectQuery(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	private Collection getWorkAreaForFacility(CatAddHeaderViewBean bean, String hasApplicationUseGroup) throws Exception {
		Vector result = new Vector();
		StringBuilder query = new StringBuilder("select * from table (pkg_catalog_add.FX_FAC_PART_APP_IG ('").append(bean.getCompanyId());
		query.append("','").append(bean.getEngEvalFacilityId()).append("','").append(bean.getCatalogCompanyId()).append("','").append(bean.getCatalogId());
		query.append("','").append(bean.getCatPartNo()).append("',").append(bean.getPartGroupNo()).append(",'','A'))");
		Collection useGroupDataColl = new Vector();
		if ("Y".equals(hasApplicationUseGroup)) {
			//getting use group for request
			useGroupDataColl = getUseGroupData(bean);
			if (useGroupDataColl.size() > 0) {
				query.append(" where application_use_group_id in (");
				Iterator iter = useGroupDataColl.iterator();
				int count = 0;
				while (iter.hasNext()) {
					ApplicationUseGroupBean dataBean = (ApplicationUseGroupBean) iter.next();
					if (StringHandler.isBlankString(dataBean.getApplicationUseGroupId())) continue;
					if (count == 0) {
						query.append(SqlHandler.delimitString(dataBean.getApplicationUseGroupId()));
					}else {
						query.append(",").append(SqlHandler.delimitString(dataBean.getApplicationUseGroupId()));
					}
					count++;
				}
				query.append(")");
			}
		}
		query.append(" order by application_use_group_name,application_desc,inventory_group_name");
		genericSqlFactory.setBeanObject(new ApplicationUseGroupBean());
		Collection data = genericSqlFactory.selectQuery(query.toString(),connection);
		if ("Y".equals(hasApplicationUseGroup)) {
			Iterator iter = data.iterator();
			String lastApplicationUseGroupId = "";
			Vector useGroupWithMember = new Vector(useGroupDataColl.size());
			while (iter.hasNext()) {
				ApplicationUseGroupBean dataBean = (ApplicationUseGroupBean) iter.next();
				//skip if work area doesn't not member of a group
				if (StringHandler.isBlankString(dataBean.getApplicationUseGroupId())) continue;

				if (lastApplicationUseGroupId.equals(dataBean.getApplicationUseGroupId())) {
					ApplicationUseGroupBean resultBean = (ApplicationUseGroupBean)result.lastElement();
					Collection innerColl = resultBean.getApplicationColl();
					ApplicationUseGroupBean resultChildBean = new ApplicationUseGroupBean();
					BeanHandler.copyAttributes(dataBean,resultChildBean);
					innerColl.add(resultChildBean);
				}else {
					ApplicationUseGroupBean resultBean = new ApplicationUseGroupBean();
					resultBean.setApplicationUseGroupId(dataBean.getApplicationUseGroupId());
					resultBean.setApplicationUseGroupName(dataBean.getApplicationUseGroupName());
					resultBean.setApplicationUseGroupDesc(dataBean.getApplicationUseGroupDesc());
					Collection innerColl = new Vector();
					ApplicationUseGroupBean resultChildBean = new ApplicationUseGroupBean();
					BeanHandler.copyAttributes(dataBean,resultChildBean);
					innerColl.add(resultChildBean);
					resultBean.setApplicationColl(innerColl);
					result.addElement(resultBean);
					useGroupWithMember.addElement(dataBean.getApplicationUseGroupId());
				}
				lastApplicationUseGroupId = dataBean.getApplicationUseGroupId();
			}
			//check to see if all use group for request has member, if not set to all
			Vector useGroupWithNoMember = new Vector(useGroupDataColl.size());
			Iterator iter2 = useGroupDataColl.iterator();
			while (iter2.hasNext()) {
				ApplicationUseGroupBean dataBean = (ApplicationUseGroupBean) iter2.next();
				boolean isMember = false;
				for (int i = 0; i < useGroupWithMember.size(); i++) {
					if (dataBean.getApplicationUseGroupId().equals((String)useGroupWithMember.elementAt(i))) {
						isMember = true;
						break;
					}
				}
				if (!isMember) {
					ApplicationUseGroupBean dBean = new ApplicationUseGroupBean();
					dBean.setApplicationUseGroupId(dataBean.getApplicationUseGroupId());
					dBean.setApplicationUseGroupName(dataBean.getApplicationUseGroupName());
					dBean.setApplicationUseGroupDesc(dataBean.getApplicationUseGroupDesc());
					//don't pass any work area to jsp because is will default to "All Work Areas"
					Collection innerColl = new Vector(0);
					dBean.setApplicationColl(innerColl);
					result.addElement(dBean);
				}
			}
		}else {
			ApplicationUseGroupBean dataBean = new ApplicationUseGroupBean();
			dataBean.setApplicationUseGroupId("All");
			dataBean.setApplicationUseGroupName("All");
			dataBean.setApplicationColl(data);
			result.addElement(dataBean);
		}
		return result;
	} //end of method

	public String submitUseApproval(CatalogAddUserGroupBean inputBean, String calledFrom) throws Exception {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			if ("newWorkArea".equalsIgnoreCase(calledFrom)) {
				StringBuilder query = new StringBuilder("select count(*) from catalog_add_user_group where request_id = ").append(inputBean.getRequestId());
				query.append(" and work_area = ").append(SqlHandler.delimitString(inputBean.getWorkArea()));
				if (!StringHandler.isBlankString(inputBean.getHasApplicationUseGroup())) {
					query.append(" and application_use_group_id = '").append(inputBean.getApplicationUseGroupId()).append("'");
				}
				if(dataCountIsZero(query.toString())) {
					result = submitNewUseApproval(inputBean);
				}else {
					result = submitEditUseApproval(inputBean);
				}
			}else {
				result = submitEditUseApproval(inputBean);
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	private String submitNewUseApproval(CatalogAddUserGroupBean inputBean) {
		String result = "OK";
		StringBuilder query = new StringBuilder("insert into catalog_add_user_group (request_id,company_id,facility_id,work_area,user_group_id,process_desc,");
		try {
			query.append("quantity_1,per_1,period_1,quantity_2,per_2,period_2,charge_number,approval_status,estimated_annual_usage,inventory_group,application_use_group_id,waste_water_discharge)");
			query.append(" values (").append(inputBean.getRequestId()).append(",").append(SqlHandler.delimitString(inputBean.getCompanyId())).append(",").append(SqlHandler.delimitString(inputBean.getFacilityId()));
			query.append(",").append(SqlHandler.delimitString(inputBean.getWorkArea()));
			query.append(",").append(SqlHandler.delimitString(inputBean.getUserGroupId()));
			query.append(",").append(SqlHandler.delimitString(inputBean.getProcessDesc()));
			if (inputBean.getQuantity1() != null) {
				query.append(",").append(inputBean.getQuantity1());
				query.append(",").append(inputBean.getPer1());
				query.append(",'").append(inputBean.getPeriod1()).append("'");
			}else {
				query.append(",''");
				query.append(",''");
				query.append(",''");
			}
			if (inputBean.getQuantity2() != null) {
				query.append(",").append(inputBean.getQuantity2());
				query.append(",").append(inputBean.getPer2());
				query.append(",'").append(inputBean.getPeriod2()).append("'");
			}else {
				query.append(",''");
				query.append(",''");
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getChargeNumber())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getChargeNumber()));
			}else {
				query.append(",''");
			}
			query.append(",").append(SqlHandler.delimitString(inputBean.getApprovalStatus()));
			if (inputBean.getEstimatedAnnualUsage() != null) {
				query.append(",").append(inputBean.getEstimatedAnnualUsage());
			}else {
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getInventoryGroup()));
			}else {
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getApplicationUseGroupId())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getApplicationUseGroupId()));
			}else {
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getWasteWaterDischarge())) {
				query.append(",").append(SqlHandler.delimitString(inputBean.getWasteWaterDischarge()));
			}else {
				query.append(",''");
			}

			query.append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			
			updateEmissionPoints(inputBean);
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit Add Work Area failed",query.toString());
		}
		return result;
	}

	private boolean dataCountIsZero(String query) {
		boolean result = false;
		try {
			String tmpVal = genericSqlFactory.selectSingleValue(query,connection);
			if ("0".equalsIgnoreCase(tmpVal)) {
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Testing count error",query);
		}
		return result;
	}

	private String submitEditUseApproval(CatalogAddUserGroupBean inputBean) {
		String result = "OK";
		StringBuilder query = new StringBuilder("update catalog_add_user_group set");
		try {
			query.append(" work_area = ").append(SqlHandler.delimitString(inputBean.getWorkArea()));
			query.append(", user_group_id = ").append(SqlHandler.delimitString(inputBean.getUserGroupId()));
			query.append(", process_desc = ").append(SqlHandler.delimitString(inputBean.getProcessDesc()));
			if (inputBean.getQuantity1() != null) {
				query.append(", quantity_1 = ").append(inputBean.getQuantity1());
				query.append(", per_1 = ").append(inputBean.getPer1());
				query.append(", period_1 = '").append(inputBean.getPeriod1()).append("'");
			}else {
				query.append(", quantity_1 = ''");
				query.append(", per_1 = ''");
				query.append(", period_1 = ''");
			}
			if (inputBean.getQuantity2() != null) {
				query.append(", quantity_2 = ").append(inputBean.getQuantity2());
				query.append(", per_2 = ").append(inputBean.getPer2());
				query.append(", period_2 = '").append(inputBean.getPeriod2()).append("'");
			}else {
				query.append(", quantity_2 = ''");
				query.append(", per_2 = ''");
				query.append(", period_2 = ''");
			}
			if (!StringHandler.isBlankString(inputBean.getChargeNumber())) {
				query.append(", charge_number = ").append(SqlHandler.delimitString(inputBean.getChargeNumber()));
			}else {
				query.append(", charge_number = ''");
			}
			query.append(", approval_status = ").append(SqlHandler.delimitString(inputBean.getApprovalStatus()));
			if (inputBean.getEstimatedAnnualUsage() != null) {
				query.append(", estimated_annual_usage = ").append(inputBean.getEstimatedAnnualUsage());
			}else {
				query.append(", estimated_annual_usage = ''");
			}
			if (!StringHandler.isBlankString(inputBean.getInventoryGroup())) {
				query.append(", inventory_group = ").append(SqlHandler.delimitString(inputBean.getInventoryGroup()));
			}else {
				query.append(", inventory_group = ''");
			}
			if (!StringHandler.isBlankString(inputBean.getWasteWaterDischarge())) {
				query.append(", waste_water_discharge = ").append(SqlHandler.delimitString(inputBean.getWasteWaterDischarge()));
			}else {
				query.append(", waste_water_discharge = ''");
			}
			query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and work_area = ").append(SqlHandler.delimitString(inputBean.getWorkArea()));
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			
			updateEmissionPoints(inputBean);
		} catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit Add Work Area failed",query.toString());
		}
		return result;
	}

	public Collection getSubmitCatalogItemData(BigDecimal requestId, BigDecimal lineItem) throws Exception{
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatPartQplViewBean());
		StringBuilder query = new StringBuilder(getItemForRequest(requestId));
		query.append(" and line_item = ").append(lineItem).append(" order by part_id");
		return factory.selectQuery(query.toString());
	} //end of method

	public void deleteLines(BigDecimal requestId, BigDecimal lineItem, BigDecimal partId, Collection<CatalogAddItemBean> deleteCatAddItemBeans) {
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			if(partId != null)
			{
				StringBuilder query = new StringBuilder("delete from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem).append(" and part_id = ").append(partId);
				factory.deleteInsertUpdate(query.toString());
			}
			else if(deleteCatAddItemBeans != null)
				for(CatalogAddItemBean b :deleteCatAddItemBeans)
				{
					StringBuilder query = new StringBuilder("delete from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem).append(" and part_id = ").append(b.getPartId());
					factory.deleteInsertUpdate(query.toString());
				}
			else
			{
				StringBuilder query = new StringBuilder("delete from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
				factory.deleteInsertUpdate(query.toString());
			}
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public void rejectLine(BigDecimal requestId, BigDecimal lineItem, PersonnelBean personnelBean, String comments) {
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("update catalog_add_item set line_status = 'Rejected', rejected_date = sysdate, rejected_by = ").append(personnelBean.getPersonnelId());
			if (!StringHandler.isBlankString(comments)) {
				query.append(", rejected_comment = ").append(SqlHandler.delimitString(comments));
			}
			query.append(" where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
			factory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public BigDecimal newWorkAreaApproval(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			createNewCatalogAddRequestNew(bean,personnelBean,result,"useApproval");
			//copy data from cpig and part to catalog_add_item
			query = new StringBuilder("select item_id from catalog_add_request_new carn, catalog_part_item_group cpig");
			query.append(" where carn.catalog_company_id = cpig.company_id and carn.catalog_id = cpig.catalog_id");
			query.append(" and carn.cat_part_no = cpig.cat_part_no and carn.part_group_no = cpig.part_group_no");
			query.append(" and cpig.status in ('A','D') and carn.request_id = ").append(result);
			genericSqlFactory.setBeanObject(new CatalogInputBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			int lineItem = 1;
			while (iter.hasNext()) {
				CatalogInputBean tmpBean = (CatalogInputBean)iter.next();
				createCatalogAddItemFromGivenItem(result,new BigDecimal(lineItem++),tmpBean.getItemId(),new BigDecimal(NEW_WORK_AREA_APPROVAL));
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public BigDecimal newPartApprovalCode(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			createNewCatalogAddRequestNew(bean,personnelBean,result,"newPartApprovalCode");
			//copy data from cpig and part to catalog_add_item
			query = new StringBuilder("select item_id from catalog_add_request_new carn, catalog_part_item_group cpig");
			query.append(" where carn.catalog_company_id = cpig.company_id and carn.catalog_id = cpig.catalog_id");
			query.append(" and carn.cat_part_no = cpig.cat_part_no and carn.part_group_no = cpig.part_group_no");
			query.append(" and cpig.status in ('A','D') and carn.request_id = ").append(result);
			genericSqlFactory.setBeanObject(new CatalogInputBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			int lineItem = 1;
			while (iter.hasNext()) {
				CatalogInputBean tmpBean = (CatalogInputBean)iter.next();
				createCatalogAddItemFromGivenItem(result,new BigDecimal(lineItem++),tmpBean.getItemId(),new BigDecimal(NEW_PART_APPROVAL_CODE));
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public BigDecimal newPartFromExistingPart(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			createNewCatalogAddRequestNew(bean,personnelBean,result,"newPart");
			//copy data from cpig and part to catalog_add_item
			query = new StringBuilder("select item_id from catalog_part_item_group");
			query.append(" where company_id = '").append(bean.getCatalogAddCatalogCompanyId()).append("' and catalog_id = '").append(bean.getCatalogAddCatalogId());
			query.append("' and cat_part_no = ").append(SqlHandler.delimitString(bean.getCatalogAddCatPartNo()));
			query.append(" and part_group_no = ").append(bean.getCatalogAddPartGroupNo()).append(" and status in ('A','D')");
			genericSqlFactory.setBeanObject(new CatalogInputBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			int lineItem = 1;
			while (iter.hasNext()) {
				CatalogInputBean tmpBean = (CatalogInputBean)iter.next();
				createCatalogAddItemFromGivenItem(result,new BigDecimal(lineItem),tmpBean.getItemId(),new BigDecimal(MODIFY_QPL));

				//copy data from fac_part_use_code or customer_msds_or_mixture_use
				copyApprovedHrmbToRequest(bean.getCompanyId(),bean.getFacilityId(),result, lineItem,personnelBean,"catalogAddMsdsRequest");

				//increase line_item
				lineItem++;
			}

			//copy data from fac_spec to catalog_add_spec
			query = new StringBuilder("insert into catalog_add_spec (request_id,spec_id,spec_name,spec_title,spec_version,spec_amendment,spec_date,content,on_line,company_id,spec_library,coc,coa,line_item,itar,spec_source)");
			query.append(" select ").append(result).append(" request_id,s.spec_id,s.spec_name,s.spec_title,s.spec_version,s.spec_amendment,");
			query.append("s.spec_date,s.content,s.on_line,'").append(bean.getCompanyId()).append("',s.spec_library,fs.coc,fs.coa,rownum,s.itar,'spec'");
			query.append(" from fac_spec fs, spec s where fs.company_id = s.company_id and fs.spec_id = s.spec_id and fs.spec_library = s.spec_library");
			query.append(" and fs.company_id = '").append(bean.getCatalogAddCatalogCompanyId()).append("' and fs.facility_id = '").append(bean.getCatalogAddCatalogId());
			query.append("' and fs.fac_part_no = ").append(SqlHandler.delimitString(bean.getCatalogAddCatPartNo()));
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//copy data from fac_item_flow_down to catalog_add_item_flow_down
			query = new StringBuilder("insert into catalog_add_flow_down (request_id,flow_down,catalog_id,company_id)");
			query.append(" select ").append(result).append(" request_id,flow_down,catalog_id,'").append(bean.getCompanyId()).append("'");
			query.append(" from fac_item_flow_down where company_id = '").append(bean.getCatalogAddCatalogCompanyId()).append("'");
			query.append(" and catalog_id = ").append(SqlHandler.delimitString(bean.getCatalogId()));
			query.append(" and fac_part_no = ").append(SqlHandler.delimitString(bean.getCatalogAddCatPartNo()));
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public BigDecimal newPartFromExistingItem(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			createNewCatalogAddRequestNew(bean,personnelBean,result,"newPart");
			//create catalog_add_item from given item
			createCatalogAddItemFromGivenItem(result,new BigDecimal(1),bean.getCatalogAddItemId(),new BigDecimal(MODIFY_QPL));

			//copy data from fac_part_use_code or customer_msds_or_mixture_use
			copyApprovedHrmbToRequest(bean.getCompanyId(),bean.getFacilityId(),result,1,personnelBean,"catalogAddMsdsRequest");
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public BigDecimal newPartFromExistingItemModifyPkg(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			createNewCatalogAddRequestNew(bean,personnelBean,result,"newPart");
			//copy from passed in item id
			query = copyCatalogAddItemByItem(result,new BigDecimal(1),bean.getCatalogAddItemId(),new BigDecimal(NEW_SIZE_PACKAGING),new BigDecimal(0));
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

			//update cai.customer_msds_number
			updateCustomerMsdsNumberFromXref(result,new BigDecimal(1));
			//copy data from fac_part_use_code or customer_msds_or_mixture_use
			copyApprovedHrmbToRequest(bean.getCompanyId(),bean.getFacilityId(),result,1,personnelBean,"catalogAddMsdsRequest");
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public BigDecimal newPart(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			String query = "select request_seq.nextval from dual";
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query,connection));
			createNewCatalogAddRequestNew(bean,personnelBean,result,"newPart");

		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public BigDecimal modifyQpl(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			String query = "select request_seq.nextval from dual";
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query,connection));
			createNewCatalogAddRequestNew(bean,personnelBean,result,"modifyQpl");
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public void createNewCatalogAddRequestNew(CatalogInputBean inputBean, PersonnelBean personnelBean, BigDecimal requestId, String startingPoint) {
		StringBuilder query = null;
		try {
			query = new StringBuilder("insert into catalog_add_request_new (request_id,requestor,request_date,catalog_id,");
			query.append("eng_eval_facility_id,catalog_company_id,eng_eval_work_area,account_sys_id,engineering_evaluation,company_id,");
			query.append("inventory_group,request_status,starting_view,cat_part_no,part_group_no,part_description,stocked");
			query.append(",incoming_testing,time_temp_sensitive,recert_instructions,max_recert_number,quality_id,production_material,cat_part_attribute,approval_path");
			query.append(")");
			query.append(" values ("+requestId+","+personnelBean.getPersonnelId()+",sysdate,'"+inputBean.getCatalogAddCatalogId()+"','");
			query.append(inputBean.getFacilityId()).append("','").append(inputBean.getCatalogAddCatalogCompanyId()).append("',null,");
			query.append("null,'N','").append(inputBean.getCompanyId()).append("'");
			if ("new".equalsIgnoreCase(startingPoint)) {
				query.append(",null,0,0,null,null,null,'OOR',null,'Y',null,null,null,null,null,'Part')");
			}else if ("newSize".equalsIgnoreCase(startingPoint)) {
				query.append(",null,1,1,null,null,null,'OOR',null,'Y',null,null,null,null,null,'Part')");
			}else if ("newPart".equalsIgnoreCase(startingPoint)) {
				query.append(",null,2,2,null,null,null,'OOR',null,'Y',null,null,null,null,null,'Part')");
			}else if ("useApproval".equalsIgnoreCase(startingPoint)) {
				query.append(",'").append(inputBean.getCatalogAddInventoryGroup()).append("'");
				query.append(",3,3,'").append(inputBean.getCatalogAddCatPartNo()).append("',").append(inputBean.getCatalogAddPartGroupNo());
				query.append(",").append(SqlHandler.delimitString(inputBean.getCatalogAddPartDesc()));
				String tmpStocked = "OOR";
				if (inputBean.getCatalogAddStocked().indexOf("MM") > -1) {
					tmpStocked = "MM";
				}
				query.append(",'").append(tmpStocked).append("'");
				setDefinedPartData(inputBean.getCatalogAddCatalogCompanyId(),inputBean.getCatalogAddCatalogId(),inputBean.getCatalogAddCatPartNo(),query);
				query.append(",'Part')");
			}else if ("modifyQpl".equalsIgnoreCase(startingPoint)) {
				query.append(",'").append(inputBean.getCatalogAddInventoryGroup()).append("'");
				query.append(",4,4,'").append(inputBean.getCatalogAddCatPartNo()).append("',").append(inputBean.getCatalogAddPartGroupNo());
				query.append(",").append(SqlHandler.delimitString(inputBean.getCatalogAddPartDesc()));
				String tmpStocked = "OOR";
				if (inputBean.getCatalogAddStocked().indexOf("MM") > -1) {
					tmpStocked = "MM";
				}
				query.append(",'").append(tmpStocked).append("'");
				setDefinedPartData(inputBean.getCatalogAddCatalogCompanyId(),inputBean.getCatalogAddCatalogId(),inputBean.getCatalogAddCatPartNo(),query);
				query.append(",'Part')");
			}else if ("newMsds".equalsIgnoreCase(startingPoint)) {
				query.append(",null,14,6,null,null,null,'OOR',null,'Y',null,null,null,null,null,'MSDS')");
			}else if ("newApprovalCode".equalsIgnoreCase(startingPoint)) {
				query.append(",null,15,7,null,null,null,'OOR',null,'Y',null,null,null,null,null,'MSDS')");
			}else if ("newPartApprovalCode".equalsIgnoreCase(startingPoint)) {
				query.append(",'").append(inputBean.getCatalogAddInventoryGroup()).append("'");
				query.append(",15,8,'").append(inputBean.getCatalogAddCatPartNo()).append("',").append(inputBean.getCatalogAddPartGroupNo());
				query.append(",").append(SqlHandler.delimitString(inputBean.getCatalogAddPartDesc()));
				String tmpStocked = "OOR";
				if (inputBean.getCatalogAddStocked().indexOf("MM") > -1) {
					tmpStocked = "MM";
				}
				query.append(",'").append(tmpStocked).append("'");
				setDefinedPartData(inputBean.getCatalogAddCatalogCompanyId(),inputBean.getCatalogAddCatalogId(),inputBean.getCatalogAddCatPartNo(),query);
				query.append(",'Part')");
			}else if ("sdsAuthoring".equalsIgnoreCase(startingPoint)) {
				query.append(",null,14,9,null,null,null,'OOR',null,'Y',null,null,null,null,null,'MSDS')");
			}else {
				query.append("0,0,null,null,null,'OOR',null,'Y',null,null,null,null,null,'Part')");
			}
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Create New Catalog Add Request New failed",query.toString());
		}
	} //end of method

	private void setDefinedPartData(String catalogCompanyId, String catalogId, String catPartNo, StringBuilder updateQuery) {
		StringBuilder query = new StringBuilder("");
		try {
			genericSqlFactory.setBeanObject(new FacItemBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, catalogCompanyId);
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, catalogId);
			criteria.addCriterion("facPartNo", SearchCriterion.EQUALS, catPartNo);
			Collection result = genericSqlFactory.select(criteria, null, connection, "fac_item");
			Iterator iter = result.iterator();
			while(iter.hasNext()) {
				FacItemBean bean = (FacItemBean)iter.next();
				if (!StringHandler.isBlankString(bean.getIncomingTesting())) {
					updateQuery.append(",").append(SqlHandler.delimitString(bean.getIncomingTesting()));
				}else {
					updateQuery.append(",null");
				}
				if (!StringHandler.isBlankString(bean.getTimeTempSensitive())) {
					updateQuery.append(",'").append(bean.getTimeTempSensitive()).append("'");
				}else {
					updateQuery.append(",'N'");
				}
				if (!StringHandler.isBlankString(bean.getRecertInstructions())) {
					updateQuery.append(",").append(SqlHandler.delimitString(bean.getRecertInstructions()));
				}else {
					updateQuery.append(",null");
				}
				if (bean.getMaxRecertNumber() != null) {
					updateQuery.append(",").append(bean.getMaxRecertNumber());
				}else {
					updateQuery.append(",null");
				}

				if (!StringHandler.isBlankString(bean.getQualityId())) {
					updateQuery.append(",").append(SqlHandler.delimitString(bean.getQualityId()));
				}else {
					updateQuery.append(",null");
				}
				if (!StringHandler.isBlankString(bean.getProductionMaterial())) {
					updateQuery.append(",").append(SqlHandler.delimitString(bean.getProductionMaterial()));
				}else {
					updateQuery.append(",null");
				}
				if (!StringHandler.isBlankString(bean.getCatPartAttribute())) {
					updateQuery.append(",").append(SqlHandler.delimitString(bean.getCatPartAttribute()));
				}else {
					updateQuery.append(",null");
				}
				break;
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","getDefinedPartData() failed",query.toString());
		}
	}

	public void getApproverForRequest(CatAddHeaderViewBean bean, BigDecimal requestId, PersonnelBean personnelBean) {
		try {
			//if request is pending approval then get all approvers who can approve this request
			StringBuilder query =  new StringBuilder("select count(*) from");
			if ("TCM_OPS".equals(getClient()))
				query.append(" vv_catalog_add_request_status cars,");
			else
				query.append(" vv_catalog_add_request_status$ cars,");
			query.append(" catalog_add_request_new carn");
			query.append(" where cars.company_id = carn.company_id and cars.request_status = carn.request_status and cars.approval_group > 0");
			query.append(" and carn.request_id = ").append(bean.getRequestId());
			if (!dataCountIsZero(query.toString())) {
				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
				engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
				bean.setApproverColl(engEvalProcess.getChemicalApprovers(requestId,personnelBean));
				bean.setApprovalCommentColl(engEvalProcess.getApprovalComments(requestId,personnelBean));
				//check to see if only one approval role left
				//lastApprovalRole(bean);
			}else {
				bean.setApproverColl(new Vector(0));
				bean.setApprovalCommentColl(new Vector(0));
				bean.setLastApprovalRole("N");
			}
		}catch (Exception e) {
			log.error(e);
		}
	}

	//this method will handle most case but not all.  For example, it cannot handle if approval role can be auto approved
	private void lastApprovalRole(CatAddHeaderViewBean bean) {
		try {
			//if any active work areas has specific_use_approval_required = Y then it applied
			StringBuilder query =  new StringBuilder("select count(*) from (");
			query.append("select car.approval_role from vv_chemical_approval_role car, catalog_add_request_new carn");
			query.append(" where car.company_id = carn.company_id and car.facility_id = carn.eng_eval_facility_id");
			query.append(" and carn.catalog_company_id = carn.catalog_company_id and car.catalog_id = carn.catalog_id");
			query.append(" and  ( (carn.approval_path = 'MSDS+Part' and ( car.msds_approval = 'Y' or car.part_approval = 'Y'))");
			query.append(" or (carn.approval_path = 'MSDS' and car.msds_approval = 'Y')");
			query.append(" or (carn.approval_path = 'Part' and car.part_approval = 'Y'))");
			query.append(" and car.active = 'Y' and carn.request_id = ").append(bean.getRequestId());
			query.append(" minus");
			query.append(" select approval_role from catalog_add_approval where request_id = ").append(bean.getRequestId());
			query.append(")");
			String tmpCount = genericSqlFactory.selectSingleValue(query.toString(),connection);
			//the reason for comparing count with size is that users can be an approver for all of the remaining approval roles
			if ("1".equals(tmpCount) || tmpCount.equals(bean.getApproverColl().size())) {
				bean.setLastApprovalRole("Y");
			}else {
				bean.setLastApprovalRole("N");
			}
		}catch (Exception e) {
			log.error(e);
		}
	}

	public void specificUseApprovalRequired(CatAddHeaderViewBean bean) {
		try {
			//if any active work areas has specific_use_approval_required = Y then it applied
			StringBuilder query =  new StringBuilder("select count(*) from catalog_add_request_new carn, fac_loc_app$ fla");
			query.append(" where carn.company_id = fla.company_id and carn.eng_eval_facility_id = fla.facility_id and fla.specific_use_approval_required = 'Y'");
			query.append(" and fla.status = 'A' and carn.request_id = ").append(bean.getRequestId());
			if (!dataCountIsZero(query.toString())) {
				bean.setSpecificUseApprovalRequired("Y");
			}else {
				bean.setSpecificUseApprovalRequired("N");
			}
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	private void getMaterialCategoryData(PersonnelBean personnelBean, CatAddHeaderViewBean inputBean) {
		try {
			if (personnelBean.isFeatureReleased("MatlCategorySubCategoryOption",inputBean.getEngEvalFacilityId(),inputBean.getCompanyId())) {
				inputBean.setHasMaterialCategoryOption("Y");
				getMaterialCategoryOption(inputBean);
				//get selected material category and subcategory from catalog_add_planned_use
				StringBuilder query = new StringBuilder("select msc.material_subcategory_id,msc.material_category_id");
				query.append(" from catalog_add_planned_use capu, vv_material_subcategory msc");
				query.append(" where capu.company_id = '").append(inputBean.getCompanyId()).append("' and capu.request_id = ").append(inputBean.getRequestId());
				query.append(" and capu.company_id = msc.company_id and capu.material_subcategory_id = msc.material_subcategory_id");
				genericSqlFactory.setBeanObject(new MaterialCategorySubcatViewBean());
				Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
				while(iter.hasNext()) {
					MaterialCategorySubcatViewBean bean = (MaterialCategorySubcatViewBean)iter.next();
					if (bean.getMaterialCategoryId() != null)
						inputBean.setSelectedMaterialCategoryId(bean.getMaterialCategoryId().toString());
					else
						inputBean.setSelectedMaterialCategoryId("");
					if (bean.getMaterialSubcategoryId() != null)
						inputBean.setSelectedMaterialSubcategoryId(bean.getMaterialSubcategoryId().toString());
					else
						inputBean.setSelectedMaterialSubcategoryId("");
					break;
				} //end of loop
				//if part number exist and starting_view it is New Work Area Approval or Modify QPL
				//the get material category for part if selectedMaterialCategory is empty
				if (StringHandler.isBlankString(inputBean.getSelectedMaterialCategoryId())) {
					if (!StringHandler.isBlankString(inputBean.getCatPartNo()) &&
							(inputBean.getStartingView().intValue() == 3 || inputBean.getStartingView().intValue() == 4)) {
						query = new StringBuilder("select msc.material_subcategory_id,msc.material_category_id");
						query.append(" from fac_part_use_code fpuc, vv_material_subcategory msc");
						query.append(" where fpuc.company_id = '").append(inputBean.getCompanyId()).append("' and fpuc.catalog_company_id = '").append(inputBean.getCatalogCompanyId());
						query.append("' and fpuc.catalog_id = '").append(inputBean.getCatalogId()).append("' and fpuc.cat_part_no = '").append(inputBean.getCatPartNo());
						query.append("' and fpuc.part_group_no = ").append(inputBean.getPartGroupNo());
						query.append(" and fpuc.company_id = msc.company_id and fpuc.material_subcategory_id = msc.material_subcategory_id");
						genericSqlFactory.setBeanObject(new MaterialCategorySubcatViewBean());
						iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
						while(iter.hasNext()) {
							MaterialCategorySubcatViewBean bean = (MaterialCategorySubcatViewBean)iter.next();
							if (bean.getMaterialCategoryId() != null)
								inputBean.setSelectedMaterialCategoryId(bean.getMaterialCategoryId().toString());
							else
								inputBean.setSelectedMaterialCategoryId("");
							if (bean.getMaterialSubcategoryId() != null)
								inputBean.setSelectedMaterialSubcategoryId(bean.getMaterialSubcategoryId().toString());
							else
								inputBean.setSelectedMaterialSubcategoryId("");
							break;
						} //end of loop
					}
				}
			}else {
				inputBean.setHasMaterialCategoryOption("N");
				inputBean.setMaterialCategoryColl(new Vector(0));
				inputBean.setSelectedMaterialCategoryId("");
				inputBean.setSelectedMaterialSubcategoryId("");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	} //end of method

	private void getMaterialCategoryOption(CatAddHeaderViewBean inputBean) {
		try {
			StringBuilder query = new StringBuilder("select * from material_category_subcat_view where company_id = '").append(inputBean.getCompanyId());
			query.append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("' and catalog_id = '").append(inputBean.getCatalogId());
			query.append("' order by material_category_name,material_subcategory_name");
			genericSqlFactory.setBeanObject(new MaterialCategorySubcatViewBean());
			Collection tmpData = genericSqlFactory.selectQuery(query.toString(),connection);
			//build object data
			Vector dataColl = new Vector();
			Iterator iter = tmpData.iterator();
			BigDecimal lastMaterialCategoryId = new BigDecimal(0);
			while (iter.hasNext()) {
				MaterialCategorySubcatViewBean bean = (MaterialCategorySubcatViewBean) iter.next();
				if (lastMaterialCategoryId.equals(bean.getMaterialCategoryId())) {
					MaterialCategorySubcatViewBean parentBean = (MaterialCategorySubcatViewBean)dataColl.lastElement();
					Collection innerColl = parentBean.getMaterialSubCategoryColl();
					MaterialCategorySubcatViewBean childBean = new MaterialCategorySubcatViewBean();
					BeanHandler.copyAttributes(bean, childBean);
					//clear parent data
					childBean.setMaterialCategoryId(null);
					childBean.setMaterialCategoryName(null);
					innerColl.add(childBean);
				}else {
					MaterialCategorySubcatViewBean parentBean = new MaterialCategorySubcatViewBean();
					parentBean.setMaterialCategoryId(bean.getMaterialCategoryId());
					parentBean.setMaterialCategoryName(bean.getMaterialCategoryName());
					parentBean.setShowForProd(bean.getShowForProd());
					parentBean.setShowForNonProd(bean.getShowForNonProd());
					Collection innerColl = new Vector();
					MaterialCategorySubcatViewBean childBean = new MaterialCategorySubcatViewBean();
					BeanHandler.copyAttributes(bean, childBean);
					//clear parent data
					childBean.setMaterialCategoryId(null);
					childBean.setMaterialCategoryName(null);
					innerColl.add(childBean);
					parentBean.setMaterialSubCategoryColl(innerColl);
					dataColl.add(parentBean);
				}
				lastMaterialCategoryId = bean.getMaterialCategoryId();
			}
			inputBean.setMaterialCategoryColl(dataColl);
		}catch (Exception e) {
			e.printStackTrace();
		}
	} //end of method

	public void getRequestInfo(CatAddHeaderViewBean bean, PersonnelBean personnelBean, boolean needHeaderData) throws Exception {
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			StringBuilder query = null;
			//header info
			if (needHeaderData) {
				Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(bean.getRequestId());
				CatAddHeaderViewBean catAddHeaderViewBean = headerColl.get(0);
				BeanHandler.copyAttributes(catAddHeaderViewBean, bean);
				//if request is pending approval then get all approvers who can approve this request
				getApproverForRequest(bean,catAddHeaderViewBean.getRequestId(),personnelBean);
				//set cat add approval detail needed flag
				catAddApprovalDetailNeeded(bean);
				//get list of inventory group for facility
				bean.setInventoryGroupColl(getFacilityInventoryGroup(bean));
				//set view level
				calculateViewLevel(bean,personnelBean);
				//can edit use approval
				canEditUseApproval(bean,personnelBean);
				//can edit het usage recording
				canEditHetUsageRecording(bean,personnelBean);
				//can edit Spec
				canEditSpec(bean,personnelBean);
				//get material category data
				getMaterialCategoryData(personnelBean,bean);
				//can edit material category subcategory data
				canEditMatlCategorySubcategory(bean,personnelBean);
				//require EMAP by catalog_id. Values: yes, no, checkHMRB (check HMRB grid per row for Program required EMAP)
				getCatAddEmapAuditOption(bean,personnelBean);
				//can edit mixture data
				canEditMixtureData(bean,personnelBean);
				//get default test
				getDefaultTestForCatalog(bean);
			}

			if(personnelBean.isFeatureReleased("ShowPerVolWeight", bean.getEngEvalFacilityId(), personnelBean.getCompanyId()))
			{
				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
				engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
				bean.setShippingWeightUnitColl(engEvalProcess.getShippingWeightSizeUnit(false));
			} else
				bean.setShippingWeightUnitColl(new Vector(0));

			//get list of storage temperature for catalog
			bean.setCatalogStorageTempColl(getCatalogStorageTemp(bean));
			//get list of shelf life basis
			bean.setShelfLifeBasisColl(getShelfLifeBasis());
			//label color
			getLabelColor(bean);
			getCatAddRequestorEditMsdsId(bean);
			//required customer mfg id
			customerMfgIdRequired(bean,personnelBean);
			//lines data
			genericSqlFactory.setBeanObject(new CatPartQplViewBean());
			query = new StringBuilder("select rownum display_line_item,x.* from ( ");
			query.append("select aa.* from (").append(getQplItemForRequest(bean.getRequestId())).append(" order by item_id, part_id) aa");
			query.append(" union all ");
			query.append("select bb.* from (").append(getItemInOtherRequestForRequest(bean.getRequestId())).append(" order by b.request_id,cai.item_id,cai.part_id) bb");
			query.append(" union all ");
			query.append("select cc.* from (").append(getItemForRequest(bean.getRequestId())).append(" order by line_item,item_id,part_id) cc");
			query.append(") x order by display_line_item");
			Collection lineDataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Collection qplDataColl = new Vector();
			HashMap m1 = new HashMap();
			Integer i1 = null;
			BigDecimal lastLineItem = new BigDecimal("0");
			String lastKey = "";
			String lastDisplayStatus = "";
			boolean hasRoomTempOutTime = false;
			//looping thru line data
			Iterator iter = lineDataColl.iterator();
			int i = 0;
			while(iter.hasNext()) {
				CatPartQplViewBean tmpBean = (CatPartQplViewBean) iter.next();
				BigDecimal lineItem = tmpBean.getDisplayLineItem();
				String currentKey = "";
				//check to see if data has room_temp_out_time
				if (tmpBean.getRoomTempOutTime() != null) {
					try {
						tmpBean.getRoomTempOutTime().floatValue();
						hasRoomTempOutTime = true;
					}catch (Exception ee) {
						//do nothing
					}
				}

				//if data is come from cpig - merge by item
				//if data come from other requests merge by other request
				//otherwise, if data come from this request then merge it by line_item
				if ("catalog".equalsIgnoreCase(tmpBean.getDataSource())) {
					currentKey = tmpBean.getItemId().toString();
				}else if ("otherRequest".equalsIgnoreCase(tmpBean.getDataSource())) {
					currentKey = tmpBean.getDisplayStatus();
				}else {
					currentKey = tmpBean.getLineItem().toString();
				}

				if (currentKey.equals(lastKey)) {
					lineItem = lastLineItem;
				}

				if (m1.get(lineItem) == null) {
					i1 = new Integer(0);
					m1.put(lineItem, i1);
				}
				i1 = (Integer) m1.get(lineItem);
				i1 = new Integer(i1.intValue() + 1);
				m1.put(lineItem, i1);

				tmpBean.setDisplayLineItem(lineItem);
				qplDataColl.add(tmpBean);

				lastLineItem = lineItem;
				lastKey = currentKey;
				lastDisplayStatus = tmpBean.getDisplayStatus();
				i++;
				//get customer manufacturer ID for our manufacturer
				if (bean.requireCustomerMfgId()) {
					tmpBean.setCustomerMfgIdDisplay(getCustomerMfg(bean.getCompanyId(),tmpBean.getMfgId()));
				}
			} //end of loop
			bean.setQplDataColl(qplDataColl);
			bean.setQplRowSpan(m1);
			if (hasRoomTempOutTime) {
				bean.setRoomTempOutTime("Y");
			}else {
				bean.setRoomTempOutTime("N");
			}

			//use approval data
			hasApplicationUseGroup(bean);
			//specific_use_approval_required
			specificUseApprovalRequired(bean);
			getUseApprovalData(bean, personnelBean);
			
			//spec data
			getSpecData(bean);

			//flowdown
			getFlowdownData(bean);

			//HMRB
			getHmrbData(bean,"Part");
			//required customer msds
			requireCustomerMsds(bean,personnelBean);
			if (personnelBean.isFeatureReleased("HmrbTab",bean.getEngEvalFacilityId(),bean.getCompanyId())) {
				//get vocet status and coat category
				bean.setVocetStatusColl(getVocetStatus(bean));
				bean.setVocetCoatCategoryColl(getVocetCoatCategory(bean));
				//can edit vocet properties
				canEditVocetProperties(bean,personnelBean);
				//can edit nano material
				canEditNanomaterial(bean,personnelBean);
				//can edit radioactive
				canEditRadioactive(bean,personnelBean);
			}else {
				bean.setVocetStatusColl(new Vector(0));
				bean.setVocetCoatCategoryColl(new Vector(0));
			}

			//storage
			getStorageData(bean);

			//has keyword and/or list approval
			bean.setHasKeywordListApproval(hasKeywordListApproval(bean.getRequestId()));

		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	} //end of method

	public void getDefaultTestForCatalog(CatAddHeaderViewBean bean) throws Exception {
		StringBuilder query = new StringBuilder("select count(*) from vv_test where company_id = '").append(bean.getCompanyId()).append("' and catalog_company_id = '").append(bean.getCatalogCompanyId());
		query.append("' and catalog_id = '").append(bean.getCatalogId()).append("' and default_test = 'Y' and active = 'Y'");
		if (!dataCountIsZero(query.toString())) {
			bean.setCatalogHasDefaultTest("Y");
		}else {
			bean.setCatalogHasDefaultTest("N");
		}
	} //end of method

	private void getLabelColor(CatAddHeaderViewBean bean) {
		try {
			StringBuilder query = new StringBuilder("select fx_require_label_color(").append(bean.getRequestId()).append(") require_label_color from dual");
			String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
			if ("Y".equals(tmpVal)) {
				bean.setLabelColorRequired("Y");
				query = new StringBuilder("select iglc.label_color from inventory_group_label_color iglc, catalog_add_request_new b,facility c");
				query.append(" where b.request_id = ").append(bean.getRequestId()).append(" and b.eng_eval_facility_id = c.facility_id");
				query.append(" and c.inventory_group_default = iglc.inventory_group order by iglc.label_color");
				genericSqlFactory.setBeanObject(new CatalogAddItemBean());
				bean.setLabelColorColl(genericSqlFactory.selectQuery(query.toString(),connection));
			}else {
				bean.setLabelColorRequired("N");
				bean.setLabelColorColl(new Vector(0));
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Get label color failed: "+bean.getRequestId(),"See logs for details.");
		}
	}

	public void calculateViewLevel(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		calculateViewLevel(bean,personnelBean,"");
	}

	//this method take in specific approval role, it's pass in then only return "approver" if user is an approver for that
	//approval role
	public void calculateViewLevel(CatAddHeaderViewBean bean, PersonnelBean personnelBean, String approvalRole) {
		String viewLevel = "view";
		try {
			int requestStatus = bean.getRequestStatus().intValue();
			if (requestStatus < 5 || requestStatus == 14 || requestStatus == 15 || requestStatus == 17) {
				if (personnelBean.getPersonnelId() == bean.getRequestor().intValue()) {
					viewLevel = "requestor";
				}else {
					if (bean.getResubmitRequestor() != null) {
						if (personnelBean.getPersonnelId() == bean.getResubmitRequestor().intValue()) {
							viewLevel = "requestor";
						}
					}
				}
			}else {
				if (requestStatus != 7 && requestStatus != 9 && requestStatus != 12) {
					Iterator iter = bean.getApproverColl().iterator();
					while(iter.hasNext()) {
						ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
						if (StringHandler.isBlankString(approvalRole))
							approvalRole = approverBean.getApprovalRole();
						if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() && approvalRole.equals(approverBean.getApprovalRole())) {
							viewLevel = "approver";
							break;
						}
					}
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setViewLevel(viewLevel);
	}

	public void canEditMatlCategorySubcategory(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditMatlCategorySubcategory = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getEditMatlCategorySubcategory())) {
					allowEditMatlCategorySubcategory = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditMatlCategorySubcategory(allowEditMatlCategorySubcategory);
	}


	public void getCatAddEmapAuditOption(CatAddHeaderViewBean bean, PersonnelBean personnelBean)
	{
		GenericSqlFactory factory = new GenericSqlFactory(new DbManager(getClient(), getLocale()), new CatAddHeaderViewBean());
		String emapAuditOption = null;
		try {
			emapAuditOption = factory.selectSingleValue("select cat_add_emap_audit_option from catalog_facility where company_id ='"+personnelBean.getCompanyId()+
					"' and catalog_company_id = '"+personnelBean.getCompanyId()+"' and catalog_id = '"+bean.getCatalogId()+"'");
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		bean.setCatAddEmapAuditOption(emapAuditOption);
	}

	public void canEditUseApproval(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditUseApproval = "N";
		try {
			int requestStatus = bean.getRequestStatus().intValue();
			//requestors are allow to edit use approval for his/her catalog add requests
			if (requestStatus < 5 || requestStatus == 14 || requestStatus == 15 || requestStatus == 17) {
				if (personnelBean.getPersonnelId() == bean.getRequestor().intValue()) {
					allowEditUseApproval = "Y";
				}else {
					if (bean.getResubmitRequestor() != null) {
						if (personnelBean.getPersonnelId() == bean.getResubmitRequestor().intValue()) {
							allowEditUseApproval = "Y";
						}
					}
				}
			}else {
				if (requestStatus != 7 && requestStatus != 9 && requestStatus != 12) {
					Iterator iter = bean.getApproverColl().iterator();
					while(iter.hasNext()) {
						ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
						if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
								"Y".equals(approverBean.getEditUseApproval())) {
							allowEditUseApproval = "Y";
							break;
						}
					}
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditUseApproval(allowEditUseApproval);
	}

	public void canEditHetUsageRecording(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditHetUsageRecording = "N";
		try {
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getEditHetUsageRecording())) {
					allowEditHetUsageRecording = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditHetUsageRecording(allowEditHetUsageRecording);
	}

	public void canEditRadioactive(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditRadioactive = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getEditRadioactive())) {
					allowEditRadioactive = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditRadioactive(allowEditRadioactive);
	}

	public void canEditNanomaterial(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditNanomaterial = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getEditNanomaterial())) {
					allowEditNanomaterial = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditNanomaterial(allowEditNanomaterial);
	}

	public void canEditVocetProperties(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditVocetProperties = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getEditVocetProperties())) {
					allowEditVocetProperties = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditVocetProperties(allowEditVocetProperties);
	}

	public void canEditSpec(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditSpec = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getEditSpec()) && "BEFORE".equals(approverBean.getBeforeTcmSpec())) {
					allowEditSpec = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditSpec(allowEditSpec);
	}

	public void requireCustomerMsds(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String requireCustomerMsds = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getRequireCustomerMsds())) {
					requireCustomerMsds = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setRequireCustomerMsds(requireCustomerMsds);
	}

	public void customerMfgIdRequired(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String customerMfgIdRequired = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"Y".equals(approverBean.getCustomerMfgIdRequired())) {
					customerMfgIdRequired = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setCustomerMfgIdRequired(customerMfgIdRequired);
	}

	public void canEditMixtureData(CatAddHeaderViewBean bean, PersonnelBean personnelBean) {
		String allowEditMixtureData = "N";
		try {
			if (bean.getApproverColl() == null) {
				getApproverForRequest(bean,bean.getRequestId(),personnelBean);
			}
			Iterator iter = bean.getApproverColl().iterator();
			while(iter.hasNext()) {
				ChemicalApproverBean approverBean = (ChemicalApproverBean)iter.next();
				if (personnelBean.getPersonnelId() == approverBean.getPersonnelId().intValue() &&
						"BEFORE".equals(approverBean.getBeforeTcmQc())) {
					allowEditMixtureData = "Y";
					break;
				}
			}
		}catch(Exception e) {
			log.error(e);
		}
		bean.setAllowEditMixtureData(allowEditMixtureData);
	}

	public void catAddApprovalDetailNeeded(CatAddHeaderViewBean bean) throws Exception {
		StringBuilder query = new StringBuilder("select count(*) from facility$ a,catalog_add_request_new b where a.company_id = b.company_id and a.facility_id = b.eng_eval_facility_id");
		query.append(" and a.cat_add_approval_detail_needed = 'Y' and b.request_id = ").append(bean.getRequestId());
		if (!dataCountIsZero(query.toString())) {
			bean.setCatAddApprovalDetailNeeded("Y");
		}
	}

	public void getHmrbData(CatAddHeaderViewBean bean, String calledFrom) throws Exception {
		StringBuilder query = new StringBuilder("select x.* from (");
		query.append(getApprovedHmrbData(bean.getRequestId(),calledFrom));
		query.append(" union all ");
		query.append(getOtherRequestHmrbData(bean.getRequestId(),calledFrom));
		query.append(" union all ");
		query.append(getRequestHmrbData(bean.getRequestId()));
		query.append(") x order by sorting_order,usage_category_name,usage_subcategory_name,material_category_name,material_subcategory_name");
		genericSqlFactory.setBeanObject(new CatalogAddHmrbBean());
		bean.setHmrbDataColl(genericSqlFactory.selectQuery(query.toString(),connection));
	}

	private String getApprovedHmrbData(BigDecimal requestId, String calledFrom) {
		StringBuilder query = new StringBuilder("select distinct to_number(1) sorting_order,'catalog' data_source,application_use_group_name approval_code_name,usage_category_name,");
		query.append("usage_subcategory_name,material_category_name,material_subcategory_name,start_date begin_date_jsp,end_date end_date_jsp");
		query.append(",'Approved' approval_code_status,point_of_contact,application_use_group_id");
		if ("MSDS".equals(calledFrom)) {
			query.append(",use_id hmrb_line_item, emap_required from msds_or_mixture_use_view where request_id = ").append(requestId);
		}else {
			query.append(",approved_id hmrb_line_item, emap_required from fac_part_approved_use_view where request_id = ").append(requestId);
		}
		//this is to excludes items that is on current request
		query.append(" and (usage_category_name,usage_subcategory_name) not in ");
		query.append("(select usage_category_name,usage_subcategory_name ");
		query.append("from catalog_add_planned_use_view where request_id = ").append(requestId).append(")");
		return query.toString();
	}

	private String getOtherRequestHmrbData(BigDecimal requestId, String calledFrom) {
		StringBuilder query = new StringBuilder("select distinct to_number(2) sorting_order,'otherRequest' data_source,application_use_group_name approval_code_name,usage_category_name,");
		query.append("usage_subcategory_name,material_category_name,material_subcategory_name,start_date begin_date_jsp,end_date end_date_jsp");
		query.append(",'Pending Req '||pending_request_id approval_code_status,point_of_contact,application_use_group_id,planned_id hmrb_line_item,'' emap_required ");
		if ("MSDS".equals(calledFrom)) {
			query.append(" from cat_add_msds_pending_use_view where request_id = ").append(requestId);
		}else {
			query.append(" from catalog_add_pending_use_view where request_id = ").append(requestId);
		}
		return query.toString();
	}

	private String getRequestHmrbData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select distinct to_number(3) sorting_order,'new' data_source,application_use_group_name approval_code_name,usage_category_name,");
		query.append("usage_subcategory_name,material_category_name,material_subcategory_name,start_date begin_date_jsp,end_date end_date_jsp");
		query.append(",decode(carn.request_status,'12','Approved','9','Approved','Pending Approval') approval_code_status,point_of_contact,application_use_group_id,planned_id hmrb_line_item, emap_required");
		query.append(" from catalog_add_planned_use_view capuv, catalog_add_request_new carn where carn.request_id = ").append(requestId);
		query.append(" and carn.company_id = capuv.company_id and carn.request_id = capuv.request_id");
		return query.toString();
	}

	public void reloadHmrbData(CatAddHeaderViewBean bean) throws Exception{
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			if ("catalogAddMsdsRequest".equals(bean.getCalledFrom())) {
				getHmrbData(bean,"MSDS");
			}else {
				getHmrbData(bean,"Part");
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	} //end of method

	private void getFlowdownData(CatAddHeaderViewBean bean) throws Exception {
		StringBuilder query = new StringBuilder("select x.* from (");
		query.append(getApprovedFlowdownData(bean.getRequestId()));
		query.append(" union all ");
		query.append(getOtherRequestFlowdownData(bean.getRequestId()));
		query.append(" union all ");
		query.append(getRequestFlowdownData(bean.getRequestId()));
		query.append(") x order by sorting_order,flow_down_desc");
		genericSqlFactory.setBeanObject(new CatalogAddFlowDownBean());
		bean.setFlowdownDataColl(genericSqlFactory.selectQuery(query.toString(),connection));
	}

	private String getApprovedFlowdownData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select distinct to_number(1) sorting_order,'catalog' data_source,fifd.company_id,fifd.catalog_id,fifd.flow_down,");
		query.append("nvl(vfd.flow_down_desc,vfd.flow_down) flow_down_desc,");
		query.append("vfd.flow_down_type,vfd.content,vfd.revision_date,vfd.current_version");
		query.append(",'Approved' display_status ");
		query.append("from fac_item_flow_down fifd, vv_flow_down vfd, catalog_add_request_new carn ");
		query.append("where fifd.company_id = vfd.company_id and fifd.catalog_id = vfd.catalog_id ");
		query.append("and fifd.flow_down = vfd.flow_down and fifd.company_id = carn.catalog_company_id ");
		query.append("and fifd.catalog_id = carn.catalog_id and fifd.fac_part_no = carn.cat_part_no ");
		query.append("and carn.request_id = ").append(requestId);
		//this is to excludes items that is on current request
		query.append(" and fifd.flow_down not in (select flow_down from catalog_add_flow_down where request_id = ").append(requestId).append(")");
		return query.toString();
	}

	private String getOtherRequestFlowdownData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select distinct to_number(2) sorting_order,'otherRequest' data_source,vfd.company_id,vfd.catalog_id,vfd.flow_down,");
		query.append("nvl(vfd.flow_down_desc,vfd.flow_down) flow_down_desc,");
		query.append("vfd.flow_down_type,vfd.content,vfd.revision_date,vfd.current_version");
		query.append(",'Pending Req ||b.request_id' display_status ");
		query.append("from catalog_add_request_new a, catalog_add_request_new b, catalog_add_flow_down cafd, vv_flow_down vfd ");
		query.append("where b.company_id = cafd.company_id and b.request_id = cafd.request_id and a.company_id = b.company_id ");
		query.append("and a.catalog_company_id = vfd.company_id and cafd.catalog_id = vfd.catalog_id and cafd.flow_down = vfd.flow_down ");
		query.append("and a.catalog_company_id = b.catalog_company_id and a.catalog_id = b.catalog_id and a.cat_part_no = b.cat_part_no ");
		query.append("and a.part_group_no = b.part_group_no and b.request_status in ('5','6','8') and a.request_id <> b.request_id and a.request_id = ").append(requestId);
		//this is to excludes items that is on current request
		query.append(" and cafd.flow_down not in (select flow_down from catalog_add_flow_down where request_id = ").append(requestId).append(")");
		return query.toString();
	}

	private String getRequestFlowdownData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select distinct to_number(3) sorting_order,'new' data_source,vfd.company_id,vfd.catalog_id,vfd.flow_down,");
		query.append("nvl(vfd.flow_down_desc,vfd.flow_down) flow_down_desc,");
		query.append("vfd.flow_down_type,vfd.content,vfd.revision_date,vfd.current_version");
		query.append(",decode(carn.request_status,9,'Approved','Pending Approval') display_status ");
		query.append("from catalog_add_flow_down cafd, vv_flow_down vfd, catalog_add_request_new carn where carn.request_id = ").append(requestId);
		query.append(" and carn.company_id = cafd.company_id and carn.request_id = cafd.request_id");
		query.append(" and cafd.catalog_id = vfd.catalog_id and cafd.flow_down = vfd.flow_down");
		return query.toString();
	}

	private void getSpecData(CatAddHeaderViewBean bean) throws Exception {
		StringBuilder query = new StringBuilder("select x.* from (");
		query.append(getApprovedSpecData(bean.getRequestId()));
		query.append(" union all ");
		query.append(getOtherRequestSpecData(bean.getRequestId()));
		query.append(" union all ");
		query.append(getRequestSpecData(bean.getRequestId()));
		query.append(") x order by sorting_order,spec_name");
		genericSqlFactory.setBeanObject(new CatalogAddSpecBean());
		bean.setSpecDataColl(genericSqlFactory.selectQuery(query.toString(),connection));
	}

	private String getApprovedSpecData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select distinct to_number(1) sorting_order,'catalog' data_source,s.spec_id,s.spec_name,s.spec_title,s.spec_version,");
		query.append("s.spec_amendment,s.spec_date,s.content,s.on_line,fs.company_id,fs.coc,fs.coa");
		query.append(",'Approved' display_status, s.itar ");
		query.append("from fac_spec fs, spec s where fs.company_id = s.company_id and fs.spec_id = s.spec_id and (fs.company_id,fs.facility_id,fs.fac_part_no) in ");
		query.append("(select catalog_company_id,catalog_id,cat_part_no from catalog_add_request_new where request_id = ").append(requestId).append(")");
		//this is to excludes items that is on current request
		query.append(" and fs.spec_id not in (select spec_id from catalog_add_spec where request_id = ").append(requestId).append(")");
		return query.toString();
	}

	private String getOtherRequestSpecData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select distinct to_number(2) sorting_order,'otherRequest' data_source,spec_id,spec_name,spec_title,spec_version,");
		query.append("spec_amendment,spec_date,content,on_line,cas.company_id,coc,coa");
		query.append(",'Pending Req '||b.request_id display_status, cas.itar ");
		query.append("from catalog_add_request_new a, catalog_add_request_new b, catalog_add_spec cas ");
		query.append("where b.company_id = cas.company_id and b.request_id = cas.request_id and a.company_id = b.company_id ");
		query.append("and a.catalog_company_id = b.catalog_company_id and a.catalog_id = b.catalog_id and a.cat_part_no = b.cat_part_no ");
		query.append("and nvl(a.part_group_no,1) = nvl(b.part_group_no,1) and b.request_status in ('5','6','8') and a.request_id <> b.request_id and a.request_id = ").append(requestId);
		//this is to excludes items that is on current request
		query.append(" and cas.spec_id not in (select spec_id from catalog_add_spec where request_id = ").append(requestId).append(")");
		return query.toString();
	}

	private String getRequestSpecData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select distinct to_number(3) sorting_order,'new' data_source,spec_id,spec_name,spec_title,spec_version,");
		query.append("spec_amendment,spec_date,content,on_line,cas.company_id,coc,coa");
		query.append(",decode(carn.request_status,9,'Approved','Pending Approval') display_status, cas.itar ");
		query.append("from catalog_add_spec cas, catalog_add_request_new carn where carn.request_id = ").append(requestId);
		query.append(" and carn.company_id = cas.company_id and carn.request_id = cas.request_id");
		return query.toString();
	}

	private void getUseApprovalData(CatAddHeaderViewBean bean, PersonnelBean personnelBean) throws Exception {
		StringBuilder query = new StringBuilder("select x.* from (");
		query.append(getApprovedUseApprovalData(bean.getRequestId()));
		query.append(" union all ");
		query.append(getOtherRequestUseApprovalData(bean.getRequestId()));
		query.append(" union all ");
		query.append(getRequestUseApprovalData(bean.getRequestId()));
		query.append(") x order by sorting_order,application_desc");
		
		genericSqlFactory.setBeanObject(new CatalogAddUserGroupBean());
		Collection<CatalogAddUserGroupBean> catAddColl = genericSqlFactory.selectQuery(query.toString(), connection);
		
		if (personnelBean.isFeatureReleased("ShowEmissionPoints", bean.getEngEvalFacilityId(), bean.getCompanyId())) {
			Collection<FacAppEmissionPointBean> epOptionColl = getEmissionPointColl(bean.getEngEvalFacilityId());
			boolean isUnique = true;
			String uniqueSeparator = null;
			for (int i = 0; i < emissionPointsIdSeparators.length(); i++) {
				String curSeparator = emissionPointsIdSeparators.substring(i, i + 1);
				for (FacAppEmissionPointBean epBean : epOptionColl)
					if (epBean.getAppEmissionPoint().contains(curSeparator) || epBean.getFacEmissionPoint().contains(curSeparator)) {
						isUnique = false;
						break;
					}
				
				if (isUnique) {
					uniqueSeparator = curSeparator;
					break;
				}
			}
			if (uniqueSeparator == null)
				uniqueSeparator = emissionPointsIdSeparators;
			
			for (CatalogAddUserGroupBean caBean : catAddColl) {
				if ("otherRequest".equalsIgnoreCase(caBean.getDataSource()) || "new".equalsIgnoreCase(caBean.getDataSource())) {
					Collection<CaProcessEmissionPointBean> epColl = getRequestEmissionPoints(bean.getEngEvalFacilityId(), caBean.getWorkArea(), caBean.getRequestId());
					if (epColl != null && epColl.size() > 0) {
						String emissionPoints = "";
						String emissionPointDescs = "";
						for (CaProcessEmissionPointBean caepBean : epColl) {
							if (!StringHandler.isBlankString(emissionPoints))
								emissionPoints += uniqueSeparator + uniqueSeparator;
							emissionPoints += caepBean.getAppEmissionPoint() + uniqueSeparator + caepBean.getFacEmissionPoint();
							
							if (!StringHandler.isBlankString(emissionPointDescs))
								emissionPointDescs += "; ";
							emissionPointDescs += caepBean.getFacEmissionPoint() + " - " + caepBean.getAppEmissionPoint() + ": " + caepBean.getEmissionPointDesc();
						}
						
						caBean.setEmissionPointIdSeparator(uniqueSeparator);
						caBean.setEmissionPoints(emissionPoints);
						caBean.setEmissionPointDescs(emissionPointDescs);
					}
				}
			}
		}
		
		bean.setUseApprovalDataColl(catAddColl);
	}

	private String getApprovedUseApprovalData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select to_number('1') sorting_order, 'catalog' data_source,carn.company_id,carn.request_id,");
		query.append("ua.facility_id,ua.application work_area,ua.user_group_id,ua.process_desc,");
		query.append("ua.limit_quantity_period1 quantity_1,ua.days_period1 per_1,'days' period_1,ua.limit_quantity_period2 quantity_2,ua.days_period2 per_2,'days' period_2,");
		query.append("'' charge_number,ua.approval_status,ua.estimate_order_quantity_period estimated_annual_usage,'' approval_request_status,");
		query.append("fla.application_desc,nvl(aug.user_group_desc,aug.user_group_id) user_group_desc ");
		query.append(",fla.inventory_group,ua.application_use_group_id,ug.application_use_group_name,ug.application_use_group_desc ");
		query.append(",'' waste_water_discharge ");
		query.append("from use_approval$ ua,catalog_add_request_new carn,fac_loc_app$ fla,approval_user_group$ aug,fac_cat_part_inventory_view cpi,application_use_group$ ug ");
		query.append("where carn.request_id =").append(requestId);
		query.append(" and carn.company_id = ua.company_id and carn.eng_eval_facility_id = ua.facility_id");
		query.append(" and carn.catalog_company_id = ua.catalog_company_id and carn.catalog_id = ua.catalog_id");
		query.append(" and carn.cat_part_no = ua.fac_part_no and carn.part_group_no = ua.part_group_no");
		query.append(" and ua.company_id = cpi.company_id and ua.catalog_company_id = cpi.catalog_company_id");
		query.append(" and ua.catalog_id = cpi.catalog_id and ua.fac_part_no = cpi.cat_part_no and ua.part_group_no = cpi.part_group_no and ua.facility_id = cpi.facility_id");
		query.append(" and ua.company_id = fla.company_id(+) and ua.facility_id = fla.facility_id(+) and ua.application = fla.application(+)");
		query.append(" and ua.company_id = aug.company_id and ua.facility_id = aug.facility_id and ua.user_group_id = aug.user_group_id");
		query.append(" and ua.company_id = ug.company_id and ua.facility_id = ug.facility_id and ua.application_use_group_id = ug.application_use_group_id");
		//this is to excludes items that is on current request
		query.append(" and (ua.application,ua.application_use_group_id) not in (select work_area,application_use_group_id from catalog_add_user_group where request_id = ").append(requestId).append(")");

		return query.toString();
	}

	private String getOtherRequestUseApprovalData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select to_number('2') sorting_order, 'otherRequest' data_source,");
		query.append("caug.company_id, caug.request_id, caug.facility_id, caug.work_area, caug.user_group_id, caug.process_desc, caug.quantity_1, caug.per_1, caug.period_1,");
		query.append("caug.quantity_2, caug.per_2, caug.period_2, caug.charge_number,'Pending Req '||b.request_id approval_status, caug.estimated_annual_usage,caug.approval_status approval_request_status,");
		query.append("fla.application_desc,nvl(aug.user_group_desc,aug.user_group_id) user_group_desc,caug.inventory_group");
		query.append(",caug.application_use_group_id,ug.application_use_group_name,ug.application_use_group_desc ");
		query.append(",caug.waste_water_discharge ");
		query.append("from catalog_add_request_new a, catalog_add_request_new b, catalog_add_user_group caug,fac_loc_app$ fla,approval_user_group$ aug,application_use_group$ ug ");
		query.append("where a.request_id = ").append(requestId).append(" and b.company_id = caug.company_id and b.request_id = caug.request_id and a.company_id = b.company_id ");
		query.append("and a.catalog_company_id = b.catalog_company_id and a.catalog_id = b.catalog_id and a.cat_part_no = b.cat_part_no ");
		query.append("and a.part_group_no = b.part_group_no and b.request_status in ('5','6','8') and a.request_id <> b.request_id ");
		query.append("and caug.company_id = fla.company_id(+) and caug.facility_id = fla.facility_id(+) and caug.work_area = fla.application(+) ");
		query.append("and caug.company_id = aug.company_id and caug.facility_id = aug.facility_id and caug.user_group_id = aug.user_group_id");
		query.append(" and caug.company_id = ug.company_id and caug.facility_id = ug.facility_id and caug.application_use_group_id = ug.application_use_group_id");
		//this is to excludes items that is on current request
		query.append(" and (caug.work_area,caug.application_use_group_id) not in (select work_area,application_use_group_id from catalog_add_user_group where request_id = ").append(requestId).append(")");
		return query.toString();
	}

	private String getRequestUseApprovalData(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select to_number('3') sorting_order, 'new' data_source,");
		query.append("caug.company_id, caug.request_id, caug.facility_id, caug.work_area, caug.user_group_id, caug.process_desc, caug.quantity_1, caug.per_1, caug.period_1,");
		query.append("caug.quantity_2, caug.per_2, caug.period_2, caug.charge_number,decode(carn.request_status,9,'Approved','') approval_status, caug.estimated_annual_usage,");
		query.append("decode(carn.request_status,9,'',caug.approval_status) approval_request_status,");
		query.append("fla.application_desc,nvl(aug.user_group_desc,aug.user_group_id) user_group_desc,caug.inventory_group");
		query.append(",caug.application_use_group_id,ug.application_use_group_name,ug.application_use_group_desc ");
		query.append(",caug.waste_water_discharge ");
		query.append("from catalog_add_user_group caug,fac_loc_app$ fla,approval_user_group$ aug, catalog_add_request_new carn,application_use_group$ ug ");
		query.append("where carn.request_id = ").append(requestId).append(" and caug.company_id = fla.company_id(+) and caug.facility_id = fla.facility_id(+) ");
		query.append(" and carn.company_id = caug.company_id and carn.request_id = caug.request_id ");
		query.append("and caug.work_area = fla.application(+) and caug.company_id = aug.company_id and caug.facility_id = aug.facility_id ");
		query.append("and caug.user_group_id = aug.user_group_id ");
		query.append("and caug.company_id = ug.company_id and caug.facility_id = ug.facility_id and caug.application_use_group_id = ug.application_use_group_id");
		return query.toString();
	}

	private Collection getApprovalUserGroupData(CatAddHeaderViewBean bean) throws Exception {
		genericSqlFactory.setBeanObject(new ApprovalUserGroupBean());
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, bean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, bean.getEngEvalFacilityId());
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("userGroupDesc");
		return genericSqlFactory.select(criteria, sortCriteria, connection, "approval_user_group");
	}

	private String getQplItemForRequest(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select to_number('1') sorting_order,ROWNUM line_item,a.item_id,a.status,a.part_id,a.material_desc,a.packaging,");
		query.append("a.mfg_desc,a.material_id,a.mfg_part_no,a.msds_on_line,a.data_source,to_number('') starting_view,a.display_status,");
		query.append("a.shelf_life_days,a.shelf_life_basis,a.grade");
		query.append(", customer_mixture_number,customer_msds_number,room_temp_out_time,a.customer_temperature_id,fx_is_item_kit(a.item_id) item_is_kit");
		query.append(",'' approval_letter_content,'' replaces_msds,aerosol aerosol_container, radioactive, nanomaterial");
		query.append(",kit_approval_code");
		query.append(",msds_approval_code");
		query.append(",a.het_usage_recording");
		query.append(",'' label_color");
		query.append(", mixture_desc");
		query.append(",room_temp_out_time_unit");
		query.append(",vocet_status_id");
		query.append(",vocet_coat_category_id");
		query.append(",mix_ratio_amount");
		query.append(",mix_ratio_size_unit");
		query.append(",CALC_MIX_RATIO_AMOUNT");
		query.append(",CALC_MIX_RATIO_SIZE_UNIT");
		query.append(",customer_msds_number approved_cust_msds_number");
		query.append(",customer_mixture_number approved_cust_mixture_number");
		query.append(",net_wt netwt,net_wt_unit netwt_unit");
		query.append(",to_number('') mfg_id");
		query.append(",'' customer_mfg_id");
		query.append(" from table(pkg_catalog_add.fx_cat_part_qpl('',").append(requestId).append("))  a");
		//this is to excludes items that is on current request but it's not right because it will returns no data if catalog_add_item is empty???
		query.append(" where a.item_id not in (select item_id from catalog_add_item where request_id = ").append(requestId);
		query.append(" and item_id is not null)");
		if (!showObsolete) {
			query.append(" and a.status <> 'O'");
		}

		return query.toString();
	}

	private String getItemInOtherRequestForRequest(BigDecimal requestId) {
		StringBuilder result = new StringBuilder("select to_number('2') sorting_order,cai.line_item,cai.item_id,'A' status,cai.part_id,cai.material_desc,");
		result.append("decode(cai.item_id,null,cai.part_size||' '||cai.size_unit||' '||cai.pkg_style,fx_kit_packaging(cai.item_id,cai.material_id)) packaging,");
		result.append("decode(cai.material_id,null,cai.manufacturer,fx_mfg_desc_from_material(cai.material_id)) mfg_desc,");
		result.append("cai.material_id,cai.mfg_catalog_id mfg_part_no,decode(cai.material_id,null,'N',fx_msds_material_online(cai.material_id)) msds_on_line,'otherRequest' data_source,");
		result.append("cai.starting_view,'Pending Req '||b.request_id display_status,");
		result.append("cai.shelf_life_days,decode(cai.mfg_recommended_shelf_life,'Y','MfgRecommended',cai.shelf_life_basis) shelf_life_basis,cai.grade,");
		result.append("cai.customer_mixture_number,cai.customer_msds_number,cai.room_temp_out_time,cai.customer_temperature_id");
		result.append(",pkg_catalog_add.FX_CAT_ADD_REQUEST_LINE_KIT(cai.company_id,cai.request_id,cai.line_item) item_is_kit");
		result.append(",cai.approval_letter_content,cai.replaces_msds,cai.aerosol_container,cai.radioactive,cai.nanomaterial");
		result.append(",pkg_msds_search.fx_approval_code(a.company_id,a.eng_eval_facility_id,cai.material_id,cc.customer_msds_db,nvl(cai.customer_mixture_number,cai.customer_mixture_number),null,'Y') kit_approval_code");
		result.append(",pkg_msds_search.fx_approval_code(a.company_id,a.eng_eval_facility_id,cai.material_id,cc.customer_msds_db,nvl(cai.approved_cust_msds_number,cai.customer_msds_number),null,'Y') msds_approval_code");
		result.append(",cai.het_usage_recording,cai.label_color,cai.mixture_desc,cai.room_temp_out_time_unit,cai.vocet_status_id,cai.vocet_coat_category_id");
		result.append(",cai.mix_ratio_amount,cai.mix_ratio_size_unit,cai.CALC_MIX_RATIO_AMOUNT,cai.CALC_MIX_RATIO_SIZE_UNIT, cai.approved_cust_mixture_number,cai.approved_cust_msds_number");
		result.append(",cai.netwt,cai.netwt_unit,cai.mfg_id,cai.customer_mfg_id");
		result.append(" from catalog_add_request_new a, catalog_add_request_new b, catalog_add_item cai, catalog_company$ cc");
		result.append(" where b.company_id = cai.company_id and b.request_id = cai.request_id and cai.item_id is not null and a.company_id = b.company_id");
		result.append(" and a.catalog_company_id = b.catalog_company_id and a.catalog_id = b.catalog_id and a.cat_part_no = b.cat_part_no");
		result.append(" and a.part_group_no = b.part_group_no and b.request_status in ('5','6','8') and a.request_id <> b.request_id and a.request_id = ").append(requestId);
		//this is to excludes items that is on current request but it's not right because it will returns no data if catalog_add_item is empty???
		result.append(" and cai.item_id not in (select item_id from catalog_add_item where request_id = ").append(requestId);
		result.append(" and item_id is not null)");
		result.append(" and a.company_id = cc.company_id and a.catalog_id = cc.catalog_id");
		return result.toString();
	}

	private String getItemForRequest(BigDecimal requestId) {
		StringBuilder result = new StringBuilder("select to_number('3') sorting_order,cai.line_item,cai.item_id,'A' status,cai.part_id,cai.material_desc,");
		result.append("decode(cai.item_id,null,cai.part_size||' '||cai.size_unit||' '||cai.pkg_style,fx_kit_packaging(cai.item_id,cai.material_id)) packaging,");
		result.append("decode(cai.material_id,null,cai.manufacturer,fx_mfg_desc_from_material(cai.material_id)) mfg_desc,");
		result.append("cai.material_id,cai.mfg_catalog_id mfg_part_no,decode(cai.material_id,null,'N',fx_msds_material_online(cai.material_id)) msds_on_line,");
		result.append("decode(cai.line_status,'Rejected','rejected','new') data_source,cai.starting_view,");
		result.append("decode(cai.line_status,'Rejected','Rejected by '||fx_personnel_id_to_name(cai.rejected_by),");
		result.append("decode(carn.request_status,'9','Approved','7','Rejected',decode(cai.starting_view,'5','Pending QPL Fadeout Approval','3','Approved','Pending Approval'))) display_status,");
		result.append("cai.shelf_life_days,decode(cai.mfg_recommended_shelf_life,'Y','MfgRecommended',cai.shelf_life_basis) shelf_life_basis,cai.grade,");
		result.append("cai.customer_mixture_number,cai.customer_msds_number,cai.room_temp_out_time,cai.customer_temperature_id");
		result.append(",pkg_catalog_add.FX_CAT_ADD_REQUEST_LINE_KIT(cai.company_id,cai.request_id,cai.line_item) item_is_kit");
		result.append(",cai.approval_letter_content,cai.replaces_msds,cai.aerosol_container,cai.radioactive,cai.nanomaterial");
		result.append(",pkg_msds_search.fx_approval_code(carn.company_id,carn.eng_eval_facility_id,cai.material_id,cc.customer_msds_db,nvl(cai.approved_cust_mixture_number,cai.customer_mixture_number),null,'Y') kit_approval_code");
		result.append(",pkg_msds_search.fx_approval_code(carn.company_id,carn.eng_eval_facility_id,cai.material_id,cc.customer_msds_db,nvl(cai.approved_cust_msds_number,cai.customer_msds_number),null,'Y') msds_approval_code");
		result.append(",cai.het_usage_recording,cai.label_color,cai.mixture_desc,cai.room_temp_out_time_unit,cai.vocet_status_id,cai.vocet_coat_category_id");
		result.append(",cai.mix_ratio_amount,cai.mix_ratio_size_unit,cai.CALC_MIX_RATIO_AMOUNT,cai.CALC_MIX_RATIO_SIZE_UNIT, cai.approved_cust_msds_number, cai.approved_cust_mixture_number");
		result.append(",cai.netwt,cai.netwt_unit,cai.mfg_id,cai.customer_mfg_id");
		result.append(" from catalog_add_item cai, catalog_add_request_new carn, catalog_company$ cc where carn.company_id = cai.company_id and carn.request_id = cai.request_id and carn.request_id = ").append(requestId);
		result.append(" and carn.company_id = cc.company_id and carn.catalog_id = cc.catalog_id");
		return result.toString();
	}


	public CatalogAddItemBean getSuggestedSupplier(BigDecimal requestId, BigDecimal lineItem) throws BaseException {
		CatalogAddItemBean bean = null;
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatalogAddItemBean());
		//the reason that the sql below is always for part_id = 1 is because the suggested supplier is by item not by component
		//if it is multi parts kit, just store data on the part_id = 1
		StringBuilder query = new StringBuilder("select * from catalog_add_item where request_id = ").append(requestId).append(" and part_id = 1 and line_item = ").append(lineItem);
		Collection data = factory.selectQuery(query.toString());
		Iterator iter = data.iterator();
		while (iter.hasNext()) {
			bean = (CatalogAddItemBean)iter.next();
			break;
		}
		if (bean == null) {
			bean = new CatalogAddItemBean();
			bean.setRequestId(requestId);
			bean.setLineItem(lineItem);
		}

		return bean;
	}

	public String submitAddSuggestedSupplier(CatalogAddItemBean inputBean) {
		String result = "OK";
		StringBuilder query = new StringBuilder("update catalog_add_item set");
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			boolean dataToUpdate = false;
			if (!StringHandler.isBlankString(inputBean.getSuggestedVendor())) {
				query.append(" suggested_vendor = ").append(SqlHandler.delimitString(inputBean.getSuggestedVendor()));
				dataToUpdate = true;
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactName())) {
				if (dataToUpdate) {
					query.append(",");
				}
				query.append(" vendor_contact_name = ").append(SqlHandler.delimitString(inputBean.getVendorContactName()));
				dataToUpdate = true;
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactEmail())) {
				if (dataToUpdate) {
					query.append(",");
				}
				query.append(" vendor_contact_email = ").append(SqlHandler.delimitString(inputBean.getVendorContactEmail()));
				dataToUpdate = true;
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactPhone())) {
				if (dataToUpdate) {
					query.append(",");
				}
				query.append(" vendor_contact_phone = ").append(SqlHandler.delimitString(inputBean.getVendorContactPhone()));
				dataToUpdate = true;
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactFax())) {
				if (dataToUpdate) {
					query.append(",");
				}
				query.append(" vendor_contact_fax = ").append(SqlHandler.delimitString(inputBean.getVendorContactFax()));
				dataToUpdate = true;
			}
			if (dataToUpdate) {
				//if it is multi parts kit, just store data on the part_id = 1
				query.append(" where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem()).append(" and part_id = 1");
				factory.deleteInsertUpdate(query.toString());
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Submit Add Suggested Supplier failed",query.toString());
		}
		return result;
	}

	public String submitEditNewItemData(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) throws BaseException {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			result = doEditNewItemSubmitLogic(inputBean,tabBeans,personnelBean);

		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public String doEditNewItemSubmitLogic(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) {
		String result = "OK";
		boolean notifyApprover = true;
		boolean askUserForMsds = false;
		try {
			if (!updateEditNewItemData(inputBean,tabBeans,personnelBean)) {
				result = library.getString("msg.tcmiserror");
			}
		}catch (Exception e) {
			log.error(e);
			result = library.getString("msg.tcmiserror");
		}
		return result;
	}

	private boolean updateEditNewItemData(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) {
		boolean result = true;
		try {
			//first need to go thru data to see if any component got deleted
			Iterator iter = tabBeans.iterator();
			while(iter.hasNext())  {
				CatalogAddItemBean catalogAddItemBean = (CatalogAddItemBean)iter.next();
				if ("closed".equalsIgnoreCase(catalogAddItemBean.getNewTabComponent())) {
					//part_id exist in table
					if (catalogAddItemBean.getPartId() != null) {
						StringBuilder query = new StringBuilder("delete from catalog_add_item where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem());
						query.append(" and part_id = ").append(catalogAddItemBean.getPartId());
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					}
				}
			}
			//update or insert data
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			engEvalProcess.setLineItem(new BigDecimal(inputBean.getLineItem()));
			iter = tabBeans.iterator();
			int lastPartId = 0;
			while(iter.hasNext())  {
				CatalogAddItemBean catalogAddItemBean = (CatalogAddItemBean)iter.next();
				if (!"closed".equalsIgnoreCase(catalogAddItemBean.getNewTabComponent())) {
					if ("New".equalsIgnoreCase(catalogAddItemBean.getNewTabComponent())) {
						copyPartDataFromPreviousPart(inputBean.getRequestId(),new BigDecimal(inputBean.getLineItem()),catalogAddItemBean,lastPartId);
						lastPartId++;
					}else {
						engEvalProcess.updateCatalogAddItemWithScreenData(inputBean.getRequestId(),catalogAddItemBean);
						lastPartId = catalogAddItemBean.getPartId().intValue();
					}
				}
			}
			//updating catalog_add_request_new data
			engEvalProcess.updateCatalogAddRequestNew(inputBean,personnelBean);
		}catch (Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	}

	private void copyPartDataFromPreviousPart(BigDecimal requestId, BigDecimal lineItem, CatalogAddItemBean bean, int previousPartId) throws Exception {
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,line_item,item_id,part_id,starting_view,material_desc,manufacturer,part_size,");
			query.append("size_unit,pkg_style,mfg_catalog_id,dimension,grade,mfg_trade_name,netwt,netwt_unit,customer_msds_number,replaces_msds,aerosol_container,radioactive,nanomaterial)");
			query.append(" (select company_id,request_id,line_item,item_id,part_id+1,starting_view,");
			if (!StringHandler.isBlankString(bean.getMaterialDesc())) {
				query.append(SqlHandler.delimitString(bean.getMaterialDesc())).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getManufacturer())) {
				query.append(SqlHandler.delimitString(bean.getManufacturer())).append(",");
			}else {
				query.append("null,");
			}
			if (bean.getPartSize() != null) {
				query.append(bean.getPartSize()+",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getSizeUnit()) && !"Select One".equalsIgnoreCase(bean.getSizeUnit())) {
				query.append(SqlHandler.delimitString(bean.getSizeUnit())).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getPkgStyle()) && !"Select One".equalsIgnoreCase(bean.getPkgStyle())) {
				query.append(SqlHandler.delimitString(bean.getPkgStyle())).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getMfgCatalogId())) {
				query.append(SqlHandler.delimitString(bean.getMfgCatalogId())).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getDimension())) {
				query.append(SqlHandler.delimitString(bean.getDimension())).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getGrade())) {
				query.append(SqlHandler.delimitString(bean.getGrade())).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getMfgTradeName())) {
				query.append(SqlHandler.delimitString(bean.getMfgTradeName())).append(",");
			}else {
				query.append("null,");
			}
			if (bean.getNetwt() != null) {
				query.append(bean.getNetwt()).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getNetwtUnit()) && !"Select One".equalsIgnoreCase(bean.getNetwtUnit())) {
				query.append("'"+bean.getNetwtUnit()).append("',");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getCustomerMsdsNumber())) {
				query.append(SqlHandler.delimitString(bean.getCustomerMsdsNumber())).append(",");
			}else {
				query.append("null,");
			}
			if (!StringHandler.isBlankString(bean.getReplacesMsds())) {
				query.append(SqlHandler.delimitString(bean.getReplacesMsds())).append(",");
			}else {
				query.append("null,");
			}
			if ("true".equals(bean.getAerosolContainer()) || "Y".equals(bean.getAerosolContainer())) {
				query.append("'Y',");
			}else {
				query.append("null,");
			}
			if ("true".equals(bean.getRadioactive()) || "Y".equals(bean.getRadioactive())) {
				query.append("'Y',");
			}else {
				query.append("null,");
			}
			if ("true".equals(bean.getNanomaterial()) || "Y".equals(bean.getNanomaterial())) {
				query.append("'Y'");
			}else {
				query.append("null");
			}
			query.append(" from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem).append(" and part_id = ").append(previousPartId).append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	}

	public CatAddHeaderViewBean getNewCatalogItemData(BigDecimal requestId, BigDecimal lineItem, PersonnelBean personnelBean) throws BaseException {
		return getNewCatalogItemData(requestId, lineItem, personnelBean, null);
	}

	public CatAddHeaderViewBean getNewCatalogItemData(BigDecimal requestId, BigDecimal lineItem, PersonnelBean personnelBean, Vector<BigDecimal> partIdColl) throws BaseException {
		CatAddHeaderViewBean catAddHeaderViewBean = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			//get catalog_add_request_new data
			Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(requestId);
			catAddHeaderViewBean = headerColl.get(0);
			catAddHeaderViewBean.setRequestId(requestId);

			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			//get catalog_add_item data
			engEvalProcess.setLineItem(lineItem);
			Collection catalogAddItemColl = engEvalProcess.getCatalogAddItem(requestId,partIdColl);
			if (catalogAddItemColl.size() == 0) {
				CatalogAddItemBean catalogAddItemBean = new CatalogAddItemBean();
				catalogAddItemBean.setRequestId(requestId);
				catalogAddItemBean.setLineItem(lineItem);
				catalogAddItemColl.add(catalogAddItemBean);
			}
			catAddHeaderViewBean.setCatalogAddItemColl(catalogAddItemColl);
			//get size units
			catAddHeaderViewBean.setSizeUnitViewColl(engEvalProcess.getSizeUnitView());
			//get net wt size units
			catAddHeaderViewBean.setNetWtSizeUnitColl(engEvalProcess.getNetWtSizeUnit());
			//get shipping weight units
			catAddHeaderViewBean.setShippingWeightUnitColl(engEvalProcess.getShippingWeightSizeUnit(false));
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return catAddHeaderViewBean;
	}

	public Collection getCatAddHeaderView(BigDecimal requestId) throws BaseException {
		genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
		StringBuilder query = new StringBuilder("");
		//the reason for this is because tcm_ops points to different facility and catalog then clients
		if ("TCM_OPS".equalsIgnoreCase(getClient()) || "CATALOG_VENDOR".equalsIgnoreCase(getClient())) {
			query.append("select carn.*, fx_personnel_id_to_name(carn.requestor) requestor_name,fx_personnel_id_to_phone(carn.requestor) requestor_phone,");
			query.append("fx_personnel_id_to_email(carn.requestor) requestor_email,carn.eng_eval_facility_id facility_name,carn.catalog_id catalog_desc,'N' allow_room_temp_out_time,'N' allow_mixture");
			query.append(",vcast.description starting_view_desc, fx_personnel_id_to_name(carn.resubmit_requestor) resubmit_requestor_name,0 facility_max_resubmittal");
			query.append(",vcars.request_status_desc,fx_personnel_id_to_phone(carn.resubmit_requestor) resubmit_requestor_phone,fx_personnel_id_to_email(carn.resubmit_requestor) resubmit_requestor_email");
			query.append(",vcast.new_material,vcast.new_part");
			query.append(" from catalog_add_request_new carn, vv_catalog_add_starting_view vcast, vv_catalog_add_request_status vcars");
			query.append(" where carn.request_id = ").append(requestId.toString());
			query.append(" and carn.starting_view = vcast.starting_view");
			query.append(" and carn.company_id = vcars.company_id and carn.request_status = vcars.request_status");
		}else {
			query.append("select carn.*, fx_personnel_id_to_name(carn.requestor) requestor_name,fx_personnel_id_to_phone(carn.requestor) requestor_phone,");
			query.append("fx_personnel_id_to_email(carn.requestor) requestor_email,f.facility_name,c.catalog_desc,c.allow_room_temp_out_time,f.allow_mixture");
			query.append(",vcast.description starting_view_desc, fx_personnel_id_to_name(carn.resubmit_requestor) resubmit_requestor_name,f.max_resubmittal facility_max_resubmittal");
			query.append(",vcars.request_status_desc,fx_personnel_id_to_phone(carn.resubmit_requestor) resubmit_requestor_phone,fx_personnel_id_to_email(carn.resubmit_requestor) resubmit_requestor_email,cc.customer_msds_db");
			query.append(",f.mix_ratio_required, cf.cat_add_allow_multiple_hmrb");
			query.append(",vcast.new_material,vcast.new_part");
			query.append(" from catalog_add_request_new carn, facility$ f, catalog$ c, vv_catalog_add_starting_view vcast, vv_catalog_add_request_status$ vcars,catalog_company$ cc, catalog_facility$ cf");
			query.append(" where carn.request_id = ").append(requestId.toString());
			query.append(" and carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id and carn.starting_view = vcast.starting_view");
			query.append(" and carn.company_id = c.company_id and carn.catalog_company_id = c.catalog_company_id and carn.catalog_id = c.catalog_id");
			query.append(" and carn.company_id = vcars.company_id and carn.request_status = vcars.request_status and carn.company_id = cc.company_id and carn.catalog_company_id = cc.catalog_company_id and carn.catalog_id = cc.catalog_id");
			query.append(" and carn.company_id = cf.company_id and carn.catalog_company_id = cf.catalog_company_id and carn.catalog_id = cf.catalog_id and carn.eng_eval_facility_id = cf.facility_id");
		}
		return genericSqlFactory.selectQuery(query.toString(),connection);
	}

	private StringBuilder createNewCatalogAddItem(BigDecimal requestId, BigDecimal lineItem, BigDecimal startingView, BigDecimal partId) {
		StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,part_id,line_item,starting_view) (select company_id,request_id,").append(partId == null?"1":partId).append(",").append(lineItem).append(",").append(startingView);
		query.append(" from catalog_add_request_new where request_id = "+requestId+")");
		return query;
	}


	public BigDecimal buildNewMaterial(BigDecimal requestId) throws Exception{
		HashMap results = buildNewMaterial(requestId, null);
		return (BigDecimal)results.get("lineItem");
	}

	public HashMap buildNewMaterial(BigDecimal requestId, String addToLineItem) throws Exception{
		BigDecimal lineItem = null;
		StringBuilder query = null;
		BigDecimal partId = null;
		Vector<BigDecimal> partIdColl = new Vector<BigDecimal>();
		HashMap results = new HashMap();
		try {
			BigDecimal startingView = new BigDecimal(CatalogAddRequestProcess.NEW_MATERIAL);
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			if(!StringHandler.isBlankString(addToLineItem))
			{
				lineItem = new BigDecimal(addToLineItem);
				query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
				partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
				partIdColl.add(partId);
			}
			else
			{
				query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_item where request_id = ").append(requestId);
				lineItem = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
				partIdColl.add(new BigDecimal(1));
			}
			results.put("lineItem", lineItem);
			results.put("partIdColl", partIdColl);
			query = createNewCatalogAddItem(requestId,lineItem,startingView,partId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return results;
	} //end of method

	private StringBuilder copyCatalogAddItemByItem(BigDecimal requestId, BigDecimal lineItem, BigDecimal itemId, BigDecimal startingView, BigDecimal padding) {
		StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,line_item,part_id,material_id,material_desc,grade,mfg_id,manufacturer,");
		query.append("mfg_trade_name,mfg_catalog_id,starting_view)");
		query.append(" (select carn.company_id,carn.request_id,").append(lineItem).append(",p.part_id+").append(padding).append(",p.material_id,m.material_desc,p.grade,mfg.mfg_id,mfg.mfg_desc,");
		query.append("m.trade_name,p.mfg_part_no,").append(startingView);
		query.append(" from catalog_add_request_new carn, part p, material m, manufacturer mfg where carn.request_id = ").append(requestId).append(" and p.item_id = ").append(itemId);
		query.append(" and p.material_id = m.material_id and m.mfg_id = mfg.mfg_id)");
		return query;
	}

	private CatalogAddItemBean custMsdsNumFromXref(GenericSqlFactory genericSqlFactory, BigDecimal requestId, BigDecimal materialId) throws Exception
	{
		CatalogAddItemBean materialData = new CatalogAddItemBean();
		StringBuilder query = new StringBuilder("SELECT min(customer_msds_number) FROM customer_msds_xref WHERE material_id = ");
		query.append(materialId).append(" AND customer_msds_db = (SELECT cc.customer_msds_db FROM catalog_add_request_new carn, catalog_company cc WHERE carn.catalog_id = cc.catalog_id and carn.catalog_company_id = cc.catalog_company_id and carn.company_id = cc.company_id and carn.request_id = ");
		query.append(requestId).append(")");
		materialData.setCustomerMsdsNumber(genericSqlFactory.selectSingleValue(query.toString(),connection));
		return materialData;
	}

	private CatalogAddItemBean custDataFromMixMat(GenericSqlFactory genericSqlFactory, BigDecimal requestId, BigDecimal materialId, String customerMixtureNumber) throws Exception
	{
		CatalogAddItemBean kitData = new CatalogAddItemBean();
		StringBuilder query = new StringBuilder("SELECT mm.customer_msds_number, mm.amount, mm.size_unit, mx.mixture_desc FROM mixture mx, mixture_material mm, catalog_add_request_new carn, catalog_company cc WHERE mm.mixture_id = mx.mixture_id AND mm.material_id = ");
		query.append(materialId).append(" AND mx.CUSTOMER_MIXTURE_NUMBER = '").append(customerMixtureNumber).append("'");
		query.append(" and mx.customer_msds_db = cc.customer_msds_db and mx.company_id = cc.company_id and carn.company_id = cc.company_id");
		query.append(" and carn.catalog_company_id = cc.catalog_company_id and carn.catalog_id = cc.catalog_id");
		query.append(" and carn.request_id = ").append(requestId);
		Vector<CatalogAddItemBean> kitDataColl = (Vector<CatalogAddItemBean>)genericSqlFactory.selectQuery(query.toString(),connection);
		kitData = kitDataColl.firstElement();
		kitData.setCustomerMixtureNumber(customerMixtureNumber);
		return kitData;
	}

	private StringBuilder copyCatalogAddItemByMaterial(BigDecimal requestId, BigDecimal lineItem, BigDecimal materialId, BigDecimal startingView, BigDecimal partId, CatalogAddItemBean kitData) {
		StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,line_item,part_id,material_id,material_desc,mfg_id,manufacturer,");
		query.append("mfg_trade_name,starting_view");
		if(kitData != null)
		{
			query.append(",customer_msds_number,approved_cust_msds_number");
			if(!StringHandler.isBlankString(kitData.getCustomerMixtureNumber()))
			{
				query.append(",customer_mixture_number");
				query.append(",approved_cust_mixture_number");
				query.append(",mix_ratio_size_unit");
				query.append(",mix_ratio_amount");
				query.append(",mixture_desc");
			}

		}
		query.append(")");
		query.append(" (select carn.company_id,carn.request_id,").append(lineItem).append(",").append(partId == null?1:partId).append(",m.material_id,m.material_desc,mfg.mfg_id,mfg.mfg_desc,");
		query.append("m.trade_name,").append(startingView);
		if(kitData != null)
		{
			query.append(",'").append(kitData.getCustomerMsdsNumber()).append("'");
			query.append(",'").append(kitData.getCustomerMsdsNumber()).append("'");
			if(!StringHandler.isBlankString(kitData.getCustomerMixtureNumber()))
			{
				query.append(",'").append(StringHandler.emptyIfNull(kitData.getCustomerMixtureNumber())).append("'");
				query.append(",'").append(StringHandler.emptyIfNull(kitData.getCustomerMixtureNumber())).append("'");
				query.append(",'").append(StringHandler.emptyIfNull(kitData.getSizeUnit())).append("'");
				query.append(",").append(kitData.getAmount());
				query.append(",").append(SqlHandler.delimitString(StringHandler.emptyIfNull(kitData.getMixtureDesc())));
			}
		}
		query.append(" from catalog_add_request_new carn, material m, manufacturer mfg where carn.request_id = ").append(requestId).append(" and m.material_id = ").append(materialId);
		query.append(" and m.mfg_id = mfg.mfg_id)");
		return query;
	}

	public HashMap buildNewSizePackaging(BigDecimal requestId, BigDecimal itemId, BigDecimal materialId, String customerMixtureNumber, String addToLineItem, String insertCustMixNo) throws Exception{
		BigDecimal lineItem = null;
		BigDecimal partId = new BigDecimal(0);
		StringBuilder query = null;
		Vector<BigDecimal> partIdColl = new Vector<BigDecimal>();
		HashMap results = new HashMap();
		try {
			BigDecimal startingView = new BigDecimal(CatalogAddRequestProcess.NEW_SIZE_PACKAGING);
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			if(StringHandler.isBlankString((addToLineItem)))
			{
				query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_item where request_id = ").append(requestId);
				lineItem = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			}
			else
				lineItem =  new BigDecimal(addToLineItem);
			results.put("lineItem", lineItem);

			if (itemId != null) {
				//copy from passed in item id
				if(!StringHandler.isBlankString((addToLineItem)))
				{
					query = new StringBuilder("select nvl(max(part_id),1) from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
					partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
				}
				genericSqlFactory.setBean(new CatalogAddItemBean());
				query = new StringBuilder("select p.part_id+").append(partId).append(" part_id").append(" from catalog_add_request_new carn, part p, material m, manufacturer mfg where carn.request_id = ").append(requestId);
				query.append(" and p.item_id = ").append(itemId).append(" and p.material_id = m.material_id and m.mfg_id = mfg.mfg_id");
				Collection<CatalogAddItemBean> itemPartIds = genericSqlFactory.selectQuery(query.toString(),connection);
				for(CatalogAddItemBean bean: itemPartIds)
					partIdColl.add(bean.getPartId());
				query = copyCatalogAddItemByItem(requestId,lineItem,itemId,startingView,partId);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

				//update cai.customer_msds_number
				if (!StringHandler.isBlankString(customerMixtureNumber)) {
					updateCustomerMsdsNumberFromMixtureMaterial(requestId,lineItem,customerMixtureNumber);
				}else {
					updateCustomerMsdsNumberFromXref(requestId,lineItem);
				}
			}
			else if(!StringHandler.isBlankString(customerMixtureNumber))
			{
				genericSqlFactory.setBean(new CatalogAddItemBean());
				query = new StringBuilder("select f.allow_mixture from catalog_add_request_new carn, facility f");
				query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id and carn.request_id = ").append(requestId);
				String allowMixture = genericSqlFactory.selectSingleValue(query.toString(),connection);
				query = new StringBuilder("SELECT material_id FROM mixture_material WHERE MIXTURE_ID = (SELECT MIN (DISTINCT mixture_id) FROM mixture m, catalog_add_request_new carn, catalog_company cc WHERE m.customer_mixture_number = '").append(customerMixtureNumber).append("' and");
				query.append(" m.customer_msds_db = cc.customer_msds_db and m.company_id = cc.company_id and carn.company_id = cc.company_id and");
				query.append(" carn.catalog_company_id = cc.catalog_company_id and carn.catalog_id = cc.catalog_id and");
				query.append(" carn.request_id = ").append(requestId);
				query.append(")");
				Collection<CatalogAddItemBean> materialIds = genericSqlFactory.selectQuery(query.toString());
				for(CatalogAddItemBean bean : materialIds)
				{
					BigDecimal kitMaterialId = bean.getMaterialId();
					genericSqlFactory.setBean(new CatalogAddItemBean());
					query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
					partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
					partIdColl.add(partId);

					CatalogAddItemBean customerData = custDataFromMixMat(genericSqlFactory,requestId,kitMaterialId,customerMixtureNumber);
					if(StringHandler.isBlankString(insertCustMixNo)  || !"Y".equalsIgnoreCase(allowMixture))
						customerData.setCustomerMixtureNumber(null);

					query = copyCatalogAddItemByMaterial(requestId,lineItem,kitMaterialId,startingView,partId,customerData);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
			else {
				//copy from passed in material id
				if(!StringHandler.isBlankString((addToLineItem)))
				{
					query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
					partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
					partIdColl.add(partId);
				}
				else
					partId = new BigDecimal(1);

				partIdColl.add(partId);

				CatalogAddItemBean materialData = custMsdsNumFromXref(genericSqlFactory,requestId,materialId);

				query = copyCatalogAddItemByMaterial(requestId,lineItem,materialId,startingView,partId,materialData);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}

			results.put("partIdColl", partIdColl);
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return results;
	} //end of method

	public HashMap buildNewSizePackagingMsds(BigDecimal requestId, BigDecimal itemId, BigDecimal materialId, String customerMixtureNumber, Boolean insertCustMixtNo, String addToLineItem) throws Exception{
		BigDecimal lineItem = null;
		StringBuilder query = null;
		BigDecimal partId = null;
		Vector<BigDecimal> partIdColl = new Vector<BigDecimal>();
		HashMap results = new HashMap();

		try {
			BigDecimal startingView = new BigDecimal(CatalogAddRequestProcess.NEW_SIZE_PACKAGING);
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			if(StringHandler.isBlankString(addToLineItem))
			{
				query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_item where request_id = ").append(requestId);
				lineItem = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			}
			else
				lineItem = new BigDecimal(addToLineItem);
			results.put("lineItem", lineItem);

			if (customerMixtureNumber != null) {

				genericSqlFactory.setBean(new CatalogAddItemBean());
				query = new StringBuilder("select f.allow_mixture from catalog_add_request_new carn, facility f");
				query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id and carn.request_id = ").append(requestId);
				String allowMixture = genericSqlFactory.selectSingleValue(query.toString(),connection);
				Collection<CatalogAddItemBean> kitMaterialIds = genericSqlFactory.selectQuery("select material_id from table (pkg_catalog_add.fx_msds_mixture_data("+requestId+",'"+customerMixtureNumber+"'))");
				for(CatalogAddItemBean bean:kitMaterialIds)
				{
					BigDecimal kitMaterialId = bean.getMaterialId();
					query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
					partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
					partIdColl.add(partId);

					CatalogAddItemBean kitData = custDataFromMixMat(genericSqlFactory,requestId,kitMaterialId,customerMixtureNumber);
					if(!insertCustMixtNo || !"Y".equalsIgnoreCase(allowMixture))
						kitData.setCustomerMixtureNumber(null);

					query = new StringBuilder("select f.allow_mixture from catalog_add_request_new carn, facility f");
					query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id and carn.request_id = ").append(requestId);
					query = copyCatalogAddItemByMaterial(requestId,lineItem,kitMaterialId,startingView,partId,kitData);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
			else
			{
				//copy from passed in material id
				partId = null;
				if(!StringHandler.isBlankString(addToLineItem))
				{
					query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
					partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
					partIdColl.add(partId);
				}
				else
					partIdColl.add(new BigDecimal(1));

				CatalogAddItemBean materialData = custMsdsNumFromXref(genericSqlFactory,requestId,materialId);

				query = copyCatalogAddItemByMaterial(requestId,lineItem,materialId,startingView,partId,materialData);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
			results.put("partIdColl", partIdColl);
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}

		return results;
	} //end of method


	//public BigDecimal buildAddItem(BigDecimal requestId, BigDecimal itemId, BigDecimal startingView, String addToLineItem, String customerMixtureNumber) throws Exception{
	public BigDecimal buildAddItem(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws Exception{
		BigDecimal lineItem = null;
		BigDecimal partId = new BigDecimal(0);
		StringBuilder query = null;

		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			if(!StringHandler.isBlankString(inputBean.getLineItem()))
			{
				lineItem = new BigDecimal(inputBean.getLineItem());
				query = new StringBuilder("select nvl(max(part_id),1) from catalog_add_item where request_id = ").append(inputBean.getRequestId()).append(" and line_item = ").append(inputBean.getLineItem());
				partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
				query = copyCatalogAddItemByItem(inputBean.getRequestId(),lineItem,inputBean.getItemId(),inputBean.getStartingView(),partId);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//update cai.customer_msds_number
				updateCustomerMsdsNumberFromXref(inputBean.getRequestId(),lineItem);
				//copy data from fac_part_use_code or customer_msds_or_mixture_use
				copyApprovedHrmbToRequest(personnelBean.getCompanyId(),inputBean.getEngEvalFacilityId(),inputBean.getRequestId(),lineItem.intValue(),personnelBean,"catalogAddMsdsRequest");
			}else {
				query = new StringBuilder("select count(*) from catalog_part_item_group cpig, catalog_add_request_new carn");
				query.append(" where cpig.company_id = carn.catalog_company_id and cpig.catalog_id = carn.catalog_id and cpig.cat_part_no = carn.cat_part_no");
				query.append(" and cpig.item_id = ").append(inputBean.getItemId()).append(" and carn.request_id = ").append(inputBean.getRequestId());
				//if part and item is not in cpig or if the request line is for fadeout from QPL
				if (dataCountIsZero(query.toString()) || inputBean.getStartingView().intValue() == 5) {
					if(lineItem == null)
					{
						query = new StringBuilder("select count(*) from catalog_add_item where item_id = ").append(inputBean.getItemId()).append(" and request_id = ").append(inputBean.getRequestId());
						if (dataCountIsZero(query.toString())) {
							query = new StringBuilder("select nvl(max(line_item)+1,1) from catalog_add_item where request_id = ").append(inputBean.getRequestId());
							lineItem = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
							createCatalogAddItemFromGivenItem(inputBean.getRequestId(),lineItem,inputBean.getItemId(),inputBean.getStartingView());
							//copy data from fac_part_use_code or customer_msds_or_mixture_use
							copyApprovedHrmbToRequest(personnelBean.getCompanyId(),inputBean.getEngEvalFacilityId(),inputBean.getRequestId(),lineItem.intValue(),personnelBean,"catalogAddMsdsRequest");
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return lineItem;
	} //end of method

	private void copyApprovedHrmbToRequest(String companyId, String facilityId, BigDecimal requestId, int lineItem, PersonnelBean personnelBean, String copySource) {
		try {
			if (personnelBean.isFeatureReleased("HmrbTab",facilityId,companyId)) {
				StringBuilder query = new StringBuilder("select distinct msds_or_mixture_use_id hmrb_line_item from catalog_add_request_new carn, catalog_add_item cai, catalog_company cc, customer_msds_or_mixture_use cmu, vv_material_subcategory vmc");
				query.append(" where carn.company_id = cai.company_id and carn.company_id = cc.company_id and carn.request_id = cai.request_id");
				query.append(" and carn.catalog_company_id = cc.catalog_company_id and carn.catalog_id = cc.catalog_id");
				query.append(" and cc.company_id = cmu.company_id and cc.customer_msds_db = cmu.customer_msds_db");
				query.append(" and nvl(cai.customer_mixture_number,cai.customer_msds_number) = cmu.customer_msds_or_mixture_no");
				query.append(" and nvl(cmu.start_date,sysdate-1) < sysdate and nvl(cmu.end_date,sysdate+1) > sysdate");
				query.append(" and carn.company_id = vmc.company_id and carn.catalog_company_id = vmc.catalog_company_id and carn.catalog_id = vmc.catalog_id");
				query.append(" and cmu.material_subcategory_id = vmc.material_subcategory_id and vmc.material_subcategory_name <> 'Grandfathered data'");
				query.append(" and carn.request_id = ").append(requestId).append(" and cai.line_item = ").append(lineItem);
				genericSqlFactory.setBeanObject(new CatalogAddHmrbBean());
				Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
				while (iter.hasNext()) {
					CatalogAddHmrbBean tBean = (CatalogAddHmrbBean)iter.next();
					//starting copying data
					CatalogAddHmrbBean bean = new CatalogAddHmrbBean();
					bean.setCalledFrom(copySource);
					bean.setCompanyId(companyId);
					bean.setRequestId(requestId);
					bean.setHmrbLineItem(tBean.getHmrbLineItem());
					CatalogAddHmrbProcess hmrbProcess = new CatalogAddHmrbProcess(this.getClient(),this.getLocale());
					hmrbProcess.setFactoryConnection(genericSqlFactory,connection);
					hmrbProcess.copyHmrbToCatalogAddRequest(bean);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void updateCustomerMsdsNumberFromXref(BigDecimal requestId, BigDecimal lineItem) throws Exception{
		//update customer_msds_number
		StringBuilder query = new StringBuilder("update catalog_add_item caii set (customer_msds_number,approved_cust_msds_number) = (");
		query.append("select customer_msds_number,customer_msds_number from (select material_id,customer_msds_number from (");
		query.append("select cmx.material_id,cmx.customer_msds_number,row_number() over (");
		query.append("partition by cmx.material_id order by 1) row_rank");
		query.append(" from catalog_add_item cai,catalog_add_request_new carn, catalog_company cc, customer_msds_xref cmx");
		query.append(" where cai.company_id = carn.company_id and cai.request_id = carn.request_id and carn.company_id = cc.company_id");
		query.append(" and carn.catalog_id = cc.catalog_id and cc.company_id = cmx.company_id");
		query.append(" and cc.customer_msds_db = cmx.customer_msds_db and cai.material_id = cmx.material_id");
		query.append(" and cai.request_id = ").append(requestId).append(") where row_rank = 1) x ");
		query.append(" where caii.material_id = x.material_id").append(")");
		query.append(" where caii.customer_msds_number is null and caii.request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
	}

	private void updateCustomerMsdsNumberFromMixtureMaterial(BigDecimal requestId, BigDecimal lineItem, String customerMixtureNumber) throws Exception{
		//update customer_msds_number
		StringBuilder query = new StringBuilder("update catalog_add_item cai set (customer_msds_number, approved_cust_msds_number) = ");
		query.append("(select customer_msds_number, customer_msds_number from mixture m, mixture_material mm, catalog_add_request_new carn, catalog_company cc");
		query.append(" where m.company_id = mm.company_id and m.mixture_id = mm.mixture_id");
		if (!StringHandler.isBlankString(customerMixtureNumber))
			query.append(" and m.customer_mixture_number = '").append(customerMixtureNumber).append("'");
		else
			query.append(" and m.customer_mixture_number = cai.customer_mixture_number");
		query.append(" and m.customer_msds_db = cc.customer_msds_db and m.company_id = cc.company_id and carn.company_id = cc.company_id");
		query.append(" and carn.catalog_company_id = cc.catalog_company_id and carn.catalog_id = cc.catalog_id");
		query.append(" and carn.request_id = ").append(requestId);
		query.append(" and mm.company_id = cai.company_id and mm.material_id = cai.material_id)");
		query.append(" where cai.customer_msds_number is null and cai.request_id = ").append(requestId).append(" and cai.line_item = ").append(lineItem);
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
	}


	public void createCatalogAddItemFromGivenItem(BigDecimal requestId, BigDecimal lineItem, BigDecimal itemId, BigDecimal startingView) throws Exception{
		StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,line_item,item_id,part_id,part_size,size_unit,pkg_style,components_per_item,");
		query.append("dimension,netwt,netwt_unit,material_id,material_desc,grade,mfg_id,manufacturer,mfg_trade_name,mfg_catalog_id,starting_view");
		query.append(",nanomaterial,aerosol_container,radioactive)");
		query.append(" (select carn.company_id,carn.request_id,"+lineItem+",p.item_id,p.part_id,p.part_size, p.size_unit,p.pkg_style,p.components_per_item,");
		query.append("p.dimension,p.net_wt,p.net_wt_unit,p.material_id,m.material_desc,p.grade,mfg.mfg_id,mfg.mfg_desc,m.trade_name,p.mfg_part_no,").append(startingView);
		query.append(",coalesce(sc.nanomaterial, s.nanomaterial) nanomaterial,decode(coalesce(sc.physical_state, s.physical_state), 'liquid/aerosol', 'Y', 'N') aerosol_container,coalesce(sc.radioactive, s.radioactive) radioactive");
		query.append(" from manufacturer mfg,company_msds sc,global.msds s,global.material m,global.part p,catalog_add_request_new carn");
		query.append(" where mfg.mfg_id = m.mfg_id and sc.company_id (+) = user and sc.revision_date(+) = s.revision_date and sc.material_id (+) = s.material_id and");
		query.append(" s.revision_date (+) = m.last_msds_revision_date and");
		query.append(" s.material_id (+) = m.material_id and m.material_id = p.material_id and");
		query.append(" p.item_id = ").append(itemId).append(" and carn.request_id = ").append(requestId).append(")");
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

		//update customer_mixture_number if facility allow_mixture
		query = new StringBuilder("select f.allow_mixture from catalog_add_request_new carn, facility f");
		query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id and carn.request_id = ").append(requestId);
		if ("Y".equals(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
			//update customer_mixture_number
			query = new StringBuilder("update catalog_add_item cai set (customer_mixture_number,approved_cust_mixture_number,mixture_desc,mix_ratio_amount,mix_ratio_size_unit,calc_mix_ratio_amount,calc_mix_ratio_size_unit) = (");
			query.append(" select m.customer_mixture_number,m.customer_mixture_number,m.mixture_desc,mm.amount,mm.size_unit,round(fx_item_weight(cai.item_id, cai.material_id) / fx_item_weight(cai.item_id) * 100,2),'%(w/w)'");
			query.append(" from catalog_add_request_new carn, catalog_company cc, mixture m, item_mixture im, mixture_material mm");
			query.append(" where cai.company_id = carn.company_id and cai.request_id = carn.request_id and carn.company_id = cc.company_id");
			query.append(" and carn.catalog_id = cc.catalog_id and cc.company_id = im.company_id and cc.customer_msds_db = im.customer_msds_db");
			query.append(" and m.company_id = mm.company_id and m.customer_msds_db = im.customer_msds_db and m.mixture_id = mm.mixture_id and cai.material_id = mm.material_id");
			query.append(" and cai.item_id = im.item_id and im.mixture_id = m.mixture_id)");
			query.append(" where cai.request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}

		//update customer_msds_number from mixture_material
		updateCustomerMsdsNumberFromMixtureMaterial(requestId,lineItem,null);
		//update customer_msds_number from xref
		updateCustomerMsdsNumberFromXref(requestId,lineItem);



		//update storage temp
		query = new StringBuilder("update catalog_add_item x set (customer_temperature_id, room_temp_out_time, room_temp_out_time_unit, shelf_life_days, shelf_life_basis) = (");
		query.append("select cpic.customer_temperature_id, cpic.room_temp_out_time, cpic.room_temp_out_time_unit, cpic.shelf_life_days, cpic.shelf_life_basis");
		query.append(" from catalog_add_request_new carn,catalog_part_item_component cpic");
		query.append(" where cpic.part_id = x.part_id and cpic.item_id = x.item_id and");
		query.append(" cpic.part_group_no = carn.part_group_no and cpic.cat_part_no = carn.cat_part_no and");
		query.append(" cpic.catalog_id = carn.catalog_id and cpic.catalog_company_id = carn.catalog_company_id and");
		query.append(" cpic.company_id = carn.company_id and carn.request_id = x.request_id)");
		query.append(" where exists (");
		query.append("select cpic.customer_temperature_id, cpic.room_temp_out_time, cpic.room_temp_out_time_unit, cpic.shelf_life_days, cpic.shelf_life_basis");
		query.append(" from catalog_add_request_new carn,catalog_part_item_component cpic");
		query.append(" where cpic.part_id = x.part_id and cpic.item_id = x.item_id and");
		query.append(" cpic.part_group_no = carn.part_group_no and cpic.cat_part_no = carn.cat_part_no and");
		query.append(" cpic.catalog_id = carn.catalog_id and cpic.catalog_company_id = carn.catalog_company_id and");
		query.append(" cpic.company_id = carn.company_id and carn.request_id = x.request_id) and request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

		//update het_usage_recording
		query = new StringBuilder("update catalog_add_item x set het_usage_recording = (");
		query.append("select cpig.het_usage_recording");
		query.append(" from catalog_add_request_new carn, catalog_part_item_group cpig");
		query.append(" where cpig.item_id = x.item_id and cpig.part_group_no = carn.part_group_no and");
		query.append(" cpig.cat_part_no = carn.cat_part_no and cpig.catalog_id = carn.catalog_id and");
		query.append(" cpig.company_id = carn.catalog_company_id and carn.request_id = x.request_id)");
		query.append(" where exists (select cpig.het_usage_recording");
		query.append(" from catalog_add_request_new carn,catalog_part_item_group cpig");
		query.append(" where cpig.item_id = x.item_id and cpig.part_group_no = carn.part_group_no and");
		query.append(" cpig.cat_part_no = carn.cat_part_no and cpig.catalog_id = carn.catalog_id and");
		query.append(" cpig.company_id = carn.catalog_company_id and carn.request_id = x.request_id) and request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

		//update vocet status and coat category for components
		query = new StringBuilder("update catalog_add_item cai set (vocet_status_id,vocet_coat_category_id) = ");
		query.append("( select vocet_status,vocet_category_id from vocet_msds vm, catalog_add_request_new carn, catalog_company cc");
		query.append(" where carn.company_id = cc.company_id and carn.catalog_company_id = cc.catalog_company_id");
		query.append(" and carn.catalog_id = cc.catalog_id and carn.company_id = vm.company_id and carn.eng_eval_facility_id = vm.facility_id");
		query.append(" and cc.customer_msds_db = vm.customer_msds_db and carn.company_id = cai.company_id and carn.request_id = cai.request_id");
		query.append(" and vm.customer_msds_or_mixture_no = cai.customer_msds_number) where cai.request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

		//update vocet status and coat category for kit/mixture
		query = new StringBuilder("update catalog_add_item cai set (vocet_status_id,vocet_coat_category_id) = ");
		query.append("( select vocet_status,vocet_category_id from vocet_msds vm, catalog_add_request_new carn, catalog_company cc");
		query.append(" where carn.company_id = cc.company_id and carn.catalog_company_id = cc.catalog_company_id");
		query.append(" and carn.catalog_id = cc.catalog_id and carn.company_id = vm.company_id and carn.eng_eval_facility_id = vm.facility_id");
		query.append(" and cc.customer_msds_db = vm.customer_msds_db and carn.company_id = cai.company_id and carn.request_id = cai.request_id");
		query.append(" and vm.customer_msds_or_mixture_no = cai.customer_mixture_number) where cai.request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

	} //end of method

	public void getStorageData(CatAddHeaderViewBean bean) throws Exception {
		String query = "select cas.company_id, cas.application,fla.application_desc, cas.maximum_quantity_stored, cas.average_quantity_stored, cas.request_id"+
				" from catalog_add_storage cas,catalog_add_request_new carn,fac_loc_app$ fla where cas.company_id = carn.company_id and cas.request_id = carn.request_id" +
				" and cas.company_id = fla.company_id and cas.application = fla.application" +
				" and carn.eng_eval_facility_id = fla.facility_id" +
				" and cas.request_id =" + bean.getRequestId() + " and cas.company_id = '"+bean.getCompanyId()+"'";
		genericSqlFactory.setBeanObject(new CatalogAddStorageBean());
		bean.setStorageDataColl(genericSqlFactory.selectQuery(query.toString(),connection));
	}

	public String saveStorageData(CatAddHeaderViewBean inputBean, Collection<CatalogAddStorageBean> beans) throws Exception {
		dbManager = new DbManager(getClient(),this.getLocale());
		connection = dbManager.getConnection();
		genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			for(CatalogAddStorageBean bean : beans)
			{
				if("Y".equalsIgnoreCase(bean.getNewRow()))
				{
					String query = "insert into catalog_add_storage (COMPANY_ID, REQUEST_ID, APPLICATION, MAXIMUM_QUANTITY_STORED, AVERAGE_QUANTITY_STORED) values ('" +
							bean.getCompanyId() + "', " + bean.getRequestId()  + ", '" + bean.getApplication() + "', " + (bean.getMaximumQuantityStored() == null ? "''": bean.getMaximumQuantityStored()) + " , " + (bean.getAverageQuantityStored() == null ? "''": bean.getAverageQuantityStored()) + ")";
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
				else if("Y".equalsIgnoreCase(bean.getUpdated()))
				{
					String query = "update catalog_add_storage set MAXIMUM_QUANTITY_STORED = " +  bean.getMaximumQuantityStored() + ", AVERAGE_QUANTITY_STORED = " + bean.getAverageQuantityStored() +
							" where COMPANY_ID = '" + bean.getCompanyId() + "' and REQUEST_ID = " + bean.getRequestId() + " and application = '" + bean.getApplication() + "'";
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch (Exception e) {
			log.error(e);
			return library.getString("msg.tcmiserror");
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}

		return "OK";
	}

	public void reloadStorageData(CatAddHeaderViewBean bean, PersonnelBean personnelBean) throws Exception {
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(bean.getRequestId());
			CatAddHeaderViewBean catAddHeaderViewBean = headerColl.get(0);
			BeanHandler.copyAttributes(catAddHeaderViewBean, bean);
			getStorageData(bean);
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}

	public String deleteStorageData(String requestId, String application, String companyId) throws Exception {
		dbManager = new DbManager(getClient(),this.getLocale());
		connection = dbManager.getConnection();
		genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			String query = "delete from catalog_add_storage where COMPANY_ID = '"+companyId+"' and REQUEST_ID = "+requestId+" and APPLICATION = '" +application+ "'";
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

		}catch (Exception e) {
			log.error(e);
			return library.getString("msg.tcmiserror");
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}

		return "OK";
	}

	public Collection getWorkArea(String requestId, String appDesc,String existingWorkAreas, String companyId) throws Exception
	{
		dbManager = new DbManager(getClient(),this.getLocale());
		connection = dbManager.getConnection();
		genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			String query = "select fla.application, fla.application_desc from fac_loc_app fla, catalog_add_request_new carn" +
					" where fla.company_id = carn.company_id and fla.facility_id = carn.eng_eval_facility_id and fla.status = 'A'" +
					" and lower(fla.application_desc) like lower('%"+appDesc+"%')" +
					" and carn.request_id = " + requestId +
					" and carn.company_id = '"+companyId+ "'" +
					(existingWorkAreas != null && existingWorkAreas.length() > 0 && !existingWorkAreas.equalsIgnoreCase("''") ? " and fla.application not in ("+existingWorkAreas.substring(0,existingWorkAreas.lastIndexOf(","))+")": "");

			genericSqlFactory.setBeanObject(new CatalogAddStorageBean());
			return  genericSqlFactory.selectQuery(query,connection);

		}catch (Exception e) {
			log.error(e);
			return null;
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}

	public String saveSingleStorageRecord(String application, BigDecimal requestId, String companyId) throws Exception {
		dbManager = new DbManager(getClient(),this.getLocale());
		connection = dbManager.getConnection();
		genericSqlFactory = new GenericSqlFactory(dbManager);
		try {

			String query = "insert into catalog_add_storage (COMPANY_ID, REQUEST_ID, APPLICATION, MAXIMUM_QUANTITY_STORED, AVERAGE_QUANTITY_STORED) values ('" +
					companyId + "', " + requestId  + ", '" + application + "', null , null )";
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

		}catch (Exception e) {
			log.error(e);
			return library.getString("msg.tcmiserror");
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}

		return "OK";
	}

	private String getCRA(BigDecimal requestId) throws Exception {
		try {

			String query = "select cf.customer_cat_add_process from catalog_facility cf, catalog_add_request_new carn" +
					" where cf.catalog_id = carn.catalog_id and cf.facility_id = carn.eng_eval_facility_id" +
					" and cf.catalog_company_id = carn.catalog_company_id and carn.request_id =" + requestId.toPlainString();
			return genericSqlFactory.selectSingleValue(query.toString(),connection);

		}catch (Exception e) {
			log.error(e);
			return library.getString("msg.tcmiserror");
		}
	}


	private String buildURLString(String requestID) throws Exception {

		String url = "";
		String cmsds = "";
		String csmnum = "";
		String query = "";

		try {
			genericSqlFactory.setBean(new NewChemUrlViewBean());

			query = "select * from new_chem_url_view where request_id = " + requestID;
			Vector<NewChemUrlViewBean> newChemUrlViewBeans = (Vector<NewChemUrlViewBean>)genericSqlFactory.selectQuery(query,connection);

			boolean firstTime = true;
			String tmpBuilding = "";
			String tmpWorkArea = "";
			String tmpProposeUse = "";
			for(NewChemUrlViewBean newChemUrlViewBean : newChemUrlViewBeans) {
				String application = newChemUrlViewBean.getApplication();
				if (firstTime) {
					url += "?facilid="+URLEncoder.encode(encodeSpecialCharacterForCRA(newChemUrlViewBean.getFacilityId()));
					if ("All".equalsIgnoreCase(application)) {
						tmpBuilding += "%20";
					}else {
						tmpBuilding = URLEncoder.encode(encodeSpecialCharacterForCRA(application.substring(0,application.indexOf("/"))));
					}
					cmsds = newChemUrlViewBean.getCustomerMsdsNumber();
					csmnum = newChemUrlViewBean.getCatPartNo();
					url += "&productname="+URLEncoder.encode(encodeSpecialCharacterForCRA(newChemUrlViewBean.getMfgTradeName()));
					url += "&containertype="+URLEncoder.encode(encodeSpecialCharacterForCRA(newChemUrlViewBean.getPkgStyle()));
					url += "&containersize="+URLEncoder.encode(encodeSpecialCharacterForCRA(newChemUrlViewBean.getPartSize()));
					url += "&containerunit="+URLEncoder.encode(encodeSpecialCharacterForCRA(newChemUrlViewBean.getSizeUnit()));
					url += "&kitflag="+URLEncoder.encode(encodeSpecialCharacterForCRA(newChemUrlViewBean.getKit()));
					url += "&radianpurchaseid="+requestID;
					tmpProposeUse += "&proposeduse="+URLEncoder.encode(encodeSpecialCharacterForCRA(newChemUrlViewBean.getProcessDesc()));
					firstTime = false;
				}
				//allow users to pick multiple work area
				if ("All".equalsIgnoreCase(application)) {
					tmpBuilding += "%20";
					tmpWorkArea += "%20";
				}else {
					tmpBuilding = URLEncoder.encode(encodeSpecialCharacterForCRA(application.substring(0,application.indexOf("/"))));
					if (tmpWorkArea.length() == 0) {
						tmpWorkArea += URLEncoder.encode(encodeSpecialCharacterForCRA(application.substring(application.indexOf("/")+1)));
					}else {
						tmpWorkArea += URLEncoder.encode("|"+encodeSpecialCharacterForCRA(application.substring(application.indexOf("/")+1)));
					}
				}
			}
			url += "&building="+tmpBuilding+"&location="+tmpWorkArea+tmpProposeUse;

			//1-12-02 decide whether to send MSDS and/or CSM or not
			if (!StringHandler.isBlankString(csmnum)) {
				query = "select msds_number from seagate_part_stage_view where catalog_id = 'Seagate CSM' and seagate_part_number = '"+csmnum+"'";
				//query += " group by seagate_part_number,catalog_id";
				String seagateMSDS = "";

				seagateMSDS = genericSqlFactory.selectSingleValue(query.toString(),connection);

				if (!StringHandler.isBlankString(seagateMSDS)) {
					url += "&msdsnumber="+addSpaceForUrl(seagateMSDS);
					url += "&csmnumber="+addSpaceForUrl(csmnum);
				}
			}else {
				if (!StringHandler.isBlankString(cmsds)) {
					query = "select count(*) from seagate_part where msds_number = '"+cmsds+"'";
					if (Integer.parseInt(genericSqlFactory.selectSingleValue(query,connection)) > 0) {
						url += "&msdsnumber="+ addSpaceForUrl(cmsds);
					}
				}
			}
		}catch (Exception e) {
			log.error(e);
			return library.getString("msg.tcmiserror");
		}

		return url;
	}

	private String encodeSpecialCharacterForCRA(String val) {
		if(!StringHandler.isBlankString(val)) {
			val = val.replace("<","&lt;");
			val = val.replace(">","&gt;");
			val = val.replace("'","&#39;");
			val = val.replace("\"","&quot;");
			val = val.replace("(","&#40;");
			val = val.replace(")","&#41;");
			val = val.replace("\\","&#92;");
			val = val.replace("/","&#47;");
		}
		return val;
	}

	public String addSpaceForUrl(String s) {
		StringTokenizer st = new StringTokenizer(s, " ");
		int i = 0;
		String result = "";
		if (st.countTokens() > 1) {
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				if (tok.startsWith("#")) {
					String temp = tok.substring(1);
					tok = "%23" + temp;
				}

				if (tok.equalsIgnoreCase("&")) {
					tok = "%26";
				}
				result += tok + "%20";
			}
			//removing the last '%20'
			result = result.substring(0, result.length() - 3);
		} else {
			result = s;
		}
		return result;
	}

	public void getCatAddRequestorEditMsdsId(CatAddHeaderViewBean bean) {
		try {
			StringBuilder query = new StringBuilder("");
			query.append("select cat_add_requestor_edit_msds_id from catalog c, catalog_facility cf, facility fac ");
			query.append("where c.company_id=cf.company_id and c.company_id=fac.company_id ");
			query.append("and c.catalog_id=cf.catalog_id and cf.facility_id=fac.facility_id ");
			query.append("and c.company_id='").append(bean.getCompanyId()).append("' ");
			query.append("and cf.facility_id='").append(bean.getEngEvalFacilityId()).append("' ");
			query.append("and cf.catalog_id='").append(bean.getCatalogId()).append("'");
			String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
			bean.setCatAddRequestorEditMsdsId(tmpVal);
		}catch (Exception e) {
			log.error(e);
		}
	}

	public Collection getMSDSColl(AutocompleteInputBean inputBean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerMaterialNumberBean());

		// This is for O'brien. We need to escape the ' in the name.
		String escapedSearchText = inputBean.getSearchText().replace("'", "''");
		StringBuilder query = new StringBuilder("select customer_msds_number from customer_msds_xref where ");
		query.append("customer_msds_number like '").append((inputBean.getSearchText()==null||inputBean.getSearchText().length()==0?"":escapedSearchText)).append("%' ");
		query.append("and customer_msds_db = ");
		query.append("(select cc.customer_msds_db from catalog_add_request_new carn, catalog_company cc where ");
		query.append("carn.catalog_id = cc.catalog_id and ").append("carn.catalog_company_id = cc.catalog_company_id and ");
		query.append("carn.company_id = cc.company_id and ");
		query.append("carn.request_id = ").append((inputBean.getRequestId()==null||inputBean.getRequestId().length()==0?"":inputBean.getRequestId())).append(")");
		query.append("and rownum<=").append((inputBean.getRowNum()==null||inputBean.getRowNum().length()==0?"20":inputBean.getRowNum()));

		return factory.selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FacAppEmissionPointBean> getEmissionPointColl(String facilityId) {
		try {
			StringBuilder query = new StringBuilder("select * from fac_app_emission_point");
			query.append(" where facility_id = ").append(SqlHandler.delimitString(facilityId));
			query.append(" and active = 'Y'");
			query.append(" order by fac_emission_point,app_emission_point,app_emission_point_desc");

			genericSqlFactory.setBean(new FacAppEmissionPointBean());
			return genericSqlFactory.selectQuery(query.toString());
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	public Collection<CaProcessEmissionPointBean> getRequestEmissionPoints(String facilityId, String workArea, BigDecimal requestId) {
		try {
			StringBuilder query = new StringBuilder("select cpep.*, faep.app_emission_point_desc emission_point_desc from ca_process_emission_point cpep, fac_app_emission_point faep");
			query.append(" where cpep.facility_id = ").append(SqlHandler.delimitString(facilityId));
			query.append(" and cpep.application = ").append(SqlHandler.delimitString(workArea));
			query.append(" and cpep.request_id = ").append(requestId);
			query.append(" and cpep.facility_id = faep.facility_id");
			query.append(" and cpep.application = faep.application");
			query.append(" and cpep.fac_emission_point = faep.fac_emission_point");
			query.append(" and cpep.app_emission_point = faep.app_emission_point");
			query.append(" order by faep.fac_emission_point,faep.app_emission_point,faep.app_emission_point_desc");

			genericSqlFactory.setBean(new CaProcessEmissionPointBean());
			return genericSqlFactory.selectQuery(query.toString());
		} catch (Exception e) {
			log.error(e);
		}
		
		return null;
	}
	
	public void updateEmissionPoints(CatalogAddUserGroupBean inputBean) throws Exception {
		StringBuilder query = null;
		try {
			query = new StringBuilder("delete from ca_process_emission_point");
			query.append(" where request_id = ").append(inputBean.getRequestId());
			query.append(" and facility_id = ").append(SqlHandler.delimitString(inputBean.getFacilityId()));
			query.append(" and application = ").append(SqlHandler.delimitString(inputBean.getWorkArea()));
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

			String[] selectedEmissionPointIds = inputBean.getEmissionPoints().split(Pattern.quote(inputBean.getEmissionPointIdSeparator() + inputBean.getEmissionPointIdSeparator()));
			for (String selectedEmissionPointId : selectedEmissionPointIds) {
				int separatorPos = selectedEmissionPointId.indexOf(inputBean.getEmissionPointIdSeparator());
				String appEmissionPoint = selectedEmissionPointId.substring(0, separatorPos);
				String facEmissionPoint = selectedEmissionPointId.substring(separatorPos + 1);

				query = new StringBuilder("insert into ca_process_emission_point (request_id,facility_id,application,fac_emission_point,app_emission_point) values");
				query.append("(").append(inputBean.getRequestId());
				query.append(",").append(SqlHandler.delimitString(inputBean.getFacilityId()));
				query.append(",").append(SqlHandler.delimitString(inputBean.getWorkArea()));
				query.append(",").append(SqlHandler.delimitString(facEmissionPoint));
				query.append(",").append(SqlHandler.delimitString(appEmissionPoint));
				query.append(")");

				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
} //end of class