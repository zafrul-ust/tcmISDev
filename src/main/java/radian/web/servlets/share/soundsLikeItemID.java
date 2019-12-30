package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 03-11-03 Not showing stocking type size verified and showing only items with stocking type S
 * 06-16-03 Code cleanup
 * 08-14-03 Showing Case Qty in the item search
 * 06-16-04 Showing only item type MA's
 */

public class soundsLikeItemID
{
    private ServerResourceBundle bundle = null;
    private TcmISDBModel db = null;
    private String nematid_Servlet = "";
	private PrintWriter out = null;
    private String lineItem = "1";
    public soundsLikeItemID(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResult( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
    {
      out=response.getWriter();
      HttpSession Session=request.getSession();
      String Action="";
      String ItemID="";
      String requestid="";
      Hashtable retrn_data=new Hashtable();

      try
      {
        retrn_data= ( Hashtable ) Session.getAttribute( "COMPONENT_DATA" );
        requestid=retrn_data.get( "REQUEST_ID" ).toString();
        lineItem = retrn_data.get( "LINE_ITEM" ).toString();
      }
      catch ( Exception ex )
      {
        out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occured.<BR> Please save the request and try again." ) );
        out.close();
        return;
      }

      Action=request.getParameter( "Action" );
      if ( Action == null )
        Action="";

      ItemID=request.getParameter( "itemid" );
      if ( ItemID == null )
        ItemID="";

      String itemDesc1=request.getParameter( "itemdesc1" );
      if ( itemDesc1 == null )
        itemDesc1="";

      String itemDesc2=request.getParameter( "itemdesc2" );
      if ( itemDesc2 == null )
        itemDesc2="";

      String itemisVerified=request.getParameter( "itemisVerified" );
      if ( itemisVerified == null )
        itemisVerified="";


      String itemType=request.getParameter( "itemType" );
      if ( itemType == null )
        itemType="";

      if ( "New".equalsIgnoreCase( Action ) )
      {
        buildNewItemIdDone( requestid,"","" );
      }
      else if ( "updatenewitem".equalsIgnoreCase( Action ) )
      {
        buildNewItemIdDone( requestid,itemDesc1,itemDesc2 );
      }
      else if ( "updateitem".equalsIgnoreCase( Action ) )
      {
        buildItemIdDone( requestid,ItemID,itemisVerified,itemType );
      }
      else
      {
        out.println(buildItemIdSearch( retrn_data,requestid ));
      }
      out.close();
    }

    public void buildItemIdDone( String requestID,String ItemId,String itemISVerified,String itemType )
    {
      //StringBuffer Msgn=new StringBuffer();
      nematid_Servlet=bundle.getString( "QUALITY_CONTROL_ITEM" );

      out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC","cataddqcdetails.js",false ) );
      out.println( "</HEAD>\n" );
      out.println( "<BODY><BR><BR><BR><BR>\n" );

      out.println( "<FORM METHOD=\"POST\" name=\"itemidsearch\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemisVerified\" VALUE=\"" + itemISVerified + "\">\n" );
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemType\" ID=\"itemType\" VALUE=\"" + itemType + "\">\n" );

      if ( this.updateItemid( requestID,ItemId,"","","" ) )
      {
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemid\" VALUE=\"" + ItemId + "\">\n" );
        out.println( "<FONT FACE=\"Arial\" SIZE=\"2\">Item <B>" + ItemId + "</B> was sucessfully updated for Request ID <B>" + requestID + "</B>" );
      }
      else
      {
        out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemid\" VALUE=\"\">\n" );
        out.println( "<FONT FACE=\"Arial\" SIZE=\"2\">Item was not updated for Request ID <B>" + requestID + "</B>.\n <BR><BR> Please try again.\n" );
      }

      out.println( "<BR><BR><BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"searchlike\" value=\"OK\" OnClick=\"sendItemID(this.form)\">\n" );
      out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER>\n" );
      out.println( "</BODY></HTML>\n" );

      return;
  }

  public void buildNewItemId( String requestID )
  {
    //StringBuffer Msgn=new StringBuffer();
    nematid_Servlet=bundle.getString( "QUALITY_CONTROL_ITEM" );

    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC","cataddqcdetails.js",false ) );
    out.println( "</HEAD>\n" );
    out.println( "<BODY><BR><BR>\n" );
    out.println( "<FORM METHOD=\"POST\" name=\"itemidsearch\" action=\"" + nematid_Servlet + "Session=Update\">\n" );

    out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemid\" VALUE=\"\">\n" );
    out.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"updatenewitem\">\n" );

    out.println( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"3\" CELLSPACING=\"0\">\n" );
    out.println( "<TR><TD WIDTH=\"8%\" HEIGHT=\"30\" CLASS=\"Inwhiter\"><B>Request ID:</B></TD><TD WIDTH=\"25%\" HEIGHT=\"30\" CLASS=\"Inwhitel\">" + requestID + "\n</TD></TR>\n" );
    out.println( "<TR><TD BGCOLOR=\"#000066\">&nbsp;</TD><TD BGCOLOR=\"#000066\">&nbsp;</TD></TR>\n" );

    out.println( "<TR>\n" );
    out.println( "<TD WIDTH=\"8%\" HEIGHT=\"30\" CLASS=\"Inwhiter\"><B>JDE Part No :</B></TD>\n" );
    out.println( "<TD WIDTH=\"25%\" HEIGHT=\"30\" CLASS=\"Inwhitel\">TCM-CAR-" + requestID + "\n</TD>\n" );
    out.println( "</TR>\n" );

    out.println( "<TR>\n" );
    out.println( "<TD WIDTH=\"8%\" HEIGHT=\"30\" CLASS=\"Inbluer\"><B>JDE Item Description 1 :</B></TD>\n" );
    out.println( "<TD WIDTH=\"25%\" HEIGHT=\"30\" CLASS=\"Inbluel\"><INPUT type=\"text\" name=\"itemdesc1\" value=\"\" maxlength=\"29\" SIZE=\"29\">\n</TD>\n" );
    out.println( "</TR>\n" );

    out.println( "<TR>\n" );
    out.println( "<TD WIDTH=\"8%\" HEIGHT=\"30\" CLASS=\"Inwhiter\"><B>JDE Item Description 2 :</B></TD>\n" );
    out.println( "<TD WIDTH=\"25%\" HEIGHT=\"30\" CLASS=\"Inwhitel\"><INPUT type=\"text\" name=\"itemdesc2\" value=\"\" maxlength=\"29\" SIZE=\"29\">\n</TD>\n" );
    out.println( "</TR>\n" );

    out.println( "</TABLE>\n" );

    out.println( "<CENTER><BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" name=\"updatenewitem\" value=\"OK\">\n" );
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER>\n" );
    out.println( "</BODY></HTML>\n" );

    return;
  }

  public void buildNewItemIdDone( String requestID,String itemdesc1,String itemdesc2 )
  {
    //StringBuffer Msgn=new StringBuffer();
    nematid_Servlet=bundle.getString( "QUALITY_CONTROL_ITEM" );

    String newItemId="0";
    try
    {
      newItemId=getnewitemID();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

    out.println( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC","cataddqcdetails.js",false ) );
    out.println( "</HEAD>\n" );
    out.println( "<BODY><BR><BR><BR><BR>\n" );
    out.println( "<FORM METHOD=\"POST\" name=\"itemidsearch\" action=\"" + nematid_Servlet + "Session=Update\">\n" );

    if ( this.updateItemid( requestID,"" + newItemId + "",itemdesc1,itemdesc2,"Yes" ) )
    {
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemid\" VALUE=\"" + newItemId + "\">\n" );
      out.println( "<FONT FACE=\"Arial\" SIZE=\"2\">New Item <B>" + newItemId + "</B> was sucessfully Created for Request ID <B>" + requestID + "</B>" );
    }
    else
    {
      out.println( "<INPUT TYPE=\"hidden\" NAME=\"itemid\" VALUE=\"\">\n" );
      out.println( "<FONT FACE=\"Arial\" SIZE=\"2\">Item was not Created for Request ID <B>" + requestID + "</B>.\n <BR><BR> Please try again.\n" );
    }

    out.println( "<BR><BR><BR><BR><CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"searchlike\" value=\"OK\" OnClick=\"sendItemID(this.form)\">\n" );
    out.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER>\n" );
    out.println( "</BODY></HTML>\n" );

    return;
  }

  public StringBuffer buildItemIdSearch( Hashtable DataH,String requestID )
  {
    StringBuffer Msgl=new StringBuffer();
    nematid_Servlet=bundle.getString( "QUALITY_CONTROL_ITEM" );
    Vector ComponentData1= ( Vector ) DataH.get( "DATA" );
    int total1= ( ( Integer ) ( DataH.get( "TOTAL_NUMBER" ) ) ).intValue();

    Msgl.append( radian.web.HTMLHelpObj.printHTMLHeader( "Catalog Add QC Select Item","cataddqcdetails.js",false ) );
    Msgl.append( "</HEAD>\n" );
    Msgl.append( "<BODY>\n" );
    Msgl.append( "<FORM METHOD=\"POST\" name=\"itemidsearch\" action=\"" + nematid_Servlet + "Session=Update\">\n" );
    Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"itemisVerified\" VALUE=\"\">\n" );
    Msgl.append( "<INPUT TYPE=\"hidden\" NAME=\"itemType\" ID=\"itemType\" VALUE=\"\">\n" );
    Msgl.append( "<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\" bgcolor=\"white\">\n" );
    Msgl.append( "<TR><TD WIDTH=\"30%\" ALIGN=\"RIGHT\"><FONT FACE=\"Arial\" SIZE=\"2\"><B>Item ID:</B></FONT></TD><TD WIDTH=\"70%\" COLSPAN=\"3\"><INPUT CLASS=\"HEADER\" type=\"text\" name=\"itemid\" value=\"\" SIZE=\"10\" readonly>&nbsp;&nbsp;&nbsp;&nbsp;For Request Id: <B>" + requestID + "</B>\n" );
    Msgl.append( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\"><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"New\" value=\"New\" OnClick=\"ShowSearch(name,this)\"></TD></TR>\n" );
    Msgl.append( "</TR>\n" );

    Msgl.append( "</TABLE><TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\">\n" );
    Msgl.append( "<TR>\n" );
    Msgl.append( "<TD WIDTH=\"5%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Item Id<br>(Type)</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"5%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Material</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"5%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Grade</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"10%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Packaging</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"10%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>MPN</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"5%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Weight</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"10%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Shelf Life : Basis</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"10%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Storage Temp</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"5%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Item Verified</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"5%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Case Qty</B></TD>\n" );
    Msgl.append( "<TD WIDTH=\"5%\" BGCOLOR=\"#000066\" ALIGN=\"CENTER\"><FONT FACE=\"Arial\" COLOR=\"#ffffff\" SIZE=\"2\"><B>Revision Comments</B></TD>\n" );
    Msgl.append( "</TR>\n" );

    String wherein="(";
    Vector materialids=new Vector();
    for ( int loop=0; loop < ComponentData1.size(); loop++ )
    {
      Msgl.append( "<TR>\n" );
      Hashtable hD=new Hashtable();
      hD= ( Hashtable ) ( ComponentData1.elementAt( loop ) );

      String Color=" BGCOLOR=\"#E6E8FA\" ";
      String materialID=hD.get( "MATERIAL_ID" ).toString();
      materialids.addElement( materialID );

      Msgl.append( "<TD WIDTH=\"5%\" " + Color + "l\">" + DataH.get( "ITEM_ID" ).toString() + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"5%\" " + Color + "l\">" + materialID + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"5%\" " + Color + "l\">" + hD.get( "GRADE" ).toString() + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"10%\" " + Color + "l\">" + hD.get( "NO_OF_COMPONENTS" ).toString() + " x " + hD.get( "PART_SIZE" ).toString() + " " + hD.get( "PART_SIZE_UNIT" ).toString() + " " + hD.get( "PACKAGING_STYLE" ).toString() + " " + hD.get( "DIMENSION" ).toString() + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"10%\" " + Color + "l\">" + hD.get( "MFG_PART_NO" ).toString() + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"5%\" " + Color + "l\">" + hD.get( "NET_WEIGHT" ).toString() + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"10%\" " + Color + "l\">" + hD.get( "SHELF_LIFE" ).toString() + " : " + hD.get( "SHELF_LIFE_BASIS" ).toString() + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"10%\" " + Color + "l\">" + hD.get( "STORAGE_TEMP" ).toString() + "</TD>\n" );
      Msgl.append( "<TD WIDTH=\"5%\" " + Color + "l\"> </TD>\n" );
      Msgl.append( "<TD WIDTH=\"5%\" " + Color + "l\"> </TD>\n" );
			Msgl.append( "<TD WIDTH=\"5%\" " + Color + "l\"> </TD>\n" );

      if ( materialID.length() > 0 )
      {
        wherein+="" + hD.get( "MATERIAL_ID" ).toString() + ",";
      }

      Msgl.append( "</TR>\n" );
    }

    wherein=wherein.substring( 0,wherein.length() - 1 );
    wherein+=")";
    Msgl.append( "</TABLE>\n" );

    int totalrecords=0;
    int count=0;

    //This is the query to get all request ids which need catalog part number
    String query="";
    query+=" select distinct p.SIZE_VERIFIED,p.STOCKING_TYPE,p.ITEM_VERIFIED, p.item_id item, p.part_id part, p.material_id material, p.GRADE Grade,p.components_per_item ||' x'||' '|| p.PART_SIZE ||' '||p.SIZE_UNIT||' '||p.PKG_STYLE||' '||p.DIMENSION Packaging,  ";
    query+=" p.SHELF_LIFE_DAYS,p.SHELF_LIFE_BASIS,p.STORAGE_TEMP,p.RECERT,p.SIZE_VARIES,p.MFG_PART_NO MPN, p.NET_WT||' '||p.NET_WT_UNIT Weight,it.CASE_QTY,it.ITEM_TYPE,it.REVISION_COMMENTS ";
    query+=" from global.part p,(select x.item_id from part x ,(select item_id  from part group by item_id  having count(*)=" + total1 + ") y  where x.item_id = y.item_id  ";

    if ( wherein.length() > 2 )
    {
      query+=" and  x.material_id in " + wherein + ") z";
    }
    else
    {
      query+=" and  x.material_id=6544645684568465844) z";
    }

    query+=",global.item it where p.item_id = z.item_id and z.item_id = it.item_id and it.item_type in ('MA','OB','MS','MV') and p.STOCKING_TYPE = 'S' order by p.item_id, p.material_id  ";

    String testfac="";
    boolean hasallmatids=true;
    boolean msgattached=false;
    int testTotal=0;
    int itemCount=0;
    int numRecsTest=0;

    Msgl.append( "<TABLE CLASS=\"columnar\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
    Msgl.append( "<TBODY>\n" );
    Msgl.append( "<TR>\n" );
    Msgl.append( "<TD VALIGN=\"TOP\">\n" );
    Msgl.append( "<DIV ID=\"orderdetail\" CLASS=\"scroll_column250\">\n" );
    Msgl.append( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"1\" WIDTH=\"100%\" ID=\"line_table\" CLASS=\"moveup\">\n" );

    StringBuffer Msgtemp=new StringBuffer();
	DBResultSet dbrs = null;
    ResultSet rs = null;
    try
    {
      dbrs=db.doQuery( query );
      rs=dbrs.getResultSet();

      String Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ALIGN=\"LEFT\" ";
      while ( rs.next() )
      {
        testTotal+=1;
        String itemID=BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM" ) );

        if ( numRecsTest != 0 )
        {
          if ( testfac.equalsIgnoreCase( ( rs.getString( "item" ).toString() ) ) )
          {
            numRecsTest+=1;
            Msgtemp.append( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"additemID('" + BothHelpObjs.makeBlankFromNull( rs.getString( "Item" ) ) + "','" + BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_VERIFIED" ) ) + "','"+BothHelpObjs.makeBlankFromNull(rs.getString( "ITEM_TYPE" ))+"')\" BORDERCOLOR=\"black\">\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( "<A HREF=\"javascript:showitemtcmBuys('" + itemID + "','')\">" + itemID + "</A><br>("+BothHelpObjs.makeBlankFromNull(rs.getString( "ITEM_TYPE" ))+")" );
            Msgtemp.append( "</TD>\n" );

            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "MATERIAL" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "GRADE" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "PACKAGING" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "MPN" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "WEIGHT" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "SHELF_LIFE_DAYS" ) ) );
            Msgtemp.append( " : " );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "SHELF_LIFE_BASIS" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "STORAGE_TEMP" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_VERIFIED" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "CASE_QTY" ) ) );
            Msgtemp.append( "</TD>\n" );
						Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
						Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "REVISION_COMMENTS" ) ) );
						Msgtemp.append( "</TD>\n" );

            Msgtemp.append( "</TR>\n" );
          }
          else
          {
            Msgl.append( Msgtemp );
            msgattached=true;
            itemCount++;

            if ( itemCount % 2 == 0 )
            {
              Color=" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ALIGN=\"LEFT\" ";
            }
            else
            {
              Color=" BGCOLOR=\"#E6E8FA\" onmouseover=\"className='menuh'\" onMouseout=\"className='menu'\" ALIGN=\"LEFT\" ";
            }

            hasallmatids=true;
            Msgtemp=new StringBuffer();

            testfac= ( rs.getString( "item" ) );
            numRecsTest+=1;

            Msgtemp.append( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"additemID('" + BothHelpObjs.makeBlankFromNull( rs.getString( "Item" ) ) + "','" + BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_VERIFIED" ) ) + "','"+BothHelpObjs.makeBlankFromNull(rs.getString( "ITEM_TYPE" ))+"')\" BORDERCOLOR=\"black\">\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( "<A HREF=\"javascript:showitemtcmBuys('" + itemID + "','')\">" + itemID + "</A><br>("+BothHelpObjs.makeBlankFromNull(rs.getString( "ITEM_TYPE" ))+")" );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "MATERIAL" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "GRADE" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "PACKAGING" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "MPN" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "WEIGHT" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "SHELF_LIFE_DAYS" ) ) );
            Msgtemp.append( " : " );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "SHELF_LIFE_BASIS" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "STORAGE_TEMP" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_VERIFIED" ) ) );
            Msgtemp.append( "</TD>\n" );
            Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
            Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "CASE_QTY" ) ) );
            Msgtemp.append( "</TD>\n" );
						Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
						Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "REVISION_COMMENTS" ) ) );
						Msgtemp.append( "</TD>\n" );

            Msgtemp.append( "</TR>\n" );
            msgattached=false;
          }
        }
        else
        {
          testfac= ( rs.getString( "item" ) );
          numRecsTest+=1;

          Msgtemp.append( "<TR ALIGN=\"MIDDLE\" ONCLICK=\"additemID('" + BothHelpObjs.makeBlankFromNull( rs.getString( "Item" ) ) + "','" +  BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_VERIFIED" ) ) + "','"+BothHelpObjs.makeBlankFromNull(rs.getString( "ITEM_TYPE" ))+"')\" BORDERCOLOR=\"black\">\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
          Msgtemp.append( "<A HREF=\"javascript:showitemtcmBuys('" + itemID + "','')\">" + itemID + "</A><br>("+BothHelpObjs.makeBlankFromNull(rs.getString( "ITEM_TYPE" ))+")" );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "MATERIAL" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "GRADE" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "PACKAGING" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "MPN" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "WEIGHT" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "SHELF_LIFE_DAYS" ) ) );
          Msgtemp.append( " : " );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "SHELF_LIFE_BASIS" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"10%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "STORAGE_TEMP" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "ITEM_VERIFIED" ) ) );
          Msgtemp.append( "</TD>\n" );
          Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
          Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "CASE_QTY" ) ) );
          Msgtemp.append( "</TD>\n" );
					Msgtemp.append( "<TD " + Color + " WIDTH=\"5%\">" );
					Msgtemp.append( BothHelpObjs.makeBlankFromNull( rs.getString( "REVISION_COMMENTS" ) ) );
					Msgtemp.append( "</TD>\n" );

          Msgtemp.append( "</TR>\n" );
        }

        totalrecords+=1;
        count+=1;
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      Msgl.append( "An Error Occured While Querying the Databse" );
      Msgl.append( "</BODY>\n</HTML>\n" );
      return Msgl;
    }
    finally
    {
      dbrs.close();
      if ( totalrecords == 0 )
      {
        Msgl.append( "<TR><TD>No Records Found</TD></TR>\n" );
      }
    }

    if ( !msgattached )
    {
      Msgl.append( Msgtemp );
    }

    Msgl.append( "</TABLE>\n" );
    Msgl.append( "</DIV>\n" );
    Msgl.append( "</TD>\n" );
    Msgl.append( "</TR>\n" );
    Msgl.append( "</TBODY>\n" );
    Msgl.append( "</TABLE>\n" );
    Msgl.append( "<CENTER><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"updateitem\" value=\"OK\" OnClick=\"ShowSearch(name,this)\">\n" );
    Msgl.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"button\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\"></CENTER>\n" );
    Msgl.append( "</FORM></BODY></HTML>\n" );

    return Msgl;
  }

  public String getnewitemID()  throws Exception
  {
    String sitemidreturned="0";
	DBResultSet dbrs = null;
    ResultSet rs = null;
    try
    {
      String bquery="select seq_item_id.nextval NEWITEMID from dual";
      dbrs=db.doQuery( bquery );
      rs=dbrs.getResultSet();

      while ( rs.next() )
      {
        sitemidreturned=rs.getString( "NEWITEMID" );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      dbrs.close();
    }

    return sitemidreturned;
    }

    public boolean updateItemid( String requestID,String itemID,String itemDesc1,String itemDesc2,String neworold )
    {
      boolean result=false;

      if ( "0".equalsIgnoreCase( itemID ) )
      {
        return false;
      }
      try
      {
        if ( "Yes".equalsIgnoreCase( neworold ) )
        {
          String updItemquery="insert into global.item (ITEM_ID,ITEM_DESC) values (" + itemID + ",'" + itemDesc1 + " " + itemDesc2 + "')";
          db.doUpdate( updItemquery );
        }

        String updquery="update catalog_add_item set ITEM_ID = " + itemID + " where REQUEST_ID = " + requestID + " and line_item = "+lineItem;
        db.doUpdate( updquery );

        result=true;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        result=false;
      }

      return result;
   }
  }