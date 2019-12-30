package radian.web.servlets.cal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CALClientHome extends radian.web.servlets.share.ClientHome
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