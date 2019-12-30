package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.util.InventoryGroupHandler;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.ReceiptBean;
import com.tcmis.internal.hub.factory.ReceiptBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptAuditBeanFactory;


/******************************************************************************
 * Process for Receipts
 * @version 1.0
 *****************************************************************************/
public class ReceiptsProcess extends BaseProcess {
       
  Log log = LogFactory.getLog(this.getClass());
   
  public ReceiptsProcess(String client) {
    super(client);
  }

  public Collection getReceiptBinHistory(ReceiptBean inputBean) throws BaseException {
     Collection receiptBeans = null;
     DbManager dbManager = new DbManager(this.getClient());
     ReceiptAuditBeanFactory receiptAuditFactory = new ReceiptAuditBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addCriterion("receiptId",SearchCriterion.EQUALS,inputBean.getReceiptId().toString());        
        
        receiptBeans = receiptAuditFactory.selectBinHistory(searchCriteria);                
     } catch (BaseException be2) {
        log.error("Base Exception in getReceiptBinHistory: " + be2);
     } finally {
        dbManager = null;
        receiptAuditFactory = null;
     }
     return receiptBeans;     
  }

  public ReceiptBean getReceipt(ReceiptBean inputBean) throws BaseException {
     Collection receiptBeans = null;
     ReceiptBean receipt = null;
     DbManager dbManager = new DbManager(this.getClient());
     ReceiptBeanFactory receiptFactory = new ReceiptBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addCriterion("receiptId",SearchCriterion.EQUALS,inputBean.getReceiptId().toString());        
        
        receiptBeans = receiptFactory.select(searchCriteria);                
        
        if (receiptBeans !=null) {
           Iterator iter = receiptBeans.iterator();         
           if (iter.hasNext()) {
              receipt = (ReceiptBean) iter.next();           
           }
        }
     } catch (BaseException be2) {
        log.error("Base Exception in getReceipt: " + be2);
     } finally {
        dbManager = null;
        receiptFactory = null;
     }
     
     return receipt;
  }
  
  public void updateReceiptNotes(ReceiptBean inputBean) throws BaseException {
    int result;
     DbManager dbManager = new DbManager(this.getClient());
     ReceiptBeanFactory receiptFactory = new ReceiptBeanFactory(dbManager);
     try {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addCriterion("receiptId",SearchCriterion.EQUALS,inputBean.getReceiptId().toString());        
        
        result = receiptFactory.updateNotes(inputBean.getNotes(),inputBean.getInternalReceiptNotes(),searchCriteria);                
     } catch (BaseException be2) {
        log.error("Base Exception in updateReceiptNotes: " + be2);
     } finally {
        dbManager = null;
        receiptFactory = null;
     }
    
  }
}
