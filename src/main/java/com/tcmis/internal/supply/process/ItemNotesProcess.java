package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.ItemBuyerCommentsBean;
import com.tcmis.internal.supply.beans.ItemBuyerCommentsViewBean;
import com.tcmis.internal.supply.factory.ItemBuyerCommentsBeanFactory;
import com.tcmis.internal.supply.factory.ItemBuyerCommentsViewBeanFactory;

/******************************************************************************
 * Process to build a web page for user to view PO/BO.
 * @version 1.0
 *****************************************************************************/
public class ItemNotesProcess extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public ItemNotesProcess(String client) {
      super(client);
   }

   public ItemNotesProcess(String client,String locale) {
	    super(client,locale);
   }

   public Collection getNotes(ItemBuyerCommentsBean inputBean) throws BaseException {
      if(inputBean.getItemId()!=null) {
         return getNotes(inputBean.getItemId().toString());
      }
      return null;
   }

   public Collection getNotes(String itemId) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      ItemBuyerCommentsViewBeanFactory factory = new ItemBuyerCommentsViewBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      if(itemId!=null && !StringHandler.isBlankString(itemId)) {
         criteria = new SearchCriteria("itemId",SearchCriterion.EQUALS,itemId);
      }
      SortCriteria sortCriteria = null;
      sortCriteria =new SortCriteria();
			sortCriteria.setSortAscending(false);
      sortCriteria.addCriterion("transactionDate");

      return factory.select(criteria,sortCriteria);
   }


   public void addNotes(ItemBuyerCommentsBean inputBean, PersonnelBean personnel) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      ItemBuyerCommentsBeanFactory factory = new ItemBuyerCommentsBeanFactory(dbManager);
      Date now = new Date();
      inputBean.setBuyerId(new BigDecimal(personnel.getPersonnelId()));
      inputBean.setTransactionDate(now);
      inputBean.setLastUpdated(now);

      factory.insert(inputBean);
   }

   public void updateNotes(Collection allComments, PersonnelBean personnel) throws BaseException {
      ItemBuyerCommentsViewBean inputBean = null;
      DbManager dbManager = new DbManager(getClient(),getLocale());
      ItemBuyerCommentsBeanFactory factory = new ItemBuyerCommentsBeanFactory(dbManager);
      Iterator iter = allComments.iterator();
      while (iter.hasNext()) {
         inputBean = (ItemBuyerCommentsViewBean) iter.next();
         if (inputBean.getDelete()!=null && inputBean.getDelete().length()>0) {
            //log.debug("got a delete for " + inputBean.getComments());
            if (inputBean.getRecordNo().intValue()!=0) {
               SearchCriteria criteria = new SearchCriteria();
               criteria.addCriterion("recordNo", SearchCriterion.EQUALS, inputBean.getRecordNo().toString());
               factory.delete(criteria);
            }
         } else if (inputBean.getChanged()!=null && inputBean.getChanged().equalsIgnoreCase("yes")) {
            //log.debug("got a change for " + inputBean.getComments());
            // update the database
            ItemBuyerCommentsBean comment = new ItemBuyerCommentsBean();
            BeanHandler.copyAttributes(inputBean,comment);
            if (inputBean.getRecordNo().intValue()==0 && inputBean.getComments()!=null && inputBean.getComments().trim().length()>0) {
               // new (insert)
               comment.setTransactionDate(new Date());
               comment.setLastUpdated(new Date());
               factory.insert(comment);
            } else {
               // update
               comment.setLastUpdated(new Date());
               factory.update(comment);
            }
         }
      }
   }
} //end of class
