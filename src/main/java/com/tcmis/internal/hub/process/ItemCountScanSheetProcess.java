package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.util.InventoryGroupHandler;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.hub.beans.InventoryGroupCollectionBean;
import com.tcmis.internal.hub.beans.ItemCountScanSheetInputBean;
import com.tcmis.internal.hub.beans.CountScanSetupViewBean;
import com.tcmis.internal.hub.factory.CountScanSetupViewBeanFactory;
import com.tcmis.internal.hub.factory.InventoryGroupCollectionBeanFactory;

/******************************************************************************
 * Process for Item Count Scan Sheet
 * @version 1.0
 *****************************************************************************/
public class ItemCountScanSheetProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ItemCountScanSheetProcess(String client) {
    super(client);
  }

	public Collection getSearchData(ItemCountScanSheetInputBean bean,
	 Collection hubInventoryGroupOvBeanCollection,
	 Collection countScanSetupViewInputBeanCollection) throws BaseException {
    Collection c = null;
    DbManager dbManager = new DbManager(getClient());
    CountScanSetupViewBeanFactory factory = new CountScanSetupViewBeanFactory(dbManager);
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
    if(bean.getInventoryGroup() != null && bean.getInventoryGroup().trim().length() > 0) {
      if(InventoryGroupHandler.isCollection(hubInventoryGroupOvBeanCollection, bean.getHub(), bean.getInventoryGroup())) {
        //get inventory groups for that collection
        SearchCriteria invCriteria = new SearchCriteria();
        invCriteria.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
        invCriteria.addCriterion("inventoryGroupCollection", SearchCriterion.EQUALS, bean.getInventoryGroup());
        InventoryGroupCollectionBeanFactory invFactory = new InventoryGroupCollectionBeanFactory(dbManager);
        Iterator iterator = invFactory.select(invCriteria).iterator();
        int count = 0;
        while(iterator.hasNext()) {
          InventoryGroupCollectionBean inventoryGroupCollectionBean = (InventoryGroupCollectionBean)iterator.next();
          if(count == 0) {
            criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
                                     inventoryGroupCollectionBean.
                                     getInventoryGroup());
          }
          else {
            criteria.addValueToCriterion("inventoryGroup", inventoryGroupCollectionBean.getInventoryGroup());
          }
          count++;
        }
      }
      else {
        criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
      }
    }
    if(bean.getItemType() != null && bean.getItemType().trim().length() > 0) {
      criteria.addCriterion("stockingMethod", SearchCriterion.EQUALS, bean.getItemType());
    }

	if (countScanSetupViewInputBeanCollection != null &&
	 countScanSetupViewInputBeanCollection.size() > 0) {
	 //This si to add he selected item_id to the where clause when they click print
	 Iterator iterator = countScanSetupViewInputBeanCollection.iterator();
	 int count = 0;
	 while (iterator.hasNext()) {
		CountScanSetupViewBean countScanSetupViewBean = (CountScanSetupViewBean)
		 iterator.next();
		if (count == 0) {
		 criteria.addCriterion("itemId", SearchCriterion.EQUALS,
			countScanSetupViewBean.getItemId());
		}
		else {
		 criteria.addValueToCriterion("itemId",
			countScanSetupViewBean.getItemId());
		}
		count++;
	 }
	}

	return factory.select(criteria);
  }

	public String buildScanSheet(Collection countScanSetupViewBeanCollection) throws BaseException {
		String pdfUrl = "";

    if (countScanSetupViewBeanCollection.size() == 0)
    {
      return "";
    }

    PrintItemCountScanSheetProcess printPdfProcess = new PrintItemCountScanSheetProcess(this.getClient());
		try {
		 pdfUrl = printPdfProcess.buildScanSheetPdf(countScanSetupViewBeanCollection);
		}
		catch (Exception ex1) {
		 ex1.printStackTrace();
		 pdfUrl = "";
		}

		return pdfUrl;
	 }

}