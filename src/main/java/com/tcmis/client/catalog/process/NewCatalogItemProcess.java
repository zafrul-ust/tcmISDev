package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.CatalogAddApprovalBean;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Process for NewCatalogItemProcess
 * @version 1.0
 *****************************************************************************/
public class NewCatalogItemProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	DbManager dbManager;
	Connection connection = null;
	GenericSqlFactory genericSqlFactory;
	private ResourceLibrary library = null;
	String URL = "";

	public NewCatalogItemProcess(String client) {
		super(client);
	}

	public NewCatalogItemProcess(String client, String locale, String URL) {
		super(client, locale);
		this.URL = URL;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
		this.connection = connection;
		this.genericSqlFactory = genericSqlFactory;
	}

	public Object[] convertGlobalItemToDistributionCatalog(CatalogInputBean bean, PersonnelBean personnelBean,CatAddHeaderViewBean inputBean) throws BaseException {
		Object[] result = new Object[2];
		BigDecimal requestId = null;
		try {
            dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			//first create catalog add data
			requestId = buildNewPartRequest(bean,personnelBean,inputBean);
			//next load catalog add data and submit it
			if (requestId != null) {
				//get catalog_add_request_new data
				Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(requestId);
				CatAddHeaderViewBean catAddHeaderViewBean = headerColl.get(0);

				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
				engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
				//get catalog_add_item data
				Collection tabBeans = engEvalProcess.getCatalogAddItem(requestId);

				//submit request
				result[1] = doSubmitLogic(catAddHeaderViewBean,tabBeans,personnelBean);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		result[0] = requestId;
		return result;
	}

	private BigDecimal buildNewPartRequest(CatalogInputBean bean, PersonnelBean personnelBean,CatAddHeaderViewBean inputBean) throws Exception{
		BigDecimal result = null;
		try {
			String query = "select request_seq.nextval from dual";
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query,connection));

			createNewCatalogAddRequestNew(bean,personnelBean,result,"newPart");
			StringBuilder query2 = new StringBuilder("insert into catalog_add_item (request_id, material_desc, manufacturer, material_id, part_size, size_unit, pkg_style, material_approved_by,");
			query2.append("material_approved_on, item_approved_by, item_approved_on, mfg_catalog_id, part_id, material_type,");
			query2.append("case_qty, dimension, grade, mfg_trade_name, netwt, netwt_unit, company_id, customer_msds_number,");
			query2.append("components_per_item, sample_only, poss_size, poss_special_note, shelf_life_days, shelf_life_basis,");
			query2.append("storage_temp, catalog_component_item_id, catalog_component_id, customer_revision_date, label_color,");
			query2.append("mfg_id, manufacturer_contact, manufacturer_url, manufacturer_notes,line_item,starting_view,item_id) ");
			query2.append("select carn.request_id, m.material_desc,mfg.mfg_desc manufacturer, p.material_id, p.part_size, p.size_unit,");
			query2.append("p.pkg_style,null material_approved_by,null material_approved_on,null item_approved_by,");
			query2.append("null item_approved_on,p.mfg_part_no mfg_catalog_id, p.part_id,null material_type, p.case_qty,");
			query2.append("p.dimension, p.grade, m.trade_name mfg_trade_name, p.net_wt, p.net_wt_unit,carn.company_id,");
			query2.append("null customer_msds_number, p.components_per_item,null sample_only,null poss_size,");
			query2.append("null poss_special_note,null shelf_life_days,null shelf_life_basis,null storage_temp,");
			query2.append("null catalog_component_item_id,null catalog_component_id,null customer_revision_date,null label_color,mfg.mfg_id,");
			query2.append("null manufacturer_contact,null manufacturer_url,null manufacturer_notes,1,2,p.item_id");
			query2.append(" from catalog_add_request_new carn, part p, material m, manufacturer mfg where carn.request_id = ").append(result).append(" and p.item_id = ").append(inputBean.getItemId());
			query2.append(" and p.material_id = m.material_id and m.mfg_id = mfg.mfg_id order by part_id");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);
            /*todo need to go back to this when work on China project
            //handling catalog_add_item_locale
            if (!"en_US".equalsIgnoreCase(this.getLocale()) && !StringHandler.isBlankString(this.getLocale())) {
                StringBuilder queryLocale = getCatalogAddItemLocaleSql(bean,result);
                genericSqlFactory.deleteInsertUpdate(queryLocale.toString(),connection);
            }
            */

        }catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} //end of method

    private StringBuilder getCatalogAddItemLocaleSql(CatalogInputBean bean, BigDecimal requestId) {
        StringBuilder queryLocale = new StringBuilder("");
        if (bean.getItemId() != null) {
            queryLocale.append("insert into catalog_add_item_locale (company_id,request_id,line_item,part_id,locale_code,material_desc,mfg_trade_name,manufacturer)");
            queryLocale.append(" (select carn.company_id,carn.request_id,1,p.part_id,'").append(this.getLocale()).append("',ml.material_desc,ml.trade_name,mfgl.mfg_desc");
            queryLocale.append(" from catalog_add_request_new carn, part p, material m, material_locale ml, manufacturer_locale mfgl where carn.request_id = ").append(requestId).append(" and p.item_id = ").append(bean.getItemId());
            queryLocale.append(" and p.material_id = m.material_id and m.material_id = ml.material_id(+) and ml.locale_code(+) = '").append(this.getLocale()).append("'");
            queryLocale.append(" and m.mfg_id = mfgl.mfg_id(+) and mfgl.locale_code(+) = '").append(this.getLocale()).append("')");
        }else {
            queryLocale.append("insert into catalog_add_item_locale (company_id,request_id,line_item,part_id,locale_code,material_desc,mfg_trade_name,manufacturer)");
            queryLocale.append(" (select carn.company_id,carn.request_id,1,1,'").append(this.getLocale()).append("',ml.material_desc,ml.trade_name,mfgl.mfg_desc");
            queryLocale.append(" from catalog_add_request_new carn, material m, material_locale ml, manufacturer_locale mfgl where carn.request_id = ").append(requestId).append(" and m.material_id = ").append(bean.getMaterialId());
            queryLocale.append(" and m.material_id = ml.material_id(+) and ml.locale_code(+) = '").append(this.getLocale()).append("'");
            queryLocale.append(" and m.mfg_id = mfgl.mfg_id(+) and mfgl.locale_code(+) = '").append(this.getLocale()).append("')");
        }
        return queryLocale;
    }  //end of method

    public BigDecimal buildNewRequest(CatalogInputBean bean, PersonnelBean personnelBean,CatAddHeaderViewBean inputBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			String query = "select request_seq.nextval from dual";
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query,connection));

			createNewCatalogAddRequestNew(bean,personnelBean,result,"new");
			StringBuilder query2 = new StringBuilder("insert into catalog_add_item (company_id,request_id,part_id,line_item,starting_view) values ('").append(bean.getCompanyId()).append("',").append(result).append(",1,1,0)");
			genericSqlFactory.deleteInsertUpdate(query2.toString(),connection);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public BigDecimal buildNewSizeRequest(CatalogInputBean bean, PersonnelBean personnelBean,CatAddHeaderViewBean inputBean) throws Exception{
		BigDecimal result = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
			result = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));

			createNewCatalogAddRequestNew(bean,personnelBean,result,"newSize");
            StringBuilder queryLocale = new StringBuilder("");
            if (bean.getItemId() != null) {
                query = new StringBuilder("insert into catalog_add_item (request_id, material_desc, manufacturer, material_id, part_size, size_unit, pkg_style, material_approved_by,");
                query.append("material_approved_on, item_approved_by, item_approved_on, mfg_catalog_id, part_id, material_type,");
                query.append("case_qty, dimension, grade, mfg_trade_name, netwt, netwt_unit, company_id, customer_msds_number,");
                query.append("components_per_item, sample_only, poss_size, poss_special_note, shelf_life_days, shelf_life_basis,");
                query.append("storage_temp, catalog_component_item_id, catalog_component_id, customer_revision_date, label_color,");
                query.append("mfg_id, manufacturer_contact, manufacturer_url, manufacturer_notes,line_item,starting_view) ");
                query.append("select carn.request_id, m.material_desc,mfg.mfg_desc manufacturer, p.material_id, null part_size, null size_unit,");
                query.append("null pkg_style,null material_approved_by,null material_approved_on,null item_approved_by,");
                query.append("null item_approved_on,null mfg_catalog_id, p.part_id,null material_type, null case_qty,");
                query.append("null dimension, p.grade, m.trade_name mfg_trade_name, null net_wt, null net_wt_unit,carn.company_id,");
                query.append("null customer_msds_number, null components_per_item,null sample_only,null poss_size,");
                query.append("null poss_special_note,null shelf_life_days,null shelf_life_basis,null storage_temp,");
                query.append("null catalog_component_item_id,null catalog_component_id,null customer_revision_date,null label_color,mfg.mfg_id,");
                query.append("null manufacturer_contact,null manufacturer_url,null manufacturer_notes,1,1");
                query.append(" from catalog_add_request_new carn, part p, material m, manufacturer mfg where carn.request_id = ");
                query.append(result).append(" and p.item_id = ").append(inputBean.getItemId());
                query.append(" and p.material_id = m.material_id and m.mfg_id = mfg.mfg_id order by part_id");
                /*todo need to go back to this when work on China project
                if (!"en_US".equalsIgnoreCase(this.getLocale()) && !StringHandler.isBlankString(this.getLocale())) {
                    queryLocale = getCatalogAddItemLocaleSql(bean,result);
                }
                */
            }else {
                query = new StringBuilder("insert into catalog_add_item (company_id,request_id,line_item,part_id,material_id,material_desc,mfg_trade_name,");
                query.append("mfg_id,manufacturer,starting_view)");
                query.append(" (select carn.company_id,carn.request_id,1,1,m.material_id,m.material_desc,m.trade_name,mfg.mfg_id,mfg.mfg_desc,1");
                query.append(" from catalog_add_request_new carn, material m, manufacturer mfg where carn.request_id = ").append(result).append(" and m.material_id = ").append(bean.getMaterialId());
                query.append(" and m.mfg_id = mfg.mfg_id)");
                /*todo need to go back to this when work on China project
                if (!"en_US".equalsIgnoreCase(this.getLocale())&& !StringHandler.isBlankString(this.getLocale())) {
                    queryLocale = getCatalogAddItemLocaleSql(bean,result);
                }
                */
            }
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            /*todo need to go back to this when work on China project
            //handling catalog_add_item_locale data
            if (!StringHandler.isBlankString(queryLocale.toString())) {
                genericSqlFactory.deleteInsertUpdate(queryLocale.toString(),connection);
            }
            */
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	private void createNewCatalogAddRequestNew(CatalogInputBean inputBean, PersonnelBean personnelBean, BigDecimal requestId, String startingPoint) {
		try {
			StringBuilder query = new StringBuilder("insert into catalog_add_request_new (request_id,requestor,request_date,cat_part_no,catalog_id,");
			query.append("eng_eval_facility_id,catalog_company_id,eng_eval_work_area,account_sys_id,engineering_evaluation,company_id,create_temporary_item,");
			query.append("inventory_group,request_status,starting_view)");
			query.append(" values (");
			query.append(requestId);
			query.append(",");
			query.append(personnelBean.getPersonnelId());
			query.append(",sysdate,null,'");
			query.append(inputBean.getCatalogId());
			query.append("','");
			query.append(inputBean.getFacilityId());
			query.append("','");
			query.append(inputBean.getCatalogCompanyId());
			query.append("',null,");
			query.append("null,'N','");
			query.append(inputBean.getCompanyId());
			query.append("','Y','");
			query.append(inputBean.getInventoryGroupDefault());
			query.append("',");
			if ("new".equalsIgnoreCase(startingPoint)) {
				query.append("0,0)");
			}else if ("newSize".equalsIgnoreCase(startingPoint)) {
				query.append("1,1)");
			}else {
				query.append("2,2)");
			}
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			e.printStackTrace();
		}
	} //end of method

	private String createTemporaryItemNPartNumber(CatAddHeaderViewBean inputBean) throws Exception {
		String result = "OK";
		try {
			StringBuilder query = new StringBuilder("delete from catalog_add_spec where request_id = ").append(inputBean.getRequestId());
         genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
         if("Y".equals(inputBean.getNoSpec())) {
				query = new StringBuilder("insert into catalog_add_spec(company_id,spec_id,request_id,spec_name,spec_title,coc,coa,spec_library,line_item,spec_source)");
				query.append(" values('").append(inputBean.getCompanyId()).append("','No Specification',").append(inputBean.getRequestId()).append(",'No Specification','No Specification','N','N','global',1,'catalog_add_spec')");
			}else {
				query = new StringBuilder("insert into catalog_add_spec(company_id,spec_id,request_id,spec_name,spec_title,coc,coa,spec_library,line_item,spec_source)");
				query.append(" values('").append(inputBean.getCompanyId()).append("','Std Mfg Cert',").append(inputBean.getRequestId()).append(",'Std Mfg Cert','Std Mfg Cert','Y','N','global',1,'catalog_add_spec')");
			}
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

         //call store procedure to add part to catalog
			Collection cin = new Vector(2);
			cin.add(inputBean.getCompanyId());
			cin.add(inputBean.getRequestId());

			Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.INTEGER));		//item_id
			cout.add(new Integer(java.sql.Types.VARCHAR));     //error value

			//optional
			Collection optIn = new Vector(1);
			optIn.add(inputBean.getItemId());
			if("Y".equals(inputBean.getNoSpec()))
				optIn.add("Y");
			else
				optIn.add("N");

			Collection procedureData = genericSqlFactory.doProcedure(connection,"p_add_catalog_item", cin, cout,optIn);
			Iterator i11 = procedureData.iterator();
			Object tmp = null;
			while (i11.hasNext()) {
				tmp = i11.next();
            }
			if(tmp != null) 
				result = tmp.toString();
			//if item already exist just continue
			if (result.endsWith("is already in HAAS Global")) {
				result = "OK";
			}

			if (!"OK".equalsIgnoreCase(result)) {
				StringBuilder esub = new StringBuilder("Error while calling procedure to add part to catalog (request #");
				esub.append(inputBean.getRequestId());
				esub.append(")");
				StringBuilder emsg = new StringBuilder("Procedure: p_add_catalog_item has an error.\n");
				emsg.append(result);
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
			}else {
				//put part into catalog
				result = putPartInCatalog(inputBean,true);
				//updating catalog_item.part_description
				//don't need to call this anymore since Dave is handling this
				// updateCatalogItemPartDescription(inputBean);
			}
		}catch (Exception e) {
			result = library.getString("error.db.update");
			e.printStackTrace();
			StringBuilder esub = new StringBuilder("Error while processing request (request #");
			esub.append(inputBean.getRequestId());
			esub.append(")");
			String emsg = "Procedure: p_add_catalog_item throw an exception.\n";
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
			throw e;
		}
		return result;
	}

	/*
	private void updateCatalogItemPartDescription(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			//first insert item_id into table
			StringBuilder query = new StringBuilder("insert into customer.catalog_item_desc_worktable");
			query.append(" select item_id from catalog_add_request_new where request_id = ");
			query.append(inputBean.getRequestId());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//call store procedure
			Collection cin = new Vector(0);
			Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.VARCHAR));
			genericSqlFactory.doProcedure(connection,"customer.pkg_part_catalog_item_upd.p_update_item_desc", cin);
		}catch (Exception ee) {
			ee.printStackTrace();
			StringBuilder esub = new StringBuilder("Error while processing request (request #");
			esub.append(inputBean.getRequestId());
			esub.append(")");
			String emsg = "Procedure: global.pkg_part_catalog_item_upd.p_update_item_desc throw an exception.\n";
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
			throw ee;
		}
	}
	*/

	public boolean dataCountIsZero(String query) {
		boolean result = false;
		try {
			String tmpVal = genericSqlFactory.selectSingleValue(query,connection);
			if ("0".equalsIgnoreCase(tmpVal)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    // this method is also called from CatalogAddRequestProcess
    public String putPartInCatalog(CatAddHeaderViewBean inputBean, boolean includeCompany) throws Exception {
		String result = "OK";
		//add part to catalog
		try {
			//call store procedure to add part to catalog
			Collection cin = new Vector(2);
			if (includeCompany) {
				cin.add(inputBean.getCompanyId());
			}
			cin.add(inputBean.getRequestId());
			Collection cout = new Vector();
			cout.add(new Integer(java.sql.Types.VARCHAR));
			if (log.isDebugEnabled()) {
				log.debug(cin);
			}
			Collection procedureData = genericSqlFactory.doProcedure(connection,"p_add_cat_part_request", cin, cout);
			Iterator i11 = procedureData.iterator();
			while (i11.hasNext()) {
				result = (String) i11.next();
			}
            if (!StringHandler.isBlankString(result) && !result.startsWith("OK")) {
				StringBuilder esub = new StringBuilder("Error while calling procedure to add part to catalog (request #");
				esub.append(inputBean.getRequestId());
				esub.append(")");
				StringBuilder emsg = new StringBuilder("Procedure: p_add_cat_part has an error.\n");
				emsg.append(result);
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
			}else {
                result = "OK";
            }
		}catch (Exception ee) {
			result = library.getString("error.db.update");
			ee.printStackTrace();
			StringBuilder esub = new StringBuilder("Error while processing request (request #");
			esub.append(inputBean.getRequestId());
			esub.append(")");
			String emsg = "Procedure: p_add_cat_part throw an exception.\n";
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
			throw ee;
		}
		return result;
	}

    //this method is also called from CatalogAddRequestProcess
    public void updateTemporaryItem(CatAddHeaderViewBean inputBean) throws Exception {
		try {
			//call store procedure to add part to catalog
			Collection cin = new Vector(1);
			cin.add(inputBean.getRequestId());
			Collection cout = new Vector(1);
			cout.add(new Integer(java.sql.Types.VARCHAR));
			Collection procedureData = genericSqlFactory.doProcedure(connection,"pkg_catalog_item.p_update_temporary_item_shell", cin, cout);
			Iterator i11 = procedureData.iterator();
			String val = "";
			while (i11.hasNext()) {
				val = (String) i11.next();
			}
			if (!val.startsWith("OK")) {
				StringBuilder esub = new StringBuilder("Error while calling procedure to update part to catalog (request #");
				esub.append(inputBean.getRequestId());
				esub.append(")");
				StringBuilder emsg = new StringBuilder("Procedure: pkg_catalog_item.p_update_temporary_item_shell has an error.\n");
				emsg.append(val);
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg.toString());
			}
		}catch (Exception ee) {
			ee.printStackTrace();
			StringBuilder esub = new StringBuilder("Error while processing request (request #");
			esub.append(inputBean.getRequestId());
			esub.append(")");
			String emsg = "Procedure: pkg_catalog_item.p_update_temporary_item_shell throw an exception.\n";
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
			throw ee;
		}
	}

	public String submitData(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) throws BaseException {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

			result = doSubmitLogic(inputBean,tabBeans,personnelBean);

		}catch (Exception e) {
			e.printStackTrace();
			result = library.getString("error.db.update");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public String doSubmitLogic(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) throws BaseException {
		String result = "OK";
		boolean notifyApprover = false;
		boolean askUserForMsds = false;
		try {
			if (!updateData(inputBean,tabBeans,personnelBean)) {
				result = library.getString("error.db.update");
			}else {
				//create temporary item and part
				result = createTemporaryItemNPartNumber(inputBean);
                if ("OK".equalsIgnoreCase(result)) {
                    //update catalog add item tables
                    EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
                    engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
					//populate catalog_add_item_locale for request
					engEvalProcess.populateCatalogAddItemLocale(inputBean.getRequestId(),inputBean.getStartingView());
                    //update catalog_add_item_qc and catalog_add_item_orig
                    engEvalProcess.updateCatalogAddItemQcNOrig(inputBean.getRequestId());
                    //update catalog_add_request_new
                    engEvalProcess.updateCatalogAddRequestNew(inputBean,personnelBean);

                    StringBuilder query = null;
					//automatically set request status
					if(inputBean.getStartingView() == null || inputBean.getStartingView().equals(new BigDecimal(0))) {
						query = new StringBuilder("update catalog_add_request_new set submit_date = sysdate, request_status = 5, approval_group_seq = 1");
                        //todo
                        //for now if request is created/submitted on China webserver
                        if (URL.indexOf("://cn") > 0) {
                            query.append(", qc_status = 'Pending Translation'");
                            genericSqlFactory.deleteInsertUpdate ("update catalog_add_item_qc set status = 'Pending Translation' where request_id = "+inputBean.getRequestId()+" and company_id = '"+inputBean.getCompanyId()+"'",connection);
                        }else {
                            query.append(", qc_status = 'Pending SDS Sourcing'");
                        }
                        query.append(" where request_id = ").append(inputBean.getRequestId());
						genericSqlFactory.deleteInsertUpdate (query.toString(),connection);
						askUserForMsds = true;
					}else if (inputBean.getStartingView().equals(new BigDecimal(1))) {
						query = new StringBuilder("update catalog_add_request_new set submit_date = sysdate, request_status = 5, approval_group_seq = 2");
                        //todo
                        //for now if request is created/submitted on China webserver
                        if (URL.indexOf("://cn") > 0) {
                            query.append(", qc_status = 'Pending Translation'");
                            genericSqlFactory.deleteInsertUpdate ("update catalog_add_item_qc set status = 'Pending Translation' where request_id = "+inputBean.getRequestId()+" and company_id = '"+inputBean.getCompanyId()+"'",connection);
                        }else {
                            query.append(", qc_status = 'Pending Item QC'");
                        }
						query.append("  where request_id = ").append(inputBean.getRequestId());
						genericSqlFactory.deleteInsertUpdate (query.toString(),connection);
					}else if (inputBean.getStartingView().equals(new BigDecimal(2))) {
						//check to see if item verified
					   query = new StringBuilder("select fx_item_verified(cai.item_id) item_verified from catalog_add_item cai where line_item = 1 and part_id = 1 and request_id = ");
						query.append(inputBean.getRequestId());
						if ("Y".equalsIgnoreCase(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
							query = new StringBuilder("update catalog_add_request_new set submit_date = sysdate, request_status = 9, approval_group_seq = 0 where request_id = ");
							query.append(inputBean.getRequestId());
							genericSqlFactory.deleteInsertUpdate (query.toString(),connection);
							notifyApprover = false;
						}else {
							query = new StringBuilder("update catalog_add_request_new set submit_date = sysdate, request_status = 5, approval_group_seq = 2");
							//todo
                            //for now if request is created/submitted on China webserver
                            if (URL.indexOf("://cn") > 0) {
                                query.append(", qc_status = 'Pending Translation'");
                                genericSqlFactory.deleteInsertUpdate ("update catalog_add_item_qc set status = 'Pending Translation' where request_id = "+inputBean.getRequestId()+" and company_id = '"+inputBean.getCompanyId()+"'",connection);
                            }else {
                                query.append(", qc_status = 'Pending Item QC'");
                            }
                            query.append("  where request_id = ").append(inputBean.getRequestId());
							genericSqlFactory.deleteInsertUpdate (query.toString(),connection);
						}
					}

					//send user email asking for MSDS
					if (askUserForMsds) {
						//mark comment = MX for temporary item
						query = new StringBuilder("select count(*) from catalog_add_item cai, item where cai.item_id = item.item_id");
						query.append(" and item.item_type = 'MX' and request_id = ");
						query.append(inputBean.getRequestId());
						String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
						if (!"0".equalsIgnoreCase(tmpVal)) {
							query = new StringBuilder("update catalog_add_item_qc set comments = 'MX'");
                            query.append(" where request_id = ").append(inputBean.getRequestId());
							genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
						}
						sendRequestorMsdsEmail(inputBean);
					}

					//sending approvers email
					if (notifyApprover) {
						sendQcApproversEmail(inputBean.getRequestId());
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = library.getString("error.db.update");
		}
		return result;
	}

	private boolean updateData(CatAddHeaderViewBean inputBean, Collection tabBeans, PersonnelBean personnelBean) {
		boolean result = true;
		try {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			//first delete catalog_add_item for this request
            StringBuilder query;
            /*todo need to go back to this when work on China project
            if (!"en_US".equals(this.getLocale())) {
                query = new StringBuilder("delete from catalog_add_item_locale where request_id = ");
                query.append(inputBean.getRequestId());
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
            */
            query = new StringBuilder("delete from catalog_add_item where request_id = ");
			query.append(inputBean.getRequestId());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//next put it back
			Iterator iter = tabBeans.iterator();
			int count = 1;
			while(iter.hasNext())  {
				CatalogAddItemBean catalogAddItemBean = (CatalogAddItemBean)iter.next();
				if (!"closed".equalsIgnoreCase(catalogAddItemBean.getNewTabComponent())) {
                    /*todo need to go back to this when work on China project
                    //if other language then need to create catalog_add_item and catalog_add_item_locale
                    //else create catalog_add_item only
                    if (!"en_US".equals(this.getLocale())) {
                        CatalogAddItemBean catalogAddItemEnUsBean = new CatalogAddItemBean();
                        BeanHandler.copyAttributes(catalogAddItemBean,catalogAddItemEnUsBean);
                        //clear language fields
                        catalogAddItemEnUsBean.setMixtureDesc("");
                        catalogAddItemEnUsBean.setGrade("");
                        catalogAddItemEnUsBean.setDimension("");
                        catalogAddItemEnUsBean.setManufacturer("");
                        catalogAddItemEnUsBean.setMfgTradeName("");
                        catalogAddItemEnUsBean.setMfgCatalogId("");
                        catalogAddItemEnUsBean.setManufacturerContact("");
                        catalogAddItemEnUsBean.setManufacturerNotes("");
                        //create catalog_add_item
                        engEvalProcess.catalogAddItemInsert(inputBean.getRequestId(),catalogAddItemEnUsBean,count);
                        //create catalog_add_item_locale
                        engEvalProcess.catalogAddItemLocaleInsert(inputBean.getRequestId(),catalogAddItemBean,count,this.getLocale());
                    }else {
                        //create catalog_add_item
                        engEvalProcess.catalogAddItemInsert(inputBean.getRequestId(),catalogAddItemBean,count);
                    }
                    */
                    //create catalog_add_item
                    engEvalProcess.catalogAddItemInsert(inputBean.getRequestId(),catalogAddItemBean,count);
                    count++;
                }
			}

		}catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public CatAddHeaderViewBean getNewCatalogItemData(BigDecimal requestId, PersonnelBean personnelBean) throws BaseException {
		CatAddHeaderViewBean catAddHeaderViewBean = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
			//get catalog_add_request_new data
			Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(requestId);
			catAddHeaderViewBean = headerColl.get(0);

			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			//get catalog_add_item data
			catAddHeaderViewBean.setCatalogAddItemColl(engEvalProcess.getCatalogAddItem(requestId));
			//get size units
			catAddHeaderViewBean.setSizeUnitViewColl(engEvalProcess.getSizeUnitView());
			//get net wt size units
			catAddHeaderViewBean.setNetWtSizeUnitColl(engEvalProcess.getNetWtSizeUnit());
			//get shipping weight units
			catAddHeaderViewBean.setShippingWeightUnitColl(engEvalProcess.getShippingWeightSizeUnit(false));
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return catAddHeaderViewBean;
	}

	private Collection getCatAddHeaderView(BigDecimal requestId) throws BaseException {
		genericSqlFactory.setBeanObject(new CatAddHeaderViewBean());
		StringBuilder query = new StringBuilder("select carn.*,cai.item_id, fx_personnel_id_to_name(carn.requestor) requestor_name,fx_personnel_id_to_phone(carn.requestor) requestor_phone,");
		query.append("fx_personnel_id_to_email(carn.requestor) requestor_email from catalog_add_request_new carn, catalog_add_item cai where carn.request_id = ");
		query.append(requestId.toString()).append(" and carn.company_id = cai.company_id and carn.request_id = cai.request_id and cai.line_item = 1 and cai.part_id = 1");
		return genericSqlFactory.selectQuery(query.toString(),connection);
	}

	public void sendRequestorMsdsEmail(CatAddHeaderViewBean inputBean) {
		try {
			Vector<CatAddHeaderViewBean> headerColl = (Vector)getCatAddHeaderView(inputBean.getRequestId());
			inputBean = headerColl.get(0);
			
			StringBuilder esub = new StringBuilder(library.getString("label.sendmsdsto")).append(" (").append(library.getString("label.catalogitem")).append(": ");
			esub.append(inputBean.getItemId()).append(")");
			
            StringBuilder emsg = new StringBuilder(library.getString("label.faxnewMSDSto")).append("\n");
            emsg.append(library.getString("label.usealternatephone")).append("\n");
            emsg.append(library.getString("label.emailmsdsto")).append("\n");
            emsg.append(library.getString("label.enteronfaxcoverpageoremailheader")).append("\n");
            emsg.append(library.getString("label.attn")).append(": ").append(library.getString("label.requestid")).append(" ");
			emsg.append(inputBean.getRequestId());
			String tmpEmailAddress = inputBean.getRequestorEmail();
			if (StringHandler.isBlankString(tmpEmailAddress)) {
				tmpEmailAddress = "deverror@tcmis.com";
			}
			MailProcess.sendEmail(tmpEmailAddress,null,"deverror@tcmis.com",esub.toString(),emsg.toString());
		}catch (Exception e) {
			e.printStackTrace();
			StringBuilder tmp = new StringBuilder("Send requestor MSDS email failed (request ID: ");
			tmp.append(inputBean.getRequestId());
			tmp.append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send email to requestor");
		}
	}

	public void sendQcApproversEmail(BigDecimal requestId) {
		try {
			StringBuilder query = new StringBuilder("select carn.company_id, c.catalog_desc,cai.item_id,carn.engineering_evaluation,carn.cat_part_no,");
			query.append("decode(carn.part_description,null,decode(cai.item_id,null,'',fx_item_desc(cai.item_id,'MA')),carn.part_description) part_desc,");
			query.append("fx_personnel_id_to_name(carn.requestor) requestor_name,fx_personnel_id_to_phone(carn.requestor) phone,f.facility_name,");
			query.append("ca.approval_role,fx_personnel_id_to_name(ca.personnel_id) approver_name,ca.personnel_id approver,");
			query.append("fx_personnel_id_to_email(ca.personnel_id) email");
			query.append(" from catalog_add_request_new carn,catalog_add_item cai,facility_view f,chemical_approver ca,catalog_company_view c");
			query.append(" where carn.company_id = f.company_id and carn.eng_eval_facility_id = f.facility_id and");
			query.append(" carn.company_id = cai.company_id and carn.request_id = cai.request_id and");
			query.append(" carn.company_id = ca.company_id and carn.eng_eval_facility_id = ca.facility_id and");
			query.append(" carn.catalog_company_id = ca.catalog_company_id and carn.catalog_id = ca.catalog_id and");
			query.append(" ca.approval_role = 'Item QC' and ca.active = 'y' and carn.company_id = c.company_id and");
			query.append(" carn.catalog_company_id = c.catalog_company_id and carn.catalog_id = c.catalog_id and");
			query.append(" carn.request_id = ");
			query.append(requestId);

			genericSqlFactory.setBeanObject(new CatalogAddApprovalBean());
			Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Vector approverV = new Vector(20);
			StringBuilder esub = new StringBuilder("");
			StringBuilder emsg = new StringBuilder(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.waitingapproval")).append("\n");
			boolean firstTime = true;
			Iterator iter = dataColl.iterator();
			while (iter.hasNext()){
				CatalogAddApprovalBean caaBean = (CatalogAddApprovalBean)iter.next();
				//header info
				if (firstTime) {
					String requestType = caaBean.getEngineeringEvaluation();
					if ("Y".equalsIgnoreCase(requestType)) {
						esub.append(library.format( "label.yourengevalpending",requestId.toString(),caaBean.getFacilityName()));
					}else {
						esub.append(library.format( "label.yourrequestpending",requestId.toString(),caaBean.getFacilityName()));
					}
					esub.append("(").append(caaBean.getCompanyId()).append(")");
					emsg.append(library.getString("label.requestid")).append("\t\t\t: ").append(requestId).append("\n");
					emsg.append(library.getString("label.requestor")).append("\t\t\t: ").append(caaBean.getRequestorName()).append("\n");
					emsg.append(library.getString("label.phone")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getPhone())).append("\n");
					emsg.append(library.getString("label.facility")).append("\t\t\t: ").append(caaBean.getFacilityName()).append("\n");
					emsg.append(library.getString("label.workarea")).append("\t\t\t: ").append(library.getString("label.allWorkAreas")).append("\n");
					emsg.append(library.getString("label.catalog")).append("\t\t\t\t: ").append(caaBean.getCatalogDesc()).append("\n");
					emsg.append(library.getString("label.itemid")).append("\t\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getItemId())).append("\n");
					emsg.append(library.getString("label.partnumber")).append("\t\t\t: ").append(StringHandler.emptyIfNull(caaBean.getCatPartNo())).append("\n");
					emsg.append(library.getString("label.partdescription")).append("\t\t: ").append(StringHandler.emptyIfNull(caaBean.getPartDesc())).append("\n\n");
					emsg.append(library.getString("label.approvalRole")).append(":\n-----------------------------------------\n");
					firstTime = false;
				}
				//get all approvers email
				if (!approverV.contains(caaBean.getEmail())) {
					approverV.addElement(caaBean.getEmail());
				}

				emsg.append(library.getString("label.name")).append("\t\t\t\t: ").append(caaBean.getApprovalRole()).append("\n");
				emsg.append(library.getString("label.status")).append("\t\t\t\t: ").append(library.getString("label.pending")).append("\n");
				emsg.append(library.getString("label.approver")).append("\t\t\t: ").append(caaBean.getApproverName()).append("\n");
				emsg.append("\n\n");
			}

			//adding link for approvers

			ResourceLibrary resource = new ResourceLibrary("label");
			String hostUrl = resource.getString("label.hosturl");
			
			emsg.append("\n\n"+hostUrl+"/tcmIS/haas/home.do\n");

			//now send out email
			String[] toAddress = new String[approverV.size()];
			for (int j = 0; j < approverV.size(); j++) {
				toAddress[j] = approverV.elementAt(j).toString();
			}
			MailProcess.sendEmail(toAddress,null,"deverror@tcmis.com",esub.toString(),emsg.toString());
		}catch (Exception e) {
			e.printStackTrace();
			StringBuilder tmp = new StringBuilder("Send confirmation email failed (request ID: ");
			tmp.append(requestId).append(")");
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",tmp.toString(),"Unable to send email to chemical approvers");
		}
	}
} //end of class