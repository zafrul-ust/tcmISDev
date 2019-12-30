package com.tcmis.internal.currency.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

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
public class BankOfIsraelCurrencyLoadProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public BankOfIsraelCurrencyLoadProcess(String client) {
    super(client);
  }

  private Vector currencies = new Vector();

  /******************************************************************************
   * Gets the xml feed from www.xe.com, parses it, and stores the values in
   * the CURRENCY_EXCHANGE_RATE table.<BR>
   *
   * @throws BaseException If anything goes wrong with the load.
   ****************************************************************************/
private String getURL(String feedUrl) {
	StringBuilder sb = null;
	try {
  for(int i = 3; i>0; i--) {
	  sb = new StringBuilder();
  URL url = new URL(feedUrl);
//User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.22) Gecko/20110902 Firefox/3.6.22 GTB7.1 ( .NET CLR 3.5.30729)
//Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//Accept-Language: en-us,en;q=0.5
//Accept-Encoding: gzip,deflate
//Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
//Keep-Alive: 115
//Connection: keep-alive
//	HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
	HttpURLConnection uc = (HttpURLConnection) url.openConnection();
	uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.22) Gecko/20110902 Firefox/3.6.22 GTB7.1 ( .NET CLR 3.5.30729)");
//Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
	uc.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	uc.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
	uc.setRequestProperty("Accept-Encoding", "gzip,deflate");
	uc.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
	uc.setRequestProperty("Keep-Alive", "115");
	uc.setRequestProperty("Connection", "keep-alive");
//Keep-Alive: 115
//Connection: keep-alive
//	if (userName != null && password != null) {
//		uc.setRequestProperty("Authorization", "Basic " + encodedAuthenticationString);
//	}
	// ori setting??
//	uc.setReadTimeout(TIME_OUT);
//	uc.setFollowRedirects(true);
//	uc.setInstanceFollowRedirects(true);
//	uc.setAllowUserInteraction(true);
	uc.setDoOutput(true);
	uc.setDoInput(true);
	uc.setUseCaches(false);
	uc.setRequestMethod("POST");
	uc.setRequestProperty("Content-Type", "text/xml");
	
	BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));//uc.getInputStream()
	String inputLine;
//	'aa7c.com')" onmouseout="menuLayers.hide()">Available</a>
	boolean refreshAgain = false;
	while ((inputLine = in.readLine()) != null) {
//		System.out.println(inputLine);
		sb.append(inputLine);
		if( inputLine.contains("Refresh") ) {
			refreshAgain = true;
			System.out.println("Refresh");
		}
	}
	in.close();
	uc.disconnect();		
//	Refresh
	if( refreshAgain ) {
	  Thread.sleep(1000);
	}
	else break;
  }
  }catch(Exception ee){}
return sb.toString();
}
  public void load() throws BaseException {
    try {
      ResourceLibrary resource = new ResourceLibrary("currency");
      String feedUrl = resource.getString("Bank_of_Israel_currency.url");//"http://www.bankisrael.gov.il/currency.xml";//resource.getString("Bank_of_Israel_currency.url");
      if (feedUrl == null) {
        BaseException be = new BaseException("INVALID URL FOR LOADING CURRENCY");
        be.setMessageKey("currency.error.url");
        throw be;
      }
      //save feed in temporary file in case something goes wrong with load
      File file = File.createTempFile("BOI_CURRENCY", ".load");
      String data= getURL(feedUrl);
      FileOutputStream fouts = new FileOutputStream(file);
      fouts.write(data.getBytes());
      fouts.close();
//      FileHandler.copy(url, file);
      System.out.println( file.getAbsolutePath() );
      if (log.isDebugEnabled()) {
        log.debug("Temp file path:" + file.getAbsolutePath());
      }

      Digester digester = new Digester();
      digester.push(this);

      String[] dataTypes = {
          "java.lang.String", "java.lang.String", "java.math.BigDecimal","java.math.BigDecimal"};
      digester.addCallMethod("CURRENCIES/CURRENCY", "addCurrency", 4,
                             dataTypes);
      digester.addCallParam("CURRENCIES/CURRENCY/CURRENCYCODE", 0);
      digester.addCallParam("CURRENCIES/CURRENCY/NAME", 1);
      digester.addCallParam("CURRENCIES/CURRENCY/RATE", 2);
      digester.addCallParam("CURRENCIES/CURRENCY/UNIT", 3);
      digester.parse(file);
      DbManager dbManager = new DbManager(getClient());
      CurrencyExchangeRateBeanFactory factory = new
          CurrencyExchangeRateBeanFactory(dbManager);
  	  BigDecimal usd = new BigDecimal("1");
  	CurrencyExchangeRateBean ils = new CurrencyExchangeRateBean();
  	ils.setCurrencyId("ILS");
      for (int i = 0; i < currencies.size(); i++) {
          CurrencyExchangeRateBean bean = (CurrencyExchangeRateBean) currencies.
              elementAt(i);
          if( "USD".equals(bean.getCurrencyId() ) ) {
        	  usd = bean.getExchangeRate();
        	  ils.setExchangeRate(bean.getExchangeRate());
        	  ils.setExchangeRateSource("Bank of Israel");
          }
      }
      for (int i = 0; i < currencies.size(); i++) {
        CurrencyExchangeRateBean bean = (CurrencyExchangeRateBean) currencies.
            elementAt(i);
        if( !"USD".equals(bean.getCurrencyId() ) ) {
        bean.setExchangeRateSource("Bank of Israel");
        if( bean.getExchangeRate() != null ) {
//        	System.out.println(usd+":"+bean.getExchangeRate());
        	
        	BigDecimal reverse = usd.divide(bean.getExchangeRate().divide(bean.getUnit()),15,BigDecimal.ROUND_CEILING); //unit always  5 or 10s integers.
//        	System.out.println(usd+"::"+bean.getExchangeRate());
        	bean.setExchangeRate(reverse);
        }
        try {
          factory.insert(bean);
        }
        catch(Exception e) {
          log.error("Error loading currency:" + bean.getCurrencyId());
        }
        } 
      } // end of for loop
      try {
    	  factory.insert(ils);
      }
      catch(Exception e) {
    	  log.error("Error loading currency:" + ils.getCurrencyId());
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
      e.printStackTrace();
      throw be;
    }
  }

  /******************************************************************************
   * Method used by the Digester parser.
   ****************************************************************************/
  public void addCurrency(String csymbol, String cname, BigDecimal crate, BigDecimal cunit) {
    CurrencyExchangeRateBean bean = new CurrencyExchangeRateBean();
    bean.setCurrencyId(csymbol);
    bean.setExchangeRate(crate);
    bean.setUnit(cunit);
    currencies.add(bean);
  }
  public static void main(String[] argv) {
	  try {
	  BankOfIsraelCurrencyLoadProcess p = new BankOfIsraelCurrencyLoadProcess("TCM_OPS");
	  p.load();
	  }catch(Exception ex){};
  }
}