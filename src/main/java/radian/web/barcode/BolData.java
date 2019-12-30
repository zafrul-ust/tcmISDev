package radian.web.barcode;

import com.tcmis.common.util.StringHandler;

import java.util.Hashtable;
import java.util.Vector;

public class BolData
{
// * 10-26-03 - Adding Net Weight to the BOL
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
private String NET_WEIGHT = "";
private String CHARGE_NUMBER = "";
private String BILL_TO_ADDRESS_1 = "";
private String BILL_TO_ADDRESS_2 = "";
private String BILL_TO_ADDRESS_3 = "";
private String BILL_TO_ADDRESS_4 = "";
private String OCONUS = "";
private String TRACKING_NUMBER = "";
private String FREIGHT_ORDER_NUMBER = "";
private String TRACKING_NUMBER_BARCODE = "";
private String SHIP_TO_ADDRESS = "";
private String INVENTORY_GROUP = "";
private String NUMBER_OF_BOXES = "";

    public BolData(Hashtable h,int size)
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
				NET_WEIGHT = "";
				CHARGE_NUMBER = "";
        BILL_TO_ADDRESS_1 = "";
        BILL_TO_ADDRESS_2 = "";
        BILL_TO_ADDRESS_3 = "";
        BILL_TO_ADDRESS_4 = "";
        OCONUS = "";
        TRACKING_NUMBER = "";
        FREIGHT_ORDER_NUMBER = "";
        TRACKING_NUMBER_BARCODE = "";
        SHIP_TO_ADDRESS = "";
        INVENTORY_GROUP = "";
        NUMBER_OF_BOXES = "";
        //System.out.println("Size in Barcode Data"+size);

        for(int i=0;i<size;i++)
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
					 String tmpPrNumber = h.get("PR_NUMBER").toString();
					 this.PR_NUMBER = tmpPrNumber.replace(".","12345");
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
            case 40:
                this.NET_WEIGHT = h.get("NET_WEIGHT").toString().toUpperCase();
                break;
						case 41:
						 this.CHARGE_NUMBER = h.get("CHARGE_NUMBER").toString().toUpperCase();
						 break;
						case 42:
						 this.BILL_TO_ADDRESS_1 = "BILL TO:"+ h.get("BILL_TO_ADDRESS_1").toString().toUpperCase();
						 break;
						case 43:
						 this.BILL_TO_ADDRESS_2 = h.get("BILL_TO_ADDRESS_2").toString().toUpperCase();
						 break;
						case 44:
						 this.BILL_TO_ADDRESS_3 = h.get("BILL_TO_ADDRESS_3").toString().toUpperCase();
						 break;
						case 45:
						 this.BILL_TO_ADDRESS_4 = h.get("BILL_TO_ADDRESS_4").toString().toUpperCase();
						 break;
                        case 46:
						 this.OCONUS = h.get("OCONUS").toString().toUpperCase();
						 break;
						case 47:
                         if (h.get("TRACKING_NUMBER").toString().trim().length() > 0)
                          {
                            try {
                                TRACKING_NUMBER_BARCODE = StringHandler.replace(h.get("TRACKING_NUMBER").toString()," ","=");
                                TRACKING_NUMBER_BARCODE = "*" +TRACKING_NUMBER_BARCODE + "*";
                                 }
                                 catch (Exception ex) {
                                ex.printStackTrace();
                               }
                          }                          
                         break;
                  case 48:
                   this.FREIGHT_ORDER_NUMBER ="FO# "+ h.get("FREIGHT_ORDER_NUMBER").toString().toUpperCase();
                   break;
                  case 49:
                    if (h.get("TRACKING_NUMBER").toString().length() > 0)
                     {
                     this.TRACKING_NUMBER = "PRO# "+ h.get("TRACKING_NUMBER").toString().toUpperCase();
                     }
                   break;
                  case 50:
                   this.SHIP_TO_ADDRESS =h.get("SHIP_TO_ADDRESS").toString();
                   break;
                   case 51:
                   String inventoryGroup = h.get("INVENTORY_GROUP").toString();
                    if ("Boston BAE-NH".equalsIgnoreCase(inventoryGroup) || "Hudson PTP".equalsIgnoreCase(inventoryGroup)
                    || "Londonderry MAN".equalsIgnoreCase(inventoryGroup) || "Merrimack MER".equalsIgnoreCase(inventoryGroup)
                    || "Nashua NCA".equalsIgnoreCase(inventoryGroup) || "Nashua NHQ".equalsIgnoreCase(inventoryGroup)
                    || "Nashua MEC".equalsIgnoreCase(inventoryGroup) ||  "BAE Austin".equalsIgnoreCase(inventoryGroup))
                   {
                        this.INVENTORY_GROUP ="We certify that the products shipped by Haas Group International to your " +
                                "company are in conformance with the quality requirements provided by your company at the time of purchase. " +
                                "Batch specific test results and/or inspection paperwork are on record and are available upon request to " +
                                "Haas Group International.\n\nQC’d by:_____________________________________";
                   }
                   break;
                   case 52:
					 this.NUMBER_OF_BOXES = h.get("NUMBER_OF_BOXES").toString();
					 break;                         
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
  public String getDetail40Desc(){return NET_WEIGHT;}
  public String getDetail41Desc(){return CHARGE_NUMBER;}
  public String getDetail42Desc(){return BILL_TO_ADDRESS_1;}
  public String getDetail43Desc(){return BILL_TO_ADDRESS_2;}
  public String getDetail46Desc(){return BILL_TO_ADDRESS_3;}
  public String getDetail47Desc(){return BILL_TO_ADDRESS_4;}
  public String getDetail44Desc(){return OCONUS;}
  public String getDetail45Desc(){return TRACKING_NUMBER;}
    public String getDetail48Desc(){return FREIGHT_ORDER_NUMBER;}
    public String getDetail49Desc(){return TRACKING_NUMBER_BARCODE;}
    public String getShipToAddress(){return SHIP_TO_ADDRESS;}
public String getInventoryGroup(){return INVENTORY_GROUP;}
public String getDetail52Desc(){return NUMBER_OF_BOXES;}

  public static Vector getFieldVector(int size)
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
      v.addElement("NET_WEIGHT = getDetail40Desc");
			v.addElement("CHARGE_NUMBER = getDetail41Desc");
			v.addElement("BILL_TO_ADDRESS_1 = getDetail42Desc");
			v.addElement("BILL_TO_ADDRESS_2 = getDetail43Desc");
			v.addElement("BILL_TO_ADDRESS_3 = getDetail46Desc");
			v.addElement("BILL_TO_ADDRESS_4 = getDetail47Desc");    
      v.addElement("OCONUS = getDetail44Desc");
			v.addElement("TRACKING_NUMBER = getDetail45Desc");
      v.addElement("FREIGHT_ORDER_NUMBER = getDetail48Desc");
      v.addElement("TRACKING_NUMBER_BARCODE = getDetail49Desc");
      v.addElement("SHIP_TO_ADDRESS = getShipToAddress");
      v.addElement("INVENTORY_GROUP = getInventoryGroup");
      v.addElement("NUMBER_OF_BOXES = getDetail52Desc");

      return v;
  }
  public static Vector getVector(Vector in,int abssize)
  {
      Vector out = new Vector();
      try
      {
        for(int i = 0; i < in.size(); i++)
        {
            //System.out.print(i);
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new BolData(h, h.size()));
        }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }
      return out;
  }
}
