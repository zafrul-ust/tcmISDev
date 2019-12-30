package radian.web.servlets.hrgivon;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HrgivonClientHome extends radian.web.servlets.share.ClientHome
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