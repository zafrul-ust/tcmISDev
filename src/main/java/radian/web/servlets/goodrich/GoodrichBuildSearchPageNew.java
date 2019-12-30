package radian.web.servlets.goodrich;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GoodrichBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Goodrich";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GoodrichServerResourceBundle();
   }
}