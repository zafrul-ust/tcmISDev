package radian.web.servlets.ge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GEBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "GE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GEServerResourceBundle();
   }
}