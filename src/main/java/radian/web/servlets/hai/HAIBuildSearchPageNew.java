package radian.web.servlets.hai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HAIBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "HAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HAIServerResourceBundle();
   }
}