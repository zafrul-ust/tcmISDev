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
import com.tcmis.internal.airgas.process.AirgasProcess;


   /******************************************************************************
   * Process for BatchPicklistView
   * @version 1.0
   *****************************************************************************/
  public class AirgasURLProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public AirgasURLProcess(String client) {
  	super(client);
   }

	// here is the socket programming test code..
   public Collection StockQuery(StockQueryInputBean bean)  throws
   BaseException, Exception {
	   DbManager dbManager = new DbManager(getClient());
	   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   AirgasProcess process = new AirgasProcess();
   Vector<StockQueryResultViewBean> out = process.StockQuery(bean);
   return out;
   }
   
   public Collection OrderSubmit(OrderSubmitInputBean bean)  throws
   BaseException, Exception {
	   DbManager dbManager = new DbManager(getClient());
	   GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
   AirgasProcess process = new AirgasProcess();
   // input params.
   bean.getHaaspo();
   bean.getHaasline();
   System.out.println("PO:"+bean.getHaaspo());
   System.out.println("LINE:"+bean.getHaasline());
   // insert your set up code here for order submit call
   
   
   // These are required params. set with test value. need to be replaced except for shared secret
   // shared secret needed to be hard coded to "airgas-haas-xml"
   bean.setSharesecret("airgas-haas-xml");

   OrderSubmitInputBean preBean = PageAirgasProcess.preBean;
// always same value as the hard coded value   bean.setSharesecret(preBean.getSharesecret());
// make sure when preBean removes and it still compiles..
   bean.setAccount(preBean.getAccount());
   bean.setRegion(preBean.getRegion());
   bean.setInventoryLocation(preBean.getInventoryLocation());
   bean.setPartNum(preBean.getPartNum());
   bean.setOrder_companyName(preBean.getOrder_companyName());
   bean.setOrder_orderQty(preBean.getOrder_orderQty());
   bean.setOrder_orderPrice(preBean.getOrder_orderPrice());
   bean.setOrderType(preBean.getOrderType()); // 4 or 0, 4 has more information
   bean.setShipto_Address1(preBean.getShipto_Address1());
   bean.setShipto_City(preBean.getShipto_City());
   bean.setShipto_State(preBean.getShipto_State()); 
//optional      bean.setShipto_Zip(preBean.getShipto_Zip());
   
   
   Vector<OrderSubmitResultViewBean> out = process.OrderSubmit(bean);
//   return order number -1 is error.
//   out.get(0).getOrder_number();
   return out;
   }
   
}
