package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.dropShipReceivingTables;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 07-16-03 - Made the response content type as HTML so that it will not print the HTML in netscape
 * 10-06-03 - Showing Customer PO in the results
 * 10-24-03 - Getting DROPSHIP_DATA every time somebody access the page. So that people at the hub can go from one client to another
 * 05-04-04 - Giving more options to search
 * 07-16-04 - Showing a summary of what was received
 * 11-02-04 - Sorting the ship to drop down
 * 07-18-05 - Giving the ability to enter receipt notes
 * 06-22-05 - Showing the help link only if a person is logged in
 */

public class dropShipReceiving
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private dropShipReceivingTables hubObj=null;
  private PrintWriter out=null;
  private boolean completeSuccess=true;
  private boolean noneToUpd=true;
  private String Receiving_Servlet="";
  private String Label_Servlet="";
  private String paper_size="";
	private String receivedReceipts="";
  private boolean allowupdate;
  private boolean intcmIsApplication = false;

  public void setupdateStatus( boolean id )
  {
    this.allowupdate=id;
  }

  private boolean getupdateStatus() throws Exception
  {
    return this.allowupdate;
  }

  public dropShipReceiving( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }


  //Process the HTTP Get request
  public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    doPost( request,response );
  }

  //Process the HTTP Post request
  public void doPost( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
  {
    hubObj=new dropShipReceivingTables( db );
    out=response.getWriter();
    response.setContentType( "text/html" );
    HttpSession session=request.getSession();

    PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    intcmIsApplication = false;
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();

    Receiving_Servlet=bundle.getString( "DROPRECEIVING_SERVLET" );
    Label_Servlet=bundle.getString( "RELABELING_SERVLET" );

    String User_Search=null;
    String User_Action=null;
    String SubUser_Action=null;
    String User_Session="";
    String User_Sort="";
    String generate_labels="";
    Hashtable initialData=null;

    generate_labels=request.getParameter( "generate_labels" );
    if ( generate_labels == null )
    {
      generate_labels="No";
    }
    paper_size=request.getParameter( "paperSize" );
    if ( paper_size == null )
    {
      paper_size="31";
    }
    User_Session=request.getParameter( "session" );
    if ( User_Session == null )
    {
      User_Session="New";
    }
    User_Action=request.getParameter( "UserAction" );
    if ( User_Action == null )
    {
      User_Action="New";
    }
    User_Search=request.getParameter( "SearchField" );
    if ( User_Search == null )
    {
      User_Search="";
    }
    if ( User_Search.trim().length() < 1 )
    {
      User_Search="";
    }
    User_Sort=request.getParameter( "SortBy" );
    if ( User_Sort == null )
    {
      User_Sort="1";
    }

    String numOldDays=BothHelpObjs.makeBlankFromNull( ( String ) request.getParameter( "nodaysold" ) );
    if ( numOldDays == null )
    {
      numOldDays="30";
    }
    if ( numOldDays.trim().length() < 1 )
    {
      numOldDays="30";
    }

    String dropshiolocation = request.getParameter( "HubName" );
    if ( dropshiolocation == null )
    {
      dropshiolocation="";
    }

	String searchlike = request.getParameter( "searchlike" );
	if ( searchlike == null )
	{
	  searchlike="a.RADIAN_PO";
	}
	searchlike = searchlike.trim();

	String searchfor = request.getParameter( "searchfor" );
	if ( searchfor == null )
	{
	  searchfor="";
	}
	searchfor = searchfor.trim();

    try
    {
      String initialDataLoaded= session.getAttribute( "INITIAL_DROPSHIP_DATA_LOADED" ) == null ? "" : session.getAttribute( "INITIAL_DROPSHIP_DATA_LOADED" ).toString();
      if ( initialDataLoaded.equalsIgnoreCase( "Yes" ) )
      {
        initialData= ( Hashtable ) session.getAttribute( "INITIAL_DROPSHIP_DATA" );
      }
      else
      {
        initialData=hubObj.createDropdownlist( personnelid, this.getupdateStatus() );
        session.setAttribute( "INITIAL_DROPSHIP_DATA",initialData );
        //session.setAttribute( "INITIAL_DROPSHIP_DATA_LOADED","Yes" );
      }

      //System.out.println("User_Session     "+User_Session+"       User_Action   "+User_Action+"    dropshiolocation    "+dropshiolocation+"");
      if ( ( User_Session.equalsIgnoreCase( "New" ) ) && ( User_Action.equalsIgnoreCase( "New" ) ) )
      {
          buildHeader( null,"",User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session ) ;
          out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
          out.close();
      }
      else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "createxls" ) ) )
      {
        Vector retrndata= ( Vector ) session.getAttribute( "DATA" );
        out.println( buildxlsDetails( retrndata,personnelid ) );
        retrndata=null;
      }
      else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "Search" ) ) )
      {
        Vector openorder_data=new Vector();
        openorder_data=hubObj.getAllopenOrder( dropshiolocation,User_Search,User_Sort,numOldDays,searchlike,searchfor );

        Hashtable sum= ( Hashtable ) ( openorder_data.elementAt( 0 ) );
        int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
        if ( 0 == count )
        {
          buildHeader( null,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session );
          out.println( radian.web.HTMLHelpObj.printHTMLNoData( "No Items Found" ) );
          out.close();
          //clean up
          openorder_data=null;
        }
        else
        {
          session.setAttribute( "DATA",openorder_data );
          buildHeader( openorder_data,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session ) ;
          buildDetails( openorder_data,"" );
          out.close();
          //clean up
          openorder_data=null;
        } //when there are open orders for hub
      }
      else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "Update" ) ) )
      {
        Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
        Vector synch_data=SynchServerData( request,retrn_data );
        retrn_data=null;

        SubUser_Action=request.getParameter( "SubUserAction" );
        if ( SubUser_Action == null )
        {
          SubUser_Action="JSError2";
        }
        if ( SubUser_Action.equalsIgnoreCase( "JSError2" ) )
        {
          session.setAttribute( "DATA",synch_data );
          buildHeader( synch_data,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session );
          out.println( radian.web.HTMLHelpObj.printJavaScriptError() );
          buildDetails( synch_data,"" );

          out.close();
          //clean up
          synch_data=null;
        }
        else if ( SubUser_Action.equalsIgnoreCase( "DuplLine" ) )
        {
          String dupl_line=BothHelpObjs.makeBlankFromNull( request.getParameter( "DuplLineNumber" ) );
          Vector new_data=createDuplicate( dupl_line,synch_data );
          session.setAttribute( "DATA",new_data );
          buildHeader( synch_data,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session );
          buildDetails( new_data,"" );
          out.close();
          //clean up
          synch_data=null;
          new_data=null;
        }
        else if ( SubUser_Action.equalsIgnoreCase( "UpdPage" ) )
        {
          session.setAttribute( "DATA",synch_data );

          Hashtable updateresults=UpdateDetails( synch_data,dropshiolocation,personnelid );
          Boolean result= ( Boolean ) updateresults.get( "RESULT" );
          Vector errordata= ( Vector ) updateresults.get( "ERRORDATA" );
          Vector errMsgs=hubObj.getErrMsgs();

          session.setAttribute( "DATA",errordata );
          //System.out.println( "*** The result ****" + completeSuccess );

          boolean resulttotest=result.booleanValue();

          if ( false == resulttotest )
          {
            buildHeader( errordata,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session );
            if ( true == noneToUpd )
            {
              out.println( radian.web.HTMLHelpObj.printMessageinTable( "No Item was Choosen for Receiving" ) );
              buildDetails( errordata,SubUser_Action );
            }
            else
            {
              out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occurred.<BR>Please Check Data and try Again." ) );
              buildDetails( errordata,SubUser_Action );
            }
            out.close();
          }
          else
          {
            Vector vList=hubObj.getLotSeqList();
            int size=vList.size();

			if ( size > 0 )
			{
			  session.setAttribute( "REC_LABEL_SEQUENCE_NUMBERS",vList );
			}

            if ( true == completeSuccess )
            {
              //System.out.println( "*** Errodata size  " + errordata.size() + "   **** " );
              if ( size > 0 )
              {
                buildHeader( errordata,dropshiolocation,User_Search,User_Sort,"GENERATE_LABELS",initialData,numOldDays,searchlike,searchfor,session );
              }
              else
              {
                buildHeader( errordata,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session );
              }

              out.println( radian.web.HTMLHelpObj.printMessageinTable( "All Your Selections Were Successfully Updated" ) );
              buildDetails( errordata,SubUser_Action );

              out.close();
            }
            else
            {
              Hashtable recsum1=new Hashtable();
              recsum1.put( "TOTAL_NUMBER",new Integer( errordata.size() - 1 ) );
              errordata.setElementAt( recsum1,0 );
              if  ( size > 0 )
              {
                buildHeader( errordata,dropshiolocation,User_Search,User_Sort,"GENERATE_LABELS",initialData,numOldDays,searchlike,searchfor,session );
              }
              else
              {
                buildHeader( errordata,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session );
              }

              out.println( radian.web.HTMLHelpObj.printHTMLPartialSuccess( errMsgs ) );
              buildDetails( errordata,SubUser_Action );

              out.close();
            }
          }

          synch_data=null;
          updateresults=null;
          errordata=null;
        }
        else
        {
          out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
        }
      }
      else if ( ( User_Session.equalsIgnoreCase( "Active" ) ) && ( User_Action.equalsIgnoreCase( "JSError1" ) ) )
      {
        Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
        Vector synch_data=SynchServerData( request,retrn_data );
        retrn_data=null;
        session.setAttribute( "DATA",synch_data );
        buildHeader( synch_data,dropshiolocation,User_Search,User_Sort,"",initialData,numOldDays,searchlike,searchfor,session );
        out.println( radian.web.HTMLHelpObj.printJavaScriptError() );
        buildDetails( synch_data,"" );

        out.close();
        synch_data=null;
      }
      else
      {
        out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
    }

    hubObj=null;
    return;
  }

  private Vector createDuplicate( String dupl_line,Vector in_data )
  {
    int line_to_dupl= ( new Integer( dupl_line ) ).intValue();
    int size=in_data.size();
    Vector new_data=new Vector();

    try
    {
      for ( int i=0; i < size; i++ )
      {
        if ( i == 0 )
        {
          Hashtable sum= ( Hashtable ) ( in_data.elementAt( i ) );
          int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
          sum.put( "TOTAL_NUMBER",new Integer( count + 1 ) );
          new_data.addElement( sum );
        }
        else
        {
          Hashtable temp= ( Hashtable ) ( in_data.elementAt( i ) );
          new_data.addElement( temp );
          if ( i == line_to_dupl )
          {
            Hashtable hDupl=new Hashtable();
            hDupl.put( "PURCHASE_ORDER",temp.get( "PURCHASE_ORDER" ) );
            hDupl.put( "PURCHASE_ORDER_LINE",temp.get( "PURCHASE_ORDER_LINE" ) );
            hDupl.put( "EXPCTD_DATE",temp.get( "EXPCTD_DATE" ) );
            hDupl.put( "SUPPLIER",temp.get( "SUPPLIER" ) );
            hDupl.put( "ITEM_ID",temp.get( "ITEM_ID" ) );
            hDupl.put( "ITEM_DESC",temp.get( "ITEM_DESC" ) );
            hDupl.put( "QTY_OPEN",temp.get( "QTY_OPEN" ) );
            hDupl.put( "TRANS_TYPE",temp.get( "TRANS_TYPE" ) );
            hDupl.put( "SPECIFICATION",temp.get( "SPECIFICATION" ) );
            hDupl.put( "PACKING",temp.get( "PACKING" ) );
            hDupl.put( "USERCHK","" );
            hDupl.put( "MFG_LOT","" );
            hDupl.put( "DATE_MFGD","" );
            hDupl.put( "DATE_RECIEVED",temp.get( "DATE_RECIEVED" ) );
            hDupl.put( "QTY_RECD","" );
            hDupl.put( "BIN_NAME",temp.get( "BIN_NAME" ) );
            hDupl.put( "STATUS_ID",temp.get( "STATUS_ID" ) );
            hDupl.put( "XFER_REQ_ID",temp.get( "XFER_REQ_ID" ) );
            hDupl.put( "EXPIRE_DATE","" );
            hDupl.put( "DATE_OF_SHIPMENT","" );
            hDupl.put( "RECEIPT_ID",temp.get( "RECEIPT_ID" ) );
            hDupl.put( "INDEFINITE_SHELF_LIFE",temp.get( "INDEFINITE_SHELF_LIFE" ) );
            hDupl.put( "LINE_STATUS",temp.get( "LINE_STATUS" ) );
            hDupl.put( "CAT_PART_NO",temp.get( "CAT_PART_NO" ) );
            hDupl.put( "COMPANY_ID",temp.get( "COMPANY_ID" ) );
            hDupl.put( "ACTUAL_SHIP_DATE",temp.get( "ACTUAL_SHIP_DATE" ) );
            hDupl.put( "CRITICAL",temp.get( "CRITICAL" ) );

            hDupl.put( "MR_NUMBER",temp.get( "MR_NUMBER" ) );
            hDupl.put( "MR_LINE_ITEM",temp.get( "MR_LINE_ITEM" ) );
            hDupl.put( "MR_QTY_OPEN",temp.get( "MR_QTY_OPEN" ) );
            hDupl.put( "REQUESTOR_NAME",temp.get( "REQUESTOR_NAME" ) );
            hDupl.put( "DELIVERY_POINT",temp.get( "DELIVERY_POINT" ) );
            hDupl.put( "BRANCH_PLANT",temp.get( "BRANCH_PLANT" ) );
			hDupl.put( "NOTES", "" );

            new_data.addElement( hDupl );
          } //end of last if
        } //end of else
      } //end of for
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }

    return new_data;
  }

  private Vector SynchServerData( HttpServletRequest request,Vector in_data )
  {
    Vector new_data=new Vector();
    Hashtable sum= ( Hashtable ) ( in_data.elementAt( 0 ) );
    int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
    new_data.addElement( sum );

    try
    {
      for ( int i=1; i < count + 1; i++ )
      {
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) ( in_data.elementAt( i ) );

        String bin_name="";
        bin_name=BothHelpObjs.makeBlankFromNull( request.getParameter( "selectElementBin" + i ) );
        hD.remove( "BIN_NAME" );
        hD.put( "BIN_NAME",bin_name );

        String check="";
        check=BothHelpObjs.makeBlankFromNull( request.getParameter( "row_chk" + i ) );
        hD.remove( "USERCHK" );
        hD.put( "USERCHK",check );

        String mfg_lot="";
        mfg_lot=BothHelpObjs.makeBlankFromNull( request.getParameter( "mfg_lot" + i ) );
        hD.remove( "MFG_LOT" );
        hD.put( "MFG_LOT",mfg_lot );

        String date_mfgd="";
        date_mfgd=BothHelpObjs.makeBlankFromNull( request.getParameter( "date_mfgd" + i ) );
        hD.remove( "DATE_MFGD" );
        hD.put( "DATE_MFGD",date_mfgd );

        String date_recieved="";
        date_recieved=BothHelpObjs.makeBlankFromNull( request.getParameter( "date_recieved" + i ) );
        hD.remove( "DATE_RECIEVED" );
        hD.put( "DATE_RECIEVED",date_recieved );

        String actshipDate="";
        actshipDate=BothHelpObjs.makeBlankFromNull( request.getParameter( "act_suppship_date" + i ) );
        hD.remove( "ACTUAL_SHIP_DATE" );
        hD.put( "ACTUAL_SHIP_DATE",actshipDate );

        String qty_recd="";
        qty_recd=BothHelpObjs.makeBlankFromNull( request.getParameter( "qty_recd" + i ) );
        hD.remove( "QTY_RECD" );
        hD.put( "QTY_RECD",qty_recd );

        String expiry_date="";
        expiry_date=BothHelpObjs.makeBlankFromNull( request.getParameter( "expiry_date" + i ) );

        if ( "Indefinite".equalsIgnoreCase( expiry_date ) )
        {
          expiry_date="01/01/3000";
        }
        hD.remove( "EXPIRE_DATE" );
        hD.put( "EXPIRE_DATE",expiry_date );

        String ship_date="";
        ship_date=BothHelpObjs.makeBlankFromNull( request.getParameter( "ship_date" + i ) );

        hD.remove( "DATE_OF_SHIPMENT" );
        hD.put( "DATE_OF_SHIPMENT",ship_date );

        String receipt_id="";
        receipt_id=BothHelpObjs.makeBlankFromNull( request.getParameter( "receipt_id" + i ) );
        hD.remove( "RECEIPT_ID" );
        hD.put( "RECEIPT_ID",receipt_id );

		String recnotes="";
		recnotes=BothHelpObjs.makeBlankFromNull( request.getParameter( "recnotes" + i ) );
		hD.remove( "NOTES" );
		hD.put( "NOTES",recnotes );

        new_data.addElement( hD );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
    }
    return new_data;
  }

  private Hashtable UpdateDetails( Vector data,String BranchPlant,String logonid )
  {
    boolean result=false;
    Hashtable updateresult=new Hashtable();
    Vector errordata=new Vector();

    try
    {
      Hashtable summary=new Hashtable();
      summary= ( Hashtable ) data.elementAt( 0 );
      int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

      errordata.addElement( summary );

      int linecheckcount=0;
      boolean one_success=false;
      for ( int i=1; i < total + 1; i++ )
      {
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) data.elementAt( i );
        String returnedReceiptId="";
        String Line_Check="";
        Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
        String trans_type= ( hD.get( "TRANS_TYPE" ) == null ? " " : hD.get( "TRANS_TYPE" ).toString() );
				String lineStatus = hD.get("LINE_STATUS") == null ? "" : hD.get("LINE_STATUS").toString();

        if ( Line_Check.equalsIgnoreCase( "yes" ) && !"Yes".equalsIgnoreCase(lineStatus))
        {
          noneToUpd=false;
          linecheckcount++;

          Hashtable ResultH=new Hashtable();
          boolean line_result=false;

          ResultH=hubObj.UpdateLine( hD,BranchPlant,logonid ); // update database

          Boolean resultline= ( Boolean ) ResultH.get( "RESULT" );
          line_result=resultline.booleanValue();
          returnedReceiptId=ResultH.get( "RCECIPT_ID" ).toString();

          if ( false == line_result )
          {
            completeSuccess=false;
            //System.out.println( "*** Adding to Error Data" );
            hD.remove( "USERCHK" );
            hD.put( "USERCHK"," " );

            hD.remove( "LINE_STATUS" );
            hD.put( "LINE_STATUS","NO" );

            errordata.addElement( hD );
          }
          if ( true == line_result )
          {
            one_success=true;

            hD.remove( "LINE_STATUS" );
            hD.put( "LINE_STATUS","YES" );

            if ( !trans_type.equalsIgnoreCase( "IT" ) )
            {
              hD.remove( "RECEIPT_ID" );
              hD.put( "RECEIPT_ID",returnedReceiptId );
            }
						receivedReceipts += returnedReceiptId + ",";
            errordata.addElement( hD );
          }
          ResultH=null;

        }
        else
        {
          errordata.addElement( hD );
        }
      } //end of for

      if ( linecheckcount == 1 )
      {
        result=true;
      }
      if ( true == one_success )
      {
        result=true;
      }
    }
    catch ( Exception e )
    {
      result=false;
      e.printStackTrace();
      out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
    }

    updateresult.put( "RESULT",new Boolean( result ) );
    updateresult.put( "ERRORDATA",errordata );

    return updateresult;
  }


  private StringBuffer buildxlsDetails( Vector data,String personnelID ) throws Exception
  {
	//StringBuffer Msg=new StringBuffer();
	String url="";
	String file="";

	Random rand=new Random();
	int tmpReq=rand.nextInt();
	Integer tmpReqNum=new Integer( tmpReq );

	String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();

	file=writefilepath + personnelID + tmpReqNum.toString() + "dropship.csv";
	url=wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() + "dropship.csv";

	try
	{
	  PrintWriter pw=new PrintWriter( new FileOutputStream( file ) );

      Hashtable summary=new Hashtable();
      summary= ( Hashtable ) data.elementAt( 0 );
      int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

      pw.print( "PO,PO Line,Date Exptd,Supplier,Open Amt,Packaging,Item,Description\n" );

      int i=0; //used for detail lines
      for ( int loop=0; loop < total; loop++ )
      {
        i++;
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) data.elementAt( i );

        // get main information
        String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
        String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
        String Expected_dt= ( hD.get( "EXPCTD_DATE" ) == null ? "" : hD.get( "EXPCTD_DATE" ).toString() );
        String supplier= ( hD.get( "SUPPLIER" ) == null ? "&nbsp;" : hD.get( "SUPPLIER" ).toString() );

        String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
        String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
        String Qty_open= ( hD.get( "QTY_OPEN" ) == null ? "&nbsp;" : hD.get( "QTY_OPEN" ).toString() );
        String Pkging= ( hD.get( "PACKING" ) == null ? "&nbsp;" : hD.get( "PACKING" ).toString() );

        pw.print( "\"" + Purchase_order + "\"," );
        pw.print( "\"" + PO_Line + "\"," );
        pw.print( "\"" + Expected_dt + "\"," );
        pw.print( "\"" + supplier + "\"," );
        pw.print( "\"" + Qty_open + "\"," );
        pw.print( "\"" + Pkging + "\"," );
        pw.print( "\"" + Item_id + "\"," );
        pw.print( "\"" + Item_desc + "\"\n" );
      }
	  pw.close();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      //System.out.println("*** Error on Hub Receiving Page ***");
      out.println( radian.web.HTMLHelpObj.printError( "tcmIS Hub Inventory Reconciliation",intcmIsApplication ) );
    }

    StringBuffer MsgSB=new StringBuffer();

    if ( url.length() > 0 )
    {
      MsgSB.append( "<HTML><HEAD>\n" );
      MsgSB.append( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
      MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
      MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
      MsgSB.append( "<TITLE>Container Label Generator</TITLE>\n" );
      MsgSB.append( "</HEAD>  \n" );
      MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
      MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
      MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Downloading Excel File</b></font><P></P><BR>\n" );
      MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n" );
      MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait ...</b></font><P></P><BR>\n" );
      MsgSB.append( "</CENTER>\n" );
      MsgSB.append( "</BODY></HTML>    \n" );
    }
    else
    {
      MsgSB.append( "<HTML><HEAD>\n" );
      MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
      MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
      MsgSB.append( "<TITLE>Container Label Generator</TITLE>\n" );
      MsgSB.append( "</HEAD>  \n" );
      MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
      MsgSB.append( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
      MsgSB.append( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>An Error Occured </b></font><P></P><BR>\n" );
      MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n" );
      MsgSB.append( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please try Again or contact Tech Center.</b></font><P></P><BR>\n" );
      MsgSB.append( "</CENTER>\n" );
      MsgSB.append( "</BODY></HTML>    \n" );
    }

    return MsgSB;
  }

  private void buildHeader( Vector data,String hub_branch_id,String search_text,String sortby,String buildlabels,Hashtable initialData1,
							String nuolddays,String searchLike, String searchfor,HttpSession session  )
  {
    //StringBuffer Msg=new StringBuffer();
    String SelectedHubName="";
    try
    {
      HeaderFooter hf=new HeaderFooter( bundle,db );
      out.print(hf.printHTMLHeader("tcmIS Drop Ship Receiving",intcmIsApplication));
      out.print( "<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
      out.print( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
      out.print( "<SCRIPT SRC=\"/clientscripts/dropshipreceiving.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );

			if (receivedReceipts.length() > 1)
			{
				receivedReceipts = receivedReceipts.substring(0, (receivedReceipts.length() - 1));
			}

      if ( "GENERATE_LABELS".equalsIgnoreCase( buildlabels ) )
      {
        //out.print( hf.printTopToolBar( "showrecrecipts()","tcmisclientreceiving.gif",session ) );
				out.print( hf.printTopToolBar( "showChemicalReceivedReceipts('"+receivedReceipts+"')","tcmisclientreceiving.gif",session ) );
      }
      else
      {
        out.print( hf.printTopToolBar( "","tcmisclientreceiving.gif",session ) );
      }

      out.print( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
      out.print( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
      out.print( "</DIV>\n" );
      out.print( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
      out.print( "<FORM method=\"POST\" NAME=\"dropshipreceiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Receiving_Servlet + "\">\n" );
      //start table #1
      out.print( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.print( "<TR VALIGN=\"MIDDLE\">\n" );

      //Ship To
      out.print( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.print( "<B>Ship To:</B>&nbsp;\n" );
      out.print( "</TD>\n" );
      out.print( "<TD WIDTH=\"10%\">\n" );
      out.print( "<SELECT CLASS=\"HEADER\" NAME=\"HubName\" size=\"1\">\n" );

	  out.print(radian.web.HTMLHelpObj.sorthashbyValue(initialData1.entrySet(),hub_branch_id));

      /*String hub_selected;
      for ( Enumeration e=initialData1.keys(); e.hasMoreElements(); )
      {
        String branch_plant= ( String ) ( e.nextElement() );
        String hub_name= ( String ) ( initialData1.get( branch_plant ) );
        //
        hub_selected="";
        if ( branch_plant.equalsIgnoreCase( hub_branch_id ) )
        {
          hub_selected="selected";
          SelectedHubName=hub_name;
        }
        out.print( "<option  " + hub_selected + " value=\"" + branch_plant + "\">" + hub_name + "</option>\n" );
      }*/
      out.print( "</SELECT>\n" );
      out.print( "</TD>\n" );

      // Sort
      out.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.print( "<B>Sort:</B>&nbsp;\n" );
      out.print( "</TD>\n" );
      out.print( "<TD WIDTH=\"30%\">\n" );
      out.print( "<SELECT CLASS=\"HEADER\"  NAME=\"SortBy\" size=\"1\">\n" );
	  Hashtable sort1result=new Hashtable();
	  sort1result.put( "PO/POLine","1" );
	  sort1result.put( "Supplier/Date Exptd","2" );
	  sort1result.put( "Date Exptd/Supplier","3" );
	  sort1result.put( "MR-Line/Date Exptd","4" );
	  out.print( radian.web.HTMLHelpObj.getDropDown( sort1result,sortby ) );

      /*if ( sortby.equalsIgnoreCase( "1" ) )
      {
        out.print( "<option selected value=\"1\">PO/POLine</option>\n" );
        out.print( "<option value=\"2\">Supplier/Date Exptd</option>\n" );
        out.print( "<option value=\"3\">Date Exptd/Supplier</option>\n" );
        out.print( "<option value=\"4\">MR-Line/Date Exptd</option>\n" );
      }
      else
      if ( sortby.equalsIgnoreCase( "2" ) )
      {
        out.print( "<option  value=\"1\">PO/POLine</option>\n" );
        out.print( "<option selected value=\"2\">Supplier/Date Exptd</option>\n" );
        out.print( "<option value=\"3\">Date Exptd/Supplier</option>\n" );
        out.print( "<option value=\"4\">MR-Line/Date Exptd</option>\n" );
      }
      else
      if ( sortby.equalsIgnoreCase( "3" ) )
      {
        out.print( "<option  value=\"1\">PO/POLine</option>\n" );
        out.print( "<option  value=\"2\">Supplier/Date Exptd</option>\n" );
        out.print( "<option selected value=\"3\">Date Exptd/Supplier</option>\n" );
        out.print( "<option  value=\"4\">MR-Line/Date Exptd</option>\n" );
      }
      else if ( sortby.equalsIgnoreCase( "4" ) )
      {
        out.print( "<option  value=\"1\">PO/POLine</option>\n" );
        out.print( "<option  value=\"2\">Supplier/Date Exptd</option>\n" );
        out.print( "<option  value=\"3\">Date Exptd/Supplier</option>\n" );
        out.print( "<option selected value=\"4\">MR-Line/Date Exptd</option>\n" );
      }*/
      out.print( "</SELECT>\n" );
      out.print( "</TD>\n" );

      //No of day expected
      out.print( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
      out.print( "Exptd Within&nbsp;<INPUT CLASS=\"HEADER\" type=\"text\" name=\"nodaysold\" value=\"" + ( nuolddays.length() < 1 ? "30" : nuolddays ) + "\" SIZE=\"1\">\n" );
      out.print( "&nbsp;Days</TD>\n" );

      //List Orders
      out.print( "<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
      out.print( "<INPUT TYPE=\"submit\" VALUE=\"List Orders\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      out.print( "</TD>\n" );

      /*//Generate Labels
      out.print( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
      if ( this.getupdateStatus() )
      {
        out.print( "&nbsp;\n" );
      }
      else
      {
        out.print( "&nbsp;\n" );
      }
      out.print( "</TD>\n" );*/

      out.print( "</TR>\n" );
      out.print( "<TR>\n" );

      //&nbsp;
      out.print( "<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n" );
      out.print( "&nbsp;\n" );
      out.print( "</TD>\n" );
      out.print( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n" );
      out.print( "&nbsp;</TD>\n" );

      // Search Text
      out.print( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
      out.print( "<B>Search:</B>&nbsp;\n" );
      out.print( "</TD>\n" );

	  out.print( "<TD WIDTH=\"30%\" COLSPAN=\"2\">\n" );
	  out.print( "<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" size=\"1\">\n" );
	  Hashtable sortresult=new Hashtable();
	  sortresult.put( "PO","a.RADIAN_PO" );
	  sortresult.put( "MR","a.MR_NUMBER" );
	  sortresult.put( "Item Id","a.ITEM_ID" );
	  sortresult.put( "Item Desc","a.ITEM_DESCRIPTION" );

	  out.print( radian.web.HTMLHelpObj.getDropDown( sortresult,searchLike ) );
	  out.print( "</SELECT>\n" );

	  out.print( "<SELECT CLASS=\"HEADER\"  NAME=\"searchfor\" size=\"1\">\n" );
	  sortresult=new Hashtable();
	  sortresult.put( "Like","Like" );
	  sortresult.put( "Equals","Equals" );
	  out.print( radian.web.HTMLHelpObj.getDropDown( sortresult,searchfor ) );
	  out.print( "</SELECT>\n" );

	  out.print( "&nbsp;&nbsp;<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"" + search_text + "\" size=\"19\">\n" );
	  out.print( "</TD>\n" );

      /*out.print( "<TD WIDTH=\"25%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
      if ( search_text.equalsIgnoreCase( "NONE" ) )
      {
        out.print( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" VALUE=\"\" size=\"19\">\n" );
      }
      else
      {
        out.print( "&nbsp;&nbsp;<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"" + search_text + "\" size=\"19\">\n" );
      }
      out.print( "</TD>\n" );*/

      //Blank
      /*out.print( "<TD WIDTH=\"10%\">\n" );
      out.print( "&nbsp;\n" );
      out.print( "</TD>\n" );*/

      //Update
      out.print( "<TD WIDTH=\"5%\" CLASS=\"announce\">\n" );
      if ( this.getupdateStatus() )
      {
        out.print(
           "<INPUT TYPE=\"submit\" VALUE=\"Receive\" onclick= \"return update(this)\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
      }
      else
      {
        out.print( "&nbsp;\n" );
      }
      out.print( "</TD>\n" );
      /*out.print( "<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n" );
      out.print( "&nbsp;\n" );
      out.print( "</TD>\n" );*/
      out.print( "</TR></TABLE>\n" );

      int total=0;
      if ( null == data )
      {
        total=0;
      }
      else
      {
        Hashtable summary=new Hashtable();
        summary= ( Hashtable ) data.elementAt( 0 );
        total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
      }

      out.print( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n" );
      out.print( "<tr><td>" );
      out.print( "<input type=\"hidden\" name=\"Total_number\" value=\"" + total + "\">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" VALUE=\" \">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"AddBin_Line_No\" VALUE=\" \">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"HubRef\" VALUE=\"" + hub_branch_id + "\">\n" );
      out.print( "<INPUT TYPE=\"hidden\" NAME=\"paperSize\" VALUE=\"" + paper_size + "\">\n" );
      out.print( "</TD></tr>" );
      out.print( "</table>\n" );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      out.print( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ));
	  return;
    }

    return;
  }
//end of method

  private void buildDetails( Vector data,String SubuserAction )
  {
    //StringBuffer Msg=new StringBuffer();
    String checkednon = "";

    try
    {
      Hashtable summary=new Hashtable();
      summary= ( Hashtable ) data.elementAt( 0 );
      int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
      out.print( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      /*out.print( "<tr align=\"center\">\n" );
      out.print( "<TH width=\"10%\"  height=\"38\">MR-Line<BR>("+bundle.getString("DB_CLIENT")+" PO)</TH>\n" );
      out.print( "<TH width=\"5%\"  height=\"38\">Requestor</TH>\n" );
      out.print( "<TH width=\"5%\"  height=\"38\">Deliver To</TH>\n" );
      out.print( "<TH width=\"5%\"  height=\"38\">PO-Line</TH>\n" );
      out.print( "<TH width=\"5%\"  height=\"38\">Date Exptd</TH>\n" );
      out.print( "<TH width=\"7%\"  height=\"38\">Supplier</TH>\n" );
      out.print( "<TH width=\"5%\"  height=\"38\">Open Amt</TH>\n" );
      out.print( "<TH width=\"5%\" height=\"38\">Packaging</TH>\n" );
      out.print( "<TH width=\"12%\" height=\"38\">Description</TH>\n" );
      if ( this.getupdateStatus() )
      {
        out.print( "<TH width=\"2%\"  height=\"38\">OK</TH>\n" );
        out.print( "<TH width=\"8%\"  height=\"38\">Lot</TH>\n" );
        out.print( "<TH width=\"5%\"  height=\"38\">Act Supp Ship Date mm/dd/yyyy</TH>\n" );
        out.print( "<TH width=\"5%\"  height=\"38\">DOR mm/dd/yyyy</TH>\n" );
        out.print( "<TH width=\"5%\"  height=\"38\">Exp Date mm/dd/yyyy</TH>\n" );
        out.print( "<TH width=\"5%\"  height=\"38\">Qty Rec'd</TH>\n" );
        out.print( "<TH width=\"5%\"  height=\"38\">Duplicate</TH>\n" );
      }
      out.print( "</tr>\n" );*/

      int i=0; //used for detail lines
      int lineadded=0;
      for ( int loop=0; loop < total; loop++ )
      {
        i++;
        boolean createHdr=false;

        if ( loop % 10 == 0 )
        {
          createHdr=true;
        }

        if ( createHdr )
        {
          out.print( "<tr align=\"center\">\n" );
          out.print( "<TH CLASS=\"results\" width=\"10%\"  height=\"38\">MR-Line<BR>("+bundle.getString("DB_CLIENT")+" PO)</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Requestor</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Deliver To</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">PO-Line</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Date Exptd</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"7%\"  height=\"38\">Supplier</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Open Amt<BR>(MR Qty Open)</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"5%\" height=\"38\">Packaging</TH>\n" );
          out.print( "<TH CLASS=\"results\" width=\"12%\" height=\"38\">Description</TH>\n" );
          if ( this.getupdateStatus() )
          {
            out.print( "<TH CLASS=\"results\" width=\"2%\"  height=\"38\">OK</TH>\n" );
            out.print( "<TH CLASS=\"results\" width=\"8%\"  height=\"38\">Lot</TH>\n" );
            out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Act Supp Ship Date mm/dd/yyyy</TH>\n" );
            out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">DOR mm/dd/yyyy</TH>\n" );
            out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Exp Date mm/dd/yyyy</TH>\n" );
            out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Qty Rec'd</TH>\n" );
			out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Notes</TH>\n" );
            out.print( "<TH CLASS=\"results\" width=\"5%\"  height=\"38\">Duplicate</TH>\n" );
          }

          out.print( "</tr>\n" );
        }

        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) data.elementAt( i );

        // get main information
        String Purchase_order= ( hD.get( "PURCHASE_ORDER" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER" ).toString() );
        String PO_Line= ( hD.get( "PURCHASE_ORDER_LINE" ) == null ? "&nbsp;" : hD.get( "PURCHASE_ORDER_LINE" ).toString() );
        String Expected_dt= ( hD.get( "EXPCTD_DATE" ) == null ? "" : hD.get( "EXPCTD_DATE" ).toString() );
        String Item_id= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
        String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
        String Qty_open= ( hD.get( "QTY_OPEN" ) == null ? "&nbsp;" : hD.get( "QTY_OPEN" ).toString() );
        String Pkging= ( hD.get( "PACKING" ) == null ? "&nbsp;" : hD.get( "PACKING" ).toString() );
        String trans_type= ( hD.get( "TRANS_TYPE" ) == null ? "&nbsp;" : hD.get( "TRANS_TYPE" ).toString() );
        //String Specs= ( hD.get( "SPECIFICATION" ) == null ? "&nbsp;" : hD.get( "SPECIFICATION" ).toString() );
        String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
        //String Date_mfgd= ( hD.get( "DATE_MFGD" ) == null ? "&nbsp;" : hD.get( "DATE_MFGD" ).toString() );
        String Qty_recd= ( hD.get( "QTY_RECD" ) == null ? "&nbsp;" : hD.get( "QTY_RECD" ).toString() );
        String Line_Check= ( hD.get( "USERCHK" ) == null ? "&nbsp;" : hD.get( "USERCHK" ).toString() );
        if ( Line_Check.equalsIgnoreCase( "yes" ) )
        {
          checkednon="checked";
        }
        else
        {
          checkednon="";
        }
        String Sel_bin= ( hD.get( "BIN_NAME" ) == null ? "&nbsp;" : hD.get( "BIN_NAME" ).toString() );
        String Oreceipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
        String indefshelflie= ( hD.get( "INDEFINITE_SHELF_LIFE" ) == null ? " " : hD.get( "INDEFINITE_SHELF_LIFE" ).toString() );
        String supplier= ( hD.get( "SUPPLIER" ) == null ? "&nbsp;" : hD.get( "SUPPLIER" ).toString() );
        String Date_recieved= ( hD.get( "DATE_RECIEVED" ) == null ? "&nbsp;" : hD.get( "DATE_RECIEVED" ).toString() );
        String Expire_date=hD.get( "EXPIRE_DATE" ).toString();
        //String shipment_date=hD.get( "DATE_OF_SHIPMENT" ).toString();
        String LINE_STATUS=hD.get( "LINE_STATUS" ).toString();
        String actshipDate= ( hD.get( "ACTUAL_SHIP_DATE" ) == null ? " " : hD.get( "ACTUAL_SHIP_DATE" ).toString() );
        //String criticalpo= ( hD.get( "CRITICAL" ) == null ? " " : hD.get( "CRITICAL" ).toString() );
        String mrnumber= ( hD.get( "MR_NUMBER" ) == null ? " " : hD.get( "MR_NUMBER" ).toString() );
        String mrlineitem= ( hD.get( "MR_LINE_ITEM" ) == null ? " " : hD.get( "MR_LINE_ITEM" ).toString() );
        String mrqtyopen= ( hD.get( "MR_QTY_OPEN" ) == null ? " " : hD.get( "MR_QTY_OPEN" ).toString() );
        String requestorname= ( hD.get( "REQUESTOR_NAME" ) == null ? " " : hD.get( "REQUESTOR_NAME" ).toString() );
        String deliverypoint= ( hD.get( "DELIVERY_POINT" ) == null ? " " : hD.get( "DELIVERY_POINT" ).toString() );
		String customerpo = ( hD.get( "CUSTOMER_PO" ) == null ? " " : hD.get( "CUSTOMER_PO" ).toString() );
		if (customerpo.trim().length() > 0) {customerpo = "("+customerpo+")";}
		String recnotes = hD.get("NOTES").toString();

        String Color=" ";
        String itemColor=" ";

        if ( i % 2 == 0 )
        {
          Color="CLASS=\"blue";
          itemColor="INVISIBLEHEADBLUE";
        }
        else
        {
          Color="CLASS=\"white";
          itemColor="INVISIBLEHEADWHITE";
        }

        String chkbox_value="no";
        if ( checkednon.equalsIgnoreCase( "checked" ) )
        {
          chkbox_value="yes";
        }

        if ( SubuserAction.equalsIgnoreCase( "UpdPage" ) && "NO".equalsIgnoreCase( LINE_STATUS ) )
        {
          Color="CLASS=\"red";
        }

        if ( !this.getupdateStatus() )
        {
          lineadded++;
          out.print( "<tr align=\"center\">\n" );
          out.print( "<td " + Color + "\" width=\"10%\" height=\"38\">" + mrnumber + "-" + mrlineitem + "<BR>"+customerpo+"</td>\n" );
          out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + requestorname + "</td>\n" );
          out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + deliverypoint + "</td>\n" );
          out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Purchase_order + "-" + PO_Line + "\n</td>\n" );
          out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Expected_dt + "</td>\n" );
          out.print( "<td " + Color + "l\" width=\"7%\" height=\"38\" >" + supplier + "</td>\n" );
          out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Qty_open + "<BR>("+mrqtyopen+")</td>\n" );
          out.print( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + Pkging + "</td>\n" );
          //out.print("<td "+Color+"\" width=\"5%\" height=\"38\">"+(Item_id)+"</td>\n");
          out.print( "<td " + Color + "l\" width=\"12%\" height=\"38\">" + Item_desc + "</td>\n" );

          out.print( "</tr>\n" );
        }
        else
        {
          if ( ( SubuserAction.equalsIgnoreCase( "Update" ) && "YES".equalsIgnoreCase( LINE_STATUS ) ) || ( "YES".equalsIgnoreCase( LINE_STATUS ) ) )
          {
            out.print( "<INPUT TYPE=\"hidden\" value=\"no\" NAME=\"row_chk" + i + "\">\n" );
            out.print( "<INPUT TYPE=\"hidden\" value=\"" + Sel_bin + "\" NAME=\"selectElementBin" + i + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\">" );
            out.print( "<input type=\"hidden\" NAME=\"receipt_id" + i + "\" value=\"" + Oreceipt_id + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"act_suppship_date" + i + "\" value=\"" + actshipDate + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"expiry_date" + i + "\" value=\"" + Expire_date + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"qty_recd" + i + "\"  value=\"" + Qty_recd + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"po\" value=\"" + Purchase_order + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"po_line" + i + "\" value=\"" + PO_Line + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"item_id" + i + "\" value=\"" + Item_id + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"item_id\" value=\"" + Item_id + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"transaction_type" + i + "\" value=\"" + trans_type + "\">\n" );
          }
          else
          {
            lineadded++;
            out.print( "<tr align=\"center\">\n" );
            out.print( "<td " + Color + "\" width=\"10%\" height=\"38\">" + mrnumber + "-" + mrlineitem + "<BR>"+customerpo+"</td>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + requestorname + "</td>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + deliverypoint + "</td>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Purchase_order + "-" + PO_Line + "</td>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Expected_dt + "</td>\n" );
            out.print( "<td " + Color + "l\" width=\"7%\" height=\"38\" >" + supplier + "</td>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\">" + Qty_open + "<BR>("+mrqtyopen+")</td>\n" );
            out.print( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + Pkging + "</td>\n" );
            out.print( "<td " + Color + "l\" width=\"12%\" height=\"38\" >" + Item_desc + "</td>\n" );
            out.print( "<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" NAME=\"row_chk" + i + "\" value=\"" + chkbox_value + "\"  onClick= \"checkValues('" + i + "')\" " + checkednon + "></td>\n" );
            out.print( "<td " + Color + "\" width=\"8%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" NAME=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\" maxlength=\"30\" size=\"20\"></td>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" NAME=\"act_suppship_date" + i + "\" value=\"" + actshipDate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" onClick=\"return getCalendar(document.dropshipreceiving.act_suppship_date" + i +");\">&diams;</a></FONT></td>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" NAME=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" onClick=\"return getCalendar(document.dropshipreceiving.date_recieved" + i +");\">&diams;</a></FONT></td>\n" );

            if ( "Y".equalsIgnoreCase( indefshelflie ) || "01/01/3000".equalsIgnoreCase( Expire_date ) )
            {
              out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" NAME=\"expiry_date" + i + "\" value=\"Indefinite\">Indefinite</td>\n" );
            }
            else
            {
              out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" NAME=\"expiry_date" + i + "\" value=\"" + Expire_date + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" onClick=\"return getCalendar(document.dropshipreceiving.expiry_date" + i + ");\">&diams;</a></FONT></td>\n" );
            }

            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" NAME=\"qty_recd" + i + "\"  value=\"" + Qty_recd + "\" maxlength=\"10\" size=\"6\"></td>\n" );
			out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><TEXTAREA name=\"recnotes" + i + "\" rows=\"3\" cols=\"25\">" + recnotes + "</TEXTAREA></TD>\n" );
            out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" onclick= \" return duplLine(name,this)\" value=\"Dupl\" NAME=\"Button" + i + "\">\n" );
            out.print( "<input type=\"hidden\" NAME=\"transaction_type" + i + "\" value=\"" + trans_type + "\"></td>\n" );
            out.print( "<input type=\"hidden\" NAME=\"item_id" + i + "\" value=\"" + Item_id + "\"></td>\n" );
            out.print( "<input type=\"hidden\" NAME=\"openqty" + i + "\" value=\"" + Qty_open + "\"></td>\n" );
            out.print( "<input type=\"hidden\" NAME=\"po" + i + "\" value=\"" + Purchase_order + "\"></td>\n" );
            out.print( "<input type=\"hidden\" NAME=\"po_line" + i + "\" value=\"" + PO_Line + "\"></td>\n" );

            out.print( "</tr>\n" );
          }
        }
      }

      out.print( "</table>\n" );
      out.print( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      out.print( "<tr><TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n</TD></tr>" );
      out.print( "<tr><TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n</TD></tr>" );
      out.print( "<tr><TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n</TD></tr>" );
      out.print( "</table>\n" );

      out.print( "</form>\n" );
      out.print( "</body></html>\n" );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      out.print( radian.web.HTMLHelpObj.printError( "tcmIS Hub Receiving",intcmIsApplication ) );
    }

    return;
  }
}
//END OF CLASS
