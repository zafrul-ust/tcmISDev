package com.tcmis.client.report.process;

import com.tcmis.client.common.beans.CabinetManagementBean;
import com.tcmis.client.report.beans.*;
import com.tcmis.client.report.factory.FacAppDockDpViewBeanFactory;
import com.tcmis.client.report.factory.FacAppReportViewBeanFactory;
import com.tcmis.client.report.factory.TcmisConstantBeanFactory;
import com.tcmis.client.report.factory.WasteProfileSynonymViewBeanFactory;
import com.tcmis.common.admin.beans.KeyValuePairBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.ThresholdDisplayBean;
import com.tcmis.common.admin.beans.VvCategoryBean;
import com.tcmis.common.admin.beans.VvCoDefUserGroupBean;
import com.tcmis.common.admin.beans.VvUserGroupBean;
import com.tcmis.common.admin.factory.SearchDropDownBeanFactory;
import com.tcmis.common.admin.factory.VvWasteManagementOptionBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

/**
 * ***************************************************************************
 * Process for receiving
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class GenericReportProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	Locale locale;

	public GenericReportProcess(String client, Locale locale) {
		super(client);
		this.locale = locale;
	}

	public Collection getGroupByOptionList(String[] groupByOptionArray) {
		Collection groupByOptionList = new Vector(groupByOptionArray.length);
		for (int i = 0; i < groupByOptionArray.length; i++) {
			KeyValuePairBean keyValuePairBean = new KeyValuePairBean();
			keyValuePairBean.setKey(groupByOptionArray[i]);
			keyValuePairBean.setValue(groupByOptionArray[i]);
			groupByOptionList.add(keyValuePairBean);
		}
		return groupByOptionList;
	}

	public Collection getGroupByList(String[] groupByArray) {
		Collection groupByList = new Vector(groupByArray.length);
		for (int i = 0; i < groupByArray.length; i++) {
			KeyValuePairBean keyValuePairBean = new KeyValuePairBean();
			keyValuePairBean.setKey(groupByArray[i]);
			keyValuePairBean.setValue(groupByArray[i]);
			groupByList.add(keyValuePairBean);
		}
		return groupByList;
	}


	public Collection getBeginYear() throws BaseException, Exception {
		Collection yearCollection = new Vector();
		//first need to find the starting report year for company
		DbManager dbManager = new DbManager(getClient());
		TcmisConstantBeanFactory tcmisConstantBeanFactory = new TcmisConstantBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("constant", SearchCriterion.LIKE, "Report.");

		Vector tcmisConstantBeanCollection = new Vector(tcmisConstantBeanFactory.select(criteria));
		Integer startingYear = new Integer(1998);
		Integer numberOfYear = new Integer(7);
		for (int j = 0; j < tcmisConstantBeanCollection.size(); j++) {
			TcmisConstantBean tcmisConstantBean = (TcmisConstantBean) tcmisConstantBeanCollection.elementAt(j);
			if ("Report.Year".equalsIgnoreCase(tcmisConstantBean.getConstant())) {
				startingYear = new Integer(tcmisConstantBean.getValue());
			} else if ("Report.NumOfYearDisplay".equalsIgnoreCase(tcmisConstantBean.getConstant())) {
				numberOfYear = new Integer(tcmisConstantBean.getValue());
			}
		}
		//putting it all together
		for (int i = startingYear.intValue(); i < startingYear.intValue() + numberOfYear.intValue(); i++) {
			KeyValuePairBean keyValuePairBean = new KeyValuePairBean();
			keyValuePairBean.setKey((new Integer(i)).toString());
			keyValuePairBean.setValue((new Integer(i)).toString());
			yearCollection.add(keyValuePairBean);
		}
		return yearCollection;
	}

	public Collection getReportList() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ListBean());
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("listName");
		return factory.select(new SearchCriteria(), sort, "list");
	}

	public Collection getSearchDropDownBean(String screenName, String category) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		SearchDropDownBeanFactory searchDropDownBeanFactory = new SearchDropDownBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("category", SearchCriterion.EQUALS, category);
		criteria.addCriterion("screenName", SearchCriterion.EQUALS, screenName);
		return searchDropDownBeanFactory.select(criteria);
	}

	public Collection getBaseFields(String reportType) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new BaseFieldViewBean());
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("baseFieldCategory,displayOrder");
		return factory.select(new SearchCriteria("reportType", SearchCriterion.EQUALS, reportType), sort, "base_field_view");
	}

	
	public Collection getBaseFieldsIn(String reportType) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new BaseFieldViewBean());
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("displayOrder");
		return factory.select(new SearchCriteria("reportType", SearchCriterion.IN, reportType), sort, "base_field_view");
	}

	
	public Collection getStorageAreas(String facilityId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), locale.toString());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FacAreaBlgFloorRmStgView());
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("storageAreaDesc");
		return factory.select(new SearchCriteria("facilityId", SearchCriterion.EQUALS, facilityId), sort, "fac_area_blg_floor_rm_stg_view");
	}

	public Collection getProfiles(ProfileSearchInputBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		WasteProfileSynonymViewBeanFactory factory = new WasteProfileSynonymViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("vendorId", SearchCriterion.EQUALS, bean.getVendorId());
		if (bean.getSearchText() != null && bean.getSearchText().length() > 0) {
			criteria.addCriterion("searchString", SearchCriterion.LIKE, bean.getSearchText(), SearchCriterion.IGNORE_CASE);
		}
		return factory.select(criteria);
	}

	public Collection getManagementOptions(ProfileSearchInputBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		VvWasteManagementOptionBeanFactory factory = new VvWasteManagementOptionBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		if (bean.getSearchText() != null && bean.getSearchText().trim().length() > 0) {
/*
      criteria.addCriterion("managementOption",
                            SearchCriterion.LIKE, 
                            bean.getSearchText(),
                            SearchCriterion.IGNORE_CASE);
      criteria.addCriterion("managementOptionDesc", 
                            SearchCriterion.LIKE,
                            bean.getSearchText(), 
                            SearchCriterion.IGNORE_CASE);
*/
			return factory.selectOption(bean.getSearchText());
		}
		return factory.select(criteria);
	}

	public Collection getWasteProfileDropDownData() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		WasteProfileSynonymViewBeanFactory factory = new WasteProfileSynonymViewBeanFactory(dbManager);
		return factory.select(new SearchCriteria());
	}


	public Collection getMaterialCategories() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new VvCategoryBean());
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("categoryDesc");
		return factory.select(new SearchCriteria(), sort, "vv_category");
	}


	public Collection getDockDeliveryPoints(BigDecimal personnelId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		FacAppDockDpViewBeanFactory factory = new FacAppDockDpViewBeanFactory(dbManager);
		SearchCriteria searchC = new SearchCriteria("personnelId", SearchCriterion.EQUALS, personnelId.toString());
		searchC.addCriterion("dockLocationId",SearchCriterion.NOT_EQUAL,"All");
		return factory.select(searchC);
	}

	public Collection getNormalizedDockDeliveryPoints(BigDecimal personnelId) throws BaseException, Exception {
		Collection normalizedCollection = new Vector();
		Collection c = this.getDockDeliveryPoints(personnelId);
		Iterator it = c.iterator();
		String previousFacility = "";
		String previousDock = "";
		FacAppDockDpViewBean facilityBean = null;
		FacAppDockDpViewBean dockBean = null;
		FacAppDockDpViewBean deliveryPointBean = null;
		
		if( c.size() > 0) {
			while (it.hasNext()) {
				FacAppDockDpViewBean flatBean = (FacAppDockDpViewBean) it.next();
				if (!previousFacility.equalsIgnoreCase(flatBean.getFacilityId())) {
					//new facility -> add bean to collection unless it's first time thru loop
					if (previousFacility.length() == 0) {
						facilityBean = new FacAppDockDpViewBean();
						dockBean = new FacAppDockDpViewBean();
						deliveryPointBean = new FacAppDockDpViewBean();
						this.copyFacilityData(flatBean, facilityBean);
						this.copyDockData(flatBean, dockBean);
						this.copyDeliveryPointData(flatBean, deliveryPointBean);
					} else {
						FacAppDockDpViewBean tempBean6 = (FacAppDockDpViewBean) deliveryPointBean.clone();
						dockBean.addDeliveryPointBean(tempBean6);
						FacAppDockDpViewBean tempBean4 = (FacAppDockDpViewBean) dockBean.clone();
						facilityBean.addDockBean(tempBean4);
						FacAppDockDpViewBean tempBean = (FacAppDockDpViewBean) facilityBean.clone();
						normalizedCollection.add(tempBean);
						facilityBean = new FacAppDockDpViewBean();
						dockBean = new FacAppDockDpViewBean();
						deliveryPointBean = new FacAppDockDpViewBean();
						this.copyFacilityData(flatBean, facilityBean);
						this.copyDockData(flatBean, dockBean);
						this.copyDeliveryPointData(flatBean, deliveryPointBean);
					}
				} else {
					//same facility
					if (!previousDock.equalsIgnoreCase(flatBean.getDockLocationId())) {
						//new dock
						FacAppDockDpViewBean tempBean7 = (FacAppDockDpViewBean) deliveryPointBean.clone();
						dockBean.addDeliveryPointBean(tempBean7);
						FacAppDockDpViewBean tempBean2 = (FacAppDockDpViewBean) dockBean.clone();
						facilityBean.addDockBean(tempBean2);
						dockBean = new FacAppDockDpViewBean();
						deliveryPointBean = new FacAppDockDpViewBean();
						this.copyDockData(flatBean, dockBean);
						this.copyDeliveryPointData(flatBean, deliveryPointBean);
					} else {
						//same dock, should be different delivery point
						FacAppDockDpViewBean tempBean3 = (FacAppDockDpViewBean) deliveryPointBean.clone();
						dockBean.addDeliveryPointBean(tempBean3);
						deliveryPointBean = new FacAppDockDpViewBean();
						this.copyDeliveryPointData(flatBean, deliveryPointBean);
					}
				}
				previousFacility = flatBean.getFacilityId();
				previousDock = flatBean.getDockLocationId();
			} //end of while loop
			
			FacAppDockDpViewBean tempBean6 = (FacAppDockDpViewBean) deliveryPointBean.clone();
			dockBean.addDeliveryPointBean(tempBean6);
			FacAppDockDpViewBean tempBean4 = (FacAppDockDpViewBean) dockBean.clone();
			facilityBean.addDockBean(tempBean4);
	
			FacAppDockDpViewBean tempBean = (FacAppDockDpViewBean) facilityBean.clone();
			normalizedCollection.add(tempBean);
	
			return normalizedCollection;
		}
		else
			return null;
	}

	private void copyFacilityData(FacAppDockDpViewBean fromBean, FacAppDockDpViewBean toBean) {
		toBean.setFacilityId(fromBean.getFacilityId());
	}

	private void copyDockData(FacAppDockDpViewBean fromBean, FacAppDockDpViewBean toBean) {
		toBean.setDockLocationId(fromBean.getDockLocationId());
		toBean.setDockDesc(fromBean.getDockDesc());
	}

	private void copyDeliveryPointData(FacAppDockDpViewBean fromBean, FacAppDockDpViewBean toBean) {
		toBean.setDeliveryPoint(fromBean.getDeliveryPoint());
		toBean.setDeliveryPointDesc(fromBean.getDeliveryPointDesc());
	}

	public Collection getFacAppReportData(BigDecimal personnelId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		FacAppReportViewBeanFactory factory = new FacAppReportViewBeanFactory(dbManager);
		return factory.select(new SearchCriteria("personnelId", SearchCriterion.EQUALS, personnelId.toString()));
	}

	public Collection getNormalizedFacAppReportData(BigDecimal personnelId) throws BaseException, Exception {
		Collection normalizedCollection = new Vector();
		Collection c = this.getFacAppReportData(personnelId);
		Iterator it = c.iterator();
		String previousFacility = "";
		String previousReportingEntity = "";
		FacAppReportViewBean facilityBean = null;
		FacAppReportViewBean reportingEntityBean = null;
		FacAppReportViewBean applicationBean = null;

		if(c.size() > 0) {
			while (it.hasNext()) {
				FacAppReportViewBean flatBean = (FacAppReportViewBean) it.next();
				if (!previousFacility.equalsIgnoreCase(flatBean.getFacilityId())) {
					//new facility -> add bean to collection unless it's first time thru loop
					if (previousFacility.length() == 0) {
						//log.debug("first bean:" + flatBean.getFacilityId());
						facilityBean = new FacAppReportViewBean();
						reportingEntityBean = new FacAppReportViewBean();
						applicationBean = new FacAppReportViewBean();
						this.copyFacilityData(flatBean, facilityBean);
						this.copyReportingEntityData(flatBean, reportingEntityBean);
						this.copyApplicationData(flatBean, applicationBean);
					} else {
						//log.debug("new facility:" + flatBean.getFacilityId());
						FacAppReportViewBean tempBean6 = (FacAppReportViewBean) applicationBean.clone();
						reportingEntityBean.addApplicationBean(tempBean6);
						FacAppReportViewBean tempBean4 = (FacAppReportViewBean) reportingEntityBean.clone();
						facilityBean.addReportingEntityBean(tempBean4);
						FacAppReportViewBean tempBean = (FacAppReportViewBean) facilityBean.clone();
						normalizedCollection.add(tempBean);
						facilityBean = new FacAppReportViewBean();
						reportingEntityBean = new FacAppReportViewBean();
						applicationBean = new FacAppReportViewBean();
						this.copyFacilityData(flatBean, facilityBean);
						this.copyReportingEntityData(flatBean, reportingEntityBean);
						this.copyApplicationData(flatBean, applicationBean);
					}
				} else {
					//same facility
					if (!previousReportingEntity.equalsIgnoreCase(flatBean.getReportingEntityId())) {
						//log.debug("new reporting entity:" + flatBean.getReportingEntityId());
						//new reporting entity
						FacAppReportViewBean tempBean7 = (FacAppReportViewBean) applicationBean.clone();
						reportingEntityBean.addApplicationBean(tempBean7);
						FacAppReportViewBean tempBean2 = (FacAppReportViewBean) reportingEntityBean.clone();
						facilityBean.addReportingEntityBean(tempBean2);
						reportingEntityBean = new FacAppReportViewBean();
						applicationBean = new FacAppReportViewBean();
						this.copyReportingEntityData(flatBean, reportingEntityBean);
						this.copyApplicationData(flatBean, applicationBean);
					} else {
						//log.debug("new application:" + flatBean.getApplication());
						//same reporting entity, should be different application
						FacAppReportViewBean tempBean3 = (FacAppReportViewBean) applicationBean.clone();
						reportingEntityBean.addApplicationBean(tempBean3);
						applicationBean = new FacAppReportViewBean();
						this.copyApplicationData(flatBean, applicationBean);
					}
				}
				previousFacility = flatBean.getFacilityId();
				previousReportingEntity = flatBean.getReportingEntityId();
			}
	
			FacAppReportViewBean tempBean6 = (FacAppReportViewBean) applicationBean.clone();
			reportingEntityBean.addApplicationBean(tempBean6);
			FacAppReportViewBean tempBean4 = (FacAppReportViewBean) reportingEntityBean.clone();
			facilityBean.addReportingEntityBean(tempBean4);
	
			FacAppReportViewBean tempBean = (FacAppReportViewBean) facilityBean.clone();
			normalizedCollection.add(tempBean);
	
			return normalizedCollection;
		}
		else
			return null;
	}

	private void copyFacilityData(FacAppReportViewBean fromBean, FacAppReportViewBean toBean) {
		toBean.setFacilityId(fromBean.getFacilityId());
		toBean.setFacilityName(fromBean.getFacilityName());
		toBean.setReportingEntityLabel(fromBean.getReportingEntityLabel());
	}

	private void copyReportingEntityData(FacAppReportViewBean fromBean, FacAppReportViewBean toBean) {
		toBean.setReportingEntityId(fromBean.getReportingEntityId());
		toBean.setReportingEntityDescription(fromBean.getReportingEntityDescription());
	}

	private void copyApplicationData(FacAppReportViewBean fromBean, FacAppReportViewBean toBean) {
		toBean.setApplication(fromBean.getApplication());
		toBean.setApplicationDesc(fromBean.getApplicationDesc());
	}


	//this method ignores reporting entity
	public Collection getNormalizedFacAppReportDataOld(BigDecimal personnelId) throws BaseException, Exception {
		Collection normalizedCollection = new Vector();
		Collection c = this.getFacAppReportData(personnelId);
		Iterator it = c.iterator();
		String previousFacility = "";
		FacAppReportViewBean facilityBean = null;
		FacAppReportViewBean applicationBean = null;

		while (it.hasNext()) {
			FacAppReportViewBean flatBean = (FacAppReportViewBean) it.next();
			if (!previousFacility.equalsIgnoreCase(flatBean.getFacilityId())) {
				//new facility -> add bean to collection unless it's first time thru loop
				if (previousFacility.length() == 0) {
					//log.debug("first bean:" + flatBean.getFacilityId());
					facilityBean = new FacAppReportViewBean();
					applicationBean = new FacAppReportViewBean();
					this.copyFacilityData(flatBean, facilityBean);
					this.copyApplicationData(flatBean, applicationBean);
				} else {
					//log.debug("new facility:" + flatBean.getFacilityId());
					FacAppReportViewBean tempBean6 = (FacAppReportViewBean) applicationBean.clone();
					facilityBean.addApplicationBean(tempBean6);
					FacAppReportViewBean tempBean = (FacAppReportViewBean) facilityBean.clone();
					normalizedCollection.add(tempBean);
					facilityBean = new FacAppReportViewBean();
					applicationBean = new FacAppReportViewBean();
					this.copyFacilityData(flatBean, facilityBean);
					this.copyApplicationData(flatBean, applicationBean);
				}
			} else {
				//same facility
				//log.debug("new application:" + flatBean.getApplication());
				FacAppReportViewBean tempBean3 = (FacAppReportViewBean) applicationBean.clone();
				facilityBean.addApplicationBean(tempBean3);
				applicationBean = new FacAppReportViewBean();
				this.copyApplicationData(flatBean, applicationBean);
			}
			previousFacility = flatBean.getFacilityId();
		}
		FacAppReportViewBean tempBean6 = (FacAppReportViewBean) applicationBean.clone();
		facilityBean.addApplicationBean(tempBean6);
		FacAppReportViewBean tempBean = (FacAppReportViewBean) facilityBean.clone();
		normalizedCollection.add(tempBean);
		return normalizedCollection;
	}

	public Collection<ThresholdDisplayBean> getListFormatDisplay(String listId, String companyId, String includeDefault) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new ThresholdDisplayBean());		
		StringBuilder query = new StringBuilder("select * from table (pkg_ad_hoc_report.FX_THRESHOLD_DISPLAY ( ");
		query.append(SqlHandler.delimitString(listId));
		query.append(",").append(SqlHandler.delimitString(companyId));
		query.append(",").append(SqlHandler.delimitString(includeDefault));
		query.append(" ))");
        Collection<ThresholdDisplayBean> beans = factory.selectQuery(query.toString());        
		return beans;
	}

	public String getListFormatDisplayTextForId(String listFormatIds, String listId, String companyId, String includeDefault) throws BaseException {
		String textForDisplay = "";
		boolean found = false;
		Collection<ThresholdDisplayBean> listFormatColl = getListFormatDisplay(listId, companyId, includeDefault);
		listFormatIds = listFormatIds + " ";
		String split[] = listFormatIds.split("[|]");
		if (split != null && split.length > 0 ) {
			for (int i = 0 ; i < split.length; i++ ) {
				Iterator it = listFormatColl.iterator();
				while (it.hasNext()){
					ThresholdDisplayBean bean = (ThresholdDisplayBean) it.next();				
					if (split[i].trim().equalsIgnoreCase(StringHandler.emptyIfNull(bean.getId()))) {
						if (!found)
							textForDisplay = bean.getDisplayValue();
						else 
							textForDisplay += "; " + bean.getDisplayValue();
						found = true;						
						break;
					}
				}
			} 
		}
		return textForDisplay;
	}

	public ArrayList<HashMap<String, String>> separateFormatIdAsPerListId(String formatId, String listId) {
    	
    	HashMap<String, String> formatIdMap = null;
    	HashMap<String, String> listIdMap = null;
    	String newListId = "";
    	String newFormatId = "";
    	String[] listFormatSplit = null;
    	String[] chemicalFieldListIdSplit = null;
    	
    	if (!StringHandler.isBlankString(formatId) && !StringHandler.isBlankString(listId)) {
			listFormatSplit = formatId.split("[|]");
			chemicalFieldListIdSplit = listId.split("[|]");
			formatIdMap = new HashMap<String, String>();
			listIdMap = new HashMap<String, String>();
		
	    	String currentListId = "";	    	
	    	for (int i=0; i < chemicalFieldListIdSplit.length; i++) {
				String nextListId = "";			
				nextListId = chemicalFieldListIdSplit[i];
				if (i==0) {
					currentListId = nextListId;
				}
				if (currentListId.equalsIgnoreCase(nextListId)) {				
					newListId += chemicalFieldListIdSplit[i] + "|";
					newFormatId += listFormatSplit[i] + "|";
				} else {					
					formatIdMap.put(currentListId, StringHandler.stripLast(newFormatId, "|"));
					listIdMap.put(currentListId, StringHandler.stripLast(newListId, "|"));
					newListId = chemicalFieldListIdSplit[i] + "|";
					newFormatId = listFormatSplit[i] + "|";
					currentListId = nextListId;
				}
			}
	    	//do this to add the last one in the hashmap
	    	formatIdMap.put(currentListId, StringHandler.stripLast(newFormatId, "|"));
			listIdMap.put(currentListId, StringHandler.stripLast(newListId, "|"));
    	}
    	ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    	result.add(formatIdMap);//format ids go in 0th positions
    	result.add(listIdMap);//list ids go in 1st position
    	return result;
    }
	
    
    public Collection getAuthorizedUserFacilitiesForUsergroup(PersonnelBean personnelBean, String userGroupId, String companyId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
		StringBuilder query = new StringBuilder("SELECT DISTINCT FACILITY_ID FROM USER_GROUP_MEMBER ");
		query.append("WHERE PERSONNEL_ID = ").append(personnelBean.getPersonnelId());
		query.append(" AND USER_GROUP_ID = '").append(userGroupId).append("'");
		query.append(" AND COMPANY_ID = '").append(companyId).append("'");
        return genericSqlFactory.selectQuery(query.toString());        
	}

	public Collection getUserGroupsForFacility(PersonnelBean pbean, String facilityId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		/*GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new VvUserGroupBean());
		StringBuilder query = new StringBuilder("SELECT USER_GROUP_ID,NVL(USER_GROUP_DESC,USER_GROUP_ID) USER_GROUP_NAME FROM USER_GROUP ");
		query.append(" WHERE COMPANY_ID = '").append(pbean.getCompanyId()).append("'");
		query.append(" AND FACILITY_ID = '").append(facilityId).append("'");
        query.append(" AND NVL(USER_GROUP_TYPE,'XXXX') <> 'Admin'");*/
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new VvCoDefUserGroupBean());
		StringBuilder query = new StringBuilder("select * from co_def_user_group where user_group_type = 'ReportPublish'");
		query.append(" and company_id = '").append(pbean.getCompanyId()).append("'");
        
        return genericSqlFactory.selectQuery(query.toString());
	}
    
}