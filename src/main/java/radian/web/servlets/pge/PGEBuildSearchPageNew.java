package radian.web.servlets.pge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PGEBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "PGE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new PGEServerResourceBundle();
   }
}