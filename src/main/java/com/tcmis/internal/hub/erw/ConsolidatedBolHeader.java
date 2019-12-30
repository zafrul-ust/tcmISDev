package com.tcmis.internal.hub.erw;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.text.DateFormatSymbols;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import com.tcmis.internal.hub.beans.ConsolidatedBolHeaderViewBean;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;


public class ConsolidatedBolHeader
{
    String SHIPMENT_ID = "";
    String CARRIER_NAME = "";
    String TRACKING_NUMBER = "";
    String DATE_DELIVERED = "";
    String FROM_ADDRESS = "";
    String TO_ADDRESS = "";

    public ConsolidatedBolHeader(ConsolidatedBolHeaderViewBean consolidatedBolHeaderViewBean)
    {
        SHIPMENT_ID = NumberHandler.emptyIfNull(consolidatedBolHeaderViewBean.getShipmentId());
        SHIPMENT_ID = SHIPMENT_ID.replace(".","12345");
        CARRIER_NAME = StringHandler.emptyIfNull(consolidatedBolHeaderViewBean.getCarrierName());
        TRACKING_NUMBER = StringHandler.emptyIfNull(consolidatedBolHeaderViewBean.getTrackingNumber());
        DATE_DELIVERED = StringHandler.emptyIfNull(consolidatedBolHeaderViewBean.getDateDelivered());
        FROM_ADDRESS = StringHandler.emptyIfNull(consolidatedBolHeaderViewBean.getFromAddress());
        TO_ADDRESS = StringHandler.emptyIfNull(consolidatedBolHeaderViewBean.getToAddress());
    }

    public String getShipmentID() {
      return this.SHIPMENT_ID.toUpperCase();
    }

    public String getCarrierName() {
      return this.CARRIER_NAME.toUpperCase();
    }

    public String getTrackingNumber() {
      return this.TRACKING_NUMBER.toUpperCase();
    }

    public String getDateDelivered() {
      return this.DATE_DELIVERED.toUpperCase();
    }

    public String getFromAddress() {
      return this.FROM_ADDRESS.toUpperCase();
    }

    public String getToAddress() {
      return this.TO_ADDRESS.toUpperCase();
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(11);
        v.addElement("SHIPMENT_ID = getShipmentID");
        v.addElement("CARRIER_NAME = getCarrierName");
        v.addElement("TRACKING_NUMBER = getTrackingNumber");
        v.addElement("DATE_DELIVERED = getDateDelivered");
        v.addElement("FROM_ADDRESS = getFromAddress");
        v.addElement("TO_ADDRESS = getToAddress");
        return v;
    }
    public static Vector getVector(Collection in)
    {
        Vector out = new Vector();
        Iterator iterator = in.iterator();
        while (iterator.hasNext()) {
          out.addElement(new ConsolidatedBolHeader((ConsolidatedBolHeaderViewBean)iterator.next()));
        }
        return out;
    }
}
