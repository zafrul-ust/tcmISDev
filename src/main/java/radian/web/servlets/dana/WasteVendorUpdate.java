// FrontEnd Plus GUI for JAD
// DeCompiled : WasteVendorUpdate.class

package radian.web.servlets.dana;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.WasteOrder;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class WasteVendorUpdate
{
    private ServerResourceBundle bundle;
    private TcmISDBModel db;
    private Vector data;
    private Vector data1;
    private Vector data2;
    private Vector datam;
    private PrintWriter out;
    private String order_no;
    private String actual_pickup_date;
    private String disabeled;
    private String borc;
    private String Logon_Id;
    private String userId;
    private String Shp_id;
    private String session;
    private String order_text;
    private String order_Drop;
    private String order_Form;
    private String msg;
    private String vendor_shp_id;
    private String plan_shp_dt;
    private String actual_shp_dt;
    private String org_submit_dt;
    private String resubmit_dt;
    private String cancel_dt;
    private String company_name;
    private String passed;
    private String Pswd;
    private String UpdateShip_Id;
    private String Vendor_Id;
    private String Status;
    private String open_order;
    private String old_order;
    private String type;
    private String session1;
    private String store_loc;
    private String fullname;
    private String phone;
    private String email;
    private int recs;
    private String vendorfacid;
    private String preferedservdate;
    private Hashtable result;
    private Hashtable resultm;
    private String vendor_order_no;
    private String planned_pickup_date;
    private String preffered_pickup_date;
    private Vector data3;
    private boolean HasVendor;
    private boolean CheckPass;
    private boolean gettingPass;
    private boolean gettingHTML;
    private boolean checkpswd;
    private boolean gettingShpDetail;
    private boolean gettingUpdate;
    private boolean UpdateDone;

    public WasteVendorUpdate(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = null;
        db = null;
        data = new Vector();
        data1 = null;
        data2 = null;
        datam = new Vector();
        out = null;
        order_no = "";
        actual_pickup_date = "";
        disabeled = "";
        borc = "";
        Logon_Id = "";
        userId = "";
        Shp_id = "";
        session = null;
        order_text = "";
        order_Drop = "";
        order_Form = "";
        msg = "";
        vendor_shp_id = "";
        plan_shp_dt = "";
        actual_shp_dt = "";
        org_submit_dt = "";
        resubmit_dt = "";
        cancel_dt = "";
        company_name = "";
        passed = null;
        Pswd = "N";
        UpdateShip_Id = "";
        Vendor_Id = null;
        Status = "";
        open_order = "";
        old_order = "";
        type = "";
        session1 = "";
        store_loc = "";
        fullname = "";
        phone = "";
        email = "";
        recs = 0;
        vendorfacid = "";
        preferedservdate = "";
        result = new Hashtable();
        resultm = new Hashtable();
        vendor_order_no = "";
        planned_pickup_date = "";
        preffered_pickup_date = "";
        data3 = null;
        HasVendor = true;
        CheckPass = false;
        gettingPass = false;
        gettingHTML = false;
        checkpswd = false;
        gettingShpDetail = false;
        gettingUpdate = false;
        UpdateDone = true;
        bundle = b;
        db = d;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HttpSession Session = request.getSession();
        Hashtable tmp1 = new Hashtable();
        Logon_Id = BothHelpObjs.makeBlankFromNull((String)Session.getAttribute("loginId"));
        Status = BothHelpObjs.makeBlankFromNull((String)Session.getAttribute("SESSION"));
        if(Status.equalsIgnoreCase("0"))
        {
            tmp1 = (Hashtable)Session.getAttribute("ALL_I_NEED");
            order_no = tmp1.get("ORDER_NO").toString();
            Shp_id = tmp1.get("SHP_ID").toString().trim();
            borc = tmp1.get("BORC").toString().trim();
            try
            {
                WasteOrder wo = new WasteOrder(db);
                data = wo.getAcessParam(Logon_Id);
            }
            catch(Exception e)
            {
                System.out.println("*** Error on open DB on Getting Open Orders ***");
            }
            if(data.size() > 0)
            {
                buildShpDetail(out);
            } else
            {
                msg = String.valueOf(String.valueOf(msg)).concat("<HTML><HEAD><TITLE>Waste Orders - Login</TITLE></HEAD>\n");
                msg = String.valueOf(String.valueOf(msg)).concat("<BODY>\n");
                msg = String.valueOf(String.valueOf(msg)).concat("<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n&nbsp;&nbsp;&nbsp;<BR><P></P>");
                msg = String.valueOf(String.valueOf(msg)).concat("<FONT FACE=\"Arial\" SIZE=3><b>Error You don't have access to this module.<br>\n");
                msg = String.valueOf(String.valueOf(msg)).concat("<p>Please contact your supervisor</P></B></FONT>\n");
                msg = String.valueOf(String.valueOf(msg)).concat("</BODY>\n");
                out.println(msg);
                return;
            }
        }
        if(Status.equalsIgnoreCase("2"))
        {
            tmp1 = (Hashtable)Session.getAttribute("ALL_I_NEED");
            open_order = tmp1.get("OPEN_ORDER").toString().trim();
            old_order = tmp1.get("OLD_ORDER").toString().trim();
            order_text = tmp1.get("OLD_ORDER_DAYS").toString().trim();
            Vendor_Id = tmp1.get("VENDOR_ID").toString().trim();
            if(!open_order.equalsIgnoreCase("openorders") && !old_order.equalsIgnoreCase("oldorders"))
                open_order = "openorders";
            buildWasteDetail(out);
        }
        if(Status.equalsIgnoreCase("4"))
        {
            tmp1 = (Hashtable)Session.getAttribute("ALL_I_NEED");
            vendor_shp_id = tmp1.get("VENDOR_SHP_ID").toString().trim();
            plan_shp_dt = tmp1.get("PLAN_SHP_DT").toString().trim();
            borc = tmp1.get("BORC").toString().trim();
            Shp_id = tmp1.get("SHP_ID").toString().trim();
            order_no = tmp1.get("ORDER_NO").toString();
            buildUpdate(out);
        }
        out.close();
    }

    public void buildWasteDetail(PrintWriter out)
    {
        String header = "";
        String store_loc = "";
        String fullname = "";
        String phone = "";
        String email = "";
        String vendorfacid = "";
        String preferedservdate = "";
        String checkedol = "";
        String checkedop = "";
        String oldnodaysi = "30";
        if(open_order.equalsIgnoreCase("openorders"))
            checkedop = "checked";
        if(old_order.equalsIgnoreCase("oldorders"))
        {
            checkedol = "checked";
            oldnodaysi = order_text;
        }
        try
        {
            WasteOrder wo = new WasteOrder(db);
            HasVendor = wo.IsinVendorLogin(Logon_Id, Vendor_Id);
            data1 = wo.getAcessParam(Logon_Id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("*** Error on open DB on Getting Open Orders *** 226");
        }
        header = String.valueOf(String.valueOf(header)).concat("<HTML><HEAd><TITLE>Waste Orders</TITLE>              \n<SCRIPT LANGUAGE=\"JavaScript\">\n<!-- \nfunction setField1(form)\n{\n  form.numberofdays.checked = true;\n}\n//-->     \n</SCRIPT></HEAD><BODY>  \n");
        header = String.valueOf(String.valueOf(header)).concat("<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n&nbsp;&nbsp;&nbsp;\n<FONT FACE=\"Arial\" SIZE=5 COLOR=\"#000080\"><B>\nWaste Orders\n</B></FONT>\n");
        String selected = "selected";
        String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");
        header = header+"<TABLE BORDER=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\">\n<TR>\n<form method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?Session=2\">\n"+"<TD WIDTH=\"20%\" VALIGN=\"MIDDLE\" HEIGHT=110>\n"+"<FONT SIZE=\"3\" FACE=\"Arial\"><B>Waste Vendor:  </B></FONT></TD>\n"+"<TD WIDTH=\"35%\" VALIGN=\"MIDDLE\" HEIGHT=110>\n"+"<FONT SIZE=\"3\" FACE=\"Arial\"><SELECT name=\"vendor_id\" SIZE=\"1\">\n"+"  <OPTION value=\"None\">Please Select The Vendor</OPTION>\n";
        Hashtable hD3 = null;
        for(int i = 0; i < data1.size(); i++)
        {
            hD3 = (Hashtable)data1.elementAt(i);
            String Vnedor_Name = hD3.get("VENDOR_NAME") == null ? "" : hD3.get("VENDOR_NAME").toString();
            String Vnedor_Id1 = hD3.get("VENDOR_ID") == null ? "" : hD3.get("VENDOR_ID").toString();
            if(!Vnedor_Id1.equalsIgnoreCase(Vendor_Id))
                selected = "";
            header = header+"  <OPTION "+selected+" Value=\""+Vnedor_Id1+"\">"+Vnedor_Name+"</OPTION>\n";
            selected = "selected";
        }

        header = header+"</SELECT>\n</FONT></TD>\n<TD WIDTH=\"22%\" VALIGN=\"MIDDLE\" HEIGHT=110>\n<INPUT type=\"checkbox\" name=\"openorders\" value=\"openorders\" "+checkedop+">\n"+
                        "&nbsp;&nbsp;<FONT SIZE=\"3\" FACE=\"Arial\">Open Orders only</FONT>\n"+"<BR><BR>\n"+"<INPUT type=\"checkbox\" name=\"numberofdays\" value=\"oldorders\" "+checkedol+
                        ">\n"+"&nbsp;&nbsp;<FONT SIZE=\"3\" FACE=\"Arial\">Closed Within &nbsp;&nbsp;<INPUT type=\"text\" name=\"nodaysold\" value=\""+checkedol == null ? "30" : oldnodaysi+
                        "\" onClick=\"setField1(this.form)\" SIZE=\"2\">\n"+"</FONT></TD>\n"+"<TD WIDTH=\"28%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" HEIGHT=110>\n"+
                        "<INPUT type=\"submit\" value=\"Search\" name=\"SearchButton\">\n"+"<INPUT type=\"hidden\" name=\"order_Form\" value=\"1\"> \n"+
                        "<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n"+"</TD>\n"+"</form>\n"+"</TR>\n"+"</TABLE>\n"+"<HR width=\"100%\" align=\"left\">\n";
        String details = buildDetail(Vendor_Id, open_order, old_order, order_text);
        header = String.valueOf(String.valueOf(header)) + String.valueOf(String.valueOf(details));
        out.println(header);
    }

    protected void printHtmlNoData(PrintWriter out, String vendor_id)
    {
        String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");
        msg = String.valueOf(String.valueOf(msg)).concat("<HTML><HEAD><TITLE>Waste Order - No Records</TITLE><HEAD>");
        msg = String.valueOf(String.valueOf(msg)).concat("<BODY>\n");
        msg = String.valueOf(String.valueOf(msg)) + "<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n&nbsp;&nbsp;&nbsp;\n<FONT FACE=\"Arial\" SIZE=4 COLOR=\"#000080\"><B>\nWaste Order Detail for Order : "+order_no+"-"+Shp_id+" \n"+"</B></FONT>\n"+"<P> </P>\n";
        msg = String.valueOf(String.valueOf(msg)).concat("<FONT SIZE=\"3\" FACE=\"Arial\"><B> No records found. </B></FONT>");
        msg = String.valueOf(String.valueOf(msg)).concat("<FONT SIZE=\"3\" FACE=\"Arial\"><B> You can go to the All Orders Page by Clicking Here</B></FONT>");
        msg = String.valueOf(String.valueOf(msg)) + "<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+"\">\n";
        msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n");
        msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n");
        msg = String.valueOf(String.valueOf(msg)) + "<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+vendor_id+"\">\n";
        msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"> </FORM>\n");
        msg = String.valueOf(String.valueOf(msg)).concat("<HR width=\"95%\" align=\"left\">\n");
        msg = String.valueOf(String.valueOf(msg)).concat("</BODY></HTML>");
        out.println(msg);
    }

    public void buildShpDetail(PrintWriter out)
    {
        data = null;
        boolean Lab_Pack = false;
        boolean Waste_material = false;
        String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");
        if(!order_no.equalsIgnoreCase("0") && !Shp_id.equalsIgnoreCase("0"))
        {
            try
            {
                WasteOrder wo = new WasteOrder(db);
                Vendor_Id = wo.getVendorId(order_no);
                HasVendor = wo.IsinVendorLogin(Logon_Id, Vendor_Id);
                result = wo.getshpidDetail(order_no, Shp_id);
                data2 = wo.getOrderHeader(order_no);
                data3 = wo.getwasteorderDetail(order_no, Shp_id);
                String where = "Select * from Waste_order_item where order_no = "+order_no+" and shipment_id = "+Shp_id;
                int count = DbHelpers.countQuery(db, where);
                if(count > 0)
                    Waste_material = true;
                String where1 = "Select lab_pack_drum_estimate, lab_pack_preferred_service_dat from Waste_shipment where order_no = "+order_no;
                int count1 = DbHelpers.countQuery(db, where1);
                if(count1 > 0)
                    Lab_Pack = true;
                String NumRecs = result.get("Number").toString();
                if(NumRecs.equalsIgnoreCase("0") && !Waste_material && !Lab_Pack)
                {
                    printHtmlNoData(out, Vendor_Id);
                    return;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("*** Error on open DB for SearchWaste ***");
                return;
            }
            if(HasVendor)
            {
                Hashtable hD = null;
                Hashtable hD4 = null;
                Hashtable hD5 = null;
                if(UpdateDone)
                {
                    msg = String.valueOf(String.valueOf(msg)).concat("<HTML><HEAD><TITLE>Waste Order Detail</TITLE>\n<SCRIPT LANGUAGE=\"JavaScript\">\n<!-- \nfunction DoLoad()\n{ \nalert(\"Your update request was successful. \");\nreturn;\n} \nfunction changed(form) {\ndocument.UpdateForm.UpdateButton.disabled=false;\n} \n//-->     \n</SCRIPT></HEAD>  \n");
                    if(Status.equalsIgnoreCase("4"))
                        msg = String.valueOf(String.valueOf(msg)).concat("<BODY onLoad=\"javascript:DoLoad()\">\n");
                    else
                        msg = String.valueOf(String.valueOf(msg)).concat("<BODY>\n");
                } else
                {
                    msg = String.valueOf(String.valueOf(msg)).concat("<HTML><HEAD><TITLE>Waste Orders</TITLE>\n<SCRIPT LANGUAGE=\"JavaScript\">\n<!-- \nfunction DoLoad()\n{ \nalert(\"Error please check your input and try again.\\nCould not update value. \");\nreturn;\n} \nfunction changed(form) {\ndocument.UpdateForm.UpdateButton.disabled=false;\n} \n//-->     \n</SCRIPT></HEAD>  \n<BODY onLoad=\"javascript:DoLoad()\">\n");
                }
                msg = String.valueOf(String.valueOf(msg)) + "<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n&nbsp;&nbsp;&nbsp;\n<FONT FACE=\"Arial\" SIZE=5 COLOR=\"#000080\"><B>\nWaste Order Detail for Order : "+order_no+"-"+Shp_id+" \n"+"</B></FONT>\n"+"<P> </P>\n";
                try
                {
                    hD4 = (Hashtable)data2.elementAt(0);
                    store_loc = hD4.get("STORAGE_LOCATION") == null ? "" : hD4.get("STORAGE_LOCATION").toString();
                    fullname = hD4.get("FULL_NAME") == null ? "" : hD4.get("FULL_NAME").toString();
                    phone = hD4.get("PHONE") == null ? "" : hD4.get("PHONE").toString();
                    email = hD4.get("EMAIL") == null ? "" : hD4.get("EMAIL").toString();
                    vendorfacid = hD4.get("VENDOR_FACILITY_ID") == null ? "" : hD4.get("VENDOR_FACILITY_ID").toString();
                    org_submit_dt = hD4.get("ORIGINAL_SUBMIT_DATE") == null ? "&nbsp;" : hD4.get("ORIGINAL_SUBMIT_DATE").toString();
                    resubmit_dt = hD4.get("RESUBMIT_DATE") == null ? "&nbsp;" : hD4.get("RESUBMIT_DATE").toString();
                    hD5 = (Hashtable)data3.elementAt(0);
                    vendor_order_no = hD5.get("VENDOR_SHIPMENT_ID") == null ? "" : hD5.get("VENDOR_SHIPMENT_ID").toString();
                    planned_pickup_date = hD5.get("PLANNED_SHIP_DATE") == null ? "" : hD5.get("PLANNED_SHIP_DATE").toString();
                    actual_pickup_date = hD5.get("ACTUAL_SHIP_DATE") == null ? "&nbsp;" : hD5.get("ACTUAL_SHIP_DATE").toString();
                    preffered_pickup_date = hD5.get("PREFERRED_SERVICE_DATE") == null ? "&nbsp;" : hD5.get("PREFERRED_SERVICE_DATE").toString();
                    disabeled = "disabled ";
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                msg = msg + "<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+
                      "\">\n"+"<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n"+"<TR>\n"+"<TD VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" WIDTH=\"40%\" HEIGHT=\"24\"><FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>General Information : </B>\n"+
                      "</FONT></TD>\n"+"<TD VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" WIDTH=\"30%\" HEIGHT=\"24\">\n"+"<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n"+"<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n"+
                      "<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+Vendor_Id+"\">\n"+"<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"></TD> </FORM>\n"+
                      "<TD VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" WIDTH=\"30%\" HEIGHT=\"24\">\n"+"<FORM method=\"POST\" name=\"UpdateForm\" action=\""+WWWHomeDirectory+
                      "/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?Session=4\">\n"+
                      "<INPUT type=\"submit\" value=\"Update\" name=\"UpdateButton\" "+disabeled+"> \n"+"<INPUT type=\"hidden\" name=\"order_no\" value=\""+order_no+
                       "\">\n"+"<INPUT type=\"hidden\" name=\"Session\" value=\"4\">\n"+"<INPUT type=\"hidden\" name=\"Shp_id\" value=\""+Shp_id+"\">\n"+
                       "<INPUT type=\"hidden\" name=\"borc\" value=\""+borc+"\">\n"+"</TD></TR></TABLE>\n"+"<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n"+
                       "<TR>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                       "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Facility :</B></FONT></div>\n"+"</td>\n"+
                       "<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+vendorfacid+
                       "</FONT></td>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                       "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Vendor Order No :</B></FONT></div>\n"+"</td>\n";

                msg = msg + "</TR>\n<TR>\n<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Storage Area :</B></FONT></div>\n</td>\n<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
                      store_loc+"</FONT></td>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Original Submittal Date :</B></FONT></div>\n"+"</td>\n"+
                      "<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+org_submit_dt+
                      "</FONT></td>\n"+"</TR>\n"+"<TR>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Requestor :</B></FONT></div>\n"+"</td>\n"+
                      "<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+fullname+
                      "</FONT></td>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Latest Re-submittal Date :</B></FONT></div>\n"+
                      "</td>\n"+"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
                      resubmit_dt+"</FONT></td>\n"+"</TR>\n"+"<TR>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Phone :</B></FONT></div>\n"+"</td>\n"+
                      "<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
                      phone+"</FONT></td>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Planned Pickup Date (mm/dd/yyyy) :</B></FONT></div>\n"+"</td>\n";

                msg = msg + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\"><INPUT type=\"text\" name=\"plan_shp_dt\" value=\""+
                      planned_pickup_date == null ? null : planned_pickup_date+"\" SIZE=\"10\" onfocus=\"changed(this.form)\"     ></FONT></td>\n";
                msg = msg + "</TR>\n<TR>\n<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Email :</B></FONT></div>\n</td>\n<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
                      email+"</FONT></td>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Preferred Pickup Date :</B></FONT></div>\n"+"</td>\n"+
                      "<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
                      preffered_pickup_date+"</FONT></td>\n"+"</TR>\n"+"<TR>\n"+"<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>&nbsp;</B></FONT></div>\n"+"</td>\n"+
                      "<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">&nbsp;</FONT></td>\n"+
                      "<TD bgcolor=\"#B0BFD0\" VALIGN=\"MIDDLE\" WIDTH=\"25%\" HEIGHT=\"34\">\n"+
                      "<div align=right><FONT FACE=\"Arial\" SIZE=\"2\"><B>Actual Pickup Date :</B></FONT></div>\n"+
                      "</td>\n"+"<TD VALIGN=\"MIDDLE\" bgcolor=\"#dddddd\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+
                      actual_pickup_date+"</FONT></td>\n"+"</TR>\n"+"</TABLE>\n"+"</FORM><HR width=\"95%\" align=\"left\"><BR>\n";
                try
                {
                    data = (Vector)result.get("Pickup");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    //System.out.println("*** Error 181 or 2***");
                    return;
                }
                if(data.size() > 0)
                {
                    if(borc.length() == 0)
                    {
                        WasteOrder wo = new WasteOrder(db);
                        try
                        {
                            borc = wo.Bulk_or_Container(order_no, Shp_id);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    try
                    {
                        if(borc.equalsIgnoreCase("C"))
                            msg = String.valueOf(String.valueOf(msg)).concat("<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Container Waste Pickup Detail</B></FONT><BR>\n");
                        else
                        if(borc.equalsIgnoreCase("B"))
                            msg = String.valueOf(String.valueOf(msg)).concat("<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Bulk Waste Pickup Detail</B></FONT><BR>\n");
                        else
                            msg = String.valueOf(String.valueOf(msg)).concat("<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Waste Pickup Detail</B></FONT><BR>\n");
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        msg = String.valueOf(String.valueOf(msg)).concat("<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Waste Pickup Detail</B></FONT><BR>\n");
                    }
                    msg = String.valueOf(String.valueOf(msg)).concat("<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\">\n");
                    for(int i = 0; i < data.size(); i++)
                    {
                        if(i % 10 == 0)
                            msg = String.valueOf(String.valueOf(msg)).concat("<tr align=\"CENTER\" >                                                                           \n  <TD bgcolor=\"#B0BFD0\" width=\"20%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>VENDOR PROFILE</strong></FONT></td>                \n  <TD bgcolor=\"#B0BFD0\" width=\"20%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong> DESCRIPTION </strong></FONT></td>                    \n  <TD bgcolor=\"#B0BFD0\" width=\"20%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>PACKAGING </strong></FONT></td>                \n  <TD bgcolor=\"#B0BFD0\" width=\"15%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>QUANTITY</strong></FONT></td>        \n  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"34\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>STATE WASTE CODE</strong></FONT></td>        \n</TR>");
                        hD = (Hashtable)data.elementAt(i);
                        String vendor_profile_id = hD.get("VENDOR_PROFILE_ID") == null ? "&nbsp;" : hD.get("VENDOR_PROFILE_ID").toString();
                        String description = hD.get("DESCRIPTION") == null ? "&nbsp;" : hD.get("DESCRIPTION").toString();
                        String packaging = hD.get("PACKAGING") == null ? "&nbsp;" : hD.get("PACKAGING").toString();
                        String quantity = hD.get("QUANTITY") == null ? "&nbsp;" : hD.get("QUANTITY").toString();
                        String statecode = hD.get("STATE_WASTE_CODE") == null ? "&nbsp;" : hD.get("STATE_WASTE_CODE").toString();
                        msg = String.valueOf(String.valueOf(msg)).concat("<tr align=\"CENTER\">\n");
                        msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"20%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+vendor_profile_id+"</FONT></td>\n";
                        msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"20%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+description+"</FONT></td>\n";
                        msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"20%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+packaging+"</FONT></td>\n";
                        msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"15%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+quantity+"</FONT></td>\n";
                        msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+statecode+"</FONT></td>\n";
                        msg = String.valueOf(String.valueOf(msg)).concat("</TR>\n");
                    }

                    msg = String.valueOf(String.valueOf(msg)).concat("</TABLE><BR>");
                }
                try
                {
                    if(Waste_material)
                    {
                        WasteOrder wo = new WasteOrder(db);
                        resultm = wo.materialdetails(order_no, Shp_id);
                        datam = (Vector)resultm.get("Material");
                        if(datam.size() > 0)
                        {
                            msg = String.valueOf(String.valueOf(msg)).concat("<FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Material Dropoff Detail</B></FONT><BR>\n");
                            msg = String.valueOf(String.valueOf(msg)).concat("<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"95%\">\n");
                            for(int i = 0; i < datam.size(); i++)
                            {
                                if(i % 10 == 0)
                                    msg = String.valueOf(String.valueOf(msg)).concat("<tr align=\"CENTER\" >                                                                           \n  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>VENDOR PROFILE</strong></FONT></td>                \n  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong> DESCRIPTION </strong></FONT></td>                    \n  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>PACKAGING </strong></FONT></td>                \n  <TD bgcolor=\"#B0BFD0\" width=\"25%\" height=\"38\"><FONT FACE=\"Arial\" SIZE=\"2\"><strong>QUANTITY</strong></FONT></td>        \n</TR>");
                                hD = (Hashtable)datam.elementAt(i);
                                String vendor_profile_id = hD.get("VENDOR_PROFILE_ID") == null ? "&nbsp;" : hD.get("VENDOR_PROFILE_ID").toString();
                                String description = hD.get("DESCRIPTION") == null ? "&nbsp;" : hD.get("DESCRIPTION").toString();
                                String packaging = hD.get("PACKAGING") == null ? "&nbsp;" : hD.get("PACKAGING").toString();
                                String quantity = hD.get("QUANTITY") == null ? "&nbsp;" : hD.get("QUANTITY").toString();
                                msg = String.valueOf(String.valueOf(msg)).concat("<tr align=\"CENTER\">\n");
                                msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+vendor_profile_id+"</FONT></td>\n";
                                msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+description+"</FONT></td>\n";
                                msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+packaging+"</FONT></td>\n";
                                msg = String.valueOf(String.valueOf(msg)) + "<TD VALIGN=\"MIDDLE\" bgcolor=\"#f4f4f4\" WIDTH=\"25%\" HEIGHT=\"24\"><FONT FACE=\"Arial\" SIZE=\"2\">"+quantity+"</FONT></td>\n";
                                msg = String.valueOf(String.valueOf(msg)).concat("</TR>\n");
                            }

                        }
                        msg = String.valueOf(String.valueOf(msg)).concat("</TABLE><BR>");
                    }
                }
                catch(Exception e)
                {
                    //System.out.println("*******************103");
                    e.printStackTrace();
                }
                try
                {
                    if(Lab_Pack)
                    {
                        WasteOrder wo = new WasteOrder(db);
                        Vector labdate = new Vector();
                        String msg2 = "";
                        try
                        {
                            labdate = wo.getlacpackdate(order_no);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        msg = String.valueOf(String.valueOf(msg)).concat("<BR><FONT COLOR=\"#000080\" FACE=\"Arial\" SIZE=\"4\"><B>Labpack Service Request</B></FONT><BR><BR>\n");
                        msg = String.valueOf(String.valueOf(msg)) + "<FONT FACE=\"Arial\" SIZE=\"2\"><B>Original Submittal Date : </B>"+org_submit_dt+"<BR><BR>";
                        msg = String.valueOf(String.valueOf(msg)) + "<B>Labpack Preferred Service Date : </B>"+labdate.elementAt(0)+"<BR><BR>";
                        msg = String.valueOf(String.valueOf(msg)) + "<B>Labpack Estimated Drums : </B>"+labdate.elementAt(1)+"<BR> </FONT><BR>";
                    }
                }
                catch(Exception e)
                {
                    //System.out.println("*******************674");
                    e.printStackTrace();
                }
                finally { }
                if(Status.equalsIgnoreCase("4"))
                    if(UpdateDone)
                    {
                        msg = String.valueOf(String.valueOf(msg)) + "<BR><FONT FACE=\"Arial\" SIZE=3>The record with Order # <B><FONT COLOR=\"#FF0000\">"+
                              order_no+"-"+UpdateShip_Id+"</FONT></B> has been updated and a email was sent to the requestor </FONT><BR> \n";
                    } else
                    {
                        msg = String.valueOf(String.valueOf(msg)).concat("<BR><FONT FACE=\"Arial\" SIZE=2><B>Error please check your input and try again<BR>\n");
                        msg = String.valueOf(String.valueOf(msg)).concat("Make sure there are no blank spaces in the Planned Shipment Date and the Vendor Order No is an integer.</B></FONT><BR>\n");
                    }
                msg = String.valueOf(String.valueOf(msg)) + "<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+"\">\n";
                msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n");
                msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n");
                msg = String.valueOf(String.valueOf(msg)) + "<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+Vendor_Id+"\">\n";
                msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"> </FORM>\n");
                msg = String.valueOf(String.valueOf(msg)).concat("<HR width=\"95%\" align=\"left\">");
                if(Vendor_Id.equalsIgnoreCase("MAD980523203"))
                    msg = String.valueOf(String.valueOf(msg)).concat("<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"Radian International Basic Ordering Agreement for Services\", between<BR>Radian  \nInternational and Supplier with the Effective Date of January 24, 2000, are incorporated into the<BR>Order, by this reference, to the same effect  \nas if such terms and conditions were printed in their<BR> entirety on the face of this Order.</FONT>");
                else
                if(Vendor_Id.equalsIgnoreCase("ARD069748192"))
                    msg = String.valueOf(String.valueOf(msg)).concat("<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"Radian International Total Chemical Management Hazardous Waste Transportation and Disposal Services Contract\" \nbetween Radian International and Supplier with the Effective Date of March 29, 2000, are incorporated into the Order, by this reference, to the same effect as if such terms and  \nconditions were printed in their entirety on the face of this Order.</FONT>");
                msg = String.valueOf(String.valueOf(msg)).concat("</BODY></HTML>");
                out.println(msg);
            } else
            {
                msg = "<HTML>\n";
                msg = String.valueOf(String.valueOf(msg)).concat("<HEAd>\n<TITLE>Waste Update - No Access</TITLE>\n</HEAd>\n<BODY>\n");
                msg = String.valueOf(String.valueOf(msg)).concat("<CENTER><FONT FACE=\"Arial\" SIZE=3><P><B>You Don't have access to this record.</B></P></FONT></CENTER>\n</BODY>\n</HTML>\n");
                out.println(msg);
            }
        } else
        {
            msg = String.valueOf(String.valueOf(msg)).concat("<CENTER><FONT FACE=\"Arial\" SIZE=3><P><B>No Records Found.<BR><BR>\n Please Check Your URL and Try Again, This Page Has Changed Recently<BR><BR>\n</B></P></FONT></CENTER>\n");
            msg = String.valueOf(String.valueOf(msg)) + "<FORM method=\"POST\" name=\"MainForm\" action=\""+WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?Session=2&openorders=openorders&vendor_id="+Vendor_Id+"\">\n";
            msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"hidden\" name=\"Session\" value=\"2\">\n");
            msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"hidden\" name=\"openorders\" value=\"openorders\">\n");
            msg = String.valueOf(String.valueOf(msg)) + "<INPUT type=\"hidden\" name=\"vendor_id\" value=\""+Vendor_Id+"\">\n";
            msg = String.valueOf(String.valueOf(msg)).concat("<INPUT type=\"submit\" value=\"All Orders\" name=\"SearchButton\"> </FORM>\n");
            msg = String.valueOf(String.valueOf(msg)).concat("<HR width=\"95%\" align=\"left\">\n");
            msg = String.valueOf(String.valueOf(msg)).concat("</FONT></BODY>\n");
            msg = String.valueOf(String.valueOf(msg)).concat("</HTML>\n");
            out.println(msg);
        }
    }

    public void buildUpdate(PrintWriter out)
    {
        String headeru = "";
        WasteOrder wo = new WasteOrder(db);
        Date CurrentDate = new Date();
        try
        {
            Vendor_Id = wo.getVendorId(order_no);
            HasVendor = wo.IsinVendorLogin(Logon_Id, Vendor_Id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(HasVendor)
        {
            if(vendor_shp_id.length() > 0)
            {
                if(plan_shp_dt.length() == 10)
                {
                    Date d = new Date();
                    Calendar cal = Calendar.getInstance();
                    String cdate = (cal.get(2) + 1)+"/"+cal.get(5)+"/"+cal.get(1);
                    try
                    {
                        //System.out.println("\nThe Dates are  - Today"+Date.parse(cdate)+"     Planned Shipment"+Date.parse(plan_shp_dt));
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        UpdateDone = false;
                        buildShpDetail(out);
                        return;
                    }
                    long test = Date.parse(cdate) - Date.parse(plan_shp_dt);
                    try
                    {
                        if(Date.parse(cdate) <= Date.parse(plan_shp_dt))
                        {
                            wo.insVendorshpId(db, vendor_shp_id, plan_shp_dt, order_no, Shp_id);
                            UpdateDone = true;
                        }
                        else
                        {
                            UpdateDone = false;
                            buildShpDetail(out);
                            return;
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        UpdateDone = false;
                        buildShpDetail(out);
                        return;
                    }
                } else
                if(plan_shp_dt.length() == 0)
                {
                    try
                    {
                        String plan_shp_dt1 = "null";
                        wo.insVendorshpId(db, vendor_shp_id, plan_shp_dt1, order_no, Shp_id);
                        UpdateDone = true;
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        UpdateDone = false;
                        buildShpDetail(out);
                        return;
                    }
                } else
                {
                    UpdateDone = false;
                    buildShpDetail(out);
                    return;
                }
                DBResultSet dbrs = null;
                try
                {
                    String logonId = null;
                    String Personnel_id = null;
                    ResultSet rs = null;
                    String query = "select REQUESTER_ID from waste_order where order_no ="+order_no;
                    dbrs = db.doQuery(query);
                    for(rs = dbrs.getResultSet(); rs.next();)
                        Personnel_id = BothHelpObjs.makeBlankFromNull(rs.getString("REQUESTER_ID"));

                    query = "select logon_id from personnel where personnel_id ="+Personnel_id;
                    dbrs = db.doQuery(query);
                    for(rs = dbrs.getResultSet(); rs.next();)
                        logonId = BothHelpObjs.makeBlankFromNull(rs.getString("logon_id"));

                    String esub = "Your Waste Vendor has updated the planned shipment date.";
                    String emsg = "The waste vendor has updated the planned shipment date of your waste pickup request.\n";
                    emsg = emsg + "The Planned pick up date is now "+plan_shp_dt+" for the shipment Id "+Shp_id+" of the waste order "+order_no;
                    Integer user = new Integer(Personnel_id);
                    HelpObjs.sendMail(db, esub, emsg, user.intValue(), false);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    dbrs.close();
                }
            } else
            {
                UpdateDone = false;
                buildShpDetail(out);
                return;
            }
            UpdateShip_Id = Shp_id;
            buildShpDetail(out);
        } else
        {
            UpdateDone = false;
            headeru = "<HTML>\n<HEAd>\n<TITLE>Waste Update - No Access</TITLE>\n</HEAd>\n<BODY>\n";
            headeru = String.valueOf(String.valueOf(headeru)).concat("<FONT FACE=\"Arial\" SIZE=3><B><P><B>Error!! You don't have acess to update this order</B></P>");
            headeru = String.valueOf(String.valueOf(headeru)).concat("</FONT></Body>\n");
            headeru = String.valueOf(String.valueOf(headeru)).concat("</HTML>\n");
            out.println(headeru);
        }
    }

    public String buildDetail(String Vendor_Id, String open_order, String old_order, String order_text)
    {
        data = null;
        Vector data_borc = new Vector();
        Vector resultHv = new Vector();
        Hashtable data_dates = new Hashtable();
        Hashtable resultH = new Hashtable();
        Hashtable temp11 = new Hashtable();
        Hashtable temp12 = new Hashtable();
        String checked = "";
        String headerw = "";
        String msgout = "";
        String compName = "";
        if(Vendor_Id.length() <= 1)
        {
            //System.out.println("*******************        437");
            msg = String.valueOf(String.valueOf(msg)).concat("<center><font face=Arial size=3><p><B>Please select and hit search.</B></P></font></center>\n");
            msgout = String.valueOf(String.valueOf(headerw)) + String.valueOf(String.valueOf(msg));
            return msgout;
        }
        try
        {
            WasteOrder wo = new WasteOrder(db);
            resultHv = wo.getorderDetail(Vendor_Id, open_order, old_order, order_text);
            recs = resultHv.size();
            if(recs == 0 || !open_order.equalsIgnoreCase("openorders") && !old_order.equalsIgnoreCase("oldorders"))
            {
                msg = "<font face=Arial size=3><BR><p><b><center> No records found for this Search  </center></b></P></font>\n";
                msgout = String.valueOf(String.valueOf(headerw)) + String.valueOf(String.valueOf(msg));
                String s = msgout;
                String s2 = s;
                return s2;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("*** Error on open DB for  Order Detail***");
            msg = "<font face=Arial size=3><BR><P><b><center> Error Please Check Your Input and Try Again</center></b></P></font>\n";
            msgout = String.valueOf(String.valueOf(headerw)) + String.valueOf(String.valueOf(msg));
            String s1 = msgout;
            String s3 = s1;
            return s3;
        }
        try
        {
            Hashtable hD = null;
            Hashtable hD2 = null;
            String WWWHomeDirectory = bundle.getString("WEBS_HOME_WWWS");
            String url = WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?";
            String qtmp = new String("");
            msg = String.valueOf(String.valueOf(msg)).concat("<table BORDER=\"0\" CELLPADDING=\"1\" WIDTH=\"100%\">\n");
            for(int K = 0; K < resultHv.size(); K++)
            {
                resultH = (Hashtable)resultHv.elementAt(K);
                temp11 = (Hashtable)resultH.get("SHIP_DETAILS");
                data = (Vector)temp11.get("SHIP_DETAILS_"+K);
                if(K % 10 == 0)
                    msg = String.valueOf(String.valueOf(msg)).concat("<font face=Arial size=2>\n<tr align=\"center\">                                                                           \n  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>ORDER #</font></strong></td>\n  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>CONTAINER(C) <B>/</B> BULK(B)</font></strong></td>\n  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>VENDOR ORDER NO</font></strong></td>\n  <td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>PREFERRED PICKUP DATE</font></strong></td>\n  <td bgcolor=\"#B0BFD0\" width=\"7%\" height=\"38\"><strong><font face=Arial size=2>PLANNED PICKUP DATE\n  <font face=Arial size=1>(MM/DD/YYYY)</font></font></strong></td>\n  <td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>ACTUAL PICKUP DATE</font></strong></td>\n  <td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>CANCELLED</font></strong></td>\n</TR>\n");
                for(int i = 0; i < data.size(); i++)
                {
                    hD = (Hashtable)data.elementAt(i);
                    String Shp_id = hD.get("SHIPMENT_ID") == null ? "&nbsp;" : hD.get("SHIPMENT_ID").toString();
                    String order_no = hD.get("ORDER_NO") == null ? "&nbsp;" : hD.get("ORDER_NO").toString();
                    String Shp_idd = order_no+"-"+Shp_id;
                    String vendor_shp_id = hD.get("VENDOR_SHIPMENT_ID") == null ? "" : hD.get("VENDOR_SHIPMENT_ID").toString();
                    String plan_shp_dt = hD.get("PLANNED_SHIP_DATE") == null ? "" : hD.get("PLANNED_SHIP_DATE").toString();
                    String actual_shp_dt = hD.get("ACTUAL_SHIP_DATE") == null ? "&nbsp;" : hD.get("ACTUAL_SHIP_DATE").toString();
                    String cancel_dt = hD.get("CANCEL_DATE") == null ? "&nbsp;" : hD.get("CANCEL_DATE").toString();
                    String BulkorCont = hD.get("BULK_CONT") == null ? "&nbsp;" : hD.get("BULK_CONT").toString();
                    String preffered_pickup_date = hD.get("PREFERRED_SERVICE_DATE") == null ? "&nbsp;" : hD.get("PREFERRED_SERVICE_DATE").toString();
                    String Color = "";
                    if(i % 2 == 0)
                        Color = "bgcolor=\"#dddddd\"";
                    qtmp = "order_no="+order_no+"&Shp_id="+Shp_id+"&borc="+BulkorCont;
                    url = String.valueOf(String.valueOf(url)) + String.valueOf(String.valueOf(qtmp));
                    msg = String.valueOf(String.valueOf(msg)).concat("<tr align=\"center\">\n");
                    msg = msg + "<td "+Color+" width=\"7%\"><a href=\""+url+"\">"+Shp_idd+"</a></td>\n";
                    msg = msg + "<td "+Color+" width=\"4%\">"+BulkorCont+"</td>\n";
                    msg = msg + "<td "+Color+" width=\"7%\">"+(vendor_shp_id.length() <= 0 ? "&nbsp;" : vendor_shp_id)+"</td>\n";
                    msg = msg + "<td "+Color+" width=\"10%\">"+preffered_pickup_date+"</td>\n";
                    msg = msg + "<td "+Color+" width=\"7%\">"+(plan_shp_dt.length() <= 0 ? "&nbsp;" : plan_shp_dt)+"</td>\n";
                    msg = msg + "<td "+Color+" width=\"10%\">"+actual_shp_dt+"</td>\n";
                    msg = msg + "<td "+Color+" width=\"10%\">"+cancel_dt+"</td>\n";
                    msg = String.valueOf(String.valueOf(msg)).concat("</TR>\n");
                    url = WWWHomeDirectory+"/tcmIS/servlet/radian.web.servlets.dana.DanaWasteVendorUpdate?";
                    checked = "";
                    Shp_id = "";
                }

            }

            msg = String.valueOf(String.valueOf(msg)).concat("</font></table><BR>\n");
            msg = String.valueOf(String.valueOf(msg)).concat("<HR>");
            if(Vendor_Id.equalsIgnoreCase("MAD980523203"))
                msg = String.valueOf(String.valueOf(msg)).concat("<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"Radian International Basic Ordering Agreement for Services\", between<BR>Radian  \nInternational and Supplier with the Effective Date of January 24, 2000, are incorporated into the<BR>Order, by this reference, to the same effect  \nas if such terms and conditions were printed in their<BR> entirety on the face of this Order.</FONT>");
            else
            if(Vendor_Id.equalsIgnoreCase("ARD069748192"))
                msg = String.valueOf(String.valueOf(msg)).concat("<FONT FACE=\"Arial\" SIZE=2>The terms and conditions of the \"Radian International Total Chemical Management Hazardous Waste Transportation and Disposal Services Contract\" \nbetween Radian International and Supplier with the Effective Date of March 29, 2000, are incorporated into the Order, by this reference, to the same effect as if such terms and  \nconditions were printed in their entirety on the face of this Order.</FONT>");
            msg = String.valueOf(String.valueOf(msg)).concat("</Body>\n");
            msg = String.valueOf(String.valueOf(msg)).concat("</html>\n");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        msgout = String.valueOf(String.valueOf(headerw)) + String.valueOf(String.valueOf(msg));
        return msgout;
    }
}