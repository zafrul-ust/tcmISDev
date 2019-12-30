// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   OrderTrackXlsReport.java

package radian.web.erw.xlsreports;

import java.util.*;
import java.sql.ResultSet;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

public class OrderTrackXlsReport
{

    public OrderTrackXlsReport()
    {
        db = null;
        dbrs = null;
        rs = null;
    }

    public StringBuffer createXlsNew(Vector dataV, String client) throws Exception{
      StringBuffer Msgcx = new StringBuffer();
      try{
          for(int i = 0; i < dataV.size(); i++ ) {
            HashMap h = (HashMap)dataV.elementAt(i);
            Msgcx.append("<TR>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_0"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_1"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_2"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_3"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_4"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_5"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_6"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_7"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_8"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_9"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_10"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_11"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_12"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_13"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_14"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_15"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_16"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_17"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_18"));
            Msgcx.append("</TD>\n");
            Msgcx.append("<TD ALIGN=\"LEFT\">");
            Msgcx.append(h.get("DETAIL_19"));
            Msgcx.append("</TD>\n");
            Msgcx.append("</TR>\n");
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }
        return Msgcx;
    }  //end of method

    public StringBuffer createXls(String query, String client)
        throws Exception
    {
        db = new RayTcmISDBModel(client);
        StringBuffer Msgcx = new StringBuffer();
        try
        {
            dbrs = db.doQuery(query);
            for(rs = dbrs.getResultSet(); rs.next(); Msgcx.append("</TR>\n"))
            {
                Msgcx.append("<TR>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("request_line_status"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("facility_name"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("application_desc"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("fac_part_no"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("part_description"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("item_type"));
                Msgcx.append("</TD>\n");
                String mrLine = rs.getString("pr_number")+"-"+rs.getString("line_item");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(mrLine);
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("notes")));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("release_date"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("required_datetime"));
                Msgcx.append("</TD>\n");
                int ts = rs.getInt("total_picked");
                int qty = rs.getInt("quantity");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(ts+" of "+qty);
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("total_shipped"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("last_shipped")));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("po_number"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("requestor_name"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("critical"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("catalog_desc"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("item_id"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(rs.getString("approver_name"));
                Msgcx.append("</TD>\n");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
            db.close();
        }
        return Msgcx;
    }

    TcmISDBModel db;
    DBResultSet dbrs;
    ResultSet rs;
}
