package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.CatalogItemNotesViewBean;

/******************************************************************************
 * Process to build a web page for user to view PO/BO.
 * @version 1.0
 *****************************************************************************/
public class CatalogItemNotesProcess extends BaseProcess {
   Log log = LogFactory.getLog(this.getClass());

   public CatalogItemNotesProcess(String client) {
      super(client);
   }

   public CatalogItemNotesProcess(String client,String locale) {
	    super(client,locale);
   }

   public Collection getNotes(String itemId) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(),getLocale());
	  	GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CatalogItemNotesViewBean());
	  	
	  	SearchCriteria criteria = new SearchCriteria();      
		criteria.addCriterion("itemId", SearchCriterion.EQUALS, itemId);

	  	SortCriteria sort = new SortCriteria();
	  	return factory.select(criteria,sort, "catalog_item_notes_view");
   }


   public void addNotes(CatalogItemNotesViewBean bean, PersonnelBean personnel) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(),getLocale());
	   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
	   try {
			String query = "insert into catalog_item_notes (personnel_id,item_id,comments,transaction_date,record_no,last_updated) values (" +
				""+personnel.getPersonnelId()+","+bean.getItemId().toString()+",'"+bean.getComments()+"',sysdate,"+
	      	    bean.getRecordNo().toString()+",sysdate)";
			genericSqlFactory.deleteInsertUpdate(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
   }
   
   public void updateNotes(CatalogItemNotesViewBean bean) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(),getLocale());
	   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
	   try {
		   String query  = "update catalog_item_notes set " +
						   "comments = '" + bean.getComments() + "'," +
						   "last_updated = sysdate " +
						   "where item_id = " + bean.getItemId().toString() + " and record_no = " + bean.getRecordNo().toString();
			genericSqlFactory.deleteInsertUpdate(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
   }
   
   public void deleteNotes(CatalogItemNotesViewBean bean) throws BaseException {
	   DbManager dbManager = new DbManager(getClient(),getLocale());
	   GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
	   try {
		   String query  = "delete catalog_item_notes " +
						   "where item_id = " + bean.getItemId().toString() + " and record_no = " + bean.getRecordNo().toString();
			genericSqlFactory.deleteInsertUpdate(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
   }


   public void processNotes(Collection<CatalogItemNotesViewBean> allComments, PersonnelBean personnel) throws BaseException {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      for(CatalogItemNotesViewBean bean: allComments) {
         if (bean.getDelete()!=null && bean.getDelete().length()>0) {
            //log.debug("got a delete for " + inputBean.getComments());
            if (bean.getRecordNo().intValue() != 0) {
               deleteNotes(bean);
            }
         } else if (!bean.getComments().equals(bean.getOriginalComments())) {
          
            if (bean.getRecordNo().intValue()==0 && bean.getComments()!=null && bean.getComments().trim().length()>0) {
               // new (insert)
//               comment.setTransactionDate(new Date());
//               comment.setLastUpdated(new Date());
               addNotes(bean,personnel);
//               factory.insert(comment);
            } else {
               // update
            	bean.setLastUpdated(new Date());
               updateNotes(bean);
            }
         }
      }
   }
} //end of class
