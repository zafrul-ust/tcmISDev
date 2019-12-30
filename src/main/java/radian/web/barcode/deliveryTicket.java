package radian.web.barcode;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

/**
 *
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 */

public class deliveryTicket
{
  public deliveryTicket()
  {

  }

  public String gendeliveryticket( Vector VFromSession ) throws Exception,IOException
  {
    delvtktGenerator obj=new delvtktGenerator();
    StringBuffer MsgSB=new StringBuffer();
    Vector dataV1=new Vector();

    Random rand=new Random();
    int tmpReq=rand.nextInt();
    Integer tmpReqNum=new Integer( tmpReq );

    String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
    String url=wwwHome + "labels/deliverytkt" + tmpReqNum.toString() + ".pdf";

    MsgSB.append( "<HTML><HEAD>\n" );
    MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
    MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
    MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
    MsgSB.append( "<TITLE>Box Label Generator</TITLE>\n" );
    MsgSB.append( "</HEAD>  \n" );
    MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
    MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
    MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n" );
    MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Labels</b></font><P></P><BR>\n" );
    MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n" );
    MsgSB.append( "</CENTER>\n" );

    Hashtable summary=new Hashtable();
    summary= ( Hashtable ) VFromSession.elementAt( 0 );
    int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
    int lineaddedcnt=0;
    int i=0;
    try
    {
      for ( int loop=0; loop < total; loop++ )
      {
        i++;
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) VFromSession.elementAt( i );

        String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
        if ( Line_Check.equalsIgnoreCase( "yes" ) )
        {
          lineaddedcnt++;
          dataV1.addElement( hD );
        }
      }

      if ( lineaddedcnt == 0 )
      {
        return buildERROR( "No Records Found" );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return buildERROR( " " );
    }

    try
    {
      if (dataV1.size() > 0)
      obj.buildTest( dataV1,tmpReqNum.toString() );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return buildERROR( " " );
    }
    finally
    {}

    MsgSB.append( "</BODY></HTML>    \n" );

    String retunrstring=MsgSB.toString();
    return retunrstring;
  }

  public String buildERROR( String message )
  {
    String Msg="";
    Msg+="<HTML><HEAD>\n";
    Msg+="</HEAD>\n";
    Msg+="<BODY><CENTER>\n";
    Msg+="<font face=Arial size=\"3\"><b>An Error Occurred - Please Try Again</b></font><P></P><BR>\n";
    Msg+="<font face=Arial size=\"3\"><b>" + message + "</b></font><P></P><BR>\n";
    Msg+="</CENTER></body></HTML>    \n";
    return Msg;
  }

}
