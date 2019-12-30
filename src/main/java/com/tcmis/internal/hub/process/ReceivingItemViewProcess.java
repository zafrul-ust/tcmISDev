package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.hub.beans.ListItemDetailViewBean;
import com.tcmis.internal.hub.factory.ListItemDetailViewBeanFactory;
import com.tcmis.internal.hub.factory.ReceiptBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
   * Process for ReceivingItemView
   * @version 1.0
   *****************************************************************************/
  public class ReceivingItemViewProcess
   extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public ReceivingItemViewProcess(String client) {
  	super(client);
   }

   public Collection getSearchResult(ListItemDetailViewBean
  	bean) throws BaseException {

  	DbManager dbManager = new DbManager(getClient());
  	ListItemDetailViewBeanFactory factory = new
  	 ListItemDetailViewBeanFactory(dbManager);
     
    SearchCriteria criteria = new SearchCriteria();
  	if (bean.getInventoryGroup() != null && !bean.getInventoryGroup().equalsIgnoreCase("ALL") &&
  	  	  bean.getInventoryGroup().length() > 0) {
  	  	 criteria.addCriterion("inventoryGroup",SearchCriterion.EQUALS,bean.getInventoryGroup());
  	}
  	if (bean.getListItemId() != null) {
  	  	 criteria.addCriterion("listItemId",SearchCriterion.EQUALS,""+bean.getListItemId()+"");
  	}

    if (bean.getListItemId() != null) {
  	  	 criteria.addCriterion("itemId",SearchCriterion.NOT_EQUAL,""+bean.getListItemId()+"");
  	}
 	   return factory.select(criteria,new SortCriteria("itemId"));
   }

  public int updateReceiptItem(ListItemDetailViewBean inputBean, BigDecimal personnelId) throws BaseException {
	 DbManager dbManager = new DbManager(getClient());
	 ReceiptBeanFactory factory = new ReceiptBeanFactory(dbManager);	 
   boolean result = true;
   if (inputBean.getReceiptList().trim().length() > 0) {
     		String receiptList = inputBean.getReceiptList().trim();
				if (receiptList.contains(",")) {
					String[] receipt = receiptList.split(",");
					for (int i = 0; i < receipt.length; i++) {
						result = factory.updateMlItem(new BigDecimal(receipt[i]),inputBean.getItemId());
					}
				}else {
					result = factory.updateMlItem(new BigDecimal(receiptList),inputBean.getItemId());
				}
			}

 if (result)
  return 0;
 else
 return 1;
}
}
