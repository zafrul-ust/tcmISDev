package radian.web.servlets.am;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AMBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "AM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AMServerResourceBundle();
   }
}