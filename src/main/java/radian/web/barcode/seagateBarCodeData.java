package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

public class seagateBarCodeData
{

private String MFG_LOT = "";
private String BIN = "";
private String DATE_OF_RECEIPT = "";
private String RECEIPT_ID = "";
private String CATALOG_ID = "";
private String CAT_PART_NO = "";
private String HEALTH = "";
private String FLAMABILITY = "";
private String REACTIVITY = "";
private String CHEMICAL_STORAGE = "";
private String SIGNAL_WORD = "";
private String PERSONAL_PROTECTIVE_EQUIPMENT = "";
private String DISPOSAL_CODE = "";
private String DISPOSAL_CONTAINER_CODE = "";
private String HAZARD_CODE1 = "";
private String HAZARD_CODE2 = "";
private String PART_DESCRIPTION = "";
private String ITEM_ID = "";
private String HAZARD_CODE_DESC1 = "";
private String HAZARD_CODE_DESC2 = "";
private String HAZARD_CODE_CAT_DESC1 = "";
private String HAZARD_CODE_CAT_DESC2 = "";
private String EXPIRE_DATE = "";
private String STORAGE_TEMP = "";
private String BARCODEID = "";
private String ITEM_DESC = "";
    public seagateBarCodeData(Hashtable h,int size)
    {
        MFG_LOT = "";
        BIN = "";
        DATE_OF_RECEIPT = "";
        RECEIPT_ID = "";
        CATALOG_ID = "";
        CAT_PART_NO = "";
        HEALTH = "";
        FLAMABILITY = "";
        REACTIVITY = "";
        CHEMICAL_STORAGE = "";
        SIGNAL_WORD = "";
        PERSONAL_PROTECTIVE_EQUIPMENT = "";
        DISPOSAL_CODE = "";
        DISPOSAL_CONTAINER_CODE = "";
        HAZARD_CODE1 = "";
        HAZARD_CODE2 = "";
        PART_DESCRIPTION = "";
        ITEM_ID = "";
        HAZARD_CODE_DESC1 = "";
        HAZARD_CODE_DESC2 = "";
        HAZARD_CODE_CAT_DESC1 = "";
        HAZARD_CODE_CAT_DESC2 = "";
        EXPIRE_DATE = "";
        STORAGE_TEMP = "";
        BARCODEID = "";
        ITEM_DESC = "";
        //System.out.println("Size in Barcode Data"+size);

        for(int i=0;i<size;i++)
        {
            switch(i)
            {
              case 0:
                    this.MFG_LOT = h.get("MFG_LOT").toString();
                    break;
              case 1:
                    this.BIN = h.get("BIN").toString();
                    break;
              case 2:
                    this.DATE_OF_RECEIPT = h.get("DATE_OF_RECEIPT").toString();
                    break;
              case 3:
                    this.RECEIPT_ID = h.get("RECEIPT_ID").toString();
                    break;
              case 4:
                    this.CATALOG_ID = h.get("CATALOG_ID").toString();
                    break;
              case 5:
                    this.CAT_PART_NO = h.get("CAT_PART_NO").toString();
                    break;
              case 6:
                    this.HEALTH = h.get("HEALTH").toString();
                    break;
              case 7:
                    this.FLAMABILITY = h.get("FLAMABILITY").toString();
                    break;
              case 8:
                    this.REACTIVITY = h.get("REACTIVITY").toString();
                    break;
              case 9:
                    this.CHEMICAL_STORAGE = h.get("CHEMICAL_STORAGE").toString();
                    break;
              case 10:
                    this.SIGNAL_WORD = h.get("SIGNAL_WORD").toString();
                    break;
              case 11:
                    this.PERSONAL_PROTECTIVE_EQUIPMENT = h.get("PERSONAL_PROTECTIVE_EQUIPMENT").toString();
                    break;
              case 12:
                    this.DISPOSAL_CODE = h.get("DISPOSAL_CODE").toString();
                    break;
              case 13:
                    this.DISPOSAL_CONTAINER_CODE = h.get("DISPOSAL_CODE").toString() + h.get("DISPOSAL_CONTAINER_CODE").toString();
                    break;
              case 14:
                    this.HAZARD_CODE1 = h.get("HAZARD_CODE1").toString();
                    break;
              case 15:
                    this.HAZARD_CODE2 = h.get("HAZARD_CODE2").toString();
                    break;
              case 16:
                    this.PART_DESCRIPTION = h.get("PART_DESCRIPTION").toString();
                    break;
              case 17:
                    this.ITEM_ID = h.get("ITEM_ID").toString();
                    break;
              case 18:
                    this.HAZARD_CODE_DESC1 = h.get("HAZARD_CODE_DESC1").toString();
                    break;
              case 19:
                    this.HAZARD_CODE_DESC2 = h.get("HAZARD_CODE_DESC2").toString();
                    break;
              case 20:
                    this.HAZARD_CODE_CAT_DESC1 = h.get("HAZARD_CODE_CAT_DESC1").toString();
                    break;
              case 21:
                    this.HAZARD_CODE_CAT_DESC2 = h.get("HAZARD_CODE_CAT_DESC2").toString();
                    break;
              case 22:
                    this.EXPIRE_DATE = h.get("EXPIRE_DATE").toString();
                    break;
              case 23:
                    this.STORAGE_TEMP = h.get("STORAGE_TEMP").toString();
                    break;
              case 24:
                    this.BARCODEID = h.get("BARCODEID").toString();
                    break;
              case 25:
              this.ITEM_DESC = h.get("ITEM_DESC").toString();
              break;
            }
        }
    }

public String getmfg_lotDesc(){return MFG_LOT;}
public String getbinDesc(){return BIN;}
public String getdate_of_receiptDesc(){return DATE_OF_RECEIPT;}
public String getreceipt_idDesc(){return RECEIPT_ID;}
public String getcatalog_idDesc(){return CATALOG_ID;}
public String getcat_part_noDesc(){return CAT_PART_NO;}
public String gethealthDesc(){return HEALTH;}
public String getflamabilityDesc(){return FLAMABILITY;}
public String getreactivityDesc(){return REACTIVITY;}
public String getchemical_storageDesc(){return CHEMICAL_STORAGE;}
public String getsignal_wordDesc(){return SIGNAL_WORD;}
public String getpersonal_protective_equipmentDesc(){return PERSONAL_PROTECTIVE_EQUIPMENT;}
public String getdisposal_codeDesc(){return DISPOSAL_CODE;}
public String getdisposal_container_codeDesc(){return DISPOSAL_CONTAINER_CODE;}
public String gethazard_code1Desc(){return HAZARD_CODE1;}
public String gethazard_code2Desc(){return HAZARD_CODE2;}
public String getpart_descriptionDesc(){return PART_DESCRIPTION;}
public String getitem_idDesc(){return ITEM_ID;}
public String gethazard_code_desc1Desc(){return HAZARD_CODE_DESC1;}
public String gethazard_code_desc2Desc(){return HAZARD_CODE_DESC2;}
public String gethazard_code_cat_desc1Desc(){return HAZARD_CODE_CAT_DESC1;}
public String gethazard_code_cat_desc2Desc(){return HAZARD_CODE_CAT_DESC2;}
public String getexpire_dateDesc(){return EXPIRE_DATE;}
public String getstorage_tempDesc(){return STORAGE_TEMP;}
public String getbarcodeidDesc(){return BARCODEID;}
public String getitemDesc(){return ITEM_DESC;}

  public static Vector getFieldVector(int size)
  {
      Vector v = new Vector();
      v.addElement("MFG_LOT = getmfg_lotDesc");
      v.addElement("BIN = getbinDesc");
      v.addElement("DATE_OF_RECEIPT = getdate_of_receiptDesc");
      v.addElement("RECEIPT_ID = getreceipt_idDesc");
      v.addElement("CATALOG_ID = getcatalog_idDesc");
      v.addElement("CAT_PART_NO = getcat_part_noDesc");
      v.addElement("HEALTH = gethealthDesc");
      v.addElement("FLAMABILITY = getflamabilityDesc");
      v.addElement("REACTIVITY = getreactivityDesc");
      v.addElement("CHEMICAL_STORAGE = getchemical_storageDesc");
      v.addElement("SIGNAL_WORD = getsignal_wordDesc");
      v.addElement("PERSONAL_PROTECTIVE_EQUIPMENT = getpersonal_protective_equipmentDesc");
      v.addElement("DISPOSAL_CODE = getdisposal_codeDesc");
      v.addElement("DISPOSAL_CONTAINER_CODE = getdisposal_container_codeDesc");
      v.addElement("HAZARD_CODE1 = gethazard_code1Desc");
      v.addElement("HAZARD_CODE2 = gethazard_code2Desc");
      v.addElement("PART_DESCRIPTION = getpart_descriptionDesc");
      v.addElement("ITEM_ID = getitem_idDesc");
      v.addElement("HAZARD_CODE_DESC1 = gethazard_code_desc1Desc");
      v.addElement("HAZARD_CODE_DESC2 = gethazard_code_desc2Desc");
      v.addElement("HAZARD_CODE_CAT_DESC1 = gethazard_code_cat_desc1Desc");
      v.addElement("HAZARD_CODE_CAT_DESC2 = gethazard_code_cat_desc2Desc");
      v.addElement("EXPIRE_DATE = getexpire_dateDesc");
      v.addElement("STORAGE_TEMP = getstorage_tempDesc");
      v.addElement("BARCODEID = getbarcodeidDesc");
      v.addElement("ITEM_DESC = getitemDesc");

      return v;
  }
  public static Vector getVector(Vector in,int size)
  {
      Vector out = new Vector();
      try
      {
        for(int i = 0; i < in.size(); i++)
        {
            //System.out.print(i);
            Hashtable h = (Hashtable)in.elementAt(i);
            String j = (String)h.get("QUANTITY_RECEIVED").toString();
            int test = 0;

            try{test = Integer.parseInt(j);}catch(Exception eee){test =1;}

            //System.out.print(test);

            Hashtable h1 = (Hashtable)h.get("DATA");
            for (int k = 0; k < test; k++)
            {
            out.addElement(new seagateBarCodeData(h1, h1.size()));
            }
        }
      }
      catch(Exception e11)
      {
          e11.printStackTrace();
      }
      return out;
  }
}
