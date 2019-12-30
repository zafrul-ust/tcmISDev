package radian.web.barcode;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 05-02-05 Printing the same BOLs for Silicon Valley as for Phoenix Hub
 */
public class BolPage
{
    private ServerResourceBundle bundle=null;
    private TcmISDBModel db = null;

    public BolPage(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public BolPage(ServerResourceBundle b, TcmISDBModel d,Vector Quantity1s)
    {
        bundle = b;
        db = d;
    }

    public BolPage()
    {

    }

    public String GenerateBol(Vector VFromSession) throws Exception,IOException
    {
        Hashtable dataFromSession = new Hashtable();
        Vector VMr_Number = new Vector();
        Vector VLine_Number = new Vector();
        String Batch_number = "";
		boolean tuscanhub = false;
        BolGenerator obj = new BolGenerator();
        StringBuffer MsgSB = new StringBuffer();
        String Query_Statement = "";
        String QueryWhere = "";

        Random rand = new Random();
        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

        Vector resultF = new Vector();
        db = new RayTcmISDBModel("Tcm_Ops");

        String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
        String url = wwwHome + "labels/Bol"+tmpReqNum.toString()+".pdf";

        MsgSB.append("<HTML><HEAD>\n");
        MsgSB.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+url+"\">\n");
        MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
        MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
        MsgSB.append("<TITLE>BOL Printer</TITLE>\n");
        MsgSB.append("</HEAD>  \n");
        MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
        MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
        MsgSB.append("</CENTER>\n");

        Hashtable Boldata = new Hashtable();
        String mrnumber="";
        String lineNumber="";

        try
        {
          for ( int i=0; i < VFromSession.size(); i++ )
          {
            dataFromSession=new Hashtable();
            VMr_Number=new Vector();
            VLine_Number=new Vector();
            Batch_number="";

            dataFromSession= ( Hashtable ) VFromSession.elementAt( 0 );

            if ( dataFromSession.size() == 0 )
            {
              return buildERROR( "No Records Found" );
            }

            Batch_number=dataFromSession.get( "batch" ).toString();
            VMr_Number= ( Vector ) dataFromSession.get( "mr_number" );
            VLine_Number= ( Vector ) dataFromSession.get( "line_number" );

            for ( int j=0; j < VMr_Number.size(); j++ )
            {
              mrnumber=VMr_Number.elementAt( j ).toString();
              lineNumber=VLine_Number.elementAt( j ).toString();

              if ( mrnumber.trim().length() > 0 && lineNumber.trim().length() > 0 && Batch_number.trim().length() > 0 )
              {
                if ( j > 0 )
                {
                  QueryWhere+=" or ";
                }

                Query_Statement=" BATCH=" + Batch_number + " and PR_NUMBER = " + mrnumber + " and LINE_ITEM = " + lineNumber + " ";

                Vector BolAlldata=new Vector();
                BolAlldata=radian.web.HTMLHelpObj.getBolHashData( db,Query_Statement );
/*				int testCount=DbHelpers.countQuery( db,"select count(*) from issue where " + Query_Statement + " and SOURCE_HUB in (2103,2119)" );
				if ( testCount > 0 )
				{
				  tuscanhub = true;
  			    }*/

                for ( int bol=0; bol < BolAlldata.size(); bol++ )
                {
                  Boldata=new Hashtable();
                  Boldata= ( Hashtable ) BolAlldata.elementAt( bol );

                  String batchreplce=Boldata.get( "BATCH" ).toString();
                  Boldata.remove( "PICKLIST_ID" );
                  Boldata.put( "PICKLIST_ID",batchreplce );

                  resultF.addElement( Boldata );
                }

                mrnumber = mrnumber.replace(".","12345");
                QueryWhere+=" (BOLDATA.PR_NUMBER = " + mrnumber + " and BOLDATA.LINE_ITEM = " + lineNumber + " and BOLDATA.PICKLIST_ID = " + Batch_number + ") ";
              }
            }
          }
        } //End Try
        catch ( Exception e )
        {
          e.printStackTrace();
          return buildERROR( " " );
        }

        try
        {
          if (resultF.size() >0)
          {
            obj.buildTest( QueryWhere,resultF,tmpReqNum.toString(),"Yes",tuscanhub );
          }
          else
          {
            return buildERROR( " " );
          }
        }
        catch ( Exception e )
        {
          e.printStackTrace();
          return buildERROR( " " );
        }
        finally
        {
          db.close();
        }

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