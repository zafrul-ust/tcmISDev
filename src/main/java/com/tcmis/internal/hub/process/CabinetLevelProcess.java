package com.tcmis.internal.hub.process;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.*;
import com.tcmis.internal.hub.factory.*;
import com.tcmis.client.common.beans.CabinetManagementBean;

/******************************************************************************
 * Process for allocation analysis
 * @version 1.0
 *****************************************************************************/
public class CabinetLevelProcess extends GenericProcess {
  Log log = LogFactory.getLog(this.getClass());
  private String calledFrom = "";

  public CabinetLevelProcess(String client) {
    super(client);
  }

  public CabinetLevelProcess(String client,String locale) {
	    super(client,locale);
  }

  public void setCalledFrom(String calledFrom) {
	  this.calledFrom = calledFrom;
  }

  public Collection getPartCurrentLevel(CabinetLevelInputBean bean) throws BaseException {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CabinetPartLevelViewBean());
	 StringBuilder query = new StringBuilder("select distinct cat_part_no,part_description,reorder_point,stocking_level,reorder_quantity,kanban_reorder_quantity,");
	 query.append("lead_time_days,ordering_application,ordering_application application,catalog_company_id,level_hold_end_date,drop_ship_override,put_away_method_override");
	 query.append(" from cabinet_part_level_view where facility_id = '").append(bean.getFacilityId()).append("' and ordering_application = '").append(bean.getApplication());
	 query.append("' and cabinet_id = ").append(bean.getCabinetId()).append(" and cat_part_no = '").append(bean.getCatPartNo()).append("'");
	 query.append(" and part_group_no = ").append(bean.getPartGroupNo());
	 return factory.selectQuery(query.toString());
  }

  public Collection getCurrentLevel(CabinetLevelInputBean bean) throws
      BaseException {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    CabinetPartLevelViewBeanFactory factory = new CabinetPartLevelViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("status", SearchCriterion.EQUALS, bean.getStatus());
	 if (bean.getBranchPlant() != null) {
	 	criteria.addCriterion("sourceHub", SearchCriterion.EQUALS,bean.getBranchPlant());
	 }
	 criteria.addCriterion("companyId", SearchCriterion.EQUALS,bean.getCompanyId());
	 criteria.addCriterion("facilityId", SearchCriterion.EQUALS,bean.getFacilityId());
    criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, bean.getCatalogCompanyId());
	 criteria.addCriterion("catalogId", SearchCriterion.EQUALS, bean.getCatalogId());
	 criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, bean.getCatPartNo());
	 criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, bean.getPartGroupNo());
	 criteria.addCriterion("itemId", SearchCriterion.EQUALS, bean.getItemId());
	 criteria.addCriterion("cabinetId", SearchCriterion.EQUALS, bean.getCabinetId().toString());
	 criteria.addCriterion("binId", SearchCriterion.EQUALS, bean.getBinId().toString());
	 return factory.selectDistinct(criteria, new SortCriteria());
  }

  public Collection getHistory(CabinetLevelInputBean bean,Collection cabinetPartLevelViewBeanCollection) throws BaseException {
    DbManager dbManager = new DbManager(getClient(),getLocale());
		CabinetPartLevelLogViewBeanFactory factory = new CabinetPartLevelLogViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (cabinetPartLevelViewBeanCollection.size() > 0) {
			 Iterator partLevelIterator = cabinetPartLevelViewBeanCollection.iterator();
			 int count = 0;
			 while (partLevelIterator.hasNext()) {
				CabinetPartLevelViewBean levlBean = (CabinetPartLevelViewBean) partLevelIterator.next();
				if (count == 0) {
				 criteria.addCriterion("application", SearchCriterion.EQUALS,levlBean.getApplication());
				}else {
				 break;
				}
			 }
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS,bean.getFacilityId());
		criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,bean.getCatPartNo());
		SortCriteria sort = new SortCriteria("dateModified");
		sort.setSortAscending(false);
		return factory.select(criteria, sort);
	}else {
	 return null;
	}
 }

  public void update(CabinetLevelInputBean bean, PersonnelBean personnelBean) throws BaseException {
    if(bean.getStockingLevel().compareTo(bean.getReorderPoint()) < 0) {
      throw new BaseException("error.reorderpoint.lessthanstockinglevel");
    }
    DbManager dbManager = new DbManager(getClient(),getLocale());
    GenericSqlFactory factory = new GenericSqlFactory(dbManager);

    Collection inArgs = buildProcedureInput(bean.getBinId(),personnelBean.getPersonnelId(),bean.getReorderPoint(),bean.getStockingLevel(),bean.getReorderQuantity(),
                                                    bean.getKanbanReorderQuantity(),bean.getLeadTimeDays(),"","","","","","","",bean.getRemarks(),"","N","");
    Vector optArgs = new Vector(3);
    optArgs.add(bean.getLevelHoldEndDate());
    optArgs.add(bean.getPutAwayMethodOverride());
    optArgs.add(bean.getDropShipOverride());
    Vector outArgs = new Vector(1);
    outArgs.add(new Integer(java.sql.Types.VARCHAR));
    Vector error = (Vector) factory.doProcedure("PKG_WORK_AREA_MANAGEMENT.P_UPDATE_CABINET", inArgs, outArgs, optArgs);
    if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
        MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","PKG_WORK_AREA_MANAGEMENT.P_UPDATE_CABINET updating levels","Error while trying to update level using PKG_WORK_AREA_MANAGEMENT.P_UPDATE_CABINET: "+(String) error.get(0));
    }else {
    	if (bean.getOldReorderQuantity() != null && bean.getReorderQuantity() == null) {
	    	String updateQuery = "update cabinet_part_inventory set reorder_quantity = null where bin_id = " + bean.getBinId() ;
	    	factory.deleteInsertUpdate(updateQuery);
    	}
        //client want to be notify
        if (!"Radian".equalsIgnoreCase(personnelBean.getCompanyId()) && !"Haas".equalsIgnoreCase(personnelBean.getCompanyId())) {
            setUserGroupNotification(bean,personnelBean,"Storage Level Change");
        }else {
            //if the change is more than 30% percent send email to hub manager
            if((bean.getOldStockingLevel().compareTo(new BigDecimal("0")) > 0 && bean.getStockingLevel().compareTo(new BigDecimal("0")) == 0) ||
                bean.getOldStockingLevel().compareTo(new BigDecimal("0")) == 0 ||
                ((bean.getStockingLevel().subtract(bean.getOldStockingLevel())).divide(bean.getOldStockingLevel(), 3, BigDecimal.ROUND_HALF_UP)).abs().compareTo(new BigDecimal("0.3")) > 0) {
                String subject = "Cabinet Level Changes -- " + "," + "P/N:" + bean.getCatPartNo() + ", Item #:" + bean.getItemId() + ", Cabinet:" + bean.getApplication();
                StringWriter message = new StringWriter();
                message.write("Cabinet Level Changes:\n\n");
                message.write("Facility: " + bean.getFacilityId() + "\n");
                message.write("Cabinet: " + bean.getApplication() + "\n");
                message.write("Part Number: " + bean.getCatPartNo() + "\n");
                message.write("Item Id: " + bean.getItemId() + "\n");
                message.write("Stocking Level: Old: " + bean.getOldStockingLevel() +
                                  " New: " + bean.getStockingLevel() + "\n");
                message.write("Reorder Point: Old: " + bean.getOldReorderPoint() +
                                  " New: " +
                                  bean.getReorderPoint() + "\n");
                message.write("Reorder Quantity: Old: " + bean.getOldReorderQuantity() +
                                  " New: " +
                                  bean.getReorderQuantity() + "\n");
                message.write("KanBan Reorder Quantity: Old: " + bean.getOldKanbanReorderQuantity() +
                                  " New: " +
                                  bean.getKanbanReorderQuantity() + "\n");
                message.write("Lead Time in Days: Old: " + bean.getOldLeadTimeDays() +
                                  " New: " + bean.getLeadTimeDays() + "\n");
                message.write("Level Hold End Date: Old: " + bean.getOldLevelHoldEndDate() +
                        " New: " + bean.getLevelHoldEndDate() + "\n");
                message.write("Comments: " + bean.getRemarks() + "\n");
                message.write("Changed by: " + personnelBean.getLastName() + "," + personnelBean.getFirstName() + "\n");
                StringBuilder query = new StringBuilder("select fx_get_hub_mgr_email(fla.inventory_group,'").append(personnelBean.getCompanyId());
                query.append("') from fac_loc_app fla where company_id = '").append(bean.getCompanyId()).append("' and facility_id = '").append(bean.getFacilityId());
                query.append("' and application = '").append(bean.getApplication()).append("'");
                String hubManagerEmails = factory.selectSingleValue(query.toString());
                if (!StringHandler.isBlankString(hubManagerEmails)) {
                    String to[] = hubManagerEmails.split(";");
                    String cc[] = {};
                    MailProcess.sendEmail(to, cc, subject, message.toString(),false);
                }
            }
        }
    } //end of no error
  } //end of method

  public void setUserGroupNotification(CabinetLevelInputBean bean, PersonnelBean personnelBean, String subject) {
	  try {
			if ("Status Update".equalsIgnoreCase(subject) ||  "New Part Added".equalsIgnoreCase(subject) || "Change Ownership".equalsIgnoreCase(subject) ||
                ("Storage Level Change".equalsIgnoreCase(subject) && (!bean.getOldReorderPoint().equals(bean.getReorderPoint()) ||
                 !bean.getOldStockingLevel().equals(bean.getStockingLevel()))))
			{
				DbManager dbManager = new DbManager(getClient(),getLocale());
				GenericSqlFactory gFactory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
				SearchCriteria criteria = new SearchCriteria();
				criteria.addCriterion("companyId", SearchCriterion.EQUALS, bean.getCompanyId());
				criteria.addCriterion("facilityId", SearchCriterion.EQUALS, bean.getFacilityId());
				criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, bean.getCatalogCompanyId());
				criteria.addCriterion("catalogId", SearchCriterion.EQUALS, bean.getCatalogId());
				criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, bean.getCatPartNo());
				criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, bean.getPartGroupNo());
				if (bean.getCabinetId() != null) {
					criteria.addCriterion("cabinetId", SearchCriterion.EQUALS, bean.getCabinetId().toString());
				}
				if (bean.getApplication() != null) {
					criteria.addCriterion("orderingApplication", SearchCriterion.EQUALS, bean.getApplication().toString());
				}
				SortCriteria sortC = new SortCriteria();
				sortC.addCriterion("itemId");
				Collection dataC = gFactory.select(criteria,sortC,"cabinet_part_level_view");
				Iterator iter = dataC.iterator();
				StringBuilder message = new StringBuilder("");
				boolean firstTime = true;
				while(iter.hasNext()) {
					CabinetManagementBean tmpBean = (CabinetManagementBean)iter.next();

					if ("Status Update".equalsIgnoreCase(subject) || "Change Ownership".equalsIgnoreCase(subject)) {
                        bean.setReorderPoint(tmpBean.getReorderPoint());
                        bean.setStockingLevel(tmpBean.getStockingLevel());
                        bean.setReorderQuantity(tmpBean.getReorderQuantity());
                    }

					if (firstTime) {
						message.append("Work Area       :").append(tmpBean.getCabinetName());
						if (!StringHandler.isBlankString(tmpBean.getCustomerCabinetId())) {
							message.append(" (").append(tmpBean.getCustomerCabinetId()).append(")");
						}
						message.append("\n");
						message.append(" ").append("\n");
						message.append("Updated by      :").append(personnelBean.getLastName()).append(", ").append(personnelBean.getFirstName()).append("\n");
                        message.append("Updated on      :").append(Calendar.getInstance().getTime()).append("\n");
                        message.append("Action          :" + subject).append("\n");
						message.append("Part Number     :").append(tmpBean.getCatPartNo()).append("\n");
                        if ("Status Update".equals(subject) || "Change Ownership".equalsIgnoreCase(subject)) {
                            message.append("RP/SL/RQ        :").append(bean.getReorderPoint()).append("/").append(bean.getStockingLevel()).append("/").append(NumberHandler.emptyIfNull(bean.getReorderQuantity())).append("\n");
                        }else {
                            message.append("New RP/SL/RQ    :").append(bean.getReorderPoint()).append("/").append(bean.getStockingLevel()).append("/").append(NumberHandler.emptyIfNull(bean.getReorderQuantity())).append("\n");
                        }
                        if ("Storage Level Change".equals(subject)) {
                            message.append("Old RP/SL/RQ    :").append(bean.getOldReorderPoint()).append("/").append(bean.getOldStockingLevel()).append("/").append(NumberHandler.emptyIfNull(bean.getOldReorderQuantity())).append("\n");
                        }
                        String tmpStatus = "Active";
                        if ("I".equalsIgnoreCase(tmpBean.getBinPartStatus())) {
                            tmpStatus = "Inactive";
                        }
                        if ("Status Update".equals(subject)) {
                            message.append("Current Status  :").append(tmpStatus).append("\n");
                            if ("I".equalsIgnoreCase(tmpBean.getBinPartStatus())) {
                                message.append("Previous Status :").append("Active").append("\n");
                            }else {
                                message.append("Previous Status :").append("Inactive").append("\n");
                            }
                        }else {
                            message.append("Status          :").append(tmpStatus).append("\n");
                        }
                        if ("Change Ownership".equals(subject)) {
                            message.append("Ownership       :").append(bean.getOwnershipName()).append("\n");
                        }

                        firstTime = false;
					}
					message.append(" ").append("\n");
					message.append("Item            :").append(tmpBean.getItemId()).append("\n");
                    if (!StringHandler.isBlankString(tmpBean.getMsdsString())) {
                        message.append("Msds Number     :").append(StringHandler.emptyIfNull(tmpBean.getMsdsString())).append("\n");
                    }else {
                        message.append("Haas Material   :").append(StringHandler.emptyIfNull(tmpBean.getMaterialIdString())).append("\n");
                    }
                    message.append("Description     :").append(tmpBean.getMaterialDesc()).append("\n");
                    message.append("Packaging       :").append(tmpBean.getPackaging()).append("\n");
                    if (!StringHandler.isBlankString(tmpBean.getMinStorageTempDisplay())) {
                        message.append("Storage Temperature  :").append(tmpBean.getMinStorageTempDisplay().replace("@ ","")).append("\n");
                    }
                }
				if (message.length() > 0) {
					BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
                    bulkMailProcess.sendBulkEmail("NotifyWorkAreaLevelChanged", bean.getFacilityId(), null, bean.getCompanyId(),subject, message.toString(), false);
                }
			}
	  }catch(Exception e) {

	  }

  }

  public void updateHistory(CabinetLevelInputBean bean) throws BaseException {
    DbManager dbManager = new DbManager(getClient(),getLocale());
    CabinetPartLevelLogBeanFactory factory = new CabinetPartLevelLogBeanFactory(dbManager);
    CabinetPartLevelLogBean cabinetPartLevelLogBean = new CabinetPartLevelLogBean();
    BeanHandler.copyAttributes(bean, cabinetPartLevelLogBean);
    cabinetPartLevelLogBean.setDateModified(new Date());
    cabinetPartLevelLogBean.setModifiedBy(bean.getPersonnelId());
    cabinetPartLevelLogBean.setApplication(bean.getOrderingApplication());
    cabinetPartLevelLogBean.setNewLeadTimeDays(bean.getLeadTimeDays());
    cabinetPartLevelLogBean.setNewReorderPoint(bean.getReorderPoint());
    cabinetPartLevelLogBean.setNewStockingLevel(bean.getStockingLevel());
	 cabinetPartLevelLogBean.setNewReorderQuantity(bean.getReorderQuantity());
	 cabinetPartLevelLogBean.setNewKanbanReorderQuantity(bean.getKanbanReorderQuantity());
	 cabinetPartLevelLogBean.setNewLevelHoldEndDate(bean.getLevelHoldEndDate());
	 factory.insert(cabinetPartLevelLogBean);
  }

}