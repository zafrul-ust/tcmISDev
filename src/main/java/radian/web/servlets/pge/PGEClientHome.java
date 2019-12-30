package radian.web.servlets.pge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PGEClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "PGE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new PGEServerResourceBundle();
   }
}