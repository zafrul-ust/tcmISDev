package com.tcmis.supplier.wbuy.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.PoHeaderViewBean;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;
import com.tcmis.internal.supply.factory.PoHeaderViewBeanFactory;
import com.tcmis.internal.supply.factory.PoLineDetailViewBeanFactory;
import com.tcmis.internal.supply.factory.PoLineExpediteDateBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class SplitPoLineProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public SplitPoLineProcess(String client) {
	super(client);
 }
 
 public SplitPoLineProcess(String client,String locale) {
	    super(client,locale);
}

 /*Used to confim changes that users did*/
 public Object[] confirmPo(PoHeaderViewBean inputBean,Collection poLineDetailViewBeanInputCollection,
	BigDecimal personnelId) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	String errorMessage = "";
  boolean errorSavingPo = false;
  Iterator mainIterator = poLineDetailViewBeanInputCollection.iterator();
	while (mainIterator.hasNext()) {
	 PoLineDetailViewBean poLineDetailViewBean = (
		PoLineDetailViewBean) mainIterator.next(); ;

   String lineChangeStatus = poLineDetailViewBean.getLineChangeStatus();
   log.info("UPDATING PO "+poLineDetailViewBean.getPoLineStatus()+" lineChangeStatus "+lineChangeStatus+" " + poLineDetailViewBean.getRadianPo() + " Line "+poLineDetailViewBean.getPoLine()+" Ammendment  "+poLineDetailViewBean.getAmendment()+"");

   if (lineChangeStatus != null && (lineChangeStatus.length() > 0 ||
       poLineDetailViewBean.getPoLineStatus().equalsIgnoreCase("Unconfirmed")) ) {

     if (poLineDetailViewBean.getPoLineStatus().equalsIgnoreCase("Confirmed"))
     {
       BigDecimal amendment = poLineDetailViewBean.getAmendment();
       amendment = amendment.add(new BigDecimal("1"));
       poLineDetailViewBean.setAmendment(amendment);
     }
                       
    Vector result = new Vector();
		try {
      PoLineDetailViewBeanFactory poLineDetailViewBeanFactory = new PoLineDetailViewBeanFactory(dbManager);
      result = (Vector) poLineDetailViewBeanFactory.updatePoLine(poLineDetailViewBean, personnelId, "");
			String errorCode = (String) result.get(0);
      if (errorCode != null && errorCode.length() > 0) {
        errorMessage += "<BR>" + errorCode;
        errorSavingPo = true;
      }
     
      if (!errorSavingPo) {
      result = (Vector) poLineDetailViewBeanFactory.updatePoLine(poLineDetailViewBean, personnelId, "confirm");
			errorCode = (String) result.get(0);
      if (errorCode != null && errorCode.length() > 0) {
        errorMessage += "<BR>" + errorCode;
        errorSavingPo = true;
      }
      }     
    }
		catch (Exception ex) {
		 //ex.printStackTrace();
		 errorMessage += ex.getMessage();
		}
	 }
	}

   PoHeaderViewBeanFactory poHeaderViewBeanFactory = new PoHeaderViewBeanFactory(dbManager);
   try {
     if (!errorSavingPo) {
       poHeaderViewBeanFactory.allocatePOLine(inputBean);
     }
   }
   catch (Exception ex) {
     //ex.printStackTrace();
     errorMessage += ex.getMessage();
   }

   Object[] results = {errorMessage};
	 return results;
 }

 public Object[] updateExpeditenotes(Collection poLineDetailViewBeanInputCollection,
	BigDecimal personnelId,boolean noChangeToPoLine) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	String errorMessage = "";
  boolean errorSavingPo = false;

  Iterator mainIterator = poLineDetailViewBeanInputCollection.iterator();
	while (mainIterator.hasNext()) {
	 PoLineDetailViewBean poLineDetailViewBean = (
		PoLineDetailViewBean) mainIterator.next(); ;

    String lineChangeStatus = poLineDetailViewBean.getExpediteNoteChangeStatus();
    if (lineChangeStatus != null && lineChangeStatus.equalsIgnoreCase("Yes"))
    {
      try {
        if (noChangeToPoLine && !poLineDetailViewBean.getOldPromisedDate().equals(poLineDetailViewBean.getPromisedDate()))
        {
          Vector result = new Vector();
          try {
            PoLineDetailViewBeanFactory poLineDetailViewBeanFactory = new PoLineDetailViewBeanFactory(dbManager);

            SearchCriteria childCriteria = new SearchCriteria();
           childCriteria.addCriterion("radianPo", SearchCriterion.EQUALS,
            "" + poLineDetailViewBean.getRadianPo().intValue()+ "");
           childCriteria.addCriterion("poLine", SearchCriterion.EQUALS,
            "" + poLineDetailViewBean.getPoLine().intValue()+ "");

           SortCriteria poLineSortCriteria = new SortCriteria();
           poLineSortCriteria.addCriterion("poLine");

           Collection currentpoLineCollection = poLineDetailViewBeanFactory.select(childCriteria,poLineSortCriteria);
            Iterator currentpoLineIterator = poLineDetailViewBeanInputCollection.iterator();
            while (currentpoLineIterator.hasNext()) {
            PoLineDetailViewBean currentPoLineDetailViewBean = (PoLineDetailViewBean) currentpoLineIterator.next(); ;

            currentPoLineDetailViewBean.setPromisedDate(poLineDetailViewBean.getPromisedDate());
            currentPoLineDetailViewBean.setVendorShipDate(poLineDetailViewBean.getVendorShipDate());
            if (currentPoLineDetailViewBean.getPoLineStatus().equalsIgnoreCase("Confirmed"))
             {
               BigDecimal amendment = currentPoLineDetailViewBean.getAmendment();
               amendment = amendment.add(new BigDecimal("1"));
               currentPoLineDetailViewBean.setAmendment(amendment);
             }

            try {
              result = (Vector) poLineDetailViewBeanFactory.updatePoLine(currentPoLineDetailViewBean, personnelId, "");
              String errorCode = (String) result.get(0);
              if (errorCode != null && errorCode.length() > 0) {
                errorMessage += "<BR>" + errorCode;
                errorSavingPo = true;
              }

              if (!errorSavingPo) {
              result = (Vector) poLineDetailViewBeanFactory.updatePoLine(currentPoLineDetailViewBean, personnelId, "confirm");
              errorCode = (String) result.get(0);
              if (errorCode != null && errorCode.length() > 0) {
                errorMessage += "<BR>" + errorCode;
                errorSavingPo = true;
              }
              }
            }
            catch (Exception ex) {
             //ex.printStackTrace();
             errorMessage += ex.getMessage();
            }
           }
           //poLineDetailViewBeanFactory.updateProjDeliveryDate(poLineDetailViewBean);
          }
          catch (Exception ex) {
           //ex.printStackTrace();
           errorMessage += ex.getMessage();
          }
        }
        
        PoLineExpediteDateBeanFactory poLineExpediteDateBeanFactory = new PoLineExpediteDateBeanFactory(dbManager);
        poLineExpediteDateBeanFactory.insert(poLineDetailViewBean,personnelId);
      }
      catch (Exception ex) {
        ex.printStackTrace();
        errorMessage += ex.getMessage();
      }
    }
	}

  Object[] results = {errorMessage};
	return results;
 }

  public PoHeaderViewBean getPOHeader(BigDecimal radianPo) throws BaseException {
     PoHeaderViewBean poHeaderBean = null;
     DbManager dbManager = new DbManager(this.getClient(),getLocale());
     PoHeaderViewBeanFactory poHeaderFactory = new PoHeaderViewBeanFactory(dbManager);

     SearchCriteria criteria = new SearchCriteria();
     if (radianPo != null) {
      criteria.addCriterion("radianPo", SearchCriterion.EQUALS,""+radianPo+"");
     }

     try {
        Collection c = poHeaderFactory.selectSplitPoData(criteria);
        if (c!=null && ! c.isEmpty()) {
           Iterator iter = c.iterator();
           if (iter.hasNext()) {
              poHeaderBean = (PoHeaderViewBean) iter.next();
           }
        }
     } catch (BaseException be) {
        log.error("Base Exception in query po header: " + be);
     } finally {
        dbManager = null;
        poHeaderFactory = null;
     }
     return poHeaderBean;
  }


  public Collection getPOLines(BigDecimal radianPo) throws BaseException {
	DbManager dbManager = new DbManager(getClient(),getLocale());
	PoLineDetailViewBeanFactory factory = new PoLineDetailViewBeanFactory(dbManager);
	SearchCriteria criteria = new SearchCriteria();
	criteria.addCriterion("radianPo", SearchCriterion.EQUALS,""+radianPo+"");

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("poLine");
	return factory.select(criteria,sortCriteria);
 }
}
