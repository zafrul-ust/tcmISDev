package com.tcmis.client.ups.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 20, 2008
 * Time: 2:36:06 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.digester.Digester;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.client.ups.beans.*;
import com.tcmis.client.raytheon.beans.XcblOrderBuyerContactBean;


public class AddressValidationResponseParser extends BaseProcess {
  public AddressValidationResponseParser(String client) {
    super(client);
  }

  public AddressValidationBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    AddressValidationBean addressValidationBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("AddressValidationResponse", AddressValidationBean.class);

      digester.addCallMethod("AddressValidationResponse/Response/TransactionReference", "setTransactionReference", 0);
      digester.addCallMethod("AddressValidationResponse/Response/ResponseStatusCode", "setResponseStatusCode", 0);
      digester.addCallMethod("AddressValidationResponse/Response/ResponseStatusDescription", "setResponseStatusDescription", 0);


      digester.addObjectCreate("AddressValidationResponse/AddressValidationResult", AddressValidationResultBean.class);
      digester.addSetNext("AddressValidationResponse/AddressValidationResult", "addAddressValidationResultBean");

      digester.addCallMethod("AddressValidationResponse/AddressValidationResult/Rank", "setRank", 0, bigDecimalClass);
      digester.addCallMethod("AddressValidationResponse/AddressValidationResult/Quality", "setQuality", 0, bigDecimalClass);
      digester.addCallMethod("AddressValidationResponse/AddressValidationResult/Address/City", "setCity", 0);
      digester.addCallMethod("AddressValidationResponse/AddressValidationResult/Address/StateProvinceCode", "setState", 0);
      digester.addCallMethod("AddressValidationResponse/AddressValidationResult/PostalCodeHighEnd", "setZipHighEnd", 0);
      digester.addCallMethod("AddressValidationResponse/AddressValidationResult/PostalCodeLowEnd", "setZipLowEnd", 0);

      digester.parse(file);
      addressValidationBean = (AddressValidationBean) digester.getRoot();
    }
    catch (Exception e) {
        System.out.println("Error:" + e.getMessage());
      log.error("Error:" + e.getMessage());
      e.printStackTrace(System.out);
      BaseException be = new BaseException(e);
      be.setMessageKey("");
      be.setRootCause(e);
      throw be;
    }
    return addressValidationBean;
  }

}
