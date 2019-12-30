package radian.web.servlets.kemfast;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KemfastBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Kemfast";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new KemfastServerResourceBundle();
   }
}