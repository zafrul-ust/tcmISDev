
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;


import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.text.*;
import java.awt.Color;

public class InventoryTable  {

   protected TcmISDBModel db;
   String expDate,promDate;
   String facility;
   String warehouse;
   String item;
   boolean eD,fac,pD,ware,hosed;
   String expired;
   String expired2;
   String promised;

   public InventoryTable() throws java.rmi.RemoteException {

   }

   public InventoryTable(TcmISDBModel db) throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable getInitialData(Hashtable inData) throws Exception {
     Hashtable result = new Hashtable();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       String query = "select distinct a.facility_id,b.inventory_group,nvl(c.facility_name,c.facility_id) facility_name from fac_loc_app a, inventory_group_definition b, facility c"+
                      " where a.inventory_group = b.inventory_group and a.status = 'A' and a.facility_id = c.facility_id and c.active = 'y' order by facility_name";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       Vector warehouseV = new Vector();
       Vector facilityIDV = new Vector();
       Vector facilityNameV = new Vector();
       String lastFacID = "";
       while (rs.next()){
         String currentFacID = rs.getString("facility_id");
         String facDesc = rs.getString("facility_name");
         String wareHouse = rs.getString("inventory_group");
         //get hubs
         if (!warehouseV.contains(wareHouse) && !"DropShip".equalsIgnoreCase(wareHouse)) {
           warehouseV.addElement(wareHouse);
         }
         //get facilities
         if (!facilityIDV.contains(currentFacID)) {
           facilityIDV.addElement(currentFacID);
           facilityNameV.addElement(facDesc);
         }

         //get hub for facility
         if (!"DropShip".equalsIgnoreCase(wareHouse)) {
           if (lastFacID.equals(currentFacID)) {
             Vector v = (Vector) result.get(currentFacID);
             v.addElement(wareHouse);
             result.put(currentFacID, v);
           }else {
             Vector v = new Vector();
             v.addElement(wareHouse);
             result.put(currentFacID, v);
           }
         }
         lastFacID = currentFacID;
       }
       //put all in hashtable
       warehouseV = BothHelpObjs.sort(warehouseV);
		 if (facilityIDV.size() > 1) {
         facilityIDV.insertElementAt("All Facilities",0);
         facilityNameV.insertElementAt("All Facilities",0);
         if (warehouseV.size() > 1) {
           warehouseV.insertElementAt("All Inventory",0);
           Vector v = new Vector(1);
           v.addElement("All Inventory");
           result.put("All Facilities",v);
         }
       }

		 if (warehouseV.size() > 1) {
       	warehouseV.insertElementAt("All",0);
       }


		 result.put("FACILITY_ID",facilityIDV);
       result.put("FACILITY_NAME",facilityNameV);
       result.put("WAREHOUSE",warehouseV);
     }catch(Exception e) {
       e.printStackTrace();
       radian.web.emailHelpObj.sendtrongemail("Error while loading initial data for inventory screen","InventoryTable.getInitialData  "+(String)inData.get("USERID"));
     }finally {
       dbrs.close();
     }
     return result;
   }

  protected String getFromWithOutSearchString(boolean ware, String facility, boolean eD, String expDate, String exp2Date, boolean pD,String promDate, String orderBy) {
    String from = " from (select cpig.catalog_id,cpig.cat_part_no,s.item_id,cpig.part_group_no,";
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
    from += " sum(s.in_purchasing) in_purchasing from inventory_status_snapshot s, catalog_part_item_group cpig where s.item_id = cpig.item_id";
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
      from += " group by cpig.catalog_id,cpig.cat_part_no,s.branch_plant,s.item_id,";
      //1-31-02
      if ("BIN".equalsIgnoreCase(orderBy)) {
        from += "s.bin,";
      }else if ("LOT".equalsIgnoreCase(orderBy)) {
        from += "s.bin,s.lot_num,";
      }

      from += "cpig.part_group_no) x,catalog_item_view b,cat_part_view a ,facility c";
    }else {
      from += " group by cpig.catalog_id,cpig.cat_part_no,s.item_id,";
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
    String from = " from (select syn.catalog_id,syn.cat_part_no,s.item_id,cpig.part_group_no,sum(s.available + s.on_hold) on_hand,";
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
    from += " from inventory_status_snapshot s, cat_part_synonym syn , catalog_part_item_group cpig where s.item_id = cpig.item_id";
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
      from += " group by syn.catalog_id,syn.cat_part_no,s.branch_plant,s.item_id,";
      //1-31-02
      if ("BIN".equalsIgnoreCase(orderBy)) {
        from += "s.bin,";
      }else if ("LOT".equalsIgnoreCase(orderBy)) {
        from += "s.bin,s.lot_num,";
      }

      from += "cpig.part_group_no) x,catalog_item_view b,cat_part_view a ,facility c";
    }else {
      from += " group by syn.catalog_id,syn.cat_part_no,s.item_id, ";
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

  public Hashtable getInventItemDetail(String itemId) throws Exception {
    String[] colHeads = {" \nWarehouse"," \nAvail."," \nHeld","On\nOrder","In\nPurchasing"};
    int[] colWidths = {115,60,60,60,60};
    final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING};
    Hashtable result = new Hashtable();
    String query = "select s.item_id ,c.facility_id warehouse,sum(s.available + s.on_hold) on_hand,";
    query += "sum(s.on_order) on_order,sum(s.available) available,sum(s.on_hold) on_hold,sum(s.in_purchasing) in_purchasing";
    query += " from inventory_status_snapshot s , facility c";
    query += " where s.branch_plant=c.branch_plant and item_id = "+itemId;
    query += " group by s.item_id , c.facility_id";

    AttributiveCellTableModel model = new AttributiveCellTableModel(colHeads,20);
    for (int j = model.getRowCount()-1;j >= 0;j--) {
      model.removeRow(j);
    }
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    model.setColumnType(colTypes);

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;
      while (rs.next()){
        Object[] oa = new Object[colHeads.length];
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("warehouse"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("available"));
        oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("on_hold"));
        oa[3] = BothHelpObjs.makeBlankFromNull(rs.getString("on_order"));
        oa[4] = BothHelpObjs.makeBlankFromNull(rs.getString("in_purchasing"));
        model.insertRow(count,oa);

        //this is where I alternate the color between row
        int cond = count % 2;
        if (cond == 0) {
          int[] rows = new int[]{count};
          for (int j = 0; j < model.getColumnCount(); j++) {
            int[] cols = new int[]{j};
            cellAtt.setBackground(new Color(224,226,250),rows,cols);
          }
        }
        count++;
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally{
      dbrs.close();
    }
    model.setColumnPrefWidth(colWidths);
    result.put("DATA_MODEL",model);

    return result;
  }

  public Hashtable getTableDataNew(Hashtable inData) throws Exception {
    Hashtable result = new Hashtable();
    try {
      String expDate = (String) inData.get("EXP_VAL");
      String exp2Date = (String) inData.get("EXP2_VAL");
      String promDate = (String) inData.get("PROM_VAL");
      boolean eD = ((Boolean) inData.get("EXP_BOL")).booleanValue();
      boolean pD = ((Boolean) inData.get("PROM_BOL")).booleanValue();
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
      PurchaseRequest PRI = new  PurchaseRequest(db);
      nowdate = PRI.getNowDate();
      nowD = simple.parse(nowdate,pos);
      timeNow = nowD.getTime();
      if (eD) {
        if (!BothHelpObjs.isBlankString(expDate)) {
          days = new Integer(expDate);
          newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
          fdate  = new String(simple2.format(newD));
          expired = BothHelpObjs.formatDate("toCharfromOrder",fdate);
        }

        if (!BothHelpObjs.isBlankString(exp2Date)) {
          days = new Integer(exp2Date);
          newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
          fdate  = new String(simple2.format(newD));
          expired2 = BothHelpObjs.formatDate("toCharfromOrder",fdate);
        }

        result.put("EXPIRE",BothHelpObjs.makeBlankFromNull(expired));
        result.put("EXPIRE2",BothHelpObjs.makeBlankFromNull(expired2));
      }

      if (pD) {
        if (!BothHelpObjs.isBlankString(promDate)) {
          days = new Integer(promDate);
          newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
          pdate  = new String(simple2.format(newD));
          promised = BothHelpObjs.formatDate("toCharfromOrder",pdate);
        }
        result.put("PROMISSED",BothHelpObjs.makeBlankFromNull(promised));
      }

      InventoryView inv = new InventoryView(db);
      result.put("TABLE_DATA",inv.retrieveSumNew(inData));

    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    }
    return result;
  }
  /*
  public Hashtable getTableDataNew(Hashtable inData) throws Exception {
    System.out.println(inData);
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

    String select = "select x.item_id,x.catalog_id,x.cat_part_no,x.available on_hand,x.on_order ,x.on_hold ,x.in_purchasing,";
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

      if (eD) {
        result.put("EXPIRE",BothHelpObjs.makeBlankFromNull(expired));
        result.put("EXPIRE2",BothHelpObjs.makeBlankFromNull(expired2));
      }

      if(pD) {
        result.put("PROMISSED",BothHelpObjs.makeBlankFromNull(promised));
      }

      InventoryView inv = new InventoryView(db);
      result.put("TABLE_DATA",inv.retrieveSumNew(select+from+where,orderBy));

    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    }
    return result;
  }
  */


  //Nawaz 01/09/02
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

    String select = "select x.item_id,x.catalog_id,x.cat_part_no,x.available on_hand,x.on_order ,x.on_hold ,x.in_purchasing,";
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

  public Hashtable getTableData(Hashtable inData) throws Exception {
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

    String item = HelpObjs.singleQuoteToDouble(itemIn);
    String where = new String("");
    boolean itemON = false;
    boolean facON = false;
    hosed = false;
    Hashtable result = new Hashtable();
    try {
      if ( item != (String) null && item.length() > 0) {
        ItemSynonym syn = new ItemSynonym(db);
        syn.setSearchString(item);
        Vector synV = syn.load();
        if (synV.size() > 0) {
          int j;
          where = where + "(";
          for (j=0;j<synV.size();j++) {
            where = where + "item_id = " + synV.elementAt(j).toString();
            if (j < synV.size() - 1) {
              where = where + " or ";
            }
          }
          where = where + ")";
          itemON = true;
        } else {
          result.put("TABLE_DATA",new Vector());
          return  result; // no data
        }
      }
      if (fac) {
        if (itemON) {
          where = where + " and ";
        }
        where = where + " facility_id = '" + facility + "'";
        facON = true;
      }
      if (ware) {
        if (itemON || facON) {
          where = where + " and ";
        }
        where = where + " warehouse = '" + warehouse + "'";
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
          throw e;

      }
      if (eD) {
          try {
            days = new Integer(expDate);
            newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
            fdate  = new String(simple2.format(newD));

            result.put("EXPIRE",BothHelpObjs.formatDate("toCharfromOrder",fdate));
            if (ware || itemON || facON) {
               where = where + " and ";
            }
            where = where + " expire_date <= to_date('"+fdate+"','yyyyDDD') ";
          } catch (Exception e){}; // no exp data

          try {
            days = new Integer(exp2Date);
            newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
            fdate  = new String(simple2.format(newD));

            result.put("EXPIRE2",BothHelpObjs.formatDate("toCharfromOrder",fdate));
            if (ware || itemON || facON) {
               where = where + " and ";
            }
            where = where + " expire_date > to_date('"+fdate+"','yyyyDDD') ";
          } catch (Exception e){}; // no exp data

      }
      if (pD) {
          days = new Integer(promDate);
          newD = new java.util.Date((long) (timeNow + 1000.0 * 24.0 * 60.0 * 60.0 * days.doubleValue()));
          pdate  = new String(simple2.format(newD));

          result.put("PROMISSED",BothHelpObjs.formatDate("toCharfromOrder",pdate));
          if (eD || ware || itemON || facON) {
             where = where + " and ";
          }
          where = where + " (promised_date <= to_date('"+pdate+"','yyyyDDD') and rev_prom_date is null ";
          where = where + "or (rev_prom_date like '%%/%%/%%' and to_date(rev_prom_date,'mm/dd/yy') <= to_date('"+pdate+"','yyyyDDD')))";
      }
      if (where.indexOf("=") < 0) {where = "item_id > 0 ";}
      InventoryView inv = new InventoryView(db);
      Vector tableCols = new Vector();
      int i;
      if (!hosed) {
        Vector IV = inv.retrieveSum(where,"item_id",allParts);

        result.put("TABLE_DATA",IV);
        return result;
      }

    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    }
    //if (tableData != null) result.put("DATA",tableData);
    result.put("TABLE_DATA",new Vector());
    return result;
  }

  public Hashtable loadFacs(int userId) throws Exception{
    Hashtable result = new Hashtable();
    try {
      //facs
      Personnel personnel = new Personnel(db);
      personnel.setPersonnelId(userId);
      personnel.load();
      //facs
      Hashtable facXref = (Hashtable) personnel.getRelatedFacilities();
      Hashtable facXware = new Hashtable();
      Facility fac = new Facility(db);
      for (Enumeration E=facXref.keys();E.hasMoreElements();){
          fac.setFacilityId(facXref.get(E.nextElement().toString()).toString());
          fac.load();
          if (fac.getPreferredWare() != null){
            facXware.put(fac.getFacilityId(),fac.getPreferredWare());
          }
      }

      result.put("preferred_ware",facXware);

      return result;
    } catch (Exception e){
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }
  }

  public Hashtable loadWare() throws Exception {
    Hashtable result = new Hashtable();
    try {
      //inv
      Facility fac = new Facility(db);
      Vector facs = new Vector();
      Hashtable facXref = new Hashtable();
      facs = fac.getAllWarehouseIds();
      facs.trimToSize();
      for (int i=0;i<facs.size();i++){
        fac.setFacilityId((String)facs.elementAt(i));
        fac.load();
        if (fac.getFacilityName() != (String)null){
          facXref.put(fac.getFacilityName(),(String)facs.elementAt(i));
        }
      }
      result.put("warehouse",facXref);
      return result;
    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;

    }
  }

  /*
  public Hashtable getApplication(String fac){
    Hashtable result = new Hashtable();
    try{
      ApplicationView ap =  new ApplicationView(db);
      ap.setFacilityId(fac);
      ap.load();
      Hashtable appXref = new Hashtable();
      Hashtable H = ap.getApplications();
      Enumeration E;
      String key;
      String appLabel;
      if (H.size() > 0) {
         for (E = H.keys();E.hasMoreElements();) {
            key = (String) E.nextElement();
            appLabel = key + "/" + H.get(key).toString();
            appXref.put(appLabel,key); // to be used by basket table
         }
      }

      return appXref;
    } catch (Exception e){
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      return null;
    }
  }
  */


}





























