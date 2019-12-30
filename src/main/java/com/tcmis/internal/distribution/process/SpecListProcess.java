package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.SpecBean;
import com.tcmis.internal.distribution.beans.CatalogItemSpecListBean;
import com.tcmis.internal.distribution.beans.JdeOrderStageBean;
import com.tcmis.internal.distribution.beans.PoLineSpecViewBean;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class SpecListProcess
  		extends GenericProcess {

   public SpecListProcess(TcmISBaseAction act) throws BaseException{
	    super(act);
   }
   
   public SpecListProcess(String client,String locale) {
	    super(client,locale);
   }
   

   public Collection getSpecListColl(BigDecimal itemId, String catPartNo) throws BaseException {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatalogItemSpecListBean());

		StringBuilder query = new StringBuilder("select * from table (PKG_SPEC_UTILITY.FX_SPEC_FOR_ITEM(");
		query.append(StringHandler.emptyIfNull(itemId)).append(",'");
		query.append(StringHandler.emptyIfNull(catPartNo)).append("'))");
	
		return factory.selectQuery(query.toString());
   }
   
   public Collection getPoLineSpecColl(String radianPo, String poLine) throws BaseException {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PoLineSpecViewBean());
		
		StringBuilder query = new StringBuilder("select * from po_line_spec_view where radian_po");
		
		if(radianPo != null)
			query.append(" = ").append(radianPo);
		else
			query.append(" is null");
		
		if(radianPo != null)
			query.append(" and po_line = ").append(poLine);
		
		return factory.selectQuery(query.toString());
  }
  
  public Collection getSpecListJDEColl(BigDecimal itemId, BigDecimal sequenceNum, BigDecimal lineNumber) throws Exception {
	    DbManager dbManager = null;
	    Connection conn = null;
	   try{
		    dbManager = new DbManager(getClient(),getLocale());
		    conn = dbManager.getConnection();
		    if(factory == null)
		    	factory = new GenericSqlFactory(dbManager);
		    
		    Vector<JdeOrderStageBean> jdeOrderInfoResults = (Vector<JdeOrderStageBean>) getJdeOrderInfo(sequenceNum, lineNumber, itemId, factory, conn);
		    String catPartNo = null;
		    BigDecimal remainingShelfLifePercent = null;
		    if(jdeOrderInfoResults.size() > 0)
		    {
		    	JdeOrderStageBean jdeOrderInfo = jdeOrderInfoResults.firstElement();
		    	catPartNo = jdeOrderInfo.getCatPartNo();
		    	remainingShelfLifePercent = jdeOrderInfo.getRemainingShelfLifePercent();
		    }
		    		    
		    factory.setBean(new CatalogItemSpecListBean());	
			StringBuilder query = new StringBuilder("select * from table (PKG_SPEC_UTILITY.FX_SPEC_FOR_ITEM(");
			query.append(StringHandler.emptyIfNull(itemId)).append(",'");
			query.append(StringHandler.emptyIfNull(catPartNo)).append("'))");
			
			Vector<CatalogItemSpecListBean> mainResults = (Vector<CatalogItemSpecListBean>)factory.selectQuery(query.toString(), conn);
			if(mainResults.size() > 0)
			{
				CatalogItemSpecListBean first = mainResults.firstElement();
				first.setCatPartNo(catPartNo);
				first.setRemainingShelfLifePercent(remainingShelfLifePercent);
			}
			return mainResults;
	   }finally
	   {
		   if(dbManager != null)
			   dbManager.returnConnection(conn);
	   }
  }
   
   public Collection setSelecedSpecListJDE(Vector<SpecBean> beans, BigDecimal seqNum, BigDecimal lineNum)
   {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection optArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		GenericProcedureFactory genericProcedureFactory = new GenericProcedureFactory(this.dbManager);
		SpecBean first = beans.firstElement();
		StringBuilder a_spec_list = new StringBuilder(),a_spec_detail_list = new StringBuilder(), a_spec_coc_list = new StringBuilder(), a_spec_coa_list = new StringBuilder(), a_spec_library_list  = new StringBuilder();
		int count = 0;
		for (SpecBean inputBean : beans) {
			if(count > 0)
			{
				a_spec_list.append("|");
				a_spec_detail_list.append("|");
				a_spec_coc_list.append("|");
				a_spec_coa_list.append("|");
				a_spec_library_list.append("|");
			}
			a_spec_list.append(inputBean.getSpecId());
			if(!StringHandler.isBlankString(inputBean.getSpecDetail()))
				a_spec_detail_list.append(inputBean.getSpecDetail());
			a_spec_coc_list.append(inputBean.getCurrentCocRequirement()?"Y":"N");
			a_spec_coa_list.append(inputBean.getCurrentCoaRequirement()?"Y":"N");
			a_spec_library_list.append(inputBean.getSpecLibrary());
			++count;
		}

		try {
			inArgs = new Vector(10);
			inArgs.add(seqNum);	
			inArgs.add(first.getItemId());
			inArgs.add(a_spec_list.toString());
			inArgs.add(a_spec_detail_list.toString());
			inArgs.add(a_spec_coc_list.toString());
			inArgs.add(a_spec_coa_list.toString());
			inArgs.add(a_spec_library_list.toString());
			inArgs.add(first.getRemainingShelfLifePercent());
			
			optArgs = new Vector(1);
			optArgs.add(lineNum);
			
			outArgs = new Vector(2);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			Vector error = (Vector) genericProcedureFactory.doProcedure("PKG_JDE_UTILITY.P_SAVE_ORDER_SPECS_STAGE", inArgs, outArgs,optArgs);

	      if(error.size()>0 && error.get(1) != null)
	      {
	     	 String errorCode = (String) error.get(0);
	     	 errorMessages.add(errorCode);
	      }  
		}
		catch (Exception e) {
			errorMsg = "Error setting selected Specs: " + seqNum + "," + lineNum;
			errorMessages.add(errorMsg);
		}

		genericProcedureFactory = null;
		dbManager = null;

		return (errorMessages.size() > 0 ? errorMessages : null);

   }
   
   public static Collection getJdeOrderInfo(BigDecimal sequenceNum, BigDecimal lineNumber, BigDecimal itemId, GenericSqlFactory factory, Connection conn) throws Exception 
   {

	    factory.setBean(new JdeOrderStageBean());	
		StringBuilder catPartQuery = new StringBuilder("select * from JDE_ORDER_STAGE where jde_sequence_no = ");
		catPartQuery.append(sequenceNum);
		catPartQuery.append(" and item_id = ");
		catPartQuery.append(itemId);
		
		if(lineNumber != null)
			catPartQuery.append(" and jde_order_line = ").append(lineNumber);
		
		return factory.selectQuery(catPartQuery.toString(),conn);
   }
   
   public void checkInsertDefaultSpec(BigDecimal itemId,BigDecimal sequenceNum,BigDecimal lineNumber) throws BaseException 
   {
	   DbManager dbManager = null;
	   Connection conn = null;
	   GenericProcedureFactory genericProcedureFactory = null;
	   try{
		    dbManager = new DbManager(getClient(),getLocale());
		    conn = dbManager.getConnection();
		    factory.setBean(new JdeOrderStageBean());	
			StringBuilder catPartQuery = new StringBuilder("select count(*) from JDE_ORDER_STAGE where jde_sequence_no = ");
			catPartQuery.append(sequenceNum);
			
			if(lineNumber != null)
				catPartQuery.append(" and jde_order_line = ").append(lineNumber);

			String count = factory.selectSingleValue(catPartQuery.toString(),conn);
			if(Integer.parseInt(count) == 0)
			{
				genericProcedureFactory = new GenericProcedureFactory(this.dbManager);
				Vector errorMessages = new Vector();
				Collection inArgs = null;
				Collection optArgs = null;
				Collection outArgs = null;
				String errorMsg = "";
				inArgs = new Vector(10);
				inArgs.add(sequenceNum);
			
				inArgs.add(itemId);
				inArgs.add(null);
				inArgs.add(null);
				inArgs.add(null);
				inArgs.add(null);
				inArgs.add(null);
				inArgs.add(null);
				
				optArgs = new Vector(1);
				optArgs.add(lineNumber);
				
				outArgs = new Vector(2);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				Vector error = (Vector) genericProcedureFactory.doProcedure("PKG_JDE_UTILITY.P_SAVE_ORDER_SPECS_STAGE", inArgs, outArgs,optArgs);

		      if(error.size()>0 && error.get(0) != null)
		      {
		     	 String errorCode = (String) error.get(0);
		     	 errorMessages.add(errorCode);
		      }   
			}
	    
	   }
	   finally
	   {
		   if(dbManager != null)
			   dbManager.returnConnection(conn);		
			genericProcedureFactory = null;
			dbManager = null;
	   }
	   
   }
   
   public Collection getSpecListColl(BigDecimal itemId, String sequenceNum, String lineNumber) throws BaseException {
	    DbManager dbManager = null;
	    Connection conn = null;
	   try{
		    dbManager = new DbManager(getClient(),getLocale());
		    
		    conn = dbManager.getConnection();
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatalogItemSpecListBean());			
			StringBuilder catPartQuery = new StringBuilder("select cat_part_no from JDE_ORDER_STAGE where sequence_num = ");
			catPartQuery.append(StringHandler.emptyIfNull(sequenceNum));
			catPartQuery.append(" and sequence_num = ").append(lineNumber);
			String catPartNo = factory.selectSingleValue(catPartQuery.toString(),conn);
	
			StringBuilder query = new StringBuilder("select * from table (PKG_SPEC_UTILITY.FX_SPEC_FOR_ITEM(");
			query.append(StringHandler.emptyIfNull(itemId)).append(",'");
			query.append(StringHandler.emptyIfNull(catPartNo)).append("'))");
		
			return factory.selectQuery(query.toString(), conn);
	   }finally
	   {
		   if(dbManager != null)
			   dbManager.returnConnection(conn);
	   }
  }
     
}
