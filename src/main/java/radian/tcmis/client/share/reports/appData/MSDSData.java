package radian.tcmis.client.share.reports.appData;

import java.util.*;

/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class MSDSData {
  public static final int NUM_COLUMNS = 6;
  public static final int FACILITY = 0;
  public static final int PART_NUM = 1;
  public static final int TRADE_NAME = 2;
  public static final int MFG = 3;
  public static final int LAST_REV_DATE = 4;
  public static final int LAST_REQ_DATE = 5;

  String facility;
  String partNum;
  String tradeName;
  String mfg;
  String lastRevDate;
  String lastReqDate;

  public MSDSData(String facility,String partNum,String tradeName,String mfg,String lastRevDate,String lastReqDate) {
    this.facility = facility;
    this.partNum = partNum;
    this.tradeName = tradeName;
    this.mfg = mfg;
    this.lastRevDate = lastRevDate;
    this.lastReqDate = lastReqDate;
  }

  public String getfacility(){return facility;}
  public String getpartNum(){return partNum;}
  public String gettradeName(){return tradeName;}
  public String getmfg(){return mfg;}
  public String getlastRevDate(){return lastRevDate;}
  public String getlastReqDate(){return lastReqDate;}


  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("facility = getfacility");
    v.addElement("partNum = getpartNum");
    v.addElement("tradeName = gettradeName");
    v.addElement("mfg = getmfg");
    v.addElement("lastRevDate = getlastRevDate");
    v.addElement("lastReqDate = getlastReqDate");
    return v;
  }

  public static Vector getVector(Object[][] oa) {
    Vector v = new Vector();
    for(int i=0;i<oa.length;i++) {
      v.addElement(new MSDSData(oa[i][FACILITY].toString(),
                                 oa[i][PART_NUM].toString(),
                                 oa[i][TRADE_NAME].toString(),
                                 oa[i][MFG].toString(),
                                 oa[i][LAST_REV_DATE].toString(),
                                 oa[i][LAST_REQ_DATE].toString()));
    }
    return v;
  }
}
