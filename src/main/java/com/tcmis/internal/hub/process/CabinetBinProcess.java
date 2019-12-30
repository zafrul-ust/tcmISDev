package com.tcmis.internal.hub.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.sql.Connection;

import org.apache.commons.logging.*;

import com.tcmis.client.catalog.factory.CatalogFacilityBeanFactory;
import com.tcmis.client.catalog.factory.UseApprovalBeanFactory;
import com.tcmis.client.common.beans.CabinetManagementBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class CabinetBinProcess extends GenericProcess {
  Log log = LogFactory.getLog(this.getClass());

	private DbManager dbManager;
	private Connection connection = null;
	private ResourceLibrary library = null;
    GenericSqlFactory genericSqlFactory;

  public CabinetBinProcess(String client) {
    super(client);
  }

	public CabinetBinProcess(String client,String locale)  {
	    super(client,locale);
	}

  public Collection getCatalogs(CabinetBinInputBean bean) throws BaseException, Exception {
    DbManager dbManager = new DbManager(getClient());
    CatalogFacilityBeanFactory factory = new CatalogFacilityBeanFactory(dbManager);
	 SearchCriteria criteria = new SearchCriteria("facilityId", SearchCriterion.EQUALS, bean.getFacilityId());
	 criteria.addCriterion("display",SearchCriterion.EQUALS,"Y");
	 return factory.select(criteria, new SortCriteria("catalogId"));
  }

	private boolean dataCountIsZero(String query) {
		boolean result = false;
		try {
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			String tmpVal = factory.selectSingleValue(query,connection);
			if ("0".equalsIgnoreCase(tmpVal)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String addPart(CabinetBinInputBean bean, PersonnelBean personnelBean) throws BaseException, Exception {
        String result = "";
        try {
			library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
			if (StringHandler.isBlankString(bean.getApplication()) && bean.getApplicationId() != null) {
				StringBuilder query = new StringBuilder("select application from fac_loc_app where company_id = '").append(bean.getCompanyId());
				query.append("' and facility_id = '").append(bean.getFacilityId()).append("' and application_id = ").append(bean.getApplicationId());
				bean.setApplication(genericSqlFactory.selectSingleValue(query.toString(),connection));
			}

            if(this.isPartDefinedInCabinet(bean)) {
				result = library.getString("error.part.definedinworkarea");
			}else {
                bean.setStatus("A");
                bean.setCatPartNo(bean.getFacPartNo());
                //insert data into cabinet_part_inventory
                result = insertCabinetPartInventory(bean,personnelBean);
                if (StringHandler.isBlankString(result)) {
                    if ("NotCounted".equals(bean.getCountType())) {
                        //insert data into cabinet_part_inventory_item
                        insertCabinetPartInventoryItem(bean,personnelBean);
                    }

                    //notify user of newly added part
                    CabinetLevelInputBean cabinetLevelInputBean = new CabinetLevelInputBean();
                    BeanHandler.copyAttributes(bean, cabinetLevelInputBean);
                    cabinetLevelInputBean.setCatPartNo(bean.getFacPartNo());
                    CabinetLevelProcess cabinetLevelProcess = new CabinetLevelProcess(getClient());
                    cabinetLevelProcess.setUserGroupNotification(cabinetLevelInputBean, personnelBean, "New Part Added");
                }
            }
		}catch (Exception e) {
			e.printStackTrace();
			result = library.getString("generic.error");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
            genericSqlFactory = null;
        }
		return result;
	}

    private void insertCabinetPartInventoryItem (CabinetBinInputBean bean, PersonnelBean personnelBean) {
        try {
            StringBuilder query = new StringBuilder("insert into cabinet_part_inventory_item ");
            query.append("(COMPANY_ID, CATALOG_COMPANY_ID, CATALOG_ID, CAT_PART_NO, PART_GROUP_NO, FACILITY_ID, APPLICATION,");
            query.append("ITEM_ID, AVG_AMOUNT, MAX_AMOUNT, STATUS, MODIFIED_BY, DATE_MODIFIED)");
            query.append(" select '").append(bean.getCompanyId()).append("' company_id, cpig.company_id catalog_company_id,cpig.catalog_id,cpig.cat_part_no,cpig.part_group_no,");
            query.append("'").append(bean.getFacilityId()).append("' facility_id,'").append(bean.getApplication()).append("' application,cpig.item_id");
            query.append(",").append(bean.getAvgAmount()).append(" avg_amount,").append(bean.getMaxAmount()).append(" max_amount");
            query.append(",'A',").append(personnelBean.getPersonnelId()).append(" modified_by, sysdate date_mofified");
            query.append(" from catalog_part_item_group cpig where company_id = '").append(bean.getCatalogCompanyId()).append("'");
            query.append(" and catalog_id = '").append(bean.getCatalogId()).append("' and cat_part_no = '").append(bean.getCatPartNo()).append("'");
            query.append(" and part_group_no = ").append(bean.getPartGroupNo()).append(" and status = 'A' and priority = 1");
            query.append(" and (company_id,catalog_id,cat_part_no,part_group_no,item_id) not in");
            query.append(" (select catalog_company_id,catalog_id,cat_part_no,part_group_no,item_id from cabinet_part_inventory_item");
            query.append(" where company_id = '").append(bean.getCompanyId()).append("' and catalog_company_id = '").append(bean.getCatalogCompanyId()).append("'");
            query.append(" and catalog_id = '").append(bean.getCatalogId()).append("' and cat_part_no = '").append(bean.getCatPartNo()).append("'");
            query.append(" and part_group_no = ").append(bean.getPartGroupNo()).append(")");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Unable to insert data into cabinet_part_inventory_item",bean.toString());
        }
    }  //end of method

  private boolean isPartDefinedInCabinet(CabinetBinInputBean bean) throws BaseException, Exception {
    boolean flag = false;
    CabinetPartInventoryBeanFactory factory = new CabinetPartInventoryBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, bean.getCompanyId());
    criteria.addCriterion("facilityId", SearchCriterion.EQUALS, bean.getFacilityId());
    criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, bean.getFacPartNo());
	 if (bean.getPartGroupNo() != null) {
		criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, bean.getPartGroupNo().toString());
	 }
	 if (!StringHandler.isBlankString(bean.getCatalogCompanyId())) {
	 	criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, bean.getCatalogCompanyId());
	 }
	 criteria.addCriterion("catalogId", SearchCriterion.EQUALS, bean.getCatalogId());
    criteria.addCriterion("application", SearchCriterion.EQUALS, bean.getApplication());
	 Collection c = factory.select(criteria,connection);
    if(c.size() > 0) {
      flag = true;
    }
    return flag;
  }

    private String insertCabinetPartInventory(CabinetBinInputBean bean, PersonnelBean personnelBean) throws BaseException, Exception {
        String errorCode = "";
        try {
            String homeCompanyOwned = "N";
            if ("Y".equals(bean.getHomeCompanyOwned())) {
                homeCompanyOwned = "Y";
            }
            Collection inArgs = buildProcedureInput(bean.getCompanyId(),bean.getFacilityId(),bean.getApplication(),bean.getCatalogId(),bean.getCatalogCompanyId(),
                                                    bean.getCatPartNo(),bean.getPartGroupNo(),bean.getReorderPoint(),bean.getStockingLevel(),bean.getReorderQuantity(),
                                                    bean.getKanbanReorderQuantity(),bean.getLeadTimeDays(),bean.getStatus(),bean.getBinName(),bean.getCountType(),
                                                    "","","","",bean.getTierIIStorageTemperature(),personnelBean.getPersonnelId(),"",homeCompanyOwned);

            Vector outArgs = new Vector(2);
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            Vector optArgs = new Vector(4);
            optArgs.add("");                            //level_hold_end_date
            optArgs.add("");                            //put_away_method_override
            String dropShipOverride = "N";
            if ("Y".equals(bean.getDropShipOverride()))
                dropShipOverride = "Y";
            optArgs.add(dropShipOverride);              //drop_ship_override
            optArgs.add(bean.getLevelUnit());           // level_unit
            optArgs.add(bean.getStartDate());
            Vector error = (Vector) genericSqlFactory.doProcedure(connection,"PKG_WORK_AREA_MANAGEMENT.P_CREATE_CABINET", inArgs, outArgs, optArgs);
            if (error.size() > 1 && error.get(1) != null && !"ok".equalsIgnoreCase((String) error.get(1))) {
                errorCode = library.getString((String) error.get(1));
            }
        }catch (Exception e) {
            errorCode = library.getString("generic.error");
        }
        return errorCode;
    } //end of method

    public Collection getAuthorizedUserWorkAreasForManangedMatl(PersonnelBean personnelBean,  String facilityId) throws BaseException, Exception  {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
        StringBuilder query = new StringBuilder("select * from fac_loc_app fla, user_group_member_application ugma");
        query.append(" where fla.company_id = ugma.company_id and fla.facility_id = ugma.facility_id");
        query.append(" and fla.application = decode(ugma.application,'All',fla.application,ugma.application)");
        query.append(" and fla.company_id = '").append(personnelBean.getCompanyId()).append("' and fla.facility_id = '").append(facilityId).append("'");
        query.append(" and ugma.user_group_id = 'StockedPartMgmt' and ugma.personnel_id = ").append(personnelBean.getPersonnelId());
        return genericSqlFactory.selectQuery(query.toString());
	}
}