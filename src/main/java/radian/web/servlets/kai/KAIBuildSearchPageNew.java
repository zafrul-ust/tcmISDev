package radian.web.servlets.kai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KAIBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "KOREA_AEROSPACE_INDUSTRIES";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new KAIServerResourceBundle();
   }
}