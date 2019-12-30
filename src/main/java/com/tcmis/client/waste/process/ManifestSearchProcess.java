package com.tcmis.client.waste.process;

import org.apache.commons.logging.*;
import com.tcmis.common.framework.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.util.*;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;

import com.tcmis.client.waste.beans.ManifestSearchInputBean;
import com.tcmis.client.waste.factory.WasteManifestViewBeanFactory;

/******************************************************************************
 * Process for TDSF Report
 * @version 1.0
 *****************************************************************************/
public class ManifestSearchProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public ManifestSearchProcess(String client) {
    super(client);
  }

  public Collection getManifestSearchData(ManifestSearchInputBean inputBean) throws BaseException {
    Collection ilmBeans = null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      WasteManifestViewBeanFactory factory = new WasteManifestViewBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      //manifest
      if (!StringHandler.isBlankString(inputBean.getManifest())) {
        if ("LIKE".equals(inputBean.getManifestSearchType())) {
          criteria.addCriterion("manifestId", SearchCriterion.LIKE, inputBean.getManifest());
        }else if ("STARTS_WITH".equals(inputBean.getManifestSearchType())) {
          criteria.addCriterion("manifestId", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, inputBean.getManifest());
        }else if ("ENDS_WITH".equals(inputBean.getManifestSearchType())) {
          criteria.addCriterion("manifestId", SearchCriterion.LESS_THAN_OR_EQUAL_TO, inputBean.getManifest());
        }else {
          criteria.addCriterion("manifestId", SearchCriterion.EQUALS, inputBean.getManifest());
        }
      }
      //ship date between
      if (StringHandler.isBlankString(inputBean.getNotShip())) {
        //ship date
        if (inputBean.getShippedToStartDate() != null && inputBean.getShippedToEndDate() != null) {
          //ship date is between
          criteria.addCriterion("actualShipDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getShippedToStartDate(), "MM/dd/yyyy"));
          criteria.addCriterion("actualShipDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getShippedToEndDate(), "MM/dd/yyyy"));
        }else if (inputBean.getShippedToStartDate() != null) {
          //ship date is after start date
          criteria.addCriterion("actualShipDate", SearchCriterion.GREATER_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getShippedToStartDate(), "MM/dd/yyyy"));
        }else if (inputBean.getShippedToEndDate() != null) {
          //ship date is before end date
          criteria.addCriterion("actualShipDate", SearchCriterion.LESS_THAN_OR_EQUAL_TO,DateHandler.formatDate(inputBean.getShippedToEndDate(), "MM/dd/yyyy"));
        }
      }
      //shipped to
      if (!StringHandler.isBlankString(inputBean.getShippedTo())) {
        if ("LIKE".equals(inputBean.getShippedToSearchType())) {
          criteria.addCriterion("shipToSearchString", SearchCriterion.LIKE, inputBean.getShippedTo());
        }else if ("STARTS_WITH".equals(inputBean.getShippedToSearchType())) {
          criteria.addCriterion("shipToSearchString", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, inputBean.getShippedTo());
        }else if ("ENDS_WITH".equals(inputBean.getShippedToSearchType())) {
          criteria.addCriterion("shipToSearchString", SearchCriterion.LESS_THAN_OR_EQUAL_TO, inputBean.getShippedTo());
        }else {
          criteria.addCriterion("shipToSearchString", SearchCriterion.EQUALS, inputBean.getShippedTo());
        }
      }
      ilmBeans = factory.select(criteria);
    } catch (Exception e) {
      log.error("Base Exception in getManifestSearchData: " + e);
    } finally {
      dbManager = null;
    }

    return ilmBeans;
  } //end of method
} //end of class