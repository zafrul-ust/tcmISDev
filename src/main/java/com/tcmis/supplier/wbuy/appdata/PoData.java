package com.tcmis.supplier.wbuy.appdata;

/**
 * Title:        tcmIS
 * Description:
 * Copyright:    Copyright (c) 1998
 * Company:      URS/Radian Corp
 * @author Your Name
 * @version
 */

import java.util.Hashtable;
import java.util.Vector;

public class PoData
{
    String ITEM_ID = "";
    String ITEM_DESC="";
    String ORDER_QTY ="";
    String ORDER_UOM ="";
    String UNIT_PRICE_PAID ="";
    String TOTAL_PRICE_PAID ="";
    String SHEDULDE_PICKUP_DATE ="";
    int LINE_ITEM = 0;
    String CONTENT = "";
    String MFG_PART_NO = "";
    String CURRENCY_ID = "";


    public PoData(Hashtable h)
    {

        ITEM_ID = "";
        ITEM_DESC ="";
        ORDER_QTY ="";
        ORDER_UOM ="";
        UNIT_PRICE_PAID ="";
        TOTAL_PRICE_PAID ="";
        SHEDULDE_PICKUP_DATE ="";
        LINE_ITEM = 0;
        CONTENT = "";
        MFG_PART_NO = "";
        CURRENCY_ID = "";

        this.ITEM_ID = h.get("ITEM_ID").toString();
        this.ITEM_DESC = h.get("LINE_DESC").toString();
        this.ORDER_QTY = h.get("QTY").toString();
        this.ORDER_UOM = h.get("UOM").toString();
        this.UNIT_PRICE_PAID = h.get("UNIT_PRICE").toString();
        this.TOTAL_PRICE_PAID = h.get("TOTAL_PRICE_PAID").toString();
        this.CURRENCY_ID = h.get("CURRENCY_ID").toString();
        
        String projectedelivdate = h.get("VENDOR_SHIP_DATE").toString();
        String scpromiseddate = h.get("SCHED_PICK").toString();

        if (projectedelivdate.length() > 0)
        {
          this.SHEDULDE_PICKUP_DATE = projectedelivdate;
        }
        else
        {
          this.SHEDULDE_PICKUP_DATE = scpromiseddate;
        }
        //this.SHEDULDE_PICKUP_DATE =  h.get("SCHED_PICK").toString();

        this.LINE_ITEM = Integer.parseInt(h.get("LINE_ITEM").toString());
        this.MFG_PART_NO = h.get("MFG_PART_NO").toString();
    }
    public String getITEM_ID()
    {
        return ITEM_ID;
    }
    public int getLINE_ITEM()
    {
        return LINE_ITEM;
    }
    public String getITEM_DESC()
    {
        return ITEM_DESC;
    }
    public String getORDER_QTY()
    {
        return ORDER_QTY;
    }
    public String getORDER_UOM()
    {
        return ORDER_UOM;
    }
    public String getUNIT_PRICE_PAID()
    {
        return UNIT_PRICE_PAID;
    }
    public String getTOTAL_PRICE_PAID()
    {
        return TOTAL_PRICE_PAID;
    }
    public String getSHEDULDE_PICKUP_DATE()
    {
        return SHEDULDE_PICKUP_DATE;
    }
    public int LINE_ITEM()
    {
        return LINE_ITEM;
    }
    public String getMFG_PART_NO()
    {
        return MFG_PART_NO;
    }
    public String getCURRENCY_ID()
    {
        return CURRENCY_ID;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("ITEM_ID= getITEM_ID");
        v.addElement("ITEM_DESC= getITEM_DESC");
        v.addElement("ORDER_QTY = getORDER_QTY");
        v.addElement("ORDER_UOM = getORDER_UOM");
        v.addElement("UNIT_PRICE_PAID = getUNIT_PRICE_PAID");
        v.addElement("TOTAL_PRICE_PAID = getTOTAL_PRICE_PAID");
        v.addElement("SHEDULDE_PICKUP_DATE = getSHEDULDE_PICKUP_DATE");
        v.addElement("LINE_ITEM = getLINE_ITEM");
        v.addElement("MFG_PART_NO = getMFG_PART_NO");
        v.addElement("CURRENCY_ID = getCURRENCY_ID");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new PoData(h));
        }
        return out;
    }
}
