package radian.web.barcode;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.DbHelpers;

// * 05-02-05 Printing the same BOLs for Silicon Valley as for Phoenix Hub

public class BolPicklistPage
{
    // Initializing global Variables
    private ServerResourceBundle bundle=null;
    private TcmISDBModel db = null;
    public BolPicklistPage(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public BolPicklistPage(ServerResourceBundle b, TcmISDBModel d,Vector Quantity1s)
    {
        bundle = b;
        db = d;
    }

    public BolPicklistPage()
    {

    }

    public String GenerateBol( String picklistID,String bolDetails )  throws Exception,IOException
    {
      String Query_Statement="";
      BolGenerator obj=new BolGenerator();

      db=new RayTcmISDBModel( "Tcm_Ops" );
	  boolean tuscanhub = false;
      Random rand=new Random();
      int tmpReq=rand.nextInt();
      Integer tmpReqNum=new Integer( tmpReq );

      Vector resultF=new Vector();
      String QueryWhere="";

      String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
      String url=wwwHome + "labels/Bol" + tmpReqNum.toString() + ".pdf";

      Query_Statement+=" PICKLIST_ID=" + picklistID + " ";
      QueryWhere+=" BOLDATA.PICKLIST_ID=" + picklistID + " ";

      resultF=radian.web.HTMLHelpObj.getBolData( db,Query_Statement );
/*	  int testCount=DbHelpers.countQuery( db,"select count(*) from issue where " + Query_Statement + " and SOURCE_HUB in (2103,2119)" );
	  if ( testCount > 0 )
	  {
		tuscanhub = true;
	  }*/

      obj.setpickbol( true );
      try
      {
        if (resultF.size() >0)
        {
        obj.buildTest( QueryWhere,resultF,tmpReqNum.toString(),bolDetails,tuscanhub ) ;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        return "";
      }
      finally
      {
        db.close();
      }

      return url;
    }

    public String GenerateBol(Vector VFromSession, String bolDetails) throws Exception,IOException
    {
	  //System.out.println("------- Bill of Lading 22222 ");
        String Query_Statement = "";
        Hashtable dataFromSession = new Hashtable();
        Vector VMr_Number = new Vector();
        Vector VLine_Number = new Vector();
        Vector Vpicklist_Number = new Vector();
		boolean tuscanhub = false;
        db = new RayTcmISDBModel("Tcm_Ops");

        BolGenerator obj = new BolGenerator();
        StringBuffer MsgSB = new StringBuffer();
        String QueryWhere = "";

        Random rand = new Random();
        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

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

        Vector resultF = new Vector();

        Hashtable Boldata = new Hashtable();
        try
        {
          for ( int i=0; i < VFromSession.size(); i++ )
          {
            dataFromSession=new Hashtable();
            VMr_Number=new Vector();
            VLine_Number=new Vector();
            Vpicklist_Number=new Vector();

            dataFromSession= ( Hashtable ) VFromSession.elementAt( i );

            if ( dataFromSession.size() == 0 )
            {
              return buildERROR( "No Records Found" );
            }

            VMr_Number= ( Vector ) dataFromSession.get( "mr_number" );
            VLine_Number= ( Vector ) dataFromSession.get( "line_number" );
            Vpicklist_Number= ( Vector ) dataFromSession.get( "picklist_number" );

		//System.out.println("------ data: "+VMr_Number+"="+VLine_Number+"-"+Vpicklist_Number);

            for ( int j=0; j < VMr_Number.size(); j++ )
            {
              if ( j > 0 )
              {
                QueryWhere+=" or ";
			  }
			  Query_Statement=" PR_NUMBER =" + ( String ) VMr_Number.elementAt( j ) + " and LINE_ITEM = " + ( String ) VLine_Number.elementAt( j ) +
                             " and PICKLIST_ID = " + ( String ) Vpicklist_Number.elementAt( j ) + " ";

			  Vector BolAlldata=new Vector();
			  BolAlldata=radian.web.HTMLHelpObj.getBolHashData( db,Query_Statement );
/*			  int testCount=DbHelpers.countQuery( db,"select count(*) from issue where " + Query_Statement + " and SOURCE_HUB in (2103,2119)" );
			  if ( testCount > 0 )
			  {
				tuscanhub = true;
			  }*/

			  for ( int bol=0; bol < BolAlldata.size(); bol++ )
			  {
				Boldata=new Hashtable();
				Boldata= ( Hashtable ) BolAlldata.elementAt( bol );
				resultF.addElement( Boldata );
			  }
              String mrnumber1 = ( String ) VMr_Number.elementAt( j );
              mrnumber1 = mrnumber1.replace(".","12345");
			  QueryWhere+=" (BOLDATA.PR_NUMBER =" + mrnumber1 + " and BOLDATA.LINE_ITEM = " + ( String ) VLine_Number.elementAt( j ) +
                " and BOLDATA.PICKLIST_ID = " + ( String ) Vpicklist_Number.elementAt( j ) + ") ";
		    }
          }
        }//End Try
        catch (Exception e)
        {
            e.printStackTrace();
            return buildERROR(" ");
        }

        obj.setpickbol(true);
        try {
          if (resultF.size() >0)
            obj.buildTest(QueryWhere,resultF,tmpReqNum.toString(),bolDetails,tuscanhub);
        }catch (Exception e)
        {
        e.printStackTrace();
        return buildERROR(" ");
        }
        finally
        {
          db.close();
        }

        MsgSB.append("</BODY></HTML>\n");

        String retunrstring = MsgSB.toString();
        return retunrstring;
    }
    public String GenerateBol(Vector VFromSession,String Picklistid, String bolDetails) throws Exception,IOException
    {
        String Query_Statement = "";
        Hashtable dataFromSession = new Hashtable();
        Vector VMr_Number = new Vector();
        Vector VLine_Number = new Vector();
        Vector Vpicklist_Number = new Vector();
        boolean tuscanhub = false;
        db = new RayTcmISDBModel("Tcm_Ops");
        BolGenerator obj = new BolGenerator();

        String QueryWhere = "";

        Random rand = new Random();
        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

        String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
        String url = wwwHome + "labels/Bol"+tmpReqNum.toString()+".pdf";

        Vector resultF=new Vector();
        Hashtable Boldata=new Hashtable();
        try
        {
          for ( int i=0; i < VFromSession.size(); i++ )
          {
            dataFromSession=new Hashtable();
            VMr_Number=new Vector();
            VLine_Number=new Vector();
            Vpicklist_Number=new Vector();

            dataFromSession= ( Hashtable ) VFromSession.elementAt( i );

            if ( dataFromSession.size() == 0 )
            {
              return buildERROR( "No Records Found" );
            }

            VMr_Number= ( Vector ) dataFromSession.get( "mr_number" );
            VLine_Number= ( Vector ) dataFromSession.get( "line_number" );
            if (Picklistid == null || Picklistid.length() == 0)
            {
              Vpicklist_Number= ( Vector ) dataFromSession.get( "picklist_number" );
            }

            for ( int j=0; j < VMr_Number.size(); j++ )
            {
              if ( j > 0 )
              {
                //Query_Statement += " or ";
                QueryWhere+=" or ";
              }
              if (Picklistid == null || Picklistid.length() == 0)
              {
                Picklistid = ( String ) Vpicklist_Number.elementAt( j );
              }
              Query_Statement=" PR_NUMBER =" + ( String ) VMr_Number.elementAt( j ) + " and LINE_ITEM = " + ( String ) VLine_Number.elementAt( j ) +
                              " and PICKLIST_ID = " + Picklistid + " ";

              Vector BolAlldata=new Vector();
              BolAlldata=radian.web.HTMLHelpObj.getBolHashData( db,Query_Statement );
/*			  int testCount=DbHelpers.countQuery( db,"select count(*) from issue where " + Query_Statement + " and SOURCE_HUB in (2103,2119)" );
			  if ( testCount > 0 )
			  {
				tuscanhub = true;
			  }*/


              for ( int bol=0; bol < BolAlldata.size(); bol++ )
              {
                Boldata=new Hashtable();
                Boldata= ( Hashtable ) BolAlldata.elementAt( bol );
                resultF.addElement( Boldata );
              }
              String mrnumber1 = ( String ) VMr_Number.elementAt( j );
              mrnumber1 = mrnumber1.replace(".","12345");
              QueryWhere+=" (BOLDATA.PR_NUMBER =" + mrnumber1 + " and BOLDATA.LINE_ITEM = " + ( String ) VLine_Number.elementAt( j ) +
                 " and BOLDATA.PICKLIST_ID = " + Picklistid + ") ";
            }
         }
        }//End Try
        catch (Exception e)
        {
            e.printStackTrace();
            return buildERROR(" ");
        }

        obj.setpickbol(true);
        try {
          if (resultF.size() >0)
            obj.buildTest(QueryWhere,resultF,tmpReqNum.toString(),bolDetails,tuscanhub);
        }catch (Exception e)
        {
        e.printStackTrace();
        return buildERROR(" ");
        }
        finally
        {
          db.close();
        }
       return url;
    }

     public String buildERROR(String message)
    {
        String Msg = "";
        Msg +="<HTML><HEAD>\n";
        Msg +="</HEAD>\n";
        Msg +="<BODY><CENTER>\n";
        Msg +="<font face=Arial size=\"3\"><b>An Error Occurred - Please Try Again</b></font><P></P><BR>\n";
        Msg +="<font face=Arial size=\"3\"><b>"+message+"</b></font><P></P><BR>\n";
        Msg +="</CENTER></body></HTML>    \n";

        return Msg;
    }
}

