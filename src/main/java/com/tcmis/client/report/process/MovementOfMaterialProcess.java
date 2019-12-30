package com.tcmis.client.report.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.sql.Connection;

import com.tcmis.client.order.beans.RequestLineItemBean;
import com.tcmis.client.report.beans.WorkareaMovementPermViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CatalogItemAddChargeBean;


public class MovementOfMaterialProcess extends GenericProcess {
	  ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	
	  public MovementOfMaterialProcess(String client) {
		    super(client);
	  }
		
	  public MovementOfMaterialProcess(String client, String locale) {
		    super(client,locale);
	  }
	  
	  public Collection getLineInfo(BigDecimal prNumber,String lineItem) throws BaseException
	  {
		  	DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RequestLineItemBean());
			SearchCriteria criteria = new SearchCriteria();
			
			try {
				criteria.addCriterion("prNumber", SearchCriterion.EQUALS, prNumber.toString());
				
				/* Searching for facility id */
				if(null != lineItem) {
					criteria.addCriterion("lineItem", SearchCriterion.EQUALS, lineItem);
				}		    
				
				SortCriteria sort = new SortCriteria();
				return factory.select(criteria, sort, "request_line_item");
			}  finally {
				dbManager = null;
				factory = null;
			}		
	  }

      private StringBuilder getSqlForWorkAreaForItself (PersonnelBean personnelBean,RequestLineItemBean bean) {
        StringBuilder query = new StringBuilder("select company_id,");
        query.append("'").append(bean.getCatalogCompanyId()).append("' catalog_company_id,'").append(bean.getFacilityId()).append("' facility_id");
        query.append(",'").append(bean.getCatalogId()).append("' catalog_id, ").append(personnelBean.getPersonnelId()).append(" personnel_id");
        query.append(",'").append(bean.getFacPartNo()).append("' fac_part_no, 1 part_group_no, application, application_desc");
        query.append(" from fac_loc_app where company_id = '").append(bean.getCompanyId()).append("' and facility_id = '").append(bean.getFacilityId());
        query.append("' and application = '").append(bean.getApplication()).append("' order by application_desc");
        return query;
      }

      public Collection getWorkAreaDropdown(PersonnelBean personnelBean,RequestLineItemBean bean) throws BaseException
	  {
		  	DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new WorkareaMovementPermViewBean());
			SearchCriteria criteria = new SearchCriteria();
			
			try {
                if (bean.getFacPartNo().contains("EVAL")) {
                    return factory.selectQuery(getSqlForWorkAreaForItself(personnelBean,bean).toString());
                }else {
                    criteria.addCriterion("personnelId", SearchCriterion.EQUALS, Integer.toString(personnelBean.getPersonnelId()));
                    if(null != bean.getPartGroupNo())
                        criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, bean.getPartGroupNo().toString());
                    if(null != bean.getFacilityId())
                        criteria.addCriterion("facilityId", SearchCriterion.EQUALS, bean.getFacilityId());
                    if(null != bean.getCatalogCompanyId())
                        criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, bean.getCatalogCompanyId());
                    if(null != bean.getCatalogId())
                        criteria.addCriterion("catalogId", SearchCriterion.EQUALS, bean.getCatalogId());
                    if(null != bean.getFacPartNo())
                        criteria.addCriterion("facPartNo", SearchCriterion.EQUALS, bean.getFacPartNo());
                    SortCriteria sort = new SortCriteria();
                    sort.addCriterion("applicationDesc");
                    Collection result = factory.select(criteria, sort, "WORKAREA_MOVEMENT_PERM_VIEW");
                    if (result.size() == 0){
                        result = factory.selectQuery(getSqlForWorkAreaForItself(personnelBean,bean).toString());
                    }
                    return result;
                }
            }  finally {
				dbManager = null;
				factory = null;
			}		
	  }
	  
	  public void updateLine(BigDecimal prNumber, String toApplication, String categoryStatus, String requestLineStatus, String facilityId) throws BaseException
	  {
		  	DbManager dbManager = new DbManager(getClient(), getLocale());
		  	GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
			
			try {
				StringBuilder query = new StringBuilder("update request_line_item set ");
                boolean needUpdate = false;
                if (toApplication != null && categoryStatus != null) {
                    query.append("(category_status,request_line_status,application,application_desc,edit_charge_number)");
                    query.append(" = (select '").append(categoryStatus).append("','").append(requestLineStatus).append("',application,application_desc,edit_charge_number from fac_loc_app where application = '").append(toApplication).append("' and facility_id = '").append(facilityId).append("')");
                    needUpdate = true;
                }else if (toApplication != null) {
                    query.append("(application,application_desc,edit_charge_number)").append(" = (select application,application_desc,edit_charge_number from fac_loc_app where application = '").append(toApplication).append("' and facility_id = '").append(facilityId).append("')");
                    needUpdate = true;
                }else if (categoryStatus != null) {
                    query.append("category_status = '").append(categoryStatus).append("', ");
					query.append("request_line_status = '").append(requestLineStatus).append("' ");
                    needUpdate = true;
                }

                if (needUpdate) {
                    query.append(" where pr_number = ").append(prNumber);
				    genericSqlFactory.deleteInsertUpdate(query.toString());
                }
			}  finally {
				dbManager = null;
				genericSqlFactory = null;
			}		
	  }
	  
	  public void updateMR(BigDecimal prNumber, String prStatus) throws BaseException
	  {
		  	DbManager dbManager = new DbManager(getClient(), getLocale());
		  	GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
            try {
				StringBuilder query = new StringBuilder("update purchase_request set ");
				query.append("pr_status = '").append(prStatus).append("',  submitted_date = sysdate");
                query.append(",material_request_origin = 'Work Area Transfer'");
                query.append(" where pr_number = ").append(prNumber);
				genericSqlFactory.deleteInsertUpdate(query.toString());
				
			}  finally {
				dbManager = null;
				genericSqlFactory = null;
			}		
	  }
	  
	  public void deleteMR(BigDecimal prNumber) throws BaseException
	  {
		  	DbManager dbManager = new DbManager(getClient(), getLocale());
            Connection connection = dbManager.getConnection();
            GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
            try {
                StringBuilder query = new StringBuilder("delete from pr_account where pr_number = ").append(prNumber);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

                query = new StringBuilder("delete from request_line_item where pr_number = ").append(prNumber);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				
				query = new StringBuilder("delete from purchase_request where pr_number = ").append(prNumber);
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }catch(Exception e) {
                e.printStackTrace();
            }  finally {
                dbManager.returnConnection(connection);
                dbManager = null;
                connection = null;
                genericSqlFactory = null;
			}		
	  }
	  
	  public String doMoveMatlToWorkArea(BigDecimal fromPrNumber, String fromLineItem, BigDecimal qty, BigDecimal prNumber, String lineItem, BigDecimal personnelId) throws BaseException  {
		  	DbManager dbManager = new DbManager(getClient(), getLocale());
		  	GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
			String errorMsg = "OK";

			try {
				Collection inArgs = buildProcedureInput(fromPrNumber, fromLineItem, qty, prNumber, lineItem);
				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
                Collection inOpts = new Vector(1);
                inOpts.add(personnelId);

				Collection results = procFactory.doProcedure("p_move_matl_to_work_area", inArgs, outArgs,inOpts);
                Iterator i11 = results.iterator();
                String val = "";
                while (i11.hasNext()) {
                    val = (String) i11.next();
                }
                if (!StringHandler.isBlankString(val) && !"OK".equals(val)) {
                    errorMsg = val;
                    StringBuilder msg = new StringBuilder("p_move_matl_to_work_area failed: ").append(val);
                    msg.append("\nfrom pr number: ").append(fromPrNumber);
                    msg.append("\nfrom line item: ").append(fromLineItem);
                    msg.append("\nto pr number: ").append(prNumber);
                    msg.append("\nto line item: ").append(lineItem);
                    msg.append("\nrequestor: ").append(personnelId);
                    MailProcess.sendEmail("Joe.Chang@HaasGroupIntl.com", null,"deverror@tcmis.com","p_move_matl_to_work_area failed",msg.toString());
                }
            }
			catch (Exception e) {
				errorMsg = library.getString("error.db.update");
				log.error(errorMsg, e);
			}
			procFactory = null;
			dbManager = null;
			return errorMsg;
	  }
}

