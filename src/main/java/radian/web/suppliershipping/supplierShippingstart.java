package radian.web.suppliershipping;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.web.BarCodeHelpObj;
import radian.web.barcode.ZPLBarCodeGenerator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.hub.beans.LabelFormatTemplateFileBean;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      Haas TCM
 * @author       Nawaz Shaik
 * @version
 */

public class supplierShippingstart  extends HttpServlet  implements SingleThreadModel {
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  /**
   * HTTP POST handler.
   *
   * @param request               An object implementing the request context
   * @param response              An object implementing the response context
   * @exception ServletException  A wrapper for all exceptions thrown by the
   *      servlet.
   */

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException,
      IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    RayTcmISDBModel db = null;
    supplierShipping obj = null;
    HttpSession session = request.getSession();

    try {
      db = new RayTcmISDBModel("supplier","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
      if (db == null) {
        PrintWriter out2 = new PrintWriter(response.getOutputStream());
        out2.println("Starting DB");
        out2.println("DB is null");
        out2.close();
        return;
      }

      Hashtable loginresult = new Hashtable();
      loginresult = radian.web.loginhelpObj.logintopage(session, request, db,out);
      String auth = (String) loginresult.get("AUTH");
      String errormsg = (String) loginresult.get("ERROSMSG");

      if (auth == null) {
        auth = "challenge";
      }
      if (errormsg == null) {
        errormsg = "";
      }

	  if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
	  {
		out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Supplier Shipping" ) );
		out.flush();
	  }
	  else
	  {
		obj=new supplierShipping( db );
		if ( radian.web.HTMLHelpObj.hasaccessto( session,"suppliershipping" ) )
		{
		  obj.setupdateStatus( true );
		}
		else
		{
		  obj.setupdateStatus( false );
		  out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Supplier Shipping",session ) );
		  out.close();
		  return;
		}

		session.setAttribute( "SUPPLIERPAGE","Yes" );
		session.setAttribute( "COMPANYID","SUPPLIER" );

		String SubUser_Action=request.getParameter( "SubUserAction" );
		if ( SubUser_Action == null )
		  SubUser_Action="";

		if ( "genpartlabels".equalsIgnoreCase( SubUser_Action ) )
		{
		  String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();

		  Random rand=new Random();
		  int tmpReq=rand.nextInt();
		  Integer tmpReqNum=new Integer( tmpReq );

			String printerPath=request.getParameter( "printerPath" );
					if ( printerPath == null )
						printerPath="";

		 String printerRes=request.getParameter( "printerRes" );
		 if ( printerRes == null )
			printerRes="";

		  String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
		  String url="";
		  String jnlpurl=wwwHome + "labels/Barcode" + tmpReqNum.toString() + ".jnlp";

		  DBResultSet dbrs=null;
		  ResultSet rs=null;
			int printerCount = 0;

      if (printerPath == null || (printerPath.length() == 0))
			{
			 String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " +  personnelid + " and x.LABEL_STOCK = '35'";
			 //String printerPath="";

			try
			{
			 dbrs=db.doQuery( iszplprinter );
			 rs=dbrs.getResultSet();

			 while ( rs.next() )
			 {
				printerPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
				printerRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );
				printerCount++;
			 }
			}
			catch ( Exception e )
			{
			 e.printStackTrace();
		  }
			finally
			{
			 if ( dbrs != null )
			 {
				dbrs.close();
			 }
			}
		 }

		 if ( printerCount > 1 )
		 {
			printPrinterChoice(db,out, "No", printerPath, printerRes, "35", personnelid, "");
			return;
		 }

		 String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
		 String file="";
		 file=writefilepath + "Barcode" +  tmpReqNum.toString() + ".txt";
		 StringBuilder zpl = new StringBuilder();

		  Vector invgrpdata=new Vector();
		  invgrpdata.addElement("supplierpart"+printerRes+".txt");
		  BarCodeHelpObj BarcodeConverter=new BarCodeHelpObj();
		  ZPLBarCodeGenerator zplobj=new ZPLBarCodeGenerator( db );
		  if ( printerPath.trim().length() > 0 )
		  {
			zpl.append(getLabelTemplateFromFileName(db,invgrpdata));

			Vector VFromSession= ( Vector ) session.getAttribute( "PRINTDATA" );
			Vector Vpart_Number=new Vector();
			Vector Vlabl_qty=new Vector();
            Vector inventoryGroupVector =new Vector();
            Vector itemIdVector=new Vector();

			Hashtable dataFromSession=new Hashtable();
			boolean usepqvalue = true;

			for ( int i=0; i < VFromSession.size(); i++ )
			{
			  dataFromSession=new Hashtable();
			  Vpart_Number=new Vector();

			  dataFromSession= ( Hashtable ) VFromSession.elementAt( i );
			  Vpart_Number= ( Vector ) dataFromSession.get( "part_number" );
			  Vlabl_qty = ( Vector ) dataFromSession.get( "lbl_qty" );
              inventoryGroupVector = ( Vector ) dataFromSession.get( "inventoryGroup" );
              itemIdVector = ( Vector ) dataFromSession.get( "itemId" );

			  for ( int j=0; j < Vpart_Number.size(); j++ )
			  {
				Hashtable recptData=new Hashtable();
				String partnumber ="";
				partnumber= ( String ) Vpart_Number.elementAt( j );

				String labelqty ="";
				labelqty= ( String ) Vlabl_qty.elementAt( j );

                String inventoryGroup ="";
				inventoryGroup= ( String ) inventoryGroupVector.elementAt( j );

                String itemId ="";
                itemId= ( String ) itemIdVector.elementAt( j );
                                                                      
                if (partnumber.trim().length() > 0)
				{
				 recptData.put("DETAIL_9", partnumber);
                 String partShortName = "";
                 String partDescription = "";

                 String Query_Statement="select distinct PART_SHORT_NAME, PART_DESCRIPTION " +
                 " from container_label_detail_view where ITEM_ID = " + itemId +" and INVENTORY_GROUP = '" + inventoryGroup + "'";
                 try
                 {
                    dbrs=db.doQuery( Query_Statement );
                    rs=dbrs.getResultSet();

                    while ( rs.next() )
                    {
                        partShortName = rs.getString( "PART_SHORT_NAME" ) == null ? " " : rs.getString( "PART_SHORT_NAME" );
                        partDescription = rs.getString( "PART_DESCRIPTION" ) == null ? " " : rs.getString( "PART_DESCRIPTION" );
                        if (partDescription.length() > 50)
                        {
                            partDescription = partDescription.substring(0,50);
                        }
                    }
                 }
                 catch ( Exception e )
                 {
                    e.printStackTrace();
                 }
                 finally
                 {
                    if ( dbrs != null ){dbrs.close();}
                 }

                    if (partShortName.trim().length() > 0)
                    {
                        recptData.put("PART_DESCRIPTION", partShortName);
                    }
                    else
                    {
                        recptData.put("PART_DESCRIPTION", partDescription);
                    }
                 zpl.append(zplobj.pmclabel(recptData, labelqty, usepqvalue));
				}
			  }
			}

			StringBuffer blankbuffer=new StringBuffer();
			zplobj.writefiletodisk(blankbuffer, zplobj.buildprintjnlpfile ("","","labels/Barcode"+tmpReqNum.toString()+".txt",personnelid,printerPath, zpl.toString()), "Barcode"+tmpReqNum.toString()+"",false);

			url=jnlpurl;
		  }

		  out.println( radian.web.HTMLHelpObj.labelredirection( url ) );
		  out.close();
		}
		else
		{
		  obj.doPost( request,response );
		}
	  }
    }
    catch (Exception e) {
      e.printStackTrace();
      return;
    }
    finally {
      db.close();
      obj = null;
      try {
        out.close();
      }
      catch(Exception e) {
        //ignore
      }
      //Runtime.getRuntime().gc();
      return;
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws
      ServletException, IOException {
    doPost(request, response);
  }
  
  private String getLabelTemplateFromFileName(RayTcmISDBModel db, Vector filenames) throws BaseException {
	  DBResultSet dbrs=null;
	  ResultSet rs=null;
	  
	  StringBuilder labelTemplate = new StringBuilder();
	  StringBuilder query = new StringBuilder("select LABEL_TEMPLATE from LABEL_FORMAT_TEMPLATE ");
      query.append(" where LABEL_TEMPLATE_FILENAME in (");
	  for (Object file : filenames) {
		  query.append("'").append(file.toString()).append("',");
	  }
	  query.replace(query.lastIndexOf(","),query.length(),")");
      try
      {
         dbrs=db.doQuery( query.toString() );
         rs=dbrs.getResultSet();

         String linefeed="";
    	 linefeed+= ( char ) ( 13 );
    	 linefeed+= ( char ) ( 10 );
	     while (rs.next()) {
	    	Clob clob = rs.getClob("LABEL_TEMPLATE");
	    	Reader stream = clob.getCharacterStream();
	    	BufferedReader br = new BufferedReader(stream);
	    	String line = "";
	    	while ((line = br.readLine()) != null) {
	    		labelTemplate.append(line).append(linefeed);
	    	}
	    	br.close();
	     }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      finally
      {
         if ( dbrs != null ){dbrs.close();}
      }
      
      return labelTemplate.toString();
  }

 private void printPrinterChoice(TcmISDBModel db,PrintWriter out,String printKitLabels,String printerPath,String printerRes,String paperSize,String personnelId,String HubNoforlabel)
	{
		out.println("<HTML>\n");
		out.println("<HEAD>\n");
		out.println("<TITLE>Re-Print Labels</TITLE>\n");
		out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
		out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
		out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
		out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
		out.println("<SCRIPT SRC=\"/clientscripts/picking.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
		out.println("</HEAD>\n");
		out.println("<BODY>\n");

		out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
		out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
		out.println("</DIV>\n");
		out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

		out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Re-Print Labels</B>\n"));

		out.println("<FORM method=\"POST\" NAME=\"picking\" onSubmit=\"return SubmitOnlyOnce()\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\"genpartlabels\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"printerRes\" VALUE=\""+printerRes+"\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"printKitLabels\" VALUE=\""+printKitLabels+"\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"paperSize\" VALUE=\""+paperSize+"\">\n");
		out.println("<INPUT TYPE=\"hidden\" NAME=\"HubNoforlabel\" VALUE=\""+HubNoforlabel+"\">\n");

		out.println("<BR><B>Please Pick a Printer:</B>");
		out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"500\" CLASS=\"moveup\">\n");
		DBResultSet dbrs = null;
		ResultSet rs = null;
		String tmpPrinterPath = "";
		String tmpPrinterRes = "";

		try
		{
		String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " +
							personnelId + " and x.LABEL_STOCK = '" + paperSize + "'";
		dbrs=db.doQuery( iszplprinter );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
			tmpPrinterPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
			tmpPrinterRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );

			out.println( "<TR VALIGN=\"MIDDLE\">\n" );
			out.println( "<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
			out.println( "<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"printerPath\" onclick=\"setprinterRes('" + tmpPrinterRes + "')\" value=\"" + tmpPrinterPath + "\" " + ( tmpPrinterPath.equalsIgnoreCase( printerPath ) ? "checked" : "" ) + ">\n" );
			out.println( "</TD>\n<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" ID=\"option3\">" + tmpPrinterPath + "&nbsp;</TD>\n" );
			/*out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
			 out.println("<INPUT ID=\"option2\" TYPE=\"radio\" NAME=\"printerRes\" value=\""+printerRes+"\">\n");
			 out.println("</TD>\n<TD WIDTH=\"5%\" CLASS=\"announce\" ID=\"option4\">"+printerRes+"</TD>\n\n");*/
			out.println( "</TR>\n" );
		}
		}
		catch ( Exception e )
		{
		e.printStackTrace();
		}
		finally
		{
		if ( dbrs != null ){dbrs.close();}
		}

		out.println("<TR VALIGN=\"MIDDLE\">\n");
		//Generate Labels Button
		out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
		out.println("</TD>\n");

		out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
		out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
		out.println("</TD></TR>\n");

		/*out.println("<TR VALIGN=\"MIDDLE\">\n");
		out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
		out.println( "</TD>\n" );
		out.println( "<TD WIDTH=\"5%\" COLSPAN=\"7\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
		out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
		out.println( "</TD>\n" );*/
		out.println("</TABLE>\n");
	}

}