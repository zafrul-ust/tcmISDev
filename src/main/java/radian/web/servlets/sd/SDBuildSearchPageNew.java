package radian.web.servlets.sd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SDBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "SD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SDServerResourceBundle();
   }
}