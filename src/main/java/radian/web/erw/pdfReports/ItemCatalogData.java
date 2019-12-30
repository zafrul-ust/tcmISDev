package radian.web.erw.pdfReports;

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
import java.util.Enumeration;

public class ItemCatalogData
{
    String itemID = "";
    String materialID = "";
    String materialDesc = "";
    String grade = "";
    String packaging = "";
    String mfgDesc = "";
    String country = "";
    String mfgPartNo = "";
    String shelfLifeStorageTemp = "";
    String msdsOnLine = "";
    String sampleSizing = "";
    String engEval = "";

    public ItemCatalogData() {
      //no data found
      materialDesc = "No Record Found.";
    }

    public ItemCatalogData(String[] data, String itemID) {
      this.itemID = itemID;
      int count = 1;
      materialID = data[count++];
      materialDesc = data[count++];
      grade = data[count++];
      packaging = data[count++];
      mfgDesc = data[count++];
      country = data[count++];
      mfgPartNo = data[count++];
      shelfLifeStorageTemp = data[count++];
      msdsOnLine = data[count++];
      sampleSizing = data[count++];
      engEval = data[count++];
    }

    public String getItemID() {
      return itemID;
    }

    public String getMaterialID()
    {
        return materialID;
    }

    public String getMaterialDesc()
    {
        return materialDesc;
    }
    public String getPackaging()
    {
        return packaging;
    }
    public String getGrade()
    {
        return grade;
    }
    public String getMfgDesc()
    {
        return mfgDesc;
    }
    public String getCountry()
    {
        return country;
    }
    public String getMfgPartNo()
    {
        return mfgPartNo;
    }
    public String getShelfLifeStorageTemp()
    {
        return shelfLifeStorageTemp;
    }
    public String getMsdsOnLine()
    {
        return msdsOnLine;
    }
    public String getSampleSizing()
    {
        return sampleSizing;
    }
    public String getEngEval()
    {
        return engEval;
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector(11);
        v.addElement("itemID = getItemID");
        v.addElement("materialID = getMaterialID");
        v.addElement("materialDesc = getMaterialDesc");
        v.addElement("grade = getGrade");
        v.addElement("packaging = getPackaging");
        v.addElement("mfgDesc = getMfgDesc");
        v.addElement("country = getCountry");
        v.addElement("mfgPartNo = getMfgPartNo");
        v.addElement("shelfLifeStorageTemp = getShelfLifeStorageTemp");
        v.addElement("msdsOnLine = getMsdsOnLine");
        v.addElement("sampleSizing = getSampleSizing");
        v.addElement("engEval = getEngEval");
        return v;
    }

    public static Vector getVector(Hashtable in)
    {
        Vector out = new Vector();
        if (in.isEmpty()) {
          out.addElement(new ItemCatalogData());
        }else {
          Enumeration enumCollec = in.keys();
          while (enumCollec.hasMoreElements()) {
            String key = enumCollec.nextElement().toString();
            Vector v = (Vector)in.get(key);
            for (int i = 0; i < v.size(); i++) {
              out.addElement(new ItemCatalogData((String[])v.elementAt(i),key));
            }
          }
        }
        return out;
    }
}
