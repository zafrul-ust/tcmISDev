package radian.web.barcode;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

/**
 *
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 03-11-03
 * Generating 10 labels for each MR instead of 5
 * 01-09-04 - Not Double closing the DB object
 * 03-02-04 - Showing Application and delivery point description.
 * TODO- Need to get the inventory group from issue
 */
public class BoxLabelPage
{
    private TcmISDBModel db = null;

    public BoxLabelPage()
    {

    }

    public String GenerateBoxLabel( Vector VFromSession ) throws Exception,IOException
    {
      db=new RayTcmISDBModel( "Tcm_Ops" );

      String Query_Statement="";
      DBResultSet dbrs=null;
      ResultSet rs=null;
      Hashtable dataFromSession=new Hashtable();
      Vector VMr_Number=new Vector();
      Vector VLine_Number=new Vector();
      BoxLabelGenerator obj=new BoxLabelGenerator();
      StringBuffer MsgSB=new StringBuffer();
      Vector dataV1=new Vector();

      Random rand=new Random();
      int tmpReq=rand.nextInt();
      Integer tmpReqNum=new Integer( tmpReq );

      String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
      String url=wwwHome + "labels/boxlabel" + tmpReqNum.toString() + ".pdf";

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

      try
      {
        for ( int i=0; i < VFromSession.size(); i++ )
        {
          dataFromSession=new Hashtable();
          VMr_Number=new Vector();
          VLine_Number=new Vector();

          dataFromSession= ( Hashtable ) VFromSession.elementAt( i );
          VMr_Number= ( Vector ) dataFromSession.get( "mr_number" );
          VLine_Number= ( Vector ) dataFromSession.get( "line_number" );

          if ( VMr_Number.size() == 0 )
          {
            //db.close();
            return buildERROR( "No Records Found" );
          }

          for ( int j=0; j < VMr_Number.size(); j++ )
          {
            Query_Statement="Select LAST_NAME, FIRST_NAME, END_USER, FACILITY_NAME, APPLICATION, DELIVERY_POINT, PR_NUMBER, LINE_ITEM, COMPANY_ID, CAT_PART_NO, APPLICATION_DESC, DELIVERY_POINT_DESC, " +
            "INVENTORY_GROUP, REQUESTOR_NAME, REQUESTOR_PHONE,REQUESTOR_FAX, REQUESTOR_EMAIL, PAYMENT_TERMS," +
            "PO_NUMBER, SHIP_TO_ADDRESS_LINE_1, SHIP_TO_ADDRESS_LINE_2,SHIP_TO_ADDRESS_LINE_3, SHIP_TO_ADDRESS_LINE_4, SHIP_TO_ADDRESS_LINE_5," +
            "SHIP_TO_PHONE, SHIP_TO_FAX, SHIP_FROM_ADDRESS_LINE_1,SHIP_FROM_ADDRESS_LINE_2, SHIP_FROM_ADDRESS_LINE_3, SHIP_FROM_ADDRESS_LINE_4, " +
            "SHIP_FROM_ADDRESS_LINE_5, SHIP_FROM_PHONE, SHIP_FROM_FAX" +
            " from box_label_view where PR_NUMBER = '" + ( String ) VMr_Number.elementAt( j ) + "' and LINE_ITEM = '" + ( String ) VLine_Number.elementAt( j ) + "'" +
            " and exists (select null from issue i where i.pr_number = pr_number and i.line_item = line_item and i.ship_confirm_date is null)";

            dbrs=db.doQuery( Query_Statement );
            rs=dbrs.getResultSet();
            Hashtable data=new Hashtable();

            try
            {
              while ( rs.next() )
              {
                String firstname=rs.getString( "FIRST_NAME" ) == null ? " " : rs.getString( "FIRST_NAME" );
                String lastname=rs.getString( "LAST_NAME" ) == null ? " " : rs.getString( "LAST_NAME" );
                String fullname=firstname + "  " + lastname;

                String end_user=rs.getString( "END_USER" ) == null ? "" : rs.getString( "END_USER" ).toString();
                String facility_name=rs.getString( "FACILITY_NAME" ) == null ? "" : rs.getString( "FACILITY_NAME" ).toString();
                //String application=rs.getString( "APPLICATION" ) == null ? "" : rs.getString( "APPLICATION" ).toString();
                //String delivery_point=rs.getString( "DELIVERY_POINT" ) == null ? "" : rs.getString( "DELIVERY_POINT" ).toString();
				String application_desc=rs.getString( "APPLICATION_DESC" ) == null ? "" : rs.getString( "APPLICATION_DESC" ).toString();
				String delivery_point_desc=rs.getString( "DELIVERY_POINT_DESC" ) == null ? "" : rs.getString( "DELIVERY_POINT_DESC" ).toString();

                String pr_number=rs.getString( "PR_NUMBER" ) == null ? "" : rs.getString( "PR_NUMBER" ).toString();
                String line_item=rs.getString( "LINE_ITEM" ) == null ? "" : rs.getString( "LINE_ITEM" ).toString();
                String cat_part_no=rs.getString( "CAT_PART_NO" ) == null ? "" : rs.getString( "CAT_PART_NO" ).toString();
                String inventoryGroup=rs.getString( "INVENTORY_GROUP" ) == null ? "" : rs.getString( "INVENTORY_GROUP" ).toString();
                String requestorName=rs.getString( "REQUESTOR_NAME" ) == null ? "" : rs.getString( "REQUESTOR_NAME" ).toString();
                String requestorPhone=rs.getString( "REQUESTOR_PHONE" ) == null ? "" : rs.getString( "REQUESTOR_PHONE" ).toString();
                String requestorFax=rs.getString( "REQUESTOR_FAX" ) == null ? "" : rs.getString( "REQUESTOR_FAX" ).toString();
                String requestorEmail=rs.getString( "REQUESTOR_EMAIL" ) == null ? "" : rs.getString( "REQUESTOR_EMAIL" ).toString();
                String paymentTerms=rs.getString( "PAYMENT_TERMS" ) == null ? "" : rs.getString( "PAYMENT_TERMS" ).toString();
                String poNumber=rs.getString( "PO_NUMBER" ) == null ? "" : rs.getString( "PO_NUMBER" ).toString();
                String shipToAddressLine1=rs.getString( "SHIP_TO_ADDRESS_LINE_1" ) == null ? "" : rs.getString( "SHIP_TO_ADDRESS_LINE_1" ).toString();
                String shipToAddressLine2=rs.getString( "SHIP_TO_ADDRESS_LINE_2" ) == null ? "" : rs.getString( "SHIP_TO_ADDRESS_LINE_2" ).toString();
                String shipToAddressLine3=rs.getString( "SHIP_TO_ADDRESS_LINE_3" ) == null ? "" : rs.getString( "SHIP_TO_ADDRESS_LINE_3" ).toString();
                String shipToAddressLine4=rs.getString( "SHIP_TO_ADDRESS_LINE_4" ) == null ? "" : rs.getString( "SHIP_TO_ADDRESS_LINE_4" ).toString();
                String shipToAddressLine5=rs.getString( "SHIP_TO_ADDRESS_LINE_5" ) == null ? "" : rs.getString( "SHIP_TO_ADDRESS_LINE_5" ).toString();
                String shipToPhone=rs.getString( "SHIP_TO_PHONE" ) == null ? "" : rs.getString( "SHIP_TO_PHONE" ).toString();
                String shipToFax=rs.getString( "SHIP_TO_FAX" ) == null ? "" : rs.getString( "SHIP_TO_FAX" ).toString();
                String shipFromAddressLine1=rs.getString( "SHIP_FROM_ADDRESS_LINE_1" ) == null ? "" : rs.getString( "SHIP_FROM_ADDRESS_LINE_1" ).toString();
                String shipFromAddressLine2=rs.getString( "SHIP_FROM_ADDRESS_LINE_2" ) == null ? "" : rs.getString( "SHIP_FROM_ADDRESS_LINE_2" ).toString();
                String shipFromAddressLine3=rs.getString( "SHIP_FROM_ADDRESS_LINE_3" ) == null ? "" : rs.getString( "SHIP_FROM_ADDRESS_LINE_3" ).toString();
                String shipFromAddressLine4=rs.getString( "SHIP_FROM_ADDRESS_LINE_4" ) == null ? "" : rs.getString( "SHIP_FROM_ADDRESS_LINE_4" ).toString();
                String shipFromAddressLine5=rs.getString( "SHIP_FROM_ADDRESS_LINE_5" ) == null ? "" : rs.getString( "SHIP_FROM_ADDRESS_LINE_5" ).toString();
                String shipFromPhone=rs.getString( "SHIP_FROM_PHONE" ) == null ? "" : rs.getString( "SHIP_FROM_PHONE" ).toString();
                String shipFromFax=rs.getString( "SHIP_FROM_FAX" ) == null ? "" : rs.getString( "SHIP_FROM_FAX" ).toString();

                for ( int ii=0; ii < 10; ii++ )
                {
                  data.put( "DETAIL_1",fullname );
                  data.put( "DETAIL_0"," " );
                  data.put( "DETAIL_2",end_user );
                  data.put( "DETAIL_3",""+delivery_point_desc+"" );
                  data.put( "DETAIL_4","" + pr_number + "-" + line_item + "" );
                  data.put( "DETAIL_5",facility_name );
                  data.put( "DETAIL_6",""+application_desc+"" );
                  data.put( "DETAIL_7",cat_part_no );
                  data.put( "INVENTORY_GROUP",inventoryGroup );
                  data.put( "REQUESTOR_NAME",requestorName );
                  data.put( "REQUESTOR_PHONE",requestorPhone );
                  data.put( "REQUESTOR_FAX",requestorFax );
                  data.put( "REQUESTOR_EMAIL",requestorEmail );
                  data.put( "PAYMENT_TERMS",paymentTerms );
                  data.put( "PO_NUMBER",poNumber );
                  data.put( "SHIP_TO_ADDRESS_LINE_1",shipToAddressLine1 );
                  data.put( "SHIP_TO_ADDRESS_LINE_2",shipToAddressLine2 );
                  data.put( "SHIP_TO_ADDRESS_LINE_3",shipToAddressLine3 );
                  data.put( "SHIP_TO_ADDRESS_LINE_4",shipToAddressLine4 );
                  data.put( "SHIP_TO_ADDRESS_LINE_5",shipToAddressLine5 );
                  data.put( "SHIP_TO_PHONE",shipToPhone );
                  data.put( "SHIP_TO_FAX",shipToFax );
                  data.put( "SHIP_FROM_ADDRESS_LINE_1",shipFromAddressLine1 );
                  data.put( "SHIP_FROM_ADDRESS_LINE_2",shipFromAddressLine2 );
                  data.put( "SHIP_FROM_ADDRESS_LINE_3",shipFromAddressLine3 );
                  data.put( "SHIP_FROM_ADDRESS_LINE_4",shipFromAddressLine4 );
                  data.put( "SHIP_FROM_ADDRESS_LINE_5",shipFromAddressLine5 );
                  data.put( "SHIP_FROM_PHONE",shipFromPhone );
                  data.put( "SHIP_FROM_FAX",shipFromFax );
                  String shipToAddress = ""+shipToAddressLine1+"\\&"+shipToAddressLine2+"\\&"+shipToAddressLine3+"\\&"+shipToAddressLine4+"\\&"+shipToAddressLine5+"";
                  if (shipToPhone.length() >0)
                  {
                      shipToAddress += "\\&Tel:"+shipToPhone+"";
                  }
                  if (shipToFax.length() >0)
                  {
                     shipToAddress += "\\&Fax:"+shipToFax+"";
                  }
                  data.put( "SHIP_TO_ADDRESS",shipToAddress );
                  String shipFromAddress =""+shipFromAddressLine1+"\\&"+shipFromAddressLine2+"\\&"+shipFromAddressLine3+"\\&"+shipFromAddressLine4+"\\&"+shipFromAddressLine5+"";
                  if (shipFromPhone.length() >0)
                  {
                      shipFromAddress += "\\&Tel:"+shipFromPhone+"";
                  }
                  if (shipFromFax.length() >0)
                  {
                     shipFromAddress += "\\&Fax:"+shipFromFax+"";
                  }
                  data.put( "SHIP_FROM_ADDRESS",shipFromAddress);            
                  dataV1.addElement( data );
                }
              }
            }
            catch ( Exception e )
            {
              e.printStackTrace();
              return buildERROR( " "+e.getMessage()+" " );
            }
            finally
            {
               if (dbrs != null)  { dbrs.close(); }
            }
          } //End of Second For
        } //End of first for
      } //End Try
      catch ( Exception e )
      {
        e.printStackTrace();
        return buildERROR( " "+e.getMessage()+" " );
      }
      finally
      {
        db.close();
      }

      try
      {
        if (dataV1.size() > 0)
        obj.buildTest( dataV1,tmpReqNum.toString() );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        return buildERROR( " "+e.getMessage()+" " );
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