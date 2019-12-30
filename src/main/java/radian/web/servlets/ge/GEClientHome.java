package radian.web.servlets.ge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GEClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "GE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GEServerResourceBundle();
   }
}