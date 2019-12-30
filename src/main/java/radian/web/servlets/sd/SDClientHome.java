package radian.web.servlets.sd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SDClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "SD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SDServerResourceBundle();
   }
}