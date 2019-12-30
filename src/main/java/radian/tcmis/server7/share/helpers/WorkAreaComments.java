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

public class WorkAreaComments
{
    String COMMENTS = "";
    String BY = "";
    String DATE = "";

    public WorkAreaComments(Hashtable h)
    {
        COMMENTS = (String)h.get("COMMENTS");
        BY = (String)h.get("BY");
        DATE = (String)h.get("DATE");
    }

    public String getComments() {
      return COMMENTS;
    }

    public String getBy()
    {
        return BY;
    }

    public String getDate()
    {
        return DATE;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(3);
        v.addElement("COMMENTS = getComments");
        v.addElement("BY = getBy");
        v.addElement("DATE = getDate");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new WorkAreaComments(h));
        }
        return out;
    }
}
