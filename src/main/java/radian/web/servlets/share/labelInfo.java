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
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.NChemObj;

/**
 *
 * 11-12-02
 * Known bug - The hazard selection combo box will show only onse selected option when it is pulled up from existing record in
 * facility_part_label this is because the getdropdown function in htmlhelpobj has only one matching check
 *
 * 02-28-03
 * Adding Order By in the hazard query
 * 06-03-03
 * Changed to do the label info stuff before a csm part number is associated.Using item_id to do the insert
 * 08-11-03 - Showing the Hazard Code number whe they pull up the request
 * 08-12-03 - Selecting distinct HAZARD_CODE,HAZARD_CODE_DESC from the database
 * 11-14-03 - Fixed Query error
 * 05-24-04 - Showing all Hazard Values defined in the database not restricting to the HMIS values selected.
 * 07-19-04 - Changed query to improve performance
 */

public class labelInfo extends HttpServlet
{
    private ServerResourceBundle bundle=null;
    private TcmISDBModel db = null;
    private String ShowThis = "";
    private String Cat_Servlet = "";
    private boolean doneUpdate = false;
    private String client = "";
    private boolean intcmIsApplication = false;

    public labelInfo(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void doResponse(HttpServletRequest request,HttpServletResponse response,HttpSession labelsession) throws ServletException,IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        PersonnelBean personnelBean = (PersonnelBean) labelsession.getAttribute("personnelBean");
        if (personnelBean != null) {
          Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
          if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
            intcmIsApplication = true;
          }
        }

        String personnelid= labelsession.getAttribute( "PERSONNELID" ) == null ? "" : labelsession.getAttribute( "PERSONNELID" ).toString();

        client = bundle.getString("DB_CLIENT");
        Cat_Servlet = bundle.getString("LABELING_SERVLET");

        String request_id=request.getParameter( "request_id" );
        if ( request_id == null )
          request_id="";
        String Session=request.getParameter( "Session" );
        if ( Session == null )
          Session="0";
        String csm_part_no=request.getParameter( "csm_part_no" );
        if ( csm_part_no == null )
          csm_part_no = "";
        String uoi   = request.getParameter("unit_of_issue");
        if (uoi == null)
            uoi = "";
        String uop   = request.getParameter("unit_of_purchase");
        if (uop == null)
            uop = "";

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
          labelsession.setAttribute("VVSEGATELABELSTUFF","Yes");
        }

        if(Session.equalsIgnoreCase("1"))
        {
          //If there is no request ID print All request Id's
          if (request_id.length() < 1)
          {
           buildRequests(out,"");
          }
          else
          {
          buildDetails(out,request_id,"",labelsession);
          }
        }
        else if (Session.equalsIgnoreCase("gethazardvalues"))
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
        else if (Session.equalsIgnoreCase("Update"))
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

          String haas_item_no  = request.getParameter("haas_item_no");
          if (haas_item_no == null)
              haas_item_no = "";
          haas_item_no = haas_item_no.trim();

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

          String catalogId   = request.getParameter("catalogId");
          if (catalogId == null)
              catalogId = "";
          catalogId = catalogId.trim();

          String disposablecode   = request.getParameter("disposablecode");
          if (disposablecode == null)
              disposablecode = "";
          disposablecode = disposablecode.trim();

          String disposablecontainercode   = request.getParameter("disposablecontainercode");
          if (disposablecontainercode == null)
              disposablecontainercode = "";
          disposablecontainercode = disposablecontainercode.trim();

          int testifrecordexists = 0;
          if (catalogId.length() > 0 && haas_item_no.length() > 0)
          {
              try
              {
                testifrecordexists = DbHelpers.countQuery(db,"select count(*) from inventory_group_item_label where INVENTORY_GROUP='NRM' and ITEM_ID="+haas_item_no+" ");

                String facpartQuery = "";
                if (testifrecordexists == 0) //insert
                {
                    facpartQuery = "insert into inventory_group_item_label (ITEM_ID,INVENTORY_GROUP,CAT_PART_NO,HEALTH,FLAMABILITY,REACTIVITY,CHEMICAL_STORAGE,SIGNAL_WORD,PERSONAL_PROTECTIVE_EQUIPMENT,DISPOSAL_CODE,DISPOSAL_CONTAINER_CODE,HAZARD_CODE1,HAZARD_CODE2) ";
                    facpartQuery +=" values ("+haas_item_no+",'NRM','"+csm_part_no.trim()+"','"+health+"','"+flamability+"','"+reactivity+"','"+chemicalstorage+"','"+signalword+"','"+perprotectiveequip+"','"+disposablecode+"','"+disposablecontainercode+"','"+hazardcodefromarray1+"','"+hazardcodefromarray2+"')";
                }
                else   //update
                {
                    facpartQuery = "update inventory_group_item_label set HEALTH='"+health+"',FLAMABILITY='"+flamability+"',REACTIVITY='"+reactivity+"',CHEMICAL_STORAGE='"+chemicalstorage+"',SIGNAL_WORD='"+signalword+"',";
                    facpartQuery +=" PERSONAL_PROTECTIVE_EQUIPMENT='"+perprotectiveequip+"',DISPOSAL_CODE='"+disposablecode+"',DISPOSAL_CONTAINER_CODE='"+disposablecontainercode+"',HAZARD_CODE1='"+hazardcodefromarray1+"',HAZARD_CODE2='"+hazardcodefromarray2+"' where  INVENTORY_GROUP='NRM' and ITEM_ID='"+haas_item_no+"'";
                }

                //www
                db.doUpdate(facpartQuery);
             }
             catch (Exception er)
             {
               er.printStackTrace();
               buildDetails(out,request_id,"An error occured and a email has been sent to the person concerned. Please Try Again.",labelsession);
               out.close();
               //Send Email to Trong
               HelpObjs.sendMail(db,"Error in Label Information Update","Error in Updating Values in Catalog Add Request New for: "+request_id,86405,false);

               return;
             }

            //www
            Hashtable h = new Hashtable();
            h.put("REQ_ID",request_id);
            h.put("USER_ID",personnelid);
            String[] vec = new String[2];
            vec[0] = "Label Info";
            vec[1] = "";
            h.put("ROLES_DATA",vec);

            try
            {
                NChemObj NcObj = new NChemObj(bundle,db);
                String temp = NcObj.approveRequest(h);
                if ("Error".equalsIgnoreCase(temp)) {
                  buildDetails(out,request_id,"An error occured and a email has been sent to the person concerned.",labelsession);
                  doneUpdate = false;
                  return;
                }else {
                  doneUpdate = true;
                }
            }
            catch (Exception e)
            {
               e.printStackTrace();
               buildDetails(out,request_id,"An error occured and a email has been sent to the person concerned.",labelsession);
               out.close();
               //Send Email to Trong
               HelpObjs.sendMail(db,"Error in Label Information Update","Error in Calling method in NchemObj for: "+request_id,86030,false);
               return;
            }

             if (doneUpdate) //Everything Went well
             {
             buildRequests(out,"Update of Label Information Was Sucessfull.");

             //For a Seperate Page Use This
             //buildSeperateScreen(out,"Update of Catalog Label Information Was Sucessfull.");
             }

             /*else   //Something went wrong while updating the request
             {
             buildDetails(out,request_id,"Something Went wrong Please Try Again.");
             }*/
           }
        }
        //If session is not 1 just print the list of Request ID's
        else
        {
        buildRequests(out,"");
        }
        out.close();
    }

    public Vector gethealth()
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
         HelpObjs.monitor( 1,"Error object(" + this.getClass().getName() + "): " + e.getMessage(),null );
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
    public void buildDetails(PrintWriter out, String requestID, String messg,HttpSession labelSession1)
    {
        //StringBuffer Msgd = new StringBuffer();
        StringBuffer Msgd1 = new StringBuffer();
		DBResultSet dbrs = null;
        ResultSet searchRs = null;
        Vector requestData = new Vector();
        Hashtable requestDataH = new Hashtable();
        Hashtable hazardData = new Hashtable();
        Vector hazardVector = new Vector();
        Vector selechazcodes = new Vector();

        String material_id = "";

        if (messg.length() >1){ShowThis = messg;}
        out.println(radian.web.HTMLHelpObj.printHTMLHeader(""+client+" Label Information","seagatelabels.js",intcmIsApplication));
        out.println("</HEAD>\n");

        if (messg.length() >1)
        {
        out.println("<BODY onload =\"showMsg()\">\n");
        }
        else
        {
        out.println("<BODY>\n");
        }

        out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>"+client+" Label Information&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Request Information</B>\n"));
        out.println("<FORM METHOD=\"POST\" name=\"MainForm\" action=\""+Cat_Servlet+"Session=Update\" onsubmit =\"return CheckValues(this)\">\n");

        String detailquery = "select xx.full_name,xx.phone,xx.FACILITY_NAME,xx.APPLICATION_DESC,xx.REQUEST_ID,xx.part_id,"+
        "xx.CUSTOMER_REQUEST_ID,xx.MANUFACTURER,xx.MATERIAL_DESC,xx.GRADE,xx.MFG_TRADE_NAME, xx.CUSTOMER_MSDS_NUMBER,xx.MFG_CATALOG_ID,"+
        "xx.PACKAGING,xx.SUGGESTED_VENDOR,xx.VENDOR_CONTACT_NAME, xx.VENDOR_CONTACT_PHONE,xx.VENDOR_CONTACT_FAX, xx.VENDOR_CONTACT_EMAIL,"+
        "xx.VENDOR_PART_NO, xx.CAT_PART_NO,xx.ITEM_ID,xx.shelf_life,xx.shelf_life_unit,xx.shelf_life_basis,"+
        "xx.storage_temp, xx.catalog_id,xx.COMPONENTS_PER_ITEM,xx.SAMPLE_ONLY, min(z.material_id) material_id"+
        " from "+
        "(select c.last_name || ', ' || c.first_name full_name,c.phone,f.FACILITY_NAME,g.APPLICATION_DESC,a.REQUEST_ID,d.part_id, "+
        "a.CUSTOMER_REQUEST_ID,d.MANUFACTURER,d.MATERIAL_DESC,d.GRADE,d.MFG_TRADE_NAME, "+
        "d.CUSTOMER_MSDS_NUMBER,d.MFG_CATALOG_ID,d.part_size|| ' ' || d.SIZE_UNIT || ' ' || d.PKG_STYLE PACKAGING,d.SUGGESTED_VENDOR,d.VENDOR_CONTACT_NAME, "+
        "d.VENDOR_CONTACT_PHONE,d.VENDOR_CONTACT_FAX, d.VENDOR_CONTACT_EMAIL,a.VENDOR_PART_NO, a.CAT_PART_NO,d.ITEM_ID,"+
        //"a.shelf_life||' '||a.shelf_life_unit||' '||decode(a.shelf_life_basis,'M','Manufactured','S','Shipped','R','Received')  shelf_life,"+
        "d.shelf_life_days shelf_life,'days' shelf_life_unit,decode(d.shelf_life_basis,'M','Manufactured','S','Shipped','R','Received') shelf_life_basis,"+
        "d.storage_temp, a.catalog_id,d.COMPONENTS_PER_ITEM,d.SAMPLE_ONLY "+
        "from catalog_add_request_new a, catalog_add_user_group b, personnel c, catalog_add_item d, facility f, fac_loc_app g "+
        "where "+
        "a.REQUEST_ID="+requestID+" and "+
        "b.REQUEST_ID="+requestID+" and "+
        "d.REQUEST_ID="+requestID+" and d.line_item = 1 and "+
        "a.REQUESTOR = c.personnel_id "+
        "and b.facility_id = f.facility_id and b.work_area = g.application and b.facility_id = g.facility_id) xx, "+
        "customer_msds_xref z "+
        "where "+
        "xx.CUSTOMER_MSDS_NUMBER = z.customer_msds_number(+) and "+
        "xx.catalog_id = z.customer_msds_db(+) "+
        "group by "+
        "xx.full_name,xx.phone,xx.FACILITY_NAME,xx.APPLICATION_DESC,xx.REQUEST_ID,xx.part_id,"+
        "xx.CUSTOMER_REQUEST_ID,xx.MANUFACTURER,xx.MATERIAL_DESC,xx.GRADE,xx.MFG_TRADE_NAME, xx.CUSTOMER_MSDS_NUMBER,xx.MFG_CATALOG_ID,"+
        "xx.PACKAGING,xx.SUGGESTED_VENDOR,xx.VENDOR_CONTACT_NAME,xx.VENDOR_CONTACT_PHONE,xx.VENDOR_CONTACT_FAX,xx.VENDOR_CONTACT_EMAIL,"+
        "xx.VENDOR_PART_NO,xx.CAT_PART_NO,xx.ITEM_ID,xx.shelf_life,xx.shelf_life_unit,xx.shelf_life_basis,xx.storage_temp,xx.catalog_id,xx.COMPONENTS_PER_ITEM,xx.SAMPLE_ONLY";

        int totalrecords = 0;
        int labelinfono = 0;
        int hazardrecords = 0;
        String selectedItemid = "";
        String selectedcatalogid = "";
        String selectedcatpartnum = "";

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

        try
        {
            dbrs = db.doQuery(detailquery);
            searchRs=dbrs.getResultSet();

            while(searchRs.next()) {
            totalrecords +=1;

            requestDataH = new Hashtable();
            //FULL_NAME
            requestDataH.put("FULL_NAME",searchRs.getString("FULL_NAME")==null?"":searchRs.getString("FULL_NAME"));
            //PHONE
            requestDataH.put("PHONE",searchRs.getString("PHONE")==null?"":searchRs.getString("PHONE"));
            //FACILITY_NAME
            requestDataH.put("FACILITY_NAME",searchRs.getString("FACILITY_NAME")==null?"":searchRs.getString("FACILITY_NAME"));
            //APPLICATION_DESC
            requestDataH.put("APPLICATION_DESC",searchRs.getString("APPLICATION_DESC")==null?"":searchRs.getString("APPLICATION_DESC"));
            //REQUEST_ID
            requestDataH.put("REQUEST_ID",searchRs.getString("REQUEST_ID")==null?"":searchRs.getString("REQUEST_ID"));
            //PART_ID
            requestDataH.put("PART_ID",searchRs.getString("PART_ID")==null?"":searchRs.getString("PART_ID"));
            //CUSTOMER_REQUEST_ID
            requestDataH.put("CUSTOMER_REQUEST_ID",searchRs.getString("CUSTOMER_REQUEST_ID")==null?"":searchRs.getString("CUSTOMER_REQUEST_ID"));
            //MANUFACTURER
            requestDataH.put("MANUFACTURER",searchRs.getString("MANUFACTURER")==null?"":searchRs.getString("MANUFACTURER"));
            //MATERIAL_DESC
            requestDataH.put("MATERIAL_DESC",searchRs.getString("MATERIAL_DESC")==null?"":searchRs.getString("MATERIAL_DESC"));
            //GRADE
            requestDataH.put("GRADE",searchRs.getString("GRADE")==null?"":searchRs.getString("GRADE"));
            //MFG_TRADE_NAME
            requestDataH.put("MFG_TRADE_NAME",searchRs.getString("MFG_TRADE_NAME")==null?"":searchRs.getString("MFG_TRADE_NAME"));
            //CUSTOMER_MSDS_NUMBER
            requestDataH.put("CUSTOMER_MSDS_NUMBER",searchRs.getString("CUSTOMER_MSDS_NUMBER")==null?"":searchRs.getString("CUSTOMER_MSDS_NUMBER"));
            //MFG_CATALOG_ID
            requestDataH.put("MFG_CATALOG_ID",searchRs.getString("MFG_CATALOG_ID")==null?"":searchRs.getString("MFG_CATALOG_ID"));
            //PACKAGING
            requestDataH.put("PACKAGING",searchRs.getString("PACKAGING")==null?"":searchRs.getString("PACKAGING"));
            //SUGGESTED_VENDOR
            requestDataH.put("SUGGESTED_VENDOR",searchRs.getString("SUGGESTED_VENDOR")==null?"":searchRs.getString("SUGGESTED_VENDOR"));
            //VENDOR_CONTACT_NAME
            requestDataH.put("VENDOR_CONTACT_NAME",searchRs.getString("VENDOR_CONTACT_NAME")==null?"":searchRs.getString("VENDOR_CONTACT_NAME"));
            //VENDOR_CONTACT_PHONE
            requestDataH.put("VENDOR_CONTACT_PHONE",searchRs.getString("VENDOR_CONTACT_PHONE")==null?"":searchRs.getString("VENDOR_CONTACT_PHONE"));
            //VENDOR_CONTACT_FAX
            requestDataH.put("VENDOR_CONTACT_FAX",searchRs.getString("VENDOR_CONTACT_FAX")==null?"":searchRs.getString("VENDOR_CONTACT_FAX"));
            //VENDOR_CONTACT_EMAIL
            requestDataH.put("VENDOR_CONTACT_EMAIL",searchRs.getString("VENDOR_CONTACT_EMAIL")==null?"":searchRs.getString("VENDOR_CONTACT_EMAIL"));
            //VENDOR_PART_NO
            requestDataH.put("VENDOR_PART_NO",searchRs.getString("VENDOR_PART_NO")==null?"":searchRs.getString("VENDOR_PART_NO"));
            //CAT_PART_NO
            selectedcatpartnum = searchRs.getString("CAT_PART_NO")==null?"":searchRs.getString("CAT_PART_NO");
            requestDataH.put("CAT_PART_NO",selectedcatpartnum);
            //ITEM_ID
            selectedItemid = searchRs.getString("ITEM_ID")==null?"":searchRs.getString("ITEM_ID");
            requestDataH.put("ITEM_ID",selectedItemid);
            //SHELF_LIFE
            requestDataH.put("SHELF_LIFE",searchRs.getString("SHELF_LIFE")==null?"":searchRs.getString("SHELF_LIFE"));
            //SHELF_LIFE_UNIT
            requestDataH.put("SHELF_LIFE_UNIT",searchRs.getString("SHELF_LIFE_UNIT")==null?"":searchRs.getString("SHELF_LIFE_UNIT"));
            //SHELF_LIFE_BASIS
            requestDataH.put("SHELF_LIFE_BASIS",searchRs.getString("SHELF_LIFE_BASIS")==null?"":searchRs.getString("SHELF_LIFE_BASIS"));
            //STORAGE_TEMP
            requestDataH.put("STORAGE_TEMP",searchRs.getString("STORAGE_TEMP")==null?"":searchRs.getString("STORAGE_TEMP"));
            //CATALOG_ID
            selectedcatalogid = searchRs.getString("CATALOG_ID")==null?"":searchRs.getString("CATALOG_ID");
            requestDataH.put("CATALOG_ID",selectedcatalogid);
            //COMPONENTS_PER_ITEM
            requestDataH.put("COMPONENTS_PER_ITEM",searchRs.getString("COMPONENTS_PER_ITEM")==null?"":searchRs.getString("COMPONENTS_PER_ITEM"));
            //SAMPLE_ONLY
            requestDataH.put("SAMPLE_ONLY",searchRs.getString("SAMPLE_ONLY")==null?"":searchRs.getString("SAMPLE_ONLY"));
            //MATERIAL_ID
            requestDataH.put("MATERIAL_ID",searchRs.getString("MATERIAL_ID")==null?"":searchRs.getString("MATERIAL_ID"));

            requestData.addElement(requestDataH);
            }

            //if (selectedcatpartnum.trim().length() > 0 && selectedItemid.trim().length() > 0) nawaz 06-03-03
            if (selectedItemid.trim().length() > 0)
            {
              dbrs = db.doQuery("select * from inventory_group_item_label where INVENTORY_GROUP='"+selectedcatalogid+"' and ITEM_ID="+selectedItemid+" ");
              searchRs=dbrs.getResultSet();

              while(searchRs.next())
              {
                labelinfono ++;
                selectedItemid=searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ).trim();
                selectedcatpartnum=searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ).trim();

                health = searchRs.getString("HEALTH")==null?"":searchRs.getString("HEALTH").trim();
                flamability = searchRs.getString("FLAMABILITY")==null?"":searchRs.getString("FLAMABILITY").trim();
                reactivity = searchRs.getString("REACTIVITY")==null?"":searchRs.getString("REACTIVITY").trim();
                chemical_storage = searchRs.getString("CHEMICAL_STORAGE")==null?"":searchRs.getString("CHEMICAL_STORAGE").trim();
                signal_word = searchRs.getString("SIGNAL_WORD")==null?"":searchRs.getString("SIGNAL_WORD").trim();
                personal_protective_equipment = searchRs.getString("PERSONAL_PROTECTIVE_EQUIPMENT")==null?"":searchRs.getString("PERSONAL_PROTECTIVE_EQUIPMENT").trim();
                disposal_code = searchRs.getString("DISPOSAL_CODE")==null?"":searchRs.getString("DISPOSAL_CODE").trim();
                disposal_container_code = searchRs.getString("DISPOSAL_CONTAINER_CODE")==null?"":searchRs.getString("DISPOSAL_CONTAINER_CODE").trim();

                hazard_code1 = searchRs.getString("HAZARD_CODE1")==null?"":searchRs.getString("HAZARD_CODE1").trim();
                hazard_code2 = searchRs.getString("HAZARD_CODE2")==null?"":searchRs.getString("HAZARD_CODE2").trim();
              }

              selechazcodes.addElement(hazard_code1);
              selechazcodes.addElement(hazard_code2);

              if (labelinfono == 0 )
              {
                dbrs=db.doQuery( "select fx_default_health(" + selectedItemid + ") HEALTH from dual" );
                searchRs=dbrs.getResultSet();
                while ( searchRs.next() )
                {
                  health=searchRs.getString( "HEALTH" ) == null ? "" : searchRs.getString( "HEALTH" ).trim();
                }

                dbrs=db.doQuery( "select fx_default_flamability(" + selectedItemid + ") FLAMABILITY from dual" );
                searchRs=dbrs.getResultSet();
                while ( searchRs.next() )
                {
                  flamability=searchRs.getString( "FLAMABILITY" ) == null ? "" : searchRs.getString( "FLAMABILITY" ).trim();
                }

                dbrs=db.doQuery( "select fx_default_reactivity(" + selectedItemid + ") REACTIVITY from dual" );
                searchRs=dbrs.getResultSet();
                while ( searchRs.next() )
                {
                  reactivity=searchRs.getString( "REACTIVITY" ) == null ? "" : searchRs.getString( "REACTIVITY" ).trim();
                }

                if ( health.length() > 0 && flamability.length() > 0 && reactivity.length() > 0 )
                {
                  dbrs=db.doQuery( "select distinct HAZARD_CODE,HAZARD_CODE_DESC from hazards where (property,property_value) in ( ('HEALTH'," + health + "),('REACTIVITY'," + reactivity + "),('FLAMABILITY'," + flamability + ") ) order by HAZARD_CODE desc" );
                  searchRs=dbrs.getResultSet();

                  while ( searchRs.next() )
                  {
                    hazardrecords++;
                    hazardData=new Hashtable();
                    String hazcode=searchRs.getString( "HAZARD_CODE" ) == null ? "" : searchRs.getString( "HAZARD_CODE" );
                    String hazcodedesc=searchRs.getString( "HAZARD_CODE_DESC" ) == null ? "" : searchRs.getString( "HAZARD_CODE_DESC" );
                     hazardData.put( ""+hazcode+":"+hazcodedesc+"",hazcode );
                    hazardVector.addElement( hazardData );
                  }
                }
              }
              else
              {
              String hazadescqur="SELECT fpl.HAZARD_CODE1,fpl.HAZARD_CODE2 ,(select distinct hz1.HAZARD_CODE_DESC  from hazards hz1 where HAZARD_CODE=HAZARD_CODE1 and ";
              hazadescqur+=" decode(hz1.property ,'HEALTH',fpl.health,'REACTIVITY',fpl.REACTIVITY,'FLAMABILITY',fpl.FLAMABILITY)=hz1.property_value ";
              hazadescqur+=" ) HAZARD_CODE_DESC1, ";
              hazadescqur+=" (select distinct hz2.HAZARD_CODE_DESC  from hazards hz2 where HAZARD_CODE=HAZARD_CODE2 and ";
              hazadescqur+=" decode(hz2.property ,'HEALTH',fpl.health,'REACTIVITY',fpl.REACTIVITY,'FLAMABILITY',fpl.FLAMABILITY)=hz2.property_value ";
              hazadescqur+="  ) HAZARD_CODE_DESC2  from inventory_group_item_label fpl where fpl.item_id = " + selectedItemid + " ";

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

            out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n");
            if (totalrecords > 0)
            {
              for (int loop  = 0 ; loop  < requestData.size()  ; loop++)
              {
                Hashtable requestData1 = new Hashtable();
                requestData1 = (Hashtable) requestData.elementAt(loop);

                if (loop == 0)
                {
                  //Requestor
                  out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Requestor: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                  out.println(requestData1.get("FULL_NAME").toString());out.println("</TD></TR>\n");
                  //Phone
                  out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Phone: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                  out.println(requestData1.get("PHONE").toString());out.println("</TD></TR>\n");
                  //Facility
                  out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Facility: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                  out.println(requestData1.get("FACILITY_NAME").toString());out.println("</TD></TR>\n");
                  //Work Area
                  out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Work Area: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                  out.println(requestData1.get("APPLICATION_DESC").toString());out.println("</TD></TR>\n");
                  //Radian Request ID
                  out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Haas Request ID: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                  out.println(requestData1.get("REQUEST_ID").toString());out.println("</TD></TR>\n");

                  if (!client.equalsIgnoreCase("Southwest Airlines"))
                  {
                  //CUSTOMER_REQUEST_ID
                  out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Seagate Request ID: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                  out.println(requestData1.get("CUSTOMER_REQUEST_ID").toString());out.println("</TD></TR>\n");
                  }

                  out.println("<TR><TD BGCOLOR=\"#000066\" COLSPAN=\"2\">&nbsp;</TD></TR>\n");
                }

                //PART
                //System.out.println(requestData1.get("PART_ID").toString());
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Part: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                out.println(requestData1.get("PART_ID").toString());out.println("</TD></TR>\n");
                //MANUFACTURER
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>ManuFacturer: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                out.println(requestData1.get("MANUFACTURER").toString());out.println("</TD></TR>\n");
                //MATERIAL_DESC
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Description: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                out.println(requestData1.get("MATERIAL_DESC").toString());out.println("</TD></TR>\n");
                //MFG_TRADE_NAME
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Trade Name: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                out.println(requestData1.get("MFG_TRADE_NAME").toString());out.println("</TD></TR>\n");

                //Material ID
                material_id = requestData1.get("MATERIAL_ID").toString();

                if (material_id.length() > 1)
                {
                //CUSTOMER_MSDS_NUMBER
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>MSDS #: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                if (client.equalsIgnoreCase("Southwest Airlines"))
                {
                out.println("<A HREF=\"/tcmIS/swa/ViewMsds?showspec=N&id=");
                }
                else
                {
                out.println("<A HREF=\"/tcmIS/seagate/ViewMsds?showspec=N&id=");
                }
                out.println(material_id);out.println("\" TARGET=\"msds\">");out.println(requestData1.get("CUSTOMER_MSDS_NUMBER").toString());
                out.println("</A></TD></TR>\n");
                }
                else
                {
                //CUSTOMER_MSDS_NUMBER
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>MSDS #: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                out.println(requestData1.get("CUSTOMER_MSDS_NUMBER").toString());out.println("</TD></TR>\n");
                }

                //MFG_CATALOG_ID
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Mfg Part #: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                out.println(requestData1.get("MFG_CATALOG_ID").toString());out.println("</TD></TR>\n");
                //Grade
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Grade: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                out.println(requestData1.get("GRADE").toString());out.println("</TD></TR>\n");

                //PACKAGING
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Packaging: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");

                String Packaging_display = requestData1.get("PACKAGING").toString();
                int comp_per_item = BothHelpObjs.makeZeroFromNull(requestData1.get("COMPONENTS_PER_ITEM").toString());
                String sample_only = requestData1.get("SAMPLE_ONLY").toString();

                if (comp_per_item > 1)
                {
                Packaging_display = comp_per_item + " x " +Packaging_display;
                }
                if ("y".equalsIgnoreCase(sample_only))
                {
                Packaging_display += " (Sample Size) ";
                }

                out.println(Packaging_display);out.println("</TD></TR>\n");

                //shelf life
                String mySL = "";
                String tmpSL = requestData1.get("SHELF_LIFE_UNIT").toString();
                //System.out.println("*"+tmpSL+"*");
                if (tmpSL.startsWith("Indefinite")) {
                  mySL = tmpSL;
                }else if (tmpSL.startsWith("Select")) {
                  mySL = "";
                }else {
                  mySL = requestData1.get("SHELF_LIFE").toString() + " "+tmpSL;
                  mySL += " from date of "+requestData1.get("SHELF_LIFE_BASIS").toString();
                }

                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Shelf Life: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                out.println(mySL);out.println("</TD></TR>\n");
                //storage temperature
                out.println("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Storage Temp: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                out.println(requestData1.get("STORAGE_TEMP").toString());out.println("</TD></TR>\n");

                out.println("<TR><TD BGCOLOR=\"#000066\" COLSPAN=\"2\">&nbsp;</TD></TR>\n");

                if (loop == 0)
                {
                  //SUGGESTED_VENDOR
                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Supplier Name: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                  Msgd1.append(requestData1.get("SUGGESTED_VENDOR").toString());Msgd1.append("</TD></TR>\n");
                  //VENDOR_CONTACT_NAME
                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Contact Name: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                  Msgd1.append(requestData1.get("VENDOR_CONTACT_NAME").toString());Msgd1.append("</TD></TR>\n");
                  //Suuplier Contact Phone
                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Phone: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                  Msgd1.append(requestData1.get("VENDOR_CONTACT_PHONE").toString());Msgd1.append("</TD></TR>\n");
                  //Suuplier Contact Fax
                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Fax: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                  Msgd1.append(requestData1.get("VENDOR_CONTACT_FAX").toString());Msgd1.append("</TD></TR>\n");
                  //Suuplier Contact Email
                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>Email: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                  Msgd1.append(requestData1.get("VENDOR_CONTACT_EMAIL").toString());Msgd1.append("</TD></TR>\n");
                  //VENDOR_PART_NO
                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Supplier Part #: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                  Msgd1.append(""+requestData1.get("VENDOR_PART_NO").toString()+"");Msgd1.append("</TD></TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\"><B>CSM Part #: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inwhitel\">");
                  Msgd1.append("<INPUT type=\"text\" CLASS=\"INVISIBLEHEADWHITE\" name=\"csm_part_no\" value=\""+requestData1.get("CAT_PART_NO").toString()+"\" SIZE=\"25\" READONLY>");Msgd1.append("</TD></TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\"><B>Haas Item #: </B></TD><TD WIDTH=\"85%\" CLASS=\"Inbluel\">");
                  Msgd1.append("<INPUT type=\"text\" CLASS=\"INVISIBLEHEADBLUE\" name=\"haas_item_no\" value=\""+selectedItemid+"\" SIZE=\"25\" READONLY>");Msgd1.append("</TD></TR>\n");

                  Msgd1.append("<TR><TD BGCOLOR=\"#000066\" COLSPAN=\"2\" CLASS=\"heading\">Label Information</TD></TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Health: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\">\n");


                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"health\" size=\"1\" onChange=\"removeAllHazards()\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDescDropDown((Vector)labelSession1.getAttribute("HEALTH"),health));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Flamability: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"flamability\" size=\"1\" onChange=\"removeAllHazards()\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDescDropDown((Vector)labelSession1.getAttribute("FLAMMABILITY"),flamability));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Reactivity: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"reactivity\" size=\"1\" onChange=\"removeAllHazards()\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDescDropDown((Vector)labelSession1.getAttribute("REACTIVITY"),reactivity));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Chemical Storage: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"chemicalstorage\" size=\"1\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDescDropDown((Vector)labelSession1.getAttribute("CHEMICALSTORAGE"),chemical_storage));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Signal Word: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"signalword\" size=\"1\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDropDown((Vector)labelSession1.getAttribute("SIGNALWORD"),signal_word));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Personal Protective Equip: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"perprotectiveequip\" size=\"1\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDescDropDown((Vector)labelSession1.getAttribute("PERSONNELPROTECTION"),personal_protective_equipment));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Hazard: </B><BR><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='hazardValues' OnClick=\"getHazardvalues()\" value=\"Get Hazard Values\"></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"hazard\" size=\"4\" multiple onChange=\"checkForTwo(this)\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  if (hazardrecords > 0)
                  {
                    Msgd1.append(radian.web.HTMLHelpObj.getmultipleDropDown(hazardVector,selechazcodes));
                  }
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inbluel\" VALIGN=\"MIDDLE\"><B>Disposal Code: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\" CLASS=\"Inbluel\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"disposablecode\" size=\"1\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDescDropDown((Vector)labelSession1.getAttribute("DISPOSABLECODE"),disposal_code));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");

                  Msgd1.append("<TR>\n<TD WIDTH=\"15%\" CLASS=\"Inwhitel\" VALIGN=\"MIDDLE\"><B>Disposal Container Code: </B></TD>\n");
                  Msgd1.append("<TD WIDTH=\"85%\">\n");
                  Msgd1.append("<SELECT CLASS=\"HEADER\" NAME=\"disposablecontainercode\" size=\"1\">\n");
                  Msgd1.append("<OPTION VALUE=\"\">Please Select</OPTION>\n");
                  Msgd1.append(radian.web.HTMLHelpObj.getDescDropDown((Vector)labelSession1.getAttribute("DISPOSECONTAINERCODE"),disposal_container_code));
                  Msgd1.append("</SELECT>\n");
                  Msgd1.append("</TD>\n</TR>\n");
              }//end of static info
            }  //end of for
          }
        }
         catch (Exception e1)
        {
            e1.printStackTrace();
            out.println(radian.web.HTMLHelpObj.printHTMLHeader(""+client+" Label Information","",intcmIsApplication));
            out.println("</HEAD>\n");
            out.println("<BODY>\n");
            out.println(radian.web.HTMLHelpObj.printMessageinTable( "An Error Occured While Querying the Databse" ));
            out.println("</TABLE>\n");
            out.println("</BODY>\n</HTML>\n");
            //out.println(Msgd);
            return;
        }
        finally
        {
            if (dbrs != null)  { dbrs.close(); }
            if (totalrecords==0)
            {out.println("<TR><TD>No Records Found</TD></TR>\n");}
        }


      if (!(totalrecords==0))
      {
      out.println(Msgd1);
      out.println("</TABLE>\n");

      out.println("<INPUT TYPE=\"hidden\" NAME=\"request_id\" VALUE=\""+requestID+"\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"catalogId\" VALUE=\""+selectedcatalogid+"\">\n");
      out.println("<HR><INPUT type=\"submit\" value=\"Submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"UpdateCat\">\n");
      out.println("</FORM>\n");
      }
      else
      {
      out.println("</FORM>\n");
      }

      if (client.equalsIgnoreCase("Southwest Airlines"))
      {
      out.println("<A HREF=\""+Cat_Servlet+"Session=0\">Show All Requests Needing SI Number</A>");
      }
      else
      {
      out.println("<A HREF=\""+Cat_Servlet+"Session=0\">Show All Requests Needing CSM Label Information</A>");
      }
      out.println("</BODY>\n</HTML>\n");
      //out.println(Msgd);
    }

    public void buildSeperateScreen(PrintWriter out, String SusMessage)
    {
      //StringBuffer MsgH = new StringBuffer();
      if (SusMessage.length() >1){ShowThis = SusMessage;}

      out.println(radian.web.HTMLHelpObj.printHTMLHeader(""+client+" Label Information","",intcmIsApplication));
      out.println("</HEAD>\n");

      if (SusMessage.length() >1)
      {
      out.println("<BODY onload =\"showMsg()\">\n");
      }
      else
      {
      out.println("<BODY>\n");
      }
      out.println("<TABLE BORDER=0 WIDTH=100% >\n");
      out.println("<TR><TD WIDTH=\"100%\" ALIGN=\"CENTER\"><B>"+client+" Label Information<B></TD></TR>\n");
      out.println("<TR VALIGN=\"TOP\">\n");
      out.println("<TD WIDTH=\"200\">\n");
      out.println("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
      out.println("</TD>\n");
      out.println("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
      out.println("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
      out.println("</TD>\n");
      out.println("</TR>\n");
      out.println("<TR><TD ALIGN=\"CENTER\"><B>"+SusMessage+"</B></TD></TR>\n");
      out.println("</TABLE>\n");

      out.println("\n<BR><BR><BR>");
      if (client.equalsIgnoreCase("Southwest Airlines"))
      {
      out.println("<CENTER><A HREF=\""+Cat_Servlet+"Session=0\">Show All Requests Needing SI Number</A></CENTER>");
      }
      else
      {
      out.println("<CENTER><A HREF=\""+Cat_Servlet+"Session=0\">Show All Requests Needing CSM Label Information</A></CENTER>");
      }

      out.println("\n</BODY>\n</HTML>\n");

     //out.println(MsgH);
    }

    public void buildRequests(PrintWriter out, String Message)
    {
        //StringBuffer Msg = new StringBuffer();
		DBResultSet dbrs = null;
		ResultSet searchRs = null;
        out.println(radian.web.HTMLHelpObj.printHTMLHeader("Requests Needing "+client+" Label Information","",intcmIsApplication));
        out.println("</HEAD>\n");
        out.println("<BODY>\n");

        out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Requests Needing "+client+" Label Information<B>\n"));
        out.println("<FONT ALIGN=\"CENTER\"><B>"+Message+"</B></FONT><BR>\n");

        out.println("<TABLE BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"95%\" >\n");
        out.println("<TR>\n");
        out.println("<TH>Request Id</B></TD>\n");
        if (!client.equalsIgnoreCase("Southwest Airlines"))
        {
        out.println("<TH>Seagate Request Id</B></TD>\n");
        }
        out.println("<TH>Requested</TH>\n");
        out.println("<TH>Requestor</TH>\n");
        out.println("<TH>Facility</TH>\n");
        out.println("<TH>Workarea</TH>\n");
        out.println("</TR>\n");

        int totalrecords = 0;
        int count = 0;

        //This is the query to get all request ids which need catalog Label Information
        String query = "select a.REQUEST_ID,a.CUSTOMER_REQUEST_ID,to_char(a.REQUEST_DATE,'mm/dd/yyyy') REQUEST_DATE,c.last_name || ', ' || c.first_name    full_name, "+
        "f.FACILITY_NAME, g.APPLICATION_DESC from personnel c,facility f,fac_loc_app g,catalog_add_request_new a,catalog_add_user_group b,vv_chemical_approval_role car "+
        "where a.REQUEST_ID=b.REQUEST_ID and"+
		  " a.catalog_id = car.catalog_id and a.eng_eval_facility_id = car.facility_id and car.active = 'Y' and car.approval_role = 'Label Info' and"+
		  " a.request_id not in (select distinct request_id from catalog_add_approval where  APPROVAL_ROLE = 'Label Info') and a.REQUESTOR = c.personnel_id and a.REQUEST_STATUS =8 and a.APPROVAL_GROUP_SEQ = 1 and b.facility_id = f.facility_id and b.work_area = g.application and b.facility_id = g.facility_id order by request_id";

        try
        {
            dbrs = db.doQuery(query);
            searchRs=dbrs.getResultSet();

            while(searchRs.next())
            {
              totalrecords += 1;
              count += 1;

              if (count%10==0)
              {
                out.println("<TR>\n");
                out.println("<TH>Request Id</TH>\n");
                if (!client.equalsIgnoreCase("Southwest Airlines"))
                {
                out.println("<TH>Seagate Request Id</TH>\n");
                }
                out.println("<TH>Requested</TH>\n");
                out.println("<TH>Requestor</TH>\n");
                out.println("<TH>Facility</TH>\n");
                out.println("<TH>Workarea</TH>\n");
                out.println("</TR>\n");
              }

              String Color = " ";
              if (count%2==0)
              {
                  Color ="CLASS=\"Inblue"  ;
              }
              else
              {
                 Color ="CLASS=\"Inwhite"  ;
              }

              out.println("<TR>\n");
              out.println("<TD "+Color+"l\">");
              out.println("<A HREF=\""+Cat_Servlet+"Session=1&request_id="+BothHelpObjs.makeSpaceFromNull(searchRs.getString("REQUEST_ID"))+"\">");
              out.println(""+BothHelpObjs.makeSpaceFromNull(searchRs.getString("REQUEST_ID"))+"</A>");
              out.println("</TD>\n");
              if (!client.equalsIgnoreCase("Southwest Airlines"))
              {
              out.println("<TD "+Color+"l\">");out.println(BothHelpObjs.makeSpaceFromNull(searchRs.getString("CUSTOMER_REQUEST_ID")));out.println("</TD>\n");
              }
              out.println("<TD "+Color+"l\">");out.println(BothHelpObjs.makeSpaceFromNull(searchRs.getString("REQUEST_DATE")));out.println("</TD>\n");
              out.println("<TD "+Color+"l\">");out.println(BothHelpObjs.makeSpaceFromNull(searchRs.getString("FULL_NAME")));out.println("</TD>\n");
              out.println("<TD "+Color+"l\">");out.println(BothHelpObjs.makeSpaceFromNull(searchRs.getString("FACILITY_NAME")));out.println("</TD>\n");
              out.println("<TD "+Color+"l\">");out.println(BothHelpObjs.makeSpaceFromNull(searchRs.getString("APPLICATION_DESC")));out.println("</TD>\n");

              out.println("</TR>\n");
            }
      }
      catch (Exception e)
      {
          e.printStackTrace();
          out.println(radian.web.HTMLHelpObj.printHTMLHeader(""+client+" Label Information","",intcmIsApplication));
          out.println("</HEAD>\n");
          out.println("<BODY>\n");
          out.println(radian.web.HTMLHelpObj.printMessageinTable( "An Error Occured While Querying the Databse" ));
          out.println("</TABLE>\n");
          out.println("</BODY>\n</HTML>\n");
          //out.println(Msg);
          return;
      }
      finally
      {
          if (dbrs != null)  { dbrs.close(); }
          if (totalrecords==0)
          {out.println("<TR><TD>No Records Found</TD></TR>\n");}
      }

      out.println("</TABLE>\n");
      out.println("</BODY>\n</HTML>\n");
      //out.println(Msg);
    }
}

