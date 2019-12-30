package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.io.PrintWriter;
import java.util.*;
import java.sql.*;
import java.awt.*;

public class AdHocReportsDBObj
{
    protected TcmISDBModel db;

    public static final int STRING = 0;
    public static final int DATE = 2;
    public static final int INT = 1;
    public static final int NULLVAL = 3;
    String[] colHeadL =
        {
        "Base Fields"
    };

    String endYear;
    String endMonth;
    String beginYear;
    String beginMonth;
    String partNo;
    String categoryID;
    String listID;
    String CASListID;
    String type;
    String manufacturer;
    String application;
    String facilityID;
    String method;
    String unitOp;
    String emissionPt;
    String process;
    String count;
    String unit;
    Vector reportFields;
    Vector basefield;
    Vector ReportData = new Vector();

    public AdHocReportsDBObj(TcmISDBModel db)
    {
        this.db = db;
    }
    public AdHocReportsDBObj()
    {
    }
    public void setDb(TcmISDBModel db)
    {
        this.db = db;
    }

    //set method
    public void setEndYear(String s)
    {
        this.endYear = s;
    }
    public void setEndMonth(String s)
    {
        this.endMonth = s;
    }
    public void setBeginYear(String s)
    {
        this.beginYear = s;
    }
    public void setBeginMonth(String s)
    {
        this.beginMonth = s;
    }
    public void setPartNo(String s)
    {
        this.partNo = s;
    }
    public void setCategoryID(String s)
    {
        this.categoryID = s;
    }
    public void setListID(String s)
    {
        this.listID = s;
    }
    public void setType(String s)
    {
        this.type = s;
    }
    public void setManufacturer(String s)
    {
        this.manufacturer = s;
    }
    public void setApplication(String s)
    {
        this.application = s;
    }
    public void setFacilityID(String s)
    {
        this.facilityID = s;
    }
    public void setMethod(String s)
    {
        this.method = s;
    }
    public void setUnitOp(String s)
    {
        this.unitOp = s;
    }
    public void setEmissionPt(String s)
    {
        this.emissionPt = s;
    }
    public void setProcess(String s)
    {
        this.process = s;
    }
    //get method
    public String getEndYear()
    {
        return this.endYear;
    }
    public String getEndMonth()
    {
        return this.endMonth;
    }
    public String getBeginYear()
    {
        return this.beginYear;
    }
    public String getBeginMonth()
    {
        return this.beginMonth;
    }
    public String getPartNo()
    {
        return this.partNo;
    }
    public String getCategoryID()
    {
        return this.categoryID;
    }
    public String getListID()
    {
        return this.listID;
    }
    public String getType()
    {
        return this.type;
    }
    public String getManufacturer()
    {
        return this.manufacturer;
    }
    public String getApplication()
    {
        return this.application;
    }
    public String getFacilityID()
    {
        return this.facilityID;
    }
    public String getMethod()
    {
        return this.method;
    }
    public String getUnitOp()
    {
        return this.unitOp;
    }
    public String getEmissionPt()
    {
        return this.emissionPt;
    }
    public String getProcess()
    {
        return this.process;
    }

    public Vector searchManufacturer(String searchT) throws Exception
    {
        Vector v = new Vector();
        String query = "select mfg_id,mfg_desc from manufacturer ";
        query += "where lower(mfg_desc) like lower('%"+searchT+"%')";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable h = new Hashtable();
                h.put("MANUFACTURER_ID",rs.getString("mfg_id"));
                h.put("MANUFACTURER_DESC",rs.getString("mfg_desc"));
                v.addElement(h);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); throw e;
        }
        finally
        {
            dbrs.close();
        }

        return v;
    }

    public Hashtable saveTemplate(Hashtable inData) throws Exception
    {
        String action = (String)inData.get("SAVE_ACTION");
        String template = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)inData.get("TEMPLATE_NAME"));
        Integer userID = (Integer)inData.get("USER_ID");
        boolean result = false;
        String msg = new String();
        boolean override = false;
        Hashtable resultH = new Hashtable();
        try
        {
            if (action.equalsIgnoreCase("new"))
            {
                if (templateExist(template,userID.intValue()))
                {
                    msg = "Template name already exist.\nDo you want to overwrite it?";
                    result = false;
                    override = true;
                }
                else
                {
                    createTemplate(inData);
                    msg = "Template was successfully saved.";
                    result = true;
                }
            }
            else
            {//updating existing template
                //first delete it
                if (deleteTemplate(template,userID.intValue())) {
                  createTemplate(inData);
                  msg = "Template was successfully saved.";
                  result = true;
                }else {
                  msg = "An error occurred while updating template.\nPlease restart tcmIS and try again.\n If the problem recurs, contact tcmIS Customer Service Representative (CSR).";
                  result = false;
                }
            }
        }
        catch (Exception e) {
          e.printStackTrace();
          throw e;
        }
        resultH.put("SUCCESS",new Boolean(result));
        resultH.put("MSG",msg);
        resultH.put("OVERRIDE",new Boolean(override));
        return (resultH);
    } //end of method

     public Hashtable getReportData(TcmISDBModel db,Hashtable inData, String uID,PrintWriter pw) throws Exception {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable Result = new Hashtable();

        endYear = inData.get("END_YEARN").toString();
        endMonth = inData.get("END_MONTH").toString();
        beginYear = inData.get("BEGIN_YEARN").toString();
        beginMonth = inData.get("BEGIN_MONTH").toString();
        partNo = (String)inData.get("PART_NO");
        String partOperator = (String)inData.get("PART_OPERATOR");
        categoryID = (String)inData.get("CATEGORY");
        type = (String)inData.get("CHEM_TYPE");
        manufacturer = (String)inData.get("MANUFACTURER");
        String mfgOperator = (String)inData.get("MFG_OPERATOR");
        application = (String)inData.get("WORK_AREA").toString();
        facilityID = (String)inData.get("FACILITY_ID").toString();
        method = (String)inData.get("METHOD");
        unitOp = (String)inData.get("UNIT_OP");
        emissionPt = (String)inData.get("EMISSION_POINT");
        process = (String)inData.get("PROCESS");
        reportFields = (Vector)inData.get("REPORT_FIELDS");
        listID = (String)inData.get("LIST_ID");
        CASListID = (String)inData.get("CAST_NUMBER");

        String reportingEntity = (String)inData.get("REPORTING_ENTITY");
        String dockId = (String)inData.get("DOCK");
        String deliveryPointId = (String)inData.get("DELIVERY_POINT");

        String searchstring = new String("");
        String Groupby = new String(" group by ");
        String where = "";

        String loc = "";
        String reportCategory = "";
        String beginDay = (String)inData.get("BEGIN_DAY");
        String endDay = (String)inData.get("END_DAY");
        if (db.getClient().equalsIgnoreCase("SWA")) {
          loc = (String)inData.get("LOCATION");
          reportCategory = (String)inData.get("REPORT_CATEGORY");
        }

        String separator = "|";
        // begin date
        String beginDate = "";
        String endDate = "";
        Integer begD = new Integer(199701);
        try {
          Integer bm = new Integer(beginMonth);
          bm = new Integer(bm.intValue()+1);
          String sm = new String(bm.toString());
          if(sm.length() < 2) sm = "0"+sm;
          Integer by = new Integer(beginYear);
          if(beginDay.length() < 2) beginDay = "0"+beginDay;
          begD = new Integer(by.toString() + sm +beginDay);
        }catch(Exception e) {
          e.printStackTrace();
        }
        // end date
        Integer endD = new Integer(209012);
        try {
          Integer em = new Integer(endMonth);
          em = new Integer(em.intValue()+1);
          Integer ey = new Integer(endYear);
          String esm = new String(em.toString());
          if(esm.length() < 2) esm = "0"+esm;
          if(beginDay.length() < 2) beginDay = "0"+beginDay;
          endD = new Integer(ey.toString() + esm +endDay);
        }catch(Exception e) {
          e.printStackTrace();
        }
        beginDate = "to_date('"+begD.toString()+ "','YYYYMMDD')";
        endDate = "to_date('"+endD.toString()+"','YYYYMMDD') + 1";

        String additionalFrom = "";
        String reportField = "";
        for (int i = 0; i < reportFields.size(); i++) {
          if (!BothHelpObjs.isBlankString(reportFields.elementAt(i).toString())) {
            reportField += reportFields.elementAt(i).toString() +separator;
          }
        }
        //removing the last | pipe
        if (reportField.length() > 0) {
            reportField = reportField.substring(0,reportField.length()-1);
        }

        //building where clause
        if ((facilityID.length()>0) && (!(facilityID.startsWith("All")))) {
          //if facility is facility group
          if (HelpObjs.countQuery(db,"select count(*) from facility where facility_group_id = '"+facilityID+"'") > 0) {
            where += "a.facility = ugm.facility_id and ugm.user_group_id = ''CreateReport'' and ugm.personnel_id = "+uID+
                     " and ugm.facility_id = fgm.facility_id and fgm.facility_group_id = ''"+facilityID+"'' and ";
            additionalFrom += "user_group_member ugm,facility fgm";
          }else {
            where += "a.facility=''"+facilityID+"'' and ";
          }
        }
        if (facilityID.startsWith("All")) {
          where += "a.facility = ugm.facility_id and ugm.user_group_id = ''CreateReport'' and ugm.personnel_id = "+uID+" and ";
          additionalFrom += "user_group_member ugm";
        }

        if ((application.length()>0) && (!(application.equalsIgnoreCase("All Work Areas")))) {
          if (application.equalsIgnoreCase("My Work Areas")) {
            if(!BothHelpObjs.isBlankString(facilityID) && !facilityID.startsWith("All")) {
              where += "a.location in (select application from my_workarea_facility_view ugma where ugma.personnel_id = "+uID+" and ugma.facility_id = ''"+facilityID+"'') and ";
            }else {
              where += "a.location in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
                       " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = ''CreateReport'' and ugma.personnel_id = "+uID+") and ";
            }
          }else {
            where += "a.location=''"+application+"'' and ";
          }
        }
        if ((categoryID.length()>0) && (!(categoryID.equalsIgnoreCase("All Categories")))) {
            where += "a.category_id=''"+categoryID+"'' and ";
        }

        String sqlListID = null;
        if (type.equalsIgnoreCase("List")) {
          if (listID.length() > 0) {
            sqlListID = "'" + listID + "'";
          }
        }
        String sqlCASID = null;
        if (type.equalsIgnoreCase("Single")) {
          if (CASListID.length() > 0) {
            sqlCASID = "'" + CASListID + "'";
          }
        }

        if (partNo.length()>0) {
          partNo = partNo.trim();
          if ("is".equalsIgnoreCase(partOperator)) {
            where += "a.fac_part_id=''"+partNo+"'' and ";
          }else {
            where += "lower(a.fac_part_id)like ''%"+partNo.toLowerCase()+"%'' and ";
          }
        }
        if (manufacturer.length()>0) {
          manufacturer = manufacturer.trim();
          if ("is".equalsIgnoreCase(mfgOperator)) {
            where += "a.mfg_desc=''"+manufacturer+"'' and ";
          }else {
            where += "lower(a.mfg_desc) like ''%"+manufacturer.toLowerCase()+"%'' and ";
          }
        }
        if (!BothHelpObjs.isBlankString(loc)) {
          where += "a.part_location=''"+loc+"'' and ";
        }
        if (!BothHelpObjs.isBlankString(reportCategory)) {
          where += "a.root_category=''"+reportCategory+"'' and ";
        }

        if (!BothHelpObjs.isBlankString(reportingEntity)) {
          if (!"No Reporting Entity".equalsIgnoreCase(reportingEntity) &&
              !"All Reporting Entities".equalsIgnoreCase(reportingEntity)) {
            where += "a.reporting_entity_id=''" + reportingEntity + "'' and ";
          }
        }
        if (!"All Docks".equalsIgnoreCase(dockId)) {
          where += "a.dock_location_id=''"+dockId+"'' and ";
        }
        if (!"All Delivery Points".equalsIgnoreCase(deliveryPointId)) {
          where += "a.delivery_point=''"+deliveryPointId+"'' and ";
        }

        //removing the last "and "
        if (where.length() > 1) {
          where = where.substring(0,where.length()-4);
        }

        int numRecs = 0 ;
        try {
          String QueryString = "select fx_ad_hoc_sql('AdHocUsage','"+reportField+"','"+separator+"',"+beginDate+","+endDate+","+sqlListID+",'"+where+"','"+additionalFrom+"',"+sqlCASID+") from dual";
          dbrs = db.doQuery(QueryString);
          rs=dbrs.getResultSet();
          String reportSql = "";
          while(rs.next()) {
            reportSql = rs.getString(1);
          }
          //System.out.println("------- now execute query: "+reportSql);
          dbrs = db.doQuery(reportSql);
          rs=dbrs.getResultSet();
          while(rs.next()) {
              numRecs += 1;
              pw.println("<TR>\n");
              for (int j = 0; j < reportFields.size(); j++) {
                pw.println("<TD ALIGN=\"CENTER\">");
                pw.println(BothHelpObjs.makeSpaceFromNull(rs.getString(j+1)));
                pw.println("</TD>\n");
              }
              pw.println("</TR>\n");
          }
        }catch(Exception e) {
          Result.put("RECORDS","Error");
          e.printStackTrace();
          return Result;
        }finally {
          dbrs.close();
        }

        Result.put("RECORDS",""+numRecs+"");
        //System.out.println("The number of Records is "+numRecs);
        return Result;
    }

    public Hashtable getReportDataOld(TcmISDBModel db,Hashtable inData, String uID) throws Exception {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        StringBuffer Msg = new StringBuffer(1<<20);
        Hashtable Result = new Hashtable();

        endYear = inData.get("END_YEARN").toString();
        endMonth = inData.get("END_MONTH").toString();
        beginYear = inData.get("BEGIN_YEARN").toString();
        beginMonth = inData.get("BEGIN_MONTH").toString();
        partNo = (String)inData.get("PART_NO");
        categoryID = (String)inData.get("CATEGORY");
        type = (String)inData.get("CHEM_TYPE");
        manufacturer = (String)inData.get("MANUFACTURER_ID");
        application = (String)inData.get("WORK_AREA").toString();
        facilityID = (String)inData.get("FACILITY_ID").toString();
        method = (String)inData.get("METHOD");
        unitOp = (String)inData.get("UNIT_OP");
        emissionPt = (String)inData.get("EMISSION_POINT");
        process = (String)inData.get("PROCESS");
        reportFields = (Vector)inData.get("REPORT_FIELDS");
        listID = (String)inData.get("LIST_ID");
        CASListID = (String)inData.get("CAST_NUMBER");

        String searchstring = new String("");
        String Groupby = new String(" group by ");
        String where = " where ";

        //8-14-01
        String loc = "";
        String reportCategory = "";
        String beginDay = (String)inData.get("BEGIN_DAY");
        String endDay = (String)inData.get("END_DAY");
        if (db.getClient().equalsIgnoreCase("SWA")) {
          loc = (String)inData.get("LOCATION");
          reportCategory = (String)inData.get("REPORT_CATEGORY");
        }

        // begin date
        Integer begD = new Integer(199701);
        try
        {
            Integer bm = new Integer(beginMonth);
            bm = new Integer(bm.intValue()+1);
            String sm = new String(bm.toString());
            if(sm.length() < 2) sm = "0"+sm;
            Integer by = new Integer(beginYear);
            if(beginDay.length() < 2) beginDay = "0"+beginDay;
            begD = new Integer(by.toString() + sm +beginDay);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // end date
        Integer endD = new Integer(209012);
        try
        {
            Integer em = new Integer(endMonth);
            //add 2 to end month so I can use < (less than)
            /*
            em = new Integer(em.intValue()+1);
            Integer ey = new Integer(endYear);
            if(em.intValue() >=12)
            {
                em = new Integer(1);
                ey = new Integer(ey.intValue() + 1);
            }    */
            em = new Integer(em.intValue()+1);
            Integer ey = new Integer(endYear);
            String esm = new String(em.toString());
            if(esm.length() < 2) esm = "0"+esm;
            if(beginDay.length() < 2) beginDay = "0"+beginDay;
            endD = new Integer(ey.toString() + esm +endDay);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //4-23-01
        boolean speciated = false;
        boolean groupBy = false;

        String valForFunction = "";
        for (int i = 0; i < reportFields.size(); i++) {
          if (!BothHelpObjs.isBlankString(reportFields.elementAt(i).toString())) {
            valForFunction += reportFields.elementAt(i).toString() +"|";
          }
        }

        //removing the last | pipe
        if (valForFunction.length() > 0) {
            valForFunction = valForFunction.substring(0,valForFunction.length()-1);
        }

        //building select clause
        //fx_ad_hoc_select(report_type, base fields list,start_date,stop_date, [separator], [group by? Y/N])
        String query = "select fx_ad_hoc_select('AdHocUsage','"+valForFunction+"',to_date('"+begD.toString()+ "','YYYYMMDD'),to_date('"+endD.toString()+"','YYYYMMDD'),'|') from dual";
        try
        {
            //System.out.print("\n\n +++++++ The ad hoc usage build select: "+query+"\n");
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while(rs.next())
            {
              searchstring = rs.getString(1);
            }
        }catch (Exception sql) {
          Result.put("STRING_BUFFER",Msg);
          Result.put("RECORDS","Error");
          sql.printStackTrace();
          return Result;
        }finally {
          dbrs.close();
        }
        //building groupby clause
        query = "select fx_ad_hoc_select('AdHocUsage','"+valForFunction+"',to_date('"+begD.toString()+ "','YYYYMMDD'),to_date('"+endD.toString()+"','YYYYMMDD'),'|','Y') from dual";
        try
        {
            //System.out.print("\n\n +++++++ The ad hoc usage build groupby: "+query+"\n");
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while(rs.next())
            {
              Groupby += rs.getString(1);
            }
        }catch (Exception sql2) {
          Result.put("STRING_BUFFER",Msg);
          Result.put("RECORDS","Error");
          sql2.printStackTrace();
          return Result;
        }finally {
          dbrs.close();
        }
        //report contains speciated fields
        query = "select fx_ad_hoc_is_speciated('AdHocUsage','"+valForFunction+"','|') from dual";
        try
        {
          String temp = null;
          //System.out.print("\n\n +++++++ The ad hoc usage build speciated: "+query+"\n");
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while(rs.next())
          {
            temp = rs.getString(1);
          }
          speciated = "Y".equalsIgnoreCase(temp);
        }catch (Exception sql3) {
          Result.put("STRING_BUFFER",Msg);
          Result.put("RECORDS","Error");
          sql3.printStackTrace();
          return Result;
        }finally {
          dbrs.close();
        }

        //building from clause
        String from = " from ";
        String isList = "N";
        if (type.equalsIgnoreCase("List")) {
          isList = "Y";
        }
        query = "select fx_ad_hoc_from('AdHocUsage','"+valForFunction+"','"+isList+"','|') from dual";
        try
        {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while(rs.next())
          {
            from += BothHelpObjs.makeBlankFromNull(rs.getString(1));
          }
        }catch (Exception sql32) {
          Result.put("STRING_BUFFER",Msg);
          Result.put("RECORDS","Error");
          sql32.printStackTrace();
          return Result;
        }finally {
          dbrs.close();
        }


        //building where clause
        if ((facilityID.length()>0) && (!(facilityID.startsWith("All"))))
        {
            where += "a.facility='"+facilityID+"' and ";
        }
        //4-30-01
        if (facilityID.startsWith("All")) {
          where += "a.facility = b.facility_id and b.user_group_id = 'CreateReport' and b.personnel_id = "+uID+" and ";
          from += ",user_group_member b";
        }
        if ((application.length()>0) && (!(application.equalsIgnoreCase("All Work Areas")))) {
            where += "a.location='"+application+"' and ";
        }
        if ((categoryID.length()>0)
                && (!(categoryID.equalsIgnoreCase("All Categories"))))
        {
            where += "a.category_id='"+categoryID+"' and ";
        }
        if (type.equalsIgnoreCase("List"))
        {
          where += "a.list_id='"+listID+"' and ";
        }
        else if (type.equalsIgnoreCase("Single"))
        {
          if (speciated) {
            where += "a.cas_number='"+CASListID+"' and ";
          }else {
            where += " fx_item_has_cas(a.item_id,'"+CASListID+"',a.date_shipped) = 'Y' and ";
          }
        }

        if (partNo.length()>0)
        {
            where += "a.fac_part_id='"+partNo+"' and ";
        }
        if (manufacturer.length()>0)
        {
            where += "a.mfg_id='"+manufacturer+"' and ";
        }

        if (!BothHelpObjs.isBlankString(loc)) {
          where += "a.part_location='"+loc+"' and ";
        }
        if (!BothHelpObjs.isBlankString(reportCategory)) {
          where += "a.root_category='"+reportCategory+"' and ";
        }

        where = where + "a.date_shipped between to_date('"+begD.toString()+ "','YYYYMMDD') and to_date('"+endD.toString()+"','YYYYMMDD') ";
        //building additional where clause
        query = "select fx_ad_hoc_where('AdHocUsage','"+valForFunction+"','|') from dual";
        try
        {
          String temp = "";
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while(rs.next())
          {
            temp = BothHelpObjs.makeBlankFromNull(rs.getString(1));
          }
          if (!BothHelpObjs.isBlankString(temp)) {
            where += " and "+temp;
          }
        }catch (Exception sql33) {
          Result.put("STRING_BUFFER",Msg);
          Result.put("RECORDS","Error");
          sql33.printStackTrace();
          return Result;
        }finally {
          dbrs.close();
        }

        String QueryString = "select " + searchstring + from + where + Groupby;   //6-14-01 always use groupby is okay
        int numRecs = 0 ;
        try
        {
            //System.out.print("\n\n ++++++ The ad hoc usage query: "+QueryString+"\n");
            dbrs = db.doQuery(QueryString);
            rs=dbrs.getResultSet();
            //System.out.print("Finished The Querry\n ");

            while(rs.next())
            {
                numRecs += 1;
                Msg.append("<TR>\n");
                for (int j = 0; j < reportFields.size(); j++)
                {
                  //how about getting the justification from database, let say
                  if (true) {
                    Msg.append("<TD ALIGN=\"RIGHT\">");
                    Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString(j+1)));
                    Msg.append("</TD>\n");
                  }else {
                    Msg.append("<TD ALIGN=\"LEFT\">\n");
                    Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString(j+1)));
                    Msg.append("</TD>\n");
                  }
                }
                Msg.append("</TR>\n");
            }
        }
        catch(Exception e)
        {
            Result.put("STRING_BUFFER",Msg);
            Result.put("RECORDS","Error");
            e.printStackTrace();
            return Result;
        }
        finally
        {
          dbrs.close();
        }

        Result.put("STRING_BUFFER",Msg);
        Result.put("RECORDS",""+numRecs+"");
        //System.out.println("The number of Records is "+numRecs);
        return Result;
    }

    boolean isAggregate(TcmISDBModel db,String val) throws Exception {
      String temp = "'";
      StringTokenizer token = new StringTokenizer(val,"|");
      while (token.hasMoreTokens()) {
        temp += token.nextToken()+"','";
      }
      temp = temp.substring(0,temp.length()-2);
      String query = "select count(*) from base_field where report_type = 'AdHocUsage' and base_field in ("+temp+") and lower(aggregate) = lower('Y') ";
      int count = 0;
      try {
        count = HelpObjs.countQuery(db,query);
      }catch (Exception sql4) {
          sql4.printStackTrace();
      }finally {
      }
      return (count > 0);
    }

    String getLastDayOfMonth(TcmISDBModel db, String endD) throws Exception {
      String query = "select to_char(last_day(to_date('"+endD+"' || '01', 'YYYYMMDD')), 'YYYYMMDD') from dual";
      String temp = null;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while(rs.next())
          {
            temp = rs.getString(1);
          }
      }catch (Exception sql4) {
          sql4.printStackTrace();
      }finally {
          dbrs.close();
      }
      return (temp);
    }
    boolean getTcmISFeature(TcmISDBModel db) throws Exception {
        String query = "select feature_mode from tcmis_feature where feature = 'adHocReportsDBObj.adHocAverageUsage'";
      String temp = null;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while(rs.next())
          {
            temp = rs.getString("feature_mode");
          }
      }catch (Exception sql4) {
          sql4.printStackTrace();
      }finally {
          dbrs.close();
      }
      return ("1".equalsIgnoreCase(temp));
    }

    //this method will insert data into template table with template(report_hash_seq)
    public String createReportTemplate(String userID, Hashtable inData) throws Exception {
      String sessionID = userID+"xyz";
      try {
        Integer x = new Integer(HelpObjs.getNextValFromSeq(db,"report_hash_seq"));
        Hashtable innerH = (Hashtable) inData.get("SELECTED_DATA");
        innerH.put("USER_ID",userID);
        for(Enumeration enuma = innerH.keys(); enuma.hasMoreElements();){
          String key = enuma.nextElement().toString();
          String value = "";
          if ("REPORT_FIELDS".equalsIgnoreCase(key)) {
            value = HelpObjs.getStringValuesFromVector((Vector)innerH.get(key));
          }else {
            value = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(innerH.get(key).toString());
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
                if ("REPORT_FIELDS".equalsIgnoreCase(key)) {
                  Vector v = HelpObjs.getVectorFromString(value);
                  result.put(key,v);
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

    void createTemplate(Hashtable inData) throws Exception
    {
        String template = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)inData.get("TEMPLATE_NAME"));
        Integer userID = (Integer)inData.get("USER_ID");
        Hashtable h = (Hashtable)inData.get("SELECTED_DATA");
        endYear = h.get("END_YEAR").toString();
        endMonth = h.get("END_MONTH").toString();
        beginYear = h.get("BEGIN_YEAR").toString();
        beginMonth = h.get("BEGIN_MONTH").toString();
        partNo = (String)h.get("PART_NO");
        String partOperator = (String)h.get("PART_OPERATOR");
        categoryID = (String)h.get("CATEGORY_DESC");
        type = (String)h.get("CHEM_TYPE");

        if (type.equalsIgnoreCase("List"))
        {
            CASListID = (String)h.get("LIST_ID");
        }
        else if (type.equalsIgnoreCase("Single"))
        {
            CASListID = (String)h.get("CAST_NUMBER");
        }
        manufacturer = (String)h.get("MANUFACTURER");
        String manufacturerId = (String)h.get("MANUFACTURER_ID");
        String mfgOperator = (String)h.get("MFG_OPERATOR");
        application = (String)h.get("WORK_AREA").toString();
        facilityID = (String)h.get("FACILITY_ID").toString();
        method = (String)h.get("METHOD");
        unitOp = (String)h.get("UNIT_OP");
        emissionPt = (String)h.get("EMISSION_POINT");
        process = (String)h.get("PROCESS");
        reportFields = (Vector)h.get("REPORT_FIELDS");

        //8-09-01
        String loc = "";
        String reportCategory = "";
        String beginDay = (String)h.get("BEGIN_DAY");
        String endDay = (String)h.get("END_DAY");
        if (db.getClient().equalsIgnoreCase("SWA")) {
          loc = (String)h.get("LOCATION");
          reportCategory = (String)h.get("REPORT_CATEGORY");
        }

        String reportingEntity = (String)h.get("REPORTING_ENTITY");
        String dock = (String)h.get("DOCK");
        String deliveryPt = (String)h.get("DELIVERY_POINT");

        String myReportFields = new String("");
        for (int i = 0; i < reportFields.size(); i++)
        {
            myReportFields += (String)reportFields.elementAt(i) + ",";
        }
        //removing the last commas ','
        if (myReportFields.length() > 0)
        {
            myReportFields = myReportFields.substring(0,myReportFields.length()-1);
        }

        ResultSet rs = null;
        try
        {
            String query = "insert into usage_template "+
                           "(personnel_id,template,type,chem_cas_list,facility_id,application,process,part_no,unit_op,emission_point,manufacturer,"+
                           "category,report_fields,method,begin_month,begin_year,end_month,end_year,manufacturer_id,begin_day,end_day,location,"+
                           "report_category,part_operator,mfg_operator,reporting_entity_id,dock_location_id,delivery_point) "+
                           "values ("+userID+",'"+template+"','"+type+"','"+CASListID+"','"+facilityID+"','"+application+"','"+process+"'"+
                           ",'"+partNo+"','"+unitOp+"','"+emissionPt+"','"+manufacturer+"','"+categoryID+"','"+myReportFields+"','"+method+"'"+
                           ",'"+beginMonth+"','"+beginYear+"','"+endMonth+"','"+endYear+"','"+manufacturerId+"'"+
                           ","+beginDay+","+endDay+",'"+loc+"','"+reportCategory+"','"+partOperator.trim()+"','"+mfgOperator.trim()+"'"+
                           ",'"+reportingEntity+"','"+dock+"','"+deliveryPt+"')";

            db.doUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        }

    }

    public boolean deleteTemplate(String templateName,
                                  int userID) throws Exception
    {
        String query = "delete from usage_template where personnel_id = "+userID;
        query += " and template = '"+templateName+"'";
        try
        {
            //System.out.println("n\n-------- query: "+query);
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean templateExist(String templateName,
                                 int userID) throws Exception
    {
        String query = "select count(*) from usage_template where personnel_id = "+userID;
        query += " and lower(template) = lower('"+templateName+"')";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int count = 0;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            dbrs.close();
        }
        return (count > 0);
    }

    public Vector getTemplate(Hashtable inData) throws Exception
    {
        //System.out.println("\n-------- got here2222");
        Vector v = new Vector();
        String query = "select template from usage_template";
        query += " where personnel_id = "+inData.get("USER_ID");
        query += " order by template";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                v.addElement(rs.getString("template"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); throw e;
        }
        finally
        {
            dbrs.close();
        }

        if (v.size() > 1)
        {
            v.insertElementAt("Select a template",0);
        }
        return v;
    }

    public Hashtable getUsageTemplateInfo(Hashtable inData) throws Exception
    {
        Hashtable result = new Hashtable();

        Hashtable baseFieldInfo = getBaseFieldInfo();
        Vector process = new Vector();
        Vector unitOp = new Vector();
        Vector emissionPt = new Vector();
        String query = "select process_id from process order by FACILITY_ID ";
        DBResultSet dbrs = null;
        ResultSet rs = null;

        if (emissionPt.size() == 0)
        {
         emissionPt.addElement("   ");
        }
        if (process.size() == 0)
        {
         process.addElement("   ");
        }
        if (unitOp.size() == 0)
        {
         unitOp.addElement("   ");
        }

        Hashtable categoryH = getCategory();
        Vector category = (Vector)categoryH.get("CATEGORY_DESC");
        Vector categoryID = (Vector)categoryH.get("CATEGORY_ID");
        if (categoryID.size() > 1)
        {
            category.insertElementAt("All Categories",0);
            categoryID.insertElementAt("All Categories",0);
        }

        result.put("BASE_FIELD_INFO",baseFieldInfo);
        result.put("PROCESS",process);
        result.put("UNIT_OP",unitOp);
        result.put("EMISSION_PT",emissionPt);
        result.put("CATEGORY",category);
        result.put("CATEGORY_ID",categoryID);

        Vector v = new Vector(2);
        v.addElement("is");
        v.addElement("contains");
        result.put("SEARCH_OPERATOR",v);

        if (db.getClient().equalsIgnoreCase("SWA")) {
          Hashtable nh = getReportCategoryLocation((Integer)inData.get("USER_ID"));
          result.put("LOCATION",nh.get("LOCATION"));
          result.put("REPORT_CATEGORY",nh.get("REPORT_CATEGORY"));
        }

        Hashtable dockDeliveryPointInfoH = new Hashtable();
        result.put("FACILITY_WORK_AREA",getFacilityWorkArea((Integer)inData.get("USER_ID"),dockDeliveryPointInfoH));
        getDockDeliveryPoint((Integer)inData.get("USER_ID"),dockDeliveryPointInfoH);
        result.put("FACILITY_DOCK_DELIVERY_POINT",dockDeliveryPointInfoH);

        return result;
    } //end of method

    public void getDockDeliveryPoint(Integer userId, Hashtable dockDeliveryPointInfoH) throws Exception {
      String query = "select * from fac_app_dock_dp_view where personnel_id = "+userId+
                     " order by facility_id,dock_desc,delivery_point_desc";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      String lastFacId = "";
      int numberOfFac = 0;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          String fac = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
          if (!fac.equals(lastFacId)) {
            lastFacId = fac;
            numberOfFac++;
          }
          String dockId = BothHelpObjs.makeBlankFromNull(rs.getString("dock_location_id"));
          String dockDesc = BothHelpObjs.makeBlankFromNull(rs.getString("dock_desc"));
          String deliveryPoint = BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point"));
          String deliveryPointDesc = BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_desc"));
          if (dockDeliveryPointInfoH.containsKey(fac)) {
            Hashtable h = (Hashtable)dockDeliveryPointInfoH.get(fac);
            //dock
            Vector dockIdV = (Vector)h.get("DOCK_ID");
            Vector dockDescV = (Vector)h.get("DOCK_DESC");
            //new dock
            if (!dockIdV.contains(dockId)) {
              dockIdV.addElement(dockId);
              dockDescV.addElement(dockDesc);
              Hashtable innerH = new Hashtable(2);
              Vector dpIdV = new Vector();
              Vector dpDescV = new Vector();
              dpIdV.addElement(deliveryPoint);
              dpDescV.addElement(deliveryPointDesc);
              innerH.put("DELIVERY_POINT_ID",dpIdV);
              innerH.put("DELIVERY_POINT_DESC",dpDescV);
              h.put(dockId,innerH);
              if (dockIdV.size() > 1) {
                if (!dockIdV.contains("All Docks")) {
                  dockIdV.insertElementAt("All Docks",0);
                  dockDescV.insertElementAt("All Docks",0);
                  Hashtable innerH2 = new Hashtable(2);
                  Vector dpIdV2 = new Vector(1);
                  Vector dpDescV2 = new Vector(1);
                  dpIdV2.addElement("All Delivery Points");
                  dpDescV2.addElement("All Delivery Points");
                  innerH2.put("DELIVERY_POINT_ID",dpIdV2);
                  innerH2.put("DELIVERY_POINT_DESC",dpDescV2);
                  h.put("All Docks",innerH2);
                }
              }
            }else {
              //delivery points
              Hashtable innerH = (Hashtable) h.get(dockId);
              Vector dpIdV = (Vector) innerH.get("DELIVERY_POINT_ID");
              Vector dpDescV = (Vector) innerH.get("DELIVERY_POINT_DESC");
              dpIdV.addElement(deliveryPoint);
              dpDescV.addElement(deliveryPointDesc);
              if (dpIdV.size() > 1) {
                if (!dpIdV.contains("All Delivery Points")) {
                  dpIdV.insertElementAt("All Delivery Points",0);
                  dpDescV.insertElementAt("All Delivery Points",0);
                }
              }
            }
          }else {  //new facility
            Hashtable h = new Hashtable();
            Vector dockIdV = new Vector();
            Vector dockDescV = new Vector();
            dockIdV.addElement(dockId);
            dockDescV.addElement(dockDesc);
            h.put("DOCK_ID",dockIdV);
            h.put("DOCK_DESC",dockDescV);
            Hashtable innerH = new Hashtable(2);
            Vector dpIdV = new Vector();
            Vector dpDescV = new Vector();
            dpIdV.addElement(deliveryPoint);
            dpDescV.addElement(deliveryPointDesc);
            innerH.put("DELIVERY_POINT_ID",dpIdV);
            innerH.put("DELIVERY_POINT_DESC",dpDescV);
            h.put(dockId,innerH);
            dockDeliveryPointInfoH.put(fac,h);
           }
         }
       } catch (Exception e) {
         e.printStackTrace(); throw e;
       } finally {
         dbrs.close();
       }
       //now put everything together
       if (numberOfFac > 1) {
         Hashtable h = new Hashtable(3);
         Vector dockIdV = new Vector(1);
         Vector dockDescV = new Vector(1);
         dockIdV.addElement("All Docks");
         dockDescV.addElement("All Docks");
         h.put("DOCK_ID",dockIdV);
         h.put("DOCK_DESC",dockDescV);
         Hashtable innerH = new Hashtable(2);
         Vector dpIdV = new Vector(1);
         Vector dpDescV = new Vector(1);
         dpIdV.addElement("All Delivery Points");
         dpDescV.addElement("All Delivery Points");
         innerH.put("DELIVERY_POINT_ID",dpIdV);
         innerH.put("DELIVERY_POINT_DESC",dpDescV);
         h.put("All Docks",innerH);
         dockDeliveryPointInfoH.put("All Facilities",h);
       }
    } //end of method

    public Hashtable getFacilityWorkArea(Integer userId, Hashtable dockDeliveryPointInfoH) throws Exception {
      Hashtable result = new Hashtable();
      Vector facilityV = new Vector();
      Vector facilityDescV = new Vector();
      String query = "select facility_id,facility_name,application,application_desc,reporting_entity_id,"+
                     "reporting_entity_description,delivery_point,delivery_point_desc,dock_location_id,"+
                     "dock_desc,reporting_entity_label from fac_app_report_view where personnel_id = "+userId+
                     " order by facility_name,reporting_entity_id,application_desc";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      String lastFacId = "";
      String defaultReportingEntityLabel = "";
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          String fac = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
          if (!fac.equals(lastFacId)) {
            facilityV.addElement(fac);
            facilityDescV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_name")));
            lastFacId = fac;
          }
          String app = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
          String desc = BothHelpObjs.makeBlankFromNull(rs.getString("application_desc"));
          String reportEntityLabel = BothHelpObjs.makeBlankFromNull(rs.getString("reporting_entity_label"));
          if (reportEntityLabel.length() > 0 && defaultReportingEntityLabel.length() == 0) {
            defaultReportingEntityLabel = reportEntityLabel;
          }
          String reportEntityId = BothHelpObjs.makeBlankFromNull(rs.getString("reporting_entity_id"));
          String reportEntityDesc = BothHelpObjs.makeBlankFromNull(rs.getString("reporting_entity_description"));
          String dockId = BothHelpObjs.makeBlankFromNull(rs.getString("dock_location_id"));
          String dockDesc = BothHelpObjs.makeBlankFromNull(rs.getString("dock_desc"));
          String deliveryPoint = BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point"));
          String deliveryPointDesc = BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_desc"));
          if (result.containsKey(fac)) {
            Hashtable h = (Hashtable)result.get(fac);
            Vector reportingEntityIdV = (Vector)h.get("REPORTING_ENTITY_ID");
            Vector reportingEntityDescV = (Vector)h.get("REPORTING_ENTITY_DESC");
            //new reporting entity
            if (!reportingEntityIdV.contains(reportEntityId)) {
              if (reportingEntityIdV.size() > 1) {
                String tmpReportingEntity = "";
                if (defaultReportingEntityLabel.length() > 0) {
                  tmpReportingEntity = "All Reporting Entities";
                }else {
                  tmpReportingEntity = "No Reporting Entity";
                }
                if (!reportingEntityIdV.contains(tmpReportingEntity)) {
                  reportingEntityIdV.insertElementAt(tmpReportingEntity,0);
                  reportingEntityDescV.insertElementAt(tmpReportingEntity,0);
                  h.put("REPORTING_ENTITY_ID",reportingEntityIdV);
                  h.put("REPORTING_ENTITY_DESC",reportingEntityDescV);
                  h.put("REPORTING_ENTITY_LABEL",defaultReportingEntityLabel);
                  Hashtable innerH = new Hashtable(2);
                  Vector workAreaID = new Vector(2);
                  Vector workAreaDesc = new Vector(2);
                  workAreaID.addElement("All Work Areas");
                  workAreaDesc.addElement("All Work Areas");
                  workAreaID.addElement("My Work Areas");
                  workAreaDesc.addElement("My Work Areas");
                  innerH.put("APPLICATION",workAreaID);
                  innerH.put("APPLICATION_DESC",workAreaDesc);
                  h.put(tmpReportingEntity,innerH);
                }
              }
              reportingEntityIdV.addElement(reportEntityId);
              reportingEntityDescV.addElement(reportEntityDesc);
              //work areas
              Hashtable innerH = new Hashtable();
              Vector appId = new Vector();
              Vector appDesc = new Vector();
              appId.addElement(app);
              appDesc.addElement(desc);
              innerH.put("APPLICATION",appId);
              innerH.put("APPLICATION_DESC",appDesc);
              //default dock for work area
              if (dockDesc.length() > 0) {
                innerH.put("DEFAULT_DOCK"+app,dockId);
              }
              //default delivery point for work area
              if (deliveryPointDesc.length() > 0) {
                innerH.put("DEFAULT_DELIVERY_POINT"+app, deliveryPoint);
              }
              h.put(reportEntityId,innerH);
            }else{
              //work area for reporting entity
              Hashtable innerH = (Hashtable) h.get(reportEntityId);
              Vector appId = (Vector)innerH.get("APPLICATION");
              Vector appDesc = (Vector)innerH.get("APPLICATION_DESC");
              if (!appId.contains(app)) {
                appId.addElement(app);
                appDesc.addElement(desc);
              }
              //when I am in here that mean there will be more than one work areas for the reporting facility
              if (!db.getClient().equalsIgnoreCase("SWA")) {
                if (!appId.contains("All Work Areas")) {
                  appId.insertElementAt("All Work Areas", 0);
                  appDesc.insertElementAt("All Work Areas", 0);
                  appId.insertElementAt("My Work Areas", 1);
                  appDesc.insertElementAt("My Work Areas", 1);
                }
              }
              //default dock for work area
              if (dockDesc.length() > 0) {
                innerH.put("DEFAULT_DOCK" + app, dockId);
              }
              //default delivery point for work area
              if (deliveryPointDesc.length() > 0) {
                innerH.put("DEFAULT_DELIVERY_POINT" + app, deliveryPoint);
              }
            }
          }else {  //new facility
            Hashtable h = new Hashtable();
            Vector reportingEntityIdV = new Vector();
            Vector reportingEntityDescV = new Vector();
            reportingEntityIdV.addElement(reportEntityId);
            reportingEntityDescV.addElement(reportEntityDesc);
            h.put("REPORTING_ENTITY_ID",reportingEntityIdV);
            h.put("REPORTING_ENTITY_DESC",reportingEntityDescV);
            //reporting lable
            h.put("REPORTING_ENTITY_LABEL",reportEntityLabel);
            //work areas
            Hashtable innerH = new Hashtable();
            Vector appId = new Vector();
            Vector appDesc = new Vector();
            appId.addElement(app);
            appDesc.addElement(desc);
            innerH.put("APPLICATION",appId);
            innerH.put("APPLICATION_DESC",appDesc);
            //default dock for work area
            if (dockDesc.length() > 0) {
              innerH.put("DEFAULT_DOCK"+app,dockId);
            }
            //default delivery point for work area
            if (deliveryPointDesc.length() > 0) {
              innerH.put("DEFAULT_DELIVERY_POINT"+app, deliveryPoint);
            }
            h.put(reportEntityId,innerH);

            result.put(fac,h);
          }
        }
      } catch (Exception e) {
        e.printStackTrace(); throw e;
      } finally {
        dbrs.close();
      }
      //now put everything together
      if (facilityV.size() > 1) {
        facilityV.insertElementAt("All Facilities",0);
        facilityDescV.insertElementAt("All Allowed",0);
        Hashtable h = new Hashtable();
        Vector reportingEntityIdV = new Vector();
        Vector reportingEntityDescV = new Vector();
        String tmpReportingEntity = "";
        if (defaultReportingEntityLabel.length() > 0) {
          tmpReportingEntity = "All Reporting Entities";
        }else {
          tmpReportingEntity = "No Reporting Entity";
        }
        reportingEntityIdV.insertElementAt(tmpReportingEntity,0);
        reportingEntityDescV.insertElementAt(tmpReportingEntity,0);
        h.put("REPORTING_ENTITY_ID",reportingEntityIdV);
        h.put("REPORTING_ENTITY_DESC",reportingEntityDescV);
        h.put("REPORTING_ENTITY_LABEL",defaultReportingEntityLabel);
        Hashtable innerH = new Hashtable(2);
        Vector workAreaID = new Vector(2);
        Vector workAreaDesc = new Vector(2);
        workAreaID.addElement("All Work Areas");
        workAreaDesc.addElement("All Work Areas");
        workAreaID.addElement("My Work Areas");
        workAreaDesc.addElement("My Work Areas");
        innerH.put("APPLICATION",workAreaID);
        innerH.put("APPLICATION_DESC",workAreaDesc);
        innerH.put("DEFAULT_DOCK"+"All Work Areas","All Docks");
        innerH.put("DEFAULT_DELIVERY_POINT"+"All Work Areas","All Delivery Points");
        innerH.put("DEFAULT_DOCK"+"My Work Areas","All Docks");
        innerH.put("DEFAULT_DELIVERY_POINT"+"My Work Areas","All Delivery Points");
        h.put(tmpReportingEntity,innerH);
        result.put("All Facilities",h);
      }
      //get additional facility groups
      try {
        ReportData reportData = new ReportData(db);
        reportData.getAdditionalFacilityGroup(result,facilityV,facilityDescV,userId.toString(),dockDeliveryPointInfoH);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.sendMail(db,"Error occur while getting additional facility groups","Error occur while getting additional facility groups for ad hoc usage",86030,false);
      }

      result.put("FACILITY",facilityV);
      result.put("FACILITY_NAME",facilityDescV);
      return result;
    }

    public Hashtable getReportCategoryLocation(Integer userId) throws Exception {
      Hashtable h = new Hashtable();
      Hashtable ch = new Hashtable();
      Hashtable lh = new Hashtable();
      String query = "select facility_id,application,root_category,location from user_report_category_view ";
      query += "where personnel_id = "+userId+
               " union all select 'All Facilities','All Work Areas','All','All locations' from dual"+
               " union all select 'All Facilities','My Work Areas','All','All locations' from dual";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          String fac = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
          String app = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
          String category = BothHelpObjs.makeBlankFromNull(rs.getString("root_category"));
          String loc = BothHelpObjs.makeBlankFromNull(rs.getString("location"));
          //first dealing with report category (depending on fac+app)
          if (ch.containsKey(fac+app)) {
            Vector reportCategory = (Vector)ch.get(fac+app);
            if (!reportCategory.contains(category)) {
              reportCategory.addElement(category);
            }
            ch.put(fac+app,reportCategory);
          }else{
            Vector reportCategory = new Vector();
            reportCategory.addElement(category);
            ch.put(fac+app,reportCategory);
          }
          //next dealing with location (depending on fac+app+category)
          if (lh.containsKey(fac+app+category)) {
            Vector location = (Vector)lh.get(fac+app+category);
            location.addElement(loc);
            lh.put(fac+app+category,location);
          }else {
            Vector location = new Vector();
            location.addElement(loc);
            lh.put(fac+app+category,location);
          }
        }
      } catch (Exception e) {
        e.printStackTrace(); throw e;
      } finally {
        dbrs.close();
      }
      h.put("REPORT_CATEGORY",ch);
      h.put("LOCATION",lh);
      return h;
    }

    public Hashtable getBaseFieldInfo() throws Exception {
      Hashtable h = new Hashtable();
      Vector baseFieldV = new Vector();
      Vector compV = new Vector();
      Vector colorV = new Vector();
      String query = "select base_field,compatibility from base_field where report_type = 'AdHocUsage' order by display_order";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          baseFieldV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("base_field")));

          if ("S".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("compatibility")))) {
            compV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("compatibility")));
            colorV.addElement(Color.black);
          }else {
            compV.addElement("ISN");
            colorV.addElement(Color.black);
          }
          /*
          compV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("compatibility")));
          if ("S".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("compatibility")))) {
            colorV.addElement(new Color(20,20,200));
          }else if ("N".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("compatibility")))){
            colorV.addElement(new Color(250,20,20));
          }else if ("I".equalsIgnoreCase(BothHelpObjs.makeBlankFromNull(rs.getString("compatibility")))){
            colorV.addElement(new Color(0,100,0));
          }else {
            colorV.addElement(Color.black);
          }     */
        }
      } catch (Exception e) {
        e.printStackTrace(); throw e;
      } finally {
        dbrs.close();
      }
      h.put("BASE_FIELDS",baseFieldV);
      h.put("COMPATIBILITY",compV);
      h.put("COLOR",colorV);
      return h;
    }

    public Hashtable getCategory() throws Exception
    {
        Hashtable categoryH = new Hashtable();
        String query = "select category_id,category_desc from vv_category order by category_desc";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            Vector categoryDesc = new Vector();
            Vector categoryID = new Vector();
            while (rs.next())
            {
                categoryDesc.addElement(rs.getString("category_desc"));
                categoryID.addElement(rs.getString("category_id"));
            }

            categoryH.put("CATEGORY_DESC",categoryDesc);
            categoryH.put("CATEGORY_ID",categoryID);
        }
        catch (Exception e)
        {
            e.printStackTrace(); throw e;
        }
        finally
        {
            dbrs.close();
        }

        return categoryH;
    }

    public void insert(String col,String val,int type)  throws Exception
    {
        Integer I;
        DBResultSet dbrs = null;
        ResultSet rs = null;

        String query = new String("update report_template set " + col + "=");
        switch (type)
        {
        case INT:
            I = new Integer(val);
            query = query + I.intValue();
            break;
        case DATE:
            if (val.equals("nowDate"))
            {
                query = query + " SYSDATE";
            }
            else
            {
                query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
            }
            break;
        case STRING:
            query = query + "'" + val + "'";
            break;
        case NULLVAL:
            query = query + null;
            break;
        default:
            query = query + "'" + val + "'";
            break;
        }
        //query += " where traveler_id = '"+ +"'";
        //System.out.println("&&&&&&&&&&&&&&&& " + query);
        try
        {
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace(); HelpObjs.monitor(1,
                                                  "Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
    }

    public Hashtable loadTemplateInfo(Hashtable inData) throws Exception
    {
        Integer userID = (Integer)inData.get("USER_ID");
        String template = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)inData.get("TEMPLATE"));
        String query = "select * from usage_template where personnel_id = '"+userID+"'";
        query += " and template = '"+template+"'";
        Hashtable result = new Hashtable();
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                result.put("TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("type")));
                result.put("CAS_LIST",BothHelpObjs.makeBlankFromNull(rs.getString("chem_cas_list")));
                result.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
                result.put("REPORTING_ENTITY", BothHelpObjs.makeBlankFromNull(rs.getString("reporting_entity_id")));
                result.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application")));
                result.put("DOCK", BothHelpObjs.makeBlankFromNull(rs.getString("dock_location_id")));
                result.put("DELIVERY_POINT", BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point")));
                result.put("PROCESS",BothHelpObjs.makeBlankFromNull(rs.getString("process")));
                result.put("PART_NO",BothHelpObjs.makeBlankFromNull(rs.getString("part_no")));
                result.put("PART_OPERATOR",BothHelpObjs.makeBlankFromNull(rs.getString("part_operator")));
                result.put("UNIT_OP",BothHelpObjs.makeBlankFromNull(rs.getString("unit_op")));
                result.put("EMISSION_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("emission_point")));
                result.put("MANUFACTURER",BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer")));
                result.put("MANUFACTURER_ID",BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer_id")));
                result.put("MFG_OPERATOR",BothHelpObjs.makeBlankFromNull(rs.getString("mfg_operator")));
                result.put("CATEGORY",BothHelpObjs.makeBlankFromNull(rs.getString("category")));
                result.put("METHOD",BothHelpObjs.makeBlankFromNull(rs.getString("method")));
                result.put("BEGIN_MONTH",BothHelpObjs.makeBlankFromNull(rs.getString("begin_month")));
                result.put("BEGIN_YEAR",BothHelpObjs.makeBlankFromNull(rs.getString("begin_year")));
                result.put("END_MONTH",BothHelpObjs.makeBlankFromNull(rs.getString("end_month")));
                result.put("END_YEAR",BothHelpObjs.makeBlankFromNull(rs.getString("end_year")));
                result.put("COUNT",BothHelpObjs.makeBlankFromNull(rs.getString("COUNT")));
                result.put("UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("UNIT")));
                result.put("LOCATION",BothHelpObjs.makeBlankFromNull(rs.getString("location")));
                result.put("REPORT_CATEGORY",BothHelpObjs.makeBlankFromNull(rs.getString("report_category")));
                result.put("BEGIN_DAY",BothHelpObjs.makeBlankFromNull(rs.getString("begin_day")));
                result.put("END_DAY",BothHelpObjs.makeBlankFromNull(rs.getString("end_day")));
                String reportFields = BothHelpObjs.makeBlankFromNull(rs.getString("report_fields"));

                Vector v = new Vector();
                if (reportFields.length() > 0)
                {
                    StringTokenizer st = new StringTokenizer(reportFields,",");
                    while (st.hasMoreTokens())
                    {
                        v.addElement(st.nextToken());
                    }
                    result.put("REPORT_FIELDS",v);
                }
                else
                {
                    result.put("REPORT_FIELDS",v);       //send back an empty vector
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); throw e;
        }
        finally
        {
            dbrs.close();
        }
        //System.out.println("------- : "+result);
        return result;
    }
}