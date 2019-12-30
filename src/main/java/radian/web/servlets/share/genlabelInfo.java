package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * <p>Title: General Safety label information</p>
 * <p>Description: to lookup item or catalog part number and enter sfety label information</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 06-26-03 Added item_desc to the page
 * 08-11-03 - Showing the Hazard Code number whe they pull up the request
 * 11-14-03 - Fixed Query error
 * 05-24-04 - Showing all Hazard Values defined in the database not restricting to the HMIS values selected.
 */

public class genlabelInfo extends HttpServlet
{
    private ServerResourceBundle bundle=null;
    private TcmISDBModel db = null;
    private String ShowThis = "";
    private boolean doneUpdate = false;
    private String client = "";
    private boolean intcmIsApplication = false;
    private boolean allowupdate;
    public void setupdateStatus( boolean id )
    {
      this.allowupdate=id;
    }

    private boolean getupdateStatus() throws Exception
    {
      return this.allowupdate;
    }

    public genlabelInfo(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResponse(HttpServletRequest request,HttpServletResponse response,HttpSession labelsession) throws ServletException,IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        PersonnelBean personnelBean = (PersonnelBean) labelsession.getAttribute("personnelBean");
        if (personnelBean !=null) {
          Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
          intcmIsApplication = false;
          if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
            intcmIsApplication = true;
          }
        }

        String personnelid= labelsession.getAttribute( "PERSONNELID" ) == null ? "" : labelsession.getAttribute( "PERSONNELID" ).toString();
        client = bundle.getString("DB_CLIENT");

        String item_id=request.getParameter( "item_id" );
        if ( item_id == null )
          item_id="";
        item_id = item_id.trim();
        String Session=request.getParameter( "Session" );
        if ( Session == null )
          Session="0";
        String csm_part_no=request.getParameter( "csm_part_no" );
        if ( csm_part_no == null )
          csm_part_no = "";
        csm_part_no = csm_part_no.trim();
        String catalogId=request.getParameter( "catalogId" );
        if ( catalogId == null )
          catalogId="";
        catalogId=catalogId.trim();

        String donevvstuff = "";
        donevvstuff = labelsession.getAttribute( "VVSEGATELABELSTUFF" ) == null ? "" : labelsession.getAttribute( "VVSEGATELABELSTUFF" ).toString();

        if (!donevvstuff.equalsIgnoreCase("Yes"))
        {
          labelsession.setAttribute("CHEMICALSTORAGE",this.getchemicalstorage());
          labelsession.setAttribute("DISPOSABLECODE",this.getdisposalcode());
          labelsession.setAttribute("DISPOSECONTAINERCODE",this.getdspcontainercode());
          labelsession.setAttribute("FLAMMABILITY",this.getflamability());
          labelsession.setAttribute("HAZARD",this.gethazard());
          labelsession.setAttribute("HEALTH",this.gethealth());
          labelsession.setAttribute("PERSONNELPROTECTION",this.getpersonalproteqp());
          labelsession.setAttribute("REACTIVITY",this.getreactivity());
          labelsession.setAttribute("SIGNALWORD",this.getsignalword());
          labelsession.setAttribute("CATALOGDESC",this.getCatalogDescs());
          labelsession.setAttribute("VVSEGATELABELSTUFF","Yes");
        }

        if (Session.equalsIgnoreCase("gethazardvalues"))
        {
          String health   = request.getParameter("health");
          if (health == null)
              health = "";
          health = health.trim();

          String flamability   = request.getParameter("flamability");
          if (flamability == null)
              flamability = "";
          flamability = flamability.trim();

          String reactivity   = request.getParameter("reactivity");
          if (reactivity == null)
              reactivity = "";
          reactivity = reactivity.trim();

          buildHazardValues(health,flamability,reactivity,out);
        }
        else if (Session.equalsIgnoreCase("UPDATE"))
        {
          String health   = request.getParameter("health");
          if (health == null)
              health = "";
          health = health.trim();

          String flamability   = request.getParameter("flamability");
          if (flamability == null)
              flamability = "";
          flamability = flamability.trim();

          String reactivity   = request.getParameter("reactivity");
          if (reactivity == null)
              reactivity = "";
          reactivity = reactivity.trim();

          String chemicalstorage   = request.getParameter("chemicalstorage");
          if (chemicalstorage == null)
              chemicalstorage = "";
          chemicalstorage = chemicalstorage.trim();

          String signalword   = request.getParameter("signalword");
          if (signalword == null)
              signalword = "";
          signalword = signalword.trim();

          String perprotectiveequip   = request.getParameter("perprotectiveequip");
          if (perprotectiveequip == null)
              perprotectiveequip = "";
          perprotectiveequip = perprotectiveequip.trim();

          String [] hazardArray = {"",""};
          hazardArray  = request.getParameterValues("hazard");
          String hazardcodefromarray1 = "";
          String hazardcodefromarray2 = "";

          if (hazardArray != null)
          {
            for (int loop  = 0 ; loop  < hazardArray.length  ; loop++)
            {
              if (loop == 0)
              {
              hazardcodefromarray1 = hazardArray[loop];
              }
              if (loop == 1)
              {
              hazardcodefromarray2 = hazardArray[loop];
              }
            }
          }

          String disposablecode   = request.getParameter("disposablecode");
          if (disposablecode == null)
              disposablecode = "";
          disposablecode = disposablecode.trim();

          String disposablecontainercode   = request.getParameter("disposablecontainercode");
          if (disposablecontainercode == null)
              disposablecontainercode = "";
          disposablecontainercode = disposablecontainercode.trim();

          int testifrecordexists = 0;
          if ( item_id.length() > 0)
          {
              try
              {
                testifrecordexists = DbHelpers.countQuery(db,"select count(*) from inventory_group_item_label where INVENTORY_GROUP='NRM' and ITEM_ID="+item_id+" ");

                String facpartQuery = "";
                if (testifrecordexists == 0) //insert
                {
                    facpartQuery = "insert into inventory_group_item_label (ITEM_ID,INVENTORY_GROUP,CAT_PART_NO,HEALTH,FLAMABILITY,REACTIVITY,CHEMICAL_STORAGE,SIGNAL_WORD,PERSONAL_PROTECTIVE_EQUIPMENT,DISPOSAL_CODE,DISPOSAL_CONTAINER_CODE,HAZARD_CODE1,HAZARD_CODE2) ";
                    facpartQuery +=" values ("+item_id+",'NRM','"+csm_part_no.trim()+"','"+health+"','"+flamability+"','"+reactivity+"','"+chemicalstorage+"','"+signalword+"','"+perprotectiveequip+"','"+disposablecode+"','"+disposablecontainercode+"','"+hazardcodefromarray1+"','"+hazardcodefromarray2+"')";
                }
                else   //update
                {
                    facpartQuery = "update inventory_group_item_label set HEALTH='"+health+"',FLAMABILITY='"+flamability+"',REACTIVITY='"+reactivity+"',CHEMICAL_STORAGE='"+chemicalstorage+"',SIGNAL_WORD='"+signalword+"',";
                    facpartQuery +=" PERSONAL_PROTECTIVE_EQUIPMENT='"+perprotectiveequip+"',DISPOSAL_CODE='"+disposablecode+"',DISPOSAL_CONTAINER_CODE='"+disposablecontainercode+"',HAZARD_CODE1='"+hazardcodefromarray1+"',HAZARD_CODE2='"+hazardcodefromarray2+"' where  INVENTORY_GROUP='NRM' and ITEM_ID='"+item_id+"'";
                }

                //www
                db.doUpdate(facpartQuery);
             }
             catch (Exception er)
             {
               er.printStackTrace();
               buildDetails(out,item_id,""+er.getMessage()+"<BR>An error occured and a email has been sent to the person concerned. Please Try Again.",labelsession,csm_part_no,catalogId);
               out.close();
               //Send Email to Nawaz
               HelpObjs.sendMail(db,"Error in General Label Information Update","Error in Updating Values in General Label Information for: "+item_id+"     "+er.getMessage()+"",86405,false);
               return;
             }

             buildDetails(out,item_id,"",labelsession,csm_part_no,catalogId);
           }
           else
           {
             buildDetails(out,item_id,"Please choose a vlid Item Id",labelsession,csm_part_no,catalogId);
           }
        }
        //If session is not 1 just print the list of Request ID's
        else
        {
        buildDetails(out,item_id,"",labelsession,csm_part_no,catalogId);
        }
        out.close();
    }

    public Vector gethealth()
    {
      String query="select HEALTH,HEALTH_DESCRIPTION from vv_health order by HEALTH";

	  DBResultSet dbrs = null;
      ResultSet rs = null;
      Hashtable result=null;
      Vector ResultV=new Vector();
      try
      {
        dbrs=db.doQuery( query );
        rs=dbrs.getResultSet();
        while ( rs.next() )
        {
          result=new Hashtable();
          String faci= ( rs.getString( "HEALTH" ) == null ? "" : rs.getString( "HEALTH" ) );
          String facn= ( rs.getString( "HEALTH_DESCRIPTION" ) == null ? "" : rs.getString( "HEALTH_DESCRIPTION" ) );
          result.put( facn,faci );
          ResultV.addElement( result );
        }

      }
      catch ( Exception e )
      {
        e.printStackTrace();
        HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
      }
      finally
      {
        dbrs.close();
      }
      return ResultV;
     }

     public Vector getflamability()
     {
       String query="select FLAMABILITY,FLAMABILITY_DESCRIPTION from vv_flamability order by FLAMABILITY";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "FLAMABILITY" ) == null ? "" : rs.getString( "FLAMABILITY" ) );
           String facn= ( rs.getString( "FLAMABILITY_DESCRIPTION" ) == null ? "" : rs.getString( "FLAMABILITY_DESCRIPTION" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

     public Vector getreactivity()
     {
       String query="select REACTIVITY,REACTIVITY_DESCRIPTION from vv_reactivity order by REACTIVITY";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "REACTIVITY" ) == null ? "" : rs.getString( "REACTIVITY" ) );
           String facn= ( rs.getString( "REACTIVITY_DESCRIPTION" ) == null ? "" : rs.getString( "REACTIVITY_DESCRIPTION" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

     public Vector getchemicalstorage()
     {
       String query="select CHEMICAL_STORAGE,CHEMICAL_STORAGE_DESCRIPTION from vv_chemical_storage order by CHEMICAL_STORAGE";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "CHEMICAL_STORAGE" ) == null ? "" : rs.getString( "CHEMICAL_STORAGE" ) );
           String facn= ( rs.getString( "CHEMICAL_STORAGE_DESCRIPTION" ) == null ? "" : rs.getString( "CHEMICAL_STORAGE_DESCRIPTION" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

     public Vector getsignalword()
     {
       String query="select SIGNAL_WORD,SIGNAL_WORD_DESCRIPTION from vv_signal_word order by SIGNAL_WORD ";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "SIGNAL_WORD" ) == null ? "" : rs.getString( "SIGNAL_WORD" ) );
           String facn= ( rs.getString( "SIGNAL_WORD_DESCRIPTION" ) == null ? "" : rs.getString( "SIGNAL_WORD_DESCRIPTION" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

     public Vector getpersonalproteqp()
     {
       String query="select PERSONAL_PROTECTIVE_EQUIPMENT,PPE_DESCRIPTION from vv_ppe order by PERSONAL_PROTECTIVE_EQUIPMENT";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "PERSONAL_PROTECTIVE_EQUIPMENT" ) == null ? "" : rs.getString( "PERSONAL_PROTECTIVE_EQUIPMENT" ) );
           String facn= ( rs.getString( "PPE_DESCRIPTION" ) == null ? "" : rs.getString( "PPE_DESCRIPTION" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

     public Vector gethazard()
     {
       String query="select HEALTH,HEALTH_DESCRIPTION from vv_health order by HEALTH";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "HEALTH" ) == null ? "" : rs.getString( "HEALTH" ) );
           String facn= ( rs.getString( "HEALTH" ) == null ? "" : rs.getString( "HEALTH" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

     public Vector getCatalogDescs()
     {
       String query="select distinct CATALOG_DESC from catalog where catalog_id not like '%TCM%' and CATALOG_DESC is not null order by CATALOG_DESC";
       DBResultSet dbrs=null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "CATALOG_DESC" ) == null ? "" : rs.getString( "CATALOG_DESC" ) );
           result.put( faci,faci );
           ResultV.addElement( result );
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
       return ResultV;
     }

     public Vector getdisposalcode()
     {
       String query="select DISPOSAL_CODE,DISPOSAL_CODE_DESCRIPTION from vv_disposal_code order by DISPOSAL_CODE";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "DISPOSAL_CODE" ) == null ? "" : rs.getString( "DISPOSAL_CODE" ) );
           String facn= ( rs.getString( "DISPOSAL_CODE_DESCRIPTION" ) == null ? "" : rs.getString( "DISPOSAL_CODE_DESCRIPTION" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

     public Vector getdspcontainercode()
     {
       String query="select DISPOSAL_CONTAINER_CODE, DISPOSAL_CONTAINER_CODE_DESC from vv_disposal_container_code  order by  DISPOSAL_CONTAINER_CODE";
	   DBResultSet dbrs = null;
       ResultSet rs=null;
       Hashtable result=null;
       Vector ResultV=new Vector();
       try
       {
         dbrs=db.doQuery( query );
         rs=dbrs.getResultSet();
         while ( rs.next() )
         {
           result=new Hashtable();
           String faci= ( rs.getString( "DISPOSAL_CONTAINER_CODE" ) == null ? "" : rs.getString( "DISPOSAL_CONTAINER_CODE" ) );
           String facn= ( rs.getString( "DISPOSAL_CONTAINER_CODE_DESC" ) == null ? "" : rs.getString( "DISPOSAL_CONTAINER_CODE_DESC" ) );
           result.put( facn,faci );
           ResultV.addElement( result );
         }

       }
       catch ( Exception e )
       {
         e.printStackTrace();
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
       }
       finally
       {
         dbrs.close();
       }
       return ResultV;
     }

    public void buildHazardValues( String health,String flamability,String reactivity,PrintWriter out)
   {
    //StringBuffer Msgn = new StringBuffer();
    //StringBuffer Msgbody = new StringBuffer();
	DBResultSet dbrs = null;
	ResultSet searchRs = null;
    out.println(radian.web.HTMLHelpObj.printHTMLHeader(""+client+" Hazard Values","seagatelabels.js",intcmIsApplication));
    out.println("</HEAD>\n");

    //StringBuffer Msgtemp = new StringBuffer();

    //Build the Java Script Here and Finish the thing
    out.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    out.println("<!--\n");

    out.println("function sendSupplierData()\n");
    out.println("{\n");

    out.println("opener.removeAllOptionItem(opener.document.getElementById(\"hazard\"));\n");
    out.println("opener.addOptionItem(opener.document.getElementById(\"hazard\"),'','Please Select');\n");

    try
    {
      //dbrs=db.doQuery( "select distinct HAZARD_CODE,HAZARD_CODE_DESC from hazards where (property,property_value) in ( ('HEALTH'," + health + "),('REACTIVITY'," + reactivity + "),('FLAMABILITY'," + flamability + ") ) order by HAZARD_CODE desc" );
	  dbrs=db.doQuery( "select distinct HAZARD_CODE,HAZARD_CODE_DESC from hazards order by HAZARD_CODE");
      searchRs=dbrs.getResultSet();

      while ( searchRs.next() )
      {
        String hazcode=searchRs.getString( "HAZARD_CODE" ) == null ? "" : searchRs.getString( "HAZARD_CODE" );
        String hazcodedesc=searchRs.getString( "HAZARD_CODE_DESC" ) == null ? "" : searchRs.getString( "HAZARD_CODE_DESC" );
        out.println( "opener.addOptionItem(opener.document.getElementById(\"hazard\"),'" + hazcode + "','" + hazcode + ":" + hazcodedesc + "');\n" );
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

    out.println("window.close();\n");
    out.println(" }\n");
    out.println("// -->\n</SCRIPT>\n");
    //out.println(Msgtemp);
    out.println("<BODY onLoad=\"sendSupplierData()\"><BR><BR>\n");
	out.println("<FORM METHOD=\"POST\" name=\"revDateLike\" action=\"\">\n");
    out.println("<CENTER><BR><BR>\n");
    out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
    out.println("</FORM></BODY></HTML>\n");
    //out.println(Msgbody);

    return;
   }

    //This method builds the list of all request ids that require catalog Label Information
    public void buildDetails(PrintWriter out, String itemid1, String messgww,HttpSession labelSession1,String catpartnom1,String catalogid )
    {
        //StringBuffer Msgd = new StringBuffer();
        Hashtable hazardData = new Hashtable();
        Vector hazardVector = new Vector();
        String itemid = "";
        String catpartnom = "";
		DBResultSet dbrs = null;
		ResultSet searchRs = null;
        out.println(radian.web.HTMLHelpObj.printHTMLHeader(""+client+" General Label Information","seagatelabels.js",intcmIsApplication));
        out.println("</HEAD>\n");
        out.println("<BODY>\n");

        out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
        out.println( "<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n" );
        out.println( "</DIV>\n" );
        out.println( "<DIV ID=\"MAINPAGE\" STYLE=\"\">\n" );
        String call_servlet = radian.web.tcmisResourceLoader.getgenlableinfourl();
        out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>"+client+" Label Information</B>\n"));
        out.println(radian.web.HTMLHelpObj.printMessageinTable("<B>"+messgww+"</B>\n"));

        out.println("<FORM METHOD=\"POST\" name=\"MainForm\" action=\""+call_servlet+"\">\n");

        int totalrecords = 0;
        int labelinfono = 0;
        int hazardrecords = 0;

        String health = "";
        String flamability = "";
        String reactivity = "";
        String chemical_storage = "";
        String signal_word = "";
        String personal_protective_equipment = "";
        String disposal_code = "";
        String disposal_container_code = "";
        String hazard_code1 = "";
        String hazard_code2 = "";
        Vector selechazcodes = new Vector();

        try
        {
          String searchqu = "";
          /*if ( itemid.length() > 0 && catpartnom.length() > 0 )
          {
            searchqu = "select * from inventory_group_item_label where ITEM_ID="+itemid+" and CAT_PART_NO='"+catpartnom+"'";
          }
          else*/ if ( itemid1.length() > 0)
          {
            searchqu = "select * from inventory_group_item_label where ITEM_ID="+itemid1+" ";
          }
          else if ( catpartnom1.length() > 0 )
          {
            searchqu = "select * from inventory_group_item_label where CAT_PART_NO='"+catpartnom1+"'";
          }

          if (catalogid.length() > 0 && searchqu.length() > 0)
          {
            searchqu += " and CATALOG_ID='"+catalogid+"'";
          }

          if (searchqu.length() > 0)
          {
            dbrs=db.doQuery( searchqu );
            searchRs=dbrs.getResultSet();

            while ( searchRs.next() )
            {
              labelinfono++;

              itemid=searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ).trim();
              catpartnom=searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ).trim();
              health=searchRs.getString( "HEALTH" ) == null ? "" : searchRs.getString( "HEALTH" ).trim();
              flamability=searchRs.getString( "FLAMABILITY" ) == null ? "" : searchRs.getString( "FLAMABILITY" ).trim();
              reactivity=searchRs.getString( "REACTIVITY" ) == null ? "" : searchRs.getString( "REACTIVITY" ).trim();
              chemical_storage=searchRs.getString( "CHEMICAL_STORAGE" ) == null ? "" : searchRs.getString( "CHEMICAL_STORAGE" ).trim();
              signal_word=searchRs.getString( "SIGNAL_WORD" ) == null ? "" : searchRs.getString( "SIGNAL_WORD" ).trim();
              personal_protective_equipment=searchRs.getString( "PERSONAL_PROTECTIVE_EQUIPMENT" ) == null ? "" :
                                            searchRs.getString( "PERSONAL_PROTECTIVE_EQUIPMENT" ).trim();
              disposal_code=searchRs.getString( "DISPOSAL_CODE" ) == null ? "" : searchRs.getString( "DISPOSAL_CODE" ).trim();
              disposal_container_code=searchRs.getString( "DISPOSAL_CONTAINER_CODE" ) == null ? "" : searchRs.getString( "DISPOSAL_CONTAINER_CODE" ).trim();

              hazard_code1=searchRs.getString( "HAZARD_CODE1" ) == null ? "" : searchRs.getString( "HAZARD_CODE1" ).trim();
              hazard_code2=searchRs.getString( "HAZARD_CODE2" ) == null ? "" : searchRs.getString( "HAZARD_CODE2" ).trim();

            }

            selechazcodes.addElement( hazard_code1 );
            selechazcodes.addElement( hazard_code2 );

            if ( labelinfono == 0 )
            {
              String queryserch = "";
              int counttiem = 0;

              if ( itemid1.length() > 0 )
              {
                queryserch =  "select ITEM_ID,CAT_PART_NO from catalog_part_item_group where ITEM_ID = "+itemid1+"";
              }
              else if ( catpartnom1.length() > 0 )
              {
                queryserch = "select ITEM_ID,CAT_PART_NO from catalog_part_item_group where CAT_PART_NO = '"+catpartnom1+"'";
              }

              if ( queryserch.length() > 0 )
              {
                dbrs=db.doQuery( queryserch);
                searchRs=dbrs.getResultSet();

                while ( searchRs.next() )
                {
                itemid =searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ).trim();
                catpartnom=searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ).trim();
                counttiem ++;
                }
              }

              if (counttiem ==0)
              {
                if ( itemid1.length() > 0 )
                {
                  itemid=itemid1;
                }
                else if ( catpartnom1.length() > 0 )
                {
                  catpartnom=catpartnom1;
                }
              }

              /*dbrs=db.doQuery( "select fx_default_health(" + itemid + ") HEALTH from dual" );
              searchRs=dbrs.getResultSet();
              while ( searchRs.next() )
              {
                health=searchRs.getString( "HEALTH" ) == null ? "" : searchRs.getString( "HEALTH" ).trim();
              }

              dbrs=db.doQuery( "select fx_default_flamability(" + itemid + ") FLAMABILITY from dual" );
              searchRs=dbrs.getResultSet();
              while ( searchRs.next() )
              {
                flamability=searchRs.getString( "FLAMABILITY" ) == null ? "" : searchRs.getString( "FLAMABILITY" ).trim();
              }

              dbrs=db.doQuery( "select fx_default_reactivity(" + itemid + ") REACTIVITY from dual" );
              searchRs=dbrs.getResultSet();
              while ( searchRs.next() )
              {
                reactivity=searchRs.getString( "REACTIVITY" ) == null ? "" : searchRs.getString( "REACTIVITY" ).trim();
              }

              if ( health.length() > 0 && flamability.length() > 0 && reactivity.length() > 0 )
              {
                dbrs=db.doQuery( "select * from hazards where (property,property_value) in ( ('HEALTH'," + health + "),('REACTIVITY'," + reactivity + "),('FLAMABILITY'," + flamability + ") ) order by HAZARD_CODE desc" );
                searchRs=dbrs.getResultSet();

                while ( searchRs.next() )
                {
                  hazardrecords++;
                  hazardData=new Hashtable();
                  String hazcode=searchRs.getString( "HAZARD_CODE" ) == null ? "" : searchRs.getString( "HAZARD_CODE" );
                  String hazcodedesc=searchRs.getString( "HAZARD_CODE_DESC" ) == null ? "" : searchRs.getString( "HAZARD_CODE_DESC" );
                  hazardData.put( hazcodedesc,hazcode );
                  hazardVector.addElement( hazardData );
                }
              }*/
            }
            else if (itemid.trim().length() > 0)
            {
              String hazadescqur="SELECT fpl.HAZARD_CODE1,fpl.HAZARD_CODE2 ,(select distinct hz1.HAZARD_CODE_DESC  from hazards hz1 where HAZARD_CODE=HAZARD_CODE1 and ";
              hazadescqur+=" decode(hz1.property ,'HEALTH',fpl.health,'REACTIVITY',fpl.REACTIVITY,'FLAMABILITY',fpl.FLAMABILITY)=hz1.property_value ";
              hazadescqur+=" ) HAZARD_CODE_DESC1, ";
              hazadescqur+=" (select distinct hz2.HAZARD_CODE_DESC  from hazards hz2 where HAZARD_CODE=HAZARD_CODE2 and ";
              hazadescqur+=" decode(hz2.property ,'HEALTH',fpl.health,'REACTIVITY',fpl.REACTIVITY,'FLAMABILITY',fpl.FLAMABILITY)=hz2.property_value ";
              hazadescqur+="  ) HAZARD_CODE_DESC2  from inventory_group_item_label fpl where fpl.item_id = " + itemid + " ";

              dbrs=db.doQuery( hazadescqur );
              searchRs=dbrs.getResultSet();

              while ( searchRs.next() )
              {
                hazardrecords++;
                hazardData=new Hashtable();
                String hazcode=searchRs.getString( "HAZARD_CODE1" ) == null ? "" : searchRs.getString( "HAZARD_CODE1" );
                String hazcodedesc=searchRs.getString( "HAZARD_CODE_DESC1" ) == null ? "" : searchRs.getString( "HAZARD_CODE_DESC1" );
                if (hazcode.length() > 0)
                {
                  hazardData.put( ""+hazcode+":"+hazcodedesc+"",hazcode );
                  hazardVector.addElement( hazardData );
                }

                hazardData=new Hashtable();
                hazcode=searchRs.getString( "HAZARD_CODE2" ) == null ? "" : searchRs.getString( "HAZARD_CODE2" );
                hazcodedesc=searchRs.getString( "HAZARD_CODE_DESC2" ) == null ? "" : searchRs.getString( "HAZARD_CODE_DESC2" );
                if (hazcode.length() > 0)
                {
                  hazardData.put( ""+hazcode+":"+hazcodedesc+"",hazcode );
                  hazardVector.addElement( hazardData );
                }
              }
            }
          }

          String itemdesc = "";
          if (itemid.trim().length() > 0)
          {
            dbrs=db.doQuery( "select fx_item_desc("+itemid+",'MA') ITEM_DESC from dual" );
            searchRs=dbrs.getResultSet();
            while ( searchRs.next() )
            {
              itemdesc =searchRs.getString( "ITEM_DESC" ) == null ? "" : searchRs.getString( "ITEM_DESC" ).trim();
            }
          }


            out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
            out.println( "<TR>\n" );

            out.println( "<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
            out.println( "<B>Haas Item ID:</B>&nbsp;\n" );
            out.println( "</TD>\n" );
            out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
            out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"item_id\" value=\"" + itemid + "\" size=\"10\">\n" );
            out.println( "</TD>\n" );

            out.println( "<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
            out.println( "<B>Seagate Catalog Part Number:</B>&nbsp;\n" );
            out.println( "</TD>\n" );
            out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" CLASS=\"announce\">\n" );
            out.println( "<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"csm_part_no\" value=\"" + catpartnom + "\" size=\"10\">\n" );
            out.println( "</TD>\n" );

            // Search
            out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
            out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n" );
            out.println( "</TD>\n" );

            //Update
            out.println( "<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
            if (this.getupdateStatus())
            {
              out.println( "<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Update\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n" );
            }
            else
            {
              out.println( "&nbsp;\n" );
            }
            out.println( "</TD>\n" );

            out.println( "</TR>\n" );


            out.println( "<TR>\n<TD WIDTH=\"5%\" CLASS=\"Inwhite\" VALIGN=\"RIGHT\"><B>Item Description: </B></TD>\n" );
            out.println( "<TD WIDTH=\"15%\" ALIGN=\"left\" COLSPAN=\"5\">\n" );
            out.println(itemdesc);
            out.println( "</TD>\n</TR>\n" );

            out.println( "</TABLE>\n" );

            out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" CLASS=\"moveup\">\n" );
            out.println( "<TR><TD BGCOLOR=\"#000066\" COLSPAN=\"2\" CLASS=\"heading\">Label Information</TD></TR>\n" );
            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Health: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\">\n" );

            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"health\" size=\"1\" onChange=\"removeAllHazards()\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDescDropDown( ( Vector ) labelSession1.getAttribute( "HEALTH" ),health ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Flamability: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"flamability\" size=\"1\" onChange=\"removeAllHazards()\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDescDropDown( ( Vector ) labelSession1.getAttribute( "FLAMMABILITY" ),flamability ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Reactivity: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"reactivity\" size=\"1\" onChange=\"removeAllHazards()\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDescDropDown( ( Vector ) labelSession1.getAttribute( "REACTIVITY" ),reactivity ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Chemical Storage: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"chemicalstorage\" size=\"1\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDescDropDown( ( Vector ) labelSession1.getAttribute( "CHEMICALSTORAGE" ),chemical_storage ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Signal Word: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"signalword\" size=\"1\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDropDown( ( Vector ) labelSession1.getAttribute( "SIGNALWORD" ),signal_word ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Personal Protective Equip: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"perprotectiveequip\" size=\"1\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDescDropDown( ( Vector ) labelSession1.getAttribute( "PERSONNELPROTECTION" ),personal_protective_equipment ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Hazard: </B><BR><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='hazardValues' OnClick=\"getHazardvalues()\" value=\"Get Hazard Values\"></TD>\n" );
            out.println( "<TD WIDTH=\"85%\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"hazard\" size=\"4\" multiple onChange=\"checkForTwo(this)\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            if ( hazardrecords > 0 )
            {
              out.println( radian.web.HTMLHelpObj.getmultipleDropDown( hazardVector,selechazcodes ) );
            }
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Disposal Code: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"disposablecode\" size=\"1\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDescDropDown( ( Vector ) labelSession1.getAttribute( "DISPOSABLECODE" ),disposal_code ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );

            out.println( "<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Disposal Container Code: </B></TD>\n" );
            out.println( "<TD WIDTH=\"85%\">\n" );
            out.println( "<SELECT CLASS=\"HEADER\" NAME=\"disposablecontainercode\" size=\"1\">\n" );
            out.println( "<OPTION VALUE=\"\">Please Select</OPTION>\n" );
            out.println( radian.web.HTMLHelpObj.getDescDropDown( ( Vector ) labelSession1.getAttribute( "DISPOSECONTAINERCODE" ),disposal_container_code ) );
            out.println( "</SELECT>\n" );
            out.println( "</TD>\n</TR>\n" );
          }
          catch ( Exception e1 )
          {
            e1.printStackTrace();
            out.println( radian.web.HTMLHelpObj.printHTMLHeader( "" + client + " Label Information","",intcmIsApplication ) );
            out.println( "</HEAD>\n" );
            out.println( "<BODY>\n" );
            out.println( radian.web.HTMLHelpObj.printMessageinTable( "An Error Occured While Querying the Databse" ) );
            out.println( "</TABLE>\n" );
            out.println( "</BODY>\n</HTML>\n" );
            //out.println( Msgd );
            return;
          }
          finally
          {
            if ( dbrs != null )
            {
              dbrs.close();
            }
          }

          out.println( "</TABLE>\n" );
          out.println( "<INPUT TYPE=\"hidden\" NAME=\"Session\" VALUE=\"\">\n" );
          out.println( "</FORM>\n" );
          out.println( "</BODY>\n</HTML>\n" );
          //out.println(Msgd);
    }
}

