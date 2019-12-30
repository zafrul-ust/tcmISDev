
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;


import java.util.*;
import java.text.SimpleDateFormat;
import java.text.*;

public class InventoryReportTable  {

   protected TcmISDBModel db;
   String expDate,promDate;
   String facility;
   String warehouse;
   String item;
   boolean eD,fac,pD,ware,hosed;
   String expired;
   String expired2;
   String promised;

   public InventoryReportTable() throws java.rmi.RemoteException {

   }

   public InventoryReportTable(TcmISDBModel db) throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

  protected String getFromWithOutSearchString(boolean ware, String facility, boolean eD, String expDate, String exp2Date, boolean pD,String promDate, String orderBy) {
    String from = " from (select cpig.catalog_id,cpig.cat_part_no,s.item_id,s.receipt_id,cpig.part_group_no,";
    if (ware) {
      from += "s.branch_plant,";
    }
    //1-31-02
    if ("BIN".equalsIgnoreCase(orderBy)) {
      from += "s.bin,";
    }else if ("LOT".equalsIgnoreCase(orderBy)) {
      from += "s.bin,s.lot_num,";
    }

    from += " sum(s.available + s.on_hold) on_hand,sum(s.on_order) on_order,sum(s.available) available,sum(s.on_hold) on_hold,";
    from += " sum(s.in_purchasing) in_purchasing from inventory_status_rpt_view s, catalog_part_item_group cpig where s.item_id = cpig.item_id";
    if (!facility.equalsIgnoreCase("All")) {
      from += " and cpig.catalog_id in (select catalog_id from catalog_facility where facility_id='"+facility+"') and cpig.status in ('A','D')";
    }
    String fdate = null;
    String pdate = null;
    String nowdate ;
    SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy");
    simple.setTimeZone(TimeZone.getDefault());
    SimpleDateFormat simple2 = new SimpleDateFormat("yyyyDDD");
    simple2.setTimeZone(TimeZone.getDefault());
    ParsePosition pos = new ParsePosition(0);
    java.util.Date nowD;
    java.util.Date newD;
    Integer days;
    long timeNow = 0;
    try {
      PurchaseRequest PRI = new  PurchaseRequest(db);
      nowdate = PRI.getNowDate();
      nowD = simple.parse(nowdate,pos);
      timeNow = nowD.getTime();
    } catch (Exception e){
      e.printStackTrace();;
    }
    if (eD) {
      try {
        days = new Integer(expDate);
        newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
        fdate  = new String(simple2.format(newD));
        expired = BothHelpObjs.formatDate("toCharfromOrder",fdate);
        from += " and expire_date <= to_date('"+fdate+"','yyyyDDD') ";
      } catch (Exception e){}; // no exp data
      try {
        days = new Integer(exp2Date);
        newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
        fdate  = new String(simple2.format(newD));
        expired2 = BothHelpObjs.formatDate("toCharfromOrder",fdate);
        from += " and expire_date > to_date('"+fdate+"','yyyyDDD') ";
      } catch (Exception e){}; // no exp data
    }
    if (pD) {
      days = new Integer(promDate);
      newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
      pdate  = new String(simple2.format(newD));
      promised = BothHelpObjs.formatDate("toCharfromOrder",pdate);
      from += " and (promised_date <= to_date('"+pdate+"','yyyyDDD') and rev_prom_date is null ";
      from += " or (rev_prom_date like '%%/%%/%%' and to_date(rev_prom_date,'mm/dd/yy') <= to_date('"+pdate+"','yyyyDDD')))";
    }
    if (ware) {
      from += " group by cpig.catalog_id,cpig.cat_part_no,s.branch_plant,s.item_id,s.receipt_id,";
      //1-31-02
      if ("BIN".equalsIgnoreCase(orderBy)) {
        from += "s.bin,";
      }else if ("LOT".equalsIgnoreCase(orderBy)) {
        from += "s.bin,s.lot_num,";
      }

      from += "cpig.part_group_no) x,catalog_item_view b,cat_part_view a ,facility c";
    }else {
      from += " group by cpig.catalog_id,cpig.cat_part_no,s.item_id,s.receipt_id,";
      //1-31-02
      if ("BIN".equalsIgnoreCase(orderBy)) {
        from += "s.bin,";
      }else if ("LOT".equalsIgnoreCase(orderBy)) {
        from += "s.bin,s.lot_num,";
      }
      from += "cpig.part_group_no) x,catalog_item_view b,cat_part_view a";
    }

    return from;
  }

  protected String getFromWithSearchString(boolean ware, String facility, String searchString, boolean eD, String expDate, String exp2Date, boolean pD,String promDate, String orderBy) {
    String from = " from (select syn.catalog_id,syn.cat_part_no,s.item_id,s.receipt_id,cpig.part_group_no,sum(s.available + s.on_hold) on_hand,";
    if (ware) {
      from += "s.branch_plant,";
    }

    //1-31-02
    if ("BIN".equalsIgnoreCase(orderBy)) {
      from += "s.bin,";
    }else if ("LOT".equalsIgnoreCase(orderBy)) {
      from += "s.bin,s.lot_num,";
    }

    from += " sum(s.on_order) on_order,sum(s.available) available,sum(s.on_hold) on_hold,sum(s.in_purchasing) in_purchasing";
    from += " from inventory_status_rpt_view s, cat_part_synonym syn , catalog_part_item_group cpig where s.item_id = cpig.item_id";
    from += " and  syn.catalog_id=cpig.catalog_id and syn.cat_part_no=cpig.cat_part_no and";
    from += " (lower(search_string1) like lower('%"+searchString+"%') or";
    from += " lower(search_string2) like lower('%"+searchString+"%') or";
    from += " lower(search_string3) like lower('%"+searchString+"%') or";
    from += " lower(search_string4) like lower('%"+searchString+"%') or";
    from += " lower(search_string5) like lower('%"+searchString+"%'))";

    if (!facility.equalsIgnoreCase("All")) {
      from += " and syn.catalog_id in (select catalog_id from catalog_facility where facility_id='"+facility+"')";
    }

    String fdate = null;
    String pdate = null;
    String nowdate ;
    SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy");
    simple.setTimeZone(TimeZone.getDefault());
    SimpleDateFormat simple2 = new SimpleDateFormat("yyyyDDD");
    simple2.setTimeZone(TimeZone.getDefault());
    ParsePosition pos = new ParsePosition(0);
    java.util.Date nowD;
    java.util.Date newD;
    Integer days;
    long timeNow = 0;
    try {
      PurchaseRequest PRI = new  PurchaseRequest(db);
      nowdate = PRI.getNowDate();
      nowD = simple.parse(nowdate,pos);
      timeNow = nowD.getTime();
    } catch (Exception e){
      e.printStackTrace();;
    }
    if (eD) {
      try {
        days = new Integer(expDate);
        newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
        fdate  = new String(simple2.format(newD));
        expired = BothHelpObjs.formatDate("toCharfromOrder",fdate);
        from += " and expire_date <= to_date('"+fdate+"','yyyyDDD') ";
      } catch (Exception e){}; // no exp data
      try {
        days = new Integer(exp2Date);
        newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
        fdate  = new String(simple2.format(newD));
        expired2 = BothHelpObjs.formatDate("toCharfromOrder",fdate);
        from += " and expire_date > to_date('"+fdate+"','yyyyDDD') ";
      } catch (Exception e){}; // no exp data
    }
    if (pD) {
      days = new Integer(promDate);
      newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
      pdate  = new String(simple2.format(newD));
      promised = BothHelpObjs.formatDate("toCharfromOrder",pdate);
      from += " and (promised_date <= to_date('"+pdate+"','yyyyDDD') and rev_prom_date is null ";
      from += " or (rev_prom_date like '%%/%%/%%' and to_date(rev_prom_date,'mm/dd/yy') <= to_date('"+pdate+"','yyyyDDD')))";
    }

    if (ware) {
      from += " group by syn.catalog_id,syn.cat_part_no,s.branch_plant,s.item_id,s.receipt_id, ";
      //1-31-02
      if ("BIN".equalsIgnoreCase(orderBy)) {
        from += "s.bin,";
      }else if ("LOT".equalsIgnoreCase(orderBy)) {
        from += "s.bin,s.lot_num,";
      }

      from += "cpig.part_group_no) x,catalog_item_view b,cat_part_view a ,facility c";
    }else {
      from += " group by syn.catalog_id,syn.cat_part_no,s.item_id,s.receipt_id, ";
      //1-31-02
      if ("BIN".equalsIgnoreCase(orderBy)) {
        from += "s.bin,";
      }else if ("LOT".equalsIgnoreCase(orderBy)) {
        from += "s.bin,s.lot_num,";
      }

      from += "cpig.part_group_no) x,catalog_item_view b,cat_part_view a";
    }
    return from;
  }

  //Nawaz 04/08/02
  public String getQuery(Hashtable inData) throws Exception
  {
    //System.out.println(inData);

    String facility = (String) inData.get("FACILITY");
    String userFac = (String) inData.get("USER_FAC");
    String warehouse = (String) inData.get("WAREHOUSE");
    String itemIn = (String) inData.get("ITEM");
    String expDate = (String) inData.get("EXP_VAL");
    String exp2Date = (String) inData.get("EXP2_VAL");
    String promDate = (String) inData.get("PROM_VAL");
    boolean fac = ((Boolean) inData.get("FAC_BOL")).booleanValue();
    boolean ware = ((Boolean) inData.get("WARE_BOL")).booleanValue();
    boolean eD = ((Boolean) inData.get("EXP_BOL")).booleanValue();
    boolean pD = ((Boolean) inData.get("PROM_BOL")).booleanValue();
    boolean oH = ((Boolean) inData.get("ON_HOLD_BOL")).booleanValue();
    boolean oO = ((Boolean) inData.get("ON_ORDER_BOL")).booleanValue();
    boolean allParts = ((Boolean) inData.get("ALL_PARTS")).booleanValue();
    String orderBy = (String) inData.get("ORDER_BY");

    String select = "select x.item_id,x.catalog_id,x.cat_part_no,x.receipt_id,x.available on_hand,x.on_order ,x.on_hold ,x.in_purchasing,";
    //1-31-02
    if ("BIN".equalsIgnoreCase(orderBy)) {
      select += "x.bin,";
    }else if ("LOT".equalsIgnoreCase(orderBy)) {
      select += "x.bin,x.lot_num,";
    }
    select += "b.material_desc trade_name,b.part_size,b.size_unit,b.pkg_style,b.mfg_desc,b.material_id,b.msds_on_line,a.part_group_no,";
    select += "a.PART_DESCRIPTION  ,a.bundle,b.QUANTITY_PER_BUNDLE,b.COMPONENTS_PER_ITEM,b.MFG_PART_NO ,a.stocked stocking_method,";
    select += "x.cat_part_no||a.part_group_no  part_group,x.cat_part_no||x.item_id||a.part_group_no  part_item_group,";
    select += "b.part_size||' '||b.size_unit||' '||b.pkg_style  packaging";

    String item = HelpObjs.singleQuoteToDouble(itemIn);
    String where = " where  a.item_id = b.item_id and nvl(a.bundle,'EA') = nvl(b.bundle,'EA') and a.cat_part_no = x.cat_part_no";
    where += " and a.catalog_id=x.catalog_id and a.item_id=x.item_id and a.part_group_no = x.part_group_no";
    boolean itemON = false;
    boolean facON = false;
    hosed = false;
    Hashtable result = new Hashtable();
    String from = "";
    try {
      if (BothHelpObjs.isBlankString(item)) {
        from = getFromWithOutSearchString(ware,facility,eD,expDate,exp2Date,pD,promDate,orderBy);
      }else {
        from = getFromWithSearchString(ware,facility,item,eD,expDate,exp2Date,pD,promDate,orderBy);
      }

      if (ware) {
        select += ",c.facility_id warehouse";
        where += " and c.branch_plant = x.branch_plant and c.facility_id = '" + warehouse + "'";
      }
    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    }
    return select+from+where;
  }
}





























