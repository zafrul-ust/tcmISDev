package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.sql.Connection;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.factory.ExistingCatalogViewBeanFactory;
import com.tcmis.client.catalog.beans.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Process for CatalogAddHmrbProcess
 * @version 1.0
 *****************************************************************************/
public class CatalogAddHmrbProcess extends BaseProcess {

    Log log = LogFactory.getLog(this.getClass());
    private DbManager dbManager;
	private Connection connection = null;
	private GenericSqlFactory genericSqlFactory;

    public CatalogAddHmrbProcess(String client, String locale) {
	    super(client, locale);
    }

    public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
		this.connection = connection;
		this.genericSqlFactory = genericSqlFactory;
	}

    public void setUpdateUseCodeExpiration(CatAddHeaderViewBean inputBean, PersonnelBean personnelBean) throws BaseException {
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("update");
            StringBuilder uaQuery = new StringBuilder("update use_approval ua set");
            if ("part".equals(inputBean.getCalledFrom())) {
                query.append(" fac_part_use_code set");
            }else {
                query.append(" customer_msds_or_mixture_use set");
            }
            boolean updateDate = false;
            if (inputBean.getStartDate() != null && inputBean.getStartDate() != null) {
                query.append(" start_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getStartDate()));
                query.append(",end_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDate()));
                uaQuery.append(" start_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getStartDate()));
                uaQuery.append(",expire_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDate()));
                updateDate = true;
            }else if (inputBean.getStartDate() != null) {
                query.append(" start_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getStartDate()));
                query.append(",end_date = null");
                uaQuery.append(" start_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getStartDate()));
                uaQuery.append(",expire_date = null");
                updateDate = true;
            }else if (inputBean.getEndDate() != null) {
                query.append(" start_date = null");
                query.append(" ,end_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDate()));
                uaQuery.append(" start_date = null");
                uaQuery.append(",expire_date = ").append(DateHandler.getOracleToDateFunction(inputBean.getEndDate()));
                updateDate = true;
            }else {
                query.append(" start_date = null");
                query.append(",end_date = null");
                uaQuery.append(" start_date = null");
                uaQuery.append(",expire_date = null");
            }

            if (updateDate) {
                if ("part".equals(inputBean.getCalledFrom())) {
                    query.append(" where approved_id = ").append(inputBean.getLineItem());
                }else {
                    query.append(" where msds_or_mixture_use_id = ").append(inputBean.getLineItem());
                }
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

                //now update use_approval
                if ("part".equals(inputBean.getCalledFrom())) {
                    uaQuery.append(" where exists (select null from fac_part_use_code fpuc, vv_usage_subcategory vus");
                    uaQuery.append(" where fpuc.approved_id = ").append(inputBean.getLineItem()).append(" and fpuc.company_id = vus.company_id");
                    uaQuery.append(" and fpuc.facility_id = vus.facility_id and fpuc.usage_subcategory_id = vus.usage_subcategory_id");
                    uaQuery.append(" and fpuc.company_id = ua.company_id and fpuc.facility_id = ua.facility_id");
                    uaQuery.append(" and fpuc.catalog_company_id = ua.catalog_company_id and fpuc.catalog_id = ua.catalog_id");
                    uaQuery.append(" and fpuc.cat_part_no = ua.fac_part_no and fpuc.part_group_no = ua.part_group_no");
                    uaQuery.append(" and vus.application_use_group_id = ua.application_use_group_id)");
                    genericSqlFactory.deleteInsertUpdate(uaQuery.toString(),connection);
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
	}

    public void copyHmrb(CatalogAddHmrbBean inputBean) throws BaseException {
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
            copyHmrbToCatalogAddRequest(inputBean);
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}

    public void copyHmrbToCatalogAddRequest(CatalogAddHmrbBean inputBean) throws BaseException {
        try {
            //first copy data from fac_part_approved_XX tables to catalog_add_XX tables
            Vector inArgs = new Vector(3);
            inArgs.add(inputBean.getHmrbLineItem());    //msds_or_mixture_use_id
            inArgs.add(inputBean.getCompanyId());
            inArgs.add(inputBean.getRequestId());
            Vector outArgs = new Vector(2);
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            Vector error = new Vector(0);
            if ("catalogAddMsdsRequest".equals(inputBean.getCalledFrom())) {
                error = (Vector)genericSqlFactory.doProcedure(connection,"pkg_catalog_planned_add.p_copy_cust_msds_to_plan_use", inArgs, outArgs);
            }else {
                error = (Vector)genericSqlFactory.doProcedure(connection,"pkg_catalog_planned_add.p_copy_approved_to_planned_use", inArgs, outArgs);
            }
            if (error.get(1) != null && !"OK".equals(error.get(1))) {
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Copying HRMB line Error","pkg_catalog_planned_add.copy_approved_to_planned_use/p_copy_cust_msds_to_plan_use - Request - "+inputBean.getRequestId().toString()+":"+inputBean.getHmrbLineItem()+"\nplanned_id:"+error.get(0)+"\nerror msg:"+error.get(1));
			}else {
                //set data for loading HMRB
                try {
                    BigDecimal plannedId = new BigDecimal((String)error.get(0));
                    inputBean.setHmrbLineItem(plannedId);
                }catch(Exception ee) {
                    ee.printStackTrace();
                }
            }
        }catch (Exception e) {
			e.printStackTrace();
        }
    } //end of method

    public String submitHmrb(CatalogAddHmrbBean inputBean) throws Exception {
        String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

            Vector inArgs = new Vector();
            if (inputBean.getHmrbLineItem() == null) {
                inArgs.add("");
            }else {
                if (inputBean.getHmrbLineItem().toString().length() > 0) {
                    inArgs.add(inputBean.getHmrbLineItem());
                }else {
                    inArgs.add("");
                }
            }
            inArgs.add(inputBean.getCompanyId());
            inArgs.add(inputBean.getRequestId());
            inArgs.add(inputBean.getUsageSubcategoryId());
            inArgs.add(inputBean.getMaterialSubcategoryId());
            inArgs.add(inputBean.getBeginDateJsp());
            inArgs.add(inputBean.getEndDateJsp());
            if ("Y".equals(inputBean.getMatlFlyAwayWithAircraft())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            if ("Y".equals(inputBean.getMatlThinnedWhenUsed())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            if ("Y".equals(inputBean.getShift1())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            if ("Y".equals(inputBean.getShift2())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            if ("Y".equals(inputBean.getShift3())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            if ("Y".equals(inputBean.getSaturday())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            if ("Y".equals(inputBean.getSunday())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            inArgs.add(inputBean.getSelectedUseDescriptionIds());
            inArgs.add(inputBean.getSelectedUseLocationIds());
            inArgs.add(inputBean.getSelectedSubstrateIds());
            inArgs.add(inputBean.getSelectedBuildingIds());
            inArgs.add(inputBean.getSelectedBoothIds());
            inArgs.add(inputBean.getSelectedPurchasingMethodIds());
            inArgs.add(inputBean.getAdditionalDescription());
            inArgs.add(inputBean.getProcessLocationOtherText());
            inArgs.add(inputBean.getMaxQtyUsePerShift());
            inArgs.add(inputBean.getMaxQtyUsePerShiftUnit());
            if ("Y".equals(inputBean.getGt54Gal())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");
            }
            
            if ("Y".equals(inputBean.getImportFlag())) {
                inArgs.add("Y");
            }
            else if ("U".equals(inputBean.getImportFlag())) {
                inArgs.add("U");
            }
            else {
                inArgs.add("N");
            }
            
            if ("Y".equals(inputBean.getExportFlag())) {
                inArgs.add("Y");
            }else {
                inArgs.add("N");    
            }

            if ("Y".equals(inputBean.getMatlThinnedWhenUsed())) {
                inArgs.add(inputBean.getThinnedCustomerMsdsNumber());
                inArgs.add(inputBean.getThinnedMatlAmountInRatio());
                inArgs.add(inputBean.getThinnerAmountInRatio());
                inArgs.add(inputBean.getThinningUnit());
            }else {
                inArgs.add(null);
                inArgs.add(null);
                inArgs.add(null);
                inArgs.add(null);
            }
            if (!StringHandler.isBlankString(inputBean.getPointOfContact())) {
                inArgs.add(inputBean.getPointOfContact());
            }else {
                inArgs.add(null);
            }
            if (!StringHandler.isBlankString(inputBean.getIntendedProductFormulation())) {
                inArgs.add(inputBean.getIntendedProductFormulation());
            }else {
                inArgs.add(null);
            }
            
            if ("Y".equalsIgnoreCase(inputBean.getShowProgram())) {
                inArgs.add(inputBean.getProgramId());
            }else {
                inArgs.add(null);
            }

            inArgs.add(inputBean.getEstimatedAnnualUsage());
            inArgs.add(inputBean.getEstimatedAnnualUsageUnit());
            
            Vector outArgs = new Vector(1);
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            Vector error = (Vector)genericSqlFactory.doProcedure(connection,"pkg_catalog_planned_add.p_planned_upsert", inArgs, outArgs);
            if (error.get(0) != null && !"OK".equals(error.get(0))) {
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Inserting/Updating HRMB line Error","pkg_catalog_planned_add.p_plan_upsert - Request - "+inputBean.getRequestId().toString()+":"+inputBean.getHmrbLineItem()+"\n"+error.get(0));
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
    
    public void deleteHmrb(CatAddHeaderViewBean inputBean) throws BaseException {
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);

            Vector inArgs = new Vector(1);
            inArgs.add(inputBean.getLineItem());
            Vector outArgs = new Vector(1);
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            Vector error = (Vector)genericSqlFactory.doProcedure(connection,"pkg_catalog_planned_add.p_planned_delete", inArgs, outArgs);
            if (error.get(0) != null && !"OK".equals(error.get(0))) {
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Deleting HRMB line Error","pkg_catalog_planned_add.p_planned_delete - Request - "+inputBean.getRequestId().toString()+":"+inputBean.getLineItem()+"\n"+error.get(0));
			}
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	}

     public CatalogAddHmrbBean getHmrbInfo(CatalogAddHmrbBean inputBean) throws BaseException {
		CatalogAddHmrbBean bean = new CatalogAddHmrbBean();
		try {
            //first copy header data
            bean.setCompanyId(inputBean.getCompanyId());
            bean.setFacilityId(inputBean.getFacilityId());
            bean.setRequestId(inputBean.getRequestId());
            bean.setHmrbLineItem(inputBean.getHmrbLineItem());
            bean.setCalledFrom(inputBean.getCalledFrom());

            dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager);
            //get esh contact
            getEshContactData(inputBean,bean);
            //get usage category
            getUsageCategoryData(inputBean,bean);
            //get material category
            getMaterialCategoryData(inputBean,bean);
            //get use description
            getUseDescriptionData(inputBean,bean);
            //get substrate
            getSubstrateData(inputBean,bean);
            //get use location
            getUseLocationData(inputBean,bean);
            //get building
            getBuildingData(inputBean,bean);
            //get booth
            getBoothData(inputBean,bean);
            //get program
            getProgramData(inputBean,bean);

            //get data for current request if user is editing
            if ("editHmrb".equals(inputBean.getUAction()) ||
                "copyHmrb".equals(inputBean.getUAction()) ||
                "viewHmrb".equals(inputBean.getUAction())) {
                getEditHmrbData(inputBean,bean);
            }

            //max qty per shift unit
            EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),"");
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
            bean.setMaxQtyUsePerShiftUnitColl(engEvalProcess.getShippingWeightSizeUnit(true));

            //purchasing method
            getPurchasingMethodData(inputBean,bean);

        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return bean;
	}

    private void getEshContactData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select esh_contact from catalog_add_request_esh");
            query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and request_id = ").append(inputBean.getRequestId());
            outputBean.setEshContact(genericSqlFactory.selectSingleValue(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void getPurchasingMethodData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select * from purchasing_method");
            query.append(" where company_id = '").append(inputBean.getCompanyId());
            query.append("' and facility_id = '").append(inputBean.getFacilityId());
            query.append("' order by purchasing_method_name");
            genericSqlFactory.setBeanObject(new PurchasingMethodBean());
            outputBean.setPurchasingMethodColl(genericSqlFactory.selectQuery(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void getEditHmrbData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select capu.*,caud.use_id,cas.substrate_id,caul.use_location_id,cab.building_id,cabo.booth_id,capm.purchasing_method_id");
            query.append(",vus.usage_category_id,vms.material_category_id");
            query.append(" from catalog_add_planned_use capu,catalog_add_use_description caud,catalog_add_substrate cas,");
            query.append("catalog_add_use_location caul,catalog_add_building cab,catalog_add_booth cabo, catalog_add_purchasing_method capm");
            query.append(",vv_usage_subcategory vus, vv_material_subcategory vms");
            query.append(" where capu.company_id = vus.company_id and capu.usage_subcategory_id = vus.usage_subcategory_id and capu.company_id = vms.company_id");
            query.append(" and capu.material_subcategory_id = vms.material_subcategory_id");
            query.append(" and capu.planned_id = caud.planned_id(+) and capu.planned_id = cas.planned_id(+)");
            query.append(" and capu.planned_id = caul.planned_id(+) and capu.planned_id = cab.planned_id(+) and capu.planned_id = cabo.planned_id(+)");
            query.append(" and capu.planned_id = capm.planned_id(+)");
            query.append(" and capu.request_id = ").append(inputBean.getRequestId()).append(" and capu.planned_id = ").append(inputBean.getHmrbLineItem());
            genericSqlFactory.setBeanObject(new CatalogAddPlannedUseBean());
            Collection dataColl = genericSqlFactory.selectQuery(query.toString(),connection);
            Iterator iter = dataColl.iterator();
            boolean firstTime = true;
            Vector useIdV = new Vector(dataColl.size());
            String selectedUseIds = "";
            Vector substrateIdV = new Vector(dataColl.size());
            String selectedSubstrateIds = "";
            Vector useLocationIdV = new Vector(dataColl.size());
            String selectedUseLocationIds = "";
            Vector buildingIdV = new Vector(dataColl.size());
            String selectedBuildingIds = "";
            Vector boothIdV = new Vector(dataColl.size());
            String selectedBoothIds = "";
            Vector purchasingMethodIdV = new Vector(dataColl.size());
            String selectedPurchasingMethodIds = "";
            while (iter.hasNext()) {
                CatalogAddPlannedUseBean tmpBean = (CatalogAddPlannedUseBean)iter.next();
                if (firstTime) {
                    copyPlannedUseData(tmpBean,outputBean);
                    outputBean.setBeginDateJsp(tmpBean.getStartDate());
                    outputBean.setEndDateJsp(tmpBean.getEndDate());
                    firstTime = false;
                }
                //get rest of data
                if (tmpBean.getUseId() != null) {
                    if (!useIdV.contains(tmpBean.getUseId())) {
                        selectedUseIds += tmpBean.getUseId()+";";
                        useIdV.add(tmpBean.getUseId());
                    }
                }
                if (tmpBean.getSubstrateId() != null) {
                    if (!substrateIdV.contains(tmpBean.getSubstrateId())) {
                        selectedSubstrateIds += tmpBean.getSubstrateId()+";";
                        substrateIdV.add(tmpBean.getSubstrateId());
                    }
                }
                if (tmpBean.getUseLocationId() != null) {
                    if (!useLocationIdV.contains(tmpBean.getUseLocationId())) {
                        selectedUseLocationIds += tmpBean.getUseLocationId()+";";
                        useLocationIdV.add(tmpBean.getUseLocationId());
                    }
                }
                if (tmpBean.getBuildingId() != null) {
                    if (!buildingIdV.contains(tmpBean.getBuildingId())) {
                        selectedBuildingIds += tmpBean.getBuildingId()+";";
                        buildingIdV.add(tmpBean.getBuildingId());
                    }
                }
                if (tmpBean.getBoothId() != null) {
                    if (!boothIdV.contains(tmpBean.getBoothId())) {
                        selectedBoothIds += tmpBean.getBoothId()+";";
                        boothIdV.add(tmpBean.getBoothId());
                    }
                }
                if (tmpBean.getPurchasingMethodId() != null) {
                    if (!purchasingMethodIdV.contains(tmpBean.getPurchasingMethodId())) {
                        selectedPurchasingMethodIds += tmpBean.getPurchasingMethodId()+";";
                        purchasingMethodIdV.add(tmpBean.getPurchasingMethodId());
                    }
                }
                
            }
            outputBean.setSelectedUseDescriptionIds(selectedUseIds);
            outputBean.setSelectedSubstrateIds(selectedSubstrateIds);
            outputBean.setSelectedUseLocationIds(selectedUseLocationIds);
            outputBean.setSelectedBuildingIds(selectedBuildingIds);
            outputBean.setSelectedBoothIds(selectedBoothIds);
            outputBean.setSelectedPurchasingMethodIds(selectedPurchasingMethodIds);
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void copyPlannedUseData(CatalogAddPlannedUseBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            if (inputBean.getUsageCategoryId() != null) {
                outputBean.setUsageCategoryId(inputBean.getUsageCategoryId().toString());
            }
            if (inputBean.getUsageSubcategoryId() != null) {
                outputBean.setUsageSubcategoryId(inputBean.getUsageSubcategoryId().toString());
            }
            if (inputBean.getMaterialCategoryId() != null) {
                outputBean.setMaterialCategoryId(inputBean.getMaterialCategoryId().toString());
            }
            if (inputBean.getMaterialSubcategoryId() != null) {
                outputBean.setMaterialSubcategoryId(inputBean.getMaterialSubcategoryId().toString());
            }
            if (inputBean.getProgramId() != null) {
                outputBean.setProgramId(inputBean.getProgramId());
            }    
            
            if (inputBean.getEstimatedAnnualUsage() != null) {
                outputBean.setEstimatedAnnualUsage(inputBean.getEstimatedAnnualUsage());
            }
            if (inputBean.getEstimatedAnnualUsageUnit() != null) {
                outputBean.setEstimatedAnnualUsageUnit(inputBean.getEstimatedAnnualUsageUnit());
            }
            
            outputBean.setBeginDateJsp(inputBean.getStartDate());
            outputBean.setEndDateJsp(inputBean.getEndDate());
            outputBean.setHaasPurchased(inputBean.getHaasPurchase());
            outputBean.setMatlFlyAwayWithAircraft(inputBean.getFlyAwayWithAircraft());
            outputBean.setMatlThinnedWhenUsed(inputBean.getThinnedWhenUsed());
            outputBean.setShift1(inputBean.getShift1());
            outputBean.setShift2(inputBean.getShift2());
            outputBean.setShift3(inputBean.getShift3());
            outputBean.setSaturday(inputBean.getSaturday());
            outputBean.setSunday(inputBean.getSunday());
            outputBean.setAdditionalDescription(inputBean.getAdditionalDescription());
            outputBean.setProcessLocationOtherText(inputBean.getOtherLocation());
            outputBean.setMaxQtyUsePerShift(inputBean.getMaxQtyPerShift());
            outputBean.setMaxQtyUsePerShiftUnit(inputBean.getMaxQtyPerShiftUnit());
            outputBean.setGt54Gal(inputBean.getGt54Gal());
            outputBean.setImportFlag(inputBean.getImportFlag());
            outputBean.setExportFlag(inputBean.getExportFlag());
            outputBean.setThinnedCustomerMsdsNumber(inputBean.getThinnedCustomerMsdsNumber());
            outputBean.setThinnedMatlAmountInRatio(inputBean.getThinnedMatlAmountInRatio());
            outputBean.setThinnerAmountInRatio(inputBean.getThinnerAmountInRatio());
            outputBean.setThinningUnit(inputBean.getThinningUnit());
            outputBean.setPointOfContact(inputBean.getPointOfContact());
            outputBean.setIntendedProductFormulation(inputBean.getIntendedProductFormulation());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUseLocationData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select * from vv_use_location");
            query.append(" where company_id = '").append(inputBean.getCompanyId());
            query.append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId());
            query.append("' and catalog_id = '").append(inputBean.getCatalogId());
            query.append("' order by display_order");
            //query.append("' order by decode(use_location_name,'Other',null,use_location_name) nulls last");
            genericSqlFactory.setBeanObject(new VvUseLocationBean());
            outputBean.setUseLocationColl(genericSqlFactory.selectQuery(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void getSubstrateData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select * from vv_substrate");
            query.append(" where company_id = '").append(inputBean.getCompanyId());
            query.append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId());
            query.append("' and catalog_id = '").append(inputBean.getCatalogId());
            query.append("' order by display_order");
            //query.append("' order by decode(substrate_name,'Other',null,substrate_name) nulls last");
            genericSqlFactory.setBeanObject(new VvSubstrateBean());
            outputBean.setSubstrateColl(genericSqlFactory.selectQuery(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void getUseDescriptionData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select * from vv_use_description");
            query.append(" where company_id = '").append(inputBean.getCompanyId());
            query.append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId());
            query.append("' and catalog_id = '").append(inputBean.getCatalogId());
            query.append("' order by display_order");
            //query.append("' order by decode(use_name,'Other',null,use_name) nulls last");
            genericSqlFactory.setBeanObject(new VvUseDescriptionBean());
            outputBean.setUseDescriptionColl(genericSqlFactory.selectQuery(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void getBoothData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select * from vv_booth");
            query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and facility_id = '").append(inputBean.getFacilityId());
            query.append("' order by booth_name");
            genericSqlFactory.setBeanObject(new BoothBean());
            outputBean.setBoothColl(genericSqlFactory.selectQuery(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method
    
    
    private void getProgramData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select * from vv_program");
            query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and facility_id = '").append(inputBean.getFacilityId());
            query.append("' and status = 'A' order by display_order");
            genericSqlFactory.setBeanObject(new VvProgramBean());
            outputBean.setProgramColl(genericSqlFactory.selectQuery(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method
    

    private void getBuildingData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select b.building_id,b.building_name,b.building_description");
            query.append(" from area a, building b").append(" where a.company_id = b.company_id and a.area_id = b.area_id");
            query.append(" and a.company_id = '").append(inputBean.getCompanyId()).append("' and a.facility_id = '").append(inputBean.getFacilityId());
            query.append("' order by b.building_description");
            genericSqlFactory.setBeanObject(new BuildingBean());
            outputBean.setBuildingColl(genericSqlFactory.selectQuery(query.toString(),connection));
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void getMaterialCategoryData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
        	
            if(inputBean.getIsKit())
            	CatalogAddHmrbProcess.removeThinnerHmrbData(inputBean.getRequestId(), inputBean.getCompanyId(), genericSqlFactory, connection);
        	
            StringBuilder query = new StringBuilder("select * from material_category_subcat_view where company_id = '").append(inputBean.getCompanyId());
            query.append("' and catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("' and catalog_id = '").append(inputBean.getCatalogId());
            query.append("' order by material_category_name,material_subcategory_name");
            genericSqlFactory.setBeanObject(new MaterialCategorySubcatViewBean());
            Collection tmpData = genericSqlFactory.selectQuery(query.toString(),connection);
            //build object data
            Vector dataColl = new Vector();
            Iterator iter = tmpData.iterator();
            String lastMaterialCategory = "";
            while (iter.hasNext()) {
                MaterialCategorySubcatViewBean bean = (MaterialCategorySubcatViewBean) iter.next();
                if (lastMaterialCategory.equals(bean.getMaterialCategoryName())) {
                    MaterialCategorySubcatViewBean parentBean = (MaterialCategorySubcatViewBean)dataColl.lastElement();
                    //set to show for Production if any subcategory is marked as show_for_prod
                    if ("Y".equals(bean.getShowForProd())) {
                        parentBean.setShowForProd(bean.getShowForProd());
                    }
                    //set to show for Non-Production if any subcategory is marked as show_for_non_prod
                    if ("Y".equals(bean.getShowForNonProd())) {
                        parentBean.setShowForNonProd(bean.getShowForNonProd());
                    }
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
                lastMaterialCategory = bean.getMaterialCategoryName();
            }
            outputBean.setMaterialCategoryColl(dataColl);
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method
    
    public static void removeThinnerHmrbData(BigDecimal requestId, String companyId, GenericSqlFactory genericSqlFactory, Connection connection) throws Exception
    {
    	StringBuilder removeThinnerHmrbData = new StringBuilder("UPDATE catalog_add_planned_use SET THINNED_CUSTOMER_MSDS_NUMBER = NULL,THINNED_MATL_AMOUNT_IN_RATIO = NULL,THINNED_WHEN_USED = NULL,THINNER_AMOUNT_IN_RATIO = NULL,THINNING_UNIT = NULL where REQUEST_ID = ");
    	removeThinnerHmrbData.append(requestId);
    	removeThinnerHmrbData.append(" and company_id = '").append(companyId).append("'");
    	genericSqlFactory.deleteInsertUpdate(removeThinnerHmrbData.toString(),connection);
    }

    private void getUsageCategoryData(CatalogAddHmrbBean inputBean, CatalogAddHmrbBean outputBean) {
        try {
            StringBuilder query = new StringBuilder("select * from usage_category_subcat_view where company_id = '").append(inputBean.getCompanyId());
            query.append("' and facility_id = '").append(inputBean.getFacilityId()).append("'");
            query.append(" and active = 'Y'");
            query.append(" order by usage_category_name,usage_subcategory_name");
            genericSqlFactory.setBeanObject(new UsageCategorySubcatViewBean());
            Collection tmpData = genericSqlFactory.selectQuery(query.toString(),connection);
            //build object data
            Vector dataColl = new Vector();
            Iterator iter = tmpData.iterator();
            String lastUsageCategory = "";
            while (iter.hasNext()) {
                UsageCategorySubcatViewBean bean = (UsageCategorySubcatViewBean) iter.next();
                if (lastUsageCategory.equals(bean.getUsageCategoryName())) {
                    UsageCategorySubcatViewBean parentBean = (UsageCategorySubcatViewBean)dataColl.lastElement();
                    Collection innerColl = parentBean.getUsageSubCategoryColl();
                    UsageCategorySubcatViewBean childBean = new UsageCategorySubcatViewBean();
                    BeanHandler.copyAttributes(bean, childBean);
                    //clear parent data
                    childBean.setUsageCategoryId(null);
                    childBean.setUsageCategoryName(null);
                    innerColl.add(childBean);
                }else {
                    UsageCategorySubcatViewBean parentBean = new UsageCategorySubcatViewBean();
                    parentBean.setUsageCategoryId(bean.getUsageCategoryId());
                    parentBean.setUsageCategoryName(bean.getUsageCategoryName());
                    parentBean.setShowProgram(bean.getShowProgram());
                    Collection innerColl = new Vector();
                    UsageCategorySubcatViewBean childBean = new UsageCategorySubcatViewBean();
                    BeanHandler.copyAttributes(bean, childBean);
                    //clear parent data
                    childBean.setUsageCategoryId(null);
                    childBean.setUsageCategoryName(null);
                    innerColl.add(childBean);
                    parentBean.setUsageSubCategoryColl(innerColl);
                    dataColl.add(parentBean);
                }
                lastUsageCategory = bean.getUsageCategoryName();
            }
            outputBean.setUsageCategoryColl(dataColl);
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

} //end of class