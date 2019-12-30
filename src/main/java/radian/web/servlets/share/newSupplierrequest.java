package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HTMLHelpObj;
import radian.web.vvHelpObj;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2005
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 02-08-05 - Showing the Date Entered on the summary page. Also changing the status to pending payment terms if the requestor
 * changes the Net Terms to not Net 45 after the initial new supplier request submission.
 * 03-22-05 - Including countries with no states in the drop down
 * 05-02-05 - If a new supplier request is entered by a user who is also allowed to approve new payment terms, the payment terms approval step is skipped.
 */

public class newSupplierrequest
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db=null;
  protected boolean allowupdate;
  protected boolean allowPaymenttermsupdate;
  protected boolean allowaccountdetails;
  private String errormsg = "";
  private String newsuppreqid = "";
  private boolean intcmIsApplication = false;

  public void setupdateStatus( boolean id )
  {
	this.allowupdate=id;
  }

  private boolean getupdateStatus() throws Exception
  {
	return this.allowupdate;
  }

  public void setPaytermsupdateStatus( boolean id1 )
  {
	this.allowPaymenttermsupdate=id1;
  }

  private boolean getPaytermsupdateStatus() throws Exception
  {
	return this.allowPaymenttermsupdate;
  }

  public void setaccountdetails( boolean id1 )
  {
	this.allowaccountdetails=id1;
  }

  private boolean getaccountdetails() throws Exception
  {
	return this.allowaccountdetails;
	  }

    public newSupplierrequest(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

     public void doResult(HttpServletRequest request, HttpServletResponse response)
          throws ServletException,  IOException
    {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
	HttpSession newsuppsession=request.getSession();

        PersonnelBean personnelBean = (PersonnelBean) newsuppsession.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	String personnelid= newsuppsession.getAttribute( "PERSONNELID" ) == null ? "" : newsuppsession.getAttribute( "PERSONNELID" ).toString();

    String action=request.getParameter( "UserAction" );
    if ( action == null )
      action="";
    action=action.trim();

	String SubUserAction=request.getParameter( "SubUserAction" );
	if ( SubUserAction == null )
	  SubUserAction="";
	SubUserAction=SubUserAction.trim();

	String status=request.getParameter( "status" );
	if ( status == null )
	  status="All";
	status=status.trim();

	String supplier_request_id=request.getParameter( "supplier_request_id" );
	if ( supplier_request_id == null )
	  supplier_request_id="";
	supplier_request_id=supplier_request_id.trim();

	String searchtext = request.getParameter("searchtext");
	if(searchtext == null)
	  searchtext = "";
	searchtext = searchtext.trim();

	//System.out.println("action   "+action+"  SubUserAction  "+SubUserAction+"   requestid  "+supplier_request_id+"");
	try
	{
	  Hashtable requeststatus=new Hashtable();
	  Hashtable purchtype=new Hashtable();
	  String donevvstuff = newsuppsession.getAttribute("VVNEWSUPPSTUFF") != null ? newsuppsession.getAttribute("VVNEWSUPPSTUFF").toString() : "";
	  if(!donevvstuff.equalsIgnoreCase("Yes"))
	  {
		requeststatus.put( "Pending Payment Terms","Pending Payment Terms" );
		requeststatus.put( "Pending Approval","Pending Approval" );
		requeststatus.put( "Approved","Approved" );
		requeststatus.put( "Created","Created" );
		requeststatus.put( "Rejected","Rejected" );

		newsuppsession.setAttribute("REQUEST_STATUS", requeststatus);

		purchtype.put( "Chemical","Chemical" );
		purchtype.put( "Computer Hardware","Computer Hardware" );
		purchtype.put( "Gas","Gas" );
		purchtype.put( "Waste","Waste" );
		purchtype.put( "Other","Other" );

		newsuppsession.setAttribute("PURCHASE_TYPE", purchtype);

		newsuppsession.setAttribute("SUPP_QUALIFICATION", vvHelpObj.getvvqualificationlevel(db));
		newsuppsession.setAttribute( "PO_VV_PAYMENT",radian.web.vvHelpObj.getPaymentTerms(db,false) );

		Hashtable initialstData = new Hashtable ();
		initialstData= radian.web.poHelpObj.getNewSupplierInitialData(db);
		newsuppsession.setAttribute( "STATE_COUNTRY",initialstData);
		newsuppsession.setAttribute( "VVNEWSUPPSTUFF","Yes" );
	  }
	  else
	  {
		requeststatus = ( Hashtable ) newsuppsession.getAttribute( "REQUEST_STATUS" );
	  }

	  if ( "New".equalsIgnoreCase( action ) )
	  {
		String FullName= newsuppsession.getAttribute( "FULLNAME" ) == null ? "" : newsuppsession.getAttribute( "FULLNAME" ).toString();

		Hashtable newTaskH=new Hashtable();
		newTaskH.put( "SUPPLIER_REQUEST_ID","New" );
		newTaskH.put( "SUPPLIER_NAME","" );
		newTaskH.put( "SUPPLIER_PARENT","" );
		newTaskH.put( "FEDERAL_TAX_ID","" );
		newTaskH.put( "SMALL_BUSINESS","" );
		newTaskH.put( "MINORITY_BUSINESS","" );
		newTaskH.put( "DISADVANTAGED_BUSINESS","" );
		newTaskH.put( "DEFAULT_PAYMENT_TERMS","Net 45" );
		newTaskH.put( "SUPPLIER_NOTES","" );
		newTaskH.put( "FORMER_SUPPLIER_NAME","" );
		newTaskH.put( "HAAS_ACCOUNT_NUMBER","" );
		newTaskH.put( "COUNTRY_ABBREV","" );
		newTaskH.put( "STATE_ABBREV","" );
		newTaskH.put( "ADDRESS_LINE_1","" );
		newTaskH.put( "ADDRESS_LINE_2","" );
		newTaskH.put( "ADDRESS_LINE_3","" );
		newTaskH.put( "CITY","" );
		newTaskH.put( "ZIP","" );
		newTaskH.put( "ZIP_PLUS","" );
		newTaskH.put( "SUPPLIER_MAIN_PHONE","" );
		newTaskH.put( "SUPPLIER_MAIN_FAX","" );
		newTaskH.put( "SUPPLIER_CONTACT_FIRST_NAME","" );
		newTaskH.put( "SUPPLIER_CONTACT_LAST_NAME","" );
		newTaskH.put( "SUPPLIER_CONTACT_NICKNAME","" );
		newTaskH.put( "SUPPLIER_CONTACT_PHONE","" );
		newTaskH.put( "SUPPLIER_CONTACT_PHONE_EXT","" );
		newTaskH.put( "SUPPLIER_CONTACT_FAX","" );
		newTaskH.put( "SUPPLIER_CONTACT_EMAIL","" );
		newTaskH.put( "SUPPLIER_REQUEST_STATUS","" );
		newTaskH.put( "E_SUPPLIER_ID","" );
		newTaskH.put( "REQUESTER","" );

		newTaskH.put("QUALIFICATION_LEVEL","New");
		newTaskH.put("TYPE_OF_PURCHASE","");
		//newTaskH.put("APPROVER","");
		//newTaskH.put("SUPPLIER_REQUEST_STATUS","");
		//newTaskH.put("PAYMENT_TERM_APPROVER","");
		//newTaskH.put("APPROVER_NAME","");
		//newTaskH.put("PAYMENT_TERM_APPROVER_NAME","");
		newTaskH.put("REQUESTER_NAME",FullName);
		newTaskH.put("REASON_FOR_REJECTION","");

		newsuppsession.setAttribute( "REQUEST_DATA",newTaskH );

		buildnewrequest( newsuppsession,"",out );
	  }
	  else if("UPDATE".equalsIgnoreCase(action) )
	  {
		//SubUserAction
		Hashtable retrn_data = (Hashtable)newsuppsession.getAttribute("REQUEST_DATA");
		Hashtable synch_data = SynchServerData(request, retrn_data);
		newsuppsession.setAttribute("REQUEST_DATA", synch_data);

		if(UpdateDetails(synch_data, SubUserAction, personnelid))
		{
		  if ("newrequest".equalsIgnoreCase(SubUserAction))
		  {
			supplier_request_id = newsuppreqid;
		  }
		  Vector searchdata=new Vector();
		  searchdata=getSearchData( "",supplier_request_id,"" );

		  Hashtable reqdetails = new Hashtable();
		  reqdetails = (Hashtable)searchdata.elementAt(1);

		  newsuppsession.setAttribute("REQUEST_DATA", reqdetails);

		  buildnewrequest(newsuppsession, "<FONT SIZE=\"2\" FACE=\"Arial\"><B>Successful</B></FONT>",out);
		}
		else
		{
		  buildnewrequest( newsuppsession,"<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Could Not Update. An error Occured</B></FONT><BR>"+errormsg+"",out );
		}
	  }
	  else if ( "reqdetails".equalsIgnoreCase( action ) )
	  {
		Vector searchdata=new Vector();
		searchdata=getSearchData( "",supplier_request_id,"" );

		Hashtable reqdetails = new Hashtable();
        reqdetails = (Hashtable)searchdata.elementAt(1);

		newsuppsession.setAttribute("REQUEST_DATA", reqdetails);

		buildnewrequest(newsuppsession,"",out);
	  }
	  else if("BackSearch".equalsIgnoreCase(action))
	  {
		String backstatus = newsuppsession.getAttribute("BACK_STATUS") != null ? newsuppsession.getAttribute("BACK_STATUS").toString() : "";
		String backsearchtext = newsuppsession.getAttribute("BACK_SEARCH_TEXT") != null ? newsuppsession.getAttribute("BACK_SEARCH_TEXT").toString() : "";

		buildHeader( backstatus,backsearchtext,requeststatus,out );

		//if ( (backstatus.length() >0 && !"All".equalsIgnoreCase( backstatus ) ) || backsearchtext.length() >0)
		if ( backstatus.length() >0 || backsearchtext.length() >0)
		{
		Vector searchdata=new Vector();
		searchdata=this.getSearchData( backstatus,"",backsearchtext );
		buildlistDetails( searchdata,out );
		}
		else
		{
		  out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
		}
	  }
	  else if ( "Search".equalsIgnoreCase( action ) )
	  {
		buildHeader( status,searchtext,requeststatus,out );
		Vector searchdata=new Vector();
		searchdata=this.getSearchData( status,supplier_request_id,searchtext );

		newsuppsession.setAttribute("BACK_STATUS", status);
		newsuppsession.setAttribute("BACK_SEARCH_TEXT", searchtext);

		buildlistDetails( searchdata,out );
	  }
	  else
	  {
		buildHeader( status,searchtext,requeststatus,out );
		out.println( radian.web.HTMLHelpObj.printHTMLSelect() );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	  out.println( radian.web.HTMLHelpObj.printError( "New Supplier Request",intcmIsApplication ) );
	}
	finally
	{
	  out.close();
	}
  }

private void sendnoticemail(String requestid,String reqstatus,String useraction,String requestor,String rejectreason,String notes,String suppliername,String requestorname)
  {
	String usergroupid = "";
	String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	wwwHome = wwwHome.substring(0, wwwHome.length() - 1);
	String resulturl = wwwHome + radian.web.tcmisResourceLoader.getnewsupplierurl();

	if ("Pending Payment Terms".equalsIgnoreCase(reqstatus))
	{
	  usergroupid = "NewPaymentTerms";
	}
	else if ("Pending Approval".equalsIgnoreCase(reqstatus))
	{
	  usergroupid="NewSuppApprover";
	}

	if ( "Pending Payment Terms".equalsIgnoreCase(reqstatus) || "Pending Approval".equalsIgnoreCase(reqstatus) )
	{
	String emailArray = "";
	String query="select distinct a.personnel_id,b.email from user_group_member a, personnel b where a.personnel_id = b.personnel_id and a.user_group_id = '"+usergroupid+"'";
	DBResultSet dbrs=null;
	ResultSet rs=null;
	int loop = 0;

	try
	{
	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();
	  while ( rs.next() )
	  {
		String faci=rs.getString( "email" ) != null ? rs.getString( "email" ) : "";
		if ( loop > 0 )
		{
		  emailArray+=",";
		}

	    if (faci.length() > 0 )
		{
		  emailArray += ""+faci+"";
		  loop++;
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

	String[] statusaray = new String[] {emailArray};
	radian.tcmis.server7.share.helpers.HelpObjs.javaSendMail("","New Supplier Request for "+suppliername+", Requested by "+requestorname+" -Waiting Approval",statusaray,"A new Supplier request for "+suppliername+", Requested by "+requestorname+" is waiting your approval. Please click on the link below.\n\n\n"+resulturl+"UserAction=reqdetails&supplier_request_id="+requestid+"\n\nNotes:\n"+notes+"");
	}
	else if ( "Approved".equalsIgnoreCase(reqstatus))
	{
	  radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"New Supplier Request for "+suppliername+" Approved","New Supplier Request for "+suppliername+" has been Approved. Please click on the link below to see the details.\n\n\n"+resulturl+"UserAction=reqdetails&supplier_request_id="+requestid+"",Integer.parseInt(requestor),true);
	}
	else if ( "Rejected".equalsIgnoreCase(reqstatus))
	{
	  radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"New Supplier Request for "+suppliername+" Rejected","New Supplier Request for "+suppliername+" has been Rejected.\n\n\nReason:\n"+rejectreason+" Please click on the link below to see the details.\n\n\n"+resulturl+"UserAction=reqdetails&supplier_request_id="+requestid+"",Integer.parseInt(requestor),true);
	}
  }

  private boolean UpdateDetails(Hashtable taskHd, String useraction,String personnelid)
  {
	  boolean result = false;
	  String supplier_request_id=taskHd.get( "SUPPLIER_REQUEST_ID" ) == null ? "" : taskHd.get( "SUPPLIER_REQUEST_ID" ).toString();
	  String supplier_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "SUPPLIER_NAME" ) == null ? "" : taskHd.get( "SUPPLIER_NAME" ).toString());
	  //String supplier_parent=taskHd.get( "SUPPLIER_PARENT" ) == null ? "" : taskHd.get( "SUPPLIER_PARENT" ).toString();
	  String federal_tax_id=taskHd.get( "FEDERAL_TAX_ID" ) == null ? "" : taskHd.get( "FEDERAL_TAX_ID" ).toString();
	  String small_business=taskHd.get( "SMALL_BUSINESS" ) == null ? "" : taskHd.get( "SMALL_BUSINESS" ).toString();
	  String minority_business=taskHd.get( "MINORITY_BUSINESS" ) == null ? "" : taskHd.get( "MINORITY_BUSINESS" ).toString();
	  String disadvantaged_business=taskHd.get( "DISADVANTAGED_BUSINESS" ) == null ? "" : taskHd.get( "DISADVANTAGED_BUSINESS" ).toString();
	  String default_payment_terms=taskHd.get( "DEFAULT_PAYMENT_TERMS" ) == null ? "" : taskHd.get( "DEFAULT_PAYMENT_TERMS" ).toString();
	  String supplier_notes=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "SUPPLIER_NOTES" ) == null ? "" : taskHd.get( "SUPPLIER_NOTES" ).toString());
	  String former_supplier_name=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "FORMER_SUPPLIER_NAME" ) == null ? "" : taskHd.get( "FORMER_SUPPLIER_NAME" ).toString());
	  String haas_account_number=taskHd.get( "HAAS_ACCOUNT_NUMBER" ) == null ? "" : taskHd.get( "HAAS_ACCOUNT_NUMBER" ).toString();
	  String country_abbrev=taskHd.get( "COUNTRY_ABBREV" ) == null ? "" : taskHd.get( "COUNTRY_ABBREV" ).toString();
	  String state_abbrev=taskHd.get( "STATE_ABBREV" ) == null ? "" : taskHd.get( "STATE_ABBREV" ).toString();
	  if ("None".equalsIgnoreCase(state_abbrev) || "null".equalsIgnoreCase(state_abbrev))
	  {
		  state_abbrev = "";
	  }
	  String address_line_1=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "ADDRESS_LINE_1" ) == null ? "" : taskHd.get( "ADDRESS_LINE_1" ).toString());
	  String address_line_2=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "ADDRESS_LINE_2" ) == null ? "" : taskHd.get( "ADDRESS_LINE_2" ).toString());
	  String address_line_3=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "ADDRESS_LINE_3" ) == null ? "" : taskHd.get( "ADDRESS_LINE_3" ).toString());
	  String city=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "CITY" ) == null ? "" : taskHd.get( "CITY" ).toString());
	  String zip=taskHd.get( "ZIP" ) == null ? "" : taskHd.get( "ZIP" ).toString();
	  String zip_plus=taskHd.get( "ZIP_PLUS" ) == null ? "" : taskHd.get( "ZIP_PLUS" ).toString();
	  String supplier_main_phone=taskHd.get( "SUPPLIER_MAIN_PHONE" ) == null ? "" : taskHd.get( "SUPPLIER_MAIN_PHONE" ).toString();
	  String supplier_main_fax=taskHd.get( "SUPPLIER_MAIN_FAX" ) == null ? "" : taskHd.get( "SUPPLIER_MAIN_FAX" ).toString();
	  String supplier_contact_first_name=taskHd.get( "SUPPLIER_CONTACT_FIRST_NAME" ) == null ? "" : taskHd.get( "SUPPLIER_CONTACT_FIRST_NAME" ).toString();
	  String supplier_contact_last_name=taskHd.get( "SUPPLIER_CONTACT_LAST_NAME" ) == null ? "" : taskHd.get( "SUPPLIER_CONTACT_LAST_NAME" ).toString();
	  String supplier_contact_nickname=BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get( "SUPPLIER_CONTACT_NICKNAME" ) == null ? "" : taskHd.get( "SUPPLIER_CONTACT_NICKNAME" ).toString());
	  String supplier_contact_phone=taskHd.get( "SUPPLIER_CONTACT_PHONE" ) == null ? "" : taskHd.get( "SUPPLIER_CONTACT_PHONE" ).toString();
	  String supplier_contact_phone_ext=taskHd.get( "SUPPLIER_CONTACT_PHONE_EXT" ) == null ? "" : taskHd.get( "SUPPLIER_CONTACT_PHONE_EXT" ).toString();
	  String supplier_contact_fax=taskHd.get( "SUPPLIER_CONTACT_FAX" ) == null ? "" : taskHd.get( "SUPPLIER_CONTACT_FAX" ).toString();
	  String supplier_contact_email=taskHd.get( "SUPPLIER_CONTACT_EMAIL" ) == null ? "" : taskHd.get( "SUPPLIER_CONTACT_EMAIL" ).toString();
	  String supplier_request_status=taskHd.get( "SUPPLIER_REQUEST_STATUS" ) == null ? "" : taskHd.get( "SUPPLIER_REQUEST_STATUS" ).toString();
	  String e_supplier_id=taskHd.get( "E_SUPPLIER_ID" ) == null ? "" : taskHd.get( "E_SUPPLIER_ID" ).toString();
      String requester = taskHd.get("REQUESTER")==null?"":taskHd.get("REQUESTER").toString();
	  String qualification_level=taskHd.get( "QUALIFICATION_LEVEL" ) == null ? "" : taskHd.get( "QUALIFICATION_LEVEL" ).toString();
	  String type_of_purchase=taskHd.get( "TYPE_OF_PURCHASE" ) == null ? "" : taskHd.get( "TYPE_OF_PURCHASE" ).toString();
      //String approver = taskHd.get("APPROVER")==null?"":taskHd.get("APPROVER").toString();
      String reason_for_rejection = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(taskHd.get("REASON_FOR_REJECTION")==null?"":taskHd.get("REASON_FOR_REJECTION").toString());
	  String woman_owened = taskHd.get("WOMAN_OWNED")==null?"":taskHd.get("WOMAN_OWNED").toString();
	  String querytorun="";
	  String requester_name = taskHd.get("REQUESTER_NAME")==null?"":taskHd.get("REQUESTER_NAME").toString();
	  String payment_terms_approved_on = taskHd.get("DATE_PAYMENT_TERMS_APPROVED")==null?"":taskHd.get("DATE_PAYMENT_TERMS_APPROVED").toString();
		String stateTaxId = taskHd.get("STATE_TAX_ID")==null?"":taskHd.get("STATE_TAX_ID").toString();

	  boolean sendapprovalemail = false;
	  try
	  {
		 boolean needPaymentTermsApproval = false;
		 int needPaymentTermsApprovalCount = DbHelpers.countQuery(db,"select count(*) from vv_payment_terms where PAYMENT_TERMS = '"+default_payment_terms+"' and STATUS='ACTIVE' and APPROVAL_REQUIRED='Y'");
		 if (needPaymentTermsApprovalCount > 0)
		 {
				 needPaymentTermsApproval = true;
		 }

		if("New".equalsIgnoreCase(supplier_request_id) || supplier_request_id.length() == 0)
		{
		int uid = DbHelpers.getNextVal(db,"pei_project_id_seq");
		supplier_request_id = ""+uid+"";
		newsuppreqid = ""+uid+"";
		String newPayTermsSelfApproval = "null,null";

		if (!this.getPaytermsupdateStatus() && needPaymentTermsApproval)
		{
		  supplier_request_status = "Pending Payment Terms";
		}
		else if (needPaymentTermsApproval && this.getPaytermsupdateStatus())
		{
		  supplier_request_status = "Pending Approval";
		  newPayTermsSelfApproval = "'"+personnelid+"',sysdate";
		}
		else
		{
		  supplier_request_status = "Pending Approval";
		}

		querytorun = "insert into supplier_add_request (STATE_TAX_ID,SUPPLIER_REQUEST_ID,SUPPLIER_NAME,FEDERAL_TAX_ID,SMALL_BUSINESS,MINORITY_BUSINESS,DISADVANTAGED_BUSINESS,DEFAULT_PAYMENT_TERMS,SUPPLIER_NOTES,FORMER_SUPPLIER_NAME,HAAS_ACCOUNT_NUMBER,COUNTRY_ABBREV,STATE_ABBREV,ADDRESS_LINE_1,ADDRESS_LINE_2,ADDRESS_LINE_3,CITY,ZIP,ZIP_PLUS,SUPPLIER_MAIN_PHONE,SUPPLIER_MAIN_FAX,SUPPLIER_CONTACT_FIRST_NAME,SUPPLIER_CONTACT_LAST_NAME,SUPPLIER_CONTACT_NICKNAME,SUPPLIER_CONTACT_PHONE,SUPPLIER_CONTACT_PHONE_EXT,SUPPLIER_CONTACT_FAX,SUPPLIER_CONTACT_EMAIL,E_SUPPLIER_ID,REQUESTER,QUALIFICATION_LEVEL,TYPE_OF_PURCHASE,SUPPLIER_REQUEST_STATUS,WOMAN_OWNED,PAYMENT_TERM_APPROVER,DATE_PAYMENT_TERMS_APPROVED)";
		querytorun += " values ('"+stateTaxId+"',"+supplier_request_id+",'"+supplier_name+"','"+federal_tax_id+"','"+small_business+"','"+minority_business+"','"+disadvantaged_business+"','"+default_payment_terms+"','"+supplier_notes+"',";
		querytorun += "'"+former_supplier_name+"','"+haas_account_number+"','"+country_abbrev+"','"+state_abbrev+"','"+address_line_1+"','"+address_line_2+"','"+address_line_3+"','"+city+"','"+zip+"','"+zip_plus+"','"+supplier_main_phone+"','"+supplier_main_fax+"',";
		querytorun += "'"+supplier_contact_first_name+"','"+supplier_contact_last_name+"','"+supplier_contact_nickname+"','"+supplier_contact_phone+"','"+supplier_contact_phone_ext+"','"+supplier_contact_fax+"','"+supplier_contact_email+"','"+e_supplier_id+"',";
		querytorun += "'"+personnelid+"','"+qualification_level+"','"+type_of_purchase+"','"+supplier_request_status+"','"+woman_owened+"',"+newPayTermsSelfApproval+")";
		//'"+supplier_parent+"'  SUPPLIER_PARENT='"+supplier_parent+"',
	  }
	  else
	  {
		querytorun = "update supplier_add_request set STATE_TAX_ID='"+stateTaxId+"',SUPPLIER_NAME='"+supplier_name+"',FEDERAL_TAX_ID='"+federal_tax_id+"',SMALL_BUSINESS='"+small_business+"',MINORITY_BUSINESS='"+minority_business+"',DISADVANTAGED_BUSINESS='"+disadvantaged_business+"',";
		querytorun +="DEFAULT_PAYMENT_TERMS='"+default_payment_terms+"',SUPPLIER_NOTES='"+supplier_notes+"',FORMER_SUPPLIER_NAME='"+former_supplier_name+"',HAAS_ACCOUNT_NUMBER='"+haas_account_number+"',COUNTRY_ABBREV='"+country_abbrev+"',STATE_ABBREV='"+state_abbrev+"',";
		querytorun +="ADDRESS_LINE_1='"+address_line_1+"',ADDRESS_LINE_2='"+address_line_2+"',ADDRESS_LINE_3='"+address_line_3+"',CITY='"+city+"',ZIP='"+zip+"',ZIP_PLUS='"+zip_plus+"',SUPPLIER_MAIN_PHONE='"+supplier_main_phone+"',SUPPLIER_MAIN_FAX='"+supplier_main_fax+"',";
		querytorun +="SUPPLIER_CONTACT_FIRST_NAME='"+supplier_contact_first_name+"',SUPPLIER_CONTACT_LAST_NAME='"+supplier_contact_last_name+"',SUPPLIER_CONTACT_NICKNAME='"+supplier_contact_nickname+"',SUPPLIER_CONTACT_PHONE='"+supplier_contact_phone+"',";
		querytorun +="SUPPLIER_CONTACT_PHONE_EXT='"+supplier_contact_phone_ext+"',SUPPLIER_CONTACT_FAX='"+supplier_contact_fax+"',SUPPLIER_CONTACT_EMAIL='"+supplier_contact_email+"',E_SUPPLIER_ID='"+e_supplier_id+"',";
		querytorun +="QUALIFICATION_LEVEL='"+qualification_level+"',TYPE_OF_PURCHASE='"+type_of_purchase+"',WOMAN_OWNED='"+woman_owened+"'";

		if ("save".equalsIgnoreCase(useraction))
		{
		  //if (needPaymentTermsApproval && !"Net 45".equalsIgnoreCase(default_payment_terms) && payment_terms_approved_on.length() == 0 && "Pending Approval".equalsIgnoreCase(supplier_request_status))
			if (needPaymentTermsApproval && "Pending Approval".equalsIgnoreCase(supplier_request_status))
			{
			 if (this.getPaytermsupdateStatus())
			 {
				querytorun += ",PAYMENT_TERM_APPROVER="+personnelid+",DATE_PAYMENT_TERMS_APPROVED=sysdate";
			 }
			 else
			 {
				supplier_request_status = "Pending Payment Terms";
				querytorun += ",SUPPLIER_REQUEST_STATUS='Pending Payment Terms',PAYMENT_TERM_APPROVER=null,DATE_PAYMENT_TERMS_APPROVED=null";
				sendapprovalemail = true;
			 }
			}
			else if (!needPaymentTermsApproval && "Pending Payment Terms".equalsIgnoreCase(supplier_request_status))
			{
			 supplier_request_status = "Pending Approval";
			 querytorun += ",SUPPLIER_REQUEST_STATUS='Pending Approval'";
			 if (this.getPaytermsupdateStatus())
			 {
				querytorun += ",SUPPLIER_REQUEST_STATUS='Pending Approval',PAYMENT_TERM_APPROVER="+personnelid+",DATE_PAYMENT_TERMS_APPROVED=sysdate";
			 }
			 else
			 {
				querytorun += ",PAYMENT_TERM_APPROVER=null,DATE_PAYMENT_TERMS_APPROVED=null";
			 }
			 sendapprovalemail = true;
			}
		}
		else if ("updpayterms".equalsIgnoreCase(useraction))
		{
		   supplier_request_status = "Pending Approval";
		   querytorun +=",SUPPLIER_REQUEST_STATUS='Pending Approval',PAYMENT_TERM_APPROVER='"+personnelid+"',DATE_PAYMENT_TERMS_APPROVED=sysdate ";
		}
		else if ("approve".equalsIgnoreCase(useraction))
		{
		   supplier_request_status = "Approved";
		   querytorun +=",SUPPLIER_REQUEST_STATUS='Approved',APPROVER='"+personnelid+"',DATE_APPROVED=sysdate ";
		}
		else if ("reject".equalsIgnoreCase(useraction))
		{
		   supplier_request_status = "Rejected";
		   querytorun +=",SUPPLIER_REQUEST_STATUS='Rejected',APPROVER='"+personnelid+"',REASON_FOR_REJECTION='"+reason_for_rejection+"',DATE_APPROVED=sysdate ";
		}

		querytorun = querytorun + " where SUPPLIER_REQUEST_ID='"+supplier_request_id+"'";
	  }

	  db.doUpdate(querytorun);
	  result = true;

	  Connection connect1 = null;
	  CallableStatement cs = null;
	  if ("approve".equalsIgnoreCase(useraction))
	  {
		connect1 = db.getConnection();

		cs = connect1.prepareCall("{call pkg_contract_setup.create_supplier_from_request(?,?)}");
		cs.setInt(1,Integer.parseInt(supplier_request_id));
		cs.registerOutParameter(2, Types.VARCHAR);
		cs.execute();
		String errormsg1 = cs.getString(2);

		if (errormsg1 == null) {errormsg1 = "";}
		if (errormsg1.length() > 0)
		{
		  result = false;
		  errormsg += errormsg1;
		}
		else
		{
		  result = true;
		}
		connect1.commit();
		if (cs !=null){cs.close();}
	  }

	  if ((result && !"save".equalsIgnoreCase(useraction) ) || sendapprovalemail )
	  {
		sendnoticemail( supplier_request_id,supplier_request_status,useraction,requester,reason_for_rejection,supplier_notes,supplier_name,requester_name );
	  }
  }
  catch(Exception ex)
  {
	ex.printStackTrace();
	errormsg += ex.getMessage();
	result = false;
  }

  return result;
  }


  private void buildHeader( String status,String searchtext,Hashtable requeststatus, PrintWriter suppout  )
  {
    try
    {
      suppout.println( radian.web.HTMLHelpObj.printHTMLHeader( "New Supplier Request","newsupplier.js",intcmIsApplication ) );
      suppout.println( "<BODY>\n" );

      suppout.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
      suppout.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
      suppout.println( "</DIV>\n" );
      suppout.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

      suppout.println( radian.web.HTMLHelpObj.printPurchaseOrderTitleBar( "<B>New Supplier Request</B>\n" ) );

      String servlettocall=radian.web.tcmisResourceLoader.getnewsupplierurl();
      suppout.println( "<FORM method=\"POST\" NAME=\"newsupplier\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + servlettocall + "\">\n" );
      suppout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );

      suppout.println( "<TR>\n" );
      suppout.println( "<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n" );
      suppout.println( "<B>Status:</B>&nbsp;\n" );
      suppout.println( "</TD>\n" );
      //Status
      suppout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
      suppout.println( "<SELECT CLASS=\"HEADER\" NAME=\"status\" size=\"1\">\n" );
	  suppout.println( "<OPTION VALUE=\"All\">All</OPTION>\n" );
	  suppout.println( HTMLHelpObj.sorthashbyValue( requeststatus.entrySet(),status ) );
      suppout.println( "</SELECT>\n" );
      suppout.println( "</TD>\n" );

	  suppout.println("<TD WIDTH=\"5%\" ALIGN=\"right\" CLASS=\"announce\">\n");
	  suppout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchtext\" value=\""+searchtext+"\" size=\"19\">\n");
	  suppout.println("</TD>\n");

      //Search
      suppout.println( "<TD WIDTH=\"15%\" ALIGN=\"LEFT\">\n" );
      suppout.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"List\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n" );
      suppout.println( "</TD>\n" );
      //New
      suppout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n" );
      if ( this.getupdateStatus() )
      {
        suppout.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"New\" onclick= \"return StartNew(this)\" NAME=\"SearchButton\">&nbsp;\n" );
      }
      else
      {
        suppout.println( "&nbsp;\n" );
      }
      suppout.println( "</TD>\n" );

      suppout.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n" );
      suppout.println( "&nbsp;</TD>\n" );
      suppout.println( "</TR>\n" );
      suppout.println( "</TABLE>\n" );
      suppout.println( "<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
      suppout.println( "<TR><td>" );
      suppout.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"\">\n" );
      suppout.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\"\">\n" );
      suppout.println( "</TD></TR>" );
      suppout.println( "</TABLE>\n" );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      suppout.println(radian.web.HTMLHelpObj.printError( "New Supplier Request",intcmIsApplication ) );
    }
  }

  private Hashtable SynchServerData(HttpServletRequest request, Hashtable taskHd)
  {
	String supplier_request_id=request.getParameter( "supplier_request_id" );
	if ( supplier_request_id == null )
	  supplier_request_id="";
	taskHd.remove( "SUPPLIER_REQUEST_ID" );
	taskHd.put( "SUPPLIER_REQUEST_ID",supplier_request_id.trim() );
	String supplier_name=request.getParameter( "supplier_name" );
	if ( supplier_name == null )
	  supplier_name="";
	taskHd.remove( "SUPPLIER_NAME" );
	taskHd.put( "SUPPLIER_NAME",supplier_name.trim() );
	/*String supplier_parent=request.getParameter( "supplier_parent" );
	if ( supplier_parent == null )
	  supplier_parent="";
	taskHd.remove( "SUPPLIER_PARENT" );
	taskHd.put( "SUPPLIER_PARENT",supplier_parent.trim() );*/
	String federal_tax_id=request.getParameter( "federal_tax_id" );
	if ( federal_tax_id == null )
	  federal_tax_id="";
	taskHd.remove( "FEDERAL_TAX_ID" );
	taskHd.put( "FEDERAL_TAX_ID",federal_tax_id.trim() );
	String small_business=request.getParameter( "small_business" );
	if ( small_business == null )
	  small_business="";
	taskHd.remove( "SMALL_BUSINESS" );
	taskHd.put( "SMALL_BUSINESS",small_business.trim() );
	String minority_business=request.getParameter( "minority_business" );
	if ( minority_business == null )
	  minority_business="";
	taskHd.remove( "MINORITY_BUSINESS" );
	taskHd.put( "MINORITY_BUSINESS",minority_business.trim() );
	String disadvantaged_business=request.getParameter( "disadvantaged_business" );
	if ( disadvantaged_business == null )
	  disadvantaged_business="";
	taskHd.remove( "DISADVANTAGED_BUSINESS" );
	taskHd.put( "DISADVANTAGED_BUSINESS",disadvantaged_business.trim() );
	String default_payment_terms=request.getParameter( "default_payment_terms" );
	if ( default_payment_terms == null )
	  default_payment_terms="";
	taskHd.remove( "DEFAULT_PAYMENT_TERMS" );
	taskHd.put( "DEFAULT_PAYMENT_TERMS",default_payment_terms.trim() );
	String supplier_notes=request.getParameter( "supplier_notes" );
	if ( supplier_notes == null )
	  supplier_notes="";
	taskHd.remove( "SUPPLIER_NOTES" );
	taskHd.put( "SUPPLIER_NOTES",supplier_notes.trim() );
	String former_supplier_name=request.getParameter( "former_supplier_name" );
	if ( former_supplier_name == null )
	  former_supplier_name="";
	taskHd.remove( "FORMER_SUPPLIER_NAME" );
	taskHd.put( "FORMER_SUPPLIER_NAME",former_supplier_name.trim() );
	String haas_account_number=request.getParameter( "haas_account_number" );
	if ( haas_account_number == null )
	  haas_account_number="";
	taskHd.remove( "HAAS_ACCOUNT_NUMBER" );
	taskHd.put( "HAAS_ACCOUNT_NUMBER",haas_account_number.trim() );
	String country_abbrev=request.getParameter( "country_abbrev" );
	if ( country_abbrev == null )
	  country_abbrev="";
	taskHd.remove( "COUNTRY_ABBREV" );
	taskHd.put( "COUNTRY_ABBREV",country_abbrev.trim() );
	String state_abbrev=request.getParameter( "state_abbrev" );
	if ( state_abbrev == null )
	  state_abbrev="";
	taskHd.remove( "STATE_ABBREV" );
	taskHd.put( "STATE_ABBREV",state_abbrev.trim() );
	String address_line_1=request.getParameter( "address_line_1" );
	if ( address_line_1 == null )
	  address_line_1="";
	taskHd.remove( "ADDRESS_LINE_1" );
	taskHd.put( "ADDRESS_LINE_1",address_line_1.trim() );
	String address_line_2=request.getParameter( "address_line_2" );
	if ( address_line_2 == null )
	  address_line_2="";
	taskHd.remove( "ADDRESS_LINE_2" );
	taskHd.put( "ADDRESS_LINE_2",address_line_2.trim() );
	String address_line_3=request.getParameter( "address_line_3" );
	if ( address_line_3 == null )
	  address_line_3="";
	taskHd.remove( "ADDRESS_LINE_3" );
	taskHd.put( "ADDRESS_LINE_3",address_line_3.trim() );
	String city=request.getParameter( "city" );
	if ( city == null )
	  city="";
	taskHd.remove( "CITY" );
	taskHd.put( "CITY",city.trim() );
	String zip=request.getParameter( "zip" );
	if ( zip == null )
	  zip="";
	taskHd.remove( "ZIP" );
	taskHd.put( "ZIP",zip.trim() );
	String zip_plus=request.getParameter( "zip_plus" );
	if ( zip_plus == null )
	  zip_plus="";
	taskHd.remove( "ZIP_PLUS" );
	taskHd.put( "ZIP_PLUS",zip_plus.trim() );
	String supplier_main_phone=request.getParameter( "supplier_main_phone" );
	if ( supplier_main_phone == null )
	  supplier_main_phone="";
	taskHd.remove( "SUPPLIER_MAIN_PHONE" );
	taskHd.put( "SUPPLIER_MAIN_PHONE",supplier_main_phone.trim() );
	String supplier_main_fax=request.getParameter( "supplier_main_fax" );
	if ( supplier_main_fax == null )
	  supplier_main_fax="";
	taskHd.remove( "SUPPLIER_MAIN_FAX" );
	taskHd.put( "SUPPLIER_MAIN_FAX",supplier_main_fax.trim() );
	String supplier_contact_first_name=request.getParameter( "supplier_contact_first_name" );
	if ( supplier_contact_first_name == null )
	  supplier_contact_first_name="";
	taskHd.remove( "SUPPLIER_CONTACT_FIRST_NAME" );
	taskHd.put( "SUPPLIER_CONTACT_FIRST_NAME",supplier_contact_first_name.trim() );
	String supplier_contact_last_name=request.getParameter( "supplier_contact_last_name" );
	if ( supplier_contact_last_name == null )
	  supplier_contact_last_name="";
	taskHd.remove( "SUPPLIER_CONTACT_LAST_NAME" );
	taskHd.put( "SUPPLIER_CONTACT_LAST_NAME",supplier_contact_last_name.trim() );
	String supplier_contact_nickname=request.getParameter( "supplier_contact_nickname" );
	if ( supplier_contact_nickname == null )
	  supplier_contact_nickname="";
	taskHd.remove( "SUPPLIER_CONTACT_NICKNAME" );
	taskHd.put( "SUPPLIER_CONTACT_NICKNAME",supplier_contact_nickname.trim() );
	String supplier_contact_phone=request.getParameter( "supplier_contact_phone" );
	if ( supplier_contact_phone == null )
	  supplier_contact_phone="";
	taskHd.remove( "SUPPLIER_CONTACT_PHONE" );
	taskHd.put( "SUPPLIER_CONTACT_PHONE",supplier_contact_phone.trim() );
	String supplier_contact_phone_ext=request.getParameter( "supplier_contact_phone_ext" );
	if ( supplier_contact_phone_ext == null )
	  supplier_contact_phone_ext="";
	taskHd.remove( "SUPPLIER_CONTACT_PHONE_EXT" );
	taskHd.put( "SUPPLIER_CONTACT_PHONE_EXT",supplier_contact_phone_ext.trim() );
	String supplier_contact_fax=request.getParameter( "supplier_contact_fax" );
	if ( supplier_contact_fax == null )
	  supplier_contact_fax="";
	taskHd.remove( "SUPPLIER_CONTACT_FAX" );
	taskHd.put( "SUPPLIER_CONTACT_FAX",supplier_contact_fax.trim() );
	String supplier_contact_email=request.getParameter( "supplier_contact_email" );
	if ( supplier_contact_email == null )
	  supplier_contact_email="";
	taskHd.remove( "SUPPLIER_CONTACT_EMAIL" );
	taskHd.put( "SUPPLIER_CONTACT_EMAIL",supplier_contact_email.trim() );
	String e_supplier_id=request.getParameter( "e_supplier_id" );
	if ( e_supplier_id == null )
	  e_supplier_id="";
	taskHd.remove( "E_SUPPLIER_ID" );
	taskHd.put( "E_SUPPLIER_ID",e_supplier_id.trim() );
	/*String requester=request.getParameter( "requester" );
	if ( requester == null )
	  requester="";
	taskHd.remove( "REQUESTER" );
	taskHd.put( "REQUESTER",requester.trim() );*/
	String qualification_level=request.getParameter( "qualification_level" );
	if ( qualification_level == null )
	  qualification_level="";
	taskHd.remove( "QUALIFICATION_LEVEL" );
	taskHd.put( "QUALIFICATION_LEVEL",qualification_level.trim() );
	String type_of_purchase=request.getParameter( "type_of_purchase" );
	if ( type_of_purchase == null )
	  type_of_purchase="";
	taskHd.remove( "TYPE_OF_PURCHASE" );
	taskHd.put( "TYPE_OF_PURCHASE",type_of_purchase.trim() );
	/*String approver=request.getParameter( "approver" );
	if ( approver == null )
	  approver="";
	taskHd.remove( "APPROVER" );
	taskHd.put( "APPROVER",approver.trim() );*/
	/*String supplier_request_status=request.getParameter( "supplier_request_status" );
	if ( supplier_request_status == null )
	  supplier_request_status="";
	taskHd.remove( "SUPPLIER_REQUEST_STATUS" );
	taskHd.put( "SUPPLIER_REQUEST_STATUS",supplier_request_status.trim() );*/
	/*String payment_term_approver=request.getParameter( "payment_term_approver" );
	if ( payment_term_approver == null )
	  payment_term_approver="";
	taskHd.remove( "PAYMENT_TERM_APPROVER" );
	taskHd.put( "PAYMENT_TERM_APPROVER",payment_term_approver.trim() );
	String approver_name=request.getParameter( "approver_name" );
	if ( approver_name == null )
	  approver_name="";
	taskHd.remove( "APPROVER_NAME" );
	taskHd.put( "APPROVER_NAME",approver_name.trim() );
	String payment_term_approver_name=request.getParameter( "payment_term_approver_name" );
	if ( payment_term_approver_name == null )
	  payment_term_approver_name="";
	taskHd.remove( "PAYMENT_TERM_APPROVER_NAME" );
	taskHd.put( "PAYMENT_TERM_APPROVER_NAME",payment_term_approver_name.trim() );
	String requester_name=request.getParameter( "requester_name" );
	if ( requester_name == null )
	  requester_name="";
	taskHd.remove( "REQUESTER_NAME" );
	taskHd.put( "REQUESTER_NAME",requester_name.trim() );*/
	String reason_for_rejection=request.getParameter( "rejectreasons" );
	if ( reason_for_rejection == null )
	  reason_for_rejection="";
	if ( reason_for_rejection.equalsIgnoreCase(null) )
	  reason_for_rejection="";
	taskHd.remove( "REASON_FOR_REJECTION" );
	taskHd.put( "REASON_FOR_REJECTION",reason_for_rejection.trim() );

	String woman_owened=request.getParameter( "woman_owened" );
	if ( woman_owened == null )
	  woman_owened="";
	taskHd.remove( "WOMAN_OWNED" );
	taskHd.put( "WOMAN_OWNED",woman_owened.trim() );

	String stateTaxId=request.getParameter( "stateTaxId" );
	if ( stateTaxId == null )
		stateTaxId="";
	taskHd.remove( "STATE_TAX_ID" );
	taskHd.put( "STATE_TAX_ID",stateTaxId.trim() );

	return taskHd;
  }

  private Vector getSearchData( String status,String requestid,String searchtxt ) throws Exception
  {
	Vector Data=new Vector();
	Hashtable DataH=new Hashtable();
	Hashtable summary=new Hashtable();
	boolean falgforand = false;
	DBResultSet dbrs=null;
	ResultSet rs=null;

	int count=0;
	summary.put( "TOTAL_NUMBER",new Integer( count ) );
	Data.addElement( summary );

	int num_rec=0;

	try
	{
	  String query="select * from supplier_add_request_view ";
	  String whereclause = "";

	  if ( requestid.trim().length() != 0 && !"New".equalsIgnoreCase(requestid))
	  {
		whereclause+=" SUPPLIER_REQUEST_ID = '" + requestid + "' ";
		falgforand=true;
	  }
	  else
	  {
		if ( status.trim().length() != 0 && !"All".equalsIgnoreCase( status ) )
		{
		  if ( falgforand )
			whereclause+=" and SUPPLIER_REQUEST_STATUS = '" + status + "' ";
		  else
		  {
			whereclause+=" SUPPLIER_REQUEST_STATUS = '" + status + "' ";
			falgforand=true;
		  }
		}

	  if ( searchtxt.trim().length() != 0 )
	  {
		if ( falgforand )
		  whereclause+=" and lower(REQUESTER_NAME || SUPPLIER_NAME || FORMER_SUPPLIER_NAME) like lower('%" + searchtxt + "%') ";
		else
		{
		  whereclause+=" lower(REQUESTER_NAME || SUPPLIER_NAME || FORMER_SUPPLIER_NAME) like lower('%" + searchtxt + "%') ";
		  falgforand=true;
		}
	  }
	  }

	  if (whereclause.length() > 0)
	  {
		query+=" where " + whereclause;
	  }

	  dbrs=db.doQuery( query );
	  rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		  DataH=new Hashtable();
		  num_rec++;
		  DataH.put("SUPPLIER_REQUEST_ID",rs.getString("SUPPLIER_REQUEST_ID")==null?"":rs.getString("SUPPLIER_REQUEST_ID"));
		  DataH.put("SUPPLIER_NAME",rs.getString("SUPPLIER_NAME")==null?"":rs.getString("SUPPLIER_NAME"));
		  DataH.put("SUPPLIER_PARENT",rs.getString("SUPPLIER_PARENT")==null?"":rs.getString("SUPPLIER_PARENT"));
		  DataH.put("FEDERAL_TAX_ID",rs.getString("FEDERAL_TAX_ID")==null?"":rs.getString("FEDERAL_TAX_ID"));
		  DataH.put("SMALL_BUSINESS",rs.getString("SMALL_BUSINESS")==null?"":rs.getString("SMALL_BUSINESS"));
		  DataH.put("MINORITY_BUSINESS",rs.getString("MINORITY_BUSINESS")==null?"":rs.getString("MINORITY_BUSINESS"));
		  DataH.put("DISADVANTAGED_BUSINESS",rs.getString("DISADVANTAGED_BUSINESS")==null?"":rs.getString("DISADVANTAGED_BUSINESS"));
		  DataH.put("DEFAULT_PAYMENT_TERMS",rs.getString("DEFAULT_PAYMENT_TERMS")==null?"":rs.getString("DEFAULT_PAYMENT_TERMS"));
		  DataH.put("SUPPLIER_NOTES",rs.getString("SUPPLIER_NOTES")==null?"":rs.getString("SUPPLIER_NOTES"));
		  DataH.put("FORMER_SUPPLIER_NAME",rs.getString("FORMER_SUPPLIER_NAME")==null?"":rs.getString("FORMER_SUPPLIER_NAME"));
		  DataH.put("HAAS_ACCOUNT_NUMBER",rs.getString("HAAS_ACCOUNT_NUMBER")==null?"":rs.getString("HAAS_ACCOUNT_NUMBER"));
		  DataH.put("COUNTRY_ABBREV",rs.getString("COUNTRY_ABBREV")==null?"":rs.getString("COUNTRY_ABBREV"));
		  DataH.put("STATE_ABBREV",rs.getString("STATE_ABBREV")==null?"":rs.getString("STATE_ABBREV"));
		  DataH.put("ADDRESS_LINE_1",rs.getString("ADDRESS_LINE_1")==null?"":rs.getString("ADDRESS_LINE_1"));
		  DataH.put("ADDRESS_LINE_2",rs.getString("ADDRESS_LINE_2")==null?"":rs.getString("ADDRESS_LINE_2"));
		  DataH.put("ADDRESS_LINE_3",rs.getString("ADDRESS_LINE_3")==null?"":rs.getString("ADDRESS_LINE_3"));
		  DataH.put("CITY",rs.getString("CITY")==null?"":rs.getString("CITY"));
		  DataH.put("ZIP",rs.getString("ZIP")==null?"":rs.getString("ZIP"));
		  DataH.put("ZIP_PLUS",rs.getString("ZIP_PLUS")==null?"":rs.getString("ZIP_PLUS"));
		  DataH.put("SUPPLIER_MAIN_PHONE",rs.getString("SUPPLIER_MAIN_PHONE")==null?"":rs.getString("SUPPLIER_MAIN_PHONE"));
		  DataH.put("SUPPLIER_MAIN_FAX",rs.getString("SUPPLIER_MAIN_FAX")==null?"":rs.getString("SUPPLIER_MAIN_FAX"));
		  DataH.put("SUPPLIER_CONTACT_FIRST_NAME",rs.getString("SUPPLIER_CONTACT_FIRST_NAME")==null?"":rs.getString("SUPPLIER_CONTACT_FIRST_NAME"));
		  DataH.put("SUPPLIER_CONTACT_LAST_NAME",rs.getString("SUPPLIER_CONTACT_LAST_NAME")==null?"":rs.getString("SUPPLIER_CONTACT_LAST_NAME"));
		  DataH.put("SUPPLIER_CONTACT_NICKNAME",rs.getString("SUPPLIER_CONTACT_NICKNAME")==null?"":rs.getString("SUPPLIER_CONTACT_NICKNAME"));
		  DataH.put("SUPPLIER_CONTACT_PHONE",rs.getString("SUPPLIER_CONTACT_PHONE")==null?"":rs.getString("SUPPLIER_CONTACT_PHONE"));
		  DataH.put("SUPPLIER_CONTACT_PHONE_EXT",rs.getString("SUPPLIER_CONTACT_PHONE_EXT")==null?"":rs.getString("SUPPLIER_CONTACT_PHONE_EXT"));
		  DataH.put("SUPPLIER_CONTACT_FAX",rs.getString("SUPPLIER_CONTACT_FAX")==null?"":rs.getString("SUPPLIER_CONTACT_FAX"));
		  DataH.put("SUPPLIER_CONTACT_EMAIL",rs.getString("SUPPLIER_CONTACT_EMAIL")==null?"":rs.getString("SUPPLIER_CONTACT_EMAIL"));
		  DataH.put("SUPPLIER_REQUEST_STATUS",rs.getString("SUPPLIER_REQUEST_STATUS")==null?"":rs.getString("SUPPLIER_REQUEST_STATUS"));
		  DataH.put("E_SUPPLIER_ID",rs.getString("E_SUPPLIER_ID")==null?"":rs.getString("E_SUPPLIER_ID"));
		  DataH.put("REQUESTER",rs.getString("REQUESTER")==null?"":rs.getString("REQUESTER"));

		  DataH.put("QUALIFICATION_LEVEL",rs.getString("QUALIFICATION_LEVEL")==null?"":rs.getString("QUALIFICATION_LEVEL"));
		  DataH.put("TYPE_OF_PURCHASE",rs.getString("TYPE_OF_PURCHASE")==null?"":rs.getString("TYPE_OF_PURCHASE"));
		  //DataH.put("APPROVER",rs.getString("APPROVER")==null?"":rs.getString("APPROVER"));
		  //DataH.put("PAYMENT_TERM_APPROVER",rs.getString("PAYMENT_TERM_APPROVER")==null?"":rs.getString("PAYMENT_TERM_APPROVER"));
		  DataH.put("APPROVER_NAME",rs.getString("APPROVER_NAME")==null?"":rs.getString("APPROVER_NAME"));
		  DataH.put("PAYMENT_TERM_APPROVER_NAME",rs.getString("PAYMENT_TERM_APPROVER_NAME")==null?"":rs.getString("PAYMENT_TERM_APPROVER_NAME"));
		  DataH.put("REQUESTER_NAME",rs.getString("REQUESTER_NAME")==null?"":rs.getString("REQUESTER_NAME"));
		  DataH.put("REASON_FOR_REJECTION",rs.getString("REASON_FOR_REJECTION")==null?"":rs.getString("REASON_FOR_REJECTION"));
		  DataH.put("WOMAN_OWNED",rs.getString("WOMAN_OWNED")==null?"":rs.getString("WOMAN_OWNED"));
		  DataH.put("DATE_APPROVED",rs.getString("DATE_APPROVED")==null?"":rs.getString("DATE_APPROVED"));
		  DataH.put("DATE_PAYMENT_TERMS_APPROVED",rs.getString("DATE_PAYMENT_TERMS_APPROVED")==null?"":rs.getString("DATE_PAYMENT_TERMS_APPROVED"));
		  DataH.put("TRANSACTION_DATE",rs.getString("TRANSACTION_DATE")==null?"":rs.getString("TRANSACTION_DATE"));
			DataH.put("STATE_TAX_ID",rs.getString("STATE_TAX_ID")==null?"":rs.getString("STATE_TAX_ID"));

		  Data.addElement( DataH );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		throw e;
	  }
	  finally
	  {
		if (dbrs != null ) {dbrs.close();}
	  }

	  Hashtable recsum=new Hashtable();
	  recsum.put( "TOTAL_NUMBER",new Integer( num_rec ) );
	  Data.setElementAt( recsum,0 );
	  Data.trimToSize();
	  return Data;
	}

	public void buildlistDetails( Vector data,PrintWriter suppout )
	{
	  try
	  {
	  Hashtable summary=new Hashtable();
	  summary= ( Hashtable ) data.elementAt( 0 );
	  int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();
	  String servlettocall=radian.web.tcmisResourceLoader.getnewsupplierurl();

	  suppout.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );

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
			suppout.println( "<tr align=\"center\" valign=\"middle\">\n" );
			suppout.println( "<TH width=\"2%\"  height=\"38\">Request ID</TH>\n" );
			suppout.println( "<TH width=\"5%\"  height=\"38\">Status</TH>\n" );
			suppout.println( "<TH width=\"5%\"  height=\"38\">Requestor</TH>\n" );
			suppout.println( "<TH width=\"25%\"  height=\"38\">Supplier Name</TH>\n" );
			suppout.println( "<TH width=\"5%\"  height=\"38\">Entered On</TH>\n" );
			suppout.println( "<TH width=\"5%\"  height=\"38\">Payment Terms Approved By</TH>\n" );
			suppout.println( "<TH width=\"5%\"  height=\"38\">Payment Terms Approved On</TH>\n" );
			suppout.println( "<TH width=\"5%\"  height=\"38\">Approved By</TH>\n" );
			suppout.println( "<TH width=\"5%\"  height=\"38\">Approved On</TH>\n" );
			suppout.println( "</tr>\n" );
		  }

		  Hashtable hD=new Hashtable();
		  hD= ( Hashtable ) data.elementAt( i );

		  String Color=" ";
		  if ( i % 2 == 0 )
		  {
			Color="CLASS=\"blue";
		  }
		  else
		  {
			Color="CLASS=\"white";
		  }

		  suppout.println( "<tr align=\"center\"  valign=\"middle\">\n" );
		  suppout.println("<TD WIDTH=\"2%\" HEIGHT=\"38\" "+Color+"l\">");
		  String supplierreqid = ( hD.get( "SUPPLIER_REQUEST_ID" ) == null ? "" : hD.get( "SUPPLIER_REQUEST_ID" ).toString() );
		  suppout.println("<A HREF=\""+servlettocall+"UserAction=reqdetails&supplier_request_id="+supplierreqid+"\">"+supplierreqid+"</A></TD>");
		  suppout.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "SUPPLIER_REQUEST_STATUS" ).toString() + "</td>\n" );
		  suppout.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "REQUESTER_NAME" ).toString() + "</td>\n" );
		  suppout.println( "<td " + Color + "l\" width=\"25%\" height=\"38\">" + hD.get( "SUPPLIER_NAME" ).toString() + "</td>\n" );
		  suppout.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "TRANSACTION_DATE" ).toString() + "</td>\n" );
		  suppout.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "PAYMENT_TERM_APPROVER_NAME" ).toString() + "</td>\n" );
		  suppout.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "DATE_PAYMENT_TERMS_APPROVED" ).toString() + "</td>\n" );
		  suppout.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "APPROVER_NAME" ).toString() + "</td>\n" );
		  suppout.println( "<td " + Color + "l\" width=\"5%\" height=\"38\">" + hD.get( "DATE_APPROVED" ).toString() + "</td>\n" );

		  suppout.println( "</tr>\n" );
		}

		if (i == 0)
		{
		  suppout.println(radian.web.HTMLHelpObj.printMessageinTable("No Requests Found"));
	    }

		suppout.println( "</table>\n" );
		suppout.println( "</body></html>\n" );
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		suppout.println(radian.web.HTMLHelpObj.printHTMLError());
	  }
	}

	public void buildnewrequest (HttpSession suppsession, String message,PrintWriter suppout)
	{
	  Hashtable taskHd= ( Hashtable ) suppsession.getAttribute( "REQUEST_DATA" );
	  Hashtable vvstatus= ( Hashtable ) suppsession.getAttribute( "REQUEST_STATUS" );
	  Vector vvPayment= ( Vector ) suppsession.getAttribute( "PO_VV_PAYMENT" );
	  Hashtable vvqualification= ( Hashtable ) suppsession.getAttribute( "SUPP_QUALIFICATION" );
	  Hashtable vvpurchtype= ( Hashtable ) suppsession.getAttribute( "PURCHASE_TYPE" );
	  Hashtable vvcountrystate= ( Hashtable ) suppsession.getAttribute( "STATE_COUNTRY" );
	  Hashtable countrylist = (Hashtable) vvcountrystate.get("COUNTRY");

	  try
	  {
	  String supplier_request_id = taskHd.get("SUPPLIER_REQUEST_ID")==null?"&nbsp;":taskHd.get("SUPPLIER_REQUEST_ID").toString();
	  String supplier_name = taskHd.get("SUPPLIER_NAME")==null?"&nbsp;":taskHd.get("SUPPLIER_NAME").toString();
	  //String supplier_parent = taskHd.get("SUPPLIER_PARENT")==null?"&nbsp;":taskHd.get("SUPPLIER_PARENT").toString();
	  String federal_tax_id = taskHd.get("FEDERAL_TAX_ID")==null?"&nbsp;":taskHd.get("FEDERAL_TAX_ID").toString();
	  String small_business = taskHd.get("SMALL_BUSINESS")==null?"&nbsp;":taskHd.get("SMALL_BUSINESS").toString();
	  String minority_business = taskHd.get("MINORITY_BUSINESS")==null?"&nbsp;":taskHd.get("MINORITY_BUSINESS").toString();
	  String disadvantaged_business = taskHd.get("DISADVANTAGED_BUSINESS")==null?"&nbsp;":taskHd.get("DISADVANTAGED_BUSINESS").toString();
	  String default_payment_terms = taskHd.get("DEFAULT_PAYMENT_TERMS")==null?"&nbsp;":taskHd.get("DEFAULT_PAYMENT_TERMS").toString();
	  String supplier_notes = taskHd.get("SUPPLIER_NOTES")==null?"&nbsp;":taskHd.get("SUPPLIER_NOTES").toString();
	  String former_supplier_name = taskHd.get("FORMER_SUPPLIER_NAME")==null?"&nbsp;":taskHd.get("FORMER_SUPPLIER_NAME").toString();
	  String haas_account_number = taskHd.get("HAAS_ACCOUNT_NUMBER")==null?"&nbsp;":taskHd.get("HAAS_ACCOUNT_NUMBER").toString();
	  String country_abbrev = taskHd.get("COUNTRY_ABBREV")==null?"&nbsp;":taskHd.get("COUNTRY_ABBREV").toString();
	  String state_abbrev = taskHd.get("STATE_ABBREV")==null?"&nbsp;":taskHd.get("STATE_ABBREV").toString();
	  String address_line_1 = taskHd.get("ADDRESS_LINE_1")==null?"&nbsp;":taskHd.get("ADDRESS_LINE_1").toString();
	  String address_line_2 = taskHd.get("ADDRESS_LINE_2")==null?"&nbsp;":taskHd.get("ADDRESS_LINE_2").toString();
	  String address_line_3 = taskHd.get("ADDRESS_LINE_3")==null?"&nbsp;":taskHd.get("ADDRESS_LINE_3").toString();
	  String city = taskHd.get("CITY")==null?"&nbsp;":taskHd.get("CITY").toString();
	  String zip = taskHd.get("ZIP")==null?"&nbsp;":taskHd.get("ZIP").toString();
	  String zip_plus = taskHd.get("ZIP_PLUS")==null?"&nbsp;":taskHd.get("ZIP_PLUS").toString();
	  String supplier_main_phone = taskHd.get("SUPPLIER_MAIN_PHONE")==null?"&nbsp;":taskHd.get("SUPPLIER_MAIN_PHONE").toString();
	  String supplier_main_fax = taskHd.get("SUPPLIER_MAIN_FAX")==null?"&nbsp;":taskHd.get("SUPPLIER_MAIN_FAX").toString();
	  String supplier_contact_first_name = taskHd.get("SUPPLIER_CONTACT_FIRST_NAME")==null?"&nbsp;":taskHd.get("SUPPLIER_CONTACT_FIRST_NAME").toString();
	  String supplier_contact_last_name = taskHd.get("SUPPLIER_CONTACT_LAST_NAME")==null?"&nbsp;":taskHd.get("SUPPLIER_CONTACT_LAST_NAME").toString();
	  String supplier_contact_nickname = taskHd.get("SUPPLIER_CONTACT_NICKNAME")==null?"&nbsp;":taskHd.get("SUPPLIER_CONTACT_NICKNAME").toString();
	  String supplier_contact_phone = taskHd.get("SUPPLIER_CONTACT_PHONE")==null?"&nbsp;":taskHd.get("SUPPLIER_CONTACT_PHONE").toString();
	  String supplier_contact_phone_ext = taskHd.get("SUPPLIER_CONTACT_PHONE_EXT")==null?"&nbsp;":taskHd.get("SUPPLIER_CONTACT_PHONE_EXT").toString();
	  String supplier_contact_fax = taskHd.get("SUPPLIER_CONTACT_FAX")==null?"&nbsp;":taskHd.get("SUPPLIER_CONTACT_FAX").toString();
	  String supplier_contact_email = taskHd.get("SUPPLIER_CONTACT_EMAIL")==null?"&nbsp;":taskHd.get("SUPPLIER_CONTACT_EMAIL").toString();
	  String supplier_request_status = taskHd.get("SUPPLIER_REQUEST_STATUS")==null?"&nbsp;":taskHd.get("SUPPLIER_REQUEST_STATUS").toString();
	  String e_supplier_id = taskHd.get("E_SUPPLIER_ID")==null?"&nbsp;":taskHd.get("E_SUPPLIER_ID").toString();
	  //String requester = taskHd.get("REQUESTER")==null?"&nbsp;":taskHd.get("REQUESTER").toString();

	  String qualification_level = taskHd.get("QUALIFICATION_LEVEL")==null?"&nbsp;":taskHd.get("QUALIFICATION_LEVEL").toString();
	  String type_of_purchase = taskHd.get("TYPE_OF_PURCHASE")==null?"&nbsp;":taskHd.get("TYPE_OF_PURCHASE").toString();
	  //String approver = taskHd.get("APPROVER")==null?"&nbsp;":taskHd.get("APPROVER").toString();
	  //String payment_term_approver = taskHd.get("PAYMENT_TERM_APPROVER")==null?"&nbsp;":taskHd.get("PAYMENT_TERM_APPROVER").toString();
	  String approver_name = taskHd.get("APPROVER_NAME")==null?"&nbsp;":taskHd.get("APPROVER_NAME").toString();
	  String payment_term_approver_name = taskHd.get("PAYMENT_TERM_APPROVER_NAME")==null?"&nbsp;":taskHd.get("PAYMENT_TERM_APPROVER_NAME").toString();
	  String requester_name = taskHd.get("REQUESTER_NAME")==null?"&nbsp;":taskHd.get("REQUESTER_NAME").toString();
	  String reason_for_rejection = taskHd.get("REASON_FOR_REJECTION")==null?"&nbsp;":taskHd.get("REASON_FOR_REJECTION").toString();
	  String woman_owened = taskHd.get("WOMAN_OWNED")==null?"":taskHd.get("WOMAN_OWNED").toString();
	  String payment_terms_approved_on = taskHd.get("DATE_PAYMENT_TERMS_APPROVED")==null?"":taskHd.get("DATE_PAYMENT_TERMS_APPROVED").toString();
	  String date_approved = taskHd.get("DATE_APPROVED")==null?"":taskHd.get("DATE_APPROVED").toString();
		String stateTaxId = taskHd.get("STATE_TAX_ID")==null?"":taskHd.get("STATE_TAX_ID").toString();

	  suppout.println( radian.web.HTMLHelpObj.printHTMLHeader( "New Supplier Request","newsupplier.js",intcmIsApplication ) );
	  suppout.println( "<BODY>\n" );

	  suppout.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	  suppout.println(radian.web.JSHelpObj.createCountryStateJs(vvcountrystate));
	  suppout.println("// -->\n </SCRIPT>\n");

	  suppout.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
	  suppout.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
	  suppout.println( "</DIV>\n" );
	  suppout.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );

	  suppout.println( radian.web.HTMLHelpObj.printPurchaseOrderTitleBar( "<B>New Supplier Request</B>\n" ) );
	  String servlettocall=radian.web.tcmisResourceLoader.getnewsupplierurl();
	  suppout.println( "<FORM method=\"POST\" NAME=\"newsupplier\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + servlettocall + "\">\n" );
	  if (message.length() > 0 )
	  {
		suppout.println( HTMLHelpObj.printMessageinTable( message ) );
	  }

	  suppout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"5\" WIDTH=\"800\" CLASS=\"moveup\">\n" );
	  suppout.println( "<TR>\n" );

	  suppout.println( "<TD WIDTH=\"5%\" CLASS=\"white\">\n" );
	  if ( (this.getupdateStatus() || this.getaccountdetails()) && !( "Created".equalsIgnoreCase(supplier_request_status) || "Rejected".equalsIgnoreCase(supplier_request_status) ) )
	  {
		if("New".equalsIgnoreCase(supplier_request_id) || supplier_request_id.length() == 0)
		{
		  suppout.println( "<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"Submit\" NAME=\"NewtskButton\" onclick= \"return newrequest(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;</TD>\n" );
		}
		else
		{
		  suppout.println( "<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"Save\" NAME=\"NewtskButton\" onclick= \"return update(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;</TD>\n" );
		}
	  }
	  suppout.println( "</TD>\n" );

	  suppout.println( "<TD CLASS=\"white\" WIDTH=\"5%\" VALIGN=\"MIDDLE\">\n" );

	  if ("Pending Payment Terms".equalsIgnoreCase(supplier_request_status) && this.getPaytermsupdateStatus())
	  {
	  suppout.println( "<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"Approve Payment Terms\" NAME=\"NewtskButton\" onclick= \"return updpayterms(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  }
	  suppout.println( "</TD>\n" );

	  suppout.println( "<TD WIDTH=\"5%\" CLASS=\"white\">\n" );
	  if ("Pending Approval".equalsIgnoreCase(supplier_request_status) && this.getaccountdetails())
	  {
		suppout.println( "<INPUT TYPE=\"submit\" ID=\"approvebudtton\" VALUE=\"Approve\" NAME=\"approveButton\" onclick= \"return approve(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  }
	  suppout.println("</TD>\n");

	  suppout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\">\n");
	  if ( (this.getupdateStatus() || this.getaccountdetails() || this.getPaytermsupdateStatus()) && !("New".equalsIgnoreCase(supplier_request_id) || "Rejected".equalsIgnoreCase(supplier_request_status) || "Created".equalsIgnoreCase(supplier_request_status)) )
	  {
		suppout.println( "<INPUT TYPE=\"submit\" ID=\"rejectbhutton1\" VALUE=\"Reject\" NAME=\"rejectbhutton\" onclick= \"return reject(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  }
	  suppout.println("</TD>\n");
	  suppout.println( "<TD WIDTH=\"5%\" CLASS=\"whitel\">\n");
	  suppout.println( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"submit\" ID=\"gbacksearchutton1\" VALUE=\"Return to List\" NAME=\"backskButton\" onclick= \"return backsearch(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
	  suppout.println("</TD>\n");
	  suppout.println( "</TR>\n" );
	  suppout.println( "</TABLE>\n" );

	  suppout.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"5\" WIDTH=\"800\" CLASS=\"moveup\">\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Requester:</B></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" CLASS=\"bluel\">" + requester_name + "</TD>\n" );
	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Request Status:</B></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" CLASS=\"bluel\">\n" );
	  suppout.println( supplier_request_status );
	  //suppout.println( "<SELECT CLASS=\"HEADER\" NAME=\"status\" size=\"1\" DISABLED>\n" );
	  //suppout.println( "<OPTION VALUE=\"\"></OPTION>\n" );
	  //suppout.println( HTMLHelpObj.sorthashbyValue( vvstatus.entrySet(),supplier_request_status ) );
	  //suppout.println( "</SELECT>\n" );
	  suppout.println( "</TD>\n" );
	  suppout.println( "</TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println( "<TD COLSPAN=\"4\">");
	  if ("Pending Approval".equalsIgnoreCase(supplier_request_status) && payment_term_approver_name.length() > 0)
	  {
		 suppout.println( "<B>Payment Terms Approved By:</B> "+payment_term_approver_name+" on "+payment_terms_approved_on+"\n" );
	  }
	  else if ("Rejected".equalsIgnoreCase(supplier_request_status))
	  {
		 suppout.println( "<B>Rejected By:</B> "+approver_name+" on "+date_approved+"\n" );
		 if (reason_for_rejection.length() > 0)
		 {
		  suppout.println( "<br><B>Comments for Rejection:</B> "+reason_for_rejection+"\n" );
		 }
	  }
	  else if ("Created".equalsIgnoreCase(supplier_request_status))
	  {
		if (payment_term_approver_name.length() > 0)
		{
		  suppout.println( "<B>Payment Terms Approved By:</B> " + payment_term_approver_name + " on " + payment_terms_approved_on + "<BR>\n" );
		}
		suppout.println( "<B>Approved By:</B> "+approver_name+" on "+date_approved+"\n" );
	  }

	  suppout.println( "</TD>\n" );
	  suppout.println( "</TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println( "<TD COLSPAN=\"4\"><BR><b>&#150;&#150;&#150;&#150;&#150;Address&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;</B></TD>");
	  suppout.println( "</TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println("<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Legal Comp Name:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n");
	  suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_name\" value=\""+supplier_name+"\" SIZE=\"40\" MAXLENGTH=\"50\"></TD>\n");
	  suppout.println("<TD WIDTH=\"8%\" CLASS=\"whiter\"></TD>\n");
	  suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\">\n");
	  //suppout.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_parent\" value=\""+supplier_parent+"\" SIZE=\"20\" MAXLENGTH=\"30\">\n");
	  suppout.println( "&nbsp;</TD>\n" );
	  suppout.println( "</TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Country:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" COLSPAN=\"3\" CLASS=\"whitel\">\n" );
	  if ( country_abbrev.trim().length() == 0 )
	  {
		country_abbrev="USA";
	  }
	  suppout.println("<SELECT CLASS=\"HEADER\"  NAME=\"country_abbrev\" size=\"1\" OnChange=\"countrychanged(document.newsupplier.country_abbrev)\">\n");
	  suppout.println(radian.web.HTMLHelpObj.sorthashbyValue(countrylist.entrySet(),country_abbrev));
	  suppout.println("</SELECT>\n");
	  //suppout.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"country_abbrev\" value=\"\" SIZE=\"5\">\n" );
	  suppout.println( "</TD>\n" );
	  suppout.println( "<TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println("<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Address:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n");
	  suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"address_line_1\" value=\""+address_line_1+"\" SIZE=\"30\" MAXLENGTH=\"40\"></TD>\n");
	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>City:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"city\" value=\""+city+"\" SIZE=\"20\" MAXLENGTH=\"30\"></TD>\n" );
	  suppout.println( "</TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"address_line_2\" value=\""+address_line_2+"\" SIZE=\"30\" MAXLENGTH=\"40\"></TD>\n" );

	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>State/Province:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\">\n" );
	  suppout.println( "<SELECT CLASS=\"HEADER\"  NAME=\"state_abbrev\" size=\"1\">\n" );
	  suppout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	  Hashtable fh= ( Hashtable ) vvcountrystate.get( country_abbrev );
	  Vector invidV= ( Vector ) fh.get( "STATE_ABBREV" );
	  Vector invidName= ( Vector ) fh.get( "STATE" );

	  for ( int i=0; i < invidV.size(); i++ )
	  {
		String preSelect="";
		String wacid= ( String ) invidV.elementAt( i );
		String invgName= ( String ) invidName.elementAt( i );

		if ( state_abbrev.equalsIgnoreCase( wacid ) )
		{
		  preSelect="selected";
		}
		suppout.println( "<option " + preSelect + " value=\"" + wacid + "\"> " + invgName + "</option>\n" );
	  }
	  suppout.println( "</SELECT>\n" );
	  //suppout.println( "<INPUT type=\"text\" CLASS=\"HEADER\" name=\"state_abbrev\" value=\"\" SIZE=\"5\">\n" );
	  suppout.println( "</TD>\n" );
	  suppout.println( "</TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"address_line_3\" value=\""+address_line_3+"\" SIZE=\"30\" MAXLENGTH=\"40\"></TD>\n" );

	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Zip:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	  suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"zip\" value=\""+zip+"\" SIZE=\"5\" MAXLENGTH=\"10\">\n" );
	  suppout.println( "-<INPUT type=\"text\" CLASS=\"HEADER\" name=\"zip_plus\" value=\""+zip_plus+"\" SIZE=\"5\" MAXLENGTH=\"4\"></TD>\n" );

	  suppout.println( "</TR>\n" );

	  suppout.println( "<TR>\n" );
	  suppout.println( "<TD COLSPAN=\"4\"><BR><b>&#150;&#150;&#150;&#150;&#150;Contact&nbsp;Info&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150</B></TD>");
	  suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Main Phone:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	suppout.println( "<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_main_phone\" value=\""+supplier_main_phone+"\" SIZE=\"15\"></TD>\n" );
	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Main Fax:</B></TD>\n" );
	suppout.println( "<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_main_fax\" value=\""+supplier_main_fax+"\" SIZE=\"15\"></TD>\n" );
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Contact First Name:</B></TD>\n" );
	suppout.println( "<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_first_name\" value=\""+supplier_contact_first_name+"\" SIZE=\"20\"></TD>\n" );

	suppout.println("<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Contact Email:</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_email\" value=\""+supplier_contact_email+"\" SIZE=\"20\"></TD>\n");

	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Contact Last Name</B></TD>\n" );
	suppout.println( "<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_last_name\" value=\""+supplier_contact_last_name+"\" SIZE=\"20\"></TD>\n" );

	suppout.println("<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Contact Phone:</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_phone\" value=\""+supplier_contact_phone+"\" SIZE=\"15\">&nbsp;&nbsp;&nbsp;&nbsp;Ext:\n");
	suppout.println("<INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_phone_ext\" value=\""+supplier_contact_phone_ext+"\" SIZE=\"3\"></TD>\n");

	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println("<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Contact Nickname</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_nickname\" value=\""+supplier_contact_nickname+"\" SIZE=\"20\"></TD>\n");

	suppout.println("<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Contact Fax:</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_fax\" value=\""+supplier_contact_fax+"\" SIZE=\"15\"></TD>\n");
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println( "<TD COLSPAN=\"4\"><BR><b>&#150;&#150;&#150;&#150;&#150;Business&nbsp;Info&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;</B></TD>");
	suppout.println( "</TR>\n" );


	suppout.println( "<TR>\n" );
	suppout.println("<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Federal Tax Id:<BR>(or Owner's SSN)</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"federal_tax_id\" value=\""+federal_tax_id+"\" SIZE=\"25\"></TD>\n");

	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><INPUT type=\"checkbox\" name=\"small_business\" value=\"Y\" " + ( small_business.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">\n" );
	suppout.println( "</TD>\n" );
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><B>Small Business</B></TD>\n");
	suppout.println( "</TD>\n" );
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println("<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>State Tax Id:</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"stateTaxId\" value=\""+stateTaxId+"\" SIZE=\"25\"></TD>\n");

	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\">&nbsp;\n" );
	suppout.println( "</TD>\n" );
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"></TD>\n");
	suppout.println( "</TD>\n" );
	suppout.println( "</TR>\n" );

  	suppout.println( "<TR>\n" );
	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Default Payment Terms:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\">\n" );
	suppout.println( "<SELECT CLASS=\"HEADER\" NAME=\"default_payment_terms\" ID=\"default_payment_terms\" size=\"1\">\n" );
	suppout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	suppout.println( radian.web.HTMLHelpObj.getDropDown( vvPayment,default_payment_terms ) );
	suppout.println( "</SELECT>\n<BR>STD TERMS ARE N45, Exceptions must be approved by your Supervisor. Document discussions in Notes below." );
	suppout.println( "</TD>\n" );

	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\">\n" );
	suppout.println( "<INPUT type=\"checkbox\" name=\"minority_business\" value=\"Y\" " + ( minority_business.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">\n" );
	suppout.println( "</TD>\n" );
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><B>Minority-Owned SBE</B></TD>\n");
	suppout.println( "</TD>\n" );
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Type of Purchase:<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\">\n" );
	suppout.println( "<SELECT CLASS=\"HEADER\" NAME=\"type_of_purchase\" ID=\"type_of_purchase\" size=\"1\">\n" );
	suppout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	suppout.println( HTMLHelpObj.sorthashbyValue( vvpurchtype.entrySet(),type_of_purchase ) );
	suppout.println( "</SELECT>\n<BR>If selecting 'Other', explain in Notes below." );
	suppout.println( "</TD>\n" );

	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\">\n" );
	suppout.println( "<INPUT type=\"checkbox\" name=\"disadvantaged_business\" value=\"Y\" " + ( disadvantaged_business.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">\n" );
	suppout.println( "</TD>\n" );
	suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><B>Disadvantaged Business</B></TD>\n");
	suppout.println( "</TD>\n" );
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Qualification Level:<BR>Supplier Rating<span style='font-size:12.0pt;color:red'>*</span></B></TD>\n" );
	suppout.println( "<TD WIDTH=\"15%\" CLASS=\"whitel\">\n" );
	suppout.println( "<SELECT CLASS=\"HEADER\" NAME=\"qualification_level\" ID=\"qualification_level\" size=\"1\">\n" );
	suppout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	suppout.println( HTMLHelpObj.sorthashbyValue( vvqualification.entrySet(),qualification_level ) );
	suppout.println( "</SELECT>\n" );
	suppout.println( "</TD>\n" );

	  suppout.println( "<TD WIDTH=\"8%\" CLASS=\"whiter\">\n" );
	  suppout.println( "<INPUT type=\"checkbox\" name=\"woman_owened\" value=\"Y\" " + ( woman_owened.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">\n" );
	  suppout.println( "</TD>\n" );
	  suppout.println("<TD WIDTH=\"15%\" CLASS=\"whitel\"><B>Woman-Owned SBE</B></TD>\n");
	  suppout.println( "</TD>\n" );

	suppout.println( "</TR>\n" );


	suppout.println( "<TR>\n" );
	suppout.println( "<TD COLSPAN=\"4\"><BR><b>&#150;&#150;&#150;&#150;&#150;Accounting&nbsp;Info&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;</B></TD>");
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println("<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>E Supplier Id:</B></TD>\n");
	if ("Pending Approval".equalsIgnoreCase(supplier_request_status) && this.getaccountdetails())
	{
	  suppout.println( "<TD WIDTH=\"15%\" COLSPAN=\"3\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"e_supplier_id\" value=\"" + e_supplier_id +	 "\" SIZE=\"25\"></TD>\n" );
	}
	else
	{
	  suppout.println( "<TD WIDTH=\"15%\" COLSPAN=\"3\" CLASS=\"bluel\">" + e_supplier_id + "</TD>\n" );
	}
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println("<TD WIDTH=\"8%\" CLASS=\"bluer\"><B>Haas Account Number:</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" COLSPAN=\"3\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"haas_account_number\" value=\""+haas_account_number+"\" SIZE=\"25\"></TD>\n");
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println( "<TD COLSPAN=\"4\"><BR><b>&#150;&#150;&#150;&#150;&#150;Misc&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;&#150;</TD>");
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println("<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Former Supplier Name:</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" COLSPAN=\"3\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"former_supplier_name\" value=\""+former_supplier_name+"\" SIZE=\"25\"></TD>\n");
	suppout.println( "</TR>\n" );

	suppout.println( "<TR>\n" );
	suppout.println("<TD WIDTH=\"8%\" CLASS=\"whiter\"><B>Notes:</B></TD>\n");
	suppout.println("<TD WIDTH=\"15%\" COLSPAN=\"3\" CLASS=\"whitel\"><TEXTAREA name=\"supplier_notes\" rows=\"10\" cols=\"80\">"+supplier_notes+"</TEXTAREA></TD>\n");
	suppout.println( "</TR>\n" );

	suppout.println( "</TABLE>\n" );

	suppout.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"\">\n" );
	suppout.println( "<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\"\">\n" );
	suppout.println( "<INPUT TYPE=\"hidden\" NAME=\"supplier_request_id\" VALUE=\""+supplier_request_id+"\">\n");
    suppout.println( "<INPUT TYPE=\"hidden\" NAME=\"rejectreasons\" VALUE=\"\">\n" );
	suppout.println( "</FORM>\n\n" );

  }
	catch (Exception e)
	{
		e.printStackTrace();
		suppout.println(radian.web.HTMLHelpObj.printError("New Supplier Request",intcmIsApplication));
	}
  }

	/*public StringBuffer buildrequestdetails ( Vector data )
	{
	  StringBuffer Msgd=new StringBuffer();
	  String Color = "";
	  Hashtable DataH = new Hashtable();

	  suppout.println( "<TD WIDTH=\"15%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_request_id\" value=\"" );
	  suppout.println( DataH.get( "SUPPLIER_REQUEST_ID" ) );
	  suppout.println( "\" SIZE=\"5\"></TD>\n" );
	  suppout.println( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_name\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_NAME" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_parent\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_PARENT" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"federal_tax_id\" value=\"" );
	  Msgd.append( DataH.get( "FEDERAL_TAX_ID" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"small_business\" value=\"" );
	  Msgd.append( DataH.get( "SMALL_BUSINESS" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"minority_business\" value=\"" );
	  Msgd.append( DataH.get( "MINORITY_BUSINESS" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"disadvantaged_business\" value=\"" );
	  Msgd.append( DataH.get( "DISADVANTAGED_BUSINESS" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"default_payment_terms\" value=\"" );
	  Msgd.append( DataH.get( "DEFAULT_PAYMENT_TERMS" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_notes\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_NOTES" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"former_supplier_name\" value=\"" );
	  Msgd.append( DataH.get( "FORMER_SUPPLIER_NAME" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"haas_account_number\" value=\"" );
	  Msgd.append( DataH.get( "HAAS_ACCOUNT_NUMBER" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"country_abbrev\" value=\"" );
	  Msgd.append( DataH.get( "COUNTRY_ABBREV" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"state_abbrev\" value=\"" );
	  Msgd.append( DataH.get( "STATE_ABBREV" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"address_line_1\" value=\"" );
	  Msgd.append( DataH.get( "ADDRESS_LINE_1" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"address_line_2\" value=\"" );
	  Msgd.append( DataH.get( "ADDRESS_LINE_2" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"address_line_3\" value=\"" );
	  Msgd.append( DataH.get( "ADDRESS_LINE_3" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"city\" value=\"" );
	  Msgd.append( DataH.get( "CITY" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"zip\" value=\"" );
	  Msgd.append( DataH.get( "ZIP" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"zip_plus\" value=\"" );
	  Msgd.append( DataH.get( "ZIP_PLUS" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_main_phone\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_MAIN_PHONE" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_main_fax\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_MAIN_FAX" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_first_name\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_CONTACT_FIRST_NAME" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_last_name\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_CONTACT_LAST_NAME" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_nickname\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_CONTACT_NICKNAME" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_phone\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_CONTACT_PHONE" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_phone_ext\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_CONTACT_PHONE_EXT" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_fax\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_CONTACT_FAX" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_contact_email\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_CONTACT_EMAIL" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"supplier_request_status\" value=\"" );
	  Msgd.append( DataH.get( "SUPPLIER_REQUEST_STATUS" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"e_supplier_id\" value=\"" );
	  Msgd.append( DataH.get( "E_SUPPLIER_ID" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );
	  Msgd.append( "<TD WIDTH=\"10%\" " + Color + "l\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"requester\" value=\"" );
	  Msgd.append( DataH.get( "REQUESTER" ) );
	  Msgd.append( "\" SIZE=\"5\"></TD>\n" );

	  return Msgd;
	}*/
}