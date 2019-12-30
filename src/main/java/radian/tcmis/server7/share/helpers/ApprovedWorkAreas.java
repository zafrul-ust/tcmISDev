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

public class ApprovedWorkAreas
{
    String FACILITY_NAME = "";
    String WORK_AREAS = "";
    String USER_GROUP = "";
    String RESTRICTION_1 = "";
    String RESTRICTION_2 = "";
    String MEMBERS = "";

    public ApprovedWorkAreas(Hashtable h)
    {
        FACILITY_NAME = (String)h.get("FACILITY_NAME");
        WORK_AREAS = (String)h.get("WORK_AREAS");
        USER_GROUP = (String)h.get("USER_GROUP");
        RESTRICTION_1 = (String)h.get("RESTRICTION_1");
        RESTRICTION_2 = (String)h.get("RESTRICTION_2");
        MEMBERS = (String)h.get("MEMBERS");
    }

    public String getFacilityName() {
      return FACILITY_NAME;
    }

    public String getWorkAreas()
    {
        return WORK_AREAS;
    }

    public String getUserGroup()
    {
        return USER_GROUP;
    }

    public String getRestriction1()
    {
        return RESTRICTION_1;
    }

    public String getRestriction2()
    {
        return RESTRICTION_2;
    }

    public String getMembers()
    {
        return MEMBERS;
    }


    public static Vector getFieldVector()
    {
        Vector v = new Vector(7);
        v.addElement("FACILITY_NAME = getFacilityName");
        v.addElement("WORK_AREAS = getWorkAreas");
        v.addElement("USER_GROUP = getUserGroup");
        v.addElement("RESTRICTION_1 = getRestriction1");
        v.addElement("RESTRICTION_2 = getRestriction2");
        v.addElement("MEMBERS = getMembers");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new ApprovedWorkAreas(h));
        }
        return out;
    }
}
