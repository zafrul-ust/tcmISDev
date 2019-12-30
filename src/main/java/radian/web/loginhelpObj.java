/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 * 12-15-03 - Added an extra parameter in the login process so that people login in the client page first can login to hub pages without problem
 * 02-08-05 - Importing the new personnelBean
 */
package radian.web;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.process.VvDataProcess;
import radian.tcmis.server7.share.dbObjs.Password;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import com.tcmis.common.admin.process.BulletinProcess;
import com.tcmis.common.admin.beans.ConnectionPoolMessageBean;
import com.tcmis.common.exceptions.*;
import radian.tcmis.server7.share.helpers.HelpObjs;

public abstract class loginhelpObj
{
  public static Hashtable logintopage( HttpSession inputsession,HttpServletRequest request,TcmISDBModel dbObj,PrintWriter thisIsnotUsed ) throws Exception {
    String auth= inputsession.getAttribute( "loginState" ) == null ? "" : inputsession.getAttribute( "loginState" ).toString();
    String clinetauth= inputsession.getAttribute( "clientloginState" ) == null ? "" : inputsession.getAttribute( "clientloginState" ).toString();
    String dbclientname = dbObj.getClient();

    String loginId1="";
    String passwd="";
    String errormsg="";
    Hashtable returnResu = new Hashtable();
    boolean showlogin = false;

    if ( "challenge".equals( auth ) || "challenge".equals( clinetauth ) ) {
      loginId1= ( String ) request.getParameter( "loginId" );
      passwd= ( String ) request.getParameter( "passwd" );
      if ( loginId1 == null )
      {
        loginId1="";
      }
      if ( passwd == null )
      {
        passwd="";
      }
      showlogin = true;
    }

    if ( showlogin && ( loginId1.length() > 0 ) && ( passwd.length() > 0 ) )
    {
      String company=request.getPathInfo();
      if ( company != null )
      {
       company=company.substring( 1 ).toUpperCase();
       if ("Radian".equalsIgnoreCase(company))
       {
        company="Radian";
       }
      }
      else if ( company == null && ( "Tcm_Ops".equalsIgnoreCase( dbclientname ) ) )
      {
	company="Radian";
      }
      else
      {
	company="";
      }

      inputsession.setAttribute( "COMPANYID",company );
      String CompanyID= inputsession.getAttribute( "COMPANYID" ) == null ? "" : inputsession.getAttribute( "COMPANYID" ).toString();

      try
      {
        Password pw = null;
        if ("Tcm_Ops".equalsIgnoreCase(dbclientname))
        {
          pw=new Password( dbObj,loginId1,passwd,CompanyID );
        }
        else
        {
          pw=new Password( dbObj,loginId1,passwd );
        }

        String personnelId=pw.getUserId();
        if ( personnelId == null )
        {
          errormsg="Server Authentication Error.<BR>\n";
          errormsg+="This Logon Id is not registered, Please re-enter your Logon Id, Password and try again.\n";

          returnResu.put("AUTH",auth);
          returnResu.put("CLIENT_AUTH",clinetauth);
          returnResu.put("ERROSMSG",errormsg);
          return returnResu;
        }

        inputsession.setAttribute( "PERSONNELID",pw.getUserId() );
        inputsession.setAttribute( "CLIENTPERSONNELID",pw.getUserId() );

        if ( pw.isAuthenticatedWeb() )
        {
          inputsession.setAttribute( "loginId",loginId1 );
          inputsession.setAttribute( "passwd",passwd );
          inputsession.setAttribute("clientloginState", "authenticated");
          clinetauth = "authenticated";

          inputsession.setAttribute("FULLNAME",radian.web.HTMLHelpObj.getfullname(dbObj,pw.getUserId(),CompanyID));
          inputsession.setAttribute("ALLOWEDGROUPS", radian.web.HTMLHelpObj.createallowedusrgrps(dbObj,pw.getUserId(),CompanyID));
          inputsession.setAttribute("HUB_PREF_LIST", radian.web.HTMLHelpObj.createHubList(dbObj,pw.getUserId(),CompanyID));
          inputsession.setAttribute( "VV_CURRENCY",radian.web.vvHelpObj.getcurrency(dbObj) );

          if ( "Tcm_Ops".equalsIgnoreCase( dbclientname ) )
          {
            radian.web.HTMLHelpObj.LoginSetup( inputsession,dbObj,pw.getUserId(),CompanyID,loginId1 );
          }
          else
          {
            inputsession.setAttribute("loginState", "authenticated");
          }

          auth="authenticated";

		  //copy input into bean
		  PersonnelBean personnelBean=new PersonnelBean();
		  personnelBean.setClient(dbclientname.toUpperCase());
		  personnelBean.setCompanyId(CompanyID);
		  personnelBean.setLogonId(loginId1);
		  personnelBean.setPassword(passwd);

		  //login
		  if ( personnelBean != null && personnelBean.getClient() != null )
		  {
			LoginProcess loginProcess=new LoginProcess( personnelBean.getClient() );
			personnelBean=loginProcess.loginWeb( personnelBean );

			//Storing the Hub_Pref and inventory groups in the session
			//inputsession.setAttribute("hubPrefViewBeanCollection",loginProcess.getHubPrefView(personnelBean));
			//inputsession.setAttribute("personnelUserGroupViewBeanCollection",loginProcess.getDistinctPersonnelUserGroupView(personnelBean));
			VvDataProcess vvDataProcess = new VvDataProcess(personnelBean.getClient());
			inputsession.setAttribute("vvCurrencyCollection",vvDataProcess.getVvCurrency());

			Collection hubInventoryGroupOvBeanCollection = loginProcess.getHubInventoryGroupData(personnelBean);
			personnelBean.setHubInventoryGroupOvBeanCollection(hubInventoryGroupOvBeanCollection);
			//Don't store in Session once all reference to it is removed from old code hubInventoryGroupOvBeanCollection
			inputsession.setAttribute("hubInventoryGroupOvBeanCollection",hubInventoryGroupOvBeanCollection);

            Collection opsHubIgOvBeanCollection = loginProcess.getOpsHubIgData(personnelBean);
            personnelBean.setOpsHubIgOvBeanCollection(opsHubIgOvBeanCollection);

            /*MenuProcess menuProcess = new MenuProcess(personnelBean.getClient());
			Collection menuItemOvBeanCollection = menuProcess.getSearchResult(personnelBean);
			personnelBean.setMenuItemOvBeanCollection(menuItemOvBeanCollection);*/

			if ( personnelBean != null )
			{
				//System.out.println( "bean:" + personnelBean.getLogonId() + " - " +  personnelBean.getPersonnelId() + " - " + personnelBean.getClient() );
				inputsession.setAttribute( "personnelBean",personnelBean );
        personnelBean.setLocale(setLocale(request,inputsession,request.getParameter("langSetting")));
      }
		  }

		  //Saving the flash info
		  /*{
			String flashversion=request.getParameter( "flashversion" );
			if ( flashversion == null )
			  flashversion="";

			String flashInstalled=request.getParameter( "flashInstalled" );
			if ( flashInstalled == null )
			  flashInstalled="";

			String finalflashver="";
			if ( "Yes".equalsIgnoreCase( flashInstalled ) )
			{
			  finalflashver="Flash " + flashversion;
			}
			else
			{
			  finalflashver="No Flash";
			}

			//copy input into bean
			UserEnvLogBean flashBean=new UserEnvLogBean();
			flashBean.setPersonnelId( Integer.parseInt( personnelId ) );
			flashBean.setDetail( finalflashver );

			//login
			if ( flashBean != null && flashBean.getPersonnelId() > 0 )
			{
			  SnoopProcess flashProcess=new SnoopProcess( dbclientname );
			  flashProcess.registerFlash( flashBean );
			}
        }*/

        }
        else
        {
          errormsg+="Authentication Error\n<BR>";
          errormsg+="Please Enter your Login_Id, Password and try again.</font>\n";

          returnResu.put("AUTH",auth);
		  returnResu.put("CLIENT_AUTH",clinetauth);
          returnResu.put("ERROSMSG",errormsg);
          return returnResu;
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
		errormsg="<font face=Arial size=3>Server Authentication Error<BR>\n";
        errormsg+="Your Session might be corrupted.<BR> Please close your browser and open it again.</font>\n";

        returnResu.put("AUTH",auth);
		returnResu.put("CLIENT_AUTH",clinetauth);
        returnResu.put("ERROSMSG",errormsg);
        return returnResu;
      }
    }
    else if ( auth.equalsIgnoreCase( "authenticated" ) )
    {

    }
    else
    {
      inputsession.setAttribute( "loginState","challenge" );
    }
    returnResu.put("AUTH",auth);
    returnResu.put("CLIENT_AUTH",clinetauth);
    returnResu.put("ERROSMSG",errormsg);
    return returnResu;
  }

  public static void buildcomments( String client,int allrelease14count1,PrintWriter commentsout )
  {
	 BulletinProcess bulletinProcess = new BulletinProcess(client.toUpperCase());
 	 try {
		 Collection connPoolMsgBeanCollection = bulletinProcess.getSearchResult();
		 Iterator poolMessageIterator = connPoolMsgBeanCollection.iterator();
		 while (poolMessageIterator.hasNext()) {
			ConnectionPoolMessageBean connPoolMessageBean = (ConnectionPoolMessageBean)
			poolMessageIterator.next();
		  commentsout.println("<br><u><b>" + connPoolMessageBean.getMessageTitle() +"</b></u><br><br>\n");

			String messageText = connPoolMessageBean.getMessageText();
			messageText = HelpObjs.findReplace(messageText, "\n", "<br>");
			commentsout.println(messageText+"<br><br>");
		 }
		}
	 catch (BaseException ex) {
	 }

	if ( allrelease14count1 == 987987 )
	{
	 commentsout.println(
		"<BR><B><CENTER><FONT FACE=\"Arial\" COLOR=\"RED\" SIZE=\"5\"><BLINK><B>Upon login we will update your Java Environment</B><BR><BR>\n" );
	 commentsout.println( "<A HREF=\"javascript:opennewwin();\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\">Click Here to review installation screens.</A></BLINK></FONT> \n" );
	 commentsout.println( "<BR><BR><FONT FACE=\"Arial\" SIZE=\"3\"><B>(If you have already updated please ignore.)</B></CENTER>  \n" );
	}
	} //end of method

  //	Larry Note: take struct locale.
public static Locale setLocale(HttpServletRequest request,HttpSession session,String lang){
	  Locale locale = (Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY);
	  if (locale == null)
		  locale = request.getLocale(); // take from brower.
	  if (lang != null && lang.length() == 5) {
		  String[] langArr = lang.split("_");
		  locale = new Locale(langArr[0],langArr[1],"");
		  session.setAttribute("langSetting",lang); // for our own use...
	  }
	  session.setAttribute("tcmISLocale", locale);
	  session.setAttribute(org.apache.struts.Globals.LOCALE_KEY, locale);
	  Config.set(session, Config.FMT_LOCALE, locale);
	  return locale;
  }
} //end class