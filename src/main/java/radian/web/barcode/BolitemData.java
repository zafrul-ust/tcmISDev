package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

public class BolitemData
{

/*BILL_OF_LADING_VIEW.MR_LINE,
BILL_OF_LADING_VIEW.PO_NUMBER,
BILL_OF_LADING_VIEW.PR_NUMBER,
BILL_OF_LADING_VIEW.CARRIER,
BILL_OF_LADING_VIEW.PICKLIST_ID,
BILL_OF_LADING_VIEW.HUB_CITY,
BILL_OF_LADING_VIEW.HUB_ADDRESS_LINE_2,
BILL_OF_LADING_VIEW.HUB_ADDRESS_LINE_1,
BILL_OF_LADING_VIEW.HUB_LOCATION_DESC,
BILL_OF_LADING_VIEW.PO_RELEASE_NUMBER,
BILL_OF_LADING_VIEW.DOT,
BILL_OF_LADING_VIEW.TOTAL_QTY,
BILL_OF_LADING_VIEW.SHIPPER_ID,
BILL_OF_LADING_VIEW.CARRIER_NO,
BILL_OF_LADING_VIEW.SHIP_TO_CITY,
BILL_OF_LADING_VIEW.SHIP_TO_ADDRESS_LINE_2,
BILL_OF_LADING_VIEW.SHIP_TO_ADDRESS_LINE_1,
BILL_OF_LADING_VIEW.SHIP_TO_LOCATION_DESC,
BILL_OF_LADING_VIEW.CAT_PART_NO,
BILL_OF_LADING_VIEW.CATALOG_ID,
BILL_OF_LADING_VIEW.DEPARTMENT,
BILL_OF_LADING_VIEW.DELIVERY_POINT,
BILL_OF_LADING_VIEW.REQUIRED_DATETIME,
BILL_OF_LADING_VIEW.PACKAGING,
BILL_OF_LADING_VIEW.END_USER,
BILL_OF_LADING_VIEW.LINE_ITEM,
BILL_OF_LADING_VIEW.APPLICATION_DESC,
BILL_OF_LADING_VIEW.HAZARDOUS,
BILL_OF_LADING_VIEW.SHIP_TO_ZIP,
BILL_OF_LADING_VIEW.SHIP_DATE,
BILL_OF_LADING_VIEW.SHIP_TO_STATE_ABBREV,
BILL_OF_LADING_VIEW.HUB_ZIP,
BILL_OF_LADING_VIEW.HUB_STATE_ABBREV,
BILL_OF_LADING_VIEW.BATCH,
BILL_OF_LADING_VIEW.ITEM_DESC,
BILL_OF_LADING_VIEW.ITEM_ID,
BILL_OF_LADING_VIEW.QUANTITY_SHIPPED,
BILL_OF_LADING_VIEW.RECEIPT_ID,
BILL_OF_LADING_VIEW.MFG_LOT,
BILL_OF_LADING_VIEW.EXPIRE_DATE,*/

private String MR_LINE = "";
private String PO_NUMBER = "";
private String PR_NUMBER = "";
private String CARRIER = "";
private String PICKLIST_ID = "";
private String HUB_CITY = "";
private String HUB_ADDRESS_LINE_2 = "";
private String HUB_ADDRESS_LINE_1 = "";
private String HUB_LOCATION_DESC = "";
private String PO_RELEASE_NUMBER = "";
private String DOT = "";
private String TOTAL_QTY = "";
private String SHIPPER_ID = "";
private String CARRIER_NO = "";
private String SHIP_TO_CITY = "";
private String SHIP_TO_ADDRESS_LINE_2 = "";
private String SHIP_TO_ADDRESS_LINE_1 = "";
private String SHIP_TO_LOCATION_DESC = "";
private String CAT_PART_NO = "";
private String CATALOG_ID = "";
private String DEPARTMENT = "";
private String DELIVERY_POINT = "";
private String REQUIRED_DATETIME = "";
private String PACKAGING = "";
private String END_USER = "";
private String LINE_ITEM = "";
private String APPLICATION_DESC = "";
private String HAZARDOUS = "";
private String SHIP_TO_ZIP = "";
private String SHIP_DATE = "";
private String SHIP_TO_STATE_ABBREV = "";
private String HUB_ZIP = "";
private String HUB_STATE_ABBREV = "";
private String BATCH = "";
private String ITEM_DESC = "";
private String ITEM_ID = "";
private String QUANTITY_SHIPPED = "";
private String RECEIPT_ID = "";
private String MFG_LOT = "";
private String EXPIRE_DATE = "";

    public BolitemData(Hashtable h)
    {
        MR_LINE = "";
        PO_NUMBER = "";
        PR_NUMBER = "";
        CARRIER = "";
        PICKLIST_ID = "";
        HUB_CITY = "";
        HUB_ADDRESS_LINE_2 = "";
        HUB_ADDRESS_LINE_1 = "";
        HUB_LOCATION_DESC = "";
        PO_RELEASE_NUMBER = "";
        DOT = "";
        TOTAL_QTY = "";
        SHIPPER_ID = "";
        CARRIER_NO = "";
        SHIP_TO_CITY = "";
        SHIP_TO_ADDRESS_LINE_2 = "";
        SHIP_TO_ADDRESS_LINE_1 = "";
        SHIP_TO_LOCATION_DESC = "";
        CAT_PART_NO = "";
        CATALOG_ID = "";
        DEPARTMENT = "";
        DELIVERY_POINT = "";
        REQUIRED_DATETIME = "";
        PACKAGING = "";
        END_USER = "";
        LINE_ITEM = "";
        APPLICATION_DESC = "";
        HAZARDOUS = "";
        SHIP_TO_ZIP = "";
        SHIP_DATE = "";
        SHIP_TO_STATE_ABBREV = "";
        HUB_ZIP = "";
        HUB_STATE_ABBREV = "";
        BATCH = "";
        ITEM_DESC = "";
        ITEM_ID = "";
        QUANTITY_SHIPPED = "";
        RECEIPT_ID = "";
        MFG_LOT = "";
        EXPIRE_DATE = "";

        //System.out.println("Size in Barcode Data"+size);

        for(int i=0;i<40;i++)
        {
            switch(i)
            {
            case 0:
                this.MR_LINE = h.get("MR_LINE").toString().toUpperCase();
                break;
            case 1:
                this.PO_NUMBER = h.get("PO_NUMBER").toString().toUpperCase();
                break;
            case 2:
                this.PR_NUMBER = h.get("PR_NUMBER").toString().toUpperCase();
                break;
            case 3:
                this.CARRIER = h.get("CARRIER").toString().toUpperCase();
                break;
            case 4:
                this.PICKLIST_ID = h.get("PICKLIST_ID").toString().toUpperCase();
                break;
            case 5:
                this.HUB_CITY = h.get("HUB_CITY").toString().toUpperCase();
                break;
            case 6:
                this.HUB_ADDRESS_LINE_2 = h.get("HUB_ADDRESS_LINE_2").toString().toUpperCase();
                break;
            case 7:
                this.HUB_ADDRESS_LINE_1 = h.get("HUB_ADDRESS_LINE_1").toString().toUpperCase();
                break;
            case 8:
                this.HUB_LOCATION_DESC = h.get("HUB_LOCATION_DESC").toString().toUpperCase();
                break;
            case 9:
                this.PO_RELEASE_NUMBER = h.get("PO_RELEASE_NUMBER").toString().toUpperCase();
                break;
            case 10:
                this.DOT = h.get("DOT").toString().toUpperCase();
                break;
            case 11:
                this.TOTAL_QTY = h.get("TOTAL_QTY").toString().toUpperCase();
                break;
            case 12:
                this.SHIPPER_ID = h.get("SHIPPER_ID").toString().toUpperCase();
                break;
            case 13:
                this.CARRIER_NO = h.get("CARRIER_NO").toString().toUpperCase();
                break;
            case 14:
                this.SHIP_TO_CITY = h.get("SHIP_TO_CITY").toString().toUpperCase();
                break;
            case 15:
                this.SHIP_TO_ADDRESS_LINE_2 = h.get("SHIP_TO_ADDRESS_LINE_2").toString().toUpperCase();
                break;
            case 16:
                this.SHIP_TO_ADDRESS_LINE_1 = h.get("SHIP_TO_ADDRESS_LINE_1").toString().toUpperCase();
                break;
            case 17:
                this.SHIP_TO_LOCATION_DESC = h.get("SHIP_TO_LOCATION_DESC").toString().toUpperCase();
                break;
            case 18:
                this.CAT_PART_NO = h.get("CAT_PART_NO").toString().toUpperCase();
                break;
            case 19:
                this.CATALOG_ID = h.get("CATALOG_ID").toString().toUpperCase();
                break;
            case 20:
                this.DEPARTMENT = h.get("DEPARTMENT").toString().toUpperCase();
                break;
            case 21:
                this.DELIVERY_POINT = h.get("DELIVERY_POINT").toString().toUpperCase();
                break;
            case 22:
                this.REQUIRED_DATETIME = h.get("REQUIRED_DATETIME").toString().toUpperCase();
                break;
            case 23:
                this.PACKAGING = h.get("PACKAGING").toString().toUpperCase();
                break;
            case 24:
                this.END_USER = h.get("END_USER").toString().toUpperCase();
                break;
            case 25:
                this.LINE_ITEM = h.get("LINE_ITEM").toString().toUpperCase();
                break;
            case 26:
                this.APPLICATION_DESC = h.get("APPLICATION_DESC").toString().toUpperCase();
                break;
            case 27:
                this.HAZARDOUS = h.get("HAZARDOUS").toString().toUpperCase();
                break;
            case 28:
                this.SHIP_TO_ZIP = h.get("SHIP_TO_ZIP").toString().toUpperCase();
                break;
            case 29:
                this.SHIP_DATE = h.get("SHIP_DATE").toString().toUpperCase();
                break;
            case 30:
                this.SHIP_TO_STATE_ABBREV = h.get("SHIP_TO_STATE_ABBREV").toString().toUpperCase();
                break;
            case 31:
                this.HUB_ZIP = h.get("HUB_ZIP").toString().toUpperCase();
                break;
            case 32:
                this.HUB_STATE_ABBREV = h.get("HUB_STATE_ABBREV").toString().toUpperCase();
                break;
            case 33:
                this.BATCH = h.get("BATCH").toString().toUpperCase();
                break;
            case 34:
                this.ITEM_DESC = h.get("ITEM_DESC").toString().toUpperCase();
                break;
            case 35:
                this.ITEM_ID = h.get("ITEM_ID").toString().toUpperCase();
                break;
            case 36:
                this.QUANTITY_SHIPPED = h.get("QUANTITY_SHIPPED").toString().toUpperCase();
                break;
            case 37:
                this.RECEIPT_ID = h.get("RECEIPT_ID").toString().toUpperCase();
                break;
            case 38:
                this.MFG_LOT = h.get("MFG_LOT").toString().toUpperCase();
                break;
            case 39:
                this.EXPIRE_DATE = h.get("EXPIRE_DATE").toString().toUpperCase();
                break;
            /*case 40:
                this.DETAIL = h.get("DETAIL").toString().toUpperCase();
                break;*/
            }
        }
    }

  public String getDetail0Desc(){return MR_LINE;}
  public String getDetail1Desc(){return PO_NUMBER;}
  public String getDetail2Desc(){return PR_NUMBER;}
  public String getDetail3Desc(){return CARRIER;}
  public String getDetail4Desc(){return PICKLIST_ID ;}
  public String getDetail5Desc(){return HUB_CITY;}
  public String getDetail6Desc(){return HUB_ADDRESS_LINE_2;}
  public String getDetail7Desc(){return HUB_ADDRESS_LINE_1;}
  public String getDetail8Desc(){return HUB_LOCATION_DESC;}
  public String getDetail9Desc(){return PO_RELEASE_NUMBER;}
  public String getDetail10Desc(){return DOT;}
  public String getDetail11Desc(){return TOTAL_QTY;}
  public String getDetail12Desc(){return SHIPPER_ID;}
  public String getDetail13Desc(){return CARRIER_NO;}
  public String getDetail14Desc(){return SHIP_TO_CITY;}
  public String getDetail15Desc(){return SHIP_TO_ADDRESS_LINE_2;}
  public String getDetail16Desc(){return SHIP_TO_ADDRESS_LINE_1;}
  public String getDetail17Desc(){return SHIP_TO_LOCATION_DESC;}
  public String getDetail18Desc(){return CAT_PART_NO;}
  public String getDetail19Desc(){return CATALOG_ID;}
  public String getDetail20Desc(){return DEPARTMENT;}
  public String getDetail21Desc(){return DELIVERY_POINT;}
  public String getDetail22Desc(){return REQUIRED_DATETIME;}
  public String getDetail23Desc(){return PACKAGING;}
  public String getDetail24Desc(){return END_USER;}
  public String getDetail25Desc(){return LINE_ITEM;}
  public String getDetail26Desc(){return APPLICATION_DESC;}
  public String getDetail27Desc(){return HAZARDOUS;}
  public String getDetail28Desc(){return SHIP_TO_ZIP;}
  public String getDetail29Desc(){return SHIP_DATE;}
  public String getDetail30Desc(){return SHIP_TO_STATE_ABBREV;}
  public String getDetail31Desc(){return HUB_ZIP;}
  public String getDetail32Desc(){return HUB_STATE_ABBREV;}
  public String getDetail33Desc(){return BATCH;}
  public String getDetail34Desc(){return ITEM_DESC;}
  public String getDetail35Desc(){return ITEM_ID;}
  public String getDetail36Desc(){return QUANTITY_SHIPPED;}
  public String getDetail37Desc(){return RECEIPT_ID;}
  public String getDetail38Desc(){return MFG_LOT;}
  public String getDetail39Desc(){return EXPIRE_DATE;}
  //public String getDetail40Desc(){return DETAIL;}


  public static Vector getFieldVector()
  {
      Vector v = new Vector();
      v.addElement("MR_LINE = getDetail0Desc");
      v.addElement("PO_NUMBER = getDetail1Desc");
      v.addElement("PR_NUMBER = getDetail2Desc");
      v.addElement("CARRIER = getDetail3Desc");
      v.addElement("PICKLIST_ID = getDetail4Desc");
      v.addElement("HUB_CITY = getDetail5Desc");
      v.addElement("HUB_ADDRESS_LINE_2 = getDetail6Desc");
      v.addElement("HUB_ADDRESS_LINE_1 = getDetail7Desc");
      v.addElement("HUB_LOCATION_DESC = getDetail8Desc");
      v.addElement("PO_RELEASE_NUMBER = getDetail9Desc");
      v.addElement("DOT = getDetail10Desc");
      v.addElement("TOTAL_QTY = getDetail11Desc");
      v.addElement("SHIPPER_ID = getDetail12Desc");
      v.addElement("CARRIER_NO = getDetail13Desc");
      v.addElement("SHIP_TO_CITY = getDetail14Desc");
      v.addElement("SHIP_TO_ADDRESS_LINE_2 = getDetail15Desc");
      v.addElement("SHIP_TO_ADDRESS_LINE_1 = getDetail16Desc");
      v.addElement("SHIP_TO_LOCATION_DESC = getDetail17Desc");
      v.addElement("CAT_PART_NO = getDetail18Desc");
      v.addElement("CATALOG_ID = getDetail19Desc");
      v.addElement("DEPARTMENT = getDetail20Desc");
      v.addElement("DELIVERY_POINT = getDetail21Desc");
      v.addElement("REQUIRED_DATETIME = getDetail22Desc");
      v.addElement("PACKAGING = getDetail23Desc");
      v.addElement("END_USER = getDetail24Desc");
      v.addElement("LINE_ITEM = getDetail25Desc");
      v.addElement("APPLICATION_DESC = getDetail26Desc");
      v.addElement("HAZARDOUS = getDetail27Desc");
      v.addElement("SHIP_TO_ZIP = getDetail28Desc");
      v.addElement("SHIP_DATE = getDetail29Desc");
      v.addElement("SHIP_TO_STATE_ABBREV = getDetail30Desc");
      v.addElement("HUB_ZIP = getDetail31Desc");
      v.addElement("HUB_STATE_ABBREV = getDetail32Desc");
      v.addElement("BATCH = getDetail33Desc");
      v.addElement("ITEM_DESC = getDetail34Desc");
      v.addElement("ITEM_ID = getDetail35Desc");
      v.addElement("QUANTITY_SHIPPED = getDetail36Desc");
      v.addElement("RECEIPT_ID = getDetail37Desc");
      v.addElement("MFG_LOT = getDetail38Desc");
      v.addElement("EXPIRE_DATE = getDetail39Desc");
      //v.addElement("DETAIL = getDetail40Desc");

      return v;
  }
  public static Vector getVector(Vector in)
  {
      Vector out = new Vector();
      try
      {
        for(int i = 0; i < in.size(); i++)
        {
            //System.out.print(i);
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new BolitemData(h));
        }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }
      return out;
  }
}
