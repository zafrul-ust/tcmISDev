// FrontEnd Plus GUI for JAD
// DeCompiled : RayhelpMenu.class

package radian.web.servlets.help;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import radian.web.servlets.seagate.SeagateServerResourceBundle;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
// Referenced classes of package radian.web.servlets.help:
//            helpMenu

public class tcmIShelpMenu extends HttpServlet
{

    public tcmIShelpMenu()
    {
    }

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        //TcmISDBModel db = null;
        try
        {
            /*db = new RayTcmISDBModel("Ray");
            if(db == null)
            {
                PrintWriter out2 = new PrintWriter(response.getOutputStream());
                out2.println("Starting DB");
                out2.println("DB is null");
                out2.close();
                return;
            }*/
            ALONEhelpMenu obj = new ALONEhelpMenu((ServerResourceBundle) new SeagateServerResourceBundle());
            obj.doPost(request, response);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            //db.close();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doPost(request, response);
    }
}
