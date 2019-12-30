package radian.web.servlets.pos;


import java.util.Hashtable;
import java.util.Vector;

public class MoreCommentsData
{

    String MORECOMMENTS = "";
    //String ORDER ="";
    int MOREORDER =0;

    public MoreCommentsData(Hashtable h)
    {
        MORECOMMENTS = "";
        MOREORDER =0;

        String test = "";

        this.MORECOMMENTS = h.get("TEXT_LINE").toString();
        //this.ORDER = h.get("associate_text_window_order").toString();
        test = h.get("LINE_NUM").toString();
        MOREORDER = Integer.parseInt(test);

        //System.out.println(MOREORDER+MORECOMMENTS);
    }

    public String getMORECOMMENTS()
    {
        return MORECOMMENTS;
    }

    public int getMOREORDER()
    {
        return MOREORDER;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("MORECOMMENTS = getMORECOMMENTS");
        v.addElement("MOREORDER = getMOREORDER");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new MoreCommentsData(h));
        }
        return out;
    }
}
