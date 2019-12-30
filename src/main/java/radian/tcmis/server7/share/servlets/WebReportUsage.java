package radian.tcmis.server7.share.servlets;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class WebReportUsage
{
    ServerResourceBundle bundle;
    TcmISDBModel db;

    public WebReportUsage(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = null;
        db = null;
        bundle = b;
        db = d;
    }

    public StringBuffer createReport(String client,String facilityID, String application, String beginYear, String beginMonth, String endYear, String endMonth)
        throws Exception
    {
        StringBuffer Msgt = new StringBuffer();
        Hashtable Result = new Hashtable();

        String searchstring = new String("");
        String Groupby = new String(" group by ");
        String where = " where ";

        boolean speciated = false;
        boolean groupBy = false;

        searchstring += "material_id,trade_name,fac_part_id,trade_name,to_char(PART_SIZE)||' '||SIZE_UNIT||' '||PKG_STYLE Packaging,mfg_desc";

        if (facilityID.length()>0)
        {
            where += "a.facility='"+facilityID+"' and ";
        }

        if ((application.length()>0) && !(client.trim().equalsIgnoreCase("Southwest Airlines")))
        {
            where += "a.location='"+application+"' and ";
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
            if(em.intValue() >=12)
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

        where = where + "a.date_shipped >= to_date('"+begD.toString()+ "','YYYYMM') and a.date_shipped < to_date('"+endD.toString()+"','YYYYMM') ";
        if (where.length() > 0)
        {
            where = where.substring(0,where.length()-1);
        }

        String from = " from ";
        from += "report_ad_hoc_chem_view  a ";
        String QueryString = "select distinct " + searchstring + from + where;

        DBResultSet dbrs = null;
        ResultSet rs = null;
        int numRecs = 0 ;

        int numRecsTest = 0 ;
        Hashtable Result1 = new Hashtable();
        Hashtable Result2 = new Hashtable();
        Vector ResultV = new Vector();
        Vector ResultF = new Vector();
        String testfac = "";

        try
        {
            dbrs = db.doQuery(QueryString);
            rs=dbrs.getResultSet();
            String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");

            while(rs.next())
            {
                numRecs += 1;
                if (numRecsTest != 0)
                {
                  if (testfac.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_id").toString())))
                  {
                  //testfac = BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_id"));
                  Result2 = new Hashtable();
                  Result2.put("Material ID",BothHelpObjs.makeSpaceFromNull(rs.getString("material_id")));
                  Result2.put("Material IDURL",""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.ray.RaymsdsSideView?showspec=N&id="+BothHelpObjs.makeSpaceFromNull(rs.getString("material_id"))+"&facility="+facilityID+"");
                  Result2.put("Material Description",BothHelpObjs.makeSpaceFromNull(rs.getString("trade_name")));
                  ResultV.add(Result2);
                  numRecsTest += 1;
                  }
                  else
                  {
                  //testfac = BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_id"));
                  Result1.put("MATERIL_IDS",ResultV);
                  ResultF.add(Result1);

                  Result1 = new Hashtable();
                  Result2 = new Hashtable();
                  ResultV = new Vector();

                  testfac = BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_id"));

                  Result1.put("Part Number",BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_id")));
                  Result1.put("Description",BothHelpObjs.makeSpaceFromNull(rs.getString("trade_name")));
                  Result1.put("Packaging",BothHelpObjs.makeSpaceFromNull(rs.getString("Packaging")));
                  Result1.put("Manufacturer",BothHelpObjs.makeSpaceFromNull(rs.getString("mfg_desc")));
                  Result2.put("Material ID",BothHelpObjs.makeSpaceFromNull(rs.getString("material_id")));
                  Result2.put("Material IDURL",""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.ray.RaymsdsSideView?showspec=N&id="+BothHelpObjs.makeSpaceFromNull(rs.getString("material_id"))+"&facility="+facilityID+"");
                  Result2.put("Material Description",BothHelpObjs.makeSpaceFromNull(rs.getString("trade_name")));
                  ResultV.add(Result2);
                  numRecsTest += 1;
                  }
                }
                else
                {
                testfac = BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_id"));

                Result1.put("Part Number",BothHelpObjs.makeSpaceFromNull(rs.getString("fac_part_id")));
                Result1.put("Description",BothHelpObjs.makeSpaceFromNull(rs.getString("trade_name")));
                Result1.put("Packaging",BothHelpObjs.makeSpaceFromNull(rs.getString("Packaging")));
                Result1.put("Manufacturer",BothHelpObjs.makeSpaceFromNull(rs.getString("mfg_desc")));
                Result2.put("Material ID",BothHelpObjs.makeSpaceFromNull(rs.getString("material_id")));
                Result2.put("Material IDURL",""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.ray.RaymsdsSideView?showspec=N&id="+BothHelpObjs.makeSpaceFromNull(rs.getString("material_id"))+"&facility="+facilityID+"");
                Result2.put("Material Description",BothHelpObjs.makeSpaceFromNull(rs.getString("trade_name")));
                ResultV.add(Result2);

                numRecsTest += 1;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
          if (dbrs != null ) {dbrs.close();}
        }

         Hashtable Final = new Hashtable();
         Hashtable Final1 = new Hashtable();
         Vector ResultV1 = new Vector();

         if (numRecs != 0)
         {
		   try
		   {
			 for ( int j=0; j < ResultF.size(); j++ )
			 {
                String Color = " ";
                ResultV1 = new Vector();

                if (j%2==0)
                {
                    Color = "bgcolor=\"#dddddd\"";
                }
                else
                {
                   Color = "bgcolor=\"#fcfcfc\"";
                }
                Final = (Hashtable)ResultF.elementAt(j);
                ResultV1 = (Vector)Final.get("MATERIL_IDS");

                Msgt.append("<TR>\n");
                Msgt.append("<TD "+Color+" ALIGN=\"LEFT\" ROWSPAN="+ResultV1.size()+">");Msgt.append(Final.get("Part Number"));Msgt.append("</TD>\n");
                Msgt.append("<TD "+Color+" ALIGN=\"LEFT\" ROWSPAN="+ResultV1.size()+">");Msgt.append(Final.get("Description"));Msgt.append("</TD>\n");
                Msgt.append("<TD "+Color+" ALIGN=\"LEFT\" ROWSPAN="+ResultV1.size()+">");Msgt.append(Final.get("Packaging"));Msgt.append("</TD>\n");
                Msgt.append("<TD "+Color+" ALIGN=\"LEFT\" ROWSPAN="+ResultV1.size()+">");Msgt.append(Final.get("Manufacturer"));Msgt.append("</TD>\n");

                //System.out.println("The number of matIds is "+ResultV1.size());

                for(int i=0;i < ResultV1.size(); i++)
                {
                  Final1 = (Hashtable)ResultV1.elementAt(i);

                  if (i > 0)
                  {
                   Msgt.append("<TR>\n");
                  }
                  Msgt.append("<TD "+Color+" ALIGN=\"LEFT\">");Msgt.append("<A HREF=\""+Final1.get("Material IDURL")+"\" TARGET=\"NEW\">"+Final1.get("Material ID")+"</A>");Msgt.append("</TD>\n");
                  Msgt.append("<TD "+Color+" ALIGN=\"LEFT\">");Msgt.append(Final1.get("Material Description"));Msgt.append("</TD>\n");
                  Msgt.append("</TR>\n");
               }
			 }
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
            return Msgt;
        }
        }
        else
        {
         Msgt.append("<TR>\n");
         Msgt.append("<TD> <B>No Records Found</B> </TD>\n");
         Msgt.append("<TR>\n");
        }

        return Msgt;
    }
}