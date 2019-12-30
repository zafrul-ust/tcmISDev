package radian.web.servlets.algat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlgatClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Algat";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AlgatServerResourceBundle();
   }
}