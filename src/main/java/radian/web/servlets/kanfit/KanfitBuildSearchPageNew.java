package radian.web.servlets.kanfit;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KanfitBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Kanfit";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new KanfitServerResourceBundle();
   }
}