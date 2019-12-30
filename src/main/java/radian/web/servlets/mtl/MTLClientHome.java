package radian.web.servlets.mtl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MTLClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "MTL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MTLServerResourceBundle();
   }
}