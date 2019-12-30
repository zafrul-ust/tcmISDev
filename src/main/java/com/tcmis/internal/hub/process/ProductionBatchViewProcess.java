package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.internal.hub.beans.BatchPicklistViewBean;
import com.tcmis.internal.hub.beans.ProductionBatchViewBean;
import com.tcmis.internal.hub.factory.ProductionBatchViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import java.util.Locale;
import java.io.Writer;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriterion;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.tcmis.common.admin.factory.VvSizeUnitBeanFactory;

   /******************************************************************************
   * Process for ProductionBatchView
   * @version 1.0
   *****************************************************************************/
  public class ProductionBatchViewProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public ProductionBatchViewProcess(String client) {
  	super(client);
   }

   public Collection getSearchResult(ProductionBatchViewBean
  	bean) throws BaseException {

  	DbManager dbManager = new DbManager(getClient());
  	ProductionBatchViewBeanFactory factory = new
  	 ProductionBatchViewBeanFactory(dbManager);
  	SearchCriteria criteria = new SearchCriteria();

  	 criteria.addCriterion("batchId",
  		SearchCriterion.EQUALS,
  		bean.getBatchId().toString());

  	return factory.select(criteria);
   }
   public Collection getSizeBeans() throws BaseException {

		  	DbManager dbManager = new DbManager(getClient());
		  	VvSizeUnitBeanFactory factory = new
		  	VvSizeUnitBeanFactory(dbManager);
		  	SearchCriteria criteria = new SearchCriteria();
		  	SortCriteria sortCriteria = new SortCriteria();
		  	Vector v = new Vector();
//		  	'volume m','weight e','volume e','weight m'
		  	v.add("volume m");
		  	v.add("weight e");
		  	v.add("volume e");
		  	v.add("weight m");
		  	 criteria.addCriterion("dispensable",
				  		SearchCriterion.EQUALS,
				  		"Y");
		  	 criteria.addCriterion("unitType",SearchCriterion.IN,"'volume m','weight e','volume e','weight m'");
//		  	 criteria.addCriterion("unitType",SearchCriterion.IN,v,"");
		  	 sortCriteria.addCriterion("sizeUnit");
		  	return factory.select(criteria,sortCriteria);
		   }


   public Collection approveLot(ProductionBatchViewBean bean,BigDecimal personnelId)  throws
   BaseException, Exception {
   Collection inArgs = new Vector();
    if( bean.getBatchId() != null) {
   inArgs.add( bean.getBatchId() );
   }
   else {
   inArgs.add("");
   }
   inArgs.add(personnelId);
   if( bean.getReceiptId() != null) {
   inArgs.add( bean.getReceiptId() );
   }
   else {
   inArgs.add("");
   }

   Vector outArgs = new Vector();
   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
   DbManager dbManager = new DbManager(getClient());
   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   return procFactory.doProcedure("p_batch_approve", inArgs,outArgs);
   }

public Collection startBatch(ProductionBatchViewBean bean,BigDecimal personnelId )  throws
BaseException, Exception {
Collection inArgs = new Vector();
 
	 if( bean.getHomeCompanyId() != null) {
		 inArgs.add( bean.getHomeCompanyId() );
		 
		 }
		 else {
		 inArgs.add("HAASTCMUSA");
		 }

	 inArgs.add(personnelId);
	 
	 if( bean.getRecipeId() != null) {
		 inArgs.add( bean.getRecipeId() );
		 }
		 else {
		 inArgs.add("");
	 }
	 
if( bean.getVesselId() != null) {
inArgs.add( bean.getVesselId() );
}
else {
inArgs.add("");
}

if( bean.getPlannedYieldAmount() != null) {
inArgs.add( bean.getPlannedYieldAmount() );
}
else {
inArgs.add("");
}

if( bean.getYieldAmountUnit() != null) {
inArgs.add( bean.getYieldAmountUnit() );
}
else {
inArgs.add("gal");
}

if( bean.getMfgLot() != null) {
inArgs.add( bean.getMfgLot() );
}
else {
inArgs.add("");
}

if( bean.getProductionDate() != null) {
try {
	inArgs.add(new Timestamp(bean.getProductionDate().getTime()));
 }
 catch (Exception ex) {
 }
}
else {
inArgs.add("");
}

Vector outArgs = new Vector();
outArgs.add( new Integer(java.sql.Types.INTEGER) );
//outArgs.add( new Integer(java.sql.Types.VARCHAR) );
outArgs.add( new Integer(java.sql.Types.VARCHAR) );
DbManager dbManager = new DbManager(getClient());
GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
return procFactory.doProcedure("p_batch_create", inArgs,outArgs);
}

public Collection updateBatch(ProductionBatchViewBean bean,BigDecimal personnelId )  throws
BaseException, Exception {
Collection inArgs = new Vector();
 
	 if( bean.getHomeCompanyId() != null) {
		 inArgs.add( bean.getHomeCompanyId() );
		 
		 }
		 else {
		 inArgs.add("HAASTCMUSA");
		 }

	 inArgs.add(personnelId);
	 
	 if( bean.getBatchId() != null) {
		 inArgs.add( bean.getBatchId() );
		 }
		 else {
		 inArgs.add("");
	 }
	 
	 if( bean.getRecipeId() != null) {
		 inArgs.add( bean.getRecipeId() );
		 }
		 else {
		 inArgs.add("");
	 }
	 
if( bean.getVesselId() != null) {
inArgs.add( bean.getVesselId() );
}
else {
inArgs.add("");
}

if( bean.getPlannedYieldAmount() != null) {
inArgs.add( bean.getPlannedYieldAmount() );
}
else {
inArgs.add("");
}

if( bean.getYieldAmountUnit() != null) {
inArgs.add( bean.getYieldAmountUnit() );
}
else {
inArgs.add("gal");
}

if( bean.getMfgLot() != null) {
inArgs.add( bean.getMfgLot() );
}
else {
inArgs.add("");
}

if( bean.getProductionDate() != null) {
try {
	inArgs.add(new Timestamp(bean.getProductionDate().getTime()));
 }
 catch (Exception ex) {
 }
}
else {
inArgs.add("");
}

Vector outArgs = new Vector();
//outArgs.add( new Integer(java.sql.Types.INTEGER) );
//outArgs.add( new Integer(java.sql.Types.VARCHAR) );
outArgs.add( new Integer(java.sql.Types.VARCHAR) );
DbManager dbManager = new DbManager(getClient());
GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
return procFactory.doProcedure("p_batch_update", inArgs,outArgs);
}

public Collection batchPickList(ProductionBatchViewBean bean,BigDecimal personnelId )  throws
BaseException {
Collection inArgs = new Vector();

	if( bean.getHomeCompanyId() != null) {
		 inArgs.add( bean.getHomeCompanyId() );
		 }
		 else {
		 inArgs.add("HAASTCMUSA");
		 }

	 inArgs.add(personnelId);
	 
	 if( bean.getBatchId() != null) {
		 inArgs.add( bean.getBatchId() );
		 }
		 else {
		 inArgs.add("");
	 }
	 

Vector outArgs = new Vector();
outArgs.add( new Integer(java.sql.Types.INTEGER) );
//outArgs.add( new Integer(java.sql.Types.VARCHAR) );
outArgs.add( new Integer(java.sql.Types.VARCHAR) );
DbManager dbManager = new DbManager(getClient());
GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
return procFactory.doProcedure("p_batch_picklist", inArgs,outArgs);
}

public Collection releaseInventory(ProductionBatchViewBean bean,BigDecimal personnelId )  throws
BaseException {
Collection inArgs = new Vector();
/*
	if( bean.getHomeCompanyId() != null) {
		 inArgs.add( bean.getHomeCompanyId() );
		 }
		 else {
		 inArgs.add("HAASTCMUSA");
		 }

	 inArgs.add(personnelId);
*/	 
	 if( bean.getBatchId() != null) {
		 inArgs.add( bean.getBatchId() );
		 }
		 else {
		 inArgs.add("");
	 }
	 

Vector outArgs = new Vector();
outArgs.add( new Integer(java.sql.Types.INTEGER) );
//outArgs.add( new Integer(java.sql.Types.VARCHAR) );
outArgs.add( new Integer(java.sql.Types.VARCHAR) );
Vector inArgs2 = new Vector();
inArgs2.add(""); // default "Y"
DbManager dbManager = new DbManager(getClient());
GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
return procFactory.doProcedure("p_batch_delete",inArgs,outArgs);
}

}
