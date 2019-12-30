/*
 * VMIProcess.java
 *
 * Created on March 28, 2008, 12:12 PM
 */

package com.tcmis.client.dla.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.dla.beans.CustomerMro846BaseViewBean;
import com.tcmis.client.dla.factory.DlaVmiBeanFactory;
// import com.tcmis.common.creator.CodeCreator;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanSorter;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.ResourceLibrary;

/**
 *
 * @author  mnajera
 */
public class VMIProcess extends BaseProcess {
   
  Log log = LogFactory.getLog(this.getClass());
   
   /** Creates a new instance of VMIProcess */
   public VMIProcess(String client) {
    super(client);
  }
 
  public Collection getDLAVMICount(String catPartNo, String unitOfSale, boolean obsolete) throws BaseException {
    Collection vmiList = null;
    log.debug("executing DLA VMI count query"); 
    DbManager dbManager = new DbManager(this.getClient());
    DlaVmiBeanFactory beanFactory = new DlaVmiBeanFactory(dbManager);
    vmiList = beanFactory.select(catPartNo, unitOfSale);
    dbManager = null;
    beanFactory = null;
    return vmiList;
  }
   
  public Collection getDLAVMICount(String catPartNo, String unitOfSale) throws BaseException {    
    log.debug("executing DLA VMI count query (from CustomerMro846BaseViewBean)"); 
    DbManager dbManager = new DbManager(this.getClient());
    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CustomerMro846BaseViewBean());        
    SearchCriteria criteria = new SearchCriteria();
    SortCriteria sort = new SortCriteria();
    criteria.addCriterion("catPartNo", SearchCriterion.EQUALS,catPartNo);
    if (unitOfSale!=null && unitOfSale.length()!=0)
       criteria.addCriterion("uom", SearchCriterion.EQUALS,unitOfSale);
    sort.addCriterion("dateSent");
    return factory.select(criteria,sort,"customer_mro_846_base_view");   
  }
  
   public String denyVMIOrder(String companyId, String customerPO, String poLine, String transactionType, String lineSeq, String changeOrderSeq, String denyQuantity) throws BaseException {
      String result=null;
      Connection connect1=null;
      CallableStatement cs=null;

      DbManager dbManager = new DbManager(this.getClient());
      connect1=dbManager.getConnection();
      try {

         cs=connect1.prepareCall( "{ call pkg_dbuy_from_customer.p_940_denial(?,?,?,?,?,?,?) }" );
        
         cs.setString(1, companyId);
         cs.setString(2, customerPO);
         cs.setString(3, poLine);
         cs.setString(4, transactionType);
         cs.setString(5, lineSeq);
         cs.setString(6, changeOrderSeq);
         cs.setString(7, denyQuantity);
         cs.execute();

      } catch (SQLException sqle) {
         log.error("SQL Exception in p_940_denial: " + sqle);
         result=sqle.toString();
      } finally {
         dbManager.returnConnection(connect1);
      }

      return result;
   }
  
}
