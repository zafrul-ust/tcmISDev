package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import java.io.OutputStream;
import java.math.BigDecimal;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 07-29-03 Code Cleanup and changed the xsl to cvs to avoid some wiered characters showing up in the report
 * 12-11-03 Added Expire Date,owner_company_id and Catpart Number to the excel sheet
 * 06-25-04 Adding log statements to trace a memory usage issue
 */

public class InvenReconcilePage
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
  public InvenReconcilePage()
  {

  }

  public InvenReconcilePage( ServerResourceBundle b,TcmISDBModel d )
  {
    bundle=b;
    db=d;
  }

  //12-17-02
  private String getTimeDate()
  {
    Calendar cal=Calendar.getInstance();
    String cdate=new String( ( cal.get( Calendar.MONTH ) + 1 ) + "/" + cal.get( Calendar.DATE ) + "/" + cal.get( Calendar.YEAR ) );
    int am_pm=cal.get( Calendar.AM_PM );
    String am="";
    if ( am_pm == 0 )
    {
      am="AM";
    }
    else if ( am_pm == 1 )
    {
      am="PM";
    }
    int min=cal.get( Calendar.MINUTE );
    int hours=cal.get( Calendar.HOUR );
    String time="";

    if ( hours < 10 )
    {
      time+="0" + hours;
    }
    else
    {
      time+=hours;
    }
    if ( min < 10 )
    {
      time+=":0" + min;
    }
    else
    {
      time+=":" + min;
    }
    time+=" " + am;

    return cdate + " " + time;
  } //end of method

  public String generateXlsReport( Vector VFromSession,String hubname,String countdate,String showall,String orderBy,ResourceLibrary res )
     throws Exception,IOException
  {
  String url="";
  String file="";

  Random rand=new Random();
  int tmpReq=rand.nextInt();
  Integer tmpReqNum=new Integer( tmpReq );

  String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
  String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();

  file=writefilepath + tmpReqNum.toString() + "invenReconcileXls.xls";
  url=wwwHome + "reports/temp/" + tmpReqNum.toString() + "invenReconcileXls.xls";

    try
    {
      OutputStream newPw = (OutputStream) (new FileOutputStream(file));
      ExcelHandler pw = new ExcelHandler(res);
      Hashtable summary = new Hashtable();
      
      pw.addRow();
      pw.addCellBold("Date Time");
      pw.addCellBold(getTimeDate());
      
      pw.addRow();
      pw.addCellBold(res.getString("label.branchplant"));
      pw.addCellBold(hubname);

      pw.addRow();
      pw.addCellBold("Count Date Time");
      pw.addCellBold(countdate);

      if ( "1".equalsIgnoreCase( orderBy ) )
      {
        orderBy="Bin";
      }
      else
      {
        orderBy="Item ID";
      }

      pw.addRow();
      pw.addCellBold(res.getString("label.orderby"));
      pw.addCellBold(orderBy);

       if ( "All".equalsIgnoreCase( showall ) )
      {
        showall="All";
      }
      else if ( "Item".equalsIgnoreCase( showall ) )
      {
        showall="Item";
      }
      else if ( "80per".equalsIgnoreCase( showall ) )
      {
        showall="80 % Inventory Value";
      }
      else
      {
        showall="Items to Reconcile";
      }
      pw.addRow();
      pw.addCellBold("Show");
      pw.addCellBold(showall);

      pw.addRow();
      pw.addRow();
      
      pw.addRow();      
      String[] headerkeys = { "label.inventorygroup","label.item","label.description","label.packaging", "label.po",
               "label.ownercompanyid","label.unitcost","label.mfglot","label.bin", "label.lotstatus",
               "label.receiptid","label.recordedonhand","label.actualonhand","label.expiredate","label.partnumber","label.legacyreceiptid"};
      int [] widths = {10,0,0,0,0,
                       13,0,0,0,0,
                       0,10,0,0,20,20 };
      int [] types = {0,0,pw.TYPE_PARAGRAPH,pw.TYPE_PARAGRAPH,0,
                      0,0, 0,0,0,
                      0,0,0,0,0,0};
      int [] aligns = {0,0,0,0,0,
                      0,0,0,0,0,
                      0,0,0,0,0,0};

      pw.applyColumnHeader(headerkeys, types, widths, aligns);

      Hashtable sum= ( Hashtable ) ( VFromSession.elementAt( 0 ) );
      int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
      for ( int i=1; i < count + 1; i++ )
      {
        Hashtable hD= ( Hashtable ) ( VFromSession.elementAt( i ) );

        String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
        Item_desc=HelpObjs.findReplace( Item_desc,","," " );
        Item_desc=HelpObjs.findReplace( Item_desc,"<BR>"," " );

        String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString() );
        String catPartNo= ( hD.get( "CAT_PART_NO" ) == null ? "" : hD.get( "CAT_PART_NO" ).toString() );
        String itemId= ( hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString() );
        String receiptId= ( hD.get( "RECEIPT_ID" ) == null ? "" : hD.get( "RECEIPT_ID" ).toString() );
        String reconhand= ( hD.get( "ON_HAND" ) == null ? "" : hD.get( "ON_HAND" ).toString() );
        String actonHand= ( hD.get( "ACTUAL_ON_HAND" ) == null ? "" : hD.get( "ACTUAL_ON_HAND" ).toString() );
        String radianpo= ( hD.get( "RADIAN_PO" ) == null ? "" : hD.get( "RADIAN_PO" ).toString() );
        String unitCost= ( hD.get( "UNIT_COST" ) == null ? "" : hD.get( "UNIT_COST" ).toString() );
        String legacyReceiptId= ( hD.get( "CUSTOMER_RECEIPT_ID" ) == null ? "" : hD.get( "CUSTOMER_RECEIPT_ID" ).toString() );

          pw.addRow();
          pw.addCell( invengrp  );
          if (itemId.trim().length() ==0)
          {
    	      pw.addCell((BigDecimal)null);
          }
          else
          {
            pw.addCell(new BigDecimal(itemId.trim()));
          }
          pw.addCell( Item_desc  );
          pw.addCell( hD.get( "PACKAGING" ).toString()  );
          if (radianpo.trim().length() ==0)
          {
    	      pw.addCell((BigDecimal)null);
          }
          else
          {
            pw.addCell(new BigDecimal(radianpo.trim()));
          }
          pw.addCell( hD.get( "owner_company_id" ).toString()  );
          if (unitCost.trim().length() ==0)
          {
    	      pw.addCell((BigDecimal)null);
          }
          else
          {
            pw.addCell(new BigDecimal(unitCost.trim()));
          }
          pw.addCell( hD.get( "MFG_LOT" ).toString()  );
          pw.addCell( hD.get( "BIN" ).toString()  );
          pw.addCell( hD.get( "LOT_STATUS" ).toString()  );
          if (receiptId.trim().length() ==0)
          {
    	      pw.addCell((BigDecimal)null);
          }
          else
          {
            pw.addCell(new BigDecimal(receiptId.trim()));
          }
          if (reconhand.trim().length() ==0)
          {
    	      pw.addCell((BigDecimal)null);
          }
          else
          {
            pw.addCell(new BigDecimal(reconhand.trim()));
          }
          if (actonHand.trim().length() ==0)
          {
    	      pw.addCell((BigDecimal)null);
          }
          else
          {
            pw.addCell(new BigDecimal(actonHand.trim()));
          }
          pw.addCell( hD.get( "EXPIRE_DATE" ).toString()  );
        if (catPartNo.trim().length() >0)
        {
          pw.addCell( catPartNo );
        }
        else
        {
         pw.addCell( hD.get( "cat_part_nos" ).toString()  );
        }
        pw.addCell( legacyReceiptId );
      }
    pw.write(newPw);
    newPw.close();
    }
      catch ( Exception e )
    {
      e.printStackTrace();
      return "";
    }
      /*StringBuffer Msg=new StringBuffer();
      //header
      Msg.append(getTimeDate());
      Msg.append( "Hub:," + hubname + "\n" );
      Msg.append( "Count Date Time:," + countdate + "\n" );

      if ( "1".equalsIgnoreCase( orderBy ) )
      {
        orderBy="Bin";
      }
      else
      {
        orderBy="Item ID";
      }
      Msg.append( "Order By:," + orderBy + "\n" );

      if ( "All".equalsIgnoreCase( showall ) )
      {
        showall="All";
      }
      else if ( "Item".equalsIgnoreCase( showall ) )
      {
        showall="Item";
      }
      else if ( "80per".equalsIgnoreCase( showall ) )
      {
        showall="80 % Inventory Value";
      }
      else
      {
        showall="Items to Reconcile";
      }
      Msg.append( "Show :," + showall + "\n" );
      Msg.append( " , \n" );
      Msg.append( "Inventory Group,Item Id,Description,Packaging,PO,Owner,Unit Cost,Mfg Lot,Bin,Lot Status,Receipt ID,Recorded On Hand,Actual on Hand,Expire Date,Cat Part Nos\n" );

      Hashtable sum= ( Hashtable ) ( VFromSession.elementAt( 0 ) );
      int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
      for ( int i=1; i < count + 1; i++ )
      {
        Hashtable hD= ( Hashtable ) ( VFromSession.elementAt( i ) );

        String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "" : hD.get( "ITEM_DESC" ).toString() );
        Item_desc=HelpObjs.findReplace( Item_desc,","," " );
        Item_desc=HelpObjs.findReplace( Item_desc,"<BR>"," " );

        String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString() );
        String catPartNo= ( hD.get( "CAT_PART_NO" ) == null ? "" : hD.get( "CAT_PART_NO" ).toString() );

        Msg.append( "\"" + invengrp + "\"," );
        Msg.append( "\"" + hD.get( "ITEM_ID" ).toString() + "\"," );
        Msg.append( "\"" + Item_desc + "\"," );
        Msg.append( "\"" + hD.get( "PACKAGING" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "RADIAN_PO" ).toString() + "\"," );
        //Msg.append( "\" \"," );
		    Msg.append( "\"" + hD.get( "owner_company_id" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "UNIT_COST" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "MFG_LOT" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "BIN" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "LOT_STATUS" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "RECEIPT_ID" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "ON_HAND" ).toString() + "\"," );
        Msg.append( "\"" + hD.get( "ACTUAL_ON_HAND" ).toString() + "\"," );
		    Msg.append( "\"" + hD.get( "EXPIRE_DATE" ).toString() + "\"," );
        if (catPartNo.trim().length() >0)
        {
          Msg.append( "\"" + catPartNo + "\"\n" );
        }
        else
        {
          Msg.append( "\"" + hD.get( "cat_part_nos" ).toString() + "\"\n" );
        }

      }
      try
      {
        File outFile=new File( file );
        FileOutputStream DBO=new FileOutputStream( outFile );
        byte outbuf[];
        outbuf=new byte[contents.length()];
        outbuf=contents.getBytes();
        DBO.write( outbuf );
        DBO.close();
      }
      catch ( IOException ioe )
      {
        return "";
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return "";

    }*/

    return url;
  }

  public String generatePicklist( Vector VFromSession,String hubname,String countdate,String showall,String skipreconhand  )
     throws Exception,IOException
  {
    Vector resultF=new Vector();

    Hashtable sum= ( Hashtable ) ( VFromSession.elementAt( 0 ) );
    int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();

    for ( int i=1; i < count + 1; i++ )
    {
      Hashtable hD=new Hashtable();
      hD= ( Hashtable ) ( VFromSession.elementAt( i ) );

      Hashtable Result2=new Hashtable();
      String Item_id= ( hD.get( "ITEM_ID" ) == null ? "&nbsp;" : hD.get( "ITEM_ID" ).toString() );
      String Item_desc= ( hD.get( "ITEM_DESC" ) == null ? "&nbsp;" : hD.get( "ITEM_DESC" ).toString() );
      String Mfg_lot= ( hD.get( "MFG_LOT" ) == null ? "&nbsp;" : hD.get( "MFG_LOT" ).toString() );
      String bin= ( hD.get( "BIN" ) == null ? "&nbsp;" : hD.get( "BIN" ).toString() );
      String receipt_id= ( hD.get( "RECEIPT_ID" ) == null ? "&nbsp;" : hD.get( "RECEIPT_ID" ).toString() );
      String pkg= ( hD.get( "PACKAGING" ) == null ? "&nbsp;" : hD.get( "PACKAGING" ).toString() );
      String reconhand= ( hD.get( "ON_HAND" ) == null ? "&nbsp;" : hD.get( "ON_HAND" ).toString() );
      String actonHand= ( hD.get( "ACTUAL_ON_HAND" ) == null ? "&nbsp;" : hD.get( "ACTUAL_ON_HAND" ).toString() );
      String radianpo= ( hD.get( "RADIAN_PO" ) == null ? "&nbsp;" : hD.get( "RADIAN_PO" ).toString() );

      String expiredate= ( hD.get( "EXPIRE_DATE" ) == null ? "&nbsp;" : hD.get( "EXPIRE_DATE" ).toString() );
      String lotstatus= ( hD.get( "LOT_STATUS" ) == null ? "&nbsp;" : hD.get( "LOT_STATUS" ).toString() );
      String unitCost= ( hD.get( "UNIT_COST" ) == null ? "&nbsp;" : hD.get( "UNIT_COST" ).toString() );
      String invengrp= ( hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString() );
      String receiptCatPartNo= ( hD.get( "CAT_PART_NO" ) == null ? "" : hD.get( "CAT_PART_NO" ).toString() );
      String catPartNo= ( hD.get( "cat_part_nos" ) == null ? "" : hD.get( "cat_part_nos" ).toString() );

      Result2.put( "DETAIL_0",Item_id );
      Result2.put( "DETAIL_1",Item_desc );
      Result2.put( "DETAIL_2",bin );
      Result2.put( "DETAIL_3",Mfg_lot );
      Result2.put( "DETAIL_4",receipt_id );
      Result2.put( "DETAIL_5",pkg );
      Result2.put( "DETAIL_6",reconhand );
      Result2.put( "DETAIL_7",actonHand );
      Result2.put( "DETAIL_8",radianpo );
      Result2.put( "DETAIL_9",expiredate );
      Result2.put( "DETAIL_10",lotstatus );
      Result2.put( "DETAIL_11",unitCost );
      Result2.put( "DETAIL_12",invengrp );
      if (receiptCatPartNo.length() > 0)
      {
        Result2.put( "DETAIL_13",receiptCatPartNo );
      }
      else
      {
        Result2.put( "DETAIL_13",catPartNo );
      }

      resultF.addElement( Result2 );
    }

    ACJEngine en=new ACJEngine();
    en.setDebugMode( true );
    en.setX11GfxAvailibility( false );
    en.setTargetOutputDevice( ACJConstants.PDF );
    TemplateManager tm=null;
	Random rand=new Random();
	int tmpReq=rand.nextInt();
	Integer tmpReqNum=new Integer( tmpReq );

    ACJOutputProcessor eClient=new ACJOutputProcessor();
    String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
    String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
    String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
	//en.setCacheOption(true, ""+writefilepath+"invenreconcile"+tmpReqNum.toString()+".joi");
    eClient.setPathForFontMapFile( fontmappath );

    String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
    String url=wwwHome + "labels/invenreconcile" + tmpReqNum.toString() + ".pdf";

    try
    {
      if (skipreconhand.equalsIgnoreCase("Y"))
      {
        en.readTemplate( ""+templatpath+"invenreconcileskip.jod" );
      }
      else
      {
        en.readTemplate( ""+templatpath+"invenreconcile.jod" );
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return "";
    }

    tm=en.getTemplateManager();
    tm.setLabel( "HUBNAME",hubname );
    tm.setLabel( "COUNTDATE",countdate );

    if ( "Yes".equalsIgnoreCase( showall ) )
      tm.setLabel( "LBL000","All Inventory" );

	  //reoprtlog.info("generatePicklist    Start "+tmpReqNum.toString()+"    Size: "+resultF.size()+" ");
    try
    {
      AppDataHandler ds=new AppDataHandler();
      RegisterTable[] rt=getData( resultF );
      for ( int i=0; i < rt.length; i++ )
      {
        ds.RegisterTable( rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere() );
      }
      en.setDataSource( ds );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      throw e;
    }

    try
    {
      eClient.setReportData( en.generateReport() );
    }
    catch ( Exception ex )
    {
      ex.printStackTrace();
      return "";
    }

    try
    {
      eClient.setPDFProperty( "FileName",""+writefilepath+"invenreconcile" + tmpReqNum.toString() + ".pdf" );
      eClient.generatePDF();
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return "";
    }
	  //reoprtlog.info("generatePicklist    Done  "+tmpReqNum.toString()+"");
    return url;
  }

  public RegisterTable[] getData( Vector reportData1 ) throws Exception
  {
    RegisterTable[] rt=new RegisterTable[1];

    try
    {
      rt[0]=new RegisterTable( invenReconcileData.getVector( reportData1,14 ),"INVENRECONCILEDATA",invenReconcileData.getFieldVector( 14 ),null );
    }
    catch ( Exception e1 )
    {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

  public String getServletInfo()
  {
    return "radian.web.barcode.InvenReconcilePage Information";
  }
}