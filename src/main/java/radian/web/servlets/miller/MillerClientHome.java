package radian.web.servlets.miller;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MillerClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Miller";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MillerServerResourceBundle();
   }
}