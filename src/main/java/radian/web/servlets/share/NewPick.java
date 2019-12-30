package radian.web.servlets.share;

import javax.servlet.http.*;
import java.util.*;
import java.lang.Integer;
import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import java.sql.ResultSet;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */


public class NewPick
{
    ServerResourceBundle bundle = null;
    TcmISDBModel db = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector data = null;
    String checkednon = "";
    //MsdsQuality  hubObj = null;

    boolean completeSuccess = true ;
    boolean noneToUpd = true ;
    Vector synch_data = null;

    String soundmatServlet = "";
    String nematid_Servlet = "";
    String Label_Servlet = "";

    HubInvenReconcilTables  hubObj = null;

    //Nawaz 06-27-02
    protected boolean allowupdate;
    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    public NewPick(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }
    private Vector SynchPickServerData( HttpServletRequest request,Vector in_data)
    {
        Vector new_data = new Vector();
        Hashtable sum = ( Hashtable)( in_data.elementAt(0));
        int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();
        new_data.addElement(sum);

        try
        {
            for ( int i = 1 ; i< count+1 ; i++)
            {
                Hashtable hD = new Hashtable();
                hD = ( Hashtable)( in_data.elementAt(i));

                String rev_qty = "";
                try{ rev_qty   = request.getParameter(("qty_picked"+i)).toString();}catch (Exception e){ rev_qty  = "";}

                if ( (rev_qty.length() > 0) && !rev_qty.equalsIgnoreCase(hD.get("QUANTITY").toString()) )
                {
                //System.out.println("Before : "+hD.get("QUANTITY").toString()+" After :"+rev_qty+"");

                hD.remove("QUANTITY");
                hD.put("QUANTITY", rev_qty );

                hD.remove("DOSTATUSUPDATE");
                hD.put("DOSTATUSUPDATE","Yes");

                }
                new_data.addElement(hD);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new_data;
    }

    public StringBuffer buildPickDetails(Vector data, String ItemId, String prnum, String linenum, String Hub, String sortby, String category, String MrQty ) throws Exception
    {
    StringBuffer Msg = new StringBuffer();
    nematid_Servlet = bundle.getString("HUB_INVEN_RECONCIL");

    DBResultSet dbrs = null;
    ResultSet rs = null;

    String facility = "";
    String workArea = "";

    String bquery = "select FACILITY_NAME,APPLICATION_DESC from mr_count_view where pr_number = "+prnum+" and line_item ="+linenum+"";
    try
    {
        dbrs = db.doQuery(bquery);
        rs=dbrs.getResultSet();
        while (rs.next())
        {
           facility = rs.getString("FACILITY_NAME");
           workArea = rs.getString("APPLICATION_DESC");
        }
    }
    catch (Exception e)
    {
    e.printStackTrace();
    throw e;
    } finally
    {
     dbrs.close();
    }


    Msg.append("<HTML>\n");
    Msg.append("<HEAD>\n");
    Msg.append("<title>New Pick for Reconciliation of Item "+ItemId+" </title>\n");
    Msg.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
    Msg.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
    Msg.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
    Msg.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");

    Msg.append(printJavaScripts());

    Msg.append("<BODY>\n");
    Msg.append("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
    Msg.append("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
    Msg.append("</DIV>\n");
    Msg.append("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

    Msg.append(radian.web.HTMLHelpObj.PrintTitleBar("<B>New Pick for Reconciliation of Item "+ItemId+" </B>\n"));

    Msg.append("<FORM METHOD=\"POST\" name=\"NewPick\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+nematid_Servlet+"\">\n");
    Msg.append("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\" CLASS=\"moveup\">\n");
    Msg.append("<TR><TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"whiteb\"><B>MR-Line :&nbsp;&nbsp;</B></TD><TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"whiteb\">\n");
    Msg.append(""+prnum+" - "+linenum+"\n");
    Msg.append("</TD>\n");
    Msg.append("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"whiteb\"><B>Facility :&nbsp;&nbsp;</B></TD>\n");
    Msg.append("<TD WIDTH=\"10%\" CLASS=\"whiteb\">"+facility+"</TD>\n");

    Msg.append("</TR>\n<TR>\n");
    Msg.append("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"blueb\"><B>MR Quantity :&nbsp;&nbsp;<B>\n</TD>\n");
    Msg.append("<TD WIDTH=\"10%\" CLASS=\"blueb\">"+MrQty+"</TD>\n");
    Msg.append("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"blueb\"><B>Work Area :&nbsp;&nbsp;<B>\n</TD>\n");
    Msg.append("<TD WIDTH=\"10%\" CLASS=\"blueb\">"+workArea+"</TD>\n");
    Msg.append("</TR>\n");

    Msg.append("</TABLE>\n<BR><BR>");

    try
    {
        Hashtable summary = new Hashtable();
        summary = (Hashtable)data.elementAt(0);
        int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;

        int vsize = data.size();
        //System.out.println("Vector Size  : "+ vsize  );

        //start table #3
        Msg.append("<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");

        String error_item ;
        int toalQty = 0;


        int i = 0;  //used for detail lines
        for (int loop  = 0 ; loop  < total  ; loop++)
        {
           i++;
           boolean createHdr = false;

           if ( loop%10 == 0 )
           { createHdr = true;
           }

            if ( createHdr )
            {
                Msg.append("<tr align=\"center\">\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Mfg Lot</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Item ID</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Bin</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Receipt ID</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Company</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Available Qty</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Pick Qty</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Qty Picked</TH>\n");
                Msg.append("<TH  width=\"10%\"  height=\"38\">Date Picked mm/dd/yyyy</TH>\n");
                Msg.append("</TR>\n");
            }

            Hashtable hD = new Hashtable();
            hD = (Hashtable) data.elementAt(i);

            // get main information
            String Mfg_lot         = (hD.get("MFG_LOT")==null?"&nbsp;":hD.get("MFG_LOT").toString());
            String bin          = (hD.get("BIN")==null?"&nbsp;":hD.get("BIN").toString());
            String available_qty  = (hD.get("QUANTITY_AVAILABLE")==null?"&nbsp;":hD.get("QUANTITY_AVAILABLE").toString());
            String receipt_id = (hD.get("RECEIPT_ID")==null?"&nbsp;":hD.get("RECEIPT_ID").toString());
            String item_id          = (hD.get("ITEM_ID")==null?"&nbsp;":hD.get("ITEM_ID").toString());
            String company_id          = (hD.get("COMPANY_ID")==null?"&nbsp;":hD.get("COMPANY_ID").toString());
            String pick_qty  = (hD.get("PICK_QTY")==null?"&nbsp;":hD.get("PICK_QTY").toString());
            String pickedFlag  = (hD.get("PICKABLE")==null?"&nbsp;":hD.get("PICKABLE").toString());
            String prevQtyPicked          = (hD.get("OLD_PICK_QTY")==null?"&nbsp;":hD.get("OLD_PICK_QTY").toString());

            toalQty += Integer.parseInt(available_qty);

            String Color = " ";
            if (i%2==0)
            {
                Color ="CLASS=\"Inblue";
            }
            else
            {
                Color ="CLASS=\"Inwhite";
            }
            //
            Msg.append("<tr align=\"center\">\n");

            Msg.append("<td "+Color+"\" width=\"10%\">"+Mfg_lot+"</td>\n");
            Msg.append("<td "+Color+"\" width=\"10%\">"+item_id+"</td>\n");
            Msg.append("<td "+Color+"\" width=\"10%\">"+bin+"</td>\n");
            Msg.append("<td "+Color+"\" width=\"10%\">"+receipt_id+"</td>\n");
            Msg.append("<td "+Color+"\" width=\"10%\">"+company_id+"</td>\n");

            if ("Y".equalsIgnoreCase(pickedFlag))
            {
             Msg.append("<td "+Color+"\" width=\"10%\">"+available_qty+"</td>\n");
             Msg.append("<td "+Color+"\" width=\"10%\">"+pick_qty+"</td>\n");
             Msg.append("<td "+Color+"\" width=\"10%\" >\n\n");
             Msg.append("<input type=\"text\" CLASS=\"DETAILS\" name=\"qty_picked"+i+"\"  value=\"\" maxlength=\"30\" size=\"6\" onChange=\"CheckPickValue(this,"+i+")\"></td>\n");
             Msg.append("<td "+Color+"\" width=\"10%\">\n\n");
             Msg.append("<input type=\"text\" CLASS=\"DETAILS\" name=\"date_picked"+i+"\"  value=\"\" maxlength=\"30\" size=\"15\" onChange=\"CheckDatePick(this,"+i+")\"></td>\n");

            }
            else
            {
              Msg.append("<td "+Color+"\" width=\"10%\">&nbsp;</td>\n");
              Msg.append("<td "+Color+"\" width=\"10%\">&nbsp;</td>\n");

              Msg.append("<td "+Color+"\" width=\"10%\" >\n\n");
              Msg.append("<input type=\"hidden\" name=\"qty_picked"+i+"\"  value=\""+prevQtyPicked+"\">"+prevQtyPicked+"</td>\n");
              Msg.append("<td "+Color+"\" width=\"10%\">&nbsp;</TD>\n\n");
            }

            Msg.append("<input type=\"hidden\" name=\"available_qty"+i+"\" value=\""+available_qty+"\">\n");
            //Msg.append("<input type=\"hidden\" name=\"item_id\" value=\""+(ItemId==null?null:ItemId)+"\">\n");
            //Msg.append("<input type=\"hidden\" name=\"item_id"+i+"\" value=\""+(ItemId==null?null:ItemId)+"\">\n");

            Msg.append("</TR>\n");
        }

        Msg.append("<TR ID=\"Totalsrow\" >\n");
        Msg.append("<td COLSPAN=\"7\" ALIGN=\"RIGHT\" CLASS=\"greenb\"><B>Totals: </B></td>\n");

        Msg.append("<td ALIGN=\"CENTER\" CLASS=\"greenb\"></td>\n");
        Msg.append("<td ALIGN=\"CENTER\" CLASS=\"greenb\">&nbsp;</td>\n");
        Msg.append("</TR>\n");

        Msg.append("<TR>\n");

        // Search Text
        Msg.append("<TD WIDTH=\"50%\" COLSPAN=\"4\" ALIGN=\"CENTER\">\n");
        //Msg.append("<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Search:</B>&nbsp;\n");
        Msg.append("   <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"OK\" onclick= \"return PickNew(this)\" NAME=\"SearchButton\">&nbsp;\n");

        Msg.append("</TD>\n");

        Msg.append("<TD WIDTH=\"50%\" COLSPAN=\"5\" ALIGN=\"CENTER\">\n");
        //Msg.append("<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Search:</B>&nbsp;\n");
        Msg.append("   <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Cancel\" onclick= \"return cancel()\" NAME=\"SearchButton\">&nbsp;\n");

        //Msg.append("   <INPUT TYPE=\"button\" VALUE=\"Cancel\" onclick= \"cancel()\" NAME=\"SearchButton\">&nbsp;\n");

        Msg.append("</TD>\n");
        Msg.append("</TR>");
        Msg.append("</TABLE>\n");

        Msg.append("<input type=\"hidden\" name=\"Total_number\" value=\""+total+"\">\n");
        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"pr_number\" VALUE=\""+prnum+"\">\n");
        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"line_item\" VALUE=\""+linenum+"\">\n");
        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"NItemId\" VALUE=\""+ItemId+"\">\n");
        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"Category\" VALUE=\""+category+"\">\n");
        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"SortBy\" VALUE=\""+sortby+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"HubName\" value=\""+Hub+"\">\n");
        Msg.append("<input type=\"hidden\" name=\"MrQty\" value=\""+MrQty+"\">\n");

        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"UPDATE\">\n");
        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\"updatePick\">\n");
        Msg.append("<INPUT TYPE=\"hidden\" NAME=\"CancelUserAction\" VALUE=\"\">\n");

        Msg.append("</FORM>\n");
        Msg.append("</BODY></HTML>\n");
    }
    catch (Exception e)
    {
        e.printStackTrace();
        throw e;
    }
        return Msg;
}
    protected StringBuffer printJavaScripts()
    {
    StringBuffer Msglt = new StringBuffer();

    //Msglt.append(radian.web.JSHelpObj.buildCSS());

    Msglt.append("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    Msglt.append("<!--\n");

    Msglt.append("  var submitcount=0; \n");
    Msglt.append("  var updatecount=0; \n");
    Msglt.append("  var RevOnHandTotal=0*1; \n");

    Msglt.append(radian.web.JSHelpObj.SubmitOnlyOnce());
    Msglt.append(radian.web.JSHelpObj.checkInteger());


    Msglt.append("function CheckDatePick(entered,LineNum){\n");
    Msglt.append(" var allClear = 0;\n");
    Msglt.append("  eval( pickDateO = \"window.document.NewPick.date_picked\" + LineNum.toString() )\n");
    Msglt.append("   if ( (eval(pickDateO.toString())).value.length == 10 )\n");
    Msglt.append("   {\n");
    Msglt.append("       if ( checkdate((eval(pickDateO.toString()))) == false )\n");
    Msglt.append("       {\n");
    Msglt.append("           allClear = 1;\n");
    Msglt.append("       }\n");
    Msglt.append("   }\n");
    Msglt.append("   else \n");
    Msglt.append("   {\n");
    Msglt.append("       allClear = 1;\n");
    Msglt.append("   }\n");
    Msglt.append("   if (allClear > 0 )\n");
    Msglt.append("   {\n");
    Msglt.append("       alert ( \"Invalid Pick Date\\nPlease Enter a Valid Date.\");\n");
    Msglt.append("       testqty12  =  eval(\"window.document.NewPick.date_picked\" + LineNum.toString() );\n");
    Msglt.append("       testqty12.value = \"\";\n");
    Msglt.append("  }\n");
    Msglt.append("}\n");


    Msglt.append("function CheckPickValue(entered,LineNum){\n");

    Msglt.append("                        eval( testqty = \"window.document.NewPick.qty_picked\" + LineNum.toString() )\n");
    Msglt.append("                        var v = (eval(testqty.toString())).value;\n");
    Msglt.append("                        eval( avaiqtyO = \"window.document.NewPick.available_qty\" + LineNum.toString() )\n");
    Msglt.append("                        var avaiqty = (eval(avaiqtyO.toString())).value;\n");

    Msglt.append("                        if ( !(isInteger(v)) )\n");
    Msglt.append("                        {\n");
    Msglt.append("                            alert ( \"Invalid Number\\n Please Enter a Valid Number.\");\n");
    Msglt.append("                            testqty12  =  eval(\"window.document.NewPick.qty_picked\" + LineNum.toString() );\n");
    Msglt.append("                            testqty12.value = \"0\";\n");
    Msglt.append("                        }\n");
    Msglt.append("                        else\n{\n");
    Msglt.append("                               if ( v*1 > avaiqty*1 )\n");
    Msglt.append("                               {\n");
    Msglt.append("                                 alert ( \"Invalid Quantity\\n Please Enter a Qty less than or equal to the Available Qty.\");\n");
    Msglt.append("                                 testqty12  =  eval(\"window.document.NewPick.qty_picked\" + LineNum.toString() );\n");
    Msglt.append("                                 testqty12.value = \"0\";\n");
    Msglt.append("                               }\n");
    Msglt.append("                        else\n{\n");
    Msglt.append("                               updatecount++; \n");
    Msglt.append("                              }\n");
    Msglt.append("                        }\n");

    Msglt.append("  var total1  =  window.document.NewPick.Total_number.value;\n");
    Msglt.append("  var Total = 0*1;\n");
    Msglt.append("  for ( var p = 0 ; p < total1 ; p ++)\n");
    Msglt.append("  {\n");
    Msglt.append("   eval ( line_num = p + 1 );\n");
    Msglt.append("   eval( actOnHandO = \"window.document.NewPick.qty_picked\" + line_num.toString() )\n");
    Msglt.append("   var actOnHand = (eval(actOnHandO.toString())).value;\n");
    Msglt.append("   actvalue = actOnHand*1;\n");
    Msglt.append("   eval (Total = Total + actvalue);\n");
    Msglt.append("  }\n");

    Msglt.append("   row_name = eval(\"Totalsrow\");\n");

    Msglt.append("  var mrQty  =  window.document.NewPick.MrQty.value;\n");
    Msglt.append("  if ( Total*1 > mrQty*1 )\n");
    Msglt.append("  {\n");
    Msglt.append("    alert ( \"Picks Exceed MR Qunatity\\nPlease Enter a Valid Number.\");\n");
    Msglt.append("    testqty12  =  eval(\"window.document.NewPick.qty_picked\" + LineNum.toString() );\n");
    Msglt.append("    testqty12.value = \"\";\n");
    Msglt.append("   row_name.cells(1).innerHTML = \" \";\n");
    Msglt.append("  }\n");
    Msglt.append("  else\n");
    Msglt.append("  {\n");
    Msglt.append("   row_name.cells(1).innerHTML = \"<B>\"+Total+\"</B>\";\n");
    Msglt.append("  }\n");
    Msglt.append("  }\n");

    Msglt.append("function PickNew(entered){\n");
    Msglt.append("   if ( eval(updatecount.toString()) < 1 )\n");
    Msglt.append("   {\n");
    Msglt.append("     alert(\"Please enter data and select Ok before Update\");\n");
    Msglt.append("     return false;\n");
    Msglt.append("   }\n");

    Msglt.append(" try {\n");
    Msglt.append(" var target = document.all.item(\"TRANSITPAGE\");\n");
    Msglt.append(" target.style[\"display\"] = \"\";\n");

    Msglt.append("  var target1 = document.all.item(\"MAINPAGE\");\n");
    Msglt.append("  target1.style[\"display\"] = \"none\";\n");

    Msglt.append("  } catch (ex) {}\n");

    /*Msglt.append("  window.document.NewPick.UserAction.value = \"UPDATE\";\n");
    Msglt.append("  window.document.NewPick.Action.value = \"UPDATE\";\n");*/

    Msglt.append("  window.document.NewPick.CancelUserAction.value = \"No\";\n");
    Msglt.append("     return true;\n");

    Msglt.append("}\n");

    Msglt.append("function cancel()\n");
    Msglt.append("{\n");
    Msglt.append("  window.document.NewPick.CancelUserAction.value = \"Yes\";\n");
    Msglt.append("  return true;\n");
    Msglt.append("}\n");

    Msglt.append("function refreshOpener()\n");
    Msglt.append("{\n");
    Msglt.append("window.opener.history.go(0);\n");
    Msglt.append("window.close();\n");
    Msglt.append("}\n");

    Msglt.append(radian.web.JSHelpObj.testDateScript());

    Msglt.append("// -->\n </SCRIPT></HEAD>\n");

    return Msglt;
    }
}