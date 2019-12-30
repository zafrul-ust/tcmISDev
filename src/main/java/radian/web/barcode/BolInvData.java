package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

public class BolInvData
{

private String LOT_STATUS = "";
private String BIN = "";
private String ITEM_ID = "";
private String QUANTITY_SHIPPED = "";
private String RECEIPT_ID = "";
private String MFG_LOT = "";
private String EXPIRE_DATE = "";

    public BolInvData(Hashtable h,int size)
    {
        LOT_STATUS = "";
        BIN = "";
        ITEM_ID = "";
        QUANTITY_SHIPPED = "";
        RECEIPT_ID = "";
        MFG_LOT = "";
        EXPIRE_DATE = "";

        for(int i=0;i< 8 ;i++)
        {
            switch(i)
            {
            case 1:
                this.ITEM_ID = h.get("ITEM_ID").toString().toUpperCase();
                break;
            case 2:
                this.QUANTITY_SHIPPED = h.get("QUANTITY").toString().toUpperCase();
                break;
            case 3:
                this.RECEIPT_ID = h.get("RECEIPT_ID").toString().toUpperCase();
                break;
            case 4:
                this.MFG_LOT = h.get("MFG_LOT").toString().toUpperCase();
                break;
            case 5:
                this.EXPIRE_DATE = h.get("EXPIRE_DATE").toString().toUpperCase();
                break;
            case 6:
                this.LOT_STATUS = h.get("LOT_STATUS").toString().toUpperCase();
                break;
            case 7:
             this.BIN = h.get("BIN").toString().toUpperCase();
             break;

            }
        }
    }

  public String getDetail0Desc(){return LOT_STATUS;}
  public String getDetail1Desc(){return BIN;}
  public String getDetail35Desc(){return ITEM_ID;}
  public String getDetail36Desc(){return QUANTITY_SHIPPED;}
  public String getDetail37Desc(){return RECEIPT_ID;}
  public String getDetail38Desc(){return MFG_LOT;}
  public String getDetail39Desc(){return EXPIRE_DATE;}

  public static Vector getFieldVector()
  {
      Vector v = new Vector();
      v.addElement("LOT_STATUS = getDetail0Desc");
      v.addElement("BIN = getDetail1Desc");
      v.addElement("ITEM_ID = getDetail35Desc");
      v.addElement("QUANTITY_SHIPPED = getDetail36Desc");
      v.addElement("RECEIPT_ID = getDetail37Desc");
      v.addElement("MFG_LOT = getDetail38Desc");
      v.addElement("EXPIRE_DATE = getDetail39Desc");

      return v;
  }
  public static Vector getVector(Vector in)
  {
      Vector out = new Vector();
      //System.out.println(in.size());
      try
      {
        for(int i = 0; i < in.size(); i++)
        {
          Hashtable h= ( Hashtable ) in.elementAt( i );
          Vector h1= ( Vector ) h.get( "INVDATA" );

          for ( int K=0; K < h1.size(); K++ )
          {
            Hashtable h2= ( Hashtable ) h1.elementAt( K );
            out.addElement( new BolInvData( h2,h1.size() ) );
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
