// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   testEmailSystem.java

package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.NoBuyOrderPo;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class testEmailSystem extends HttpServlet
    implements SingleThreadModel
{

    public testEmailSystem()
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
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        RayTcmISDBModel db = null;
        try
        {
            db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
            if(db == null)
            {
                out.println("Starting DB");
                out.println("DB is null");
                out.close();
                HelpObjs.sendMail(db, "AutoProcessNoBuyOrder", "Unable to get a DB connection", 0x1500e, false);
                return;
            }
            NoBuyOrderPo noBuyOrderPo = new NoBuyOrderPo();
            noBuyOrderPo.autoProcessRequest(db);
        }
        catch(Throwable e)
        {
            e.printStackTrace();
            HelpObjs.sendMail(db, "JRun Scheduler", "Error occurred while scheduler processing NBO request ", 0x1500e, false);
        }
        finally
        {
            db.close();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doPost(request, response);
    }
}
