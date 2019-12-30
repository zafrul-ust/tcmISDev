package radian.web.servlets.verasun;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VerasunBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Verasun";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new VerasunServerResourceBundle();
   }
}