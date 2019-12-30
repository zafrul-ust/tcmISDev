//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class URLCall
{

  String url;

  CmisApp parent;

  public URLCall( String sUrl,CmisApp p )
  {
	  	sUrl = sUrl.replaceAll(" ", "%20");
        url = new String((new ClientResourceBundle()).getString("WEBS_HOME"));
        url = url + sUrl;
        parent = p;
        // web start
        //ServiceManager.setServiceManagerStub("javax.jnlp.ServiceManagerStub");
        try
        {
        URL gohere = new URL(url);
        System.out.println("The URL is "+gohere.toString());
        if (showURL(gohere))
         {
         System.out.println("Launched URL Successfully");
         }
         else
         {
         System.out.println("Could not Launch URL Successfully");
         }
   }
   catch(Exception u33)
   {
         u33.printStackTrace();
   }
    //end

   //running it local
   //go(); //
  }

  public URLCall( String server,String sUrl,CmisApp p )
  {
        url=new String( server );
        url=url + sUrl;
        parent=p;
        go();
  }

  //11-13-01 I create this method so I can call my url directly (i.e. Seagate punch out ).  Ignore the boolean parameter
  public URLCall( String sUrl,CmisApp p,boolean b )
  {
        url=sUrl;
        parent=p;
        go();
  }

  private void go()
  {
        String prog=null;
        // use when login from web start
        try
        {
          URL gohere = new URL(url);
          if (showURL(gohere))
          {
          System.out.println("Launched URL Successfully");
          }
          else
          {
          System.out.println("Could not Launch URL Successfully");
          }
        }
        catch(Exception u33)
        {
          u33.printStackTrace();
        }
          //end of running web using web start

        /* running web pages local begin
        Runtime r = Runtime.getRuntime();
        try {
          ConfigFile cFile = new ConfigFile(parent);
          prog = "\""+cFile.getBrowser()+"\""+" "+url;
          System.out.println("Prog1:"+prog);
          r.exec(prog);

        } catch (Exception e0) {
        try {
          // From the stand alone application
          prog = (new ClientResourceBundle()).getString("BROWSER1") + url;
          r.exec(prog);
        } catch (Exception e) {
          try {
                prog = (new ClientResourceBundle()).getString("BROWSER2") + url;
                r.exec(prog);
          } catch (Exception e2) {
                try {
                  prog = (new ClientResourceBundle()).getString("BROWSER3") + url;
                  r.exec(prog);
                } catch (Exception e3) {
                  try {
                          prog = (new ClientResourceBundle()).getString("BROWSER4") + url;
                          r.exec(prog);
                  } catch (Exception e4) {
                        try {
                          prog = (new ClientResourceBundle()).getString("BROWSER5") + url;
                          r.exec(prog);
                        } catch (Exception e5) {
                          e5.printStackTrace();
                        }
                  }
                  }
                }
          }
        }   //end of running web page local
        */ //end
  }

  boolean showURL( URL url1 )
  {
        try
        {
          BasicService bs= ( BasicService ) ServiceManager.lookup( "javax.jnlp.BasicService" );
          return bs.showDocument( url1 );
        }
        catch ( UnavailableServiceException ue )
        {
          ue.printStackTrace();
          return false;
        }
  }
  }