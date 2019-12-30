package radian.web.servlets.share;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.InvoiceData;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;


/**
 * Title:        TcmIS
 * Description:  Hub Receiving Page
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corp
 * @author
 * @version 1.0
 *
 * 08-12-03 - A little bit of code cleanup.
 * 08-29-03 - Removed /var/htdocs from the code and www.tcmis.com
 * 01-28-04 Making relative URLs to go to the property file to get hosturl
 * 02-10-04 Removed hardcoded file location
 * 03-14-06 -Removed StringBuffer where possible. Writting directly to a file instead of buffering it into a stringBuffer first.
 */

public class InvoiceCreator
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private boolean intcmIsApplication = false;

  public InvoiceCreator( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  public void doResult( HttpServletRequest request,HttpServletResponse response,HttpSession mySession )
     throws ServletException,IOException
  {
    PrintWriter out=response.getWriter();
    response.setContentType( "text/html" );
    Hashtable initialData=null;

    PersonnelBean personnelBean = (PersonnelBean) mySession.getAttribute("personnelBean");
		if (personnelBean !=null)
		{
			 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			 intcmIsApplication = false;
			 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
				intcmIsApplication = true;
			 }
		}

    String personnelID= mySession.getAttribute( "PERSONNELID" ) == null ? "" : mySession.getAttribute( "PERSONNELID" ).toString();

    String action=request.getParameter( "Action" );
    if ( action == null )
      action="New";
    try
    {
      //System.out.println( "------------ action: " + action );
      //first set initial data in mySession (global data) if not already set
      String initialDataLoaded=mySession.getAttribute( "INITIAL_DATA_LOADED" ) == null ? "" : mySession.getAttribute( "INITIAL_DATA_LOADED" ).toString();
      if ( !initialDataLoaded.equalsIgnoreCase( "Yes" ) )
      {
        InvoiceData invoiceD=new InvoiceData( db );
        mySession.setAttribute( "INITIAL_DATA",invoiceD.getInvoiceInitialData( personnelID ) );
        mySession.setAttribute( "INITIAL_DATA_LOADED","Yes" );
      }

        //next get initial data from mySession (global data)
        initialDataLoaded=mySession.getAttribute( "INITIAL_DATA_LOADED" ) == null ? "" : mySession.getAttribute( "INITIAL_DATA_LOADED" ).toString();
        if (initialDataLoaded.equalsIgnoreCase("Yes"))
        {
          initialData  = (Hashtable)mySession.getAttribute("INITIAL_DATA");
        }
        else
        {
          InvoiceData invoiceD = new InvoiceData(db);
          initialData = invoiceD.getInvoiceInitialData(personnelID);
          mySession.setAttribute("INITIAL_DATA",initialData);
          mySession.setAttribute("INITIAL_DATA_LOADED","Yes");
        }

        if ( action.equalsIgnoreCase("New")) {
          //build display
          buildDisplayData(action,initialData,getDefaultDisplay(),out);
        }else if ( action.equalsIgnoreCase("generateR")) {
          Hashtable userSelectedData = getUserDisplay(request);
          userSelectedData.put("PO_REQUIRED",request.getParameter("PoRequired"));
          getReportFields(userSelectedData);
          //get combo box desc from ID
          getComboBoxDescFromID(userSelectedData,initialData);
          //save user selected data for invoice detail
          mySession.setAttribute("USER_SELECTED_DATA",userSelectedData);
          buildDisplayData(action,initialData,userSelectedData,out);
        }else if ( action.equalsIgnoreCase("saveT")) {
          Hashtable userSelectedData = getUserDisplay(request);
          //insert record into cost_report_template
          InvoiceData invoiceD = new InvoiceData(db);
          invoiceD.saveTemplate(userSelectedData,personnelID);
          //add the new template to list
          String templateList = (String)initialData.get("TEMPLATE_LIST");
          if (templateList.length() > 0 ) {
            String tmpName = (String)userSelectedData.get("TEMPLATE_NAME");
            StringTokenizer str = new StringTokenizer(templateList,";");
            boolean containTemplate = false;
            while (str.hasMoreElements()) {
              if (str.nextElement().toString().equals(tmpName)) {
                containTemplate = true;
                break;
              }
            }
            //if list does not contain template name then add template to list
            if (!containTemplate) {
              templateList += ";"+tmpName;
            }
          }else {
            templateList = (String)userSelectedData.get("TEMPLATE_NAME");
          }
          initialData.put("TEMPLATE_LIST",templateList);
          //build display
          buildDisplayData(action,initialData,userSelectedData,out);
        }else if ( action.equalsIgnoreCase("openT")) {
          Hashtable userSelectedData = getDefaultDisplay();
          //get template from cost_report_template
          InvoiceData invoiceD = new InvoiceData(db);
          invoiceD.openTemplate(userSelectedData,personnelID,request.getParameter("TemplateName"));
          //build display
          buildDisplayData(action,initialData,userSelectedData,out);
        }else if (action.equalsIgnoreCase("invoiceDetail")) {
          Hashtable userSelectedData = (Hashtable)mySession.getAttribute("USER_SELECTED_DATA");
          InvoiceData invoiceD = new InvoiceData(db);
          Vector reportDataV = invoiceD.getReportDataNew(userSelectedData,personnelID);
          //save report data for xls report
          mySession.setAttribute("REPORT_DATA",reportDataV);
          generateHtmlReport(action,reportDataV,userSelectedData,personnelID,out);
        }else if (action.equalsIgnoreCase("generateXlsReport")) {
          Hashtable userSelectedData = (Hashtable)mySession.getAttribute("USER_SELECTED_DATA");
          Vector reportDataV = (Vector)mySession.getAttribute("REPORT_DATA");
          out.println("<HTML><HEAD>\n");
          out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+generateXlsReport(reportDataV,userSelectedData,personnelID)+"\">\n");
          out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
          out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
          out.println("<TITLE>Invoice Summary XLS Report</TITLE>\n");
          out.println("</HEAD>  \n");
          out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
          out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
          out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
          out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
          out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
          out.println("</CENTER>\n");
          out.println("</BODY></HTML>    \n");
        }else if (action.equalsIgnoreCase("addCharge")) {
          /*
          Enumeration enu = request.getParameterNames();
          while (enu.hasMoreElements()) {
            System.out.println("----- why : " +enu.nextElement().toString());
          }*/
          //report data
          String sqlFields = request.getParameter("sqlFields");
          String sqlFieldDelim = request.getParameter("sqlFieldDelim");
          StringTokenizer st = new StringTokenizer(sqlFields,sqlFieldDelim);
          Vector sqlFieldV = new Vector(51);
          Vector sqlValueV = new Vector(51);
          while (st.hasMoreTokens()) {
            String tmp = st.nextElement().toString();
            sqlFieldV.addElement(tmp);
            sqlValueV.addElement(request.getParameter(tmp));
          }
          //original where clause
          String origSqlFields = request.getParameter("originalWhereKey");
          Vector origSqlFieldV = new Vector(51);
          Vector origSqlValueV = new Vector(51);
          if (!BothHelpObjs.isBlankString(origSqlFields)) {
            st = new StringTokenizer(origSqlFields,sqlFieldDelim);
            while (st.hasMoreTokens()) {
              String tmp = st.nextElement().toString();
              origSqlFieldV.addElement(tmp);
              origSqlValueV.addElement(request.getParameter(tmp));
            }
          }
          //not build data
          buildAdditionalCharge(sqlFieldV,sqlValueV,origSqlFieldV,origSqlValueV,out);
        }else if (action.equalsIgnoreCase("searchWin")) {
          String searchText = BothHelpObjs.makeBlankFromNull(request.getParameter("searchString"));
          String searchBy = "LASTNAME";       //preselect last name if it is the first time
          if (!BothHelpObjs.isBlankString(searchText)) {
            if ("Yes".equalsIgnoreCase(request.getParameter("lastR"))) {
              searchBy = "LASTNAME";
            }else {
              searchBy = "FIRSTNAME";
            }
          }else {
            searchText = "";
            searchBy = "LASTNAME";
          }
          buildSearchWin(personnelID,searchText,searchBy,out);
        }

        //set to null so gabage collector can pick it up
        initialData=null;
        personnelID=null;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        out.close();
      }
    } //end of method

  private void buildSearchWin(String userID, String searchText, String searchBy,PrintWriter costReportOut) throws Exception {
    costReportOut.println(radian.web.HTMLHelpObj.printHTMLHeader("Search for User","invoice.js",intcmIsApplication));
    costReportOut.println(getPageHeader("Search"));
    costReportOut.println("<FORM method=\"POST\" NAME=\"searchUserWin\">\n");
    costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"searchWin\">\n");
    //5-10-03 send company_id to client
    costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+db.getClient().toLowerCase()+"\">\n");

    costReportOut.println("<TR><TD>&nbsp;</TD></TR>\n");
    //table
    costReportOut.println("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
    costReportOut.println("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
    costReportOut.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchString\" ID=\"searchString\" value=\""+searchText+"\" size=\"20\">\n");

    costReportOut.println("<INPUT type=\"checkbox\" onClick= \"lastNameChecked()\" name=\"lastR\" value=\"Yes\" "+("LASTNAME".equalsIgnoreCase(searchBy)?"checked":"")+">\n");
    costReportOut.println("<B>Last Name</B>\n");
    costReportOut.println("<INPUT type=\"checkbox\" onClick= \"firstNameChecked()\" name=\"firstR\" value=\"Yes\" "+("FIRSTNAME".equalsIgnoreCase(searchBy)?"checked":"")+">\n");
    costReportOut.println("<B>First Name</B>\n");
    costReportOut.println("</TD>\n");
    costReportOut.println("</TR>\n");
    if (!BothHelpObjs.isBlankString(searchText)) {
      Personnel p = new Personnel(db);
      Vector v = p.getNameSearch(new Integer(userID),"All",searchText,searchBy);
      if (v.size() > 0) {
        //table
        costReportOut.println("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
        costReportOut.println("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
        costReportOut.println("<TR>\n");
        costReportOut.println("<TD BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=Arial SIZE=\"2\" COLOR=\"#FFFFFF\">Last Name</FONT></TD>\n");
        costReportOut.println("<TD BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=Arial SIZE=\"2\" COLOR=\"#FFFFFF\">First Name</FONT></TD>\n");
        costReportOut.println("</TR>\n");
        Object[][] data = BothHelpObjs.get2DArrayFromVector(v,6);
        for (int i = 0; i < data.length; i++) {
          Object[] oa = (Object[])data[i];
          costReportOut.println("<TR>\n");
          if (i % 2 == 0) {
            costReportOut.println("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");
            costReportOut.println("<A HREF=\"javascript:getUserID('"+(String)oa[3]+"','"+(String)oa[2]+"','"+((Integer)oa[0]).toString()+"')\" STYLE=\"color:#0000ff\">"+(String)oa[3]+"</A>\n");
            costReportOut.println("</FONT></TD>\n");
            costReportOut.println("<TD bgcolor=\"#E6E8FA\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">"+(String)oa[2]+"</FONT></TD>\n");
          }else {
            costReportOut.println("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");
            costReportOut.println("<A HREF=\"javascript:getUserID('"+(String)oa[3]+"','"+(String)oa[2]+"','"+((Integer)oa[0]).toString()+"')\" STYLE=\"color:#0000ff\">"+(String)oa[3]+"</A>\n");
            costReportOut.println("</FONT></TD>\n");
            costReportOut.println("<TD ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">"+(String)oa[2]+"</FONT></TD>\n");
          }
          costReportOut.println("</TR>\n");
        }
        costReportOut.println("</TABLE>\n");
        costReportOut.println("<TR><TD&nbsp;></TD></TR>\n");
        costReportOut.println("</TD></TR>\n");
      }else {
        costReportOut.println("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
        costReportOut.println("<B>No record found.  Please check your search criterion and try again.</B></TD></TR>\n");
      }
    }
    costReportOut.println("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
    costReportOut.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onClick= \"return searchUser()\" NAME=\"searchB\">\n");
    costReportOut.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" onClick= \"closeUserWin()\" NAME=\"closeB\">\n");
    costReportOut.println("</TD></TR>\n");
    costReportOut.println("</TABLE></FORM></HTML>");
  }

  private void getComboBoxDescFromID(Hashtable userSelectedData, Hashtable initialData) {
    try {
      //first Hub drop down
      Vector hubID = (Vector)initialData.get("HUB_ID");
      Vector hubDesc = (Vector)initialData.get("HUB_DESC");
      String tmpSelectedHub = (String)userSelectedData.get("HUB_C");
      int index = hubID.indexOf(tmpSelectedHub);
      if (index > -1) {
        userSelectedData.put("HUB_DISPLAY",(String)hubDesc.elementAt(index));
      }else {
        userSelectedData.put("HUB_DISPLAY","All Reporting Groups");
      }
      //second facility drop down
      Hashtable fh = (Hashtable)initialData.get(tmpSelectedHub);
      Vector tmpID = (Vector)fh.get("FACILITY_ID");
      Vector tmpDesc = (Vector)fh.get("FACILITY_DESC");
      String comboID = (String)userSelectedData.get("FACILITY");
      index = tmpID.indexOf(comboID);
      if (index > -1) {
        userSelectedData.put("FACILITY_DISPLAY",(String)tmpDesc.elementAt(index));
      }else {
        userSelectedData.put("FACILITY_DISPLAY","All Facilities");
      }
      tmpID = null;
      tmpDesc = null;
      //next work area
      Hashtable h = (Hashtable)fh.get(comboID);
      tmpID = (Vector)h.get("APPLICATION");
      tmpDesc = (Vector)h.get("APPLICATION_DESC");
      comboID = (String)userSelectedData.get("WORK_AREA");
      index = tmpID.indexOf(comboID);
      if (index > -1) {
        userSelectedData.put("WORK_AREA_DISPLAY",(String)tmpDesc.elementAt(index));
      }else {
        userSelectedData.put("WORK_AREA_DISPLAY","All Work Areas");
      }
      tmpID = null;
      tmpDesc = null;
      //next invoice period
      tmpID = (Vector)initialData.get("INVOICE_PERIOD");
      tmpDesc = (Vector)initialData.get("INVOICE_PERIOD_DESC");
      comboID = (String)userSelectedData.get("INVOICE_PERIOD_C");
      index = tmpID.indexOf(comboID);
      if (index > -1) {
        userSelectedData.put("INVOICE_PERIOD_DISPLAY",(String)tmpDesc.elementAt(index));
      }else {
        userSelectedData.put("INVOICE_PERIOD_DISPLAY","All Invoice Periods");
      }
      tmpID = null;
      tmpDesc = null;
      /*next material category
      tmpID = (Vector)initialData.get("MATERIAL_CATEGORY_ID");
      tmpDesc = (Vector)initialData.get("MATERIAL_CATEGORY_DESC");
      comboID = (String)userSelectedData.get("MATERIAL_CATEGORY_C");
      index = tmpID.indexOf(comboID);
      if (index > -1) {
        userSelectedData.put("MATERIAL_CATEGORY_DISPLAY",(String)tmpDesc.elementAt(index));
      }else {
        userSelectedData.put("MATERIAL_CATEGORY_DISPLAY","All Categories");
      }
      tmpID = null;
      tmpDesc = null;
      */
      //item type
      tmpID = (Vector)initialData.get("ITEM_TYPE_ID");
      tmpDesc = (Vector)initialData.get("ITEM_TYPE_DESC");
      Vector selectedItemType = (Vector)userSelectedData.get("ITEM_TYPE_C");
      comboID = "";
      for (int i = 0; i < selectedItemType.size(); i++) {
        String tmp = (String)selectedItemType.elementAt(i);
        if (!"All Types".equalsIgnoreCase(tmp)) {
          comboID += (String)tmpDesc.elementAt(tmpID.indexOf(tmp))+",";
        }
      }
      //removing the last commas ','
      if (comboID.length() > 0) {
        comboID = comboID.substring(0,comboID.length()-1);
      }else {
        comboID = "All Types";
      }
      userSelectedData.put("ITEM_TYPE_DISPLAY",comboID);
      tmpID = null;
      tmpDesc = null;

    }catch (Exception e) {
      userSelectedData.put("HUB_DISPLAY","All Reporting Groups");
      userSelectedData.put("FACILITY_DISPLAY","All Facilities");
      userSelectedData.put("WORK_AREA_DISPLAY","All Work Areas");
      userSelectedData.put("INVOICE_PERIOD_DISPLAY","All Invoice Periods");
      //userSelectedData.put("MATERIAL_CATEGORY_DISPLAY","All Categories");
      userSelectedData.put("ITEM_TYPE_DISPLAY","All Types");
    }
  }

  private StringBuffer getPageHeader(String message) {
    StringBuffer Msg = new StringBuffer();

    Msg.append("<TABLE BORDER=0 WIDTH=100% CLASS=\"moveupmore\">\n");
    Msg.append("<TR VALIGN=\"TOP\">\n");
    Msg.append("<TD WIDTH=\"200\">\n");
    Msg.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
    Msg.append("</TD>\n");
    Msg.append("<TD ALIGN=\"right\">\n");
    Msg.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
    Msg.append("</TD>\n");
    Msg.append("</TR>\n");
    Msg.append("</Table>\n");
    Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

    Msg.append("<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">\n");
    Msg.append(message);
    Msg.append("</TD>\n");
    Msg.append("</TR>\n");
    Msg.append("</TABLE>\n");
    return Msg;
}

  private void buildAdditionalCharge(Vector sqlFieldV, Vector sqlValueV, Vector origSqlFieldV, Vector origSqlValueV,PrintWriter costReportOut) throws Exception {
    costReportOut.println(radian.web.HTMLHelpObj.printHTMLHeader("Additional Charge",intcmIsApplication));
    costReportOut.println(getPageHeader("Additional Charge Distribution"));
    costReportOut.println("<TR><TD>&nbsp;</TD></TR>\n");
    //table
    costReportOut.println("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
    costReportOut.println("<TR>\n");
    costReportOut.println("<TD BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=Arial SIZE=\"2\" COLOR=\"#FFFFFF\">Description</FONT></TD>\n");
    costReportOut.println("<TD BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=Arial SIZE=\"2\" COLOR=\"#FFFFFF\">Amount</FONT></TD>\n");
    costReportOut.println("<TD BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=Arial SIZE=\"2\" COLOR=\"#FFFFFF\">Currency</FONT></TD>\n");
    costReportOut.println("</TR>\n");
    try {
      InvoiceData invoiceD = new InvoiceData(db);
      costReportOut.println(invoiceD.getAdditionalCharge(sqlFieldV,sqlValueV,origSqlFieldV,origSqlValueV));
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    costReportOut.println("</HTML>");
  }

  private String numberFormatter(double value, int num) {
    BigDecimal bg = new BigDecimal(value);
    bg = bg.setScale(num,bg.ROUND_HALF_UP);
    return (bg.toString());
  }

  private void generateHtmlReport(String userAction, Vector reportDataV, Hashtable userSelectedData, String userID,PrintWriter costReportOut) throws Exception {
    //build header info
    String tmp = (String) userSelectedData.get("TEMPLATE_NAME");
    if (tmp.equals("default") || BothHelpObjs.isBlankString(tmp)) {
      tmp = "Cost Report";
    }
    costReportOut.println(radian.web.HTMLHelpObj.printHTMLHeader(tmp, "invoice.js",intcmIsApplication));
    costReportOut.println(getPageHeader(tmp));

    //5-10-03 send company_id to client
    costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\"" + db.getClient().toLowerCase() + "\">\n");

    costReportOut.println("<FONT FACE=\"Arial\">");
    costReportOut.println("<TABLE BORDER=\"0\">\n");
    costReportOut.println("<TR><TD>&nbsp;</TD></TR>\n");
    costReportOut.println("<TR bgcolor=\"#E6E8FA\"><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Requestor: </B>" + (String) userSelectedData.get("REQUESTOR_NAME") + "</FONT></TD>\n");
    tmp = (String) userSelectedData.get("SEARCH_BY");
    if ("NONE".equalsIgnoreCase(tmp)) {
      tmp = "Select One";
    } else {
      tmp = tmp + " contains  ";
    }
    costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Search By: </B>" + tmp + " ");
    costReportOut.println( (String) userSelectedData.get("SEARCH_TEXT") + "</FONT></TD></TR>\n");

    costReportOut.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Reporting Group: </B>" + (String) userSelectedData.get("HUB_DISPLAY") + "</FONT></TD>\n");
    costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Invoice #: </B>" + (String) userSelectedData.get("INVOICE_NUMBER") + "</FONT></TD></TR>\n");

    costReportOut.println("<TR bgcolor=\"#E6E8FA\"><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Facility: </B>" + (String) userSelectedData.get("FACILITY_DISPLAY") + "</FONT></TD>\n");
    costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Invoice Period: </B>" + (String) userSelectedData.get("INVOICE_PERIOD_DISPLAY") + "</FONT></TD></TR>\n");

    tmp = (String) userSelectedData.get("WORK_AREA_DISPLAY");
    if ("NONE".equalsIgnoreCase(tmp)) {
      tmp = "All Work Areas";
    }
    costReportOut.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>WorkArea: </B>" + tmp + "</FONT></TD>\n");
    //invoice date
    tmp = (String) userSelectedData.get("INVOICE_START_DATE");
    String tmp2 = (String) userSelectedData.get("INVOICE_END_DATE");
    if (tmp.length() > 0 && tmp2.length() > 0) {
      costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Invoiced Between: </B>" + tmp + " and " + tmp2 + "</FONT></TD></TR>\n");
    } else if (tmp.length() > 0) {
      costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Invoiced After: </B>" + tmp + "</FONT></TD></TR>\n");
    } else {
      costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Invoiced Before: </B>" + tmp2 + "</FONT></TD></TR>\n");
    }

    tmp = (String) userSelectedData.get("ACCOUNT_SYS");
    if (tmp.equals("All Accounting Systems")) {
      costReportOut.println("<TR bgcolor=\"#E6E8FA\"><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Accounting System: </B>" + (String) userSelectedData.get("ACCOUNT_SYS") + "</FONT></TD>\n");
      //delivered date
      tmp = (String) userSelectedData.get("START_DATE");
      tmp2 = (String) userSelectedData.get("END_DATE");
      if (tmp.length() > 0 && tmp2.length() > 0) {
        costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Delivered Between: </B>" + tmp + " and " + tmp2 + "</FONT></TD></TR>\n");
      } else if (tmp.length() > 0) {
        costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Delivered After: </B>" + tmp + "</FONT></TD></TR>\n");
      } else {
        costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Delivered Before: </B>" + tmp2 + "</FONT></TD></TR>\n");
      }

      costReportOut.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Charge Type: </B></FONT></TD>\n");
      Vector v = (Vector) userSelectedData.get("COMMODITY_C");
      String tmpInvoiceType = "";
      int jj = 0;
      for (jj = 0; jj < v.size(); jj++) {
        if (!"All Types".equalsIgnoreCase( (String) v.elementAt(jj))) {
          tmpInvoiceType += (String) v.elementAt(jj) + ",";
        }
      }
      //removing the last commas ','
      if (tmpInvoiceType.length() > 0) {
        tmpInvoiceType = tmpInvoiceType.substring(0, tmpInvoiceType.length() - 1);
      } else {
        tmpInvoiceType = "All Types";
      }
      costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Invoice Type: </B>" + tmpInvoiceType + "</FONT></TD></TR>\n");

      costReportOut.println("<TR bgcolor=\"#E6E8FA\"><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Charge Number 1: </B></FONT></TD>\n");
      costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Item Type: </B>" + (String) userSelectedData.get("ITEM_TYPE_DISPLAY") + "</FONT></TD></TR>\n");

      costReportOut.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Charge Number 2: </B></FONT></TD>\n");
      costReportOut.println("<TD>&nbsp;</TD></TR>\n");
    } else {
      costReportOut.println("<TR bgcolor=\"#E6E8FA\"><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Accounting System: </B>" + (String) userSelectedData.get("ACCOUNT_SYS") + "</FONT></TD>\n");
      //delivered date
      tmp = (String) userSelectedData.get("START_DATE");
      tmp2 = (String) userSelectedData.get("END_DATE");
      if (tmp.length() > 0 && tmp2.length() > 0) {
        costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Delivered Between: </B>" + tmp + " and " + tmp2 + "</FONT></TD></TR>\n");
      } else if (tmp.length() > 0) {
        costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Delivered After: </B>" + tmp + "</FONT></TD></TR>\n");
      } else {
        costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Delivered Before: </B>" + tmp2 + "</FONT></TD></TR>\n");
      }

      String chargeType = "Direct";
      if ("Yes".equalsIgnoreCase( (String) userSelectedData.get("INDIRECT"))) {
        chargeType = "Indirect";
      }
      costReportOut.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Charge Type: </B>" + chargeType + "</FONT></TD>\n");
      Vector v = (Vector) userSelectedData.get("COMMODITY_C");
      String tmpInvoiceType = "";
      int jj = 0;
      for (jj = 0; jj < v.size(); jj++) {
        if (!"All Types".equalsIgnoreCase( (String) v.elementAt(jj))) {
          tmpInvoiceType += (String) v.elementAt(jj) + ",";
        }
      }
      //removing the last commas ','
      if (tmpInvoiceType.length() > 0) {
        tmpInvoiceType = tmpInvoiceType.substring(0, tmpInvoiceType.length() - 1);
      } else {
        tmpInvoiceType = "All Types";
      }
      costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Invoice Type: </B>" + tmpInvoiceType + "</FONT></TD></TR>\n");

      costReportOut.println("<TR bgcolor=\"#E6E8FA\"><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Charge Number 1: </B>" + (String) userSelectedData.get("CHARGE_NUMBER_1") + "</FONT></TD>\n");
      costReportOut.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Item Type: </B>" + (String) userSelectedData.get("ITEM_TYPE_DISPLAY") + "</FONT></TD></TR>\n");

      costReportOut.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" ><B>Charge Number 2: </B>" + (String) userSelectedData.get("CHARGE_NUMBER_2") + "</FONT></TD>\n");
      costReportOut.println("<TD>&nbsp;</TD></TR>\n");
    }
    costReportOut.println("<TR><TD>&nbsp;</TD></TR>\n");
    costReportOut.println("<TR><TD><INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate Excel Report\" onClick= \"generateXlsReport()\" NAME=\"generateR\">&nbsp;\n");
    costReportOut.println("</TD></TR>\n");
    costReportOut.println("<TR><TD>&nbsp;</TD></TR>\n");
    costReportOut.println("</TABLE>\n");

    costReportOut.println("<TABLE BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
    costReportOut.println("<TR>\n");
    Vector v = (Vector) userSelectedData.get("REPORT_FIELDS");

    Hashtable originalWhereH = (Hashtable) reportDataV.lastElement();
    Vector additionalFields = (Vector) originalWhereH.get("ADDITIONAL_FIELDS");
    Hashtable totalPerCurrency = (Hashtable) originalWhereH.get("TOTAL_PER_CURRENCY");

    //col below for right justify and other special function
    int numTotalCol = 7; //there are five columns that user can ask for the total of and where clause for additional charge
    int numOfCols = -6;
    int qtyCol = -1;
    int invoiceUnitPriceCol = -1;
    int unitRebateCol = -1;
    int additionalChargeCol = -1;
	 int totalSalesTaxCol = -1;
	 int serviceFeeCol = -1;
    int netAmountCol = -1;
    int materialSavingCol = -1;
    int peiAmountCol = -1;
    int invoiceCol = -1;
    int invoiceLineCol = -1;
    int partDescCol = -1;
    int itemDescCol = -1;
    int mfgDescCol = -1;
    int applicationDescCol = -1;
    int receiptCol = -1;
    int voucherURLCol = 0;
    boolean displayVoucher = false;
    for (int i = 0; i < v.size(); i++) {
      String tmpCol = (String) v.elementAt(i);
      //DON'T DISPLAY Additional fields to user
      if (additionalFields.contains(tmpCol)) {
        continue;
      }
      //DON'T display voucher url to user as well
      if ("Voucher URL".equalsIgnoreCase(tmpCol)) {
        voucherURLCol = 1;
        displayVoucher = true;
        continue;
      }
      //display user selected report fields
      costReportOut.println("<TD BGCOLOR=\"#000066\" WIDTH=\"10%\" ALIGN=\"CENTER\"><FONT FACE=Arial SIZE=\"2\" COLOR=\"#FFFFFF\">" + tmpCol + "</FONT></TD>\n");
      //determine whether or not user ask for additional charge column
      if (tmpCol.equals("Total Additional Charge")) {
        additionalChargeCol = i;
        numOfCols++;
		} else if (tmpCol.equals("Total Sales Tax")) {
        totalSalesTaxCol = i;
        numOfCols++;
		} else if (tmpCol.equals("Invoice")) {
        invoiceCol = i;
      } else if (tmpCol.equals("Invoice Line")) {
        invoiceLineCol = i;
      } else if (tmpCol.equals("Service Fee")) {
        serviceFeeCol = i;
        numOfCols++;
      } else if (tmpCol.equals("Net Amount")) {
        netAmountCol = i;
        numOfCols++;
      } else if (tmpCol.equals("Material Savings")) {
        materialSavingCol = i;
        numOfCols++;
      } else if (tmpCol.equals("PEI Amount")) {
        peiAmountCol = i;
        numOfCols++;
      } else if (tmpCol.equals("Quantity")) {
        qtyCol = i;
      } else if (tmpCol.equals("Invoice Unit Price")) {
        invoiceUnitPriceCol = i;
      } else if (tmpCol.equals("Unit Rebate")) {
        unitRebateCol = i;
      } else if (tmpCol.equals("Part Description")) {
        partDescCol = i;
      } else if (tmpCol.equals("Item Description")) {
        itemDescCol = i;
      } else if (tmpCol.equals("Manufacturer")) {
        mfgDescCol = i;
      } else if (tmpCol.equals("Receipt ID")) {
        receiptCol = i;
      } else if (tmpCol.equals("Work Area")) {
        applicationDescCol = i;
      }
    }
    costReportOut.println("</TR>\n");

    //build body info
    if (reportDataV.size() <= numTotalCol) {
      costReportOut.println("<TR><TD COLSPAN=\"6\" ALIGN=\"CENTER\"><B>No record found.  Please check your search criterion and try again.</B></TD></TR>\n");
    } else {
      for (int i = 0; i < reportDataV.size() - numTotalCol; i++) { //minus 5 for the five total columns
        costReportOut.println("<TR>\n");
        Vector tmpV = (Vector) reportDataV.elementAt(i);
        //prepare for additional charge column
        Vector sqlFieldV = (Vector) userSelectedData.get("SQL_FIELDS");
        //create string for sql_fields
        String sqlFieldDelim = "|";
        String sqlFields = "";
        String sqlValues = "";
        if (additionalChargeCol > 0) {
          //build original where
          String originalWhere = buildOriginalWhere(originalWhereH, sqlFieldDelim, sqlFieldV);
          //next build URL with current data
          for (int n = 0; n < sqlFieldV.size(); n++) {
            if (n == partDescCol || n == mfgDescCol || n == itemDescCol || n == applicationDescCol) {
              continue;
            }

            String tmpCol = (String) sqlFieldV.elementAt(n);
            String tmpVal = (String) tmpV.elementAt(n);
            //ship if data is blank
            if ("&nbsp;".equalsIgnoreCase(tmpVal)) {
              continue;
            }

            if (tmpCol.startsWith("to_char(")) {
              tmpCol = tmpCol.substring("to_char(".length(), tmpCol.indexOf(","));
            }
            sqlFields += tmpCol + sqlFieldDelim;
            sqlValues += "&" + URLEncoder.encode(tmpCol) + "=" + URLEncoder.encode(tmpVal);
          }
          sqlValues = URLEncoder.encode(sqlValues);
          //remove the last delim
          if (sqlFields.length() > sqlFieldDelim.length()) {
            sqlFields = sqlFields.substring(0, sqlFields.length() - sqlFieldDelim.length());
          }
          //NOW add original where to sql values
          sqlFields = URLEncoder.encode(sqlFields);
          if (originalWhere.length() > 1) {
            sqlValues += URLEncoder.encode(originalWhere);
          }
          sqlFieldDelim = URLEncoder.encode(sqlFieldDelim);
        } //end of if additional charge column

        //displaying alternate color
        if (i % 2 == 0) {
          for (int j = 0; j < tmpV.size(); j++) {
            String tmpD = (String) tmpV.elementAt(j);
            //DON'T display additional fields to user
            if (additionalFields.contains(sqlFieldV.elementAt(j))) {
              continue;
            }
            //right justify
            if (j == qtyCol || j == invoiceUnitPriceCol || j == unitRebateCol || j == additionalChargeCol || j == totalSalesTaxCol ||
                j == serviceFeeCol || j == peiAmountCol || j == netAmountCol || j == materialSavingCol) {
              costReportOut.println("<TD bgcolor=\"#E6E8FA\" WIDTH=\"10%\" ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">");
            } else {
              costReportOut.println("<TD bgcolor=\"#E6E8FA\" WIDTH=\"10%\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");
            }
            //Display all fields user selected
            if (j == additionalChargeCol && !"0".equals(tmpD) && !tmpD.equals("&nbsp;")) {
              costReportOut.println("<A HREF=\"javascript:showAdditionalCharge('" + sqlFields + "','" + sqlValues + "','" + sqlFieldDelim + "')\" STYLE=\"color:#0000ff\">" + tmpD + "</A>\n");
            } else if (j == receiptCol && displayVoucher) {
              String url = (String) tmpV.elementAt(j + 1); //URL is the next column to receipt
              if (!"&nbsp;".equalsIgnoreCase(url)) {
                costReportOut.println("<A HREF=\"" + url + "\" TARGET=\"msds\"  STYLE=\"color:#0000ff\">\n");
                costReportOut.println( (String) tmpV.elementAt(j));
                costReportOut.println("</A>\n");
              } else {
                costReportOut.println( (String) tmpV.elementAt(j));
              }
              //increase count to skip voucher URL
              j++;
            } else {
              costReportOut.println( (String) tmpV.elementAt(j));
            }
            costReportOut.println("</FONT></TD>\n");
          }
        } else {
          for (int j = 0; j < tmpV.size(); j++) {
            String tmpD = (String) tmpV.elementAt(j);
            //DON'T display additional fields to user
            if (additionalFields.contains(sqlFieldV.elementAt(j))) {
              continue;
            }
            //right justify
            if (j == qtyCol || j == invoiceUnitPriceCol || j == unitRebateCol || j == additionalChargeCol || j == totalSalesTaxCol ||
                j == serviceFeeCol || j == peiAmountCol || j == netAmountCol || j == materialSavingCol) {
              costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">");
            } else {
              costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\"><FONT FACE=Arial SIZE=\"2\">");
            }
            //Display all fields user selected
            if (j == additionalChargeCol && !"0".equals(tmpD) && !tmpD.equals("&nbsp;")) {
              costReportOut.println("<A HREF=\"javascript:showAdditionalCharge('" + sqlFields + "','" + sqlValues + "','" + sqlFieldDelim + "')\" STYLE=\"color:#0000ff\">" + tmpD + "</A>\n");
            } else if (j == receiptCol && displayVoucher) {
              String url = (String) tmpV.elementAt(j + 1); //URL is the next column to receipt
              if (!"&nbsp;".equalsIgnoreCase(url)) {
                costReportOut.println("<A HREF=\"" + url + "\" TARGET=\"msds\"  STYLE=\"color:#0000ff\">\n");
                costReportOut.println( (String) tmpV.elementAt(j));
                costReportOut.println("</A>\n");
              } else {
                costReportOut.println( (String) tmpV.elementAt(j));
              }
              //increase count to skip voucher URL
              j++;
            } else {
              costReportOut.println( (String) tmpV.elementAt(j));
            }
            costReportOut.println("</FONT></TD>\n");
          }
        }
        costReportOut.println("</TR>\n");
      }

      //add total to bottom of report
      //if report show different currency then
      if (v.contains("Currency")) {
        int containCurrency = 1;
        int currencyTitle = 1;
        //totalPerCurrency
        costReportOut.println("<TR>\n");
        costReportOut.println("<TD>&nbsp;</TD>\n");
        costReportOut.println("</TR>\n");
        Enumeration enumCollec = totalPerCurrency.keys();
        int innerCount = 0;
        while (enumCollec.hasMoreElements()) {
          if (innerCount % 2 == 0) {
            costReportOut.println("<TR bgcolor=\"#E6E8FA\">\n");
          } else {
            costReportOut.println("<TR>\n");
          }
          String key = enumCollec.nextElement().toString();
          Hashtable innerH = (Hashtable) totalPerCurrency.get(key);
          costReportOut.println("<TD>Total " + key + "</TD>\n");
          if (numOfCols == 0) {
            for (int k = 0; k < v.size() - (6 + additionalFields.size() + voucherURLCol + containCurrency + currencyTitle); k++) {
              costReportOut.println("<TD>&nbsp;</TD>\n");
            }
            Double tmpVal = (Double) innerH.get("Total Additional Charge");
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total Sales Tax");
				if (tmpVal == 0) {
					costReportOut.println("<TD>&nbsp;</TD>\n");
				}else {
					costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
				}
				tmpVal = (Double) innerH.get("Total Service Fee");
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total PEI Amount");
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total Net Amount");
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total Material Savings");
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
          } else if (numOfCols == -1) { //selected five
            for (int k = 0; k < v.size() - (5 + additionalFields.size() + voucherURLCol + containCurrency + currencyTitle); k++) {
              costReportOut.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
					Double tmpVal = (Double) innerH.get("Total Sales Tax");
					if (tmpVal == 0) {
						costReportOut.println("<TD>&nbsp;</TD>\n");
					}else {
						costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
					}
				}
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
          } else if (numOfCols == -2) { //selected four
            for (int k = 0; k < v.size() - (4 + additionalFields.size() + voucherURLCol + containCurrency + currencyTitle); k++) {
              costReportOut.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
					Double tmpVal = (Double) innerH.get("Total Sales Tax");
					if (tmpVal == 0) {
						costReportOut.println("<TD>&nbsp;</TD>\n");
					}else {
						costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
					}
				}
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
          } else if (numOfCols == -3) { //selected three
            for (int k = 0; k < v.size() - (3 + additionalFields.size() + voucherURLCol + containCurrency + currencyTitle); k++) {
              costReportOut.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
					Double tmpVal = (Double) innerH.get("Total Sales Tax");
					if (tmpVal == 0) {
						costReportOut.println("<TD>&nbsp;</TD>\n");
					}else {
						costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
					}
				}
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
          } else if (numOfCols == -4) { //selected two
            for (int k = 0; k < v.size() - (2 + additionalFields.size() + voucherURLCol + containCurrency + currencyTitle); k++) {
              costReportOut.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
					Double tmpVal = (Double) innerH.get("Total Sales Tax");
					if (tmpVal == 0) {
						costReportOut.println("<TD>&nbsp;</TD>\n");
					}else {
						costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
					}
				}
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
          }  else if (numOfCols == -5) { //selected one
            for (int k = 0; k < v.size() - (1 + additionalFields.size() + voucherURLCol + containCurrency + currencyTitle); k++) {
              costReportOut.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
					Double tmpVal = (Double) innerH.get("Total Sales Tax");
					if (tmpVal == 0) {
						costReportOut.println("<TD>&nbsp;</TD>\n");
					}else {
						costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
					}
				}
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            }
          }

          costReportOut.println("</TR>\n");
          innerCount++;
        }
      } else {
        Double totalAddCharge = (Double) reportDataV.elementAt(reportDataV.size() - 7);
		  Double totalSalesTax = (Double) reportDataV.elementAt(reportDataV.size() - 6);
		  Double totalServiceFee = (Double) reportDataV.elementAt(reportDataV.size() - 5);
        Double totalNetAmount = (Double) reportDataV.elementAt(reportDataV.size() - 4);
        Double totalMaterialSaving = (Double) reportDataV.elementAt(reportDataV.size() - 3);
        Double totalPeiAmount = (Double) reportDataV.elementAt(reportDataV.size() - 2);
        if ( (reportDataV.size() - numTotalCol) % 2 == 0) {
          costReportOut.println("<TR bgcolor=\"#E6E8FA\">\n");
        } else {
          costReportOut.println("<TR>\n");
        }
        //calculate number of selected columns
        if (numOfCols == 0) { //all six columns
          for (int k = 0; k < v.size() - (6 + additionalFields.size() + voucherURLCol); k++) {
            costReportOut.println("<TD>&nbsp;</TD>\n");
          }
          costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalAddCharge.doubleValue(), 6) + "</FONT></TD>\n");
			 if (totalSalesTax == 0 ) {
			  costReportOut.println("<TD>&nbsp;</TD>\n");
			 }else {
			  costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
			 }
			 costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalServiceFee.doubleValue(), 6) + "</FONT></TD>\n");
          costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalPeiAmount.doubleValue(), 6) + "</FONT></TD>\n");
          costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalNetAmount.doubleValue(), 6) + "</FONT></TD>\n");
          costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalMaterialSaving.doubleValue(), 6) + "</FONT></TD>\n");
        } else if (numOfCols == -1) { //selected five
          for (int k = 0; k < v.size() - (5 + additionalFields.size() + voucherURLCol); k++) {
            costReportOut.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalAddCharge.doubleValue(), 6) + "</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0 ) {
			  		costReportOut.println("<TD>&nbsp;</TD>\n");
			 	}else {
			  		costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
			 	}
          }
			 if (serviceFeeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalServiceFee.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalPeiAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalNetAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalMaterialSaving.doubleValue(), 6) + "</FONT></TD>\n");
          }
        } else if (numOfCols == -2) { //selected four
          for (int k = 0; k < v.size() - (4 + additionalFields.size() + voucherURLCol); k++) {
            costReportOut.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalAddCharge.doubleValue(), 6) + "</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0 ) {
			  		costReportOut.println("<TD>&nbsp;</TD>\n");
			 	}else {
			  		costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
			 	}
          }
			 if (serviceFeeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalServiceFee.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalPeiAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalNetAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalMaterialSaving.doubleValue(), 6) + "</FONT></TD>\n");
          }
        } else if (numOfCols == -3) { //selected three
          for (int k = 0; k < v.size() - (3 + additionalFields.size() + voucherURLCol); k++) {
            costReportOut.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalAddCharge.doubleValue(), 6) + "</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0 ) {
			  		costReportOut.println("<TD>&nbsp;</TD>\n");
			 	}else {
			  		costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
			 	}
          }
			 if (serviceFeeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalServiceFee.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalPeiAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalNetAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalMaterialSaving.doubleValue(), 6) + "</FONT></TD>\n");
          }
        } else if (numOfCols == -4) { //selected two
          for (int k = 0; k < v.size() - (2 + additionalFields.size() + voucherURLCol); k++) {
            costReportOut.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalAddCharge.doubleValue(), 6) + "</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0 ) {
			  		costReportOut.println("<TD>&nbsp;</TD>\n");
			 	}else {
			  		costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
			 	}
          }
			 if (serviceFeeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalServiceFee.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalPeiAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalNetAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalMaterialSaving.doubleValue(), 6) + "</FONT></TD>\n");
          }
		  } else if (numOfCols == -5) { //selected one
          for (int k = 0; k < v.size() - (1 + additionalFields.size() + voucherURLCol); k++) {
            costReportOut.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalAddCharge.doubleValue(), 6) + "</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0 ) {
			  		costReportOut.println("<TD>&nbsp;</TD>\n");
			 	}else {
			  		costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
			 	}
          }
			 if (serviceFeeCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalServiceFee.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalPeiAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalNetAmount.doubleValue(), 6) + "</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            costReportOut.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalMaterialSaving.doubleValue(), 6) + "</FONT></TD>\n");
          }
		  } else if (numOfCols == -6) { //selected zero
          costReportOut.println("<TD>&nbsp;</TD>\n");
        }
        costReportOut.println("</TR>\n");
      } //end of not showing currency
    } //end of else
  } //end of method

  private String buildOriginalWhere(Hashtable h, String sqlFieldDelim, Vector sqlFields) {
    String result = "";
    Vector sqlVals = (Vector)h.get("SQL_VALS");
    Vector sqlCols = (Vector)h.get("SQL_COLS");
    String sqlCol = "";
    for (int i = 0; i < sqlCols.size(); i++) {
      String tmpCol = (String)sqlCols.elementAt(i);
      if (!sqlFields.contains(tmpCol)) {
        //first build URL
        result += "&" + tmpCol + "=" + URLEncoder.encode((String)sqlVals.elementAt(i));
        //build key for URL
        sqlCol += tmpCol + sqlFieldDelim;
      }
    }
    //add key for URL to URL
    //but first remove the last delim from key URL
    if (sqlCol.length() > 0) {
      result += "&originalWhereKey="+sqlCol.substring(0,sqlCol.length()-sqlFieldDelim.length());
    }
    return result;
  }

  //not using this method
  private String generateHtmlURL(String contents, String userID) {
    String file = "";

    Random ran = new Random();
    int tmp = ran.nextInt();
    Integer tmp2 = new Integer(tmp);

    String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath()+ "cost/";
    file = HomeDirectory+userID+"CostReport"+tmp2.toString()+".html";
    try {
      File outFile = new File(file);
      FileOutputStream DBO = new FileOutputStream(outFile);
      byte outbuf[];
      outbuf = new byte[contents.length()];
      outbuf = contents.getBytes();
      DBO.write(outbuf);
      DBO.close();
    }catch(IOException ioe) {
      ioe.printStackTrace();
      //System.out.println("\n\nError opening output file: ");
      //System.out.println(String.valueOf(ioe));
      //System.out.println("\n");
    }
    return "/reports/cost/"+userID+"CostReport"+tmp2.toString()+".html";
  } //end of method

  private String generateXlsReport(Vector reportDataV, Hashtable userSelectedData, String userID) throws Exception {
    //build header info
		String serverPath = "";
		serverPath = radian.web.tcmisResourceLoader.getsavelreportpath()+ "cost/";
		//System.out.println("serverPath   "+serverPath+"");
		File dir = new File(serverPath);
		File file = File.createTempFile(""+userID+"CostReport", ".xls", dir);
		PrintWriter pw = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));

		try {
    pw.println("<FONT FACE=\"Arial\">");
    pw.println("<TABLE BORDER=\"0\">\n");
    pw.println("<TR>\n");
    String tmp = (String)userSelectedData.get("TEMPLATE_NAME");
    if (tmp.equals("default") || BothHelpObjs.isBlankString(tmp)) {
      tmp = "Cost Report";
    }
    pw.println("<TD COLSPAN=\"8\" ALIGN=\"CENTER\" ><B><FONT size=\"5\">"+tmp+"</FONT></B></TD>\n");
    pw.println(getTimeDate());
    pw.println("</TR>\n");
    pw.println("<TR><TD>&nbsp;</TD></TR>\n");

    pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Requestor: "+(String)userSelectedData.get("REQUESTOR_NAME")+"</FONT></TD>\n");
    tmp = (String)userSelectedData.get("SEARCH_BY");
    if ("NONE".equalsIgnoreCase(tmp)) {
      tmp = "Select One";
    }else {
      tmp = tmp + " contains  ";
    }
    pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Search By: "+tmp+" ");
    pw.println((String)userSelectedData.get("SEARCH_TEXT")+"</FONT></TD></TR>\n");

    pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Reporting Group: "+(String)userSelectedData.get("HUB_DISPLAY")+"</FONT></TD>\n");
    pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Invoice #: "+(String)userSelectedData.get("INVOICE_NUMBER")+"</FONT></TD></TR>\n");

    pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Facility: "+(String)userSelectedData.get("FACILITY_DISPLAY")+"</FONT></TD>\n");
    pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Invoice Period: "+(String)userSelectedData.get("INVOICE_PERIOD_DISPLAY")+"</FONT></TD></TR>\n");

    tmp = (String)userSelectedData.get("WORK_AREA_DISPLAY");
    if ("NONE".equalsIgnoreCase(tmp)) {
      tmp = "All Work Areas";
    }
    pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >WorkArea: "+tmp+"</FONT></TD>\n");
    //invoice date
    tmp = (String)userSelectedData.get("INVOICE_START_DATE");
    String tmp2 = (String)userSelectedData.get("INVOICE_END_DATE");
    if (tmp.length() > 0 && tmp2.length() > 0) {
      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Invoiced Between: "+tmp+" and "+tmp2+"</FONT></TD></TR>\n");
    }else if (tmp.length() > 0) {
      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Invoiced After: "+tmp+"</FONT></TD></TR>\n");
    }else {
      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Invoiced Before: "+tmp2+"</FONT></TD></TR>\n");
    }

    String accountSys = (String)userSelectedData.get("ACCOUNT_SYS");
    if (tmp.equals("All Accounting Systems")) {
      pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Accounting System: "+accountSys+"</FONT></TD>\n");
      //delivered date
      tmp = (String)userSelectedData.get("START_DATE");
      tmp2 = (String)userSelectedData.get("END_DATE");
      if (tmp.length() > 0 && tmp2.length() > 0) {
        pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Delivered Between: "+tmp+" and "+tmp2+"</FONT></TD></TR>\n");
      }else if (tmp.length() > 0) {
        pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Delivered After: "+tmp+"</FONT></TD></TR>\n");
      }else {
        pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Delivered Before: "+tmp2+"</FONT></TD></TR>\n");
      }

      pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Charge Type: </FONT></TD>\n");
      Vector v = (Vector)userSelectedData.get("COMMODITY_C");
      String tmpInvoiceType = "";
      int jj = 0;
      for (jj = 0; jj < v.size(); jj++) {
        if (!"All Types".equalsIgnoreCase((String)v.elementAt(jj))) {
          tmpInvoiceType += (String)v.elementAt(jj)+",";
        }
      }
      //removing the last commas ','
      if (tmpInvoiceType.length() > 0) {
        tmpInvoiceType = tmpInvoiceType.substring(0,tmpInvoiceType.length()-1);
      }else {
        tmpInvoiceType = "All Types";
      }
      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Invoice Type: "+tmpInvoiceType+"</FONT></TD></TR>\n");

      pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Charge Number 1: </FONT></TD>\n");
      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Item Type: "+(String)userSelectedData.get("ITEM_TYPE_DISPLAY")+"</FONT></TD></TR>\n");

      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Charge Number 2: </FONT></TD></TR>\n");
    }else {
      pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Accounting System: "+accountSys+"</FONT></TD>\n");
      //delivered date
      tmp = (String)userSelectedData.get("START_DATE");
      tmp2 = (String)userSelectedData.get("END_DATE");
      if (tmp.length() > 0 && tmp2.length() > 0) {
        pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Delivered Between: "+tmp+" and "+tmp2+"</FONT></TD></TR>\n");
      }else if (tmp.length() > 0) {
        pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Delivered After: "+tmp+"</FONT></TD></TR>\n");
      }else {
        pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Delivered Before: "+tmp2+"</FONT></TD></TR>\n");
      }

      String chargeType = "Direct";
      if ("Yes".equalsIgnoreCase((String)userSelectedData.get("INDIRECT"))) {
        chargeType = "Indirect";
      }
      pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Charge Type: "+chargeType+"</FONT></TD>\n");
      Vector v = (Vector)userSelectedData.get("COMMODITY_C");
      String tmpInvoiceType = "";
      int jj = 0;
      for (jj = 0; jj < v.size(); jj++) {
        if (!"All Types".equalsIgnoreCase((String)v.elementAt(jj))) {
          tmpInvoiceType += (String)v.elementAt(jj)+",";
        }
      }
      //removing the last commas ','
      if (tmpInvoiceType.length() > 0) {
        tmpInvoiceType = tmpInvoiceType.substring(0,tmpInvoiceType.length()-1);
      }else {
        tmpInvoiceType = "All Types";
      }
      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Invoice Type: "+tmpInvoiceType+"</FONT></TD></TR>\n");

      pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Charge Number 1: "+(String)userSelectedData.get("CHARGE_NUMBER_1")+"</FONT></TD>\n");
      pw.println("<TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Item Type: "+(String)userSelectedData.get("ITEM_TYPE_DISPLAY")+"</FONT></TD></TR>\n");

      pw.println("<TR><TD COLSPAN=\"4\" ALIGN=\"LEFT\"><FONT size=\"2\" >Charge Number 2: "+(String)userSelectedData.get("CHARGE_NUMBER_2")+"</FONT></TD></TR>\n");
    }
    pw.println("<TR><TD>&nbsp;</TD></TR>\n");
    pw.println("<TR><TD>&nbsp;</TD></TR>\n");
    pw.println("</TABLE>\n");

    pw.println("<TABLE BORDER=\"1\">\n");
    pw.println("<TR>\n");
    Vector v = (Vector)userSelectedData.get("REPORT_FIELDS");

    Hashtable originalWhereH = (Hashtable)reportDataV.lastElement();
    Vector additionalFields = (Vector) originalWhereH.get("ADDITIONAL_FIELDS");
    Vector sqlFieldV = (Vector)userSelectedData.get("SQL_FIELDS");
    //total per Currency
    Hashtable totalPerCurrency = (Hashtable) originalWhereH.get("TOTAL_PER_CURRENCY");

    int numTotalCol = 7;      //there are five columns that user can ask for the total of and where clause for additional charge
    int numOfCols = -6;
    int qtyCol = -1;
    int invoiceUnitPriceCol = -1;
    int unitRebateCol = -1;
    int additionalChargeCol = -1;
	 int totalSalesTaxCol = -1;
	 int serviceFeeCol = -1;
    int netAmountCol = -1;
    int materialSavingCol = -1;
    int peiAmountCol = -1;
    int receiptCol = -1;
    int voucherURLCol = 0;
    boolean displayVoucher = false;
    for (int i = 0; i < v.size(); i++) {
      String tmpCol = (String)v.elementAt(i);
      //DON'T DISPLAY Additional fields to user
      if (additionalFields.contains(tmpCol)) {
        continue;
      }
      //DON'T display voucher URL to user
      if ("Voucher URL".equalsIgnoreCase(tmpCol)) {
        voucherURLCol = 1;
        displayVoucher = true;
        continue;
      }
      //display user selected report fields
      pw.println("<TD ALIGN=\"CENTER\">"+tmpCol+"</TD>\n");
      //determine whether or not user ask for additional charge column
      if (tmpCol.equals("Total Additional Charge")) {
        additionalChargeCol = i;
        numOfCols++;
		}else if (tmpCol.equals("Total Sales Tax")) {
        totalSalesTaxCol = i;
        numOfCols++;
		}else if (tmpCol.equals("Service Fee")) {
        serviceFeeCol = i;
        numOfCols++;
      }else if (tmpCol.equals("Net Amount")) {
        netAmountCol = i;
        numOfCols++;
      }else if (tmpCol.equals("Material Savings")) {
        materialSavingCol = i;
        numOfCols++;
      }else if (tmpCol.equals("PEI Amount")) {
        peiAmountCol = i;
        numOfCols++;
      }else if (tmpCol.equals("Quantity")) {
        qtyCol = i;
      }else if (tmpCol.equals("Invoice Unit Price")) {
        invoiceUnitPriceCol = i;
      }else if (tmpCol.equals("Unit Rebate")) {
        unitRebateCol = i;
      }else if (tmpCol.equals("Receipt ID")) {
        receiptCol = i;
      }
    }
    pw.println("</TR>\n");

    //build body info
    if (reportDataV.size() <= numTotalCol) {
      pw.println("<TR><TD COLSPAN=\"6\"><B>No record found.  Please check your search criterion and try again.</B></TD></TR>\n");
    }else {
      for (int i = 0; i < reportDataV.size() - numTotalCol; i++) {
        pw.println("<TR>\n");
        Vector tmpV = (Vector)reportDataV.elementAt(i);
        for (int j = 0; j < tmpV.size(); j++) {
          String tmpD = (String)tmpV.elementAt(j);
          //DON'T display additional fields to user
          //System.out.println("-- sql:"+sqlFieldV.size()+"+data:"+tmpV.size()+"-"+sqlFieldV+"="+tmpV);
          if (additionalFields.contains(sqlFieldV.elementAt(j))) {
            continue;
          }
          //Display all fields user selected
          //right justify
          if (j == qtyCol || j == invoiceUnitPriceCol || j == unitRebateCol || j == additionalChargeCol || j == totalSalesTaxCol ||
              j == serviceFeeCol || j == peiAmountCol || j == netAmountCol || j == materialSavingCol) {
            pw.println("<TD ALIGN=\"RIGHT\">");
          }else {
            pw.println("<TD ALIGN=\"LEFT\">");
          }

          if (j == receiptCol && displayVoucher) {
            pw.println(tmpD);
            //increase count to skip voucher URL
            j++;
          }else {
            pw.println(tmpD);
          }
          pw.println("</TD>\n");
        }
        pw.println("</TR>\n");
      }
      //add total to bottom of report
      //if report show different currency then
      if (v.contains("Currency")) {
        int containCurrency = 1;
        int currencyTitle = 1;
        //totalPerCurrency
        Enumeration enumCollec = totalPerCurrency.keys();
        while (enumCollec.hasMoreElements()) {
          pw.println("<TR>\n");
          String key = enumCollec.nextElement().toString();
          Hashtable innerH = (Hashtable) totalPerCurrency.get(key);
          pw.println("<TD>Total "+key+"</TD>\n");
          if (numOfCols == 0) {
            for (int k = 0; k < v.size() - (6 + additionalFields.size() + voucherURLCol +containCurrency + currencyTitle); k++) {
              pw.println("<TD>&nbsp;</TD>\n");
            }
            Double tmpVal = (Double) innerH.get("Total Additional Charge");
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total Sales Tax");
				if (tmpVal == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				}else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
				}
				tmpVal = (Double) innerH.get("Total Service Fee");
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total PEI Amount");
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total Net Amount");
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
            tmpVal = (Double) innerH.get("Total Material Savings");
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
          }else if (numOfCols == -1) {     //selected five
            for (int k = 0; k < v.size()-(5+additionalFields.size()+voucherURLCol+containCurrency+currencyTitle); k++) {
              pw.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Sales Tax");
				  if (tmpVal == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				  }else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
				  }
				}
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
          }else if (numOfCols == -2) {     //selected four
            for (int k = 0; k < v.size()-(4+additionalFields.size()+voucherURLCol+containCurrency+currencyTitle); k++) {
              pw.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Sales Tax");
				  if (tmpVal == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				  }else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
				  }
            }
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
          }else if (numOfCols == -3) {     //selected three
            for (int k = 0; k < v.size()-(3+additionalFields.size()+voucherURLCol+containCurrency+currencyTitle); k++) {
              pw.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Sales Tax");
				  if (tmpVal == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				  }else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
				  }
            }
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
          }else if (numOfCols == -4) {     //selected two
            for (int k = 0; k < v.size()-(2+additionalFields.size()+voucherURLCol+containCurrency+currencyTitle); k++) {
              pw.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Sales Tax");
				  if (tmpVal == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				  }else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
				  }
            }
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
          }else if (numOfCols == -5) {     //selected one
            for (int k = 0; k < v.size()-(1+additionalFields.size()+voucherURLCol+containCurrency+currencyTitle); k++) {
              pw.println("<TD>&nbsp;</TD>\n");
            }
            if (additionalChargeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Additional Charge");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
				if (totalSalesTaxCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Sales Tax");
				  if (tmpVal == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				  }else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(tmpVal.doubleValue(), 6) + "</FONT></TD>\n");
				  }
            }
				if (serviceFeeCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Service Fee");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (peiAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total PEI Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (netAmountCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Net Amount");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
            if (materialSavingCol >= 0) {
              Double tmpVal = (Double) innerH.get("Total Material Savings");
              pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+"</FONT></TD>\n");
            }
          }

          /*
          Enumeration innerE = innerH.keys();
          while (innerE.hasMoreElements()) {
            String innerKey = innerE.nextElement().toString();
            Double tmpVal = (Double) innerH.get(innerKey);
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(tmpVal.doubleValue(),6)+" "+innerKey+"</FONT></TD>\n");
          }
          */
          pw.println("</TR>\n");
        }
      }else {
        Double totalAddCharge = (Double)reportDataV.elementAt(reportDataV.size()-7);
		  Double totalSalesTax = (Double)reportDataV.elementAt(reportDataV.size()-6);
		  Double totalServiceFee = (Double)reportDataV.elementAt(reportDataV.size()-5);
        Double totalNetAmount = (Double)reportDataV.elementAt(reportDataV.size()-4);
        Double totalMaterialSaving = (Double)reportDataV.elementAt(reportDataV.size()-3);
        Double totalPeiAmount = (Double)reportDataV.elementAt(reportDataV.size()-2);
        pw.println("<TR>\n");
        //calculate number of selected columns
        if (numOfCols == 0 ) {          //all six columns
          for (int k = 0; k < v.size()-(6+additionalFields.size()+voucherURLCol); k++) {
            pw.println("<TD>&nbsp;</TD>\n");
          }
          pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalAddCharge.doubleValue(),6)+"</FONT></TD>\n");
			 pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalSalesTax.doubleValue(),6)+"</FONT></TD>\n");
			 pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalServiceFee.doubleValue(),6)+"</FONT></TD>\n");
          pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalPeiAmount.doubleValue(),6)+"</FONT></TD>\n");
          pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalNetAmount.doubleValue(),6)+"</FONT></TD>\n");
          pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalMaterialSaving.doubleValue(),6)+"</FONT></TD>\n");
        }else if (numOfCols == -1) {     //selected five
          for (int k = 0; k < v.size()-(5+additionalFields.size()+voucherURLCol); k++) {
            pw.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalAddCharge.doubleValue(),6)+"</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				}else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
				}
          }
			 if (serviceFeeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalServiceFee.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalPeiAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalNetAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalMaterialSaving.doubleValue(),6)+"</FONT></TD>\n");
          }
        }else if (numOfCols == -2) {     //selected four
          for (int k = 0; k < v.size()-(4+additionalFields.size()+voucherURLCol); k++) {
            pw.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalAddCharge.doubleValue(),6)+"</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				}else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
				}
          }
			 if (serviceFeeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalServiceFee.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalPeiAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalNetAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalMaterialSaving.doubleValue(),6)+"</FONT></TD>\n");
          }
        }else if (numOfCols == -3) {     //selected three
          for (int k = 0; k < v.size()-(3+additionalFields.size()+voucherURLCol); k++) {
            pw.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalAddCharge.doubleValue(),6)+"</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				}else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
				}
          }
			 if (serviceFeeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalServiceFee.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalPeiAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalNetAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalMaterialSaving.doubleValue(),6)+"</FONT></TD>\n");
          }
        }else if (numOfCols == -4) {     //selected two
          for (int k = 0; k < v.size()-(2+additionalFields.size()+voucherURLCol); k++) {
            pw.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalAddCharge.doubleValue(),6)+"</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				}else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
				}
          }
			 if (serviceFeeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalServiceFee.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalPeiAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalNetAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalMaterialSaving.doubleValue(),6)+"</FONT></TD>\n");
          }
		  }else if (numOfCols == -5) {     //selected one
          for (int k = 0; k < v.size()-(1+additionalFields.size()+voucherURLCol); k++) {
            pw.println("<TD>&nbsp;</TD>\n");
          }
          if (additionalChargeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalAddCharge.doubleValue(),6)+"</FONT></TD>\n");
          }
			 if (totalSalesTaxCol >= 0) {
				if (totalSalesTax == 0) {
					pw.println("<TD>&nbsp;</TD>\n");
				}else {
					pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">" + numberFormatter(totalSalesTax.doubleValue(), 6) + "</FONT></TD>\n");
				}
          } 
			 if (serviceFeeCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalServiceFee.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (peiAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalPeiAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (netAmountCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalNetAmount.doubleValue(),6)+"</FONT></TD>\n");
          }
          if (materialSavingCol >= 0) {
            pw.println("<TD ALIGN=\"RIGHT\"><FONT FACE=Arial SIZE=\"2\">"+numberFormatter(totalMaterialSaving.doubleValue(),6)+"</FONT></TD>\n");
          }
		  }else if (numOfCols == -6) {     //selected zero
          pw.println("<TD>&nbsp;</TD>\n");
        }
        pw.println("</TR>\n");
      } //end of else not showing currency
    } //end of there are some records found
	  }
	  catch(Exception ioe) {
            ioe.printStackTrace();
		 //System.out.println("\n\nError opening output file: ");
		 //System.out.println(String.valueOf(ioe));
		 //System.out.println("\n");
	 }
    //return (generateXlsURL(Msg.toString(),userID));
		pw.close();
		String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
		return ""+wwwHome+"reports/cost/"+file.getName();
  } //end of method

  /*private String generateXlsURL(String contents, String userID) {
    String file = "";
    Random ran = new Random();
    int tmp = ran.nextInt();
    Integer tmp2 = new Integer(tmp);
    String HomeDirectory = radian.web.tcmisResourceLoader.getsavelreportpath()+ "cost/";
    file = HomeDirectory+userID+"CostReport"+tmp2.toString()+".xls";
    try {
      File outFile = new File(file);
      FileOutputStream DBO = new FileOutputStream(outFile);
      byte outbuf[];
      outbuf = new byte[contents.length()];
      outbuf = contents.getBytes();
      DBO.write(outbuf);
      DBO.close();
    }catch(IOException ioe) {
      System.out.println("\n\nError opening output file: ");
      System.out.println(String.valueOf(ioe));
      System.out.println("\n");
    }
    String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
    return ""+wwwHome+"reports/cost/"+userID+"CostReport"+tmp2.toString()+".xls";
  } //end of method*/

  private String getTimeDate() {
    Calendar cal = Calendar.getInstance();
    String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
    int am_pm = cal.get(Calendar.AM_PM);
    String am = "";
    if (am_pm == 0)
    {
        am = "AM";
    }
    else if (am_pm == 1)
    {
        am = "PM";
    }
    int min = cal.get(Calendar.MINUTE);
    int hours = cal.get(Calendar.HOUR);
    String time = "";

    if (hours < 10)
    {
        time += "0"+hours;
    }
    else
    {
        time += hours;
    }
    if (min < 10)
    {
        time += ":0"+min;
    }
    else
    {
        time += ":"+min;
    }
    time += " "+am;

    return "<TD COLSPAN=\"8\" ALIGN=\"CENTER\"><FONT size=\"4\" >Date: "+cdate+" Time: "+time+"</FONT></TD>\n";
  } //end of method

  private void saveTemplate(Hashtable userSelectedData, String userID) throws Exception {
    try{
      //System.out.println("------ SAVE Template for: "+userID+" name: "+(String)userSelectedData.get("TEMPLATE_NAME"));
      InvoiceData invoiceD = new InvoiceData(db);
      invoiceD.saveTemplate(userSelectedData,userID);
    }catch (Exception e) {
      throw e;
    }
  } //end of method

  private void getReportFields(Hashtable userSelectedData) {
    Vector reportFields = new Vector(50);
    Vector sqlFields = new Vector(50);
    String orderBy = "";
    String tmp = "";
    tmp = (String)userSelectedData.get("INVOICE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Invoice");
      sqlFields.addElement("invoice");
    }
    tmp = (String)userSelectedData.get("INVOICE_TYPE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Invoice Type");
      sqlFields.addElement("commodity");
    }
    tmp = (String)userSelectedData.get("INVOICE_DATE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Invoice Date");
      sqlFields.addElement("to_char(invoice_date,'mm/dd/yyyy')");
    }
    tmp = (String)userSelectedData.get("INVOICE_PERIOD");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Invoice Period");
      sqlFields.addElement("invoice_period_desc");
    }
    tmp = (String)userSelectedData.get("INVOICE_LINE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Invoice Line");
      sqlFields.addElement("invoice_line");
    }
    tmp = (String)userSelectedData.get("ACCOUNTING_SYSTEM");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Accounting System");
      sqlFields.addElement("account_sys_id");
    }
    tmp = (String)userSelectedData.get("CHARGE_NUM_1");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Charge Number 1");
      sqlFields.addElement("account_number");
    }
    tmp = (String)userSelectedData.get("CHARGE_NUM_2");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Charge Number 2");
      sqlFields.addElement("account_number2");
    }
    tmp = (String)userSelectedData.get("PO");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("PO");
      sqlFields.addElement("po_number");
    }
    tmp = (String)userSelectedData.get("SPLIT_CHARGE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Split Charge");
      sqlFields.addElement("percent_split_charge");
    }
    tmp = (String)userSelectedData.get("HUB");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Hub");
      sqlFields.addElement("hub_name");
    }
    tmp = (String)userSelectedData.get("FAC");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Facility");
      sqlFields.addElement("facility_display");
    }
    tmp = (String)userSelectedData.get("WA");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Work Area");
      sqlFields.addElement("application_desc");
    }
    tmp = (String)userSelectedData.get("REQUESTOR");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Requestor");
      sqlFields.addElement("requestor_name");
    }
    tmp = (String)userSelectedData.get("FINANCE_APPROVER");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Finance Approver");
      sqlFields.addElement("finance_approver_name");
    }
    tmp = (String)userSelectedData.get("MR_LINE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("MR-Line");
      sqlFields.addElement("mr_line");
    }
    tmp = (String)userSelectedData.get("WASTE_ORDER_NO");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Waste Order");
      sqlFields.addElement("waste_order_no");
    }
    tmp = (String)userSelectedData.get("WASTE_MANIFEST_ID");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Waste Manifest");
      sqlFields.addElement("waste_manifest_id");
    }
    tmp = (String)userSelectedData.get("PART_NUMBER");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Part Number");
      sqlFields.addElement("cat_part_no");
    }
    tmp = (String)userSelectedData.get("PART_DESC");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Part Description");
      sqlFields.addElement("part_description");
    }
    tmp = (String)userSelectedData.get("PACKAGING");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Packaging");
      sqlFields.addElement("packaging");
    }
    tmp = (String)userSelectedData.get("ITEM");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Item ID");
      sqlFields.addElement("item_id");
    }
    tmp = (String)userSelectedData.get("ITEM_DESC");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Item Description");
      //sqlFields.addElement("item_desc");
      sqlFields.addElement("fx_display_item_desc(item_id)");
    }
    tmp = (String)userSelectedData.get("ITEM_TYPE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Item Type");
      sqlFields.addElement("type_desc");
    }
    tmp = (String)userSelectedData.get("MFG");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Manufacturer");
      sqlFields.addElement("mfg_desc");
    }
    tmp = (String)userSelectedData.get("MFG_LOT");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Mfg Lot");
      sqlFields.addElement("mfg_lot");
    }
    tmp = (String)userSelectedData.get("HAAS_PO");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Haas PO");
      sqlFields.addElement("radian_po");
    }
    tmp = (String)userSelectedData.get("REFERENCE_NUMBER");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Reference");
      sqlFields.addElement("reference_number");
    }
    tmp = (String)userSelectedData.get("RECEIPT_ID");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Receipt ID");
      sqlFields.addElement("receipt_id");
      try {
        if (HelpObjs.countQuery(db,"select count(*) from tcmis_feature where feature = 'costReport.displayVoucher' and feature_mode = 1") > 0) {
          reportFields.addElement("Voucher URL");
          sqlFields.addElement("fx_voucher_url(receipt_id)");
        }
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
    tmp = (String)userSelectedData.get("DATE_DELIVERED");
    if ("Yes".equalsIgnoreCase(tmp)) {
      if ("Yes".equalsIgnoreCase((String)userSelectedData.get("DATE_DELIVERED_GROUP_BY_D"))) {
        reportFields.addElement("Date Delivered (group by day)");
        sqlFields.addElement("to_char(date_delivered,'yyyy-mm-dd')");
        orderBy += "to_char(date_delivered,'yyyy-mm-dd')";
      }else if ("Yes".equalsIgnoreCase((String)userSelectedData.get("DATE_DELIVERED_GROUP_BY_M"))) {
        reportFields.addElement("Date Delivered (group by month)");
        sqlFields.addElement("to_char(date_delivered,'yyyy-mm')");
        orderBy += "to_char(date_delivered,'yyyy-mm')";
      }else {
        reportFields.addElement("Date Delivered (group by year)");
        sqlFields.addElement("to_char(date_delivered,'yyyy')");
        orderBy += "to_char(date_delivered,'yyyy')";
      }
    }
    tmp = (String)userSelectedData.get("QUANTITY");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Quantity");
      sqlFields.addElement("quantity");
    }
    tmp = (String)userSelectedData.get("INVOICE_UNIT_PRICE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Invoice Unit Price");
      sqlFields.addElement("invoice_unit_price");
    }
    tmp = (String)userSelectedData.get("UNIT_REBATE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Unit Rebate");
      sqlFields.addElement("unit_rebate");
    }
    tmp = (String)userSelectedData.get("TOTAL_ADDITIONAL_CHARGE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Total Additional Charge");
      sqlFields.addElement("total_add_charge");
    }
	 tmp = (String)userSelectedData.get("TOTAL_SALES_TAX");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Total Sales Tax");
      sqlFields.addElement("total_sales_tax");
    }
	 tmp = (String)userSelectedData.get("SERVICE_FEE");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Service Fee");
      sqlFields.addElement("service_fee");
    }
    tmp = (String)userSelectedData.get("PER_AMOUNT");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("PEI Amount");
      sqlFields.addElement("pei_amount");
    }
    tmp = (String)userSelectedData.get("NET_AMOUNT");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Net Amount");
      sqlFields.addElement("net_amount");
    }
    tmp = (String)userSelectedData.get("MATERIAL_SAVINGS");
    if ("Yes".equalsIgnoreCase(tmp)) {
      reportFields.addElement("Material Savings");
      sqlFields.addElement("total_rebate");
    }
    //show currency
    showCurrency(reportFields,sqlFields);
    //put report fields into user's selected data
    userSelectedData.put("REPORT_FIELDS",reportFields);
    userSelectedData.put("SQL_FIELDS",sqlFields);
    userSelectedData.put("ORDER_BY",orderBy);
  } //end of method

  private void showCurrency(Vector reportFields, Vector sqlFields) {
    try {
      if (HelpObjs.showCurrency(db)) {
        if (sqlFields.contains("net_amount") || sqlFields.contains("pei_amount") ||
            sqlFields.contains("service_fee") || sqlFields.contains("total_add_charge") ||
            sqlFields.contains("unit_rebate") || sqlFields.contains("invoice_unit_price") ||
            sqlFields.contains("total_rebate") || sqlFields.contains("total_sales_tax")) {
          reportFields.addElement("Currency");
          sqlFields.addElement("currency_id");
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  } // end of method

  private Hashtable getUserDisplay(HttpServletRequest request) {
    Hashtable result = new Hashtable(61);   //give an extra space to report url and report fields vector
    result.put("REQUESTOR_ID",BothHelpObjs.makeBlankFromNull(request.getParameter("RequestorID")));
    result.put("REQUESTOR_NAME",BothHelpObjs.makeBlankFromNull(request.getParameter("requestorName")));
    result.put("REPORT_FIELD_COUNT",BothHelpObjs.makeBlankFromNull(request.getParameter("ReportFieldCount")));
    result.put("TEMPLATE_NAME",BothHelpObjs.makeBlankFromNull(request.getParameter("TemplateName")));
    result.put("HUB_C",BothHelpObjs.makeBlankFromNull(request.getParameter("hubC")));
    result.put("FACILITY",BothHelpObjs.makeBlankFromNull(request.getParameter("facilityName")));
    result.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(request.getParameter("workAreaName")));
    result.put("ACCOUNT_SYS",BothHelpObjs.makeBlankFromNull(request.getParameter("actSystem")));
    result.put("DIRECT",BothHelpObjs.makeBlankFromNull(request.getParameter("direct")));
    result.put("INDIRECT",BothHelpObjs.makeBlankFromNull(request.getParameter("inDirect")));
    result.put("CHARGE_NUMBER_1",BothHelpObjs.makeBlankFromNull(request.getParameter("chargeNumber1")));
    result.put("CHARGE_NUMBER_2",BothHelpObjs.makeBlankFromNull(request.getParameter("chargeNumber2")));
    result.put("SEARCH_BY",BothHelpObjs.makeBlankFromNull(request.getParameter("searchBy")));
    result.put("SEARCH_TEXT",BothHelpObjs.makeBlankFromNull(request.getParameter("searchText")));
    result.put("INVOICE_NUMBER",BothHelpObjs.makeBlankFromNull(request.getParameter("invoiceNumber")));
    result.put("INVOICE_PERIOD_C",BothHelpObjs.makeBlankFromNull(request.getParameter("invoicePeriodC")));
    result.put("START_DATE",BothHelpObjs.makeBlankFromNull(request.getParameter("startDate")));
    result.put("END_DATE",BothHelpObjs.makeBlankFromNull(request.getParameter("endDate")));
    result.put("INVOICE_START_DATE",BothHelpObjs.makeBlankFromNull(request.getParameter("invoiceStartDate")));
    result.put("INVOICE_END_DATE",BothHelpObjs.makeBlankFromNull(request.getParameter("invoiceEndDate")));
    //allow user to select multiple from drop down
    String[] invoiceType = request.getParameterValues("commodity");
    Vector tmpInvoiceType = new Vector(invoiceType.length);
    int i = 0;
    for (i = 0; i < invoiceType.length; i++) {
      tmpInvoiceType.addElement(invoiceType[i]);
    }
    result.put("COMMODITY_C",tmpInvoiceType);
    //item type
    String[] itemType = request.getParameterValues("itemTypeC");
    Vector tmpItemType = new Vector(itemType.length);
    for (i = 0; i < itemType.length; i++) {
      tmpItemType.addElement(itemType[i]);
    }
    result.put("ITEM_TYPE_C",tmpItemType);

    //REPORT FIELDS
    result.put("INVOICE",BothHelpObjs.makeBlankFromNull(request.getParameter("invoice")));
    result.put("INVOICE_TYPE",BothHelpObjs.makeBlankFromNull(request.getParameter("invoiceType")));
    result.put("INVOICE_DATE",BothHelpObjs.makeBlankFromNull(request.getParameter("invoiceDate")));
    result.put("INVOICE_PERIOD",BothHelpObjs.makeBlankFromNull(request.getParameter("invoicePeriod")));
    result.put("INVOICE_LINE",BothHelpObjs.makeBlankFromNull(request.getParameter("invoiceLine")));
    result.put("ACCOUNTING_SYSTEM",BothHelpObjs.makeBlankFromNull(request.getParameter("actSys")));
    result.put("CHARGE_NUM_1",BothHelpObjs.makeBlankFromNull(request.getParameter("chargeNum1")));
    result.put("CHARGE_NUM_2",BothHelpObjs.makeBlankFromNull(request.getParameter("chargeNum2")));
    result.put("PO",BothHelpObjs.makeBlankFromNull(request.getParameter("po")));
    result.put("SPLIT_CHARGE",BothHelpObjs.makeBlankFromNull(request.getParameter("splitCharge")));
    result.put("HUB",BothHelpObjs.makeBlankFromNull(request.getParameter("hub")));
    result.put("FAC",BothHelpObjs.makeBlankFromNull(request.getParameter("fac")));
    result.put("WA",BothHelpObjs.makeBlankFromNull(request.getParameter("wa")));
    result.put("REQUESTOR",BothHelpObjs.makeBlankFromNull(request.getParameter("requestor")));
    result.put("FINANCE_APPROVER",BothHelpObjs.makeBlankFromNull(request.getParameter("financeApprover")));
    result.put("MR_LINE",BothHelpObjs.makeBlankFromNull(request.getParameter("MRLine")));
    result.put("WASTE_ORDER_NO",BothHelpObjs.makeBlankFromNull(request.getParameter("wasteOrderNo")));
    result.put("WASTE_MANIFEST_ID",BothHelpObjs.makeBlankFromNull(request.getParameter("wasteManifestID")));
    result.put("PART_NUMBER",BothHelpObjs.makeBlankFromNull(request.getParameter("partNumber")));
    result.put("PART_DESC",BothHelpObjs.makeBlankFromNull(request.getParameter("partDesc")));
    result.put("PACKAGING",BothHelpObjs.makeBlankFromNull(request.getParameter("packaging")));
    result.put("ITEM",BothHelpObjs.makeBlankFromNull(request.getParameter("item")));
    result.put("ITEM_DESC",BothHelpObjs.makeBlankFromNull(request.getParameter("itemDesc")));
    result.put("ITEM_TYPE",BothHelpObjs.makeBlankFromNull(request.getParameter("itemType")));
    //result.put("MATERIAL_CATEGORY",BothHelpObjs.makeBlankFromNull(request.getParameter("materialCat")));
    result.put("MFG",BothHelpObjs.makeBlankFromNull(request.getParameter("mfg")));
    result.put("MFG_LOT",BothHelpObjs.makeBlankFromNull(request.getParameter("mfgLot")));
    result.put("HAAS_PO",BothHelpObjs.makeBlankFromNull(request.getParameter("radianPo")));
    result.put("REFERENCE_NUMBER",BothHelpObjs.makeBlankFromNull(request.getParameter("referenceNumber")));
    result.put("RECEIPT_ID",BothHelpObjs.makeBlankFromNull(request.getParameter("receiptID")));
    result.put("DATE_DELIVERED",BothHelpObjs.makeBlankFromNull(request.getParameter("dateDelivered")));
    result.put("DATE_DELIVERED_GROUP_BY_D",BothHelpObjs.makeBlankFromNull(request.getParameter("dayC")));
    result.put("DATE_DELIVERED_GROUP_BY_M",BothHelpObjs.makeBlankFromNull(request.getParameter("monthC")));
    result.put("DATE_DELIVERED_GROUP_BY_Y",BothHelpObjs.makeBlankFromNull(request.getParameter("yearC")));
    result.put("QUANTITY",BothHelpObjs.makeBlankFromNull(request.getParameter("quantity")));
    result.put("INVOICE_UNIT_PRICE",BothHelpObjs.makeBlankFromNull(request.getParameter("invoiceUnitPrice")));
    result.put("UNIT_REBATE",BothHelpObjs.makeBlankFromNull(request.getParameter("unitRebate")));
    result.put("TOTAL_ADDITIONAL_CHARGE",BothHelpObjs.makeBlankFromNull(request.getParameter("totalAdditionalCharge")));
	 result.put("TOTAL_SALES_TAX",BothHelpObjs.makeBlankFromNull(request.getParameter("totalSalesTax")));
	 result.put("SERVICE_FEE",BothHelpObjs.makeBlankFromNull(request.getParameter("serviceFee")));
    result.put("PER_AMOUNT",BothHelpObjs.makeBlankFromNull(request.getParameter("perAmount")));     //41 elements in the hashtable
    result.put("NET_AMOUNT",BothHelpObjs.makeBlankFromNull(request.getParameter("netAmount")));
    result.put("MATERIAL_SAVINGS",BothHelpObjs.makeBlankFromNull(request.getParameter("materialSavings")));
    return result;
  } //end of method

  private Hashtable getDefaultDisplay() {
    Hashtable result = new Hashtable(60);
    result.put("REQUESTOR_ID","0");
    result.put("REQUESTOR_NAME","");
    result.put("REPORT_FIELD_COUNT","0");
    result.put("TEMPLATE_NAME","default");
    result.put("HUB_C","");
    result.put("FACILITY","");
    result.put("WORK_AREA","");
    result.put("ACCOUNT_SYS","");
    result.put("DIRECT","Yes");
    result.put("INDIRECT","");
    result.put("CHARGE_NUMBER_1","");
    result.put("CHARGE_NUMBER_2","");
    result.put("SEARCH_BY","");
    result.put("SEARCH_TEXT","");
    result.put("INVOICE_NUMBER","");
    result.put("INVOICE_PERIOD_C","");
    result.put("START_DATE","");
    result.put("END_DATE","");
    result.put("INVOICE_START_DATE","");
    result.put("INVOICE_END_DATE","");
    //result.put("MATERIAL_CATEGORY_C","");
    Vector v = new Vector(1);
    v.addElement("All Types");
    result.put("COMMODITY_C",v);
    Vector vt = new Vector(1);
    vt.addElement("All Types");
    result.put("ITEM_TYPE_C",vt);
    //REPORT FIELDS
    result.put("INVOICE","");
    result.put("INVOICE_TYPE","");
    result.put("INVOICE_DATE","");
    result.put("INVOICE_PERIOD","");
    result.put("INVOICE_LINE","");
    result.put("ACCOUNTING_SYSTEM","");
    result.put("CHARGE_NUM_1","");
    result.put("CHARGE_NUM_2","");
    result.put("PO","");
    result.put("SPLIT_CHARGE","");
    result.put("HUB","");
    result.put("FAC","");
    result.put("WA","");
    result.put("REQUESTOR","");
    result.put("FINANCE_APPROVER","");
    result.put("MR_LINE","");
    result.put("WASTE_ORDER_NO","");
    result.put("WASTE_MANIFEST_ID","");
    result.put("PART_NUMBER","");
    result.put("PART_DESC","");
    result.put("PACKAGING","");
    result.put("ITEM","");
    result.put("ITEM_DESC","");
    result.put("ITEM_TYPE","");
    //result.put("MATERIAL_CATEGORY","");
    result.put("MFG","");
    result.put("MFG_LOT","");
    result.put("HAAS_PO","");
    result.put("REFERENCE_NUMBER","");
    result.put("RECEIPT_ID","");
    result.put("DATE_DELIVERED","");
    result.put("DATE_DELIVERED_GROUP_BY_D","Yes");
    result.put("DATE_DELIVERED_GROUP_BY_M","");
    result.put("DATE_DELIVERED_GROUP_BY_Y","");
    result.put("QUANTITY","");
    result.put("INVOICE_UNIT_PRICE","");
    result.put("UNIT_REBATE","");
    result.put("TOTAL_ADDITIONAL_CHARGE","");
	 result.put("TOTAL_SALES_TAX","");
	 result.put("SERVICE_FEE","");
    result.put("PER_AMOUNT","");
    result.put("NET_AMOUNT","");
    result.put("MATERIAL_SAVINGS","");
    return result;
  } //end of method

  private String createComboBoxData(Hashtable initialData) {
    String tmp = new String("");
    //first build hub array
    String tmpHub = "var hubID = new Array(";

    String tmpFac = "var facID = new Array(";
    String altFacID = "var altFacID = new Array();\n ";
    String altFacDesc = "var altFacDesc = new Array();\n ";
    String altWAID = "var altWAID = new Array();\n ";
    String altWADesc = "var altWADesc = new Array();\n ";
    String altActSysID = "var altActSysID = new Array();\n ";

    Vector hubIDV = (Vector)initialData.get("HUB_ID");
    int i = 0;
    for (int ii = 0 ; ii < hubIDV.size(); ii++) {
      String hubID = (String)hubIDV.elementAt(ii);
      tmpHub += "\""+hubID+"\""+",";
      //second build facility array
      Hashtable fh = (Hashtable)initialData.get(hubID);
      Vector facIDV = (Vector)fh.get("FACILITY_ID");
      Vector facDescV = (Vector)fh.get("FACILITY_DESC");
      if (facIDV.size() > 1 && !facIDV.contains("All Facilities")) {
        facIDV.insertElementAt("All Facilities",0);
        facDescV.insertElementAt("All Facilities",0);
        Vector wAreaID = new Vector(1);
        wAreaID.addElement("All Work Areas");
        Vector wAreaDesc = new Vector(1);
        wAreaDesc.addElement("All Work Areas");
        Vector tmpAcS = new Vector(1);
        tmpAcS.addElement("All Accounting Systems");
        Hashtable tmpFacWA = new Hashtable(3);
        tmpFacWA.put("APPLICATION",wAreaID);
        tmpFacWA.put("APPLICATION_DESC",wAreaDesc);
        tmpFacWA.put("ACCOUNTING_SYSTEM",tmpAcS);
        fh.put("All Facilities",tmpFacWA);
      }
      altFacID += "altFacID[\""+hubID+"\"] = new Array(";
      altFacDesc += "altFacDesc[\""+hubID+"\"] = new Array(";
      for (i = 0; i < facIDV.size(); i++) {
        String facID = (String)facIDV.elementAt(i);
        tmp += "\""+facID+"\""+",";
        altFacID += "\""+facID+"\""+",";
        altFacDesc += "\""+(String)facDescV.elementAt(i)+"\""+",";
        //build work area
        Hashtable h = (Hashtable)fh.get(facID);
        Vector application = (Vector)h.get("APPLICATION");
        Vector applicationDesc = (Vector)h.get("APPLICATION_DESC");
        if (application.size() > 1 && !application.contains("All Work Areas")) {
          application.insertElementAt("All Work Areas",0);
          applicationDesc.insertElementAt("All Work Areas",0);
        }
        altWAID += "altWAID[\""+facID+"\"] = new Array(";
        altWADesc += "altWADesc[\""+facID+"\"] = new Array(";
        for (int j = 0; j < application.size(); j++) {
          altWAID += "\""+(String)application.elementAt(j)+"\""+",";
          altWADesc += "\""+(String)applicationDesc.elementAt(j)+"\""+",";
        }
        altWAID = altWAID.substring(0,altWAID.length()-1) + ");\n";
        altWADesc = altWADesc.substring(0,altWADesc.length()-1) + ");\n";

        //next build accounting system
        altActSysID += "altActSysID[\""+facID+"\"] = new Array(";
        Vector actSys = (Vector)h.get("ACCOUNTING_SYSTEM");
        if (actSys.size() > 1 && !actSys.contains("All Accounting Systems")) {
          actSys.insertElementAt("All Accounting Systems",0);
        }
        for (int k = 0; k < actSys.size(); k++) {
          altActSysID += "\""+(String)actSys.elementAt(k)+"\""+",";
        }
        altActSysID = altActSysID.substring(0,altActSysID.length()-1) + ");\n";
      }
      //removing the last commas ','
      altFacID = altFacID.substring(0,altFacID.length()-1) + ");\n";
      altFacDesc = altFacDesc.substring(0,altFacDesc.length()-1) + ");\n";
    }

    //account info
    String altActType = "var altActType = new Array();\n ";
    //String tmpActType = "var accountType = new Array(\"All\",";
    Hashtable actType = (Hashtable)initialData.get("ACCOUNT_INFO");
    Enumeration enumCollec = actType.keys();
    while (enumCollec.hasMoreElements()) {
      String tmpAT = enumCollec.nextElement().toString();
      //tmpActType += "\""+tmpAT+"\""+",";
      Vector v = (Vector)actType.get(tmpAT);
      altActType += "altActType[\""+tmpAT+"\"] = new Array(";
      for (i = 0; i < v.size(); i++) {
        altActType += "\""+(String)v.elementAt(i)+"\""+",";
      }
      altActType = altActType.substring(0,altActType.length()-1) + ");\n";
    }
    //tmpActType = tmpActType.substring(0,tmpActType.length()-1) + ");\n";

    //removing the last commas ','
    if (tmpHub.indexOf(",") > 1) {
      tmpHub = tmpHub.substring(0,tmpHub.length()-1) +");\n";
    }

    return (tmpHub+" "+tmpFac+tmp.substring(0,tmp.length()-1) +");\n"+" "+altFacID+" "+altFacDesc+" "+altWAID+" "+altWADesc+" "+altActSysID+" "+altActType);
  }

  public StringBuffer printInvoiceTitleBar(String Message) {
      StringBuffer Msg = new StringBuffer();
			Msg.append("<div class=\"topNavigation\" id=\"topNavigation\">\n");
      Msg.append("<TABLE BORDER=0 WIDTH=100% CLASS=\"moveupmore\">\n");
      Msg.append("<TR VALIGN=\"TOP\">\n");
      Msg.append("<TD WIDTH=\"200\">\n");
      Msg.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
      Msg.append("</TD>\n");
      Msg.append("<TD ALIGN=\"right\">\n");
      Msg.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
      Msg.append("</TD>\n");
      Msg.append("</TR>\n");
      Msg.append("</Table>\n");
      Msg.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      Msg.append("<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">\n");
      Msg.append(Message);
      Msg.append("</TD>\n");
      Msg.append("</TR>\n");
      Msg.append("</TABLE>\n");
			Msg.append("</div>\n");
      return Msg;
  }

  private void buildDisplayData(String userAction, Hashtable initialData, Hashtable selectedData,PrintWriter costReportOut) {
    try {
      costReportOut.println(radian.web.HTMLHelpObj.printHTMLHeader("Cost Report","invoice.js",intcmIsApplication));
      costReportOut.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      costReportOut.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      costReportOut.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");

      costReportOut.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
      costReportOut.println(createComboBoxData(initialData));
      costReportOut.println("// -->\n </SCRIPT>\n");

      costReportOut.println("</HEAD>  \n");
      if ("generateR".equalsIgnoreCase(userAction)) {
        costReportOut.println("<BODY onLoad=\"openReport()\">\n");
      }else if ("saveT".equalsIgnoreCase(userAction) || "openT".equalsIgnoreCase(userAction)) {
        costReportOut.println("<BODY onLoad=\"templateLoaded()\">\n");
      }else {
        costReportOut.println("<BODY onLoad=\"initialDisplay()\">\n");
      }
      costReportOut.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      costReportOut.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
      costReportOut.println("</DIV>\n");
      costReportOut.println("<DIV ID=\"MAINPAGE\">\n");
      costReportOut.println(printInvoiceTitleBar("Cost Report\n"));
      costReportOut.println("<FORM method=\"POST\" NAME=\"invoicereports\" onSubmit=\"return SubmitOnlyOnce()\">\n");
      //send url back to client
      if ("generateR".equalsIgnoreCase(userAction)) {
        String reportURL = (String)selectedData.get("REPORT_URL");
        if (reportURL == null || reportURL.length() < 5) {
          reportURL = radian.web.tcmisResourceLoader.gethosturl();
        }
        costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"reportURL\" VALUE=\""+reportURL+"\">\n");
      }
      //5-10-03 send company_id to client
      costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"companyID\" VALUE=\""+db.getClient().toLowerCase()+"\">\n");
      costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Details\">\n");
      costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"PoRequired\" VALUE=\"n\">\n");
      costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"RequestorID\" VALUE=\""+(String)selectedData.get("REQUESTOR_ID")+"\">\n");
      costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"ReportFieldCount\" VALUE=\""+(String)selectedData.get("REPORT_FIELD_COUNT")+"\">\n");
      costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"TemplateName\" VALUE=\""+(String)selectedData.get("TEMPLATE_NAME")+"\">\n");
      costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"TemplateList\" VALUE=\""+(String)initialData.get("TEMPLATE_LIST")+"\">\n");
      costReportOut.println("<TABLE BORDER=\"0\" ID=\"mainheadertable\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      costReportOut.println("<TR>\n");
      //String message = "Test Invoice";

      String message = (String)selectedData.get("TEMPLATE_NAME");
      if (!message.equals("default") && message.length() > 0) {
        //costReportOut.println("<TD>\n");
        //costReportOut.println("&nbsp;</TD>\n");
        costReportOut.println("<TD WIDTH=\"10%\" COLSPAN=\"4\" HEIGHT=\"5\" ALIGN=\"CENTER\" ID=\"messagerow\"><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Template Name: "+message+"</B></FONT></TD>\n");
        costReportOut.println("</TD>\n");
        costReportOut.println("<TR>\n");
        costReportOut.println("</TR>\n");
      }

      //left table
      costReportOut.println("<TD WIDTH=\"45%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"TOP\">\n");
      costReportOut.println("<TABLE BORDER=\"0\" ID=\"headerLefttable\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      String tmpSelect = "";
      //requestor
      tmpSelect = (String)selectedData.get("REQUESTOR_NAME");
      costReportOut.println("<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Requestor:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"40%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      //costReportOut.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"requestorNane\" ID=\"requestorNane\" value=\""+tmpSelect+"\" size=\"20\">\n");
      //<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\"
      costReportOut.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"requestorName\" ID=\"requestorName\" value=\""+tmpSelect+"\" size=\"20\" READONLY>\n");
      costReportOut.println("<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"...\" onClick= \"searchUserWin()\" NAME=\"searchWin\"><IMG src=\"/images/search_small.gif\" alt=\"Search\"></BUTTON>\n");

      //costReportOut.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Clear\" onClick= \"clearRequestor(document.invoicereports.requestorName)\" NAME=\"clearReq\">\n");
      costReportOut.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Clear\" onClick= \"clearRequestor(document.invoicereports.RequestorID,document.invoicereports.requestorName)\" NAME=\"clearReq\">\n");

      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //Hub
      tmpSelect = (String)selectedData.get("HUB_C");
      costReportOut.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Reporting Group:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.invoicereports.hubC)\">\n");
      Vector hubID = (Vector)initialData.get("HUB_ID");
      Vector hubDesc = (Vector)initialData.get("HUB_DESC");
      String selectedHub = "";
      int i = 0;
      selectedHub = (String)hubID.firstElement();   //select the first facility if none were selected
      String preSelect = "";
      for (i = 0; i < hubID.size(); i++) {
        preSelect = "";
        if (tmpSelect.equalsIgnoreCase((String)hubID.elementAt(i))) {
          preSelect = "selected";
          selectedHub = (String)hubID.elementAt(i);
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)hubID.elementAt(i)+"\">"+(String)hubDesc.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //Facility
      tmpSelect = (String)selectedData.get("FACILITY");
      costReportOut.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Facility:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" size=\"1\" OnChange=\"facilityChanged(document.invoicereports.facilityName)\">\n");
      Hashtable fh = (Hashtable)initialData.get(selectedHub);
      Vector facID = (Vector)fh.get("FACILITY_ID");
      Vector facDesc = (Vector)fh.get("FACILITY_DESC");
      String selectedFac = "";
      i = 0;
      selectedFac = (String)facID.firstElement();   //select the first facility if none were selected
      preSelect = "";
      for (i = 0; i < facID.size(); i++) {
        preSelect = "";
        if (tmpSelect.equalsIgnoreCase((String)facID.elementAt(i))) {
          preSelect = "selected";
          selectedFac = (String)facID.elementAt(i);
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)facID.elementAt(i)+"\">"+(String)facDesc.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //Work Area
      tmpSelect = (String)selectedData.get("WORK_AREA");
      costReportOut.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Work Area:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      //costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"WorkAreaFullName\" ID=\"WorkAreaFullName\" value=\"\">\n");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" >\n");
      Hashtable h = (Hashtable)fh.get(selectedFac);
      Vector application = (Vector)h.get("APPLICATION");
      Vector applicationDesc = (Vector)h.get("APPLICATION_DESC");
      for (i = 0; i < application.size(); i++) {
        preSelect = "";
        if (tmpSelect.equalsIgnoreCase((String)application.elementAt(i))) {
          preSelect = "selected";
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)application.elementAt(i)+"\">"+(String)applicationDesc.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");

      //accounting system
      tmpSelect = (String)selectedData.get("ACCOUNT_SYS");
      costReportOut.println("<TD WIDTH=\"20%\" CLASS=\"announce\"  ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Accounting System:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      //costReportOut.println("<INPUT TYPE=\"hidden\" NAME=\"ActSys\" ID=\"ActSys\" value=\"\">\n");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"actSystem\" ID=\"actSystem\" size=\"1\" OnChange=\"accountSysChanged(document.invoicereports.actSystem)\">\n");
      Vector actV = (Vector)h.get("ACCOUNTING_SYSTEM");
      for (i = 0; i < actV.size(); i++) {
        preSelect = "";
        if (tmpSelect.equalsIgnoreCase((String)actV.elementAt(i))) {
          preSelect = "selected";
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)actV.elementAt(i)+"\">"+(String)actV.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //charge type
      tmpSelect = (String)selectedData.get("DIRECT");
      costReportOut.println("<TD WIDTH=\"20%\" ALIGN=\"RIGHT\" CLASS=\"announce\" ID=\"directCell\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" onClick= \"directChecked(this)\" name=\"direct\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+">\n");
      costReportOut.println("<B>Direct</B>\n");
      costReportOut.println("</TD>\n");
      tmpSelect = (String)selectedData.get("INDIRECT");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" CLASS=\"announce\" ID=\"indirectCell\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" onClick= \"inDirectChecked(this)\" name=\"inDirect\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+">\n");
      costReportOut.println("<B>Indirect</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //charge number 1
      tmpSelect = (String)selectedData.get("CHARGE_NUMBER_1");
      costReportOut.println("<TD WIDTH=\"20%\" CLASS=\"announce\" ID=\"chargeNumber1Label\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Charge number 1:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"chargeNumber1\" ID=\"chargeNumber1\" value=\""+tmpSelect+"\" size=\"20\">\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //charge number 2
      tmpSelect = (String)selectedData.get("CHARGE_NUMBER_2");
      costReportOut.println("<TD WIDTH=\"20%\" CLASS=\"announce\" ID=\"chargeNumber2Label\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Charge number 2:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"chargeNumber2\" ID=\"chargeNumber2\" value=\""+tmpSelect+"\" size=\"20\">\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //create blank row so the data stay where it is
      //blank row 1
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" ID=\"blankRow1\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("&nbsp;</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //blank row 2
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" ID=\"blankRow2\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("&nbsp;</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      costReportOut.println("</TABLE>\n");     //end of left table
      costReportOut.println("</TD>\n");        //end of left table column

      //right table
      //costReportOut.println("<TD>\n");
      costReportOut.println("<TD WIDTH=\"55%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"TOP\">\n");
      costReportOut.println("<TABLE BORDER=\"0\" ID=\"headerRighttable\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      //Search by
      tmpSelect = (String)selectedData.get("SEARCH_BY");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
      costReportOut.println("<B>Search By:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"searchBy\" ID=\"searchBy\" size=\"1\" >\n");
      costReportOut.println("<OPTION VALUE=\"None\">Select One</OPTION>\n");
      Vector tmpV = (Vector)initialData.get("SEARCH_BY");
      for (i = 0; i < tmpV.size(); i++) {
        preSelect = "";
        if (tmpSelect.equalsIgnoreCase((String)tmpV.elementAt(i))) {
          preSelect = "selected";
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)tmpV.elementAt(i)+"\">"+(String)tmpV.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      //search text
      tmpSelect = (String)selectedData.get("SEARCH_TEXT");
      costReportOut.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchText\" ID=\"search\" value=\""+tmpSelect+"\" size=\"20\">\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("</TR>\n");
      //invoice number
      tmpSelect = (String)selectedData.get("INVOICE_NUMBER");
      costReportOut.println("<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Invoice #:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"invoiceNumber\" ID=\"invoiceNumber\" value=\""+tmpSelect+"\" onChange=\"invalidatePo()\" size=\"20\">\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //Invoice Periods
      tmpSelect = (String)selectedData.get("INVOICE_PERIOD_C");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
      costReportOut.println("<B>Invoice Period:</B>&nbsp;\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"invoicePeriodC\" ID=\"invoicePeriodC\" size=\"1\" >\n");
      Vector invoicePeriod = (Vector)initialData.get("INVOICE_PERIOD");
      Vector invoicePeriodDesc = (Vector)initialData.get("INVOICE_PERIOD_DESC");
      if (invoicePeriod.size() > 1 && !invoicePeriod.contains("All")) {
        invoicePeriod.insertElementAt("All",0);
        invoicePeriodDesc.insertElementAt("All Invoice Periods",0);
      }
      for (i = 0; i < invoicePeriod.size(); i++) {
        preSelect = "";
        if (tmpSelect.equalsIgnoreCase((String)invoicePeriod.elementAt(i))) {
          preSelect = "selected";
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)invoicePeriod.elementAt(i)+"\">"+(String)invoicePeriodDesc.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //invoice date
      tmpSelect = (String)selectedData.get("INVOICE_START_DATE");
      costReportOut.println("<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Invoiced Between:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoiceStartDate\" ID=\"invoiceStartDate\" value=\""+tmpSelect+"\" size=\"10\" READONLY><FONT SIZE=\"5\"><a href=\"javascript: void(0);\" ID=\"invoiceStartDate\" onClick=\"return getCalendar(document.invoicereports.invoiceStartDate);\">&diams;</a></FONT>\n");
      costReportOut.println("<B>and:</B>\n");
      tmpSelect = (String)selectedData.get("INVOICE_END_DATE");
      costReportOut.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"invoiceEndDate\" ID=\"invoiceEndDate\" value=\""+tmpSelect+"\" size=\"10\" READONLY><FONT SIZE=\"5\"><a href=\"javascript: void(0);\" ID=\"invoiceEndDate\" onClick=\"return getCalendar(document.invoicereports.invoiceEndDate);\">&diams;</a></FONT>\n");
      costReportOut.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Clear\" onClick= \"clearDate(document.invoicereports.invoiceStartDate,document.invoicereports.invoiceEndDate)\" NAME=\"clearSD\">\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //delivered date
      tmpSelect = (String)selectedData.get("START_DATE");
      costReportOut.println("<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<B>Delivered Between:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"startDate\" ID=\"startDate\" value=\""+tmpSelect+"\" size=\"10\" READONLY><FONT SIZE=\"5\"><a href=\"javascript: void(0);\" ID=\"deliverStartDate\" onClick=\"return getCalendar(document.invoicereports.startDate);\">&diams;</a></FONT>\n");
      costReportOut.println("<B>and:</B>\n");
      tmpSelect = (String)selectedData.get("END_DATE");
      costReportOut.println("<INPUT CLASS=\"INVISIBLEHEADWHITE\" TYPE=\"text\" NAME=\"endDate\" ID=\"endDate\" value=\""+tmpSelect+"\" onChange=\"invalidatePo()\" size=\"10\" READONLY><FONT SIZE=\"5\"><a href=\"javascript: void(0);\" ID=\"deliverEndData\" onClick=\"return getCalendar(document.invoicereports.endDate);\">&diams;</a></FONT>\n");
      costReportOut.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Clear\" onClick= \"clearDate(document.invoicereports.startDate,document.invoicereports.endDate)\" NAME=\"clearED\">\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");

      //commodity
      Vector tmpSelectV = (Vector)selectedData.get("COMMODITY_C");
      costReportOut.println("<TD WIDTH=\"10%\" CLASS=\"announce\"  ALIGN=\"RIGHT\" VALIGN=\"BOTTOM\">\n");
      costReportOut.println("<B>Invoice Type:</B>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" VALIGN=\"BOTTOM\">\n");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"commodity\" ID=\"commodity\" size=\"5\" multiple>\n");
      Vector comV = (Vector)initialData.get("COMMODITY");
      if (!comV.contains("All Types")) {
        comV.insertElementAt("All Types",0);
      }
      for (i = 0; i < comV.size(); i++) {
        preSelect = "";
        if (tmpSelectV.contains((String)comV.elementAt(i))) {
          preSelect = "selected";
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)comV.elementAt(i)+"\">"+(String)comV.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      //item type
      costReportOut.println("<B>Item Type:</B>\n");
      tmpSelectV = (Vector)selectedData.get("ITEM_TYPE_C");
      costReportOut.println("<SELECT CLASS=\"HEADER\" NAME=\"itemTypeC\" ID=\"itemTypeC\" size=\"5\" multiple>\n");
      Vector itemTypeID = (Vector)initialData.get("ITEM_TYPE_ID");
      Vector itemTypeDesc = (Vector)initialData.get("ITEM_TYPE_DESC");
      if (!itemTypeID.contains("All Types")) {
        itemTypeID.insertElementAt("All Types",0);
        itemTypeDesc.insertElementAt("All Types",0);
      }
      for (i = 0; i < itemTypeID.size(); i++) {
        preSelect = "";
        if (tmpSelectV.contains((String)itemTypeID.elementAt(i))) {
          preSelect = "selected";
        }
        costReportOut.println("<option "+preSelect+" value=\""+(String)itemTypeID.elementAt(i)+"\">"+(String)itemTypeDesc.elementAt(i)+"</option>\n");
      }
      costReportOut.println("</SELECT>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      costReportOut.println("</TABLE>\n");     //end of right table
      costReportOut.println("</TD>\n");        //end of right table column

      costReportOut.println("</TABLE>\n");     //end of top table

      //bottom table start here
      costReportOut.println("<CENTER>\n");
      costReportOut.println("<TABLE BORDER=\"0\" ID=\"reportTable\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"60%\" >\n");
      costReportOut.println("<TR>\n");
      //report fields
      message = "Report Fields";
      costReportOut.println("<TD>\n");
      costReportOut.println("&nbsp;</TD>\n");
      costReportOut.println("<TD WIDTH=\"10%\" HEIGHT=\"5\" ALIGN=\"LEFT\" ID=\"messagerow\"><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>"+message+"</B></FONT></TD>\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");

      //first column
      costReportOut.println("<TD>\n");
      costReportOut.println("<TABLE BORDER=\"0\" ID=\"columnTable\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      costReportOut.println("<TR>\n");
      //invoice
      tmpSelect = (String)selectedData.get("INVOICE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"invoice\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.invoice)\">\n");
      costReportOut.println("Invoice\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //Invoice type
      tmpSelect = (String)selectedData.get("INVOICE_TYPE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"invoiceType\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.invoiceType)\">\n");
      costReportOut.println("Invoice Type\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //invoice date
      tmpSelect = (String)selectedData.get("INVOICE_DATE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"invoiceDate\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.invoiceDate)\">\n");
      costReportOut.println("Invoice Date\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //invoice Period
      tmpSelect = (String)selectedData.get("INVOICE_PERIOD");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"invoicePeriod\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.invoicePeriod)\">\n");
      costReportOut.println("Invoice Period\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //invoice line
      tmpSelect = (String)selectedData.get("INVOICE_LINE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"invoiceLine\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.invoiceLine)\">\n");
      costReportOut.println("Invoice Line\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //accounting system
      tmpSelect = (String)selectedData.get("ACCOUNTING_SYSTEM");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"actSys\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.actSys)\">\n");
      costReportOut.println("Accounting System\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //charge number 1
      tmpSelect = (String)selectedData.get("CHARGE_NUM_1");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"chargeNum1\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.chargeNum1)\">\n");
      costReportOut.println("Charge Number 1\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //charge number 2
      tmpSelect = (String)selectedData.get("CHARGE_NUM_2");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"chargeNum2\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.chargeNum2)\">\n");
      costReportOut.println("Charge Number 2\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //po
      tmpSelect = (String)selectedData.get("PO");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"po\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.po)\">\n");
      costReportOut.println("PO\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //split charge
      tmpSelect = (String)selectedData.get("SPLIT_CHARGE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"splitCharge\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.splitCharge)\">\n");
      costReportOut.println("Split Charge\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //facility
      tmpSelect = (String)selectedData.get("FAC");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"fac\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.fac)\">\n");
      costReportOut.println("Facility\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //work area
      tmpSelect = (String)selectedData.get("WA");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"wa\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.wa)\">\n");
      costReportOut.println("Work Area\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //requestor
      tmpSelect = (String)selectedData.get("REQUESTOR");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"requestor\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.requestor)\">\n");
      costReportOut.println("Requestor\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
		//blank row
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" ID=\"blankRow2\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("&nbsp;</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n"); 
		costReportOut.println("</TABLE>\n");     //end of first column table

      //second column
      costReportOut.println("<TD>\n");
      costReportOut.println("<TABLE BORDER=\"0\" ID=\"column2Table\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      costReportOut.println("<TR>\n");
      //finance approver
      tmpSelect = (String)selectedData.get("FINANCE_APPROVER");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"financeApprover\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.financeApprover)\">\n");
      costReportOut.println("Finance Approver\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //mr-line
      tmpSelect = (String)selectedData.get("MR_LINE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"MRLine\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.MRLine)\">\n");
      costReportOut.println("MR-Line\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //waste order
      tmpSelect = (String)selectedData.get("WASTE_ORDER_NO");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"wasteOrderNo\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.wasteOrderNo)\">\n");
      costReportOut.println("Waste Order\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //waste manifest
      tmpSelect = (String)selectedData.get("WASTE_MANIFEST_ID");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"wasteManifestID\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.wasteManifestID)\">\n");
      costReportOut.println("Waste Manifest\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //part number
      tmpSelect = (String)selectedData.get("PART_NUMBER");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"partNumber\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.partNumber)\">\n");
      costReportOut.println("Part Number\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //part desc
      tmpSelect = (String)selectedData.get("PART_DESC");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"partDesc\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.partDesc)\">\n");
      costReportOut.println("Part Description\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //packaging
      tmpSelect = (String)selectedData.get("PACKAGING");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"packaging\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.packaging)\">\n");
      costReportOut.println("Packaging\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //item
      tmpSelect = (String)selectedData.get("ITEM");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"item\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.item)\">\n");
      costReportOut.println("Item\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //item desc
      tmpSelect = (String)selectedData.get("ITEM_DESC");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"itemDesc\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.itemDesc)\">\n");
      costReportOut.println("Item Description\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //item type
      tmpSelect = (String)selectedData.get("ITEM_TYPE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"itemType\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.itemType)\">\n");
      costReportOut.println("Item Type\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //manufacturer
      tmpSelect = (String)selectedData.get("MFG");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"mfg\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.mfg)\">\n");
      costReportOut.println("Manufacturer\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      boolean showPO = false;
      try {
        showPO = HelpObjs.countQuery(db,"select count(*) from tcmis_feature where feature = 'costReport.showRadianPo' and feature_mode = 1") > 0;
      }catch (Exception e) {
        e.printStackTrace();
        showPO = false;
      }
      if (showPO) {
        //haas po
        tmpSelect = (String)selectedData.get("HAAS_PO");
        costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
        costReportOut.println("<INPUT type=\"checkbox\" name=\"radianPo\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.radianPo)\">\n");
        costReportOut.println("Haas PO\n");
        costReportOut.println("</TD>\n");
        costReportOut.println("<TR>\n");
        costReportOut.println("</TR>\n");
      }else {
        //blank row 1
        costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" ID=\"blankRow1\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
        costReportOut.println("&nbsp;</TD>\n");
        costReportOut.println("<TR>\n");
        costReportOut.println("</TR>\n");
      }
      //blank row
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" ID=\"blankRow2\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("&nbsp;</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
		//blank row
      costReportOut.println("<TD WIDTH=\"30%\" ALIGN=\"LEFT\" ID=\"blankRow2\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      costReportOut.println("&nbsp;</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
		costReportOut.println("</TABLE>\n");     //end of second column table

      //third column
      costReportOut.println("<TD>\n");
      costReportOut.println("<TABLE BORDER=\"0\" ID=\"column3Table\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" >\n");
      costReportOut.println("<TR>\n");
      //reference number
      tmpSelect = (String)selectedData.get("REFERENCE_NUMBER");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"referenceNumber\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.referenceNumber)\">\n");
      costReportOut.println("Reference\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //receipt ID
      tmpSelect = (String)selectedData.get("RECEIPT_ID");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"receiptID\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.receiptID)\">\n");
      costReportOut.println("Receipt ID\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //mfg lot
      tmpSelect = (String)selectedData.get("MFG_LOT");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"mfgLot\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.mfgLot)\">\n");
      costReportOut.println("Mfg Lot\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("</TR>\n");
      //date delivered
      tmpSelect = (String)selectedData.get("DATE_DELIVERED");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"dateDelivered\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.dateDelivered)\">\n");
      costReportOut.println("Date Delivered (Group by: \n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");

      tmpSelect = (String)selectedData.get("DATE_DELIVERED_GROUP_BY_D");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\" >\n");
      costReportOut.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
      costReportOut.println("<INPUT type=\"radio\" name=\"dayC\" value=\"Yes\" "+("Yes".equalsIgnoreCase(tmpSelect)?"checked":"")+" OnClick=\"dateDeliveredGroupByChanged(document.invoicereports.dayC)\">\n");
      costReportOut.println("Day\n");
      tmpSelect = (String)selectedData.get("DATE_DELIVERED_GROUP_BY_M");
      costReportOut.println("<INPUT type=\"radio\" name=\"monthC\" value=\"Yes\" "+("Yes".equalsIgnoreCase(tmpSelect)?"checked":"")+" OnClick=\"dateDeliveredGroupByChanged(document.invoicereports.monthC)\">\n");
      costReportOut.println("Month\n");
      tmpSelect = (String)selectedData.get("DATE_DELIVERED_GROUP_BY_Y");
      costReportOut.println("<INPUT type=\"radio\" name=\"yearC\" value=\"Yes\" "+("Yes".equalsIgnoreCase(tmpSelect)?"checked":"")+" OnClick=\"dateDeliveredGroupByChanged(document.invoicereports.yearC)\">\n");
      costReportOut.println("Year)\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");

      //quantity
      tmpSelect = (String)selectedData.get("QUANTITY");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"quantity\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.quantity)\">\n");
      costReportOut.println("Quantity\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //invoice unit price
      tmpSelect = (String)selectedData.get("INVOICE_UNIT_PRICE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"invoiceUnitPrice\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.invoiceUnitPrice)\">\n");
      costReportOut.println("Invoice Unit Price\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //unit rebate
      tmpSelect = (String)selectedData.get("UNIT_REBATE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"unitRebate\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.unitRebate)\">\n");
      costReportOut.println("Unit Rebate\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //additional charges
      tmpSelect = (String)selectedData.get("TOTAL_ADDITIONAL_CHARGE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"totalAdditionalCharge\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.totalAdditionalCharge)\">\n");
      costReportOut.println("Total Additional Charge\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //sales tax
      tmpSelect = (String)selectedData.get("TOTAL_SALES_TAX");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"totalSalesTax\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.totalSalesTax)\">\n");
      costReportOut.println("Total Sales Tax\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
		//service fee
      tmpSelect = (String)selectedData.get("SERVICE_FEE");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"serviceFee\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.serviceFee)\">\n");
      costReportOut.println("Service Fee\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //pei amount
      tmpSelect = (String)selectedData.get("PER_AMOUNT");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"perAmount\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.perAmount)\">\n");
      costReportOut.println("PEI Amount\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //net amount
      tmpSelect = (String)selectedData.get("NET_AMOUNT");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"netAmount\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.netAmount)\">\n");
      costReportOut.println("Net Amount\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      //material saving
      tmpSelect = (String)selectedData.get("MATERIAL_SAVINGS");
      costReportOut.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      costReportOut.println("<INPUT type=\"checkbox\" name=\"materialSavings\" value=\"Yes\" "+(tmpSelect.equalsIgnoreCase("Yes")?"checked":"")+" OnClick=\"reportFieldChanged(document.invoicereports.materialSaving)\">\n");
      costReportOut.println("Material Savings\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("<TR>\n");
      costReportOut.println("</TR>\n");
      costReportOut.println("</TABLE>\n");      //end of third column table

      //save and open template button
      costReportOut.println("<TR>\n");
      costReportOut.println("<TD>\n");
      costReportOut.println("&nbsp;</TD>\n");
      costReportOut.println("</TR>\n");
      costReportOut.println("<TD WIDTH=\"30%\" CLASS=\"announce\"  ALIGN=\"CENTER\" COLSPAN=\"4\"  VALIGN=\"MIDDLE\">\n");
      costReportOut.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Clear Template\" onClick= \"return clearTemplate(name,this)\" NAME=\"clearT\">&nbsp;\n");
      costReportOut.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Save Template\" onClick= \"return saveTemplate(name,this)\" NAME=\"saveT\">&nbsp;\n");
      costReportOut.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Open Template\" onClick= \"return openTemplate(name,this)\" NAME=\"openT\">&nbsp;\n");
      costReportOut.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Generate Report\" onClick= \"return generateReport(name,this)\" NAME=\"generateR\">&nbsp;\n");
      costReportOut.println("</TD>\n");
      costReportOut.println("</TABLE>\n");
      costReportOut.println("<CENTER>\n");

      //final
      costReportOut.println("</FORM>\n");
      costReportOut.println("</BODY>\n");
      costReportOut.println("</HTML>\n");
    }catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method
} //end of class
