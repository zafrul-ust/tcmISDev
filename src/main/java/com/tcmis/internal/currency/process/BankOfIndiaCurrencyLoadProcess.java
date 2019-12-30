package com.tcmis.internal.currency.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

//import com.itextpdf.kernel.pdf.*;
//import com.itextpdf.kernel.pdf.canvas.parser.*;
//import com.itextpdf.text.pdf.*;
//import com.itextpdf.text.pdf.parser.PdfTextExtractor;

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
public class BankOfIndiaCurrencyLoadProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public BankOfIndiaCurrencyLoadProcess(String client) {
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
	HttpURLConnection uc = (HttpURLConnection) url.openConnection();
	uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.22) Gecko/20110902 Firefox/3.6.22 GTB7.1 ( .NET CLR 3.5.30729)");
	uc.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	uc.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
	uc.setRequestProperty("Accept-Encoding", "gzip,deflate");
	uc.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
	uc.setRequestProperty("Keep-Alive", "115");
	uc.setRequestProperty("Connection", "keep-alive");
	uc.setDoOutput(true);
	uc.setDoInput(true);
	uc.setUseCaches(false);
	uc.setRequestMethod("POST");
	uc.setRequestProperty("Content-Type", "text/xml");
	
	BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));//uc.getInputStream()
	String inputLine;
	boolean refreshAgain = false;
	while ((inputLine = in.readLine()) != null) {
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
  public void load(String fin,String effdate) throws BaseException {
    try {
//      //save feed in temporary file in case something goes wrong with load
    	
    File file = File.createTempFile("BOIndia_CURRENCY", ".load");
    System.out.println( file.getAbsolutePath() );
    DbManager dbManager = new DbManager(getClient());
    CurrencyExchangeRateBeanFactory factory = new
        CurrencyExchangeRateBeanFactory(dbManager);
	BigDecimal usd = new BigDecimal("1");
	CurrencyExchangeRateBean ils = new CurrencyExchangeRateBean();
	CurrencyExchangeRateBean bean = new CurrencyExchangeRateBean();

	
	ils.setExchangeRateSource("Bank Of India");
    bean.setExchangeRateSource("Bank Of India");
	ils.setCurrencyId("INR");
	String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"
		+ "<EffectiveDate>"+effdate+"</EffectiveDate><CURRENCIES>";

	File ff = new File(fin); 
			PDDocument document = PDDocument.load(ff);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String contentS = pdfStripper.getText(document);
//			System.out.print(pdfStripper.getText(document));
			document.close();
//	PdfReader reader = new PdfReader(fin);
//   int n = reader.getNumberOfPages(); 
//   String contentS = "";
//   for (int page = 1; page <= n; page++) {
//    	byte[] ss = PdfTextExtractor.getTextFromPage(reader, page).getBytes("UTF-8");
//    	String ss1 = PdfTextExtractor.getTextFromPage(reader, page);
//    	contentS+=new String(ss1);
//   }
   Pattern curPat = null;
   Matcher curMat = null;
   int groupCount = 0; //	  	    	  int groupCount = curMat.groupCount(); 0..groupCount

   String data = contentS;

   System.out.println(data);
   //INR is the reverse of USD.		
   
   Pattern eurPattern = Pattern.compile("US Dollar\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)");
   Matcher m = eurPattern.matcher(data);
		if (m.find()) {
			String eurS = m.group(3);
			eurS = eurS.replace(',','.');
			usd = new BigDecimal(eurS);
	      	ils.setExchangeRate(usd);
	      	factory.insert(ils);
		xmlString+="<CURRENCY><UNIT>1</UNIT><CURRENCYCODE>USD</CURRENCYCODE><RATE>"+eurS+"</RATE></CURRENCY>";
System.out.println("INR="+usd);
		}
//GBP		
 		eurPattern = Pattern.compile("Pound Sterling\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)");
  		m = eurPattern.matcher(data);
		if (m.find()) {
			String eurS = m.group(3);
			eurS = eurS.replace(',','.');
			BigDecimal eurd = new BigDecimal(eurS);
	        
	        	BigDecimal reverse = usd.divide(eurd,15,BigDecimal.ROUND_CEILING); //unit always  5 or 10s integers.
	        	bean.setExchangeRate(reverse);
	        	bean.setCurrencyId("GBP");
	        	factory.insert(bean);
	    xmlString+="<CURRENCY><UNIT>1</UNIT><CURRENCYCODE>GBP</CURRENCYCODE><RATE>"+eurS+"</RATE></CURRENCY>";
		System.out.println("GBP="+eurd);
			
		}
//EUR
		eurPattern = Pattern.compile("EURO\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)");
  		m = eurPattern.matcher(data);
		if (m.find()) {
			String eurS = m.group(3);
			eurS = eurS.replace(',','.');
			BigDecimal eurd = new BigDecimal(eurS);
        	BigDecimal reverse = usd.divide(eurd,15,BigDecimal.ROUND_CEILING); //unit always  5 or 10s integers.
//        	System.out.println(usd+"::"+bean.getExchangeRate());
        	bean.setExchangeRate(reverse);
        	bean.setCurrencyId("EUR");
	    	factory.insert(bean);
		xmlString+="<CURRENCY><UNIT>1</UNIT><CURRENCYCODE>EUR</CURRENCYCODE><RATE>"+eurS+"</RATE></CURRENCY>";
			System.out.println("EUR="+eurd);
			
		}
//		CHF
		eurPattern = Pattern.compile("Chinese Yuan\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)");
  		m = eurPattern.matcher(data);
		if (m.find()) {
			String eurS = m.group(3);
			eurS = eurS.replace(',','.');
			BigDecimal eurd = new BigDecimal(eurS);
        	BigDecimal reverse = usd.divide(eurd,15,BigDecimal.ROUND_CEILING); //unit always  5 or 10s integers.
//        	System.out.println(usd+"::"+bean.getExchangeRate());
        	bean.setExchangeRate(reverse);
        	bean.setCurrencyId("CHF");
        	factory.insert(bean);
		xmlString+="<CURRENCY><UNIT>1</UNIT><CURRENCYCODE>CHF</CURRENCYCODE><RATE>"+eurS+"</RATE></CURRENCY>";
			System.out.println("CHF="+eurd);
			
		}

//      JPY unit is 100.		
		BigDecimal yenUnit = new BigDecimal(100);
 		eurPattern = Pattern.compile("Rate of exchange of 100 units of foreign.*Japanese Yen\\s+(\\d+(\\.\\d+)?)\\s+(\\d+(\\.\\d+)?)", Pattern.DOTALL);
  		m = eurPattern.matcher(data);
		if (m.find()) {
			String eurS = m.group(3);
			eurS = eurS.replace(',','.');
			BigDecimal eurd = new BigDecimal(eurS);
        	BigDecimal reverse = usd.divide(eurd,15,BigDecimal.ROUND_CEILING); //unit always  5 or 10s integers.
        	reverse = reverse.multiply(yenUnit); //unit always  5 or 10s integers.
        	bean.setExchangeRate(reverse);
        	bean.setCurrencyId("JPY");
        	factory.insert(bean);
		xmlString+="<CURRENCY><UNIT>100</UNIT><CURRENCYCODE>JPY</CURRENCYCODE><RATE>"+eurS+"</RATE></CURRENCY>";		 
			System.out.println("JPY="+eurd);
			
		}
		xmlString+="</CURRENCIES>";		
      try {
    	  GenericProcedureFactory fac = new GenericProcedureFactory(dbManager);
    	  fac.doProcedure("p_exchange_rate_daily",new Vector());
      }
      catch(Exception e) {
    	  log.error("Error calling GLOBAL.p_exchange_rate_daily");
      }
//    save a dated copy      
      SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMdd" );
      String dateStr = effdate;//dateFormat.format(new Date());
      PrintWriter out = new PrintWriter(westcoDir+"bankofindia"+dateStr+".xml");
      out.println(xmlString);
      out.close();
// 	  save a current copy      
      out = new PrintWriter(westcoDirCopy+"bankofindia.xml");
      out.println(xmlString);
      out.close();
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
  
  //second phase to "/ftpdata/jails/sftp_wair/home/sftp_wair/currency_boin/"

  String westcoDir = "/home/ecommerce/currency_rates/";
  String westcoDirCopy = "/home/ecommerce/currency_rates/currency_boin/";
  
  public static void main(String argv[]) {
	try {
		String fin = "C:\\GeneratedJava\\PDFstamp\\csnt84-2017.pdf";
    	String datestr = " 02 June, 2017]";//22nd Sept, 2017";08th Sept, 2017];18th August, 2017];4th August, 2017]; 21st July, 2017]
		if(argv.length>=1)
			fin = argv[0];
		if(argv.length>=2)
			datestr = argv[1];
		String months[] = {"jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec"};
    	Pattern datePattern = Pattern.compile("(\\d+)\\w* (\\w+), (\\d\\d\\d\\d)");//+\\w* (\\w), (\\d\\d\\d\\d)");
  		Matcher m = datePattern.matcher(datestr);
		
		if (!m.find() || m.groupCount()!=3) {
			System.out.println("Cannot find Date String!!");
			System.exit(2);
		}
		String dates=m.group(1);
		String monthStr=m.group(2);
		String yearStr=m.group(3);
		String monthss="";

		if( dates.length() == 1) dates="0"+dates;
		monthStr = monthStr.toLowerCase().substring(0, 3);
		for(int i=0; i<= months.length;i++ ) {
			if( months[i].equals(monthStr) ) {
				monthss += (i+1);
				break;
			}
		}
		if( monthss.length() == 1) monthss="0"+monthss;
		String effdate = yearStr+monthss+dates;

	  BankOfIndiaCurrencyLoadProcess p = new BankOfIndiaCurrencyLoadProcess("TCM_OPS");
	  System.out.println("file:"+fin+":date:"+effdate);
	  p.load(fin,effdate);
	  }catch(Exception ex){};
  }
}
