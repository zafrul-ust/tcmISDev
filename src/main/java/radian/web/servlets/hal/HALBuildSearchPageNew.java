package radian.web.servlets.hal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HALBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "HAL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HALServerResourceBundle();
   }
}