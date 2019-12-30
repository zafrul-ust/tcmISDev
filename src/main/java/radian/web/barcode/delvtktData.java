package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

public class delvtktData
{

    private String DETAIL_0 ="";
    private String DETAIL_1 ="";
    private String DETAIL_2 ="";
    private String DETAIL_3 ="";
    private String DETAIL_4 ="";
    private String DETAIL_5 ="";
    private String DETAIL_6 ="";
    private String DETAIL_7 ="";
    private String DETAIL_8 ="";
    private String DETAIL_9 ="";
    private String DETAIL_10 ="";
    private String DETAIL_11 ="";
    private String DETAIL_12 ="";
    private String DETAIL_13 ="";
    private String DETAIL_14 ="";
    private String DETAIL_15 ="";

    private String detailname = "";

    public delvtktData(Hashtable h,int size)
    {
        DETAIL_0 ="";
        DETAIL_1 ="";
        DETAIL_2 ="";
        DETAIL_3 ="";
        DETAIL_4 ="";
        DETAIL_5 ="";
        DETAIL_6 ="";
        DETAIL_7 ="";
        DETAIL_8 ="";
        DETAIL_9 ="";
        DETAIL_10 ="";
        DETAIL_11 ="";
        DETAIL_12 ="";
        DETAIL_13 ="";
        DETAIL_14 ="";
        DETAIL_15 ="";

        for(int i=0;i<size;i++)
        {
            switch(i)
            {
            case 0:
                this.DETAIL_0 = h.get("PICKLIST_ID").toString();
                break;
            case 1:
                this.DETAIL_1 = h.get("MR_LINE").toString();
                break;
            case 2:
                this.DETAIL_2 = h.get("FACILITY_ID").toString();
                break;
            case 3:
                this.DETAIL_3 = h.get("APPLICATION_DESC").toString();
                break;
            case 4:
                this.DETAIL_4 = h.get("REQUESTOR").toString();
                break;
            case 5:
                this.DETAIL_5 = h.get("CAT_PART_NO").toString();
                break;
            case 6:
                this.DETAIL_6 = h.get("ITEM_ID").toString();
                break;
            case 7:
                this.DETAIL_7 = h.get("PART_DESCRIPTION").toString();
                break;
            case 8:
                this.DETAIL_8 = h.get("QUANTITY_PICKED").toString();
                break;
            case 9:
                this.DETAIL_9 = h.get("SHIP_TO_LOCATION_ID").toString();
                break;
            case 10:
                this.DETAIL_10 = h.get("RECEIPT_ID").toString();
                break;
            case 11:
                this.DETAIL_11 = h.get("EXPIRE_DATE").toString();
                break;
            case 12:
                this.DETAIL_12 = h.get("MFG_LOT").toString();
                break;
            case 13:
                this.DETAIL_13 = h.get("PACKAGING").toString();
                break;
            case 14:
                this.DETAIL_14 = h.get("COMPANY_ID").toString();
                break;
            case 15:
              this.DETAIL_15 = h.get("END_USER").toString();
              break;
            }
        }
    }
  public String getDetail0Desc(){return DETAIL_0;}
  public String getDetail1Desc(){return DETAIL_1;}
  public String getDetail2Desc(){return DETAIL_2;}
  public String getDetail3Desc(){return DETAIL_3;}
  public String getDetail4Desc(){return DETAIL_4;}
  public String getDetail5Desc(){return DETAIL_5;}
  public String getDetail6Desc(){return DETAIL_6;}
  public String getDetail7Desc(){return DETAIL_7;}
  public String getDetail8Desc(){return DETAIL_8;}
  public String getDetail9Desc(){return DETAIL_9;}
  public String getDetail10Desc(){return DETAIL_10;}
  public String getDetail11Desc(){return DETAIL_11;}
  public String getDetail12Desc(){return DETAIL_12;}
  public String getDetail13Desc(){return DETAIL_13;}
  public String getDetail14Desc(){return DETAIL_14;}
  public String getDetail15Desc(){return DETAIL_15;}

  public static Vector getFieldVector(int size)
  {
      Vector v = new Vector();
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
      v.addElement("DETAIL_12 = getDetail12Desc");
      v.addElement("DETAIL_13 = getDetail13Desc");
      v.addElement("DETAIL_14 = getDetail14Desc");
      v.addElement("DETAIL_15 = getDetail15Desc");
      return v;
  }
  public static Vector getVector(Vector in,int size)
  {
      Vector out = new Vector();
      try
      {
        for(int i = 0; i < in.size(); i++)
        {
          Hashtable h = (Hashtable)in.elementAt(i);
          out.addElement(new delvtktData(h,size));
        }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }
      return out;
  }
}
