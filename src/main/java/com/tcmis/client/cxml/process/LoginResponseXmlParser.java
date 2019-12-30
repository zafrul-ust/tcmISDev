package com.tcmis.client.cxml.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.cxml.beans.*;



public class LoginResponseXmlParser extends BaseProcess {

  public LoginResponseXmlParser(String client) {
    super(client);
  }
  
  public String getResponse(PunchOutSetupResponseBean bean) throws BaseException {
    String response = null;
    StringWriter sw = new StringWriter();

//  Successful.    
//    <?xml version = '1.0' encoding = 'UTF-8'?>
//    <response>
//    <header version="1.0">
//    <return returnCode="S"/>
//    </header>
//    <body>
//    <loginURL>
//    <![CDATA[http://exchange.oracle.com/orders/LinkinCallback.jsp?sessionKey=84vw2wn
//    uq1.ml0Tah9NrkSIrlaIpR9vmQLz/AbJphDGpQbvp6vJqReUbxaPaK--1733&action=shopping&lan
//    guage=US&searchKeywords=]]>
//    </loginURL>
//    </body>
//    </response>

// Failed.    
//    <?xml version = '1.0' encoding = 'UTF-8'?>
//    <response>
//    <header version="1.0">
//    <return returnCode="A">
//    <returnMessage>
//    <![CDATA[Authentication Failure]]>
//    </returnMessage>
//    </return>
//    </header>
//    <body>
//    <loginURL/>
//    </body>
//    </response>
    if( BaseProcess.isBlank(bean.getStartPageUrl())) {
	    sw.write("<?xml version = '1.0' encoding = 'UTF-8'?>");
	    sw.write("<response>");
	    sw.write("<header version='1.0'><return returnCode='A'><returnMessage><![CDATA[Authentication Failure]]></returnMessage></return></header>");
	    sw.write("<body><loginURL>");
	    sw.write("</loginURL></body></response>");
    }
    else {
	    sw.write("<?xml version = '1.0' encoding = 'UTF-8'?>");
	    sw.write("<response>");
	    sw.write("<header version='1.0'><return returnCode='S'/></header>");
	    sw.write("<body><loginURL>");
	    sw.write(bean.getStartPageUrl());
	    sw.write("</loginURL></body></response>");
    }
    response = sw.toString();
   return response;
  }
}