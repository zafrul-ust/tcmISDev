package radian.web.servlets.timken;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TimkenBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Timken";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TimkenServerResourceBundle();
   }
}