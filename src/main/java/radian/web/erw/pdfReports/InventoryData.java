// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   InventoryData.java

package radian.web.erw.pdfReports;

import java.util.HashMap;
import java.util.Vector;

public class InventoryData
{

    public InventoryData(HashMap h, int size)
    {
        DETAIL_0 = "";
        DETAIL_1 = "";
        DETAIL_2 = "";
        DETAIL_3 = "";
        DETAIL_4 = "";
        DETAIL_5 = "";
        DETAIL_6 = "";
        DETAIL_7 = "";
        DETAIL_8 = "";
        DETAIL_9 = "";
        DETAIL_10 = "";
        DETAIL_11 = "";
        DETAIL_12 = "";
        DETAIL_13 = "";
        DETAIL_14 = "";
        DETAIL_0 = "";
        DETAIL_1 = "";
        DETAIL_2 = "";
        DETAIL_3 = "";
        DETAIL_4 = "";
        DETAIL_5 = "";
        DETAIL_6 = "";
        DETAIL_7 = "";
        DETAIL_8 = "";
        DETAIL_9 = "";
        DETAIL_10 = "";
        DETAIL_11 = "";
        DETAIL_12 = "";
        DETAIL_13 = "";
        DETAIL_14 = "";
        for(int i = 0; i < size; i++)
            switch(i)
            {
            case 0: // '\0'
                DETAIL_0 = h.get("DETAIL_0").toString();
                break;

            case 1: // '\001'
                DETAIL_1 = h.get("DETAIL_1").toString();
                break;

            case 2: // '\002'
                DETAIL_2 = h.get("DETAIL_2").toString();
                break;

            case 3: // '\003'
                DETAIL_3 = h.get("DETAIL_3").toString();
                break;

            case 4: // '\004'
                DETAIL_4 = h.get("DETAIL_4").toString();
                break;

            case 5: // '\005'
                DETAIL_5 = h.get("DETAIL_5").toString();
                break;

            case 6: // '\006'
                DETAIL_6 = h.get("DETAIL_6").toString();
                break;

            case 7: // '\007'
                DETAIL_7 = h.get("DETAIL_7").toString();
                break;

            case 8: // '\b'
                DETAIL_8 = h.get("DETAIL_8").toString();
                break;

            case 9: // '\t'
                DETAIL_9 = h.get("DETAIL_9").toString();
                break;

            case 10: // '\n'
                DETAIL_10 = h.get("DETAIL_10").toString();
                break;

            case 11: // '\013'
                DETAIL_11 = h.get("DETAIL_11").toString();
                break;

            case 12: // '\f'
                DETAIL_11 = h.get("DETAIL_11").toString();
                break;

            case 13: // '\r'
                DETAIL_11 = h.get("DETAIL_11").toString();
                break;

            case 14: // '\016'
                DETAIL_11 = h.get("DETAIL_11").toString();
                break;
            }

    }

    public String getDetail0Desc()
    {
        return DETAIL_0;
    }

    public String getDetail1Desc()
    {
        return DETAIL_1;
    }

    public String getDetail2Desc()
    {
        return DETAIL_2;
    }

    public String getDetail3Desc()
    {
        return DETAIL_3;
    }

    public String getDetail4Desc()
    {
        return DETAIL_4;
    }

    public String getDetail5Desc()
    {
        return DETAIL_5;
    }

    public String getDetail6Desc()
    {
        return DETAIL_6;
    }

    public String getDetail7Desc()
    {
        return DETAIL_7;
    }

    public String getDetail8Desc()
    {
        return DETAIL_8;
    }

    public String getDetail9Desc()
    {
        return DETAIL_9;
    }

    public String getDetail10Desc()
    {
        return DETAIL_10;
    }

    public String getDetail11Desc()
    {
        return DETAIL_11;
    }

    public String getDetail12Desc()
    {
        return DETAIL_12;
    }

    public String getDetail13Desc()
    {
        return DETAIL_13;
    }

    public String getDetail14Desc()
    {
        return DETAIL_14;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(15);
        v.addElement("DETAIL_0 = getDetail0Desc");
        v.addElement("DETAIL_1 = getDetail1Desc");
        v.addElement("DETAIL_2 = getDetail2Desc");
        v.addElement("DETAIL_3 = getDetail3Desc");
        v.addElement("DETAIL_4 = getDetail4Desc");
        v.addElement("DETAIL_5 = getDetail5Desc");
        v.addElement("DETAIL_6 = getDetail6Desc");
        v.addElement("DETAIL_7 = getDetail7Desc");
        v.addElement("DETAIL_8 = getDetail8Desc");
        v.addElement("DETAIL_9 = getDetail9Desc");
        v.addElement("DETAIL_10 = getDetail10Desc");
        v.addElement("DETAIL_11 = getDetail11Desc");
        v.addElement("DETAIL_12 = getDetail11Desc");
        v.addElement("DETAIL_13 = getDetail11Desc");
        v.addElement("DETAIL_14 = getDetail11Desc");
        return v;
    }

    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        try
        {
            String lastPartGroupItem = "";
            for(int i = 0; i < in.size(); i++)
            {
                HashMap h = (HashMap)in.elementAt(i);
                String partGroupItem = (String)h.get("DETAIL_1")+(String)h.get("DETAIL_3")+(String)h.get("DETAIL_6");
                if(!lastPartGroupItem.equals(partGroupItem))
                {
                    out.addElement(new InventoryData(h, h.size()));
                    lastPartGroupItem = partGroupItem;
                }
            }

        }
        catch(Exception e11)
        {
            e11.printStackTrace();
        }
        return out;
    }

    private String DETAIL_0;
    private String DETAIL_1;
    private String DETAIL_2;
    private String DETAIL_3;
    private String DETAIL_4;
    private String DETAIL_5;
    private String DETAIL_6;
    private String DETAIL_7;
    private String DETAIL_8;
    private String DETAIL_9;
    private String DETAIL_10;
    private String DETAIL_11;
    private String DETAIL_12;
    private String DETAIL_13;
    private String DETAIL_14;
}
