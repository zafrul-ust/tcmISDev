package radian.web.barcode;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 */

public class hublabelPage
{
    // Initializing global Variables
    private TcmISDBModel db = null;
    private static org.apache.log4j.Logger barclog = org.apache.log4j.Logger.getLogger(hublabelPage.class);
    public hublabelPage(TcmISDBModel d)
    {
        db = d;
    }

    public String generateContainerLabel(Vector Seqnumbers,String personnelId,String hubNum) throws ServletException,IOException
    {
        String Query_Statement = "";
        DBResultSet dbrs = null;
        ResultSet rs = null;

        Random rand = new Random();
        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

        String updateprintdate = "";

        String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
        String url = wwwHome + "labels/hubbin"+tmpReqNum.toString()+".pdf";
		String jnlpurl = wwwHome + "labels/hubbin"+tmpReqNum.toString()+".jnlp";

        if (Seqnumbers.size() == 0)
        {
            return "";
        }

	try
	{
	  String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelId + " and x.LABEL_STOCK = '31'";
	  String printerPath = "";
	  String printerRes = "";
	  try
	  {
		dbrs=db.doQuery( iszplprinter );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		  printerPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
		  printerRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
	  }
	  finally
	  {
		if ( dbrs != null ) { dbrs.close();}
	  }

	  if (printerPath.trim().length() > 0)
	  {
		ZPLBarCodeGenerator zplobj=new ZPLBarCodeGenerator( db );
		zplobj.buildbinZpl( Seqnumbers,tmpReqNum.toString(),"31",hubNum,personnelId,printerPath,printerRes );
		url=jnlpurl;
	  }
	  else
	  {
		return "";
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  return "";
	}

	return url;
  }
}

