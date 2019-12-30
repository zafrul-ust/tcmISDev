package com.tcmis.client.catalog.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ApprovalReviewMessageViewBean;
import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.CatAddStatusViewBean;
import com.tcmis.client.catalog.beans.CatalogAddApprovalBean;
import com.tcmis.client.catalog.beans.CatalogAddApprovalReviewBean;
import com.tcmis.client.catalog.beans.CatalogAddEmailBean;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.beans.CatalogAddPlannedUseBean;
import com.tcmis.client.catalog.beans.CatalogAddRequestNewBean;
import com.tcmis.client.catalog.beans.CatalogAddSpecBean;
import com.tcmis.client.catalog.beans.CatalogBean;
import com.tcmis.client.catalog.beans.CatalogFacilityBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.EngEvalHistoryBean;
import com.tcmis.client.catalog.beans.InventoryDetailInSupplyChainBean;
import com.tcmis.client.catalog.beans.InventoryDetailOnHandMaterialBean;
import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.client.common.beans.ChemicalApproverBean;
import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.beans.PrAccountBean;
import com.tcmis.client.common.beans.PreviousOrderEngEvalBean;
import com.tcmis.client.common.beans.SizeUnitViewBean;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.common.process.MsdsIndexingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Process for EngEvalProcess
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class EngEvalProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	DbManager dbManager;
	Connection connection = null;
	GenericSqlFactory genericSqlFactory;
	private ResourceLibrary library = null;
	String URL = "";
	BigDecimal lineItem = new BigDecimal(1);
	//
	static final int DONE_INDEX = 0;
	static final int EVAL_INDEX = 1;
	static final int NEXT_GROUP_INDEX = 2;
	static final int POSS_REQUEST_INDEX = 3;
	static final int MESSAGE_TO_APPROVER_INDEX = 4;
	static final int STARTING_VIEW_INDEX = 5;
	static final int REJECT_ON_FAILURE_APPROVAL_ROLE_INDEX = 6;
	static final int REJECT_ON_FAILURE_STATEMENT_INDEX = 7;
	//this variable keep track of whether approval group was set in catalog add request new
	//the reason is that we are looping thru the all the roles in the same group and sequence
	//to see if any can be auto approved.
	boolean approvalGroupSeqSet = false;

	static final String REQUESTOR_MSG_DELIMITER = "; ";
	private String nextStatusAction = "Submit";

	//this is to keep track if current approval role need/want to notify other approval roles
	Vector otherApprovalRoleNotification = new Vector();
	Vector notifyOnlyApprovalRoleV = new Vector();

	Hashtable approvalRoleAdditionalMessage = new Hashtable();

	static final String SDS_AUTHORING_STARTING_VIEW = "9";
	String msdsAuthorId = "23957";
	private PersonnelBean personnelBean;
	private boolean distributionCatalogAddRequest = false;

	public EngEvalProcess(String client) {
		super(client);
	}

	public EngEvalProcess(String client, String locale, String URL) {
		super(client, locale);
		this.URL = URL;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
		this.connection = connection;
		this.genericSqlFactory = genericSqlFactory;
	}

	public void setPersonnelBean(PersonnelBean personnelBean) {
		this.personnelBean = personnelBean;
	}

	public void setNextStatusAction(String action) {
		this.nextStatusAction = action;
	}

	public void advancePwaCatalogAddRequest() {
		try {
			StringBuilder query = new StringBuilder("select distinct request_id from catalog_add_request_new where company_id = 'UTC'");
			query.append(" and catalog_id like 'PWA%' and request_status = 17");
			advanceCatalogAddRequest(query,true);
		}catch (Exception e) {
			log.error(e);
		}
	}

	public void advanceDistributionCatalogAddRequest(BigDecimal prNumber) {
		try {
			StringBuilder query = new StringBuilder("select distinct carn.request_id from catalog_add_item cai,item i,catalog_part_item_group cpig,request_line_item rli,catalog_add_request_new carn");
			query.append(" where i.item_type = 'MX' and i.item_id = cpig.item_id and cpig.company_id = rli.catalog_company_id");
			query.append(" and cpig.catalog_id = rli.catalog_id and cpig.cat_part_no = rli.fac_part_no and cpig.part_group_no = rli.part_group_no");
			query.append(" and rli.release_date IS NOT NULL and rli.catalog_company_id = carn.catalog_company_id and rli.catalog_id = carn.catalog_id");
			query.append(" and rli.item_id = cai.item_id and cai.request_id = carn.request_id and carn.request_status = 5 and rli .pr_number = ").append(prNumber);
			distributionCatalogAddRequest = true;
			advanceCatalogAddRequest(query,false);
			distributionCatalogAddRequest = false;
		}catch (Exception e) {
			log.error(e);
		}
	}

	private void advanceCatalogAddRequest(StringBuilder query, boolean populateAdditionalCatalogAddData) {
		try {
			genericSqlFactory.setBean(new CatAddStatusViewBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(), connection).iterator();
			while (iter.hasNext()) {
				CatAddStatusViewBean catAddStatusViewBean = (CatAddStatusViewBean) iter.next();
				if (populateAdditionalCatalogAddData) {
					this.createCatalogAddItemQc(catAddStatusViewBean.getRequestId());
					this.populateCatalogAddItemLocale(catAddStatusViewBean.getRequestId());
				}
				approvalGroupSeqSet = false;
				this.setNextStatus(catAddStatusViewBean.getRequestId());
			}
		}catch (Exception e) {
			log.error(e);
		}
	}

	public void revertRequestToPreviouslyApprovedSpecApproval(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			//first get last approval role that has ability to edit_spec
			StringBuilder query = new StringBuilder("select caa.approval_role,cars.request_status,car.approval_group_seq");
			query.append(" from catalog_add_approval caa, vv_chemical_approval_role car, vv_catalog_add_request_status cars");
			query.append(" where caa.company_id = car.company_id and caa.catalog_company_id = car.catalog_company_id and");
			query.append(" caa.catalog_id = car.catalog_id and caa.facility_id = car.facility_id and");
			query.append(" caa.approval_role = car.approval_role and car.active = 'Y' and car.edit_spec = 'Y' and");
			query.append(" car.part_approval = 'Y' and car.role_function not in ('Verify Item','Verify Pricing','Verify Spec') and");
			query.append(" caa.chemical_approver <> -1 and car.company_id = cars.company_id and car.approval_group = cars.approval_group and");
			query.append(" caa.company_id = '").append(inputBean.getCompanyId()).append("' and caa.request_id = ").append(inputBean.getRequestId());
			query.append(" order by caa.approval_date desc");
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			CatalogAddApprovalBean bean = new CatalogAddApprovalBean();
			while (iter.hasNext()) {
				bean = (CatalogAddApprovalBean)iter.next();
				break;
			}
			if (!StringHandler.isBlankString(bean.getApprovalRole())) {
				//remove approval role from catalog_add_approval
				query = new StringBuilder("delete from catalog_add_approval where company_id = '").append(inputBean.getCompanyId()).append("'");
				query.append(" and request_id = ").append(inputBean.getRequestId()).append(" and approval_role = ").append(SqlHandler.delimitString(bean.getApprovalRole()));
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//reset catalog_add_request_new status to just deleted approval role
				query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = sysdate,request_status = ").append(bean.getRequestStatus());
				query.append(",approval_group_seq = ").append(bean.getApprovalGroupSeq());
				query.append(" where company_id = '").append(inputBean.getCompanyId()).append("'").append(" and request_id = ").append(inputBean.getRequestId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//resend email notification to approver(s)
				sendApproversEmail(inputBean.getRequestId(),"",new Vector(0),new Vector(0));
			}
		}catch (Exception e) {
			log.error(e);
		}
	}

	public void completedPreviousOrderedEval(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("update request_line_item set eval_comment = ").append(SqlHandler.delimitString(inputBean.getEvalComment()));
			query.append(",eval_completed_date = sysdate where pr_number = ").append(inputBean.getPrNumber());
			query.append(" and line_item = ").append(inputBean.getLineItem());
			genericSqlFactory.deleteInsertUpdate(query.toString());
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}

	public Collection getPreviousOrderedEval(CatAddHeaderViewBean inputBean, BigDecimal personnelId) throws Exception {
		Collection result = new Vector(0);
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			genericSqlFactory = new GenericSqlFactory(dbManager,new PreviousOrderEngEvalBean());
			StringBuilder query = new StringBuilder("select * from previous_ordered_eval_view where item_id = ").append(inputBean.getItemId());
			query.append(" and (company_id,facility_id) in (select company_id,facility_id from user_facility where personnel_id = ").append(personnelId).append(")");
			query.append(" order by submit_date desc, facility_name,application_desc");
			result = genericSqlFactory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public BigDecimal buildReorderRequest(CatalogInputBean inputBean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = new BigDecimal(-1);
		String query = "";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			query = "select request_seq.nextval from dual";
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query,connection));
			StringBuilder evalPartNo = new StringBuilder("");
			if ("RAYTHEON".equals(inputBean.getCompanyId())) {
				evalPartNo.append("RAY-");
			}
			evalPartNo.append("EVAL-").append(genericSqlFactory.selectSingleValue("select eval_seq.nextval from dual",connection));
			if (!StringHandler.isBlankString(inputBean.getCatalogAddCatalogCompanyId()) && !StringHandler.isBlankString(inputBean.getCatalogAddCatalogId())) {
				inputBean.setCatalogCompanyId(inputBean.getCatalogAddCatalogCompanyId());
				inputBean.setCatalogId(inputBean.getCatalogAddCatalogId());
			}else {
				getCatalogForFacility(inputBean);
			}
			createNewCatalogAddRequestNew(inputBean,personnelBean,result,"reorder",evalPartNo.toString());
			StringBuilder query2 = new StringBuilder("insert into catalog_add_item (request_id, material_desc, manufacturer, material_id, part_size, size_unit, ");
			query2.append("pkg_style, material_approved_by, material_approved_on, item_approved_by, item_approved_on, mfg_catalog_id, ");
			query2.append("part_id, material_type, case_qty, dimension, grade, mfg_trade_name, netwt, netwt_unit, company_id,");
			query2.append("customer_msds_number, components_per_item, sample_only, poss_size, poss_special_note, shelf_life_days,");
			query2.append("shelf_life_basis, storage_temp, catalog_component_item_id, catalog_component_id, customer_revision_date, label_color,mfg_id,");
			query2.append("line_item,item_id,starting_view)");
			query2.append(" (select carn.request_id, m.material_desc,mfg.mfg_desc manufacturer, p.material_id, p.part_size, p.size_unit,");
			query2.append("p.pkg_style,null material_approved_by,null material_approved_on,null item_approved_by,");
			query2.append("null item_approved_on,p.mfg_part_no mfg_catalog_id, p.part_id,null material_type, p.case_qty,");
			query2.append("p.dimension, p.grade, m.trade_name mfg_trade_name, p.net_wt, p.net_wt_unit,carn.company_id,");
			query2.append("null customer_msds_number, p.components_per_item,null sample_only,null poss_size,");
			query2.append("null poss_special_note,null shelf_life_days,null shelf_life_basis,null storage_temp,");
			query2.append("null catalog_component_item_id,null catalog_component_id,null customer_revision_date,null label_color,mfg.mfg_id,");
			query2.append("1,p.item_id,2");
			query2.append(" from catalog_add_request_new carn, part p, material m, manufacturer mfg where carn.request_id = ").append(result).append(" and p.item_id = ").append(inputBean.getSelectedItemId());
			query2.append(" and p.material_id = m.material_id and m.mfg_id = mfg.mfg_id)");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);

			query2 = new StringBuilder("insert into catalog_add_spec(spec_id,request_id,spec_name,spec_title,line_item,spec_source)");
			query2.append(" values('No Specification',").append(result).append(",'No Specification','No Specification',1,'catalog_add_spec')");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);

			query2 = new StringBuilder("insert into catalog_add_user_group(request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)");
			query2.append(" values(").append(result).append(",'").append(inputBean.getFacilityId()).append("','").append(inputBean.getApplicationId());
			query2.append("','All','Engineering Evaluation',0,0,'days',0,0,'days')");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);
			//create MR data
			buildEngEvalMr(inputBean,personnelBean,result,"reorder",evalPartNo.toString());
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

	public BigDecimal buildNewRequest(CatalogInputBean inputBean, PersonnelBean personnelBean) throws Exception{
		BigDecimal result = new BigDecimal(-1);
		String query = "";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			query = "select request_seq.nextval from dual";
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query,connection));
			StringBuilder evalPartNo = new StringBuilder("");
			if ("RAYTHEON".equals(inputBean.getCompanyId())) {
				evalPartNo.append("RAY-");
			}
			evalPartNo.append("EVAL-").append(genericSqlFactory.selectSingleValue("select eval_seq.nextval from dual",connection));
			if (!StringHandler.isBlankString(inputBean.getCatalogAddCatalogCompanyId()) && !StringHandler.isBlankString(inputBean.getCatalogAddCatalogId())) {
				inputBean.setCatalogCompanyId(inputBean.getCatalogAddCatalogCompanyId());
				inputBean.setCatalogId(inputBean.getCatalogAddCatalogId());
			}else {
				getCatalogForFacility(inputBean);
			}
			createNewCatalogAddRequestNew(inputBean,personnelBean,result,"new",evalPartNo.toString());
			StringBuilder query2 = new StringBuilder("insert into catalog_add_item (request_id,line_item,part_id) values (").append(result).append(",1,1)");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);

			query2 = new StringBuilder("insert into catalog_add_spec(spec_id,request_id,spec_name,spec_title,line_item,spec_source)");
			query2.append(" values('No Specification',").append(result).append(",'No Specification','No Specification',1,'catalog_add_spec')");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);

			query2 = new StringBuilder("insert into catalog_add_user_group(request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)");
			query2.append(" values(").append(result).append(",'").append(inputBean.getFacilityId()).append("','").append(inputBean.getApplicationId());
			query2.append("','All','Engineering Evaluation',0,0,'days',0,0,'days')");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);
			//create MR data
			buildEngEvalMr(inputBean,personnelBean,result,"new",evalPartNo.toString());
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

	private void buildEngEvalMr(CatalogInputBean inputBean, PersonnelBean personnelBean, BigDecimal requestId, String evalType, String evalPartNo) {
		try {
			String pkg = "";
			StringBuilder query = null;
			if ("reorder".equalsIgnoreCase(evalType)) {
				query = new StringBuilder("select substr(fx_kit_packaging(cai.item_id),0,instr(fx_kit_packaging(cai.item_id),' ',1)-1) from catalog_add_item cai where request_id = ").append(requestId);
				query.append(" and line_item = 1 and part_id = 1");  //there should only be one item for eng eval per request
				pkg = genericSqlFactory.selectSingleValue(query.toString(),connection);
			}
			BigDecimal prNumber = new BigDecimal(genericSqlFactory.selectSingleValue("select pr_seq.nextval from dual",connection));
			//purchase request
			query = new StringBuilder("insert into purchase_request ");
			query.append("(pr_status,request_date,engineering_evaluation,pr_number,requestor,request_id,facility_id,account_sys_id) ");
			query.append(" values('entered',sysdate,'Y',").append(prNumber).append(",").append(personnelBean.getPersonnelId());
			query.append(",").append(requestId).append(",'").append(inputBean.getFacilityId()).append("','").append(inputBean.getAccountSysId()).append("')");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//request line item
			//for eng eval the default charge type is as following
			//USE fac_loc_app.charge_type default first unless it's overrides by vv_account_sys.fac_item_charge_type_override
			//here's the logic for overriding fac_loc_app.charge_type_default
			//vv_account_sys.fac_item_charge_type_override == fac_item.part_charge_type OR vv_accoount_sys.fac_item_charge_type_override = a
			//then USE fac_item.part_charge_type
			//vv_account_sys.fac_item_charge_type_override:
			// d - and fac_item.part_charge_type = d then USE fac_item.part_charge_type (in this case it's d)
			// i - and fac_item.part_charge_type = i then USE fac_item.part_charge_type (in this case it's i)
			// a - always USE fac_item.part_charge_type
			// n - never override => USE fla.charge_type_default
			String defaultChargeType = "";
			if ("d".equals(inputBean.getFacLocAppChargeTypeDefault()) || "i".equals(inputBean.getFacLocAppChargeTypeDefault())) {
				defaultChargeType = inputBean.getFacLocAppChargeTypeDefault();
			}

			if (!StringHandler.isBlankString(inputBean.getFacItemChargeTypeOverride())) {
				if (inputBean.getFacItemChargeTypeOverride().equals(inputBean.getEngEvalPartType()) || "a".equals(inputBean.getFacItemChargeTypeOverride())) {
					if (!StringHandler.isBlankString(inputBean.getEngEvalPartType())) {
						defaultChargeType = inputBean.getEngEvalPartType();
					}
				}
			}

			query = new StringBuilder("insert into request_line_item (pr_number,line_item,application,fac_part_no,item_type,requested_item_type,request_line_status,");
			query.append("category_status,inventory_group,application_desc,ship_to_location_id,delivery_point,catalog_id,catalog_company_id,part_group_no,example_packaging,edit_charge_number,item_id");
			if (defaultChargeType.length() > 0) {
				query.append(",charge_type");
			}
			query.append(") ");
			query.append("select ").append(prNumber).append(",1,'").append(inputBean.getApplicationId()).append("','").append(evalPartNo);
			query.append("','OOR','OOR','Draft Eval','Draft Eval',");
			query.append("fla.inventory_group,fla.application_desc,decode(fla.dock_selection_rule,'FIXED',fla.location_id,'')");
			query.append(",decode(fla.delivery_point_selection_rule,'FIXED',fla.delivery_point,''),'");
			query.append(inputBean.getCatalogId()).append("','").append(inputBean.getCatalogCompanyId()).append("',1,'").append(pkg).append("'");
			query.append(",edit_charge_number");
			//for reorder selectecItemId should not be null
			if (inputBean.getSelectedItemId() != null) {
				query.append(","+inputBean.getSelectedItemId());
			}else {
				query.append(",''");
			}
			if (defaultChargeType.length() > 0) {
				query.append(",'").append(defaultChargeType).append("'");
			}
			query.append(" from fac_loc_app fla where facility_id = '").append(inputBean.getFacilityId()).append("' and application = '").append(inputBean.getApplicationId()).append("'");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	private void createNewCatalogAddRequestNew(CatalogInputBean inputBean, PersonnelBean personnelBean, BigDecimal requestId, String evalType, String evalPartNo) {
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_request_new (request_id,requestor,request_date,cat_part_no,catalog_id,");
			query.append("eng_eval_facility_id,catalog_company_id,eng_eval_work_area,account_sys_id,engineering_evaluation,");
			query.append("eval_status,request_status,starting_view,eval_type)");
			query.append(" values (").append(requestId).append(",").append(personnelBean.getPersonnelId()).append(",sysdate,'");
			query.append(evalPartNo).append("','").append(inputBean.getCatalogId()).append("','").append(inputBean.getFacilityId());
			query.append("','").append(inputBean.getCatalogCompanyId()).append("','").append(inputBean.getApplicationId());
			query.append("','").append(inputBean.getAccountSysId()).append("','Y','Pending Use Approval',");
			if ("new".equalsIgnoreCase(evalType)) {
				query.append("0,0,'new')");
			}else {
				query.append("2,2,'reorder')");
			}
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	private void getCatalogForFacility(CatalogInputBean inputBean) {
		try {
			StringBuilder query = new StringBuilder("select catalog_id,catalog_company_id from catalog_facility where facility_id = '").append(inputBean.getFacilityId());
			query.append("' and display = 'Y' and catalog_add_allowed = 'Y' order by catalog_id,catalog_company_id");
			genericSqlFactory.setBeanObject(new CatalogFacilityBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				CatalogFacilityBean bean = (CatalogFacilityBean) iter.next();
				inputBean.setCatalogId(bean.getCatalogId());
				inputBean.setCatalogCompanyId(bean.getCatalogCompanyId());
				break;
			}
		}catch (Exception e) {
			log.error(e);
		}
	}  //end of method


	public String rejectRequest(CatAddHeaderViewBean inputBean, Collection approvalRoleBeans, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		StringBuilder query = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder approvalRoles = new StringBuilder("");
			Iterator iter = approvalRoleBeans.iterator();
			while (iter.hasNext()) {
				CatAddStatusViewBean bean = (CatAddStatusViewBean)iter.next();
				if (!StringHandler.isBlankString(bean.getStatus())) {
					//keeping tracking of approval roles so I can determine whether to continue on reject or not
					if (approvalRoles.length() == 0) {
						approvalRoles.append("'").append(bean.getApprovalRole()).append("'");
					}else {
						approvalRoles.append(",'").append(bean.getApprovalRole()).append("'");
					}
					//insert approval role into catalog_add_approval
					if (!insertApprovalData(inputBean.getRequestId(),bean.getApprovalRole(),"Rejected",bean.getReason(),new BigDecimal(personnelBean.getPersonnelId()),"N")) {
						result = library.getString("error.db.update");
						break;
					}
				}
			}
			if ("OK".equalsIgnoreCase(result)) {
				if (continueOnRejection(inputBean,approvalRoles)) {
					this.nextStatusAction = "Rejected";
					setRequestNextStatus(inputBean,personnelBean);
				}else {
					//reject catalog add request
					query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, request_status = 7,approval_group_seq = 0,request_rejected = 'Y'");
					query.append(",eval_status = 'Rejected',last_updated = sysdate where request_id = ").append(inputBean.getRequestId());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//reject MR
					query = new StringBuilder("update purchase_request set pr_status = 'rejected',submitted_date = sysdate,last_updated = sysdate,last_updated_by = ").append(personnelBean.getPersonnelId());
					query.append(" where pr_number = ").append(inputBean.getPrNumber());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//send user email
					sendUserConfirmedEmail(inputBean.getRequestId());
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

	public boolean continueOnRejection(CatAddHeaderViewBean inputBean, StringBuilder approvalRoles) {
		boolean result = false;
		StringBuilder query = new StringBuilder("select count(*) from vv_chemical_approval_role vcar, catalog_add_request_new carn");
		query.append(" where carn.company_id = vcar.company_id and carn.eng_eval_facility_id = vcar.facility_id and carn.catalog_id = vcar.catalog_id");
		query.append(" and carn.request_id = ").append(inputBean.getRequestId());
		query.append(" and vcar.approval_role in (").append(approvalRoles).append(") and vcar.continue_on_rejection = 'N'");
		if (dataCountIsZero(query.toString())) {
			result = true;
		}
		return result;
	}

	public String approvalRequest(CatAddHeaderViewBean inputBean, Collection approvalRoleBeans, PersonnelBean personnelBean) throws Exception{
		String result = "OK";
		StringBuilder query = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			Iterator iter = approvalRoleBeans.iterator();
			while (iter.hasNext()) {
				CatAddStatusViewBean bean = (CatAddStatusViewBean)iter.next();
				if (!StringHandler.isBlankString(bean.getStatus())) {
					if (insertApprovalData(inputBean.getRequestId(),bean.getApprovalRole(),"Approved",bean.getReason(),new BigDecimal(personnelBean.getPersonnelId()),inputBean.getApprovedWithRestriction())) {
						//if approval role is before TCM QC then copy data from catalog_add_item to catalog_add_item_qc
						if ("BEFORE".equalsIgnoreCase(bean.getBeforeTcmQc()))
							createCatalogAddItemQc(inputBean.getRequestId());
						//if approval role is before TCM SPEC then copy data from catalog_add_spec to catalog_add_spec_qc
						if ("BEFORE".equalsIgnoreCase(bean.getBeforeTcmSpec()))
							createCatalogAddSpecQc(inputBean.getRequestId());
						//update for Eng Eval only
						if (inputBean.getPrNumber() != null && "Item QC".equalsIgnoreCase(bean.getApprovalRole())) {
							query = new StringBuilder("update request_line_item rli set example_packaging = (select substr(fx_kit_packaging(cai.item_id),0,instr(fx_kit_packaging(cai.item_id),' ',1)-1) from catalog_add_item cai where line_item = 1 and part_id = 1 and request_id = ");
							query.append(inputBean.getRequestId()).append(")").append(" where rli.pr_number = ").append(inputBean.getPrNumber());
							genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						}
					}else {
						result = library.getString("error.db.update");
						break;
					}
				}
			}
			if ("OK".equalsIgnoreCase(result)) {
				this.nextStatusAction = "Approved";
				setRequestNextStatus(inputBean,personnelBean);
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

	private void setRequestNextStatus(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws Exception {
		String[] resultData = setNextStatus(inputBean.getRequestId());
		String done = resultData[EngEvalProcess.DONE_INDEX];
		String eval = resultData[EngEvalProcess.EVAL_INDEX];
		String nextGroup = resultData[EngEvalProcess.NEXT_GROUP_INDEX];
		//send email to user if the end of approval process is reach.  Otherwise, continue to the next approval group
		if ("Done".equalsIgnoreCase(done)) {
			if ("y".equalsIgnoreCase(eval)) {
				sendUserConfirmedEmail(inputBean.getRequestId());
				//for eng eval call p_cat_add_item
				addItemToTable(inputBean.getRequestId());
				//next do MR logics
				StringBuilder query = new StringBuilder("update request_line_item rli set request_line_status = 'In Progress',item_id = (select cai.item_id from catalog_add_item cai where line_item = 1 and part_id = 1 and request_id = ");
				query.append(inputBean.getRequestId()+")").append(" where rli.pr_number = ").append(inputBean.getPrNumber());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

				query = new StringBuilder("update purchase_request set pr_status = 'posubmit',submitted_date = sysdate,last_updated = sysdate,last_updated_by = ");
				query.append(personnelBean.getPersonnelId()).append(" where pr_number = "+inputBean.getPrNumber());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),dbManager,connection,genericSqlFactory);
				MaterialRequestInputBean mrInputBean = new MaterialRequestInputBean();
				mrInputBean.setPrNumber(inputBean.getPrNumber());
				mrProcess.releaseMrToHaas(mrInputBean,personnelBean);
			}
			sendApproversNotifyOnlyEmail(inputBean.getRequestId());
		}else {
			//sending email to the next group/group seq
			if (nextGroup.equalsIgnoreCase("New group")) {
				sendApproversEmail(inputBean.getRequestId(),resultData[EngEvalProcess.MESSAGE_TO_APPROVER_INDEX],getOtherApprovalRoleEmail(inputBean.getRequestId()),getApproversNotifyOnlyEmail(inputBean.getRequestId()));
			}
		}
	}

	public boolean insertApprovalData(BigDecimal requestId, String approvalRole, String status, String reason, BigDecimal approverId, String approvalWithRestriction) {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_approval (company_id,request_id,facility_id,catalog_id,catalog_company_id,approval_role,chemical_approver,status,approval_date,reason,start_time,restricted)");
			query.append(" select company_id,request_id,eng_eval_facility_id,catalog_id,catalog_company_id,'").append(approvalRole).append("',").append(approverId).append(",'").append(status).append("',sysdate");
			if (StringHandler.isBlankString(reason)) {
				query.append(",null");
			}else {
				query.append(",").append(SqlHandler.delimitString(reason));
			}
			//start_time
			query.append(",approval_group_seq_start_time");
			//restricted
			if ("Y".equals(approvalWithRestriction)) {
				query.append(",'Y'");
			}else {
				query.append(",'N'");
			}

			query.append(" from catalog_add_request_new where request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	} //end of method

	public void setApprovalPath(BigDecimal requestId) {
		try {
			StringBuilder query = new StringBuilder("select fx_feature_released('HmrbTab',eng_eval_facility_id,company_id) from catalog_add_request_new");
			query.append(" where request_id = ").append(requestId);
			if ("Y".equals(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
				//1) if part and item not in cpig then request need to go thru hmrb
				//2) if user ask for new approval code for part then request need to go thru hmrb
				query = new StringBuilder("select PKG_CATALOG_PLANNED_ADD.fx_need_msds_approval(").append(requestId).append(") from dual");
				String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
				if ("Y".equalsIgnoreCase(tmpVal)) {
					//update this if approval path is not already set to MSDS
					query = new StringBuilder("update catalog_add_request_new set approval_path = 'MSDS+Part'");
					query.append(" where approval_path <> 'MSDS' and request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public void setCatPartNeedsReview(BigDecimal requestId, int personnelId) {
		try {
			StringBuilder query = new StringBuilder("select fx_cat_add_part_needs_review(carn.catalog_id,carn.cat_part_no,cai.item_id,").append(personnelId).append(",carn.catalog_company_id) cat_add_needs_review");
			query.append(" from catalog_add_requesT_new carn, catalog_add_item cai where carn.company_id = cai.company_id and carn.request_id = cai.request_id");
			query.append(" and cai.part_id = 1 and carn.request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddRequestNewBean());
			Collection data = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = data.iterator();
			String partNeedsReview = "N";
			while (iter.hasNext()) {
				CatalogAddRequestNewBean bean = (CatalogAddRequestNewBean)iter.next();
				if ("Y".equalsIgnoreCase(bean.getCatAddPartNeedsReview())) {
					partNeedsReview = "Y";
					break;
				}
			}

			if ("Y".equals(partNeedsReview)) {
				query = new StringBuilder("update catalog_add_request_new set cat_add_part_needs_review = 'Y'");
				query.append(",old_cat_part_no = cat_part_no where request_id = ").append(requestId);
			}else {
				query = new StringBuilder("update catalog_add_request_new set cat_add_part_needs_review = 'N'");
				query.append(",old_cat_part_no = cat_part_no where request_id = ").append(requestId);
			}
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		} catch (Exception e) {
			log.error(e);
		}
	} //end of method

	public String[] setNextStatus(BigDecimal requestId) throws Exception {
		String[] result = new String[8];
		result[EngEvalProcess.DONE_INDEX] = "Next Status";
		result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
		result[EngEvalProcess.POSS_REQUEST_INDEX] = "N";
		result[EngEvalProcess.MESSAGE_TO_APPROVER_INDEX] = "";
		result[EngEvalProcess.STARTING_VIEW_INDEX] = "";
		result[EngEvalProcess.REJECT_ON_FAILURE_APPROVAL_ROLE_INDEX] = "";
		result[EngEvalProcess.REJECT_ON_FAILURE_STATEMENT_INDEX] = "";
		try {
			//first get request info
			StringBuilder query = new StringBuilder("select carn.eng_eval_facility_id,carn.poss,carn.qc_status,carn.engineering_evaluation,carn.starting_view,");
			query.append("ars.approval_group,nvl(carn.approval_group_seq,'0') approval_group_seq,carn.approval_path,carn.company_id");
			query.append(" from catalog_add_request_new carn,vv_catalog_add_request_status ars where carn.request_status = ars.request_status and");
			query.append(" carn.company_id = ars.company_id and carn.request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Vector<CatalogAddApprovalBean> dataColl = (Vector)genericSqlFactory.selectQuery(query.toString(),connection);
			CatalogAddApprovalBean caaBean = dataColl.get(0);
			result[EngEvalProcess.EVAL_INDEX] = caaBean.getEngineeringEvaluation();
			result[EngEvalProcess.STARTING_VIEW_INDEX] = caaBean.getStartingView().toString();

			//if POSS then set poss_request_index flag to use later
			if ("Y".equalsIgnoreCase(caaBean.getPoss())) {
				result[EngEvalProcess.POSS_REQUEST_INDEX] = "Y";
				//return result;
			}

			//otherwise continue
			query = new StringBuilder("select distinct caa.approval_role from catalog_add_approval caa,vv_chemical_approval_role car where caa.approval_role = car.approval_role and");
			query.append(" caa.facility_id = car.facility_id and caa.catalog_id = car.catalog_id and caa.catalog_company_id = car.catalog_company_id and car.approval_group = ").append(caaBean.getApprovalGroup());
			query.append(" and car.approval_group_seq = "+caaBean.getApprovalGroupSeq()+" and caa.request_id = ").append(requestId);
			query.append(" and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = ").append(requestId).append(")");
			caaBean.setApprovalRole(genericSqlFactory.selectSingleValue(query.toString(),connection));
			//next approval info
			if ("Y".equalsIgnoreCase(caaBean.getEngineeringEvaluation())) {
				if ("2".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("reorder_eng_eval");
				}else {
					caaBean.setChemicalType("eng_eval");
				}
			}else {
				if ("0".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("new_material");
				}else if ("1".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("new_size");
				}else if ("2".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("new_part");
				}else if ("3".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("new_approval");
				}else if ("4".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("modify_qpl");
				}else if ("5".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("fadeout_from_qpl");
				}else if ("6".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("new_msds");
				}else if ("7".equalsIgnoreCase(caaBean.getStartingView().toString()) || "8".equalsIgnoreCase(caaBean.getStartingView().toString()) ) {
					caaBean.setChemicalType("new_approval_code");
				}else if ("9".equalsIgnoreCase(caaBean.getStartingView().toString())) {
					caaBean.setChemicalType("sds_authoring");
				}else {
					caaBean.setChemicalType("new_material");
				}
			}

			query = new StringBuilder("select a.company_id,a.approval_role,a.approval_group,a.approval_group_seq,a.role_function,a.skip_with_msds_approval");
			query.append(",a.edit_het_usage_recording, arf.create_catalog_queue from vv_chemical_approval_role a, vv_approval_role_function arf, catalog_add_approval b");
			query.append(" where a.").append(caaBean.getChemicalType()).append(" = 'Y'");
			query.append(" and a.role_function = arf.role_function");

			//approval_path flag is setup to handle clients with two approval path and a request have to go thru one path before the next
			//this flag also allow me to set certain requests to go thru both paths or just one
			//if this flag is MSDS+Part then need to go thru msds_approval = Y or part_approval = Y
			//if this flag is Part on the request then need to go thru part_approval = Y
			//if MSDS then is only need to go thru approval roles where msds_approval = Y
			if ("MSDS+Part".equals(caaBean.getApprovalPath())) {
				query.append(" and (a.msds_approval = 'Y' or a.part_approval = 'Y')");
			}else if ("MSDS".equals(caaBean.getApprovalPath())) {
				//if request is for MSDS approval code then it only need go thru approval_path = MSDS approval roles
				query.append(" and a.msds_approval = 'Y'");
			}else if ("Part".equals(caaBean.getApprovalPath())) {
				query.append(" and a.part_approval = 'Y'");
			}else {
				query.append(" and a.part_approval = 'Y'");
			}

			query.append(" and a.facility_id = b.facility_id(+) and a.approval_role = b.approval_role(+) and ");
			query.append("b.approval_role is null and b.request_id(+) = ").append(requestId);
			//if POSS
			if ("Y".equalsIgnoreCase(caaBean.getPoss())) {
				query.append(" and a.poss_material = 'Y'");
			}
			query.append(" and a.active = 'Y' and a.catalog_id = b.catalog_id(+) and a.catalog_company_id = b.catalog_company_id(+)");
			query.append(" and (a.facility_id,a.catalog_id,a.catalog_company_id) = (select eng_eval_facility_id,catalog_id,catalog_company_id from catalog_add_request_new where request_id = ").append(requestId).append(")");
			query.append(" and a.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = ").append(requestId).append(")");
			query.append(" order by a.approval_group,a.approval_group_seq,a.approval_role");
			Collection approvalDataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Vector aGroupV = new Vector();
			Vector aGroupSeqV = new Vector();
			Vector approvalRoleV = new Vector();
			Vector roleFunctionV = new Vector();
			Vector skipWithMsdsApprovalV = new Vector();
			Vector editHetUsageRecordingV = new Vector();
			Vector createCatalogQueueV = new Vector();
			Iterator iter = approvalDataColl.iterator();
			while (iter.hasNext()){
				CatalogAddApprovalBean tmpBean = (CatalogAddApprovalBean)iter.next();
				aGroupV.addElement(tmpBean.getApprovalGroup().toString());
				aGroupSeqV.addElement(tmpBean.getApprovalGroupSeq().toString());
				approvalRoleV.addElement(tmpBean.getApprovalRole());
				roleFunctionV.addElement(tmpBean.getRoleFunction());
				skipWithMsdsApprovalV.addElement(tmpBean.getSkipWithMsdsApproval());
				editHetUsageRecordingV.add(tmpBean.getEditHetUsageRecording());
				createCatalogQueueV.addElement(tmpBean.getCreateCatalogQueue());
			}

			//if above query return no record then move status to complete
			if (aGroupV.size() == 0) {
				if ("Y".equalsIgnoreCase(caaBean.getEngineeringEvaluation()) || "6".equals(caaBean.getStartingView().toString()) || "7".equals(caaBean.getStartingView().toString())) {
					query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
				}else {
					query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
				}
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				result[EngEvalProcess.DONE_INDEX] = "Done";
				return result;
			}  //end of no record returned

			int currentGroupPos = -1;
			if (currentGroupPos+1 == aGroupSeqV.size()) {  //reach the end of the approval seq
				if ("Y".equalsIgnoreCase(caaBean.getEngineeringEvaluation())) {
					query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
				}else {
					query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
				}
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				result[EngEvalProcess.DONE_INDEX] = "Done";
				return result;
			}else {  //otherwise, find and set the next approval role, group and seq
				String nextApprovalRole = (String)approvalRoleV.elementAt(currentGroupPos+1);
				Hashtable approvalDetailH = new Hashtable();
				approvalDetailH.put("APPROVAL_ROLE",approvalRoleV);
				approvalDetailH.put("APPROVAL_GROUP",aGroupV);
				approvalDetailH.put("APPROVAL_GROUP_SEQ",aGroupSeqV);
				approvalDetailH.put("ROLE_FUNCTION",roleFunctionV);
				approvalDetailH.put("CURRENT_ROLE",caaBean.getApprovalRole());
				approvalDetailH.put("CURRENT_GROUP",caaBean.getApprovalGroup().toString());
				approvalDetailH.put("CURRENT_GROUP_SEQ",caaBean.getApprovalGroupSeq().toString());
				approvalDetailH.put("STARTING_VIEW",caaBean.getStartingView().toString());
				approvalDetailH.put("EVALUATION",caaBean.getEngineeringEvaluation());
				approvalDetailH.put("QC_STATUS",StringHandler.emptyIfNull(caaBean.getQcStatus()));
				approvalDetailH.put("CHEM_TYPE",caaBean.getChemicalType());
				approvalDetailH.put("COMPANY_ID",caaBean.getCompanyId());
				approvalDetailH.put("SKIP_WITH_MSDS_APPROVAL",skipWithMsdsApprovalV);
				approvalDetailH.put("EDIT_HET_USAGE_RECORDING",editHetUsageRecordingV);
				approvalDetailH.put("APPROVAL_PATH",caaBean.getApprovalPath());
				approvalDetailH.put("POSS",caaBean.getPoss());
				approvalDetailH.put("CREATE_CATALOG_QUEUE",createCatalogQueueV);
				setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			} //end of going the thru the next approval role
		}catch (Exception e) {
			result[EngEvalProcess.DONE_INDEX] = "Error";
			log.error(e);
			StringBuilder tmp = new StringBuilder("Set next status failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to set next status for request.");
			throw e;
		}
		return result;
	}

	//NOTE: the following role functions are not ready for parallel processing
	//  1) "Verify CRA" => approvalRoleCRA
	//  2) "Verify Label" => approvalRoleLabelInfo
	//  3) "Verify All" => approvalRoleTCMProcess
	private void setNextApprovalRole(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector roleFunctionV = (Vector) approvalDetailH.get("ROLE_FUNCTION");
			String roleFunction = (String)roleFunctionV.elementAt(currentGroupPos+1);

			//next approval role is allow to edit HET usage recording
			Vector editHetUsageRecordingV = (Vector) approvalDetailH.get("EDIT_HET_USAGE_RECORDING");
			if ("Y".equals(editHetUsageRecordingV.elementAt(currentGroupPos+1)) &&
					("MSDS+Part".equals((String)approvalDetailH.get("APPROVAL_PATH")) ||
							"Part".equals((String)approvalDetailH.get("APPROVAL_PATH")))
			) {
				calculateHetUsageRecording(requestId);
			}
			if ("Auto Approved".equalsIgnoreCase(roleFunction)) {
				approvalRoleAutoApproved(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Create Temporary Item".equalsIgnoreCase(roleFunction)) {
				approvalRoleCreateTemporaryItem(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Approval Review".equalsIgnoreCase(roleFunction)) {
				approvalRoleApprovalReview(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify CRA".equalsIgnoreCase(roleFunction)) {
				approvalRoleCRA(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Directed Charge".equalsIgnoreCase(roleFunction)) {
				approvalRoleDirectedCharge(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify ITAR Spec".equalsIgnoreCase(roleFunction)) {
				approvalRoleItarSpec(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Item".equalsIgnoreCase(roleFunction) || "Verify Item Wesco".equalsIgnoreCase(roleFunction)) {
				approvalRoleQcItem(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Label".equalsIgnoreCase(roleFunction)) {
				approvalRoleLabelInfo(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Material".equalsIgnoreCase(roleFunction)) {
				approvalRoleMaterialExistForFacility(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Msds".equalsIgnoreCase(roleFunction)) {
				approvalRoleMsds(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Custom Indexing".equalsIgnoreCase(roleFunction)) {
				approvalRoleCustomIndexing(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Part Number".equalsIgnoreCase(roleFunction)) {
				approvalRolePartNeedReview(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Pre-Screening".equalsIgnoreCase(roleFunction)) {
				approvalRolePreScreening(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Pricing".equalsIgnoreCase(roleFunction)) {
				approvalRolePricing(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Production Material".equalsIgnoreCase(roleFunction)) {
				approvalRoleProductionMaterial(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify SDS Indexing".equalsIgnoreCase(roleFunction) || "Verify SDS Indexing Wesco".equalsIgnoreCase(roleFunction)) {
				approvalRoleMsdsIndexing(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify SDS QC".equalsIgnoreCase(roleFunction)) {
				approvalRoleSdsQc(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify SDS Sourcing".equalsIgnoreCase(roleFunction) || "Verify SDS Sourcing Wesco".equalsIgnoreCase(roleFunction)) {
				//if Authoring SDS request
				if (SDS_AUTHORING_STARTING_VIEW.equals((String) approvalDetailH.get("STARTING_VIEW")))
					approvalRoleSdsAuthoring(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
				else
					approvalRoleQcMaterial(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Sourcing".equalsIgnoreCase(roleFunction)) {
				approvalRoleSourcing(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else if ("Verify Spec".equalsIgnoreCase(roleFunction)) {
				approvalRoleTCMSpec(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
			}else {
				Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
				Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
				String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
				String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
				if (!approvalGroupSeqSet) {
					StringBuilder query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}

				//set local variable to null
				aGroupV = null;
				aGroupSeqV = null;
				aGroup = null;
				aGroupSeq = null;
			} //end of next approval role is not a special role need additional attention
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void calculateHetUsageRecording(BigDecimal requestId) {
		try {
			StringBuilder query = new StringBuilder("select line_item,het_usage_recording from catalog_add_item where request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataC = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataC.iterator();
			while(iter.hasNext()) {
				CatalogAddItemBean bean = (CatalogAddItemBean)iter.next();
				if (StringHandler.isBlankString(bean.getHetUsageRecording())) {
					query = new StringBuilder("select pkg_approval_review.fx_req_voc_g_per_l_and_fl_oz(").append(requestId).append(",'','','','','',").append(bean.getLineItem()).append(",'') het_usage_recording from dual");
					if (StringHandler.isBlankString(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
						query = new StringBuilder("update catalog_add_item set het_usage_recording = 'OTR' where request_id = ").append(requestId);
						query.append(" and line_item = ").append(bean.getLineItem());
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					}else {
						query = new StringBuilder("update catalog_add_item set het_usage_recording = 'Daily Usage' where request_id = ").append(requestId);
						query.append(" and line_item = ").append(bean.getLineItem());
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					}
				}
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Calculating HET Usage Recording failed: "+requestId,"See log for more details.");
		}

	} //end of method

	void approvalRoleAutoApproved(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");

			//auto approve list approval
			approvalRoleAutoApproved(reqID,nextApprovalRole);
			//now find the next approval role
			currentGroupPos++;
			if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
				if (!approvalGroupSeqSet) {
					StringBuilder query;
					if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
					}else {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
					}
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					result[EngEvalProcess.DONE_INDEX] = "Done";
				}
			}else {
				//if the approval role is set for that group and seq
				//then only continue to check within that group and seq
				if (approvalGroupSeqSet) {
					//handle parallel approval roles with auto approval
					//continue to go thru next approval roles if it's in the same group
					//reason for +1 is next approval role
					if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
						if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
								aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
							nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
							setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
						}
					}
					//else it's not set so it's okay to continue and auto approve other group and seq
				}else {
					nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
					setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
				}
			}

			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	/*
    function FX_TBL_REQUEST_REVIEW (
        a_company_id chemical_approval_review.company_id%type,
        a_request_id catalog_add_request_new.request_id%type,
        a_approval_role chemical_approval_review.approval_role%type,
        a_stop_on_failure char default null,
        a_rule_id chemical_approval_review.rule_id%type default null,
        a_argument_1 chemical_approval_review.argument_1%type default null,
        a_argument_2 chemical_approval_review.argument_2%type default null)
    */
	void approvalRoleApprovalReview(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String companyId = (String) approvalDetailH.get("COMPANY_ID");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");
			Vector skipWithMsdsApprovalV = (Vector) approvalDetailH.get("SKIP_WITH_MSDS_APPROVAL");

			String notifyOnly = "N";
			String testPassed = "Y";
			String rejectOnFailure = "N";
			String stopOnFailure = "N";
			String rejectedComment = "";
			StringBuilder messageToApprover = new StringBuilder("");
			StringBuilder messageToRequestor = new StringBuilder("");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String autoApproveWithRestriction = "N";

			StringBuilder query = new StringBuilder("select count(*) from catalog_add_item where restricted = 'Y' and request_id = ").append(reqID);

			//if the next group is the same as current group then the data already evaluated so we don't need to do it again
			if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) && aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1)) &&
					"Approved".equals(nextStatusAction)) {
				testPassed = "N";
				notifyOnly = "N";
				rejectOnFailure = "N";
				//check to see if request contains all previously approved material/msds
			}else if ("Y".equals((String)skipWithMsdsApprovalV.elementAt(currentGroupPos+1)) && dataCountIsZero(query.toString())) {
				testPassed = "Y";
				notifyOnly = "N";
				rejectOnFailure = "N";
			}else {
				int totalRuleFailedWithStopOnFailure = 0;
				//first delete previous messages
				genericSqlFactory.deleteInsertUpdate("delete from gt_approval_review_message",connection);
				//run tests
				query = new StringBuilder("select * from table (PKG_APPROVAL_REVIEW.fx_tbl_request_review('").append(companyId).append("',");
				query.append(reqID).append(",'").append(nextApprovalRole).append("','Y'))");
				genericSqlFactory.setBeanObject(new CatalogAddApprovalReviewBean());
				Collection dataC = genericSqlFactory.selectQuery(query.toString(),connection);
				Iterator iter = dataC.iterator();

				while (iter.hasNext()) {
					CatalogAddApprovalReviewBean tmpBean = (CatalogAddApprovalReviewBean)iter.next();
					//if reject_on_failure == Y and FAIL == Y then reject request
					if ("Y".equals(tmpBean.getRejectOnFailure()) && "Y".equals(tmpBean.getFail())) {
						rejectOnFailure = "Y";
						rejectedComment = tmpBean.getOutputStatement();
						//stop on failure
						if ("Y".equals(tmpBean.getStopOnFailure())) {
							stopOnFailure = "Y";
						}
						break;
					}
					//approve_on_pass = Y then auto approve this role
					//if there is no rules that says to stop_on_failure = Y before this rule.
					if ("Y".equals(tmpBean.getApproveOnPass()) && "N".equals(tmpBean.getFail())) {
						if ("N".equals(stopOnFailure)) {
							notifyOnly = "N";
							stopOnFailure = "N";
							testPassed = "Y";
							totalRuleFailedWithStopOnFailure = 0;
							//check to see if auto approve with restriction
							if ("Y".equals(tmpBean.getRestrictedApproval())) {
								autoApproveWithRestriction = "Y";
							}else {
								autoApproveWithRestriction = "N";
							}
						}
						break;
					}
					//failed
					if ("Y".equals(tmpBean.getFail())) {
						if ("Y".equals(tmpBean.getApproveOnPass()) && "N".equals(tmpBean.getStopOnFailure())) {
							//don't need to add message to send to approver
						}else {
							if (!StringHandler.isBlankString(tmpBean.getOutputStatement()) && !"Fail".equals(tmpBean.getOutputStatement()))
								messageToApprover.append("\n\n").append(nextApprovalRole).append(": ").append(tmpBean.getOutputStatement());
						}

						//other approval role
						if (!StringHandler.isBlankString(tmpBean.getAddlNotifyApprovalRole1()) ||
								!StringHandler.isBlankString(tmpBean.getAddlNotifyApprovalRole2())) {
							otherApprovalRoleNotification.add(tmpBean);
						}
						//requestor message
						if (!StringHandler.isBlankString(tmpBean.getRequestorMessage())) {
							if (messageToRequestor.length() == 0) {
								messageToRequestor.append(tmpBean.getRequestorMessage());
							}else {
								messageToRequestor.append(REQUESTOR_MSG_DELIMITER).append(tmpBean.getRequestorMessage());
							}
						}
						//notify only
						if ("Y".equals(tmpBean.getNotifyOnly())) {
							notifyOnly = "Y";
						}
						//stop on failure
						if ("Y".equals(tmpBean.getStopOnFailure())) {
							stopOnFailure = "Y";
							testPassed = "N";
							totalRuleFailedWithStopOnFailure++;
						}
						//check to see if auto approve with restriction
						if ("Y".equals(tmpBean.getRestrictedApproval())) {
							autoApproveWithRestriction = "Y";
						}
					} //end of if failed
				} //end of while loop

				//get approval additional messages
				getAdditionalApprovalRoleMsg(nextApprovalRole);

				//if any rules fail and stop for approval role then don't need to additionally send approval role notification only email
				//if below works becuase totalRuleFailed include totalRuleNotificationOnly
				//the the two value equal than it's Notification Only
				if (totalRuleFailedWithStopOnFailure > 0) {
					notifyOnly = "N";
				}

				//if reject on failure then return flag
				if ("Y".equals(rejectOnFailure)) {
					//if there are messages then send approvers email notificication for Auto Rejected
					if (approvalRoleAdditionalMessage.containsKey(nextApprovalRole)) {
						sendAutoApprovedRejectedEmail(false,reqID,nextApprovalRole);
					}
					result[EngEvalProcess.DONE_INDEX] = "Rejected";
					if ("Y".equals(stopOnFailure)) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Rejected";
					}
					result[EngEvalProcess.REJECT_ON_FAILURE_APPROVAL_ROLE_INDEX] = nextApprovalRole;
					result[EngEvalProcess.REJECT_ON_FAILURE_STATEMENT_INDEX] = rejectedComment;
					return;
				}

				//message to requestor
				if (messageToRequestor.length() > 0) {
					updateMessageToRequestor(reqID,messageToRequestor.toString());
				}
				//message to approver
				result[EngEvalProcess.MESSAGE_TO_APPROVER_INDEX] = result[EngEvalProcess.MESSAGE_TO_APPROVER_INDEX] + messageToApprover;
			} //end of else

			//do everything else
			if ("Y".equals(testPassed) || "Y".equals(notifyOnly)) {
				//send out notify only email
				if ("Y".equals(notifyOnly)) {
					CatalogAddEmailBean emailBean = new CatalogAddEmailBean();
					emailBean.setApprovalRole(nextApprovalRole);
					String[] msgToAppArr = messageToApprover.toString().split("\n\n");
					StringBuilder msgToApp = new StringBuilder("");
					for (int k = 0; k < msgToAppArr.length; k++) {
						String tmpVal = msgToAppArr[k];
						if (tmpVal.startsWith(nextApprovalRole)) {
							msgToApp.append("   - ").append(tmpVal.substring(nextApprovalRole.length()+2)).append("\n");
						}
					}
					emailBean.setMessageToApprover(msgToApp);
					notifyOnlyApprovalRoleV.add(emailBean);
				}

				//auto approve list approval
				if ("Y".equals(autoApproveWithRestriction)) {
					approvalRoleAutoApprovedWithRestriction(reqID,nextApprovalRole);
				}else {
					approvalRoleAutoApproved(reqID,nextApprovalRole);
				}
				//if there are messages then send approvers email notificication for Auto Approved
				if (approvalRoleAdditionalMessage.containsKey(nextApprovalRole)) {
					sendAutoApprovedRejectedEmail(true,reqID,nextApprovalRole);
				}

				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//set next status
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1)) &&
							"Approved".equals(nextStatusAction)) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	public void updateMessageToRequestor(BigDecimal requestId, String messageToRequestor) {
		try {
			StringBuilder query = new StringBuilder("update catalog_add_request_new set requestor_message = decode(requestor_message,null,'',requestor_message||'").append(REQUESTOR_MSG_DELIMITER).append("')||");
			query.append(SqlHandler.delimitString(messageToRequestor)).append(" where request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Update message to requestor failed: "+requestId,"See logs for more details.\n"+messageToRequestor);
		}
	}

	void approvalRoleProductionMaterial(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");

			StringBuilder query = new StringBuilder("select count(*) from catalog_add_request_new where production_material = 'Y' and request_id = ").append(reqID);
			if (dataCountIsZero(query.toString())) {
				//auto approve production material
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//set next status to directed charge
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
					String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRoleDirectedCharge(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");

			StringBuilder query = new StringBuilder("select count(*) from catalog_add_user_group where charge_number is null and request_id = ").append(reqID);
			if (dataCountIsZero(query.toString())) {
				//auto approve directed charge
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//set next status to directed charge
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
					String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method


	void approvalRolePartNeedReview(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");

			StringBuilder query = new StringBuilder("select cat_add_part_needs_review from catalog_add_request_new carn where request_id = ").append(reqID);
			String partNeedReview = genericSqlFactory.selectSingleValue(query.toString(),connection);
			if ("N".equalsIgnoreCase(partNeedReview)) {
				//auto approve part
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//set next status
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
					String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRoleMaterialExistForFacility(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");

			StringBuilder query = new StringBuilder("select count(*) count from part p,catalog_part_item_group cpig,catalog_add_item cai,catalog_add_request_new carn");
			query.append(" where exists (select * from use_approval where facility_id = carn.eng_eval_facility_id and");
			query.append(" approval_status = 'approved' and catalog_id = cpig.catalog_id and fac_part_no = cpig.cat_part_no) and");
			query.append(" cai.material_id = p.material_id and carn.catalog_id = cpig.catalog_id and carn.catalog_company_id = cpig.company_id and cpig.status = 'A' and");
			query.append(" p.item_id = cpig.item_id and carn.request_id = cai.request_id and carn.request_id = ").append(reqID);
			//if material exist for facility then auto approve
			if (!dataCountIsZero(query.toString())) {
				//auto approved
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//if not set to approval role
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRoleMsds(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");

			StringBuilder query = new StringBuilder("select count(*) from catalog_add_item cai, material m");
			query.append(" where cai.request_id = ").append(reqID).append(" and cai.material_id = m.material_id");
			query.append(" and m.trade_name not like 'MSDS Not Required%'");
			//if "MSDS Not Required"
			if (dataCountIsZero(query.toString())) {
				//auto approved
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//if not set to approval role
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRoleLabelInfo(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");

			//check to see if need label info, if not auto approve Label Info
			StringBuilder query = new StringBuilder("select count(*) from inventory_group_item_label a, catalog_add_request_new b, catalog_add_item cai, facility_view f where a.health is not null and a.flamability is not null and a.reactivity is not null");
			query.append(" and a.chemical_storage is not null and a.signal_word is not null and a.personal_protective_equipment is not null and a.disposal_code is not null");
			query.append(" and a.hazard_code1 is not null and a.item_id = cai.item_id and a.inventory_group = f.inventory_group_default and b.company_id = f.company_id and b.eng_eval_facility_id = f.facility_id and b.request_id = ").append(reqID);
			query.append(" and b.company_id = cai.company_id and b.request_id = cai.request_id");
			if (dataCountIsZero(query.toString())) {
				//set next status to Label Info
				query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
				query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				result[EngEvalProcess.NEXT_GROUP_INDEX] = "New Group";
				//if next group and seq is the same as current then call next approval group as well.
				//In this case, the next group is Pricing
				query = new StringBuilder("select count(*) from catalog_add_request_new carn, vv_chemical_approval_role car, vv_chemical_approval_role car2");
				query.append(" where carn.eng_eval_facility_id = car.facility_id and carn.catalog_id = car.catalog_id and carn.catalog_company_id = car.catalog_company_id");
				query.append(" and car.approval_role = 'Label Info' and car.active = 'Y' and carn.eng_eval_facility_id = car2.facility_id");
				query.append(" and carn.catalog_id = car2.catalog_id and carn.catalog_company_id = car2.catalog_company_id and car2.approval_role = 'Pricing' and car2.active = 'Y'");
				query.append(" and car.approval_group = car2.approval_group and car.approval_group_seq = car2.approval_group_seq");
				query.append(" and carn.requesT_id = ").append(reqID);
				if (!dataCountIsZero(query.toString())) {
					BigDecimal cItemID = new BigDecimal(0);
					String facID = "";
					String defaultInventoryGroup = "";
					String tmpCatPartNo = "";
					String tmpCatalogID = "";
					String branchPlant = "";
					String companyId = "";
					try {
						query = new StringBuilder("select distinct a.company_id,a.catalog_id,a.catalog_company_id,cai.item_id,f.facility_id,f.inventory_group_default,f2.branch_plant from catalog_add_request_new a, catalog_add_item cai,");
						query.append("facility_view f,facility_view f2 where a.request_id = ").append(reqID).append(" and a.company_id = f.company_id and a.eng_eval_facility_id = f.facility_id and f.company_id = f2.company_id and f.preferred_warehouse = f2.facility_id");
						query.append(" and a.company_id = cai.company_id and a.request_id = cai.request_id");
						genericSqlFactory.setBeanObject(new CatalogInputBean());
						Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
						while (iter.hasNext()){
							CatalogInputBean bean = (CatalogInputBean)iter.next();
							cItemID = bean.getItemId();
							facID = bean.getFacilityId();
							branchPlant = bean.getBranchPlant();
							companyId = bean.getCompanyId();
						}

					}catch (Exception e) {
						log.error(e);
					}
					//Seagate always require price quotes
					boolean requiredPriceQuote = true;
					if (cItemID != null && !cItemID.equals(new BigDecimal(0)) && requiredPriceQuote) {
						query = new StringBuilder("select count(*) from price_quote_view where request_date > sysdate - 365 and item_id = ").append(cItemID);
						if (dataCountIsZero(query.toString())) {
							NetHandler netHandler = new NetHandler();

							ResourceLibrary resource = new ResourceLibrary("label");
							String hostName = resource.getString("label.hosturl");

							StringBuilder tmp = new StringBuilder(hostName).append("/cgi-bin/rpq.cgi?item=").append(cItemID);
							StringBuilder tmp2 = new StringBuilder("&item=").append(cItemID).append("&per=-1&fac=").append(facID).append("&bp=").append(branchPlant).append("&comp=").append(companyId);
							netHandler.sendHttpsPost(tmp.toString(),null,null,tmp2.toString(),null);
						}
					}
				}
			}else {
				//auto approve Label Info
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if ("Y".equalsIgnoreCase(eEvaluation)) {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
					}else {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
					}
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					result[EngEvalProcess.DONE_INDEX] = "Done";

				}else {
					nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
					setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRoleItarSpec(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");

			//if request has specs marked as ITAR
			StringBuilder query = new StringBuilder("select count(*) from catalog_add_spec where itar = 'Y' and request_id = ").append(reqID);
			//no specs are marked as ITAR
			if (this.dataCountIsZero(query.toString())) {
				//auto approve Spec
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//set next status to ITAR Spec
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate,approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
					String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRoleTCMSpec(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");

			//if request has spec other than Std Mfg Cert or No Specification
			StringBuilder query = new StringBuilder("select * from catalog_add_spec where request_id = ").append(reqID);
			genericSqlFactory.setBeanObject(new CatalogAddSpecBean());
			boolean autoApprovedSpec = true;
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				CatalogAddSpecBean bean = (CatalogAddSpecBean)iter.next();
				//don't need to stop for Spec approval if specs on request is global or source of the spec is copied from
				//spec table
				if (!"global".equalsIgnoreCase(bean.getSpecLibrary()) && !"spec".equals(bean.getSpecSource())) {
					autoApprovedSpec = false;
					break;
				}
			} //end of while

			if (autoApprovedSpec) {
				//auto approve Spec
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//set next status to TCM Spec
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set qc_status = 'Pending Spec',approval_group_seq_start_time = sysdate,approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
					String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRolePricing(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			result[EngEvalProcess.NEXT_GROUP_INDEX] = "Punchout group";       //treating pricing like punchout since buyers doesn't want email notification
			StringBuilder query = new StringBuilder("select distinct a.catalog_id,cai.item_id,f.facility_id,f.inventory_group_default,igd.hub branch_plant,a.company_id,a.catalog_company_id");
			query.append(" from catalog_add_request_new a,catalog_add_item cai,facility_view f,inventory_group_definition igd");
			query.append(" where a.request_id = ").append(reqID).append(" AND a.company_id = f.company_id and a.eng_eval_facility_id = f.facility_id AND");
			query.append(" f.inventory_group_default = igd.inventory_group and a.company_id = cai.company_id and a.request_id = cai.request_id");
			genericSqlFactory.setBeanObject(new CatalogInputBean());
			BigDecimal cItemID = new BigDecimal(0);
			String facID = "";
			String branchPlant = "";
			String companyId = "";
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()){
				CatalogInputBean bean = (CatalogInputBean)iter.next();
				cItemID = bean.getItemId();
				facID = bean.getFacilityId();
				branchPlant = bean.getBranchPlant();
				companyId = bean.getCompanyId();
			}
			//if starting from new_part then check price_quote_request
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String sView = (String) approvalDetailH.get("STARTING_VIEW");
			String eEvaluation = (String) approvalDetailH.get("EVAULATION");
			boolean requiredPriceQuote = false;
			if ("2".equalsIgnoreCase(sView) || "3".equalsIgnoreCase(sView)) {
				requiredPriceQuote = isPriceQuoteRequired(reqID,cItemID);
				//if price_quote_request already exist or fac_item contains catalog_price then ship over Pricing
				if (!requiredPriceQuote) {
					//if Pricing is the last group in the approval process then declare as finish.  Otherwise, skip to next group
					if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
						if (!approvalGroupSeqSet) {
							if ("Y".equalsIgnoreCase(eEvaluation)) {
								query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
							}else {
								query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
							}
							genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
							result[EngEvalProcess.DONE_INDEX] = "Done";
						}
					}else {  //Pricing is not the last approval role for this request, then skip pricing and set to next group
						//auto approve for PRICING
						approvalRoleAutoApproved(reqID,nextApprovalRole);
						//now find the next approval role
						currentGroupPos++;
						if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
							if (!approvalGroupSeqSet) {
								if ("Y".equalsIgnoreCase(eEvaluation)) {
									query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
								}else {
									query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
								}
								genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
								result[EngEvalProcess.DONE_INDEX] = "Done";
							}
						}else {
							//if the approval role is set for that group and seq
							//then only continue to check within that group and seq
							if (approvalGroupSeqSet) {
								//handle parallel approval roles with auto approval
								//continue to go thru next approval roles if it's in the same group
								//reason for +1 is next approval role
								if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
									if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
											aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
										nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
										setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
									}
								}
								//else it's not set so it's okay to continue and auto approve other group and seq
							}else {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
					}
				}else { //requiring price quote
					if (currentGroupPos == aGroupV.size()) {    //this mean that we reached the end of the approval process
						if ("Y".equalsIgnoreCase(eEvaluation)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}else {  //Pricing is not the last approval role for this request, then skip pricing and set to next group
						//Raytheon requested that we not wait for price quote at this point
						query = new StringBuilder("select count(*) from tcmis_feature where feature = 'AutoPricing' and feature_mode = 1 and company_id = '"+companyId+"'");
						if (!dataCountIsZero(query.toString())) {
							//auto approve for PRICING
							approvalRoleAutoApproved(reqID,nextApprovalRole);
							//now find the next approval role
							currentGroupPos++;
							if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
								if (!approvalGroupSeqSet) {
									if ("Y".equalsIgnoreCase(eEvaluation)) {
										query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
									}else {
										query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
									}
									genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
									result[EngEvalProcess.DONE_INDEX] = "Done";
								}
							}else {
								//if the approval role is set for that group and seq
								//then only continue to check within that group and seq
								if (approvalGroupSeqSet) {
									//handle parallel approval roles with auto approval
									//continue to go thru next approval roles if it's in the same group
									//reason for +1 is next approval role
									if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
										if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
												aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
											nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
											setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
										}
									}
									//else it's not set so it's okay to continue and auto approve other group and seq
								}else {
									nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
									setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
								}
							}
						}else { //else it is not AutoPricing
							if (!approvalGroupSeqSet) {
								query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
								query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
								query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
								genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
								//if next group and seq is the same as current then don't send email
								if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
										aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
									result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
								}else {
									result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
								}
								approvalGroupSeqSet = true;
							}
							//handle parallel approval roles with auto approval
							//continue to go thru next approval roles if it's in the same group
							//reason for +1 is next approval role
							//reason for +2 is next next approval role
							if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
								if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
										aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
									nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
									setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
								}
							}
						} //end of else it is not Raytheon
					}  //end of Pricing is not the last group
				} //end of requiring price quote
			}else {
				if (currentGroupPos == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if ("Y".equalsIgnoreCase(eEvaluation)) {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
					}else {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
					}
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					result[EngEvalProcess.DONE_INDEX] = "Done";
				}else {  //Pricing is not the last approval role for this request, then skip pricing and set to next group
					//Raytheon requested that we not wait for price quote at this point
					query = new StringBuilder("select count(*) from tcmis_feature where feature = 'AutoPricing' and feature_mode = 1 and company_id = '"+companyId+"'");
					if (!dataCountIsZero(query.toString())) {
						//auto approve for PRICING
						approvalRoleAutoApproved(reqID,nextApprovalRole);
						//now find the next approval role
						currentGroupPos++;
						if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
							if (!approvalGroupSeqSet) {
								if ("Y".equalsIgnoreCase(eEvaluation)) {
									query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
								}else {
									query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
								}
								genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
								result[EngEvalProcess.DONE_INDEX] = "Done";
							}
						}else {
							//if the approval role is set for that group and seq
							//then only continue to check within that group and seq
							if (approvalGroupSeqSet) {
								//handle parallel approval roles with auto approval
								//continue to go thru next approval roles if it's in the same group
								//reason for +1 is next approval role
								if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
									if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
											aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
										nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
										setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
									}
								}
								//else it's not set so it's okay to continue and auto approve other group and seq
							}else {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
					}else { //else it is not Raytheon
						if (!approvalGroupSeqSet) {
							query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
							query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
							query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
							genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
							//if next group and seq is the same as current then don't send email
							if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
							}else {
								result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
							}
							approvalGroupSeqSet = true;
						}
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						//reason for +2 is next next approval role
						if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
									aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
							}
						}
					} //end of else it is not Raytheon
				}  //end of Pricing is not the last group
				requiredPriceQuote = true;
			}

			//request price quote
			if (cItemID != null && !cItemID.equals(new BigDecimal(0)) && requiredPriceQuote) {
				query = new StringBuilder("select count(*) from price_quote_view where request_date > sysdate - 365 and item_id = ").append(cItemID);
				if (dataCountIsZero(query.toString())) {
					NetHandler netHandler = new NetHandler();

					ResourceLibrary resource = new ResourceLibrary("label");
					String hostUrl = resource.getString("label.hosturl");

					StringBuilder tmp = new StringBuilder(hostUrl).append("/cgi-bin/rpq.cgi?item=").append(cItemID);
					StringBuilder tmp2 = new StringBuilder("&item=").append(cItemID).append("&per=-1&fac=").append(facID).append("&bp=").append(branchPlant).append("&comp=").append(companyId);
					netHandler.sendHttpsPost(tmp.toString(),null,null,tmp2.toString(),null);
				}
			}
			//set local variables to null
			cItemID = null;
			facID = null;
			branchPlant = null;
			companyId = null;
			approvalRoleV = null;
			aGroupV = null;
			aGroup = null;
			aGroupSeqV = null;
			aGroupSeq = null;
			sView = null;
			eEvaluation = null;
			query = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	public boolean dataCountIsZero(String query) {
		boolean result = false;
		try {
			String tmpVal = genericSqlFactory.selectSingleValue(query,connection);
			if ("0".equalsIgnoreCase(tmpVal)) {
				result = true;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	public boolean isPriceQuoteRequired(BigDecimal reqID, BigDecimal itemID) throws Exception{
		boolean priceQuoteRequired = false;
		try {
			StringBuilder query = new StringBuilder("");
			if ("tcm_ops".equalsIgnoreCase(this.getClient())) {
				query.append("select fx_fac_part_base_price(company_id,eng_eval_facility_id,catalog_id,cat_part_no,part_group_no,catalog_company_id) baseline_price");
			}else {
				query.append("select fx_fac_part_base_price(eng_eval_facility_id,catalog_id,cat_part_no,part_group_no,catalog_company_id) baseline_price");
			}

			query.append(" from catalog_add_request_new where request_id = ").append(reqID);
			String baseLinePrice = genericSqlFactory.selectSingleValue(query.toString(),connection);
			if (StringHandler.isBlankString(baseLinePrice)) {
				query = new StringBuilder("select count(*) from price_quote_last_stat_view where last_quote_date > sysdate -365 and item_id = ").append(itemID);
				if (dataCountIsZero(query.toString())) {
					priceQuoteRequired = true;
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return priceQuoteRequired;
	} //end of method

	boolean requiredCatalogPrice(BigDecimal reqID) {
		boolean result = false;
		try {
			StringBuilder query = new StringBuilder("");
			if ("tcm_ops".equalsIgnoreCase(this.getClient())) {
				query.append("select fx_fac_part_catalog_price(company_id,eng_eval_facility_id,catalog_id,cat_part_no,part_group_no,catalog_company_id) catalog_price");
			}else {
				query.append("select fx_fac_part_catalog_price(eng_eval_facility_id,catalog_id,cat_part_no,part_group_no,catalog_company_id) catalog_price");
			}

			query.append(" from catalog_add_request_new where request_id = ").append(reqID);
			String catalogPrice = genericSqlFactory.selectSingleValue(query.toString(),connection);
			if (StringHandler.isBlankString(catalogPrice)) {
				result = true;
			}
		}catch (Exception e) {
			log.error(e);
			result = true;
		}
		return result;
	} //end of method

	void approvalRoleCRA(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			//allowing CRA to run parallel with others approval group
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");

			Integer tempCurrentGroup = new Integer(aGroup);
			StringBuilder query = new StringBuilder("select approval_group from vv_catalog_add_request_status where punchout = 'Y'");
			String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
			int punchoutGroup = (new BigDecimal(tmpVal)).intValue();

			//adjusting current group position
			if (tempCurrentGroup.intValue() < punchoutGroup) {
				query = new StringBuilder("select count(*) from vv_catalog_add_request_status ars,vv_chemical_approval_role car,catalog_add_approval caa");
				query.append(" where ars.punchout = 'Y' and ars.approval_group = car.approval_group and car.approval_role = caa.approval_role");
				query.append(" and car.facility_id = caa.facility_id and car.catalog_id = caa.catalog_id and car.catalog_company_id = caa.catalog_company_id and caa.request_id = ").append(reqID);
				tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
				if ((new BigDecimal(tmpVal)).intValue() > 0) {
					int tempCurrentGroupPos = currentGroupPos;
					if (tempCurrentGroupPos+1 < aGroupV.size()) {
						tempCurrentGroupPos++;
					}
					Integer tempGroup = new Integer((String)aGroupV.elementAt(tempCurrentGroupPos));
					if (punchoutGroup == tempGroup.intValue()) {
						if (tempCurrentGroupPos+1 < aGroupV.size()) {
							currentGroupPos++;
							if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
								if (!approvalGroupSeqSet) {
									if ("Y".equalsIgnoreCase(eEvaluation)) {
										query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
									}else {
										query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
									}
									genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
									result[EngEvalProcess.DONE_INDEX] = "Done";
								}
							}else {
								//if the approval role is set for that group and seq
								//then only continue to check within that group and seq
								if (approvalGroupSeqSet) {
									//handle parallel approval roles with auto approval
									//continue to go thru next approval roles if it's in the same group
									//reason for +1 is next approval role
									if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
										if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
												aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
											nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
											setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
										}
									}
									//else it's not set so it's okay to continue and auto approve other group and seq
								}else {
									nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
									setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
								}
							}
						}
					}
				}else {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					result[EngEvalProcess.NEXT_GROUP_INDEX] = "Punchout group";
				}
			}else {
				query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
				query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
				query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				result[EngEvalProcess.NEXT_GROUP_INDEX] = "Punchout group";
			}
			//set local variable to null
			approvalRoleV = null;
			aGroupV = null;
			aGroup = null;
			aGroupSeqV = null;
			tempCurrentGroup = null;
			query = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	void approvalRoleSourcing(BigDecimal reqID, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");

			boolean autoApprovedContractPricing = true;
			StringBuilder query = new StringBuilder("select PKG_CONTRACT_SETUP.fx_need_pricing(cai.item_id,catalog_id,cat_part_no,part_group_no,eng_eval_facility_id,catalog_company_id) catalog_desc");
			query.append(" from catalog_add_request_new carn, catalog_add_item cai where carn.company_id = cai.company_id and carn.request_id = cai.request_id and carn.request_id = ").append(reqID);
			query.append(" nvl(cai.line_status,'xx') <> 'Rejected'");
			//I just use this bean because I need a small bean with String column
			genericSqlFactory.setBeanObject(new CatalogBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogBean tmpB = (CatalogBean)iter.next();
				//if any item need pricing then the whole request has to stop
				//note: I used the catalogDesc column for convenience
				if ("Y".equalsIgnoreCase(tmpB.getCatalogDesc())) {
					autoApprovedContractPricing = false;
					break;
				}
			}

			if (autoApprovedContractPricing) {
				//auto approve Contract Pricing
				approvalRoleAutoApproved(reqID,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(reqID);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(reqID);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				//set next status to next approval role
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(") where request_id = ").append(reqID);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
					String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}
				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(reqID,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			}
			//set local variables to null
			approvalRoleV = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	public void setCatalogAddItemHetUsageRecording(BigDecimal requestId) {
		try {
			//first check to make sure that facility use this data
			StringBuilder query =new StringBuilder("select fx_feature_released('HmrbTab',carn.eng_eval_facility_id,carn.company_id) released");
			query.append(" from catalog_add_request_new carn where request_id = ").append(requestId);
			if ("Y".equals(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
				//get all lines for request
				query = new StringBuilder("select distinct line_item from catalog_add_item where request_id = ").append(requestId);
				query.append(" order by line_item");
				genericSqlFactory.setBeanObject(new CatalogAddItemBean());
				Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
				Iterator iter = dataColl.iterator();
				while (iter.hasNext()) {
					CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
					//VOC greater than 20 gram/liter and size greater than 16 oz
					query = new StringBuilder("select PKG_APPROVAL_REVIEW.FX_REQ_VOC_G_PER_L_AND_FL_OZ(").append(requestId);
					query.append(",null,null,null,null,'Y',").append(tmpB.getLineItem()).append(",'') from dual");
					String vocGt20GpLnSizeGt16Oz = genericSqlFactory.selectSingleValue(query.toString(),connection);
					String tmpHetUsageRecording = "OTR";
					if ("Y".equals(vocGt20GpLnSizeGt16Oz)) {
						tmpHetUsageRecording = "Daily Usage";
					}
					//update catalog_add_item.het_usage_recording
					query = new StringBuilder("update catalog_add_item set het_usage_recording = '").append(tmpHetUsageRecording).append("' where request_id = ").append(requestId);
					query.append(" and line_item = ").append(tmpB.getLineItem());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	private boolean isItemVerified(BigDecimal requestId) {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("select company_id,line_item,fx_item_verified(item_id) item_verified from catalog_add_item where nvl(line_status,'xx') <> 'Rejected' and request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//if any item not verified then the whole request is not verified
				//update each item_id that not verified so we can keep track which item required verifying
				StringBuilder caiQc = new StringBuilder(" ,status = ");
				query = new StringBuilder(" set item_qc_status = ");
				if ("N".equalsIgnoreCase(tmpB.getItemVerified())) {
					result = false;
					//update catalog_add_item.item_qc_status to Pending Item
					query.append("'Pending'");
					caiQc.append("'Pending QC', item_verified = 'N'");
				}else {
					//update catalog_add_item.item_qc_status to Approved Item
					query.append("'Approved'");
					caiQc.append("'Approved QC', item_verified = 'Y'");
				}
				StringBuilder whereQ = new StringBuilder(" where company_id = '").append(tmpB.getCompanyId()).append("' and request_id = ").append(requestId);
				whereQ.append(" and line_item = ").append(tmpB.getLineItem());
				//update catalog_add_item
				StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
				tmpQ.append(query).append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
				//update catalog_add_item_qc
				tmpQ = new StringBuilder("update catalog_add_item_qc");
				tmpQ.append(query).append(caiQc).append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
			}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	}

	private boolean isMsdsRequiredQc(BigDecimal requestId) {
		boolean result = false;
		try {
			StringBuilder query = new StringBuilder("select cai.company_id,cai.line_item,cai.part_id,cai.material_id");
			query.append(",pkg_company_msds.fx_msds_max_rev_date(cai.material_id,cai.company_id) customer_revision_date");
			query.append(",pkg_company_msds.fx_msds_max_rev_date(cai.material_id) global_revision_date");
			query.append( " from catalog_add_item cai");
			query.append(" where nvl(cai.line_status,'xx') <> 'Rejected' and cai.request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//check to see if material and revision date need to be qced
				query = new StringBuilder("select sum(x) from (");
				query.append(" select count(*) x from msds_qc where material_id = ").append(tmpB.getMaterialId());
				query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(tmpB.getGlobalRevisionDate()));
				query.append(" and submit_date is not null and approve_date is null");
				query.append(" union all");
				query.append(" select count(*) x from company_msds_qc where material_id = ").append(tmpB.getMaterialId());
				query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(tmpB.getCustomerRevisionDate()));
				query.append(" and company_id = '").append(tmpB.getCompanyId()).append("'");
				query.append(" and submit_date is not null and approve_date is null");
				query.append(")");
				//if has data in msds_qc or company_msds_qc
				StringBuilder caiQc = new StringBuilder(" ,status = ");
				StringBuilder updateQuery = new StringBuilder(" set msds_qc_status = ");
				if (!dataCountIsZero(query.toString())) {
					result = true;
					updateQuery.append("'Pending'");
					caiQc.append("'Pending MSDS'");
				}else {
					updateQuery.append("'Approved'");
					caiQc.append("'Approved QC'");
				}
				StringBuilder whereQ = new StringBuilder(" where company_id = '").append(tmpB.getCompanyId()).append("' and request_id = ").append(requestId);
				whereQ.append(" and line_item = ").append(tmpB.getLineItem()).append(" and part_id = ").append(tmpB.getPartId());
				//update catalog_add_item
				StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
				tmpQ.append(updateQuery).append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
				//update catalog_add_item_qc
				tmpQ = new StringBuilder("update catalog_add_item_qc");
				tmpQ.append(updateQuery).append(caiQc).append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
			}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	}

	private boolean isCustomVerified(BigDecimal requestId) {
		boolean result = true;
		try {
			//I am using the pkg_company_msds.fx_msds_verified because it should use the same logic as isMsdsIndexed; however, at this point
			//global data should already indexed and it would stop if company data is not yet indexed
			StringBuilder query = new StringBuilder("select cai.company_id,cai.line_item,cai.part_id,pkg_company_msds.fx_msds_verified(cai.material_id,cai.company_id,fx_facility_default_locale(f.company_id, f.facility_id)) msds_verified");
			query.append(" from catalog_add_item cai, catalog_add_request_new carn,");
			if ("tcm_ops".equalsIgnoreCase(this.getClient()))
				query.append(" customer.facility f");
			else
				query.append(" facility f");
			query.append(" where nvl(cai.line_status,'xx') <> 'Rejected' and cai.request_id = ").append(requestId);
			query.append(" and cai.company_id = carn.company_id and cai.request_id = carn.request_id");
			query.append(" and f.company_id = carn.company_id and f.facility_id = carn.eng_eval_facility_id");
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//if any msds not indexed then the whole request is not indexed
				//update each material_id that not indexed so we can keep track which material required indexing
				StringBuilder caiQc = new StringBuilder(" ,status = ");
				query = new StringBuilder(" set custom_indexing_status = ");
				if ("N".equalsIgnoreCase(tmpB.getMsdsVerified())) {
					result = false;
					//update catalog_add_item.msds_indexing_status to Pending
					query.append("'Pending'");
					caiQc.append("'Pending MSDS'");
				}else {
					//update catalog_add_item.msds_indexing_status to Approved
					query.append("'Approved'");
					caiQc = new StringBuilder("");
				}
				StringBuilder whereQ = new StringBuilder(" where company_id = '").append(tmpB.getCompanyId()).append("' and request_id = ").append(requestId);
				whereQ.append(" and line_item = ").append(tmpB.getLineItem()).append(" and part_id = ").append(tmpB.getPartId());
				//update catalog_add_item
				StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
				tmpQ.append(query).append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
				//update catalog_add_item_qc
				tmpQ = new StringBuilder("update catalog_add_item_qc");
				tmpQ.append(query);
				if (!StringHandler.isBlankString(caiQc.toString()))
					tmpQ.append(caiQc);
				tmpQ.append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
			}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	}

	private boolean isRequestedSdsIndexed(CatalogAddItemBean bean) {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("select pkg_company_msds.fx_msds_verified(x.material_id,null,x.to_locale_code) msds_verified");
			query.append(",x.to_locale_code,nvl2(q.insert_date,'Y','N') qc_required");
			if ("TCM_OPS".equals(this.getClient()))
				query.append(",global.pkg_data_entry_complete.fx_data_entry_complete(x.material_id");
			else
				query.append(",pkg_data_entry_complete.fx_data_entry_complete(x.material_id");
			query.append(", pkg_company_msds.fx_msds_max_rev_date(x.material_id, null, x.to_locale_code), 'N') ready_for_qc");
			query.append(" from msds_qc q,");
			query.append(" (select cai.material_id, cail.to_locale_code from catalog_add_item cai, catalog_add_item_locale cail");
			query.append(" where cai.catalog_add_item_id = ").append(bean.getCatalogAddItemId());
			query.append(" and cai.company_id = '").append(bean.getCompanyId()).append("' and cai.request_id = ").append(bean.getRequestId());
			query.append(" and cai.company_id = cail.company_id and cai.catalog_add_item_id = cail.catalog_add_item_id) x");
			query.append(" where q.material_id(+) = x.material_id");
			query.append(" and q.revision_date(+) = pkg_company_msds.fx_msds_max_rev_date(x.material_id, null, x.to_locale_code)");
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//check to see if we currently have this in requested locale
				String statusV = "";
				if ("N".equalsIgnoreCase(tmpB.getMsdsVerified()) && !
						(tmpB.isSdsQcRequired() && tmpB.isReadyForSdsQc())) {
					result = false;
					statusV = "'Pending'";
				}else
					statusV = "'Exists'";

				//update catalog_add_item_locale_status
				query = new StringBuilder("update catalog_add_item_locale set sds_indexing_status = ").append(statusV);
				query.append(" where catalog_add_item_id = ").append(bean.getCatalogAddItemId()).append(" and to_locale_code = '").append(tmpB.getToLocaleCode()).append("'");
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	} //end of method

	private boolean isMsdsIndexed(BigDecimal requestId) {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("select cai.request_id,cai.company_id,cai.line_item,cai.catalog_add_item_id,cai.part_id");
			query.append(",cai.material_id,pkg_company_msds.fx_msds_indexed(cai.material_id) msds_indexed");
			query.append(" from catalog_add_item cai");
			query.append(" where nvl(cai.line_status,'xx') <> 'Rejected' and cai.request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//if any msds not indexed then the whole request is not indexed
				//update each material_id that not indexed so we can keep track which material required indexing
				StringBuilder caiQc = new StringBuilder(" ,status = ");
				query = new StringBuilder(" set msds_indexing_status = ");
				//MSDS Indexed == mark for Maxcom, MSDS Verified == the latest revision is data entry completed
				if (!isRequestedSdsIndexed(tmpB) || "N".equalsIgnoreCase(tmpB.getMsdsIndexed())) {
					result = false;
					//update catalog_add_item.msds_indexing_status to Pending
					query.append("'Pending'");
					caiQc.append("'Pending MSDS'");
				}else {
					//update catalog_add_item.msds_indexing_status to Approved
					query.append("'Approved'");
					caiQc = new StringBuilder("");
				}
				StringBuilder whereQ = new StringBuilder(" where company_id = '").append(tmpB.getCompanyId()).append("' and request_id = ").append(requestId);
				whereQ.append(" and line_item = ").append(tmpB.getLineItem()).append(" and part_id = ").append(tmpB.getPartId());
				//update catalog_add_item
				StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
				tmpQ.append(query).append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
				//update catalog_add_item_qc
				tmpQ = new StringBuilder("update catalog_add_item_qc");
				tmpQ.append(query);
				if (!StringHandler.isBlankString(caiQc.toString()))
					tmpQ.append(caiQc);
				tmpQ.append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
			}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	}

	//return 2 boolean values
	//0 - is request for kit
	//1 - is kit verified
	private boolean[] isKitMsdsVerified(BigDecimal requestId) {
		boolean[] result = new boolean[2];
		result[0] = false;      //default to request is not for kit
		result[1] = true;       //default to kit is verified
		try {
			StringBuilder query = new StringBuilder("select cai.company_id,nvl(f.allow_mixture,'N') allow_mixture, cai.approved_cust_mixture_number, cai.line_item, count(*) number_of_components_per_line");
			query.append(" from catalog_add_item cai, catalog_add_request_new carn,");
			if ("tcm_ops".equalsIgnoreCase(this.getClient()))
				query.append(" customer.facility f");
			else
				query.append(" facility f");
			query.append(" where carn.request_id = ").append(requestId);
			query.append(" and carn.company_id = cai.company_id and carn.request_id = cai.request_id");
			query.append(" and carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id");
			query.append(" group by cai.company_id,nvl(f.allow_mixture,'N'),cai.approved_cust_mixture_number,cai.line_item");
			query.append(" order by line_item");
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//if line is kit
				if (tmpB.getNumberOfComponentsPerLine().intValue() > 1 && "Y".equals(tmpB.getAllowMixture())) {
					result[0] = true;
					StringBuilder caiQc = new StringBuilder(" ,status = ");
					query = new StringBuilder(" set material_qc_status = ");
					//if new kit
					if (StringHandler.isBlankString(tmpB.getApprovedCustMixtureNumber())) {
						result[1] = false;
						//update catalog_add_item.material_qc_status to Pending
						query.append("'Pending'");
						caiQc.append("'Pending MSDS'");
					}else {
						//update catalog_add_item.material_qc_status to Approved
						query.append("'Approved'");
						caiQc = new StringBuilder("");
					}
					StringBuilder whereQ = new StringBuilder(" where company_id = '").append(tmpB.getCompanyId()).append("' and request_id = ").append(requestId);
					whereQ.append(" and line_item = ").append(tmpB.getLineItem());
					//update catalog_add_item
					StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
					tmpQ.append(query).append(whereQ);
					genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
					//update catalog_add_item_qc
					tmpQ = new StringBuilder("update catalog_add_item_qc");
					tmpQ.append(query);
					if (!StringHandler.isBlankString(caiQc.toString()))
						tmpQ.append(caiQc);
					tmpQ.append(whereQ);
					genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
				}
			}
		}catch(Exception e) {
			log.error(e);
			result[1] = false;
		}
		return result;
	}

	//will stop the request
	// if Kit:
	// 		kit SDS is needed or any components of the kit need an SDS
	// if NON-Kit:
	//		any component and locale requested need an SDS
	public boolean isSdsExists(BigDecimal requestId) {
		boolean result = true;
		try {
			//first check to see if kit SDS is verified
			boolean[] isRequestKitA = isKitMsdsVerified(requestId);
			boolean isRequestKit = isRequestKitA[0];
			result = isRequestKitA[1];
			//next check to see component of the kit is verified
			//if (!isRequestKit) {
			StringBuilder query = new StringBuilder("select cai.company_id,cai.line_item,cai.part_id,cai.material_id,m.customer_only_msds,cai.catalog_add_item_id");
			query.append(" from catalog_add_item cai, material m");
			query.append(" where nvl(cai.line_status,'xx') <> 'Rejected' and cai.request_id = ").append(requestId);
			query.append(" and nvl(cai.material_id,-1) = m.material_id(+)");
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(), connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean) iter.next();
				//if any msds not verified then the whole request is not verified
				//update each material_id that not verified so we can keep track which material required verifying
				StringBuilder caiQc = new StringBuilder(" ,status = ");
				query = new StringBuilder(" set material_qc_status = ");
				if (tmpB.getMaterialId() == null || "Y".equals(tmpB.getCustomerOnlyMsds()) || distributionCatalogAddRequest) {
					result = false;
					//update catalog_add_item.material_qc_status to Pending
					query.append("'Pending',material_id_sourced = 'N'");
					caiQc.append("'Pending MSDS'");
					StringBuilder innerQuery = new StringBuilder("update catalog_add_item_locale set sds_sourcing_status = 'Pending'");
					innerQuery.append(" where company_id = '").append(tmpB.getCompanyId()).append("' and catalog_add_item_id = ").append(tmpB.getCatalogAddItemId());
					genericSqlFactory.deleteInsertUpdate(innerQuery.toString(), connection);
				} else {
					if (!isRequestedSdsExist(tmpB)) {
						result = false;
						query.append("'Pending',material_id_sourced = 'X'");
						caiQc.append("'Pending MSDS'");
					} else {
						if (!isRequestKit) {
							//update catalog_add_item.material_qc_status to Approved
							query.append("'Approved',material_id_sourced = 'Y'");
							caiQc = new StringBuilder("");
						}else {
							//don't update catalog add item here because it already handle in isKitMsdsVerified if request is kit
							query = new StringBuilder("");
							caiQc = new StringBuilder("");
						}
					}
				}

				//don't update catalog add item here because it already handle in isKitMsdsVerified if request is kit
				if (!StringHandler.isBlankString(query.toString())) {
					StringBuilder whereQ = new StringBuilder(" where company_id = '").append(tmpB.getCompanyId()).append("' and request_id = ").append(requestId);
					whereQ.append(" and line_item = ").append(tmpB.getLineItem()).append(" and part_id = ").append(tmpB.getPartId());
					//update catalog_add_item
					StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
					tmpQ.append(query).append(whereQ);
					genericSqlFactory.deleteInsertUpdate(tmpQ.toString(), connection);

					//update catalog_add_item_qc
					tmpQ = new StringBuilder("update catalog_add_item_qc");
					tmpQ.append(query);
					if (!StringHandler.isBlankString(caiQc.toString()))
						tmpQ.append(caiQc);
					tmpQ.append(whereQ);
					genericSqlFactory.deleteInsertUpdate(tmpQ.toString(), connection);
				}
			}
			//}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	}  //end of method

	private boolean isRequestedSdsExist(CatalogAddItemBean bean) {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("select cail.to_locale_code");
			query.append(" from catalog_add_item_locale cail");
			query.append(" where cail.catalog_add_item_id = ").append(bean.getCatalogAddItemId());
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//check to see if we currently have this in requested locale
				query = new StringBuilder("select count(*) from global.msds where material_id = ").append(bean.getMaterialId());
				query.append(" and locale_code = '").append(tmpB.getToLocaleCode()).append("'");
				query.append(" and content is not null");
				String tmpV = genericSqlFactory.selectSingleValue(query.toString(),connection);
				String statusV = "";
				if ("0".equals(tmpV)) {
					result = false;
					statusV = "'Pending'";
				}else
					statusV = "'Exists'";

				//update catalog_add_item_locale_status
				query = new StringBuilder("update catalog_add_item_locale set sds_sourcing_status = ").append(statusV);
				query.append(" where catalog_add_item_id = ").append(bean.getCatalogAddItemId()).append(" and to_locale_code = '").append(tmpB.getToLocaleCode()).append("'");
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	} //end of method

	private void approvalRoleQcItem(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");
			Vector createCatalogQueueV = (Vector) approvalDetailH.get("CREATE_CATALOG_QUEUE");

			StringBuilder query = null;
			//if item is verified or request is for new MSDS approval code then auto approved QC
			if (isItemVerified(requestId) || "7".equals((String)approvalDetailH.get("STARTING_VIEW"))) {
				//auto approved item qc
				approvalRoleAutoApproved(requestId,nextApprovalRole);
				//set catalog_add_item.het_usage_recording
				setCatalogAddItemHetUsageRecording(requestId);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				if (!approvalGroupSeqSet) {
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
					query.append(",qc_status = 'Pending Item QC'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}else {
					//just update the status
					//if any lines with material_id is null then the whole request is Pending MSDS
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("qc_status = 'Pending Item QC'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

				//insert record into catalog_queue for vendor processing if approval role is qualified.
				boolean supplierIsWesco = true;
				if ("Y".equals(createCatalogQueueV.elementAt(currentGroupPos+1)) && !keepItInHouse(requestId))
					supplierIsWesco = false;
				createWorkQueueItem((String)approvalDetailH.get("COMPANY_ID"),requestId,"Item Creation","item_qc_status",startingView, supplierIsWesco, "");

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	//Master data team has the ability to keep the catalog add request in house during pre-screening queue
	private boolean keepItInHouse(BigDecimal requestId) {
		boolean result = false;
		try {
			StringBuilder query = new StringBuilder("select md_verified from catalog_add_request_new where request_id = ").append(requestId);
			String mdVerified = genericSqlFactory.selectSingleValue(query.toString(), connection);
			if ("Y".equals(mdVerified))
				result = true;
		}catch (Exception e) {
			log.error(e);
		}
		return result;
	} //end of method

	private void approvalRoleQcMaterial(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");
			Vector createCatalogQueueV = (Vector) approvalDetailH.get("CREATE_CATALOG_QUEUE");

			StringBuilder query = null;
			//if msds is verified
			if (isSdsExists(requestId)) {
				//auto approved Material QC
				approvalRoleAutoApproved(requestId,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				if (!approvalGroupSeqSet) {
					//if qc_status is not set or setted to Pending Translation then set qc_status and qc_date here
					//the reason for Pending Translation is that any translation has to take place before sending requests to our catalog group
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
					query.append(",qc_status = 'Pending SDS Sourcing'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}else {
					//just update the status
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("qc_status = 'Pending SDS Sourcing'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

				//insert record into catalog_queue for vendor processing if approval role is qualified.
				String wqiCompanyId = (String)approvalDetailH.get("COMPANY_ID");
				String wqiLineStatus = "material_qc_status";
				boolean supplierIsWesco = true;
				if ("Y".equals(createCatalogQueueV.elementAt(currentGroupPos+1)) && !keepItInHouse(requestId))
					supplierIsWesco = false;
				createWorkQueueItem(wqiCompanyId,requestId,"SDS Sourcing", wqiLineStatus,startingView,supplierIsWesco,"");

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	private boolean hasRequestedSdsForAuthoring(CatalogAddItemBean bean) {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("select cail.to_locale_code");
			query.append(" from catalog_add_item_locale cail");
			query.append(" where cail.catalog_add_item_id = ").append(bean.getCatalogAddItemId());
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			MsdsIndexingProcess msdsIndexingProcess = new MsdsIndexingProcess(personnelBean.getPersonnelIdBigDecimal(), this.getClient());
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//check to see if we currently have this in requested locale
				SimpleDateFormat dateFormatter = new SimpleDateFormat( "MM/dd/yyyy" );
				String tmpDate = dateFormatter.format(bean.getSourceRevisionDate());
				query = new StringBuilder("select count(*) from global.msds where material_id = ").append(bean.getMaterialId());
				query.append(" and locale_code = '").append(tmpB.getToLocaleCode()).append("' and trunc(revision_date) = to_date('").append(tmpDate).append("','MM/dd/yyyy')");
				query.append(" and content is not null");
				String tmpV = genericSqlFactory.selectSingleValue(query.toString(),connection);
				String statusV = "";
				if ("0".equals(tmpV)) {
					result = false;
					statusV = "'Pending'";
					//we have companies where the revision_date of an SDS is the date of creation NOT when the Manufacturer produced the document.
					if (personnelBean != null && !personnelBean.isFeatureReleased("SdsAuthorManualCreateRevDate", bean.getFacilityId(), bean.getCompanyId())) {
						//look at ApprovedMaterialProcess for date formatting
						Date newRevDate = msdsIndexingProcess.getNewRevisionDate(bean.getMaterialId().toString(), dateFormatter.parse(tmpDate), msdsAuthorId, genericSqlFactory, connection);
						//insert data into global.msds
						query = new StringBuilder("insert into global.msds (material_id,revision_date,locale_code,on_line,msds_author_id)");
						query.append(" values (").append(bean.getMaterialId()).append(",").append(DateHandler.getOracleToDateFunction(newRevDate)).append(",'").append(tmpB.getToLocaleCode()).append("','N'");
						query.append(",'").append(msdsAuthorId).append("'");
						query.append(")");
						genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
						//insert data into material_locale
						query = new StringBuilder("insert into material_locale (material_id,locale_code)");
						query.append(" select material_id,locale_code from global.msds where material_id = ").append(bean.getMaterialId());
						query.append(" and locale_code = '").append(tmpB.getToLocaleCode()).append("' and revision_date = ").append(DateHandler.getOracleToDateFunction(newRevDate));
						query.append(" and (material_id,locale_code) not in (select material_id,locale_code from material_locale where material_id = ").append(bean.getMaterialId());
						query.append(" and locale_code = '").append(tmpB.getToLocaleCode()).append("')");
						genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
						//insert data into global.msds_locale
						query = new StringBuilder("insert into global.msds_locale (material_id,revision_date,locale_code,on_line)");
						query.append(" select material_id,revision_date,locale_code,'N' on_line from global.msds where material_id = ").append(bean.getMaterialId());
						query.append(" and locale_code = '").append(tmpB.getToLocaleCode()).append("' and revision_date = ").append(DateHandler.getOracleToDateFunction(newRevDate));
						genericSqlFactory.deleteInsertUpdate(query.toString(), connection);
					}
				}else
					statusV = "'Exists'";

				//update catalog_add_item_locale_status
				query = new StringBuilder("update catalog_add_item_locale set sds_sourcing_status = ").append(statusV);
				query.append(" where catalog_add_item_id = ").append(bean.getCatalogAddItemId()).append(" and to_locale_code = '").append(tmpB.getToLocaleCode()).append("'");
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	} //end of method

	private boolean isSdsAuthored(BigDecimal requestId) {
		boolean result = true;
		try {
			StringBuilder query = new StringBuilder("select cai.company_id,cai.line_item,cai.part_id,cai.source_revision_date,cai.material_id,cai.catalog_add_item_id");
			query.append(",cai.request_id,cai.company_id,carn.eng_eval_facility_id facility_id");
			query.append(" from catalog_add_item cai, catalog_add_request_new carn");
			query.append(" where nvl(cai.line_status,'xx') <> 'Rejected' and cai.request_id = ").append(requestId);
			query.append(" and cai.company_id = carn.company_id and cai.request_id = carn.request_id");
			genericSqlFactory.setBeanObject(new CatalogAddItemBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddItemBean tmpB = (CatalogAddItemBean)iter.next();
				//if any msds not verified then the whole request is not verified
				//update each material_id that not verified so we can keep track which material required verifying
				StringBuilder caiQc = new StringBuilder(" ,status = ");
				query = new StringBuilder(" set sds_authoring_status = ");
				if (!hasRequestedSdsForAuthoring(tmpB)) {
					result = new Boolean(false);
					//update catalog_add_item.material_qc_status to Pending
					query.append("'Pending'");
					caiQc.append("'Pending MSDS'");
				}else {
					//update catalog_add_item.material_qc_status to Approved
					query.append("'Approved'");
					caiQc = new StringBuilder("");
				}
				StringBuilder whereQ = new StringBuilder(" where company_id = '").append(tmpB.getCompanyId()).append("' and request_id = ").append(requestId);
				whereQ.append(" and line_item = ").append(tmpB.getLineItem()).append(" and part_id = ").append(tmpB.getPartId());
				//update catalog_add_item
				StringBuilder tmpQ = new StringBuilder("update catalog_add_item");
				tmpQ.append(query).append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
				//update catalog_add_item_qc
				tmpQ = new StringBuilder("update catalog_add_item_qc");
				tmpQ.append(query);
				if (!StringHandler.isBlankString(caiQc.toString()))
					tmpQ.append(caiQc);
				tmpQ.append(whereQ);
				genericSqlFactory.deleteInsertUpdate(tmpQ.toString(),connection);
			}
		}catch(Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	} //end of method

	private void approvalRoleSdsAuthoring(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");
			Vector createCatalogQueueV = (Vector) approvalDetailH.get("CREATE_CATALOG_QUEUE");

			StringBuilder query = null;
			if (isSdsAuthored(requestId)) {
				//auto approved Material QC
				approvalRoleAutoApproved(requestId,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				if (!approvalGroupSeqSet) {
					//if qc_status is not set or setted to Pending Translation then set qc_status and qc_date here
					//the reason for Pending Translation is that any translation has to take place before sending requests to our catalog group
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
					query.append(",qc_status = 'Pending SDS Sourcing'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}else {
					//just update the status
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("qc_status = 'Pending SDS Sourcing'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

				//insert record into catalog_queue for vendor processing if approval role is qualified.
				boolean supplierIsWesco = true;
				if ("Y".equals(createCatalogQueueV.elementAt(currentGroupPos+1)) && !keepItInHouse(requestId))
					supplierIsWesco = false;
				createWorkQueueItem((String)approvalDetailH.get("COMPANY_ID"),requestId,"SDS Sourcing","sds_authoring_status",startingView,supplierIsWesco,"");

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method


	private boolean isRequestSentToMaxcomForIndexing(BigDecimal requestId) {
		boolean result = false;
		try {
			//function below return N if the latest revision date is id_only = 'Y'
			StringBuilder query = new StringBuilder("select min(pkg_company_msds.fx_msds_indexed(cai.material_id)) msds_indexed from catalog_add_item cai where nvl(cai.line_status,'xx') <> 'Rejected' and cai.request_id = ").append(requestId);
			if ("N".equals(genericSqlFactory.selectSingleValue(query.toString(),connection)))
				result = true;
		}catch (Exception e) {
			log.error(e);
		}
		return result;
	} //end of method

	public void createWorkQueueItem(String companyId, BigDecimal requestId, String task, String caiLineStatus, String startingView, final boolean supplierIsWesco, String tmpCatalogVendorAssignmentId) throws Exception {
		try {
			//if creating work queue for material level
			if ("SDS Sourcing".equals(task) || "SDS Indexing".equals(task)) {
				//if catalog add is for authoring
				if (SDS_AUTHORING_STARTING_VIEW.equals(startingView)) {
					if ("SDS Sourcing".equals(task))
						task = "SDS Authoring";
				}

				//if task is SDS Indexing then check to see if any
				boolean sentToMaxcomForIndexing = false;
				if ("SDS Indexing".equals(task)) {
					sentToMaxcomForIndexing = isRequestSentToMaxcomForIndexing(requestId);
				}

				//create queue item for SDS Authoring
				//determine which supplier to send request to
				StringBuilder query = new StringBuilder("select cai.request_id,cai.line_item,cai.material_id,cai.source_revision_date,cai.catalog_add_item_id,cail.to_locale_code");
				query.append(",cqi.task,cqi.catalog_queue_item_id,cqi.item_id,cai.company_id,carn.eng_eval_facility_id facility_id");
				query.append(" from catalog_add_item cai, catalog_add_item_locale cail, catalog_queue_item cqi, catalog_add_request_new carn");
				query.append(" where cai.company_id = '").append(companyId).append("' and cai.request_id = ").append(requestId);
				query.append(" and cai.").append(caiLineStatus).append(" = 'Pending'");
				query.append(" and cai.company_id = carn.company_id and cai.request_id = carn.request_id");
				if ("SDS Authoring".equals(task) || "SDS Sourcing".equals(task))
					query.append(" and cail.sds_sourcing_status").append(" = 'Pending'");
				else
					query.append(" and cail.sds_indexing_status").append(" = 'Pending'");
				query.append(" and cqi.task = '").append(task).append("'");
				query.append(" and cai.catalog_add_item_id = cail.catalog_add_item_id and cail.to_locale_code = cqi.locale_code");
				query.append(" and not exists (select null from catalog_queue cq where cq.task = '").append(task)
						.append("' and cq.catalog_add_request_id = cai.request_id and cq.line_item = cai.line_item")
						.append(" and cq.catalog_add_item_id = cai.catalog_add_item_id and cq.cat_add_reversed <> 'Y')");
				genericSqlFactory.setBeanObject(new CatalogAddItemBean());
				Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
				Iterator iter = dataColl.iterator();
				while (iter.hasNext()) {
					CatalogAddItemBean tmpB = (CatalogAddItemBean) iter.next();
					//determine which supplier to send request line to
					//keep the request to same supplier per task
					if (supplierIsWesco) {
						query = new StringBuilder("select catalog_vendor_assignment_id from catalog_vendor_assignment");
						query.append(" where supplier = ").append(SqlHandler.delimitString(CatalogQueueBean.WESCO_SUPPLIER_ID));
						query.append(" and catalog_queue_item_id = ").append(tmpB.getCatalogQueueItemId());
						tmpCatalogVendorAssignmentId = genericSqlFactory.selectSingleValue(query.toString(), connection);
					}else if (StringHandler.isBlankString(tmpCatalogVendorAssignmentId)) {
						query = new StringBuilder("select pkg_catalog_queue.fx_next_catalog_vendor(").append(tmpB.getCatalogQueueItemId()).append(") from dual");
						tmpCatalogVendorAssignmentId = genericSqlFactory.selectSingleValue(query.toString(), connection);
					}else {
						query = new StringBuilder("select pkg_catalog_queue.fx_next_catalog_vendor(").append(tmpB.getCatalogQueueItemId()).append(",").append(tmpCatalogVendorAssignmentId).append(") from dual");
						tmpCatalogVendorAssignmentId = genericSqlFactory.selectSingleValue(query.toString(), connection);
					}

					if (!StringHandler.isBlankString(tmpCatalogVendorAssignmentId)) {
						if ("SDS Authoring".equals(task)) {
							//we have companies where the revision_date of an SDS is the date of creation NOT when the Manufacturer produced the document.
							if (personnelBean != null && !personnelBean.isFeatureReleased("SdsAuthorManualCreateRevDate", tmpB.getFacilityId(), tmpB.getCompanyId())) {
								//get revision_date for given locale (newly created)
								query = new StringBuilder("select to_char(max(revision_date),'MM/dd/yyyy hh:mi:ss AM') from msds where material_id = ").append(tmpB.getMaterialId());
								query.append(" and locale_code = '").append(tmpB.getToLocaleCode()).append("' and to_char(revision_date,'MM/dd/yyyy') = '").append(DateHandler.formatDate(tmpB.getSourceRevisionDate(), "MM/dd/yyyy")).append("'");
								query.append(" and on_line = 'N' and msds_author_id = '").append(msdsAuthorId).append("'");
								tmpB.setRevisionDate(DateHandler.getDateFromString("MM/dd/yyyy hh:mm:ss a", genericSqlFactory.selectSingleValue(query.toString(), connection)));
							}
						}
						tmpB.setCatalogVendorAssignmentId(new BigDecimal(tmpCatalogVendorAssignmentId));
						//if any material on request sent to Maxcom then set queue status = 'Assigned' and assigned_to = -1
						if (sentToMaxcomForIndexing) {
							tmpB.setCatalogQueueStatus("Assigned");
							tmpB.setCatalogQueueAssignedTo(new BigDecimal(-1));
						}
						createCatalogQueue(tmpB);
					}
				}
			}else if ("Item Creation".equals(task)) {
				//create queue item for Item Creation
				StringBuilder query = new StringBuilder("select distinct cai.request_id,cai.line_item,null catalog_add_item_id,cqi.locale_code to_locale_code");
				query.append(",cqi.task,cqi.catalog_queue_item_id,cqi.item_id");
				query.append(" from catalog_add_item cai, catalog_queue_item cqi");
				query.append(" where cai.company_id = '").append(companyId).append("' and cai.request_id = ").append(requestId);
				query.append(" and cai.").append(caiLineStatus).append(" = 'Pending'");
				query.append(" and cqi.task = '").append(task).append("'");
				query.append(" and nvl(cai.locale_code,'en_US') = cqi.locale_code");
				query.append(" and not exists (select null from catalog_queue cq where cq.task = 'Item Creation' and cq.catalog_add_request_id = cai.request_id")
						.append(" and cq.line_item = cai.line_item and cq.approved_date is null and cq.status not in ('Reversed'))");
				genericSqlFactory.setBeanObject(new CatalogAddItemBean());
				Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
				Iterator iter = dataColl.iterator();
				while (iter.hasNext()) {
					CatalogAddItemBean tmpB = (CatalogAddItemBean) iter.next();
					//determine which supplier to send request line to
					//keep the request to same supplier per task
					if (supplierIsWesco) {
						query = new StringBuilder("select catalog_vendor_assignment_id from catalog_vendor_assignment");
						query.append(" where supplier = ").append(SqlHandler.delimitString(CatalogQueueBean.WESCO_SUPPLIER_ID));
						query.append(" and catalog_queue_item_id = ").append(tmpB.getCatalogQueueItemId());
						tmpCatalogVendorAssignmentId = genericSqlFactory.selectSingleValue(query.toString(), connection);
					}else if (StringHandler.isBlankString(tmpCatalogVendorAssignmentId)) {
						query = new StringBuilder("select pkg_catalog_queue.fx_next_catalog_vendor(").append(tmpB.getCatalogQueueItemId()).append(") from dual");
						tmpCatalogVendorAssignmentId = genericSqlFactory.selectSingleValue(query.toString(), connection);
					}else {
						query = new StringBuilder("select pkg_catalog_queue.fx_next_catalog_vendor(").append(tmpB.getCatalogQueueItemId()).append(",").append(tmpCatalogVendorAssignmentId).append(") from dual");
						tmpCatalogVendorAssignmentId = genericSqlFactory.selectSingleValue(query.toString(), connection);
					}
					if (!StringHandler.isBlankString(tmpCatalogVendorAssignmentId)) {
						tmpB.setCatalogVendorAssignmentId(new BigDecimal(tmpCatalogVendorAssignmentId));
						createCatalogQueue(tmpB);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			StringBuilder msg = new StringBuilder("Error occurred while trying to create work queue item for: ");
			msg.append("\nRequest ID: ").append(requestId).append("\nTask: ").append(task).append("\nLine Status: ").append(caiLineStatus);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Catalog Add process failed to create work queue",msg.toString());
		}
	} //end of method

	public void populateCatalogAddItemLocale(BigDecimal requestId) throws Exception{
		populateCatalogAddItemLocale(requestId,new BigDecimal(0));
	}

	public void populateCatalogAddItemLocale(BigDecimal requestId, BigDecimal startingView) throws Exception {
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_item_locale (company_id,catalog_add_item_id,to_locale_code)");
			query.append(" select cai.company_id,cai.catalog_add_item_id,fl.locale_code from catalog_add_request_new carn, catalog_add_item cai, facility_locale fl");
			query.append(" where carn.request_id = ").append(requestId).append(" and carn.company_id = cai.company_id and carn.request_id = cai.request_id");
			query.append(" and carn.company_id = fl.company_id and carn.eng_eval_facility_id = fl.facility_id");
			query.append(" and nvl(cai.line_status,'X') <> 'Rejected'");
			//if catalog add is for SDS Authoring then use fl.cat_add_sds_authoring flag
			if (startingView != null && startingView.intValue() == 9) {
				query.append(" and fl.cat_add_sds_authoring = 'Y'");
			}else {
				query.append(" and fl.create_catalog_queue_task = 'Y'");
			}
			query.append(" and not exists (select null from catalog_add_item_locale cail where cail.company_id = cai.company_id and cail.catalog_add_item_id = cai.catalog_add_item_id");
			query.append(" and cail.to_locale_code = fl.locale_code)");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Failed to insert data into catalog_add_item_locale","Failed to insert data into catalog_add_item_locale for request: "+requestId);
		}
	}  //end of method

	public void createCatalogQueue(CatalogAddItemBean bean) throws Exception {
		try {
			StringBuilder insertQuery = new StringBuilder("insert into catalog_queue (catalog_add_request_id,line_item,catalog_add_item_id");
			insertQuery.append(",insert_date,task,item_id,supplier,locale_code,status,unit_price,currency_id");
			insertQuery.append(",addl_ingred_item_id,addl_ingred_unit_price,addl_ingred_base_qty,addl_ingred_bundle_qty");
			insertQuery.append(",ops_entity_id,catalog_vendor_assignment_id,revision_date");
			insertQuery.append(",billed_by_component");
			if (bean.getCatalogQueueAssignedTo() != null)
				insertQuery.append(",assigned_to,assigned_date");
			insertQuery.append(")");
			insertQuery.append(" select ").append(bean.getRequestId()).append(",").append(bean.getLineItem()).append(",").append(bean.getCatalogAddItemId());
			insertQuery.append(",sysdate,cqi.task,cqi.item_id,cva.supplier,cqi.locale_code");
			if (!StringHandler.isBlankString(bean.getCatalogQueueStatus()))
				insertQuery.append(",'").append(bean.getCatalogQueueStatus()).append("'");
			else
				insertQuery.append(",'Open'");	//status
			insertQuery.append(",cva.unit_price,cva.currency_id");
			insertQuery.append(",cqi.addl_ingred_item_id,cva.addl_ingred_unit_price,cva.addl_ingred_base_qty,cva.addl_ingred_bundle_qty");
			insertQuery.append(",cva.ops_entity_id,cva.catalog_vendor_assignment_id,").append(DateHandler.getOracleToDateFunction(bean.getRevisionDate(),"MM/dd/yyyy hh:mm:ss a","MM/DD/RRRR hh:mi:ss AM"));
			insertQuery.append(",cva.billed_by_component");
			if (bean.getCatalogQueueAssignedTo() != null)
				insertQuery.append(",").append(bean.getCatalogQueueAssignedTo()).append(",sysdate");
			insertQuery.append(" from catalog_vendor_assignment cva, catalog_queue_item cqi where catalog_vendor_assignment_id = ").append(bean.getCatalogVendorAssignmentId());
			insertQuery.append(" and cva.catalog_queue_item_id = cqi.catalog_queue_item_id");
			//insert data
			genericSqlFactory.deleteInsertUpdate(insertQuery.toString(),connection);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	} //end of method

	private void approvalRoleMsdsIndexing(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");
			Vector createCatalogQueueV = (Vector) approvalDetailH.get("CREATE_CATALOG_QUEUE");

			StringBuilder query = null;
			if (isMsdsIndexed(requestId)) {
				//auto approved MSDS Indexing
				approvalRoleAutoApproved(requestId,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12,msds_indexing_status = 'Approved' where request_id = ").append(requestId);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9,msds_indexing_status = 'Approved' where request_id = ").append(requestId);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//just update the status
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("msds_indexing_status = 'Approved'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				if (!approvalGroupSeqSet) {
					//if qc_status is not set or setted to Pending Translation then set qc_status and qc_date here
					//the reason for Pending Translation is that any translation has to take place before sending requests to our catalog group
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
					query.append(",msds_indexing_status = 'Pending'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}else {
					//just update the status
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("msds_indexing_status = 'Pending'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

				String wqiCompanyId = (String)approvalDetailH.get("COMPANY_ID");
				String wqiLineStatus = "msds_indexing_status";
				boolean supplierIsWesco = true;
				//insert record into catalog_queue for vendor processing if approval role is qualified.
				if ("Y".equals(createCatalogQueueV.elementAt(currentGroupPos+1)) && !keepItInHouse(requestId))
					supplierIsWesco = false;
				createWorkQueueItem(wqiCompanyId,requestId,"SDS Indexing", wqiLineStatus,startingView,supplierIsWesco,"");

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	private void approvalRoleCustomIndexing(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");

			StringBuilder query = null;
			if (isCustomVerified(requestId)) {
				//auto approved Custom Indexing
				approvalRoleAutoApproved(requestId,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				if (!approvalGroupSeqSet) {
					//if qc_status is not set or setted to Pending Translation then set qc_status and qc_date here
					//the reason for Pending Translation is that any translation has to take place before sending requests to our catalog group
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
					query.append(",qc_status = 'Pending SDS Custom Indexing'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}else {
					//just update the status
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("qc_status = 'Pending SDS Custom Indexing'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	private boolean isRequestRequiredPreScreening (BigDecimal requestId, String startingView) {
		boolean result = false;
		try {
			//if any records in catalog add item where material_id or item_id is null then this request needs to stop for pre-screening
			StringBuilder query = new StringBuilder("select count(*) from catalog_add_item where request_id = ").append(requestId);
			//if material catalog add
			if ("6".equals(startingView) || "7".equals(startingView))
				query.append(" and material_id is null");
			else //part catalog add
				query.append(" and (material_id is null or item_id is null)");

			if (!dataCountIsZero(query.toString()))
				result = true;

			//also check to see if user uploaded SDS
			if (!result) {
				if (!StringHandler.isBlankString(getUserUploadedMsdsForRequest(requestId)))
					result = true;
			}
		}catch(Exception e) {
			log.error(e);
		}
		return result;
	} //end of method

	private void approvalRolePreScreening(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");

			StringBuilder query = null;
			if (!isRequestRequiredPreScreening(requestId,startingView)) {
				//auto approved SDS QC
				approvalRoleAutoApproved(requestId,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				if (!approvalGroupSeqSet) {
					//if qc_status is not set or setted to Pending Translation then set qc_status and qc_date here
					//the reason for Pending Translation is that any translation has to take place before sending requests to our catalog group
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
					query.append(",qc_status = 'Pending Assignment'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}else {
					//just update the status
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("qc_status = 'Pending Assignment'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method


	private void approvalRoleSdsQc(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String aGroup = (String) approvalDetailH.get("CURRENT_GROUP");
			String aGroupSeq = (String) approvalDetailH.get("CURRENT_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");

			StringBuilder query = null;
			if (!isMsdsRequiredQc(requestId)) {
				//auto approved SDS QC
				approvalRoleAutoApproved(requestId,nextApprovalRole);
				//now find the next approval role
				currentGroupPos++;
				if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
					if (!approvalGroupSeqSet) {
						if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
						}else {
							query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
						}
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						result[EngEvalProcess.DONE_INDEX] = "Done";
					}
				}else {
					//if the approval role is set for that group and seq
					//then only continue to check within that group and seq
					if (approvalGroupSeqSet) {
						//handle parallel approval roles with auto approval
						//continue to go thru next approval roles if it's in the same group
						//reason for +1 is next approval role
						if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
							if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
									aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
								nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
								setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
							}
						}
						//else it's not set so it's okay to continue and auto approve other group and seq
					}else {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
					}
				}
			}else {
				if (!approvalGroupSeqSet) {
					//if qc_status is not set or setted to Pending Translation then set qc_status and qc_date here
					//the reason for Pending Translation is that any translation has to take place before sending requests to our catalog group
					query = new StringBuilder("update catalog_add_request_new carn set approval_group_seq_start_time = sysdate, approval_group_seq = ").append((String)aGroupSeqV.elementAt(currentGroupPos+1));
					query.append(",request_status = (select request_status from vv_catalog_add_request_status cars where carn.company_id = cars.company_id");
					query.append(" and approval_group = ").append((String)aGroupV.elementAt(currentGroupPos+1)).append(")");
					query.append(",qc_status = 'Pending [M]SDS QC'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//if next group and seq is the same as current then don't send email
					if (aGroup.equals(aGroupV.elementAt(currentGroupPos+1)) &&
							aGroupSeq.equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "Same group";
					}else {
						result[EngEvalProcess.NEXT_GROUP_INDEX] = "New group";
					}
					approvalGroupSeqSet = true;
				}else {
					//just update the status
					query = new StringBuilder("update catalog_add_request_new carn set ");
					query.append("qc_status = 'Pending [M]SDS QC'");
					query.append(" where request_id = ").append(requestId);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

				//handle parallel approval roles with auto approval
				//continue to go thru next approval roles if it's in the same group
				//reason for +1 is next approval role
				//reason for +2 is next next approval role
				if (currentGroupPos+2 < aGroupV.size()) {   //if there more approval roles
					if (aGroupV.elementAt(currentGroupPos+1).equals(aGroupV.elementAt(currentGroupPos+2)) &&
							aGroupSeqV.elementAt(currentGroupPos+1).equals(aGroupSeqV.elementAt(currentGroupPos+2))) {
						nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+2);
						setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos+1,result);
					}
				}
			} //end of item not verified
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			aGroup = null;
			aGroupSeq = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	private void createTemporaryItem(BigDecimal requestId) {
		try {
			StringBuilder query = new StringBuilder("select seq_item_id.nextval from dual");
			BigDecimal itemId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			//insert into item
			query = new StringBuilder("insert into item (item_id, item_type, case_qty, stocking_type, item_desc)");
			query.append(" select ").append(itemId).append(",'MX','1','S',").append("cai.manufacturer|| ' '|| cai.material_desc|| ' '||");
			query.append("cai.components_per_item|| ' x '||cai.part_size||' '||cai.size_unit||' '||cai.pkg_style from catalog_add_item cai");
			query.append(" where request_id = ").append(requestId).append(" and line_item = 1 and part_id = 1");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//update catalog_add_item
			query = new StringBuilder("update catalog_add_item set item_id = ").append(itemId).append(" where request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//insert into part
			query = new StringBuilder("insert into part (part_id,material_id,components_per_item,part_size,size_unit,pkg_style,item_id,mfg_part_no,size_verified,item_verified,temp_verified)");
			query.append(" select part_id,material_id,components_per_item,part_size,size_unit,pkg_style,item_id,mfg_catalog_id,'N','N','N' from catalog_add_item");
			query.append(" where request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
		}
	} //end of method

	private void approvalRoleCreateTemporaryItem(BigDecimal requestId, Hashtable approvalDetailH, String nextApprovalRole, int currentGroupPos, String[] result) throws Exception {
		try {
			Vector approvalRoleV = (Vector) approvalDetailH.get("APPROVAL_ROLE");
			Vector aGroupV = (Vector) approvalDetailH.get("APPROVAL_GROUP");
			Vector aGroupSeqV = (Vector) approvalDetailH.get("APPROVAL_GROUP_SEQ");
			String eEvaluation = (String) approvalDetailH.get("EVALUATION");
			String startingView = (String) approvalDetailH.get("STARTING_VIEW");

			StringBuilder query = null;
			//create MX item
			createTemporaryItem(requestId);

			//auto approved Create Item
			approvalRoleAutoApproved(requestId,nextApprovalRole);
			//now find the next approval role
			currentGroupPos++;
			if (currentGroupPos+1 == aGroupV.size()) {    //this mean that we reached the end of the approval process
				if (!approvalGroupSeqSet) {
					if ("Y".equalsIgnoreCase(eEvaluation) || "6".equals(startingView) || "7".equals(startingView)) {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 12 where request_id = ").append(requestId);
					}else {
						query = new StringBuilder("update catalog_add_request_new set approval_group_seq_start_time = null, approval_group_seq = 0,request_status = 9 where request_id = ").append(requestId);
					}
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					result[EngEvalProcess.DONE_INDEX] = "Done";
				}
			}else {
				//if the approval role is set for that group and seq
				//then only continue to check within that group and seq
				if (approvalGroupSeqSet) {
					//handle parallel approval roles with auto approval
					//continue to go thru next approval roles if it's in the same group
					//reason for +1 is next approval role
					if (currentGroupPos+1 < aGroupV.size()) {   //if there more approval roles
						if (aGroupV.elementAt(currentGroupPos).equals(aGroupV.elementAt(currentGroupPos+1)) &&
								aGroupSeqV.elementAt(currentGroupPos).equals(aGroupSeqV.elementAt(currentGroupPos+1))) {
							nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
							setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
						}
					}
					//else it's not set so it's okay to continue and auto approve other group and seq
				}else {
					nextApprovalRole = (String) approvalRoleV.elementAt(currentGroupPos+1);
					setNextApprovalRole(requestId,approvalDetailH,nextApprovalRole,currentGroupPos,result);
				}
			}
			//set local variable to null
			query = null;
			aGroupV = null;
			aGroupSeqV = null;
			eEvaluation = null;
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
	} //end of method

	private String[] approvalRoleAutoApproved(BigDecimal requestId, String approvalRole, String autoApproveMsg) throws Exception {
		String[] result = new String[2];
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_approval(company_id,request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id,start_time)");
			query.append(" select distinct carn.company_id,carn.request_id,-1,sysdate,'Approved','").append(autoApproveMsg).append("','").append(approvalRole).append("',carn.eng_eval_facility_id,carn.catalog_id,carn.catalog_company_id,sysdate from");
			query.append(" catalog_add_request_new carn where carn.request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	} //end of method

	private String[] approvalRoleAutoApproved(BigDecimal requestId, String approvalRole) throws Exception {
		String[] result = new String[2];
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_approval(company_id,request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id,start_time)");
			query.append(" select distinct carn.company_id,carn.request_id,-1,sysdate,'Approved','Auto approved','").append(approvalRole).append("',carn.eng_eval_facility_id,carn.catalog_id,carn.catalog_company_id,sysdate from");
			query.append(" catalog_add_request_new carn where carn.request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	} //end of method

	private String[] approvalRoleAutoApprovedWithRestriction(BigDecimal requestId, String approvalRole) throws Exception {
		String[] result = new String[2];
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_approval(company_id,request_id,chemical_approver,approval_date,status,reason,approval_role,facility_id,catalog_id,catalog_company_id,start_time,restricted)");
			query.append(" select distinct carn.company_id,carn.request_id,-1,sysdate,'Approved','Auto approved','").append(approvalRole).append("',carn.eng_eval_facility_id,carn.catalog_id,carn.catalog_company_id,sysdate,'Y' from");
			query.append(" catalog_add_request_new carn where carn.request_id = ").append(requestId);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	} //end of method


	private String getFacWorkAreaForRequest(BigDecimal requestId) {
		StringBuilder result = new StringBuilder("");
		try {
			StringBuilder query = new StringBuilder("select nvl(f.facility_name,f.facility_id) facility_name, nvl(caug.process_desc,' ') process_desc,");
			query.append("decode(caug.work_area,null,' ','All','All Work Areas',nvl(fla.application_desc,fla.application)) application_desc");
			query.append(" from catalog_add_user_group caug, facility_view f, fac_loc_app fla");
			query.append(" where caug.company_id = f.company_id and caug.facility_id = f.facility_id and caug.facility_id = fla.facility_id(+) and caug.work_area = fla.application(+)");
			query.append(" and caug.request_id = ").append(requestId).append(" order by f.facility_name,fla.application_desc");
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			String lastFac = "";
			while (iter.hasNext()) {
				CatalogAddApprovalBean caaBean = (CatalogAddApprovalBean)iter.next();
				String tmpFac = caaBean.getFacilityName();
				if (tmpFac.equalsIgnoreCase(lastFac)) {
					result.append("\n").append(library.getString("label.workarea")).append("\t\t\t: ").append(caaBean.getApplicationDesc());
					result.append("\n").append(library.getString("label.useandprocessdesc")).append("\t: ").append(caaBean.getProcessDesc());
				}else {
					if (result.length() > 0) {
						result.append("\n").append(library.getString("label.facility")).append("\t\t\t\t: ").append(tmpFac);
						result.append("\n").append(library.getString("label.workarea")).append("\t\t\t: ").append(caaBean.getApplicationDesc());
						result.append("\n").append(library.getString("label.useandprocessdesc")).append("\t: ").append(caaBean.getProcessDesc());
					}else {
						result.append(library.getString("label.facility")).append("\t\t\t: ").append(tmpFac);
						result.append("\n").append(library.getString("label.workarea")).append("\t\t\t: ").append(caaBean.getApplicationDesc());
						result.append("\n").append(library.getString("label.useandprocessdesc")).append("\t\t: ").append(caaBean.getProcessDesc());
					}
				}
				lastFac = tmpFac;
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Get facility work areas for request (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Get facility work areas for request failed");
		}
		return result.toString();
	} //end of method

	private String getMSDSLink(BigDecimal requestId) {
		StringBuilder result = new StringBuilder("");
		try {
			StringBuilder query = new StringBuilder("select carn.eng_eval_facility_id,fx_feature_released('NewMsdsViewer','ALL',carn.company_id) new_msds_viewer,p.material_id");
			query.append(" from catalog_add_request_new carn, catalog_add_item cai, part p");
			query.append(" where cai.item_id = p.item_id and carn.company_id = cai.company_id and carn.request_id = cai.request_id and carn.request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddApprovalBean caaBean = (CatalogAddApprovalBean)iter.next();
				if (result.length() > 0) {
					if ("Y".equals(caaBean.getNewMsdsViewer())) {
						result.append("\n").append(URL).append("/viewmsds.do?act=msds&id=").append(caaBean.getMaterialId());
						result.append("&facility=").append(caaBean.getEngEvalFacilityId());
					}else {
						result.append("\n").append(URL).append("/ViewMsds?act=msds&id=").append(caaBean.getMaterialId());
					}
				}else {
					if ("Y".equals(caaBean.getNewMsdsViewer())) {
						result.append(URL).append("/viewmsds.do?act=msds&id=").append(caaBean.getMaterialId());
						result.append("&facility=").append(caaBean.getEngEvalFacilityId());
					}else {
						result.append(URL).append("/ViewMsds?act=msds&id=").append(caaBean.getMaterialId());
					}
				}
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Getting msds link for request (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Getting msds link failed");
		}
		return result.toString();
	} //end of method

	public void sendRequestorMsdsEmail(BigDecimal requestId) {
		try {
			if (!isItemVerified(requestId)) {
				Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(requestId);
				CatAddHeaderViewBean bean = headerColl.get(0);
				StringBuilder esub = new StringBuilder("");
				if (!"TCMPROD".equalsIgnoreCase(bean.getDatabaseName())) {
					esub.append("(").append(bean.getDatabaseName()).append(") ");
				}
				esub.append(library.getString("label.sendmsdsto"));

				StringBuilder emsg = new StringBuilder(library.getString("label.faxnewMSDSto")).append("\n");
				emsg.append(library.getString("label.usealternatephone")).append("\n");
				emsg.append(library.getString("label.emailmsdsto")).append("\n");
				emsg.append(library.getString("label.enteronfaxcoverpageoremailheader")).append("\n");
				emsg.append(library.getString("label.attn")).append(": ").append(library.getString("label.requestid")).append(" ").append(bean.getRequestId());

				String tmpEmailAddress = bean.getRequestorEmail();
				if (StringHandler.isBlankString(tmpEmailAddress)) {
					tmpEmailAddress = "deverror@tcmis.com";
				}
				MailProcess.sendEmail(tmpEmailAddress,null,"tcmis@haastcm.com",esub.toString(),emsg.toString());
			}
		}catch (Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Send requestor MSDS email failed (request ID: "+requestId+")","Unable to send email to requestor");
		}
	}

	public void sendUserConfirmedEmail(BigDecimal requestId) {
		try {
			boolean rejected = false;
			StringBuilder query = new StringBuilder("select carn.requestor,carn.cat_part_no,cai.item_id,carn.engineering_evaluation,carn.request_status,");
			query.append("caa.approval_role,caa.status,caa.approval_date,fx_personnel_id_to_name(caa.chemical_approver) approver_name,caa.reason,");
			query.append("nvl(part_description,fx_material_desc(cai.item_id)) part_desc,fx_personnel_id_to_name(carn.requestor) requestor_name,");
			query.append("fx_personnel_id_to_email(carn.requestor) email,cai.line_item,carn.requestor_message,fx_get_database_name database_name,");
			query.append("cai.manufacturer,cai.customer_mixture_number,cai.customer_msds_number,cai.material_desc,");
			query.append("cai.part_id,fx_feature_released('HmrbTab',carn.eng_eval_facility_id,carn.company_id) has_hmrb");
			query.append(",cai.line_status,cai.rejected_date,fx_personnel_id_to_name(cai.rejected_by) rejected_by,cai.rejected_comment");
			query.append(",pkg_catalog_planned_add.fx_app_use_groups_descs_list(carn.company_id,carn.request_id) application_use_group_string");
			query.append(",fx_feature_released('IncludeInventoryDetail',carn.eng_eval_facility_id,carn.company_id) include_inventory_detail, cai.starting_view cai_starting_view");
			query.append(",carn.company_id,carn.catalog_company_id,carn.catalog_id,carn.part_group_no,cai.mfg_trade_name,cai.replaces_msds,carn.approval_path");
			query.append(",carn.incoming_testing");
			query.append(" from catalog_add_request_new carn, catalog_add_item cai, catalog_add_approval caa, vv_chemical_approval_role car");
			query.append(" where carn.company_id = caa.company_id and carn.request_id = caa.request_id and carn.request_id = ").append(requestId);
			query.append(" and carn.company_id = cai.company_id and carn.request_id = cai.request_id");
			query.append(" and caa.company_id = car.company_id and caa.facility_id = car.facility_id");
			query.append(" and caa.catalog_company_id = car.catalog_company_id and caa.catalog_id = car.catalog_id");
			query.append(" and caa.approval_role = car.approval_role and car.include_in_requestor_email = 'Y'");
			query.append(" order by caa.approval_role,cai.line_item,cai.part_id");
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			StringBuilder esub = new StringBuilder("");
			StringBuilder emsg = new StringBuilder("");
			StringBuilder lineItemMsg = new StringBuilder("");
			StringBuilder approvalRoleMsg = new StringBuilder("");
			StringBuilder incomingTestingInstructions = new StringBuilder("");
			boolean firstTime = true;
			String facWaInfo = getFacWorkAreaForRequest(requestId);
			Iterator iter = dataColl.iterator();
			String requestorEmail = "";
			boolean catAddCreatedBySystem = false;
			Vector lineDataKey = new Vector(dataColl.size());
			Vector approvalRoleDataKey = new Vector(dataColl.size());
			String hasHmrb = "";
			String requestStatus = "";
			Vector materialDataKey = new Vector(dataColl.size());
			while (iter.hasNext()){
				CatalogAddApprovalBean caaBean = (CatalogAddApprovalBean)iter.next();
				BigDecimal currentLine = caaBean.getLineItem();
				String currentApprovalRole = caaBean.getApprovalRole();
				String currentMaterialDataKey = caaBean.getLineItem().toString()+caaBean.getPartId().toString();

				//header info
				if (firstTime) {
					hasHmrb = caaBean.getHasHmrb();
					//don't need to notify if catalog add is created by system
					//leave requestor email address empty
					if (caaBean.getRequestor().equals(new BigDecimal(-1)))
						catAddCreatedBySystem = true;
					else
						requestorEmail = caaBean.getEmail();
					String requestType = StringHandler.emptyIfNull(caaBean.getEngineeringEvaluation());
					if (caaBean.getRequestStatus() != null) {
						requestStatus = caaBean.getRequestStatus().toString();
					}
					if (!"TCMPROD".equalsIgnoreCase(caaBean.getDatabaseName())) {
						esub.append("(").append(caaBean.getDatabaseName()).append(") ");
					}
					if ("7".equalsIgnoreCase(requestStatus) || "18".equalsIgnoreCase(requestStatus)) {
						esub.append(library.format( "label.yourrequestrejected",requestId.toString()));
						emsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.rejected")).append("\n");
						rejected = true;
					}else if ("16".equals(requestStatus)) {
						esub.append(library.format( "label.yourrequestresubmitted",requestId.toString()));
						emsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.resubmitted")).append("\n");
						rejected = true;
					}else {
						if ("Y".equalsIgnoreCase(requestType)) {
							esub.append(library.format( "label.yourengevalapproved",requestId.toString()));
							emsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.approved")).append("\n");
							emsg.append(library.getString("label.inbuyerqueque")).append("\n");
						}else {
							esub.append(library.format( "label.yourrequestapproved",requestId.toString()));
							emsg.append(library.getString("label.status")).append("\t\t\t: ").append(library.getString("label.approved")).append("\n");
							if (!"MSDS".equals(caaBean.getApprovalPath()))
								emsg.append(library.getString("label.youarenowapproved")).append("\n");
						}
					}
					emsg.append(library.getString("label.requestid")).append("\t\t\t: ").append(requestId).append("\n");
					emsg.append(library.getString("label.partnumber")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCatPartNo())).append("\n");
					emsg.append(library.getString("label.partdescription")).append("\t\t: ").append(StringHandler.emptyIfNull(caaBean.getPartDesc())).append("\n");
					emsg.append(library.getString("label.requestor")).append("\t\t\t: ").append(caaBean.getRequestorName()).append("\n");
					if (facWaInfo.length() > 0) {
						emsg.append(facWaInfo).append("\n\n");
					}else {
						emsg.append("\n");
					}
					if(!StringHandler.isBlankString(caaBean.getRequestorMessage())) {
						emsg.append("\n").append(library.getString("label.notes"));
						emsg.append("\n--------------------------\n");
						String[] tmpArr = caaBean.getRequestorMessage().split(";");
						for (int i = 0; i < tmpArr.length; i++) {
							emsg.append((i+1)+") ").append(tmpArr[i]).append("\n");
						}
					}
					//incoming testing
					if ("Y".equalsIgnoreCase(caaBean.getIncomingTesting()))
						incomingTestingInstructions.append(library.getString("label.incomingtestinginstruction"));

					firstTime = false;
				} //end of first time

				StringBuilder inventoryDetails = new StringBuilder("");
				if (!lineDataKey.contains(currentLine)) {
					if (lineDataKey.size() > 0) {
						lineItemMsg.append("\n");
					}
					lineItemMsg.append("\n--------------------------\n");
					lineItemMsg.append(library.getString("label.line")).append("\t\t\t\t: ").append(caaBean.getLineItem()).append("\n");
					String tmpLineStatus = caaBean.getLineStatus();
					if ("Rejected".equals(tmpLineStatus)) {
						tmpLineStatus += " ("+caaBean.getRejectedBy()+": "+DateHandler.formatDate(caaBean.getRejectedDate(),"dd-MMM-yyyy",this.getLocaleObject())+")";
						lineItemMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(tmpLineStatus).append("\n");
					}
					lineItemMsg.append(library.getString("label.itemid")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getItemId())).append("\n");
					if ("Y".equals(hasHmrb)) {
						lineItemMsg.append(library.getString("label.kitmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCustomerMixtureNumber())).append("\n");
					}
					//additional message for fadeout here
					//if request_status is 9 and line_status is not Rejected and line_starting_view is Fadeout and feature release is includeInventoryDetail
					if ("9".equalsIgnoreCase(requestStatus) && !"Rejected".equals(tmpLineStatus) && (new BigDecimal(5)).equals(caaBean.getCaiStartingView()) &&
							"Y".equals(caaBean.getIncludeInventoryDetail())) {
						inventoryDetails.append(getInventoryDetail(caaBean));
					}
					lineDataKey.add(currentLine);
				}
				if (!materialDataKey.contains(currentMaterialDataKey)) {
					lineItemMsg.append(library.getString("label.msds")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCustomerMsdsNumber())).append("\n");
					lineItemMsg.append(library.getString("label.replacesmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getReplacesMsds())).append("\n");
					lineItemMsg.append(library.getString("label.description")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getMaterialDesc())).append("\n");
					lineItemMsg.append(library.getString("label.tradename")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getMfgTradeName())).append("\n");
					lineItemMsg.append(library.getString("label.manufacturer")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getManufacturer())).append("\n");
					materialDataKey.add(currentMaterialDataKey);
				}
				//adding inventory details
				if (inventoryDetails.length() > 0) {
					lineItemMsg.append("\n");
					lineItemMsg.append("\t").append(library.getString("label.fadeoutmessage")).append("\n");
					lineItemMsg.append("\t--------------------------\n");
					lineItemMsg.append(inventoryDetails);
					lineItemMsg.append("\t--------------------------\n");
				}

				if (!approvalRoleDataKey.contains(currentApprovalRole)) {
					approvalRoleMsg.append(library.getString("label.name")).append("\t\t:").append(caaBean.getApprovalRole()).append("\n");
					approvalRoleMsg.append(library.getString("label.status")).append("\t\t:").append(caaBean.getStatus()).append("\n");
					approvalRoleMsg.append(library.getString("label.date")).append("\t\t:").append(DateHandler.formatDate(caaBean.getApprovalDate(),"dd-MMM-yyyy",this.getLocaleObject())).append("\n");
					approvalRoleMsg.append(library.getString("label.approver")).append("\t:").append(caaBean.getApproverName()).append("\n");
					approvalRoleMsg.append(library.getString("label.notes")).append("\t\t:").append(StringHandler.emptyIfNull(caaBean.getReason())).append("\n\n");
					approvalRoleDataKey.add(currentApprovalRole);
				}
			} //end of loop

			//get approval code
			if ("Y".equals(hasHmrb)) {
				StringBuilder hmrbMsg = new StringBuilder("");
				getHmrbEmailData(hmrbMsg,requestId);
				emsg.append("\n--------------------------\n");
				emsg.append(hmrbMsg);
				emsg.append("\n");
			}
			//put it together
			emsg.append(lineItemMsg);
			emsg.append("\n\n");
			emsg.append(library.getString("label.approvalRoles")).append(":\n--------------------------\n");
			emsg.append(approvalRoleMsg);
			//add incoming testing instructions
			if (incomingTestingInstructions.length() > 0) {
				emsg.append("\n\n");
				emsg.append(incomingTestingInstructions);
			}
			//add link
			if (!StringHandler.isBlankString(URL)) {
				emsg.append("\n\n");
				emsg.append(library.getString("label.linktologin"));
				emsg.append("\n");;
				emsg.append(URL).append("/home.do\n");
				//for msds links
				String msdsLink = getMSDSLink(requestId);
				if (msdsLink.length() > 0) {
					emsg.append("\n\n").append(library.getString("label.msdslinks")).append(":\n--------------------------\n");
					emsg.append("\n").append(msdsLink);
				}
			}
			//preparing email list
			Vector emailList = new Vector();
			if (!StringHandler.isBlankString(requestorEmail)) {
				emailList.add(requestorEmail);
			}

			//send email to addition people
			query = new StringBuilder("select fx_personnel_id_to_email(personnel_id) email from user_group_member where user_group_id = 'CatAddNotification' and");
			query.append(" ((facility_id = 'All' and company_id = (select company_id from catalog_add_request_new where request_id = '").append(requestId+"')) or (company_id,facility_id) = (select company_id,eng_eval_facility_id from catalog_add_request_new where request_id = ").append(requestId+"))");
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection tmpDataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			iter = tmpDataColl.iterator();
			while (iter.hasNext()) {
				CatalogAddApprovalBean cBean = (CatalogAddApprovalBean)iter.next();
				if (StringHandler.isBlankString(cBean.getEmail())) continue;
				//don't send the users email twice
				if (!emailList.contains(cBean.getEmail())) {
					emailList.add(cBean.getEmail());
				}
			}
			if (emailList.size() == 0) {
				if (!catAddCreatedBySystem) {
					StringBuilder tmp = new StringBuilder("User email address is empty (request ID: ").append(requestId).append(")");
					MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", tmp.toString(), "User without email address");
				}
			}else {
				String[] toAddress = new String[emailList.size()];
				for (int j = 0; j < emailList.size(); j++) {
					toAddress[j] = emailList.elementAt(j).toString();
				}
				MailProcess.sendEmail(toAddress,null,esub.toString(),emsg.toString(),false);
			}

			//notify hub if request is MM
			if (!rejected) {
				sendHubEmail(requestId);
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Send confirmation email failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send email to user");
		}
	}

	private StringBuilder getInventoryDetail(CatalogAddApprovalBean caaBean) {
		StringBuilder result = new StringBuilder("");
		try {
			//first need to get On Hand Inventory
			StringBuilder query = new StringBuilder("select igd.inventory_group_name inventory_group,decode(rt.lot_status, r.lot_status, decode(r.notes, null, rt.receipt_id, r.receipt_id), r.receipt_id) receipt_id,");
			query.append("r.lot_status,round(sum(r.quantity_received-NVL(a.quantity, 0) -NVL(r.total_quantity_issued, 0))) quantity,");
			query.append("r.mfg_lot,r.quality_tracking_number,r.expire_date");
			query.append(" from inventory_group_definition igd,receipt rt,receipt r,");
			query.append("(SELECT x.doc_num,SUM(x.doc_quantity) quantity FROM mr_allocation x");
			query.append(" WHERE x.doc_type = 'OV' AND x.pr_number IS NOT NULL GROUP BY x.doc_num) a");
			query.append(" where r.inventory_group = igd.inventory_group and r.return_receipt_id = rt.receipt_id (+) and");
			query.append(" r.receipt_id=a.doc_num(+) AND r.qc_date IS NOT NULL AND r.on_hand = 'Y' AND");
			query.append(" r.quantity_received - NVL(a.quantity,0) - NVL(r.total_quantity_issued, 0) > 0 AND");
			query.append(" r.lot_status IN (SELECT LOT_STATUS FROM vv_lot_status WHERE ALLOCATION_ORDER> 0 ) AND");
			query.append(" r.item_id = ").append(caaBean.getItemId()).append(" AND");
			query.append(" r.inventory_group in (select inventory_group from catalog_part_inventory cpi");
			query.append(" where cpi.company_id = '").append(caaBean.getCompanyId()).append("' and cpi.catalog_company_id = '").append(caaBean.getCatalogCompanyId()).append("' and");
			query.append(" cpi.catalog_id = '").append(caaBean.getCatalogId()).append("' and cat_part_no = '").append(caaBean.getCatPartNo()).append("'");
			query.append(" and part_group_no = ").append(caaBean.getPartGroupNo()).append(" and status = 'ACTIVE')");
			query.append(" group by igd.inventory_group_name,");
			query.append("decode(rt.lot_status, r.lot_status, decode(r.notes, null, rt.receipt_id, r.receipt_id), r.receipt_id),");
			query.append("r.lot_status,r.mfg_lot,r.quality_tracking_number,r.expire_date");
			genericSqlFactory.setBeanObject(new InventoryDetailOnHandMaterialBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			boolean firstTime = true;
			while (iter.hasNext()) {
				InventoryDetailOnHandMaterialBean bean = (InventoryDetailOnHandMaterialBean)iter.next();
				if (firstTime) {
					result.append("\t\t").append(library.getString("inventorydetail.label.onhandmaterial")).append("\n");
					firstTime = false;
				}else {
					result.append("\n");
				}
				result.append(library.getString("label.receipt")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(bean.getReceiptId())).append("\n");
				result.append(library.getString("label.status")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(bean.getLotStatus())).append("\n");
				result.append(library.getString("label.inventorygroup")).append("\t\t: ").append(StringHandler.emptyIfNull(bean.getInventoryGroup())).append("\n");
				result.append(library.getString("label.quantity")).append("\t\t\t: ").append(StringHandler.emptyIfNull(bean.getQuantity())).append("\n");
				result.append(library.getString("label.lot")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(bean.getMfgLot())).append("\n");
				result.append(library.getString("label.expiredate")).append("\t\t\t: ").append(DateHandler.formatDate(bean.getExpireDate(),"dd-MMM-yyyy",this.getLocaleObject())).append("\n");
			}
			//in Supply chain
			query = new StringBuilder("SELECT 'On Order' status,igd.inventory_group_name inventory_group,");
			query.append("round((pl.quantity-NVL(a.quantity,0) -NVL(pl.total_quantity_received,0))/nvl(pl.purchasing_units_per_item, 1), 2) quantity,");
			query.append("'PO='||pl.radian_po  REFERENCE FROM inventory_group_definition igd,po p,po_line pl,");
			query.append("(SELECT x.doc_num ,x.doc_line,  SUM(x.doc_quantity) quantity FROM mr_allocation x");
			query.append(" WHERE x.doc_type = 'PO' AND x.pr_number IS NOT NULL GROUP BY x.doc_num,x.doc_line ) a");
			query.append(" WHERE p.inventory_group = igd.inventory_group and");
			query.append(" p.radian_po=pl.radian_po AND pl.radian_po=a.doc_num(+) AND");
			query.append(" pl.po_line=a.doc_line(+) AND pl.archived = 'N' AND pl.open_po = 'Y' AND");
			query.append(" pl.quantity-NVL(a.quantity,0)-NVL(pl.total_quantity_received,0)  >0 AND");
			query.append(" pl.item_id = ").append(caaBean.getItemId()).append(" AND");
			query.append(" p.inventory_group in (select inventory_group from catalog_part_inventory cpi");
			query.append(" where cpi.company_id = '").append(caaBean.getCompanyId()).append("' and cpi.catalog_company_id = '").append(caaBean.getCatalogCompanyId()).append("' and");
			query.append(" cpi.catalog_id = '").append(caaBean.getCatalogId()).append("' and cat_part_no = '").append(caaBean.getCatPartNo()).append("'");
			query.append(" and part_group_no = ").append(caaBean.getPartGroupNo()).append(" and status = 'ACTIVE')");
			query.append(" UNION ALL");
			query.append(" SELECT 'In Purchasing' status,igd.inventory_group_name inventory_group,");
			query.append("b.ORDER_QUANTITY-NVL(a.quantity,0)  quantity,'BO='||b.pr_number  REFERENCE");
			query.append(" FROM inventory_group_definition igd,buy_order b,");
			query.append("(SELECT x.doc_num ,  SUM(x.doc_quantity) quantity FROM mr_allocation x");
			query.append(" WHERE x.doc_type='PR' AND x.pr_number IS NOT NULL GROUP BY x.doc_num) a");
			query.append(" WHERE b.inventory_group = igd.inventory_group and b.pr_number=a.doc_num(+) AND");
			query.append(" b.po_in_jde='n' AND b.status<> 'Closed' AND b.ORDER_QUANTITY-NVL(a.quantity,0) > 0 AND");
			query.append(" b.item_id = ").append(caaBean.getItemId()).append(" AND");
			query.append(" b.inventory_group in (select inventory_group from catalog_part_inventory cpi");
			query.append(" where cpi.company_id = '").append(caaBean.getCompanyId()).append("' and cpi.catalog_company_id = '").append(caaBean.getCatalogCompanyId()).append("' and");
			query.append(" cpi.catalog_id = '").append(caaBean.getCatalogId()).append("' and cat_part_no = '").append(caaBean.getCatPartNo()).append("'");
			query.append(" and part_group_no = ").append(caaBean.getPartGroupNo()).append(" and status = 'ACTIVE')");
			query.append(" ORDER BY 1,3,2");
			genericSqlFactory.setBeanObject(new InventoryDetailInSupplyChainBean());
			iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			firstTime = true;
			while (iter.hasNext()) {
				InventoryDetailInSupplyChainBean bean = (InventoryDetailInSupplyChainBean)iter.next();
				if (firstTime) {
					result.append("\n");
					result.append("\t\t").append(library.getString("inventorydetail.label.insupplychain")).append("\n");
					firstTime = false;
				}else {
					result.append("\n");
				}
				result.append(library.getString("label.status")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(bean.getStatus())).append("\n");
				result.append(library.getString("label.inventorygroup")).append("\t\t\t: ").append(StringHandler.emptyIfNull(bean.getInventoryGroup())).append("\n");
				result.append(library.getString("label.quantity")).append("\t\t\t: ").append(StringHandler.emptyIfNull(bean.getQuantity())).append("\n");
				result.append(library.getString("label.ref")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(bean.getReference())).append("\n");
			}
		}catch (Exception e) {
			log.error(e);
		}
		return result;
	} //end of method

	public void sendHubEmail(BigDecimal requestId) {
		try {
			String query = "select distinct carn.cat_part_no,cai.item_id,fx_personnel_id_to_email(ugm.personnel_id) vendor_contact_email,p.last_name||', '||p.first_name requestor_name,"+
					"p.email requestor_email,p.phone requestor_phone,f.facility_name eng_eval_facility_id, "+
					"fla.application_desc eng_eval_work_area,caug.ESTIMATED_ANNUAL_USAGE init_90_day,fx_get_database_name database_name "+
					"from catalog_add_request_new carn,catalog_add_item cai, catalog_add_user_group caug,user_group_member ugm,";
			if ("tcm_ops".equalsIgnoreCase(this.getClient()))
				query += "customer.facility f";
			else
				query += "facility f";
			query +=     ",personnel p,fac_loc_app fla "+
					"where carn.eng_eval_facility_id = f.facility_id and p.personnel_id = carn.requestor and "+
					"f.preferred_warehouse = ugm.facility_id and ugm.user_group_id in ('CSR','HubManager') and ugm.company_id = 'Radian' and "+
					"carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.part_id = 1 "+
					"and carn.company_id = caug.company_id and carn.request_id = caug.request_id and caug.company_id = fla.company_id "+
					"and caug.facility_id = fla.facility_id and caug.work_area = fla.application and "+
					"carn.stocked = 'MM' and carn.starting_view < 3 and carn.request_id = "+requestId+
					" order by item_id,application_desc";
			genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			StringBuilder esub = new StringBuilder("");
			StringBuilder emsg = new StringBuilder("");
			BigDecimal lastItemId = new BigDecimal(0);
			String lastWorkArea = "";
			boolean firstTime = true;
			boolean hasData = false;
			Vector hubPersonnel = new Vector();
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()){
				CatAddHeaderViewBean bean = (CatAddHeaderViewBean)iter.next();
				//header info
				if (firstTime) {
					if (!"TCMPROD".equalsIgnoreCase(bean.getDatabaseName())) {
						esub.append("(").append(bean.getDatabaseName()).append(") ");
					}
					esub.append(library.getString("label.newmmrequest")).append(" ").append(requestId).append("\n");
					emsg.append(library.getString("label.requestor")).append(": ").append(bean.getRequestorName()).append("\n");
					emsg.append(library.getString("label.phone")).append(": ").append(StringHandler.emptyIfNull(bean.getRequestorPhone())).append("\n");
					emsg.append(library.getString("label.email")).append(": ").append(bean.getRequestorEmail()).append("\n");
					emsg.append(library.getString("label.facility")).append(": ").append(bean.getEngEvalFacilityId()).append("\n");
					emsg.append(library.getString("label.requestid")).append(": ").append(requestId).append("\n");
					emsg.append(library.getString("label.partnumber")).append(": ").append(bean.getCatPartNo()).append("\n");
					firstTime = false;
				}
				if (!lastItemId.equals(bean.getItemId())) {
					emsg.append(library.getString("label.item")).append(": ").append(bean.getItemId()).append("\n");
				}
				lastItemId = bean.getItemId();

				if (!lastWorkArea.equals(bean.getEngEvalWorkArea())) {
					emsg.append(library.getString("label.workarea")).append(": ").append(bean.getEngEvalWorkArea()).append("\n");
					emsg.append(library.getString("label.estimatedquarterlyusage")).append(": ").append(StringHandler.emptyIfZero(bean.getInit90Day())).append("\n");
				}
				lastWorkArea = bean.getEngEvalWorkArea();

				//make sure that a user will only be notify once if he/she is listed for both CSR and HubManager
				String emailAddress = bean.getVendorContactEmail();
				if (!hubPersonnel.contains(emailAddress)) {
					hubPersonnel.addElement(emailAddress);
				}
				hasData = true;
			}
			//now send out email
			if (hasData) {
				if (hubPersonnel.size() == 0) {
					hubPersonnel.addElement("deverror@tcmis.com");
					if (esub.length() == 0) {
						esub.append(library.getString("label.nocsrorhubmanagerassigned")).append(requestId);
					}else {
						esub.append("(").append(library.getString("label.nocsrorhubmanagerassigned")).append(")");
					}
					if (emsg.length() == 0) {
						emsg.append(library.getString("label.nocsrorhubmanagerassigned")).append(requestId);
					}
				}
				String[] hubEmailAddresses = new String[hubPersonnel.size()];
				for (int i = 0; i < hubPersonnel.size(); i++) {
					hubEmailAddresses[i] = (String)hubPersonnel.elementAt(i);
				}
				MailProcess.sendEmail(hubEmailAddresses,null,esub.toString(),emsg.toString(),false);
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Send hub email failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send email to hub personnel");
		}
	}

	public String getUserUploadedMsdsForRequest(BigDecimal requestId) {
		StringBuffer result = new StringBuffer("");
		if (!StringHandler.isBlankString(URL)) {
			ResourceLibrary resource = new ResourceLibrary("uploadFile");
			String catalogAddMsdsPath = resource.getString("file.path") + "catalogAddMsds/";
			String tmpUrl = URL.substring(0, URL.indexOf("tcmIS")) + "catalogAddMsds/";
			File tmpFile = new File(catalogAddMsdsPath);
			String[] fileNameList = tmpFile.list();
			String tmpFileName = "";
			if (fileNameList != null) {
				for (int j = 0; j < fileNameList.length; j++) {
					tmpFileName = fileNameList[j];
					if (tmpFileName.startsWith(requestId.toString())) {
						result.append(tmpUrl).append(tmpFileName).append("\n");
					}
				}
			}

			String catalogAddKitMsdsPath = resource.getString("file.path") + "catalogAddKitMsds/";
			tmpUrl = URL.substring(0, URL.indexOf("tcmIS")) + "catalogAddKitMsds/";
			tmpFile = new File(catalogAddKitMsdsPath);
			fileNameList = tmpFile.list();
			tmpFileName = "";
			if (fileNameList != null) {
				for (int j = 0; j < fileNameList.length; j++) {
					tmpFileName = fileNameList[j];
					if (tmpFileName.startsWith(requestId.toString())) {
						result.append(tmpUrl).append(tmpFileName).append("\n");
					}
				}
			}
		}
		return result.toString();
	}

	public String getUserUploadedSpecForRequest(BigDecimal requestId) {
		StringBuffer result = new StringBuffer("");
		ResourceLibrary resource = new ResourceLibrary("uploadFile");
		String catalogAddSpecPath = resource.getString("file.path")+"catalogAddSpec/";
		String tmpUrl = URL.substring(0,URL.indexOf("tcmIS"))+"catalogAddSpec/";
		File tmpFile = new File(catalogAddSpecPath);
		String[] fileNameList = tmpFile.list();
		String tmpFileName = "";
		if (fileNameList != null) {
			for (int j = 0; j < fileNameList.length; j++) {
				tmpFileName = fileNameList[j];
				if (tmpFileName.startsWith(requestId.toString())) {
					result.append(tmpUrl).append(tmpFileName).append("\n");
				}
			}
		}
		return result.toString();
	}

	private CatalogAddEmailBean createEmailMessage(BigDecimal requestId, StringBuilder query) {
		CatalogAddEmailBean resultBean = new CatalogAddEmailBean();
		try {
			query.append(" order by cai.line_item, cai.part_id,capu.material_subcategory_id");
			Collection dataC = genericSqlFactory.selectQuery(query.toString(),connection);
			Vector toAddress = new Vector(dataC.size());
			Iterator iter = dataC.iterator();
			//String tmpDatabaseName = "";
			StringBuilder msg = new StringBuilder("");
			StringBuilder materialMsg = new StringBuilder("");
			String hasHmrb = "";
			Vector dataKey = new Vector(dataC.size());
			Vector materialSubcategoryKey = new Vector(dataC.size());
			boolean firstTime = true;
			while(iter.hasNext()) {
				CatalogAddApprovalBean tmpB = (CatalogAddApprovalBean)iter.next();
				String currentDataKey = tmpB.getLineItem()+""+tmpB.getPartId();
				String currentMaterialSubcategory = StringHandler.emptyIfNull(tmpB.getMaterialSubcategoryName());

				if (firstTime) {
					hasHmrb = tmpB.getHasHmrb();
					msg.append(library.getString("label.requestor")).append("\t\t\t: ").append(tmpB.getRequestorName()).append("\n");
					if ("Y".equals(hasHmrb)) {
						msg.append(library.getString("label.kitmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getKitMsds())).append("\n");
						msg.append(library.getString("label.approvalcode")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getApplicationUseGroupString())).append("\n");
					}
					//tmpDatabaseName = tmpB.getDatabaseName();
					firstTime = false;
				}

				if ("Y".equals(hasHmrb)) {
					if (!materialSubcategoryKey.contains(currentMaterialSubcategory)) {
						msg.append("\n").append(library.getString("label.materialcategory")).append("\t\t: ").append(StringHandler.emptyIfNull(tmpB.getMaterialCategoryName())).append("\n");
						msg.append(library.getString("label.materialsubcategory")).append("\t\t: ").append(StringHandler.emptyIfNull(currentMaterialSubcategory)).append("\n");
						materialSubcategoryKey.add(currentMaterialSubcategory);
					}
				}
				if (!dataKey.contains(currentDataKey)) {
					materialMsg.append(library.getString("label.msds")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getCustomerMsdsNumber())).append("\n");
					materialMsg.append(library.getString("label.description")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getMaterialDesc())).append("\n");
					materialMsg.append(library.getString("label.tradename")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getMfgTradeName())).append("\n");
					materialMsg.append(library.getString("label.manufacturer")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getManufacturer())).append("\n\n");
					dataKey.add(currentDataKey);
				}

				if (!toAddress.contains(tmpB.getEmail())) {
					toAddress.add(tmpB.getEmail());
				}
			}
			//put message together
			msg.append("\n").append(materialMsg);

			StringBuilder esub = new StringBuilder("");
			String[] toEmailAddress = new String[toAddress.size()];
			for (int j = 0; j < toAddress.size(); j++) {
				toEmailAddress[j] = (String)toAddress.elementAt(j);
			}

			resultBean.setToAddress(toEmailAddress);
			resultBean.setEmailSubject(esub);
			resultBean.setEmailMessage(msg);
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("createEmailMessage failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send notify email to other approval role\n"+query.toString());
		}
		return resultBean;
	}

	private void createEmailMessageByEmailAddress(BigDecimal requestId, StringBuilder query, String approvalRole, StringBuilder additionalMsg, Collection result) {
		try {
			query.append(" order by cai.line_item, cai.part_id,capu.material_subcategory_id");
			Collection dataC = genericSqlFactory.selectQuery(query.toString(),connection);
			Vector toAddress = new Vector(dataC.size());
			Iterator iter = dataC.iterator();
			//String tmpDatabaseName = "";
			StringBuilder msg = new StringBuilder("");
			StringBuilder materialMsg = new StringBuilder("");
			String hasHmrb = "";
			Vector dataKey = new Vector(dataC.size());
			Vector materialSubcategoryKey = new Vector(dataC.size());
			boolean firstTime = true;
			while(iter.hasNext()) {
				CatalogAddApprovalBean tmpB = (CatalogAddApprovalBean)iter.next();
				String currentDataKey = tmpB.getLineItem()+""+tmpB.getPartId();
				String currentMaterialSubcategory = StringHandler.emptyIfNull(tmpB.getMaterialSubcategoryName());

				if (firstTime) {
					hasHmrb = tmpB.getHasHmrb();
					msg.append(library.getString("label.requestor")).append("\t\t\t: ").append(tmpB.getRequestorName()).append("\n");
					if ("Y".equals(hasHmrb)) {
						msg.append(library.getString("label.kitmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getKitMsds())).append("\n");
						msg.append(library.getString("label.approvalcode")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getApplicationUseGroupString())).append("\n");
					}
					//tmpDatabaseName = tmpB.getDatabaseName();
					firstTime = false;
				}

				if ("Y".equals(hasHmrb)) {
					if (!materialSubcategoryKey.contains(currentMaterialSubcategory)) {
						msg.append("\n").append(library.getString("label.materialcategory")).append("\t\t: ").append(StringHandler.emptyIfNull(tmpB.getMaterialCategoryName())).append("\n");
						msg.append(library.getString("label.materialsubcategory")).append("\t\t: ").append(StringHandler.emptyIfNull(currentMaterialSubcategory)).append("\n");
						materialSubcategoryKey.add(currentMaterialSubcategory);
					}
				}
				if (!dataKey.contains(currentDataKey)) {
					materialMsg.append(library.getString("label.msds")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getCustomerMsdsNumber())).append("\n");
					materialMsg.append(library.getString("label.description")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getMaterialDesc())).append("\n");
					materialMsg.append(library.getString("label.tradename")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getMfgTradeName())).append("\n");
					materialMsg.append(library.getString("label.manufacturer")).append("\t\t\t: ").append(StringHandler.emptyIfNull(tmpB.getManufacturer())).append("\n\n");
					dataKey.add(currentDataKey);
				}

				if (!toAddress.contains(tmpB.getEmail())) {
					toAddress.add(tmpB.getEmail());
				}
			} //end of loop

			//put message together
			msg.append("\n").append(materialMsg);
			StringBuilder esub = new StringBuilder("");
			esub.append(library.getString("label.catalogaddrequest")).append(" (").append(requestId).append(") - ").append(approvalRole);
			for (int j = 0; j < toAddress.size(); j++) {
				CatalogAddEmailBean resultBean = new CatalogAddEmailBean();
				resultBean.setEmailSubject(esub);
				resultBean.setEmailMessage(msg);
				resultBean.setEmailAddress((String)toAddress.elementAt(j));
				resultBean.setApprovalRole(approvalRole);
				resultBean.setAdditionalMessage(additionalMsg);
				result.add(resultBean);
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("createEmailMessage failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send notify email to other approval role\n"+query.toString());
		}
	} //end of method

	private StringBuilder getApprovalEmailQuery(BigDecimal requestId) {
		StringBuilder query = new StringBuilder("select carn.request_id,fx_personnel_id_to_name(carn.requestor) requestor_name,cai.material_desc,");
		query.append("cai.manufacturer,cai.customer_mixture_number,cai.customer_msds_number,");
		query.append("fx_feature_released('HmrbTab',carn.eng_eval_facility_id,carn.company_id) has_hmrb, msc.material_subcategory_name, mc.material_category_name,");
		query.append("fx_personnel_id_to_email(ca.personnel_id) email, fx_get_database_name database_name, cai.line_item, cai.part_id");
		query.append(",pkg_catalog_planned_add.fx_app_use_groups_descs_list(carn.company_id,carn.request_id) application_use_group_string");
		query.append(",carn.starting_view,cai.mfg_trade_name");
		query.append(" from catalog_add_request_new carn, catalog_add_item cai, catalog_add_planned_use capu,");
		query.append("vv_material_subcategory msc, vv_material_category mc, chemical_approver ca");
		query.append(" where carn.company_id = ca.company_id and carn.catalog_company_id = ca.catalog_company_id");
		query.append(" and carn.catalog_id = ca.catalog_id and ca.active = 'y'");
		query.append(" and carn.company_id = cai.company_id and carn.request_id = cai.request_id and carn.company_id = capu.company_id");
		query.append(" and carn.request_id = capu.request_id and carn.company_id = msc.company_id and carn.catalog_company_id = msc.catalog_company_id");
		query.append(" and carn.catalog_id = msc.catalog_id and capu.material_subcategory_id = msc.material_subcategory_id");
		query.append(" and carn.company_id = mc.company_id and carn.catalog_company_id = mc.catalog_company_id and carn.catalog_id = mc.catalog_id");
		query.append(" and msc.material_category_id = mc.material_category_id and carn.request_id = ").append(requestId);
		return query;
	}

	public Collection getOtherApprovalRoleEmail(BigDecimal requestId) {
		Collection result = new Vector();

		try {
			Hashtable approvalRoleH = new Hashtable(otherApprovalRoleNotification.size());
			for (int i = 0; i < otherApprovalRoleNotification.size(); i++) {
				CatalogAddApprovalReviewBean bean = (CatalogAddApprovalReviewBean)otherApprovalRoleNotification.elementAt(i);
				if (!StringHandler.isBlankString(bean.getAddlNotifyApprovalRole1())) {
					if (!approvalRoleH.containsKey(bean.getAddlNotifyApprovalRole1())) {
						StringBuilder additionalMsg = new StringBuilder("");
						if (!StringHandler.isBlankString(bean.getOutputStatement()) && !"Fail".equals(bean.getOutputStatement()))
							additionalMsg.append("   - ").append(bean.getOutputStatement());
						if (!StringHandler.isBlankString(bean.getAddlNotifyApprovalMsg1())) {
							additionalMsg.append(" : ").append(bean.getAddlNotifyApprovalMsg1());
						}
						approvalRoleH.put(bean.getAddlNotifyApprovalRole1(),additionalMsg);
					}else {
						StringBuilder additionalMsg = (StringBuilder)approvalRoleH.get(bean.getAddlNotifyApprovalRole1());
						if (!StringHandler.isBlankString(bean.getOutputStatement()) && !"Fail".equals(bean.getOutputStatement()))
							additionalMsg.append("\n").append("   - ").append(bean.getOutputStatement());
						if (!StringHandler.isBlankString(bean.getAddlNotifyApprovalMsg1())) {
							additionalMsg.append(" : ").append(bean.getAddlNotifyApprovalMsg1());
						}
					}
				}
				if (!StringHandler.isBlankString(bean.getAddlNotifyApprovalRole2())) {
					if (!approvalRoleH.containsKey(bean.getAddlNotifyApprovalRole2())) {
						StringBuilder additionalMsg = new StringBuilder("");
						if (!StringHandler.isBlankString(bean.getOutputStatement()) && !"Fail".equals(bean.getOutputStatement()))
							additionalMsg.append("   - ").append(bean.getOutputStatement());
						if (!StringHandler.isBlankString(bean.getAddlNotifyApprovalMsg2())) {
							additionalMsg.append(" : ").append(bean.getAddlNotifyApprovalMsg2());
						}
						approvalRoleH.put(bean.getAddlNotifyApprovalRole2(),additionalMsg);
					}else {
						StringBuilder additionalMsg = (StringBuilder)approvalRoleH.get(bean.getAddlNotifyApprovalRole2());
						if (!StringHandler.isBlankString(bean.getOutputStatement()) && !"Fail".equals(bean.getOutputStatement()))
							additionalMsg.append("\n").append("   - ").append(bean.getOutputStatement());
						if (!StringHandler.isBlankString(bean.getAddlNotifyApprovalMsg2())) {
							additionalMsg.append(" : ").append(bean.getAddlNotifyApprovalMsg2());
						}
					}
				}
			} //end of loop
			//putting it together
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Enumeration enumr = approvalRoleH.keys();
			while (enumr.hasMoreElements()) {
				String approvalRole = (String)enumr.nextElement();
				StringBuilder query = getApprovalEmailQuery(requestId);
				query.append(" and ca.approval_role = '").append(approvalRole).append("'");
				createEmailMessageByEmailAddress(requestId,query,approvalRole,(StringBuilder)approvalRoleH.get(approvalRole),result);
			}
			//reset data
			otherApprovalRoleNotification = new Vector();
		}catch (Exception e) {
			otherApprovalRoleNotification = new Vector();
			log.error(e);
			StringBuilder tmp = new StringBuilder("getOtherApprovalRoleEmail failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send notify email to other approval role");
		}
		return result;
	} //end of method


	public void sendAdditionalNotificationWhenApproved(BigDecimal requestId, String approvalRole) {
		try {
			StringBuilder query = new StringBuilder("select vcar.approval_notification_role,vcar.approval_notification_msg,vcar.approval_notification_msg_req");
			query.append(" from vv_chemical_approval_role vcar, catalog_add_request_new carn");
			query.append(" where carn.company_id = vcar.company_id and carn.catalog_company_id = vcar.catalog_company_id and carn.catalog_id = vcar.catalog_id");
			query.append(" and carn.eng_eval_facility_id = vcar.facility_id and vcar.approval_role = '").append(approvalRole).append("' and carn.request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection dataC = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = dataC.iterator();
			while(iter.hasNext()) {
				CatalogAddApprovalBean bean = (CatalogAddApprovalBean)iter.next();
				//update message to requestor
				if (!StringHandler.isBlankString(bean.getApprovalNotificationMsgReq())) {
					updateMessageToRequestor(requestId,bean.getApprovalNotificationMsgReq());
				}
				//notify other approval role
				if (!StringHandler.isBlankString(bean.getApprovalNotificationRole()) &&
						!StringHandler.isBlankString(bean.getApprovalNotificationMsg())) {
					query = getApprovalEmailQuery(requestId);
					query.append(" and ca.approval_role = '").append(bean.getApprovalNotificationRole()).append("'");
					CatalogAddEmailBean emailBean = createEmailMessage(requestId,query);
					StringBuilder esub = emailBean.getEmailSubject();
					StringBuilder msg = emailBean.getEmailMessage();
					String[] toEmailAddress = emailBean.getToAddress();
					if (toEmailAddress == null || toEmailAddress.length == 0 ) {
						toEmailAddress[0] = "deverror@tcmis.com";
					}
					msg.append("\n");
					msg.append(library.getString("label.notes")).append(":\n--------------------------\n");
					esub.append(library.getString("label.catalogaddrequest")).append(" (").append(requestId).append(") - ").append(bean.getApprovalNotificationRole());
					msg.append(bean.getApprovalNotificationMsg());
					MailProcess.sendEmail(toEmailAddress,null,esub.toString(),msg.toString(),false);
				}
			}
		}catch(Exception e) {
			log.error(e);
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Additional notification failed: "+requestId+" - "+approvalRole,"See logs for more details.");
		}
	} //end of method

	public void sendApproversNotifyOnlyEmail(BigDecimal requestId) {
		StringBuilder approvalRole = new StringBuilder("");
		try {
			Hashtable additionalEmailH = new Hashtable();
			StringBuilder esub = new StringBuilder("");
			Iterator itera = getApproversNotifyOnlyEmail(requestId).iterator();
			while (itera.hasNext()) {
				CatalogAddEmailBean emailBean = (CatalogAddEmailBean)itera.next();
				if (!StringHandler.isBlankString(emailBean.getEmailAddress())) {
					approvalRole.append(emailBean.getApprovalRole()).append(";");
					if (additionalEmailH.containsKey(emailBean.getEmailAddress())) {
						Hashtable dataH = (Hashtable)additionalEmailH.get(emailBean.getEmailAddress());
						StringBuilder msg = (StringBuilder)dataH.get("EMAIL_MESSAGE");
						msg.append("\n").append(emailBean.getAdditionalMessage());
					}else {
						Hashtable dataH = new Hashtable(2);
						esub = emailBean.getEmailSubject();
						StringBuilder msg = new StringBuilder("");
						msg.append(emailBean.getEmailMessage());
						msg.append("\n");
						msg.append(library.getString("label.notifications")).append(":\n--------------------------\n");
						msg.append(emailBean.getAdditionalMessage());
						dataH.put("EMAIL_SUBJECT",esub);
						dataH.put("EMAIL_MESSAGE",msg);
						additionalEmailH.put(emailBean.getEmailAddress(),dataH);
					}
				}
			}

			//now send out email
			Enumeration addEnu = additionalEmailH.keys();
			while (addEnu.hasMoreElements()) {
				String addEmail = (String)addEnu.nextElement();
				Hashtable dataH = (Hashtable)additionalEmailH.get(addEmail);
				esub = (StringBuilder)dataH.get("EMAIL_SUBJECT");
				StringBuilder msg = (StringBuilder)dataH.get("EMAIL_MESSAGE");
				MailProcess.sendEmail(addEmail,null,"tcmis@haastcm.com",esub.toString(),msg.toString());
			}

		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Send notification email failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send notify only email to approval role: "+approvalRole);
		}
	} //end of method

	public Collection getApproversNotifyOnlyEmail(BigDecimal requestId) {
		Collection result = new Vector();
		StringBuilder approvalRole = new StringBuilder("");
		try {
			StringBuilder headerQuery = getApprovalEmailQuery(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			for (int i = 0; i < notifyOnlyApprovalRoleV.size(); i++) {
				CatalogAddEmailBean emailBean = (CatalogAddEmailBean)notifyOnlyApprovalRoleV.elementAt(i);
				approvalRole.append(emailBean.getApprovalRole()).append(";");
				StringBuilder detailQuery = new StringBuilder(" and ca.approval_role = '").append(emailBean.getApprovalRole()).append("'");
				StringBuilder query = new StringBuilder(headerQuery);
				query.append(detailQuery);
				createEmailMessageByEmailAddress(requestId,query,emailBean.getApprovalRole(),emailBean.getMessageToApprover(),result);
			}
			//reset data
			notifyOnlyApprovalRoleV = new Vector(0);
		}catch (Exception e) {
			//reset data
			notifyOnlyApprovalRoleV = new Vector(0);
			log.error(e);
			StringBuilder tmp = new StringBuilder("Get notification email failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to get notify only email to approval role: "+approvalRole);
		}
		return result;
	} //end of method

	public void sendApproversEmail(BigDecimal requestId, String msgToApprover, Collection otherApprovalRoleDataCol, Collection notificationOnlyApprovalRoleDataCol) {
		sendApproversEmail(requestId,msgToApprover,otherApprovalRoleDataCol,notificationOnlyApprovalRoleDataCol,true);
	}

	public void sendApproversEmail(BigDecimal requestId, String msgToApprover, Collection otherApprovalRoleDataCol, Collection notificationOnlyApprovalRoleDataCol, boolean useEmailNotificationFlag) {
		try {
			StringBuilder query = new StringBuilder("select distinct carn.company_id, c.catalog_desc,cai.item_id,carn.engineering_evaluation,carn.cat_part_no,");
			query.append("decode(carn.part_description,null,decode(cai.item_id,null,'',fx_item_desc(cai.item_id,'MA')),carn.part_description) part_desc,");
			query.append("fx_personnel_id_to_name(carn.requestor) requestor_name,fx_personnel_id_to_phone(carn.requestor) phone,f.facility_name,");
			query.append("ca.approval_role,fx_personnel_id_to_name(ca.personnel_id) approver_name,ca.personnel_id approver,car.approval_group,");
			query.append("fx_personnel_id_to_email(ca.personnel_id) email, car.catalog_add_process_url, cai.line_item,fx_get_database_name database_name");
			query.append(",cai.part_id,cai.manufacturer,cai.customer_mixture_number,cai.customer_msds_number,cai.material_desc,fx_feature_released('HmrbTab',carn.eng_eval_facility_id,carn.company_id) has_hmrb");
			query.append(",pkg_catalog_planned_add.fx_app_use_groups_descs_list(carn.company_id,carn.request_id) application_use_group_string");
			query.append(",carn.starting_view,car.role_function,cai.mfg_trade_name,cai.replaces_msds");
			query.append(" from catalog_add_request_new carn, catalog_add_item cai, facility_view f,");
			query.append("chemical_approver ca,vv_catalog_add_request_status ars,vv_chemical_approval_role car,catalog_company_view c");
			query.append(" where carn.company_id = cai.company_id and carn.request_id = cai.request_id and nvl(cai.line_status,'xx')  <> 'Rejected'");
			query.append(" and carn.catalog_id = car.catalog_id and carn.catalog_company_id = car.catalog_company_id and");
			query.append(" carn.company_id = car.company_id and carn.company_id = c.company_id and carn.company_id = ca.company_id and carn.catalog_id = ca.catalog_id and");
			query.append(" carn.catalog_company_id = ca.catalog_company_id and carn.company_id = f.company_id and ars.company_id = car.company_id and car.company_id = ca.company_id and");
			query.append(" carn.request_status = ars.request_status and carn.catalog_id = c.catalog_id and carn.catalog_company_id = c.catalog_company_id and carn.eng_eval_facility_id = car.facility_id and");
			query.append(" carn.approval_group_seq = car.approval_group_seq and carn.eng_eval_facility_id = ca.facility_id and carn.eng_eval_facility_id = f.facility_id and");
			query.append(" ars.approval_group = car.approval_group and car.approval_role = ca.approval_role and car.active = 'Y' and ca.active = 'y' and");
			query.append(" car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and");
			query.append(" 'Y'=decode(decode(carn.engineering_evaluation,'Y',decode(carn.starting_view,2,8888,9999),carn.starting_view)");
			query.append(",0,car.new_material,1,car.new_size,2,car.new_part,3,car.new_approval,6,car.new_msds,7,car.new_approval_code,9,car.sds_authoring,8888,car.reorder_eng_eval,9999,car.eng_eval,'Y')");
			if (useEmailNotificationFlag)
				query.append(" and car.email_notification = 'Y'");
			query.append(" and carn.request_id = ").append(requestId);
			query.append(" and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = ").append(requestId+")");
			query.append(" and car.approval_role not in (select approval_role from catalog_add_approval where request_id = ").append(requestId).append(")");
			query.append(" order by ca.approval_role,cai.line_item,cai.part_id");

			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Vector approverV = new Vector(20);
			StringBuilder esub = new StringBuilder("");
			StringBuilder emsg = new StringBuilder(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.waitingapproval")).append("\n");
			StringBuilder lineItemMsg = new StringBuilder("");
			boolean firstTime = true;
			boolean includeTcmQc = false;
			boolean includeTcmSpec = false;
			String hasHmrb = "";
			Vector lineItemDataKey = new Vector(dataColl.size());
			Vector materialDataKey = new Vector(dataColl.size());

			Hashtable approverByEmailH = new Hashtable();
			Hashtable approvalRoleApproverH = new Hashtable();
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()){
				CatalogAddApprovalBean caaBean = (CatalogAddApprovalBean)iter.next();
				String currentMaterialDataKey = caaBean.getLineItem().toString()+caaBean.getPartId().toString();
				String currentApprovalRole = caaBean.getApprovalRole();
				BigDecimal currentLineItem = caaBean.getLineItem();
				String currentApproverName = currentApprovalRole+caaBean.getApproverName();
				//header info
				if (firstTime) {
					hasHmrb = caaBean.getHasHmrb();
					if (!"TCMPROD".equalsIgnoreCase(caaBean.getDatabaseName())) {
						esub.append("(").append(caaBean.getDatabaseName()).append(") ");
					}
					String requestType = caaBean.getEngineeringEvaluation();
					if ("Y".equalsIgnoreCase(requestType)) {
						esub.append(library.format( "label.yourengevalpending",requestId.toString(),caaBean.getFacilityName()));
					}else {
						esub.append(library.format( "label.yourrequestpending",requestId.toString(),caaBean.getFacilityName()));
					}
					if (caaBean.getApprovalRole().startsWith("TCM") || caaBean.getApprovalRole().startsWith("QC")) {
						esub.append("("+caaBean.getCompanyId()).append(")");
					}
					emsg.append(library.getString("label.requestid")).append("\t\t\t: ").append(requestId).append("\n");
					emsg.append(library.getString("label.requestor")).append("\t\t\t: ").append(caaBean.getRequestorName()).append("\n");
					emsg.append(library.getString("label.phone")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getPhone())).append("\n");
					emsg.append(library.getString("label.facility")).append("\t\t\t\t: ").append(caaBean.getFacilityName()).append("\n");
					if (!new BigDecimal(6).equals(caaBean.getStartingView()) && !new BigDecimal(7).equals(caaBean.getStartingView())) {
						emsg.append(library.getString("label.workarea")).append("\t\t\t: ").append(getWorkAreaDesc(requestId)).append("\n");
					}
					emsg.append(library.getString("label.catalog")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCatalogDesc())).append("\n");
					emsg.append(library.getString("label.partnumber")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCatPartNo())).append("\n");
					emsg.append(library.getString("label.partdescription")).append("\t\t: ").append(StringHandler.emptyIfNull(caaBean.getPartDesc())).append("\n\n");
					firstTime = false;
				}
				//get all approvers email
				if (!StringHandler.isBlankString(caaBean.getEmail())) {
					if (!approverV.contains(caaBean.getEmail())) {
						approverV.addElement(caaBean.getEmail());
					}
				}

				if (!lineItemDataKey.contains(currentLineItem)) {
					if (lineItemDataKey.size() > 0) {
						lineItemMsg.append("\n");
					}
					lineItemMsg.append(library.getString("label.line")).append("\t\t\t\t: ").append(caaBean.getLineItem()).append("\n");
					lineItemMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append("Pending Approval").append("\n");
					lineItemMsg.append(library.getString("label.itemid")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getItemId())).append("\n");
					if ("Y".equals(hasHmrb)) {
						lineItemMsg.append(library.getString("label.kitmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCustomerMixtureNumber())).append("\n");
					}
					lineItemDataKey.add(currentLineItem);
				}
				if (!materialDataKey.contains(currentMaterialDataKey)) {
					lineItemMsg.append(library.getString("label.msds")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCustomerMsdsNumber())).append("\n");
					lineItemMsg.append(library.getString("label.replacesmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getReplacesMsds())).append("\n");
					lineItemMsg.append(library.getString("label.description")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getMaterialDesc())).append("\n");
					lineItemMsg.append(library.getString("label.tradename")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getMfgTradeName())).append("\n");
					lineItemMsg.append(library.getString("label.manufacturer")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getManufacturer())).append("\n");
					materialDataKey.add(currentMaterialDataKey);
				}

				//get approval message by approver/email address
				//if user have no email address then send message to deverror
				if (StringHandler.isBlankString(caaBean.getEmail())) {
					caaBean.setEmail("deverror@tcmis.com");
				}

				//keep track of all approver for each approval role
				if (approvalRoleApproverH.containsKey(caaBean.getApprovalRole())) {
					Vector v = (Vector)approvalRoleApproverH.get(caaBean.getApprovalRole());
					if (!v.contains(caaBean.getApproverName())) {
						v.add(caaBean.getApproverName());
					}
				}else {
					Vector v = new Vector();
					v.add(caaBean.getApproverName());
					approvalRoleApproverH.put(caaBean.getApprovalRole(),v);
				}
				//approver by email
				if (approverByEmailH.containsKey(caaBean.getEmail())) {
					Hashtable approverByRoleH = (Hashtable)approverByEmailH.get(caaBean.getEmail());
					if (!approverByRoleH.containsKey(caaBean.getApprovalRole())) {
						Hashtable innerH = new Hashtable(3);
						StringBuilder approvalRoleMsg = new StringBuilder(library.getString("label.name")).append("\t\t\t\t: ").append(caaBean.getApprovalRole()).append("\n");
						approvalRoleMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.pending")).append("\n");
						StringBuilder processUrlMsg = new StringBuilder("");
						if (!StringHandler.isBlankString(caaBean.getCatalogAddProcessUrl())) {
							processUrlMsg.append(library.getString("label.url")).append("\t\t: ").append(caaBean.getCatalogAddProcessUrl()).append(requestId).append("\n\n");
						}else {
							processUrlMsg.append("\n\n");
						}
						innerH.put("APPROVAL_ROLE_MSG",approvalRoleMsg);
						innerH.put("APPROVAL_ROLE_PROCESS_URL",processUrlMsg);
						approverByRoleH.put(caaBean.getApprovalRole(),innerH);
					}

				}else {
					Hashtable approverByRoleH = new Hashtable(23);
					Hashtable innerH = new Hashtable(3);
					StringBuilder approvalRoleMsg = new StringBuilder(library.getString("label.name")).append("\t\t\t\t: ").append(caaBean.getApprovalRole()).append("\n");
					approvalRoleMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.pending")).append("\n");
					StringBuilder processUrlMsg = new StringBuilder("");
					if (!StringHandler.isBlankString(caaBean.getCatalogAddProcessUrl())) {
						processUrlMsg.append(library.getString("label.url")).append("\t\t: ").append(caaBean.getCatalogAddProcessUrl()).append(requestId).append("\n\n");
					}else {
						processUrlMsg.append("\n\n");
					}
					innerH.put("APPROVAL_ROLE_MSG",approvalRoleMsg);
					innerH.put("APPROVAL_ROLE_PROCESS_URL",processUrlMsg);
					approverByRoleH.put(caaBean.getApprovalRole(),innerH);
					approverByEmailH.put(caaBean.getEmail(),approverByRoleH);
				}

				if ("Verify SDS Sourcing".equalsIgnoreCase(caaBean.getRoleFunction()) || "Verify SDS Sourcing Wesco".equalsIgnoreCase(caaBean.getRoleFunction()) ) {
					includeTcmQc = true;
				}else {
					includeTcmQc = false;
				}
				if ("Verify Spec".equalsIgnoreCase(caaBean.getRoleFunction())) {
					includeTcmSpec = true;
				}else {
					includeTcmSpec = false;
				}

			} //end of loop

			//get approval code
			if ("Y".equals(hasHmrb)) {
				StringBuilder hmrbMsg = new StringBuilder("");
				getHmrbEmailData(hmrbMsg,requestId);
				emsg.append("\n--------------------------\n");
				emsg.append(hmrbMsg);
				emsg.append("\n");
			}

			//put it together
			emsg.append("\n--------------------------\n");
			emsg.append(lineItemMsg);
			emsg.append("\n\n");
			emsg.append(library.getString("label.approvalRole")).append(":\n--------------------------\n");
			//building approval for by approver
			Enumeration emailEnumer = approverByEmailH.keys();
			while (emailEnumer.hasMoreElements()) {
				StringBuilder currentApprovalMsg = new StringBuilder("");
				String emailAddress = (String)emailEnumer.nextElement();
				Hashtable approvalRoleH = (Hashtable)approverByEmailH.get(emailAddress);
				Enumeration roleEnumer = approvalRoleH.keys();
				String[] msgToApproverArray = msgToApprover.split("\n\n");
				while(roleEnumer.hasMoreElements()) {
					String approvalRole = (String)roleEnumer.nextElement();
					Hashtable innerH = (Hashtable)approvalRoleH.get(approvalRole);
					currentApprovalMsg.append(innerH.get("APPROVAL_ROLE_MSG"));
					StringBuilder approverName = new StringBuilder("");
					Vector v = (Vector)approvalRoleApproverH.get(approvalRole);
					for (int j = 0; j < v.size(); j++) {
						if (j == 0) {
							approverName.append((String)v.elementAt(j));
						}else {
							approverName.append("; ").append((String)v.elementAt(j));
						}
					}
					currentApprovalMsg.append(library.getString("label.approvers")).append("\t\t\t: ").append(approverName).append("\n");
					//look at message to approver
					boolean approvalRoleHasNotes = false;
					for (int i = 0; i < msgToApproverArray.length; i++ ) {
						if (msgToApproverArray[i].startsWith(approvalRole)) {
							if (!approvalRoleHasNotes) {
								currentApprovalMsg.append(library.getString("label.notes")).append(": ").append("\n");
								approvalRoleHasNotes = true;
							}
							currentApprovalMsg.append("   - ").append(msgToApproverArray[i].substring(approvalRole.length()+2)).append("\n");
						}
					}

					//additional email to approval role
					Collection approvalRoleMsg = (Collection)approvalRoleAdditionalMessage.get(approvalRole);
					if (approvalRoleMsg != null) {
						Iterator iter34 = approvalRoleMsg.iterator();
						while(iter34.hasNext()) {
							ApprovalReviewMessageViewBean aBean = (ApprovalReviewMessageViewBean)iter34.next();
							if (!StringHandler.isBlankString(aBean.getMessageText())) {
								String[] tmpS = aBean.getMessageText().split("\n\n");
								for (int ii = 0; ii < tmpS.length; ii++) {
									if (ii == 0)
										currentApprovalMsg.append("   - ").append(tmpS[ii]).append("\n");
									else{
										if (ii == tmpS.length -1)
											currentApprovalMsg.append("     ").append(tmpS[ii]);
										else
											currentApprovalMsg.append("     ").append(tmpS[ii]).append("\n");
									}
								}
							}
						}
					}

					currentApprovalMsg.append(innerH.get("APPROVAL_ROLE_PROCESS_URL"));
				} //end of for each approval role

				//look at message to other approval roles to see if current user is also notified
				Iterator itera = otherApprovalRoleDataCol.iterator();
				Hashtable additionalApprovalMsgH = new Hashtable();
				while (itera.hasNext()) {
					CatalogAddEmailBean emailBean = (CatalogAddEmailBean)itera.next();
					if (emailAddress.equalsIgnoreCase(emailBean.getEmailAddress())) {
						if (additionalApprovalMsgH.containsKey(emailBean.getApprovalRole())) {
							StringBuilder approvalRoleMsg = (StringBuilder)additionalApprovalMsgH.get(emailBean.getApprovalRole());
							approvalRoleMsg.append("\n").append(emailBean.getAdditionalMessage());
						}else {
							StringBuilder approvalRoleMsg = new StringBuilder("\n").append(emailBean.getApprovalRole()).append("\n");
							approvalRoleMsg.append(emailBean.getAdditionalMessage());
							additionalApprovalMsgH.put(emailBean.getApprovalRole(),approvalRoleMsg);
						}
						//since we are sending out email here, set it to null for we won't send it out again later (at the end of this method)
						emailBean.setEmailAddress("");
					}
				} //end of loop
				//look at message to notification only approval roles to see if current user is also notified
				itera = notificationOnlyApprovalRoleDataCol.iterator();
				while (itera.hasNext()) {
					CatalogAddEmailBean emailBean = (CatalogAddEmailBean)itera.next();
					if (emailAddress.equalsIgnoreCase(emailBean.getEmailAddress())) {
						if (additionalApprovalMsgH.containsKey(emailBean.getApprovalRole())) {
							StringBuilder approvalRoleMsg = (StringBuilder)additionalApprovalMsgH.get(emailBean.getApprovalRole());
							approvalRoleMsg.append("\n").append(emailBean.getAdditionalMessage());
						}else {
							StringBuilder approvalRoleMsg = new StringBuilder("\n").append(emailBean.getApprovalRole()).append("\n");
							approvalRoleMsg.append("").append(emailBean.getAdditionalMessage());
							additionalApprovalMsgH.put(emailBean.getApprovalRole(),approvalRoleMsg);
						}
						//since we are sending out email here, set it to null for we won't send it out again later (at the end of this method)
						emailBean.setEmailAddress("");
					}
				} //end of loop

				//putting additional msg together
				if (additionalApprovalMsgH.size() > 0) {
					currentApprovalMsg.append("\n\n");
					currentApprovalMsg.append(library.getString("label.notifications")).append(":\n--------------------------\n");
				}
				Enumeration appEnu = additionalApprovalMsgH.keys();
				while (appEnu.hasMoreElements()) {
					currentApprovalMsg.append("\n").append(additionalApprovalMsgH.get(appEnu.nextElement()));
				}

				//additional links
				if (includeTcmSpec) {
					String tmpMsg = getUserUploadedSpecForRequest(requestId);
					if (tmpMsg.length() > 0) {
						currentApprovalMsg.append("\n\n").append(library.getString("label.userspec")).append(":\n").append(tmpMsg);
					}
				}

				//adding link for approvers
				if (!StringHandler.isBlankString(URL)) {
					currentApprovalMsg.append("\n\n").append(library.getString("label.linktologin")).append(":\n");
					currentApprovalMsg.append(URL).append("/home.do\n");
				}

				//now send out email
				MailProcess.sendEmail(emailAddress,null,"tcmis@haastcm.com",esub.toString(),emsg.toString()+currentApprovalMsg.toString());
			} //end of approvers loop

			//send uploaded msds to msds queue
			if (includeTcmQc) {
				String tmpMsg = getUserUploadedMsdsForRequest(requestId);
				if (!StringHandler.isBlankString(tmpMsg)) {
					StringBuilder msdsMsg = new StringBuilder("\n\n").append(library.getString("label.msdsuploadedbyuser")).append(":\n");
					msdsMsg.append(tmpMsg);
					MailProcess.sendEmail("msds.queue@haasgroupintl.com", null,"tcmis@haastcm.com","ATTN: REQUEST "+requestId,msdsMsg.toString());
				}
			}

			//make sure all sendOtherApproval and notification only email have been taking care of
			//if if not then we have to handle it here.
			Hashtable additionalEmailH = new Hashtable();
			//other approval roles
			Iterator itera = otherApprovalRoleDataCol.iterator();
			while (itera.hasNext()) {
				CatalogAddEmailBean emailBean = (CatalogAddEmailBean)itera.next();
				if (!StringHandler.isBlankString(emailBean.getEmailAddress())) {
					if (additionalEmailH.containsKey(emailBean.getEmailAddress())) {
						Hashtable dataH = (Hashtable)additionalEmailH.get(emailBean.getEmailAddress());
						StringBuilder msg = (StringBuilder)dataH.get("EMAIL_MESSAGE");
						msg.append("\n").append(emailBean.getAdditionalMessage());
					}else {
						Hashtable dataH = new Hashtable(2);
						esub = emailBean.getEmailSubject();
						StringBuilder msg = new StringBuilder("");
						msg.append(emailBean.getEmailMessage());
						msg.append("\n");
						msg.append(library.getString("label.notifications")).append(":\n--------------------------\n");
						msg.append(emailBean.getAdditionalMessage());
						dataH.put("EMAIL_SUBJECT",esub);
						dataH.put("EMAIL_MESSAGE",msg);
						additionalEmailH.put(emailBean.getEmailAddress(),dataH);
					}
				}
			}
			//notification only
			itera = notificationOnlyApprovalRoleDataCol.iterator();
			while (itera.hasNext()) {
				CatalogAddEmailBean emailBean = (CatalogAddEmailBean)itera.next();
				if (!StringHandler.isBlankString(emailBean.getEmailAddress())) {
					if (additionalEmailH.containsKey(emailBean.getEmailAddress())) {
						Hashtable dataH = (Hashtable)additionalEmailH.get(emailBean.getEmailAddress());
						StringBuilder msg = (StringBuilder)dataH.get("EMAIL_MESSAGE");
						msg.append("\n").append(emailBean.getAdditionalMessage());
					}else {
						Hashtable dataH = new Hashtable(2);
						esub = emailBean.getEmailSubject();
						StringBuilder msg = new StringBuilder("");
						msg.append(emailBean.getEmailMessage());
						msg.append("\n");
						msg.append(library.getString("label.notifications")).append(":\n--------------------------\n");
						msg.append(emailBean.getAdditionalMessage());
						dataH.put("EMAIL_SUBJECT",esub);
						dataH.put("EMAIL_MESSAGE",msg);
						additionalEmailH.put(emailBean.getEmailAddress(),dataH);
					}
				}
			}

			//now send out email
			Enumeration addEnu = additionalEmailH.keys();
			while (addEnu.hasMoreElements()) {
				String addEmail = (String)addEnu.nextElement();
				Hashtable dataH = (Hashtable)additionalEmailH.get(addEmail);
				esub = (StringBuilder)dataH.get("EMAIL_SUBJECT");
				StringBuilder msg = (StringBuilder)dataH.get("EMAIL_MESSAGE");
				MailProcess.sendEmail(addEmail,null,"tcmis@haastcm.com",esub.toString(),msg.toString());
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Send confirmation email failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send email to chemical approvers");
		}
	} //end of method

	public void sendAutoApprovedRejectedEmail(boolean autoApproved, BigDecimal requestId, String autoApprovalRole) {
		try {
			StringBuilder query = new StringBuilder("select distinct carn.company_id, c.catalog_desc,cai.item_id,carn.engineering_evaluation,carn.cat_part_no,");
			query.append("decode(carn.part_description,null,decode(cai.item_id,null,'',fx_item_desc(cai.item_id,'MA')),carn.part_description) part_desc,");
			query.append("fx_personnel_id_to_name(carn.requestor) requestor_name,fx_personnel_id_to_phone(carn.requestor) phone,f.facility_name,");
			query.append("ca.approval_role,fx_personnel_id_to_name(ca.personnel_id) approver_name,ca.personnel_id approver,car.approval_group,");
			query.append("fx_personnel_id_to_email(ca.personnel_id) email, car.catalog_add_process_url, cai.line_item,fx_get_database_name database_name");
			query.append(",cai.part_id,cai.manufacturer,cai.customer_mixture_number,cai.customer_msds_number,cai.material_desc,fx_feature_released('HmrbTab',carn.eng_eval_facility_id,carn.company_id) has_hmrb");
			query.append(",pkg_catalog_planned_add.fx_app_use_groups_descs_list(carn.company_id,carn.request_id) application_use_group_string");
			query.append(",carn.starting_view,car.role_function,cai.mfg_trade_name,cai.replaces_msds");
			query.append(" from catalog_add_request_new carn, catalog_add_item cai, facility_view f,");
			query.append("chemical_approver ca,vv_chemical_approval_role car,catalog_company_view c");
			query.append(" where carn.company_id = cai.company_id and carn.request_id = cai.request_id and nvl(cai.line_status,'xx')  <> 'Rejected'");
			query.append(" and carn.catalog_id = car.catalog_id and carn.catalog_company_id = car.catalog_company_id and");
			query.append(" carn.company_id = car.company_id and carn.company_id = c.company_id and carn.company_id = ca.company_id and carn.catalog_id = ca.catalog_id and");
			query.append(" carn.catalog_company_id = ca.catalog_company_id and carn.company_id = f.company_id and car.company_id = ca.company_id and");
			query.append(" carn.catalog_id = c.catalog_id and carn.catalog_company_id = c.catalog_company_id and carn.eng_eval_facility_id = car.facility_id and");
			query.append(" carn.eng_eval_facility_id = ca.facility_id and carn.eng_eval_facility_id = f.facility_id and");
			query.append(" car.approval_role = ca.approval_role and car.active = 'Y' and ca.active = 'y' and");
			query.append(" car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and");
			query.append(" car.email_notification = 'Y' and carn.request_id = ").append(requestId).append(" and car.approval_role = '").append(autoApprovalRole).append("'");
			query.append(" and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = ").append(requestId+")");
			query.append(" order by ca.approval_role,cai.line_item,cai.part_id");

			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Vector approverV = new Vector(20);
			StringBuilder esub = new StringBuilder("");
			StringBuilder emsg = new StringBuilder("");
			if (autoApproved)
				emsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.autoapproved")).append("\n");
			else
				emsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.autorejected")).append("\n");
			StringBuilder lineItemMsg = new StringBuilder("");
			boolean firstTime = true;
			String hasHmrb = "";
			Vector lineItemDataKey = new Vector(dataColl.size());
			Vector materialDataKey = new Vector(dataColl.size());

			Hashtable approverByEmailH = new Hashtable();
			Hashtable approvalRoleApproverH = new Hashtable();
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()){
				CatalogAddApprovalBean caaBean = (CatalogAddApprovalBean)iter.next();
				String currentMaterialDataKey = caaBean.getLineItem().toString()+caaBean.getPartId().toString();
				String currentApprovalRole = caaBean.getApprovalRole();
				BigDecimal currentLineItem = caaBean.getLineItem();
				String currentApproverName = currentApprovalRole+caaBean.getApproverName();
				//header info
				if (firstTime) {
					hasHmrb = caaBean.getHasHmrb();
					if (!"TCMPROD".equalsIgnoreCase(caaBean.getDatabaseName())) {
						esub.append("(").append(caaBean.getDatabaseName()).append(") ");
					}
					if (autoApproved)
						esub.append(library.format( "label.autoapprovedrole",requestId.toString(),caaBean.getFacilityName()));
					else
						esub.append(library.format( "label.autorejectedrole",requestId.toString(),caaBean.getFacilityName()));

					if (caaBean.getApprovalRole().startsWith("TCM") || caaBean.getApprovalRole().startsWith("QC")) {
						esub.append("("+caaBean.getCompanyId()).append(")");
					}
					emsg.append(library.getString("label.requestid")).append("\t\t\t: ").append(requestId).append("\n");
					emsg.append(library.getString("label.requestor")).append("\t\t\t: ").append(caaBean.getRequestorName()).append("\n");
					emsg.append(library.getString("label.phone")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getPhone())).append("\n");
					emsg.append(library.getString("label.facility")).append("\t\t\t\t: ").append(caaBean.getFacilityName()).append("\n");
					if (!new BigDecimal(6).equals(caaBean.getStartingView()) && !new BigDecimal(7).equals(caaBean.getStartingView())) {
						emsg.append(library.getString("label.workarea")).append("\t\t\t: ").append(getWorkAreaDesc(requestId)).append("\n");
					}
					emsg.append(library.getString("label.catalog")).append("\t\t\t\t: ").append(caaBean.getCatalogDesc()).append("\n");
					emsg.append(library.getString("label.partnumber")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCatPartNo())).append("\n");
					emsg.append(library.getString("label.partdescription")).append("\t\t: ").append(StringHandler.emptyIfNull(caaBean.getPartDesc())).append("\n\n");
					firstTime = false;
				}
				//get all approvers email
				if (!StringHandler.isBlankString(caaBean.getEmail())) {
					if (!approverV.contains(caaBean.getEmail())) {
						approverV.addElement(caaBean.getEmail());
					}
				}

				if (!lineItemDataKey.contains(currentLineItem)) {
					if (lineItemDataKey.size() > 0) {
						lineItemMsg.append("\n");
					}
					lineItemMsg.append(library.getString("label.line")).append("\t\t\t\t: ").append(caaBean.getLineItem()).append("\n");
					lineItemMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append("Pending Approval").append("\n");
					lineItemMsg.append(library.getString("label.itemid")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getItemId())).append("\n");
					if ("Y".equals(hasHmrb)) {
						lineItemMsg.append(library.getString("label.kitmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCustomerMixtureNumber())).append("\n");
					}
					lineItemDataKey.add(currentLineItem);
				}
				if (!materialDataKey.contains(currentMaterialDataKey)) {
					lineItemMsg.append(library.getString("label.msds")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCustomerMsdsNumber())).append("\n");
					lineItemMsg.append(library.getString("label.replacesmsds")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getReplacesMsds())).append("\n");
					lineItemMsg.append(library.getString("label.description")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getMaterialDesc())).append("\n");
					lineItemMsg.append(library.getString("label.tradename")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getMfgTradeName())).append("\n");
					lineItemMsg.append(library.getString("label.manufacturer")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getManufacturer())).append("\n");
					materialDataKey.add(currentMaterialDataKey);
				}

				//get approval message by approver/email address
				//if user have no email address then send message to deverror
				if (StringHandler.isBlankString(caaBean.getEmail())) {
					caaBean.setEmail("deverror@tcmis.com");
				}

				//keep track of all approver for each approval role
				if (approvalRoleApproverH.containsKey(caaBean.getApprovalRole())) {
					Vector v = (Vector)approvalRoleApproverH.get(caaBean.getApprovalRole());
					if (!v.contains(caaBean.getApproverName())) {
						v.add(caaBean.getApproverName());
					}
				}else {
					Vector v = new Vector();
					v.add(caaBean.getApproverName());
					approvalRoleApproverH.put(caaBean.getApprovalRole(),v);
				}
				//approver by email
				if (approverByEmailH.containsKey(caaBean.getEmail())) {
					Hashtable approverByRoleH = (Hashtable)approverByEmailH.get(caaBean.getEmail());
					if (!approverByRoleH.containsKey(caaBean.getApprovalRole())) {
						Hashtable innerH = new Hashtable(3);
						StringBuilder approvalRoleMsg = new StringBuilder(library.getString("label.name")).append("\t\t\t\t: ").append(caaBean.getApprovalRole()).append("\n");
						if (autoApproved)
							approvalRoleMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.autoapproved")).append("\n");
						else
							approvalRoleMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.autorejected")).append("\n");
						StringBuilder processUrlMsg = new StringBuilder("");
						if (!StringHandler.isBlankString(caaBean.getCatalogAddProcessUrl())) {
							processUrlMsg.append(library.getString("label.url")).append("\t\t: ").append(caaBean.getCatalogAddProcessUrl()).append(requestId).append("\n\n");
						}else {
							processUrlMsg.append("\n\n");
						}
						innerH.put("APPROVAL_ROLE_MSG",approvalRoleMsg);
						innerH.put("APPROVAL_ROLE_PROCESS_URL",processUrlMsg);
						approverByRoleH.put(caaBean.getApprovalRole(),innerH);
					}

				}else {
					Hashtable approverByRoleH = new Hashtable(23);
					Hashtable innerH = new Hashtable(3);
					StringBuilder approvalRoleMsg = new StringBuilder(library.getString("label.name")).append("\t\t\t\t: ").append(caaBean.getApprovalRole()).append("\n");
					if (autoApproved)
						approvalRoleMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.autoapproved")).append("\n");
					else
						approvalRoleMsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.autorejected")).append("\n");
					StringBuilder processUrlMsg = new StringBuilder("");
					if (!StringHandler.isBlankString(caaBean.getCatalogAddProcessUrl())) {
						processUrlMsg.append(library.getString("label.url")).append("\t\t: ").append(caaBean.getCatalogAddProcessUrl()).append(requestId).append("\n\n");
					}else {
						processUrlMsg.append("\n\n");
					}
					innerH.put("APPROVAL_ROLE_MSG",approvalRoleMsg);
					innerH.put("APPROVAL_ROLE_PROCESS_URL",processUrlMsg);
					approverByRoleH.put(caaBean.getApprovalRole(),innerH);
					approverByEmailH.put(caaBean.getEmail(),approverByRoleH);
				}

			} //end of loop

			//get approval code
			if ("Y".equals(hasHmrb)) {
				StringBuilder hmrbMsg = new StringBuilder("");
				getHmrbEmailData(hmrbMsg,requestId);
				emsg.append("\n--------------------------\n");
				emsg.append(hmrbMsg);
				emsg.append("\n");
			}

			//put it together
			emsg.append("\n--------------------------\n");
			emsg.append(lineItemMsg);
			emsg.append("\n\n");
			emsg.append(library.getString("label.approvalRole")).append(":\n--------------------------\n");
			//building approval for by approver
			Enumeration emailEnumer = approverByEmailH.keys();
			while (emailEnumer.hasMoreElements()) {
				StringBuilder currentApprovalMsg = new StringBuilder("");
				String emailAddress = (String)emailEnumer.nextElement();
				Hashtable approvalRoleH = (Hashtable)approverByEmailH.get(emailAddress);
				Enumeration roleEnumer = approvalRoleH.keys();
				while(roleEnumer.hasMoreElements()) {
					String approvalRole = (String)roleEnumer.nextElement();
					Hashtable innerH = (Hashtable)approvalRoleH.get(approvalRole);
					currentApprovalMsg.append(innerH.get("APPROVAL_ROLE_MSG"));
					StringBuilder approverName = new StringBuilder("");
					Vector v = (Vector)approvalRoleApproverH.get(approvalRole);
					for (int j = 0; j < v.size(); j++) {
						if (j == 0) {
							approverName.append((String)v.elementAt(j));
						}else {
							approverName.append("; ").append((String)v.elementAt(j));
						}
					}

					//additional email to approval role
					Collection approvalRoleMsg = (Collection)approvalRoleAdditionalMessage.get(approvalRole);
					if (approvalRoleMsg != null) {
						Iterator iter34 = approvalRoleMsg.iterator();
						while(iter34.hasNext()) {
							ApprovalReviewMessageViewBean aBean = (ApprovalReviewMessageViewBean)iter34.next();
							if (!StringHandler.isBlankString(aBean.getMessageText())) {
								String[] tmpS = aBean.getMessageText().split("\n\n");
								for (int ii = 0; ii < tmpS.length; ii++) {
									if (ii == 0)
										currentApprovalMsg.append("   - ").append(tmpS[ii]).append("\n");
									else {
										if (ii == tmpS.length -1)
											currentApprovalMsg.append("     ").append(tmpS[ii]);
										else
											currentApprovalMsg.append("     ").append(tmpS[ii]).append("\n");
									}
								}
							}
						}
					}
					currentApprovalMsg.append(innerH.get("APPROVAL_ROLE_PROCESS_URL"));
				} //end of for each approval role

				//adding link for approvers
				if (!StringHandler.isBlankString(URL)) {
					currentApprovalMsg.append("\n\n").append(library.getString("label.linktologin")).append(":\n");
					currentApprovalMsg.append(URL).append("/home.do\n");
				}
				//now send out email
				MailProcess.sendEmail(emailAddress,null,"tcmis@haastcm.com",esub.toString(),emsg.toString()+currentApprovalMsg.toString());
			} //end of approvers loop
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("Send confirmation email failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send email to chemical approvers");
		}
	} //end of method

	private void getAdditionalApprovalRoleMsg(String approvalRole){
		try {
			StringBuilder query = new StringBuilder("select * from gt_approval_review_message order by review_id,display_order");
			genericSqlFactory.setBeanObject(new ApprovalReviewMessageViewBean());
			Collection result = genericSqlFactory.selectQuery(query.toString(),connection);
			if (!result.isEmpty()){
				approvalRoleAdditionalMessage.put(approvalRole,result);
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("get approval role additional message failed (request ID: ").append(approvalRole).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"EngEvalProcess.getAdditionalApprovalRoleMsg");
		}
	} //end of method

	private void getHmrbEmailData(StringBuilder hmrbMsg, BigDecimal requestId){
		try {
			StringBuilder query = new StringBuilder("select * from approval_review_header_view where request_id = ").append(requestId);
			genericSqlFactory.setBeanObject(new CatalogAddPlannedUseBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				CatalogAddPlannedUseBean bean = (CatalogAddPlannedUseBean)iter.next();
				hmrbMsg.append(library.getString("label.approvalcode")).append("\t\t\t: ").append(StringHandler.emptyIfNull(bean.getApprovalCode())).append("\n");
				hmrbMsg.append(library.getString("label.usagesubcategory")).append("\t\t: ").append(StringHandler.emptyIfNull(bean.getUsageSubcategoryName())).append("\n");
				hmrbMsg.append(library.getString("label.materialsubcategory")).append("\t\t: ").append(StringHandler.emptyIfNull(bean.getMaterialSubcategoryName())).append("\n");
				hmrbMsg.append(library.getString("label.dischargetosinkdrain")).append("\t\t: ").append(StringHandler.emptyIfNull(bean.getDischargeToSinkDrain())).append("\n");
				hmrbMsg.append(library.getString("label.useprocesses")).append("\t\t\t: ").append(StringHandler.emptyIfNull(bean.getUseDescriptionList())).append("\n");
				hmrbMsg.append(library.getString("label.maxqtyusepershift")).append("\t: ").append(StringHandler.emptyIfNull(bean.getMaxQtyPerShift())).append(" ").append(StringHandler.emptyIfNull(bean.getMaxQtyPerShiftUnit())).append("\n");
				hmrbMsg.append(library.getString("label.building")).append("\t\t\t: ").append(StringHandler.emptyIfNull(bean.getBuildingName())).append("\n");
				hmrbMsg.append(library.getString("label.processlocation")).append("\t\t: ").append(StringHandler.emptyIfNull(bean.getProcessLocation())).append("\n");
				hmrbMsg.append(library.getString("label.boothifapplicable")).append("\t\t: ").append(StringHandler.emptyIfNull(bean.getBoothName())).append("\n");
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("get HMRB email data failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"EngEvalProcess.getHmrbEmailData");
		}
	} //end of method

	private String getWorkAreaDesc(BigDecimal requestId) {
		String result = "";
		try {
			StringBuilder query = new StringBuilder("select string_agg(application_desc) application_desc from fac_loc_app fla,catalog_add_user_group caug where caug.request_id = ").append(requestId);
			query.append(" and fla.company_id = caug.company_id and fla.facility_id = caug.facility_id and fla.application = caug.work_area");
			result = genericSqlFactory.selectSingleValue(query.toString(),connection);
			if (StringHandler.isBlankString(result)) {
				result = "All Work Areas";
			}
		}catch (Exception e) {
			log.error(e);
			StringBuilder tmp = new StringBuilder("get work areas desc failed (request ID: ").append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"EngEvalProcess.getWorkAreaDesc");
		}
		return result;
	} //end of method

	//for eng eval add item to component_inventory_group
	public void addItemToTable(BigDecimal requestId) {
		try {
			StringBuilder query = new StringBuilder("select f.inventory_group_default from facility_view f, catalog_add_request_new carn");
			query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id and carn.request_id = ").append(requestId);
			String defaultIg = genericSqlFactory.selectSingleValue(query.toString(),connection);
			//call store procedure to add item to table
			Vector cin  = new Vector();
			cin.addElement(requestId);
			cin.addElement(defaultIg);
			Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.VARCHAR));
			Collection resultData = genericSqlFactory.doProcedure(connection,"p_add_catalog_add_item", cin, cout);
			Iterator i11 = resultData.iterator();
			String val = "";
			while (i11.hasNext()) {
				val = (String) i11.next();
			}
			if (!StringHandler.isBlankString(val)) {
				StringBuilder esub = new StringBuilder("Error while calling store procedure to add item to table (request #").append(requestId).append(")");
				StringBuilder emsg = new StringBuilder("Procedure: p_add_catalog_add_item has an error.\n").append(val).append("\n");
				emsg.append("Check data then run execute customer.p_add_catalog_add_item('COMPANY',requestID,inventoryGroup)\n").append(val);
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
			}
		}catch (Exception ee) {
			log.error(ee);
			StringBuilder esub = new StringBuilder("Error while addItemToTable approving request (request #").append(requestId).append(")");
			StringBuilder emsg = new StringBuilder("Procedure: p_add_catalog_add_item throw an exception.\n");
			emsg.append("Check data then run execute customer.p_add_catalog_add_item('COMPANY',requestID,inventoryGroup)");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
		}
	} //end of method

	public String submitEval(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) throws BaseException {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			//first save data
			if (updateData(inputBean,tabBeans,personnelBean)) {
				//validate charge numbers
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),dbManager,connection,genericSqlFactory);
				MaterialRequestInputBean mrInputBean = new MaterialRequestInputBean();
				mrInputBean.setPrNumber(inputBean.getPrNumber());
				mrInputBean.setCompanyId(inputBean.getCompanyId());
				mrInputBean.setAccountSysId(inputBean.getAccountSysId());
				mrInputBean.setChargeType(inputBean.getChargeType());
				mrInputBean.setChargeNumbers(inputBean.getChargeNumbers());
				boolean noError = true;
				if(!mrProcess.validateChargeNumbers(mrInputBean)) {
					//invalid charge numbers
					result = library.getString("label.invalidchargenumbers");
					noError = false;
				}else {
					String tmpVal = mrProcess.additionalChargeNumberValidation(mrInputBean);
					if (!"OK".equals(tmpVal)) {
						result = tmpVal;
						noError = false;
					}
				}

				if (noError) {
					//do approval logic
					StringBuilder query = new StringBuilder("update catalog_add_request_new set submit_date = sysdate where request_id = ").append(inputBean.getRequestId());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					//populate catalog_add_item_locale for request
					populateCatalogAddItemLocale(inputBean.getRequestId(),inputBean.getStartingView());
					//update catalog_add_item_qc and catalog_add_item_orig
					updateCatalogAddItemQcNOrig(inputBean.getRequestId());
					//update catalog_add_spec_qc
					updateCatalogAddSpecQc(inputBean.getRequestId());

					query = new StringBuilder("update request_line_item set request_line_status = 'Pending Use Approval',category_status = 'Open'");
					query.append(" where pr_number = ").append(inputBean.getPrNumber());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					this.nextStatusAction = "Submit";
					String[] resultData = setNextStatus(inputBean.getRequestId());
					String done = resultData[EngEvalProcess.DONE_INDEX];
					String eval = resultData[EngEvalProcess.EVAL_INDEX];
					String nextGroup = resultData[EngEvalProcess.NEXT_GROUP_INDEX];
					//send email to user if the end of approval process is reach.  Otherwise, continue to the next approval group
					if ("Done".equalsIgnoreCase(done)) {
						if ("y".equalsIgnoreCase(eval)) {
							sendUserConfirmedEmail(inputBean.getRequestId());
							//for eng eval call p_cat_add_item
							addItemToTable(inputBean.getRequestId());
							//next do MR logics
							query = new StringBuilder("update purchase_request set pr_status = 'posubmit',submitted_date = sysdate,last_updated = sysdate,last_updated_by = ").append(personnelBean.getPersonnelId());
							query.append(" where pr_number = ").append(inputBean.getPrNumber());
							genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
							mrProcess.releaseMrToHaas(mrInputBean,personnelBean);
						}
						sendApproversNotifyOnlyEmail(inputBean.getRequestId());
					}else {
						query = new StringBuilder("update purchase_request set pr_status = 'compchk3',submitted_date = sysdate,last_updated = sysdate,last_updated_by = ").append(personnelBean.getPersonnelId());
						query.append(" where pr_number = ").append(inputBean.getPrNumber());
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						//sending email to the next group/group seq
						if (nextGroup.equalsIgnoreCase("New group")) {
							sendApproversEmail(inputBean.getRequestId(),resultData[EngEvalProcess.MESSAGE_TO_APPROVER_INDEX],getOtherApprovalRoleEmail(inputBean.getRequestId()),getApproversNotifyOnlyEmail(inputBean.getRequestId()));
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
		return result;
	}

	public boolean saveEval(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) throws BaseException {
		boolean result = true;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			result = updateData(inputBean,tabBeans,personnelBean);
		}catch (Exception e) {
			log.error(e);
			result = false;
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	private boolean updateData(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) {
		boolean result = true;
		try {
			if ("new".equalsIgnoreCase(inputBean.getEvalType())) {
				//first delete catalog_add_item for this request
				StringBuilder query = new StringBuilder("delete from catalog_add_item where request_id = ").append(inputBean.getRequestId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				//next put it back
				Iterator iter = tabBeans.iterator();
				int count = 1;
				while(iter.hasNext())  {
					CatalogAddItemBean catalogAddItemBean = (CatalogAddItemBean)iter.next();
					if (!"closed".equalsIgnoreCase(catalogAddItemBean.getNewTabComponent())) {
						catalogAddItemBean.setCompanyId(inputBean.getCompanyId());
						catalogAddItemInsert(inputBean.getRequestId(),catalogAddItemBean,count++);
					}
				}
				//update material_id and item from catalog_add_item_qc
				updateItemMaterialFromQc(inputBean);
			}
			//update suggested supply data
			updateCatalogAddItem(inputBean);

			//update catalog_add_request_new
			updateCatalogAddRequestNew(inputBean,personnelBean);

			//update MR data
			updateMrData(inputBean,personnelBean);

		}catch (Exception e) {
			log.error(e);
			result = false;
		}
		return result;
	}

	private void updateItemMaterialFromQc(CatAddHeaderViewBean inputBean) {
		try {
			StringBuilder query = new StringBuilder("update catalog_add_item cai set (material_id,item_id) = ");
			query.append(" (select material_id,item_id from catalog_add_item_qc qc");
			query.append(" where cai.company_id = qc.company_id and cai.request_id = qc.request_id and cai.line_item = qc.line_item and cai.part_id = qc.part_id )");
			query.append(" where cai.request_id = ").append(inputBean.getRequestId());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			log.error(e);
		}
	}

	private void updateMrData(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws Exception {
		try {
			//first update purchase_request
			StringBuilder query = new StringBuilder("update purchase_request set last_updated = sysdate, last_updated_by = ").append(personnelBean.getPersonnelId());
			if (!StringHandler.isBlankString(inputBean.getEndUser())) {
				query.append(",end_user = ").append(SqlHandler.delimitString(inputBean.getEndUser()));
			}else {
				query.append(",end_user = null");
			}
			if (!StringHandler.isBlankString(inputBean.getDepartment())) {
				query.append(",department = ").append(SqlHandler.delimitString(inputBean.getDepartment()));
			}else {
				query.append(",department = null");
			}
			query.append(" where pr_number = ").append(inputBean.getPrNumber().toString());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			query = null;

			//update pr_account
			if ("Y".equals(inputBean.getCanEditMr()))
				updatePrAccount(inputBean);

			//update request_line_item
			query = new StringBuilder("update request_line_item set last_updated = sysdate, last_updated_by = ").append(personnelBean.getPersonnelId());
			if (!StringHandler.isBlankString(inputBean.getNotes())) {
				query.append(",notes = ").append(SqlHandler.delimitString(inputBean.getNotes()));
			}else {
				query.append(",notes = null");
			}
			if (inputBean.getCritical() != null) {
				query.append(",critical = 'y'");
			}else {
				query.append(",critical = 'n'");
			}
			if (!StringHandler.isBlankString(inputBean.getChargeType())) {
				query.append(",charge_type = '").append(inputBean.getChargeType()+"'");
			}else {
				query.append(",charge_type = 'd'");
			}
			if (inputBean.getQty() != null) {
				query.append(",quantity = ").append(inputBean.getQty().toString());
			}
			//delivery type
			query.append(",delivery_type = 'Deliver by'");
			if (inputBean.getDeliveryBy() != null) {
				query.append(",required_datetime = ").append(DateHandler.getOracleToDateFunction(inputBean.getDeliveryBy()));
			}else {
				query.append(",required_datetime = null");
			}
			if (!StringHandler.isBlankString(inputBean.getDock())) {
				query.append(",ship_to_location_id = '").append(inputBean.getDock()+"'");
			}else {
				query.append(",ship_to_location_id = null");
			}
			if (!StringHandler.isBlankString(inputBean.getDeliverTo())) {
				query.append(",delivery_point = '").append(inputBean.getDeliverTo()+"'");
			}else {
				query.append(",delivery_point = null");
			}
			if (!StringHandler.isBlankString(inputBean.getPoNumber())) {
				query.append(",po_number = ").append(SqlHandler.delimitString(inputBean.getPoNumber()));
			}else {
				query.append(",po_number = null");
			}
			if (!StringHandler.isBlankString(inputBean.getPoLineInput())) {
				query.append(",release_number = ").append(SqlHandler.delimitString(inputBean.getPoLineInput()));
			}else {
				query.append(",release_number = null");
			}
			query.append(" where pr_number = ").append(inputBean.getPrNumber().toString()).append(" and line_item = '1'");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	}

	private void updatePrAccount(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			//first delete pr_account for line
			genericSqlFactory.deleteInsertUpdate("delete from pr_account where pr_number ="+inputBean.getPrNumber().toString(),connection);
			//insert new data
			String tmpChargeNumbers = inputBean.getChargeNumbers();
			if (!StringHandler.isBlankString(tmpChargeNumbers)) {
				//insert new data
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),"");
				String query = "insert into pr_account (pr_number,line_item,company_id,percentage,account_number,account_number2,account_number3,account_number4)";
				String values = " values ("+inputBean.getPrNumber().toString()+",'1','"+inputBean.getCompanyId()+"'";
				Collection prAccountColl = mrProcess.getChargeNumberFromString(tmpChargeNumbers);
				Iterator iter = prAccountColl.iterator();
				while (iter.hasNext()) {
					PrAccountBean prAccountBean = (PrAccountBean)iter.next();
					StringBuilder tmpValues = new StringBuilder("");
					if (prAccountBean.getPercentage() != null) {
						tmpValues.append(",").append(prAccountBean.getPercentage().toString());
					}else {
						tmpValues.append(",null");
					}
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber())) {
						tmpValues.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber()));
					}else {
						tmpValues.append(",null");
					}
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber2())) {
						tmpValues.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber2()));
					}else {
						tmpValues.append(",null");
					}
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber3())) {
						tmpValues.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber3()));
					}else {
						tmpValues.append(",null");
					}
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber4())) {
						tmpValues.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber4()));
					}else {
						tmpValues.append(",null");
					}
					tmpValues.append(")");
					genericSqlFactory.deleteInsertUpdate(query+values+tmpValues.toString(),connection);
				}
			}
		}catch (Exception e) {
			throw e;
		}
	}

	public void updateCatalogAddItem(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			StringBuilder query = new StringBuilder("update catalog_add_item set ");
			if (!StringHandler.isBlankString(inputBean.getSuggestedVendor())) {
				query.append( "suggested_vendor = ").append(SqlHandler.delimitString(inputBean.getSuggestedVendor()));
			}else {
				query.append("suggested_vendor = null");
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactName())) {
				query.append(",vendor_contact_name = ").append(SqlHandler.delimitString(inputBean.getVendorContactName()));
			}else {
				query.append(",vendor_contact_name = null");
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactEmail())) {
				query.append(",vendor_contact_email = ").append(SqlHandler.delimitString(inputBean.getVendorContactEmail()));
			}else {
				query.append(",vendor_contact_email = null");
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactPhone())) {
				query.append(",vendor_contact_phone = ").append(SqlHandler.delimitString(inputBean.getVendorContactPhone()));
			}else {
				query.append(",vendor_contact_phone = null");
			}
			if (!StringHandler.isBlankString(inputBean.getVendorContactFax())) {
				query.append(",vendor_contact_fax = ").append(SqlHandler.delimitString(inputBean.getVendorContactFax()));
			}else {
				query.append(",vendor_contact_fax = null");
			}
			//if it is multi parts kit, just store data on the part_id = 1
			query.append(" where line_item = 1 and part_id = 1 and request_id = ").append(inputBean.getRequestId().toString());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	}

	public void updateCatalogAddRequestNew(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws Exception {
		try {
			StringBuilder query = new StringBuilder("update catalog_add_request_new set last_updated = sysdate,last_changed_by = ").append(personnelBean.getPersonnelId());
			if (inputBean.getFreeSample() != null) {
				query.append(",free_sample = 'Y'");
			}else {
				query.append(",free_sample = 'N'");
			}
			if (inputBean.getShippingWeight() != null) {
				query.append(",shipping_weight = ").append(inputBean.getShippingWeight());
			}else {
				query.append(",shipping_weight = null");
			}
			if (!StringHandler.isBlankString(inputBean.getShippingWeightUnit()) && !"Select One".equalsIgnoreCase(inputBean.getShippingWeightUnit())) {
				query.append(",shipping_weight_unit = ").append(SqlHandler.delimitString(inputBean.getShippingWeightUnit()));
			}else {
				query.append(",shipping_weight_unit = null");
			}
			query.append(" where request_id = ").append(inputBean.getRequestId().toString());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	}

	public void catalogAddItemInsert(BigDecimal requestId, CatalogAddItemBean bean, int partId) throws Exception {
		try {
			StringBuffer query = new StringBuffer("insert into catalog_add_item (company_id,request_id,part_id,line_item");
			StringBuffer values = new StringBuffer("values ('").append(bean.getCompanyId()).append("',").append(requestId).append(",").append(partId).append(",").append(lineItem);
			query.append(",material_desc");
			if (!StringHandler.isBlankString(bean.getMaterialDesc())) {
				values.append(",").append(SqlHandler.delimitString(bean.getMaterialDesc()));
			}else {
				values.append(",null");
			}
			query.append(",manufacturer");
			if (!StringHandler.isBlankString(bean.getManufacturer())) {
				values.append(",").append(SqlHandler.delimitString(bean.getManufacturer()));
			}else {
				values.append(",null");
			}
			query.append(",material_id");
			if (bean.getMaterialId() != null) {
				values.append(",").append(bean.getMaterialId());
			}else {
				values.append(",null");
			}
			query.append(",part_size");
			if (bean.getPartSize() != null) {
				values.append(",").append(bean.getPartSize());
			}else {
				values.append(",null");
			}
			query.append(",size_unit");
			if (!StringHandler.isBlankString(bean.getSizeUnit()) && !"Select One".equalsIgnoreCase(bean.getSizeUnit())) {
				values.append(",").append(SqlHandler.delimitString(bean.getSizeUnit()));
			}else {
				values.append(",null");
			}
			query.append(",pkg_style");
			if (!StringHandler.isBlankString(bean.getPkgStyle()) && !"Select One".equalsIgnoreCase(bean.getPkgStyle())) {
				values.append(",").append(SqlHandler.delimitString(bean.getPkgStyle()));
			}else {
				values.append(",null");
			}
			query.append(",material_approved_by");
			if (bean.getMaterialApprovedBy() != null) {
				values.append(",").append(bean.getMaterialApprovedBy());
			}else {
				values.append(",null");
			}
			query.append(",material_approved_on");
			if (bean.getMaterialApprovedOn() != null) {
				values.append(",").append(DateHandler.getOracleToDateFunction(bean.getMaterialApprovedOn()));
			}else {
				values.append(",null");
			}
			query.append(",item_approved_by");
			if (bean.getItemApprovedBy() != null) {
				values.append(",").append(bean.getItemApprovedBy());
			}else {
				values.append(",null");
			}
			query.append(",item_approved_on");
			if (bean.getItemApprovedOn() != null) {
				values.append(",").append(DateHandler.getOracleToDateFunction(bean.getItemApprovedOn()));
			}else {
				values.append(",null");
			}
			query.append(",mfg_catalog_id");
			if (!StringHandler.isBlankString(bean.getMfgCatalogId())) {
				values.append(",").append(SqlHandler.delimitString(bean.getMfgCatalogId()));
			}else {
				values.append(",null");
			}
			//part_id 	-at the top
			query.append(",material_type");
			if (!StringHandler.isBlankString(bean.getMaterialType())) {
				values.append(",").append(SqlHandler.delimitString(bean.getMaterialType()));
			}else {
				values.append(",null");
			}
			query.append(",case_qty");
			if (bean.getCaseQty() != null) {
				values.append(",").append(bean.getCaseQty());
			}else {
				values.append(",1");
			}
			query.append(",dimension");
			if (!StringHandler.isBlankString(bean.getDimension())) {
				values.append(",").append(SqlHandler.delimitString(bean.getDimension()));
			}else {
				values.append(",null");
			}
			query.append(",grade");
			if (!StringHandler.isBlankString(bean.getGrade())) {
				values.append(",").append(SqlHandler.delimitString(bean.getGrade()));
			}else {
				values.append(",null");
			}
			query.append(",mfg_trade_name");
			if (!StringHandler.isBlankString(bean.getMfgTradeName())) {
				values.append(",").append(SqlHandler.delimitString(bean.getMfgTradeName()));
			}else {
				values.append(",null");
			}
			query.append(",netwt");
			if (bean.getNetwt() != null) {
				values.append(",").append(bean.getNetwt());
			}else {
				values.append(",null");
			}
			query.append(",netwt_unit");
			if (!StringHandler.isBlankString(bean.getNetwtUnit()) && !"Select One".equalsIgnoreCase(bean.getNetwtUnit())) {
				values.append(",'").append(bean.getNetwtUnit()).append("'");
			}else {
				values.append(",null");
			}
			//company_id  -at the top
			query.append(",customer_msds_number");
			if (!StringHandler.isBlankString(bean.getCustomerMsdsNumber())) {
				values.append(",").append(SqlHandler.delimitString(bean.getCustomerMsdsNumber()));
			}else {
				values.append(",null");
			}
			query.append(",components_per_item");
			if (bean.getComponentsPerItem() != null) {
				values.append(",").append(bean.getComponentsPerItem());
			}else {
				values.append(",1");
			}
			query.append(",sample_only");
			if (bean.getSampleOnly() != null) {
				values.append(",'Y'");
			}else {
				values.append(",null");
			}
			query.append(",poss_size");
			if (!StringHandler.isBlankString(bean.getPossSize())) {
				values.append(",").append(SqlHandler.delimitString(bean.getPossSize()));
			}else {
				values.append(",null");
			}
			query.append(",poss_special_note");
			if (!StringHandler.isBlankString(bean.getPossSpecialNote())) {
				values.append(",").append(SqlHandler.delimitString(bean.getPossSpecialNote()));
			}else {
				values.append(",null");
			}
			query.append(",shelf_life_days");
			if (bean.getShelfLifeDays() != null) {
				values.append(","+bean.getShelfLifeDays());
			}else {
				values.append(",null");
			}
			query.append(",shelf_life_basis");
			if (!StringHandler.isBlankString(bean.getShelfLifeBasis())) {
				values.append(",").append(SqlHandler.delimitString(bean.getShelfLifeBasis()));
			}else {
				values.append(",null");
			}
			query.append(",storage_temp");
			if (!StringHandler.isBlankString(bean.getStorageTemp())) {
				values.append(",").append(SqlHandler.delimitString(bean.getStorageTemp()));
			}else {
				values.append(",null");
			}
			query.append(",catalog_component_item_id");
			if (!StringHandler.isBlankString(bean.getCatalogComponentItemId())) {
				values.append(",").append(SqlHandler.delimitString(bean.getCatalogComponentItemId()));
			}else {
				values.append(",null");
			}
			query.append(",catalog_component_id");
			if (!StringHandler.isBlankString(bean.getCatalogComponentId())) {
				values.append(",").append(SqlHandler.delimitString(bean.getCatalogComponentId()));
			}else {
				values.append(",null");
			}
			query.append(",customer_revision_date");
			if (bean.getCustomerRevisionDate() != null) {
				values.append(",").append(DateHandler.getOracleToDateFunction(bean.getCustomerRevisionDate()));
			}else {
				values.append(",null");
			}
			query.append(",label_color");
			if (!StringHandler.isBlankString(bean.getLabelColor())) {
				values.append(",").append(SqlHandler.delimitString(bean.getLabelColor()));
			}else {
				values.append(",null");
			}
			query.append(",mfg_id");
			if (bean.getMfgId() != null) {
				values.append(",").append(bean.getMfgId());
			}else {
				values.append(",null");
			}
			//line_item		-at the top
			query.append(",vendor_contact_name");
			if (!StringHandler.isBlankString(bean.getVendorContactName())) {
				values.append(",").append(SqlHandler.delimitString(bean.getVendorContactName()));
			}else {
				values.append(",null");
			}
			query.append(",vendor_contact_email");
			if (!StringHandler.isBlankString(bean.getVendorContactEmail())) {
				values.append(",").append(SqlHandler.delimitString(bean.getVendorContactEmail()));
			}else {
				values.append(",null");
			}
			query.append(",vendor_contact_phone");
			if (!StringHandler.isBlankString(bean.getVendorContactPhone())) {
				values.append(",").append(SqlHandler.delimitString(bean.getVendorContactPhone()));
			}else {
				values.append(",null");
			}
			query.append(",vendor_contact_fax");
			if (!StringHandler.isBlankString(bean.getVendorContactFax())) {
				values.append(",").append(SqlHandler.delimitString(bean.getVendorContactFax()));
			}else {
				values.append(",null");
			}
			query.append(",suggested_vendor");
			if (!StringHandler.isBlankString(bean.getSuggestedVendor())) {
				values.append(",").append(SqlHandler.delimitString(bean.getSuggestedVendor()));
			}else {
				values.append(",null");
			}
			query.append(",item_id");
			if (bean.getItemId() != null) {
				values.append(",").append(bean.getItemId());
			}else {
				values.append(",null");
			}
			query.append(",starting_view");
			if (bean.getStartingView() != null) {
				values.append(",").append(bean.getStartingView());
			}else {
				values.append(",0");
			}
			query.append(",manufacturer_contact");
			if (!StringHandler.isBlankString(bean.getManufacturerContact())) {
				values.append(",").append(SqlHandler.delimitString(bean.getManufacturerContact()));
			}else {
				values.append(",null");
			}
			query.append(",manufacturer_url");
			if (!StringHandler.isBlankString(bean.getManufacturerUrl())) {
				values.append(",").append(SqlHandler.delimitString(bean.getManufacturerUrl()));
			}else {
				values.append(",null");
			}
			query.append(",manufacturer_notes");
			if (!StringHandler.isBlankString(bean.getManufacturerNotes())) {
				values.append(",").append(SqlHandler.delimitString(bean.getManufacturerNotes()));
			}else {
				values.append(",null");
			}

			query.append(") ");
			query.append(values.toString());
			query.append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	}

	public void updateCatalogAddItemWithScreenData(BigDecimal requestId, CatalogAddItemBean bean) throws Exception {
		try {
			StringBuffer query = new StringBuffer("update catalog_add_item set ");

			if (!StringHandler.isBlankString(bean.getMaterialDesc())) {
				query.append("material_desc = ").append(SqlHandler.delimitString(bean.getMaterialDesc()));
			}else {
				query.append("material_desc = null");
			}
			if (!StringHandler.isBlankString(bean.getManufacturer())) {
				query.append(",manufacturer = ").append(SqlHandler.delimitString(bean.getManufacturer()));
			}else {
				query.append(",manufacturer = null");
			}
			if (bean.getPartSize() != null) {
				query.append(",part_size = ").append(bean.getPartSize());
			}else {
				query.append(",part_size = null");
			}
			if (!StringHandler.isBlankString(bean.getSizeUnit()) && !"Select One".equalsIgnoreCase(bean.getSizeUnit())) {
				query.append(",size_unit = ").append(SqlHandler.delimitString(bean.getSizeUnit()));
			}else {
				query.append(",size_unit = null");
			}
			if (!StringHandler.isBlankString(bean.getPkgStyle()) && !"Select One".equalsIgnoreCase(bean.getPkgStyle())) {
				query.append(",pkg_style = ").append(SqlHandler.delimitString(bean.getPkgStyle()));
			}else {
				query.append(",pkg_style = null");
			}
			if (!StringHandler.isBlankString(bean.getMfgCatalogId())) {
				query.append(",mfg_catalog_id = ").append(SqlHandler.delimitString(bean.getMfgCatalogId()));
			}else {
				query.append(",mfg_catalog_id = null");
			}
			if (!StringHandler.isBlankString(bean.getDimension())) {
				query.append(",dimension = ").append(SqlHandler.delimitString(bean.getDimension()));
			}else {
				query.append(",dimension = null");
			}
			if (!StringHandler.isBlankString(bean.getGrade())) {
				query.append(",grade = ").append(SqlHandler.delimitString(bean.getGrade()));
			}else {
				query.append(",grade = null");
			}
			if (!StringHandler.isBlankString(bean.getMfgTradeName())) {
				query.append(",mfg_trade_name = ").append(SqlHandler.delimitString(bean.getMfgTradeName()));
			}else {
				query.append(",mfg_trade_name = null");
			}
			if (bean.getNetwt() != null) {
				query.append(",netwt = ").append(bean.getNetwt());
			}else {
				query.append(",netwt = null");
			}
			if (!StringHandler.isBlankString(bean.getNetwtUnit()) && !"Select One".equalsIgnoreCase(bean.getNetwtUnit())) {
				query.append(",netwt_unit = ").append("'"+bean.getNetwtUnit()).append("'");
			}else {
				query.append(",netwt_unit = null");
			}
			if (!StringHandler.isBlankString(bean.getCustomerMsdsNumber())) {
				query.append(",customer_msds_number = ").append(SqlHandler.delimitString(bean.getCustomerMsdsNumber()));
			}else {
				query.append(",customer_msds_number = null");
			}
			if (!StringHandler.isBlankString(bean.getReplacesMsds())) {
				query.append(",replaces_msds = ").append(SqlHandler.delimitString(bean.getReplacesMsds()));
			}else {
				query.append(",replaces_msds = null");
			}
			if ("Y".equals(bean.getRadioactive()) || "true".equals(bean.getRadioactive())) {
				query.append(",radioactive = 'Y'");
			}
			if ("N".equals(bean.getRadioactive()) || "false".equals(bean.getRadioactive())) {
				query.append(",radioactive = null");
			}
			if ("Y".equals(bean.getNanomaterial()) || "true".equals(bean.getNanomaterial())) {
				query.append(",nanomaterial = 'Y'");
			}
			if ("N".equals(bean.getNanomaterial()) || "false".equals(bean.getNanomaterial())){
				query.append(",nanomaterial = null");
			}
			if (bean.getComponentsPerItem() != null) {
				query.append(",components_per_item = ").append(bean.getComponentsPerItem());
			}else {
				query.append(",components_per_item = 1");
			}
			if (bean.getSampleOnly() != null) {
				query.append(",sample_only = ").append("'Y'");
			}else {
				query.append(",sample_only = null");
			}
			if (!StringHandler.isBlankString(bean.getCustomerMixtureNumber())) {
				query.append(",customer_mixture_number = ").append(SqlHandler.delimitString(bean.getCustomerMixtureNumber()));
			}else {
				query.append(",customer_mixture_number = null");
			}
			if (!StringHandler.isBlankString(bean.getMixtureDesc())) {
				query.append(",mixture_desc = ").append(SqlHandler.delimitString(bean.getMixtureDesc()));
			}else {
				query.append(",mixture_desc = null");
			}
			if (!StringHandler.isBlankString(bean.getVocetStatusId())) {
				query.append(",vocet_status_id = '").append(bean.getVocetStatusId()).append("'");
			}else {
				query.append(",vocet_status_id = null");
			}
			if (!StringHandler.isBlankString(bean.getVocetCoatCategoryId())) {
				query.append(",vocet_coat_category_id = '").append(bean.getVocetCoatCategoryId()).append("'");
			}else {
				query.append(",vocet_coat_category_id = null");
			}

			//mix_ratio
			if (bean.getMixRatioAmount() != null) {
				query.append(",mix_ratio_amount = ").append(bean.getMixRatioAmount());
			}else {
				query.append(",mix_ratio_amount = null");
			}
			if (!StringHandler.isBlankString(bean.getMixRatioSizeUnit()) && !"Select One".equalsIgnoreCase(bean.getMixRatioSizeUnit())) {
				query.append(",mix_ratio_size_unit = ").append(SqlHandler.delimitString(bean.getMixRatioSizeUnit()));
			}else {
				query.append(",mix_ratio_size_unit = null");
			}

			if (!StringHandler.isBlankString(bean.getCustomerMfgId()))
				query.append(",customer_mfg_id = ").append(SqlHandler.delimitString(bean.getCustomerMfgId()));
			else
				query.append(",customer_mfg_id = ''");

			query.append(" where request_id = ").append(requestId).append(" and line_item = ").append(lineItem).append(" and part_id = ").append(bean.getPartId());

			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	}

	public void createCatalogAddItemQc(BigDecimal requestId) throws Exception {
		//first remove record just incase user already hit submit and failed.
		genericSqlFactory.deleteInsertUpdate("delete from catalog_add_item_qc where request_id = "+requestId,connection);

		String tmpComment = "";
		//catalog_add_item_qc
		StringBuilder query = new StringBuilder("insert into catalog_add_item_qc (company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,");
		query.append("material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,");
		query.append("mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,status,comments,label_color,mfg_id,line_item,item_id,starting_view,mixture_desc");
		query.append(",catalog_add_item_id,locale_code,source_revision_date,customer_mixture_number");
		query.append(") (select company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,");
		query.append("material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,");
		query.append("mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,");
		query.append("decode(starting_view,'5','Approved QC',decode(material_id,null,'Pending MSDS','0','Pending MSDS','Pending QC')),'");
		query.append(tmpComment).append("',label_color,mfg_id,line_item,item_id,starting_view,mixture_desc");
		query.append(",catalog_add_item_id,locale_code,source_revision_date,customer_mixture_number");
		query.append(" from catalog_add_item where request_id = ").append(requestId+")");
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		//call below procedure to update kit manufacturer
		setMixtureMfgId(requestId);
	} //end of method

	private void setMixtureMfgId(BigDecimal requestId) throws Exception {
		try {
			Vector cin = new Vector(1);
			cin.addElement(requestId);
			genericSqlFactory.doProcedure("p_set_catadd_mixture_data", cin);
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Setting Kit Manufacturer Id for catalog add request Error","p_set_catadd_mixture_data - Request - "+requestId+"\n"+e.getMessage());
		}
	} //end of method

	public void createCatalogAddItemOrig(BigDecimal requestId) throws Exception {
		//catalog_add_item_orig
		genericSqlFactory.deleteInsertUpdate("delete from catalog_add_item_orig where request_id = "+requestId,connection);
		StringBuilder query = new StringBuilder("insert into catalog_add_item_orig (company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,");
		query.append("material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,");
		query.append("mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note,label_color,mfg_id,line_item,item_id");
		query.append(",catalog_add_item_id,locale_code,source_revision_date");
		query.append(",postscreen_material_desc, postscreen_mfg_trade_name, postscreen_mfg_catalog_id, postscreen_dimension, postscreen_grade");
		query.append(") (select company_id,request_id,part_id,material_desc,manufacturer,material_id,part_size,size_unit,pkg_style,");
		query.append("material_approved_by,material_approved_on,item_approved_by,item_approved_on,mfg_catalog_id,material_type,case_qty,dimension,grade,");
		query.append("mfg_trade_name,netwt,netwt_unit,customer_msds_number,components_per_item,sample_only,poss_size,poss_special_note,label_color,mfg_id,line_item,item_id");
		query.append(",catalog_add_item_id,locale_code,source_revision_date");
		query.append(",material_desc,mfg_trade_name,mfg_catalog_id,dimension,grade");
		query.append(" from catalog_add_item where request_id = ").append(requestId+")");
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
	} //end of method

	public void updateCatalogAddItemQcNOrig(BigDecimal requestId) throws Exception {
		try {
			//catalog_add_item_qc
			createCatalogAddItemQc(requestId);
			//catalog_add_item_orig
			createCatalogAddItemOrig(requestId);
		}catch (Exception e) {
			throw e;
		}
	} //end of method

	public void createCatalogAddSpecQc(BigDecimal requestId) throws Exception {
		//first remove record just incase user already hit submit and failed.
		genericSqlFactory.deleteInsertUpdate("delete from catalog_add_spec_qc where request_id = "+requestId,connection);

		//catalog_add_spec_qc
		StringBuilder query = new StringBuilder("insert into catalog_add_spec_qc (company_id,request_id,spec_id,spec_name,spec_title");
		query.append(",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,spec_source)");
		query.append(" select company_id,request_id,spec_id,spec_name,spec_title");
		query.append(",spec_version,spec_amendment,spec_date,content,on_line,spec_library,coc,coa,itar,line_item,");
		query.append("decode(spec_source,null,'catalog_add_spec_qc','catalog_add_spec','catalog_add_spec_qc',spec_source)");
		query.append(" from catalog_add_spec where request_id = ").append(requestId);
		genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
	} //end of method

	public void updateCatalogAddSpecQc(BigDecimal requestId) throws Exception {
		try {
			//catalog_add_spec_qc
			createCatalogAddSpecQc(requestId);
		}catch (Exception e) {
			throw e;
		}
	} //end of method

	public boolean deleteRequest(CatAddHeaderViewBean inputBean) {
		boolean result = true;
		try{
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			Vector cin = new Vector(1);
			cin.addElement(inputBean.getRequestId().toString());
			Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.VARCHAR));
			Collection resultData = factory.doProcedure("p_del_new_chem_req", cin, cout);
			Iterator i11 = resultData.iterator();
			String val = "";
			while (i11.hasNext()) {
				val = (String) i11.next();
			}
			if (!"OK".equalsIgnoreCase(val)) {
				result = false;
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Deleting catalog add request Error","p_del_new_chem_req - Request - "+inputBean.getRequestId().toString()+"\n"+val);
			}
		} catch(Exception e){
			log.error(e);
			result = false;
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Deleting catalog add request Error","Request - "+inputBean.getRequestId().toString());
		}
		return result;
	}

	public CatAddHeaderViewBean getEngEvalData(BigDecimal requestId, PersonnelBean personnelBean) throws BaseException {
		CatAddHeaderViewBean catAddHeaderViewBean = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			//get catalog_add_request_new data
			Vector<CatAddHeaderViewBean> engEvalHeaderColl = (Vector)getCatAddHeaderView(requestId);
			catAddHeaderViewBean = engEvalHeaderColl.get(0);
			//get catalog_add_item data
			catAddHeaderViewBean.setCatalogAddItemColl(getCatalogAddItem(requestId));
			//if request is pending approval then get all approvers who can approve this request
			if (catAddHeaderViewBean.getRequestStatus().intValue() > 4 && catAddHeaderViewBean.getRequestStatus().intValue() < 9) {
				catAddHeaderViewBean.setApproverColl(getChemicalApprovers(requestId,personnelBean));
			}
			//get eng eval history for item
			catAddHeaderViewBean.setEngEvalHistoryColl(getEngEvalHistory(catAddHeaderViewBean.getRequestId()));
			//get size unit
			catAddHeaderViewBean.setSizeUnitViewColl(getSizeUnitView());
			//get net wt size unit
			catAddHeaderViewBean.setNetWtSizeUnitColl(getNetWtSizeUnit());
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

	public Collection getChemicalApprovers(BigDecimal requestId,PersonnelBean personnelBean) throws Exception {
		Collection result = new Vector();
		try {
			StringBuilder query = new StringBuilder("select ca.personnel_id,ca.approval_role,car.*");
			query.append(",pkg_company_msds.fx_is_approval_role_before(car.company_id,car.facility_id,car.approval_role,car.catalog_company_id,car.catalog_id,car.application,'Material QC') before_tcm_qc");
			query.append(",pkg_company_msds.fx_is_approval_role_before(car.company_id,car.facility_id,car.approval_role,car.catalog_company_id,car.catalog_id,car.application,'TCM Spec') before_tcm_spec");
			query.append(" from catalog_add_request_new carn,chemical_approver ca,vv_chemical_approval_role car");
			if ("TCM_OPS".equals(getClient()))
				query.append(",vv_catalog_add_request_status ars");
			else
				query.append(",vv_catalog_add_request_status$ ars");
			query.append(" where carn.request_status = ars.request_status and carn.company_id = ars.company_id and");
			query.append(" ca.personnel_id = "+personnelBean.getPersonnelId()+" and ars.approval_group = car.approval_group and");
			query.append(" carn.approval_group_seq = car.approval_group_seq and car.active = 'Y' and car.approval_role = ca.approval_role and");
			query.append(" car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and ca.active = 'y' and");
			query.append(" car.facility_id = carn.eng_eval_facility_id and car.catalog_id = carn.catalog_id and car.catalog_company_id = carn.catalog_company_id and");
			query.append(" 'Y'=decode(decode(carn.engineering_evaluation,'Y',decode(carn.starting_view,2,8888,9999),carn.starting_view),0");
			query.append(",car.new_material,1,car.new_size,2,car.new_part,3,car.new_approval,6,car.new_msds,7,car.new_approval_code,9,car.sds_authoring,8888,car.reorder_eng_eval,9999,car.eng_eval,'Y') and");
			query.append(" carn.request_id = "+requestId.toString());
			query.append(" and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = ").append(requestId.toString()).append(")");
			query.append(" and ca.approval_role not in (select approval_role from catalog_add_approval where request_id = ").append(requestId.toString()).append(")");
			query.append(" and ( (carn.approval_path = 'MSDS+Part' and ( car.msds_approval = 'Y' or car.part_approval = 'Y'))");
			query.append(" or (carn.approval_path = 'MSDS' and car.msds_approval = 'Y')");
			query.append(" or (carn.approval_path = 'Part' and car.part_approval = 'Y'))");
			genericSqlFactory.setBeanObject(new ChemicalApproverBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				ChemicalApproverBean bean = (ChemicalApproverBean)iter.next();
				//if user is an approver for Pricing role then make sure that there is a price quote before allowing user to approve this role
				if ("Pricing".equals(bean.getApprovalRole())) {
					query = new StringBuilder("select count(*) from price_quote_last_stat_view p, catalog_add_item cai");
					query.append( " where p.item_id = cai.item_id and trunc(p.last_quote_date) + 365 > trunc(sysdate)");
					query.append(" and cai.request_id = ").append(requestId);
					//no price quote within the last year
					//user cannot approve this role until price quote is completed
					if (!this.dataCountIsZero(query.toString())) {
						result.add(bean);
					}
				}else {
					result.add(bean);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public Collection getApprovalComments(BigDecimal requestId,PersonnelBean personnelBean) throws Exception {
		Collection result = null;
		try {
			StringBuilder query = new StringBuilder("select mrc.approval_role,mrc.request_comment");
			query.append(" from catalog_add_request_new carn");
			if ("TCM_OPS".equals(getClient()))
				query.append(",chemical_approver ca,vv_chemical_approval_role car,vv_catalog_add_request_status ars");
			else
				query.append(",chemical_approver$ ca,vv_chemical_approval_role$ car,vv_catalog_add_request_status$ ars");
			query.append(",vv_material_request_comment mrc ");
			query.append(" where carn.request_status = ars.request_status and carn.company_id = ars.company_id and");
			query.append(" ca.personnel_id = "+personnelBean.getPersonnelId()+" and ars.approval_group = car.approval_group and");
			query.append(" carn.approval_group_seq = car.approval_group_seq and car.active = 'Y' and car.company_id = ca.company_id and car.approval_role = ca.approval_role and");
			query.append(" car.facility_id = ca.facility_id and car.catalog_id = ca.catalog_id and car.catalog_company_id = ca.catalog_company_id and ca.active = 'y'");
			query.append(" and car.company_id = carn.company_id and car.facility_id = carn.eng_eval_facility_id and car.catalog_id = carn.catalog_id and car.catalog_company_id = carn.catalog_company_id and");
			query.append(" 'Y'=decode(decode(carn.engineering_evaluation,'Y',decode(carn.starting_view,2,8888,9999),carn.starting_view),0");
			query.append(",car.new_material,1,car.new_size,2,car.new_part,3,car.new_approval,6,car.new_msds,7,car.new_approval_code,9,car.sds_authoring,8888,car.reorder_eng_eval,9999,car.eng_eval,'Y') and");
			query.append(" carn.request_id = "+requestId.toString());
			query.append(" and car.application = ca.application and car.application in (select 'All' from dual union select work_area from catalog_add_user_group where request_id = ").append(requestId.toString()).append(")");
			query.append(" and ca.approval_role not in (select approval_role from catalog_add_approval where request_id = ").append(requestId.toString()).append(")");
			query.append(" and carn.company_id = mrc.company_id and carn.catalog_company_id = mrc.catalog_company_id and carn.catalog_id = mrc.catalog_id");
			query.append(" and mrc.approval_role = car.approval_role");
			query.append(" and mrc.approval_comment = 'Y'");
			query.append(" order by approval_role,request_comment");
			genericSqlFactory.setBeanObject(new ChemicalApproverBean());
			result = genericSqlFactory.selectQuery(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
		return result;
	}


	private Collection getCatAddHeaderView(BigDecimal requestId) throws BaseException {
		genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
		StringBuilder query = new StringBuilder("select carn.*, fx_personnel_id_to_name(carn.requestor) requestor_name,fx_personnel_id_to_email(carn.requestor) requestor_email,fx_personnel_id_to_phone(carn.requestor) requestor_phone, pr.pr_number,");
		query.append("cai.suggested_vendor,cai.vendor_contact_name,cai.vendor_contact_email,cai.vendor_contact_phone,cai.vendor_contact_fax,fx_get_database_name database_name");
		query.append(" from catalog_add_request_new carn, catalog_add_item cai, purchase_request pr where carn.company_id = pr.company_id(+) and carn.eng_eval_facility_id = pr.facility_id(+)");
		query.append(" and carn.request_id = pr.request_id(+) and carn.request_id = ").append(requestId.toString());
		query.append(" and carn.company_id = cai.company_id and carn.request_id = cai.request_id and nvl(cai.line_status,'XX') <> 'Rejected' and cai.part_id = 1");
		return genericSqlFactory.selectQuery(query.toString(),connection);
	}

	public Collection getCatalogAddItem(BigDecimal requestId) throws BaseException {
		return getCatalogAddItem(requestId, null);
	}

	public Collection getCatalogAddItem(BigDecimal requestId, Vector<BigDecimal> partIdColl) throws BaseException {
		genericSqlFactory.setBeanObject(new CatalogAddItemBean());
		StringBuilder query = new StringBuilder("select cai.*,decode(cai.material_id,null,'N',fx_msds_material_online(cai.material_id)) msds_on_line");
		query.append(" from catalog_add_item cai where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
		if(partIdColl != null)
		{
			query.append(" and part_id in(");
			int partCount = 1;
			int partIdCollSize = partIdColl.size();
			for(BigDecimal partId: partIdColl)
			{
				query.append(partId);
				if(partCount != partIdCollSize)
					query.append(",");
				++partCount;
			}
			query.append(")");
		}

		query.append(" order by part_id");
		return genericSqlFactory.selectQuery(query.toString(), connection);
	}

	private Collection getEngEvalHistory(BigDecimal requestId) throws BaseException {
		genericSqlFactory.setBeanObject(new EngEvalHistoryBean());
		StringBuilder query = new StringBuilder("select distinct f.facility_name,fla.application_desc application_display,");
		query.append("caug.user_group_id,rli.quantity qty,carn.submit_date,p.last_name||', '||p.first_name||' - '||p.phone  requestor");
		query.append(" from catalog_add_request_new carn, catalog_add_item cai, catalog_add_user_group caug, facility_view f, fac_loc_app fla, personnel p,purchase_request pr,request_line_item rli");
		query.append(" where carn.company_id = caug.company_id and carn.request_id = caug.request_id and caug.company_id = f.company_id and caug.facility_id = f.facility_id and caug.facility_id = fla.facility_id and");
		query.append(" carn.company_id = cai.company_id and carn.request_id = cai.request_id and");
		query.append(" caug.work_area = fla.application and carn.requestor = p.personnel_id and carn.request_status = 12 and carn.request_id = ").append(requestId);
		query.append(" and cai.item_id <> 442554 and caug.company_id = pr.company_id and caug.facility_id = pr.facility_id");
		query.append(" and carn.request_id = pr.request_id and pr.company_id = rli.company_id and pr.pr_number = rli.pr_number");
		query.append(" order by f.facility_name,application_display");
		return genericSqlFactory.selectQuery(query.toString(),connection);
	}

	public Collection getSizeUnitView() throws BaseException, Exception {
		genericSqlFactory.setBeanObject(new SizeUnitViewBean());
		SearchCriteria criteria = new SearchCriteria();

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("sizeUnit");
		return genericSqlFactory.select(criteria, sortCriteria,connection,"size_unit_view");
	}

	public Collection getNetWtSizeUnit() throws BaseException, Exception {
		genericSqlFactory.setBeanObject(new SizeUnitViewBean());
		StringBuilder query = null;
		if ("SEAGATE".equalsIgnoreCase(getClient())) {
			query = new StringBuilder("select a.from_unit size_unit from size_unit_conversion a, cont_size_size_unit_link b");
			query.append(" where a.sp_gravity_required = 'N' and a.to_unit = 'lb' and a.from_unit = b.vv_size_unit order by a.from_unit");
		} else {
			query = new StringBuilder("select from_unit size_unit from size_unit_conversion where to_unit = 'lb' order by from_unit");
		}
		return genericSqlFactory.selectQuery(query.toString(),connection);
	}

	public Collection getShippingWeightSizeUnit(boolean includeVolume) throws BaseException, Exception {
		genericSqlFactory.setBeanObject(new SizeUnitViewBean());
		SearchCriteria criteria = new SearchCriteria();
		if (includeVolume) {
			criteria.addCriterion("netWtRequired", SearchCriterion.IN, "'N','O'");
		}else {
			criteria.addCriterion("netWtRequired", SearchCriterion.EQUALS, "N");
		}
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("netWtRequired");
		sortCriteria.addCriterion("sizeUnit");

		return genericSqlFactory.select(criteria, sortCriteria,connection,"size_unit_view");
	}

	public Collection getInfo(String facilityId, String application, BigDecimal itemId, String accountSysId, BigDecimal personnelId) throws BaseException {
		Collection inArgs = new Vector();

		if (facilityId != null) {
			inArgs.add(facilityId);
		} else {
			inArgs.add("");
		}
		if (application != null) {
			inArgs.add(application);
		} else {
			inArgs.add("");
		}
		if (itemId != null) {
			inArgs.add(itemId);
		} else {
			inArgs.add("");
		}
		if (accountSysId != null) {
			inArgs.add(accountSysId);
		} else {
			inArgs.add("");
		}
		if (personnelId != null) {
			inArgs.add(personnelId);
		} else {
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.NUMERIC));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		//	   return procFactory.doProcedure("p_create_eng_eval", inArgs,outArgs);
		return null;
	}

} //end of class