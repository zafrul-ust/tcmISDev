package radian.web.servlets.aerocz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroczClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Aerocz";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AeroczServerResourceBundle();
   }
}