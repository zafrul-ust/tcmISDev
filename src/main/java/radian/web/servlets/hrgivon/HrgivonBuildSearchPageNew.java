package radian.web.servlets.hrgivon;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HrgivonBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Hrgivon";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HrgivonServerResourceBundle();
   }
}