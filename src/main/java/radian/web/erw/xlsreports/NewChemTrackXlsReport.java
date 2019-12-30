package radian.web.erw.xlsreports;

import java.sql.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.*;

public class NewChemTrackXlsReport
{
 public NewChemTrackXlsReport()
    {

    }
    TcmISDBModel db = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;

    public StringBuffer createXls(String query, String client) throws Exception
    {
        db = new RayTcmISDBModel(client);

        StringBuffer Msgcx = new StringBuffer();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
             Msgcx.append("<TR>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_ID")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("NAME")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_DATE")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_STATUS")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FACILITY_ID")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MATERIAL_DESC")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MANUFACTURER")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PACKAGING")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PART_ID")));Msgcx.append("</TD>\n");
             Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_CATALOG_ID")));Msgcx.append("</TD>\n");
             Msgcx.append("</TR>\n");
             }
        }
        catch (Exception e)
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

}
