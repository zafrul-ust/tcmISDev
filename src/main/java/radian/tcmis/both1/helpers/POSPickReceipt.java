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

public class POSPickReceipt
{
    String MR_LINE ="";
    String WORK_AREA = "";
    String CAT_PART_NO ="";
    String ITEM_ID ="";
    String QUANTITY ="";

    public POSPickReceipt(Hashtable h)
    {
        MR_LINE = h.get("MR_LINE").toString();
        WORK_AREA = h.get("WORK_AREA").toString();
        CAT_PART_NO = h.get("CAT_PART_NO").toString();
        ITEM_ID = h.get("ITEM_ID").toString();
        QUANTITY = h.get("QUANTITY").toString();
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

    public String getItemID()
    {
        return ITEM_ID;
    }

    public String getQuantity()
    {
        return QUANTITY;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("MR_LINE = getMRLine");
        v.addElement("WORK_AREA = getWorkArea");
        v.addElement("CAT_PART_NO = getCatPartNo");
        v.addElement("ITEM_ID = getItemID");
        v.addElement("QUANTITY = getQuantity");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new POSPickReceipt(h));
        }
        return out;
    }
}
