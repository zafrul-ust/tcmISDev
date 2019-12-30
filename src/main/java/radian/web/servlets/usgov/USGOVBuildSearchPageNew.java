package radian.web.servlets.usgov;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class USGOVBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "USGOV";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new USGOVServerResourceBundle();
   }
}