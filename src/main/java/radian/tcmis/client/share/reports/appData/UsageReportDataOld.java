package radian.tcmis.client.share.reports.appData;

import java.util.*;
import java.text.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class UsageReportDataOld {
  public static final int FACILITY = 2;
  public static final int WORKAREA = 3;
  public static final int CASNUM = 0;
  public static final int DELIVERY_POINT = 14;
  public static final int YEARMONTH = 12;

  public static final int NUM_COLUMNS = 14;
  public static final int CHEMNAME = 1;
  public static final int PARTNUM = 4;
  public static final int TRADENAME = 5;
  public static final int UNITSUSED = 6;
  public static final int WTPERUNIT = 7;
  public static final int LBSUSED = 8;
  public static final int PERCENTWTCONST = 9;
  public static final int LBSREPORTABLE = 10;
  public static final int MIXTUREVOC = 11;
  public static final int CHEMGROUP = 13;
  
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
  String mixtureVOC;
  String yearMonth;
  String chemGroup;
  String delPoint;

  public UsageReportDataOld(String casNum,String chemName,String facility,String workArea,String partNum,String tradeName,String unitsUsed,String wtPerUnit,String lbsUsed,String percentWtConst,String lbsReportable,String mixtureVOC,String yearMonth, String chemGroup,String delPoint,int i) {
    this.casNum = casNum;
    this.chemName = chemName;
    this.facility = facility;
    this.workArea = workArea;
    this.partNum = partNum;
    this.tradeName = tradeName;
    this.unitsUsed = unitsUsed;
    this.wtPerUnit = wtPerUnit;
    this.lbsUsed = lbsUsed;
    this.percentWtConst = percentWtConst;
    this.lbsReportable = lbsReportable;
    this.mixtureVOC = mixtureVOC;
    this.yearMonth = yearMonth;
    this.chemGroup = chemGroup;
    this.delPoint = delPoint;
    groupByCol = i;
  }

  public String getcasNum(){return casNum;}
  public String getchemName(){return chemName;}
  public String getfacility(){return facility;}
  public String getworkArea(){return workArea;}
  public String getworkAreaFull(){return workArea;}
  public String getpartNum(){return partNum;}
  public String gettradeName(){return tradeName;}
  public String getyearMonth(){
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    String s = months[Integer.parseInt(yearMonth.substring(4))]+ " " + yearMonth.substring(0,4);
    return s;
  }
  public String getchemGroup(){return chemGroup;}
  public Float getunitsUsed(){try{return new Float(unitsUsed);}catch(Exception e) {return new Float(0.0);}}
  public Float getwtPerUnit(){try{return new Float(wtPerUnit);}catch(Exception e) {return new Float(0.0);}}
  public Float getlbsUsed(){try{return new Float(lbsUsed);}catch(Exception e) {return new Float(0.0);}}
  public Float getpercentWtConst(){try { return new Float(percentWtConst);}catch(Exception e) {return new Float(0.0);}}
  public Float getlbsReportable(){try { return new Float(lbsReportable);}catch(Exception e) {return new Float(0.0);}}
  public Float getmixtureVOC(){try{ return new Float(mixtureVOC);}catch(Exception e) {return new Float(0.0);}}
  public String getDelPoint(){return delPoint;}

  public String getGroupBy() {
     switch (groupByCol) {
       case CASNUM:
         return getcasNum();
       case CHEMNAME:
         return getchemName();
       case FACILITY:
         return getfacility();
       case WORKAREA:
         return getworkAreaFull();
       case PARTNUM:
         return getpartNum();
       case TRADENAME:
         return gettradeName();
       case UNITSUSED:
         return getunitsUsed().toString();
       case WTPERUNIT:
         return getwtPerUnit().toString();
       case LBSUSED:
         return getlbsUsed().toString();
       case PERCENTWTCONST:
         return getpercentWtConst().toString();
       case LBSREPORTABLE:
         return getlbsReportable().toString();
       case MIXTUREVOC:
         return getmixtureVOC().toString();
       case YEARMONTH:
         return getyearMonth();
       case CHEMGROUP:
         return getchemGroup();
       case DELIVERY_POINT:
         return getDelPoint();
       default:
         return "";
     }
  }


  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("casNum = getcasNum");
    v.addElement("chemName = getchemName");
    v.addElement("facility = getfacility");
    v.addElement("workArea = getworkArea");
    v.addElement("partNum = getpartNum");
    v.addElement("tradeName = gettradeName");
    v.addElement("unitsUsed = getunitsUsed");
    v.addElement("wtPerUnit = getwtPerUnit");
    v.addElement("lbsUsed = getlbsUsed");
    v.addElement("percentWtConst = getpercentWtConst");
    v.addElement("lbsReportable = getlbsReportable");
    v.addElement("mixtureVOC = getmixtureVOC");
    v.addElement("yearMonth = getyearMonth");
    v.addElement("chemGroup = getchemGroup");
    v.addElement("groupByCol = getGroupBy");
    return v;
  }

  public static Vector getVector(Object[][] oa) {
    return getVector(oa,0);
  }

  public static Vector getVector(Object[][] oa, int groupCol) {
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new UsageReportDataOld(oa[i][CASNUM].toString(),
                                 oa[i][CHEMNAME].toString(),
                                 oa[i][FACILITY].toString(),
                                 oa[i][WORKAREA].toString(),
                                 oa[i][PARTNUM].toString(),
                                 oa[i][TRADENAME].toString(),
                                 oa[i][UNITSUSED].toString(),
                                 oa[i][WTPERUNIT].toString(),
                                 oa[i][LBSUSED].toString(),
                                 oa[i][PERCENTWTCONST].toString(),
                                 oa[i][LBSREPORTABLE].toString(),
                                 oa[i][MIXTUREVOC].toString(),
                                 oa[i][YEARMONTH].toString(),
                                 oa[i][CHEMGROUP].toString(),
                                 oa[i][DELIVERY_POINT].toString(),
                                 groupCol));
    }
    return v;
  }
}