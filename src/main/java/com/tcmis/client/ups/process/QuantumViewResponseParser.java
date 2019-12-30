package com.tcmis.client.ups.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 6, 2008
 * Time: 3:22:07 PM
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


public class QuantumViewResponseParser extends BaseProcess {
  public QuantumViewResponseParser(String client) {
    super(client);
  }

  public QuantumViewBean parse(File file) throws BaseException {
    Class[] bigDecimalClass = {
        BigDecimal.class};
    Class[] dateClass = {
        Date.class};
    QuantumViewBean quantumViewBean = null;
    try {
      Digester digester = new Digester();
      digester.addObjectCreate("QuantumViewResponse", QuantumViewBean.class);

      digester.addCallMethod("QuantumViewResponse/Response/ResponseStatusDescription", "setResponseStatusDescription", 0);
      digester.addCallMethod("QuantumViewResponse/Response/Error/ErrorCode", "setError", 0);
      digester.addCallMethod("QuantumViewResponse/Response/Error/ErrorDescription", "setErrorDescription", 0);
      digester.addCallMethod("QuantumViewResponse/Bookmark", "setBookmark", 0);
      digester.addCallMethod("QuantumViewResponse/QuantumViewEvents/SubscriberID", "setSubscriberId", 0);

      digester.addObjectCreate("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents", QuantumViewResponseBean.class);
      digester.addSetNext("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents", "addQuantumViewResponseBean");

/*
      //this is a bit odd but I can't get an answer where the tracking number is actually passed...
      //I'm assuming that any pickupdate in the manifest for this subscriptionevent also applies to any delivery or origin object
      digester.addObjectCreate("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Delivery", PackageResultsBean.class);
      digester.addSetNext("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Delivery", "addPackageResultsBean");
      digester.addCallMethod("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Delivery/TrackingNumber", "setTrackingNumber", 0);
*/
      digester.addObjectCreate("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Origin", PackageResultsBean.class);
      digester.addSetNext("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Origin", "addPackageResultsBean");
      digester.addCallMethod("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Origin/TrackingNumber", "setTrackingNumber", 0);
      digester.addCallMethod("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Origin/Date", "setPickupDate", 0);
/*
      digester.addObjectCreate("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Manifest", ManifestBean.class);
      digester.addSetNext("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Manifest", "addManifestBean");
      digester.addCallMethod("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Manifest/PickupDate", "setPickupDate", 0);

      digester.addObjectCreate("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Manifest/Package", PackageResultsBean.class);
      digester.addSetNext("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Manifest/Package", "addPackageResultsBean");
      digester.addCallMethod("QuantumViewResponse/QuantumViewEvents/SubscriptionEvents/SubscriptionFile/Manifest/Package/TrackingNumber", "setTrackingNumber", 0);
*/

      digester.parse(file);
      quantumViewBean = (QuantumViewBean) digester.getRoot();
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
    return quantumViewBean;
  }

}