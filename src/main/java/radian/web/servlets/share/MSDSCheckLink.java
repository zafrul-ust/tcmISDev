
package radian.web.servlets.share;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        Check MSDS URLS for 404 Errors
 * Description:  Your description
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 */

public class MSDSCheckLink extends HttpServlet implements SingleThreadModel
{
      private ServerResourceBundle bundle=null;
      private TcmISDBModel db = null;
      private static org.apache.log4j.Logger msdslog = org.apache.log4j.Logger.getLogger(MSDSCheckLink.class);

      public MSDSCheckLink(ServerResourceBundle b, TcmISDBModel d)
      {
          bundle = b;
          db = d;
      }

      public MSDSCheckLink()
      {

      }

    //public static void testMSDS(String client) throws Exception
    public void doPost(HttpServletRequest request,HttpServletResponse response,String client) throws ServletException,IOException
    {
        ResultSet rs = null;
        ResultSet rs12 = null;
        DBResultSet dbrs=null;
        String query="";

	    String onlymostrecent = request.getParameter("onlymostrecent");
		  if (onlymostrecent == null)
			onlymostrecent = "";
		onlymostrecent = onlymostrecent.trim();

        query = "select  /*+ RULE */ distinct m.REVIEWED_BY REVIEWED_BY ,to_char(m.REVIEW_DATE,'mm/dd/yyyy') REVIEW_DATE1, m.on_line,m.content,'' content11, m.REVISION_DATE,to_char(m.REVISION_DATE,'mm/dd/yyyy') revision_date1,b.material_id, ' ' cat_part_no, ' ' catalog_id, '' mfg_desc, b.MSDS_ON_LINE, ' ' trade_name,' ' FACILITY_ID ";
        query = query + "from cat_part_all_view a, catalog_item_view b, cat_part_synonym syn ,msds m where  a.item_id = b.item_id ";
        query = query + "and nvl(a.bundle,'EA') = nvl(b.bundle,'EA') and a.cat_part_no = syn.cat_part_no and a.catalog_id=syn.catalog_id ";
        query = query + "and a.status in (select status from catalog_part_status where active='y')  ";
        query = query + "and b.MATERIAL_ID = m.material_id  ";
        query = query + "and m.on_line = 'Y' ";
		if ("Yes".equalsIgnoreCase(onlymostrecent))
		{
		  query=query + "and m.revision_date=fx_msds_most_recent_date(b.MATERIAL_ID) ";
		}
        query = query + "order by material_id,revision_date desc ";

				query = "SELECT DISTINCT m.REVIEWED_BY REVIEWED_BY1 ,to_char(m.REVIEW_DATE,'mm/dd/yyyy') REVIEW_DATE1, m.on_line, m.content, '' content11, m.revision_date,TO_CHAR (m.revision_date, 'mm/dd/yyyy') revision_date1, ";
				query += "m.material_id, ' ' cat_part_no, ' ' catalog_id, '' mfg_desc,m.on_line msds_on_line, ' ' trade_name, ' ' facility_id	 ";
 			  query += "FROM GLOBAL.msds m WHERE m.on_line = 'Y' ORDER BY material_id, revision_date DESC  ";

        int testTotal = 0;
        int numRecsTest = 0 ;

        Hashtable Result1 = new Hashtable();
        Hashtable Result2 = new Hashtable();
        Vector ResultV = new Vector();
        Vector ResultF = new Vector();
        String testfac = "";

        try
        {
            dbrs=db.doQuery( query );
            rs=dbrs.getResultSet();

            while (rs.next())
            {
                testTotal += 1;
								//System.out.println("Here 98  "+rs.getString("material_id").toString()+"");
                if (numRecsTest != 0)
                {
                    if (testfac.equalsIgnoreCase((rs.getString("material_id").toString())))
                    {
                        Result2 = new Hashtable();
                        //CONTENT
                        Result2.put( "CONTENT",rs.getString( "CONTENT" ) == null ? "" : rs.getString( "CONTENT" ) );
                        //CONTENT11
                        Result2.put( "CONTENT11",rs.getString( "CONTENT11" ) == null ? "" : rs.getString( "CONTENT11" ) );
                        //REVISION_DATE
                        Result2.put( "REVISION_DATE",rs.getString( "REVISION_DATE" ) == null ? "" : rs.getString( "REVISION_DATE" ) );
                        //REVISION_DATE1
                        Result2.put( "REVISION_DATE1",rs.getString( "REVISION_DATE1" ) == null ? "" : rs.getString( "REVISION_DATE1" ) );
                        //MATERIAL_ID
                        Result2.put( "MATERIAL_ID",rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" ) );
                        //CAT_PART_NO
                        Result2.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ) );
                        //CATALOG_ID
                        Result2.put( "CATALOG_ID",rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ) );
                        //MFG_DESC
                        Result2.put( "MFG_DESC",rs.getString( "MFG_DESC" ) == null ? "" : rs.getString( "MFG_DESC" ) );
                        //TRADE_NAME
                        Result2.put( "TRADE_NAME",rs.getString( "TRADE_NAME" ) == null ? "" : rs.getString( "TRADE_NAME" ) );
                        //FACILITY_ID
                        Result2.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ) );
												//REVIEWED_BY1
												Result2.put( "REVIEWED_BY1",rs.getString( "REVIEWED_BY1" ) == null ? "" : rs.getString( "REVIEWED_BY1" ) );
												//REVIEW_DATE1
												Result2.put( "REVIEW_DATE1",rs.getString( "REVIEW_DATE1" ) == null ? "" : rs.getString( "REVIEW_DATE1" ) );

                        ResultV.addElement(Result2);

                        numRecsTest += 1;
                    }
                    else
                    {
                        String Sizeorrecords = ""+ResultV.size();

                        Result1.put("MATERIAL_IDS",ResultV);
                        Result1.put("SIZE",Sizeorrecords);
                        ResultF.addElement(Result1);

                        Result1 = new Hashtable();
                        Result2 = new Hashtable();
                        ResultV = new Vector();

                        testfac = (rs.getString("material_id"));
                        //CONTENT
                        Result2.put( "CONTENT",rs.getString( "CONTENT" ) == null ? "" : rs.getString( "CONTENT" ) );
                        //CONTENT11
                        Result2.put( "CONTENT11",rs.getString( "CONTENT11" ) == null ? "" : rs.getString( "CONTENT11" ) );
                        //REVISION_DATE
                        Result2.put( "REVISION_DATE",rs.getString( "REVISION_DATE" ) == null ? "" : rs.getString( "REVISION_DATE" ) );
                        //REVISION_DATE1
                        Result2.put( "REVISION_DATE1",rs.getString( "REVISION_DATE1" ) == null ? "" : rs.getString( "REVISION_DATE1" ) );
                        //MATERIAL_ID
                        Result2.put( "MATERIAL_ID",rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" ) );
                        //CAT_PART_NO
                        Result2.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ) );
                        //CATALOG_ID
                        Result2.put( "CATALOG_ID",rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ) );
                        //MFG_DESC
                        Result2.put( "MFG_DESC",rs.getString( "MFG_DESC" ) == null ? "" : rs.getString( "MFG_DESC" ) );
                        //TRADE_NAME
                        Result2.put( "TRADE_NAME",rs.getString( "TRADE_NAME" ) == null ? "" : rs.getString( "TRADE_NAME" ) );
                        //FACILITY_ID
                        Result2.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ) );
												//REVIEWED_BY1
												Result2.put( "REVIEWED_BY1",rs.getString( "REVIEWED_BY1" ) == null ? "" : rs.getString( "REVIEWED_BY1" ) );
												//REVIEW_DATE1
												Result2.put( "REVIEW_DATE1",rs.getString( "REVIEW_DATE1" ) == null ? "" : rs.getString( "REVIEW_DATE1" ) );

                        ResultV.addElement(Result2);

                        numRecsTest += 1;
                    }
                }
                else
                {
                    testfac = (rs.getString("material_id"));
                    //CONTENT
                    Result2.put( "CONTENT",rs.getString( "CONTENT" ) == null ? "" : rs.getString( "CONTENT" ) );
                    //CONTENT11
                    Result2.put( "CONTENT11",rs.getString( "CONTENT11" ) == null ? "" : rs.getString( "CONTENT11" ) );
                    //REVISION_DATE
                    Result2.put( "REVISION_DATE",rs.getString( "REVISION_DATE" ) == null ? "" : rs.getString( "REVISION_DATE" ) );
                    //REVISION_DATE1
                    Result2.put( "REVISION_DATE1",rs.getString( "REVISION_DATE1" ) == null ? "" : rs.getString( "REVISION_DATE1" ) );
                    //MATERIAL_ID
                    Result2.put( "MATERIAL_ID",rs.getString( "MATERIAL_ID" ) == null ? "" : rs.getString( "MATERIAL_ID" ) );
                    //CAT_PART_NO
                    Result2.put( "CAT_PART_NO",rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ) );
                    //CATALOG_ID
                    Result2.put( "CATALOG_ID",rs.getString( "CATALOG_ID" ) == null ? "" : rs.getString( "CATALOG_ID" ) );
                    //MFG_DESC
                    Result2.put( "MFG_DESC",rs.getString( "MFG_DESC" ) == null ? "" : rs.getString( "MFG_DESC" ) );
                    //TRADE_NAME
                    Result2.put( "TRADE_NAME",rs.getString( "TRADE_NAME" ) == null ? "" : rs.getString( "TRADE_NAME" ) );
                    //FACILITY_ID
                    Result2.put( "FACILITY_ID",rs.getString( "FACILITY_ID" ) == null ? "" : rs.getString( "FACILITY_ID" ) );
										//REVIEWED_BY1
										Result2.put( "REVIEWED_BY1",rs.getString( "REVIEWED_BY1" ) == null ? "" : rs.getString( "REVIEWED_BY1" ) );
										//REVIEW_DATE1
										Result2.put( "REVIEW_DATE1",rs.getString( "REVIEW_DATE1" ) == null ? "" : rs.getString( "REVIEW_DATE1" ) );

                    ResultV.addElement(Result2);

                    numRecsTest += 1;
                }
            }

            if (ResultV.size() > 0)
            {
            String Sizeorrecords = ""+ResultV.size();

            Result1.put("MATERIAL_IDS",ResultV);
            Result1.put("SIZE",Sizeorrecords);
            ResultF.addElement(Result1);
            }

            rs.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }

        /*String query1 = "";
        query1 = "select  distinct m.on_line,m.content,replace(m.content,'https://www.tcmis.com/','') content11, m.REVISION_DATE,to_char(m.REVISION_DATE,'mm/dd/yyyy') revision_date1,a2.MATERIAL_ID, a2.FAC_ITEM_ID cat_part_no,' ' catalog_id,  ";
        query1 = query1 + "a2.MFG_DESC, a2.MSDS_ON_LINE, a2.TRADE_NAME, ' ' FACILITY_ID from  ";
        query1 = query1 + "noncatalog_material_view a2,msds m where ";
        query1 = query1 + "a2.MATERIAL_ID = m.material_id ";
        query1 = query1 + "and m.on_line = 'Y' ";
		if ("Yes".equalsIgnoreCase(onlymostrecent))
		{
		  query1=query1 + "and m.revision_date=fx_msds_most_recent_date(a2.MATERIAL_ID) ";
		}
        query1 = query1 + "order by material_id,revision_date desc ";

        numRecsTest = 0 ;
        Result2 = new Hashtable();
        try
        {
            dbrs=db.doQuery( query1 );
            rs12=dbrs.getResultSet();
            while (rs12.next())
            {
                testTotal += 1;
                if (numRecsTest != 0)
                {
                    if (testfac.equalsIgnoreCase((rs12.getString("material_id").toString())))
                    {
                        Result2 = new Hashtable();
                        //CONTENT
                        Result2.put( "CONTENT",rs12.getString( "CONTENT" ) == null ? "" : rs12.getString( "CONTENT" ) );
                        //CONTENT11
                        Result2.put( "CONTENT11",rs12.getString( "CONTENT11" ) == null ? "" : rs12.getString( "CONTENT11" ) );
                        //REVISION_DATE
                        Result2.put( "REVISION_DATE",rs12.getString( "REVISION_DATE" ) == null ? "" : rs12.getString( "REVISION_DATE" ) );
                        //REVISION_DATE1
                        Result2.put( "REVISION_DATE1",rs12.getString( "REVISION_DATE1" ) == null ? "" : rs12.getString( "REVISION_DATE1" ) );
                        //MATERIAL_ID
                        Result2.put( "MATERIAL_ID",rs12.getString( "MATERIAL_ID" ) == null ? "" : rs12.getString( "MATERIAL_ID" ) );
                        //CAT_PART_NO
                        Result2.put( "CAT_PART_NO",rs12.getString( "CAT_PART_NO" ) == null ? "" : rs12.getString( "CAT_PART_NO" ) );
                        //CATALOG_ID
                        Result2.put( "CATALOG_ID",rs12.getString( "CATALOG_ID" ) == null ? "" : rs12.getString( "CATALOG_ID" ) );
                        //MFG_DESC
                        Result2.put( "MFG_DESC",rs12.getString( "MFG_DESC" ) == null ? "" : rs12.getString( "MFG_DESC" ) );
                        //TRADE_NAME
                        Result2.put( "TRADE_NAME",rs12.getString( "TRADE_NAME" ) == null ? "" : rs12.getString( "TRADE_NAME" ) );
                        //FACILITY_ID
                        Result2.put( "FACILITY_ID",rs12.getString( "FACILITY_ID" ) == null ? "" : rs12.getString( "FACILITY_ID" ) );

                        ResultV.addElement(Result2);

                        numRecsTest += 1;
                    }
                    else
                    {
                        String Sizeorrecords = ""+ResultV.size();
                        Result1.put("MATERIAL_IDS",ResultV);
                        Result1.put("SIZE",Sizeorrecords);
                        ResultF.addElement(Result1);

                        Result1 = new Hashtable();
                        Result2 = new Hashtable();
                        ResultV = new Vector();

                        testfac = (rs12.getString("material_id"));
                        //CONTENT
                        Result2.put( "CONTENT",rs12.getString( "CONTENT" ) == null ? "" : rs12.getString( "CONTENT" ) );
                        //CONTENT11
                        Result2.put( "CONTENT11",rs12.getString( "CONTENT11" ) == null ? "" : rs12.getString( "CONTENT11" ) );
                        //REVISION_DATE
                        Result2.put( "REVISION_DATE",rs12.getString( "REVISION_DATE" ) == null ? "" : rs12.getString( "REVISION_DATE" ) );
                        //REVISION_DATE1
                        Result2.put( "REVISION_DATE1",rs12.getString( "REVISION_DATE1" ) == null ? "" : rs12.getString( "REVISION_DATE1" ) );
                        //MATERIAL_ID
                        Result2.put( "MATERIAL_ID",rs12.getString( "MATERIAL_ID" ) == null ? "" : rs12.getString( "MATERIAL_ID" ) );
                        //CAT_PART_NO
                        Result2.put( "CAT_PART_NO",rs12.getString( "CAT_PART_NO" ) == null ? "" : rs12.getString( "CAT_PART_NO" ) );
                        //CATALOG_ID
                        Result2.put( "CATALOG_ID",rs12.getString( "CATALOG_ID" ) == null ? "" : rs12.getString( "CATALOG_ID" ) );
                        //MFG_DESC
                        Result2.put( "MFG_DESC",rs12.getString( "MFG_DESC" ) == null ? "" : rs12.getString( "MFG_DESC" ) );
                        //TRADE_NAME
                        Result2.put( "TRADE_NAME",rs12.getString( "TRADE_NAME" ) == null ? "" : rs12.getString( "TRADE_NAME" ) );
                        //FACILITY_ID
                        Result2.put( "FACILITY_ID",rs12.getString( "FACILITY_ID" ) == null ? "" : rs12.getString( "FACILITY_ID" ) );

                        ResultV.addElement(Result2);

                        numRecsTest += 1;
                    }
                }
                else
                {
                    testfac = (rs12.getString("material_id"));

                    //CONTENT
                    Result2.put( "CONTENT",rs12.getString( "CONTENT" ) == null ? "" : rs12.getString( "CONTENT" ) );
                    //CONTENT11
                    Result2.put( "CONTENT11",rs12.getString( "CONTENT11" ) == null ? "" : rs12.getString( "CONTENT11" ) );
                    //REVISION_DATE
                    Result2.put( "REVISION_DATE",rs12.getString( "REVISION_DATE" ) == null ? "" : rs12.getString( "REVISION_DATE" ) );
                    //REVISION_DATE1
                    Result2.put( "REVISION_DATE1",rs12.getString( "REVISION_DATE1" ) == null ? "" : rs12.getString( "REVISION_DATE1" ) );
                    //MATERIAL_ID
                    Result2.put( "MATERIAL_ID",rs12.getString( "MATERIAL_ID" ) == null ? "" : rs12.getString( "MATERIAL_ID" ) );
                    //CAT_PART_NO
                    Result2.put( "CAT_PART_NO",rs12.getString( "CAT_PART_NO" ) == null ? "" : rs12.getString( "CAT_PART_NO" ) );
                    //CATALOG_ID
                    Result2.put( "CATALOG_ID",rs12.getString( "CATALOG_ID" ) == null ? "" : rs12.getString( "CATALOG_ID" ) );
                    //MFG_DESC
                    Result2.put( "MFG_DESC",rs12.getString( "MFG_DESC" ) == null ? "" : rs12.getString( "MFG_DESC" ) );
                    //TRADE_NAME
                    Result2.put( "TRADE_NAME",rs12.getString( "TRADE_NAME" ) == null ? "" : rs12.getString( "TRADE_NAME" ) );
                    //FACILITY_ID
                    Result2.put( "FACILITY_ID",rs12.getString( "FACILITY_ID" ) == null ? "" : rs12.getString( "FACILITY_ID" ) );

                    ResultV.addElement(Result2);

                    numRecsTest += 1;
                }
            }

            if (ResultV.size() > 0)
            {
            String Sizeorrecords = ""+ResultV.size();

            Result1.put("MATERIAL_IDS",ResultV);
            Result1.put("SIZE",Sizeorrecords);
            ResultF.addElement(Result1);
            }

            rs12.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }*/

      msdslog.info("Done: Total Records: "+testTotal+" Vector Size  "+ResultF.size());
	    PrintWriter out=response.getWriter();
	    response.setContentType( "text/html" );
        out.println(buildHTML(ResultF,client));
    }

    public StringBuffer buildHTML(Vector ResultFinal,String clientname) throws IOException
    {
        StringBuffer Msg1 = new StringBuffer();
        Hashtable Final = new Hashtable();
        Hashtable Finaop = new Hashtable();
        Vector ResultV1 = new Vector();

        String sizeof = "";
        String Content = "";
        Random rand = new Random();
        int tmpReq = rand.nextInt();
        Integer tmpReqNum = new Integer(tmpReq);

        Msg1.append("<HTML><HEAD><TITLE>TEST</TITLE></HEAD>\n");
        Msg1.append("<BODY>\n");
        Msg1.append("<FONT FACE=\"Arial\" SIZE=\"3\">\n");
        Msg1.append("For the Client :"+clientname+"\n");
        Msg1.append("<BR><BR>QC Check of MSDS Images\n");
        Msg1.append("<BR><BR>The following Material Ids have a problem with the MSDS.\n");
        Msg1.append("<BR><BR>Total Number of Material Ids Checked for MSDS: "+ResultFinal.size()+"\n");

        Msg1.append("<BR><BR><TABLE width =\"100%\" BORDER=1>");

        Msg1.append("<TR>");
        Msg1.append("<TD><B>MATERIAL ID</B></TD><TD><B>REVISION DATE</B></TD><TD><B>REVIEWED BY</B></TD><TD><B>REVIEW DATE</B></TD><TD><B>ACTIVE</B></TD><TD><B>CONTENT</B></TD><TD><B>LATEST?</B></TD><TD><B>ONLY REVISION DATE</B></TD>");
        Msg1.append("</TR>\n");

        HttpURLConnection huc = null;

        for ( int j=0; j < ResultFinal.size(); j++ )
        {
          Final= ( Hashtable ) ResultFinal.elementAt( j );
          ResultV1= ( Vector ) Final.get( "MATERIAL_IDS" );
          sizeof= ( String ) Final.get( "SIZE" );

          for ( int i=0; i < ResultV1.size(); i++ )
          {
            Finaop= ( Hashtable ) ResultV1.elementAt( i );
            Content=Finaop.get( "CONTENT" ).toString().trim();

            try
            {
                String testurl2="";
                if ( Content.startsWith( "https://" ) )
                {
                  String testurl1=Content.substring( 5 );
                  testurl2="http" + testurl1;
                }

                URL url=new URL( testurl2 );
                huc= ( HttpURLConnection ) url.openConnection();
                int isss;
                isss=huc.getResponseCode();

				if ( isss == 404 )
				{
				  msdslog.info( "Not Found Content, " + Finaop.get( "MATERIAL_ID" ) + ", " + Finaop.get( "REVISION_DATE" ) + " , " + Content);
				  Msg1.append( "<TR><TD ALIGN=\"LEFT\">" );
				  Msg1.append( Finaop.get( "MATERIAL_ID" ) );
				  Msg1.append( "</TD><TD ALIGN=\"LEFT\">" );
				  Msg1.append( Finaop.get( "REVISION_DATE1" ) );
				  Msg1.append( "</TD>" );
					Msg1.append( "<TD ALIGN=\"LEFT\">" );
					Msg1.append( Finaop.get( "REVIEWED_BY1" ) );
					Msg1.append( "</TD>" );
					Msg1.append( "<TD ALIGN=\"LEFT\">" );
					Msg1.append( Finaop.get( "REVIEW_DATE1" ) );
					Msg1.append( "</TD><TD>" );
				  Msg1.append( Finaop.get( "CATALOG_ID" ) );
				  Msg1.append( "</TD><TD ALIGN=\"LEFT\">" );
				  Msg1.append( "<A HREF=\"" + Finaop.get( "CONTENT" ) + "\">" + Finaop.get( "CONTENT" ) + "</A>" );

				  Msg1.append( "</TD>" );
				  if ( i == 0 )
				  {
					Msg1.append( "<TD>YES</TD>" );
				  }
				  else
				  {
					Msg1.append( "<TD>&nbsp;</TD>" );
				  }

				  if ( ( sizeof.equalsIgnoreCase( "1" ) ) && ( i == 0 ) )
				  {
					Msg1.append( "<TD>YES</TD>" );
				  }
				  else
				  {
					Msg1.append( "<TD>&nbsp;</TD>" );
				  }

				  Msg1.append( "</TR>  \n" );

				}
            }
            catch ( Exception e )
            {
              msdslog.info( "Exception Msg-"+e.getMessage()+", " + Finaop.get( "MATERIAL_ID" ) + ", "+Finaop.get( "REVISION_DATE" )+" , " + Content + " , "+e.getMessage() );
              //huc.disconnect();
              /*Msg1.append( "<TR><TD ALIGN=\"LEFT\">" );
              Msg1.append( Finaop.get( "MATERIAL_ID" ) );
              Msg1.append( "</TD><TD ALIGN=\"LEFT\">" );
              Msg1.append( Finaop.get( "REVISION_DATE1" ) );
							Msg1.append( "</TD>" );
							Msg1.append( "<TD ALIGN=\"LEFT\">" );
							Msg1.append( Finaop.get( "REVIEWED_BY1" ) );
							Msg1.append( "</TD>" );
							Msg1.append( "<TD ALIGN=\"LEFT\">" );
							Msg1.append( Finaop.get( "REVIEW_DATE1" ) );
							Msg1.append( "</TD><TD>" );
              Msg1.append( Finaop.get( "CATALOG_ID" ) );
              Msg1.append( "</TD><TD ALIGN=\"LEFT\">" );
              Msg1.append( "<A HREF=\"" + Finaop.get( "CONTENT" ) + "\">" + Finaop.get( "CONTENT" ) + "</A>" );

              Msg1.append( "</TD>" );
              if ( i == 0 )
              {
                Msg1.append( "<TD>YES</TD>" );
              }
              else
              {
                Msg1.append( "<TD>&nbsp;</TD>" );
              }

              if ( ( sizeof.equalsIgnoreCase( "1" ) ) && ( i == 0 ) )
              {
                Msg1.append( "<TD>YES</TD>" );
              }
              else
              {
                Msg1.append( "<TD>&nbsp;</TD>" );
              }

              Msg1.append( "</TR>  \n" );*/
            }
            finally
            {
              huc.disconnect();
            }
          }
        }

        String contents1="" + Msg1;
        String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();
        String file=writefilepath + clientname+ "checklink" + tmpReqNum.toString() + ".xls";
		String temprpturl =radian.web.tcmisResourceLoader.getreporturl();
        String url= temprpturl + clientname+ "checklink"+ tmpReqNum.toString() + ".xls";

        try
        {
          File outFile=new File( file );
          FileOutputStream DBO=new FileOutputStream( outFile );
          byte outbuf[];
          outbuf=new byte[contents1.length()];
          outbuf=contents1.getBytes();
          DBO.write( outbuf );
          DBO.close();
        }
        catch ( IOException ioe )
        {
          ioe.printStackTrace();
		  url = "";
        }

	StringBuffer Msgbb = new StringBuffer();
	Msgbb.append(radian.web.HTMLHelpObj.labelredirection(url));
  if (url != null || url.length() > 0)
	{
    String reportUrl="https://www.tcmis.com/"+url;
     radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"MSDS Check Results","MSDS Check Results, please go to the URL below to look at the results.\n\n\n"+reportUrl+"",11862,true);
     radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"MSDS Check Results","MSDS Check Results, please go to the URL below to look at the results.\n\n\n"+reportUrl+"",6375,true);
    }


  return Msgbb;
    }
}
