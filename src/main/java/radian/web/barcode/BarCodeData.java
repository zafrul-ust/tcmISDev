package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

public class BarCodeData
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
		private String DETAIL_16 ="";
		/*private String MAX_USE_IN_MONTHS ="";
		private String EXPIRE_DATE_LOCALE ="";
		private String DATE_OF_MANUFACTURE_LOCALE ="";
		private String LABEL_STORAGE_TEMP ="";
		private String PART_LIST ="";
		private String ITALY_BARCODE ="";
		private String DATE_OF_MANUFACTURE ="";*/

    public BarCodeData(Hashtable h,int size)
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
				DETAIL_16 ="";
				/*MAX_USE_IN_MONTHS ="";
				EXPIRE_DATE_LOCALE ="";
				DATE_OF_MANUFACTURE_LOCALE ="";
				LABEL_STORAGE_TEMP ="";
				PART_LIST ="";
				ITALY_BARCODE ="";
				DATE_OF_MANUFACTURE ="";*/

        for(int i=0;i<size;i++)
        {
            switch(i)
            {
            case 0:
                this.DETAIL_0 = h.get("DETAIL_0").toString();
                //System.out.println("The First Dataelement it :"+ DETAIL_0);
                break;
            case 1:
                this.DETAIL_1 = h.get("DETAIL_1").toString();
                //System.out.println("The Second Dataelement it :"+ DETAIL_1);
                break;
            case 2:
                this.DETAIL_2 = h.get("DETAIL_2").toString();
                //System.out.println("The Third Dataelement it :"+ DETAIL_2);
                break;
            case 3:
                this.DETAIL_3 = h.get("DETAIL_3").toString();
                break;
            case 4:
                this.DETAIL_4 = h.get("DETAIL_4").toString();
                break;
            case 5:
                this.DETAIL_5 = h.get("DETAIL_5").toString();
                break;
            case 6:
                this.DETAIL_6 = h.get("DETAIL_6").toString();
                break;
            case 7:
                this.DETAIL_7 = h.get("DETAIL_7").toString();
                break;
            case 8:
                this.DETAIL_8 = h.get("DETAIL_8").toString();
                break;
            case 9:
                this.DETAIL_9 = h.get("DETAIL_9").toString();
                //System.out.println("The Third Dataelement it :"+ DETAIL_9);
                break;
            case 10:
                this.DETAIL_10 = h.get("DETAIL_10").toString();
                break;
            case 11:
                this.DETAIL_11 = h.get("DETAIL_11").toString();
                break;
            case 12:
                this.DETAIL_12 = h.get("DETAIL_12").toString();
                break;
            case 13:
                this.DETAIL_13 = h.get("DETAIL_13").toString();
                break;
            case 14:
                this.DETAIL_14 = h.get("DETAIL_14").toString();
                break;
						case 15:
						 this.DETAIL_15=h.get( "DETAIL_15" ).toString();
						 break;
						case 16:
						 this.DETAIL_15=h.get( "DETAIL_16" ).toString();
						 break;
						/*case 17:
						 this.MAX_USE_IN_MONTHS=h.get( "MAX_USE_IN_MONTHS" ).toString();
						 break;
						case 18:
						 this.EXPIRE_DATE_LOCALE=h.get( "EXPIRE_DATE_LOCALE" ).toString();
						 break;
						case 19:
						 this.DATE_OF_MANUFACTURE_LOCALE=h.get( "DATE_OF_MANUFACTURE_LOCALE" ).toString();
						 break;
						case 20:
						 String tempStorage = h.get( "LABEL_STORAGE_TEMP" ).toString();
						 if (tempStorage.trim().length() >0)
						 {
							this.LABEL_STORAGE_TEMP = tempStorage.substring(1,tempStorage.length());
						 }
						 break;
						case 21:
						 this.PART_LIST=h.get( "PART_LIST" ).toString();
						 break;
						case 22:
						 this.ITALY_BARCODE=h.get( "ITALY_BARCODE" ).toString();
						 break;
						case 23:
						 this.DATE_OF_MANUFACTURE=h.get( "DATE_OF_MANUFACTURE" ).toString();
						 break;*/
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
  public String getDetail16Desc(){return DETAIL_16;}
	/*public String getMAX_USE_IN_MONTHSDesc(){return MAX_USE_IN_MONTHS;}
	public String getEXPIRE_DATE_LOCALEDesc(){return EXPIRE_DATE_LOCALE;}
	public String getDATE_OF_MANUFACTURE_LOCALEDesc(){return DATE_OF_MANUFACTURE_LOCALE;}
	public String getLABEL_STORAGE_TEMPDesc(){return LABEL_STORAGE_TEMP;}
	public String getPART_LISTDesc(){return PART_LIST;}
	public String getITALY_BARCODEDesc(){return ITALY_BARCODE;}
	public String getDATE_OF_MANUFACTUREDesc(){return DATE_OF_MANUFACTURE;}*/

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
			v.addElement("DETAIL_16 = getDetail16Desc");
			/*v.addElement("MAX_USE_IN_MONTHS = getMAX_USE_IN_MONTHSDesc");
			v.addElement("EXPIRE_DATE_LOCALE = getEXPIRE_DATE_LOCALEDesc");
			v.addElement("DATE_OF_MANUFACTURE_LOCALE = getDATE_OF_MANUFACTURE_LOCALEDesc");
			v.addElement("LABEL_STORAGE_TEMP = getLABEL_STORAGE_TEMPSDesc");
			v.addElement("PART_LIST = getPART_LISTDesc");
			v.addElement("ITALY_BARCODE = getITALY_BARCODEDesc");
			v.addElement("DATE_OF_MANUFACTURE = getDATE_OF_MANUFACTUREDesc");*/

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
            out.addElement(new BarCodeData(h1, h1.size()));
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
