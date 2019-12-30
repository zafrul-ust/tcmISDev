package com.tcmis.client.pnnl.process;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.pnnl.beans.PunchOutInspectRequestBean;


public class PunchOutInspectRequestParser extends BaseProcess {
   public PunchOutInspectRequestParser(String client) {
    super(client);
  }

  public PunchOutInspectRequestBean parse(File file) throws BaseException {
     Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    PunchOutInspectRequestBean punchOutInspectRequestBean = null;    
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("cXML", PunchOutInspectRequestBean.class);
      digester.addSetProperties("cXML");
      digester.addSetProperties("cXML", "payloadID", "payloadId");
      digester.addSetProperties("cXML", "timestamp", "timestamp");
      //header section
      digester.addSetProperties("cXML/Header/From/Credential", "domain", "fromDomain");
      digester.addCallMethod("cXML/Header/From/Credential/Identity", "setFromIdentity", 0);
      digester.addSetProperties("cXML/Header/To/Credential", "domain", "toDomain");
      digester.addCallMethod("cXML/Header/To/Credential/Identity", "setToIdentity", 0);
      digester.addSetProperties("cXML/Header/Sender/Credential", "domain", "senderDomain");
      digester.addCallMethod("cXML/Header/Sender/Credential/Identity", "setSenderIdentity", 0);
      digester.addCallMethod("cXML/Header/Sender/Credential/SharedSecret", "setSharedSecret", 0);
      digester.addCallMethod("cXML/Header/Sender/UserAgent", "setUserAgent", 0);
      //request section
      digester.addSetProperties("cXML/Request/PunchOutSetupRequest", "operation", "operation");
      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/BuyerCookie", "setBuyerCookie", 0);
      //right now we always get one Extrinisic name but that might not always be the case in the future
      //digester.addSetProperties("cXML/Request/PunchOutSetupRequest/Extrinsic", "name", "extrinsicName");
      //digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Extrinsic", "setExtrinisicName", 0);
      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/BrowserFormPost/URL", "setBrowserFormPost", 0);
      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Contact/Name", "setContactName", 0);
      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Contact/Email", "setContactEmail", 0);
      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/SupplierSetup/URL", "setSupplierSetupUrl", 0);
      digester.addCallMethod("cXML/Request/PunchOutSetupRequest/ItemOut/ItemID/SupplierPartID", "setSupplierPartId", 0);

      // * no extrinsic data for PNNL
      //digester.addCallMethod("cXML/Request/PunchOutSetupRequest/Extrinsic", "addAdditionalInfo", 2);
      //digester.addCallParam("cXML/Request/PunchOutSetupRequest/Extrinsic", 0, "name");
      //digester.addCallParam("cXML/Request/PunchOutSetupRequest/Extrinsic", 1);

      digester.parse(file);
      punchOutInspectRequestBean = (PunchOutInspectRequestBean) digester.getRoot();
    }
    catch (Exception e) {
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("");
      be.setRootCause(e);
      throw be;
    }
    return punchOutInspectRequestBean;
  }
  
}