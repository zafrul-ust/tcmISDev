package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.hub.beans.BatchPicklistViewBean;
import com.tcmis.internal.hub.factory.BatchPicklistViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import java.util.Locale;
import java.io.Writer;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

   /******************************************************************************
   * Process for BatchPicklistView
   * @version 1.0
   *****************************************************************************/
  public class BatchPicklistViewProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public BatchPicklistViewProcess(String client) {
  	super(client);
   }

   public Collection getSearchResult(BatchPicklistViewBean bean,boolean queryQcDate) throws BaseException {

  	DbManager dbManager = new DbManager(getClient());
  	BatchPicklistViewBeanFactory factory = new
  	 BatchPicklistViewBeanFactory(dbManager);
  	SearchCriteria criteria = new SearchCriteria();
  	SortCriteria scriteria = new SortCriteria();
  	
  	 criteria.addCriterion("batchId",
  		SearchCriterion.EQUALS,
  		bean.getBatchId().toString());
  	 if( queryQcDate )
  	  	 criteria.addCriterion("qcDate",
  	  	  		SearchCriterion.IS_NOT,
  	  	  		null);

    scriteria.addCriterion("catPartNo");
  	scriteria.addCriterion("itemId");
  	scriteria.addCriterion("receiptId");
  	return factory.select(criteria,scriteria);
   }

   public Collection updateInventory(BatchPicklistViewBean bean,BigDecimal personnelId)  throws
   BaseException, Exception {
   Collection inArgs = new Vector();
   if( bean.getPicklistId() != null) {
	   inArgs.add( bean.getPicklistId() );
	   }
	   else {
	   inArgs.add("");
	   }
   if( bean.getCompanyId() != null) {
	   inArgs.add( bean.getCompanyId()  );
	   }
	   else {
	   inArgs.add("");
	   }
   if( bean.getBatchId() != null) {
   inArgs.add( bean.getBatchId() );
   }
   else {
   inArgs.add("");
   }

   inArgs.add(""); // line item
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
    if( bean.getHub() != null) {
   	 inArgs.add( bean.getHub() );
   	 }
   	 else {
   	 inArgs.add("");
   	 }
    if( bean.getReceiptId() != null) {
   	 inArgs.add( bean.getReceiptId() );
   	 }
   	 else {
   	 inArgs.add("");
   	 }
    inArgs.add(  new Timestamp(  (new java.util.Date()).getTime()  ) );
    if( bean.getQuantityPicked() != null) {
      	 inArgs.add( bean.getQuantityPicked() );
      	 }
      	 else {
      	 inArgs.add("");
     }
    inArgs.add(personnelId);
    ///
   /*
    * if( bean.getBatchId() != null) {
   inArgs.add( bean.getBatchId() );
   }
   else {
   inArgs.add("");
   }
   */
   Vector outArgs = new Vector();
   outArgs.add( new Integer(java.sql.Types.NUMERIC) );
   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
   DbManager dbManager = new DbManager(getClient());
   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   Collection coll = procFactory.doProcedure("p_enter_pick", inArgs,outArgs);
   return coll;
   }
   
   public void qcInventory(BatchPicklistViewBean bean,java.math.BigDecimal personnelId)  throws
   BaseException, Exception {
   Collection inArgs = new Vector();
    if( bean.getPicklistId() != null) {
   inArgs.add( bean.getPicklistId() );
   }
   else {
   inArgs.add("");
   }
   inArgs.add(personnelId);
   DbManager dbManager = new DbManager(getClient());
   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   procFactory.doProcedure("p_qc_picklist", inArgs);
   return;
   }

   public void showInventory(BigDecimal batchId,HttpServletRequest request,boolean queryQcDate) throws BaseException
   {
   
   BatchPicklistViewBean pickBean = new BatchPicklistViewBean();

	pickBean.setBatchId(batchId);
	Vector bv = (Vector) this.getSearchResult(pickBean,queryQcDate);
	request.setAttribute("BatchPicklistViewBeanCollection",bv);
//calculate m1 and m2
	HashMap m1 = new HashMap();
	HashMap m2 = new HashMap();
	Integer i1 = null;
	HashMap map = null;
	Integer i2 = null;
	String partNum = null;

	BatchPicklistViewBean pbean = null;
	for(int i=0;i<bv.size();i++) {
		pbean = (BatchPicklistViewBean)bv.get(i);    	
		partNum = pbean.getCatPartNo();
		
		if( m1.get(partNum) == null ) {
			i1 = new Integer(0);
			m1.put(partNum, i1);
			map = new HashMap();
			m2.put(partNum,map);
		}
		i1 = (Integer) m1.get(partNum);
		i1 = new Integer(i1.intValue()+1);
		m1.put(partNum,i1);

		BigDecimal itemId = pbean.getItemId();
		
		if( map.get(itemId) == null ) {
			i2 = new Integer(0);
			map.put(itemId, i2);
		}
		i2 = (Integer) map.get(itemId);
		i2 = new Integer(i2.intValue()+1);
		map.put(itemId, i2);
	}
/*	if( pbean != null )
		request.setAttribute("inventoryGroup",pbean.getInventoryGroup());
*/
	request.setAttribute("rowCountPart",m1);
   	request.setAttribute("rowCountItem",m2);
	}

	public BatchPicklistViewBean update(Collection beans1, BigDecimal pid ) throws Exception {
		BatchPicklistViewBean bean0 = null, anybean = null;
		BigDecimal zero = new BigDecimal("0");
		Object[] beans = beans1.toArray();
		for( int ii = 0 ; ii < beans.length; ii++ ) {
			BatchPicklistViewBean bean = (BatchPicklistViewBean)beans[ii];
			if( anybean == null ) anybean = bean;
			if( bean.getModified().equalsIgnoreCase("Y") ||
				!bean.getQuantityPicked().equals(zero) ) {
					updateInventory(bean,pid);
					if( bean0 == null ) bean0 = bean;
		}
		}
		if( bean0 != null ) {
			this.qcInventory(bean0,pid);
		}
		return anybean;
	}
}
