package radian.tcmis.both1.helpers;

import java.util.Hashtable;
import java.util.Vector;

public class AdhocMatrixData
{

    String DETAIL_0 ="";
    String DETAIL_1 ="";
    String DETAIL_2 ="";
    String DETAIL_3 ="";
    String DETAIL_4 ="";
    String DETAIL_5 ="";
    String DETAIL_6 ="";
    String DETAIL_7 ="";
    String DETAIL_8 ="";
    String DETAIL_9 ="";

    public AdhocMatrixData(Hashtable h,int size)
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
        //System.out.println("The Size is  :"+ size);
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
                break;
            }

            /*        DETAIL_0 = h.get("DETAIL_0").toString();
                    DETAIL_1 = h.get("DETAIL_1").toString();
                    DETAIL_2 = h.get("DETAIL_2").toString();
                    DETAIL_3 = h.get("DETAIL_3").toString();
                    DETAIL_4 = h.get("DETAIL_4").toString();
                    DETAIL_5 = h.get("DETAIL_5").toString();
                    DETAIL_6 = h.get("DETAIL_6").toString();
                    DETAIL_7 = h.get("DETAIL_7").toString();
                    DETAIL_8 = h.get("DETAIL_8").toString();
                    DETAIL_9 = h.get("DETAIL_9").toString();*/
        }
    }
    public String getDetail0Desc()
    {
        return DETAIL_0;
    }

    public String getDetail1Desc()
    {
        return DETAIL_1;
    }

    public String getDetail2Desc()
    {
        return DETAIL_2;
    }

    public String getDetail3Desc()
    {
        return DETAIL_3;
    }

    public String getDetail4Desc()
    {
        return DETAIL_4;
    }

    public String getDetail5Desc()
    {
        return DETAIL_5;
    }

    public String getDetail6Desc()
    {
        return DETAIL_6;
    }

    public String getDetail7Desc()
    {
        return DETAIL_7;
    }

    public String getDetail8Desc()
    {
        return DETAIL_8;
    }

    public String getDetail9Desc()
    {
        return DETAIL_9;
    }

    /*String getDetailDesc(int i)
    {
        int z = Detail[i];
        switch(z)
        {
        case 0: // '\0'
            return casNum;

        case 1: // '\001'
            return getyearMonth();

        case 2: // '\002'
            return facility;

        case 3: // '\003'
            return workArea;

        case 4: // '\004'
            return delPoint;
        }
        return "";
    }*/

    public static Vector getFieldVector(int size)
    {
        Vector v = new Vector();
        /*for(int i=0;i<size;i++)
        {
            switch(i)
            {
            case 0:
                v.addElement("Detail0Desc = getDetail0Desc");
                break;
            case 1:
                v.addElement("Detail1Desc = getDetail1Desc");
                break;
            case 2:
                v.addElement("Detail2Desc = getDetail2Desc");
                break;
            case 3:
                v.addElement("Detail3Desc = getDetail3Desc");
                break;
            case 4:
                v.addElement("Detail4Desc = getDetail4Desc");
                break;
            case 5:
                v.addElement("Detail5Desc = getDetail5Desc");
                break;
            case 6:
                v.addElement("Detail6Desc = getDetail6Desc");
                break;
            case 7:
                v.addElement("Detail7Desc = getDetail7Desc");
                break;
            case 8:
                v.addElement("Detail8Desc = getDetail8Desc");
                break;
            case 9:
                v.addElement("Detail9Desc = getDetail9Desc");
                break;
            }*/

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
            return v;
    }
    public static Vector getVector(Vector in,int size)
    {
        Vector out = new Vector();
        for(int i = 0; i < in.size(); i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new AdhocMatrixData(h, size));
        }
        return out;
    }
}
