package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.internal.hub.beans.CabinetLabelInputBean;
import com.tcmis.internal.hub.beans.HubCabinetViewBean;
import com.tcmis.internal.hub.factory.HubCabinetViewBeanFactory;

/******************************************************************************
 * Process for cabinet labels
 * @version 1.0
 *****************************************************************************/
public class CabinetLabelProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	boolean calledFromClientCabinet = false;

	public CabinetLabelProcess(String client) {
		super(client);
	}

	public void setCalledFromClientCabinet(boolean value) {
		this.calledFromClientCabinet = value;
	}

	public Collection getAllLabelData(PersonnelBean personnelBean) throws BaseException, Exception {
		Collection criteriaColl = new Vector();
		Iterator iterator = personnelBean.getHubInventoryGroupOvBeanCollection().iterator();
		while (iterator.hasNext()) {
			HubInventoryGroupOvBean ovBean = (HubInventoryGroupOvBean) iterator.next();
			criteriaColl.add(ovBean.getBranchPlant());
		}
		DbManager dbManager = new DbManager(getClient());
		HubCabinetViewBeanFactory factory = new HubCabinetViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria("branchPlant", SearchCriterion.EQUALS, criteriaColl);

		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("preferredWarehouse");
		sortCriteria.addCriterion("facilityId");
		sortCriteria.addCriterion("application");
		Collection flatCollection = factory.select(new SearchCriteria(), sortCriteria);
		return getNormalizedHubCollection(flatCollection);
	}

	public Collection getSearchData(CabinetLabelInputBean input) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient());
		HubCabinetViewBeanFactory factory = new HubCabinetViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		
		if (!StringHandler.isBlankString(input.getCompanyId())) {
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, input.getCompanyId());
		}
		
		if (!StringHandler.isBlankString(input.getAreaId())) {
			criteria.addCriterion("areaId", SearchCriterion.IN, "'" + input.getAreaId().replace("|", "','") + "'");
		}
		
		if (!StringHandler.isBlankString(input.getBuildingId())) {
			criteria.addCriterion("buildingId", SearchCriterion.IN, "'" + input.getBuildingId().replace("|", "','") + "'");
		}
		
		if (!StringHandler.isBlankString(input.getDeptId())) {
			criteria.addCriterion("deptId", SearchCriterion.IN, "'" + input.getDeptId().replace("|", "','") + "'");
		}
		
		if (input.hasWarehouse()) {
			criteria = new SearchCriteria("preferredWarehouse", SearchCriterion.EQUALS, input.getPreferredWarehouse());
		}
		if (input.hasFacility()) {
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, input.getFacilityId());
		}
		if (input.hasApplication()) {
			criteria.addCriterion("application", SearchCriterion.EQUALS, input.getApplication());
		}
		if (input.hasCabinet()) {
			criteria.addCriterionArray("cabinetId", SearchCriterion.IN, input.getCabinetIdArray());
		}
		SortCriteria sortCriteria = new SortCriteria();
		if (!StringHandler.isBlankString(input.getSortBy())) {
			sortCriteria.addCriterion(input.getSortBy());
		}
		return factory.select(criteria, sortCriteria);
	}

	private Collection getNormalizedHubCollection(Collection c) throws Exception {
		Collection normalizedCollection = new Vector(c.size());
		Iterator flatIterator = c.iterator();
		String previousHub = "";
		String previousFacility = "";
		String previousApplication = "";
		HubCabinetViewBean hubBean = null;
		HubCabinetViewBean facilityBean = null;
		HubCabinetViewBean applicationBean = null;
		while (flatIterator.hasNext()) {
			HubCabinetViewBean flatBean = (HubCabinetViewBean) flatIterator.next();
			if (!previousHub.equalsIgnoreCase(flatBean.getPreferredWarehouse())) {
				//new bean
				if (previousHub.length() == 0) {
					//first time in loop
					hubBean = new HubCabinetViewBean();
					facilityBean = new HubCabinetViewBean();
					applicationBean = new HubCabinetViewBean();
					this.copyApplicationData(flatBean, applicationBean);
					this.copyFacilityData(flatBean, facilityBean);
					this.copyHubData(flatBean, hubBean);
				}
				else {
					//different hub
					facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
					hubBean.addFacilityBean((HubCabinetViewBean) facilityBean.clone());
					normalizedCollection.add(hubBean.clone());
					hubBean = new HubCabinetViewBean();
					facilityBean = new HubCabinetViewBean();
					applicationBean = new HubCabinetViewBean();
					this.copyApplicationData(flatBean, applicationBean);
					this.copyFacilityData(flatBean, facilityBean);
					this.copyHubData(flatBean, hubBean);
				}
			}
			else {
				//same hub
				if (!previousFacility.equalsIgnoreCase(flatBean.getFacilityId())) {
					//new facility
					facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
					hubBean.addFacilityBean((HubCabinetViewBean) facilityBean.clone());
					facilityBean = new HubCabinetViewBean();
					applicationBean = new HubCabinetViewBean();
					this.copyApplicationData(flatBean, applicationBean);
					this.copyFacilityData(flatBean, facilityBean);
				}
				else {
					//same dock, check for new application
					if (!previousApplication.equalsIgnoreCase(flatBean.getApplication())) {
						facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
						applicationBean = new HubCabinetViewBean();
						this.copyApplicationData(flatBean, applicationBean);
					}
				}
			}
			previousHub = flatBean.getPreferredWarehouse();
			previousFacility = flatBean.getFacilityId();
			previousApplication = flatBean.getApplication();
		}
		facilityBean.addApplicationBean((HubCabinetViewBean) applicationBean.clone());
		hubBean.addFacilityBean((HubCabinetViewBean) facilityBean.clone());
		normalizedCollection.add(hubBean.clone());
		return normalizedCollection;
	}

	private void copyHubData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
		toBean.setPreferredWarehouse(fromBean.getPreferredWarehouse());
	}

	private void copyFacilityData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
		toBean.setFacilityId(fromBean.getFacilityId());
		toBean.setFacilityName(fromBean.getFacilityName());
	}

	private void copyApplicationData(HubCabinetViewBean fromBean, HubCabinetViewBean toBean) {
		toBean.setApplication(fromBean.getApplication());
		toBean.setApplicationDesc(fromBean.getApplicationDesc());
	}

	  	public ExcelHandler getExcelReport(CabinetLabelInputBean bean, Locale locale) {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		Collection data;
		try {
			data = this.getSearchData(bean);
		}catch (Exception e) {
			log.debug("caught BaseException; " + e.toString());
			return pw;
		}
		// log.debug("data:" + data);
		Iterator iterator = data.iterator();
		pw.addTable();

		// write column headers
		pw.addRow();

		if (calledFromClientCabinet) {
			pw.addCellKeyBold("label.id");
			pw.addCellKeyBold("label.description");
			pw.addCellKeyBold("label.workareagroup");
		}else {
			pw.addCellKeyBold("label.cabinetid");
			pw.addCellKeyBold("label.cabinetdescription");
			pw.addCellKeyBold("label.workarea");
		}

		while (iterator.hasNext()) {
			HubCabinetViewBean tmpBean = (HubCabinetViewBean) iterator.next();
			pw.addRow();
			pw.addCell(tmpBean.getCabinetId());
			pw.addCell(tmpBean.getCabinetName());
			pw.addCell(tmpBean.getApplicationDesc());
		}
		return pw;
	}
}
