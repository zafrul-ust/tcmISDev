package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import java.math.BigDecimal;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2002
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 03-27-03 Coloring Critical orders red
 * 08-15-03 - Sending emails through common object
 * 11-10-04 - Ability to sort the Picklist
 * 12-07-04 - Ability to print consolidated item picklist
 */



public class genPickList
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private PrintWriter out = null;
    private boolean completeSuccess = true ;
    private boolean noneToUpd = true ;
		private String thedecidingRandomNumber = null;
    //Nawaz 06-27-02
    protected boolean allowupdate;
    private boolean intcmIsApplication = false;
    private static org.apache.log4j.Logger invlog = org.apache.log4j.Logger.getLogger(genPickList.class);

    public void setupdateStatus(boolean id)
    {
      this.allowupdate = id;
    }

    private boolean getupdateStatus()  throws Exception
    {
     return this.allowupdate;
    }

    protected String dropDownJs;
    public void setdropDownJs(String compjs)
    {
      this.dropDownJs = compjs;
    }

    private String getdropDownJs()  throws Exception
    {
     return this.dropDownJs;
    }


    public genPickList(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    //Process the HTTP Post request passed from whoever called it
    public void doResult(HttpServletRequest request, HttpServletResponse response,HttpSession session)
          throws ServletException,  IOException
    {

        out = response.getWriter();
        response.setContentType("text/html");

        PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

        String branch_plant =(String)session.getAttribute("HUB_BACK");
        String loginId = BothHelpObjs.makeBlankFromNull((String)session.getAttribute("loginId"));
        String personnelid = BothHelpObjs.makeBlankFromNull((String)session.getAttribute("PERSONNELID"));
        String CompanyID = session.getAttribute("COMPANYID").toString();

        String User_Action = null;
        User_Action = request.getParameter("UserAction");
        if (User_Action == null)
              User_Action = "New";

        String subuserAction = null;
        subuserAction = request.getParameter("SubUserAction");
        if (subuserAction == null)
              subuserAction = "New";

        String facility   = request.getParameter("FacName");
        if (facility == null)
            facility = "";

        String regenPickid = null;
        regenPickid = request.getParameter("regenPickid");
        if (regenPickid == null)
              regenPickid = "";

		String User_Sort=request.getParameter( "sortPicklist" );
		if ( User_Sort == null )
 		    User_Sort="";
		  //User_Sort="PR_NUMBER";

		 thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
		 if (thedecidingRandomNumber == null)
			thedecidingRandomNumber = "";
		 thedecidingRandomNumber = thedecidingRandomNumber.trim();

		 Random rand = new Random();
		 int tmpReq = rand.nextInt();
		 Integer tmpReqNum = new Integer(tmpReq);


				String expdays=request.getParameter( "expdays" );
				if ( expdays == null )
				 expdays="30";

	      if ( expdays.trim().length() == 0 )
	      {
      	 expdays="30";
      	}

				try {
				 if (thedecidingRandomNumber.length() > 0) {
					String randomnumberfromsesion = session.getAttribute("LOGRNDSERCHPICKCOOKIE") == null ? "" :
					 session.getAttribute("LOGRNDSERCHPICKCOOKIE").toString();
					if (randomnumberfromsesion.equalsIgnoreCase(thedecidingRandomNumber)) {
					 thedecidingRandomNumber = tmpReqNum.toString();
					 session.setAttribute("LOGRNDSERCHPICKCOOKIE", thedecidingRandomNumber);
					}
					else {
					 thedecidingRandomNumber = tmpReqNum.toString();
					 session.setAttribute("LOGRNDSERCHPICKCOOKIE", thedecidingRandomNumber);
					 session.setAttribute("DATA", new Vector());

					 buildHeader(" ", "", "", branch_plant, User_Sort);
					 out.println("<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>");
					 return;
					}
				 }
				 else {
					thedecidingRandomNumber = tmpReqNum.toString();
					session.setAttribute("LOGRNDSERCHPICKCOOKIE", thedecidingRandomNumber);
				 }

            if ( subuserAction.equalsIgnoreCase("regeneratepicks") )
            {
               Vector searchdata = new Vector();
               searchdata  = this.getSearchData(regenPickid,facility);

               session.removeAttribute("PRINTDATA");

               session.setAttribute("PICKLIST_ID", regenPickid );
               session.setAttribute("DATA", searchdata);

               buildHeader(""+regenPickid+"",subuserAction,"",branch_plant,User_Sort);
               buildDetails(searchdata,"regeneratepicks");
            }
            if ( subuserAction.equalsIgnoreCase("reprintpicks") )
            {
             Vector synch_data = (Vector) session.getAttribute("DATA");
             String picklistid = session.getAttribute("PICKLIST_ID").toString();

             Vector print_data =  SynchprintData(synch_data);

             session.setAttribute("PRINTDATA", print_data );

             buildHeader(picklistid,subuserAction,"Yes",branch_plant,User_Sort);
             buildDetails(synch_data,"");

            }
			else if ( subuserAction.equalsIgnoreCase("consolidatedpicks") )
			{
			 Vector synch_data = (Vector) session.getAttribute("DATA");
			 String picklistid = session.getAttribute("PICKLIST_ID").toString();

			 Vector print_data =  SynchprintData(synch_data);

			 session.setAttribute("PRINTDATA", print_data );

			 buildHeader(picklistid,subuserAction,"Yes",branch_plant,User_Sort);
			 buildDetails(synch_data,"");

			}
            else if ( subuserAction.equalsIgnoreCase("PrintBOLDetail") )
            {
             Vector synch_data = (Vector) session.getAttribute("DATA");
             String picklistid = session.getAttribute("PICKLIST_ID").toString();

             Vector print_data =  SynchprintData(synch_data);

             session.setAttribute("PRINTDATA", print_data );

             buildHeader(picklistid,subuserAction,"Yes",branch_plant,User_Sort);
             buildDetails(synch_data,"");

            }
            else if ( subuserAction.equalsIgnoreCase("PrintBOL") )
            {
             Vector synch_data = (Vector) session.getAttribute("DATA");
             String picklistid = session.getAttribute("PICKLIST_ID").toString();

             Vector print_data =  SynchprintData(synch_data);

             session.setAttribute("PRINTDATA", print_data );

             buildHeader(picklistid,subuserAction,"No",branch_plant,User_Sort);
             buildDetails(synch_data,"");

            }
            else if ( subuserAction.equalsIgnoreCase("PrintBOXLabels") )
            {
             Vector synch_data = (Vector) session.getAttribute("DATA");
             String picklistid = session.getAttribute("PICKLIST_ID").toString();
             Vector print_data =  SynchprintData(synch_data);

             session.setAttribute("PRINTDATA", print_data );

             buildHeader(picklistid,subuserAction,"No",branch_plant,User_Sort);
             buildDetails(synch_data,"");
            }
            else if ( subuserAction.equalsIgnoreCase("updatepicks") )
            {
                Vector synch_data = (Vector) session.getAttribute("DATA");

                String updatestatus = (String)session.getAttribute("UPDATEPICKLIST");

                Vector print_data =  SynchprintData(synch_data);
                session.setAttribute("PRINTDATA", print_data );

                if ("Yes".equalsIgnoreCase(updatestatus))
                {
                buildHeader(" ","","",branch_plant,User_Sort);
                out.println(radian.web.HTMLHelpObj.printMessageinTable("This Form was Already Submitted."));
                return;
                }

                int uid = DbHelpers.getNextVal(db,"print_batch_seq");

                session.setAttribute("PICKLIST_ID", ""+uid+"" );

                Hashtable updateresults = UpdateDetails( synch_data, branch_plant, personnelid,CompanyID,uid,expdays);

                session.setAttribute("UPDATEPICKLIST", "Yes");

                Boolean result = (Boolean)updateresults.get("RESULT");

                session.setAttribute("UPDATEDONE", "YES");
                //Vector errordata = (Vector)updateresults.get("ERRORDATA");

                Vector errordata  = this.getSearchData(""+uid+"","");
                session.setAttribute("DATA", errordata );

                boolean resulttotest =  result.booleanValue();

                 if ( false == resulttotest )
                    {
                        buildHeader(""+uid+"","","",branch_plant,User_Sort);
                        if ( true == noneToUpd )
                        {
                           out.println(radian.web.HTMLHelpObj.printMessageinTable("No Item was Choosen"));
                           //buildDetails(errordata,"updatepicks"));
                        }
                        else
                        {
                            out.println(radian.web.HTMLHelpObj.printMessageinTable("An Error Occurred.<BR>Please Check Data and try Again."));
                            buildDetails(errordata,"updatepicks");
                        }
                        out.close();
                    }
                else
                {
                   if ( true == completeSuccess )
                   {
                        //out.println(radian.web.HTMLHelpObj.printMessageinTable("All Your Selections Were Successfully Updated"));
                        //out.println(radian.web.HTMLHelpObj.printHTMLCompletSuccess());
                        buildHeader(""+uid+"",subuserAction,"",branch_plant,User_Sort);
                        buildDetails(errordata,"updatepicks");
                        out.close();
                   }
                   else
                   {
                      //out.println(radian.web.HTMLHelpObj.printHTMLPartialSuccess(errMsgs));
                      buildHeader(""+uid+"","","",branch_plant,User_Sort);
                      out.println(radian.web.HTMLHelpObj.printMessageinTable("Some of Your Selections Shown below Were not Updated"));
                      buildDetails(errordata,"updatepicks");
                      out.close();
                   }
               }

              //clean up
              errordata        = null;
              updateresults    = null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            out.println(radian.web.HTMLHelpObj.printError("Print Picklist",intcmIsApplication));
        }

        return;

    }

    private Vector getSearchData(String picklistid,String facilityid) throws Exception
    {
        Vector Data = new Vector();
        Hashtable DataH = new Hashtable();
        Hashtable summary = new Hashtable();
        boolean flagforwhere = false;

        DBResultSet dbrs = null;
        ResultSet rs = null;

        int count = 0;
        summary.put("TOTAL_NUMBER", new Integer(count) );
        Data.addElement(summary);

        int num_rec = 0;

       try
          {
          String query = "select to_char(a.NEED_DATE,'mm/dd/yyyy') NEED_DATE1, a.* from picklist_reprint_view a where picklist_id = "+picklistid+"";

          if (facilityid.length() > 1 && !"All".equalsIgnoreCase(facilityid))
          {
              query += " and FACILITY_ID = '"+facilityid+"' ";
          }
				  query+=" order by PR_NUMBER,LINE_ITEM";

          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();

          while (rs.next())
          {
          DataH = new Hashtable();
          num_rec++;
          DataH.put("CAT_PART_NO",rs.getString("CAT_PART_NO")==null?"":rs.getString("CAT_PART_NO"));
          DataH.put("PR_NUMBER",rs.getString("PR_NUMBER")==null?"":rs.getString("PR_NUMBER"));
          DataH.put("LINE_ITEM",rs.getString("LINE_ITEM")==null?"":rs.getString("LINE_ITEM"));
          DataH.put("FACILITY_ID",rs.getString("FACILITY_ID")==null?"&nbsp;":rs.getString("FACILITY_ID"));
          DataH.put("APPLICATION",rs.getString("APPLICATION")==null?"&nbsp;":rs.getString("APPLICATION"));

          DataH.put("NEED_DATE",rs.getString("NEED_DATE1")==null?"":rs.getString("NEED_DATE1"));
          DataH.put("PICK_QTY",rs.getString("PICK_QTY")==null?"":rs.getString("PICK_QTY"));
          DataH.put("PART_DESCRIPTION",rs.getString("PART_DESCRIPTION")==null?"&nbsp;":rs.getString("PART_DESCRIPTION"));
          DataH.put("PACKAGING",rs.getString("PACKAGING")==null?"&nbsp;":rs.getString("PACKAGING"));
          DataH.put("REQUESTOR",rs.getString("REQUESTOR")==null?"&nbsp;":rs.getString("REQUESTOR"));
          DataH.put("STOCKING_METHOD",rs.getString("STOCKING_METHOD")==null?"&nbsp;":rs.getString("STOCKING_METHOD"));

          DataH.put("COMPANY_ID",rs.getString("COMPANY_ID")==null?"":rs.getString("COMPANY_ID"));

          DataH.put("DELIVERY_POINT",rs.getString("DELIVERY_POINT")==null?"&nbsp;":rs.getString("DELIVERY_POINT"));
          DataH.put("SHIP_TO_LOCATION_ID",rs.getString("SHIP_TO_LOCATION_ID")==null?"&nbsp;":rs.getString("SHIP_TO_LOCATION_ID"));

          //DataH.put("QUANTITY",rs.getString("QUANTITY")==null?"&nbsp;":rs.getString("QUANTITY"));

          DataH.put("NEED_DATE_PREFIX",rs.getString("NEED_DATE_PREFIX")==null?"&nbsp;":rs.getString("NEED_DATE_PREFIX"));
          DataH.put( "MR_NOTES",rs.getString( "MR_NOTES" ) == null ? "&nbsp;" : rs.getString( "MR_NOTES" ) );
          DataH.put("CRITICAL",rs.getString("CRITICAL")==null?"&nbsp;":rs.getString("CRITICAL"));
          DataH.put("LINE_STATUS", "Yes" );
          DataH.put("USERCHK", "" );

          Data.addElement(DataH);
          }

          }
          catch (Exception e)
          {
          e.printStackTrace();
          throw e;
          }
          finally
          {
              dbrs.close();
          }

          Hashtable recsum  = new Hashtable();
          recsum.put("TOTAL_NUMBER", new Integer(num_rec) );
          Data.setElementAt(recsum, 0);
          Data.trimToSize();
          return Data;
    }

     public boolean createPicklist( Hashtable hD,String Branch_plant, String personnelID, int picklistid,String expdays )
    {
        // get main information
        boolean result = false;

        String prnumber       = hD.get("PR_NUMBER").toString().trim();
        String lineitem       = hD.get("LINE_ITEM").toString().trim();
        String needdate     = hD.get("NEED_DATE").toString().trim();
        String company  = hD.get("COMPANY_ID").toString().trim();
        String printdate     = hD.get("PRINT_DATE").toString().trim();

        invlog.debug("Creating P_CREATE_PICKLIST Pick List "+prnumber+"-"+lineitem+"   pickID "+picklistid+"   "+Branch_plant+"   "+personnelID+"   "+needdate+"   "+company+" Expire Dates  "+expdays+"");
        CallableStatement cs = null;
        try
        {
            Connection connect1 = null;

            connect1 = db.getConnection();

            cs = connect1.prepareCall("{call P_CREATE_PICKLIST(?,?,?,?,?,?,?,?,?,?)}");
            cs.setInt(1,picklistid);
            cs.setString(2,company);
            cs.setBigDecimal( 3,new BigDecimal(""+ prnumber +"") );
            cs.setString(4,lineitem);
            cs.setString(5,Branch_plant);
						if (needdate.length() > 0 ) {cs.setTimestamp(6,radian.web.HTMLHelpObj.getDateFromString(needdate));} else {cs.setNull(6,java.sql.Types.DATE);}
            cs.setInt(7,Integer.parseInt(personnelID));
            cs.setTimestamp(8,radian.web.HTMLHelpObj.getDateFromString("dd MMM yyyy HH:mm:ss",printdate));
            cs.registerOutParameter(9,java.sql.Types.VARCHAR);
						if (expdays.trim().length() > 0)
						{
							cs.setInt(10, Integer.parseInt(expdays));
						}
						else
						{
							cs.setNull(10,java.sql.Types.INTEGER);
						}

            cs.execute();

            String errorcode = cs.getString(9);

            if (errorcode == null)
            {
            result = true;
            }
            else
            {
              invlog.debug("Result from P_CREATE_PICKLIST procedure Error Code:  "+errorcode);
              result = false;
            }

	    /*System.out.println("Calling P_LINE_ITEM_ALLOCATE ");
	    cs = connect1.prepareCall("{call P_LINE_ITEM_ALLOCATE(?,?,?)}");
            cs.setString(1,company);
            cs.setInt(2,Integer.parseInt(prnumber));
            cs.setString(3,lineitem);
            //cs.setString(4,Branch_plant);
	    cs.execute();*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
            radian.web.emailHelpObj.senddatabaseHelpemails(" Error Calling  P_CREATE_PICKLIST from picking page","Error occured while running call P_CREATE_PICKLIST\nError Msg:\n"+e.getMessage()+"\n Parameters passed for P_CREATE_PICKLIST:  pickID "+picklistid+" \n PRNUMBER "+prnumber+"-"+lineitem+" \nBranch Plant "+Branch_plant+"  \nPersonnel Id "+personnelID+" \nNeed Date  "+needdate+" \nCompany  "+company);
            result = false;
        }
        finally
        {
            if (cs != null) {
              try {
                      cs.close();
              } catch (Exception se) {se.printStackTrace();}
            }
        }

        return result;
    }


    private Vector SynchprintData(Vector in_data)
    {
        Vector new_data = new Vector();
        Hashtable sum = ( Hashtable)( in_data.elementAt(0));
        int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();
        Vector prnumbers = new Vector();
        Vector linenumber = new Vector();
        Vector picklistid = new Vector();
        Hashtable FResult = new Hashtable();

        try
        {
            for ( int i = 1 ; i< count+1 ; i++)
            {
                Hashtable hD = new Hashtable();
                hD = ( Hashtable)( in_data.elementAt(i));

                String mrline = hD.get("PR_NUMBER").toString();
                String lineitem = hD.get("LINE_ITEM").toString();

                /*if (prnumbers.contains(mrline))
                {
                  System.out.println("PR number already there ");
                }
                else*/
                {
                 //System.out.println("Adding prnumber "+mrline);
                 prnumbers.addElement(mrline);
                 linenumber.addElement(lineitem);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("*** Error on Hub Receiving Page ***");
            out.println(radian.web.HTMLHelpObj.printError("Picking",intcmIsApplication));
        }
        //System.out.println("Exit Method : "+ "SynchServerData()" );

        FResult.put("mr_number",prnumbers);
        FResult.put("line_number",linenumber);
        FResult.put("picklist_number",picklistid);

        new_data.add(FResult);
        return new_data;
    }

    private Hashtable UpdateDetails(Vector data, String BranchPlant, String personnelid, String CompanyID, int pickListId,String expdays)
    {
        //System.out.println("Enter Method : "+ "UpdateDetails()" );
        boolean result = false;

        Hashtable updateresult = new Hashtable();
        Vector errordata = new Vector();

        try
        {
            Hashtable summary = new Hashtable();
            summary = (Hashtable)data.elementAt(0);
            int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;
            //System.out.println("Total Number in UpdateDetails : "+ total );
            int vsize = data.size();
            errordata.addElement(summary);
            //System.out.println("Vector Size in UpdateDetails : "+ vsize  );

            boolean one_success = false;
            for (int i = 1; i < total+1  ; i++)
            {
                Hashtable hD = new Hashtable();
                hD = (Hashtable) data.elementAt(i);
                String Line_Check = "";
                Line_Check      =  (hD.get("USERCHK")==null?"&nbsp;":hD.get("USERCHK").toString());

                if ("yes".equalsIgnoreCase(Line_Check))
                {
                    noneToUpd = false;
                    System.out.println("check Found at line # : "+ i );
                    boolean line_result = false;
                    line_result = this.createPicklist(hD, BranchPlant, personnelid,pickListId,expdays ); // update database
                    if ( false == line_result)
                    {
                    completeSuccess = false;

                    hD.remove("LINE_STATUS");
                    hD.put("LINE_STATUS", "NO");
                    errordata.addElement(hD);
                    }
                    if ( true == line_result)
                    {
                    one_success = true;
                    hD.remove("LINE_STATUS");
                    hD.put("LINE_STATUS", "YES");

                    errordata.addElement(hD);
                    }
                }
                else
                {
                     errordata.addElement(hD);
                }
            } //end of for
            if ( true == one_success )
            {  result = true;  }
        }
        catch (Exception e)
        {
            result = false;
            e.printStackTrace();
        }
       updateresult.put("RESULT",new Boolean(result));
       updateresult.put("ERRORDATA",errordata);
      return updateresult;
    }
    //

    private void buildHeader(String picklistid,String useraction,String boloptions,String hubname,String sortby)
    {
        //System.out.println("Enter Method : "+ "buildHeader()" );
        //StringBuffer Msg = new StringBuffer();
        try
        {
          out.println("<HTML><HEAD>\n");
          out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
          out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
          out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
          out.println("<title>Picking</title>\n");

          out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
          out.println("<SCRIPT SRC=\"/clientscripts/genpicklist.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");

          if ( "PrintBOLDetail".equalsIgnoreCase(useraction) || "PrintBOL".equalsIgnoreCase(useraction) )
          {
            out.println("<BODY onLoad=\"doPrintbol()\">\n");
          }
          else if ("updatepicks".equalsIgnoreCase(useraction))
          {
            out.println("<BODY onLoad=\"doPrintpicks('')\">\n");
          }
		  else if ("reprintpicks".equalsIgnoreCase(useraction))
		  {
			out.println("<BODY onLoad=\"doPrintpicks('"+sortby+"')\">\n");
		  }
		  else if ("consolidatedpicks".equalsIgnoreCase(useraction))
		  {
			out.println("<BODY onLoad=\"doPrintconsolpicks()\">\n");
		  }
          else if ("PrintBOXLabels".equalsIgnoreCase(useraction) )
          {
            out.println("<BODY onLoad=\"doPrintbox("+hubname+")\">\n");
          }
          else
          {
            out.println("<BODY>\n");
          }

          out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
          out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
          out.println("</DIV>\n");
          out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

          out.println(radian.web.HTMLHelpObj.PrintTitleBar("<B>Picking</B>\n"));
          out.println("<FORM method=\"POST\" NAME=\"genPickList\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+bundle.getString("SEARCH_PICKLIST")+"\">\n");
          //start table #1
          out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

          out.println("<TR>\n");

          out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("Picklist No:\n");
          out.println("</TD>\n");
          out.println("<TD ALIGN=\"LEFT\" WIDTH=\"15%\" CLASS=\"announce\">\n");
          out.println("PL "+picklistid+"\n");
          out.println("</TD>\n");

          /*out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");*/
          //Return
          out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
          out.println("<INPUT TYPE=\"submit\" VALUE=\"Return to Picking\" onclick= \"return goBack(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
          out.println("</TD>\n");

          //Print BOL SHORT
          out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOL Short\" onclick= \"return bolshort(this)\" NAME=\"SearchButton\">&nbsp;\n");
          out.println("</TD>\n");

          //Box Labels
          out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOX Labels\" onclick= \"return boxlabels(this)\" NAME=\"UpdateButton\">&nbsp;\n");
          out.println("</TD>\n");

          out.println("</TR>\n");

          out.println("<TR>\n");
          //Blanks
          /*out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
          out.println("<TD WIDTH=\"15%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");*/

          /*out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
          out.println("<TD WIDTH=\"10%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");*/

		  // Sort
		  out.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\">\n" );
		  out.println( "<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\"><B>Sort Picklist By:</B></FONT>&nbsp;\n" );
		  out.println( "</TD>\n" );
		  out.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
		  out.println( "<SELECT NAME=\"sortPicklist\" CLASS=\"HEADER\" size=\"1\">\n" );
		  out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
		  Hashtable sortresult=new Hashtable();
		  sortresult.put( "Facility/Work Area","FACILITY_ID,APPLICATION" );
		  sortresult.put( "Item Id","ITEM_ID" );
		  sortresult.put( "Part Number","CAT_PART_NO" );
		  sortresult.put( "MR","PR_NUMBER,LINE_ITEM" );
		  //sortresult.put( "Needed","NEED_DATE" );
		  out.println( radian.web.HTMLHelpObj.getDropDown( sortresult,sortby ) );
		  out.println( "</SELECT>\n" );
		  out.println( "</TD>\n" );

	      //Print Consolidated Picklist
	      out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
	      out.println("<INPUT TYPE=\"submit\" VALUE=\"Print Consolidated Picklist\" onclick= \"return printconsolidatedpicks(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
	      out.println("</TD>\n");

          //Print BOL Long
          out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOL Long\" onclick= \"return bollong(this)\" NAME=\"UpdateButton\">&nbsp;\n");
          out.println("</TD>\n");

          //Reprint Pick List
          out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
          out.println("<INPUT TYPE=\"submit\" VALUE=\"Re-Print Picklist\" onclick= \"return reprintpicks(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
          out.println("</TD>\n");

          out.println("</TR>\n");

          /*out.println("<TR>\n");
          //Blanks
          out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
          out.println("<TD WIDTH=\"15%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");

          out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
          out.println("<TD WIDTH=\"10%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");

          out.println("</TR>\n");*/

          out.println("</TABLE>\n");

          out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
          out.println("<tr><td>");
          out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
          out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\"NEW\">\n");
          out.println("<INPUT TYPE=\"hidden\" NAME=\"boldetails\" VALUE=\""+boloptions+"\">\n");
					out.println( "<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" + thedecidingRandomNumber + "\">\n" );
          out.println("</TD></tr>");
          out.println("</table>\n");
          //end table #2

    }
    catch (Exception e)
    {
        e.printStackTrace();
        //System.out.println("*** Error on Hub Receiving Page ***");
        out.println(radian.web.HTMLHelpObj.printError("Picking",intcmIsApplication));
    }

    //System.out.println("Exit Method : "+ "buildHeader()" );
    return;
}
//end of method

private void buildDetails(Vector data,String SubuserAction)
{

    //System.out.println("Enter Method : "+ "buildDetails()" );
    //StringBuffer Msg = new StringBuffer();
    String checkednon = "";


    try
    {
        Hashtable summary = new Hashtable();
        summary = (Hashtable)data.elementAt(0);
        int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;
        //System.out.println("Total Number : "+ total );
        int vsize = data.size();
        //System.out.println("Vector Size  : "+ vsize  );
		out.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showpickingpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );

        //start table #3
        //out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
        out.println("<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");


        int i = 0;  //used for detail lines
        int lineadded = 0;

        out.println("<tr align=\"center\">\n");
        if (this.getupdateStatus() && !SubuserAction.equalsIgnoreCase("Generate"))
        {
        out.println("<TH width=\"2%\"  height=\"38\">OK</TH>\n");
        }
        out.println("<TH width=\"5%\"  height=\"38\">Facility</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Work Area</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Delivery Point</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Ship To</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Requestor</TH>\n");
        out.println("<TH width=\"7%\"  height=\"38\">MR-Line</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Part No.</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Type</TH>\n");
        out.println("<TH width=\"35%\" height=\"38\">Part Desc</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Packaging</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Quantity</TH>\n");
        out.println("<TH width=\"5%\"  height=\"38\">Needed</TH>\n");
        out.println( "<TH width=\"7%\"  height=\"38\">MR Notes</TH>\n" );
        out.println("</tr>\n");

        for (int loop  = 0 ; loop  < total  ; loop++)
        {
           i++;
           boolean createHdr = false;

           if ( (lineadded%10 == 0) && (lineadded != 0 ) )
           {
           createHdr = true;
           }

            if ( createHdr )
            {
                out.println("<tr align=\"center\">\n");
                if (this.getupdateStatus() && !SubuserAction.equalsIgnoreCase("Generate"))
                {
                out.println("<TH width=\"2%\"  height=\"38\">OK</TH>\n");
                }
                out.println("<TH width=\"5%\"  height=\"38\">Facility</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Work Area</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Delivery Point</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Ship To</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Requestor</TH>\n");
                out.println("<TH width=\"7%\"  height=\"38\">MR-Line</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Part No.</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Type</TH>\n");
                out.println("<TH width=\"35%\"  height=\"38\">Part Desc</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Packagin</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Quantity</TH>\n");
                out.println("<TH width=\"5%\"  height=\"38\">Needed</TH>\n");
                out.println( "<TH width=\"7%\"  height=\"38\">MR Notes</TH>\n" );
                out.println("</tr>\n");
            }

            Hashtable hD = new Hashtable();
            hD = (Hashtable) data.elementAt(i);

            // get main information
            String Qty      = hD.get("PICK_QTY").toString();
            String facility = hD.get("FACILITY_ID").toString();
            String workarea = hD.get("APPLICATION").toString();
            String prnumber = hD.get("PR_NUMBER").toString();
            String lineitem = hD.get("LINE_ITEM").toString();
            String part     = hD.get("CAT_PART_NO").toString();
            String needdate = hD.get("NEED_DATE").toString();
            String partdesc = hD.get("PART_DESCRIPTION").toString();
            String pkg      = hD.get("PACKAGING").toString();
            String requestor= hD.get("REQUESTOR").toString();
            String type     = hD.get("STOCKING_METHOD").toString();

            String deliverypt = hD.get("DELIVERY_POINT").toString();
            String shipto = hD.get("SHIP_TO_LOCATION_ID").toString();

            String needdateprefix  = hD.get("NEED_DATE_PREFIX").toString();

            String Line_Check      =  (hD.get("USERCHK")==null?"&nbsp;":hD.get("USERCHK").toString());
            if (Line_Check.equalsIgnoreCase("yes")){checkednon = "checked";}else{checkednon = "";}


            String LINE_STATUS = hD.get("LINE_STATUS").toString();
            String mrnotes=hD.get( "MR_NOTES" ).toString();

            String Color = " ";
            if (lineadded%2==0)
            {
                Color ="CLASS=\"white";
            }
            else
            {
                Color ="CLASS=\"blue";
            }

            String ismrCritical= ( hD.get( "CRITICAL" ) == null ? "&nbsp;" : hD.get( "CRITICAL" ).toString() );
            if ( "Y".equalsIgnoreCase( ismrCritical ) )
            {
              Color="CLASS=\"red";
            }

			if ( "S".equalsIgnoreCase( ismrCritical ) )
			{
			  Color="CLASS=\"pink";
			}

            String chkbox_value = "no";
            if ( checkednon.equalsIgnoreCase("checked"))
            {
                chkbox_value = "yes";
            }

            if ("NO".equalsIgnoreCase(LINE_STATUS) )
            {
            Color ="CLASS=\"error";
            }

            if ("YES".equalsIgnoreCase(LINE_STATUS) )
            {
            lineadded++;
            out.println("<tr align=\"center\">\n");
            out.println("<INPUT TYPE=\"hidden\" value=\""+(chkbox_value)+"\" "+checkednon+" NAME=\"row_chk"+i+"\">\n");
            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+facility+"</td>\n");
            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+workarea+"</td>\n");

            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+deliverypt+"</td>\n");
            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+shipto+"</td>\n");

            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+requestor+"</td>\n");
            out.println("<td "+Color+"\" width=\"7%\" height=\"38\">"+prnumber+" - "+lineitem+"</td>\n");

            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+part+"</td>\n");
            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+type+"</td>\n");
            out.println("<td "+Color+"l\" width=\"35%\" height=\"38\">"+partdesc+"</td>\n");
            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+pkg+"</td>\n");
            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+Qty+"</td>\n");
            out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+needdateprefix+" "+needdate+"</td>\n");
            out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + mrnotes + "</td>\n" );
            out.println("</tr>\n");
            }
        }

        out.println("</table>\n");
        //
        out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
        out.println("<tr>");
        out.println("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
        out.println("</TD></tr>");
        out.println("</table>\n");


        out.println("</form>\n");
        out.println("</body></html>\n");
    }
    catch (Exception e)
    {
        e.printStackTrace();
        out.println(radian.web.HTMLHelpObj.printError("Picking",intcmIsApplication));
    }

    //System.out.println("Exit Method : "+ "buildDetails()" );
    return ;
}
//End of method

}
//END OF CLASS

