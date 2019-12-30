package radian.web.servlets.pos;


import java.util.Hashtable;
import java.util.Vector;

public class CommentsData
{

    String COMMENTS = "";
    String RADIAN_PO = "";
    String LINE_ITEM = "";
    //String ORDER ="";
    int ORDER =0;

    public CommentsData(Hashtable h)
    {
        COMMENTS = "";
        ORDER =0;

        String test = "";

        this.COMMENTS = h.get("associate_text_window").toString();
        //this.ORDER = h.get("associate_text_window_order").toString();
        test = h.get("associate_text_window_order").toString();
        ORDER = Integer.parseInt(test);

        this.RADIAN_PO = h.get("RADIAN_PO").toString();
        this.LINE_ITEM = h.get("LINE_ITEM").toString();
        //System.out.println(ORDER+COMMENTS);
    }

    public String getCOMMENTS()
    {
        return COMMENTS;
    }

    public int getORDER()
    {
        return ORDER;
    }

    public String getRADIAN_PO()
    {
        return RADIAN_PO;
    }

    public String getLINE_ITEM()
    {
        return LINE_ITEM;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("COMMENTS = getCOMMENTS");
        v.addElement("ORDER = getORDER");
        v.addElement("RADIAN_PO = getRADIAN_PO");
        v.addElement("LINE_ITEM = getLINE_ITEM");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new CommentsData(h));
        }
        return out;
    }
}
