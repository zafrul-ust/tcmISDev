package com.tcmis.internal.currency.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import com.tcmis.internal.currency.process.*;
import java.io.*;

public class RunCurrencyLoadServlet extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html";
    
    public void init() throws ServletException {
    }
    
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        final Date date = new Date();
        String message = "Currency load ran fine at: ".concat(String.valueOf(String.valueOf(date)));
        try {
            final CurrencyLoadProcess process = new CurrencyLoadProcess("BATCH");
            process.load();
        }
        catch (Exception e) {
            message = "There was an error loading currency data";
            System.out.println("THERE WAS AN ERROR LOADING CURRENCY DATA AT:".concat(String.valueOf(String.valueOf(date))));
            System.out.println("ERROR:".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>RunCurrencyLoadServlet</title></head>");
        out.println("<body bgcolor=\"#ffff00\">");
        out.println(String.valueOf(String.valueOf(((StringBuffer)new StringBuffer("<p>").append(message)).append("</p>"))));
        out.println("</body></html>");
    }
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        this.doGet(request, response);
    }
    
    public void destroy() {
    }
    
//    static {
//        CONTENT_TYPE = "text/html";
//    }
}
