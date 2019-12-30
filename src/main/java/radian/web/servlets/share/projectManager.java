// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 12/13/2004 5:16:10 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3)
// Source File Name:   mytaskManager.java

package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HTMLHelpObj;
import radian.web.HeaderFooter;
import radian.web.vvHelpObj;

public class projectManager
{
  private ServerResourceBundle bundle=null;
  private TcmISDBModel db = null;
  private static org.apache.log4j.Logger projlog = org.apache.log4j.Logger.getLogger(projectManager.class);
  private boolean allowupdate;
  private String errormsg = "";
  private boolean intcmIsApplication = false;

  public void setupdateStatus( boolean id )
  {
	this.allowupdate=id;
  }

  private boolean getupdateStatus() throws Exception
  {
	return this.allowupdate;
  }

  public projectManager( ServerResourceBundle b,TcmISDBModel d )
  {
	bundle=b;
	db=d;
  }

    public void doResult(HttpServletRequest request, HttpServletResponse response, HttpSession tsksession1)
        throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        PersonnelBean personnelBean = (PersonnelBean) tsksession1.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

		String personnelid= tsksession1.getAttribute( "PERSONNELID" ) == null ? "" : tsksession1.getAttribute( "PERSONNELID" ).toString();

        String searchthis = request.getParameter("searchthis");
        if(searchthis == null)
            searchthis = "";
        searchthis = searchthis.trim();
        String User_Action = request.getParameter("Action");
        if(User_Action == null)
            User_Action = "";
        User_Action = User_Action.trim();
        String owenedby = request.getParameter("owenedby");
        if(owenedby == null)
            owenedby = personnelid;
        owenedby = owenedby.trim();
        String tsktype = request.getParameter("tsktype");
        if(tsktype == null)
            tsktype = "";
        tsktype = tsktype.trim();
        String status = request.getParameter("status");
        if(status == null)
            status = "";
        status = status.trim();
        String searchtext = request.getParameter("searchtext");
        if(searchtext == null)
            searchtext = "";
        searchtext = searchtext.trim();
        String prority = request.getParameter("prority");
        if(prority == null)
            prority = "";
        prority = prority.trim();
        String sortby = request.getParameter("sortby");
        if(sortby == null)
            sortby = "";
        sortby = sortby.trim();
        String projectid = request.getParameter("projectid");
        if(projectid == null)
            projectid = "";
        projectid = projectid.trim();
        String facilityid = request.getParameter("facilityid");
        if(facilityid == null)
            facilityid = "";
        facilityid = facilityid.trim();

		String showonly_bestpractice = request.getParameter("showonly_bestpractice");
		if(showonly_bestpractice == null)
		  showonly_bestpractice = "";
		showonly_bestpractice = showonly_bestpractice.trim();

		String[] keywordsearch= {"All Statuses"};
		keywordsearch=request.getParameterValues( "keywordsearch" );
		Vector keywordsearchV = new Vector();
		String keywordsrhstg = "";

		if ( keywordsearch != null )
		{
		  for ( int loop=0; loop < keywordsearch.length; loop++ )
		  {
			if ( loop > 0 )
			{
			  keywordsrhstg+=",";
			}
			keywordsrhstg+="'" + keywordsearch[loop] + "'";
			keywordsearchV.add( keywordsearch[loop] );
		  }
		}

	    String keywordandoror = request.getParameter("keywordandoror");
	    if(keywordandoror == null)
		  keywordandoror = "or";
		keywordandoror = keywordandoror.trim();

		projlog.debug("User_Action  "+User_Action+"");

        try
        {
            String donevvstuff = tsksession1.getAttribute("VVTASKSTUFF") != null ? tsksession1.getAttribute("VVTASKSTUFF").toString() : "";
            if(!donevvstuff.equalsIgnoreCase("Yes"))
            {
                tsksession1.setAttribute("PROJECT_PRIORITY", vvHelpObj.getvvprjpriorityType(db));
                tsksession1.setAttribute("PROJECT_STATUS", vvHelpObj.getvvprjstatusType(db));
                tsksession1.setAttribute("PROJECT_CATEGORY", vvHelpObj.getvvtaskType(db));
                tsksession1.setAttribute("PROJECT_OWNERS", vvHelpObj.getvvprojectpersonnel(db));
                tsksession1.setAttribute("SORT_BY", vvHelpObj.getprjvvsortby());
                tsksession1.setAttribute("PROJECT_FACILITY", vvHelpObj.getprjfacilities(db));
                tsksession1.setAttribute("VVTASKSTUFF", "Yes");
				tsksession1.setAttribute("PROJECT_KEYWORDS", vvHelpObj.getvvkeywords(db));
            }

            if("newtask".equalsIgnoreCase(User_Action))
            {
                Hashtable newTaskH = new Hashtable();
				newTaskH.put("PROJECT_ID","");
				newTaskH.put("PROJECT_NAME","");
				newTaskH.put("PROJECT_DESC","");
				newTaskH.put("PROJECT_CATEGORY","");
				newTaskH.put("PROJECT_MANAGER_ID",personnelid);
				newTaskH.put("FACILITY_ID","");
				newTaskH.put("APPROVED_DATE","");
				newTaskH.put("START_DATE","");
				newTaskH.put("PROJECTED_FINISTED_DATE","");
				newTaskH.put("POJECTED_COST","");
				newTaskH.put("ACTUAL_COST","");
				newTaskH.put("PROJECTED_SAVING_YEAR_1","");
				newTaskH.put("PROJECTED_SAVING_YEAR_2","");
				newTaskH.put("PROJECTED_SAVING_YEAR_3","");
				newTaskH.put("PROJECTED_SAVING_YEAR_4","");
				newTaskH.put("PROJECTED_SAVING_YEAR_5","");
				newTaskH.put("ACTUAL_SAVING_YEAR_1","");
				newTaskH.put("ACTUAL_SAVING_YEAR_2","");
				newTaskH.put("ACTUAL_SAVING_YEAR_3","");
				newTaskH.put("ACTUAL_SAVING_YEAR_4","");
				newTaskH.put("ACTUAL_SAVING_YEAR_5","");
				newTaskH.put("COMMENTS","");
				newTaskH.put("BEST_PRACTICE","");
				newTaskH.put("PROJECT_STATUS","");
				newTaskH.put("PRIORITY","");
				Vector keywordV = new Vector();
				newTaskH.put("KEYWORDS",keywordV);
				newTaskH.put("ACTUAL_FINISH_DATE","");
				newTaskH.put("CUSTOMER_CONTACT","");
				newTaskH.put("CUSTOMER_CONTACT_MANAGER","");

                tsksession1.setAttribute("PROJECT_DATA", newTaskH);
                out.println("");
                buildnewTask(tsksession1, "",out);
            }
			else if("taskdetails".equalsIgnoreCase(User_Action))
            {
                Vector taskdetaiV = getResult("", "", "", "", "", "", projectid, facilityid,showonly_bestpractice,keywordsearchV,keywordandoror,keywordsrhstg);

                Hashtable summary = new Hashtable();
                summary = (Hashtable)taskdetaiV.elementAt(0);
                int total = ((Integer)summary.get("TOTAL_NUMBER")).intValue();
                Hashtable taskdetails = new Hashtable();
                taskdetails = (Hashtable)taskdetaiV.elementAt(1);

                tsksession1.setAttribute("PROJECT_DATA", taskdetails);
                buildnewTask(tsksession1, "",out);
            }
			else if("entertask".equalsIgnoreCase(User_Action) || "updatetask".equalsIgnoreCase(User_Action))
            {
                Hashtable retrn_data = (Hashtable)tsksession1.getAttribute("PROJECT_DATA");
                Hashtable synch_data = SynchServerData(request, retrn_data);
                tsksession1.setAttribute("PROJECT_DATA", synch_data);
                if(UpdateDetails(synch_data, User_Action))
				{
				  buildnewTask(tsksession1, "<FONT SIZE=\"2\" FACE=\"Arial\"><B>Successfull</B></FONT>",out);
				}
				else
                {
				  buildnewTask( tsksession1,"<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Could Not Update. An error Occured</B></FONT><BR>"+errormsg+"",out );
				}
            }
			else if("Search".equalsIgnoreCase(User_Action))
            {
                buildHeader("", owenedby, tsktype, status, searchtext, prority, sortby, facilityid, tsksession1,out,User_Action,showonly_bestpractice,keywordsearchV,keywordandoror);
                tsksession1.setAttribute("BACK_OWENED", owenedby);
                tsksession1.setAttribute("BACK_PROJECT_TYPE", tsktype);
                tsksession1.setAttribute("BACK_STATUS", status);
                tsksession1.setAttribute("BACK_SEARCHTEXT", searchtext);
                tsksession1.setAttribute("BACK_PRIORITY", prority);
                tsksession1.setAttribute("BACK_SORTBY", sortby);
                tsksession1.setAttribute("BACK_DEP", facilityid);
                try
                {
                    Vector OpenOrders = getResult(owenedby, tsktype, status, searchtext, prority, sortby, "", facilityid,showonly_bestpractice,keywordsearchV,keywordandoror,keywordsrhstg);
                    tsksession1.setAttribute("PROJECT_LIST", OpenOrders);
                    Hashtable sum = (Hashtable)OpenOrders.elementAt(0);
                    int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();
                    if(count == 0)
                        out.println(HTMLHelpObj.printHTMLNoData("No Items Found"));
                    else
                        buildprojectlist(OpenOrders, tsksession1, User_Action,out);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    out.println("An Error Occured While Querying the Databse");
                }
            }
			else if("createxlsrpt".equalsIgnoreCase(User_Action))
            {
                Vector OpendbOrders = (Vector)tsksession1.getAttribute("PROJECT_LIST");
                out.println(buildXlsDetails(OpendbOrders, tsksession1, User_Action,personnelid));
            }
			else if("printpdf".equalsIgnoreCase(User_Action))
            {
		      Vector retrn_data= ( Vector ) tsksession1.getAttribute( "PROJECT_LIST" );
		      Vector print_data=SynchprintData( request,retrn_data );
		      tsksession1.setAttribute("PROJECT_LIST", print_data);

			  buildHeader("", owenedby, tsktype, status, searchtext, prority, sortby, facilityid, tsksession1,out,User_Action,showonly_bestpractice,keywordsearchV,keywordandoror);
			  buildprojectlist(print_data, tsksession1, User_Action,out);
            }
			else if("prinprojectlist".equalsIgnoreCase(User_Action))
			{
			  Vector retrn_data= ( Vector ) tsksession1.getAttribute( "PROJECT_LIST" );
			  out.println(radian.web.HTMLHelpObj.labelredirection(buildprojectpdf(retrn_data,tsksession1)));
			}
			else if("printtask".equalsIgnoreCase(User_Action))
			{
			  Vector new_data=new Vector();
			  Hashtable summary = new Hashtable();
			  int count = 1;
			  summary.put("TOTAL_NUMBER", new Integer(count));
			  new_data.addElement(summary);

			  Hashtable taskdetails = ( Hashtable ) tsksession1.getAttribute( "PROJECT_DATA" );
			  taskdetails.put("USERCHK","Y");
			  new_data.addElement(taskdetails);

		      out.println(radian.web.HTMLHelpObj.labelredirection(buildprojectpdf(new_data,tsksession1)));
			}
			else if("BackSearch".equalsIgnoreCase(User_Action))
            {
                String owenedby1 = tsksession1.getAttribute("BACK_OWENED") != null ? tsksession1.getAttribute("BACK_OWENED").toString() : "";
                String tsktype1 = tsksession1.getAttribute("BACK_PROJECT_TYPE") != null ? tsksession1.getAttribute("BACK_PROJECT_TYPE").toString() : "";
                String status1 = tsksession1.getAttribute("BACK_STATUS") != null ? tsksession1.getAttribute("BACK_STATUS").toString() : "";
                String searchtext1 = tsksession1.getAttribute("BACK_SEARCHTEXT") != null ? tsksession1.getAttribute("BACK_SEARCHTEXT").toString() : "";
                String prority1 = tsksession1.getAttribute("BACK_PRIORITY") != null ? tsksession1.getAttribute("BACK_PRIORITY").toString() : "";
                String sortby1 = tsksession1.getAttribute("BACK_SORTBY") != null ? tsksession1.getAttribute("BACK_SORTBY").toString() : "";
                String facilityid1 = tsksession1.getAttribute("BACK_DEP") != null ? tsksession1.getAttribute("BACK_DEP").toString() : "";

                buildHeader("", owenedby1, tsktype1, status1, searchtext1, prority1, sortby1, facilityid1, tsksession1,out,User_Action,showonly_bestpractice,keywordsearchV,keywordandoror);
                try
                {
                    Vector OpenOrders = getResult(owenedby1, tsktype1, status1, searchtext1, prority1, sortby1, "", facilityid1,showonly_bestpractice,keywordsearchV,keywordandoror,keywordsrhstg);
                    tsksession1.setAttribute("PROJECT_LIST", OpenOrders);
                    Hashtable sum = (Hashtable)OpenOrders.elementAt(0);
                    int count = ((Integer)sum.get("TOTAL_NUMBER")).intValue();
                    if(count == 0)
                        out.println(HTMLHelpObj.printHTMLNoData("No Items Found"));
                    else
                        buildprojectlist(OpenOrders, tsksession1, User_Action,out);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    out.println("An Error Occured While Querying the Databse");
                }
            }
			else
			{
                buildHeader("", owenedby, tsktype, status, searchtext, prority, sortby, facilityid, tsksession1,out,User_Action,showonly_bestpractice,keywordsearchV,keywordandoror);
                out.println(HTMLHelpObj.printHTMLSelect());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            out.println("</BODY></HTML>\n");
            out.close();
        }
    }

	public String buildprojectpdf( Vector AllTheData,HttpSession tasksession ) throws Exception
	{
	  ACJEngine en=new ACJEngine();
	  en.setDebugMode( true );
	  en.setX11GfxAvailibility( false );
	  en.setTargetOutputDevice( ACJConstants.PDF );

	  Random rand=new Random();
	  int tmpReq=rand.nextInt();
	  Integer tmpReqNum=new Integer( tmpReq );

	  String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	  String url=wwwHome + "labels/projects" + tmpReqNum.toString() + ".pdf";

	  ACJOutputProcessor eClient=new ACJOutputProcessor();
	  String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
	  String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
	  String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
	  //en.setCacheOption( true,"" + writefilepath + "projects" + tmpReqNum.toString() + ".joi" );
	  eClient.setPathForFontMapFile( fontmappath );

	  try
	  {
		en.readTemplate( "" + templatpath + "Projects.erw" );
	  }
	  catch ( Exception e )
	  {
		System.out.println( "ERROR loading template" );
		e.printStackTrace();
		throw e;
	  }

	  TemplateManager tm=en.getTemplateManager();

	  try
	  {
		AppDataHandler ds=new AppDataHandler();
		RegisterTable[] rt=getData( AllTheData,tasksession );
		for ( int i=0; i < rt.length; i++ )
		{
		  Vector v1=rt[i].getData();
		  Vector v2=rt[i].getMethods();
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
		System.out.println( "\n\n---------ERROR: While generating report" );
		ex.printStackTrace();
		throw ex;
	  }

	  try
	  {
		eClient.setPDFProperty( "FileName","" + writefilepath + "projects" + tmpReqNum.toString() + ".pdf" );
		eClient.generatePDF();
	  }
	  catch ( Exception e )
	  {
		System.out.println( "\n\n---------ERROR generating report" );
		e.printStackTrace();
		throw e;
	  }

	  return url;
	}

	public RegisterTable[] getData( Vector reportData1,HttpSession tasksession ) throws Exception
	{
	  RegisterTable[] rt=new RegisterTable[1];

	  try
	  {
		rt[0]=new RegisterTable( projectData.getVector( reportData1,tasksession ),"PROJECTDATA",projectData.getFieldVector(),null );
	  }
	  catch ( Exception e1 )
	  {
		e1.printStackTrace();
		throw e1;
	  }
	  return rt;
	}

    private boolean UpdateDetails(Hashtable taskHd, String useraction)
    {
        boolean result = false;
		String project_id=taskHd.get( "PROJECT_ID" ) == null ? "" : taskHd.get( "PROJECT_ID" ).toString();
		String project_name=taskHd.get( "PROJECT_NAME" ) == null ? "" : taskHd.get( "PROJECT_NAME" ).toString();
		String project_desc=taskHd.get( "PROJECT_DESC" ) == null ? "" : taskHd.get( "PROJECT_DESC" ).toString();
		String project_category=taskHd.get( "PROJECT_CATEGORY" ) == null ? "" : taskHd.get( "PROJECT_CATEGORY" ).toString();
		String project_manager_id=taskHd.get( "PROJECT_MANAGER_ID" ) == null ? "" : taskHd.get( "PROJECT_MANAGER_ID" ).toString();
		String facility_id=taskHd.get( "FACILITY_ID" ) == null ? "" : taskHd.get( "FACILITY_ID" ).toString();
		String approved_date=taskHd.get( "APPROVED_DATE" ) == null ? "" : taskHd.get( "APPROVED_DATE" ).toString();
		String start_date=taskHd.get( "START_DATE" ) == null ? "" : taskHd.get( "START_DATE" ).toString();
		String projected_finisted_date=taskHd.get( "PROJECTED_FINISTED_DATE" ) == null ? "" : taskHd.get( "PROJECTED_FINISTED_DATE" ).toString();
		String pojected_cost=taskHd.get( "POJECTED_COST" ) == null ? "" : taskHd.get( "POJECTED_COST" ).toString();
		String actual_cost=taskHd.get( "ACTUAL_COST" ) == null ? "" : taskHd.get( "ACTUAL_COST" ).toString();
		String projected_saving_year_1=taskHd.get( "PROJECTED_SAVING_YEAR_1" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_1" ).toString();
		String projected_saving_year_2=taskHd.get( "PROJECTED_SAVING_YEAR_2" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_2" ).toString();
		String projected_saving_year_3=taskHd.get( "PROJECTED_SAVING_YEAR_3" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_3" ).toString();
		String projected_saving_year_4=taskHd.get( "PROJECTED_SAVING_YEAR_4" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_4" ).toString();
		String projected_saving_year_5=taskHd.get( "PROJECTED_SAVING_YEAR_5" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_5" ).toString();
		String actual_saving_year_1=taskHd.get( "ACTUAL_SAVING_YEAR_1" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_1" ).toString();
		String actual_saving_year_2=taskHd.get( "ACTUAL_SAVING_YEAR_2" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_2" ).toString();
		String actual_saving_year_3=taskHd.get( "ACTUAL_SAVING_YEAR_3" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_3" ).toString();
		String actual_saving_year_4=taskHd.get( "ACTUAL_SAVING_YEAR_4" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_4" ).toString();
		String actual_saving_year_5=taskHd.get( "ACTUAL_SAVING_YEAR_5" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_5" ).toString();
		String comments=taskHd.get( "COMMENTS" ) == null ? "" : taskHd.get( "COMMENTS" ).toString();
		String best_practice=taskHd.get( "BEST_PRACTICE" ) == null ? "" : taskHd.get( "BEST_PRACTICE" ).toString();
		String project_status=taskHd.get( "PROJECT_STATUS" ) == null ? "" : taskHd.get( "PROJECT_STATUS" ).toString();
		String priority=taskHd.get( "PRIORITY" ) == null ? "" : taskHd.get( "PRIORITY" ).toString();

		Vector seleckeywords = new Vector();
		seleckeywords = (Vector) taskHd.get( "KEYWORDS" );
		if (seleckeywords == null) {seleckeywords = new Vector();}

		String actual_finish_date =taskHd.get( "ACTUAL_FINISH_DATE" ) == null ? "" : taskHd.get( "ACTUAL_FINISH_DATE" ).toString();
		String customercontact =taskHd.get( "CUSTOMER_CONTACT_ID" ) == null ? "" : taskHd.get( "CUSTOMER_CONTACT_ID" ).toString();

	    project_name = HTMLHelpObj.changeSingleQuotetoTwoSingleQuote(project_name);
        project_desc = HTMLHelpObj.changeSingleQuotetoTwoSingleQuote(project_desc);
		//keywords = HTMLHelpObj.changeSingleQuotetoTwoSingleQuote(keywords);
		comments = HTMLHelpObj.changeSingleQuotetoTwoSingleQuote(comments);

		String querytorun="";
		if ( start_date.trim().length() == 0 )
		  start_date="NULL";
		else
		  start_date="to_date('" + start_date + "','MM/DD/YYYY')";

		if ( projected_finisted_date.trim().length() == 0 )
		  projected_finisted_date="NULL";
		else
		  projected_finisted_date="to_date('" + projected_finisted_date + "','MM/DD/YYYY')";

		if ( actual_finish_date.trim().length() == 0 )
		  actual_finish_date="NULL";
		else
		  actual_finish_date="to_date('" + actual_finish_date + "','MM/DD/YYYY')";

		if ( approved_date.trim().length() == 0 )
		  approved_date="NULL";
		else
            approved_date = "to_date('"+approved_date+"','MM/DD/YYYY')";


		try
        {

        if("entertask".equalsIgnoreCase(useraction))
        {
		  int uid = DbHelpers.getNextVal(db,"pei_project_id_seq");
		  project_id = ""+uid+"";

		  querytorun = "insert into PEI_PROJECT (PROJECT_ID,PROJECT_NAME,PROJECT_DESC,PROJECT_CATEGORY,PROJECT_MANAGER_ID,FACILITY_ID,APPROVED_DATE,START_DATE,PROJECTED_FINISTED_DATE,POJECTED_COST,ACTUAL_COST,PROJECTED_SAVING_YEAR_1,PROJECTED_SAVING_YEAR_2,PROJECTED_SAVING_YEAR_3,PROJECTED_SAVING_YEAR_4,PROJECTED_SAVING_YEAR_5,ACTUAL_SAVING_YEAR_1,ACTUAL_SAVING_YEAR_2,ACTUAL_SAVING_YEAR_3,ACTUAL_SAVING_YEAR_4,ACTUAL_SAVING_YEAR_5,COMMENTS,BEST_PRACTICE,PROJECT_STATUS,ACTUAL_FINISH_DATE,PRIORITY,CUSTOMER_CONTACT_ID)";
		  querytorun = querytorun + " values ("+project_id+",'"+project_name+"','"+project_desc+"','"+project_category+"','"+project_manager_id+"','"+facility_id+"',"+approved_date+","+start_date+","+projected_finisted_date+",'"+pojected_cost+"','"+actual_cost+"','"+projected_saving_year_1+"','"+projected_saving_year_2+"','"+projected_saving_year_3+"','"+projected_saving_year_4+"','"+projected_saving_year_5+"','"+actual_saving_year_1+"','"+actual_saving_year_2+"','"+actual_saving_year_3+"','"+actual_saving_year_4+"','"+actual_saving_year_5+"','"+comments+"','"+best_practice+"','"+project_status+"',"+actual_finish_date+",'"+priority+"','"+customercontact+"')";
        }
		else
        {
		  querytorun = "update PEI_PROJECT set PROJECT_NAME='"+project_name+"',PROJECT_DESC='"+project_desc+"',PROJECT_CATEGORY='"+project_category+"',PROJECT_MANAGER_ID='"+project_manager_id+"',FACILITY_ID='"+facility_id+"',APPROVED_DATE="+approved_date+",START_DATE="+start_date+",PROJECTED_FINISTED_DATE="+projected_finisted_date+",POJECTED_COST='"+pojected_cost+"',ACTUAL_COST='"+actual_cost+"',PROJECTED_SAVING_YEAR_1='"+projected_saving_year_1+"',PROJECTED_SAVING_YEAR_2='"+projected_saving_year_2+"',PROJECTED_SAVING_YEAR_3='"+projected_saving_year_3+"',PROJECTED_SAVING_YEAR_4='"+projected_saving_year_4+"',PROJECTED_SAVING_YEAR_5='"+projected_saving_year_5+"',";
		  querytorun = querytorun + "ACTUAL_SAVING_YEAR_1='"+actual_saving_year_1+"',ACTUAL_SAVING_YEAR_2='"+actual_saving_year_2+"',ACTUAL_SAVING_YEAR_3='"+actual_saving_year_3+"',ACTUAL_SAVING_YEAR_4='"+actual_saving_year_4+"',ACTUAL_SAVING_YEAR_5='"+actual_saving_year_5+"',COMMENTS='"+comments+"',BEST_PRACTICE='"+best_practice+"',PROJECT_STATUS='"+project_status+"',ACTUAL_FINISH_DATE="+actual_finish_date+",PRIORITY='"+priority+"',CUSTOMER_CONTACT_ID='"+customercontact+"'";
		  querytorun = querytorun + " where PROJECT_ID='"+project_id+"'";
        }

	  db.doUpdate(querytorun);
	  result = true;
	  result = Updatekeywords(seleckeywords,project_id);
	}
	catch(Exception ex)
	{
	  ex.printStackTrace();
	  errormsg = ex.getMessage();
	  result = false;
	}
	return result;
  }

	private boolean Updatekeywords( Vector keyData,String projectid )
	{
	  boolean result=false;

	  try
	  {
		db.doUpdate( "delete from PEI_PROJECT_KEYWORD where PROJECT_ID = " + projectid + "" );
		for ( int id=0; id < keyData.size(); id++ )
		{
		  String querytorun="insert into PEI_PROJECT_KEYWORD (PROJECT_ID, KEYWORD_ID)";
		  querytorun=querytorun + " values (" + projectid + ",'" + keyData.get( id ) + "')";

		  db.doUpdate( querytorun );
		}
		result=true;
	  }
	  catch ( Exception ex )
	  {
		ex.printStackTrace();
		errormsg=ex.getMessage();
		result=false;
	  }
	  return result;
	}

	private Vector SynchprintData( HttpServletRequest request,Vector in_data )
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

		  String usercheck=request.getParameter( "usercheck"+i+"" );
		  if ( usercheck == null )
			usercheck="";

		  projlog.debug("Line_Check   "+usercheck+"     i   "+i+"");

		  hD.remove( "USERCHK" );
		  hD.put( "USERCHK",usercheck );

		  new_data.addElement( hD );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
	  }
	  return new_data;
	}

    private Hashtable SynchServerData(HttpServletRequest request, Hashtable taskHd)
    {
        try
        {
		  String project_id =  request.getParameter("project_id");
		  if ( project_id == null )
			project_id="";
		  taskHd.remove( "PROJECT_ID" );
		  taskHd.put( "PROJECT_ID",project_id.trim() );
		  String project_name=request.getParameter( "project_name" );
		  if ( project_name == null )
			project_name="";
		  taskHd.remove( "PROJECT_NAME" );
		  taskHd.put( "PROJECT_NAME",project_name.trim() );
		  String project_desc=request.getParameter( "project_desc" );
		  if ( project_desc == null )
			project_desc="";
		  taskHd.remove( "PROJECT_DESC" );
		  taskHd.put( "PROJECT_DESC",project_desc.trim() );
		  String project_category=request.getParameter( "project_category" );
		  if ( project_category == null )
			project_category="";
		  taskHd.remove( "PROJECT_CATEGORY" );
		  taskHd.put( "PROJECT_CATEGORY",project_category.trim() );
		  String project_manager_id=request.getParameter( "project_manager_id" );
		  if ( project_manager_id == null )
			project_manager_id="";
		  taskHd.remove( "PROJECT_MANAGER_ID" );
		  taskHd.put( "PROJECT_MANAGER_ID",project_manager_id.trim() );
		  String facility_id=request.getParameter( "facility_id" );
		  if ( facility_id == null )
			facility_id="";
		  taskHd.remove( "FACILITY_ID" );
		  taskHd.put( "FACILITY_ID",facility_id.trim() );
		  String approved_date=request.getParameter( "approved_date" );
		  if ( approved_date == null )
			approved_date="";
		  taskHd.remove( "APPROVED_DATE" );
		  taskHd.put( "APPROVED_DATE",approved_date.trim() );
		  String start_date=request.getParameter( "start_date" );
		  if ( start_date == null )
			start_date="";
		  taskHd.remove( "START_DATE" );
		  taskHd.put( "START_DATE",start_date.trim() );
		  String projected_finisted_date=request.getParameter( "projected_finisted_date" );
		  if ( projected_finisted_date == null )
			projected_finisted_date="";
		  taskHd.remove( "PROJECTED_FINISTED_DATE" );
		  taskHd.put( "PROJECTED_FINISTED_DATE",projected_finisted_date.trim() );
		  String pojected_cost=request.getParameter( "pojected_cost" );
		  if ( pojected_cost == null )
			pojected_cost="";
		  taskHd.remove( "POJECTED_COST" );
		  taskHd.put( "POJECTED_COST",pojected_cost.trim() );
		  String actual_cost=request.getParameter( "actual_cost" );
		  if ( actual_cost == null )
			actual_cost="";
		  taskHd.remove( "ACTUAL_COST" );
		  taskHd.put( "ACTUAL_COST",actual_cost.trim() );
		  String projected_saving_year_1=request.getParameter( "projected_saving_year_1" );
		  if ( projected_saving_year_1 == null )
			projected_saving_year_1="";
		  taskHd.remove( "PROJECTED_SAVING_YEAR_1" );
		  taskHd.put( "PROJECTED_SAVING_YEAR_1",projected_saving_year_1.trim() );
		  String projected_saving_year_2=request.getParameter( "projected_saving_year_2" );
		  if ( projected_saving_year_2 == null )
			projected_saving_year_2="";
		  taskHd.remove( "PROJECTED_SAVING_YEAR_2" );
		  taskHd.put( "PROJECTED_SAVING_YEAR_2",projected_saving_year_2.trim() );
		  String projected_saving_year_3=request.getParameter( "projected_saving_year_3" );
		  if ( projected_saving_year_3 == null )
			projected_saving_year_3="";
		  taskHd.remove( "PROJECTED_SAVING_YEAR_3" );
		  taskHd.put( "PROJECTED_SAVING_YEAR_3",projected_saving_year_3.trim() );
		  String projected_saving_year_4=request.getParameter( "projected_saving_year_4" );
		  if ( projected_saving_year_4 == null )
			projected_saving_year_4="";
		  taskHd.remove( "PROJECTED_SAVING_YEAR_4" );
		  taskHd.put( "PROJECTED_SAVING_YEAR_4",projected_saving_year_4.trim() );
		  String projected_saving_year_5=request.getParameter( "projected_saving_year_5" );
		  if ( projected_saving_year_5 == null )
			projected_saving_year_5="";
		  taskHd.remove( "PROJECTED_SAVING_YEAR_5" );
		  taskHd.put( "PROJECTED_SAVING_YEAR_5",projected_saving_year_5.trim() );
		  String actual_saving_year_1=request.getParameter( "actual_saving_year_1" );
		  if ( actual_saving_year_1 == null )
			actual_saving_year_1="";
		  taskHd.remove( "ACTUAL_SAVING_YEAR_1" );
		  taskHd.put( "ACTUAL_SAVING_YEAR_1",actual_saving_year_1.trim() );
		  String actual_saving_year_2=request.getParameter( "actual_saving_year_2" );
		  if ( actual_saving_year_2 == null )
			actual_saving_year_2="";
		  taskHd.remove( "ACTUAL_SAVING_YEAR_2" );
		  taskHd.put( "ACTUAL_SAVING_YEAR_2",actual_saving_year_2.trim() );
		  String actual_saving_year_3=request.getParameter( "actual_saving_year_3" );
		  if ( actual_saving_year_3 == null )
			actual_saving_year_3="";
		  taskHd.remove( "ACTUAL_SAVING_YEAR_3" );
		  taskHd.put( "ACTUAL_SAVING_YEAR_3",actual_saving_year_3.trim() );
		  String actual_saving_year_4=request.getParameter( "actual_saving_year_4" );
		  if ( actual_saving_year_4 == null )
			actual_saving_year_4="";
		  taskHd.remove( "ACTUAL_SAVING_YEAR_4" );
		  taskHd.put( "ACTUAL_SAVING_YEAR_4",actual_saving_year_4.trim() );
		  String actual_saving_year_5=request.getParameter( "actual_saving_year_5" );
		  if ( actual_saving_year_5 == null )
			actual_saving_year_5="";
		  taskHd.remove( "ACTUAL_SAVING_YEAR_5" );
		  taskHd.put( "ACTUAL_SAVING_YEAR_5",actual_saving_year_5.trim() );
		  String comments=request.getParameter( "comments" );
		  if ( comments == null )
			comments="";
		  taskHd.remove( "COMMENTS" );
		  taskHd.put( "COMMENTS",comments.trim() );
		  String best_practice=request.getParameter( "best_practice" );
		  if ( best_practice == null )
			best_practice="";
		  taskHd.remove( "BEST_PRACTICE" );
		  taskHd.put( "BEST_PRACTICE",best_practice.trim() );
		  String project_status=request.getParameter( "project_status" );
		  if ( project_status == null )
			project_status="";
		  taskHd.remove( "PROJECT_STATUS" );
		  taskHd.put( "PROJECT_STATUS",project_status.trim() );
		  String actual_finish_date=request.getParameter( "actual_finish_date" );
		  if ( actual_finish_date == null )
			actual_finish_date="";
		  taskHd.remove( "ACTUAL_FINISH_DATE" );
		  taskHd.put( "ACTUAL_FINISH_DATE",actual_finish_date.trim() );
		  String priority=request.getParameter( "priority" );
		  if ( priority == null )
			priority="";
		  taskHd.remove( "PRIORITY" );
		  taskHd.put( "PRIORITY",priority.trim() );

		  //String keywords= "";
  	      String[] keywordsarray= {""};
		  Vector keywordsearchV = new Vector();
		  keywordsarray=request.getParameterValues( "keywords" );
		  //projlog.debug("keywordsarray     "+keywordsarray+"");
		  if ( keywordsarray != null )
		  {
			  for ( int loop=0; loop < keywordsarray.length; loop++ )
			  {
				/*if ( loop > 0 )
				{
				  keywords+=",";
				}
				keywords+="" + keywordsarray[loop] + "";*/
				keywordsearchV.add( keywordsarray[loop] );
			  }
		  }
		  taskHd.remove( "KEYWORDS" );
		  taskHd.put( "KEYWORDS",keywordsearchV );

	      String customercontact=request.getParameter( "customercontact" );
		  if ( customercontact == null )
			customercontact="";
		  taskHd.remove( "CUSTOMER_CONTACT_ID" );
		  taskHd.put( "CUSTOMER_CONTACT_ID",customercontact.trim() );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return taskHd;
    }

    private Vector getResult(String prjmgr, String tsktype, String status, String searchtext, String prority, String sortby, String projectid,
            String facilityid,String showonly_bestpractice,Vector searchKeywords,String keywordandoror,String keywordsrhstg)
        throws Exception
    {
        Vector Data = new Vector();
        Hashtable DataH = new Hashtable();
        Hashtable summary = new Hashtable();
        boolean falgforand = false;
        int count = 0;
        summary.put("TOTAL_NUMBER", new Integer(count));
        Data.addElement(summary);
        ResultSet searchRs = null;
    	DBResultSet dbrs = null;

        try
        {
		  String query="select PROJECT_MANAGER_EMAIL,PROJECT_MANAGER_PHONE,CUSTOMER_CONTACT_MANAGER,CUSTOMER_CONTACT_EMAIL,CUSTOMER_CONTACT_PHONE,CUSTOMER_CONTACT_ID,PROJECT_MANAGER,PROJECT_ID,PROJECT_NAME,PROJECT_DESC,PROJECT_CATEGORY,PROJECT_MANAGER_ID,FACILITY_ID,to_char(APPROVED_DATE,'mm/dd/yyyy') APPROVED_DATE,to_char(START_DATE,'mm/dd/yyyy') START_DATE,to_char(PROJECTED_FINISTED_DATE,'mm/dd/yyyy') PROJECTED_FINISTED_DATE,";
		  query+=" POJECTED_COST,ACTUAL_COST,COMMENTS,BEST_PRACTICE,PROJECT_STATUS,to_char(ACTUAL_FINISH_DATE,'mm/dd/yyyy') ACTUAL_FINISH_DATE,PRIORITY from PEI_PROJECT_VIEW ";

	      String whereclause = "";

		  if ( projectid.trim().length() != 0 )
		  {
			whereclause+=" PROJECT_ID = '" + projectid + "' ";
			falgforand=true;
		  }
		  else
		  {
		  if ( prjmgr.trim().length() != 0 && !"All".equalsIgnoreCase( prjmgr ) )
		  {
			if ( falgforand )
			  whereclause+=" and PROJECT_MANAGER_ID = '" + prjmgr + "' ";
			else
			{
			  whereclause+=" PROJECT_MANAGER_ID = '" + prjmgr + "' ";
			  falgforand=true;
			}
		  }

		  if ( tsktype.trim().length() != 0 && !"All".equalsIgnoreCase( tsktype ) )
		  {
			if ( falgforand )
			  whereclause+=" and PROJECT_TYPE = '" + tsktype + "' ";
			else
			{
			  whereclause+=" PROJECT_TYPE = '" + tsktype + "' ";
			  falgforand=true;
			}
		  }

		  if ( status.trim().length() != 0 && !"All".equalsIgnoreCase( status ) )
		  {
			if ( falgforand )
			  whereclause+=" and PROJECT_STATUS = '" + status + "' ";
			else
			{
			  whereclause+=" PROJECT_STATUS = '" + status + "' ";
			  falgforand=true;
			}
		  }

		  if ( prority.trim().length() != 0 && !"All".equalsIgnoreCase( prority ) )
		  {
			if ( falgforand )
			  whereclause+=" and PRIORITY = '" + prority + "' ";
			else
			{
			  whereclause+=" PRIORITY = '" + prority + "' ";
			  falgforand=true;
			}
		  }

		  if ( searchKeywords.size() > 0 && "or".equalsIgnoreCase( keywordandoror ) && !"'All'".equalsIgnoreCase( keywordsrhstg ) )
		  {
			if ( falgforand )
			  whereclause+=" and project_id in (select project_id from PEI_PROJECT_KEYWORD where KEYWORD_ID in (" + keywordsrhstg + ")) ";
			else
			{
			  whereclause+=" project_id in (select project_id from PEI_PROJECT_KEYWORD where KEYWORD_ID in (" + keywordsrhstg + ")) ";
			  falgforand=true;
			}
		  }
		  else if ( searchKeywords.size() > 0 && "and".equalsIgnoreCase( keywordandoror ) )
		  {
			String keyquery="";
			keyquery+="(";
			int serchcnt=0;
			for ( int loop=0; loop < searchKeywords.size(); loop++ )
			{
			  if ( serchcnt > 0 )
			  {
				keyquery+=" and ";
			  }

			  String seKeywrd= ( String ) searchKeywords.elementAt( loop );
			  if ( !"All".equalsIgnoreCase( seKeywrd ) )
			  {
				keyquery+=" KEYWORD_ID = '" + seKeywrd + "' ";
				serchcnt++;
			  }
			}
			keyquery+=")";

			if ( !"()".equalsIgnoreCase( keyquery ) )
			{
			  if ( falgforand )
			  {
				whereclause+=" and project_id in (select project_id from PEI_PROJECT_KEYWORD where " + keyquery + " )";
			  }
			  else
			  {
				whereclause+=" project_id in (select project_id from PEI_PROJECT_KEYWORD where " + keyquery + " )";
				falgforand=true;
			  }
			}
		 }

		  if ( facilityid.trim().length() != 0 && !"All".equalsIgnoreCase( facilityid ) )
		  {
			if ( falgforand )
			  whereclause+=" and FACILITY_ID  = '" + facilityid + "' ";
			else
			{
			  whereclause+=" FACILITY_ID = '" + facilityid + "' ";
			  falgforand=true;
			}
		  }

		 if ( "Y".equalsIgnoreCase( showonly_bestpractice ) )
		 {
			if ( falgforand )
			  whereclause+=" and BEST_PRACTICE  = '" + showonly_bestpractice + "' ";
			else
			{
			  whereclause+=" BEST_PRACTICE = '" + showonly_bestpractice + "' ";
			  falgforand=true;
			}
		  }

		  if ( searchtext.trim().length() != 0 )
		  {
			if ( falgforand )
			  whereclause+=" and lower(SEARCH) like lower('%" + searchtext + "%') ";
			else
			{
			  whereclause+=" lower(SEARCH) like lower('%" + searchtext + "%') ";
			  falgforand=true;
			}
		  }
		  }

		  if (whereclause.length() > 0)
		  {
			query += " where " +whereclause;
	      }

		  if ( sortby.trim().length() != 0 )
			query+=" ORDER BY " + sortby + "";
		  else
			query+=" ORDER BY PROJECT_ID";

		  dbrs=db.doQuery( query );
		  searchRs=dbrs.getResultSet();

             while ( searchRs.next() )
            {
                DataH = new Hashtable();
				DataH.put("PROJECT_ID",searchRs.getString("PROJECT_ID")==null?"":searchRs.getString("PROJECT_ID"));
				DataH.put("PROJECT_NAME",searchRs.getString("PROJECT_NAME")==null?"":searchRs.getString("PROJECT_NAME"));
				DataH.put("PROJECT_DESC",searchRs.getString("PROJECT_DESC")==null?"":searchRs.getString("PROJECT_DESC"));
				DataH.put("PROJECT_CATEGORY",searchRs.getString("PROJECT_CATEGORY")==null?"":searchRs.getString("PROJECT_CATEGORY"));
				DataH.put("PROJECT_MANAGER_ID",searchRs.getString("PROJECT_MANAGER_ID")==null?"":searchRs.getString("PROJECT_MANAGER_ID"));
				DataH.put("FACILITY_ID",searchRs.getString("FACILITY_ID")==null?"":searchRs.getString("FACILITY_ID"));
				DataH.put("APPROVED_DATE",searchRs.getString("APPROVED_DATE")==null?"":searchRs.getString("APPROVED_DATE"));
				DataH.put("START_DATE",searchRs.getString("START_DATE")==null?"":searchRs.getString("START_DATE"));
				DataH.put("PROJECTED_FINISTED_DATE",searchRs.getString("PROJECTED_FINISTED_DATE")==null?"":searchRs.getString("PROJECTED_FINISTED_DATE"));
				DataH.put("POJECTED_COST",searchRs.getString("POJECTED_COST")==null?"":searchRs.getString("POJECTED_COST"));
				DataH.put("ACTUAL_COST",searchRs.getString("ACTUAL_COST")==null?"":searchRs.getString("ACTUAL_COST"));
				/*DataH.put("PROJECTED_SAVING_YEAR_1",searchRs.getString("PROJECTED_SAVING_YEAR_1")==null?"":searchRs.getString("PROJECTED_SAVING_YEAR_1"));
				DataH.put("PROJECTED_SAVING_YEAR_2",searchRs.getString("PROJECTED_SAVING_YEAR_2")==null?"":searchRs.getString("PROJECTED_SAVING_YEAR_2"));
				DataH.put("PROJECTED_SAVING_YEAR_3",searchRs.getString("PROJECTED_SAVING_YEAR_3")==null?"":searchRs.getString("PROJECTED_SAVING_YEAR_3"));
				DataH.put("PROJECTED_SAVING_YEAR_4",searchRs.getString("PROJECTED_SAVING_YEAR_4")==null?"":searchRs.getString("PROJECTED_SAVING_YEAR_4"));
				DataH.put("PROJECTED_SAVING_YEAR_5",searchRs.getString("PROJECTED_SAVING_YEAR_5")==null?"":searchRs.getString("PROJECTED_SAVING_YEAR_5"));
				DataH.put("ACTUAL_SAVING_YEAR_1",searchRs.getString("ACTUAL_SAVING_YEAR_1")==null?"":searchRs.getString("ACTUAL_SAVING_YEAR_1"));
				DataH.put("ACTUAL_SAVING_YEAR_2",searchRs.getString("ACTUAL_SAVING_YEAR_2")==null?"":searchRs.getString("ACTUAL_SAVING_YEAR_2"));
				DataH.put("ACTUAL_SAVING_YEAR_3",searchRs.getString("ACTUAL_SAVING_YEAR_3")==null?"":searchRs.getString("ACTUAL_SAVING_YEAR_3"));
				DataH.put("ACTUAL_SAVING_YEAR_4",searchRs.getString("ACTUAL_SAVING_YEAR_4")==null?"":searchRs.getString("ACTUAL_SAVING_YEAR_4"));
				DataH.put("ACTUAL_SAVING_YEAR_5",searchRs.getString("ACTUAL_SAVING_YEAR_5")==null?"":searchRs.getString("ACTUAL_SAVING_YEAR_5"));*/
				DataH.put("COMMENTS",searchRs.getString("COMMENTS")==null?"":searchRs.getString("COMMENTS"));
				DataH.put("BEST_PRACTICE",searchRs.getString("BEST_PRACTICE")==null?"":searchRs.getString("BEST_PRACTICE"));
				DataH.put("PROJECT_STATUS",searchRs.getString("PROJECT_STATUS")==null?"":searchRs.getString("PROJECT_STATUS"));
				DataH.put("PROJECT_MANAGER",searchRs.getString("PROJECT_MANAGER")==null?"":searchRs.getString("PROJECT_MANAGER"));
				DataH.put("PRIORITY",searchRs.getString("PRIORITY")==null?"":searchRs.getString("PRIORITY"));
				DataH.put("USERCHK","");
				DataH.put("KEYWORDS","");
				DataH.put("ACTUAL_FINISH_DATE",searchRs.getString("ACTUAL_FINISH_DATE")==null?"":searchRs.getString("ACTUAL_FINISH_DATE"));
				DataH.put("CUSTOMER_CONTACT_ID",searchRs.getString("CUSTOMER_CONTACT_ID")==null?"":searchRs.getString("CUSTOMER_CONTACT_ID"));
				DataH.put("CUSTOMER_CONTACT_MANAGER",searchRs.getString("CUSTOMER_CONTACT_MANAGER")==null?"":searchRs.getString("CUSTOMER_CONTACT_MANAGER"));
				DataH.put("CUSTOMER_CONTACT_EMAIL",searchRs.getString("CUSTOMER_CONTACT_EMAIL")==null?"":searchRs.getString("CUSTOMER_CONTACT_EMAIL"));
				DataH.put("CUSTOMER_CONTACT_PHONE",searchRs.getString("CUSTOMER_CONTACT_PHONE")==null?"":searchRs.getString("CUSTOMER_CONTACT_PHONE"));
				DataH.put("PROJECT_MANAGER_PHONE",searchRs.getString("PROJECT_MANAGER_PHONE")==null?"":searchRs.getString("PROJECT_MANAGER_PHONE"));
				DataH.put("PROJECT_MANAGER_EMAIL",searchRs.getString("PROJECT_MANAGER_EMAIL")==null?"":searchRs.getString("PROJECT_MANAGER_EMAIL"));

				Data.addElement(DataH);
				count++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if(searchRs != null)
                try
                {
                    searchRs.close();
                }
                catch(SQLException sqlexception) { }
        }
        Hashtable recsum = new Hashtable();
        recsum.put("TOTAL_NUMBER", new Integer(count));
        Data.setElementAt(recsum, 0);

	//if ( projectid.trim().length() != 0 )
	{
	  Vector withkeydata=new Vector();
	  withkeydata=getKeywords( Data );
	  return withkeydata;
	}
	/*else
	{
	  return Data;
	}*/

    }

	private Vector getKeywords( Vector resultset ) throws Exception
	{
	  Vector new_data=new Vector();
	  Hashtable sum= ( Hashtable ) ( resultset.elementAt( 0 ) );
	  int count= ( ( Integer ) sum.get( "TOTAL_NUMBER" ) ).intValue();
	  new_data.addElement( sum );
	  Vector keyData=new Vector();
	  ResultSet searchRs=null;
	  DBResultSet dbrs=null;

	  try
	  {
		for ( int i=1; i < count + 1; i++ )
		{
		  Hashtable hD=new Hashtable();
		  hD= ( Hashtable ) ( resultset.elementAt( i ) );
		  String projectid= ( hD.get( "PROJECT_ID" ) == null ? "" : hD.get( "PROJECT_ID" ).toString() );

		  String query="select KEYWORD_ID from PEI_PROJECT_KEYWORD where PROJECT_ID = " + projectid + "";

		  dbrs=db.doQuery( query );
		  searchRs=dbrs.getResultSet();

		  keyData=new Vector();
		  while ( searchRs.next() )
		  {
			keyData.addElement( searchRs.getString( "KEYWORD_ID" ) == null ? "" : searchRs.getString( "KEYWORD_ID" ) );
		  }

		  hD.remove( "KEYWORDS" );
		  hD.put( "KEYWORDS",keyData );

		  new_data.addElement( hD );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		throw e;
	  }
	  finally
	  {
		if ( searchRs != null )
		  try
		  {
			searchRs.close();
		  }
		  catch ( SQLException sqlexception )
		  {}
	  }
	  return new_data;
	}

    private void buildprojectlist(Vector projectlist, HttpSession tasksession, String useaction,PrintWriter prjout)
    {
        //Hashtable vvprority = (Hashtable)tasksession.getAttribute("PROJECT_PRIORITY");
		Hashtable parentvvstatus= ( Hashtable ) tasksession.getAttribute( "PROJECT_STATUS" );
		Hashtable vvstatus=(Hashtable)(parentvvstatus.get("PROJECT_STATUS_LIST"));
        Hashtable vvtype = (Hashtable)tasksession.getAttribute("PROJECT_CATEGORY");
        //Hashtable vvowner = (Hashtable)tasksession.getAttribute("PROJECT_OWNERS");
        //Hashtable depdata = (Hashtable)tasksession.getAttribute("PROJECT_FACILITY");
		String Search_servlet=  bundle.getString( "PROJECTS_SERVLET" ) == null ? "" :  bundle.getString( "PROJECTS_SERVLET" ).toString();

        prjout.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=100% CLASS=\"moveup\">\n");
		//BGCOLOR=\"#000000\"
        Hashtable summary = new Hashtable();
        summary = (Hashtable)projectlist.elementAt(0);
        int total = ((Integer)summary.get("TOTAL_NUMBER")).intValue();
        int i = 0;
		if ( projectlist.size() == 0 )
		{
		  prjout.println( "<TR><TD HEIGHT=\"30\" CLASS=\"Inwhite\">No Records Found</TD></TR>\n" );
		}
		else
        {
		  for ( int count=0; count < total; count++ )
		  {
			i++;
			Hashtable taskHd=new Hashtable();
			taskHd= ( Hashtable ) projectlist.elementAt( i );
			if ( count % 10 == 0 )
			{
			  prjout.println( "<TR>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Print</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Category</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Best Practice</TH>\n" );
			  prjout.println( "<TH WIDTH=\"2%\" HEIGHT=\"38\">ID</TH>\n" );
			  prjout.println( "<TH WIDTH=\"15%\" HEIGHT=\"38\">Proj Name</TH>\n" );
			  prjout.println( "<TH WIDTH=\"20%\" HEIGHT=\"38\">Proj Desc</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Facility</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Proj Mgr</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Approved Date</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Projected/Actual Finish Date</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Projected/Actual Cost</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Projected/Actual Savings</TH>\n" );
			  prjout.println( "<TH WIDTH=\"5%\" HEIGHT=\"38\">Status</TH>\n" );
			  prjout.println( "</TR>\n" );
			}

			String Color=" ";
			if ( count % 2 == 0 )
			  Color="CLASS=\"Inblue";
			else
			  Color="CLASS=\"Inwhite";

			String project_id=taskHd.get( "PROJECT_ID" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_ID" ).toString();
			String project_name=taskHd.get( "PROJECT_NAME" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_NAME" ).toString();
			String project_desc=taskHd.get( "PROJECT_DESC" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_DESC" ).toString();
			String project_category=taskHd.get( "PROJECT_CATEGORY" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_CATEGORY" ).toString();
			//String project_manager_id=taskHd.get( "PROJECT_MANAGER_ID" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_MANAGER_ID" ).toString();
			String facility_id=taskHd.get( "FACILITY_ID" ) == null ? "&nbsp;" : taskHd.get( "FACILITY_ID" ).toString();
			String approved_date=taskHd.get( "APPROVED_DATE" ) == null ? "&nbsp;" : taskHd.get( "APPROVED_DATE" ).toString();
			//String start_date=taskHd.get( "START_DATE" ) == null ? "&nbsp;" : taskHd.get( "START_DATE" ).toString();
			String projected_finisted_date=taskHd.get( "PROJECTED_FINISTED_DATE" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_FINISTED_DATE" ).toString();
			String pojected_cost=taskHd.get( "POJECTED_COST" ) == null ? "&nbsp;" : taskHd.get( "POJECTED_COST" ).toString();
			String actual_cost=taskHd.get( "ACTUAL_COST" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_COST" ).toString();

			/*String projected_saving_year_1=taskHd.get( "PROJECTED_SAVING_YEAR_1" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_1" ).toString();
			String projected_saving_year_2=taskHd.get( "PROJECTED_SAVING_YEAR_2" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_2" ).toString();
			String projected_saving_year_3=taskHd.get( "PROJECTED_SAVING_YEAR_3" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_3" ).toString();
			String projected_saving_year_4=taskHd.get( "PROJECTED_SAVING_YEAR_4" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_4" ).toString();
			String projected_saving_year_5=taskHd.get( "PROJECTED_SAVING_YEAR_5" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_5" ).toString();
			String actual_saving_year_1=taskHd.get( "ACTUAL_SAVING_YEAR_1" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_1" ).toString();
			String actual_saving_year_2=taskHd.get( "ACTUAL_SAVING_YEAR_2" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_2" ).toString();
			String actual_saving_year_3=taskHd.get( "ACTUAL_SAVING_YEAR_3" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_3" ).toString();
			String actual_saving_year_4=taskHd.get( "ACTUAL_SAVING_YEAR_4" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_4" ).toString();
			String actual_saving_year_5=taskHd.get( "ACTUAL_SAVING_YEAR_5" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_5" ).toString();*/

		int totalprojecsavings = 0;
		int actualsavings = 0;

		/*if (projected_saving_year_1.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_1);}
		if (projected_saving_year_2.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_2);}
		if (projected_saving_year_3.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_3);}
		if (projected_saving_year_4.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_4);}
		if (projected_saving_year_5.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_5);}

		if (actual_saving_year_1.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_1);}
		if (actual_saving_year_2.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_2);}
		if (actual_saving_year_3.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_3);}
		if (actual_saving_year_4.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_4);}
		if (actual_saving_year_5.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_5);}*/

			String comments=taskHd.get( "COMMENTS" ) == null ? "&nbsp;" : taskHd.get( "COMMENTS" ).toString();
			String best_practice=taskHd.get( "BEST_PRACTICE" ) == null ? "&nbsp;" : taskHd.get( "BEST_PRACTICE" ).toString();
			String project_status=taskHd.get( "PROJECT_STATUS" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_STATUS" ).toString();
			String project_manager=taskHd.get( "PROJECT_MANAGER" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_MANAGER" ).toString();
			String priority=taskHd.get( "PRIORITY" ) == null ? "&nbsp;" : taskHd.get( "PRIORITY" ).toString();
			//String keywords=taskHd.get( "KEYWORDS" ) == null ? "&nbsp;" : taskHd.get( "KEYWORDS" ).toString();
			String actual_finish_date=taskHd.get( "ACTUAL_FINISH_DATE" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_FINISH_DATE" ).toString();
			String Line_Check= ( taskHd.get( "USERCHK" ) == null ? "" : taskHd.get( "USERCHK" ).toString() );
			//projlog.debug("Line_Check   "+Line_Check+"");

			/*if ( "4".equalsIgnoreCase( priority ) )
			  Color="CLASS=\"red";
			else
			if ( "3".equalsIgnoreCase( priority ) )
			  Color="CLASS=\"yellow";
			else
			if ( "1".equalsIgnoreCase( priority ) )
			  Color="CLASS=\"gray";*/

			prjout.println( "<TR>\n" );
			prjout.println( "<TD WIDTH=\"5%\" " + Color + "l\"><INPUT type=\"checkbox\" name=\"usercheck"+i+"\" value=\"Y\" " + ( Line_Check.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "></TD>\n" );

			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + vvtype.get( project_category ) + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + best_practice + "</td>\n" );
			prjout.println( "<TD WIDTH=\"2%\" HEIGHT=\"38\" " + Color + "l\"><A HREF=\""+Search_servlet+"projectid=" + project_id + "&Action=taskdetails\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">" + project_id + "</A></td>\n" );
			prjout.println( "<TD WIDTH=\"15%\" HEIGHT=\"38\" " + Color + "l\">" + project_name + "</td>\n" );
			prjout.println( "<TD WIDTH=\"20%\" HEIGHT=\"38\" " + Color + "l\">" + project_desc + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + facility_id + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + project_manager + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + approved_date + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + projected_finisted_date + "<BR>" + actual_finish_date + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + pojected_cost + "/" + actual_cost + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + totalprojecsavings + "/" + actualsavings + "</td>\n" );
			prjout.println( "<TD WIDTH=\"5%\" HEIGHT=\"38\" " + Color + "l\">" + vvstatus.get(project_status) + "</td>\n" );

			prjout.println( "</TR>\n" );
		  }
		}
		prjout.println( "</TABLE>\n</FORM>" );
    }

    private StringBuffer buildXlsDetails(Vector tsklistdata, HttpSession tasksession, String useaction,String personnelID)
    {
	  Hashtable vvprority= ( Hashtable ) tasksession.getAttribute( "PROJECT_PRIORITY" );
	  Hashtable parentvvstatus= ( Hashtable ) tasksession.getAttribute( "PROJECT_STATUS" );
	  Hashtable vvstatus=(Hashtable)(parentvvstatus.get("PROJECT_STATUS_LIST"));
	  Hashtable vvtype= ( Hashtable ) tasksession.getAttribute( "PROJECT_CATEGORY" );
	  Hashtable projkeywords= ( Hashtable ) tasksession.getAttribute( "PROJECT_KEYWORDS" );

	  String url="";
	  String file="";

	  Random rand=new Random();
	  int tmpReq=rand.nextInt();
	  Integer tmpReqNum=new Integer( tmpReq );

	  String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	  String writefilepath=radian.web.tcmisResourceLoader.getsaveltempreportpath();

	  file=writefilepath + personnelID + tmpReqNum.toString() + "projects.csv";
	  url=wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() + "projects.csv";

	  try
	  {
		PrintWriter pw=new PrintWriter( new FileOutputStream( file ) );

		Hashtable summary=new Hashtable();
		summary= ( Hashtable ) tsklistdata.elementAt( 0 );
		int total= ( ( Integer ) ( summary.get( "TOTAL_NUMBER" ) ) ).intValue();

		if ( total == 0 )
		{
		  StringBuffer Msg=new StringBuffer();
		  Msg.append( "No Records Found " );
		  return Msg;
		}

           pw.print("Project ID,Project Name,Project Desc,Category,Project Manager,Facility ID,Customer Contact Name,Best Practice,Status,Priority,");
		   pw.print("Approved Date,Start Date,Projected Finish Date,Actual Finish Date,Projected Cost,Actual Cost,Comments,Projected Savings Year 1,");
		   pw.print("Projected Savings Year 2,Projected Savings Year 3,Projected Savings Year 4,Projected Savings Year 5,Actual Savings Year 1,");
		   pw.print("Actual Savings Year 2,Actual Savings Year 3,Actual Savings Year 4,Actual Savings Year 5,Keywords\n");
		   int i = 0;
		   for(int loop = 0; loop < total; loop++)
		   {
              i++;
              Hashtable taskHd = new Hashtable();
              taskHd = (Hashtable)tsklistdata.elementAt(i);

			  String project_id=taskHd.get( "PROJECT_ID" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_ID" ).toString();
			  String project_name=taskHd.get( "PROJECT_NAME" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_NAME" ).toString();
			  String project_desc=taskHd.get( "PROJECT_DESC" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_DESC" ).toString();
			  String project_category=taskHd.get( "PROJECT_CATEGORY" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_CATEGORY" ).toString();
			  project_category = (String)vvtype.get(project_category);
			  //String project_manager_id=taskHd.get( "PROJECT_MANAGER_ID" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_MANAGER_ID" ).toString();
			  String facility_id=taskHd.get( "FACILITY_ID" ) == null ? "&nbsp;" : taskHd.get( "FACILITY_ID" ).toString();
			  String approved_date=taskHd.get( "APPROVED_DATE" ) == null ? "&nbsp;" : taskHd.get( "APPROVED_DATE" ).toString();
			  String start_date=taskHd.get( "START_DATE" ) == null ? "&nbsp;" : taskHd.get( "START_DATE" ).toString();
			  String projected_finisted_date=taskHd.get( "PROJECTED_FINISTED_DATE" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_FINISTED_DATE" ).toString();
			  String pojected_cost=taskHd.get( "POJECTED_COST" ) == null ? "&nbsp;" : taskHd.get( "POJECTED_COST" ).toString();
			  String actual_cost=taskHd.get( "ACTUAL_COST" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_COST" ).toString();
			  String projected_saving_year_1=taskHd.get( "PROJECTED_SAVING_YEAR_1" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_1" ).toString();
			  String projected_saving_year_2=taskHd.get( "PROJECTED_SAVING_YEAR_2" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_2" ).toString();
			  String projected_saving_year_3=taskHd.get( "PROJECTED_SAVING_YEAR_3" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_3" ).toString();
			  String projected_saving_year_4=taskHd.get( "PROJECTED_SAVING_YEAR_4" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_4" ).toString();
			  String projected_saving_year_5=taskHd.get( "PROJECTED_SAVING_YEAR_5" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_SAVING_YEAR_5" ).toString();
			  String actual_saving_year_1=taskHd.get( "ACTUAL_SAVING_YEAR_1" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_1" ).toString();
			  String actual_saving_year_2=taskHd.get( "ACTUAL_SAVING_YEAR_2" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_2" ).toString();
			  String actual_saving_year_3=taskHd.get( "ACTUAL_SAVING_YEAR_3" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_3" ).toString();
			  String actual_saving_year_4=taskHd.get( "ACTUAL_SAVING_YEAR_4" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_4" ).toString();
			  String actual_saving_year_5=taskHd.get( "ACTUAL_SAVING_YEAR_5" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_SAVING_YEAR_5" ).toString();
			  String comments=taskHd.get( "COMMENTS" ) == null ? "&nbsp;" : taskHd.get( "COMMENTS" ).toString();
			  String best_practice=taskHd.get( "BEST_PRACTICE" ) == null ? "&nbsp;" : taskHd.get( "BEST_PRACTICE" ).toString();
			  String project_status=taskHd.get( "PROJECT_STATUS" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_STATUS" ).toString();
			  project_status = (String)vvstatus.get(project_status);
			  String project_manager=taskHd.get( "PROJECT_MANAGER" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_MANAGER" ).toString();
			  String priority=taskHd.get( "PRIORITY" ) == null ? "&nbsp;" : taskHd.get( "PRIORITY" ).toString();
			  priority = (String)vvprority.get(priority);
			  //String keywords=taskHd.get( "KEYWORDS" ) == null ? "&nbsp;" : taskHd.get( "KEYWORDS" ).toString();
			  Vector seleckeywords = new Vector();
			  seleckeywords = (Vector) taskHd.get( "KEYWORDS" );
			  if (seleckeywords == null) {seleckeywords = new Vector();}

		      String keywords= "";
			  for ( int id=0; id < seleckeywords.size(); id++ )
			  {
				if ( id > 0 )
				{
				  keywords+=",";
				}
				String keykey = (String) seleckeywords.get(id);
				keywords+= projkeywords.get( keykey ).toString();
			  }

			  String actual_finish_date =taskHd.get( "ACTUAL_FINISH_DATE" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_FINISH_DATE" ).toString();
			  String customercontactname =taskHd.get( "CUSTOMER_CONTACT_MANAGER" ) == null ? "&nbsp;" : taskHd.get( "CUSTOMER_CONTACT_MANAGER" ).toString();

			  pw.print("\""+project_id+"\",\""+project_name+"\",\""+project_desc+"\",\""+project_category+"\",\""+project_manager+"\",\""+facility_id+"\",\""+customercontactname+"\",\""+best_practice+"\",\""+project_status+"\",\""+priority+"\",\""+approved_date+"\",\""+start_date+"\",\""+projected_finisted_date+"\",\""+actual_finish_date+"\",\""+pojected_cost+"\",\""+actual_cost+"\",\""+comments+"\",\""+projected_saving_year_1+"\",\""+projected_saving_year_2+"\",\""+projected_saving_year_3+"\",\""+projected_saving_year_4+"\",\""+projected_saving_year_5+"\",\""+actual_saving_year_1+"\",\""+actual_saving_year_2+"\",\""+actual_saving_year_3+"\",\""+actual_saving_year_4+"\",\""+actual_saving_year_5+"\",\""+keywords+"\"\n");
			}
			pw.close();
		  }
		  catch(Exception e)
		  {
            e.printStackTrace();
            StringBuffer stringbuffer = HTMLHelpObj.printHTMLError();
            return stringbuffer;
		  }

        StringBuffer MsgSB = new StringBuffer();
        if(url.length() > 0)
        {
            MsgSB.append("<HTML><HEAD>\n");
            MsgSB.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+url+"\">\n");
            MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
            MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
            MsgSB.append("<TITLE>Container Label Generator</TITLE>\n");
            MsgSB.append("</HEAD>  \n");
            MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
            MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
            MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Downloading Excel File</b></font><P></P><BR>\n");
            MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
            MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait ...</b></font><P></P><BR>\n");
            MsgSB.append("</CENTER>\n");
            MsgSB.append("</BODY></HTML>    \n");
        } else
        {
            MsgSB.append("<HTML><HEAD>\n");
            MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
            MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
            MsgSB.append("<TITLE>Container Label Generator</TITLE>\n");
            MsgSB.append("</HEAD>  \n");
            MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
            MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
            MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>An Error Occured </b></font><P></P><BR>\n");
            MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
            MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please try Again or contact Tech Center.</b></font><P></P><BR>\n");
            MsgSB.append("</CENTER>\n");
            MsgSB.append("</BODY></HTML>    \n");
        }
        return MsgSB;
    }

    private void buildnewTask(HttpSession tasksession, String message,PrintWriter prjout)
	{
	  Hashtable vvprority= ( Hashtable ) tasksession.getAttribute( "PROJECT_PRIORITY" );
	  Hashtable parentvvstatus= ( Hashtable ) tasksession.getAttribute( "PROJECT_STATUS" );
	  Hashtable vvstatus=(Hashtable)(parentvvstatus.get("PROJECT_STATUS_LIST"));
	  Hashtable vvtype= ( Hashtable ) tasksession.getAttribute( "PROJECT_CATEGORY" );
	  Hashtable vvowner= ( Hashtable ) tasksession.getAttribute( "PROJECT_OWNERS" );
	  Hashtable taskHd= ( Hashtable ) tasksession.getAttribute( "PROJECT_DATA" );
	  Hashtable depdata= ( Hashtable ) tasksession.getAttribute( "PROJECT_FACILITY" );
	  Hashtable projkeywords= ( Hashtable ) tasksession.getAttribute( "PROJECT_KEYWORDS" );

	  HeaderFooter hf=new HeaderFooter( bundle,db );
	  prjout.println( hf.printHTMLHeader( "tcmIS Projects",intcmIsApplication ) );
	  prjout.println( "<SCRIPT SRC=\"/clientscripts/projects.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	  prjout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	  prjout.println( hf.printTopToolBar( "","addeditprojects.gif",tasksession ) );

	  String Search_servlet=  bundle.getString( "PROJECTS_SERVLET" ) == null ? "" :  bundle.getString( "PROJECTS_SERVLET" ).toString();

	  prjout.println( "<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n" );
	  prjout.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n" );
	  prjout.println( "</DIV>\n" );

	  prjout.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
	  if (message.length() > 0 )
	  {
		prjout.println( HTMLHelpObj.printMessageinTable( message ) );
	  }
	  prjout.println( "<FORM method=\"POST\" NAME=\"projectmanager\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+Search_servlet+"\">\n" );
	  prjout.println( "<TABLE BORDER=0 WIDTH=725 CELLSPACING=\"1\" CELLPADDING=\"3\" CLASS=\"moveup\">\n" );
	  prjout.println( "<TR>\n" );
	  prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"Save New\" NAME=\"NewtskButton\" onclick= \"return entertask(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;</TD>\n" );
	  prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"whitel\"><INPUT TYPE=\"submit\" ID=\"genddbutton1\" VALUE=\"Update\" NAME=\"UpdtskButton\" onclick= \"return updatetask(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n</TD>" );
	  prjout.println("<TD CLASS=\"whiter\" WIDTH=\"5%\" VALIGN=\"MIDDLE\">\n");
	  prjout.println("<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"New Project\" NAME=\"NewtskButton\" onclick= \"return newtask(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
	  prjout.println("</TD>\n");
//	  prjout.println( "<TD CLASS=\"announce\" COLSPAN=\"2\" WIDTH=\"5%\" VALIGN=\"MIDDLE\"><INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"New Task\" NAME=\"NewtskButton\" onclick= \"return newtask(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n</TD>\n" );
	  //prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><A HREF=\"javascript:backsearch(this)\" STYLE=\"color:#FFFFFF\">Return to List</A>&nbsp;\n</TD>" );
	  prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><INPUT TYPE=\"submit\" ID=\"gbacksearchutton1\" VALUE=\"Return to List\" NAME=\"backskButton\" onclick= \"return backsearch(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n</TD>" );
	  prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"whitel\"><INPUT TYPE=\"button\" ID=\"printtaskbutton1\" VALUE=\"Print\" NAME=\"printtaskbutton1\" onclick= \"return printtask(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n</TD>" );
	  prjout.println( "</TR>\n" );

	  String project_id=taskHd.get( "PROJECT_ID" ) == null ? "" : taskHd.get( "PROJECT_ID" ).toString();
	  String project_name=taskHd.get( "PROJECT_NAME" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_NAME" ).toString();
	  String project_desc=taskHd.get( "PROJECT_DESC" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_DESC" ).toString();
	  String project_category=taskHd.get( "PROJECT_CATEGORY" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_CATEGORY" ).toString();
	  String project_manager_id=taskHd.get( "PROJECT_MANAGER_ID" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_MANAGER_ID" ).toString();
	  String facility_id=taskHd.get( "FACILITY_ID" ) == null ? "&nbsp;" : taskHd.get( "FACILITY_ID" ).toString();
	  String approved_date=taskHd.get( "APPROVED_DATE" ) == null ? "&nbsp;" : taskHd.get( "APPROVED_DATE" ).toString();
	  String start_date=taskHd.get( "START_DATE" ) == null ? "&nbsp;" : taskHd.get( "START_DATE" ).toString();
	  String projected_finisted_date=taskHd.get( "PROJECTED_FINISTED_DATE" ) == null ? "&nbsp;" : taskHd.get( "PROJECTED_FINISTED_DATE" ).toString();
	  String pojected_cost=taskHd.get( "POJECTED_COST" ) == null ? "&nbsp;" : taskHd.get( "POJECTED_COST" ).toString();
	  String actual_cost=taskHd.get( "ACTUAL_COST" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_COST" ).toString();
	  /*String projected_saving_year_1=taskHd.get( "PROJECTED_SAVING_YEAR_1" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_1" ).toString();
	  String projected_saving_year_2=taskHd.get( "PROJECTED_SAVING_YEAR_2" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_2" ).toString();
	  String projected_saving_year_3=taskHd.get( "PROJECTED_SAVING_YEAR_3" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_3" ).toString();
	  String projected_saving_year_4=taskHd.get( "PROJECTED_SAVING_YEAR_4" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_4" ).toString();
	  String projected_saving_year_5=taskHd.get( "PROJECTED_SAVING_YEAR_5" ) == null ? "" : taskHd.get( "PROJECTED_SAVING_YEAR_5" ).toString();
	  String actual_saving_year_1=taskHd.get( "ACTUAL_SAVING_YEAR_1" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_1" ).toString();
	  String actual_saving_year_2=taskHd.get( "ACTUAL_SAVING_YEAR_2" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_2" ).toString();
	  String actual_saving_year_3=taskHd.get( "ACTUAL_SAVING_YEAR_3" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_3" ).toString();
	  String actual_saving_year_4=taskHd.get( "ACTUAL_SAVING_YEAR_4" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_4" ).toString();
	  String actual_saving_year_5=taskHd.get( "ACTUAL_SAVING_YEAR_5" ) == null ? "" : taskHd.get( "ACTUAL_SAVING_YEAR_5" ).toString();*/
	  String comments=taskHd.get( "COMMENTS" ) == null ? "&nbsp;" : taskHd.get( "COMMENTS" ).toString();
	  String best_practice=taskHd.get( "BEST_PRACTICE" ) == null ? "&nbsp;" : taskHd.get( "BEST_PRACTICE" ).toString();
	  String project_status=taskHd.get( "PROJECT_STATUS" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_STATUS" ).toString();
	  String project_manager=taskHd.get( "PROJECT_MANAGER" ) == null ? "&nbsp;" : taskHd.get( "PROJECT_MANAGER" ).toString();
	  String priority=taskHd.get( "PRIORITY" ) == null ? "&nbsp;" : taskHd.get( "PRIORITY" ).toString();
	  //String keywords=taskHd.get( "KEYWORDS" ) == null ? "&nbsp;" : taskHd.get( "KEYWORDS" ).toString();
	  Vector seleckeywords = new Vector();
	  seleckeywords = (Vector) taskHd.get( "KEYWORDS" );
	  if (seleckeywords == null) {seleckeywords = new Vector();}

	  /*StringTokenizer st=new StringTokenizer( keywords,"," );
	  Vector seleckeywords = new Vector();
	  if ( st.countTokens() > 1 )
	  {
		while ( st.hasMoreTokens() )
		{
		  String tok=st.nextToken();
		  seleckeywords.addElement(tok);
		}
	  }*/

	  String actual_finish_date =taskHd.get( "ACTUAL_FINISH_DATE" ) == null ? "&nbsp;" : taskHd.get( "ACTUAL_FINISH_DATE" ).toString();
	  String customercontact =taskHd.get( "CUSTOMER_CONTACT_ID" ) == null ? "&nbsp;" : taskHd.get( "CUSTOMER_CONTACT_ID" ).toString();
	  String customercontactname =taskHd.get( "CUSTOMER_CONTACT_MANAGER" ) == null ? "&nbsp;" : taskHd.get( "CUSTOMER_CONTACT_MANAGER" ).toString();

	  int totalprojecsavings = 0;
	  int actualsavings = 0;

	  /*if (projected_saving_year_1.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_1);}
	  if (projected_saving_year_2.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_2);}
	  if (projected_saving_year_3.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_3);}
	  if (projected_saving_year_4.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_4);}
	  if (projected_saving_year_5.length() > 0 ) {totalprojecsavings += Integer.parseInt(projected_saving_year_5);}

	  if (actual_saving_year_1.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_1);}
	  if (actual_saving_year_2.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_2);}
	  if (actual_saving_year_3.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_3);}
	  if (actual_saving_year_4.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_4);}
	  if (actual_saving_year_5.length() > 0 ) {actualsavings += Integer.parseInt(actual_saving_year_5);}*/

	  prjout.println( "<TR>\n" );
	  prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Project Name:</B></TD>\n" );
	  prjout.println( "<TD COLSPAN=\"3\" WIDTH=\"5%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"project_name\" value=\"" + project_name + "\" SIZE=\"50\"></TD>\n" );
	  //prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><INPUT type=\"checkbox\" name=\"best_practice\" value=\"Y\" " + ( best_practice.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + ">Best Practice</TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Haas Proj Mgr:</B></TD>\n" );
	/*if ( project_manager_id.length() > 0 )
	{
	  prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"bluel\">" + vvowner.get(project_manager_id) + "<INPUT TYPE=\"hidden\" NAME=\"project_manager_id\" VALUE=\""+project_manager_id+"\"></TD>\n" );
	}
	else*/
	{
	  prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"bluel\">" );
	  prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"project_manager_id\" size=\"1\">\n" );
	  prjout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	  prjout.println( HTMLHelpObj.sorthashbyValue( vvowner.entrySet(),project_manager_id ) );
	  prjout.println( "</SELECT>\n</TD>\n" );
	}
	prjout.println( "</TR>\n" );
	prjout.println( "<TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><B>Description:</B></TD>\n" );
	prjout.println( "<TD COLSPAN=\"6\" CLASS=\"whitel\"><TEXTAREA name=\"project_desc\" rows=\"5\" cols=\"100\">"+project_desc+"</TEXTAREA></TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Keywords:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"bluel\"><B>Base Keywords</B><BR>" );
	prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"basekeywords\" size=\"6\" multiple>\n" );
	Enumeration E;
	for ( E=projkeywords.keys(); E.hasMoreElements(); )
	{
	  String key= ( String ) E.nextElement();
	  String keyvalue=projkeywords.get( key ).toString();

	  if (!seleckeywords.contains(key))
	  {
		prjout.println( "<OPTION VALUE=\""+key+"\">"+keyvalue+"</OPTION>\n" );
	  }
	}
	prjout.println( "</SELECT>\n</TD>\n" );

	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"blue\"><INPUT TYPE=\"button\" ID=\"basekeywordsss\" VALUE=\"===>\" NAME=\"NewtskButton\" onclick= \"transferMultipleItems(this.form.basekeywords,this.form.keywords)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"><BR><BR><BR>\n" );
	prjout.println( "<INPUT TYPE=\"button\" ID=\"basekeywordssss\" VALUE=\"<===\" NAME=\"NewtskButton\" onclick= \"transferMultipleItems(this.form.keywords,this.form.basekeywords)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"></TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"3\" CLASS=\"bluel\"><B>Selected Keywords</B><BR>" );
	prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"keywords\" size=\"6\" multiple>\n" );
	for ( E=projkeywords.keys(); E.hasMoreElements(); )
	{
	  String key= ( String ) E.nextElement();
	  String keyvalue=projkeywords.get( key ).toString();

	  if (seleckeywords.contains(key))
	  {
		prjout.println( "<OPTION VALUE=\""+key+"\">"+keyvalue+"</OPTION>\n" );
	  }
	}
	prjout.println( "</SELECT>\n</TD>\n" );
	//prjout.println( "<TD COLSPAN=\"6\" CLASS=\"bluel\"><TEXTAREA name=\"keywords\" rows=\"5\" cols=\"80\">"+keywords+"</TEXTAREA></TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><B>Facility:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"whitel\">" );
	prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"facility_id\" size=\"1\">\n" );
	prjout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	prjout.println( HTMLHelpObj.sorthashbyValue( depdata.entrySet(),facility_id ) );
	prjout.println( "</SELECT>\n</TD>\n" );

	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"whiter\"><B>Client Contact:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whitel\">\n" );
	prjout.println( "<INPUT TYPE=\"hidden\" NAME=\"customercontact\" VALUE=\""+customercontact+"\">\n" );
	prjout.println( "<INPUT type=\"text\" CLASS=\"INVISIBLEHEADWHITE\" name=\"customercontactname\" value=\""+customercontactname+"\" SIZE=\"12\" readonly><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchpersonnellike\" value=\"...\" OnClick=\"getpersonnelID()\" type=\"button\"></TD>\n");
	prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"whitel\"><INPUT type=\"checkbox\" name=\"best_practice\" value=\"Y\" " + ( best_practice.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "><B>Best Practice</B></TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Status:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluel\">" );
	prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"project_status\" size=\"1\">\n" );
	prjout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	Hashtable vvstatusprority=(Hashtable)(parentvvstatus.get("PROJECT_STATUS_PRORITY_LIST"));
	prjout.println(HTMLHelpObj.sorthashbyValue(vvstatusprority.entrySet(), project_status,vvstatus));
	prjout.println( "</SELECT>\n</TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Start Date:</B><BR>(mm/dd/yyyy)</TD>\n" );
	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"start_date\" value=\"" + start_date + "\" SIZE=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"startdate\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" onClick=\"return getCalendar(document.projectmanager.start_date);\">&diams;</a></FONT></TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Approve Date:</B><BR>(mm/dd/yyyy)</TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"approved_date\" value=\""+approved_date+"\" SIZE=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"approveddate\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" onClick=\"return getCalendar(document.projectmanager.approved_date);\">&diams;</a></FONT></TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><B>Category:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whitel\">" );
	prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"project_category\" size=\"1\">\n" );
	prjout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	prjout.println( HTMLHelpObj.sorthashbyValue( vvtype.entrySet(),project_category ) );
	prjout.println( "</SELECT>\n</TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><B>Projected Finish:</B><BR>(mm/dd/yyyy)</TD>\n" );
	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"projected_finisted_date\" value=\"" + projected_finisted_date + "\" SIZE=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"projectedfinishdate\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" onClick=\"return getCalendar(document.projectmanager.projected_finisted_date);\">&diams;</a></FONT></TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><B>Actual Finish:</B><BR>(mm/dd/yyyy)</TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"actual_finish_date\" value=\"" + actual_finish_date + "\" SIZE=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"actualfinishdate\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" onClick=\"return getCalendar(document.projectmanager.actual_finish_date);\">&diams;</a></FONT></TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Priority:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluel\">" );
	prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"priority\" size=\"1\">\n" );
	prjout.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
	prjout.println( HTMLHelpObj.sorthashbyValue( vvprority.entrySet(),priority ) );
	prjout.println( "</SELECT>\n</TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Projected Cost:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"pojected_cost\" value=\"" + pojected_cost + "\" SIZE=\"7\"></TD>\n" );

	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Actual Cost:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" COLSPAN=\"2\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"actual_cost\" value=\"" + actual_cost + "\" SIZE=\"7\"></TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\">&nbsp;</TD>\n" );
	prjout.println( "<TH width=\"5%\" ALIGN=\"LEFT\">1st yr</TH>\n" );
	prjout.println( "<TH width=\"5%\" ALIGN=\"LEFT\">2st yr</TH>\n" );
	prjout.println( "<TH width=\"5%\" ALIGN=\"LEFT\">3st yr</TH>\n" );
	prjout.println( "<TH width=\"5%\" ALIGN=\"LEFT\">4st yr</TH>\n" );
	prjout.println( "<TH width=\"5%\" ALIGN=\"LEFT\">5st yr</TH>\n" );
	prjout.println( "<TH width=\"2%\" ALIGN=\"LEFT\">Total</TH>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><B>Projected Savings:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"projected_saving_year_1\" value=\"" + totalprojecsavings + "\" SIZE=\"5\" onChange=\"calprojectedsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"projected_saving_year_2\" value=\"" + totalprojecsavings + "\" SIZE=\"5\" onChange=\"calprojectedsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"projected_saving_year_3\" value=\"" + totalprojecsavings + "\" SIZE=\"5\" onChange=\"calprojectedsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"projected_saving_year_4\" value=\"" + totalprojecsavings + "\" SIZE=\"5\" onChange=\"calprojectedsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whitel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"projected_saving_year_5\" value=\"" + totalprojecsavings + "\" SIZE=\"5\" onChange=\"calprojectedsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"whitel\" ID=\"projsavingstotal\">"+totalprojecsavings+"</TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluer\"><B>Actual Savings:</B></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"actual_saving_year_1\" value=\"" + actualsavings + "\" SIZE=\"5\" onChange=\"calactualsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"actual_saving_year_2\" value=\"" + actualsavings + "\" SIZE=\"5\" onChange=\"calactualsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"actual_saving_year_3\" value=\"" + actualsavings + "\" SIZE=\"5\" onChange=\"calactualsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"actual_saving_year_4\" value=\"" + actualsavings + "\" SIZE=\"5\" onChange=\"calactualsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"bluel\"><INPUT type=\"text\" CLASS=\"HEADER\" name=\"actual_saving_year_5\" value=\"" + actualsavings + "\" SIZE=\"5\" onChange=\"calactualsavings()\"></TD>\n" );
	prjout.println( "<TD WIDTH=\"2%\" CLASS=\"bluel\" ID=\"totalactualsavings\">"+actualsavings+"</TD>\n" );
	prjout.println( "</TR>\n" );

	prjout.println( "<TR>\n" );
	prjout.println( "<TD WIDTH=\"5%\" CLASS=\"whiter\"><B>Comments:</B></TD>\n" );
	prjout.println( "<TD COLSPAN=\"6\" CLASS=\"whitel\"><TEXTAREA name=\"comments\" rows=\"5\" cols=\"100\">"+comments+"</TEXTAREA></TD>\n" );
	prjout.println( "</TR>\n" );
	prjout.println( "</TABLE>\n" );

	prjout.println( "<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"\">\n" );
	prjout.println( "<INPUT TYPE=\"hidden\" NAME=\"project_id\" VALUE=\""+project_id+"\">\n");
	prjout.println( "</FORM>\n\n" );
  }

    private void buildHeader(String Message, String owenedby, String tsktype, String status, String searchtext, String prority, String sortby,
            String department, HttpSession tasksession,PrintWriter prjout,String useraction, String showonly_bestpractice,Vector searchKeywords,String keywordandoror)
    {
		HeaderFooter hf=new HeaderFooter( bundle,db );
		prjout.println(hf.printHTMLHeader("tcmIS Projects",intcmIsApplication));
		prjout.println( "<SCRIPT SRC=\"/clientscripts/projects.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );

	    if ( "printpdf".equalsIgnoreCase(useraction) )
		{
		  prjout.println( hf.printTopToolBar( "doPrintprojectlist()","projects.gif",tasksession ) );
		}
		else
		{
		  prjout.println( hf.printTopToolBar( "","projects.gif",tasksession ) );
		}

		String Search_servlet=  bundle.getString( "PROJECTS_SERVLET" ) == null ? "" :  bundle.getString( "PROJECTS_SERVLET" ).toString();

        prjout.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
        prjout.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
        prjout.println("</DIV>\n");
        prjout.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

        prjout.println("<FORM method=\"POST\" NAME=\"projectmanager\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""+Search_servlet+"\">\n");
        prjout.println("<TABLE BORDER=0 WIDTH=725 CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");
        prjout.println("<TR>\n");

        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
        prjout.println("<B>Project Mgr:</B>&nbsp;\n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
        prjout.println("<SELECT CLASS=\"HEADER\" NAME=\"owenedby\">\n");
        prjout.println("<OPTION VALUE=\"ALL\">All</OPTION>\n");
        prjout.println(HTMLHelpObj.sorthashbyValue(((Hashtable)tasksession.getAttribute("PROJECT_OWNERS")).entrySet(), owenedby));
        prjout.println("</SELECT>\n");
        prjout.println("</TD>\n");

        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
        prjout.println("<B>Status:</B>&nbsp;\n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
        prjout.println("<SELECT CLASS=\"HEADER\" NAME=\"status\" size=\"1\">\n");
		Hashtable parentvvstatus= ( Hashtable ) tasksession.getAttribute( "PROJECT_STATUS" );
		Hashtable vvstatus=(Hashtable)(parentvvstatus.get("PROJECT_STATUS_LIST"));
	    Hashtable vvstatusprority=(Hashtable)(parentvvstatus.get("PROJECT_STATUS_PRORITY_LIST"));

        prjout.println("<OPTION VALUE=\"ALL\">All</OPTION>\n");
        prjout.println(HTMLHelpObj.sorthashbyValue(vvstatusprority.entrySet(), status,vvstatus));
        prjout.println("</SELECT>\n");
        prjout.println("</TD>\n");

	    prjout.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" VALIGN=\"middle\" ROWSPAN=\"2\"><B>Key Words:</B>\n" );
		prjout.println("</TD>\n");
		prjout.println( "<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"top\" ROWSPAN=\"2\">\n" );
		prjout.println( "<SELECT CLASS=\"HEADER\" NAME=\"keywordsearch\" size=\"4\" multiple>\n" );
		String allbuyerselected="";
	   if ( searchKeywords.contains( "ALL" ) )
	   {
		 allbuyerselected="selected";
	   }
		prjout.println("<OPTION " + allbuyerselected + " VALUE=\"ALL\">All</OPTION>\n");
		prjout.println( radian.web.HTMLHelpObj.getmultipleDropDown( ( Hashtable ) tasksession.getAttribute( "PROJECT_KEYWORDS" ),searchKeywords ) );
		prjout.println( "</SELECT>\n" );
		prjout.println( "</TD>\n" );

        prjout.println("<TD WIDTH=\"5%\" ALIGN=\"right\" CLASS=\"announce\">\n");
        prjout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchtext\" value=\""+searchtext+"\" size=\"19\">\n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" ALIGN=\"left\" WIDTH=\"5%\" VALIGN=\"MIDDLE\">\n");
        prjout.println("<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"List Projects\" NAME=\"SearchButton\" onclick= \"return search(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
        prjout.println("</TD>\n");
        prjout.println("</TR>\n");

        prjout.println("<TR>\n");
        prjout.println("<TD CLASS=\"announce\" HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
        prjout.println("<B>Category:</B>&nbsp; \n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
        prjout.println("<SELECT CLASS=\"HEADER\" NAME=\"tsktype\">\n");
        prjout.println("<OPTION VALUE=\"ALL\">All</OPTION>\n");
        prjout.println(HTMLHelpObj.sorthashbyValue(((Hashtable)tasksession.getAttribute("PROJECT_CATEGORY")).entrySet(), tsktype));
        prjout.println("</SELECT>\n");
		prjout.println("</TD>\n");

        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
        prjout.println("<B>Prority:</B>&nbsp;\n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
        prjout.println("<SELECT CLASS=\"HEADER\" NAME=\"prority\" size=\"1\">\n");
        prjout.println("<OPTION VALUE=\"ALL\">All</OPTION>\n");
        prjout.println(HTMLHelpObj.sorthashbyValue(((Hashtable)tasksession.getAttribute("PROJECT_PRIORITY")).entrySet(), prority));
        prjout.println("</SELECT>\n");
		prjout.println("</TD>\n");

        prjout.println("<TD CLASS=\"announce\" HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
		prjout.println( "<INPUT type=\"checkbox\" name=\"showonly_bestpractice\" value=\"Y\" " + ( showonly_bestpractice.equalsIgnoreCase( "Y" ) ? "checked" : "" ) + "><B>Only Best Practice</B>\n" );
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" ALIGN=\"left\" WIDTH=\"5%\" VALIGN=\"MIDDLE\">\n");
        prjout.println("<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"New Project\" NAME=\"NewtskButton\" onclick= \"return newtask(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
        prjout.println("</TD>\n");
        prjout.println("</TR>\n");

        prjout.println("</TR>\n");
        prjout.println("<TR>\n");
        prjout.println("<TD CLASS=\"announce\" HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
        prjout.println("<B>Sort By:</B>&nbsp; \n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
        prjout.println("<SELECT CLASS=\"HEADER\" NAME=\"sortby\">\n");
        prjout.println(HTMLHelpObj.sorthashbyValue(((Hashtable)tasksession.getAttribute("SORT_BY")).entrySet(), sortby));
        prjout.println("</SELECT>\n");
		prjout.println("</TD>\n");

        prjout.println("<TD CLASS=\"announce\" HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
        prjout.println("<B>Facility:</B>&nbsp; \n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" WIDTH=\"5%\"  COLSPAN=\"2\" ALIGN=\"LEFT\">\n");
        prjout.println("<SELECT CLASS=\"HEADER\" NAME=\"facilityid\">\n");
        prjout.println("<OPTION VALUE=\"ALL\">All</OPTION>\n");
        prjout.println(HTMLHelpObj.sorthashbyValue(((Hashtable)tasksession.getAttribute("PROJECT_FACILITY")).entrySet(), department));
        prjout.println("</SELECT>\n");
		prjout.println("</TD>\n");

		prjout.println("<TD WIDTH=\"15%\" HEIGHT=\"35\" CLASS=\"announce\">\n");
		prjout.println("<INPUT TYPE=\"radio\" NAME=\"keywordandoror\" value=\"and\" "+("and".equalsIgnoreCase(keywordandoror)?"checked":"")+">and\n");
		prjout.println("<INPUT TYPE=\"radio\" NAME=\"keywordandoror\" value=\"or\" "+("or".equalsIgnoreCase(keywordandoror)?"checked":"")+">or\n");
		prjout.println("&nbsp;\n");
		prjout.println("</TD>\n");

        prjout.println("<TD CLASS=\"announce\" HEIGHT=45 WIDTH=\"5%\" ALIGN=\"RIGHT\">\n");
        prjout.println("<INPUT TYPE=\"button\" ID=\"createxlsrpttbutton1\" VALUE=\"Excel\" NAME=\"createxlsrptkButton\" onclick= \"return createxlsrpt(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
        prjout.println("</TD>\n");
        prjout.println("<TD CLASS=\"announce\" ALIGN=\"left\" WIDTH=\"5%\" VALIGN=\"MIDDLE\">\n");
        prjout.println("<INPUT TYPE=\"submit\" ID=\"genbutton1\" VALUE=\"Print\" NAME=\"DatabseListkButton\" onclick= \"return printpdf(this)\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
        prjout.println("</TD>\n");
        prjout.println("</TR>\n");
        prjout.println("</TABLE>\n");

        prjout.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
        prjout.println("<tr><td>");
        prjout.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Search\">\n");
        prjout.println("</TD></tr>");
        prjout.println("</table>\n\n");
        prjout.println(HTMLHelpObj.printMessageinTable(Message));
    }
}