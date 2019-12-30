package radian.tcmis.both1.helpers;

import java.util.*;
import java.text.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class UsageReportDataServ
{
    public static final int CASNUM = 0;
    public static final int YEARMONTH = 1;
    public static final int FACILITY = 2;
    public static final int WORKAREA = 3;
    public static final int DELIVERY_POINT = 4;

    public static final int NUM_SECTIONS = 5;

    int groupByCol = 0;
    String casNum;
    String chemName;
    String facility;
    String workArea;
    String partNum;
    String tradeName;
    String unitsUsed;
    String wtPerUnit;
    String lbsUsed;
    String percentWtConst;
    String lbsReportable;
    String yearMonth;
    String delPoint;
    String mixtureVOC;
    String mfgdesc;
    int[] group;

    public UsageReportDataServ(Hashtable h,int[] g)
    {
        // set groupBy array
        group = new int[]
            {
            -1,-1,-1,-1,-1
        };
        group[group.length-1] = g[g.length-1];
        for(int i=0;i<g.length -1;i++)
        {
            group[i] = g[i];
        }

        this.casNum = h.get("CAS_NUMBER").toString();
        this.chemName = h.get("RPT_CHEMICAL").toString();
        this.facility = h.get("FACILITY").toString();
        this.workArea = h.get("WORK_AREA").toString();
        this.partNum = h.get("FAC_PART_ID").toString();
        this.tradeName = h.get("TRADE_NAME").toString();
        this.unitsUsed = h.get("QTY_SHIPPED").toString();
        this.wtPerUnit = h.get("WT_PER_UNIT").toString();
        this.lbsUsed = h.get("WT_SHIPPED").toString();
        this.percentWtConst = h.get("PERCENT").toString();
        this.lbsReportable = h.get("LBS_REPORTABLE").toString();
        this.delPoint = h.get("DELIVERY_POINT").toString();
        this.yearMonth = h.get("YEAR_MONTH").toString();
        //this.mfgdesc = h.get("MFG_DESC").toString();
        this.mfgdesc = "";
        try
        {
            this.mixtureVOC = h.get("MIXTURE_VOC").toString();
        }
        catch(Exception v)
        {
            this.mixtureVOC = "";
        }
    }

    public String getcasNum()
    {
        return casNum;
    }
    public String getchemName()
    {
        return chemName;
    }
    public String getfacility()
    {
        return facility;
    }
    public String getworkArea()
    {
        return workArea;
    }
    public String getworkAreaFull()
    {
        return workArea;
    }
    public String getpartNum()
    {
        return partNum;
    }
    public String gettradeName()
    {
        return tradeName;
    }
    //Nawaz 2/26/01
    public String getmfgdesc()
    {
        return mfgdesc;
    }

    public String getyearMonth()
    {
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        String s = months[(Integer.parseInt(yearMonth.substring(4)))-1]+ " " + yearMonth.substring(0,4);
        return s;
    }
    //public String getchemGroup(){return chemGroup;}
    public Float getunitsUsed()
    {
        try
        {
            return new Float(unitsUsed);
        }
        catch(Exception e)
        {
            return new Float(0.0);
        }
    }
    public Float getwtPerUnit()
    {
        try
        {
            return new Float(wtPerUnit);
        }
        catch(Exception e)
        {
            return new Float(0.0);
        }
    }
    public Float getlbsUsed()
    {
        try
        {
            return new Float(lbsUsed);
        }
        catch(Exception e)
        {
            return new Float(0.0);
        }
    }
    public Float getpercentWtConst()
    {
        try
        {
            return new Float(percentWtConst);
        }
        catch(Exception e)
        {
            return new Float(0.0);
        }
    }
    public Float getlbsReportable()
    {
        try
        {
            return new Float(lbsReportable);
        }
        catch(Exception e)
        {
            return new Float(0.0);
        }
    }
    public String getDelPoint()
    {
        return delPoint;
    }
    public Float getMixtureVoc()
    {
        try
        {
            return new Float(mixtureVOC);
        }
        catch(Exception e)
        {
            return new Float(0.0);
        }
    }

    public String getGroup0Desc()
    {
        return getGroupDesc(0);
    }
    public String getGroup1Desc()
    {
        return getGroupDesc(1);
    }
    public String getGroup2Desc()
    {
        return getGroupDesc(2);
    }
    public String getGroup3Desc()
    {
        return getGroupDesc(3);
    }
    public String getGroup4Desc()
    {
        return getGroupDesc(4);
    }

    public String getGroup0Desc2()
    {
        return getGroupDesc2(0);
    }
    public String getGroup1Desc2()
    {
        return getGroupDesc2(1);
    }
    public String getGroup2Desc2()
    {
        return getGroupDesc2(2);
    }
    public String getGroup3Desc2()
    {
        return getGroupDesc2(3);
    }
    public String getGroup4Desc2()
    {
        return getGroupDesc2(4);
    }
    public String getFoot0Label()
    {
        return getFootLabel(0);
    }
    public String getFoot1Label()
    {
        return getFootLabel(1);
    }
    public String getFoot2Label()
    {
        return getFootLabel(2);
    }
    public String getFoot3Label()
    {
        return getFootLabel(3);
    }
    public String getFoot4Label()
    {
        return getFootLabel(4);
    }
    String getGroupDesc2(int i)
    {
        int z = group[i];
        switch(z)
        {
        case CASNUM:
            return chemName;
        default:
            return "";
        }
    }
    String getGroupDesc(int i)
    {
        int z = group[i];
        switch(z)
        {
        case CASNUM:
            return casNum;
        case YEARMONTH:
            return getyearMonth();
        case FACILITY:
            return facility;
        case WORKAREA:
            return workArea;
        case DELIVERY_POINT:
            return delPoint;
        default:
            return "";
        }
    }
    String getFootLabel(int i)
    {
        return getGroupDesc(i);
    }

    public static Vector getFieldVector()
    {
        Vector v = new Vector();
        v.addElement("casNum = getcasNum");
        v.addElement("chemName = getchemName");
        v.addElement("facility = getfacility");
        v.addElement("workArea = getworkArea");
        v.addElement("delivery_point =  getDelPoint");
        v.addElement("partNum = getpartNum");
        v.addElement("tradeName = gettradeName");
        v.addElement("unitsUsed = getunitsUsed");
        v.addElement("wtPerUnit = getwtPerUnit");
        v.addElement("lbsUsed = getlbsUsed");
        v.addElement("percentWtConst = getpercentWtConst");
        v.addElement("mixtureVoc = getMixtureVoc");
        v.addElement("lbsReportable = getlbsReportable");
        //Nawaz 2/26/01
        v.addElement("mfgdesc = getmfgdesc");
        v.addElement("yearMonth = getyearMonth");
        v.addElement("group0Desc = getGroup0Desc");
        v.addElement("group1Desc = getGroup1Desc");
        v.addElement("group2Desc = getGroup2Desc");
        v.addElement("group3Desc = getGroup3Desc");
        v.addElement("group4Desc = getGroup4Desc");
        v.addElement("group0Desc2 = getGroup0Desc2");
        v.addElement("group1Desc2 = getGroup1Desc2");
        v.addElement("group2Desc2 = getGroup2Desc2");
        v.addElement("group3Desc2 = getGroup3Desc2");
        v.addElement("group4Desc2 = getGroup4Desc2");
        v.addElement("foot0Label = getFoot0Label");
        v.addElement("foot1Label = getFoot1Label");
        v.addElement("foot2Label = getFoot2Label");
        v.addElement("foot3Label = getFoot3Label");
        v.addElement("foot4Label = getFoot4Label");
        return v;
    }
    public static Vector getVector(Vector in, int[] g)
    {
        Vector out = new Vector();
        //   System.out.println("\n\n-------HI! its mee.."+ in +"\n" + g );

        for(int i=0;i<in.size();i++)
        {
            Hashtable h = (Hashtable)in.elementAt(i);
            out.addElement(new UsageReportDataServ(h,g));
        }
        return out;
    }
}
