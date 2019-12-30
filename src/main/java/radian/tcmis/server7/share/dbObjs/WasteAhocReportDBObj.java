package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;

public class WasteAhocReportDBObj
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
    String hubWaste;
    String type;
    String application;
    String facilityID;
    String method;
    Vector reportFields;
    String vendor;
    String profile;
    String mgmtOption;
    String accumept;
    Vector ReportData = new Vector();

    public WasteAhocReportDBObj(TcmISDBModel db)
    {
        this.db = db;
    }
    public WasteAhocReportDBObj()
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
    public void setHubWaste(String s)
    {
        this.hubWaste = s;
    }
    public void setType(String s)
    {
        this.type = s;
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
    public String getHubWaste()
    {
        return this.hubWaste;
    }
    public String getType()
    {
        return this.type;
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

    public Hashtable getVendorInfo() throws Exception
    {
        Hashtable h = new Hashtable();
        Vector vID = new Vector();
        Vector vDesc = new Vector();
        String query = "select distinct(vendor_id),vendor_name from waste_facility_vendor_view";
        query += " order by vendor_id";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                vID.addElement(rs.getString("vendor_id"));
                vDesc.addElement(rs.getString("vendor_name"));
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

        if(vID.size() > 1)
        {
            vID.insertElementAt("Select a vendor",0);
            vDesc.insertElementAt("Select a vendor",0);
        }

        h.put("VENDOR_ID",vID);
        h.put("VENDOR_NAME",vDesc);
        return h;
    }

    public Vector getWasteMgmtOpt(Hashtable inData) throws Exception
    {
        Vector v = new Vector();
        String searchT = (String)inData.get("SEARCH_TEXT");
        String query = "select management_option,management_option_desc,management_option || management_option_desc from vv_waste_management_option";
        if (!BothHelpObjs.isBlankString(searchT))
        {
            query += " where lower(management_option || management_option_desc) like lower('%"+searchT+"%')";
        }
        query += " order by management_option";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable h = new Hashtable();
                h.put("MGMT_OPTION_ID",rs.getString("management_option"));
                h.put("DESCRIPTION",BothHelpObjs.makeBlankFromNull(rs.getString("management_option_desc")));
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

    public Vector getWasteProfile(Hashtable inData) throws Exception
    {
        Vector v = new Vector();
        String searchT = (String)inData.get("SEARCH_TEXT");
        String vendorID = (String)inData.get("VENDOR_ID");
        String query = "select vendor_profile_id,description from waste_profile_synonym_view";
        query += " where vendor_id = '"+vendorID+"'";
        if (!BothHelpObjs.isBlankString(searchT))
        {
            query += " and lower(search_string) like lower('%"+searchT+"%')";
        }
        query += " order by vendor_profile_id";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable h = new Hashtable();
                h.put("VENDOR_PROFILE_ID",rs.getString("vendor_profile_id"));
                h.put("DESCRIPTION",
                      BothHelpObjs.makeBlankFromNull(rs.getString("description")));
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
                if (deleteTemplate(template,userID.intValue()))
                {
                    createTemplate(inData);
                    msg = "Template was successfully saved.";
                    result = true;
                }
                else
                {
                    msg = "An error occurred while updating template.\nPlease restart tcmIS and try again.\n If the problem recurs, contact tcmIS Customer Service Representative (CSR).";
                    result = false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        resultH.put("SUCCESS",new Boolean(result));
        resultH.put("MSG",msg);
        resultH.put("OVERRIDE",new Boolean(override));

        return (resultH);
    }

    public Hashtable getReportData(TcmISDBModel db,Hashtable inData,String uID) throws Exception
    {
        StringBuffer Msg = new StringBuffer(1<<20);
        Hashtable Result = new Hashtable();

        endYear = inData.get("END_YEARN").toString();
        endMonth = inData.get("END_MONTH").toString();
        beginYear = inData.get("BEGIN_YEARN").toString();
        beginMonth = inData.get("BEGIN_MONTH").toString();
        hubWaste = (String)inData.get("HUB_WASTE");
        type = (String)inData.get("TYPE");
        application = (String)inData.get("WORK_AREA_NAME");
        facilityID = (String)inData.get("FACILITY_ID");
        method = (String)inData.get("METHOD");
        reportFields = (Vector)inData.get("REPORT_FIELDS");
        this.vendor = (String)inData.get("VENDOR");
        this.accumept = (String)inData.get("ACCUME_PT");
        this.profile = (String)inData.get("PROFILE_ID");
        mgmtOption = (String)inData.get("MGMT_OPTION");

        String reportField = "";
        String where = "";
        String separator = "|";

        boolean groupBy = false;
        boolean speciated = false;

        for (int i = 0; i < reportFields.size(); i++) {
          reportField += reportFields.elementAt(i)+separator;
        }
        //removing the last '|'
        if (reportField.length() > 0) {
          reportField = reportField.substring(0,reportField.length()-1);
        }

        if ((facilityID.length()>0) && (!(facilityID.startsWith("All")))) {
          where += "a.facility_id=''"+facilityID+"'' and ";
        }
        if (facilityID.startsWith("All")) {
          where += "a.facility_id = ugm.facility_id and ugm.user_group_id = ''CreateReport'' and ugm.personnel_id = "+uID+" and ";
        }
        if ((application.length()>0) && (!(application.startsWith("All")))) {
          where += "a.application like ''%"+application+"%'' and ";
        }
        if ((vendor.length()>0) && (!(vendor.startsWith("All")))) {
          where += "a.vendor_id=''"+vendor+"'' and ";
        }
        if ((accumept.length()>0) && (!(accumept.startsWith("All")))) {
          where += "a.generation_point=''"+accumept+"'' and ";
        }
        if (type.equalsIgnoreCase("Profile")) {
          where += "a.vendor_profile_id=''"+this.profile+"'' and ";
        }
        if (mgmtOption.length() > 0) {
          where += "a.management_option = ''"+mgmtOption+"'' and ";
        }
        //removing last 'and '
        if (where.length() > 1) {
          where = where.substring(0,where.length()-4);
        }

        //user choose not to see hub waste
        if ("Y".equalsIgnoreCase(hubWaste)) {
          where += " and a.facility_id = wl.facility_id and a.generation_point = wl.waste_location_id and wl.hub_based = ''N'' ";
        }

        // begin date
        String beginDate = "";
        Integer begD = new Integer(199701);
        try {
          Integer bm = new Integer(beginMonth);
          bm = new Integer(bm.intValue()+1);
          String sm = new String(bm.toString());
          if(sm.length() < 2) sm = "0"+sm;
          Integer by = new Integer(beginYear);
          begD = new Integer(by.toString() + sm);
        }catch(Exception e) {
          e.printStackTrace();
        }
        // end date
        String endDate = "";
        Integer endD = new Integer(209012);
        try {
          Integer em = new Integer(endMonth);
          em = new Integer(em.intValue()+1);
          Integer ey = new Integer(endYear);
          if(em.intValue() > 12) {
            em = new Integer(1);
            ey = new Integer(ey.intValue() + 1);
          }
          String esm = new String(em.toString());
          if(esm.length() < 2) esm = "0"+esm;
          endD = new Integer(ey.toString() + esm);
        } catch(Exception e) {
          e.printStackTrace();
        }
        beginDate = "to_date('"+begD.toString()+ "','YYYYMM')";
        endDate = "last_day(to_date('"+endD.toString()+"','YYYYMM'))";


        String additionalFrom = "";
        if (facilityID.startsWith("All")) {
          additionalFrom += "user_group_member ugm,";

          //user choose not to see hub waste
          if ("Y".equalsIgnoreCase(hubWaste)) {
            additionalFrom += "waste_location wl,";
          }
        }else {
          //user choose not to see hub waste
          if ("Y".equalsIgnoreCase(hubWaste)) {
            additionalFrom += "waste_location wl,";
          }
        }
        //removing the last ','
        if (additionalFrom.length() > 1) {
          additionalFrom = additionalFrom.substring(0,additionalFrom.length()-1);
        }

        DBResultSet dbrs = null;
        ResultSet rs = null;
        int numRecs = 0 ;
        try {
          String QueryString = "select fx_ad_hoc_sql('AdHocWaste','"+reportField+"','"+separator+"',"+beginDate+","+endDate+",null,'"+where+"','"+additionalFrom+"') from dual";
          dbrs = db.doQuery(QueryString);
          rs=dbrs.getResultSet();
          String reportSql = "";
          while(rs.next()) {
            reportSql = rs.getString(1);
          }
          //System.out.println("------- report sql: "+reportSql);
          dbrs = db.doQuery(reportSql);
          rs=dbrs.getResultSet();
          while(rs.next()) {
            numRecs += 1;
            for (int j = 0; j < reportFields.size(); j++) {
              Msg.append("<TD ALIGN=\"CENTER\">\n");
              Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString(j+1)));
              Msg.append("</TD>\n");
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


    //public Vector getReportData(TcmISDBModel db,Hashtable inData,String uID) throws Exception
    public Hashtable getReportDataOld(TcmISDBModel db,Hashtable inData,String uID) throws Exception
    {
        StringBuffer Msg = new StringBuffer(1<<20);
        Hashtable Result = new Hashtable();

        endYear = inData.get("END_YEARN").toString();
        endMonth = inData.get("END_MONTH").toString();
        beginYear = inData.get("BEGIN_YEARN").toString();
        beginMonth = inData.get("BEGIN_MONTH").toString();
        hubWaste = (String)inData.get("HUB_WASTE");
        type = (String)inData.get("TYPE");
        application = (String)inData.get("WORK_AREA_NAME");
        facilityID = (String)inData.get("FACILITY_ID");
        method = (String)inData.get("METHOD");
        reportFields = (Vector)inData.get("REPORT_FIELDS");
        this.vendor = (String)inData.get("VENDOR");
        this.accumept = (String)inData.get("ACCUME_PT");
        this.profile = (String)inData.get("PROFILE_ID");

        //5-10-01
        mgmtOption = (String)inData.get("MGMT_OPTION");

        String searchstring = new String("");
        String Groupby = new String(" group by ");
        String where = " where ";

        boolean groupBy = false;
        boolean speciated = false;

        for (int i = 0; i < reportFields.size(); i++)
        {
            if (reportFields.elementAt(i).toString().startsWith("Weight (lb.)"))
            {
                searchstring += "sum(weight) weight,";
                groupBy = true;
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Facility"))
            {
                searchstring += "a.facility_id,";
                Groupby +=  "a.facility_id,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Work Area"))
            {
                searchstring += "application,";
                Groupby += "application,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Accumulation Point"))
            {
                searchstring += "generation_point,";
                Groupby += "generation_point,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Profile"))
            {
                searchstring += "vendor_profile_id,";
                Groupby += "vendor_profile_id,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Vendor"))
            {
                /*searchstring += "vendor_id,";
                Groupby += "vendor_id,";     using company name coz user does not know vendor_id*/
                searchstring += "company_name,";
                Groupby += "company_name,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Packaging"))
            {
                searchstring += "packaging,";
                Groupby +=  "packaging,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("CAS Number"))
            {
                searchstring += "cas_number,";
                Groupby +=  "cas_number,";
                speciated = true;
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Chemical Name"))
            {
                searchstring += "chemical_name,";
                Groupby +=  "chemical_name,";
                speciated = true;
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Profile Description"))
            {
                searchstring += "description,";
                Groupby +=  "description,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Management Option"))
            {
                searchstring += "management_option_desc,";
                Groupby +=  "management_option_desc,";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Month"))
            {
                searchstring += "to_char(actual_ship_date,'mm/yyyy')  month,";
                Groupby +=  "to_char(actual_ship_date,'mm/yyyy'),";
            }
            if (reportFields.elementAt(i).toString().equalsIgnoreCase("Cost"))
            {
                //searchstring += "sum(cost) cost,";
                searchstring += "to_char(round(sum(cost)),'$9,999,999,999.99') cost,";
                //Groupby +=  "cost,";
                groupBy = true;
            }
        }

        if (searchstring.length() > 0)
        {
            searchstring = searchstring.substring(0,searchstring.length()-1);
        }
        if (Groupby.length() > 0)
        {
            Groupby = Groupby.substring(0,Groupby.length()-1);
        }

        if ((facilityID.length()>0) && (!(facilityID.startsWith("All"))))
        {
            where += "a.facility_id='"+facilityID+"' and ";
        }
        //4-30-01
        if (facilityID.startsWith("All"))
        {
            where += "a.facility_id = b.facility_id and b.user_group_id = 'CreateReport' and b.personnel_id = "+uID+" and ";
        }

        if ((application.length()>0) && (!(application.startsWith("All"))))
        {
            where += "a.application like '%"+application+"%' and ";
        }
        if ((vendor.length()>0) && (!(vendor.startsWith("All"))))
        {
            where += "a.vendor_id='"+vendor+"' and ";
        }
        if ((accumept.length()>0) && (!(accumept.startsWith("All"))))
        {
            where += "a.generation_point='"+accumept+"' and ";
        }
        if (type.equalsIgnoreCase("Profile"))
        {
            where += "a.vendor_profile_id='"+this.profile+"' and ";
        }
        if (mgmtOption.length() > 0) {
          where += "a.management_option = '"+mgmtOption+"' and ";
        }

        //user choose not to see hub waste
        if ("Y".equalsIgnoreCase(hubWaste)) {
          where += "a.facility_id = c.facility_id and a.generation_point = c.waste_location_id and c.hub_based = 'N' and ";
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
            begD = new Integer(by.toString() + sm);
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
            em = new Integer(em.intValue()+2);
            Integer ey = new Integer(endYear);
            if(em.intValue() > 12)
            {
                em = new Integer(1);
                ey = new Integer(ey.intValue() + 1);
            }
            String esm = new String(em.toString());
            if(esm.length() < 2) esm = "0"+esm;
            endD = new Integer(ey.toString() + esm);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //where = where + "a.seal_date >= to_date('"+begD.toString()+ "','YYYYMM') and a.seal_date < to_date('"+endD.toString()+"','YYYYMM') ";
        where = where + "a.actual_ship_date >= to_date('"+begD.toString()+ "','YYYYMM') and a.actual_ship_date < to_date('"+endD.toString()+"','YYYYMM') ";
        if (where.length() > 0)
        {
            where = where.substring(0,where.length()-1);
        }

        String from = " from ";
        if (facilityID.startsWith("All")) {
          if (speciated) {
            from += "waste_report_chem_view a, user_group_member b ";
          }else {
            from += "waste_report_view a, user_group_member b ";
          }

          //user choose not to see hub waste
          if ("Y".equalsIgnoreCase(hubWaste)) {
            from += ",waste_location c";
          }

        }else {
          if (speciated) {
            from += "waste_report_chem_view a ";
          }else {
            from += "waste_report_view a ";
          }

          //user choose not to see hub waste
          if ("Y".equalsIgnoreCase(hubWaste)) {
            from += ",waste_location c";
          }
        }

        //String QueryString = searchstring + from + where + Groupby;
        String QueryString = "";
        if (groupBy) {
          QueryString = "select " + searchstring + from + where + Groupby;
        }else {
          QueryString = "select distinct " + searchstring + from + where;
        }
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int numRecs = 0 ;
        try
        {
            dbrs = db.doQuery(QueryString);
            rs=dbrs.getResultSet();
            //System.out.print("Finished The Querry\n");
            while(rs.next())
            {
                numRecs += 1;

                for (int j = 0; j < reportFields.size(); j++)
                {
                    if (reportFields.elementAt(j).toString().startsWith("Weight (lb.)"))
                    {
                        Msg.append("<TD ALIGN=\"RIGHT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("weight")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Facility"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("facility_id")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Work Area"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("application")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Accumulation Point"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("generation_point")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Profile"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("vendor_profile_id")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Vendor"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("company_name")));
                        Msg.append("</TD>\n");     //getting company_name instead of vendor_id
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Packaging"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("packaging")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Profile Description"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("description")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Management Option"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("management_option_desc")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("CAS Number"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("cas_number")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Month"))
                    {
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("month")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Cost"))
                    {
                        //Msg.append("<TD ALIGN=\"LEFT\">\n");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("cost")));
                        Msg.append("<TD ALIGN=\"RIGHT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("cost")));
                        Msg.append("</TD>\n");
                    }
                    if (reportFields.elementAt(j).toString().equalsIgnoreCase("Chemical Name"))
                    {
                        //Msg.append("<TD ALIGN=\"LEFT\">\n");Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("month")));
                        Msg.append("<TD ALIGN=\"LEFT\">\n");
                        Msg.append(BothHelpObjs.makeSpaceFromNull(rs.getString("chemical_name")));
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

    void createTemplate(Hashtable inData) throws Exception
    {

        String template = BothHelpObjs.changeSingleQuotetoTwoSingleQuote((String)inData.get("TEMPLATE_NAME"));
        Integer userID = (Integer)inData.get("USER_ID");
        Hashtable h = (Hashtable)inData.get("SELECTED_DATA");
        endYear = h.get("END_YEAR").toString();
        endMonth = h.get("END_MONTH").toString();
        beginYear = h.get("BEGIN_YEAR").toString();
        beginMonth = h.get("BEGIN_MONTH").toString();
        vendor = (String)h.get("VENDOR_DESC");

        hubWaste = (String)h.get("HUB_WASTE");

        //vendor = (String)h.get("VENDOR");
        type = (String)h.get("TYPE");
        profile = (String)h.get("PROFILE");
        //4-28-01
        String myProfileId = (String)h.get("PROFILE_ID");
        String myMgmt =  (String)h.get("MGMT_OPTION_DESC");
        String myMgmtId =  (String)h.get("MGMT_OPTION");

        application = h.get("WORK_AREA").toString();
        facilityID = h.get("FACILITY_ID").toString();

        method = (String)h.get("METHOD");
        accumept = (String)h.get("ACCUME_PT");
        reportFields = (Vector)h.get("REPORT_FIELDS");

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
            String query = "insert into waste_template ";
            query = query + "(personnel_id,template,exclude_waste,type,profile,facility_id,application,vendor,accountpt,";
            query += "report_fields,method,begin_month,begin_year,end_month,end_year,profile_id,management_opt_desc,management_opt) ";
            query += "values ("+userID+",'"+template+"','"+hubWaste+"','"+type+"','"+profile+"','"+facilityID+"','"+application+"','"+vendor+"'";
            query += ",'"+accumept+"','"+myReportFields+"','"+method+"'";
            query += ",'"+beginMonth+"','"+beginYear+"','"+endMonth+"','"+endYear+"','"+myProfileId+"','"+myMgmt+"','"+myMgmtId+"')";

            //System.out.println("\n-------- insert query: "+query);
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
        String query = "delete from waste_template where personnel_id = "+userID;
        query += " and template = '"+templateName+"'";
        try
        {
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
        String query = "select count(*) from waste_template where personnel_id = "+userID;
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
        Vector v = new Vector();
        String query = "select template from waste_template";
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

    public Hashtable getWasteTemplateInfo(Hashtable inData) throws Exception
    {
        Hashtable result = new Hashtable();
        /*
        Vector baseField = new Vector();
        baseField.addElement("Facility");
        baseField.addElement("Work Area");
        baseField.addElement("Accumulation Point");
        baseField.addElement("Vendor");
        baseField.addElement("Profile");
        baseField.addElement("Profile Description");
        baseField.addElement("Packaging");
        baseField.addElement("Management Option");
        baseField.addElement("Month");
        baseField.addElement("Weight (lbs)");
        baseField.addElement("Cost");
        baseField.addElement("CAS");
        baseField.addElement("Chemical Name");
        */
        Vector baseField = getBaseField();

        Hashtable facAppVendorInfo = getFacAppVendorInfo((Integer)inData.get("USER_ID"));      //4-25-01 add a input val
        //Hashtable categoryH = getCategory();

        result.put("BASE_FIELDS",baseField);
        result.put("FAC_APP_VENDOR_INFO",facAppVendorInfo);
        return result;
    }

    public Vector getBaseField() throws Exception {
      Vector v = new Vector();
      String query = "select base_field from base_field where report_type = 'AdHocWaste' order by display_order";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          v.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("base_field")));
        }
      } catch (Exception e) {
        e.printStackTrace(); throw e;
      } finally {
        dbrs.close();
      }
      return v;
    }

    public Hashtable getFacAppVendorInfo(Integer uid) throws Exception
    {
        Hashtable facAppVendor = new Hashtable();
        String query = "select * from waste_fac_app_act_vendor_view order by facility_id,application";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector facIDV = new Vector();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            String lastFacId = "";
            while (rs.next())
            {
                String facId = rs.getString("facility_id");
                if (!lastFacId.equals(facId))
                {
                    facIDV.addElement(facId);
                }

                if (facAppVendor.containsKey(facId))
                {
                    Hashtable h = (Hashtable)facAppVendor.get(facId);
                    Hashtable innerH = (Hashtable)h.get("INNER_H");
                    Vector appId = (Vector)h.get("WORK_AREA_ID");
                    Vector appDesc = (Vector)h.get("WORK_AREA_DESC");
                    String application = (String)rs.getString("application");
                    if (!appId.contains(application)) {                           //5-09-01
                      appId.addElement(application);
                      appDesc.addElement(rs.getString("application_desc"));
                    }
                    if (innerH.containsKey(application))
                    {
                        Vector generationPt = (Vector)innerH.get(application);
                        String genpt = rs.getString("waste_location_id");
                        if (!generationPt.contains(genpt)) {                       //5-09-01
                          generationPt.addElement(genpt);
                        }
                    }
                    else
                    {
                        Vector generationPt = new Vector();
                        generationPt.addElement(rs.getString("waste_location_id"));
                        innerH.put(application,generationPt);
                    }
                    String vendor = rs.getString("vendor_id");
                    Vector vendorId = (Vector)h.get("VENDOR_ID");
                    Vector vendorDesc = (Vector)h.get("VENDOR_DESC");
                    if (!vendorId.contains(vendor))
                    {
                        vendorId.addElement(vendor);
                        vendorDesc.addElement(rs.getString("company_name"));
                    }
                    h.put("WORK_AREA_ID",appId);
                    h.put("WORK_AREA_DESC",appDesc);
                    h.put("VENDOR_ID",vendorId);
                    h.put("VENDOR_DESC",vendorDesc);
                    h.put("INNER_H",innerH);
                    facAppVendor.put(facId,h);

                }
                else
                {
                    Hashtable h = new Hashtable();
                    Hashtable innerH = new Hashtable();
                    Vector appId = new Vector();
                    String application = (String)rs.getString("application");
                    appId.addElement(application);
                    Vector appDesc = new Vector();
                    appDesc.addElement(rs.getString("application_desc"));
                    Vector generationPt = new Vector();
                    generationPt.addElement(rs.getString("waste_location_id"));
                    innerH.put(application,generationPt);
                    Vector vendorId = new Vector();
                    vendorId.addElement(rs.getString("vendor_id"));
                    Vector vendorDesc = new Vector();
                    vendorDesc.addElement(rs.getString("company_name"));
                    h.put("WORK_AREA_ID",appId);
                    h.put("WORK_AREA_DESC",appDesc);
                    h.put("VENDOR_ID",vendorId);
                    h.put("VENDOR_DESC",vendorDesc);
                    h.put("INNER_H",innerH);
                    facAppVendor.put(facId,h);
                }
                lastFacId = facId;
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

        facAppVendor.put("FACILITY_ID",facIDV);
        return facAppVendor;
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

    public Hashtable loadTemplateInfo(Hashtable inData) throws Exception
    {
        Integer userID = (Integer)inData.get("USER_ID");
        String template = (String)inData.get("TEMPLATE");
        String query = "select * from waste_template where personnel_id = '"+userID+"'";
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
                result.put("HUB_WASTE",
                           BothHelpObjs.makeBlankFromNull(rs.getString("EXCLUDE_WASTE")));
                result.put("TYPE",
                           BothHelpObjs.makeBlankFromNull(rs.getString("TYPE")));
                result.put("PROFILE",
                           BothHelpObjs.makeBlankFromNull(rs.getString("PROFILE")));
                result.put("PROFILE_ID",
                           BothHelpObjs.makeBlankFromNull(rs.getString("PROFILE_ID")));
                result.put("VENDOR",
                           BothHelpObjs.makeBlankFromNull(rs.getString("VENDOR")));
                result.put("FACILITY_ID",
                           BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID")));
                result.put("WORK_AREA",
                           BothHelpObjs.makeBlankFromNull(rs.getString("application")));
                result.put("ACCUME_PT",
                           BothHelpObjs.makeBlankFromNull(rs.getString("ACCOUNTPT")));
                result.put("MGMT_OPTION_ID",
                           BothHelpObjs.makeBlankFromNull(rs.getString("MANAGEMENT_OPT")));
                result.put("MGMT_OPTION",
                           BothHelpObjs.makeBlankFromNull(rs.getString("MANAGEMENT_OPT_DESC")));
                result.put("METHOD",
                           BothHelpObjs.makeBlankFromNull(rs.getString("method")));
                result.put("BEGIN_MONTH",
                           BothHelpObjs.makeBlankFromNull(rs.getString("begin_month")));
                result.put("BEGIN_YEAR",
                           BothHelpObjs.makeBlankFromNull(rs.getString("begin_year")));
                result.put("END_MONTH",
                           BothHelpObjs.makeBlankFromNull(rs.getString("end_month")));
                result.put("END_YEAR",
                           BothHelpObjs.makeBlankFromNull(rs.getString("end_year")));
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
                    result.put("REPORT_FIELDS", v);       //send back an empty vector
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
        return result;
    }
}
