package radian.tcmis.server7.share.servlets;

//ACJEngine
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.ReportData;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.SearchPScreen;
import radian.tcmis.server7.share.dbObjs.ReceiptDocument;
import radian.tcmis.server7.share.factory.WorkAreaCommentFactory;
import radian.tcmis.server7.share.factory.PartNumberCommentFactory;

public class ReportObj  extends TcmisServletObj
{
  String function=null;
  Hashtable mySendObj=null;
  Hashtable inData=null;

  public ReportObj( ServerResourceBundle b,TcmISDBModel d )
  {
	super();
	bundle=b;
	db=d;
  }

  public ReportObj( TcmISDBModel d,Hashtable ata )
  {
	super();
	db=d;
	inData=ata;

  }

  protected void resetAllVars()
  {
	function=null;
	inData=null;
  }

  protected void print( TcmisOutputStreamServer out )
  {
	try
	{
	  out.sendObject( ( Hashtable ) mySendObj );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
  }

  protected void getResult()
  {
	mySendObj=new Hashtable();
	inData= ( Hashtable ) myRecvObj;
	function=inData.get( "ACTION" ).toString();
	if ( function.equalsIgnoreCase( "GET_LIST_CHEMICALS" ) ) {
	  getListChemicals();
	}else if ( function.equalsIgnoreCase( "SELECT_CHEMICAL" ) ){
	  selectChemical();
        }else if ( function.equalsIgnoreCase( "GET_FORMATTED_INITAL_DATA" ) ){
          getFormattedInitialData();
        }else if ( function.equalsIgnoreCase( "GET_MSDS_REVISION_INITAL_DATA" ) ){
          getMSDSRevisionInitialData();
        }else if ( function.equalsIgnoreCase( "PRINT_CHEMICAL_LIST" ) ){
          printChemicalList();
        }else if ( function.equalsIgnoreCase( "PRINT_APPROVED_WORK_AREAS" ) ){
          printApprovedWorkAreas();
        }else if ( function.equalsIgnoreCase( "GET_APPROVED_WORK_AREAS" ) ){
          getApprovedWorkAreas();
        }else if ( function.equalsIgnoreCase( "GET_WORK_AREA_COMMENTS" ) ){
          getWorkAreaComments();
        }else if ( function.equalsIgnoreCase( "PRINT_WORK_AREA_COMMENTS" ) ){
          printWorkAreaComments();
        }else if ( function.equalsIgnoreCase( "PROCESS_WORK_AREA_COMMENTS" ) ){
          processWorkAreaComments();
        }else if ( function.equalsIgnoreCase( "GET_PART_NUMBER_COMMENTS" ) ){
          getPartNumberComments();
        }else if ( function.equalsIgnoreCase( "PRINT_PART_NUMBER_COMMENTS" ) ){
          printPartNumberComments();
        }else if ( function.equalsIgnoreCase( "PROCESS_PART_NUMBER_COMMENTS" ) ){
          processPartNumberComments();
        }else if ( function.equalsIgnoreCase( "GET_RECEIPT_DOCUMENT_DATA" ) ){
          getReceiptDocument();
        }

  } //end of method

  protected void getReceiptDocument() {
    try {
      ReceiptDocument receiptDocument = new ReceiptDocument(db);
      mySendObj.put("DATA",receiptDocument.getReceiptDocument(inData));
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG", "");                                 
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while retrieving document.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }


  protected void processPartNumberComments() {
    try {
      PartNumberCommentFactory wacf = new PartNumberCommentFactory(db);
      wacf.processWorkAreaComments(inData);
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG", "Comments successfully updated.");
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while processing work area comments.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void getPartNumberComments() {
    try {
      PartNumberCommentFactory wacf = new PartNumberCommentFactory(db);
      mySendObj.put("PART_NUMBER_COMMENTS",wacf.getData(inData));
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG","");
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while retrieving work area comments.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void printPartNumberComments() {
    PartNumberCommentsReportGenerator awarg = new PartNumberCommentsReportGenerator();
    String url = awarg.buildReport((Vector)inData.get("PART_NUMBER_COMMENTS_DATA"),(String)inData.get("CATALOG_DESC"),(String)inData.get("CAT_PART_NO"));
    if (url.length() > 1) {
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("URL",url);
      mySendObj.put("MSG","");
    }else {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while printing work area comments.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void processWorkAreaComments() {
    try {
      WorkAreaCommentFactory wacf = new WorkAreaCommentFactory(db);
      wacf.processWorkAreaComments(inData);
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG", "Comments successfully updated.");
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while processing work area comments.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void getWorkAreaComments() {
    try {
      WorkAreaCommentFactory wacf = new WorkAreaCommentFactory(db);
      mySendObj.put("WORK_AREA_COMMENTS",wacf.getData(inData));
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG","");
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while retrieving work area comments.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void printWorkAreaComments() {
    WorkAreaCommentsReportGenerator awarg = new WorkAreaCommentsReportGenerator();
    String url = awarg.buildReport((Vector)inData.get("WORK_AREA_COMMENTS_DATA"),(String)inData.get("FACILITY_NAME"),(String)inData.get("WORK_AREA_DESC"),(String)inData.get("CATALOG_DESC"),(String)inData.get("CAT_PART_NO"));
    if (url.length() > 1) {
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("URL",url);
      mySendObj.put("MSG","");
    }else {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while printing work area comments.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void getApprovedWorkAreas() {
    try {
      SearchPScreen sp = new SearchPScreen(db);
      mySendObj.put("APPROVED_WORK_AREAS_DATA",sp.getApprovedWorkAreas(inData));
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("MSG","");
    }catch (Exception e) {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while retrieving approved work areas data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void printApprovedWorkAreas() {
    ApprovedWorkAreasReportGenerator awarg = new ApprovedWorkAreasReportGenerator();
    String url = awarg.buildReport((Vector)inData.get("APPROVED_WORKAREA_DATA"),(String)inData.get("CATALOG_ID"),(String)inData.get("FACILITY_NAME"),(String)inData.get("PART_NO"),(Boolean)inData.get("ALL_CATALOG"));
    if (url.length() > 1) {
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("URL",url);
      mySendObj.put("MSG","");
    }else {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while printing approved work areas data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void printChemicalList() {
    ChemicalListReportGenerator osrg = new ChemicalListReportGenerator();
    String url = osrg.buildReport((Vector)inData.get("LIST_DATA"),(String)inData.get("LIST_ID"),(String)inData.get("LIST_DESC"));
    if (url.length() > 1) {
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("URL",url);
      mySendObj.put("MSG","");
    }else {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","tcmIS encountered an error while printing chemical list data.\nPlease try again.\nIf problem persistent send a message to\nthe tcmIS support staff at tcmis@tcmis.com.");
    }
  }

  protected void getMSDSRevisionInitialData() {
    try {
      ReportData rd = new ReportData(db);
      mySendObj.put("DATA",rd.getMSDSRevisionInitialData((String)inData.get("USERID")));
    }catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Error while loading initial data to formatted report","User: "+(String)inData.get("USERID"));
    }
  } //end of method

  protected void getFormattedInitialData() {
    try {
      ReportData rd = new ReportData(db);
      mySendObj.put("DATA",rd.getFormattedInitialData((String)inData.get("USERID")));
    }catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.sendtrongemail("Error while loading initial data to formatted report","User: "+(String)inData.get("USERID"));
    }
  }

  protected void getListChemicals()
  {
	try
	{
	  String listId=inData.get( "LIST_ID" ).toString();
	  Vector v=ReportData.getListChemicals( db,listId );
	  mySendObj.put( "LIST_CHEMICALS",v );
	  //Nawaz 19/09/01 new changes to gui tables
	  mySendObj.put( "TABLE_STYLE",BothHelpObjs.getTableStyle() );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
  }

  protected void selectChemical()
  {
	try
	{
	  String queryString=inData.get( "QUERY_STRING" ).toString();
	  Vector v=ReportData.selectChemicalsForSynonym( db,queryString );
	  mySendObj.put( "LIST_CHEMICALS",v );
	  //Nawaz 19/09/01 new changes to gui tables
	  mySendObj.put( "TABLE_STYLE",BothHelpObjs.getTableStyle() );
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
  }
}
