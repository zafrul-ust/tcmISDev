package com.tcmis.internal.airgas.process;

import java.util.Collection;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.internal.airgas.beans.StockQueryInputBean;
import com.tcmis.internal.airgas.beans.StockQueryResultViewBean;
import com.tcmis.internal.airgas.beans.OrderSubmitInputBean;
import com.tcmis.internal.airgas.beans.OrderSubmitResultViewBean;


   /******************************************************************************
   * Process for BatchPicklistView
   * @version 1.0
   *****************************************************************************/
  public class PageAirgasProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());
   static public OrderSubmitInputBean preBean = null;
   public PageAirgasProcess(String client) {
  	super(client);
   }

   public Collection StockQuery(StockQueryInputBean bean)  throws
   BaseException, Exception {
	   StockQueryResultViewBean outBean = new StockQueryResultViewBean();
	   outBean.setQtyAvailable("");
	   Vector out = new Vector();
	   out.add(outBean);
	try {   
	   Collection inArgs = new Vector();
   
	   inArgs.add( bean.getAccount() );
   inArgs.add( bean.getRegion() );
   inArgs.add( bean.getInventoryLocation() );
   inArgs.add( bean.getPartNum() );

   Vector outArgs = new Vector();
//   outArgs.add( new Integer(java.sql.Types.NUMERIC) );
   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
   DbManager dbManager = new DbManager(getClient());
   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   Collection coll = procFactory.doProcedure("p_airgas_stock_query", inArgs,outArgs);
   String result = (String) coll.toArray()[0];
   outBean.setQtyAvailable(result);
	} catch(Exception ex){ ex.printStackTrace(); }
	if( outBean.getQtyAvailable() == null || outBean.getQtyAvailable().length() == 0 )
		outBean.setQtyAvailable("-1");
	return out;
}
   
   public Collection OrderSubmit(OrderSubmitInputBean bean)  throws
   BaseException, Exception {
	   preBean = bean;
	   OrderSubmitResultViewBean outBean = new OrderSubmitResultViewBean();
	   outBean.setOrder_number("");
	   Vector out = new Vector();
	   out.add(outBean);

		try {   
	   Collection inArgs = new Vector();
   inArgs.add( bean.getHaaspo() );
   inArgs.add( bean.getHaasline() );

   Vector outArgs = new Vector();
//   outArgs.add( new Integer(java.sql.Types.NUMERIC) );
   outArgs.add( new Integer(java.sql.Types.VARCHAR) );
   DbManager dbManager = new DbManager(getClient());
   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   Collection coll = procFactory.doProcedure("p_airgas_order_submit", inArgs,outArgs);
   String result = (String) coll.toArray()[0];
   
   outBean.setOrder_number(result);
		} catch(Exception ex){ ex.printStackTrace(); }
	if( outBean.getOrder_number() == null || outBean.getOrder_number().length() == 0 )
			outBean.setOrder_number("-1");
	System.out.println("AirgasPO:"+outBean.getOrder_number());
	return out;
   }
}
