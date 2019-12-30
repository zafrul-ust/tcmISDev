package com.tcmis.internal.currency.process;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.currency.beans.CurrencyExchangeRateBean;
import com.tcmis.internal.currency.factory.CurrencyExchangeRateBeanFactory;

/******************************************************************************
 * Process to load currencies. 
 * @version 1.0
 *****************************************************************************/
public class CurrencyLoadProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public CurrencyLoadProcess(String client) {
    super(client);
  }

  private Vector currencies = new Vector();

  /******************************************************************************
   * Gets the xml feed from www.xe.com, parses it, and stores the values in
   * the CURRENCY_EXCHANGE_RATE table.<BR>
   *
   * @throws BaseException If anything goes wrong with the load.
   ****************************************************************************/
  public void load() throws BaseException {
    try {
      ResourceLibrary resource = new ResourceLibrary("currency");
      String feedUrl = resource.getString("currency.url");
//    hard code this line to manually load the currency of certain date, also needs to change the factory. ( for the start date )
//    feedUrl+="&date=20110220"; // Year 2011, Feb, 20
      if (feedUrl == null) {
        BaseException be = new BaseException("INVALID URL FOR LOADING CURRENCY");
        be.setMessageKey("currency.error.url");
        throw be;
      }
      //save feed in temporary file in case something goes wrong with load
      File file = File.createTempFile("CURRENCY", ".load");
      URL url = new URL(feedUrl);
      FileHandler.copy(url, file);
      if (log.isDebugEnabled()) {
        log.debug("Temp file path:" + file.getAbsolutePath());
      }

      Digester digester = new Digester();
      digester.push(this);

      String[] dataTypes = {
          "java.lang.String", "java.lang.String", "java.math.BigDecimal"};
      digester.addCallMethod("xe-datafeed/currency", "addCurrency", 3,
                             dataTypes);
      digester.addCallParam("xe-datafeed/currency/csymbol", 0);
      digester.addCallParam("xe-datafeed/currency/cname", 1);
      digester.addCallParam("xe-datafeed/currency/crate", 2);
      digester.parse(file);
      DbManager dbManager = new DbManager(getClient());
      CurrencyExchangeRateBeanFactory factory = new
          CurrencyExchangeRateBeanFactory(dbManager);
      for (int i = 0; i < currencies.size(); i++) {
        CurrencyExchangeRateBean bean = (CurrencyExchangeRateBean) currencies.
            elementAt(i);
        try {
//        Now 'DEFAULT' and null should have same result 
//        bean.setExchangeRateSource("DEFAULT"); 
          factory.insert(bean);
        }
        catch(Exception e) {
          log.error("Error loading currency:" + bean.getCurrencyId());
        }
      }
      try {
    	  GenericProcedureFactory fac = new GenericProcedureFactory(dbManager);
    	  fac.doProcedure("GLOBAL.p_exchange_rate_daily",new Vector());
      }
      catch(Exception e) {
    	  log.error("Error calling GLOBAL.p_exchange_rate_daily");
      }
    }
    catch (Exception e) {
      BaseException be = new BaseException(e);
      be.setMessageKey("currency.error.load");
      be.setRootCause(e);
      throw be;
    }
  }

  /******************************************************************************
   * Method used by the Digester parser.
   ****************************************************************************/
  public void addCurrency(String csymbol, String cname, BigDecimal crate) {
    CurrencyExchangeRateBean bean = new CurrencyExchangeRateBean();
    bean.setCurrencyId(csymbol);
    bean.setExchangeRate(crate);
    currencies.add(bean);
  }
  public static void main(String[] argv) {
	  try {
	  CurrencyLoadProcess p = new CurrencyLoadProcess("TCM_OPS");
	  p.load();
	  }catch(Exception ex){};
  }  
}