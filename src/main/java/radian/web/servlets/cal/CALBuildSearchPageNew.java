package radian.web.servlets.cal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CALBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "CAL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CALServerResourceBundle();
   }
}