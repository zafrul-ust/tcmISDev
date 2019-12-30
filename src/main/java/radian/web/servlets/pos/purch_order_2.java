package radian.web.servlets.pos;

import java.io.*;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import javax.servlet.*;
import javax.servlet.http.*;
import radian.tcmis.both1.helpers.*;

public class purch_order_2 extends HttpServlet
{
     public purch_order_2()
    {

    }
    String date_requested = "";
    String po = "";
    String sl =  "";
    String po_notes =  "";
    String vendor="";
    String vendor_1 = "";
    String vendor_2 = "";
    String vendor_name =  "";
    String vendor_address1 =  "";
    String vendor_address2 =  "";
    String vendor_address3 =  "";
    String vendor_city =  "";
    String vendor_state =  "";
    String vendor_fax = "";
    String vendor_zipcode =  "";
    int hub=0;
    int index = 0;
    String branch_plant =  "";
    String buyer =  "";
    String shipto_company   =  "";
    String shipto_address1 =  "";
    String shipto_address2 =  "";
    String shipto_address3 =  "";
    String shipto_city =  "";
    String shipto_state =  "";
    String shipto_zip =  "";
    String delivery_instr_1 =  "";
    String delivery_instr_2 =  "";
    String pymt_terms =  "";
    String order_taken_by =  "";
    String area_code =  "";
    String area_code_1 =  "";
    String fax_number = "";
    String company_id =  "";
    String client_acct =  "";
    String add_note =  "";
    String bill1 = "";
    String company =  "";
    String bill_to_address1 =  "";
    String bill_to_address2 =  "";
    String bill_to_address3 =  "";
    String bill2 = "";
    String stmt = "";
    String byrsig = "";
    String buyer_name = "";
    String buyer_email = "";
    String buyer_phone = "";
    String buyer_fax = "";
    String payment_terms = "";
    String shipper_name = "";
    String temp_desc_1 = "";
    String temp_desc_2 = "";
    String timeOut = "";
    String signature = "";
    String msg = "";
    String errormsg = "";
    String errormsgH = "";
    double order_total = 0.00;
    Date today;
    String dateOut;

    //DateFormat dateFormatter;
    Vector item_id = new Vector();
    Vector temp_desc = new Vector();
    Vector desc = new Vector();
    Vector qty = new Vector();
    Vector uom = new Vector();
    Vector prc = new Vector();
    Vector lt = new Vector();
    Vector rdd = new Vector();
    Vector associate_text_window = new Vector();
    Vector sched_pick = new Vector();

    Vector Line_item = new Vector();
    Vector Msds_Date = new Vector();


    String table1 = "purchase_info_data_view";
    String table2 = "purchase_order_jde_view";
    String table3 = "shipper_info";
    String table4 = "shipping_billing_addresses";
    String table5 = "flow_down_view";

    long time = System.currentTimeMillis();

    public void doGet (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
     date_requested = "";
     po = "";
     sl =  "";
     po_notes =  "";
     vendor="";
     vendor_1 = "";
     vendor_2 = "";
     vendor_name =  "";
     vendor_address1 =  "";
     vendor_address2 =  "";
     vendor_address3 =  "";
     vendor_city =  "";
     vendor_state =  "";
     vendor_fax = "";
     vendor_zipcode =  "";
     hub=0;
     index = 0;
     branch_plant =  "";
     buyer =  "";
     shipto_company   =  "";
     shipto_address1 =  "";
     shipto_address2 =  "";
     shipto_address3 =  "";
     shipto_city =  "";
     shipto_state =  "";
     shipto_zip =  "";
     delivery_instr_1 =  "";
     delivery_instr_2 =  "";
     pymt_terms =  "";
     order_taken_by =  "";
     area_code =  "";
     area_code_1 =  "";
     fax_number = "";
     company_id =  "";
     client_acct =  "";
     add_note =  "";
     bill1 = "";
     company =  "";
     bill_to_address1 =  "";
     bill_to_address2 =  "";
     bill_to_address3 =  "";
     bill2 = "";
     stmt = "";
     byrsig = "";
     buyer_name = "";
     buyer_email = "";
     buyer_phone = "";
     buyer_fax = "";
     payment_terms = "";
     shipper_name = "";
     temp_desc_1 = "";
     temp_desc_2 = "";
     timeOut = "";
     signature = "";
     msg = "";
     errormsg = "";
     order_total = 0.00;

     item_id.clear();
     temp_desc.clear();
     desc.clear();
     qty.clear();
     uom.clear();
     prc.clear();
     lt.clear();
     rdd.clear();
     Line_item.clear();
     Msds_Date.clear();

     associate_text_window.clear();
     sched_pick.clear();

      StringBuffer Msg1 = new StringBuffer();
      response.setContentType("text/html; charset=ISO-8859-1");
      HttpSession session = request.getSession();
      PrintWriter out = response.getWriter();
      item_id.clear();

      /*//Get values from the seesion varuiable
      Hashtable tmp1 = new Hashtable();
      //Logon_Id = "nawaz"; for local testing

      String buyername = BothHelpObjs.makeBlankFromNull((String)session.getValue("BUYER_NAME"));

      tmp1 = (Hashtable)session.getValue("ALL_I_NEED");
      System.out.println("the servlet "+tmp1);
      po = tmp1.get("PO-NUMBER").toString().trim();
      System.out.println("Login Name: in FaxPo and Radian Po  "+buyername+"   "+po);

      errormsgH = "<HTML><HEAD><TITLE>ERROR OCCURED</TITLE></HEAD>";
      errormsgH +="<BODY text=\"#000000\" link=\"#0000FF\" vlink= \"#FF00FF\"><FONT FACE=\"Arial\"><CENTER>\n";*/

        po = new String(request.getParameter("po")).trim();
        System.out.println("The PO Number is "+po);

        getAddressInfo(out);

        String buyername = "nawaz";
        buyer = "nawaz";

        if ((buyername.indexOf("Nawaz") < 0 ))
        {

        }
        else if ((buyer.indexOf(buyername) < 0 ))
        {
          out.println(errormsgH);
          out.println("<P>You Don't have access to this PO or there is no buyer associated with this po</P>");
          out.println("<P>Go Back and Please Try Another </P>");
          out.println("<P>Thanks.</P></FONT></CENTER></BODY></HTML>");
          return;
        }

        if (!(errormsg.length() > 2)){getPoInfo(out);}
        else{PrintError(out);return;}
        if (!(errormsg.length() > 2))
        {
        getJDEPoInfo(out);
        }
        else
        {
        PrintError(out);
        return;
        }
        if (!(errormsg.length() > 2))
        {
         getsomeThingDone(out);
        }
        else
        {
        PrintError(out);
        return;
        }
        if (!(errormsg.length() > 2))
        {
         getShipperinfo(out);
        }
        else
        {
        PrintError(out);
        return;
        }

        getMSDSRevisionDate(out);

        if (!(errormsg.length() > 2))
        {
        Msg1.append(buildPage(out));
        }
        else
        {
        PrintError(out);
        return;
        }
        out.println(Msg1);
    }
    public void PrintError(PrintWriter out)
    {
     errormsg +="<P>Go Back and Please Try Again </P>";
     errormsg +="<P>Thanks.</P></FONT></CENTER></BODY></HTML>";
     errormsgH += errormsg;
     out.println(errormsgH);
    }

    public void getAddressInfo(PrintWriter out)
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod", "tcm_ops", "n0s3ha1r");
            Statement statement = connection.createStatement();

            int numberRec = 0;

            stmt = "select distinct po_notes, vendor_name, ";
            stmt += "vendor_address1, vendor_address2, vendor_address3, ";
            stmt += "vendor_city, vendor_state, vendor_zipcode, ";
            stmt += "branch_plant, buyer, ";
            stmt += "shipto_company, shipto_address1, shipto_address2, ";
            stmt += "shipto_address3,  shipto_city, shipto_state, ";
            stmt += "shipto_zip ";
            stmt += "from " + table1 ;
            stmt += " where radian_po = "+po+" and buyer is not null";

            System.out.println ("stmt:" + stmt);
            ResultSet rs = statement.executeQuery(stmt);
            while (rs.next())
            {
                po_notes =  BothHelpObjs.makeBlankFromNull(rs.getString(1));
                vendor =  BothHelpObjs.makeBlankFromNull(rs.getString(2));
                vendor_address1 =  BothHelpObjs.makeBlankFromNull(rs.getString(3));
                vendor_address2 =  BothHelpObjs.makeBlankFromNull(rs.getString(4));
                vendor_address3 =  BothHelpObjs.makeBlankFromNull(rs.getString(5));
                vendor_city =  BothHelpObjs.makeBlankFromNull(rs.getString(6));
                vendor_state =  BothHelpObjs.makeBlankFromNull(rs.getString(7));
                vendor_zipcode =  BothHelpObjs.makeBlankFromNull(rs.getString(8));
                branch_plant =  BothHelpObjs.makeBlankFromNull(rs.getString(9)).trim();
                buyer =   BothHelpObjs.makeBlankFromNull(rs.getString(10));
                shipto_company  =  BothHelpObjs.makeBlankFromNull(rs.getString(11));
                shipto_address1 =  BothHelpObjs.makeBlankFromNull(rs.getString(12));
                shipto_address2 =  BothHelpObjs.makeBlankFromNull(rs.getString(13));
                shipto_address3 =  BothHelpObjs.makeBlankFromNull(rs.getString(14));
                shipto_city =  BothHelpObjs.makeBlankFromNull(rs.getString(15));
                shipto_state =  BothHelpObjs.makeBlankFromNull(rs.getString(16));
                shipto_zip =  BothHelpObjs.makeBlankFromNull(rs.getString(17));
                System.out.println("buyer: " + buyer + "\n");
                numberRec += 1;
            }
            rs.close();
            System.out.println("Branch Plant:   "+branch_plant+"\n");

            if (numberRec == 0)
            {
            errormsg +="<P><B>Error!! </B></P>";
            errormsg +="<P>There are no records matching this PO in 'purchase_info_data_view'</P>";
            errormsg +="<P>Please try again or Contact the Database Team.</P>";
            return;
            }

            hub = Integer.parseInt(branch_plant);
            //hub = branch_plant;
            System.out.println("hub: " + hub + "\n");
        }
        catch(ClassNotFoundException cnfe)
        {
            errormsg +="<P>Error loading driver AddressInfo: </P>";
            cnfe.printStackTrace();
            return;
        }
        catch(SQLException sqle)
        {
            errormsg +="<P>Error InSQL AddressInfo: </P>";
            sqle.printStackTrace();
            return;
        }
    }

    public void getPoInfo(PrintWriter out)
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod","tcm_ops","n0s3ha1r");
            Statement statement = connection.createStatement();
            int numberRec = 0;


            stmt = "Select ";
            stmt +="PURCHASE_INFO_DESC_VIEW.ITEM_ID, ";
            stmt +="PURCHASE_INFO_DESC_VIEW.LINE_DESC, ";
            stmt +="PURCHASE_INFO_DESC_VIEW.QTY, ";
            stmt +="PURCHASE_INFO_DESC_VIEW.UOM, ";
            stmt +="PURCHASE_INFO_DESC_VIEW.UNIT_PRICE, ";
            stmt +="PURCHASE_INFO_DESC_VIEW.TOTAL_PRICE_PAID, ";
            stmt +="PURCHASE_INFO_DESC_VIEW.SCHED_PICK, ";
            stmt +="PURCHASE_INFO_DESC_VIEW.LINE_ITEM ";
            stmt +="from ";
            stmt +="PURCHASE_INFO_DESC_VIEW ";
            stmt +="where ";
  //          stmt +="PURCHASE_INFO_DESC_VIEW.ITEM_ID != 311546 AND ";
            stmt +="PURCHASE_INFO_DESC_VIEW.RADIAN_PO ="+po+" AND " ;
            stmt +="PURCHASE_INFO_DESC_VIEW.TOTAL_PRICE_PAID > 0.01 " ;
            stmt +="order by line_item asc";
            //stmt +="PURCHASE_INFO_DESC_VIEW.BUYER = 'Mills' order by line_item asc";
            //stmt +="PURCHASE_INFO_DESC_VIEW.BUYER = '"+buyer+"'";


            /*stmt = "select distinct a.item_id, a.item_desc, b.order_qty, a.order_uom, a.unit_price_paid,";
            stmt +="round(a.unit_price_paid*b.ORDER_QTY,2) total_price_paid,";
            stmt +=" to_char(b.REQUEST_DATE,'mm/dd/yyyy') sched_pick ";
            stmt +="from purchase_info_data_view a, purchase_order_jde_snapshot b ";
            stmt +="where a.radian_po = b.radian_po(+) and a.item_id = b.item_id(+) and";
            stmt +=" a.radian_po ="+po+" and a.item_id != 311546" ;
            stmt +=" order by item_id";*/


            /*stmt = "select distinct a.item_id, a.item_desc, a.order_qty, a.order_uom, ";
            stmt += "a.unit_price_paid, a.total_price_paid, ";
            stmt += "to_char(b.REQUEST_DATE,'mm/dd/yyyy') sched_pick ";
            stmt += "from " + table1 + " a, " + table2 + " b ";
            stmt += " where a.radian_po = b.radian_po(+) ";
            stmt += " and a.item_id = b.item_id(+) and a.order_qty = b.order_qty(+) ";
            stmt += " and a.radian_po = '" + po + "'" ;
            stmt += " order by item_id";*/

            System.out.println ("stmt:" + stmt+"\n");
            ResultSet rs = statement.executeQuery(stmt);
            while (rs.next())
            {
                item_id.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(1)));
                temp_desc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(2)));
                qty.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(3)));
                uom.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(4)));
                prc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(5)));
                lt.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(6)));
                /*prc.addElement(formatter.format(Double.valueOf(BothHelpObjs.makeBlankFromNull(rs.getString(5))).doubleValue()));
                lt.addElement(formatter.format(Double.valueOf(BothHelpObjs.makeBlankFromNull(rs.getString(6))).doubleValue()));*/
                sched_pick.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(7)));
                Line_item.addElement(BothHelpObjs.makeBlankFromNull(rs.getString(8)));
                order_total += Double.valueOf(BothHelpObjs.makeBlankFromNull(rs.getString(6))).doubleValue();
                numberRec += 1;
            }
            System.out.println("item_Id" + item_id + "\n");
            System.out.println("Order_Total" + order_total + "\n");
            rs.close();
            if (numberRec == 0)
            {
            errormsg +="<P><B>Error!! </B></P>";
            errormsg +="<P>There are no records matching this PO in 'purchase_info_data_view'</P>";
            errormsg +="<P>Please try again or Contact the Database Team.</P>";
            return;
            }
        }
        catch(NumberFormatException nfe)
        {
            errormsg +="<P>Error InNumber Format of PO Query: </P>";
            nfe.printStackTrace();
            return;
        }
        catch(ClassNotFoundException cnfe)
        {
            errormsg +="<P>Error In Driver of PO Query: </P>";
            cnfe.printStackTrace();
            return;
        }
        catch(SQLException sqle)
        {
            errormsg +="<P>Error In SQL Format of PO Query: </P>";
            sqle.printStackTrace();
            return;
        }

        System.out.println("Total: " + item_id.size() + "\n");

        System.out.println("Finished the po query\n");

    }

    public void getJDEPoInfo(PrintWriter out)
    {
        //lookup purchase order information from purchase_info_data_view
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod", "tcm_ops", "n0s3ha1r");
            Statement statement = connection.createStatement();

            stmt  = "select distinct delivery_instr_1, delivery_instr_2, payment_terms, ";
            stmt += "order_taken_by, area_code, fax_no, associate_text_window,  associate_text_window_order ";
            stmt += "from " + table2;
            stmt += " where radian_po = "+ po;
            stmt += " order by associate_text_window_order" ;
            System.out.println ("stmt:" + stmt);
            ResultSet rs = statement.executeQuery(stmt);
            int numberRec = 0;
            while (rs.next())
            {
                delivery_instr_1 = BothHelpObjs.makeBlankFromNull(rs.getString("delivery_instr_1"));
                delivery_instr_2 =  BothHelpObjs.makeBlankFromNull(rs.getString("delivery_instr_2"));
                pymt_terms =  BothHelpObjs.makeBlankFromNull(rs.getString("payment_terms"));
                order_taken_by =  BothHelpObjs.makeBlankFromNull(rs.getString("order_taken_by"));
                area_code =  BothHelpObjs.makeBlankFromNull(rs.getString("area_code"));
                fax_number =  BothHelpObjs.makeBlankFromNull(rs.getString("fax_no"));
                associate_text_window.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("associate_text_window")));
                numberRec += 1;
            }
            rs.close();

            if (numberRec == 0)
            {
            errormsg +="<P><B>Error!! </B></P>";
            errormsg +="<P>There are no records matching this PO in 'purchase_order_jde_view'</P>";
            errormsg +="<P>Please try again or Contact the Database Team.</P>";
            return;
            }
        }
        catch(ClassNotFoundException cnfe)
        {
            errormsg +="<P>Error In SQL Format of JDEPO Query: </P>";
            cnfe.printStackTrace();
            return;
        }
        catch(SQLException sqle)
        {
            while ((sqle.getNextException()) !=null)
            {System.out.println(sqle.getMessage());}
            errormsg +="<P>Error In SQL Format of PO Query: </P>";
            return;
        }
    }

    public void getsomeThingDone(PrintWriter out)
    {
        //convert & to &amp; in item descriptions
        System.out.println("\n---- Starting item descriptions\n");
        for(int p=0; p<item_id.size(); p++) {
            temp_desc_1 = (String) temp_desc.elementAt(p);
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
            errormsg +="<P>Error In Null Pointer Format of Something Query: </P>";
            npe.printStackTrace();
            return;
            }
            desc.addElement(temp_desc_2);
        }

        System.out.println("Finished item descriptions.\n");

        if ( buyer.equalsIgnoreCase ("Arnett"))
        {
            buyer_name = "Mary Arnett";
            byrsig = "Mary_Arnett";
            buyer_phone = "512-419-6841";
            buyer_fax = "512-419-6991";
        }
        else if ( buyer.equalsIgnoreCase ("Baletka"))
        {
            buyer_name = "Mark Baletka";
            byrsig = "Mark_Baletka";
            buyer_phone = "512-419-6842";
            buyer_fax = "512-419-6991";
        }
        else if ( buyer.equalsIgnoreCase ("Buckler"))
        {
            buyer_name = "Kathy Buckler";
            byrsig = "Kathy_Buckler";
            buyer_phone = "512-419-6843";
            buyer_fax = "512-419-6991";
        }
        else if ( buyer.equalsIgnoreCase ("Collins"))
        {
            buyer_name = "Joanne Collins";
            byrsig = "Joanne_Collins";
            buyer_phone = "512-419-6847";
            buyer_fax = "512-419-6991";
        }
        else if ( buyer.equalsIgnoreCase ("Mills"))
        {
            buyer_name = "Ben Mills";
            byrsig = "Ben_Mills";
            buyer_phone = "512-419-6844";
            buyer_fax = "512-419-6991";
        }
        else if ( buyer.equalsIgnoreCase ("Williams"))
        {
            buyer_name = "Lynne Williams";
            byrsig = "Lynne_Williams";
            buyer_phone = "512-419-6845";
            buyer_fax = "512-419-6991";
        }
        else if ( buyer.equalsIgnoreCase ("Yancey"))
        {
            buyer_name = "Ken Yancey";
            byrsig = "Kenneth_Yancey";
            buyer_phone = "512-419-6846";
            buyer_fax = "512-419-6991";
        }


        if (buyer.equalsIgnoreCase ("Buckler"))
        {
            buyer_email = "Evelyn_Sadler@haastcm.com";
        }
        else
        {
            buyer_email = byrsig +  "@haastcm.com";
        }

        //set payment terms
        System.out.println("Starting payment terms.\n");
        if ( pymt_terms.equalsIgnoreCase ("N45"))
        {
            payment_terms = "Net 45 days";
        }
        else if ( pymt_terms.equalsIgnoreCase ("N30"))
        {
            payment_terms = "Net 30 days";
        }
        else if ( pymt_terms.equalsIgnoreCase ("N10"))
        {
            payment_terms = "Net 10 days";
        }
        else if ( pymt_terms.equalsIgnoreCase ("CC"))
        {
            payment_terms = "credit card";
        }
        //set carrier
        System.out.println ("Setting carrier.\n");

        if ( delivery_instr_1.startsWith ("AB")){
            shipper_name = "Airborne";
        }
        else if ( delivery_instr_1.startsWith ("AIRBORNE")) {
            shipper_name = "Airborne";
        }
        else if ( delivery_instr_1.startsWith ("AF")){
            shipper_name = "American Freightway";
        }
        else if ( delivery_instr_1.startsWith ("BA")) {
            shipper_name = "Burlington Airway";
        }
        else if ( delivery_instr_1.startsWith ("EW")) {
            shipper_name = "Emery Worldwide";
        }
        else if ( delivery_instr_1.startsWith ("FE")) {
            shipper_name = "Federal Express";
        }
        else if ( delivery_instr_1.startsWith ("FEDERAL EXPRESS")) {
            shipper_name = "Federal Express";
        }
        else if ( delivery_instr_1.startsWith ("MC")) {
            shipper_name = "Motor Cargo";
        }
        else if ( delivery_instr_1.startsWith ("MOTOR CARGO")) {
            shipper_name = "Motor Cargo";
        }
        else if ( delivery_instr_1.startsWith  ("NEW PENN")) {
            shipper_name = "New Penn";
        }
        else if ( delivery_instr_1.startsWith  ("NP")) {
            shipper_name = "New Penn";
        }
        else if ( delivery_instr_1.startsWith ("PPA")) {
            shipper_name = "Pre Pay and Add";
        }
        else if ( delivery_instr_1.startsWith ("PRE-PAY & ADD")) {
            shipper_name = "Pre Pay and Add";
        }
        else if ( delivery_instr_1.startsWith ("SJT")) {
            shipper_name = "S&J Transportation (refrigerated)";
        }
        else if ( delivery_instr_1.startsWith ("UPS")) {
            shipper_name = "UPS";
        }
        else if ( delivery_instr_1.startsWith ("VT")) {
            shipper_name = "Vendor Truck";
        }
        else if ( delivery_instr_1.startsWith ("YF")) {
            shipper_name = "Yellow Freight";
        }
        else if ( delivery_instr_1.startsWith ("YELLOW FREIGHT")) {
            shipper_name = "Yellow Freight";
        }
        else if ( delivery_instr_1.startsWith ("BB")) {
            shipper_name = "Bob Brinks";
        }
        else if ( delivery_instr_1.startsWith ("BOB BRINKS")) {
            shipper_name = "Bob Brinks";
        }
    }

    public void getShipperinfo(PrintWriter out)
    {
        // look up shippers data
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod", "tcm_ops", "n0s3ha1r");
            Statement statement = connection.createStatement();
            stmt = "select d.company_id, d.client_acct,";
            stmt += "d.add_note from " + table3 + " d ";
            stmt += "where hub = " + hub + " and shipper_name = '";
            stmt += shipper_name + "'";
            System.out.println ("stmt:" + stmt);
            ResultSet rs = statement.executeQuery(stmt);
            int numberRec = 0;
            while (rs.next()) {
                company_id =  BothHelpObjs.makeBlankFromNull(rs.getString(1));
                client_acct = BothHelpObjs.makeBlankFromNull(rs.getString(2));
                add_note =  BothHelpObjs.makeBlankFromNull(rs.getString(3));
                 numberRec += 1;
            }
            rs.close();
            if (numberRec == 0)
            {
            errormsg +="<P><B>Error!! </B></P>";
            errormsg +="<P>There are no records matching this PO in 'shipper_info'</P>";
            errormsg +="<P>Please try again or Contact the Database Team.</P>";
            return;
            }

        } catch(ClassNotFoundException cnfe) {
           errormsg +="<P>Error Driver ShipperInfo: </P>";
            cnfe.printStackTrace();
            return;
        } catch(SQLException sqle) {
            errormsg +="<P>Error In  SQL ShipperInfoNull Query: </P>";
            sqle.printStackTrace();
            return;
        }

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod", "tcm_ops", "n0s3ha1r");
            Statement statement = connection.createStatement();
            stmt = "select company, address_1, address_2, address_3 ";
            stmt += "from " + table4;
            stmt += " where branch_plant = '" + branch_plant + "'";
            System.out.println ("stmt:" + stmt);
            ResultSet rs = statement.executeQuery(stmt);
            int numberRec = 0;

            while (rs.next()) {
                bill1 = "All shipping invoices are billed third party to:";
                company = BothHelpObjs.makeBlankFromNull(rs.getString(1));
                bill_to_address1 =  BothHelpObjs.makeBlankFromNull(rs.getString(2));
                bill_to_address2 =  BothHelpObjs.makeBlankFromNull(rs.getString(3));
                bill_to_address3 =  BothHelpObjs.makeBlankFromNull(rs.getString(4));
                bill2 = "Radian will not pay freight invoices billed to our company";
                 numberRec += 1;
            }

            rs.close();
            if (numberRec == 0)
            {
            errormsg +="<P><B>Error!! </B></P>";
            errormsg +="<P>There are no records matching this PO in 'shipping_billing_addresses'</P>";
            errormsg +="<P>Please try again or Contact the Database Team.</P>";
            return;
            }

        } catch(ClassNotFoundException cnfe) {
            errormsg +="<P>Error In  DRIVER ShipperInfoNull Query2 : </P>";
            cnfe.printStackTrace();
            return;
        } catch(SQLException sqle) {
            errormsg +="<P>Error In  SQL ShipperInfoNull Query2 : </P>";
            sqle.printStackTrace();
            return;
        }

        System.out.println("Finished the shippers bill to lookup\n");

        //set shelf life
        if ( delivery_instr_2.startsWith("80"))
        {
            sl = "80% shelf life required upon delivery";
        }
        else if
            (
                delivery_instr_2.startsWith("50")) {
            sl = "50% shelf life or 6 months required whichever is greater required upon delivery";
        }
        else if ( delivery_instr_2.startsWith("None"))
        {
            sl = "None";
        }
        else if (delivery_instr_2.equalsIgnoreCase(null))
        {
         sl = delivery_instr_2+ "% shelf life required upon delivery";
        }
        else
        {
        //sl = "Minimum shelf life required upon delivery";
        }

        //set vendor name
        try
        {
            index = vendor.indexOf("&");
            if (index > -1)
            {
                vendor_name = vendor.substring(0, vendor.indexOf("&"));
                vendor_name += "&amp;";
                vendor_name += vendor.substring(vendor.indexOf("&")+1);
            }
            else
            {
                vendor_name = vendor;
            }
        }
        catch(NullPointerException npe)
        {
            errormsg +="<P>Error Null Pointer ShipperInfoNull Vendor Name: </P>";


            npe.printStackTrace();
            return;
        }

        //set vendor fax

        if (area_code.equalsIgnoreCase(""))
        {
            vendor_fax = fax_number;
        }
        else
        {
            try
            {
                index = area_code.indexOf(" ");
                if (index > -1)
                {
                    vendor_fax = "1-" + area_code.substring(0,
                                                            area_code.indexOf(" ")) + "-" + fax_number;
                }
                else
                {
                    vendor_fax = "1-" + area_code + "-" + fax_number;
                }
            }
            catch(NullPointerException npe)
            {
            errormsg +="<P>Error Null Pointer ShipperInfoNull Vendor Name: </P>";
            npe.printStackTrace();
            return;
            }
        }

    }

     public void getMSDSRevisionDate (PrintWriter out)
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod", "tcm_ops", "n0s3ha1r");
            Statement statement = connection.createStatement();

            int numberRec = 0;

            stmt = "select * from PO_MSDS_REVISION_DATE where radian_po="+po+"";

            System.out.println ("stmt:" + stmt);
            ResultSet rs = statement.executeQuery(stmt);
            Hashtable result = new Hashtable();

            result = new Hashtable();
            while (rs.next())
            {
               result = new Hashtable();
               result.put("REVISION_DATE",BothHelpObjs.makeBlankFromNull(rs.getString("REVISION_DATE")));
               System.out.println (rs.getString("REVISION_DATE")+"     "+result);

               Msds_Date.add(numberRec,result);

               numberRec += 1;
            }
            rs.close();
            System.out.println (Msds_Date);

        }
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public StringBuffer buildPage(PrintWriter out)
    {
        //Select buyer signature
        //String byrsigref=new String ("/home/servlet/radian/web/servlets/pos/"+byrsig+"_.gif");
        //signature = new String("https://eagle.tcmis.com/tmpPO/"+byrsig+"_.gif");

        String byrsigref=new String ("/webdata/html/images/"+byrsig+"_.gif");
        signature = new String("https://www.tcmis.com/images/"+byrsig+"_.gif");

        StringBuffer Msg = new StringBuffer();

        System.out.println("Starting to write the html.\n");
        Msg.append("<html>\n");
        Msg.append("<head>\n");
        Msg.append("<title>Purchase Order Form</title>\n");
        Msg.append("</head>\n");
        Msg.append("<body text=\"#000000\">\n");
        Msg.append("<FONT FACE=\"Arial\"><table width =\"100%\">\n");
        Msg.append("<tr><td>Purchase Order: "+po+"</td></tr>\n");
        Msg.append("<tr><td width =\"25%\">\n");
        Msg.append("Supplier:</td><td width =\"25%\">Ship To:</td>\n");
        Msg.append("<td width =\"25%\">Buyer Name:</td>\n");
        //Msg.append("<td width = \"25%\" align=\"right\">Date:" + dateOut);
        //Msg.append("</td></tr>\n");
        Msg.append("</tr>\n");
        Msg.append("<tr><td>"+vendor_name+"</td><td>"+shipto_company+"</td>\n");
        Msg.append("<td>"+buyer_name+"</td></tr>\n");
        Msg.append("<tr><td>"+vendor_address1+"</td><td>"+shipto_address1+"</td></tr>\n");
        Msg.append("<tr><td>"+vendor_address2+"</td><td>"+shipto_address2+"</td></tr>\n");
        Msg.append("<tr><td>"+vendor_city+","+vendor_state+" "+vendor_zipcode+"</td>\n");
        Msg.append("<td>"+shipto_city+","+shipto_state+" "+shipto_zip+"</td></tr>\n");
        Msg.append("<tr><td>Order taken by:</td><td>"+order_taken_by+"</td>\n");
        Msg.append("</tr></table>\n");
        Msg.append("<table><tr><td><B>Line #</B></td><td><B>Radian Item ID</B></td>\n");
        Msg.append("<td><B>Material Description: </B></td>\n");
        Msg.append("<td><B>Quantity</B></td><td><B>UM</B></td><td><B>Unit</B></td>\n");
        Msg.append("<td><B>Line</B></td><td><B>Required Delivery Date:</B></td></tr>\n");
        Msg.append("<tr><td></td><td></td><td></td><td></td><td><B>Cost:</B></td>\n");
        Msg.append("<td><B>Total:</B></td><td></td></tr>\n");
        //System.out.println ("order total: " + order_total + "\n");
        for(int k=0; k < item_id.size(); k++)
        {
            //if (!(item_id.elementAt(k).toString().equalsIgnoreCase("311546")))
            {
                Msg.append("<tr>\n");
                Msg.append("<td>"+Line_item.elementAt(k)+"</td>\n");
                Msg.append("<td>"+item_id.elementAt(k)+"</td>\n");
                Msg.append("<td>"+desc.elementAt(k)+"</td>\n");
                Msg.append("<td align = center>"+qty.elementAt(k)+"</td>\n");
                Msg.append("<td>"+uom.elementAt(k)+"</td>\n");
                Msg.append("<td align = right>"+prc.elementAt(k)+"</td>\n");
                Msg.append("<td align = right>"+lt.elementAt(k)+"</td>\n");
                Msg.append("<td>"+sched_pick.elementAt(k)+"</td>\n");
                Msg.append("</tr>\n");
                Msg.append("<tr><td></td></tr>\n");
            }
        }
        Msg.append("</table>\n");

        Msg.append("<table width = \"90%\"><tr><td width = \"30%\">\n");
        Msg.append("<b>Payment Terms:</b></td>\n");
        Msg.append("<td width = \"30%\"><b>Order Total:</b>"+order_total+"</td>\n");
        Msg.append("<td width = \"30%\">\n");
        Msg.append("Radian TCM orders are tax exempt. </td></tr>\n");
        Msg.append("<tr><td>"+payment_terms+"</td><td></td><td>A copy of the resale\n");
        Msg.append(" certificate will be</td></tr> \n");
        Msg.append("<tr><td></td><td></td><td>provided  upon request.</td></tr></table>\n");
        Msg.append("<p><b>Special Requirements</b><br>\n");
        Msg.append("* Material Safety Data Sheets (MSDS) must be enclosed with \n");
        Msg.append("product.<br> * Shipping papers, Invoices and Cartons must\n");
        Msg.append(" reference PO # and Radian Item ID.<br></p>\n");

        if (!(branch_plant.trim().equalsIgnoreCase("2202")))
        {
        Msg.append("<p><b>Shelf Life Requirements:</b>"+sl+"</p>\n");
        }
        Msg.append("<p>Failure to meet these special requirements or on dock delivery \n");
        Msg.append("date may result in delayed payment of invoice, cancellation of<br>\n");
        Msg.append("order or reduction in future orders.</p>\n");
        Msg.append("<p><b>Notes: </b><br>\n");
        for(int l=0; l<associate_text_window.size(); l++)
        {
            String dtl_associate_text_window = (String) associate_text_window.elementAt(l);
            Msg.append(""+dtl_associate_text_window+"<br>\n");
        }

        //  associate_text_window+"</p>\n");

        Msg.append("<p><b>Online Documentation</b><br>\n");
        Msg.append("Radian Terms and Conditions and Radian Supplier Quality Control \n");
        Msg.append(" documents can be found at \n");
        Msg.append("the website http://www.radian.com/html/services/tcm/tcmhme.htm.</p>\n");
        Msg.append("<table width = \"90%\"><tr><td width = \"30%\">\n");

        if (company.equalsIgnoreCase("Raytheon"))
        {
            Msg.append("<b>Shipping Information</b></td>\n");
            Msg.append("<td width = \"30%\"></td>\n");
            Msg.append("<td width = \"30%\">"+bill1+"</td></tr>\n");
            Msg.append("<tr><td colspan=\"2\">Approved Shippers and Account Numbers:</td>\n");
            Msg.append("<td> "+company+"</td></tr>\n");
            Msg.append("<tr><td>"+shipper_name+"</td><td>"+client_acct+"</td>\n");
            Msg.append("<td>"+bill_to_address1+"</td></tr>\n");
            Msg.append("<tr><td colspan=\"2\">Notes:  "+add_note+"</td>\n");
            Msg.append("<td>"+bill_to_address2+"</td></tr>\n");
            Msg.append("<tr><td></td><td></td><td>"+bill_to_address3+"</td></tr>\n");
            Msg.append("<tr><td></td><td></td><td>"+bill2+"</td></tr>\n");
            Msg.append("<tr><td></td></tr>\n");
        }
        else
        {
            Msg.append("<b>Shipping Information</b></td>\n");
            Msg.append("<td width = \"30%\"></td>\n");
            Msg.append("<td width = \"30%\"></td></tr>\n");
            Msg.append("<tr><td colspan=\"2\">Approved Shippers and Account Numbers:</td>\n");
            Msg.append("<td></td></tr>\n");
            Msg.append("<tr><td>"+shipper_name+"</td><td>"+client_acct+"</td>\n");
            Msg.append("<td></td></tr>\n");
            Msg.append("<tr><td colspan=\"2\">Notes:  "+add_note+"</td>\n");
            Msg.append("<td></td></tr>\n");
            Msg.append("<tr><td></td><td></td><td></td></tr>\n");
            Msg.append("<tr><td></td><td></td><td></td></tr>\n");
            Msg.append("<tr><td></td></tr>\n");
        }
        Msg.append("<tr><td><b>Billing Information</b></td><td></td><td></td></tr>\n");
        Msg.append("<tr><td>Invoices must be sent in duplicate to:</td><td></td><td></td></tr>\n");
        Msg.append("<tr><td>Accounts Payable</td><td></td><td></td></tr>\n");
        Msg.append("<tr><td>Radian International</td><td></td><td></td></tr>\n");
        Msg.append("<tr><td>PO Box 201088</td><td></td><td></td></tr>\n");
        Msg.append("<tr><td>Austin, TX 78720-1088</td></tr>\n");
        Msg.append("</table>\n");
        Msg.append("<table><tr><td></td></tr>\n");
        Msg.append("<tr><td></td></tr>\n");
        Msg.append("<tr><td></td></tr>\n");
        Msg.append("<tr><td><img src =\""+signature+"\" alt=\"Cannot display\" \n");
        Msg.append("align=left></td></tr>\n");
        Msg.append("<tr><td>Buyer Signature</td></tr>\n");
        Msg.append("</table>\n");

        //encode redirected URL to maintain session
        String formURL1 = "tcmIS/servlet/radian.web.servlets.pos.purchOrderForm";
        //formURL1 = response.encodeRedirectUrl(formURL1);

        // print button
        Msg.append("<FORM method=\"POST\" target=\"New\" action=\"/"+formURL1+"\">\n");
        Msg.append("<input type=\"submit\" name=\"Create\" value=\"Create\">\n");
        Msg.append("&nbsp;&nbsp;&nbsp;&nbsp;\n");
        Msg.append("<input type=\"hidden\" name=\"po\" value=\""+po+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"hub\" value=\""+hub+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"sl\" value=\""+sl+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"payment_terms\" value=\""+payment_terms+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"shipper_name\" value=\""+shipper_name+"\">\n");

        String histitem_id_passed = "";
        for(int i=0; i<item_id.size()-1; i++)
        {
            if (!(item_id.elementAt(i).toString().equalsIgnoreCase("311546")))
            {
                String histitem_id = (String) item_id.elementAt(i);
                histitem_id_passed += histitem_id + " or item_id = ";
            }

        }
        if (!(item_id.lastElement().toString().equalsIgnoreCase("311546")))
        {
            histitem_id_passed += item_id.lastElement();
        }

        String Msds_dates = "* MATERIAL SAFETY DATA SHEETS (MSDS) MUST BE ENCLOSED WITH PRODUCT.\n";
        Msds_dates += "*********************************************************************\n";
        Msds_dates += "\n* SHIPPING PAPERS, INVOICES, AND CARTONS MUST REFERENCE PO # "+po+".\n";
        Msds_dates += "*********************************************************************\n";
        Msds_dates += "\n* RADIAN ITEM # "+item_id.elementAt(0)+" MUST APPEAR ON ALL SHIPPING DOCUMENTS. \n";
        Msds_dates += "*********************************************************************\n";
        Msds_dates += "\n* IF MATERIAL HAS MSDS REVISION LATER THAN ";
        Hashtable Msds_Datess = new Hashtable();

        System.out.println(Msds_Date.size());

        if (Msds_Date.size() > 1)
        {
          for(int i=0; i<Msds_Date.size(); i++)
          {
           Msds_Datess = (Hashtable)Msds_Date.get(i);
           Msds_dates = Msds_dates + Msds_Datess.get("REVISION_DATE").toString();
           Msds_dates = Msds_dates + "(PART"+(i+1)+") or ";
          }
         Msds_dates = Msds_dates.substring(0,Msds_dates.length()-3);
        }
        else
        {
           Msds_Datess = (Hashtable)Msds_Date.get(0);
           Msds_dates +=  Msds_Datess.get("REVISION_DATE");
        }

        Msds_dates += "\nFAX UPDATED MSDS(s) REFERENCING ITEM # "+item_id.elementAt(0)+" TO 512-419-6991\n";
        //Msds_dates += "*********************************************************************\n";
        System.out.println("This is the Message        " +Msds_dates+item_id.elementAt(0)+Msds_Date);

        Msg.append("<input type=\"hidden\" name=\"MSDS_dates\" value=\""+Msds_dates+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"item_id\" value=\""+histitem_id_passed+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"item_id_one\" value=\""+item_id.elementAt(0)+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"Order_Total\" value=\""+order_total+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"company\" value=\""+company+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"bill_to_address1\" value=\""+bill_to_address1+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"bill_to_address2\" value=\""+bill_to_address2+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"bill_to_address3\" value=\""+bill_to_address3+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"buyer\" value=\""+buyer+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"buyer_name\" value=\""+buyer_name+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"byrsigref\" value=\""+byrsigref+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"buyer_phone\" value=\""+buyer_phone+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"branch_plant\" value=\""+branch_plant+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"bill1\" value=\""+bill1+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"bill2\" value=\""+bill2+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"company_id\" value=\""+company_id+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"sched_pick\" value=\""+sched_pick+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"order_taken_by\" value=\""+order_taken_by+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"client_acct\" value=\""+client_acct+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"shipto_company\" value=\""+shipto_company+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"shipto_address1\" value=\""+shipto_address1+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"shipto_city\" value=\""+shipto_city+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"shipto_state\" value=\""+shipto_state+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"shipto_zip\" value=\""+shipto_zip+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_name\" value=\""+vendor_name+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_address1\" value=\""+vendor_address1+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_city\" value=\""+vendor_city+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_state\" value=\""+vendor_state+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_zipcode\" value=\""+vendor_zipcode+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_zipcode\" value=\""+vendor_zipcode+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"add_note\" value=\""+add_note+"\">\n");
        Msg.append("</form>\n");

        //encode redirected URL to maintain session
        String formURL2 = "tcmIS/servlet/radian.web.servlets.pos.purch_order_3";
       // formURL2 = response.encodeRedirectUrl(formURL2);

        // send button
        Msg.append("<FORM method=\"POST\" action=\"/"+formURL2+"\">\n");
        Msg.append("<input type=\"submit\" name=\"Send\" value=\"Send\">\n");
        Msg.append("&nbsp;&nbsp;&nbsp;&nbsp;\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_name\" value=\""+vendor_name+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"vendor_fax\" value=\""+vendor_fax+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"buyer_name\" value=\""+buyer_name+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"buyer_phone\" value=\""+buyer_phone+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"buyer_fax\" value=\""+buyer_fax+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"po\" value=\""+po+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"order_taken_by\" \n");
        Msg.append(" value=\""+order_taken_by+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"buyer_email\" value=\""+buyer_email+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"reference\" value=\"purchase order "+po+"\">\n");
        Msg.append("</FORM>\n");

        //encode redirected URL to maintain session
        String formURL3 = "tcmIS/servlet/radian.web.servlets.pos.purch_order_1";
        //formURL3 = response.encodeRedirectUrl(formURL3);

        // new po button
        //Msg.append("<FORM method=\"POST\" action=\"/"+formURL3+"\">\n");
        Msg.append("<FORM method=\"POST\" action=\"/tcmIS/servlet/radian.web.servlets.pos.faxlogin?Session=0\">\n");
        Msg.append("<input type=\"submit\" name=\"New_Po\" value=\"New_Po\">\n");
        Msg.append("<input type=\"hidden\" name=\"filetoDelete\" value=\"/home/httpd/htdocs/tmpPO/po_"+po+".pdf\">\n");
        Msg.append("&nbsp;&nbsp;&nbsp;&nbsp;\n");
        Msg.append("</FORM>\n");
        Msg.append("</FONT></body></html>\n");
        System.out.println("Finished writing the html\n");
        return Msg;
    }
    // doGet

    public void doPost (HttpServletRequest request,
                        HttpServletResponse response)
        throws ServletException, IOException
    {
        doGet (request, response);
    }

} // class purchase_order



    //set time of event;
    /*//set date;
    dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
    today = new Date();
    dateOut = dateFormatter.format(today);
    //set currency format;
    NumberFormat formatter = NumberFormat.getCurrencyInstance();*/
    //lookup purchase order information from purchase_info_data_view