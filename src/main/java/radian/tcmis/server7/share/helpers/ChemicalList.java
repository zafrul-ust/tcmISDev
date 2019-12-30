package radian.tcmis.server7.share.helpers;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import java.util.Vector;

public class ChemicalList
{
    String CAS_NUMBER ="";
    String CHEMICAL = "";

    public ChemicalList(String[] oa)
    {
        CAS_NUMBER = oa[0];
        CHEMICAL = oa[1];
    }

    public String getCASNumber()
    {
        return CAS_NUMBER;
    }

    public String getChemical()
    {
        return CHEMICAL;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("CAS_NUMBER = getCASNumber");
        v.addElement("CHEMICAL = getChemical");
        return v;
    }
    public static Vector getVector(Vector in)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            String[] oa = (String[])in.elementAt(i);
            out.addElement(new ChemicalList(oa));
        }
        return out;
    }
}
