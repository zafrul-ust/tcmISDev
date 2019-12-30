package radian.web.servlets.algat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlgatBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Algat";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AlgatServerResourceBundle();
   }
}