package radian.web.erw.xlsreports;

import java.sql.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.client.ray.dbObjs.*;
import radian.tcmis.server7.share.db.*;

public class UserProfileXlsReport
{
public UserProfileXlsReport()
    {

    }
    TcmISDBModel db = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;

    public StringBuffer createXls(String personnel_id, String client, String browser) throws Exception
    {
        db = new RayTcmISDBModel(client);
        String query1 = "Select * from PERSONNEL where personnel_id = "+personnel_id;

        String query2 = "Select * from FACILITY_USER_GROUP_VIEW where personnel_id = "+personnel_id;

        StringBuffer Msgcx = new StringBuffer();
        Msgcx.append("<TABLE BORDER=\"0\">\n");
        try
        {
          try
          {
              dbrs = db.doQuery(query1);
              rs=dbrs.getResultSet();
              while (rs.next())
              {
               Msgcx.append("<TR>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">Name: ");
               Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("LAST_NAME")));
               Msgcx.append(",");
               Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FIRST_NAME")));
               Msgcx.append("</TD>\n");

               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Login Name: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("LOGON_ID")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Login ID: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PERSONNEL_ID")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Phone: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PHONE")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Phone(2nd): ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("ALT_PHONE")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Pager: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PAGER")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Fax: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FAX")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Shipping Location: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("SHIPPING_LOCATION")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Mailing Location: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MAIL_LOCATION")));Msgcx.append("</TD></TR>\n");
               //Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Browser: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(browser));Msgcx.append("</TD></TR>\n");
               //Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Proxy:");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PROXY_NAME")));Msgcx.append("</TD></TR>\n");
               //Msgcx.append("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\">Port: ");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PROXY_PORT")));Msgcx.append("</TD></TR>\n");
               Msgcx.append("</TR>\n");
              }
          }
          catch (Exception e1)
          {
              e1.printStackTrace();
              throw e1;
          }
          finally
          {
              dbrs.close();
          }

        Msgcx.append("<TR><TD>&nbsp;</TD></TR>\n");
        Msgcx.append("<TR><TD>&nbsp;</TD></TR>\n");
        Msgcx.append("</TABLE>\n");

        Msgcx.append("<TABLE BORDER=\"1\">\n");
        Msgcx.append("<TR>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>FACILITY</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>PREFERRED</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>CREATE MR</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>APPROVER</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>AMOUNT</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>RELEASER</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>CREATE NEW CHEMICAL</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>ADMIN</B></TD>\n");
        Msgcx.append("<TD ALIGN=\"CENTER\"><B>WASTE MANAGER</B></TD>\n");
        Msgcx.append("</TR>\n");

          try
          {
              dbrs = db.doQuery(query2);
              rs=dbrs.getResultSet();
              while (rs.next())
              {
               Msgcx.append("<TR>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("FACILITY_ID")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PREFERRED_FACILITY")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("CREATE_MR")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("APPROVER")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("COST_LIMIT")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("RELEASER")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("CREATE_NEW_CHEM")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("ADMINISTRATOR")));Msgcx.append("</TD>\n");
               Msgcx.append("<TD ALIGN=\"LEFT\">");Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("WASTE_MANAGER")));Msgcx.append("</TD>\n");
               Msgcx.append("</TR>\n");
              }
          }
          catch (Exception e2)
          {
              e2.printStackTrace();
              throw e2;
          }
          finally
          {
              dbrs.close();
          }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            db.close();
        }

        return Msgcx;
    }

}
