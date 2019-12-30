package radian.web.servlets.doe;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DOEBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "DOE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DOEServerResourceBundle();
   }
}