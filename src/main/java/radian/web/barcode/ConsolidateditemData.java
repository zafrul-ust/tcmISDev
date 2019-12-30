package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class ConsolidateditemData
{
    private String PICKLIST_ID ="";
    /*private String MR_LINE ="";
    private String FACILITY_ID ="";
    private String APPLICATION ="";
    private String REQUESTOR ="";
    private String CATALOG_ID ="";*/
    private String CAT_PART_NO ="";
    private String PART_DESCRIPTION ="";
    private String ITEM_ID ="";
	private String BIN ="";
    /*private String LOT_STATUS ="";
    private String EXPIRE_DATE ="";
    private String MFG_LOT ="";
    private String INVENTORY_QUANTITY ="";*/
    private String PICKLIST_QUANTITY ="";
	private String RECEIPT_ID ="";
    /*private String QUANTITY_PICKED ="";
    private String PR_NUMBER ="";
    private String LINE_ITEM ="";
    private String DELIVERY_POINT ="";
    private String SHIP_TO_LOCATION_ID ="";
    private String STOCKING_METHOD ="";
    private String PACKAGING = "";
    private String END_USER = "";
	private String MR_NOTES = "";*/

    public ConsolidateditemData(Hashtable h,int size)
    {
        PICKLIST_ID ="";
        /*MR_LINE ="";
        FACILITY_ID ="";
        APPLICATION ="";
        REQUESTOR ="";
        CATALOG_ID ="";*/
        CAT_PART_NO ="";
        PART_DESCRIPTION ="";
        ITEM_ID ="";
		BIN ="";
        /*LOT_STATUS ="";
        EXPIRE_DATE ="";
        MFG_LOT ="";
        INVENTORY_QUANTITY ="";*/
        PICKLIST_QUANTITY ="";
		RECEIPT_ID ="";
        /*QUANTITY_PICKED ="";
        PR_NUMBER ="";
        LINE_ITEM ="";
        DELIVERY_POINT ="";
        SHIP_TO_LOCATION_ID ="";
        STOCKING_METHOD ="";
        PACKAGING = "";
        END_USER = "";
		MR_NOTES = "";*/

        for(int i=0;i<size;i++)
        {
            switch(i)
            {
			  case 0:
				this.PICKLIST_ID=h.get( "PICKLIST_ID" ).toString();
				break;
			  case 1:
				this.PART_DESCRIPTION=h.get( "PART_DESCRIPTION" ).toString();
				break;
			  case 2:
				this.ITEM_ID=h.get( "ITEM_ID" ).toString();
				break;
			  case 3:
				this.BIN=h.get( "BIN" ).toString();
				break;
			  case 4:
				this.PICKLIST_QUANTITY=h.get( "QUANTITY" ).toString();
				break;
			  case 5:
				this.RECEIPT_ID = h.get("RECEIPT_ID").toString();
				break;

            /*case 1:
                this.MR_LINE = h.get("MR_LINE").toString();
                break;
            case 2:
                this.FACILITY_ID = h.get("FACILITY_ID").toString();
                break;
            case 3:
                this.APPLICATION = h.get("APPLICATION").toString();
                break;
            case 4:
                this.REQUESTOR = h.get("REQUESTOR").toString();
                break;
            case 5:
                this.CATALOG_ID = h.get("CATALOG_ID").toString();
                break;*/
            case 6:
                this.CAT_PART_NO = h.get("CAT_PART_NO").toString();
                break;

            /*case 9:
                this.LOT_STATUS = h.get("LOT_STATUS").toString();
                break;
            case 10:
                this.EXPIRE_DATE = h.get("EXPIRE_DATE").toString();
                break;
            case 11:
                this.MFG_LOT = h.get("MFG_LOT").toString();
                break;
            case 14:
                this.INVENTORY_QUANTITY = h.get("INVENTORY_QUANTITY").toString();
                break;*/
          /*case 16:
                this.QUANTITY_PICKED = h.get("QUANTITY_PICKED").toString();
                break;
          case 17:
                this.PR_NUMBER = h.get("PR_NUMBER").toString();
                break;
          case 18:
                this.LINE_ITEM = h.get("LINE_ITEM").toString();
                break;
          case 19:
                this.DELIVERY_POINT = h.get("DELIVERY_POINT").toString();
                break;
          case 20:
                this.SHIP_TO_LOCATION_ID = h.get("SHIP_TO_LOCATION_ID").toString();
                break;
          case 21:
                this.STOCKING_METHOD = h.get("STOCKING_METHOD").toString();
                break;
          case 22:
                this.PACKAGING = h.get("PACKAGING").toString();
                break;
          case 23:
                this.END_USER = h.get("END_USER").toString();
                break;
		  case 24:
  			    this.MR_NOTES = h.get("MR_NOTES").toString();
			    break;*/
            }
        }
    }
  public String getDetail0Desc(){return PICKLIST_ID;}
/*  public String getDetail1Desc(){return MR_LINE;}
  public String getDetail2Desc(){return FACILITY_ID;}
  public String getDetail3Desc(){return APPLICATION;}
  public String getDetail4Desc(){return REQUESTOR;}
  public String getDetail5Desc(){return CATALOG_ID;}*/
  public String getDetail6Desc(){return CAT_PART_NO;}
  public String getDetail7Desc(){return PART_DESCRIPTION;}
  public String getDetail8Desc(){return ITEM_ID;}
  public String getDetail12Desc(){return BIN;}
/*  public String getDetail9Desc(){return LOT_STATUS;}
  public String getDetail10Desc(){return EXPIRE_DATE;}
  public String getDetail11Desc(){return MFG_LOT;}
  public String getDetail14Desc(){return INVENTORY_QUANTITY;}*/
  public String getDetail15Desc(){return PICKLIST_QUANTITY;}
  public String getDetail13Desc(){return RECEIPT_ID;}
/*  public String getDetail16Desc(){return QUANTITY_PICKED;}
  public String getDetail17Desc(){return PR_NUMBER;}
  public String getDetail18Desc(){return LINE_ITEM;}
  public String getDetail19Desc(){return DELIVERY_POINT;}
  public String getDetail20Desc(){return SHIP_TO_LOCATION_ID;}
  public String getDetail21Desc(){return STOCKING_METHOD;}
  public String getDetail22Desc(){return PACKAGING;}
  public String getDetail23Desc(){return END_USER;}
  public String getDetail24Desc(){return MR_NOTES;}*/

  public static Vector getFieldVector()
  {
      Vector v = new Vector();
      v.addElement("PICKLIST_ID = getDetail0Desc");
      /*v.addElement("MR_LINE = getDetail1Desc");
      v.addElement("FACILITY_ID = getDetail2Desc");
      v.addElement("APPLICATION = getDetail3Desc");
      v.addElement("REQUESTOR = getDetail4Desc");
      v.addElement("CATALOG_ID = getDetail5Desc");*/
      v.addElement("CAT_PART_NO = getDetail6Desc");
      v.addElement("PART_DESCRIPTION = getDetail7Desc");
      v.addElement("ITEM_ID = getDetail8Desc");
	  v.addElement("BIN = getDetail12Desc");
      /*v.addElement("LOT_STATUS = getDetail9Desc");
      v.addElement("EXPIRE_DATE = getDetail10Desc");
      v.addElement("MFG_LOT = getDetail11Desc");
      v.addElement("INVENTORY_QUANTITY = getDetail14Desc");*/
      v.addElement("PICKLIST_QUANTITY = getDetail15Desc");
	  v.addElement("RECEIPT_ID = getDetail13Desc");
      /*v.addElement("QUANTITY_PICKED = getDetail16Desc");
      v.addElement("PR_NUMBER = getDetail17Desc");
      v.addElement("LINE_ITEM = getDetail18Desc");
      v.addElement("DELIVERY_POINT = getDetail19Desc");
      v.addElement("SHIP_TO_LOCATION_ID = getDetail20Desc");
      v.addElement("STOCKING_METHOD = getDetail21Desc");
      v.addElement("PACKAGING = getDetail22Desc");
      v.addElement("END_USER = getDetail23Desc");
	  v.addElement("MR_NOTES = getDetail24Desc");*/

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
            out.addElement(new ConsolidateditemData(h, h.size()));
        }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }
      return out;
  }
}
