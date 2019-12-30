package radian.web.servlets.bl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BLClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "BL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BLServerResourceBundle();
   }
}