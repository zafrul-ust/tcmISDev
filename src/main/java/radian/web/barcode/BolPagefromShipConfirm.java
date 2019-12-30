package radian.web.barcode;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

// 01-21-04 If it errors out giving the right message
// 05-02-05 Printing the same BOLs for Silicon Valley as for Phoenix Hub
public class BolPagefromShipConfirm
{
    private TcmISDBModel db = null;

    public BolPagefromShipConfirm()
    {

    }

    public String GenerateBol(Vector VFromSession, String bolDetails) throws Exception,IOException
    {
      String QueryWhere="";
      String Query_Statement="";
      BolGenerator obj=new BolGenerator();
      Random rand=new Random();
      int tmpReq=rand.nextInt();
      Integer tmpReqNum=new Integer( tmpReq );
      Vector resultF=new Vector();
      db=new RayTcmISDBModel( "Tcm_Ops" );
      String hosturl=radian.web.tcmisResourceLoader.gethosturl();
      String url=hosturl + "labels/Bol" + tmpReqNum.toString() + ".pdf";
      Hashtable Boldata=new Hashtable();
	  boolean tuscanhub = false;

      try
      {

        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) VFromSession.elementAt( 0 );
        int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

        int line_count=0;
        for ( int i=1; i < total + 1; i++ )
        {
          Hashtable hD=new Hashtable();
          hD= ( Hashtable ) VFromSession.elementAt( i );

          String Line_Check="";
          Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );

          String prnumber=hD.get( "PR_NUMBER" ).toString();
          String lineitem=hD.get( "LINE_ITEM" ).toString();
          String batch=hD.get( "BATCH" ).toString();

          if ( "yes".equalsIgnoreCase( Line_Check ) )
          {
            if ( line_count > 0 )
            {
              QueryWhere+=" or ";
            }

            Query_Statement=" PR_NUMBER=" + prnumber + " and LINE_ITEM=" + lineitem + " and BATCH=" + batch + " ";

            Vector BolAlldata=new Vector();
            BolAlldata=radian.web.HTMLHelpObj.getBolHashData( db,Query_Statement );
/*			int testCount=DbHelpers.countQuery( db,"select count(*) from issue where " + Query_Statement + " and SOURCE_HUB in (2103,2119)" );
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

            QueryWhere+=" (BOLDATA.PR_NUMBER=" + prnumber.replace(".","12345") + " and BOLDATA.LINE_ITEM=" + lineitem + " and BOLDATA.BATCH=" + batch + ") ";

            line_count++;
          }
        }

        if ( line_count == 0 )
        {
          return "";
        }

        Query_Statement=Query_Statement.trim();

      }
      catch ( Exception e )
      {
        e.printStackTrace();
        return "";
      }

      try
      {
        String url2test ="";
        if (resultF.size() > 0)
        {
         url2test = obj.buildTest( QueryWhere,resultF,tmpReqNum.toString(),bolDetails,tuscanhub ) ;
        }        
      if (url2test.trim().length() == 0 ){url = "";}
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

    public String getServletInfo()
    {
        return "radian.web.servlets.SearchMsds Information";
    }

}

