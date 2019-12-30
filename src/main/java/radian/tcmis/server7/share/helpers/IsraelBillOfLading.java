package radian.tcmis.server7.share.helpers;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.util.Hashtable;
import java.util.Vector;

public class IsraelBillOfLading
{
    String TAX_ID = "";
    String REQUIRED_DATETIME = "";
    String PRINT_DATE = "";
    String PO_LINE = "";
    String MR_LINE = "";
    String HUB_LOCATION_DESC = "";
    String HUB_ADDRESS_LINE_1 = "";
    String HUB_ADDRESS_LINE_2 = "";
    String HUB_ADDRESS_LINE_3 = "";
    String HUB_CITY = "";
    String HUB_STATE_ABBREV = "";
    String HUB_ZIP = "";
    String HUB_PHONE = "";
    String HUB_FAX = "";
    String SHIP_TO_LOCATION_DESC = "";
    String SHIP_TO_ADDRESS_LINE_1 = "";
    String SHIP_TO_ADDRESS_LINE_2 = "";
    String SHIP_TO_ADDRESS_LINE_3 = "";
    String SHIP_TO_CITY = "";
    String SHIP_TO_STATE_ABBREV = "";
    String SHIP_TO_ZIP = "";
    String SHIP_TO_PHONE = "";
    String SHIP_TO_FAX = "";
    String QUANTITY_SHIPPED = "";
    String RECEIPT_ID = "";
    String ITEM_DESC = "";
    String CAT_PART_NO = "";


    public IsraelBillOfLading(Hashtable h)
    {
        TAX_ID = (String)h.get("TAX_ID");
        REQUIRED_DATETIME = (String)h.get("REQUIRED_DATETIME");
        PRINT_DATE = (String)h.get("PRINT_DATE");
        PO_LINE = (String)h.get("PO_LINE");
        MR_LINE = (String)h.get("MR_LINE");
        HUB_LOCATION_DESC = (String)h.get("HUB_LOCATION_DESC");
        HUB_ADDRESS_LINE_1 = (String)h.get("HUB_ADDRESS_LINE_1");
        HUB_ADDRESS_LINE_2 = (String)h.get("HUB_ADDRESS_LINE_2");
        HUB_ADDRESS_LINE_3 = (String)h.get("HUB_ADDRESS_LINE_3");
        HUB_CITY = (String)h.get("HUB_CITY");
        HUB_STATE_ABBREV = (String)h.get("HUB_STATE_ABBREV");
        HUB_ZIP = (String)h.get("HUB_ZIP");
        HUB_PHONE = (String)h.get("HUB_PHONE");
        HUB_FAX = (String)h.get("HUB_FAX");
        SHIP_TO_LOCATION_DESC = (String)h.get("SHIP_TO_LOCATION_DESC");
        SHIP_TO_ADDRESS_LINE_1 = (String)h.get("SHIP_TO_ADDRESS_LINE_1");
        SHIP_TO_ADDRESS_LINE_2 = (String)h.get("SHIP_TO_ADDRESS_LINE_2");
        SHIP_TO_ADDRESS_LINE_3 = (String)h.get("SHIP_TO_ADDRESS_LINE_3");
        SHIP_TO_CITY = (String)h.get("SHIP_TO_CITY");
        SHIP_TO_STATE_ABBREV = (String)h.get("SHIP_TO_STATE_ABBREV");
        SHIP_TO_ZIP = (String)h.get("SHIP_TO_ZIP");
        SHIP_TO_PHONE = (String)h.get("SHIP_TO_PHONE");
        SHIP_TO_FAX = (String)h.get("SHIP_TO_FAX");
        QUANTITY_SHIPPED = (String)h.get("QUANTITY_SHIPPED");
        RECEIPT_ID = (String)h.get("RECEIPT_ID");
        ITEM_DESC = (String)h.get("ITEM_DESC");
        CAT_PART_NO = (String)h.get("CAT_PART_NO");
    }

    public String getTaxID() {
      return this.TAX_ID;
    }

    public String getRequiredDateTime()
    {
        return this.REQUIRED_DATETIME;
    }

    public String getprintDate()
    {
        return this.PRINT_DATE;
    }

    public String getPOLine()
    {
        return this.PO_LINE;
    }
    public String getMRLine()
    {
        return this.MR_LINE;
    }
    public String getHubLocationDesc()
    {
        return this.HUB_LOCATION_DESC;
    }
    public String getHubAddressLine1()
    {
        return this.HUB_ADDRESS_LINE_1;
    }
    public String getHubAddressLine2()
    {
        return this.HUB_ADDRESS_LINE_2;
    }
    public String getHubAddressLine3()
    {
        return this.HUB_ADDRESS_LINE_3;
    }
    public String getHubCity()
    {
        return this.HUB_CITY;
    }
    public String getHubStateAbbrev()
    {
        return this.HUB_STATE_ABBREV;
    }
    public String getHubZip()
    {
        return this.HUB_ZIP;
    }
    public String getHubPhone()
    {
        return this.HUB_PHONE;
    }
    public String getHubFax()
    {
        return this.HUB_FAX;
    }
    public String getShipToLocationDesc()
    {
        return this.SHIP_TO_LOCATION_DESC;
    }
    public String getShipToAddressLine1()
    {
        return this.SHIP_TO_ADDRESS_LINE_1;
    }
    public String getShipToAddressLine2()
    {
        return this.SHIP_TO_ADDRESS_LINE_2;
    }
    public String getShipToAddressLine3()
    {
        return this.SHIP_TO_ADDRESS_LINE_3;
    }
    public String getShipToCity()
    {
        return this.SHIP_TO_CITY;
    }
    public String getShipToStateAbbrev()
    {
        return this.SHIP_TO_STATE_ABBREV;
    }
    public String getShipToZip()
    {
        return this.SHIP_TO_ZIP;
    }
    public String getShipToPhone()
    {
        return this.SHIP_TO_PHONE;
    }
    public String getShipToFax()
    {
        return this.SHIP_TO_FAX;
    }
    public String getQuantityShipped()
    {
        return this.QUANTITY_SHIPPED;
    }
    public String getReceiptID()
    {
        return this.RECEIPT_ID;
    }
    public String getItemDesc()
    {
        return this.ITEM_DESC;
    }
    public String getCatPartNo()
    {
        return this.CAT_PART_NO;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(29);
        v.addElement("TAX_ID = getTaxID");
        v.addElement("REQUIRED_DATETIME = getFirstName");
        v.addElement("PRINT_DATE = getPhone");
        v.addElement("PO_LINE = getEmail");
        v.addElement("MR_LINE = ");
        v.addElement("HUB_LOCATION_DESC = getHubLocationDesc");
        v.addElement("HUB_ADDRESS_LINE_1 = getHubAddressLine1");
        v.addElement("HUB_ADDRESS_LINE_2 = getHubAddressLine2");
        v.addElement("HUB_ADDRESS_LINE_3 = getHubAddressLine3");
        v.addElement("HUB_CITY = getHubCity");
        v.addElement("HUB_STATE_ABBREV = getHubStateAbbrev");
        v.addElement("HUB_ZIP = getHubZip");
        v.addElement("HUB_PHONE = getHubPhone");
        v.addElement("HUB_FAX = getHubFax");
        v.addElement("SHIP_TO_LOCATION_DESC = getShipToLocationDesc");
        v.addElement("SHIP_TO_ADDRESS_LINE_1 = getShipToAddressLine1");
        v.addElement("SHIP_TO_ADDRESS_LINE_2 = getShipToAddressLine2");
        v.addElement("SHIP_TO_ADDRESS_LINE_3 = getShipToAddressLine3");
        v.addElement("SHIP_TO_CITY = getShipToCity");
        v.addElement("SHIP_TO_STATE_ABBREV = getShipToStateAbbrev");
        v.addElement("SHIP_TO_ZIP = getShipToZip");
        v.addElement("SHIP_TO_PHONE = getShipToPhone");
        v.addElement("SHIP_TO_FAX = getShipToFax");
        v.addElement("QUANTITY_SHIPPED = getQuantityShipped");
        v.addElement("RECEIPT_ID = getReceiptID");
        v.addElement("ITEM_DESC = getItemDesc");
        v.addElement("CAT_PART_NO = getCatPartNo");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new IsraelBillOfLading(h));
        }
        return out;
    }
}
