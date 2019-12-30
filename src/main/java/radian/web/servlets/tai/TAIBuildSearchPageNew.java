package radian.web.servlets.tai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TAIBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
       System.out.println("whyyyyy");
      return "TAI";
   }
   protected ServerResourceBundle getBundle()
   {
      System.out.println("whyyyyy111");
      return (ServerResourceBundle) new TAIServerResourceBundle();
   }
}