package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.jamonapi.Monitor;
//import com.jamonapi.MonitorFactory;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.PrintLargeLabels;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2005
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class InternalPrintLargeLabel  extends HttpServlet implements SingleThreadModel
{

    public void init(ServletConfig config) throws ServletException
    {
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

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
      PrintWriter out = response.getWriter();
      response.setContentType("text/html");
      HttpSession session=request.getSession();

      RayTcmISDBModel db = null;
      String branch_plant;
      PrintLargeLabels obj = null;
      //BarCodePage obj1 = null;
      String User_Action = "";
      String User_Session = "";
      String generate_labels = "";
      String po_line = "";
      String po = "";
      String purchorder= "";


      try
      {
        db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
        if (db == null)
        {
            PrintWriter out2 = new PrintWriter (response.getOutputStream());
            out2.println("Starting DB");
            out2.println("DB is null");
            out2.close();
            return;
        }

        Hashtable loginresult = new Hashtable();
        loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
        String auth = (String)loginresult.get("AUTH");
        String errormsg = (String)loginresult.get("ERROSMSG");

        if (auth == null) {auth = "challenge";}
        if (errormsg == null) {errormsg = "";}

        if (! ( auth.equalsIgnoreCase( "authenticated" ) ) )
        {
          out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Large Labels" ) );
          out.flush();
        }
        else
        {
          obj = new PrintLargeLabels((ServerResourceBundle) new InternalServerResourceBundle(),db);


          String unreceive = "";
          String receipt_id = "";
          String HubNo = "";
          String PaperSize = "";

          HubNo = request.getParameter("HubNo");
          if (HubNo == null)
            HubNo = "ERROR";
          User_Session= request.getParameter("session");
          if (User_Session == null)
            User_Session = "New";
          User_Action= request.getParameter("UserAction");
          if (User_Action == null)
            User_Action = "New";
          generate_labels = request.getParameter("generate_labels");
          if (generate_labels == null)
            generate_labels ="No";
          po_line = request.getParameter("poline");
          if (po_line == null)
            po_line = "";
          PaperSize = request.getParameter("paperSize");
          if (PaperSize == null)
            PaperSize = "31";
          purchorder = request.getParameter("purchorder");
          if (purchorder == null)
            purchorder = "";
          po = request.getParameter("po");
          if (po == null)
            po = "";
          unreceive = request.getParameter("unreceive");
           if (unreceive == null)
            unreceive = "";
          receipt_id = request.getParameter("receipt_id");
           if (receipt_id == null)
            receipt_id = "";

          //System.out.println("Checkbok   "+generate_labels+" Size  "+PaperSize+"  User Action  :"+ User_Action+" User session  :"+ User_Session +" Po Line: "+po_line);
          if ("yes".equalsIgnoreCase(generate_labels) && "GenerateLabels".equalsIgnoreCase(User_Action) )
          {
          /*sequencenumbers  = (Vector)session.getAttribute("LABEL_SEQUENCE_NUMBERS");
          qunatitynumbers = (Vector)session.getAttribute("LABEL_QUANTITYS");

          obj1 = new BarCodePage((ServerResourceBundle) new InternalServerResourceBundle(),db,qunatitynumbers,PaperSize);

          StringBuffer MsgSB = new StringBuffer();
          String url = "";

          String HubNoforlabel = request.getParameter("HubNoforlabel");
          if (HubNoforlabel == null)
            HubNoforlabel = "";
          HubNoforlabel.trim();
          obj1.sethubNum(HubNoforlabel);

		  String printKitLabels=request.getParameter( "printKitLabels" );
		  if ( printKitLabels == null )
			printKitLabels="";

		  String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		  url = obj1.generateContainerLabel(sequencenumbers,personnelid,!"Yes".equalsIgnoreCase(printKitLabels));

          out.println(radian.web.HTMLHelpObj.labelredirection(url));
          out.close();*/
          }
          else
          {
            obj.doResult( request,response,session );
          }
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        return;
      }
      finally
      {
        db.close();
        obj=null;
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

    public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
    {
        doPost(request,response);
    }
    //
}

