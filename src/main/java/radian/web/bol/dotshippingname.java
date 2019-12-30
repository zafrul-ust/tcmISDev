package radian.web.bol;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *add link to show MSDS. Javascript error .
 * 08-06-03 - Checking the Not Regulated check box when none of the hazard values are checked. Making the read only version of the page really read only
 * 09-09-03 Catching Number Format Exception if a non integer is passed to the item_desc function
 * 12-15-03 Ability to lookup proper shipping names from a vv table
 */

public class dotshippingname
{
    private TcmISDBModel db = null;
    private boolean completeSuccess = true ;
    private boolean allowupdate;
	private boolean clientdot;

	private boolean intcmIsApplication = false;

    public void setupdateStatus( boolean id )
    {
      this.allowupdate=id;
    }

    private boolean getupdateStatus() throws Exception
    {
      return this.allowupdate;
    }

	public void setclientdotStatus( boolean id )
	{
	  this.clientdot=id;
	}

	private boolean getclientdotStatus() throws Exception
	{
	  return this.clientdot;
   }

    public dotshippingname(TcmISDBModel d)
    {
        db = d;
    }

    public void doResult(HttpServletRequest request, HttpServletResponse response,HttpSession dotsession)  throws ServletException,  IOException
    {
        PrintWriter out = response.getWriter();
        String personnelid= dotsession.getAttribute( "PERSONNELID" ) == null ? "" : dotsession.getAttribute( "PERSONNELID" ).toString();
				PersonnelBean personnelBean = (PersonnelBean) dotsession.getAttribute("personnelBean");
				Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
				intcmIsApplication = false;
				if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
				{
				 intcmIsApplication = true;
				}

        dothelpObj cataddqcObj = new dothelpObj(db);

        String User_Action=request.getParameter( "Action" );
        if ( User_Action == null )
          User_Action="New";

        String item_id=request.getParameter( "item_id" );
            if ( item_id == null )
              item_id="";
		item_id=item_id.trim();

		int itemIdint = 0;
		try
		{
		  itemIdint=Integer.parseInt( item_id );
		}
		catch ( NumberFormatException ex )
		{
		  item_id="";
		}

        String CompNum=request.getParameter( "CompNum" );
        if ( CompNum == null )
          CompNum="0";

        try
        {
          String donevvstuff=dotsession.getAttribute( "VVDOTSTUFF" ) == null ? "" : dotsession.getAttribute( "VVDOTSTUFF" ).toString();

          if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
          {
            dotsession.setAttribute( "DOT_HAZ_CLASS",this.gethazclass() );
            dotsession.setAttribute( "PKG_GRP",this.getpkggrp() );
			dotsession.setAttribute( "VVDOTSTUFF","Yes" );
          }

          if ( User_Action.equalsIgnoreCase( "Update" ) )
          {
            Hashtable retrn_data= ( Hashtable ) dotsession.getAttribute( "DOT_COMPONENT_DATA" );
            Vector ComponentData1= ( Vector ) retrn_data.get( "DATA" );

            Vector synch_data=SynchServerData( request,ComponentData1 );

            int total1= ( ( Integer ) ( retrn_data.get( "TOTAL_NUMBER" ) ) ).intValue();
            Vector updateresults=UpdateDetails( synch_data,personnelid,total1,"save" );
            retrn_data.remove( "DATA" );
            retrn_data.put( "DATA",synch_data );

            Hashtable HeaderData=new Hashtable();
            Hashtable Data=new Hashtable();

            if (item_id.length() > 0)
            {
              Data=cataddqcObj.getMSDSData( item_id );
              HeaderData=cataddqcObj.getHeaderData( item_id );
            }

            dotsession.setAttribute( "DOT_COMPONENT_DATA",Data );

            if ( completeSuccess )
            {
              buildDetails( out,"Saved the Data Successfully",Data,HeaderData,item_id,dotsession );
            }
            else
            {
              buildDetails( out,"An Error Occured when saving Data",Data,HeaderData,item_id,dotsession );
            }

            retrn_data=null;
            Data = null;
          }
          else if ( !User_Action.equalsIgnoreCase( "Update" ) )
          {
            Hashtable Data=new Hashtable();
            Hashtable HeaderData=new Hashtable();

            if (item_id.length() > 0)
            {
              Data=cataddqcObj.getMSDSData( item_id );
              HeaderData=cataddqcObj.getHeaderData( item_id );
            }

            dotsession.setAttribute( "DOT_COMPONENT_DATA",Data );

            try
            {
              buildDetails( out,"",Data,HeaderData,item_id,dotsession );
            }
            catch ( Exception budetails )
            {
              budetails.printStackTrace();
            }
            Data = null;
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
        }
        finally
        {
          out.close();
        }
  }

  public Vector gethazclass()
      {
        String query="select HAZARD_CLASS,HAZARD_CLASS_DESC from global.vv_hazard_class order by HAZARD_CLASS";
        DBResultSet dbrs=null;
        ResultSet rs=null;
        Hashtable result=null;
        Vector ResultV=new Vector();
        try
        {
          dbrs=db.doQuery( query );
          rs=dbrs.getResultSet();
          while ( rs.next() )
          {
            result=new Hashtable();
            String faci= ( rs.getString( "HAZARD_CLASS" ) == null ? "" : rs.getString( "HAZARD_CLASS" ) );
            //String facn= ( rs.getString( "HAZARD_CLASS_DESC" ) == null ? "" : rs.getString( "HAZARD_CLASS_DESC" ) );
            result.put( faci,faci );
            ResultV.addElement( result );
          }

        }
        catch ( Exception e )
        {
          e.printStackTrace();
        }
        finally
        {
          if ( dbrs != null ) {dbrs.close();}
        }
        return ResultV;
      }

      public Vector getpkggrp()
      {
        Hashtable result=null;
        Vector ResultV=new Vector();

        result=new Hashtable();
        result.put( "I","I" );
        ResultV.addElement( result );

        result=new Hashtable();
        result.put( "II","II" );
        ResultV.addElement( result );

        result=new Hashtable();
        result.put( "III","III" );

        ResultV.addElement( result );

        return ResultV;
      }


  public void buildArrayofCom( int totalNum,PrintWriter dotout )
  {
    //StringBuffer Msgd2=new StringBuffer();

    dotout.println( "<SCRIPT>\n" );
    dotout.println( "var cmds = new Array();\n" );
    for ( int count=0; count < totalNum; count++ )
    {
      dotout.println( "cmds[" + count + "] = \"Component" + ( count + 1 ) + "\";\n" );
    }
    dotout.println( "</SCRIPT>\n" );

    return;
  }

  public void buildTabs( int totalNum,Vector DataT,PrintWriter dotout )
  {
    //StringBuffer Msgd3=new StringBuffer();
    int numofTabs=totalNum * 2 + 2;
    int usedWith=0;

    dotout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"800\">\n" );
    dotout.println( "<TR>\n" );
    dotout.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );

    Hashtable hD1=new Hashtable();
    hD1= ( Hashtable ) DataT.elementAt( 0 );
    int MaterialId=BothHelpObjs.makeZeroFromNull( hD1.get( "MATERIAL_ID" ).toString() );

    if (MaterialId > 0)
    {
    dotout.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component1_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_selected_tab\" onClick=\"togglePage('Component1', '');\"> 1\n");
    }
    else
    {
    dotout.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component1_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_nomatID_selected\" onClick=\"togglePage('Component1', '');\"> 1\n");
    }

    dotout.println( "</TD>\n" );
    dotout.println( "<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n" );

    usedWith+=36;

    if ( totalNum > 1 )
    {
      for ( int count=1; count < totalNum; count++ )
      {
        hD1=new Hashtable();
        hD1= ( Hashtable ) DataT.elementAt( count );

        MaterialId=BothHelpObjs.makeZeroFromNull( hD1.get( "MATERIAL_ID" ).toString() );
        if ( MaterialId > 0 )
        {
        dotout.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component"+(count+1)+"_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_tab\" onClick=\"togglePage('Component"+(count+1)+"', '');\"> "+(count+1)+"\n");
        }
        else
        {
        dotout.println("<TD WIDTH=\"30\" HEIGHT=\"15\" ID=\"Component"+(count+1)+"_tabchild\" ALIGN=\"CENTER\" CLASS=\"tab_nomatID\" onClick=\"togglePage('Component"+(count+1)+"', '');\"> "+(count+1)+"\n");
        }
        dotout.println("</TD>\n");
        dotout.println("<TD WIDTH=\"3\" HEIGHT=\"15\" CLASS=\"tab_buffer\"></TD>\n");
        usedWith += 33;
       }
     }

     dotout.println( "<TD WIDTH=\"" + ( 800 - usedWith ) + "\" HEIGHT=\"15\" CLASS=\"tab_buffer\">&nbsp;</TD>\n" );
     dotout.println( "</TR>\n" );
     dotout.println( "<TR>\n<TD CLASS=\"tab\" COLSPAN=" + numofTabs + " HEIGHT=\"2\"></TD></TR>\n" );
     dotout.println( "</TABLE>\n" );

     return;
  }

  public void buildDetails( PrintWriter dotout,String Message,Hashtable DataH,Hashtable HeaderData1,String item_idsersch,HttpSession dotsession1 ) throws Exception
  {
    int total=0;
    try
    {
      total= ( ( Integer ) ( DataH.get( "TOTAL_NUMBER" ) ) ).intValue();
    }
    catch ( Exception ex )
    {
    }

    Vector msdsComponentData= ( Vector ) DataH.get( "DATA" );
    //StringBuffer Msgd1=new StringBuffer();
    dotout.println( radian.web.HTMLHelpObj.printHTMLHeader( "DOT Shipping Name","dotshipname.js",intcmIsApplication ) );
    dotout.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
    dotout.println( "</HEAD>\n" );

    if ( total == 0 )
    {
      dotout.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\">\n" );
    }
    else
    {
      dotout.println( "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\" onload =\"initTabs()\">\n" );
    }

    buildArrayofCom( total,dotout );
	if (this.clientdot)
	{
	  dotout.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "DOT Shipping Name" ) );
	}
	else
	{
	  dotout.println( radian.web.HTMLHelpObj.PrintTitleBar( "DOT Shipping Name" ) );
	}

    dotout.println( "<FORM METHOD=\"POST\" name=\"MainForm\" onSubmit=\"return SubmitOnlyOnce()\" action=\"" + radian.web.tcmisResourceLoader.getdotshipnameurl() + "\" >\n" );
    dotout.println( "<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"5\">\n" );

    if ( Message.length() > 1 )
    {
      dotout.println( "<TR>\n<TD WIDTH=\"100%\" COLSPAN=\"7\" ALIGN=\"CENTER\">\n" );
      dotout.println( "<FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>" + Message + "</B></FONT>&nbsp;\n" );
      dotout.println( "</TD>\n</TR>\n" );
    }

    //QCStatus
    dotout.println( "<TR>\n" );
    // Search Text
    dotout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
    dotout.println( "<B>Item ID:</B>&nbsp;\n" );
    dotout.println( "</TD>\n" );
    dotout.println( "<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
    dotout.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"item_id\" value=\"" + item_idsersch + "\" size=\"19\">\n" );
    dotout.println( "</TD>\n" );
    dotout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Search\" name=\"Search\" onclick= \"return actionValue(name,this)\"></TD>\n");
    if (this.getupdateStatus())
    {
      dotout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\"><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Update\" name=\"Update\" onclick= \"return actionValue(name,this)\"></TD>\n" );
    }
    else
    {
      dotout.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\">&nbsp;</TD>\n" );
    }
    dotout.println( "</TR>\n" );

    dotout.println( "<TR>\n<TD HEIGHT=\"20\" CLASS=\"Inbluer\" WIDTH=\"5%\"><B>Description:</B></TD>\n" );
    dotout.println( "<TD HEIGHT=\"20\" COLSPAN=\"3\" WIDTH=\"20%\" CLASS=\"Inbluel\">" );
    dotout.println( HeaderData1.get( "ITEM_DESC" ) == null ? "" : HeaderData1.get( "ITEM_DESC" )  );
    dotout.println( "</TD>\n</TR>\n" );

    dotout.println( "<TR>\n<TD HEIGHT=\"20\" CLASS=\"Inwhiter\" WIDTH=\"5%\"><B>Final Shipping Name:</B>" );
    dotout.println("<BR><INPUT TYPE=\"button\"  CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"showfinalshipname\" value=\"Ship Name\" OnClick=\"showshipname()\" type=\"button\">\n");
    //dotout.println("<BR><BR><INPUT TYPE=\"button\"  CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"showfinalrqshipname\" value=\"RQ Ship Name\" OnClick=\"showrqshipname()\" type=\"button\">\n");
    dotout.println("</TD>");
    dotout.println( "<TD HEIGHT=\"20\" COLSPAN=\"3\" WIDTH=\"20%\" ID=\"finalshipname\" CLASS=\"Inwhitel\">" );
    dotout.println("</TD>");
    dotout.println( "</TR>\n</TABLE>\n" );

    dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Details\">\n" );
    dotout.println( "<INPUT TYPE=\"hidden\" NAME=\"totalComps\" VALUE=\"" + total + "\">\n" );

    if ( total == 0 )
    {
      dotout.println( "No Records Found for this Item Id.\n" );
    }
    else
    {
      for (int i  = 0 ; i  < total  ; i++)
      {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) msdsComponentData.elementAt(i);
        buildHiddenElements(hD,i,dotout);
      }

      dotout.println( "</FORM>\n" );
      buildTabs( total,msdsComponentData,dotout );

      for ( int i=0; i < total; i++ )
      {
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) msdsComponentData.elementAt( i );

	    dotout.println("<DIV ID=\"Component"+(i+1)+"\" STYLE=\"display: none;\">\n");
	    dotout.println("<FORM METHOD=\"POST\" name=\"ComponentForm"+(i+1)+"\" action=\""+radian.web.tcmisResourceLoader.getdotshipnameurl()+"Session=Update\" onsubmit=\"return Checkshipnames(this)\">\n");
        if (this.getupdateStatus())
        {
          buildDivs( hD,i,dotsession1,dotout );
        }
        else
        {
          buildreadonlyDivs( hD,i,dotsession1,dotout );
        }

        dotout.println( "</FORM>\n" );
        dotout.println( "</DIV>\n" );
      }
    }

   dotout.println( "</BODY>\n</HTML>\n" );

   //out.println( Msgd1 );
  }

  public void buildHiddenElements( Hashtable DataH,int TabNumber,PrintWriter dotout )
  {

    //StringBuffer MsgHE=new StringBuffer();
    Hashtable materialData=null;
    materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );

    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"part_id"+TabNumber+"\" VALUE=\""+materialData.get("PART_ID").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"material_id"+TabNumber+"\" VALUE=\""+materialData.get("MATERIAL_ID").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"item_id"+TabNumber+"\" VALUE=\""+materialData.get("ITEM_ID").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"pkg_gt_rq"+TabNumber+"\" VALUE=\""+materialData.get("PKG_GT_RQ").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"test_pkg_gt_rq"+TabNumber+"\" VALUE=\""+materialData.get("TEST_PKG_GT_RQ").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"bulk_pkg_marine_pollutant"+TabNumber+"\" VALUE=\""+materialData.get("BULK_PKG_MARINE_POLLUTANT").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"orm_d"+TabNumber+"\" VALUE=\""+materialData.get("ORM_D").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"weight_lb"+TabNumber+"\" VALUE=\""+materialData.get("WEIGHT_LB").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"material_desc"+TabNumber+"\" VALUE=\""+materialData.get("MATERIAL_DESC").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"hazard_class"+TabNumber+"\" VALUE=\""+materialData.get("HAZARD_CLASS").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"subsidiary_hazard_class"+TabNumber+"\" VALUE=\""+materialData.get("SUBSIDIARY_HAZARD_CLASS").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"packing_group"+TabNumber+"\" VALUE=\""+materialData.get("PACKING_GROUP").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"un_number"+TabNumber+"\" VALUE=\""+materialData.get("UN_NUMBER").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"ground_shipping_name"+TabNumber+"\" VALUE=\""+materialData.get("GROUND_SHIPPING_NAME").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"dry_ice"+TabNumber+"\" VALUE=\""+materialData.get("DRY_ICE").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"erg"+TabNumber+"\" VALUE=\""+materialData.get("ERG").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"passenger_air_limit"+TabNumber+"\" VALUE=\""+materialData.get("PASSENGER_AIR_LIMIT").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"passenger_air_unit"+TabNumber+"\" VALUE=\""+materialData.get("PASSENGER_AIR_UNIT").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"cargo_air_limit"+TabNumber+"\" VALUE=\""+materialData.get("CARGO_AIR_LIMIT").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"cargo_air_unit"+TabNumber+"\" VALUE=\""+materialData.get("CARGO_AIR_UNIT").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"marine_pollutant"+TabNumber+"\" VALUE=\""+materialData.get("MARINE_POLLUTANT").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"reportable_quantity_lb"+TabNumber+"\" VALUE=\""+materialData.get("REPORTABLE_QUANTITY_LB").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"ground_hazard"+TabNumber+"\" VALUE=\""+materialData.get("GROUND_HAZARD").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"air_hazard"+TabNumber+"\" VALUE=\""+materialData.get("AIR_HAZARD").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"water_hazard"+TabNumber+"\" VALUE=\""+materialData.get("WATER_HAZARD").toString()+"\">\n");
    dotout.println("<INPUT TYPE=\"hidden\" NAME=\"shipping_info_remarks"+TabNumber+"\" VALUE=\""+materialData.get("SHIPPING_INFO_REMARKS").toString()+"\">\n");
    //dotout.println("<INPUT TYPE=\"hidden\" NAME=\"packaging"+TabNumber+"\" VALUE=\""+materialData.get("PACKAGING").toString()+"\">\n");
	dotout.println("<INPUT TYPE=\"hidden\" NAME=\"hazmat_id"+TabNumber+"\" VALUE=\""+materialData.get("HAZMAT_ID").toString()+"\">\n");
	dotout.println("<INPUT TYPE=\"hidden\" NAME=\"technical_name"+TabNumber+"\" VALUE=\""+materialData.get("TECHNICAL_NAME").toString()+"\">\n");
	dotout.println("<INPUT TYPE=\"hidden\" NAME=\"symbol"+TabNumber+"\" VALUE=\""+materialData.get("SYMBOL").toString()+"\">\n");
	dotout.println("<INPUT TYPE=\"hidden\" NAME=\"scheduleB"+TabNumber+"\" VALUE=\""+materialData.get("SCHEDULE_B").toString()+"\">\n");

    return;
  }

  public void buildDivs( Hashtable DataH,int TabNumber,HttpSession dotsession2,PrintWriter dotout )
  {
    //StringBuffer Msgd=new StringBuffer();
    Hashtable materialData=null;
    materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );

    String numbervar = ""+(TabNumber+1)+"" + ""+TabNumber+"";

    try
    {
      dotout.println( "\n" );
      dotout.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100% >\n" );

      //Material ID
      dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Material ID: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
      dotout.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\" name=\"material_id" + numbervar + "\" value=\"" );
      dotout.println(materialData.get("MATERIAL_ID"));
      dotout.println("\" SIZE=\"7\" readonly>\n");
      dotout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\"  CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"msdsbutton"+numbervar+"\" value=\"MSDS\" OnClick=\"ShowMSDS('"+numbervar+"')\" type=\"button\"></TD>\n");
      //MATERIAL_DESC
      dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Material Description: </B></TD><TD WIDTH=\"25%\" CLASS=\"Inbluel\">");
      dotout.println(materialData.get("MATERIAL_DESC"));dotout.println("</TD>\n");
      dotout.println("</TR>\n");

      //PACKAGING
      dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Packaging: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
      dotout.println(materialData.get("PACKAGING"));
      dotout.println("</TD>\n");
      //WEIGHT_LB
      dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Net Weight(lb): </B></TD><TD WIDTH=\"25%\" CLASS=\"Inwhitel\">");
      dotout.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADWHITE\" name=\"weight_lb" + numbervar + "\" value=\"" );
      dotout.println(materialData.get("WEIGHT_LB"));
      dotout.println("\" SIZE=\"10\" readonly>\n");
      dotout.println("</TD>\n");
      dotout.println("</TR>\n");

      //LAST_CHANGED_BY
      dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Last Updated By: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
      dotout.println( materialData.get( "LAST_CHANGED_BY" ) );
      dotout.println( "</TD>\n" );
      //LAST_UPDATED
      dotout.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Last Updated Date: </B></TD><TD WIDTH=\"25%\" CLASS=\"Inbluel\">" );
      dotout.println( materialData.get( "LAST_UPDATED" ) );
      dotout.println( "</TD>\n" );
      dotout.println("</TR>\n");

      dotout.println("</TABLE>\n<BR><BR>");
      //dotout.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100% >\n" );

      //ORM-D
      String checkednon = "";
      String chkbox_value="N";
      /*String ormd = materialData.get("ORM_D").toString();
      String grundshipingname = materialData.get("ORM_D").toString();
      if ("Y".equalsIgnoreCase(ormd)) {checkednon="checked";chkbox_value="Y";}
      dotout.println("<TR><TD WIDTH=\"2%\" CLASS=\"Inbluer\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" " + checkednon+ "  name=\"orm_d"+numbervar+"\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
      dotout.println("ORM-D</TD>\n");

      //Non Regulated
      checkednon = "";
      chkbox_value="N";
      grundshipingname = materialData.get("GROUND_SHIPPING_NAME").toString();
      String groundhazard = materialData.get("GROUND_HAZARD").toString();
      String airhazard = materialData.get("AIR_HAZARD").toString();
      String waterhazard = materialData.get("WATER_HAZARD").toString();
      //if ("Not Regulated".equalsIgnoreCase(grundshipingname)) {checkednon="checked";chkbox_value="Y";}
      if ( !( "Y".equalsIgnoreCase(groundhazard) || "Y".equalsIgnoreCase(airhazard) || "Y".equalsIgnoreCase(waterhazard) ) ){checkednon="checked";chkbox_value="Y";}
      dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" onClick= \"nonregulatedvalues('"+numbervar+"')\" " + checkednon+ "  name=\"nonreg"+numbervar+"\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
      dotout.println("Not Regulated</TD>\n");

      //Ground Hazard
      checkednon = "";
      chkbox_value="N";
      if ("Y".equalsIgnoreCase(groundhazard)) {checkednon="checked";chkbox_value="Y";}
      dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" onClick= \"groundhazchecked('"+numbervar+"')\" " + checkednon+ "  name=\"ground_hazard"+numbervar+"\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
      dotout.println("Ground Hazard</TD>\n");

      //Air Hazard
      checkednon = "";
      chkbox_value="N";
      if ("Y".equalsIgnoreCase(airhazard)) {checkednon="checked";chkbox_value="Y";}
      dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" " + checkednon+ "  onClick= \"uncehknonreg('"+numbervar+"')\" name=\"air_hazard"+numbervar+"\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
      dotout.println("Air Hazard</TD>\n");

      //Water Hazard
      checkednon = "";
      chkbox_value="N";
      if ("Y".equalsIgnoreCase(waterhazard)) {checkednon="checked";chkbox_value="Y";}
      dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" " + checkednon+ " onClick= \"uncehknonreg('"+numbervar+"')\" name=\"water_hazard"+numbervar+"\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
      dotout.println("Water Hazard</TD></TR>\n");

      dotout.println("</TABLE>\n");
*/
      dotout.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100% >\n" );
      //GROUND_SHIPPING_NAME
      dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Proper Shipping Name: </B></TD>\n");
      dotout.println("<TD COLSPAN=\"3\" WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"80\" CLASS=\"INVISIBLEHEADBLUE\" name=\"ground_shipping_name"+numbervar+"\" value=\"");
      dotout.println(materialData.get("GROUND_SHIPPING_NAME"));
      dotout.println("\" SIZE=\"70\" readonly><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"propershpnamelike\" value=\"Lookup\" OnClick=\"getpropershipingname("+numbervar+")\"></TD></TR>\n");

      //TECHNICAL_NAME
	  dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>Technical Name: </B></TD>\n");
	  dotout.println("<TD COLSPAN=\"3\" WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"80\" CLASS=\"INVISIBLEHEADWHITE\" name=\"technical_name"+numbervar+"\" value=\"");
	  dotout.println(materialData.get("TECHNICAL_NAME"));
	  dotout.println("\" SIZE=\"70\" readonly><INPUT type=\"hidden\" name=\"hazmat_id"+numbervar+"\" value=\""+materialData.get("HAZMAT_ID")+"\"><INPUT type=\"hidden\" name=\"symbol"+numbervar+"\" value=\""+materialData.get("SYMBOL")+"\"></TD></TR>\n");

      //HAZARD_CLASS
      dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Hazard Class: </B></TD>\n");
      /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
      Vector vvhazclass= ( Vector ) dotsession2.getAttribute( "DOT_HAZ_CLASS" );
      String selectedhazclass=materialData.get( "HAZARD_CLASS" ).toString();
      dotout.println( "<SELECT CLASS=\"HEADER\" NAME=\"hazard_class" + numbervar + "\" size=\"1\" DISABLED>\n" );
      dotout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
      dotout.println( radian.web.HTMLHelpObj.getDropDown( vvhazclass,selectedhazclass ) );
      dotout.println( "</SELECT>\n" );
      dotout.println("</TD>");*/
	  dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADBLUE\" name=\"hazard_class"+numbervar+"\" value=\"");
	  dotout.println(materialData.get("HAZARD_CLASS"));
	  dotout.println("\" SIZE=\"15\" readonly></TD>");


      /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"8\" CLASS=\"Inbluel\" name=\"hazard_class"+numbervar+"\" value=\"");
      dotout.println(materialData.get("HAZARD_CLASS"));
      dotout.println("\" SIZE=\"8\"></TD>");*/

      //PACKING_GROUP
      dotout.println("\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Packing Group: </B></TD>\n");
      /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
      selectedhazclass=materialData.get( "PACKING_GROUP" ).toString();
      Vector vvpgrgrp= ( Vector ) dotsession2.getAttribute( "PKG_GRP" );
      dotout.println( "<SELECT CLASS=\"HEADER\" NAME=\"packing_group" + numbervar + "\" size=\"1\" DISABLED>\n" );
      dotout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
      dotout.println( radian.web.HTMLHelpObj.getDropDown( vvpgrgrp,selectedhazclass ) );
      dotout.println( "</SELECT>\n" );
      dotout.println("</TD>");*/
	  dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADBLUE\" name=\"packing_group"+numbervar+"\" value=\"");
	  dotout.println(materialData.get("PACKING_GROUP"));
	  dotout.println("\" SIZE=\"15\" readonly></TD>");

      /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"8\" CLASS=\"Inbluel\" name=\"packing_group"+numbervar+"\" value=\"");
      dotout.println(materialData.get("PACKING_GROUP"));
      dotout.println("\" SIZE=\"8\"></TD>");*/

      dotout.println("</TR>\n");

      //SUBSIDIARY_HAZARD_CLASS
      dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>Subsidary Risk: </B></TD>\n");
      dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\">");
      String selectedhazclass=materialData.get( "SUBSIDIARY_HAZARD_CLASS" ).toString();
	  Vector vvhazclass= ( Vector ) dotsession2.getAttribute( "DOT_HAZ_CLASS" );
      dotout.println( "<SELECT CLASS=\"HEADER\" NAME=\"subsidiary_hazard_class" + numbervar + "\" size=\"1\">\n" );
      dotout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
      dotout.println( radian.web.HTMLHelpObj.getDropDown( vvhazclass,selectedhazclass ) );
      dotout.println( "</SELECT>\n" );
      dotout.println("</TD>");


      /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"Inwhitel\" name=\"subsidiary_hazard_class"+numbervar+"\" value=\"");
      dotout.println(materialData.get("SUBSIDIARY_HAZARD_CLASS"));
      dotout.println("\" SIZE=\"15\"></TD>");*/

      //UN_NUMBER
      dotout.println("\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>UN/NA #: </B></TD>\n");
      dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADWHITE\" name=\"un_number"+numbervar+"\" value=\"");
      dotout.println(materialData.get("UN_NUMBER"));
      dotout.println("\" SIZE=\"15\" readonly></TD>");
      dotout.println("</TR>\n");

      //SCHEDULE_B
	  dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Schedule B: </B></TD>\n");
	  dotout.println("<TD COLSPAN=\"3\" WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"80\" CLASS=\"INVISIBLEHEADBLUE\" name=\"scheduleB"+numbervar+"\" value=\"");
	  dotout.println(materialData.get("SCHEDULE_B"));
	  dotout.println(" : ");
	  dotout.println(materialData.get("SCHEDULE_B_DESC"));
	  dotout.println("\" SIZE=\"70\" readonly></TD></TR>\n");
	  //dotout.println("\" SIZE=\"70\" readonly><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"scheduleBLike\" value=\"Lookup\" OnClick=\"getscheduleB("+numbervar+")\"></TD></TR>\n");

      //RQ_WEIGHT_IN_LBS
      dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>RQ Weight(lb): </B></TD>\n");
      dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"40\" CLASS=\"Inwhitel\" name=\"reportable_quantity_lb"+numbervar+"\" value=\"");
      dotout.println(materialData.get("REPORTABLE_QUANTITY_LB"));
      dotout.println("\" SIZE=\"5\" onChange= \"checkrqweight('"+numbervar+"')\"></TD>");

      String rqcheckednon = "";
      chkbox_value = "N";
      String rqchangesshipname = materialData.get("TEST_PKG_GT_RQ").toString();
      String pkggtrq = materialData.get("PKG_GT_RQ").toString();
      if ("Y".equalsIgnoreCase(rqchangesshipname) || "Y".equalsIgnoreCase(pkggtrq)) {rqcheckednon="checked";chkbox_value = "Y";}

      dotout.println("<TD WIDTH=\"8%\" CLASS=\"Inwhiter\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\"  " + rqcheckednon+ "  name=\"pkg_gt_rq"+numbervar+"\"></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">");
      dotout.println("Component Net Weight > RQ</TD>\n</TR>\n");

     dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>ERG: </B></TD>\n" );
     dotout.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"Inbluel\" name=\"erg" + numbervar + "\" value=\"" );
     dotout.println( materialData.get( "ERG" ) );
     dotout.println( "\" SIZE=\"15\"></TD>" );

      //ORM-D
	  String ormd = materialData.get("ORM_D").toString();
	  String grundshipingname = materialData.get("ORM_D").toString();
	  if ("Y".equalsIgnoreCase(ormd)) {checkednon="checked";chkbox_value="Y";}
	  dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" " + checkednon+ "  name=\"orm_d"+numbervar+"\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
	  dotout.println("ORM-D</TD>\n");
	  dotout.println("</TR>\n");

     //marine_pollutant
     checkednon = "";
     chkbox_value="N";
     String marinepolutant = materialData.get("MARINE_POLLUTANT").toString();
     if ("Y".equalsIgnoreCase(marinepolutant)) {checkednon="checked";chkbox_value="Y";}
     dotout.println("<TR><TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" " + checkednon+ "  name=\"marine_pollutant"+numbervar+"\"></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
     dotout.println("Marine Pollutant</TD>\n");

     //Water Hazard
     checkednon = "";
     chkbox_value="N";
     String bulkpkging = materialData.get("BULK_PKG_MARINE_POLLUTANT").toString();
     if ("Y".equalsIgnoreCase(bulkpkging)) {checkednon="checked";chkbox_value="Y";}
     dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><INPUT TYPE=\"checkbox\" value=\""+chkbox_value+"\" " + checkednon+ "  name=\"bulk_pkg_marine_pollutant"+numbervar+"\"></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
     dotout.println("Bulk Packaging</TD></TR>\n");


/*
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"bulk_pkg_marine_pollutant"+TabNumber+"\" value=\"");
dotout.println(DataH.get("BULK_PKG_MARINE_POLLUTANT"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"orm_d"+TabNumber+"\" value=\"");
dotout.println(DataH.get("ORM_D"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"dry_ice"+TabNumber+"\" value=\"");
dotout.println(DataH.get("DRY_ICE"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"passenger_air_limit"+TabNumber+"\" value=\"");
dotout.println(DataH.get("PASSENGER_AIR_LIMIT"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"passenger_air_unit"+TabNumber+"\" value=\"");
dotout.println(DataH.get("PASSENGER_AIR_UNIT"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"cargo_air_limit"+TabNumber+"\" value=\"");
dotout.println(DataH.get("CARGO_AIR_LIMIT"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"cargo_air_unit"+TabNumber+"\" value=\"");
dotout.println(DataH.get("CARGO_AIR_UNIT"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"marine_pollutant"+TabNumber+"\" value=\"");
dotout.println(DataH.get("MARINE_POLLUTANT"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"ground_hazard"+TabNumber+"\" value=\"");
dotout.println(DataH.get("GROUND_HAZARD"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"air_hazard"+TabNumber+"\" value=\"");
dotout.println(DataH.get("AIR_HAZARD"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
dotout.println("<TD WIDTH=\"10%\" Inwhitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"water_hazard"+TabNumber+"\" value=\"");
dotout.println(DataH.get("WATER_HAZARD"));
dotout.println("\" SIZE=\"5\"></TD>xxn");
*/
      //Comments
      dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Shipping Remarks: </B></TD>\n");
      dotout.println("<TD WIDTH=\"15%\" CLASS=\"Inbluel\" COLSPAN=\"3\">");
      dotout.println("<TEXTAREA name=\"shipping_info_remarks"+numbervar+"\" rows=\"3\" cols=\"80\">\n");dotout.println(materialData.get("SHIPPING_INFO_REMARKS").toString().trim());dotout.println("\n</TEXTAREA></TD>\n");
      dotout.println("</TR>\n");
      dotout.println("</TABLE>\n");

        }
        catch (Exception e1)
        {
            e1.printStackTrace();
            dotout.println(radian.web.HTMLHelpObj.printMessageinTable("An Error Occured While Building Divs"));
            return;
        }

      return;
    }

    public void buildreadonlyDivs( Hashtable DataH,int TabNumber,HttpSession dotsession2,PrintWriter dotout )
      {
       //StringBuffer Msgd=new StringBuffer();
        Hashtable materialData=null;
        materialData= ( Hashtable ) DataH.get( "MATERIAL_DATA" );
        String numbervar = ""+(TabNumber+1)+"" + ""+TabNumber+"";

        try
        {
          dotout.println( "\n" );
          dotout.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100% >\n" );

          //Material ID
          dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Material ID: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
          dotout.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\" name=\"material_id" + numbervar + "\" value=\"" );
          dotout.println(materialData.get("MATERIAL_ID"));
          dotout.println("\" SIZE=\"7\" readonly>\n");
          dotout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\"  CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"  name=\"msdsbutton"+numbervar+"\" value=\"MSDS\" OnClick=\"ShowMSDS('"+numbervar+"')\" type=\"button\"></TD>\n");
          //MATERIAL_DESC
          dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Material Description: </B></TD><TD WIDTH=\"25%\" CLASS=\"Inbluel\">");
          dotout.println(materialData.get("MATERIAL_DESC"));dotout.println("</TD>\n");
          dotout.println("</TR>\n");

          //PACKAGING
          dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Packaging: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">" );
          dotout.println(materialData.get("PACKAGING"));
          dotout.println("</TD>\n");
          //WEIGHT_LB
          dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"><B>Net Weight(lb): </B></TD><TD WIDTH=\"25%\" CLASS=\"Inwhitel\">");
          dotout.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADWHITE\" name=\"weight_lb" + numbervar + "\" value=\"" );
          dotout.println(materialData.get("WEIGHT_LB"));
          dotout.println("\" SIZE=\"10\" readonly>\n");
          dotout.println("</TD>\n");
          dotout.println("</TR>\n");

          //LAST_CHANGED_BY
          dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Last Updated By: </B></TD><TD WIDTH=\"15%\" CLASS=\"Inbluel\">" );
          dotout.println( materialData.get( "LAST_CHANGED_BY" ) );
          dotout.println( "</TD>\n" );
          //LAST_UPDATED
          dotout.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluer\"><B>Last Updated Date: </B></TD><TD WIDTH=\"25%\" CLASS=\"Inbluel\">" );
          dotout.println( materialData.get( "LAST_UPDATED" ) );
          dotout.println( "</TD>\n" );
          dotout.println("</TR>\n");

          dotout.println("</TABLE>\n<BR><BR>");
          /*dotout.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100% >\n" );
          //Non Regulated
          grundshipingname = materialData.get("GROUND_SHIPPING_NAME").toString();
          String groundhazard = materialData.get("GROUND_HAZARD").toString();
          String airhazard = materialData.get("AIR_HAZARD").toString();
          String waterhazard = materialData.get("WATER_HAZARD").toString();
          dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
          if ( !( "Y".equalsIgnoreCase(groundhazard) || "Y".equalsIgnoreCase(airhazard) || "Y".equalsIgnoreCase(waterhazard) ) ) {dotout.println("Not Regulated</TD>\n");}
          else {dotout.println("</TD>\n");}

          //Ground Hazard
          dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
          if ("Y".equalsIgnoreCase(groundhazard)) {dotout.println("Ground Hazard</TD>\n");}
          else {dotout.println("</TD>\n");}

          //Air Hazard
          dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
          if ("Y".equalsIgnoreCase(airhazard)) {dotout.println("Air Hazard</TD>\n");}
          else {dotout.println("</TD>\n");}
          //Water Hazard
          dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
          if ("Y".equalsIgnoreCase(waterhazard)) {dotout.println("Water Hazard</TD></TR>\n");}
          else {dotout.println("</TD>\n");}

          dotout.println("</TABLE>\n");*/

          dotout.println( "<TABLE BORDER=0 CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=100% >\n" );
          //GROUND_SHIPPING_NAME
          dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Proper Shipping Name: </B></TD>\n");
          dotout.println("<TD COLSPAN=\"3\" WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"80\" CLASS=\"INVISIBLEHEADBLUE\" name=\"ground_shipping_name"+numbervar+"\" value=\"");
          dotout.println(materialData.get("GROUND_SHIPPING_NAME"));
          dotout.println("\" SIZE=\"70\" readonly></TD></TR>\n");

 	      //TECHNICAL_NAME
		  dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>Technical Name: </B></TD>\n");
		  dotout.println("<TD COLSPAN=\"3\" WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"80\" CLASS=\"INVISIBLEHEADWHITE\" name=\"technical_name"+numbervar+"\" value=\"");
		  dotout.println(materialData.get("TECHNICAL_NAME"));
		  dotout.println("\" SIZE=\"70\" readonly><INPUT type=\"hidden\" name=\"hazmat_id"+numbervar+"\" value=\""+materialData.get("HAZMAT_ID")+"\"><INPUT type=\"hidden\" name=\"symbol"+numbervar+"\" value=\""+materialData.get("SYMBOL")+"\"></TD></TR>\n");

          //HAZARD_CLASS
          dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Hazard Class: </B></TD>\n");
          dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
          String selectedhazclass=materialData.get( "HAZARD_CLASS" ).toString();
          dotout.println( selectedhazclass );
          dotout.println("</TD>");

          /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"8\" CLASS=\"Inbluel\" name=\"hazard_class"+numbervar+"\" value=\"");
          dotout.println(materialData.get("HAZARD_CLASS"));
          dotout.println("\" SIZE=\"8\"></TD>");*/

          //PACKING_GROUP
          dotout.println("\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Packing Group: </B></TD>\n");
          dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
          selectedhazclass=materialData.get( "PACKING_GROUP" ).toString();
          dotout.println( selectedhazclass );
          dotout.println("</TD>");

          /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"8\" CLASS=\"Inbluel\" name=\"packing_group"+numbervar+"\" value=\"");
          dotout.println(materialData.get("PACKING_GROUP"));
          dotout.println("\" SIZE=\"8\"></TD>");*/

          dotout.println("</TR>\n");

          //SUBSIDIARY_HAZARD_CLASS
          dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>Subsidary Risk: </B></TD>\n");
          dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\">");
          selectedhazclass=materialData.get( "SUBSIDIARY_HAZARD_CLASS" ).toString();
          dotout.println( selectedhazclass );
          dotout.println( "</SELECT>\n" );
          dotout.println("</TD>");

          /*dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADWHITE\" name=\"subsidiary_hazard_class"+numbervar+"\" value=\"");
          dotout.println(materialData.get("SUBSIDIARY_HAZARD_CLASS"));
          dotout.println("\" SIZE=\"15\"></TD>");*/

          //UN_NUMBER
          dotout.println("\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>UN/NA #: </B></TD>\n");
          dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADWHITE\" name=\"un_number"+numbervar+"\" value=\"");
          dotout.println(materialData.get("UN_NUMBER"));
          dotout.println("\" SIZE=\"15\"></TD>");
          dotout.println("</TR>\n");

		  //SCHEDULE_B
		  dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Schedule B: </B></TD>\n");
		  dotout.println("<TD COLSPAN=\"3\" WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"80\" CLASS=\"INVISIBLEHEADBLUE\" name=\"scheduleB"+numbervar+"\" value=\"");
		  dotout.println(materialData.get("SCHEDULE_B"));
		  dotout.println(" : ");
		  dotout.println(materialData.get("SCHEDULE_B_DESC"));
		  dotout.println("\" SIZE=\"70\" readonly></TD></TR>\n");
		  //dotout.println("\" SIZE=\"70\" readonly><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"scheduleBLike\" value=\"Lookup\" OnClick=\"getscheduleB("+numbervar+")\"></TD></TR>\n");

          //RQ_WEIGHT_IN_LBS
          dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\" ALIGN=\"LEFT\"><B>RQ Weight(lb): </B></TD>\n");
          dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhitel\"><INPUT type=\"text\" MAXLENGTH=\"40\" CLASS=\"INVISIBLEHEADWHITE\" name=\"reportable_quantity_lb"+numbervar+"\" value=\"");
          dotout.println(materialData.get("REPORTABLE_QUANTITY_LB"));
          dotout.println("\" SIZE=\"5\" onChange= \"checkrqweight('"+numbervar+"')\"></TD>");

          String rqchangesshipname = materialData.get("TEST_PKG_GT_RQ").toString();
          String pkggtrq = materialData.get("PKG_GT_RQ").toString();
          dotout.println("<TD WIDTH=\"8%\" CLASS=\"Inwhiter\"></TD><TD WIDTH=\"20%\" CLASS=\"Inwhitel\">");
          if ("Y".equalsIgnoreCase(rqchangesshipname) || "Y".equalsIgnoreCase(pkggtrq)) {dotout.println("Component Net Weight > RQ</TD>\n</TR>\n");}
          else {dotout.println("</TD>\n");}

         dotout.println( "<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>ERG: </B></TD>\n" );
         dotout.println( "<TD WIDTH=\"10%\" CLASS=\"Inbluel\"><INPUT type=\"text\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADBLUE\" name=\"erg" + numbervar + "\" value=\"" );
         dotout.println( materialData.get( "ERG" ) );
         dotout.println( "\" SIZE=\"15\"></TD>" );
		 //ORM-D
		  String ormd = materialData.get("ORM_D").toString();
		  String grundshipingname = materialData.get("ORM_D").toString();
		  dotout.println("<TD WIDTH=\"2%\" CLASS=\"Inbluer\"></TD><TD WIDTH=\"10%\" CLASS=\"Inbluel\">");
		  if ("Y".equalsIgnoreCase(ormd)) {dotout.println("ORM-D</TD>\n");} else {dotout.println("</TD>\n");}
         dotout.println("</TR>\n");

         //marine_pollutant
         String marinepolutant = materialData.get("MARINE_POLLUTANT").toString();
         dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
         if ("Y".equalsIgnoreCase(marinepolutant)) {dotout.println("Marine Pollutant</TD>\n");}
         else {dotout.println("</TD>\n");}

         //Water Hazard
         String bulkpkging = materialData.get("BULK_PKG_MARINE_POLLUTANT").toString();
         dotout.println("<TD WIDTH=\"10%\" CLASS=\"Inwhiter\"></TD><TD WIDTH=\"15%\" CLASS=\"Inwhitel\">");
         if ("Y".equalsIgnoreCase(bulkpkging)) {dotout.println("Bulk Packaging</TD></TR>\n");}
         else {dotout.println("</TD>\n");}

    /*
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"bulk_pkg_marine_pollutant"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("BULK_PKG_MARINE_POLLUTANT"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"orm_d"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("ORM_D"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"dry_ice"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("DRY_ICE"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"passenger_air_limit"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("PASSENGER_AIR_LIMIT"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"passenger_air_unit"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("PASSENGER_AIR_UNIT"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"cargo_air_limit"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("CARGO_AIR_LIMIT"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"cargo_air_unit"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("CARGO_AIR_UNIT"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"marine_pollutant"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("MARINE_POLLUTANT"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"ground_hazard"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("GROUND_HAZARD"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"air_hazard"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("AIR_HAZARD"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    dotout.println("<TD WIDTH=\"10%\" Inbluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"water_hazard"+TabNumber+"\" value=\"");
    dotout.println(DataH.get("WATER_HAZARD"));
    dotout.println("\" SIZE=\"5\"></TD>xxn");
    */
          //Comments
          dotout.println("<TR>\n<TD WIDTH=\"10%\" CLASS=\"Inbluer\" ALIGN=\"LEFT\"><B>Shipping Remarks: </B></TD>\n");
          dotout.println("<TD WIDTH=\"15%\" CLASS=\"Inbluel\" COLSPAN=\"3\">");
          dotout.println(materialData.get("SHIPPING_INFO_REMARKS").toString().trim());
          dotout.println("</TD>\n");
          dotout.println("</TR>\n");
          dotout.println("</TABLE>\n");

            }
            catch (Exception e1)
            {
                e1.printStackTrace();
                dotout.println(radian.web.HTMLHelpObj.printMessageinTable("An Error Occured While Building Divs"));
                return;
            }

          return;
        }

    //
    private Vector UpdateDetails( Vector in_data,String logonid,int total,String action )
    {
      dothelpObj cataddqcObj1=new dothelpObj( db );
      Vector new_data=new Vector();

      try
      {
        for ( int i=0; i < total; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) in_data.elementAt( i );

          boolean line_result=false;
          line_result=cataddqcObj1.updateMsdsData( hD,logonid,action ); // update database

          if ( false == line_result )
          {
            completeSuccess=false;
            hD.remove( "UPDATE_FLAG" );
            hD.put( "UPDATE_FLAG",new Boolean( false ) );
          }

          new_data.addElement( hD );
        } //end of for
      }
      catch ( Exception e )
      {
        completeSuccess=false;
        e.printStackTrace();
      }
      return new_data;
    }

    private Vector SynchServerData( HttpServletRequest request,Vector in_data )
    {
      Vector new_data=new Vector();
      Hashtable materialData=null;

      try
      {
        int i=0; //used for detail lines
        for ( int loop=0; loop < in_data.size(); loop++ )
        {
          i++;
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) ( in_data.elementAt( loop ) );

          materialData= ( Hashtable ) hD.get( "MATERIAL_DATA" );

          /*String part_id= request.getParameter(("part_id"+loop)).toString();
          if ( part_id == null )
            part_id="";
          materialData.remove( "PART_ID" );
          materialData.put( "PART_ID",part_id.trim() );
          String material_id=request.getParameter( ( "material_id" + loop ) ).toString();
          if ( material_id == null )
            material_id="";
          materialData.remove( "MATERIAL_ID" );
          materialData.put( "MATERIAL_ID",material_id.trim() );
          String item_id=request.getParameter( ( "item_id" + loop ) ).toString();
          if ( item_id == null )
            item_id="";
          materialData.remove( "ITEM_ID" );
          materialData.put( "ITEM_ID",item_id.trim() );*/
          String pkg_gt_rq=request.getParameter( ( "pkg_gt_rq" + loop ) ).toString();
          if ( pkg_gt_rq == null )
            pkg_gt_rq="";
          materialData.remove( "PKG_GT_RQ" );
          materialData.put( "PKG_GT_RQ",pkg_gt_rq.trim() );
          /*String test_pkg_gt_rq=request.getParameter( ( "test_pkg_gt_rq" + loop ) ).toString();
          if ( test_pkg_gt_rq == null )
            test_pkg_gt_rq="";
          materialData.remove( "TEST_PKG_GT_RQ" );
          materialData.put( "TEST_PKG_GT_RQ",test_pkg_gt_rq.trim() );*/
          String bulk_pkg_marine_pollutant=request.getParameter( ( "bulk_pkg_marine_pollutant" + loop ) ).toString();
          if ( bulk_pkg_marine_pollutant == null )
            bulk_pkg_marine_pollutant="";
          materialData.remove( "BULK_PKG_MARINE_POLLUTANT" );
          materialData.put( "BULK_PKG_MARINE_POLLUTANT",bulk_pkg_marine_pollutant.trim() );
          String orm_d=request.getParameter( ( "orm_d" + loop ) ).toString();
          if ( orm_d == null )
            orm_d="";
          materialData.remove( "ORM_D" );
          materialData.put( "ORM_D",orm_d.trim() );
          /*String weight_lb=request.getParameter( ( "weight_lb" + loop ) ).toString();
          if ( weight_lb == null )
            weight_lb="";
          materialData.remove( "WEIGHT_LB" );
          materialData.put( "WEIGHT_LB",weight_lb.trim() );
          String material_desc=request.getParameter( ( "material_desc" + loop ) ).toString();
          if ( material_desc == null )
            material_desc="";
          materialData.remove( "MATERIAL_DESC" );
          materialData.put( "MATERIAL_DESC",material_desc.trim() );*/
          String hazard_class=request.getParameter( ( "hazard_class" + loop ) ).toString();
          if ( hazard_class == null )
            hazard_class="";
          materialData.remove( "HAZARD_CLASS" );
          materialData.put( "HAZARD_CLASS",hazard_class.trim() );
          String subsidiary_hazard_class=request.getParameter( ( "subsidiary_hazard_class" + loop ) ).toString();
          if ( subsidiary_hazard_class == null )
            subsidiary_hazard_class="";
          materialData.remove( "SUBSIDIARY_HAZARD_CLASS" );
          materialData.put( "SUBSIDIARY_HAZARD_CLASS",subsidiary_hazard_class.trim() );
          String packing_group=request.getParameter( ( "packing_group" + loop ) ).toString();
          if ( packing_group == null )
            packing_group="";
          materialData.remove( "PACKING_GROUP" );
          materialData.put( "PACKING_GROUP",packing_group.trim() );
          String un_number=request.getParameter( ( "un_number" + loop ) ).toString();
          if ( un_number == null )
            un_number="";
          materialData.remove( "UN_NUMBER" );
          materialData.put( "UN_NUMBER",un_number.trim() );
          String ground_shipping_name=request.getParameter( ( "ground_shipping_name" + loop ) ).toString();
          if ( ground_shipping_name == null )
            ground_shipping_name="";
          materialData.remove( "GROUND_SHIPPING_NAME" );
          materialData.put( "GROUND_SHIPPING_NAME",ground_shipping_name.trim() );
          /*String dry_ice=request.getParameter( ( "dry_ice" + loop ) ).toString();
          if ( dry_ice == null )
            dry_ice="";
          materialData.remove( "DRY_ICE" );
          materialData.put( "DRY_ICE",dry_ice.trim() );*/
          String erg=request.getParameter( ( "erg" + loop ) ).toString();
          if ( erg == null )
            erg="";
          materialData.remove( "ERG" );
          materialData.put( "ERG",erg.trim() );
          /*String passenger_air_limit=request.getParameter( ( "passenger_air_limit" + loop ) ).toString();
          if ( passenger_air_limit == null )
            passenger_air_limit="";
          materialData.remove( "PASSENGER_AIR_LIMIT" );
          materialData.put( "PASSENGER_AIR_LIMIT",passenger_air_limit.trim() );
          String passenger_air_unit=request.getParameter( ( "passenger_air_unit" + loop ) ).toString();
          if ( passenger_air_unit == null )
            passenger_air_unit="";
          materialData.remove( "PASSENGER_AIR_UNIT" );
          materialData.put( "PASSENGER_AIR_UNIT",passenger_air_unit.trim() );
          String cargo_air_limit=request.getParameter( ( "cargo_air_limit" + loop ) ).toString();
          if ( cargo_air_limit == null )
            cargo_air_limit="";
          materialData.remove( "CARGO_AIR_LIMIT" );
          materialData.put( "CARGO_AIR_LIMIT",cargo_air_limit.trim() );
          String cargo_air_unit=request.getParameter( ( "cargo_air_unit" + loop ) ).toString();
          if ( cargo_air_unit == null )
            cargo_air_unit="";
          materialData.remove( "CARGO_AIR_UNIT" );
          materialData.put( "CARGO_AIR_UNIT",cargo_air_unit.trim() );*/
          String marine_pollutant=request.getParameter( ( "marine_pollutant" + loop ) ).toString();
          if ( marine_pollutant == null )
            marine_pollutant="";
          materialData.remove( "MARINE_POLLUTANT" );
          materialData.put( "MARINE_POLLUTANT",marine_pollutant.trim() );
          String reportable_quantity_lb=request.getParameter( ( "reportable_quantity_lb" + loop ) ).toString();
          if ( reportable_quantity_lb == null )
            reportable_quantity_lb="";
          materialData.remove( "REPORTABLE_QUANTITY_LB" );
          materialData.put( "REPORTABLE_QUANTITY_LB",reportable_quantity_lb.trim() );
          /*String ground_hazard=request.getParameter( ( "ground_hazard" + loop ) ).toString();
          if ( ground_hazard == null )
            ground_hazard="";
          materialData.remove( "GROUND_HAZARD" );
          materialData.put( "GROUND_HAZARD",ground_hazard.trim() );
          String air_hazard=request.getParameter( ( "air_hazard" + loop ) ).toString();
          if ( air_hazard == null )
            air_hazard="";
          materialData.remove( "AIR_HAZARD" );
          materialData.put( "AIR_HAZARD",air_hazard.trim() );
          String water_hazard=request.getParameter( ( "water_hazard" + loop ) ).toString();
          if ( water_hazard == null )
            water_hazard="";
          materialData.remove( "WATER_HAZARD" );
          materialData.put( "WATER_HAZARD",water_hazard.trim() );*/
          String shipping_info_remarks=request.getParameter( ( "shipping_info_remarks" + loop ) ).toString();
          if ( shipping_info_remarks == null )
            shipping_info_remarks="";
          materialData.remove( "SHIPPING_INFO_REMARKS" );
          materialData.put( "SHIPPING_INFO_REMARKS",shipping_info_remarks.trim() );
          /*String packaging=request.getParameter( ( "packaging" + loop ) ).toString();
          if ( packaging == null )
            packaging="";
          materialData.remove( "PACKAGING" );
          materialData.put( "PACKAGING",packaging.trim() );  */

		 String technical_name=request.getParameter( ( "technical_name" + loop ) ).toString();
		 if ( technical_name == null )
		   technical_name="";
		 materialData.remove( "TECHNICAL_NAME" );
		 materialData.put( "TECHNICAL_NAME",technical_name.trim() );

		 String hazmat_id=request.getParameter( ( "hazmat_id" + loop ) ).toString();
		 if ( hazmat_id == null )
		   hazmat_id="";
		 materialData.remove( "HAZMAT_ID" );
		 materialData.put( "HAZMAT_ID",hazmat_id.trim() );

		String symbol=request.getParameter( ( "symbol" + loop ) ).toString();
		 if ( symbol == null )
		   symbol="";
		 materialData.remove( "SYMBOL" );
		 materialData.put( "SYMBOL",symbol.trim() );

		String scheduleB=request.getParameter( ( "scheduleB" + loop ) ).toString();
		 if ( scheduleB == null )
		   scheduleB="";
		 materialData.remove( "SCHEDULE_B" );
		 materialData.put( "SCHEDULE_B",scheduleB.trim() );

          hD.remove( "MATERIAL_DATA" );
          hD.put( "MATERIAL_DATA",materialData );

          new_data.addElement( hD );
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();

      }
      return new_data;
    }
}

