package radian.web;

import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 * Title:        Email Resoource
 * Copyright:    Copyright (c) 2003
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 */

public abstract class emailHelpObj
{
  public static void senddeveloperemail(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.getdeveloperemailaddress();
    String[] devloperemails = new String[]{gg};

    HelpObjs.javaSendMail("",subject,devloperemails,message);
  }

  public static void senddatabaseHelpemails(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.getdatabaseHelpemails();
    String[] dabatabasehelp = new String[]{gg};

    HelpObjs.javaSendMail("",subject,dabatabasehelp,message);
  }

  public static void sendnawazemail(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.getnawazemail();
    String[] nawazemail = new String[]{gg};

    HelpObjs.javaSendMail("",subject,nawazemail,message);
  }

  public static void sendtrongemail(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.gettrongemail();
    String[] trongemail = new String[]{gg};

    HelpObjs.javaSendMail("",subject,trongemail,message);
  }

  public static void sendsteveemail(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.getsteveemail();
    String[] steveemail = new String[]{gg};

    HelpObjs.javaSendMail("",subject,steveemail,message);
  }

  public static void sendnishemail(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.getnishemail();
    String[] nishemail = new String[]{gg};

    HelpObjs.javaSendMail("",subject,nishemail,message);
  }

   public static void sendSujataemail(String subject,String message)
  {
    String gg = "skulkarni@haastcm.com";
    String[] nishemail = new String[]{gg};

    HelpObjs.javaSendMail("",subject,nishemail,message);
  }


  public static void sendmsdshelpemail(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.getmsdshelpemail();
    String[] msdshelpemail = new String[]{gg};

    HelpObjs.javaSendMail("",subject,msdshelpemail,message);
  }

  public static void sendcatalogemail(String subject,String message)
  {
    String gg = radian.web.tcmisResourceLoader.getcatalogemail();
    String[] catalogemail = new String[]{gg};

    HelpObjs.javaSendMail("",subject,catalogemail,message);
  }
 }