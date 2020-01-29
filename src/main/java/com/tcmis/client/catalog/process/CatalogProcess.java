package com.tcmis.client.catalog.process;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.*;
import com.tcmis.client.catalog.factory.PrCatalogScreenSearchBeanFactory;
import com.tcmis.client.catalog.factory.SpecDisplayViewBeanFactory;
import com.tcmis.client.catalog.factory.PkgInventoryDetailWebPrInventoryDetailBeanFactory;
import com.tcmis.client.common.beans.ChargeNumberDisplayViewBean;
import com.tcmis.client.common.beans.FacAccountSysPoBean;
import com.tcmis.client.common.beans.ItemCatalogViewBean;
import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;
import com.tcmis.client.common.beans.PrRulesViewBean;
import com.tcmis.client.common.process.ItemCatalogProcess;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.common.process.MsdsViewerProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.admin.beans.FacilityBean;
import com.tcmis.common.admin.beans.FeatureReleaseBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.UserGroupMemberBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class CatalogProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public CatalogProcess(String client) {
		super(client);
	}

	public CatalogProcess(String client,String locale) {
		super(client,locale);
	}

	public CatalogProcess(String client,Locale locale) {
		super(client,locale);
	}

    public String getFacLocAppHoldAsCustomerOwned(CatalogInputBean bean) {
        String result = "N";
        StringBuilder query = new StringBuilder("");
		try {
            if (!StringHandler.isBlankString(bean.getApplicationId()) && !"My Work Areas".equals(bean.getApplicationId())) {
                DbManager dbManager = new DbManager(getClient(),this.getLocale());
			    GenericSqlFactory factory = new GenericSqlFactory(dbManager);
                query.append("select hold_as_customer_owned from fac_loc_app where company_id = '").append(bean.getCompanyId()).append("'");
                query.append(" and facility_id = '").append(bean.getFacilityId()).append("' and application = '").append(bean.getApplicationId()).append("'");
                result = factory.selectSingleValue(query.toString());
            }
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error getting fac_loc_app.hold_as_customer_owned",query.toString());
		}
        return result;
    }	//end of method

    public String showDirectedCharge(PrCatalogScreenSearchBean bean) throws BaseException {
        String result = "N";
        DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select count(*) from directed_charge");
        query.append(" where company_id = '"+bean.getCompanyId()+"' and facility_id = '"+bean.getFacilityId()).append("'");
		query.append(" and application = '").append(bean.getApplicationId()).append("'");
        query.append(" and catalog_company_id = '").append(bean.getCatalogCompanyId()).append("'");
        query.append(" and catalog_id = '").append(bean.getCatalogId()).append("'");
        query.append(" and cat_part_no = '").append(bean.getCatPartNo()).append("'");
        query.append(" and part_group_no = ").append(bean.getPartGroupNo());
        if (!"0".equalsIgnoreCase(factory.selectSingleValue(query.toString()))) {
            result = "Y";
        }
        return result;
	} //end om method

    public String addGrandfatheredMaterial(CatalogInputBean inputBean) throws BaseException {
		String result = "OK";
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			StringBuilder query = new StringBuilder("select use_seq.nextval from dual");
            String approvalCodeId = genericSqlFactory.selectSingleValue(query.toString(),connection);
            //insert data into table
            query = new StringBuilder("insert into customer_msds_or_mixture_use (msds_or_mixture_use_id,company_id,facility_id,customer_msds_db,");
            query.append("customer_msds_or_mixture_no,usage_subcategory_id,material_subcategory_id)");
            query.append(" select distinct ").append(approvalCodeId).append(",a.company_id,a.facility_id,'").append(inputBean.getCustomerMsdsDb());
            query.append("','").append(inputBean.getCustMsdsNo()).append("',a.usage_subcategory_id,b.material_subcategory_id");
            query.append(" from vv_usage_subcategory a, vv_material_subcategory b where a.company_id = b.company_id");
            query.append(" and a.type_of_use = b.material_subcategory_name and a.type_of_use = 'Grandfathered data'");
            query.append(" and a.company_id = '").append(inputBean.getCompanyId()).append("' and a.facility_id = '").append(inputBean.getFacilityId()).append("'");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
			e.printStackTrace();
            result = "Failed";
        }finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

    public String getFacLocAppChargeTypeDefault(CatalogInputBean bean) {
        String result = "";
        StringBuilder query = new StringBuilder("");
		try {
            if (!StringHandler.isBlankString(bean.getApplicationId()) && !"My Work Areas".equals(bean.getApplicationId())) {
                DbManager dbManager = new DbManager(getClient(),this.getLocale());
			    GenericSqlFactory factory = new GenericSqlFactory(dbManager);
                query.append("select charge_type_default from fac_loc_app where company_id = '").append(bean.getCompanyId()).append("'");
                query.append(" and facility_id = '").append(bean.getFacilityId()).append("' and application = '").append(bean.getApplicationId()).append("'");
                result = factory.selectSingleValue(query.toString());
            }
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Error getting fac_loc_app.charge_type_default",query.toString());
		}
        return result;
    }	//end of method
    
    public String isFeatureReleasedAnyFacility(String feature) {
		String result = "N";
		StringBuilder query = null;
		try {
			query = new StringBuilder("select count (*) from feature_release where company_id = '");
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			String companyId = dbManager.getConnection().getMetaData().getUserName();
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			query.append(companyId).append("' and feature = '").append(feature).append("' and active = 'Y'");
			result = Integer.valueOf(factory.selectSingleValue(query.toString())) > 0 ? "Y":null;
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","fx_feature_released failed",query.toString());
		}
		return result;
    }

    //the reason that this method is still exist is because ViewMsdsAction is using it and user is not required to log in
    //so I can't use personnelBean.isFeatureReleased
    public String isFeatureReleased(String facilityId, String feature) {
        return isFeatureReleased("",facilityId,feature);
    }

    public String isFeatureReleased(String companyId, String facilityId, String feature) {
		String result = "N";
		StringBuilder query = new StringBuilder("");
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			query.append("select fx_feature_released('").append(feature).append("','").append(facilityId).append("'");
            if (!StringHandler.isBlankString(companyId))
                query.append(",'").append(companyId).append("'");
			query.append(") released from dual");
			result = factory.selectSingleValue(query.toString());
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","fx_feature_released failed",query.toString());
		}
		return result;
	}	//end of method

	public String validateChargeNumbers(CatalogInputBean inputBean) throws BaseException {
		String result = "OK";
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			//validate charge numbers
			MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),dbManager,connection,genericSqlFactory);
			MaterialRequestInputBean mrInputBean = new MaterialRequestInputBean();
			mrInputBean.setCompanyId(inputBean.getCompanyId());
			mrInputBean.setAccountSysId(inputBean.getAccountSysId());
			mrInputBean.setChargeType(inputBean.getChargeType());
			mrInputBean.setChargeNumbers(inputBean.getChargeNumbers());
            mrInputBean.setIgnoreNullChargeNumber(inputBean.getIgnoreNullChargeNumber());
            if(!mrProcess.validateChargeNumbers(mrInputBean)) {
                //invalid charge numbers
				result = "Failed";          //this value is use in chargeNumberSetupProcess to do specific thing
			}else {
                String tmpVal = mrProcess.additionalChargeNumberValidation(mrInputBean);
                if (!"OK".equals(tmpVal)) {
                    result = tmpVal;    
                }
            }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public PointOfSaleAccountSysInfoBean getPosAccountSysChargeNumberPoData(CatalogInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		PointOfSaleAccountSysInfoBean resultBean = new PointOfSaleAccountSysInfoBean();
		try {
			//get pr_rules
			factory.setBeanObject(new PrRulesViewBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS,inputBean.getFacilityId());
			criteria.addCriterion("accountSysId", SearchCriterion.EQUALS,inputBean.getAccountSysId());
			SortCriteria sort = new SortCriteria();
			sort.addCriterion("accountSysId");
			sort.addCriterion("chargeType");
			Collection prRulesColl = factory.select(criteria,sort,connection,"pr_rules_view");
			resultBean.setPrRulesColl(prRulesColl);
			//need to get account sys info and defined charge numbers
			Iterator iter = prRulesColl.iterator();
			while(iter.hasNext()) {
				PrRulesViewBean tmpBean = (PrRulesViewBean)iter.next();
				//get POs if required
				if ("p".equalsIgnoreCase(tmpBean.getPoRequired())) {
					factory.setBeanObject(new FacAccountSysPoBean());
					criteria = new SearchCriteria();
					criteria.addCriterion("status", SearchCriterion.EQUALS,"A");
					criteria.addCriterion("facilityId", SearchCriterion.EQUALS,inputBean.getFacilityId());
					criteria.addCriterion("accountSysId", SearchCriterion.EQUALS,inputBean.getAccountSysId());
					sort = new SortCriteria();
					sort.addCriterion("poNumber");
					if ("d".equalsIgnoreCase(tmpBean.getChargeType())) {
						resultBean.setFacAccountSysPoForDirectColl(factory.select(criteria,sort,connection,"fac_account_sys_po"));
					}else {
						resultBean.setFacAccountSysPoForIndirectColl(factory.select(criteria,sort,connection,"fac_account_sys_po"));
					}
				}
				//get charge number if required
				if ("y".equalsIgnoreCase(tmpBean.getPrAccountRequired())) {
					if ("y".equalsIgnoreCase(tmpBean.getChargeDisplay1()) || "y".equalsIgnoreCase(tmpBean.getChargeDisplay2())) {
						factory.setBeanObject(new ChargeNumberDisplayViewBean());
						criteria = new SearchCriteria();
						criteria.addCriterion("facilityId", SearchCriterion.EQUALS,inputBean.getFacilityId());
						criteria.addCriterion("accountSysId", SearchCriterion.EQUALS,inputBean.getAccountSysId());
						criteria.addCriterion("status", SearchCriterion.EQUALS,"A");
						criteria.addCriterion("chargeType", SearchCriterion.EQUALS,tmpBean.getChargeType());
						criteria.addCriterion("rownum", SearchCriterion.LESS_THAN_OR_EQUAL_TO,tmpBean.getMaxChargeNumberToDisplay().toString());
						sort = new SortCriteria();
						sort.addCriterion("chargeNumber1");
						sort.addCriterion("chargeNumber2");
						if ("d".equalsIgnoreCase(tmpBean.getChargeType())) {
							resultBean.setChargeNumberForDirectColl(factory.select(criteria,sort,connection,"charge_number_display_view"));
						}else {
							resultBean.setChargeNumberForIndirectColl(factory.select(criteria,sort,connection,"charge_number_display_view"));
						}
					}
				} //end of charge number is required
			} //end of while loop
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}

		return resultBean;
	} //end of method

	public PointOfSaleAccountSysInfoBean getPosAccountSysData(CatalogInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		PointOfSaleAccountSysInfoBean resultBean = new PointOfSaleAccountSysInfoBean();
		try {
			//get all work areas with directed_charge
			StringBuilder query = new StringBuilder("select distinct dc.application from directed_charge dc, fac_loc_app fla where dc.account_sys_id = '").append(inputBean.getAccountSysId());
			query.append("' and dc.facility_id = '").append(inputBean.getFacilityId()).append("'");
			query.append(" and dc.company_id = fla.company_id and dc.facility_id = fla.facility_id and dc.application = fla.application and fla.status = 'A' and dc.cat_part_no is null");
            query.append(" and nvl(fla.charge_type_default,'x') <> 'n'");
            factory.setBeanObject(new FacLocAppBean());
			Collection directedChargeAppColl = factory.selectQuery(query.toString(),connection);
			resultBean.setDirectedChargeAppColl(directedChargeAppColl);
			//get pr_rules
			factory.setBeanObject(new PrRulesViewBean());
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS,inputBean.getFacilityId());
			criteria.addCriterion("accountSysId", SearchCriterion.EQUALS,inputBean.getAccountSysId());
			SortCriteria sort = new SortCriteria();
			sort.addCriterion("accountSysId");
			sort.addCriterion("chargeType");
			Collection prRulesColl = factory.select(criteria,sort,connection,"pr_rules_view");
			resultBean.setPrRulesColl(prRulesColl);

			//next check to see if current work area is in the directed charge number list
			Iterator iter = directedChargeAppColl.iterator();
			boolean applicationUseDirectedCharge = false;
			while(iter.hasNext()) {
				FacLocAppBean flaBean = (FacLocAppBean)iter.next();
				if (inputBean.getApplicationId().equals(flaBean.getApplication())) {
					applicationUseDirectedCharge = true;
					break;
				}
			}
			if (applicationUseDirectedCharge) {
				//then limit this MR to only work areas with directed charge number
				resultBean.setWorkAreaOption("directed");
			}else {
				resultBean.setWorkAreaOption("nonDirected");
			} //end of not directed charge

			String financeApproverRequired = "N";
			iter = prRulesColl.iterator();
			while(iter.hasNext()) {
				PrRulesViewBean tmpBean = (PrRulesViewBean)iter.next();
				//if any type required finance approver then it required financial approver
				if ("y".equalsIgnoreCase(tmpBean.getApproverRequired())) {
					financeApproverRequired = "Y";
					break;
				}
			}
			if ("Y".equalsIgnoreCase(financeApproverRequired)) {
				//get point of sales requestor ordering limit
				query = new StringBuilder("select decode(cost_limit,null,0,-1,'Unlimited',cost_limit) cost_limit from finance_approver_list");
				query.append(" where facility_id = '").append(inputBean.getFacilityId()).append("' and personnel_id = ").append(inputBean.getPosRequestorId());
				resultBean.setOrderingLimit(factory.selectSingleValue(query.toString(),connection));
			}
			resultBean.setFinanceApproverRequired(financeApproverRequired);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			factory = null;
		}

		return resultBean;
	} //end of method



	public Collection getCatalogFacility(CatalogFacilityBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatalogFacilityBean());
		StringBuilder query = new StringBuilder("select * from catalog c, catalog_facility cf");
		query.append(" where c.company_id = cf.company_id and c.catalog_company_id = cf.catalog_company_id");
		query.append(" and c.catalog_id = cf.catalog_id");
		if (!StringHandler.isBlankString(inputBean.getCompanyId())) {
			query.append(" and cf.company_id = '").append(inputBean.getCompanyId()).append("'");
		}
		if (!StringHandler.isBlankString(inputBean.getCatalogCompanyId())) {
			query.append(" and c.catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("'");
		}
		if (!StringHandler.isBlankString(inputBean.getCatalogId())) {
			query.append(" and c.catalog_id = '").append(inputBean.getCatalogId()).append("'");
		}
		if (!StringHandler.isBlankString(inputBean.getFacilityId())) {
			query.append(" and cf.facility_id = '").append(inputBean.getFacilityId()).append("'");
		}
		query.append(" order by c.catalog_desc");
		return factory.selectQuery(query.toString());
	}

	public Collection getPrRulesForFacility(CatalogInputBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PrRulesViewBean());
        String query = "select distinct account_sys_id,nvl(account_sys_desc,account_sys_id) account_sys_desc,account_sys_label,fac_item_charge_type_override from pr_rules_view where facility_id = '"+bean.getFacilityId()+"'"+
		" and status = 'A' order by account_sys_desc,account_sys_id";
		return factory.selectQuery(query);
	}

	public Collection getSearchResult(CatalogInputBean bean,PersonnelBean user) throws BaseException {
		return getSearchResult(bean,user,true);
	}
	
	public Collection getSearchResult(CatalogInputBean bean) throws BaseException {
		return getSearchResult(bean,null,true);
	}

	public Collection getSearchResult(CatalogInputBean bean,PersonnelBean user, boolean searchFlag) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		PrCatalogScreenSearchBeanFactory factory = new PrCatalogScreenSearchBeanFactory(dbManager);
		if (bean.getCatalog() !=null && bean.getCatalog().startsWith("POS:")) {
			return factory.selectPos(bean, user.getPersonnelIdBigDecimal());
		}else {
			return factory.select(bean, user);
		}
	}

	public Collection getSpecMenu(PrCatalogScreenSearchBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());
		SpecDisplayViewBeanFactory factory = new SpecDisplayViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		SortCriteria sriteria = new SortCriteria();
		criteria.addCriterion("catalogId", SearchCriterion.EQUALS,bean.getCatalogId());
		criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,bean.getCatPartNo());
		sriteria.addCriterion("specId");
		return factory.select(criteria,sriteria);
	}

	public Object getInventoryMenu(PrCatalogScreenSearchBean bean) throws BaseException {
        HashMap result = new HashMap();
		Vector itemInv = new Vector();
		Vector itemID = new Vector();
        BigDecimal partInv = new BigDecimal(0);

        DbManager dbManager = new DbManager(getClient(), getLocale());
		PkgInventoryDetailWebPrInventoryDetailBeanFactory factory = new PkgInventoryDetailWebPrInventoryDetailBeanFactory(dbManager);
        String partGroupNo = "1";
        if (bean.getPartGroupNo() != null) {
            partGroupNo = bean.getPartGroupNo().toString();
        }
        Iterator iter = factory.select(bean.getCatPartNo(), bean.getInventoryGroup(), bean.getCatalogId(), partGroupNo, bean.getCatalogCompanyId(),"popupFromCatalog").iterator();
		while (iter.hasNext()) {
            InventoryDetailOnHandMaterialBean tmpBean = (InventoryDetailOnHandMaterialBean)iter.next();
            BigDecimal qty = tmpBean.getQuantity();
            itemID.addElement(tmpBean.getItemId());
            itemInv.addElement(qty);
            partInv = partInv.add(qty);
        }

        result.put("partInventory", partInv);
		result.put("itemId", itemID);
		result.put("itemQty", itemInv);
        return result;
    }
	public Object getStockingReorder(PrCatalogScreenSearchBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),this.getLocale());

		PrCatalogScreenSearchBeanFactory factory = new
		PrCatalogScreenSearchBeanFactory(dbManager);

		return factory.selectStockingReorder(bean);
	}

	public Object getImgLit(PrCatalogScreenSearchBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());

		PrCatalogScreenSearchBeanFactory factory = new
		PrCatalogScreenSearchBeanFactory(dbManager);

		return factory.selectImgLit(bean);
	}

	public String getKitMsdsNumber(PrCatalogScreenSearchBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select m.customer_mixture_number from mixture m, item_mixture im, catalog_company cc");
        query.append(" where cc.company_id = '"+bean.getCompanyId()+"' and cc.catalog_company_id = '"+bean.getCatalogCompanyId()).append("'");
		query.append(" and cc.catalog_id = '").append(bean.getCatalogId()).append("'");
		query.append(" and im.item_id = ").append(bean.getItemId()).append(" and cc.company_id = im.company_id and cc.customer_msds_db = im.customer_msds_db");
        query.append(" and im.company_id = m.company_id and im.customer_msds_db = m.customer_msds_db and im.mixture_id = m.mixture_id");
        return factory.selectSingleValue(query.toString());
	}
	
	public Collection getRequestIdColl(PrCatalogScreenSearchBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select * from cat_add_part_use_code_view");
		query.append(" where catalog_id = '").append(bean.getCatalogId()).append("'");
		query.append(" and cat_part_no = '").append(bean.getCatPartNo()).append("'");

        return factory.setBean(new CatAddReqUseCodesBean()).selectQuery(query.toString());
	}
	
	public Collection getRequestIdCollWithMaterialId(PrCatalogScreenSearchBean bean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select distinct request_id,application_use_group_name,start_date,end_date from cat_add_msds_use_code_view ");
        query.append(" where company_id = '"+bean.getCompanyId()+"'");
        if (!StringHandler.isBlankString(bean.getKitMsdsNumber())) {
            query.append(" and customer_mixture_number = ").append(SqlHandler.delimitString(bean.getKitMsdsNumber()));
        }else if (!StringHandler.isBlankString(bean.getComponentMsdsNumber())) {
            query.append(" and customer_mixture_number is null and customer_msds_number = ").append(SqlHandler.delimitString(bean.getComponentMsdsNumber()));
        }else {
            query.append(" and material_id = ").append(bean.getMaterialId()).append("");
        }

        return factory.setBean(new CatAddReqUseCodesBean()).selectQuery(query.toString());
	}


	public ExcelHandler  getItemExcelReport(CatalogInputBean inputbean, BigDecimal personnelId) throws
	NoDataException, BaseException, Exception {

		//Vector<PrCatalogScreenSearchBean> data = (Vector<PrCatalogScreenSearchBean>) getSearchResult(inputbean, personnelId, false);
		ItemCatalogProcess itemCatalogProcess = new ItemCatalogProcess(this.getClient(),this.getLocale());
		Vector<ItemCatalogViewBean> data = (Vector)itemCatalogProcess.searchItemCatalog(inputbean,personnelId);


		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);


		pw.addTable();

		//	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.facility")+":"+inputbean.getFacilityName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.workarea")+":"+inputbean.getApplicationDesc(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.searchtype")+":"+inputbean.getSearchTypeName(),1,3);
		pw.addRow();
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = {
				"label.item",
				"inventory.label.componentdescription",
				"inventory.label.packaging",
				"label.grade",
				"label.manufacturer",
				"label.country",
				"label.mfgpartno",
		"catalog.label.shelflife"};
		/*This array defines the type of the excel column.
 0 means default depending on the data type. */
		int[] types = {
				0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,0,pw.TYPE_PARAGRAPH,0,0,pw.TYPE_PARAGRAPH};
		/*This array defines the default width of the column when the Excel file opens.
 0 means the width will be default depending on the data type.*/
		int[] widths = {0,0,0,10,0,10,10,0};
		/*This array defines the horizontal alignment of the data in a cell.
 0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

		for(ItemCatalogViewBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getItemId());
			pw.addCell(bean.getMaterialDesc());
			pw.addCell(bean.getPackaging());
			pw.addCell(bean.getGrade());
			pw.addCell(bean.getMfgDesc());
			pw.addCell(bean.getCountryName());
			pw.addCell(bean.getMfgPartNo());
			pw.addCell(bean.getShelfLife());
		}
		return pw;
	}

	public ExcelHandler  getMSDSExcelReport(CatalogInputBean inputbean, Vector<MsdsSearchDataTblBean>  data, PersonnelBean personnel) throws
	NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.facility")+":"+inputbean.getFacilityName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.workarea")+":"+inputbean.getApplicationDesc(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.searchtype")+":"+inputbean.getSearchTypeName(),1,3);
		pw.addRow();
		pw.addRow();

        boolean showMsds = personnel.isFeatureReleased("ShowKitMaterialMsds", "ALL",inputbean.getCompanyId());
        MsdsViewerProcess msdsProcess = new MsdsViewerProcess(this.getClient(),this.getLocale());
        boolean showApprovalCode = "Y".equals(msdsProcess.showFacilityUseCode(inputbean.getFacilityId()));

        /*Pass the header keys for the Excel.*/
        ArrayList<String> hk = new ArrayList<String>();
        if (showMsds) {
            hk.add("label.kitmsds");
            hk.add("label.kitdesc");
        }
        if (showApprovalCode)
            hk.add("label.approvalcode");
        if (showMsds)
            hk.add("label.msds");
        hk.add("label.materialdesc");
        if (showMsds) {
            hk.add("label.kitmixratio");
            hk.add("label.kitmixratiouom");
        }
        hk.add("label.materialid");
        hk.add("label.manufacturer");
        hk.add("label.tradename");


		/*This array defines the type of the excel column.
        0 means default depending on the data type. */
		int[] types = new int[hk.size()];
        int tmpIndex = 0;
        if (showMsds) {
            types[tmpIndex++] = 0;
            types[tmpIndex++] = pw.TYPE_PARAGRAPH;
        }
        if (showApprovalCode)
            types[tmpIndex++] = 0;
        if (showMsds)
            types[tmpIndex++] = 0;
        types[tmpIndex++] = pw.TYPE_PARAGRAPH;
        if (showMsds) {
            types[tmpIndex++] = 0;
            types[tmpIndex++] = 0;
        }
        types[tmpIndex++] = 0;
        types[tmpIndex++] = pw.TYPE_PARAGRAPH;
        types[tmpIndex++] = pw.TYPE_PARAGRAPH;

        /*This array defines the default width of the column when the Excel file opens.
          0 means the width will be default depending on the data type.*/
		int[] widths = null;
		/*This array defines the horizontal alignment of the data in a cell.
          0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;

        String [] headerkeys = new String[hk.size()];
		headerkeys = hk.toArray(headerkeys);
        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		for(MsdsSearchDataTblBean bean: data) {
			pw.addRow();
            if (showMsds) {
                pw.addCell(bean.getCustomerMixtureNumber());
                pw.addCell(bean.getMixtureDesc());
            }
            if (showApprovalCode)
                pw.addCell(bean.getApprovalCode());
            if (showMsds)
                pw.addCell(bean.getCustomerMsdsNumber());
            pw.addCell(bean.getMaterialDesc());
            if (showMsds) {
                pw.addCell(bean.getMixRatioAmount());
                pw.addCell(bean.getMixRatioSizeUnit());
            }
            pw.addCell(bean.getMaterialId());
            pw.addCell(bean.getMfgDesc());
            pw.addCell(bean.getTradeName());
		}
		return pw;
	}

	public ExcelHandler  getPartExcelReport(CatalogInputBean inputbean, PersonnelBean personnel) throws
	NoDataException, BaseException, Exception {

		Vector<PrCatalogScreenSearchBean> data = (Vector<PrCatalogScreenSearchBean>) getSearchResult(inputbean, personnel, false);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);

		HashSet<String> qualityIdLabelSet = new HashSet();
		HashSet<String> catPartAttrHeaderSet = new HashSet();
		Vector<PrCatalogScreenSearchBean> bv = (Vector<PrCatalogScreenSearchBean>) data;

		for (PrCatalogScreenSearchBean pbean:bv) {
			if( !StringUtils.isEmpty(pbean.getQualityIdLabel()) )
				qualityIdLabelSet.add(pbean.getQualityIdLabel());
			if( !StringUtils.isEmpty(pbean.getCatPartAttributeHeader()) )
				catPartAttrHeaderSet.add(pbean.getCatPartAttributeHeader());
		}
		// if Hide, then no show column
		// if empty then appened ( header ) to value and header is empty
		// else if all the same, used as header.
		String qualityIdLabelColumnHeader = "--Hide--"; 
		String catPartAttrColumnHeader = "--Hide--"; 
		if( qualityIdLabelSet.size() == 1 ) {
			for(String s:qualityIdLabelSet) 
				qualityIdLabelColumnHeader = s;
		}
		if( qualityIdLabelSet.size() > 1 ) {
			qualityIdLabelColumnHeader = " ";  	//adding empty space because grid will not display column if empty
			for (PrCatalogScreenSearchBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getQualityIdLabel() ) && StringUtils.isNotEmpty(pbean.getQualityId() )) 
						pbean.setQualityId(pbean.getQualityId() +" ("+pbean.getQualityIdLabel()+")");
			}
		}
		if( catPartAttrHeaderSet.size() == 1 )
			for(String s:catPartAttrHeaderSet) 
				catPartAttrColumnHeader = s;
		if( catPartAttrHeaderSet.size() > 1 ) {
			catPartAttrColumnHeader = " "; 		//adding empty space because grid will not display column if empty
			for (PrCatalogScreenSearchBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getCatPartAttributeHeader() ) && StringUtils.isNotEmpty(pbean.getCatPartAttribute() ))  
						pbean.setCatPartAttribute(pbean.getCatPartAttribute()+" ("+pbean.getCatPartAttributeHeader()+")");
			}
		}

		pw.addTable();

        //	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.facility")+":"+inputbean.getFacilityName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.workarea")+":"+inputbean.getApplicationDesc(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.searchtype")+":"+inputbean.getSearchTypeName(),1,3);
		pw.addRow();
		if( inputbean.getFacilityOrAllCatalog().equals("All Catalogs") ) {
			pw.addThRegion("label.allcatalogs",1,2);
			if( inputbean.getActiveOnly() != null ) {
				pw.addRow();
				pw.addThRegion("label.activeOnly",1,2);
			}
		}
		else {
			pw.addThRegion("label.activeforFacility",1,2);
			if( inputbean.getWorkAreaApprovedOnly() != null ) {
				pw.addRow();
				pw.addThRegion("catalog.label.workAreaApprovedOnly",1,2);
			}
		}

		pw.addRow();
		pw.addRow();

		/*Pass the header keys for the Excel.*/

		boolean hideCatalogPrice = personnel.isFeatureReleased("HideCatalogPrice", inputbean.getFacilityId(),inputbean.getCompanyId());
        boolean showMsds = personnel.isFeatureReleased("ShowKitMaterialMsds", "ALL",inputbean.getCompanyId());
        boolean hideCatalogCol = personnel.isFeatureReleased("HideCatalogCol", "ALL",inputbean.getCompanyId());
        boolean includeObsoleteParts = inputbean.getIncludeObsoleteParts() != null && inputbean.getIncludeObsoleteParts().trim().length() > 0 ? true:false;
		boolean showPartRevision = personnel.isFeatureReleased("ShowPartRevision", inputbean.getFacilityId(),inputbean.getCompanyId());

		int partDescPosition = 0;
		int pricePosition = 0;
		ArrayList<String> hk = new ArrayList<String>();
		if(!hideCatalogCol) {
			hk.add("label.catalog");
			partDescPosition++;
			pricePosition++;
		}
		hk.add("label.part");
		partDescPosition++;
		pricePosition++;
		if (showPartRevision) {
			hk.add("label.revision");
			partDescPosition++;
			pricePosition++;
		}
		hk.add("label.description");
		pricePosition++;
		hk.add("label.type");
		pricePosition++;
		if(!hideCatalogPrice)
		{
			hk.add("label.price");
            hk.add("label.currencyid");
        }
		hk.add("label.partuom");
		hk.add("catalog.label.unitOfSaleQuantityPerEach");
		hk.add("catalog.label.shelflife");
		hk.add("label.item");
        if (showMsds)
            hk.add("label.kitmsds");
        hk.add("inventory.label.componentdescription");
		hk.add("label.grade");
		hk.add("inventory.label.packaging");
		hk.add("label.manufacturer");
		hk.add("label.mfgpartno");
		hk.add("label.status");
        if (showMsds)
            hk.add("label.msds");

        String [] headerkeys = new String[hk.size()];
		headerkeys = hk.toArray(headerkeys);

		/*This array defines the type of the excel column.
 		 0 means default depending on the data type. */
		int [] types = {0,0,0,0};
		types[partDescPosition]=pw.TYPE_PARAGRAPH;
		/*This array defines the default width of the column when the Excel file opens.
 		 0 means the width will be default depending on the data type.*/
		int[] widths = null;
		/*This array defines the horizontal alignment of the data in a cell.
 		 0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = null;
		if (!hideCatalogPrice)
			pw.setColumnDigit(pricePosition,4); // final price 4 digit

		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

	if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
			pw.addCellBold(qualityIdLabelColumnHeader);
	if(!"--Hide--".equals(catPartAttrColumnHeader) )
			pw.addCellBold(catPartAttrColumnHeader);
		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		for(PrCatalogScreenSearchBean bean: data) {
			pw.addRow();
			if(!hideCatalogCol)
				pw.addCell(bean.getCatalogDesc());
			pw.addCell(bean.getCatPartNo());
			if (showPartRevision)
				pw.addCell(bean.getCustomerPartRevision());
			pw.addCell(bean.getPartDescription());
			pw.addCell(bean.getStockingMethod());
			if(!hideCatalogPrice) {
				pw.addCell(bean.getCatalogPrice());
                pw.addCell(bean.getCurrencyId());
            }
			pw.addCell(bean.getUnitOfSale());
			pw.addCell(bean.getQtyOfUomPerItem());
			String storageTemp = bean.getStorageTemp();
			if( storageTemp == null || storageTemp.trim().length() == 0 )
				storageTemp = "";
			else {
				if( ! "Indefinite".equals(bean.getShelfLife() ) ) {
					String shelfBasis = bean.getShelfLifeBasis();
					if( shelfBasis != null && shelfBasis.length() != 0 )
						storageTemp = bean.getShelfLife() + " " + bean.getShelfLifeBasis() + "@" + storageTemp;
				}
					else
						storageTemp = bean.getShelfLife() + " @" + storageTemp;				
			}
			pw.addCell(storageTemp);

            String tmpItem = "";
            if (bean.getItemId() != null)
                tmpItem = bean.getItemId().toString();
            if(includeObsoleteParts)
				if(bean.getPartStatus().equalsIgnoreCase("O"))
					tmpItem +=  "("+library.getString("label.obsolete")+")";
				else if(bean.getPartStatus().equalsIgnoreCase("D"))
				    tmpItem +=  "("+library.getString("label.fadeout")+")";

            pw.addCell(tmpItem);
            if (showMsds)
                pw.addCell(bean.getKitMsdsNumber());
            pw.addCell(bean.getMaterialDesc());
            pw.addCell(bean.getGrade());
            pw.addCell(bean.getPackaging());
			pw.addCell(bean.getMfgDesc());
			pw.addCell(bean.getMfgPartNo());
            if(includeObsoleteParts)
				if(bean.getPartStatus().equalsIgnoreCase("O"))
					pw.addCell(library.getString("label.obsolete"));
				else
					pw.addCell(bean.getApprovalStatus());
            else
            	pw.addCell(bean.getApprovalStatus());
            if (showMsds)
                pw.addCell(bean.getComponentMsdsNumber());
			if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
				pw.addCell(bean.getQualityId());//"<TD ROWSPAN=" + mainRowSpan + ">" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getQtyOfUomPerItem()) + "</TD>");
			if(!"--Hide--".equals(catPartAttrColumnHeader) )
				pw.addCell(bean.getCatPartAttribute());

        }
		return pw;
	}
	
	public Object[] createRelationalObjects(Vector<PrCatalogScreenSearchBean> bv) {
		return createRelationalObjects(false, bv);
	}

	public Object[] createRelationalObjects(boolean checkObsoleteItems, Vector<PrCatalogScreenSearchBean> bv) {
		//		Vector bv = (Vector) this.getSearchResult(inputBean);
		// calculate m1 and m2
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		HashSet<String> qualityIdLabelSet = new HashSet();
		HashSet<String> catPartAttrHeaderSet = new HashSet();
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		String partNum = null;
		
		boolean resetAllStatusToObsolete = true;
		int totalBeanCount = 0;
		String obsoleteLocale = null;
		if(checkObsoleteItems)
			obsoleteLocale = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject()).getLabel("label.obsolete");

		for (PrCatalogScreenSearchBean pbean:bv) {
			if( !StringUtils.isEmpty(pbean.getQualityIdLabel()) )
				qualityIdLabelSet.add(pbean.getQualityIdLabel());
			if( !StringUtils.isEmpty(pbean.getCatPartAttributeHeader()) )
				catPartAttrHeaderSet.add(pbean.getCatPartAttributeHeader());
			partNum = pbean.getCatPartNo()+pbean.getCatalogId()+pbean.getCatalogCompanyId()+pbean.getPartGroupNo();
			//			System.out.println(partNum);

			if (m1.get(partNum) == null) {
				i1 = new Integer(0);
				m1.put(partNum, i1);
				map = new HashMap();
				m2.put(partNum, map);
			}
			i1 = (Integer) m1.get(partNum);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(partNum, i1);

			String itemId = "itemId" + pbean.getItemId();

			if (map.get(itemId) == null) {
				i2 = new Integer(0);
				map.put(itemId, i2);
			}
			i2 = (Integer) map.get(itemId);
			i2 = new Integer(i2.intValue() + 1);
			map.put(itemId, i2);
			
			if(checkObsoleteItems)
			{
				if(!pbean.getPartStatus().equalsIgnoreCase("O"))
					resetAllStatusToObsolete = false;

				if(totalBeanCount + 1 < bv.size())
				{
					PrCatalogScreenSearchBean nextBean = bv.get(totalBeanCount + 1);
					String nextBeanPartNum = nextBean.getCatPartNo()+nextBean.getCatalogId()+nextBean.getCatalogCompanyId()+nextBean.getPartGroupNo();
					if(partNum.equalsIgnoreCase(nextBeanPartNum) == false)
					{
						if(resetAllStatusToObsolete)
						{
							int backTrackindex = totalBeanCount - (i1-1);
							for(int i = 0; i < i1; i++)
							{
								bv.get(backTrackindex++).setApprovalStatus(obsoleteLocale);
							}
						}
						resetAllStatusToObsolete = true;
					}
					totalBeanCount++;
				}
				else
				{
					if(resetAllStatusToObsolete)
					{
						int backTrackindex = totalBeanCount - (i1-1);
						for(int i = 0; i < i1; i++)
						{
							bv.get(backTrackindex++).setApprovalStatus(obsoleteLocale);
						}
					}
				}
			}
			
			
		}
		// if Hide, then no show column
		// if empty then appened ( header ) to value and header is empty
		// else if all the same, used as header.
		String qualityIdLabelColumnHeader = "--Hide--"; 
		String catPartAttrColumnHeader = "--Hide--"; 
		if( qualityIdLabelSet.size() == 1 ) {
			for(String s:qualityIdLabelSet) 
				qualityIdLabelColumnHeader = s;
		}
		if( qualityIdLabelSet.size() > 1 ) {
			qualityIdLabelColumnHeader = ""; 
			for (PrCatalogScreenSearchBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getQualityIdLabel() ) && StringUtils.isNotEmpty(pbean.getQualityId() )) 
						pbean.setQualityId(pbean.getQualityId() +" ("+pbean.getQualityIdLabel()+")");
			}
		}
		if( catPartAttrHeaderSet.size() == 1 )
			for(String s:catPartAttrHeaderSet) 
				catPartAttrColumnHeader = s;
		if( catPartAttrHeaderSet.size() > 1 ) {
			catPartAttrColumnHeader = ""; 
			for (PrCatalogScreenSearchBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getCatPartAttributeHeader() ) && StringUtils.isNotEmpty(pbean.getCatPartAttribute() ))  
						pbean.setCatPartAttribute(pbean.getCatPartAttribute()+" ("+pbean.getCatPartAttributeHeader()+")");
			}
		}
		Object[] objs = {bv,m1,m2,qualityIdLabelColumnHeader,catPartAttrColumnHeader};
		return objs;
	}

	public Collection createRelationalObject(Collection
			pkgInventoryDetailWebPrInventoryBeanCollection) {

		Collection finalpkgInventoryDetailWebPrInventoryBeanCollection = new Vector();
		String nextPartNumber = "";
		String nextItem = "";

		int samePartNumberCount = 0;
		Vector collectionVector = new Vector(
				pkgInventoryDetailWebPrInventoryBeanCollection);
		Vector itemIdV1 = new Vector();
		Vector materialIdV1 = new Vector();
		for (int loop = 0; loop < collectionVector.size(); loop++) {

			PrCatalogScreenSearchBean currentPrCatalogScreenSearchBean = (
					PrCatalogScreenSearchBean) collectionVector.elementAt(loop);
			String currentPartNumber = currentPrCatalogScreenSearchBean.getCatPartNo()+currentPrCatalogScreenSearchBean.getCatalogId()+
			currentPrCatalogScreenSearchBean.getCatalogCompanyId()+currentPrCatalogScreenSearchBean.getPartGroupNo();
			String currentItem = "" + currentPrCatalogScreenSearchBean.getItemId() + "";

			if (! ( (loop + 1) == collectionVector.size())) {
				PrCatalogScreenSearchBean nextPrCatalogScreenSearchBean = (
						PrCatalogScreenSearchBean) collectionVector.elementAt(loop + 1);

				nextPartNumber = nextPrCatalogScreenSearchBean.getCatPartNo()+nextPrCatalogScreenSearchBean.getCatalogId()+
				nextPrCatalogScreenSearchBean.getCatalogCompanyId()+nextPrCatalogScreenSearchBean.getPartGroupNo();
				nextItem = "" + nextPrCatalogScreenSearchBean.getItemId() + "";
			}
			else {
				nextPartNumber = "";
				nextItem = "";
			}

			boolean samePartNumber = false;
			boolean sameItemId = false;

			if (currentPartNumber.equalsIgnoreCase(nextPartNumber)) {
				samePartNumber = true;
				samePartNumberCount++;
				if (nextItem.equalsIgnoreCase(currentItem)) {
					sameItemId = true;
				}
			}

			PrCatalogScreenSearchComponentBean prCatalogScreenSearchComponentBean = new
			PrCatalogScreenSearchComponentBean();
			prCatalogScreenSearchComponentBean.setMaterialDesc(
					currentPrCatalogScreenSearchBean.getMaterialDesc());
			prCatalogScreenSearchComponentBean.setMaterialId(
					currentPrCatalogScreenSearchBean.getMaterialId());
			prCatalogScreenSearchComponentBean.setMfgDesc(
					currentPrCatalogScreenSearchBean.getMfgDesc());
			prCatalogScreenSearchComponentBean.setMfgPartNo(
					currentPrCatalogScreenSearchBean.getMfgPartNo());
			prCatalogScreenSearchComponentBean.setMsdsOnLine(
					currentPrCatalogScreenSearchBean.getMsdsOnLine());
			prCatalogScreenSearchComponentBean.setPackaging(
					currentPrCatalogScreenSearchBean.getPackaging());
			prCatalogScreenSearchComponentBean.setGrade(
					currentPrCatalogScreenSearchBean.getGrade());

			materialIdV1.add(prCatalogScreenSearchComponentBean);

			if (sameItemId) {

			}
			else {
				PrCatalogScreenSearchItemBean prCatalogScreenSearchItemBean = new
				PrCatalogScreenSearchItemBean();
				prCatalogScreenSearchItemBean.setComponentCollection( (Vector) materialIdV1.
						clone());
				materialIdV1 = new Vector();

				prCatalogScreenSearchItemBean.setItemId(currentPrCatalogScreenSearchBean.
						getItemId());
				prCatalogScreenSearchItemBean.setApprovalStatus(
						currentPrCatalogScreenSearchBean.getApprovalStatus());
				prCatalogScreenSearchItemBean.setBundle(currentPrCatalogScreenSearchBean.
						getBundle());

				itemIdV1.add(prCatalogScreenSearchItemBean);
			}

			if (samePartNumber) {

			}
			else {
				currentPrCatalogScreenSearchBean.setItemCollection( (Vector) itemIdV1.clone());
				itemIdV1 = new Vector();
				currentPrCatalogScreenSearchBean.setRowSpan(new BigDecimal(
						samePartNumberCount + 1));

				finalpkgInventoryDetailWebPrInventoryBeanCollection.add(
						currentPrCatalogScreenSearchBean);
				samePartNumberCount = 0;
			}
		}

		//log.info("Final collectionSize here " + finalpkgInventoryDetailWebPrInventoryBeanCollection.size() + "");
		return finalpkgInventoryDetailWebPrInventoryBeanCollection;

	}


	public void writeExcelFile(Collection searchCollection, String filePath,
			CatalogInputBean bean,java.util.Locale locale) throws BaseException, Exception {
//		DbManager dbManager = new DbManager(getClient());
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		HashSet<String> qualityIdLabelSet = new HashSet();
		HashSet<String> catPartAttrHeaderSet = new HashSet();
		Vector<PrCatalogScreenSearchBean> bv = (Vector<PrCatalogScreenSearchBean>) searchCollection;
		for (PrCatalogScreenSearchBean pbean:bv) {
			if( !StringUtils.isEmpty(pbean.getQualityIdLabel()) )
				qualityIdLabelSet.add(pbean.getQualityIdLabel());
			if( !StringUtils.isEmpty(pbean.getCatPartAttributeHeader()) )
				catPartAttrHeaderSet.add(pbean.getCatPartAttributeHeader());
		}
		// if Hide, then no show column
		// if empty then appened ( header ) to value and header is empty
		// else if all the same, used as header.
		String qualityIdLabelColumnHeader = "--Hide--"; 
		String catPartAttrColumnHeader = "--Hide--"; 
		if( qualityIdLabelSet.size() == 1 ) {
			for(String s:qualityIdLabelSet) 
				qualityIdLabelColumnHeader = s;
		}
		if( qualityIdLabelSet.size() > 1 ) {
			qualityIdLabelColumnHeader = ""; 
			for (PrCatalogScreenSearchBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getQualityIdLabel() ) && StringUtils.isNotEmpty(pbean.getQualityId() )) 
						pbean.setQualityId(pbean.getQualityId() +" ("+pbean.getQualityIdLabel()+")");
			}
		}
		if( catPartAttrHeaderSet.size() == 1 )
			for(String s:catPartAttrHeaderSet) 
				catPartAttrColumnHeader = s;
		if( catPartAttrHeaderSet.size() > 1 ) {
			catPartAttrColumnHeader = ""; 
			for (PrCatalogScreenSearchBean pbean:bv) {
				if( StringUtils.isNotEmpty(pbean.getCatPartAttributeHeader() ) && StringUtils.isNotEmpty(pbean.getCatPartAttribute() ))  
						pbean.setCatPartAttribute(pbean.getCatPartAttribute()+" ("+pbean.getCatPartAttributeHeader()+")");
			}
		}

		pw.addTable();
		boolean allCatalog = false;
		if (bean.getFacilityOrAllCatalog() != null &&
				bean.getFacilityOrAllCatalog().equalsIgnoreCase("All Catalogs")) {
			allCatalog = true;
		}

		pw.addRow();
		pw.addCellKeyBold("label.facility");
		pw.addCell(bean.getFacilityId());

		pw.addRow();
		pw.addCellKeyBold("label.workarea");
		pw.addCell(bean.getApplicationId());
		pw.addRow();
		pw.addCellKeyBold("label.search");
		pw.addCell(bean.getSearchText());
		pw.addRow();
		pw.addRow();
		//write column headers

		pw.addCellKeyBold("label.catalog");
		pw.addCellKeyBold("label.part");
		pw.addCellKeyBold("label.description");
		pw.addCellKeyBold("label.type");
		pw.addCellKeyBold("label.price");
		pw.addCellKeyBold("catalog.label.shelflife");
		/*pw.addCellKeyBold(Part UOM</TH>");
	pw.addCellKeyBold(Qty UOM per Item</TH>");*/
		pw.addCellKeyBold("catalog.label.qtyofuomperitem");
		pw.addCellKeyBold("label.status");
		pw.addCellKeyBold("label.item");
		pw.addCellKeyBold("label.componentdescription");
		pw.addCellKeyBold("label.packaging");
		pw.addCellKeyBold("label.grade");
		pw.addCellKeyBold("label.manufacturer");
		pw.addCellKeyBold("label.mfgpartno");

		if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
				pw.addCellBold(qualityIdLabelColumnHeader);
		if(!"--Hide--".equals(catPartAttrColumnHeader) )
				pw.addCellBold(catPartAttrColumnHeader);
		DecimalFormat valuesFormat = new DecimalFormat("####.00##");
		valuesFormat.setMinimumFractionDigits(2);

		//print rows
		Iterator i11 = searchCollection.iterator();
		while (i11.hasNext()) {
			pw.addTr();

			PrCatalogScreenSearchBean prCatalogScreenSearchBean = (
					PrCatalogScreenSearchBean) i11.next(); ;

					int mainRowSpan = prCatalogScreenSearchBean.getRowSpan().intValue();
					Collection itemCollection = prCatalogScreenSearchBean.getItemCollection();

					pw.addTdRegion(prCatalogScreenSearchBean.getCatalogDesc(),mainRowSpan,1);//"<TD ROWSPAN=" + mainRowSpan + ">" +prCatalogScreenSearchBean.getCatalogId() + "</TD>");
					pw.addTdRegion(prCatalogScreenSearchBean.getCatPartNo(),mainRowSpan,1);//"<TD ROWSPAN=" + mainRowSpan + ">" +prCatalogScreenSearchBean.getCatPartNo() + "</TD>");
					pw.addTdRegion(prCatalogScreenSearchBean.getPartDescription(),mainRowSpan,1);//"<TD ROWSPAN=" + mainRowSpan + ">" +prCatalogScreenSearchBean.getPartDescription() + "</TD>");
					pw.addTdRegion(prCatalogScreenSearchBean.getStockingMethod(),mainRowSpan,1);//"<TD ROWSPAN=" + mainRowSpan + ">" + prCatalogScreenSearchBean.getStockingMethod() + "</TD>");

					BigDecimal finalPrice = new BigDecimal("0");
					BigDecimal minCatalogPrice = prCatalogScreenSearchBean.getMinCatalogPrice();
					BigDecimal maxCatalogPrice = prCatalogScreenSearchBean.getMaxCatalogPrice();

					if (allCatalog) {
						if (minCatalogPrice != null && maxCatalogPrice != null) {
							finalPrice = maxCatalogPrice;
						}
						else if (minCatalogPrice != null) {
							finalPrice = minCatalogPrice;
						}
						else if (maxCatalogPrice != null) {
							finalPrice = maxCatalogPrice;
						}
					}
					else {
						finalPrice = minCatalogPrice;
					}
					String str = null;

					if (finalPrice == null || finalPrice.setScale(2, 3).floatValue() == 0) {

					}
					else {
						str = valuesFormat.format(finalPrice) + " " + prCatalogScreenSearchBean.getCurrencyId();
					}
					pw.addTdRegion(str,mainRowSpan,1);//pw.addTd("<TD ROWSPAN=" + mainRowSpan + ">");

					String storageTemp = prCatalogScreenSearchBean.getStorageTemp();
					String fnialShelfLife = "";
					if (storageTemp != null && storageTemp.length() > 0) {
						if (!"Indefinite".equalsIgnoreCase(prCatalogScreenSearchBean.getShelfLife())) {
							if (prCatalogScreenSearchBean.getShelfLifeBasis() != null) {
								fnialShelfLife = prCatalogScreenSearchBean.getShelfLife() +
								prCatalogScreenSearchBean.getShelfLifeBasis() + " @ " + storageTemp;
							}
							else {
								fnialShelfLife = prCatalogScreenSearchBean.getShelfLife() + " @ " +
								storageTemp;
							}
						}
						else {
							fnialShelfLife = prCatalogScreenSearchBean.getShelfLife() + " @ " +
							storageTemp;
						}
					}
					else {
						fnialShelfLife = "";
					}

					pw.addTdRegion(fnialShelfLife,mainRowSpan,1);//("<TD ROWSPAN=" + mainRowSpan + ">" + fnialShelfLife + "</TD>");
					/*pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.
		getUnitOfSale()) + "</TD>");
	 pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
		com.tcmis.common.util.StringHandler.emptyIfZero(com.tcmis.common.util.
		NumberHandler.zeroBigDecimalIfNull(prCatalogScreenSearchBean.
		getUnitOfSaleQuantityPerEach())) + "</TD>");*/
					pw.addTdRegion(prCatalogScreenSearchBean.getQtyOfUomPerItem(),mainRowSpan,1);//"<TD ROWSPAN=" + mainRowSpan + ">" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getQtyOfUomPerItem()) + "</TD>");
					pw.addTdRegion(prCatalogScreenSearchBean.getApprovalStatus(),mainRowSpan,1);//"<TD ROWSPAN=" + mainRowSpan + ">" +	com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getApprovalStatus()) + "</TD>");

					int itemCount = 0;
					Iterator i11Item = itemCollection.iterator();
					while (i11Item.hasNext()) {
						itemCount++;
						PrCatalogScreenSearchItemBean prCatalogScreenSearchItemBean = (
								PrCatalogScreenSearchItemBean) i11Item.next(); ;
								if (itemCount > 1 ) {
									// for 8 columns are merged
									pw.addTr();
									for(int j = 8; j-- != 0;)
										pw.addTdEmpty();
								}

								Collection componenetCollection = prCatalogScreenSearchItemBean.
								getComponentCollection();

								int componenetSize = componenetCollection.size();

								pw.addTdRegion(""+prCatalogScreenSearchItemBean.getItemId(),componenetSize,1);//("<TD ROWSPAN=" + componenetSize + ">" + prCatalogScreenSearchItemBean.getItemId() + "</TD>");

								int componenetCount = 0;
								Iterator i11Allocation = componenetCollection.iterator();
								while (i11Allocation.hasNext()) {
									componenetCount++;
									PrCatalogScreenSearchComponentBean prCatalogScreenSearchComponentBean = (
											PrCatalogScreenSearchComponentBean) i11Allocation.next(); ;

											if (componenetCount > 1 ) {
												pw.addTr();
												pw.addTdEmpty();
												for(int j = 8; j-- != 0;)
													pw.addTdEmpty();
											}

											pw.addTd(prCatalogScreenSearchComponentBean.getMaterialDesc());//"<TD>" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean. getMaterialDesc()) + "</TD>");

											pw.addTd(prCatalogScreenSearchComponentBean.getPackaging());//"<TD>" +	com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.	getPackaging()) + "</TD>");

											pw.addTd(prCatalogScreenSearchComponentBean.getGrade());//"<TD>" +	com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.getGrade()) + "</TD>");

											pw.addTd(prCatalogScreenSearchComponentBean.getMfgDesc());//"<TD>" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.getMfgDesc()) + "</TD>");

											pw.addTd(prCatalogScreenSearchComponentBean.getMfgPartNo());//"<TD>" +	com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchComponentBean.	getMfgPartNo()) + "</TD>");

											if (componenetCount > 1 || componenetSize == 1) {
												//pw.println("</TR>");
											}
								}

								if (itemCount > 1) {
									//pw.println("</TR>");
								}
					}
					if(!"--Hide--".equals(qualityIdLabelColumnHeader) )
						pw.addTdRegion(prCatalogScreenSearchBean.getQualityId(),mainRowSpan,1);//"<TD ROWSPAN=" + mainRowSpan + ">" + com.tcmis.common.util.StringHandler.emptyIfNull(prCatalogScreenSearchBean.getQtyOfUomPerItem()) + "</TD>");
					if(!"--Hide--".equals(catPartAttrColumnHeader) )
						pw.addTdRegion(prCatalogScreenSearchBean.getCatPartAttribute(),mainRowSpan,1);
		} // end print row loop.
		pw.write(new FileOutputStream(filePath));
	}

	public Boolean isAllowSplitKits(String facilityId) throws BaseException {
		GenericSqlFactory generateSqlFactory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		return "Y".equals(generateSqlFactory.selectSingleValue("select ALLOW_MANAGE_SPLIT_KITS from facility where facility_id = '" + facilityId + "'"));
	}

    public Collection getActiveFeatureRelease(PersonnelBean personnelBean) throws BaseException {
        Collection result = new Vector(0);
        StringBuilder query = new StringBuilder("");
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FeatureReleaseBean());
			query.append("select * from feature_release where active = 'Y' and company_id = '").append(personnelBean.getCompanyId()).append("'");
            query.append(" and scope in (select 'ALL' from dual union all select facility_id from user_facility where personnel_id = ").append(personnelBean.getPersonnelId());
            query.append(") order by scope,feature");
			result = factory.selectQuery(query.toString());
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Get active feature release",query.toString());
		}
        return result;
    } //end of method

    public Collection getUserGroupMemberForCatalogScreen(PersonnelBean personnelBean) throws BaseException {
        Collection result = new Vector(0);
        StringBuilder query = new StringBuilder("");
		try {
			DbManager dbManager = new DbManager(getClient(),this.getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new UserGroupMemberBean());
			query.append("select ugm.* from user_group_member ugm, user_facility uf where ugm.company_id = '").append(personnelBean.getCompanyId()).append("'");
            query.append(" and ugm.personnel_id = ").append(personnelBean.getPersonnelId());
            query.append(" and ugm.user_group_id in ('CreateNewChemical','FullFacilityCatalogSearch')");
            query.append(" and ugm.company_id = uf.company_id and ugm.facility_id = uf.facility_id and ugm.personnel_id = uf.personnel_id");
            query.append(" order by ugm.facility_id,ugm.user_group_id");
			result = factory.selectQuery(query.toString());
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Get active user group member",query.toString());
		}
        return result;
    } //end of method

	public int getDefaultLeadTime(String companyId) throws BaseException {
		GenericSqlFactory generateSqlFactory = new GenericSqlFactory(new DbManager(getClient(), getLocale()));
		String defaultLeadTime = generateSqlFactory.selectSingleValue("select default_lead_time from company where company_id = '" + companyId+ "'");
		int leadtime = 0;
		if (defaultLeadTime != null && !defaultLeadTime.equalsIgnoreCase(""))
			leadtime = new BigDecimal(defaultLeadTime).intValue();  
		return leadtime;
	}
	
	public String getProp65Flag(String facilityId) throws BaseException {
		String defaultFlag = "N";
		
		if(!StringHandler.isBlankString(facilityId) && !facilityId.toUpperCase().equals("ALL")){
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			StringBuilder query = new StringBuilder("");
			query.append("select case when exists ");
			query.append("       (select 1 from facility_dock where dock_location_id in ");
			query.append("          (select location_id from location where state_abbrev = 'CA')");
			query.append("          and status = 'A'");
			query.append("          and facility_id  = ").append(SqlHandler.delimitString(facilityId)).append(")");
			query.append("       then 'Y' ");
			query.append("       else 'N' ");
			query.append("       end as result ");
			query.append("from dual");
					
			return factory.selectSingleValue(query.toString());
		}
		
		return defaultFlag;
	}
	
	public ExcelHandler  getShelfLifeStorTempSplashExcelReport(CatalogInputBean inputbean) throws
	NoDataException, BaseException, Exception {

		Vector<PrCatalogScreenSearchBean> data = (Vector<PrCatalogScreenSearchBean>) getSearchResult(inputbean, null, false);
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

        //	 write column headers
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.facility")+":"+inputbean.getFacilityName(),1,3);
		pw.addRow();
		pw.addTdRegionBold(library.getString("label.workarea")+":"+inputbean.getApplicationDesc(),1,3);
		pw.addRow();
		pw.addRow();
		
		String[] headerkeys = { "label.part", "label.description", "catalog.label.shelflife", "inventory.label.componentdescription", "label.manufacturer", "label.mfgpartno", "catalogspec.label.spec", "transactions.source"};

		/*This array defines the type of the excel column.
		0 means default depending on the data type.
		pw.TYPE_PARAGRAPH defaults to 40 characters.
		pw.TYPE_CALENDAR set up the date with no time.
		pw.TYPE_DATE set up the date with time.*/
		int[] types = { 0, pw.TYPE_PARAGRAPH , 0, pw.TYPE_PARAGRAPH , 0, 0, 0, 0 };

		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/
		int[] widths = { 15, 35, 25, 35, 35, 25, 25, 15};
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0 };


		pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		//	 now write data
		//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		for(PrCatalogScreenSearchBean bean: data) {
			pw.addRow();
			pw.addCell(bean.getCatPartNo());
			pw.addCell(bean.getPartDescription());
			String storageTemp = bean.getStorageTemp();
			if(inputbean.isFacilityOrAllShelflife()) {
				storageTemp = bean.getShelfLifeList();
			}
			else if( storageTemp == null || storageTemp.trim().length() == 0 )
				storageTemp = "";
			else {
				if( ! "Indefinite".equals(bean.getShelfLife() ) ) {
					String shelfBasis = bean.getShelfLifeBasis();
					if( shelfBasis != null && shelfBasis.length() != 0 )
						storageTemp = bean.getShelfLife() + " " + bean.getShelfLifeBasis() + "@" + storageTemp;
				}
					else
						storageTemp = bean.getShelfLife() + " @" + storageTemp;				
			}
			pw.addCell(storageTemp);
            pw.addCell(bean.getMaterialDesc());
			pw.addCell(bean.getMfgDesc());
			pw.addCell(bean.getMfgPartNo());
			pw.addCell(bean.getSpecs());
			pw.addCell(bean.getSource());
        }
		return pw;
	}
	
	

} //end of class