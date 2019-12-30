package com.tcmis.internal.hub.process;

//import java.io.*;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.BatchProductionInputBean;
import com.tcmis.internal.hub.beans.ProductionBatchViewBean;
import com.tcmis.internal.hub.factory.ProductionBatchViewBeanFactory;

import com.tcmis.common.util.ExcelHandler;

/******************************************************************************
 * Process used by BatchProductionAction
 * @version 1.0
 *****************************************************************************/

public class BatchProductionProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public BatchProductionProcess(String client) {
	super(client);
 }

 public void closeBatch(String batchId) throws BaseException, Exception {
	DbManager dbManager = new DbManager(getClient());
	ProductionBatchViewBeanFactory factory = new ProductionBatchViewBeanFactory(
	 dbManager);
	//factory.closeBatch(batchId);
	return;
 }

 public Collection getProductionBatchViewBeanCollection(
	BatchProductionInputBean bean) throws BaseException, Exception {
	DbManager dbManager = new DbManager(getClient());
	ProductionBatchViewBeanFactory factory = new ProductionBatchViewBeanFactory(dbManager);
	SearchCriteria searchCriteria = new SearchCriteria();

	if (bean.getHub() != null && !bean.getHub().equalsIgnoreCase("ALL")) {
	 searchCriteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
	}
	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
	 searchCriteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		bean.getInventoryGroup());
	}
	if (bean.getRecipeId() != null && bean.getRecipeId().length() != 0) {
	 searchCriteria.addCriterion("recipeId", SearchCriterion.EQUALS,
		bean.getRecipeId());
	}
	if (bean.getMaterialDesc() != null && bean.getMaterialDesc().length() != 0) {
	 searchCriteria.addCriterion("materialDesc", SearchCriterion.LIKE,
		bean.getMaterialDesc(), "Y");
	}
	if (bean.getMfgLot() != null && bean.getMfgLot().length() != 0) {
	 searchCriteria.addCriterion("mfgLot", SearchCriterion.LIKE, bean.getMfgLot(),
		"Y");
	}
	if (bean.getVesselId() != null && bean.getVesselId().length() != 0) {
	 searchCriteria.addCriterion("vesselId", SearchCriterion.EQUALS,
		bean.getVesselId());
	}
	if (bean.getOpenBatchesOnly() != null && bean.getOpenBatchesOnly().equals("Y")) {
	 searchCriteria.addCriterion("batchCloseDate", SearchCriterion.IS, "NULL");
	}
	if (bean.getProductionDateFrom() != null &&
	 bean.getProductionDateFrom().length() != 0) {
	 searchCriteria.addCriterion("productionDate",
		SearchCriterion.GREATER_THAN_OR_EQUAL_TO, bean.getProductionDateFrom());
	}
	if (bean.getProductionDateTo() != null &&
	 bean.getProductionDateTo().length() != 0) {
	 searchCriteria.addCriterion("productionDate",
		SearchCriterion.LESS_THAN_OR_EQUAL_TO, bean.getProductionDateTo());
	}

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("batchId");
	Collection c = factory.select(searchCriteria);
	return c;
  }

  public ExcelHandler getExcelReport(BatchProductionInputBean inputBean, Locale locale) throws
      NoDataException, BaseException, Exception
  {
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
    ExcelHandler pw = new ExcelHandler(library);
    Collection data = this.getProductionBatchViewBeanCollection ( inputBean);
    Iterator iterator = data.iterator();

	pw.addTable();

// 	  write column headers
	pw.addRow();
    pw.addCellKeyBold("label.request");
    pw.addCellKeyBold("label.requestdate");
    pw.addCellKeyBold("label.productiondate");
    pw.addCellKeyBold("label.requestor");
    pw.addCellKeyBold("label.recipe");
    pw.addCellKeyBold("label.itemid");
    pw.addCellKeyBold("label.itemdesc");
    pw.addCellKeyBold("label.lot");
    pw.addCellKeyBold("label.plannedyield");
    pw.addCellKeyBold("label.actualyield");
    pw.addCellKeyBold("label.percent");
    pw.addCellKeyBold("label.productioncost");

    //now write data
    while(iterator.hasNext())
    {
    	ProductionBatchViewBean batchProductionViewBean = (ProductionBatchViewBean) iterator.next();
    	DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
    	String startDate = shortDateFormat.format(batchProductionViewBean.getBatchStartDate() );
    	String productionDate = shortDateFormat.format(batchProductionViewBean.getProductionDate() );
    	pw.addRow();
    	pw.addCell(batchProductionViewBean.getBatchId());
    	pw.addCell(startDate);
    	pw.addCell(productionDate);
    	pw.addCell(batchProductionViewBean.getBatchStartUserId());
    	pw.addCell(batchProductionViewBean.getRecipeName());
    	pw.addCell(batchProductionViewBean.getItemId());
    	pw.addCell(batchProductionViewBean.getMaterialDesc());
    	pw.addCell(batchProductionViewBean.getMfgLot());
    	pw.addCell(batchProductionViewBean.getPlannedYieldAmount());
    	pw.addCell(batchProductionViewBean.getActualYieldAmount());
    	pw.addCell(batchProductionViewBean.getYieldPercentage());
    	pw.addCell(batchProductionViewBean.getProductionBatchCost());

    }
    return pw;
}
}

