package radian.tcmis.both1.helpers;

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

public class OrderStatus
{
    String MR_LINE ="";
    String WORK_AREA ="";
    String CAT_PART_NO ="";
    String ORDERED_QTY ="";
    String ITEM_TYPE ="";
    String DELIVERY_TYPE ="";
    String NEED_DATE ="";
    String STATUS ="";
    String SOURCE ="";
    String ALLOCATED_QTY ="";
    String PROJECTED_DELIVERY ="";
    String NOTES ="";

    public OrderStatus(Hashtable h)
    {
        MR_LINE = h.get("MR_LINE").toString();
        WORK_AREA = h.get("WORK_AREA").toString();
        CAT_PART_NO = h.get("CAT_PART_NO").toString();
        ITEM_TYPE = h.get("ITEM_TYPE").toString();
        DELIVERY_TYPE = h.get("DELIVERY_TYPE").toString();
        NEED_DATE = h.get("NEED_DATE").toString();
        ORDERED_QTY = h.get("ORDERED_QTY").toString();
        STATUS = h.get("STATUS").toString();
        SOURCE = h.get("SOURCE").toString();
        ALLOCATED_QTY = h.get("ALLOCATED_QTY").toString();
        PROJECTED_DELIVERY = h.get("PROJECTED_DELIVERY").toString();
        NOTES = h.get("NOTES").toString();
    }
    public String getMRLine()
    {
        return MR_LINE;
    }

    public String getWorkArea()
    {
        return WORK_AREA;
    }

    public String getCatPartNo()
    {
        return CAT_PART_NO;
    }

    public String getItemType()
    {
        return ITEM_TYPE;
    }

    public String getDeliveryType()
    {
        return DELIVERY_TYPE;
    }

    public String getNeedDate()
    {
        return NEED_DATE;
    }

    public String getOrderedQty()
    {
        return ORDERED_QTY;
    }

    public String getStatus()
    {
        return STATUS;
    }

    public String getSource()
    {
        return SOURCE;
    }

    public String getAllocatedQty()
    {
        return ALLOCATED_QTY;
    }

    public String getProjectedDelivery()
    {
        return PROJECTED_DELIVERY;
    }

    public String getNotes()
    {
        return NOTES;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("MR_LINE = getMRLine");
        v.addElement("WORK_AREA = getWorkArea");
        v.addElement("CAT_PART_NO = getCatPartNo");
        v.addElement("ITEM_TYPE = getItemType");
        v.addElement("DELIVERY_TYPE = getDeliveryType");
        v.addElement("NEED_DATE = getNeedDate");
        v.addElement("ORDERED_QTY = getOrderedQty");
        v.addElement("STATUS = getStatus");
        v.addElement("SOURCE = getSource");
        v.addElement("ALLOCATED_QTY = getAllocatedQty");
        v.addElement("PROJECTED_DELIVERY = getProjectedDelivery");
        v.addElement("NOTES = getNotes");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new OrderStatus(h));
        }
        return out;
    }
}
