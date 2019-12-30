/*
 * StatusProcess.java
 *
 * Created on April 10, 2007, 4:48 PM
 */

package com.tcmis.supplier.wbuy.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.supplier.wbuy.factory.WbuyStatusViewBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

/**
 *
 * @author  mnajera
 */
public class StatusProcess extends BaseProcess {

   Log log = LogFactory.getLog(this.getClass());
   
   /** Creates a new instance of StatusProcess */
   public StatusProcess(String client) {
     super(client);
   }
   
 public Collection getWbuyStatusBeans() throws BaseException {
     Collection statusBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     WbuyStatusViewBeanFactory wbuyViewFactory = new WbuyStatusViewBeanFactory(dbManager);
     try {        
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addCriterion("dbuyStatus",SearchCriterion.NOT_EQUAL,"CONFIRMED");
        searchCriteria.addCriterion("dbuyStatus",SearchCriterion.NOT_EQUAL,"REJECTED");
        statusBeans = wbuyViewFactory.select(searchCriteria);
     } catch (BaseException be2) {
        log.error("Base Exception in getWbuyStatusBeans: " + be2);
     } finally {
        dbManager = null;
        wbuyViewFactory = null;
     }          
     return statusBeans;     
 }
   
   
}
