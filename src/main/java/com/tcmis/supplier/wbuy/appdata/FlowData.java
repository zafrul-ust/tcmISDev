package com.tcmis.supplier.wbuy.appdata;

/**
 * Title:        tcmIS
 * Description:
 * Copyright:    Copyright (c) 1998
 * Company:      URS/Radian Corp
 * @author Your Name
 * @version
 *
 * 11-13-02
 * moving the line comments right next to the line
 */

import java.util.Hashtable;
import java.util.Vector;

public class FlowData
{
    String FLOW_DOWN_DESC = "";
    String CONTENT = "";
    String LINE_ITEM = "";

    public FlowData(Hashtable h)
    {
        FLOW_DOWN_DESC = "";
        CONTENT = "";
        LINE_ITEM = "";

        this.FLOW_DOWN_DESC = h.get("FLOW_DOWN_DESC").toString();
        this.CONTENT = h.get("CONTENT").toString();
        this.LINE_ITEM = h.get("LINE_ITEM").toString();
    }

    public String getFLOW_DOWN_DESC()
    {
        return FLOW_DOWN_DESC;
    }
    public String getCONTENT()
    {
        return CONTENT;
    }
    public String getLINE_ITEM()
    {
        return LINE_ITEM;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("FLOW_DOWN_DESC = getFLOW_DOWN_DESC");
        v.addElement("CONTENT = getCONTENT");
        v.addElement("LINE_ITEM = getLINE_ITEM");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new FlowData(h));
        }
        return out;
    }
}
