package com.tcmis.internal.hub.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.factory.NonchemicalReceivingQcViewBeanFactory;

/******************************************************************************
 * Process for receiving qc
 * @version 1.0
 *****************************************************************************/
public class NonChemicalReceivingQcProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public NonChemicalReceivingQcProcess(String client) {
		super(client);
	}

	public NonChemicalReceivingQcProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getNonChemicalResult(String receivedReceipts, String hubNumber, String inventoryGroup, Collection hubInventoryGroupOvBeanColl, boolean justReceived) throws BaseException {

		DbManager dbManager = new DbManager(getClient(), getLocale());
		NonchemicalReceivingQcViewBeanFactory factory = new NonchemicalReceivingQcViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();

		if (hubNumber != null && hubNumber.length() > 0) {
			criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, hubNumber);

			if (!justReceived) {
				criteria.addCriterion("approved", SearchCriterion.EQUALS, "N");
			}
		}

		HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(this.getClient());
		boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl, hubNumber, inventoryGroup);

		if (inventoryGroup != null && !inventoryGroup.equalsIgnoreCase("ALL")) {

			if (iscollection) {

			}
			else if (inventoryGroup.length() > 0) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, inventoryGroup);
			}
		}

		/*if (inventoryGroup !=null && !inventoryGroup.equalsIgnoreCase("ALL") && inventoryGroup.length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		inventoryGroup);
		}*/

		if (receivedReceipts != null && receivedReceipts.length() > 0) {
			criteria.addCriterion("receiptId", SearchCriterion.IN, receivedReceipts);
		}

		ReceivingInputBean inputBean = new ReceivingInputBean();
		inputBean.setInventoryGroup(inventoryGroup);
		inputBean.setSourceHub(hubNumber);
		inputBean.setSort("Receipt ID");
		return factory.selectSorted(criteria, inputBean, iscollection);

	}

	public Collection getNonChemicalResult(ReceivingInputBean bean, Collection hubInventoryGroupOvBeanColl) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		NonchemicalReceivingQcViewBeanFactory factory = new NonchemicalReceivingQcViewBeanFactory(dbManager);
		String sortBy = null;
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("branchPlant", SearchCriterion.EQUALS, bean.getSourceHub());

		HubInventoryGroupProcess hubInventoryGroupProcess = new HubInventoryGroupProcess(this.getClient());
		boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl, bean.getSourceHub(), bean.getInventoryGroup());

		if (bean.getInventoryGroup() != null && !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {

			if (iscollection) {

			}
			else if (bean.getInventoryGroup().length() > 0) {
				criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			}
		}

		//add description search if it's not null
		if (bean.getSearchWhat() != null && bean.getSearch() != null && bean.getSearch().trim().length() > 0) {
			//check search criterion
			String criterion = null;
			if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("IS")) {
				criterion = SearchCriterion.EQUALS;
			}
			else if (bean.getSearchType() != null && bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
				criterion = SearchCriterion.LIKE;
			}

			String searchWhat = bean.getSearchWhat();
			//check what to search by
			if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("PO")) {
				criteria.addCriterion("radianPo", criterion, bean.getSearch());
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("Receipt Id")) {
				criteria.addCriterion("receiptId", criterion, bean.getSearch());
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("ITEM DESC")) {
				criteria.addCriterion("lineDesc", criterion, bean.getSearch(), SearchCriterion.IGNORE_CASE);
			}
			else if (bean.getSearchWhat() != null && bean.getSearchWhat().equalsIgnoreCase("ITEM ID")) {
				criteria.addCriterion("itemId", criterion, bean.getSearch());
			}
		}

		criteria.addCriterion("approved", SearchCriterion.EQUALS, "N");

		return factory.selectSorted(criteria, bean, iscollection);
	}
}