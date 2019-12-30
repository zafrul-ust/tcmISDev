package radian.web.servlets.pos;


import java.util.Hashtable;
import java.util.Vector;

public class VendorQualData
{
    String QUAL_STMNT = "";
    String LINE_ITEM = "";

    public VendorQualData(Hashtable h)
    {
        QUAL_STMNT = "";
        LINE_ITEM = "";
        //System.out.println(h);
        this.QUAL_STMNT = h.get("VENDOR_QUAL_STMNT")== null ? "" : h.get("VENDOR_QUAL_STMNT").toString();
        this.LINE_ITEM = h.get("LINE_ITEM")== null ? "" : h.get("LINE_ITEM").toString();
    }

    public String getQUAL_STMNT()
    {
        return QUAL_STMNT;
    }

    public String getLINE_ITEM()
    {
        return LINE_ITEM;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("QUAL_STMNT = getQUAL_STMNT");
        v.addElement("LINE_ITEM = getLINE_ITEM");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new VendorQualData(h));
        }
        return out;
    }
}
