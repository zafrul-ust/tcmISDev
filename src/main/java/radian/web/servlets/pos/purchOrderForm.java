package radian.web.servlets.pos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.actuate.ereport.common.IViewerInterface;
//ACJEngine
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.BothHelpObjs;

public class purchOrderForm extends HttpServlet
{
    ACJEngine erw = null;
    TemplateManager tm = null;
    ACJOutputProcessor ec = null;

    String item_id = "";
    String print = "";
    String po = "";
    String hub = "";
    String sl = "";
    String payment_terms = "";
    String shipper_name = "";
    String po_notes = "";
    String company = "";
    String bill_to_address1 = "";
    String bill_to_address2 = "";
    String bill_to_address3 = "";
    String buyer = "";
    String branch_plant = "";
    String bill1 = "";
    String bill2 = "";
    String buyer_name = "";
    String byrsigref = "";
    String radian_po = "";
    String msg  = "";
    String client_acct  = "";
    String sched_pick  = "";
    String order_taken_by = "";
    String shipto_company= "";
    String shipto_address1= "";
    String shipto_city= "";
    String shipto_state= "";
    String shipto_zip= "";
    String vendor_name= "";
    String vendor_address1= "";
    String vendor_city= "";
    String vendor_state= "";
    String vendor_zipcode= "";
    String buyer_phone = "";
    String add_note = "";
    String Order_Total = "";
    String errormsg = "";
    String errormsgH = "";
    String Msds_dates = "";
    int index = 0;

    double order_total = 0.00;
    String temp_desc_1 = "";
    String temp_desc_2 = "";

    //NumberFormat formatter = NumberFormat.getCurrencyInstance();
    //DateFormat dateFormatter;

    //Local
    /*String tempPath = new String ("D:\\");
    String logo = new String ("http://localhost/tcmIS/logo_s.gif");
    String signaturePath = new String ("D:/Projects/java/radian/web/servlets/pos");
    String myURL = new String ("http://localhost/tmpPO/");*/

    //Cardnial
    /*String tempPath = new String ("/export2/faxtemp0/");
    String logo = new String ("/webdata/html/images/logo_s.gif");
    String signaturePath = new String ("/webdata/html/images/");
    String myURL = new String ("http://www.tcmis.com/tcmIS/faxfiles/");*/

    //Eagle
    String tempPath = new String ("/webdata/html/tmpPO/");
    String logo = new String ("/webdata/html/images/logo_s.gif");
    //String signature = new String ("/webdata/html/images/"+byrsig+"_.gif");
    String myURL = new String ("http://eagle.tcmis.com/tmpPO/");

   //Condor
   /*String tempPath = new String ("/home/httpd/htdocs/tmpPO/");
   String logo = new String ("/home/servlet/radian/web/servlets/pos/logo_s.gif");
   String signaturePath = new String ("/home/servlet/radian/web/servlets/pos/");
   String myURL = new String ("http://eagle.tcmis.com/tmpPO/");*/

    String signature;

    PrintWriter out;

    public purchOrderForm()throws ServletException
    {
        //  out.println("--------- initializing");
    }

    //Process the HTTP Get request

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request,response);

    }

    //Process the HTTP Post request

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
        IOException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        erw = new ACJEngine();

        erw.setX11GfxAvailibility(false);

        /*Globals.setPathForFontSettings("/usr/jrun/servers/default/default-app/WEB-INF/classes/ACJFont.properties");*/

        ec = new ACJOutputProcessor();
        PrintWriter out = response.getWriter();

        msg = "";
        errormsg = "";

        errormsgH = "<HTML><HEAD><TITLE>ERROR OCCURED</TITLE></HEAD>";
        errormsgH +="<BODY bgcolor=\"#00CCFF\" text=\"#000000\" link=\"#0000FF\" vlink= \"#FF00FF\"><FONT FACE=\"Arial\"><CENTER>\n";

        buildPurchOrderForm(request,response);

        if (errormsg.length() > 2)
        {
         errormsg +="<P>Go Back and Please Try Again </P>";
         errormsg +="<P>Thanks.</P></FONT></CENTER></BODY></HTML>";
         errormsgH += errormsg;
         out.println(errormsgH);
         out.close();
         return;
        }
        else
        {
         out.println(msg);
         out.close();
        }

        System.out.println("This is the last thing Iam suposed to do.     "+po);
        return;
    }

    public void buildPurchOrderForm(HttpServletRequest request,
                                    HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
        print = BothHelpObjs.makeBlankFromNull(request.getParameter("print"));
        po = BothHelpObjs.makeBlankFromNull(request.getParameter("po"));
        hub = BothHelpObjs.makeBlankFromNull(request.getParameter("hub"));
        sl = BothHelpObjs.makeBlankFromNull(request.getParameter("sl"));
        payment_terms = BothHelpObjs.makeBlankFromNull(request.getParameter("payment_terms"));
        shipper_name = BothHelpObjs.makeBlankFromNull(request.getParameter("shipper_name"));
        po_notes = BothHelpObjs.makeBlankFromNull(request.getParameter("po_notes"));
        company = BothHelpObjs.makeBlankFromNull(request.getParameter("company"));
        bill_to_address1 = BothHelpObjs.makeBlankFromNull(request.getParameter("bill_to_address1"));
        bill_to_address2 = BothHelpObjs.makeBlankFromNull(request.getParameter("bill_to_address2"));
        bill_to_address3 = BothHelpObjs.makeBlankFromNull(request.getParameter("bill_to_address3"));
        buyer = BothHelpObjs.makeBlankFromNull(request.getParameter("buyer"));
        buyer_name = BothHelpObjs.makeBlankFromNull(request.getParameter("buyer_name"));
        byrsigref = BothHelpObjs.makeBlankFromNull(request.getParameter("byrsigref"));
        buyer_phone = BothHelpObjs.makeBlankFromNull(request.getParameter("buyer_phone"));
        branch_plant = BothHelpObjs.makeBlankFromNull(request.getParameter("branch_plant"));
        bill1 = BothHelpObjs.makeBlankFromNull(request.getParameter("bill1"));
        bill2 = BothHelpObjs.makeBlankFromNull(request.getParameter("bill2"));
        item_id = BothHelpObjs.makeBlankFromNull(request.getParameter("item_id"));
        sched_pick = BothHelpObjs.makeBlankFromNull(request.getParameter("sched_pick"));
        order_taken_by  = BothHelpObjs.makeBlankFromNull(request.getParameter("order_taken_by"));
        client_acct  = BothHelpObjs.makeBlankFromNull(request.getParameter("client_acct"));
        shipto_company  = BothHelpObjs.makeBlankFromNull(request.getParameter("shipto_company"));
        shipto_address1  = BothHelpObjs.makeBlankFromNull(request.getParameter("shipto_address1"));
        shipto_city  = BothHelpObjs.makeBlankFromNull(request.getParameter("shipto_city"));
        shipto_state  = BothHelpObjs.makeBlankFromNull(request.getParameter("shipto_state"));
        shipto_zip  = BothHelpObjs.makeBlankFromNull(request.getParameter("shipto_zip"));
        vendor_name  = BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_name"));
        vendor_address1  = BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_address1"));
        vendor_city  = BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_city"));
        vendor_state  = BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_state"));
        vendor_zipcode  = BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_zipcode"));
        add_note = BothHelpObjs.makeBlankFromNull(request.getParameter("add_note"));
        Order_Total = BothHelpObjs.makeBlankFromNull(request.getParameter("Order_Total"));
        Msds_dates = BothHelpObjs.makeBlankFromNull(request.getParameter("MSDS_dates"));

        }
        catch (Exception e)

        {
         errormsg +="<P>Error In  Getting Values from \"form\": </P>";
         e.printStackTrace();
         return;
        }

        signature = new String (byrsigref);
        String template = "";
        System.out.println("Setting signature: " + signature + "Branch"+branch_plant+"   "+Msds_dates+"\n");

        if (branch_plant.trim().equalsIgnoreCase("2202"))
        {
        //template = new String ("D:\\purchase_order_template_SWA.erw");
        template = new String ("/usr/jrun/servers/default/default-app/WEB-INF/classes/radian/web/servlets/pos/purchase_order_template_SWA.erw");
        //template = new String ("/home/servlet/radian/web/servlets/pos/purchase_order_template_SWA.erw");
        }
        else if (shipper_name.trim().equalsIgnoreCase("Pre Pay and Add"))
        {
        //template = new String ("D:\\purchase_order_template_PREPAY.erw");
        template = new String ("/usr/jrun/servers/default/default-app/WEB-INF/classes/radian/web/servlets/pos/purchase_order_template_PREPAY.erw");
        //template = new String ("/home/servlet/radian/web/servlets/pos/purchase_order_template_PREPAY.erw");
        }
        else
        {
        //template = new String ("D:\\purchase_order_template_modified.erw");
        template = new String ("/var/jrun/servers/default/tcmIS/WEB-INF/lib/purchase_order_template.erw");
        //template = new String ("/home/servlet/radian/web/servlets/pos/purchase_order_template.erw");
        }
        try
        {
            erw.readTemplate(template);
            //Query the result directly from database
        }
        catch(Exception ex)
        {
          errormsg +="<P><B>Error!!</B></P>";
          errormsg +="<P>ERROR LOADING PURCHASE ORDER TEMPLATE</P>";
          ex.printStackTrace();
          return;
        }

        //ReportData = getdata();
        String where01 = null;
        where01 = "radian_po = " + po;
        String where02 = null;
        //where02 ="PURCHASE_INFO_DESC_VIEW.RADIAN_PO = PURCHASE_ORDER_JDE_SNAPSHOT.RADIAN_PO(+) ";
        //where02 +="AND PURCHASE_INFO_DESC_VIEW.ITEM_ID = PURCHASE_ORDER_JDE_SNAPSHOT.ITEM_ID(+) ";
//      where02 +="AND PURCHASE_INFO_DATA_VIEW.ORDER_QTY = PURCHASE_ORDER_JDE_SNAPSHOT.ORDER_QTY(+) ";
//where02 +="AND PURCHASE_INFO_DESC_VIEW.ITEM_ID != 311546 AND PURCHASE_INFO_DESC_VIEW.RADIAN_PO = "+po+" AND ";
        where02 =" PURCHASE_INFO_DESC_VIEW.RADIAN_PO = "+po+" AND ";
        where02 +="PURCHASE_INFO_DESC_VIEW.TOTAL_PRICE_PAID > 0.1";
       // where02 +="PURCHASE_INFO_DESC_VIEW.BUYER = '"+buyer+"' AND PURCHASE_INFO_DESC_VIEW.TOTAL_PRICE_PAID > 0.1";

        System.out.println ("Defining the where clauses.\n"+where02);

        String Property = System.getProperties().getProperty("java.class.path").toString();
        System.out.println (Property);

        String where03 = null;
        where03 = "radian_po ="+po;
        String where04 = null;
        where04 = "radian_po ="+po;
        String where05 = null;
        where05  = "hub ="+branch_plant+" and shipper_name='"+shipper_name+"'";
        String where07 = null;
        where07 = "radian_po ="+po;

        String where08 = null;
        where08 = "radian_po ="+po;

        System.out.println("Passing variables and setting the where clauses.\n");

        try
        {
        tm = erw.getTemplateManager();

        tm.setLabel("IMA001", logo);
        tm.setLabel("IMA000", signature);
        tm.setLabel("LAB037", buyer_name);
        tm.setLabel("LAB038", payment_terms);
        tm.setLabel("LAB039", sched_pick);
        tm.setLabel("LAB041", sl);


        tm.setLabel("LAB006", shipper_name);
        tm.setLabel("LAB047", order_taken_by);
        tm.setLabel("LAB048", client_acct);
        tm.setLabel("LAB049", add_note);
        tm.setLabel("LAB051", po);
        tm.setLabel("PURCAHSE_ORDER_NO",po);
        tm.setLabel("LAB052", vendor_name);
        tm.setLabel("LAB053", vendor_address1);
        tm.setLabel("LAB054", vendor_city);
        tm.setLabel("LAB055", vendor_state);
        tm.setLabel("LAB056", vendor_zipcode);
        tm.setLabel("LAB057", shipto_company);
        tm.setLabel("LAB058", shipto_address1);
        tm.setLabel("LAB059", shipto_city);
        tm.setLabel("LAB060", shipto_state);
        tm.setLabel("LAB061", shipto_zip);
        tm.setLabel("FORSUM", Order_Total);

        tm.setLabel("MSDS_DATE_TEST", Msds_dates);

        }
        catch(Exception ex)
        {
          errormsg +="<P><B>Error!!</B></P>";
          errormsg +="<P>ERROR LOADING PURCHASE ORDER TEMPLATE</P>";
          ex.printStackTrace();
          return;
        }

        System.out.println("Setting shipping data.\n");

        try
        {
        if (company.startsWith("Raytheon"))
        {
            tm.setLabel("LAB033", bill1);
            tm.setLabel("LAB042", company);
            tm.setLabel("LAB043", bill_to_address1);
            tm.setLabel("LAB044", bill_to_address2);
            tm.setLabel("LAB045", bill_to_address3);
            tm.setLabel("LAB034", bill2);
        }
        tm.setWHEREClause("SEC_01",where01);
        tm.setWHEREClause("SEC_02",where02);
        tm.setWHEREClause("SEC_03",where03);
        tm.setWHEREClause("SEC_05",where04);
        tm.setWHEREClause("SEC_04",where04);
        //tm.setWHEREClause("SEC_05",where05);
        tm.setWHEREClause("SEC_08",where08);

        double width;
        //    width = 11.0;
        width = 8.5;
        tm.setPageWidth(width);

        double height;
        //    height = 8.5;
        height = 11.0;
        tm.setPageHeight(height);
        }
        catch(Exception ex)
        {
            errormsg +="<P><B>Error!!</B></P>";
            errormsg +="<P>During Modify Template</P>";
            ex.printStackTrace();
            return;
        }
        System.out.println("Genereating IViewerInterface");
        try
        {
            IViewerInterface ivi = erw.generateReport();
            //IViewerInterface ivi = erw.getCommunicationStub();
            System.out.println("Done Genereating Report");
            if (!ec.setReportData(ivi,null)) System.exit(0);
            System.out.println("Done Setting Re[prt Data");
        }
        catch(Exception ex)
        {
            errormsg +="<P><B>Error!!</B></P>";
            errormsg +="<P>During Generating Report</P>";
            ex.printStackTrace();
            return;
        }
        try
        {
            ec.generatePDF(tempPath+"po_"+po+".pdf",null);
        }
        catch (Exception e)
        {
           errormsg +="<P><B>Error!!</B></P>";
           errormsg +="<P>During Generating PDF Report</P>";
           e.printStackTrace();
           return;
        }
        System.out.println("got here done");
        response.setContentType("text/html");
        try
        {
            System.out.println("Purchase order pdf file- Redirect.   "+po+"    "+item_id+"    \n"+Order_Total+"  \n");
            msg +="<HTML><HEAD><TITLE>Fax Po PDF File</TITLE>";
            msg +="<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0;url="+myURL+"po_"+po+".pdf\"></HEAD>";
            msg +="<BODY>   </BODY></HTML>";
            return;
        }
        catch(Exception io)
        {
            System.out.println("++++++++ ERRROR can't open pdf at url = "+myURL+"po_"+ po+".pdf\">\n");
            errormsg +="<P><B>Error!!</B></P>";
            errormsg +="<P>During Generating HTML for Redirection</P>";
            io.printStackTrace();
            return;
        }
    }
}





















/*    public RegisterTable[] getDataV(Vector reportData1)
    {
        RegisterTable[] rt = new RegisterTable[1];
        try
        {
            rt[0] = new RegisterTable(PoData.getVector(reportData1),
                                      "PURCHASE_INFO_DATA_REPORT",
                                      PoData.getFieldVector(),null);
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }
        return rt;
    }
    public Vector getdata()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod","tcm_ops","n0s3ha1r");
            Statement statement = connection.createStatement();
            String stmt = "";
            stmt = "select distinct a.item_id, a.item_desc, a.order_qty, a.order_uom, ";
            stmt += "a.unit_price_paid, a.total_price_paid, ";
            stmt += "to_char(b.sched_pick,'mm/dd/yyyy') sched_pick ";
            stmt += "from purchase_info_data_view a, purchase_order_jde_snapshot b ";
            stmt += " where a.radian_po = b.radian_po(+) ";
            stmt += " and a.item_id = b.item_id(+) and a.order_qty = b.order_qty(+) ";
            stmt += " and a.radian_po ='"+po+"'" ;
            stmt += " order by item_id";
            System.out.println ("stmt:" + stmt);
            ResultSet rs = statement.executeQuery(stmt);
            String item_ids = "";
            while (rs.next())
            {
                Hashtable h = new Hashtable();
                item_ids = rs.getString(1).toString();
                if (item_ids.equalsIgnoreCase("311546"))
                {
                 System.out.println ("It came Across a Shipment charge");
                }
                else
                {
                    h.put("ITEM_ID",
                          (BothHelpObjs.makeBlankFromNull(rs.getString(1))));
                    temp_desc_1 = (BothHelpObjs.makeBlankFromNull(rs.getString(2))).toString();

                    try
                    {
                        index = temp_desc_1.indexOf("&");
                        if (index > -1)
                        {
                            temp_desc_2 = temp_desc_1.substring(0,
                                                                temp_desc_1.indexOf("&"));
                            temp_desc_2 += "&amp;";
                            temp_desc_2 += temp_desc_1.substring(temp_desc_1.indexOf("&")+1);
                        }
                        else
                        {
                            temp_desc_2 = temp_desc_1;
                        }
                    }
                    catch(NullPointerException npe)
                    {
                        System.out.println("\n<p>\nError finding item description: ");
                        System.out.println(temp_desc_1.indexOf("&"));
                        System.out.println("\n");
                    }
                    h.put("ITEM_DESC",temp_desc_2);
                    h.put("ORDER_QTY",
                          BothHelpObjs.makeBlankFromNull(rs.getString(3)));
                    h.put("ORDER_UOM",
                          BothHelpObjs.makeBlankFromNull(rs.getString(4)));
                    h.put("UNIT_PRICE_PAID",
                          (formatter.format(Double.valueOf(BothHelpObjs.makeBlankFromNull(rs.getString(5))).doubleValue())));
                    h.put("TOTAL_PRICE_PAID",
                          (formatter.format(Double.valueOf(BothHelpObjs.makeBlankFromNull(rs.getString(6))).doubleValue())));
                    h.put("SHEDULDE_PICKUP_DATE",
                          BothHelpObjs.makeBlankFromNull(rs.getString(7)));

                   stmt = "select FLOW_DOWN_DESC, CONTENT from FLOW_DOWN_VIEW ";
                   stmt += "where item_id ='"+item_ids+"'";
                   System.out.println ("stmt:" + stmt);
                   ResultSet rs1 = statement.executeQuery(stmt);

                   while (rs1.next())
                   {
                   h.put("FLOW_DOWN_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("FLOW_DOWN_DESC")));
                   h.put("CONTENT",BothHelpObjs.makeBlankFromNull(rs.getString("CONTENT")));
                   }
                   rs1.close();

                    //order_total += Double.valueOf(BothHelpObjs.makeBlankFromNull(rs.getString(6))).doubleValue();
                    ReportData.addElement(h);
                }
            }
            System.out.println("item_Id" + item_id + "\n");
            rs.close();
        }
        catch(NumberFormatException nfe)
        {
            out.print("\n<p>\nError in number: ");
            out.print(String.valueOf(nfe));
            out.print(" \n");
        }
        catch(ClassNotFoundException cnfe)
        {
            out.print("\n<p>\nError loading driver: ");
            out.print(String.valueOf(cnfe));
            out.print(" \n");
        }
        catch(SQLException sqle)
        {
            out.println("\n<p>\nError connecting: ");
            out.println(String.valueOf(sqle));
            out.println("\n");
        }
        return ReportData;
    }
*/
