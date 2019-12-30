
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class ReportData  {
  protected TcmISDBModel db;
  protected boolean materialMatrixReport = false;

  public ReportData(TcmISDBModel db) throws java.rmi.RemoteException {
    this.db = db;
  }

  public void setDB(TcmISDBModel db){
    this.db = db;
  }

  public void setMaterialMatrixReport(boolean b) {
    materialMatrixReport = b;
  }

  //this method will insert data into template table with template(report_hash_seq)
  public String createReportTemplate(String userID, Hashtable inData) throws Exception {
    String sessionID = userID+"xyz";
    try {
      Integer x = new Integer(HelpObjs.getNextValFromSeq(db,"report_hash_seq"));
      for(Enumeration enuma = inData.keys(); enuma.hasMoreElements();){
        String key = enuma.nextElement().toString();
        String value = "";
        if ("GROUP_BY".equalsIgnoreCase(key)) {
          value = HelpObjs.getStringValuesFromVector((Vector)inData.get(key));
        }else if ("Group_Matrix".equalsIgnoreCase(key)) {
          int[] tmp = (int[])inData.get("Group_Matrix");
          Vector tmpV = new Vector(tmp.length);
          for (int i = 0; i < tmp.length; i++) {
            tmpV.add((new Integer(tmp[i]).toString()));
          }
          value = HelpObjs.getStringValuesFromVector(tmpV);
        }else {
          value = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(inData.get(key).toString());
        }
        db.doQuery("insert into report_hash_table(report_id,field_name,field_value) values("+x.toString()+",'"+key+"','"+value+"')");
      }
      sessionID = x.toString();
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return sessionID;
  } //end of method

  public Hashtable loadReportTemplate(String sessionID) throws Exception {
      String query = "select field_name,field_value from report_hash_table where report_id = "+sessionID;
      Hashtable result = new Hashtable();
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while (rs.next()) {
              String key = rs.getString("field_name");
              String value = BothHelpObjs.makeBlankFromNull(rs.getString("field_value"));
              if ("GROUP_BY".equalsIgnoreCase(key)) {
                Vector v = HelpObjs.getVectorFromString(value);
                result.put(key,v);
              }else if ("Group_Matrix".equalsIgnoreCase(key)) {
                Vector v = HelpObjs.getVectorFromString(value);
                int[] tmp = new int[v.size()];
                for (int i = 0; i < v.size(); i++) {
                  Integer val = new Integer((String)v.elementAt(i));
                  tmp[i] = val.intValue();
                }
                result.put(key,tmp);
              }else {
                result.put(key,value);
              }
          }
      }catch (Exception e) {
          e.printStackTrace(); throw e;
      }finally {
          dbrs.close();
      }
      return result;
  } //end of method



  public Hashtable getMSDSRevisionInitialData(String userID) throws Exception {
    Hashtable result = new Hashtable(2);
    Vector facilityIDV = new Vector();
    Vector facilityNameV = new Vector();
    String query = "select distinct a.facility_id,nvl(a.facility_name,a.facility_id) facility_name"+
                   " from my_workarea_facility_view a, user_group_member ugm"+
                   " where ugm.facility_id = a.facility_id and ugm.personnel_id = a.personnel_id"+
                   " and ugm.user_group_id = 'CreateReport' and a.personnel_id = "+userID+
                   " order by facility_name";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFacID = "";
      while(rs.next()){
        facilityIDV.addElement(rs.getString("facility_id"));
        facilityNameV.addElement(rs.getString("facility_name"));
      }
      //now put everything together
      if (facilityIDV.size() > 1) {
        facilityIDV.insertElementAt("All",0);
        facilityNameV.insertElementAt("Allowed Facilities",0);
      }
      result.put("FACILITY_ID",facilityIDV);
      result.put("FACILITY_NAME",facilityNameV);
    }catch(Exception e){e.printStackTrace();throw e;
    }finally{
      dbrs.close();
    }
    return result;
  } //end of method

  public Hashtable getFormattedInitialData(String userID) throws Exception {
    Hashtable result = new Hashtable();
    Vector facilityIDV = new Vector();
    Vector facilityNameV = new Vector();
    String query = "select a.facility_id,nvl(a.facility_name,a.facility_id) facility_name,a.application,a.application_desc"+
                   " from my_workarea_facility_view a, user_group_member ugm"+
                   " where ugm.facility_id = a.facility_id and ugm.personnel_id = a.personnel_id"+
                   " and ugm.user_group_id = 'CreateReport' and a.personnel_id = "+userID+
                   " order by facility_name,application_desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFacID = "";
      while(rs.next()){
        String currentFacID = rs.getString("facility_id");
        if (currentFacID.equalsIgnoreCase(lastFacID)) {
          //add new work areas for facility
          Hashtable innerH = (Hashtable) result.get(currentFacID);
          Vector workAreaID = (Vector) innerH.get("WORK_AREA_ID");
          Vector workAreaDesc = (Vector) innerH.get("WORK_AREA_DESC");
          workAreaID.addElement(rs.getString("application"));
          workAreaDesc.addElement(rs.getString("application_desc"));
          //when I am here, I know that I have more than one work areas
          if (!workAreaID.contains("All")) {
            workAreaID.insertElementAt("All",0);
            workAreaDesc.insertElementAt("All Work Areas",0);
            workAreaID.insertElementAt("My Work Areas",1);
            workAreaDesc.insertElementAt("My Work Areas",1);
          }
        }else {
          //add new facility to list
          facilityIDV.addElement(currentFacID);
          facilityNameV.addElement(rs.getString("facility_name"));
          //add new work areas for facility
          Hashtable innerH = new Hashtable();
          Vector workAreaID = new Vector();
          Vector workAreaDesc = new Vector();
          workAreaID.addElement(rs.getString("application"));
          workAreaDesc.addElement(rs.getString("application_desc"));
          innerH.put("WORK_AREA_ID",workAreaID);
          innerH.put("WORK_AREA_DESC",workAreaDesc);
          result.put(currentFacID,innerH);
        }
        lastFacID = currentFacID;
      }
      //now put everything together
      if (facilityIDV.size() > 1) {
        facilityIDV.insertElementAt("All",0);
        facilityNameV.insertElementAt("All Allowed",0);
        Hashtable innerH = new Hashtable(2);
        Vector workAreaID = new Vector(2);
        Vector workAreaDesc = new Vector(2);
        workAreaID.addElement("All");
        workAreaDesc.addElement("All Work Areas");
        workAreaID.addElement("My Work Areas");
        workAreaDesc.addElement("My Work Areas");
        innerH.put("WORK_AREA_ID",workAreaID);
        innerH.put("WORK_AREA_DESC",workAreaDesc);
        result.put("All",innerH);
      }
      //if material matrix then get additional facility group
      if (materialMatrixReport) {
        try {
          getAdditionalFacilityGroup(result,facilityIDV,facilityNameV,userID,new Hashtable(13));
        }catch (Exception e) {
          e.printStackTrace();
          HelpObjs.sendMail(db,"Error occur while getting additional facility groups","Error occur while getting additional facility groups for material matrix",86030,false);
        }
      }

      result.put("FACILITY_ID",facilityIDV);
      result.put("FACILITY_NAME",facilityNameV);
    }catch(Exception e){e.printStackTrace();throw e;
    }finally{
      dbrs.close();
    }
    return result;
  } //end of method

  public void getAdditionalFacilityGroup(Hashtable result, Vector facilityV, Vector facilityDescV, String userID, Hashtable dockDeliveryPointInfoH) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {

     String query = "select distinct a.facility_group_id,'ReportEntity' reporting_entity_label from facility a, user_group_member ugm"+
                    " where a.facility_id = ugm.facility_id and ugm.user_group_id = 'CreateReport' and ugm.personnel_id = "+userID+
                    " and a.facility_group_id is not null order by facility_group_id desc";

      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String facilityGroup = rs.getString("facility_group_id");
        facilityV.insertElementAt(facilityGroup,1);
        facilityDescV.insertElementAt("Allowed "+facilityGroup,1);
        String reportingEntityLabel = BothHelpObjs.makeBlankFromNull(rs.getString("reporting_entity_label"));
        Hashtable h = new Hashtable();
        Vector reportingEntityIdV = new Vector();
        Vector reportingEntityDescV = new Vector();
        String tmpReportingEntity = "";
        if (reportingEntityLabel.length() > 0) {
          tmpReportingEntity = "All Reporting Entities";
        }else {
          tmpReportingEntity = "No Reporting Entity";
        }
        reportingEntityIdV.addElement(tmpReportingEntity);
        reportingEntityDescV.addElement(tmpReportingEntity);
        h.put("REPORTING_ENTITY_ID",reportingEntityIdV);
        h.put("REPORTING_ENTITY_DESC",reportingEntityDescV);
        h.put("REPORTING_ENTITY_LABEL",reportingEntityLabel);
        //work areas
        Hashtable innerH = new Hashtable(7);
        Vector workAreaID = new Vector(1);
        Vector workAreaDesc = new Vector(1);
        workAreaID.addElement("All Work Areas");
        workAreaDesc.addElement("All Work Areas");
        if (materialMatrixReport) {
          innerH.put("WORK_AREA_ID",workAreaID);
          innerH.put("WORK_AREA_DESC",workAreaDesc);
        }else {
          innerH.put("APPLICATION", workAreaID);
          innerH.put("APPLICATION_DESC", workAreaDesc);
        }
        innerH.put("DEFAULT_DOCK"+"All Work Areas","All Docks");
        innerH.put("DEFAULT_DELIVERY_POINT"+"All Work Areas","All Delivery Points");
        h.put(tmpReportingEntity,innerH);
        result.put(facilityGroup,h);

        //docks and delivery points info
        Hashtable h2 = new Hashtable(3);
        Vector dockIdV = new Vector(1);
        Vector dockDescV = new Vector(1);
        dockIdV.addElement("All Docks");
        dockDescV.addElement("All Docks");
        h2.put("DOCK_ID",dockIdV);
        h2.put("DOCK_DESC",dockDescV);
        Hashtable innerH2 = new Hashtable(2);
        Vector dpIdV = new Vector(1);
        Vector dpDescV = new Vector(1);
        dpIdV.addElement("All Delivery Points");
        dpDescV.addElement("All Delivery Points");
        innerH2.put("DELIVERY_POINT_ID",dpIdV);
        innerH2.put("DELIVERY_POINT_DESC",dpDescV);
        h2.put("All Docks",innerH2);
        dockDeliveryPointInfoH.put(facilityGroup,h2);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }
  } //end of method


  public static Vector getReportableUsageReport(TcmISDBModel db,Hashtable inHash)throws Exception {
    Vector out = new Vector();
    String fac = inHash.get("FACILITY").toString();
    String workArea = inHash.get("WORK_AREA").toString();
    String begMonth = inHash.get("BEGMONTH").toString();
    String begYear = inHash.get("BEGYEAR").toString();
    String endMonth = inHash.get("ENDMONTH").toString();
    String endYear = inHash.get("ENDYEAR").toString();
    String sortBy = inHash.get("SORT_BY").toString();
    Vector groupBy = (Vector)inHash.get("GROUP_BY");
    String searchType = inHash.get("SEARCH_TYPE").toString();
    String listId = inHash.get("LIST_ID").toString();
    String keyword = inHash.get("KEYWORD").toString();
    String cas_num = inHash.get("CAS_NUM").toString();
    String userID = inHash.get("USER_ID").toString();

    // group by Month
    boolean groupByMonth = false;
    for(int i=0;i<groupBy.size();i++){
      if(groupBy.elementAt(i).toString().equalsIgnoreCase("month")){
        groupByMonth = true;
        break;
      }
    }

    // search type
    boolean chemList = searchType.equalsIgnoreCase("list");
    boolean all = searchType.equalsIgnoreCase("all");
    boolean kw = searchType.equalsIgnoreCase("keyword");
    boolean singleChem = searchType.equalsIgnoreCase("CAS_NUM");

    String where = "where ";
    String from = "";
    String orderBy = "";

    // wh

    // facility
    if(!BothHelpObjs.isBlankString(fac) && !fac.equalsIgnoreCase("all")) {
      where = where + "facility = '"+fac+"' and ";
    }else if (fac.equalsIgnoreCase("All")) {
      where += "facility in (select facility_id from user_group_member where user_group_id = 'CreateReport' and personnel_id = "+userID+") and ";
    }
    // work area
    if(!BothHelpObjs.isBlankString(workArea) && !workArea.equalsIgnoreCase("all")) {
      if (workArea.equalsIgnoreCase("My Work Areas")) {
        if(!BothHelpObjs.isBlankString(fac) && !fac.equalsIgnoreCase("all")) {
          where += "location in (select application from my_workarea_facility_view ugma where ugma.personnel_id = "+userID+" and ugma.facility_id = '"+fac+"') and ";
        }else {
          where += "location in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
              " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = "+userID+") and ";
        }
      }else {
        where = where + "location = '" + workArea + "' and ";
      }
    }
    // begin date
    Integer begD = new Integer(199701);
    try{
      Integer bm = new Integer(begMonth);
      bm = new Integer(bm.intValue()+1);
      String sm = new String(bm.toString());
      if(sm.length() < 2) sm = "0"+sm;
      Integer by = new Integer(begYear);
      begD = new Integer(by.toString() + sm);
    }catch(Exception e){e.printStackTrace();}

    // end date
    Integer endD = new Integer(209012);
    try{
      Integer em = new Integer(endMonth);
      //add 2 to end month so I can use < (less than)
      em = new Integer(em.intValue()+2);
      Integer ey = new Integer(endYear);
      if(em.intValue() > 12){
        em = new Integer(1);
        ey = new Integer(ey.intValue() + 1);
      }
      String esm = new String(em.toString());
      if(esm.length() < 2) esm = "0"+esm;
      endD = new Integer(ey.toString() + esm);
    }catch(Exception e){e.printStackTrace();}

    where = where + "date_shipped >= to_date('"+begD.toString()+ "','YYYYMM') and date_shipped < to_date('"+endD.toString()+"','YYYYMM') ";

    if(chemList){
      where = where + "and list_id = '"+listId+"' ";
    }
    if(singleChem){
      where = where + "and cas_number = '"+cas_num+"' ";
    }

    // order by
    orderBy = "order by ";
    for(int i=0;i<groupBy.size();i++){
      String s = groupBy.elementAt(i).toString();
      if(s.equalsIgnoreCase("FACILITY")){
        s = "facility";
      }else if(s.equalsIgnoreCase("WORK_AREA")){
        s = "location";
      }else if(s.equalsIgnoreCase("CAS_NUM")){
        s = "cas_number";
      }else if(s.equalsIgnoreCase("DEL_POINT")){
        s = "delivery_point";
      }else if(s.equalsIgnoreCase("MONTH")){
        s = "year_month";
      }
      orderBy = orderBy + s + ", ";
    }
    if(sortBy.equalsIgnoreCase("DELIVERY_POINT")){
      orderBy = orderBy + "delivery_point ";
    }else if(sortBy.equalsIgnoreCase("WORK_AREA")){
      orderBy = orderBy + "location ";
    }else if(sortBy.equalsIgnoreCase("PART_NUM")){
      orderBy = orderBy + "fac_part_id ";
    }
    // now build the select, from and groupby parts and put the query together
    String query = "";
    if(chemList){
      // select
      String select1 = "select fac_part_id,display_name,location,facility,trade_name,cas_number,percent,wt_per_unit,sum(qty_shipped) qty_shipped, sum(wt_shipped) wt_shipped, sum(chemical_wt_shipped) lbs_reportable, delivery_point,application_display,delivery_point_display ";
      String groupBy1 = " group by fac_part_id,location,facility,trade_name,cas_number,display_name,percent,wt_per_unit,delivery_point,application_display,delivery_point_display ";
      if(groupByMonth){
        select1 = select1 + ", to_char(date_shipped,'yyyymm') year_month ";
        groupBy1 = groupBy1 + ", to_char(date_shipped,'yyyymm') ";
      }
      from = "from report_list_view_new ";
      query = select1+from+where+groupBy1+orderBy;
    }else{
      String select = "select fac_part_id,display_name,location,facility,trade_name,cas_number,percent,wt_per_unit,sum(qty_shipped) qty_shipped, sum(wt_shipped) wt_shipped, sum(chemical_wt_shipped) lbs_reportable, delivery_point,application_display,delivery_point_display ";
      String gb = " group by fac_part_id,location,facility,trade_name,cas_number,display_name,percent,wt_per_unit,delivery_point,application_display,delivery_point_display ";
      if(groupByMonth){
        select = select + ", to_char(date_shipped,'yyyymm') year_month ";
        gb = gb + ", to_char(date_shipped,'yyyymm') ";
      }
      from = "from report_composition_view ";
      query = select+from+where+gb+orderBy;
    }

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      //System.out.println("In RepoertData.java  Finished Querry ");
     while(rs.next()){
        Hashtable h = new Hashtable();
        h.put("CAS_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("cas_number")));
        h.put("RPT_CHEMICAL",BothHelpObjs.makeBlankFromNull(rs.getString("display_name")));
        h.put("FACILITY",BothHelpObjs.makeBlankFromNull(rs.getString("facility")));
        h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        h.put("DELIVERY_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_display")));
        h.put("FAC_PART_ID",BothHelpObjs.makeBlankFromNull(rs.getString("fac_part_id")));
        h.put("TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("trade_name")));
        h.put("QTY_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("qty_shipped")));
        h.put("WT_PER_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("wt_per_unit")));
        h.put("WT_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("wt_shipped")));
        h.put("PERCENT",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
        h.put("LBS_REPORTABLE",BothHelpObjs.makeBlankFromNull(rs.getString("lbs_reportable")));
        if(groupByMonth){
          h.put("YEAR_MONTH",BothHelpObjs.makeBlankFromNull(rs.getString("year_month")));
        }else{
          h.put("YEAR_MONTH","year_month");
        }
        out.addElement(h);
      }
    }catch (Exception e) { e.printStackTrace();throw e;
    }finally{dbrs.close();
    }
    return out;
  }


  public static Vector getVocUsageReport(TcmISDBModel db, Hashtable inHash)throws Exception {
    Vector out = new Vector();
    String fac = inHash.get("FACILITY").toString();
    String workArea = inHash.get("WORK_AREA").toString();
    String begMonth = inHash.get("BEGMONTH").toString();
    String begYear = inHash.get("BEGYEAR").toString();
    String endMonth = inHash.get("ENDMONTH").toString();
    String endYear = inHash.get("ENDYEAR").toString();
    String sortBy = inHash.get("SORT_BY").toString();
    String userID = inHash.get("USER_ID").toString();
    Vector groupBy = (Vector)inHash.get("GROUP_BY");
    // group by Month
    boolean groupByMonth = false;
    for(int i=0;i<groupBy.size();i++){
      if(groupBy.elementAt(i).toString().equalsIgnoreCase("month")){
        groupByMonth = true;
        break;
      }
    }

    String where = "where ";
    String from = "";
    String orderBy = "";

    // where
    // facility
    if(!BothHelpObjs.isBlankString(fac) && !fac.equalsIgnoreCase("all")) {
      where = where + "facility = '"+fac+"' and ";
    }else if (fac.equalsIgnoreCase("All")) {
      where += "facility in (select facility_id from user_group_member where user_group_id = 'CreateReport' and personnel_id = "+userID+") and ";
    }
    // work area
    if(!BothHelpObjs.isBlankString(workArea) && !workArea.equalsIgnoreCase("all")) {
      if (workArea.equalsIgnoreCase("My Work Areas")) {
        if(!BothHelpObjs.isBlankString(fac) && !fac.equalsIgnoreCase("all")) {
          where += "location in (select application from my_workarea_facility_view ugma where ugma.personnel_id = "+userID+" and ugma.facility_id = '"+fac+"') and ";
        }else {
          where += "location in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
                   " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = "+userID+") and ";
        }
      }else {
        where = where + "location = '" + workArea + "' and ";
      }
    }

    // begin date
    Integer begD = new Integer(199701);
    try{
      Integer bm = new Integer(begMonth);
      bm = new Integer(bm.intValue()+1);
      String sm = new String(bm.toString());
      if(sm.length() < 2) sm = "0"+sm;
      Integer by = new Integer(begYear);
      begD = new Integer(by.toString() + sm);
    }catch(Exception e){e.printStackTrace();}

    // end date
    Integer endD = new Integer(209012);
    try{
      Integer em = new Integer(endMonth);
      //add 2 to end month so I can use < (less than)
      em = new Integer(em.intValue()+2);
      Integer ey = new Integer(endYear);
      if(em.intValue() > 12){
        em = new Integer(1);
        ey = new Integer(ey.intValue() + 1);
      }
      String esm = new String(em.toString());
      if(esm.length() < 2) esm = "0"+esm;
      endD = new Integer(ey.toString() + esm);
    }catch(Exception e){e.printStackTrace();}

    where = where + "date_shipped >= to_date('"+begD.toString()+ "','YYYYMM') and date_shipped < to_date('"+endD.toString()+"','YYYYMM') ";

    // order by
    orderBy = "order by ";
    for(int i=0;i<groupBy.size();i++){
      String s = groupBy.elementAt(i).toString();
      if(s.equalsIgnoreCase("FACILITY")){
        s = "facility";
      }else if(s.equalsIgnoreCase("WORK_AREA")){
        s = "location";
      }else if(s.equalsIgnoreCase("DEL_POINT")){
        s = "delivery_point";
      }else if(s.equalsIgnoreCase("MONTH")){
        s = "year_month";
      }else if(s.equalsIgnoreCase("CAS_NUM")){
        s = "";   //not using this anymore
      }
      if (s.length() > 0) {
        orderBy = orderBy + s + ", ";
      }
    }
    if(sortBy.equalsIgnoreCase("DELIVERY_POINT")){
      orderBy = orderBy + "delivery_point ";
    }else if(sortBy.equalsIgnoreCase("WORK_AREA")){
      orderBy = orderBy + "location ";
    }else if(sortBy.equalsIgnoreCase("PART_NUM")){
      orderBy = orderBy + "fac_part_id ";
    }

    // group by
    String gb = " group by fac_part_id,facility,location,trade_name,voc_percent,wt_per_unit,wt_voc,application_display,delivery_point_display ";

    String select = "select fac_part_id,facility,location,sum(qty_shipped) qty_shipped,"+
                    "trade_name,voc_percent,wt_per_unit,sum(wt_shipped) wt_shipped,"+
                    "sum(wt_voc*qty_shipped) wt_voc,wt_voc mixture_voc,application_display,delivery_point_display ";
    if(groupByMonth){
      select = select + ", to_char(date_shipped,'yyyymm') year_month ";
      gb = gb + ", to_char(date_shipped,'yyyymm') ";
    }

	 //NEW Fields
	 select += ",density_lb_per_gal,volume_per_unit_gal,sum(volume_gal) volume_gal ";
	 gb += ",density_lb_per_gal,volume_per_unit_gal ";

	 from = "from report_material_view ";
    String query = select+from+where+gb+orderBy;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
      while(rs.next()){
        Hashtable h = new Hashtable();
        h.put("CAS_NUMBER","cas_number");
        h.put("RPT_CHEMICAL","display_name");
        h.put("FACILITY",BothHelpObjs.makeBlankFromNull(rs.getString("facility")));
        h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        h.put("DELIVERY_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_display")));
        h.put("FAC_PART_ID",BothHelpObjs.makeBlankFromNull(rs.getString("fac_part_id")));
        h.put("TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("trade_name")));
        h.put("QTY_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("qty_shipped")));
        h.put("WT_PER_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("wt_per_unit")));
        h.put("WT_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("wt_shipped")));
        h.put("PERCENT",BothHelpObjs.makeBlankFromNull(rs.getString("voc_percent")));
        h.put("MIXTURE_VOC",BothHelpObjs.makeBlankFromNull(rs.getString("mixture_voc")));
        h.put("LBS_REPORTABLE",BothHelpObjs.makeBlankFromNull(rs.getString("wt_voc")));
        if(groupByMonth){
          h.put("YEAR_MONTH",BothHelpObjs.makeBlankFromNull(rs.getString("year_month")));
        }else{
          h.put("YEAR_MONTH","year_month");
        }
		  h.put("DENSITY_LB_PER_GAL",BothHelpObjs.makeBlankFromNull(rs.getString("density_lb_per_gal")));
		  h.put("VOLUME_PER_UNIT_GAL",BothHelpObjs.makeBlankFromNull(rs.getString("volume_per_unit_gal")));
		  h.put("VOLUME_GAL",BothHelpObjs.makeBlankFromNull(rs.getString("volume_gal")));
		  out.addElement(h);
      }
    }catch (Exception e) { e.printStackTrace();throw e;
    }finally{
      dbrs.close();
    }
    return out;
  }
  public static Vector getMSDSReport(TcmISDBModel db,String facID) throws Exception {
    String query = "select * from msds_revision_view ";
    if(!BothHelpObjs.isBlankString(facID) && !facID.equalsIgnoreCase("all")){
      query = query + "where facility = '"+facID+"'";
    }
    Vector v = new Vector();
    DBResultSet dbrs = null;
      ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
      while(rs.next()){
        v.addElement(rs.getString("facility"));
        v.addElement(rs.getString("part_no"));
        v.addElement(rs.getString("trade_name"));
        v.addElement(rs.getString("manufacturer"));
        v.addElement(BothHelpObjs.formatDate("toCharfromDB",rs.getString("last_revision_date")));
        v.addElement(BothHelpObjs.formatDate("toCharfromDB",rs.getString("last_request_date")));
      }


    } catch (Exception e) { e.printStackTrace();throw e;
       } finally{
             dbrs.close();
            }
    return v;
  }

  public static Hashtable getReportLists(TcmISDBModel db) throws Exception {
    Hashtable h = new Hashtable();
    String query = "select * from list";
    DBResultSet dbrs = null;
      ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
      while(rs.next()){
        h.put(rs.getString("list_id"),rs.getString("list_name"));
      }

    } catch (Exception e) { e.printStackTrace();throw e;
       } finally{
             dbrs.close();
            }
    return h;
  }
  public static Vector getListChemicals(TcmISDBModel db,String listId) throws Exception {
    Vector v = new Vector();
    String query = "select distinct cas_number,rpt_chemical from report_list_snap where list_id = '"+listId+"' order by cas_number";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Hashtable h = new Hashtable();
        h.put("CAS_NUM",rs.getString("CAS_NUMBER"));
        h.put("DESC",rs.getString("RPT_CHEMICAL"));
        v.addElement(h);
      }

    }catch (Exception e) { e.printStackTrace();throw e;
    }finally{
      dbrs.close();
    }
    return v;
  }

  public static Vector selectChemicalsForSynonym(TcmISDBModel db,String s) throws Exception {
    Vector v = new Vector();
    String s1 = s.toLowerCase();
    String query = "select distinct cas_number, preferred_name from chem_synonym_view ";
    query = query + "where alternate_name like '%"+s1+"%' order by cas_number";

    //System.out.println("chem query:"+query);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Hashtable h = new Hashtable();
        h.put("CAS_NUM",rs.getString("cas_number"));
        h.put("NAME",rs.getString("preferred_name"));
        v.addElement(h);
      }
    }catch(Exception e){e.printStackTrace();throw e;
    }finally{
      dbrs.close();
    }
    return v;
  }
}
