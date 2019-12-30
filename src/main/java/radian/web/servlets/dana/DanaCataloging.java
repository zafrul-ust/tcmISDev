package radian.web.servlets.dana;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.client.dana.dbObjs.DanaTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import radian.web.servlets.share.SCataloging;
import radian.web.servlets.share.CatalogAddApprovalHelper;

public class DanaCataloging extends HttpServlet
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DanaTcmISDBModel db = null;
        String User_ID = "";
        HttpSession session=request.getSession();
				PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
				boolean intcmIsApplication = false;
				if (personnelBean !=null)
				{
					 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
					 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
						intcmIsApplication = true;
					 }
				}

        PrintWriter out=response.getWriter();

        try {
          db = new DanaTcmISDBModel("Dana");
          if (db == null) {
            PrintWriter out2 = new PrintWriter (response.getOutputStream());
            out2.println("Starting DB");
            out2.println("DB is null");
            out2.close();
            return;
          }

          ServerResourceBundle bundlefec=new DanaServerResourceBundle();
          HeaderFooter hf=new HeaderFooter( bundlefec,db );

          Hashtable loginresult = new Hashtable();
          loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
          String auth = (String)loginresult.get("CLIENT_AUTH");
          String errormsg = (String)loginresult.get("ERROSMSG");

          if (auth == null) {auth = "challenge";}
          if (errormsg == null) {errormsg = "";}

           if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) ) {
             session.setAttribute( "clientloginState","challenge" );
             out.println( hf.printclientLoginScreen( errormsg,"Customer Cataloging","tcmistcmis32.gif",intcmIsApplication ) );
             out.flush();
           } else {
             SCataloging obj = new SCataloging((ServerResourceBundle) new DanaServerResourceBundle(),db);
             String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
             CatalogAddApprovalHelper caah = new CatalogAddApprovalHelper(db);
             Vector approvalRoleV = caah.getApprovalRoles(personnelid,"cataloging");
             if (approvalRoleV.size() > 0 ) {
               session.setAttribute( "APPROVAL_ROLES",approvalRoleV);
               obj.doResponse( request,response );
             }else {
               errormsg = "You Don't Have Access to this Web Page. Please contact your CSR for Further Information.";
               out.println( hf.printclientLoginScreen( errormsg,"Customer Cataloging","tcmistcmis32.gif",intcmIsApplication ) );
               out.flush();
             }
           }
        }catch ( Exception e ) {
          e.printStackTrace();
        }finally {
          db.close();
        }
    } //end of method

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        doPost(request,response);
    }
} //end of class
