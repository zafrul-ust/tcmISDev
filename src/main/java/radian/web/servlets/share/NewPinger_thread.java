// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewPinger.java

package radian.web.servlets.share;

import java.io.PrintWriter;

class NewPinger_Thread extends Thread
{

    public NewPinger_Thread(PrintWriter o)
    {
        out = null;
        out = o;
    }

    public void run()
    {
        try
        {
            Thread.currentThread();
            Thread.sleep(20000L);
        }
        catch(Exception exception) { }
        try
        {
            out.println("<html>\n");
            out.println("<head><title>This Pinger</title></head>\n");
            out.println("<body bgcolor=white text=000000>\n");
            out.println("Error\n");
            out.println("</html>\n");
            out.close();
        }
        catch(Exception exception1) { }
    }

    protected PrintWriter out;
}
