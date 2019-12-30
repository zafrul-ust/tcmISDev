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

public class AlternateApprover
{
    String LAST_NAME = "";
    String FIRST_NAME = "";
    String PHONE = "";
    String EMAIL = "";

    public AlternateApprover(Hashtable h)
    {
        LAST_NAME = (String)h.get("LAST_NAME");
        FIRST_NAME = (String)h.get("FIRST_NAME");
        PHONE = (String)h.get("PHONE");
        EMAIL = (String)h.get("EMAIL");
    }

    public String getLastName() {
      return LAST_NAME;
    }

    public String getFirstName()
    {
        return FIRST_NAME;
    }

    public String getPhone()
    {
        return PHONE;
    }

    public String getEmail()
    {
        return EMAIL;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(4);
        v.addElement("LAST_NAME = getLastName");
        v.addElement("FIRST_NAME = getFirstName");
        v.addElement("PHONE = getPhone");
        v.addElement("EMAIL = getEmail");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new AlternateApprover(h));
        }
        return out;
    }
}
