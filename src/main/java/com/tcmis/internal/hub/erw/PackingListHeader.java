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
import com.tcmis.internal.hub.beans.PackingListHeaderViewBean;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;


public class PackingListHeader
{
    String SHIPMENT_ID = "";
    String DATE_PICKED = "";
    String DATE_PRINTED = "";
    String BRANCH_PLANT = "";
    String TAX_ID = "";
    String COMPANY_ID = "";
    String SHIP_TO_LOCATION_ID = "";
    String INVENTORY_GROUP = "";
    String HUB_COMPANY_ID = "";
    String HUB_LOCATION_ID = "";
    String SHIP_TO_ADDRESS = "";
    String HUB_ADDRESS = "";
    String PAGE_DESC = "";

    public PackingListHeader(PackingListHeaderViewBean packingListHeaderViewBean, String pageDesc)
    {
        PAGE_DESC = pageDesc;
        SHIPMENT_ID = NumberHandler.emptyIfNull(packingListHeaderViewBean.getShipmentId());
        DATE_PICKED = StringHandler.emptyIfNull(packingListHeaderViewBean.getDatePicked());
        DATE_PRINTED = StringHandler.emptyIfNull(packingListHeaderViewBean.getDatePrinted());
        BRANCH_PLANT = StringHandler.emptyIfNull(packingListHeaderViewBean.getBranchPlant());
        TAX_ID = StringHandler.emptyIfNull(packingListHeaderViewBean.getTaxId());
        COMPANY_ID = StringHandler.emptyIfNull(packingListHeaderViewBean.getCompanyId());
        SHIP_TO_LOCATION_ID = StringHandler.emptyIfNull(packingListHeaderViewBean.getShipToLocationId());
        INVENTORY_GROUP = StringHandler.emptyIfNull(packingListHeaderViewBean.getInventoryGroup());
        HUB_COMPANY_ID = StringHandler.emptyIfNull(packingListHeaderViewBean.getHubCompanyId());
        HUB_LOCATION_ID = StringHandler.emptyIfNull(packingListHeaderViewBean.getHubLocationId());
        SHIP_TO_ADDRESS = StringHandler.emptyIfNull(packingListHeaderViewBean.getShipToAddress());
        HUB_ADDRESS = StringHandler.emptyIfNull(packingListHeaderViewBean.getHubAddress());
    }

    public String getPageDesc() {
      return this.PAGE_DESC;
    }

    public String getShipmentID() {
      return this.SHIPMENT_ID;
    }

    public String getDatePicked() {
      return this.DATE_PICKED;
    }

    public String getDatePrinted() {
      return this.DATE_PRINTED;
    }

    public String getBranchPlant() {
      return this.BRANCH_PLANT;
    }

    public String getTaxID() {
      return this.TAX_ID;
    }

    public String getCompanyID() {
      return this.COMPANY_ID;
    }

    public String getShipToLocationID() {
      return this.SHIP_TO_LOCATION_ID;
    }

    public String getInventoryGroup() {
      return this.INVENTORY_GROUP;
    }

    public String getHubCompanyID() {
      return this.HUB_COMPANY_ID;
    }

    public String getHubLocationID() {
      return this.HUB_LOCATION_ID;
    }

    public String getShipToAddress() {
      return this.SHIP_TO_ADDRESS;
    }

    public String getHubAddress()
    {
        return this.HUB_ADDRESS;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(41);
        v.addElement("PAGE_DESC = getPageDesc");
        v.addElement("SHIPMENT_ID = getShipmentID");
        v.addElement("DATE_PICKED = getDatePicked");
        v.addElement("DATE_PRINTED = getDatePrinted");
        v.addElement("BRANCH_PLANT = getBranchPlant");
        v.addElement("TAX_ID = getTaxID");
        v.addElement("COMPANY_ID = getCompanyID");
        v.addElement("SHIP_TO_LOCATION_ID = getShipToLocationID");
        v.addElement("INVENTORY_GROUP = getInventoryGroup");
        v.addElement("HUB_COMPANY_ID = getHubCompanyID");
        v.addElement("HUB_LOCATION_ID = getHubLocationID");
        v.addElement("SHIP_TO_ADDRESS = getShipToAddress");
        v.addElement("HUB_ADDRESS = getHubAddress");
        return v;
    }
    public static Vector getVector(Collection in, int numberOfCopies)
    {
        Vector out = new Vector();
        Iterator iterator = in.iterator();
        while (iterator.hasNext()) {
            PackingListHeaderViewBean packingListHeaderViewBean = (PackingListHeaderViewBean)iterator.next();
            if (numberOfCopies > 1) {
              out.addElement(new PackingListHeader(packingListHeaderViewBean, "(Original)"));
              for (int i = 1; i < numberOfCopies; i++) {
                out.addElement(new PackingListHeader(packingListHeaderViewBean, "(Copy " + i + ")"));
              }
            }else {
              out.addElement(new PackingListHeader(packingListHeaderViewBean, ""));
            }
        }
        return out;
    }
}
