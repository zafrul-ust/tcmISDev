// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   NewPinger.java

package radian.web.servlets.share;

import java.io.PrintWriter;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

// Referenced classes of package radian.web.servlets.share:
//            NewPinger_Thread

public class NewPinger
{

    public NewPinger(TcmISDBModel db, String user, PrintWriter out)
    {
        this.db = null;
        test = null;
        this.user = null;
        this.out = null;
        count = 0;
        error = false;
        this.db = db;
        this.user = user;
        this.out = out;
    }

    public boolean checkDB()
    {
        test = null;
        NewPinger_Thread npt = new NewPinger_Thread(out);
        npt.start();
        try
        {
            if(user != null)
                test = new String(getNextName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            boolean flag = false;
            return flag;
        }
        if(test.startsWith("Generic"))
        {
            out.println("<html>\n");
            out.println("<head><title>This Pinger</title></head>\n");
            out.println("<body bgcolor=white text=000000>\n");
            out.println("<h2>Name:" + test + "</h2>");
            out.println("</body>\n");
            out.println("</html>\n");
            return true;
        } else
        {
            out.println("<html>\n");
            out.println("<head><title>This Pinger</title></head>\n");
            out.println("<body bgcolor=white text=000000>\n");
            out.println("Error\n");
            out.println("</html>\n");
            return false;
        }
    }

    protected String getNextName()
        throws Exception
    {
        Personnel PR = new Personnel(db);
        PR.setPersonnelId(Integer.parseInt(user));
        PR.load();
        return PR.getFullName();
    }

    public String getServletInfo()
    {
        return "radian.tcmis.server.share.servlets.NewPinger Information";
    }

    public void setError(boolean e)
    {
        error = e;
    }

    TcmISDBModel db;
    String test;
    String user;
    PrintWriter out;
    int count;
    boolean error;
}
