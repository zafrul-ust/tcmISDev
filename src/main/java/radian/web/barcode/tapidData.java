package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-15-03 - adding END_USER to the picklist
 */

public class tapidData
{
    private String RECEIPT_ID1 ="";
    private String RECEIPT_ID ="";
    private String ITEM_PKG ="";
    private String ITEM_DESC ="";

    public tapidData(Hashtable h,int size)
    {
	    RECEIPT_ID1 ="";
		RECEIPT_ID ="";
		ITEM_PKG ="";
		ITEM_DESC ="";

        for(int i=0;i<size;i++)
        {
            switch(i)
            {
            case 0:
                this.RECEIPT_ID1 = h.get("RECEIPT_ID1").toString();
                break;
            case 1:
                this.RECEIPT_ID = h.get("RECEIPT_ID").toString();
                break;
            case 2:
                this.ITEM_PKG = h.get("ITEM_PKG").toString();
                break;
            case 3:
                this.ITEM_DESC = h.get("ITEM_DESC").toString();
                break;
            }
        }
    }
  public String getDetail0Desc(){return RECEIPT_ID1;}
  public String getDetail1Desc(){return RECEIPT_ID;}
  public String getDetail2Desc(){return ITEM_PKG;}
  public String getDetail3Desc(){return ITEM_DESC;}

  public static Vector getFieldVector()
  {
      Vector v = new Vector();
      v.addElement("RECEIPT_ID1 = getDetail0Desc");
      v.addElement("RECEIPT_ID = getDetail1Desc");
      v.addElement("ITEM_PKG = getDetail2Desc");
      v.addElement("ITEM_DESC = getDetail3Desc");
      return v;
  }
  public static Vector getVector(Vector in)
  {
      Vector out = new Vector();
      try
      {
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new tapidData(h, h.size()));
        }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }
      return out;
  }
}
