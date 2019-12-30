package radian.web.servlets.oma;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMABuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "OMA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OMAServerResourceBundle();
   }
}